/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package c;

import b.a;
import c.u;
import e.a.b;
import javax.microedition.lcdui.Graphics;

public final class l
extends u {
    private int a = 0;
    private String b = null;
    private String[] c = null;
    private int d = 0;
    private boolean e = false;

    public l(String[] stringArray) {
        if (stringArray != null) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i2 = 0; i2 < stringArray.length; ++i2) {
                stringBuffer.append(stringArray[i2]);
                if (i2 == stringArray.length - 1) continue;
                stringBuffer.append("\n");
            }
            this.b = stringBuffer.toString();
        }
    }

    public l(String string) {
        this.b = string;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.c == null) {
            return;
        }
        graphics.setColor(0xFFFFFF);
        int n4 = Math.min(this.c.length - this.a, this.d);
        for (int i2 = 0; i2 < n4; ++i2) {
            graphics.drawString(this.c[i2 + this.a], n2, n3 + i2 * e.a.b.b, 20);
        }
        if (this.e) {
            e.a.b.a(graphics, b.a.O, this.c.length, this.a, this.d, n2 + this.i - 6, n3 + 6, this.j);
        }
    }

    public final void a(int n2, int n3) {
        if (n2 == -7) {
            this.k.b();
            return;
        }
        if (this.c == null) {
            return;
        }
        switch (n3) {
            case 56: {
                if (this.a + this.d >= this.c.length) break;
                ++this.a;
                return;
            }
            case 50: {
                if (this.a <= 0) break;
                --this.a;
            }
        }
    }

    public final void e() {
        if (this.c == null) {
            return;
        }
        this.e = this.d < this.c.length;
    }

    public final void a(int n2) {
        super.a(n2);
        this.d = n2 / e.a.b.b;
    }

    public final void a_(int n2) {
        super.a_(n2);
        this.c = e.a.b.a(this.b, e.a.b.a, n2);
    }
}

