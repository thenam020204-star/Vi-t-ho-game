/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
package main;

import b.a;
import b.e;
import b.f;
import c.d;
import c.j;
import c.q;
import c.t;
import c.u;
import d.m;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Image;
import main.b;

public final class c {
    private int k = -1;
    private b.b l = null;
    private int m = -1;
    private int n = -1;
    public short a = (short)-1;
    public short b = (short)-1;
    public short c = (short)-1;
    public short d = (short)-1;
    public byte e = (byte)-1;
    public byte f = (byte)-1;
    public byte g = (byte)-2;
    private int o = 0;
    private int p = 0;
    public c[] h = null;
    private int q = 0;
    private String r = null;
    private String s = null;
    private int t = 2;
    private c u = null;
    private static int[] v = null;
    private f.b w = null;
    private f x = null;
    private f[] y = null;
    public static byte i = 1;
    public static byte j = (byte)2;

    private c(int n2) {
        this.k = n2;
    }

    public static c a(short s2, byte by, short s3, byte by2, short s4) {
        c c2 = new c(5);
        new c(5).a = s2;
        c2.b = s4;
        c2.c = s3;
        c2.e = by;
        c2.f = by2;
        return c2;
    }

    public static c a(int n2, int n3, b.b b2, int n4, int n5) {
        c c2 = new c(29);
        new c(29).a = (short)n4;
        c2.b = (short)n2;
        c2.c = (short)n3;
        c2.l = b2;
        c2.e = (byte)n5;
        return c2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void a() {
        if (this.t == 1) {
            return;
        }
        switch (this.k) {
            case 5: {
                if (this.p == 0) {
                    this.l = b.a.x.a(this.a);
                    if (this.l == null) {
                        c c2 = this;
                        this.t = 1;
                        return;
                    }
                    this.o = (this.c << 4) / this.b;
                    this.l.a(this.f, true);
                    this.l.b(this.f);
                    this.l.g = 0;
                }
                this.l.a(this.e, this.b);
                ++this.p;
                if (this.p < this.o) return;
                this.l.g = 1;
                c c3 = this;
                this.t = 1;
                this.l.a(this.f, false);
                return;
            }
            case 1: {
                e.b.c c4 = b.a.d("head_r" + this.e);
                if (c4 == null && (c4 = e.b.c.b("head_r" + this.e)) != null) {
                    b.a.a(c4);
                }
                Object object = null;
                if (c4 != null) {
                    object = c4.b(this.g);
                }
                c.b b2 = c.b.a(this.r, this.s, (e.b.b)object, this.f);
                b2.f();
                object = this;
                this.t = 1;
                return;
            }
            case 2: {
                this.g();
                return;
            }
            case 6: {
                v0.l = b.a.x.a(this.e);
                if (this.l != null) {
                    this.l.a(this.f, false);
                    this.l.b(this.f);
                }
                c c5 = this;
                this.t = 1;
                return;
            }
            case 7: {
                b.b b3;
                c c6;
                if (this.p == 0) {
                    c6 = this;
                    if ((c6.e & 0xF) == 2) {
                        b.b b4 = b.a.x.a(c6.b);
                        if (b4 != null) {
                            c6.m = b4.j + 8 - main.b.f;
                            c6.n = b4.k - 8 - main.b.g;
                        } else {
                            c6.m = b.a.x.a;
                            c6.n = b.a.x.b;
                        }
                    } else {
                        c6.m = (c6.m << 4) + 8 - main.b.f;
                        c6.n = (c6.n << 4) + 8 - main.b.g;
                    }
                    b.a.v = 2;
                    if (c6.m < 0) {
                        c6.m = 0;
                    } else if (c6.m + main.b.d > b.a.x.c()) {
                        c6.m = b.a.x.c() - main.b.d;
                    }
                    if (c6.n < 0) {
                        c6.n = 0;
                    } else if (c6.n + main.b.e > b.a.x.f()) {
                        c6.n = b.a.x.f() - main.b.e;
                    }
                    ++this.p;
                }
                switch (this.e >> 4 & 0xF) {
                    case 2: {
                        b.a.x.a(this.m, this.n, (int)this.a);
                        break;
                    }
                    case 1: {
                        b.a.x.d(this.m, this.n);
                        break;
                    }
                    case 3: {
                        b.a.x.a = this.m;
                        b.a.x.b = this.n;
                    }
                }
                if (b.a.x.b != this.n || b.a.x.a != this.m) return;
                if ((this.e & 0xF) == 2 && this.f == 1 && (b3 = b.a.x.a(this.b)) != null) {
                    b.a.v = 1;
                    b.a.w = this.b;
                }
                c6 = this;
                this.t = 1;
                return;
            }
            case 8: {
                b.b b5 = b.a.x.a(this.e);
                if (b5 == null) {
                    c c7 = this;
                    this.t = 1;
                    return;
                }
                b.a.v = 1;
                b.a.w = this.e;
                c c8 = this;
                this.t = 1;
                return;
            }
            case 9: {
                if (this.e != -1 && e.a.b.a((int)this.e) != b.a.x.c) {
                    b.a.y = e.a.b.a((int)this.e);
                    b.a.z = this.a;
                    b.a.A = this.b;
                    main.b.a.i.b = new m();
                } else {
                    b.a.B[0].j = this.a << 4;
                    b.a.B[0].k = this.b << 4;
                }
                c c9 = this;
                this.t = 1;
                return;
            }
            case 10: {
                switch (this.p) {
                    case 0: {
                        c c10;
                        if (this.l == null) {
                            this.l = b.a.x.a(this.a);
                        }
                        if (this.l == null || this.l.c == null) {
                            c c11 = this;
                            this.t = 1;
                            return;
                        }
                        this.l.a(this.b, (int)this.e);
                        if (this.m == 1 && this.l.d != null) {
                            this.l.d.a(true);
                        }
                        ++this.p;
                        if (this.e == -1) {
                            c10 = this;
                            this.t = 1;
                            ++this.p;
                        }
                    }
                    case 1: {
                        if (b.a.x != null && !b.a.x.b(this.l)) {
                            this.l.b();
                        }
                        if (!this.l.c()) return;
                        c c10 = this;
                        this.t = 1;
                        ++this.p;
                        if (this.n == 1 || this.l.d != null && this.l.d.e() == -1) return;
                        this.l.a(this.c, -1);
                    }
                }
                return;
            }
            case 12: {
                b.a.a(this.e, this.f);
                c c12 = this;
                this.t = 1;
                return;
            }
            case 14: {
                c c13 = this;
                this.t = 1;
                e e2 = b.a.x.b(this.a);
                if (e2 == null) return;
                e2.a = (this.e & 1) != 0;
                e2.o = (this.e >> 1 & 1) != 0;
                boolean bl = e2.p = (this.e >> 2 & 1) != 0;
                if (this.r != null && e2.b != null && !e2.b.endsWith(this.r) && !this.r.equals("")) {
                    e2.h();
                    e2.b = this.r;
                }
                if (e2.a && e2.c == null) {
                    e2.a();
                    e2.a(e2.e, -1);
                } else if (!e2.a) {
                    e2.h();
                }
                if (this.b != -1) {
                    e2.j = this.b << 4;
                }
                if (this.c != -1) {
                    e2.k = this.c << 4;
                }
                if (this.f != 4) {
                    e2.c(this.f);
                }
                if (this.g == 2) return;
                e2.d(this.g);
                if (this.g != 1) return;
                e2.a((short)this.m);
                e2.b((short)this.n);
                return;
            }
            case 15: {
                c c14 = this;
                this.t = 1;
                if (this.l == null) {
                    this.l = b.a.b(this.a);
                    if (this.l == null) {
                        this.l = b.a.a(this.a);
                    }
                    if (this.l == null) return;
                }
                ((f)this.l).a(this.f, this.e, this.b);
                return;
            }
            case 16: {
                switch (this.p) {
                    case 0: {
                        byte by;
                        int n2 = -1;
                        switch (this.e) {
                            case 0: {
                                n2 = e.a.b.a((int)b.a.a(this.f));
                                break;
                            }
                            case 1: {
                                if (this.l == null) {
                                    c c15 = this;
                                    this.t = 1;
                                    return;
                                }
                                n2 = ((f)this.l).c((int)this.f);
                            }
                        }
                        int n3 = this.m;
                        byte by2 = this.g;
                        switch (by2) {
                            case 0: {
                                if (n2 > n3) {
                                    by = 1;
                                    break;
                                }
                                by = 0;
                                break;
                            }
                            case 2: {
                                if (n2 < n3) {
                                    by = 1;
                                    break;
                                }
                                by = 0;
                                break;
                            }
                            case 1: {
                                if (n2 == n3) {
                                    by = 1;
                                    break;
                                }
                                by = 0;
                                break;
                            }
                            default: {
                                by = by2 = 0;
                            }
                        }
                        if (by == 0) {
                            c c16 = this;
                            this.t = 1;
                            return;
                        }
                        this.p = 1;
                        return;
                    }
                    case 1: {
                        boolean bl = this.h();
                        if (!bl) return;
                        c c17 = this;
                        this.t = 1;
                    }
                }
                return;
            }
            case 17: {
                this.d();
                return;
            }
            case 4: 
            case 18: 
            case 19: 
            case 20: {
                boolean bl = this.h();
                if (!bl) return;
                this.t = 1;
                return;
            }
            case 24: {
                b.a.r = null;
                return;
            }
            case 26: {
                c c18;
                c.b b6 = null;
                switch (this.e) {
                    case 1: {
                        b6 = c.b.a(this.r);
                        break;
                    }
                    case 0: {
                        b6 = c.b.a(this.r, 4000L);
                        break;
                    }
                    default: {
                        c18 = this;
                        this.t = 1;
                    }
                }
                b6.f();
                c18 = this;
                this.t = 1;
                return;
            }
            case 28: {
                c c19 = this;
                this.t = 1;
                c19 = this.u;
                this.u.t = 1;
                c19 = this.u.u;
                this.u.u.t = 1;
                return;
            }
            case 27: {
                boolean bl = this.h();
                if (!bl) return;
                c c20 = this;
                this.t = 1;
                return;
            }
            case 31: {
                if (this.p != 0) return;
                main.b.a.i.b = d.c.a(this);
                ++this.p;
                return;
            }
            case 29: {
                if (this.p == 0) {
                    this.l.a = true;
                    ++this.p;
                }
                this.l.j = e.a.b.b(this.l.j, this.b, this.a);
                this.l.k = e.a.b.b(this.l.k, this.c, this.a);
                if (this.l.j != this.b || this.l.k != this.c) return;
                if (this.e == 1) {
                    this.l.a = false;
                    main.b.a.i.b(this.l);
                }
                c c21 = this;
                this.t = 1;
                return;
            }
            case 32: {
                switch (this.p) {
                    case 0: {
                        Object object;
                        c c22 = this;
                        this.l = b.a.x.a(c22.a);
                        if (c22.l == null) {
                            object = c22;
                            c22.t = 1;
                        } else {
                            object = b.a.x.a(c22.l.f(), c22.l.g(), (int)c22.b, (int)c22.c);
                            c22.h = main.c.a((byte[])object, c22.l, c22.f);
                        }
                        if (this.b()) return;
                        this.p = 1;
                    }
                    case 1: {
                        if (!this.h()) return;
                        this.l.a(this.e, false);
                    }
                    default: {
                        Object object = this;
                        this.t = 1;
                    }
                }
                return;
            }
            case 34: {
                b.a.c(this.e, this.f);
                c c23 = this;
                this.t = 1;
                return;
            }
            case 35: {
                this.f();
                return;
            }
            case 36: {
                b.a.a(this.a, this.e, this.b);
                this.t = 1;
                return;
            }
            case 37: {
                boolean bl;
                if (this.p == 0) {
                    ++this.p;
                    c c24 = b.a.a(b.a.x.c, (short)this.e);
                    if (c24 == null) {
                        c c25 = this;
                        this.t = 1;
                        return;
                    }
                    this.h = new c[]{c24};
                }
                if (!(bl = this.h())) return;
                c c26 = this;
                this.t = 1;
                return;
            }
            case 38: {
                boolean bl;
                Object object;
                if (this.p == 0) {
                    f.b b7 = b.a.a((int)this.a);
                    this.h = new c[4];
                    this.h[0] = new c(10);
                    this.h[0].a = b.a.s;
                    this.h[0].b = (short)2;
                    this.h[0].e = 0;
                    this.h[0].c = 1;
                    this.h[1] = new c(36);
                    this.h[1].a = this.a;
                    this.h[1].e = 0;
                    this.h[1].b = this.b;
                    this.h[2] = new c(26);
                    object = new StringBuffer();
                    if (this.a == -1) {
                        ((StringBuffer)object).append(b.a.p[31]);
                        ((StringBuffer)object).append(b.a.p[32]);
                        ((StringBuffer)object).append(this.b);
                    } else if (b7 == null) {
                        ((StringBuffer)object).append(b.a.p[3]);
                    } else {
                        ((StringBuffer)object).append(b.a.p[31]);
                        ((StringBuffer)object).append(b7.t);
                        ((StringBuffer)object).append(this.b);
                        ((StringBuffer)object).append(b.a.p[40]);
                    }
                    this.h[2].r = ((StringBuffer)object).toString();
                    this.h[2].e = 0;
                    this.h[3] = new c(14);
                    this.h[3].a = b.a.s;
                    this.h[3].e = 1;
                    this.h[3].f = 0;
                    this.h[3].g = 0;
                    this.h[3].b = (short)-1;
                    this.h[3].c = (short)-1;
                    ++this.p;
                }
                if (!(bl = this.h())) return;
                object = this;
                this.t = 1;
                return;
            }
            case 39: {
                if (this.p == 0) {
                    this.l = new q("r" + this.a);
                    this.l.a();
                    this.l.a((int)this.e, (int)this.f);
                    if (this.l == null || this.l.d == null) {
                        c c27 = this;
                        this.t = 1;
                        return;
                    }
                    main.b.a.i.a(this.l);
                    this.l.a = true;
                    if (this.g == 0) {
                        if (b.a.x == null) {
                            c c28 = this;
                            this.t = 1;
                            return;
                        }
                        this.l.j = this.b - b.a.x.a;
                        this.l.k = this.c - b.a.x.b;
                    } else if (this.g == 1) {
                        this.l.j = this.b;
                        this.l.k = this.c;
                    }
                    ++this.p;
                    return;
                }
                if (!this.l.c()) return;
                c c29 = this;
                this.t = 1;
                main.b.a.i.b(this.l);
                this.l.c = null;
                this.l = null;
                return;
            }
            case 40: {
                if (this.p != 0) return;
                if (v == null || v.length == 0) {
                    c c30 = this;
                    this.t = 1;
                    return;
                }
                u[] uArray = new u[2];
                Object object = uArray;
                uArray[0] = new c.f(v);
                object[1] = new d();
                Object object2 = e.a.c.a("/c.pak");
                Image[] imageArray = new Image[2];
                Image[] imageArray2 = imageArray;
                imageArray[0] = ((e.a.c)object2).b("s0.png");
                imageArray2[1] = ((e.a.c)object2).b("s1.png");
                object = new t(imageArray2, (u[])object, main.b.d, main.b.e);
                c.t.a(e.a.b.a("/bg.png"));
                ((j)object).f();
                object2 = this;
                this.t = 1;
                ++this.p;
                v = null;
                return;
            }
            case 41: {
                c c31 = this;
                this.t = 1;
                if (this.w == null) return;
                this.w.a((f)this.l, this.x, this.y);
                this.w = null;
                this.x = null;
                this.y = null;
                return;
            }
            case 42: {
                this.e();
                return;
            }
            case 43: {
                if (this.p == 0) {
                    boolean bl = false;
                    bl = b.a.b(this.a, (int)this.b);
                    this.m = bl ? 1 : 0;
                    ++this.p;
                }
                if (!this.a(this.m == 1)) return;
                c c32 = this;
                this.t = 1;
                return;
            }
            case 44: {
                c c33 = this;
                this.t = 1;
                return;
            }
            case 45: {
                c c34 = this;
                this.t = 1;
                return;
            }
            case 46: {
                main.b.a.i.a(new d.e());
                c c35 = this;
                this.t = 1;
                return;
            }
            case 47: {
                c c36 = this;
                this.t = 1;
                main.b.a.b();
                return;
            }
            case 48: {
                c c37 = this;
                this.t = 1;
                return;
            }
            case 50: {
                c c38 = this;
                this.t = 1;
                return;
            }
            case 51: {
                c c39 = this;
                this.t = 1;
                b.a.ae = true;
                return;
            }
            case 52: {
                c c40 = this;
                this.t = 1;
                b.a.ae = false;
                return;
            }
            case 53: {
                if (this.p >= this.a) {
                    c c41 = this;
                    this.t = 1;
                    return;
                }
                ++this.p;
                return;
            }
            case 54: {
                if (this.p == 0) {
                    if (this.g <= 0) {
                        c c42 = this;
                        this.t = 1;
                        return;
                    }
                    this.l = b.a.x.a(this.e);
                    if (this.l == null) {
                        c c43 = this;
                        this.t = 1;
                        return;
                    }
                    this.o = this.g << 2;
                    this.b = 0;
                }
                if (this.b <= 0) {
                    byte by = b.b.a(this.l.i, this.f);
                    this.l.b(by);
                    this.l.a(by, false);
                    this.b = this.a;
                    ++this.p;
                } else {
                    this.b = (short)(this.b - 1);
                }
                if (this.p < this.o) return;
                c c44 = this;
                this.t = 1;
                return;
            }
            case 55: {
                c c45 = this;
                this.t = 1;
                b.a.ag = this.e;
                b.a.ah = this.f;
                b.a.af = this.m;
                return;
            }
            case 56: {
                c c46 = this;
                this.t = 1;
                b.a.ah = 0;
                return;
            }
            case 57: {
                c c47 = this;
                this.t = 1;
                b.a.ai = this.e;
                b.a.aj = this.f;
                b.a.ak = this.g;
                return;
            }
            case 58: {
                c c48 = this;
                this.t = 1;
                b.a.aj = 0;
                return;
            }
            case 60: {
                if (this.p == 0) {
                    this.l = b.a.x.a(this.e);
                    if (this.l == null) {
                        c c49 = this;
                        this.t = 1;
                        return;
                    }
                    this.m = this.l.e;
                    ++this.p;
                    this.a = 0;
                    this.b = 0;
                }
                if (this.b < this.f) {
                    this.a = (short)(this.a + 1);
                    if (this.a < this.g) return;
                    this.a = 0;
                    if (this.l.d == null) {
                        this.l.a(this.m, -1);
                    } else {
                        this.l.a(-1, -1);
                    }
                    this.b = (short)(this.b + 1);
                    return;
                }
                this.l.a(this.m, -1);
                c c50 = this;
                this.t = 1;
                return;
            }
            case 61: {
                if (b.a.x != null) {
                    b.a.x.b(this.r);
                    b.a.x.c(this.s);
                    b.a.x.a(this.m);
                }
                c c51 = this;
                this.t = 1;
                return;
            }
            case 62: {
                c.b b8 = c.b.b(this.r);
                b8.f();
                c c52 = this;
                this.t = 1;
                return;
            }
            case 63: {
                c c53 = this;
                this.t = 1;
                b.a.d = this.m == 1;
            }
        }
    }

    private void d() {
        switch (this.p) {
            case 0: {
                int n2;
                if (this.h == null || this.h.length == 0) {
                    this.t = 1;
                    return;
                }
                for (n2 = 0; n2 < this.h.length; ++n2) {
                    this.h[n2].a();
                }
                for (n2 = 0; n2 < this.h.length; ++n2) {
                    if (this.h[n2].b()) continue;
                    return;
                }
                c c2 = this;
                this.t = 1;
                ++this.p;
            }
        }
    }

    public static c a(DataInputStream object, c c2) {
        if (object == null) {
            return null;
        }
        try {
            short s2 = ((DataInputStream)object).readByte();
            Object object2 = null;
            switch (s2) {
                case 4: {
                    c c3 = new c(4);
                    int n2 = ((DataInputStream)object).readInt();
                    if (n2 > 0) {
                        c3.h = new c[n2];
                        for (int i2 = 0; i2 < n2; ++i2) {
                            c3.h[i2] = main.c.a((DataInputStream)object, c3);
                        }
                    }
                    object2 = c3;
                    break;
                }
                case 1: {
                    String string = ((DataInputStream)object).readUTF();
                    object2 = ((DataInputStream)object).readUTF();
                    byte by = ((DataInputStream)object).readByte();
                    byte by2 = ((DataInputStream)object).readByte();
                    byte by3 = ((DataInputStream)object).readByte();
                    byte by4 = by;
                    byte by5 = by2;
                    by2 = by3;
                    by = by5;
                    byte by6 = by4;
                    c c4 = new c(1);
                    new c(1).r = string;
                    c4.s = object2;
                    c4.e = by6;
                    c4.f = by;
                    c4.g = by2;
                    c c5 = c4;
                    object2 = c5;
                    break;
                }
                case 2: {
                    c c6 = new c(2);
                    int n3 = ((DataInputStream)object).readByte();
                    c[] cArray = null;
                    if (n3 > 0) {
                        cArray = new c[n3];
                        for (int i3 = 0; i3 < n3; ++i3) {
                            cArray[i3] = main.c.a((DataInputStream)object, c6);
                        }
                    }
                    c6.h = cArray;
                    object2 = c6;
                    break;
                }
                case 3: {
                    c c7 = new c(3);
                    new c(3).r = ((DataInputStream)object).readUTF();
                    int n4 = ((DataInputStream)object).readShort();
                    if (n4 > 0) {
                        c7.h = new c[n4];
                        for (int i4 = 0; i4 < n4; ++i4) {
                            c7.h[i4] = main.c.a((DataInputStream)object, c7);
                        }
                    }
                    object2 = c7;
                    break;
                }
                case 5: {
                    s2 = ((DataInputStream)object).readShort();
                    byte by = ((DataInputStream)object).readByte();
                    short s3 = ((DataInputStream)object).readShort();
                    byte by7 = ((DataInputStream)object).readByte();
                    short s4 = ((DataInputStream)object).readShort();
                    object2 = object = main.c.a(s2, by, s3, by7, s4);
                    break;
                }
                case 7: {
                    s2 = ((DataInputStream)object).readByte();
                    short s5 = ((DataInputStream)object).readByte();
                    int n5 = ((DataInputStream)object).readInt();
                    int n6 = ((DataInputStream)object).readInt();
                    short s6 = ((DataInputStream)object).readShort();
                    int n7 = ((DataInputStream)object).readByte();
                    short s7 = s2;
                    s2 = (short)n7;
                    n7 = e.a.b.a(s5);
                    s5 = s7;
                    c c8 = new c(7);
                    new c(7).e = (byte)s5;
                    c8.a = (short)n7;
                    c8.m = n5;
                    c8.n = n6;
                    c8.b = s6;
                    c8.f = (byte)s2;
                    c c9 = c8;
                    object2 = c9;
                    break;
                }
                case 8: {
                    byte by = ((DataInputStream)object).readByte();
                    s2 = by;
                    byte by8 = by;
                    c c10 = new c(8);
                    new c(8).e = by8;
                    object2 = c10;
                    break;
                }
                case 6: {
                    Object object3;
                    byte by;
                    s2 = ((DataInputStream)object).readByte();
                    byte by9 = by = ((DataInputStream)object).readByte();
                    short s8 = s2;
                    object = new c(6);
                    new c(6).e = (byte)s8;
                    ((c)object).f = by9;
                    object2 = object3 = object;
                    break;
                }
                case 9: {
                    Object object4;
                    s2 = ((DataInputStream)object).readByte();
                    short s9 = ((DataInputStream)object).readShort();
                    short s10 = ((DataInputStream)object).readShort();
                    short s11 = s2;
                    s2 = s10;
                    short s12 = s9;
                    short s13 = s11;
                    object2 = new c(9);
                    new c(9).e = (byte)s13;
                    ((c)object2).a = s12;
                    ((c)object2).b = s2;
                    object2 = object4 = object2;
                    break;
                }
                case 10: {
                    c c11 = new c(10);
                    new c(10).a = ((DataInputStream)object).readShort();
                    c11.b = ((DataInputStream)object).readShort();
                    c11.e = ((DataInputStream)object).readByte();
                    c11.c = ((DataInputStream)object).readShort();
                    c11.m = ((DataInputStream)object).readBoolean() ? 1 : 0;
                    c11.n = ((DataInputStream)object).readBoolean() ? 1 : 0;
                    int n8 = ((DataInputStream)object).readShort();
                    if (n8 > 0) {
                        c11.h = new c[n8];
                        for (int i5 = 0; i5 < n8; ++i5) {
                            c11.h[i5] = main.c.a((DataInputStream)object, c11);
                        }
                    }
                    object2 = c11;
                    break;
                }
                case 12: {
                    Object object5;
                    byte by;
                    s2 = ((DataInputStream)object).readByte();
                    byte by10 = by = ((DataInputStream)object).readByte();
                    short s14 = s2;
                    object = new c(12);
                    new c(12).e = (byte)s14;
                    ((c)object).f = by10;
                    object2 = object5 = object;
                    break;
                }
                case 14: {
                    c c12 = new c(14);
                    new c(14).a = ((DataInputStream)object).readShort();
                    c12.e = ((DataInputStream)object).readByte();
                    c12.b = ((DataInputStream)object).readShort();
                    c12.c = ((DataInputStream)object).readShort();
                    c12.f = ((DataInputStream)object).readByte();
                    c12.g = ((DataInputStream)object).readByte();
                    if (c12.g == 1) {
                        c12.m = ((DataInputStream)object).readShort();
                        c12.n = ((DataInputStream)object).readShort();
                    }
                    c12.r = ((DataInputStream)object).readUTF();
                    object2 = c12;
                    break;
                }
                case 15: {
                    c c13 = new c(15);
                    new c(15).a = ((DataInputStream)object).readShort();
                    c13.e = ((DataInputStream)object).readByte();
                    c13.b = ((DataInputStream)object).readShort();
                    c13.f = ((DataInputStream)object).readByte();
                    object2 = c13;
                    break;
                }
                case 16: {
                    byte by;
                    c c14 = new c(16);
                    c14.e = by = ((DataInputStream)object).readByte();
                    switch (by) {
                        case 0: {
                            c14.f = ((DataInputStream)object).readByte();
                            break;
                        }
                        case 1: {
                            c14.a = ((DataInputStream)object).readShort();
                            c14.l = b.a.b(c14.a);
                            c14.f = ((DataInputStream)object).readByte();
                        }
                    }
                    c14.g = ((DataInputStream)object).readByte();
                    c14.m = ((DataInputStream)object).readShort();
                    c14.h = main.c.b((DataInputStream)object, c14);
                    object2 = c14;
                    break;
                }
                case 17: {
                    c c15 = new c(17);
                    int n9 = ((DataInputStream)object).readShort();
                    if (n9 >= 0) {
                        c15.h = new c[n9];
                        for (int i6 = 0; i6 < n9; ++i6) {
                            c15.h[i6] = main.c.a((DataInputStream)object, c15);
                        }
                    }
                    object2 = c15;
                    break;
                }
                case 18: {
                    c c16 = new c(18);
                    int n10 = ((DataInputStream)object).readShort();
                    if (n10 >= 0) {
                        c16.h = new c[n10];
                        for (int i7 = 0; i7 < n10; ++i7) {
                            c16.h[i7] = main.c.a((DataInputStream)object, c16);
                        }
                    }
                    object2 = c16;
                    break;
                }
                case 19: {
                    c c17 = new c(19);
                    new c(19).h = main.c.b((DataInputStream)object, c17);
                    object2 = c17;
                    break;
                }
                case 20: {
                    c c18 = new c(20);
                    new c(20).h = main.c.b((DataInputStream)object, c18);
                    object2 = c18;
                    break;
                }
                case 24: {
                    object2 = object = new c(24);
                    break;
                }
                case 25: {
                    byte by;
                    c c19 = new c(25);
                    c19.e = by = ((DataInputStream)object).readByte();
                    int n11 = e.a.b.a((int)((DataInputStream)object).readByte());
                    if (n11 > 0) {
                        c19.h = new c[n11];
                        for (int i8 = 0; i8 < n11; ++i8) {
                            c19.h[i8] = main.c.a((DataInputStream)object, c19);
                        }
                    }
                    object2 = c19;
                    break;
                }
                case 26: {
                    c c20 = new c(26);
                    new c(26).r = ((DataInputStream)object).readUTF();
                    c20.e = ((DataInputStream)object).readByte();
                    object2 = c20;
                    break;
                }
                case 28: {
                    object2 = new c(28);
                    break;
                }
                case 27: {
                    c c21 = new c(27);
                    new c(27).h = main.c.b((DataInputStream)object, c21);
                    object2 = c21;
                    break;
                }
                case 31: {
                    c c22 = new c(31);
                    new c(31).a = ((DataInputStream)object).readShort();
                    c22.b = ((DataInputStream)object).readShort();
                    c22.c = ((DataInputStream)object).readShort();
                    c22.e = ((DataInputStream)object).readByte();
                    c22.f = ((DataInputStream)object).readByte();
                    c22.g = ((DataInputStream)object).readByte();
                    c22.d = ((DataInputStream)object).readByte();
                    c22.h = main.c.b((DataInputStream)object, c22);
                    object2 = c22;
                    break;
                }
                case 32: {
                    c c23 = new c(32);
                    new c(32).a = ((DataInputStream)object).readShort();
                    c23.b = ((DataInputStream)object).readShort();
                    c23.c = ((DataInputStream)object).readShort();
                    c23.e = ((DataInputStream)object).readByte();
                    c23.f = ((DataInputStream)object).readByte();
                    object2 = c23;
                    break;
                }
                case 34: {
                    c c24 = new c(34);
                    new c(34).e = ((DataInputStream)object).readByte();
                    c24.f = ((DataInputStream)object).readByte();
                    object2 = c24;
                    break;
                }
                case 35: {
                    c c25 = new c(35);
                    new c(35).e = ((DataInputStream)object).readByte();
                    c25.f = ((DataInputStream)object).readByte();
                    object2 = c25;
                    break;
                }
                case 36: {
                    c c26 = new c(36);
                    new c(36).a = ((DataInputStream)object).readShort();
                    c26.e = ((DataInputStream)object).readByte();
                    c26.b = ((DataInputStream)object).readShort();
                    object2 = c26;
                    break;
                }
                case 37: {
                    c c27 = new c(37);
                    new c(37).e = ((DataInputStream)object).readByte();
                    object2 = c27;
                    break;
                }
                case 38: {
                    c c28 = new c(38);
                    new c(38).a = ((DataInputStream)object).readShort();
                    c28.b = ((DataInputStream)object).readShort();
                    object2 = c28;
                    break;
                }
                case 39: {
                    c c29 = new c(39);
                    new c(39).a = ((DataInputStream)object).readShort();
                    c29.e = ((DataInputStream)object).readByte();
                    c29.f = ((DataInputStream)object).readByte();
                    c29.b = ((DataInputStream)object).readShort();
                    c29.c = ((DataInputStream)object).readShort();
                    c29.g = ((DataInputStream)object).readByte();
                    object2 = c29;
                    break;
                }
                case 40: {
                    c c30 = new c(40);
                    int n12 = ((DataInputStream)object).readShort();
                    if (n12 > 0) {
                        v = new int[n12];
                        for (int i9 = 0; i9 < n12; ++i9) {
                            main.c.v[i9] = ((DataInputStream)object).readShort();
                        }
                    }
                    object2 = c30;
                    break;
                }
                case 42: {
                    c c31 = new c(42);
                    new c(42).e = (byte)(((DataInputStream)object).readBoolean() ? 1 : 0);
                    object2 = c31;
                    break;
                }
                case 43: {
                    c c32 = new c(43);
                    new c(43).a = ((DataInputStream)object).readShort();
                    c32.b = ((DataInputStream)object).readShort();
                    c32.h = main.c.b((DataInputStream)object, c32);
                    object2 = c32;
                    break;
                }
                case 44: {
                    object2 = new c(44);
                    break;
                }
                case 45: {
                    object2 = new c(45);
                    break;
                }
                case 46: {
                    object2 = new c(46);
                    break;
                }
                case 47: {
                    object2 = new c(47);
                    break;
                }
                case 48: {
                    c c33 = new c(48);
                    new c(48).a = ((DataInputStream)object).readShort();
                    c33.r = ((DataInputStream)object).readUTF();
                    c33.s = ((DataInputStream)object).readUTF();
                    object2 = c33;
                    break;
                }
                case 50: {
                    object2 = object = new c(50);
                    break;
                }
                case 51: {
                    object2 = new c(51);
                    break;
                }
                case 52: {
                    object2 = new c(52);
                    break;
                }
                case 53: {
                    c c34 = new c(53);
                    new c(53).a = ((DataInputStream)object).readShort();
                    object2 = c34;
                    break;
                }
                case 54: {
                    c c35 = new c(54);
                    new c(54).e = ((DataInputStream)object).readByte();
                    c35.f = ((DataInputStream)object).readByte();
                    c35.g = ((DataInputStream)object).readByte();
                    c35.a = ((DataInputStream)object).readByte();
                    object2 = c35;
                    break;
                }
                case 55: {
                    c c36 = new c(55);
                    new c(55).e = ((DataInputStream)object).readByte();
                    c36.f = ((DataInputStream)object).readByte();
                    c36.m = ((DataInputStream)object).readInt();
                    object2 = c36;
                    break;
                }
                case 56: {
                    object2 = new c(56);
                    break;
                }
                case 57: {
                    c c37 = new c(57);
                    new c(57).e = ((DataInputStream)object).readByte();
                    c37.f = ((DataInputStream)object).readByte();
                    c37.g = ((DataInputStream)object).readByte();
                    object2 = c37;
                    break;
                }
                case 58: {
                    object2 = new c(58);
                    break;
                }
                case 60: {
                    c c38 = new c(60);
                    new c(60).e = ((DataInputStream)object).readByte();
                    c38.f = ((DataInputStream)object).readByte();
                    c38.g = ((DataInputStream)object).readByte();
                    object2 = c38;
                    break;
                }
                case 61: {
                    c c39 = new c(61);
                    new c(61).r = ((DataInputStream)object).readUTF();
                    c39.s = ((DataInputStream)object).readUTF();
                    c39.m = ((DataInputStream)object).readInt();
                    object2 = c39;
                    break;
                }
                case 62: {
                    c c40 = new c(62);
                    new c(62).r = ((DataInputStream)object).readUTF();
                    object2 = c40;
                    break;
                }
                case 63: {
                    c c41 = new c(63);
                    new c(63).e = ((DataInputStream)object).readByte();
                    c41.m = ((DataInputStream)object).readBoolean() ? 1 : 0;
                    object2 = c41;
                    break;
                }
                default: {
                    return null;
                }
            }
            ((c)object2).u = c2;
            return object2;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return null;
        }
    }

    private boolean a(boolean bl) {
        if (this.h == null || this.h.length != 2) {
            return true;
        }
        for (int i2 = 0; i2 < this.h.length; ++i2) {
            if (this.h[i2].k == 19 && bl) {
                this.h[i2].a();
                return this.h[i2].b();
            }
            if (this.h[i2].k != 20 || bl) continue;
            this.h[i2].a();
            return this.h[i2].b();
        }
        return true;
    }

    private void e() {
        c c2 = this;
        this.t = 1;
        b.a.i = this.e == 1;
        if (b.a.i) {
            for (int i2 = 1; i2 < b.a.B.length; ++i2) {
                b.a.B[i2].j = b.a.B[0].j;
                b.a.B[i2].k = b.a.B[0].k;
                b.a.B[i2].a(b.a.B[0].i, false);
            }
        }
    }

    public static c a(f.b b2, f f2, f f3, f[] fArray) {
        c c2 = new c(41);
        new c(41).w = b2;
        c2.l = f2;
        c2.x = f3;
        c2.y = fArray;
        return c2;
    }

    public static c a(int n2, short s2, short s3, int n3) {
        c c2 = new c(39);
        new c(39).a = (short)n2;
        c2.e = 0;
        c2.f = (byte)n3;
        c2.b = s2;
        c2.c = s3;
        c2.g = 1;
        return c2;
    }

    private void f() {
        Object object = this;
        this.t = 1;
        object = b.a.a((short)this.e);
        if (object == null) {
            return;
        }
        switch (this.f) {
            case 0: {
                b.a.a((f)object);
                break;
            }
            case 1: {
                b.a.b((f)object);
                break;
            }
            default: {
                return;
            }
        }
        for (int i2 = 1; i2 < b.a.B.length; ++i2) {
            b.a.B[i2].j = b.a.B[0].f() << 4;
            b.a.B[i2].k = b.a.B[0].g() << 4;
            b.a.B[i2].a(b.a.B[0].i, false);
        }
    }

    public static c a(c c2) {
        c c3 = new c(28);
        new c(28).u = c2;
        return c3;
    }

    public static c a(String string, byte by) {
        c c2 = new c(26);
        new c(26).r = string;
        c2.e = 0;
        return c2;
    }

    public static c a(c[][] cArray) {
        c c2 = new c(17);
        if (cArray != null) {
            c2.h = new c[cArray.length];
            for (int i2 = 0; i2 < cArray.length; ++i2) {
                c c3 = new c(18);
                new c(18).h = cArray[i2];
                c2.h[i2] = c3;
            }
        }
        return c2;
    }

    public static c a(f f2, byte by, byte by2, int n2, c[] cArray) {
        c c2 = new c(16);
        new c(16).l = f2;
        c2.e = 1;
        c2.f = (byte)2;
        c2.g = by2;
        c2.m = n2;
        c2.h = null;
        return c2;
    }

    public static c a(c[] cArray) {
        c c2 = new c(27);
        new c(27).h = cArray;
        for (int i2 = 0; i2 < cArray.length; ++i2) {
            if (cArray[i2] == null) continue;
            cArray[i2].u = c2;
        }
        return c2;
    }

    public static c a(b.b b2, byte by, short s2, byte by2) {
        c c2 = new c(15);
        new c(15).l = b2;
        c2.e = by;
        c2.b = s2;
        c2.f = by2;
        return c2;
    }

    private void g() {
        boolean bl;
        if (this.p == 0) {
            int n2;
            if (this.h == null || this.h.length <= 0) {
                this.t = 1;
                return;
            }
            Object object = new String[this.h.length];
            for (int i2 = 0; i2 < this.h.length; ++i2) {
                c c2 = this.h[i2];
                object[i2] = c2.r;
            }
            c.b b2 = c.b.a(object, (byte)0, (byte)4, false);
            b2.f();
            int n3 = n2 = b2.c();
            object = this;
            if (object.h != null && object.h.length > 0) {
                if (n3 < 0 || n3 >= object.h.length) {
                    object.h = null;
                } else {
                    c c3 = object.h[n3];
                    object.h = c3.h;
                }
            }
            ++this.p;
        }
        if (bl = this.h()) {
            c c4 = this;
            this.t = 1;
            ++this.p;
        }
    }

    public static c a(b.b b2, int n2, int n3, int n4, c[] object, boolean bl) {
        object = new c(10);
        new c(10).l = b2;
        object.b = (short)n2;
        object.e = (byte)n3;
        object.c = (short)n4;
        object.h = null;
        object.m = bl ? 1 : 0;
        return object;
    }

    public final boolean b() {
        return this.t == 1;
    }

    public final void c() {
        this.t = 1;
    }

    private boolean h() {
        if (this.h == null || this.h.length == 0 || this.q >= this.h.length) {
            return true;
        }
        c c2 = this.h[this.q];
        if (c2 == null) {
            ++this.q;
            return false;
        }
        c2.a();
        if (c2.b()) {
            ++this.q;
            if (this.q >= this.h.length) {
                return true;
            }
        }
        return false;
    }

    private static c[] b(DataInputStream dataInputStream, c c2) {
        try {
            int n2 = dataInputStream.readShort();
            if (n2 <= 0) {
                return null;
            }
            c[] cArray = new c[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                cArray[i2] = main.c.a(dataInputStream, c2);
            }
            return cArray;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return null;
        }
    }

    private static c[] a(byte[] byArray, b.b b2, short s2) {
        if (byArray == null || byArray.length == 0 || b2 == null) {
            return null;
        }
        Vector<c> vector = new Vector<c>();
        Object var4_4 = null;
        boolean bl = false;
        byte by = 0;
        for (int i2 = 0; i2 < byArray.length; ++i2) {
            by = byArray[i2];
            short s3 = 1;
            while (i2 + 1 < byArray.length && by == byArray[i2 + 1]) {
                s3 = (short)(s3 + 1);
                ++i2;
            }
            c c2 = main.c.a(b2.f, by, s3, by, s2);
            vector.addElement(c2);
        }
        if (vector.isEmpty()) {
            return null;
        }
        Object[] objectArray = new c[vector.size()];
        vector.copyInto(objectArray);
        return objectArray;
    }
}

