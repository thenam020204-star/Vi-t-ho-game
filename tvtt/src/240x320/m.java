/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
package src;

import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.microedition.lcdui.Image;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class m {
    private static Random a = new Random();
    static short[] a;
    static int a;
    static short[][] a;
    static byte[][][] a;
    static short[][][][] a;
    static short[][] b;
    static short[][] c;
    static byte[] a;
    static int[] a;
    static String[] a;
    static short[][] d;
    static short[][] e;
    static short[][] f;
    static short[][][] a;
    static byte[][][][] a;
    static byte[][][][] b;
    static String[][] a;
    static byte[][] a;
    static short[][] g;

    public static void a() {
        a = null;
        a = null;
        a = null;
        a = null;
        b = null;
        c = null;
    }

    public static void a(String object) {
        try {
            int n2;
            int n3;
            int n4;
            object = new DataInputStream(new Object().getClass().getResourceAsStream((String)object));
            a = new short[((DataInputStream)object).readShort()];
            for (n4 = 0; n4 < a.length; ++n4) {
                m.a[n4] = ((DataInputStream)object).readShort();
            }
            a = ((DataInputStream)object).readInt();
            a = new short[((DataInputStream)object).readShort()][];
            for (n4 = 0; n4 < a.length; ++n4) {
                m.a[n4] = new short[5];
                for (n3 = 0; n3 < a[n4].length; ++n3) {
                    m.a[n4][n3] = ((DataInputStream)object).readShort();
                }
            }
            a = new byte[((DataInputStream)object).readShort()][][];
            for (n3 = 0; n3 < a.length; ++n3) {
                m.a[n3] = new byte[((DataInputStream)object).readShort()][];
                block18: for (n2 = 0; n2 < a[n3].length; ++n2) {
                    byte by = ((DataInputStream)object).readByte();
                    n4 = by;
                    switch (by) {
                        case 0: {
                            m.a[n3][n2] = new byte[1];
                            m.a[n3][n2][0] = n4;
                            continue block18;
                        }
                        case 1: {
                            m.a[n3][n2] = new byte[3];
                            m.a[n3][n2][0] = n4;
                            m.a[n3][n2][1] = 0;
                            m.a[n3][n2][2] = 0;
                            continue block18;
                        }
                        case 2: {
                            m.a[n3][n2] = new byte[5];
                            m.a[n3][n2][0] = n4;
                            for (n4 = 1; n4 < a[n3][n2].length; ++n4) {
                                m.a[n3][n2][n4] = ((DataInputStream)object).readByte();
                            }
                            continue block18;
                        }
                        case 3: {
                            m.a[n3][n2] = new byte[7];
                            m.a[n3][n2][0] = n4;
                            for (n4 = 1; n4 < a[n3][n2].length; ++n4) {
                                m.a[n3][n2][n4] = ((DataInputStream)object).readByte();
                            }
                            continue block18;
                        }
                    }
                }
            }
            a = new short[a.length][][][];
            for (n3 = 0; n3 < a.length; ++n3) {
                m.a[n3] = new short[a[n3].length][][];
                for (n2 = 0; n2 < a[n3].length; ++n2) {
                    m.a[n3][n2] = new short[((DataInputStream)object).readShort()][];
                    block23: for (n4 = 0; n4 < a[n3][n2].length; ++n4) {
                        switch (a[n3][n2][0]) {
                            case 0: {
                                int n5;
                                m.a[n3][n2][n4] = new short[4];
                                for (n5 = 0; n5 < a[n3][n2][n4].length; ++n5) {
                                    m.a[n3][n2][n4][n5] = ((DataInputStream)object).readShort();
                                }
                                continue block23;
                            }
                            case 1: {
                                int n5;
                                m.a[n3][n2][n4] = new short[5];
                                for (n5 = 0; n5 < a[n3][n2][n4].length; ++n5) {
                                    m.a[n3][n2][n4][n5] = ((DataInputStream)object).readShort();
                                }
                                continue block23;
                            }
                            case 2: {
                                int n5;
                                m.a[n3][n2][n4] = new short[4];
                                for (n5 = 0; n5 < a[n3][n2][n4].length; ++n5) {
                                    m.a[n3][n2][n4][n5] = ((DataInputStream)object).readShort();
                                }
                                continue block23;
                            }
                            case 3: {
                                int n5;
                                m.a[n3][n2][n4] = new short[6];
                                for (n5 = 0; n5 < 4; ++n5) {
                                    m.a[n3][n2][n4][n5] = ((DataInputStream)object).readShort();
                                }
                                m.a[n3][n2][n4][4] = a[n3][n2][n4][2];
                                m.a[n3][n2][n4][5] = a[n3][n2][n4][3];
                            }
                        }
                    }
                }
            }
            b = new short[((DataInputStream)object).readShort()][4];
            for (n3 = 0; n3 < b.length; ++n3) {
                for (n2 = 0; n2 < b[n3].length; ++n2) {
                    m.b[n3][n2] = ((DataInputStream)object).readShort();
                }
            }
            c = new short[((DataInputStream)object).readShort()][4];
            for (n3 = 0; n3 < c.length; ++n3) {
                for (n2 = 0; n2 < c[n3].length; ++n2) {
                    m.c[n3][n2] = ((DataInputStream)object).readShort();
                }
            }
            ((FilterInputStream)object).close();
            return;
        }
        catch (IOException iOException) {
            object = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public static void b(String object) {
        try {
            int n2;
            int n3;
            object = new DataInputStream(new Object().getClass().getResourceAsStream((String)object));
            a = new String[((DataInputStream)object).readShort()];
            for (n3 = 0; n3 < a.length; ++n3) {
                m.a[n3] = ((DataInputStream)object).readUTF();
            }
            d = new short[((DataInputStream)object).readShort()][];
            for (n3 = 0; n3 < d.length; ++n3) {
                m.d[n3] = new short[((DataInputStream)object).readShort()];
                for (n2 = 0; n2 < d[n3].length; ++n2) {
                    m.d[n3][n2] = ((DataInputStream)object).readShort();
                }
            }
            e = new short[((DataInputStream)object).readShort()][];
            for (n3 = 0; n3 < e.length; ++n3) {
                m.e[n3] = new short[((DataInputStream)object).readShort()];
                for (n2 = 0; n2 < e[n3].length; ++n2) {
                    m.e[n3][n2] = ((DataInputStream)object).readShort();
                }
            }
            f = new short[((DataInputStream)object).readShort()][];
            for (n3 = 0; n3 < f.length; ++n3) {
                m.f[n3] = new short[((DataInputStream)object).readShort()];
                for (n2 = 0; n2 < f[n3].length; ++n2) {
                    m.f[n3][n2] = ((DataInputStream)object).readShort();
                }
            }
            a = new short[((DataInputStream)object).readShort()][][];
            for (n3 = 0; n3 < a.length; ++n3) {
                m.a[n3] = new short[((DataInputStream)object).readShort()][];
                for (n2 = 0; n2 < a[n3].length; ++n2) {
                    m.a[n3][n2] = new short[((DataInputStream)object).readShort()];
                    for (int i2 = 0; i2 < a[n3][n2].length; ++i2) {
                        m.a[n3][n2][i2] = ((DataInputStream)object).readShort();
                    }
                }
            }
            ((FilterInputStream)object).close();
            return;
        }
        catch (IOException iOException) {
            object = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public static void b() {
        a = null;
        d = null;
        e = null;
        f = null;
        a = null;
    }

    public static Image a(String string) {
        try {
            Image image = Image.createImage((String)string);
            return image;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            System.out.print(string);
            return null;
        }
    }

    public static Image b(String string) {
        try {
            byte[] byArray = m.b(string);
            return Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            System.out.print(string);
            return null;
        }
    }

    private static byte[] b(String string) {
        byte[] byArray = null;
        try {
            int n2;
            DataInputStream dataInputStream = new DataInputStream(new Object().getClass().getResourceAsStream(string));
            byte[] byArray2 = new byte[4];
            for (n2 = 0; n2 < 4; ++n2) {
                byArray2[n2] = dataInputStream.readByte();
            }
            n2 = (byArray2[3] & 0xFF) << 24 | (byArray2[2] & 0xFF) << 16 | (byArray2[1] & 0xFF) << 8 | byArray2[0] & 0xFF;
            byArray = new byte[n2];
            dataInputStream.read(byArray);
            dataInputStream.close();
        }
        catch (Exception exception) {
            System.out.print("getStream err! path = " + string);
            exception.printStackTrace();
        }
        return byArray;
    }

    private static byte[] c(String object) {
        try {
            int n2;
            object = new DataInputStream(new Object().getClass().getResourceAsStream((String)object));
            byte[] byArray = new byte[4];
            for (n2 = 0; n2 < 4; ++n2) {
                byArray[n2] = ((DataInputStream)object).readByte();
            }
            n2 = (byArray[0] & 0xFF) << 24 | (byArray[1] & 0xFF) << 16 | (byArray[2] & 0xFF) << 8 | byArray[3] & 0xFF;
            byArray = new byte[n2 - 4];
            ((DataInputStream)object).read(byArray);
            ((FilterInputStream)object).close();
            return byArray;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            System.out.print("getColorData err!");
            return null;
        }
    }

    public static Image a(String object, String object2) {
        object = m.b((String)object);
        object2 = m.c((String)object2);
        for (int i2 = 0; i2 < ((Object)object2).length; ++i2) {
            object[i2 + 41] = object2[i2];
        }
        return Image.createImage((byte[])object, (int)0, (int)((Object)object).length);
    }

    public static int a(int n2) {
        return (a.nextInt() & Integer.MAX_VALUE) % n2;
    }

    public static int a(int n2, int n3) {
        return (a.nextInt() & Integer.MAX_VALUE) % (n2 - n3) + n2;
    }

    public static int b(int n2) {
        return a.nextInt() % n2;
    }

    public static void c() {
        a = null;
        b = null;
    }

    public static void c(String object) {
        try {
            int n2;
            int n3;
            int n4;
            int n5;
            object = new DataInputStream(new Object().getClass().getResourceAsStream((String)object));
            a = new byte[((DataInputStream)object).readShort()][][][];
            for (n5 = 0; n5 < a.length; ++n5) {
                m.a[n5] = new byte[((DataInputStream)object).readShort()][][];
                for (n4 = 0; n4 < a[n5].length; ++n4) {
                    m.a[n5][n4] = new byte[((DataInputStream)object).readShort()][];
                    for (n3 = 0; n3 < a[n5][n4].length; ++n3) {
                        m.a[n5][n4][n3] = new byte[13];
                        for (n2 = 0; n2 < a[n5][n4][n3].length; ++n2) {
                            m.a[n5][n4][n3][n2] = ((DataInputStream)object).readByte();
                        }
                    }
                }
            }
            b = new byte[a.length][][][];
            for (n5 = 0; n5 < b.length; ++n5) {
                m.b[n5] = new byte[((DataInputStream)object).readShort()][][];
                for (n4 = 0; n4 < b[n5].length; ++n4) {
                    m.b[n5][n4] = new byte[((DataInputStream)object).readShort()][];
                    for (n3 = 0; n3 < b[n5][n4].length; ++n3) {
                        m.b[n5][n4][n3] = new byte[17];
                        for (n2 = 0; n2 < b[n5][n4][n3].length; ++n2) {
                            m.b[n5][n4][n3][n2] = ((DataInputStream)object).readByte();
                        }
                    }
                }
            }
            ((FilterInputStream)object).close();
            return;
        }
        catch (IOException iOException) {
            object = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public static void a(byte[] byArray, String string, int n2) {
        try {
            InputStream inputStream = new Object().getClass().getResourceAsStream(string);
            inputStream = new DataInputStream(inputStream);
            ((FilterInputStream)inputStream).skip(0L);
            ((DataInputStream)inputStream).read(byArray, 0, byArray.length);
            ((FilterInputStream)inputStream).close();
            return;
        }
        catch (Exception exception) {
            System.out.print("name = " + string);
            System.out.print("GameInf err at getStream()  :  " + exception);
            exception.printStackTrace();
            return;
        }
    }

    public static short[] a(byte[] byArray, int[] nArray) {
        short s2 = m.a(byArray, nArray);
        int n2 = m.a(byArray, nArray);
        short[] sArray = new short[s2 * n2];
        for (n2 = 0; n2 < sArray.length; ++n2) {
            byte by = byArray[nArray[0]];
            nArray[0] = nArray[0] + 1;
            byte by2 = byArray[nArray[0]];
            nArray[0] = nArray[0] + 1;
            sArray[n2] = (short)(by << 8 | by2 & 0xFF);
        }
        return sArray;
    }

    public static short[][] a(byte[] byArray, int[] nArray) {
        int n2 = m.a(byArray, nArray);
        short s2 = m.a(byArray, nArray);
        short[][] sArrayArray = new short[n2][];
        for (int i2 = 0; i2 < n2; ++i2) {
            int n3 = m.a(byArray, nArray);
            sArrayArray[i2] = new short[n3 * s2];
            for (n3 = 0; n3 < sArrayArray[i2].length; ++n3) {
                byte by = byArray[nArray[0]];
                nArray[0] = nArray[0] + 1;
                byte by2 = byArray[nArray[0]];
                nArray[0] = nArray[0] + 1;
                sArrayArray[i2][n3] = (short)(by << 8 | by2 & 0xFF);
            }
        }
        return sArrayArray;
    }

    private static short a(byte[] byArray, int[] nArray) {
        int n2 = nArray[0];
        nArray[0] = n2 + 1;
        int n3 = nArray[0];
        nArray[0] = n3 + 1;
        return (short)((byArray[n2] & 0xFF) << 8 | byArray[n3] & 0xFF);
    }

    public static int[] a(Image object) {
        int n2 = object.getWidth();
        int n3 = object.getHeight();
        object = m.b(object);
        for (int i2 = 0; i2 < n3; ++i2) {
            for (int i3 = 0; i3 < n2; ++i3) {
                Object object2 = object[i2 * n2 + i3];
                int n4 = object2 & 0xFF000000;
                int n5 = 0xFF0000 - (object2 & 0xFF0000);
                int n6 = 65280 - (object2 & 0xFF00);
                object2 = 255 - (object2 & 0xFF);
                object[i2 * n2 + i3] = (Image)(n4 | n5 | n6 | object2);
            }
        }
        return object;
    }

    public static int[] b(Image image) {
        int n2 = image.getWidth();
        int n3 = image.getHeight();
        int[] nArray = new int[n2 * n3];
        image.getRGB(nArray, 0, n2, 0, 0, n2, n3);
        return nArray;
    }

    public static byte[] a(String object) {
        byte[] byArray = null;
        try {
            object = new Object().getClass().getResourceAsStream((String)object);
            byArray = new byte[((InputStream)object).available()];
            ((InputStream)object).read(byArray, 0, byArray.length);
            ((InputStream)object).close();
        }
        catch (IOException iOException) {
            object = iOException;
            iOException.printStackTrace();
        }
        return byArray;
    }

    public static void d() {
        a = null;
    }

    public static void d(String object) {
        try {
            object = new DataInputStream(new Object().getClass().getResourceAsStream((String)object));
            a = new String[((DataInputStream)object).readShort()][((DataInputStream)object).readShort()];
            for (int i2 = 0; i2 < a.length; ++i2) {
                for (int i3 = 0; i3 < a[i2].length; ++i3) {
                    m.a[i2][i3] = ((DataInputStream)object).readUTF();
                }
            }
            ((FilterInputStream)object).close();
            return;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return;
        }
    }

    public static void e() {
        a = null;
    }

    public static void e(String object) {
        try {
            object = new DataInputStream(new Object().getClass().getResourceAsStream((String)object));
            a = new byte[((DataInputStream)object).readShort()][((DataInputStream)object).readShort()];
            for (int i2 = 0; i2 < a.length; ++i2) {
                for (int i3 = 0; i3 < a[i2].length; ++i3) {
                    m.a[i2][i3] = ((DataInputStream)object).readByte();
                }
            }
            ((FilterInputStream)object).close();
            return;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return;
        }
    }

    public static void f() {
        g = null;
    }

    public static void f(String object) {
        try {
            object = new DataInputStream(new Object().getClass().getResourceAsStream((String)object));
            g = new short[((DataInputStream)object).readShort()][((DataInputStream)object).readShort()];
            for (int i2 = 0; i2 < g.length; ++i2) {
                for (int i3 = 0; i3 < g[i2].length; ++i3) {
                    m.g[i2][i3] = ((DataInputStream)object).readShort();
                }
            }
            ((FilterInputStream)object).close();
            return;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return;
        }
    }

    static {
        a = new int[]{0};
    }
}

