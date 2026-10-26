/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b;

import javax.microedition.lcdui.Graphics;
import main.a;
import main.d;

public abstract class j {
    protected a i = null;
    protected d j = null;
    public int k = 0;
    protected int l = 0;
    protected int m = 0;
    public int n = 0;
    public int o = 0;
    public byte p = (byte)-1;
    public byte q = (byte)-1;

    public abstract void a();

    public abstract void a(int var1, int var2);

    public abstract void a(Graphics var1, int var2, int var3);

    public abstract void e();

    public abstract void b();

    public abstract String d();

    public j(byte by) {
        this.k = by;
    }

    public void b(int n2) {
        this.l = n2;
    }

    public void c(int n2) {
        this.m = n2;
    }

    public final int l() {
        return this.l;
    }

    public final int m() {
        return this.m;
    }

    protected final void n() {
        if (this.i != null) {
            this.i.a(this);
        }
    }

    protected final void d(int n2) {
        if (this.i != null) {
            this.i.d(this);
        }
        if (this.j != null) {
            this.j.a(n2);
        }
    }

    protected final void o() {
        if (this.i != null) {
            this.i.c(this);
        }
    }

    public final int[] p() {
        return new int[]{this.l, this.m, this.n, this.o};
    }

    public final void a(a a2) {
        this.i = a2;
    }
}

