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
    protected Vector h = new Vector();
    public Image i = null;

    public abstract void b(int var1, int var2);

    public e(int n2, int n3, int n4, int n5) {
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
        this.h.addElement(l2);
        l2.d = this;
    }

    public final void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        b.e.a.a(graphics, this.k, n2, n3, n4, n5);
    }

    public final void a(int n2) {
        this.c();
        this.b(n2);
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.h.size() == 0) {
            return;
        }
        l l2 = null;
        for (int i2 = 0; i2 < this.h.size(); ++i2) {
            l2 = (l)this.h.elementAt(i2);
            l2.a(graphics, n2 + l2.e, n3 + l2.f);
        }
    }
}

