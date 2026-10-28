import java.util.Vector;

/**
 * Ngat dong theo dau cach, do bang BitmapFont (ViFont.cw = f.a(C)I da hook).
 * scripts/patch_wrap.py thay than hai ham ngat dong cua game, giu nguyen descriptor:
 *   k.b(String,int)[String        -> ViWrap.wrap(s, w, 1)   (dong rong <= w, nhu k.b goc)
 *   j.a(String,char,int)[String   -> ViWrap.wrap(s, w, 0)   (dong rong <  w, nhu j.a goc)
 * Hieu: '|' va '\n' xuong dong; #RRGGBB va ## la ma mau (rong 0, khong bao gio bi cat doi);
 * '#' dang do o cuoi chuoi (khong du 6 ky tu sau) bi bo di thay vi gay StringIndexOutOfBounds.
 * Tu dai hon ca dong thi moi cat theo ky tu. Bo nho dem 4 muc theo (chuoi, rong, che do) de ham ve
 * goi lai moi khung khong cap phat. Mang tra ve dung chung: nguoi goi khong duoc sua.
 */
public final class ViWrap {
    private static final int N = 4;
    private static final String[] ks = new String[N];
    private static final int[] kw = new int[N];
    private static final int[] km = new int[N];
    private static final String[][] kr = new String[N][];
    private static int next;

    public static String[] wrap(String s, int w, int mode) {
        for (int i = 0; i < N; i++) {
            String k = ks[i];
            if (k != null && kw[i] == w && km[i] == mode && (k == s || k.equals(s))) {
                return kr[i];
            }
        }
        String[] r = doWrap(s, w, mode);
        ks[next] = s;
        kw[next] = w;
        km[next] = mode;
        kr[next] = r;
        next = (next + 1) % N;
        return r;
    }

    /** Vi tri het van ban: cat ma mau '#' do dang o cuoi. */
    private static int effectiveEnd(String s) {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) == '#') {
                if (i + 1 < len && s.charAt(i + 1) == '#') {
                    i++;
                } else if (i + 7 > len) {
                    return i;
                } else {
                    i += 6;
                }
            }
        }
        return len;
    }

    private static int measure(String s, int a, int b) {
        int w = 0;
        for (int i = a; i < b; i++) {
            char c = s.charAt(i);
            if (c == '#') {
                if (i + 1 < b && s.charAt(i + 1) == '#') {
                    i++;
                } else {
                    i += 6;
                }
                continue;
            }
            w += ViFont.cw(c);
        }
        return w;
    }

    private static void emit(Vector v, String s, int a, int b) {
        while (b > a && s.charAt(b - 1) == ' ') {
            b--;
        }
        v.addElement(s.substring(a, b));
    }

    private static String[] doWrap(String s, int w, int mode) {
        if (w < 20) {
            w = 20;
        }
        int limit = mode == 0 ? w - 1 : w;
        int end = effectiveEnd(s);
        Vector v = new Vector();
        int ls = 0;
        int lw = 0;
        int bp = -1;
        boolean seen = false;
        for (int i = 0; i < end; i++) {
            char c = s.charAt(i);
            if (c == '|' || c == '\n') {
                v.addElement(s.substring(ls, i));
                ls = i + 1;
                lw = 0;
                bp = -1;
                seen = false;
                continue;
            }
            if (c == '#') {
                if (i + 1 < end && s.charAt(i + 1) == '#') {
                    i++;
                } else {
                    i += 6;
                }
                continue;
            }
            int cw = ViFont.cw(c);
            if (c == ' ') {
                if (seen) {
                    bp = i;
                }
                lw += cw;
                continue;
            }
            seen = true;
            lw += cw;
            if (lw > limit) {
                if (bp > ls) {
                    emit(v, s, ls, bp);
                    ls = bp + 1;
                    while (ls < i && s.charAt(ls) == ' ') {
                        ls++;
                    }
                    lw = measure(s, ls, i + 1);
                    bp = -1;
                    if (lw > limit && i > ls) {
                        emit(v, s, ls, i);
                        ls = i;
                        lw = cw;
                    }
                } else if (i > ls) {
                    emit(v, s, ls, i);
                    ls = i;
                    lw = cw;
                    bp = -1;
                }
            }
        }
        if (ls > end) {
            ls = end;
        }
        v.addElement(s.substring(ls, end));
        String[] r = new String[v.size()];
        v.copyInto(r);
        return r;
    }
}
