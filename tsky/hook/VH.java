import java.io.DataInputStream;
import java.io.InputStream;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * VH - lop hook Viet hoa cho "Tien Sung Ky Duyen" (J2ME, CLDC 1.0 / MIDP 2.0).
 * Patch bytecode tinh (patcher/Patch.java) chuyen moi lenh ve/do chu sang day:
 *   Graphics.drawString          -> VH.drawString(Graphics,String,int,int,int)
 *   Font.stringWidth/charWidth/substringWidth/getHeight -> VH.stringWidth/charWidth/substringWidth/getHeight(Font,...)
 *   Main/g.a(char[],...)         (ve thoai)        -> VH.dialog   : ngat theo px o dau cach, N dong/trang, cache theo mang
 *   rf/b.a/b(String,int)         (ngat dong)       -> VH.wrap     : ngat px o dau cach, cache theo noi dung
 *   rf/b.a(G,...) va Main/f.a    (vien 8 huong)    -> VH.outline
 * Font: /font/f12.bin (bo tool NAM.P, BitmapFont: writeUTF(charset), byte h, byte w[n], atlas 1bpp LSB-first).
 * Ky tu khong co trong f12: chu Han (>= U+2E80) = o trong rong 12px (giu bo cuc), ky tu khac = rong dau cach.
 * Chi dung API CLDC 1.0/MIDP 2.0: drawRegion, createRGBImage, fillRect. Khong float, khong reflection.
 */
public final class VH {
    /** Chieu cao dong (tra cho Font.getHeight cua game). */
    public static final int LH = 13;
    /** Do rong 1 chu Han goc (k.w = stringWidth("额") cua game). */
    public static final int SW = 12;
    /** So dong moi trang thoai. */
    static final int PAGE_LINES = 4;

    static int fh;
    static int baseline;
    static int atlasW;
    static byte[] widths;
    static int[] offsets;
    static byte[] bits;
    static char[] sorted;
    static short[] sortedIdx;
    static short[] ascii;
    static int spaceW = 4;
    static int nGlyph;

    /** Run ngang cua tung glyph (ve du phong bang fillRect): (y<<16)|(x<<8)|w */
    static int[] runs;
    static int[] runStart;

    /** Atlas theo mau: so khe co dinh (khong dung lai atlas moi khung hinh). */
    static final int SLOTS = 3;
    static final int[] slotCol = new int[SLOTS];
    static final Image[] slotImg = new Image[SLOTS];
    static int slotN;
    static boolean atlasOk = true;

    static final String TRONG = "Trống";

    static {
        load("/font/f12.bin");
    }

    private VH() {
    }

