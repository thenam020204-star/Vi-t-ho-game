/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package d;

import b.g;
import c.j;
import d.a;
import d.e;
import e.b.c;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.b;

public final class k
extends a
implements main.a {
    public k() {
        super((byte)5);
    }

    public final void a(Graphics graphics) {
        d.a.c(graphics);
        if (this.c) {
            d.a.a(graphics, this.d);
            return;
        }
        super.b(graphics);
    }

    public final void a() {
    }

    public final void a(int n2, int n3) {
        this.c = true;
        b.a.p = e.a.b.b("/t.dat");
        b.a.c();
        super.a(30);
        main.b.a.a();
        Object object = e.a.b.b("/tips.dat");
        if (object != null) {
            b.a.ac = new String[((String[])object).length][];
            for (n3 = 0; n3 < ((String[])object).length; ++n3) {
                b.a.ac[n3] = e.a.b.a(object[n3], e.a.b.a, main.b.d);
            }
        }
        super.a(40);
        main.b.a.a();
        b.a.d();
        super.a(60);
        main.b.a.a();
        b.a.e();
        super.a(75);
        main.b.a.a();
        b.a.f();
        super.a(80);
        main.b.a.a();
        object = e.a.c.a("/ic.pak");
        c.b.a = ((e.a.c)object).b("border.png");
        b.a.G = ((e.a.c)object).b("num1.png");
        b.a.H = b.a.G.getWidth() / 10;
        b.a.I = ((e.a.c)object).b("num2.png");
        b.a.J = b.a.I.getWidth() / 10;
        b.a.M = ((e.a.c)object).b("nm.png");
        b.a.N = ((e.a.c)object).b("nl.png");
        b.a.K = ((e.a.c)object).b("sign.png");
        b.a.L = b.a.K.getWidth() / 5;
        b.a.O = ((e.a.c)object).b("st.png");
        b.a.P = ((e.a.c)object).b("slash.png");
        b.a.T = ((e.a.c)object).b("text.png");
        b.a.U = ((e.a.c)object).b("elements.png");
        b.a.Q = ((e.a.c)object).b("money.png");
        b.a.R = new Image[4];
        for (n3 = 0; n3 < b.a.R.length; ++n3) {
            b.a.R[n3] = ((e.a.c)object).b("n" + n3 + ".png");
        }
        b.a.S = new Image[c.a.a.length];
        for (n3 = 0; n3 < b.a.S.length; ++n3) {
            b.a.S[n3] = ((e.a.c)object).b("i" + c.a.a[n3] + ".png");
        }
        b.a.V = ((e.a.c)object).b("numg.png");
        b.a.W = ((e.a.c)object).b("numr.png");
        b.a.X = ((e.a.c)object).b("o.png");
        b.a.Y = ((e.a.c)object).b("r.png");
        b.a.Z = ((e.a.c)object).b("me.png");
        b.a.aa = e.a.b.a("/in.png");
        b.a.ab = ((e.a.c)object).b("ta.png");
        super.a(90);
        main.b.a.a();
        c[] cArray = new c[4];
        b.a.l = cArray;
        cArray[0] = e.b.c.b("r0");
        b.a.l[1] = e.b.c.b("r1");
        b.a.l[2] = e.b.c.b("r2");
        super.a(95);
        main.b.a.a();
        g.a = 58 + e.a.b.b + b.a.G.getHeight() + b.a.I.getHeight();
        super.a(100);
        main.b.a.a();
        this.c = false;
        Object object2 = new String[]{b.a.p[70] + b.a.p[57], b.a.p[70] + b.a.p[58]};
        object = c.b.a((String[])object2, (byte)0, (byte)4, false);
        ((j)object).f();
        object2 = object = ((c.b)object).d();
        object = this;
        if (object2 != null) {
            b.a.n[0] = ((String)object2).equals(b.a.p[70] + b.a.p[57]);
        }
        ((a)object).a(new e());
        b.a.p();
    }

    public final void a(c.k k2) {
    }

    public final void c(c.k k2) {
        d.a.e(k2);
    }

    public final void b(c.k k2) {
    }
}

