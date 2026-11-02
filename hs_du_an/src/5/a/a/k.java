/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package a.a;

import a.a.a;
import a.a.g;
import a.a.n;
import a.c.b;
import javax.microedition.lcdui.Graphics;

public final class k
extends n {
    private int h = 0;
    private int i = 0;

    public k(int n2, int n3, int n4, int n5) {
        super(n2, n3, n4, n5);
    }

    public final void a(g g2) {
        super.a(g2);
        k k2 = this;
        k2.i = k2.r.size() == 1 ? k2.c : (k2.c - b.a.J.getWidth()) / (k2.r.size() - 1);
        if (g2 != null) {
            g2.b = 4;
            g2.c = b.a.J.getHeight();
            g2.d = this.c - 8;
            g2.e = this.d - g2.c - 8;
            g2.a();
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4 = n3;
        int n5 = n2;
        Graphics graphics2 = graphics;
        k k2 = g2;
        if (k2.s != null) {
            graphics2.setColor(2693133);
            graphics2.fillRect(n5, n4, k2.c, k2.d);
            a.c.b.a(graphics2, k2.s, 0, 0, k2.s.getWidth(), k2.s.getHeight(), 2, n5 + k2.c / 2, n4 + k2.d / 2, 10);
            a.c.b.a(graphics2, k2.s, 0, 0, k2.s.getWidth(), k2.s.getHeight(), 0, n5 + k2.c / 2, n4 + k2.d / 2, 6);
        } else {
            graphics2.setColor(2890254);
            graphics2.fillRect(n5, n4 + b.a.J.getHeight(), k2.c, k2.d - b.a.J.getHeight());
        }
        int n6 = ((k)((Object)g2)).h;
        int n7 = ((a)((Object)g2)).c;
        n4 = n3;
        n5 = n2;
        graphics2 = graphics;
        k2 = g2;
        if (k2.r.size() != 0) {
            int n8 = k2.r.size();
            String string = null;
            if (k2.i < b.a.J.getWidth()) {
                for (int i2 = n8 - 1; i2 >= 0; --i2) {
                    if (n6 == i2) continue;
                    string = ((g)k2.r.elementAt((int)i2)).f;
                    graphics2.drawImage(b.a.K, n5 + n7 - (n8 - 1 - i2) * k2.i, n4, 24);
                    graphics2.setColor(0xFFFFFF);
                    graphics2.drawString(string, n5 + n7 - (k2.r.size() - 1 - i2) * k2.i - b.a.J.getWidth() / 2, n4, 17);
                }
                string = ((g)k2.r.elementAt((int)n6)).f;
                graphics2.drawImage(b.a.J, n5 + n7 - (n8 - 1 - n6) * k2.i, n4, 24);
                graphics2.setColor(0xFFFF00);
                graphics2.drawString(string, n5 + n7 - (n8 - 1 - n6) * k2.i - b.a.J.getWidth() / 2, n4, 17);
            } else {
                for (int i3 = 0; i3 < n8; ++i3) {
                    if (n6 == i3) {
                        graphics2.drawImage(b.a.J, n5 + i3 * b.a.J.getWidth(), n4, 20);
                        graphics2.setColor(0xFFFF00);
                    } else {
                        graphics2.drawImage(b.a.K, n5 + i3 * b.a.K.getWidth(), n4, 20);
                        graphics2.setColor(0xFFFFFF);
                    }
                    string = ((g)k2.r.elementAt((int)i3)).f;
                    graphics2.drawString(string, n5 + i3 * b.a.J.getWidth() + b.a.J.getWidth() / 2, n4 - a.c.b.b / 2 + b.a.J.getHeight() / 2, 17);
                }
            }
        }
        ((n)((Object)g2)).a(graphics, n2, n3 + b.a.J.getHeight(), ((a)((Object)g2)).c, ((a)((Object)g2)).d - b.a.J.getHeight());
        if (((k)((Object)g2)).h < ((n)((Object)g2)).r.size()) {
            g g2 = (g)((n)((Object)g2)).r.elementAt(((k)((Object)g2)).h);
            g2.a(graphics, n2 + 4, n3 + b.a.J.getHeight() + 4);
        }
    }

    public final String c() {
        return null;
    }

    public final void b(int n2, int n3) {
        if (((n)((Object)g2)).r.size() == 0) {
            return;
        }
        switch (n2) {
            case 52: {
                ((k)((Object)g2)).h = (((k)((Object)g2)).h - 1 + ((n)((Object)g2)).r.size()) % ((n)((Object)g2)).r.size();
                return;
            }
            case 54: {
                ((k)((Object)g2)).h = (((k)((Object)g2)).h + 1) % ((n)((Object)g2)).r.size();
                return;
            }
        }
        g g2 = (g)((n)((Object)g2)).r.elementAt(((k)((Object)g2)).h);
        g2.a(n2, n3);
    }

    protected final void g() {
        if (((n)((Object)g2)).r.size() == 0) {
            return;
        }
        g g2 = (g)((n)((Object)g2)).r.elementAt(((k)((Object)g2)).h);
        g2.b();
    }
}

