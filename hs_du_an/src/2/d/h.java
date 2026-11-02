/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package d;

import c.i;
import c.k;
import c.t;
import c.u;
import d.a;
import e.a.c;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.b;

public final class h
extends a
implements main.a {
    public h() {
        super((byte)3);
    }

    public final void a(Graphics graphics) {
        d.a.c(graphics);
        super.b(graphics);
    }

    public final void a() {
        System.gc();
        e.a.b.a(100L);
        d.a.d(b.a.x);
    }

    public final void b() {
        super.b();
        t.a(null);
    }

    public final void a(int n2, int n3) {
        d.a.c();
        k k2 = (k)d.a.e().peek();
        k2.a(n2, n3);
        if (b.a.r != null) {
            b.a.r.a();
            if (b.a.r == null || b.a.r != null && b.a.r.b()) {
                b.a.r = null;
                b.a.k();
            }
        }
    }

    public final void b(k object) {
        if (b.a.r != null) {
            return;
        }
        if (object == b.a.x) {
            u[] uArray = new i[1];
            object = uArray;
            uArray[0] = new i();
            Image[] imageArray = new Image[1];
            c c2 = e.a.c.a("/c.pak");
            imageArray[0] = c2.b("s0.png");
            object = new t(imageArray, (u[])object, main.b.d, main.b.e);
            t.a(e.a.b.a("/bg.png"));
            ((k)object).a(this);
            d.a.d((k)object);
        }
    }

    public final void a(k k2) {
        k2.b();
    }

    public final void c(k k2) {
        d.a.e(k2);
    }
}

