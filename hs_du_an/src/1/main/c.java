/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package main;

import b.j;
import java.util.Stack;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import main.a;
import main.b;

public abstract class c
implements a {
    protected c a = null;
    protected boolean b = false;
    protected byte c = 0;
    private Stack d = new Stack();
    private Vector e = new Vector();

    public abstract void a(Graphics var1);

    public abstract void a();

    public abstract void a(int var1, int var2);

    public c(byte by) {
    }

    public void b() {
        this.d.removeAllElements();
        this.d = null;
    }

    protected final void c() {
        int n2;
        j j2;
        if (!this.d.isEmpty()) {
            j2 = null;
            for (n2 = 0; n2 < this.d.size(); ++n2) {
                j2 = (j)this.d.elementAt(n2);
                j2.a();
            }
        }
        if (!this.e.isEmpty()) {
            j2 = null;
            for (n2 = 0; n2 < this.e.size(); ++n2) {
                j2 = (j)this.e.elementAt(n2);
                j2.a();
            }
        }
    }

    protected final void b(Graphics graphics) {
        j j2;
        Graphics graphics2 = graphics;
        c c2 = this;
        if (!c2.d.isEmpty()) {
            j2 = null;
            j j3 = null;
            int[] nArray = null;
            int n2 = c2.d.size();
            block0: for (int i2 = 0; i2 < n2; ++i2) {
                j2 = (j)c2.d.elementAt(i2);
                nArray = j2.p();
                for (int i3 = i2 + 1; i3 < c2.d.size(); ++i3) {
                    j3 = (j)c2.d.elementAt(i3);
                    if (h.b.a(nArray, j3.p())) continue block0;
                }
                j2.a(graphics2, j2.l(), j2.m());
                if (i2 != n2 - 1) continue;
                main.c.a(graphics2, j2.p, 0, main.b.d, 36);
                main.c.a(graphics2, j2.q, main.b.c, main.b.d, 40);
            }
        }
        graphics2 = graphics;
        c2 = this;
        if (!c2.e.isEmpty()) {
            j2 = null;
            int n3 = c2.e.size();
            for (int i4 = 0; i4 < n3; ++i4) {
                j2 = (d.b)c2.e.elementAt(i4);
                if (!((d.b)j2).k()) continue;
                ((d.b)j2).a(graphics2, j2.l(), j2.m());
            }
        }
    }

    private static void a(Graphics graphics, byte by, int n2, int n3, int n4) {
        switch (by) {
            case 1: 
            case 2: {
                graphics.drawImage(d.a.Z, n2, n3, n4);
                return;
            }
            case 0: {
                graphics.drawImage(d.a.Y, n2, n3, n4);
                return;
            }
            case 3: {
                graphics.drawImage(d.a.aa, n2, n3, n4);
                return;
            }
            case 5: {
                graphics.drawImage(d.a.ab, n2, n3, n4);
            }
        }
    }

    protected static void c(Graphics graphics) {
        graphics.setColor(0);
        graphics.fillRect(0, 0, main.b.c, main.b.d);
    }

    public final c d() {
        return this.a;
    }

    public final void a(c c2) {
        this.a = c2;
    }

    protected static void a(Graphics graphics, byte by) {
        if (d.a.ae != null) {
            graphics.setColor(0xFFFFFF);
            for (int i2 = 0; i2 < d.a.ae.length; ++i2) {
                graphics.drawString(d.a.ae[i2], 0, main.b.d - 5 - h.b.b * d.a.ae.length + i2 * h.b.b, 36);
            }
        }
        graphics.setColor(10802659);
        graphics.fillRect(0, main.b.d - 5, main.b.c * by / 100, 5);
        graphics.setColor(0xFFFFFF);
        graphics.drawString(by + "%", 0, main.b.d - 5, 36);
    }

    protected final void a(int n2) {
        this.c = (byte)n2;
    }

    protected final void b(int n2) {
        this.c = (byte)(this.c + 10);
        if (this.c > 100) {
            this.c = (byte)100;
        }
    }

    public final Stack e() {
        return this.d;
    }

    public final boolean f() {
        return this.d == null || this.d.isEmpty();
    }

    public final void e(j j2) {
        if (j2 == null) {
            return;
        }
        this.d.push(j2);
    }

    public final void f(j j2) {
        if (j2 == null) {
            return;
        }
        this.d.removeElement(j2);
    }

    public final void a(d.b b2) {
        if (b2 == null) {
            return;
        }
        this.e.addElement(b2);
    }

    public final void b(d.b b2) {
        if (b2 == null) {
            return;
        }
        this.e.removeElement(b2);
    }

    public final Vector g() {
        return this.e;
    }

    public final j h() {
        if (this.d == null || this.d.isEmpty()) {
            return null;
        }
        return (j)this.d.peek();
    }

    public abstract void d(j var1);

    public abstract void c(j var1);

    public abstract void b(j var1);

    public abstract void a(j var1);
}

