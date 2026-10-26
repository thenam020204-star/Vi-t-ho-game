/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package g;

import d.h;
import g.d;
import java.io.DataInputStream;
import java.io.InputStream;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.a;
import main.b;
import main.c;

public final class j
extends c
implements a {
    public j() {
        super((byte)5);
    }

    public final void a(Graphics graphics) {
        main.c.c(graphics);
        if (this.b) {
            main.c.a(graphics, this.c);
            return;
        }
        super.b(graphics);
    }

    public final void a() {
    }

    public final void a(int n2, int n3) {
        if (!this.f()) {
            super.c();
            b.j j2 = this.h();
            j2.a(n2, n3);
            return;
        }
        this.b = true;
        d.a.q = h.b.b("/t.dat");
        d.a.c();
        super.a(30);
        main.b.a.a();
        String[] stringArray = h.b.b("/tips.dat");
        if (stringArray != null) {
            d.a.ad = new String[stringArray.length][];
            for (n2 = 0; n2 < stringArray.length; ++n2) {
                d.a.ad[n2] = h.b.a(stringArray[n2], h.b.a, main.b.c);
            }
        }
        super.a(40);
        main.b.a.a();
        d.a.d();
        super.a(60);
        main.b.a.a();
        d.a.f();
        super.a(70);
        main.b.a.a();
        d.a.e();
        super.a(75);
        main.b.a.a();
        d.a.g();
        super.a(80);
        main.b.a.a();
        Object object = e.a.a("/f.pak");
        e.a.a((e.a)object, "/f.pak");
        super.a(85);
        main.b.a.a();
        object = e.a.a("/ic.pak");
        b.c.a = object.c("border.png");
        d.a.H = object.c("num1.png");
        d.a.I = d.a.H.getWidth() / 10;
        d.a.J = object.c("num2.png");
        d.a.K = d.a.J.getWidth() / 10;
        d.a.N = object.c("nm.png");
        d.a.O = object.c("nl.png");
        d.a.L = object.c("sign.png");
        d.a.M = d.a.L.getWidth() / 5;
        d.a.P = object.c("st.png");
        d.a.Q = object.c("slash.png");
        d.a.U = object.c("text.png");
        d.a.V = object.c("elements.png");
        d.a.R = object.c("money.png");
        d.a.S = new Image[4];
        for (n3 = 0; n3 < d.a.S.length; ++n3) {
            d.a.S[n3] = object.c("n" + n3 + ".png");
        }
        d.a.T = new Image[b.b.a.length];
        for (n3 = 0; n3 < d.a.T.length; ++n3) {
            d.a.T[n3] = object.c("i" + b.b.a[n3] + ".png");
        }
        d.a.W = object.c("numg.png");
        d.a.X = object.c("numr.png");
        d.a.Y = object.c("o.png");
        d.a.Z = object.c("r.png");
        d.a.aa = object.c("me.png");
        d.a.ab = h.b.a("/in.png");
        d.a.ac = object.c("ta.png");
        super.a(90);
        main.b.a.a();
        f.d[] dArray = new f.d[4];
        d.a.n = dArray;
        dArray[0] = d.a.a("r0", null);
        d.a.n[1] = d.a.a("r1", null);
        d.a.n[3] = d.a.a("r3", null);
        super.a(95);
        main.b.a.a();
        this.i();
        super.a(98);
        main.b.a.a();
        h.a = 58 + h.b.b + d.a.H.getHeight() + d.a.J.getHeight();
        super.a(100);
        main.b.a.a();
        object = new String[]{d.a.q[70] + d.a.q[57], d.a.q[70] + d.a.q[58]};
        object = b.c.a(object, null, (byte)0, (byte)4);
        object.a(this);
        this.e((b.j)object);
        d.a.p();
        this.b = false;
    }

    private void i() {
        int n2;
        DataInputStream dataInputStream;
        InputStream inputStream;
        block8: {
            block7: {
                inputStream = null;
                dataInputStream = null;
                inputStream = exception2.getClass().getResourceAsStream("/map.dat");
                if (inputStream != null) break block7;
                h.b.a(inputStream);
                h.b.a(null);
                return;
            }
            dataInputStream = new DataInputStream(inputStream);
            n2 = dataInputStream.readShort();
            if (n2 > 0) break block8;
            h.b.a(inputStream);
            h.b.a(dataInputStream);
            return;
        }
        try {
            d.a.i = new short[n2];
            d.a.j = new String[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                d.a.i[i2] = dataInputStream.readShort();
                d.a.j[i2] = dataInputStream.readUTF();
            }
        }
        catch (Exception exception) {
            try {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
            catch (Throwable throwable) {
                h.b.a(inputStream);
                h.b.a(dataInputStream);
                throw throwable;
            }
            h.b.a(inputStream);
            h.b.a(dataInputStream);
            return;
        }
        h.b.a(inputStream);
        h.b.a(dataInputStream);
        return;
    }

    public final void a(b.j object) {
        d.a.o[0] = ((String)(object = ((b.j)object).d())).equals(d.a.q[70] + d.a.q[57]);
        this.a(new d());
    }

    public final void d(b.j j2) {
        this.f(j2);
    }

    public final void b(b.j j2) {
    }

    public final void c(b.j j2) {
    }
}

