/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package d;

import b.e;
import c.k;
import c.q;
import c.t;
import c.u;
import d.a;
import d.b;
import d.d;
import d.f;
import d.g;
import d.h;
import d.i;
import d.l;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.MainMIDlet;

public final class c
extends a
implements main.a {
    private static byte[] l = new byte[]{1, 2, 5};
    private static final short[][] m = new short[][]{{209, 138}, {181, 170}, {209, 200}, {141, 160}, {141, 190}};
    private static final short[][] n = new short[][]{{54, 138}, {81, 185}, {41, 215}};
    private Image o = null;
    private Image p = null;
    private Image q = null;
    private Image r = null;
    private Image s = null;
    private Image t = null;
    private Image u = null;
    public static Image g = null;
    public static Image[] h = null;
    public static Image i = null;
    public static Image j = null;
    private Vector v = new Vector();
    private b.f[] w = null;
    public b.c[] k = null;
    private b.b[] x = null;
    private b.b y = null;
    private int z = 0;
    private int[] A = null;
    private int B = -1;
    private int C = -1;
    private int D = -1;
    private int[][] E = null;
    private main.c F = null;
    private Hashtable G = null;
    private c.b H = null;
    private t I = null;
    private t J = null;
    private t K = null;
    private b.f L = null;
    private b.f M = null;
    private f.c N = null;
    private f.c O = null;
    private f.b P = null;
    private byte Q = 0;
    private byte R = 1;
    private byte S = 1;
    private int T = 0;
    private g U = null;
    private boolean V = true;
    private byte W = (byte)3;
    private int X = -1;
    private e Y = null;
    private static String[] Z = new String[]{"\u6fc0\u6d3b", "\u8fd4\u56de"};
    private boolean aa = false;
    private byte ab = (byte)-1;
    private int ac = 0;
    private int ad = 0;
    private t ae = null;

    public static c a(main.c c2) {
        c c3 = new c();
        new c().F = c2;
        return c3;
    }

    public static c a(e e2) {
        c c2 = new c();
        new c().Y = e2;
        return c2;
    }

    private c() {
        super((byte)4);
        this.U = new g(this);
        this.U.a(this);
        this.ab = (byte)3;
    }

    public final void a() {
        this.c = true;
        b.a.a();
        Object object = b.a.x.k();
        if (object != null) {
            this.E = new int[((e[])object).length][2];
            for (int i2 = 0; i2 < this.E.length; ++i2) {
                this.E[i2][0] = object[i2].e();
                this.E[i2][1] = object[i2].d();
            }
        }
        b.a.k();
        b.a.x.m();
        b.a.x.n();
        System.gc();
        e.a.b.a(100L);
        super.a(10);
        main.b.a.a();
        long l2 = System.currentTimeMillis();
        this.G = new Hashtable();
        object = this;
        e.a.c c2 = e.a.c.a("/b.pak");
        if (object.F != null) {
            object.o = c2.b(object.F.e + "_t.png");
            object.p = c2.b(object.F.f + "_b.png");
        } else {
            object.o = c2.b(object.Y.m() + "_t.png");
            object.p = c2.b(object.Y.n() + "_b.png");
        }
        if (object.o == null || object.p == null) {
            object.p = c2.b("0_b.png");
            object.o = c2.b("0_t.png");
        }
        object.q = c2.b("bg.png");
        object.r = c2.b("pointer.png");
        g = c2.b("status.png");
        Image[] imageArray = new Image[3];
        h = imageArray;
        imageArray[0] = c2.b("a.png");
        d.c.h[1] = c2.b("d.png");
        d.c.h[2] = c2.b("s.png");
        i = c2.b("up.png");
        j = c2.b("down.png");
        object.t = c2.b("miss.png");
        object.s = c2.b("statusBorder.png");
        object.u = c2.b("angry.png");
        super.a(20);
        main.b.a.a();
        this.A = new int[2];
        this.A[0] = b.a.B[0].j;
        this.A[1] = b.a.B[0].k;
        this.B = b.a.B[0].e;
        this.C = b.a.B[0].d();
        this.D = b.a.B[0].e();
        this.t();
        this.l();
        this.X = b.a.am;
        if (this.F != null && this.F.d >= 0) {
            b.a.c(this.F.d, -1);
        } else if (this.Y != null) {
            b.a.c(this.Y.l(), -1);
        }
        super.a(100);
        main.b.a.a();
        this.c = false;
        long l3 = 1500L - (System.currentTimeMillis() - l2);
        e.a.b.a(l3 <= 0L ? 100L : l3);
    }

    public final void b() {
        e[] eArray;
        super.b();
        this.c = true;
        b.a.a();
        super.a(0);
        super.b();
        c c2 = this;
        this.p = null;
        c2.o = null;
        c2.q = null;
        c2.r = null;
        g = null;
        h = null;
        h = null;
        i = null;
        j = null;
        c2.u = null;
        c2.t = null;
        c2.s = null;
        this.G.clear();
        this.G = null;
        this.H = null;
        c2 = this;
        for (int i2 = 0; i2 < b.a.B.length; ++i2) {
            if (c2.w[i2].w()) {
                c2.w[i2].a((byte)2, 1);
            }
            c2.w[i2].B();
            c2.w[i2].e((short)0);
            c2.w[i2].c = e.b.c.b(c2.w[i2].b);
            c2.w[i2].a(c2.B, c2.D);
            c2.w[i2].a(c2.C);
            b.a.B[i2].j = c2.A[0];
            b.a.B[i2].k = c2.A[1];
        }
        b.a.x.a(b.a.B[0]);
        this.w = null;
        for (int i3 = 0; i3 < this.k.length; ++i3) {
            if (this.k[i3] == null) continue;
            this.k[i3].k();
        }
        this.k = null;
        super.a(30);
        main.b.a.a();
        long l2 = System.currentTimeMillis();
        b.a.x.o();
        c2 = this;
        if (b.a.x != null && (eArray = b.a.x.k()) != null && c2.E != null && eArray.length == c2.E.length) {
            for (int i4 = 0; i4 < eArray.length; ++i4) {
                eArray[i4].a(eArray[i4].e, c2.E[i4][0]);
                eArray[i4].a(c2.E[i4][1]);
            }
        }
        super.a(60);
        main.b.a.a();
        b.a.x.l();
        super.a(100);
        main.b.a.a();
        if (this.Y != null) {
            this.Y.v = true;
            this.Y.a = false;
            this.Y.o = true;
            this.Y.h();
        }
        if (this.F != null) {
            this.F.c();
        }
        b.a.c(this.X, -1);
        long l3 = 1500L - (System.currentTimeMillis() - l2);
        e.a.b.a(l3 <= 0L ? 100L : l3);
        this.c = false;
    }

    private void l() {
        this.k = new b.c[3];
        if (this.F != null) {
            this.k[0] = this.F.a == 0 ? null : b.a.c(this.F.a);
            this.k[1] = this.F.b == 0 ? null : b.a.c(this.F.b);
            this.k[2] = this.F.c == 0 ? null : b.a.c(this.F.c);
        } else {
            this.k[0] = this.Y.s == 0 ? null : b.a.c(this.Y.s);
            this.k[1] = this.Y.t == 0 ? null : b.a.c(this.Y.t);
            this.k[2] = this.Y.u == 0 ? null : b.a.c(this.Y.u);
        }
        int n2 = 0;
        int n3 = 0;
        for (int i2 = 0; i2 < this.k.length; ++i2) {
            if (this.k[i2] == null) continue;
            this.T = i2;
            n3 += this.k[i2].c(0);
            ++n2;
            this.G.put(this.k[i2], new Vector());
            this.k[i2].a();
            this.k[i2].a(0, -1);
            this.k[i2].j = n[i2][0];
            this.k[i2].k = n[i2][1];
            this.k[i2].s();
        }
        this.z = n3 / n2;
    }

    public final void a(Graphics graphics) {
        d.a.c(graphics);
        if (this.c) {
            d.a.a(graphics, this.d);
            return;
        }
        Graphics graphics2 = graphics;
        c c2 = this;
        if (c2.o != null) {
            graphics2.drawImage(c2.o, 0, 0, 20);
        }
        if (c2.p != null) {
            graphics2.drawImage(c2.p, 0, c2.o.getHeight(), 20);
        }
        graphics2 = graphics;
        c2 = this;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        if (c2.q != null) {
            n4 = c2.q.getWidth();
            n5 = c2.q.getHeight();
        } else {
            n4 = main.b.d / 3;
            n5 = main.b.d / 3;
        }
        for (int i2 = 0; i2 < 3; ++i2) {
            n2 = i2 * n4;
            n3 = main.b.e - n5;
            b.f f2 = c2.w[i2];
            int n6 = n3;
            int n7 = n2;
            Graphics graphics3 = graphics2;
            c c3 = c2;
            if (c3.q != null) {
                graphics3.drawImage(c3.q, n7, n6, 20);
            } else {
                graphics3.setColor(15454341);
                graphics3.fillRect(n7, n6, main.b.d / 3, main.b.d / 3);
            }
            if (f2 == null) continue;
            int n8 = n7 + 5;
            int n9 = n6 + 4;
            int n10 = f2.r();
            int n11 = 6;
            n11 = 35;
            int n12 = n9;
            int n13 = n8;
            Graphics graphics4 = graphics3;
            graphics4.setColor(0);
            graphics4.fillRect(n13, n12, 35, 6);
            int n14 = 6;
            int n15 = n10 * 35 / 100;
            int n16 = n12;
            n10 = n13;
            n12 = 0xFFFA00;
            n13 = 16729601;
            if (n15 != 0) {
                int n17;
                int n18;
                int n19 = 0 / n15;
                if (n19 == 0) {
                    n19 = -1;
                }
                if ((n18 = 180 / n15) == 0) {
                    n18 = 1;
                }
                if ((n17 = -1 / n15) == 0) {
                    n17 = -1;
                }
                int n20 = 0;
                int n21 = 0;
                int n22 = 0;
                for (int i3 = 0; i3 < n15; ++i3) {
                    graphics4.setColor(n13);
                    graphics4.drawLine(n10 + i3, n16, n10 + i3, n16 + n14 - 1);
                    if (n13 == n12) continue;
                    n20 = 255 + i3 * n19;
                    if (n20 > 255) {
                        n20 = 255;
                    } else if (n20 < 0) {
                        n20 = 0;
                    }
                    n21 = 70 + i3 * n18;
                    if (n21 > 255) {
                        n21 = 255;
                    } else if (n21 < 0) {
                        n21 = 0;
                    }
                    n22 = 1 + i3 * n17;
                    if (n22 > 255) {
                        n22 = 255;
                    } else if (n22 < 0) {
                        n22 = 0;
                    }
                    n13 = n20 << 16 | n21 << 8 | n22;
                }
            }
            d.c.a(graphics3, 35, n8, n9 += 9, f2.q(), b.a.h);
            graphics3.drawImage(b.a.T, n7 + 6, n6 + 23, 20);
            n8 = n7 + 29;
            n9 = n6 + 23;
            e.a.b.a(graphics3, b.a.G, f2.c(2), n8, n9);
            d.c.a(graphics3, 35, n8 + 10, n9 += b.a.G.getHeight(), f2.c(2) * 100 / f2.c(3), b.a.e);
            e.a.b.a(graphics3, b.a.G, f2.c(4), n8, n9 += 2 + b.a.e.length);
            d.c.a(graphics3, 35, n8 + 10, n9 += b.a.G.getHeight(), f2.c(4) * 100 / f2.c(5), b.a.f);
            e.a.b.a(graphics3, b.a.G, f2.c(6), n8, n9 += 2 + b.a.f.length);
            d.c.a(graphics3, 35, n8 + 10, n9 += b.a.G.getHeight(), f2.c(6) * 100 / f2.c(7), b.a.g);
            n8 = n7 + 80 - 12;
            f2.a(graphics3, n8, n6 + 21 + 5, (short)20);
        }
        graphics2 = graphics;
        c2 = this;
        if (c2.V) {
            d.c.a(graphics2, (b.b[])c2.k, c2.L);
            d.c.a(graphics2, (b.b[])c2.w, c2.L);
        } else {
            d.c.a(graphics2, (b.b[])c2.w, c2.L);
            d.c.a(graphics2, (b.b[])c2.k, c2.L);
        }
        if (c2.L != null) {
            c2.L.a(graphics2, c2.L.j, c2.L.k);
        }
        if (c2.x != null) {
            for (n2 = 0; n2 < c2.x.length; ++n2) {
                if (c2.x[n2] == null) continue;
                c2.x[n2].a(graphics2, c2.x[n2].j, c2.x[n2].k);
            }
        }
        graphics2 = graphics;
        c2 = this;
        if (c2.ab != 1) {
            for (n2 = 0; n2 < c2.k.length; ++n2) {
                if (c2.k[n2] == null || c2.k[n2].w() || c2.k[n2].d == null) continue;
                c2.a(graphics2, (b.f)c2.k[n2], c2.k[n2].j + c2.k[n2].l / 2, c2.k[n2].k - c2.k[n2].m, false);
            }
            for (n2 = 0; n2 < c2.w.length; ++n2) {
                if (c2.w[n2] == null || c2.w[n2].w() || c2.w[n2].d == null) continue;
                c2.a(graphics2, c2.w[n2], c2.w[n2].j - c2.w[n2].l / 2 - c2.s.getWidth() / 2, c2.w[n2].k - c2.w[n2].m * 3 / 4, true);
            }
        }
        if (this.L != null) {
            e.a.a.a(graphics, 0, this.L.z, main.b.d, 0, 24);
        }
        this.b(graphics);
    }

    private static void a(Graphics graphics, b.b[] bArray, b.b b2) {
        if (bArray == null) {
            return;
        }
        for (int i2 = 0; i2 < bArray.length; ++i2) {
            if (bArray[i2] == null || bArray[i2] == b2) continue;
            bArray[i2].a(graphics, bArray[i2].j, bArray[i2].k);
        }
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int[] nArray) {
        if (nArray == null) {
            return;
        }
        graphics.setColor(3739672);
        graphics.fillRect(n3, n4, 35, nArray.length + 2);
        if (n5 <= 0) {
            return;
        }
        if (n5 > 100) {
            n5 = 100;
        }
        n2 = n5 * 33 / 100;
        for (n5 = 0; n5 < nArray.length; ++n5) {
            graphics.setColor(nArray[n5]);
            graphics.drawLine(n3 + 1, n4 + 1 + n5, n3 + n2, n4 + 1 + n5);
        }
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public final void a(k var1_2) {
        block37: {
            block50: {
                block35: {
                    block47: {
                        block41: {
                            block48: {
                                block49: {
                                    block45: {
                                        block46: {
                                            block42: {
                                                block44: {
                                                    block43: {
                                                        block40: {
                                                            block39: {
                                                                block38: {
                                                                    block36: {
                                                                        if (var1_2 != this.H) break block35;
                                                                        var1_2 = this.H.d();
                                                                        var2_7 = b.a.b((short)2);
                                                                        if (b.a.n() || var2_7 == null || this.L != var2_7 || var1_2.equals(b.a.p[60]) || var1_2.equals("\u6fc0\u6d3b")) break block36;
                                                                        var1_2 = c.b.a("\u7389\u513f\u672a\u88ab\u6fc0\u6d3b,\u76ee\u524d\u53ea\u80fd\u9632\u5fa1!", 3000L);
                                                                        var1_2.f();
                                                                        var3_11 = c.b.a(d.c.Z, this.e, this.f, true);
                                                                        var4_18 = var3_11.f();
                                                                        if (var4_18 == 1 && (var5_22 = var3_11.d()).equals("\u6fc0\u6d3b")) {
                                                                            this.m();
                                                                        }
                                                                        break block37;
                                                                    }
                                                                    if (!var1_2.equals("\u6fc0\u6d3b")) break block38;
                                                                    this.m();
                                                                    break block37;
                                                                }
                                                                if (!var1_2.equals("\u5546\u5e97")) break block39;
                                                                var6_29 = this;
                                                                v0 = new u[1];
                                                                var7_39 = v0;
                                                                v0[0] = new c.f(c.i.a);
                                                                var8_43 = e.a.c.a("/c.pak");
                                                                v1 = new Image[1];
                                                                var5_23 = v1;
                                                                v1[0] = var8_43.b("s0.png");
                                                                var6_29.ae = new t(var5_23, var7_39, main.b.d, main.b.e);
                                                                var6_29.ae.a(var6_29);
                                                                d.a.d(var6_29.ae);
                                                                break block37;
                                                            }
                                                            if (!var1_2.equals(b.a.p[16])) break block40;
                                                            var6_30 = this;
                                                            if (var6_30.T >= var6_30.k.length || var6_30.k[var6_30.T] == null || var6_30.k[var6_30.T].w()) {
                                                                var6_30.T = d.c.a(var6_30.T, var6_30.k, false);
                                                            }
                                                            var6_30.R = 1;
                                                            var6_30.S = 1;
                                                            var6_30.Q = 0;
                                                            var1_2 = var6_30;
                                                            d.a.d(var1_2.U);
                                                            break block41;
                                                        }
                                                        if (!var1_2.equals(b.a.p[17])) break block42;
                                                        if (!this.L.H()) break block43;
                                                        var1_2 = c.b.a(b.a.p[61], 3000L);
                                                        var1_2.f();
                                                        break block37;
                                                    }
                                                    if (this.L.q() == 100) break block44;
                                                    var1_2 = c.b.a(b.a.p[62], 2000L);
                                                    var1_2.f();
                                                    break block37;
                                                }
                                                this.x();
                                                break block41;
                                            }
                                            if (!var1_2.equals(b.a.p[18])) break block45;
                                            var6_31 = this;
                                            var7_40 = null;
                                            if (!var6_31.u()) break block46;
                                            var5_24 = var6_31.L;
                                            var8_44 = var6_31;
                                            if (var5_24 == null) ** GOTO lbl-1000
                                            var3_12 = 0;
                                            var0_1 = var5_24.c(0);
                                            var3_12 = var0_1 >= var8_44.z ? 100 - 100 * (25 / (var0_1 - var8_44.z + 50)) : 2500 / (50 - var0_1 + var8_44.z);
                                            if (e.a.b.c(100) < var3_12) {
                                                v2 = true;
                                            } else lbl-1000:
                                            // 2 sources

                                            {
                                                v2 = false;
                                            }
                                            if (v2) {
                                                var7_40 = c.b.a(b.a.p[27], 3000L);
                                                var6_31.W = (byte)2;
                                            } else {
                                                var7_40 = c.b.a(b.a.p[63], 3000L);
                                                var6_31.W = 1;
                                            }
                                            var7_40.f();
                                            var6_31.H.b();
                                            break block47;
                                        }
                                        var7_40 = c.b.a(b.a.p[64], 3000L);
                                        var7_40.f();
                                        break block37;
                                    }
                                    if (!var1_2.equals(b.a.p[7])) break block48;
                                    if (!this.L.H()) break block49;
                                    var1_2 = c.b.a(b.a.p[71], 3000L);
                                    var1_2.f();
                                    break block37;
                                }
                                var6_32 = this;
                                var6_32.w();
                                break block41;
                            }
                            if (var1_2.equals(b.a.p[60])) {
                                var6_33 = this;
                                var6_33.L.a(9, 0);
                                var6_33.W = 1;
                                var6_33.L.G = true;
                            } else if (var1_2.equals(b.a.p[8])) {
                                var6_34 = this;
                                var6_34.y();
                            } else if (var1_2.equals(b.a.h(77))) {
                                var6_35 = this;
                                if (var6_35.T >= var6_35.k.length || var6_35.k[var6_35.T] == null || var6_35.k[var6_35.T].w()) {
                                    var6_35.T = d.c.a(var6_35.T, var6_35.k, false);
                                }
                                var6_35.R = 1;
                                var6_35.S = 1;
                                var6_35.Q = (byte)4;
                                var1_2 = var6_35;
                                d.a.d(var1_2.U);
                            } else if (var1_2.equals(b.a.h(25))) {
                                this.n();
                            }
                        }
                        this.H.b();
                    }
                    return;
                }
                if (var1_2 != this.U) break block50;
                this.M = this.R == 1 ? this.k[this.T] : this.w[this.T];
                switch (this.Q) {
                    case 0: {
                        this.a(this.L, this.M);
                        break;
                    }
                    case 1: 
                    case 5: {
                        var4_19 = this.N;
                        var3_13 = this.M;
                        var1_2 = this.L;
                        var2_8 = this;
                        if (var1_2 == null || var4_19 == null || var4_19.v == 1 && var3_13 == null) break;
                        var5_25 /* !! */  = null;
                        var5_25 /* !! */  = main.c.a((b.b)var1_2, 16, -1, 8, null, false);
                        var2_8.a((b.b)var1_2, (main.c)var5_25 /* !! */ );
                        var6_36 = 0;
                        var7_41 = 0;
                        switch (var4_19.v) {
                            case 0: {
                                var6_36 = (short)(main.b.d / 3);
                                var7_41 = (short)((main.b.e - var2_8.q.getHeight() << 2) / 5);
                                if (var4_19.w != 0) break;
                                var6_36 = (short)(var6_36 + main.b.d / 3);
                                break;
                            }
                            case 1: {
                                var6_36 = (short)var3_13.j;
                                var7_41 = (short)var3_13.k;
                            }
                        }
                        var2_8.y = new q("r" + var4_19.a[22]);
                        var2_8.y.j = var6_36;
                        var2_8.y.k = var7_41;
                        var2_8.y.a();
                        var2_8.y.a = true;
                        var2_8.y.a(-1, -1);
                        var2_8.c(var2_8.y);
                        var5_25 /* !! */  = main.c.a(var2_8.y, 0, 0, -1, null, false);
                        var2_8.a((b.b)var1_2, (main.c)var5_25 /* !! */ );
                        var8_45 = var4_19.w == 1 ? var2_8.k : var2_8.w;
                        var5_25 /* !! */  = main.c.a(var4_19, (b.f)var1_2, var3_13, var8_45);
                        var2_8.a((b.b)var1_2, (main.c)var5_25 /* !! */ );
                        var5_25 /* !! */  = null;
                        switch (var4_19.v) {
                            case 0: {
                                var5_25 /* !! */  = new main.c[var8_45.length][];
                                for (var3_14 = 0; var3_14 < var8_45.length; ++var3_14) {
                                    var5_25 /* !! */ [var3_14] = var2_8.a(var4_19, (b.f)var1_2, var8_45[var3_14]);
                                }
                                break;
                            }
                            case 1: {
                                v3 = new main.c[1][];
                                var5_25 /* !! */  = v3;
                                v3[0] = var2_8.a(var4_19, (b.f)var1_2, var3_13);
                            }
                        }
                        var5_25 /* !! */  = main.c.a(var5_25 /* !! */ );
                        var2_8.a((b.b)var1_2, (main.c)var5_25 /* !! */ );
                        var5_25 /* !! */  = d.c.a((b.f)var1_2);
                        var2_8.a((b.b)var1_2, (main.c)var5_25 /* !! */ );
                        break;
                    }
                    case 2: {
                        this.a(this.L, this.M, this.k, this.O);
                        this.L.a((byte)14, 0);
                        break;
                    }
                    case 3: {
                        b.a.a(this.P, 1);
                        var5_26 = this.P;
                        var4_20 = this.R == 1 ? this.k : this.w;
                        var3_15 = this.M;
                        var1_2 = this.L;
                        var2_9 = this;
                        if (var1_2 == null || var5_26 == null || var5_26.v == 0 && var4_20 == null || var5_26.v == 1 && var3_15 == null) break;
                        var6_37 = null;
                        var6_37 = main.c.a((b.b)var1_2, 17, 0, 8, null, false);
                        var2_9.a((b.b)var1_2, var6_37);
                        var6_37 = d.c.a((b.f)var1_2);
                        var2_9.a((b.b)var1_2, var6_37);
                        var6_37 = main.c.a(var5_26, (b.f)var1_2, var3_15, var4_20);
                        var2_9.a((b.b)var1_2, var6_37);
                        var6_37 = var2_9.a(var3_15, var4_20, var5_26);
                        var2_9.a((b.b)var1_2, var6_37);
                        break;
                    }
                    case 4: {
                        var1_3 = false;
                        if (!this.u()) ** GOTO lbl-1000
                        var1_4 = this.M;
                        var2_10 = this.L;
                        if (var2_10 == null || var1_4 == null) {
                            v4 = false;
                        } else {
                            var3_16 = var2_10.c(0) - var1_4.c(0);
                            var4_21 = var1_4.I();
                            v4 = var3_16 < 0 || var4_21 >= 50 ? false : (var5_27 = ((50 + var3_16 * 2 / 100) * (50 - var4_21) << 1) / 100) >= (var6_38 = e.a.b.c(100));
                        }
                        if (v4) {
                            var1_5 = 1;
                            b.a.a((int)(-this.M.f), 1);
                        } else lbl-1000:
                        // 2 sources

                        {
                            var1_5 = 0;
                        }
                        var4_21 = var1_5;
                        var3_17 = this.M;
                        var1_6 = this.L;
                        var2_10 = this;
                        if (var1_6 == null || var3_17 == null) break;
                        var5_28 = null;
                        var6_38 = 0;
                        var7_42 = var3_17.k - var3_17.m / 2;
                        var5_28 = main.c.a(var1_6, 17, 1, 8, null, false);
                        super.a((b.b)var1_6, var5_28);
                        var5_28 = d.c.a(var1_6);
                        super.a((b.b)var1_6, var5_28);
                        if (var4_21 != 0) {
                            var8_46 = new main.c[2][1];
                            var8_46[0][0] = main.c.a(var3_17, 3, 0, -1, null, false);
                            var8_46[1][0] = main.c.a(var1_6.j, var1_6.k, var3_17, 2, 1);
                            var5_28 = main.c.a(var8_46);
                            super.a((b.b)var1_6, var5_28);
                            var5_28 = main.c.a(b.a.h(77) + b.a.h(78), (byte)0);
                            super.a((b.b)var1_6, var5_28);
                            var5_28 = main.c.a(var3_17, (byte)2, (short)0, (byte)1);
                            super.a((b.b)var1_6, var5_28);
                            break;
                        }
                        var6_38 = var3_17.j - var2_10.t.getWidth() / 2;
                        var8_47 = b.d.a(var2_10.t, var6_38, var7_42);
                        var2_10.a(var8_47);
                        var5_28 = main.c.a(var6_38, var7_42 - var3_17.m / 2, var8_47, 2, 1);
                        super.a((b.b)var1_6, var5_28);
                        var5_28 = main.c.a(b.a.h(77) + b.a.h(79), (byte)0);
                        super.a((b.b)var1_6, var5_28);
                    }
                }
                this.W = 1;
                this.U.b();
                return;
            }
            var1_2.b();
        }
    }

    public final void c(k k2) {
        d.a.e(k2);
    }

    private void m() {
        String string;
        String string2 = "\u662f\u5426\u6fc0\u6d3b\u7389\u513f?\u8d60\u90012\u4e2a\u5143\u5b9d(\u7528\u4e8e\u6362\u9ad8\u7ea7\u88c5\u5907),\u8bf7\u8bb0\u5f97\u5b58\u6863!";
        Object var2_2 = null;
        boolean bl = false;
        bl = b.a.n() ? true : g.a.a.a(MainMIDlet.a, (Displayable)main.b.a, string2, "08", "08", 200);
        if (bl) {
            b.a.a(1001, 2);
            b.a.a(true);
            string = "\u6fc0\u6d3b\u6210\u529f,\u7389\u513f\u73b0\u5728\u53ef\u4ee5\u4f7f\u7528\u4e86!";
            d.a.e(((c)((Object)b2)).H);
            super.v();
            b.a.o();
        } else {
            string = "\u6fc0\u6d3b\u5931\u8d25!";
        }
        c.b b2 = c.b.a(string, 3000L);
        b2.f();
    }

    private void n() {
        Image[] imageArray = new Image[1];
        Image[] imageArray2 = imageArray;
        imageArray[0] = b.a.S[3];
        u[] uArray = new u[1];
        u[] uArray2 = uArray;
        uArray[0] = new b(this, this.L);
        this.I = new t(imageArray2, uArray2, (main.b.d << 2) / 5, main.b.e * 3 / 4);
        this.I.a(this);
        d.a.d(this.I);
    }

    private main.c a(b.f f2, b.f[] fArray, f.b b2) {
        if (b2 == null || b2.v == 0 && fArray == null || b2.v == 1 && f2 == null) {
            return null;
        }
        main.c[][] cArray = null;
        switch (b2.v) {
            case 1: {
                main.c[][] cArray2 = new main.c[1][];
                cArray = cArray2;
                cArray2[0] = this.a(f2, b2);
                break;
            }
            case 0: {
                cArray = new main.c[fArray.length][];
                for (int i2 = 0; i2 < fArray.length; ++i2) {
                    if (fArray[i2] == null || fArray[i2].w() && !b2.a()) continue;
                    cArray[i2] = this.a(fArray[i2], b2);
                }
                break;
            }
            default: {
                return null;
            }
        }
        return main.c.a(cArray);
    }

    private main.c[] a(b.f f2, f.b b2) {
        int n2;
        if (b2 == null || f2 == null) {
            return null;
        }
        Vector<b.b[]> vector = new Vector<b.b[]>();
        Object object = null;
        if (b2.w == 1) {
            object = main.c.a(f2, 2, 0, 0, null, false);
        } else {
            short s2 = (short)f2.j;
            n2 = f2.k;
            object = main.c.a(576, s2, (short)n2, 0);
        }
        vector.addElement((b.b[])object);
        object = b2.a(null, f2);
        if (object != null) {
            for (n2 = 0; n2 < ((b.b[])object).length; ++n2) {
                this.a(object[n2]);
            }
            main.c[][] cArray = new main.c[((b.b[])object).length][1];
            for (int i2 = 0; i2 < cArray.length; ++i2) {
                cArray[i2][0] = main.c.a(object[i2].j, object[i2].k - f2.m / 2, object[i2], 2, 1);
            }
            object = main.c.a(cArray);
            vector.addElement((b.b[])object);
        }
        object = d.c.a(f2);
        vector.addElement((b.b[])object);
        Object[] objectArray = new main.c[vector.size()];
        vector.copyInto(objectArray);
        return objectArray;
    }

    private void a(b.f f2, b.f cArray, boolean n2) {
        if (f2 == null || cArray == null) {
            return;
        }
        int n3 = f2.j;
        int n4 = f2.k;
        boolean bl = false;
        int n5 = 0;
        this.aa = e.a.b.c(100) > 95 - (f2.F() ? 75 : 0);
        Object var6_8 = null;
        short s2 = 0;
        if (!b.a.n[1]) {
            s2 = n2 != 0 ? (short)(cArray.j + cArray.l / 2 + f2.l / 2) : (short)(cArray.j - cArray.l / 2 - f2.l / 2);
            main.c c2 = main.c.a((int)s2, cArray.k, f2, 999, 2);
            this.a((b.b)f2, c2);
        }
        if (this.aa) {
            if (n2 != 0) {
                main.c c3 = main.c.a(cArray.j - cArray.l / 2, cArray.k, (b.b)cArray, 4, 2);
                this.a((b.b)f2, c3);
            } else {
                main.c c4 = main.c.a(cArray.j + cArray.l / 2, cArray.k, (b.b)cArray, 4, 2);
                this.a((b.b)f2, c4);
            }
        }
        main.c c5 = main.c.a(f2, 1, 0, 0, null, false);
        this.a((b.b)f2, c5);
        n5 = cArray.k - cArray.m / 2;
        if (!this.aa) {
            n2 = f2.a((b.f)cArray, false);
            c5 = main.c.a((b.b)cArray, (byte)2, (short)(-n2), (byte)0);
            this.a((b.b)f2, c5);
            int n6 = n2 * 100 / this.M.c(3);
            main.c c6 = main.c.a((b.b)cArray, (byte)14, (short)(n6 <= 0 ? 1 : n6), (byte)0);
            this.a((b.b)f2, c6);
            c6 = b.a.W;
            int n7 = cArray.j - String.valueOf(n2).length() * c6.getWidth() / 10 / 2;
            b.b[] bArray = new b.b[1];
            b.b[] bArray2 = bArray;
            bArray[0] = b.d.a(n2, n7, n5, b.a.W);
            this.a(bArray2[0]);
            cArray = this.a(bArray2, (b.f)cArray);
            if (cArray != null) {
                for (n2 = 0; n2 < cArray.length; ++n2) {
                    this.a((b.b)f2, cArray[n2]);
                }
            }
        } else {
            int n8 = cArray.j - this.t.getWidth() / 2;
            b.d d2 = b.d.a(this.t, n8, n5);
            this.a(d2);
            main.c c7 = main.c.a(n8, n5 - cArray.m / 2, d2, 2, 1);
            this.a((b.b)f2, c7);
            c7 = main.c.a(cArray.j, cArray.k, (b.b)cArray, 4, 2);
            this.a((b.b)f2, c7);
        }
        if (!b.a.n[1]) {
            main.c c8 = main.c.a((int)((short)n3), (short)n4, f2, 999, 2);
            this.a((b.b)f2, c8);
        }
        main.c c9 = main.c.a(f2, 0, -1, -1, null, false);
        this.a((b.b)f2, c9);
    }

    private void a(b.f f2, b.f f3, b.f[] fArray, f.c c2) {
        short s2;
        if (f2 == null || c2 == null || c2.v == 0 && fArray == null || c2.v == 1 && f3 == null) {
            return;
        }
        int n2 = f2.j;
        int n3 = f2.k;
        Object object = null;
        if (!b.a.n[1]) {
            object = main.c.a(f2, 13, -1, -1, null, false);
            this.a((b.b)f2, (main.c)object);
            short s3 = 0;
            s2 = 0;
            switch (c2.v) {
                case 0: {
                    s3 = (short)main.b.f;
                    s2 = (short)main.b.g;
                    break;
                }
                case 1: {
                    s3 = (short)(this.M.j + this.M.l / 2 + f2.l / 2);
                    s2 = (short)this.M.k;
                }
            }
            object = main.c.a((int)s3, s2, f2, 8, 2);
            this.a((b.b)f2, (main.c)object);
        }
        object = main.c.a(f2, c2.a[22], 0, 8, null, false);
        this.a((b.b)f2, (main.c)object);
        object = main.c.a(c2, f2, f3, fArray);
        this.a((b.b)f2, (main.c)object);
        object = null;
        switch (c2.v) {
            case 0: {
                object = new main.c[fArray.length][];
                for (s2 = 0; s2 < fArray.length; s2 = (short)(s2 + 1)) {
                    object[s2] = this.a(c2, f2, fArray[s2]);
                }
                break;
            }
            case 1: {
                main.c[][] cArrayArray = new main.c[1][];
                object = cArrayArray;
                cArrayArray[0] = this.a(c2, f2, f3);
            }
        }
        object = main.c.a(object);
        this.a((b.b)f2, (main.c)object);
        if (!b.a.n[1]) {
            object = main.c.a(f2, 14, -1, -1, null, false);
            this.a((b.b)f2, (main.c)object);
            object = main.c.a((int)((short)n2), (short)n3, f2, 8, 2);
            this.a((b.b)f2, (main.c)object);
        }
        object = d.c.a(f2);
        this.a((b.b)f2, (main.c)object);
    }

    private main.c[] a(b.b[] bArray, b.f f2) {
        if (f2 == null || bArray == null) {
            return null;
        }
        Vector vector = new Vector();
        Object object = null;
        object = new main.c[bArray.length][1];
        for (int i2 = 0; i2 < bArray.length; ++i2) {
            object[i2][0] = main.c.a(bArray[i2].j, bArray[i2].k - 20, bArray[i2], 2, 1);
        }
        object = main.c.a(object);
        vector.addElement(object);
        object = d.c.a(f2);
        vector.addElement(object);
        Object[] objectArray = new main.c[vector.size()];
        vector.copyInto(objectArray);
        return objectArray;
    }

    private static main.c a(b.f object) {
        if (object == null) {
            return null;
        }
        boolean bl = ((b.b)object).h == 8;
        main.c[] cArray = new main.c[3];
        main.c[] cArray2 = cArray;
        cArray[0] = main.c.a((b.f)object, (byte)2, (byte)2, 1, null);
        cArray2[0].h = new main.c[2];
        cArray2[0].h[0] = bl ? main.c.a((b.b)object, 3, 0, -1, null, false) : main.c.a((b.b)object, 11, -1, -1, null, false);
        cArray2[0].h[1] = main.c.a(cArray2[0]);
        cArray2[1] = main.c.a((b.f)object, (byte)2, (byte)2, (int)((short)(((b.f)object).c(3) / 5)), null);
        cArray2[1].h = new main.c[2];
        cArray2[1].h[0] = bl ? main.c.a((b.b)object, 0, -1, -1, null, false) : main.c.a((b.b)object, 10, -1, -1, null, false);
        cArray2[1].h[1] = main.c.a(cArray2[1]);
        cArray2[2] = main.c.a((b.f)object, (byte)2, (byte)0, (int)((short)(((b.f)object).c(3) / 5 - 1)), null);
        cArray2[2].h = new main.c[2];
        cArray2[2].h[0] = bl ? main.c.a((b.b)object, 0, -1, -1, null, false) : main.c.a((b.b)object, 8, -1, -1, null, false);
        cArray2[2].h[1] = main.c.a(cArray2[2]);
        object = main.c.a(cArray2);
        return object;
    }

    private void a(b.f f2, b.f f3) {
        if (f2 == null || f3 == null) {
            return;
        }
        int n2 = f2.j;
        int n3 = f2.k;
        this.aa = e.a.b.c(100) > 95 - (f2.F() ? 75 : 0);
        int n4 = e.a.b.c(100) > 95 ? 1 : 0;
        Object object = null;
        if (n4 != 0 && !this.aa) {
            object = b.d.a(this.u, f2.j - f2.l / 2, f2.k - f2.m / 2);
            this.a((b.b)object);
            object = main.c.a(((b.b)object).j, ((b.b)object).k - f2.m, (b.b)object, 2, 1);
            this.a((b.b)f2, (main.c)object);
        }
        if (!b.a.n[1]) {
            object = main.c.a(f2, 13, -1, -1, null, false);
            this.a((b.b)f2, (main.c)object);
            short s2 = (short)(f3.j + f3.l / 2 + f2.l / 2);
            object = main.c.a((int)s2, f3.k, f2, 8, 2);
            this.a((b.b)f2, (main.c)object);
        }
        if (this.aa) {
            object = main.c.a(f3.j - f3.l / 2, f3.k, f3, 4, 2);
            this.a((b.b)f2, (main.c)object);
        }
        if (this.aa || n4 == 0) {
            object = main.c.a(f2, 15, 0, 8, null, false);
            this.a((b.b)f2, (main.c)object);
        } else {
            object = main.c.a(f2, 21, 0, 8, null, false);
            this.a((b.b)f2, (main.c)object);
        }
        int n5 = 0;
        int n6 = f3.k - f3.m / 2;
        if (this.aa) {
            n5 = f3.j - this.t.getWidth() / 2;
            b.d d2 = b.d.a(this.t, n5, n6);
            this.a(d2);
            main.c c2 = main.c.a(n5, n6 - 20, d2, 2, 1);
            this.a((b.b)f2, c2);
            c2 = main.c.a(f3.j, f3.k, f3, 4, 2);
            this.a((b.b)f2, c2);
        } else {
            n4 = f2.a(f3, n4 != 0);
            main.c c3 = main.c.a(f3, (byte)2, (short)(-n4), (byte)0);
            this.a((b.b)f2, c3);
            b.b[] bArray = new b.b[1];
            Image image = b.a.W;
            int n7 = f3.j - String.valueOf(n4).length() * image.getWidth() / 20;
            bArray[0] = b.d.a(n4, n7, n6, image);
            for (n4 = 0; n4 < bArray.length; ++n4) {
                this.a(bArray[n4]);
            }
            main.c[] cArray = this.a(bArray, f3);
            if (cArray != null) {
                for (int i2 = 0; i2 < cArray.length; ++i2) {
                    this.a((b.b)f2, cArray[i2]);
                }
            }
        }
        if (!b.a.n[1]) {
            main.c c4 = main.c.a(f2, 14, -1, -1, null, false);
            this.a((b.b)f2, c4);
            c4 = main.c.a((int)((short)n2), (short)n3, f2, 8, 2);
            this.a((b.b)f2, c4);
        }
        main.c c5 = d.c.a(f2);
        this.a((b.b)f2, c5);
    }

    private void a(b.c c2, b.f f2, b.f[] fArray, f.c c3, boolean bl) {
        if (c2 == null || c3 == null || c3.v == 1 && f2 == null || c3.v == 0 && fArray == null) {
            return;
        }
        main.c c4 = null;
        c4 = main.c.a(c2, 1, 0, 0, null, false);
        this.a((b.b)c2, c4);
        this.y = new q("r" + c3.a[22]);
        this.y.a();
        this.y.a(-1, -1);
        this.y.a = true;
        this.c(this.y);
        short s2 = 0;
        short s3 = 0;
        boolean bl2 = false;
        switch (c3.v) {
            case 0: {
                s3 = (short)(main.b.e / 2);
                if (bl) {
                    s2 = (short)(main.b.d / 3);
                    break;
                }
                bl2 = true;
                s2 = (short)((main.b.d << 1) / 3);
                break;
            }
            case 1: {
                s2 = (short)f2.j;
                s3 = (short)f2.k;
            }
        }
        this.y.j = s2;
        this.y.k = s3;
        main.c c5 = main.c.a(this.y, 0, 0, -1, null, bl2);
        this.a((b.b)c2, c5);
        c5 = main.c.a(c3, c2, f2, fArray);
        this.a((b.b)c2, c5);
        main.c[][] cArrayArray = null;
        switch (c3.v) {
            case 0: {
                cArrayArray = new main.c[fArray.length][];
                for (int i2 = 0; i2 < fArray.length; ++i2) {
                    cArrayArray[i2] = this.a(c3, (b.f)c2, fArray[i2]);
                }
                break;
            }
            case 1: {
                main.c[][] cArrayArray2 = new main.c[1][];
                cArrayArray = cArrayArray2;
                cArrayArray2[0] = this.a(c3, (b.f)c2, f2);
            }
        }
        c5 = main.c.a(cArrayArray);
        this.a((b.b)c2, c5);
    }

    private main.c[] a(f.b bArray, b.f f2, b.f f3) {
        if (bArray == null || f3 == null || f3.w()) {
            return null;
        }
        if ((bArray = bArray.a(f2, f3)) == null) {
            return null;
        }
        for (int i2 = 0; i2 < bArray.length; ++i2) {
            if (bArray[i2] == null) continue;
            this.a(bArray[i2]);
        }
        return this.a(bArray, f3);
    }

    private void a(b.b b2, main.c c2) {
        Vector vector = (Vector)((c)((Object)vector)).G.get(b2);
        vector.addElement(c2);
    }

    public final void a(int n2, int n3) {
        d.a.c();
        Object object = this;
        object = ((c)object).G.elements();
        Vector vector = null;
        main.c c2 = null;
        boolean bl = false;
        while (object.hasMoreElements()) {
            vector = (Vector)object.nextElement();
            if (vector.isEmpty()) continue;
            bl = true;
            c2 = (main.c)vector.elementAt(0);
            c2.a();
            if (!c2.b()) continue;
            vector.removeElement(c2);
        }
        byte by = 0;
        if (!d.a.f()) {
            k k2 = d.a.h();
            k2.a(n2, n3);
            by = this.W;
            this.W = (byte)5;
        } else {
            by = this.a(this.ab, (byte)1, n2, n3);
        }
        if (by != 5 && by != this.ab) {
            this.a(this.ab, (byte)2, n2, n3);
            this.ab = by;
            this.a(this.ab, (byte)0, n2, n3);
        }
    }

    private static int a(int n2, b.f[] fArray, boolean bl) {
        if (fArray == null) {
            return -1;
        }
        for (int i2 = 0; i2 < fArray.length; ++i2) {
            if (fArray[n2 = (n2 + 1) % fArray.length] == null || fArray[n2].w() && !bl) continue;
            return n2;
        }
        return -1;
    }

    private void a(e.b.b object, b.f f2) {
        boolean bl;
        if (object == null || f2 == null || f2.w()) {
            return;
        }
        e.b.b b2 = object;
        object = f2;
        Object object2 = b2;
        if (!(b2 == null || object == null ? false : (bl = (object2 = ((e.b.b)object2).c()) != null && ((e.b.d)object2).a()))) {
            return;
        }
        if (f2.h == 8) {
            f2.a(2, 0);
            return;
        }
        f2.a(12, 0);
    }

    private boolean o() {
        if (((c)((Object)enumeration)).G.isEmpty()) {
            return true;
        }
        Enumeration enumeration = ((c)((Object)enumeration)).G.elements();
        Vector vector = null;
        while (enumeration.hasMoreElements()) {
            vector = (Vector)enumeration.nextElement();
            if (vector.isEmpty()) continue;
            return false;
        }
        return true;
    }

    private boolean p() {
        for (int i2 = 0; i2 < this.k.length; ++i2) {
            if (this.k[i2] == null || this.k[i2].w()) continue;
            return false;
        }
        return true;
    }

    private boolean q() {
        for (int i2 = 0; i2 < this.w.length; ++i2) {
            if (this.w[i2] == null || this.w[i2].w()) continue;
            return false;
        }
        return true;
    }

    private void a(b.f f2, int n2) {
        int n3 = f2.j;
        int n4 = f2.k - f2.m / 2;
        Image image = b.a.W;
        int n5 = image.getWidth() / 10;
        n2 = Math.max(1, n2);
        b.b[] bArray = new b.b[1];
        b.b[] bArray2 = bArray;
        bArray[0] = b.d.a(n2, n3 - String.valueOf(n2).length() * n5 / 2, n4, image);
        for (n3 = 0; n3 < bArray2.length; ++n3) {
            if (bArray2[n3] == null) continue;
            this.a(bArray2[n3]);
        }
        f2.b((byte)2, -n2);
        main.c[] cArray = this.a(bArray2, f2);
        if (cArray != null) {
            for (n2 = 0; n2 < cArray.length; ++n2) {
                this.a((b.b)f2, cArray[n2]);
            }
        }
    }

    private byte r() {
        if (this.p()) {
            return 2;
        }
        if (this.q()) {
            return 2;
        }
        return 3;
    }

    private static String a(b.f f2, f.c[] cArray) {
        if (f2 == null || cArray == null || cArray.length <= 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(f2.z);
        stringBuffer.append(b.a.p[65]);
        for (int i2 = 0; i2 < cArray.length; ++i2) {
            stringBuffer.append(cArray[i2].t);
            if (i2 >= cArray.length - 1) continue;
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    private static String a(int n2, int n3, f.b[] bArray) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(b.a.p[34]);
        stringBuffer.append(b.a.p[42]);
        stringBuffer.append(':');
        stringBuffer.append("\n");
        stringBuffer.append(b.a.p[39]);
        stringBuffer.append(b.a.p[35]);
        stringBuffer.append('+');
        stringBuffer.append(n2);
        stringBuffer.append("\n");
        stringBuffer.append("\u6cd5\u5b9d\u7ecf\u9a8c\u503c");
        stringBuffer.append('+');
        stringBuffer.append(n2);
        stringBuffer.append("\n");
        stringBuffer.append(b.a.p[32]);
        stringBuffer.append('+');
        stringBuffer.append(n3);
        if (bArray != null) {
            for (n2 = 0; n2 < bArray.length; ++n2) {
                if (bArray[n2] == null) continue;
                stringBuffer.append("\n");
                stringBuffer.append(bArray[n2].t);
                stringBuffer.append('+');
                stringBuffer.append("1");
                stringBuffer.append(b.a.p[40]);
            }
        }
        return stringBuffer.toString();
    }

    private int s() {
        int n2 = 0;
        for (int i2 = 0; i2 < this.k.length; ++i2) {
            if (this.k[i2] == null) continue;
            ++n2;
        }
        return n2;
    }

    private void t() {
        this.w = new b.f[5];
        System.arraycopy(b.a.B, 0, this.w, 0, b.a.B.length);
        for (int i2 = 0; i2 < this.w.length; ++i2) {
            if (this.w[i2] == null) continue;
            this.w[i2].c = e.b.c.b("r" + this.w[i2].f + "_1");
            this.G.put(this.w[i2], new Vector());
            if (this.w[i2].c(2) < this.w[i2].c(3) * 20 / 100) {
                this.w[i2].a(10, -1);
            } else {
                this.w[i2].a(8, -1);
            }
            this.w[i2].j = m[i2][0];
            this.w[i2].k = m[i2][1];
            this.w[i2].t();
        }
    }

    private boolean u() {
        if (this.F != null) {
            return (this.F.g & 1) != 0;
        }
        return this.Y.j();
    }

    public final void b(k k2) {
        if (k2 == this.U) {
            switch (this.Q) {
                case 1: {
                    this.w();
                    break;
                }
                case 2: {
                    this.x();
                    break;
                }
                case 0: 
                case 4: {
                    this.v();
                    break;
                }
                case 3: {
                    this.y();
                    break;
                }
                case 5: {
                    this.n();
                }
            }
            k2.b();
        }
        if (k2 == this.ae) {
            k2.b();
        }
    }

    private void v() {
        String[] stringArray;
        if (this.L == null) {
            throw new NullPointerException();
        }
        b.f f2 = this.L;
        if (f2 == null) {
            stringArray = null;
        } else {
            f.c[] cArray;
            int n2 = 6;
            if (f2.f == 0) {
                ++n2;
            }
            if ((cArray = f2.J()) != null && cArray.length > 0) {
                ++n2;
            }
            if (f2.f == 3 && !b.a.n()) {
                ++n2;
            }
            int n3 = 0;
            String[] stringArray2 = new String[++n2];
            String[] stringArray3 = stringArray2;
            ++n3;
            stringArray2[0] = b.a.p[16];
            ++n3;
            stringArray3[1] = b.a.p[7];
            ++n3;
            stringArray3[2] = b.a.p[8];
            ++n3;
            stringArray3[3] = b.a.p[17];
            ++n3;
            stringArray3[4] = b.a.p[60];
            if (f2.f == 0) {
                ++n3;
                stringArray3[5] = b.a.h(77);
            }
            if (cArray != null && cArray.length > 0) {
                stringArray3[n3++] = b.a.h(25);
            }
            stringArray3[n3++] = b.a.p[18];
            if (f2.f == 3 && !b.a.n()) {
                stringArray3[n3++] = "\u6fc0\u6d3b";
            }
            stringArray3[n3] = "\u5546\u5e97";
            stringArray = stringArray3;
        }
        f2 = stringArray;
        this.H = c.b.a((String[])f2, (byte)0, (byte)4, false, false);
        d.a.d(this.H);
        this.H.a(this);
    }

    private void w() {
        Image[] imageArray = new Image[1];
        Image[] imageArray2 = imageArray;
        imageArray[0] = b.a.R[this.L.f];
        u[] uArray = new f[1];
        u[] uArray2 = uArray;
        uArray[0] = new f(this, this.L);
        this.J = new t(imageArray2, uArray2, (main.b.d << 2) / 5, main.b.e * 3 / 4);
        this.J.a(this);
        d.a.d(this.J);
    }

    private void x() {
        Image[] imageArray = new Image[1];
        Image[] imageArray2 = imageArray;
        imageArray[0] = b.a.R[this.L.f];
        u[] uArray = new d[1];
        u[] uArray2 = uArray;
        uArray[0] = new d(this, this.L);
        this.K = new t(imageArray2, uArray2, (main.b.d << 2) / 5, main.b.e * 3 / 4);
        this.K.a(this);
        d.a.d(this.K);
    }

    private void a(Graphics graphics, b.f f2, int n2, int n3, boolean bl) {
        if (f2 == null || f2.w() || f2.D() == 0 || g == null) {
            return;
        }
        int n4 = g.getWidth() / 13;
        int n5 = g.getHeight();
        short s2 = f2.D();
        for (int i2 = 0; i2 < 15; ++i2) {
            if ((s2 & 1 << i2) == 0) continue;
            e.a.b.a(graphics, this.s, 0, 0, this.s.getWidth(), this.s.getHeight(), bl ? 2 : 0, n2, n3, 36);
            e.a.b.a(graphics, g, (i2 - 1) * n4, 0, n4, n5, n2 + this.s.getWidth() / 2, n3 - 3, 33);
            return;
        }
    }

    private void y() {
        Image[] imageArray = new Image[l.length];
        for (int i2 = 0; i2 < imageArray.length; ++i2) {
            imageArray[i2] = b.a.S[l[i2]];
        }
        u[] uArray = new l[l.length];
        for (int i3 = 0; i3 < uArray.length; ++i3) {
            uArray[i3] = new l(this, l[i3]);
        }
        this.I = new t(imageArray, uArray, (main.b.d << 2) / 5, main.b.e * 3 / 4);
        this.I.a(this);
        d.a.d(this.I);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private byte a(byte var1_1, byte var2_9, int var3_16, int var4_23) {
        switch (var1_1) {
            case 3: {
                var1_1 = var2_9;
                block7 : switch (var1_1) {
                    case 0: {
                        ** GOTO lbl33
                    }
                    case 1: {
                        var1_2 = this;
                        while (var1_2.ac < var1_2.w.length) {
                            if (var1_2.w[var1_2.ac] != null && !var1_2.w[var1_2.ac].w()) {
                                var1_2.w[var1_2.ac].u();
                                if (var1_2.w[var1_2.ac].v()) {
                                    var1_2.L = var1_2.w[var1_2.ac];
                                    var1_2.V = true;
                                    ++var1_2.ac;
                                    v0 = 4;
                                    break block7;
                                }
                            }
                            ++var1_2.ac;
                        }
                        var1_2.ac = 0;
                        while (var1_2.ad < var1_2.k.length) {
                            if (var1_2.k[var1_2.ad] != null && !var1_2.k[var1_2.ad].w()) {
                                var1_2.k[var1_2.ad].u();
                                if (var1_2.k[var1_2.ad].v()) {
                                    var1_2.L = var1_2.k[var1_2.ad];
                                    ++var1_2.ad;
                                    v0 = 4;
                                    break block7;
                                }
                            }
                            ++var1_2.ad;
                        }
                        var1_2.ad = 0;
                        return 5;
                    }
lbl33:
                    // 2 sources

                    default: {
                        v0 = 5;
                    }
                }
                return (byte)v0;
            }
            case 4: {
                var1_1 = var2_9;
                switch (var1_1) {
                    case 0: {
                        this.L.G = false;
                        var2_10 = this.L;
                        var1_3 = this;
                        if (var2_10 != null && var2_10.D() != 0) {
                            var3_16 = 0;
                            v1 = var2_10.D();
                            var4_23 = v1;
                            switch (v1) {
                                case 1: 
                                case 4: 
                                case 16: 
                                case 32: 
                                case 64: 
                                case 128: 
                                case 256: 
                                case 512: 
                                case 2048: {
                                    break;
                                }
                                case 8192: {
                                    var3_16 = var2_10.c(2) * (30 + e.a.b.c(20)) / 100;
                                    super.a(var2_10, var3_16);
                                    break;
                                }
                                case 1024: {
                                    switch (var2_10.C()) {
                                        case 0: {
                                            var3_16 = 55 + e.a.b.c(45);
                                            break;
                                        }
                                        case 1: {
                                            var3_16 = 100 + e.a.b.c(990);
                                            break;
                                        }
                                        case 2: {
                                            var3_16 = 100 + e.a.b.c(8999);
                                        }
                                    }
                                    super.a(var2_10, var3_16);
                                    break;
                                }
                                case 8: {
                                    var3_16 = var2_10.c(2) * e.a.b.c(30) / 100;
                                    super.a(var2_10, var3_16);
                                    break;
                                }
                                case 2: {
                                    var3_16 = var2_10.c(2) * e.a.b.c(10) / 100;
                                    super.a(var2_10, var3_16);
                                    break;
                                }
                                case 4096: {
                                    var3_16 = var2_10.c(2) * e.a.b.c(10) / 100;
                                    super.a(var2_10, var3_16);
                                }
                            }
                        }
                        var1_3 = this.L;
                        if (var1_3 == null) break;
                        if (var1_3.C > 0) {
                            var1_3.b((byte)2, var1_3.c(3) * var1_3.C / 100);
                        }
                        if (var1_3.D > 0) {
                            var1_3.b((byte)4, var1_3.c(5) * var1_3.D / 100);
                        }
                        if (var1_3.E <= 0) break;
                        var1_3.b((byte)6, var1_3.c(7) * var1_3.E / 100);
                        break;
                    }
                    case 1: {
                        if (!this.o()) break;
                        if (this.L.w()) {
                            return this.r();
                        }
                        if (this.L.G()) {
                            return 1;
                        }
                        if ((this.L.D() & 32) != 0) {
                            var2_11 = this.L;
                            var1_4 = this;
                            if (var2_11 != null) {
                                if (var2_11 == var1_4.w[3] || var2_11 == var1_4.w[4]) {
                                    var1_4.V = true;
                                }
                                v2 = var3_16 = e.a.b.c(100) < 50 ? 1 : 0;
                                if (var2_11.h == 6) {
                                    var1_4.M = var2_11.a(var3_16 != 0 ? var1_4.k : var1_4.w);
                                    if (var1_4.M == null) {
                                        var1_4.M = var2_11.a(var3_16 != 0 ? var1_4.w : var1_4.k);
                                        var3_16 = var3_16 == 0 ? 1 : 0;
                                    }
                                } else {
                                    var1_4.M = var2_11.a(var3_16 != 0 ? var1_4.w : var1_4.k);
                                    if (var1_4.M == null) {
                                        var1_4.M = var2_11.a(var3_16 != 0 ? var1_4.k : var1_4.w);
                                        v3 = var3_16 = var3_16 == 0 ? 1 : 0;
                                    }
                                }
                                if (var3_16 != 0) {
                                    if (var2_11.h == 8) {
                                        var1_4.a(var2_11, var1_4.M, var1_4.V);
                                    } else {
                                        var1_4.a(var2_11, var1_4.M);
                                    }
                                } else {
                                    v4 = var2_11;
                                    var2_11 = var1_4.M;
                                    var5_30 = v4;
                                    var4_24 = var1_4;
                                    if (var5_30 != null && var2_11 != null) {
                                        var3_16 = 0;
                                        var6_38 = 0;
                                        var7_41 = null;
                                        var3_16 = var5_30.h == 6 ? 15 : 1;
                                        var6_38 = var5_30.h == 6 ? 8 : 0;
                                        var7_41 = main.c.a(var5_30, var3_16, 0, var6_38, null, false);
                                        var4_24.a((b.b)var5_30, var7_41);
                                        var8_46 = 0;
                                        var9_50 = var2_11.k - var2_11.m / 2;
                                        var6_38 = var5_30.a(var2_11, false);
                                        var7_41 = main.c.a(var2_11, (byte)2, (short)(-var6_38), (byte)0);
                                        var4_24.a((b.b)var5_30, var7_41);
                                        var7_42 = var6_38 * 100 / var4_24.M.c(3);
                                        var7_43 = main.c.a(var2_11, (byte)14, (short)(var7_42 <= 0 ? 1 : var7_42), (byte)0);
                                        var4_24.a((b.b)var5_30, var7_43);
                                        var10_55 = new b.b[1];
                                        var7_43 = b.a.W;
                                        var8_46 = var2_11.j - String.valueOf(var6_38).length() * var7_43.getWidth() / 20;
                                        var10_55[0] = b.d.a(var6_38, var8_46, var9_50, (Image)var7_43);
                                        for (var8_46 = 0; var8_46 < var10_55.length; ++var8_46) {
                                            var4_24.a(var10_55[var8_46]);
                                        }
                                        var8_47 = var4_24.a(var10_55, var2_11);
                                        if (var8_47 != null) {
                                            for (var2_12 = 0; var2_12 < var8_47.length; ++var2_12) {
                                                var4_24.a((b.b)var5_30, var8_47[var2_12]);
                                            }
                                        }
                                    }
                                }
                                var1_4.Q = 0;
                                var1_4.S = 1;
                            }
                            return 1;
                        }
                        if (this.L.h != 8) ** GOTO lbl191
                        var2_13 = (b.c)this.L;
                        var1_5 = this;
                        if (var2_13 == null) ** GOTO lbl190
                        var3_17 = null;
                        if (var2_13 == var1_5.w[3] || var2_13 == var1_5.w[4]) {
                            var1_5.V = true;
                            var3_17 = var1_5.k;
                        } else {
                            var1_5.V = false;
                            var3_17 = var1_5.w;
                        }
                        var4_25 = var2_13.j();
                        if (var1_5.k == null || var4_25 == null || var2_13.D() == 512) ** GOTO lbl174
                        for (var5_31 = 0; var5_31 < var1_5.k.length; ++var5_31) {
                            if (var1_5.k[var5_31] == null || var1_5.k[var5_31].I() >= 30 || var1_5.k[var5_31].w()) continue;
                            var1_5.N = var4_25;
                            var2_13.a(var4_25, -1);
                            var1_5.Q = 1;
                            var1_5.S = var1_5.N.v;
                            var1_5.R = var1_5.N.w;
                            var3_17 = var1_5.a(var1_5.V, var1_5.N);
                            var1_5.M = var1_5.k[var5_31];
                            var1_5.a(var2_13, var1_5.M, var3_17, var1_5.N, var1_5.V);
                            ** GOTO lbl190
                        }
lbl174:
                        // 2 sources

                        if ((var5_31 = e.a.b.c(100)) >= 40 || var2_13.D() == 512) ** GOTO lbl-1000
                        var1_5.N = var2_13.i();
                        if (var1_5.N != null) {
                            var2_13.a(var1_5.N, -1);
                            var1_5.Q = 1;
                            var1_5.S = var1_5.N.v;
                            var1_5.R = var1_5.N.w;
                            var3_17 = var1_5.a(var1_5.V, var1_5.N);
                            var1_5.M = var2_13.a(var3_17);
                            var1_5.a(var2_13, var1_5.M, var3_17, var1_5.N, var1_5.V);
                        } else lbl-1000:
                        // 2 sources

                        {
                            var1_5.M = var2_13.a(var3_17);
                            var1_5.a((b.f)var2_13, var1_5.M, var1_5.V);
                            var1_5.Q = 0;
                            var1_5.S = 1;
                            var1_5.R = 1;
                        }
lbl190:
                        // 4 sources

                        return 1;
lbl191:
                        // 1 sources

                        this.v();
                        return 0;
                    }
                }
                return 5;
            }
            case 0: {
                return 5;
            }
            case 1: {
                var1_1 = var2_9;
                switch (var1_1) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        var3_18 = this;
                        block33 : switch (var3_18.Q) {
                            case 1: 
                            case 5: {
                                var4_26 = var3_18;
                                if (var4_26.N == null) break;
                                var5_32 = var4_26.V != false ? var4_26.k : var4_26.w;
                                switch (var4_26.S) {
                                    case 0: {
                                        for (var2_9 = 0; var2_9 < var5_32.length; var2_9 = (byte)(var2_9 + 1)) {
                                            if (var5_32[var2_9] == null || var5_32[var2_9].w()) continue;
                                            var4_26.a(var4_26.y.d, var5_32[var2_9]);
                                        }
                                        break block33;
                                    }
                                    case 1: {
                                        var4_26.a(var4_26.y.d, var4_26.M);
                                    }
                                }
                                break;
                            }
                            case 0: {
                                if (var3_18.aa) break;
                                var3_18.a(var3_18.L.d, var3_18.M);
                                break;
                            }
                            case 2: {
                                var4_27 = var3_18;
                                switch (var4_27.S) {
                                    case 0: {
                                        for (var5_33 = 0; var5_33 < var4_27.k.length; ++var5_33) {
                                            var4_27.a(var4_27.L.d, (b.f)var4_27.k[var5_33]);
                                        }
                                        ** GOTO lbl232
                                    }
                                    case 1: {
                                        var4_27.a(var4_27.L.d, var4_27.M);
                                    }
                                }
                            }
                        }
lbl232:
                        // 9 sources

                        var1_1 = (byte)this.o();
                        var3_18 = this;
                        for (var4_23 = 0; var4_23 < var3_18.w.length; ++var4_23) {
                            if (var3_18.w[var4_23] == null || !var3_18.w[var4_23].c() || var3_18.w[var4_23].e() == -1) continue;
                            if (var3_18.w[var4_23].c(2) * 100 / var3_18.w[var4_23].c(3) <= 20) {
                                if (var3_18.w[var4_23].h != 6) continue;
                                var3_18.w[var4_23].a(10, -1);
                                continue;
                            }
                            if (var3_18.w[var4_23].w()) continue;
                            if (var3_18.w[var4_23].h == 6) {
                                var3_18.w[var4_23].a(8, -1);
                                continue;
                            }
                            var3_18.w[var4_23].a(0, -1);
                        }
                        for (var4_23 = 0; var4_23 < var3_18.k.length; ++var4_23) {
                            if (var3_18.k[var4_23] == null || !var3_18.k[var4_23].c() || var3_18.k[var4_23].e() == -1 || var3_18.k[var4_23].w()) continue;
                            var3_18.k[var4_23].a(0, -1);
                        }
                        var3_18 = this;
                        if (var3_18.w != null) {
                            for (var4_23 = 0; var4_23 < var3_18.w.length; ++var4_23) {
                                if (var3_18.w[var4_23] == null || var3_18.w[var4_23].c()) continue;
                                v5 = 0;
                                break;
                            }
                        } else if (var3_18.k != null) {
                            for (var4_23 = 0; var4_23 < var3_18.k.length; ++var4_23) {
                                if (var3_18.k[var4_23] == null || var3_18.k[var4_23].c()) continue;
                                v5 = 0;
                                break;
                            }
                        } else {
                            v5 = var2_9 = 1;
                        }
                        if (var1_1 == 0 || var2_9 == 0) break;
                        return this.r();
                    }
                    case 2: {
                        var3_19 = this.L;
                        if (var3_19 != null && (var4_23 = var3_19.C()) > 0) {
                            var3_19.i(var4_23 - 1);
                            if (var4_23 - 1 <= 0) {
                                var3_19.e((short)0);
                            }
                        }
                        if ((var3_19 = this.L) != null) {
                            for (var4_23 = 0; var4_23 < var3_19.y.length; ++var4_23) {
                                if (var3_19.y[var4_23] <= 0) continue;
                                v6 = var4_23;
                                var3_19.y[v6] = var3_19.y[v6] - 1;
                                if (var3_19.y[var4_23] != 0) continue;
                                var3_19.k(var4_23);
                            }
                        }
                        this.L.s();
                        this.N = null;
                        this.O = null;
                        this.L = null;
                        this.M = null;
                        this.aa = false;
                        this.x = null;
                        this.y = null;
                    }
                }
                return 5;
            }
            case 2: {
                var1_1 = var2_9;
                switch (var1_1) {
                    case 0: {
                        if (!this.p()) ** GOTO lbl411
                        var3_20 = this;
                        b.a.c(8, 1);
                        for (var6_39 = 0; var6_39 < 3; ++var6_39) {
                            if (var3_20.w[var6_39] == null) continue;
                            var3_20.w[var6_39].a(18, 0);
                        }
                        var2_14 = var3_20;
                        var1_1 = 0;
                        for (var4_23 = 0; var4_23 < var2_14.k.length; ++var4_23) {
                            if (var2_14.k[var4_23] == null) continue;
                            var1_1 = (byte)(var1_1 + var2_14.k[var4_23].c(1));
                        }
                        var4_23 = b.a.b(var2_14.k);
                        var1_1 = var4_23 - 5 > (var5_34 = b.a.b(var2_14.w)) ? (byte)(var1_1 << 1) : (byte)((var4_23 - var5_34) * var1_1 / 5 + var1_1);
                        v7 = var1_1 * b.a.c / 2;
                        var1_1 = (byte)v7;
                        var6_39 = v7 <= 0 ? 1 : var1_1;
                        var2_14 = var3_20;
                        var1_1 = 0;
                        for (var4_23 = 0; var4_23 < var2_14.k.length; ++var4_23) {
                            if (var2_14.k[var4_23] == null) continue;
                            var1_1 = (byte)(var1_1 + var2_14.k[var4_23].o);
                        }
                        var4_23 = b.a.b(var2_14.k);
                        if (var4_23 < (var5_34 = b.a.b(var2_14.w))) {
                            var1_1 = (byte)((var4_23 - var5_34) * var1_1 / 5 + var1_1);
                        }
                        v8 = var1_1 * b.a.b / 2;
                        var1_1 = (byte)v8;
                        var7_44 = v8 < 0 ? 1 : var1_1;
                        var2_14 = var3_20;
                        var1_6 = new f.b[var2_14.k.length];
                        for (var4_23 = 0; var4_23 < var2_14.k.length; ++var4_23) {
                            if (var2_14.k[var4_23] != null) ** GOTO lbl329
                            v9 = var1_6;
                            v10 = var4_23;
                            ** GOTO lbl-1000
lbl329:
                            // 1 sources

                            v9 = var1_6;
                            v10 = var4_23;
                            var5_35 = var2_14.k[var4_23];
                            var9_51 = e.a.b.c(100);
                            if (var9_51 <= var5_35.q) {
                                v11 = b.a.a((int)var5_35.p);
                            } else lbl-1000:
                            // 2 sources

                            {
                                v11 = null;
                            }
                            v9[v10] = v11;
                        }
                        var5_36 = var8_48 = var1_6;
                        var4_23 = var7_44;
                        var1_1 = (byte)var6_39;
                        var2_14 = var3_20;
                        b.a.g(var4_23);
                        var9_51 = 0;
                        var4_28 = new String[3];
                        for (var10_56 = 0; var10_56 < 3; ++var10_56) {
                            if (var2_14.w[var10_56] == null) continue;
                            var9_52 = var2_14.w[var10_56].c(0);
                            var2_14.w[var10_56].b((byte)1, (int)var1_1);
                            var11_57 = var9_52;
                            var9_53 = var2_14.w[var10_56];
                            if (var9_53 == null) {
                                v12 = null;
                            } else {
                                var12_58 = var9_53.c(0);
                                if (var12_58 == var11_57) {
                                    v12 = null;
                                } else {
                                    var13_59 = new StringBuffer();
                                    var13_59.append(var9_53.z);
                                    var13_59.append(b.a.p[41]);
                                    var13_59.append(var9_53.c(0));
                                    var13_59.append(b.a.p[37]);
                                    var14_60 = null;
                                    ++var11_57;
                                    while (var11_57 <= var12_58) {
                                        var14_60 = b.a.b((int)var9_53.e(var11_57));
                                        if (var14_60 != null) {
                                            var13_59.append("\n");
                                            var13_59.append(b.a.p[65]);
                                            var13_59.append(var14_60.t);
                                        }
                                        ++var11_57;
                                    }
                                    v12 = var13_59.toString();
                                }
                            }
                            var4_28[var10_56] = v12;
                        }
                        if (var5_36 != null) {
                            for (var10_56 = 0; var10_56 < var5_36.length; ++var10_56) {
                                b.a.b(var5_36[var10_56], 1);
                            }
                        }
                        for (var10_56 = 0; var10_56 < 3; ++var10_56) {
                            if (var2_14.w[var10_56] == null) continue;
                            b.a.B[var10_56].l(var1_1);
                            var2_14.w[var10_56].L();
                        }
                        var9_54 = var4_28;
                        var6_40 = d.c.a(var6_39, var7_44, var8_48);
                        var7_45 /* !! */  = c.b.a(var6_40, 5000L);
                        var3_20.v.addElement(var7_45 /* !! */ );
                        for (var10_56 = 0; var10_56 < var9_54.length; ++var10_56) {
                            if (var9_54[var10_56] == null) continue;
                            var7_45 /* !! */  = c.b.a(var9_54[var10_56], 3000L);
                            var3_20.v.addElement(var7_45 /* !! */ );
                        }
                        var10_56 = var3_20.s();
                        var7_45 /* !! */  = null;
                        for (var8_49 = 0; var8_49 < var3_20.w.length; ++var8_49) {
                            if (var3_20.w[var8_49] == null || (var7_45 /* !! */  = var3_20.w[var8_49].d(var10_56)) == null || (var6_40 = d.c.a(var3_20.w[var8_49], var7_45 /* !! */ )) == null) continue;
                            var7_45 /* !! */  = c.b.a(var6_40, 3000L);
                            var3_20.v.addElement(var7_45 /* !! */ );
                        }
                        ** GOTO lbl415
lbl411:
                        // 1 sources

                        if (this.q()) {
                            var3_21 = c.b.a(b.a.p[67], 5000L);
                            var3_21.f();
                        }
lbl415:
                        // 6 sources

                        for (var1_1 = (byte)3; var1_1 < this.w.length; var1_1 = (byte)(var1_1 + 1)) {
                            if (this.w[var1_1] == null) continue;
                            b.a.a((int)(-this.w[var1_1].f), 1);
                        }
                        this.W = (byte)5;
                        break;
                    }
                    case 1: {
                        if (this.v.isEmpty()) {
                            if (this.q()) {
                                var3_22 = this;
                                if (!(var3_22.F != null ? (var3_22.F.g & 2) != 0 : var3_22.Y.k())) {
                                    var1_7 = c.b.a(new String[]{"\u5168\u4f53\u590d\u6d3b", "\u8fd4\u56de"}, (byte)0, (byte)1, true);
                                    v13 = var1_7.f();
                                    var2_9 = (byte)v13;
                                    if (v13 == 1 && var1_7.d().equals("\u5168\u4f53\u590d\u6d3b")) {
                                        var3_22 = "\u662f\u5426\u5168\u4f53\u590d\u6d3b?\u8d60\u90012\u4e2a\u5143\u5b9d(\u7528\u4e8e\u6362\u9ad8\u7ea7\u88c5\u5907),\u8bf7\u8bb0\u5f97\u5b58\u6863!";
                                        var4_29 = null;
                                        var5_37 = false;
                                        var5_37 = b.a.a != false ? true : g.a.a.a(MainMIDlet.a, (Displayable)main.b.a, (String)var3_22, "02", "02", 200);
                                        if (var5_37) {
                                            b.a.a(1001, 2);
                                            for (var2_9 = 0; var2_9 < this.w.length; var2_9 = (byte)(var2_9 + 1)) {
                                                if (this.w[var2_9] == null) continue;
                                                this.w[var2_9].K();
                                                this.w[var2_9].a(8, -1);
                                            }
                                            var4_29 = "\u590d\u6d3b\u6210\u529f!";
                                            var2_15 = c.b.a("\u590d\u6d3b\u6210\u529f!", 3000L);
                                            var2_15.f();
                                            return 3;
                                        }
                                    }
                                    this.b = new i();
                                    break;
                                }
                            }
                            this.b = new h();
                            break;
                        }
                        var1_8 = (c.b)this.v.elementAt(0);
                        var1_8.f();
                        this.v.removeElementAt(0);
                    }
                }
                return 5;
            }
        }
        return 5;
    }

    private b.f[] a(boolean bl, f.c c2) {
        if (c2 == null) {
            return null;
        }
        if (bl) {
            if (c2.w == 1) {
                return this.k;
            }
            return this.w;
        }
        if (c2.w == 1) {
            return this.w;
        }
        return this.k;
    }

    private void c(b.b b2) {
        if (b2 == null) {
            return;
        }
        if (this.x == null) {
            this.x = new b.b[1];
            this.x[0] = b2;
            return;
        }
        b.b[] bArray = new b.b[this.x.length + 1];
        System.arraycopy(this.x, 0, bArray, 0, this.x.length);
        bArray[bArray.length - 1] = b2;
        this.x = bArray;
    }

    public final b.c[] i() {
        return this.k;
    }

    static Image a(c c2) {
        return c2.r;
    }

    static byte b(c c2) {
        return c2.R;
    }

    static byte c(c c2) {
        return c2.S;
    }

    static short[][] j() {
        return n;
    }

    static int d(c c2) {
        return c2.T;
    }

    static short[][] k() {
        return m;
    }

    static b.f[] e(c c2) {
        return c2.w;
    }

    static byte f(c c2) {
        return c2.Q;
    }

    static f.b g(c c2) {
        return c2.P;
    }

    static int a(c c2, int n2, b.f[] fArray, boolean n3) {
        int n4 = n3;
        b.f[] fArray2 = fArray;
        int n5 = n2;
        if (fArray2 != null) {
            for (n3 = 0; n3 < fArray2.length; ++n3) {
                if (fArray2[n5 = (n5 - 1 + fArray2.length) % fArray2.length] == null || fArray2[n5].w() && n4 == 0) continue;
                return n5;
            }
        }
        return -1;
    }

    static int a(c c2, int n2) {
        c2.T = n2;
        return c2.T;
    }

    static int b(c c2, int n2, b.f[] fArray, boolean bl) {
        return d.c.a(n2, fArray, bl);
    }

    static void h(c c2) {
        c2.v();
    }

    static f.b a(c c2, f.b b2) {
        c2.P = b2;
        return c2.P;
    }

    static b.f i(c c2) {
        return c2.L;
    }

    static void a(c c2, c.b b2) {
        v0.W = 1;
        Stack stack = d.a.e();
        Object object = null;
        for (int i2 = 0; i2 < stack.size(); ++i2) {
            object = (k)stack.elementAt(i2);
            ((k)object).b();
        }
        c2.L.b((byte)6, (int)(-((f.a)c2.P).n));
        b.c c3 = b.a.c(-c2.P.r);
        if (c3 != null) {
            b.a.a(c2.P, 1);
            int n2 = b2.c();
            if (c2.w[n2 += 3] != null) {
                c2.G.remove(c2.w[n2]);
                if (!c2.w[n2].w()) {
                    b.a.a((int)(-c2.w[n2].f), 1);
                }
            }
            c2.w[n2] = c3;
            c2.w[n2].n = true;
            c2.w[n2].a();
            c2.w[n2].a(-1, -1);
            c2.w[n2].j = m[n2][0];
            c2.w[n2].k = m[n2][1];
            c2.G.put(c2.w[n2], new Vector());
            int n3 = n2;
            b.f f2 = c2.L;
            if (f2 != null) {
                object = null;
                if (c2.w[n3] != null && !c2.w[n3].w()) {
                    object = main.c.a(c2.w[n3], -1, -1, -1, null, false);
                    c2.a((b.b)f2, (main.c)object);
                }
                object = main.c.a(f2, 17, 0, 8, null, false);
                c2.a((b.b)f2, (main.c)object);
                object = d.c.a(f2);
                c2.a((b.b)f2, (main.c)object);
                object = main.c.a(1002, m[n3][0], (short)(m[n3][1] + 25), 2);
                c2.a((b.b)f2, (main.c)object);
                object = main.c.a(c2.w[n3], 0, -1, -1, null, true);
                c2.a((b.b)f2, (main.c)object);
            }
        }
    }

    static byte a(c c2, byte by) {
        c2.R = by;
        return c2.R;
    }

    static byte b(c c2, byte by) {
        c2.S = by;
        return c2.S;
    }

    static byte c(c c2, byte by) {
        c2.Q = by;
        return c2.Q;
    }

    static void j(c c2) {
        d.a.d(c2.U);
    }

    static f.c a(c c2, f.c c3) {
        c2.N = c3;
        return c2.N;
    }

    static f.c k(c c2) {
        return c2.N;
    }

    static f.c b(c c2, f.c c3) {
        c2.O = c3;
        return c2.O;
    }
}

