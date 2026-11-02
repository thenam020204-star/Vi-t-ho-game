/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b;

import b.c;
import b.f;
import b.j;
import g.d;
import javax.microedition.lcdui.Graphics;
import main.a;
import main.b;

public final class h
extends f
implements a {
    private static String[] a = new String[]{d.a.q[55], d.a.q[56]};
    private String[] g = new String[]{d.a.q[59]};

    public final void a(Graphics graphics, int n2, int n3) {
        this.d(graphics, n2, n3, this.i);
        if (this.f) {
            h.b.a(graphics, this.b, this.e, this.c, n2 + this.i - 6, n3 + d.a.P.getHeight(), this.j - 51 - (d.a.P.getHeight() << 1));
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
        graphics.drawString(d.a.q[55], n3, n4, 20);
    }

    protected final void b(Graphics graphics, int n2, int n3, int n4) {
        if (n2 < 0 || n2 > this.g.length) {
            return;
        }
        if (n2 < this.g.length) {
            graphics.drawString(d.a.o[n2] ? d.a.q[57] : d.a.q[58], n3, n4, 24);
        }
    }

    protected final String a() {
        return null;
    }

    protected final void c() {
        switch (this.d) {
            case 0: {
                boolean bl = d.a.o[0] = !d.a.o[0];
                if (d.a.o[0] && d.a.ag == -1) {
                    d.a.c(d.a.af, -1);
                    return;
                }
                d.a.j();
                return;
            }
            case 1: {
                c c2 = b.c.a(a, null, (byte)0, (byte)2);
                c2.a(this);
                main.b.a.i.e(c2);
            }
        }
    }

    protected final void d() {
        this.k.b();
    }

    public final void e() {
        this.b(2);
    }

    public final void a(j j2) {
        String string = j2.d();
        if (string.equals(d.a.q[55])) {
            main.b.a.i.a(new d());
            return;
        }
        j2.b();
    }

    public final void d(j j2) {
        main.b.a.i.f(j2);
    }

    public final void b(j j2) {
    }

    public final void c(j j2) {
        j2.b();
    }

    public final void a(int n2) {
        super.a(n2);
        this.c = n2 / h.b.b;
    }
}

