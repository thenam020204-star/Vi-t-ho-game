/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package d;

import b.b.c;
import b.b.f;
import b.b.g;
import b.b.m;
import b.e.d;
import c.e;
import d.j;
import d.k;
import g.b;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.MainMIDlet;
import main.a;

public final class h
extends k
implements b.b.j {
    private Image a = null;
    private Image b = null;
    private Image c = null;
    private Image d = null;
    private int[] e = null;
    private int f = 0;
    private int g = 0;
    private int m = 0;
    private int n = 0;
    private int o = 0;
    private long p = 0L;
    private int q = 0;
    private boolean r = false;
    private static String[] s = new String[]{"\u65b0\u5f00\u6e38\u620f", "\u4e0b\u8f7d\u7b2c\u4e09\u7ae0\u5b58\u6863"};
    private static String[] t = new String[]{"\u8fd4\u56de", "\u4e0b\u8f7d\u8f69\u8f95\u5251-\u7b2c\u4e00\u7ae0", "\u4e0b\u8f7d\u8f69\u8f95\u5251-\u7b2c\u4e8c\u7ae0", "\u4e0b\u8f7d\u8f69\u8f95\u5251-\u7b2c\u4e09\u7ae0", "\u4e0b\u8f7d\u9b54\u5bfc\u7eaa\u5143"};

    public h() {
        super((byte)1);
    }

    public final void a() {
        a.f.r();
        a.f.B = new byte[256];
        a.f.C = 0;
        a.f.p.clear();
        if (a.f.w != null) {
            a.f.w.l();
            a.f.w.k();
            a.f.w = null;
        }
        a.f.q = null;
        d d2 = b.e.d.a("/mm.pak");
        this.a = d2.c("title.png");
        this.c = d2.c("tp.png");
        this.d = d2.c("m.png");
        this.b = b.e.a.b("/tt.png");
        this.e = new int[main.a.d / this.d.getWidth() + 2];
        for (int i2 = 0; i2 < this.e.length; ++i2) {
            this.e[i2] = i2 * this.d.getWidth();
        }
        if (this.b != null) {
            this.f = this.b.getWidth() / 8;
            this.g = this.b.getHeight();
        }
        this.m = main.a.e * 3 / 4;
        this.q = a.f.W.getWidth();
        a.f.a(3, -1, (int)a.f.m);
        a.f.b();
    }

    public final void b() {
        super.b();
        this.a = null;
        this.c = null;
        this.b = null;
        System.gc();
    }

    public final void a(Graphics graphics) {
        d.k.c(graphics);
        Graphics graphics2 = graphics;
        h h2 = this;
        if (graphics2 != null && h2.a != null) {
            int n2;
            graphics2.drawImage(h2.a, 0, 0, 20);
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            for (n2 = 0; n2 < 4; ++n2) {
                n3 = h2.n + n2;
                n4 = n3 % 8 * h2.f;
                n5 = main.a.d * (n2 + 1) / 5;
                if (n3 == h2.o) {
                    graphics2.drawImage(h2.c, n5, h2.m, 3);
                }
                graphics2.drawRegion(h2.b, n4, 0, h2.f, h2.g, 0, n5, h2.m, 3);
            }
            Graphics graphics3 = graphics2;
            h h3 = h2;
            if (h3.p < System.currentTimeMillis()) {
                h3.q = (h3.q + a.f.W.getWidth() / 2) % a.f.W.getWidth();
                h3.p = System.currentTimeMillis() + 500L;
            }
            if (h3.n > 0) {
                b.e.a.a(graphics3, a.f.W, 0, 0, a.f.W.getWidth(), a.f.W.getHeight(), 0, h3.q, h3.m, 6);
            }
            if (h3.n + 4 < 8) {
                b.e.a.a(graphics3, a.f.W, 0, 0, a.f.W.getWidth(), a.f.W.getHeight(), 2, main.a.d - h3.q, h3.m, 10);
            }
            for (n2 = 0; n2 < h2.e.length; ++n2) {
                graphics2.drawImage(h2.d, h2.e[n2], main.a.e, 36);
            }
            graphics2.setColor(0xFFFFFF);
            graphics2.drawString("\u00a9 2009 Softstar Mobile", main.a.f, main.a.e - b.e.a.b, 33);
            graphics2.drawString("\u00a9 2009 Gameislive", main.a.f, main.a.e, 33);
        }
        super.b(graphics);
    }

    public final void a(int n2, int n3) {
        block38: {
            d.k.d();
            for (int i2 = 0; i2 < ((h)object2).e.length; ++i2) {
                int n4 = i2;
                ((h)object2).e[n4] = ((h)object2).e[n4] - 1;
                if (((h)object2).e[i2] >= -((h)object2).d.getWidth()) continue;
                ((h)object2).e[i2] = ((h)object2).e[(i2 - 1 + ((h)object2).e.length) % ((h)object2).e.length] + ((h)object2).d.getWidth();
            }
            if (!d.k.g()) {
                c c2 = d.k.i();
                c2.a(n2, n3);
                return;
            }
            block1 : switch (n2) {
                case 52: {
                    --((h)object2).o;
                    if (((h)object2).o < 0) {
                        ((h)object2).o = 0;
                    }
                    if (((h)object2).o >= ((h)object2).n) break;
                    ((h)object2).n = ((h)object2).o;
                    return;
                }
                case 54: {
                    ++((h)object2).o;
                    if (((h)object2).o >= 8) {
                        ((h)object2).o = 7;
                    }
                    if (((h)object2).n + 4 > ((h)object2).o) break;
                    ((h)object2).n = ((h)object2).o - 4 + 1;
                    return;
                }
                case -6: 
                case 53: {
                    switch (((h)object2).o) {
                        case 0: {
                            Object object = b.b.g.a(s, (byte)0, (byte)4, true);
                            n3 = ((m)object).k();
                            if (n3 == 1) {
                                if (((String)(object = ((g)object).a())).equals("\u65b0\u5f00\u6e38\u620f")) {
                                    a.f.z();
                                    ((k)object2).a(new j());
                                    return;
                                }
                                if (((String)object).equals("\u4e0b\u8f7d\u7b2c\u4e09\u7ae0\u5b58\u6863")) {
                                    a.c c3;
                                    object = b.b.g.a(new String[]{"\u8fde\u7f51\u65b9\u5f0f1", "\u8fde\u7f51\u65b9\u5f0f2"}, (byte)0, (byte)4, false);
                                    ((m)object).k();
                                    n3 = 1;
                                    object = ((g)object).a();
                                    if (((String)object).equals("\u8fde\u7f51\u65b9\u5f0f2")) {
                                        n3 = 0;
                                    }
                                    if ((c3 = a.f.a(n3 != 0)) != null) {
                                        b.b.g.a("\u4e0b\u8f7d\u5b58\u6863\u6210\u529f!", -1L).k();
                                        a.f.z();
                                        c3.e();
                                        a.f.a(1030, 1);
                                        b.b.g.a("\u5df2\u7ee7\u627f\u4e0a\u4e00\u7ae0\u7ea7\u6570\u4e0e\u6cd5\u5b9d!", -1L).k();
                                        b.b.g.a("\u5e76\u8d60\u9001\u4e24\u500d\u91d1\u94b1\u7684\u670d\u52a1!", -1L).k();
                                        a.f.m(2);
                                        a.f.o();
                                        ((k)object2).a(new j());
                                        break block1;
                                    }
                                    b.b.g.a("\u4e0b\u8f7d\u5b58\u6863\u5931\u8d25!", -1L).k();
                                    return;
                                }
                            }
                            break block38;
                        }
                        case 1: {
                            n2 = (main.a.d - main.a.h) / 2;
                            n3 = (main.a.e - main.a.i) / 2;
                            b.b.h h2 = new b.b.h(n2, n3, main.a.h, main.a.i);
                            e e2 = new e();
                            h2.a(e2);
                            e2.k = 1;
                            if (h2.k() == 1) {
                                ((k)object2).a(new j());
                            }
                            return;
                        }
                        case 2: {
                            Object object = b.b.g.a(t, (byte)0, (byte)1, true);
                            n3 = ((m)object).k();
                            if (n3 == 1 && !((String)(object = ((g)object).a())).equals("\u8fd4\u56de")) {
                                String string;
                                Object object2 = MainMIDlet.a.getAppProperty("CP");
                                if (object2 == null) {
                                    object2 = "0";
                                }
                                if ((string = MainMIDlet.a.getAppProperty("UA")) == null) {
                                    string = "N73";
                                }
                                String string2 = null;
                                if (((String)object).equals("\u4e0b\u8f7d\u8f69\u8f95\u5251-\u7b2c\u4e8c\u7ae0")) {
                                    string2 = "http://lakoo.cn/portal/1008525/gil/" + (String)object2 + "/" + string + "/sword2_" + string + ".jad";
                                } else if (((String)object).equals("\u4e0b\u8f7d\u8f69\u8f95\u5251-\u7b2c\u4e00\u7ae0")) {
                                    string2 = "http://lakoo.cn/portal/1008260/gil/" + (String)object2 + "/" + string + "/sword_" + string + ".jad";
                                } else if (((String)object).equals("\u4e0b\u8f7d\u8f69\u8f95\u5251-\u7b2c\u4e09\u7ae0")) {
                                    string2 = "http://lakoo.cn/portal/1008656/gil/" + (String)object2 + "/" + string + "/sword3_" + string + ".jad";
                                } else if (((String)object).equals("\u4e0b\u8f7d\u9b54\u5bfc\u7eaa\u5143")) {
                                    string2 = "http://lakoo.cn/portal/1008630/gil/" + (String)object2 + "/" + string + "/MagicTime_" + string + ".jad";
                                } else {
                                    return;
                                }
                                try {
                                    MainMIDlet.a.platformRequest(string2);
                                }
                                catch (Exception exception) {}
                                main.a.a.b();
                                return;
                            }
                            break block38;
                        }
                        case 3: {
                            g.b.b = 0;
                            g.b.a(MainMIDlet.a, (Displayable)main.a.a, "0117", "\u8f69\u8f95\u5251-\u5929\u4e4b\u75d5-\u56db\u7ae0", "10", main.a.d, main.a.e, -6, -7);
                            g.b.a = null;
                            return;
                        }
                        case 4: {
                            int n5 = (main.a.d - main.a.h) / 2;
                            n2 = (main.a.e - main.a.i) / 2;
                            b.b.h h3 = new b.b.h(n5, n2, main.a.h, main.a.i);
                            new b.b.h(n5, n2, main.a.h, main.a.i).k = b.e.a.b("/bg.png");
                            c.a a2 = new c.a();
                            h3.a(a2);
                            h3.k();
                            return;
                        }
                        case 5: {
                            int n6 = (main.a.d - main.a.h) / 2;
                            n2 = (main.a.e - main.a.i) / 2;
                            f f2 = new f(n6, n2, main.a.h, main.a.i);
                            new f(n6, n2, main.a.h, main.a.i).k = b.e.a.b("/bg.png");
                            String string = b.e.a.e("/help.dat");
                            b.b.a a3 = new b.b.a("\u5e2e\u52a9", string);
                            new b.b.a("\u5e2e\u52a9", string).k = 1;
                            f2.a(a3);
                            f2.k();
                            return;
                        }
                        case 6: {
                            int n7 = (main.a.d - main.a.h) / 2;
                            n2 = (main.a.e - main.a.i) / 2;
                            f f3 = new f(n7, n2, main.a.h, main.a.i);
                            new f(n7, n2, main.a.h, main.a.i).k = b.e.a.b("/bg.png");
                            b.b.a a4 = new b.b.a("\u5e2e\u52a9", "\u8f69\u8f95\u5251-\u5929\u4e4b\u75d5-\u7b2c\u56db\u7ae0\n\u62c9\u9614\u6e38\u620f\u51fa\u54c1\n\u00a9 2009 gameislive\n\n\u8f69\u8f95\u5251\u7cfb\u5217\u7531\u53f0\u6e7e\u5927\u5b87\u6b63\u5f0f\u6388\u6743\nsoftstar\n\u00a9 2008 Softstar Mobile\n\n\u62c9\u9614\u6e38\u620f\u53d1\u884c\n\u90ae\u7bb1:kefu@gameislive.com\n\u7535\u8bdd:020-86001126\n\n\u5236\u4f5c\u7fa4:\n\u5348\u65f6\u4e09\u523b\u5de5\u4f5c\u5ba4\n\u9879\u76ee\u8d1f\u8d23:\u98de\u9c7c\n\u6e38\u620f\u7b56\u5212:\u8fea\u5947\u9a6c\u4e01\n\u4e3b\u7a0b\u5e8f:\u98de\u9c7c\n\u4eba\u7269\u52a8\u753b:\u6851\u5c3c\n\u5730\u56fe\u5236\u4f5c:\u533b\u795e\n\u6280\u80fd\u6548\u679c/\u6e38\u620f\u754c\u9762:\u9b3c\u4e1d,\u963f\u529b\u514b\u65af\n\u8d28\u91cf\u76d1\u63a7:\u6587\u68ee\u7279,\u8d4c\u540e,\u98ce\u94c3\n");
                            new b.b.a("\u5e2e\u52a9", "\u8f69\u8f95\u5251-\u5929\u4e4b\u75d5-\u7b2c\u56db\u7ae0\n\u62c9\u9614\u6e38\u620f\u51fa\u54c1\n\u00a9 2009 gameislive\n\n\u8f69\u8f95\u5251\u7cfb\u5217\u7531\u53f0\u6e7e\u5927\u5b87\u6b63\u5f0f\u6388\u6743\nsoftstar\n\u00a9 2008 Softstar Mobile\n\n\u62c9\u9614\u6e38\u620f\u53d1\u884c\n\u90ae\u7bb1:kefu@gameislive.com\n\u7535\u8bdd:020-86001126\n\n\u5236\u4f5c\u7fa4:\n\u5348\u65f6\u4e09\u523b\u5de5\u4f5c\u5ba4\n\u9879\u76ee\u8d1f\u8d23:\u98de\u9c7c\n\u6e38\u620f\u7b56\u5212:\u8fea\u5947\u9a6c\u4e01\n\u4e3b\u7a0b\u5e8f:\u98de\u9c7c\n\u4eba\u7269\u52a8\u753b:\u6851\u5c3c\n\u5730\u56fe\u5236\u4f5c:\u533b\u795e\n\u6280\u80fd\u6548\u679c/\u6e38\u620f\u754c\u9762:\u9b3c\u4e1d,\u963f\u529b\u514b\u65af\n\u8d28\u91cf\u76d1\u63a7:\u6587\u68ee\u7279,\u8d4c\u540e,\u98ce\u94c3\n").k = 1;
                            f3.a(a4);
                            f3.k();
                            return;
                        }
                        case 7: {
                            g.b.b = 1;
                            g.b.a(MainMIDlet.a, (Displayable)main.a.a, "0117", "\u8f69\u8f95\u5251-\u5929\u4e4b\u75d5-\u56db\u7ae0", "10", main.a.d, main.a.e, -6, -7);
                            g.b.a = null;
                            main.a.a.b();
                        }
                    }
                }
            }
        }
    }

    public final void a(c c2) {
    }

    public final void b(c c2) {
        d.k.d(c2);
    }
}

