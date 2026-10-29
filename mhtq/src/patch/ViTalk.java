/**
 * Ham tro giup cho khung thoai 3 dong/trang (scripts/patch_wrap.py vá m.a(String,String,w,z,Z,I,Z)V).
 * tien do go chu prog[i] (px) cua tung dong, rong that cua dong wid[i], rong cho phep n.
 */
public final class ViTalk {
    /** moi dong da go du: prog[i] >= wid[i]. */
    public static boolean done(int[] prog, int[] wid) {
        for (int i = 0; i < prog.length; i++) {
            if (prog[i] < wid[i]) {
                return false;
            }
        }
        return true;
    }

    /** con dong nao chua go toi n. */
    public static boolean lt(int[] prog, int n) {
        for (int i = 0; i < prog.length; i++) {
            if (prog[i] < n) {
                return true;
            }
        }
        return false;
    }

    public static void fill(int[] prog, int n) {
        for (int i = 0; i < prog.length; i++) {
            prog[i] = n;
        }
    }

    /** go them 15px vao dong dau tien chua toi n; tra true neu da tien. */
    public static boolean step(int[] prog, int n) {
        for (int i = 0; i < prog.length; i++) {
            if (prog[i] < n) {
                prog[i] += 15;
                return true;
            }
        }
        return false;
    }
}
