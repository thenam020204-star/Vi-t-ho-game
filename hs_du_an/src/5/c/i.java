/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package c;

import a.c.b;
import c.a;
import c.j;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class i
extends a {
    private int f = -1;
    private Image g = null;
    private long h = 0L;
    private static byte[] i = new byte[]{2, 0, 1};

    public i() {
        super((byte)6);
    }

    public final void a(Graphics graphics) {
        graphics.setColor(0xFFFFFF);
        graphics.fillRect(0, 0, main.a.d, main.a.e);
        if (this.g != null) {
            graphics.drawImage(this.g, main.a.f, main.a.g, 3);
        }
    }

    public final void a() {
    }

    public final void a(int n2, int n3) {
        if (this.f < 0) {
            this.f = 0;
            this.g = a.c.b.b("/logo" + i[this.f] + ".png");
            a.c.b.a(100L);
            this.h = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - this.h > 2000L) {
            ++this.f;
            if (this.f >= i.length) {
                this.a(new j());
                return;
            }
            this.g = a.c.b.b("/logo" + i[this.f] + ".png");
            this.h = System.currentTimeMillis();
        }
    }

    public final void d(a.a.a a2) {
    }

    public final void c(a.a.a a2) {
    }
}

