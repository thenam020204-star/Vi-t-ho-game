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

final class e
extends l {
    private b.b.i a;
    private b b;
    private boolean c;
    private a.g d;
    private Vector l;
    private final d m;

    public e(d d2, a.g g2, boolean bl) {
        super(bl ? "\u6cd5\u5b9d" : "\u5947\u672f");
        this.m = d2;
        this.a = null;
        this.b = null;
        this.c = false;
        this.d = null;
        this.l = null;
        this.c = bl;
        this.d = g2;
    }

    protected final void a() {
        boolean bl = false;
        this.a = new b.b.i();
        this.a.b = 0;
        this.a.c = 0;
        this.a.d = this.h;
        this.a.e = this.i - 4 - b.b.b.a;
        e e2 = this;
        e2.l = e2.c ? e2.d.P() : e2.d.S();
        c c2 = null;
        short s = 0;
        for (int i2 = 0; i2 < e2.l.size(); ++i2) {
            c2 = (c)e2.l.elementAt(i2);
            s = e2.c ? c2.i[1] : c2.i[0];
            e2.a.a(c2.e, "" + s);
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
                if (this.l.size() <= 0) break;
                e e2 = this;
                n3 = e2.a.c();
                e e3 = e2 = n3 < 0 || n3 >= e2.l.size() ? null : (c)e2.l.elementAt(n3);
                if (d.d.b(this.m).c(4) < Math.abs(((c)((Object)e2)).i[0])) {
                    b.b.g.a("\u7075\u529b\u4e0d\u8db3!", 3000L).k();
                    return;
                }
                if (d.d.b(this.m).c(6) < Math.abs(((c)((Object)e2)).i[1])) {
                    b.b.g.a("\u4f53\u529b\u4e0d\u8db3!", 3000L).k();
                    return;
                }
                this.e.i = false;
                i i2 = new i(this.m, ((a)((Object)e2)).h == 1 ? this.m.g : this.m.f, ((a)((Object)e2)).h, ((a)((Object)e2)).g, ((c)((Object)e2)).a());
                if (i2.k() == 1) {
                    d.d.a(this.m, i2.j(), (c)((Object)e2));
                    this.e.a(1);
                    d.d.c(this.m).c();
                    return;
                }
                this.e.i = true;
            }
        }
    }

    private void c() {
        int n2 = this.a.c();
        if (n2 < 0 || n2 >= this.l.size()) {
            return;
        }
        c c2 = (c)this.l.elementAt(n2);
        this.b.a(c2.e());
    }

    public final void b() {
        this.b.b();
    }
}

