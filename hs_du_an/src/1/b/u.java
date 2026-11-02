/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Graphics
 */
package b;

import b.c;
import b.f;
import b.i;
import b.j;
import g.g;
import java.util.Vector;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import main.MainMIDlet;
import main.a;
import main.b;

public final class u
extends f
implements a {
    private static String[] a = new String[]{d.a.q[46], d.a.q[2]};
    private c.f[] g = null;
    private int[] h = null;
    private int[] l = null;

    public u(int[] nArray) {
        this.l = nArray;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        super.a(graphics, n2, n3);
        graphics.drawImage(d.a.R, n2 + this.i - String.valueOf(d.a.E).length() * d.a.I, n3 - d.a.N.getHeight() / 2, 40);
        h.b.a(graphics, d.a.H, d.a.E, n2 + this.i - String.valueOf(d.a.E).length() * d.a.I, n3 - d.a.N.getHeight() / 2 - d.a.H.getHeight());
    }

    protected final void a(Graphics graphics, int n2, int n3, int n4) {
        if (this.g == null || n2 < 0 || n2 > this.g.length) {
            return;
        }
        if (n2 == 0) {
            graphics.drawString(b.i.g[0], n3, n4, 20);
            return;
        }
        graphics.drawString(this.g[n2 - 1].r, n3, n4, 20);
    }

    protected final void b(Graphics graphics, int n2, int n3, int n4) {
        if (this.g == null || n2 < 0 || n2 > this.g.length) {
            return;
        }
        if (n2 > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(this.g[n2 - 1].t);
            stringBuffer.append("(");
            stringBuffer.append(this.h[n2 - 1]);
            stringBuffer.append(")");
            graphics.drawString(stringBuffer.toString(), n3, n4, 24);
        }
    }

    protected final String a() {
        if (this.d == 0) {
            return b.i.h[0] + "\u8d60\u90012\u4e2a\u5143\u5b9d(\u7528\u4e8e\u6362\u9ad8\u7ea7\u88c5\u5907),\u8bf7\u8bb0\u5f97\u5b58\u6863!";
        }
        if (this.g != null && this.d - 1 >= 0 && this.d - 1 < this.g.length && this.g[this.d - 1] != null) {
            return this.g[this.d - 1].s;
        }
        return null;
    }

    private Object b() {
        if (this.g == null || this.d <= 0 || this.d > this.g.length) {
            return null;
        }
        return this.g[this.d - 1];
    }

    public final void a(j object) {
        ((j)object).b();
        object = ((j)object).d();
        if (((String)object).equals(d.a.q[46])) {
            if (this.d == 0) {
                boolean bl = false;
                bl = d.a.a ? true : i.a.a(MainMIDlet.a, (Displayable)main.b.a, b.i.g[0], "06", "06", 200);
                c c2 = null;
                if (bl) {
                    d.a.a(1001, 2);
                    c2 = b.c.a(d.a.j(49), 3000L, null);
                    d.a.E += 3000;
                } else {
                    c2 = b.c.a(d.a.j(73), 3000L, null);
                }
                c2.a(this);
                main.b.a.i.e(c2);
                return;
            }
            object = (c.f)this.b();
            if (object == null) {
                ((g)main.b.a.i).a(d.a.q[47], 3000L);
                return;
            }
            c c3 = null;
            if (d.a.E < ((c.f)object).t) {
                c3 = b.c.a(d.a.q[48], 3000L, null);
            } else {
                c3 = b.c.a(d.a.q[49], 3000L, null);
                d.a.E -= ((c.f)object).t;
                d.a.b((c.f)object, 1);
                int n2 = this.d - 1;
                this.h[n2] = this.h[n2] + 1;
            }
            c3.a(this);
            main.b.a.i.e(c3);
        }
    }

    protected final void c() {
        if (this.d != 0 && this.b() == null) {
            return;
        }
        c c2 = b.c.a(a, null, (byte)0, (byte)2);
        c2.a(this);
        main.b.a.i.e(c2);
    }

    protected final void d() {
        this.k.b();
    }

    public final void e() {
        int n2;
        u u2;
        int[] nArray = this.l;
        u u3 = this;
        if (nArray == null) {
            u2 = u3;
            n2 = 1;
        } else {
            int n3;
            Vector<c.f> vector = new Vector<c.f>();
            c.f f2 = null;
            for (n3 = 0; n3 < nArray.length; ++n3) {
                f2 = d.a.a(nArray[n3]);
                if (f2 == null || f2.t <= 0) continue;
                vector.addElement(f2);
            }
            if (vector.size() > 0) {
                u3.g = new c.f[vector.size()];
                u3.h = new int[u3.g.length];
                for (n3 = 0; n3 < vector.size(); ++n3) {
                    u3.g[n3] = (c.f)vector.elementAt(n3);
                    u3.h[n3] = d.a.a(u3.g[n3]);
                }
            }
            u2 = u3;
            n2 = u3.g == null ? 1 : u3.g.length + 1;
        }
        u2.b(n2);
        this.f();
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

