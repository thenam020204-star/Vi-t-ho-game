/*
 * Decompiled with CFR 0.152.
 */
package e;

import a.a.c;
import a.a.g;
import a.a.k;
import a.a.l;
import a.a.m;
import b.f;
import e.j;
import main.a;

public final class b
extends g {
    private static String[] h = new String[]{"\u88c5\u5907", "\u5378\u4e0b", "\u8fd4\u56de"};
    private f i = null;
    private String[] j = null;
    private c k = null;
    private a.a.b l = null;

    public b(f f2) {
        super(f2.K);
        this.i = f2;
    }

    protected final void a() {
        boolean bl = false;
        this.k = new c();
        this.k.b = 0;
        this.k.c = 0;
        this.k.d = this.d;
        this.k.e = this.e - a.a.b.a - 4;
        b b2 = this;
        f.a[] aArray = b2.i.E();
        b2.j = new String[aArray.length];
        String string = null;
        for (int i2 = 0; i2 < aArray.length; ++i2) {
            string = aArray[i2] == null ? "\u65e0" : aArray[i2].s;
            b2.k.a(b.f.E[i2], string);
            b2.j[i2] = aArray[i2] == null ? "" : aArray[i2].f();
        }
        this.a(this.k);
        int n2 = 0 + this.k.e;
        this.a(n2);
        this.l = new a.a.b();
        this.l.b = 0;
        this.l.c = n2 += 4;
        this.l.d = this.d;
        this.l.e = a.a.b.a;
        this.c();
        this.a(this.l);
    }

    public final void a(int n2, int n3) {
        switch (n2) {
            case 50: {
                this.k.a();
                this.c();
                return;
            }
            case 56: {
                this.k.b();
                this.c();
                return;
            }
            case 53: {
                Object object = a.a.l.a(h, (byte)0, (byte)1, true);
                n3 = ((m)object).k();
                if (n3 != 1) break;
                object = ((l)object).c();
                if (object == null) break;
                if (((String)object).equals("\u88c5\u5907")) {
                    int n4 = this.k.c();
                    n3 = (main.a.d - main.a.h) / 2;
                    int n5 = (main.a.e - main.a.i) / 2;
                    k k2 = new k(n3, n5, main.a.h, main.a.i);
                    Object object2 = this.i.g(n4);
                    object2 = new j((f.a)object2, b.f.F[n4], this.i);
                    k2.a((g)object2);
                    int n6 = k2.k();
                    if (n6 == 1 && (object2 = b.a.g(n6 = ((j)object2).c())) != null) {
                        this.i.c(n6, n4);
                        this.k.a(n4, b.f.E[n4], ((f.b)object2).s);
                        this.j[n4] = object2 == null ? "" : ((f.b)object2).f();
                        this.c();
                    }
                    return;
                }
                if (!((String)object).equals("\u5378\u4e0b")) break;
                int n7 = this.k.c();
                this.i.f(n7);
                this.j[n7] = null;
                this.k.a(n7, b.f.E[n7], "\u65e0");
                this.c();
            }
        }
    }

    private void c() {
        int n2 = this.k.c();
        if (n2 < 0 || n2 >= this.j.length) {
            return;
        }
        this.l.a(this.j[n2] == null ? "" : this.j[n2]);
        this.l.d();
    }

    public final void b() {
        this.l.b();
    }
}

