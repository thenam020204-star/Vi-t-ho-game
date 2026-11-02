/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package c;

import c.j;
import e.a.a;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class b
extends j {
    private int b = 3739672;
    private int c = 6373665;
    private int l = 8870682;
    private String[] m = null;
    private long n = 0L;
    private long o = 0L;
    private static int p = (main.b.e - 30 - 6) / e.a.b.b;
    private String[][] q = null;
    private int r = 0;
    private int s = 0;
    private boolean t = false;
    private int u = 0;
    private int v = 0;
    private int w = 0;
    private int x = 0;
    private int y = 0;
    private Image z = null;
    private String A = null;
    private byte B = (byte)9;
    private int C = 0;
    private int D = 0;
    private int E = 0;
    private byte F = (byte)-1;
    public static Image a = null;
    private e.b.b G = null;
    private int H = 0;
    private int I = 0;
    private boolean J = true;
    private boolean K = true;
    private int L = 0;

    private b(byte by) {
        super((byte)1);
        this.F = by;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        block0 : switch (this.F) {
            case 1: {
                int n4;
                int[] nArray = e.a.b.a(graphics);
                graphics.clipRect(this.e, this.f, this.g - 1, this.h - 1);
                this.b(graphics);
                this.c(graphics);
                n3 = e.a.b.b * this.q[this.u].length;
                int n5 = 0;
                int n6 = Math.min(p, this.r);
                int n7 = this.v;
                int n8 = this.w;
                for (n4 = 0; n4 < n6; ++n4) {
                    boolean c2;
                    n5 = this.f + 3 + n4 * e.a.b.b;
                    if (n7 == this.u && !c2) {
                        e.a.a.a(graphics, 5851198, 3021840, this.e + 3, n5, this.g - 6 - 1, n3 / 2 - 1);
                        e.a.a.a(graphics, 3021840, 5851198, this.e + 3, n5 + n3 / 2, this.g - 6 - 1, n3 / 2 - 1);
                    }
                    graphics.setColor(16776624);
                    graphics.drawString(this.q[n7][c2], this.e + 3 + this.s / 2, n5, 17);
                    if ((c2 += 1) < this.q[n7].length) continue;
                    c2 = false;
                    ++n7;
                }
                if (this.t) {
                    int n9;
                    int n10 = this.v;
                    b b2 = this;
                    if (n10 < 0 || n10 >= b2.q.length) {
                        n9 = 0;
                    } else {
                        int n11 = 0;
                        for (int i2 = 0; i2 < n10; ++i2) {
                            n11 += b2.q[i2].length;
                        }
                        n9 = n11;
                    }
                    n4 = n9;
                    e.a.b.a(graphics, b.a.O, this.r, n4 + this.w, p, this.e + this.g - 6, this.f + b.a.O.getHeight(), this.h - 2 * b.a.O.getHeight());
                }
                e.a.b.a(graphics, nArray);
                return;
            }
            case 2: {
                int[] nArray = e.a.b.a(graphics);
                graphics.clipRect(this.e, this.f, this.g - 1, this.h - 1);
                this.b(graphics);
                this.c(graphics);
                if (this.m != null) {
                    for (n3 = 0; n3 < this.m.length; ++n3) {
                        graphics.setColor(16776624);
                        graphics.drawString(this.m[n3], this.e + this.g / 2, this.f + 3 + n3 * e.a.b.b, 17);
                    }
                }
                e.a.b.a(graphics, nArray);
                return;
            }
            case 4: {
                int n12;
                int n13;
                int[] nArray = e.a.b.a(graphics);
                graphics.clipRect(this.e, this.f, this.g, this.h);
                Graphics graphics2 = graphics;
                b b3 = this;
                graphics2.setColor(b3.b);
                graphics2.drawLine(b3.e, b3.f, b3.e + b3.g, b3.f);
                graphics2.setColor(b3.c);
                graphics2.drawLine(b3.e, b3.f + 1, b3.e + b3.g, b3.f + 1);
                graphics2.setColor(b3.l);
                graphics2.drawLine(b3.e, b3.f + 2, b3.e + b3.g, b3.f + 2);
                graphics2.setColor(b3.b);
                graphics2.drawLine(b3.e, b3.f + b3.h - 1, b3.e + b3.g, b3.f + b3.h - 1);
                graphics2.setColor(b3.c);
                graphics2.drawLine(b3.e, b3.f + 1 + b3.h - 3, b3.e + b3.g, b3.f + 1 + b3.h - 3);
                graphics2.setColor(b3.l);
                graphics2.drawLine(b3.e, b3.f + 2 + b3.h - 5, b3.e + b3.g, b3.f + 2 + b3.h - 5);
                graphics2 = graphics;
                b3 = this;
                e.a.a.a(graphics2, 3217664, 8352325, b3.e, b3.f + 3, b3.g, b3.h - 6 - 1);
                if (this.m != null) {
                    graphics2 = graphics;
                    b3 = this;
                    n13 = 0;
                    while ((n13 + 1) * e.a.b.b <= b3.y) {
                        ++n13;
                    }
                    int[] nArray2 = e.a.b.a(graphics2);
                    graphics2.clipRect(b3.L, b3.f + 3, b3.g, b3.h - 6);
                    boolean bl = false;
                    n12 = 0;
                    int n14 = 0;
                    if ((b3.B & 1) != 0) {
                        n14 = b3.L + 3;
                        n14 += b3.G == null ? 0 : 80;
                    } else {
                        n14 = b3.L + 9 + 3;
                    }
                    graphics2.setColor(0xFFFFFF);
                    int n15 = b3.x == b3.y ? 3 : 4;
                    for (int i3 = 0; i3 < n15 && n13 + i3 < b3.m.length; ++i3) {
                        n12 = n14;
                        for (int i4 = 0; i4 < b3.m[n13 + i3].length(); ++i4) {
                            char c2 = b3.m[n13 + i3].charAt(i4);
                            if (c2 == '{') {
                                graphics2.setColor(0xFF0000);
                                continue;
                            }
                            if (c2 == '}') {
                                graphics2.setColor(0xFFFFFF);
                                continue;
                            }
                            graphics2.drawChar(c2, n12, b3.f + 3 + (n13 + i3) * e.a.b.b - b3.y, 20);
                            n12 += e.a.b.a.charWidth(c2);
                        }
                    }
                    e.a.b.a(graphics2, nArray2);
                }
                e.a.b.a(graphics, nArray);
                if (this.i == 2) {
                    this.a(graphics);
                }
                graphics2 = graphics;
                b3 = this;
                if (b3.G != null) {
                    n13 = 0;
                    n13 = (b3.B & 1) != 0 ? b3.L + 40 : b3.L + b3.g - 40;
                    int n16 = b3.f + b3.h - 3;
                    b3.G.a(graphics2, n13, n16);
                }
                graphics2 = graphics;
                b3 = this;
                if (b3.A != null && b3.A.trim().length() != 0) {
                    n13 = 0;
                    int n17 = 0;
                    int n16 = e.a.b.a.stringWidth(b3.A);
                    n12 = n16 + 6;
                    if ((b3.B & 4) != 0) {
                        n17 = b3.f + b3.h;
                    } else {
                        n17 = b3.f - e.a.b.b;
                        n17 -= 3;
                    }
                    n13 = (b3.B & 1) != 0 ? 0 : main.b.d - n12;
                    graphics2.setColor(8077832);
                    graphics2.fillRect(n13, n17, e.a.b.a.stringWidth(b3.A) + 5, e.a.b.b + 5);
                    graphics2.setColor(15440139);
                    graphics2.fillRect(n13 + 1, n17 + 1, e.a.b.a.stringWidth(b3.A) + 3, e.a.b.b + 3);
                    graphics2.setColor(3086592);
                    graphics2.fillRect(n13 + 2, n17 + 2, e.a.b.a.stringWidth(b3.A) + 1, e.a.b.b + 1);
                    e.a.a.a(graphics2, 0, b3.A, n13 + 3, n17 + 3, 20);
                }
                this.a(graphics);
                if ((this.B & 1) != 0) {
                    c.b.a(graphics, this.e + this.g - 9, this.f - 3, this.h + 6);
                    break;
                }
                c.b.a(graphics, this.e, this.f - 3, this.h + 6);
                return;
            }
            case 5: {
                if (this.m != null) {
                    graphics.setColor(0);
                    graphics.fillRect(this.e, this.f, this.g, this.h);
                    graphics.setColor(0xFFFFFF);
                    n2 = this.f + this.h / 2 - this.m.length * e.a.b.b / 2;
                    for (n3 = 0; n3 < this.C; ++n3) {
                        graphics.drawString(this.m[n3], this.e, n2 + n3 * e.a.b.b, 20);
                    }
                    graphics.drawString(this.m[this.C].substring(0, this.D), this.e, n2 + this.C * e.a.b.b, 20);
                }
                return;
            }
            case 6: {
                if (this.m == null || this.v < 0 || this.v >= this.m.length) {
                    return;
                }
                graphics.setColor(0);
                graphics.fillRect(this.e, this.f, this.g, this.h);
                n2 = 0;
                for (n3 = this.v; n3 < this.m.length; ++n3) {
                    n2 = this.f + this.E + (n3 - this.v) * e.a.b.b;
                    if (n2 < this.f) {
                        graphics.setColor(c.b.a(n2, this.f - e.a.b.b, e.a.b.b));
                    } else if (n2 > this.f + this.h - e.a.b.b) {
                        graphics.setColor(c.b.a(n2, this.f + this.h, e.a.b.b));
                    } else {
                        graphics.setColor(0xFFFFFF);
                    }
                    if (n2 > this.f + this.h) break block0;
                    graphics.drawString(this.m[n3], this.e, n2, 20);
                }
                break;
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
        int n2 = this.e + this.g / 2;
        int n3 = this.f + this.h;
        if (System.currentTimeMillis() / 1000L % 2L == 0L) {
            n3 += 3;
        }
        e.a.b.a(graphics, b.a.ab, 0, 0, b.a.ab.getWidth(), b.a.ab.getHeight(), 6, n2, n3, 33);
    }

    public final void a(int n2, int n3) {
        if (this.i != 2) {
            return;
        }
        switch (this.F) {
            case 1: {
                switch (n2) {
                    case -7: {
                        if (this.J) {
                            this.a(2);
                            this.b();
                        }
                        this.i();
                        break;
                    }
                    case 50: {
                        this.u = (this.u - 1 + this.q.length) % this.q.length;
                        this.k();
                        break;
                    }
                    case 56: {
                        this.u = (this.u + 1) % this.q.length;
                        this.k();
                        break;
                    }
                    case -6: 
                    case 53: {
                        if (this.K) {
                            this.a(1);
                            this.b();
                        }
                        this.g();
                    }
                }
                return;
            }
            case 2: {
                if (n2 == 53 || n2 == -6 || n3 == 48 || n2 == 48) {
                    this.b();
                }
                return;
            }
            case 4: {
                if ((n3 == 48 || n2 == 53 || n2 == 56 || n3 == 35 || n3 == 999) && this.x == this.y) {
                    if (this.v + 3 >= this.m.length) {
                        this.i = 3;
                    } else {
                        ++this.v;
                        this.x = this.y + e.a.b.b;
                    }
                }
                return;
            }
            case 5: {
                if (n2 == 53) {
                    if (this.C == this.m.length - 1 && this.D == this.m[this.C].length()) {
                        this.h();
                        this.a(true);
                    } else {
                        this.C = this.m.length - 1;
                        this.D = this.m[this.C].length();
                    }
                }
                return;
            }
            case 6: {
                n2 = n3;
                if (n2 != 53) break;
                this.E -= 2;
            }
        }
    }

    private void k() {
        int n2;
        if (this.u < this.v || this.u == this.v && this.w != 0) {
            this.v = this.u;
            this.w = 0;
            return;
        }
        int n3 = this.u;
        int n4 = this.w;
        int n5 = this.v;
        b b2 = this;
        if (n5 < 0 || n5 >= b2.q.length || n3 < 0 || n3 >= b2.q.length || n4 < 0 || n4 >= b2.q[n5].length) {
            n2 = -1;
        } else {
            n4 = b2.q[n5].length - n4;
            ++n5;
            while (n5 < n3) {
                n4 += b2.q[n5].length;
                ++n5;
            }
            n2 = n4 = n4 + b2.q[n3].length;
        }
        if (n2 <= p) {
            return;
        }
        this.v = this.u + 1;
        for (int i2 = p; i2 > 0; i2 -= this.q[this.v].length) {
            --this.v;
            if (i2 >= this.q[this.v].length) {
                this.w = 0;
                continue;
            }
            this.w = this.q[this.v].length - i2;
            break;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void a() {
        switch (this.i) {
            case 1: {
                int[] nArray = e.a.b.a(this.g, 0, this.I, 0);
                this.g = nArray[0];
                nArray = e.a.b.a(this.e, 0, this.H, 0);
                this.e = nArray[0];
                if (this.g != this.I) return;
                if (this.e != this.H) return;
                this.o = System.currentTimeMillis();
                this.i = 2;
                return;
            }
            case 2: {
                switch (this.F) {
                    case 2: {
                        b b2 = this;
                        if (b2.n == -1L) return;
                        if (System.currentTimeMillis() <= b2.o + b2.n) return;
                        b2.b();
                        return;
                    }
                    case 1: {
                        return;
                    }
                    case 4: {
                        b b3 = this;
                        if (b3.G != null) {
                            b3.G.b();
                        }
                        b3.y = e.a.b.a(0, b3.y, 0, b3.x)[1];
                        return;
                    }
                    case 5: {
                        b b4 = this;
                        ++b4.D;
                        if (b4.D < b4.m[b4.C].length()) return;
                        ++b4.C;
                        b4.D = 0;
                        if (b4.C < b4.m.length) {
                            if ((b4.C + 1) * e.a.b.b <= b4.h / 2) return;
                            b4.E -= e.a.b.b;
                            return;
                        }
                        b4.C = b4.m.length - 1;
                        b4.D = b4.m[b4.C].length();
                        break;
                    }
                    case 6: {
                        b b5 = this;
                        if (b5.m == null) {
                            b5.h();
                            b5.a(true);
                            return;
                        }
                        --b5.E;
                        if (b5.E >= -e.a.b.b) return;
                        b5.E = 0;
                        ++b5.v;
                        if (b5.v < b5.m.length) return;
                        b5.a(true);
                        b5.h();
                    }
                }
                return;
            }
            case 3: {
                int[] nArray = e.a.b.a(this.g, 0, this.I, 0);
                this.g = nArray[0];
                nArray = e.a.b.a(this.e, 0, this.H, 0);
                this.e = nArray[0];
                if (this.g != this.I) return;
                if (this.e != this.H) return;
                this.h();
                super.a(true);
                return;
            }
        }
    }

    public static b a(String[] stringArray, byte by, byte by2, boolean bl) {
        return c.b.a(stringArray, by, by2, bl, true);
    }

    /*
     * WARNING - void declaration
     */
    public static b a(String[] stringArray, byte by, byte by2, boolean n2, boolean bl) {
        void var4_7;
        int n3;
        if (stringArray == null || stringArray.length == 0) {
            return null;
        }
        b b2 = new b(1);
        new b(1).J = n2;
        b2.K = bl;
        n2 = 0;
        int n4 = main.b.d - 30;
        b2.q = new String[stringArray.length][];
        b2.s = n4 - 6;
        for (n3 = 0; n3 < stringArray.length; ++n3) {
            b2.q[n3] = e.a.b.a(stringArray[n3], e.a.b.a, b2.s);
            n2 += b2.q[n3].length;
        }
        b2.r = n2;
        int n42 = (main.b.e - 30 - 6) / e.a.b.b;
        if (n2 > n42) {
            n3 = n42 * e.a.b.b + 6;
            b2.t = true;
            var4_7 += 10;
        } else {
            n3 = n2 * e.a.b.b + 6;
            b2.t = false;
        }
        b2.e = 15;
        b2.f = main.b.e / 2 - n3 / 2;
        b2.g = var4_7;
        b2.h = n3;
        b2.j = by;
        b2.k = by2;
        b2.I = b2.g;
        b2.g = 0;
        b2.H = b2.e;
        return b2;
    }

    public static b a(String string, long l2) {
        b b2 = new b(2);
        new b(2).n = l2;
        int n2 = main.b.d - 30;
        b2.m = e.a.b.a(string, e.a.b.a, n2 - 6);
        int n3 = (main.b.e - 30 - 6) / e.a.b.b;
        int n4 = 0;
        n4 = b2.m.length > n3 ? n3 * e.a.b.b + 6 : b2.m.length * e.a.b.b + 6;
        b2.e = 15;
        b2.f = main.b.e / 2 - n4 / 2;
        b2.g = n2;
        b2.h = n4;
        b2.I = b2.g;
        b2.g = 0;
        b2.H = b2.e;
        return b2;
    }

    public static b a(String string, String string2, e.b.b b2, byte by) {
        if (string == null) {
            string = "";
        }
        b b3 = new b(4);
        new b(4).g = main.b.d;
        b3.h = e.a.b.b * 3 + 6;
        b3.B = by;
        e.b.b b4 = b2;
        b b5 = b3;
        b5.f = (b5.B & 4) != 0 ? (b4 != null && b5.h - 3 < 80 ? 80 - b5.h + 3 : 0) : main.b.e - b5.h;
        b3.A = string2;
        b3.G = b2;
        b3.m = b3.G != null ? e.a.b.a(string, e.a.b.a, b3.g - 80 - 9) : e.a.b.a(string, e.a.b.a, b3.g - 9);
        b3.L = b3.e;
        if ((by & 2) != 0) {
            b3.H = b3.e;
            b3.e += b3.g;
            b3.I = b3.g;
            if (b2 != null) {
                b2.a(true);
            }
        } else {
            b3.I = b3.g;
            b3.g = 0;
            b3.H = b3.e;
        }
        b3.b = 8077832;
        b3.c = 15440139;
        b3.l = 16304136;
        return b3;
    }

    public static b a(String string) {
        if (string == null) {
            throw new NullPointerException("message == null");
        }
        b b2 = new b(5);
        new b(5).h = main.b.e;
        b2.g = main.b.d;
        b2.e = 0;
        b2.f = 0;
        b2.m = e.a.b.a(string, e.a.b.a, b2.g - 6);
        b2.i = 2;
        return b2;
    }

    public static b b(String string) {
        b b2 = new b(6);
        new b(6).h = main.b.e;
        b2.g = main.b.d;
        b2.e = 0;
        b2.f = 0;
        b2.m = e.a.b.a(string, e.a.b.a, b2.g - 6);
        b2.i = 2;
        b2.E = b2.h;
        return b2;
    }

    public final void b() {
        if (this.d == 4 && (this.B & 2) != 0) {
            this.H = this.e + this.g;
        } else {
            this.I = 0;
        }
        this.i = 3;
    }

    private void b(Graphics graphics) {
        graphics.setColor(2890254);
        graphics.fillRect(this.e, this.f, this.g, this.h);
    }

    private void c(Graphics graphics) {
        if (a == null) {
            return;
        }
        graphics.setColor(this.b);
        graphics.drawRect(this.e, this.f, this.g - 1, this.h - 1);
        graphics.setColor(this.c);
        graphics.drawRect(this.e + 1, this.f + 1, this.g - 3, this.h - 3);
        graphics.setColor(this.l);
        graphics.drawRect(this.e + 2, this.f + 2, this.g - 5, this.h - 5);
        e.a.b.a(graphics, a, 0, 0, a.getWidth(), a.getHeight(), 0, this.e, this.f, 20);
        e.a.b.a(graphics, a, 0, 0, a.getWidth(), a.getHeight(), 5, this.e + this.g - a.getWidth(), this.f, 20);
        e.a.b.a(graphics, a, 0, 0, a.getWidth(), a.getHeight(), 3, this.e + this.g - a.getWidth(), this.f + this.h - a.getHeight(), 20);
        e.a.b.a(graphics, a, 0, 0, a.getWidth(), a.getHeight(), 6, this.e, this.f + this.h - a.getHeight(), 20);
    }

    public final int c() {
        return this.u;
    }

    public final String d() {
        if (this.q == null || this.u < 0 || this.u >= this.q.length) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < this.q[this.u].length; ++i2) {
            stringBuffer.append(this.q[this.u][i2]);
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

