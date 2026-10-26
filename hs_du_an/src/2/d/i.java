/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package d;

import c.k;
import d.a;
import d.e;
import javax.microedition.lcdui.Graphics;

public final class i
extends a {
    private int g = 0;
    private long h = 0L;
    private String i = b.a.p[68];

    public i() {
        super((byte)7);
    }

    public final void a(Graphics graphics) {
        d.a.c(graphics);
        graphics.setColor(0xFFFFFF);
        graphics.drawString(this.i.substring(0, this.g), 0, 0, 20);
    }

    public final void a() {
        if (b.a.x != null) {
            b.a.x.n();
            b.a.x.m();
            b.a.x = null;
        }
        b.a.j();
    }

    public final void a(int n2, int n3) {
        if (this.g < this.i.length() && this.h + 200L < System.currentTimeMillis()) {
            ++this.g;
            this.h = System.currentTimeMillis();
            return;
        }
        if (n2 == 53 || n2 == -6) {
            this.a(new e());
        }
    }

    public final void a(k k2) {
    }

    public final void c(k k2) {
    }

    public final void b(k k2) {
    }
}

