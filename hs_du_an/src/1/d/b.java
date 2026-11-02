/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package d;

import b.j;
import d.a;
import f.d;
import javax.microedition.lcdui.Graphics;

public abstract class b
extends j {
    protected boolean a = true;
    public String b = null;
    public d c = null;
    public f.a d = null;
    public int e = 0;
    public short f = (short)-1;
    public byte g = 1;
    private byte r = (byte)4;
    private byte s = (byte)2;
    private int t = 0;
    private int u = 0;
    public boolean h = false;

    public b(byte by) {
        super(by);
        this.n = 20;
        this.o = 50;
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.d == null) {
            return;
        }
        if (n2 <= -this.n / 2 || n2 >= main.b.c + this.n / 2 || n3 <= 0 || n3 >= main.b.d + 50) {
            return;
        }
        this.d.a(graphics, n2, n3);
    }

    public final void a(e.a a2) {
        this.c = d.a.a(this.b, null);
    }

    public final void b(int n2, int n3) {
        if (this.c == null) {
            return;
        }
        f.a a2 = this.c.b(n2);
        if (a2 == null) {
            this.d = a2;
            return;
        }
        this.e = n2;
        this.d = a2;
        this.d.a();
        this.d.b(n3);
        this.d.a(this.h);
    }

    public final void c() {
        if (this.d != null) {
            this.d.b();
        }
    }

    public final boolean f() {
        if (this.d == null) {
            return true;
        }
        return this.d.h();
    }

    public final void a(byte by) {
        this.a(by, this.r);
    }

    public final void a(byte by, short s2) {
        switch (by) {
            case 2: {
                this.c(this.m + s2);
                return;
            }
            case 3: {
                this.c(this.m - s2);
                return;
            }
            case 1: {
                this.b(this.l - s2);
                return;
            }
            case 0: {
                this.b(this.l + s2);
            }
        }
    }

    public final int g() {
        if (this.d == null) {
            return -1;
        }
        return this.d.e();
    }

    public final void a(byte by, boolean bl, boolean bl2) {
        if (this.c == null) {
            return;
        }
        int n2 = 0;
        switch (by) {
            case 1: {
                n2 = 1;
                break;
            }
            case 0: {
                n2 = 0;
                break;
            }
            case 3: {
                n2 = 3;
                break;
            }
            case 2: {
                n2 = 2;
                break;
            }
            case 4: {
                return;
            }
            default: {
                return;
            }
        }
        if (!bl) {
            n2 = (byte)(n2 + 4);
        }
        if (this.e != n2 || bl2) {
            this.b(n2, -1);
        }
    }

    public final void b(byte by) {
        if (by >= 0 && by <= 4) {
            this.s = by;
        }
    }

    public static byte a(int n2) {
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

    public final byte h() {
        return this.s;
    }

    private void q() {
        this.t = this.l / 16;
        this.u = this.m / 16;
    }

    public final void b(int n2) {
        super.b(n2);
        this.q();
    }

    public final void c(int n2) {
        super.c(n2);
        this.q();
    }

    public final int i() {
        return this.t;
    }

    public final int j() {
        return this.u;
    }

    public final boolean k() {
        return this.a;
    }

    public final void a(boolean bl) {
        this.a = bl;
    }
}

