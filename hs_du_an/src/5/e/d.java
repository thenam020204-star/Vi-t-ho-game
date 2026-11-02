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

public final class d
extends g {
    private static String[] h = new String[]{"\u4f7f\u7528", "\u4e22\u5f03", "\u8fd4\u56de"};
    private static String[] i = new String[]{"\u8fd4\u56de", "\u4e22\u5f03"};
    private byte j = (byte)-1;
    private c k = null;
    private b l = null;
    private Vector m = new Vector();
    private Vector n = new Vector();

    public d(String string, byte by) {
        super(string);
        this.j = by;
    }

    protected final void a() {
        int n2;
        boolean bl = false;
        this.k = new c();
        this.k.b = 0;
        this.k.c = 0;
        this.k.d = this.d;
        this.k.e = this.e - a.a.b.a - 4;
        d d2 = this;
        Object object = d2;
        Object object2 = b.a.a(d2.j, true);
        if (object2 != null) {
            for (n2 = 0; n2 < ((int[])object2).length; ++n2) {
                ((d)object).m.addElement(new Integer(object2[n2]));
            }
        }
        if (d2.m.size() > 0) {
            object = null;
            object2 = null;
            for (n2 = 0; n2 < d2.m.size(); ++n2) {
                Integer n3 = (Integer)d2.m.elementAt(n2);
                object2 = n3;
                object = b.a.f(n3);
                d2.k.a(((f.b)object).s, "" + b.a.h(object2.intValue()));
                d2.n.addElement(((f.b)object).f());
            }
        }
        this.a(this.k);
        int n4 = 0 + this.k.e;
        this.a(n4);
        this.l = new b();
        this.l.b = 0;
        this.l.c = n4 += 4;
        this.l.d = this.d - 8;
        this.l.e = a.a.b.a;
        if (this.n.size() > 0) {
            this.l.a((String)this.n.elementAt(0));
        }
        this.a(this.l);
    }

    public final void a(int n2, int n3) {
        if (n2 == 0 && n3 == 0) {
            return;
        }
        switch (n2) {
            case 50: {
                this.k.a();
                this.c();
                break;
            }
            case 56: {
                this.k.b();
                this.c();
                break;
            }
            case 53: {
                Integer n4;
                int n5;
                Object object;
                d d2 = this;
                int n6 = d2.k.c();
                if (n6 < 0 || n6 >= d2.m.size() || (object = b.a.f(n5 = (n4 = (Integer)d2.m.elementAt(n6)).intValue())) == null) break;
                Object object2 = null;
                if (((f.b)object).r == 1) {
                    object2 = a.a.l.a(h, (byte)0, (byte)1, true);
                } else {
                    if (((f.b)object).r == 15) break;
                    object2 = a.a.l.a(i, (byte)0, (byte)1, true);
                }
                int n7 = ((m)object2).k();
                if (n7 != 1) break;
                if (((String)(object2 = ((l)object2).c())).equals("\u4f7f\u7528")) {
                    int n8 = n5;
                    object = (f.c)object;
                    n5 = n6;
                    n6 = n8;
                    if (((f.b)object).u == 1) {
                        object2 = new String[b.a.A.length];
                        for (n7 = 0; n7 < ((String[])object2).length; ++n7) {
                            object2[n7] = b.a.A[n7].k;
                        }
                        l l2 = a.a.l.a((String[])object2, (byte)0, (byte)1, true);
                        int n9 = l2.k();
                        if (n9 != 1) break;
                        Object object3 = l2.c();
                        object = b.a.d((String)object3);
                        int n10 = n5;
                        Object object4 = object;
                        if (object4 == null || (object3 = b.a.f(n6)) == null || ((f.b)object3).r != 1) break;
                        b.a.d(n6, 1);
                        n6 = b.a.h(n6);
                        if (n6 <= 0) {
                            d2.k.a(n10);
                            d2.m.removeElementAt(n10);
                        } else {
                            d2.k.a(n10, ((f.b)object3).s, "" + n6);
                        }
                        ((f.b)object3).a((f)object4, (f)object4, null);
                        break;
                    }
                    object2 = a.a.l.a(new String[]{"\u5168\u90e8", "\u8fd4\u56de"}, (byte)0, (byte)1, true);
                    n7 = ((m)object2).k();
                    if (n7 != 1 || !((String)(object2 = ((l)object2).c())).equals("\u5168\u90e8")) break;
                    b.a.d(n6, 1);
                    int n11 = b.a.h(n6);
                    if (n11 <= 0) {
                        d2.k.a(n5);
                        d2.m.removeElementAt(n5);
                    } else {
                        d2.k.a(n5, ((f.b)object).s, "" + n11);
                    }
                    ((f.b)object).a(b.a.A[0], null, b.a.A);
                    break;
                }
                if (!((String)object2).equals("\u4e22\u5f03")) break;
                int n12 = n5;
                n5 = n6;
                n6 = n12;
                b.a.d(n6, 999);
                d2.k.a(n5);
            }
        }
        switch (n3) {
            case 51: {
                this.l.a();
                return;
            }
            case 57: {
                this.l.c();
            }
        }
    }

    private void c() {
        int n2 = this.k.c();
        if (n2 < 0 || n2 >= this.n.size()) {
            return;
        }
        this.l.d();
        this.l.a((String)this.n.elementAt(n2));
    }

    public final void b() {
        this.l.b();
    }
}

