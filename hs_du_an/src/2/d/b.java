/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package d;

import b.a;
import b.f;
import c.e;
import c.u;
import d.c;
import javax.microedition.lcdui.Graphics;

final class b
extends e {
    private f.c[] a;
    private f g;
    private final c h;

    public b(c c2, f f2) {
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
        if (this.a == null || n2 < 0 || n2 >= this.a.length || this.a[n2] == null) {
            return;
        }
        graphics.drawString(String.valueOf(this.a[n2].a[1]), n3, n4, 24);
    }

    protected final String a() {
        if (this.a == null || this.d < 0 || this.d >= this.a.length || this.a[this.d] == null) {
            return null;
        }
        return this.a[this.d].d();
    }

    protected final void c() {
        Object object = b2;
        object = ((b)object).a == null || ((e)object).d < 0 || ((e)object).d >= ((b)object).a.length ? null : ((b)object).a[((e)object).d];
        if (object == null) {
            d.c.h(((b)((Object)b2)).h);
            ((u)((Object)b2)).k.b();
            return;
        }
        object = (f.c)object;
        if (Math.abs(((f.c)object).a[1]) > d.c.i(((b)((Object)b2)).h).c(6)) {
            c.b b2 = c.b.a(b.a.p[66], 3000L);
            b2.f();
            return;
        }
        d.c.c(((b)((Object)b2)).h, (byte)5);
        d.c.a(((b)((Object)b2)).h, (f.c)object);
        d.c.a(((b)((Object)b2)).h, ((f.b)object).w);
        d.c.b(((b)((Object)b2)).h, ((f.b)object).v);
        d.c.j(((b)((Object)b2)).h);
        if (d.c.b(((b)((Object)b2)).h) == 1 && (d.c.d(((b)((Object)b2)).h) < 0 || d.c.d(((b)((Object)b2)).h) >= ((b)((Object)b2)).h.k.length || ((b)((Object)b2)).h.k[d.c.d(((b)((Object)b2)).h)] == null || ((b)((Object)b2)).h.k[d.c.d(((b)((Object)b2)).h)].w())) {
            d.c.a(((b)((Object)b2)).h, d.c.a(((b)((Object)b2)).h, d.c.d(((b)((Object)b2)).h), ((b)((Object)b2)).h.k, d.c.k(((b)((Object)b2)).h).a()));
        } else if (d.c.b(((b)((Object)b2)).h) == 0 && (d.c.d(((b)((Object)b2)).h) < 0 || d.c.d(((b)((Object)b2)).h) >= d.c.e(((b)((Object)b2)).h).length || d.c.e(((b)((Object)b2)).h)[d.c.d(((b)((Object)b2)).h)] == null || d.c.e(((b)((Object)b2)).h)[d.c.d(((b)((Object)b2)).h)].w())) {
            d.c.a(((b)((Object)b2)).h, d.c.b(((b)((Object)b2)).h, d.c.d(((b)((Object)b2)).h), d.c.e(((b)((Object)b2)).h), d.c.k(((b)((Object)b2)).h).a()));
        }
        ((u)((Object)b2)).k.b();
    }

    protected final void d() {
        d.c.h(this.h);
        this.k.b();
    }

    public final void e() {
        this.a = this.g.J();
        this.b(this.a == null ? 0 : this.a.length);
        this.f();
    }
}

