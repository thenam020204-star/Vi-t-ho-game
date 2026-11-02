package com.namp.vh;

/**
 * Font bitmap (bo tool NAM_P + glyph ve tay) thay cho Paint/Typeface trong
 * com.com2us.wrapper.font.CFont / CSGLFont cua Inotia 4. Hai mat chu:
 *  - F18 (font/f18vn.bin, o 18 hang): size nho hon 24, phong q/16, q = max(16, size) nhu TEST7;
 *  - F24 (font/f24vn.bin, o 28 hang, ve lai o co lon that, moi dau >= 2x2 px): size lon hon hoac bang 24, phong q/24 (q = size)
 *    nen chi to len (he so khong nho hon 1): 24 -> x1, 28 -> x1.167, 38 -> x1.583. Xem docs/font_lon.md.
 *
 * Hop dong voi libgame.so (giu nguyen nhu ban goc):
 *  - chuoi vao la byte[] UTF-8 (MainActivity.TextEncodingType = "UTF-8");
 *  - chu ve mau trang 0xFFFFFFFF tren nen 0 vao int[] mPixelbuffer dung chung voi native;
 *  - drawText: hang ngang = do rong tra ve, cao (int)(size*1.3), duong chan chu o y = size;
 *  - drawTextInRect: hang ngang = rectW, cao = tong chieu cao cac dong;
 *  - getStringWidthHeightInRect: out[0] = rong, out[1] = cao, cung thuat toan ngat dong.
 *
 * Phong nearest-neighbour: F18 he so q/16 (14 -> x1, 20 -> x1.25), F24 he so q/24 (24 -> x1, 28 -> x1.167, 38 -> x1.583).
 * Chieu cao dong (H+GAP)*q/den: cung gia tri voi TEST7 o moi size (20*q/16 == 30*q/24 == 1.25*q).
 * Khong cap phat object trong luc ve: UTF-8 giai ma vao bo dem char[] dung lai.
 */
public final class VNFont {

    /** Mot mat chu: bang glyph + thong so luoi. Khoi tao mot lan (init), khong cap phat trong luc ve. */
    private static final class Face {
        final int h;            // chieu cao o chu
        final int base;         // hang day chu (hang cuoi cua than chu)
        final int gap;          // khoang cach dong (px nguon, nhan theo he so)
        final int ulRows;       // do day gach chan (hang nguon)
        final int den;          // mau so he so phong (16 hoac 24)
        boolean ready;
        char[] chars;           // da sap xep tang dan
        short[] sortedIdx;
        short[] asciiIdx;
        byte[] widths;
        int[] offsets;
        byte[] bits;
        int atlasW;
        int qIdx;               // glyph '?' thay cho ky tu thieu

        Face(int h, int base, int gap, int ulRows, int den) {
            this.h = h;
            this.base = base;
            this.gap = gap;
            this.ulRows = ulRows;
            this.den = den;
        }
    }

    private static final int BIG = 24;                              // size >= BIG dung F24
    private static final Face F18 = new Face(18, 14, 2, 1, 16);
    private static final Face F24 = new Face(28, 23, 2, 2, 24);
    private static Face cur = F18;                                  // mat chu cua lan goi hien tai (cac ham cong khai deu synchronized)

    private static char[] cbuf = new char[256];
    private static int clen;

    private VNFont() { }

    // ------------------------------------------------------------ nap font
    private static void init() {
        if (F18.ready && F24.ready) return;
        String d = FontData.DATA;
        byte[] b = new byte[d.length()];
        for (int i = 0; i < b.length; i++) b[i] = (byte) d.charAt(i);
        parse(F18, b);
        String[] ps = FontData24.PARTS;
        int total = 0;
        for (int i = 0; i < ps.length; i++) total += ps[i].length();
        b = new byte[total];
        int k = 0;
        for (int i = 0; i < ps.length; i++) {
            String t = ps[i];
            for (int m = 0; m < t.length(); m++) b[k++] = (byte) t.charAt(m);
        }
        parse(F24, b);
    }

