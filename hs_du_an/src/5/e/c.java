/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package e;

import a.a.l;
import a.a.n;
import a.c.b;
import a.c.d;
import b.a;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class c
extends n {
    private static final String[] t = new String[]{b.a.o[7], b.a.o[8], b.a.o[0], b.a.o[9], "\u4efb\u52a1", b.a.o[11]};
    private int u = 0;
    public Image h = null;
    public Image i = null;
    private Image v = null;
    private Image w = null;

    public c(int n2, int n3, int n4, int n5) {
        super(0, 0, n4, 0);
        d d2 = a.c.d.a("/c.pak");
        this.h = d2.c("mmbg.png");
        this.i = d2.c("mmtext.png");
        this.v = d2.c("mmp.png");
        this.w = d2.c("mm2.png");
        this.d = this.q = this.h.getHeight();
        this.a = this.l + this.m;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        graphics.drawImage(this.h, n2, n3, 20);
        graphics.drawImage(this.v, n2 + (this.u + 1) * this.c / (t.length + 1), n3 + this.d / 2, 3);
        for (int i2 = 0; i2 < t.length; ++i2) {
            a.c.b.a(graphics, this.i, this.i.getWidth() / t.length * i2, 0, this.i.getWidth() / t.length, this.i.getHeight(), 0, n2 + (i2 + 1) * this.c / (t.length + 1), n3 + this.d / 2, 3);
        }
        graphics.setColor(0);
        graphics.drawRect(n2, n3, this.c - 1, this.d - 1);
        graphics.setColor(8673569);
        graphics.drawRect(n2 + 1, n3 + 1, this.c - 3, this.d - 3);
        graphics.setColor(13677668);
        graphics.drawRect(n2 + 2, n3 + 2, this.c - 5, this.d - 5);
        graphics.setColor(5386256);
        graphics.drawRect(n2 + 3, n3 + 3, this.c - 7, this.d - 7);
        a.c.b.a(graphics, a.a.l.h, 0, 0, a.a.l.h.getWidth(), a.a.l.h.getHeight(), 0, n2, n3, 20);
        a.c.b.a(graphics, a.a.l.h, 0, 0, a.a.l.h.getWidth(), a.a.l.h.getHeight(), 2, n2 + this.c, n3, 24);
        a.c.b.a(graphics, a.a.l.h, 0, 0, a.a.l.h.getWidth(), a.a.l.h.getHeight(), 6, n2, n3 + this.d - 1, 36);
        a.c.b.a(graphics, a.a.l.h, 0, 0, a.a.l.h.getWidth(), a.a.l.h.getHeight(), 3, n2 + this.c, n3 + this.d - 1, 40);
        graphics.drawImage(this.w, n2 + this.c / 2, n3, 17);
        graphics.setColor(11901522);
        graphics.drawLine(n2 + 4, n3 + 4, n2 + 4, n3 + this.d - 7);
        graphics.drawLine(n2 + this.c - 4, n3 + 4, n2 + this.c - 4, n3 + this.d - 7);
        graphics.setColor(8673569);
        graphics.drawLine(n2 + 5, n3 + 4, n2 + 5, n3 + this.d - 7);
        graphics.drawLine(n2 + this.c - 5, n3 + 4, n2 + this.c - 5, n3 + this.d - 7);
        graphics.setColor(0);
        graphics.drawLine(n2 + 6, n3 + 4, n2 + 6, n3 + this.d - 7);
        graphics.drawLine(n2 + this.c - 6, n3 + 4, n2 + this.c - 6, n3 + this.d - 7);
    }

    public final String c() {
        return t[this.u];
    }

    protected final void g() {
    }

    public final void a(int n2, int n3) {
        if (this.j != 2) {
            return;
        }
        switch (n2) {
            case -7: {
                this.b();
                return;
            }
            case 52: {
                this.u = (this.u - 1 + t.length) % t.length;
                return;
            }
            case 54: {
                this.u = (this.u + 1) % t.length;
                return;
            }
            case -6: 
            case 53: {
                super.d();
            }
        }
    }

    public final void b() {
        super.b();
        this.n = this.l + this.m;
    }

    public final void b(int n2, int n3) {
    }

    static {
        int[] nArray = new int[]{15165513, 11688241, 11688241, 9588245};
    }
}

