/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Graphics
 */
package c;

import c.b;
import c.e;
import c.i;
import c.j;
import g.a.a;
import java.util.Vector;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import main.MainMIDlet;

public final class f
extends e {
    private static String[] a = new String[]{b.a.p[46], b.a.p[2]};
    private f.b[] g = null;
    private int[] h = null;
    private int[] l = null;

    public f(int[] nArray) {
        this.l = nArray;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        super.a(graphics, n2, n3);
        graphics.drawImage(b.a.Q, n2 + this.i - String.valueOf(b.a.D).length() * b.a.H, n3 - b.a.M.getHeight() / 2, 40);
        e.a.b.a(graphics, b.a.G, b.a.D, n2 + this.i - String.valueOf(b.a.D).length() * b.a.H, n3 - b.a.M.getHeight() / 2 - b.a.G.getHeight());
    }

    protected final void a(Graphics graphics, int n2, int n3, int n4) {
        if (this.g == null || n2 < 0 || n2 > this.g.length) {
            return;
        }
        if (n2 == 0) {
            graphics.drawString(c.i.g[0], n3, n4, 20);
            return;
        }
        graphics.drawString(this.g[n2 - 1].t, n3, n4, 20);
    }

    protected final void b(Graphics graphics, int n2, int n3, int n4) {
        if (this.g == null || n2 < 0 || n2 > this.g.length) {
            return;
        }
        if (n2 > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(this.g[n2 - 1].u);
            stringBuffer.append("(");
            stringBuffer.append(this.h[n2 - 1]);
            stringBuffer.append(")");
            graphics.drawString(stringBuffer.toString(), n3, n4, 24);
        }
    }

    protected final String a() {
        if (this.d == 0) {
            return c.i.h[0] + "\u8d60\u90012\u4e2a\u5143\u5b9d(\u7528\u4e8e\u6362\u9ad8\u7ea7\u88c5\u5907),\u8bf7\u8bb0\u5f97\u5b58\u6863!";
        }
        if (this.g != null && this.d - 1 >= 0 && this.d - 1 < this.g.length && this.g[this.d - 1] != null) {
            return this.g[this.d - 1].d();
        }
        return null;
    }

    private Object b() {
        if (this.g == null || this.d <= 0 || this.d > this.g.length) {
            return null;
        }
        return this.g[this.d - 1];
    }

    protected final void c() {
        if (((e)((Object)b3)).d != 0 && ((f)((Object)b3)).b() == null) {
            return;
        }
        Object object = c.b.a(a, (byte)0, (byte)2, true);
        int n2 = ((j)object).f();
        if (n2 == 1) {
            object = ((b)object).d();
            if (object != null && ((String)object).equals(b.a.p[46])) {
                if (((e)((Object)b3)).d == 0) {
                    boolean bl = false;
                    bl = b.a.a ? true : g.a.a.a(MainMIDlet.a, (Displayable)main.b.a, c.i.g[0], "06", "06", 200);
                    b b2 = null;
                    if (bl) {
                        b.a.a(1001, 2);
                        b2 = c.b.a(b.a.h(49), 3000L);
                        b.a.D += 3000;
                    } else {
                        b2 = c.b.a(b.a.h(73), 3000L);
                    }
                    b2.f();
                    return;
                }
                object = (f.b)((f)((Object)b3)).b();
                String string = null;
                if (object == null) {
                    string = b.a.p[47];
                } else if (b.a.D < ((f.b)object).u) {
                    string = b.a.p[48];
                } else {
                    string = b.a.p[49];
                    b.a.D -= ((f.b)object).u;
                    b.a.b((f.b)object, 1);
                    int n3 = ((e)((Object)b3)).d - 1;
                    ((f)((Object)b3)).h[n3] = ((f)((Object)b3)).h[n3] + 1;
                }
                b b3 = c.b.a(string, 3000L);
                b3.f();
            }
        }
    }

    protected final void d() {
        this.k.b();
    }

    public final void e() {
        int n2;
        f f2;
        int[] nArray = this.l;
        f f3 = this;
        if (nArray == null) {
            f2 = f3;
            n2 = 1;
        } else {
            int n3;
            Vector<f.b> vector = new Vector<f.b>();
            f.b b2 = null;
            for (n3 = 0; n3 < nArray.length; ++n3) {
                b2 = b.a.a(nArray[n3]);
                if (b2 == null || b2.u <= 0) continue;
                vector.addElement(b2);
            }
            if (vector.size() > 0) {
                f3.g = new f.b[vector.size()];
                f3.h = new int[f3.g.length];
                for (n3 = 0; n3 < vector.size(); ++n3) {
                    f3.g[n3] = (f.b)vector.elementAt(n3);
                    f3.h[n3] = b.a.a(f3.g[n3]);
                }
            }
            f2 = f3;
            n2 = f3.g == null ? 1 : f3.g.length + 1;
        }
        f2.b(n2);
        this.f();
    }
}

