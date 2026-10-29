import javax.microedition.lcdui.Graphics;

/**
 * Cau noi giua class f/u cua game (da bi obfuscate) va BitmapFont f13c.
 * scripts/patch_font.py sua bytecode f/u de goi cac ham static duoi day:
 *   f.a(String)I -> sw   f.a(C)I -> cw   f.a()I -> h   f.b()I -> 0
 *   u: Graphics.drawChar -> dc, Graphics.drawString -> ds (cung vi tri, anchor luon 20)
 * Khong cap phat object trong ham ve/do. Bien dich: ecj -source 1.3 -target 1.3.
 */
public final class ViFont {
    private static BitmapFont bf;
    private static boolean tried;

    private static BitmapFont font() {
        if (!tried) {
            tried = true;
            BitmapFont.setDefault(BitmapFont.SIZE_13, BitmapFont.SELECTIVE);
            bf = BitmapFont.get(BitmapFont.SIZE_13, BitmapFont.SELECTIVE);
        }
        return bf;
    }

    /** Font.stringWidth */
    public static int sw(String s) {
        BitmapFont b = font();
        if (s == null) {
            return 0;
        }
        return b != null ? b.stringWidth(s) : s.length() * 7;
    }

    /** Font.charWidth */
    public static int cw(char c) {
        BitmapFont b = font();
        return b != null ? b.charWidth(c) : 7;
    }

    /** Chieu cao dong (thay cho do dong tu anh ve thu). */
    public static int h() {
        BitmapFont b = font();
        return b != null ? b.getHeight() : 13;
    }

    /** Graphics.drawChar(c, x, y, anchor) (game luon truyen anchor 20 = TOP|LEFT). */
    public static void dc(Graphics g, char c, int x, int y, int anchor) {
        BitmapFont b = font();
        if (b != null) {
            b.drawChar(g, c, x, y);
        } else {
            g.drawChar(c, x, y, anchor);
        }
    }

    /** Graphics.drawString(s, x, y, anchor). */
    public static void ds(Graphics g, String s, int x, int y, int anchor) {
        BitmapFont b = font();
        if (b != null) {
            b.drawString(g, s, x, y, anchor);
        } else {
            g.drawString(s, x, y, anchor);
        }
    }
}
