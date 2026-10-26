/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package c;

import b.a;
import b.f;
import c.e;
import c.u;
import d.c;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.b;

public final class m
extends u {
    private f a = null;
    private int b = 0;
    private int c = 0;
    private int d = 0;
    private int e = 0;
    private int f = 0;
    private Image g = null;
    private Image h = null;
    private Image l = null;

    public m(f f2, Image[] imageArray, Image image) {
        this.a = f2;
        this.c = f2.l();
        this.d = f2.m();
        this.e = f2.n();
        this.f = f2.o();
        this.g = imageArray[0];
        this.h = imageArray[1];
        this.l = image;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4 = n3 - b.a.M.getHeight() - 4;
        int n5 = n2;
        Graphics graphics2 = graphics;
        m m2 = this;
        graphics2.drawImage(m2.g, n5 + 26, n4 + 76, 20);
        graphics2.drawImage(m2.h, n5 + 184 - 5, n4 + 76, 24);
        int n6 = n5 + 26;
        int n7 = n4 + 37;
        int n8 = m2.a.c(0);
        e.a.b.a(graphics2, b.a.I, n8, n6, n7);
        b.a.a(graphics2, (byte)2, n6 += ("" + n8).length() * b.a.L, n7);
        b.a.a(graphics2, (byte)0, n6 += b.a.L, n7);
        n8 = m2.a.c(1);
        e.a.b.a(graphics2, b.a.I, n8, n6 += b.a.L, n7);
        b.a.a(graphics2, (byte)0, n6 += ("" + n8).length() * b.a.J, n7);
        e.a.b.a(graphics2, b.a.I, m2.f, n6 += b.a.L, n7);
        n6 = n5 + 78;
        n7 = n4 + 76;
        e.a.b.a(graphics2, b.a.I, m2.c, n6, n7);
        m.a(graphics2, b.a.U, m2.a.F, n6, n7 += 25);
        e.a.b.a(graphics2, b.a.I, m2.a.c(8), n6, n7 += 25);
        e.a.b.a(graphics2, b.a.I, m2.a.c(9), n6, n7 += 25);
        e.a.b.a(graphics2, b.a.I, m2.a.c(10), n6, n7 += 25);
        e.a.b.a(graphics2, b.a.I, m2.e, n6, n7 += 25);
        n6 = n5 + 184;
        n7 = n4 + 76;
        e.a.b.a(graphics2, b.a.I, m2.d, n6, n7);
        e.a.b.a(graphics2, b.a.I, m2.a.h(1), n6, n7 += 25);
        e.a.b.a(graphics2, b.a.I, m2.a.h(2), n6, n7 += 25);
        e.a.b.a(graphics2, b.a.I, m2.a.h(4), n6, n7 += 25);
        e.a.b.a(graphics2, b.a.I, m2.a.h(8), n6, n7 += 25);
        e.a.b.a(graphics2, b.a.I, m2.a.h(16), n6, n7 += 25);
        c.e.c(graphics2, n5, n4 + 237, m2.i + 1);
        graphics2.drawImage(b.a.Q, n5 + 79, n4 + 257, 20);
        e.a.b.a(graphics2, b.a.G, b.a.D, n5 + 79 + b.a.Q.getWidth() + 3, n4 + 257);
        d.c.a(graphics2, 35, n5 + 79, n4 + 294, m2.a.q(), b.a.h);
        n6 = m2.a.c(2);
        n8 = String.valueOf(n6).length() * b.a.H;
        n7 = n4 + 257;
        e.a.b.a(graphics2, b.a.G, n6, n5 + 181 - n8, n7);
        graphics2.drawImage(b.a.P, n5 + 181, n7, 20);
        n6 = m2.a.c(3);
        e.a.b.a(graphics2, b.a.G, n6, n5 + 181 + b.a.P.getWidth(), n7);
        d.c.a(graphics2, 35, n5 + 181, n7 += b.a.G.getHeight(), m2.a.c(2) * 100 / m2.a.c(3), b.a.e);
        n6 = m2.a.c(4);
        n8 = String.valueOf(n6).length() * b.a.H;
        e.a.b.a(graphics2, b.a.G, n6, n5 + 181 - n8, n7 += 5);
        graphics2.drawImage(b.a.P, n5 + 181, n7, 20);
        n6 = m2.a.c(5);
        e.a.b.a(graphics2, b.a.G, n6, n5 + 181 + b.a.P.getWidth(), n7);
        d.c.a(graphics2, 35, n5 + 181, n7 += b.a.G.getHeight(), m2.a.c(4) * 100 / m2.a.c(5), b.a.f);
        n6 = m2.a.c(6);
        n8 = String.valueOf(n6).length() * b.a.H;
        e.a.b.a(graphics2, b.a.G, n6, n5 + 181 - n8, n7 += 5);
        graphics2.drawImage(b.a.P, n5 + 181, n7, 20);
        n6 = m2.a.c(7);
        e.a.b.a(graphics2, b.a.G, n6, n5 + 181 + b.a.P.getWidth(), n7);
        d.c.a(graphics2, 35, n5 + 181, n7 += b.a.G.getHeight(), m2.a.c(6) * 100 / m2.a.c(7), b.a.g);
        n4 = n3;
        n5 = n2;
        graphics2 = graphics;
        m2 = this;
        if (m2.l != null) {
            graphics2.drawRegion(m2.l, 0, 0, main.b.h[m2.a.f][0], main.b.h[m2.a.f][1], 0, n5, n4 + m2.j, 36);
        }
    }

    public final void a(int n2, int n3) {
        if (n2 == -7) {
            this.k.b();
        }
    }

    public final void e() {
    }

    private static void a(Graphics graphics, Image image, byte by, int n2, int n3) {
        if (image == null) {
            return;
        }
        int n4 = image.getWidth() / 5;
        int n5 = image.getHeight();
        for (int i2 = 0; i2 < 5; ++i2) {
            if ((by & 1 << i2) == 0) continue;
            graphics.drawRegion(image, i2 * n4, 0, n4, n5, 0, n2, n3, 20);
        }
    }
}

