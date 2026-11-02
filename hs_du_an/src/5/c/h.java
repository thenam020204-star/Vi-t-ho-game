/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package c;

import a.c.b;
import c.a;
import c.e;
import javax.microedition.lcdui.Graphics;

public final class h
extends a {
    private int f = 0;
    private long g = 0L;
    private String h = b.a.o[68];

    public h() {
        super((byte)7);
    }

    public final void a(Graphics graphics) {
        c.a.c(graphics);
        graphics.setColor(0xFFFFFF);
        char c2 = '\u0000';
        int n2 = 0;
        int n3 = 0;
        for (int i2 = 0; i2 < this.f; ++i2) {
            c2 = this.h.charAt(i2);
            if (n2 + a.c.b.a.charWidth(c2) > main.a.d) {
                n3 += a.c.b.b;
                n2 = 0;
            }
            graphics.drawChar(c2, n2, n3, 20);
            n2 += a.c.b.a.charWidth(c2);
        }
    }

    public final void a() {
        if (b.a.w != null) {
            b.a.w.k();
            b.a.w.j();
            b.a.w = null;
        }
        b.a.j();
    }

    public final void a(int n2, int n3) {
        if (this.f < this.h.length() && this.g + 200L < System.currentTimeMillis()) {
            ++this.f;
            this.g = System.currentTimeMillis();
            return;
        }
        if (n2 == 53 || n2 == -6) {
            this.a(new e());
        }
    }

    public final void d(a.a.a a2) {
    }

    public final void c(a.a.a a2) {
    }
}

