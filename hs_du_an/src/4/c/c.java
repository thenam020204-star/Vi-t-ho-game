/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package c;

import a.f;
import a.g;
import b.a.b;
import b.b.l;
import b.e.a;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class c
extends l {
    private g a = null;
    private Image b = null;
    private int c = 0;
    private int d = 0;
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

    public c(g g2) {
        super(g2.K);
        this.a = g2;
        this.m = g2.c(0);
        this.n = g2.g();
        this.o = g2.c(1);
        this.p = g2.c(8);
        this.q = g2.c(9);
        this.r = g2.f();
        this.s = g2.d();
        this.t = g2.e();
        this.u = g2.c(10);
        this.A = g2.B();
        this.B = g2.C();
        this.C = g2.D();
        this.x = g2.c(2);
        this.y = g2.c(4);
        this.z = g2.c(6);
        this.D = g2.M();
        this.E = g2.N();
        this.F = g2.O();
        this.G = g2.q();
        this.w[0] = g2.h(1);
        this.w[1] = g2.h(2);
        this.w[2] = g2.h(4);
        this.w[3] = g2.h(8);
        this.w[4] = g2.h(16);
        this.v = g2.U();
        this.b = b.a.b.b("r" + g2.n);
        this.H = a.f.E * 5;
    }

    protected final void a() {
        this.l = a.f.D.getHeight() * 5 + a.f.e.length * 3;
        this.c = (this.i - this.l - 4 - a.f.Z.getHeight() * 7) / 9;
        this.d = (this.h - (a.f.Z.getWidth() << 2)) / 6;
        this.c(this.i - this.l - 4);
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
        c c2 = c3;
        int n6 = n5 + c2.d;
        int n7 = n4 + c2.c;
        b.e.a.a(graphics2, a.f.F, c2.m, n6, n7);
        a.f.a(graphics2, (byte)2, n6 += ("" + c2.m).length() * a.f.I, n7);
        a.f.a(graphics2, (byte)0, n6 += a.f.I, n7);
        b.e.a.a(graphics2, a.f.F, c2.o, n6 += a.f.I, n7);
        a.f.a(graphics2, (byte)0, n6 += ("" + c2.o).length() * a.f.G, n7);
        b.e.a.a(graphics2, a.f.F, c2.n, n6 += a.f.I, n7);
        n6 = n5 + c2.d;
        graphics2.drawImage(a.f.Z, n6, n7 += a.f.Z.getHeight() + (c2.c << 1), 20);
        b.e.a.a(graphics2, a.f.F, c2.s, n6 += a.f.Z.getWidth() + c2.d, n7);
        graphics2.drawImage(a.f.aa, n6 += a.f.Z.getWidth() + (c2.d << 1), n7, 20);
        b.e.a.a(graphics2, a.f.F, c2.t, n6 += a.f.Z.getWidth() + c2.d, n7);
        n6 = n5 + c2.d;
        graphics2.drawImage(a.f.ag, n6, n7 += a.f.Z.getHeight() + c2.c, 20);
        graphics2.drawImage(a.f.af[c2.v], n6 += a.f.Z.getWidth() + c2.d, n7, 20);
        graphics2.drawImage(a.f.af[0], n6 += a.f.Z.getWidth() + (c2.d << 1), n7, 20);
        b.e.a.a(graphics2, a.f.F, c2.w[0], n6 += a.f.Z.getWidth() + c2.d, n7);
        n6 = n5 + c2.d;
        graphics2.drawImage(a.f.ab, n6, n7 += a.f.Z.getHeight() + c2.c, 20);
        b.e.a.a(graphics2, a.f.F, c2.p, n6 += a.f.Z.getWidth() + c2.d, n7);
        graphics2.drawImage(a.f.af[1], n6 += a.f.Z.getWidth() + (c2.d << 1), n7, 20);
        b.e.a.a(graphics2, a.f.F, c2.w[1], n6 += a.f.Z.getWidth() + c2.d, n7);
        n6 = n5 + c2.d;
        graphics2.drawImage(a.f.ad, n6, n7 += a.f.Z.getHeight() + c2.c, 20);
        b.e.a.a(graphics2, a.f.F, c2.q, n6 += a.f.Z.getWidth() + c2.d, n7);
        graphics2.drawImage(a.f.af[2], n6 += a.f.Z.getWidth() + (c2.d << 1), n7, 20);
        b.e.a.a(graphics2, a.f.F, c2.w[2], n6 += a.f.Z.getWidth() + c2.d, n7);
        n6 = n5 + c2.d;
        graphics2.drawImage(a.f.ac, n6, n7 += a.f.Z.getHeight() + c2.c, 20);
        b.e.a.a(graphics2, a.f.F, c2.u, n6 += a.f.Z.getWidth() + c2.d, n7);
        graphics2.drawImage(a.f.af[3], n6 += a.f.Z.getWidth() + (c2.d << 1), n7, 20);
        b.e.a.a(graphics2, a.f.F, c2.w[3], n6 += a.f.Z.getWidth() + c2.d, n7);
        n6 = n5 + c2.d;
        graphics2.drawImage(a.f.ae, n6, n7 += a.f.Z.getHeight() + c2.c, 20);
        b.e.a.a(graphics2, a.f.F, c2.r, n6 += a.f.Z.getWidth() + c2.d, n7);
        graphics2.drawImage(a.f.af[4], n6 += a.f.Z.getWidth() + (c2.d << 1), n7, 20);
        b.e.a.a(graphics2, a.f.F, c2.w[4], n6 += a.f.Z.getWidth() + c2.d, n7);
        n4 = n3;
        n5 = n2;
        graphics2 = graphics;
        c2 = c3;
        n3 = n4;
        n2 = n5;
        graphics = graphics2;
        c c3 = c2;
        if (c2.b != null) {
            graphics.drawRegion(c3.b, 0, 0, main.a.j[c3.a.n][0], main.a.j[c3.a.n][1], 0, n2, n3 + c3.i, 36);
        }
        a.f.Z.getWidth();
        n6 = 0;
        n7 = n4 + c2.i - c2.l + a.f.D.getHeight();
        n6 = n5 + main.a.j[c2.a.n][0];
        graphics2.drawImage(a.f.N, n6, n7, 20);
        b.e.a.a(graphics2, a.f.D, a.f.C, n6 += a.f.N.getWidth(), n7);
        n6 = n5 + main.a.j[c2.a.n][0];
        n7 = n4 + c2.i - c2.l + a.f.D.getHeight() * 3 + (a.f.e.length + 2 << 1);
        b.e.a.a(graphics2, c2.H, n6, n7, c2.G, a.f.h);
        n7 = n4 + c2.i - c2.l + a.f.D.getHeight();
        int n8 = String.valueOf(c2.x).length() * a.f.E;
        n6 = n5 + 5 * c2.d + 3 * a.f.Z.getWidth();
        b.e.a.a(graphics2, a.f.D, c2.x, n6 - n8, n7);
        graphics2.drawImage(a.f.M, n6, n7, 20);
        b.e.a.a(graphics2, a.f.D, c2.A, n6 + a.f.M.getWidth(), n7);
        b.e.a.a(graphics2, c2.H, n6, n7 += a.f.D.getHeight(), c2.D, a.f.e);
        n8 = String.valueOf(c2.y).length() * a.f.E;
        n6 = n5 + 5 * c2.d + 3 * a.f.Z.getWidth();
        b.e.a.a(graphics2, a.f.D, c2.y, n6 - n8, n7 += a.f.e.length + 2);
        graphics2.drawImage(a.f.M, n6, n7, 20);
        b.e.a.a(graphics2, a.f.D, c2.B, n6 + a.f.M.getWidth(), n7);
        b.e.a.a(graphics2, c2.H, n6, n7 += a.f.D.getHeight(), c2.E, a.f.f);
        n8 = String.valueOf(c2.z).length() * a.f.E;
        n6 = n5 + 5 * c2.d + 3 * a.f.Z.getWidth();
        b.e.a.a(graphics2, a.f.D, c2.z, n6 - n8, n7 += a.f.e.length + 2);
        graphics2.drawImage(a.f.M, n6, n7, 20);
        b.e.a.a(graphics2, a.f.D, c2.C, n6 + a.f.M.getWidth(), n7);
        b.e.a.a(graphics2, c2.H, n6, n7 += a.f.D.getHeight(), c2.F, a.f.g);
    }
}

