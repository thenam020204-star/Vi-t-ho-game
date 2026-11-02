/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Displayable
 */
package b.c;

import b.b.f;
import b.b.g;
import b.b.j;
import b.b.m;
import b.c.c;
import c.b;
import c.d;
import c.i;
import c.n;
import d.k;
import javax.microedition.lcdui.Displayable;
import main.MainMIDlet;

public final class a
extends c
implements j {
    private b m = null;

    public a(int n2) {
        super(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void a(int n2, int n3) {
        boolean bl;
        if (a.f.q != null) {
            return;
        }
        switch (n2) {
            case -6: {
                v0.m = new b(0, 0, main.a.d, 0);
                this.m.a(this);
                d.k.c(this.m);
                return;
            }
            case -7: {
                int n4 = (main.a.d - main.a.h) / 2;
                int n5 = (main.a.e - main.a.i) / 2;
                f f2 = new f(n4, n5, main.a.h, main.a.i);
                new f(n4, n5, main.a.h, main.a.i).i = b.e.a.b("/bg.png");
                i i2 = new i();
                f2.a(i2);
                f2.k();
                return;
            }
            case 48: {
                if (a.f.w()) {
                    if (a.f.a = !a.f.a) {
                        b.b.g.a("\u4e0d\u9047\u654c\u529f\u80fd\u5df2\u5f00\u542f!", 3000L).k();
                        return;
                    }
                    b.b.g.a("\u4e0d\u9047\u654c\u529f\u80fd\u5df2\u5173\u95ed!", 3000L).k();
                    return;
                }
                boolean bl2 = false;
                if (a.f.t()) {
                    bl2 = true;
                } else {
                    g.a.a = null;
                    bl2 = g.a.a(MainMIDlet.a, (Displayable)main.a.a, "\u4e0d\u9047\u654c", "0110", String.valueOf(8), "0", 200, false);
                }
                if (bl2) {
                    g.a.a(String.valueOf(8), 0);
                    a.f.a(1001, 2);
                    a.f.d(true);
                    a.f.a = true;
                    b.b.g.a("\u4e0d\u9047\u654c\u529f\u80fd\u5df2\u5f00\u542f!", 3000L).k();
                    a.f.o();
                    return;
                }
                b.b.g.a("\u4ed8\u8d39\u5931\u8d25!", 3000L).k();
                return;
            }
        }
        int n6 = n2;
        switch (n6) {
            case 49: {
                b.c.a.q();
                return;
            }
            case 51: {
                b.c.a.t();
                return;
            }
            case 55: {
                b.c.a.s();
                return;
            }
            case 57: {
                b.c.a.v();
                return;
            }
            case 42: {
                b.c.a.r();
                return;
            }
            case 35: {
                b.c.a.u();
                return;
            }
            default: {
                bl = false;
            }
        }
        if (bl) {
            return;
        }
        a.f.A[0].b(n2, n3);
    }

    public final void a(b.b.c object) {
        String string = ((b.b.c)object).a();
        if (object == c2.m) {
            object = string;
            if (((String)object).equals(a.f.o[11])) {
                b.c.a.u();
                return;
            }
            if (((String)object).equals(a.f.o[9])) {
                b.c.a.s();
                return;
            }
            if (((String)object).equals(a.f.o[8])) {
                b.c.a.q();
                return;
            }
            if (((String)object).equals(a.f.o[7])) {
                b.c.a.t();
                return;
            }
            if (((String)object).equals(a.f.o[10])) {
                b.b.c c2 = b.b.g.a(a.f.o[69], 3000L);
                ((m)c2).k();
                return;
            }
            if (((String)object).equals(a.f.o[0])) {
                b.c.a.r();
                return;
            }
            if (((String)object).equals("\u4efb\u52a1")) {
                b.c.a.v();
            }
        }
    }

    public final void b(b.b.c c2) {
        d.k.d(c2);
    }

    private static void q() {
        int n2 = (main.a.d - main.a.h) / 2;
        int n3 = (main.a.e - main.a.i) / 2;
        f f2 = new f(n2, n3, main.a.h, main.a.i);
        new f(n2, n3, main.a.h, main.a.i).i = b.e.a.b("/bg.png");
        n n4 = null;
        for (int i2 = 0; i2 < f.a.a.length; ++i2) {
            n4 = new n(f.a.b[i2], f.a.a[i2]);
            f2.a(n4);
        }
        f2.k();
    }

    private static void r() {
        int n2 = (main.a.d - main.a.h) / 2;
        int n3 = (main.a.e - main.a.i) / 2;
        f f2 = new f(n2, n3, main.a.h, main.a.i);
        new f(n2, n3, main.a.h, main.a.i).i = b.e.a.b("/bg.png");
        c.g g2 = null;
        for (int i2 = 0; i2 < a.f.A.length; ++i2) {
            g2 = new c.g(a.f.A[i2]);
            f2.a(g2);
        }
        f2.k();
    }

    private static void s() {
        int n2 = (main.a.d - main.a.h) / 2;
        int n3 = (main.a.e - main.a.i) / 2;
        f f2 = new f(n2, n3, main.a.h, main.a.i);
        new f(n2, n3, main.a.h, main.a.i).i = b.e.a.b("/bg.png");
        c.c c2 = null;
        for (int i2 = 0; i2 < a.f.A.length; ++i2) {
            c2 = new c.c(a.f.A[i2]);
            f2.a(c2);
        }
        f2.k();
    }

    private static void t() {
        int n2 = (main.a.d - main.a.h) / 2;
        int n3 = (main.a.e - main.a.i) / 2;
        f f2 = new f(n2, n3, main.a.h, main.a.i);
        new f(n2, n3, main.a.h, main.a.i).i = b.e.a.b("/bg.png");
        d d2 = null;
        for (int i2 = 0; i2 < a.f.A.length; ++i2) {
            d2 = new d(a.f.A[i2]);
            f2.a(d2);
        }
        f2.k();
    }

    private static void u() {
        int n2 = (main.a.d - main.a.h) / 2;
        int n3 = (main.a.e - main.a.i) / 2;
        f f2 = new f(n2, n3, main.a.h, main.a.i);
        new f(n2, n3, main.a.h, main.a.i).i = b.e.a.b("/bg.png");
        c.f f3 = new c.f();
        f2.a(f3);
        f2.k();
    }

    public final void c() {
    }

    public final String a() {
        return null;
    }

    public final void b() {
        if (this.l == null) {
            return;
        }
        for (int i2 = 0; i2 < this.l.length; ++i2) {
            if (this.l[i2] == null || !this.l[i2].h || this.l[i2].a != 2) continue;
            this.l[i2].g = true;
            this.l[i2].e = false;
            this.l[i2].f = true;
        }
    }

    private static void v() {
        if (a.f.c != null) {
            a.f.c.d();
            a.f.q = a.f.c;
        }
    }
}

