/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package a.a;

import a.a.j;
import javax.microedition.lcdui.Graphics;

public final class d
extends j {
    public d() {
        super(5);
        this.e = 4;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4 = n2;
        int n5 = n3;
        n3 = d2.d;
        n2 = n5;
        int n6 = n4;
        d d2 = graphics;
        d2.setColor(6373665);
        d2.drawLine(n6, n2, n6 + n3, n2);
        d2.setColor(5583390);
        d2.drawLine(n6, n2 + 1, n6 + n3, n2 + 1);
        d2.setColor(7692354);
        d2.drawLine(n6, n2 + 2, n6 + n3, n2 + 2);
        d2.setColor(5916203);
        d2.drawLine(n6, n2 + 3, n6 + n3, n2 + 3);
    }
}

