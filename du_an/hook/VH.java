import java.io.DataInputStream;
import java.io.InputStream;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * VH - lop hook Viet hoa cho "Di The Ta Quan" (RPG22).
 * Moi lenh ve/do chu cua c.class (CGame) duoc chuyen sang day bang patch bytecode tinh:
 *   Graphics.drawString/drawChar  -> VH.drawString/drawChar  (cung kieu stack)
 *   Font.charWidth/stringWidth    -> VH.cw/sw
 *   DrawLineString / CalcTextPos / CalcTextPage (ngat theo so ky tu) -> VH.dl / VH.calcPos / VH.calcPage
 *     (ngat dong theo px, o dau cach)
 *   Ham chu chay hoi thoai: do rong "ky tu ke tiep" -> VH.nw (do ca tu, xuong dong truoc tu khong vua)
 * Font: /font/f12.bin cua bo tool NAM.P (dinh dang BitmapFont: writeUTF(charset), byte h, byte w[n], bit LSB-first).
 * CLDC 1.0 / MIDP 2.0: chi dung drawRegion, createRGBImage, fillRect. Khong float, khong reflection.
 */
public final class VH {
    /** Chieu cao dong (= i.R / FONT_HEIGHT cua game sau patch). */
    public static final int LH = 13;
    /** Do rong "1 chu" goc (= i.S / FONT_WIDTH), dung doi so ky tu -> px. */
    public static final int SW = 12;
    /** Do rong icon "/i" trong hoi thoai. */
    static final int ICON_W = 16;

    static int fh;              // chieu cao glyph
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

    /** Run ngang cua tung glyph (ve du phong bang fillRect, khong cap phat). (y<<16)|(x<<8)|w */
    static int[] runs;
    static int[] runStart;

    /** Atlas theo mau: so khe co dinh, khong xoay vong (tranh dung lai atlas moi khung hinh). */
    static final int SLOTS = 3;
    static final int[] slotCol = new int[SLOTS];
    static final Image[] slotImg = new Image[SLOTS];
    static int slotN;
    static boolean atlasOk = true;

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

    public static int charWidth(char c) {
        int i = idx(c);
        return i >= 0 ? widths[i] : spaceW;
    }

    public static int stringWidth(String s) {
        if (s == null) return 0;
        int w = 0;
        for (int i = 0, n = s.length(); i < n; i++) w += charWidth(s.charAt(i));
        return w;
    }

    public static int substringWidth(String s, int off, int len) {
        int w = 0;
        for (int i = off, e = off + len; i < e; i++) w += charWidth(s.charAt(i));
        return w;
    }

    /** Thay Font.charWidth(C) (stack: Font, char -> int). */
    public static int cw(Font f, char c) {
        return charWidth(c);
    }

    /** Thay Font.stringWidth(String). */
    public static int sw(Font f, String s) {
        return stringWidth(s);
    }

    /** Thay Q.stringWidth("宽") trong i.<clinit>: FONT_WIDTH giu 12 nhu o chu Han goc. */
    public static int sw0(Font f, String s) {
        return SW;
    }

    /** Thay Font.getHeight(). */
    public static int fh(Font f) {
        return LH;
    }

