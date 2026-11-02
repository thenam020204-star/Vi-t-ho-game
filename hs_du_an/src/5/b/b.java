/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b;

import a.e.c;
import javax.microedition.lcdui.Graphics;
import main.a;

public abstract class b {
    public boolean a = true;
    public boolean b = false;
    public boolean c = true;
    public String d = null;
    public c e = null;
    public a.e.b f = null;
    public int g = 0;
    public int h = 0;
    public short i = (short)-1;
    public byte j = 1;
    private byte w = (byte)4;
    public String k = "";
    public int l = -1;
    public byte m = (byte)2;
    public int n = 0;
    public int o = 0;
    public int p = 0;
    public int q = 0;
    public boolean r = false;
    public boolean s = false;
    public b t = null;
    public b u = null;
    public boolean v = false;
    private static byte[] x = new byte[]{3, 0, 2, 1};

    public b(byte by) {
        this.l = by;
        this.p = 20;
        this.q = 50;
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.f == null) {
            return;
        }
        if (n2 <= -this.p / 2 || n2 >= main.a.d + this.p / 2 || n3 <= 0 || n3 >= main.a.e + 50) {
            return;
        }
        this.f.a(graphics, n2, n3);
    }

    public final void a() {
        this.e = a.e.c.b(this.d);
    }

    public final void a(int n2, int n3) {
        if (this.e == null) {
            return;
        }
        a.e.b b2 = this.e.b(n2);
        if (b2 == null) {
            this.f = b2;
            return;
        }
        this.g = n2;
        this.f = b2;
        this.f.a();
        this.f.b(n3);
        this.f.a(this.r);
    }

    public final void b() {
        if (this.f != null) {
            this.f.b();
        }
    }

    public final boolean c() {
        if (this.f == null) {
            return true;
        }
        return this.f.g();
    }

    public final void a(byte by) {
        this.a(by, this.w);
    }

    public final void a(byte by, short s) {
        switch (by) {
            case 2: {
                this.o += s;
                return;
            }
            case 3: {
                this.o -= s;
                return;
            }
            case 1: {
                this.n -= s;
                return;
            }
            case 0: {
                this.n += s;
            }
        }
    }

    public final int d() {
        if (this.f == null) {
            return -1;
        }
        return this.f.d();
    }

    public final void a(int n2) {
        if (this.f == null) {
            return;
        }
        this.f.a(n2);
    }

    public final int e() {
        if (this.f == null) {
            return -1;
        }
        return this.f.e();
    }

    public final void a(byte by, boolean bl) {
        this.a(by, bl, false);
    }

    public static int a(int n2, boolean bl) {
        int n3 = 0;
        switch (n2) {
            case 1: {
                n3 = 1;
                break;
            }
            case 0: {
                n3 = 0;
                break;
            }
            case 3: {
                n3 = 3;
                break;
            }
            case 2: {
                n3 = 2;
                break;
            }
            default: {
                return -1;
            }
        }
        if (!bl) {
            n3 += 4;
        }
        return n3;
    }

    public final void a(byte by, boolean bl, boolean bl2) {
        if (this.e == null) {
            return;
        }
        int n2 = b.b.a((int)by, bl);
        by = (byte)n2;
        if (n2 < 0) {
            return;
        }
        if (this.g != by || bl2) {
            this.a((int)by, -1);
        }
    }

    public final void b(byte by) {
        if (by >= 0 && by <= 4) {
            this.m = by;
        }
    }

    public static byte b(int n2) {
        switch (n2) {
            case 2: {
                return 3;
            }
            case 3: {
                return 2;
            }
            case 1: {
                return 0;
            }
            case 0: {
                return 1;
            }
        }
        return 4;
    }

    public final int f() {
        return this.n / 16;
    }

    public final int g() {
        return this.o / 16;
    }

    public final void h() {
        this.e = null;
        this.f = null;
    }

    public static byte a(byte by, byte by2) {
        int n2;
        for (n2 = 0; n2 < x.length && by != x[n2]; ++n2) {
        }
        if (n2 >= x.length) {
            return 4;
        }
        if (by2 == a.d.b.a) {
            n2 = (n2 + 1) % x.length;
            return x[n2];
        }
        if (by2 == a.d.b.b) {
            n2 = (n2 - 1 + x.length) % x.length;
            return x[n2];
        }
        return 4;
    }

    public static b a(b b2, b b3) {
        if (b2 != null && b3 != null && b2 == b3) {
            if (b2.u != null && b2.u.o < b2.o) {
                return b.b.a(b2.u, b2);
            }
            return b2;
        }
        if (b3 == null) {
            return b2;
        }
        b b4 = b3;
        if (b4.t != null) {
            b4.t.u = b4.u;
        }
        if (b4.u != null) {
            b4.u.t = b4.t;
        }
        b4.u = null;
        b4.t = null;
        if (b2 == null) {
            return b3;
        }
        if (b3.o <= b2.o && b3 != b2) {
            b2.t = b3;
            b3.u = b2;
            return b3;
        }
        b4 = b2;
        while (b4.u != null) {
            if (b3.o <= b4.u.o) {
                b3.u = b4.u;
                b4.u.t = b3;
                b4.u = b3;
                b3.t = b4;
                return b2;
            }
            b4 = b4.u;
        }
        b3.t = b4;
        b4.u = b3;
        return b2;
    }
}

