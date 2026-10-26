/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package d;

import d.a;
import d.k;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.b;

public final class j
extends a {
    private int g = -1;
    private Image h = null;
    private long i = 0L;
    private static byte[] j = new byte[]{2, 0, 1};

    public j() {
        super((byte)6);
    }

    public final void a(Graphics graphics) {
        graphics.setColor(0xFFFFFF);
        graphics.fillRect(0, 0, main.b.d, main.b.e);
        if (this.h != null) {
            graphics.drawImage(this.h, main.b.f, main.b.g, 3);
        }
    }

    public final void a() {
    }

    public final void a(int n2, int n3) {
        if (this.g < 0) {
            this.g = 0;
            this.h = e.a.b.a("/logo" + j[this.g] + ".png");
            e.a.b.a(100L);
            this.i = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - this.i > 2000L) {
            ++this.g;
            if (this.g >= j.length) {
                this.a(new k());
                return;
            }
            this.h = e.a.b.a("/logo" + j[this.g] + ".png");
            this.i = System.currentTimeMillis();
        }
    }

    public final void a(c.k k2) {
    }

    public final void c(c.k k2) {
    }

    public final void b(c.k k2) {
    }
}

