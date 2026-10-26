/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b.b;

import b.b.l;
import b.b.n;
import javax.microedition.lcdui.Graphics;

public final class a
extends l {
    private n a = null;
    private String b = null;

    public a(String string, String string2) {
        super(string);
        this.b = string2;
    }

    public final void a(int n2, int n3) {
        switch (n3) {
            case 50: {
                this.a.a();
                return;
            }
            case 56: {
                this.a.b();
            }
        }
    }

    protected final void a() {
        this.a = new n();
        this.a.b = 0;
        this.a.c = 0;
        this.a.d = this.g;
        this.a.e = this.h;
        this.a.a(this.b);
        this.a(this.a);
    }

    public final void b() {
    }

    public final void a(Graphics graphics, int n2, int n3) {
        super.a(graphics, n2, n3);
    }
}

