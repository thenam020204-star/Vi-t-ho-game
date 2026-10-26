/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package d;

import b.b.c;
import d.a;
import d.k;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class l
extends k {
    private int a = -1;
    private Image b = null;
    private long c = 0L;
    private static byte[] d = new byte[]{2, 0, 1};

    public l() {
        super((byte)6);
    }

    public final void a(Graphics graphics) {
        graphics.setColor(0xFFFFFF);
        graphics.fillRect(0, 0, main.a.d, main.a.e);
        if (this.b != null) {
            graphics.drawImage(this.b, main.a.f, main.a.g, 3);
        }
    }

    public final void a() {
    }

    public final void a(int n2, int n3) {
        if (this.a < 0) {
            this.a = 0;
            this.b = b.e.a.b("/logo" + d[this.a] + ".png");
            b.e.a.a(100L);
            this.c = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - this.c > 2000L) {
            ++this.a;
            if (this.a >= d.length) {
                this.a(new a());
                return;
            }
            this.b = b.e.a.b("/logo" + d[this.a] + ".png");
            this.c = System.currentTimeMillis();
        }
    }

    public final void a(c c2) {
    }

    public final void b(c c2) {
    }
}

