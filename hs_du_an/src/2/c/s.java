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
import e.a.c;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class s
extends g {
    private static final String[] l = new String[]{b.a.p[7], b.a.p[8], b.a.p[0], b.a.p[9], b.a.p[11]};
    private int m = 0;
    private Image n = null;
    private Image o = null;
    private Image p = null;
    private Image q = null;

    public s() {
        super((byte)4);
        c c2 = e.a.c.a("/c.pak");
        this.n = c2.b("mmbg.png");
        this.o = c2.b("mmtext.png");
        this.p = c2.b("mmp.png");
        this.q = c2.b("mm2.png");
        this.g = main.b.d;
        this.h = this.n.getHeight();
    }

    public final void e() {
        this.n = null;
        this.o = null;
        this.p = null;
        this.q = null;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        graphics.drawImage(this.n, n2, n3, 20);
        graphics.drawImage(this.p, n2 + (this.m + 1) * this.g / (l.length + 1), n3 + this.h / 2, 3);
        for (int i2 = 0; i2 < l.length; ++i2) {
            e.a.b.a(graphics, this.o, this.o.getWidth() / l.length * i2, 0, this.o.getWidth() / l.length, this.o.getHeight(), n2 + (i2 + 1) * this.g / (l.length + 1), n3 + this.h / 2, 3);
        }
        graphics.setColor(0);
        graphics.drawRect(n2, n3, this.g - 1, this.h - 1);
        graphics.setColor(8673569);
        graphics.drawRect(n2 + 1, n3 + 1, this.g - 3, this.h - 3);
        graphics.setColor(13677668);
        graphics.drawRect(n2 + 2, n3 + 2, this.g - 5, this.h - 5);
        graphics.setColor(5386256);
        graphics.drawRect(n2 + 3, n3 + 3, this.g - 7, this.h - 7);
        e.a.b.a(graphics, c.b.a, 0, 0, c.b.a.getWidth(), c.b.a.getHeight(), 0, n2, n3, 20);
        e.a.b.a(graphics, c.b.a, 0, 0, c.b.a.getWidth(), c.b.a.getHeight(), 2, n2 + this.g, n3, 24);
        e.a.b.a(graphics, c.b.a, 0, 0, c.b.a.getWidth(), c.b.a.getHeight(), 6, n2, n3 + this.h - 1, 36);
        e.a.b.a(graphics, c.b.a, 0, 0, c.b.a.getWidth(), c.b.a.getHeight(), 3, n2 + this.g, n3 + this.h - 1, 40);
        graphics.drawImage(this.q, n2 + this.g / 2, n3, 17);
        graphics.setColor(11901522);
        graphics.drawLine(n2 + 4, n3 + 4, n2 + 4, n3 + this.h - 7);
        graphics.drawLine(n2 + this.g - 4, n3 + 4, n2 + this.g - 4, n3 + this.h - 7);
        graphics.setColor(8673569);
        graphics.drawLine(n2 + 5, n3 + 4, n2 + 5, n3 + this.h - 7);
        graphics.drawLine(n2 + this.g - 5, n3 + 4, n2 + this.g - 5, n3 + this.h - 7);
        graphics.setColor(0);
        graphics.drawLine(n2 + 6, n3 + 4, n2 + 6, n3 + this.h - 7);
        graphics.drawLine(n2 + this.g - 6, n3 + 4, n2 + this.g - 6, n3 + this.h - 7);
    }

    public final String d() {
        return l[this.m];
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
                this.m = (this.m - 1 + l.length) % l.length;
                return;
            }
            case 54: {
                this.m = (this.m + 1) % l.length;
                return;
            }
            case -6: 
            case 53: {
                super.g();
            }
        }
    }
}

