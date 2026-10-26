/*
 * Decompiled with CFR 0.152.
 */
package d;

import b.a;
import c.b;
import c.j;
import c.p;
import c.u;
import d.c;

final class f
extends p {
    private final c a;

    public f(c c2, b.f f2) {
        super(f2);
        this.a = c2;
    }

    protected final void d() {
        d.c.h(this.a);
        this.k.b();
    }

    protected final void c() {
        Object object = ((p)object2).b();
        if (object == null) {
            d.c.h(((f)object2).a);
        } else {
            Object object2;
            object = (f.c)object;
            if (Math.abs(((f.c)object).a[0]) > d.c.i(((f)object2).a).c(4)) {
                object2 = c.b.a(b.a.p[51], 3000L);
                ((j)object2).f();
                return;
            }
            if (Math.abs(((f.c)object).a[1]) > d.c.i(((f)object2).a).c(6)) {
                object2 = c.b.a(b.a.p[66], 3000L);
                ((j)object2).f();
                return;
            }
            d.c.c(((f)object2).a, (byte)1);
            d.c.a(((f)object2).a, (f.c)object);
            d.c.a(((f)object2).a, ((f.b)object).w);
            d.c.b(((f)object2).a, ((f.b)object).v);
            d.c.j(((f)object2).a);
            if (d.c.b(((f)object2).a) == 1 && (d.c.d(((f)object2).a) < 0 || d.c.d(((f)object2).a) >= ((f)object2).a.k.length || ((f)object2).a.k[d.c.d(((f)object2).a)] == null || ((f)object2).a.k[d.c.d(((f)object2).a)].w())) {
                d.c.a(((f)object2).a, d.c.a(((f)object2).a, d.c.d(((f)object2).a), ((f)object2).a.k, d.c.k(((f)object2).a).a()));
            } else if (d.c.b(((f)object2).a) == 0 && (d.c.d(((f)object2).a) < 0 || d.c.d(((f)object2).a) >= d.c.e(((f)object2).a).length || d.c.e(((f)object2).a)[d.c.d(((f)object2).a)] == null || d.c.e(((f)object2).a)[d.c.d(((f)object2).a)].w())) {
                d.c.a(((f)object2).a, d.c.b(((f)object2).a, d.c.d(((f)object2).a), d.c.e(((f)object2).a), d.c.k(((f)object2).a).a()));
            }
        }
        ((u)object2).k.b();
    }
}

