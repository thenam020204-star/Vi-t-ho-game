/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 *  javax.microedition.media.Manager
 *  javax.microedition.media.Player
 */
package a;

import a.a;
import a.b;
import a.c;
import a.d;
import a.g;
import d.k;
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
import main.MainMIDlet;

public final class f {
    private static boolean aq = false;
    private static int ar = 1;
    private static int as = 1;
    private static boolean at = false;
    private static boolean au = false;
    private static boolean av = false;
    public static boolean a = false;
    public static short b = 0;
    public static b.f.b c = null;
    public static boolean d = true;
    public static final int[] e = new int[]{0xFFFA00, 0xFFFA00, 16748800, 16748800};
    public static final int[] f = new int[]{65310, 65310, 41491, 41491};
    public static final int[] g = new int[]{982271, 982271, 2236156, 2236156};
    public static final int[] h = new int[]{0xFFFA00, 0xFFFA00, 13676807, 13676807};
    public static boolean i = true;
    public static g[] j = null;
    public static int[][] k = null;
    public static b.a.c[] l = null;
    public static boolean[] m;
    public static boolean n;
    public static String[] o;
    public static Hashtable p;
    private static short[] aw;
    private static int[] ax;
    private static byte[] ay;
    public static b.f.b q;
    public static short r;
    private static f.a[] az;
    private static short[] aA;
    private static int[] aB;
    private static byte[] aC;
    public static f.c[] s;
    public static Vector t;
    public static int u;
    public static short v;
    public static b.c.a w;
    public static int x;
    public static int y;
    public static int z;
    public static g[] A;
    public static byte[] B;
    public static int C;
    private static b.d.a[] aD;
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
    public static String[][] X;
    public static String[] Y;
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
    private static short[] aE;
    private static Player aF;
    public static int ao;
    public static int ap;
    private static Vector aG;

    public static void a() {
        if (X == null || X.length == 0) {
            return;
        }
        Y = X[b.e.a.c(X.length)];
    }

    public static g a(short s) {
        if (j == null) {
            return null;
        }
        for (int i2 = 0; i2 < j.length; ++i2) {
            if (a.f.j[i2].n != s) continue;
            return j[i2];
        }
        return null;
    }

    public static g b(short s) {
        if (A == null) {
            return null;
        }
        for (int i2 = 0; i2 < A.length; ++i2) {
            if (a.f.A[i2].n != s) continue;
            return A[i2];
        }
        return null;
    }

    public static void a(g g2) {
        if (g2 == null) {
            return;
        }
        g2.a(true);
        g2.f = true;
        A = a.f.a(j);
        if (g2.j != null) {
            return;
        }
        g2.j = l[g2.n];
    }

