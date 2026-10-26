/*
 * Decompiled with CFR 0.152.
 */
package g;

import b.c;
import c.d;
import c.f;
import d.a;
import g.b;

final class k
extends b.b {
    private final b g;

    public k(b b2, byte by) {
        super(by, true);
        this.g = b2;
    }

    protected final void d() {
        g.b.i(this.g);
        this.k.b();
    }

    protected final void c() {
        g.b.a(this.g, (f)this.b());
        if (g.b.h(this.g) == null) {
            return;
        }
        if (g.b.h((b)this.g).q == 16) {
            short s2 = ((d)g.b.h((b)this.g)).n;
            if (s2 > g.b.j(this.g).e(6)) {
                c c2 = b.c.a(d.a.j(66), 2000L, null);
                this.g.e(c2);
                c2.a(main.b.a.i);
                return;
            }
            String[] stringArray = new String[]{g.b.f(this.g)[3] == null || g.b.f(this.g)[3].z() ? d.a.j(80) : g.b.f((b)this.g)[3].B, g.b.f(this.g)[4] == null || g.b.f(this.g)[4].z() ? d.a.j(80) : g.b.f((b)this.g)[4].B};
            g.b.a(this.g, b.c.a(stringArray, null, (byte)0, (byte)1));
            this.g.e(g.b.k(this.g));
            g.b.k(this.g).a(main.b.a.i);
            return;
        }
        g.b.a(this.g, g.b.h((b)this.g).v);
        g.b.b(this.g, g.b.h((b)this.g).u);
        g.b.c(this.g, (byte)3);
        g.b.l(this.g);
        this.k.b();
        if (g.b.b(this.g) == 1 && (g.b.d(this.g) < 0 || g.b.d(this.g) >= g.b.e(this.g).length || g.b.e(this.g)[g.b.d(this.g)] == null || g.b.e(this.g)[g.b.d(this.g)].z())) {
            g.b.a(this.g, g.b.a(this.g, g.b.d(this.g), g.b.e(this.g), g.b.h(this.g).a()));
            return;
        }
        if (g.b.b(this.g) == 0 && (g.b.d(this.g) < 0 || g.b.d(this.g) >= g.b.f(this.g).length || g.b.f(this.g)[g.b.d(this.g)] == null || g.b.f(this.g)[g.b.d(this.g)].z())) {
            g.b.a(this.g, g.b.b(this.g, g.b.d(this.g), g.b.f(this.g), g.b.h(this.g).a()));
        }
    }
}

