/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package d;

import c.h;
import c.j;
import c.k;
import c.l;
import c.n;
import c.t;
import c.u;
import d.a;
import d.m;
import e.a.c;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.MainMIDlet;
import main.b;

public final class e
extends a
implements main.a {
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
    private t s = null;
    private t t = null;
    private boolean u = false;

    public e() {
        super((byte)1);
    }

    public final void a() {
        b.a.E = new f.b[255];
        b.a.F = new int[255];
        b.a.C = new byte[256];
        b.a.D = 0;
        b.a.q.clear();
        if (b.a.x != null) {
            b.a.x.n();
            b.a.x.m();
            b.a.x = null;
        }
        b.a.r = null;
        c c2 = e.a.c.a("/mm.pak");
        this.g = c2.b("title.png");
        this.i = c2.b("tp.png");
        this.j = c2.b("m.png");
        this.h = e.a.b.a("/tt.png");
        this.k = new int[main.b.d / this.j.getWidth() + 2];
        for (int i2 = 0; i2 < this.k.length; ++i2) {
            this.k[i2] = i2 * this.j.getWidth();
        }
        if (this.h != null) {
            this.l = this.h.getWidth() / 8;
            this.m = this.h.getHeight();
        }
        this.n = main.b.e * 3 / 4;
        this.r = b.a.ab.getWidth();
        b.a.c(3, -1);
    }

    public final void b() {
        super.b();
        this.g = null;
        this.i = null;
        this.h = null;
        c.t.a(null);
        System.gc();
    }

    public final void a(Graphics graphics) {
        d.a.c(graphics);
        if (this.u) {
            graphics.setColor(0);
            graphics.fillRect(0, 0, main.b.d, main.b.e);
            e.a.a.a(graphics, 0, b.a.h(72), main.b.f, main.b.g - e.a.b.b / 2, 17);
            graphics.drawImage(b.a.X, 0, main.b.e, 36);
            graphics.drawImage(b.a.Y, main.b.d, main.b.e, 40);
            return;
        }
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
                n5 = main.b.d * (n2 + 1) / 5;
                if (n3 == e2.p) {
                    graphics2.drawImage(e2.i, n5, e2.n, 3);
                }
                graphics2.drawRegion(e2.h, n4, 0, e2.l, e2.m, 0, n5, e2.n, 3);
            }
            Graphics graphics3 = graphics2;
            e e3 = e2;
            if (e3.q < System.currentTimeMillis()) {
                e3.r = (e3.r + b.a.ab.getWidth() / 2) % b.a.ab.getWidth();
                e3.q = System.currentTimeMillis() + 500L;
            }
            if (e3.o > 0) {
                e.a.b.a(graphics3, b.a.ab, 0, 0, b.a.ab.getWidth(), b.a.ab.getHeight(), 0, e3.r, e3.n, 6);
            }
            if (e3.o + 4 < 8) {
                e.a.b.a(graphics3, b.a.ab, 0, 0, b.a.ab.getWidth(), b.a.ab.getHeight(), 2, main.b.d - e3.r, e3.n, 10);
            }
            for (n2 = 0; n2 < e2.k.length; ++n2) {
                graphics2.drawImage(e2.j, e2.k[n2], main.b.e, 36);
            }
            graphics2.setColor(0xFFFFFF);
            graphics2.drawString("\u00a9 2008 Softstar Mobile", main.b.f, main.b.e - e.a.b.b, 33);
            graphics2.drawString("\u00a9 2008 \u62c9\u9614\u6e38\u620f", main.b.f, main.b.e, 33);
        }
        super.b(graphics);
    }

    public final void a(int n2, int n3) {
        block28: {
            if (((e)object).u) {
                if (n2 == -6) {
                    main.b.a.b();
                    return;
                }
                if (n2 == -7) {
                    ((e)object).u = false;
                }
                return;
            }
            d.a.c();
            for (int i2 = 0; i2 < ((e)object).k.length; ++i2) {
                int n4 = i2;
                ((e)object).k[n4] = ((e)object).k[n4] - 1;
                if (((e)object).k[i2] >= -((e)object).j.getWidth()) continue;
                ((e)object).k[i2] = ((e)object).k[(i2 - 1 + ((e)object).k.length) % ((e)object).k.length] + ((e)object).j.getWidth();
            }
            if (!d.a.f()) {
                k k2 = d.a.h();
                k2.a(n2, n3);
                return;
            }
            switch (n2) {
                case 52: {
                    --((e)object).p;
                    if (((e)object).p < 0) {
                        ((e)object).p = 0;
                    }
                    if (((e)object).p >= ((e)object).o) break;
                    ((e)object).o = ((e)object).p;
                    return;
                }
                case 54: {
                    ++((e)object).p;
                    if (((e)object).p >= 8) {
                        ((e)object).p = 7;
                    }
                    if (((e)object).o + 4 > ((e)object).p) break;
                    ((e)object).o = ((e)object).p - 4 + 1;
                    return;
                }
                case -6: 
                case 53: {
                    switch (((e)object).p) {
                        case 0: {
                            b.a.b();
                            ((a)object).a(new m());
                            return;
                        }
                        case 1: {
                            c.n.a();
                            u[] uArray = new u[1];
                            u[] uArray2 = uArray;
                            uArray[0] = new n(1);
                            Image[] imageArray = new Image[1];
                            c c2 = e.a.c.a("/c.pak");
                            imageArray[0] = c2.b("load.png");
                            ((e)object).s = new t(imageArray, uArray2, main.b.d, main.b.e);
                            c.t.a(e.a.b.a("/bg.png"));
                            ((e)object).s.a((main.a)object);
                            d.a.d(((e)object).s);
                            return;
                        }
                        case 2: {
                            Object object = c.b.a(new String[]{"\u8fd4\u56de", "\u4e0b\u8f7d\u8f69\u8f95\u5251\u7b2c\u4e00\u7ae0"}, (byte)0, (byte)1, true);
                            n2 = ((j)object).f();
                            if (n2 == 1 && ((c.b)object).d().equals("\u4e0b\u8f7d\u8f69\u8f95\u5251\u7b2c\u4e00\u7ae0")) {
                                main.b.a.b();
                                try {
                                    object = MainMIDlet.a.getAppProperty("Brand");
                                    String string = MainMIDlet.a.getAppProperty("Term");
                                    MainMIDlet.a.platformRequest("http://csg13.3g.qq.com/g/s?cid=gamejx&aid=g_downApp&downType=bySeries&resId=MA2008090900009&chargeMethod=S&feeType=F&brand=" + (String)object + "&series=" + string);
                                    return;
                                }
                                catch (Exception exception) {
                                    object = exception;
                                    exception.printStackTrace();
                                    return;
                                }
                            }
                            break block28;
                        }
                        case 3: {
                            Object object = c.b.a(new String[]{"\u8fd4\u56de", "\u8bbf\u95eeQQ\u6e38\u620f\u4e2d\u5fc3"}, (byte)0, (byte)1, true);
                            n2 = ((j)object).f();
                            if (n2 == 1 && ((c.b)object).d().equals("\u8bbf\u95eeQQ\u6e38\u620f\u4e2d\u5fc3")) {
                                main.b.a.b();
                                try {
                                    MainMIDlet.a.platformRequest("http://3g.qq.com/forward.jsp?from=kjavagame&bid=4");
                                    return;
                                }
                                catch (Exception exception) {
                                    object = exception;
                                    exception.printStackTrace();
                                    return;
                                }
                            }
                            break block28;
                        }
                        case 4: {
                            u[] uArray = new u[1];
                            Object object = uArray;
                            uArray[0] = new h();
                            c c3 = e.a.c.a("/c.pak");
                            Image[] imageArray = new Image[1];
                            Image[] imageArray2 = imageArray;
                            imageArray[0] = c3.b("set.png");
                            object = new t(imageArray2, (u[])object, main.b.d, main.b.e);
                            c.t.a(e.a.b.a("/bg.png"));
                            ((k)object).a((main.a)object);
                            d.a.d((k)object);
                            return;
                        }
                        case 5: {
                            String[] stringArray = e.a.b.b("/help.dat");
                            u[] uArray = new u[1];
                            u[] uArray3 = uArray;
                            uArray[0] = new l(stringArray);
                            c c4 = e.a.c.a("/c.pak");
                            Image[] imageArray = new Image[1];
                            stringArray = imageArray;
                            imageArray[0] = c4.b("help.png");
                            ((e)object).t = new t((Image[])stringArray, uArray3, main.b.d, main.b.e);
                            c.t.a(e.a.b.a("/bg.png"));
                            ((e)object).t.a((main.a)object);
                            d.a.d(((e)object).t);
                            return;
                        }
                        case 6: {
                            u[] uArray = new u[1];
                            u[] uArray4 = uArray;
                            uArray[0] = new l("\u62c9\u9614\u6e38\u620f\u51fa\u54c1\n\u00a9 2008 gameislive\n\n\u8f69\u8f95\u5251\u7cfb\u5217\u7531\u53f0\u6e7e\u5927\u5b87\u6b63\u5f0f\u6388\u6743\nsoftstar\n\u00a9 2008 Softstar Mobile\n\n\u90ae\u7bb1:kefu@gameislive.com\n\u7535\u8bdd:020-88302012\n\n\u5236\u4f5c\u7fa4:\n\u5348\u65f6\u4e09\u523b\u5de5\u4f5c\u5ba4\n\u9879\u76ee\u8d1f\u8d23:\u98de\u9c7c\n\u6e38\u620f\u7b56\u5212:\u963f\u8fbe,\u96f7\u65af\u6797,\u8fea\u5947\u9a6c\u4e01\n\u4e3b\u7a0b\u5e8f:\u98de\u9c7c\n\u4eba\u7269\u52a8\u753b:\u6851\u5c3c\n\u5730\u56fe\u5236\u4f5c:\u5c0f\u8377\u8f69\u8f69\n\u6280\u80fd\u6548\u679c/\u6e38\u620f\u754c\u9762:\u5510\u58a8\n\u8d28\u91cf\u76d1\u63a7:\u6587\u68ee\u7279,\u8d4c\u540e\n\n\u7248\u672c\u53f7:" + b.a.al);
                            Image[] imageArray = new Image[1];
                            Image[] imageArray3 = imageArray;
                            imageArray[0] = e.a.b.a("/about.png");
                            ((e)object).t = new t(imageArray3, uArray4, main.b.d, main.b.e);
                            c.t.a(e.a.b.a("/bg.png"));
                            ((e)object).t.a((main.a)object);
                            d.a.d(((e)object).t);
                            return;
                        }
                        case 7: {
                            ((e)object).u = true;
                        }
                    }
                }
            }
        }
    }

    public final void a(k k2) {
    }

    public final void b(k k2) {
    }

    public final void c(k k2) {
        d.a.e(k2);
        if (k2 == this.s) {
            c.n.a = null;
        }
    }

    static {
        String[] stringArray = new String[]{"1", "2", "3", "4"};
    }
}

