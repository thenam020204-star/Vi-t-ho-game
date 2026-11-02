/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package a;

import b.a.c;
import b.a.d;
import javax.microedition.lcdui.Graphics;
import main.a;

public abstract class b {
    public boolean f = true;
    public boolean g = false;
    public boolean h = true;
    public String i = null;
    public c j = null;
    public d k = null;
    public int l = 0;
    public int m = 0;
    public short n = (short)-1;
    public byte o = 1;
    private byte a = (byte)4;
    public String p = "";
    public int q = -1;
    public byte r = (byte)2;
    public int s = 0;
    public int t = 0;
    public int u = 0;
    public int v = 0;
    public boolean w = false;
    public boolean x = false;
    public b y = null;
    public b z = null;
    public boolean A = false;
    private static byte[] b = new byte[]{3, 0, 2, 1};

    public b(byte by) {
        this.q = by;
        this.u = 20;
        this.v = 50;
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.k == null) {
            return;
        }
        if (n2 <= -this.u / 2 || n2 >= main.a.d + this.u / 2 || n3 <= 0 || n3 >= main.a.e + 50) {
            return;
        }
        this.k.a(graphics, n2, n3);
    }

    public final void i() {
        this.j = c.b(this.i);
    }

    public final void a(int n2, int n3) {
        if (this.j == null) {
            return;
        }
        d d2 = this.j.b(n2);
        if (d2 == null) {
            this.k = d2;
            return;
        }
        this.l = n2;
        this.k = d2;
        this.k.a();
        this.k.b(n3);
        this.k.a(this.w);
    }

    public final void j() {
        if (this.k != null) {
            this.k.b();
        }
    }

    public final boolean k() {
        if (this.k == null) {
            return true;
        }
        return this.k.h();
    }

    public final void c(byte by) {
        this.a(by, this.a);
    }

    public final void a(byte by, short s) {
        switch (by) {
            case 2: {
                this.t += s;
                return;
            }
            case 3: {
                this.t -= s;
                return;
            }
            case 1: {
                this.s -= s;
                return;
            }
            case 0: {
                this.s += s;
            }
        }
    }

    public final int l() {
        if (this.k == null) {
            return -1;
        }
        return this.k.d();
    }

    public final void a(int n2) {
        if (this.k == null) {
            return;
        }
        this.k.a(n2);
    }

    public final int m() {
        if (this.k == null) {
            return -1;
        }
        return this.k.e();
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
        if (this.j == null) {
            return;
        }
        int n2 = a.b.a((int)by, bl);
        by = (byte)n2;
        if (n2 < 0) {
            return;
        }
        if (this.l != by || bl2) {
            this.a((int)by, -1);
        }
    }

    public final void d(byte by) {
        if (by >= 0 && by <= 4) {
            this.r = by;
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

    public final int n() {
        return this.s / 16;
    }

    public final int o() {
        return this.t / 16;
    }

    public final void p() {
        this.j = null;
        this.k = null;
    }

    public static byte a(byte by, byte by2) {
        int n2;
        for (n2 = 0; n2 < b.length && by != b[n2]; ++n2) {
        }
        if (n2 >= b.length) {
            return 4;
        }
        if (by2 == b.f.b.a) {
            n2 = (n2 + 1) % b.length;
            return b[n2];
        }
        if (by2 == b.f.b.b) {
            n2 = (n2 - 1 + b.length) % b.length;
            return b[n2];
        }
        return 4;
    }

    public static b a(b b2, b b3) {
        if (b2 != null && b3 != null && b2 == b3) {
            if (b2.z != null && b2.z.t < b2.t) {
                return a.b.a(b2.z, b2);
            }
            return b2;
        }
        if (b3 == null) {
            return b2;
        }
        b b4 = b3;
        if (b4.y != null) {
            b4.y.z = b4.z;
        }
        if (b4.z != null) {
            b4.z.y = b4.y;
        }
        b4.z = null;
        b4.y = null;
        if (b2 == null) {
            return b3;
        }
        if (b3.t <= b2.t && b3 != b2) {
            b2.y = b3;
            b3.z = b2;
            return b3;
        }
        b4 = b2;
        while (b4.z != null) {
            if (b3.t <= b4.z.t) {
                b3.z = b4.z;
                b4.z.y = b3;
                b4.z = b3;
                b3.y = b4;
                return b2;
            }
            b4 = b4.z;
        }
        b3.y = b4;
        b4.z = b3;
        return b2;
    }
}

