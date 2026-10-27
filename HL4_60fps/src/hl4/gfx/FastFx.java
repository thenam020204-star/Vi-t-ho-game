package hl4.gfx;

/*
 * session 2c: drop-in replacement of Fx.scaled (scaled / rotated / blended image blit, used by
 * every extended sprite cell, damage numbers and big skill effects). Same arithmetic as
 * Fx.scaled + Fx.colorRaw + Fx.blendPixel, same pixel write order, so the output is identical
 * pixel for pixel; only the work is cheaper: the visible row/column range is clipped once
 * instead of testing every pixel, per-row source values and blend constants are hoisted, and
 * the two per-pixel method calls are inlined. Fx.scaled jumps here (tools/src/Patch.java).
 */
public final class FastFx {
    private FastFx() {
    }

    // arguments in the order of Fx.scaled(frame, gbm, n .. n10, bl, n11, n12)
    public static void scaled(Frame frame, Gbm gbm, int x, int y, int w, int h, int srcX, int srcY, int mode,
            int scaleX, int scaleY, int anchor, boolean bl, int alpha, int bmode) {
        if (bl && alpha == 0) {
            return;
        }
        int dw = w * scaleX / 100;
        if (dw < 1) {
            dw = 1;
        }
        int dh = h * scaleY / 100;
        if (dh < 1) {
            dh = 1;
        }
        int stepX = (w << 10) / dw + 1;
        int stepY = (h << 10) / dh + 1;
        int outW;
        int outH;
        int cols;
        int rows;
        if (mode < 4) {
            outW = dw;
            outH = dh;
            cols = dw;
            rows = dh;
        } else {
            outW = dh;
            outH = dw;
            cols = dh;
            rows = dw;
        }
        int ox = x;
        int oy = y;
        if ((anchor & 0x10) != 0) {
            ox -= outW >> 1;
        } else if ((anchor & 0x20) != 0) {
            ox -= outW;
        }
        if ((anchor & 2) != 0) {
            oy -= outH >> 1;
        } else if ((anchor & 4) != 0) {
            oy -= outH;
        }
        // rows: oy + r in [max(clipT, 0), min(clipB, h)); cols: ox + c in [max(clipL, 0), clipR)
        int top = frame.clipT < 0 ? 0 : frame.clipT;
        int bottom = frame.clipB < frame.h ? frame.clipB : frame.h;
        int left = frame.clipL < 0 ? 0 : frame.clipL;
        int right = frame.clipR;
        int r0 = top - oy;
        if (r0 < 0) {
            r0 = 0;
        }
        int r1 = bottom - oy;
        if (r1 > rows) {
            r1 = rows;
        }
        int c0 = left - ox;
        if (c0 < 0) {
            c0 = 0;
        }
        int c1 = right - ox;
        if (c1 > cols) {
            c1 = cols;
        }
        if (r0 >= r1 || c0 >= c1) {
            return;
        }
        short[] px = frame.px;
        int fw = frame.w;
        int plen = px.length;
        byte[] data = gbm.data;
        int dlen = data.length;
        int off = gbm.pixOff;
        short[] pal = gbm.pal;
        int npal = pal.length;
        boolean nib = gbm.type == 11;
        int rowLen = nib ? gbm.w + 1 >> 1 : gbm.w;
        int half = nib ? srcX >> 1 : srcX;
        // blend constants (Fx.blendPixel with n3 = alpha, n4 = bmode)
        int bm = bmode;
        if (bm < 0 || bm > 16) {
            bm = 0;
        }
        bTab = bm * 1024;
        bGray = bm == 16;
        bFade = alpha < 255;
        bA15 = ((alpha & 0xF8) >> 3) + 1;
        bA16 = ((alpha & 0xF8) >> 2) + 1;
        bA17 = (alpha & 0xF8) >> 3;
        int n = c1 - c0;
        if (n > SCRATCH) {
            FastFx.slow(px, fw, plen, data, dlen, off, pal, npal, nib, rowLen, half, srcY, w, h, mode, stepX, stepY,
                    ox, oy, r0, r1, c0, c1, bl);
            return;
        }
        int[] cA = colA;
        int[] cB = colB;
        int i;
        if (mode < 4) {
            // per column: source x -> byte offset within the row and nibble shift
            boolean flip = mode == 1 || mode == 3;
            i = 0;
            while (i < n) {
                int sx = (c0 + i) * stepX >> 10;
                if (flip) {
                    sx = w - 1 - sx;
                }
                cA[i] = nib ? half + (sx >> 1) : half + sx;
                cB[i] = (sx & 1) == 0 ? 4 : 0;
                ++i;
            }
            int mn = cA[0];
            int mx = cA[0];
            i = 1;
            while (i < n) {
                if (cA[i] < mn) {
                    mn = cA[i];
                }
                if (cA[i] > mx) {
                    mx = cA[i];
                }
                ++i;
            }
            boolean flipY = mode == 2 || mode == 3;
            int r = r0;
            while (r < r1) {
                int sy = r * stepY >> 10;
                if (flipY) {
                    sy = h - 1 - sy;
                }
                int line = srcY + sy;
                if (line >= 0) {
                    int ro = off + rowLen * line;
                    int base = (oy + r) * fw + ox + c0;
                    boolean safe = ro + mn >= 0 && ro + mx < dlen && base + n <= plen;
                    i = 0;
                    while (i < n) {
                        int k = ro + cA[i];
                        if (safe || k >= 0 && k < dlen) {
                            int q = data[k] & 0xFF;
                            if (nib) {
                                q = q >> cB[i] & 0xF;
                            }
                            int di = base + i;
                            if (q < npal && (safe || di < plen)) {
                                int col = pal[q] & 0xFFFF;
                                if (col != 63519) {
                                    px[di] = bl ? (short) FastFx.blend(col, px[di] & 0xFFFF) : (short) col;
                                }
                            }
                        }
                        ++i;
                    }
                }
                ++r;
            }
            return;
        }
        // mode >= 4 (90 / 270 degrees): columns walk the source rows, rows walk the source columns
        boolean flipY = mode == 4 || mode == 7;
        i = 0;
        while (i < n) {
            int sy = (c0 + i) * stepY >> 10;
            if (flipY) {
                sy = h - 1 - sy;
            }
            int line = srcY + sy;
            cA[i] = line;
            cB[i] = off + rowLen * line;
            ++i;
        }
        boolean flipX = mode == 6 || mode == 7;
        int r = r0;
        while (r < r1) {
            int sx = r * stepX >> 10;
            if (flipX) {
                sx = w - 1 - sx;
            }
            int xo = nib ? half + (sx >> 1) : half + sx;
            int sh = (sx & 1) == 0 ? 4 : 0;
            int base = (oy + r) * fw + ox + c0;
            i = 0;
            while (i < n) {
                if (cA[i] >= 0) {
                    int k = cB[i] + xo;
                    if (k >= 0 && k < dlen) {
                        int q = data[k] & 0xFF;
                        if (nib) {
                            q = q >> sh & 0xF;
                        }
                        int di = base + i;
                        if (q < npal && di < plen) {
                            int col = pal[q] & 0xFFFF;
                            if (col != 63519) {
                                px[di] = bl ? (short) FastFx.blend(col, px[di] & 0xFFFF) : (short) col;
                            }
                        }
                    }
                }
                ++i;
            }
            ++r;
        }
    }

