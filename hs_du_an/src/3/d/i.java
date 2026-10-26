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
    private int h;
    private g[] i;
    private byte r;
    private byte s;
    private boolean t;
    private final d u;

    public i(d d2, g[] gArray, byte by, byte by2, boolean bl) {
        super((byte)5);
        this.u = d2;
        this.h = 0;
        this.i = null;
        this.r = 1;
        this.s = 1;
        this.t = false;
        this.i = gArray;
        this.r = by;
        this.s = by2;
        this.t = bl;
        for (int i2 = 0; i2 < gArray.length; ++i2) {
            if (gArray[i2] == null || !bl && gArray[i2].w()) continue;
            this.h = i2;
            return;
        }
    }

    public final g j() {
        if (this.i == null || this.h < 0 || this.h >= this.i.length) {
            return null;
        }
        return this.i[this.h];
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
                this.a = b2.s + b2.u / 2;
                this.b = b2.t - b2.v / 2 - a.f.V.getHeight() / 2;
                graphics.drawImage(a.f.V, this.a, this.b, 20);
                return;
            }
            case 0: {
                this.a = b2.s;
                this.b = b2.t - b2.v / 2 - a.f.V.getHeight() / 2;
                b.e.a.a(graphics, a.f.V, 0, 0, a.f.V.getWidth(), a.f.V.getHeight(), 2, this.a, this.b, 24);
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        switch (this.s) {
            case 0: {
                boolean bl = false;
                n3 = this.r;
                g[] gArray = this.i;
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
                this.a(graphics, this.i[this.h], this.r);
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
                n2 = d.d.a(this.u, this.h, this.i, this.t);
                if (n2 == -1) break;
                this.h = n2;
                return;
            }
            case 56: {
                n2 = d.d.b(this.u, this.h, this.i, this.t);
                if (n2 == -1) break;
                this.h = n2;
            }
        }
    }

    public final void c() {
        super.c();
        this.a(true);
    }
}

