/*
 * Decompiled with CFR 0.152.
 */
package g;

import b.c;
import b.j;
import b.o;
import c.f;
import d.a;
import d.g;
import g.b;

final class e
extends o {
    private final b a;

    public e(b b2, g g2) {
        super(g2);
        this.a = b2;
    }

    protected final void d() {
        g.b.i(this.a);
        this.k.b();
    }

    protected final void c() {
        Object object = this.b();
        if (object == null) {
            g.b.i(this.a);
        } else {
            object = (c.b)object;
            if (Math.abs(((c.b)object).c) > g.b.j(this.a).e(4)) {
                object = b.c.a(d.a.q[51], 3000L, null);
                ((j)object).a(this);
                this.a.e((j)object);
                return;
            }
            if (Math.abs(((c.b)object).d) > g.b.j(this.a).e(6)) {
                object = b.c.a(d.a.q[66], 3000L, null);
                ((j)object).a(this);
                this.a.e((j)object);
                return;
            }
            g.b.c(this.a, (byte)1);
            g.b.a(this.a, (c.b)object);
            g.b.a(this.a, ((f)object).v);
            g.b.b(this.a, ((c.b)object).a);
            g.b.l(this.a);
            if (g.b.b(this.a) == 1 && (g.b.d(this.a) < 0 || g.b.d(this.a) >= g.b.e(this.a).length || g.b.e(this.a)[g.b.d(this.a)] == null || g.b.e(this.a)[g.b.d(this.a)].z())) {
                g.b.a(this.a, g.b.a(this.a, g.b.d(this.a), g.b.e(this.a), g.b.m(this.a).a()));
            } else if (g.b.b(this.a) == 0 && (g.b.d(this.a) < 0 || g.b.d(this.a) >= g.b.f(this.a).length || g.b.f(this.a)[g.b.d(this.a)] == null || g.b.f(this.a)[g.b.d(this.a)].z())) {
                g.b.a(this.a, g.b.b(this.a, g.b.d(this.a), g.b.f(this.a), g.b.m(this.a).a()));
            }
        }
        this.k.b();
    }
}

