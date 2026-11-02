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
import c.c;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public class f
extends b {
    public static short[][] z = null;
    public static final short[][] A = new short[][]{{75, 100, 35, 20, 8, 6, 10, 24, 93, 1}, {552, 344, 203, 42, 11, 10, 19, 27, 109, 4}, {1182, 1205, 305, 339, 44, 35, 43, 31, 87, 13}, {1014, 836, 173, 342, 30, 35, 14, 27, 65, 8}};
    protected int[] B = new int[15];
    public static final byte[] C = new byte[]{1, 2, 4, 8, 16};
    public static final byte[] D = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    public static String[] E = new String[]{b.a.o[19], b.a.o[20], b.a.o[21], b.a.o[22], b.a.o[23], b.a.o[24], b.a.o[24], b.a.o[25], b.a.o[25], b.a.o[26], b.a.o[26]};
    public static final byte[] F = new byte[]{4, 6, 7, 8, 9, 10, 10, 11, 11, 16, 16};
    private byte w = (byte)4;
    private short x = 0;
    private int y = 0;
    public int[] G = null;
    public int[] H = null;
    private int[] O = null;
    private byte P = 0;
    private boolean Q = false;
    protected int I = 1;
    protected int J = 0;
    private int R = 100;
    private int S = 0;
    public String K = "";
    private short T = 0;
    private short U = 0;
    private short V = 0;
    private short W = 0;
    private short X = 0;
    private int Y = 0;
    private int Z = 0;
    private int aa = 0;
    private int ab = 0;
    private int ac = 0;
    public byte L = 0;
    private byte ad = 0;
    private Vector ae = new Vector();
    private Vector af = new Vector();
    private int ag = 0;
    private int ah = 150;
    public boolean M = false;
    public short N = 0;

    public f() {
        super((byte)6);
        this.p = 20;
        this.q = 50;
        this.O = new int[11];
        for (int i2 = 0; i2 < this.O.length; ++i2) {
            this.O[i2] = -1;
        }
        this.G = new int[3];
        this.H = new int[3];
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
        if (b.a.q != null) {
            return;
        }
        if (n2 == 53 && f4.j != 0) {
            a.d.b b2;
            void var4_14;
            f f2 = f4;
            int n4 = f2.f();
            int n5 = f2.g();
            byte by = f2.m;
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
            e e2 = b.a.w.c(n4, (int)var4_14);
            if (e2 == null) {
                b2 = null;
            } else if (e2.o() != 1 || !e2.c) {
                b2 = null;
            } else {
                a.d.b b3;
                if (!e2.v && e2.g >= 0 && e2.g <= 7) {
                    byte by2 = b.b.b(by);
                    e2.a(by2, false);
                }
                b2 = b3 = b.a.w.c(e2.i);
            }
            b.a.q = b2;
            return;
        }
        int n6 = n3;
        f f3 = f4;
        if (f3.j == 0) {
            bl2 = false;
        } else {
            byte by = b.f.o(n6);
            if (by == 4) {
                bl2 = false;
            } else if (f3.m == by && f3.p(f3.m)) {
                f3.j = 0;
                f3.P = 0;
                f3.a(f3.m, true);
                bl2 = true;
            } else {
                f3.b(by);
                f3.a(by, false);
                bl2 = false;
            }
        }
        n6 = n3;
        f3 = f4;
        if (f3.j != 0) return;
        if (f3.P < 16) {
            f3.a(f3.m);
            f3.P = (byte)(f3.P + 4);
            if (b.a.i) {
                f f4 = null;
                f f5 = null;
                boolean bl3 = false;
                int n7 = 0;
                for (int i2 = 1; i2 < b.a.A.length; ++i2) {
                    f4 = b.a.A[i2 - 1];
                    f5 = b.a.A[i2];
                    int n8 = Math.abs(f4.n - f5.n);
                    if (n8 + (n7 = Math.abs(f4.o - f5.o)) <= 16) continue;
                    if (f5.m != f4.w) {
                        f5.b(f4.w);
                    }
                    f5.a(f5.m, true);
                    f5.a(f5.m);
                }
            }
        }
        if (f3.P < 16) return;
        f3.P = 0;
        boolean bl4 = false;
        while (var4_21 < b.a.A.length) {
            b.a.A[var4_21].w = b.a.A[var4_21].m;
            ++var4_21;
        }
        if (n6 == 0 || b.f.o(n6) != f3.m || !f3.p(f3.m)) {
            void var4_23;
            boolean bl5 = false;
            while (var4_23 < b.a.A.length) {
                b.a.A[var4_23].a(b.a.A[var4_23].m, false);
                ++var4_23;
            }
            f3.j = 1;
        }
        if ((b.a.q = b.a.w.a((f4 = f3).f(), f4.g(), (byte)2)) != null) {
            void var4_25;
            boolean bl6 = false;
            while (var4_25 < b.a.A.length) {
                b.a.A[var4_25].a(b.a.A[var4_25].m, false);
                ++var4_25;
            }
            f3.j = 1;
            return;
        }
        int n9 = b.a.A[0].n;
        n3 = b.a.A[0].o;
        if (b.a.w == null) return;
        if (b.a.q != null) return;
        e e3 = b.a.w.a(n9 - 16, n3 - 16, 48, 48, 3);
        if (e3 == null) return;
        switch (e3.w) {
            case 2: {
                if (e3.A) return;
                if (b.a.a) return;
                main.a.a.k.a(c.c.a(e3));
                bl = true;
                break;
            }
            case 1: {
                b.a.q = b.a.w.a(e3);
                if (b.a.q == null) return;
                bl = true;
                break;
            }
            default: {
                return;
            }
        }
        if (!bl) return;
        boolean bl7 = false;
        while (var4_28 < b.a.A.length) {
            b.a.A[var4_28].a(b.a.A[var4_28].m, false);
            ++var4_28;
        }
        f3.j = 1;
    }

    private static byte o(int n2) {
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

    private boolean p(int n2) {
        switch (n2) {
            case 2: {
                return b.a.w.b(this.n, this.o + 16);
            }
            case 3: {
                return b.a.w.b(this.n, this.o - 16);
            }
            case 1: {
                return b.a.w.b(this.n - 16, this.o);
            }
            case 0: {
                return b.a.w.b(this.n + 16, this.o);
            }
        }
        return false;
    }

    public final int c(int n2) {
        if (n2 < 0 || n2 >= this.B.length) {
            return 0;
        }
        switch (n2) {
            case 1: {
                return this.J;
            }
            case 0: {
                return this.I;
            }
        }
        return this.B[n2];
    }

    public final void a(byte by, int n2) {
        switch (by) {
            case 1: {
                this.q(n2);
                return;
            }
            case 0: {
                this.s(n2);
                return;
            }
            case 14: {
                this.t(n2);
                return;
            }
        }
        this.d(by, n2);
    }

    public final void b(byte by, int n2) {
        if (n2 == 0) {
            return;
        }
        switch (by) {
            case 1: {
                this.q(this.J + n2);
                return;
            }
            case 0: {
                this.s(this.I + n2);
                return;
            }
            case 14: {
                this.t(this.R + n2);
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
        n2 = this.S + n2;
        int n3 = this.S;
        this.S = a.c.b.c(n2, 710, 0);
        Vector<f.c> vector = new Vector<f.c>();
        f.c c2 = null;
        for (int i2 = 0; i2 < b.a.s.length; ++i2) {
            if (b.a.s[i2].a[23] != this.i || b.a.s[i2].a[25] <= n3 || b.a.s[i2].a[25] > this.S || (c2 = b.a.d((int)b.a.s[i2].q)) == null) continue;
            this.a(c2.q);
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
        if (by < 0 || by >= this.B.length || by == 1 || by == 0) {
            return;
        }
        switch (by) {
            case 2: {
                this.B[by] = a.c.b.b(0, n2, this.B(), 0);
                return;
            }
            case 4: {
                this.B[by] = a.c.b.b(0, n2, this.C(), 0);
                return;
            }
            case 6: {
                this.B[by] = a.c.b.b(0, n2, this.D(), 0);
                return;
            }
        }
        this.B[by] = a.c.b.b(0, n2, 99999, 0);
    }

    public final int o() {
        return A[this.i][0] + (this.I - A[this.i][9]) * A[this.i][8];
    }

    private boolean q(int n2) {
        if (n2 > Integer.MAX_VALUE) {
            n2 = Integer.MAX_VALUE;
        } else if (n2 < 0) {
            n2 = 0;
        }
        if (this.I >= 80) {
            return false;
        }
        int n3 = this.o();
        if (n2 > n3) {
            while (n2 > n3) {
                n2 -= n3;
                this.b((byte)0, 1);
                n3 = this.o();
                this.r(this.I);
                this.b(this.e(this.I));
            }
            this.J = n2;
            return true;
        }
        this.J = n2;
        return false;
    }

    public final short e(int n2) {
        if ((n2 -= A[this.i][9]) < 0 || n2 >= z[this.i].length) {
            return -1;
        }
        return z[this.i][n2];
    }

    private void r(int n2) {
        if (n2 > 30) {
            int n3 = n2;
            f f2 = this;
            switch (f2.i) {
                case 0: {
                    f2.B[3] = f2.B[3] + 59;
                    f2.B[5] = f2.B[5] + 20;
                    f2.B[7] = f2.B[7] + 12;
                    f2.B[8] = f2.B[8] + 2;
                    f2.B[9] = f2.B[9] + 2;
                    f2.B[10] = f2.B[10] + 4;
                    f2.B[11] = f2.B[11] + (n3 % 4 == 0 ? 1 : 0);
                    break;
                }
                case 1: {
                    f2.B[3] = f2.B[3] + 49;
                    f2.B[5] = f2.B[5] + 31;
                    f2.B[7] = f2.B[7] + 6;
                    f2.B[8] = f2.B[8] + (n3 % 2 == 0 ? 1 : 2);
                    f2.B[9] = f2.B[9] + (n3 % 2 == 0 ? 1 : 2);
                    f2.B[10] = f2.B[10] + 2;
                    f2.B[11] = f2.B[11] + (n3 % 2 == 0 ? 1 : 0);
                    break;
                }
                case 2: {
                    f2.B[3] = f2.B[3] + 56;
                    f2.B[5] = f2.B[5] + 15;
                    f2.B[7] = f2.B[7] + 14;
                    f2.B[8] = f2.B[8] + 2;
                    f2.B[9] = f2.B[9] + (n3 % 2 == 0 ? 1 : 2);
                    f2.B[10] = f2.B[10] + 2;
                    f2.B[11] = f2.B[11] + (n3 % 3 == 0 ? 1 : 0);
                    break;
                }
                case 3: {
                    f2.B[3] = f2.B[3] + 63;
                    f2.B[5] = f2.B[5] + 13;
                    f2.B[7] = f2.B[7] + 26;
                    f2.B[8] = f2.B[8] + 2;
                    f2.B[9] = f2.B[9] + 2;
                    f2.B[10] = f2.B[10] + 1;
                    f2.B[11] = f2.B[11] + (n3 % 4 == 0 ? 1 : 0);
                    break;
                }
            }
        } else {
            int n4 = n2;
            f f3 = this;
            switch (f3.i) {
                case 0: {
                    f3.B[3] = f3.B[3] + 98;
                    f3.B[5] = f3.B[5] + 33;
                    f3.B[7] = f3.B[7] + 20;
                    f3.B[8] = f3.B[8] + 2;
                    f3.B[9] = f3.B[9] + 3;
                    f3.B[10] = f3.B[10] + 3;
                    f3.B[11] = f3.B[11] + (n4 % 2 == 0 ? 1 : 0);
                    break;
                }
                case 1: {
                    f3.B[3] = f3.B[3] + 75;
                    f3.B[5] = f3.B[5] + 51;
                    f3.B[7] = f3.B[7] + 11;
                    f3.B[8] = f3.B[8] + 2;
                    f3.B[9] = f3.B[9] + 2;
                    f3.B[10] = f3.B[10] + 3;
                    f3.B[11] = f3.B[11] + 1;
                    break;
                }
                case 2: {
                    f3.B[3] = f3.B[3] + 93;
                    f3.B[5] = f3.B[5] + 24;
                    f3.B[7] = f3.B[7] + 26;
                    f3.B[8] = f3.B[8] + 3;
                    f3.B[9] = f3.B[9] + (n4 % 2 == 0 ? 3 : 2);
                    f3.B[10] = f3.B[10] + 3;
                    f3.B[11] = f3.B[11] + (n4 % 2 == 0 ? 1 : 0);
                    break;
                }
                case 3: {
                    f3.B[3] = f3.B[3] + 105;
                    f3.B[5] = f3.B[5] + 22;
                    f3.B[7] = f3.B[7] + 43;
                    f3.B[8] = f3.B[8] + 3;
                    f3.B[9] = f3.B[9] + 4;
                    f3.B[10] = f3.B[10] + 3;
                    f3.B[11] = f3.B[11] + (n4 % 2 == 0 ? 1 : 0);
                    break;
                }
            }
        }
        this.B[2] = this.B();
        this.B[4] = this.C();
        this.B[6] = this.D();
    }

    private void s(int n2) {
        if (n2 < this.I + 1) {
            return;
        }
        if (n2 <= 0) {
            n2 = 1;
        } else if (n2 > 80) {
            n2 = 80;
        }
        for (int i2 = this.I + 1; i2 < n2; ++i2) {
            this.r(i2);
            this.b(this.e(i2));
        }
        this.J = 0;
        this.I = n2;
        this.a((byte)2, this.c(3));
        this.a((byte)4, this.c(5));
        this.a((byte)6, this.c(7));
    }

    public final void a(byte by, byte by2, short s) {
        switch (by) {
            case 0: {
                this.b(by2, (int)s);
                return;
            }
            case 1: {
                this.a(by2, (int)s);
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3, short s) {
        if (((b)((Object)b2)).e == null) {
            return;
        }
        a.e.b b2 = ((b)((Object)b2)).e.b(20);
        b2.a(graphics, n2, n3);
    }

    public final boolean p() {
        return this.Q;
    }

    public final int q() {
        return this.R;
    }

    private void t(int n2) {
        this.R = a.c.b.c(n2, 100, 0);
    }

    public final void a(boolean n2) {
        if (this.Q == n2) {
            return;
        }
        this.Q = n2;
        if (n2 != 0) {
            n2 = 0;
            for (int i2 = 0; i2 < b.a.k[this.i].length; ++i2) {
                n2 = b.a.a(b.a.k[this.i][i2], 1);
                if (n2 < 0 || n2 >= 256) continue;
                this.c(n2, i2);
            }
            return;
        }
        f.b b2 = null;
        for (int i3 = 0; i3 < b.a.k[this.i].length; ++i3) {
            b2 = b.a.f(this.O[i3]);
            if (b2 == null) {
                b.a.k[this.i][i3] = -1;
            } else {
                b.a.k[this.i][i3] = b2.q;
                b.a.d(this.O[i3], 1);
            }
            this.f(i3);
        }
    }

    public final int r() {
        int n2 = this.n();
        return (this.ag - n2) * 100 / (this.ah - n2);
    }

    public final void s() {
        this.ag = this.n();
    }

    public final void t() {
        this.ag += a.c.b.c(50);
    }

    public final void u() {
        this.ag += 4;
        if (this.ag > this.ah) {
            this.ag = this.ah;
        }
    }

    public final boolean v() {
        return this.ag >= this.ah;
    }

    public final boolean w() {
        return this.B[2] <= 0;
    }

    public final int x() {
        int n2 = 0;
        f.a a2 = this.g(1);
        n2 = 0 + (a2 == null ? (byte)0 : a2.d);
        a2 = this.g(3);
        n2 += a2 == null ? (byte)0 : a2.d;
        a2 = this.g(2);
        n2 += a2 == null ? (byte)0 : a2.d;
        a2 = this.g(2);
        n2 += a2 == null ? (byte)0 : a2.d;
        a2 = this.g(4);
        return n2 += a2 == null ? (byte)0 : a2.d;
    }

    public final int y() {
        int n2 = 0;
        f.a a2 = this.g(1);
        n2 = 0 + (a2 == null ? (byte)0 : a2.e);
        a2 = this.g(3);
        n2 += a2 == null ? (byte)0 : a2.e;
        a2 = this.g(2);
        n2 += a2 == null ? (byte)0 : a2.e;
        a2 = this.g(2);
        n2 += a2 == null ? (byte)0 : a2.e;
        a2 = this.g(4);
        return n2 += a2 == null ? (byte)0 : a2.e;
    }

    public final int z() {
        int n2 = 0;
        f.a a2 = this.g(1);
        n2 = 0 + (a2 == null ? (byte)0 : a2.f);
        a2 = this.g(3);
        n2 += a2 == null ? (byte)0 : a2.f;
        a2 = this.g(2);
        n2 += a2 == null ? (byte)0 : a2.f;
        a2 = this.g(2);
        n2 += a2 == null ? (byte)0 : a2.f;
        a2 = this.g(4);
        return n2 += a2 == null ? (byte)0 : a2.f;
    }

    public final byte[] A() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            int n2;
            dataOutputStream.writeShort(this.i);
            dataOutputStream.writeUTF(this.k);
            dataOutputStream.writeUTF(this.K);
            dataOutputStream.writeShort(this.I);
            dataOutputStream.writeBoolean(this.Q);
            dataOutputStream.writeShort(this.B[2]);
            dataOutputStream.writeShort(this.B[4]);
            dataOutputStream.writeShort(this.B[6]);
            dataOutputStream.writeShort(this.R);
            dataOutputStream.writeUTF(this.d);
            dataOutputStream.writeByte(this.L);
            dataOutputStream.writeByte(this.ad);
            dataOutputStream.writeShort(this.S);
            dataOutputStream.writeInt(this.J);
            dataOutputStream.writeShort(this.B[3]);
            dataOutputStream.writeShort(this.B[5]);
            dataOutputStream.writeShort(this.B[7]);
            dataOutputStream.writeShort(this.B[8]);
            dataOutputStream.writeShort(this.B[10]);
            dataOutputStream.writeShort(this.B[9]);
            dataOutputStream.writeShort(this.B[11]);
            dataOutputStream.writeShort(this.B[12]);
            for (int i2 = 0; i2 < this.O.length; ++i2) {
                dataOutputStream.writeByte(this.O[i2]);
            }
            if (this.ae.size() == 0) {
                dataOutputStream.writeByte(0);
            } else {
                dataOutputStream.writeByte(this.ae.size());
                f.c c2 = null;
                for (n2 = 0; n2 < this.ae.size(); ++n2) {
                    c2 = (f.c)this.ae.elementAt(n2);
                    dataOutputStream.writeShort(c2.q);
                }
            }
            if (this.af.size() == 0) {
                dataOutputStream.writeByte(0);
            } else {
                dataOutputStream.writeByte(this.af.size());
                f.c c3 = null;
                for (n2 = 0; n2 < this.af.size(); ++n2) {
                    c3 = (f.c)this.af.elementAt(n2);
                    dataOutputStream.writeShort(c3.q);
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

    public final void b(DataInputStream dataInputStream) {
        try {
            int n2;
            this.i = dataInputStream.readShort();
            this.k = dataInputStream.readUTF();
            this.K = dataInputStream.readUTF();
            this.I = dataInputStream.readShort();
            this.Q = dataInputStream.readBoolean();
            this.B[2] = dataInputStream.readShort();
            this.B[4] = dataInputStream.readShort();
            this.B[6] = dataInputStream.readShort();
            this.R = dataInputStream.readShort();
            this.d = dataInputStream.readUTF();
            this.L = dataInputStream.readByte();
            this.ad = dataInputStream.readByte();
            this.S = dataInputStream.readShort();
            this.J = dataInputStream.readInt();
            this.B[3] = dataInputStream.readShort();
            this.B[5] = dataInputStream.readShort();
            this.B[7] = dataInputStream.readShort();
            this.B[8] = dataInputStream.readShort();
            this.B[10] = dataInputStream.readShort();
            this.B[9] = dataInputStream.readShort();
            this.B[11] = dataInputStream.readShort();
            this.B[12] = dataInputStream.readShort();
            for (int i2 = 0; i2 < this.O.length; ++i2) {
                this.O[i2] = a.c.b.a((int)dataInputStream.readByte());
            }
            Vector vector = new Vector();
            f.c c2 = null;
            int n3 = dataInputStream.readByte();
            if (n3 > 0) {
                for (n2 = 0; n2 < n3; ++n2) {
                    c2 = b.a.d((int)dataInputStream.readShort());
                    if (c2 == null) continue;
                    this.ae.addElement(c2);
                }
            }
            vector.removeAllElements();
            n2 = dataInputStream.readByte();
            if (n2 > 0) {
                for (int i3 = 0; i3 < n2; ++i3) {
                    c2 = b.a.c((int)dataInputStream.readShort());
                    if (c2 == null) continue;
                    this.af.addElement(c2);
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
        this.i = dataInputStream.readShort();
        this.k = dataInputStream.readUTF();
        this.K = dataInputStream.readUTF();
        this.d = dataInputStream.readUTF();
        this.a(dataInputStream.readBoolean());
        short s = dataInputStream.readShort();
        this.I = A[this.i][9];
        this.b(this.e(this.I));
        this.R();
        this.a((byte)0, (int)s);
        this.L = dataInputStream.readByte();
        this.ad = dataInputStream.readByte();
        dataInputStream.readByte();
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
        n5 = f2.h(n5) > 0 ? -n3 * 3 / 2 * (100 - f2.u(n2)) / 100 : (f2.h(n6) > 0 ? n3 * (f2.u(n6) + f2.h(n6)) / 100 : -n3 * (100 - f2.u(n2)) / 100);
        n5 += n5 * a.c.b.d(10) / 100;
        return n5;
    }

    public int l() {
        int n2 = this.c(8);
        f.a a2 = null;
        for (int i2 = 0; i2 < this.O.length; ++i2) {
            a2 = this.g(i2);
            n2 += a2 == null ? (short)0 : a2.a;
        }
        return n2 += this.G[0];
    }

    public int m() {
        int n2 = this.c(9);
        f.a a2 = null;
        for (int i2 = 0; i2 < this.O.length; ++i2) {
            a2 = this.g(i2);
            n2 += a2 == null ? (short)0 : a2.b;
        }
        return n2 += this.G[1];
    }

    public int n() {
        int n2 = this.c(11);
        f.a a2 = null;
        for (int i2 = 0; i2 < this.O.length; ++i2) {
            a2 = this.g(i2);
            n2 += a2 == null ? (short)0 : a2.c;
        }
        return n2 += this.G[2];
    }

    public final int B() {
        int n2 = this.B[3];
        f.a a2 = null;
        for (int i2 = 0; i2 < this.O.length; ++i2) {
            a2 = this.g(i2);
            n2 += a2 == null ? (short)0 : a2.j;
        }
        return n2;
    }

    public final int C() {
        int n2 = this.B[5];
        f.a a2 = null;
        for (int i2 = 0; i2 < this.O.length; ++i2) {
            a2 = this.g(i2);
            n2 += a2 == null ? (short)0 : a2.k;
        }
        return n2;
    }

    public final int D() {
        int n2 = this.B[7];
        f.a a2 = null;
        for (int i2 = 0; i2 < this.O.length; ++i2) {
            a2 = this.g(i2);
            n2 += a2 == null ? (short)0 : a2.l;
        }
        return n2;
    }

    public final f.a[] E() {
        f.a[] aArray = new f.a[this.O.length];
        f.b b2 = null;
        for (int i2 = 0; i2 < aArray.length; ++i2) {
            b2 = b.a.f(this.O[i2]);
            if (b2 == null || !b2.e()) continue;
            aArray[i2] = (f.a)b2;
        }
        return aArray;
    }

    public final void c(int n2, int n3) {
        if (n3 < 0 || n3 >= this.O.length) {
            return;
        }
        this.O[n3] = n2;
    }

    public final void f(int n2) {
        if (n2 < 0 || n2 >= this.O.length) {
            return;
        }
        this.O[n2] = -1;
    }

    public final f.a g(int n2) {
        if (n2 < 0 || n2 >= ((f)((Object)b2)).O.length) {
            return null;
        }
        f.b b2 = b.a.f(((f)((Object)b2)).O[n2]);
        if (b2 == null) {
            return null;
        }
        return (f.a)b2;
    }

    public final short h(int n2) {
        int n3 = 0;
        int n4 = 0;
        switch (n2) {
            case 16: {
                n3 = this.X;
                n4 = 4;
                break;
            }
            case 1: {
                n3 = this.T;
                n4 = 0;
                break;
            }
            case 8: {
                n3 = this.W;
                n4 = 3;
                break;
            }
            case 4: {
                n3 = this.V;
                n4 = 2;
                break;
            }
            case 2: {
                n3 = this.U;
                n4 = 1;
                break;
            }
            default: {
                return 0;
            }
        }
        f.a[] aArray = this.E();
        for (int i2 = 0; i2 < aArray.length; ++i2) {
            if (aArray[i2] == null) continue;
            n3 += aArray[i2].m[n4];
        }
        if (n2 == this.L) {
            n3 += this.ad;
        }
        if (n3 < 0) {
            n3 = 0;
        }
        return (short)n3;
    }

    private int u(int n2) {
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
            this.X = (short)a.c.b.b(0, n2, 100, -100);
        }
        if ((by & 1) != 0) {
            this.T = (short)a.c.b.b(0, n2, 100, -100);
        }
        if ((by & 8) != 0) {
            this.W = (short)a.c.b.b(0, n2, 100, -100);
        }
        if ((by & 4) != 0) {
            this.V = (short)a.c.b.b(0, n2, 100, -100);
        }
        if ((by & 2) != 0) {
            this.U = (short)a.c.b.b(0, n2, 100, -100);
        }
    }

    public final void a(short s) {
        f.c c2 = b.a.d((int)s);
        if (c2 == null) {
            return;
        }
        this.ae.addElement(c2);
    }

    public final void b(short s) {
        f.c c2;
        block9: {
            block8: {
                boolean bl;
                block7: {
                    c2 = b.a.c((int)s);
                    if (c2 == null) break block8;
                    short s2 = s;
                    Vector vector = this.af;
                    if (vector == null || vector.size() == 0) {
                        bl = false;
                    } else {
                        f.b b2 = null;
                        for (int i2 = 0; i2 < vector.size(); ++i2) {
                            b2 = (f.b)vector.elementAt(i2);
                            if (b2 == null || b2.q != s2) continue;
                            bl = true;
                            break block7;
                        }
                        bl = false;
                    }
                }
                if (!bl) break block9;
            }
            return;
        }
        this.af.addElement(c2);
    }

    public final void F() {
        for (int i2 = 0; i2 < this.G.length; ++i2) {
            this.G[i2] = 0;
        }
    }

    public final boolean c(short s) {
        if (s < 0) {
            this.x = (short)(this.x & ~(-s));
        } else if (s == 0) {
            this.x = 0;
        } else {
            short s2 = (short)(s & ~this.N);
            if (s2 == 0) {
                return false;
            }
            this.x = s;
        }
        return true;
    }

    public final void i(int n2) {
        this.y = n2;
    }

    public final int G() {
        return this.y;
    }

    public final short H() {
        return this.x;
    }

    public final f I() {
        f f2 = new f();
        new f().i = this.i;
        f2.k = this.k;
        f2.K = this.K;
        f2.I = this.I;
        f2.Q = this.Q;
        f2.R = this.R;
        f2.d = this.d;
        f2.L = this.L;
        f2.ad = this.ad;
        f2.S = this.S;
        f2.J = this.J;
        System.arraycopy(this.B, 0, f2.B, 0, this.B.length);
        System.arraycopy(this.O, 0, f2.O, 0, this.O.length);
        f.c c2 = null;
        if (this.ae.size() > 0) {
            for (int i2 = 0; i2 < this.ae.size(); ++i2) {
                c2 = (f.c)this.ae.elementAt(i2);
                f2.ae.addElement(c2);
            }
        }
        f.c c3 = null;
        if (this.af.size() > 0) {
            for (int i3 = 0; i3 < this.af.size(); ++i3) {
                c3 = (f.c)this.af.elementAt(i3);
                f2.af.addElement(c3);
            }
        }
        return f2;
    }

    public final int j(int n2) {
        for (int i2 = 0; i2 < this.O.length; ++i2) {
            if (this.O[i2] != n2) continue;
            return i2;
        }
        return -1;
    }

    public final boolean J() {
        return (this.x & 4) != 0;
    }

    public final boolean K() {
        return (this.x & 0x80) != 0 || (this.x & 0x100) != 0 || (this.x & 0x10) != 0 || (this.x & 0x40) != 0;
    }

    public final boolean L() {
        return (this.x & 0x800) != 0 || (this.x & 0x200) != 0;
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
        n2 = a.c.b.c(n3);
        return fArray2[n2];
    }

    public final void k(int n2) {
        if (n2 < 0 || n2 >= this.G.length) {
            return;
        }
        this.G[n2] = 0;
    }

    public final void a(int n2, int n3, int n4) {
        if (n2 < 0 || n2 >= this.G.length) {
            return;
        }
        this.G[n2] = n3;
        this.H[n2] = n4;
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
        n2 = n2 * (100 + a.c.b.d(10)) / 100;
        if (f2.M) {
            n2 = n2 * (50 + a.c.b.c(50)) / 100;
        }
        int n4 = n2 = n2 <= 0 ? 1 : n2;
        if (bl) {
            n2 <<= 1;
        }
        if (f2.i > 5) {
            n2 = (c.c.j + 1) * n2;
        }
        return n2;
    }

    public final int M() {
        return this.c(2) * 100 / this.B();
    }

    public final int N() {
        return this.c(4) * 100 / this.C();
    }

    public final int O() {
        return this.c(6) * 100 / this.D();
    }

    public final Vector P() {
        Vector<f.c> vector = new Vector<f.c>();
        f.a a2 = this.g(7);
        f.c c2 = null;
        if (a2 != null && a2.r == 11 && (c2 = a2.b()) != null) {
            vector.addElement(c2);
        }
        if ((a2 = this.g(8)) != null && a2.r == 11 && (c2 = a2.b()) != null) {
            vector.addElement(c2);
        }
        return vector;
    }

    public final void Q() {
        this.a((byte)2, 9999);
        this.a((byte)4, 9999);
        this.a((byte)6, 9999);
        this.a((byte)14, 100);
        this.x = 0;
        this.y = 0;
    }

    public final void R() {
        this.B[3] = A[this.i][1];
        this.B[5] = A[this.i][2];
        this.B[7] = A[this.i][3];
        this.B[8] = A[this.i][4];
        this.B[9] = A[this.i][5];
        this.B[10] = A[this.i][6];
        this.B[11] = A[this.i][7];
    }

    public final void l(int n2) {
        f.a a2 = this.g(7);
        if (a2 != null && a2.r == 11) {
            b.a.e(a2.q, n2);
        }
        if ((a2 = this.g(8)) != null && a2.r == 11) {
            b.a.e(a2.q, n2);
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

    public final Vector S() {
        return this.af;
    }

    public final f.c n(int n2) {
        if (n2 < 0 || n2 >= this.af.size()) {
            return null;
        }
        return (f.c)this.af.elementAt(n2);
    }

    public final Vector T() {
        return this.ae;
    }

    public final int U() {
        switch (this.L) {
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 4: {
                return 2;
            }
            case 8: {
                return 3;
            }
            case 16: {
                return 4;
            }
        }
        return 0;
    }

    public final int V() {
        return this.B[2];
    }

    public final int W() {
        return this.B[4];
    }

    public final int X() {
        return this.B[6];
    }
}

