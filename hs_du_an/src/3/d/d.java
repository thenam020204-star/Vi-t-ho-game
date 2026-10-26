/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package d;

import b.b.h;
import b.b.j;
import b.b.l;
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
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.MainMIDlet;
import main.a;

public final class d
extends k
implements j {
    private static final short[][] n;
    private static final short[][] o;
    private Image p = null;
    private Image q = null;
    private Image r = null;
    private Image s = null;
    private Image t = null;
    private Image u = null;
    public static Image a;
    public static Image[] b;
    public static Image c;
    public static Image d;
    private Vector v = new Vector();
    public a.g[] e = null;
    public a.d[] f = null;
    private a.b w = null;
    private a.b x = null;
    private int y = 0;
    private int[] z = null;
    private int A = -1;
    private int B = -1;
    private int C = -1;
    private int[][] D = null;
    private b.f.b E = null;
    private Hashtable F = null;
    private b.b.g G = null;
    private a.g H = null;
    private a.g I = null;
    private f.c J = null;
    private byte K = 0;
    private byte L = 1;
    private byte M = 1;
    private boolean N = true;
    private byte O = (byte)3;
    private int P = -1;
    private a.a Q = null;
    private static String[] R;
    private boolean S = false;
    private byte T = (byte)3;
    private int U = 0;
    private int V = 0;

    public static d a(b.f.b b2) {
        d d2 = new d();
        new d().E = b2;
        return d2;
    }

    public static d a(a.a a2) {
        d d2 = new d();
        new d().Q = a2;
        return d2;
    }

    private d() {
        super((byte)4);
    }

    public final void a() {
        this.i = true;
        a.f.a();
        Object object = a.f.w.j();
        if (object != null) {
            this.D = new int[((a.a[])object).length][3];
            for (int i2 = 0; i2 < this.D.length; ++i2) {
                this.D[i2][0] = object[i2].m();
                this.D[i2][1] = object[i2].l();
                this.D[i2][2] = object[i2].k != null && object[i2].k.i() ? 1 : 0;
            }
        }
        a.f.k();
        a.f.w.l();
        System.gc();
        b.e.a.a(100L);
        super.a(10);
        main.a.a.a();
        long l2 = System.currentTimeMillis();
        this.F = new Hashtable();
        object = this;
        b.e.d d2 = b.e.d.a("/b.pak");
        if (object.E != null) {
            object.p = d2.c(object.E.k + "_t.png");
            object.q = d2.c(object.E.l + "_b.png");
        } else {
            object.p = d2.c(object.Q.e() + "_t.png");
            object.q = d2.c(object.Q.f() + "_b.png");
        }
        if (object.p == null || object.q == null) {
            object.q = d2.c("0_b.png");
            object.p = d2.c("0_t.png");
        }
        object.r = d2.c("bg.png");
        a = d2.c("status.png");
        Image[] imageArray = new Image[3];
        b = imageArray;
        imageArray[0] = d2.c("a.png");
        d.d.b[1] = d2.c("d.png");
        d.d.b[2] = d2.c("s.png");
        c = d2.c("up.png");
        d = d2.c("down.png");
        object.t = d2.c("miss.png");
        object.s = d2.c("statusBorder.png");
        object.u = d2.c("angry.png");
        super.a(20);
        main.a.a.a();
        this.z = new int[2];
        this.z[0] = a.f.A[0].s;
        this.z[1] = a.f.A[0].t;
        this.A = a.f.A[0].l;
        this.B = a.f.A[0].l();
        this.C = a.f.A[0].m();
        this.q();
        this.j();
        this.u();
        this.P = a.f.ao;
        if (this.E != null && this.E.j >= 0) {
            a.f.d(this.E.j, -1);
        } else if (this.Q != null) {
            a.f.d(this.Q.d(), -1);
        }
        super.a(100);
        main.a.a.a();
        this.i = false;
        long l3 = 1500L - (System.currentTimeMillis() - l2);
        b.e.a.a(l3 <= 0L ? 100L : l3);
    }

    public final void b() {
        a.a[] aArray;
        int n2;
        super.b();
        this.i = true;
        a.f.a();
        super.a(0);
        super.b();
        Object object = this;
        this.q = null;
        ((d)object).p = null;
        ((d)object).r = null;
        a = null;
        b = null;
        b = null;
        c = null;
        d = null;
        ((d)object).u = null;
        ((d)object).t = null;
        ((d)object).s = null;
        this.F.clear();
        this.F = null;
        this.G = null;
        object = this;
        for (int i2 = 0; i2 < a.f.A.length; ++i2) {
            if (((d)object).e[i2].w()) {
                ((d)object).e[i2].a((byte)2, 1);
            }
            ((d)object).e[i2].C();
            ((d)object).e[i2].c((short)0);
            ((d)object).e[i2].j = b.a.c.b(((d)object).e[i2].i);
            ((d)object).e[i2].a(((d)object).A, ((d)object).C);
            ((d)object).e[i2].a(((d)object).B);
            a.f.A[i2].s = ((d)object).z[0];
            a.f.A[i2].t = ((d)object).z[1];
        }
        a.f.w.a(a.f.A[0]);
        object = null;
        if (this.e != null) {
            for (n2 = 0; n2 < this.e.length; ++n2) {
                object = this.e[n2];
                if (object == null) continue;
                ((a.b)object).y = null;
                ((a.b)object).z = null;
            }
        }
        this.e = null;
        for (n2 = 0; n2 < this.f.length; ++n2) {
            if (this.f[n2] == null) continue;
            this.f[n2].c();
        }
        this.f = null;
        super.a(30);
        main.a.a.a();
        long l2 = System.currentTimeMillis();
        a.f.w.m();
        object = this;
        if (a.f.w != null && (aArray = a.f.w.j()) != null && ((d)object).D != null && aArray.length == ((d)object).D.length) {
            for (int i3 = 0; i3 < aArray.length; ++i3) {
                aArray[i3].a(aArray[i3].l, ((d)object).D[i3][0]);
                aArray[i3].a(((d)object).D[i3][1]);
                if (((d)object).D[i3][2] != 1 || aArray[i3].k == null) continue;
                aArray[i3].k.a(true);
            }
        }
        super.a(60);
        main.a.a.a();
        super.a(100);
        main.a.a.a();
        if (this.Q != null) {
            this.Q.e = true;
            this.Q.f = false;
            this.Q.g = true;
            this.Q.p();
        }
        if (this.E != null) {
            this.E.c();
        }
        a.f.d(this.P, -1);
        long l3 = 1500L - (System.currentTimeMillis() - l2);
        b.e.a.a(l3 <= 0L ? 100L : l3);
        this.i = false;
    }

    private void j() {
        this.f = new a.d[3];
        if (this.E != null) {
            this.f[0] = this.E.g == 0 ? null : a.f.c(this.E.g);
            this.f[1] = this.E.h == 0 ? null : a.f.c(this.E.h);
            this.f[2] = this.E.i == 0 ? null : a.f.c(this.E.i);
        } else {
            this.f[0] = this.Q.b == 0 ? null : a.f.c(this.Q.b);
            this.f[1] = this.Q.c == 0 ? null : a.f.c(this.Q.c);
            this.f[2] = this.Q.d == 0 ? null : a.f.c(this.Q.d);
        }
        int n2 = 0;
        int n3 = 0;
        for (int i2 = 0; i2 < this.f.length; ++i2) {
            if (this.f[i2] == null) continue;
            n3 += this.f[i2].c(0);
            ++n2;
            this.F.put(this.f[i2], new Vector());
            this.f[i2].i();
            this.f[i2].a(0, -1);
            this.f[i2].s = o[i2][0];
            this.f[i2].t = o[i2][1];
            this.f[i2].s();
        }
        this.y = n3 / n2;
    }

    public final void a(Graphics graphics) {
        d.k.c(graphics);
        if (this.i) {
            d.k.a(graphics, this.j);
            return;
        }
        Graphics graphics2 = graphics;
        d d2 = this;
        if (d2.p != null) {
            graphics2.drawImage(d2.p, 0, 0, 20);
        }
        if (d2.q != null) {
            graphics2.drawImage(d2.q, 0, d2.p.getHeight(), 20);
        }
        graphics2 = graphics;
        d2 = this;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        if (d2.r != null) {
            n4 = d2.r.getWidth();
            n5 = d2.r.getHeight();
        } else {
            n4 = main.a.d / 3;
            n5 = main.a.d / 3;
        }
        for (int i2 = 0; i2 < 3; ++i2) {
            n2 = i2 * n4;
            n3 = main.a.e - n5;
            a.g g2 = d2.e[i2];
            int n6 = n3;
            int n7 = n2;
            Graphics graphics3 = graphics2;
            d d3 = d2;
            if (d3.r != null) {
                graphics3.drawImage(d3.r, n7, n6, 20);
            } else {
                graphics3.setColor(15454341);
                graphics3.fillRect(n7, n6, main.a.d / 3, main.a.d / 3);
            }
            if (g2 == null) continue;
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
            b.e.a.a(graphics3, a.f.D, g2.c(2), n8, n9);
            d.d.a(graphics3, 35, n8 + 10, n9 += a.f.D.getHeight(), g2.J(), a.f.e);
            b.e.a.a(graphics3, a.f.D, g2.c(4), n8, n9 += 2 + a.f.e.length);
            d.d.a(graphics3, 35, n8 + 10, n9 += a.f.D.getHeight(), g2.K(), a.f.f);
            b.e.a.a(graphics3, a.f.D, g2.c(6), n8, n9 += 2 + a.f.f.length);
            d.d.a(graphics3, 35, n8 + 10, n9 += a.f.D.getHeight(), g2.L(), a.f.g);
            n8 = n7 + 80 - 12;
            g2.a(graphics3, n8, n6 + 21 + 5, (short)20);
        }
        graphics2 = graphics;
        d2 = this;
        for (n2 = 3; n2 < 5; ++n2) {
            if (d2.e[n2] == null) continue;
            d2.e[n2].a(graphics2, d2.e[n2].s, d2.e[n2].t);
        }
        a.b b2 = d2.w;
        while (b2 != null) {
            b2.a(graphics2, b2.s, b2.t);
            b2 = b2.z;
        }
        graphics2 = graphics;
        d2 = this;
        if (d2.T != 1) {
            int n23;
            for (n23 = 0; n23 < d2.f.length; ++n23) {
                if (d2.f[n23] == null || d2.f[n23].w() || d2.f[n23].k == null) continue;
                d2.a(graphics2, d2.f[n23], d2.f[n23].s + d2.f[n23].u / 2, d2.f[n23].t - d2.f[n23].v, false);
            }
            for (n23 = 0; n23 < d2.e.length; ++n23) {
                if (d2.e[n23] == null || d2.e[n23].w() || d2.e[n23].k == null) continue;
                d2.a(graphics2, d2.e[n23], d2.e[n23].s - d2.e[n23].u / 2 - d2.s.getWidth() / 2, d2.e[n23].t - d2.e[n23].v * 3 / 4, true);
            }
        }
        if (this.H != null) {
            b.e.c.a(graphics, 0, this.H.p, main.a.d, 0, 24);
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
        block16: {
            block33: {
                block34: {
                    block31: {
                        block32: {
                            block30: {
                                block27: {
                                    block29: {
                                        block28: {
                                            block25: {
                                                block26: {
                                                    block21: {
                                                        block24: {
                                                            block23: {
                                                                block22: {
                                                                    block19: {
                                                                        block20: {
                                                                            block18: {
                                                                                block17: {
                                                                                    block15: {
                                                                                        if (var1_2 != this.G) ** GOTO lbl210
                                                                                        var1_2 = this.G.a();
                                                                                        var2_6 = a.f.b((short)2);
                                                                                        if (a.f.n() || var2_6 == null || this.H != var2_6 || var1_2.equals(a.f.o[60]) || var1_2.equals("\u6fc0\u6d3b")) break block15;
                                                                                        var1_2 = b.b.g.a("\u7389\u513f\u672a\u88ab\u6fc0\u6d3b,\u76ee\u524d\u53ea\u80fd\u9632\u5fa1!", 3000L);
                                                                                        var1_2.k();
                                                                                        var1_2 = b.b.g.a(d.d.R, this.l, this.m, true);
                                                                                        var2_7 = var1_2.k();
                                                                                        if (var2_7 == 1 && (var1_2 = var1_2.a()).equals("\u6fc0\u6d3b")) {
                                                                                            this.k();
                                                                                        }
                                                                                        break block16;
                                                                                    }
                                                                                    if (!var1_2.equals("\u6fc0\u6d3b")) break block17;
                                                                                    this.k();
                                                                                    break block16;
                                                                                }
                                                                                if (!var1_2.equals("\u5546\u5e97")) break block18;
                                                                                var0_1 = (main.a.d - main.a.h) / 2;
                                                                                var1_3 = (main.a.e - main.a.i) / 2;
                                                                                var2_6 = new h(var0_1, var1_3, main.a.h, main.a.i);
                                                                                var1_4 = new c.j(c.i.a);
                                                                                var2_6.a(var1_4);
                                                                                var2_6.k();
                                                                                break block16;
                                                                            }
                                                                            if (!var1_2.equals(a.f.o[16])) break block19;
                                                                            v0.G.g = false;
                                                                            this.M = 1;
                                                                            this.L = 1;
                                                                            var1_2 = new i(this, this.f, this.L, this.M, false);
                                                                            if (var1_2.k() != 1) break block20;
                                                                            var1_2 = var1_2.j();
                                                                            var2_6 = this;
                                                                            this.L = 1;
                                                                            var2_6.M = 1;
                                                                            var2_6.K = 0;
                                                                            var2_6.I = var1_2;
                                                                            super.a(var2_6.H, var2_6.I);
                                                                            var2_6.O = 1;
                                                                            this.G.c();
                                                                            ** GOTO lbl209
                                                                        }
                                                                        this.G.g = true;
                                                                        break block16;
                                                                    }
                                                                    if (!var1_2.equals(a.f.o[17])) break block21;
                                                                    if (!this.H.I()) break block22;
                                                                    b.b.g.a(a.f.o[61], 3000L).k();
                                                                    break block16;
                                                                }
                                                                if (this.H.q() == 100) break block23;
                                                                b.b.g.a(a.f.o[62], 2000L).k();
                                                                break block16;
                                                            }
                                                            v1.G.g = false;
                                                            var1_2 = new b.b.f(main.a.d / 10, main.a.e / 8, (main.a.d << 2) / 5, main.a.e * 3 / 4);
                                                            var2_6 = new f(this, this.H);
                                                            var1_2.a((l)var2_6);
                                                            if (var1_2.k() != 2) break block24;
                                                            this.G.g = true;
                                                            ** GOTO lbl209
                                                        }
                                                        this.G.c();
                                                        break block16;
                                                    }
                                                    if (!var1_2.equals(a.f.o[18])) break block25;
                                                    var1_2 = null;
                                                    if (!this.r()) break block26;
                                                    var1_2 = this.H;
                                                    var2_6 = this;
                                                    if (var1_2 == null) ** GOTO lbl-1000
                                                    var3_8 = 0;
                                                    var4_12 = var1_2.c(0);
                                                    var3_8 = var4_12 >= var2_6.y ? 100 - 100 * (25 / (var4_12 - var2_6.y + 50)) : 2500 / (50 - var4_12 + var2_6.y);
                                                    if (b.e.a.c(100) < var3_8) {
                                                        v2 = true;
                                                    } else lbl-1000:
                                                    // 2 sources

                                                    {
                                                        v2 = false;
                                                    }
                                                    if (v2) {
                                                        var1_2 = b.b.g.a(a.f.o[27], 3000L);
                                                        this.O = (byte)2;
                                                    } else {
                                                        var1_2 = b.b.g.a(a.f.o[63], 3000L);
                                                        this.O = 1;
                                                    }
                                                    var1_2.k();
                                                    this.G.c();
                                                    ** GOTO lbl209
                                                }
                                                var1_2 = b.b.g.a(a.f.o[64], 3000L);
                                                var1_2.k();
                                                break block16;
                                            }
                                            if (!var1_2.equals(a.f.o[7])) break block27;
                                            if (!this.H.I()) break block28;
                                            var1_2 = b.b.g.a(a.f.o[71], 3000L);
                                            var1_2.k();
                                            break block16;
                                        }
                                        v3.G.g = false;
                                        var1_2 = new b.b.f(main.a.d / 10, main.a.e / 8, (main.a.d << 2) / 5, main.a.e * 3 / 4);
                                        var2_6 = new e(this, this.H, false);
                                        var1_2.a((l)var2_6);
                                        if (var1_2.k() != 2) break block29;
                                        this.G.g = true;
                                        ** GOTO lbl209
                                    }
                                    this.G.c();
                                    break block16;
                                }
                                if (!var1_2.equals(a.f.o[60])) break block30;
                                this.H.a(9, 0);
                                this.O = 1;
                                this.H.P = true;
                                ** GOTO lbl-1000
                            }
                            if (!var1_2.equals(a.f.o[8])) break block31;
                            v4.G.g = false;
                            var1_2 = new b.b.f(main.a.d / 10, main.a.e / 8, (main.a.d << 2) / 5, main.a.e * 3 / 4);
                            var2_6 = new g(this, 1);
                            var1_2.a((l)var2_6);
                            var2_6 = new g(this, 2);
                            var1_2.a((l)var2_6);
                            var2_6 = new g(this, 5);
                            var1_2.a((l)var2_6);
                            if (var1_2.k() != 2) break block32;
                            this.G.g = true;
                            ** GOTO lbl209
                        }
                        this.G.c();
                        break block16;
                    }
                    if (!var1_2.equals(a.f.j(77))) break block33;
                    v5.G.g = false;
                    var1_2 = new i(this, this.f, 1, 1, false);
                    if (var1_2.k() != 1) break block34;
                    var1_2 = var1_2.j();
                    var2_6 = this;
                    this.L = 1;
                    var2_6.M = 1;
                    var2_6.K = (byte)4;
                    var3_9 = 0;
                    if (super.r()) {
                        var5_14 = var1_2;
                        var4_13 = var2_6.H;
                        if (var4_13 == null || var5_14 == null) {
                            v6 = false;
                        } else {
                            var6_15 = var4_13.c(0) - var5_14.c(0);
                            var7_17 = var5_14.J();
                            v6 = var6_15 < 0 || var7_17 >= 50 ? false : (var8_18 = ((50 + var6_15 * 2 / 100) * (50 - var7_17) << 1) / 100) >= (var9_20 = b.e.a.c(100));
                        }
                        if (v6) {
                            var3_9 = 1;
                            a.f.a(-var1_2.n, 1);
                        }
                    }
                    var7_17 = var3_9;
                    var6_16 = var1_2;
                    var5_14 = var2_6.H;
                    var4_13 = var2_6;
                    if (var5_14 != null && var6_16 != null) {
                        var8_19 = null;
                        var9_20 = 0;
                        var1_5 = var6_16.t - var6_16.v / 2;
                        var8_19 = b.f.b.a((a.b)var5_14, 17, 1, 8, null, var5_14.w);
                        super.a((a.b)var5_14, var8_19);
                        var8_19 = d.d.a((a.g)var5_14);
                        super.a((a.b)var5_14, var8_19);
                        if (var7_17 != 0) {
                            var3_10 = new b.f.b[2][1];
                            var3_10[0][0] = b.f.b.a((a.b)var6_16, 3, 0, -1, null, var6_16.w);
                            var3_10[1][0] = b.f.b.a(var5_14.s, var5_14.t, (a.b)var6_16, 2, 1);
                            var8_19 = b.f.b.a(var3_10);
                            super.a((a.b)var5_14, var8_19);
                            var8_19 = b.f.b.a(a.f.j(77) + a.f.j(78), (byte)0);
                            super.a((a.b)var5_14, var8_19);
                            var8_19 = b.f.b.a((a.b)var6_16, (byte)2, (short)0, (byte)1);
                            super.a((a.b)var5_14, var8_19);
                        } else {
                            var9_20 = var6_16.s - var4_13.t.getWidth() / 2;
                            var3_11 = a.e.a(var4_13.t, var9_20, var1_5);
                            d.k.a(var3_11);
                            var8_19 = b.f.b.a(var9_20, var1_5 - var6_16.v / 2, var3_11, 2, 1);
                            super.a((a.b)var5_14, var8_19);
                            var8_19 = b.f.b.a(a.f.j(77) + a.f.j(79), (byte)0);
                            super.a((a.b)var5_14, var8_19);
                        }
                    }
                    var2_6.O = 1;
                    this.G.c();
                    ** GOTO lbl209
                }
                this.G.g = true;
                break block16;
            }
            if (var1_2.equals(a.f.j(25))) {
                v7.G.g = false;
                var1_2 = new b.b.f(main.a.d / 10, main.a.e / 8, (main.a.d << 2) / 5, main.a.e * 3 / 4);
                var2_6 = new e(this, this.H, true);
                var1_2.a((l)var2_6);
                ** if (var1_2.k() != 2) goto lbl-1000
lbl-1000:
                // 1 sources

                {
                    this.G.g = true;
                    ** GOTO lbl211
                }
            }
            ** GOTO lbl209
lbl-1000:
            // 2 sources

            {
                this.G.c();
lbl209:
                // 8 sources

                return;
lbl210:
                // 1 sources

                var1_2.c();
            }
        }
    }

    public final void b(b.b.c c2) {
        d.k.d(c2);
    }

    private void k() {
        String string;
        String string2 = "\u6fc0\u6d3b\u7389\u513f?\u8d60\u90012\u4e2a\u5143\u5b9d(\u7528\u4e8e\u6362\u9ad8\u7ea7\u88c5\u5907),\u8bf7\u8bb0\u5f97\u5b58\u6863!";
        Object var2_2 = null;
        boolean bl = false;
        if (a.f.n()) {
            bl = true;
        } else {
            g.a.a = null;
            bl = g.a.a(MainMIDlet.a, (Displayable)main.a.a, string2, "0110", String.valueOf(4), "1", 200, false);
        }
        if (bl) {
            g.a.a(String.valueOf(4), 0);
            a.f.a(1001, 2);
            a.f.b(true);
            string = "\u6fc0\u6d3b\u6210\u529f,\u7389\u513f\u73b0\u5728\u53ef\u4ee5\u4f7f\u7528\u4e86!";
            d.k.d(((d)((Object)g2)).G);
            super.s();
            a.f.o();
        } else {
            string = "\u6fc0\u6d3b\u5931\u8d25!";
        }
        b.b.g g2 = b.b.g.a(string, 3000L);
        g2.k();
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
        this.S = b.e.a.c(100) > 95 - (g2.G() ? 75 : 0);
        Object var6_8 = null;
        short s = 0;
        if (!a.f.m[1]) {
            s = n2 != 0 ? (short)(bArray.s + bArray.u / 2 + g2.u / 2) : (short)(bArray.s - bArray.u / 2 - g2.u / 2);
            b.f.b b2 = b.f.b.a((int)s, bArray.t + 1, g2, 999, 2);
            this.a((a.b)g2, b2);
        }
        if (this.S) {
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
        if (!this.S) {
            n2 = g2.a((a.g)bArray, false);
            b5 = b.f.b.a((a.b)bArray, (byte)2, (short)(-n2), (byte)0);
            this.a((a.b)g2, b5);
            int n6 = n2 * 100 / this.I.y();
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
            int n8 = bArray.s - this.t.getWidth() / 2;
            a.e e2 = a.e.a(this.t, n8, n5);
            d.k.a(e2);
            b.f.b b7 = b.f.b.a(n8, n5 - bArray.v / 2, e2, 2, 1);
            this.a((a.b)g2, b7);
            b7 = b.f.b.a(bArray.s, bArray.t, (a.b)bArray, 4, 2);
            this.a((a.b)g2, b7);
        }
        if (!a.f.m[1]) {
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
        bArray2[1] = b.f.b.a((a.g)object, (byte)2, (byte)2, (int)((short)(((a.g)object).y() / 5)), null);
        bArray2[1].p = new b.f.b[2];
        bArray2[1].p[0] = bl ? b.f.b.a((a.b)object, 0, -1, -1, null, ((a.b)object).w) : b.f.b.a((a.b)object, 10, -1, -1, null, ((a.b)object).w);
        bArray2[1].p[1] = b.f.b.a(bArray2[1]);
        bArray2[2] = b.f.b.a((a.g)object, (byte)2, (byte)0, (int)((short)(((a.g)object).y() / 5 - 1)), null);
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
        this.S = b.e.a.c(100) > 95 - (g2.G() ? 75 : 0);
        int n4 = b.e.a.c(100) > 95 ? 1 : 0;
        Object object = null;
        if (n4 != 0 && !this.S) {
            object = a.e.a(this.u, g2.s - g2.u / 2, g2.t - g2.v / 2);
            d.k.a((a.b)object);
            object = b.f.b.a(((a.b)object).s, ((a.b)object).t - g2.v, (a.b)object, 2, 1);
            this.a((a.b)g2, (b.f.b)object);
        }
        if (!a.f.m[1]) {
            object = b.f.b.a(g2, 13, -1, -1, null, g2.w);
            this.a((a.b)g2, (b.f.b)object);
            short s = (short)(g3.s + g3.u / 2 + g2.u / 2);
            object = b.f.b.a((int)s, g3.t + 1, g2, 8, 2);
            this.a((a.b)g2, (b.f.b)object);
        }
        if (this.S) {
            object = b.f.b.a(g3.s - g3.u / 2, g3.t, g3, 4, 2);
            this.a((a.b)g2, (b.f.b)object);
        }
        if (this.S || n4 == 0) {
            object = b.f.b.a(g2, 15, 0, 8, null, g2.w);
            this.a((a.b)g2, (b.f.b)object);
        } else {
            object = b.f.b.a(g2, 21, 0, 8, null, g2.w);
            this.a((a.b)g2, (b.f.b)object);
        }
        int n5 = 0;
        int n6 = g3.t - g3.v / 2;
        if (this.S) {
            n5 = g3.s - this.t.getWidth() / 2;
            a.e e2 = a.e.a(this.t, n5, n6);
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
        if (!a.f.m[1]) {
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
                s2 = (short)((main.a.e - this.r.getHeight() << 2) / 5);
                if (c2.h != 0) break;
                s = (short)(s + main.a.d / 3);
                break;
            }
            case 1: {
                s = (short)g3.s;
                s2 = (short)g3.t;
            }
        }
        this.x = new c.h("r" + c2.i[22]);
        this.x.s = s;
        this.x.t = s2;
        this.x.i();
        this.x.f = true;
        this.x.a(-1, -1);
        d.k.a(this.x);
        Object object = b.f.b.a(this.x, 0, 0, -1, null, this.x.w);
        this.a((a.b)g2, (b.f.b)object);
        a.g[] gArray = c2.h == 1 ? this.f : this.e;
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
        a.g[] gArray = c2.h == 1 ? this.e : this.f;
        b2 = b.f.b.a(d2, 1, 0, 0, null, d2.w);
        this.a((a.b)d2, b2);
        this.x = new c.h("r" + c2.i[22]);
        this.x.i();
        this.x.a(-1, -1);
        this.x.f = true;
        d.k.a(this.x);
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
        this.x.s = s;
        this.x.t = s2;
        b.f.b[][] bArrayArray = b.f.b.a(this.x, 0, 0, -1, null, bl);
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
        Vector vector = (Vector)((d)((Object)vector)).F.get(b2);
        vector.addElement(b3);
    }

    public final void a(int n2, int n3) {
        this.t();
        d.k.d();
        d d2 = this;
        Enumeration enumeration = d2.F.elements();
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
                d2.w = a.b.a(d2.w, b2.d);
            }
            if (!b2.b()) continue;
            vector.removeElement(b2);
        }
        byte by = 0;
        if (!d.k.g()) {
            b.b.c c2 = d.k.i();
            c2.a(n2, n3);
            by = this.O;
            this.O = (byte)5;
        } else {
            by = this.a(this.T, (byte)1, n2, n3);
        }
        if (by != 5 && by != this.T) {
            this.a(this.T, (byte)2, n2, n3);
            this.T = by;
            this.a(this.T, (byte)0, n2, n3);
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

    private boolean l() {
        if (((d)((Object)enumeration)).F.isEmpty()) {
            return true;
        }
        Enumeration enumeration = ((d)((Object)enumeration)).F.elements();
        Vector vector = null;
        while (enumeration.hasMoreElements()) {
            vector = (Vector)enumeration.nextElement();
            if (vector.isEmpty()) continue;
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

    private boolean n() {
        for (int i2 = 0; i2 < this.e.length; ++i2) {
            if (this.e[i2] == null || this.e[i2].w()) continue;
            return false;
        }
        return true;
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

    private byte o() {
        if (this.m()) {
            return 2;
        }
        if (this.n()) {
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

    private int p() {
        int n2 = 0;
        for (int i2 = 0; i2 < this.f.length; ++i2) {
            if (this.f[i2] == null) continue;
            ++n2;
        }
        return n2;
    }

    private void q() {
        this.e = new a.g[5];
        System.arraycopy(a.f.A, 0, this.e, 0, a.f.A.length);
        for (int i2 = 0; i2 < this.e.length; ++i2) {
            if (this.e[i2] == null) continue;
            this.e[i2].j = b.a.c.b("r" + this.e[i2].n + "_1");
            this.F.put(this.e[i2], new Vector());
            if (this.e[i2].c(2) < this.e[i2].y() * 20 / 100) {
                this.e[i2].a(10, -1);
            } else {
                this.e[i2].a(8, -1);
            }
            this.e[i2].s = n[i2][0];
            this.e[i2].t = n[i2][1];
            this.e[i2].t();
        }
    }

    private boolean r() {
        if (this.E != null) {
            return (this.E.m & 1) != 0;
        }
        return this.Q.b();
    }

    private void s() {
        String[] stringArray;
        if (this.H == null) {
            throw new NullPointerException();
        }
        a.g g2 = this.H;
        if (g2 == null) {
            stringArray = null;
        } else {
            Vector vector;
            int n2 = 6;
            if (g2.n == 0) {
                ++n2;
            }
            if ((vector = g2.M()).size() > 0) {
                ++n2;
            }
            if (g2.n == 3 && !a.f.n()) {
                ++n2;
            }
            int n3 = 0;
            String[] stringArray2 = new String[++n2];
            String[] stringArray3 = stringArray2;
            ++n3;
            stringArray2[0] = a.f.o[16];
            ++n3;
            stringArray3[1] = a.f.o[7];
            ++n3;
            stringArray3[2] = a.f.o[8];
            ++n3;
            stringArray3[3] = a.f.o[17];
            ++n3;
            stringArray3[4] = a.f.o[60];
            if (g2.n == 0) {
                ++n3;
                stringArray3[5] = a.f.j(77);
            }
            if (vector.size() > 0) {
                stringArray3[n3++] = a.f.j(25);
            }
            stringArray3[n3++] = a.f.o[18];
            if (g2.n == 3 && !a.f.n()) {
                stringArray3[n3++] = "\u6fc0\u6d3b";
            }
            stringArray3[n3] = "\u5546\u5e97";
            stringArray = stringArray3;
        }
        g2 = stringArray;
        this.G = b.b.g.a((String[])g2, (byte)0, (byte)4, false, false);
        d.k.c(this.G);
        this.G.a(this);
    }

    private void a(Graphics graphics, a.g g2, int n2, int n3, boolean bl) {
        if (g2 == null || g2.w() || g2.E() == 0 || a == null) {
            return;
        }
        int n4 = a.getWidth() / 13;
        int n5 = a.getHeight();
        short s = g2.E();
        for (int i2 = 0; i2 < 15; ++i2) {
            if ((s & 1 << i2) == 0) continue;
            b.e.a.a(graphics, this.s, 0, 0, this.s.getWidth(), this.s.getHeight(), bl ? 2 : 0, n2, n3, 36);
            b.e.a.a(graphics, a, (i2 - 1) * n4, 0, n4, n5, 0, n2 + this.s.getWidth() / 2, n3 - 3, 33);
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private byte a(byte var1_1, byte var2_9, int var3_16, int var4_28) {
        switch (var1_1) {
            case 3: {
                var1_1 = var2_9;
                switch (var1_1) {
                    case 0: {
                        ** GOTO lbl31
                    }
                    case 1: {
                        var1_2 = this;
                        while (var1_2.U < var1_2.e.length) {
                            if (var1_2.e[var1_2.U] != null && !var1_2.e[var1_2.U].w()) {
                                var1_2.e[var1_2.U].u();
                                if (var1_2.e[var1_2.U].v()) {
                                    var1_2.H = var1_2.e[var1_2.U];
                                    var1_2.N = true;
                                    ++var1_2.U;
                                    return (byte)4;
                                }
                            }
                            ++var1_2.U;
                        }
                        var1_2.U = 0;
                        while (var1_2.V < var1_2.f.length) {
                            if (var1_2.f[var1_2.V] != null && !var1_2.f[var1_2.V].w()) {
                                var1_2.f[var1_2.V].u();
                                if (var1_2.f[var1_2.V].v()) {
                                    var1_2.H = var1_2.f[var1_2.V];
                                    ++var1_2.V;
                                    return (byte)4;
                                }
                            }
                            ++var1_2.V;
                        }
                        var1_2.V = 0;
                        return 5;
                    }
lbl31:
                    // 2 sources

                    default: {
                        return (byte)5;
                    }
                }
            }
            case 4: {
                var1_1 = var2_9;
                switch (var1_1) {
                    case 0: {
                        this.H.P = false;
                        var2_10 = this.H;
                        var1_3 = this;
                        if (var2_10 != null && var2_10.E() != 0) {
                            var3_16 = 0;
                            v0 = var2_10.E();
                            var4_28 = v0;
                            switch (v0) {
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
                                    var3_16 = var2_10.c(2) * (30 + b.e.a.c(20)) / 100;
                                    super.a(var2_10, var3_16);
                                    break;
                                }
                                case 1024: {
                                    switch (var2_10.D()) {
                                        case 0: {
                                            var3_16 = 55 + b.e.a.c(45);
                                            break;
                                        }
                                        case 1: {
                                            var3_16 = 100 + b.e.a.c(990);
                                            break;
                                        }
                                        case 2: {
                                            var3_16 = 100 + b.e.a.c(8999);
                                        }
                                    }
                                    super.a(var2_10, var3_16);
                                    break;
                                }
                                case 8: {
                                    var3_16 = var2_10.c(2) * b.e.a.c(30) / 100;
                                    super.a(var2_10, var3_16);
                                    break;
                                }
                                case 2: {
                                    var3_16 = var2_10.c(2) * b.e.a.c(10) / 100;
                                    super.a(var2_10, var3_16);
                                    break;
                                }
                                case 4096: {
                                    var3_16 = var2_10.c(2) * b.e.a.c(10) / 100;
                                    super.a(var2_10, var3_16);
                                }
                            }
                        }
                        var1_3 = this.H;
                        if (var1_3 != null) return 5;
                    }
                    case 1: {
                        if (this.l() == false) return 5;
                        if (this.H.w()) {
                            return this.o();
                        }
                        if (this.H.H()) {
                            return 1;
                        }
                        if ((this.H.E() & 32) != 0) {
                            var2_11 = this.H;
                            var1_4 = this;
                            if (var2_11 == null) return 1;
                            if (var2_11 == var1_4.e[3] || var2_11 == var1_4.e[4]) {
                                var1_4.N = true;
                            }
                            v1 = var3_16 = b.e.a.c(100) < 50 ? 1 : 0;
                            if (var2_11.q == 6) {
                                var1_4.I = var2_11.a(var3_16 != 0 ? var1_4.f : var1_4.e);
                                if (var1_4.I == null) {
                                    var1_4.I = var2_11.a(var3_16 != 0 ? var1_4.e : var1_4.f);
                                    var3_16 = var3_16 == 0 ? 1 : 0;
                                }
                            } else {
                                var1_4.I = var2_11.a(var3_16 != 0 ? var1_4.e : var1_4.f);
                                if (var1_4.I == null) {
                                    var1_4.I = var2_11.a(var3_16 != 0 ? var1_4.f : var1_4.e);
                                    v2 = var3_16 = var3_16 == 0 ? 1 : 0;
                                }
                            }
                            if (var3_16 != 0) {
                                if (var2_11.q == 8) {
                                    var1_4.a((a.g)var2_11, var1_4.I, var1_4.N);
                                } else {
                                    var1_4.a((a.g)var2_11, var1_4.I);
                                }
                            } else {
                                var3_17 = var1_4.I;
                                var5_38 = var2_11;
                                var4_29 = var1_4;
                                if (var5_38 != null && var3_17 != null) {
                                    var6_47 = 0;
                                    var7_51 = 0;
                                    var2_11 = null;
                                    var6_47 = var5_38.q == 6 ? 15 : 1;
                                    var7_51 = var5_38.q == 6 ? 8 : 0;
                                    var2_11 = b.f.b.a(var5_38, var6_47, 0, var7_51, null, var5_38.w);
                                    var4_29.a((a.b)var5_38, (b.f.b)var2_11);
                                    var8_55 = 0;
                                    var9_59 = var3_17.t - var3_17.v / 2;
                                    var7_51 = var5_38.a(var3_17, false);
                                    var2_11 = b.f.b.a(var3_17, (byte)2, (short)(-var7_51), (byte)0);
                                    var4_29.a((a.b)var5_38, (b.f.b)var2_11);
                                    var2_12 = var7_51 * 100 / var4_29.I.y();
                                    var2_13 = b.f.b.a(var3_17, (byte)14, (short)(var2_12 <= 0 ? 1 : var2_12), (byte)0);
                                    var4_29.a((a.b)var5_38, var2_13);
                                    var10_64 = new a.b[1];
                                    var2_13 = a.f.Q;
                                    var8_55 = var3_17.s - String.valueOf(var7_51).length() * var2_13.getWidth() / 20;
                                    var10_64[0] = a.e.a(var7_51, var8_55, var9_59, (Image)var2_13);
                                    for (var8_55 = 0; var8_55 < var10_64.length; ++var8_55) {
                                        d.k.a(var10_64[var8_55]);
                                    }
                                    var8_56 = var4_29.a(var10_64, var3_17);
                                    if (var8_56 != null) {
                                        for (var3_18 = 0; var3_18 < var8_56.length; ++var3_18) {
                                            var4_29.a((a.b)var5_38, var8_56[var3_18]);
                                        }
                                    }
                                }
                            }
                            var1_4.K = 0;
                            var1_4.M = 1;
                            return 1;
                        }
                        if (this.H.q != 8) ** GOTO lbl196
                        var2_14 = (a.d)this.H;
                        var1_5 = this;
                        if (var2_14 == null) return 1;
                        var3_19 = null;
                        if (var2_14 == var1_5.e[3] || var2_14 == var1_5.e[4]) {
                            var1_5.N = true;
                            var3_19 = var1_5.f;
                        } else {
                            var1_5.N = false;
                            var3_19 = var1_5.e;
                        }
                        var4_30 = var2_14.b();
                        if (!var1_5.N && var1_5.f != null && var4_30 != null && var2_14.E() != 512) {
                            for (var5_39 = 0; var5_39 < var1_5.f.length; ++var5_39) {
                                if (var1_5.f[var5_39] == null || var1_5.f[var5_39].J() >= 30 || var1_5.f[var5_39].w()) continue;
                                var1_5.J = var4_30;
                                var2_14.a(var4_30, -1);
                                var1_5.K = 1;
                                var1_5.M = var1_5.J.g;
                                var1_5.L = var1_5.J.h;
                                var1_5.I = var1_5.f[var5_39];
                                var1_5.a(var2_14, var1_5.I, var1_5.J);
                                return 1;
                            }
                        }
                        if ((var5_39 = b.e.a.c(100)) >= 60 || var2_14.E() == 512) ** GOTO lbl190
                        var1_5.J = var2_14.a();
                        if (var1_5.J == null) ** GOTO lbl190
                        var2_14.a(var1_5.J, -1);
                        var1_5.K = 1;
                        var1_5.M = var1_5.J.g;
                        var1_5.L = var1_5.J.h;
                        var7_52 = var1_5.J;
                        var6_48 = var1_5.N;
                        var3_19 = var1_5;
                        if (var7_52 != null) ** GOTO lbl175
                        v3 = null;
                        ** GOTO lbl183
lbl175:
                        // 1 sources

                        if (!var6_48) ** GOTO lbl179
                        if (var7_52.h == 1) ** GOTO lbl-1000
                        v3 = var3_19.e;
                        ** GOTO lbl183
lbl179:
                        // 1 sources

                        if (var7_52.h == 1) {
                            v3 = var3_19.e;
                        } else lbl-1000:
                        // 2 sources

                        {
                            v3 = var3_19.f;
                        }
lbl183:
                        // 4 sources

                        var3_19 = v3;
                        var1_5.I = var2_14.a(var3_19);
                        if (var1_5.N) {
                            var1_5.a((a.g)var2_14, var1_5.I, var1_5.J);
                            return 1;
                        } else {
                            var1_5.a(var2_14, var1_5.I, var1_5.J);
                        }
                        return 1;
lbl190:
                        // 2 sources

                        var1_5.I = var2_14.a(var3_19);
                        var1_5.a((a.g)var2_14, var1_5.I, var1_5.N);
                        var1_5.K = 0;
                        var1_5.M = 1;
                        var1_5.L = 1;
                        return 1;
lbl196:
                        // 1 sources

                        this.s();
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
                        return 5;
                    }
                    case 1: {
                        var3_20 = this;
                        switch (var3_20.K) {
                            case 1: 
                            case 5: {
                                var4_31 = var3_20;
                                if (var4_31.J == null) break;
                                var5_40 = var4_31.N != false ? var4_31.f : var4_31.e;
                                switch (var4_31.M) {
                                    case 0: {
                                        for (var3_21 = 0; var3_21 < var5_40.length; ++var3_21) {
                                            if (var5_40[var3_21] == null || var5_40[var3_21].w()) continue;
                                            var4_31.a(var4_31.x.k, var5_40[var3_21]);
                                        }
                                        break;
                                    }
                                    case 1: {
                                        var4_31.a(var4_31.x.k, var4_31.I);
                                    }
                                }
                                break;
                            }
                            case 0: {
                                if (var3_20.S) break;
                                var3_20.a(var3_20.H.k, var3_20.I);
                                break;
                            }
                            case 2: {
                                var4_32 = var3_20;
                                switch (var4_32.M) {
                                    case 0: {
                                        for (var5_41 = 0; var5_41 < var4_32.f.length; ++var5_41) {
                                            var4_32.a(var4_32.H.k, (a.g)var4_32.f[var5_41]);
                                        }
                                        ** GOTO lbl237
                                    }
                                    case 1: {
                                        var4_32.a(var4_32.H.k, var4_32.I);
                                    }
                                }
                            }
                        }
lbl237:
                        // 8 sources

                        var1_1 = (byte)this.l();
                        var3_22 = this;
                        var4_33 = null;
                        for (var5_42 = 0; var5_42 < var3_22.e.length; ++var5_42) {
                            var4_33 = var3_22.e[var5_42];
                            if (var4_33 == null || !var4_33.k() || var4_33.m() == -1) continue;
                            if (var4_33.J() <= 20) {
                                if (var4_33.q == 6) {
                                    var4_33.a(10, -1);
                                } else {
                                    var4_33.a(0, -1);
                                }
                            } else if (!var4_33.w()) {
                                if (var4_33.q == 6) {
                                    var4_33.a(8, -1);
                                } else {
                                    var4_33.a(0, -1);
                                }
                            }
                            if (var4_33.k == null) continue;
                            var4_33.k.a(var4_33.w);
                        }
                        for (var5_42 = 0; var5_42 < var3_22.f.length; ++var5_42) {
                            if (var3_22.f[var5_42] == null || !var3_22.f[var5_42].k() || var3_22.f[var5_42].m() == -1 || var3_22.f[var5_42].w()) continue;
                            var3_22.f[var5_42].a(0, -1);
                        }
                        var3_22 = this;
                        if (var3_22.e != null) {
                            for (var4_34 = 0; var4_34 < var3_22.e.length; ++var4_34) {
                                if (var3_22.e[var4_34] == null || var3_22.e[var4_34].k()) continue;
                                v4 = 0;
                                break;
                            }
                        } else if (var3_22.f != null) {
                            for (var4_35 = 0; var4_35 < var3_22.f.length; ++var4_35) {
                                if (var3_22.f[var4_35] == null || var3_22.f[var4_35].k()) continue;
                                v4 = 0;
                                break;
                            }
                        } else {
                            v4 = 1;
                        }
                        var2_9 = v4;
                        if (var1_1 == 0) return 5;
                        if (var2_9 == 0) return 5;
                        return this.o();
                    }
                    case 2: {
                        var3_23 = this.H;
                        if (var3_23 != null && (var4_28 = var3_23.D()) > 0) {
                            var3_23.i(var4_28 - 1);
                            if (var4_28 - 1 <= 0) {
                                var3_23.c((short)0);
                            }
                        }
                        if ((var3_23 = this.H) != null) {
                            for (var4_28 = 0; var4_28 < var3_23.H.length; ++var4_28) {
                                if (var3_23.H[var4_28] <= 0) continue;
                                v5 = var4_28;
                                var3_23.H[v5] = var3_23.H[v5] - 1;
                                if (var3_23.H[var4_28] != 0) continue;
                                var3_23.k(var4_28);
                            }
                        }
                        this.H.s();
                        this.J = null;
                        this.H = null;
                        this.I = null;
                        this.S = false;
                        d.k.k.removeAllElements();
                        this.x = null;
                    }
                }
                return 5;
            }
            case 2: {
                var1_1 = var2_9;
                switch (var1_1) {
                    case 0: {
                        if (!this.m()) ** GOTO lbl422
                        var6_49 = this;
                        a.f.d(8, 1);
                        for (var7_53 = 0; var7_53 < 3; ++var7_53) {
                            if (var6_49.e[var7_53] == null) continue;
                            var6_49.e[var7_53].a(18, 0);
                        }
                        var3_24 = var6_49;
                        var1_1 = 0;
                        for (var4_28 = 0; var4_28 < var3_24.f.length; ++var4_28) {
                            if (var3_24.f[var4_28] == null) continue;
                            var1_1 = (byte)(var1_1 + var3_24.f[var4_28].c(1));
                        }
                        var4_28 = a.f.b(var3_24.f);
                        var1_1 = var4_28 - 5 > (var5_43 = a.f.b(var3_24.e)) ? (byte)(var1_1 << 1) : (byte)((var4_28 - var5_43) * var1_1 / 5 + var1_1);
                        v6 = var1_1 * a.f.v() / 2;
                        var1_1 = (byte)v6;
                        var7_53 = v6 <= 0 ? 1 : var1_1;
                        var3_24 = var6_49;
                        var1_1 = 0;
                        for (var4_28 = 0; var4_28 < var3_24.f.length; ++var4_28) {
                            if (var3_24.f[var4_28] == null) continue;
                            var1_1 = (byte)(var1_1 + var3_24.f[var4_28].a);
                        }
                        var4_28 = a.f.b(var3_24.f);
                        if (var4_28 < (var5_43 = a.f.b(var3_24.e))) {
                            var1_1 = (byte)((var4_28 - var5_43) * var1_1 / 5 + var1_1);
                        }
                        v7 = var1_1 * a.f.u() / 2;
                        var1_1 = (byte)v7;
                        var2_9 = v7 < 0 ? 1 : var1_1;
                        var3_24 = var6_49;
                        var1_6 = new f.a[var3_24.f.length];
                        for (var4_28 = 0; var4_28 < var3_24.f.length; ++var4_28) {
                            if (var3_24.f[var4_28] != null) ** GOTO lbl341
                            v8 = var1_6;
                            v9 = var4_28;
                            ** GOTO lbl-1000
lbl341:
                            // 1 sources

                            v8 = var1_6;
                            v9 = var4_28;
                            var5_44 = var3_24.f[var4_28];
                            var9_60 = b.e.a.c(100);
                            if (var9_60 <= var5_44.c) {
                                v10 = a.f.b((int)var5_44.b);
                            } else lbl-1000:
                            // 2 sources

                            {
                                v10 = null;
                            }
                            v8[v9] = v10;
                        }
                        var5_45 = var8_57 = var1_6;
                        var4_28 = var2_9;
                        var1_1 = (byte)var7_53;
                        var3_24 = var6_49;
                        a.f.i(var4_28);
                        var9_60 = 0;
                        var4_36 = new String[3];
                        for (var10_65 = 0; var10_65 < 3; ++var10_65) {
                            if (var3_24.e[var10_65] == null || var3_24.e[var10_65].w()) continue;
                            var9_61 = var3_24.e[var10_65].c(0);
                            var3_24.e[var10_65].b((byte)1, (int)var1_1);
                            var11_66 = var9_61;
                            var9_62 = var3_24.e[var10_65];
                            if (var9_62 == null) {
                                v11 = null;
                            } else {
                                var12_67 = var9_62.c(0);
                                if (var12_67 == var11_66) {
                                    v11 = null;
                                } else {
                                    var13_68 = new StringBuffer();
                                    var13_68.append(var9_62.p);
                                    var13_68.append(a.f.o[41]);
                                    var13_68.append(var9_62.c(0));
                                    var13_68.append(a.f.o[37]);
                                    var14_69 = null;
                                    ++var11_66;
                                    while (var11_66 <= var12_67) {
                                        var14_69 = a.f.c((int)var9_62.e(var11_66));
                                        if (var14_69 != null) {
                                            var13_68.append("\n");
                                            var13_68.append(a.f.o[65]);
                                            var13_68.append(var14_69.e);
                                        }
                                        ++var11_66;
                                    }
                                    v11 = var13_68.toString();
                                }
                            }
                            var4_36[var10_65] = v11;
                        }
                        if (var5_45 != null) {
                            for (var10_65 = 0; var10_65 < var5_45.length; ++var10_65) {
                                a.f.b(var5_45[var10_65], 1);
                            }
                        }
                        for (var10_65 = 0; var10_65 < 3; ++var10_65) {
                            if (var3_24.e[var10_65] == null) continue;
                            a.f.A[var10_65].l(var1_1);
                        }
                        var9_63 = var4_36;
                        var7_54 = d.d.a(var7_53, (int)var2_9, var8_57);
                        var2_15 /* !! */  = b.b.g.a(var7_54, 5000L);
                        var6_49.v.addElement(var2_15 /* !! */ );
                        for (var10_65 = 0; var10_65 < var9_63.length; ++var10_65) {
                            if (var9_63[var10_65] == null) continue;
                            var2_15 /* !! */  = b.b.g.a(var9_63[var10_65], 3000L);
                            var6_49.v.addElement(var2_15 /* !! */ );
                        }
                        var10_65 = super.p();
                        var2_15 /* !! */  = null;
                        for (var8_58 = 0; var8_58 < var6_49.e.length; ++var8_58) {
                            if (var6_49.e[var8_58] == null || (var2_15 /* !! */  = var6_49.e[var8_58].d(var10_65)) == null || (var7_54 = d.d.a(var6_49.e[var8_58], var2_15 /* !! */ )) == null) continue;
                            var2_15 /* !! */  = b.b.g.a(var7_54, 3000L);
                            var6_49.v.addElement(var2_15 /* !! */ );
                        }
                        ** GOTO lbl426
lbl422:
                        // 1 sources

                        if (this.n()) {
                            var6_49 = b.b.g.a(a.f.o[67], 5000L);
                            var6_49.k();
                        }
lbl426:
                        // 6 sources

                        for (var1_1 = (byte)3; var1_1 < this.e.length; var1_1 = (byte)(var1_1 + 1)) {
                            if (this.e[var1_1] == null || this.e[var1_1].w()) continue;
                            a.f.a(-this.e[var1_1].n, 1);
                        }
                        this.O = (byte)5;
                        return 5;
                    }
                    case 1: {
                        if (this.v.isEmpty()) {
                            if (this.n()) {
                                var6_50 = this;
                                if (!(var6_50.E != null ? (var6_50.E.m & 2) != 0 : var6_50.Q.c())) {
                                    var1_7 = b.b.g.a(new String[]{"\u5168\u4f53\u590d\u6d3b", "\u8fd4\u56de"}, (byte)0, (byte)1, true);
                                    v12 = var1_7.k();
                                    var2_9 = (byte)v12;
                                    if (v12 == 1 && var1_7.a().equals("\u5168\u4f53\u590d\u6d3b")) {
                                        var3_25 = "\u662f\u5426\u5168\u4f53\u590d\u6d3b?\u8d60\u90012\u4e2a\u5143\u5b9d(\u7528\u4e8e\u6362\u9ad8\u7ea7\u88c5\u5907),\u8bf7\u8bb0\u5f97\u5b58\u6863!";
                                        var4_37 = null;
                                        var5_46 = false;
                                        if (a.f.t()) {
                                            var5_46 = true;
                                        } else {
                                            g.a.a = null;
                                            var5_46 = g.a.a(MainMIDlet.a, (Displayable)main.a.a, var3_25, "0110", String.valueOf(5), "2", 200, false);
                                        }
                                        if (var5_46) {
                                            g.a.a(String.valueOf(5), 0);
                                            a.f.a(1001, 2);
                                            for (var3_26 = 0; var3_26 < this.e.length; ++var3_26) {
                                                if (this.e[var3_26] == null || this.e[var3_26].q == 8) continue;
                                                this.e[var3_26].N();
                                                this.e[var3_26].a(8, -1);
                                            }
                                            var4_37 = "\u590d\u6d3b\u6210\u529f!";
                                            var3_27 = b.b.g.a("\u590d\u6d3b\u6210\u529f!", 3000L);
                                            var3_27.k();
                                            return 3;
                                        }
                                    }
                                    this.h = new b();
                                    return 5;
                                }
                            }
                            this.h = new c();
                            return 5;
                        }
                        var1_8 = (b.b.g)this.v.elementAt(0);
                        var1_8.k();
                        this.v.removeElementAt(0);
                    }
                }
                return 5;
            }
        }
        return 5;
    }

    public final a.d[] c() {
        return this.f;
    }

    private void t() {
        int n2;
        for (n2 = this.e.length - 1; n2 >= 0; --n2) {
            if (this.e[n2] == null) continue;
            this.e[n2].j();
        }
        for (n2 = 0; n2 < this.f.length; ++n2) {
            if (this.f[n2] == null) continue;
            this.f[n2].j();
        }
        if (d.k.k.size() > 0) {
            a.b b2 = null;
            for (int i2 = 0; i2 < d.k.k.size(); ++i2) {
                b2 = (a.b)d.k.k.elementAt(i2);
                b2.j();
            }
        }
    }

    private void u() {
        int n2;
        this.w = this.e[0];
        for (n2 = 1; n2 < this.e.length; ++n2) {
            if (this.e[n2] == null) continue;
            this.w = a.b.a(this.w, this.e[n2]);
        }
        for (n2 = 0; n2 < this.f.length; ++n2) {
            if (this.f[n2] == null) continue;
            this.w = a.b.a(this.w, this.f[n2]);
        }
    }

    static void a(d d2) {
        d2.t();
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
        return d2.H;
    }

    static void a(d d2, a.g g2, f.c c2) {
        v0.L = c2.h;
        d2.M = c2.g;
        d2.K = 1;
        d2.I = g2;
        d2.J = c2;
        d2.a(d2.H, d2.I, c2);
        d2.O = 1;
    }

    static b.b.g c(d d2) {
        return d2.G;
    }

    static void a(d d2, int n2, f.b object) {
        Stack stack = d.k.f();
        Object object2 = null;
        for (int i2 = 0; i2 < stack.size(); ++i2) {
            object2 = (b.b.c)stack.elementAt(i2);
            ((b.b.c)object2).c();
        }
        d2.H.b((byte)6, (int)(-((f.b)object).s));
        a.d d3 = a.f.c(-((f.a)object).c);
        if (d3 != null) {
            a.f.a((f.a)object, 1);
            if (d2.e[n2] != null) {
                d2.F.remove(d2.e[n2]);
                if (!d2.e[n2].w()) {
                    a.f.a(-d2.e[n2].n, 1);
                }
            }
            d2.e[n2] = d3;
            d2.e[n2].w = true;
            d2.e[n2].i();
            d2.e[n2].a(-1, -1);
            d2.e[n2].s = n[n2][0];
            d2.e[n2].t = n[n2][1];
            d2.F.put(d2.e[n2], new Vector());
            int n3 = n2;
            object = d2.H;
            d d4 = d2;
            if (object != null) {
                object2 = null;
                if (d4.e[n3] != null && !d4.e[n3].w()) {
                    object2 = b.f.b.a(d4.e[n3], -1, -1, -1, null, d4.e[n3].w);
                    d4.a((a.b)object, (b.f.b)object2);
                }
                object2 = b.f.b.a((a.b)object, 17, 0, 8, null, ((a.b)object).w);
                d4.a((a.b)object, (b.f.b)object2);
                object2 = d.d.a((a.g)object);
                d4.a((a.b)object, (b.f.b)object2);
                object2 = b.f.b.a("r1002", n[n3][0], (short)(n[n3][1] + 25), 2);
                d4.a((a.b)object, (b.f.b)object2);
                object2 = b.f.b.a(d4.e[n3], 0, -1, -1, null, d4.e[n3].w);
                d4.a((a.b)object, (b.f.b)object2);
            }
            d2.O = 1;
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
                    v0.L = a2.a.h;
                    d2.M = a2.a.g;
                    d2.K = (byte)3;
                    d2.I = object;
                    gArray = a2.a;
                    object4 = d2.L == 1 ? d2.f : d2.e;
                    object3 = d2.I;
                    g2 = d2.H;
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
        a.f.e(a2.b, 1);
        d2.O = 1;
    }

    static void b(d d2, a.g object, f.c object2) {
        d2.H.a((byte)14, 0);
        d2.L = 1;
        d2.M = ((f.a)object2).g;
        d2.K = (byte)2;
        d2.I = object;
        f.c c2 = object2;
        a.g[] gArray = d2.L == 1 ? d2.f : d2.e;
        a.g g2 = object;
        object2 = d2.H;
        object = d2;
        if (!(object2 == null || c2 == null || c2.g == 0 && gArray == null || c2.g == 1 && g2 == null)) {
            int n2;
            int n3 = ((a.b)object2).s;
            int n4 = ((a.b)object2).t;
            Object object3 = null;
            if (!a.f.m[1]) {
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
                        s = (short)(((d)object).I.s + ((d)object).I.u / 2 + ((a.b)object2).u / 2);
                        n2 = (short)((d)object).I.t;
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
            if (!a.f.m[1]) {
                object3 = b.f.b.a((a.b)object2, 14, -1, -1, null, ((a.b)object2).w);
                super.a((a.b)object2, (b.f.b)object3);
                object3 = b.f.b.a((int)((short)n3), (short)n4, (a.b)object2, 8, 2);
                super.a((a.b)object2, (b.f.b)object3);
            }
            object3 = d.d.a((a.g)object2);
            super.a((a.b)object2, (b.f.b)object3);
        }
        d2.O = 1;
    }

    static {
        byte[] byArray = new byte[]{1, 2, 5};
        n = new short[][]{{209, 138}, {181, 170}, {209, 200}, {141, 140}, {141, 190}};
        o = new short[][]{{54, 138}, {81, 185}, {41, 215}};
        a = null;
        b = null;
        c = null;
        d = null;
        R = new String[]{"\u6fc0\u6d3b", "\u8fd4\u56de"};
    }
}

