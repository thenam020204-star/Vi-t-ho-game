/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package d;

import c.d;
import d.a;
import d.b;
import d.c;
import d.f;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public class g
extends b {
    public static final byte[] u = new byte[]{1, 2, 4, 8, 16};
    public static final byte[] v = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    public static String[] w = new String[]{d.a.q[19], d.a.q[20], d.a.q[21], d.a.q[22], d.a.q[23], d.a.q[24], d.a.q[24], d.a.q[25], d.a.q[25], d.a.q[26], d.a.q[26]};
    public static final byte[] x = new byte[]{4, 6, 7, 8, 9, 10, 10, 11, 11, 16, 16};
    public short y = 0;
    public short[] z = new short[3];
    private byte r = (byte)4;
    private short s = 0;
    private int t = 0;
    private int[] N = null;
    public byte[] A = null;
    private int[] O = null;
    private byte P = 0;
    public String B = "";
    private boolean Q = false;
    protected int C = 1;
    protected int D = 0;
    private int R = 100;
    private int S = 0;
    protected int E = 0;
    protected int F = 0;
    private int T = 0;
    public int G = 0;
    public int H = 0;
    public int I = 0;
    private String U = "";
    protected int[] J = null;
    private short V = 0;
    private short W = 0;
    private short X = 0;
    private short Y = 0;
    private short Z = 0;
    private int aa = 0;
    private int ab = 0;
    private int ac = 0;
    private int ad = 0;
    private int ae = 0;
    public byte K = 0;
    private byte af = 0;
    private c.a[] ag = null;
    private c.b[] ah = null;
    private short ai = 0;
    private short aj = (short)120;
    public boolean L = false;
    public short M = 0;

    public g() {
        super((byte)6);
        this.J = new int[13];
        this.n = 20;
        this.o = 50;
        this.O = new int[11];
        for (int i2 = 0; i2 < this.O.length; ++i2) {
            this.O[i2] = -1;
        }
        this.N = new int[8];
        this.A = new byte[8];
    }

    public final void a(int n2, int n3) {
        int n4;
        boolean bl;
        int n5;
        boolean bl2;
        if (d.a.s != null) {
            return;
        }
        if (n2 == 53 && g5.g != 0) {
            Object object;
            g g2 = g5;
            int n6 = g2.l() / 16;
            byte by = g2.m() / 16;
            byte by2 = g2.h();
            switch (by2) {
                case 3: {
                    --by;
                    break;
                }
                case 2: {
                    ++by;
                    break;
                }
                case 1: {
                    --n6;
                    break;
                }
                case 0: {
                    ++n6;
                }
            }
            f f2 = d.a.y.c(n6, by);
            if (f2 == null || f2.k != 7) {
                object = null;
            } else if (f2.q() != 1 || !f2.t()) {
                object = null;
            } else {
                Object object2;
                if (!f2.r && f2.e >= 0 && f2.e <= 7) {
                    by = d.b.a(by2);
                    boolean bl3 = false;
                    byte by3 = by;
                    object2 = f2;
                    ((b)object2).a(by3, bl3, false);
                }
                by = (byte)f2.f;
                object = object2 = d.a.a(d.a.y.d, (short)by);
            }
            d.a.s = object;
            return;
        }
        int n7 = n3;
        g g3 = g5;
        if (g3.g == 0) {
            bl2 = false;
        } else {
            g g4;
            n5 = d.g.k(n7);
            if (n5 == 4) {
                bl2 = false;
            } else if (g3.h() == n5 && g3.l(g3.h())) {
                g3.g = 0;
                g3.P = 0;
                bl = true;
                n4 = g3.h();
                g4 = g3;
                g4.a((byte)n4, bl, false);
                bl2 = true;
            } else {
                g3.b((byte)n5);
                bl = false;
                n4 = n5;
                g4 = g3;
                g4.a((byte)n4, bl, false);
                bl2 = false;
            }
        }
        n7 = n3;
        g3 = g5;
        if (g3.g == 0) {
            g g5;
            if (g3.P < 16) {
                g3.a(g3.h());
                g3.P = (byte)(g3.P + 4);
                if (d.a.k) {
                    g5 = null;
                    g g6 = null;
                    n5 = 0;
                    boolean bl4 = false;
                    for (int i2 = 1; i2 < d.a.C.length; ++i2) {
                        int n8;
                        g5 = d.a.C[i2 - 1];
                        g6 = d.a.C[i2];
                        n5 = Math.abs(g5.l() - g6.l());
                        if (n5 + (n8 = Math.abs(g5.m() - g6.m())) <= 16) continue;
                        if (g6.h() != g5.r) {
                            g6.b(g5.r);
                        }
                        bl = true;
                        n4 = g6.h();
                        g g7 = g6;
                        g7.a((byte)n4, bl, false);
                        g6.a(g6.h());
                    }
                }
            }
            if (g3.P >= 16) {
                g3.P = 0;
                for (n5 = 0; n5 < d.a.C.length; ++n5) {
                    d.a.C[n5].r = d.a.C[n5].h();
                }
                if (n7 == 0 || d.g.k(n7) != g3.h() || !g3.l(g3.h())) {
                    for (n5 = 0; n5 < d.a.C.length; ++n5) {
                        bl = false;
                        n4 = d.a.C[n5].h();
                        g g8 = d.a.C[n5];
                        g8.a((byte)n4, bl, false);
                    }
                    g3.g = 1;
                }
                if ((d.a.s = d.a.y.a((g5 = g3).i(), g5.j(), (byte)2)) != null) {
                    for (n5 = 0; n5 < d.a.C.length; ++n5) {
                        bl = false;
                        n4 = d.a.C[n5].h();
                        g g9 = d.a.C[n5];
                        g9.a((byte)n4, bl, false);
                    }
                    g3.g = 1;
                    return;
                }
                g5 = g3;
                f f3 = d.a.y.e(g5.i(), g5.j());
                d.a.s = f3 == null || !f3.t() || f3.q() != 3 ? null : d.a.y.a(f3);
                if (d.a.s != null) {
                    for (n5 = 0; n5 < d.a.C.length; ++n5) {
                        bl = false;
                        n4 = d.a.C[n5].h();
                        g g10 = d.a.C[n5];
                        g10.a((byte)n4, bl, false);
                    }
                    g3.g = 1;
                }
            }
        }
    }

    private static byte k(int n2) {
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

    private boolean l(int n2) {
        switch (n2) {
            case 2: {
                return d.a.y.b(this.l(), this.m() + 16);
            }
            case 3: {
                return d.a.y.b(this.l(), this.m() - 16);
            }
            case 1: {
                return d.a.y.b(this.l() - 16, this.m());
            }
            case 0: {
                return d.a.y.b(this.l() + 16, this.m());
            }
        }
        return false;
    }

    public final int e(int n2) {
        if (n2 < 0 || n2 >= this.J.length) {
            return 0;
        }
        switch (n2) {
            case 1: {
                return this.D;
            }
            case 0: {
                return this.C;
            }
        }
        return this.J[n2];
    }

    public final void a(byte by, int n2) {
        switch (by) {
            case 1: {
                this.m(n2);
                return;
            }
            case 0: {
                this.n(n2);
                return;
            }
        }
        this.d(by, n2);
    }

    public final void b(byte by, int n2) {
        switch (by) {
            case 1: {
                this.m(this.D + n2);
                return;
            }
            case 0: {
                this.n(this.C + n2);
                return;
            }
            case 13: {
                this.g(this.R + n2);
                return;
            }
            case 12: {
                this.f(n2);
                return;
            }
        }
        this.d(by, this.e((int)by) + n2);
    }

    public final c.a[] f(int n2) {
        n2 = this.S + n2;
        int n3 = this.S;
        this.S = h.b.c(n2, 300, 0);
        Vector<c.a> vector = new Vector<c.a>();
        c.a a2 = null;
        for (int i2 = 0; i2 < d.a.v.length; ++i2) {
            if (d.a.v[i2].c != this.f || d.a.v[i2].b <= n3 || d.a.v[i2].b > this.S || (a2 = d.a.c((int)d.a.v[i2].p)) == null) continue;
            this.b(a2.p);
            vector.addElement(a2);
        }
        if (vector.size() == 0) {
            return null;
        }
        Object[] objectArray = new c.a[vector.size()];
        vector.copyInto(objectArray);
        return objectArray;
    }

    private void d(byte by, int n2) {
        if (by < 0 || by >= this.J.length || by == 1 || by == 0) {
            return;
        }
        switch (by) {
            case 2: {
                this.J[by] = h.b.b(0, n2, this.J[3], 0);
                return;
            }
            case 4: {
                this.J[by] = h.b.b(0, n2, this.J[5], 0);
                return;
            }
            case 6: {
                this.J[by] = h.b.b(0, n2, this.J[7], 0);
                return;
            }
        }
        this.J[by] = h.b.b(0, n2, 9999, 0);
    }

    private boolean m(int n2) {
        if (n2 > Integer.MAX_VALUE) {
            n2 = Integer.MAX_VALUE;
        } else if (n2 < 0) {
            n2 = 0;
        }
        if (this.C >= 20) {
            return false;
        }
        if (n2 >= d.a.u[this.f][this.C].b) {
            this.b((byte)0, 1);
            this.m(n2 - d.a.u[this.f][this.C - 1].b);
            return true;
        }
        this.D = n2;
        return false;
    }

    private static boolean a(c.f[] fArray, short s2) {
        if (fArray == null) {
            return false;
        }
        for (int i2 = 0; i2 < fArray.length; ++i2) {
            if (fArray[i2].p != s2) continue;
            return true;
        }
        return false;
    }

    private void n(int n2) {
        if (n2 <= 0) {
            n2 = 1;
        } else if (n2 > 20) {
            n2 = 20;
        }
        this.D = 0;
        this.C = n2;
        this.A();
        this.a((byte)2, this.e(3));
        this.a((byte)4, this.e(5));
        this.a((byte)6, this.e(7));
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
        if (((b)((Object)a2)).c == null) {
            return;
        }
        f.a a2 = ((b)((Object)a2)).c.b(20);
        a2.a(graphics, n2, n3);
    }

    public final boolean s() {
        return this.Q;
    }

    public final int t() {
        return this.R;
    }

    public final void g(int n2) {
        this.R = h.b.c(n2, 100, 0);
    }

    public final void b(boolean n2) {
        this.Q = n2;
        if (n2 != 0) {
            n2 = 0;
            for (int i2 = 0; i2 < d.a.m[this.f].length; ++i2) {
                n2 = d.a.a(d.a.m[this.f][i2], 1);
                if (n2 < 0 || n2 >= 256) continue;
                this.c(n2, i2);
            }
            return;
        }
        c.f f2 = null;
        for (int i3 = 0; i3 < d.a.m[this.f].length; ++i3) {
            f2 = d.a.f(this.O[i3]);
            if (f2 == null) {
                d.a.m[this.f][i3] = -1;
            } else {
                d.a.m[this.f][i3] = f2.p;
                d.a.d(this.O[i3], 1);
            }
            this.O[i3] = -1;
        }
    }

    public final int u() {
        return this.ai * 100 / this.aj;
    }

    public final void v() {
        this.ai = 0;
    }

    public final void a(short s2) {
        this.ai = s2;
    }

    public final short w() {
        return this.aj;
    }

    public final void x() {
        this.ai = (short)(this.ai + 1);
        if (this.ai > this.aj) {
            this.ai = this.aj;
        }
    }

    public final boolean y() {
        return this.ai >= this.aj;
    }

    public final boolean z() {
        return this.J[2] <= 0;
    }

    public final void A() {
        if (d.a.u == null || this.f >= d.a.u.length || this.C - 1 >= d.a.u[this.f].length || d.a.u[this.f][this.C - 1] == null) {
            return;
        }
        Object object = d.a.u[this.f][this.C - 1];
        this.J[3] = ((c)object).d;
        this.J[5] = ((c)object).e;
        this.J[7] = ((c)object).f;
        this.J[8] = ((c)object).g;
        this.J[9] = ((c)object).i;
        this.J[10] = ((c)object).h;
        this.J[11] = ((c)object).j;
        if (((c)object).c > 0) {
            short s2 = ((c)object).c;
            object = this;
            c.b b2 = d.a.b((int)s2);
            if (b2 != null) {
                if (((g)object).ah == null) {
                    ((g)object).ah = new c.b[1];
                    ((g)object).ah[0] = b2;
                } else if (!d.g.a(((g)object).ah, s2)) {
                    c.b[] bArray = new c.b[((g)object).ah.length + 1];
                    System.arraycopy(((g)object).ah, 0, bArray, 0, ((g)object).ah.length);
                    bArray[bArray.length - 1] = b2;
                    ((g)object).ah = bArray;
                }
            }
        }
        this.q();
        this.B();
    }

    protected final void B() {
        this.aj = (short)((18 * this.J[11] * this.J[11] - 3000 * this.J[11] + 150000) / 1000);
    }

    private void q() {
        this.E = this.e(8);
        this.T = this.e(11);
        this.F = this.e(9);
        this.G = 0;
        this.H = 0;
        this.I = 0;
        this.y = 0;
        this.Z = 0;
        this.W = 0;
        this.V = 0;
        this.Y = 0;
        this.X = 0;
        this.c(this.K, (int)this.af);
        d[] dArray = this.F();
        for (int i2 = 0; i2 < dArray.length; ++i2) {
            if (dArray[i2] == null) continue;
            g g2 = this;
            d d2 = dArray[i2];
            if (g2 == null) continue;
            g g3 = g2;
            int n2 = g3.E + d2.a;
            g g4 = g2;
            g2.E = n2;
            g3 = g2;
            n2 = g3.F + d2.b;
            g4 = g2;
            g2.F = n2;
            g3 = g2;
            n2 = g3.T + d2.c;
            g4 = g2;
            g2.T = n2;
            g2.G += d2.d;
            g2.H += d2.e;
            g2.I += d2.f;
            g2.b((byte)3, (int)d2.j);
            g2.b((byte)5, (int)d2.k);
            g2.b((byte)7, (int)d2.l);
            for (int i3 = 0; i3 < d2.m.length; ++i3) {
                short s2 = d2.m[i3];
                n2 = u[i3];
                g4 = g2;
                if (n2 == 0) continue;
                if ((n2 & 0x10) != 0) {
                    g4.Z = (short)h.b.b(g4.Z, s2, 255, 0);
                }
                if ((n2 & 1) != 0) {
                    g4.V = (short)h.b.b(g4.V, s2, 255, 0);
                }
                if ((n2 & 8) != 0) {
                    g4.Y = (short)h.b.b(g4.Y, s2, 255, 0);
                }
                if ((n2 & 4) != 0) {
                    g4.X = (short)h.b.b(g4.X, s2, 255, 0);
                }
                if ((n2 & 2) == 0) continue;
                g4.W = (short)h.b.b(g4.W, s2, 255, 0);
            }
            if (d2.o < 0) continue;
            g2.y = (short)(g2.y | 1 << d2.o);
        }
    }

    public final byte[] C() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            int n2;
            dataOutputStream.writeShort(this.f);
            dataOutputStream.writeUTF(this.B);
            dataOutputStream.writeUTF(this.U);
            dataOutputStream.writeShort(this.C);
            dataOutputStream.writeBoolean(this.Q);
            dataOutputStream.writeShort(this.J[2]);
            dataOutputStream.writeShort(this.J[4]);
            dataOutputStream.writeShort(this.J[6]);
            dataOutputStream.writeShort(this.R);
            dataOutputStream.writeUTF(this.b);
            dataOutputStream.writeByte(this.K);
            dataOutputStream.writeByte(this.af);
            dataOutputStream.writeShort(this.S);
            dataOutputStream.writeInt(this.D);
            for (n2 = 0; n2 < this.O.length; ++n2) {
                dataOutputStream.writeByte(this.O[n2]);
            }
            if (this.ag == null) {
                dataOutputStream.writeByte(0);
            } else {
                dataOutputStream.writeByte(this.ag.length);
                for (n2 = 0; n2 < this.ag.length; ++n2) {
                    dataOutputStream.writeShort(this.ag[n2].p);
                }
            }
            if (this.ah == null) {
                dataOutputStream.writeByte(0);
            } else {
                dataOutputStream.writeByte(this.ah.length);
                for (n2 = 0; n2 < this.ah.length; ++n2) {
                    dataOutputStream.writeShort(this.ah[n2].p);
                }
            }
            dataOutputStream.writeByte(this.z.length);
            for (n2 = 0; n2 < this.z.length; ++n2) {
                dataOutputStream.writeShort(this.z[n2]);
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

    public final void b(DataInputStream dataInputStream) {
        try {
            int n2;
            this.f = dataInputStream.readShort();
            this.B = dataInputStream.readUTF();
            this.U = dataInputStream.readUTF();
            this.C = dataInputStream.readShort();
            this.Q = dataInputStream.readBoolean();
            this.J[2] = dataInputStream.readShort();
            this.J[4] = dataInputStream.readShort();
            this.J[6] = dataInputStream.readShort();
            this.R = dataInputStream.readShort();
            this.b = dataInputStream.readUTF();
            this.K = dataInputStream.readByte();
            this.af = dataInputStream.readByte();
            this.S = dataInputStream.readShort();
            this.D = dataInputStream.readInt();
            for (int i2 = 0; i2 < this.O.length; ++i2) {
                this.O[i2] = h.b.a((int)dataInputStream.readByte());
            }
            Vector<c.a> vector = new Vector<c.a>();
            c.f f2 = null;
            int n3 = dataInputStream.readByte();
            if (n3 > 0) {
                for (n2 = 0; n2 < n3; ++n2) {
                    f2 = d.a.c((int)dataInputStream.readShort());
                    if (f2 == null) continue;
                    vector.addElement((c.a)f2);
                }
                if (vector.size() > 0) {
                    this.ag = new c.a[vector.size()];
                    vector.copyInto(this.ag);
                }
            }
            vector.removeAllElements();
            n2 = dataInputStream.readByte();
            if (n2 > 0) {
                for (n3 = 0; n3 < n2; ++n3) {
                    f2 = d.a.b((int)dataInputStream.readShort());
                    if (f2 == null) continue;
                    vector.addElement((c.a)f2);
                }
                if (vector.size() > 0) {
                    this.ah = new c.b[vector.size()];
                    vector.copyInto(this.ah);
                }
            }
            if ((n3 = dataInputStream.readByte()) > 0) {
                this.z = new short[n3];
                for (int i3 = 0; i3 < n3; ++i3) {
                    this.z[i3] = dataInputStream.readShort();
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
            ((b)((Object)iOException2)).f = dataInputStream.readShort();
            ((g)((Object)iOException2)).B = dataInputStream.readUTF();
            ((g)((Object)iOException2)).U = dataInputStream.readUTF();
            ((b)((Object)iOException2)).b = dataInputStream.readUTF();
            ((g)((Object)iOException2)).Q = dataInputStream.readBoolean();
            ((g)((Object)iOException2)).C = dataInputStream.readShort();
            ((g)((Object)iOException2)).K = dataInputStream.readByte();
            ((g)((Object)iOException2)).af = dataInputStream.readByte();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public static int a(byte by, int n2, g g2) {
        int n3;
        if (g2 == null || n2 == 0) {
            return 0;
        }
        int n4 = 0;
        byte by2 = by;
        switch (by2) {
            case 16: {
                n3 = 4;
                break;
            }
            case 8: {
                n3 = 1;
                break;
            }
            case 4: {
                n3 = 8;
                break;
            }
            case 2: {
                n3 = 16;
                break;
            }
            case 1: {
                n3 = 2;
                break;
            }
            default: {
                n3 = 32;
            }
        }
        n4 = n3;
        by2 = by;
        switch (by2) {
            case 16: {
                byte by3 = 1;
                break;
            }
            case 8: {
                byte by3 = 16;
                break;
            }
            case 4: {
                byte by3 = 2;
                break;
            }
            case 2: {
                byte by3 = 8;
                break;
            }
            case 1: {
                byte by3 = 4;
                break;
            }
            default: {
                byte by3 = by2 = 32;
            }
        }
        n4 = g2.d((byte)n4) > 0 ? -n2 * 3 / 2 * (100 - g2.e(by)) / 100 : (g2.d(by2) > 0 ? n2 * (g2.e(by2) + g2.d(by2)) / 100 : -n2 * (100 - g2.e(by)) / 100);
        n4 += n4 * h.b.d(10) / 100;
        return n4;
    }

    public final int D() {
        return this.E;
    }

    public final int E() {
        return this.F;
    }

    public final d[] F() {
        d[] dArray = new d[this.O.length];
        c.f f2 = null;
        for (int i2 = 0; i2 < dArray.length; ++i2) {
            f2 = d.a.f(this.O[i2]);
            if (f2 == null || !f2.c()) continue;
            dArray[i2] = (d)f2;
        }
        return dArray;
    }

    public final void c(int n2, int n3) {
        if (n3 < 0 || n3 >= this.O.length) {
            return;
        }
        c.f f2 = d.a.f(n2);
        this.a(f2, n3, n2);
    }

    private void a(c.f f2, int n2, int n3) {
        if (n2 < 0 || n2 >= this.O.length) {
            return;
        }
        if (f2 == null) {
            this.O[n2] = -1;
        } else if (x[n2] != f2.q) {
            return;
        }
        this.O[n2] = n3;
        this.q();
        this.B();
    }

    public final d c(byte by) {
        if (by < 0 || by >= ((g)((Object)f2)).O.length) {
            return null;
        }
        c.f f2 = d.a.f(((g)((Object)f2)).O[by]);
        if (f2 == null || c.f.a(f2.q) != 4) {
            return null;
        }
        return (d)f2;
    }

    public final void G() {
        for (int i2 = 0; i2 < this.O.length; ++i2) {
            this.a(d.a.f(this.O[i2]), (int)v[i2], this.O[i2]);
        }
    }

    public final short d(byte by) {
        switch (by) {
            case 16: {
                return this.Z;
            }
            case 1: {
                return this.V;
            }
            case 8: {
                return this.Y;
            }
            case 4: {
                return this.X;
            }
            case 2: {
                return this.W;
            }
        }
        return 0;
    }

    private int e(byte by) {
        switch (by) {
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
            this.Z = (short)h.b.b(0, n2, 255, -255);
        }
        if ((by & 1) != 0) {
            this.V = (short)h.b.b(0, n2, 255, -255);
        }
        if ((by & 8) != 0) {
            this.Y = (short)h.b.b(0, n2, 255, -255);
        }
        if ((by & 4) != 0) {
            this.X = (short)h.b.b(0, n2, 255, -255);
        }
        if ((by & 2) != 0) {
            this.W = (short)h.b.b(0, n2, 255, -255);
        }
    }

    public final void b(short s2) {
        c.a a2 = d.a.c((int)s2);
        if (a2 == null) {
            return;
        }
        if (this.ag == null) {
            this.ag = new c.a[1];
            this.ag[0] = a2;
            return;
        }
        if (d.g.a(this.ag, s2)) {
            return;
        }
        c.a[] aArray = new c.a[this.ag.length + 1];
        System.arraycopy(this.ag, 0, aArray, 0, this.ag.length);
        aArray[aArray.length - 1] = a2;
        this.ag = aArray;
    }

    public final c.b[] H() {
        return this.ah;
    }

    public final c.a[] I() {
        return this.ag;
    }

    public final void e() {
    }

    public final void b() {
    }

    public final void a() {
    }

    public final int J() {
        return this.T;
    }

    public final boolean c(short s2) {
        if (s2 < 0) {
            this.s = (short)(this.s & ~s2);
        } else if (s2 == 0) {
            this.s = 0;
        } else {
            short s3 = (short)(s2 & ~this.M);
            if (s3 == 0) {
                return false;
            }
            this.s = s2;
        }
        return true;
    }

    public final void h(int n2) {
        this.t = n2;
    }

    public final int K() {
        return this.t;
    }

    public final short L() {
        return this.s;
    }

    public final g M() {
        g g2 = new g();
        new g().f = this.f;
        g2.B = this.B;
        g2.U = this.U;
        g2.C = this.C;
        g2.Q = this.Q;
        g2.R = this.R;
        g2.b = this.b;
        g2.K = this.K;
        g2.af = this.af;
        g2.S = this.S;
        g2.D = this.D;
        System.arraycopy(this.J, 0, g2.J, 0, this.J.length);
        System.arraycopy(this.O, 0, g2.O, 0, this.O.length);
        if (this.ag != null) {
            g2.ag = new c.a[this.ag.length];
            System.arraycopy(this.ag, 0, g2.ag, 0, this.ag.length);
        }
        if (this.ah != null) {
            g2.ah = new c.b[this.ah.length];
            System.arraycopy(this.ah, 0, g2.ah, 0, this.ah.length);
        }
        this.q();
        this.B();
        return g2;
    }

    public final int i(int n2) {
        for (int i2 = 0; i2 < this.O.length; ++i2) {
            if (this.O[i2] != n2) continue;
            return i2;
        }
        return -1;
    }

    public final boolean N() {
        return (this.s & 4) != 0;
    }

    public final boolean O() {
        return (this.s & 0x80) != 0 || (this.s & 0x100) != 0 || (this.s & 0x10) != 0 || (this.s & 0x40) != 0;
    }

    public final boolean P() {
        return (this.s & 0x800) != 0 || (this.s & 0x200) != 0;
    }

    public final g a(g[] gArray) {
        int n2;
        int n3 = 0;
        for (int i2 = 0; i2 < gArray.length; ++i2) {
            if (gArray[i2] == null || gArray[i2].z() || gArray[i2] == this) continue;
            ++n3;
        }
        if (n3 == 0) {
            return null;
        }
        g[] gArray2 = new g[n3];
        int n4 = 0;
        for (n2 = 0; n2 < gArray.length; ++n2) {
            if (gArray[n2] == null || gArray[n2].z() || gArray[n2] == this) continue;
            gArray2[n4] = gArray[n2];
            ++n4;
        }
        n2 = h.b.c(n3);
        return gArray2[n2];
    }

    public final void j(int n2) {
        switch (n2) {
            case 0: {
                this.E -= this.N[0];
                return;
            }
            case 1: {
                this.F -= this.N[1];
                return;
            }
            case 2: {
                this.T -= this.N[2];
                return;
            }
            case 3: {
                this.V = (short)(this.V - this.N[3]);
                return;
            }
            case 4: {
                this.W = (short)(this.W - this.N[4]);
                return;
            }
            case 5: {
                this.X = (short)(this.X - this.N[5]);
                return;
            }
            case 6: {
                this.Y = (short)(this.Y - this.N[6]);
                return;
            }
            case 7: {
                this.Z = (short)(this.Z - this.N[7]);
            }
        }
    }

    public final void a(int n2, int n3, int n4) {
        if (n3 == 0 || n4 <= 0) {
            this.j(n2);
            return;
        }
        switch (n2) {
            case 0: {
                this.N[0] = n3;
                this.E += n3;
                this.A[0] = (byte)n4;
                return;
            }
            case 1: {
                this.N[1] = n3;
                this.F += n3;
                this.A[1] = (byte)n4;
                return;
            }
            case 2: {
                this.N[2] = n3;
                this.T += n3;
                this.A[2] = (byte)n4;
                return;
            }
            case 3: {
                this.N[3] = n3;
                this.V = (short)(this.V + n3);
                this.A[3] = (byte)n4;
                return;
            }
            case 4: {
                this.N[4] = n3;
                this.W = (short)(this.W + n3);
                this.A[4] = (byte)n4;
                return;
            }
            case 5: {
                this.N[5] = n3;
                this.X = (short)(this.X + n3);
                this.A[5] = (byte)n4;
                return;
            }
            case 6: {
                this.N[6] = n3;
                this.Y = (short)(this.Y + n3);
                this.A[6] = (byte)n4;
                return;
            }
            case 7: {
                this.N[7] = n3;
                this.Z = (short)(this.Z + n3);
                this.A[7] = (byte)n4;
            }
        }
    }

    public final int a(g g2, boolean bl) {
        if (g2 == null) {
            return 0;
        }
        g g3 = this;
        int n2 = g3.E;
        g3 = g2;
        int n3 = g3.F;
        n2 -= n3;
        if ((this = ((g)this).c((byte)0)) != null) {
            n2 -= d.g.a(((d)this).h, ((d)this).i, g2);
        }
        n2 = n2 * (100 + h.b.d(10)) / 100;
        if (g2.L) {
            n2 = n2 * (50 + h.b.c(50)) / 100;
        }
        int n4 = n2 = n2 <= 0 ? 1 : n2;
        if (bl) {
            n2 <<= 1;
        }
        return n2;
    }

    public final int Q() {
        return this.e(2) * 100 / this.e(3);
    }

    public final b.a[] R() {
        if (this.y == 0) {
            return null;
        }
        Vector<b.a> vector = new Vector<b.a>();
        b.a a2 = null;
        int n2 = 0;
        int n3 = this.y;
        while (n3 != 0) {
            if ((n3 & 1) != 0) {
                a2 = d.a.d(n2);
                vector.addElement(a2);
            }
            n3 >>= 1;
            ++n2;
        }
        if (vector.size() == 0) {
            return null;
        }
        b.a[] aArray = new b.a[vector.size()];
        for (int i2 = 0; i2 < aArray.length; ++i2) {
            aArray[i2] = (b.a)vector.elementAt(i2);
        }
        return aArray;
    }

    public final String d() {
        return null;
    }

    public final void S() {
        this.a((byte)2, 9999);
        this.a((byte)4, 9999);
        this.a((byte)6, 9999);
        this.a((byte)13, 100);
    }
}

