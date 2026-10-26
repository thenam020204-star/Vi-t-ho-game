/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package g;

import b.j;
import d.g;
import g.b;
import h.a;
import javax.microedition.lcdui.Graphics;

final class f
extends j {
    private final b a;

    public f(b b2) {
        super((byte)11);
        this.a = b2;
    }

    public final void e() {
    }

    public final void b() {
        this.d(-1);
    }

    private int a(int n2, g g2) {
        if (g2 == null) {
            return 0;
        }
        int n3 = 0;
        switch (n2) {
            case 0: {
                n3 = -g2.o + 5 + b.a(this.a).getHeight();
                break;
            }
            case 1: {
                n3 = -g2.o / 2 + b.a(this.a).getHeight();
            }
        }
        return n3;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        n2 = 0;
        n3 = 0;
        switch (b.b(this.a)) {
            case 1: {
                if (b.c(this.a) == 1) {
                    n2 = b.i()[b.d(this.a)][0] + b.e((b)this.a)[b.d((b)this.a)].n / 2;
                    n3 = b.i()[b.d(this.a)][1] + this.a(b.d(this.a), b.e(this.a)[b.d(this.a)]);
                    graphics.drawImage(b.a(this.a), n2, n3, 36);
                    h.a.a(graphics, 0, b.e((b)this.a)[b.d((b)this.a)].B, 0, 0, 20);
                    return;
                }
                for (int i2 = 0; i2 < b.e(this.a).length; ++i2) {
                    if (b.e(this.a)[i2] == null || b.e(this.a)[i2].z()) continue;
                    n2 = b.i()[i2][0] + b.e((b)this.a)[i2].n / 2;
                    n3 = b.i()[i2][1] + this.a(i2, b.e(this.a)[i2]);
                    graphics.drawImage(b.a(this.a), n2, n3, 36);
                }
                return;
            }
            case 0: {
                if (b.c(this.a) == 1) {
                    n2 = b.j()[b.d(this.a)][0] - 20;
                    n3 = b.j()[b.d(this.a)][1] + this.a(b.d(this.a), b.f(this.a)[b.d(this.a)]);
                    h.b.a(graphics, b.a(this.a), 0, 0, b.a(this.a).getWidth(), b.a(this.a).getHeight(), 2, n2, n3, 40);
                    return;
                }
                for (int i3 = 0; i3 < b.f(this.a).length; ++i3) {
                    if (b.f(this.a)[i3] == null) continue;
                    n2 = b.j()[i3][0] - 20;
                    n3 = b.j()[i3][1] + this.a(i3, b.f(this.a)[i3]);
                    h.b.a(graphics, b.a(this.a), 0, 0, b.a(this.a).getWidth(), b.a(this.a).getHeight(), 2, n2, n3, 40);
                }
                break;
            }
        }
    }

    public final String d() {
        return null;
    }

    public final void a(int n2, int n3) {
        n3 = b.b(this.a) == 0 && b.g(this.a) == 3 && b.h(this.a) != null && b.h(this.a).a() ? 1 : 0;
        g[] gArray = b.b(this.a) == 1 ? b.e(this.a) : b.f(this.a);
        switch (n2) {
            case -7: {
                this.o();
                return;
            }
            case -6: 
            case 53: {
                this.n();
                return;
            }
            case 50: {
                n2 = b.a(this.a, b.d(this.a), gArray, n3 != 0);
                if (n2 == -1) break;
                b.a(this.a, n2);
                return;
            }
            case 56: {
                n2 = b.b(this.a, b.d(this.a), gArray, n3 != 0);
                if (n2 == -1) break;
                b.a(this.a, n2);
            }
        }
    }

    public final void a() {
    }
}

