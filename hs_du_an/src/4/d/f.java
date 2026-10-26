/*
 * Decompiled with CFR 0.152.
 */
package d;

import b.b.b;
import b.b.g;
import b.b.l;
import d.d;
import d.i;
import f.a;
import f.c;
import java.util.Vector;

final class f
extends l {
    private b.b.i a;
    private b b;
    private a.g c;
    private Vector d;
    private final d l;

    public f(d d2, a.g g2) {
        super("\u7edd\u6280");
        this.l = d2;
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.c = g2;
    }

    protected final void a() {
        boolean bl = false;
        this.a = new b.b.i();
        this.a.b = 0;
        this.a.c = 0;
        this.a.d = this.h;
        this.a.e = this.i - 4 - b.b.b.a;
        f f2 = this;
        this.d = f2.c.T();
        c c2 = null;
        for (int i2 = 0; i2 < f2.d.size(); ++i2) {
            c2 = (c)f2.d.elementAt(i2);
            f2.a.a(c2.e, "");
        }
        this.a(this.a);
        int n2 = 0 + this.a.e;
        this.c(n2);
        this.b = new b();
        this.b.b = 0;
        this.b.c = n2 += 4;
        this.b.d = this.h;
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
                if (this.d.size() <= 0) break;
                f f2 = this;
                n3 = f2.a.c();
                f f3 = f2 = n3 < 0 || n3 >= f2.d.size() ? null : (c)f2.d.elementAt(n3);
                if (d.d.b(this.l).c(4) < Math.abs(((c)((Object)f2)).i[0])) {
                    b.b.g.a("\u7075\u529b\u4e0d\u8db3!", 3000L).k();
                    return;
                }
                if (d.d.b(this.l).c(6) < Math.abs(((c)((Object)f2)).i[1])) {
                    b.b.g.a("\u4f53\u529b\u4e0d\u8db3!", 3000L).k();
                    return;
                }
                this.e.i = false;
                i i2 = new i(this.l, this.l.g, 1, ((a)((Object)f2)).g, ((c)((Object)f2)).a());
                if (i2.k() == 1) {
                    d.d.b(this.l, i2.j(), (c)((Object)f2));
                    this.e.a(1);
                    d.d.c(this.l).c();
                    return;
                }
                this.e.i = true;
            }
        }
    }

    private void c() {
        int n2 = this.a.c();
        if (n2 < 0 || n2 >= this.d.size()) {
            return;
        }
        c c2 = (c)this.d.elementAt(n2);
        this.b.a(c2.e());
    }

    public final void b() {
        this.b.b();
    }
}

