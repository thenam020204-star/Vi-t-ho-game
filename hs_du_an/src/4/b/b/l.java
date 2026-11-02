/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b.b;

import b.b.d;
import b.b.e;
import b.b.k;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public abstract class l {
    private Vector a = new Vector();
    protected e e = null;
    public int f = 0;
    public int g = 0;
    public int h = 0;
    public int i = 0;
    public String j = null;
    private byte b = (byte)-1;
    public byte k = (byte)-1;

    public abstract void a(int var1, int var2);

    public abstract void b();

    protected abstract void a();

    public l(String string) {
        this.j = string;
    }

    public void a(Graphics graphics, int n2, int n3) {
        d d2 = null;
        for (int i2 = 0; i2 < this.a.size(); ++i2) {
            d2 = (d)this.a.elementAt(i2);
            d2.a(graphics, n2, n3 + d2.c);
        }
        d.k.a(graphics, this.b, n2, n3 + this.i, 36);
        d.k.a(graphics, this.k, n2 + this.h, n3 + this.i, 40);
    }

    public final void a(d d2) {
        if (d2 != null) {
            this.a.addElement(d2);
        }
    }

    protected final void c(int n2) {
        k k2 = new k();
        new k().b = 0;
        k2.c = n2;
        k2.d = this.h;
        this.a(k2);
    }

    protected final void d(int n2) {
        if (this.e != null) {
            this.e.a(1);
        }
    }
}

