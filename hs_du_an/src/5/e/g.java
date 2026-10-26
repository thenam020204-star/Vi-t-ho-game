/*
 * Decompiled with CFR 0.152.
 */
package e;

import a.a.c;
import a.a.l;
import a.c.b;
import a.f.a;
import java.util.Vector;

public final class g
extends a.a.g {
    private c h = null;
    private a.a.b i = null;
    private a.a.b j = null;
    private a.a.b k = null;
    private Vector l = new Vector();
    private Vector m = new Vector();
    private int n = 0;
    private static String[] o = new String[]{"\u8fd4\u56de", "\u786e\u5b9a\u5356\u51fa"};

    public g() {
        super("\u5356\u51fa");
        int[] nArray = b.a.a((byte)0, true);
        if (nArray != null) {
            f.b b2 = null;
            a a2 = null;
            int n2 = 0;
            for (int i2 = 0; i2 < nArray.length; ++i2) {
                a2 = new a();
                n2 = b.a.h(nArray[i2]);
                a2.a = b2 = b.a.f(nArray[i2]);
                a2.c = n2;
                a2.b = nArray[i2];
                this.l.addElement(a2);
                this.m.addElement(new Integer(0));
            }
        }
    }

    protected final void a() {
        int n2 = 0;
        this.i = new a.a.b();
        this.i.b = 0;
        this.i.c = 0;
        this.i.d = this.d;
        this.i.e = a.c.b.b;
        this.i.a("\u62e5\u6709\u91d1\u94b1:" + b.a.C);
        this.a(this.i);
        n2 = 0 + this.i.e;
        this.a(n2);
        this.j = new a.a.b();
        this.j.b = 0;
        this.j.c = n2 += 4;
        this.j.d = this.d;
        this.j.e = a.c.b.b;
        this.j.a("\u7269\u54c1\u4ef7\u503c:0");
        this.a(this.j);
        this.a(n2 += this.j.e);
        this.h = new c();
        this.h.b = 0;
        this.h.c = n2 += 4;
        this.h.d = this.d;
        this.h.e = this.e - n2 - a.a.b.a - 4;
        this.c();
        this.a(this.h);
        this.a(n2 += this.h.e);
        this.k = new a.a.b();
        this.k.b = 0;
        this.k.c = n2 += 4;
        this.k.d = this.d;
        this.k.e = a.a.b.a;
        this.d();
        this.a(this.k);
    }

    private void c() {
        a a2 = null;
        for (int i2 = 0; i2 < this.l.size(); ++i2) {
            a2 = (a)this.l.elementAt(i2);
            this.h.a(a2.a.s, a2.a.t / 2 + "[" + a2.c + "]<0>");
        }
    }

    private void d() {
        int n2 = this.h.c();
        if (n2 < 0 || n2 >= this.l.size()) {
            return;
        }
        a a2 = (a)this.l.elementAt(n2);
        this.k.a(a2.a.f());
    }

    public final void a(int n2, int n3) {
        if (n2 == 0 && n3 == 0) {
            return;
        }
        switch (n2) {
            case 50: {
                this.h.a();
                this.d();
                return;
            }
            case 56: {
                this.h.b();
                this.d();
                return;
            }
            case 52: {
                n2 = this.h.c();
                n3 = (Integer)this.m.elementAt(n2);
                if (n3 != 0) {
                    a a2 = (a)this.l.elementAt(n2);
                    this.n -= a2.a.t / 2;
                    this.j.a("\u7269\u54c1\u4ef7\u503c:" + this.n);
                    this.m.setElementAt(new Integer(--n3), n2);
                    this.h.a(n2, a2.a.s, a2.a.t / 2 + "[" + a2.c + "]<" + n3 + ">");
                }
                return;
            }
            case 54: {
                n2 = this.h.c();
                n3 = (Integer)this.m.elementAt(n2);
                a a3 = (a)this.l.elementAt(n2);
                if (n3 < a3.c) {
                    this.n += a3.a.t / 2;
                    this.j.a("\u7269\u54c1\u4ef7\u503c:" + this.n);
                    this.m.setElementAt(new Integer(++n3), n2);
                    this.h.a(n2, a3.a.s, a3.a.t / 2 + "[" + a3.c + "]<" + n3 + ">");
                }
                return;
            }
            case 53: {
                String string;
                l l2 = a.a.l.a(o, (byte)0, (byte)1, true);
                n3 = l2.k();
                if (n3 != 1 || !(string = l2.c()).equals("\u786e\u5b9a\u5356\u51fa")) break;
                this.e();
            }
        }
    }

    private void e() {
        int n2;
        b.a.C += this.n;
        int n3 = 0;
        a a2 = null;
        Vector<Integer> vector = new Vector<Integer>();
        int n4 = 0;
        for (n2 = 0; n2 < this.l.size(); ++n2) {
            n3 = (Integer)this.m.elementAt(n2);
            if (n3 <= 0) continue;
            a2 = (a)this.l.elementAt(n2);
            this.m.setElementAt(new Integer(0), n2);
            a2.c = n4 = a2.c - n3;
            b.a.d(a2.b, n3);
            if (n4 <= 0) {
                vector.addElement(new Integer(n2));
                continue;
            }
            this.h.a(n2, a2.a.s, a2.a.t / 2 + "[" + n4 + "]<0>");
        }
        for (n3 = 0; n3 < vector.size(); ++n3) {
            n2 = (Integer)vector.elementAt(n3);
            this.h.a(n2 - n3);
            this.l.removeElementAt(n2 - n3);
            this.m.removeElementAt(n2 - n3);
        }
        this.n = 0;
        this.i.a("\u62e5\u6709\u91d1\u94b1:" + b.a.C);
        this.j.a("\u7269\u54c1\u4ef7\u503c:" + this.n);
        this.d();
        a.a.l.a("\u5356\u51fa\u6210\u529f!", 3000L).k();
    }

    public final void b() {
        this.k.b();
    }
}

