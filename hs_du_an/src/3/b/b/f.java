/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b.b;

import b.b.c;
import b.b.e;
import b.b.l;
import b.e.a;
import javax.microedition.lcdui.Graphics;

public final class f
extends e {
    private int r = 0;
    private int s = 0;

    public f(int n2, int n3, int n4, int n5) {
        super(n2, n3, n4, n5);
    }

    public final void a(l l2) {
        super.a(l2);
        f f2 = this;
        f2.s = f2.h.size() == 1 ? f2.c : (f2.c - a.f.J.getWidth()) / (f2.h.size() - 1);
        if (l2 != null) {
            l2.e = 4;
            l2.f = a.f.J.getHeight();
            l2.g = this.c - 8;
            l2.h = this.d - l2.f - 8;
            l2.a();
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4 = n3;
        int n5 = n2;
        Graphics graphics2 = graphics;
        f f2 = l2;
        if (f2.i != null) {
            graphics2.setColor(2693133);
            graphics2.fillRect(n5, n4, f2.c, f2.d);
            b.e.a.a(graphics2, f2.i, 0, 0, f2.i.getWidth(), f2.i.getHeight(), 2, n5 + f2.c / 2, n4 + f2.d / 2, 10);
            b.e.a.a(graphics2, f2.i, 0, 0, f2.i.getWidth(), f2.i.getHeight(), 0, n5 + f2.c / 2, n4 + f2.d / 2, 6);
        } else {
            graphics2.setColor(2890254);
            graphics2.fillRect(n5, n4 + a.f.J.getHeight(), f2.c, f2.d - a.f.J.getHeight());
        }
        int n6 = ((f)((Object)l2)).r;
        int n7 = ((c)((Object)l2)).c;
        n4 = n3;
        n5 = n2;
        graphics2 = graphics;
        f2 = l2;
        if (f2.h.size() != 0) {
            int n8 = f2.h.size();
            String string = null;
            if (f2.s < a.f.J.getWidth()) {
                for (int i2 = n8 - 1; i2 >= 0; --i2) {
                    if (n6 == i2) continue;
                    string = ((l)f2.h.elementAt((int)i2)).i;
                    graphics2.drawImage(a.f.K, n5 + n7 - (n8 - 1 - i2) * f2.s, n4, 24);
                    graphics2.setColor(0xFFFFFF);
                    graphics2.drawString(string, n5 + n7 - (f2.h.size() - 1 - i2) * f2.s - a.f.J.getWidth() / 2, n4, 17);
                }
                string = ((l)f2.h.elementAt((int)n6)).i;
                graphics2.drawImage(a.f.J, n5 + n7 - (n8 - 1 - n6) * f2.s, n4, 24);
                graphics2.setColor(0xFFFF00);
                graphics2.drawString(string, n5 + n7 - (n8 - 1 - n6) * f2.s - a.f.J.getWidth() / 2, n4, 17);
            } else {
                for (int i3 = 0; i3 < n8; ++i3) {
                    if (n6 == i3) {
                        graphics2.drawImage(a.f.J, n5 + i3 * a.f.J.getWidth(), n4, 20);
                        graphics2.setColor(0xFFFF00);
                    } else {
                        graphics2.drawImage(a.f.K, n5 + i3 * a.f.K.getWidth(), n4, 20);
                        graphics2.setColor(0xFFFFFF);
                    }
                    string = ((l)f2.h.elementAt((int)i3)).i;
                    graphics2.drawString(string, n5 + i3 * a.f.J.getWidth() + a.f.J.getWidth() / 2, n4 - b.e.a.b / 2 + a.f.J.getHeight() / 2, 17);
                }
            }
        }
        ((e)((Object)l2)).a(graphics, n2, n3 + a.f.J.getHeight(), ((c)((Object)l2)).c, ((c)((Object)l2)).d - a.f.J.getHeight());
        if (((f)((Object)l2)).r < ((e)((Object)l2)).h.size()) {
            l l2 = (l)((e)((Object)l2)).h.elementAt(((f)((Object)l2)).r);
            l2.a(graphics, n2 + 4, n3 + a.f.J.getHeight() + 4);
        }
    }

    public final String a() {
        return null;
    }

    public final void b(int n2, int n3) {
        if (((e)((Object)l2)).h.size() == 0) {
            return;
        }
        switch (n2) {
            case 52: {
                ((f)((Object)l2)).r = (((f)((Object)l2)).r - 1 + ((e)((Object)l2)).h.size()) % ((e)((Object)l2)).h.size();
                return;
            }
            case 54: {
                ((f)((Object)l2)).r = (((f)((Object)l2)).r + 1) % ((e)((Object)l2)).h.size();
                return;
            }
        }
        l l2 = (l)((e)((Object)l2)).h.elementAt(((f)((Object)l2)).r);
        l2.a(n2, n3);
    }

    protected final void b() {
        if (((e)((Object)l2)).h.size() == 0) {
            return;
        }
        l l2 = (l)((e)((Object)l2)).h.elementAt(((f)((Object)l2)).r);
        l2.b();
    }
}

