/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b.b;

import a.c;
import b.b.d;
import b.e.a;
import javax.microedition.lcdui.Graphics;

public final class o
extends d {
    private c[] a = null;
    private int f = 0;
    private int g = 0;

    public o(c[] cArray) {
        super(6);
        this.a = cArray;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.a == null) {
            return;
        }
        int n4 = this.e / a.c.b;
        if (n4 == 0) {
            n4 = 1;
        }
        boolean bl = (n4 = Math.min(2, n4)) < this.a.length;
        int n5 = 0;
        int n6 = 0;
        for (int i2 = 0; i2 < n4; ++i2) {
            n5 = n3 + i2 * a.c.b;
            if (i2 + this.f == this.g) {
                b.e.c.a(graphics, 5848857, 15966997, n2, n5, this.d - 1, a.c.b / 2);
                b.e.c.a(graphics, 15966997, 3681308, n2, n5 + a.c.b / 2, this.d - 1, a.c.b / 2);
                graphics.setColor(16120515);
            } else {
                graphics.setColor(11971479);
            }
            n6 = i2 + this.f;
            if (this.a[n6] == null) continue;
            this.a[n6].a(graphics, n2, n5, this.d);
        }
        if (bl) {
            b.e.a.a(graphics, this.a.length, this.f, n4, n2 + this.d - 6, n3, this.e);
        }
    }

    public final void a() {
        if (this.a == null) {
            return;
        }
        int n2 = this.a.length;
        if (n2 > 0) {
            this.g = (this.g - 1 + n2) % n2;
            this.d();
        }
    }

    public final void b() {
        if (this.a == null) {
            return;
        }
        int n2 = this.a.length;
        if (n2 > 0) {
            this.g = (this.g + 1) % n2;
            this.d();
        }
    }

    private void d() {
        int n2 = this.e / a.c.b;
        if (this.g < this.f) {
            this.f = this.g;
            return;
        }
        if (this.g >= this.f + n2 - 1) {
            this.f = this.g - n2 + 1;
        }
    }

    public final int c() {
        return this.g;
    }

    public final void a(c[] cArray) {
        this.a = cArray;
    }
}