    public static void b(g g2) {
        if (g2 == null) {
            return;
        }
        g2.a(false);
        g2.j = null;
        A = a.f.a(j);
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
                            b.e.a.a(null);
                            break block10;
                        }
                        dataInputStream = new DataInputStream(inputStream);
                        short s2 = dataInputStream.readByte();
                        k = new int[s2][a.g.D.length];
                        for (s = 0; s < s2; ++s) {
                            for (n2 = 0; n2 < k[s].length; ++n2) {
                                a.f.k[s][n2] = dataInputStream.readShort();
                            }
                        }
                        j = new g[s2];
                        for (n2 = 0; n2 < s2; ++n2) {
                            a.f.j[n2] = new g();
                            j[n2].a(dataInputStream);
                            a.f.j[n2].B[2] = j[n2].y();
                            a.f.j[n2].B[4] = j[n2].z();
                            a.f.j[n2].B[6] = j[n2].A();
                            a.f.j[n2].j = l[a.f.j[n2].n];
                            j[n2].a(aE[a.f.j[n2].n]);
                            s = j[n2].e(a.f.j[n2].I);
                            j[n2].b(s);
                        }
                        b.e.a.a(dataInputStream);
                        break block11;
                    }
                    catch (Exception exception) {
                        Exception exception2 = exception;
                        exception.printStackTrace();
                        j = null;
                        break block12;
                    }
                }
                b.e.a.a(inputStream);
                break block12;
            }
            b.e.a.a(inputStream);
            break block12;
            finally {
                b.e.a.a(dataInputStream);
                b.e.a.a(inputStream);
            }
        }
        A = a.f.a(j);
        x = 169;
        y = 7;
        z = 10;
    }

    public static byte a(byte by) {
        int n2 = b.e.a.a((int)by);
        by = (byte)n2;
        if (n2 < 0 || by > 255) {
            return 0;
        }
        return B[by];
    }

    public static void a(byte by, byte by2) {
        int n2 = b.e.a.a((int)by);
        by = (byte)n2;
        if (n2 < 0 || by > 255) {
            return;
        }
        a.f.B[by] = by2;
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
                        b.e.a.a(null);
                        break block9;
                    }
                    dataInputStream = new DataInputStream(inputStream);
                    int n2 = dataInputStream.readByte();
                    a.g.d = new short[n2][];
                    for (int i2 = 0; i2 < n2; ++i2) {
                        int n3 = b.e.a.a((int)dataInputStream.readByte());
                        a.g.d[i2] = new short[n3];
                        for (int i3 = 0; i3 < n3; ++i3) {
                            a.g.d[i2][i3] = dataInputStream.readShort();
                        }
                    }
                    b.e.a.a(dataInputStream);
                    break block10;
                }
                catch (IOException iOException) {
                    IOException iOException2 = iOException;
                    iOException.printStackTrace();
                    return;
                }
            }
            b.e.a.a(inputStream);
            return;
        }
        b.e.a.a(inputStream);
        return;
        finally {
            b.e.a.a(dataInputStream);
            b.e.a.a(inputStream);
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
            aA = new short[n3];
            aB = new int[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                a.f.aA[n2] = dataInputStream.readShort();
                a.f.aB[n2] = dataInputStream.readInt();
            }
            n2 = dataInputStream.readInt();
            aC = new byte[n2];
            dataInputStream.read(aC);
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
        finally {
            b.e.a.a(inputStream);
            b.e.a.a(dataInputStream);
        }
        a.f.a(aA, aB);
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
        f.a[] aArray = a.f.e("/item15.dat");
        if (aArray == null) {
            return;
        }
        s = new f.c[aArray.length];
        for (int i2 = 0; i2 < aArray.length; ++i2) {
            a.f.s[i2] = (f.c)aArray[i2];
        }
        aArray = s;
        if (s == null || aArray.length <= 1) {
            return;
        }
        f.a a2 = null;
        for (int i3 = 1; i3 < aArray.length; ++i3) {
            for (int i4 = aArray.length - 1; i4 >= i3; --i4) {
                if (aArray[i4].c >= aArray[i4 - 1].c) continue;
                a2 = aArray[i4 - 1];
                aArray[i4 - 1] = aArray[i4];
                aArray[i4] = a2;
            }
        }
    }

    private static f.a[] e(String object) {
        object = "".getClass().getResourceAsStream((String)object);
        if (object == null) {
            return null;
        }
        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
        try {
            short s = dataInputStream.readShort();
            f.a[] aArray = new f.a[s];
            for (int i2 = 0; i2 < aArray.length; ++i2) {
                aArray[i2] = a.f.b(dataInputStream);
            }
            f.a[] aArray2 = aArray;
            return aArray2;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return null;
        }
        finally {
            b.e.a.a(dataInputStream);
            b.e.a.a((InputStream)object);
        }
    }

    private static f.a b(DataInputStream object) {
        f.a a2 = null;
        byte by = 0;
        try {
            by = ((DataInputStream)object).readByte();
            switch (by) {
                case 1: 
                case 5: 
                case 12: 
                case 14: 
                case 15: {
                    a2 = new f.c(by);
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
                    a2 = new f.b(by);
                    break;
                }
                default: {
                    String cfr_ignored_0 = "\u672a\u77e5\u7269\u54c1\u7c7b\u578b:" + by;
                }
            }
            a2.a((DataInputStream)object);
            return a2;
        }
        catch (IOException iOException) {
            object = iOException;
            iOException.printStackTrace();
            return null;
        }
    }

    public static d c(short s) {
        for (int i2 = 0; i2 < aw.length; ++i2) {
            if (aw[i2] != s) continue;
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(ay, ax[i2], ay.length - ax[i2]);
            DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
            d d2 = new d();
            d2.a(dataInputStream);
            b.e.a.a(byteArrayInputStream);
            b.e.a.a(dataInputStream);
            return d2;
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
            b.e.a.a(inputStream);
            b.e.a.a(null);
            return;
        }
        try {
            int n2;
            dataInputStream = new DataInputStream(inputStream);
            int n3 = dataInputStream.readShort();
            aw = new short[n3];
            ax = new int[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                a.f.aw[n2] = dataInputStream.readShort();
                a.f.ax[n2] = dataInputStream.readInt();
            }
            n2 = dataInputStream.readInt();
            ay = new byte[n2];
            dataInputStream.read(ay);
        }
        catch (Exception exception) {
            try {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
            catch (Throwable throwable) {
                b.e.a.a(inputStream);
                b.e.a.a(dataInputStream);
                throw throwable;
            }
            b.e.a.a(inputStream);
            b.e.a.a(dataInputStream);
            return;
        }
        b.e.a.a(inputStream);
        b.e.a.a(dataInputStream);
        return;
    }

    public static c[] g() {
        Object object = b.e.a.e("sword3");
        if (object == null) {
            return new c[2];
        }
        object = new ByteArrayInputStream((byte[])object);
        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
        c[] cArray = a.f.c(dataInputStream);
        b.e.a.a((InputStream)object);
        b.e.a.a(dataInputStream);
        return cArray;
    }

    private static c[] c(DataInputStream dataInputStream) {
        try {
            short s = dataInputStream.readShort();
            if (s <= 0) {
                return new c[2];
            }
            c[] cArray = new c[s];
            for (int i2 = 0; i2 < cArray.length; ++i2) {
                if (dataInputStream.readBoolean()) continue;
                cArray[i2] = new c();
                if (cArray[i2].a(dataInputStream)) continue;
                return new c[2];
            }
            return cArray;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return new c[2];
        }
    }

    public static void a(int n2) {
        a.f.o();
        c c2 = new c();
        a.f.h();
        c2.c();
        c[] cArray = a.f.g();
        if (n2 < 0 || n2 >= cArray.length) {
            return;
        }
        cArray[n2] = c2;
        Object[] objectArray = cArray;
        a.f.o();
        objectArray = a.f.a(objectArray);
        b.e.a.a("sword3", (byte[])objectArray);
    }

    public static void h() {
        if (w == null) {
            return;
        }
        byte[] byArray = w.o();
        p.put(new Integer(a.f.w.k), byArray);
    }

    private static byte[] a(c[] cArray) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            if (cArray == null) {
                dataOutputStream.writeShort(0);
            } else {
                dataOutputStream.writeShort(cArray.length);
                for (int i2 = 0; i2 < cArray.length; ++i2) {
                    if (cArray[i2] == null) {
                        dataOutputStream.writeBoolean(true);
                        continue;
                    }
                    dataOutputStream.writeBoolean(false);
                    ((OutputStream)dataOutputStream).write(cArray[i2].a());
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
            b.e.a.a(byteArrayOutputStream);
            b.e.a.a(dataOutputStream);
        }
    }

    public static void a(Graphics graphics, byte by, int n2, int n3) {
        b.e.a.a(graphics, H, by * I, 0, I, H.getHeight(), 0, n2, n3, 20);
    }

    public static g[] a(g[] gArray) {
        if (gArray == null) {
            return null;
        }
        int n2 = 0;
        for (int i2 = 0; i2 < gArray.length; ++i2) {
            if (!gArray[i2].h()) continue;
            ++n2;
        }
        g[] gArray2 = new g[n2];
        int n3 = 0;
        for (int i3 = 0; i3 < gArray.length; ++i3) {
            if (!gArray[i3].h()) continue;
            gArray2[n3] = gArray[i3];
            if (++n3 >= n2) break;
        }
        return gArray2;
    }

    public static f.a b(int n2) {
        Object object = a.f.a(az, n2);
        if (object == null) {
            if ((n2 = b.e.a.a(aA, n2)) < 0) {
                object = null;
            } else {
                n2 = aB[n2];
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(aC, n2, aC.length - n2);
                object = new DataInputStream(byteArrayInputStream);
                f.a a2 = a.f.b((DataInputStream)object);
                b.e.a.a(byteArrayInputStream);
                b.e.a.a((InputStream)object);
                az = a.f.a(az, a2);
                object = a2;
            }
        }
        return object;
    }

    private static f.a[] a(f.a[] aArray, f.a a2) {
        if (a2 == null) {
            return aArray;
        }
        f.a[] aArray2 = null;
        if (aArray == null) {
            f.a[] aArray3 = new f.a[1];
            aArray2 = aArray3;
            aArray3[0] = a2;
        } else {
            int n2;
            aArray2 = new f.a[aArray.length + 1];
            for (n2 = 0; n2 < aArray.length && aArray[n2].c < a2.c; ++n2) {
                aArray2[n2] = aArray[n2];
            }
            aArray2[n2] = a2;
            ++n2;
            while (n2 < aArray2.length) {
                aArray2[n2] = aArray[n2 - 1];
                ++n2;
            }
        }
        return aArray2;
    }

    public static void d(short s) {
        if (az == null) {
            return;
        }
        for (int i2 = 0; i2 < az.length; ++i2) {
            if (s != a.f.az[i2].c) continue;
            if (az.length == 1) {
                az = null;
                return;
            }
            f.a[] aArray = new f.a[az.length - 1];
            System.arraycopy(az, 0, aArray, 0, i2);
            System.arraycopy(az, i2 + 1, aArray, i2, az.length - i2 - 1);
            return;
        }
    }

    public static f.c c(int n2) {
        f.a a2 = a.f.b(n2);
        if (a2 == null || a2.d != 12) {
            return null;
        }
        return (f.c)a2;
    }

    public static f.c d(int n2) {
        f.a a2 = a.f.a((f.a[])s, n2);
        if (a2 == null) {
            return null;
        }
        return (f.c)a2;
    }

    private static f.a a(f.a[] aArray, int n2) {
        if (aArray == null) {
            return null;
        }
        int n3 = 0;
        int n4 = aArray.length - 1;
        int n5 = 0;
        while (n3 <= n4) {
            n5 = (n3 + n4) / 2;
            if (aArray[n5].c == n2) {
                return aArray[n5];
            }
            if (aArray[n5].c < n2) {
                n3 = n5 + 1;
                continue;
            }
            n4 = n5 - 1;
        }
        return null;
    }

    public static int a(int n2, int n3) {
        f.a a2 = a.f.b(n2);
        return a.f.b(a2, n3);
    }

    public static boolean b(int n2, int n3) {
        if (n2 == -1) {
            return C + n3 >= 0;
        }
        f.a a2 = a.f.b(n2);
        if (a2 == null || n3 > 255) {
            return false;
        }
        int n4 = 0;
        if (n3 == 0) {
            return true;
        }
        if (n3 > 0) {
            if (a2.b()) {
                n2 = a.f.b(a2);
                if (n2 < 0) {
                    return true;
                }
                n4 = a.f.aD[n2].c;
                return n4 + n3 <= 255;
            }
            n2 = 0;
            for (int i2 = 0; i2 < aD.length; ++i2) {
                if (aD[i2] != null) continue;
                ++n2;
            }
            return n2 + n3 <= 256;
        }
        a2 = a.f.b(n2);
        n4 = a.f.a(a2);
        return n4 + n3 >= 0;
    }

    public static boolean e(int n2) {
        if (n2 < 0 || n2 >= aD.length) {
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
        for (int i2 = 0; i2 < aD.length; ++i2) {
            if (aD[i2] == null || a.f.aD[i2].a == null || by != 0 && a.f.aD[i2].a.c() != by || a.f.e(i2)) continue;
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
        for (int i2 = 0; i2 < aD.length; ++i2) {
            if (aD[i2] == null || a.f.aD[i2].a == null || a.f.aD[i2].a.d != by || a.f.e(i2)) continue;
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
        f.a a2 = a.f.b(n2);
        return a.f.a(a2, n3);
    }

    public static boolean a(f.a a2, int n2) {
        if (a2 == null || n2 <= 0) {
            return false;
        }
        int n3 = 0;
        boolean bl = false;
        if (a2.b()) {
            n3 = a.f.b(a2);
            bl = a.f.e(n3, n2);
        } else {
            int n4 = 0;
            for (int i2 = 0; i2 < n2; ++i2) {
                n3 = a.f.b(a2);
                bl = a.f.e(n3, 1);
                for (int i3 = 0; i3 < A.length; ++i3) {
                    n4 = A[i3].j(n3);
                    if (n4 < 0) continue;
                    A[i3].f(n4);
                }
            }
        }
        return bl;
    }

    public static int b(f.a a2, int n2) {
        if (a2 == null || n2 <= 0) {
            return -1;
        }
        int n3 = -1;
        if (a2.b()) {
            n3 = a.f.b(a2);
            if (n3 < 0) {
                b.d.a a3;
                n3 = a.f.y();
                if (n3 < 0) {
                    return -2;
                }
                n2 = n2 > 255 ? 255 : n2;
                a.f.aD[n3] = a3 = new b.d.a(a2, n2);
            } else {
                a.f.aD[n3].c = b.e.a.b(a.f.aD[n3].c, n2, 255, 0);
            }
        } else {
            for (int i2 = 0; i2 < n2; ++i2) {
                b.d.a a4;
                n3 = a.f.y();
                if (n3 < 0) {
                    return -1;
                }
                a.f.aD[n3] = a4 = new b.d.a(a2, 1);
            }
        }
        return n3;
    }

    private static int y() {
        for (int i2 = 0; i2 < aD.length; ++i2) {
            if (aD[i2] != null) continue;
            return i2;
        }
        return -1;
    }

    public static f.a f(int n2) {
        if (n2 < 0 || n2 >= aD.length || aD[n2] == null) {
            return null;
        }
        return a.f.aD[n2].a;
    }

    public static f.b g(int n2) {
        f.a a2 = a.f.f(n2);
        if (a2 == null || !a2.d()) {
            return null;
        }
        return (f.b)a2;
    }

    public static int h(int n2) {
        if (n2 < 0 || n2 >= aD.length || aD[n2] == null) {
            return 0;
        }
        return a.f.aD[n2].c;
    }

    private static int b(f.a a2) {
        if (a2 == null || aD == null) {
            return -1;
        }
        for (int i2 = aD.length - 1; i2 >= 0; --i2) {
            if (aD[i2] == null || a.f.aD[i2].a == null || a.f.aD[i2].a != a2) continue;
            return i2;
        }
        return -1;
    }

    public static int a(f.a a2) {
        if (a2 == null) {
            return 0;
        }
        int n2 = 0;
        if (a2.b()) {
            int n3 = a.f.b(a2);
            if (n3 < 0 || n3 >= aD.length || aD[n3] == null) {
                return 0;
            }
            n2 = a.f.aD[n3].c;
        } else {
            for (int i2 = 0; i2 < aD.length; ++i2) {
                if (aD[i2] == null || a.f.aD[i2].a == null || a.f.aD[i2].a != a2) continue;
                ++n2;
            }
        }
        return n2;
    }

    public static void i() {
        if (aF == null) {
            return;
        }
        try {
            aF.start();
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            a.f.j();
            return;
        }
    }

    public static void j() {
        if (aF == null) {
            return;
        }
        try {
            aF.stop();
            aF.deallocate();
            aF.close();
            aF = null;
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void d(int n2, int n3) {
        InputStream inputStream = null;
        try {
            block13: {
                block12: {
                    if (n2 < 0) {
                        a.f.j();
                        ao = -1;
                        b.e.a.a(null);
                        return;
                    }
                    if (!m[0] || n2 == ao && aF != null) break block12;
                    if (aF != null) {
                        a.f.j();
                    }
                    if ((inputStream = main.a.a.getClass().getResourceAsStream("/midi/" + n2 + ".mid")) == null) break block13;
                    aF = Manager.createPlayer((InputStream)inputStream, (String)"audio/midi");
                    aF.prefetch();
                    aF.setLoopCount(n3);
                    aF.start();
                }
                ao = n2;
                ap = n3;
                return;
            }
            b.e.a.a(inputStream);
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
        finally {
            b.e.a.a(inputStream);
        }
    }

    public static void i(int n2) {
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
                    C = b.e.a.b(0, by, 65534, 0);
                    break;
                }
                f.a a2 = a.f.b(n2);
                f.a a3 = a2;
                a3 = a2;
                int n4 = a.f.b(a2);
                if (n4 >= 0) {
                    a.f.aD[n4].c = b.e.a.b(0, by, 255, 0);
                    if (a.f.aD[n4].c == 0) {
                        a.f.e(n4, 999);
                    }
                }
                return;
            }
            case 0: {
                if (n2 == -1) {
                    C = b.e.a.b(C, n3, 65534, 0);
                    return;
                }
                if (n3 > 0) {
                    a.f.a(n2, n3);
                    return;
                }
                if (n3 >= 0) break;
                a.f.c(n2, -n3);
            }
        }
    }

    public static boolean e(int n2, int n3) {
        if (n2 < 0 || n2 >= aD.length || n3 <= 0 || aD[n2] == null) {
            return false;
        }
        a.f.aD[n2].c -= n3;
        if (a.f.aD[n2].c <= 0) {
            a.f.aD[n2] = null;
        }
        return true;
    }

    public static b.a.c a(String string) {
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
            if (object2 == null || l == null) {
                object = null;
            } else {
                for (int i2 = 0; i2 < l.length; ++i2) {
                    if (l[i2] == null || !a.f.l[i2].a.equals(object2)) continue;
                    object = l[i2];
                    break block12;
                }
                object = object2 = null;
            }
        }
        if (object != null) {
            return object2;
        }
        if (main.a.a.k != null) {
            object2 = d.k.h();
            b b2 = null;
            for (int i3 = ((Vector)object2).size() - 1; i3 >= 0; --i3) {
                b2 = (b)((Vector)object2).elementAt(i3);
                if (b2.i == null || !b2.i.equals(string) || b2.j == null) continue;
                return b2.j;
            }
        }
        if (main.a.a.k != null && main.a.a.k.g == 4) {
            object2 = (d.d)main.a.a.k;
            d[] dArray = ((d.d)object2).c();
            d d2 = null;
            if (dArray != null) {
                for (int i4 = 0; i4 < dArray.length; ++i4) {
                    d2 = dArray[i4];
                    if (d2 == null || d2.i == null || !d2.i.equals(string) || d2.j == null) continue;
                    return d2.j;
                }
            }
        }
        return null;
    }

    public static b.a.b b(String string) {
        int n2;
        a[] aArray;
        if (string == null || string.trim().length() == 0) {
            return null;
        }
        b.a.b b2 = null;
        if (l != null) {
            for (int i2 = 0; i2 < l.length; ++i2) {
                if (l[i2] == null || (b2 = l[i2].a(string)) == null) continue;
                return b2;
            }
        }
        if (w != null && (aArray = w.j()) != null) {
            for (n2 = 0; n2 < aArray.length; ++n2) {
                if (aArray[n2] == null || aArray[n2].j == null || (b2 = aArray[n2].j.a(string)) == null) continue;
                return b2;
            }
        }
        if (main.a.a.k != null) {
            Vector vector = d.k.h();
            b b3 = null;
            for (int i3 = 0; i3 < vector.size(); ++i3) {
                b3 = (b)vector.elementAt(i3);
                if (b3.j == null || (b2 = b3.j.a(string)) == null) continue;
                return b2;
            }
        }
        if (main.a.a.k != null && main.a.a.k.g == 4) {
            d.d d2 = (d.d)main.a.a.k;
            for (n2 = 0; n2 < A.length; ++n2) {
                if (A[n2] == null || a.f.A[n2].j == null || (b2 = a.f.A[n2].j.a(string)) == null) continue;
                return b2;
            }
            d[] dArray = d2.c();
            d d3 = null;
            if (dArray != null) {
                for (int i4 = 0; i4 < dArray.length; ++i4) {
                    d3 = dArray[i4];
                    if (d3 == null || d3.j == null || (b2 = d3.j.a(string)) == null) continue;
                    return b2;
                }
            }
        }
        return null;
    }

    public static b.a.c c(String string) {
        if (string == null || string.trim().length() == 0) {
            return null;
        }
        b.a.c c2 = null;
        for (int i2 = 0; i2 < aG.size(); ++i2) {
            c2 = (b.a.c)aG.elementAt(i2);
            if (!c2.a.equals(string)) continue;
            return c2;
        }
        return null;
    }

    public static void k() {
        aG.removeAllElements();
    }

    public static void a(b.a.c c2) {
        if (c2 == null) {
            return;
        }
        aG.addElement(c2);
    }

    public static String j(int n2) {
        return o[n2];
    }

    public static boolean l() {
        return ar >= 2;
    }

    public static boolean m() {
        return as >= 2;
    }

    public static boolean n() {
        return aq || at || d;
    }

    public static boolean o() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeByte(ar);
            dataOutputStream.writeByte(as);
            dataOutputStream.writeBoolean(aq);
            dataOutputStream.writeBoolean(at);
            dataOutputStream.writeBoolean(au);
            dataOutputStream.writeBoolean(av);
            byte[] byArray = byteArrayOutputStream.toByteArray();
            b.e.a.a("swd_3_gil_pl", byArray);
            return true;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return false;
        }
        finally {
            b.e.a.a(byteArrayOutputStream);
            b.e.a.a(dataOutputStream);
        }
    }

    public static void p() {
        Object object = b.e.a.e("swd_3_gil_pl");
        if (object == null) {
            return;
        }
        object = new ByteArrayInputStream((byte[])object);
        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
        try {
            ar = dataInputStream.readByte();
            as = dataInputStream.readByte();
            aq = dataInputStream.readBoolean();
            at = dataInputStream.readBoolean();
            au = dataInputStream.readBoolean();
            av = dataInputStream.readBoolean();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
        finally {
            b.e.a.a((InputStream)object);
            b.e.a.a(dataInputStream);
        }
    }

    private static c a(byte[] object) {
        if (object == null || ((byte[])object).length == 0) {
            return null;
        }
        object = new ByteArrayInputStream((byte[])object);
        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
        try {
            dataInputStream.readByte();
            boolean bl = false;
            dataInputStream.readByte();
            dataInputStream.readBoolean();
            dataInputStream.readBoolean();
            c c2 = new c();
            c2.b(dataInputStream);
            return c2;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return null;
        }
        finally {
            b.e.a.a((InputStream)object);
            b.e.a.a(dataInputStream);
        }
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
        if (object2 == null || !((String)object2).equals("audio/midi")) {
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

    public static c a(boolean bl) {
        g.a.a(MainMIDlet.a, (Displayable)main.a.a, "0110");
        boolean bl2 = true;
        String[] stringArray = g.a.a();
        if (stringArray.length < 3 || stringArray[2] == null || stringArray[2].trim().length() == 0) {
            bl2 = false;
        }
        if (!bl2) {
            return null;
        }
        if (stringArray.length < 4 || stringArray[0] == null || stringArray[1] == null || stringArray[3] == null) {
            return null;
        }
        byte[] byArray = a.f.a("0A14", "swd2", stringArray[0].trim(), stringArray[1].trim(), stringArray[3].trim().equals("true"), bl);
        return a.f.a(byArray);
    }

    public static void f(int n2, int n3) {
        t.addElement(new int[]{n2, n3});
    }

    public static int k(int n2) {
        int n3;
        if (!t.isEmpty()) {
            int[] nArray = null;
            for (int i2 = 0; i2 < t.size(); ++i2) {
                nArray = (int[])t.elementAt(i2);
                if (nArray == null || nArray.length != 2 || nArray[0] != n2) continue;
                n3 = i2;
                break;
            }
        } else {
            n3 = n2 = -1;
        }
        if (n3 < 0 || n2 >= t.size()) {
            return 0;
        }
        int[] nArray = (int[])t.elementAt(n2);
        return nArray[1];
    }

    public static byte[] a(Vector vector) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            Object[] objectArray;
            if (vector == null || vector.size() == 0) {
                dataOutputStream.writeByte(0);
            } else {
                dataOutputStream.writeByte(vector.size());
                objectArray = null;
                for (int i2 = 0; i2 < vector.size(); ++i2) {
                    objectArray = (int[])vector.elementAt(i2);
                    dataOutputStream.writeInt(objectArray[0]);
                    dataOutputStream.writeInt(objectArray[1]);
                }
            }
            objectArray = byteArrayOutputStream.toByteArray();
            return objectArray;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return null;
        }
        finally {
            b.e.a.a(byteArrayOutputStream);
            b.e.a.a(dataOutputStream);
        }
    }

    public static Vector a(DataInputStream dataInputStream) {
        Vector<int[]> vector = new Vector<int[]>();
        int n2 = dataInputStream.readByte();
        if (n2 <= 0) {
            return vector;
        }
        int[] nArray = null;
        for (int i2 = 0; i2 < n2; ++i2) {
            int[] nArray2 = new int[2];
            nArray = nArray2;
            nArray2[0] = dataInputStream.readInt();
            nArray[1] = dataInputStream.readInt();
            vector.addElement(nArray);
        }
        return vector;
    }

    public static int b(g[] gArray) {
        int n2;
        if (gArray == null || gArray.length == 0) {
            return 1;
        }
        int n3 = 0;
        int n4 = 0;
        for (n2 = 0; n2 < 3; ++n2) {
            if (gArray[n2] == null) continue;
            ++n3;
            n4 += gArray[n2].I;
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

    public static void b(boolean bl) {
        at = true;
    }

    public static b.d.a[] q() {
        return aD;
    }

    public static void a(b.d.a[] aArray) {
        aD = aArray;
    }

    public static void r() {
        aD = new b.d.a[256];
    }

    public static int s() {
        int n2 = 0;
        for (int i2 = 0; i2 < aD.length; ++i2) {
            if (aD[i2] != null) continue;
            ++n2;
        }
        return n2;
    }

    public static g d(String string) {
        if (string == null || A == null) {
            return null;
        }
        for (int i2 = 0; i2 < A.length; ++i2) {
            if (!string.equals(a.f.A[i2].p)) continue;
            return A[i2];
        }
        return null;
    }

    public static boolean t() {
        return aq;
    }

    public static void c(boolean bl) {
        aq = true;
    }

    public static int u() {
        return ar;
    }

    public static void l(int n2) {
        ar = 2;
    }

    public static int v() {
        return as;
    }

    public static void m(int n2) {
        as = 2;
    }

    public static boolean w() {
        return au;
    }

    public static void d(boolean bl) {
        au = true;
    }

    public static boolean x() {
        return av;
    }

    public static void e(boolean bl) {
        av = true;
    }

    static {
        String[] stringArray = new String[]{"\u97f3\u4e50\u72b6\u6001", "\u7b80\u5355\u6218\u6597", "\u52a8\u6001\u5730\u56fe"};
        m = new boolean[]{false, false, true};
        n = false;
        o = null;
        p = new Hashtable();
        aw = null;
        ax = null;
        ay = null;
        q = null;
        r = (short)-3;
        az = null;
        aA = null;
        aB = null;
        aC = null;
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
        aD = new b.d.a[255];
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
        Y = null;
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
        aE = new short[]{594, 599, 603, 607};
        aF = null;
        ao = -1;
        ap = -1;
        aG = new Vector();
    }
}

