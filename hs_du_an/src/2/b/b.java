/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b;

import javax.microedition.lcdui.Graphics;
import main.c;

public abstract class b {
    public boolean a = true;
    public String b = null;
    public e.b.c c = null;
    public e.b.b d = null;
    public int e = 0;
    public short f = (short)-1;
    public byte g = 1;
    private byte o = (byte)4;
    public int h = -1;
    public byte i = (byte)2;
    public int j = 0;
    public int k = 0;
    public int l = 0;
    public int m = 0;
    public boolean n = false;
    private static byte[] p = new byte[]{3, 0, 2, 1};

    public b(byte by) {
        this.h = by;
        this.l = 20;
        this.m = 50;
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.d == null) {
            return;
        }
        this.b();
        if (n2 <= -this.l / 2 || n2 >= main.b.d + this.l / 2 || n3 <= 0 || n3 >= main.b.e + 50) {
            return;
        }
        this.d.a(graphics, n2, n3);
    }

    public final void a() {
        this.c = e.b.c.b(this.b);
    }

    public final void a(int n2, int n3) {
        if (this.c == null) {
            return;
        }
        e.b.b b2 = this.c.b(n2);
        if (b2 == null) {
            this.d = b2;
            return;
        }
        this.e = n2;
        this.d = b2;
        this.d.a();
        this.d.b(n3);
        this.d.a(this.n);
    }

    public final void b() {
        if (this.d != null) {
            this.d.b();
        }
    }

    public final boolean c() {
        if (this.d == null) {
            return true;
        }
        return this.d.h();
    }

    public final void a(byte by) {
        this.a(by, this.o);
    }

    public final void a(byte by, short s2) {
        switch (by) {
            case 2: {
                this.k += s2;
                return;
            }
            case 3: {
                this.k -= s2;
                return;
            }
            case 1: {
                this.j -= s2;
                return;
            }
            case 0: {
                this.j += s2;
            }
        }
    }

    public final int d() {
        if (this.d == null) {
            return -1;
        }
        return this.d.d();
    }

    public final void a(int n2) {
        if (this.d == null) {
            return;
        }
        this.d.a(n2);
    }

    public final int e() {
        if (this.d == null) {
            return -1;
        }
        return this.d.e();
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
        if (this.c == null) {
            return;
        }
        int n2 = b.b.a((int)by, bl);
        by = (byte)n2;
        if (n2 < 0) {
            return;
        }
        if (this.e != by || bl2) {
            this.a((int)by, -1);
        }
    }

    public final void b(byte by) {
        if (by >= 0 && by <= 4) {
            this.i = by;
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
        return this.j / 16;
    }

    public final int g() {
        return this.k / 16;
    }

    public final void h() {
        this.c = null;
        this.d = null;
    }

    public static byte a(byte by, byte by2) {
        int n2;
        for (n2 = 0; n2 < p.length && by != p[n2]; ++n2) {
        }
        if (n2 >= p.length) {
            return 4;
        }
        if (by2 == main.c.i) {
            n2 = (n2 + 1) % p.length;
            return p[n2];
        }
        if (by2 == main.c.j) {
            n2 = (n2 - 1 + p.length) % p.length;
            return p[n2];
        }
        return 4;
    }
}

