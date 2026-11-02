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
    private static final String[] t = new String[]{a.f.o[7], a.f.o[8], a.f.o[0], a.f.o[9], "\u4efb\u52a1", a.f.o[11]};
    private int u = 0;
    public Image a = null;
    public Image b = null;
    private Image v = null;
    private Image w = null;

    public b(int n2, int n3, int n4, int n5) {
        super(0, 0, n4, 0);
        d d2 = b.e.d.a("/c.pak");
        this.a = d2.c("mmbg.png");
        this.b = d2.c("mmtext.png");
        this.v = d2.c("mmp.png");
        this.w = d2.c("mm2.png");
        this.f = this.s = this.a.getHeight();
        this.c = this.n + this.o;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        graphics.drawImage(this.a, n2, n3, 20);
        graphics.drawImage(this.v, n2 + (this.u + 1) * this.e / (t.length + 1), n3 + this.f / 2, 3);
        for (int i2 = 0; i2 < t.length; ++i2) {
            b.e.a.a(graphics, this.b, this.b.getWidth() / t.length * i2, 0, this.b.getWidth() / t.length, this.b.getHeight(), 0, n2 + (i2 + 1) * this.e / (t.length + 1), n3 + this.f / 2, 3);
        }
        graphics.setColor(0);
        graphics.drawRect(n2, n3, this.e - 1, this.f - 1);
        graphics.setColor(8673569);
        graphics.drawRect(n2 + 1, n3 + 1, this.e - 3, this.f - 3);
        graphics.setColor(13677668);
        graphics.drawRect(n2 + 2, n3 + 2, this.e - 5, this.f - 5);
        graphics.setColor(5386256);
        graphics.drawRect(n2 + 3, n3 + 3, this.e - 7, this.f - 7);
        b.e.a.a(graphics, b.b.g.a, 0, 0, b.b.g.a.getWidth(), b.b.g.a.getHeight(), 0, n2, n3, 20);
        b.e.a.a(graphics, b.b.g.a, 0, 0, b.b.g.a.getWidth(), b.b.g.a.getHeight(), 2, n2 + this.e, n3, 24);
        b.e.a.a(graphics, b.b.g.a, 0, 0, b.b.g.a.getWidth(), b.b.g.a.getHeight(), 6, n2, n3 + this.f - 1, 36);
        b.e.a.a(graphics, b.b.g.a, 0, 0, b.b.g.a.getWidth(), b.b.g.a.getHeight(), 3, n2 + this.e, n3 + this.f - 1, 40);
        graphics.drawImage(this.w, n2 + this.e / 2, n3, 17);
        graphics.setColor(11901522);
        graphics.drawLine(n2 + 4, n3 + 4, n2 + 4, n3 + this.f - 7);
        graphics.drawLine(n2 + this.e - 4, n3 + 4, n2 + this.e - 4, n3 + this.f - 7);
        graphics.setColor(8673569);
        graphics.drawLine(n2 + 5, n3 + 4, n2 + 5, n3 + this.f - 7);
        graphics.drawLine(n2 + this.e - 5, n3 + 4, n2 + this.e - 5, n3 + this.f - 7);
        graphics.setColor(0);
        graphics.drawLine(n2 + 6, n3 + 4, n2 + 6, n3 + this.f - 7);
        graphics.drawLine(n2 + this.e - 6, n3 + 4, n2 + this.e - 6, n3 + this.f - 7);
    }

    public final String a() {
        return t[this.u];
    }

    protected final void b() {
    }

    public final void a(int n2, int n3) {
        if (this.l != 2) {
            return;
        }
        switch (n2) {
            case -7: {
                this.c();
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
                super.e();
            }
        }
    }

    public final void c() {
        super.c();
        this.p = this.n + this.o;
    }

    public final void b(int n2, int n3) {
    }

    static {
        int[] nArray = new int[]{15165513, 11688241, 11688241, 9588245};
    }
}

