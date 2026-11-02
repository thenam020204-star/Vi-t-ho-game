/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package b.b;

import b.b.l;
import b.b.m;
import b.e.a;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public abstract class e
extends m {
    protected Vector j = new Vector();
    public Image k = null;

    public abstract void b(int var1, int var2);

    public e(int n2, int n3, int n4, int n5) {
        super((byte)7);
        this.n = this.p = n2;
        this.c = this.p;
        this.d = this.q = n3;
        this.o = this.r = n4;
        this.e = this.r;
        this.f = this.s = n5;
    }

    public void a(int n2, int n3) {
        if (n2 == -7) {
            this.c();
            this.b(2);
            return;
        }
        this.b(n2, n3);
    }

    public void a(l l2) {
        if (l2 == null) {
            return;
        }
        this.j.addElement(l2);
        l2.e = this;
    }

    public final void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        a.a(graphics, this.m, n2, n3, n4, n5);
    }

    public final void a(int n2) {
        this.c();
        this.b(n2);
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.j.size() == 0) {
            return;
        }
        l l2 = null;
        for (int i2 = 0; i2 < this.j.size(); ++i2) {
            l2 = (l)this.j.elementAt(i2);
            l2.a(graphics, n2 + l2.f, n3 + l2.g);
        }
    }
}

