/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package e;

import a.a.g;
import a.c.b;
import b.f;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.a;

public final class h
extends g {
    private f h = null;
    private Image i = null;
    private int j = 0;
    private int k = 0;
    private int l = 0;
    private int m = 0;
    private int n = 0;
    private int o = 0;
    private int p = 0;
    private int q = 0;
    private int r = 0;
    private int s = 0;
    private int t = 0;
    private int u = 0;
    private int v = 0;
    private int[] w = new int[5];
    private int x = 0;
    private int y = 0;
    private int z = 0;
    private int A = 0;
    private int B = 0;
    private int C = 0;
    private int D = 0;
    private int E = 0;
    private int F = 0;
    private int G = 0;
    private int H = 0;

    public h(f f2) {
        super(f2.K);
        this.h = f2;
        this.m = f2.c(0);
        this.n = f2.o();
        this.o = f2.c(1);
        this.p = f2.c(8);
        this.q = f2.c(9);
        this.r = f2.n();
        this.s = f2.l();
        this.t = f2.m();
        this.u = f2.c(10);
        this.A = f2.B();
        this.B = f2.C();
        this.C = f2.D();
        this.x = f2.c(2);
        this.y = f2.c(4);
        this.z = f2.c(6);
        this.D = f2.M();
        this.E = f2.N();
        this.F = f2.O();
        this.G = f2.q();
        this.w[0] = f2.h(1);
        this.w[1] = f2.h(2);
        this.w[2] = f2.h(4);
        this.w[3] = f2.h(8);
        this.w[4] = f2.h(16);
        this.v = f2.U();
        this.i = a.e.a.b("r" + f2.i);
        this.H = b.a.E * 5;
    }

    protected final void a() {
        this.l = b.a.D.getHeight() * 5 + b.a.e.length * 3;
        this.j = (this.e - this.l - 4 - b.a.Z.getHeight() * 7) / 9;
        this.k = (this.d - (b.a.Z.getWidth() << 2)) / 6;
        this.a(this.e - this.l - 4);
    }

    public final void a(int n2, int n3) {
    }

    public final void b() {
    }

    public final void a(Graphics graphics, int n2, int n3) {
        super.a(graphics, n2, n3);
        int n4 = n3;
        int n5 = n2;
        Graphics graphics2 = graphics;
        h h2 = h3;
        int n6 = n5 + h2.k;
        int n7 = n4 + h2.j;
        a.c.b.a(graphics2, b.a.F, h2.m, n6, n7);
        b.a.a(graphics2, (byte)2, n6 += ("" + h2.m).length() * b.a.I, n7);
        b.a.a(graphics2, (byte)0, n6 += b.a.I, n7);
        a.c.b.a(graphics2, b.a.F, h2.o, n6 += b.a.I, n7);
        b.a.a(graphics2, (byte)0, n6 += ("" + h2.o).length() * b.a.G, n7);
        a.c.b.a(graphics2, b.a.F, h2.n, n6 += b.a.I, n7);
        n6 = n5 + h2.k;
        graphics2.drawImage(b.a.Z, n6, n7 += b.a.Z.getHeight() + (h2.j << 1), 20);
        a.c.b.a(graphics2, b.a.F, h2.s, n6 += b.a.Z.getWidth() + h2.k, n7);
        graphics2.drawImage(b.a.aa, n6 += b.a.Z.getWidth() + (h2.k << 1), n7, 20);
        a.c.b.a(graphics2, b.a.F, h2.t, n6 += b.a.Z.getWidth() + h2.k, n7);
        n6 = n5 + h2.k;
        graphics2.drawImage(b.a.ag, n6, n7 += b.a.Z.getHeight() + h2.j, 20);
        graphics2.drawImage(b.a.af[h2.v], n6 += b.a.Z.getWidth() + h2.k, n7, 20);
        graphics2.drawImage(b.a.af[0], n6 += b.a.Z.getWidth() + (h2.k << 1), n7, 20);
        a.c.b.a(graphics2, b.a.F, h2.w[0], n6 += b.a.Z.getWidth() + h2.k, n7);
        n6 = n5 + h2.k;
        graphics2.drawImage(b.a.ab, n6, n7 += b.a.Z.getHeight() + h2.j, 20);
        a.c.b.a(graphics2, b.a.F, h2.p, n6 += b.a.Z.getWidth() + h2.k, n7);
        graphics2.drawImage(b.a.af[1], n6 += b.a.Z.getWidth() + (h2.k << 1), n7, 20);
        a.c.b.a(graphics2, b.a.F, h2.w[1], n6 += b.a.Z.getWidth() + h2.k, n7);
        n6 = n5 + h2.k;
        graphics2.drawImage(b.a.ad, n6, n7 += b.a.Z.getHeight() + h2.j, 20);
        a.c.b.a(graphics2, b.a.F, h2.q, n6 += b.a.Z.getWidth() + h2.k, n7);
        graphics2.drawImage(b.a.af[2], n6 += b.a.Z.getWidth() + (h2.k << 1), n7, 20);
        a.c.b.a(graphics2, b.a.F, h2.w[2], n6 += b.a.Z.getWidth() + h2.k, n7);
        n6 = n5 + h2.k;
        graphics2.drawImage(b.a.ac, n6, n7 += b.a.Z.getHeight() + h2.j, 20);
        a.c.b.a(graphics2, b.a.F, h2.u, n6 += b.a.Z.getWidth() + h2.k, n7);
        graphics2.drawImage(b.a.af[3], n6 += b.a.Z.getWidth() + (h2.k << 1), n7, 20);
        a.c.b.a(graphics2, b.a.F, h2.w[3], n6 += b.a.Z.getWidth() + h2.k, n7);
        n6 = n5 + h2.k;
        graphics2.drawImage(b.a.ae, n6, n7 += b.a.Z.getHeight() + h2.j, 20);
        a.c.b.a(graphics2, b.a.F, h2.r, n6 += b.a.Z.getWidth() + h2.k, n7);
        graphics2.drawImage(b.a.af[4], n6 += b.a.Z.getWidth() + (h2.k << 1), n7, 20);
        a.c.b.a(graphics2, b.a.F, h2.w[4], n6 += b.a.Z.getWidth() + h2.k, n7);
        n4 = n3;
        n5 = n2;
        graphics2 = graphics;
        h2 = h3;
        n3 = n4;
        n2 = n5;
        graphics = graphics2;
        h h3 = h2;
        if (h2.i != null) {
            graphics.drawRegion(h3.i, 0, 0, main.a.j[h3.h.i][0], main.a.j[h3.h.i][1], 0, n2, n3 + h3.e, 36);
        }
        b.a.Z.getWidth();
        n6 = 0;
        n7 = n4 + h2.e - h2.l + b.a.D.getHeight();
        n6 = n5 + main.a.j[h2.h.i][0];
        graphics2.drawImage(b.a.N, n6, n7, 20);
        a.c.b.a(graphics2, b.a.D, b.a.C, n6 += b.a.N.getWidth(), n7);
        n6 = n5 + main.a.j[h2.h.i][0];
        n7 = n4 + h2.e - h2.l + b.a.D.getHeight() * 3 + (b.a.e.length + 2 << 1);
        a.c.b.a(graphics2, h2.H, n6, n7, h2.G, b.a.h);
        n7 = n4 + h2.e - h2.l + b.a.D.getHeight();
        int n8 = String.valueOf(h2.x).length() * b.a.E;
        n6 = n5 + 5 * h2.k + 3 * b.a.Z.getWidth();
        a.c.b.a(graphics2, b.a.D, h2.x, n6 - n8, n7);
        graphics2.drawImage(b.a.M, n6, n7, 20);
        a.c.b.a(graphics2, b.a.D, h2.A, n6 + b.a.M.getWidth(), n7);
        a.c.b.a(graphics2, h2.H, n6, n7 += b.a.D.getHeight(), h2.D, b.a.e);
        n8 = String.valueOf(h2.y).length() * b.a.E;
        n6 = n5 + 5 * h2.k + 3 * b.a.Z.getWidth();
        a.c.b.a(graphics2, b.a.D, h2.y, n6 - n8, n7 += b.a.e.length + 2);
        graphics2.drawImage(b.a.M, n6, n7, 20);
        a.c.b.a(graphics2, b.a.D, h2.B, n6 + b.a.M.getWidth(), n7);
        a.c.b.a(graphics2, h2.H, n6, n7 += b.a.D.getHeight(), h2.E, b.a.f);
        n8 = String.valueOf(h2.z).length() * b.a.E;
        n6 = n5 + 5 * h2.k + 3 * b.a.Z.getWidth();
        a.c.b.a(graphics2, b.a.D, h2.z, n6 - n8, n7 += b.a.e.length + 2);
        graphics2.drawImage(b.a.M, n6, n7, 20);
        a.c.b.a(graphics2, b.a.D, h2.C, n6 + b.a.M.getWidth(), n7);
        a.c.b.a(graphics2, h2.H, n6, n7 += b.a.D.getHeight(), h2.F, b.a.g);
    }
}

