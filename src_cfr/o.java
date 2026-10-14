/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 *  javax.microedition.midlet.MIDlet
 *  javax.microedition.rms.RecordStore
 */
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class o {
    static int a = 16;
    static int b = 32;
    private static int g = 64;
    static int c = 1;
    static int d = 2;
    static int e = 4;
    static int f = 8;
    private static Object a;
    private static Object b;
    private static byte[] a;
    private static short[] a;
    private static short[] b;
    private static short[] c;
    private static byte[] b;
    private static short[] d;
    private static short[] e;
    private static byte[][] a;
    private static short[][] a;
    private static byte[] c;
    private static byte[] d;
    private static byte a;
    private static short[][] b;
    private static short[] f;
    private static short[] g;
    private static short[] h;
    private static byte[] e;
    private static byte[] f;
    private static int h;
    private static String[] a;
    private static byte[] g;
    private static byte b;
    private static int[] a;
    private static int[] b;
    private static byte[] h;
    private static l a;
    private static boolean a;
    static MIDlet a;
    private static RecordStore a;
    private static byte[] i;
    private static int i;
    private static int j;
    private static int k;
    private static boolean b;
    private static final byte[] j;

    public static Object a(h h2, byte[] objectArray) {
        int n2;
        boolean bl = false;
        h h3 = h2;
        int n3 = 0;
        byte by = objectArray[0];
        boolean bl2 = (by & 0x80) != 0;
        int n4 = n2 = (by & 0x40) != 0 ? 1 : 0;
        if (bl2) {
            String cfr_ignored_0 = null + "loadFontChunk::detected rectangle font but support disabled, use SDKConfig to enable";
            Object var2_5 = null;
        }
        byte[] byArray = new byte[12];
        System.arraycopy(objectArray, 1, byArray, 0, 7);
        n3 = 8;
        byArray[7] = 0;
        byArray[8] = byArray[0];
        byArray[9] = byArray[1];
        byArray[10] = bl2 ? 2 : 1;
        if (n2 != 0) {
            byArray[10] = (byte)(byArray[10] | 0xFFFFFF80);
        }
        int n5 = o.a(objectArray, 8);
        n3 += 2;
        short s2 = o.a(objectArray, 10);
        n3 += 2;
        short[] sArray = new short[n5];
        for (int i2 = 0; i2 < n5; ++i2) {
            sArray[i2] = o.a(objectArray, n3);
            n3 += 2;
        }
        short[] sArray2 = new short[n5 + 1];
        for (n2 = 0; n2 < n5; ++n2) {
            sArray2[n2] = o.a(objectArray, n3);
            n3 += 2;
        }
        sArray2[n5] = -1;
        n2 = (n5 << 1) + s2 * 6;
        byte[] byArray2 = new byte[n2];
        System.arraycopy(objectArray, n3, byArray2, 0, n2);
        short[] sArray3 = new short[256];
        for (n2 = 0; n2 < 128; n2 = (int)((short)(n2 + 1))) {
            sArray3[n2] = (short)o.a(sArray, n2, 0, n5 - 1);
        }
        byArray[11] = 1;
        objectArray = new Object[]{byArray, sArray, sArray3, sArray2, byArray2, null};
        return new Object[]{h3, objectArray[0], objectArray[1], objectArray[2], objectArray[3], objectArray[4], objectArray[5], new Integer(-1)};
    }

    public static long a(InputStream inputStream, long l2) {
        return inputStream.skip(l2);
    }

    public static void a(Object object) {
        a = null;
        a = null;
        b = null;
        b = null;
        a = null;
        c = null;
        b = null;
        if (object != null) {
            a = (byte[])((Object[])object)[1];
            b = ((Object[])object)[0];
            ((Integer)((Object[])object)[7]).intValue();
            b = (short[])((Object[])object)[2];
            a = (short[])((Object[])object)[3];
            c = (short[])((Object[])object)[4];
            b = (byte[])((Object[])object)[5];
            if (f != null && a[11] != f[b] && f[b] > 1) {
                o.a();
            }
        }
        a = object;
    }

    public static void a(int n2) {
        if ((a[3] & 0xFF) + (a[4] & 0xFF) + n2 < 0) {
            n2 = -(a[3] & 255 + a[4] & 0xFF);
        }
        o.a[2] = (byte)n2;
    }

    public static int a() {
        return a[2];
    }

    public static int b() {
        return (a[3] & 0xFF) + (a[4] & 0xFF) + a[2];
    }

    public static void a(e e2, int n2, int n3, int n4) {
        o.a(e2, 0, e2.a(), n2, n3, n4);
    }

    private static void a(int[] nArray, int n2) {
        if (a[8] == 1) {
            nArray[0] = nArray[0] + n2;
            return;
        }
        if (a[8] == 4) {
            nArray[0] = nArray[0] - n2;
            return;
        }
        if (a[8] == 2) {
            nArray[1] = nArray[1] + n2;
            return;
        }
        if (a[8] == 8) {
            nArray[1] = nArray[1] - n2;
        }
    }

    private static void a(e e2, int n2, int n3, int n4, int n5, int n6) {
        int n7 = a[3] & 0xFF;
        int n8 = o.b();
        byte by = a[8];
        short s2 = a[9];
        boolean bl = (a[10] & 2) != 0;
        int n9 = 0;
        int n10 = 0;
        if ((n6 & f) != 0) {
            n9 = 0 - o.a(e2, n2, n3);
        } else if ((n6 & c) != 0) {
            n9 = 0 - (o.a(e2, n2, n3) >> 1);
        }
        if ((n6 & g) != 0) {
            n10 = 0 - n7;
        } else if ((n6 & b) != 0) {
            n10 = 0 - n8;
        }
        if (by == 1) {
            n4 += n9;
            n5 += n10;
        } else if (by == 4) {
            n4 -= n9;
            n5 -= n10;
        } else if (by == 2) {
            n4 -= n10;
            n5 += n9;
        } else if (by == 8) {
            n4 -= n10;
            n5 -= n9;
        }
        o.a[0] = n4;
        o.a[1] = n5;
        n4 = e2.b();
        n5 = (byte)((by & 5) != 0 ? (by & 4) >> 1 : ((s2 & 1) << 1) + ((~s2 & 2) >> 1));
        n6 = (byte)((by & 0xA) != 0 ? (by & 8) >> 2 : (s2 & 2) + (~s2 & 1));
        Object var8_9 = null;
        int n11 = o.a.a.getColor();
        for (n9 = n2; n9 < n2 + n3; ++n9) {
            short s3 = e2.a(n9);
            n10 = s3;
            if (s3 == 173 && n9 != n2 + n3 - 1) continue;
            s2 = o.a((short)n10, n4);
            int n12 = o.a(s2);
            if (s2 == -1) {
                if (n10 != 32) {
                    a.a(65280);
                    a.d(a[0], a[1], n12, n8);
                    a.a(0xFF0000);
                    a.b(a[0], a[1], a[0] + n12, a[1] + n8);
                    a.b(a[0], a[1] + n8, a[0] + n12, a[1]);
                }
                o.a(a, n12);
                continue;
            }
            short s4 = s2 = (short)(s2 + 1);
            s2 = (short)(s2 + 1);
            n10 = b[s4] & 0xFF;
            for (int i2 = 0; i2 < n10; ++i2) {
                if (bl) continue;
                short s5 = s2;
                s2 = (short)(s2 + 1);
                int n13 = b[s5] & 0xFF;
                short s6 = s2;
                s2 = (short)(s2 + 1);
                int n14 = b[s6] & 0xFF;
                short s7 = s2;
                s2 = (short)(s2 + 1);
                int n15 = b[s7];
                short s8 = s2;
                s2 = (short)(s2 + 1);
                int n16 = b[s8];
                short s9 = s2;
                s2 = (short)(s2 + 1);
                int n17 = b[s9] & 0xFF;
                short s10 = s2;
                s2 = (short)(s2 + 1);
                int n18 = b[s10] & 0xFF;
                o.b[0] = n15;
                o.b[1] = n16 - n7;
                o.b[2] = -n15 - n17;
                o.b[3] = n7 - n16 - n18;
                n15 = a[0] + b[n5];
                n16 = a[1] + b[n6];
                a.a((h)b, n13, n14, n17, n18, a[7], n15, n16, 0);
            }
            o.a(a, n12);
        }
        a.a(n11);
    }

    public static void a(e e2, short[] sArray, int n2, int n3, int n4, int n5, int n6) {
        int n7 = o.b();
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        if ((n6 & d) != 0) {
            n10 = sArray[0] * n7 - a[2] >> 1;
            n6 ^= d;
        } else if ((n6 & b) != 0) {
            n10 = sArray[0] * n7 - a[2];
            n6 ^= b;
        }
        n6 |= a;
        if (a[9] == 1) {
            n4 -= n10;
            n8 = n7;
        } else if (a[9] == 4) {
            n4 += n10;
            n8 = -n7;
        } else if (a[9] == 2) {
            n5 -= n10;
            n9 = n7;
        } else {
            n5 += n10;
            n9 = -n7;
        }
        n7 = sArray[(sArray[0] & 0xFFFF) + 2];
        for (n10 = n2; n10 < n2 + n3; ++n10) {
            int n11 = sArray[n10] & 0xFFFF;
            int n12 = (sArray[n10 + 1] & 0xFFFF) - n11;
            short s2 = e2.a((sArray[n10 + 1] & 0xFFFF) - 1);
            if (s2 == n7 || s2 == 32) {
                --n12;
            }
            o.a(e2, n11, n12, n4, n5, n6);
            n4 += n8;
            n5 += n9;
        }
    }

    private static int a(short s2) {
        int n2 = a[6] & 0xFF;
        if (s2 == -1) {
            return (a[5] & 0xFF) + n2;
        }
        return (b[s2 & 0xFFFF] & 0xFF) + n2;
    }

    private static short a(short s2, int n2) {
        if (n2 == 0) {
            return c[o.a(b, (int)s2, 0, b.length - 1)];
        }
        if (s2 < 128 || n2 == f[b]) {
            return c[a[s2] & 0xFFFF];
        }
        return -1;
    }

    public static short[] a(e e2, short[] sArray, int n2, short n3) {
        n3 = n2;
        short[] sArray2 = sArray;
        int n4 = e2.a();
        boolean bl = false;
        e e3 = e2;
        boolean bl2 = false;
        if (sArray2 == null) {
            if (e == null) {
                e = new short[50];
            }
            sArray2 = e;
            bl2 = true;
        }
        int n5 = e3.b();
        int n6 = 0;
        int n7 = 0;
        int n8 = -1;
        sArray2[1] = 0;
        int n9 = 2;
        for (int i2 = 0; i2 < n4; ++i2) {
            short s2 = e3.a(i2);
            if (n9 + 3 >= sArray2.length && bl2) {
                e = new short[sArray2.length + 50];
                System.arraycopy(sArray2, 0, e, 0, sArray2.length);
                sArray2 = e;
            }
            if (s2 == 124) {
                sArray2[n9++] = (short)(i2 + 1);
                n6 = 0;
                n7 = 0;
                n8 = -1;
                continue;
            }
            int n10 = 0;
            n10 = o.a(o.a(s2, n5));
            n7 += n10;
            if ((n6 += n10) > n3) {
                if (n8 == -1) {
                    sArray2[n9++] = (short)i2;
                    n6 = n10;
                    n7 = 0;
                    continue;
                }
                sArray2[n9++] = (short)(n8 + 1);
                n8 = s2 == 32 || s2 == 173 ? i2 : -1;
                n6 = n7;
                n7 = 0;
                continue;
            }
            if (s2 != 32 && s2 != 173) continue;
            n8 = i2;
            n7 = 0;
        }
        sArray2[0] = (short)(n9 - 1);
        sArray2[n9] = (short)n4;
        sArray2[n9 + 1] = 124;
        if (bl2) {
            short[] sArray3 = new short[n9 + 2];
            System.arraycopy(sArray2, 0, sArray3, 0, sArray3.length);
            sArray2 = sArray3;
        }
        return sArray2;
    }

    public static int a(e e2) {
        return o.a(e2, 0, e2.a());
    }

    private static int a(e e2, int n2, int n3) {
        int n4 = 0;
        int n5 = e2.b();
        for (int i2 = n2; i2 < n2 + n3; ++i2) {
            n4 += o.a(o.a(e2.a(i2), n5));
        }
        return n4;
    }

    private static boolean c(int n2) {
        byte by = c[n2];
        return a[by] != null && e[by] == n2;
    }

    public static int a(int n2) {
        if (o.c(n2) && !a) {
            return f[n2];
        }
        InputStream inputStream = null;
        try {
            int n3;
            int n4;
            int n5 = n4 = (d[n2] & 0x40) == 0 ? b : 0;
            if (a == 0) {
                inputStream = a.getClass().getResourceAsStream("/t_" + a[n4]);
                for (n3 = 1; n3 < n2; ++n3) {
                    if ((d[n3] & 0x40) != 0 && n4 != 0) continue;
                    long l2 = (f[n3] + 1 << 1) + (b[n4][n3] & 0xFFFF);
                    Object var4_4 = null;
                    inputStream.skip(l2);
                }
            } else if (a == 1) {
                inputStream = a.getClass().getResourceAsStream("/t_" + a[n4] + n2);
            }
            if (a[n3 = c[n2]] == null) {
                o.a[n3] = new short[h[n3]];
            }
            for (int i2 = 0; i2 < f[n2] + 1; ++i2) {
                o.h[0] = (byte)inputStream.read();
                o.h[1] = (byte)inputStream.read();
                o.a[n3][i2] = o.a(h, 0);
            }
            if (a[n3] == null) {
                o.a[n3] = new byte[g[n3] & 0xFFFF];
            }
            n4 = b[n4][n2] & 0xFFFF;
            inputStream.read(a[n3], 0, n4);
            o.e[n3] = (byte)n2;
            inputStream.close();
        }
        catch (IOException iOException) {}
        return f[n2];
    }

    public static int a(byte[] byArray) {
        int n2 = 0;
        if (a == null) {
            return 0;
        }
        ++n2;
        short s2 = 0;
        if (byArray[0] != 1) {
            return 0;
        }
        ++n2;
        a = byArray[1];
        ++n2;
        ++n2;
        ++n2;
        h = (byArray[2] & 0xFF) << 16 | (byArray[3] & 0xFF) << 8 | byArray[4] & 0xFF;
        s2 = o.a(byArray, 5);
        n2 += 2;
        ++n2;
        byte by = byArray[7];
        ++n2;
        short s3 = byArray[8];
        ++n2;
        int n3 = byArray[9];
        int n4 = s3 + 1;
        int n5 = n3 + 1;
        f = new byte[by];
        a = new String[by];
        g = new byte[by];
        f = new short[n5];
        d = new byte[n5];
        c = new byte[n5];
        b = new short[by][n5];
        g = new short[n5];
        h = new short[n5];
        e = new byte[n5];
        a = new byte[n4][];
        a = new short[n4][];
        o.f[0] = s2;
        s3 = (short)(s2 + 1);
        for (n3 = 0; n3 < by; ++n3) {
            o.a[n3] = new String(byArray, n2, 5).trim();
            n2 += 5;
            o.f[n3] = byArray[n2++];
            o.g[n3] = byArray[n2++];
            if (s2 <= 0) continue;
            if (a[0] == null) {
                o.a[0] = new short[by * s3];
            }
            for (n4 = 0; n4 < s3; ++n4) {
                o.a[0][n3 * s3 + n4] = o.a(byArray, n2);
                n2 += 2;
            }
        }
        if (s2 > 0) {
            n3 = a[0][by * s3 - 1];
            o.a[0] = new byte[n3];
            System.arraycopy(byArray, n2, a[0], 0, n3);
            n2 += n3;
        }
        for (n3 = 1; n3 < n5; ++n3) {
            o.d[n3] = (byte)(byArray[n2] & 0xC0);
            o.c[n3] = (byte)(byArray[n2] & 0x3F);
            o.f[n3] = o.a(byArray, ++n2);
            n2 += 2;
            if (f[n3] + 1 > h[c[n3]]) {
                o.h[o.c[n3]] = (short)(f[n3] + 1);
            }
            for (n4 = 0; n4 < by; ++n4) {
                o.b[n4][n3] = o.a(byArray, n2);
                if ((b[n4][n3] & 0xFFFF) > (g[c[n3]] & 0xFFFF)) {
                    o.g[o.c[n3]] = b[n4][n3];
                }
                n2 += 2;
            }
        }
        if (f[b] > 1) {
            o.a(f[b]);
        }
        return by;
    }

    public static e a(int n2, int n3, e e2) {
        n2 = n3 + (f[0] + 1) * n2;
        if (e2 == null) {
            e2 = new e();
        }
        e2.a = a[0];
        e2.a = a[0][n2];
        e2.a = (short)(a[0][n2 + 1] - a[0][n2] >> 1);
        e2.a = 0;
        return e2;
    }

    public static e a(int n2, e e2) {
        int n3;
        int n4 = -1;
        int n5 = 0;
        for (n3 = 0; n3 < f.length; ++n3) {
            if (n2 >= (n5 += f[n3])) continue;
            n4 = n3;
            break;
        }
        n3 = n2 - (n5 - f[n4]);
        n2 = c[n4];
        if (e2 == null) {
            e2 = new e();
        }
        e2.a = a[n2];
        e2.a = a[n2][n3] & 0xFFFF;
        e2.a = (short)((a[n2][n3 + 1] & 0xFFFF) - (a[n2][n3] & 0xFFFF));
        n2 = f[b];
        if ((d[n4] & 0xFFFFFFC0) != 0) {
            n2 = 0;
        }
        if (n2 == 0) {
            e2.a = (short)(e2.a >> 1);
        }
        e2.a = (byte)n2;
        return e2;
    }

    public static int c() {
        return b;
    }

    public static boolean a(int n2) {
        byte by = b;
        if (a != null && n2 >= 0 && n2 < a.length) {
            b = (byte)n2;
        }
        if (b != by) {
            a = true;
            for (int i2 = 1; i2 < c.length; ++i2) {
                if (!o.c(i2) || (d[i2] & 0x40) != 0) continue;
                o.a(i2);
            }
            a = false;
            if (f[b] != f[by] && f[b] > 1) {
                o.a(f[b]);
            }
        }
        return b == n2;
    }

    public static void a(MIDlet mIDlet) {
        a = mIDlet;
    }

    public static int b(int n2) {
        if (a.length == 1) {
            return 0;
        }
        String string = a.getAppProperty("Force-Locale");
        String[] stringArray = new String[a.length];
        System.arraycopy(a, 0, stringArray, 0, stringArray.length);
        if (n2 == -1) {
            int n3;
            int n4 = 0;
            if (string == null) {
                n4 = 2;
            } else if ((string = string.toLowerCase().trim()).length() < 2) {
                n4 = 2;
            } else if (string.equals("menu")) {
                n4 = 1;
            } else if (string.startsWith("auto-")) {
                n4 = 3;
                string = string.substring(5);
            } else {
                n4 = 4;
            }
            for (n3 = 0; n3 < stringArray.length; ++n3) {
                if (stringArray[n3].length() <= 2) continue;
                String string2 = stringArray[n3].substring(0, 2);
                boolean bl = true;
                for (int i2 = 0; i2 < stringArray.length; ++i2) {
                    if (n3 == i2 || !stringArray[i2].startsWith(string2)) continue;
                    bl = false;
                    break;
                }
                if (!bl) continue;
                stringArray[n3] = string2;
            }
            if (n4 == 2 || n4 == 3 || n4 == 1) {
                n2 = o.a(stringArray);
                if (n4 == 1 && n2 > -1) {
                    n2 = -1 - n2;
                }
            }
            if (n4 == 4 || n4 == 3 && n2 == -1) {
                for (n3 = 0; n3 < stringArray.length; ++n3) {
                    if (!string.startsWith(stringArray[n3])) continue;
                    n2 = n3;
                    break;
                }
            }
            if (n4 == 4 && n2 == -1) {
                n2 = o.a(stringArray);
            }
        }
        return n2;
    }

    public static void a(l l2) {
        a = null;
        a = l2;
    }

    public static void a(int n2, byte[] byArray) {
        try {
            o.a(n2, true);
            if (i == null) {
                byte[] byArray2 = new byte[byArray.length + 3];
                i = byArray2;
                byArray2[0] = (byte)k;
                o.i[1] = (byte)(byArray.length >> 8);
                o.i[2] = (byte)byArray.length;
                System.arraycopy(byArray, 0, i, 3, byArray.length);
                o.a(j, i, i.length);
            } else {
                n2 = 0;
                if (i < i.length) {
                    n2 = (i[i + 1] & 0xFF) << 8 | i[i + 2] & 0xFF;
                }
                if (n2 == byArray.length) {
                    System.arraycopy(byArray, 0, i += 3, i, byArray.length);
                    o.a(j, i, i.length);
                } else if (n2 > byArray.length) {
                    ((byte[])(++o.i))[o.i++] = (byte)(byArray.length >> 8);
                    o.i[o.i++] = (byte)byArray.length;
                    System.arraycopy(byArray, 0, i, i, byArray.length);
                    System.arraycopy(i, i + n2, i, i + byArray.length, i.length - i - n2);
                    o.a(j, i, i.length - n2 + byArray.length);
                } else {
                    int n3 = i.length + byArray.length - n2;
                    if (n2 == 0) {
                        n3 += 3;
                    }
                    byte[] byArray3 = new byte[n3];
                    System.arraycopy(i, 0, byArray3, 0, i);
                    byArray3[o.i++] = (byte)k;
                    byArray3[o.i++] = (byte)(byArray.length >> 8);
                    byArray3[o.i++] = (byte)byArray.length;
                    System.arraycopy(byArray, 0, byArray3, i, byArray.length);
                    if (n2 != 0) {
                        System.arraycopy(i, i + n2, byArray3, i + byArray.length, i.length - (i + n2));
                    }
                    o.a(j, byArray3, byArray3.length);
                }
            }
            a.closeRecordStore();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static byte[] a(int n2) {
        try {
            o.a(n2, false);
            if (b) {
                n2 = (i[i + 1] & 0xFF) << 8 | i[i + 2] & 0xFF;
                byte[] byArray = new byte[n2];
                System.arraycopy(i, i + 3, byArray, 0, n2);
                a.closeRecordStore();
                return byArray;
            }
            a.closeRecordStore();
        }
        catch (Exception exception) {}
        return null;
    }

    public static boolean b(int n2) {
        try {
            o.a(n2, false);
            a.closeRecordStore();
        }
        catch (Exception exception) {}
        return !b;
    }

    private static void a(int n2, boolean bl) {
        b = true;
        j = n2 % 10 + 1;
        k = (byte)(n2 / 10);
        a = RecordStore.openRecordStore((String)"sdk", (boolean)true);
        i = 0;
        if (bl) {
            for (n2 = a.getNumRecords() + 1; n2 <= j; ++n2) {
                a.addRecord(j, 0, 0);
            }
        } else if (a.getNumRecords() < j) {
            b = false;
            return;
        }
        if ((i = a.getRecord(j)) == null) {
            b = false;
            return;
        }
        while (i < i.length && i[i] != k) {
            i += (i[i + 1] & 0xFF) << 8 | i[i + 2] & 0xFF;
            i += 3;
        }
        b = i < i.length;
    }

    private static void a(int n2, byte[] byArray, int n3) {
        a.setRecord(n2, byArray, 0, n3);
    }

    static short a(byte[] byArray, int n2) {
        return (short)((byArray[n2] & 0xFF) << 8 | byArray[n2 + 1] & 0xFF);
    }

    static short a(short s2) {
        return (short)(o.a(d, (int)s2, 0, d.length - 1) + 128);
    }

    static short b(short s2) {
        return d[s2 - 128];
    }

    private static byte a(String[] stringArray) {
        int n2 = -1;
        String string = System.getProperty("microedition.locale");
        if (string != null) {
            int n3 = (string = string.toLowerCase().trim()).length();
            if (n3 < 2) {
                return -1;
            }
            for (int i2 = 0; i2 < stringArray.length; ++i2) {
                if (stringArray[i2].length() > n3 && stringArray[i2].startsWith(string)) {
                    n2 = -1;
                    break;
                }
                if (!string.startsWith(stringArray[i2])) continue;
                n2 = (byte)i2;
                break;
            }
        }
        return (byte)n2;
    }

    private static int a(short[] sArray, int n2, int n3, int n4) {
        if (n3 > n4) {
            return sArray.length;
        }
        int n5 = n3 + n4 >> 1;
        int n6 = sArray[n5] & 0xFFFF;
        if (n2 == n6) {
            return n5;
        }
        if (n2 < n6) {
            return o.a(sArray, n2, n3, n5 - 1);
        }
        return o.a(sArray, n2, n5 + 1, n4);
    }

    private static void a(byte n2) {
        int n3 = 0;
        for (int i2 = 1; i2 < n2; ++i2) {
            if ((h & 1 << i2) == 0) continue;
            ++n3;
        }
        if (d == null) {
            d = new short[128];
        }
        InputStream inputStream = null;
        try {
            inputStream = a.getClass().getResourceAsStream("/charmap");
            long l2 = n3 << 8;
            Object var0_1 = null;
            inputStream.skip(l2);
            for (int i3 = 0; i3 < 128; ++i3) {
                o.h[0] = (byte)inputStream.read();
                o.h[1] = (byte)inputStream.read();
                o.d[i3] = o.a(h, 0);
            }
            inputStream.close();
        }
        catch (IOException iOException) {}
        if (a != null && (a[10] & 4) == 0) {
            o.a();
        }
    }

    private static void a() {
        for (short s2 = 128; s2 < 256; s2 = (short)(s2 + 1)) {
            o.a[s2] = (short)o.a(b, (int)o.b(s2), 0, b.length - 1);
        }
        o.a[11] = f[b];
    }

    public static h a(int n2, int n3) {
        h h2 = new h();
        h2.a(Image.createImage((int)n2, (int)n3));
        return h2;
    }

    public static h a(byte[] byArray, int n2, int n3) {
        h h2 = new h();
        h2.a(Image.createImage((byte[])byArray, (int)0, (int)n3));
        return h2;
    }

    public static h a(InputStream inputStream) {
        h h2 = new h();
        h2.a(Image.createImage((InputStream)inputStream));
        return h2;
    }

    static l a() {
        return new l();
    }

    public static int a(InputStream inputStream, byte[] byArray, int n2, int n3) {
        return inputStream.read(byArray, n2, n3);
    }

    private o() {
    }

    static {
        a = false;
        a = new int[2];
        b = new int[4];
        h = new byte[2];
        b = 0;
        a = null;
        i = null;
        i = -1;
        j = -1;
        k = -1;
        b = false;
        j = new byte[0];
    }
}

