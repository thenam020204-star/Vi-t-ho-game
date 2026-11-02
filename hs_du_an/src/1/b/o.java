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
import main.b;

public class o
extends f
implements a {
    private static String[] a = new String[]{d.a.q[28], d.a.q[2]};
    private c.b[] g = null;
    private g h = null;

    public o(g g2) {
        this.h = g2;
    }

    protected final void a(Graphics graphics, int n2, int n3, int n4) {
        if (this.g == null || n2 < 0 || n2 >= this.g.length || this.g[n2] == null) {
            return;
        }
        graphics.drawString(this.g[n2].r, n3, n4, 20);
    }

    protected final void b(Graphics graphics, int n2, int n3, int n4) {
        if (this.g == null || n2 < 0 || n2 >= this.g.length || this.g[n2] == null) {
            return;
        }
        graphics.drawString(String.valueOf(this.g[n2].c), n3, n4, 24);
    }

    protected final String a() {
        if (this.g == null || this.d < 0 || this.d >= this.g.length || this.g[this.d] == null) {
            return null;
        }
        return this.g[this.d].s;
    }

    protected final Object b() {
        if (this.g == null || this.d < 0 || this.d >= this.g.length) {
            return null;
        }
        return this.g[this.d];
    }

    protected void c() {
        Object object = this.b();
        if (object == null) {
            return;
        }
        object = (c.b)object;
        if ((((c.b)object).b & 1) != 0) {
            object = b.c.a(a, null, (byte)0, (byte)2);
            ((j)object).a(this);
            main.b.a.i.e((j)object);
        }
    }

    protected void d() {
        this.k.b();
    }

    public final void e() {
        o o2 = this;
        this.g = o2.h.H();
        o2.b(o2.g == null ? 0 : o2.g.length);
        this.f();
    }

    public final void a(j j2) {
        block8: {
            Object object;
            block10: {
                block7: {
                    c.b b2;
                    block9: {
                        object = j2.d();
                        if (!((String)object).equals(d.a.q[28])) break block7;
                        object = this.b();
                        if (object == null) break block8;
                        b2 = (c.b)object;
                        if (b2.c <= this.h.e(4)) break block9;
                        c c2 = b.c.a(d.a.q[51], 3000L, null);
                        c2.a(this);
                        main.b.a.i.e(c2);
                        break block8;
                    }
                    String[] stringArray = null;
                    switch (b2.a) {
                        case 0: {
                            stringArray = new String[]{d.a.q[50], d.a.q[2]};
                            break;
                        }
                        case 1: {
                            stringArray = new String[d.a.C.length + 1];
                            for (int i2 = 0; i2 < d.a.C.length; ++i2) {
                                stringArray[i2] = d.a.C[i2].B;
                            }
                            stringArray[stringArray.length - 1] = d.a.q[2];
                            break;
                        }
                        default: {
                            break block8;
                        }
                    }
                    c c3 = b.c.a(stringArray, null, (byte)0, (byte)2);
                    c3.a(this);
                    main.b.a.i.e(c3);
                    break block8;
                }
                if (!((String)object).equals(d.a.q[50])) break block10;
                object = this.b();
                if (object == null) break block8;
                c.b b3 = (c.b)object;
                if (b3.a == 0) {
                    b3.a(this.h, null, d.a.C);
                }
                break block8;
            }
            for (int i3 = 0; i3 < d.a.C.length; ++i3) {
                Object object2;
                if (!((String)object).equals(d.a.C[i3].B)) continue;
                object = d.a.C[i3];
                if (object == null || (object2 = this.b()) == null) break;
                c.b b4 = (c.b)object2;
                if (b4.a != 1) break;
                b4.a(this.h, (g)object, d.a.C);
                break;
            }
        }
        j2.b();
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

