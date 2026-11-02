/*
 * Decompiled with CFR 0.152.
 */
package c;

import a.f;
import b.b.b;
import b.b.g;
import b.b.i;
import b.b.l;
import b.e.a;
import java.util.Vector;

public final class k
extends l {
    private i a = null;
    private b b = null;
    private b c = null;
    private b d = null;
    private Vector l = new Vector();
    private Vector m = new Vector();
    private int n = 0;
    private static String[] o = new String[]{"\u8fd4\u56de", "\u786e\u5b9a\u5356\u51fa"};

    public k() {
        super("\u5356\u51fa");
        int[] nArray = a.f.a((byte)0, true);
        if (nArray != null) {
            f.a a2 = null;
            b.d.a a3 = null;
            int n2 = 0;
            for (int i2 = 0; i2 < nArray.length; ++i2) {
                a3 = new b.d.a();
                n2 = a.f.h(nArray[i2]);
                a3.a = a2 = a.f.f(nArray[i2]);
                a3.c = n2;
                a3.b = nArray[i2];
                this.l.addElement(a3);
                this.m.addElement(new Integer(0));
            }
        }
    }

    protected final void a() {
        int n2 = 0;
        this.b = new b();
        this.b.b = 0;
        this.b.c = 0;
        this.b.d = this.h;
        this.b.e = b.e.a.b;
        this.b.a("\u62e5\u6709\u91d1\u94b1:" + a.f.C);
        this.a(this.b);
        n2 = 0 + this.b.e;
        this.c(n2);
        this.c = new b();
        this.c.b = 0;
        this.c.c = n2 += 4;
        this.c.d = this.h;
        this.c.e = b.e.a.b;
        this.c.a("\u7269\u54c1\u4ef7\u503c:0");
        this.a(this.c);
        this.c(n2 += this.c.e);
        this.a = new i();
        this.a.b = 0;
        this.a.c = n2 += 4;
        this.a.d = this.h;
        this.a.e = this.i - n2 - b.b.b.a - 4;
        this.c();
        this.a(this.a);
        this.c(n2 += this.a.e);
        this.d = new b();
        this.d.b = 0;
        this.d.c = n2 += 4;
        this.d.d = this.h;
        this.d.e = b.b.b.a;
        this.d();
        this.a(this.d);
    }

    private void c() {
        b.d.a a2 = null;
        for (int i2 = 0; i2 < this.l.size(); ++i2) {
            a2 = (b.d.a)this.l.elementAt(i2);
            this.a.a(a2.a.e, a2.a.f / 2 + "[" + a2.c + "]<0>");
        }
    }

    private void d() {
        int n2 = this.a.c();
        if (n2 < 0 || n2 >= this.l.size()) {
            return;
        }
        b.d.a a2 = (b.d.a)this.l.elementAt(n2);
        this.d.a(a2.a.e());
    }

    public final void a(int n2, int n3) {
        if (n2 == 0 && n3 == 0) {
            return;
        }
        switch (n2) {
            case 50: {
                this.a.a();
                this.d();
                return;
            }
            case 56: {
                this.a.b();
                this.d();
                return;
            }
            case 52: {
                n2 = this.a.c();
                n3 = (Integer)this.m.elementAt(n2);
                if (n3 != 0) {
                    b.d.a a2 = (b.d.a)this.l.elementAt(n2);
                    this.n -= a2.a.f / 2;
                    this.c.a("\u7269\u54c1\u4ef7\u503c:" + this.n);
                    this.m.setElementAt(new Integer(--n3), n2);
                    this.a.a(n2, a2.a.e, a2.a.f / 2 + "[" + a2.c + "]<" + n3 + ">");
                }
                return;
            }
            case 54: {
                n2 = this.a.c();
                n3 = (Integer)this.m.elementAt(n2);
                b.d.a a3 = (b.d.a)this.l.elementAt(n2);
                if (n3 < a3.c) {
                    this.n += a3.a.f / 2;
                    this.c.a("\u7269\u54c1\u4ef7\u503c:" + this.n);
                    this.m.setElementAt(new Integer(++n3), n2);
                    this.a.a(n2, a3.a.e, a3.a.f / 2 + "[" + a3.c + "]<" + n3 + ">");
                }
                return;
            }
            case 53: {
                String string;
                g g2 = b.b.g.a(o, (byte)0, (byte)1, true);
                n3 = g2.k();
                if (n3 != 1 || !(string = g2.a()).equals("\u786e\u5b9a\u5356\u51fa")) break;
                this.e();
            }
        }
    }

    private void e() {
        int n2;
        a.f.C += this.n;
        int n3 = 0;
        b.d.a a2 = null;
        Vector<Integer> vector = new Vector<Integer>();
        int n4 = 0;
        for (n2 = 0; n2 < this.l.size(); ++n2) {
            n3 = (Integer)this.m.elementAt(n2);
            if (n3 <= 0) continue;
            a2 = (b.d.a)this.l.elementAt(n2);
            this.m.setElementAt(new Integer(0), n2);
            a2.c = n4 = a2.c - n3;
            a.f.d(a2.b, n3);
            if (n4 <= 0) {
                vector.addElement(new Integer(n2));
                continue;
            }
            this.a.a(n2, a2.a.e, a2.a.f / 2 + "[" + n4 + "]<0>");
        }
        for (n3 = 0; n3 < vector.size(); ++n3) {
            n2 = (Integer)vector.elementAt(n3);
            this.a.a(n2 - n3);
            this.l.removeElementAt(n2 - n3);
            this.m.removeElementAt(n2 - n3);
        }
        this.n = 0;
        this.b.a("\u62e5\u6709\u91d1\u94b1:" + a.f.C);
        this.c.a("\u7269\u54c1\u4ef7\u503c:" + this.n);
        this.d();
        b.b.g.a("\u5356\u51fa\u6210\u529f!", 3000L).k();
    }

    public final void b() {
        this.d.b();
    }
}

