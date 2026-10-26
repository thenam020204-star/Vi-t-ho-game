/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package a.a;

import a.a.j;
import a.c.a;
import a.c.b;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class c
extends j {
    private Vector a = new Vector();
    private Vector f = new Vector();
    private Vector g = new Vector();
    private int h = 0;
    private int i = 0;

    public c() {
        super(1);
    }

    public final void a() {
        int n2 = this.a.size();
        if (n2 > 0) {
            this.i = (this.i - 1 + n2) % n2;
            this.d();
        }
    }

    public final void b() {
        int n2 = this.a.size();
        if (n2 > 0) {
            this.i = (this.i + 1) % n2;
            this.d();
        }
    }

    private void d() {
        int n2 = this.e / a.c.b.b;
        if (this.i < this.h) {
            this.h = this.i;
            return;
        }
        if (this.i >= this.h + n2 - 1) {
            this.h = this.i - n2 + 1;
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.a.size() == 0 || this.a.size() != this.f.size()) {
            return;
        }
        int n4 = this.e / a.c.b.b;
        if (n4 == 0) {
            return;
        }
        boolean bl = n4 < this.a.size();
        int n5 = 0;
        int n6 = 0;
        String string = null;
        int n7 = 0;
        string = null;
        string = null;
        for (int i2 = 0; i2 < n4; ++i2) {
            n5 = n2;
            n6 = n3 + i2 * a.c.b.b;
            if (i2 + this.h == this.i) {
                a.c.a.a(graphics, 5848857, 15966997, n2, n6, this.d - 1, a.c.b.b / 2);
                a.c.a.a(graphics, 15966997, 5848857, n2, n6 + a.c.b.b / 2, this.d - 1, a.c.b.b / 2);
                graphics.setColor(16120515);
            } else {
                graphics.setColor(11971479);
            }
            n7 = i2 + this.h;
            if (n7 >= 0 && n7 < this.g.size()) {
                Object e2 = this.g.elementAt(n7);
                string = (String)e2;
                if (e2 != null) {
                    string = (Image)string;
                    graphics.drawImage((Image)string, n5, n6, 20);
                    n5 += string.getWidth();
                }
            }
            if (n7 >= 0 && n7 < this.a.size()) {
                string = (String)this.a.elementAt(n7);
                graphics.drawString(string, n5, n6, 20);
            }
            if (n7 < 0 || n7 >= this.f.size()) continue;
            string = (String)this.f.elementAt(n7);
            graphics.drawString(string, n2 + this.d - (bl ? 6 : 0), n6, 24);
        }
        if (bl) {
            a.c.b.a(graphics, this.a.size(), this.h, n4, n2 + this.d - 6, n3, this.e);
        }
    }

    public final void a(String string, String string2) {
        c c2 = c3;
        c c3 = null;
        c3 = c2;
        if (string != null) {
            c3.a.addElement(string);
            c3.f.addElement(string2 == null ? "" : string2);
            c3.g.addElement(null);
        }
    }

    public final void a(int n2) {
        if (n2 < 0 || n2 >= this.a.size() || n2 >= this.f.size()) {
            return;
        }
        this.a.removeElementAt(n2);
        this.f.removeElementAt(n2);
        this.g.removeElementAt(n2);
        if (this.i >= this.a.size()) {
            --this.i;
            n2 = this.e / a.c.b.b;
            if (this.i - n2 >= 0) {
                this.h = this.i - n2 + 1;
            }
        }
    }

    public final void a(int n2, String string, String string2) {
        c c2 = c3;
        c c3 = null;
        c3 = c2;
        if (n2 < 0 || n2 >= c3.a.size() || n2 >= c3.f.size()) {
            return;
        }
        c3.a.setElementAt(string, n2);
        c3.f.setElementAt(string2, n2);
        c3.g.setElementAt(null, n2);
    }

    public final int c() {
        return this.i;
    }
}

