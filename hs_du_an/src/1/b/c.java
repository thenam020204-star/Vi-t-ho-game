/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package b;

import b.j;
import d.a;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.b;
import main.d;

public final class c
extends j {
    private int b = 3739672;
    private int c = 6373665;
    private int d = 8870682;
    private int e = 1;
    private String[] f = null;
    private long g = 0L;
    private long h = 0L;
    private static int r = (main.b.d - 30 - 6) / h.b.b;
    private String[][] s = null;
    private int t = 0;
    private int u = 0;
    private boolean v = false;
    private int w = 0;
    private int x = 0;
    private int y = 0;
    private int z = 0;
    private int A = 0;
    private Image B = null;
    private String C = null;
    private byte D = (byte)9;
    private int E = 0;
    private int F = 0;
    private int G = 0;
    private byte H = (byte)-1;
    public static Image a = null;
    private f.a I = null;
    private int J = 0;
    private int K = 0;
    private int L = 0;

    private c(byte by) {
        super((byte)1);
        this.H = by;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        switch (this.H) {
            case 1: {
                int n4;
                int[] nArray = h.b.a(graphics);
                graphics.clipRect(this.l(), this.m(), this.n - 1, this.o - 1);
                this.b(graphics);
                this.c(graphics);
                n3 = h.b.b * this.s[this.w].length;
                int n5 = 0;
                int n6 = Math.min(r, this.t);
                int n7 = this.x;
                int n8 = this.y;
                for (n4 = 0; n4 < n6; ++n4) {
                    boolean c3;
                    n5 = this.m() + 3 + n4 * h.b.b;
                    if (n7 == this.w && !c3) {
                        h.a.a(graphics, 5851198, 3021840, this.l() + 3, n5, this.n - 6 - 1, n3 / 2 - 1);
                        h.a.a(graphics, 3021840, 5851198, this.l() + 3, n5 + n3 / 2, this.n - 6 - 1, n3 / 2 - 1);
                    }
                    graphics.setColor(16776624);
                    graphics.drawString(this.s[n7][c3], this.l() + 3 + this.u / 2, n5, 17);
                    if ((c3 += 1) < this.s[n7].length) continue;
                    c3 = false;
                    ++n7;
                }
                if (this.v) {
                    int n9;
                    int n10 = this.x;
                    c c2 = this;
                    if (n10 < 0 || n10 >= c2.s.length) {
                        n9 = 0;
                    } else {
                        int n11 = 0;
                        for (int i2 = 0; i2 < n10; ++i2) {
                            n11 += c2.s[i2].length;
                        }
                        n9 = n11;
                    }
                    n4 = n9;
                    h.b.a(graphics, this.t, n4 + this.y, r, this.l() + this.n - 6, this.m() + d.a.P.getHeight(), this.o - 2 * d.a.P.getHeight());
                }
                h.b.a(graphics, nArray);
                return;
            }
            case 2: {
                int[] nArray = h.b.a(graphics);
                graphics.clipRect(this.l(), this.m(), this.n - 1, this.o - 1);
                this.b(graphics);
                this.c(graphics);
                if (this.f != null) {
                    for (n3 = 0; n3 < this.f.length; ++n3) {
                        graphics.setColor(16776624);
                        graphics.drawString(this.f[n3], this.l() + this.n / 2, this.m() + 3 + n3 * h.b.b, 17);
                    }
                }
                h.b.a(graphics, nArray);
                return;
            }
            case 3: {
                return;
            }
            case 4: {
                int n12;
                int n13;
                int[] nArray = h.b.a(graphics);
                graphics.clipRect(this.l(), this.m(), this.n, this.o);
                Graphics graphics2 = graphics;
                c c4 = this;
                graphics2.setColor(c4.b);
                graphics2.drawLine(c4.l(), c4.m(), c4.l() + c4.n, c4.m());
                graphics2.setColor(c4.c);
                graphics2.drawLine(c4.l(), c4.m() + 1, c4.l() + c4.n, c4.m() + 1);
                graphics2.setColor(c4.d);
                graphics2.drawLine(c4.l(), c4.m() + 2, c4.l() + c4.n, c4.m() + 2);
                graphics2.setColor(c4.b);
                graphics2.drawLine(c4.l(), c4.m() + c4.o - 1, c4.l() + c4.n, c4.m() + c4.o - 1);
                graphics2.setColor(c4.c);
                graphics2.drawLine(c4.l(), c4.m() + 1 + c4.o - 3, c4.l() + c4.n, c4.m() + 1 + c4.o - 3);
                graphics2.setColor(c4.d);
                graphics2.drawLine(c4.l(), c4.m() + 2 + c4.o - 5, c4.l() + c4.n, c4.m() + 2 + c4.o - 5);
                graphics2 = graphics;
                c4 = this;
                h.a.a(graphics2, 3217664, 8352325, c4.l(), c4.m() + 3, c4.n, c4.o - 6 - 1);
                if (this.f != null) {
                    graphics2 = graphics;
                    c4 = this;
                    n13 = 0;
                    while ((n13 + 1) * h.b.b <= c4.A) {
                        ++n13;
                    }
                    int[] nArray2 = h.b.a(graphics2);
                    graphics2.clipRect(c4.L, c4.m() + 3, c4.n, c4.o - 6);
                    boolean bl = false;
                    n12 = 0;
                    int n14 = 0;
                    if ((c4.D & 1) != 0) {
                        n14 = c4.L + 3;
                        n14 += c4.I == null ? 0 : 80;
                    } else {
                        n14 = c4.L + 9 + 3;
                    }
                    graphics2.setColor(0xFFFFFF);
                    int n15 = c4.z == c4.A ? 3 : 4;
                    for (int i3 = 0; i3 < n15 && n13 + i3 < c4.f.length; ++i3) {
                        n12 = n14;
                        for (int i4 = 0; i4 < c4.f[n13 + i3].length(); ++i4) {
                            char c3 = c4.f[n13 + i3].charAt(i4);
                            if (c3 == '{') {
                                graphics2.setColor(0xFF0000);
                                continue;
                            }
                            if (c3 == '}') {
                                graphics2.setColor(0xFFFFFF);
                                continue;
                            }
                            graphics2.drawChar(c3, n12, c4.m() + 3 + (n13 + i3) * h.b.b - c4.A, 20);
                            n12 += h.b.a.charWidth(c3);
                        }
                    }
                    h.b.a(graphics2, nArray2);
                }
                h.b.a(graphics, nArray);
                if (this.e == 2) {
                    this.a(graphics);
                }
                graphics2 = graphics;
                c4 = this;
                if (c4.I != null) {
                    n13 = 0;
                    n13 = (c4.D & 1) != 0 ? c4.L + 40 : c4.L + c4.n - 40;
                    int n16 = c4.m() + c4.o - 3;
                    c4.I.a(graphics2, n13, n16);
                }
                graphics2 = graphics;
                c4 = this;
                if (c4.C != null && c4.C.trim().length() != 0) {
                    n13 = 0;
                    int n17 = 0;
                    int n16 = h.b.a.stringWidth(c4.C);
                    n12 = n16 + 6;
                    if ((c4.D & 4) != 0) {
                        n17 = c4.m() + c4.o;
                    } else {
                        n17 = c4.m() - h.b.b;
                        n17 -= 3;
                    }
                    n13 = (c4.D & 1) != 0 ? 0 : main.b.c - n12;
                    graphics2.setColor(8077832);
                    graphics2.fillRect(n13, n17, h.b.a.stringWidth(c4.C) + 5, h.b.b + 5);
                    graphics2.setColor(15440139);
                    graphics2.fillRect(n13 + 1, n17 + 1, h.b.a.stringWidth(c4.C) + 3, h.b.b + 3);
                    graphics2.setColor(3086592);
                    graphics2.fillRect(n13 + 2, n17 + 2, h.b.a.stringWidth(c4.C) + 1, h.b.b + 1);
                    h.a.a(graphics2, 0, c4.C, n13 + 3, n17 + 3, 20);
                }
                this.a(graphics);
                if ((this.D & 1) != 0) {
                    b.c.a(graphics, this.l() + this.n - 9, this.m() - 3, this.o + 6);
                    break;
                }
                b.c.a(graphics, this.l(), this.m() - 3, this.o + 6);
                return;
            }
            case 5: {
                if (this.f == null) break;
                graphics.setColor(0);
                graphics.fillRect(this.l(), this.m(), this.n, this.o);
                graphics.setColor(0xFFFFFF);
                n2 = this.m() + this.o / 2 - this.f.length * h.b.b / 2;
                for (n3 = 0; n3 < this.E; ++n3) {
                    graphics.drawString(this.f[n3], this.l(), n2 + n3 * h.b.b, 20);
                }
                graphics.drawString(this.f[this.E].substring(0, this.F), this.l(), n2 + this.E * h.b.b, 20);
            }
        }
    }

    private void a(Graphics graphics) {
        int n2 = this.l() + this.n / 2;
        int n3 = this.m() + this.o;
        if (main.b.h % 20 > 10) {
            n3 += 3;
        }
        h.b.a(graphics, d.a.ac, 0, 0, d.a.ac.getWidth(), d.a.ac.getHeight(), 6, n2, n3, 33);
    }

    public final void a(int n2, int n3) {
        if (this.e != 2) {
            return;
        }
        switch (this.H) {
            case 3: {
                return;
            }
            case 1: {
                switch (n2) {
                    case -7: {
                        this.i.c(this);
                        break;
                    }
                    case 50: {
                        this.w = (this.w - 1 + this.s.length) % this.s.length;
                        this.f();
                        break;
                    }
                    case 56: {
                        this.w = (this.w + 1) % this.s.length;
                        this.f();
                        break;
                    }
                    case -6: 
                    case 53: {
                        this.n();
                    }
                }
                return;
            }
            case 2: {
                if (n2 == 53 || n2 == -6) {
                    this.b();
                }
                return;
            }
            case 4: {
                if ((n2 == 53 || n3 == 35 || n3 == 999) && this.z == this.A) {
                    if (this.x + 3 >= this.f.length) {
                        this.e = 3;
                    } else {
                        ++this.x;
                        this.z = this.A + h.b.b;
                    }
                }
                return;
            }
            case 5: {
                if (n2 != 53) break;
                if (this.E == this.f.length - 1 && this.F == this.f[this.E].length()) {
                    this.d(-1);
                    break;
                }
                this.E = this.f.length - 1;
                this.F = this.f[this.E].length();
            }
        }
    }

    private void f() {
        int n2;
        if (this.w < this.x || this.w == this.x && this.y != 0) {
            this.x = this.w;
            this.y = 0;
            return;
        }
        int n3 = this.w;
        int n4 = this.y;
        int n5 = this.x;
        c c2 = this;
        if (n5 < 0 || n5 >= c2.s.length || n3 < 0 || n3 >= c2.s.length || n4 < 0 || n4 >= c2.s[n5].length) {
            n2 = -1;
        } else {
            n4 = c2.s[n5].length - n4;
            ++n5;
            while (n5 < n3) {
                n4 += c2.s[n5].length;
                ++n5;
            }
            n2 = n4 = n4 + c2.s[n3].length;
        }
        if (n2 <= r) {
            return;
        }
        this.x = this.w + 1;
        for (int i2 = r; i2 > 0; i2 -= this.s[this.x].length) {
            --this.x;
            if (i2 >= this.s[this.x].length) {
                this.y = 0;
                continue;
            }
            this.y = this.s[this.x].length - i2;
            break;
        }
    }

    public final void a() {
        block0 : switch (this.e) {
            case 1: {
                int[] nArray = h.b.a(this.n, 0, this.K, 0);
                this.n = nArray[0];
                nArray = h.b.a(this.l(), 0, this.J, 0);
                this.b(nArray[0]);
                if (this.n == this.K && this.l() == this.J) {
                    this.h = System.currentTimeMillis();
                    this.e = 2;
                }
                return;
            }
            case 2: {
                switch (this.H) {
                    case 2: {
                        c c2 = this;
                        if (c2.g == -1L || System.currentTimeMillis() <= c2.h + c2.g) break block0;
                        c2.b();
                        break block0;
                    }
                    case 1: {
                        break block0;
                    }
                    case 3: {
                        break block0;
                    }
                    case 4: {
                        c c3 = this;
                        if (c3.I != null) {
                            c3.I.b();
                        }
                        if (c3.z <= c3.A) break block0;
                        int n2 = (c3.z - c3.A) / 2;
                        c3.A = n2 <= 2 ? c3.z : (c3.A += n2);
                        if (c3.A <= c3.z) break block0;
                        c3.A = c3.z;
                        break block0;
                    }
                    case 5: {
                        c c4 = this;
                        ++c4.F;
                        if (c4.F >= c4.f[c4.E].length()) {
                            ++c4.E;
                            c4.F = 0;
                            if (c4.E >= c4.f.length) {
                                c4.E = c4.f.length - 1;
                                c4.F = c4.f[c4.E].length();
                                break block0;
                            }
                            if ((c4.E + 1) * h.b.b > c4.o / 2) {
                                c4.G -= h.b.b;
                            }
                        }
                    }
                    default: {
                        return;
                    }
                }
            }
            case 3: {
                int[] nArray = h.b.a(this.n, 0, this.K, 0);
                this.n = nArray[0];
                nArray = h.b.a(this.l(), 0, this.J, 0);
                this.b(nArray[0]);
                if (this.n != this.K || this.l() != this.J) break;
                this.d(this.w);
            }
        }
    }

    public static c a(String[] stringArray, d d2, byte by, byte by2) {
        int n2;
        if (stringArray == null || stringArray.length == 0) {
            return null;
        }
        c c2 = new c(1);
        int n3 = 0;
        int n4 = main.b.c - 30;
        c2.s = new String[stringArray.length][];
        c2.u = n4 - 6;
        for (n2 = 0; n2 < stringArray.length; ++n2) {
            c2.s[n2] = h.b.a(stringArray[n2], h.b.a, c2.u);
            n3 += c2.s[n2].length;
        }
        c2.t = n3;
        int n5 = (main.b.d - 30 - 6) / h.b.b;
        if (n3 > n5) {
            n2 = n5 * h.b.b + 6;
            c2.v = true;
            n4 += 10;
        } else {
            n2 = n3 * h.b.b + 6;
            c2.v = false;
        }
        c2.b(15);
        c2.c(main.b.d / 2 - n2 / 2);
        c2.n = n4;
        c2.o = n2;
        c2.j = d2;
        c2.p = 0;
        c2.q = by2;
        c2.K = c2.n;
        c2.n = 0;
        c2.J = c2.l();
        return c2;
    }

    public static c a(String string, long l2, d d2) {
        c c2 = new c(2);
        new c(2).g = l2;
        int n2 = main.b.c - 30;
        c2.f = h.b.a(string, h.b.a, n2 - 6);
        c2.j = d2;
        int n3 = (main.b.d - 30 - 6) / h.b.b;
        int n4 = 0;
        n4 = c2.f.length > n3 ? n3 * h.b.b + 6 : c2.f.length * h.b.b + 6;
        c2.b(15);
        c2.c(main.b.d / 2 - n4 / 2);
        c2.n = n2;
        c2.o = n4;
        c2.K = c2.n;
        c2.n = 0;
        c2.J = c2.l();
        return c2;
    }

    public static c a(String string, d d2, String string2, f.a a2, byte by) {
        if (string == null) {
            string = "";
        }
        c c2 = new c(4);
        new c(4).n = main.b.c;
        c2.o = h.b.b * 3 + 6;
        c2.D = by;
        f.a a3 = a2;
        c c3 = c2;
        if ((c3.D & 4) != 0) {
            if (a3 != null && c3.o - 3 < 80) {
                c3.c(80 - c3.o + 3);
            } else {
                c3.c(0);
            }
        } else {
            c3.c(main.b.d - c3.o);
        }
        c2.j = d2;
        c2.C = string2;
        c2.I = a2;
        c2.f = c2.I != null ? h.b.a(string, h.b.a, c2.n - 80 - 9) : h.b.a(string, h.b.a, c2.n - 9);
        c2.L = c2.l();
        if ((by & 2) != 0) {
            c2.J = c2.l();
            c2.b(c2.l() + c2.n);
            c2.K = c2.n;
            if (a2 != null) {
                a2.a(true);
            }
        } else {
            c2.K = c2.n;
            c2.n = 0;
            c2.J = c2.l();
        }
        c2.b = 8077832;
        c2.c = 15440139;
        c2.d = 16304136;
        return c2;
    }

    public static c a(String string, d d2) {
        if (string == null) {
            throw new NullPointerException("message == null");
        }
        c c2 = new c(5);
        new c(5).o = main.b.d;
        c2.n = main.b.c;
        c2.b(0);
        c2.c(0);
        c2.j = d2;
        c2.f = h.b.a(string, h.b.a, c2.n - 6);
        c2.e = 2;
        return c2;
    }

    public final void b() {
        if (this.k == 4 && (this.D & 2) != 0) {
            this.J = this.l() + this.n;
        } else {
            this.K = 0;
        }
        this.e = 3;
    }

    private void b(Graphics graphics) {
        graphics.setColor(2890254);
        graphics.fillRect(this.l(), this.m(), this.n, this.o);
    }

    private void c(Graphics graphics) {
        if (a == null) {
            return;
        }
        graphics.setColor(this.b);
        graphics.drawRect(this.l(), this.m(), this.n - 1, this.o - 1);
        graphics.setColor(this.c);
        graphics.drawRect(this.l() + 1, this.m() + 1, this.n - 3, this.o - 3);
        graphics.setColor(this.d);
        graphics.drawRect(this.l() + 2, this.m() + 2, this.n - 5, this.o - 5);
        h.b.a(graphics, a, 0, 0, a.getWidth(), a.getHeight(), 0, this.l(), this.m(), 20);
        h.b.a(graphics, a, 0, 0, a.getWidth(), a.getHeight(), 5, this.l() + this.n - a.getWidth(), this.m(), 20);
        h.b.a(graphics, a, 0, 0, a.getWidth(), a.getHeight(), 3, this.l() + this.n - a.getWidth(), this.m() + this.o - a.getHeight(), 20);
        h.b.a(graphics, a, 0, 0, a.getWidth(), a.getHeight(), 6, this.l(), this.m() + this.o - a.getHeight(), 20);
    }

    public final int c() {
        return this.w;
    }

    public final String d() {
        if (this.s == null || this.w < 0 || this.w >= this.s.length) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < this.s[this.w].length; ++i2) {
            stringBuffer.append(this.s[this.w][i2]);
        }
        return stringBuffer.toString();
    }

    public final void e() {
    }

    private static void a(Graphics graphics, int n2, int n3, int n4) {
        graphics.setColor(0);
        graphics.drawLine(n2, n3, n2, n3 + n4 - 1);
        graphics.drawLine(n2 + 9 - 1, n3, n2 + 9 - 1, n3 + n4 - 1);
        graphics.setColor(8209160);
        graphics.drawLine(n2 + 1, n3, n2 + 1, n3 + n4 - 1);
        graphics.drawLine(n2 + 9 - 2, n3, n2 + 9 - 2, n3 + n4 - 1);
        graphics.setColor(9983497);
        graphics.drawLine(n2 + 2, n3, n2 + 2, n3 + n4 - 1);
        graphics.drawLine(n2 + 9 - 3, n3, n2 + 9 - 3, n3 + n4 - 1);
        graphics.setColor(15440139);
        graphics.drawLine(n2 + 3, n3, n2 + 3, n3 + n4 - 1);
        graphics.drawLine(n2 + 9 - 4, n3, n2 + 9 - 4, n3 + n4 - 1);
        graphics.setColor(16304136);
        graphics.drawLine(n2 + 4, n3, n2 + 4, n3 + n4 - 1);
        graphics.setColor(16444773);
        graphics.fillRect(n2 + 1, n3 + 1, 7, 2);
        graphics.fillRect(n2 + 1, n3 + n4 - 1 - 2, 7, 2);
    }
}

