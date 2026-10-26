/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package a.a;

import a.a.h;
import javax.microedition.lcdui.Graphics;

public abstract class a {
    private h h = null;
    public int a = 0;
    public int b = 0;
    public int c = 0;
    public int d = 0;
    public byte e = (byte)-1;
    public byte f = (byte)-1;
    public boolean g = true;

    public abstract void a();

    public abstract void a(int var1, int var2);

    public abstract void a(Graphics var1, int var2, int var3);

    public abstract void b();

    public abstract String c();

    public a(byte by) {
    }

    protected final void d() {
        if (this.h != null) {
            this.h.d(this);
        }
    }

    protected final void e() {
        if (this.h != null) {
            this.h.c(this);
        }
    }

    public final int[] f() {
        return new int[]{this.a, this.b, this.c, this.d};
    }

    public final void a(h h2) {
        this.h = h2;
    }
}

