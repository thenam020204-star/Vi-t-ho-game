/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package g;

import b.f;
import c.a;
import d.g;
import g.b;
import javax.microedition.lcdui.Graphics;

final class c
extends f {
    private a[] a;
    private g g;
    private final b h;

    public c(b b2, g g2) {
        this.h = b2;
        this.a = null;
        this.g = null;
        this.g = g2;
    }

    protected final void a(Graphics graphics, int n2, int n3, int n4) {
        if (this.a == null || n2 < 0 || n2 >= this.a.length || this.a[n2] == null) {
            return;
        }
        graphics.drawString(this.a[n2].r, n3, n4, 20);
    }

    protected final void b(Graphics graphics, int n2, int n3, int n4) {
    }

    protected final String a() {
        if (this.a == null || this.d < 0 || this.d >= this.a.length || this.a[this.d] == null) {
            return null;
        }
        return this.a[this.d].s;
    }

    protected final void c() {
        Object object = this;
        object = ((c)object).a == null || ((f)object).d < 0 || ((f)object).d >= ((c)object).a.length ? null : ((c)object).a[((f)object).d];
        if (object == null) {
            g.b.i(this.h);
            this.k.b();
            return;
        }
        object = (a)object;
        g.b.c(this.h, (byte)2);
        g.b.a(this.h, (a)object);
        g.b.a(this.h, (byte)1);
        g.b.b(this.h, ((c.f)object).u);
        g.b.l(this.h);
        if (g.b.d(this.h) < 0 || g.b.d(this.h) >= g.b.e(this.h).length || g.b.e(this.h)[g.b.d(this.h)] == null || g.b.e(this.h)[g.b.d(this.h)].z()) {
            g.b.a(this.h, g.b.b(this.h, g.b.d(this.h), g.b.e(this.h), false));
        }
        this.k.b();
    }

    protected final void d() {
        g.b.i(this.h);
        this.k.b();
    }

    public final void e() {
        this.a = this.g.I();
        this.b(this.a == null ? 0 : this.a.length);
        this.f();
    }
}

