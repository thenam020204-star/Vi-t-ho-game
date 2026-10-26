/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 *  javax.microedition.media.Manager
 *  javax.microedition.media.Player
 *  javax.microedition.media.control.VolumeControl
 */
package b;

import b.b;
import b.c;
import b.e;
import b.f;
import b.g;
import e.l;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;
import main.MainMIDlet;

public final class a {
    private static boolean ap = false;
    private static int aq = 1;
    private static int ar = 1;
    private static boolean as = false;
    private static boolean at = false;
    private static boolean au = false;
    public static boolean a = false;
    public static short b = 0;
    public static a.d.b c = null;
    public static boolean d = true;
    public static final int[] e = new int[]{0xFFFA00, 0xFFFA00, 16748800, 16748800};
    public static final int[] f = new int[]{65310, 65310, 41491, 41491};
    public static final int[] g = new int[]{982271, 982271, 2236156, 2236156};
    public static final int[] h = new int[]{0xFFFA00, 0xFFFA00, 13676807, 13676807};
    public static boolean i = true;
    public static f[] j = null;
    public static int[][] k = null;
    public static boolean[] l;
    public static byte m;
    public static boolean n;
    public static String[] o;
    public static Hashtable p;
    private static short[] av;
    private static int[] aw;
    private static byte[] ax;
    public static a.d.b q;
    public static short r;
    private static f.b[] ay;
    private static short[] az;
    private static int[] aA;
    private static byte[] aB;
    public static f.c[] s;
    public static Vector t;
    public static int u;
    public static short v;
    public static a.b.c w;
    public static int x;
    public static int y;
    public static int z;
    public static f[] A;
    public static byte[] B;
    public static int C;
    private static a.f.a[] aC;
    public static Image D;
    public static int E;
    public static Image F;
    public static int G;
    public static Image H;
    public static int I;
    public static Image J;
    public static Image K;
    public static Image L;
    public static Image M;
    public static Image N;
    public static Image O;
    public static Image P;
    public static Image Q;
    public static Image R;
    public static Image S;
    public static Image T;
    public static Image U;
    public static Image V;
    public static Image W;
    public static String X;
    public static int Y;
    public static Image Z;
    public static Image aa;
    public static Image ab;
    public static Image ac;
    public static Image ad;
    public static Image ae;
    public static Image[] af;
    public static Image ag;
    public static boolean ah;
    public static int ai;
    public static byte aj;
    public static byte ak;
    public static int al;
    public static int am;
    public static int an;
    private static short[] aD;
    private static Player aE;
    public static int ao;
    private static Vector aF;

    public static void a() {
        X = a.c.b.d("/tips.dat");
        Y = a.c.b.a(X, main.a.d);
    }

    public static f a(short s) {
        if (j == null) {
            return null;
        }
        for (int i2 = 0; i2 < j.length; ++i2) {
            if (b.a.j[i2].i != s) continue;
            return j[i2];
        }
        return null;
    }

    public static int b(short s) {
        if (j == null) {
            return -1;
        }
        for (int i2 = 0; i2 < j.length; ++i2) {
            if (b.a.j[i2].i != s) continue;
            return i2;
        }
        return -1;
    }

    public static f c(short s) {
        if (A == null) {
            return null;
        }
        for (int i2 = 0; i2 < A.length; ++i2) {
            if (b.a.A[i2].i != s) continue;
            return A[i2];
        }
        return null;
    }

    public static void a(f f2) {
        if (f2 == null) {
            return;
        }
        f2.a(true);
        f2.a = true;
        A = b.a.a(j);
        if (f2.e != null) {
            return;
        }
        f2.e = a.e.c.b("r" + f2.i);
    }

