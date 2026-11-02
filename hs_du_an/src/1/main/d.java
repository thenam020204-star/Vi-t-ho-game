/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
package main;

import b.c;
import b.e;
import b.j;
import b.p;
import b.s;
import b.t;
import b.u;
import d.f;
import d.g;
import e.a;
import g.l;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Image;
import main.b;

public final class d {
    private int i = -1;
    private d.b j = null;
    private int k = -1;
    private int l = -1;
    public short a = (short)-1;
    public short b = (short)-1;
    public short c = (short)-1;
    public short d = (short)-1;
    public byte e = (byte)-1;
    public byte f = (byte)-1;
    public byte g = (byte)-2;
    private int m = 0;
    private int n = 0;
    public d[] h = null;
    private int o = 0;
    private String p = null;
    private String q = null;
    private int r = 2;
    private d s = null;
    private static int[] t = null;
    private c.f u = null;
    private g v = null;
    private g[] w = null;

    private d(int n2) {
        this.i = n2;
    }

    public static d a(short s2, byte by, short s3, byte by2, short s4) {
        d d2 = new d(5);
        new d(5).a = s2;
        d2.b = s4;
        d2.c = s3;
        d2.e = by;
        d2.f = by2;
        return d2;
    }

    public static d a(int n2, int n3, d.b b2, int n4, int n5) {
        d d2 = new d(29);
        new d(29).a = (short)n4;
        d2.b = (short)n2;
        d2.c = (short)n3;
        d2.j = b2;
        d2.e = (byte)n5;
        return d2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void a() {
        if (this.r == 1) {
            return;
        }
        switch (this.i) {
            case 5: {
                Object object;
                byte by;
                boolean bl;
                if (this.n == 0) {
                    this.j = d.a.y.a(this.a);
                    if (this.j == null) {
                        d d2 = this;
                        this.r = 1;
                        return;
                    }
                    this.m = (this.c << 4) / this.b;
                    bl = true;
                    by = this.f;
                    object = this.j;
                    ((d.b)object).a(by, bl, false);
                    this.j.b(this.f);
                    this.j.g = 0;
                }
                this.j.a(this.e, this.b);
                ++this.n;
                if (this.n < this.m) return;
                this.j.g = 1;
                object = this;
                this.r = 1;
                bl = false;
                by = this.f;
                object = this.j;
                ((d.b)object).a(by, bl, false);
                return;
            }
            case 1: {
                if (this.n != 0) return;
                f.d d3 = d.a.b("head_r" + this.e);
                if (d3 == null && (d3 = d.a.a("head_r" + this.e, null)) != null) {
                    d.a.a(d3);
                }
                f.a a2 = null;
                if (d3 != null) {
                    a2 = d3.b(this.g);
                }
                c c2 = b.c.a(this.p, this, this.q, a2, this.f);
                c2.a((g.g)main.b.a.i);
                main.b.a.i.e(c2);
                ++this.n;
                return;
            }
            case 2: {
                this.g();
                return;
            }
            case 6: {
                Object object;
                v0.j = d.a.y.a(this.e);
                if (this.j != null) {
                    boolean bl = false;
                    byte by = this.f;
                    object = this.j;
                    ((d.b)object).a(by, bl, false);
                    this.j.b(this.f);
                }
                object = this;
                this.r = 1;
                return;
            }
            case 7: {
                d d4;
                if (this.n == 0) {
                    d4 = this;
                    if ((d4.e & 0xF) == 2) {
                        d.b b2 = d.a.y.a(d4.b);
                        if (b2 != null) {
                            d4.k = b2.l() + 8 - main.b.e;
                            d4.l = b2.m() - 8 - main.b.f;
                        } else {
                            d4.k = d.a.y.b;
                            d4.l = d.a.y.c;
                        }
                    } else {
                        d4.k = (d4.k << 4) + 8 - main.b.e;
                        d4.l = (d4.l << 4) + 8 - main.b.f;
                    }
                    d.a.w = 2;
                    if (d4.k < 0) {
                        d4.k = 0;
                    } else if (d4.k + main.b.c > d.a.y.c()) {
                        d4.k = d.a.y.c() - main.b.c;
                    }
                    if (d4.l < 0) {
                        d4.l = 0;
                    } else if (d4.l + main.b.d > d.a.y.f()) {
                        d4.l = d.a.y.f() - main.b.d;
                    }
                    ++this.n;
                }
                switch (this.e >> 4 & 0xF) {
                    case 2: {
                        d.a.y.a(this.k, this.l, (int)this.a);
                        break;
                    }
                    case 1: {
                        d.a.y.d(this.k, this.l);
                        break;
                    }
                    case 3: {
                        d.a.y.b = this.k;
                        d.a.y.c = this.l;
                    }
                }
                if (d.a.y.c != this.l || d.a.y.b != this.k) return;
                d4 = this;
                this.r = 1;
                return;
            }
            case 8: {
                Object object = d.a.y.a(this.e);
                if (object == null) {
                    object = this;
                    this.r = 1;
                    return;
                }
                d.a.w = 1;
                d.a.x = this.e;
                object = this;
                this.r = 1;
                return;
            }
            case 9: {
                if (this.e != -1 && h.b.a((int)this.e) != d.a.y.d) {
                    d.a.z = h.b.a((int)this.e);
                    d.a.A = this.a;
                    d.a.B = this.b;
                    main.b.a.i.a = new l();
                } else {
                    d.a.C[0].b(this.a << 4);
                    d.a.C[0].c(this.b << 4);
                }
                d d5 = this;
                this.r = 1;
                return;
            }
            case 10: {
                switch (this.n) {
                    case 0: {
                        d d6;
                        if (this.j == null) {
                            this.j = d.a.y.a(this.a);
                        }
                        if (this.j == null || this.j.c == null) {
                            d d7 = this;
                            this.r = 1;
                            return;
                        }
                        this.j.b(this.b, this.e);
                        if (this.k == 1 && this.j.d != null) {
                            this.j.d.a(true);
                        }
                        ++this.n;
                        if (this.e == -1) {
                            d6 = this;
                            this.r = 1;
                            ++this.n;
                        }
                    }
                    case 1: {
                        if (!this.j.f()) return;
                        d d6 = this;
                        this.r = 1;
                        ++this.n;
                        if (this.j.d != null && this.j.d.e() == -1) return;
                        this.j.b(this.c, -1);
                    }
                }
                return;
            }
            case 12: {
                d.a.a(this.e, this.f);
                d d8 = this;
                this.r = 1;
                return;
            }
            case 14: {
                Object object = this;
                this.r = 1;
                object = d.a.y.b(this.a);
                if (object == null) return;
                ((d.b)object).a((this.e & 1) != 0);
                ((f)object).b((this.e >> 1 & 1) != 0);
                ((f)object).c((this.e >> 2 & 1) != 0);
                if (this.b != -1) {
                    ((d.b)object).b(this.b << 4);
                }
                if (this.c != -1) {
                    ((d.b)object).c(this.c << 4);
                }
                if (this.f != 4) {
                    ((f)object).c(this.f);
                }
                if (this.g == 2) return;
                ((f)object).d(this.g);
                if (this.g != 1) return;
                ((f)object).a((short)this.k);
                ((f)object).b((short)this.l);
                return;
            }
            case 15: {
                d d9 = this;
                this.r = 1;
                if (this.j == null) {
                    this.j = d.a.b(this.a);
                    if (this.j == null) return;
                }
                ((g)this.j).a(this.f, this.e, this.b);
                return;
            }
            case 16: {
                switch (this.n) {
                    case 0: {
                        byte by;
                        int n2 = -1;
                        switch (this.e) {
                            case 0: {
                                n2 = h.b.a((int)d.a.a(this.f));
                                break;
                            }
                            case 1: {
                                if (this.j == null) {
                                    d d10 = this;
                                    this.r = 1;
                                    return;
                                }
                                n2 = ((g)this.j).e((int)this.f);
                            }
                        }
                        int n3 = this.k;
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
                            d d11 = this;
                            this.r = 1;
                            return;
                        }
                        this.n = 1;
                        return;
                    }
                    case 1: {
                        boolean bl = this.h();
                        if (!bl) return;
                        d d12 = this;
                        this.r = 1;
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
                this.r = 1;
                return;
            }
            case 24: {
                d.a.s = null;
                return;
            }
            case 26: {
                if (this.n != 0) return;
                switch (this.e) {
                    case 1: {
                        c c3 = b.c.a(this.p, this);
                        main.b.a.i.e(c3);
                        c3.a(main.b.a.i);
                        break;
                    }
                    case 0: {
                        c c4 = b.c.a(this.p, 4000L, this);
                        main.b.a.i.e(c4);
                        c4.a(main.b.a.i);
                        break;
                    }
                    default: {
                        d d13 = this;
                        this.r = 1;
                    }
                }
                ++this.n;
                return;
            }
            case 28: {
                d d14 = this;
                this.r = 1;
                d14 = this.s;
                this.s.r = 1;
                d14 = this.s.s;
                this.s.s.r = 1;
                return;
            }
            case 27: {
                boolean bl = this.h();
                if (!bl) return;
                d d15 = this;
                this.r = 1;
                return;
            }
            case 31: {
                if (this.n != 0) return;
                main.b.a.i.a = new g.b(this);
                ++this.n;
                return;
            }
            case 29: {
                if (this.n == 0) {
                    this.j.a(true);
                    ++this.n;
                }
                this.j.b(h.b.b(this.j.l(), this.b, this.a));
                this.j.c(h.b.b(this.j.m(), this.c, this.a));
                if (this.j.l() != this.b || this.j.m() != this.c) return;
                if (this.e == 1) {
                    this.j.a(false);
                    main.b.a.i.b(this.j);
                }
                d d16 = this;
                this.r = 1;
                return;
            }
            case 32: {
                switch (this.n) {
                    case 0: {
                        Object object = this;
                        this.j = d.a.y.a(((d)object).a);
                        if (((d)object).j == null) {
                            v2.r = 1;
                        } else {
                            byte[] byArray = d.a.y.a(((d)object).j.i(), ((d)object).j.j(), (int)((d)object).b, (int)((d)object).c);
                            ((d)object).h = main.d.a(byArray, ((d)object).j, ((d)object).f);
                        }
                        if (this.b()) return;
                        this.n = 1;
                    }
                    case 1: {
                        if (!this.h()) return;
                        boolean bl = false;
                        byte by = this.e;
                        Object object = this.j;
                        ((d.b)object).a(by, bl, false);
                    }
                    default: {
                        Object object = this;
                        this.r = 1;
                    }
                }
                return;
            }
            case 34: {
                d.a.c(this.e, this.f);
                d d17 = this;
                this.r = 1;
                return;
            }
            case 35: {
                this.f();
                return;
            }
            case 36: {
                d.a.a(this.a, this.e, this.b);
                this.r = 1;
                return;
            }
            case 37: {
                boolean bl;
                if (this.n == 0) {
                    ++this.n;
                    d d18 = d.a.a(d.a.y.d, (short)this.e);
                    if (d18 == null) {
                        d18 = this;
                        this.r = 1;
                        return;
                    }
                    this.h = new d[]{d18};
                }
                if (!(bl = this.h())) return;
                d d19 = this;
                this.r = 1;
                return;
            }
            case 38: {
                boolean bl;
                if (this.n == 0) {
                    c.f f2 = d.a.a((int)this.a);
                    this.h = new d[4];
                    this.h[0] = new d(10);
                    this.h[0].a = d.a.t;
                    this.h[0].b = (short)2;
                    this.h[0].e = 0;
                    this.h[0].c = 1;
                    this.h[1] = new d(36);
                    this.h[1].a = this.a;
                    this.h[1].e = 0;
                    this.h[1].b = this.b;
                    this.h[2] = new d(26);
                    StringBuffer stringBuffer = new StringBuffer();
                    if (this.a == -1) {
                        stringBuffer.append(d.a.q[31]);
                        stringBuffer.append(d.a.q[32]);
                        stringBuffer.append(this.b);
                    } else if (f2 == null) {
                        stringBuffer.append(d.a.q[3]);
                    } else {
                        stringBuffer.append(d.a.q[31]);
                        stringBuffer.append(f2.r);
                        stringBuffer.append(this.b);
                        stringBuffer.append(d.a.q[40]);
                    }
                    this.h[2].p = stringBuffer.toString();
                    this.h[2].e = 0;
                    this.h[3] = new d(14);
                    this.h[3].a = d.a.t;
                    this.h[3].e = 1;
                    this.h[3].f = 0;
                    this.h[3].g = 0;
                    this.h[3].b = (short)-1;
                    this.h[3].c = (short)-1;
                    ++this.n;
                }
                if (!(bl = this.h())) return;
                d d20 = this;
                this.r = 1;
                return;
            }
            case 39: {
                if (this.n == 0) {
                    this.j = new p("r" + this.a);
                    this.j.a((a)null);
                    this.j.b(this.e, this.f);
                    if (this.j == null || this.j.d == null) {
                        d d21 = this;
                        this.r = 1;
                        return;
                    }
                    main.b.a.i.a(this.j);
                    this.j.a(true);
                    if (this.g == 0) {
                        if (d.a.y == null) {
                            d d22 = this;
                            this.r = 1;
                            return;
                        }
                        this.j.b(this.b - d.a.y.b);
                        this.j.c(this.c - d.a.y.c);
                    } else if (this.g == 1) {
                        this.j.b(this.b);
                        this.j.c(this.c);
                    }
                    ++this.n;
                    return;
                }
                if (!this.j.f()) return;
                d d23 = this;
                this.r = 1;
                main.b.a.i.b(this.j);
                this.j.c = null;
                this.j = null;
                return;
            }
            case 40: {
                if (this.n != 0) return;
                if (t == null || t.length == 0) {
                    d d24 = this;
                    this.r = 1;
                    return;
                }
                t[] tArray = new t[2];
                Object object = tArray;
                tArray[0] = new u(t);
                object[1] = new e();
                a a3 = e.a.a("/c.pak");
                Image[] imageArray = new Image[2];
                Image[] imageArray2 = imageArray;
                imageArray[0] = a3.c("s0.png");
                imageArray2[1] = a3.c("s1.png");
                object = new s(imageArray2, (t[])object, main.b.c, main.b.d, this);
                a3 = a3.c("bg.png");
                ((s)object).a((Image)a3);
                main.b.a.i.e((j)object);
                ((j)object).a((g.g)main.b.a.i);
                ++this.n;
                t = null;
                return;
            }
            case 41: {
                d d25 = this;
                this.r = 1;
                if (this.u == null) return;
                this.u.a((g)this.j, this.v, this.w);
                this.u = null;
                this.v = null;
                this.w = null;
                return;
            }
            case 42: {
                this.e();
                return;
            }
            case 43: {
                if (this.n == 0) {
                    boolean bl = false;
                    bl = d.a.b(this.a, (int)this.b);
                    this.k = bl ? 1 : 0;
                    ++this.n;
                }
                if (!this.a(this.k == 1)) return;
                d d26 = this;
                this.r = 1;
                return;
            }
            case 44: {
                d d27 = this;
                this.r = 1;
                return;
            }
            case 45: {
                d d28 = this;
                this.r = 1;
                return;
            }
            case 46: {
                main.b.a.i.a(new g.d());
                d d29 = this;
                this.r = 1;
                return;
            }
            case 47: {
                d d30 = this;
                this.r = 1;
                main.b.a.b();
                return;
            }
            case 48: {
                d d31 = this;
                this.r = 1;
            }
        }
    }

    private void d() {
        switch (this.n) {
            case 0: {
                int n2;
                if (this.h == null || this.h.length == 0) {
                    this.r = 1;
                    return;
                }
                for (n2 = 0; n2 < this.h.length; ++n2) {
                    this.h[n2].a();
                }
                for (n2 = 0; n2 < this.h.length; ++n2) {
                    if (this.h[n2].b()) continue;
                    return;
                }
                d d2 = this;
                this.r = 1;
                ++this.n;
            }
        }
    }

    public static d a(DataInputStream object, d d2) {
        if (object == null) {
            return null;
        }
        try {
            int n2 = ((DataInputStream)object).readByte();
            Object object2 = null;
            switch (n2) {
                case 4: {
                    d d3 = new d(4);
                    int n3 = ((DataInputStream)object).readInt();
                    if (n3 > 0) {
                        d3.h = new d[n3];
                        for (int i2 = 0; i2 < n3; ++i2) {
                            d3.h[i2] = main.d.a((DataInputStream)object, d3);
                        }
                    }
                    object2 = d3;
                    break;
                }
                case 1: {
                    String string = ((DataInputStream)object).readUTF();
                    object2 = ((DataInputStream)object).readUTF();
                    byte by = ((DataInputStream)object).readByte();
                    byte by2 = ((DataInputStream)object).readByte();
                    byte by3 = ((DataInputStream)object).readByte();
                    byte by4 = by2;
                    by2 = by3;
                    byte by5 = by4;
                    by3 = by;
                    Object object3 = object2;
                    object2 = string;
                    d d4 = new d(1);
                    new d(1).p = object2;
                    d4.q = object3;
                    d4.e = by3;
                    d4.f = by5;
                    d4.g = by2;
                    d d5 = d4;
                    object2 = d5;
                    break;
                }
                case 2: {
                    d d6 = new d(2);
                    int n4 = ((DataInputStream)object).readByte();
                    d[] dArray = null;
                    if (n4 > 0) {
                        dArray = new d[n4];
                        for (int i3 = 0; i3 < n4; ++i3) {
                            dArray[i3] = main.d.a((DataInputStream)object, d6);
                        }
                    }
                    d6.h = dArray;
                    object2 = d6;
                    break;
                }
                case 3: {
                    d d7 = new d(3);
                    new d(3).p = ((DataInputStream)object).readUTF();
                    int n5 = ((DataInputStream)object).readShort();
                    if (n5 > 0) {
                        d7.h = new d[n5];
                        for (int i4 = 0; i4 < n5; ++i4) {
                            d7.h[i4] = main.d.a((DataInputStream)object, d7);
                        }
                    }
                    object2 = d7;
                    break;
                }
                case 5: {
                    n2 = ((DataInputStream)object).readShort();
                    byte by = ((DataInputStream)object).readByte();
                    short s2 = ((DataInputStream)object).readShort();
                    byte by6 = ((DataInputStream)object).readByte();
                    short s3 = ((DataInputStream)object).readShort();
                    d d8 = main.d.a((short)n2, by, s2, by6, s3);
                    object2 = d8;
                    break;
                }
                case 7: {
                    n2 = ((DataInputStream)object).readByte();
                    int n6 = ((DataInputStream)object).readByte();
                    int n7 = ((DataInputStream)object).readInt();
                    int n8 = ((DataInputStream)object).readInt();
                    int n9 = ((DataInputStream)object).readShort();
                    int n10 = n2;
                    int n11 = n8;
                    n8 = n9;
                    n2 = n11;
                    n9 = n7;
                    n7 = h.b.a(n6);
                    n6 = n10;
                    d d9 = new d(7);
                    new d(7).e = (byte)n6;
                    d9.a = (short)n7;
                    d9.k = n9;
                    d9.l = n2;
                    d9.b = (short)n8;
                    d d10 = d9;
                    object2 = d10;
                    break;
                }
                case 8: {
                    byte by = ((DataInputStream)object).readByte();
                    n2 = by;
                    byte by7 = by;
                    d d11 = new d(8);
                    new d(8).e = by7;
                    object2 = d11;
                    break;
                }
                case 6: {
                    byte by;
                    n2 = ((DataInputStream)object).readByte();
                    byte by8 = by = ((DataInputStream)object).readByte();
                    int n12 = n2;
                    d d12 = new d(6);
                    new d(6).e = (byte)n12;
                    d12.f = by8;
                    d d13 = d12;
                    object2 = d13;
                    break;
                }
                case 9: {
                    n2 = ((DataInputStream)object).readByte();
                    int n13 = ((DataInputStream)object).readShort();
                    int n14 = ((DataInputStream)object).readShort();
                    int n15 = n2;
                    int n16 = n13;
                    n13 = n14;
                    n2 = n16;
                    int n17 = n15;
                    d d14 = new d(9);
                    new d(9).e = (byte)n17;
                    d14.a = (short)n2;
                    d14.b = (short)n13;
                    d d15 = d14;
                    object2 = d15;
                    break;
                }
                case 10: {
                    d d16 = new d(10);
                    new d(10).a = ((DataInputStream)object).readShort();
                    d16.b = ((DataInputStream)object).readShort();
                    d16.e = ((DataInputStream)object).readByte();
                    d16.c = ((DataInputStream)object).readShort();
                    int n18 = ((DataInputStream)object).readShort();
                    if (n18 > 0) {
                        d16.h = new d[n18];
                        for (int i5 = 0; i5 < n18; ++i5) {
                            d16.h[i5] = main.d.a((DataInputStream)object, d16);
                        }
                    }
                    object2 = d16;
                    break;
                }
                case 12: {
                    byte by;
                    n2 = ((DataInputStream)object).readByte();
                    byte by9 = by = ((DataInputStream)object).readByte();
                    int n19 = n2;
                    d d17 = new d(12);
                    new d(12).e = (byte)n19;
                    d17.f = by9;
                    d d18 = d17;
                    object2 = d18;
                    break;
                }
                case 14: {
                    d d19 = new d(14);
                    new d(14).a = ((DataInputStream)object).readShort();
                    d19.e = ((DataInputStream)object).readByte();
                    d19.b = ((DataInputStream)object).readShort();
                    d19.c = ((DataInputStream)object).readShort();
                    d19.f = ((DataInputStream)object).readByte();
                    d19.g = ((DataInputStream)object).readByte();
                    if (d19.g == 1) {
                        d19.k = ((DataInputStream)object).readShort();
                        d19.l = ((DataInputStream)object).readShort();
                    }
                    object2 = d19;
                    break;
                }
                case 15: {
                    d d20 = new d(15);
                    new d(15).a = ((DataInputStream)object).readShort();
                    d20.e = ((DataInputStream)object).readByte();
                    d20.b = ((DataInputStream)object).readShort();
                    d20.f = ((DataInputStream)object).readByte();
                    object2 = d20;
                    break;
                }
                case 16: {
                    byte by;
                    d d21 = new d(16);
                    d21.e = by = ((DataInputStream)object).readByte();
                    switch (by) {
                        case 0: {
                            d21.f = ((DataInputStream)object).readByte();
                            break;
                        }
                        case 1: {
                            d21.a = ((DataInputStream)object).readShort();
                            d21.j = d.a.b(d21.a);
                            d21.f = ((DataInputStream)object).readByte();
                        }
                    }
                    d21.g = ((DataInputStream)object).readByte();
                    d21.k = ((DataInputStream)object).readShort();
                    d21.h = main.d.b((DataInputStream)object, d21);
                    object2 = d21;
                    break;
                }
                case 17: {
                    d d22 = new d(17);
                    int n20 = ((DataInputStream)object).readShort();
                    if (n20 >= 0) {
                        d22.h = new d[n20];
                        for (int i6 = 0; i6 < n20; ++i6) {
                            d22.h[i6] = main.d.a((DataInputStream)object, d22);
                        }
                    }
                    object2 = d22;
                    break;
                }
                case 18: {
                    d d23 = new d(18);
                    int n21 = ((DataInputStream)object).readShort();
                    if (n21 >= 0) {
                        d23.h = new d[n21];
                        for (int i7 = 0; i7 < n21; ++i7) {
                            d23.h[i7] = main.d.a((DataInputStream)object, d23);
                        }
                    }
                    object2 = d23;
                    break;
                }
                case 19: {
                    d d24 = new d(19);
                    new d(19).h = main.d.b((DataInputStream)object, d24);
                    object2 = d24;
                    break;
                }
                case 20: {
                    d d25 = new d(20);
                    new d(20).h = main.d.b((DataInputStream)object, d25);
                    object2 = d25;
                    break;
                }
                case 24: {
                    object2 = object = new d(24);
                    break;
                }
                case 25: {
                    byte by;
                    d d26 = new d(25);
                    d26.e = by = ((DataInputStream)object).readByte();
                    int n22 = h.b.a((int)((DataInputStream)object).readByte());
                    if (n22 > 0) {
                        d26.h = new d[n22];
                        for (int i8 = 0; i8 < n22; ++i8) {
                            d26.h[i8] = main.d.a((DataInputStream)object, d26);
                        }
                    }
                    object2 = d26;
                    break;
                }
                case 26: {
                    d d27 = new d(26);
                    new d(26).p = ((DataInputStream)object).readUTF();
                    d27.e = ((DataInputStream)object).readByte();
                    object2 = d27;
                    break;
                }
                case 28: {
                    object2 = new d(28);
                    break;
                }
                case 27: {
                    d d28 = new d(27);
                    new d(27).h = main.d.b((DataInputStream)object, d28);
                    object2 = d28;
                    break;
                }
                case 31: {
                    d d29 = new d(31);
                    new d(31).a = ((DataInputStream)object).readShort();
                    d29.b = ((DataInputStream)object).readShort();
                    d29.c = ((DataInputStream)object).readShort();
                    d29.e = ((DataInputStream)object).readByte();
                    d29.f = ((DataInputStream)object).readByte();
                    d29.g = ((DataInputStream)object).readByte();
                    d29.d = ((DataInputStream)object).readByte();
                    d29.h = main.d.b((DataInputStream)object, d29);
                    object2 = d29;
                    break;
                }
                case 32: {
                    d d30 = new d(32);
                    new d(32).a = ((DataInputStream)object).readShort();
                    d30.b = ((DataInputStream)object).readShort();
                    d30.c = ((DataInputStream)object).readShort();
                    d30.e = ((DataInputStream)object).readByte();
                    d30.f = ((DataInputStream)object).readByte();
                    object2 = d30;
                    break;
                }
                case 34: {
                    d d31 = new d(34);
                    new d(34).e = ((DataInputStream)object).readByte();
                    d31.f = ((DataInputStream)object).readByte();
                    object2 = d31;
                    break;
                }
                case 35: {
                    d d32 = new d(35);
                    new d(35).e = ((DataInputStream)object).readByte();
                    d32.f = ((DataInputStream)object).readByte();
                    object2 = d32;
                    break;
                }
                case 36: {
                    d d33 = new d(36);
                    new d(36).a = ((DataInputStream)object).readShort();
                    d33.e = ((DataInputStream)object).readByte();
                    d33.b = ((DataInputStream)object).readShort();
                    object2 = d33;
                    break;
                }
                case 37: {
                    d d34 = new d(37);
                    new d(37).e = ((DataInputStream)object).readByte();
                    object2 = d34;
                    break;
                }
                case 38: {
                    d d35 = new d(38);
                    new d(38).a = ((DataInputStream)object).readShort();
                    d35.b = ((DataInputStream)object).readShort();
                    object2 = d35;
                    break;
                }
                case 39: {
                    d d36 = new d(39);
                    new d(39).a = ((DataInputStream)object).readShort();
                    d36.e = ((DataInputStream)object).readByte();
                    d36.f = ((DataInputStream)object).readByte();
                    d36.b = ((DataInputStream)object).readShort();
                    d36.c = ((DataInputStream)object).readShort();
                    d36.g = ((DataInputStream)object).readByte();
                    object2 = d36;
                    break;
                }
                case 40: {
                    d d37 = new d(40);
                    int n23 = ((DataInputStream)object).readShort();
                    if (n23 > 0) {
                        t = new int[n23];
                        for (int i9 = 0; i9 < n23; ++i9) {
                            main.d.t[i9] = ((DataInputStream)object).readShort();
                        }
                    }
                    object2 = d37;
                    break;
                }
                case 42: {
                    d d38 = new d(42);
                    new d(42).e = (byte)(((DataInputStream)object).readBoolean() ? 1 : 0);
                    object2 = d38;
                    break;
                }
                case 43: {
                    d d39 = new d(43);
                    new d(43).a = ((DataInputStream)object).readShort();
                    d39.b = ((DataInputStream)object).readShort();
                    d39.h = main.d.b((DataInputStream)object, d39);
                    object2 = d39;
                    break;
                }
                case 44: {
                    object2 = new d(44);
                    break;
                }
                case 45: {
                    object2 = new d(45);
                    break;
                }
                case 46: {
                    object2 = new d(46);
                    break;
                }
                case 47: {
                    object2 = new d(47);
                    break;
                }
                case 48: {
                    d d40 = new d(48);
                    new d(48).a = ((DataInputStream)object).readShort();
                    d40.p = ((DataInputStream)object).readUTF();
                    d40.q = ((DataInputStream)object).readUTF();
                    object2 = d40;
                    break;
                }
                default: {
                    return null;
                }
            }
            ((d)object2).s = d2;
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
            if (this.h[i2].i == 19 && bl) {
                this.h[i2].a();
                return this.h[i2].b();
            }
            if (this.h[i2].i != 20 || bl) continue;
            this.h[i2].a();
            return this.h[i2].b();
        }
        return true;
    }

    private void e() {
        Object object = this;
        this.r = 1;
        d.a.k = this.e == 1;
        if (d.a.k) {
            for (int i2 = 1; i2 < d.a.C.length; ++i2) {
                d.a.C[i2].b(d.a.C[0].l());
                d.a.C[i2].c(d.a.C[0].m());
                boolean bl = false;
                byte by = d.a.C[0].h();
                object = d.a.C[i2];
                ((d.b)object).a(by, bl, false);
            }
        }
    }

    public static d a(c.f f2, g g2, g g3, g[] gArray) {
        d d2 = new d(41);
        new d(41).u = f2;
        d2.j = g2;
        d2.v = g3;
        d2.w = gArray;
        return d2;
    }

    public static d a(int n2, short s2, short s3, int n3) {
        d d2 = new d(39);
        new d(39).a = (short)n2;
        d2.e = 0;
        d2.f = (byte)n3;
        d2.b = s2;
        d2.c = s3;
        d2.g = 1;
        return d2;
    }

    private void f() {
        Object object = this;
        this.r = 1;
        object = d.a.a((short)this.e);
        if (object == null) {
            return;
        }
        switch (this.f) {
            case 0: {
                d.a.a((g)object);
                break;
            }
            case 1: {
                d.a.b((g)object);
                break;
            }
            default: {
                return;
            }
        }
        for (int i2 = 1; i2 < d.a.C.length; ++i2) {
            d.a.C[i2].b(d.a.C[0].i() << 4);
            d.a.C[i2].c(d.a.C[0].j() << 4);
            boolean bl = false;
            byte by = d.a.C[0].h();
            object = d.a.C[i2];
            ((d.b)object).a(by, bl, false);
        }
    }

    public static d a(d d2) {
        d d3 = new d(28);
        new d(28).s = d2;
        return d3;
    }

    public static d a(String string, byte by) {
        d d2 = new d(26);
        new d(26).p = string;
        d2.e = 0;
        return d2;
    }

    public static d a(d[][] dArray) {
        d d2 = new d(17);
        if (dArray != null) {
            d2.h = new d[dArray.length];
            for (int i2 = 0; i2 < dArray.length; ++i2) {
                d d3 = new d(18);
                new d(18).h = dArray[i2];
                d2.h[i2] = d3;
            }
        }
        return d2;
    }

    public static d a(g g2, byte by, byte by2, int n2, d[] dArray) {
        d d2 = new d(16);
        new d(16).j = g2;
        d2.e = 1;
        d2.f = (byte)2;
        d2.g = by2;
        d2.k = n2;
        d2.h = null;
        return d2;
    }

    public static d a(d[] dArray) {
        d d2 = new d(27);
        new d(27).h = dArray;
        for (int i2 = 0; i2 < dArray.length; ++i2) {
            if (dArray[i2] == null) continue;
            dArray[i2].s = d2;
        }
        return d2;
    }

    public static d a(d.b b2, byte by, short s2, byte by2) {
        d d2 = new d(15);
        new d(15).j = b2;
        d2.e = by;
        d2.b = s2;
        d2.f = by2;
        return d2;
    }

    private void g() {
        switch (this.n) {
            case 0: {
                if (this.h == null || this.h.length <= 0) {
                    this.r = 1;
                    return;
                }
                String[] stringArray = new String[this.h.length];
                for (int i2 = 0; i2 < this.h.length; ++i2) {
                    d d2 = this.h[i2];
                    stringArray[i2] = d2.p;
                }
                c c2 = b.c.a(stringArray, this, (byte)0, (byte)4);
                main.b.a.i.e(c2);
                c2.a((g.g)main.b.a.i);
                ++this.n;
                return;
            }
            case 1: {
                return;
            }
            case 2: {
                boolean bl = this.h();
                if (!bl) break;
                d d3 = this;
                this.r = 1;
                ++this.n;
            }
        }
    }

    public static d a(d.b b2, int n2, int n3, int n4, d[] object, boolean bl) {
        object = new d(10);
        new d(10).j = b2;
        object.b = (short)n2;
        object.e = (byte)n3;
        object.c = (short)n4;
        object.h = null;
        object.k = bl ? 1 : 0;
        return object;
    }

    public final void a(int n2) {
        switch (this.i) {
            case 1: {
                v0.r = 1;
                return;
            }
            case 2: {
                ++this.n;
                if (this.h == null || this.h.length <= 0) break;
                if (n2 < 0 || n2 >= this.h.length) {
                    this.h = null;
                    break;
                }
                d d2 = this.h[n2];
                this.h = d2.h;
                return;
            }
            case 26: {
                v1.r = 1;
                return;
            }
            case 40: {
                v2.r = 1;
            }
        }
    }

    public final boolean b() {
        return this.r == 1;
    }

    public final void c() {
        this.r = 1;
    }

    private boolean h() {
        if (this.h == null || this.h.length == 0 || this.o >= this.h.length) {
            return true;
        }
        d d2 = this.h[this.o];
        if (d2 == null) {
            ++this.o;
            return false;
        }
        d2.a();
        if (d2.b()) {
            ++this.o;
            if (this.o >= this.h.length) {
                return true;
            }
        }
        return false;
    }

    private static d[] b(DataInputStream dataInputStream, d d2) {
        try {
            int n2 = dataInputStream.readShort();
            if (n2 <= 0) {
                return null;
            }
            d[] dArray = new d[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                dArray[i2] = main.d.a(dataInputStream, d2);
            }
            return dArray;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return null;
        }
    }

    private static d[] a(byte[] byArray, d.b b2, short s2) {
        if (byArray == null || byArray.length == 0 || b2 == null) {
            return null;
        }
        Vector<d> vector = new Vector<d>();
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
            d d2 = main.d.a(b2.f, by, s3, by, s2);
            vector.addElement(d2);
        }
        if (vector.isEmpty()) {
            return null;
        }
        Object[] objectArray = new d[vector.size()];
        vector.copyInto(objectArray);
        return objectArray;
    }
}

