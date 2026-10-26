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
    protected e d = null;
    public int e = 0;
    public int f = 0;
    public int g = 0;
    public int h = 0;
    public String i = null;
    private byte b = (byte)-1;
    public byte j = (byte)-1;

    public abstract void a(int var1, int var2);

    public abstract void b();

    protected abstract void a();

    public l(String string) {
        this.i = string;
    }

    public void a(Graphics graphics, int n2, int n3) {
        d d2 = null;
        for (int i2 = 0; i2 < this.a.size(); ++i2) {
            d2 = (d)this.a.elementAt(i2);
            d2.a(graphics, n2, n3 + d2.c);
        }
        d.k.a(graphics, this.b, n2, n3 + this.h, 36);
        d.k.a(graphics, this.j, n2 + this.g, n3 + this.h, 40);
    }

    public final void a(d d2) {
        if (d2 != null) {
            this.a.addElement(d2);
        }
    }

    protected final void a(int n2) {
        k k2 = new k();
        new k().b = 0;
        k2.c = n2;
        k2.d = this.g;
        this.a(k2);
    }

    protected final void b(int n2) {
        if (this.d != null) {
            this.d.a(1);
        }
    }
}

