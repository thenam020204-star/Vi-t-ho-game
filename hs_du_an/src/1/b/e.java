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
import g.g;
import javax.microedition.lcdui.Graphics;
import main.a;
import main.b;

public final class e
extends f
implements a {
    private static String[] a = new String[]{d.a.q[54], d.a.q[2]};
    private c.f[] g = null;
    private int[] h = null;

    protected final void a(Graphics graphics, int n2, int n3, int n4) {
        if (this.g == null || n2 < 0 || n2 >= this.g.length) {
            return;
        }
        graphics.drawString(this.g[n2].r, n3, n4, 20);
    }

    protected final void b(Graphics graphics, int n2, int n3, int n4) {
        if (this.g == null || n2 < 0 || n2 >= this.g.length) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.g[n2].t / 2);
        stringBuffer.append("(");
        stringBuffer.append(this.h[n2]);
        stringBuffer.append(")");
        graphics.drawString(stringBuffer.toString(), n3, n4, 24);
    }

    protected final String a() {
        if (this.g == null || this.d < 0 || this.d >= this.g.length || this.g[this.d] == null) {
            return null;
        }
        return this.g[this.d].s;
    }

    private Object b() {
        if (this.g == null || this.d < 0 || this.d >= this.g.length) {
            return null;
        }
        return this.g[this.d];
    }

    protected final void c() {
        if (this.b() == null) {
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
        this.g();
        this.f();
    }

    private void g() {
        int[] nArray = d.a.a((byte)0, false);
        if (nArray == null) {
            this.g = null;
            this.h = null;
            this.b(0);
            return;
        }
        this.g = new c.f[nArray.length];
        this.h = new int[nArray.length];
        for (int i2 = 0; i2 < nArray.length; ++i2) {
            this.g[i2] = d.a.f(nArray[i2]);
            this.h[i2] = d.a.g(nArray[i2]);
        }
        this.b(this.g.length);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        super.a(graphics, n2, n3);
        graphics.drawImage(d.a.R, n2 + this.i - String.valueOf(d.a.E).length() * d.a.I, n3 - d.a.N.getHeight() / 2, 40);
        h.b.a(graphics, d.a.H, d.a.E, n2 + this.i - String.valueOf(d.a.E).length() * d.a.I, n3 - d.a.N.getHeight() / 2 - d.a.H.getHeight());
    }

    public final void a(j j2) {
        Object object = j2.d();
        if (((String)object).equals(d.a.q[54])) {
            object = (c.f)this.b();
            if (object == null) {
                ((g)main.b.a.i).a(d.a.q[47], 3000L);
            } else {
                d.a.E += ((c.f)object).t / 2;
                d.a.a((c.f)object, 1);
                int n2 = this.d;
                this.h[n2] = this.h[n2] - 1;
                if (this.h[this.d] == 0) {
                    this.g();
                    if (this.d >= this.g.length) {
                        this.d = this.g.length - 1;
                    }
                }
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

