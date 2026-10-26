/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package d;

import a.f;
import b.f.b;
import d.c;
import d.k;
import javax.microedition.lcdui.Graphics;
import main.a;

public final class j
extends k {
    public j() {
        super((byte)2);
    }

    public final void b() {
        super.b();
        f.x = -1;
        f.y = 0;
        f.z = 0;
    }

    private static void c() {
        b b2 = f.w.c((short)-2);
        if (b2 != null) {
            while (!b2.b()) {
                f.w.d();
                b2.a();
            }
        }
        f.q = f.w.c((short)-1);
    }

    public final void a(Graphics graphics) {
        d.k.c(graphics);
        d.k.a(graphics, this.j);
    }

    public final void a() {
        f.h();
        f.w = null;
        f.q = null;
        f.k();
        f.ah = false;
        System.gc();
        b.e.a.a(100L);
    }

    public final void a(int n2, int n3) {
        f.a();
        super.a(30);
        a.a.a();
        long l2 = System.currentTimeMillis();
        f.w = new b.c.a(f.x);
        for (n2 = 0; n2 < f.A.length; ++n2) {
            f.A[n2].a(f.A[n2].r, false, true);
        }
        super.b(10);
        a.a.a();
        for (n2 = 0; n2 < f.A.length; ++n2) {
            f.A[n2].s = f.y << 4;
            f.A[n2].t = f.z << 4;
        }
        f.u = 1;
        f.v = f.A[0].n;
        f.w.a(f.A[0]);
        super.b(10);
        a.a.a();
        Object object = new c();
        this.a((k)object);
        f.w.a((b.b.j)object);
        super.a(100);
        a.a.a();
        byte[] byArray = (byte[])f.p.get(new Integer(f.w.k));
        object = byArray;
        if (byArray != null) {
            f.w.a((byte[])object);
        }
        f.w.m();
        f.w.n();
        super.b(10);
        a.a.a();
        d.j.c();
        f.w.b();
        System.gc();
        long l3 = 2000L - (System.currentTimeMillis() - l2);
        b.e.a.a(l3 <= 0L ? 100L : l3);
    }

    public final void a(b.b.c c2) {
    }

    public final void b(b.b.c c2) {
    }
}

