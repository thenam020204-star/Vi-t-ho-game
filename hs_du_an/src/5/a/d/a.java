/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Displayable
 */
package a.d;

import a.a.g;
import a.a.o;
import a.d.b;
import a.e.c;
import b.f;
import c.e;
import c.l;
import e.i;
import e.n;
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
                    this.d = b.a.w.a(this.g);
                    if (this.d == null) {
                        this.c();
                        break;
                    }
                    if (this.h == 0) {
                        this.h = (short)4;
                    }
                    this.n = (this.i << 4) / this.h;
                    if (!this.d.v) {
                        this.d.a(this.l, true);
                    }
                    this.d.b(this.l);
                    this.d.j = 0;
                }
                this.d.a(this.k, this.h);
                ++this.o;
                if (this.o >= this.n) {
                    this.d.j = 1;
                    this.c();
                    if (!this.d.v) {
                        this.d.a(this.l, false);
                    }
                }
                return;
            }
            case 1: {
                var1_1 = b.a.c("head_r" + this.k);
                if (var1_1 == null && (var1_1 = a.e.c.b("head_r" + this.k)) != null) {
                    b.a.a((c)var1_1);
                }
                var2_29 = null;
                if (var1_1 != null) {
                    var2_29 = var1_1.b(this.m);
                }
                var3_38 = null;
                if (this.g == 0 && (var1_1 = b.a.w.a(this.h)) != null) {
                    this.s = var1_1.k;
                }
                var3_38 = a.a.l.a(this.r, this.s, var2_29, this.l);
                var3_38.k();
                this.c();
                return;
            }
            case 2: {
                this.l();
                return;
            }
            case 6: {
                v0.d = b.a.w.a(this.k);
                if (this.d != null) {
                    this.d.a(this.l, false);
                    this.d.b(this.l);
                }
                this.c();
                return;
            }
            case 7: {
                if (this.o == 0) {
                    var2_30 = this;
                    if ((var2_30.k & 15) == 2) {
                        var3_39 = b.a.w.a(var2_30.h);
                        if (var3_39 != null) {
                            var2_30.e = var3_39.n + 8 - main.a.f;
                            var2_30.f = var3_39.o - 8 - main.a.g;
                        } else {
                            var2_30.e = b.a.w.i;
                            var2_30.f = b.a.w.j;
                        }
                    } else {
                        var2_30.e = (var2_30.e << 4) + 8 - main.a.f;
                        var2_30.f = (var2_30.f << 4) + 8 - main.a.g;
                    }
                    b.a.u = 2;
                    if (var2_30.e < 0) {
                        var2_30.e = 0;
                    } else if (var2_30.e + main.a.d > b.a.w.g()) {
                        var2_30.e = b.a.w.g() - main.a.d;
                    }
                    if (var2_30.f < 0) {
                        var2_30.f = 0;
                    } else if (var2_30.f + main.a.e > b.a.w.h()) {
                        var2_30.f = b.a.w.h() - main.a.e;
                    }
                    ++this.o;
                }
                switch (this.k >> 4 & 15) {
                    case 2: {
                        b.a.w.a(this.e, this.f, (int)this.g);
                        break;
                    }
                    case 1: {
                        b.a.w.d(this.e, this.f);
                        break;
                    }
                    case 3: {
                        b.a.w.i = this.e;
                        b.a.w.j = this.f;
                    }
                }
                if (b.a.w.j == this.f && b.a.w.i == this.e) {
                    if ((this.k & 15) == 2 && this.l == 1 && (var1_2 = b.a.w.a(this.h)) != null) {
                        b.a.u = 1;
                        b.a.v = this.h;
                    }
                    this.c();
                }
                return;
            }
            case 8: {
                var1_3 = b.a.w.a(this.k);
                if (var1_3 == null) {
                    this.c();
                    break;
                }
                b.a.u = 1;
                b.a.v = this.k;
                this.c();
                return;
            }
            case 9: {
                if (this.i != -1 && this.i != b.a.w.k) {
                    b.a.x = this.i;
                    b.a.y = this.g;
                    b.a.z = this.h;
                    main.a.a.k.b = new l();
                } else {
                    b.a.A[0].n = this.g << 4;
                    b.a.A[0].o = this.h << 4;
                }
                this.c();
                return;
            }
            case 10: {
                switch (this.o) {
                    case 0: {
                        if (this.d == null) {
                            this.d = b.a.w.a(this.g);
                        }
                        if (this.d == null || this.d.e == null) {
                            this.c();
                            break block0;
                        }
                        this.d.a(this.h, (int)this.k);
                        if (this.d.f == null) {
                            this.c();
                            break block0;
                        }
                        if (this.e == 1) {
                            this.d.f.a(true);
                        } else {
                            this.d.f.a(false);
                        }
                        ++this.o;
                        if (this.k == -1) {
                            this.c();
                            ++this.o;
                        }
                    }
                    case 1: {
                        if (!this.d.c()) break;
                        this.c();
                        ++this.o;
                        if (this.f == 1 || this.d.f != null && this.d.f.e() == -1) break;
                        this.d.a(this.i, -1);
                    }
                }
                return;
            }
            case 12: {
                b.a.a(this.k, this.l);
                this.c();
                return;
            }
            case 14: {
                this.c();
                var1_4 = b.a.w.b(this.g);
                if (var1_4 != null) {
                    var1_4.a = (this.k & 1) != 0;
                    var1_4.b = (this.k >> 1 & 1) != 0;
                    v1 = var1_4.c = (this.k >> 2 & 1) != 0;
                    if (this.r != null && var1_4.d != null && !var1_4.d.endsWith(this.r) && !this.r.equals("")) {
                        var1_4.h();
                        var1_4.d = this.r;
                    }
                    if (var1_4.a && var1_4.e == null) {
                        var1_4.a();
                        var1_4.a(var1_4.g, -1);
                    } else if (!var1_4.a) {
                        var1_4.h();
                    }
                    if (this.h != -1) {
                        var1_4.n = this.h << 4;
                    }
                    if (this.i != -1) {
                        var1_4.o = this.i << 4;
                    }
                    if (this.l != 4) {
                        var1_4.c(this.l);
                    }
                    if (this.m != 2) {
                        var1_4.d(this.m);
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
                this.d = b.a.c(this.g);
                if (this.d == null) {
                    this.d = b.a.a(this.g);
                }
                if (this.d == null) ** GOTO lbl183
lbl178:
                // 2 sources

                var1_5 = (f)this.d;
                var1_5.a(this.l, this.k, this.h);
                if (var1_5.w()) {
                    var1_5.c((short)0);
                }
lbl183:
                // 4 sources

                return;
            }
            case 16: {
                switch (this.o) {
                    case 0: {
                        var1_6 = -1;
                        switch (this.k) {
                            case 0: {
                                var1_6 = a.c.b.a((int)b.a.a(this.l));
                                break;
                            }
                            case 1: {
                                if (this.d == null) {
                                    this.c();
                                    break block0;
                                }
                                var1_6 = ((f)this.d).c((int)this.l);
                            }
                        }
                        var4_46 = this.e;
                        var2_31 = this.m;
                        switch (var2_31) {
                            case 0: {
                                if (var1_6 > var4_46) {
                                    v2 = 1;
                                    break;
                                }
                                v2 = 0;
                                break;
                            }
                            case 2: {
                                if (var1_6 < var4_46) {
                                    v2 = 1;
                                    break;
                                }
                                v2 = 0;
                                break;
                            }
                            case 1: {
                                if (var1_6 == var4_46) {
                                    v2 = 1;
                                    break;
                                }
                                v2 = 0;
                                break;
                            }
                            default: {
                                v2 = var2_31 = 0;
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
                        var3_40 = this.m();
                        if (!var3_40) break;
                        this.c();
                    }
                }
                return;
            }
            case 17: {
                this.g();
                return;
            }
            case 4: 
            case 18: 
            case 19: 
            case 20: {
                var1_7 = this.m();
                if (var1_7) {
                    this.c();
                }
                return;
            }
            case 24: {
                b.a.q = null;
                return;
            }
            case 26: {
                var1_8 = null;
                switch (this.k) {
                    case 1: {
                        var1_8 = a.a.l.a(this.r);
                        break;
                    }
                    case 0: {
                        var1_8 = a.a.l.a(this.r, 4000L);
                        break;
                    }
                    default: {
                        this.c();
                    }
                }
                var1_8.k();
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
                var1_9 = this.m();
                if (var1_9) {
                    this.c();
                }
                return;
            }
            case 31: {
                if (this.o == 0) {
                    main.a.a.k.b = c.c.a((b)this);
                    ++this.o;
                }
                return;
            }
            case 29: {
                if (this.o == 0) {
                    this.d.a = true;
                    ++this.o;
                }
                this.d.n = a.c.b.b(this.d.n, this.h, this.g);
                this.d.o = a.c.b.b(this.d.o, this.i, this.g);
                if (this.d.n == this.h && this.d.o == this.i) {
                    if (this.k == 1) {
                        this.d.a = false;
                        c.a.b(this.d);
                    }
                    this.c();
                }
                return;
            }
            case 32: {
                switch (this.o) {
                    case 0: {
                        var1_10 = this;
                        this.d = b.a.w.a(var1_10.g);
                        if (var1_10.d == null) {
                            var1_10.c();
                        } else {
                            var2_32 = b.a.w.a(var1_10.d.f(), var1_10.d.g(), var1_10.h, var1_10.i);
                            if (var2_32 == null) {
                                var1_10.d.n = var1_10.h << 4;
                                var1_10.d.o = var1_10.i << 4;
                                var1_10.d.a(var1_10.k, false);
                                var1_10.c();
                            } else {
                                var1_10.p = a.d.b.a(var2_32, var1_10.d, var1_10.l);
                            }
                        }
                        if (this.b()) break;
                        this.o = 1;
                    }
                    case 1: {
                        if (!this.m()) break;
                        this.d.a(this.k, false);
                    }
                    default: {
                        this.c();
                    }
                }
                return;
            }
            case 34: {
                b.a.a((int)this.k, (int)this.l, (int)b.a.m);
                this.c();
                return;
            }
            case 35: {
                this.k();
                return;
            }
            case 36: {
                b.a.a((int)this.g, this.k, (int)this.h);
                this.c();
                return;
            }
            case 37: {
                if (this.o == 0) {
                    ++this.o;
                    var1_11 = b.a.w.c(this.k);
                    if (var1_11 == null) {
                        this.c();
                        break;
                    }
                    this.p = new b[]{var1_11};
                }
                if (var1_12 = this.m()) {
                    this.c();
                }
                return;
            }
            case 38: {
                if (b.a.x() || this.k != 1) ** GOTO lbl353
                a.a.l.a("\u82b1\u8d392\u5143, \u4e60\u5f97\u4e07\u80fd\u94a5\u5319\u6280\u80fd,\u53ef\u4ee5\u65e0\u9650\u6b21\u5f00\u542f\u73cd\u8d35\u5b9d\u7bb1", 3000L).k();
                var1_13 = a.a.l.a(a.d.a.B, (byte)0, (byte)1, false);
                if (var1_13.k() != 1) ** GOTO lbl351
                var2_33 = var1_13.c();
                if (var2_33.equals("\u5f00\u542f!")) {
                    var3_41 = false;
                    var3_41 = b.a.t() != false ? true : e.l.a("\u4e60\u5f97\u4e07\u80fd\u94a5\u5319\u6280\u80fd", 9, 0);
                    if (var3_41) {
                        b.a.d(true);
                        b.a.a(1001, 2);
                        d.b.a(String.valueOf(9), 0);
                        b.a.o();
                        a.a.l.a("\u8c22\u8c22\u60a8\u7684\u652f\u6301,\u5df2\u6210\u529f\u4e60\u5f97\u4e07\u80fd\u94a5\u5319\u6280\u80fd", 3000L).k();
                    }
                } else {
                    this.c();
                    break;
lbl351:
                    // 1 sources

                    this.c();
                    break;
                }
lbl353:
                // 2 sources

                if (this.o == 0) {
                    var1_13 = b.a.b((int)this.g);
                    this.p = new b[4];
                    this.p[0] = new a(10);
                    this.p[0].g = b.a.r;
                    this.p[0].h = (short)2;
                    this.p[0].k = 0;
                    this.p[0].i = 1;
                    this.p[1] = new a(36);
                    this.p[1].g = this.g;
                    this.p[1].k = 0;
                    this.p[1].h = this.h;
                    this.p[2] = new a(26);
                    var2_33 = new StringBuffer();
                    if (this.g == -1) {
                        var2_33.append(b.a.o[31]);
                        var2_33.append(b.a.o[32]);
                        var2_33.append(this.h);
                    } else if (var1_13 == null) {
                        var2_33.append(b.a.o[3]);
                    } else {
                        var2_33.append(b.a.o[31]);
                        var2_33.append(var1_13.s);
                        var2_33.append(this.h);
                        var2_33.append(b.a.o[40]);
                    }
                    this.p[2].r = var2_33.toString();
                    this.p[2].k = 0;
                    this.p[3] = new a(14);
                    this.p[3].g = b.a.r;
                    this.p[3].k = 1;
                    this.p[3].l = 0;
                    this.p[3].m = 0;
                    this.p[3].h = (short)-1;
                    this.p[3].i = (short)-1;
                    ++this.o;
                }
                if (var1_14 = this.m()) {
                    this.c();
                }
                return;
            }
            case 39: {
                if (this.o == 0) {
                    this.d = new n(this.r);
                    this.d.a();
                    this.d.a((int)this.k, (int)this.l);
                    if (this.d == null || this.d.f == null) {
                        this.c();
                        break;
                    }
                    c.a.a(this.d);
                    this.d.a = true;
                    if (this.m == 0) {
                        if (b.a.w == null) {
                            this.c();
                            break;
                        }
                        this.d.n = this.h - b.a.w.i;
                        this.d.o = this.i - b.a.w.j;
                    } else if (this.m == 1) {
                        this.d.n = this.h;
                        this.d.o = this.i;
                    }
                    ++this.o;
                    break;
                }
                this.d.b();
                if (this.d.c()) {
                    this.c();
                    c.a.b(this.d);
                    this.d.e = null;
                    this.d = null;
                }
                return;
            }
            case 40: {
                this.c();
                var1_15 = a.a.l.a(a.d.a.A, (byte)0, (byte)1, true);
                var2_34 = var1_15.k();
                if (var2_34 == 1) {
                    var3_42 = var1_15.c();
                    var1_16 = (main.a.d - main.a.h) / 2;
                    var2_34 = (main.a.e - main.a.i) / 2;
                    var4_47 = new o(var1_16, var2_34, main.a.h, main.a.i);
                    if (var3_42.equals("\u8d2d\u4e70")) {
                        if (a.d.b.u == null || a.d.b.u.length == 0) break;
                        this = new e.f(a.d.b.u);
                        var4_47.a((g)this);
                    } else {
                        if (!var3_42.equals("\u5356\u51fa")) break;
                        this = new e.g();
                        var4_47.a((g)this);
                    }
                    var4_47.k();
                }
                a.d.b.u = null;
                return;
            }
            case 41: {
                this.c();
                if (this.v != null) {
                    this.v.a((f)this.d, this.w, this.x);
                    this.v = null;
                    this.w = null;
                    this.x = null;
                }
                return;
            }
            case 42: {
                this.j();
                return;
            }
            case 43: {
                if (this.o == 0) {
                    var1_17 = false;
                    var1_17 = b.a.b(this.g, (int)this.h);
                    this.e = var1_17 != false ? 1 : 0;
                    ++this.o;
                }
                if (this.a(this.e == 1)) {
                    this.c();
                }
                return;
            }
            case 46: {
                main.a.a.k.a(new e());
                this.c();
                return;
            }
            case 48: {
                this.c();
                a.a.l.a("\u4f60\u7684\u6210\u5c31\u503c\u8fbe\u5230\u4e86:" + b.a.b, 3000L).k();
                var1_18 = a.a.l.a(a.d.a.z, (byte)0, (byte)1, true);
                if (var1_18.k() == 1 && var1_18.c().equals("\u4e0a\u4f20\u6210\u5c31")) {
                    d.b.a(MainMIDlet.a, (Displayable)main.a.a, this.s, "0118", String.valueOf(b.a.b), this.r);
                }
                return;
            }
            case 50: {
                this.c();
                d.b.a(MainMIDlet.a, (Displayable)main.a.a, "0118");
                var1_19 = true;
                var2_35 = d.b.a();
                if (var2_35.length < 3 || var2_35[2] == null || var2_35[2].trim().length() == 0) {
                    var1_19 = false;
                }
                var3_43 = null;
                var3_43 = var1_19 ? ((var1_19 = a.d.a.h()) ? "\u4e0a\u4f20\u5b58\u6863\u6210\u529f!" : "\u4e0a\u4f20\u5b58\u6863\u5931\u8d25!") : "\u672a\u767b\u9646,\u4e0a\u4f20\u5b58\u6863\u5931\u8d25!";
                var1_20 = a.a.l.a(var3_43, 3000L);
                var1_20.k();
                return;
            }
            case 51: {
                this.c();
                b.a.ah = true;
                return;
            }
            case 52: {
                this.c();
                b.a.ah = false;
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
                    this.d = b.a.w.a(this.k);
                    if (this.d == null) {
                        this.c();
                        break;
                    }
                    this.n = this.m << 2;
                    this.h = 0;
                }
                if (this.h <= 0) {
                    var1_21 = b.b.a(this.d.m, this.l);
                    this.d.b(var1_21);
                    this.d.a(var1_21, false);
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
                b.a.aj = this.k;
                b.a.ak = this.l;
                b.a.ai = this.e;
                return;
            }
            case 56: {
                this.c();
                b.a.ak = 0;
                return;
            }
            case 57: {
                this.c();
                b.a.al = this.k;
                b.a.am = this.l;
                b.a.an = this.m;
                return;
            }
            case 58: {
                this.c();
                b.a.am = 0;
                return;
            }
            case 60: {
                if (this.o == 0) {
                    this.d = b.a.w.a(this.k);
                    if (this.d == null) {
                        this.c();
                        break;
                    }
                    this.e = this.d.g;
                    ++this.o;
                    this.g = 0;
                    this.h = 0;
                }
                if (this.h < this.l) {
                    this.g = (short)(this.g + 1);
                    if (this.g >= this.m) {
                        this.g = 0;
                        if (this.d.f == null) {
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
                var1_22 = a.a.l.a(this.r, "/" + this.s + ".png");
                var1_22.k();
                this.c();
                return;
            }
            case 63: {
                this.c();
                b.a.d = this.e == 1;
                return;
            }
            case 64: {
                if (this.o == 0) {
                    this.d = b.a.w.a(this.k);
                    var1_23 = b.a.w.a(this.l);
                    if (this.d == null || var1_23 == null) {
                        this.c();
                        break;
                    }
                    var2_36 = var1_23.f();
                    var3_44 = var1_23.g();
                    switch (this.h) {
                        case 2: {
                            var3_44 += this.i;
                            break;
                        }
                        case 3: {
                            var3_44 -= this.i;
                            break;
                        }
                        case 1: {
                            var2_36 -= this.i;
                            break;
                        }
                        case 0: {
                            var2_36 += this.i;
                        }
                    }
                    v3 = b.a.w.a(this.d.f(), this.d.g(), var2_36, var3_44);
                    var1_23 = v3;
                    if (v3 == null) {
                        this.d.n = var2_36 << 4;
                        this.d.o = var3_44 << 4;
                        this.d.a(this.m, false);
                        if (this.j == 1) {
                            this.d.a = false;
                            this.d.b = true;
                            this.d.c = false;
                        }
                        this.c();
                        break;
                    }
                    this.p = a.d.b.a((byte[])var1_23, this.d, this.g);
                    ++this.o;
                    break;
                }
                if (this.m()) {
                    this.d.a(this.m, false);
                    if (this.j == 1) {
                        this.d.a = false;
                        this.d.b = true;
                        this.d.c = false;
                    }
                    this.c();
                }
                return;
            }
            case 65: {
                if (this.o == 0) {
                    var1_24 = b.a.w.a(a.c.b.a(this.k));
                    if (var1_24 == null) {
                        this.c();
                        break;
                    }
                    this.d = new n("m_face");
                    this.d.a();
                    if (this.d == null || this.d.e == null) {
                        this.c();
                        break;
                    }
                    c.a.a(this.d);
                    this.d.a = true;
                    this.d.a((int)this.l, 0);
                    this.d.n = var1_24.n - b.a.w.i + 8;
                    this.d.o = var1_24.o - 32 - b.a.w.j;
                    this.o = 1;
                }
                this.d.b();
                if (this.d.c()) {
                    this.c();
                    c.a.b(this.d);
                    this.d.e = null;
                    this.d = null;
                }
                return;
            }
            case 66: {
                if (b.a.q != this) {
                    b.a.c = this;
                    this.c();
                    break;
                }
                if (this.m()) {
                    this.c();
                }
                return;
            }
            case 68: {
                this.c();
                a.a.l.a("\u4f60\u7684\u6210\u5c31\u503c\u8fbe\u5230\u4e86:" + b.a.b, 3000L).k();
                return;
            }
            case 67: {
                b.a.b = (short)a.c.b.b(b.a.b, this.g, 1000, 0);
                this.c();
                return;
            }
            case 69: {
                this.c();
                var1_25 = b.a.b(this.g, (int)(-this.h));
                if (!var1_25) {
                    a.a.l.a("\u6570\u91cf\u4e0d\u8db3!", 3000L).k();
                    break;
                }
                var1_25 = b.a.b(this.i, (int)this.j);
                if (!var1_25) {
                    a.a.l.a("\u80cc\u5305\u5df2\u6ee1!", 3000L).k();
                    break;
                }
                b.a.c(this.g, this.h);
                b.a.a(this.i, (int)this.j);
                a.a.l.a("\u4ea4\u6613\u6210\u529f!", 3000L).k();
                return;
            }
            case 70: {
                this.c();
                var1_26 = a.a.l.a(a.d.a.y, (byte)0, (byte)1, true);
                if (var1_26.k() == 1 && (var2_37 = var1_26.c()).equals("\u5b58\u6863")) {
                    var3_45 = (main.a.d - main.a.h) / 2;
                    var1_27 = (main.a.e - main.a.i) / 2;
                    var2_37 = new o(var3_45, var1_27, main.a.h, main.a.i);
                    var4_48 = new i();
                    var2_37.a(var4_48);
                    var2_37.k();
                }
                return;
            }
            case 71: {
                this.f();
                return;
            }
            case 49: {
                var1_28 = false;
                if (b.a.o(this.k)) {
                    var1_28 = true;
                } else if (e.l.a(this.r, this.k, 0)) {
                    var1_28 = true;
                    e.l.c(this.k);
                    b.a.o();
                } else {
                    var1_28 = false;
                }
                if (this.a(var1_28)) {
                    this.c();
                }
                return;
            }
            default: {
                this.c();
            }
        }
    }

    private void f() {
        this.c();
        int n2 = b.a.b((short)this.k);
        if (n2 <= 0) {
            return;
        }
        f f2 = b.a.j[0];
        b.a.j[0] = b.a.j[n2];
        b.a.j[n2] = f2;
        b.a.v = b.a.j[0].i;
        b.a.A = b.a.a(b.a.j);
        for (n2 = 1; n2 < b.a.A.length; ++n2) {
            b.a.A[n2].n = b.a.A[0].n;
            b.a.A[n2].o = b.a.A[0].o;
        }
    }

    private void g() {
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

    private static boolean h() {
        Object object = d.b.a();
        if (((String[])object).length < 4 || object[0] == null || object[1] == null || object[3] == null) {
            return false;
        }
        b.a.h();
        Object object2 = a.d.a.i();
        if (object2 == null) {
            return false;
        }
        Object object3 = "http://59.42.254.3/gs/upstore.do?gamecode=0118&saveid=swd5&u=" + object[0] + "&p=" + object[1];
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

    private static byte[] i() {
        Object object = new b.g();
        ((b.g)object).c();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeByte(b.a.u());
            dataOutputStream.writeByte(b.a.v());
            dataOutputStream.writeBoolean(b.a.t());
            dataOutputStream.writeBoolean(b.a.n());
            object = ((b.g)object).b();
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
            a.c.b.a(byteArrayOutputStream);
            a.c.b.a(dataOutputStream);
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

    private void j() {
        this.c();
        b.a.i = this.k == 1;
        if (b.a.i) {
            for (int i2 = 1; i2 < b.a.A.length; ++i2) {
                b.a.A[i2].n = b.a.A[0].n;
                b.a.A[i2].o = b.a.A[0].o;
                b.a.A[i2].a(b.a.A[0].m, false);
            }
        }
    }

    private void k() {
        this.c();
        f f2 = b.a.a((short)this.k);
        if (f2 == null) {
            return;
        }
        switch (this.l) {
            case 0: {
                b.a.a(f2);
                break;
            }
            case 1: {
                b.a.b(f2);
                break;
            }
            default: {
                return;
            }
        }
        for (int i2 = 1; i2 < b.a.A.length; ++i2) {
            b.a.A[i2].n = b.a.A[0].f() << 4;
            b.a.A[i2].o = b.a.A[0].g() << 4;
            b.a.A[i2].a(b.a.A[0].m, false);
        }
    }

    private void l() {
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
            a.a.l l2 = a.a.l.a(object, (byte)0, (byte)4, false);
            l2.k();
            int n3 = n2 = l2.j();
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
        if (bl = this.m()) {
            this.c();
            ++this.o;
        }
    }

    private boolean m() {
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