    /** Doc du lieu .bin (xem f18io.py): writeUTF charset | u8 cao | u8 rong[n] | atlas 1bpp LSB truoc. */
    private static void parse(Face f, byte[] b) {
        int p = 0;
        int nb = ((b[p] & 0xFF) << 8) | (b[p + 1] & 0xFF);
        p += 2;
        int cl = decode(b, p, nb, null);
        char[] cs = new char[cl];
        decode(b, p, nb, cs);
        p += nb;
        int n = cs.length;
        int h = b[p++] & 0xFF;
        if (h != f.h) throw new IllegalStateException("font height " + h + " != " + f.h);
        f.widths = new byte[n];
        f.offsets = new int[n];
        int w = 0;
        for (int i = 0; i < n; i++) {
            f.widths[i] = b[p++];
            f.offsets[i] = w;
            w += f.widths[i];
        }
        f.atlasW = w;
        int nbits = (f.atlasW * f.h + 7) >> 3;
        f.bits = new byte[nbits];
        System.arraycopy(b, p, f.bits, 0, nbits);
        f.asciiIdx = new short[128];
        for (int i = 0; i < 128; i++) f.asciiIdx[i] = -1;
        f.chars = new char[n];
        f.sortedIdx = new short[n];
        for (int i = 0; i < n; i++) {
            char c = cs[i];
            if (c < 128) f.asciiIdx[c] = (short) i;
            int k = i;
            while (k > 0 && f.chars[k - 1] > c) {
                f.chars[k] = f.chars[k - 1];
                f.sortedIdx[k] = f.sortedIdx[k - 1];
                k--;
            }
            f.chars[k] = c;
            f.sortedIdx[k] = (short) i;
        }
        f.qIdx = f.asciiIdx['?'];
        f.ready = true;
    }

    /** Giai ma UTF-8 (1-3 byte, BMP). out == null: chi dem. */
    private static int decode(byte[] b, int off, int len, char[] out) {
        int n = 0, i = off, end = off + len;
        while (i < end) {
            int c = b[i] & 0xFF;
            char ch;
            if (c < 0x80) { ch = (char) c; i++; }
            else if ((c & 0xE0) == 0xC0 && i + 1 < end) {
                ch = (char) (((c & 0x1F) << 6) | (b[i + 1] & 0x3F)); i += 2;
            } else if ((c & 0xF0) == 0xE0 && i + 2 < end) {
                ch = (char) (((c & 0x0F) << 12) | ((b[i + 1] & 0x3F) << 6) | (b[i + 2] & 0x3F)); i += 3;
            } else if ((c & 0xF8) == 0xF0 && i + 3 < end) {
                ch = '?'; i += 4;
            } else { ch = '?'; i++; }
            if (ch == 0) break;                      // chuoi C ket thuc bang 0
            if (out != null) out[n] = ch;
            n++;
        }
        return n;
    }

    /** Chon mat chu theo size (size >= 24 -> F24, con lai F18) va giai ma chuoi vao cbuf. */
    private static void load(byte[] b, int size) {
        init();
        cur = size >= BIG ? F24 : F18;
        if (b == null) { clen = 0; return; }
        if (cbuf.length < b.length) cbuf = new char[b.length + 64];
        clen = decode(b, 0, b.length, cbuf);
    }

    // ------------------------------------------------------------ glyph
    private static int glyph(char c) {
        Face f = cur;
        if (c < 128) {
            int i = f.asciiIdx[c];
            return i >= 0 ? i : f.qIdx;
        }
        int lo = 0, hi = f.chars.length - 1;
        while (lo <= hi) {
            int m = (lo + hi) >>> 1;
            char v = f.chars[m];
            if (v < c) lo = m + 1;
            else if (v > c) hi = m - 1;
            else return f.sortedIdx[m];
        }
        return f.qIdx;
    }

    /**
     * Tu so q cua he so phong q/den: q = max(16, size). F18: den 16 (14 -> x1, 20 -> x1.25); F24 (size >= 24): den 24
     * (24 -> x1, 28 -> x1.167, 38 -> x1.583), chi to len. Ban dau dung boi so nguyen k = (size+2)/18 nhung tren may that
     * (2400x1080) chu chi ~60% co chu goc, con x2 thi vuot bo dem drawText (cao size*1.3) -> cat chu.
     */
    private static int scale(int size) {
        return size < 16 ? 16 : size;
    }

    /** Do rong nguon (px f18) cua 1 ky tu. */
    private static int srcAdv(char c, boolean bold) {
        if (c == '\n' || c == '\r') return 0;
        return cur.widths[glyph(c)] + (bold ? 1 : 0);
    }

