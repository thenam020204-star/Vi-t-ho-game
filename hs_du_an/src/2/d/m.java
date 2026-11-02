/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package d;

import c.k;
import c.r;
import d.a;
import d.h;
import javax.microedition.lcdui.Graphics;
import main.b;
import main.c;

public final class m
extends a {
    public m() {
        super((byte)2);
    }

    public final void b() {
        super.b();
        b.a.y = -1;
        b.a.z = 0;
        b.a.A = 0;
    }

    private static void i() {
        c c2 = b.a.a(b.a.x.c, (short)-2);
        if (c2 != null) {
            while (!c2.b()) {
                c2.a();
            }
        }
        b.a.r = b.a.a(b.a.x.c, (short)-1);
    }

    public final void a(Graphics graphics) {
        d.a.c(graphics);
        d.a.a(graphics, this.d);
    }

    public final void a() {
        b.a.h();
        b.a.x = null;
        b.a.r = null;
        b.a.k();
        b.a.ae = false;
        System.gc();
        e.a.b.a(100L);
    }

    public final void a(int n2, int n3) {
        b.a.a();
        super.a(30);
        main.b.a.a();
        long l2 = System.currentTimeMillis();
        b.a.x = new r(b.a.y);
        for (n2 = 0; n2 < b.a.B.length; ++n2) {
            b.a.B[n2].a(b.a.B[n2].i, false, true);
        }
        super.b(10);
        main.b.a.a();
        for (n2 = 0; n2 < b.a.B.length; ++n2) {
            b.a.B[n2].j = b.a.z << 4;
            b.a.B[n2].k = b.a.A << 4;
        }
        b.a.v = 1;
        b.a.w = b.a.B[0].f;
        b.a.x.a(b.a.B[0]);
        super.b(10);
        main.b.a.a();
        Object object = new h();
        this.a((a)object);
        b.a.x.a((main.a)object);
        super.a(100);
        main.b.a.a();
        byte[] byArray = (byte[])b.a.q.get(new Integer(b.a.x.c));
        object = byArray;
        if (byArray != null) {
            b.a.x.a((byte[])object);
        }
        b.a.x.o();
        b.a.x.p();
        super.b(10);
        main.b.a.a();
        m.i();
        b.a.x.s();
        System.gc();
        long l3 = 2000L - (System.currentTimeMillis() - l2);
        e.a.b.a(l3 <= 0L ? 100L : l3);
    }

    public final void a(k k2) {
    }

    public final void c(k k2) {
    }

    public final void b(k k2) {
    }
}

