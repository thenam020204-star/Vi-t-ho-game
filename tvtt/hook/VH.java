package src;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;

/**
 * VH - lop hook Viet hoa cho "Tinh Van Truyen Thuyet" (J2ME, CLDC 1.1 / MIDP 2.0).
 * Nam trong package src cung cac class game. patcher/Patch.java thay THAN cac method chu cua src/b
 * (giu nguyen ten/descriptor/access flag) bang loi goi sang day; field rieng cua b truyen qua tham so.
 *   b.a(String,int,int,int,byte[])I      ve gradient + ma mau $n          -> drawG
 *   b.a(String,int,int,int,byte[],int)V  ve mot mau, anchor               -> drawC
 *   b.a(String[],int*6)V                 nhieu dong gradient              -> lines
 *   b.a(String[],int*6,byte)V            thoai chay chu (gioi han byte)   -> linesTW
 *   b.b(String[],int*6)V                 nhieu dong can giua + mo dan     -> linesFade
 *   b.a(String)I / b.c(String)I          do rong (bo qua $n / khong bo)   -> width / widthRaw
 *   b.b(String)I (static)                so don vi chay chu cua 1 dong    -> twUnits
 *   b.a(String,int,int)[String           ngat dong                        -> wrap (theo px, o dau cach)
 *   Graphics.drawString trong b          font he thong                    -> drawString
 * Font: /font/f12.bin (bo tool NAM.P: writeUTF(charset), byte h, byte w[n], atlas 1bpp LSB-first).
 * Ve tung glyph bang drawRGB vao bo dem dung lai (nhu renderer goc) de giu gradient tung hang.
 * Chu Han con sot = o trong rong 12px; dau cau Han -> ASCII. Khong float, khong reflection.
 */
public final class VH {
    /** Chieu cao o chu (= hzk12 goc). */
    static final int FH = 12;
    /** So ky tu cho moi don vi bo dem chay chu (bo dem cua game la byte). */
    static final int TW = 3;

