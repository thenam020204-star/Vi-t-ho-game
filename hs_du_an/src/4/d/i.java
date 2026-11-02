/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package d;

import a.b;
import a.f;
import a.g;
import b.b.m;
import b.e.a;
import d.d;
import javax.microedition.lcdui.Graphics;

final class i
extends m {
    private int a;
    private g[] b;
    private byte j;
    private byte k;
    private boolean t;
    private final d u;

    public i(d d2, g[] gArray, byte by, byte by2, boolean bl) {
        super((byte)5);
        this.u = d2;
        this.a = 0;
        this.b = null;
        this.j = 1;
        this.k = 1;
        this.t = false;
        this.b = gArray;
        this.j = by;
        this.k = by2;
        this.t = bl;
        for (int i2 = 0; i2 < gArray.length; ++i2) {
            if (gArray[i2] == null || !bl && gArray[i2].w()) continue;
            this.a = i2;
            return;
        }
    }

    public final g j() {
        if (this.b == null || this.a < 0 || this.a >= this.b.length) {
            return null;
        }
        return this.b[this.a];
    }

    protected final void b() {
        d.d.a(this.u);
    }

    private void a(Graphics graphics, b b2, byte by) {
        if (b2 == null) {
            return;
        }
        switch (by) {
            case 1: {
                this.c = b2.s + b2.u / 2;
                this.d = b2.t - b2.v / 2 - a.f.V.getHeight() / 2;
                graphics.drawImage(a.f.V, this.c, this.d, 20);
                return;
            }
            case 0: {
                this.c = b2.s;
                this.d = b2.t - b2.v / 2 - a.f.V.getHeight() / 2;
                b.e.a.a(graphics, a.f.V, 0, 0, a.f.V.getWidth(), a.f.V.getHeight(), 2, this.c, this.d, 24);
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        switch (this.k) {
            case 0: {
                boolean bl = false;
                n3 = this.j;
                g[] gArray = this.b;
                i i2 = this;
                if (gArray != null) {
                    for (int i3 = 0; i3 < gArray.length; ++i3) {
                        if (gArray[i3] == null || gArray[i3].w()) continue;
                        i2.a(graphics, gArray[i3], (byte)n3);
                    }
                }
                return;
            }
            case 1: {
                this.a(graphics, this.b[this.a], this.j);
            }
        }
    }

    public final String a() {
        return null;
    }

    public final void a(int n2, int n3) {
        switch (n2) {
            case -7: {
                this.b(2);
                this.c();
                return;
            }
            case -6: 
            case 53: {
                this.b(1);
                this.c();
                return;
            }
            case 50: {
                n2 = d.d.a(this.u, this.a, this.b, this.t);
                if (n2 == -1) break;
                this.a = n2;
                return;
            }
            case 56: {
                n2 = d.d.b(this.u, this.a, this.b, this.t);
                if (n2 == -1) break;
                this.a = n2;
            }
        }
    }

    public final void c() {
        super.c();
        this.a(true);
    }
}

