/*
 * Decompiled with CFR 0.152.
 */
package d;

import a.f;
import b.b.b;
import b.b.l;
import b.b.m;
import b.d.a;
import d.d;
import d.i;
import java.util.Vector;

final class g
extends l {
    private b.b.i a;
    private b b;
    private Vector c;
    private byte k;
    private final d l;

    public g(d d2, byte by) {
        super(f.a.b[by]);
        this.l = d2;
        this.a = null;
        this.b = null;
        this.c = new Vector();
        this.k = (byte)-1;
        this.k = by;
    }

    protected final void a() {
        boolean bl = false;
        this.a = new b.b.i();
        this.a.b = 0;
        this.a.c = 0;
        this.a.d = this.g;
        this.a.e = this.h - 4 - b.b.b.a;
        g g2 = this;
        int[] nArray = a.f.a(g2.k, true);
        a a2 = null;
        f.a a3 = null;
        if (nArray != null) {
            for (int i2 = 0; i2 < nArray.length; ++i2) {
                a3 = a.f.f(nArray[i2]);
                if (a3 == null) continue;
                a2 = new a();
                new a().a = a3;
                a2.c = a.f.h(nArray[i2]);
                a2.b = nArray[i2];
                g2.a.a(a3.e, "" + a2.c);
                g2.c.addElement(a2);
            }
        }
        this.a(this.a);
        int n2 = 0 + this.a.e;
        this.a(n2);
        this.b = new b();
        this.b.b = 0;
        this.b.c = n2 += 4;
        this.b.d = this.g;
        this.b.e = b.b.b.a;
        this.a(this.b);
        this.c();
    }

    public final void a(int n2, int n3) {
        switch (n2) {
            case 50: {
                this.a.a();
                this.c();
                return;
            }
            case 56: {
                this.a.b();
                this.c();
                return;
            }
            case 53: {
                if (this.c.size() <= 0) break;
                this.d.g = false;
                g g2 = this;
                if (((a)((Object)(g2 = (n3 = g2.a.c()) < 0 || n3 >= g2.c.size() ? null : (a)g2.c.elementAt((int)n3)))).a.c() == 5) {
                    Object object = new String[]{this.l.e[3] == null ? "\u7a7a\u4f4d" : this.l.e[3].p, this.l.e[4] == null ? "\u7a7a\u4f4d" : this.l.e[4].p};
                    if (((m)(object = b.b.g.a((String[])object, (byte)0, (byte)1, true))).k() == 1) {
                        d.d.a(this.l, ((b.b.g)object).j() + 3, (f.b)((a)((Object)g2)).a);
                        this.d.a(1);
                        d.d.c(this.l).c();
                        break;
                    }
                    this.d.g = true;
                    return;
                }
                i i2 = new i(this.l, ((a)((Object)g2)).a.h == 1 ? this.l.f : this.l.e, ((a)((Object)g2)).a.h, ((a)((Object)g2)).a.g, ((a)((Object)g2)).a.a());
                if (i2.k() == 1) {
                    d.d.a(this.l, i2.j(), (a)((Object)g2));
                    this.d.a(1);
                    d.d.c(this.l).c();
                    return;
                }
                this.d.g = true;
            }
        }
    }

    private void c() {
        int n2 = this.a.c();
        if (n2 < 0 || n2 >= this.c.size()) {
            return;
        }
        a a2 = (a)this.c.elementAt(n2);
        this.b.a(a2.a.e());
    }

    public final void b() {
        this.b.b();
    }
}

