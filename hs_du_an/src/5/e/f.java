/*
 * Decompiled with CFR 0.152.
 */
package e;

import a.a.c;
import a.a.g;
import a.a.m;
import a.c.b;
import b.a;
import e.l;
import java.util.Vector;

public final class f
extends g {
    private Vector h = new Vector();
    private Vector i = new Vector();
    private Vector j = new Vector();
    private int k = 0;
    private c l = null;
    private a.a.b m = null;
    private a.a.b n = null;
    private a.a.b o = null;
    private static String[] p = new String[]{"\u8fd4\u56de", "\u786e\u5b9a\u8d2d\u4e70"};

    public f(int[] nArray) {
        super("");
        f.b b2 = null;
        for (int i2 = 0; i2 < nArray.length; ++i2) {
            b2 = b.a.b(nArray[i2]);
            if (b2 == null) continue;
            this.h.addElement(b2);
        }
    }

    protected final void a() {
        int n2 = 0;
        this.m = new a.a.b();
        this.m.b = 0;
        this.m.c = 0;
        this.m.d = this.d;
        this.m.e = a.c.b.b;
        this.m.a("\u62e5\u6709\u91d1\u94b1:" + b.a.C);
        this.a(this.m);
        n2 = 0 + this.m.e;
        this.a(n2);
        this.n = new a.a.b();
        this.n.b = 0;
        this.n.c = n2 += 4;
        this.n.d = this.d;
        this.n.e = a.c.b.b;
        this.n.a("\u9700\u8981\u91d1\u94b1:" + this.k);
        this.a(this.n);
        this.a(n2 += this.n.e);
        this.l = new c();
        this.l.b = 0;
        this.l.c = n2 += 4;
        this.l.d = this.d;
        this.l.e = this.e - n2 - a.a.b.a - 4;
        this.c();
        this.a(this.l);
        this.a(n2 += this.l.e);
        this.o = new a.a.b();
        this.o.b = 0;
        this.o.c = n2 += 4;
        this.o.d = this.d;
        this.o.e = a.a.b.a;
        this.d();
        this.a(this.o);
    }

    private void c() {
        f.b b2 = null;
        int n2 = 0;
        this.l.a("\u5151\u6362\u91d1\u94b1", "2\u5143");
        for (int i2 = 0; i2 < this.h.size(); ++i2) {
            b2 = (f.b)this.h.elementAt(i2);
            n2 = b.a.a(b2);
            this.j.addElement(new Integer(n2));
            this.l.a(b2.s, b2.t + "[" + n2 + "]<" + 0 + ">");
            this.i.addElement(new Integer(0));
        }
    }

    private void d() {
        int n2 = this.l.c();
        if (n2 == 0) {
            this.o.a(e.l.j[0]);
            return;
        }
        if (--n2 < 0 || n2 >= this.h.size()) {
            return;
        }
        f.b b2 = (f.b)this.h.elementAt(n2);
        this.o.a(b2.f());
    }

    public final void a(int n2, int n3) {
        if (n2 == 0 && n3 == 0) {
            return;
        }
        switch (n2) {
            case 50: {
                ((f)object).l.a();
                ((f)object).d();
                return;
            }
            case 56: {
                ((f)object).l.b();
                ((f)object).d();
                return;
            }
            case 52: {
                n2 = ((f)object).l.c();
                if (n2 != 0 && (n3 = ((Integer)((f)object).i.elementAt(--n2)).intValue()) != 0) {
                    f.b b2 = (f.b)((f)object).h.elementAt(n2);
                    int n4 = (Integer)((f)object).j.elementAt(n2);
                    ((f)object).k -= b2.t;
                    ((f)object).n.a("\u9700\u8981\u91d1\u94b1:" + ((f)object).k);
                    ((f)object).i.setElementAt(new Integer(--n3), n2);
                    ((f)object).l.a(n2 + 1, b2.s, b2.t + "[" + n4 + "]<" + n3 + ">");
                }
                return;
            }
            case 54: {
                Object object;
                n2 = ((f)object).l.c();
                if (n2 != 0) {
                    n3 = (Integer)((f)object).i.elementAt(--n2);
                    f.b b3 = (f.b)((f)object).h.elementAt(n2);
                    int n5 = (Integer)((f)object).j.elementAt(n2);
                    if (n5 == 0 && b.a.s() == 0) {
                        object = a.a.l.a("\u80cc\u5305\u5df2\u6ee1", 3000L);
                        ((m)object).k();
                        break;
                    }
                    if (n3 + 1 + n5 <= 255) {
                        ((f)object).k += b3.t;
                        ((f)object).n.a("\u9700\u8981\u91d1\u94b1:" + ((f)object).k);
                        ((f)object).i.setElementAt(new Integer(++n3), n2);
                        ((f)object).l.a(n2 + 1, b3.s, b3.t + "[" + n5 + "]<" + n3 + ">");
                    }
                }
                return;
            }
            case 53: {
                String string;
                Object object;
                n2 = ((f)object).l.c();
                if (n2 == 0) {
                    n3 = 0;
                    n3 = b.a.t() ? 1 : (int)(e.l.a(e.l.k[0], 3, 0) ? 1 : 0);
                    if (n3 != 0) {
                        b.a.a(1001, 2);
                        a.a.l.a("\u5151\u6362\u6210\u529f!", 3000L).k();
                        ((f)object).m.a("\u62e5\u6709\u91d1\u94b1:" + (b.a.C += 3000));
                        d.b.a(String.valueOf(3), 0);
                        break;
                    }
                    a.a.l.a("\u5151\u6362\u5931\u8d25!", 3000L).k();
                    return;
                }
                a.a.l l2 = a.a.l.a(p, (byte)0, (byte)1, true);
                int n6 = l2.k();
                if (n6 != 1 || !(string = l2.c()).equals("\u786e\u5b9a\u8d2d\u4e70") || ((f)object).k <= 0) break;
                if (((f)object).k > b.a.C) {
                    object = a.a.l.a("\u91d1\u94b1\u4e0d\u8db3", 3000L);
                    ((m)object).k();
                    return;
                }
                ((f)object).e();
            }
        }
    }

    private void e() {
        b.a.C -= this.k;
        int n2 = 0;
        int n3 = 0;
        f.b b2 = null;
        for (int i2 = 0; i2 < this.i.size(); ++i2) {
            n2 = (Integer)this.i.elementAt(i2);
            if (n2 <= 0) continue;
            b2 = (f.b)this.h.elementAt(i2);
            this.i.setElementAt(new Integer(0), i2);
            n3 = (Integer)this.j.elementAt(i2);
            this.j.setElementAt(new Integer(n3 + n2), i2);
            b.a.b(b2, n2);
            this.l.a(i2 + 1, b2.s, b2.t + "[" + (n3 + n2) + "]<" + 0 + ">");
        }
        this.k = 0;
        this.m.a("\u62e5\u6709\u91d1\u94b1:" + b.a.C);
        this.n.a("\u9700\u8981\u91d1\u94b1:" + this.k);
        a.a.l.a("\u8d2d\u4e70\u6210\u529f!", 3000L).k();
    }

    public final void b() {
        this.o.b();
    }
}

