/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package c;

import a.a.h;
import a.c.b;
import java.util.Stack;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public abstract class a
implements h {
    public byte a = (byte)-1;
    public a b = null;
    protected boolean c = false;
    protected byte d = 0;
    private static Stack f = new Stack();
    protected static Vector e = null;

    public abstract void a(Graphics var1);

    public abstract void a();

    public abstract void a(int var1, int var2);

    public a(byte by) {
        this.a = by;
        e = new Vector();
    }

    public void b() {
        f.removeAllElements();
        e.removeAllElements();
        b.a.X = null;
    }

    protected static void c() {
        if (!f.isEmpty()) {
            a.a.a a2 = null;
            for (int i2 = 0; i2 < f.size(); ++i2) {
                a2 = (a.a.a)f.elementAt(i2);
                a2.a();
            }
        }
    }

    protected final void b(Graphics graphics) {
        Object object;
        a a2 = graphics;
        if (!f.isEmpty()) {
            object = null;
            a.a.a a3 = null;
            int[] nArray = null;
            int n2 = f.size();
            block0: for (int i2 = 0; i2 < n2; ++i2) {
                object = (a.a.a)f.elementAt(i2);
                if (!((a.a.a)object).g) continue;
                nArray = ((a.a.a)object).f();
                for (int i3 = i2 + 1; i3 < f.size(); ++i3) {
                    a3 = (a.a.a)f.elementAt(i3);
                    if (a.c.b.a(nArray, a3.f())) continue block0;
                }
                ((a.a.a)object).a((Graphics)a2, ((a.a.a)object).a, ((a.a.a)object).b);
                if (i2 != n2 - 1) continue;
                c.a.a((Graphics)a2, ((a.a.a)object).e, 0, main.a.e, 36);
                c.a.a((Graphics)a2, ((a.a.a)object).f, main.a.d, main.a.e, 40);
            }
        }
        a2 = graphics;
        if (!e.isEmpty()) {
            object = null;
            int n3 = e.size();
            for (int i4 = 0; i4 < n3; ++i4) {
                object = (b.b)e.elementAt(i4);
                if (!((b.b)object).a) continue;
                ((b.b)object).a((Graphics)a2, ((b.b)object).n, ((b.b)object).o);
            }
        }
    }

    public static void a(Graphics graphics, byte by, int n2, int n3, int n4) {
        switch (by) {
            case 1: 
            case 2: {
                graphics.drawImage(b.a.S, n2, n3, n4);
                return;
            }
            case 0: {
                graphics.drawImage(b.a.R, n2, n3, n4);
                return;
            }
            case 3: {
                graphics.drawImage(b.a.T, n2, n3, n4);
                return;
            }
            case 5: {
                graphics.drawImage(b.a.U, n2, n3, n4);
            }
        }
    }

    protected static void c(Graphics graphics) {
        graphics.setColor(0);
        graphics.fillRect(0, 0, main.a.d, main.a.e);
    }

    public final a d() {
        return this.b;
    }

    public final void a(a a2) {
        this.b = a2;
    }

    protected static void a(Graphics graphics, byte by) {
        int n2 = main.a.e - 5 - a.c.b.b * b.a.Y - a.c.b.b;
        a.c.b.a(graphics, b.a.X, main.a.d, 0, n2, 0xFFFFFF);
        graphics.setColor(10802659);
        graphics.fillRect(0, main.a.e - 5, main.a.d * by / 100, 5);
        graphics.setColor(0xFFFFFF);
        graphics.drawString(by + "%", 0, main.a.e - 5, 36);
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
        return f;
    }

    public static boolean f() {
        return f == null || f.isEmpty();
    }

    public static void a(a.a.a a2) {
        if (a2 == null) {
            return;
        }
        f.push(a2);
    }

    public static void b(a.a.a a2) {
        if (a2 == null) {
            return;
        }
        f.removeElement(a2);
    }

    public static void a(b.b b2) {
        if (b2 == null) {
            return;
        }
        e.addElement(b2);
    }

    public static void b(b.b b2) {
        if (b2 == null) {
            return;
        }
        e.removeElement(b2);
    }

    public static Vector g() {
        return e;
    }

    public static a.a.a h() {
        if (f == null || f.isEmpty()) {
            return null;
        }
        return (a.a.a)f.peek();
    }

    public abstract void c(a.a.a var1);

    public abstract void d(a.a.a var1);
}

