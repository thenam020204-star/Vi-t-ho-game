/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package a.a;

import a.a.m;
import a.c.b;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.a;

public final class l
extends m {
    private static int i = 16776624;
    private static int r = 5851198;
    private static int s = 3021840;
    private int t = i;
    private int u = r;
    private int v = s;
    private int w = 3739672;
    private int x = 6373665;
    private int y = 8870682;
    private int z = 2890254;
    private String[] A = null;
    private long B = 0L;
    private long C = 0L;
    private static int D = (main.a.e - 30 - 6) / a.c.b.b;
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
    public static Image h = null;
    private a.e.b U = null;
    private boolean V = true;
    private boolean W = true;
    private static int X = 3;

    private l(byte by) {
        super((byte)1);
        this.T = by;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        switch (this.T) {
            case 1: {
                int n4;
                int[] nArray = a.c.b.a(graphics);
                graphics.clipRect(this.a, this.b, this.c - 1, this.d - 1);
                this.b(graphics);
                this.c(graphics);
                n3 = a.c.b.b * this.E[this.I].length;
                int n5 = 0;
                int n6 = Math.min(D, this.F);
                int n7 = this.J;
                int n8 = this.K;
                for (n4 = 0; n4 < n6; ++n4) {
                    boolean c2;
                    n5 = this.b + 3 + n4 * a.c.b.b;
                    if (n7 == this.I && !c2) {
                        a.c.a.a(graphics, this.v, this.u, this.a + 3, n5, this.c - 6 - 1, n3 / 2 - 1);
                        a.c.a.a(graphics, this.u, this.v, this.a + 3, n5 + n3 / 2, this.c - 6 - 1, n3 / 2 - 1);
                    }
                    graphics.setColor(this.t);
                    graphics.drawString(this.E[n7][c2], this.a + 3 + this.G / 2, n5, 17);
                    if ((c2 += 1) < this.E[n7].length) continue;
                    c2 = false;
                    ++n7;
                }
                if (this.H) {
                    int n9;
                    int n10 = this.J;
                    l l2 = this;
                    if (n10 < 0 || n10 >= l2.E.length) {
                        n9 = 0;
                    } else {
                        int n11 = 0;
                        for (int i2 = 0; i2 < n10; ++i2) {
                            n11 += l2.E[i2].length;
                        }
                        n9 = n11;
                    }
                    n4 = n9;
                    a.c.b.a(graphics, b.a.L, this.F, n4 + this.K, D, this.a + this.c - 6, this.b + b.a.L.getHeight(), this.d - 2 * b.a.L.getHeight());
                }
                a.c.b.a(graphics, nArray);
                return;
            }
            case 2: {
                int[] nArray = a.c.b.a(graphics);
                graphics.clipRect(this.a, this.b, this.c - 1, this.d - 1);
                this.b(graphics);
                this.c(graphics);
                if (this.A != null) {
                    for (n3 = 0; n3 < this.A.length; ++n3) {
                        graphics.setColor(this.t);
                        graphics.drawString(this.A[n3], this.a + this.c / 2, this.b + 3 + n3 * a.c.b.b, 17);
                    }
                }
                a.c.b.a(graphics, nArray);
                return;
            }
            case 4: 
            case 7: {
                int n12;
                int n13;
                int[] nArray = a.c.b.a(graphics);
                graphics.clipRect(this.a, this.b, this.c, this.d);
                Graphics graphics2 = graphics;
                l l3 = this;
                graphics2.setColor(l3.w);
                graphics2.drawLine(l3.a, l3.b, l3.a + l3.c, l3.b);
                graphics2.setColor(l3.x);
                graphics2.drawLine(l3.a, l3.b + 1, l3.a + l3.c, l3.b + 1);
                graphics2.setColor(l3.y);
                graphics2.drawLine(l3.a, l3.b + 2, l3.a + l3.c, l3.b + 2);
                graphics2.setColor(l3.w);
                graphics2.drawLine(l3.a, l3.b + l3.d - 1, l3.a + l3.c, l3.b + l3.d - 1);
                graphics2.setColor(l3.x);
                graphics2.drawLine(l3.a, l3.b + 1 + l3.d - 3, l3.a + l3.c, l3.b + 1 + l3.d - 3);
                graphics2.setColor(l3.y);
                graphics2.drawLine(l3.a, l3.b + 2 + l3.d - 5, l3.a + l3.c, l3.b + 2 + l3.d - 5);
                graphics2 = graphics;
                l3 = this;
                a.c.a.a(graphics2, 3217664, 8352325, l3.a, l3.b + 3, l3.c, l3.d - 6 - 1);
                if (this.A != null) {
                    graphics2 = graphics;
                    l3 = this;
                    n13 = 0;
                    while ((n13 + 1) * a.c.b.b <= l3.M) {
                        ++n13;
                    }
                    int[] nArray2 = a.c.b.a(graphics2);
                    graphics2.clipRect(l3.l, l3.b + 3, l3.c, l3.d - 6);
                    boolean bl = false;
                    n12 = 0;
                    int n14 = 0;
                    if ((l3.P & 1) != 0) {
                        n14 = l3.l + 3;
                        n14 += l3.U == null ? 0 : 80;
                    } else {
                        n14 = l3.l + 9 + 3;
                    }
                    graphics2.setColor(0xFFFFFF);
                    int n15 = l3.L == l3.M ? X : X + 1;
                    for (int i3 = 0; i3 < n15 && n13 + i3 < l3.A.length; ++i3) {
                        n12 = n14;
                        for (int i4 = 0; i4 < l3.A[n13 + i3].length(); ++i4) {
                            char c2 = l3.A[n13 + i3].charAt(i4);
                            if (c2 == '{') {
                                graphics2.setColor(0xFF0000);
                                continue;
                            }
                            if (c2 == '}') {
                                graphics2.setColor(0xFFFFFF);
                                continue;
                            }
                            graphics2.drawChar(c2, n12, l3.b + 3 + (n13 + i3) * a.c.b.b - l3.M, 20);
                            n12 += a.c.b.a.charWidth(c2);
                        }
                    }
                    a.c.b.a(graphics2, nArray2);
                }
                a.c.b.a(graphics, nArray);
                if (this.j == 2) {
                    this.a(graphics);
                }
                graphics2 = graphics;
                l3 = this;
                if (l3.O != null && l3.O.trim().length() != 0) {
                    n13 = 0;
                    int n16 = 0;
                    int n17 = a.c.b.a.stringWidth(l3.O);
                    n12 = n17 + 6;
                    if ((l3.P & 4) != 0) {
                        n16 = l3.b + l3.d;
                        if (l3.N != null) {
                            n16 += l3.N.getHeight();
                        }
                    } else {
                        n16 = l3.b - a.c.b.b;
                        n16 -= 3;
                    }
                    n13 = (l3.P & 1) != 0 ? 0 : main.a.d - n12;
                    graphics2.setColor(8077832);
                    graphics2.fillRect(n13, n16, a.c.b.a.stringWidth(l3.O) + 5, a.c.b.b + 5);
                    graphics2.setColor(15440139);
                    graphics2.fillRect(n13 + 1, n16 + 1, a.c.b.a.stringWidth(l3.O) + 3, a.c.b.b + 3);
                    graphics2.setColor(3086592);
                    graphics2.fillRect(n13 + 2, n16 + 2, a.c.b.a.stringWidth(l3.O) + 1, a.c.b.b + 1);
                    a.c.a.a(graphics2, 0, l3.O, n13 + 3, n16 + 3, 20);
                }
                graphics2 = graphics;
                l3 = this;
                if (l3.U != null && l3.j != 3) {
                    n13 = 0;
                    n13 = (l3.P & 1) != 0 ? l3.l + 40 : l3.l + l3.c - 40;
                    int n17 = l3.b + l3.d - 3;
                    l3.U.a(graphics2, n13, n17);
                }
                this.a(graphics);
                if ((this.P & 1) != 0) {
                    a.a.l.a(graphics, this.a + this.c - 9, this.b - 3, this.d + 6);
                    break;
                }
                a.a.l.a(graphics, this.a, this.b - 3, this.d + 6);
                return;
            }
            case 5: {
                if (this.A != null) {
                    graphics.setColor(0);
                    graphics.fillRect(this.a, this.b, this.c, this.d);
                    graphics.setColor(0xFFFFFF);
                    n2 = this.b + this.d / 2 - this.A.length * a.c.b.b / 2;
                    for (n3 = 0; n3 < this.Q; ++n3) {
                        graphics.drawString(this.A[n3], this.a, n2 + n3 * a.c.b.b, 20);
                    }
                    graphics.drawString(this.A[this.Q].substring(0, this.R), this.a, n2 + this.Q * a.c.b.b, 20);
                }
                return;
            }
            case 6: {
                if (this.A == null || this.J < 0 || this.J >= this.A.length) {
                    return;
                }
                graphics.setColor(0);
                graphics.fillRect(this.a, this.b, this.c, this.d);
                n2 = 0;
                n3 = this.b;
                int n18 = this.d;
                if (this.N != null) {
                    if (this.N.getHeight() < this.d / 2) {
                        n3 += this.d / 2;
                        n18 -= this.d / 2;
                    } else {
                        n3 += this.N.getHeight();
                        n18 -= this.N.getHeight();
                    }
                }
                for (int i5 = this.J; i5 < this.A.length; ++i5) {
                    n2 = n3 + this.S + (i5 - this.J) * a.c.b.b;
                    if (n2 < n3) {
                        graphics.setColor(a.a.l.a(n2, n3 - a.c.b.b, a.c.b.b));
                    } else if (n2 > n3 + n18 - a.c.b.b) {
                        graphics.setColor(a.a.l.a(n2, n3 + n18, a.c.b.b));
                    } else {
                        graphics.setColor(0xFFFFFF);
                    }
                    if (n2 <= n3 - a.c.b.b) continue;
                    if (n2 > n3 + n18) break;
                    graphics.drawString(this.A[i5], this.a, n2, 20);
                }
                if (this.N == null) break;
                if (this.N.getHeight() < this.d / 2) {
                    graphics.drawImage(this.N, this.c / 2, this.d / 4, 3);
                    return;
                }
                graphics.drawImage(this.N, this.c / 2, 0, 17);
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
        int n2 = this.a + this.c / 2;
        int n3 = this.b + this.d;
        if (System.currentTimeMillis() / 1000L % 2L == 0L) {
            n3 += 3;
        }
        a.c.b.a(graphics, b.a.W, 0, 0, b.a.W.getWidth(), b.a.W.getHeight(), 6, n2, n3, 33);
    }

    public final void a(int n2, int n3) {
        if (this.j != 2) {
            return;
        }
        switch (this.T) {
            case 1: {
                switch (n2) {
                    case -7: {
                        if (!this.V) break;
                        this.a(2);
                        this.b();
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
                            this.a(1);
                            this.b();
                        }
                        this.d();
                    }
                }
                return;
            }
            case 2: {
                if (n2 != 0 || n3 != 0) {
                    this.b();
                }
                return;
            }
            case 4: 
            case 7: {
                if ((n3 == 48 || n2 == 53 || n2 == 56 || n3 == 35 || n3 == 999) && this.L == this.M) {
                    if (this.J + X >= this.A.length) {
                        this.b();
                    } else {
                        ++this.J;
                        this.L = this.M + a.c.b.b;
                    }
                }
                return;
            }
            case 5: {
                if (n2 == 53) {
                    if (this.Q == this.A.length - 1 && this.R == this.A[this.Q].length()) {
                        this.e();
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
                this.e();
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
        l l2 = this;
        if (n5 < 0 || n5 >= l2.E.length || n3 < 0 || n3 >= l2.E.length || n4 < 0 || n4 >= l2.E[n5].length) {
            n2 = -1;
        } else {
            n4 = l2.E[n5].length - n4;
            ++n5;
            while (n5 < n3) {
                n4 += l2.E[n5].length;
                ++n5;
            }
            n2 = n4 = n4 + l2.E[n3].length;
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

    protected final void a_() {
        this.C = System.currentTimeMillis();
        this.j = 2;
    }

    protected final void i() {
        this.e();
        super.a(true);
    }

    protected final void g() {
        switch (this.T) {
            case 2: {
                if (this.B != -1L && System.currentTimeMillis() > this.C + this.B) {
                    this.b();
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
                this.M = a.c.b.a(0, this.M, 0, this.L)[1];
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
                    if ((this.Q + 1) * a.c.b.b > this.d / 2) {
                        this.S -= a.c.b.b;
                    }
                }
                return;
            }
            case 6: {
                if (this.A == null) {
                    this.e();
                    this.a(true);
                    return;
                }
                --this.S;
                if (this.S >= -a.c.b.b) break;
                this.S = 0;
                ++this.J;
                if (this.J < this.A.length) break;
                this.a(true);
                this.e();
            }
        }
    }

    public static l a(String[] stringArray, byte by, byte by2, boolean bl) {
        return a.a.l.a(stringArray, (byte)0, by2, bl, true);
    }

    /*
     * WARNING - void declaration
     */
    public static l a(String[] stringArray, byte by, byte by2, boolean n2, boolean bl) {
        void var4_7;
        int n3;
        if (stringArray == null || stringArray.length == 0) {
            return null;
        }
        l l2 = new l(1);
        new l(1).V = n2;
        l2.W = bl;
        n2 = 0;
        int n4 = main.a.d - 30;
        l2.E = new String[stringArray.length][];
        l2.G = n4 - 6;
        for (n3 = 0; n3 < stringArray.length; ++n3) {
            l2.E[n3] = a.c.b.a(stringArray[n3], a.c.b.a, l2.G);
            n2 += l2.E[n3].length;
        }
        l2.F = n2;
        int n42 = (main.a.e - 30 - 6) / a.c.b.b;
        if (n2 > n42) {
            n3 = n42 * a.c.b.b + 6;
            l2.H = true;
            var4_7 += 10;
        } else {
            n3 = n2 * a.c.b.b + 6;
            l2.H = false;
        }
        l2.a = 15;
        l2.n = 15;
        l2.l = 15;
        l2.o = l2.b = main.a.e / 2 - n3 / 2;
        l2.p = l2.c = var4_7;
        l2.m = l2.c;
        l2.q = l2.d = n3;
        l2.e = by;
        l2.f = by2;
        l2.c = 0;
        return l2;
    }

    public static l a(String string, long l2) {
        l l3 = new l(2);
        new l(2).B = l2;
        int n2 = main.a.d - 30;
        l3.A = a.c.b.a(string, a.c.b.a, n2 - 6);
        int n3 = (main.a.e - 30 - 6) / a.c.b.b;
        int n4 = 0;
        n4 = l3.A.length > n3 ? n3 * a.c.b.b + 6 : l3.A.length * a.c.b.b + 6;
        l3.a = 15;
        l3.n = 15;
        l3.l = 15;
        l3.o = l3.b = main.a.e / 2 - n4 / 2;
        l3.p = l3.c = n2;
        l3.m = l3.c;
        l3.q = l3.d = n4;
        l3.c = 0;
        return l3;
    }

    public static l a(String string, String string2, a.e.b b2, byte by) {
        if (string == null) {
            string = "";
        }
        l l2 = new l(4);
        l2.p = l2.c = main.a.d;
        new l(4).m = l2.c;
        l2.q = l2.d = a.c.b.b * X + 6;
        l2.P = by;
        a.e.b b3 = b2;
        l l3 = l2;
        l3.b = (l3.P & 4) != 0 ? (b3 != null && l3.d - 3 < 80 ? 80 - l3.d + 3 : 0) : main.a.e - l3.d;
        l2.a = 0;
        l2.n = 0;
        l2.l = 0;
        l2.o = l2.b;
        l2.O = string2;
        l2.U = b2;
        l2.A = l2.U != null ? a.c.b.a(string, a.c.b.a, l2.c - 80 - 9) : a.c.b.a(string, a.c.b.a, l2.c - 9);
        if ((by & 2) != 0) {
            l2.a = l2.m + l2.l;
            if (b2 != null) {
                b2.a(true);
            }
        } else {
            l2.c = 0;
        }
        return l2;
    }

    public static l a(String string) {
        if (string == null) {
            throw new NullPointerException("message == null");
        }
        l l2 = new l(5);
        new l(5).q = l2.d = main.a.e;
        l2.p = l2.c = main.a.d;
        l2.m = l2.c;
        l2.a = 0;
        l2.n = 0;
        l2.l = 0;
        l2.b = 0;
        l2.o = 0;
        l2.A = a.c.b.a(string, a.c.b.a, l2.c - 6);
        l2.j = 2;
        return l2;
    }

    public static l a(String string, String string2) {
        l l2 = new l(6);
        new l(6).q = l2.d = main.a.e;
        l2.q = l2.c = main.a.d;
        l2.m = l2.c;
        l2.a = 0;
        l2.n = 0;
        l2.l = 0;
        l2.b = 0;
        l2.o = 0;
        l2.N = a.c.b.b(string2);
        l2.A = a.c.b.a(string, a.c.b.a, l2.c - 6);
        l2.j = 2;
        l2.S = l2.d;
        if (l2.N != null) {
            l2.S = l2.N.getHeight() < l2.d / 2 ? l2.d / 2 : l2.d - l2.N.getHeight();
        }
        l2.f = 0;
        return l2;
    }

    public final void b() {
        if (this.T == 4 && (this.P & 2) != 0) {
            this.n = this.l + this.m;
        } else {
            this.p = 0;
        }
        this.j = 3;
    }

    private void b(Graphics graphics) {
        graphics.setColor(this.z);
        graphics.fillRect(this.a, this.b, this.c - 1, this.d - 1);
    }

    private void c(Graphics graphics) {
        if (h == null) {
            return;
        }
        graphics.setColor(this.w);
        graphics.drawRect(this.a, this.b, this.c - 1, this.d - 1);
        graphics.setColor(this.x);
        graphics.drawRect(this.a + 1, this.b + 1, this.c - 3, this.d - 3);
        graphics.setColor(this.y);
        graphics.drawRect(this.a + 2, this.b + 2, this.c - 5, this.d - 5);
        a.c.b.a(graphics, h, 0, 0, h.getWidth(), h.getHeight(), 0, this.a, this.b, 20);
        a.c.b.a(graphics, h, 0, 0, h.getWidth(), h.getHeight(), 5, this.a + this.c - h.getWidth(), this.b, 20);
        a.c.b.a(graphics, h, 0, 0, h.getWidth(), h.getHeight(), 3, this.a + this.c - h.getWidth(), this.b + this.d - h.getHeight(), 20);
        a.c.b.a(graphics, h, 0, 0, h.getWidth(), h.getHeight(), 6, this.a, this.b + this.d - h.getHeight(), 20);
    }

    public final int j() {
        return this.I;
    }

    public final String c() {
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

