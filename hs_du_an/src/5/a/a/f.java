/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package a.a;

import a.a.g;
import a.a.i;
import javax.microedition.lcdui.Graphics;

public final class f
extends g {
    private i h = null;
    private String i = null;

    public f(String string, String string2) {
        super(string);
        this.i = string2;
    }

    public final void a(int n2, int n3) {
        switch (n3) {
            case 50: {
                this.h.a();
                return;
            }
            case 56: {
                this.h.b();
            }
        }
    }

    protected final void a() {
        this.h = new i();
        this.h.b = 0;
        this.h.c = 0;
        this.h.d = this.d;
        this.h.e = this.e;
        this.h.a(this.i);
        this.a(this.h);
    }

    public final void b() {
    }

    public final void a(Graphics graphics, int n2, int n3) {
        super.a(graphics, n2, n3);
    }
}

