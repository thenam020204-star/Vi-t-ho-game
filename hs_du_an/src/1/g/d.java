/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.ConnectionNotFoundException
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package g;

import b.h;
import b.j;
import b.k;
import b.m;
import b.s;
import b.t;
import c.f;
import g.l;
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.MainMIDlet;
import main.a;
import main.b;
import main.c;

public final class d
extends c
implements a {
    private Image d = null;
    private Image e = null;
    private Image f = null;
    private Image g = null;
    private int[] h = null;
    private int i = 0;
    private int j = 0;
    private int k = 0;
    private int l = 0;
    private int m = 0;
    private long n = 0L;
    private int o = 0;
    private s p = null;
    private s q = null;
    private boolean r = false;

    public d() {
        super((byte)1);
    }

    public final void a() {
        d.a.F = new f[256];
        d.a.G = new int[256];
        d.a.D = new byte[256];
        d.a.E = 0;
        d.a.r.clear();
        d.a.y = null;
        d.a.s = null;
        e.a a2 = e.a.a("/mm.pak");
        this.d = a2.c("title.png");
        this.f = a2.c("tp.png");
        this.g = a2.c("m.png");
        a2.c("cr.png");
        this.e = h.b.a("/tt.png");
        this.h = new int[main.b.c / this.g.getWidth() + 2];
        for (int i2 = 0; i2 < this.h.length; ++i2) {
            this.h[i2] = i2 * this.g.getWidth();
        }
        if (this.e != null) {
            this.i = this.e.getWidth() / 7;
            this.j = this.e.getHeight();
        }
        this.k = main.b.d * 3 / 4;
        this.o = d.a.ac.getWidth();
        d.a.c(3, -1);
    }

    public final void b() {
        super.b();
        this.d = null;
        this.f = null;
        this.e = null;
        System.gc();
    }

    public final void a(Graphics graphics) {
        main.c.c(graphics);
        if (this.r) {
            graphics.setColor(0);
            graphics.fillRect(0, 0, main.b.c, main.b.d);
            h.a.a(graphics, 0, d.a.j(72), main.b.e, main.b.f - h.b.b / 2, 17);
            graphics.drawImage(d.a.Y, 0, main.b.d, 36);
            graphics.drawImage(d.a.Z, main.b.c, main.b.d, 40);
            return;
        }
        Graphics graphics2 = graphics;
        d d2 = this;
        if (graphics2 != null && d2.d != null) {
            int n2;
            graphics2.drawImage(d2.d, 0, 0, 20);
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            for (n2 = 0; n2 < 4; ++n2) {
                n3 = d2.l + n2;
                n4 = n3 % 7 * d2.i;
                n5 = main.b.c * (n2 + 1) / 5;
                if (n3 == d2.m) {
                    graphics2.drawImage(d2.f, n5, d2.k, 3);
                }
                graphics2.drawRegion(d2.e, n4, 0, d2.i, d2.j, 0, n5, d2.k, 3);
            }
            Graphics graphics3 = graphics2;
            d d3 = d2;
            if (d3.n < System.currentTimeMillis()) {
                d3.o = (d3.o + d.a.ac.getWidth() / 2) % d.a.ac.getWidth();
                d3.n = System.currentTimeMillis() + 500L;
            }
            if (d3.l > 0) {
                h.b.a(graphics3, d.a.ac, 0, 0, d.a.ac.getWidth(), d.a.ac.getHeight(), 0, d3.o, d3.k, 6);
            }
            if (d3.l + 4 < 7) {
                h.b.a(graphics3, d.a.ac, 0, 0, d.a.ac.getWidth(), d.a.ac.getHeight(), 2, main.b.c - d3.o, d3.k, 10);
            }
            for (n2 = 0; n2 < d2.h.length; ++n2) {
                graphics2.drawImage(d2.g, d2.h[n2], main.b.d, 36);
            }
            graphics2.setColor(0xFFFFFF);
            graphics2.drawString("\u00a9 2008 Softstar Mobile", main.b.e, main.b.d - h.b.b, 33);
            graphics2.drawString("\u00a9 2008 \u62c9\u9614\u6e38\u620f", main.b.e, main.b.d, 33);
        }
        super.b(graphics);
    }

    public final void a(int n2, int n3) {
        if (((d)((Object)connectionNotFoundException2)).r) {
            if (n2 == -6) {
                main.b.a.b();
                return;
            }
            if (n2 == -7) {
                ((d)((Object)connectionNotFoundException2)).r = false;
            }
            return;
        }
        super.c();
        for (int i2 = 0; i2 < ((d)((Object)connectionNotFoundException2)).h.length; ++i2) {
            int n4 = i2;
            ((d)((Object)((Object)connectionNotFoundException2))).h[n4] = ((d)((Object)((Object)connectionNotFoundException2))).h[n4] - 1;
            if (((d)((Object)connectionNotFoundException2)).h[i2] >= -((d)((Object)connectionNotFoundException2)).g.getWidth()) continue;
            ((d)((Object)connectionNotFoundException2)).h[i2] = ((d)((Object)connectionNotFoundException2)).h[(i2 - 1 + ((d)((Object)connectionNotFoundException2)).h.length) % ((d)((Object)connectionNotFoundException2)).h.length] + ((d)((Object)connectionNotFoundException2)).g.getWidth();
        }
        if (!((c)((Object)connectionNotFoundException2)).f()) {
            j j2 = ((c)((Object)connectionNotFoundException2)).h();
            j2.a(n2, n3);
            return;
        }
        switch (n2) {
            case 52: {
                --((d)((Object)connectionNotFoundException2)).m;
                if (((d)((Object)connectionNotFoundException2)).m < 0) {
                    ((d)((Object)connectionNotFoundException2)).m = 0;
                }
                if (((d)((Object)connectionNotFoundException2)).m >= ((d)((Object)connectionNotFoundException2)).l) break;
                ((d)((Object)connectionNotFoundException2)).l = ((d)((Object)connectionNotFoundException2)).m;
                return;
            }
            case 54: {
                ++((d)((Object)connectionNotFoundException2)).m;
                if (((d)((Object)connectionNotFoundException2)).m >= 7) {
                    ((d)((Object)connectionNotFoundException2)).m = 6;
                }
                if (((d)((Object)connectionNotFoundException2)).l + 4 > ((d)((Object)connectionNotFoundException2)).m) break;
                ((d)((Object)connectionNotFoundException2)).l = ((d)((Object)connectionNotFoundException2)).m - 4 + 1;
                return;
            }
            case -6: 
            case 53: {
                switch (((d)((Object)connectionNotFoundException2)).m) {
                    case 0: {
                        d.a.b();
                        ((c)((Object)connectionNotFoundException2)).a(new l());
                        return;
                    }
                    case 1: {
                        b.m.a();
                        t[] tArray = new t[1];
                        t[] tArray2 = tArray;
                        tArray[0] = new m(1);
                        Image[] imageArray = new Image[1];
                        e.a a2 = e.a.a("/c.pak");
                        imageArray[0] = a2.c("load.png");
                        ((d)((Object)connectionNotFoundException2)).p = new s(imageArray, tArray2, main.b.c, main.b.d, null);
                        ((d)((Object)connectionNotFoundException2)).p.a(a2.c("bg.png"));
                        ((d)((Object)connectionNotFoundException2)).p.a((a)((Object)connectionNotFoundException2));
                        ((c)((Object)connectionNotFoundException2)).e(((d)((Object)connectionNotFoundException2)).p);
                        return;
                    }
                    case 2: {
                        main.b.a.b();
                        try {
                            MainMIDlet.a.platformRequest("http://3g.qq.com/forward.jsp?from=kjavagame&bid=4");
                            return;
                        }
                        catch (ConnectionNotFoundException connectionNotFoundException) {
                            ConnectionNotFoundException connectionNotFoundException2 = connectionNotFoundException;
                            connectionNotFoundException.printStackTrace();
                            return;
                        }
                    }
                    case 3: {
                        t[] tArray = new t[1];
                        Object object = tArray;
                        tArray[0] = new h();
                        e.a a3 = e.a.a("/c.pak");
                        Image[] imageArray = new Image[1];
                        Image[] imageArray2 = imageArray;
                        imageArray[0] = a3.c("set.png");
                        object = new s(imageArray2, (t[])object, main.b.c, main.b.d, null);
                        ((s)object).a(a3.c("bg.png"));
                        ((j)object).a((a)((Object)connectionNotFoundException2));
                        ((c)((Object)connectionNotFoundException2)).e((j)object);
                        return;
                    }
                    case 4: {
                        String[] stringArray = h.b.b("/t2.dat");
                        t[] tArray = new t[1];
                        t[] tArray3 = tArray;
                        tArray[0] = new k(stringArray);
                        e.a a4 = e.a.a("/c.pak");
                        Image[] imageArray = new Image[1];
                        stringArray = imageArray;
                        imageArray[0] = a4.c("help.png");
                        ((d)((Object)connectionNotFoundException2)).q = new s((Image[])stringArray, tArray3, main.b.c, main.b.d, null);
                        ((d)((Object)connectionNotFoundException2)).q.a(a4.c("bg.png"));
                        ((d)((Object)connectionNotFoundException2)).q.a((a)((Object)connectionNotFoundException2));
                        ((c)((Object)connectionNotFoundException2)).e(((d)((Object)connectionNotFoundException2)).q);
                        return;
                    }
                    case 5: {
                        t[] tArray = new t[1];
                        t[] tArray4 = tArray;
                        tArray[0] = new k("\u62c9\u9614\u6e38\u620f\u51fa\u54c1\n\u00a9 2008 gameislive\n\n\u8f69\u8f95\u5251\u7cfb\u5217\u7531\u53f0\u6e7e\u5927\u5b87\u6b63\u5f0f\u6388\u6743\nsoftstar\n\u00a9 2008 Softstar Mobile\n\n\u90ae\u7bb1:gameislive@wubile.com\n\u7535\u8bdd:020-88302012\n\n\u5236\u4f5c\u7fa4:\n\u5348\u65f6\u4e09\u523b\u5de5\u4f5c\u5ba4\n\u6e38\u620f\u7b56\u5212:\u963f\u8fbe,\u96f7\u65af\u6797\n\u4e3b\u7a0b\u5e8f:\u98de\n\u4eba\u7269\u52a8\u753b:\u6d77\u8fb9\u7684\u6536\u97f3\u673a\n\u5730\u56fe\u5236\u4f5c:\u5c0f\u8377\u8f69\u8f69\n\u6280\u80fd\u6548\u679c/\u6e38\u620f\u754c\u9762:\u8d35\u4e1d\n\u8d28\u91cf\u76d1\u63a7:\u4e54\u4e1d,\u6587\u68ee\u7279,\u8d4c\u540e,\u8fea\u5947\u9a6c\u4e01\n");
                        e.a a5 = e.a.a("/c.pak");
                        Image[] imageArray = new Image[1];
                        Image[] imageArray3 = imageArray;
                        imageArray[0] = a5.c("help.png");
                        ((d)((Object)connectionNotFoundException2)).q = new s(imageArray3, tArray4, main.b.c, main.b.d, null);
                        ((d)((Object)connectionNotFoundException2)).q.a(a5.c("bg.png"));
                        ((d)((Object)connectionNotFoundException2)).q.a((a)((Object)connectionNotFoundException2));
                        ((c)((Object)connectionNotFoundException2)).e(((d)((Object)connectionNotFoundException2)).q);
                        return;
                    }
                    case 6: {
                        ((d)((Object)connectionNotFoundException2)).r = true;
                    }
                }
            }
        }
    }

    public final String toString() {
        return "MainMenuStage";
    }

    public final void a(j j2) {
    }

    public final void b(j j2) {
    }

    public final void c(j j2) {
    }

    public final void d(j j2) {
        this.f(j2);
        if (j2 == this.p) {
            b.m.a = null;
        }
    }
}

