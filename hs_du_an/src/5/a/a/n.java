/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package a.a;

import a.a.g;
import a.a.m;
import a.c.b;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public abstract class n
extends m {
    protected Vector r = new Vector();
    public Image s = null;

    public abstract void b(int var1, int var2);

    public n(int n2, int n3, int n4, int n5) {
        super((byte)7);
        this.l = this.n = n2;
        this.a = this.n;
        this.b = this.o = n3;
        this.m = this.p = n4;
        this.c = this.p;
        this.d = this.q = n5;
    }

    public void a(int n2, int n3) {
        if (n2 == -7) {
            this.b();
            this.a(2);
            return;
        }
        this.b(n2, n3);
    }

    public void a(g g2) {
        if (g2 == null) {
            return;
        }
        this.r.addElement(g2);
        g2.a = this;
    }

    public final void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        a.c.b.a(graphics, this.k, n2, n3, n4, n5);
    }

    public final void b(int n2) {
        this.b();
        this.a(n2);
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.r.size() == 0) {
            return;
        }
        g g2 = null;
        for (int i2 = 0; i2 < this.r.size(); ++i2) {
            g2 = (g)this.r.elementAt(i2);
            g2.a(graphics, n2 + g2.b, n3 + g2.c);
        }
    }
}

