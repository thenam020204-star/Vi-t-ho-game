/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b;

import b.t;
import d.a;
import h.b;
import javax.microedition.lcdui.Graphics;

public abstract class f
extends t {
    protected int b = 0;
    protected int c = 0;
    protected int d = 0;
    protected int e = 0;
    protected boolean f = false;
    private String a = null;
    private int g = 0;
    private int h = 0;

    protected abstract void a(Graphics var1, int var2, int var3, int var4);

    protected abstract void b(Graphics var1, int var2, int var3, int var4);

    protected abstract void c();

    protected abstract String a();

    protected abstract void d();

    public void a(Graphics object, int n2, int n3) {
        this.d((Graphics)object, n2, n3, this.i);
        if (this.f) {
            h.b.a(object, this.b, this.e, this.c, n2 + this.i - 6, n3 + d.a.P.getHeight(), this.j - 51 - (d.a.P.getHeight() << 1));
        }
        b.f.c(object, n2, n3 + this.j - 51, this.i);
        Graphics graphics = object;
        int n4 = 47;
        int n5 = this.i;
        int n6 = n3 + this.j - 51 + 4;
        n3 = n2;
        n2 = this.h;
        object = this.a;
        Graphics graphics2 = graphics;
        if (object != null) {
            int[] nArray = h.b.a(graphics2);
            graphics2.clipRect(n3, n6, n5, 47);
            h.b.a(graphics2, (String)object, n5, n3, n6 + n2, 0xFFFFFF);
            h.b.a(graphics2, nArray);
        }
    }

    public static void c(Graphics graphics, int n2, int n3, int n4) {
        graphics.setColor(6373665);
        graphics.drawLine(n2, n3, n2 + n4, n3);
        graphics.setColor(5583390);
        graphics.drawLine(n2, n3 + 1, n2 + n4, n3 + 1);
        graphics.setColor(7692354);
        graphics.drawLine(n2, n3 + 2, n2 + n4, n3 + 2);
        graphics.setColor(5916203);
        graphics.drawLine(n2, n3 + 3, n2 + n4, n3 + 3);
    }

    protected final void d(Graphics graphics, int n2, int n3, int n4) {
        if (this.b <= 0) {
            return;
        }
        int n5 = 0;
        for (int i2 = 0; i2 < this.c; ++i2) {
            n5 = n3 + i2 * h.b.b;
            if (i2 + this.e == this.d) {
                h.a.a(graphics, 5848857, 15966997, n2, n5, n4, h.b.b / 2);
                h.a.a(graphics, 15966997, 3681308, n2, n5 + h.b.b / 2, n4, h.b.b / 2);
                graphics.setColor(16120515);
            } else {
                graphics.setColor(11971479);
            }
            this.a(graphics, i2 + this.e, n2, n5);
            this.b(graphics, i2 + this.e, n2 + n4 - (this.f ? 6 : 0), n5);
        }
    }

    public final void a(int n2, int n3) {
        switch (n2) {
            case -7: {
                this.d();
                break;
            }
            case 50: {
                if (this.b <= 0) break;
                this.d = (this.d - 1 + this.b) % this.b;
                this.b();
                break;
            }
            case 56: {
                if (this.b <= 0) break;
                this.d = (this.d + 1) % this.b;
                this.b();
                break;
            }
            case -6: 
            case 53: {
                this.c();
            }
        }
        if (n3 == 51 || n3 == 57 || n3 == 999) {
            n2 = n3;
            switch (n2) {
                case 51: 
                case 999: {
                    this.h -= 4;
                    if (this.h >= -this.g * h.b.b) break;
                    this.h = 51;
                    return;
                }
                case 57: {
                    this.h += 4;
                    if (this.h <= 51) break;
                    this.h = -this.g * h.b.b;
                }
            }
        }
    }

    public void a(int n2) {
        super.a(n2);
        this.c = (n2 - 51) / h.b.b;
    }

    private void b() {
        if (this.d < this.e) {
            this.e = this.d;
        } else if (this.d >= this.e + this.c - 1) {
            this.e = this.d - this.c + 1;
        }
        this.f();
    }

    protected final void f() {
        this.a = this.a();
        if (this.a == null || this.a.trim().length() == 0) {
            this.g = 0;
        }
        this.g = h.b.a(h.b.d(this.a), this.i);
        if (this.g == 1) {
            this.h = 21 - h.b.b / 2;
            return;
        }
        this.h = 0;
    }

    public final void b(int n2) {
        this.b = n2;
        this.f = n2 > this.c;
    }
}

