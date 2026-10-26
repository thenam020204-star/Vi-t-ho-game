/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package g;

import b.j;
import d.a;
import g.d;
import javax.microedition.lcdui.Graphics;
import main.c;

public final class h
extends c {
    private int d = 0;
    private long e = 0L;
    private String f = d.a.q[68];

    public h() {
        super((byte)7);
    }

    public final void a(Graphics graphics) {
        main.c.c(graphics);
        graphics.setColor(0xFFFFFF);
        graphics.drawString(this.f.substring(0, this.d), 0, 0, 20);
    }

    public final void a() {
        d.a.j();
    }

    public final void a(int n2, int n3) {
        if (this.d < this.f.length() && this.e + 200L < System.currentTimeMillis()) {
            ++this.d;
            this.e = System.currentTimeMillis();
            return;
        }
        if (n2 == 53 || n2 == -6) {
            this.a(new d());
        }
    }

    public final void a(j j2) {
    }

    public final void d(j j2) {
    }

    public final void b(j j2) {
    }

    public final void c(j j2) {
    }
}