    private static int srcWidth(int from, int to, boolean bold) {
        int w = 0;
        for (int i = from; i < to; i++) w += srcAdv(cbuf[i], bold);
        return w;
    }

    /** Doi px nguon -> px dich theo q/den (lam tron xuong, dung cho ca do va ve nen luon khop). */
    private static int sc(int v, int q) {
        int d = cur.den;
        return d == 16 ? (v * q) >> 4 : (v * q) / d;
    }

    private static int width(int from, int to, int q, boolean bold) {
        return sc(srcWidth(from, to, bold), q);
    }

    private static int lineH(int q) {
        return sc(cur.h + cur.gap, q);
    }

    /** Ve [from,to) cua cbuf: goc trai-tren o chu tai (x,y), hang ngang stride, toi da rows hang. */
    private static void blit(int[] buf, int stride, int rows, int from, int to, int x, int y,
                             int q, boolean bold, boolean underline) {
        Face f = cur;
        int H = f.h, atlasW = f.atlasW;
        byte[] bits = f.bits;
        int sx = 0;                                   // vi tri cong don theo px nguon
        for (int i = from; i < to; i++) {
            char c = cbuf[i];
            if (c == '\n' || c == '\r') continue;
            int g = glyph(c);
            int gw = f.widths[g];
            int go = f.offsets[g];
            for (int r = 0; r < H; r++) {
                int y0 = y + sc(r, q), y1 = y + sc(r + 1, q);
                int base = r * atlasW + go;
                for (int col = 0; col < gw; col++) {
                    int kk = base + col;
                    if ((bits[kk >> 3] & (1 << (kk & 7))) == 0) continue;
                    int cx = sx + col;
                    fill(buf, stride, rows, x + sc(cx, q), y0, x + sc(cx + (bold ? 2 : 1), q), y1);
                }
            }
            sx += gw + (bold ? 1 : 0);
        }
        if (underline && sx > 0) {
            int uy = y + sc(f.base + 2, q), uy1 = y + sc(f.base + 2 + f.ulRows, q);
            for (int yy = uy; yy < uy1; yy++) hline(buf, stride, rows, x, x + sc(sx, q), yy);
        }
    }

    /** To dac hinh chu nhat [x0,x1) x [y0,y1), cat theo khung buf. */
    private static void fill(int[] buf, int stride, int rows, int x0, int y0, int x1, int y1) {
        if (y0 < 0) y0 = 0;
        if (y1 > rows) y1 = rows;
        if (x0 < 0) x0 = 0;
        if (x1 > stride) x1 = stride;
        for (int yy = y0; yy < y1; yy++) {
            int row = yy * stride;
            for (int xx = x0; xx < x1; xx++) {
                int p = row + xx;
                if (p < buf.length) buf[p] = 0xFFFFFFFF;
            }
        }
    }

    private static void hline(int[] buf, int stride, int rows, int x0, int x1, int yy) {
        if (yy < 0 || yy >= rows) return;
        if (x1 > stride) x1 = stride;
        for (int xx = x0 < 0 ? 0 : x0; xx < x1; xx++) {
            int p = yy * stride + xx;
            if (p < buf.length) buf[p] = 0xFFFFFFFF;
        }
    }

    private static void clear(int[] buf, int n) {
        if (n > buf.length) n = buf.length;
        for (int i = 0; i < n; i++) buf[i] = 0;
    }

    /** Hang tren cua o chu sao cho duong chan chu (hang base) nam o y = size nhu Paint goc. */
    private static int topFor(int size, int q) {
        int t = size - sc(cur.base + 1, q);
        return t < 0 ? 0 : t;
    }

    // ------------------------------------------------------------ CFont
    /** CFont.getStringWidth([BII)I */
    public static synchronized int getStringWidth(byte[] b, int size, int style) {
        load(b, size);
        boolean bold = (style & 1) != 0;
        return width(0, clen, scale(size), bold) + 1;
    }

