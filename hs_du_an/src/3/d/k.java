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
    public byte g = (byte)-1;
    public k h = null;
    protected boolean i = false;
    protected byte j = 0;
    private static Stack a = new Stack();
    protected static Vector k = null;
    protected byte l = (byte)-1;
    protected byte m = (byte)-1;

    public abstract void a(Graphics var1);

    public abstract void a();

    public abstract void a(int var1, int var2);

    public k(byte by) {
        this.g = by;
        k = new Vector();
    }

    public void b() {
        a.removeAllElements();
        k.removeAllElements();
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
                if (!((c)object).g) continue;
                nArray = ((c)object).g();
                for (int i3 = i2 + 1; i3 < a.size(); ++i3) {
                    c2 = (c)a.elementAt(i3);
                    if (b.e.a.a(nArray, c2.g())) continue block0;
                }
                ((c)object).a((Graphics)k2, ((c)object).a, ((c)object).b);
                if (i2 != n2 - 1) continue;
                d.k.a((Graphics)k2, ((c)object).e, 0, main.a.e, 36);
                d.k.a((Graphics)k2, ((c)object).f, main.a.d, main.a.e, 40);
            }
        }
        k2 = graphics;
        if (!k.isEmpty()) {
            object = null;
            int n3 = k.size();
            for (int i4 = 0; i4 < n3; ++i4) {
                object = (b)k.elementAt(i4);
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
        return this.h;
    }

    public final void a(k k2) {
        this.h = k2;
    }

    protected static void a(Graphics graphics, byte by) {
        if (f.Y != null) {
            graphics.setColor(0xFFFFFF);
            for (int i2 = 0; i2 < f.Y.length; ++i2) {
                graphics.drawString(f.Y[i2], 0, main.a.e - 5 - b.e.a.b * f.Y.length + i2 * b.e.a.b, 36);
            }
        }
        graphics.setColor(10802659);
        graphics.fillRect(0, main.a.e - 5, main.a.d * by / 100, 5);
        graphics.setColor(0xFFFFFF);
        graphics.drawString(by + "%", 0, main.a.e - 5, 36);
    }

    protected final void a(int n2) {
        this.j = (byte)n2;
    }

    protected final void b(int n2) {
        this.j = (byte)(this.j + 10);
        if (this.j > 100) {
            this.j = (byte)100;
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
        k.addElement(b2);
    }

    public static void b(b b2) {
        if (b2 == null) {
            return;
        }
        k.removeElement(b2);
    }

    public static Vector h() {
        return k;
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

