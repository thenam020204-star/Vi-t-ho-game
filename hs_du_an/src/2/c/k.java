/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package c;

import javax.microedition.lcdui.Graphics;
import main.a;

public abstract class k {
    private a a = null;
    public int d = 0;
    public int e = 0;
    public int f = 0;
    public int g = 0;
    public int h = 0;
    public int i = 1;
    public byte j = (byte)-1;
    public byte k = (byte)-1;

    public abstract void a();

    public abstract void a(int var1, int var2);

    public abstract void a(Graphics var1, int var2, int var3);

    public abstract void e();

    public abstract void b();

    public abstract String d();

    public k(byte by) {
        this.d = by;
    }

    protected final void g() {
        if (this.a != null) {
            this.a.a(this);
        }
    }

    protected final void h() {
        if (this.a != null) {
            this.a.c(this);
        }
    }

    protected final void i() {
        if (this.a != null) {
            this.a.b(this);
        }
    }

    public final int[] j() {
        return new int[]{this.e, this.f, this.g, this.h};
    }

    public final void a(a a2) {
        this.a = a2;
    }
}

