/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package a;

import a.b;
import b.e.a;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class e
extends b {
    private byte a = 1;
    private Image b = null;
    private int c = 0;
    private int d = 0;
    private int e = 0;
    private int B = 0;
    private int C = 0;

    private e(byte by, Image image) {
        super((byte)4);
        this.a = by;
        this.b = image;
        this.f = false;
    }

    public static e a(int n2, int n3, int n4, Image object) {
        object = new e(1, (Image)object);
        v0.C = Math.abs(n2);
        object.s = n3;
        object.t = n4;
        return object;
    }

    public static e a(Image object, int n2, int n3, int n4, int n5, int n6, int n7) {
        object = new e(0, (Image)object);
        v0.s = n2;
        object.t = n3;
        object.c = n4;
        object.d = 0;
        object.e = n6;
        object.B = n7;
        return object;
    }

    public static e a(Image image, int n2, int n3) {
        e e2 = new e(0, image);
        new e(0, image).s = n2;
        e2.t = n3;
        e2.c = 0;
        e2.d = 0;
        e2.e = image.getWidth();
        e2.B = image.getHeight();
        return e2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.f) {
            return;
        }
        switch (this.a) {
            case 1: {
                b.e.a.a(graphics, this.b, this.C, this.s, this.t);
                return;
            }
            case 0: {
                b.e.a.a(graphics, this.b, this.c, this.d, this.e, this.B, 0, n2, n3, 20);
            }
        }
    }
}