    static int fh;
    static int atlasW;
    static byte[] widths;
    static int[] offsets;
    static byte[] bits;
    static char[] sorted;
    static short[] sortedIdx;
    static short[] ascii;
    static int spaceW = 3;
    static int maxW;
    static int[] buf;

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
            fh = d.readUnsignedByte();
            widths = new byte[n];
            d.readFully(widths);
            offsets = new int[n];
            int w = 0;
            for (int i = 0; i < n; i++) {
                offsets[i] = w;
                w += widths[i];
                if (widths[i] > maxW) maxW = widths[i];
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
            if (maxW < 12) maxW = 12;
            buf = new int[maxW * FH];
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try { in.close(); } catch (Exception e) { }
            }
        }
    }

    /** Dau cau Han/ky tu ngoai font -> ky tu co trong f12. */
    static char map(char c) {
        if (c < 128) return c;
        switch (c) {
            case '：': return ':';
            case '，': case '、': return ',';
            case '。': return '.';
            case '！': return '!';
            case '？': return '?';
            case '（': return '(';
            case '）': return ')';
            case '％': return '%';
            case '／': return '/';
            case '；': return ';';
            case '“': case '”': case '《': case '》': return '"';
            case '‘': case '’': return '\'';
            case '～': return '~';
            case '—': case '–': return '-';
            case '　': return ' ';
            default: return c;
        }
    }

    static int idx(char c) {
        if (c < 128) return ascii[c];
        int lo = 0, hi = sorted.length - 1;
        while (lo <= hi) {
            int m = (lo + hi) >> 1;
            char v = sorted[m];
            if (v == c) return sortedIdx[m];
            if (v < c) lo = m + 1; else hi = m - 1;
        }
        return -1;
    }

    /** Do rong mot ky tu (sau map). Chu Han/ky tu la >= U+2E80 = 12px (giu bo cuc), con lai = dau cach. */
    static int adv(char c) {
        c = map(c);
        int i = idx(c);
        if (i >= 0) return widths[i];
        if (c == '…') return 3 * adv('.');
        return c >= '⺀' ? 12 : spaceW;
    }

    static boolean px(int x, int y) {
        int k = y * atlasW + x;
        return (bits[k >> 3] & (1 << (k & 7))) != 0;
    }

    /** Ve mot ky tu; argb[r] = mau hang r (gradient) hoac argb == null dung solid. Tra ve do rong. */
    static int glyph(Graphics g, char c, int x, int y, int[] grad, int alpha, int solid) {
        c = map(c);
        if (c == '…') {
            int w = 0;
            for (int k = 0; k < 3; k++) w += glyph(g, '.', x + w, y, grad, alpha, solid);
            return w;
        }
        int i = idx(c);
        if (i < 0) return c >= '⺀' ? 12 : spaceW;
        int w = widths[i];
        if (c == ' ' || w <= 0) return w;
        int off = offsets[i];
        int[] b = buf;
        int rows = fh < FH ? fh : FH;
        boolean any = false;
        for (int r = 0; r < FH; r++) {
            int col = grad != null ? (alpha | grad[r]) : solid;
            int p = r * w;
            for (int q = 0; q < w; q++) {
                boolean on = r < rows && px(off + q, r);
                b[p + q] = on ? col : 0;
                if (on) any = true;
            }
        }
        if (any) g.drawRGB(b, 0, w, x, y, w, FH, true);
        return w;
    }

    /** Gradient 16 muc tu mau n3 (dinh) toi n2 (day) - y het b.a(int,int) goc. */
    static void setGrad(int[] grad, int n2, int n3) {
        for (int i = 0; i < grad.length; i++) {
            int r = i * ((n2 & 0xFF0000) - (n3 & 0xFF0000)) / 15 + n3 & 0xFF0000;
            int gg = i * ((n2 & 0xFF00) - (n3 & 0xFF00)) / 15 + n3 & 0xFF00;
            int bb = i * ((n2 & 0xFF) - (n3 & 0xFF)) / 15 + n3 & 0xFF;
            grad[i] = r | gg | bb;
        }
    }

    static int digit(char c) {
        return (c >= '0' && c <= '9') ? c - '0' : -1;
    }

    // ------------------------------------------------------------------ do rong

    /** b.a(String): bo qua "$n". */
    public static int width(String s) {
        int w = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '$') { i++; continue; }
            w += adv(c);
        }
        return w;
    }

    /** b.c(String): tinh ca '$' (nhu goc). */
    public static int widthRaw(String s) {
        int w = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) w += adv(s.charAt(i));
        return w;
    }

    /** b.b(String): so don vi chay chu (TW ky tu hien thi / don vi, lam tron len). */
    public static int twUnits(String s) {
        int k = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '$') { i++; continue; }
            k++;
        }
        return (k + TW - 1) / TW;
    }

    // ------------------------------------------------------------------ ve

    /** b.a(String,x,y,anchor,byte[]): gradient, "$n" doi mau. anchor 17 = giua, 24 = phai. */
    public static int drawG(Graphics g, int[] grad, int[][] ctab, String s, int x, int y, int anchor) {
        if (anchor == 17) x -= width(s) >> 1;
        else if (anchor == 24) x -= width(s);
        int w = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '$') {
                if (i + 1 < n) {
                    int d = digit(s.charAt(++i));
                    if (d >= 0) setGrad(grad, ctab[0][d], ctab[1][d]);
                }
                continue;
            }
            int a = glyph(g, c, x, y, grad, 0xFF000000, 0);
            x += a;
            w += a;
        }
        return w;
    }

    /** b.a(String,x,y,anchor,byte[],color): mot mau; anchor 8 phai, 1 giua, 32 day, 2 giua doc. */
    public static void drawC(Graphics g, String s, int x, int y, int anchor, int color) {
        if ((anchor & 8) == 8) x -= widthRaw(s);
        else if ((anchor & 1) == 1) x -= widthRaw(s) >> 1;
        if ((anchor & 0x20) == 32) y -= FH;
        else if ((anchor & 2) == 2) y -= FH >> 1;
        int argb = 0xFF000000 | color;
        int n = s.length();
        for (int i = 0; i < n; i++) x += glyph(g, s.charAt(i), x, y, null, 0, argb);
    }

    /** Graphics.drawString(s,x,y,anchor) -> font f12 voi mau hien tai cua g. */
    public static void drawString(Graphics g, String s, int x, int y, int anchor) {
        int w = widthRaw(s);
        if ((anchor & Graphics.RIGHT) != 0) x -= w;
        else if ((anchor & Graphics.HCENTER) != 0) x -= w >> 1;
        if ((anchor & Graphics.BOTTOM) != 0) y -= FH;
        else if ((anchor & Graphics.BASELINE) != 0) y -= FH - 2;
        int argb = 0xFF000000 | g.getColor();
        int n = s.length();
        for (int i = 0; i < n; i++) x += glyph(g, s.charAt(i), x, y, null, 0, argb);
    }

    /** b.a(String[],x,y,anchor,start,count,color): moi dong drawG, cach dong 16px. */
    public static void lines(Graphics g, int[] grad, int[][] ctab, String[] ss, int x, int y, int anchor, int start, int count) {
        for (int i = 0; i < count; i++) {
            int k = i + start;
            if (k < 0 || k >= ss.length) continue;
            drawG(g, grad, ctab, ss[k], x, y + (i << 4), anchor);
        }
    }

    /** b.a(String[],x,y,anchor,start,count,color,byte lim): chay chu, lim tinh theo don vi TW ky tu. */
    public static void linesTW(Graphics g, int[] grad, int[][] ctab, String[] ss, int x0, int y, int start, int count, int lim) {
        int budget = lim * TW;
        int shown = 0;
        for (int i = 0; i < count; i++) {
            int k = i + start;
            if (k < 0 || k >= ss.length) continue;
            String s = ss[k];
            int x = x0;
            int n = s.length();
            for (int j = 0; j < n && shown < budget; j++) {
                char c = s.charAt(j);
                if (c == '$') {
                    if (j + 1 < n) {
                        int d = digit(s.charAt(++j));
                        if (d >= 0) setGrad(grad, ctab[0][d], ctab[1][d]);
                    }
                    continue;
                }
                x += glyph(g, c, x, y + (i << 4), grad, 0xFF000000, 0);
                shown++;
            }
        }
    }

    /** b.b(String[],x,y,anchor,start,count,fade): can giua quanh cx, dong cuoi alpha = fade*0x11. */
    public static void linesFade(Graphics g, int[] grad, int[][] ctab, String[] ss, int cx, int y, int start, int count, int fade) {
        for (int i = 0; i < count; i++) {
            int k = i + start;
            if (k < 0 || k >= ss.length) continue;
            String s = ss[k];
            int x = cx - (width(s) >> 1);
            int alpha = 0xFF000000;
            if (i == count - 1 && fade >= 0) alpha = fade * 0x11000000;
            int n = s.length();
            for (int j = 0; j < n; j++) {
                char c = s.charAt(j);
                if (c == '$') {
                    if (j + 1 < n) {
                        int d = digit(s.charAt(++j));
                        if (d >= 0) setGrad(grad, ctab[0][d], ctab[1][d]);
                    }
                    continue;
                }
                x += glyph(g, c, x, y + (i << 4), grad, alpha, 0);
            }
        }
    }

    // ------------------------------------------------------------------ ngat dong

    /**
     * b.a(String,maxW,_): ngat theo px o dau cach ('|' = xuong dong bat buoc, "$n" rong 0).
     * Moi dong bat dau bang "$<mau>" (mau dang dung; dau tien '0') nhu ban goc. Goi khi doi thoai, khong goi trong paint.
     */
    public static String[] wrap(String s, int maxW) {
        Vector out = new Vector();
        char[] color = new char[] { '0' };
        int n = s.length();
        int ls = 0;
        int sp = -1;
        int w = 0;
        int i = 0;
        while (i < n) {
            char c = s.charAt(i);
            if (c == '|') {
                emit(out, s.substring(ls, i), color);
                ls = i + 1;
                sp = -1;
                w = 0;
                i++;
                continue;
            }
            if (c == '$') {
                i += 2;
                continue;
            }
            if (c == ' ' && i > ls) sp = i;
            w += adv(c);
            if (w > maxW && i > ls) {
                int cut;
                int next;
                if (sp > ls) {
                    cut = sp;
                    next = sp + 1;
                } else {
                    cut = i;
                    next = i;
                }
                emit(out, s.substring(ls, cut), color);
                ls = next;
                while (ls < n && s.charAt(ls) == ' ') ls++;
                sp = -1;
                w = 0;
                i = ls;
                continue;
            }
            i++;
        }
        if (ls < n) emit(out, s.substring(ls), color);
        String[] r = new String[out.size()];
        out.copyInto(r);
        return r;
    }

    private static void emit(Vector out, String line, char[] color) {
        if (line.length() == 0 || line.charAt(0) != '$') {
            StringBuffer b = new StringBuffer(line.length() + 2);
            b.append('$');
            b.append(color[0]);
            b.append(line);
            line = b.toString();
        }
        int p = line.lastIndexOf('$');
        if (p >= 0 && p + 1 < line.length()) color[0] = line.charAt(p + 1);
        out.addElement(line);
    }

    // ------------------------------------------------------------------ khoi dong NAMP

    static javax.microedition.lcdui.Image logo;

    /**
     * Thay than q.run() (splash: logo nhom upload + hoi am thanh + logo nha phat hanh):
     * hien /logo/namp.png ~2 giay roi dong Player nhac logo. Than run() moi (Patch) sau do dat am luong = 4
     * (nhu chon "Co") va goi GameMidlet.startGame(1).
     */
    public static void boot(javax.microedition.lcdui.Canvas c, javax.microedition.media.Player p) {
        try {
            logo = javax.microedition.lcdui.Image.createImage("/logo/namp.png");
        } catch (Exception e) {
            logo = null;
        }
        long t0 = System.currentTimeMillis();
        try {
            while (System.currentTimeMillis() - t0 < 2000) {
                c.repaint();
                c.serviceRepaints();
                Thread.sleep(50);
            }
        } catch (Exception e) {
        }
        if (p != null) {
            try { p.close(); } catch (Exception e) { }
        }
        logo = null;
        System.gc();
    }

    /** Thay than q.paint(Graphics): nen den + logo NAMP o giua. */
    public static void bootPaint(Graphics g, javax.microedition.lcdui.Canvas c) {
        int w = c.getWidth(), h = c.getHeight();
        g.setClip(0, 0, w, h);
        g.setColor(0);
        g.fillRect(0, 0, w, h);
        if (logo != null) g.drawImage(logo, w >> 1, h >> 1, Graphics.HCENTER | Graphics.VCENTER);
    }
}
