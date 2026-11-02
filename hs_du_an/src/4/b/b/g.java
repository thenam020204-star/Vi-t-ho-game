/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package b.b;

import a.f;
import b.a.d;
import b.b.m;
import b.e.c;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.a;

public final class g
extends m {
    private static int b = 16776624;
    private static int j = 5851198;
    private static int k = 3021840;
    private int t = b;
    private int u = j;
    private int v = k;
    private int w = 3739672;
    private int x = 6373665;
    private int y = 8870682;
    private int z = 2890254;
    private String[] A = null;
    private long B = 0L;
    private long C = 0L;
    private static int D = (main.a.e - 30 - 6) / b.e.a.b;
    private String[][] E = null;
    private int F = 0;
    private int G = 0;
    private boolean H = false;
    private int I = 0;
    private int J = 0;
    private int K = 0;
    private int L = 0;
    private int M = 0;
    private Image N = null;
    private String O = null;
    private byte P = (byte)9;
    private int Q = 0;
    private int R = 0;
    private int S = 0;
    private byte T = (byte)-1;
    public static Image a = null;
    private d U = null;
    private boolean V = true;
    private boolean W = true;
    private static int X = 3;

    private g(byte by) {
        super((byte)1);
        this.T = by;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        switch (this.T) {
            case 1: {
                int n4;
                int[] nArray = b.e.a.a(graphics);
                graphics.clipRect(this.c, this.d, this.e - 1, this.f - 1);
                this.b(graphics);
                this.c(graphics);
                n3 = b.e.a.b * this.E[this.I].length;
                int n5 = 0;
                int n6 = Math.min(D, this.F);
                int n7 = this.J;
                int n8 = this.K;
                for (n4 = 0; n4 < n6; ++n4) {
                    boolean c2;
                    n5 = this.d + 3 + n4 * b.e.a.b;
                    if (n7 == this.I && !c2) {
                        b.e.c.a(graphics, this.v, this.u, this.c + 3, n5, this.e - 6 - 1, n3 / 2 - 1);
                        b.e.c.a(graphics, this.u, this.v, this.c + 3, n5 + n3 / 2, this.e - 6 - 1, n3 / 2 - 1);
                    }
                    graphics.setColor(this.t);
                    graphics.drawString(this.E[n7][c2], this.c + 3 + this.G / 2, n5, 17);
                    if ((c2 += 1) < this.E[n7].length) continue;
                    c2 = false;
                    ++n7;
                }
                if (this.H) {
                    int n9;
                    int n10 = this.J;
                    g g2 = this;
                    if (n10 < 0 || n10 >= g2.E.length) {
                        n9 = 0;
                    } else {
                        int n11 = 0;
                        for (int i2 = 0; i2 < n10; ++i2) {
                            n11 += g2.E[i2].length;
                        }
                        n9 = n11;
                    }
                    n4 = n9;
                    b.e.a.a(graphics, a.f.L, this.F, n4 + this.K, D, this.c + this.e - 6, this.d + a.f.L.getHeight(), this.f - 2 * a.f.L.getHeight());
                }
                b.e.a.a(graphics, nArray);
                return;
            }
            case 2: {
                int[] nArray = b.e.a.a(graphics);
                graphics.clipRect(this.c, this.d, this.e - 1, this.f - 1);
                this.b(graphics);
                this.c(graphics);
                if (this.A != null) {
                    for (n3 = 0; n3 < this.A.length; ++n3) {
                        graphics.setColor(this.t);
                        graphics.drawString(this.A[n3], this.c + this.e / 2, this.d + 3 + n3 * b.e.a.b, 17);
                    }
                }
                b.e.a.a(graphics, nArray);
                return;
            }
            case 4: 
            case 7: {
                int n12;
                int n13;
                int[] nArray = b.e.a.a(graphics);
                graphics.clipRect(this.c, this.d, this.e, this.f);
                Graphics graphics2 = graphics;
                g g3 = this;
                graphics2.setColor(g3.w);
                graphics2.drawLine(g3.c, g3.d, g3.c + g3.e, g3.d);
                graphics2.setColor(g3.x);
                graphics2.drawLine(g3.c, g3.d + 1, g3.c + g3.e, g3.d + 1);
                graphics2.setColor(g3.y);
                graphics2.drawLine(g3.c, g3.d + 2, g3.c + g3.e, g3.d + 2);
                graphics2.setColor(g3.w);
                graphics2.drawLine(g3.c, g3.d + g3.f - 1, g3.c + g3.e, g3.d + g3.f - 1);
                graphics2.setColor(g3.x);
                graphics2.drawLine(g3.c, g3.d + 1 + g3.f - 3, g3.c + g3.e, g3.d + 1 + g3.f - 3);
                graphics2.setColor(g3.y);
                graphics2.drawLine(g3.c, g3.d + 2 + g3.f - 5, g3.c + g3.e, g3.d + 2 + g3.f - 5);
                graphics2 = graphics;
                g3 = this;
                b.e.c.a(graphics2, 3217664, 8352325, g3.c, g3.d + 3, g3.e, g3.f - 6 - 1);
                if (this.A != null) {
                    graphics2 = graphics;
                    g3 = this;
                    n13 = 0;
                    while ((n13 + 1) * b.e.a.b <= g3.M) {
                        ++n13;
                    }
                    int[] nArray2 = b.e.a.a(graphics2);
                    graphics2.clipRect(g3.n, g3.d + 3, g3.e, g3.f - 6);
                    boolean bl = false;
                    n12 = 0;
                    int n14 = 0;
                    if ((g3.P & 1) != 0) {
                        n14 = g3.n + 3;
                        n14 += g3.U == null ? 0 : 80;
                    } else {
                        n14 = g3.n + 9 + 3;
                    }
                    graphics2.setColor(0xFFFFFF);
                    int n15 = g3.L == g3.M ? X : X + 1;
                    for (int i3 = 0; i3 < n15 && n13 + i3 < g3.A.length; ++i3) {
                        n12 = n14;
                        for (int i4 = 0; i4 < g3.A[n13 + i3].length(); ++i4) {
                            char c2 = g3.A[n13 + i3].charAt(i4);
                            if (c2 == '{') {
                                graphics2.setColor(0xFF0000);
                                continue;
                            }
                            if (c2 == '}') {
                                graphics2.setColor(0xFFFFFF);
                                continue;
                            }
                            graphics2.drawChar(c2, n12, g3.d + 3 + (n13 + i3) * b.e.a.b - g3.M, 20);
                            n12 += b.e.a.a.charWidth(c2);
                        }
                    }
                    b.e.a.a(graphics2, nArray2);
                }
                b.e.a.a(graphics, nArray);
                if (this.l == 2) {
                    this.a(graphics);
                }
                graphics2 = graphics;
                g3 = this;
                if (g3.O != null && g3.O.trim().length() != 0) {
                    n13 = 0;
                    int n16 = 0;
                    int n17 = b.e.a.a.stringWidth(g3.O);
                    n12 = n17 + 6;
                    if ((g3.P & 4) != 0) {
                        n16 = g3.d + g3.f;
                        if (g3.N != null) {
                            n16 += g3.N.getHeight();
                        }
                    } else {
                        n16 = g3.d - b.e.a.b;
                        n16 -= 3;
                    }
                    n13 = (g3.P & 1) != 0 ? 0 : main.a.d - n12;
                    graphics2.setColor(8077832);
                    graphics2.fillRect(n13, n16, b.e.a.a.stringWidth(g3.O) + 5, b.e.a.b + 5);
                    graphics2.setColor(15440139);
                    graphics2.fillRect(n13 + 1, n16 + 1, b.e.a.a.stringWidth(g3.O) + 3, b.e.a.b + 3);
                    graphics2.setColor(3086592);
                    graphics2.fillRect(n13 + 2, n16 + 2, b.e.a.a.stringWidth(g3.O) + 1, b.e.a.b + 1);
                    b.e.c.a(graphics2, 0, g3.O, n13 + 3, n16 + 3, 20);
                }
                graphics2 = graphics;
                g3 = this;
                if (g3.U != null && g3.l != 3) {
                    n13 = 0;
                    n13 = (g3.P & 1) != 0 ? g3.n + 40 : g3.n + g3.e - 40;
                    int n17 = g3.d + g3.f - 3;
                    g3.U.a(graphics2, n13, n17);
                }
                this.a(graphics);
                if ((this.P & 1) != 0) {
                    b.b.g.a(graphics, this.c + this.e - 9, this.d - 3, this.f + 6);
                    break;
                }
                b.b.g.a(graphics, this.c, this.d - 3, this.f + 6);
                return;
            }
            case 5: {
                if (this.A != null) {
                    graphics.setColor(0);
                    graphics.fillRect(this.c, this.d, this.e, this.f);
                    graphics.setColor(0xFFFFFF);
                    n2 = this.d + this.f / 2 - this.A.length * b.e.a.b / 2;
                    for (n3 = 0; n3 < this.Q; ++n3) {
                        graphics.drawString(this.A[n3], this.c, n2 + n3 * b.e.a.b, 20);
                    }
                    graphics.drawString(this.A[this.Q].substring(0, this.R), this.c, n2 + this.Q * b.e.a.b, 20);
                }
                return;
            }
            case 6: {
                if (this.A == null || this.J < 0 || this.J >= this.A.length) {
                    return;
                }
                graphics.setColor(0);
                graphics.fillRect(this.c, this.d, this.e, this.f);
                n2 = 0;
                n3 = this.d;
                int n18 = this.f;
                if (this.N != null) {
                    if (this.N.getHeight() < this.f / 2) {
                        n3 += this.f / 2;
                        n18 -= this.f / 2;
                    } else {
                        n3 += this.N.getHeight();
                        n18 -= this.N.getHeight();
                    }
                }
                for (int i5 = this.J; i5 < this.A.length; ++i5) {
                    n2 = n3 + this.S + (i5 - this.J) * b.e.a.b;
                    if (n2 < n3) {
                        graphics.setColor(b.b.g.a(n2, n3 - b.e.a.b, b.e.a.b));
                    } else if (n2 > n3 + n18 - b.e.a.b) {
                        graphics.setColor(b.b.g.a(n2, n3 + n18, b.e.a.b));
                    } else {
                        graphics.setColor(0xFFFFFF);
                    }
                    if (n2 <= n3 - b.e.a.b) continue;
                    if (n2 > n3 + n18) break;
                    graphics.drawString(this.A[i5], this.c, n2, 20);
                }
                if (this.N == null) break;
                if (this.N.getHeight() < this.f / 2) {
                    graphics.drawImage(this.N, this.e / 2, this.f / 4, 3);
                    return;
                }
                graphics.drawImage(this.N, this.e / 2, 0, 17);
            }
        }
    }

    private static int a(int n2, int n3, int n4) {
        n2 = Math.abs(n2 - n3);
        n3 = 255 / n4;
        if ((n2 = n3 * n2) > 255) {
            n2 = 255;
        }
        return n2 << 16 | n2 << 8 | n2;
    }

    private void a(Graphics graphics) {
        int n2 = this.c + this.e / 2;
        int n3 = this.d + this.f;
        if (System.currentTimeMillis() / 1000L % 2L == 0L) {
            n3 += 3;
        }
        b.e.a.a(graphics, a.f.W, 0, 0, a.f.W.getWidth(), a.f.W.getHeight(), 6, n2, n3, 33);
    }

    public final void a(int n2, int n3) {
        if (this.l != 2) {
            return;
        }
        switch (this.T) {
            case 1: {
                switch (n2) {
                    case -7: {
                        if (!this.V) break;
                        this.b(2);
                        this.c();
                        break;
                    }
                    case 50: {
                        this.I = (this.I - 1 + this.E.length) % this.E.length;
                        this.l();
                        break;
                    }
                    case 56: {
                        this.I = (this.I + 1) % this.E.length;
                        this.l();
                        break;
                    }
                    case -6: 
                    case 53: {
                        if (this.W) {
                            this.b(1);
                            this.c();
                        }
                        this.e();
                    }
                }
                return;
            }
            case 2: {
                if (n2 != 0 || n3 != 0) {
                    this.c();
                }
                return;
            }
            case 4: 
            case 7: {
                if ((n3 == 48 || n2 == 53 || n2 == 56 || n3 == 35 || n3 == 999) && this.L == this.M) {
                    if (this.J + X >= this.A.length) {
                        this.c();
                    } else {
                        ++this.J;
                        this.L = this.M + b.e.a.b;
                    }
                }
                return;
            }
            case 5: {
                if (n2 == 53) {
                    if (this.Q == this.A.length - 1 && this.R == this.A[this.Q].length()) {
                        this.f();
                        this.a(true);
                    } else {
                        this.Q = this.A.length - 1;
                        this.R = this.A[this.Q].length();
                    }
                }
                return;
            }
            case 6: {
                if (n3 == 53) {
                    this.S -= 2;
                    return;
                }
                if (n2 != -7) break;
                this.f();
                this.a(true);
            }
        }
    }

    private void l() {
        int n2;
        if (this.I < this.J || this.I == this.J && this.K != 0) {
            this.J = this.I;
            this.K = 0;
            return;
        }
        int n3 = this.I;
        int n4 = this.K;
        int n5 = this.J;
        g g2 = this;
        if (n5 < 0 || n5 >= g2.E.length || n3 < 0 || n3 >= g2.E.length || n4 < 0 || n4 >= g2.E[n5].length) {
            n2 = -1;
        } else {
            n4 = g2.E[n5].length - n4;
            ++n5;
            while (n5 < n3) {
                n4 += g2.E[n5].length;
                ++n5;
            }
            n2 = n4 = n4 + g2.E[n3].length;
        }
        if (n2 <= D) {
            return;
        }
        this.J = this.I + 1;
        for (int i2 = D; i2 > 0; i2 -= this.E[this.J].length) {
            --this.J;
            if (i2 >= this.E[this.J].length) {
                this.K = 0;
                continue;
            }
            this.K = this.E[this.J].length - i2;
            break;
        }
    }

    protected final void h() {
        this.C = System.currentTimeMillis();
        this.l = 2;
    }

    protected final void i() {
        this.f();
        super.a(true);
    }

    protected final void b() {
        switch (this.T) {
            case 2: {
                if (this.B != -1L && System.currentTimeMillis() > this.C + this.B) {
                    this.c();
                }
                return;
            }
            case 1: {
                return;
            }
            case 4: 
            case 7: {
                if (this.U != null) {
                    this.U.b();
                }
                this.M = b.e.a.a(0, this.M, 0, this.L)[1];
                return;
            }
            case 5: {
                ++this.R;
                if (this.R >= this.A[this.Q].length()) {
                    ++this.Q;
                    this.R = 0;
                    if (this.Q >= this.A.length) {
                        this.Q = this.A.length - 1;
                        this.R = this.A[this.Q].length();
                        break;
                    }
                    if ((this.Q + 1) * b.e.a.b > this.f / 2) {
                        this.S -= b.e.a.b;
                    }
                }
                return;
            }
            case 6: {
                if (this.A == null) {
                    this.f();
                    this.a(true);
                    return;
                }
                --this.S;
                if (this.S >= -b.e.a.b) break;
                this.S = 0;
                ++this.J;
                if (this.J < this.A.length) break;
                this.a(true);
                this.f();
            }
        }
    }

    public static g a(String[] stringArray, byte by, byte by2, boolean bl) {
        return b.b.g.a(stringArray, (byte)0, by2, bl, true);
    }

    /*
     * WARNING - void declaration
     */
    public static g a(String[] stringArray, byte by, byte by2, boolean n2, boolean bl) {
        void var4_7;
        int n3;
        if (stringArray == null || stringArray.length == 0) {
            return null;
        }
        g g2 = new g(1);
        new g(1).V = n2;
        g2.W = bl;
        n2 = 0;
        int n4 = main.a.d - 30;
        g2.E = new String[stringArray.length][];
        g2.G = n4 - 6;
        for (n3 = 0; n3 < stringArray.length; ++n3) {
            g2.E[n3] = b.e.a.a(stringArray[n3], b.e.a.a, g2.G);
            n2 += g2.E[n3].length;
        }
        g2.F = n2;
        int n42 = (main.a.e - 30 - 6) / b.e.a.b;
        if (n2 > n42) {
            n3 = n42 * b.e.a.b + 6;
            g2.H = true;
            var4_7 += 10;
        } else {
            n3 = n2 * b.e.a.b + 6;
            g2.H = false;
        }
        g2.c = 15;
        g2.p = 15;
        g2.n = 15;
        g2.q = g2.d = main.a.e / 2 - n3 / 2;
        g2.r = g2.e = var4_7;
        g2.o = g2.e;
        g2.s = g2.f = n3;
        g2.g = by;
        g2.h = by2;
        g2.e = 0;
        return g2;
    }

    public static g a(String string, long l2) {
        g g2 = new g(2);
        new g(2).B = l2;
        int n2 = main.a.d - 30;
        g2.A = b.e.a.a(string, b.e.a.a, n2 - 6);
        int n3 = (main.a.e - 30 - 6) / b.e.a.b;
        int n4 = 0;
        n4 = g2.A.length > n3 ? n3 * b.e.a.b + 6 : g2.A.length * b.e.a.b + 6;
        g2.c = 15;
        g2.p = 15;
        g2.n = 15;
        g2.q = g2.d = main.a.e / 2 - n4 / 2;
        g2.r = g2.e = n2;
        g2.o = g2.e;
        g2.s = g2.f = n4;
        g2.e = 0;
        return g2;
    }

    public static g a(String string, String string2, d d2, byte by) {
        if (string == null) {
            string = "";
        }
        g g2 = new g(4);
        g2.r = g2.e = main.a.d;
        new g(4).o = g2.e;
        g2.s = g2.f = b.e.a.b * X + 6;
        g2.P = by;
        d d3 = d2;
        g g3 = g2;
        g3.d = (g3.P & 4) != 0 ? (d3 != null && g3.f - 3 < 80 ? 80 - g3.f + 3 : 0) : main.a.e - g3.f;
        g2.c = 0;
        g2.p = 0;
        g2.n = 0;
        g2.q = g2.d;
        g2.O = string2;
        g2.U = d2;
        g2.A = g2.U != null ? b.e.a.a(string, b.e.a.a, g2.e - 80 - 9) : b.e.a.a(string, b.e.a.a, g2.e - 9);
        if ((by & 2) != 0) {
            g2.c = g2.o + g2.n;
            if (d2 != null) {
                d2.a(true);
            }
        } else {
            g2.e = 0;
        }
        return g2;
    }

    public static g a(String string) {
        if (string == null) {
            throw new NullPointerException("message == null");
        }
        g g2 = new g(5);
        new g(5).s = g2.f = main.a.e;
        g2.r = g2.e = main.a.d;
        g2.o = g2.e;
        g2.c = 0;
        g2.p = 0;
        g2.n = 0;
        g2.d = 0;
        g2.q = 0;
        g2.A = b.e.a.a(string, b.e.a.a, g2.e - 6);
        g2.l = 2;
        return g2;
    }

    public static g a(String string, String string2) {
        g g2 = new g(6);
        new g(6).s = g2.f = main.a.e;
        g2.s = g2.e = main.a.d;
        g2.o = g2.e;
        g2.c = 0;
        g2.p = 0;
        g2.n = 0;
        g2.d = 0;
        g2.q = 0;
        g2.N = b.e.a.b(string2);
        g2.A = b.e.a.a(string, b.e.a.a, g2.e - 6);
        g2.l = 2;
        g2.S = g2.f;
        if (g2.N != null) {
            g2.S = g2.N.getHeight() < g2.f / 2 ? g2.f / 2 : g2.f - g2.N.getHeight();
        }
        g2.h = 0;
        return g2;
    }

    public final void c() {
        if (this.T == 4 && (this.P & 2) != 0) {
            this.p = this.n + this.o;
        } else {
            this.r = 0;
        }
        this.l = 3;
    }

    private void b(Graphics graphics) {
        graphics.setColor(this.z);
        graphics.fillRect(this.c, this.d, this.e - 1, this.f - 1);
    }

    private void c(Graphics graphics) {
        if (a == null) {
            return;
        }
        graphics.setColor(this.w);
        graphics.drawRect(this.c, this.d, this.e - 1, this.f - 1);
        graphics.setColor(this.x);
        graphics.drawRect(this.c + 1, this.d + 1, this.e - 3, this.f - 3);
        graphics.setColor(this.y);
        graphics.drawRect(this.c + 2, this.d + 2, this.e - 5, this.f - 5);
        b.e.a.a(graphics, a, 0, 0, a.getWidth(), a.getHeight(), 0, this.c, this.d, 20);
        b.e.a.a(graphics, a, 0, 0, a.getWidth(), a.getHeight(), 5, this.c + this.e - a.getWidth(), this.d, 20);
        b.e.a.a(graphics, a, 0, 0, a.getWidth(), a.getHeight(), 3, this.c + this.e - a.getWidth(), this.d + this.f - a.getHeight(), 20);
        b.e.a.a(graphics, a, 0, 0, a.getWidth(), a.getHeight(), 6, this.c, this.d + this.f - a.getHeight(), 20);
    }

    public final int j() {
        return this.I;
    }

    public final String a() {
        if (this.E == null || this.I < 0 || this.I >= this.E.length) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < this.E[this.I].length; ++i2) {
            stringBuffer.append(this.E[this.I][i2]);
        }
        return stringBuffer.toString();
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

