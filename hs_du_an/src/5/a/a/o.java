/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package a.a;

import a.a.g;
import a.a.n;
import a.c.b;
import javax.microedition.lcdui.Graphics;

public final class o
extends n {
    public o(int n2, int n3, int n4, int n5) {
        super(n2, n3, n4, n5);
    }

    public final void b(int n2, int n3) {
        if (((n)((Object)g2)).r.size() == 0) {
            return;
        }
        g g2 = (g)((n)((Object)g2)).r.elementAt(((n)((Object)g2)).r.size() - 1);
        g2.a(n2, n3);
    }

    protected final void g() {
        if (((n)((Object)g2)).r.size() == 0) {
            return;
        }
        g g2 = (g)((n)((Object)g2)).r.elementAt(((n)((Object)g2)).r.size() - 1);
        g2.b();
    }

    public final String c() {
        return null;
    }

    public final void a(g g2) {
        super.a(g2);
        if (g2 != null) {
            g2.b = 4;
            g2.c = 4;
            g2.d = this.c - 8;
            g2.e = this.d - 8;
            g2.a();
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4 = n3;
        int n5 = n2;
        Graphics graphics2 = graphics;
        o o2 = this;
        if (o2.s != null) {
            graphics2.setColor(2693133);
            graphics2.fillRect(n5, n4, o2.c, o2.d);
            a.c.b.a(graphics2, o2.s, 0, 0, o2.s.getWidth(), o2.s.getHeight(), 2, n5 + o2.c / 2, n4 + o2.d / 2, 10);
            a.c.b.a(graphics2, o2.s, 0, 0, o2.s.getWidth(), o2.s.getHeight(), 0, n5 + o2.c / 2, n4 + o2.d / 2, 6);
        } else {
            graphics2.setColor(2890254);
            graphics2.fillRect(n5, n4, o2.c, o2.d);
        }
        this.a(graphics, n2, n3, this.c, this.d);
        super.a(graphics, n2, n3);
    }
}

