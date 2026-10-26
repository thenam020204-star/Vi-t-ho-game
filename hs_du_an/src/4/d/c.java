/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package d;

import a.f;
import b.e.a;
import d.k;
import javax.microedition.lcdui.Graphics;

public final class c
extends k {
    public c() {
        super((byte)3);
    }

    public final void a(Graphics graphics) {
        d.k.c(graphics);
        super.b(graphics);
    }

    public final void a() {
        System.gc();
        a.a(100L);
        d.k.c(f.w);
    }

    public final void b() {
        super.b();
    }

    public final void a(int n2, int n3) {
        d.k.d();
        b.b.c c2 = (b.b.c)d.k.f().peek();
        c2.a(n2, n3);
        if (f.q != null) {
            f.q.a();
            if (f.q == null || f.q != null && f.q.b()) {
                f.q = null;
                f.k();
            }
        }
    }

    public final void a(b.b.c c2) {
        c2.c();
    }

    public final void b(b.b.c c2) {
        d.k.d(c2);
    }
}

