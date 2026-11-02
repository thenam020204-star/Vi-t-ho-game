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
    private int a = 0;
    private int b = 0;

    public f(int n2, int n3, int n4, int n5) {
        super(n2, n3, n4, n5);
    }

    public final void a(l l2) {
        super.a(l2);
        f f2 = this;
        f2.b = f2.j.size() == 1 ? f2.e : (f2.e - a.f.J.getWidth()) / (f2.j.size() - 1);
        if (l2 != null) {
            l2.f = 4;
            l2.g = a.f.J.getHeight();
            l2.h = this.e - 8;
            l2.i = this.f - l2.g - 8;
            l2.a();
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4 = n3;
        int n5 = n2;
        Graphics graphics2 = graphics;
        f f2 = l2;
        if (f2.k != null) {
            graphics2.setColor(2693133);
            graphics2.fillRect(n5, n4, f2.e, f2.f);
            b.e.a.a(graphics2, f2.k, 0, 0, f2.k.getWidth(), f2.k.getHeight(), 2, n5 + f2.e / 2, n4 + f2.f / 2, 10);
            b.e.a.a(graphics2, f2.k, 0, 0, f2.k.getWidth(), f2.k.getHeight(), 0, n5 + f2.e / 2, n4 + f2.f / 2, 6);
        } else {
            graphics2.setColor(2890254);
            graphics2.fillRect(n5, n4 + a.f.J.getHeight(), f2.e, f2.f - a.f.J.getHeight());
        }
        int n6 = ((f)((Object)l2)).a;
        int n7 = ((c)((Object)l2)).e;
        n4 = n3;
        n5 = n2;
        graphics2 = graphics;
        f2 = l2;
        if (f2.j.size() != 0) {
            int n8 = f2.j.size();
            String string = null;
            if (f2.b < a.f.J.getWidth()) {
                for (int i2 = n8 - 1; i2 >= 0; --i2) {
                    if (n6 == i2) continue;
                    string = ((l)f2.j.elementAt((int)i2)).j;
                    graphics2.drawImage(a.f.K, n5 + n7 - (n8 - 1 - i2) * f2.b, n4, 24);
                    graphics2.setColor(0xFFFFFF);
                    graphics2.drawString(string, n5 + n7 - (f2.j.size() - 1 - i2) * f2.b - a.f.J.getWidth() / 2, n4, 17);
                }
                string = ((l)f2.j.elementAt((int)n6)).j;
                graphics2.drawImage(a.f.J, n5 + n7 - (n8 - 1 - n6) * f2.b, n4, 24);
                graphics2.setColor(0xFFFF00);
                graphics2.drawString(string, n5 + n7 - (n8 - 1 - n6) * f2.b - a.f.J.getWidth() / 2, n4, 17);
            } else {
                for (int i3 = 0; i3 < n8; ++i3) {
                    if (n6 == i3) {
                        graphics2.drawImage(a.f.J, n5 + i3 * a.f.J.getWidth(), n4, 20);
                        graphics2.setColor(0xFFFF00);
                    } else {
                        graphics2.drawImage(a.f.K, n5 + i3 * a.f.K.getWidth(), n4, 20);
                        graphics2.setColor(0xFFFFFF);
                    }
                    string = ((l)f2.j.elementAt((int)i3)).j;
                    graphics2.drawString(string, n5 + i3 * a.f.J.getWidth() + a.f.J.getWidth() / 2, n4 - b.e.a.b / 2 + a.f.J.getHeight() / 2, 17);
                }
            }
        }
        ((e)((Object)l2)).a(graphics, n2, n3 + a.f.J.getHeight(), ((c)((Object)l2)).e, ((c)((Object)l2)).f - a.f.J.getHeight());
        if (((f)((Object)l2)).a < ((e)((Object)l2)).j.size()) {
            l l2 = (l)((e)((Object)l2)).j.elementAt(((f)((Object)l2)).a);
            l2.a(graphics, n2 + 4, n3 + a.f.J.getHeight() + 4);
        }
    }

    public final String a() {
        return null;
    }

    public final void b(int n2, int n3) {
        if (((e)((Object)l2)).j.size() == 0) {
            return;
        }
        switch (n2) {
            case 52: {
                ((f)((Object)l2)).a = (((f)((Object)l2)).a - 1 + ((e)((Object)l2)).j.size()) % ((e)((Object)l2)).j.size();
                return;
            }
            case 54: {
                ((f)((Object)l2)).a = (((f)((Object)l2)).a + 1) % ((e)((Object)l2)).j.size();
                return;
            }
        }
        l l2 = (l)((e)((Object)l2)).j.elementAt(((f)((Object)l2)).a);
        l2.a(n2, n3);
    }

    protected final void b() {
        if (((e)((Object)l2)).j.size() == 0) {
            return;
        }
        l l2 = (l)((e)((Object)l2)).j.elementAt(((f)((Object)l2)).a);
        l2.b();
    }
}

