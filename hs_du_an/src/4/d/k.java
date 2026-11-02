/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package d;

import a.b;
import a.f;
import b.b.c;
import b.b.j;
import b.e.a;
import java.util.Stack;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public abstract class k
implements j {
    public byte h = (byte)-1;
    public k i = null;
    protected boolean j = false;
    protected byte k = 0;
    private static Stack a = new Stack();
    protected static Vector l = null;

    public abstract void a(Graphics var1);

    public abstract void a();

    public abstract void a(int var1, int var2);

    public k(byte by) {
        this.h = by;
        l = new Vector();
    }

    public void b() {
        a.removeAllElements();
        l.removeAllElements();
        f.X = null;
    }

    protected static void d() {
        if (!a.isEmpty()) {
            c c2 = null;
            for (int i2 = 0; i2 < a.size(); ++i2) {
                c2 = (c)a.elementAt(i2);
                c2.d();
            }
        }
    }

    protected final void b(Graphics graphics) {
        Object object;
        k k2 = graphics;
        if (!a.isEmpty()) {
            object = null;
            c c2 = null;
            int[] nArray = null;
            int n2 = a.size();
            block0: for (int i2 = 0; i2 < n2; ++i2) {
                object = (c)a.elementAt(i2);
                if (!((c)object).i) continue;
                nArray = ((c)object).g();
                for (int i3 = i2 + 1; i3 < a.size(); ++i3) {
                    c2 = (c)a.elementAt(i3);
                    if (b.e.a.a(nArray, c2.g())) continue block0;
                }
                ((c)object).a((Graphics)k2, ((c)object).c, ((c)object).d);
                if (i2 != n2 - 1) continue;
                d.k.a((Graphics)k2, ((c)object).g, 0, main.a.e, 36);
                d.k.a((Graphics)k2, ((c)object).h, main.a.d, main.a.e, 40);
            }
        }
        k2 = graphics;
        if (!l.isEmpty()) {
            object = null;
            int n3 = l.size();
            for (int i4 = 0; i4 < n3; ++i4) {
                object = (b)l.elementAt(i4);
                if (!((b)object).f) continue;
                ((b)object).a((Graphics)k2, ((b)object).s, ((b)object).t);
            }
        }
    }

    public static void a(Graphics graphics, byte by, int n2, int n3, int n4) {
        switch (by) {
            case 1: 
            case 2: {
                graphics.drawImage(f.S, n2, n3, n4);
                return;
            }
            case 0: {
                graphics.drawImage(f.R, n2, n3, n4);
                return;
            }
            case 3: {
                graphics.drawImage(f.T, n2, n3, n4);
                return;
            }
            case 5: {
                graphics.drawImage(f.U, n2, n3, n4);
            }
        }
    }

    protected static void c(Graphics graphics) {
        graphics.setColor(0);
        graphics.fillRect(0, 0, main.a.d, main.a.e);
    }

    public final k e() {
        return this.i;
    }

    public final void a(k k2) {
        this.i = k2;
    }

    protected static void a(Graphics graphics, byte by) {
        int n2 = main.a.e - 5 - b.e.a.b * f.Y - b.e.a.b;
        b.e.a.a(graphics, f.X, main.a.d, 0, n2, 0xFFFFFF);
        graphics.setColor(10802659);
        graphics.fillRect(0, main.a.e - 5, main.a.d * by / 100, 5);
        graphics.setColor(0xFFFFFF);
        graphics.drawString(by + "%", 0, main.a.e - 5, 36);
    }

    protected final void a(int n2) {
        this.k = (byte)n2;
    }

    protected final void b(int n2) {
        this.k = (byte)(this.k + 10);
        if (this.k > 100) {
            this.k = (byte)100;
        }
    }

    public static Stack f() {
        return a;
    }

    public static boolean g() {
        return a == null || a.isEmpty();
    }

    public static void c(c c2) {
        if (c2 == null) {
            return;
        }
        a.push(c2);
    }

    public static void d(c c2) {
        if (c2 == null) {
            return;
        }
        a.removeElement(c2);
    }

    public static void a(b b2) {
        if (b2 == null) {
            return;
        }
        l.addElement(b2);
    }

    public static void b(b b2) {
        if (b2 == null) {
            return;
        }
        l.removeElement(b2);
    }

    public static Vector h() {
        return l;
    }

    public static c i() {
        if (a == null || a.isEmpty()) {
            return null;
        }
        return (c)a.peek();
    }

    public abstract void b(c var1);

    public abstract void a(c var1);
}

