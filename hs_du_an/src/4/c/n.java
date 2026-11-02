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

public final class n
extends l {
    private static String[] a = new String[]{"\u4f7f\u7528", "\u4e22\u5f03", "\u8fd4\u56de"};
    private static String[] b = new String[]{"\u8fd4\u56de", "\u4e22\u5f03"};
    private byte c = (byte)-1;
    private i d = null;
    private b l = null;
    private Vector m = new Vector();
    private Vector n = new Vector();

    public n(String string, byte by) {
        super(string);
        this.c = by;
    }

    protected final void a() {
        int n2;
        boolean bl = false;
        this.d = new i();
        this.d.b = 0;
        this.d.c = 0;
        this.d.d = this.h;
        this.d.e = this.i - b.b.b.a - 4;
        n n3 = this;
        Object object = n3;
        Object object2 = a.f.a(n3.c, true);
        if (object2 != null) {
            for (n2 = 0; n2 < ((int[])object2).length; ++n2) {
                ((n)object).m.addElement(new Integer(object2[n2]));
            }
        }
        if (n3.m.size() > 0) {
            object = null;
            object2 = null;
            for (n2 = 0; n2 < n3.m.size(); ++n2) {
                Integer n4 = (Integer)n3.m.elementAt(n2);
                object2 = n4;
                object = a.f.f(n4);
                n3.d.a(((a)object).e, "" + a.f.h(object2.intValue()));
                n3.n.addElement(((a)object).e());
            }
        }
        this.a(this.d);
        int n5 = 0 + this.d.e;
        this.c(n5);
        this.l = new b();
        this.l.b = 0;
        this.l.c = n5 += 4;
        this.l.d = this.h - 8;
        this.l.e = b.b.b.a;
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
                this.d.a();
                this.c();
                break;
            }
            case 56: {
                this.d.b();
                this.c();
                break;
            }
            case 53: {
                Integer n4;
                int n5;
                Object object;
                n n6 = this;
                int n7 = n6.d.c();
                if (n7 < 0 || n7 >= n6.m.size() || (object = a.f.f(n5 = (n4 = (Integer)n6.m.elementAt(n7)).intValue())) == null) break;
                Object object2 = null;
                if (((a)object).d == 1) {
                    object2 = b.b.g.a(a, (byte)0, (byte)1, true);
                } else {
                    if (((a)object).d == 15) break;
                    object2 = b.b.g.a(b, (byte)0, (byte)1, true);
                }
                int n8 = ((m)object2).k();
                if (n8 != 1) break;
                if (((String)(object2 = ((g)object2).a())).equals("\u4f7f\u7528")) {
                    int n9 = n5;
                    object = (c)object;
                    n5 = n7;
                    n7 = n9;
                    if (((a)object).g == 1) {
                        object2 = new String[a.f.A.length];
                        for (n8 = 0; n8 < ((String[])object2).length; ++n8) {
                            object2[n8] = a.f.A[n8].p;
                        }
                        g g2 = b.b.g.a((String[])object2, (byte)0, (byte)1, true);
                        int n10 = g2.k();
                        if (n10 != 1) break;
                        Object object3 = g2.a();
                        object = a.f.d((String)object3);
                        int n11 = n5;
                        Object object4 = object;
                        if (object4 == null || (object3 = a.f.f(n7)) == null || ((a)object3).d != 1) break;
                        a.f.d(n7, 1);
                        n7 = a.f.h(n7);
                        if (n7 <= 0) {
                            n6.d.a(n11);
                            n6.m.removeElementAt(n11);
                        } else {
                            n6.d.a(n11, ((a)object3).e, "" + n7);
                        }
                        ((a)object3).a((a.g)object4, (a.g)object4, null);
                        break;
                    }
                    object2 = b.b.g.a(new String[]{"\u5168\u90e8", "\u8fd4\u56de"}, (byte)0, (byte)1, true);
                    n8 = ((m)object2).k();
                    if (n8 != 1 || !((String)(object2 = ((g)object2).a())).equals("\u5168\u90e8")) break;
                    a.f.d(n7, 1);
                    int n12 = a.f.h(n7);
                    if (n12 <= 0) {
                        n6.d.a(n5);
                        n6.m.removeElementAt(n5);
                    } else {
                        n6.d.a(n5, ((a)object).e, "" + n12);
                    }
                    ((a)object).a(a.f.A[0], null, a.f.A);
                    break;
                }
                if (!((String)object2).equals("\u4e22\u5f03")) break;
                int n13 = n5;
                n5 = n7;
                n7 = n13;
                a.f.d(n7, 999);
                n6.d.a(n5);
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
        int n2 = this.d.c();
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

