/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package d;

import c.k;
import java.util.Stack;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import main.b;

public abstract class a
implements main.a {
    public byte a = (byte)-1;
    public a b = null;
    protected boolean c = false;
    protected byte d = 0;
    private static Stack g = new Stack();
    private Vector h = null;
    protected byte e = (byte)-1;
    protected byte f = (byte)-1;

    public abstract void a(Graphics var1);

    public abstract void a();

    public abstract void a(int var1, int var2);

    public a(byte by) {
        this.a = by;
        this.h = new Vector();
    }

    public void b() {
        g.removeAllElements();
    }

    protected static void c() {
        if (!g.isEmpty()) {
            k k2 = null;
            for (int i2 = 0; i2 < g.size(); ++i2) {
                k2 = (k)g.elementAt(i2);
                k2.a();
            }
        }
    }

    protected final void b(Graphics graphics) {
        Object object;
        Graphics graphics2 = graphics;
        a a2 = this;
        if (!g.isEmpty()) {
            object = null;
            k k2 = null;
            int[] nArray = null;
            int n2 = g.size();
            block0: for (int i2 = 0; i2 < n2; ++i2) {
                object = (k)g.elementAt(i2);
                nArray = ((k)object).j();
                for (int i3 = i2 + 1; i3 < g.size(); ++i3) {
                    k2 = (k)g.elementAt(i3);
                    if (e.a.b.a(nArray, k2.j())) continue block0;
                }
                ((k)object).a(graphics2, ((k)object).e, ((k)object).f);
                if (i2 != n2 - 1) continue;
                d.a.a(graphics2, ((k)object).j, 0, main.b.e, 36);
                d.a.a(graphics2, ((k)object).k, main.b.d, main.b.e, 40);
            }
        }
        graphics2 = graphics;
        a2 = this;
        if (!a2.h.isEmpty()) {
            object = null;
            int n3 = a2.h.size();
            for (int i4 = 0; i4 < n3; ++i4) {
                object = (b.b)a2.h.elementAt(i4);
                if (!((b.b)object).a) continue;
                ((b.b)object).a(graphics2, ((b.b)object).j, ((b.b)object).k);
            }
        }
    }

    private static void a(Graphics graphics, byte by, int n2, int n3, int n4) {
        switch (by) {
            case 1: 
            case 2: {
                graphics.drawImage(b.a.Y, n2, n3, n4);
                return;
            }
            case 0: {
                graphics.drawImage(b.a.X, n2, n3, n4);
                return;
            }
            case 3: {
                graphics.drawImage(b.a.Z, n2, n3, n4);
                return;
            }
            case 5: {
                graphics.drawImage(b.a.aa, n2, n3, n4);
            }
        }
    }

    protected static void c(Graphics graphics) {
        graphics.setColor(0);
        graphics.fillRect(0, 0, main.b.d, main.b.e);
    }

    public final a d() {
        return this.b;
    }

    public final void a(a a2) {
        this.b = a2;
    }

    protected static void a(Graphics graphics, byte by) {
        if (b.a.ad != null) {
            graphics.setColor(0xFFFFFF);
            for (int i2 = 0; i2 < b.a.ad.length; ++i2) {
                graphics.drawString(b.a.ad[i2], 0, main.b.e - 5 - e.a.b.b * b.a.ad.length + i2 * e.a.b.b, 36);
            }
        }
        graphics.setColor(10802659);
        graphics.fillRect(0, main.b.e - 5, main.b.d * by / 100, 5);
        graphics.setColor(0xFFFFFF);
        graphics.drawString(by + "%", 0, main.b.e - 5, 36);
    }

    protected final void a(int n2) {
        this.d = (byte)n2;
    }

    protected final void b(int n2) {
        this.d = (byte)(this.d + 10);
        if (this.d > 100) {
            this.d = (byte)100;
        }
    }

    public static Stack e() {
        return g;
    }

    public static boolean f() {
        return g == null || g.isEmpty();
    }

    public static void d(k k2) {
        if (k2 == null) {
            return;
        }
        g.push(k2);
    }

    public static void e(k k2) {
        if (k2 == null) {
            return;
        }
        g.removeElement(k2);
    }

    public final void a(b.b b2) {
        if (b2 == null) {
            return;
        }
        this.h.addElement(b2);
    }

    public final void b(b.b b2) {
        if (b2 == null) {
            return;
        }
        this.h.removeElement(b2);
    }

    public final Vector g() {
        return this.h;
    }

    public static k h() {
        if (g == null || g.isEmpty()) {
            return null;
        }
        return (k)g.peek();
    }

    public abstract void c(k var1);

    public abstract void b(k var1);

    public abstract void a(k var1);
}

