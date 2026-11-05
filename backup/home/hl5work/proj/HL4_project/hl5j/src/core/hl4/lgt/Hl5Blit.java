package hl4.lgt;

import hl4.cpu.Cpu;
import hl4.cpu.Memory;

/**
 * HL5: Java versions of the hot inner paths of the sprite blitter 0x8888c (BAO-CAO B10).
 * Each hook sits at the start of one pixel path (after the common set-up), reads the blitter's
 * stack frame, draws, adds exactly the instruction count the ARM path would have used and
 * continues at the common exit 0x889c6. Frame layout (sp after "sub sp,#0x120"):
 * d4 ctx, dc x, d8 y, 144 w, 148 h, a8 imgW, ac fbW, b0 4bpp row bytes, b4 source row start,
 * bc palette, c4 fb, cc pixels, 104..106 tint bytes, r10 alpha.
 */
final class Hl5Blit {
    static final int EXIT = 0x889c7;
    static final int KEY = 0x150bf7c;
    static final int H_4M16 = 0x8947c, H_8M16 = 0x89030, H_8M12 = 0x88ec8, H_8M8 = 0x891a8;
    /** second blitter 0x879d8 (keyed copy, no blend): exit 0x87acc */
    static final int H2_8M16 = 0x87c68, H2_8M8 = 0x87c04, H2_4M16 = 0x87dbc, H2_4M8 = 0x87e94, EXIT2 = 0x87acd;
    static final int[] HOOKS = {H_4M16, H_8M16, H_8M12, H_8M8, H2_8M16, H2_8M8, H2_4M16, H2_4M8};

    static boolean second(int addr) {
        return addr == H2_8M16 || addr == H2_8M8 || addr == H2_4M16 || addr == H2_4M8;
    }

    private final Cpu cpu;
    private final Memory m;
    // per-blit state
    private int tab, a32, f, g, b, key, pal, fb;
    private boolean tint;

    Hl5Blit(Cpu cpu, Memory m) {
        this.cpu = cpu;
        this.m = m;
    }

    private void blend(int c, int p) {
        int d = m.read16(p);
        int dR = (d & 0xf800) >> 11, dG = (d & 0x7e0) >> 6, dB = d & 0x1f;
        int R = m.read8(tab + ((a32 + ((c & 0xf800) >> 11)) << 5) + dR);
        int G = m.read8(tab + ((a32 + ((c & 0x7e0) >> 6)) << 5) + dG) << 1;
        int B = m.read8(tab + ((a32 + (c & 0x1f)) << 5) + dB);
        if (tint) {
            R = (R * f + (32 - f) * dR) >> 5;
            G = (G * g + (64 - g) * (dG << 1)) >> 6;
            B = (B * b + (32 - b) * dB) >> 5;
        }
        m.write16(p, (R << 11) | ((G << 5) & 0x7e0) | (B & 0x1f));
    }

    private void setup(int sp) {
        tab = m.read32(sp + 0xd4) + 0x69;
        a32 = cpu.r[10] << 5;
        f = m.read8(sp + 0x104);
        g = m.read8(sp + 0x105);
        b = m.read8(sp + 0x106);
        tint = f <= 31;
        key = m.read32(KEY);
        pal = m.read32(sp + 0xbc);
        fb = m.read32(sp + 0xc4);
    }

    /** Run the path hooked at addr; returns the instruction count of the original path from addr. */
    long run(int addr) {
        if (second(addr)) return run2(addr);
        int sp = cpu.r[13];
        setup(sp);
        if (addr == H_4M16) return blit4m16(sp);
        int x = m.read32(sp + 0xdc), y = m.read32(sp + 0xd8);
        int w = m.read32(sp + 0x144), h = m.read32(sp + 0x148);
        int fbW = m.read32(sp + 0xac);
        // start cost, cost if h<=0, set-up cost, pixel tail, final exit branch
        if (addr == H_8M16)
            return blit8(sp, y * fbW + x, 1, fbW - w, w, h, 9, 1, 6, 11, 2);
        if (addr == H_8M12)
            return blit8(sp, (y + h - 1) * fbW + x + w - 1, -1, -(fbW - w), w, h, 15, 1, 4, 12, 1);
        return blit8(sp, y * fbW + x + w - 1, -1, fbW + w, w, h, 13, 2, 4, 12, 2);
    }

