/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b;

import b.c;
import b.f;
import b.j;
import d.g;
import javax.microedition.lcdui.Graphics;
import main.a;

public class b
extends f
implements a {
    public static final byte[] a = new byte[]{0, 1, 2, 3, 4, 5, 6};
    private static String[] g = new String[]{d.a.q[28], d.a.q[29], d.a.q[2]};
    private static String[] h = new String[]{d.a.q[29], d.a.q[2]};
    private c.f[] l = null;
    private int[] m = null;
    private int[] n = null;
    private byte o = (byte)-1;
    private boolean p = false;
    private c q = null;
    private c r = null;

    public b(byte by, boolean bl) {
        this.o = by;
        this.p = bl;
    }

    protected final void a(Graphics graphics, int n2, int n3, int n4) {
        if (this.l == null || n2 < 0 || n2 >= this.l.length) {
            return;
        }
        graphics.drawString(this.l[n2].r, n3, n4, 20);
    }

    protected final void b(Graphics graphics, int n2, int n3, int n4) {
        if (this.m == null || n2 < 0 || n2 >= this.m.length) {
            return;
        }
        graphics.drawString(String.valueOf(this.m[n2]), n3, n4, 24);
    }

    protected final String a() {
        if (this.l == null || this.d < 0 || this.d >= this.l.length || this.l[this.d] == null) {
            return null;
        }
        return this.l[this.d].s;
    }

    protected final Object b() {
        if (this.l == null || this.d < 0 || this.d >= this.l.length) {
            return null;
        }
        return this.l[this.d];
    }

    protected void c() {
        if (this.l == null) {
            return;
        }
        this.q = this.o == 1 ? b.c.a(g, null, (byte)0, (byte)2) : b.c.a(h, null, (byte)0, (byte)2);
        this.q.a(this);
        main.b.a.i.e(this.q);
    }

    protected void d() {
        this.k.b();
    }

    public final void e() {
        this.d = 0;
        this.e = 0;
        this.a(this.o);
        this.f();
    }

    private void a(byte by) {
        this.n = d.a.a(by, this.p);
        if (this.n == null) {
            this.l = null;
            this.m = null;
            this.b(0);
            return;
        }
        this.l = new c.f[this.n.length];
        this.m = new int[this.n.length];
        for (by = 0; by < this.n.length; by = (byte)(by + 1)) {
            this.l[by] = d.a.f(this.n[by]);
            this.m[by] = d.a.g(this.n[by]);
        }
        if (this.d < 0) {
            this.d = 0;
        } else if (this.d >= this.m.length) {
            this.d = this.m.length - 1;
        }
        this.b(this.l.length);
    }

    public final void a(j j2) {
        if (j2 == this.r) {
            Object object = j2;
            if (((String)(object = ((j)object).d())).equals(d.a.q[43])) {
                this.a((short)0);
            } else if (((String)object).equals(d.a.q[44])) {
                this.a((short)1);
            } else if (((String)object).equals(d.a.q[45])) {
                this.a((short)3);
            }
        } else if (j2 == this.q) {
            Object object = j2;
            if ((object = object.d()).equals(d.a.q[28])) {
                object = (c.f)this.b();
                if (object != null) {
                    switch (object.u) {
                        case 0: {
                            object.a(d.a.C[0], null, d.a.C);
                            d.a.a((c.f)object, 1);
                            this.a(this.o);
                            break;
                        }
                        case 1: {
                            object = new String[d.a.C.length + 1];
                            for (int i2 = 0; i2 < d.a.C.length; ++i2) {
                                object[i2] = d.a.C[i2].B;
                            }
                            object[((String[])object).length - 1] = d.a.q[2];
                            this.r = b.c.a(object, null, (byte)0, (byte)2);
                            this.r.a(this);
                            main.b.a.i.e(this.r);
                        }
                        default: {
                            break;
                        }
                    }
                }
            } else if (object.equals(d.a.q[29]) && (object = (c.f)this.b()) != null) {
                d.a.a((c.f)object, object.b() ? 999 : 1);
                this.a(this.o);
            }
        }
        j2.b();
    }

    private void a(short s2) {
        g g2 = d.a.b(s2);
        if (g2 == null) {
            return;
        }
        c.f f2 = (c.f)this.b();
        if (f2 == null) {
            return;
        }
        f2.a(g2, g2, null);
        d.a.a(f2, 1);
        this.a(this.o);
    }

    public final void d(j j2) {
        main.b.a.i.f(j2);
    }

    public final void b(j j2) {
    }

    public final void c(j j2) {
        j2.b();
    }
}