    public static void b(f f2) {
        if (f2 == null) {
            return;
        }
        f2.a(false);
        f2.e = null;
        A = b.a.a(j);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void b() {
        block12: {
            DataInputStream dataInputStream;
            InputStream inputStream;
            block11: {
                block10: {
                    b = 0;
                    c = null;
                    inputStream = null;
                    dataInputStream = null;
                    try {
                        int n2;
                        short s;
                        inputStream = "".getClass().getResourceAsStream("/c.dat");
                        if (inputStream == null) {
                            a.c.b.a(null);
                            break block10;
                        }
                        dataInputStream = new DataInputStream(inputStream);
                        short s2 = dataInputStream.readByte();
                        k = new int[s2][b.f.D.length];
                        for (s = 0; s < s2; ++s) {
                            for (n2 = 0; n2 < k[s].length; ++n2) {
                                b.a.k[s][n2] = dataInputStream.readShort();
                            }
                        }
                        j = new f[s2];
                        for (n2 = 0; n2 < s2; ++n2) {
                            b.a.j[n2] = new f();
                            j[n2].a(dataInputStream);
                            b.a.j[n2].B[2] = j[n2].B();
                            b.a.j[n2].B[4] = j[n2].C();
                            b.a.j[n2].B[6] = j[n2].D();
                            j[n2].a(aD[b.a.j[n2].i]);
                            s = j[n2].e(b.a.j[n2].I);
                            j[n2].b(s);
                        }
                        a.c.b.a(dataInputStream);
                        break block11;
                    }
                    catch (Exception exception) {
                        Exception exception2 = exception;
                        exception.printStackTrace();
                        j = null;
                        break block12;
                    }
                }
                a.c.b.a(inputStream);
                break block12;
            }
            a.c.b.a(inputStream);
            break block12;
            finally {
                a.c.b.a(dataInputStream);
                a.c.b.a(inputStream);
            }
        }
        A = b.a.a(j);
        x = 401;
        y = 8;
        z = 35;
    }

    public static byte a(byte by) {
        int n2 = a.c.b.a((int)by);
        by = (byte)n2;
        if (n2 < 0 || by > 255) {
            return 0;
        }
        return B[by];
    }

    public static void a(byte by, byte by2) {
        int n2 = a.c.b.a((int)by);
        by = (byte)n2;
        if (n2 < 0 || by > 255) {
            return;
        }
        b.a.B[by] = by2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void c() {
        DataInputStream dataInputStream;
        InputStream inputStream;
        block10: {
            block9: {
                inputStream = null;
                dataInputStream = null;
                try {
                    inputStream = "".getClass().getResourceAsStream("/lmagic.dat");
                    if (inputStream == null) {
                        a.c.b.a(null);
                        break block9;
                    }
                    dataInputStream = new DataInputStream(inputStream);
                    int n2 = dataInputStream.readByte();
                    b.f.z = new short[n2][];
                    for (int i2 = 0; i2 < n2; ++i2) {
                        int n3 = a.c.b.a((int)dataInputStream.readByte());
                        b.f.z[i2] = new short[n3];
                        for (int i3 = 0; i3 < n3; ++i3) {
                            b.f.z[i2][i3] = dataInputStream.readShort();
                        }
                    }
                    a.c.b.a(dataInputStream);
                    break block10;
                }
                catch (IOException iOException) {
                    IOException iOException2 = iOException;
                    iOException.printStackTrace();
                    return;
                }
            }
            a.c.b.a(inputStream);
            return;
        }
        a.c.b.a(inputStream);
        return;
        finally {
            a.c.b.a(dataInputStream);
            a.c.b.a(inputStream);
        }
    }

    public static void d() {
        InputStream inputStream = "".getClass().getResourceAsStream("/i.dat");
        if (inputStream == null) {
            return;
        }
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        try {
            int n2;
            int n3 = dataInputStream.readInt();
            if (n3 <= 0) {
                return;
            }
            az = new short[n3];
            aA = new int[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                b.a.az[n2] = dataInputStream.readShort();
                b.a.aA[n2] = dataInputStream.readInt();
            }
            n2 = dataInputStream.readInt();
            aB = new byte[n2];
            dataInputStream.read(aB);
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
        finally {
            a.c.b.a(inputStream);
            a.c.b.a(dataInputStream);
        }
        b.a.a(az, aA);
    }

    private static void a(short[] sArray, int[] nArray) {
        if (sArray == null || sArray.length <= 1 || nArray == null || nArray.length != sArray.length) {
            return;
        }
        int n2 = 0;
        n2 = 0;
        for (int i2 = 1; i2 < sArray.length; ++i2) {
            for (int i3 = sArray.length - 1; i3 >= i2; --i3) {
                if (sArray[i3] >= sArray[i3 - 1]) continue;
                n2 = sArray[i3 - 1];
                sArray[i3 - 1] = sArray[i3];
                sArray[i3] = n2;
                n2 = nArray[i3 - 1];
                nArray[i3 - 1] = nArray[i3];
                nArray[i3] = n2;
            }
        }
    }

    public static void e() {
        f.b[] bArray = b.a.e("/item15.dat");
        if (bArray == null) {
            return;
        }
        s = new f.c[bArray.length];
        for (int i2 = 0; i2 < bArray.length; ++i2) {
            b.a.s[i2] = (f.c)bArray[i2];
        }
        bArray = s;
        if (s == null || bArray.length <= 1) {
            return;
        }
        f.b b2 = null;
        for (int i3 = 1; i3 < bArray.length; ++i3) {
            for (int i4 = bArray.length - 1; i4 >= i3; --i4) {
                if (bArray[i4].q >= bArray[i4 - 1].q) continue;
                b2 = bArray[i4 - 1];
                bArray[i4 - 1] = bArray[i4];
                bArray[i4] = b2;
            }
        }
    }

    private static f.b[] e(String object) {
        object = "".getClass().getResourceAsStream((String)object);
        if (object == null) {
            return null;
        }
        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
        try {
            short s = dataInputStream.readShort();
            f.b[] bArray = new f.b[s];
            for (int i2 = 0; i2 < bArray.length; ++i2) {
                bArray[i2] = b.a.a(dataInputStream);
            }
            f.b[] bArray2 = bArray;
            return bArray2;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return null;
        }
        finally {
            a.c.b.a(dataInputStream);
            a.c.b.a((InputStream)object);
        }
    }

    private static f.b a(DataInputStream object) {
        f.b b2 = null;
        byte by = 0;
        try {
            by = ((DataInputStream)object).readByte();
            switch (by) {
                case 1: 
                case 5: 
                case 12: 
                case 14: 
                case 15: {
                    b2 = new f.c(by);
                    break;
                }
                case 4: 
                case 6: 
                case 7: 
                case 8: 
                case 9: 
                case 10: 
                case 11: 
                case 16: {
                    b2 = new f.a(by);
                    break;
                }
                default: {
                    String cfr_ignored_0 = "\u672a\u77e5\u7269\u54c1\u7c7b\u578b:" + by;
                }
            }
            b2.a((DataInputStream)object);
            return b2;
        }
        catch (IOException iOException) {
            object = iOException;
            iOException.printStackTrace();
            return null;
        }
    }

    public static c d(short s) {
        for (int i2 = 0; i2 < av.length; ++i2) {
            if (av[i2] != s) continue;
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(ax, aw[i2], ax.length - aw[i2]);
            DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
            c c2 = new c();
            c2.a(dataInputStream);
            a.c.b.a(byteArrayInputStream);
            a.c.b.a(dataInputStream);
            return c2;
        }
        return null;
    }

    public static void f() {
        DataInputStream dataInputStream;
        InputStream inputStream;
        block6: {
            inputStream = null;
            dataInputStream = null;
            inputStream = "".getClass().getResourceAsStream("/monster.dat");
            if (inputStream != null) break block6;
            a.c.b.a(inputStream);
            a.c.b.a(null);
            return;
        }
        try {
            int n2;
            dataInputStream = new DataInputStream(inputStream);
            int n3 = dataInputStream.readShort();
            av = new short[n3];
            aw = new int[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                b.a.av[n2] = dataInputStream.readShort();
                b.a.aw[n2] = dataInputStream.readInt();
            }
            n2 = dataInputStream.readInt();
            ax = new byte[n2];
            dataInputStream.read(ax);
        }
        catch (Exception exception) {
            try {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
            catch (Throwable throwable) {
                a.c.b.a(inputStream);
                a.c.b.a(dataInputStream);
                throw throwable;
            }
            a.c.b.a(inputStream);
            a.c.b.a(dataInputStream);
            return;
        }
        a.c.b.a(inputStream);
        a.c.b.a(dataInputStream);
        return;
    }

    public static g[] g() {
        Object object = a.c.b.f("sword5");
        if (object == null) {
            return new g[2];
        }
        object = new ByteArrayInputStream((byte[])object);
        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
        g[] gArray = b.a.b(dataInputStream);
        b.g.a.clear();
        a.c.b.a((InputStream)object);
        a.c.b.a(dataInputStream);
        return gArray;
    }

    private static g[] b(DataInputStream dataInputStream) {
        try {
            short s = dataInputStream.readShort();
            if (s <= 0) {
                return new g[2];
            }
            g[] gArray = new g[s];
            for (int i2 = 0; i2 < gArray.length; ++i2) {
                if (dataInputStream.readBoolean()) continue;
                gArray[i2] = new g();
                if (gArray[i2].a(dataInputStream)) continue;
                return new g[2];
            }
            return gArray;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return new g[2];
        }
    }

    public static void a(int n2) {
        b.a.o();
        g g2 = new g();
        b.a.h();
        g2.c();
        g[] gArray = b.a.g();
        if (n2 < 0 || n2 >= gArray.length) {
            return;
        }
        gArray[n2] = g2;
        Object[] objectArray = gArray;
        b.a.o();
        objectArray = b.a.a(objectArray);
        a.c.b.a("sword5", (byte[])objectArray);
    }

    public static void h() {
        if (w == null) {
            return;
        }
        byte[] byArray = w.n();
        p.put(new Integer(b.a.w.k), byArray);
    }

    private static byte[] a(g[] gArray) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            if (gArray == null) {
                dataOutputStream.writeShort(0);
            } else {
                dataOutputStream.writeShort(gArray.length);
                for (int i2 = 0; i2 < gArray.length; ++i2) {
                    if (gArray[i2] == null) {
                        dataOutputStream.writeBoolean(true);
                        continue;
                    }
                    dataOutputStream.writeBoolean(false);
                    ((OutputStream)dataOutputStream).write(gArray[i2].a());
                }
            }
            byte[] byArray = byteArrayOutputStream.toByteArray();
            return byArray;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return null;
        }
        finally {
            a.c.b.a(byteArrayOutputStream);
            a.c.b.a(dataOutputStream);
        }
    }

    public static void a(Graphics graphics, byte by, int n2, int n3) {
        a.c.b.a(graphics, H, by * I, 0, I, H.getHeight(), 0, n2, n3, 20);
    }

    public static f[] a(f[] fArray) {
        if (fArray == null) {
            return null;
        }
        int n2 = 0;
        for (int i2 = 0; i2 < fArray.length; ++i2) {
            if (!fArray[i2].p()) continue;
            ++n2;
        }
        f[] fArray2 = new f[n2];
        int n3 = 0;
        for (int i3 = 0; i3 < fArray.length; ++i3) {
            if (!fArray[i3].p()) continue;
            fArray2[n3] = fArray[i3];
            if (++n3 >= n2) break;
        }
        return fArray2;
    }

    public static f.b b(int n2) {
        Object object = b.a.a(ay, n2);
        if (object == null) {
            if ((n2 = a.c.b.a(az, n2)) < 0) {
                object = null;
            } else {
                n2 = aA[n2];
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(aB, n2, aB.length - n2);
                object = new DataInputStream(byteArrayInputStream);
                f.b b2 = b.a.a((DataInputStream)object);
                a.c.b.a(byteArrayInputStream);
                a.c.b.a((InputStream)object);
                ay = b.a.a(ay, b2);
                object = b2;
            }
        }
        return object;
    }

    private static f.b[] a(f.b[] bArray, f.b b2) {
        if (b2 == null) {
            return bArray;
        }
        f.b[] bArray2 = null;
        if (bArray == null) {
            f.b[] bArray3 = new f.b[1];
            bArray2 = bArray3;
            bArray3[0] = b2;
        } else {
            int n2;
            bArray2 = new f.b[bArray.length + 1];
            for (n2 = 0; n2 < bArray.length && bArray[n2].q < b2.q; ++n2) {
                bArray2[n2] = bArray[n2];
            }
            bArray2[n2] = b2;
            ++n2;
            while (n2 < bArray2.length) {
                bArray2[n2] = bArray[n2 - 1];
                ++n2;
            }
        }
        return bArray2;
    }

    public static void e(short s) {
        if (ay == null) {
            return;
        }
        for (int i2 = 0; i2 < ay.length; ++i2) {
            if (s != b.a.ay[i2].q) continue;
            if (ay.length == 1) {
                ay = null;
                return;
            }
            f.b[] bArray = new f.b[ay.length - 1];
            System.arraycopy(ay, 0, bArray, 0, i2);
            System.arraycopy(ay, i2 + 1, bArray, i2, ay.length - i2 - 1);
            return;
        }
    }

    public static f.c c(int n2) {
        f.b b2 = b.a.b(n2);
        if (b2 == null || b2.r != 12) {
            return null;
        }
        return (f.c)b2;
    }

    public static f.c d(int n2) {
        f.b b2 = b.a.a((f.b[])s, n2);
        if (b2 == null) {
            return null;
        }
        return (f.c)b2;
    }

    private static f.b a(f.b[] bArray, int n2) {
        if (bArray == null) {
            return null;
        }
        int n3 = 0;
        int n4 = bArray.length - 1;
        int n5 = 0;
        while (n3 <= n4) {
            n5 = (n3 + n4) / 2;
            if (bArray[n5].q == n2) {
                return bArray[n5];
            }
            if (bArray[n5].q < n2) {
                n3 = n5 + 1;
                continue;
            }
            n4 = n5 - 1;
        }
        return null;
    }

    public static int a(int n2, int n3) {
        f.b b2 = b.a.b(n2);
        return b.a.b(b2, n3);
    }

    public static boolean b(int n2, int n3) {
        if (n2 == -1) {
            return C + n3 >= 0;
        }
        f.b b2 = b.a.b(n2);
        if (b2 == null || n3 > 255) {
            return false;
        }
        int n4 = 0;
        if (n3 == 0) {
            return true;
        }
        if (n3 > 0) {
            if (b2.c()) {
                n2 = b.a.b(b2);
                if (n2 < 0) {
                    return true;
                }
                n4 = b.a.aC[n2].c;
                return n4 + n3 <= 255;
            }
            n2 = 0;
            for (int i2 = 0; i2 < aC.length; ++i2) {
                if (aC[i2] != null) continue;
                ++n2;
            }
            return n2 + n3 <= 256;
        }
        n4 = b.a.i(n2);
        return n4 + n3 >= 0;
    }

    public static boolean e(int n2) {
        if (n2 < 0 || n2 >= aC.length) {
            return false;
        }
        for (int i2 = 0; i2 < A.length; ++i2) {
            if (A[i2].j(n2) < 0) continue;
            return true;
        }
        return false;
    }

    public static int[] a(byte by, boolean bl) {
        Vector<Integer> vector = new Vector<Integer>();
        for (int i2 = 0; i2 < aC.length; ++i2) {
            if (aC[i2] == null || b.a.aC[i2].a == null || by != 0 && b.a.aC[i2].a.d() != by || b.a.e(i2)) continue;
            vector.addElement(new Integer(i2));
        }
        if (vector.isEmpty()) {
            return null;
        }
        int[] nArray = new int[vector.size()];
        for (by = 0; by < nArray.length; by = (byte)(by + 1)) {
            nArray[by] = (Integer)vector.elementAt(by);
        }
        return nArray;
    }

    public static int[] b(byte by, boolean bl) {
        Vector<Integer> vector = new Vector<Integer>();
        for (int i2 = 0; i2 < aC.length; ++i2) {
            if (aC[i2] == null || b.a.aC[i2].a == null || b.a.aC[i2].a.r != by || b.a.e(i2)) continue;
            vector.addElement(new Integer(i2));
        }
        if (vector.isEmpty()) {
            return null;
        }
        int[] nArray = new int[vector.size()];
        for (by = 0; by < nArray.length; by = (byte)(by + 1)) {
            nArray[by] = (Integer)vector.elementAt(by);
        }
        return nArray;
    }

    public static boolean c(int n2, int n3) {
        f.b b2 = b.a.b(n2);
        return b.a.a(b2, n3);
    }

    public static boolean a(f.b b2, int n2) {
        if (b2 == null || n2 <= 0) {
            return false;
        }
        int n3 = 0;
        boolean bl = false;
        if (b2.c()) {
            n3 = b.a.b(b2);
            bl = b.a.d(n3, n2);
        } else {
            int n4 = 0;
            for (int i2 = 0; i2 < n2; ++i2) {
                n3 = b.a.b(b2);
                bl = b.a.d(n3, 1);
                for (int i3 = 0; i3 < A.length; ++i3) {
                    n4 = A[i3].j(n3);
                    if (n4 < 0) continue;
                    A[i3].f(n4);
                }
            }
        }
        return bl;
    }

    public static int b(f.b b2, int n2) {
        if (b2 == null || n2 <= 0) {
            return -1;
        }
        int n3 = -1;
        if (b2.c()) {
            n3 = b.a.b(b2);
            if (n3 < 0) {
                a.f.a a2;
                n3 = b.a.A();
                if (n3 < 0) {
                    return -2;
                }
                n2 = n2 > 255 ? 255 : n2;
                b.a.aC[n3] = a2 = new a.f.a(b2, n2);
            } else {
                b.a.aC[n3].c = a.c.b.b(b.a.aC[n3].c, n2, 255, 0);
            }
        } else {
            for (int i2 = 0; i2 < n2; ++i2) {
                a.f.a a3;
                n3 = b.a.A();
                if (n3 < 0) {
                    return -1;
                }
                b.a.aC[n3] = a3 = new a.f.a(b2, 1);
            }
        }
        return n3;
    }

    private static int A() {
        for (int i2 = 0; i2 < aC.length; ++i2) {
            if (aC[i2] != null) continue;
            return i2;
        }
        return -1;
    }

    public static f.b f(int n2) {
        if (n2 < 0 || n2 >= aC.length || aC[n2] == null) {
            return null;
        }
        return b.a.aC[n2].a;
    }

    public static f.a g(int n2) {
        f.b b2 = b.a.f(n2);
        if (b2 == null || !b2.e()) {
            return null;
        }
        return (f.a)b2;
    }

    public static int h(int n2) {
        if (n2 < 0 || n2 >= aC.length || aC[n2] == null) {
            return 0;
        }
        return b.a.aC[n2].c;
    }

    private static int b(f.b b2) {
        if (b2 == null || aC == null) {
            return -1;
        }
        for (int i2 = aC.length - 1; i2 >= 0; --i2) {
            if (aC[i2] == null || b.a.aC[i2].a == null || b.a.aC[i2].a != b2) continue;
            return i2;
        }
        return -1;
    }

    public static int a(f.b b2) {
        if (b2 == null) {
            return 0;
        }
        int n2 = 0;
        if (b2.c()) {
            int n3 = b.a.b(b2);
            if (n3 < 0 || n3 >= aC.length || aC[n3] == null) {
                return 0;
            }
            n2 = b.a.aC[n3].c;
        } else {
            for (int i2 = 0; i2 < aC.length; ++i2) {
                if (aC[i2] == null || b.a.aC[i2].a == null || b.a.aC[i2].a != b2) continue;
                ++n2;
            }
        }
        return n2;
    }

    public static int i(int n2) {
        f.b b2 = b.a.b(n2);
        return b.a.a(b2);
    }

    public static void i() {
        if (aE == null) {
            return;
        }
        try {
            aE.start();
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            b.a.j();
            return;
        }
    }

    public static void j() {
        if (aE == null) {
            return;
        }
        try {
            aE.stop();
            aE.deallocate();
            aE.close();
            aE = null;
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public static void b(byte by) {
        if (by < 0) {
            by = 0;
        } else if (by > 100) {
            by = (byte)100;
        }
        if (by == m) {
            return;
        }
        m = by;
        if (by == 0) {
            b.a.j();
            return;
        }
        if (aE == null && by > 0) {
            b.a.a(ao, -1, (int)m);
            return;
        }
        VolumeControl volumeControl = (VolumeControl)aE.getControl("VolumeControl");
        volumeControl.setLevel((int)by);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void a(int n2, int n3, int n4) {
        InputStream inputStream = null;
        try {
            block14: {
                block13: {
                    if (n2 < 0 || n4 <= 0) {
                        b.a.j();
                        if (n2 < 0) {
                            ao = -1;
                        }
                        a.c.b.a(null);
                        return;
                    }
                    if (n2 == ao && aE != null) break block13;
                    if (aE != null) {
                        b.a.j();
                    }
                    if ((inputStream = main.a.a.getClass().getResourceAsStream("/midi/" + n2 + ".mid")) == null) break block14;
                    aE = Manager.createPlayer((InputStream)inputStream, (String)"audio/midi");
                    aE.prefetch();
                    aE.setLoopCount(n3);
                    VolumeControl volumeControl = (VolumeControl)aE.getControl("VolumeControl");
                    volumeControl.setLevel(n4);
                    aE.start();
                }
                ao = n2;
                return;
            }
            a.c.b.a(inputStream);
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
        finally {
            a.c.b.a(inputStream);
        }
    }

    public static void j(int n2) {
        if ((C += n2) > 65534) {
            C = 65534;
            return;
        }
        if (C < 0) {
            C = 0;
        }
    }

    public static void a(int n2, byte by, int n3) {
        switch (by) {
            case 1: {
                by = (byte)n3;
                if (n2 == -1) {
                    C = a.c.b.b(0, by, 65534, 0);
                    break;
                }
                f.b b2 = b.a.b(n2);
                f.b b3 = b2;
                b3 = b2;
                int n4 = b.a.b(b2);
                if (n4 >= 0) {
                    b.a.aC[n4].c = a.c.b.b(0, by, 255, 0);
                    if (b.a.aC[n4].c == 0) {
                        b.a.d(n4, 999);
                    }
                }
                return;
            }
            case 0: {
                if (n2 == -1) {
                    C = a.c.b.b(C, n3, 65534, 0);
                    return;
                }
                if (n3 > 0) {
                    b.a.a(n2, n3);
                    return;
                }
                if (n3 >= 0) break;
                b.a.c(n2, -n3);
            }
        }
    }

    public static boolean d(int n2, int n3) {
        if (n2 < 0 || n2 >= aC.length || n3 <= 0 || aC[n2] == null) {
            return false;
        }
        b.a.aC[n2].c -= n3;
        if (b.a.aC[n2].c <= 0) {
            b.a.aC[n2] = null;
        }
        return true;
    }

    public static a.e.c a(String string) {
        Object object;
        Object object2;
        block12: {
            if (string == null) {
                return null;
            }
            object2 = null;
            if (w != null) {
                object2 = w.a(string);
            }
            if (object2 != null) {
                return object2;
            }
            object2 = string;
            if (object2 == null || j == null) {
                object = null;
            } else {
                for (int i2 = 0; i2 < j.length; ++i2) {
                    if (b.a.j[i2].e == null || !b.a.j[i2].d.equals(object2)) continue;
                    object = b.a.j[i2].e;
                    break block12;
                }
                object = object2 = null;
            }
        }
        if (object != null) {
            return object2;
        }
        if (main.a.a.k != null) {
            object2 = c.a.g();
            b b2 = null;
            for (int i3 = ((Vector)object2).size() - 1; i3 >= 0; --i3) {
                b2 = (b)((Vector)object2).elementAt(i3);
                if (b2.d == null || !b2.d.equals(string) || b2.e == null) continue;
                return b2.e;
            }
        }
        if (main.a.a.k != null && main.a.a.k.a == 4) {
            object2 = (c.c)main.a.a.k;
            c[] cArray = ((c.c)object2).i();
            c c2 = null;
            if (cArray != null) {
                for (int i4 = 0; i4 < cArray.length; ++i4) {
                    c2 = cArray[i4];
                    if (c2 == null || c2.d == null || !c2.d.equals(string) || c2.e == null) continue;
                    return c2.e;
                }
            }
        }
        return null;
    }

    public static a.e.a b(String string) {
        int n2;
        e[] eArray;
        if (string == null || string.trim().length() == 0) {
            return null;
        }
        a.e.a a2 = null;
        if (j != null) {
            for (int i2 = 0; i2 < j.length; ++i2) {
                if (j[i2] == null || b.a.j[i2].e == null || (a2 = b.a.j[i2].e.a(string)) == null) continue;
                return a2;
            }
        }
        if (w != null && (eArray = w.i()) != null) {
            for (n2 = 0; n2 < eArray.length; ++n2) {
                if (eArray[n2] == null || eArray[n2].e == null || (a2 = eArray[n2].e.a(string)) == null) continue;
                return a2;
            }
        }
        if (main.a.a.k != null) {
            Vector vector = c.a.g();
            b b2 = null;
            for (int i3 = 0; i3 < vector.size(); ++i3) {
                b2 = (b)vector.elementAt(i3);
                if (b2.e == null || (a2 = b2.e.a(string)) == null) continue;
                return a2;
            }
        }
        if (main.a.a.k != null && main.a.a.k.a == 4) {
            c.c c2 = (c.c)main.a.a.k;
            for (n2 = 0; n2 < A.length; ++n2) {
                if (A[n2] == null || b.a.A[n2].e == null || (a2 = b.a.A[n2].e.a(string)) == null) continue;
                return a2;
            }
            c[] cArray = c2.i();
            c c3 = null;
            if (cArray != null) {
                for (int i4 = 0; i4 < cArray.length; ++i4) {
                    c3 = cArray[i4];
                    if (c3 == null || c3.e == null || (a2 = c3.e.a(string)) == null) continue;
                    return a2;
                }
            }
        }
        return null;
    }

    public static a.e.c c(String string) {
        if (string == null || string.trim().length() == 0) {
            return null;
        }
        a.e.c c2 = null;
        for (int i2 = 0; i2 < aF.size(); ++i2) {
            c2 = (a.e.c)aF.elementAt(i2);
            if (!c2.a.equals(string)) continue;
            return c2;
        }
        return null;
    }

    public static void k() {
        aF.removeAllElements();
    }

    public static void a(a.e.c c2) {
        if (c2 == null) {
            return;
        }
        aF.addElement(c2);
    }

    public static String k(int n2) {
        return o[n2];
    }

    public static boolean l() {
        return aq >= 2;
    }

    public static boolean m() {
        return ar >= 2;
    }

    public static boolean n() {
        return ap || as || d;
    }

    public static boolean o() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeByte(aq);
            dataOutputStream.writeByte(ar);
            dataOutputStream.writeBoolean(ap);
            dataOutputStream.writeBoolean(as);
            dataOutputStream.writeBoolean(at);
            dataOutputStream.writeBoolean(au);
            int n2 = e.l.h.size();
            dataOutputStream.writeByte(n2);
            Integer n3 = null;
            --n2;
            while (n2 >= 0) {
                n3 = (Integer)e.l.h.elementAt(n2);
                dataOutputStream.writeByte(n3.byteValue());
                --n2;
            }
            byte[] byArray = byteArrayOutputStream.toByteArray();
            a.c.b.a("swd_5_gil_pl", byArray);
            return true;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return false;
        }
        finally {
            a.c.b.a(byteArrayOutputStream);
            a.c.b.a(dataOutputStream);
        }
    }

    public static void p() {
        Object object = a.c.b.f("swd_5_gil_pl");
        if (object == null) {
            return;
        }
        object = new ByteArrayInputStream((byte[])object);
        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
        try {
            aq = dataInputStream.readByte();
            ar = dataInputStream.readByte();
            ap = dataInputStream.readBoolean();
            as = dataInputStream.readBoolean();
            at = dataInputStream.readBoolean();
            au = dataInputStream.readBoolean();
            int n2 = dataInputStream.readByte();
            byte by = 0;
            for (int i2 = 0; i2 < n2; ++i2) {
                by = dataInputStream.readByte();
                e.l.c(by);
            }
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
        finally {
            a.c.b.a((InputStream)object);
            a.c.b.a(dataInputStream);
        }
    }

    private static g a(byte[] object) {
        g g2;
        DataInputStream dataInputStream;
        block6: {
            if (object == null || ((byte[])object).length == 0) {
                return null;
            }
            object = new ByteArrayInputStream((byte[])object);
            dataInputStream = new DataInputStream((InputStream)object);
            try {
                dataInputStream.readByte();
                boolean bl = false;
                dataInputStream.readByte();
                dataInputStream.readBoolean();
                dataInputStream.readBoolean();
                g2 = new g();
                if (g2.b(dataInputStream)) break block6;
                g2 = null;
                return null;
            }
            catch (IOException iOException) {
                IOException iOException2 = iOException;
                iOException.printStackTrace();
                return null;
            }
            finally {
                a.c.b.a((InputStream)object);
                a.c.b.a(dataInputStream);
            }
        }
        a.c.b.a((InputStream)object);
        a.c.b.a(dataInputStream);
        return g2;
    }

    private static byte[] a(String object, String object2, String object3, String string, boolean bl, boolean bl2) {
        if (object3 == null || string == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("gamecode=" + (String)object);
        stringBuffer.append("&saveid=" + (String)object2);
        stringBuffer.append("&u=" + (String)object3);
        stringBuffer.append("&p=" + string);
        object = "http://59.42.254.3/gs/downstore.do?";
        object2 = new Hashtable<String, String>();
        ((Hashtable)object2).put("Content-Type", "application/x-www-form-urlencoded");
        object3 = null;
        if (bl2) {
            object3 = new h.a((String)object, "POST", stringBuffer.toString(), (Hashtable)object2);
        } else {
            object = (String)object + stringBuffer.toString();
            object3 = new h.a((String)object, "GET", null, (Hashtable)object2);
        }
        object = null;
        try {
            object = h.a.a((h.a)object3, bl, null);
        }
        catch (Exception exception) {
            object = h.a.a((h.a)object3, !bl, null);
        }
        object2 = null;
        if (object != null && ((Hashtable)object).containsKey("contType")) {
            object2 = (String)((Hashtable)object).get("contType");
        }
        if (object2 == null || ((String)object2).indexOf("audio/midi") == -1) {
            try {
                object = h.a.a((h.a)object3, bl, null);
            }
            catch (Exception exception) {
                object = h.a.a((h.a)object3, !bl, null);
            }
        }
        int n2 = 0;
        if (object == null || !((Hashtable)object).containsKey("respCode")) {
            return null;
        }
        try {
            n2 = Integer.parseInt(((Hashtable)object).get("respCode").toString());
            if (n2 == 200 && ((Hashtable)object).containsKey("data")) {
                if (((Hashtable)object).get("data") == null) {
                    return null;
                }
                return (byte[])((Hashtable)object).get("data");
            }
            return null;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return null;
        }
    }

    public static g a(boolean bl) {
        d.b.a(MainMIDlet.a, (Displayable)main.a.a, "0118");
        boolean bl2 = true;
        Object object = d.b.a();
        if (((String[])object).length < 3 || object[2] == null || object[2].trim().length() == 0) {
            bl2 = false;
        }
        if (!bl2) {
            return null;
        }
        if (((String[])object).length < 4 || object[0] == null || object[1] == null || object[3] == null) {
            return null;
        }
        g g2 = b.a.a(bl, "0117", "swd4", object[0].trim(), object[1].trim(), true);
        if (g2 == null) {
            return null;
        }
        boolean bl3 = bl;
        bl = true;
        String string = object[1].trim();
        String string2 = object[0].trim();
        String string3 = "swd3";
        object = "0110";
        bl = bl3;
        Object object2 = b.a.a((String)object, string3, string2, string, true, bl);
        object2 = b.a.a(object2);
        g2.a((g)object2);
        return g2;
    }

    private static g a(boolean bl, String object, String object2, String object3, String object4, boolean n2) {
        Object object5 = b.a.a((String)object, (String)object2, (String)object3, (String)object4, true, bl);
        object5 = new ByteArrayInputStream((byte[])object5);
        object = new DataInputStream((InputStream)object5);
        object2 = null;
        try {
            int n3 = ((DataInputStream)object).readInt();
            object2 = new byte[1024];
            object4 = new ByteArrayOutputStream();
            n2 = 0;
            while ((n2 = ((DataInputStream)object).read((byte[])object2)) != -1) {
                ((ByteArrayOutputStream)object4).write((byte[])object2, 0, n2);
            }
            object2 = ((ByteArrayOutputStream)object4).toByteArray();
            if (n3 < 0) {
                c.e.f = new String((byte[])object2, "UTF-8");
                return null;
            }
        }
        catch (IOException iOException) {
            object3 = iOException;
            iOException.printStackTrace();
            object2 = null;
            return null;
        }
        finally {
            a.c.b.a((InputStream)object5);
            a.c.b.a((InputStream)object);
        }
        return b.a.a((byte[])object2);
    }

    public static void e(int n2, int n3) {
        int n4;
        if (n3 == 0) {
            return;
        }
        int n5 = n2;
        if (!t.isEmpty()) {
            int[] nArray = null;
            for (int i2 = 0; i2 < t.size(); ++i2) {
                nArray = (int[])t.elementAt(i2);
                if (nArray == null || nArray.length != 2 || nArray[0] != n5) continue;
                n4 = i2;
                break;
            }
        } else {
            n4 = n5 = -1;
        }
        if (n4 < 0) {
            t.addElement(new int[]{n2, n3});
            return;
        }
        if (n5 < t.size()) {
            int[] nArray = (int[])t.elementAt(n5);
            int[] nArray2 = nArray;
            nArray[1] = nArray[1] + n3;
            t.setElementAt(nArray2, n5);
        }
    }

    public static int l(int n2) {
        if (t == null || t.size() == 0) {
            return 0;
        }
        int[] nArray = null;
        for (int i2 = 0; i2 < t.size(); ++i2) {
            nArray = (int[])t.elementAt(i2);
            if (nArray[0] != n2) continue;
            return nArray[1];
        }
        return 0;
    }

    public static int b(f[] fArray) {
        int n2;
        if (fArray == null || fArray.length == 0) {
            return 1;
        }
        int n3 = 0;
        int n4 = 0;
        for (n2 = 0; n2 < 3; ++n2) {
            if (fArray[n2] == null) continue;
            ++n3;
            n4 += fArray[n2].I;
        }
        if (n3 == 0) {
            return 1;
        }
        n2 = n4 / n3;
        if (n2 <= 0) {
            return 1;
        }
        return n2;
    }

    public static a.f.a[] q() {
        return aC;
    }

    public static void a(a.f.a[] aArray) {
        aC = aArray;
    }

    public static void r() {
        aC = new a.f.a[256];
    }

    public static int s() {
        int n2 = 0;
        for (int i2 = 0; i2 < aC.length; ++i2) {
            if (aC[i2] != null) continue;
            ++n2;
        }
        return n2;
    }

    public static f d(String string) {
        if (string == null || A == null) {
            return null;
        }
        for (int i2 = 0; i2 < A.length; ++i2) {
            if (!string.equals(b.a.A[i2].k)) continue;
            return A[i2];
        }
        return null;
    }

    public static boolean t() {
        return ap;
    }

    public static void b(boolean bl) {
        ap = true;
    }

    public static int u() {
        return aq;
    }

    public static void m(int n2) {
        aq = 2;
    }

    public static int v() {
        return ar;
    }

    public static void n(int n2) {
        ar = 2;
    }

    public static boolean w() {
        return at || ap;
    }

    public static void c(boolean bl) {
        at = true;
    }

    public static boolean x() {
        return au || ap;
    }

    public static void d(boolean bl) {
        au = true;
    }

    public static boolean o(int n2) {
        return ap || e.l.d(n2);
    }

    public static void y() {
        for (int i2 = 0; i2 < j.length; ++i2) {
            if (j[i2].p()) continue;
            b.a.j[i2].e = null;
        }
    }

    public static void z() {
        if (j == null) {
            return;
        }
        for (int i2 = 0; i2 < j.length; ++i2) {
            if (j[i2] == null || !j[i2].p() || b.a.j[i2].e != null) continue;
            b.a.j[i2].e = a.e.c.b(b.a.j[i2].d);
        }
    }

    static {
        String[] stringArray = new String[]{"\u97f3\u4e50\u72b6\u6001", "\u7b80\u5355\u6218\u6597", "\u52a8\u6001\u5730\u56fe", "\u83dc\u5355\u6548\u679c"};
        l = new boolean[]{false, false, true, true};
        m = (byte)100;
        n = false;
        o = null;
        p = new Hashtable();
        av = null;
        aw = null;
        ax = null;
        q = null;
        r = (short)-3;
        ay = null;
        az = null;
        aA = null;
        aB = null;
        s = null;
        t = new Vector();
        u = 2;
        v = (short)-1;
        w = null;
        x = 1;
        y = 20;
        z = 20;
        A = null;
        B = new byte[256];
        C = 0;
        aC = new a.f.a[255];
        D = null;
        E = 0;
        F = null;
        G = 0;
        H = null;
        I = 0;
        J = null;
        K = null;
        L = null;
        M = null;
        N = null;
        O = null;
        P = null;
        Q = null;
        R = null;
        S = null;
        T = null;
        U = null;
        V = null;
        W = null;
        X = null;
        Y = 1;
        Z = null;
        aa = null;
        ab = null;
        ac = null;
        ad = null;
        ae = null;
        af = null;
        ag = null;
        ah = false;
        ai = 0;
        aj = (byte)-1;
        ak = 0;
        al = -1;
        am = -1;
        an = 0;
        aD = new short[]{594, 599, 603, 607};
        aE = null;
        ao = 3;
        aF = new Vector();
    }
}

