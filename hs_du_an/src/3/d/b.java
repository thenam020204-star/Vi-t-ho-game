/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package d;

import a.f;
import b.b.c;
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
        graphics.drawString(this.c.substring(0, this.a), 0, 0, 20);
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

