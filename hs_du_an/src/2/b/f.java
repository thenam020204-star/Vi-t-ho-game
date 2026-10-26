/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b;

import b.a;
import b.b;
import b.e;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import main.c;

public class f
extends b {
    public static short[][] r = null;
    private static short[][] o = new short[][]{{75, 100, 35, 20, 8, 6, 10, 24, 93, 1}, {552, 344, 203, 42, 11, 10, 19, 27, 109, 4}, {1182, 1205, 305, 339, 44, 35, 43, 31, 87, 13}, {1014, 836, 173, 342, 30, 35, 14, 27, 65, 8}};
    protected int[] s = new int[13];
    public static final byte[] t = new byte[]{1, 2, 4, 8, 16};
    public static final byte[] u = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    public static String[] v = new String[]{b.a.p[19], b.a.p[20], b.a.p[21], b.a.p[22], b.a.p[23], b.a.p[24], b.a.p[24], b.a.p[25], b.a.p[25], b.a.p[26], b.a.p[26]};
    public static final byte[] w = new byte[]{4, 6, 7, 8, 9, 10, 10, 11, 11, 16, 16};
    private byte p = (byte)4;
    private short q = 0;
    private int I = 0;
    public int[] x = null;
    public int[] y = null;
    private int[] J = null;
    private byte K = 0;
    public String z = "";
    private boolean L = false;
    protected int A = 1;
    protected int B = 0;
    private int M = 100;
    private int N = 0;
    public int C = 0;
    public int D = 0;
    public int E = 0;
    private String O = "";
    private short P = 0;
    private short Q = 0;
    private short R = 0;
    private short S = 0;
    private short T = 0;
    private int U = 0;
    private int V = 0;
    private int W = 0;
    private int X = 0;
    private int Y = 0;
    public byte F = 0;
    private byte Z = 0;
    private f.c[] aa = null;
    private f.c[] ab = null;
    private f.c[] ac = null;
    private int ad = 0;
    private int ae = 150;
    public boolean G = false;
    public short H = 0;

    public f() {
        super((byte)6);
        this.l = 20;
        this.m = 50;
        this.J = new int[11];
        for (int i2 = 0; i2 < this.J.length; ++i2) {
            this.J[i2] = -1;
        }
        this.x = new int[3];
        this.y = new int[3];
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void b(int n2, int n3) {
        void var4_28;
        boolean bl;
        void var4_21;
        boolean bl2;
        if (b.a.r != null) {
            return;
        }
        if (n2 == 53 && f4.g != 0) {
            c c2;
            void var4_14;
            f f2 = f4;
            int n4 = f2.f();
            int n5 = f2.g();
            byte by = f2.i;
            switch (by) {
                case 3: {
                    void var4_12;
                    --var4_12;
                    break;
                }
                case 2: {
                    void var4_13;
                    ++var4_13;
                    break;
                }
                case 1: {
                    --n4;
                    break;
                }
                case 0: {
                    ++n4;
                }
            }
            e e2 = b.a.x.c(n4, (int)var4_14);
            if (e2 == null) {
                c2 = null;
            } else if (e2.o() != 1 || !e2.p) {
                System.out.println(e2.o());
                c2 = null;
            } else {
                c c3;
                if (!e2.q && e2.e >= 0 && e2.e <= 7) {
                    byte by2 = b.b.b(by);
                    e2.a(by2, false);
                }
                c2 = c3 = b.a.a(b.a.x.c, e2.f);
            }
            b.a.r = c2;
            return;
        }
        int n6 = n3;
        f f3 = f4;
        if (f3.g == 0) {
            bl2 = false;
        } else {
            byte by = b.f.n(n6);
            if (by == 4) {
                bl2 = false;
            } else if (f3.i == by && f3.o(f3.i)) {
                f3.g = 0;
                f3.K = 0;
                f3.a(f3.i, true);
                bl2 = true;
            } else {
                f3.b(by);
                f3.a(by, false);
                bl2 = false;
            }
        }
        n6 = n3;
        f3 = f4;
        if (f3.g != 0) return;
        if (f3.K < 16) {
            f3.a(f3.i);
            f3.K = (byte)(f3.K + 4);
            if (b.a.i) {
                f f4 = null;
                f f5 = null;
                boolean bl3 = false;
                int n7 = 0;
                for (int i2 = 1; i2 < b.a.B.length; ++i2) {
                    f4 = b.a.B[i2 - 1];
                    f5 = b.a.B[i2];
                    int n8 = Math.abs(f4.j - f5.j);
                    if (n8 + (n7 = Math.abs(f4.k - f5.k)) <= 16) continue;
                    if (f5.i != f4.p) {
                        f5.b(f4.p);
                    }
                    f5.a(f5.i, true);
                    f5.a(f5.i);
                }
            }
        }
        if (f3.K < 16) return;
        f3.K = 0;
        boolean bl4 = false;
        while (var4_21 < b.a.B.length) {
            b.a.B[var4_21].p = b.a.B[var4_21].i;
            ++var4_21;
        }
        if (n6 == 0 || b.f.n(n6) != f3.i || !f3.o(f3.i)) {
            void var4_23;
            boolean bl5 = false;
            while (var4_23 < b.a.B.length) {
                b.a.B[var4_23].a(b.a.B[var4_23].i, false);
                ++var4_23;
            }
            f3.g = 1;
        }
        if ((b.a.r = b.a.x.a((f4 = f3).f(), f4.g(), (byte)2)) != null) {
            void var4_25;
            boolean bl6 = false;
            while (var4_25 < b.a.B.length) {
                b.a.B[var4_25].a(b.a.B[var4_25].i, false);
                ++var4_25;
            }
            f3.g = 1;
            return;
        }
        int n9 = b.a.B[0].j;
        n3 = b.a.B[0].k;
        if (b.a.x == null) return;
        if (b.a.r != null) return;
        e e3 = b.a.x.a(n9 - 16, n3 - 16, 48, 48, 3);
        if (e3 == null) return;
        switch (e3.r) {
            case 2: {
                if (e3.v) return;
                main.b.a.i.a(d.c.a(e3));
                bl = true;
                break;
            }
            case 1: {
                b.a.r = b.a.x.a(e3);
                if (b.a.r == null) return;
                bl = true;
                break;
            }
            default: {
                return;
            }
        }
        if (!bl) return;
        boolean bl7 = false;
        while (var4_28 < b.a.B.length) {
            b.a.B[var4_28].a(b.a.B[var4_28].i, false);
            ++var4_28;
        }
        f3.g = 1;
    }

    private static byte n(int n2) {
        switch (n2) {
            case 50: {
                return 3;
            }
            case 52: {
                return 1;
            }
            case 54: {
                return 0;
            }
            case 56: {
                return 2;
            }
        }
        return 4;
    }

    private boolean o(int n2) {
        switch (n2) {
            case 2: {
                return b.a.x.b(this.j, this.k + 16);
            }
            case 3: {
                return b.a.x.b(this.j, this.k - 16);
            }
            case 1: {
                return b.a.x.b(this.j - 16, this.k);
            }
            case 0: {
                return b.a.x.b(this.j + 16, this.k);
            }
        }
        return false;
    }

    public final int c(int n2) {
        if (n2 < 0 || n2 >= this.s.length) {
            return 0;
        }
        switch (n2) {
            case 1: {
                return this.B;
            }
            case 0: {
                return this.A;
            }
        }
        return this.s[n2];
    }

    public final void a(byte by, int n2) {
        switch (by) {
            case 1: {
                this.p(n2);
                return;
            }
            case 0: {
                this.r(n2);
                return;
            }
            case 14: {
                this.s(n2);
                return;
            }
        }
        this.d(by, n2);
    }

    public final void b(byte by, int n2) {
        switch (by) {
            case 1: {
                this.p(this.B + n2);
                return;
            }
            case 0: {
                this.r(this.A + n2);
                return;
            }
            case 14: {
                this.s(this.M + n2);
                return;
            }
            case 13: {
                this.d(n2);
                return;
            }
        }
        this.d(by, this.c((int)by) + n2);
    }

    public final f.c[] d(int n2) {
        n2 = this.N + n2;
        int n3 = this.N;
        this.N = e.a.b.c(n2, 710, 0);
        Vector<f.c> vector = new Vector<f.c>();
        f.c c2 = null;
        for (int i2 = 0; i2 < b.a.t.length; ++i2) {
            if (b.a.t[i2].a[23] != this.f || b.a.t[i2].a[25] <= n3 || b.a.t[i2].a[25] > this.N || (c2 = b.a.c((int)b.a.t[i2].r)) == null) continue;
            this.a(c2.r);
            vector.addElement(c2);
        }
        if (vector.size() == 0) {
            return null;
        }
        Object[] objectArray = new f.c[vector.size()];
        vector.copyInto(objectArray);
        return objectArray;
    }

    private void d(byte by, int n2) {
        if (by < 0 || by >= this.s.length || by == 1 || by == 0) {
            return;
        }
        switch (by) {
            case 2: {
                this.s[by] = e.a.b.b(0, n2, this.s[3], 0);
                return;
            }
            case 4: {
                this.s[by] = e.a.b.b(0, n2, this.s[5], 0);
                return;
            }
            case 6: {
                this.s[by] = e.a.b.b(0, n2, this.s[7], 0);
                return;
            }
        }
        this.s[by] = e.a.b.b(0, n2, 9999, 0);
    }

    public final int o() {
        return o[this.f][0] + (this.A - o[this.f][9]) * o[this.f][8];
    }

    private boolean p(int n2) {
        if (n2 > Integer.MAX_VALUE) {
            n2 = Integer.MAX_VALUE;
        } else if (n2 < 0) {
            n2 = 0;
        }
        if (this.A >= 30) {
            return false;
        }
        int n3 = this.o();
        if (n2 > n3) {
            while (n2 > n3) {
                n2 -= n3;
                this.b((byte)0, 1);
                n3 = this.o();
                this.q(this.A);
                this.d(this.e(this.A));
            }
            this.B = n2;
            return true;
        }
        this.B = n2;
        return false;
    }

    public final short e(int n2) {
        if ((n2 -= o[this.f][9]) < 0 || n2 >= r[this.f].length) {
            return -1;
        }
        return r[this.f][n2];
    }

    private void q(int n2) {
        switch (this.f) {
            case 0: {
                this.s[3] = this.s[3] + 98;
                this.s[5] = this.s[5] + 33;
                this.s[7] = this.s[7] + 20;
                this.s[8] = this.s[8] + 2;
                this.s[9] = this.s[9] + 3;
                this.s[10] = this.s[10] + 3;
                this.s[11] = this.s[11] + (n2 % 2 == 0 ? 1 : 0);
                break;
            }
            case 1: {
                this.s[3] = this.s[3] + 75;
                this.s[5] = this.s[5] + 51;
                this.s[7] = this.s[7] + 11;
                this.s[8] = this.s[8] + 2;
                this.s[9] = this.s[9] + 2;
                this.s[10] = this.s[10] + 3;
                this.s[11] = this.s[11] + 1;
                break;
            }
            case 2: {
                this.s[3] = this.s[3] + 93;
                this.s[5] = this.s[5] + 24;
                this.s[7] = this.s[7] + 26;
                this.s[8] = this.s[8] + 3;
                this.s[9] = this.s[9] + (n2 % 2 == 0 ? 3 : 2);
                this.s[10] = this.s[10] + 3;
                this.s[11] = this.s[11] + (n2 % 2 == 0 ? 1 : 0);
                break;
            }
            case 3: {
                this.s[3] = this.s[3] + 105;
                this.s[5] = this.s[5] + 22;
                this.s[7] = this.s[7] + 43;
                this.s[8] = this.s[8] + 3;
                this.s[9] = this.s[9] + 4;
                this.s[10] = this.s[10] + 3;
                this.s[11] = this.s[11] + (n2 % 2 == 0 ? 1 : 0);
                break;
            }
            default: {
                return;
            }
        }
        this.s[2] = this.s[3];
        this.s[4] = this.s[5];
        this.s[6] = this.s[7];
    }

    private static boolean a(f.b[] bArray, short s2) {
        if (bArray == null) {
            return false;
        }
        for (int i2 = 0; i2 < bArray.length; ++i2) {
            if (bArray[i2] == null || bArray[i2].r != s2) continue;
            return true;
        }
        return false;
    }

    private void r(int n2) {
        if (n2 < this.A + 1) {
            return;
        }
        if (n2 <= 0) {
            n2 = 1;
        } else if (n2 > 30) {
            n2 = 30;
        }
        for (int i2 = this.A + 1; i2 < n2; ++i2) {
            this.q(i2);
            this.d(this.e(i2));
        }
        this.B = 0;
        this.A = n2;
        this.a((byte)2, this.c(3));
        this.a((byte)4, this.c(5));
        this.a((byte)6, this.c(7));
    }

    public final void a(byte by, byte by2, short s2) {
        switch (by) {
            case 0: {
                this.b(by2, (int)s2);
                return;
            }
            case 1: {
                this.a(by2, (int)s2);
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3, short s2) {
        if (((b)((Object)b2)).c == null) {
            return;
        }
        e.b.b b2 = ((b)((Object)b2)).c.b(20);
        b2.a(graphics, n2, n3);
    }

    public final boolean p() {
        return this.L;
    }

    public final int q() {
        return this.M;
    }

    private void s(int n2) {
        this.M = e.a.b.c(n2, 100, 0);
    }

    public final void a(boolean n2) {
        this.L = n2;
        if (n2 != 0) {
            n2 = 0;
            for (int i2 = 0; i2 < b.a.k[this.f].length; ++i2) {
                n2 = b.a.a(b.a.k[this.f][i2], 1);
                if (n2 < 0 || n2 >= 256) continue;
                this.c(n2, i2);
            }
            return;
        }
        f.b b2 = null;
        for (int i3 = 0; i3 < b.a.k[this.f].length; ++i3) {
            this.f(i3);
            b2 = b.a.e(this.J[i3]);
            if (b2 == null) {
                b.a.k[this.f][i3] = -1;
                continue;
            }
            b.a.k[this.f][i3] = b2.r;
            b.a.d(this.J[i3], 1);
        }
    }

    public final int r() {
        int n2 = this.n();
        return (this.ad - n2) * 100 / (this.ae - n2);
    }

    public final void s() {
        this.ad = this.n();
    }

    public final void t() {
        this.ad += e.a.b.c(50);
    }

    public final void u() {
        this.ad += 4;
        if (this.ad > this.ae) {
            this.ad = this.ae;
        }
    }

    public final boolean v() {
        return this.ad >= this.ae;
    }

    public final boolean w() {
        return this.s[2] <= 0;
    }

    public final byte[] x() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            int n2;
            dataOutputStream.writeShort(this.f);
            dataOutputStream.writeUTF(this.z);
            dataOutputStream.writeUTF(this.O);
            dataOutputStream.writeShort(this.A);
            dataOutputStream.writeBoolean(this.L);
            dataOutputStream.writeShort(this.s[2]);
            dataOutputStream.writeShort(this.s[4]);
            dataOutputStream.writeShort(this.s[6]);
            dataOutputStream.writeShort(this.M);
            dataOutputStream.writeUTF(this.b);
            dataOutputStream.writeByte(this.F);
            dataOutputStream.writeByte(this.Z);
            dataOutputStream.writeShort(this.N);
            dataOutputStream.writeInt(this.B);
            dataOutputStream.writeShort(this.s[3]);
            dataOutputStream.writeShort(this.s[5]);
            dataOutputStream.writeShort(this.s[7]);
            dataOutputStream.writeShort(this.s[8]);
            dataOutputStream.writeShort(this.s[10]);
            dataOutputStream.writeShort(this.s[9]);
            dataOutputStream.writeShort(this.s[11]);
            dataOutputStream.writeShort(this.s[12]);
            for (n2 = 0; n2 < this.J.length; ++n2) {
                dataOutputStream.writeByte(this.J[n2]);
            }
            if (this.aa == null) {
                dataOutputStream.writeByte(0);
            } else {
                dataOutputStream.writeByte(this.aa.length);
                for (n2 = 0; n2 < this.aa.length; ++n2) {
                    dataOutputStream.writeShort(this.aa[n2].r);
                }
            }
            if (this.ab == null) {
                dataOutputStream.writeByte(0);
            } else {
                dataOutputStream.writeByte(this.ab.length);
                for (n2 = 0; n2 < this.ab.length; ++n2) {
                    dataOutputStream.writeShort(this.ab[n2].r);
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

    public final void b(DataInputStream dataInputStream) {
        try {
            int n2;
            this.f = dataInputStream.readShort();
            this.z = dataInputStream.readUTF();
            this.O = dataInputStream.readUTF();
            this.A = dataInputStream.readShort();
            this.L = dataInputStream.readBoolean();
            this.s[2] = dataInputStream.readShort();
            this.s[4] = dataInputStream.readShort();
            this.s[6] = dataInputStream.readShort();
            this.M = dataInputStream.readShort();
            this.b = dataInputStream.readUTF();
            this.F = dataInputStream.readByte();
            this.Z = dataInputStream.readByte();
            this.N = dataInputStream.readShort();
            this.B = dataInputStream.readInt();
            this.s[3] = dataInputStream.readShort();
            this.s[5] = dataInputStream.readShort();
            this.s[7] = dataInputStream.readShort();
            this.s[8] = dataInputStream.readShort();
            this.s[10] = dataInputStream.readShort();
            this.s[9] = dataInputStream.readShort();
            this.s[11] = dataInputStream.readShort();
            this.s[12] = dataInputStream.readShort();
            for (int i2 = 0; i2 < this.J.length; ++i2) {
                this.J[i2] = e.a.b.a((int)dataInputStream.readByte());
            }
            Vector<f.c> vector = new Vector<f.c>();
            f.c c2 = null;
            int n3 = dataInputStream.readByte();
            if (n3 > 0) {
                for (n2 = 0; n2 < n3; ++n2) {
                    c2 = b.a.c((int)dataInputStream.readShort());
                    if (c2 == null) continue;
                    vector.addElement(c2);
                }
                if (vector.size() > 0) {
                    this.aa = new f.c[vector.size()];
                    vector.copyInto(this.aa);
                }
            }
            vector.removeAllElements();
            n2 = dataInputStream.readByte();
            if (n2 > 0) {
                for (n3 = 0; n3 < n2; ++n3) {
                    c2 = b.a.b((int)dataInputStream.readShort());
                    if (c2 == null) continue;
                    vector.addElement(c2);
                }
                if (vector.size() > 0) {
                    this.ab = new f.c[vector.size()];
                    vector.copyInto(this.ab);
                }
            }
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public void a(DataInputStream dataInputStream) {
        try {
            this.f = dataInputStream.readShort();
            this.z = dataInputStream.readUTF();
            this.O = dataInputStream.readUTF();
            this.b = dataInputStream.readUTF();
            this.L = dataInputStream.readBoolean();
            short s2 = dataInputStream.readShort();
            this.A = o[this.f][9];
            this.d(this.e(this.A));
            f f2 = this;
            f2.s[3] = o[f2.f][1];
            f2.s[5] = o[f2.f][2];
            f2.s[7] = o[f2.f][3];
            f2.s[8] = o[f2.f][4];
            f2.s[9] = o[f2.f][5];
            f2.s[10] = o[f2.f][6];
            f2.s[11] = o[f2.f][7];
            this.a((byte)0, (int)s2);
            this.F = dataInputStream.readByte();
            this.Z = dataInputStream.readByte();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public static int a(int n2, int n3, f f2) {
        int n4;
        if (f2 == null || n3 == 0) {
            return 0;
        }
        int n5 = 0;
        int n6 = n2;
        switch (n6) {
            case 16: {
                n4 = 4;
                break;
            }
            case 8: {
                n4 = 1;
                break;
            }
            case 4: {
                n4 = 8;
                break;
            }
            case 2: {
                n4 = 16;
                break;
            }
            case 1: {
                n4 = 2;
                break;
            }
            default: {
                n4 = 32;
            }
        }
        n5 = n4;
        n6 = n2;
        switch (n6) {
            case 16: {
                int n7 = 1;
                break;
            }
            case 8: {
                int n7 = 16;
                break;
            }
            case 4: {
                int n7 = 2;
                break;
            }
            case 2: {
                int n7 = 8;
                break;
            }
            case 1: {
                int n7 = 4;
                break;
            }
            default: {
                int n7 = n6 = 32;
            }
        }
        n5 = f2.h(n5) > 0 ? -n3 * 3 / 2 * (100 - f2.t(n2)) / 100 : (f2.h(n6) > 0 ? n3 * (f2.t(n6) + f2.h(n6)) / 100 : -n3 * (100 - f2.t(n2)) / 100);
        n5 += n5 * e.a.b.d(10) / 100;
        return n5;
    }

    public int l() {
        int n2 = this.c(8);
        f.a a2 = null;
        for (int i2 = 0; i2 < this.J.length; ++i2) {
            a2 = this.g(i2);
            n2 += a2 == null ? (short)0 : a2.a;
        }
        return n2 += this.x[0];
    }

    public int m() {
        int n2 = this.c(9);
        f.a a2 = null;
        for (int i2 = 0; i2 < this.J.length; ++i2) {
            a2 = this.g(i2);
            n2 += a2 == null ? (short)0 : a2.b;
        }
        return n2 += this.x[1];
    }

    public int n() {
        int n2 = this.c(11);
        f.a a2 = null;
        for (int i2 = 0; i2 < this.J.length; ++i2) {
            a2 = this.g(i2);
            n2 += a2 == null ? (short)0 : a2.c;
        }
        return n2 += this.x[2];
    }

    public final f.a[] y() {
        f.a[] aArray = new f.a[this.J.length];
        f.b b2 = null;
        for (int i2 = 0; i2 < aArray.length; ++i2) {
            b2 = b.a.e(this.J[i2]);
            if (b2 == null || !b2.c()) continue;
            aArray[i2] = (f.a)b2;
        }
        return aArray;
    }

    public final void c(int n2, int n3) {
        if (n3 < 0 || n3 >= ((f)((Object)a3)).J.length) {
            return;
        }
        f.b b2 = b.a.e(n2);
        if (b2 == null || b2.s != w[n3]) {
            return;
        }
        ((f)((Object)a3)).f(n3);
        ((f)((Object)a3)).J[n3] = n2;
        f.a a2 = (f.a)b2;
        Object object = a2;
        object = a3;
        f.a a3 = a2;
        if (object != null) {
            ((f)object).C += a3.d;
            ((f)object).D += a3.e;
            ((f)object).E += a3.f;
            ((f)object).b((byte)3, (int)a3.j);
            ((f)object).b((byte)5, (int)a3.k);
            ((f)object).b((byte)7, (int)a3.l);
            for (n3 = 0; n3 < a3.m.length; ++n3) {
                ((f)object).d((int)t[n3], (int)a3.m[n3]);
            }
            a3.a((f)object);
        }
    }

    public final void f(int n2) {
        if (n2 < 0 || n2 >= this.J.length) {
            return;
        }
        f.b b2 = b.a.e(this.J[n2]);
        if (b2 != null && b2.c()) {
            b2 = (f.a)b2;
            f f2 = this;
            if (f2 != null) {
                f2.C -= ((f.a)b2).d;
                f2.D -= ((f.a)b2).e;
                f2.E -= ((f.a)b2).f;
                f2.b((byte)3, (int)(-((f.a)b2).j));
                f2.b((byte)5, (int)(-((f.a)b2).k));
                f2.b((byte)7, (int)(-((f.a)b2).l));
                for (int i2 = 0; i2 < ((f.a)b2).m.length; ++i2) {
                    f2.d((int)t[i2], (int)(-((f.a)b2).m[i2]));
                }
                if (b2.s == 11) {
                    f2.c(((f.a)b2).o);
                    f2.c(((f.a)b2).p);
                    f2.c(((f.a)b2).q);
                }
            }
        }
        this.J[n2] = -1;
    }

    public final f.a g(int n2) {
        if (n2 < 0 || n2 >= ((f)((Object)b2)).J.length) {
            return null;
        }
        f.b b2 = b.a.e(((f)((Object)b2)).J[n2]);
        if (b2 == null) {
            return null;
        }
        return (f.a)b2;
    }

    public final void d(int n2, int n3) {
        if (n2 == 0) {
            return;
        }
        if ((n2 & 0x10) != 0) {
            this.T = (short)e.a.b.b(this.T, n3, 127, -128);
        }
        if ((n2 & 1) != 0) {
            this.P = (short)e.a.b.b(this.P, n3, 127, -128);
        }
        if ((n2 & 8) != 0) {
            this.S = (short)e.a.b.b(this.S, n3, 127, -128);
        }
        if ((n2 & 4) != 0) {
            this.R = (short)e.a.b.b(this.R, n3, 127, -128);
        }
        if ((n2 & 2) != 0) {
            this.Q = (short)e.a.b.b(this.Q, n3, 127, -128);
        }
    }

    public final short h(int n2) {
        int n3 = 0;
        switch (n2) {
            case 16: {
                n3 = this.T;
                break;
            }
            case 1: {
                n3 = this.P;
                break;
            }
            case 8: {
                n3 = this.S;
                break;
            }
            case 4: {
                n3 = this.R;
                break;
            }
            case 2: {
                n3 = this.Q;
                break;
            }
            default: {
                return 0;
            }
        }
        if (n2 == this.F) {
            n3 += this.Z;
        }
        if (n3 < 0) {
            n3 = 0;
        }
        return (short)n3;
    }

    private int t(int n2) {
        switch (n2) {
            case 16: {
                return 0;
            }
            case 1: {
                return 0;
            }
            case 8: {
                return 0;
            }
            case 4: {
                return 0;
            }
            case 2: {
                return 0;
            }
        }
        return 0;
    }

    public final void c(byte by, int n2) {
        if ((by & 0x10) != 0) {
            this.T = (short)e.a.b.b(0, n2, 127, -127);
        }
        if ((by & 1) != 0) {
            this.P = (short)e.a.b.b(0, n2, 127, -127);
        }
        if ((by & 8) != 0) {
            this.S = (short)e.a.b.b(0, n2, 127, -127);
        }
        if ((by & 4) != 0) {
            this.R = (short)e.a.b.b(0, n2, 127, -127);
        }
        if ((by & 2) != 0) {
            this.Q = (short)e.a.b.b(0, n2, 127, -127);
        }
    }

    public final void a(short s2) {
        f.c c2 = b.a.c((int)s2);
        if (c2 == null) {
            return;
        }
        if (this.aa == null) {
            this.aa = new f.c[1];
            this.aa[0] = c2;
            return;
        }
        if (b.f.a(this.aa, s2)) {
            return;
        }
        f.c[] cArray = new f.c[this.aa.length + 1];
        System.arraycopy(this.aa, 0, cArray, 0, this.aa.length);
        cArray[cArray.length - 1] = c2;
        this.aa = cArray;
    }

    public final void b(short s2) {
        f.c c2 = b.a.b((int)s2);
        if (c2 == null) {
            return;
        }
        if (this.ac == null) {
            this.ac = new f.c[1];
            this.ac[0] = c2;
            return;
        }
        if (b.f.a(this.ac, s2)) {
            return;
        }
        f.c[] cArray = new f.c[this.ac.length + 1];
        System.arraycopy(this.ac, 0, cArray, 0, this.ac.length);
        cArray[cArray.length - 1] = c2;
        this.ac = cArray;
    }

    public final void c(short s2) {
        if (this.ac == null) {
            return;
        }
        boolean bl = false;
        for (int i2 = 0; i2 < this.ac.length; ++i2) {
            if (this.ac[i2] == null || this.ac[i2].r != s2) continue;
            this.ac[i2] = null;
            bl = true;
        }
        if (bl) {
            Vector<f.c> vector = new Vector<f.c>();
            for (s2 = 0; s2 < this.ac.length; s2 = (short)(s2 + 1)) {
                if (this.ac[s2] == null) continue;
                vector.addElement(this.ac[s2]);
            }
            if (vector.size() == 0) {
                this.ac = null;
                return;
            }
            this.ac = new f.c[vector.size()];
            for (s2 = 0; s2 < this.ac.length; s2 = (short)(s2 + 1)) {
                this.ac[s2] = (f.c)vector.elementAt(s2);
            }
        }
    }

    public final void d(short s2) {
        f.c c2 = b.a.b((int)s2);
        if (c2 == null) {
            return;
        }
        if (this.ab == null) {
            this.ab = new f.c[1];
            this.ab[0] = c2;
            return;
        }
        if (b.f.a(this.ab, s2)) {
            return;
        }
        f.c[] cArray = new f.c[this.ab.length + 1];
        System.arraycopy(this.ab, 0, cArray, 0, this.ab.length);
        cArray[cArray.length - 1] = c2;
        this.ab = cArray;
    }

    public final f.c[] z() {
        return this.ab;
    }

    public final f.c[] A() {
        return this.aa;
    }

    public final void B() {
        for (int i2 = 0; i2 < this.x.length; ++i2) {
            this.x[i2] = 0;
        }
    }

    public final boolean e(short s2) {
        if (s2 < 0) {
            this.q = (short)(this.q & ~(-s2));
        } else if (s2 == 0) {
            this.q = 0;
        } else {
            short s3 = (short)(s2 & ~this.H);
            if (s3 == 0) {
                return false;
            }
            this.q = s2;
        }
        return true;
    }

    public final void i(int n2) {
        this.I = n2;
    }

    public final int C() {
        return this.I;
    }

    public final short D() {
        return this.q;
    }

    public final f E() {
        f f2 = new f();
        new f().f = this.f;
        f2.z = this.z;
        f2.O = this.O;
        f2.A = this.A;
        f2.L = this.L;
        f2.M = this.M;
        f2.b = this.b;
        f2.F = this.F;
        f2.Z = this.Z;
        f2.N = this.N;
        f2.B = this.B;
        System.arraycopy(this.s, 0, f2.s, 0, this.s.length);
        System.arraycopy(this.J, 0, f2.J, 0, this.J.length);
        if (this.aa != null) {
            f2.aa = new f.c[this.aa.length];
            System.arraycopy(this.aa, 0, f2.aa, 0, this.aa.length);
        }
        if (this.ab != null) {
            f2.ab = new f.c[this.ab.length];
            System.arraycopy(this.ab, 0, f2.ab, 0, this.ab.length);
        }
        return f2;
    }

    public final int j(int n2) {
        for (int i2 = 0; i2 < this.J.length; ++i2) {
            if (this.J[i2] != n2) continue;
            return i2;
        }
        return -1;
    }

    public final boolean F() {
        return (this.q & 4) != 0;
    }

    public final boolean G() {
        return (this.q & 0x80) != 0 || (this.q & 0x100) != 0 || (this.q & 0x10) != 0 || (this.q & 0x40) != 0;
    }

    public final boolean H() {
        return (this.q & 0x800) != 0 || (this.q & 0x200) != 0;
    }

    public final f a(f[] fArray) {
        int n2;
        int n3 = 0;
        for (int i2 = 0; i2 < fArray.length; ++i2) {
            if (fArray[i2] == null || fArray[i2].w() || fArray[i2] == this) continue;
            ++n3;
        }
        if (n3 == 0) {
            return null;
        }
        f[] fArray2 = new f[n3];
        int n4 = 0;
        for (n2 = 0; n2 < fArray.length; ++n2) {
            if (fArray[n2] == null || fArray[n2].w() || fArray[n2] == this) continue;
            fArray2[n4] = fArray[n2];
            ++n4;
        }
        n2 = e.a.b.c(n3);
        return fArray2[n2];
    }

    public final void k(int n2) {
        if (n2 < 0 || n2 >= this.x.length) {
            return;
        }
        this.x[n2] = 0;
    }

    public final void a(int n2, int n3, int n4) {
        if (n2 < 0 || n2 >= this.x.length) {
            return;
        }
        this.x[n2] = n3;
        this.y[n2] = n4;
    }

    public final int a(f f2, boolean bl) {
        if (f2 == null) {
            return 0;
        }
        int n2 = ((f)this).l();
        int n3 = f2.m();
        n2 -= n3;
        if ((this = ((f)this).g(0)) != null) {
            n2 -= b.f.a(((f.a)this).h, ((f.a)this).i, f2);
        }
        n2 = n2 * (100 + e.a.b.d(10)) / 100;
        if (f2.G) {
            n2 = n2 * (50 + e.a.b.c(50)) / 100;
        }
        int n4 = n2 = n2 <= 0 ? 1 : n2;
        if (bl) {
            n2 <<= 1;
        }
        return n2;
    }

    public final int I() {
        return this.c(2) * 100 / this.c(3);
    }

    public final f.c[] J() {
        return this.ac;
    }

    public final void K() {
        this.a((byte)2, 9999);
        this.a((byte)4, 9999);
        this.a((byte)6, 9999);
        this.a((byte)14, 100);
        this.q = 0;
        this.I = 0;
    }

    public final void L() {
        f.a a2 = this.g(7);
        if (a2 != null) {
            a2.a(this);
        }
        if ((a2 = this.g(8)) != null) {
            a2.a(this);
        }
    }

    public final void l(int n2) {
        f.a a2 = this.g(7);
        if (a2 != null && a2.s == 11) {
            b.a.e(a2.r, n2);
        }
        if ((a2 = this.g(8)) != null && a2.s == 11) {
            b.a.e(a2.r, n2);
        }
    }

    public static String m(int n2) {
        switch (n2) {
            case 1: {
                return "\u91d1";
            }
            case 2: {
                return "\u6728";
            }
            case 4: {
                return "\u6c34";
            }
            case 8: {
                return "\u706b";
            }
            case 16: {
                return "\u571f";
            }
        }
        return "";
    }
}