    static void load(String path) {
        InputStream in = null;
        try {
            in = "".getClass().getResourceAsStream(path);
            DataInputStream d = new DataInputStream(in);
            String cs = d.readUTF();
            int n = cs.length();
            nGlyph = n;
            fh = d.readUnsignedByte();
            widths = new byte[n];
            d.readFully(widths);
            offsets = new int[n];
            int w = 0;
            for (int i = 0; i < n; i++) {
                offsets[i] = w;
                w += widths[i];
            }
            atlasW = w;
            bits = new byte[(atlasW * fh + 7) >> 3];
            d.readFully(bits);
            ascii = new short[128];
            for (int i = 0; i < 128; i++) ascii[i] = -1;
            sorted = new char[n];
            sortedIdx = new short[n];
            for (int i = 0; i < n; i++) {
                char c = cs.charAt(i);
                if (c < 128) ascii[c] = (short) i;
                int k = i;
                while (k > 0 && sorted[k - 1] > c) {
                    sorted[k] = sorted[k - 1];
                    sortedIdx[k] = sortedIdx[k - 1];
                    k--;
                }
                sorted[k] = c;
                sortedIdx[k] = (short) i;
            }
            int sp = idx(' ');
            if (sp >= 0) spaceW = widths[sp];
            buildRuns();
            baseline = findBaseline();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try { in.close(); } catch (Exception e) { }
            }
        }
    }

    static boolean px(int x, int y) {
        int k = y * atlasW + x;
        return (bits[k >> 3] & (1 << (k & 7))) != 0;
    }

    static void buildRuns() {
        int total = 0;
        for (int pass = 0; pass < 2; pass++) {
            int r = 0;
            if (pass == 1) {
                runs = new int[total];
                runStart = new int[nGlyph + 1];
            }
            for (int g = 0; g < nGlyph; g++) {
                if (pass == 1) runStart[g] = r;
                int off = offsets[g], w = widths[g];
                for (int y = 0; y < fh; y++) {
                    int x = 0;
                    while (x < w) {
                        if (!px(off + x, y)) { x++; continue; }
                        int x0 = x;
                        while (x < w && px(off + x, y)) x++;
                        if (pass == 1) runs[r] = (y << 16) | (x0 << 8) | (x - x0);
                        r++;
                    }
                }
            }
            if (pass == 1) runStart[nGlyph] = r;
            total = r;
        }
    }

    static int findBaseline() {
        int i = idx('x');
        if (i < 0) return fh - 1;
        for (int y = fh - 1; y >= 0; y--) {
            for (int x = 0; x < widths[i]; x++) {
                if (px(offsets[i] + x, y)) return y;
            }
        }
        return fh - 1;
    }

    static int idx(char c) {
        if (c < 128) return ascii[c];
        int lo = 0, hi = nGlyph - 1;
        while (lo <= hi) {
            int m = (lo + hi) >> 1;
            char v = sorted[m];
            if (v < c) lo = m + 1;
            else if (v > c) hi = m - 1;
            else return sortedIdx[m];
        }
        return -1;
    }

    // ================================================================ do

    /** Rong ky tu: glyph f12, neu khong co: chu Han/dau cau Han = SW, con lai = dau cach. */
    public static int cw(char c) {
        int i = idx(c);
        if (i >= 0) return widths[i];
        return c >= 0x2E80 ? SW : spaceW;
    }

    /** "空" (nhan o luu rong, giu nguyen trong class/RMS) -> "Trong" khi ve/do. */
    static String map(String s) {
        if (s.length() == 1 && s.charAt(0) == '空') return TRONG;
        return s;
    }

    static int sw(String s) {
        int w = 0;
        for (int i = 0, n = s.length(); i < n; i++) w += cw(s.charAt(i));
        return w;
    }

    public static int charWidth(Font f, char c) {
        return cw(c);
    }

    public static int stringWidth(Font f, String s) {
        return sw(map(s));
    }

    public static int substringWidth(Font f, String s, int off, int len) {
        int w = 0;
        for (int i = off, e = off + len; i < e; i++) w += cw(s.charAt(i));
        return w;
    }

    public static int getHeight(Font f) {
        return LH;
    }

    // ================================================================ ve

    static Image atlas(int rgb) {
        for (int i = 0; i < slotN; i++) {
            if (slotCol[i] == rgb) return slotImg[i];
        }
        if (!atlasOk || slotN >= SLOTS) return null;
        Image img;
        try {
            int[] p = new int[atlasW * fh];
            int argb = 0xFF000000 | rgb;
            int k = 0;
            for (int y = 0; y < fh; y++) {
                for (int x = 0; x < atlasW; x++, k++) {
                    if ((bits[k >> 3] & (1 << (k & 7))) != 0) p[k] = argb;
                }
            }
            img = Image.createRGBImage(p, atlasW, fh, true);
        } catch (Throwable t) {
            atlasOk = false;
            return null;
        }
        slotCol[slotN] = rgb;
        slotImg[slotN] = img;
        slotN++;
        return img;
    }

    static void glyph(Graphics g, Image img, int i, int x, int y) {
        if (img != null) {
            g.drawRegion(img, offsets[i], 0, widths[i], fh, 0, x, y, Graphics.TOP | Graphics.LEFT);
        } else {
            for (int r = runStart[i], e = runStart[i + 1]; r < e; r++) {
                int v = runs[r];
                g.fillRect(x + ((v >> 8) & 0xFF), y + (v >> 16), v & 0xFF, 1);
            }
        }
    }

    static int alignY(int y, int anchor) {
        if ((anchor & Graphics.BOTTOM) != 0) return y - LH;
        if ((anchor & Graphics.BASELINE) != 0) return y - baseline;
        if ((anchor & Graphics.VCENTER) != 0) return y - (LH >> 1);
        return y;
    }

    /** Ve s[off, off+len): da canh theo anchor, bo qua phan nam ngoai clip. */
    static void drawRange(Graphics g, String s, int off, int len, int x, int y, int anchor) {
        if (len <= 0) return;
        if ((anchor & (Graphics.HCENTER | Graphics.RIGHT)) != 0) {
            int w = substringWidth(null, s, off, len);
            if ((anchor & Graphics.RIGHT) != 0) x -= w;
            else x -= w >> 1;
        }
        y = alignY(y, anchor);
        int cx = g.getClipX(), cy = g.getClipY();
        if (y + fh <= cy || y >= cy + g.getClipHeight()) return;
        int right = cx + g.getClipWidth();
        Image img = null;
        boolean have = false;
        for (int k = off, e = off + len; k < e; k++) {
            if (x >= right) break;
            char c = s.charAt(k);
            int i = idx(c);
            if (i < 0) {
                x += c >= 0x2E80 ? SW : spaceW;
                continue;
            }
            int w = widths[i];
            if (x + w > cx) {
                if (!have) {
                    img = atlas(g.getColor() & 0xFFFFFF);
                    have = true;
                }
                glyph(g, img, i, x, y);
            }
            x += w;
        }
    }

    /** Thay Graphics.drawString(String,int,int,int). */
    public static void drawString(Graphics g, String s, int x, int y, int anchor) {
        if (s == null) return;
        s = map(s);
        drawRange(g, s, 0, s.length(), x, y, anchor);
    }

    /** Thay rf.b.a(G,String,x,y,c1,c2,anchor) va Main.f.a(...): vien 8 huong mau c1, than mau c2 (c1==c2: 1 lan). */
    public static void outline(Graphics g, String s, int x, int y, int c1, int c2, int anchor) {
        if (s == null) return;
        s = map(s);
        int n = s.length();
        if (c1 == c2) {
            g.setColor(c1);
            drawRange(g, s, 0, n, x, y, anchor);
            return;
        }
        g.setColor(c1);
        drawRange(g, s, 0, n, x + 1, y, anchor);
        drawRange(g, s, 0, n, x - 1, y, anchor);
        drawRange(g, s, 0, n, x, y + 1, anchor);
        drawRange(g, s, 0, n, x, y - 1, anchor);
        drawRange(g, s, 0, n, x + 1, y + 1, anchor);
        drawRange(g, s, 0, n, x - 1, y - 1, anchor);
        drawRange(g, s, 0, n, x + 1, y - 1, anchor);
        drawRange(g, s, 0, n, x - 1, y + 1, anchor);
        g.setColor(c2);
        drawRange(g, s, 0, n, x, y, anchor);
    }

    // ================================================================ ngat dong cho rf.b.a/b (String[] + so dong)

    static final String[][] wDest = new String[2][];
    static final String[] wKey = new String[2];
    static final int[] wW = new int[2];
    static final int[] wCount = new int[2];

    /**
     * Thay than rf.b.a(String,int) / rf.b.b(String,int): ngat theo px o dau cach, '^' = xuong dong cung.
     * Ghi vao dest[0..count) va tra ve count (game gan vao rf.b.e/f). Cache theo noi dung: dest van giu ket qua cu nen
     * khi chuoi + do rong khong doi thi khong lam gi (khong tao String moi moi khung hinh).
     */
    public static int wrap(String s, int maxW, String[] dest) {
        int slot = dest == wDest[0] ? 0 : dest == wDest[1] ? 1 : wDest[0] == null ? 0 : 1;
        if (wDest[slot] == dest && wW[slot] == maxW && wKey[slot] != null && wKey[slot].equals(s)) return wCount[slot];
        int old = wDest[slot] == dest ? wCount[slot] : dest.length;
        int cnt = 0;
        int n = s.length();
        int cap = dest.length;
        int start = 0;
        if (maxW < SW) maxW = SW;
        while (start < n && cnt < cap) {
            int end = s.indexOf('^', start);
            boolean hard = end >= 0;
            if (!hard) end = n;
            int ls = start;
            if (ls == end && cnt < cap) {
                dest[cnt++] = "";
            }
            while (ls < end && cnt < cap) {
                int w = 0, k = ls, lastSp = -1;
                while (k < end) {
                    char c = s.charAt(k);
                    int cwid = cw(c);
                    if (w + cwid > maxW && k > ls) break;
                    if (c == ' ') lastSp = k;
                    w += cwid;
                    k++;
                }
                int le;
                if (k >= end) le = end;
                else if (s.charAt(k) == ' ') le = k;
                else if (lastSp > ls) le = lastSp;
                else le = k;
                int e2 = le;
                while (e2 > ls && s.charAt(e2 - 1) == ' ') e2--;
                dest[cnt++] = s.substring(ls, e2);
                ls = le;
                while (ls < end && s.charAt(ls) == ' ') ls++;
            }
            start = end + 1;
        }
        for (int i = cnt; i < old && i < cap; i++) dest[i] = null;
        wDest[slot] = dest;
        wKey[slot] = s;
        wW[slot] = maxW;
        wCount[slot] = cnt;
        return cnt;
    }

    // ================================================================ thoai (Main/g.a)

    static char[] dArr;
    static int dLen, dW;
    static int[] dLs = new int[64];
    static int dN;

    static int bw(char c) {
        return (c == '[' || c == ']') ? 0 : cw(c);
    }

    /** Tinh danh sach dong cua a[] voi do rong w (ngat o dau cach, '[' ']' khong ve va khong do). */
    static void layout(char[] a, int w) {
        int n = a.length;
        if (dLs.length < n + 2) dLs = new int[n + 2];
        int maxW = w - 5;
        if (maxW < SW) maxW = SW;
        int cnt = 0, ls = 0;
        while (ls < n) {
            dLs[cnt++] = ls;
            int wd = 0, k = ls, lastSp = -1;
            while (k < n) {
                char c = a[k];
                int cwid = bw(c);
                if (wd + cwid > maxW && k > ls) break;
                if (c == ' ') lastSp = k;
                wd += cwid;
                k++;
            }
            int next;
            if (k >= n) next = n;
            else if (a[k] == ' ') next = k;
            else if (lastSp > ls) next = lastSp;
            else next = k;
            ls = next;
            while (ls < n && a[ls] == ' ') ls++;
        }
        dLs[cnt] = n;
        dN = cnt;
        dArr = a;
        dLen = n;
        dW = w;
    }

    /**
     * Thay Main/g.a(char[] a, Graphics g, int w, int x, int y, int color, int start): ve 1 trang (PAGE_LINES dong) bat dau
     * tai chi so 'start' va tra ve chi so ky tu cuoi cung cua trang (game dat start moi = tra ve + 1; >= length = het).
     */
    public static int dialog(char[] a, Graphics g, int w, int x, int y, int color, int start) {
        int n = a.length;
        if (n == 0 || start >= n) return 0;
        if (a != dArr || n != dLen || w != dW) layout(a, w);
        int k = 0;
        while (k + 1 < dN && dLs[k + 1] <= start) k++;
        int first = k - k % PAGE_LINES;
        int end = first + PAGE_LINES;
        if (end > dN) end = dN;
        g.setColor(color);
        Image img = atlas(color & 0xFFFFFF);
        int cx = g.getClipX(), cy = g.getClipY();
        int right = cx + g.getClipWidth(), bottom = cy + g.getClipHeight();
        for (int li = first; li < end; li++) {
            int yy = y + (li - first) * LH;
            int xx = x;
            int le = dLs[li + 1];
            if (yy + fh <= cy || yy >= bottom) continue;
            for (int c = dLs[li]; c < le; c++) {
                char ch = a[c];
                if (ch == '[' || ch == ']') continue;
                if (xx >= right) break;
                int i = idx(ch);
                if (i < 0) {
                    xx += ch >= 0x2E80 ? SW : spaceW;
                    continue;
                }
                if (xx + widths[i] > cx) glyph(g, img, i, xx, yy);
                xx += widths[i];
            }
        }
        return dLs[end] - 1;
    }

    // ================================================================ khoi dong

    /** Logo NAMP khoi dong (Main/f.c() sau patch). */
    public static Image logo() {
        try {
            return Image.createImage("/Image/namp.png");
        } catch (Exception e) {
            return null;
        }
    }
}
