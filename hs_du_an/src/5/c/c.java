/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package c;

import a.a.o;
import b.e;
import c.a;
import c.b;
import c.d;
import c.f;
import c.g;
import c.h;
import c.k;
import e.l;
import e.n;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class c
extends a
implements a.a.h {
    private static final short[][] m;
    private static final short[][] n;
    private Image o = null;
    private Image p = null;
    private Image q = null;
    private Image r = null;
    private Image s = null;
    private Image t = null;
    public static Image f;
    public static Image[] g;
    public static Image h;
    public static Image i;
    private static int u;
    public static int j;
    private Vector v = new Vector();
    public b.f[] k = null;
    private int[][] w = null;
    public b.c[] l = null;
    private b.b x = null;
    private b.b y = null;
    private int z = 0;
    private int[] A = null;
    private int B = -1;
    private int C = -1;
    private int D = -1;
    private a.e.c[] E = null;
    private int[][] F = null;
    private a.d.b G = null;
    private Hashtable H = null;
    private a.a.l I = null;
    private b.f J = null;
    private b.f K = null;
    private f.c L = null;
    private byte M = 0;
    private byte N = 1;
    private byte O = 1;
    private boolean P = true;
    private byte Q = (byte)3;
    private int R = -1;
    private e S = null;
    private boolean T = false;
    private byte U = (byte)3;
    private int V = 0;
    private int W = 0;

    public static c a(a.d.b b2) {
        c c2 = new c();
        new c().G = b2;
        return c2;
    }

    public static c a(e e2) {
        c c2 = new c();
        new c().S = e2;
        return c2;
    }

    private c() {
        super((byte)4);
    }

    public final void a() {
        j = 0;
        this.c = true;
        b.a.a();
        Object object = b.a.w.i();
        if (object != null) {
            this.F = new int[((e[])object).length][3];
            for (int i2 = 0; i2 < this.F.length; ++i2) {
                this.F[i2][0] = object[i2].e();
                this.F[i2][1] = object[i2].d();
                this.F[i2][2] = object[i2].f != null && object[i2].f.h() ? 1 : 0;
            }
        }
        b.a.k();
        b.a.w.k();
        System.gc();
        a.c.b.a(100L);
        super.a(10);
        main.a.a.a();
        long l2 = System.currentTimeMillis();
        this.H = new Hashtable();
        object = this;
        a.c.d d2 = a.c.d.a("/b.pak");
        if (object.G != null) {
            object.o = d2.c(object.G.k + "_t.png");
            object.p = d2.c(object.G.l + "_b.png");
        } else {
            object.o = d2.c(object.S.m() + "_t.png");
            object.p = d2.c(object.S.n() + "_b.png");
        }
        if (object.o == null || object.p == null) {
            object.p = d2.c("0_b.png");
            object.o = d2.c("0_t.png");
        }
        object.q = d2.c("bg.png");
        f = d2.c("status.png");
        Image[] imageArray = new Image[3];
        g = imageArray;
        imageArray[0] = d2.c("a.png");
        c.c.g[1] = d2.c("d.png");
        c.c.g[2] = d2.c("s.png");
        h = d2.c("up.png");
        i = d2.c("down.png");
        object.s = d2.c("miss.png");
        object.r = d2.c("statusBorder.png");
        object.t = d2.c("angry.png");
        super.a(20);
        main.a.a.a();
        this.A = new int[2];
        this.A[0] = b.a.A[0].n;
        this.A[1] = b.a.A[0].o;
        this.B = b.a.A[0].g;
        this.C = b.a.A[0].d();
        this.D = b.a.A[0].e();
        this.E = new a.e.c[b.a.A.length];
        for (int i3 = 0; i3 < this.E.length; ++i3) {
            this.E[i3] = b.a.A[i3].e;
        }
        this.t();
        this.j();
        this.w();
        this.R = b.a.ao;
        if (this.G != null && this.G.j >= 0) {
            b.a.a((int)this.G.j, -1, (int)b.a.m);
        } else if (this.S != null) {
            b.a.a(this.S.l(), -1, (int)b.a.m);
        }
        super.a(100);
        main.a.a.a();
        this.c = false;
        long l3 = 1500L - (System.currentTimeMillis() - l2);
        a.c.b.a(l3 <= 0L ? 100L : l3);
    }

    public final void b() {
        e[] eArray;
        int n2;
        j = 0;
        super.b();
        this.c = true;
        b.a.a();
        super.a(0);
        Object object = this;
        this.p = null;
        ((c)object).o = null;
        ((c)object).q = null;
        f = null;
        g = null;
        g = null;
        h = null;
        i = null;
        ((c)object).t = null;
        ((c)object).s = null;
        ((c)object).r = null;
        this.H.clear();
        this.H = null;
        this.I = null;
        object = this;
        for (int i2 = 0; i2 < b.a.A.length; ++i2) {
            if (((c)object).k[i2].w()) {
                ((c)object).k[i2].a((byte)2, 1);
            }
            ((c)object).k[i2].F();
            ((c)object).k[i2].c((short)0);
            ((c)object).k[i2].e = ((c)object).E[i2];
            ((c)object).k[i2].a(((c)object).B, ((c)object).D);
            ((c)object).k[i2].a(((c)object).C);
            b.a.A[i2].n = ((c)object).A[0];
            b.a.A[i2].o = ((c)object).A[1];
        }
        b.a.w.a(b.a.A[0]);
        object = null;
        if (this.k != null) {
            for (n2 = 0; n2 < this.k.length; ++n2) {
                object = this.k[n2];
                if (object == null) continue;
                ((b.b)object).t = null;
                ((b.b)object).u = null;
            }
        }
        this.k = null;
        for (n2 = 0; n2 < this.l.length; ++n2) {
            if (this.l[n2] == null) continue;
            this.l[n2].k();
        }
        this.l = null;
        super.a(30);
        main.a.a.a();
        long l2 = System.currentTimeMillis();
        b.a.w.l();
        object = this;
        if (b.a.w != null && (eArray = b.a.w.i()) != null && ((c)object).F != null && eArray.length == ((c)object).F.length) {
            for (int i3 = 0; i3 < eArray.length; ++i3) {
                eArray[i3].a(eArray[i3].g, ((c)object).F[i3][0]);
                eArray[i3].a(((c)object).F[i3][1]);
                if (((c)object).F[i3][2] != 1 || eArray[i3].f == null) continue;
                eArray[i3].f.a(true);
            }
        }
        super.a(60);
        main.a.a.a();
        super.a(100);
        main.a.a.a();
        if (this.S != null) {
            this.S.A = true;
            this.S.a = false;
            this.S.b = true;
            this.S.h();
        }
        if (this.G != null) {
            this.G.c();
        }
        b.a.a(this.R, -1, (int)b.a.m);
        long l3 = 1500L - (System.currentTimeMillis() - l2);
        a.c.b.a(l3 <= 0L ? 100L : l3);
        this.c = false;
        b.a.X = null;
        b.a.Y = 0;
    }

    private void j() {
        this.l = new b.c[3];
        if (this.G != null) {
            this.l[0] = this.G.g == 0 ? null : b.a.d(this.G.g);
            this.l[1] = this.G.h == 0 ? null : b.a.d(this.G.h);
            this.l[2] = this.G.i == 0 ? null : b.a.d(this.G.i);
        } else {
            this.l[0] = this.S.x == 0 ? null : b.a.d(this.S.x);
            this.l[1] = this.S.y == 0 ? null : b.a.d(this.S.y);
            this.l[2] = this.S.z == 0 ? null : b.a.d(this.S.z);
        }
        int n2 = 0;
        int n3 = 0;
        for (int i2 = 0; i2 < this.l.length; ++i2) {
            if (this.l[i2] == null) continue;
            n3 += this.l[i2].c(0);
            ++n2;
            this.H.put(this.l[i2], new Vector());
            this.l[i2].a();
            this.l[i2].a(0, -1);
            this.l[i2].n = n[i2][0];
            this.l[i2].o = n[i2][1];
            this.l[i2].s();
        }
        this.z = n3 / n2;
    }

    public final void a(Graphics graphics) {
        c.a.c(graphics);
        if (this.c) {
            c.a.a(graphics, this.d);
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
            n4 = main.a.d / 3;
            n5 = main.a.d / 3;
        }
        for (int i2 = 0; i2 < 3; ++i2) {
            n2 = i2 * n4;
            n3 = main.a.e - n5;
            int[] nArray = c2.w[i2];
            b.f f2 = c2.k[i2];
            int n6 = n3;
            int n7 = n2;
            Graphics graphics3 = graphics2;
            c c3 = c2;
            if (c3.q != null) {
                graphics3.drawImage(c3.q, n7, n6, 20);
            } else {
                graphics3.setColor(15454341);
                graphics3.fillRect(n7, n6, main.a.d / 3, main.a.d / 3);
            }
            if (f2 == null || nArray == null || nArray.length != 3) continue;
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
            c.c.a(graphics3, 35, n8, n9 += 9, f2.q(), b.a.h);
            graphics3.drawImage(b.a.O, n7 + 6, n6 + 23, 20);
            n8 = n7 + 29;
            n9 = n6 + 23;
            n11 = f2.V() * 100 / nArray[0];
            a.c.b.a(graphics3, b.a.D, f2.c(2), n8, n9);
            c.c.a(graphics3, 35, n8 + 10, n9 += b.a.D.getHeight(), n11, b.a.e);
            n11 = f2.W() * 100 / nArray[1];
            a.c.b.a(graphics3, b.a.D, f2.c(4), n8, n9 += 2 + b.a.e.length);
            c.c.a(graphics3, 35, n8 + 10, n9 += b.a.D.getHeight(), n11, b.a.f);
            n11 = f2.X() * 100 / nArray[2];
            a.c.b.a(graphics3, b.a.D, f2.c(6), n8, n9 += 2 + b.a.f.length);
            c.c.a(graphics3, 35, n8 + 10, n9 += b.a.D.getHeight(), n11, b.a.g);
            n8 = n7 + 80 - 12;
            f2.a(graphics3, n8, n6 + 21 + 5, (short)20);
        }
        graphics2 = graphics;
        c2 = this;
        for (n2 = 3; n2 < 5; ++n2) {
            if (c2.k[n2] == null) continue;
            c2.k[n2].a(graphics2, c2.k[n2].n, c2.k[n2].o);
        }
        b.b b2 = c2.x;
        while (b2 != null) {
            b2.a(graphics2, b2.n, b2.o);
            b2 = b2.u;
        }
        graphics2 = graphics;
        c2 = this;
        if (c2.U != 1) {
            int n23;
            for (n23 = 0; n23 < c2.l.length; ++n23) {
                if (c2.l[n23] == null || c2.l[n23].w() || c2.l[n23].f == null) continue;
                c2.a(graphics2, c2.l[n23], c2.l[n23].n + c2.l[n23].p / 2, c2.l[n23].o - c2.l[n23].q, false);
            }
            for (n23 = 0; n23 < c2.k.length; ++n23) {
                if (c2.k[n23] == null || c2.k[n23].w() || c2.k[n23].f == null) continue;
                c2.a(graphics2, c2.k[n23], c2.k[n23].n - c2.k[n23].p / 2 - c2.r.getWidth() / 2, c2.k[n23].o - c2.k[n23].q * 3 / 4, true);
            }
        }
        if (this.J != null) {
            a.c.a.a(graphics, 0, this.J.k, main.a.d, 0, 24);
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
    public final void d(a.a.a var1_2) {
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
                                                                                if (var1_2 != this.I) ** GOTO lbl196
                                                                                var1_2 = this.I.c();
                                                                                if (!var1_2.equals("\u5546\u5e97")) break block14;
                                                                                var0_1 = (main.a.d - main.a.h) / 2;
                                                                                var1_3 = (main.a.e - main.a.i) / 2;
                                                                                var2_6 = new o(var0_1, var1_3, main.a.h, main.a.i);
                                                                                var1_4 = new e.f(e.l.i);
                                                                                var2_6.a(var1_4);
                                                                                var2_6.k();
                                                                                break block15;
                                                                            }
                                                                            if (!var1_2.equals(b.a.o[16])) break block16;
                                                                            v0.I.g = false;
                                                                            this.O = 1;
                                                                            this.N = 1;
                                                                            var1_2 = new b((c)this, this.l, this.N, this.O, false);
                                                                            if (var1_2.k() != 1) break block17;
                                                                            var1_2 = var1_2.h();
                                                                            var2_7 = this;
                                                                            this.N = 1;
                                                                            var2_7.O = 1;
                                                                            var2_7.M = 0;
                                                                            var2_7.K = var1_2;
                                                                            var2_7.a(var2_7.J, var2_7.K);
                                                                            var2_7.Q = 1;
                                                                            this.I.b();
                                                                            ** GOTO lbl195
                                                                        }
                                                                        this.I.g = true;
                                                                        break block15;
                                                                    }
                                                                    if (!var1_2.equals(b.a.o[17])) break block18;
                                                                    if (!this.J.L()) break block19;
                                                                    a.a.l.a(b.a.o[61], 3000L).k();
                                                                    break block15;
                                                                }
                                                                if (this.J.q() == 100) break block20;
                                                                a.a.l.a(b.a.o[62], 2000L).k();
                                                                break block15;
                                                            }
                                                            v1.I.g = false;
                                                            var1_2 = new a.a.k(main.a.d / 10, main.a.e / 8, (main.a.d << 2) / 5, main.a.e * 3 / 4);
                                                            var2_8 = new d((c)this, this.J);
                                                            var1_2.a(var2_8);
                                                            if (var1_2.k() != 2) break block21;
                                                            this.I.g = true;
                                                            ** GOTO lbl195
                                                        }
                                                        this.I.b();
                                                        break block15;
                                                    }
                                                    if (!var1_2.equals(b.a.o[18])) break block22;
                                                    var1_2 = null;
                                                    if (!this.u()) break block23;
                                                    var1_2 = this.J;
                                                    var2_9 = this;
                                                    if (var1_2 == null) ** GOTO lbl-1000
                                                    var3_14 = 0;
                                                    var4_18 = var1_2.c(0);
                                                    var3_14 = var4_18 >= var2_9.z ? 100 - 100 * (25 / (var4_18 - var2_9.z + 50)) : 2500 / (50 - var4_18 + var2_9.z);
                                                    if (a.c.b.c(100) < var3_14) {
                                                        v2 = true;
                                                    } else lbl-1000:
                                                    // 2 sources

                                                    {
                                                        v2 = false;
                                                    }
                                                    if (v2) {
                                                        var1_2 = a.a.l.a(b.a.o[27], 3000L);
                                                        this.Q = (byte)2;
                                                        this.J.s();
                                                    } else {
                                                        var1_2 = a.a.l.a(b.a.o[63], 3000L);
                                                        this.Q = 1;
                                                    }
                                                    var1_2.k();
                                                    this.I.b();
                                                    ** GOTO lbl195
                                                }
                                                var1_2 = a.a.l.a(b.a.o[64], 3000L);
                                                var1_2.k();
                                                break block15;
                                            }
                                            if (!var1_2.equals(b.a.o[7])) break block24;
                                            if (!this.J.L()) break block25;
                                            this = a.a.l.a(b.a.o[71], 3000L);
                                            this.k();
                                            break block15;
                                        }
                                        v3.I.g = false;
                                        var1_2 = new a.a.k(main.a.d / 10, main.a.e / 8, (main.a.d << 2) / 5, main.a.e * 3 / 4);
                                        var2_10 = new f((c)this, this.J, false);
                                        var1_2.a(var2_10);
                                        if (var1_2.k() != 2) break block26;
                                        this.I.g = true;
                                        ** GOTO lbl195
                                    }
                                    this.I.b();
                                    break block15;
                                }
                                if (!var1_2.equals(b.a.o[60])) break block27;
                                this.J.a(9, 0);
                                this.Q = 1;
                                this.J.M = true;
                                ** GOTO lbl-1000
                            }
                            if (!var1_2.equals(b.a.o[8])) break block28;
                            v4.I.g = false;
                            var1_2 = new a.a.k(main.a.d / 10, main.a.e / 8, (main.a.d << 2) / 5, main.a.e * 3 / 4);
                            var2_11 = new k((c)this, 1);
                            var1_2.a(var2_11);
                            var2_11 = new k((c)this, 2);
                            var1_2.a(var2_11);
                            var2_11 = new k((c)this, 5);
                            var1_2.a(var2_11);
                            if (var1_2.k() != 2) break block29;
                            this.I.g = true;
                            ** GOTO lbl195
                        }
                        this.I.b();
                        break block15;
                    }
                    if (!var1_2.equals(b.a.k(77))) break block30;
                    v5.I.g = false;
                    var1_2 = new b((c)this, this.l, 1, 1, false);
                    if (var1_2.k() != 1) break block31;
                    var1_2 = var1_2.h();
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
                            v6 = var6_21 < 0 || var7_23 >= 50 ? false : (var8_24 = ((50 + var6_21 * 2 / 100) * (50 - var7_23) << 1) / 100) >= (var9_26 = a.c.b.c(100));
                        }
                        if (v6) {
                            var3_15 = 1;
                            b.a.a(-var1_2.i, 1);
                        }
                    }
                    var7_23 = var3_15;
                    var6_22 = var1_2;
                    var5_20 = var2_12.J;
                    var4_19 = var2_12;
                    if (var5_20 != null && var6_22 != null) {
                        var8_25 = null;
                        var9_26 = 0;
                        var1_5 = var6_22.o - var6_22.q / 2;
                        var8_25 = a.d.b.a((b.b)var5_20, 17, 1, 8, null, var5_20.r);
                        super.a((b.b)var5_20, var8_25);
                        var8_25 = c.c.a((b.f)var5_20);
                        super.a((b.b)var5_20, var8_25);
                        if (var7_23 != 0) {
                            var3_16 = new a.d.b[2][1];
                            var3_16[0][0] = a.d.b.a((b.b)var6_22, 3, 0, -1, null, var6_22.r);
                            var3_16[1][0] = a.d.b.a(var5_20.n, var5_20.o, (b.b)var6_22, 2, 1);
                            var8_25 = a.d.b.a(var3_16);
                            super.a((b.b)var5_20, var8_25);
                            var8_25 = a.d.b.a(b.a.k(77) + b.a.k(78), (byte)0);
                            super.a((b.b)var5_20, var8_25);
                            var8_25 = a.d.b.a((b.b)var6_22, (byte)2, (short)0, (byte)1);
                            super.a((b.b)var5_20, var8_25);
                        } else {
                            var9_26 = var6_22.n - var4_19.s.getWidth() / 2;
                            var3_17 = b.d.a(var4_19.s, var9_26, var1_5);
                            c.a.a(var3_17);
                            var8_25 = a.d.b.a(var9_26, var1_5 - var6_22.q / 2, var3_17, 2, 1);
                            super.a((b.b)var5_20, var8_25);
                            var8_25 = a.d.b.a(b.a.k(77) + b.a.k(79), (byte)0);
                            super.a((b.b)var5_20, var8_25);
                        }
                    }
                    var2_12.Q = 1;
                    this.I.b();
                    ** GOTO lbl195
                }
                this.I.g = true;
                break block15;
            }
            if (var1_2.equals(b.a.k(25))) {
                v7.I.g = false;
                var1_2 = new a.a.k(main.a.d / 10, main.a.e / 8, (main.a.d << 2) / 5, main.a.e * 3 / 4);
                var2_13 = new f((c)this, this.J, true);
                var1_2.a(var2_13);
                ** if (var1_2.k() != 2) goto lbl-1000
lbl-1000:
                // 1 sources

                {
                    this.I.g = true;
                    ** GOTO lbl197
                }
            }
            ** GOTO lbl195
