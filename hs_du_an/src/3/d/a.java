/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package d;

import a.f;
import b.b.c;
import b.b.g;
import b.b.j;
import b.b.m;
import b.e.d;
import d.h;
import d.k;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class a
extends k
implements j {
    public a() {
        super((byte)5);
    }

    public final void a(Graphics graphics) {
        d.k.c(graphics);
        if (this.i) {
            b.e.a.a(graphics, "\u4e86\u89e3\u4f60\u7684\u8f69\u8f95\u5251\u6210\u5c31,\u627e\u653b\u7565,\u5bf9\u8f69\u8f95\u5251\u8bc4\u5206,\u8bc4\u8bba.\u8bf7\u8fdb\u5165\u4e3b\u83dc\u5355\"\u793e\u533a\".", main.a.d, 0, 0, 0xFFFFFF);
            d.k.a(graphics, this.j);
            return;
        }
        super.b(graphics);
    }

    public final void a() {
    }

    public final void a(int n2, int n3) {
        this.i = true;
        f.o = b.e.a.c("/t.dat");
        f.c();
        super.a(30);
        main.a.a.a();
        Object object = b.e.a.c("/tips.dat");
        if (object != null) {
            f.X = new String[((String[])object).length][];
            for (n3 = 0; n3 < ((String[])object).length; ++n3) {
                f.X[n3] = b.e.a.a(object[n3], b.e.a.a, main.a.d);
            }
        }
        if ((object = b.e.a.c("/lk_tips.dat")) != null) {
            n3 = ((String[])object).length + (f.X == null ? 0 : f.X.length);
            String[][] stringArrayArray = new String[n3][];
            if (f.X != null) {
                System.arraycopy(f.X, 0, stringArrayArray, 0, f.X.length);
            }
            int n4 = f.X == null ? 0 : f.X.length;
            for (int i2 = 0; i2 < ((String[])object).length; ++i2) {
                stringArrayArray[n4 + i2] = b.e.a.a(object[i2], b.e.a.a, main.a.d);
            }
            f.X = stringArrayArray;
        }
        super.a(40);
        main.a.a.a();
        f.d();
        super.a(60);
        main.a.a.a();
        f.e();
        super.a(75);
        main.a.a.a();
        f.f();
        super.a(80);
        main.a.a.a();
        object = d.a("/ic.pak");
        b.b.g.h = ((d)object).c("border.png");
        f.D = ((d)object).c("num1.png");
        f.E = f.D.getWidth() / 10;
        f.F = ((d)object).c("num2.png");
        f.G = f.F.getWidth() / 10;
        f.J = ((d)object).c("nm.png");
        f.K = ((d)object).c("nl.png");
        f.H = ((d)object).c("sign.png");
        f.I = f.H.getWidth() / 5;
        f.L = ((d)object).c("st.png");
        f.M = ((d)object).c("slash.png");
        f.O = ((d)object).c("text.png");
        f.N = ((d)object).c("money.png");
        f.P = ((d)object).c("numg.png");
        f.Q = ((d)object).c("numr.png");
        f.R = ((d)object).c("o.png");
        f.S = ((d)object).c("r.png");
        f.T = ((d)object).c("me.png");
        f.U = b.e.a.b("/in.png");
        f.W = ((d)object).c("ta.png");
        f.Z = ((d)object).c("atk.png");
        f.aa = ((d)object).c("def.png");
        f.ad = ((d)object).c("sta.png");
        f.ae = ((d)object).c("spd.png");
        f.ab = ((d)object).c("str.png");
        f.ac = ((d)object).c("int.png");
        f.ag = ((d)object).c("phase.png");
        f.af = new Image[5];
        for (int i3 = 0; i3 < f.af.length; ++i3) {
            f.af[i3] = ((d)object).c("ph" + i3 + ".png");
        }
        f.V = ((d)object).c("pointer.png");
        super.a(90);
        main.a.a.a();
        b.a.c[] cArray = new b.a.c[4];
        f.l = cArray;
        cArray[0] = b.a.c.b("r0");
        f.l[1] = b.a.c.b("r1");
        f.l[2] = b.a.c.b("r2");
        super.a(95);
        main.a.a.a();
        a.c.a = 58 + b.e.a.b + f.D.getHeight() + f.F.getHeight();
        super.a(100);
        main.a.a.a();
        this.i = false;
        Object object2 = new String[]{f.o[70] + f.o[57], f.o[70] + f.o[58]};
        object2 = b.b.g.a((String[])object2, (byte)0, (byte)4, false);
        ((m)object2).k();
        String string = ((g)object2).a();
        object2 = string;
        object = this;
        if (object2 != null) {
            f.m[0] = ((String)object2).equals(f.o[70] + f.o[57]);
        }
        ((k)object).a(new h());
        f.p();
    }

    public final void a(c c2) {
    }

    public final void b(c c2) {
        d.k.d(c2);
    }
}

