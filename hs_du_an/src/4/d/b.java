/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package d;

import a.f;
import b.b.c;
import b.e.a;
import d.h;
import d.k;
import javax.microedition.lcdui.Graphics;

public final class b
extends k {
    private int a = 0;
    private long b = 0L;
    private String c = f.o[68];

    public b() {
        super((byte)7);
    }

    public final void a(Graphics graphics) {
        d.k.c(graphics);
        graphics.setColor(0xFFFFFF);
        char c2 = '\u0000';
        int n2 = 0;
        int n3 = 0;
        for (int i2 = 0; i2 < this.a; ++i2) {
            c2 = this.c.charAt(i2);
            if (n2 + b.e.a.a.charWidth(c2) > main.a.d) {
                n3 += b.e.a.b;
                n2 = 0;
            }
            graphics.drawChar(c2, n2, n3, 20);
            n2 += b.e.a.a.charWidth(c2);
        }
    }

    public final void a() {
        if (f.w != null) {
            f.w.l();
            f.w.k();
            f.w = null;
        }
        f.j();
    }

    public final void a(int n2, int n3) {
        if (this.a < this.c.length() && this.b + 200L < System.currentTimeMillis()) {
            ++this.a;
            this.b = System.currentTimeMillis();
            return;
        }
        if (n2 == 53 || n2 == -6) {
            this.a(new h());
        }
    }

    public final void a(c c2) {
    }

    public final void b(c c2) {
    }
}

