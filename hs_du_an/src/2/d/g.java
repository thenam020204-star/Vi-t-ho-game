/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package d;

import b.f;
import c.k;
import d.c;
import e.a.a;
import e.a.b;
import javax.microedition.lcdui.Graphics;

final class g
extends k {
    private final c a;

    public g(c c2) {
        super((byte)11);
        this.a = c2;
    }

    public final void e() {
    }

    public final void b() {
        this.h();
    }

    private int a(int n2, f f2) {
        if (f2 == null) {
            return 0;
        }
        int n3 = 0;
        switch (n2) {
            case 0: {
                n3 = -f2.m + 5 + c.a(this.a).getHeight();
                break;
            }
            case 1: {
                n3 = -f2.m / 2 + c.a(this.a).getHeight();
            }
        }
        return n3;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        n2 = 0;
        n3 = 0;
        switch (c.b(this.a)) {
            case 1: {
                if (c.c(this.a) == 1) {
                    n2 = c.j()[c.d(this.a)][0] + this.a.k[c.d((c)this.a)].l / 2;
                    n3 = c.j()[c.d(this.a)][1] + this.a(c.d(this.a), this.a.k[c.d(this.a)]);
                    graphics.drawImage(c.a(this.a), n2, n3, 36);
                    e.a.a.a(graphics, 0, this.a.k[c.d((c)this.a)].z, 0, 0, 20);
                    return;
                }
                for (int i2 = 0; i2 < this.a.k.length; ++i2) {
                    if (this.a.k[i2] == null || this.a.k[i2].w()) continue;
                    n2 = c.j()[i2][0] + this.a.k[i2].l / 2;
                    n3 = c.j()[i2][1] + this.a(i2, this.a.k[i2]);
                    graphics.drawImage(c.a(this.a), n2, n3, 36);
                }
                return;
            }
            case 0: {
                if (c.c(this.a) == 1) {
                    n2 = c.k()[c.d(this.a)][0] - 20;
                    n3 = c.k()[c.d(this.a)][1] + this.a(c.d(this.a), c.e(this.a)[c.d(this.a)]);
                    b.a(graphics, c.a(this.a), 0, 0, c.a(this.a).getWidth(), c.a(this.a).getHeight(), 2, n2, n3, 40);
                    return;
                }
                for (int i3 = 0; i3 < c.e(this.a).length; ++i3) {
                    if (c.e(this.a)[i3] == null) continue;
                    n2 = c.k()[i3][0] - 20;
                    n3 = c.k()[i3][1] + this.a(i3, c.e(this.a)[i3]);
                    b.a(graphics, c.a(this.a), 0, 0, c.a(this.a).getWidth(), c.a(this.a).getHeight(), 2, n2, n3, 40);
                }
                break;
            }
        }
    }

    public final String d() {
        return null;
    }

    public final void a(int n2, int n3) {
        n3 = c.b(this.a) == 0 && c.f(this.a) == 3 && c.g(this.a) != null && c.g(this.a).a() ? 1 : 0;
        f[] fArray = c.b(this.a) == 1 ? this.a.k : c.e(this.a);
        switch (n2) {
            case -7: {
                this.i();
                return;
            }
            case -6: 
            case 53: {
                this.g();
                return;
            }
            case 50: {
                n2 = c.a(this.a, c.d(this.a), fArray, n3 != 0);
                if (n2 == -1) break;
                c.a(this.a, n2);
                return;
            }
            case 56: {
                n2 = c.b(this.a, c.d(this.a), fArray, n3 != 0);
                if (n2 == -1) break;
                c.a(this.a, n2);
            }
        }
    }

    public final void a() {
    }
}