    private long blit8(int sp, int dst, int step, int rowAdj, int w, int h,
                       int cStart, int cNoRows, int cSetup, int cTail, int cExit) {
        long ic = cStart;
        if (h <= 0) return ic + cNoRows;
        ic += cSetup;
        int src = m.read32(sp + 0xcc), imgW = m.read32(sp + 0xa8);
        int rs = m.read32(sp + 0xb4);
        int opaque = tint ? 58 + 34 + 11 : 58 + 11;
        int key = this.key, pal = this.pal, fb = this.fb;
        for (int row = h; ; ) {
            ic += 3;
            if (w <= 0) {
                ic += 1;
            } else {
                ic += 9;
                int s = rs;
                for (int n = w; ; ) {
                    int c = m.read16(pal + (m.read8(src + s) << 1));
                    s++;
                    ic += 12 + cTail;
                    if (c != key) {
                        blend(c, fb + (dst << 1));
                        ic += opaque;
                    }
                    dst += step;
                    if (--n == 0) break;
                    ic += 1;
                }
            }
            rs += imgW;
            dst += rowAdj;
            ic += 16;
            if (--row == 0) break;
            ic += 1;
        }
        return ic + cExit;
    }

    private long blit4m16(int sp) {
        int x = m.read32(sp + 0xdc), y = m.read32(sp + 0xd8);
        int w = m.read32(sp + 0x144), h = m.read32(sp + 0x148);
        int fbW = m.read32(sp + 0xac);
        int sx = m.read32(sp + 0x150);
        long ic = 10;
        if (h <= 0) return ic + 2;
        ic += 11;
        int src = m.read32(sp + 0xcc), rowBytes = m.read32(sp + 0xb0);
        int rs = m.read32(sp + 0xb4);
        int dst = y * fbW + x;
        boolean odd = (sx & 1) != 0;
        int op63 = tint ? 63 + 34 + 11 : 63 + 11;
        int op58 = tint ? 58 + 34 + 11 : 58 + 11;
        int opTrail = tint ? 63 + 35 + 11 : 63 + 11;
        int key = this.key, pal = this.pal, fb = this.fb;
        for (int row = h; ; ) {
            ic += 5;
            int cnt = w;
            int s = rs;
            if (odd) {
                int c = m.read16(pal + ((m.read8(src + s) & 0xf) << 1));
                s++;
                ic += 15 + 6;
                if (c != key) {
                    blend(c, fb + (dst << 1));
                    ic += op63;
                }
                dst++;
                cnt = w - 1;
            } else {
                ic += 1;
            }
            int n = cnt >> 1;
            ic += 4;
            if (n <= 0) {
                ic += 1;
            } else {
                ic += 9;
                for (; ; ) {
                    int v = m.read8(src + s);
                    s++;
                    int c = m.read16(pal + ((v >> 4) << 1));
                    ic += 16 + 15 + 11;
                    if (c != key) {
                        blend(c, fb + (dst << 1));
                        ic += op58;
                    }
                    dst++;
                    c = m.read16(pal + ((v & 0xf) << 1));
                    if (c != key) {
                        blend(c, fb + (dst << 1));
                        ic += op58;
                    }
                    dst++;
                    if (--n == 0) break;
                    ic += 1;
                }
            }
            ic += 4;
            if ((cnt & 1) == 0) {
                ic += 1;
            } else {
                int c = m.read16(pal + ((m.read8(src + s) >> 4) << 1));
                ic += 11 + 3;
                if (c != key) {
                    blend(c, fb + (dst << 1));
                    ic += opTrail;
                }
                dst++;
            }
            rs += rowBytes;
            dst += fbW - w;
            ic += 16;
            if (--row == 0) break;
            ic += 1;
        }
        return ic + 2;
    }

