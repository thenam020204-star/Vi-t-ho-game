/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b.b;

import b.b.e;
import b.b.l;
import b.e.a;
import javax.microedition.lcdui.Graphics;

public final class h
extends e {
    public h(int n2, int n3, int n4, int n5) {
        super(n2, n3, n4, n5);
    }

    public final void b(int n2, int n3) {
        if (((e)((Object)l2)).h.size() == 0) {
            return;
        }
        l l2 = (l)((e)((Object)l2)).h.elementAt(((e)((Object)l2)).h.size() - 1);
        l2.a(n2, n3);
    }

    protected final void b() {
        if (((e)((Object)l2)).h.size() == 0) {
            return;
        }
        l l2 = (l)((e)((Object)l2)).h.elementAt(((e)((Object)l2)).h.size() - 1);
        l2.b();
    }

    public final String a() {
        return null;
    }

    public final void a(l l2) {
        super.a(l2);
        if (l2 != null) {
            l2.e = 4;
            l2.f = 4;
            l2.g = this.c - 8;
            l2.h = this.d - 8;
            l2.a();
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4 = n3;
        int n5 = n2;
        Graphics graphics2 = graphics;
        h h2 = this;
        if (h2.i != null) {
            graphics2.setColor(2693133);
            graphics2.fillRect(n5, n4, h2.c, h2.d);
            b.e.a.a(graphics2, h2.i, 0, 0, h2.i.getWidth(), h2.i.getHeight(), 2, n5 + h2.c / 2, n4 + h2.d / 2, 10);
            b.e.a.a(graphics2, h2.i, 0, 0, h2.i.getWidth(), h2.i.getHeight(), 0, n5 + h2.c / 2, n4 + h2.d / 2, 6);
        } else {
            graphics2.setColor(2890254);
            graphics2.fillRect(n5, n4, h2.c, h2.d);
        }
        this.a(graphics, n2, n3, this.c, this.d);
        super.a(graphics, n2, n3);
    }
}