    private static final int SCRATCH = 1024;
    private static final int[] colA = new int[SCRATCH];
    private static final int[] colB = new int[SCRATCH];
    private static int bTab;
    private static boolean bGray;
    private static boolean bFade;
    private static int bA15;
    private static int bA16;
    private static int bA17;

    // Fx.blendPixel(col, d, alpha, bmode) with the per-call constants set by scaled()
    private static int blend(int col, int d) {
        int dr = d >> 11;
        int dg = (d & 0x7E0) >> 6;
        int db = d & 0x1F;
        int cr;
        int cg;
        int cb;
        if (bGray) {
            cr = (col >> 11) * 6 + (col & 0x1F) * 2 + ((col & 0x7E0) >> 6) * 4 >> 4;
            cg = cr;
            cb = cr;
        } else {
            byte[] t = Fx.TABLE;
            int tb = bTab;
            cr = t[tb + (col >> 11) * 32 + dr] & 0xFF;
            cg = t[tb + ((col & 0x7E0) >> 6) * 32 + dg] & 0xFF;
            cb = t[tb + (col & 0x1F) * 32 + db] & 0xFF;
        }
        int g2 = cg * 2;
        if (bFade) {
            cr = cr * bA15 + dr * (32 - bA15) >> 5;
            g2 = g2 * bA16 + dg * 2 * (64 - bA16) >> 6;
            cb = cb * bA17 + db * (32 - bA17) >> 5;
        }
        return (cr << 11 | g2 << 5 & 0x7E0 | cb & 0x1F) & 0xFFFF;
    }

    // generic path (visible span wider than the scratch tables), same arithmetic per pixel
    private static void slow(short[] px, int fw, int plen, byte[] data, int dlen, int off, short[] pal, int npal,
            boolean nib, int rowLen, int half, int srcY, int w, int h, int mode, int stepX, int stepY,
            int ox, int oy, int r0, int r1, int c0, int c1, boolean bl) {
        int r = r0;
        while (r < r1) {
            int base = (oy + r) * fw + ox;
            int c = c0;
            while (c < c1) {
                int sx;
                int sy;
                if (mode < 4) {
                    sx = c * stepX >> 10;
                    if (mode == 1 || mode == 3) {
                        sx = w - 1 - sx;
                    }
                    sy = r * stepY >> 10;
                    if (mode == 2 || mode == 3) {
                        sy = h - 1 - sy;
                    }
                } else {
                    sy = c * stepY >> 10;
                    if (mode == 4 || mode == 7) {
                        sy = h - 1 - sy;
                    }
                    sx = r * stepX >> 10;
                    if (mode == 6 || mode == 7) {
                        sx = w - 1 - sx;
                    }
                }
                int line = srcY + sy;
                if (line >= 0) {
                    int k = off + rowLen * line + (nib ? half + (sx >> 1) : half + sx);
                    if (k >= 0 && k < dlen) {
                        int q = data[k] & 0xFF;
                        if (nib) {
                            q = (sx & 1) == 0 ? q >> 4 : q & 0xF;
                        }
                        int di = base + c;
                        if (q < npal && di < plen) {
                            int col = pal[q] & 0xFFFF;
                            if (col != 63519) {
                                px[di] = bl ? (short) FastFx.blend(col, px[di] & 0xFFFF) : (short) col;
                            }
                        }
                    }
                }
                ++c;
            }
            ++r;
        }
    }
}