lbl-1000:
            // 2 sources

            {
                this.I.b();
lbl195:
                // 8 sources

                return;
lbl196:
                // 1 sources

                var1_2.b();
            }
        }
    }

    public final void c(a.a.a a2) {
        c.a.b(a2);
    }

    private a.d.b[] a(b.f f2, f.b b2) {
        int n2;
        if (b2 == null || f2 == null) {
            return null;
        }
        Vector vector = new Vector();
        Object object = null;
        if (b2.v == 1) {
            object = a.d.b.a(f2, 2, 0, 0, null, f2.r);
        } else {
            short s = (short)f2.n;
            n2 = f2.o;
            object = a.d.b.a("r576", s, (short)n2, 0);
        }
        vector.addElement(object);
        object = b2.a(null, f2);
        if (object != null) {
            for (n2 = 0; n2 < ((b.b[])object).length; ++n2) {
                c.a.a(object[n2]);
            }
            a.d.b[][] bArray = new a.d.b[((b.b[])object).length][1];
            for (int i2 = 0; i2 < bArray.length; ++i2) {
                bArray[i2][0] = a.d.b.a(object[i2].n, object[i2].o - f2.q / 2, object[i2], 2, 1);
            }
            object = a.d.b.a(bArray);
            vector.addElement(object);
        }
        object = c.c.a(f2);
        vector.addElement(object);
        Object[] objectArray = new a.d.b[vector.size()];
        vector.copyInto(objectArray);
        return objectArray;
    }

    private void a(b.f f2, b.f bArray, boolean n2) {
        if (f2 == null || bArray == null) {
            return;
        }
        int n3 = f2.n;
        int n4 = f2.o;
        boolean bl = false;
        int n5 = 0;
        this.T = a.c.b.c(100) > 95 - (f2.J() ? 75 : 0);
        Object var6_8 = null;
        short s = 0;
        if (!b.a.l[1]) {
            s = n2 != 0 ? (short)(bArray.n + bArray.p / 2 + f2.p / 2) : (short)(bArray.n - bArray.p / 2 - f2.p / 2);
            a.d.b b2 = a.d.b.a((int)s, bArray.o + 1, f2, 999, 2);
            this.a((b.b)f2, b2);
        }
        if (this.T) {
            if (n2 != 0) {
                a.d.b b3 = a.d.b.a(bArray.n - bArray.p / 2, bArray.o, (b.b)bArray, 4, 2);
                this.a((b.b)f2, b3);
            } else {
                a.d.b b4 = a.d.b.a(bArray.n + bArray.p / 2, bArray.o, (b.b)bArray, 4, 2);
                this.a((b.b)f2, b4);
            }
        }
        a.d.b b5 = a.d.b.a(f2, 1, 0, 0, null, f2.r);
        this.a((b.b)f2, b5);
        n5 = bArray.o - bArray.q / 2;
        if (!this.T) {
            n2 = f2.a((b.f)bArray, false);
            b5 = a.d.b.a((b.b)bArray, (byte)2, (short)(-n2), (byte)0);
            this.a((b.b)f2, b5);
            int n6 = n2 * 100 / this.K.B();
            a.d.b b6 = a.d.b.a((b.b)bArray, (byte)14, (short)(n6 <= 0 ? 1 : n6), (byte)0);
            this.a((b.b)f2, b6);
            b6 = b.a.Q;
            int n7 = bArray.n - String.valueOf(n2).length() * b6.getWidth() / 10 / 2;
            b.b[] bArray2 = new b.b[1];
            b.b[] bArray3 = bArray2;
            bArray2[0] = b.d.a(n2, n7, n5, b.a.Q);
            c.a.a(bArray3[0]);
            bArray = this.a(bArray3, (b.f)bArray);
            if (bArray != null) {
                for (n2 = 0; n2 < bArray.length; ++n2) {
                    this.a((b.b)f2, bArray[n2]);
                }
            }
        } else {
            int n8 = bArray.n - this.s.getWidth() / 2;
            b.d d2 = b.d.a(this.s, n8, n5);
            c.a.a(d2);
            a.d.b b7 = a.d.b.a(n8, n5 - bArray.q / 2, d2, 2, 1);
            this.a((b.b)f2, b7);
            b7 = a.d.b.a(bArray.n, bArray.o, (b.b)bArray, 4, 2);
            this.a((b.b)f2, b7);
        }
        if (!b.a.l[1]) {
            a.d.b b8 = a.d.b.a((int)((short)n3), (short)n4, f2, 999, 2);
            this.a((b.b)f2, b8);
        }
        a.d.b b9 = a.d.b.a(f2, 0, -1, -1, null, f2.r);
        this.a((b.b)f2, b9);
    }

    private a.d.b[] a(b.b[] bArray, b.f f2) {
        if (f2 == null || bArray == null) {
            return null;
        }
        Vector vector = new Vector();
        Object object = null;
        object = new a.d.b[bArray.length][1];
        for (int i2 = 0; i2 < bArray.length; ++i2) {
            object[i2][0] = a.d.b.a(bArray[i2].n, bArray[i2].o - 20, bArray[i2], 2, 1);
        }
        object = a.d.b.a(object);
        vector.addElement(object);
        object = c.c.a(f2);
        vector.addElement(object);
        Object[] objectArray = new a.d.b[vector.size()];
        vector.copyInto(objectArray);
        return objectArray;
    }

    private static a.d.b a(b.f object) {
        if (object == null) {
            return null;
        }
        boolean bl = ((b.b)object).l == 8;
        a.d.b[] bArray = new a.d.b[3];
        a.d.b[] bArray2 = bArray;
        bArray[0] = a.d.b.a((b.f)object, (byte)2, (byte)2, 1, null);
        bArray2[0].p = new a.d.b[2];
        bArray2[0].p[0] = bl ? a.d.b.a((b.b)object, 3, 0, -1, null, ((b.b)object).r) : a.d.b.a((b.b)object, 11, -1, -1, null, ((b.b)object).r);
        bArray2[0].p[1] = a.d.b.a(bArray2[0]);
        bArray2[1] = a.d.b.a((b.f)object, (byte)2, (byte)2, (int)((short)(((b.f)object).B() / 5)), null);
        bArray2[1].p = new a.d.b[2];
        bArray2[1].p[0] = bl ? a.d.b.a((b.b)object, 0, -1, -1, null, ((b.b)object).r) : a.d.b.a((b.b)object, 10, -1, -1, null, ((b.b)object).r);
        bArray2[1].p[1] = a.d.b.a(bArray2[1]);
        bArray2[2] = a.d.b.a((b.f)object, (byte)2, (byte)0, (int)((short)(((b.f)object).B() / 5 - 1)), null);
        bArray2[2].p = new a.d.b[2];
        bArray2[2].p[0] = bl ? a.d.b.a((b.b)object, 0, -1, -1, null, ((b.b)object).r) : a.d.b.a((b.b)object, 8, -1, -1, null, ((b.b)object).r);
        bArray2[2].p[1] = a.d.b.a(bArray2[2]);
        object = a.d.b.a(bArray2);
        return object;
    }

    private void a(b.f f2, b.f f3) {
        if (f2 == null || f3 == null) {
            return;
        }
        int n2 = f2.n;
        int n3 = f2.o;
        this.T = a.c.b.c(100) > 95 - (f2.J() ? 75 : 0);
        int n4 = a.c.b.c(100) > 95 ? 1 : 0;
        Object object = null;
        if (n4 != 0 && !this.T) {
            object = b.d.a(this.t, f2.n - f2.p / 2, f2.o - f2.q / 2);
            c.a.a((b.b)object);
            object = a.d.b.a(((b.b)object).n, ((b.b)object).o - f2.q, (b.b)object, 2, 1);
            this.a((b.b)f2, (a.d.b)object);
        }
        if (!b.a.l[1]) {
            object = a.d.b.a(f2, 13, -1, -1, null, f2.r);
            this.a((b.b)f2, (a.d.b)object);
            short s = (short)(f3.n + f3.p / 2 + f2.p / 2);
            object = a.d.b.a((int)s, f3.o + 1, f2, 8, 2);
            this.a((b.b)f2, (a.d.b)object);
        }
        if (this.T) {
            object = a.d.b.a(f3.n - f3.p / 2, f3.o, f3, 4, 2);
            this.a((b.b)f2, (a.d.b)object);
        }
        if (this.T || n4 == 0) {
            object = a.d.b.a(f2, 15, 0, 8, null, f2.r);
            this.a((b.b)f2, (a.d.b)object);
        } else {
            object = a.d.b.a(f2, 21, 0, 8, null, f2.r);
            this.a((b.b)f2, (a.d.b)object);
        }
        int n5 = 0;
        int n6 = f3.o - f3.q / 2;
        if (this.T) {
            n5 = f3.n - this.s.getWidth() / 2;
            b.d d2 = b.d.a(this.s, n5, n6);
            c.a.a(d2);
            a.d.b b2 = a.d.b.a(n5, n6 - 20, d2, 2, 1);
            this.a((b.b)f2, b2);
            b2 = a.d.b.a(f3.n, f3.o, f3, 4, 2);
            this.a((b.b)f2, b2);
        } else {
            n4 = f2.a(f3, n4 != 0);
            a.d.b b3 = a.d.b.a(f3, (byte)2, (short)(-n4), (byte)0);
            this.a((b.b)f2, b3);
            b.b[] bArray = new b.b[1];
            Image image = b.a.Q;
            int n7 = f3.n - String.valueOf(n4).length() * image.getWidth() / 20;
            bArray[0] = b.d.a(n4, n7, n6, image);
            for (n4 = 0; n4 < bArray.length; ++n4) {
                c.a.a(bArray[n4]);
            }
            a.d.b[] bArray2 = this.a(bArray, f3);
            if (bArray2 != null) {
                for (int i2 = 0; i2 < bArray2.length; ++i2) {
                    this.a((b.b)f2, bArray2[i2]);
                }
            }
        }
        if (!b.a.l[1]) {
            a.d.b b4 = a.d.b.a(f2, 14, -1, -1, null, f2.r);
            this.a((b.b)f2, b4);
            b4 = a.d.b.a((int)((short)n2), (short)n3, f2, 8, 2);
            this.a((b.b)f2, b4);
        }
        a.d.b b5 = c.c.a(f2);
        this.a((b.b)f2, b5);
    }

    private void a(b.f f2, b.f f3, f.c c2) {
        if (f2 == null || c2 == null || c2.u == 1 && f3 == null) {
            return;
        }
        a.d.b b2 = null;
        b2 = f2.l == 8 ? a.d.b.a(f2, 1, 0, 0, null, f2.r) : a.d.b.a(f2, 16, -1, 8, null, f2.r);
        this.a((b.b)f2, b2);
        short s = 0;
        short s2 = 0;
        switch (c2.u) {
            case 0: {
                s = (short)(main.a.d / 3);
                s2 = (short)((main.a.e - this.q.getHeight() << 2) / 5);
                if (c2.v != 0) break;
                s = (short)(s + main.a.d / 3);
                break;
            }
            case 1: {
                s = (short)f3.n;
                s2 = (short)f3.o;
            }
        }
        this.y = new n("r" + c2.a[22]);
        this.y.n = s;
        this.y.o = s2;
        this.y.a();
        this.y.a = true;
        this.y.a(-1, -1);
        c.a.a(this.y);
        Object object = a.d.b.a(this.y, 0, 0, -1, null, this.y.r);
        this.a((b.b)f2, (a.d.b)object);
        b.f[] fArray = c2.v == 1 ? this.l : this.k;
        object = a.d.b.a(c2, f2, f3, fArray);
        this.a((b.b)f2, (a.d.b)object);
        object = null;
        switch (c2.u) {
            case 0: {
                object = new a.d.b[fArray.length][];
                for (int i2 = 0; i2 < fArray.length; ++i2) {
                    object[i2] = this.a(c2, f2, fArray[i2]);
                }
                break;
            }
            case 1: {
                a.d.b[][] bArrayArray = new a.d.b[1][];
                object = bArrayArray;
                bArrayArray[0] = this.a(c2, f2, f3);
            }
        }
        object = a.d.b.a(object);
        this.a((b.b)f2, (a.d.b)object);
        if (f2.l == 6) {
            object = c.c.a(f2);
            this.a((b.b)f2, (a.d.b)object);
        }
    }

    private void a(b.c c2, b.f f2, f.c c3) {
        if (c2 == null || c3 == null) {
            return;
        }
        a.d.b b2 = null;
        b.f[] fArray = c3.v == 1 ? this.k : this.l;
        b2 = a.d.b.a(c2, 1, 0, 0, null, c2.r);
        this.a((b.b)c2, b2);
        this.y = new n("r" + c3.a[22]);
        this.y.a();
        this.y.a(-1, -1);
        this.y.a = true;
        c.a.a(this.y);
        short s = 0;
        short s2 = 0;
        boolean bl = false;
        switch (c3.u) {
            case 0: {
                s2 = (short)(main.a.e / 2);
                bl = true;
                if (c3.v == 0) {
                    s = (short)(main.a.d / 3);
                    break;
                }
                s = (short)((main.a.d << 1) / 3);
                break;
            }
            case 1: {
                s = (short)f2.n;
                s2 = (short)f2.o;
            }
        }
        this.y.n = s;
        this.y.o = s2;
        a.d.b[][] bArrayArray = a.d.b.a(this.y, 0, 0, -1, null, bl);
        this.a((b.b)c2, (a.d.b)bArrayArray);
        bArrayArray = a.d.b.a(c3, c2, f2, fArray);
        this.a((b.b)c2, (a.d.b)bArrayArray);
        bArrayArray = null;
        switch (c3.u) {
            case 0: {
                bArrayArray = new a.d.b[fArray.length][];
                for (int i2 = 0; i2 < fArray.length; ++i2) {
                    bArrayArray[i2] = this.a(c3, (b.f)c2, fArray[i2]);
                }
                break;
            }
            case 1: {
                a.d.b[][] bArrayArray2 = new a.d.b[1][];
                bArrayArray = bArrayArray2;
                bArrayArray2[0] = this.a(c3, (b.f)c2, f2);
            }
        }
        bArrayArray = a.d.b.a(bArrayArray);
        this.a((b.b)c2, (a.d.b)bArrayArray);
    }

    private a.d.b[] a(f.b bArray, b.f f2, b.f f3) {
        if (bArray == null || f3 == null || f3.w() && !bArray.a()) {
            return null;
        }
        if ((bArray = bArray.a(f2, f3)) == null) {
            return null;
        }
        for (int i2 = 0; i2 < bArray.length; ++i2) {
            if (bArray[i2] == null) continue;
            c.a.a(bArray[i2]);
        }
        return this.a(bArray, f3);
    }

    private void a(b.b b2, a.d.b b3) {
        Vector vector = (Vector)((c)((Object)vector)).H.get(b2);
        vector.addElement(b3);
    }

    public final void a(int n2, int n3) {
        this.v();
        c.a.c();
        c c2 = this;
        Enumeration enumeration = c2.H.elements();
        Vector vector = null;
        a.d.b b2 = null;
        boolean bl = false;
        while (enumeration.hasMoreElements()) {
            vector = (Vector)enumeration.nextElement();
            if (vector.isEmpty()) continue;
            bl = true;
            b2 = (a.d.b)vector.elementAt(0);
            b2.a();
            if (b2.c == 29 && (b2.d.l == 8 || b2.d.l == 6)) {
                c2.x = b.b.a(c2.x, b2.d);
            }
            if (!b2.b()) continue;
            vector.removeElement(b2);
        }
        byte by = 0;
        if (!c.a.f()) {
            a.a.a a2 = c.a.h();
            a2.a(n2, n3);
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

    private void a(a.e.b object, b.f f2) {
        boolean bl;
        if (object == null || f2 == null || f2.w()) {
            return;
        }
        a.e.b b2 = object;
        object = f2;
        Object object2 = b2;
        if (!(b2 == null || object == null ? false : (bl = (object2 = ((a.e.b)object2).c()) != null && ((a.e.d)object2).a()))) {
            return;
        }
        if (f2.l == 8) {
            f2.a(2, 0);
            return;
        }
        f2.a(12, 0);
    }

    private boolean k() {
        if (((c)((Object)enumeration)).H.isEmpty()) {
            return true;
        }
        Enumeration enumeration = ((c)((Object)enumeration)).H.elements();
        Vector vector = null;
        while (enumeration.hasMoreElements()) {
            vector = (Vector)enumeration.nextElement();
            if (vector.isEmpty()) continue;
            return false;
        }
        return true;
    }

    private boolean l() {
        for (int i2 = 0; i2 < this.l.length; ++i2) {
            if (this.l[i2] == null || this.l[i2].w()) continue;
            return false;
        }
        return true;
    }

    private boolean m() {
        for (int i2 = 0; i2 < this.k.length; ++i2) {
            if (this.k[i2] == null || this.k[i2].w()) continue;
            return false;
        }
        return true;
    }

    private void n() {
        switch (this.O) {
            case 0: {
                for (int i2 = 0; i2 < this.l.length; ++i2) {
                    this.a(this.J.f, (b.f)this.l[i2]);
                }
                return;
            }
            case 1: {
                this.a(this.J.f, this.K);
            }
        }
    }

    private void o() {
        if (this.L == null) {
            return;
        }
        b.f[] fArray = this.P ? this.l : this.k;
        switch (this.O) {
            case 0: {
                for (int i2 = 0; i2 < fArray.length; ++i2) {
                    if (fArray[i2] == null || fArray[i2].w()) continue;
                    this.a(this.y.f, fArray[i2]);
                }
                return;
            }
            case 1: {
                this.a(this.y.f, this.K);
            }
        }
    }

    private static void b(b.f f2) {
        if (f2 == null) {
            return;
        }
        for (int i2 = 0; i2 < f2.H.length; ++i2) {
            if (f2.H[i2] <= 0) continue;
            int n2 = i2;
            f2.H[n2] = f2.H[n2] - 1;
            if (f2.H[i2] != 0) continue;
            f2.k(i2);
        }
    }

    private void a(b.f f2, int n2) {
        int n3 = f2.n;
        int n4 = f2.o - f2.q / 2;
        Image image = b.a.Q;
        int n5 = image.getWidth() / 10;
        n2 = Math.max(1, n2);
        b.b[] bArray = new b.b[1];
        b.b[] bArray2 = bArray;
        bArray[0] = b.d.a(n2, n3 - String.valueOf(n2).length() * n5 / 2, n4, image);
        for (n3 = 0; n3 < bArray2.length; ++n3) {
            if (bArray2[n3] == null) continue;
            c.a.a(bArray2[n3]);
        }
        f2.b((byte)2, -n2);
        a.d.b[] bArray3 = this.a(bArray2, f2);
        if (bArray3 != null) {
            for (n2 = 0; n2 < bArray3.length; ++n2) {
                this.a((b.b)f2, bArray3[n2]);
            }
        }
    }

    private boolean p() {
        int n2;
        if (this.k != null) {
            for (n2 = 0; n2 < this.k.length; ++n2) {
                if (this.k[n2] == null || this.k[n2].c()) continue;
                return false;
            }
        }
        if (this.l != null) {
            for (n2 = 0; n2 < this.l.length; ++n2) {
                if (this.l[n2] == null || this.l[n2].c()) continue;
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

    private static String a(b.f f2, f.c[] cArray) {
        if (f2 == null || cArray == null || cArray.length <= 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(f2.k);
        stringBuffer.append(b.a.o[65]);
        for (int i2 = 0; i2 < cArray.length; ++i2) {
            stringBuffer.append(cArray[i2].s);
            if (i2 >= cArray.length - 1) continue;
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    private static String a(int n2, int n3, f.b[] bArray) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(b.a.o[34]);
        stringBuffer.append(b.a.o[42]);
        stringBuffer.append(':');
        stringBuffer.append("\n");
        stringBuffer.append(b.a.o[39]);
        stringBuffer.append("(\u5b58\u6d3b\u7684)");
        stringBuffer.append(b.a.o[35]);
        stringBuffer.append('+');
        stringBuffer.append(n2);
        stringBuffer.append("\n");
        stringBuffer.append("\u6cd5\u5b9d\u7ecf\u9a8c\u503c");
        stringBuffer.append('+');
        stringBuffer.append(n2);
        stringBuffer.append("\n");
        stringBuffer.append(b.a.o[32]);
        stringBuffer.append('+');
        stringBuffer.append(n3);
        if (bArray != null) {
            for (n2 = 0; n2 < bArray.length; ++n2) {
                if (bArray[n2] == null) continue;
                stringBuffer.append("\n");
                stringBuffer.append(bArray[n2].s);
                stringBuffer.append('+');
                stringBuffer.append("1");
                stringBuffer.append(b.a.o[40]);
            }
        }
        return stringBuffer.toString();
    }

    private int r() {
        int n2 = 0;
        for (int i2 = 0; i2 < this.l.length; ++i2) {
            if (this.l[i2] == null) continue;
            ++n2;
        }
        return n2;
    }

    private void s() {
        int n2;
        b.f f2 = null;
        for (n2 = 0; n2 < this.k.length; ++n2) {
            f2 = this.k[n2];
            if (f2 == null || !f2.c() || f2.e() == -1) continue;
            if (f2.M() <= 20) {
                if (f2.l == 6) {
                    f2.a(10, -1);
                } else {
                    f2.a(0, -1);
                }
            } else if (!f2.w()) {
                if (f2.l == 6) {
                    f2.a(8, -1);
                } else {
                    f2.a(0, -1);
                }
            }
            if (f2.f == null) continue;
            f2.f.a(f2.r);
        }
        for (n2 = 0; n2 < this.l.length; ++n2) {
            if (this.l[n2] == null || !this.l[n2].c() || this.l[n2].e() == -1 || this.l[n2].w()) continue;
            this.l[n2].a(0, -1);
        }
    }

    private void t() {
        this.k = new b.f[5];
        System.arraycopy(b.a.A, 0, this.k, 0, b.a.A.length);
        this.w = new int[5][3];
        for (int i2 = 0; i2 < this.k.length; ++i2) {
            this.w[i2] = new int[]{1, 1, 1};
            if (this.k[i2] == null) continue;
            this.w[i2][0] = this.k[i2].B();
            this.w[i2][1] = this.k[i2].C();
            this.w[i2][2] = this.k[i2].D();
            this.k[i2].e = a.e.c.b("r" + this.k[i2].i + "_1");
            this.H.put(this.k[i2], new Vector());
            if (this.k[i2].c(2) < this.k[i2].B() * 20 / 100) {
                this.k[i2].a(10, -1);
            } else {
                this.k[i2].a(8, -1);
            }
            this.k[i2].n = m[i2][0];
            this.k[i2].o = m[i2][1];
            this.k[i2].t();
        }
    }

    private boolean u() {
        if (this.G != null) {
            return (this.G.m & 1) != 0;
        }
        return this.S.j();
    }

    private void a(Graphics graphics, b.f f2, int n2, int n3, boolean bl) {
        if (f2 == null || f2.w() || f2.H() == 0 || f == null) {
            return;
        }
        int n4 = f.getWidth() / 13;
        int n5 = f.getHeight();
        short s = f2.H();
        for (int i2 = 0; i2 < 15; ++i2) {
            if ((s & 1 << i2) == 0) continue;
            a.c.b.a(graphics, this.r, 0, 0, this.r.getWidth(), this.r.getHeight(), bl ? 2 : 0, n2, n3, 36);
            a.c.b.a(graphics, f, (i2 - 1) * n4, 0, n4, n5, 0, n2 + this.r.getWidth() / 2, n3 - 3, 33);
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
                        while (var1_4.V < var1_4.k.length) {
                            if (var1_4.k[var1_4.V] != null && !var1_4.k[var1_4.V].w()) {
                                var1_4.k[var1_4.V].u();
                                if (var1_4.k[var1_4.V].v()) {
                                    var1_4.J = var1_4.k[var1_4.V];
                                    var1_4.P = true;
                                    ++var1_4.V;
                                    v0 = 4;
                                    break block7;
                                }
                            }
                            ++var1_4.V;
                        }
                        var1_4.V = 0;
                        while (var1_4.W < var1_4.l.length) {
                            if (var1_4.l[var1_4.W] != null && !var1_4.l[var1_4.W].w()) {
                                var1_4.l[var1_4.W].u();
                                if (var1_4.l[var1_4.W].v()) {
                                    var1_4.J = var1_4.l[var1_4.W];
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
                                    var3_21 = var2_10.c(2) * (30 + a.c.b.c(20)) / 100;
                                    super.a(var2_10, var3_21);
                                    break;
                                }
                                case 1024: {
                                    switch (var2_10.G()) {
                                        case 0: {
                                            var3_21 = 55 + a.c.b.c(45);
                                            break;
                                        }
                                        case 1: {
                                            var3_21 = 100 + a.c.b.c(990);
                                            break;
                                        }
                                        case 2: {
                                            var3_21 = 100 + a.c.b.c(8999);
                                        }
                                    }
                                    super.a(var2_10, var3_21);
                                    break;
                                }
                                case 8: {
                                    var3_21 = var2_10.c(2) * a.c.b.c(30) / 100;
                                    super.a(var2_10, var3_21);
                                    break;
                                }
                                case 2: {
                                    var3_21 = var2_10.c(2) * a.c.b.c(10) / 100;
                                    super.a(var2_10, var3_21);
                                    break;
                                }
                                case 4096: {
                                    var3_21 = var2_10.c(2) * a.c.b.c(10) / 100;
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
                                if (var2_12 == var1_6.k[3] || var2_12 == var1_6.k[4]) {
                                    var1_6.P = true;
                                }
                                v2 = var3_21 = a.c.b.c(100) < 50 ? 1 : 0;
                                if (var2_12.l == 6) {
                                    var1_6.K = var2_12.a(var3_21 != 0 ? var1_6.l : var1_6.k);
                                    if (var1_6.K == null) {
                                        var1_6.K = var2_12.a(var3_21 != 0 ? var1_6.k : var1_6.l);
                                        var3_21 = var3_21 == 0 ? 1 : 0;
                                    }
                                } else {
                                    var1_6.K = var2_12.a(var3_21 != 0 ? var1_6.k : var1_6.l);
                                    if (var1_6.K == null) {
                                        var1_6.K = var2_12.a(var3_21 != 0 ? var1_6.l : var1_6.k);
                                        v3 = var3_21 = var3_21 == 0 ? 1 : 0;
                                    }
                                }
                                if (var3_21 != 0) {
                                    if (var2_12.l == 8) {
                                        var1_6.a((b.f)var2_12, var1_6.K, var1_6.P);
                                    } else {
                                        var1_6.a((b.f)var2_12, var1_6.K);
                                    }
                                } else {
                                    var5_29 = var1_6.K;
                                    this = var2_12;
                                    var4_27 = var1_6;
                                    if (this != null && var5_29 != null) {
                                        var6_32 = 0;
                                        var3_21 = 0;
                                        var2_12 = null;
                                        var6_32 = this.l == 6 ? 15 : 1;
                                        var3_21 = this.l == 6 ? 8 : 0;
                                        var2_12 = a.d.b.a((b.b)this, var6_32, 0, var3_21, null, this.r);
                                        var4_27.a((b.b)this, (a.d.b)var2_12);
                                        var2_13 = false;
                                        var3_21 = var5_29.o - var5_29.q / 2;
                                        var6_32 = this.a(var5_29, false);
                                        var2_14 = a.d.b.a(var5_29, (byte)2, (short)(-var6_32), (byte)0);
                                        var4_27.a((b.b)this, var2_14);
                                        var2_15 = var6_32 * 100 / var4_27.K.B();
                                        var2_16 = a.d.b.a(var5_29, (byte)14, (short)(var2_15 <= 0 ? 1 : var2_15), (byte)0);
                                        var4_27.a((b.b)this, var2_16);
                                        var7_35 = new b.b[1];
                                        var8_36 = b.a.Q;
                                        var2_17 = var5_29.n - String.valueOf(var6_32).length() * var8_36.getWidth() / 20;
                                        var7_35[0] = b.d.a(var6_32, var2_17, var3_21, var8_36);
                                        for (var2_17 = 0; var2_17 < var7_35.length; ++var2_17) {
                                            c.a.a(var7_35[var2_17]);
                                        }
                                        var2_18 = var4_27.a(var7_35, var5_29);
                                        if (var2_18 != null) {
                                            for (var3_21 = 0; var3_21 < var2_18.length; ++var3_21) {
                                                var4_27.a((b.b)this, var2_18[var3_21]);
                                            }
                                        }
                                    }
                                }
                                var1_6.M = 0;
                                var1_6.O = 1;
                            }
                            return 1;
                        }
                        if (this.J.l != 8) ** GOTO lbl207
                        var2_19 = (b.c)this.J;
                        var1_7 = this;
                        if (var2_19 == null) ** GOTO lbl206
                        var3_22 = null;
                        if (var2_19 == var1_7.k[3] || var2_19 == var1_7.k[4]) {
                            var1_7.P = true;
                            var3_22 = var1_7.l;
                        } else {
                            var1_7.P = false;
                            var3_22 = var1_7.k;
                        }
                        var4_28 = var2_19.j();
                        if (var1_7.P || var1_7.l == null || var4_28 == null || var2_19.H() == 512) ** GOTO lbl173
                        for (var0_1 = 0; var0_1 < var1_7.l.length; ++var0_1) {
                            if (var1_7.l[var0_1] == null || var1_7.l[var0_1].M() >= 30 || var1_7.l[var0_1].w()) continue;
                            var1_7.L = var4_28;
                            var2_19.a(var4_28, -1);
                            var1_7.M = 1;
                            var1_7.O = var1_7.L.u;
                            var1_7.N = var1_7.L.v;
                            var1_7.K = var1_7.l[var0_1];
                            var1_7.a(var2_19, var1_7.K, var1_7.L);
                            ** GOTO lbl206
                        }
lbl173:
                        // 2 sources

                        if ((var0_2 = a.c.b.c(100)) >= 60 || var2_19.H() == 512) ** GOTO lbl201
                        var1_7.L = var2_19.i();
                        if (var1_7.L == null) ** GOTO lbl201
                        var2_19.a(var1_7.L, -1);
                        var1_7.M = 1;
                        var1_7.O = var1_7.L.u;
                        var1_7.N = var1_7.L.v;
                        var3_22 = var1_7.L;
                        var6_33 = var1_7.P;
                        var5_30 = var1_7;
                        if (var3_22 != null) ** GOTO lbl186
                        v4 = null;
                        ** GOTO lbl194
lbl186:
                        // 1 sources

                        if (!var6_33) ** GOTO lbl190
                        if (var3_22.v == 1) ** GOTO lbl-1000
                        v4 = var5_30.k;
                        ** GOTO lbl194
lbl190:
                        // 1 sources

                        if (var3_22.v == 1) {
                            v4 = var5_30.k;
                        } else lbl-1000:
                        // 2 sources

                        {
                            v4 = var5_30.l;
                        }
lbl194:
                        // 4 sources

                        var3_22 = v4;
                        var1_7.K = var2_19.a(var3_22);
                        if (var1_7.P) {
                            var1_7.a((b.f)var2_19, var1_7.K, var1_7.L);
                        } else {
                            var1_7.a(var2_19, var1_7.K, var1_7.L);
                        }
                        ** GOTO lbl206
lbl201:
                        // 2 sources

                        var1_7.K = var2_19.a(var3_22);
                        var1_7.a((b.f)var2_19, var1_7.K, var1_7.P);
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
                            if (var3_23.i == 0) {
                                ++var4_26;
                            }
                            if ((this = var3_23.P()).size() > 0) {
                                ++var4_26;
                            }
                            var5_31 = 0;
                            v6 = new String[++var4_26];
                            var6_34 = v6;
                            ++var5_31;
                            v6[0] = b.a.o[16];
                            ++var5_31;
                            var6_34[1] = b.a.o[7];
                            ++var5_31;
                            var6_34[2] = b.a.o[8];
                            ++var5_31;
                            var6_34[3] = b.a.o[17];
                            ++var5_31;
                            var6_34[4] = b.a.o[60];
                            if (var3_23.i == 0) {
                                ++var5_31;
                                var6_34[5] = b.a.k(77);
                            }
                            if (this.size() > 0) {
                                var6_34[var5_31++] = b.a.k(25);
                            }
                            var6_34[var5_31++] = b.a.o[18];
                            var6_34[var5_31] = "\u5546\u5e97";
                            v5 = var6_34;
                        }
                        var2_20 = v5;
                        var1_8.I = a.a.l.a(var2_20, (byte)0, (byte)4, false, false);
                        c.a.a(var1_8.I);
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
                                var3_24.a(var3_24.J.f, var3_24.K);
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
                        c.c.b(this.J);
                        this.J.s();
                        this.L = null;
                        this.J = null;
                        this.K = null;
                        this.T = false;
                        c.a.e.removeAllElements();
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
                b.a.a(8, 1, (int)b.a.m);
                for (var2_9 = 0; var2_9 < 3; ++var2_9) {
                    if (var1_2.k[var2_9] == null) continue;
                    var1_2.k[var2_9].a(18, 0);
                }
                var5_15 = var1_2;
                var6_16 = 0;
                for (var7_19 = 0; var7_19 < var5_15.l.length; ++var7_19) {
                    if (var5_15.l[var7_19] == null) continue;
                    var6_16 += var5_15.l[var7_19].c(1);
                }
                var7_19 = b.a.b(var5_15.l);
                var6_16 = var7_19 - 5 > (var8_21 = b.a.b(var5_15.k)) ? (var6_16 <<= 1) : (var7_19 - var8_21) * var6_16 / 5 + var6_16;
                var6_16 = var6_16 * b.a.v() / 2;
                var2_9 = var6_16 <= 0 ? 1 : var6_16;
                var5_15 = var1_2;
                var6_16 = 0;
                for (var7_19 = 0; var7_19 < var5_15.l.length; ++var7_19) {
                    if (var5_15.l[var7_19] == null) continue;
                    var6_16 += var5_15.l[var7_19].w;
                }
                var7_19 = b.a.b(var5_15.l);
                if (var7_19 < (var8_21 = b.a.b(var5_15.k))) {
                    var6_16 = (var7_19 - var8_21) * var6_16 / 5 + var6_16;
                }
                var3_24 = (var6_16 = var6_16 * b.a.u() / 2) < 0 ? 1 : var6_16;
                var5_15 = var1_2;
                var6_17 = new f.b[var1_2.l.length];
                for (var7_19 = 0; var7_19 < var5_15.l.length; ++var7_19) {
                    if (var5_15.l[var7_19] != null) ** GOTO lbl37
                    v0 = var6_17;
                    v1 = var7_19;
                    ** GOTO lbl-1000
lbl37:
                    // 1 sources

                    v0 = var6_17;
                    v1 = var7_19;
                    var8_22 = var5_15.l[var7_19];
                    var9_25 = a.c.b.c(100);
                    if (var9_25 <= var8_22.y) {
                        v2 = b.a.b((int)var8_22.x);
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
                b.a.j(var7_19);
                var9_25 = 0;
                var7_20 = new String[3];
                for (var10_30 = 0; var10_30 < 3; ++var10_30) {
                    if (var5_15.k[var10_30] == null || var5_15.k[var10_30].w()) continue;
                    var9_26 = var5_15.k[var10_30].c(0);
                    var5_15.k[var10_30].b((byte)1, var6_18);
                    var11_31 = var9_26;
                    var9_27 = var5_15.k[var10_30];
                    if (var9_27 == null) {
                        v3 = null;
                    } else {
                        var12_32 = var9_27.c(0);
                        if (var12_32 == var11_31) {
                            v3 = null;
                        } else {
                            var13_33 = new StringBuffer();
                            var13_33.append(var9_27.k);
                            var13_33.append(b.a.o[41]);
                            var13_33.append(var9_27.c(0));
                            var13_33.append(b.a.o[37]);
                            var14_34 = null;
                            ++var11_31;
                            while (var11_31 <= var12_32) {
                                var14_34 = b.a.c((int)var9_27.e(var11_31));
                                if (var14_34 != null) {
                                    var13_33.append("\n");
                                    var13_33.append(b.a.o[65]);
                                    var13_33.append(var14_34.s);
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
                        b.a.b(var8_23[var10_30], 1);
                    }
                }
                for (var10_30 = 0; var10_30 < 3; ++var10_30) {
                    if (var5_15.k[var10_30] == null) continue;
                    b.a.A[var10_30].l(var6_18);
                }
                var5_15 = var7_20;
                var2_10 /* !! */  = c.c.a(var2_9, var3_24, var4_28);
                var2_10 /* !! */  = a.a.l.a((String)var2_10 /* !! */ , 5000L);
                var1_2.v.addElement(var2_10 /* !! */ );
                for (var3_24 = 0; var3_24 < var5_15.length; ++var3_24) {
                    if (var5_15[var3_24] == null) continue;
                    var2_10 /* !! */  = a.a.l.a(var5_15[var3_24], 3000L);
                    var1_2.v.addElement(var2_10 /* !! */ );
                }
                var3_24 = super.r();
                var2_10 /* !! */  = null;
                for (var4_29 = 0; var4_29 < var1_2.k.length; ++var4_29) {
                    if (var1_2.k[var4_29] == null || (var2_10 /* !! */  = var1_2.k[var4_29].d(var3_24)) == null || (var2_10 /* !! */  = c.c.a(var1_2.k[var4_29], var2_10 /* !! */ )) == null) continue;
                    var2_10 /* !! */  = a.a.l.a((String)var2_10 /* !! */ , 3000L);
                    var1_2.v.addElement(var2_10 /* !! */ );
                }
                ** GOTO lbl122
lbl118:
                // 1 sources

                if (this.m()) {
                    var1_3 = a.a.l.a(b.a.o[67], 5000L);
                    var1_3.k();
                }
lbl122:
                // 6 sources

                for (var1_4 = 3; var1_4 < this.k.length; ++var1_4) {
                    if (this.k[var1_4] == null || this.k[var1_4].w()) continue;
                    b.a.a(-this.k[var1_4].i, 1);
                }
                this.Q = (byte)5;
                break;
            }
            case 1: {
                if (this.v.isEmpty()) {
                    if (this.m()) {
                        var1_5 = this;
                        if (!(this.G != null ? (var1_5.G.m & 2) != 0 : var1_5.S.k())) {
                            if (c.c.j >= c.c.u) {
                                this.b = new h();
                                break;
                            }
                            a.a.l.a("\u5168\u4f53\u590d\u6d3b,\u5e76\u5168\u4f53\u589e\u52a0\u4e00\u500d\u653b\u51fb\u529b,\u76f4\u5230\u6218\u6597\u7ed3\u675f!\u6bcf\u573a\u6218\u6597\u53ea\u80fd\u590d\u6d3b3\u6b21!", 3000L).k();
                            var1_5 = a.a.l.a(new String[]{"\u5168\u4f53\u590d\u6d3b", "\u8fd4\u56de"}, (byte)0, (byte)1, true);
                            var2_11 = var1_5.k();
                            if (var2_11 == 1 && var1_5.c().equals("\u5168\u4f53\u590d\u6d3b")) {
                                var1_5 = "\u662f\u5426\u82b1\u8d392\u5143\u5168\u4f53\u590d\u6d3b?\u8d60\u90012\u4e2a\u5143\u5b9d(\u7528\u4e8e\u6362\u9ad8\u7ea7\u88c5\u5907),\u8bf7\u8bb0\u5f97\u5b58\u6863!";
                                var2_12 = null;
                                var2_13 = false;
                                var2_13 = b.a.t() != false ? true : e.l.a((String)var1_5, 5, 0);
                                if (var2_13) {
                                    ++c.c.j;
                                    d.b.a(String.valueOf(5), 0);
                                    b.a.a(1001, 2);
                                    for (var1_6 = 0; var1_6 < this.k.length; ++var1_6) {
                                        if (this.k[var1_6] == null || this.k[var1_6].l == 8) continue;
                                        this.k[var1_6].Q();
                                        this.k[var1_6].a(8, -1);
                                    }
                                    var2_14 = "\uff28\uff29\uff16\uff16\uff16.\uff23\uff2f\uff2d";
                                    var1_7 = a.a.l.a("\uff28\uff29\uff16\uff16\uff16.\uff23\uff2f\uff2d", 3000L);
                                    var1_7.k();
                                    return 3;
                                }
                            }
                            this.b = new h();
                            break;
                        }
                    }
                    this.b = new g();
                    break;
                }
                var1_8 = (a.a.l)this.v.elementAt(0);
                var1_8.k();
                this.v.removeElementAt(0);
            }
        }
        return 5;
    }

    public final b.c[] i() {
        return this.l;
    }

    private void v() {
        int n2;
        for (n2 = this.k.length - 1; n2 >= 0; --n2) {
            if (this.k[n2] == null) continue;
            this.k[n2].b();
        }
        for (n2 = 0; n2 < this.l.length; ++n2) {
            if (this.l[n2] == null) continue;
            this.l[n2].b();
        }
        if (c.a.e.size() > 0) {
            b.b b2 = null;
            for (int i2 = 0; i2 < c.a.e.size(); ++i2) {
                b2 = (b.b)c.a.e.elementAt(i2);
                b2.b();
            }
        }
    }

    private void w() {
        int n2;
        this.x = this.k[0];
        for (n2 = 1; n2 < this.k.length; ++n2) {
            if (this.k[n2] == null) continue;
            this.x = b.b.a(this.x, this.k[n2]);
        }
        for (n2 = 0; n2 < this.l.length; ++n2) {
            if (this.l[n2] == null) continue;
            this.x = b.b.a(this.x, this.l[n2]);
        }
    }

    static void a(c c2) {
        c2.v();
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

    static int b(c c2, int n2, b.f[] fArray, boolean n3) {
        int n4 = n3;
        b.f[] fArray2 = fArray;
        int n5 = n2;
        if (fArray2 != null) {
            for (n3 = 0; n3 < fArray2.length; ++n3) {
                if (fArray2[n5 = (n5 + 1) % fArray2.length] == null || fArray2[n5].w() && n4 == 0) continue;
                return n5;
            }
        }
        return -1;
    }

    static b.f b(c c2) {
        return c2.J;
    }

    static void a(c c2, b.f f2, f.c c3) {
        v0.N = c3.v;
        c2.O = c3.u;
        c2.M = 1;
        c2.K = f2;
        c2.L = c3;
        c2.a(c2.J, c2.K, c3);
        c2.Q = 1;
    }

    static a.a.l c(c c2) {
        return c2.I;
    }

    static void a(c c2, int n2, f.a object) {
        Stack stack = c.a.e();
        Object object2 = null;
        for (int i2 = 0; i2 < stack.size(); ++i2) {
            object2 = (a.a.a)stack.elementAt(i2);
            ((a.a.a)object2).b();
        }
        c2.J.b((byte)6, (int)(-((f.a)object).n));
        b.c c3 = b.a.d(-((f.b)object).q);
        if (c3 != null) {
            b.a.a((f.b)object, 1);
            if (c2.k[n2] != null) {
                c2.H.remove(c2.k[n2]);
                if (!c2.k[n2].w()) {
                    b.a.a(-c2.k[n2].i, 1);
                }
            }
            c2.k[n2] = c3;
            c2.k[n2].r = true;
            c2.k[n2].a();
            c2.k[n2].a(-1, -1);
            c2.k[n2].n = m[n2][0];
            c2.k[n2].o = m[n2][1];
            c2.H.put(c2.k[n2], new Vector());
            int n3 = n2;
            object = c2.J;
            c c4 = c2;
            if (object != null) {
                object2 = null;
                if (c4.k[n3] != null && !c4.k[n3].w()) {
                    object2 = a.d.b.a(c4.k[n3], -1, -1, -1, null, c4.k[n3].r);
                    c4.a((b.b)object, (a.d.b)object2);
                }
                object2 = a.d.b.a((b.b)object, 17, 0, 8, null, ((b.b)object).r);
                c4.a((b.b)object, (a.d.b)object2);
                object2 = c.c.a((b.f)object);
                c4.a((b.b)object, (a.d.b)object2);
                object2 = a.d.b.a("r1002", m[n3][0], (short)(m[n3][1] + 25), 2);
                c4.a((b.b)object, (a.d.b)object2);
                object2 = a.d.b.a(c4.k[n3], 0, -1, -1, null, c4.k[n3].r);
                c4.a((b.b)object, (a.d.b)object2);
            }
            c2.Q = 1;
        }
    }

    static void a(c c2, b.f object, a.f.a a2) {
        block5: {
            a.d.b b2;
            Object object2;
            b.f f2;
            block7: {
                Object object3;
                Object object4;
                b.f[] fArray;
                block6: {
                    v0.N = a2.a.v;
                    c2.O = a2.a.u;
                    c2.M = (byte)3;
                    c2.K = object;
                    fArray = a2.a;
                    object4 = c2.N == 1 ? c2.l : c2.k;
                    object3 = c2.K;
                    f2 = c2.J;
                    object = c2;
                    if (f2 == null || fArray == null || fArray.u == 0 && object4 == null || fArray.u == 1 && object3 == null) break block5;
                    object2 = null;
                    object2 = a.d.b.a(f2, 17, 0, 8, null, f2.r);
                    super.a((b.b)f2, (a.d.b)object2);
                    object2 = c.c.a(f2);
                    super.a((b.b)f2, (a.d.b)object2);
                    object2 = a.d.b.a((f.b)fArray, f2, (b.f)object3, object4);
                    super.a((b.b)f2, (a.d.b)object2);
                    object2 = fArray;
                    fArray = object4;
                    object4 = object3;
                    object3 = object;
                    if (object2 != null && (((f.b)object2).u != 0 || fArray != null) && (((f.b)object2).u != 1 || object4 != null)) break block6;
                    b2 = null;
                    break block7;
                }
                a.d.b[][] bArray = null;
                switch (((f.b)object2).u) {
                    case 1: {
                        a.d.b[][] bArray2 = new a.d.b[1][];
                        bArray = bArray2;
                        bArray2[0] = super.a((b.f)object4, (f.b)object2);
                        break;
                    }
                    case 0: {
                        bArray = new a.d.b[fArray.length][];
                        for (int i2 = 0; i2 < fArray.length; ++i2) {
                            if (fArray[i2] == null || fArray[i2].w() && !((f.b)object2).a()) continue;
                            bArray[i2] = super.a(fArray[i2], (f.b)object2);
                        }
                        break;
                    }
                    default: {
                        b2 = null;
                        break block7;
                    }
                }
                b2 = a.d.b.a(bArray);
            }
            object2 = b2;
            super.a((b.b)f2, (a.d.b)object2);
        }
        b.a.d(a2.b, 1);
        c2.Q = 1;
    }

    static void b(c c2, b.f object, f.c object2) {
        c2.J.a((byte)14, 0);
        c2.N = 1;
        c2.O = ((f.b)object2).u;
        c2.M = (byte)2;
        c2.K = object;
        f.c c3 = object2;
        b.f[] fArray = c2.N == 1 ? c2.l : c2.k;
        b.f f2 = object;
        object2 = c2.J;
        object = c2;
        if (!(object2 == null || c3 == null || c3.u == 0 && fArray == null || c3.u == 1 && f2 == null)) {
            int n2;
            int n3 = ((b.b)object2).n;
            int n4 = ((b.b)object2).o;
            Object object3 = null;
            if (!b.a.l[1]) {
                object3 = a.d.b.a((b.b)object2, 13, -1, -1, null, ((b.b)object2).r);
                super.a((b.b)object2, (a.d.b)object3);
                short s = 0;
                n2 = 0;
                switch (c3.u) {
                    case 0: {
                        s = (short)main.a.f;
                        n2 = (short)main.a.g;
                        break;
                    }
                    case 1: {
                        s = (short)(((c)object).K.n + ((c)object).K.p / 2 + ((b.b)object2).p / 2);
                        n2 = (short)((c)object).K.o;
                    }
                }
                object3 = a.d.b.a((int)s, n2 + 1, (b.b)object2, 8, 2);
                super.a((b.b)object2, (a.d.b)object3);
            }
            object3 = a.d.b.a((b.b)object2, c3.a[22], 0, 8, null, ((b.b)object2).r);
            super.a((b.b)object2, (a.d.b)object3);
            object3 = a.d.b.a(c3, (b.f)object2, f2, fArray);
            super.a((b.b)object2, (a.d.b)object3);
            object3 = null;
            switch (c3.u) {
                case 0: {
                    object3 = new a.d.b[fArray.length][];
                    for (n2 = 0; n2 < fArray.length; ++n2) {
                        object3[n2] = super.a(c3, (b.f)object2, fArray[n2]);
                    }
                    break;
                }
                case 1: {
                    a.d.b[][] bArrayArray = new a.d.b[1][];
                    object3 = bArrayArray;
                    bArrayArray[0] = super.a(c3, (b.f)object2, f2);
                }
            }
            object3 = a.d.b.a(object3);
            super.a((b.b)object2, (a.d.b)object3);
            if (!b.a.l[1]) {
                object3 = a.d.b.a((b.b)object2, 14, -1, -1, null, ((b.b)object2).r);
                super.a((b.b)object2, (a.d.b)object3);
                object3 = a.d.b.a((int)((short)n3), (short)n4, (b.b)object2, 8, 2);
                super.a((b.b)object2, (a.d.b)object3);
            }
            object3 = c.c.a((b.f)object2);
            super.a((b.b)object2, (a.d.b)object3);
        }
        c2.Q = 1;
    }

    static {
        byte[] byArray = new byte[]{1, 2, 5};
        m = new short[][]{{209, 138}, {181, 170}, {209, 200}, {141, 140}, {141, 190}};
        n = new short[][]{{54, 138}, {81, 185}, {41, 215}};
        f = null;
        g = null;
        h = null;
        i = null;
        u = 3;
        j = 0;
    }
}

