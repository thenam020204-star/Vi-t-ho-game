/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class h
implements i {
    private static a F;
    h a;
    h[] b = new h[8];
    h c;
    int d = 0;
    int e = 0;
    boolean f;
    int g;
    boolean h = false;
    int i;
    int j = 0;
    int k;
    private boolean G;
    static boolean l;
    static int m;
    static int n;
    static boolean o;
    private static boolean H;
    private static int I;
    int[] p;
    int q;
    int r;
    int s = 0;
    int[] t;
    private int[] J = new int[5];
    int u;
    int v;
    int w;
    int x;
    private int[] K;
    private boolean L;
    int y;
    int z;
    int A;
    int B;
    private Image M;
    b C = null;
    b D = null;
    private static h[] N;
    private static int O;
    static byte E;
    private h[] P = new h[2];
    private int aK = 0;

    private h() {
    }

    final void a() {
        if (this.a != null) {
            this.a.k = -1;
            return;
        }
        if (this.h) {
            c.e();
        }
    }

    final void b() {
        this.a();
        this.f = false;
    }

    final void a(int n2) {
        if (this.a != null) {
            this.a.k = this.s;
        }
        this.k = -1;
        this.f = true;
        this.w = -1;
        this.j = 0;
        this.r = n2;
    }

    final void c() {
        this.a(-1);
    }

    final void a(h h2) {
        h2.a = this;
        this.b[this.d] = h2;
        this.b[this.d].s = this.d;
        ++this.d;
    }

    final void d() {
        for (int i2 = 0; i2 < this.d; ++i2) {
            this.b[i2].h();
            this.b[i2] = null;
        }
        this.d = 0;
    }

    final void e() {
        this.q = 0;
        this.d = 0;
        this.K = null;
        this.a = null;
        this.C = null;
        this.D = null;
        this.M = null;
        this.i();
        this.t = null;
        this.p = null;
    }

    static void f() {
        if (N == null) {
            N = new h[32];
            O = 31;
            for (int i2 = 0; i2 < 32; ++i2) {
                h.N[i2] = new h();
            }
        }
    }

    static h g() {
        h h2 = N[O];
        --O;
        return h2;
    }

    final void h() {
        for (int i2 = 0; i2 < this.d; ++i2) {
            this.b[i2].h();
            this.b[i2] = null;
        }
        if (this.c != null) {
            this.c.h();
            this.c = null;
        }
        this.e();
        h.N[++h.O] = this;
    }

    final void b(int n2) {
        if (this.p == null) {
            this.p = new int[n2];
        }
    }

    final void c(int n2) {
        if (this.q >= this.p.length) {
            int[] nArray = new int[this.p.length + 8];
            System.arraycopy(this.p, 0, nArray, 0, this.p.length);
            this.p = null;
            this.p = nArray;
        }
        this.p[this.q] = n2;
        ++this.q;
    }

    final void a(int n2, int n3, int n4, int n5, int n6) {
        this.J[0] = n2;
        this.J[1] = n3;
        this.J[2] = n4;
        this.J[3] = n5;
        this.J[4] = n6;
        if (n6 == 1 && this.M == null) {
            n2 = 0;
            n3 = 0;
            if (this.J[1] >= 0) {
                n2 = F.b(this.J[1]);
            }
            if (this.J[2] >= 0) {
                n3 = F.a(this.J[2]);
            }
            int[] nArray = new int[(this.t[2] - (n3 << 1)) * (this.t[3] - (n2 << 1))];
            for (n6 = 0; n6 < nArray.length; ++n6) {
                nArray[n6] = 0x99000000 | n5;
            }
            this.M = Image.createRGBImage((int[])nArray, (int)(this.t[2] - (n3 << 1)), (int)(this.t[3] - (n2 << 1)), (boolean)true);
        }
    }

    static void a(a a2) {
        F = a2;
    }

    private void a(int n2, int n3, int n4, boolean bl2, int n5) {
        if (this.K == null) {
            this.K = new int[3];
        }
        this.K[0] = n2;
        this.K[1] = n3;
        this.K[2] = n4;
        this.L = bl2;
        this.g = n5;
    }

    private void b(h h2) {
        this.P[this.aK] = h2;
        ++this.aK;
    }

    final void i() {
        for (int i2 = 0; i2 < this.aK; ++i2) {
            this.P[i2] = null;
        }
        this.aK = 0;
    }

    final void d(int n2) {
        this.z = n2;
        this.m();
    }

    private void m() {
        while (true) {
            h2.y = 0;
            h2.A = 0;
            if (h2.c == null) break;
            h h2 = h2.c;
        }
    }

    /*
     * Unable to fully structure code
     */
    final void j() {
        block96: {
            block90: {
                block93: {
                    block94: {
                        block95: {
                            block92: {
                                block91: {
                                    if (this.k >= 0) break block90;
                                    var1_1 = this;
                                    var2_4 = c.m[var1_1.g];
                                    var3_5 = 0;
                                    var4_6 = false;
                                    var1_1.j = 0;
                                    if (var1_1.K == null) break block91;
                                    switch (h.E) {
                                        case 0: {
                                            var3_5 = c.a(var1_1.g, var1_1.u, var1_1.K[0], var1_1.K[1], var1_1.K[2], var1_1.L);
                                            var4_6 = c.b(524288);
                                            ** GOTO lbl20
                                        }
                                        case 1: 
                                        case 2: {
                                            if (var1_1.G) ** GOTO lbl20
                                            if (h.I <= 0) ** GOTO lbl18
                                            --h.I;
                                            break block92;
lbl18:
                                            // 1 sources

                                            var3_5 = c.d(var1_1);
                                            var4_6 = h.H;
                                        }
lbl20:
                                        // 4 sources

                                        default: {
                                            if (c.o) {
                                                var1_1.j = 6;
                                                var1_1.m();
                                                break;
                                            }
                                            if (var3_5 > var2_4) {
                                                var1_1.j = 5;
                                                var1_1.m();
                                                break;
                                            }
                                            if (var3_5 >= var2_4) break;
                                            var1_1.j = 4;
                                            var1_1.m();
                                        }
                                    }
                                }
                                if (var4_6 || var1_1.r == 0) {
                                    if (c.r[0] >= 0) {
                                        c.j();
                                    } else {
                                        var1_1.j = -1;
                                        var1_1.m();
                                    }
                                } else {
                                    var1_1.w = c.n;
                                }
                            }
                            switch (this.e) {
                                case 55: {
                                    this.k();
                                    break;
                                }
                                case 58: {
                                    var1_1 = this;
                                    var2_4 = c.m[var1_1.g];
                                    if (var1_1.j == -1 || var1_1.w >= 0) {
                                        var1_1.a();
                                        break;
                                    }
                                    if (var1_1.j == 5) {
                                        if (var1_1.C != null) {
                                            var1_1.C = var1_1.C.b;
                                        }
                                        if (var2_4 / var1_1.B - var1_1.x >= var1_1.v) {
                                            ++var1_1.x;
                                            for (var3_5 = 0; var3_5 < var1_1.B; ++var3_5) {
                                                if (var1_1.D == null) continue;
                                                var1_1.D = var1_1.D.b;
                                            }
                                        }
                                        var1_1.a();
                                        break;
                                    }
                                    if (var1_1.j == 4) {
                                        if (var1_1.C != null) {
                                            var1_1.C = var1_1.C.a;
                                        }
                                        if (var2_4 / var1_1.B < var1_1.x) {
                                            --var1_1.x;
                                            for (var3_5 = 0; var3_5 < var1_1.B; ++var3_5) {
                                                if (var1_1.D == null) continue;
                                                var1_1.D = var1_1.D.a;
                                            }
                                        }
                                        var1_1.a();
                                        break;
                                    }
                                    if (c.b(16388)) {
                                        if (var2_4 >= var1_1.u - var1_1.B) break;
                                        v0 = var1_1.g;
                                        c.m[v0] = c.m[v0] + var1_1.B;
                                        if (var1_1.C != null) {
                                            for (var3_5 = 0; var3_5 < var1_1.B; ++var3_5) {
                                                if (var1_1.C.b == null) continue;
                                                var1_1.C = var1_1.C.b;
                                            }
                                        }
                                        if (var2_4 / var1_1.B - var1_1.x >= var1_1.v - 1) {
                                            ++var1_1.x;
                                            if (var1_1.D != null) {
                                                for (var3_5 = 0; var3_5 < var1_1.B; ++var3_5) {
                                                    if (var1_1.D.b == null) continue;
                                                    var1_1.D = var1_1.D.b;
                                                }
                                            }
                                        }
                                        var1_1.j = 5;
                                        var1_1.m();
                                        var1_1.a();
                                        break;
                                    }
                                    if (!c.b(258) || var2_4 < var1_1.B) break;
                                    v1 = var1_1.g;
                                    c.m[v1] = c.m[v1] - var1_1.B;
                                    if (var1_1.C != null) {
                                        for (var3_5 = 0; var3_5 < var1_1.B; ++var3_5) {
                                            if (var1_1.C.a == null) continue;
                                            var1_1.C = var1_1.C.a;
                                        }
                                    }
                                    if (var2_4 / var1_1.B <= var1_1.x) {
                                        --var1_1.x;
                                        if (var1_1.D != null) {
                                            for (var3_5 = 0; var3_5 < var1_1.B; ++var3_5) {
                                                if (var1_1.D.a == null) continue;
                                                var1_1.D = var1_1.D.a;
                                            }
                                        }
                                    }
                                    var1_1.j = 4;
                                    var1_1.m();
                                    var1_1.a();
                                    break;
                                }
                                case 56: {
                                    c.p(this);
                                    break;
                                }
                                case 46: {
                                    c.J(this);
                                    break;
                                }
                                case 12: 
                                case 13: {
                                    c.I(this);
                                    break;
                                }
                                case 1: {
                                    c.q(this);
                                    break;
                                }
                                case 14: {
                                    c.r(this);
                                    break;
                                }
                                case 36: {
                                    c.s(this);
                                    break;
                                }
                                case 5: {
                                    c.u(this);
                                    break;
                                }
                                case 15: {
                                    c.v(this);
                                    break;
                                }
                                case 60: {
                                    c.t(this);
                                    break;
                                }
                                case 52: {
                                    c.w(this);
                                    break;
                                }
                                case 6: {
                                    c.F(this);
                                    break;
                                }
                                case 16: {
                                    c.h();
                                    break;
                                }
                                case 17: {
                                    c.x(this);
                                    break;
                                }
                                case 19: {
                                    break;
                                }
                                case 20: {
                                    c.z(this);
                                    break;
                                }
                                case 9: {
                                    c.y(this);
                                    break;
                                }
                                case 18: {
                                    c.A(this);
                                    break;
                                }
                                case 10: {
                                    c.B(this);
                                    break;
                                }
                                case 25: {
                                    c.C(this);
                                    break;
                                }
                                case 24: {
                                    c.g();
                                    break;
                                }
                                case 2: {
                                    c.G(this);
                                    break;
                                }
                                case 4: {
                                    c.H(this);
                                    break;
                                }
                                case 50: {
                                    c.i();
                                    break;
                                }
                                case 47: {
                                    c.a(this);
                                    break;
                                }
                                case 48: {
                                    c.c(this);
                                    break;
                                }
                                case 49: {
                                    c.b(this);
                                    break;
                                }
                                case 7: {
                                    c.D(this);
                                    break;
                                }
                                case 40: {
                                    c.m(this);
                                    break;
                                }
                                case 41: {
                                    c.n(this);
                                    break;
                                }
                                case 43: {
                                    c.o(this);
                                    break;
                                }
                                case 42: {
                                    c.l(this);
                                    break;
                                }
                                case 57: {
                                    c.k(this);
                                    break;
                                }
                                case 34: {
                                    c.h(this);
                                    break;
                                }
                                case 35: {
                                    c.e(this);
                                    break;
                                }
                                case 32: {
                                    c.L(this);
                                    break;
                                }
                                case 33: {
                                    c.M(this);
                                    break;
                                }
                                case 11: {
                                    c.K(this);
                                    break;
                                }
                                case 37: {
                                    c.i(this);
                                    break;
                                }
                                case 38: {
                                    c.j(this);
                                    break;
                                }
                                case 39: {
                                    break;
                                }
                                case 51: {
                                    c.E(this);
                                    break;
                                }
                                case 8: {
                                    c.N(this);
                                    break;
                                }
                                case 21: {
                                    this.k();
                                    break;
                                }
                                case 22: {
                                    c.O(this);
                                    break;
                                }
                                case 23: {
                                    c.P(this);
                                    break;
                                }
                                case 61: {
                                    var1_1 = this;
                                    if (!h.F.l) {
                                        h.F.e();
                                        break;
                                    }
                                    var1_1.j = -1;
                                    var1_1.b();
                                    break;
                                }
                                case 62: {
                                    var1_1 = this;
                                    if (!c.c.l) {
                                        c.c.e();
                                        break;
                                    }
                                    var1_1.j = -1;
                                    var1_1.b();
                                    break;
                                }
                                case 59: {
                                    c.f();
                                }
                            }
                            for (var1_2 = 0; var1_2 < this.aK; ++var1_2) {
                                this.P[var1_2].j();
                            }
                            if (this.c == null) break block93;
                            var1_3 = this.c;
                            if ((c.k & 2) == 0) break block94;
                            if (var1_3.A > 10) break block95;
                            ++var1_3.A;
                            v2 = var1_3;
                            ** GOTO lbl-1000
                        }
                        v2 = var1_3;
                        v3 = true;
                        break block93;
                    }
                    if (c.b(64)) {
                        v2 = var1_3;
                        ** if (var1_3.f) goto lbl-1000
lbl-1000:
                        // 1 sources

                        {
                            v3 = true;
                            ** GOTO lbl286
                        }
                    }
                    break block93;
lbl-1000:
                    // 2 sources

                    {
                        v3 = v2.f = false;
                    }
                }
                if (h.l) {
                    h.l = false;
                    h.o = false;
                    h.H = false;
                }
                break block96;
            }
            this.b[this.k].j();
        }
        if (this.r > 0) {
            --this.r;
        }
    }

    final void k() {
        int n2 = c.m[this.g];
        if (this.j == 6) {
            if (n2 == 0) {
                this.x = 0;
                return;
            }
            this.x = Math.max(0, n2 - this.v + 1);
            return;
        }
        if (this.j == 5) {
            if (this.C != null) {
                this.C = this.C.b;
            }
            if (n2 - this.x >= this.v) {
                ++this.x;
                if (this.D != null) {
                    this.D = this.D.b;
                }
            }
            return;
        }
        if (this.j == 4) {
            if (this.C != null) {
                this.C = this.C.a;
            }
            if (n2 < this.x) {
                --this.x;
                if (this.D != null) {
                    this.D = this.D.a;
                }
            }
            return;
        }
    }

    final void a(Graphics graphics) {
        if (this.f) {
            Graphics graphics2 = graphics;
            h h2 = this;
            if (h2.t != null && h2.t[2] > 0 && h2.t[3] > 0) {
                int n2;
                int n3 = h2.J[0];
                int n4 = h2.J[1];
                int n5 = h2.J[2];
                int n6 = h2.J[3];
                int n7 = 0;
                int n8 = 0;
                int n9 = 0;
                int n10 = 0;
                if (n3 >= 0) {
                    n7 = F.a(n3);
                    n8 = F.b(n3);
                }
                if (n4 >= 0) {
                    n9 = F.b(n4);
                }
                if (n5 >= 0) {
                    n10 = F.a(n5);
                }
                int n11 = h2.t[0] + n10;
                int n12 = h2.t[1] + n9;
                n10 = h2.t[2] - (n10 << 1);
                n9 = h2.t[3] - (n9 << 1);
                graphics2.setClip(h2.t[0], h2.t[1], h2.t[2], h2.t[3]);
                if (n6 >= 0) {
                    if (h2.J[4] == 1) {
                        graphics2.drawImage(h2.M, n11, n12, 0);
                    } else {
                        graphics2.setColor(n6);
                        graphics2.fillRect(n11, n12, n10, n9);
                    }
                }
                int n13 = 0;
                if (n4 >= 0) {
                    n2 = h2.t[1];
                    n13 = F.a(n4);
                    for (n6 = h2.t[0] + n7; n6 < h2.t[0] + h2.t[2] - n13 - n7; n6 += n13) {
                        F.a(graphics2, n4, n6, n2, 0);
                        F.a(graphics2, n4, n6, n12 + n9, 1);
                    }
                    if (h2.t[2] > n7 << 1) {
                        F.a(graphics2, n4, h2.t[2] + h2.t[0] - n13 - n7, n2, 0);
                        F.a(graphics2, n4, h2.t[2] + h2.t[0] - n13 - n7, n12 + n9, 1);
                    }
                }
                if (n5 >= 0) {
                    n6 = h2.t[0];
                    n13 = F.b(n5);
                    for (n2 = h2.t[1] + n8; n2 < h2.t[1] + h2.t[3] - n13 - n8; n2 += n13) {
                        F.a(graphics2, n5, n6, n2, 0);
                        F.a(graphics2, n5, n11 + n10, n2, 2);
                    }
                    if (h2.t[3] > n8 << 1) {
                        F.a(graphics2, n5, n6, h2.t[1] + h2.t[3] - n13 - n8, 0);
                        F.a(graphics2, n5, n11 + n10, h2.t[1] + h2.t[3] - n13 - n8, 2);
                    }
                }
                if (n3 >= 0) {
                    F.a(graphics2, n3, h2.t[0], h2.t[1], 0);
                    F.a(graphics2, n3, h2.t[0] + h2.t[2] - n7, h2.t[1], 2);
                    F.a(graphics2, n3, h2.t[0], h2.t[1] + h2.t[3] - n8, 1);
                    F.a(graphics2, n3, h2.t[0] + h2.t[2] - n7, h2.t[1] + h2.t[3] - n8, 3);
                }
            }
            switch (this.e) {
                case 55: {
                    break;
                }
                case 58: {
                    c.A(graphics, this);
                    break;
                }
                case 56: {
                    c.j(graphics, this);
                    break;
                }
                case 46: {
                    c.G(graphics, this);
                    break;
                }
                case 1: {
                    c.k(graphics, this);
                    break;
                }
                case 14: {
                    c.l(graphics, this);
                    break;
                }
                case 36: {
                    c.m(graphics, this);
                    break;
                }
                case 45: {
                    c.E(graphics, this);
                    break;
                }
                case 12: 
                case 13: {
                    c.F(graphics, this);
                    break;
                }
                case 5: {
                    c.o(graphics, this);
                    break;
                }
                case 15: {
                    c.p(graphics, this);
                    break;
                }
                case 60: {
                    c.n(graphics, this);
                    break;
                }
                case 52: {
                    c.q(graphics, this);
                    break;
                }
                case 6: {
                    c.r(graphics, this);
                    break;
                }
                case 16: {
                    c.s(graphics, this);
                    break;
                }
                case 17: {
                    c.t(graphics, this);
                    break;
                }
                case 19: {
                    c.v(graphics, this);
                    break;
                }
                case 20: {
                    c.u(graphics, this);
                    break;
                }
                case 9: {
                    c.t(graphics, this);
                    break;
                }
                case 18: {
                    c.w(graphics, this);
                    break;
                }
                case 24: {
                    c.x(graphics, this);
                    break;
                }
                case 10: {
                    c.y(graphics, this);
                    break;
                }
                case 25: {
                    c.z(graphics, this);
                    break;
                }
                case 7: {
                    c.B(graphics, this);
                    break;
                }
                case 2: {
                    c.C(graphics, this);
                    break;
                }
                case 4: {
                    c.D(graphics, this);
                    break;
                }
                case 40: {
                    c.L(graphics, this);
                    break;
                }
                case 41: {
                    c.I(graphics, this);
                    break;
                }
                case 42: {
                    c.K(graphics, this);
                    break;
                }
                case 43: {
                    c.J(graphics, this);
                    break;
                }
                case 57: {
                    c.h(graphics, this);
                    break;
                }
                case 34: {
                    c.d(graphics, this);
                    break;
                }
                case 35: {
                    break;
                }
                case 32: {
                    c.M(graphics, this);
                    break;
                }
                case 33: {
                    c.N(graphics, this);
                    break;
                }
                case 11: {
                    c.H(graphics, this);
                    break;
                }
                case 37: {
                    c.e(graphics, this);
                    break;
                }
                case 38: {
                    c.f(graphics, this);
                    break;
                }
                case 39: {
                    c.g(graphics, this);
                    break;
                }
                case 51: {
                    c.a(graphics);
                    break;
                }
                case 50: {
                    c.b(graphics);
                    break;
                }
                case 48: {
                    c.c(graphics, this);
                    break;
                }
                case 49: {
                    c.b(graphics, this);
                    break;
                }
                case 47: {
                    c.a(graphics, this);
                    break;
                }
                case 8: {
                    c.O(graphics, this);
                    break;
                }
                case 21: {
                    c.P(graphics, this);
                    break;
                }
                case 22: {
                    c.Q(graphics, this);
                    break;
                }
                case 23: {
                    c.R(graphics, this);
                    break;
                }
                case 61: {
                    c.c(graphics);
                    break;
                }
                case 62: {
                    c.d(graphics);
                    break;
                }
                case 59: {
                    c.i(graphics, this);
                }
            }
        }
        if (this.c != null && this.c.f) {
            this.c.a(graphics);
        }
        for (int i2 = 0; i2 < this.d; ++i2) {
            if (!this.b[i2].f) continue;
            this.b[i2].a(graphics);
        }
    }

    final void a(int n2, int n3, int n4, int n5) {
        if (this.t == null) {
            this.t = new int[4];
        }
        this.t[0] = n2;
        this.t[1] = n3;
        this.t[2] = n4;
        this.t[3] = n5;
    }

    final void l() {
        switch (m) {
            case 0: {
                I += n;
                return;
            }
            case 1: {
                if (this.g < 0) break;
                c.m[this.g] = n;
                return;
            }
            case 2: {
                o = true;
                return;
            }
            case 3: {
                H = true;
                return;
            }
            case 4: {
                if (this.g < 0) break;
                c.m[this.g] = 0;
                return;
            }
            case 5: {
                E = (byte)n;
                return;
            }
            case 6: {
                if (n == -1) {
                    this.G = !this.G;
                    return;
                }
                if (n >= this.P.length) {
                    for (int i2 = 0; i2 < this.P.length; ++i2) {
                        this.P[i2].G = !this.P[i2].G;
                    }
                    return;
                }
                this.P[h.n].G = !this.P[h.n].G;
            }
        }
    }

    final void e(int n2) {
        this.e = n2;
        this.u = 0;
        this.j = 0;
        this.k = -1;
        this.w = -1;
        this.h = false;
        this.x = 0;
        this.r = -1;
        this.y = 0;
        this.z = 0;
        this.i = 0;
        this.G = false;
        switch (n2) {
            case 55: {
                h h2 = this;
                h2.a(-1, -1, -1, -1, 0);
                break;
            }
            case 58: {
                h h3 = this;
                h3.a(-1, -1, -1, -1, 0);
                h3.a(4112, 1032, 264224, false, 23);
                break;
            }
            case 46: {
                h h4 = this;
                this.u = 9;
                h4.v = 1;
                h4.a(4112, 1032, 264224, true, 1);
                h h5 = h.g();
                h5.a(12, 32, 56, 176, 208, false);
                h4.a(h5);
                h h6 = h.g();
                h6.a(0, 32, 56, 176, 208, false);
                h4.a(h6);
                h5 = h.g();
                h5.e(50);
                h5.f = false;
                h4.a(h5);
                h5 = h.g();
                h5.a(56, 39, 130, 162, 60, false);
                h4.a(h5);
                h5 = h.g();
                h5.a(27, 0, 0, 240, 320, false);
                h4.a(h5);
                h5 = h.g();
                h5.e(53);
                h5.f = false;
                h4.a(h5);
                h5 = h.g();
                h5.a(51, 0, 0, 240, 320, false);
                h4.a(h5);
                break;
            }
            case 56: {
                h h7 = this;
                this.u = 2;
                h7.z = i.R;
                h7.a(3, 4, 5, 14543359, 0);
                h7.a(4112, 1032, 264224, false, 21);
                break;
            }
            case 45: {
                this.a(3, 4, 5, 14876594, 0);
                break;
            }
            case 12: 
            case 13: {
                h h8;
                h h9 = this;
                h9.a(0, 1, 2, 6723007, 0);
                h9.a(16388, 258, 264224, true, 15);
                h9.u = 2;
                for (int i2 = 0; i2 < h9.u; ++i2) {
                    h8 = h.g();
                    h8.a(45, 38, 76 + i2 * 84, 164, 84, true);
                    h9.a(h8);
                }
                h h10 = h.g();
                h10.a(56, 39, 130, 162, 60, false);
                h9.a(h10);
                h8 = h.g();
                h8.a(57, 50, i.ao, 140, i.an, false);
                h9.a(h8);
                h10 = h.g();
                h10.a(59, 32, 56, 176, 208, true);
                h9.a(h10);
                break;
            }
            case 47: {
                h h11 = this;
                h11.a(0, 1, 2, 6723007, 0);
                h11.a(16388, 258, 264224, true, 29);
                h11.u = 2;
                h h12 = h.g();
                h12.a(59, 32, 56, 176, 208, true);
                h11.a(h12);
                break;
            }
            case 48: {
                h h13 = this;
                h13.a(0, 1, 2, 6723007, 0);
                h13.i = 1215;
                h13.a(16388, 258, 264224, false, 29);
                h h14 = h.g();
                h14.a(59, 32, 56, 176, 208, true);
                h13.a(h14);
                break;
            }
            case 49: {
                h h15 = this;
                h15.a(0, 1, 2, 6723007, 0);
                h15.i = 1242;
                h15.a(16388, 258, 264224, false, 29);
                h h16 = h.g();
                h16.a(59, 32, 56, 176, 208, true);
                h15.a(h16);
                break;
            }
            case 50: {
                break;
            }
            case 1: {
                h h17 = this;
                this.u = 6;
                h17.a(201, 202, 203, 14543359, 0);
                h17.a(4112, 1032, 264224, true, 2);
                h h18 = h.g();
                h18.e(0);
                h17.a(h18);
                c.a();
                break;
            }
            case 14: {
                h h19 = this;
                h19.a(3, 4, 5, 14876594, 0);
                h19.b(8);
                break;
            }
            case 36: {
                h h20 = this;
                h20.a(3, 4, 5, 14876594, 0);
                h20.b(8);
                break;
            }
            case 5: {
                h h21;
                h h22 = this;
                this.u = c.p;
                h22.a(0, 1, 2, 6723007, 0);
                h22.a(4112, 1032, 264224, true, 3);
                h22.b(8);
                for (int i3 = 0; i3 < c.p; ++i3) {
                    h21 = h.g();
                    h21.a(15, 36 + i3 * 56, 78, 55, 163, true);
                    h21.c(c.q[i3]);
                    h22.a(h21);
                }
                h h23 = h.g();
                h23.a(60, 32, 159, 176, 90, false);
                h23.a(-1, -1, 264224, false, 3);
                h22.a(h23);
                h21 = h.g();
                h21.a(57, 50, i.ao, 140, i.an, false);
                h22.a(h21);
                h23 = h.g();
                h23.a(59, 32, 56, 176, 208, true);
                h22.a(h23);
                break;
            }
            case 15: {
                h h24 = this;
                h24.a(3, 4, 5, 14876594, 0);
                h24.b(8);
                break;
            }
            case 60: {
                h h25 = this;
                h25.a(3, 4, 5, 14876594, 0);
                h25.b(8);
                break;
            }
            case 52: {
                h h26 = this;
                h26.a(6, 7, 8, 0xFFFFFF, 0);
                h26.b(16);
                h26.u = 8;
                h26.v = 6;
                break;
            }
            case 6: {
                this.x();
                break;
            }
            case 16: {
                h h27 = this;
                h27.a(-1, -1, -1, -1, 0);
                h27.a(4112, 1032, 264224, true, 4);
                h27.u = c.p;
                h27.b(8);
                h27.c(-1);
                break;
            }
            case 17: {
                h h28 = this;
                h28.a(0, 1, 2, 6723007, 0);
                h28.a(0, 0, 264224, false, 36);
                h28.u = 4;
                h28.b(8);
                h h29 = h.g();
                h29.a(14, 38, 172, 164, 75, true);
                h29.p[2] = 1;
                h29.p[0] = 2;
                h28.a(h29);
                h h30 = h.g();
                h30.a(58, 96, 88, 25, 23, true);
                h30.B = 4;
                h30.v = 4;
                h30.i = 1;
                h30.c = h.g();
                h30.c.a(19, 0, 0, 75, 102, false);
                h28.a(h30);
                h28.b(h29);
                h28.b(h30);
                break;
            }
            case 19: {
                h h31 = this;
                h31.a(6, 7, 8, 14543359, 1);
                h31.b(22);
                break;
            }
            case 20: {
                h h32 = this;
                h32.a(-1, -1, -1, -1, 0);
                h32.a(16388, 258, 264224, true, 6);
                h32.u = 3;
                break;
            }
            case 9: {
                this.y();
                break;
            }
            case 18: {
                h h33 = this;
                h33.a(0, 1, 2, 6723007, 0);
                h33.a(0, 0, 264224, false, 0);
                h33.b(12);
                h h34 = h.g();
                h34.a(57, 50, i.ao, 140, i.an, false);
                h33.a(h34);
                h h35 = h.g();
                h35.a(59, 32, 56, 176, 208, true);
                h33.a(h35);
                break;
            }
            case 7: {
                this.z();
                break;
            }
            case 24: {
                h h36 = this;
                h36.a(-1, -1, -1, -1, 0);
                h36.a(4112, 1032, 264224, false, 25);
                h36.u = c.p;
                c.m[h36.g] = 0;
                break;
            }
            case 10: {
                this.v();
                break;
            }
            case 25: {
                this.w();
                break;
            }
            case 2: {
                this.A();
                break;
            }
            case 4: {
                this.B();
                break;
            }
            case 40: {
                this.q();
                break;
            }
            case 41: {
                this.r();
                break;
            }
            case 43: {
                this.s();
                break;
            }
            case 42: {
                this.t();
                break;
            }
            case 57: {
                this.p();
                break;
            }
            case 34: {
                this.C();
                break;
            }
            case 35: {
                h h37 = this;
                h37.b(8);
                break;
            }
            case 32: {
                this.u();
                break;
            }
            case 33: {
                this.n();
                break;
            }
            case 11: {
                this.o();
                break;
            }
            case 37: {
                this.D();
                break;
            }
            case 38: {
                this.E();
                break;
            }
            case 39: {
                this.F();
                break;
            }
            case 8: {
                this.G();
                break;
            }
            case 21: {
                this.H();
                break;
            }
            case 22: {
                this.I();
                break;
            }
            case 23: {
                this.J();
                break;
            }
            case 61: {
                this.K();
                break;
            }
            case 62: {
                this.L();
                break;
            }
            case 51: {
                this.M();
                break;
            }
            case 59: {
                this.a(-1, -1, -1, -1, 0);
            }
        }
        c.m[this.g] = 0;
    }

    private void n() {
        this.a(6, 7, 8, 16770483, 0);
        this.a(258, 16388, 264224, false, 26);
    }

    private void o() {
        this.u = 2;
        this.a(0, 1, 2, 6723007, 0);
        this.a(4112, 1032, 0, false, 13);
        h h2 = h.g();
        h2.e(55);
        h2.a(16388, 258, 0, false, 14);
        h2.v = 3;
        c.a(0, h2, c.d[6]);
        this.a(h2);
        h h3 = h.g();
        h3.a(14, this.t[0] + 6, this.t[1] + 116, 164, 75, true);
        h3.p[1] = h2.C == null ? -1 : h2.C.d;
        h3.p[0] = 6;
        h3.p[2] = 1;
        if (h2.C != null) {
            h3.d(c.a(c.c(522 + h2.C.d), 164, 1));
        }
        this.a(h3);
        this.b(h2);
        this.b(h3);
        h2 = h.g();
        h2.a(59, 32, 56, 176, 208, true);
        this.a(h2);
    }

    final void a(int n2, int n3, int n4, int n5, int n6, boolean bl2) {
        this.a(n3, n4, n5, n6);
        this.f = bl2;
        this.e(n2);
    }

    private void p() {
        this.a(3, 4, 5, 14543359, 0);
        this.z = i.R;
    }

    private void q() {
        this.a(0, 1, 2, 6723007, 0);
        this.a(32, 83, 176, 155);
        this.v = 4;
        this.b(32);
        c.f(this);
        this.u = (this.q - 2) / 3;
    }

    private void r() {
        this.a(0, 1, 2, 6723007, 0);
        this.a(45, 75, 150, 170);
        this.b(12);
        c.g(this);
        this.u = this.q / 3;
        h h2 = h.g();
        h2.a(43, 32, i.av, 176, i.au, false);
        this.a(h2);
    }

    private void s() {
        this.a(0, 1, 2, 6723007, 0);
        this.b(8);
    }

    private void t() {
        this.a(6, 7, 8, 14543359, 0);
        this.a(32, i.ax, 176, i.aw);
    }

    private void u() {
        this.a(0, 1, 2, 6723007, 0);
        this.a(4112, 1032, 264224, true, 19);
        this.u = 2;
        h h2 = h.g();
        h2.a(58, 47, 106, 25, 25, true);
        h2.v = 4;
        h2.B = 6;
        h2.D = h2.C = c.d[3].b;
        h2.u = h2.C == null ? 0 : h2.C.e();
        this.a(h2);
        h h3 = h.g();
        h3.a(14, 43, 193, 155, 49, true);
        h3.p[2] = 0;
        this.a(h3);
        h h4 = h.g();
        h4.a(33, 70, 120, 100, 80, false);
        this.a(h4);
        h4 = h.g();
        h4.a(57, 50, i.ao, 140, i.an, false);
        this.a(h4);
        h2.b(h3);
        h2 = h.g();
        h2.a(59, 32, 56, 176, 208, true);
        this.a(h2);
    }

    private void v() {
        this.a(0, 1, 2, 6723007, 0);
        this.a(16388, 258, 264224, false, 11);
        this.v = 3;
        this.D = this.C = c.g[c.q[0]].J.b;
        this.u = this.C == null ? 0 : this.C.e();
        h h2 = h.g();
        h2.a(24, 44, 92, 0, 0, true);
        this.a(h2);
        h h3 = h.g();
        h3.a(25, 32, 56, 176, 208, false);
        h3.d(c.a(c.c(325 + this.C.d), h3.t[2] - 10, 1));
        this.a(h3);
        h3 = h.g();
        h3.a(14, 38, 172, 164, 75, false);
        h3.f = true;
        h3.p[0] = 1;
        h3.p[2] = 1;
        h3.p[1] = this.C != null ? this.C.d : -1;
        this.a(h3);
        this.b(h2);
        this.b(h3);
        h3.d(c.a(c.c(325 + this.C.d), h3.t[2] - 10, 1));
        h2 = h.g();
        h2.a(59, 32, 56, 176, 208, true);
        this.a(h2);
        h2 = h.g();
        h2.a(57, 50, i.ao, 140, i.an, false);
        this.a(h2);
    }

    private void w() {
        this.a(0, 1, 2, 6723007, 0);
        this.a(0, 0, 264224, false, 32);
        this.b(18);
        h h2 = h.g();
        h2.a(57, 50, i.ao, 140, i.an, false);
        this.a(h2);
        h2 = h.g();
        h2.a(59, 32, 56, 176, 208, true);
        this.a(h2);
    }

    private void x() {
        this.u = i.aJ.length;
        this.a(0, 1, 2, 6723007, 0);
        this.a(16388, 258, 264224, true, 5);
        h h2 = h.g();
        h2.a(16, 32, 56, 176, 89, true);
        this.a(h2);
        h h3 = h.g();
        h3.a(20, this.t[0] + 62, this.t[1] + 23, 52, 72, false);
        this.a(h3);
        h3 = h.g();
        h3.a(17, 32, 56, 176, 208, false);
        this.a(h3);
        h3 = h.g();
        h3.a(18, 32, 56, 176, 208, false);
        this.a(h3);
        h3 = h.g();
        h3.a(14, this.t[0] + 6, this.t[1] + 160, 164, 30, true);
        h3.p[2] = c.g[c.q[c.m[h2.g]]].G[c.m[this.g]] >= 0 ? 1 : 0;
        h3.p[0] = 2;
        h3.p[1] = c.g[c.q[0]].G[0];
        this.a(h3);
        int n2 = c.m[h2.g];
        int n3 = c.m[h3.g];
        a a2 = c.g[c.q[n2]];
        int n4 = a2.G[n3];
        h3.d(c.a(c.c(n4 + 194), 164, 1));
        h h4 = h.g();
        h4.a(57, 50, i.ao, 140, i.an, false);
        this.a(h4);
        this.b(h2);
        this.b(h3);
        h2 = h.g();
        h2.a(59, 32, 56, 176, 208, true);
        this.a(h2);
    }

    private void y() {
        this.a(0, 1, 2, 6723007, 0);
        this.a(16388, 258, 264224, true, 5);
        this.u = 4;
        h h2 = h.g();
        h2.a(14, 38, 172, 164, 75, true);
        h2.p[0] = 2;
        this.a(h2);
        h h3 = h.g();
        h3.a(58, 96, 88, 25, 23, true);
        h3.B = 4;
        h3.v = 4;
        c.a(i.aJ[0], h3, -1);
        this.a(h3);
        h h4 = h.g();
        h4.a(18, 32, 56, 176, 208, false);
        this.a(h4);
        h4 = h.g();
        h4.a(57, 50, i.ao, 140, i.an, false);
        this.a(h4);
        h3.b(h2);
        h3.c = h.g();
        h3.c.a(19, 0, 0, 75, 102, false);
        h2 = h.g();
        h2.a(59, 32, 56, 176, 208, true);
        this.a(h2);
    }

    private void z() {
        this.u = 3;
        this.a(0, 1, 2, 6723007, 0);
        this.a(16388, 258, 264224, true, 7);
        this.b(8);
        h h2 = h.g();
        h2.a(14, 38, 171, 164, 75, true);
        h2.p[2] = 0;
        h2.p[0] = 0;
        this.a(h2);
        h h3 = h.g();
        h3.a(58, 96, 87, 25, 23, true);
        h3.v = 4;
        h3.B = 4;
        c.a(0, h3, c.d[0]);
        h3.b(h2);
        this.a(h3);
        h2 = h.g();
        h2.a(5, 32, 56, 176, 208, false);
        this.a(h2);
        h2 = h.g();
        h2.a(57, 50, i.ao, 140, i.an, false);
        this.a(h2);
        h2 = h.g();
        h2.a(52, 40, 90, 160, 140, false);
        this.a(h2);
        h2 = h.g();
        h2.a(59, 32, 56, 176, 208, true);
        this.a(h2);
    }

    private void A() {
        this.a(0, 1, 2, 6723007, 0);
        this.a(16388, 258, 264224, true, 9);
        this.u = 5;
        h h2 = h.g();
        h2.e(0);
        this.a(h2);
        h2 = h.g();
        h2.a(56, 39, 130, 162, 60, false);
        this.a(h2);
        h2 = h.g();
        h2.a(57, 50, i.ao, 140, i.an, false);
        this.a(h2);
        h2 = h.g();
        h2.a(59, 32, 56, 176, 208, false);
        this.a(h2);
    }

    private void B() {
        this.a(0, 1, 2, 6723007, 0);
        this.a(16388, 258, 264224, false, 33);
        this.v = this.u = c.d(12, -1);
        this.D = this.C = c.e.b;
        h h2 = h.g();
        h2.a(32, 32, 56, 176, 208, false);
        h2.b(8);
        this.a(h2);
    }

    private void C() {
        this.u = 4;
        this.v = 4;
        this.a(0, 0, 264224, true, 17);
    }

    private void D() {
        this.a(0, 1, 2, 6723007, 0);
        h h2 = h.g();
        h.g().g = this.g;
        h2.e(55);
        h2.a(16388, 258, 264224, false, 11);
        h2.D = h2.C = c.c();
        h2.u = h2.C.e();
        h2.v = 4;
        this.a(h2);
        h h3 = h.g();
        h3.a(57, 50, i.ao, 140, i.an, false);
        this.a(h3);
        h3 = h.g();
        h3.a(36, this.t[0] + 6, this.t[1] + 116, 164, 75, true);
        h3.p[0] = 1;
        h3.p[1] = h2.C.d;
        this.a(h3);
        this.b(h2);
        this.b(h3);
        h2.d(c.a(c.c(590 + h2.C.d), 90, 1));
        h3.d(c.a(c.c(325 + h2.C.d), h3.t[2] - 10, 1));
        h2 = h.g();
        h2.a(59, 32, 56, 176, 208, true);
        this.a(h2);
    }

    private void E() {
        this.a(0, 1, 2, 6723007, 0);
        this.g = 12;
        h h2 = h.g();
        h2.a(58, this.t[0] + 33, this.t[1] + 33, 25, 23, true);
        h2.B = 5;
        h2.v = 4;
        h2.D = h2.C = c.d[5].b;
        h2.u = h2.C.e();
        h2.i = 1;
        this.a(h2);
        h h3 = h.g();
        h3.a(36, this.t[0] + 6, this.t[1] + 116, 164, 75, true);
        h3.p[0] = 5;
        h3.p[1] = h2.C.d;
        this.a(h3);
        this.b(h3);
        h3.d(c.a(c.c(67 + h2.C.d), h3.t[2], 1));
        h3 = h.g();
        h3.a(57, 50, i.ao, 140, i.an, false);
        this.a(h3);
        this.b(h2);
        h2 = h.g();
        h2.a(59, 32, 56, 176, 208, true);
        this.a(h2);
    }

    private void F() {
        this.a(201, 202, 203, 16770483, 0);
        this.b(8);
    }

    private void G() {
        this.a(0, 1, 2, 6723007, 0);
        this.a(4112, 1032, 264224, true, 34);
        this.u = 2;
        h h2 = h.g();
        h2.a(21, 39, 91, 162, 80, true);
        this.a(h2);
        h h3 = h.g();
        h3.a(14, 39, 171, 162, 75, true);
        h3.p[0] = 10;
        h3.p[1] = h2.C == null ? -1 : h2.C.d;
        h3.p[2] = 1;
        this.a(h3);
        h h4 = h.g();
        h4.a(22, 32, 56, 176, 208, false);
        this.a(h4);
        h4 = h.g();
        h4.a(57, 50, i.ao, 140, i.an, false);
        this.a(h4);
        this.b(h2);
        this.b(h3);
        h2 = h.g();
        h2.a(59, 32, 56, 176, 208, true);
        this.a(h2);
    }

    private void H() {
        this.a(-1, -1, -1, -1, 0);
        this.v = 64 / i.R;
        this.a(16388, 258, 264224, false, 35);
        c.a(0, this, c.d[10]);
        int n2 = this.u > 0 ? this.C.d : 0;
        this.d(c.a(c.c(n2 + 732), 95, 0));
    }

    private void I() {
        this.a(0, 1, 2, 6723007, 0);
        this.a(0, 0, 264224, false, 0);
        this.b(12);
        h h2 = h.g();
        h2.a(23, 32, 86, 176, i.ae, false);
        this.a(h2);
        h2 = h.g();
        h2.a(61, 38, 123, 165, 75, false);
        this.a(h2);
    }

    private void J() {
        this.a(6, 7, 8, 16770483, 0);
        this.b(12);
    }

    private void K() {
        this.a(3, 4, 5, 14876594, 0);
        F.b(this.t[0] + (this.t[2] >> 1), this.t[1] + (this.t[3] >> 1));
        h.F.k = false;
    }

    private void L() {
        this.a(-1, -1, -1, -1, 0);
        c.c.b(120, 160);
        c.c.k = false;
    }

    private void M() {
        this.a(-1, -1, -1, 0, 0);
    }

    static {
        l = false;
        m = -1;
        n = -1;
        o = false;
        H = false;
        I = 0;
        N = null;
        O = 0;
        E = 0;
    }
}

