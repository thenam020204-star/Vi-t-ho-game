/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package g;

import g.j;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.b;
import main.c;

public final class i
extends c {
    private int d = 0;
    private Image e = null;
    private long f = 0L;
    private static byte[] g = new byte[]{2, 0, 1};

    public i() {
        super((byte)6);
    }

    public final void a(Graphics graphics) {
        graphics.setColor(0xFFFFFF);
        graphics.fillRect(0, 0, main.b.c, main.b.d);
        if (this.e != null) {
            graphics.drawImage(this.e, main.b.e, main.b.f, 3);
        }
    }

    public final void a() {
        this.e = h.b.a("/logo" + g[this.d] + ".png");
        this.f = System.currentTimeMillis();
    }

    public final void a(int n2, int n3) {
        if (System.currentTimeMillis() - this.f > 2000L) {
            ++this.d;
            if (this.d >= g.length) {
                this.a(new j());
                return;
            }
            this.e = h.b.a("/logo" + g[this.d] + ".png");
            this.f = System.currentTimeMillis();
        }
    }

    public final void a(b.j j2) {
    }

    public final void d(b.j j2) {
    }

    public final void b(b.j j2) {
    }

    public final void c(b.j j2) {
    }
}

