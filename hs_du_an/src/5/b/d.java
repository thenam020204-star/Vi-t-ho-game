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
    private byte w = 1;
    private Image x = null;
    private int y = 0;
    private int z = 0;
    private int A = 0;
    private int B = 0;
    private int C = 0;

    private d(byte by, Image image) {
        super((byte)4);
        this.w = by;
        this.x = image;
        this.a = false;
    }

    public static d a(int n2, int n3, int n4, Image object) {
        object = new d(1, (Image)object);
        v0.C = Math.abs(n2);
        object.n = n3;
        object.o = n4;
        return object;
    }

    public static d a(Image object, int n2, int n3, int n4, int n5, int n6, int n7) {
        object = new d(0, (Image)object);
        v0.n = n2;
        object.o = n3;
        object.y = n4;
        object.z = 0;
        object.A = n6;
        object.B = n7;
        return object;
    }

    public static d a(Image image, int n2, int n3) {
        d d2 = new d(0, image);
        new d(0, image).n = n2;
        d2.o = n3;
        d2.y = 0;
        d2.z = 0;
        d2.A = image.getWidth();
        d2.B = image.getHeight();
        return d2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.a) {
            return;
        }
        switch (this.w) {
            case 1: {
                a.c.b.a(graphics, this.x, this.C, this.n, this.o);
                return;
            }
            case 0: {
                a.c.b.a(graphics, this.x, this.y, this.z, this.A, this.B, 0, n2, n3, 20);
            }
        }
    }
}

