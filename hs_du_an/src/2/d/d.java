/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package d;

import b.f;
import c.e;
import d.c;
import f.b;
import javax.microedition.lcdui.Graphics;

final class d
extends e {
    private f.c[] a;
    private f g;
    private final c h;

    public d(c c2, f f2) {
        this.h = c2;
        this.a = null;
        this.g = null;
        this.g = f2;
    }

    protected final void a(Graphics graphics, int n2, int n3, int n4) {
        if (this.a == null || n2 < 0 || n2 >= this.a.length || this.a[n2] == null) {
            return;
        }
        graphics.drawString(this.a[n2].t, n3, n4, 20);
    }

    protected final void b(Graphics graphics, int n2, int n3, int n4) {
    }

    protected final String a() {
        if (this.a == null || this.d < 0 || this.d >= this.a.length || this.a[this.d] == null) {
            return null;
        }
        return this.a[this.d].d();
    }

    protected final void c() {
        Object object = this;
        object = ((d)object).a == null || ((e)object).d < 0 || ((e)object).d >= ((d)object).a.length ? null : ((d)object).a[((e)object).d];
        if (object == null) {
            d.c.h(this.h);
            this.k.b();
            return;
        }
        object = (f.c)object;
        d.c.c(this.h, (byte)2);
        d.c.b(this.h, (f.c)object);
        d.c.a(this.h, (byte)1);
        d.c.b(this.h, ((b)object).v);
        d.c.j(this.h);
        if (d.c.d(this.h) < 0 || d.c.d(this.h) >= this.h.k.length || this.h.k[d.c.d(this.h)] == null || this.h.k[d.c.d(this.h)].w()) {
            d.c.a(this.h, d.c.b(this.h, d.c.d(this.h), this.h.k, false));
        }
        this.k.b();
    }

    protected final void d() {
        d.c.h(this.h);
        this.k.b();
    }

    public final void e() {
        this.a = this.g.A();
        this.b(this.a == null ? 0 : this.a.length);
        this.f();
    }
}

