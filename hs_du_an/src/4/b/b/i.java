/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b.b;

import b.b.d;
import b.e.a;
import b.e.c;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class i
extends d {
    private Vector a = new Vector();
    private Vector f = new Vector();
    private int g = 0;
    private int h = 0;

    public i() {
        super(1);
    }

    public final void a() {
        int n2 = this.a.size();
        if (n2 > 0) {
            this.h = (this.h - 1 + n2) % n2;
            this.d();
        }
    }

    public final void b() {
        int n2 = this.a.size();
        if (n2 > 0) {
            this.h = (this.h + 1) % n2;
            this.d();
        }
    }

    private void d() {
        int n2 = this.e / b.e.a.b;
        if (this.h < this.g) {
            this.g = this.h;
            return;
        }
        if (this.h >= this.g + n2 - 1) {
            this.g = this.h - n2 + 1;
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.a.size() == 0 || this.a.size() != this.f.size()) {
            return;
        }
        int n4 = this.e / b.e.a.b;
        if (n4 == 0) {
            return;
        }
        boolean bl = n4 < this.a.size();
        int n5 = 0;
        String string = null;
        int n6 = 0;
        for (int i2 = 0; i2 < n4; ++i2) {
            n5 = n3 + i2 * b.e.a.b;
            if (i2 + this.g == this.h) {
                b.e.c.a(graphics, 5848857, 15966997, n2, n5, this.d - 1, b.e.a.b / 2);
                b.e.c.a(graphics, 15966997, 5848857, n2, n5 + b.e.a.b / 2, this.d - 1, b.e.a.b / 2);
                graphics.setColor(16120515);
            } else {
                graphics.setColor(11971479);
            }
            n6 = i2 + this.g;
            if (n6 >= 0 && n6 < this.a.size()) {
                string = (String)this.a.elementAt(n6);
                graphics.drawString(string, n2, n5, 20);
            }
            if (n6 < 0 || n6 >= this.f.size()) continue;
            string = (String)this.f.elementAt(n6);
            graphics.drawString(string, n2 + this.d - (bl ? 6 : 0), n5, 24);
        }
        if (bl) {
            b.e.a.a(graphics, this.a.size(), this.g, n4, n2 + this.d - 6, n3, this.e);
        }
    }

    public final void a(String string, String string2) {
        if (string == null) {
            return;
        }
        this.a.addElement(string);
        this.f.addElement(string2 == null ? "" : string2);
    }

    public final void a(int n2) {
        if (n2 < 0 || n2 >= this.a.size() || n2 >= this.f.size()) {
            return;
        }
        this.a.removeElementAt(n2);
        this.f.removeElementAt(n2);
        if (this.h >= this.a.size()) {
            --this.h;
            n2 = this.e / b.e.a.b;
            if (this.h - n2 >= 0) {
                this.g = this.h - n2 + 1;
            }
        }
    }

    public final void a(int n2, String string, String string2) {
        if (n2 < 0 || n2 >= this.a.size() || n2 >= this.f.size()) {
            return;
        }
        this.a.setElementAt(string, n2);
        this.f.setElementAt(string2, n2);
    }

    public final int c() {
        return this.h;
    }
}

