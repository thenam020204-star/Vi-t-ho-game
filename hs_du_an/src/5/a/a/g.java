/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package a.a;

import a.a.d;
import a.a.j;
import a.a.n;
import c.a;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public abstract class g {
    private Vector h = new Vector();
    protected n a = null;
    public int b = 0;
    public int c = 0;
    public int d = 0;
    public int e = 0;
    public String f = null;
    private byte i = (byte)-1;
    public byte g = (byte)-1;

    public abstract void a(int var1, int var2);

    public abstract void b();

    protected abstract void a();

    public g(String string) {
        this.f = string;
    }

    public void a(Graphics graphics, int n2, int n3) {
        j j2 = null;
        for (int i2 = 0; i2 < this.h.size(); ++i2) {
            j2 = (j)this.h.elementAt(i2);
            j2.a(graphics, n2, n3 + j2.c);
        }
        c.a.a(graphics, this.i, n2, n3 + this.e, 36);
        c.a.a(graphics, this.g, n2 + this.d, n3 + this.e, 40);
    }

    public final void a(j j2) {
        if (j2 != null) {
            this.h.addElement(j2);
        }
    }

    protected final void a(int n2) {
        d d2 = new d();
        new d().b = 0;
        d2.c = n2;
        d2.d = this.d;
        this.a(d2);
    }

    protected final void b(int n2) {
        if (this.a != null) {
            this.a.b(1);
        }
    }
}

