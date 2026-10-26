/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package b;

import b.c;
import b.d;
import b.f;
import b.j;
import b.s;
import b.t;
import d.g;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.a;
import main.b;

public final class n
extends f
implements a {
    private static String[] a = new String[]{d.a.q[0], d.a.q[1], d.a.q[2]};
    private g g = null;
    private c.d[] h = null;
    private s l = null;

    public n(g g2) {
        this.g = g2;
    }

    protected final void a(Graphics graphics, int n2, int n3, int n4) {
        if (n2 < 0 || n2 >= d.g.w.length) {
            return;
        }
        graphics.drawString(d.g.w[n2], n3, n4, 20);
    }

    protected final void b(Graphics graphics, int n2, int n3, int n4) {
        if (n2 < 0 || n2 >= d.g.w.length) {
            return;
        }
        if (this.h == null || this.h[n2] == null) {
            graphics.drawString(d.a.q[3], n3, n4, 24);
            return;
        }
        graphics.drawString(this.h[n2].r, n3, n4, 24);
    }

    protected final String a() {
        if (this.h == null || this.d < 0 || this.d >= this.h.length || this.h[this.d] == null) {
            return null;
        }
        return this.h[this.d].s;
    }

    protected final void c() {
        c c2 = b.c.a(a, null, (byte)0, (byte)2);
        c2.a(this);
        main.b.a.i.e(c2);
    }

    protected final void d() {
        this.k.b();
    }

    public final void e() {
        this.g();
        this.f();
        this.b(d.g.w.length);
    }

    public final void a(j j2) {
        Object object = j2.d();
        if (((String)object).equals(d.a.q[0])) {
            object = imageArray2;
            Image[] imageArray = new Image[1];
            Image[] imageArray2 = imageArray;
            imageArray[0] = d.a.T[4];
            byte by = super.b();
            t[] tArray = new d[1];
            t[] tArray2 = tArray;
            tArray[0] = new d(((n)object).g, by);
            ((n)object).l = new s(imageArray2, tArray2, main.b.c, main.b.d, null);
            ((n)object).l.a((a)object);
            ((n)object).l.a(((t)object).k.d);
            main.b.a.i.e(((n)object).l);
        } else if (((String)object).equals(d.a.q[1])) {
            byte by = super.b();
            imageArray2.g.c(-1, (int)by);
            super.g();
            imageArray2.f();
        }
        j2.b();
    }

    private byte b() {
        return d.g.v[this.d];
    }

    public final void d(j j2) {
        if (j2 == this.l) {
            this.g();
            this.f();
        }
        main.b.a.i.f(j2);
    }

    public final void b(j j2) {
    }

    public final void c(j j2) {
        j2.b();
    }

    private void g() {
        this.h = this.g.F();
    }
}

