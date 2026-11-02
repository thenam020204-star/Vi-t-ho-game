/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package c;

import c.t;
import javax.microedition.lcdui.Graphics;

public abstract class u {
    protected int i = 0;
    protected int j = 0;
    protected t k = null;

    public abstract void e();

    public abstract void a(int var1, int var2);

    public abstract void a(Graphics var1, int var2, int var3);

    public void a_(int n2) {
        this.i = n2;
    }

    public void a(int n2) {
        this.j = n2;
    }

    public final void a(t t2) {
        this.k = t2;
    }
}

