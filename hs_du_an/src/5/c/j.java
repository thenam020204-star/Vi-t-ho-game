/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package c;

import a.a.h;
import a.a.l;
import a.a.m;
import a.c.b;
import a.c.d;
import b.g;
import c.a;
import c.e;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class j
extends a
implements h {
    public j() {
        super((byte)5);
    }

    public final void a(Graphics graphics) {
        c.a.c(graphics);
        if (this.c) {
            a.c.b.a(graphics, "\u4e86\u89e3\u4f60\u7684\u8f69\u8f95\u5251\u6210\u5c31,\u627e\u653b\u7565,\u5bf9\u8f69\u8f95\u5251\u8bc4\u5206,\u8bc4\u8bba.\u8bf7\u8fdb\u5165\u4e3b\u83dc\u5355\"\u793e\u533a\".", main.a.d, 0, 0, 0xFFFFFF);
            c.a.a(graphics, this.d);
            return;
        }
        super.b(graphics);
    }

    public final void a() {
    }

    public final void a(int n2, int n3) {
        this.c = true;
        b.a.o = a.c.b.c("/t.dat");
        b.a.c();
        super.a(30);
        main.a.a.a();
        super.a(40);
        main.a.a.a();
        b.a.d();
        super.a(60);
        main.a.a.a();
        b.a.e();
        super.a(75);
        main.a.a.a();
        b.a.f();
        super.a(80);
        main.a.a.a();
        Object object = a.c.d.a("/ic.pak");
        l.h = ((d)object).c("border.png");
        b.a.D = ((d)object).c("num1.png");
        b.a.E = b.a.D.getWidth() / 10;
        b.a.F = ((d)object).c("num2.png");
        b.a.G = b.a.F.getWidth() / 10;
        b.a.J = ((d)object).c("nm.png");
        b.a.K = ((d)object).c("nl.png");
        b.a.H = ((d)object).c("sign.png");
        b.a.I = b.a.H.getWidth() / 5;
        b.a.L = ((d)object).c("st.png");
        b.a.M = ((d)object).c("slash.png");
        b.a.O = ((d)object).c("text.png");
        b.a.N = ((d)object).c("money.png");
        b.a.P = ((d)object).c("numg.png");
        b.a.Q = ((d)object).c("numr.png");
        b.a.R = ((d)object).c("o.png");
        b.a.S = ((d)object).c("r.png");
        b.a.T = ((d)object).c("me.png");
        b.a.U = a.c.b.b("/in.png");
        Object object2 = object;
        if (object == null) {
            object2 = a.c.d.a("/ic.pak");
        }
        if (object2 != null) {
            b.a.Z = ((d)object2).c("atk.png");
            b.a.aa = ((d)object2).c("def.png");
            b.a.ad = ((d)object2).c("sta.png");
            b.a.ae = ((d)object2).c("spd.png");
            b.a.ab = ((d)object2).c("str.png");
            b.a.ac = ((d)object2).c("int.png");
            b.a.ag = ((d)object2).c("phase.png");
            b.a.af = new Image[5];
            for (int i2 = 0; i2 < b.a.af.length; ++i2) {
                b.a.af[i2] = ((d)object2).c("ph" + i2 + ".png");
            }
        }
        b.a.W = ((d)object).c("ta.png");
        b.a.V = ((d)object).c("pointer.png");
        super.a(90);
        main.a.a.a();
        super.a(95);
        main.a.a.a();
        g.b = 58 + a.c.b.b + b.a.D.getHeight() + b.a.F.getHeight();
        super.a(100);
        main.a.a.a();
        this.c = false;
        object = new String[]{b.a.o[70] + b.a.o[57], b.a.o[70] + b.a.o[58]};
        object = l.a((String[])object, (byte)0, (byte)4, false);
        ((m)object).k();
        object2 = object = ((l)object).c();
        object = this;
        if (object2 != null) {
            if (((String)object2).equals(b.a.o[70] + b.a.o[57])) {
                b.a.l[0] = true;
            } else {
                b.a.m = 0;
                b.a.l[0] = false;
            }
        }
        ((a)object).a(new e());
        b.a.p();
    }

    public final void d(a.a.a a2) {
    }

    public final void c(a.a.a a2) {
        c.a.b(a2);
    }
}

