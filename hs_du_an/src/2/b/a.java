/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 *  javax.microedition.media.Manager
 *  javax.microedition.media.Player
 */
package b;

import b.b;
import b.c;
import b.e;
import b.f;
import b.g;
import c.r;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;

public final class a {
    public static boolean a = false;
    public static int b = 1;
    public static int c = 1;
    private static boolean ao = false;
    public static boolean d = true;
    public static final int[] e = new int[]{0xFFFA00, 0xFFFA00, 16748800, 16748800};
    public static final int[] f = new int[]{65310, 65310, 41491, 41491};
    public static final int[] g = new int[]{982271, 982271, 2236156, 2236156};
    public static final int[] h = new int[]{0xFFFA00, 0xFFFA00, 13676807, 13676807};
    public static boolean i = true;
    public static f[] j = null;
    public static int[][] k = null;
    public static e.b.c[] l = null;
    public static String[] m = new String[]{"\u97f3\u4e50\u72b6\u6001", "\u7b80\u5355\u6218\u6597", "\u52a8\u6001\u5730\u56fe"};
    public static boolean[] n = new boolean[]{false, false, true};
    public static boolean o = false;
    public static String[] p = null;
    public static Hashtable q = new Hashtable();
    private static short[] ap = null;
    private static int[] aq = null;
    private static byte[] ar = null;
    public static main.c r = null;
    public static short s = (short)-3;
    private static f.b[] as = null;
    private static short[] at = null;
    private static int[] au = null;
    private static byte[] av = null;
    public static f.c[] t = null;
    public static int[][] u = null;
    public static int v = 2;
    public static short w = (short)-1;
    public static r x = null;
    public static int y = 1;
    public static int z = 20;
    public static int A = 20;
    public static f[] B = null;
    public static byte[] C = new byte[256];
    public static int D = 0;
    public static f.b[] E = new f.b[255];
    public static int[] F = new int[255];
    public static Image G = null;
    public static int H = 0;
    public static Image I = null;
    public static int J = 0;
    public static Image K = null;
    public static int L = 0;
    public static Image M = null;
    public static Image N = null;
    public static Image O = null;
    public static Image P = null;
    public static Image Q = null;
    public static Image[] R = null;
    public static Image[] S = null;
    public static Image T = null;
    public static Image U = null;
    public static Image V = null;
    public static Image W = null;
    public static Image X = null;
    public static Image Y = null;
    public static Image Z = null;
    public static Image aa = null;
    public static Image ab = null;
    public static String[][] ac = null;
    public static String[] ad = null;
    public static boolean ae = false;
    public static int af = 0;
    public static byte ag = (byte)-1;
    public static byte ah = 0;
    public static int ai = -1;
    public static int aj = -1;
    public static int ak = 0;
    public static String al = "0.1.0";
    private static short[] aw = new short[]{594, 599, 603, 607};
    private static Player ax = null;
    public static int am = -1;
    public static int an = -1;
    private static Vector ay = new Vector();

    public static void a() {
        if (ac == null || ac.length == 0) {
            return;
        }
        ad = ac[e.a.b.c(ac.length)];
    }

    public static f a(short s2) {
        if (j == null) {
            return null;
        }
        for (int i2 = 0; i2 < j.length; ++i2) {
            if (b.a.j[i2].f != s2) continue;
            return j[i2];
        }
        return null;
    }

    public static f a(String string) {
        if (string == null || j == null) {
            return null;
        }
        for (int i2 = 0; i2 < j.length; ++i2) {
            if (!b.a.j[i2].z.equals(string)) continue;
            return j[i2];
        }
        return null;
    }

    public static f b(short s2) {
        if (B == null) {
            return null;
        }
        for (int i2 = 0; i2 < B.length; ++i2) {
            if (b.a.B[i2].f != s2) continue;
            return B[i2];
        }
        return null;
    }

    public static void a(f f2) {
        if (f2 == null) {
            return;
        }
        f2.a(true);
        f2.a = true;
        B = b.a.a(j);
        if (f2.c != null) {
            return;
        }
        f2.c = l[f2.f];
    }

