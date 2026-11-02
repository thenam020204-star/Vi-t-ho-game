/*
 * Decompiled with CFR 0.152.
 */
package c;

import a.a.g;
import a.a.l;
import a.a.m;
import a.f.a;
import c.b;
import c.c;
import java.util.Vector;

final class k
extends g {
    private a.a.c h;
    private a.a.b i;
    private Vector j;
    private byte k;
    private final c l;

    public k(c c2, byte by) {
        super(f.b.p[by]);
        this.l = c2;
        this.h = null;
        this.i = null;
        this.j = new Vector();
        this.k = (byte)-1;
        this.k = by;
    }

    protected final void a() {
        boolean bl = false;
        this.h = new a.a.c();
        this.h.b = 0;
        this.h.c = 0;
        this.h.d = this.d;
        this.h.e = this.e - 4 - a.a.b.a;
        k k2 = this;
        int[] nArray = b.a.a(k2.k, true);
        a a2 = null;
        f.b b2 = null;
        if (nArray != null) {
            for (int i2 = 0; i2 < nArray.length; ++i2) {
                b2 = b.a.f(nArray[i2]);
                if (b2 == null) continue;
                a2 = new a();
                new a().a = b2;
                a2.c = b.a.h(nArray[i2]);
                a2.b = nArray[i2];
                k2.h.a(b2.s, "" + a2.c);
                k2.j.addElement(a2);
            }
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
                if (this.j.size() <= 0) break;
                this.a.g = false;
                k k2 = this;
                if (((a)((Object)(k2 = (n3 = k2.h.c()) < 0 || n3 >= k2.j.size() ? null : (a)k2.j.elementAt((int)n3)))).a.d() == 5) {
                    Object object = new String[]{this.l.k[3] == null ? "\u7a7a\u4f4d" : this.l.k[3].k, this.l.k[4] == null ? "\u7a7a\u4f4d" : this.l.k[4].k};
                    if (((m)(object = a.a.l.a((String[])object, (byte)0, (byte)1, true))).k() == 1) {
                        c.c.a(this.l, ((l)object).j() + 3, (f.a)((a)((Object)k2)).a);
                        this.a.b(1);
                        c.c.c(this.l).b();
                        break;
                    }
                    this.a.g = true;
                    return;
                }
                b b2 = new b(this.l, ((a)((Object)k2)).a.v == 1 ? this.l.l : this.l.k, ((a)((Object)k2)).a.v, ((a)((Object)k2)).a.u, ((a)((Object)k2)).a.a());
                if (b2.k() == 1) {
                    c.c.a(this.l, b2.h(), (a)((Object)k2));
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
        if (n2 < 0 || n2 >= this.j.size()) {
            return;
        }
        a a2 = (a)this.j.elementAt(n2);
        this.i.a(a2.a.f());
    }

    public final void b() {
        this.i.b();
    }
}

