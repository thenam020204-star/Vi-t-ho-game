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
    private j h = null;
    public int a = 0;
    public int b = 0;
    public int c = 0;
    public int d = 0;
    public byte e = (byte)-1;
    public byte f = (byte)-1;
    public boolean g = true;

    public abstract void d();

    public abstract void a(int var1, int var2);

    public abstract void a(Graphics var1, int var2, int var3);

    public abstract void c();

    public abstract String a();

    public c(byte by) {
    }

    protected final void e() {
        if (this.h != null) {
            this.h.a(this);
        }
    }

    protected final void f() {
        if (this.h != null) {
            this.h.b(this);
        }
    }

    public final int[] g() {
        return new int[]{this.a, this.b, this.c, this.d};
    }

    public final void a(j j2) {
        this.h = j2;
    }
}

