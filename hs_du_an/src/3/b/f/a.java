/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Displayable
 */
package b.f;

import a.f;
import b.a.c;
import b.b.g;
import b.b.h;
import b.b.l;
import b.f.b;
import c.m;
import d.d;
import d.j;
import d.k;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import javax.microedition.lcdui.Displayable;
import main.MainMIDlet;

public final class a
extends b {
    private static String[] y = new String[]{"\u5b58\u6863", "\u8fd4\u56de"};
    private static String[] z = new String[]{"\u4e0a\u4f20\u6210\u5c31", "\u4e0d\u7528\u4e86"};
    private static String[] A = new String[]{"\u8d2d\u4e70", "\u5356\u51fa", "\u79bb\u5f00"};
    private static String[] B = new String[]{"\u5f00\u542f!", "\u4e0d\u7528\u4e86"};

    public a(int n2) {
        super(n2);
    }

    /*
     * Unable to fully structure code
     */
    public final void a() {
        if (this.b()) {
            return;
        }
        block0 : switch (this.c) {
            case 5: {
                if (this.o == 0) {
                    this.d = a.f.w.a(this.g);
                    if (this.d == null) {
                        this.c();
                        break;
                    }
                    if (this.h == 0) {
                        this.h = (short)4;
                    }
                    this.n = (this.i << 4) / this.h;
                    if (!this.d.A) {
                        this.d.a(this.l, true);
                    }
                    this.d.d(this.l);
                    this.d.o = 0;
                }
                this.d.a(this.k, this.h);
                ++this.o;
                if (this.o >= this.n) {
                    this.d.o = 1;
                    this.c();
                    if (!this.d.A) {
                        this.d.a(this.l, false);
                    }
                }
                return;
            }
            case 1: {
                var1_1 = a.f.c("head_r" + this.k);
                if (var1_1 == null && (var1_1 = b.a.c.b("head_r" + this.k)) != null) {
                    a.f.a((c)var1_1);
                }
                var2_27 = null;
                if (var1_1 != null) {
                    var2_27 = var1_1.b(this.m);
                }
                var3_36 = null;
                if (this.g == 0 && (var1_1 = a.f.w.a(this.h)) != null) {
                    this.s = var1_1.p;
                }
                var3_36 = b.b.g.a(this.r, this.s, var2_27, this.l);
                var3_36.k();
                this.c();
                return;
            }
            case 2: {
                this.k();
                return;
            }
            case 6: {
                v0.d = a.f.w.a(this.k);
                if (this.d != null) {
                    this.d.a(this.l, false);
                    this.d.d(this.l);
                }
                this.c();
                return;
            }
            case 7: {
                if (this.o == 0) {
                    var2_28 = this;
                    if ((var2_28.k & 15) == 2) {
                        var3_37 = a.f.w.a(var2_28.h);
                        if (var3_37 != null) {
                            var2_28.e = var3_37.s + 8 - main.a.f;
                            var2_28.f = var3_37.t - 8 - main.a.g;
                        } else {
                            var2_28.e = a.f.w.i;
                            var2_28.f = a.f.w.j;
                        }
                    } else {
                        var2_28.e = (var2_28.e << 4) + 8 - main.a.f;
                        var2_28.f = (var2_28.f << 4) + 8 - main.a.g;
                    }
                    a.f.u = 2;
                    if (var2_28.e < 0) {
                        var2_28.e = 0;
                    } else if (var2_28.e + main.a.d > a.f.w.h()) {
                        var2_28.e = a.f.w.h() - main.a.d;
                    }
                    if (var2_28.f < 0) {
                        var2_28.f = 0;
                    } else if (var2_28.f + main.a.e > a.f.w.i()) {
                        var2_28.f = a.f.w.i() - main.a.e;
                    }
                    ++this.o;
                }
                switch (this.k >> 4 & 15) {
                    case 2: {
                        a.f.w.a(this.e, this.f, (int)this.g);
                        break;
                    }
                    case 1: {
                        a.f.w.d(this.e, this.f);
                        break;
                    }
                    case 3: {
                        a.f.w.i = this.e;
                        a.f.w.j = this.f;
                    }
                }
                if (a.f.w.j == this.f && a.f.w.i == this.e) {
                    if ((this.k & 15) == 2 && this.l == 1 && (var1_2 = a.f.w.a(this.h)) != null) {
                        a.f.u = 1;
                        a.f.v = this.h;
                    }
                    this.c();
                }
                return;
            }
            case 8: {
                var1_3 = a.f.w.a(this.k);
                if (var1_3 == null) {
                    this.c();
                    break;
                }
                a.f.u = 1;
                a.f.v = this.k;
                this.c();
                return;
            }
            case 9: {
                if (this.i != -1 && this.i != a.f.w.k) {
                    a.f.x = this.i;
                    a.f.y = this.g;
                    a.f.z = this.h;
                    main.a.a.k.h = new j();
                } else {
                    a.f.A[0].s = this.g << 4;
                    a.f.A[0].t = this.h << 4;
                }
                this.c();
                return;
            }
            case 10: {
                switch (this.o) {
                    case 0: {
                        if (this.d == null) {
                            this.d = a.f.w.a(this.g);
                        }
                        if (this.d == null || this.d.j == null) {
                            this.c();
                            break block0;
                        }
                        this.d.a(this.h, (int)this.k);
                        if (this.d.k == null) {
                            this.c();
                            break block0;
                        }
                        if (this.e == 1) {
                            this.d.k.a(true);
                        } else {
                            this.d.k.a(false);
                        }
                        ++this.o;
                        if (this.k == -1) {
                            this.c();
                            ++this.o;
                        }
                    }
                    case 1: {
                        if (!this.d.k()) break;
                        this.c();
                        ++this.o;
                        if (this.f == 1 || this.d.k != null && this.d.k.e() == -1) break;
                        this.d.a(this.i, -1);
                    }
                }
                return;
            }
            case 12: {
                a.f.a(this.k, this.l);
                this.c();
                return;
            }
            case 14: {
                this.c();
                var1_4 = a.f.w.b(this.g);
                if (var1_4 != null) {
                    var1_4.f = (this.k & 1) != 0;
                    var1_4.g = (this.k >> 1 & 1) != 0;
                    v1 = var1_4.h = (this.k >> 2 & 1) != 0;
                    if (this.r != null && var1_4.i != null && !var1_4.i.endsWith(this.r) && !this.r.equals("")) {
                        var1_4.p();
                        var1_4.i = this.r;
                    }
                    if (var1_4.f && var1_4.j == null) {
                        var1_4.i();
                        var1_4.a(var1_4.l, -1);
                    } else if (!var1_4.f) {
                        var1_4.p();
                    }
                    if (this.h != -1) {
                        var1_4.s = this.h << 4;
                    }
                    if (this.i != -1) {
                        var1_4.t = this.i << 4;
                    }
                    if (this.l != 4) {
                        var1_4.a(this.l);
                    }
                    if (this.m != 2) {
                        var1_4.b(this.m);
                        if (this.m == 1) {
                            var1_4.a((short)this.e);
                            var1_4.b((short)this.f);
                        }
                    }
                }
                return;
            }
            case 15: {
                this.c();
                if (this.d != null) ** GOTO lbl178
                this.d = a.f.b(this.g);
                if (this.d == null) {
                    this.d = a.f.a(this.g);
                }
                if (this.d == null) ** GOTO lbl179
lbl178:
                // 2 sources

                ((a.g)this.d).a(this.l, this.k, this.h);
lbl179:
                // 2 sources

                return;
            }
            case 16: {
                switch (this.o) {
                    case 0: {
                        var1_5 = -1;
                        switch (this.k) {
                            case 0: {
                                var1_5 = b.e.a.a((int)a.f.a(this.l));
                                break;
                            }
                            case 1: {
                                if (this.d == null) {
                                    this.c();
                                    break block0;
                                }
                                var1_5 = ((a.g)this.d).c((int)this.l);
                            }
                        }
                        var4_44 = this.e;
                        var2_29 = this.m;
                        switch (var2_29) {
                            case 0: {
                                if (var1_5 > var4_44) {
                                    v2 = 1;
                                    break;
                                }
                                v2 = 0;
                                break;
                            }
                            case 2: {
                                if (var1_5 < var4_44) {
                                    v2 = 1;
                                    break;
                                }
                                v2 = 0;
                                break;
                            }
                            case 1: {
                                if (var1_5 == var4_44) {
                                    v2 = 1;
                                    break;
                                }
                                v2 = 0;
                                break;
                            }
                            default: {
                                v2 = var2_29 = 0;
                            }
                        }
                        if (v2 == 0) {
                            this.c();
                            break block0;
                        }
                        this.o = 1;
                        break block0;
                    }
                    case 1: {
                        var3_38 = this.l();
                        if (!var3_38) break;
                        this.c();
                    }
                }
                return;
            }
            case 17: {
                this.f();
                return;
            }
            case 4: 
            case 18: 
            case 19: 
            case 20: {
                var1_6 = this.l();
                if (var1_6) {
                    this.c();
                }
                return;
            }
            case 24: {
                a.f.q = null;
                return;
            }
            case 26: {
                var1_7 = null;
                switch (this.k) {
                    case 1: {
                        var1_7 = b.b.g.a(this.r);
                        break;
                    }
                    case 0: {
                        var1_7 = b.b.g.a(this.r, 4000L);
                        break;
                    }
                    default: {
                        this.c();
                    }
                }
                var1_7.k();
                this.c();
                return;
            }
            case 28: {
                this.c();
                this.t.c();
                this.t.t.c();
                return;
            }
            case 27: {
                var1_8 = this.l();
                if (var1_8) {
                    this.c();
                }
                return;
            }
            case 31: {
                if (this.o == 0) {
                    main.a.a.k.h = d.d.a((b)this);
                    ++this.o;
                }
                return;
            }
            case 29: {
                if (this.o == 0) {
                    this.d.f = true;
                    ++this.o;
                }
                this.d.s = b.e.a.b(this.d.s, this.h, this.g);
                this.d.t = b.e.a.b(this.d.t, this.i, this.g);
                if (this.d.s == this.h && this.d.t == this.i) {
                    if (this.k == 1) {
                        this.d.f = false;
                        d.k.b(this.d);
                    }
                    this.c();
                }
                return;
            }
            case 32: {
                switch (this.o) {
                    case 0: {
                        var1_9 = this;
                        this.d = a.f.w.a(var1_9.g);
                        if (var1_9.d == null) {
                            var1_9.c();
                        } else {
                            var2_30 = a.f.w.a(var1_9.d.n(), var1_9.d.o(), var1_9.h, var1_9.i);
                            if (var2_30 == null) {
                                var1_9.d.s = var1_9.h << 4;
                                var1_9.d.t = var1_9.i << 4;
                                var1_9.d.a(var1_9.k, false);
                                var1_9.c();
                            } else {
                                var1_9.p = b.f.b.a(var2_30, var1_9.d, var1_9.l);
                            }
                        }
                        if (this.b()) break;
                        this.o = 1;
                    }
                    case 1: {
                        if (!this.l()) break;
                        this.d.a(this.k, false);
                    }
                    default: {
                        this.c();
                    }
                }
                return;
            }
            case 34: {
                a.f.d(this.k, this.l);
                this.c();
                return;
            }
            case 35: {
                this.j();
                return;
            }
            case 36: {
                a.f.a(this.g, this.k, this.h);
                this.c();
                return;
            }
            case 37: {
                if (this.o == 0) {
                    ++this.o;
                    var1_10 = a.f.w.c(this.k);
                    if (var1_10 == null) {
                        this.c();
                        break;
                    }
                    this.p = new b[]{var1_10};
                }
                if (var1_11 = this.l()) {
                    this.c();
                }
                return;
            }
            case 38: {
                if (a.f.x() || this.k != 1) ** GOTO lbl350
                b.b.g.a("\u4e60\u5f97\u4e07\u80fd\u94a5\u5319\u6280\u80fd,\u53ef\u4ee5\u65e0\u9650\u6b21\u5f00\u542f\u73cd\u8d35\u5b9d\u7bb1", 3000L).k();
                var1_12 = b.b.g.a(b.f.a.B, (byte)0, (byte)1, false);
                if (var1_12.k() != 1) ** GOTO lbl348
                var2_31 = var1_12.a();
                if (var2_31.equals("\u5f00\u542f!")) {
                    var3_39 = false;
                    g.a.a = null;
                    var3_39 = g.a.a(MainMIDlet.a, (Displayable)main.a.a, "\u4e60\u5f97\u4e07\u80fd\u94a5\u5319\u6280\u80fd", "0110", String.valueOf(9), "0", 200, false);
                    if (var3_39) {
                        a.f.e(true);
                        a.f.a(1001, 2);
                        g.a.a(String.valueOf(9), 0);
                        a.f.o();
                        b.b.g.a("\u8c22\u8c22\u60a8\u7684\u652f\u6301,\u5df2\u6210\u529f\u4e60\u5f97\u4e07\u80fd\u94a5\u5319\u6280\u80fd", 3000L).k();
                    }
                } else {
                    this.c();
                    break;
lbl348:
                    // 1 sources

                    this.c();
                    break;
                }
lbl350:
                // 2 sources

                if (this.o == 0) {
                    var1_12 = a.f.b((int)this.g);
                    this.p = new b[4];
                    this.p[0] = new a(10);
                    this.p[0].g = a.f.r;
                    this.p[0].h = (short)2;
                    this.p[0].k = 0;
                    this.p[0].i = 1;
                    this.p[1] = new a(36);
                    this.p[1].g = this.g;
                    this.p[1].k = 0;
                    this.p[1].h = this.h;
                    this.p[2] = new a(26);
                    var2_31 = new StringBuffer();
                    if (this.g == -1) {
                        var2_31.append(a.f.o[31]);
                        var2_31.append(a.f.o[32]);
                        var2_31.append(this.h);
                    } else if (var1_12 == null) {
                        var2_31.append(a.f.o[3]);
                    } else {
                        var2_31.append(a.f.o[31]);
                        var2_31.append(var1_12.e);
                        var2_31.append(this.h);
                        var2_31.append(a.f.o[40]);
                    }
                    this.p[2].r = var2_31.toString();
                    this.p[2].k = 0;
                    this.p[3] = new a(14);
                    this.p[3].g = a.f.r;
                    this.p[3].k = 1;
                    this.p[3].l = 0;
                    this.p[3].m = 0;
                    this.p[3].h = (short)-1;
                    this.p[3].i = (short)-1;
                    ++this.o;
                }
                if (var1_13 = this.l()) {
                    this.c();
                }
                return;
            }
            case 39: {
                if (this.o == 0) {
                    this.d = new c.h(this.r);
                    this.d.i();
                    this.d.a((int)this.k, (int)this.l);
                    if (this.d == null || this.d.k == null) {
                        this.c();
                        break;
                    }
                    d.k.a(this.d);
                    this.d.f = true;
                    if (this.m == 0) {
                        if (a.f.w == null) {
                            this.c();
                            break;
                        }
                        this.d.s = this.h - a.f.w.i;
                        this.d.t = this.i - a.f.w.j;
                    } else if (this.m == 1) {
                        this.d.s = this.h;
                        this.d.t = this.i;
                    }
                    ++this.o;
                    break;
                }
                this.d.j();
                if (this.d.k()) {
                    this.c();
                    d.k.b(this.d);
                    this.d.j = null;
                    this.d = null;
                }
                return;
            }
            case 40: {
                this.c();
                var1_14 = b.b.g.a(b.f.a.A, (byte)0, (byte)1, true);
                var2_32 = var1_14.k();
                if (var2_32 == 1) {
                    var3_40 = var1_14.a();
                    var1_15 = (main.a.d - main.a.h) / 2;
                    var2_32 = (main.a.e - main.a.i) / 2;
                    var4_45 = new h(var1_15, var2_32, main.a.h, main.a.i);
                    if (var3_40.equals("\u8d2d\u4e70")) {
                        if (b.f.b.u == null || b.f.b.u.length == 0) break;
                        this = new c.j(b.f.b.u);
                        var4_45.a((l)this);
                    } else {
                        if (!var3_40.equals("\u5356\u51fa")) break;
                        this = new c.k();
                        var4_45.a((l)this);
                    }
                    var4_45.k();
                }
                b.f.b.u = null;
                return;
            }
            case 41: {
                this.c();
                if (this.v != null) {
                    this.v.a((a.g)this.d, this.w, this.x);
                    this.v = null;
                    this.w = null;
                    this.x = null;
                }
                return;
            }
            case 42: {
                this.i();
                return;
            }
            case 43: {
                if (this.o == 0) {
                    var1_16 = false;
                    var1_16 = a.f.b(this.g, (int)this.h);
                    this.e = var1_16 != false ? 1 : 0;
                    ++this.o;
                }
                if (this.a(this.e == 1)) {
                    this.c();
                }
                return;
            }
            case 46: {
                main.a.a.k.a(new d.h());
                this.c();
                return;
            }
            case 48: {
                this.c();
                b.b.g.a("\u4f60\u7684\u6210\u5c31\u503c\u8fbe\u5230\u4e86:" + a.f.b, 3000L).k();
                var1_17 = b.b.g.a(b.f.a.z, (byte)0, (byte)1, true);
                if (var1_17.k() == 1 && var1_17.a().equals("\u4e0a\u4f20\u6210\u5c31")) {
                    g.a.a(MainMIDlet.a, (Displayable)main.a.a, this.s, "0110", String.valueOf(a.f.b), this.r);
                }
                return;
            }
            case 50: {
                this.c();
                g.a.a(MainMIDlet.a, (Displayable)main.a.a, "0110");
                var1_18 = true;
                var2_33 = g.a.a();
                if (var2_33.length < 3 || var2_33[2] == null || var2_33[2].trim().length() == 0) {
                    var1_18 = false;
                }
                var3_41 = null;
                var3_41 = var1_18 ? ((var1_18 = b.f.a.g()) ? "\u4e0a\u4f20\u5b58\u6863\u6210\u529f!" : "\u4e0a\u4f20\u5b58\u6863\u5931\u8d25!") : "\u672a\u767b\u9646,\u4e0a\u4f20\u5b58\u6863\u5931\u8d25!";
                var1_19 = b.b.g.a(var3_41, 3000L);
                var1_19.k();
                return;
            }
            case 51: {
                this.c();
                a.f.ah = true;
                return;
            }
            case 52: {
                this.c();
                a.f.ah = false;
                return;
            }
            case 53: {
                if (this.o >= this.g) {
                    this.c();
                    break;
                }
                ++this.o;
                return;
            }
            case 54: {
                if (this.o == 0) {
                    if (this.m <= 0) {
                        this.c();
                        break;
                    }
                    this.d = a.f.w.a(this.k);
                    if (this.d == null) {
                        this.c();
                        break;
                    }
                    this.n = this.m << 2;
                    this.h = 0;
                }
                if (this.h <= 0) {
                    var1_20 = a.b.a(this.d.r, this.l);
                    this.d.d(var1_20);
                    this.d.a(var1_20, false);
                    this.h = this.g;
                    ++this.o;
                } else {
                    this.h = (short)(this.h - 1);
                }
                if (this.o >= this.n) {
                    this.c();
                }
                return;
            }
            case 55: {
                this.c();
                a.f.aj = this.k;
                a.f.ak = this.l;
                a.f.ai = this.e;
                return;
            }
            case 56: {
                this.c();
                a.f.ak = 0;
                return;
            }
            case 57: {
                this.c();
                a.f.al = this.k;
                a.f.am = this.l;
                a.f.an = this.m;
                return;
            }
            case 58: {
                this.c();
                a.f.am = 0;
                return;
            }
            case 60: {
                if (this.o == 0) {
                    this.d = a.f.w.a(this.k);
                    if (this.d == null) {
                        this.c();
                        break;
                    }
                    this.e = this.d.l;
                    ++this.o;
                    this.g = 0;
                    this.h = 0;
                }
                if (this.h < this.l) {
                    this.g = (short)(this.g + 1);
                    if (this.g >= this.m) {
                        this.g = 0;
                        if (this.d.k == null) {
                            this.d.a(this.e, -1);
                        } else {
                            this.d.a(-1, -1);
                        }
                        this.h = (short)(this.h + 1);
                        break;
                    }
                } else {
                    this.d.a(this.e, -1);
                    this.c();
                }
                return;
            }
            case 61: {
                this.c();
                return;
            }
            case 62: {
                var1_21 = b.b.g.b(this.r);
                var1_21.k();
                this.c();
                return;
            }
            case 63: {
                this.c();
                a.f.d = this.e == 1;
                return;
            }
            case 64: {
                if (this.o == 0) {
                    this.d = a.f.w.a(this.k);
                    var1_22 = a.f.w.a(this.l);
                    if (this.d == null || var1_22 == null) {
                        this.c();
                        break;
                    }
                    var2_34 = var1_22.n();
                    var3_42 = var1_22.o();
                    switch (this.h) {
                        case 2: {
                            var3_42 += this.i;
                            break;
                        }
                        case 3: {
                            var3_42 -= this.i;
                            break;
                        }
                        case 1: {
                            var2_34 -= this.i;
                            break;
                        }
                        case 0: {
                            var2_34 += this.i;
                        }
                    }
                    v3 = a.f.w.a(this.d.n(), this.d.o(), var2_34, var3_42);
                    var1_22 = v3;
                    if (v3 == null) {
                        this.d.s = var2_34 << 4;
                        this.d.t = var3_42 << 4;
                        this.d.a(this.m, false);
                        if (this.j == 1) {
                            this.d.f = false;
                            this.d.g = true;
                            this.d.h = false;
                        }
                        this.c();
                        break;
                    }
                    this.p = b.f.b.a((byte[])var1_22, this.d, this.g);
                    ++this.o;
                    break;
                }
                if (this.l()) {
                    this.d.a(this.m, false);
                    if (this.j == 1) {
                        this.d.f = false;
                        this.d.g = true;
                        this.d.h = false;
                    }
                    this.c();
                }
                return;
            }
            case 65: {
                if (this.o == 0) {
                    var1_23 = a.f.w.a(b.e.a.a(this.k));
                    if (var1_23 == null) {
                        this.c();
                        break;
                    }
                    this.d = new c.h("m_face");
                    this.d.i();
                    if (this.d == null || this.d.j == null) {
                        this.c();
                        break;
                    }
                    d.k.a(this.d);
                    this.d.f = true;
                    this.d.a((int)this.l, 0);
                    this.d.s = var1_23.s - a.f.w.i + 8;
                    this.d.t = var1_23.t - 32 - a.f.w.j;
                    this.o = 1;
                }
                this.d.j();
                if (this.d.k()) {
                    this.c();
                    d.k.b(this.d);
                    this.d.j = null;
                    this.d = null;
                }
                return;
            }
            case 66: {
                if (a.f.q != this) {
                    a.f.c = this;
                    this.c();
                    break;
                }
                if (this.l()) {
                    this.c();
                }
                return;
            }
            case 68: {
                this.c();
                b.b.g.a("\u4f60\u7684\u6210\u5c31\u503c\u8fbe\u5230\u4e86:" + a.f.b, 3000L).k();
                return;
            }
            case 67: {
                a.f.b = (short)b.e.a.b(a.f.b, this.g, 1000, 0);
                this.c();
                return;
            }
            case 69: {
                this.c();
                var1_24 = a.f.b(this.g, (int)(-this.h));
                if (!var1_24) {
                    b.b.g.a("\u6570\u91cf\u4e0d\u8db3!", 3000L).k();
                    break;
                }
                var1_24 = a.f.b(this.i, (int)this.j);
                if (!var1_24) {
                    b.b.g.a("\u80cc\u5305\u5df2\u6ee1!", 3000L).k();
                    break;
                }
                a.f.c(this.g, this.h);
                a.f.a(this.i, (int)this.j);
                b.b.g.a("\u4ea4\u6613\u6210\u529f!", 3000L).k();
                return;
            }
            case 70: {
                this.c();
                var1_25 = b.b.g.a(b.f.a.y, (byte)0, (byte)1, true);
                if (var1_25.k() == 1 && (var2_35 = var1_25.a()).equals("\u5b58\u6863")) {
                    var3_43 = (main.a.d - main.a.h) / 2;
                    var1_26 = (main.a.e - main.a.i) / 2;
                    var2_35 = new h(var3_43, var1_26, main.a.h, main.a.i);
                    var4_46 = new m();
                    var2_35.a(var4_46);
                    var2_35.k();
                }
                return;
            }
            default: {
                this.c();
            }
        }
    }

    private void f() {
        switch (this.o) {
            case 0: {
                int n2;
                if (this.p == null || this.p.length == 0) {
                    this.c();
                    return;
                }
                for (n2 = 0; n2 < this.p.length; ++n2) {
                    this.p[n2].a();
                }
                for (n2 = 0; n2 < this.p.length; ++n2) {
                    if (this.p[n2].b()) continue;
                    return;
                }
                this.c();
                ++this.o;
            }
        }
    }

    private static boolean g() {
        Object object = g.a.a();
        if (((String[])object).length < 4 || object[0] == null || object[1] == null || object[3] == null) {
            return false;
        }
        a.f.h();
        Object object2 = b.f.a.h();
        if (object2 == null) {
            return false;
        }
        Object object3 = "http://59.42.254.3/gs/upstore.do?gamecode=0110&saveid=swd3&u=" + object[0] + "&p=" + object[1];
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("Content-Type", "application/x-www-form-urlencoded");
        object2 = new h.a((String)object3, "POST", (byte[])object2, (Hashtable)hashtable);
        object3 = null;
        try {
            object3 = h.a.a((h.a)object2, object[3].equals("true"), null);
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return false;
        }
        if (object3 == null || !((Hashtable)object3).containsKey("respCode")) {
            return false;
        }
        try {
            return Integer.parseInt(((Hashtable)object3).get("respCode").toString()) == 200;
        }
        catch (Exception exception) {
            return false;
        }
    }

    private static byte[] h() {
        Object object = new a.c();
        ((a.c)object).c();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeByte(a.f.u());
            dataOutputStream.writeByte(a.f.v());
            dataOutputStream.writeBoolean(a.f.t());
            dataOutputStream.writeBoolean(a.f.n());
            object = ((a.c)object).b();
            ((OutputStream)dataOutputStream).write((byte[])object);
            object = byteArrayOutputStream.toByteArray();
            return object;
        }
        catch (IOException iOException) {
            object = iOException;
            iOException.printStackTrace();
            return null;
        }
        finally {
            b.e.a.a(byteArrayOutputStream);
            b.e.a.a(dataOutputStream);
        }
    }

    private boolean a(boolean bl) {
        if (this.p == null || this.p.length != 2) {
            return true;
        }
        for (int i2 = 0; i2 < this.p.length; ++i2) {
            if (this.p[i2].c == 19 && bl) {
                this.p[i2].a();
                return this.p[i2].b();
            }
            if (this.p[i2].c != 20 || bl) continue;
            this.p[i2].a();
            return this.p[i2].b();
        }
        return true;
    }

    private void i() {
        this.c();
        a.f.i = this.k == 1;
        if (a.f.i) {
            for (int i2 = 1; i2 < a.f.A.length; ++i2) {
                a.f.A[i2].s = a.f.A[0].s;
                a.f.A[i2].t = a.f.A[0].t;
                a.f.A[i2].a(a.f.A[0].r, false);
            }
        }
    }

    private void j() {
        this.c();
        a.g g2 = a.f.a((short)this.k);
        if (g2 == null) {
            return;
        }
        switch (this.l) {
            case 0: {
                a.f.a(g2);
                break;
            }
            case 1: {
                a.f.b(g2);
                break;
            }
            default: {
                return;
            }
        }
        for (int i2 = 1; i2 < a.f.A.length; ++i2) {
            a.f.A[i2].s = a.f.A[0].n() << 4;
            a.f.A[i2].t = a.f.A[0].o() << 4;
            a.f.A[i2].a(a.f.A[0].r, false);
        }
    }

    private void k() {
        boolean bl;
        if (this.o == 0) {
            int n2;
            if (this.p == null || this.p.length <= 0) {
                this.c();
                return;
            }
            Object object = new String[this.p.length];
            for (int i2 = 0; i2 < this.p.length; ++i2) {
                b b2 = this.p[i2];
                object[i2] = b2.r;
            }
            g g2 = b.b.g.a(object, (byte)0, (byte)4, false);
            g2.k();
            int n3 = n2 = g2.j();
            object = this;
            if (object.p != null && object.p.length > 0) {
                if (n3 < 0 || n3 >= object.p.length) {
                    object.p = null;
                } else {
                    b b3 = object.p[n3];
                    object.p = b3.p;
                }
            }
            ++this.o;
        }
        if (bl = this.l()) {
            this.c();
            ++this.o;
        }
    }

    private boolean l() {
        if (this.p == null || this.p.length == 0 || this.q >= this.p.length) {
            return true;
        }
        b b2 = this.p[this.q];
        if (b2 == null) {
            ++this.q;
            return false;
        }
        b2.a();
        if (b2.b()) {
            ++this.q;
            if (this.q >= this.p.length) {
                return true;
            }
        }
        return false;
    }
}

