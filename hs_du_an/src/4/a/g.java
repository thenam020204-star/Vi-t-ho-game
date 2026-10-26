/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package a;

import a.a;
import a.b;
import a.f;
import b.a.d;
import f.c;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public class g
extends b {
    public static short[][] d = null;
    public static final short[][] e = new short[][]{{75, 100, 35, 20, 8, 6, 10, 24, 93, 1}, {552, 344, 203, 42, 11, 10, 19, 27, 109, 4}, {1182, 1205, 305, 339, 44, 35, 43, 31, 87, 13}, {1014, 836, 173, 342, 30, 35, 14, 27, 65, 8}};
    protected int[] B = new int[15];
    public static final byte[] C = new byte[]{1, 2, 4, 8, 16};
    public static final byte[] D = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    public static String[] E = new String[]{a.f.o[19], a.f.o[20], a.f.o[21], a.f.o[22], a.f.o[23], a.f.o[24], a.f.o[24], a.f.o[25], a.f.o[25], a.f.o[26], a.f.o[26]};
    public static final byte[] F = new byte[]{4, 6, 7, 8, 9, 10, 10, 11, 11, 16, 16};
    private byte a = (byte)4;
    private short b = 0;
    private int c = 0;
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

    public g() {
        super((byte)6);
        this.u = 20;
        this.v = 50;
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
        if (a.f.q != null) {
            return;
        }
        if (n2 == 53 && g4.o != 0) {
            b.f.b b2;
            void var4_14;
            g g2 = g4;
            int n4 = g2.n();
            int n5 = g2.o();
            byte by = g2.r;
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
            a a2 = a.f.w.c(n4, (int)var4_14);
            if (a2 == null) {
                b2 = null;
            } else if (a2.g() != 1 || !a2.h) {
                b2 = null;
            } else {
                b.f.b b3;
                if (!a2.A && a2.l >= 0 && a2.l <= 7) {
                    byte by2 = a.b.b(by);
                    a2.a(by2, false);
                }
                b2 = b3 = a.f.w.c(a2.n);
            }
            a.f.q = b2;
            return;
        }
        int n6 = n3;
        g g3 = g4;
        if (g3.o == 0) {
            bl2 = false;
        } else {
            byte by = a.g.o(n6);
            if (by == 4) {
                bl2 = false;
            } else if (g3.r == by && g3.p(g3.r)) {
                g3.o = 0;
                g3.P = 0;
                g3.a(g3.r, true);
                bl2 = true;
            } else {
                g3.d(by);
                g3.a(by, false);
                bl2 = false;
            }
        }
        n6 = n3;
        g3 = g4;
        if (g3.o != 0) return;
        if (g3.P < 16) {
            g3.c(g3.r);
            g3.P = (byte)(g3.P + 4);
            if (a.f.i) {
                g g4 = null;
                g g5 = null;
                boolean bl3 = false;
                int n7 = 0;
                for (int i2 = 1; i2 < a.f.A.length; ++i2) {
                    g4 = a.f.A[i2 - 1];
                    g5 = a.f.A[i2];
                    int n8 = Math.abs(g4.s - g5.s);
                    if (n8 + (n7 = Math.abs(g4.t - g5.t)) <= 16) continue;
                    if (g5.r != g4.a) {
                        g5.d(g4.a);
                    }
                    g5.a(g5.r, true);
                    g5.c(g5.r);
                }
            }
        }
        if (g3.P < 16) return;
        g3.P = 0;
        boolean bl4 = false;
        while (var4_21 < a.f.A.length) {
            a.f.A[var4_21].a = a.f.A[var4_21].r;
            ++var4_21;
        }
        if (n6 == 0 || a.g.o(n6) != g3.r || !g3.p(g3.r)) {
            void var4_23;
            boolean bl5 = false;
            while (var4_23 < a.f.A.length) {
                a.f.A[var4_23].a(a.f.A[var4_23].r, false);
                ++var4_23;
            }
            g3.o = 1;
        }
        if ((a.f.q = a.f.w.a((g4 = g3).n(), g4.o(), (byte)2)) != null) {
            void var4_25;
            boolean bl6 = false;
            while (var4_25 < a.f.A.length) {
                a.f.A[var4_25].a(a.f.A[var4_25].r, false);
                ++var4_25;
            }
            g3.o = 1;
            return;
        }
        int n9 = a.f.A[0].s;
        n3 = a.f.A[0].t;
        if (a.f.w == null) return;
        if (a.f.q != null) return;
        a a3 = a.f.w.a(n9 - 16, n3 - 16, 48, 48, 3);
        if (a3 == null) return;
        switch (a3.a) {
            case 2: {
                if (a3.e) return;
                if (a.f.a) return;
                main.a.a.k.a(d.d.a(a3));
                bl = true;
                break;
            }
            case 1: {
                a.f.q = a.f.w.a(a3);
                if (a.f.q == null) return;
                bl = true;
                break;
            }
            default: {
                return;
            }
        }
        if (!bl) return;
        boolean bl7 = false;
        while (var4_28 < a.f.A.length) {
            a.f.A[var4_28].a(a.f.A[var4_28].r, false);
            ++var4_28;
        }
        g3.o = 1;
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
                return a.f.w.b(this.s, this.t + 16);
            }
            case 3: {
                return a.f.w.b(this.s, this.t - 16);
            }
            case 1: {
                return a.f.w.b(this.s - 16, this.t);
            }
            case 0: {
                return a.f.w.b(this.s + 16, this.t);
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

    public final c[] d(int n2) {
        n2 = this.S + n2;
        int n3 = this.S;
        this.S = b.e.a.c(n2, 710, 0);
        Vector<c> vector = new Vector<c>();
        c c2 = null;
        for (int i2 = 0; i2 < a.f.s.length; ++i2) {
            if (a.f.s[i2].i[23] != this.n || a.f.s[i2].i[25] <= n3 || a.f.s[i2].i[25] > this.S || (c2 = a.f.d((int)a.f.s[i2].c)) == null) continue;
            this.a(c2.c);
            vector.addElement(c2);
        }
        if (vector.size() == 0) {
            return null;
        }
        Object[] objectArray = new c[vector.size()];
        vector.copyInto(objectArray);
        return objectArray;
    }

    private void d(byte by, int n2) {
        if (by < 0 || by >= this.B.length || by == 1 || by == 0) {
            return;
        }
        switch (by) {
            case 2: {
                this.B[by] = b.e.a.b(0, n2, this.B[3], 0);
                return;
            }
            case 4: {
                this.B[by] = b.e.a.b(0, n2, this.B[5], 0);
                return;
            }
            case 6: {
                this.B[by] = b.e.a.b(0, n2, this.B[7], 0);
                return;
            }
        }
        this.B[by] = b.e.a.b(0, n2, 99999, 0);
    }

    public final int g() {
        return e[this.n][0] + (this.I - e[this.n][9]) * e[this.n][8];
    }

    private boolean q(int n2) {
        if (n2 > Integer.MAX_VALUE) {
            n2 = Integer.MAX_VALUE;
        } else if (n2 < 0) {
            n2 = 0;
        }
        if (this.I >= 65) {
            return false;
        }
        int n3 = this.g();
        if (n2 > n3) {
            while (n2 > n3) {
                n2 -= n3;
                this.b((byte)0, 1);
                n3 = this.g();
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
        if ((n2 -= e[this.n][9]) < 0 || n2 >= d[this.n].length) {
            return -1;
        }
        return d[this.n][n2];
    }

    private void r(int n2) {
        if (n2 > 30) {
            int n3 = n2;
            g g2 = this;
            switch (g2.n) {
                case 0: {
                    g2.B[3] = g2.B[3] + 59;
                    g2.B[5] = g2.B[5] + 20;
                    g2.B[7] = g2.B[7] + 12;
                    g2.B[8] = g2.B[8] + 2;
                    g2.B[9] = g2.B[9] + 2;
                    g2.B[10] = g2.B[10] + 4;
                    g2.B[11] = g2.B[11] + (n3 % 4 == 0 ? 1 : 0);
                    break;
                }
                case 1: {
                    g2.B[3] = g2.B[3] + 49;
                    g2.B[5] = g2.B[5] + 31;
                    g2.B[7] = g2.B[7] + 6;
                    g2.B[8] = g2.B[8] + (n3 % 2 == 0 ? 1 : 2);
                    g2.B[9] = g2.B[9] + (n3 % 2 == 0 ? 1 : 2);
                    g2.B[10] = g2.B[10] + 2;
                    g2.B[11] = g2.B[11] + (n3 % 2 == 0 ? 1 : 0);
                    break;
                }
                case 2: {
                    g2.B[3] = g2.B[3] + 56;
                    g2.B[5] = g2.B[5] + 15;
                    g2.B[7] = g2.B[7] + 14;
                    g2.B[8] = g2.B[8] + 2;
                    g2.B[9] = g2.B[9] + (n3 % 2 == 0 ? 1 : 2);
                    g2.B[10] = g2.B[10] + 2;
                    g2.B[11] = g2.B[11] + (n3 % 3 == 0 ? 1 : 0);
                    break;
                }
                case 3: {
                    g2.B[3] = g2.B[3] + 63;
                    g2.B[5] = g2.B[5] + 13;
                    g2.B[7] = g2.B[7] + 26;
                    g2.B[8] = g2.B[8] + 2;
                    g2.B[9] = g2.B[9] + 2;
                    g2.B[10] = g2.B[10] + 1;
                    g2.B[11] = g2.B[11] + (n3 % 4 == 0 ? 1 : 0);
                    break;
                }
            }
        } else {
            int n4 = n2;
            g g3 = this;
            switch (g3.n) {
                case 0: {
                    g3.B[3] = g3.B[3] + 98;
                    g3.B[5] = g3.B[5] + 33;
                    g3.B[7] = g3.B[7] + 20;
                    g3.B[8] = g3.B[8] + 2;
                    g3.B[9] = g3.B[9] + 3;
                    g3.B[10] = g3.B[10] + 3;
                    g3.B[11] = g3.B[11] + (n4 % 2 == 0 ? 1 : 0);
                    break;
                }
                case 1: {
                    g3.B[3] = g3.B[3] + 75;
                    g3.B[5] = g3.B[5] + 51;
                    g3.B[7] = g3.B[7] + 11;
                    g3.B[8] = g3.B[8] + 2;
                    g3.B[9] = g3.B[9] + 2;
                    g3.B[10] = g3.B[10] + 3;
                    g3.B[11] = g3.B[11] + 1;
                    break;
                }
                case 2: {
                    g3.B[3] = g3.B[3] + 93;
                    g3.B[5] = g3.B[5] + 24;
                    g3.B[7] = g3.B[7] + 26;
                    g3.B[8] = g3.B[8] + 3;
                    g3.B[9] = g3.B[9] + (n4 % 2 == 0 ? 3 : 2);
                    g3.B[10] = g3.B[10] + 3;
                    g3.B[11] = g3.B[11] + (n4 % 2 == 0 ? 1 : 0);
                    break;
                }
                case 3: {
                    g3.B[3] = g3.B[3] + 105;
                    g3.B[5] = g3.B[5] + 22;
                    g3.B[7] = g3.B[7] + 43;
                    g3.B[8] = g3.B[8] + 3;
                    g3.B[9] = g3.B[9] + 4;
                    g3.B[10] = g3.B[10] + 3;
                    g3.B[11] = g3.B[11] + (n4 % 2 == 0 ? 1 : 0);
                    break;
                }
            }
        }
        this.B[2] = this.B[3];
        this.B[4] = this.B[5];
        this.B[6] = this.B[7];
    }

    private void s(int n2) {
        if (n2 < this.I + 1) {
            return;
        }
        if (n2 <= 0) {
            n2 = 1;
        } else if (n2 > 65) {
            n2 = 65;
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
        if (((b)((Object)d2)).j == null) {
            return;
        }
        d d2 = ((b)((Object)d2)).j.b(20);
        d2.a(graphics, n2, n3);
    }

    public final boolean h() {
        return this.Q;
    }

    public final int q() {
        return this.R;
    }

    private void t(int n2) {
        this.R = b.e.a.c(n2, 100, 0);
    }

    public final void a(boolean n2) {
        if (this.Q == n2) {
            return;
        }
        this.Q = n2;
        if (n2 != 0) {
            n2 = 0;
            for (int i2 = 0; i2 < a.f.k[this.n].length; ++i2) {
                n2 = a.f.a(a.f.k[this.n][i2], 1);
                if (n2 < 0 || n2 >= 256) continue;
                this.c(n2, i2);
            }
        } else {
            f.a a2 = null;
            for (int i3 = 0; i3 < a.f.k[this.n].length; ++i3) {
                a2 = a.f.f(this.O[i3]);
                if (a2 == null) {
                    a.f.k[this.n][i3] = -1;
                } else {
                    a.f.k[this.n][i3] = a2.c;
                    a.f.d(this.O[i3], 1);
                }
                this.f(i3);
            }
        }
    }

    public final int r() {
        int n2 = this.f();
        return (this.ag - n2) * 100 / (this.ah - n2);
    }

    public final void s() {
        this.ag = this.f();
    }

    public final void t() {
        this.ag += b.e.a.c(50);
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
        f.b b2 = this.g(1);
        n2 = 0 + (b2 == null ? (byte)0 : b2.l);
        b2 = this.g(3);
        n2 += b2 == null ? (byte)0 : b2.l;
        b2 = this.g(2);
        n2 += b2 == null ? (byte)0 : b2.l;
        b2 = this.g(2);
        n2 += b2 == null ? (byte)0 : b2.l;
        b2 = this.g(4);
        return n2 += b2 == null ? (byte)0 : b2.l;
    }

    public final int y() {
        int n2 = 0;
        f.b b2 = this.g(1);
        n2 = 0 + (b2 == null ? (byte)0 : b2.m);
        b2 = this.g(3);
        n2 += b2 == null ? (byte)0 : b2.m;
        b2 = this.g(2);
        n2 += b2 == null ? (byte)0 : b2.m;
        b2 = this.g(2);
        n2 += b2 == null ? (byte)0 : b2.m;
        b2 = this.g(4);
        return n2 += b2 == null ? (byte)0 : b2.m;
    }

    public final int z() {
        int n2 = 0;
        f.b b2 = this.g(1);
        n2 = 0 + (b2 == null ? (byte)0 : b2.n);
        b2 = this.g(3);
        n2 += b2 == null ? (byte)0 : b2.n;
        b2 = this.g(2);
        n2 += b2 == null ? (byte)0 : b2.n;
        b2 = this.g(2);
        n2 += b2 == null ? (byte)0 : b2.n;
        b2 = this.g(4);
        return n2 += b2 == null ? (byte)0 : b2.n;
    }

    public final byte[] A() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            int n2;
            dataOutputStream.writeShort(this.n);
            dataOutputStream.writeUTF(this.p);
            dataOutputStream.writeUTF(this.K);
            dataOutputStream.writeShort(this.I);
            dataOutputStream.writeBoolean(this.Q);
            dataOutputStream.writeShort(this.B[2]);
            dataOutputStream.writeShort(this.B[4]);
            dataOutputStream.writeShort(this.B[6]);
            dataOutputStream.writeShort(this.R);
            dataOutputStream.writeUTF(this.i);
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
                c c2 = null;
                for (n2 = 0; n2 < this.ae.size(); ++n2) {
                    c2 = (c)this.ae.elementAt(n2);
                    dataOutputStream.writeShort(c2.c);
                }
            }
            if (this.af.size() == 0) {
                dataOutputStream.writeByte(0);
            } else {
                dataOutputStream.writeByte(this.af.size());
                c c3 = null;
                for (n2 = 0; n2 < this.af.size(); ++n2) {
                    c3 = (c)this.af.elementAt(n2);
                    dataOutputStream.writeShort(c3.c);
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

    public final void b(DataInputStream dataInputStream) {
        try {
            int n2;
            this.n = dataInputStream.readShort();
            this.p = dataInputStream.readUTF();
            this.K = dataInputStream.readUTF();
            this.I = dataInputStream.readShort();
            this.Q = dataInputStream.readBoolean();
            this.B[2] = dataInputStream.readShort();
            this.B[4] = dataInputStream.readShort();
            this.B[6] = dataInputStream.readShort();
            this.R = dataInputStream.readShort();
            this.i = dataInputStream.readUTF();
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
                this.O[i2] = b.e.a.a((int)dataInputStream.readByte());
            }
            Vector vector = new Vector();
            c c2 = null;
            int n3 = dataInputStream.readByte();
            if (n3 > 0) {
                for (n2 = 0; n2 < n3; ++n2) {
                    c2 = a.f.d((int)dataInputStream.readShort());
                    if (c2 == null) continue;
                    this.ae.addElement(c2);
                }
            }
            vector.removeAllElements();
            n2 = dataInputStream.readByte();
            if (n2 > 0) {
                for (int i3 = 0; i3 < n2; ++i3) {
                    c2 = a.f.c((int)dataInputStream.readShort());
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
        this.n = dataInputStream.readShort();
        this.p = dataInputStream.readUTF();
        this.K = dataInputStream.readUTF();
        this.i = dataInputStream.readUTF();
        this.a(dataInputStream.readBoolean());
        short s = dataInputStream.readShort();
        this.I = e[this.n][9];
        this.b(this.e(this.I));
        this.R();
        this.a((byte)0, (int)s);
        this.L = dataInputStream.readByte();
        this.ad = dataInputStream.readByte();
        dataInputStream.readByte();
    }

    public static int a(int n2, int n3, g g2) {
        int n4;
        if (g2 == null || n3 == 0) {
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
        n5 = g2.h(n5) > 0 ? -n3 * 3 / 2 * (100 - g2.u(n2)) / 100 : (g2.h(n6) > 0 ? n3 * (g2.u(n6) + g2.h(n6)) / 100 : -n3 * (100 - g2.u(n2)) / 100);
        n5 += n5 * b.e.a.d(10) / 100;
        return n5;
    }

    public int d() {
        int n2 = this.c(8);
        f.b b2 = null;
        for (int i2 = 0; i2 < this.O.length; ++i2) {
            b2 = this.g(i2);
            n2 += b2 == null ? (short)0 : b2.i;
        }
        return n2 += this.G[0];
    }

    public int e() {
        int n2 = this.c(9);
        f.b b2 = null;
        for (int i2 = 0; i2 < this.O.length; ++i2) {
            b2 = this.g(i2);
            n2 += b2 == null ? (short)0 : b2.j;
        }
        return n2 += this.G[1];
    }

    public int f() {
        int n2 = this.c(11);
        f.b b2 = null;
        for (int i2 = 0; i2 < this.O.length; ++i2) {
            b2 = this.g(i2);
            n2 += b2 == null ? (short)0 : b2.k;
        }
        return n2 += this.G[2];
    }

    public final int B() {
        int n2 = this.B[3];
        f.b b2 = null;
        for (int i2 = 0; i2 < this.O.length; ++i2) {
            b2 = this.g(i2);
            n2 += b2 == null ? (short)0 : b2.r;
        }
        return n2;
    }

    public final int C() {
        int n2 = this.B[5];
        f.b b2 = null;
        for (int i2 = 0; i2 < this.O.length; ++i2) {
            b2 = this.g(i2);
            n2 += b2 == null ? (short)0 : b2.s;
        }
        return n2;
    }

    public final int D() {
        int n2 = this.B[7];
        f.b b2 = null;
        for (int i2 = 0; i2 < this.O.length; ++i2) {
            b2 = this.g(i2);
            n2 += b2 == null ? (short)0 : b2.t;
        }
        return n2;
    }

    public final f.b[] E() {
        f.b[] bArray = new f.b[this.O.length];
        f.a a2 = null;
        for (int i2 = 0; i2 < bArray.length; ++i2) {
            a2 = a.f.f(this.O[i2]);
            if (a2 == null || !a2.d()) continue;
            bArray[i2] = (f.b)a2;
        }
        return bArray;
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

    public final f.b g(int n2) {
        if (n2 < 0 || n2 >= ((g)((Object)a2)).O.length) {
            return null;
        }
        f.a a2 = a.f.f(((g)((Object)a2)).O[n2]);
        if (a2 == null) {
            return null;
        }
        return (f.b)a2;
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
        f.b[] bArray = this.E();
        for (int i2 = 0; i2 < bArray.length; ++i2) {
            if (bArray[i2] == null) continue;
            n3 += bArray[i2].u[n4];
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
            this.X = (short)b.e.a.b(0, n2, 100, -100);
        }
        if ((by & 1) != 0) {
            this.T = (short)b.e.a.b(0, n2, 100, -100);
        }
        if ((by & 8) != 0) {
            this.W = (short)b.e.a.b(0, n2, 100, -100);
        }
        if ((by & 4) != 0) {
            this.V = (short)b.e.a.b(0, n2, 100, -100);
        }
        if ((by & 2) != 0) {
            this.U = (short)b.e.a.b(0, n2, 100, -100);
        }
    }

    public final void a(short s) {
        c c2 = a.f.d((int)s);
        if (c2 == null) {
            return;
        }
        this.ae.addElement(c2);
    }

    public final void b(short s) {
        c c2;
        block9: {
            block8: {
                boolean bl;
                block7: {
                    c2 = a.f.c((int)s);
                    if (c2 == null) break block8;
                    short s2 = s;
                    Vector vector = this.af;
                    if (vector == null || vector.size() == 0) {
                        bl = false;
                    } else {
                        f.a a2 = null;
                        for (int i2 = 0; i2 < vector.size(); ++i2) {
                            a2 = (f.a)vector.elementAt(i2);
                            if (a2 == null || a2.c != s2) continue;
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
            this.b = (short)(this.b & ~(-s));
        } else if (s == 0) {
            this.b = 0;
        } else {
            short s2 = (short)(s & ~this.N);
            if (s2 == 0) {
                return false;
            }
            this.b = s;
        }
        return true;
    }

    public final void i(int n2) {
        this.c = n2;
    }

    public final int G() {
        return this.c;
    }

    public final short H() {
        return this.b;
    }

    public final g I() {
        g g2 = new g();
        new g().n = this.n;
        g2.p = this.p;
        g2.K = this.K;
        g2.I = this.I;
        g2.Q = this.Q;
        g2.R = this.R;
        g2.i = this.i;
        g2.L = this.L;
        g2.ad = this.ad;
        g2.S = this.S;
        g2.J = this.J;
        System.arraycopy(this.B, 0, g2.B, 0, this.B.length);
        System.arraycopy(this.O, 0, g2.O, 0, this.O.length);
        c c2 = null;
        if (this.ae.size() > 0) {
            for (int i2 = 0; i2 < this.ae.size(); ++i2) {
                c2 = (c)this.ae.elementAt(i2);
                g2.ae.addElement(c2);
            }
        }
        c c3 = null;
        if (this.af.size() > 0) {
            for (int i3 = 0; i3 < this.af.size(); ++i3) {
                c3 = (c)this.af.elementAt(i3);
                g2.af.addElement(c3);
            }
        }
        return g2;
    }

    public final int j(int n2) {
        for (int i2 = 0; i2 < this.O.length; ++i2) {
            if (this.O[i2] != n2) continue;
            return i2;
        }
        return -1;
    }

    public final boolean J() {
        return (this.b & 4) != 0;
    }

    public final boolean K() {
        return (this.b & 0x80) != 0 || (this.b & 0x100) != 0 || (this.b & 0x10) != 0 || (this.b & 0x40) != 0;
    }

    public final boolean L() {
        return (this.b & 0x800) != 0 || (this.b & 0x200) != 0;
    }

    public final g a(g[] gArray) {
        int n2;
        int n3 = 0;
        for (int i2 = 0; i2 < gArray.length; ++i2) {
            if (gArray[i2] == null || gArray[i2].w() || gArray[i2] == this) continue;
            ++n3;
        }
        if (n3 == 0) {
            return null;
        }
        g[] gArray2 = new g[n3];
        int n4 = 0;
        for (n2 = 0; n2 < gArray.length; ++n2) {
            if (gArray[n2] == null || gArray[n2].w() || gArray[n2] == this) continue;
            gArray2[n4] = gArray[n2];
            ++n4;
        }
        n2 = b.e.a.c(n3);
        return gArray2[n2];
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

    public final int a(g g2, boolean bl) {
        if (g2 == null) {
            return 0;
        }
        int n2 = ((g)this).d();
        int n3 = g2.e();
        n2 -= n3;
        if ((this = ((g)this).g(0)) != null) {
            n2 -= a.g.a(((f.b)this).p, ((f.b)this).q, g2);
        }
        n2 = n2 * (100 + b.e.a.d(10)) / 100;
        if (g2.M) {
            n2 = n2 * (50 + b.e.a.c(50)) / 100;
        }
        int n4 = n2 = n2 <= 0 ? 1 : n2;
        if (bl) {
            n2 <<= 1;
        }
        if (g2.n > 5) {
            n2 = (d.d.e + 1) * n2;
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
        Vector<c> vector = new Vector<c>();
        f.b b2 = this.g(7);
        c c2 = null;
        if (b2 != null && b2.d == 11 && (c2 = b2.f()) != null) {
            vector.addElement(c2);
        }
        if ((b2 = this.g(8)) != null && b2.d == 11 && (c2 = b2.f()) != null) {
            vector.addElement(c2);
        }
        return vector;
    }

    public final void Q() {
        this.a((byte)2, 9999);
        this.a((byte)4, 9999);
        this.a((byte)6, 9999);
        this.a((byte)14, 100);
        this.b = 0;
        this.c = 0;
    }

    public final void R() {
        this.B[3] = e[this.n][1];
        this.B[5] = e[this.n][2];
        this.B[7] = e[this.n][3];
        this.B[8] = e[this.n][4];
        this.B[9] = e[this.n][5];
        this.B[10] = e[this.n][6];
        this.B[11] = e[this.n][7];
    }

    public final void l(int n2) {
        f.b b2 = this.g(7);
        if (b2 != null && b2.d == 11) {
            a.f.e(b2.c, n2);
        }
        if ((b2 = this.g(8)) != null && b2.d == 11) {
            a.f.e(b2.c, n2);
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

    public final c n(int n2) {
        if (n2 < 0 || n2 >= this.af.size()) {
            return null;
        }
        return (c)this.af.elementAt(n2);
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

