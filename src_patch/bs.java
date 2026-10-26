/*
 * bs = renderer font bitmap cua game (font.bin, 12px), viet lai tu ban CFR da doi chieu javap.
 * Giu nguyen ten/descriptor/access cua moi field + method goc. Thay doi:
 *  - do rong theo advance that cua tung glyph (bin/fw.bin), thay cho luat "c < 0x7F ? a/2 : a";
 *    khong co fw.bin thi quay ve luat goc.
 *  - ky tu khong co trong font: khong ve gi (goc: ve glyph gan nhat trong bang).
 *  - dung lai buffer pixel f thay vi new short[] moi lan ve; vien (mode 1) co le 1px nen dau
 *    tieng Viet o hang tren cung khong bi cat vien.
 *  - nap font bang readFully (goc: vong read(d) khong cong offset).
 */
import com.nokia.mid.ui.DirectGraphics;
import com.nokia.mid.ui.DirectUtils;
import java.io.DataInputStream;
import java.io.InputStream;
import javax.microedition.lcdui.Graphics;

public final class bs {
    public static byte a;
    private static short[] c;
    private static byte[] d;
    private static String e;
    private static short[] f;
    public static final int[] b;
    private static byte[] g;

    public static final void a() {
        if (!r.a) {
            return;
        }
        int n2 = 0;
        DataInputStream dataInputStream = new DataInputStream("".getClass().getResourceAsStream(e));
        try {
            int n3;
            a = dataInputStream.readByte();
            n2 = dataInputStream.readShort();
            c = new short[n2];
            for (n3 = 0; n3 < n2; ++n3) {
                bs.c[n3] = dataInputStream.readShort();
            }
            n3 = a * ((a - 1 >> 3) + 1);
            d = new byte[n2 * n3];
            dataInputStream.readFully(d);
            dataInputStream.close();
            InputStream inputStream = "".getClass().getResourceAsStream("/bin/fw.bin");
            if (inputStream != null) {
                DataInputStream dis = new DataInputStream(inputStream);
                byte[] w = new byte[n2];
                dis.readFully(w);
                dis.close();
                g = w;
            }
            return;
        }
        catch (Exception exception) {
            System.out.println("字库初始化失败！");
            return;
        }
    }

    public static final int a(String string) {
        if (string == null) {
            return 0;
        }
        if (!r.a) {
            return az.a.stringWidth(string);
        }
        int n2 = 0;
        for (int i2 = string.length() - 1; i2 >= 0; --i2) {
            char ch = string.charAt(i2);
            n2 += bs.c(bs.b(ch), ch);
        }
        return n2;
    }

    public static final void a(Graphics graphics, String string, int n2, int n3, int n4, int n5, int n6, byte by2) {
        int n7 = bs.a(string);
        n2 -= bs.a(n4, n7);
        n3 -= bs.b(n4, a);
        switch (by2) {
            case 0: {
                bs.a(graphics, string, n2, n3, n5);
                return;
            }
            case 1: {
                bs.a(graphics, string, n2, n3, n5, n6);
                return;
            }
            case 2: {
                bs.a(graphics, string, n2, n3);
            }
        }
    }

    /* Ve mot mau. */
    public static final void a(Graphics graphics, String string, int n2, int n3, int n4) {
        int w = bs.a(string);
        if (w <= 0) {
            return;
        }
        bs.d(w * a);
        bs.a(string, w, 0, 0, bs.a(n4), false);
        bs.a(graphics, f, 0, w, n2, n3, w, (int)a);
    }

    /* Ve chu mau n4 + vien mau n5, le 1px moi phia. */
    public static final void a(Graphics graphics, String string, int n2, int n3, int n4, int n5) {
        int tw = bs.a(string);
        if (tw <= 0) {
            return;
        }
        int w = tw + 2;
        int h = a + 2;
        int len = w * h;
        bs.d(len);
        short s2 = bs.a(n4);
        short s3 = bs.a(n5);
        bs.a(string, w, 1, 1, s2, false);
        short[] buf = f;
        for (int p = 0; p < len; ++p) {
            if (buf[p] != s2) continue;
            int x = p % w;
            if (x > 0 && buf[p - 1] == 0) buf[p - 1] = 1;
            if (x < w - 1 && buf[p + 1] == 0) buf[p + 1] = 1;
            if (p >= w && buf[p - w] == 0) buf[p - w] = 1;
            if (p + w < len && buf[p + w] == 0) buf[p + w] = 1;
        }
        for (int p = 0; p < len; ++p) {
            if (buf[p] == 1) buf[p] = s3;
        }
        bs.a(graphics, buf, 0, w, n2 - 1, n3 - 1, w, h);
    }

