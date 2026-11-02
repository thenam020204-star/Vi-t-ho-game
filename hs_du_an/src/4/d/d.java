/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package d;

import b.b.h;
import b.b.j;
import d.b;
import d.c;
import d.e;
import d.f;
import d.g;
import d.i;
import d.k;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.a;

public final class d
extends k
implements j {
    private static final short[][] m;
    private static final short[][] n;
    private Image o = null;
    private Image p = null;
    private Image q = null;
    private Image r = null;
    private Image s = null;
    private Image t = null;
    public static Image a;
    public static Image[] b;
    public static Image c;
    public static Image d;
    private static int u;
    public static int e;
    private Vector v = new Vector();
    public a.g[] f = null;
    private int[][] w = null;
    public a.d[] g = null;
    private a.b x = null;
    private a.b y = null;
    private int z = 0;
    private int[] A = null;
    private int B = -1;
    private int C = -1;
    private int D = -1;
    private b.a.c[] E = null;
    private int[][] F = null;
    private b.f.b G = null;
    private Hashtable H = null;
    private b.b.g I = null;
    private a.g J = null;
    private a.g K = null;
    private f.c L = null;
    private byte M = 0;
    private byte N = 1;
    private byte O = 1;
    private boolean P = true;
    private byte Q = (byte)3;
    private int R = -1;
    private a.a S = null;
    private boolean T = false;
    private byte U = (byte)3;
    private int V = 0;
    private int W = 0;

    public static d a(b.f.b b2) {
        d d2 = new d();
        new d().G = b2;
        return d2;
    }

    public static d a(a.a a2) {
        d d2 = new d();
        new d().S = a2;
        return d2;
    }

    private d() {
        super((byte)4);
    }

    public final void a() {
        e = 0;
        this.j = true;
        a.f.a();
        Object object = a.f.w.j();
        if (object != null) {
            this.F = new int[((a.a[])object).length][3];
            for (int i2 = 0; i2 < this.F.length; ++i2) {
                this.F[i2][0] = object[i2].m();
                this.F[i2][1] = object[i2].l();
                this.F[i2][2] = object[i2].k != null && object[i2].k.h() ? 1 : 0;
            }
        }
        a.f.k();
        a.f.w.l();
        System.gc();
        b.e.a.a(100L);
        super.a(10);
        main.a.a.a();
        long l2 = System.currentTimeMillis();
        this.H = new Hashtable();
        object = this;
        b.e.d d2 = b.e.d.a("/b.pak");
        if (object.G != null) {
            object.o = d2.c(object.G.k + "_t.png");
            object.p = d2.c(object.G.l + "_b.png");
        } else {
            object.o = d2.c(object.S.e() + "_t.png");
            object.p = d2.c(object.S.f() + "_b.png");
        }
        if (object.o == null || object.p == null) {
            object.p = d2.c("0_b.png");
            object.o = d2.c("0_t.png");
        }
        object.q = d2.c("bg.png");
        a = d2.c("status.png");
        Image[] imageArray = new Image[3];
        b = imageArray;
        imageArray[0] = d2.c("a.png");
        d.d.b[1] = d2.c("d.png");
        d.d.b[2] = d2.c("s.png");
        c = d2.c("up.png");
        d = d2.c("down.png");
        object.s = d2.c("miss.png");
        object.r = d2.c("statusBorder.png");
        object.t = d2.c("angry.png");
        super.a(20);
        main.a.a.a();
        this.A = new int[2];
        this.A[0] = a.f.A[0].s;
        this.A[1] = a.f.A[0].t;
        this.B = a.f.A[0].l;
        this.C = a.f.A[0].l();
        this.D = a.f.A[0].m();
        this.E = new b.a.c[a.f.A.length];
        for (int i3 = 0; i3 < this.E.length; ++i3) {
            this.E[i3] = a.f.A[i3].j;
        }
        this.t();
        this.j();
        this.w();
        this.R = a.f.ao;
        if (this.G != null && this.G.j >= 0) {
            a.f.a((int)this.G.j, -1, (int)a.f.m);
        } else if (this.S != null) {
            a.f.a(this.S.d(), -1, (int)a.f.m);
        }
        super.a(100);
        main.a.a.a();
        this.j = false;
        long l3 = 1500L - (System.currentTimeMillis() - l2);
        b.e.a.a(l3 <= 0L ? 100L : l3);
    }

    public final void b() {
        a.a[] aArray;
        int n2;
        e = 0;
        super.b();
        this.j = true;
        a.f.a();
        super.a(0);
        Object object = this;
        this.p = null;
        ((d)object).o = null;
        ((d)object).q = null;
        a = null;
        b = null;
        b = null;
        c = null;
        d = null;
        ((d)object).t = null;
        ((d)object).s = null;
        ((d)object).r = null;
        this.H.clear();
        this.H = null;
        this.I = null;
        object = this;
        for (int i2 = 0; i2 < a.f.A.length; ++i2) {
            if (((d)object).f[i2].w()) {
                ((d)object).f[i2].a((byte)2, 1);
            }
            ((d)object).f[i2].F();
            ((d)object).f[i2].c((short)0);
            ((d)object).f[i2].j = ((d)object).E[i2];
            ((d)object).f[i2].a(((d)object).B, ((d)object).D);
            ((d)object).f[i2].a(((d)object).C);
            a.f.A[i2].s = ((d)object).A[0];
            a.f.A[i2].t = ((d)object).A[1];
        }
        a.f.w.a(a.f.A[0]);
        object = null;
        if (this.f != null) {
            for (n2 = 0; n2 < this.f.length; ++n2) {
                object = this.f[n2];
                if (object == null) continue;
                ((a.b)object).y = null;
                ((a.b)object).z = null;
            }
        }
        this.f = null;
        for (n2 = 0; n2 < this.g.length; ++n2) {
            if (this.g[n2] == null) continue;
            this.g[n2].c();
        }
        this.g = null;
        super.a(30);
        main.a.a.a();
        long l2 = System.currentTimeMillis();
        a.f.w.m();
        object = this;
        if (a.f.w != null && (aArray = a.f.w.j()) != null && ((d)object).F != null && aArray.length == ((d)object).F.length) {
            for (int i3 = 0; i3 < aArray.length; ++i3) {
                aArray[i3].a(aArray[i3].l, ((d)object).F[i3][0]);
                aArray[i3].a(((d)object).F[i3][1]);
                if (((d)object).F[i3][2] != 1 || aArray[i3].k == null) continue;
                aArray[i3].k.a(true);
            }
        }
        super.a(60);
        main.a.a.a();
        super.a(100);
        main.a.a.a();
        if (this.S != null) {
            this.S.e = true;
            this.S.f = false;
            this.S.g = true;
            this.S.p();
        }
        if (this.G != null) {
            this.G.c();
        }
        a.f.a(this.R, -1, (int)a.f.m);
        long l3 = 1500L - (System.currentTimeMillis() - l2);
        b.e.a.a(l3 <= 0L ? 100L : l3);
        this.j = false;
        a.f.X = null;
        a.f.Y = 0;
    }

    private void j() {
        this.g = new a.d[3];
        if (this.G != null) {
            this.g[0] = this.G.g == 0 ? null : a.f.d(this.G.g);
            this.g[1] = this.G.h == 0 ? null : a.f.d(this.G.h);
            this.g[2] = this.G.i == 0 ? null : a.f.d(this.G.i);
        } else {
            this.g[0] = this.S.b == 0 ? null : a.f.d(this.S.b);
            this.g[1] = this.S.c == 0 ? null : a.f.d(this.S.c);
            this.g[2] = this.S.d == 0 ? null : a.f.d(this.S.d);
        }
        int n2 = 0;
        int n3 = 0;
        for (int i2 = 0; i2 < this.g.length; ++i2) {
            if (this.g[i2] == null) continue;
            n3 += this.g[i2].c(0);
            ++n2;
            this.H.put(this.g[i2], new Vector());
            this.g[i2].i();
            this.g[i2].a(0, -1);
            this.g[i2].s = n[i2][0];
            this.g[i2].t = n[i2][1];
            this.g[i2].s();
        }
        this.z = n3 / n2;
    }

    public final void a(Graphics graphics) {
        d.k.c(graphics);
        if (this.j) {
            d.k.a(graphics, this.k);
            return;
        }
        Graphics graphics2 = graphics;
        d d2 = this;
        if (d2.o != null) {
            graphics2.drawImage(d2.o, 0, 0, 20);
        }
        if (d2.p != null) {
            graphics2.drawImage(d2.p, 0, d2.o.getHeight(), 20);
        }
        graphics2 = graphics;
        d2 = this;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        if (d2.q != null) {
            n4 = d2.q.getWidth();
            n5 = d2.q.getHeight();
        } else {
            n4 = main.a.d / 3;
            n5 = main.a.d / 3;
        }
        for (int i2 = 0; i2 < 3; ++i2) {
            n2 = i2 * n4;
            n3 = main.a.e - n5;
            int[] nArray = d2.w[i2];
            a.g g2 = d2.f[i2];
            int n6 = n3;
            int n7 = n2;
            Graphics graphics3 = graphics2;
            d d3 = d2;
            if (d3.q != null) {
                graphics3.drawImage(d3.q, n7, n6, 20);
            } else {
                graphics3.setColor(15454341);
                graphics3.fillRect(n7, n6, main.a.d / 3, main.a.d / 3);
            }
            if (g2 == null || nArray == null || nArray.length != 3) continue;
            int n8 = n7 + 5;
            int n9 = n6 + 4;
            int n10 = g2.r();
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
            d.d.a(graphics3, 35, n8, n9 += 9, g2.q(), a.f.h);
            graphics3.drawImage(a.f.O, n7 + 6, n6 + 23, 20);
            n8 = n7 + 29;
            n9 = n6 + 23;
            n11 = g2.V() * 100 / nArray[0];
            b.e.a.a(graphics3, a.f.D, g2.c(2), n8, n9);
            d.d.a(graphics3, 35, n8 + 10, n9 += a.f.D.getHeight(), n11, a.f.e);
            n11 = g2.W() * 100 / nArray[1];
            b.e.a.a(graphics3, a.f.D, g2.c(4), n8, n9 += 2 + a.f.e.length);
            d.d.a(graphics3, 35, n8 + 10, n9 += a.f.D.getHeight(), n11, a.f.f);
            n11 = g2.X() * 100 / nArray[2];
            b.e.a.a(graphics3, a.f.D, g2.c(6), n8, n9 += 2 + a.f.f.length);
            d.d.a(graphics3, 35, n8 + 10, n9 += a.f.D.getHeight(), n11, a.f.g);
            n8 = n7 + 80 - 12;
            g2.a(graphics3, n8, n6 + 21 + 5, (short)20);
        }
        graphics2 = graphics;
        d2 = this;
        for (n2 = 3; n2 < 5; ++n2) {
            if (d2.f[n2] == null) continue;
            d2.f[n2].a(graphics2, d2.f[n2].s, d2.f[n2].t);
        }
        a.b b2 = d2.x;
        while (b2 != null) {
            b2.a(graphics2, b2.s, b2.t);
            b2 = b2.z;
        }
        graphics2 = graphics;
        d2 = this;
        if (d2.U != 1) {
            int n23;
            for (n23 = 0; n23 < d2.g.length; ++n23) {
                if (d2.g[n23] == null || d2.g[n23].w() || d2.g[n23].k == null) continue;
                d2.a(graphics2, d2.g[n23], d2.g[n23].s + d2.g[n23].u / 2, d2.g[n23].t - d2.g[n23].v, false);
            }
            for (n23 = 0; n23 < d2.f.length; ++n23) {
                if (d2.f[n23] == null || d2.f[n23].w() || d2.f[n23].k == null) continue;
                d2.a(graphics2, d2.f[n23], d2.f[n23].s - d2.f[n23].u / 2 - d2.r.getWidth() / 2, d2.f[n23].t - d2.f[n23].v * 3 / 4, true);
            }
        }
        if (this.J != null) {
            b.e.c.a(graphics, 0, this.J.p, main.a.d, 0, 24);
        }
        this.b(graphics);
    }

    private static void a(Graphics graphics, int n2, int n3, int n4, int n5, int[] nArray) {
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
     */
    public final void a(b.b.c var1_2) {
        block15: {
            block30: {
                block31: {
                    block28: {
                        block29: {
                            block27: {
                                block24: {
                                    block26: {
                                        block25: {
                                            block22: {
                                                block23: {
                                                    block18: {
                                                        block21: {
                                                            block20: {
                                                                block19: {
                                                                    block16: {
                                                                        block17: {
                                                                            block14: {
                                                                                if (var1_2 != this.I) ** GOTO lbl195
                                                                                var1_2 = this.I.a();
                                                                                if (!var1_2.equals("\u5546\u5e97")) break block14;
                                                                                var0_1 = (main.a.d - main.a.h) / 2;
                                                                                var1_3 = (main.a.e - main.a.i) / 2;
                                                                                var2_6 = new h(var0_1, var1_3, main.a.h, main.a.i);
                                                                                var1_4 = new c.j(c.i.b);
                                                                                var2_6.a(var1_4);
                                                                                var2_6.k();
                                                                                break block15;
                                                                            }
                                                                            if (!var1_2.equals(a.f.o[16])) break block16;
                                                                            v0.I.i = false;
                                                                            this.O = 1;
                                                                            this.N = 1;
                                                                            var1_2 = new i((d)this, this.g, this.N, this.O, false);
                                                                            if (var1_2.k() != 1) break block17;
                                                                            var1_2 = var1_2.j();
                                                                            var2_7 = this;
                                                                            this.N = 1;
                                                                            var2_7.O = 1;
                                                                            var2_7.M = 0;
                                                                            var2_7.K = var1_2;
                                                                            var2_7.a(var2_7.J, var2_7.K);
                                                                            var2_7.Q = 1;
                                                                            this.I.c();
                                                                            ** GOTO lbl194
                                                                        }
                                                                        this.I.i = true;
                                                                        break block15;
                                                                    }
                                                                    if (!var1_2.equals(a.f.o[17])) break block18;
                                                                    if (!this.J.L()) break block19;
                                                                    b.b.g.a(a.f.o[61], 3000L).k();
                                                                    break block15;
                                                                }
                                                                if (this.J.q() == 100) break block20;
                                                                b.b.g.a(a.f.o[62], 2000L).k();
                                                                break block15;
                                                            }
                                                            v1.I.i = false;
                                                            var1_2 = new b.b.f(main.a.d / 10, main.a.e / 8, (main.a.d << 2) / 5, main.a.e * 3 / 4);
                                                            var2_8 = new f((d)this, this.J);
                                                            var1_2.a(var2_8);
                                                            if (var1_2.k() != 2) break block21;
                                                            this.I.i = true;
                                                            ** GOTO lbl194
                                                        }
                                                        this.I.c();
                                                        break block15;
                                                    }
                                                    if (!var1_2.equals(a.f.o[18])) break block22;
                                                    var1_2 = null;
                                                    if (!this.u()) break block23;
                                                    var1_2 = this.J;
                                                    var2_9 = this;
                                                    if (var1_2 == null) ** GOTO lbl-1000
                                                    var3_14 = 0;
                                                    var4_18 = var1_2.c(0);
                                                    var3_14 = var4_18 >= var2_9.z ? 100 - 100 * (25 / (var4_18 - var2_9.z + 50)) : 2500 / (50 - var4_18 + var2_9.z);
                                                    if (b.e.a.c(100) < var3_14) {
                                                        v2 = true;
                                                    } else lbl-1000:
                                                    // 2 sources

                                                    {
                                                        v2 = false;
                                                    }
                                                    if (v2) {
                                                        var1_2 = b.b.g.a(a.f.o[27], 3000L);
                                                        this.Q = (byte)2;
                                                    } else {
                                                        var1_2 = b.b.g.a(a.f.o[63], 3000L);
                                                        this.Q = 1;
                                                    }
                                                    var1_2.k();
                                                    this.I.c();
                                                    ** GOTO lbl194
                                                }
                                                var1_2 = b.b.g.a(a.f.o[64], 3000L);
                                                var1_2.k();
                                                break block15;
                                            }
                                            if (!var1_2.equals(a.f.o[7])) break block24;
                                            if (!this.J.L()) break block25;
                                            this = b.b.g.a(a.f.o[71], 3000L);
                                            this.k();
                                            break block15;
                                        }
                                        v3.I.i = false;
                                        var1_2 = new b.b.f(main.a.d / 10, main.a.e / 8, (main.a.d << 2) / 5, main.a.e * 3 / 4);
                                        var2_10 = new e((d)this, this.J, false);
                                        var1_2.a(var2_10);
                                        if (var1_2.k() != 2) break block26;
                                        this.I.i = true;
                                        ** GOTO lbl194
                                    }
                                    this.I.c();
                                    break block15;
                                }
                                if (!var1_2.equals(a.f.o[60])) break block27;
                                this.J.a(9, 0);
                                this.Q = 1;
                                this.J.M = true;
                                ** GOTO lbl-1000
                            }
                            if (!var1_2.equals(a.f.o[8])) break block28;
                            v4.I.i = false;
                            var1_2 = new b.b.f(main.a.d / 10, main.a.e / 8, (main.a.d << 2) / 5, main.a.e * 3 / 4);
                            var2_11 = new g((d)this, 1);
                            var1_2.a(var2_11);
                            var2_11 = new g((d)this, 2);
                            var1_2.a(var2_11);
                            var2_11 = new g((d)this, 5);
                            var1_2.a(var2_11);
                            if (var1_2.k() != 2) break block29;
                            this.I.i = true;
                            ** GOTO lbl194
                        }
                        this.I.c();
                        break block15;
                    }
                    if (!var1_2.equals(a.f.k(77))) break block30;
                    v5.I.i = false;
                    var1_2 = new i((d)this, this.g, 1, 1, false);
                    if (var1_2.k() != 1) break block31;
                    var1_2 = var1_2.j();
                    var2_12 = this;
                    this.N = 1;
                    var2_12.O = 1;
                    var2_12.M = (byte)4;
                    var3_15 = 0;
                    if (super.u()) {
                        var5_20 = var1_2;
                        var4_19 = var2_12.J;
                        if (var4_19 == null || var5_20 == null) {
                            v6 = false;
                        } else {
                            var6_21 = var4_19.c(0) - var5_20.c(0);
                            var7_23 = var5_20.M();
                            v6 = var6_21 < 0 || var7_23 >= 50 ? false : (var8_24 = ((50 + var6_21 * 2 / 100) * (50 - var7_23) << 1) / 100) >= (var9_26 = b.e.a.c(100));
                        }
                        if (v6) {
                            var3_15 = 1;
                            a.f.a(-var1_2.n, 1);
                        }
                    }
                    var7_23 = var3_15;
                    var6_22 = var1_2;
                    var5_20 = var2_12.J;
                    var4_19 = var2_12;
                    if (var5_20 != null && var6_22 != null) {
                        var8_25 = null;
                        var9_26 = 0;
                        var1_5 = var6_22.t - var6_22.v / 2;
                        var8_25 = b.f.b.a((a.b)var5_20, 17, 1, 8, null, var5_20.w);
                        super.a((a.b)var5_20, var8_25);
                        var8_25 = d.d.a((a.g)var5_20);
                        super.a((a.b)var5_20, var8_25);
                        if (var7_23 != 0) {
                            var3_16 = new b.f.b[2][1];
                            var3_16[0][0] = b.f.b.a((a.b)var6_22, 3, 0, -1, null, var6_22.w);
                            var3_16[1][0] = b.f.b.a(var5_20.s, var5_20.t, (a.b)var6_22, 2, 1);
                            var8_25 = b.f.b.a(var3_16);
                            super.a((a.b)var5_20, var8_25);
                            var8_25 = b.f.b.a(a.f.k(77) + a.f.k(78), (byte)0);
                            super.a((a.b)var5_20, var8_25);
                            var8_25 = b.f.b.a((a.b)var6_22, (byte)2, (short)0, (byte)1);
                            super.a((a.b)var5_20, var8_25);
                        } else {
                            var9_26 = var6_22.s - var4_19.s.getWidth() / 2;
                            var3_17 = a.e.a(var4_19.s, var9_26, var1_5);
                            d.k.a(var3_17);
                            var8_25 = b.f.b.a(var9_26, var1_5 - var6_22.v / 2, var3_17, 2, 1);
                            super.a((a.b)var5_20, var8_25);
                            var8_25 = b.f.b.a(a.f.k(77) + a.f.k(79), (byte)0);
                            super.a((a.b)var5_20, var8_25);
                        }
                    }
                    var2_12.Q = 1;
                    this.I.c();
                    ** GOTO lbl194
                }
                this.I.i = true;
                break block15;
            }
            if (var1_2.equals(a.f.k(25))) {
                v7.I.i = false;
                var1_2 = new b.b.f(main.a.d / 10, main.a.e / 8, (main.a.d << 2) / 5, main.a.e * 3 / 4);
                var2_13 = new e((d)this, this.J, true);
                var1_2.a(var2_13);
                ** if (var1_2.k() != 2) goto lbl-1000
lbl-1000:
                // 1 sources

                {
                    this.I.i = true;
                    ** GOTO lbl196
                }
            }
            ** GOTO lbl194
lbl-1000:
            // 2 sources

            {
                this.I.c();
lbl194:
                // 8 sources

                return;
lbl195:
                // 1 sources

                var1_2.c();
            }
        }
    }

    public final void b(b.b.c c2) {
        d.k.d(c2);
    }

    private b.f.b[] a(a.g g2, f.a a2) {
        int n2;
        if (a2 == null || g2 == null) {
            return null;
        }
        Vector vector = new Vector();
        Object object = null;
        if (a2.h == 1) {
            object = b.f.b.a(g2, 2, 0, 0, null, g2.w);
        } else {
            short s = (short)g2.s;
            n2 = g2.t;
            object = b.f.b.a("r576", s, (short)n2, 0);
        }
        vector.addElement(object);
        object = a2.b(null, g2);
        if (object != null) {
            for (n2 = 0; n2 < ((a.b[])object).length; ++n2) {
                d.k.a(object[n2]);
            }
            b.f.b[][] bArray = new b.f.b[((a.b[])object).length][1];
            for (int i2 = 0; i2 < bArray.length; ++i2) {
                bArray[i2][0] = b.f.b.a(object[i2].s, object[i2].t - g2.v / 2, object[i2], 2, 1);
            }
            object = b.f.b.a(bArray);
            vector.addElement(object);
        }
        object = d.d.a(g2);
        vector.addElement(object);
        Object[] objectArray = new b.f.b[vector.size()];
        vector.copyInto(objectArray);
        return objectArray;
    }

    private void a(a.g g2, a.g bArray, boolean n2) {
        if (g2 == null || bArray == null) {
            return;
        }
        int n3 = g2.s;
        int n4 = g2.t;
        boolean bl = false;
        int n5 = 0;
        this.T = b.e.a.c(100) > 95 - (g2.J() ? 75 : 0);
        Object var6_8 = null;
        short s = 0;
        if (!a.f.l[1]) {
            s = n2 != 0 ? (short)(bArray.s + bArray.u / 2 + g2.u / 2) : (short)(bArray.s - bArray.u / 2 - g2.u / 2);
            b.f.b b2 = b.f.b.a((int)s, bArray.t + 1, g2, 999, 2);
            this.a((a.b)g2, b2);
        }
        if (this.T) {
            if (n2 != 0) {
                b.f.b b3 = b.f.b.a(bArray.s - bArray.u / 2, bArray.t, (a.b)bArray, 4, 2);
                this.a((a.b)g2, b3);
            } else {
                b.f.b b4 = b.f.b.a(bArray.s + bArray.u / 2, bArray.t, (a.b)bArray, 4, 2);
                this.a((a.b)g2, b4);
            }
        }
        b.f.b b5 = b.f.b.a(g2, 1, 0, 0, null, g2.w);
        this.a((a.b)g2, b5);
        n5 = bArray.t - bArray.v / 2;
        if (!this.T) {
            n2 = g2.a((a.g)bArray, false);
            b5 = b.f.b.a((a.b)bArray, (byte)2, (short)(-n2), (byte)0);
            this.a((a.b)g2, b5);
            int n6 = n2 * 100 / this.K.B();
            b.f.b b6 = b.f.b.a((a.b)bArray, (byte)14, (short)(n6 <= 0 ? 1 : n6), (byte)0);
            this.a((a.b)g2, b6);
            b6 = a.f.Q;
            int n7 = bArray.s - String.valueOf(n2).length() * b6.getWidth() / 10 / 2;
            a.b[] bArray2 = new a.b[1];
            a.b[] bArray3 = bArray2;
            bArray2[0] = a.e.a(n2, n7, n5, a.f.Q);
            d.k.a(bArray3[0]);
            bArray = this.a(bArray3, (a.g)bArray);
            if (bArray != null) {
                for (n2 = 0; n2 < bArray.length; ++n2) {
                    this.a((a.b)g2, bArray[n2]);
                }
            }
        } else {
            int n8 = bArray.s - this.s.getWidth() / 2;
            a.e e2 = a.e.a(this.s, n8, n5);
            d.k.a(e2);
            b.f.b b7 = b.f.b.a(n8, n5 - bArray.v / 2, e2, 2, 1);
            this.a((a.b)g2, b7);
            b7 = b.f.b.a(bArray.s, bArray.t, (a.b)bArray, 4, 2);
            this.a((a.b)g2, b7);
        }
        if (!a.f.l[1]) {
            b.f.b b8 = b.f.b.a((int)((short)n3), (short)n4, g2, 999, 2);
            this.a((a.b)g2, b8);
        }
        b.f.b b9 = b.f.b.a(g2, 0, -1, -1, null, g2.w);
        this.a((a.b)g2, b9);
    }

    private b.f.b[] a(a.b[] bArray, a.g g2) {
        if (g2 == null || bArray == null) {
            return null;
        }
        Vector vector = new Vector();
        Object object = null;
        object = new b.f.b[bArray.length][1];
        for (int i2 = 0; i2 < bArray.length; ++i2) {
            object[i2][0] = b.f.b.a(bArray[i2].s, bArray[i2].t - 20, bArray[i2], 2, 1);
        }
        object = b.f.b.a(object);
        vector.addElement(object);
        object = d.d.a(g2);
        vector.addElement(object);
        Object[] objectArray = new b.f.b[vector.size()];
        vector.copyInto(objectArray);
        return objectArray;
    }

    private static b.f.b a(a.g object) {
        if (object == null) {
            return null;
        }
        boolean bl = ((a.b)object).q == 8;
        b.f.b[] bArray = new b.f.b[3];
        b.f.b[] bArray2 = bArray;
        bArray[0] = b.f.b.a((a.g)object, (byte)2, (byte)2, 1, null);
        bArray2[0].p = new b.f.b[2];
        bArray2[0].p[0] = bl ? b.f.b.a((a.b)object, 3, 0, -1, null, ((a.b)object).w) : b.f.b.a((a.b)object, 11, -1, -1, null, ((a.b)object).w);
        bArray2[0].p[1] = b.f.b.a(bArray2[0]);
        bArray2[1] = b.f.b.a((a.g)object, (byte)2, (byte)2, (int)((short)(((a.g)object).B() / 5)), null);
        bArray2[1].p = new b.f.b[2];
        bArray2[1].p[0] = bl ? b.f.b.a((a.b)object, 0, -1, -1, null, ((a.b)object).w) : b.f.b.a((a.b)object, 10, -1, -1, null, ((a.b)object).w);
        bArray2[1].p[1] = b.f.b.a(bArray2[1]);
        bArray2[2] = b.f.b.a((a.g)object, (byte)2, (byte)0, (int)((short)(((a.g)object).B() / 5 - 1)), null);
        bArray2[2].p = new b.f.b[2];
        bArray2[2].p[0] = bl ? b.f.b.a((a.b)object, 0, -1, -1, null, ((a.b)object).w) : b.f.b.a((a.b)object, 8, -1, -1, null, ((a.b)object).w);
        bArray2[2].p[1] = b.f.b.a(bArray2[2]);
        object = b.f.b.a(bArray2);
        return object;
    }

    private void a(a.g g2, a.g g3) {
        if (g2 == null || g3 == null) {
            return;
        }
        int n2 = g2.s;
        int n3 = g2.t;
        this.T = b.e.a.c(100) > 95 - (g2.J() ? 75 : 0);
        int n4 = b.e.a.c(100) > 95 ? 1 : 0;
        Object object = null;
        if (n4 != 0 && !this.T) {
            object = a.e.a(this.t, g2.s - g2.u / 2, g2.t - g2.v / 2);
            d.k.a((a.b)object);
            object = b.f.b.a(((a.b)object).s, ((a.b)object).t - g2.v, (a.b)object, 2, 1);
            this.a((a.b)g2, (b.f.b)object);
        }
        if (!a.f.l[1]) {
            object = b.f.b.a(g2, 13, -1, -1, null, g2.w);
            this.a((a.b)g2, (b.f.b)object);
            short s = (short)(g3.s + g3.u / 2 + g2.u / 2);
            object = b.f.b.a((int)s, g3.t + 1, g2, 8, 2);
            this.a((a.b)g2, (b.f.b)object);
        }
        if (this.T) {
            object = b.f.b.a(g3.s - g3.u / 2, g3.t, g3, 4, 2);
            this.a((a.b)g2, (b.f.b)object);
        }
        if (this.T || n4 == 0) {
            object = b.f.b.a(g2, 15, 0, 8, null, g2.w);
            this.a((a.b)g2, (b.f.b)object);
        } else {
            object = b.f.b.a(g2, 21, 0, 8, null, g2.w);
            this.a((a.b)g2, (b.f.b)object);
        }
        int n5 = 0;
        int n6 = g3.t - g3.v / 2;
        if (this.T) {
            n5 = g3.s - this.s.getWidth() / 2;
            a.e e2 = a.e.a(this.s, n5, n6);
            d.k.a(e2);
            b.f.b b2 = b.f.b.a(n5, n6 - 20, e2, 2, 1);
            this.a((a.b)g2, b2);
            b2 = b.f.b.a(g3.s, g3.t, g3, 4, 2);
            this.a((a.b)g2, b2);
        } else {
            n4 = g2.a(g3, n4 != 0);
            b.f.b b3 = b.f.b.a(g3, (byte)2, (short)(-n4), (byte)0);
            this.a((a.b)g2, b3);
            a.b[] bArray = new a.b[1];
            Image image = a.f.Q;
            int n7 = g3.s - String.valueOf(n4).length() * image.getWidth() / 20;
            bArray[0] = a.e.a(n4, n7, n6, image);
            for (n4 = 0; n4 < bArray.length; ++n4) {
                d.k.a(bArray[n4]);
            }
            b.f.b[] bArray2 = this.a(bArray, g3);
            if (bArray2 != null) {
                for (int i2 = 0; i2 < bArray2.length; ++i2) {
                    this.a((a.b)g2, bArray2[i2]);
                }
            }
        }
        if (!a.f.l[1]) {
            b.f.b b4 = b.f.b.a(g2, 14, -1, -1, null, g2.w);
            this.a((a.b)g2, b4);
            b4 = b.f.b.a((int)((short)n2), (short)n3, g2, 8, 2);
            this.a((a.b)g2, b4);
        }
        b.f.b b5 = d.d.a(g2);
        this.a((a.b)g2, b5);
    }

    private void a(a.g g2, a.g g3, f.c c2) {
        if (g2 == null || c2 == null || c2.g == 1 && g3 == null) {
            return;
        }
        b.f.b b2 = null;
        b2 = g2.q == 8 ? b.f.b.a(g2, 1, 0, 0, null, g2.w) : b.f.b.a(g2, 16, -1, 8, null, g2.w);
        this.a((a.b)g2, b2);
        short s = 0;
        short s2 = 0;
        switch (c2.g) {
            case 0: {
                s = (short)(main.a.d / 3);
                s2 = (short)((main.a.e - this.q.getHeight() << 2) / 5);
                if (c2.h != 0) break;
                s = (short)(s + main.a.d / 3);
                break;
            }
            case 1: {
                s = (short)g3.s;
                s2 = (short)g3.t;
            }
        }
        this.y = new c.h("r" + c2.i[22]);
        this.y.s = s;
        this.y.t = s2;
        this.y.i();
        this.y.f = true;
        this.y.a(-1, -1);
        d.k.a(this.y);
        Object object = b.f.b.a(this.y, 0, 0, -1, null, this.y.w);
        this.a((a.b)g2, (b.f.b)object);
        a.g[] gArray = c2.h == 1 ? this.g : this.f;
        object = b.f.b.a(c2, g2, g3, gArray);
        this.a((a.b)g2, (b.f.b)object);
        object = null;
        switch (c2.g) {
            case 0: {
                object = new b.f.b[gArray.length][];
                for (int i2 = 0; i2 < gArray.length; ++i2) {
                    object[i2] = this.a(c2, g2, gArray[i2]);
                }
                break;
            }
            case 1: {
                b.f.b[][] bArrayArray = new b.f.b[1][];
                object = bArrayArray;
                bArrayArray[0] = this.a(c2, g2, g3);
            }
        }
        object = b.f.b.a(object);
        this.a((a.b)g2, (b.f.b)object);
        if (g2.q == 6) {
            object = d.d.a(g2);
            this.a((a.b)g2, (b.f.b)object);
        }
    }

    private void a(a.d d2, a.g g2, f.c c2) {
        if (d2 == null || c2 == null) {
            return;
        }
        b.f.b b2 = null;
        a.g[] gArray = c2.h == 1 ? this.f : this.g;
        b2 = b.f.b.a(d2, 1, 0, 0, null, d2.w);
        this.a((a.b)d2, b2);
        this.y = new c.h("r" + c2.i[22]);
        this.y.i();
        this.y.a(-1, -1);
        this.y.f = true;
        d.k.a(this.y);
        short s = 0;
        short s2 = 0;
        boolean bl = false;
        switch (c2.g) {
            case 0: {
                s2 = (short)(main.a.e / 2);
                bl = true;
                if (c2.h == 0) {
                    s = (short)(main.a.d / 3);
                    break;
                }
                s = (short)((main.a.d << 1) / 3);
                break;
            }
            case 1: {
                s = (short)g2.s;
                s2 = (short)g2.t;
            }
        }
        this.y.s = s;
        this.y.t = s2;
        b.f.b[][] bArrayArray = b.f.b.a(this.y, 0, 0, -1, null, bl);
        this.a((a.b)d2, (b.f.b)bArrayArray);
        bArrayArray = b.f.b.a(c2, d2, g2, gArray);
        this.a((a.b)d2, (b.f.b)bArrayArray);
        bArrayArray = null;
        switch (c2.g) {
            case 0: {
                bArrayArray = new b.f.b[gArray.length][];
                for (int i2 = 0; i2 < gArray.length; ++i2) {
                    bArrayArray[i2] = this.a(c2, (a.g)d2, gArray[i2]);
                }
                break;
            }
            case 1: {
                b.f.b[][] bArrayArray2 = new b.f.b[1][];
                bArrayArray = bArrayArray2;
                bArrayArray2[0] = this.a(c2, (a.g)d2, g2);
            }
        }
        bArrayArray = b.f.b.a(bArrayArray);
        this.a((a.b)d2, (b.f.b)bArrayArray);
    }

    private b.f.b[] a(f.a bArray, a.g g2, a.g g3) {
        if (bArray == null || g3 == null || g3.w()) {
            return null;
        }
        if ((bArray = bArray.b(g2, g3)) == null) {
            return null;
        }
        for (int i2 = 0; i2 < bArray.length; ++i2) {
            if (bArray[i2] == null) continue;
            d.k.a(bArray[i2]);
        }
        return this.a(bArray, g3);
    }

    private void a(a.b b2, b.f.b b3) {
        Vector vector = (Vector)((d)((Object)vector)).H.get(b2);
        vector.addElement(b3);
    }

    public final void a(int n2, int n3) {
        this.v();
        d.k.d();
        d d2 = this;
        Enumeration enumeration = d2.H.elements();
        Vector vector = null;
        b.f.b b2 = null;
        boolean bl = false;
        while (enumeration.hasMoreElements()) {
            vector = (Vector)enumeration.nextElement();
            if (vector.isEmpty()) continue;
            bl = true;
            b2 = (b.f.b)vector.elementAt(0);
            b2.a();
            if (b2.c == 29 && (b2.d.q == 8 || b2.d.q == 6)) {
                d2.x = a.b.a(d2.x, b2.d);
            }
            if (!b2.b()) continue;
            vector.removeElement(b2);
        }
        byte by = 0;
        if (!d.k.g()) {
            b.b.c c2 = d.k.i();
            c2.a(n2, n3);
            by = this.Q;
            this.Q = (byte)5;
        } else {
            by = this.a(this.U, (byte)1, n2, n3);
        }
        if (by != 5 && by != this.U) {
            this.a(this.U, (byte)2, n2, n3);
            this.U = by;
            this.a(this.U, (byte)0, n2, n3);
        }
    }

    private void a(b.a.d object, a.g g2) {
        boolean bl;
        if (object == null || g2 == null || g2.w()) {
            return;
        }
        b.a.d d2 = object;
        object = g2;
        Object object2 = d2;
        if (!(d2 == null || object == null ? false : (bl = (object2 = ((b.a.d)object2).c()) != null && ((b.a.a)object2).a()))) {
            return;
        }
        if (g2.q == 8) {
            g2.a(2, 0);
            return;
        }
        g2.a(12, 0);
    }

    private boolean k() {
        if (((d)((Object)enumeration)).H.isEmpty()) {
            return true;
        }
        Enumeration enumeration = ((d)((Object)enumeration)).H.elements();
        Vector vector = null;
        while (enumeration.hasMoreElements()) {
            vector = (Vector)enumeration.nextElement();
            if (vector.isEmpty()) continue;
            return false;
        }
        return true;
    }

    private boolean l() {
        for (int i2 = 0; i2 < this.g.length; ++i2) {
            if (this.g[i2] == null || this.g[i2].w()) continue;
            return false;
        }
        return true;
    }

    private boolean m() {
        for (int i2 = 0; i2 < this.f.length; ++i2) {
            if (this.f[i2] == null || this.f[i2].w()) continue;
            return false;
        }
        return true;
    }

    private void n() {
        switch (this.O) {
            case 0: {
                for (int i2 = 0; i2 < this.g.length; ++i2) {
                    this.a(this.J.k, (a.g)this.g[i2]);
                }
                break;
            }
            case 1: {
                this.a(this.J.k, this.K);
            }
        }
    }

    private void o() {
        if (this.L == null) {
            return;
        }
        a.g[] gArray = this.P ? this.g : this.f;
        switch (this.O) {
            case 0: {
                for (int i2 = 0; i2 < gArray.length; ++i2) {
                    if (gArray[i2] == null || gArray[i2].w()) continue;
                    this.a(this.y.k, gArray[i2]);
                }
                break;
            }
            case 1: {
                this.a(this.y.k, this.K);
            }
        }
    }

    private static void b(a.g g2) {
        if (g2 == null) {
            return;
        }
        for (int i2 = 0; i2 < g2.H.length; ++i2) {
            if (g2.H[i2] <= 0) continue;
            int n2 = i2;
            g2.H[n2] = g2.H[n2] - 1;
            if (g2.H[i2] != 0) continue;
            g2.k(i2);
        }
    }

    private void a(a.g g2, int n2) {
        int n3 = g2.s;
        int n4 = g2.t - g2.v / 2;
        Image image = a.f.Q;
        int n5 = image.getWidth() / 10;
        n2 = Math.max(1, n2);
        a.b[] bArray = new a.b[1];
        a.b[] bArray2 = bArray;
        bArray[0] = a.e.a(n2, n3 - String.valueOf(n2).length() * n5 / 2, n4, image);
        for (n3 = 0; n3 < bArray2.length; ++n3) {
            if (bArray2[n3] == null) continue;
            d.k.a(bArray2[n3]);
        }
        g2.b((byte)2, -n2);
        b.f.b[] bArray3 = this.a(bArray2, g2);
        if (bArray3 != null) {
            for (n2 = 0; n2 < bArray3.length; ++n2) {
                this.a((a.b)g2, bArray3[n2]);
            }
        }
    }

    private boolean p() {
        int n2;
        if (this.f != null) {
            for (n2 = 0; n2 < this.f.length; ++n2) {
                if (this.f[n2] == null || this.f[n2].k()) continue;
                return false;
            }
        }
        if (this.g != null) {
            for (n2 = 0; n2 < this.g.length; ++n2) {
                if (this.g[n2] == null || this.g[n2].k()) continue;
                return false;
            }
        }
        return true;
    }

    private byte q() {
        if (this.l()) {
            return 2;
        }
        if (this.m()) {
            return 2;
        }
        return 3;
    }

    private static String a(a.g g2, f.c[] cArray) {
        if (g2 == null || cArray == null || cArray.length <= 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(g2.p);
        stringBuffer.append(a.f.o[65]);
        for (int i2 = 0; i2 < cArray.length; ++i2) {
            stringBuffer.append(cArray[i2].e);
            if (i2 >= cArray.length - 1) continue;
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    private static String a(int n2, int n3, f.a[] aArray) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(a.f.o[34]);
        stringBuffer.append(a.f.o[42]);
        stringBuffer.append(':');
        stringBuffer.append("\n");
        stringBuffer.append(a.f.o[39]);
        stringBuffer.append("(\u5b58\u6d3b\u7684)");
        stringBuffer.append(a.f.o[35]);
        stringBuffer.append('+');
        stringBuffer.append(n2);
        stringBuffer.append("\n");
        stringBuffer.append("\u6cd5\u5b9d\u7ecf\u9a8c\u503c");
        stringBuffer.append('+');
        stringBuffer.append(n2);
        stringBuffer.append("\n");
        stringBuffer.append(a.f.o[32]);
        stringBuffer.append('+');
        stringBuffer.append(n3);
        if (aArray != null) {
            for (n2 = 0; n2 < aArray.length; ++n2) {
                if (aArray[n2] == null) continue;
                stringBuffer.append("\n");
                stringBuffer.append(aArray[n2].e);
                stringBuffer.append('+');
                stringBuffer.append("1");
                stringBuffer.append(a.f.o[40]);
            }
        }
        return stringBuffer.toString();
    }

    private int r() {
        int n2 = 0;
        for (int i2 = 0; i2 < this.g.length; ++i2) {
            if (this.g[i2] == null) continue;
            ++n2;
        }
        return n2;
    }

    private void s() {
        int n2;
        a.g g2 = null;
        for (n2 = 0; n2 < this.f.length; ++n2) {
            g2 = this.f[n2];
            if (g2 == null || !g2.k() || g2.m() == -1) continue;
            if (g2.M() <= 20) {
                if (g2.q == 6) {
                    g2.a(10, -1);
                } else {
                    g2.a(0, -1);
                }
            } else if (!g2.w()) {
                if (g2.q == 6) {
                    g2.a(8, -1);
                } else {
                    g2.a(0, -1);
                }
            }
            if (g2.k == null) continue;
            g2.k.a(g2.w);
        }
        for (n2 = 0; n2 < this.g.length; ++n2) {
            if (this.g[n2] == null || !this.g[n2].k() || this.g[n2].m() == -1 || this.g[n2].w()) continue;
            this.g[n2].a(0, -1);
        }
    }

    private void t() {
        this.f = new a.g[5];
        System.arraycopy(a.f.A, 0, this.f, 0, a.f.A.length);
        this.w = new int[5][3];
        for (int i2 = 0; i2 < this.f.length; ++i2) {
            this.w[i2] = new int[]{1, 1, 1};
            if (this.f[i2] == null) continue;
            this.w[i2][0] = this.f[i2].B();
            this.w[i2][1] = this.f[i2].C();
            this.w[i2][2] = this.f[i2].D();
            this.f[i2].j = b.a.c.b("r" + this.f[i2].n + "_1");
            this.H.put(this.f[i2], new Vector());
            if (this.f[i2].c(2) < this.f[i2].B() * 20 / 100) {
                this.f[i2].a(10, -1);
            } else {
                this.f[i2].a(8, -1);
            }
            this.f[i2].s = m[i2][0];
            this.f[i2].t = m[i2][1];
            this.f[i2].t();
        }
    }

    private boolean u() {
        if (this.G != null) {
            return (this.G.m & 1) != 0;
        }
        return this.S.b();
    }

    private void a(Graphics graphics, a.g g2, int n2, int n3, boolean bl) {
        if (g2 == null || g2.w() || g2.H() == 0 || a == null) {
            return;
        }
        int n4 = a.getWidth() / 13;
        int n5 = a.getHeight();
        short s = g2.H();
        for (int i2 = 0; i2 < 15; ++i2) {
            if ((s & 1 << i2) == 0) continue;
            b.e.a.a(graphics, this.r, 0, 0, this.r.getWidth(), this.r.getHeight(), bl ? 2 : 0, n2, n3, 36);
            b.e.a.a(graphics, a, (i2 - 1) * n4, 0, n4, n5, 0, n2 + this.r.getWidth() / 2, n3 - 3, 33);
            return;
        }
    }

    /*
     * Unable to fully structure code
     */
    private byte a(byte var1_3, byte var2_9, int var3_21, int var4_26) {
        switch (var1_3) {
            case 3: {
                var1_3 = var2_9;
                block7 : switch (var1_3) {
                    case 0: {
                        ** GOTO lbl33
                    }
                    case 1: {
                        var1_4 = this;
                        while (var1_4.V < var1_4.f.length) {
                            if (var1_4.f[var1_4.V] != null && !var1_4.f[var1_4.V].w()) {
                                var1_4.f[var1_4.V].u();
                                if (var1_4.f[var1_4.V].v()) {
                                    var1_4.J = var1_4.f[var1_4.V];
                                    var1_4.P = true;
                                    ++var1_4.V;
                                    v0 = 4;
                                    break block7;
                                }
                            }
                            ++var1_4.V;
                        }
                        var1_4.V = 0;
                        while (var1_4.W < var1_4.g.length) {
                            if (var1_4.g[var1_4.W] != null && !var1_4.g[var1_4.W].w()) {
                                var1_4.g[var1_4.W].u();
                                if (var1_4.g[var1_4.W].v()) {
                                    var1_4.J = var1_4.g[var1_4.W];
                                    ++var1_4.W;
                                    v0 = 4;
                                    break block7;
                                }
                            }
                            ++var1_4.W;
                        }
                        var1_4.W = 0;
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
                var1_3 = var2_9;
                switch (var1_3) {
                    case 0: {
                        this.J.M = false;
                        var2_10 = this.J;
                        var1_5 = this;
                        if (var2_10 != null && var2_10.H() != 0) {
                            var3_21 = 0;
                            v1 = var2_10.H();
                            var4_26 = v1;
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
                                    var3_21 = var2_10.c(2) * (30 + b.e.a.c(20)) / 100;
                                    super.a(var2_10, var3_21);
                                    break;
                                }
                                case 1024: {
                                    switch (var2_10.G()) {
                                        case 0: {
                                            var3_21 = 55 + b.e.a.c(45);
                                            break;
                                        }
                                        case 1: {
                                            var3_21 = 100 + b.e.a.c(990);
                                            break;
                                        }
                                        case 2: {
                                            var3_21 = 100 + b.e.a.c(8999);
                                        }
                                    }
                                    super.a(var2_10, var3_21);
                                    break;
                                }
                                case 8: {
                                    var3_21 = var2_10.c(2) * b.e.a.c(30) / 100;
                                    super.a(var2_10, var3_21);
                                    break;
                                }
                                case 2: {
                                    var3_21 = var2_10.c(2) * b.e.a.c(10) / 100;
                                    super.a(var2_10, var3_21);
                                    break;
                                }
                                case 4096: {
                                    var3_21 = var2_10.c(2) * b.e.a.c(10) / 100;
                                    super.a(var2_10, var3_21);
                                }
                            }
                        }
                        var1_5 = this.J;
                        if (var1_5 == null) break;
                        var2_11 = var1_5.x();
                        if (var2_11 > 0) {
                            var1_5.b((byte)2, var1_5.B() * var2_11 / 100);
                        }
                        if ((var2_11 = var1_5.y()) > 0) {
                            var1_5.b((byte)4, var1_5.C() * var2_11 / 100);
                        }
                        if ((var2_11 = var1_5.z()) <= 0) break;
                        var1_5.b((byte)6, var1_5.D() * var2_11 / 100);
                        break;
                    }
                    case 1: {
                        if (!this.k()) break;
                        if (this.J.w()) {
                            return this.q();
                        }
                        if (this.J.K()) {
                            return 1;
                        }
                        if ((this.J.H() & 32) != 0) {
                            var2_12 = this.J;
                            var1_6 = this;
                            if (var2_12 != null) {
                                if (var2_12 == var1_6.f[3] || var2_12 == var1_6.f[4]) {
                                    var1_6.P = true;
                                }
                                v2 = var3_21 = b.e.a.c(100) < 50 ? 1 : 0;
                                if (var2_12.q == 6) {
                                    var1_6.K = var2_12.a(var3_21 != 0 ? var1_6.g : var1_6.f);
                                    if (var1_6.K == null) {
                                        var1_6.K = var2_12.a(var3_21 != 0 ? var1_6.f : var1_6.g);
                                        var3_21 = var3_21 == 0 ? 1 : 0;
                                    }
                                } else {
                                    var1_6.K = var2_12.a(var3_21 != 0 ? var1_6.f : var1_6.g);
                                    if (var1_6.K == null) {
                                        var1_6.K = var2_12.a(var3_21 != 0 ? var1_6.g : var1_6.f);
                                        v3 = var3_21 = var3_21 == 0 ? 1 : 0;
                                    }
                                }
                                if (var3_21 != 0) {
                                    if (var2_12.q == 8) {
                                        var1_6.a((a.g)var2_12, var1_6.K, var1_6.P);
                                    } else {
                                        var1_6.a((a.g)var2_12, var1_6.K);
                                    }
                                } else {
                                    var5_29 = var1_6.K;
                                    this = var2_12;
                                    var4_27 = var1_6;
                                    if (this != null && var5_29 != null) {
                                        var6_32 = 0;
                                        var3_21 = 0;
                                        var2_12 = null;
                                        var6_32 = this.q == 6 ? 15 : 1;
                                        var3_21 = this.q == 6 ? 8 : 0;
                                        var2_12 = b.f.b.a((a.b)this, var6_32, 0, var3_21, null, this.w);
                                        var4_27.a((a.b)this, (b.f.b)var2_12);
                                        var2_13 = false;
                                        var3_21 = var5_29.t - var5_29.v / 2;
                                        var6_32 = this.a(var5_29, false);
                                        var2_14 = b.f.b.a(var5_29, (byte)2, (short)(-var6_32), (byte)0);
                                        var4_27.a((a.b)this, var2_14);
                                        var2_15 = var6_32 * 100 / var4_27.K.B();
                                        var2_16 = b.f.b.a(var5_29, (byte)14, (short)(var2_15 <= 0 ? 1 : var2_15), (byte)0);
                                        var4_27.a((a.b)this, var2_16);
                                        var7_35 = new a.b[1];
                                        var8_36 = a.f.Q;
                                        var2_17 = var5_29.s - String.valueOf(var6_32).length() * var8_36.getWidth() / 20;
                                        var7_35[0] = a.e.a(var6_32, var2_17, var3_21, var8_36);
                                        for (var2_17 = 0; var2_17 < var7_35.length; ++var2_17) {
                                            d.k.a(var7_35[var2_17]);
                                        }
                                        var2_18 = var4_27.a(var7_35, var5_29);
                                        if (var2_18 != null) {
                                            for (var3_21 = 0; var3_21 < var2_18.length; ++var3_21) {
                                                var4_27.a((a.b)this, var2_18[var3_21]);
                                            }
                                        }
                                    }
                                }
                                var1_6.M = 0;
                                var1_6.O = 1;
                            }
                            return 1;
                        }
                        if (this.J.q != 8) ** GOTO lbl207
                        var2_19 = (a.d)this.J;
                        var1_7 = this;
                        if (var2_19 == null) ** GOTO lbl206
                        var3_22 = null;
                        if (var2_19 == var1_7.f[3] || var2_19 == var1_7.f[4]) {
                            var1_7.P = true;
                            var3_22 = var1_7.g;
                        } else {
                            var1_7.P = false;
                            var3_22 = var1_7.f;
                        }
                        var4_28 = var2_19.b();
                        if (var1_7.P || var1_7.g == null || var4_28 == null || var2_19.H() == 512) ** GOTO lbl173
                        for (var0_1 = 0; var0_1 < var1_7.g.length; ++var0_1) {
                            if (var1_7.g[var0_1] == null || var1_7.g[var0_1].M() >= 30 || var1_7.g[var0_1].w()) continue;
                            var1_7.L = var4_28;
                            var2_19.a(var4_28, -1);
                            var1_7.M = 1;
                            var1_7.O = var1_7.L.g;
                            var1_7.N = var1_7.L.h;
                            var1_7.K = var1_7.g[var0_1];
                            var1_7.a(var2_19, var1_7.K, var1_7.L);
                            ** GOTO lbl206
                        }
lbl173:
                        // 2 sources

                        if ((var0_2 = b.e.a.c(100)) >= 60 || var2_19.H() == 512) ** GOTO lbl201
                        var1_7.L = var2_19.a();
                        if (var1_7.L == null) ** GOTO lbl201
                        var2_19.a(var1_7.L, -1);
                        var1_7.M = 1;
                        var1_7.O = var1_7.L.g;
                        var1_7.N = var1_7.L.h;
                        var3_22 = var1_7.L;
                        var6_33 = var1_7.P;
                        var5_30 = var1_7;
                        if (var3_22 != null) ** GOTO lbl186
                        v4 = null;
                        ** GOTO lbl194
lbl186:
                        // 1 sources

                        if (!var6_33) ** GOTO lbl190
                        if (var3_22.h == 1) ** GOTO lbl-1000
                        v4 = var5_30.f;
                        ** GOTO lbl194
lbl190:
                        // 1 sources

                        if (var3_22.h == 1) {
                            v4 = var5_30.f;
                        } else lbl-1000:
                        // 2 sources

                        {
                            v4 = var5_30.g;
                        }
lbl194:
                        // 4 sources

                        var3_22 = v4;
                        var1_7.K = var2_19.a(var3_22);
                        if (var1_7.P) {
                            var1_7.a((a.g)var2_19, var1_7.K, var1_7.L);
                        } else {
                            var1_7.a(var2_19, var1_7.K, var1_7.L);
                        }
                        ** GOTO lbl206
lbl201:
                        // 2 sources

                        var1_7.K = var2_19.a(var3_22);
                        var1_7.a((a.g)var2_19, var1_7.K, var1_7.P);
                        var1_7.M = 0;
                        var1_7.O = 1;
                        var1_7.N = 1;
lbl206:
                        // 5 sources

                        return 1;
lbl207:
                        // 1 sources

                        var1_8 = this;
                        if (var1_8.J == null) {
                            throw new NullPointerException();
                        }
                        var3_23 = var1_8.J;
                        if (var3_23 == null) {
                            v5 = null;
                        } else {
                            var4_26 = 6;
                            if (var3_23.n == 0) {
                                ++var4_26;
                            }
                            if ((this = var3_23.P()).size() > 0) {
                                ++var4_26;
                            }
                            var5_31 = 0;
                            v6 = new String[++var4_26];
                            var6_34 = v6;
                            ++var5_31;
                            v6[0] = a.f.o[16];
                            ++var5_31;
                            var6_34[1] = a.f.o[7];
                            ++var5_31;
                            var6_34[2] = a.f.o[8];
                            ++var5_31;
                            var6_34[3] = a.f.o[17];
                            ++var5_31;
                            var6_34[4] = a.f.o[60];
                            if (var3_23.n == 0) {
                                ++var5_31;
                                var6_34[5] = a.f.k(77);
                            }
                            if (this.size() > 0) {
                                var6_34[var5_31++] = a.f.k(25);
                            }
                            var6_34[var5_31++] = a.f.o[18];
                            var6_34[var5_31] = "\u5546\u5e97";
                            v5 = var6_34;
                        }
                        var2_20 = v5;
                        var1_8.I = b.b.g.a(var2_20, (byte)0, (byte)4, false, false);
                        d.k.c(var1_8.I);
                        var1_8.I.a(var1_8);
                        return 0;
                    }
                }
                return 5;
            }
            case 0: {
                return 5;
            }
            case 1: {
                var1_3 = var2_9;
                switch (var1_3) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        var3_24 = this;
                        switch (var3_24.M) {
                            case 1: 
                            case 5: {
                                var3_24.o();
                                break;
                            }
                            case 0: {
                                if (var3_24.T) break;
                                var3_24.a(var3_24.J.k, var3_24.K);
                                break;
                            }
                            case 2: {
                                var3_24.n();
                            }
                        }
                        var1_3 = (byte)this.k();
                        this.s();
                        var2_9 = (byte)this.p();
                        if (var1_3 == 0 || var2_9 == 0) break;
                        return this.q();
                    }
                    case 2: {
                        var3_25 = this.J;
                        if (var3_25 != null && (var4_26 = var3_25.G()) > 0) {
                            var3_25.i(var4_26 - 1);
                            if (var4_26 - 1 <= 0) {
                                var3_25.c((short)0);
                            }
                        }
                        d.d.b(this.J);
                        this.J.s();
                        this.L = null;
                        this.J = null;
                        this.K = null;
                        this.T = false;
                        d.k.l.removeAllElements();
                        this.y = null;
                    }
                }
                return 5;
            }
            case 2: {
                return this.a(var2_9);
            }
        }
        return 5;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private byte a(byte var1_1) {
        switch (var1_1) {
            case 0: {
                if (!this.l()) ** GOTO lbl118
                var1_2 = this;
                a.f.a(8, 1, (int)a.f.m);
                for (var2_9 = 0; var2_9 < 3; ++var2_9) {
                    if (var1_2.f[var2_9] == null) continue;
                    var1_2.f[var2_9].a(18, 0);
                }
                var5_15 = var1_2;
                var6_16 = 0;
                for (var7_19 = 0; var7_19 < var5_15.g.length; ++var7_19) {
                    if (var5_15.g[var7_19] == null) continue;
                    var6_16 += var5_15.g[var7_19].c(1);
                }
                var7_19 = a.f.b(var5_15.g);
                var6_16 = var7_19 - 5 > (var8_21 = a.f.b(var5_15.f)) ? (var6_16 <<= 1) : (var7_19 - var8_21) * var6_16 / 5 + var6_16;
                var6_16 = var6_16 * a.f.v() / 2;
                var2_9 = var6_16 <= 0 ? 1 : var6_16;
                var5_15 = var1_2;
                var6_16 = 0;
                for (var7_19 = 0; var7_19 < var5_15.g.length; ++var7_19) {
                    if (var5_15.g[var7_19] == null) continue;
                    var6_16 += var5_15.g[var7_19].a;
                }
                var7_19 = a.f.b(var5_15.g);
                if (var7_19 < (var8_21 = a.f.b(var5_15.f))) {
                    var6_16 = (var7_19 - var8_21) * var6_16 / 5 + var6_16;
                }
                var3_24 = (var6_16 = var6_16 * a.f.u() / 2) < 0 ? 1 : var6_16;
                var5_15 = var1_2;
                var6_17 = new f.a[var1_2.g.length];
                for (var7_19 = 0; var7_19 < var5_15.g.length; ++var7_19) {
                    if (var5_15.g[var7_19] != null) ** GOTO lbl37
                    v0 = var6_17;
                    v1 = var7_19;
                    ** GOTO lbl-1000
lbl37:
                    // 1 sources

                    v0 = var6_17;
                    v1 = var7_19;
                    var8_22 = var5_15.g[var7_19];
                    var9_25 = b.e.a.c(100);
                    if (var9_25 <= var8_22.c) {
                        v2 = a.f.b((int)var8_22.b);
                    } else lbl-1000:
                    // 2 sources

                    {
                        v2 = null;
                    }
                    v0[v1] = v2;
                }
                var8_23 = var4_28 = var6_17;
                var7_19 = var3_24;
                var6_18 = var2_9;
                var5_15 = var1_2;
                a.f.j(var7_19);
                var9_25 = 0;
                var7_20 = new String[3];
                for (var10_30 = 0; var10_30 < 3; ++var10_30) {
                    if (var5_15.f[var10_30] == null || var5_15.f[var10_30].w()) continue;
                    var9_26 = var5_15.f[var10_30].c(0);
                    var5_15.f[var10_30].b((byte)1, var6_18);
                    var11_31 = var9_26;
                    var9_27 = var5_15.f[var10_30];
                    if (var9_27 == null) {
                        v3 = null;
                    } else {
                        var12_32 = var9_27.c(0);
                        if (var12_32 == var11_31) {
                            v3 = null;
                        } else {
                            var13_33 = new StringBuffer();
                            var13_33.append(var9_27.p);
                            var13_33.append(a.f.o[41]);
                            var13_33.append(var9_27.c(0));
                            var13_33.append(a.f.o[37]);
                            var14_34 = null;
                            ++var11_31;
                            while (var11_31 <= var12_32) {
                                var14_34 = a.f.c((int)var9_27.e(var11_31));
                                if (var14_34 != null) {
                                    var13_33.append("\n");
                                    var13_33.append(a.f.o[65]);
                                    var13_33.append(var14_34.e);
                                }
                                ++var11_31;
                            }
                            v3 = var13_33.toString();
                        }
                    }
                    var7_20[var10_30] = v3;
                }
                if (var8_23 != null) {
                    for (var10_30 = 0; var10_30 < var8_23.length; ++var10_30) {
                        a.f.b(var8_23[var10_30], 1);
                    }
                }
                for (var10_30 = 0; var10_30 < 3; ++var10_30) {
                    if (var5_15.f[var10_30] == null) continue;
                    a.f.A[var10_30].l(var6_18);
                }
                var5_15 = var7_20;
                var2_10 /* !! */  = d.d.a(var2_9, var3_24, var4_28);
                var2_10 /* !! */  = b.b.g.a((String)var2_10 /* !! */ , 5000L);
                var1_2.v.addElement(var2_10 /* !! */ );
                for (var3_24 = 0; var3_24 < var5_15.length; ++var3_24) {
                    if (var5_15[var3_24] == null) continue;
                    var2_10 /* !! */  = b.b.g.a(var5_15[var3_24], 3000L);
                    var1_2.v.addElement(var2_10 /* !! */ );
                }
                var3_24 = super.r();
                var2_10 /* !! */  = null;
                for (var4_29 = 0; var4_29 < var1_2.f.length; ++var4_29) {
                    if (var1_2.f[var4_29] == null || (var2_10 /* !! */  = var1_2.f[var4_29].d(var3_24)) == null || (var2_10 /* !! */  = d.d.a(var1_2.f[var4_29], var2_10 /* !! */ )) == null) continue;
                    var2_10 /* !! */  = b.b.g.a((String)var2_10 /* !! */ , 3000L);
                    var1_2.v.addElement(var2_10 /* !! */ );
                }
                ** GOTO lbl122
lbl118:
                // 1 sources

                if (this.m()) {
                    var1_3 = b.b.g.a(a.f.o[67], 5000L);
                    var1_3.k();
                }
lbl122:
                // 6 sources

                for (var1_4 = 3; var1_4 < this.f.length; ++var1_4) {
                    if (this.f[var1_4] == null || this.f[var1_4].w()) continue;
                    a.f.a(-this.f[var1_4].n, 1);
                }
                this.Q = (byte)5;
                break;
            }
            case 1: {
                if (this.v.isEmpty()) {
                    if (this.m()) {
                        var1_5 = this;
                        if (!(this.G != null ? (var1_5.G.m & 2) != 0 : var1_5.S.c())) {
                            if (d.d.e >= d.d.u) {
                                this.i = new b();
                                break;
                            }
                            b.b.g.a("\u5168\u4f53\u590d\u6d3b,\u5e76\u5168\u4f53\u589e\u52a0\u4e00\u500d\u653b\u51fb\u529b,\u76f4\u5230\u6218\u6597\u7ed3\u675f!\u6bcf\u573a\u6218\u6597\u53ea\u80fd\u590d\u6d3b3\u6b21!", 3000L).k();
                            var1_5 = b.b.g.a(new String[]{"\u5168\u4f53\u590d\u6d3b", "\u8fd4\u56de"}, (byte)0, (byte)1, true);
                            var2_11 = var1_5.k();
                            if (var2_11 == 1 && var1_5.a().equals("\u5168\u4f53\u590d\u6d3b")) {
                                var1_5 = "\u662f\u5426\u82b1\u8d392\u5143\u5168\u4f53\u590d\u6d3b?\u8d60\u90012\u4e2a\u5143\u5b9d(\u7528\u4e8e\u6362\u9ad8\u7ea7\u88c5\u5907),\u8bf7\u8bb0\u5f97\u5b58\u6863!";
                                var2_12 = null;
                                var2_13 = false;
                                var2_13 = a.f.t() != false ? true : c.i.a((String)var1_5, 5, 0);
                                if (var2_13) {
                                    ++d.d.e;
                                    g.a.a(String.valueOf(5), 0);
                                    a.f.a(1001, 2);
                                    for (var1_6 = 0; var1_6 < this.f.length; ++var1_6) {
                                        if (this.f[var1_6] == null || this.f[var1_6].q == 8) continue;
                                        this.f[var1_6].Q();
                                        this.f[var1_6].a(8, -1);
                                    }
                                    var2_14 = "\u590d\u6d3b\u6210\u529f!";
                                    var1_7 = b.b.g.a("\u590d\u6d3b\u6210\u529f!", 3000L);
                                    var1_7.k();
                                    return 3;
                                }
                            }
                            this.i = new b();
                            break;
                        }
                    }
                    this.i = new c();
                    break;
                }
                var1_8 = (b.b.g)this.v.elementAt(0);
                var1_8.k();
                this.v.removeElementAt(0);
            }
        }
        return 5;
    }

    public final a.d[] c() {
        return this.g;
    }

    private void v() {
        int n2;
        for (n2 = this.f.length - 1; n2 >= 0; --n2) {
            if (this.f[n2] == null) continue;
            this.f[n2].j();
        }
        for (n2 = 0; n2 < this.g.length; ++n2) {
            if (this.g[n2] == null) continue;
            this.g[n2].j();
        }
        if (d.k.l.size() > 0) {
            a.b b2 = null;
            for (int i2 = 0; i2 < d.k.l.size(); ++i2) {
                b2 = (a.b)d.k.l.elementAt(i2);
                b2.j();
            }
        }
    }

    private void w() {
        int n2;
        this.x = this.f[0];
        for (n2 = 1; n2 < this.f.length; ++n2) {
            if (this.f[n2] == null) continue;
            this.x = a.b.a(this.x, this.f[n2]);
        }
        for (n2 = 0; n2 < this.g.length; ++n2) {
            if (this.g[n2] == null) continue;
            this.x = a.b.a(this.x, this.g[n2]);
        }
    }

    static void a(d d2) {
        d2.v();
    }

    static int a(d d2, int n2, a.g[] gArray, boolean n3) {
        int n4 = n3;
        a.g[] gArray2 = gArray;
        int n5 = n2;
        if (gArray2 != null) {
            for (n3 = 0; n3 < gArray2.length; ++n3) {
                if (gArray2[n5 = (n5 - 1 + gArray2.length) % gArray2.length] == null || gArray2[n5].w() && n4 == 0) continue;
                return n5;
            }
        }
        return -1;
    }

    static int b(d d2, int n2, a.g[] gArray, boolean n3) {
        int n4 = n3;
        a.g[] gArray2 = gArray;
        int n5 = n2;
        if (gArray2 != null) {
            for (n3 = 0; n3 < gArray2.length; ++n3) {
                if (gArray2[n5 = (n5 + 1) % gArray2.length] == null || gArray2[n5].w() && n4 == 0) continue;
                return n5;
            }
        }
        return -1;
    }

    static a.g b(d d2) {
        return d2.J;
    }

    static void a(d d2, a.g g2, f.c c2) {
        v0.N = c2.h;
        d2.O = c2.g;
        d2.M = 1;
        d2.K = g2;
        d2.L = c2;
        d2.a(d2.J, d2.K, c2);
        d2.Q = 1;
    }

    static b.b.g c(d d2) {
        return d2.I;
    }

    static void a(d d2, int n2, f.b object) {
        Stack stack = d.k.f();
        Object object2 = null;
        for (int i2 = 0; i2 < stack.size(); ++i2) {
            object2 = (b.b.c)stack.elementAt(i2);
            ((b.b.c)object2).c();
        }
        d2.J.b((byte)6, (int)(-((f.b)object).v));
        a.d d3 = a.f.d(-((f.a)object).c);
        if (d3 != null) {
            a.f.a((f.a)object, 1);
            if (d2.f[n2] != null) {
                d2.H.remove(d2.f[n2]);
                if (!d2.f[n2].w()) {
                    a.f.a(-d2.f[n2].n, 1);
                }
            }
            d2.f[n2] = d3;
            d2.f[n2].w = true;
            d2.f[n2].i();
            d2.f[n2].a(-1, -1);
            d2.f[n2].s = m[n2][0];
            d2.f[n2].t = m[n2][1];
            d2.H.put(d2.f[n2], new Vector());
            int n3 = n2;
            object = d2.J;
            d d4 = d2;
            if (object != null) {
                object2 = null;
                if (d4.f[n3] != null && !d4.f[n3].w()) {
                    object2 = b.f.b.a(d4.f[n3], -1, -1, -1, null, d4.f[n3].w);
                    d4.a((a.b)object, (b.f.b)object2);
                }
                object2 = b.f.b.a((a.b)object, 17, 0, 8, null, ((a.b)object).w);
                d4.a((a.b)object, (b.f.b)object2);
                object2 = d.d.a((a.g)object);
                d4.a((a.b)object, (b.f.b)object2);
                object2 = b.f.b.a("r1002", m[n3][0], (short)(m[n3][1] + 25), 2);
                d4.a((a.b)object, (b.f.b)object2);
                object2 = b.f.b.a(d4.f[n3], 0, -1, -1, null, d4.f[n3].w);
                d4.a((a.b)object, (b.f.b)object2);
            }
            d2.Q = 1;
        }
    }

    static void a(d d2, a.g object, b.d.a a2) {
        block5: {
            b.f.b b2;
            Object object2;
            a.g g2;
            block7: {
                Object object3;
                Object object4;
                a.g[] gArray;
                block6: {
                    v0.N = a2.a.h;
                    d2.O = a2.a.g;
                    d2.M = (byte)3;
                    d2.K = object;
                    gArray = a2.a;
                    object4 = d2.N == 1 ? d2.g : d2.f;
                    object3 = d2.K;
                    g2 = d2.J;
                    object = d2;
                    if (g2 == null || gArray == null || gArray.g == 0 && object4 == null || gArray.g == 1 && object3 == null) break block5;
                    object2 = null;
                    object2 = b.f.b.a(g2, 17, 0, 8, null, g2.w);
                    super.a((a.b)g2, (b.f.b)object2);
                    object2 = d.d.a(g2);
                    super.a((a.b)g2, (b.f.b)object2);
                    object2 = b.f.b.a((f.a)gArray, g2, (a.g)object3, object4);
                    super.a((a.b)g2, (b.f.b)object2);
                    object2 = gArray;
                    gArray = object4;
                    object4 = object3;
                    object3 = object;
                    if (object2 != null && (((f.a)object2).g != 0 || gArray != null) && (((f.a)object2).g != 1 || object4 != null)) break block6;
                    b2 = null;
                    break block7;
                }
                b.f.b[][] bArray = null;
                switch (((f.a)object2).g) {
                    case 1: {
                        b.f.b[][] bArray2 = new b.f.b[1][];
                        bArray = bArray2;
                        bArray2[0] = super.a((a.g)object4, (f.a)object2);
                        break;
                    }
                    case 0: {
                        bArray = new b.f.b[gArray.length][];
                        for (int i2 = 0; i2 < gArray.length; ++i2) {
                            if (gArray[i2] == null || gArray[i2].w() && !((f.a)object2).a()) continue;
                            bArray[i2] = super.a(gArray[i2], (f.a)object2);
                        }
                        break;
                    }
                    default: {
                        b2 = null;
                        break block7;
                    }
                }
                b2 = b.f.b.a(bArray);
            }
            object2 = b2;
            super.a((a.b)g2, (b.f.b)object2);
        }
        a.f.d(a2.b, 1);
        d2.Q = 1;
    }

    static void b(d d2, a.g object, f.c object2) {
        d2.J.a((byte)14, 0);
        d2.N = 1;
        d2.O = ((f.a)object2).g;
        d2.M = (byte)2;
        d2.K = object;
        f.c c2 = object2;
        a.g[] gArray = d2.N == 1 ? d2.g : d2.f;
        a.g g2 = object;
        object2 = d2.J;
        object = d2;
        if (!(object2 == null || c2 == null || c2.g == 0 && gArray == null || c2.g == 1 && g2 == null)) {
            int n2;
            int n3 = ((a.b)object2).s;
            int n4 = ((a.b)object2).t;
            Object object3 = null;
            if (!a.f.l[1]) {
                object3 = b.f.b.a((a.b)object2, 13, -1, -1, null, ((a.b)object2).w);
                super.a((a.b)object2, (b.f.b)object3);
                short s = 0;
                n2 = 0;
                switch (c2.g) {
                    case 0: {
                        s = (short)main.a.f;
                        n2 = (short)main.a.g;
                        break;
                    }
                    case 1: {
                        s = (short)(((d)object).K.s + ((d)object).K.u / 2 + ((a.b)object2).u / 2);
                        n2 = (short)((d)object).K.t;
                    }
                }
                object3 = b.f.b.a((int)s, n2 + 1, (a.b)object2, 8, 2);
                super.a((a.b)object2, (b.f.b)object3);
            }
            object3 = b.f.b.a((a.b)object2, c2.i[22], 0, 8, null, ((a.b)object2).w);
            super.a((a.b)object2, (b.f.b)object3);
            object3 = b.f.b.a(c2, (a.g)object2, g2, gArray);
            super.a((a.b)object2, (b.f.b)object3);
            object3 = null;
            switch (c2.g) {
                case 0: {
                    object3 = new b.f.b[gArray.length][];
                    for (n2 = 0; n2 < gArray.length; ++n2) {
                        object3[n2] = super.a(c2, (a.g)object2, gArray[n2]);
                    }
                    break;
                }
                case 1: {
                    b.f.b[][] bArrayArray = new b.f.b[1][];
                    object3 = bArrayArray;
                    bArrayArray[0] = super.a(c2, (a.g)object2, g2);
                }
            }
            object3 = b.f.b.a(object3);
            super.a((a.b)object2, (b.f.b)object3);
            if (!a.f.l[1]) {
                object3 = b.f.b.a((a.b)object2, 14, -1, -1, null, ((a.b)object2).w);
                super.a((a.b)object2, (b.f.b)object3);
                object3 = b.f.b.a((int)((short)n3), (short)n4, (a.b)object2, 8, 2);
                super.a((a.b)object2, (b.f.b)object3);
            }
            object3 = d.d.a((a.g)object2);
            super.a((a.b)object2, (b.f.b)object3);
        }
        d2.Q = 1;
    }

    static {
        byte[] byArray = new byte[]{1, 2, 5};
        m = new short[][]{{209, 138}, {181, 170}, {209, 200}, {141, 140}, {141, 190}};
        n = new short[][]{{54, 138}, {81, 185}, {41, 215}};
        a = null;
        b = null;
        c = null;
        d = null;
        u = 3;
        e = 0;
    }
}

