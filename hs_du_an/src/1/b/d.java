/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b;

import b.f;
import d.a;
import d.g;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class d
extends f {
    private g a = null;
    private byte g = (byte)-1;
    private c.d[] h = null;
    private int[] l = null;
    private int[] m = null;

    public d(g g2, byte by) {
        this.a = g2;
        this.g = by;
    }

    protected final void a(Graphics graphics, int n2, int n3, int n4) {
        if (this.h == null || n2 < 0 || n2 >= this.h.length) {
            return;
        }
        graphics.drawString(this.h[n2].r, n3, n4, 20);
    }

    protected final void b(Graphics graphics, int n2, int n3, int n4) {
        if (this.l == null || n2 < 0 || n2 >= this.l.length) {
            return;
        }
        graphics.drawString(String.valueOf(this.l[n2]), n3, n4, 24);
    }

    protected final String a() {
        if (((d)object2).h == null || ((f)object2).d < 0 || ((f)object2).d >= ((d)object2).h.length || ((d)object2).h[((f)object2).d] == null) {
            return null;
        }
        Object object = ((d)object2).a.c(((d)object2).g);
        Object object2 = ((d)object2).h[((f)object2).d];
        int[] nArray = new int[6];
        if (object == null) {
            nArray[0] = ((c.d)object2).a;
            nArray[1] = ((c.d)object2).b;
            nArray[2] = ((c.d)object2).c;
            nArray[3] = ((c.d)object2).j;
            nArray[4] = ((c.d)object2).k;
            nArray[5] = ((c.d)object2).l;
        } else {
            nArray[0] = ((c.d)object2).a - ((c.d)object).a;
            nArray[1] = ((c.d)object2).b - ((c.d)object).b;
            nArray[2] = ((c.d)object2).c - ((c.d)object).c;
            nArray[3] = ((c.d)object2).j - ((c.d)object).j;
            nArray[4] = ((c.d)object2).k - ((c.d)object).k;
            nArray[5] = ((c.d)object2).l - ((c.d)object).l;
        }
        object2 = nArray;
        object2 = nArray;
        if (nArray == null || ((Object)object2).length != 6) {
            return null;
        }
        object = new StringBuffer();
        ((StringBuffer)object).append(d.a.q[4] + " ");
        ((StringBuffer)object).append(object2[0] >= 0 ? "@1+" : "@0");
        ((StringBuffer)object).append((int)object2[0]);
        ((StringBuffer)object).append("#");
        ((StringBuffer)object).append(" ");
        ((StringBuffer)object).append(d.a.q[5] + " ");
        ((StringBuffer)object).append(object2[1] >= 0 ? "@1+" : "@0");
        ((StringBuffer)object).append((int)object2[1]);
        ((StringBuffer)object).append("#");
        ((StringBuffer)object).append(" ");
        ((StringBuffer)object).append(d.a.q[6] + " ");
        ((StringBuffer)object).append(object2[2] >= 0 ? "@1+" : "@0");
        ((StringBuffer)object).append((int)object2[2]);
        ((StringBuffer)object).append("#");
        ((StringBuffer)object).append(" ");
        ((StringBuffer)object).append(d.a.j(74) + " ");
        ((StringBuffer)object).append(object2[3] >= 0 ? "@1+" : "@0");
        ((StringBuffer)object).append((int)object2[3]);
        ((StringBuffer)object).append("#");
        ((StringBuffer)object).append(" ");
        ((StringBuffer)object).append(d.a.j(75) + " ");
        ((StringBuffer)object).append(object2[4] >= 0 ? "@1+" : "@0");
        ((StringBuffer)object).append((int)object2[4]);
        ((StringBuffer)object).append("#");
        ((StringBuffer)object).append(" ");
        ((StringBuffer)object).append(d.a.j(76) + " ");
        ((StringBuffer)object).append(object2[5] >= 0 ? "@1+" : "@0");
        ((StringBuffer)object).append((int)object2[5]);
        ((StringBuffer)object).append("#");
        return ((StringBuffer)object).toString();
    }

    protected final void c() {
        if (this.m == null || this.d < 0 || this.d >= this.m.length) {
            return;
        }
        this.a.c(this.m[this.d], (int)this.g);
        this.k.b();
    }

    protected final void d() {
        this.k.b();
    }

    public final void e() {
        g g2 = this.a;
        byte by = d.g.x[this.g];
        d d2 = this;
        this.m = d.a.b(by);
        if (d2.m != null && d2.m.length > 0) {
            int[] nArray;
            int[] nArray2 = d2.m;
            if (d2.m == null || nArray2.length <= 0) {
                nArray = null;
            } else if (g2 == null) {
                nArray = nArray2;
            } else {
                Vector<Integer> vector = new Vector<Integer>();
                c.f f2 = null;
                f2 = null;
                for (int i2 = 0; i2 < nArray2.length; ++i2) {
                    f2 = d.a.f(nArray2[i2]);
                    if (f2 == null || !f2.c() || d.a.e(nArray2[i2])) continue;
                    f2 = (c.d)f2;
                    if ((((c.d)f2).g & 1 << g2.f) == 0) continue;
                    vector.addElement(new Integer(nArray2[i2]));
                }
                if (vector.isEmpty()) {
                    nArray = null;
                } else {
                    int[] nArray3 = new int[vector.size()];
                    for (int i3 = 0; i3 < nArray3.length; ++i3) {
                        nArray3[i3] = (Integer)vector.elementAt(i3);
                    }
                    nArray = d2.m = nArray3;
                }
            }
            if (d2.m != null && d2.m.length > 0) {
                d2.h = new c.d[d2.m.length];
                d2.l = new int[d2.m.length];
                for (int i4 = 0; i4 < d2.m.length; ++i4) {
                    d2.h[i4] = (c.d)d.a.f(d2.m[i4]);
                    d2.l[i4] = d.a.g(d2.m[i4]);
                }
            } else {
                d2.h = null;
                d2.l = null;
            }
            d2.b(d2.h == null ? 0 : d2.h.length);
        }
        this.f();
    }
}

