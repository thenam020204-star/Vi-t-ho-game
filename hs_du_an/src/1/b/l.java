/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package b;

import b.f;
import b.t;
import d.a;
import d.g;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.b;

public final class l
extends t {
    private g a = null;
    private int b = 0;
    private Image c = null;
    private Image d = null;
    private Image e = null;

    public l(g g2, Image[] imageArray, Image image) {
        this.a = g2;
        this.c = imageArray[0];
        this.d = imageArray[1];
        this.e = image;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4 = n3 - d.a.N.getHeight() - 4;
        int n5 = n2;
        Graphics graphics2 = graphics;
        l l2 = this;
        graphics2.drawImage(l2.c, n5 + 26, n4 + 76, 20);
        graphics2.drawImage(l2.d, n5 + 184 - 5, n4 + 76, 24);
        int n6 = n5 + 26;
        int n7 = n4 + 37;
        int n8 = l2.a.e(0);
        h.b.a(graphics2, d.a.J, n8, n6, n7);
        d.a.a(graphics2, (byte)2, n6 += ("" + n8).length() * d.a.M, n7);
        d.a.a(graphics2, (byte)0, n6 += d.a.M, n7);
        int n9 = l2.a.e(1);
        h.b.a(graphics2, d.a.J, n9, n6 += d.a.M, n7);
        d.a.a(graphics2, (byte)0, n6 += ("" + n9).length() * d.a.K, n7);
        n8 = d.a.u[l2.a.f][n8].b;
        h.b.a(graphics2, d.a.J, n8, n6 += d.a.M, n7);
        n6 = n5 + 78;
        n7 = n4 + 76;
        h.b.a(graphics2, d.a.J, l2.a.D(), n6, n7);
        l.a(graphics2, d.a.V, l2.a.K, n6, n7 += 25);
        h.b.a(graphics2, d.a.J, l2.a.e(8), n6, n7 += 25);
        h.b.a(graphics2, d.a.J, l2.a.e(9), n6, n7 += 25);
        h.b.a(graphics2, d.a.J, l2.a.e(10), n6, n7 += 25);
        h.b.a(graphics2, d.a.J, l2.a.J(), n6, n7 += 25);
        n6 = n5 + 184;
        n7 = n4 + 76;
        h.b.a(graphics2, d.a.J, l2.a.E(), n6, n7);
        h.b.a(graphics2, d.a.J, l2.a.d((byte)1), n6, n7 += 25);
        h.b.a(graphics2, d.a.J, l2.a.d((byte)2), n6, n7 += 25);
        h.b.a(graphics2, d.a.J, l2.a.d((byte)4), n6, n7 += 25);
        h.b.a(graphics2, d.a.J, l2.a.d((byte)8), n6, n7 += 25);
        h.b.a(graphics2, d.a.J, l2.a.d((byte)16), n6, n7 += 25);
        f.c(graphics2, n5, n4 + 237, l2.i + 1);
        graphics2.drawImage(d.a.R, n5 + 79, n4 + 257, 20);
        h.b.a(graphics2, d.a.H, d.a.E, n5 + 79 + d.a.R.getWidth() + 3, n4 + 257);
        g.b.a(graphics2, 35, n5 + 79, n4 + 294, l2.a.t(), d.a.h);
        n6 = l2.a.e(2);
        n8 = String.valueOf(n6).length() * d.a.I;
        n7 = n4 + 257;
        h.b.a(graphics2, d.a.H, n6, n5 + 181 - n8, n7);
        graphics2.drawImage(d.a.Q, n5 + 181, n7, 20);
        n6 = l2.a.e(3);
        h.b.a(graphics2, d.a.H, n6, n5 + 181 + d.a.Q.getWidth(), n7);
        g.b.a(graphics2, 35, n5 + 181, n7 += d.a.H.getHeight(), l2.a.e(2) * 100 / l2.a.e(3), d.a.e);
        n6 = l2.a.e(4);
        n8 = String.valueOf(n6).length() * d.a.I;
        h.b.a(graphics2, d.a.H, n6, n5 + 181 - n8, n7 += 5);
        graphics2.drawImage(d.a.Q, n5 + 181, n7, 20);
        n6 = l2.a.e(5);
        h.b.a(graphics2, d.a.H, n6, n5 + 181 + d.a.Q.getWidth(), n7);
        g.b.a(graphics2, 35, n5 + 181, n7 += d.a.H.getHeight(), l2.a.e(4) * 100 / l2.a.e(5), d.a.f);
        n6 = l2.a.e(6);
        n8 = String.valueOf(n6).length() * d.a.I;
        h.b.a(graphics2, d.a.H, n6, n5 + 181 - n8, n7 += 5);
        graphics2.drawImage(d.a.Q, n5 + 181, n7, 20);
        n6 = l2.a.e(7);
        h.b.a(graphics2, d.a.H, n6, n5 + 181 + d.a.Q.getWidth(), n7);
        g.b.a(graphics2, 35, n5 + 181, n7 += d.a.H.getHeight(), l2.a.e(6) * 100 / l2.a.e(7), d.a.g);
        n4 = n3;
        n5 = n2;
        graphics2 = graphics;
        l2 = this;
        if (l2.e != null) {
            graphics2.drawRegion(l2.e, 0, 0, main.b.g[l2.a.f][0], main.b.g[l2.a.f][1], 0, n5, n4 + l2.j, 36);
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

