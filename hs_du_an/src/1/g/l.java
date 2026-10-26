/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package g;

import b.j;
import b.q;
import d.a;
import g.g;
import javax.microedition.lcdui.Graphics;
import main.b;
import main.c;
import main.d;

public final class l
extends c {
    public l() {
        super((byte)2);
    }

    public final void b() {
        super.b();
        d.a.z = -1;
        d.a.A = 0;
        d.a.B = 0;
        d d2 = d.a.a(d.a.y.d, (short)-2);
        if (d2 != null) {
            while (!d2.b()) {
                d2.a();
            }
        }
        d.a.s = d.a.a(d.a.y.d, (short)-1);
    }

    public final void a(Graphics graphics) {
        main.c.c(graphics);
        main.c.a(graphics, this.c);
    }

    public final void a() {
        d.a.i();
        d.a.y = null;
        d.a.s = null;
        System.gc();
        h.b.a(100L);
    }

    public final void a(int n2, int n3) {
        d.a.a();
        super.a(30);
        main.b.a.a();
        d.a.y = new q(d.a.z);
        for (n2 = 0; n2 < d.a.C.length; ++n2) {
            d.a.C[n2].a(d.a.C[n2].h(), false, true);
        }
        super.b(10);
        main.b.a.a();
        d.a.y.k();
        d.a.y.r();
        super.b(10);
        main.b.a.a();
        byte[] byArray = (byte[])d.a.r.get(new Integer(d.a.y.d));
        if (byArray != null) {
            d.a.y.a(byArray);
        }
        for (int i2 = 0; i2 < d.a.C.length; ++i2) {
            d.a.C[i2].b(d.a.A << 4);
            d.a.C[i2].c(d.a.B << 4);
        }
        d.a.w = 1;
        d.a.x = d.a.C[0].f;
        d.a.y.a(d.a.C[0]);
        super.b(10);
        main.b.a.a();
        g g2 = new g();
        this.a(g2);
        d.a.y.a(g2);
        super.a(100);
        main.b.a.a();
        System.gc();
        h.b.a(100L);
    }

    public final void a(j j2) {
    }

    public final void d(j j2) {
    }

    public final void b(j j2) {
    }

    public final void c(j j2) {
    }
}

