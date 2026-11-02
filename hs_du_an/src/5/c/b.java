/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package c;

import a.a.m;
import b.a;
import b.f;
import c.c;
import javax.microedition.lcdui.Graphics;

final class b
extends m {
    private int h;
    private f[] i;
    private byte r;
    private byte s;
    private boolean t;
    private final c u;

    public b(c c2, f[] fArray, byte by, byte by2, boolean bl) {
        super((byte)5);
        this.u = c2;
        this.h = 0;
        this.i = null;
        this.r = 1;
        this.s = 1;
        this.t = false;
        this.i = fArray;
        this.r = by;
        this.s = by2;
        this.t = bl;
        for (int i2 = 0; i2 < fArray.length; ++i2) {
            if (fArray[i2] == null || !bl && fArray[i2].w()) continue;
            this.h = i2;
            return;
        }
    }

    public final f h() {
        if (this.i == null || this.h < 0 || this.h >= this.i.length) {
            return null;
        }
        return this.i[this.h];
    }

    protected final void g() {
        c.c.a(this.u);
    }

    private void a(Graphics graphics, b.b b2, byte by) {
        if (b2 == null) {
            return;
        }
        switch (by) {
            case 1: {
                this.a = b2.n + b2.p / 2;
                this.b = b2.o - b2.q / 2 - b.a.V.getHeight() / 2;
                graphics.drawImage(b.a.V, this.a, this.b, 20);
                return;
            }
            case 0: {
                this.a = b2.n;
                this.b = b2.o - b2.q / 2 - b.a.V.getHeight() / 2;
                a.c.b.a(graphics, b.a.V, 0, 0, b.a.V.getWidth(), b.a.V.getHeight(), 2, this.a, this.b, 24);
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        switch (this.s) {
            case 0: {
                boolean bl = false;
                n3 = this.r;
                f[] fArray = this.i;
                b b2 = this;
                if (fArray != null) {
                    for (int i2 = 0; i2 < fArray.length; ++i2) {
                        if (fArray[i2] == null || fArray[i2].w()) continue;
                        b2.a(graphics, fArray[i2], (byte)n3);
                    }
                }
                return;
            }
            case 1: {
                this.a(graphics, this.i[this.h], this.r);
            }
        }
    }

    public final String c() {
        return null;
    }

    public final void a(int n2, int n3) {
        switch (n2) {
            case -7: {
                this.a(2);
                this.b();
                return;
            }
            case -6: 
            case 53: {
                this.a(1);
                this.b();
                return;
            }
            case 50: {
                n2 = c.c.a(this.u, this.h, this.i, this.t);
                if (n2 == -1) break;
                this.h = n2;
                return;
            }
            case 56: {
                n2 = c.c.b(this.u, this.h, this.i, this.t);
                if (n2 == -1) break;
                this.h = n2;
            }
        }
    }

    public final void b() {
        super.b();
        this.a(true);
    }
}