    // ------------------------------------------------------------------
    // blitter 2 (0x879d8): sb = source row start, sl = source base, r8 = palette,
    // sp+40 fb, 8c w, 90 h, 48 y, 4c x, 34 fbW, 30 imgW, 38 4bpp row bytes, r4 = sx
    private long run2(int addr) {
        int sp = cpu.r[13];
        int[] r = cpu.r;
        int fb2 = m.read32(sp + 0x40), w = m.read32(sp + 0x8c), h = m.read32(sp + 0x90);
        int y = m.read32(sp + 0x48), x = m.read32(sp + 0x4c), fbW = m.read32(sp + 0x34);
        int rs = r[9], src = r[10], pal2 = r[8];
        int key2 = m.read32(KEY);
        if (addr == H2_8M16 || addr == H2_8M8) {
            boolean mir = addr == H2_8M8;
            int imgW = m.read32(sp + 0x30);
            long ic = mir ? 11 : 8;
            if (h <= 0) return ic + 1;
            ic += mir ? 1 : 3;
            int dst = y * fbW + x + (mir ? w - 1 : 0);
            int step = mir ? -1 : 1, adj = mir ? fbW + w : fbW - w;
            for (int row = h; ; ) {
                ic += 3;
                if (w > 0) {
                    ic += 6;
                    int s = rs;
                    for (int n = w; n > 0; n--) {
                        int c = m.read16(pal2 + (m.read8(src + s) << 1));
                        s++;
                        ic += 13;
                        if (c != key2) { m.write16(fb2 + (dst << 1), c); ic++; }
                        dst += step;
                    }
                }
                rs += imgW;
                dst += adj;
                ic += mir ? 12 : 13;
                if (--row == 0) break;
            }
            return ic + 1;
        }
        boolean mir = addr == H2_4M8;
        int rowBytes = m.read32(sp + 0x38);
        boolean odd = (r[4] & 1) != 0;
        long ic = mir ? 10 : 8;
        if (h <= 0) return ic + 1;
        ic += 4;
        int dst = y * fbW + x + (mir ? w - 1 : 0);
        int step = mir ? -1 : 1, adj = mir ? fbW + w : fbW - w;
        for (int row = h; ; ) {
            ic += 5;
            int cnt = w, s = rs;
            if (odd) {
                int c = m.read16(pal2 + ((m.read8(src + s) & 0xf) << 1));
                s++;
                ic += 14 + 4;
                if (c != key2) { m.write16(fb2 + (dst << 1), c); ic += 3; }
                dst += step;
                cnt = w - 1;
            }
            int n = cnt >> 1;
            ic += 4;
            if (n > 0) {
                ic += 6;
                for (; n > 0; n--) {
                    int v = m.read8(src + s);
                    s++;
                    int c = m.read16(pal2 + ((v >> 4) << 1));
                    ic += 11 + 9 + 5;
                    if (c != key2) { m.write16(fb2 + (dst << 1), c); ic++; }
                    dst += step;
                    c = m.read16(pal2 + ((v & 0xf) << 1));
                    if (c != key2) { m.write16(fb2 + (dst << 1), c); ic++; }
                    dst += step;
                }
            }
            ic += 4;
            if ((cnt & 1) != 0) {
                int c = m.read16(pal2 + ((m.read8(src + s) >> 4) << 1));
                ic += 10 + 1;
                if (c != key2) { m.write16(fb2 + (dst << 1), c); ic += 3; }
                dst += step;
            }
            rs += rowBytes;
            dst += adj;
            ic += mir ? 12 : 13;
            if (--row == 0) break;
        }
        return ic + 1;
    }
}
