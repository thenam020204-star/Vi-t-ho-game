/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package c;

import a.f;
import b.b.e;
import b.b.g;
import b.e.a;
import b.e.d;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class b
extends e {
    private static final String[] r = new String[]{a.f.o[7], a.f.o[8], a.f.o[0], a.f.o[9], "\u4efb\u52a1", a.f.o[11]};
    private int s = 0;
    private Image t = null;
    private Image u = null;
    private Image v = null;
    private Image w = null;

    public b(int n2, int n3, int n4, int n5) {
        super(0, 0, n4, 0);
        d d2 = b.e.d.a("/c.pak");
        this.t = d2.c("mmbg.png");
        this.u = d2.c("mmtext.png");
        this.v = d2.c("mmp.png");
        this.w = d2.c("mm2.png");
        this.d = this.q = this.t.getHeight();
        this.a = this.l + this.m;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        graphics.drawImage(this.t, n2, n3, 20);
        graphics.drawImage(this.v, n2 + (this.s + 1) * this.c / (r.length + 1), n3 + this.d / 2, 3);
        for (int i2 = 0; i2 < r.length; ++i2) {
            b.e.a.a(graphics, this.u, this.u.getWidth() / r.length * i2, 0, this.u.getWidth() / r.length, this.u.getHeight(), 0, n2 + (i2 + 1) * this.c / (r.length + 1), n3 + this.d / 2, 3);
        }
        graphics.setColor(0);
        graphics.drawRect(n2, n3, this.c - 1, this.d - 1);
        graphics.setColor(8673569);
        graphics.drawRect(n2 + 1, n3 + 1, this.c - 3, this.d - 3);
        graphics.setColor(13677668);
        graphics.drawRect(n2 + 2, n3 + 2, this.c - 5, this.d - 5);
        graphics.setColor(5386256);
        graphics.drawRect(n2 + 3, n3 + 3, this.c - 7, this.d - 7);
        b.e.a.a(graphics, b.b.g.h, 0, 0, b.b.g.h.getWidth(), b.b.g.h.getHeight(), 0, n2, n3, 20);
        b.e.a.a(graphics, b.b.g.h, 0, 0, b.b.g.h.getWidth(), b.b.g.h.getHeight(), 2, n2 + this.c, n3, 24);
        b.e.a.a(graphics, b.b.g.h, 0, 0, b.b.g.h.getWidth(), b.b.g.h.getHeight(), 6, n2, n3 + this.d - 1, 36);
        b.e.a.a(graphics, b.b.g.h, 0, 0, b.b.g.h.getWidth(), b.b.g.h.getHeight(), 3, n2 + this.c, n3 + this.d - 1, 40);
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

    public final String a() {
        return r[this.s];
    }

    protected final void b() {
    }

    public final void a(int n2, int n3) {
        if (this.j != 2) {
            return;
        }
        switch (n2) {
            case -7: {
                this.c();
                return;
            }
            case 52: {
                this.s = (this.s - 1 + r.length) % r.length;
                return;
            }
            case 54: {
                this.s = (this.s + 1) % r.length;
                return;
            }
            case -6: 
            case 53: {
                super.e();
            }
        }
    }

    public final void c() {
        super.c();
        this.n = this.l + this.m;
    }

    public final void b(int n2, int n3) {
    }

    static {
        int[] nArray = new int[]{15165513, 11688241, 11688241, 9588245};
    }
}