    /** CFont.drawText([BII)I */
    public static synchronized int drawText(int[] buf, byte[] b, int size, int style) {
        load(b, size);
        int k = scale(size);
        boolean bold = (style & 1) != 0;
        int w = width(0, clen, k, bold) + 1;
        int rows = (int) (size * 1.3d);
        clear(buf, w * rows);
        blit(buf, w, rows, 0, clen, 0, topFor(size, k), k, bold, (style & 4) != 0);
        return w;
    }

    /** CFont.drawText([BII[I)I : ve tung doan vua maxW, pos[0] = vi tri tiep theo hoac -1. */
    public static synchronized int drawTextPart(int[] buf, int maxW, byte[] b, int size, int style, int[] pos) {
        load(b, size);
        int start = pos[0];
        if (start >= clen) { pos[0] = -1; return 0; }
        if (start < 0) return 0;
        int k = scale(size);
        boolean bold = (style & 1) != 0;
        int end = start, sw = 0;
        while (end < clen) {
            int a = srcAdv(cbuf[end], bold);
            if (sc(sw + a, k) + 1 >= maxW && end > start) break;
            sw += a;
            end++;
        }
        int w = sc(sw, k) + 1;
        int rows = (int) (size * 1.3d);
        clear(buf, w * rows);
        blit(buf, w, rows, start, end, 0, topFor(size, k), k, bold, (style & 4) != 0);
        pos[0] = end >= clen ? -1 : end;
        return w;
    }

    // ------------------------------------------------------------ ngat dong
    private static int lineEnd;   // ket qua nextLine: cuoi phan ve
    private static int lineNext;  // dau dong ke tiep

    /** Ngat dong theo px tai dau cach; tu dai hon ca dong thi cat theo ky tu. */
    private static void nextLine(int start, int maxW, int k, boolean bold) {
        int w = 0, i = start, lastSpace = -1;
        while (i < clen) {
            char c = cbuf[i];
            if (c == '\n') { lineEnd = i; lineNext = i + 1; return; }
            int a = srcAdv(c, bold);
            if (sc(w + a, k) + 1 > maxW && i > start) {
                if (c == ' ') { lineEnd = i; lineNext = i + 1; return; }
                if (lastSpace > start) { lineEnd = lastSpace; lineNext = lastSpace + 1; return; }
                lineEnd = i; lineNext = i; return;
            }
            if (c == ' ') lastSpace = i;
            w += a;
            i++;
        }
        lineEnd = clen; lineNext = clen;
    }

    /** CSGLFont.getStringWidthHeightInRect([BIIII[I)I */
    public static synchronized int measureInRect(byte[] b, int size, int style, int maxW, int mode, int[] out) {
        if (mode != 0 && mode != 1) { out[0] = 0; out[1] = 0; return 0; }
        load(b, size);
        int k = scale(size);
        boolean bold = (style & 1) != 0;
        int lh = lineH(k);
        int mw = 0, h = 0, s = 0;
        while (s < clen) {
            nextLine(s, maxW, k, bold);
            int w = width(s, lineEnd, k, bold) + 1;
            if (w > mw) mw = w;
            h += lh;
            s = lineNext;
        }
        if (mw > maxW) mw = maxW;
        out[0] = mw;
        out[1] = h;
        return mw;
    }

    /** CSGLFont.drawTextInRect([BIIIII)I : align 0 trai, 1 giua, 2 phai. */
    public static synchronized int drawInRect(int[] buf, byte[] b, int size, int style, int align, int maxW, int mode) {
        load(b, size);
        if (maxW <= 0) return 0;
        int k = scale(size);
        boolean bold = (style & 1) != 0;
        boolean ul = (style & 4) != 0;
        int lh = lineH(k);
        int lines = 0, s = 0;
        if (mode == 0 || mode == 1) {
            while (s < clen) { nextLine(s, maxW, k, bold); lines++; s = lineNext; }
        }
        int rows = lines * lh;
        clear(buf, maxW * rows);
        s = 0;
        int y = 0;
        int top = (lh - sc(cur.h, k)) / 2;
        while (s < clen && y < rows) {
            nextLine(s, maxW, k, bold);
            int w = width(s, lineEnd, k, bold);
            int x = align == 1 ? (maxW - w) / 2 : align == 2 ? maxW - w : 0;
            if (x < 0) x = 0;
            blit(buf, maxW, rows, s, lineEnd, x, y + top, k, bold, ul);
            y += lh;
            s = lineNext;
        }
        return 0;
    }
}
