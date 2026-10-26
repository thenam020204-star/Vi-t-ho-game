/*
 * Decompiled with CFR 0.152.
 */
package c;

import b.b.b;
import b.b.f;
import b.b.i;
import b.b.m;
import c.l;
import main.a;

public final class g
extends b.b.l {
    private static String[] a = new String[]{"\u88c5\u5907", "\u5378\u4e0b", "\u8fd4\u56de"};
    private a.g b = null;
    private String[] c = null;
    private i d = null;
    private b l = null;

    public g(a.g g2) {
        super(g2.K);
        this.b = g2;
    }

    protected final void a() {
        boolean bl = false;
        this.d = new i();
        this.d.b = 0;
        this.d.c = 0;
        this.d.d = this.h;
        this.d.e = this.i - b.b.b.a - 4;
        g g2 = this;
        f.b[] bArray = g2.b.E();
        g2.c = new String[bArray.length];
        String string = null;
        for (int i2 = 0; i2 < bArray.length; ++i2) {
            string = bArray[i2] == null ? "\u65e0" : bArray[i2].e;
            g2.d.a(a.g.E[i2], string);
            g2.c[i2] = bArray[i2] == null ? "" : bArray[i2].e();
        }
        this.a(this.d);
        int n2 = 0 + this.d.e;
        this.c(n2);
        this.l = new b();
        this.l.b = 0;
        this.l.c = n2 += 4;
        this.l.d = this.h;
        this.l.e = b.b.b.a;
        this.c();
        this.a(this.l);
    }

    public final void a(int n2, int n3) {
        switch (n2) {
            case 50: {
                this.d.a();
                this.c();
                return;
            }
            case 56: {
                this.d.b();
                this.c();
                return;
            }
            case 53: {
                Object object = b.b.g.a(a, (byte)0, (byte)1, true);
                n3 = ((m)object).k();
                if (n3 != 1) break;
                object = ((b.b.g)object).a();
                if (object == null) break;
                if (((String)object).equals("\u88c5\u5907")) {
                    int n4 = this.d.c();
                    n3 = (main.a.d - main.a.h) / 2;
                    int n5 = (main.a.e - main.a.i) / 2;
                    f f2 = new f(n3, n5, main.a.h, main.a.i);
                    Object object2 = this.b.g(n4);
                    object2 = new l((f.b)object2, a.g.F[n4], this.b);
                    f2.a((b.b.l)object2);
                    int n6 = f2.k();
                    if (n6 == 1 && (object2 = a.f.g(n6 = ((l)object2).c())) != null) {
                        this.b.c(n6, n4);
                        this.d.a(n4, a.g.E[n4], ((f.a)object2).e);
                        this.c[n4] = object2 == null ? "" : ((f.a)object2).e();
                        this.c();
                    }
                    return;
                }
                if (!((String)object).equals("\u5378\u4e0b")) break;
                int n7 = this.d.c();
                this.b.f(n7);
                this.c[n7] = null;
                this.d.a(n7, a.g.E[n7], "\u65e0");
                this.c();
            }
        }
    }

    private void c() {
        int n2 = this.d.c();
        if (n2 < 0 || n2 >= this.c.length) {
            return;
        }
        this.l.a(this.c[n2] == null ? "" : this.c[n2]);
        this.l.d();
    }

    public final void b() {
        this.l.b();
    }
}

