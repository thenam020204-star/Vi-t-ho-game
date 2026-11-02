/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package a.a;

import a.a.j;
import a.c.b;
import javax.microedition.lcdui.Graphics;

public final class i
extends j {
    private int a = 0;
    private String[] f = null;
    private int g = 0xFFFFFF;

    public i() {
        super(1);
    }

    public final void a(String string) {
        if (string == null) {
            return;
        }
        this.f = a.c.b.a(string, a.c.b.a, this.d);
    }

    public final void a() {
        if (this.a > 0) {
            --this.a;
        }
    }

    public final void b() {
        if (this.f == null) {
            return;
        }
        int n2 = this.e / a.c.b.b;
        if (this.a + n2 < this.f.length) {
            ++this.a;
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.f == null) {
            return;
        }
        int n4 = this.e / a.c.b.b;
        if (n4 == 0) {
            return;
        }
        boolean bl = n4 < this.f.length;
        int n5 = 0;
        int n6 = 0;
        for (int i2 = 0; i2 < n4 && (n6 = i2 + this.a) < this.f.length; ++i2) {
            n5 = n3 + i2 * a.c.b.b;
            graphics.setColor(this.g);
            graphics.drawString(this.f[n6], n2, n5, 20);
        }
        if (bl) {
            a.c.b.a(graphics, this.f.length, this.a, n4, n2 + this.d - 6, n3, this.e);
        }
    }
}

