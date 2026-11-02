/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package c;

import b.a;
import b.f;
import c.b;
import c.c;
import c.e;
import c.t;
import c.u;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class o
extends e {
    private static String[] a = new String[]{b.a.p[0], b.a.p[1], b.a.p[2]};
    private f g = null;
    private f.a[] h = null;
    private t l = null;

    public o(f f2) {
        this.g = f2;
    }

    protected final void a(Graphics graphics, int n2, int n3, int n4) {
        if (n2 < 0 || n2 >= b.f.v.length) {
            return;
        }
        graphics.drawString(b.f.v[n2], n3, n4, 20);
    }

    protected final void b(Graphics graphics, int n2, int n3, int n4) {
        if (n2 < 0 || n2 >= b.f.v.length) {
            return;
        }
        if (this.h == null || this.h[n2] == null) {
            graphics.drawString(b.a.p[3], n3, n4, 24);
            return;
        }
        graphics.drawString(this.h[n2].t, n3, n4, 24);
    }

    protected final String a() {
        if (this.h == null || this.d < 0 || this.d >= this.h.length || this.h[this.d] == null) {
            return null;
        }
        return this.h[this.d].d();
    }

    protected final void c() {
        Object object = c.b.a(a, (byte)0, (byte)2, true);
        int n2 = object.f();
        if (n2 == 1) {
            object = object.d();
            if (object != null) {
                if (object.equals(b.a.p[0])) {
                    Image[] imageArray = new Image[1];
                    object = imageArray;
                    imageArray[0] = b.a.S[4];
                    n2 = this.b();
                    u[] uArray = new c[1];
                    u[] uArray2 = uArray;
                    uArray[0] = new c(this.g, (byte)n2);
                    this.l = new t((Image[])object, uArray2, main.b.d, main.b.e);
                    t.a(t.l);
                    int n3 = this.l.f();
                    if (n3 == 1) {
                        this.g();
                        this.f();
                    }
                    return;
                }
                if (object.equals(b.a.p[1])) {
                    byte by = this.b();
                    this.g.f(by);
                    this.g();
                    this.f();
                }
            }
        }
    }

    protected final void d() {
        this.k.b();
    }

    public final void e() {
        this.g();
        this.f();
        this.b(b.f.v.length);
    }

    private byte b() {
        return b.f.u[this.d];
    }

    private void g() {
        this.h = this.g.y();
    }
}

