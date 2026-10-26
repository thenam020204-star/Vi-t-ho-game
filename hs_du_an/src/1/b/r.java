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
import e.a;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.b;

public final class r
extends g {
    private static final String[] d = new String[]{d.a.q[7], d.a.q[8], d.a.q[0], d.a.q[9], d.a.q[11]};
    private int e = 0;
    private Image f = null;
    private Image g = null;
    private Image h = null;
    private Image r = null;

    public r() {
        super((byte)4);
        a a2 = e.a.a("/c.pak");
        this.f = a2.c("mmbg.png");
        this.g = a2.c("mmtext.png");
        this.h = a2.c("mmp.png");
        this.r = a2.c("mm2.png");
        this.n = main.b.c;
        this.o = this.f.getHeight();
    }

    public final void e() {
        this.f = null;
        this.g = null;
        this.h = null;
        this.r = null;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        graphics.drawImage(this.f, n2, n3, 20);
        graphics.drawImage(this.h, n2 + (this.e + 1) * this.n / (d.length + 1), n3 + this.o / 2, 3);
        for (int i2 = 0; i2 < d.length; ++i2) {
            h.b.a(graphics, this.g, this.g.getWidth() / d.length * i2, 0, this.g.getWidth() / d.length, this.g.getHeight(), n2 + (i2 + 1) * this.n / (d.length + 1), n3 + this.o / 2, 3);
        }
        graphics.setColor(0);
        graphics.drawRect(n2, n3, this.n - 1, this.o - 1);
        graphics.setColor(8673569);
        graphics.drawRect(n2 + 1, n3 + 1, this.n - 3, this.o - 3);
        graphics.setColor(13677668);
        graphics.drawRect(n2 + 2, n3 + 2, this.n - 5, this.o - 5);
        graphics.setColor(5386256);
        graphics.drawRect(n2 + 3, n3 + 3, this.n - 7, this.o - 7);
        h.b.a(graphics, b.c.a, 0, 0, b.c.a.getWidth(), b.c.a.getHeight(), 0, n2, n3, 20);
        h.b.a(graphics, b.c.a, 0, 0, b.c.a.getWidth(), b.c.a.getHeight(), 2, n2 + this.n, n3, 24);
        h.b.a(graphics, b.c.a, 0, 0, b.c.a.getWidth(), b.c.a.getHeight(), 6, n2, n3 + this.o - 1, 36);
        h.b.a(graphics, b.c.a, 0, 0, b.c.a.getWidth(), b.c.a.getHeight(), 3, n2 + this.n, n3 + this.o - 1, 40);
        graphics.drawImage(this.r, n2 + this.n / 2, n3, 17);
        graphics.setColor(11901522);
        graphics.drawLine(n2 + 4, n3 + 4, n2 + 4, n3 + this.o - 7);
        graphics.drawLine(n2 + this.n - 4, n3 + 4, n2 + this.n - 4, n3 + this.o - 7);
        graphics.setColor(8673569);
        graphics.drawLine(n2 + 5, n3 + 4, n2 + 5, n3 + this.o - 7);
        graphics.drawLine(n2 + this.n - 5, n3 + 4, n2 + this.n - 5, n3 + this.o - 7);
        graphics.setColor(0);
        graphics.drawLine(n2 + 6, n3 + 4, n2 + 6, n3 + this.o - 7);
        graphics.drawLine(n2 + this.n - 6, n3 + 4, n2 + this.n - 6, n3 + this.o - 7);
    }

    public final String d() {
        return d[this.e];
    }

    public final void a(int n2, int n3) {
        if (this.a != 1) {
            return;
        }
        switch (n2) {
            case -7: {
                this.b();
                return;
            }
            case 52: {
                this.e = (this.e - 1 + d.length) % d.length;
                return;
            }
            case 54: {
                this.e = (this.e + 1) % d.length;
                return;
            }
            case -6: 
            case 53: {
                super.n();
            }
        }
    }
}

