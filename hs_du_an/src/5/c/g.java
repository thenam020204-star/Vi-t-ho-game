/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package c;

import a.c.b;
import c.a;
import javax.microedition.lcdui.Graphics;

public final class g
extends a {
    public g() {
        super((byte)3);
    }

    public final void a(Graphics graphics) {
        c.a.c(graphics);
        super.b(graphics);
    }

    public final void a() {
        System.gc();
        a.c.b.a(100L);
        c.a.a(b.a.w);
    }

    public final void b() {
        super.b();
    }

    public final void a(int n2, int n3) {
        c.a.c();
        a.a.a a2 = (a.a.a)c.a.e().peek();
        a2.a(n2, n3);
        if (b.a.q != null) {
            b.a.q.a();
            if (b.a.q == null || b.a.q != null && b.a.q.b()) {
                b.a.q = null;
                b.a.k();
            }
        }
    }

    public final void d(a.a.a a2) {
        a2.b();
    }

    public final void c(a.a.a a2) {
        c.a.b(a2);
    }
}

