/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b.b;

import b.b.d;
import javax.microedition.lcdui.Graphics;

public final class k
extends d {
    public k() {
        super(5);
        this.e = 4;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4 = n2;
        int n5 = n3;
        n3 = k2.d;
        n2 = n5;
        int n6 = n4;
        k k2 = graphics;
        k2.setColor(6373665);
        k2.drawLine(n6, n2, n6 + n3, n2);
        k2.setColor(5583390);
        k2.drawLine(n6, n2 + 1, n6 + n3, n2 + 1);
        k2.setColor(7692354);
        k2.drawLine(n6, n2 + 2, n6 + n3, n2 + 2);
        k2.setColor(5916203);
        k2.drawLine(n6, n2 + 3, n6 + n3, n2 + 3);
    }
}

