/*
 * Decompiled with CFR 0.152.
 */
package e;

import a.a.b;
import a.a.c;
import a.a.g;
import a.a.l;
import a.a.m;
import b.a;
import b.f;
import java.util.Vector;

public final class k
extends g {
    private c h = null;
    private b i = null;
    private f j = null;
    private static String[] k = new String[]{"\u4f7f\u7528", "\u8fd4\u56de"};
    private int l = 0;

    public k(f f2) {
        super(f2.K);
        this.j = f2;
    }

    protected final void a() {
        boolean bl = false;
        this.h = new c();
        f.c c2 = null;
        Vector vector = this.j.S();
        for (int i2 = 0; i2 < vector.size(); ++i2) {
            c2 = (f.c)vector.elementAt(i2);
            this.h.a(c2.s, "" + c2.a[0]);
        }
        this.h.b = 0;
        this.h.c = 0;
        this.h.d = this.d;
        this.h.e = this.e - a.a.b.a - 4;
        this.a(this.h);
        int n2 = 0 + this.h.e;
        this.a(n2);
        this.i = new b();
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
                n2 = this.h.c();
                Object object = this.j.n(n2);
                if (object == null) break;
                Object object2 = null;
                if ((((f.c)object).a[24] & 1) == 0) {
                    return;
                }
                object2 = a.a.l.a(k, (byte)0, (byte)1, true);
                int n4 = ((m)object2).k();
                if (n4 != 1 || !((String)(object2 = ((l)object2).c())).equals("\u4f7f\u7528")) break;
                object2 = object;
                object = this.j;
                if (object == null || object2 == null) {
                    return;
                }
                if (Math.abs(((f.c)object2).a[0]) > ((f)object).c(4)) {
                    object = a.a.l.a("\u7075\u529b\u4e0d\u8db3!", 3000L);
                    ((m)object).k();
                    return;
                }
                object = object2;
                String[] stringArray = null;
                switch (((f.b)object).u) {
                    case 0: {
                        stringArray = new String[]{"\u5168\u90e8", "\u8fd4\u56de"};
                        break;
                    }
                    case 1: {
                        stringArray = new String[b.a.A.length + 1];
                        for (int i2 = 0; i2 < b.a.A.length; ++i2) {
                            stringArray[i2] = b.a.A[i2].k;
                        }
                        stringArray[stringArray.length - 1] = "\u8fd4\u56de";
                    }
                }
                object = a.a.l.a(stringArray, (byte)0, (byte)2, true);
                int n5 = ((m)object).k();
                if (n5 != 1) break;
                if (((String)(object = ((l)object).c())).equals("\u5168\u90e8")) {
                    Object object3 = object2;
                    object = this;
                    if (object3 != null && ((f.b)object3).u == 0) {
                        ((f.b)object3).a(((k)object).j, null, b.a.A);
                    }
                    return;
                }
                Object object4 = object = b.a.d((String)object);
                Object object5 = object2;
                object = this;
                if (object4 == null || object5 == null) {
                    return;
                }
                if (((f.b)object5).u != 1) break;
                ((f.b)object5).a(((k)object).j, (f)object4, b.a.A);
            }
        }
    }

    private void c() {
        int n2 = this.h.c();
        f.c c2 = this.j.n(n2);
        if (c2 == null) {
            return;
        }
        this.i.d();
        this.i.a(c2.f());
    }

    public final void b() {
        ++this.l;
        this.l %= 2;
        if (this.l == 0) {
            this.i.b();
        }
    }
}

