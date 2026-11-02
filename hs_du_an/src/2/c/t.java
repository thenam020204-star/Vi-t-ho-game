/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package c;

import b.a;
import c.b;
import c.g;
import c.u;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class t
extends g {
    private Image[] m = null;
    private int n = 0;
    private u[] o = null;
    private int p = 0;
    protected static Image l = null;

    public t(Image[] imageArray, u[] uArray, int n2, int n3) {
        super((byte)4);
        this.g = n2;
        this.h = n3;
        this.b = (main.b.d - n2) / 2;
        this.f = this.c = (main.b.e - n3) / 2;
        this.m = imageArray;
        this.n = imageArray.length == 1 ? n2 : (n2 - 8 - b.a.M.getWidth()) / (imageArray.length - 1);
        this.o = uArray;
        for (int i2 = 0; i2 < uArray.length; ++i2) {
            uArray[i2].a(this);
            uArray[i2].a_(n2 - 8);
            uArray[i2].a(n3 - b.a.M.getHeight() - 8);
        }
        this.c();
    }

    public static void a(Image image) {
        l = image;
    }

    public final void e() {
        this.m = null;
    }

    private void c() {
        if (this.o == null || this.p < 0 || this.p >= this.o.length || this.o[this.p] == null) {
            return;
        }
        this.o[this.p].e();
    }

    public final void a(int n2, int n3) {
        if (this.a != 1) {
            return;
        }
        switch (n2) {
            case 52: {
                this.p = (this.p - 1 + this.m.length) % this.m.length;
                this.c();
                return;
            }
            case 54: {
                this.p = (this.p + 1) % this.m.length;
                this.c();
                return;
            }
        }
        if (this.o == null || this.p < 0 || this.p >= this.o.length || this.o[this.p] == null) {
            return;
        }
        this.o[this.p].a(n2, n3);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4 = n3;
        int n5 = n2;
        Graphics graphics2 = graphics;
        t t2 = this;
        if (l != null) {
            graphics2.setColor(2693133);
            graphics2.fillRect(n5, n4, t2.g, t2.h);
            e.a.b.a(graphics2, l, 0, 0, l.getWidth(), l.getHeight(), 2, n5 + t2.g / 2, n4 + t2.h / 2, 10);
            e.a.b.a(graphics2, l, 0, 0, l.getWidth(), l.getHeight(), 0, n5 + t2.g / 2, n4 + t2.h / 2, 6);
        } else {
            graphics2.setColor(2890254);
            graphics2.fillRect(n5, n4 + b.a.M.getHeight(), t2.g, t2.h - b.a.M.getHeight());
        }
        int n6 = this.p;
        int n7 = this.g - 8;
        n4 = n3;
        n5 = n2 + 4;
        graphics2 = graphics;
        t2 = this;
        if (t2.m != null) {
            if (t2.n < b.a.M.getWidth()) {
                for (int i2 = t2.m.length - 1; i2 >= 0; --i2) {
                    if (n6 == i2) continue;
                    graphics2.drawImage(b.a.N, n5 + n7 - (t2.m.length - 1 - i2) * t2.n, n4, 24);
                    graphics2.drawImage(t2.m[i2], n5 + n7 - (t2.m.length - 1 - i2) * t2.n - b.a.M.getWidth() / 2, n4 + b.a.M.getHeight() / 2, 3);
                }
                graphics2.drawImage(b.a.M, n5 + n6 * t2.n, n4, 20);
                graphics2.drawImage(t2.m[n6], n5 + n6 * t2.n + b.a.M.getWidth() / 2, n4 + b.a.M.getHeight() / 2, 3);
            } else {
                for (int i3 = 0; i3 < t2.m.length; ++i3) {
                    if (n6 == i3) {
                        graphics2.drawImage(b.a.M, n5 + i3 * b.a.M.getWidth(), n4, 20);
                    } else {
                        graphics2.drawImage(b.a.N, n5 + i3 * b.a.N.getWidth(), n4, 20);
                    }
                    graphics2.drawImage(t2.m[i3], n5 + i3 * b.a.M.getWidth() + b.a.M.getWidth() / 2, n4 + b.a.M.getHeight() / 2, 3);
                }
            }
        }
        n4 = n3 + b.a.M.getHeight() + 4;
        n5 = n2 + 4;
        graphics2 = graphics;
        t2 = this;
        if (t2.o != null && t2.p >= 0 && t2.p < t2.o.length && t2.o[t2.p] != null) {
            t2.o[t2.p].a(graphics2, n5, n4);
        }
        n4 = n3 + b.a.M.getHeight();
        n5 = n2;
        graphics2 = graphics;
        t2 = this;
        n7 = t2.h - b.a.M.getHeight();
        graphics2.setColor(3739672);
        graphics2.drawRect(n5, n4, t2.g - 1, n7 - 1);
        graphics2.setColor(6373665);
        graphics2.drawRect(n5 + 1, n4 + 1, t2.g - 3, n7 - 3);
        graphics2.drawRect(n5 + 2, n4 + 2, t2.g - 5, n7 - 5);
        graphics2.setColor(7885355);
        graphics2.drawRect(n5 + 3, n4 + 3, t2.g - 7, n7 - 7);
        e.a.b.a(graphics2, c.b.a, 0, 0, c.b.a.getWidth(), c.b.a.getHeight(), 0, n5, n4, 20);
        e.a.b.a(graphics2, c.b.a, 0, 0, c.b.a.getWidth(), c.b.a.getHeight(), 2, n5 + t2.g, n4, 24);
        e.a.b.a(graphics2, c.b.a, 0, 0, c.b.a.getWidth(), c.b.a.getHeight(), 6, n5, n4 + n7, 36);
        e.a.b.a(graphics2, c.b.a, 0, 0, c.b.a.getWidth(), c.b.a.getHeight(), 3, n5 + t2.g, n4 + n7, 40);
    }

    public final String d() {
        return null;
    }
}

