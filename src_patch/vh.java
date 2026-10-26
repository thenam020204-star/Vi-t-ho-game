/*
 * vh = lop moi cho ban Viet hoa: ngat dong theo do rong that (bs), cache ket qua ngat dong,
 * ve khung thoai theo tu, ve chu he thong qua font bitmap. Chi dung API CLDC 1.0 / MIDP.
 * Cac method goc (z, cb, n) duoc sua bytecode de goi sang day, giu nguyen descriptor.
 */
import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public final class vh {
    /* ket qua tra ve cho n.a(Graphics,short[],int,int[],int): ez, ex moi */
    public static int z;
    public static int x;

    private static final int CN = 8;
    private static final String[] cS = new String[CN];
    private static final int[] cW = new int[CN];
    private static final char[] cC = new char[CN];
    private static final String[][] cL = new String[CN][];
    private static int cP;

    private static final String[] EMPTY = new String[0];

    /* do rong 1 ky tu theo renderer dang dung */
    private static int cw(char ch) {
        return bs.w(ch);
    }

    /* Ngat dong: tai dau cach (bo dau cach o dau dong moi), truoc chu Han, cat tu dai hon maxW;
       nl = ky tu xuong dong cuong buc (0 = khong co). */
    public static String[] wrap(String s, int maxW, char nl) {
        if (s == null) {
            return EMPTY;
        }
        for (int i = 0; i < CN; ++i) {
            if (cW[i] == maxW && cC[i] == nl && (cS[i] == s || s.equals(cS[i]))) {
                return cL[i];
            }
        }
        String[] res = wrap0(s, maxW, nl);
        cS[cP] = s;
        cW[cP] = maxW;
        cC[cP] = nl;
        cL[cP] = res;
        cP = (cP + 1) % CN;
        return res;
    }

    private static String[] wrap0(String s, int maxW, char nl) {
        Vector v = new Vector(4, 4);
        int n = s.length();
        int start = 0;
        int w = 0;
        int brk = -1;
        int nxt = -1;
        int i = 0;
        boolean soft = false;
        while (i < n) {
            char c = s.charAt(i);
            if (nl != 0 && c == nl) {
                v.addElement(s.substring(start, i));
                start = ++i;
                w = 0;
                brk = -1;
                soft = false;
                continue;
            }
            if (soft && i == start && c == ' ') {
                start = ++i;
                continue;
            }
            int k = cw(c);
            if (w + k > maxW && i > start) {
                int b;
                int nx;
                if (brk > start) {
                    b = brk;
                    nx = nxt;
                } else {
                    b = i;
                    nx = i;
                }
                v.addElement(s.substring(start, b));
                start = nx;
                i = nx;
                w = 0;
                brk = -1;
                soft = true;
                continue;
            }
            if (c == ' ') {
                brk = i;
                nxt = i + 1;
            } else if (c >= '⺀' && i > start) {
                brk = i;
                nxt = i;
            }
            w += k;
            ++i;
        }
        if (start < n) {
            v.addElement(s.substring(start, n));
        }
        String[] out = new String[v.size()];
        v.copyInto(out);
        return out;
    }

    /* thay z.a(String, Font, int): '|' xuong dong; khong co dong -> null */
    public static String[] a(String s, Font f, int maxW) {
        String[] r0 = wrap(s, maxW, '|');
        return r0.length == 0 ? null : r0;
    }

    /* thay z.a(String, int): goc chia co dinh maxW/14 ky tu moi dong */
    public static String[] a(String s, int maxW) {
        return wrap(s, maxW, '\u0000');
    }

    /* thay z.a(Graphics, String, int, short[], int, int): '&' xuong dong, dong cao 24 */
    public static int a(Graphics g, String s, int dy, short[] box, int anchor, int color) {
        g.setClip(box[1], box[2], box[3], box[4]);
        String[] ls = wrap(s, box[3], '&');
        short y = box[2];
        int last = 0;
        for (int i = 0; i < ls.length; ++i) {
            short[] b2 = new short[]{box[1], y, box[3], 24};
            short[] p = cb.a(b2, anchor, false);
            r.a(g, ls[i], p[0], p[1] + dy, anchor, color);
            y = (short)(y + 24);
            last = y;
        }
        g.setClip(0, 0, 240, 320);
        return last;
    }

    /* thay cb.a 11 tham so: '&' xuong dong, dong cao 12 + gap */
    public static int b(Graphics g, String s, int dy, int x0, int y0, int w, int h, int gap, int anchor, int c1, int c2) {
        cb.a(g);
        String[] ls = wrap(s, w, '&');
        int y = y0;
        int last = 0;
        for (int i = 0; i < ls.length; ++i) {
            g.setClip(x0, y0, w, h);
            if (anchor == 17) {
                r.a(g, ls[i], x0 + (w >> 1), y + dy, anchor, c1, c2);
            } else if (anchor == 20) {
                r.a(g, ls[i], x0, y + dy, anchor, c1, c2);
            }
            y += 12 + gap;
            last = y;
        }
        cb.b(g);
        return last - y0;
    }

    /* thay cb.a 10 tham so: nhu tren, dong cao 15 */
    public static int c(Graphics g, String s, int dy, int x0, int y0, int w, int h, int anchor, int c1, int c2) {
        cb.a(g);
        String[] ls = wrap(s, w, '&');
        int y = y0;
        int last = 0;
        for (int i = 0; i < ls.length; ++i) {
            g.setClip(x0, y0, w, h);
            if (anchor == 17) {
                r.a(g, ls[i], x0 + (w >> 1), y + dy, anchor, c1, c2);
            } else if (anchor == 20) {
                r.a(g, ls[i], x0, y + dy, anchor, c1, c2);
            }
            y += 15;
            last = y;
        }
        cb.b(g);
        return last - y0;
    }

    /* thay cb.a(Graphics, String, bn, int, int) (chu chay trong o). Goc dung 1 bo dem chung cho moi
       chuoi nen nhieu o chu chay trong cung 1 khung hinh bi reset lien tuc. Ban nay: chuoi vua o thi
       dung yen, dai hon o thi chay rieng tung chuoi (cung buoc 3px/khung hinh nhu goc). */
    private static final int MN = 8;
    private static final String[] mS = new String[MN];
    private static final int[] mE = new int[MN];
    private static int mP;

    public static void m(Graphics g, String s, bn b, int c1, int c2) {
        if (s == null || s.length() == 0) {
            return;
        }
        int tw = bs.a(s);
        int bw = b.c - 3;
        int off = 0;
        if (tw > bw) {
            int k = -1;
            for (int i = 0; i < MN; ++i) {
                if (mS[i] == s || s.equals(mS[i])) {
                    k = i;
                    break;
                }
            }
            if (k < 0) {
                k = mP;
                mP = (mP + 1) % MN;
                mS[k] = s;
                mE[k] = -12;
            }
            off = mE[k] < 0 ? 0 : mE[k];
            mE[k] += 3;
            if (mE[k] > tw - bw + 24) {
                mE[k] = -24;
            }
            if (off > tw - bw) {
                off = tw - bw;
            }
        }
        cb.a(g);
        g.setClip(b.a, b.b, bw, b.d);
        r.a(g, s, b.a - off + 1, b.b, 20, c1, c2);
        cb.b(g);
    }

    /* Graphics.drawString cua chu game -> font bitmap, mau hien tai */
    public static void ds(Graphics g, String s, int x0, int y0, int anchor) {
        if (s == null) {
            return;
        }
        r.b(g, s, x0, y0, anchor, g.getColor());
    }

    /* Font.stringWidth trong class ve bang bitmap -> do rong bitmap */
    public static int sw(Font f, String s) {
        return r.a ? bs.a(s) : f.stringWidth(s);
    }

    /* ------------------------------------------------ khung thoai (thay n.a(Graphics,short[],int,int[],int)) */
    private static String dS;
    private static int dFrom = -1;
    private static int dW;
    private static int dL;
    private static short[] dSg;
    private static int dN;
    private static Object[] oT;   /* chuoi ve, null = op sign */
    private static int[] oA;      /* x, hoac offset sign */
    private static int[] oB;      /* y */
    private static int dEnd;
    /* mau dau trang: goc lay n.da, ma n.da = ex sau moi lan lat trang lam ten nguoi noi (al ve bang
       n.da) doi mau theo chu cuoi trang truoc. Giu mau noi tiep o day, tra n.da ve nhu cu. */
    private static String pS;
    private static int pFrom;
    private static int pBase;
    private static int pCol;
    private static int pLast;

    private static int sign(short[] sg, int ns, int idx) {
        for (int k = 0, o = 0; k < ns; ++k, o += 3) {
            if (sg[o] == idx) {
                return o;
            }
        }
        return -1;
    }

    private static void layout(String s, int from, short[] sg, int ns, int bx, int by, int bw, int lines, int lh) {
        Vector t = new Vector(8, 8);
        Vector a = new Vector(8, 8);
        Vector b = new Vector(8, 8);
        int n = s.length();
        int line = 0;
        int i = from;
        int end = n;
        while (i < n) {
            /* tim cuoi dong [i, e) va vi tri bat dau dong sau nx */
            int w = 0;
            int e = i;
            int brk = -1;
            int nxt = -1;
            boolean forced = false;
            int j = i;
            while (j < n) {
                int so = sign(sg, ns, j);
                if (j > i && so >= 0 && sg[so + 1] == 0) {
                    forced = true;
                    break;
                }
                char c = s.charAt(j);
                int k = cw(c);
                if (w + k > bw && j > i) {
                    break;
                }
                if (c == ' ') {
                    brk = j;
                    nxt = j + 1;
                } else if (c >= '⺀' && j > i) {
                    brk = j;
                    nxt = j;
                }
                w += k;
                ++j;
            }
            int nx;
            if (j >= n || forced) {
                e = j;
                nx = j;
            } else if (brk > i) {
                e = brk;
                nx = nxt;
            } else {
                e = j;
                nx = j;
            }
            if (line >= lines) {
                end = i;
                break;
            }
            /* tach dong thanh cac doan theo sign */
            int x0 = bx;
            int y0 = by + line * lh;
            int rs = i;
            for (int p = i; p <= e; ++p) {
                int so = p < n ? sign(sg, ns, p) : -1;
                boolean cut = p == e || (so >= 0 && sg[so + 1] != 0);
                if (!cut) {
                    continue;
                }
                if (p > rs) {
                    String part = s.substring(rs, p);
                    t.addElement(part);
                    a.addElement(new Integer(x0));
                    b.addElement(new Integer(y0));
                    x0 += bs.a(part);
                    rs = p;
                }
                if (p < e && so >= 0) {
                    t.addElement(null);
                    a.addElement(new Integer(so));
                    b.addElement(new Integer(0));
                }
            }
            /* sign nam tren cac ky tu bi bo (dau cach cuoi dong) van ap dung */
            for (int p = e; p < nx && p < n; ++p) {
                int so = sign(sg, ns, p);
                if (so >= 0 && sg[so + 1] != 0) {
                    t.addElement(null);
                    a.addElement(new Integer(so));
                    b.addElement(new Integer(0));
                }
            }
            ++line;
            i = nx;
            while (i < n && s.charAt(i) == ' ' && !(sign(sg, ns, i) >= 0)) {
                ++i;
            }
        }
        int m = t.size();
        oT = new Object[m];
        oA = new int[m];
        oB = new int[m];
        for (int k = 0; k < m; ++k) {
            oT[k] = t.elementAt(k);
            oA[k] = ((Integer)a.elementAt(k)).intValue();
            oB[k] = ((Integer)b.elementAt(k)).intValue();
        }
        dEnd = end;
    }

    public static void d(Graphics g, short[] sg, int lines, int[] box, int unused, String s, int from, int ns, int ex0, ae act) {
        int lh = 22;
        if (box.length > 4 && box[4] > 0) {
            int l3 = (box[4] - 15) / 18 + 1;
            if (l3 > lines) {
                lines = l3 > 3 ? 3 : l3;
                lh = 18;
            }
        }
        if (s != dS || from != dFrom || box[3] != dW || lines != dL || sg != dSg || ns != dN) {
            layout(s, from, sg, ns, box[1], box[2] + 3, box[3], lines, lh);
            dS = s;
            dFrom = from;
            dW = box[3];
            dL = lines;
            dSg = sg;
            dN = ns;
        }
        if (s != pS) {
            pS = s;
            pFrom = from;
            pBase = n.da;
            pCol = n.da;
        } else if (from != pFrom) {
            pFrom = from;
            pCol = pLast;
        }
        g.setColor(pCol);
        int ex = pCol;
        for (int k = 0; k < oT.length; ++k) {
            Object o = oT[k];
            if (o == null) {
                int so = oA[k];
                switch (sg[so + 1]) {
                    case 1: {
                        act.e(sg[so + 2] - 1);
                        break;
                    }
                    case 2: {
                        short st = sg[so + 2];
                        g.setColor(n.p(st));
                        g.setFont(n.o(st));
                        g.setStrokeStyle(0);
                        break;
                    }
                    case 3: {
                        g.setColor(n.p(0));
                        g.setFont(n.o(0));
                        g.setStrokeStyle(0);
                        break;
                    }
                }
                continue;
            }
            ex = g.getColor();
            r.a(g, (String)o, oA[k], oB[k], 0, ex);
        }
        pLast = ex;
        z = dEnd;
        x = pBase;
    }
}