    /* Ve mau chuyen theo hang (bang b). */
    public static final void a(Graphics graphics, String string, int n2, int n3) {
        int w = bs.a(string);
        if (w <= 0) {
            return;
        }
        bs.d(w * a);
        bs.a(string, w, 0, 0, (short)0, true);
        bs.a(graphics, f, 0, w, n2, n3, w, (int)a);
    }

    /* Tim glyph cua ky tu; -1 neu font khong co. */
    private static int b(char ch) {
        short s2 = (short)ch;
        int lo = 0;
        int hi = c.length - 1;
        while (lo <= hi) {
            int mid = lo + hi >> 1;
            short v = c[mid];
            if (v == s2) {
                return mid;
            }
            if (v > s2) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return -1;
    }

    /* Advance (px) cua glyph gi / ky tu ch. */
    private static int c(int gi, char ch) {
        if (gi >= 0 && g != null) {
            return g[gi] & 0xFF;
        }
        return ch < '\u007f' ? a >> 1 : a;
    }

    /* Dam bao buffer f du len phan tu va xoa ve 0 (trong suot). */
    private static void d(int len) {
        if (f == null || f.length < len) {
            f = new short[len];
            return;
        }
        short[] buf = f;
        for (int i2 = 0; i2 < len; ++i2) {
            buf[i2] = 0;
        }
    }

    /* Ghi cac glyph cua chuoi vao f (stride px), goc (ox, oy). */
    private static void a(String string, int stride, int ox, int oy, short color, boolean grad) {
        short[] buf = f;
        int bpr = (a - 1 >> 3) + 1;
        int gl = a * bpr;
        int x0 = ox;
        int n = string.length();
        for (int i2 = 0; i2 < n; ++i2) {
            char ch = string.charAt(i2);
            int gi = bs.b(ch);
            int adv = bs.c(gi, ch);
            if (gi >= 0) {
                int lim = adv < a ? adv : a;
                int base = gi * gl;
                for (int y = 0; y < a; ++y) {
                    int row = (y + oy) * stride + x0;
                    int rb = base + y * bpr;
                    short col = grad ? (short)b[y] : color;
                    for (int x = 0; x < lim; ++x) {
                        if ((d[rb + (x >> 3)] & 128 >> (x & 7)) == 0) continue;
                        buf[row + x] = col;
                    }
                }
            }
            x0 += adv;
        }
    }

    private static int a(short s2) {
        int n2 = 0;
        int n3 = c.length - 1;
        int n4 = -1;
        while (n2 <= n3) {
            n4 = n2 + n3 >> 1;
            if (c[n4] == s2) {
                return n4;
            }
            if (c[n4] > s2) {
                n3 = n4 - 1;
                continue;
            }
            if (c[n4] >= s2) continue;
            n2 = n4 + 1;
        }
        return n4;
    }

    private static final int a(int n2, int n3) {
        if ((n2 & 1) != 0) {
            return n3 >> 1;
        }
        if ((n2 & 8) != 0) {
            return n3;
        }
        return 0;
    }

    private static final int b(int n2, int n3) {
        if ((n2 & 2) != 0) {
            return n3 >> 1;
        }
        if ((n2 & 0x20) != 0) {
            return n3;
        }
        if ((n2 & 0x40) != 0) {
            return n3 >> 1;
        }
        return 0;
    }

    public static final void a(Graphics graphics, short[] sArray, int n2, int n3, int n4, int n5, int n6, int n7) {
        DirectGraphics directGraphics = null;
        directGraphics = DirectUtils.getDirectGraphics((Graphics)graphics);
        directGraphics.drawPixels(sArray, true, n2, n3, n4, n5, n6, n7, 0, 4444);
    }

    public static final short a(int n2) {
        n2 = (n2 & 0xFF000000) == 0 ? 0xFF000000 | n2 : n2;
        int n3 = (n2 & 0xFF000000) >>> 24 >> 4;
        int n4 = (n2 >> 16 & 0xFF) >> 4;
        int n5 = (n2 >> 8 & 0xFF) >> 4;
        int n6 = (n2 & 0xFF) >> 4;
        return (short)(n3 << 12 | n4 << 8 | n5 << 4 | n6);
    }

    static {
        e = "/bin/font.bin";
        b = new int[]{0xBBFF00, 0xAAFF11, 0x99FF22, 0x88FF33, 0x77FF44, 0x66FF55, 0x55FF66, 0x44FF77, 0x33FF88, 0x22FF99, 0x11FFAA, 65467};
    }
}
