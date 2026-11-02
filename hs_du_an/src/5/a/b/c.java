/*
 * Decompiled with CFR 0.152.
 */
package a.b;

import a.a.h;
import a.a.o;
import a.b.a;
import a.c.b;
import e.d;
import e.k;
import e.l;
import e.m;

public final class c
extends a
implements h {
    private e.c m = null;

    public c(int n2) {
        super(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void a(int n2, int n3) {
        boolean bl;
        if (b.a.q != null) {
            return;
        }
        switch (n2) {
            case -6: {
                v0.m = new e.c(0, 0, main.a.d, 0);
                this.m.a(this);
                c.a.a(this.m);
                return;
            }
            case -7: {
                int n4 = (main.a.d - main.a.h) / 2;
                int n5 = (main.a.e - main.a.i) / 2;
                a.a.k k2 = new a.a.k(n4, n5, main.a.h, main.a.i);
                new a.a.k(n4, n5, main.a.h, main.a.i).s = a.c.b.b("/bg.png");
                l l2 = new l();
                k2.a(l2);
                k2.k();
                return;
            }
            case 48: {
                if (b.a.w()) {
                    if (b.a.a = !b.a.a) {
                        a.a.l.a("\u4e0d\u9047\u654c\u529f\u80fd\u5df2\u5f00\u542f!", 3000L).k();
                        return;
                    }
                    a.a.l.a("\u4e0d\u9047\u654c\u529f\u80fd\u5df2\u5173\u95ed!", 3000L).k();
                    return;
                }
                boolean bl2 = false;
                bl2 = b.a.t() ? true : e.l.a("\u4e0d\u9047\u654c", 8, 0);
                if (bl2) {
                    d.b.a(String.valueOf(8), 0);
                    b.a.a(1001, 2);
                    b.a.c(true);
                    b.a.a = true;
                    a.a.l.a("\u4e0d\u9047\u654c\u529f\u80fd\u5df2\u5f00\u542f!", 3000L).k();
                    b.a.o();
                    return;
                }
                a.a.l.a("\u4ed8\u8d39\u5931\u8d25!", 3000L).k();
                return;
            }
        }
        int n6 = n2;
        switch (n6) {
            case 49: {
                a.b.c.q();
                return;
            }
            case 51: {
                a.b.c.t();
                return;
            }
            case 55: {
                a.b.c.s();
                return;
            }
            case 57: {
                a.b.c.v();
                return;
            }
            case 42: {
                a.b.c.r();
                return;
            }
            case 35: {
                a.b.c.u();
                return;
            }
            default: {
                bl = false;
            }
        }
        if (bl) {
            return;
        }
        b.a.A[0].b(n2, n3);
    }

    public final void d(a.a.a object) {
        String string = ((a.a.a)object).c();
        if (object == a2.m) {
            object = string;
            if (((String)object).equals(b.a.o[11])) {
                a.b.c.u();
                return;
            }
            if (((String)object).equals(b.a.o[9])) {
                a.b.c.s();
                return;
            }
            if (((String)object).equals(b.a.o[8])) {
                a.b.c.q();
                return;
            }
            if (((String)object).equals(b.a.o[7])) {
                a.b.c.t();
                return;
            }
            if (((String)object).equals(b.a.o[10])) {
                a.a.a a2 = a.a.l.a(b.a.o[69], 3000L);
                ((a.a.m)a2).k();
                return;
            }
            if (((String)object).equals(b.a.o[0])) {
                a.b.c.r();
                return;
            }
            if (((String)object).equals("\u4efb\u52a1")) {
                a.b.c.v();
            }
        }
    }

    public final void c(a.a.a a2) {
        if (a2 == this.m) {
            this.m.h = null;
            this.m.i = null;
        }
        c.a.b(a2);
    }

    private static void q() {
        int n2 = (main.a.d - main.a.h) / 2;
        int n3 = (main.a.e - main.a.i) / 2;
        a.a.k k2 = new a.a.k(n2, n3, main.a.h, main.a.i);
        new a.a.k(n2, n3, main.a.h, main.a.i).s = a.c.b.b("/bg.png");
        d d2 = null;
        for (int i2 = 0; i2 < f.b.o.length; ++i2) {
            d2 = new d(f.b.p[i2], f.b.o[i2]);
            k2.a(d2);
        }
        k2.k();
    }

    private static void r() {
        int n2 = (main.a.d - main.a.h) / 2;
        int n3 = (main.a.e - main.a.i) / 2;
        a.a.k k2 = new a.a.k(n2, n3, main.a.h, main.a.i);
        new a.a.k(n2, n3, main.a.h, main.a.i).s = a.c.b.b("/bg.png");
        e.b b2 = null;
        for (int i2 = 0; i2 < b.a.A.length; ++i2) {
            b2 = new e.b(b.a.A[i2]);
            k2.a(b2);
        }
        k2.k();
    }

    private static void s() {
        int n2 = (main.a.d - main.a.h) / 2;
        int n3 = (main.a.e - main.a.i) / 2;
        a.a.k k2 = new a.a.k(n2, n3, main.a.h, main.a.i);
        new a.a.k(n2, n3, main.a.h, main.a.i).s = a.c.b.b("/bg.png");
        e.h h2 = null;
        for (int i2 = 0; i2 < b.a.A.length; ++i2) {
            h2 = new e.h(b.a.A[i2]);
            k2.a(h2);
        }
        k2.k();
    }

    private static void t() {
        int n2 = (main.a.d - main.a.h) / 2;
        int n3 = (main.a.e - main.a.i) / 2;
        a.a.k k2 = new a.a.k(n2, n3, main.a.h, main.a.i);
        new a.a.k(n2, n3, main.a.h, main.a.i).s = a.c.b.b("/bg.png");
        k k3 = null;
        for (int i2 = 0; i2 < b.a.A.length; ++i2) {
            k3 = new k(b.a.A[i2]);
            k2.a(k3);
        }
        k2.k();
    }

    private static void u() {
        int n2 = (main.a.d - main.a.h) / 2;
        int n3 = (main.a.e - main.a.i) / 2;
        o o2 = new o(n2, n3, main.a.h, main.a.i);
        new o(n2, n3, main.a.h, main.a.i).s = a.c.b.b("/bg.png");
        m m2 = new m();
        o2.a(m2);
        o2.k();
    }

    public final void b() {
    }

    public final String c() {
        return null;
    }

    public final void p() {
        if (this.l == null) {
            return;
        }
        for (int i2 = 0; i2 < this.l.length; ++i2) {
            if (this.l[i2] == null || !this.l[i2].c || this.l[i2].w != 2) continue;
            this.l[i2].b = true;
            this.l[i2].A = false;
            this.l[i2].a = true;
        }
    }

    private static void v() {
        if (b.a.c != null) {
            b.a.c.d();
            b.a.q = b.a.c;
        }
    }
}

