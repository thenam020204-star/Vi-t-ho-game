/*
 * Decompiled with CFR 0.152.
 */
package c;

import a.a.g;
import a.a.l;
import c.b;
import c.c;
import java.util.Vector;

final class f
extends g {
    private a.a.c h;
    private a.a.b i;
    private boolean j;
    private b.f k;
    private Vector l;
    private final c m;

    public f(c c2, b.f f2, boolean bl) {
        super(bl ? "\u6cd5\u5b9d" : "\u5947\u672f");
        this.m = c2;
        this.h = null;
        this.i = null;
        this.j = false;
        this.k = null;
        this.l = null;
        this.j = bl;
        this.k = f2;
    }

    protected final void a() {
        boolean bl = false;
        this.h = new a.a.c();
        this.h.b = 0;
        this.h.c = 0;
        this.h.d = this.d;
        this.h.e = this.e - 4 - a.a.b.a;
        f f2 = this;
        f2.l = f2.j ? f2.k.P() : f2.k.S();
        f.c c2 = null;
        short s = 0;
        for (int i2 = 0; i2 < f2.l.size(); ++i2) {
            c2 = (f.c)f2.l.elementAt(i2);
            s = f2.j ? c2.a[1] : c2.a[0];
            f2.h.a(c2.s, "" + s);
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
                if (this.l.size() <= 0) break;
                f f2 = this;
                n3 = f2.h.c();
                f f3 = f2 = n3 < 0 || n3 >= f2.l.size() ? null : (f.c)f2.l.elementAt(n3);
                if (c.c.b(this.m).c(4) < Math.abs(((f.c)((Object)f2)).a[0])) {
                    a.a.l.a("\u7075\u529b\u4e0d\u8db3!", 3000L).k();
                    return;
                }
                if (c.c.b(this.m).c(6) < Math.abs(((f.c)((Object)f2)).a[1])) {
                    a.a.l.a("\u4f53\u529b\u4e0d\u8db3!", 3000L).k();
                    return;
                }
                this.a.g = false;
                b b2 = new b(this.m, ((f.b)((Object)f2)).v == 1 ? this.m.l : this.m.k, ((f.b)((Object)f2)).v, ((f.b)((Object)f2)).u, ((f.c)((Object)f2)).a());
                if (b2.k() == 1) {
                    c.c.a(this.m, b2.h(), (f.c)((Object)f2));
                    this.a.b(1);
                    c.c.c(this.m).b();
                    return;
                }
                this.a.g = true;
            }
        }
    }

    private void c() {
        int n2 = this.h.c();
        if (n2 < 0 || n2 >= this.l.size()) {
            return;
        }
        f.c c2 = (f.c)this.l.elementAt(n2);
        this.i.a(c2.f());
    }

    public final void b() {
        this.i.b();
    }
}

