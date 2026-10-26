/*
 * Decompiled with CFR 0.152.
 */
package c;

import a.a.g;
import a.a.l;
import b.f;
import c.b;
import c.c;
import java.util.Vector;

final class d
extends g {
    private a.a.c h;
    private a.a.b i;
    private f j;
    private Vector k;
    private final c l;

    public d(c c2, f f2) {
        super("\u7edd\u6280");
        this.l = c2;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = null;
        this.j = f2;
    }

    protected final void a() {
        boolean bl = false;
        this.h = new a.a.c();
        this.h.b = 0;
        this.h.c = 0;
        this.h.d = this.d;
        this.h.e = this.e - 4 - a.a.b.a;
        d d2 = this;
        this.k = d2.j.T();
        f.c c2 = null;
        for (int i2 = 0; i2 < d2.k.size(); ++i2) {
            c2 = (f.c)d2.k.elementAt(i2);
            d2.h.a(c2.s, "");
        }
        this.a(this.h);
        int n2 = 0 + this.h.e;
        this.a(n2);
        this.i = new a.a.b();
        this.i.b = 0;
        this.i.c = n2 += 4;
        this.i.d = this.d;
        this.i.e = a.a.b.a;
        this.a(this.i);
        this.c();
    }

    public final void a(int n2, int n3) {
        switch (n2) {
            case 50: {
                this.h.a();
                this.c();
                return;
            }
            case 56: {
                this.h.b();
                this.c();
                return;
            }
            case 53: {
                if (this.k.size() <= 0) break;
                d d2 = this;
                n3 = d2.h.c();
                d d3 = d2 = n3 < 0 || n3 >= d2.k.size() ? null : (f.c)d2.k.elementAt(n3);
                if (c.c.b(this.l).c(4) < Math.abs(((f.c)((Object)d2)).a[0])) {
                    a.a.l.a("\u7075\u529b\u4e0d\u8db3!", 3000L).k();
                    return;
                }
                if (c.c.b(this.l).c(6) < Math.abs(((f.c)((Object)d2)).a[1])) {
                    a.a.l.a("\u4f53\u529b\u4e0d\u8db3!", 3000L).k();
                    return;
                }
                this.a.g = false;
                b b2 = new b(this.l, this.l.l, 1, ((f.b)((Object)d2)).u, ((f.c)((Object)d2)).a());
                if (b2.k() == 1) {
                    c.c.b(this.l, b2.h(), (f.c)((Object)d2));
                    this.a.b(1);
                    c.c.c(this.l).b();
                    return;
                }
                this.a.g = true;
            }
        }
    }

    private void c() {
        int n2 = this.h.c();
        if (n2 < 0 || n2 >= this.k.size()) {
            return;
        }
        f.c c2 = (f.c)this.k.elementAt(n2);
        this.i.a(c2.f());
    }

    public final void b() {
        this.i.b();
    }
}

