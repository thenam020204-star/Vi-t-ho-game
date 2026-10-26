/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package c;

import a.a.f;
import a.a.h;
import a.a.k;
import a.a.o;
import a.c.b;
import a.c.d;
import c.a;
import c.l;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.MainMIDlet;

public final class e
extends a
implements h {
    private Image g = null;
    private Image h = null;
    private Image i = null;
    private Image j = null;
    private int[] k = null;
    private int l = 0;
    private int m = 0;
    private int n = 0;
    private int o = 0;
    private int p = 0;
    private long q = 0L;
    private int r = 0;
    private boolean s = false;
    private static String[] t = new String[]{"\u65b0\u5f00\u6e38\u620f", "\u4e0b\u8f7d\u7b2c\u56db\u7ae0\u5b58\u6863"};
    private static String[] u = new String[]{"\u8fd4\u56de", "\u4e0b\u8f7d\u8f69\u8f95\u5251-\u7b2c\u4e00\u7ae0", "\u4e0b\u8f7d\u8f69\u8f95\u5251-\u7b2c\u4e8c\u7ae0", "\u4e0b\u8f7d\u8f69\u8f95\u5251-\u7b2c\u4e09\u7ae0", "\u4e0b\u8f7d\u8f69\u8f95\u5251-\u7b2c\u56db\u7ae0", "\u4e0b\u8f7d\u9b54\u5bfc\u7eaa\u5143"};
    public static String f = "\u4e0b\u8f7d\u5b58\u6863\u5931\u8d25!";

    public e() {
        super((byte)1);
    }

    public final void a() {
        b.a.r();
        b.a.B = new byte[256];
        b.a.C = 0;
        b.a.p.clear();
        if (b.a.w != null) {
            b.a.w.k();
            b.a.w.j();
            b.a.w = null;
        }
        b.a.q = null;
        d d2 = a.c.d.a("/mm.pak");
        this.g = d2.c("title.png");
        this.i = d2.c("tp.png");
        this.j = d2.c("m.png");
        this.h = a.c.b.b("/tt.png");
        this.k = new int[main.a.d / this.j.getWidth() + 2];
        for (int i2 = 0; i2 < this.k.length; ++i2) {
            this.k[i2] = i2 * this.j.getWidth();
        }
        if (this.h != null) {
            this.l = this.h.getWidth() / 8;
            this.m = this.h.getHeight();
        }
        this.n = main.a.e * 3 / 4;
        this.r = b.a.W.getWidth();
        b.a.a(3, -1, (int)b.a.m);
        b.a.b();
    }

    public final void b() {
        super.b();
        this.g = null;
        this.i = null;
        this.h = null;
        System.gc();
    }

    public final void a(Graphics graphics) {
        c.a.c(graphics);
        Graphics graphics2 = graphics;
        e e2 = this;
        if (graphics2 != null && e2.g != null) {
            int n2;
            graphics2.drawImage(e2.g, 0, 0, 20);
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            for (n2 = 0; n2 < 4; ++n2) {
                n3 = e2.o + n2;
                n4 = n3 % 8 * e2.l;
                n5 = main.a.d * (n2 + 1) / 5;
                if (n3 == e2.p) {
                    graphics2.drawImage(e2.i, n5, e2.n, 3);
                }
                graphics2.drawRegion(e2.h, n4, 0, e2.l, e2.m, 0, n5, e2.n, 3);
            }
            Graphics graphics3 = graphics2;
            e e3 = e2;
            if (e3.q < System.currentTimeMillis()) {
                e3.r = (e3.r + b.a.W.getWidth() / 2) % b.a.W.getWidth();
                e3.q = System.currentTimeMillis() + 500L;
            }
            if (e3.o > 0) {
                a.c.b.a(graphics3, b.a.W, 0, 0, b.a.W.getWidth(), b.a.W.getHeight(), 0, e3.r, e3.n, 6);
            }
            if (e3.o + 4 < 8) {
                a.c.b.a(graphics3, b.a.W, 0, 0, b.a.W.getWidth(), b.a.W.getHeight(), 2, main.a.d - e3.r, e3.n, 10);
            }
            for (n2 = 0; n2 < e2.k.length; ++n2) {
                graphics2.drawImage(e2.j, e2.k[n2], main.a.e, 36);
            }
            graphics2.setColor(0xFFFFFF);
            graphics2.drawString("\u00a9 2009 Softstar Mobile", main.a.f, main.a.e - a.c.b.b, 33);
            graphics2.drawString("\u00a9 2009 Gameislive", main.a.f, main.a.e, 33);
        }
        super.b(graphics);
    }

    /*
     * Unable to fully structure code
     */
    public final void a(int var1_4, int var2_9) {
        block31: {
            c.a.c();
            for (var3_14 = 0; var3_14 < this.k.length; ++var3_14) {
                v0 = var3_14;
                this.k[v0] = this.k[v0] - 1;
                if (this.k[var3_14] >= -this.j.getWidth()) continue;
                this.k[var3_14] = this.k[(var3_14 - 1 + this.k.length) % this.k.length] + this.j.getWidth();
            }
            if (!c.a.f()) {
                var3_15 = c.a.h();
                var3_15.a(var1_4, var2_9);
                return;
            }
            block0 : switch (var1_4) {
                case 52: {
                    --this.p;
                    if (this.p < 0) {
                        this.p = 0;
                    }
                    if (this.p >= this.o) break;
                    this.o = this.p;
                    return;
                }
                case 54: {
                    ++this.p;
                    if (this.p >= 8) {
                        this.p = 7;
                    }
                    if (this.o + 4 > this.p) break;
                    this.o = this.p - 4 + 1;
                    return;
                }
                case -6: 
                case 53: {
                    switch (this.p) {
                        case 0: {
                            var1_5 = a.a.l.a(c.e.t, (byte)0, (byte)1, true);
                            var2_9 = var1_5.k();
                            if (var2_9 == 1) {
                                if ((var1_5 = var1_5.c()).equals("\u65b0\u5f00\u6e38\u620f")) {
                                    b.a.z();
                                    this.a(new l());
                                    break block0;
                                }
                                if (var1_5.equals("\u4e0b\u8f7d\u7b2c\u56db\u7ae0\u5b58\u6863")) {
                                    var1_5 = a.a.l.a(new String[]{"\u8fde\u7f51\u65b9\u5f0f1", "\u8fde\u7f51\u65b9\u5f0f2"}, (byte)0, (byte)4, false);
                                    var1_5.k();
                                    var2_9 = 1;
                                    var3_16 = var1_5.c();
                                    if (var3_16.equals("\u8fde\u7f51\u65b9\u5f0f2")) {
                                        var2_9 = 0;
                                    }
                                    if ((var1_5 = b.a.a((boolean)var2_9)) != null) {
                                        a.a.l.a("\u4e0b\u8f7d\u5b58\u6863\u6210\u529f!", -1L).k();
                                        b.a.z();
                                        var1_5.e();
                                        b.a.a(1033, 1);
                                        a.a.l.a("\u5df2\u7ee7\u627f\u4e0a\u4e00\u7ae0\u7ea7\u6570\u4e0e\u6cd5\u5b9d!", -1L).k();
                                        a.a.l.a("\u5e76\u8d60\u9001\u4e24\u500d\u91d1\u94b1\u7684\u670d\u52a1!", -1L).k();
                                        b.a.m(2);
                                        b.a.o();
                                        this.a(new l());
                                        break block0;
                                    }
                                    a.a.l.a(c.e.f, -1L).k();
                                    c.e.f = "\u4e0b\u8f7d\u5b58\u6863\u5931\u8d25!";
                                }
                                return;
                            }
                            break block31;
                        }
                        case 1: {
                            var1_4 = (main.a.d - main.a.h) / 2;
                            var2_9 = (main.a.e - main.a.i) / 2;
                            var3_17 = new o(var1_4, var2_9, main.a.h, main.a.i);
                            var1_6 = new e.e();
                            var3_17.a(var1_6);
                            var1_6.g = 1;
                            if (var3_17.k() == 1) {
                                this.a(new l());
                            }
                            return;
                        }
                        case 2: {
                            var1_7 = a.a.l.a(c.e.u, (byte)0, (byte)1, true);
                            var2_9 = var1_7.k();
                            if (var2_9 != 1 || (var1_7 = var1_7.c()).equals("\u8fd4\u56de")) break block31;
                            this = var1_7;
                            var1_7 = MainMIDlet.a.getAppProperty("CP");
                            if (var1_7 == null) {
                                var1_7 = "0";
                            }
                            if ((var2_10 = MainMIDlet.a.getAppProperty("UA")) == null) {
                                var2_10 = "N73";
                            }
                            var3_18 = null;
                            if (!this.equals("\u4e0b\u8f7d\u8f69\u8f95\u5251-\u7b2c\u4e8c\u7ae0")) ** GOTO lbl90
                            var3_18 = "http://lakoo.cn/portal/1008525/gil/" + (String)var1_7 + "/" + var2_10 + "/sword2_" + var2_10 + ".jad";
                            ** GOTO lbl101
lbl90:
                            // 1 sources

                            if (!this.equals("\u4e0b\u8f7d\u8f69\u8f95\u5251-\u7b2c\u4e00\u7ae0")) ** GOTO lbl93
                            var3_18 = "http://lakoo.cn/portal/1008260/gil/" + (String)var1_7 + "/" + var2_10 + "/sword_" + var2_10 + ".jad";
                            ** GOTO lbl101
lbl93:
                            // 1 sources

                            if (!this.equals("\u4e0b\u8f7d\u8f69\u8f95\u5251-\u7b2c\u4e09\u7ae0")) ** GOTO lbl96
                            var3_18 = "http://lakoo.cn/portal/1008656/gil/" + (String)var1_7 + "/" + var2_10 + "/sword3_" + var2_10 + ".jad";
                            ** GOTO lbl101
lbl96:
                            // 1 sources

                            if (!this.equals("\u4e0b\u8f7d\u8f69\u8f95\u5251-\u7b2c\u56db\u7ae0")) ** GOTO lbl99
                            var3_18 = "http://lakoo.cn/portal/1008766/gil/" + (String)var1_7 + "/" + var2_10 + "/sword4_" + var2_10 + ".jad";
                            ** GOTO lbl101
lbl99:
                            // 1 sources

                            if (!this.equals("\u4e0b\u8f7d\u9b54\u5bfc\u7eaa\u5143")) ** GOTO lbl102
                            var3_18 = "http://lakoo.cn/portal/1008630/gil/" + (String)var1_7 + "/" + var2_10 + "/MagicTime_" + var2_10 + ".jad";
lbl101:
                            // 5 sources

                            a.c.b.a(MainMIDlet.a, var3_18);
lbl102:
                            // 2 sources

                            main.a.a.b();
                            return;
                        }
                        case 3: {
                            d.a.b = 0;
                            d.a.a(MainMIDlet.a, (Displayable)main.a.a, "0118", "\u8f69\u8f95\u5251-\u5929\u4e4b\u75d5-\u7ec8\u7ae0", "10", main.a.d, main.a.e, -6, -7);
                            d.a.a = null;
                            return;
                        }
                        case 4: {
                            var0_1 = (main.a.d - main.a.h) / 2;
                            var1_4 = (main.a.e - main.a.i) / 2;
                            var2_11 = new o(var0_1, var1_4, main.a.h, main.a.i);
                            new o(var0_1, var1_4, main.a.h, main.a.i).s = a.c.b.b("/bg.png");
                            var3_19 = new e.a();
                            var2_11.a(var3_19);
                            var2_11.k();
                            return;
                        }
                        case 5: {
                            var0_2 = (main.a.d - main.a.h) / 2;
                            var1_4 = (main.a.e - main.a.i) / 2;
                            var2_12 = new k(var0_2, var1_4, main.a.h, main.a.i);
                            new k(var0_2, var1_4, main.a.h, main.a.i).s = a.c.b.b("/bg.png");
                            var3_20 = a.c.b.e("/help.dat");
                            var1_8 = new f("\u5e2e\u52a9", var3_20);
                            new f("\u5e2e\u52a9", var3_20).g = 1;
                            var2_12.a(var1_8);
                            var2_12.k();
                            return;
                        }
                        case 6: {
                            var0_3 = (main.a.d - main.a.h) / 2;
                            var1_4 = (main.a.e - main.a.i) / 2;
                            var2_13 = new k(var0_3, var1_4, main.a.h, main.a.i);
                            new k(var0_3, var1_4, main.a.h, main.a.i).s = a.c.b.b("/bg.png");
                            var3_21 = new f("\u5e2e\u52a9", "\u8f69\u8f95\u5251-\u5929\u4e4b\u75d5-\u7ec8\u7ae0\n\u62c9\u9614\u6e38\u620f\u51fa\u54c1\n\u00a9 2009 Gameislive\n\n\u8f69\u8f95\u5251\u7cfb\u5217\u7531\u53f0\u6e7e\u5927\u5b87\u6b63\u5f0f\u6388\u6743\nsoftstar\n\u00a9 2009 Softstar Mobile\n\n\u90ae\u7bb1:kefu@gameislive.com\n\u7535\u8bdd:020-38871667\n\n\u5236\u4f5c\u7fa4:\n\u5348\u65f6\u4e09\u523b\u5de5\u4f5c\u5ba4\n\u9879\u76ee\u8d1f\u8d23:\u98de\u9c7c\n\u6e38\u620f\u7b56\u5212:\u6ec5!\u6bba!\u5929!,\u8fea\u5947\u9a6c\u4e01\n\u4e3b\u7a0b\u5e8f:\u98de\u9c7c\n\u7a0b\u5e8f:\u6770\u592b\n\u4eba\u7269\u52a8\u753b:\u963f\u529b\u514b\u65af,\u6851\u5c3c\n\u5730\u56fe\u5236\u4f5c:\u9b3c\u4e1d,\u533b\u795e\n\u6280\u80fd\u6548\u679c/\u6e38\u620f\u754c\u9762:\u9b3c\u4e1d,\u963f\u529b\u514b\u65af\n\u8d28\u91cf\u76d1\u63a7:\u6587\u68ee\u7279,\u98ce\u94c3,\u80af\u5c3c\n");
                            new f("\u5e2e\u52a9", "\u8f69\u8f95\u5251-\u5929\u4e4b\u75d5-\u7ec8\u7ae0\n\u62c9\u9614\u6e38\u620f\u51fa\u54c1\n\u00a9 2009 Gameislive\n\n\u8f69\u8f95\u5251\u7cfb\u5217\u7531\u53f0\u6e7e\u5927\u5b87\u6b63\u5f0f\u6388\u6743\nsoftstar\n\u00a9 2009 Softstar Mobile\n\n\u90ae\u7bb1:kefu@gameislive.com\n\u7535\u8bdd:020-38871667\n\n\u5236\u4f5c\u7fa4:\n\u5348\u65f6\u4e09\u523b\u5de5\u4f5c\u5ba4\n\u9879\u76ee\u8d1f\u8d23:\u98de\u9c7c\n\u6e38\u620f\u7b56\u5212:\u6ec5!\u6bba!\u5929!,\u8fea\u5947\u9a6c\u4e01\n\u4e3b\u7a0b\u5e8f:\u98de\u9c7c\n\u7a0b\u5e8f:\u6770\u592b\n\u4eba\u7269\u52a8\u753b:\u963f\u529b\u514b\u65af,\u6851\u5c3c\n\u5730\u56fe\u5236\u4f5c:\u9b3c\u4e1d,\u533b\u795e\n\u6280\u80fd\u6548\u679c/\u6e38\u620f\u754c\u9762:\u9b3c\u4e1d,\u963f\u529b\u514b\u65af\n\u8d28\u91cf\u76d1\u63a7:\u6587\u68ee\u7279,\u98ce\u94c3,\u80af\u5c3c\n").g = 1;
                            var2_13.a(var3_21);
                            var2_13.k();
                            return;
                        }
                        case 7: {
                            d.a.b = 1;
                            d.a.a(MainMIDlet.a, (Displayable)main.a.a, "0118", "\u8f69\u8f95\u5251-\u5929\u4e4b\u75d5-\u7ec8\u7ae0", "10", main.a.d, main.a.e, -6, -7);
                            d.a.a = null;
                            main.a.a.b();
                        }
                    }
                }
            }
        }
    }

    public final void d(a.a.a a2) {
    }

    public final void c(a.a.a a2) {
        c.a.b(a2);
    }
}

