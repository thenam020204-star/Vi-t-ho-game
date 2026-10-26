import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 * Cầu nối font: các lệnh vẽ/đo chữ của game (Graphics.drawString/drawChar,
 * Font.charWidth/stringWidth/getHeight) được patch bytecode thành invokestatic
 * vào đây, cùng thứ tự tham số trên stack (đối tượng gốc thành tham số đầu).
 * Tham số Font bị bỏ qua: mọi chữ đi qua BitmapFont f15c.
 * split(): thay hàm ngắt dòng theo từng ký tự của game, ngắt tại dấu cách theo px.
 * Biên dịch ecj -source 1.3 -target 1.3 (CLDC 1.0 / MIDP 2.0).
 */
public final class VnFont {

    /** Khoảng cách thêm giữa các dòng (px), cộng vào getHeight(). */
    private static final int LINE_GAP = 2;

    private static BitmapFont bf;

    private VnFont() {
    }

    private static BitmapFont f() {
        if (bf == null) {
            BitmapFont.setDefault(BitmapFont.SIZE_15, BitmapFont.SELECTIVE);
            bf = BitmapFont.current();
        }
        return bf;
    }

    public static void drawString(Graphics g, String s, int x, int y, int anchor) {
        f().drawString(g, s, x, y, anchor);
    }

    public static void drawChar(Graphics g, char c, int x, int y, int anchor) {
        BitmapFont b = f();
        if ((anchor & Graphics.HCENTER) != 0) {
            x -= b.charWidth(c) / 2;
        } else if ((anchor & Graphics.RIGHT) != 0) {
            x -= b.charWidth(c);
        }
        if ((anchor & Graphics.BOTTOM) != 0) {
            y -= b.getHeight();
        } else if ((anchor & Graphics.BASELINE) != 0) {
            y -= b.getBaseline();
        } else if ((anchor & Graphics.VCENTER) != 0) {
            y -= b.getHeight() / 2;
        }
        b.drawChar(g, c, x, y);
    }

    public static int charWidth(Font font, char c) {
        return f().charWidth(c);
    }

    public static int stringWidth(Font font, String s) {
        return f().stringWidth(s);
    }

    public static int getHeight(Font font) {
        return f().getHeight() + LINE_GAP;
    }

    /** Độ rộng hiển thị: bỏ qua mã màu '{' '}' (game không vẽ chúng). */
    private static int w(char c) {
        return (c == '{' || c == '}') ? 0 : f().charWidth(c);
    }

    /**
     * Ngắt dòng cho khung chữ rộng maxW px. Cùng chữ ký và ngữ nghĩa với hàm gốc
     * (String, Font, int) -> String[]: mỗi phần tử là một dòng, hiểu '\n'.
     * Ngắt tại dấu cách cuối cùng còn vừa; từ dài hơn cả dòng thì cắt cứng.
     */
    public static String[] split(String s, Font font, int maxW) {
        if (s == null) {
            return new String[] { "" };
        }
        int n = s.length();
        // lượt 1: đếm dòng; lượt 2: cắt chuỗi (không dùng Vector để đỡ cấp phát)
        int count = 0;
        for (int pass = 0; pass < 2; pass++) {
            String[] out = pass == 0 ? null : new String[count];
            int k = 0;
            int start = 0;
            while (start <= n) {
                int lineW = 0;
                int lastSpace = -1;
                int i = start;
                int end = -1;
                int next = -1;
                while (i < n) {
                    char c = s.charAt(i);
                    if (c == '\n') {
                        end = i;
                        next = i + 1;
                        break;
                    }
                    int cw = w(c);
                    if (lineW + cw > maxW && i > start) {
                        if (c == ' ') {
                            end = i;
                            next = i + 1;
                        } else if (lastSpace > start) {
                            end = lastSpace;
                            next = lastSpace + 1;
                        } else {
                            end = i;
                            next = i;
                        }
                        break;
                    }
                    if (c == ' ') {
                        lastSpace = i;
                    }
                    lineW += cw;
                    i++;
                }
                if (end < 0) {
                    end = n;
                    next = n + 1;
                }
                if (out != null) {
                    out[k] = s.substring(start, end);
                }
                k++;
                start = next;
                if (start == n && end != n && s.charAt(n - 1) != '\n') {
                    break;
                }
            }
            count = k;
            if (out != null) {
                return out;
            }
        }
        return null;
    }
}
