/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package g;

import b.i;
import b.j;
import b.p;
import b.s;
import b.t;
import b.u;
import g.a;
import g.c;
import g.e;
import g.f;
import g.g;
import g.h;
import g.k;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.MainMIDlet;
import main.d;

public final class b
extends main.c
implements main.a {
    private static byte[] h = new byte[]{1, 2, 5};
    private static final short[][] i = new short[][]{{209, 138}, {181, 170}, {209, 200}, {141, 160}, {141, 190}};
    private static final short[][] j = new short[][]{{54, 138}, {81, 185}, {41, 215}};
    private Image k = null;
    private Image l = null;
    private Image m = null;
    private Image n = null;
    private Image o = null;
    private Image p = null;
    private Image q = null;
    public static Image d = null;
    public static Image[] e = null;
    public static Image f = null;
    public static Image g = null;
    private Vector r = new Vector();
    private d.g[] s = null;
    private d.d[] t = null;
    private d.b[] u = null;
    private d.b v = null;
    private int w = 0;
    private int[] x = null;
    private int y = -1;
    private int z = -1;
    private int A = -1;
    private d B = null;
    private Hashtable C = null;
    private b.c D = null;
    private s E = null;
    private s F = null;
    private s G = null;
    private d.g H = null;
    private d.g I = null;
    private c.b J = null;
    private c.a K = null;
    private b.a L = null;
    private c.f M = null;
    private byte N = 0;
    private byte O = 1;
    private byte P = 1;
    private int Q = 0;
    private f R = null;
    private boolean S = true;
    private byte T = (byte)3;
    private int U = -1;
    private b.c V = null;
    private boolean W = false;
    private byte X = (byte)-1;
    private int Y = 0;
    private int Z = 0;
    private s aa = null;

    public b(d d2) {
        super((byte)4);
        this.B = d2;
        this.R = new f(this);
        this.R.a(this);
        this.X = (byte)3;
    }

    public final void a() {
        this.b = true;
        d.a.a();
        d.a.y.i();
        d.a.y.j();
        System.gc();
        h.b.a(100L);
        super.a(10);
        main.b.a.a();
        this.C = new Hashtable();
        Object object = this;
        e.a a2 = e.a.a("/b.pak");
        ((b)object).k = a2.c(((b)object).B.e + "_t.png");
        ((b)object).l = a2.c(((b)object).B.f + "_b.png");
        if (((b)object).k == null || ((b)object).l == null) {
            ((b)object).l = a2.c("0_b.png");
            ((b)object).k = a2.c("0_t.png");
        }
        ((b)object).m = a2.c("bg.png");
        ((b)object).n = a2.c("pointer.png");
        d = a2.c("status.png");
        Image[] imageArray = new Image[3];
        e = imageArray;
        imageArray[0] = a2.c("a.png");
        g.b.e[1] = a2.c("d.png");
        g.b.e[2] = a2.c("s.png");
        f = a2.c("up.png");
        g = a2.c("down.png");
        ((b)object).p = a2.c("miss.png");
        ((b)object).o = a2.c("statusBorder.png");
        ((b)object).q = a2.c("angry.png");
        super.a(20);
        main.b.a.a();
        this.x = new int[2];
        this.x[0] = d.a.C[0].l();
        this.x[1] = d.a.C[0].m();
        this.y = d.a.C[0].e;
        object = d.a.C[0];
        this.z = ((d.b)object).d == null ? -1 : ((d.b)object).d.d();
        this.A = d.a.C[0].g();
        this.v();
        this.k();
        this.U = d.a.af;
        if (this.B.d >= 0) {
            d.a.c(this.B.d, -1);
        }
        super.a(100);
        main.b.a.a();
        this.b = false;
    }

    public final void b() {
        short s2;
        this.b = true;
        d.a.a();
        super.a(0);
        super.b();
        b b2 = this;
        this.l = null;
        b2.k = null;
        b2.m = null;
        b2.n = null;
        d = null;
        e = null;
        d = null;
        e = null;
        f = null;
        g = null;
        this.C = null;
        this.D = null;
        b2 = this;
        for (int i2 = 0; i2 < d.a.C.length; ++i2) {
            if (b2.s[i2].z()) {
                b2.s[i2].a((byte)2, 1);
            }
            b2.s[i2].b(b2.y, b2.A);
            int n2 = b2.z;
            d.g g2 = b2.s[i2];
            if (g2.d != null) {
                g2.d.a(n2);
            }
            d.a.C[i2].b(b2.x[0]);
            d.a.C[i2].c(b2.x[1]);
        }
        d.a.y.a(d.a.C[0]);
        this.s = null;
        for (s2 = 0; s2 < this.t.length; ++s2) {
            if (this.t[s2] == null) continue;
            this.t[s2].r();
        }
        this.t = null;
        super.a(30);
        main.b.a.a();
        d.a.y.k();
        d.a.y.q();
        super.a(60);
        main.b.a.a();
        d.a.y.h();
        super.a(100);
        main.b.a.a();
        s2 = d.a.t;
        d.f f2 = d.a.y.b(s2);
        if (f2 != null) {
            f2.a(false);
            f2.b(true);
            f2.c(false);
        }
        this.B.c();
        d.a.c(this.U, -1);
        this.b = false;
    }

    private void k() {
        this.t = new d.d[3];
        this.t[0] = this.B.a == 0 ? null : d.a.c(this.B.a);
        this.t[1] = this.B.b == 0 ? null : d.a.c(this.B.b);
        this.t[2] = this.B.c == 0 ? null : d.a.c(this.B.c);
        int n2 = 0;
        int n3 = 0;
        for (int i2 = 0; i2 < this.t.length; ++i2) {
            if (this.t[i2] == null) continue;
            this.Q = i2;
            n3 += this.t[i2].e(0);
            ++n2;
            this.C.put(this.t[i2], new Vector());
            this.t[i2].a((e.a)null);
            this.t[i2].b(0, -1);
            this.t[i2].b((int)j[i2][0]);
            this.t[i2].c((int)j[i2][1]);
            this.t[i2].v();
        }
        this.w = n3 / n2;
    }

    public final void a(Graphics graphics) {
        main.c.c(graphics);
        if (this.b) {
            main.c.a(graphics, this.c);
            return;
        }
        Graphics graphics2 = graphics;
        b b2 = this;
        if (b2.k != null) {
            graphics2.drawImage(b2.k, 0, 0, 20);
        }
        if (b2.l != null) {
            graphics2.drawImage(b2.l, 0, b2.k.getHeight(), 20);
        }
        graphics2 = graphics;
        b2 = this;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        if (b2.m != null) {
            n4 = b2.m.getWidth();
            n5 = b2.m.getHeight();
        } else {
            n4 = main.b.c / 3;
            n5 = main.b.c / 3;
        }
        for (int i2 = 0; i2 < 3; ++i2) {
            n2 = i2 * n4;
            n3 = main.b.d - n5;
            d.g g2 = b2.s[i2];
            int n6 = n3;
            int n7 = n2;
            Graphics graphics3 = graphics2;
            b b3 = b2;
            if (b3.m != null) {
                graphics3.drawImage(b3.m, n7, n6, 20);
            } else {
                graphics3.setColor(15454341);
                graphics3.fillRect(n7, n6, main.b.c / 3, main.b.c / 3);
            }
            if (g2 == null) continue;
            int n8 = n7 + 5;
            int n9 = n6 + 4;
            int n10 = g2.u();
            int n11 = 6;
            n11 = 35;
            int n12 = n9;
            int n13 = n8;
            Graphics graphics4 = graphics3;
            graphics4.setColor(0);
            graphics4.fillRect(n13, n12, 35, 6);
            int n14 = 6;
            int n15 = n10 * 35 / 100;
            int n16 = n12;
            n10 = n13;
            n12 = 0xFFFA00;
            n13 = 16729601;
            if (n15 != 0) {
                int n17;
                int n18;
                int n19 = 0 / n15;
                if (n19 == 0) {
                    n19 = -1;
                }
                if ((n18 = 180 / n15) == 0) {
                    n18 = 1;
                }
                if ((n17 = -1 / n15) == 0) {
                    n17 = -1;
                }
                int n20 = 0;
                int n21 = 0;
                int n22 = 0;
                for (int i3 = 0; i3 < n15; ++i3) {
                    graphics4.setColor(n13);
                    graphics4.drawLine(n10 + i3, n16, n10 + i3, n16 + n14 - 1);
                    if (n13 == n12) continue;
                    n20 = 255 + i3 * n19;
                    if (n20 > 255) {
                        n20 = 255;
                    } else if (n20 < 0) {
                        n20 = 0;
                    }
                    n21 = 70 + i3 * n18;
                    if (n21 > 255) {
                        n21 = 255;
                    } else if (n21 < 0) {
                        n21 = 0;
                    }
                    n22 = 1 + i3 * n17;
                    if (n22 > 255) {
                        n22 = 255;
                    } else if (n22 < 0) {
                        n22 = 0;
                    }
                    n13 = n20 << 16 | n21 << 8 | n22;
                }
            }
            g.b.a(graphics3, 35, n8, n9 += 9, g2.t(), d.a.h);
            graphics3.drawImage(d.a.U, n7 + 6, n6 + 23, 20);
            n8 = n7 + 29;
            n9 = n6 + 23;
            h.b.a(graphics3, d.a.H, g2.e(2), n8, n9);
            g.b.a(graphics3, 35, n8 + 10, n9 += d.a.H.getHeight(), g2.e(2) * 100 / g2.e(3), d.a.e);
            h.b.a(graphics3, d.a.H, g2.e(4), n8, n9 += 2 + d.a.e.length);
            g.b.a(graphics3, 35, n8 + 10, n9 += d.a.H.getHeight(), g2.e(4) * 100 / g2.e(5), d.a.f);
            h.b.a(graphics3, d.a.H, g2.e(6), n8, n9 += 2 + d.a.f.length);
            g.b.a(graphics3, 35, n8 + 10, n9 += d.a.H.getHeight(), g2.e(6) * 100 / g2.e(7), d.a.g);
            n8 = n7 + 80 - 12;
            g2.a(graphics3, n8, n6 + 21 + 5, (short)20);
        }
        graphics2 = graphics;
        b2 = this;
        n2 = main.b.d - (b2.m == null ? main.b.c / 3 : b2.m.getHeight());
        for (n3 = 0; n3 < n2; ++n3) {
            if (b2.S) {
                g.b.a(graphics2, (d.b[])b2.t, n3);
                g.b.a(graphics2, (d.b[])b2.s, n3);
                continue;
            }
            g.b.a(graphics2, (d.b[])b2.s, n3);
            g.b.a(graphics2, (d.b[])b2.t, n3);
        }
        if (b2.u != null) {
            for (n3 = 0; n3 < b2.u.length; ++n3) {
                if (b2.u[n3] == null) continue;
                b2.u[n3].a(graphics2, b2.u[n3].l(), b2.u[n3].m());
            }
        }
        graphics2 = graphics;
        b2 = this;
        if (b2.X != 1) {
            for (n2 = 0; n2 < b2.t.length; ++n2) {
                if (b2.t[n2] == null || b2.t[n2].z() || b2.t[n2].d == null) continue;
                b2.a(graphics2, b2.t[n2], b2.t[n2].l() + b2.t[n2].n / 2, b2.t[n2].m() - b2.t[n2].o, false);
            }
            for (n2 = 0; n2 < b2.s.length; ++n2) {
                if (b2.s[n2] == null || b2.s[n2].z() || b2.s[n2].d == null) continue;
                b2.a(graphics2, b2.s[n2], b2.s[n2].l() - b2.s[n2].n / 2 - b2.o.getWidth() / 2, b2.s[n2].m() - b2.s[n2].o * 3 / 4, true);
            }
        }
        if (this.H != null) {
            h.a.a(graphics, 0, this.H.B, main.b.c, 0, 24);
        }
        this.b(graphics);
    }

    private static void a(Graphics graphics, d.b[] bArray, int n2) {
        if (bArray == null) {
            return;
        }
        for (int i2 = 0; i2 < bArray.length; ++i2) {
            if (bArray[i2] == null || bArray[i2].m() != n2) continue;
            bArray[i2].a(graphics, bArray[i2].l(), bArray[i2].m());
        }
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int[] nArray) {
        if (nArray == null) {
            return;
        }
        graphics.setColor(3739672);
        graphics.fillRect(n3, n4, 35, nArray.length + 2);
        if (n5 <= 0) {
            return;
        }
        if (n5 > 100) {
            n5 = 100;
        }
        n2 = n5 * 33 / 100;
        for (n5 = 0; n5 < nArray.length; ++n5) {
            graphics.setColor(nArray[n5]);
            graphics.drawLine(n3 + 1, n4 + 1 + n5, n3 + n2, n4 + 1 + n5);
        }
    }

    private void l() {
        this.T = 1;
        Stack stack = this.e();
        j j2 = null;
        for (int i2 = 0; i2 < stack.size(); ++i2) {
            j2 = (j)stack.elementAt(i2);
            j2.b();
        }
        this.H.b((byte)6, (int)(-((c.d)this.M).n));
        Object object = d.a.c(-this.M.p);
        if (object == null) {
            return;
        }
        d.a.a(this.M, 1);
        int n2 = this.V.c();
        if (this.s[n2 += 3] != null) {
            this.C.remove(this.s[n2]);
            if (!this.s[n2].z()) {
                d.a.a((int)(-this.s[n2].f), 1);
            }
        }
        this.s[n2] = object;
        this.s[n2].h = true;
        this.s[n2].a((e.a)null);
        this.s[n2].b(-1, -1);
        this.s[n2].b((int)i[n2][0]);
        this.s[n2].c((int)i[n2][1]);
        this.C.put(this.s[n2], new Vector());
        int n3 = n2;
        d.g g2 = this.H;
        if (g2 != null) {
            object = null;
            if (this.s[n3] != null && !this.s[n3].z()) {
                object = main.d.a(this.s[n3], -1, -1, -1, null, false);
                this.a((d.b)g2, (d)object);
            }
            object = main.d.a(g2, 17, 0, 8, null, false);
            this.a((d.b)g2, (d)object);
            object = g.b.a(g2);
            this.a((d.b)g2, (d)object);
            object = main.d.a(1002, i[n3][0], (short)(i[n3][1] + 25), 2);
            this.a((d.b)g2, (d)object);
            object = main.d.a(this.s[n3], 0, -1, -1, null, true);
            this.a((d.b)g2, (d)object);
        }
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public final void a(j var1_2) {
        block40: {
            block53: {
                block38: {
                    block50: {
                        block44: {
                            block51: {
                                block52: {
                                    block48: {
                                        block49: {
                                            block45: {
                                                block47: {
                                                    block46: {
                                                        block43: {
                                                            block42: {
                                                                block41: {
                                                                    block39: {
                                                                        if (var1_2 != this.D) break block38;
                                                                        var1_2 = this.D.d();
                                                                        var2_7 = d.a.b((short)3);
                                                                        if (d.a.n() || var2_7 == null || this.H != var2_7 || var1_2.equals(d.a.q[60]) || var1_2.equals("\u6fc0\u6d3b")) break block39;
                                                                        var1_2 = b.c.a("\u5f20\u70c8\u76ee\u524d\u53ea\u80fd\u9632\u5fa1!", 3000L, null);
                                                                        var1_2.a(this);
                                                                        this.e((j)var1_2);
                                                                        break block40;
                                                                    }
                                                                    if (!var1_2.equals("\u6fc0\u6d3b")) break block41;
                                                                    var1_2 = "\u6fc0\u6d3b\u5f20\u70c8,\u662f\u5426\u6fc0\u6d3b?\u8d60\u90012\u4e2a\u5143\u5b9d(\u7528\u4e8e\u6362\u9ad8\u7ea7\u88c5\u5907),\u8bf7\u8bb0\u5f97\u5b58\u6863!";
                                                                    var3_11 = null;
                                                                    var4_18 = false;
                                                                    var4_18 = d.a.n() != false ? true : i.a.a(MainMIDlet.a, (Displayable)main.b.a, (String)var1_2, "08", "08", 200);
                                                                    if (var4_18) {
                                                                        d.a.a(1001, 2);
                                                                        d.a.d = true;
                                                                        var3_11 = "\u6fc0\u6d3b\u6210\u529f,\u5f20\u70c8\u73b0\u5728\u53ef\u4ee5\u4f7f\u7528\u4e86!";
                                                                        this.f(this.D);
                                                                        this.x();
                                                                    } else {
                                                                        var3_11 = "\u6fc0\u6d3b\u5931\u8d25!";
                                                                    }
                                                                    var5_22 = b.c.a(var3_11, 3000L, null);
                                                                    var5_22.a(this);
                                                                    this.e(var5_22);
                                                                    break block40;
                                                                }
                                                                if (!var1_2.equals("\u5546\u5e97")) break block42;
                                                                var6_29 = this;
                                                                v0 = new t[1];
                                                                var7_41 = v0;
                                                                v0[0] = new u(b.i.a);
                                                                var8_46 = e.a.a("/c.pak");
                                                                v1 = new Image[1];
                                                                var5_23 = v1;
                                                                v1[0] = var8_46.c("s0.png");
                                                                var6_29.aa = new s(var5_23, var7_41, main.b.c, main.b.d, null);
                                                                var6_29.aa.a(var8_46.c("bg.png"));
                                                                var6_29.aa.a(var6_29);
                                                                var6_29.e(var6_29.aa);
                                                                break block40;
                                                            }
                                                            if (!var1_2.equals(d.a.q[16])) break block43;
                                                            var6_30 = this;
                                                            if (var6_30.Q >= var6_30.t.length || var6_30.t[var6_30.Q] == null || var6_30.t[var6_30.Q].z()) {
                                                                var6_30.Q = g.b.a(var6_30.Q, var6_30.t, false);
                                                            }
                                                            var6_30.O = 1;
                                                            var6_30.P = 1;
                                                            var6_30.N = 0;
                                                            var6_30.A();
                                                            break block44;
                                                        }
                                                        if (!var1_2.equals(d.a.q[17])) break block45;
                                                        if (!this.H.P()) break block46;
                                                        var1_2 = b.c.a(d.a.q[61], 3000L, null);
                                                        var1_2.a(this);
                                                        this.e((j)var1_2);
                                                        break block40;
                                                    }
                                                    if (this.H.t() == 100) break block47;
                                                    var1_2 = b.c.a(d.a.q[62], 2000L, null);
                                                    var1_2.a(this);
                                                    this.e((j)var1_2);
                                                    break block40;
                                                }
                                                var6_31 = this;
                                                var6_31.z();
                                                break block44;
                                            }
                                            if (!var1_2.equals(d.a.q[18])) break block48;
                                            var6_32 = this;
                                            var7_42 = null;
                                            if (!var6_32.w()) break block49;
                                            var5_24 = var6_32.H;
                                            var8_47 = var6_32;
                                            if (var5_24 == null) ** GOTO lbl-1000
                                            var3_12 = 0;
                                            var0_1 = var5_24.e(0);
                                            var3_12 = var0_1 >= var8_47.w ? 100 - 100 * (25 / (var0_1 - var8_47.w + 50)) : 2500 / (50 - var0_1 + var8_47.w);
                                            if (h.b.c(100) < var3_12) {
                                                v2 = true;
                                            } else lbl-1000:
                                            // 2 sources

                                            {
                                                v2 = false;
                                            }
                                            if (v2) {
                                                var7_42 = b.c.a(d.a.q[27], 3000L, null);
                                                var6_32.T = (byte)2;
                                            } else {
                                                var7_42 = b.c.a(d.a.q[63], 3000L, null);
                                                var6_32.T = 1;
                                            }
                                            var7_42.a(var6_32);
                                            var6_32.e(var7_42);
                                            var6_32.D.b();
                                            break block50;
                                        }
                                        var7_42 = b.c.a(d.a.q[64], 3000L, null);
                                        var7_42.a(var6_32);
                                        var6_32.e(var7_42);
                                        break block40;
                                    }
                                    if (!var1_2.equals(d.a.q[7])) break block51;
                                    if (!this.H.P()) break block52;
                                    var1_2 = b.c.a(d.a.q[71], 3000L, null);
                                    var1_2.a(this);
                                    this.e((j)var1_2);
                                    break block40;
                                }
                                var6_33 = this;
                                var6_33.y();
                                break block44;
                            }
                            if (var1_2.equals(d.a.q[60])) {
                                var6_34 = this;
                                var6_34.H.b(9, 0);
                                var6_34.T = 1;
                                var6_34.H.L = true;
                            } else if (var1_2.equals(d.a.q[8])) {
                                var6_35 = this;
                                var6_35.B();
                            } else if (var1_2.equals(d.a.j(77))) {
                                var6_36 = this;
                                if (var6_36.Q >= var6_36.t.length || var6_36.t[var6_36.Q] == null || var6_36.t[var6_36.Q].z()) {
                                    var6_36.Q = g.b.a(var6_36.Q, var6_36.t, false);
                                }
                                var6_36.O = 1;
                                var6_36.P = 1;
                                var6_36.N = (byte)4;
                                var6_36.A();
                            } else if (var1_2.equals(d.a.j(25))) {
                                var6_37 = this;
                                v3 = new Image[1];
                                var7_43 = v3;
                                v3[0] = d.a.T[3];
                                v4 = new t[1];
                                var8_48 = v4;
                                v4[0] = new a(var6_37, var6_37.H);
                                var6_37.E = new s(var7_43, var8_48, (main.b.c << 2) / 5, main.b.d * 3 / 4, null);
                                var6_37.E.a(var6_37);
                                var6_37.e(var6_37.E);
                            }
                        }
                        this.D.b();
                    }
                    return;
                }
                if (var1_2 != this.R) break block53;
                this.I = this.O == 1 ? this.t[this.Q] : this.s[this.Q];
                switch (this.N) {
                    case 0: {
                        this.a(this.H, this.I);
                        break;
                    }
                    case 1: {
                        var4_19 = this.J;
                        var3_13 = this.I;
                        var1_2 = this.H;
                        var2_8 = this;
                        if (var1_2 == null || var4_19 == null || var4_19.a == 1 && var3_13 == null) break;
                        var5_25 /* !! */  = null;
                        var5_25 /* !! */  = main.d.a((d.b)var1_2, 16, -1, 8, null, false);
                        var2_8.a((d.b)var1_2, (d)var5_25 /* !! */ );
                        var6_38 = 0;
                        var7_44 = 0;
                        switch (var4_19.a) {
                            case 0: {
                                var6_38 = (short)(main.b.c / 3);
                                var7_44 = (short)(main.b.d / 2);
                                break;
                            }
                            case 1: {
                                var6_38 = (short)var3_13.l();
                                var7_44 = (short)var3_13.m();
                            }
                        }
                        var2_8.v = new p("r" + var4_19.e);
                        var2_8.v.b(var6_38);
                        var2_8.v.c(var7_44);
                        var2_8.v.a((e.a)null);
                        var2_8.v.a(true);
                        var2_8.v.b(-1, -1);
                        var2_8.c(var2_8.v);
                        var5_25 /* !! */  = main.d.a(var2_8.v, 0, 0, -1, null, false);
                        var2_8.a((d.b)var1_2, (d)var5_25 /* !! */ );
                        var8_49 = var4_19.v == 1 ? var2_8.t : var2_8.s;
                        var5_25 /* !! */  = main.d.a(var4_19, (d.g)var1_2, var3_13, var8_49);
                        var2_8.a((d.b)var1_2, (d)var5_25 /* !! */ );
                        var5_25 /* !! */  = null;
                        switch (var4_19.a) {
                            case 0: {
                                var5_25 /* !! */  = new d[var8_49.length][];
                                for (var3_14 = 0; var3_14 < var8_49.length; ++var3_14) {
                                    var5_25 /* !! */ [var3_14] = var2_8.a(var4_19, (d.g)var1_2, var8_49[var3_14]);
                                }
                                break;
                            }
                            case 1: {
                                v5 = new d[1][];
                                var5_25 /* !! */  = v5;
                                v5[0] = var2_8.a(var4_19, (d.g)var1_2, var3_13);
                            }
                        }
                        var5_25 /* !! */  = main.d.a(var5_25 /* !! */ );
                        var2_8.a((d.b)var1_2, (d)var5_25 /* !! */ );
                        var5_25 /* !! */  = g.b.a((d.g)var1_2);
                        var2_8.a((d.b)var1_2, (d)var5_25 /* !! */ );
                        break;
                    }
                    case 2: {
                        this.a(this.H, this.I, this.t, this.K);
                        this.H.g(0);
                        break;
                    }
                    case 3: {
                        d.a.a(this.M, 1);
                        var5_26 = this.M;
                        var4_20 = this.O == 1 ? this.t : this.s;
                        var3_15 = this.I;
                        var1_2 = this.H;
                        var2_9 = this;
                        if (var1_2 == null || var5_26 == null || var5_26.u == 0 && var4_20 == null || var5_26.u == 1 && var3_15 == null) break;
                        var6_39 = null;
                        var6_39 = main.d.a((d.b)var1_2, 17, 0, 8, null, false);
                        var2_9.a((d.b)var1_2, var6_39);
                        var6_39 = g.b.a((d.g)var1_2);
                        var2_9.a((d.b)var1_2, var6_39);
                        var6_39 = main.d.a(var5_26, (d.g)var1_2, var3_15, var4_20);
                        var2_9.a((d.b)var1_2, var6_39);
                        var6_39 = var2_9.a(var3_15, var4_20, var5_26);
                        var2_9.a((d.b)var1_2, var6_39);
                        break;
                    }
                    case 4: {
                        var1_3 = false;
                        if (!this.w()) ** GOTO lbl-1000
                        var1_4 = this.I;
                        var2_10 = this.H;
                        if (var2_10 == null || var1_4 == null) {
                            v6 = false;
                        } else {
                            var3_16 = var2_10.e(0) - var1_4.e(0);
                            var4_21 = var1_4.Q();
                            v6 = var3_16 < 0 || var4_21 >= 50 ? false : (var5_27 = ((50 + var3_16 * 2 / 100) * (50 - var4_21) << 1) / 100) >= (var6_40 = h.b.c(100));
                        }
                        if (v6) {
                            var1_5 = 1;
                            d.a.a((int)(-this.I.f), 1);
                        } else lbl-1000:
                        // 2 sources

                        {
                            var1_5 = 0;
                        }
                        var4_21 = var1_5;
                        var3_17 = this.I;
                        var1_6 = this.H;
                        var2_10 = this;
                        if (var1_6 == null || var3_17 == null) break;
                        var5_28 = null;
                        var6_40 = 0;
                        var7_45 = var3_17.m() - var3_17.o / 2;
                        var5_28 = main.d.a(var1_6, 17, 1, 8, null, false);
                        super.a((d.b)var1_6, var5_28);
                        var5_28 = g.b.a(var1_6);
                        super.a((d.b)var1_6, var5_28);
                        if (var4_21 != 0) {
                            var8_50 = new d[2][1];
                            var8_50[0][0] = main.d.a(var3_17, 3, 0, -1, null, false);
                            var8_50[1][0] = main.d.a(var1_6.l(), var1_6.m(), var3_17, 2, 1);
                            var5_28 = main.d.a(var8_50);
                            super.a((d.b)var1_6, var5_28);
                            var5_28 = main.d.a(d.a.j(77) + d.a.j(78), (byte)0);
                            super.a((d.b)var1_6, var5_28);
                            var5_28 = main.d.a(var3_17, (byte)2, (short)0, (byte)1);
                            super.a((d.b)var1_6, var5_28);
                            break;
                        }
                        var6_40 = var3_17.l() - var2_10.p.getWidth() / 2;
                        var8_51 = d.e.a(var2_10.p, var6_40, var7_45);
                        var2_10.a(var8_51);
                        var5_28 = main.d.a(var6_40, var7_45 - var3_17.o / 2, var8_51, 2, 1);
                        super.a((d.b)var1_6, var5_28);
                        var5_28 = main.d.a(d.a.j(77) + d.a.j(79), (byte)0);
                        super.a((d.b)var1_6, var5_28);
                        break;
                    }
                    case 5: {
                        this.b(this.H, this.I);
                    }
                }
                this.T = 1;
                this.R.b();
                return;
            }
            if (var1_2 == this.V) {
                this.l();
                return;
            }
            var1_2.b();
        }
    }

    public final void d(j j2) {
        this.f(j2);
    }

    private d a(d.g g2, d.g[] gArray, c.f f2) {
        if (f2 == null || f2.u == 0 && gArray == null || f2.u == 1 && g2 == null) {
            return null;
        }
        d[][] dArray = null;
        switch (f2.u) {
            case 1: {
                d[][] dArray2 = new d[1][];
                dArray = dArray2;
                dArray2[0] = this.a(g2, f2);
                break;
            }
            case 0: {
                dArray = new d[gArray.length][];
                for (int i2 = 0; i2 < gArray.length; ++i2) {
                    if (gArray[i2] == null || gArray[i2].z() && !f2.a()) continue;
                    dArray[i2] = this.a(gArray[i2], f2);
                }
                break;
            }
            default: {
                return null;
            }
        }
        return main.d.a(dArray);
    }

    private d[] a(d.g g2, c.f f2) {
        int n2;
        if (f2 == null || g2 == null) {
            return null;
        }
        Vector<d.b[]> vector = new Vector<d.b[]>();
        Object object = null;
        if (f2.v == 1) {
            object = main.d.a(g2, 2, 0, 0, null, false);
        } else {
            short s2 = (short)g2.l();
            n2 = g2.m();
            object = main.d.a(576, s2, (short)n2, 0);
        }
        vector.addElement((d.b[])object);
        object = f2.a(null, g2);
        if (object != null) {
            for (n2 = 0; n2 < ((d.b[])object).length; ++n2) {
                this.a(object[n2]);
            }
            d[][] dArray = new d[((d.b[])object).length][1];
            for (int i2 = 0; i2 < dArray.length; ++i2) {
                dArray[i2][0] = main.d.a(object[i2].l(), object[i2].m() - g2.o / 2, object[i2], 2, 1);
            }
            object = main.d.a(dArray);
            vector.addElement((d.b[])object);
        }
        object = g.b.a(g2);
        vector.addElement((d.b[])object);
        Object[] objectArray = new d[vector.size()];
        vector.copyInto(objectArray);
        return objectArray;
    }

    private void a(d.g g2, d.g dArray, boolean n2) {
        if (g2 == null || dArray == null) {
            return;
        }
        int n3 = g2.l();
        int n4 = g2.m();
        boolean bl = false;
        int n5 = 0;
        this.W = h.b.c(100) > 95 - (g2.N() ? 75 : 0);
        Object var6_8 = null;
        short s2 = 0;
        s2 = n2 != 0 ? (short)(dArray.l() + dArray.n / 2 + g2.n / 2) : (short)(dArray.l() - dArray.n / 2 - g2.n / 2);
        d d2 = main.d.a((int)s2, dArray.m(), g2, 999, 2);
        this.a((d.b)g2, d2);
        if (this.W) {
            if (n2 != 0) {
                d2 = main.d.a(dArray.l() - dArray.n / 2, dArray.m(), (d.b)dArray, 4, 2);
                this.a((d.b)g2, d2);
            } else {
                d2 = main.d.a(dArray.l() + dArray.n / 2, dArray.m(), (d.b)dArray, 4, 2);
                this.a((d.b)g2, d2);
            }
        }
        d2 = main.d.a(g2, 1, 0, 0, null, false);
        this.a((d.b)g2, d2);
        n5 = dArray.m() - dArray.o / 2;
        if (!this.W) {
            n2 = g2.a((d.g)dArray, false);
            d2 = main.d.a((d.b)dArray, (byte)2, (short)(-n2), (byte)0);
            this.a((d.b)g2, d2);
            int n6 = n2 * 100 / this.I.e(3);
            d d3 = main.d.a((d.b)dArray, (byte)13, (short)(n6 <= 0 ? 1 : n6), (byte)0);
            this.a((d.b)g2, d3);
            d3 = d.a.X;
            int n7 = dArray.l() - String.valueOf(n2).length() * d3.getWidth() / 10 / 2;
            d.b[] bArray = new d.b[1];
            d.b[] bArray2 = bArray;
            bArray[0] = d.e.a(n2, n7, n5, d.a.X);
            this.a(bArray2[0]);
            dArray = this.a(bArray2, (d.g)dArray);
            if (dArray != null) {
                for (n2 = 0; n2 < dArray.length; ++n2) {
                    this.a((d.b)g2, dArray[n2]);
                }
            }
        } else {
            int n8 = dArray.l() - this.p.getWidth() / 2;
            d.e e2 = d.e.a(this.p, n8, n5);
            this.a(e2);
            d d4 = main.d.a(n8, n5 - dArray.o / 2, e2, 2, 1);
            this.a((d.b)g2, d4);
            d4 = main.d.a(dArray.l(), dArray.m(), (d.b)dArray, 4, 2);
            this.a((d.b)g2, d4);
        }
        d d5 = main.d.a((int)((short)n3), (short)n4, g2, 999, 2);
        this.a((d.b)g2, d5);
        d5 = main.d.a(g2, 0, -1, -1, null, false);
        this.a((d.b)g2, d5);
    }

    private void a(d.g g2, d.g g3, d.g[] gArray, c.a a2) {
        if (g2 == null || a2 == null || a2.u == 0 && gArray == null || a2.u == 1 && g3 == null) {
            return;
        }
        int n2 = g2.l();
        int n3 = g2.m();
        d d2 = main.d.a(g2, 13, -1, -1, null, false);
        this.a((d.b)g2, d2);
        short s2 = 0;
        short s3 = 0;
        switch (a2.u) {
            case 0: {
                s2 = (short)main.b.e;
                s3 = (short)main.b.f;
                break;
            }
            case 1: {
                s2 = (short)(this.I.l() + this.I.n / 2 + g2.n / 2);
                s3 = (short)this.I.m();
            }
        }
        Object object = main.d.a((int)s2, s3, g2, 8, 2);
        this.a((d.b)g2, (d)object);
        object = main.d.a(g2, a2.a, 0, 8, null, false);
        this.a((d.b)g2, (d)object);
        object = main.d.a(a2, g2, g3, gArray);
        this.a((d.b)g2, (d)object);
        object = null;
        switch (a2.u) {
            case 0: {
                object = new d[gArray.length][];
                for (int i2 = 0; i2 < gArray.length; ++i2) {
                    object[i2] = this.a(a2, g2, gArray[i2]);
                }
                break;
            }
            case 1: {
                d[][] dArrayArray = new d[1][];
                object = dArrayArray;
                dArrayArray[0] = this.a(a2, g2, g3);
            }
        }
        object = main.d.a(object);
        this.a((d.b)g2, (d)object);
        object = main.d.a(g2, 14, -1, -1, null, false);
        this.a((d.b)g2, (d)object);
        object = main.d.a((int)((short)n2), (short)n3, g2, 8, 2);
        this.a((d.b)g2, (d)object);
        object = g.b.a(g2);
        this.a((d.b)g2, (d)object);
    }

    private d[] a(d.b[] bArray, d.g g2) {
        if (g2 == null || bArray == null) {
            return null;
        }
        Vector vector = new Vector();
        Object object = null;
        object = new d[bArray.length][1];
        for (int i2 = 0; i2 < bArray.length; ++i2) {
            object[i2][0] = main.d.a(bArray[i2].l(), bArray[i2].m() - g2.o / 2, bArray[i2], 2, 1);
        }
        object = main.d.a(object);
        vector.addElement(object);
        object = g.b.a(g2);
        vector.addElement(object);
        Object[] objectArray = new d[vector.size()];
        vector.copyInto(objectArray);
        return objectArray;
    }

    private static d a(d.g object) {
        if (object == null) {
            return null;
        }
        boolean bl = ((j)object).k == 8;
        d[] dArray = new d[3];
        d[] dArray2 = dArray;
        dArray[0] = main.d.a((d.g)object, (byte)2, (byte)2, 1, null);
        dArray2[0].h = new d[2];
        dArray2[0].h[0] = bl ? main.d.a((d.b)object, 3, 0, -1, null, false) : main.d.a((d.b)object, 11, -1, -1, null, false);
        dArray2[0].h[1] = main.d.a(dArray2[0]);
        dArray2[1] = main.d.a((d.g)object, (byte)2, (byte)2, (int)((short)(((d.g)object).e(3) / 5)), null);
        dArray2[1].h = new d[2];
        dArray2[1].h[0] = bl ? main.d.a((d.b)object, 0, -1, -1, null, false) : main.d.a((d.b)object, 10, -1, -1, null, false);
        dArray2[1].h[1] = main.d.a(dArray2[1]);
        dArray2[2] = main.d.a((d.g)object, (byte)2, (byte)0, (int)((short)(((d.g)object).e(3) / 5 - 1)), null);
        dArray2[2].h = new d[2];
        dArray2[2].h[0] = bl ? main.d.a((d.b)object, 0, -1, -1, null, false) : main.d.a((d.b)object, 8, -1, -1, null, false);
        dArray2[2].h[1] = main.d.a(dArray2[2]);
        object = main.d.a(dArray2);
        return object;
    }

    private void a(d.g g2, d.g g3) {
        if (g2 == null || g3 == null) {
            return;
        }
        int n2 = g2.l();
        int n3 = g2.m();
        this.W = h.b.c(100) > 95 - (g2.N() ? 75 : 0);
        int n4 = h.b.c(100) > 95 ? 1 : 0;
        Object object = null;
        if (n4 != 0 && !this.W) {
            object = d.e.a(this.q, g2.l() - g2.n / 2, g2.m() - g2.o / 2);
            this.a((d.b)object);
            object = main.d.a(((j)object).l(), ((j)object).m() - g2.o, (d.b)object, 2, 1);
            this.a((d.b)g2, (d)object);
        }
        object = main.d.a(g2, 13, -1, -1, null, false);
        this.a((d.b)g2, (d)object);
        short s2 = (short)(g3.l() + g3.n / 2 + g2.n / 2);
        d d2 = main.d.a((int)s2, g3.m(), g2, 8, 2);
        this.a((d.b)g2, d2);
        if (this.W) {
            d2 = main.d.a(g3.l() - g3.n / 2, g3.m(), g3, 4, 2);
            this.a((d.b)g2, d2);
        }
        if (this.W || n4 == 0) {
            d2 = main.d.a(g2, 15, 0, 8, null, false);
            this.a((d.b)g2, d2);
        } else {
            d2 = main.d.a(g2, 21, 0, 8, null, false);
            this.a((d.b)g2, d2);
        }
        int n5 = 0;
        int n6 = g3.m() - g3.o / 2;
        if (this.W) {
            n5 = g3.l() - this.p.getWidth() / 2;
            d.e e2 = d.e.a(this.p, n5, n6);
            this.a(e2);
            d d3 = main.d.a(n5, n6 - g3.o / 2, e2, 2, 1);
            this.a((d.b)g2, d3);
            d3 = main.d.a(g3.l(), g3.m(), g3, 4, 2);
            this.a((d.b)g2, d3);
        } else {
            n4 = g2.a(g3, n4 != 0);
            d d4 = main.d.a(g3, (byte)2, (short)(-n4), (byte)0);
            this.a((d.b)g2, d4);
            d.b[] bArray = new d.b[1];
            Image image = d.a.X;
            int n7 = g3.l() - String.valueOf(n4).length() * image.getWidth() / 20;
            bArray[0] = d.e.a(n4, n7, n6, image);
            for (n4 = 0; n4 < bArray.length; ++n4) {
                this.a(bArray[n4]);
            }
            d[] dArray = this.a(bArray, g3);
            if (dArray != null) {
                for (int i2 = 0; i2 < dArray.length; ++i2) {
                    this.a((d.b)g2, dArray[i2]);
                }
            }
        }
        d d5 = main.d.a(g2, 14, -1, -1, null, false);
        this.a((d.b)g2, d5);
        d5 = main.d.a((int)((short)n2), (short)n3, g2, 8, 2);
        this.a((d.b)g2, d5);
        d5 = g.b.a(g2);
        this.a((d.b)g2, d5);
    }

    private void b(d.g g2, d.g dArray) {
        if (g2 == null || dArray == null || this.L == null) {
            return;
        }
        int n2 = g2.l();
        int n3 = g2.m();
        d d2 = null;
        d2 = main.d.a(g2, 13, -1, -1, null, false);
        this.a((d.b)g2, d2);
        short s2 = (short)(dArray.l() + dArray.n / 2 + g2.n / 2);
        d d3 = main.d.a((int)s2, dArray.m(), g2, 8, 2);
        this.a((d.b)g2, d3);
        if (this.W) {
            d3 = main.d.a(dArray.l() - dArray.n / 2, dArray.m(), (d.b)dArray, 4, 2);
            this.a((d.b)g2, d3);
        }
        d3 = main.d.a(g2, 21, 0, 8, null, false);
        this.a((d.b)g2, d3);
        d3 = main.d.a(this.L, g2, (d.g)dArray, null);
        this.a((d.b)g2, d3);
        dArray = this.a(this.L, g2, (d.g)dArray);
        if (dArray != null) {
            for (int i2 = 0; i2 < dArray.length; ++i2) {
                this.a((d.b)g2, dArray[i2]);
            }
        }
        d d4 = main.d.a(g2, 14, -1, -1, null, false);
        this.a((d.b)g2, d4);
        d4 = main.d.a((int)((short)n2), (short)n3, g2, 8, 2);
        this.a((d.b)g2, d4);
        d4 = g.b.a(g2);
        this.a((d.b)g2, d4);
    }

    private d[] a(c.f bArray, d.g g2, d.g g3) {
        if (bArray == null || g3 == null || g3.z()) {
            return null;
        }
        if ((bArray = bArray.a(g2, g3)) == null) {
            return null;
        }
        for (int i2 = 0; i2 < bArray.length; ++i2) {
            if (bArray[i2] == null) continue;
            this.a(bArray[i2]);
        }
        return this.a(bArray, g3);
    }

    private void a(d.b b2, d d2) {
        Vector vector = (Vector)((b)((Object)vector)).C.get(b2);
        vector.addElement(d2);
    }

    public final void a(int n2, int n3) {
        int n4;
        Object object = this;
        for (n4 = 0; n4 < ((b)object).s.length; ++n4) {
            if (((b)object).s[n4] == null) continue;
            ((b)object).s[n4].c();
        }
        for (n4 = 0; n4 < ((b)object).t.length; ++n4) {
            if (((b)object).t[n4] == null) continue;
            ((b)object).t[n4].c();
        }
        if (((b)object).u != null) {
            for (n4 = 0; n4 < ((b)object).u.length; ++n4) {
                ((b)object).u[n4].c();
            }
        }
        this.c();
        object = this;
        Enumeration enumeration = ((b)object).C.elements();
        object = null;
        d d2 = null;
        boolean bl = false;
        while (enumeration.hasMoreElements()) {
            object = (Vector)enumeration.nextElement();
            if (((Vector)object).isEmpty()) continue;
            bl = true;
            d2 = (d)((Vector)object).elementAt(0);
            d2.a();
            if (!d2.b()) continue;
            ((Vector)object).removeElement(d2);
        }
        byte by = 0;
        if (!this.f()) {
            j j2 = this.h();
            j2.a(n2, n3);
            by = this.T;
            this.T = (byte)5;
        } else {
            by = this.a(this.X, (byte)1, n2, n3);
        }
        if (by != 5 && by != this.X) {
            this.a(this.X, (byte)2, n2, n3);
            this.X = by;
            this.a(this.X, (byte)0, n2, n3);
        }
    }

    private static int a(int n2, d.g[] gArray, boolean bl) {
        if (gArray == null) {
            return -1;
        }
        for (int i2 = 0; i2 < gArray.length; ++i2) {
            if (gArray[n2 = (n2 + 1) % gArray.length] == null || gArray[n2].z() && !bl) continue;
            return n2;
        }
        return -1;
    }

    private void a(f.a object, d.g g2) {
        boolean bl;
        if (object == null || g2 == null) {
            return;
        }
        f.a a2 = object;
        object = g2;
        Object object2 = a2;
        if (!(a2 == null || object == null ? false : (bl = (object2 = ((f.a)object2).c()) != null && ((f.c)object2).a()))) {
            return;
        }
        if (g2.k == 8) {
            g2.b(2, 0);
            return;
        }
        g2.b(12, 0);
    }

    private boolean m() {
        if (((b)((Object)enumeration)).C.isEmpty()) {
            return true;
        }
        Enumeration enumeration = ((b)((Object)enumeration)).C.elements();
        Vector vector = null;
        while (enumeration.hasMoreElements()) {
            vector = (Vector)enumeration.nextElement();
            if (vector.isEmpty()) continue;
            return false;
        }
        return true;
    }

    private boolean n() {
        for (int i2 = 0; i2 < this.t.length; ++i2) {
            if (this.t[i2] == null || this.t[i2].z()) continue;
            return false;
        }
        return true;
    }

    private boolean o() {
        for (int i2 = 0; i2 < this.s.length; ++i2) {
            if (this.s[i2] == null || this.s[i2].z()) continue;
            return false;
        }
        return true;
    }

    private void p() {
        switch (this.P) {
            case 0: {
                for (int i2 = 0; i2 < this.t.length; ++i2) {
                    this.a(this.H.d, (d.g)this.t[i2]);
                }
                return;
            }
            case 1: {
                this.a(this.H.d, this.I);
            }
        }
    }

    private void q() {
        if (this.J == null) {
            return;
        }
        d.g[] gArray = this.S ? this.t : this.s;
        switch (this.P) {
            case 0: {
                for (int i2 = 0; i2 < gArray.length; ++i2) {
                    if (gArray[i2] == null || gArray[i2].z()) continue;
                    this.a(this.v.d, gArray[i2]);
                }
                return;
            }
            case 1: {
                this.a(this.v.d, this.I);
            }
        }
    }

    private static void b(d.g g2) {
        if (g2 == null) {
            return;
        }
        for (int i2 = 0; i2 < g2.A.length; ++i2) {
            if (g2.A[i2] <= 0) continue;
            int n2 = i2;
            g2.A[n2] = (byte)(g2.A[n2] - 1);
            if (g2.A[i2] != 0) continue;
            g2.j(i2);
        }
    }

    private void a(d.g g2, int n2) {
        int n3 = g2.l();
        int n4 = g2.m() - g2.o / 2;
        Image image = d.a.X;
        int n5 = image.getWidth() / 10;
        n2 = Math.max(1, n2);
        d.b[] bArray = new d.b[1];
        d.b[] bArray2 = bArray;
        bArray[0] = d.e.a(n2, n3 - String.valueOf(n2).length() * n5 / 2, n4, image);
        for (n3 = 0; n3 < bArray2.length; ++n3) {
            if (bArray2[n3] == null) continue;
            this.a(bArray2[n3]);
        }
        g2.b((byte)2, -n2);
        d[] dArray = this.a(bArray2, g2);
        if (dArray != null) {
            for (n2 = 0; n2 < dArray.length; ++n2) {
                this.a((d.b)g2, dArray[n2]);
            }
        }
    }

    private boolean r() {
        int n2;
        if (this.s != null) {
            for (n2 = 0; n2 < this.s.length; ++n2) {
                if (this.s[n2] == null || this.s[n2].f()) continue;
                return false;
            }
        }
        if (this.t != null) {
            for (n2 = 0; n2 < this.t.length; ++n2) {
                if (this.t[n2] == null || this.t[n2].f()) continue;
                return false;
            }
        }
        return true;
    }

    private byte s() {
        if (this.n()) {
            return 2;
        }
        if (this.o()) {
            return 2;
        }
        return 3;
    }

    private static String a(d.g g2, c.a[] aArray) {
        if (g2 == null || aArray == null || aArray.length <= 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(g2.B);
        stringBuffer.append(d.a.q[65]);
        for (int i2 = 0; i2 < aArray.length; ++i2) {
            stringBuffer.append(aArray[i2].r);
            if (i2 >= aArray.length - 1) continue;
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    private static String a(int n2, int n3, c.f[] fArray) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(d.a.q[34]);
        stringBuffer.append(d.a.q[42]);
        stringBuffer.append(':');
        stringBuffer.append("\n");
        stringBuffer.append(d.a.q[39]);
        stringBuffer.append(d.a.q[35]);
        stringBuffer.append('+');
        stringBuffer.append(n2);
        stringBuffer.append("\n");
        stringBuffer.append(d.a.q[32]);
        stringBuffer.append('+');
        stringBuffer.append(n3);
        if (fArray != null) {
            for (n2 = 0; n2 < fArray.length; ++n2) {
                if (fArray[n2] == null) continue;
                stringBuffer.append("\n");
                stringBuffer.append(fArray[n2].r);
                stringBuffer.append('+');
                stringBuffer.append("1");
                stringBuffer.append(d.a.q[40]);
            }
        }
        return stringBuffer.toString();
    }

    private int t() {
        int n2 = 0;
        for (int i2 = 0; i2 < this.t.length; ++i2) {
            if (this.t[i2] == null) continue;
            ++n2;
        }
        return n2;
    }

    private void u() {
        int n2;
        for (n2 = 0; n2 < this.s.length; ++n2) {
            if (this.s[n2] == null || !this.s[n2].f() || this.s[n2].g() == -1) continue;
            if (this.s[n2].e(2) * 100 / this.s[n2].e(3) <= 20) {
                if (this.s[n2].k != 6) continue;
                this.s[n2].b(10, -1);
                continue;
            }
            if (this.s[n2].z()) continue;
            if (this.s[n2].k == 6) {
                this.s[n2].b(8, -1);
                continue;
            }
            this.s[n2].b(0, -1);
        }
        for (n2 = 0; n2 < this.t.length; ++n2) {
            if (this.t[n2] == null || !this.t[n2].f() || this.t[n2].g() == -1 || this.t[n2].z()) continue;
            this.t[n2].b(0, -1);
        }
    }

    private void v() {
        this.s = new d.g[5];
        System.arraycopy(d.a.C, 0, this.s, 0, d.a.C.length);
        for (int i2 = 0; i2 < this.s.length; ++i2) {
            if (this.s[i2] == null) continue;
            this.C.put(this.s[i2], new Vector());
            if (this.s[i2].e(2) < this.s[i2].e(3) * 20 / 100) {
                this.s[i2].b(10, -1);
            } else {
                this.s[i2].b(8, -1);
            }
            this.s[i2].b((int)i[i2][0]);
            this.s[i2].c((int)i[i2][1]);
            this.s[i2].a((short)(this.s[i2].w() / 2));
        }
    }

    private boolean w() {
        return (this.B.g & 1) != 0;
    }

    public final void b(j j2) {
    }

    public final void c(j j2) {
        if (j2 == this.R) {
            switch (this.N) {
                case 1: {
                    this.y();
                    break;
                }
                case 2: {
                    this.z();
                    break;
                }
                case 0: 
                case 4: {
                    this.x();
                    break;
                }
                case 3: {
                    this.B();
                }
            }
            j2.b();
        } else if (j2 == this.V) {
            j2.b();
        }
        if (j2 == this.aa) {
            j2.b();
        }
    }

    private void x() {
        String[] stringArray;
        if (this.H == null) {
            throw new NullPointerException();
        }
        d.g g2 = this.H;
        if (g2 == null) {
            stringArray = null;
        } else {
            int n2 = 6;
            if (g2.f == 0) {
                ++n2;
            }
            if (g2.y != 0) {
                ++n2;
            }
            if (g2.f == 3 && !d.a.n()) {
                ++n2;
            }
            int n3 = 0;
            String[] stringArray2 = new String[++n2];
            String[] stringArray3 = stringArray2;
            ++n3;
            stringArray2[0] = d.a.q[16];
            ++n3;
            stringArray3[1] = d.a.q[7];
            ++n3;
            stringArray3[2] = d.a.q[8];
            ++n3;
            stringArray3[3] = d.a.q[17];
            ++n3;
            stringArray3[4] = d.a.q[60];
            if (g2.f == 0) {
                ++n3;
                stringArray3[5] = d.a.j(77);
            }
            if (g2.y != 0) {
                stringArray3[n3++] = d.a.j(25);
            }
            stringArray3[n3++] = d.a.q[18];
            if (g2.f == 3 && !d.a.n()) {
                stringArray3[n3++] = "\u6fc0\u6d3b";
            }
            stringArray3[n3] = "\u5546\u5e97";
            stringArray = stringArray3;
        }
        g2 = stringArray;
        this.D = b.c.a((String[])g2, null, (byte)0, (byte)4);
        this.e(this.D);
        this.D.a(this);
    }

    private void y() {
        Image[] imageArray = new Image[1];
        Image[] imageArray2 = imageArray;
        imageArray[0] = d.a.S[this.H.f];
        t[] tArray = new e[1];
        t[] tArray2 = tArray;
        tArray[0] = new e(this, this.H);
        this.F = new s(imageArray2, tArray2, (main.b.c << 2) / 5, main.b.d * 3 / 4, null);
        this.F.a(this);
        this.e(this.F);
    }

    private void z() {
        Image[] imageArray = new Image[1];
        Image[] imageArray2 = imageArray;
        imageArray[0] = d.a.S[this.H.f];
        t[] tArray = new c[1];
        t[] tArray2 = tArray;
        tArray[0] = new c(this, this.H);
        this.G = new s(imageArray2, tArray2, (main.b.c << 2) / 5, main.b.d * 3 / 4, null);
        this.G.a(this);
        this.e(this.G);
    }

    private void A() {
        this.e(this.R);
    }

    private void a(Graphics graphics, d.g g2, int n2, int n3, boolean bl) {
        if (g2 == null || g2.z() || g2.L() == 0 || d == null) {
            return;
        }
        int n4 = d.getWidth() / 13;
        int n5 = d.getHeight();
        short s2 = g2.L();
        for (int i2 = 0; i2 < 15; ++i2) {
            if ((s2 & 1 << i2) == 0) continue;
            h.b.a(graphics, this.o, 0, 0, this.o.getWidth(), this.o.getHeight(), bl ? 2 : 0, n2, n3, 36);
            h.b.a(graphics, d, (i2 - 1) * n4, 0, n4, n5, n2 + this.o.getWidth() / 2, n3 - 3, 33);
            return;
        }
    }

    private void B() {
        Image[] imageArray = new Image[h.length];
        for (int i2 = 0; i2 < imageArray.length; ++i2) {
            imageArray[i2] = d.a.T[h[i2]];
        }
        t[] tArray = new k[h.length];
        for (int i3 = 0; i3 < tArray.length; ++i3) {
            tArray[i3] = new k(this, h[i3]);
        }
        this.E = new s(imageArray, tArray, (main.b.c << 2) / 5, main.b.d * 3 / 4, null);
        this.E.a(this);
        this.e(this.E);
    }

    /*
     * Unable to fully structure code
     */
    private byte a(byte var1_1, byte var2_6, int var3_10, int var4_16) {
        switch (var1_1) {
            case 3: {
                var1_1 = var2_6;
                block7 : switch (var1_1) {
                    case 0: {
                        ** GOTO lbl33
                    }
                    case 1: {
                        var1_2 = this;
                        while (var1_2.Y < var1_2.s.length) {
                            if (var1_2.s[var1_2.Y] != null && !var1_2.s[var1_2.Y].z()) {
                                var1_2.s[var1_2.Y].x();
                                if (var1_2.s[var1_2.Y].y()) {
                                    var1_2.H = var1_2.s[var1_2.Y];
                                    var1_2.S = true;
                                    ++var1_2.Y;
                                    v0 = 4;
                                    break block7;
                                }
                            }
                            ++var1_2.Y;
                        }
                        var1_2.Y = 0;
                        while (var1_2.Z < var1_2.t.length) {
                            if (var1_2.t[var1_2.Z] != null && !var1_2.t[var1_2.Z].z()) {
                                var1_2.t[var1_2.Z].x();
                                if (var1_2.t[var1_2.Z].y()) {
                                    var1_2.H = var1_2.t[var1_2.Z];
                                    ++var1_2.Z;
                                    v0 = 4;
                                    break block7;
                                }
                            }
                            ++var1_2.Z;
                        }
                        var1_2.Z = 0;
                        return 5;
                    }
lbl33:
                    // 2 sources

                    default: {
                        v0 = 5;
                    }
                }
                return (byte)v0;
            }
            case 4: {
                var1_1 = var2_6;
                switch (var1_1) {
                    case 0: {
                        this.H.L = false;
                        var2_7 = this.H;
                        var1_3 = this;
                        if (var2_7 != null && var2_7.L() != 0) {
                            var3_10 = 0;
                            v1 = var2_7.L();
                            var4_16 = v1;
                            switch (v1) {
                                case 1: 
                                case 4: 
                                case 16: 
                                case 32: 
                                case 64: 
                                case 128: 
                                case 256: 
                                case 512: 
                                case 2048: {
                                    break;
                                }
                                case 8192: {
                                    var3_10 = var2_7.e(2) * (30 + h.b.c(20)) / 100;
                                    super.a(var2_7, var3_10);
                                    break;
                                }
                                case 1024: {
                                    switch (var2_7.K()) {
                                        case 0: {
                                            var3_10 = 55 + h.b.c(45);
                                            break;
                                        }
                                        case 1: {
                                            var3_10 = 100 + h.b.c(990);
                                            break;
                                        }
                                        case 2: {
                                            var3_10 = 100 + h.b.c(8999);
                                        }
                                    }
                                    super.a(var2_7, var3_10);
                                    break;
                                }
                                case 8: {
                                    var3_10 = var2_7.e(2) * h.b.c(30) / 100;
                                    super.a(var2_7, var3_10);
                                    break;
                                }
                                case 2: {
                                    var3_10 = var2_7.e(2) * h.b.c(50) / 100;
                                    super.a(var2_7, var3_10);
                                    break;
                                }
                                case 4096: {
                                    var3_10 = var2_7.e(2) * (30 + h.b.c(20)) / 100;
                                    super.a(var2_7, var3_10);
                                }
                            }
                        }
                        var1_3 = this.H;
                        if (var1_3 == null) break;
                        if (var1_3.G > 0) {
                            var1_3.b((byte)2, var1_3.e(3) * var1_3.G / 100);
                        }
                        if (var1_3.H > 0) {
                            var1_3.b((byte)4, var1_3.e(5) * var1_3.H / 100);
                        }
                        if (var1_3.I <= 0) break;
                        var1_3.b((byte)6, var1_3.e(7) * var1_3.I / 100);
                        break;
                    }
                    case 1: {
                        if (!this.m()) break;
                        if (this.H.z()) {
                            return this.s();
                        }
                        if (this.H.O()) {
                            return 1;
                        }
                        if ((this.H.L() & 32) != 0) {
                            var2_8 = this.H;
                            var1_4 = this;
                            if (var2_8 != null) {
                                if (var2_8 == var1_4.s[3] || var2_8 == var1_4.s[4]) {
                                    var1_4.S = true;
                                }
                                v2 = var4_16 = h.b.c(100) < 50 ? 1 : 0;
                                if (var2_8.k == 6) {
                                    var1_4.I = var2_8.a(var4_16 != 0 ? var1_4.t : var1_4.s);
                                    if (var1_4.I == null) {
                                        var1_4.I = var2_8.a(var4_16 != 0 ? var1_4.s : var1_4.t);
                                        var4_16 = var4_16 == 0 ? 1 : 0;
                                    }
                                } else {
                                    var1_4.I = var2_8.a(var4_16 != 0 ? var1_4.s : var1_4.t);
                                    if (var1_4.I == null) {
                                        var1_4.I = var2_8.a(var4_16 != 0 ? var1_4.t : var1_4.s);
                                        v3 = var4_16 = var4_16 == 0 ? 1 : 0;
                                    }
                                }
                                if (var4_16 != 0) {
                                    if (var2_8.k == 8) {
                                        var1_4.a(var2_8, var1_4.I, var1_4.S);
                                    } else {
                                        var1_4.a(var2_8, var1_4.I);
                                    }
                                } else {
                                    var3_11 = var1_4.I;
                                    this = var1_4;
                                    if (var2_8 != null && var3_11 != null) {
                                        var4_16 = 0;
                                        var5_19 = 0;
                                        var6_21 = null;
                                        var4_16 = var2_8.k == 6 ? 15 : 1;
                                        var5_19 = var2_8.k == 6 ? 8 : 0;
                                        var6_21 = main.d.a(var2_8, var4_16, 0, var5_19, null, false);
                                        this.a((d.b)var2_8, (d)var6_21);
                                        var7_24 = 0;
                                        var8_28 = var3_11.m() - var3_11.o / 2;
                                        var9_30 = var2_8.a(var3_11, false);
                                        var6_21 = main.d.a(var3_11, (byte)2, (short)(-var9_30), (byte)0);
                                        this.a((d.b)var2_8, (d)var6_21);
                                        var7_24 = var9_30 * 100 / this.I.e(3);
                                        var6_21 = main.d.a(var3_11, (byte)13, (short)(var7_24 <= 0 ? 1 : var7_24), (byte)0);
                                        this.a((d.b)var2_8, (d)var6_21);
                                        var6_21 = new d.b[1];
                                        var10_32 = d.a.X;
                                        var7_24 = var3_11.l() - String.valueOf(var9_30).length() * var10_32.getWidth() / 20;
                                        var6_21[0] = d.e.a(var9_30, var7_24, var8_28, var10_32);
                                        for (var4_16 = 0; var4_16 < var6_21.length; ++var4_16) {
                                            this.a(var6_21[var4_16]);
                                        }
                                        var4_17 = this.a(var6_21, var3_11);
                                        if (var4_17 != null) {
                                            for (var3_12 = 0; var3_12 < var4_17.length; ++var3_12) {
                                                this.a((d.b)var2_8, var4_17[var3_12]);
                                            }
                                        }
                                    }
                                }
                                var1_4.N = 0;
                                var1_4.P = 1;
                            }
                            return 1;
                        }
                        if (this.H.k != 8) ** GOTO lbl223
                        var2_9 = (d.d)this.H;
                        var1_5 = this;
                        var3_13 = null;
                        if (var2_9 == var1_5.s[3] || var2_9 == var1_5.s[4]) {
                            var1_5.S = true;
                            var3_13 = var1_5.t;
                        } else {
                            var1_5.S = false;
                            var3_13 = var1_5.s;
                        }
                        var4_16 = h.b.c(100);
                        if (var4_16 <= 30) ** GOTO lbl-1000
                        var1_5.J = var2_9.q();
                        if (var1_5.J != null) {
                            var2_9.a(var1_5.J, -1);
                            var1_5.N = 1;
                            var1_5.P = var1_5.J.a;
                            var1_5.O = var1_5.J.v;
                            var1_5.I = var2_9.a((d.g[])var3_13);
                            var6_22 = var1_5.S;
                            var5_20 = var1_5.J;
                            var4_18 = var3_13;
                            var3_13 = var1_5.I;
                            this = var1_5;
                            if (!(var2_9 == null || var5_20 == null || var5_20.a == 1 && var3_13 == null || var5_20.a == 0 && var4_18 == null)) {
                                var7_25 = null;
                                var7_25 = main.d.a(var2_9, 1, 0, 0, null, false);
                                this.a((d.b)var2_9, var7_25);
                                this.v = new p("r" + var5_20.e);
                                this.v.a((e.a)null);
                                this.v.b(-1, -1);
                                this.v.a(true);
                                this.c(this.v);
                                var8_29 = 0;
                                var9_31 = 0;
                                var7_26 = false;
                                switch (var5_20.a) {
                                    case 0: {
                                        var9_31 = (short)(main.b.d / 2);
                                        if (var6_22) {
                                            var8_29 = (short)(main.b.c / 3);
                                            break;
                                        }
                                        var7_26 = true;
                                        var8_29 = (short)((main.b.c << 1) / 3);
                                        break;
                                    }
                                    case 1: {
                                        var8_29 = (short)var3_13.l();
                                        var9_31 = (short)var3_13.m();
                                    }
                                }
                                this.v.b(var8_29);
                                this.v.c(var9_31);
                                var7_27 = main.d.a(this.v, 0, 0, -1, null, var7_26);
                                this.a((d.b)var2_9, var7_27);
                                var7_27 = main.d.a(var5_20, var2_9, (d.g)var3_13, var4_18);
                                this.a((d.b)var2_9, var7_27);
                                var6_23 = null;
                                switch (var5_20.a) {
                                    case 0: {
                                        var6_23 = new d[var4_18.length][];
                                        for (var10_33 = 0; var10_33 < var4_18.length; ++var10_33) {
                                            var6_23[var10_33] = this.a(var5_20, (d.g)var2_9, var4_18[var10_33]);
                                        }
                                        break;
                                    }
                                    case 1: {
                                        v4 = new d[1][];
                                        var6_23 = v4;
                                        v4[0] = this.a(var5_20, (d.g)var2_9, (d.g)var3_13);
                                    }
                                }
                                var7_27 = main.d.a(var6_23);
                                this.a((d.b)var2_9, var7_27);
                            }
                        } else lbl-1000:
                        // 2 sources

                        {
                            var1_5.I = var2_9.a((d.g[])var3_13);
                            var1_5.a((d.g)var2_9, var1_5.I, var1_5.S);
                            var1_5.N = 0;
                            var1_5.P = 1;
                            var1_5.O = 1;
                        }
                        return 1;
lbl223:
                        // 1 sources

                        this.x();
                        return 0;
                    }
                }
                return 5;
            }
            case 0: {
                return 5;
            }
            case 1: {
                var1_1 = var2_6;
                switch (var1_1) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        var3_14 = this;
                        switch (var3_14.N) {
                            case 1: {
                                var3_14.q();
                                break;
                            }
                            case 0: {
                                if (var3_14.W) break;
                                var3_14.a(var3_14.H.d, var3_14.I);
                                break;
                            }
                            case 5: {
                                var3_14.a(var3_14.H.d, var3_14.I);
                                break;
                            }
                            case 2: {
                                var3_14.p();
                            }
                        }
                        var1_1 = (byte)this.m();
                        this.u();
                        var2_6 = (byte)this.r();
                        if (var1_1 == 0 || var2_6 == 0) break;
                        return this.s();
                    }
                    case 2: {
                        var3_15 = this.H;
                        if (var3_15 != null && (var4_16 = var3_15.K()) > 0) {
                            var3_15.h(var4_16 - 1);
                            if (var4_16 - 1 <= 0) {
                                var3_15.c((short)0);
                            }
                        }
                        g.b.b(this.H);
                        this.H.v();
                        this.J = null;
                        this.K = null;
                        this.H = null;
                        this.I = null;
                        this.W = false;
                        this.u = null;
                        this.v = null;
                    }
                }
                return 5;
            }
            case 2: {
                return this.a(var2_6);
            }
        }
        return 5;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private byte a(byte var1_1) {
        switch (var1_1) {
            case 0: {
                if (!this.n()) ** GOTO lbl111
                var1_2 = this;
                d.a.c(8, 1);
                for (var2_9 = 0; var2_9 < 3; ++var2_9) {
                    if (var1_2.s[var2_9] == null) continue;
                    var1_2.s[var2_9].b(18, 0);
                }
                var5_14 = var1_2;
                var6_15 = 0;
                for (var7_18 = 0; var7_18 < var5_14.t.length; ++var7_18) {
                    if (var5_14.t[var7_18] == null) continue;
                    var6_15 += var5_14.t[var7_18].e(1);
                }
                var2_9 = (var6_15 = var6_15 * d.a.c / 2) <= 0 ? 1 : var6_15;
                var5_14 = var1_2;
                var6_15 = 0;
                for (var7_18 = 0; var7_18 < var5_14.t.length; ++var7_18) {
                    if (var5_14.t[var7_18] == null) continue;
                    var6_15 += var5_14.t[var7_18].r;
                }
                var3_20 = var6_15 = var6_15 * d.a.b / 3;
                var5_14 = var1_2;
                var6_16 = new c.f[var1_2.t.length];
                for (var7_18 = 0; var7_18 < var5_14.t.length; ++var7_18) {
                    if (var5_14.t[var7_18] != null) ** GOTO lbl31
                    v0 = var6_16;
                    v1 = var7_18;
                    ** GOTO lbl-1000
lbl31:
                    // 1 sources

                    v0 = var6_16;
                    v1 = var7_18;
                    var8_21 /* !! */  = var5_14.t[var7_18];
                    var9_22 = h.b.c(100);
                    if (var9_22 <= var8_21 /* !! */ .t) {
                        v2 = d.a.a((int)var8_21 /* !! */ .s);
                    } else lbl-1000:
                    // 2 sources

                    {
                        v2 = null;
                    }
                    v0[v1] = v2;
                }
                var8_21 /* !! */  = var4_25 = var6_16;
                var7_18 = var3_20;
                var6_17 = var2_9;
                var5_14 = var1_2;
                d.a.h(var7_18);
                var9_22 = 0;
                var7_19 = new String[3];
                for (var10_27 = 0; var10_27 < 3; ++var10_27) {
                    if (var5_14.s[var10_27] == null) continue;
                    var9_23 = var5_14.s[var10_27].e(0);
                    var5_14.s[var10_27].b((byte)1, var6_17);
                    var11_28 = var9_23;
                    var9_24 = var5_14.s[var10_27];
                    if (var9_24 == null) {
                        v3 = null;
                    } else {
                        var12_29 = var9_24.e(0);
                        if (var12_29 == var11_28) {
                            v3 = null;
                        } else {
                            var13_30 = new StringBuffer();
                            var13_30.append(var9_24.B);
                            var13_30.append(d.a.q[41]);
                            var13_30.append(var9_24.e(0));
                            var13_30.append(d.a.q[37]);
                            var14_31 = null;
                            ++var11_28;
                            while (var11_28 <= var12_29) {
                                var14_31 = d.a.b((int)d.a.u[var9_24.f][var11_28 - 1].c);
                                if (var14_31 != null) {
                                    var13_30.append("\n");
                                    var13_30.append(d.a.q[65]);
                                    var13_30.append(var14_31.r);
                                }
                                ++var11_28;
                            }
                            v3 = var13_30.toString();
                        }
                    }
                    var7_19[var10_27] = v3;
                }
                if (var8_21 /* !! */  != null) {
                    for (var10_27 = 0; var10_27 < var8_21 /* !! */ .length; ++var10_27) {
                        d.a.b(var8_21 /* !! */ [var10_27], 1);
                    }
                }
                var5_14 = var7_19;
                var2_10 /* !! */  = g.b.a(var2_9, var3_20, var4_25);
                var2_10 /* !! */  = b.c.a((String)var2_10 /* !! */ , 5000L, null);
                var2_10 /* !! */ .a((main.a)var1_2);
                var1_2.r.addElement(var2_10 /* !! */ );
                for (var3_20 = 0; var3_20 < var5_14.length; ++var3_20) {
                    if (var5_14[var3_20] == null) continue;
                    var2_10 /* !! */  = b.c.a(var5_14[var3_20], 3000L, null);
                    var2_10 /* !! */ .a((main.a)var1_2);
                    var1_2.r.addElement(var2_10 /* !! */ );
                }
                var3_20 = super.t();
                var2_10 /* !! */  = null;
                for (var4_26 = 0; var4_26 < var1_2.s.length; ++var4_26) {
                    if (var1_2.s[var4_26] == null || (var2_10 /* !! */  = var1_2.s[var4_26].f(var3_20)) == null || (var2_10 /* !! */  = g.b.a(var1_2.s[var4_26], var2_10 /* !! */ )) == null) continue;
                    var2_10 /* !! */  = b.c.a((String)var2_10 /* !! */ , 3000L, null);
                    var2_10 /* !! */ .a((main.a)var1_2);
                    var1_2.r.addElement(var2_10 /* !! */ );
                }
                ** GOTO lbl116
lbl111:
                // 1 sources

                if (this.o()) {
                    var1_3 = this;
                    var2_10 /* !! */  = b.c.a(d.a.q[67], 5000L, null);
                    var1_3.e((j)var2_10 /* !! */ );
                    var2_10 /* !! */ .a((main.a)var1_3);
                }
lbl116:
                // 6 sources

                for (var1_4 = 3; var1_4 < this.s.length; ++var1_4) {
                    if (this.s[var1_4] == null) continue;
                    d.a.a((int)(-this.s[var1_4].f), 1);
                }
                break;
            }
            case 1: {
                if (this.r.isEmpty()) {
                    if (this.o()) {
                        var1_5 = this;
                        if (!((this.B.g & 2) != 0)) {
                            var1_5 = "\u5168\u4f53\u590d\u6d3b?\u8d60\u90012\u4e2a\u5143\u5b9d(\u7528\u4e8e\u6362\u9ad8\u7ea7\u88c5\u5907),\u8bf7\u8bb0\u5f97\u5b58\u6863!";
                            var2_11 = null;
                            var2_12 = false;
                            var2_12 = d.a.a != false ? true : i.a.a(MainMIDlet.a, (Displayable)main.b.a, (String)var1_5, "02", "02", 200);
                            if (var2_12) {
                                d.a.a(1001, 2);
                                for (var1_6 = 0; var1_6 < this.s.length; ++var1_6) {
                                    if (this.s[var1_6] == null) continue;
                                    this.s[var1_6].S();
                                    this.s[var1_6].b(8, -1);
                                }
                                var2_13 = "\u590d\u6d3b\u6210\u529f!";
                                var1_7 = b.c.a("\u590d\u6d3b\u6210\u529f!", 3000L, null);
                                var1_7.a((main.a)this);
                                this.e(var1_7);
                                return 3;
                            }
                            this.a = new h();
                            break;
                        }
                    }
                    this.a = new g();
                    break;
                }
                var1_8 = (b.c)this.r.elementAt(0);
                var1_8.a((main.a)this);
                this.e(var1_8);
                this.r.removeElementAt(0);
            }
        }
        return 5;
    }

    private void c(d.b b2) {
        if (b2 == null) {
            return;
        }
        if (this.u == null) {
            this.u = new d.b[1];
            this.u[0] = b2;
            return;
        }
        d.b[] bArray = new d.b[this.u.length + 1];
        System.arraycopy(this.u, 0, bArray, 0, this.u.length);
        bArray[bArray.length - 1] = b2;
        this.u = bArray;
    }

    static Image a(b b2) {
        return b2.n;
    }

    static byte b(b b2) {
        return b2.O;
    }

    static byte c(b b2) {
        return b2.P;
    }

    static short[][] i() {
        return j;
    }

    static int d(b b2) {
        return b2.Q;
    }

    static d.d[] e(b b2) {
        return b2.t;
    }

    static short[][] j() {
        return i;
    }

    static d.g[] f(b b2) {
        return b2.s;
    }

    static byte g(b b2) {
        return b2.N;
    }

    static c.f h(b b2) {
        return b2.M;
    }

    static int a(b b2, int n2, d.g[] gArray, boolean n3) {
        int n4 = n3;
        d.g[] gArray2 = gArray;
        int n5 = n2;
        if (gArray2 != null) {
            for (n3 = 0; n3 < gArray2.length; ++n3) {
                if (gArray2[n5 = (n5 - 1 + gArray2.length) % gArray2.length] == null || gArray2[n5].z() && n4 == 0) continue;
                return n5;
            }
        }
        return -1;
    }

    static int a(b b2, int n2) {
        b2.Q = n2;
        return b2.Q;
    }

    static int b(b b2, int n2, d.g[] gArray, boolean bl) {
        return g.b.a(n2, gArray, bl);
    }

    static void i(b b2) {
        b2.x();
    }

    static c.f a(b b2, c.f f2) {
        b2.M = f2;
        return b2.M;
    }

    static d.g j(b b2) {
        return b2.H;
    }

    static b.c a(b b2, b.c c2) {
        b2.V = c2;
        return b2.V;
    }

    static b.c k(b b2) {
        return b2.V;
    }

    static byte a(b b2, byte by) {
        b2.O = by;
        return b2.O;
    }

    static byte b(b b2, byte by) {
        b2.P = by;
        return b2.P;
    }

    static byte c(b b2, byte by) {
        b2.N = by;
        return b2.N;
    }

    static void l(b b2) {
        b2.A();
    }

    static c.b a(b b2, c.b b3) {
        b2.J = b3;
        return b2.J;
    }

    static c.b m(b b2) {
        return b2.J;
    }

    static c.a a(b b2, c.a a2) {
        b2.K = a2;
        return b2.K;
    }

    static b.a a(b b2, b.a a2) {
        b2.L = a2;
        return b2.L;
    }
}