    /** Do rong khung ten: tra ve so "o chu goc" (game nhan voi i.S roi cong le). */
    public static int nlen(String s) {
        if (s == null || s.length() == 0) return 0;
        return (stringWidth(s) + SW - 1) / SW;
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

    static int glyph(Graphics g, Image img, int i, int x, int y) {
        if (img != null) {
            g.drawRegion(img, offsets[i], 0, widths[i], fh, 0, x, y, Graphics.TOP | Graphics.LEFT);
        } else {
            for (int r = runStart[i], e = runStart[i + 1]; r < e; r++) {
                int v = runs[r];
                g.fillRect(x + ((v >> 8) & 0xFF), y + (v >> 16), v & 0xFF, 1);
            }
        }
        return widths[i];
    }

    static int alignY(int y, int anchor) {
        if ((anchor & Graphics.BOTTOM) != 0) return y - LH;
        if ((anchor & Graphics.BASELINE) != 0) return y - baseline;
        if ((anchor & Graphics.VCENTER) != 0) return y - (LH >> 1);
        return y;
    }

    static void drawRange(Graphics g, String s, int off, int len, int x, int y, int anchor) {
        if (s == null || len <= 0) return;
        if ((anchor & (Graphics.HCENTER | Graphics.RIGHT)) != 0) {
            int w = substringWidth(s, off, len);
            if ((anchor & Graphics.RIGHT) != 0) x -= w;
            else x -= w >> 1;
        }
        y = alignY(y, anchor);
        Image img = atlas(g.getColor() & 0xFFFFFF);
        for (int k = off, e = off + len; k < e; k++) {
            int i = idx(s.charAt(k));
            if (i < 0) {
                x += spaceW;
                continue;
            }
            x += glyph(g, img, i, x, y);
        }
    }

    /** Thay Graphics.drawString(String,int,int,int). */
    public static void drawString(Graphics g, String s, int x, int y, int anchor) {
        if (s == null) return;
        drawRange(g, s, 0, s.length(), x, y, anchor);
    }

    /** Thay Graphics.drawSubstring(String,int,int,int,int,int). */
    public static void drawSubstring(Graphics g, String s, int off, int len, int x, int y, int anchor) {
        drawRange(g, s, off, len, x, y, anchor);
    }

    /** Thay Graphics.drawChar(char,int,int,int). */
    public static void drawChar(Graphics g, char c, int x, int y, int anchor) {
        int i = idx(c);
        if (i < 0) return;
        int w = widths[i];
        if ((anchor & Graphics.RIGHT) != 0) x -= w;
        else if ((anchor & Graphics.HCENTER) != 0) x -= w >> 1;
        glyph(g, atlas(g.getColor() & 0xFFFFFF), i, x, alignY(y, anchor));
    }

    // ================================================================ ngat dong (px)

    /** Cache ket qua ngat dong theo (doi tuong chuoi, maxW): chuoi game lay tu bang co dinh. */
    static final int CACHE = 8;
    static final String[] cKey = new String[CACHE];
    static final int[] cW = new int[CACHE];
    static final int[][] cLines = new int[CACHE][];
    static int cPos;

    /** Tra ve mang [n, off0, len0, off1, len1, ...] (khong cap phat lai neu da co trong cache). */
    static int[] lines(String s, int maxW) {
        for (int i = 0; i < CACHE; i++) {
            if (cKey[i] == s && cW[i] == maxW) return cLines[i];
        }
        int[] r = wrap(s, maxW);
        cKey[cPos] = s;
        cW[cPos] = maxW;
        cLines[cPos] = r;
        cPos = (cPos + 1) % CACHE;
        return r;
    }

    static int[] wrap(String s, int maxW) {
        int n = s.length();
        int[] out = new int[1 + 2 * (n + 1)];
        int cnt = 0;
        int start = 0;
        if (maxW < SW) maxW = SW;
        while (true) {
            int end = s.indexOf('\n', start);
            if (end < 0) end = n;
            if (start == end) {
                out[1 + 2 * cnt] = start;
                out[2 + 2 * cnt] = 0;
                cnt++;
            }
            int ls = start;
            while (ls < end) {
                int w = 0, k = ls, lastSp = -1;
                while (k < end) {
                    char c = s.charAt(k);
                    int cwid = charWidth(c);
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
                int len = le - ls;
                while (len > 0 && s.charAt(ls + len - 1) == ' ') len--;
                out[1 + 2 * cnt] = ls;
                out[2 + 2 * cnt] = len;
                cnt++;
                ls = le;
                while (ls < end && s.charAt(ls) == ' ') ls++;
            }
            if (end >= n) break;
            start = end + 1;
        }
        out[0] = cnt;
        return out;
    }

    static int nlines(String s, int maxW) {
        if (s == null || s.length() == 0) return 0;
        return lines(s, maxW)[0];
    }

    /**
     * Thay DrawLineString(g, str, x, y, anchor, color0, color1, num, offSet, line):
     * num = so chu Han/dong -> maxW = num*SW px; offSet (theo ky tu) -> dong bat dau = offSet/num.
     */
    public static void dl(Graphics g, String s, int x, int y, int anchor, int c0, int c1, int num, int off, int line) {
        if (s == null || s.length() == 0 || num <= 0) return;
        int[] L = lines(s, num * SW);
        int n = L[0];
        int first = off / num;
        int count = line > 0 ? line : n;
        for (int i = 0; i < count; i++) {
            int li = first + i;
            if (li >= n) break;
            int lo = L[1 + 2 * li], ll = L[2 + 2 * li];
            int yy = y + i * LH;
            if (c0 != c1) {
                g.setColor(c1);
                drawRange(g, s, lo, ll, x - 1, yy, anchor);
                drawRange(g, s, lo, ll, x + 1, yy, anchor);
                drawRange(g, s, lo, ll, x, yy - 1, anchor);
                drawRange(g, s, lo, ll, x, yy + 1, anchor);
            }
            g.setColor(c0);
            drawRange(g, s, lo, ll, x, yy, anchor);
        }
    }

    /** Thay CalcTextPos(str, w, dir): 0 = do rong cuon ngang, 1 = chieu cao cuon doc. */
    public static int calcPos(String s, int w, int dir) {
        if (dir == 0) return stringWidth(s);
        if (dir == 1) return nlines(s, (w / SW) * SW) * LH;
        return 0;
    }

    /** Thay CalcTextPage(str, w, h): so trang. */
    public static int calcPage(String s, int w, int h) {
        int per = h / LH;
        if (per <= 0) per = 1;
        int n = nlines(s, (w / SW) * SW);
        return n / per + (n % per == 0 ? 0 : 1);
    }

    // ================================================================ menu chinh

    /** Muc menu chinh gan nhat (de biet huong khi nhay qua muc da go). */
    static int lastMain;

    /**
     * Goi dau UpdateMainMenu (c.J) va RenderMainMenu (c.G).
     * Muc 2 (游戏圈/Game Center), 3 (精品推荐), 4 (更多游戏) la dich vu mang cua nha mang -> nhay qua.
     * Muc chinh: 0 Moi, 1 Tiep, 5 Cai dat, 6 Huong dan, 7 Gioi thieu, 8 Thoat.
     */
    public static void mainMenu(h w) {
        int[] s = c.m;
        int i = w.g;
        int v = s[i];
        if (v >= 2 && v <= 4) {
            v = lastMain >= 5 ? 1 : 5;
            s[i] = v;
        }
        lastMain = v;
        if (w.w >= 2 && w.w <= 4) w.w = -1;
    }

    // ================================================================ chu chay hoi thoai

    static boolean colorMark(char m) {
        return m == 'b' || m == 'r' || m == 'w';
    }

    /**
     * Thay "font.charWidth(eo[ep])" o buoc kiem tra xuong dong cua ham chu chay.
     * Dau tu: tra ve do rong ca tu -> game xuong dong truoc tu neu khong vua.
     * Giua tu: do rong ky tu (tu dai hon dong thi van cat nhu cu). Dau cach/ma lenh: 0.
     */
    public static int nw(Font f, char[] a, int i) {
        int n = a.length;
        int j = i;
        while (j + 1 < n && a[j] == '/' && colorMark(a[j + 1])) j += 2;
        if (j >= n) return 0;
        char c = a[j];
        if (c == ' ' || c == '/') return 0;
        int p = i - 1;
        while (p >= 1 && a[p - 1] == '/' && colorMark(a[p])) p -= 2;
        boolean wordStart = p < 0 || a[p] == ' ' || (p >= 1 && a[p - 1] == '/');
        if (!wordStart) return charWidth(c);
        int w = 0;
        for (int k = j; k < n; k++) {
            char d = a[k];
            if (d == ' ') break;
            if (d == '/' && k + 1 < n) {
                if (colorMark(a[k + 1])) {
                    k++;
                    continue;
                }
                break;
            }
            w += charWidth(d);
        }
        return w;
    }
}
