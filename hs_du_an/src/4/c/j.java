/*
 * Decompiled with CFR 0.152.
 */
package c;

import a.f;
import b.b.b;
import b.b.g;
import b.b.l;
import b.b.m;
import b.e.a;
import c.i;
import java.util.Vector;

public final class j
extends l {
    private Vector a = new Vector();
    private Vector b = new Vector();
    private Vector c = new Vector();
    private int d = 0;
    private b.b.i l = null;
    private b m = null;
    private b n = null;
    private b o = null;
    private static String[] p = new String[]{"\u8fd4\u56de", "\u786e\u5b9a\u8d2d\u4e70"};

    public j(int[] nArray) {
        super("");
        f.a a2 = null;
        for (int i2 = 0; i2 < nArray.length; ++i2) {
            a2 = a.f.b(nArray[i2]);
            if (a2 == null) continue;
            this.a.addElement(a2);
        }
    }

    protected final void a() {
        int n2 = 0;
        this.m = new b();
        this.m.b = 0;
        this.m.c = 0;
        this.m.d = this.h;
        this.m.e = b.e.a.b;
        this.m.a("\u62e5\u6709\u91d1\u94b1:" + a.f.C);
        this.a(this.m);
        n2 = 0 + this.m.e;
        this.c(n2);
        this.n = new b();
        this.n.b = 0;
        this.n.c = n2 += 4;
        this.n.d = this.h;
        this.n.e = b.e.a.b;
        this.n.a("\u9700\u8981\u91d1\u94b1:" + this.d);
        this.a(this.n);
        this.c(n2 += this.n.e);
        this.l = new b.b.i();
        this.l.b = 0;
        this.l.c = n2 += 4;
        this.l.d = this.h;
        this.l.e = this.i - n2 - b.b.b.a - 4;
        this.c();
        this.a(this.l);
        this.c(n2 += this.l.e);
        this.o = new b();
        this.o.b = 0;
        this.o.c = n2 += 4;
        this.o.d = this.h;
        this.o.e = b.b.b.a;
        this.d();
        this.a(this.o);
    }

    private void c() {
        f.a a2 = null;
        int n2 = 0;
        this.l.a("\u5151\u6362\u91d1\u94b1", "2\u5143");
        for (int i2 = 0; i2 < this.a.size(); ++i2) {
            a2 = (f.a)this.a.elementAt(i2);
            n2 = a.f.a(a2);
            this.c.addElement(new Integer(n2));
            this.l.a(a2.e, a2.f + "[" + n2 + "]<" + 0 + ">");
            this.b.addElement(new Integer(0));
        }
    }

    private void d() {
        int n2 = this.l.c();
        if (n2 == 0) {
            this.o.a(c.i.c[0]);
            return;
        }
        if (--n2 < 0 || n2 >= this.a.size()) {
            return;
        }
        f.a a2 = (f.a)this.a.elementAt(n2);
        this.o.a(a2.e());
    }

    public final void a(int n2, int n3) {
        if (n2 == 0 && n3 == 0) {
            return;
        }
        switch (n2) {
            case 50: {
                ((j)object).l.a();
                ((j)object).d();
                return;
            }
            case 56: {
                ((j)object).l.b();
                ((j)object).d();
                return;
            }
            case 52: {
                n2 = ((j)object).l.c();
                if (n2 != 0 && (n3 = ((Integer)((j)object).b.elementAt(--n2)).intValue()) != 0) {
                    f.a a2 = (f.a)((j)object).a.elementAt(n2);
                    int n4 = (Integer)((j)object).c.elementAt(n2);
                    ((j)object).d -= a2.f;
                    ((j)object).n.a("\u9700\u8981\u91d1\u94b1:" + ((j)object).d);
                    ((j)object).b.setElementAt(new Integer(--n3), n2);
                    ((j)object).l.a(n2 + 1, a2.e, a2.f + "[" + n4 + "]<" + n3 + ">");
                }
                return;
            }
            case 54: {
                Object object;
                n2 = ((j)object).l.c();
                if (n2 != 0) {
                    n3 = (Integer)((j)object).b.elementAt(--n2);
                    f.a a3 = (f.a)((j)object).a.elementAt(n2);
                    int n5 = (Integer)((j)object).c.elementAt(n2);
                    if (n5 == 0 && a.f.s() == 0) {
                        object = b.b.g.a("\u80cc\u5305\u5df2\u6ee1", 3000L);
                        ((m)object).k();
                        break;
                    }
                    if (n3 + 1 + n5 <= 255) {
                        ((j)object).d += a3.f;
                        ((j)object).n.a("\u9700\u8981\u91d1\u94b1:" + ((j)object).d);
                        ((j)object).b.setElementAt(new Integer(++n3), n2);
                        ((j)object).l.a(n2 + 1, a3.e, a3.f + "[" + n5 + "]<" + n3 + ">");
                    }
                }
                return;
            }
            case 53: {
                String string;
                Object object;
                n2 = ((j)object).l.c();
                if (n2 == 0) {
                    n3 = 0;
                    n3 = a.f.t() ? 1 : (int)(c.i.a(c.i.d[0], 3, 0) ? 1 : 0);
                    if (n3 != 0) {
                        a.f.a(1001, 2);
                        b.b.g.a("[D~\u50b7\u6fc0\u6d3b]", 3000L).k();
                        ((j)object).m.a("\u62e5\u6709\u91d1\u94b1:" + (a.f.C += 3000));
                        g.a.a(String.valueOf(3), 0);
                        break;
                    }
                    b.b.g.a("\u5151\u6362\u5931\u8d25!", 3000L).k();
                    return;
                }
                g g2 = b.b.g.a(p, (byte)0, (byte)1, true);
                int n6 = g2.k();
                if (n6 != 1 || !(string = g2.a()).equals("\u786e\u5b9a\u8d2d\u4e70") || ((j)object).d <= 0) break;
                if (((j)object).d > a.f.C) {
                    object = b.b.g.a("\u91d1\u94b1\u4e0d\u8db3", 3000L);
                    ((m)object).k();
                    return;
                }
                ((j)object).e();
            }
        }
    }

    private void e() {
        a.f.C -= this.d;
        int n2 = 0;
        int n3 = 0;
        f.a a2 = null;
        for (int i2 = 0; i2 < this.b.size(); ++i2) {
            n2 = (Integer)this.b.elementAt(i2);
            if (n2 <= 0) continue;
            a2 = (f.a)this.a.elementAt(i2);
            this.b.setElementAt(new Integer(0), i2);
            n3 = (Integer)this.c.elementAt(i2);
            this.c.setElementAt(new Integer(n3 + n2), i2);
            a.f.b(a2, n2);
            this.l.a(i2 + 1, a2.e, a2.f + "[" + (n3 + n2) + "]<" + 0 + ">");
        }
        this.d = 0;
        this.m.a("\u62e5\u6709\u91d1\u94b1:" + a.f.C);
        this.n.a("\u9700\u8981\u91d1\u94b1:" + this.d);
        b.b.g.a("[D~\u50b7\u6fc0\u6d3b]", 3000L).k();
    }

    public final void b() {
        this.o.b();
    }
}

