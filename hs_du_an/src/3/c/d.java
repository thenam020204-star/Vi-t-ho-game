/*
 * Decompiled with CFR 0.152.
 */
package c;

import a.f;
import b.b.b;
import b.b.g;
import b.b.i;
import b.b.l;
import b.b.m;
import f.a;
import f.c;
import java.util.Vector;

public final class d
extends l {
    private i a = null;
    private b b = null;
    private a.g c = null;
    private static String[] k = new String[]{"\u4f7f\u7528", "\u8fd4\u56de"};
    private int l = 0;

    public d(a.g g2) {
        super(g2.N);
        this.c = g2;
    }

    protected final void a() {
        boolean bl = false;
        this.a = new i();
        c c2 = null;
        Vector vector = this.c.P();
        for (int i2 = 0; i2 < vector.size(); ++i2) {
            c2 = (c)vector.elementAt(i2);
            this.a.a(c2.e, "" + c2.i[0]);
        }
        this.a.b = 0;
        this.a.c = 0;
        this.a.d = this.g;
        this.a.e = this.h - b.b.b.a - 4;
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
                n2 = this.a.c();
                Object object = this.c.n(n2);
                if (object == null) break;
                Object object2 = null;
                if ((((c)object).i[24] & 1) == 0) {
                    return;
                }
                object2 = b.b.g.a(k, (byte)0, (byte)1, true);
                int n4 = ((m)object2).k();
                if (n4 != 1 || !((String)(object2 = ((g)object2).a())).equals("\u4f7f\u7528")) break;
                object2 = object;
                object = this.c;
                if (object == null || object2 == null) {
                    return;
                }
                if (Math.abs(((c)object2).i[0]) > ((a.g)object).c(4)) {
                    object = b.b.g.a("\u7075\u529b\u4e0d\u8db3!", 3000L);
                    ((m)object).k();
                    return;
                }
                object = object2;
                String[] stringArray = null;
                switch (((a)object).g) {
                    case 0: {
                        stringArray = new String[]{"\u5168\u90e8", "\u8fd4\u56de"};
                        break;
                    }
                    case 1: {
                        stringArray = new String[a.f.A.length + 1];
                        for (int i2 = 0; i2 < a.f.A.length; ++i2) {
                            stringArray[i2] = a.f.A[i2].p;
                        }
                        stringArray[stringArray.length - 1] = "\u8fd4\u56de";
                    }
                }
                object = b.b.g.a(stringArray, (byte)0, (byte)2, true);
                int n5 = ((m)object).k();
                if (n5 != 1) break;
                if (((String)(object = ((g)object).a())).equals("\u5168\u90e8")) {
                    Object object3 = object2;
                    object = this;
                    if (object3 != null && ((a)object3).g == 0) {
                        ((a)object3).a(((d)object).c, null, a.f.A);
                    }
                    return;
                }
                Object object4 = object = a.f.d((String)object);
                Object object5 = object2;
                object = this;
                if (object4 == null || object5 == null) {
                    return;
                }
                if (((a)object5).g != 1) break;
                ((a)object5).a(((d)object).c, (a.g)object4, a.f.A);
            }
        }
    }

    private void c() {
        int n2 = this.a.c();
        c c2 = this.c.n(n2);
        if (c2 == null) {
            return;
        }
        this.b.d();
        this.b.a(c2.e());
    }

    public final void b() {
        ++this.l;
        this.l %= 2;
        if (this.l == 0) {
            this.b.b();
        }
    }
}

