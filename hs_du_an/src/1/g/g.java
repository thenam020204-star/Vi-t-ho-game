/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package g;

import b.h;
import b.i;
import b.j;
import b.l;
import b.m;
import b.n;
import b.o;
import b.q;
import b.r;
import b.s;
import b.t;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.a;
import main.b;
import main.c;

public final class g
extends c
implements a {
    private static r d = null;
    private static s e = null;
    private static s f = null;
    private static s g = null;
    private static s h = null;
    private static s i = null;

    public g() {
        super((byte)3);
    }

    public final void a(Graphics graphics) {
        main.c.c(graphics);
        super.b(graphics);
    }

    public final void a() {
        System.gc();
        h.b.a(100L);
        this.e(d.a.y);
        d.a.y.a(this);
    }

    public final void b() {
        super.b();
        q.a = null;
    }

    public final void a(int n2, int n3) {
        super.c();
        j j2 = (j)((c)((Object)j2)).e().peek();
        j2.a(n2, n3);
        if (d.a.s != null) {
            d.a.s.a();
            if (d.a.s == null || d.a.s != null && d.a.s.b()) {
                d.a.s = null;
                d.a.k();
            }
        }
    }

    public final void b(j j2) {
        if (d.a.s != null) {
            return;
        }
        if (j2 == d.a.y) {
            d = new r();
            d.a(this);
            this.e(d);
        }
    }

    public final void c(j tArray) {
        if (d.a.s != null) {
            return;
        }
        if (tArray == d.a.y) {
            t[] tArray2 = new i[1];
            tArray = tArray2;
            tArray2[0] = new i();
            Image[] imageArray = new Image[1];
            e.a a2 = e.a.a("/c.pak");
            imageArray[0] = a2.c("s0.png");
            e = new s(imageArray, tArray, main.b.c, main.b.d, null);
            e.a(a2.c("bg.png"));
            e.a(this);
            this.e(e);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public final void a(j object) {
        Object object2 = ((j)object).d();
        if (object != d) {
            ((j)object).b();
            return;
        }
        Object object3 = object2;
        if (((String)object3).equals(d.a.q[11])) {
            m.a();
            t[] tArray = new t[3];
            t[] tArray2 = tArray;
            tArray[0] = new m(0);
            tArray2[1] = new m(1);
            tArray2[2] = new h();
            object2 = e.a.a("/c.pak");
            Image[] imageArray = new Image[3];
            Image[] imageArray2 = imageArray;
            imageArray[0] = ((e.a)object2).c("save.png");
            imageArray2[1] = ((e.a)object2).c("load.png");
            imageArray2[2] = ((e.a)object2).c("set.png");
            i = new s(imageArray2, tArray2, main.b.c, main.b.d, null);
            i.a(((e.a)object2).c("bg.png"));
            i.a(this);
            this.e(i);
            return;
        }
        if (((String)object3).equals(d.a.q[9])) {
            Image[] imageArray = new Image[2];
            object2 = e.a.a("/c.pak");
            imageArray[0] = ((e.a)object2).c("pt0.png");
            imageArray[1] = ((e.a)object2).c("pt1.png");
            Image image = null;
            t[] tArray = new l[d.a.C.length];
            for (int i2 = 0; i2 < tArray.length; ++i2) {
                image = h.b.a("/fra/r" + d.a.C[i2].f + ".png");
                tArray[i2] = new l(d.a.C[i2], imageArray, image);
            }
            Image[] imageArray3 = new Image[d.a.C.length];
            boolean bl = false;
            while (true) {
                void var1_6;
                if (var1_6 >= imageArray3.length) {
                    g = new s(imageArray3, tArray, main.b.c, main.b.d, null);
                    g.a(((e.a)object2).c("bg.png"));
                    g.a(this);
                    this.e(g);
                    return;
                }
                imageArray3[var1_6] = d.a.S[d.a.C[var1_6].f];
                ++var1_6;
            }
        }
        if (((String)object3).equals(d.a.q[8])) {
            this.i();
            return;
        }
        if (((String)object3).equals(d.a.q[7])) {
            this.k();
            return;
        }
        if (((String)object3).equals(d.a.q[10])) {
            b.c c2 = b.c.a(d.a.q[69], 3000L, null);
            c2.a(this);
            this.e(c2);
            return;
        }
        if (!((String)object3).equals(d.a.q[0])) return;
        this.j();
    }

    public final void d(j j2) {
        this.f(j2);
        if (j2 == i) {
            m.a = null;
        }
    }

    public final void a(String object, long l2) {
        object = b.c.a((String)object, l2, null);
        ((j)object).a(this);
        this.e((j)object);
    }

    private void i() {
        t[] tArray = new b.b[b.b.a.length];
        for (int i2 = 0; i2 < tArray.length; ++i2) {
            tArray[i2] = new b.b(b.b.a[i2], false);
        }
        Image[] imageArray = new Image[b.b.a.length];
        for (int i3 = 0; i3 < imageArray.length; ++i3) {
            imageArray[i3] = d.a.T[b.b.a[i3]];
        }
        e.a a2 = e.a.a("/c.pak");
        e = new s(imageArray, tArray, main.b.c, main.b.d, null);
        e.a(a2.c("bg.png"));
        e.a(this);
        this.e(e);
    }

    private void j() {
        t[] tArray = new n[d.a.C.length];
        for (int i2 = 0; i2 < tArray.length; ++i2) {
            tArray[i2] = new n(d.a.C[i2]);
        }
        Image[] imageArray = new Image[d.a.C.length];
        for (int i3 = 0; i3 < imageArray.length; ++i3) {
            imageArray[i3] = d.a.S[d.a.C[i3].f];
        }
        e.a a2 = e.a.a("/c.pak");
        f = new s(imageArray, tArray, main.b.c, main.b.d, null);
        f.a(a2.c("bg.png"));
        f.a(this);
        this.e(f);
    }

    private void k() {
        t[] tArray = new o[d.a.C.length];
        for (int i2 = 0; i2 < tArray.length; ++i2) {
            tArray[i2] = new o(d.a.C[i2]);
        }
        Image[] imageArray = new Image[d.a.C.length];
        for (int i3 = 0; i3 < imageArray.length; ++i3) {
            imageArray[i3] = d.a.S[d.a.C[i3].f];
        }
        h = new s(imageArray, tArray, main.b.c, main.b.d, null);
        e.a a2 = e.a.a("/c.pak");
        h.a(a2.c("bg.png"));
        h.a(this);
        this.e(h);
    }
}

