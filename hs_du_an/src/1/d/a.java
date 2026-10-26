/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 *  javax.microedition.media.Manager
 *  javax.microedition.media.Player
 */
package d;

import b.q;
import c.e;
import d.b;
import d.c;
import d.d;
import d.f;
import d.g;
import d.h;
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
    public static boolean d = false;
    public static final int[] e = new int[]{0xFFFA00, 0xFFFA00, 16748800, 16748800};
    public static final int[] f = new int[]{65310, 65310, 41491, 41491};
    public static final int[] g = new int[]{982271, 982271, 2236156, 2236156};
    public static final int[] h = new int[]{0xFFFA00, 0xFFFA00, 13676807, 13676807};
    public static short[] i = null;
    public static String[] j = null;
    public static boolean k = true;
    public static g[] l = null;
    public static int[][] m = null;
    public static f.d[] n = null;
    public static boolean[] o = new boolean[]{false};
    public static boolean p = false;
    public static String[] q = null;
    public static Hashtable r = new Hashtable();
    private static short[] ah = null;
    private static int[] ai = null;
    private static byte[] aj = null;
    public static main.d s = null;
    public static short t = (short)-3;
    public static c[][] u = null;
    private static c.f[] ak = null;
    private static short[] al = null;
    private static int[] am = null;
    private static byte[] an = null;
    public static c.a[] v = null;
    private static b.a[] ao = null;
    public static int w = 2;
    public static short x = (short)-1;
    public static q y = null;
    public static int z = 1;
    public static int A = 20;
    public static int B = 20;
    public static g[] C = null;
    public static byte[] D = new byte[256];
    public static int E = 0;
    public static c.f[] F = new c.f[256];
    public static int[] G = new int[256];
    public static Image H = null;
    public static int I = 0;
    public static Image J = null;
    public static int K = 0;
    public static Image L = null;
    public static int M = 0;
    public static Image N = null;
    public static Image O = null;
    public static Image P = null;
    public static Image Q = null;
    public static Image R = null;
    public static Image[] S = null;
    public static Image[] T = null;
    public static Image U = null;
    public static Image V = null;
    public static Image W = null;
    public static Image X = null;
    public static Image Y = null;
    public static Image Z = null;
    public static Image aa = null;
    public static Image ab = null;
    public static Image ac = null;
    public static String[][] ad = null;
    public static String[] ae = null;
    private static short[] ap = new short[]{594, 599, 603, 607};
    private static Player aq = null;
    public static int af = -1;
    public static int ag = -1;
    private static Vector ar = new Vector();

    public static void a() {
        if (ad == null || ad.length == 0) {
            return;
        }
        ae = ad[h.b.c(ad.length)];
    }

    public static g a(short s2) {
        if (l == null) {
            return null;
        }
        for (int i2 = 0; i2 < l.length; ++i2) {
            if (d.a.l[i2].f != s2) continue;
            return l[i2];
        }
        return null;
    }

    public static g b(short s2) {
        if (C == null) {
            return null;
        }
        for (int i2 = 0; i2 < C.length; ++i2) {
            if (d.a.C[i2].f != s2) continue;
            return C[i2];
        }
        return null;
    }

    public static void a(g g2) {
        if (g2 == null) {
            return;
        }
        g2.b(true);
        g2.a(true);
        C = d.a.a(l);
        if (g2.c != null) {
            return;
        }
        g2.c = n[g2.f];
    }

    public static void b(g g2) {
        if (g2 == null) {
            return;
        }
        g2.b(false);
        g2.c = null;
        C = d.a.a(l);
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
                        inputStream = "".getClass().getResourceAsStream("/c.dat");
                        if (inputStream == null) {
                            h.b.a(null);
                            break block10;
                        }
                        dataInputStream = new DataInputStream(inputStream);
                        int n3 = dataInputStream.readByte();
                        m = new int[n3][d.g.v.length];
                        for (n2 = 0; n2 < n3; ++n2) {
                            for (int i2 = 0; i2 < m[n2].length; ++i2) {
                                d.a.m[n2][i2] = dataInputStream.readShort();
                            }
                        }
                        l = new g[n3];
                        for (n2 = 0; n2 < n3; ++n2) {
                            d.a.l[n2] = new g();
                            l[n2].a(dataInputStream);
                            l[n2].A();
                            d.a.l[n2].J[2] = d.a.l[n2].J[3];
                            d.a.l[n2].J[4] = d.a.l[n2].J[5];
                            d.a.l[n2].J[6] = d.a.l[n2].J[7];
                            d.a.l[n2].c = n[d.a.l[n2].f];
                            l[n2].b(ap[d.a.l[n2].f]);
                            if (!l[n2].s()) continue;
                            l[n2].b(true);
                        }
                        h.b.a(dataInputStream);
                        break block11;
                    }
                    catch (IOException iOException) {
                        IOException iOException2 = iOException;
                        iOException.printStackTrace();
                        l = null;
                        break block12;
                    }
                }
                h.b.a(inputStream);
                break block12;
            }
            h.b.a(inputStream);
            break block12;
            finally {
                h.b.a(dataInputStream);
                h.b.a(inputStream);
            }
        }
        C = d.a.a(l);
        z = 1;
        A = 29;
        B = 12;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static main.d a(int n2, short s2) {
        main.d d2;
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
                                h.b.a(null);
                                break block13;
                            }
                            dataInputStream = new DataInputStream(inputStream);
                            dataInputStream.skip(8L);
                            int n3 = h.b.a((int)dataInputStream.readByte());
                            if (n3 <= 0) {
                                Object var4_9 = null;
                                h.b.a(dataInputStream);
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
                                h.b.a(dataInputStream);
                                break block15;
                            }
                            dataInputStream.skip(n4);
                            main.d d3 = main.d.a(dataInputStream, null);
                            t = s2;
                            d2 = d3;
                            h.b.a(dataInputStream);
                            break block16;
                        }
                        catch (IOException iOException) {
                            IOException iOException2 = iOException;
                            iOException.printStackTrace();
                            return null;
                        }
                    }
                    h.b.a(inputStream);
                    return null;
                }
                h.b.a(inputStream);
                return null;
            }
            h.b.a(inputStream);
            return null;
        }
        h.b.a(inputStream);
        return d2;
        finally {
            h.b.a(dataInputStream);
            h.b.a(inputStream);
        }
    }

    public static byte a(byte by) {
        int n2 = h.b.a((int)by);
        by = (byte)n2;
        if (n2 < 0 || by > 255) {
            return 0;
        }
        return D[by];
    }

    public static void a(byte by, byte by2) {
        int n2 = h.b.a((int)by);
        by = (byte)n2;
        if (n2 < 0 || by > 255) {
            return;
        }
        d.a.D[by] = by2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void c() {
        DataInputStream dataInputStream;
        InputStream inputStream;
        block12: {
            block11: {
                inputStream = null;
                dataInputStream = null;
                try {
                    inputStream = "".getClass().getResourceAsStream("/exp.dat");
                    if (inputStream == null) {
                        h.b.a(null);
                        break block11;
                    }
                    dataInputStream = new DataInputStream(inputStream);
                    int n2 = dataInputStream.readByte();
                    byte by = dataInputStream.readByte();
                    c[][] cArray = new c[n2][by];
                    c c2 = null;
                    for (int i2 = 0; i2 < n2; ++i2) {
                        int n3 = h.b.a((int)dataInputStream.readByte());
                        for (int i3 = 0; i3 < n3; ++i3) {
                            c2 = new c();
                            DataInputStream dataInputStream2 = dataInputStream;
                            Object object = c2;
                            try {
                                ((c)object).a = dataInputStream2.readShort();
                                ((c)object).b = dataInputStream2.readInt();
                                ((c)object).c = dataInputStream2.readShort();
                                ((c)object).d = dataInputStream2.readShort();
                                ((c)object).e = dataInputStream2.readShort();
                                ((c)object).f = dataInputStream2.readShort();
                                ((c)object).g = dataInputStream2.readShort();
                                ((c)object).h = dataInputStream2.readShort();
                                ((c)object).i = dataInputStream2.readShort();
                                ((c)object).j = dataInputStream2.readShort();
                            }
                            catch (Exception exception) {
                                object = exception;
                                exception.printStackTrace();
                            }
                            cArray[i2][c2.a - 1] = c2;
                        }
                    }
                    u = cArray;
                    h.b.a(dataInputStream);
                    break block12;
                }
                catch (IOException iOException) {
                    IOException iOException2 = iOException;
                    iOException.printStackTrace();
                    return;
                }
            }
            h.b.a(inputStream);
            return;
        }
        h.b.a(inputStream);
        return;
        finally {
            h.b.a(dataInputStream);
            h.b.a(inputStream);
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
            al = new short[n3];
            am = new int[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                d.a.al[n2] = dataInputStream.readShort();
                d.a.am[n2] = dataInputStream.readInt();
            }
            n2 = dataInputStream.readInt();
            an = new byte[n2];
            dataInputStream.read(an);
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
        finally {
            h.b.a(inputStream);
            h.b.a(dataInputStream);
        }
        d.a.a(al, am);
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
        c.f[] fArray = d.a.c("/item15.dat");
        if (fArray == null) {
            return;
        }
        v = new c.a[fArray.length];
        for (int i2 = 0; i2 < fArray.length; ++i2) {
            d.a.v[i2] = (c.a)fArray[i2];
        }
        d.a.a(v);
    }

    public static void f() {
        c.f[] fArray = d.a.c("/fb_skill.dat");
        if (fArray == null) {
            return;
        }
        ao = new b.a[fArray.length];
        for (int i2 = 0; i2 < fArray.length; ++i2) {
            d.a.ao[i2] = (b.a)fArray[i2];
        }
        d.a.a(ao);
    }

    private static c.f[] c(String object) {
        object = "".getClass().getResourceAsStream((String)object);
        if (object == null) {
            return null;
        }
        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
        try {
            short s2 = dataInputStream.readShort();
            c.f[] fArray = new c.f[s2];
            for (int i2 = 0; i2 < fArray.length; ++i2) {
                fArray[i2] = d.a.a(dataInputStream);
            }
            c.f[] fArray2 = fArray;
            return fArray2;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return null;
        }
        finally {
            h.b.a(dataInputStream);
            h.b.a((InputStream)object);
        }
    }

    private static c.f a(DataInputStream object) {
        c.f f2 = null;
        byte by = 0;
        try {
            by = ((DataInputStream)object).readByte();
            switch (by) {
                case 1: {
                    f2 = new c.c();
                    break;
                }
                case 5: {
                    f2 = new c.g();
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
                    f2 = new c.d(by);
                    break;
                }
                case 12: {
                    f2 = new c.b();
                    break;
                }
                case 14: {
                    f2 = new c.a();
                    break;
                }
                case 15: {
                    f2 = new e();
                    break;
                }
                case 17: {
                    f2 = new b.a();
                }
            }
            f2.a((DataInputStream)object);
            return f2;
        }
        catch (IOException iOException) {
            object = iOException;
            iOException.printStackTrace();
            return null;
        }
    }

    public static d c(short s2) {
        for (int i2 = 0; i2 < ah.length; ++i2) {
            if (ah[i2] != s2) continue;
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(aj, ai[i2], aj.length - ai[i2]);
            DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
            d d2 = new d();
            d2.a(dataInputStream);
            h.b.a(byteArrayInputStream);
            h.b.a(dataInputStream);
            return d2;
        }
        return null;
    }

    public static void g() {
        DataInputStream dataInputStream;
        InputStream inputStream;
        block6: {
            inputStream = null;
            dataInputStream = null;
            inputStream = "".getClass().getResourceAsStream("/monster.dat");
            if (inputStream != null) break block6;
            h.b.a(inputStream);
            h.b.a(null);
            return;
        }
        try {
            int n2;
            dataInputStream = new DataInputStream(inputStream);
            int n3 = dataInputStream.readShort();
            ah = new short[n3];
            ai = new int[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                d.a.ah[n2] = dataInputStream.readShort();
                d.a.ai[n2] = dataInputStream.readInt();
            }
            n2 = dataInputStream.readInt();
            aj = new byte[n2];
            dataInputStream.read(aj);
        }
        catch (Exception exception) {
            try {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
            catch (Throwable throwable) {
                h.b.a(inputStream);
                h.b.a(dataInputStream);
                throw throwable;
            }
            h.b.a(inputStream);
            h.b.a(dataInputStream);
            return;
        }
        h.b.a(inputStream);
        h.b.a(dataInputStream);
        return;
    }

    public static void a(h h2) {
        if (h2 == null) {
            return;
        }
        d.a.i();
        h2.b();
    }

    public static h[] h() {
        Object object = h.b.c("sword");
        if (object == null) {
            return null;
        }
        object = new ByteArrayInputStream((byte[])object);
        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
        h[] hArray = d.a.b(dataInputStream);
        h.b.a((InputStream)object);
        h.b.a(dataInputStream);
        return hArray;
    }

    private static h[] b(DataInputStream dataInputStream) {
        try {
            short s2 = dataInputStream.readShort();
            if (s2 <= 0) {
                return null;
            }
            h[] hArray = new h[s2];
            for (int i2 = 0; i2 < hArray.length; ++i2) {
                if (dataInputStream.readBoolean()) continue;
                hArray[i2] = new h();
                if (hArray[i2].a(dataInputStream)) continue;
                hArray[i2] = null;
                return null;
            }
            return hArray;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return null;
        }
    }

    public static void a(h[] objectArray) {
        objectArray = d.a.b(objectArray);
        h.b.a("sword", (byte[])objectArray);
    }

    public static void i() {
        if (y == null) {
            return;
        }
        byte[] byArray = y.s();
        r.put(new Integer(d.a.y.d), byArray);
    }

    private static byte[] b(h[] hArray) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            if (hArray == null) {
                dataOutputStream.writeShort(0);
            } else {
                dataOutputStream.writeShort(hArray.length);
                for (int i2 = 0; i2 < hArray.length; ++i2) {
                    if (hArray[i2] == null) {
                        dataOutputStream.writeBoolean(true);
                        continue;
                    }
                    dataOutputStream.writeBoolean(false);
                    ((OutputStream)dataOutputStream).write(hArray[i2].a());
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
            h.b.a(byteArrayOutputStream);
            h.b.a(dataOutputStream);
        }
    }

    public static void a(Graphics graphics, byte by, int n2, int n3) {
        h.b.a(graphics, L, by * M, 0, M, L.getHeight(), n2, n3, 20);
    }

    public static g[] a(g[] gArray) {
        if (gArray == null) {
            return null;
        }
        int n2 = 0;
        for (int i2 = 0; i2 < gArray.length; ++i2) {
            if (!gArray[i2].s()) continue;
            ++n2;
        }
        g[] gArray2 = new g[n2];
        int n3 = 0;
        for (int i3 = 0; i3 < gArray.length; ++i3) {
            if (!gArray[i3].s()) continue;
            gArray2[n3] = gArray[i3];
            if (++n3 >= n2) break;
        }
        return gArray2;
    }

    public static c.f a(int n2) {
        Object object = d.a.a(ak, n2);
        if (object == null) {
            if ((n2 = h.b.a(al, n2)) < 0) {
                object = null;
            } else {
                n2 = am[n2];
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(an, n2, an.length - n2);
                object = new DataInputStream(byteArrayInputStream);
                c.f f2 = d.a.a((DataInputStream)object);
                h.b.a(byteArrayInputStream);
                h.b.a((InputStream)object);
                ak = d.a.a(ak, f2);
                object = f2;
            }
        }
        return object;
    }

    private static c.f[] a(c.f[] fArray, c.f f2) {
        if (f2 == null) {
            return fArray;
        }
        c.f[] fArray2 = null;
        if (fArray == null) {
            c.f[] fArray3 = new c.f[1];
            fArray2 = fArray3;
            fArray3[0] = f2;
        } else {
            int n2;
            fArray2 = new c.f[fArray.length + 1];
            for (n2 = 0; n2 < fArray.length && fArray[n2].p < f2.p; ++n2) {
                fArray2[n2] = fArray[n2];
            }
            fArray2[n2] = f2;
            ++n2;
            while (n2 < fArray2.length) {
                fArray2[n2] = fArray[n2 - 1];
                ++n2;
            }
        }
        return fArray2;
    }

    public static void d(short s2) {
        if (ak == null) {
            return;
        }
        for (int i2 = 0; i2 < ak.length; ++i2) {
            if (s2 != d.a.ak[i2].p) continue;
            if (ak.length == 1) {
                ak = null;
                return;
            }
            c.f[] fArray = new c.f[ak.length - 1];
            System.arraycopy(ak, 0, fArray, 0, i2);
            System.arraycopy(ak, i2 + 1, fArray, i2, ak.length - i2 - 1);
            return;
        }
    }

    public static c.b b(int n2) {
        c.f f2 = d.a.a(n2);
        if (f2 == null || f2.q != 12) {
            return null;
        }
        return (c.b)f2;
    }

    public static c.a c(int n2) {
        c.f f2 = d.a.a((c.f[])v, n2);
        if (f2 == null) {
            return null;
        }
        return (c.a)f2;
    }

    public static b.a d(int n2) {
        c.f f2 = d.a.a((c.f[])ao, n2);
        if (f2 == null) {
            return null;
        }
        return (b.a)f2;
    }

    private static c.f a(c.f[] fArray, int n2) {
        if (fArray == null) {
            return null;
        }
        int n3 = 0;
        int n4 = fArray.length - 1;
        int n5 = 0;
        while (n3 <= n4) {
            n5 = (n3 + n4) / 2;
            if (fArray[n5].p == n2) {
                return fArray[n5];
            }
            if (fArray[n5].p < n2) {
                n3 = n5 + 1;
                continue;
            }
            n4 = n5 - 1;
        }
        return null;
    }

    public static int a(int n2, int n3) {
        c.f f2 = d.a.a(n2);
        return d.a.b(f2, n3);
    }

    public static boolean b(int n2, int n3) {
        if (n2 == -1) {
            return E + n3 >= 0;
        }
        c.f f2 = d.a.a(n2);
        if (f2 == null || n3 > 256) {
            return false;
        }
        int n4 = 0;
        if (n3 == 0) {
            return true;
        }
        if (n3 > 0) {
            if (f2.b()) {
                n2 = d.a.b(f2);
                if (n2 < 0) {
                    return true;
                }
                n4 = G[n2];
                return n4 + n3 <= 256;
            }
            n2 = 0;
            for (int i2 = 0; i2 < F.length; ++i2) {
                if (F[i2] != null) continue;
                ++n2;
            }
            return n2 + n3 <= 256;
        }
        f2 = d.a.a(n2);
        n4 = d.a.a(f2);
        return n4 + n3 >= 0;
    }

    public static boolean e(int n2) {
        if (n2 < 0 || n2 >= F.length) {
            return false;
        }
        for (int i2 = 0; i2 < C.length; ++i2) {
            if (C[i2].i(n2) < 0) continue;
            return true;
        }
        return false;
    }

    public static int[] a(byte by, boolean bl) {
        Vector<Integer> vector = new Vector<Integer>();
        for (int i2 = 0; i2 < F.length; ++i2) {
            if (F[i2] == null || by != 0 && c.f.a(d.a.F[i2].q) != by || bl && d.a.e(i2)) continue;
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
        for (int i2 = 0; i2 < F.length; ++i2) {
            if (F[i2] == null || d.a.F[i2].q != by) continue;
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

    public static boolean a(c.f f2, int n2) {
        if (f2 == null || n2 <= 0) {
            return false;
        }
        int n3 = 0;
        boolean bl = false;
        if (f2.b()) {
            n3 = d.a.b(f2);
            bl = d.a.d(n3, n2);
        } else {
            int n4 = 0;
            for (int i2 = 0; i2 < n2; ++i2) {
                n3 = d.a.b(f2);
                bl = d.a.d(n3, 1);
                for (int i3 = 0; i3 < C.length; ++i3) {
                    n4 = C[i3].i(n3);
                    if (n4 < 0) continue;
                    C[i3].c(-1, n4);
                }
            }
        }
        return bl;
    }

    public static int b(c.f f2, int n2) {
        int n3;
        block6: {
            block4: {
                block5: {
                    if (f2 == null || n2 <= 0) {
                        return -1;
                    }
                    n3 = -1;
                    if (!f2.b()) break block4;
                    n3 = d.a.b(f2);
                    if (n3 >= 0) break block5;
                    n3 = d.a.q();
                    if (n3 < 0) {
                        return -2;
                    }
                    n2 = n2 > 256 ? 256 : n2;
                    d.a.F[n3] = f2;
                    d.a.G[n3] = n2;
                    break block6;
                }
                int n4 = n3;
                G[n4] = G[n4] + n2;
                if (G[n3] <= 256) break block6;
                d.a.G[n3] = 256;
                break block6;
            }
            for (int i2 = 0; i2 < n2; ++i2) {
                n3 = d.a.q();
                if (n3 < 0) {
                    return -1;
                }
                d.a.F[n3] = f2;
                d.a.G[n3] = 1;
            }
        }
        return n3;
    }

    private static int q() {
        for (int i2 = 0; i2 < F.length; ++i2) {
            if (F[i2] != null) continue;
            return i2;
        }
        return -1;
    }

    public static c.f f(int n2) {
        if (n2 < 0 || n2 >= F.length) {
            return null;
        }
        return F[n2];
    }

    public static int g(int n2) {
        if (n2 < 0 || n2 >= F.length) {
            return 0;
        }
        return G[n2];
    }

    private static int b(c.f f2) {
        if (f2 == null || F == null) {
            return -1;
        }
        for (int i2 = 0; i2 < F.length; ++i2) {
            if (F[i2] == null || F[i2] != f2) continue;
            return i2;
        }
        return -1;
    }

    public static int a(c.f f2) {
        if (f2 == null) {
            return 0;
        }
        int n2 = 0;
        if (f2.b()) {
            int n3 = d.a.b(f2);
            if (n3 < 0 || n3 >= G.length) {
                return 0;
            }
            n2 = G[n3];
        } else {
            for (int i2 = 0; i2 < F.length; ++i2) {
                if (F[i2] == null || F[i2] != f2) continue;
                ++n2;
            }
        }
        return n2;
    }

    private static void a(c.f[] fArray) {
        if (fArray == null || fArray.length <= 1) {
            return;
        }
        c.f f2 = null;
        for (int i2 = 1; i2 < fArray.length; ++i2) {
            for (int i3 = fArray.length - 1; i3 >= i2; --i3) {
                if (fArray[i3].p >= fArray[i3 - 1].p) continue;
                f2 = fArray[i3 - 1];
                fArray[i3 - 1] = fArray[i3];
                fArray[i3] = f2;
            }
        }
    }

    public static void j() {
        if (aq == null) {
            return;
        }
        try {
            aq.stop();
            aq.deallocate();
            aq = null;
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
                        d.a.j();
                        af = -1;
                        h.b.a(null);
                        return;
                    }
                    if (!o[0] || n2 == af && aq != null) break block12;
                    if (aq != null) {
                        d.a.j();
                    }
                    if ((inputStream = main.b.a.getClass().getResourceAsStream("/midi/" + n2 + ".mid")) == null) break block13;
                    aq = Manager.createPlayer((InputStream)inputStream, (String)"audio/midi");
                    aq.prefetch();
                    aq.setLoopCount(n3);
                    aq.start();
                }
                af = n2;
                ag = n3;
                return;
            }
            h.b.a(inputStream);
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
        finally {
            h.b.a(inputStream);
        }
    }

    public static void h(int n2) {
        if ((E += n2) > 65534) {
            E = 65534;
            return;
        }
        if (E < 0) {
            E = 0;
        }
    }

    public static void a(int n2, byte by, int n3) {
        switch (by) {
            case 1: {
                by = (byte)n3;
                if (n2 == -1) {
                    E = h.b.b(0, by, 65534, 0);
                    break;
                }
                c.f f2 = d.a.a(n2);
                c.f f3 = f2;
                f3 = f2;
                int n4 = d.a.b(f2);
                if (n4 >= 0) {
                    d.a.G[n4] = h.b.b(0, by, 256, 0);
                    if (G[n4] <= 0) {
                        d.a.d(n4, 999);
                    }
                }
                return;
            }
            case 0: {
                if (n2 == -1) {
                    E = h.b.b(E, n3, 65534, 0);
                    return;
                }
                if (n3 > 0) {
                    d.a.a(n2, n3);
                    return;
                }
                if (n3 >= 0) break;
                by = (byte)(-n3);
                c.f f4 = d.a.a(n2);
                d.a.a(f4, (int)by);
            }
        }
    }

    public static boolean d(int n2, int n3) {
        if (n2 < 0 || n2 >= F.length || n3 <= 0) {
            return false;
        }
        int n4 = n2;
        G[n4] = G[n4] - n3;
        if (G[n2] <= 0) {
            d.a.G[n2] = 0;
            d.a.F[n2] = null;
        }
        return true;
    }

    public static f.b a(String object) {
        Object object2;
        f[] fArray;
        if (object == null || ((String)object).trim().length() == 0) {
            return null;
        }
        Object object3 = null;
        if (n != null) {
            for (int i2 = 0; i2 < n.length; ++i2) {
                if (n[i2] == null || (object3 = n[i2].a((String)object)) == null) continue;
                return object3;
            }
        }
        if (y != null && (fArray = y.g()) != null) {
            for (int i3 = 0; i3 < fArray.length; ++i3) {
                if (fArray[i3] == null || fArray[i3].c == null || (object3 = fArray[i3].c.a((String)object)) == null) continue;
                return object3;
            }
        }
        if (main.b.a.i != null) {
            Vector vector = main.b.a.i.g();
            b b2 = null;
            for (int i4 = 0; i4 < vector.size(); ++i4) {
                b2 = (b)vector.elementAt(i4);
                if (b2.c == null || (object3 = b2.c.a((String)object)) == null) continue;
                return object3;
            }
        }
        object3 = e.a.a("/f.pak");
        byte[] byArray = ((e.a)object3).b((String)object + ".fra");
        object3 = byArray;
        if (byArray == null) {
            object2 = null;
        } else {
            object3 = new ByteArrayInputStream((byte[])object3);
            DataInputStream dataInputStream = new DataInputStream((InputStream)object3);
            object = new f.b((String)object);
            ((f.b)object).a(dataInputStream);
            h.b.a((InputStream)object3);
            h.b.a(dataInputStream);
            object2 = object;
        }
        object3 = object2;
        return object2;
    }

    public static String i(int n2) {
        if (j == null || i == null || j.length != i.length) {
            return "";
        }
        for (int i2 = 0; i2 < i.length; ++i2) {
            if (i[i2] != n2) continue;
            return j[i2];
        }
        return "";
    }

    public static f.d b(String string) {
        if (string == null || string.trim().length() == 0) {
            return null;
        }
        f.d d2 = null;
        for (int i2 = 0; i2 < ar.size(); ++i2) {
            d2 = (f.d)ar.elementAt(i2);
            if (!d2.a().equals(string)) continue;
            return d2;
        }
        return null;
    }

    public static void k() {
        ar.removeAllElements();
    }

    public static void a(f.d d2) {
        if (d2 == null) {
            return;
        }
        ar.addElement(d2);
    }

    /*
     * Unable to fully structure code
     */
    public static f.d a(String var0, e.a var1_1) {
        var2_2 = var0;
        if (var2_2 == null) ** GOTO lbl-1000
        var3_3 = null;
        if (d.a.y != null) {
            var3_3 = d.a.y.a((String)var2_2);
        }
        if (var3_3 != null) {
            v0 = var3_3;
        } else {
            block13: {
                var3_3 = var2_2;
                if (var3_3 == null || d.a.n == null) {
                    v1 = null;
                } else {
                    for (var4_4 = 0; var4_4 < d.a.n.length; ++var4_4) {
                        if (d.a.n[var4_4] == null || !d.a.n[var4_4].a().equals(var3_3)) continue;
                        v1 = d.a.n[var4_4];
                        break block13;
                    }
                    v1 = var3_3 = null;
                }
            }
            if (v1 != null) {
                v0 = var3_3;
            } else if (main.b.a.i != null) {
                var3_3 = main.b.a.i.g();
                var4_5 = null;
                for (var5_6 = var3_3.size() - 1; var5_6 >= 0; --var5_6) {
                    var4_5 = (b)var3_3.elementAt(var5_6);
                    if (var4_5.b == null || !var4_5.b.equals(var2_2) || var4_5.c == null) continue;
                    v0 = var4_5.c;
                    break;
                }
            } else lbl-1000:
            // 3 sources

            {
                v0 = var2_2 = null;
            }
        }
        if (v0 != null) {
            return var2_2;
        }
        var3_3 = var1_1;
        var2_2 = var0;
        var2_2 = var2_2 == null || var3_3 == null ? null : var3_3.d((String)var2_2);
        if (var2_2 != null) {
            return var2_2;
        }
        var2_2 = d.a.d(var0);
        return var2_2;
    }

    private static f.d d(String object) {
        String string = "/ani/" + (String)object + ".ani";
        InputStream inputStream = null;
        DataInputStream dataInputStream = null;
        inputStream = "".getClass().getResourceAsStream(string);
        if (inputStream == null) {
            return null;
        }
        try {
            dataInputStream = new DataInputStream(inputStream);
            object = new f.d((String)object);
            ((f.d)object).a(dataInputStream);
            return object;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return null;
        }
        finally {
            h.b.a(inputStream);
            h.b.a(dataInputStream);
        }
    }

    public static String j(int n2) {
        return q[n2];
    }

    public static boolean l() {
        return b >= 2;
    }

    public static boolean m() {
        return c >= 3;
    }

    public static boolean n() {
        return a || d;
    }

    public static boolean o() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeByte(b);
            dataOutputStream.writeByte(c);
            dataOutputStream.writeBoolean(a);
            dataOutputStream.writeBoolean(d);
            byte[] byArray = byteArrayOutputStream.toByteArray();
            h.b.a("swd_pl", byArray);
            return true;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return false;
        }
        finally {
            h.b.a(byteArrayOutputStream);
            h.b.a(dataOutputStream);
        }
    }

    public static void p() {
        Object object = h.b.c("swd_pl");
        if (object == null) {
            return;
        }
        object = new ByteArrayInputStream((byte[])object);
        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
        try {
            b = dataInputStream.readByte();
            c = dataInputStream.readByte();
            a = dataInputStream.readBoolean();
            d = dataInputStream.readBoolean();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
        finally {
            h.b.a((InputStream)object);
            h.b.a(dataInputStream);
        }
    }
}

