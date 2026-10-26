/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package d;

import d.b;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class e
extends b {
    private byte r = 1;
    private Image s = null;
    private int t = 0;
    private int u = 0;
    private int v = 0;
    private int w = 0;
    private int x = 0;

    private e(byte by, Image image) {
        super((byte)9);
        this.r = by;
        this.s = image;
        this.a(false);
    }

    public static e a(int n2, int n3, int n4, Image object) {
        object = new e(1, (Image)object);
        v0.x = Math.abs(n2);
        ((b)object).b(n3);
        ((b)object).c(n4);
        return object;
    }

    public static e a(Image object, int n2, int n3, int n4, int n5, int n6, int n7) {
        object = new e(0, (Image)object);
        ((b)object).b(n2);
        ((b)object).c(n3);
        ((e)object).t = n4;
        ((e)object).u = 0;
        ((e)object).v = n6;
        ((e)object).w = n7;
        return object;
    }

    public static e a(Image image, int n2, int n3) {
        e e2 = new e(0, image);
        e2.b(n2);
        e2.c(n3);
        e2.t = 0;
        e2.u = 0;
        e2.v = image.getWidth();
        e2.w = image.getHeight();
        return e2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!super.k()) {
            return;
        }
        switch (this.r) {
            case 1: {
                h.b.a(graphics, this.s, this.x, this.l(), this.m());
                return;
            }
            case 0: {
                h.b.a(graphics, this.s, this.t, this.u, this.v, this.w, n2, n3, 20);
            }
        }
    }

    public final void e() {
    }

    public final void b() {
    }

    public final void a() {
    }

    public final void a(int n2, int n3) {
    }

    public final String d() {
        return null;
    }
}