    public static void b(f f2) {
        if (f2 == null) {
            return;
        }
        f2.a(false);
        f2.c = null;
        B = b.a.a(j);
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
                    inputStream = null;
                    dataInputStream = null;
                    try {
                        int n2;
                        short s2;
                        inputStream = "".getClass().getResourceAsStream("/c.dat");
                        if (inputStream == null) {
                            e.a.b.a(null);
                            break block10;
                        }
                        dataInputStream = new DataInputStream(inputStream);
                        short s3 = dataInputStream.readByte();
                        k = new int[s3][b.f.u.length];
                        for (s2 = 0; s2 < s3; ++s2) {
                            for (n2 = 0; n2 < k[s2].length; ++n2) {
                                b.a.k[s2][n2] = dataInputStream.readShort();
                            }
                        }
                        j = new f[s3];
                        for (n2 = 0; n2 < s3; ++n2) {
                            b.a.j[n2] = new f();
                            j[n2].a(dataInputStream);
                            b.a.j[n2].s[2] = b.a.j[n2].s[3];
                            b.a.j[n2].s[4] = b.a.j[n2].s[5];
                            b.a.j[n2].s[6] = b.a.j[n2].s[7];
                            b.a.j[n2].c = l[b.a.j[n2].f];
                            j[n2].a(aw[b.a.j[n2].f]);
                            s2 = j[n2].e(b.a.j[n2].A);
                            j[n2].d(s2);
                            if (!j[n2].p()) continue;
                            j[n2].a(true);
                        }
                        e.a.b.a(dataInputStream);
                        break block11;
                    }
                    catch (IOException iOException) {
                        IOException iOException2 = iOException;
                        iOException.printStackTrace();
                        j = null;
                        break block12;
                    }
                }
                e.a.b.a(inputStream);
                break block12;
            }
            e.a.b.a(inputStream);
            break block12;
            finally {
                e.a.b.a(dataInputStream);
                e.a.b.a(inputStream);
            }
        }
        B = b.a.a(j);
        y = 143;
        z = 0;
        A = 12;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static main.c a(int n2, short s2) {
        main.c c2;
        DataInputStream dataInputStream;
        InputStream inputStream;
        block16: {
            block15: {
                block14: {
                    block13: {
                        inputStream = null;
                        dataInputStream = null;
                        try {
                            String string = "/map/" + n2 + ".m";
                            inputStream = "".getClass().getResourceAsStream(string);
                            if (inputStream == null) {
                                string = null;
                                e.a.b.a(null);
                                break block13;
                            }
                            dataInputStream = new DataInputStream(inputStream);
                            dataInputStream.skip(8L);
                            int n3 = e.a.b.a((int)dataInputStream.readByte());
                            if (n3 <= 0) {
                                Object var4_9 = null;
                                e.a.b.a(dataInputStream);
                                break block14;
                            }
                            int n4 = 0;
                            byte by = 0;
                            int n5 = 0;
                            boolean bl = false;
                            for (int i2 = 0; i2 < n3; ++i2) {
                                by = dataInputStream.readByte();
                                n5 = dataInputStream.readInt();
                                if (bl) continue;
                                if (s2 == by) {
                                    bl = true;
                                    continue;
                                }
                                n4 += n5;
                            }
                            if (!bl) {
                                Object var8_15 = null;
                                e.a.b.a(dataInputStream);
                                break block15;
                            }
                            dataInputStream.skip(n4);
                            main.c c3 = main.c.a(dataInputStream, null);
                            s = s2;
                            c2 = c3;
                            e.a.b.a(dataInputStream);
                            break block16;
                        }
                        catch (IOException iOException) {
                            IOException iOException2 = iOException;
                            iOException.printStackTrace();
                            return null;
                        }
                    }
                    e.a.b.a(inputStream);
                    return null;
                }
                e.a.b.a(inputStream);
                return null;
            }
            e.a.b.a(inputStream);
            return null;
        }
        e.a.b.a(inputStream);
        return c2;
        finally {
            e.a.b.a(dataInputStream);
            e.a.b.a(inputStream);
        }
    }

    public static byte a(byte by) {
        int n2 = e.a.b.a((int)by);
        by = (byte)n2;
        if (n2 < 0 || by > 255) {
            return 0;
        }
        return C[by];
    }

    public static void a(byte by, byte by2) {
        int n2 = e.a.b.a((int)by);
        by = (byte)n2;
        if (n2 < 0 || by > 255) {
            return;
        }
        b.a.C[by] = by2;
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
                        e.a.b.a(null);
                        break block9;
                    }
                    dataInputStream = new DataInputStream(inputStream);
                    int n2 = dataInputStream.readByte();
                    b.f.r = new short[n2][];
                    for (int i2 = 0; i2 < n2; ++i2) {
                        int n3 = e.a.b.a((int)dataInputStream.readByte());
                        b.f.r[i2] = new short[n3];
                        for (int i3 = 0; i3 < n3; ++i3) {
                            b.f.r[i2][i3] = dataInputStream.readShort();
                        }
                    }
                    e.a.b.a(dataInputStream);
                    break block10;
                }
                catch (IOException iOException) {
                    IOException iOException2 = iOException;
                    iOException.printStackTrace();
                    return;
                }
            }
            e.a.b.a(inputStream);
            return;
        }
        e.a.b.a(inputStream);
        return;
        finally {
            e.a.b.a(dataInputStream);
            e.a.b.a(inputStream);
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
            at = new short[n3];
            au = new int[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                b.a.at[n2] = dataInputStream.readShort();
                b.a.au[n2] = dataInputStream.readInt();
            }
            n2 = dataInputStream.readInt();
            av = new byte[n2];
            dataInputStream.read(av);
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
        finally {
            e.a.b.a(inputStream);
            e.a.b.a(dataInputStream);
        }
        b.a.a(at, au);
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
        t = new f.c[bArray.length];
        for (int i2 = 0; i2 < bArray.length; ++i2) {
            b.a.t[i2] = (f.c)bArray[i2];
        }
        bArray = t;
        if (t == null || bArray.length <= 1) {
            return;
        }
        f.b b2 = null;
        for (int i3 = 1; i3 < bArray.length; ++i3) {
            for (int i4 = bArray.length - 1; i4 >= i3; --i4) {
                if (bArray[i4].r >= bArray[i4 - 1].r) continue;
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
            short s2 = dataInputStream.readShort();
            f.b[] bArray = new f.b[s2];
            for (int i2 = 0; i2 < bArray.length; ++i2) {
                bArray[i2] = b.a.b(dataInputStream);
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
            e.a.b.a(dataInputStream);
            e.a.b.a((InputStream)object);
        }
    }

    private static f.b b(DataInputStream object) {
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

    public static c c(short s2) {
        for (int i2 = 0; i2 < ap.length; ++i2) {
            if (ap[i2] != s2) continue;
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(ar, aq[i2], ar.length - aq[i2]);
            DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
            c c2 = new c();
            c2.a(dataInputStream);
            e.a.b.a(byteArrayInputStream);
            e.a.b.a(dataInputStream);
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
            e.a.b.a(inputStream);
            e.a.b.a(null);
            return;
        }
        try {
            int n2;
            dataInputStream = new DataInputStream(inputStream);
            int n3 = dataInputStream.readShort();
            ap = new short[n3];
            aq = new int[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                b.a.ap[n2] = dataInputStream.readShort();
                b.a.aq[n2] = dataInputStream.readInt();
            }
            n2 = dataInputStream.readInt();
            ar = new byte[n2];
            dataInputStream.read(ar);
        }
        catch (Exception exception) {
            try {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
            catch (Throwable throwable) {
                e.a.b.a(inputStream);
                e.a.b.a(dataInputStream);
                throw throwable;
            }
            e.a.b.a(inputStream);
            e.a.b.a(dataInputStream);
            return;
        }
        e.a.b.a(inputStream);
        e.a.b.a(dataInputStream);
        return;
    }

    public static void a(g g2) {
        if (g2 == null) {
            return;
        }
        b.a.h();
        g2.b();
    }

    public static g[] g() {
        Object object = e.a.b.c("sword2");
        if (object == null) {
            return null;
        }
        object = new ByteArrayInputStream((byte[])object);
        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
        g[] gArray = b.a.c(dataInputStream);
        e.a.b.a((InputStream)object);
        e.a.b.a(dataInputStream);
        return gArray;
    }

    private static g[] c(DataInputStream dataInputStream) {
        try {
            short s2 = dataInputStream.readShort();
            if (s2 <= 0) {
                return null;
            }
            g[] gArray = new g[s2];
            for (int i2 = 0; i2 < gArray.length; ++i2) {
                if (dataInputStream.readBoolean()) continue;
                gArray[i2] = new g();
                if (gArray[i2].a(dataInputStream)) continue;
                gArray[i2] = null;
                return null;
            }
            return gArray;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return null;
        }
    }

    public static void a(g[] objectArray) {
        b.a.o();
        objectArray = b.a.b(objectArray);
        e.a.b.a("sword2", (byte[])objectArray);
    }

    public static void h() {
        if (x == null) {
            return;
        }
        byte[] byArray = x.q();
        q.put(new Integer(b.a.x.c), byArray);
    }

    private static byte[] b(g[] gArray) {
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
            e.a.b.a(byteArrayOutputStream);
            e.a.b.a(dataOutputStream);
        }
    }

    public static void a(Graphics graphics, byte by, int n2, int n3) {
        e.a.b.a(graphics, K, by * L, 0, L, K.getHeight(), n2, n3, 20);
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

    public static f.b a(int n2) {
        Object object = b.a.a(as, n2);
        if (object == null) {
            if ((n2 = e.a.b.a(at, n2)) < 0) {
                object = null;
            } else {
                n2 = au[n2];
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(av, n2, av.length - n2);
                object = new DataInputStream(byteArrayInputStream);
                f.b b2 = b.a.b((DataInputStream)object);
                e.a.b.a(byteArrayInputStream);
                e.a.b.a((InputStream)object);
                as = b.a.a(as, b2);
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
            for (n2 = 0; n2 < bArray.length && bArray[n2].r < b2.r; ++n2) {
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

    public static void d(short s2) {
        if (as == null) {
            return;
        }
        for (int i2 = 0; i2 < as.length; ++i2) {
            if (s2 != b.a.as[i2].r) continue;
            if (as.length == 1) {
                as = null;
                return;
            }
            f.b[] bArray = new f.b[as.length - 1];
            System.arraycopy(as, 0, bArray, 0, i2);
            System.arraycopy(as, i2 + 1, bArray, i2, as.length - i2 - 1);
            return;
        }
    }

    public static f.c b(int n2) {
        f.b b2 = b.a.a(n2);
        if (b2 == null || b2.s != 12) {
            return null;
        }
        return (f.c)b2;
    }

    public static f.c c(int n2) {
        f.b b2 = b.a.a((f.b[])t, n2);
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
            if (bArray[n5].r == n2) {
                return bArray[n5];
            }
            if (bArray[n5].r < n2) {
                n3 = n5 + 1;
                continue;
            }
            n4 = n5 - 1;
        }
        return null;
    }

    public static int a(int n2, int n3) {
        f.b b2 = b.a.a(n2);
        return b.a.b(b2, n3);
    }

    public static boolean b(int n2, int n3) {
        if (n2 == -1) {
            return D + n3 >= 0;
        }
        f.b b2 = b.a.a(n2);
        if (b2 == null || n3 > 255) {
            return false;
        }
        int n4 = 0;
        if (n3 == 0) {
            return true;
        }
        if (n3 > 0) {
            if (b2.b()) {
                n2 = b.a.b(b2);
                if (n2 < 0) {
                    return true;
                }
                n4 = F[n2];
                return n4 + n3 <= 255;
            }
            n2 = 0;
            for (int i2 = 0; i2 < E.length; ++i2) {
                if (E[i2] != null) continue;
                ++n2;
            }
            return n2 + n3 <= 256;
        }
        b2 = b.a.a(n2);
        n4 = b.a.a(b2);
        return n4 + n3 >= 0;
    }

    public static boolean d(int n2) {
        if (n2 < 0 || n2 >= E.length) {
            return false;
        }
        for (int i2 = 0; i2 < B.length; ++i2) {
            if (B[i2].j(n2) < 0) continue;
            return true;
        }
        return false;
    }

    public static int[] a(byte by, boolean bl) {
        Vector<Integer> vector = new Vector<Integer>();
        for (int i2 = 0; i2 < E.length; ++i2) {
            if (E[i2] == null || by != 0 && f.b.a(b.a.E[i2].s) != by || bl && b.a.d(i2)) continue;
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

    public static int[] b(byte by) {
        Vector<Integer> vector = new Vector<Integer>();
        for (int i2 = 0; i2 < E.length; ++i2) {
            if (E[i2] == null || b.a.E[i2].s != by) continue;
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

    public static boolean a(f.b b2, int n2) {
        if (b2 == null || n2 <= 0) {
            return false;
        }
        int n3 = 0;
        boolean bl = false;
        if (b2.b()) {
            n3 = b.a.b(b2);
            bl = b.a.d(n3, n2);
        } else {
            int n4 = 0;
            for (int i2 = 0; i2 < n2; ++i2) {
                n3 = b.a.b(b2);
                bl = b.a.d(n3, 1);
                for (int i3 = 0; i3 < B.length; ++i3) {
                    n4 = B[i3].j(n3);
                    if (n4 < 0) continue;
                    B[i3].f(n4);
                }
            }
        }
        return bl;
    }

    public static int b(f.b b2, int n2) {
        int n3;
        block6: {
            block4: {
                block5: {
                    if (b2 == null || n2 <= 0) {
                        return -1;
                    }
                    n3 = -1;
                    if (!b2.b()) break block4;
                    n3 = b.a.b(b2);
                    if (n3 >= 0) break block5;
                    n3 = b.a.q();
                    if (n3 < 0) {
                        return -2;
                    }
                    n2 = n2 > 255 ? 255 : n2;
                    b.a.E[n3] = b2;
                    b.a.F[n3] = n2;
                    break block6;
                }
                int n4 = n3;
                F[n4] = F[n4] + n2;
                if (F[n3] <= 255) break block6;
                b.a.F[n3] = 255;
                break block6;
            }
            for (int i2 = 0; i2 < n2; ++i2) {
                n3 = b.a.q();
                if (n3 < 0) {
                    return -1;
                }
                b.a.E[n3] = b2;
                b.a.F[n3] = 1;
            }
        }
        return n3;
    }

    private static int q() {
        for (int i2 = 0; i2 < E.length; ++i2) {
            if (E[i2] != null) continue;
            return i2;
        }
        return -1;
    }

    public static f.b e(int n2) {
        if (n2 < 0 || n2 >= E.length) {
            return null;
        }
        return E[n2];
    }

    public static int f(int n2) {
        if (n2 < 0 || n2 >= E.length) {
            return 0;
        }
        return F[n2];
    }

    private static int b(f.b b2) {
        if (b2 == null || E == null) {
            return -1;
        }
        for (int i2 = 0; i2 < E.length; ++i2) {
            if (E[i2] == null || E[i2] != b2) continue;
            return i2;
        }
        return -1;
    }

    public static int a(f.b b2, boolean n2) {
        if (b2 == null || E == null) {
            return -1;
        }
        for (n2 = 0; n2 < E.length; ++n2) {
            if (E[n2] == null || E[n2] != b2 || E[n2].c() && b.a.d(n2)) continue;
            return n2;
        }
        return -1;
    }

    public static int a(f.b b2) {
        if (b2 == null) {
            return 0;
        }
        int n2 = 0;
        if (b2.b()) {
            int n3 = b.a.b(b2);
            if (n3 < 0 || n3 >= F.length) {
                return 0;
            }
            n2 = F[n3];
        } else {
            for (int i2 = 0; i2 < E.length; ++i2) {
                if (E[i2] == null || E[i2] != b2) continue;
                ++n2;
            }
        }
        return n2;
    }

    public static void i() {
        if (ax == null) {
            return;
        }
        try {
            ax.start();
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
        if (ax == null) {
            return;
        }
        try {
            ax.stop();
            ax.deallocate();
            ax = null;
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
    public static void c(int n2, int n3) {
        InputStream inputStream = null;
        try {
            block13: {
                block12: {
                    if (n2 < 0) {
                        b.a.j();
                        am = -1;
                        e.a.b.a(null);
                        return;
                    }
                    if (!n[0] || n2 == am && ax != null) break block12;
                    if (ax != null) {
                        b.a.j();
                    }
                    if ((inputStream = main.b.a.getClass().getResourceAsStream("/midi/" + n2 + ".mid")) == null) break block13;
                    ax = Manager.createPlayer((InputStream)inputStream, (String)"audio/midi");
                    ax.prefetch();
                    ax.setLoopCount(n3);
                    ax.start();
                }
                am = n2;
                an = n3;
                return;
            }
            e.a.b.a(inputStream);
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
        finally {
            e.a.b.a(inputStream);
        }
    }

    public static void g(int n2) {
        if ((D += n2) > 65534) {
            D = 65534;
            return;
        }
        if (D < 0) {
            D = 0;
        }
    }

    public static void a(int n2, byte by, int n3) {
        switch (by) {
            case 1: {
                by = (byte)n3;
                if (n2 == -1) {
                    D = e.a.b.b(0, by, 65534, 0);
                    break;
                }
                f.b b2 = b.a.a(n2);
                f.b b3 = b2;
                b3 = b2;
                int n4 = b.a.b(b2);
                if (n4 >= 0) {
                    b.a.F[n4] = e.a.b.b(0, by, 255, 0);
                    if (F[n4] <= 0) {
                        b.a.d(n4, 999);
                    }
                }
                return;
            }
            case 0: {
                if (n2 == -1) {
                    D = e.a.b.b(D, n3, 65534, 0);
                    return;
                }
                if (n3 > 0) {
                    b.a.a(n2, n3);
                    return;
                }
                if (n3 >= 0) break;
                by = (byte)(-n3);
                f.b b4 = b.a.a(n2);
                b.a.a(b4, (int)by);
            }
        }
    }

    public static boolean d(int n2, int n3) {
        if (n2 < 0 || n2 >= E.length || n3 <= 0) {
            return false;
        }
        int n4 = n2;
        F[n4] = F[n4] - n3;
        if (F[n2] <= 0) {
            b.a.F[n2] = 0;
            b.a.E[n2] = null;
        }
        return true;
    }

    public static e.b.c b(String string) {
        Object object;
        Object object2;
        block12: {
            if (string == null) {
                return null;
            }
            object2 = null;
            if (x != null) {
                object2 = x.a(string);
            }
            if (object2 != null) {
                return object2;
            }
            object2 = string;
            if (object2 == null || l == null) {
                object = null;
            } else {
                for (int i2 = 0; i2 < l.length; ++i2) {
                    if (l[i2] == null || !b.a.l[i2].a.equals(object2)) continue;
                    object = l[i2];
                    break block12;
                }
                object = object2 = null;
            }
        }
        if (object != null) {
            return object2;
        }
        if (main.b.a.i != null) {
            object2 = main.b.a.i.g();
            b b2 = null;
            for (int i3 = ((Vector)object2).size() - 1; i3 >= 0; --i3) {
                b2 = (b)((Vector)object2).elementAt(i3);
                if (b2.b == null || !b2.b.equals(string) || b2.c == null) continue;
                return b2.c;
            }
        }
        if (main.b.a.i != null && main.b.a.i.a == 4) {
            object2 = (d.c)main.b.a.i;
            c[] cArray = ((d.c)object2).i();
            c c2 = null;
            if (cArray != null) {
                for (int i4 = 0; i4 < cArray.length; ++i4) {
                    c2 = cArray[i4];
                    if (c2 == null || c2.b == null || !c2.b.equals(string) || c2.c == null) continue;
                    return c2.c;
                }
            }
        }
        return null;
    }

    public static e.b.a c(String string) {
        int n2;
        e[] eArray;
        if (string == null || string.trim().length() == 0) {
            return null;
        }
        e.b.a a2 = null;
        if (l != null) {
            for (int i2 = 0; i2 < l.length; ++i2) {
                if (l[i2] == null || (a2 = l[i2].a(string)) == null) continue;
                return a2;
            }
        }
        if (x != null && (eArray = x.k()) != null) {
            for (n2 = 0; n2 < eArray.length; ++n2) {
                if (eArray[n2] == null || eArray[n2].c == null || (a2 = eArray[n2].c.a(string)) == null) continue;
                return a2;
            }
        }
        if (main.b.a.i != null) {
            Vector vector = main.b.a.i.g();
            b b2 = null;
            for (int i3 = 0; i3 < vector.size(); ++i3) {
                b2 = (b)vector.elementAt(i3);
                if (b2.c == null || (a2 = b2.c.a(string)) == null) continue;
                return a2;
            }
        }
        if (main.b.a.i != null && main.b.a.i.a == 4) {
            d.c c2 = (d.c)main.b.a.i;
            for (n2 = 0; n2 < B.length; ++n2) {
                if (B[n2] == null || b.a.B[n2].c == null || (a2 = b.a.B[n2].c.a(string)) == null) continue;
                return a2;
            }
            c[] cArray = c2.i();
            c c3 = null;
            if (cArray != null) {
                for (int i4 = 0; i4 < cArray.length; ++i4) {
                    c3 = cArray[i4];
                    if (c3 == null || c3.c == null || (a2 = c3.c.a(string)) == null) continue;
                    return a2;
                }
            }
        }
        return null;
    }

    public static e.b.c d(String string) {
        if (string == null || string.trim().length() == 0) {
            return null;
        }
        e.b.c c2 = null;
        for (int i2 = 0; i2 < ay.size(); ++i2) {
            c2 = (e.b.c)ay.elementAt(i2);
            if (!c2.a.equals(string)) continue;
            return c2;
        }
        return null;
    }

    public static void k() {
        ay.removeAllElements();
    }

    public static void a(e.b.c c2) {
        if (c2 == null) {
            return;
        }
        ay.addElement(c2);
    }

    public static String h(int n2) {
        return p[n2];
    }

    public static boolean l() {
        return b >= 2;
    }

    public static boolean m() {
        return c >= 2;
    }

    public static boolean n() {
        return a || ao || d;
    }

    public static boolean o() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeByte(b);
            dataOutputStream.writeByte(c);
            dataOutputStream.writeBoolean(a);
            dataOutputStream.writeBoolean(ao);
            byte[] byArray = byteArrayOutputStream.toByteArray();
            e.a.b.a("swd_2_qq_pl", byArray);
            return true;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return false;
        }
        finally {
            e.a.b.a(byteArrayOutputStream);
            e.a.b.a(dataOutputStream);
        }
    }

    public static void p() {
        Object object = e.a.b.c("swd_2_qq_pl");
        if (object == null) {
            return;
        }
        object = new ByteArrayInputStream((byte[])object);
        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
        try {
            b = dataInputStream.readByte();
            c = dataInputStream.readByte();
            a = dataInputStream.readBoolean();
            ao = dataInputStream.readBoolean();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
        finally {
            e.a.b.a((InputStream)object);
            e.a.b.a(dataInputStream);
        }
    }

    public static void e(int n2, int n3) {
        if (u == null) {
            u = new int[1][2];
            b.a.u[0][0] = n2;
            b.a.u[0][1] = n3;
            return;
        }
        int n4 = b.a.j(n2);
        if (n4 < 0) {
            int[][] nArray = new int[u.length + 1][2];
            System.arraycopy(u, 0, nArray, 0, u.length);
            nArray[nArray.length - 1][0] = n2;
            nArray[nArray.length - 1][1] = n3;
            u = nArray;
            return;
        }
        int[] nArray = u[n4];
        nArray[1] = nArray[1] + n3;
    }

    private static int j(int n2) {
        if (u == null) {
            return -1;
        }
        for (int i2 = 0; i2 < u.length; ++i2) {
            if (u[i2] == null || u[i2].length != 2 || u[i2][0] != n2) continue;
            return i2;
        }
        return -1;
    }

    public static int i(int n2) {
        if (u == null) {
            return 0;
        }
        if ((n2 = b.a.j(n2)) < 0 || n2 >= u.length) {
            return 0;
        }
        return u[n2][1];
    }

    public static byte[] a(int[][] nArray) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            if (nArray == null) {
                dataOutputStream.writeByte(0);
            } else {
                dataOutputStream.writeByte(nArray.length);
                for (int i2 = 0; i2 < nArray.length; ++i2) {
                    dataOutputStream.writeInt(nArray[i2][0]);
                    dataOutputStream.writeInt(nArray[i2][1]);
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
            e.a.b.a(byteArrayOutputStream);
            e.a.b.a(dataOutputStream);
        }
    }

    public static int[][] a(DataInputStream dataInputStream) {
        int n2 = dataInputStream.readByte();
        if (n2 <= 0) {
            return null;
        }
        int[][] nArray = new int[n2][2];
        for (int i2 = 0; i2 < n2; ++i2) {
            nArray[i2][0] = dataInputStream.readInt();
            nArray[i2][1] = dataInputStream.readInt();
        }
        return nArray;
    }

    public static int b(f[] fArray) {
        int n2;
        if (fArray == null || fArray.length == 0) {
            return 1;
        }
        int n3 = 0;
        int n4 = 0;
        for (n2 = 0; n2 < fArray.length; ++n2) {
            if (fArray[n2] == null) continue;
            ++n3;
            n4 += fArray[n2].A;
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

    public static void a(boolean bl) {
        ao = true;
    }
}

