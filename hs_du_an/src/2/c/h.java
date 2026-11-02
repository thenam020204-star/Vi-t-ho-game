/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package c;

import b.a;
import c.b;
import c.e;
import c.j;
import javax.microedition.lcdui.Graphics;

public final class h
extends e {
    private static String[] a = new String[]{b.a.p[55], b.a.p[56]};
    private String[] g = b.a.m;

    public final void a(Graphics graphics, int n2, int n3) {
        this.d(graphics, n2, n3, this.i);
        if (this.f) {
            e.a.b.a(graphics, b.a.O, this.b, this.e, this.c, n2 + this.i - 6, n3 + b.a.O.getHeight(), this.j - 51 - (b.a.O.getHeight() << 1));
        }
    }

    protected final void a(Graphics graphics, int n2, int n3, int n4) {
        if (n2 < 0 || n2 > this.g.length) {
            return;
        }
        if (n2 < this.g.length) {
            graphics.drawString(this.g[n2], n3, n4, 20);
            return;
        }
        graphics.drawString(b.a.p[55], n3, n4, 20);
    }

    protected final void b(Graphics graphics, int n2, int n3, int n4) {
        if (n2 < 0 || n2 > this.g.length) {
            return;
        }
        if (n2 < this.g.length) {
            graphics.drawString(b.a.n[n2] ? b.a.p[57] : b.a.p[58], n3, n4, 24);
        }
    }

    protected final String a() {
        return null;
    }

    protected final void c() {
        switch (((e)object).d) {
            case 0: {
                boolean bl = b.a.n[0] = !b.a.n[0];
                if (b.a.n[0] && b.a.an == -1) {
                    b.a.c(b.a.am, -1);
                    return;
                }
                b.a.j();
                return;
            }
            case 1: 
            case 2: {
                b.a.n[((e)object).d] = !b.a.n[((e)object).d];
                return;
            }
            case 3: {
                Object object = c.b.a(a, (byte)0, (byte)2, true);
                int n2 = ((j)object).f();
                if (n2 != 1 || !((String)(object = ((b)object).d())).equals(b.a.p[55])) break;
                main.b.a.i.a(new d.e());
            }
        }
    }

    protected final void d() {
        this.k.b();
    }

    public final void e() {
        if (this.g == null) {
            this.b(1);
            return;
        }
        this.b(this.g.length + 1);
    }

    public final void a(int n2) {
        super.a(n2);
        this.c = n2 / e.a.b.b;
    }
}

