/*
 * Decompiled with CFR 0.152.
 */
package d;

import c.a;
import c.b;
import d.c;

final class l
extends a {
    private final c g;

    public l(c c2, byte by) {
        super(by, true);
        this.g = c2;
    }

    protected final void d() {
        d.c.h(this.g);
        this.k.b();
    }

    protected final void c() {
        d.c.a(this.g, (f.b)this.b());
        if (d.c.g(this.g) == null) {
            return;
        }
        if (d.c.g((c)this.g).s == 16) {
            short s2 = ((f.a)d.c.g((c)this.g)).n;
            if (s2 > d.c.i(this.g).c(6)) {
                b b2 = c.b.a(b.a.h(66), 2000L);
                b2.f();
                return;
            }
            Object object = new String[]{d.c.e(this.g)[3] == null || d.c.e(this.g)[3].w() ? b.a.h(80) : d.c.e((c)this.g)[3].z, d.c.e(this.g)[4] == null || d.c.e(this.g)[4].w() ? b.a.h(80) : d.c.e((c)this.g)[4].z};
            int n2 = (object = c.b.a(object, (byte)0, (byte)1, true)).f();
            if (n2 == 1) {
                d.c.a(this.g, (b)object);
            }
            return;
        }
        d.c.a(this.g, d.c.g((c)this.g).w);
        d.c.b(this.g, d.c.g((c)this.g).v);
        d.c.c(this.g, (byte)3);
        d.c.j(this.g);
        this.k.b();
        if (d.c.b(this.g) == 1 && (d.c.d(this.g) < 0 || d.c.d(this.g) >= this.g.k.length || this.g.k[d.c.d(this.g)] == null || this.g.k[d.c.d(this.g)].w())) {
            d.c.a(this.g, d.c.a(this.g, d.c.d(this.g), this.g.k, d.c.g(this.g).a()));
            return;
        }
        if (d.c.b(this.g) == 0 && (d.c.d(this.g) < 0 || d.c.d(this.g) >= d.c.e(this.g).length || d.c.e(this.g)[d.c.d(this.g)] == null || d.c.e(this.g)[d.c.d(this.g)].w())) {
            d.c.a(this.g, d.c.b(this.g, d.c.d(this.g), d.c.e(this.g), d.c.g(this.g).a()));
        }
    }
}

