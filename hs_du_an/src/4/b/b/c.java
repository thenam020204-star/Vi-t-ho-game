/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b.b;

import b.b.j;
import javax.microedition.lcdui.Graphics;

public abstract class c {
    private j a = null;
    public int c = 0;
    public int d = 0;
    public int e = 0;
    public int f = 0;
    public byte g = (byte)-1;
    public byte h = (byte)-1;
    public boolean i = true;

    public abstract void d();

    public abstract void a(int var1, int var2);

    public abstract void a(Graphics var1, int var2, int var3);

    public abstract void c();

    public abstract String a();

    public c(byte by) {
    }

    protected final void e() {
        if (this.a != null) {
            this.a.a(this);
        }
    }

    protected final void f() {
        if (this.a != null) {
            this.a.b(this);
        }
    }

    public final int[] g() {
        return new int[]{this.c, this.d, this.e, this.f};
    }

    public final void a(j j2) {
        this.a = j2;
    }
}

