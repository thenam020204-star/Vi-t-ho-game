/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package b;

import b.c;
import b.g;
import b.t;
import d.a;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.b;
import main.d;

public final class s
extends g {
    private Image[] e = null;
    private int f = 0;
    private t[] g = null;
    private int h = 0;
    protected Image d = null;

    public s(Image[] imageArray, t[] tArray, int n2, int n3, d d2) {
        super((byte)4);
        this.n = n2;
        this.o = n3;
        this.b = (main.b.c - n2) / 2;
        this.m = this.c = (main.b.d - n3) / 2;
        this.e = imageArray;
        this.f = imageArray.length == 1 ? n2 : (n2 - 8 - d.a.N.getWidth()) / (imageArray.length - 1);
        this.g = tArray;
        for (int i2 = 0; i2 < tArray.length; ++i2) {
            tArray[i2].a(this);
            tArray[i2].a_(n2 - 8);
            tArray[i2].a(n3 - d.a.N.getHeight() - 8);
        }
        this.j = d2;
        this.c();
    }

    public final void a(Image image) {
        this.d = image;
    }

    public final void e() {
        this.e = null;
    }

    private void c() {
        if (this.g == null || this.h < 0 || this.h >= this.g.length || this.g[this.h] == null) {
            return;
        }
        this.g[this.h].e();
    }

    public final void a(int n2, int n3) {
        if (this.a != 1) {
            return;
        }
        switch (n2) {
            case 52: {
                this.h = (this.h - 1 + this.e.length) % this.e.length;
                this.c();
                return;
            }
            case 54: {
                this.h = (this.h + 1) % this.e.length;
                this.c();
                return;
            }
        }
        if (this.g == null || this.h < 0 || this.h >= this.g.length || this.g[this.h] == null) {
            return;
        }
        this.g[this.h].a(n2, n3);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4 = n3;
        int n5 = n2;
        Graphics graphics2 = graphics;
        s s2 = this;
        if (s2.d != null) {
            graphics2.drawImage(s2.d, n5, n4, 20);
        } else {
            graphics2.setColor(2890254);
            graphics2.fillRect(n5, n4 + d.a.N.getHeight(), s2.n, s2.o - d.a.N.getHeight());
        }
        int n6 = this.h;
        int n7 = this.n - 8;
        n4 = n3;
        n5 = n2 + 4;
        graphics2 = graphics;
        s2 = this;
        if (s2.e != null) {
            if (s2.f < d.a.N.getWidth()) {
                for (int i2 = s2.e.length - 1; i2 >= 0; --i2) {
                    if (n6 == i2) continue;
                    graphics2.drawImage(d.a.O, n5 + n7 - (s2.e.length - 1 - i2) * s2.f, n4, 24);
                    graphics2.drawImage(s2.e[i2], n5 + n7 - (s2.e.length - 1 - i2) * s2.f - d.a.N.getWidth() / 2, n4 + d.a.N.getHeight() / 2, 3);
                }
                graphics2.drawImage(d.a.N, n5 + n6 * s2.f, n4, 20);
                graphics2.drawImage(s2.e[n6], n5 + n6 * s2.f + d.a.N.getWidth() / 2, n4 + d.a.N.getHeight() / 2, 3);
            } else {
                for (int i3 = 0; i3 < s2.e.length; ++i3) {
                    if (n6 == i3) {
                        graphics2.drawImage(d.a.N, n5 + i3 * d.a.N.getWidth(), n4, 20);
                    } else {
                        graphics2.drawImage(d.a.O, n5 + i3 * d.a.O.getWidth(), n4, 20);
                    }
                    graphics2.drawImage(s2.e[i3], n5 + i3 * d.a.N.getWidth() + d.a.N.getWidth() / 2, n4 + d.a.N.getHeight() / 2, 3);
                }
            }
        }
        n4 = n3 + d.a.N.getHeight() + 4;
        n5 = n2 + 4;
        graphics2 = graphics;
        s2 = this;
        if (s2.g != null && s2.h >= 0 && s2.h < s2.g.length && s2.g[s2.h] != null) {
            s2.g[s2.h].a(graphics2, n5, n4);
        }
        n4 = n3 + d.a.N.getHeight();
        n5 = n2;
        graphics2 = graphics;
        s2 = this;
        n7 = s2.o - d.a.N.getHeight();
        graphics2.setColor(3739672);
        graphics2.drawRect(n5, n4, s2.n - 1, n7 - 1);
        graphics2.setColor(6373665);
        graphics2.drawRect(n5 + 1, n4 + 1, s2.n - 3, n7 - 3);
        graphics2.drawRect(n5 + 2, n4 + 2, s2.n - 5, n7 - 5);
        graphics2.setColor(7885355);
        graphics2.drawRect(n5 + 3, n4 + 3, s2.n - 7, n7 - 7);
        h.b.a(graphics2, b.c.a, 0, 0, b.c.a.getWidth(), b.c.a.getHeight(), 0, n5, n4, 20);
        h.b.a(graphics2, b.c.a, 0, 0, b.c.a.getWidth(), b.c.a.getHeight(), 2, n5 + s2.n, n4, 24);
        h.b.a(graphics2, b.c.a, 0, 0, b.c.a.getWidth(), b.c.a.getHeight(), 6, n5, n4 + n7, 36);
        h.b.a(graphics2, b.c.a, 0, 0, b.c.a.getWidth(), b.c.a.getHeight(), 3, n5 + s2.n, n4 + n7, 40);
    }

    public final String d() {
        return null;
    }
}

