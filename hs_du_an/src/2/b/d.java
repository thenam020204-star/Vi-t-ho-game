/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package b;

import b.b;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class d
extends b {
    private byte o = 1;
    private Image p = null;
    private int q = 0;
    private int r = 0;
    private int s = 0;
    private int t = 0;
    private int u = 0;

    private d(byte by, Image image) {
        super((byte)9);
        this.o = by;
        this.p = image;
        this.a = false;
    }

    public static d a(int n2, int n3, int n4, Image object) {
        object = new d(1, (Image)object);
        v0.u = Math.abs(n2);
        object.j = n3;
        object.k = n4;
        return object;
    }

    public static d a(Image object, int n2, int n3, int n4, int n5, int n6, int n7) {
        object = new d(0, (Image)object);
        v0.j = n2;
        object.k = n3;
        object.q = n4;
        object.r = 0;
        object.s = n6;
        object.t = n7;
        return object;
    }

    public static d a(Image image, int n2, int n3) {
        d d2 = new d(0, image);
        new d(0, image).j = n2;
        d2.k = n3;
        d2.q = 0;
        d2.r = 0;
        d2.s = image.getWidth();
        d2.t = image.getHeight();
        return d2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.a) {
            return;
        }
        switch (this.o) {
            case 1: {
                e.a.b.a(graphics, this.p, this.u, this.j, this.k);
                return;
            }
            case 0: {
                e.a.b.a(graphics, this.p, this.q, this.r, this.s, this.t, n2, n3, 20);
            }
        }
    }
}

