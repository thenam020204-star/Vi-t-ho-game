/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package c;

import a.a.h;
import a.b.c;
import a.c.b;
import c.a;
import c.g;
import javax.microedition.lcdui.Graphics;

public final class l
extends a {
    public l() {
        super((byte)2);
    }

    public final void b() {
        super.b();
        b.a.x = -1;
        b.a.y = 0;
        b.a.z = 0;
    }

    private static void i() {
        a.d.b b2 = b.a.w.c((short)-2);
        if (b2 != null) {
            while (!b2.b()) {
                b.a.w.a();
                b2.a();
            }
        }
        b.a.q = b.a.w.c((short)-1);
    }

    public final void a(Graphics graphics) {
        c.a.c(graphics);
        c.a.a(graphics, this.d);
    }

    public final void a() {
        b.a.h();
        b.a.w = null;
        b.a.q = null;
        b.a.y();
        b.a.k();
        b.a.ah = false;
        System.gc();
        a.c.b.a(100L);
    }

    public final void a(int n2, int n3) {
        b.a.a();
        super.a(30);
        main.a.a.a();
        long l2 = System.currentTimeMillis();
        b.a.w = new c(b.a.x);
        for (n2 = 0; n2 < b.a.A.length; ++n2) {
            b.a.A[n2].a(b.a.A[n2].m, false, true);
        }
        super.b(10);
        main.a.a.a();
        for (n2 = 0; n2 < b.a.A.length; ++n2) {
            b.a.A[n2].n = b.a.y << 4;
            b.a.A[n2].o = b.a.z << 4;
        }
        b.a.u = 1;
        b.a.v = b.a.A[0].i;
        b.a.w.a(b.a.A[0]);
        super.b(10);
        main.a.a.a();
        Object object = new g();
        this.a((a)object);
        b.a.w.a((h)object);
        super.a(100);
        main.a.a.a();
        byte[] byArray = (byte[])b.a.p.get(new Integer(b.a.w.k));
        object = byArray;
        if (byArray != null) {
            b.a.w.a((byte[])object);
        }
        b.a.w.l();
        b.a.w.m();
        super.b(10);
        main.a.a.a();
        l.i();
        b.a.w.p();
        System.gc();
        long l3 = 2000L - (System.currentTimeMillis() - l2);
        a.c.b.a(l3 <= 0L ? 100L : l3);
    }

    public final void d(a.a.a a2) {
    }

    public final void c(a.a.a a2) {
    }
}

