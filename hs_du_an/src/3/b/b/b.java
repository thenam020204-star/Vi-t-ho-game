/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b.b;

import b.b.d;
import b.e.a;
import javax.microedition.lcdui.Graphics;

public final class b
extends d {
    public static int a = 40;
    private int f = 0;
    private String g = null;
    private int h = 0;

    public b() {
        super(3);
    }

    public final void a(String string) {
        this.g = string;
        this.h = b.e.a.a(string, this.d);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.g == null) {
            return;
        }
        int[] nArray = b.e.a.a(graphics);
        graphics.clipRect(n2, n3, this.d, this.e);
        b.e.a.a(graphics, this.g, this.d, n2, n3 + this.f, 0xFFFFFF);
        b.e.a.a(graphics, nArray);
    }

    public final void a() {
        if (this.g == null) {
            return;
        }
        this.f -= 4;
        if (this.f < -b.e.a.b * this.h) {
            this.f = this.e;
        }
    }

    public final void b() {
        if (this.g == null) {
            return;
        }
        --this.f;
        if (this.f < -b.e.a.b * this.h) {
            this.f = this.e;
        }
    }

    public final void c() {
        if (this.g == null) {
            return;
        }
        this.f += 4;
        if (this.f > this.e + b.e.a.b * this.h) {
            this.f = -b.e.a.b * this.h;
        }
    }

    public final void d() {
        this.f = 0;
    }
}

