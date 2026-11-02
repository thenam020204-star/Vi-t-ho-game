/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package c;

import b.a;
import b.f;
import c.e;
import f.b;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class c
extends e {
    private f a = null;
    private byte g = (byte)-1;
    private f.a[] h = null;
    private int[] l = null;
    private int[] m = null;

    public c(f f2, byte by) {
        this.a = f2;
        this.g = by;
    }

    protected final void a(Graphics graphics, int n2, int n3, int n4) {
        if (this.h == null || n2 < 0 || n2 >= this.h.length) {
            return;
        }
        graphics.drawString(this.h[n2].t, n3, n4, 20);
    }

    protected final void b(Graphics graphics, int n2, int n3, int n4) {
        if (this.l == null || n2 < 0 || n2 >= this.l.length) {
            return;
        }
        graphics.drawString(String.valueOf(this.l[n2]), n3, n4, 24);
    }

    protected final String a() {
        if (((c)object2).h == null || ((e)object2).d < 0 || ((e)object2).d >= ((c)object2).h.length || ((c)object2).h[((e)object2).d] == null) {
            return null;
        }
        Object object = ((c)object2).a.g(((c)object2).g);
        if (((c)object2).h[((e)object2).d].s == 11) {
            return ((c)object2).h[((e)object2).d].d();
        }
        Object object2 = ((c)object2).h[((e)object2).d];
        int[] nArray = new int[6];
        if (object == null) {
            nArray[0] = ((f.a)object2).a;
            nArray[1] = ((f.a)object2).b;
            nArray[2] = ((f.a)object2).c;
            nArray[3] = ((f.a)object2).j;
            nArray[4] = ((f.a)object2).k;
            nArray[5] = ((f.a)object2).l;
        } else {
            nArray[0] = ((f.a)object2).a - ((f.a)object).a;
            nArray[1] = ((f.a)object2).b - ((f.a)object).b;
            nArray[2] = ((f.a)object2).c - ((f.a)object).c;
            nArray[3] = ((f.a)object2).j - ((f.a)object).j;
            nArray[4] = ((f.a)object2).k - ((f.a)object).k;
            nArray[5] = ((f.a)object2).l - ((f.a)object).l;
        }
        object2 = nArray;
        object2 = nArray;
        if (nArray == null || ((Object)object2).length != 6) {
            return null;
        }
        object = new StringBuffer();
        ((StringBuffer)object).append(b.a.p[4] + " ");
        ((StringBuffer)object).append(object2[0] >= 0 ? "@1+" : "@0");
        ((StringBuffer)object).append((int)object2[0]);
        ((StringBuffer)object).append("#");
        ((StringBuffer)object).append(" ");
        ((StringBuffer)object).append(b.a.p[5] + " ");
        ((StringBuffer)object).append(object2[1] >= 0 ? "@1+" : "@0");
        ((StringBuffer)object).append((int)object2[1]);
        ((StringBuffer)object).append("#");
        ((StringBuffer)object).append(" ");
        ((StringBuffer)object).append(b.a.p[6] + " ");
        ((StringBuffer)object).append(object2[2] >= 0 ? "@1+" : "@0");
        ((StringBuffer)object).append((int)object2[2]);
        ((StringBuffer)object).append("#");
        ((StringBuffer)object).append(" ");
        ((StringBuffer)object).append(b.a.h(74) + " ");
        ((StringBuffer)object).append(object2[3] >= 0 ? "@1+" : "@0");
        ((StringBuffer)object).append((int)object2[3]);
        ((StringBuffer)object).append("#");
        ((StringBuffer)object).append(" ");
        ((StringBuffer)object).append(b.a.h(75) + " ");
        ((StringBuffer)object).append(object2[4] >= 0 ? "@1+" : "@0");
        ((StringBuffer)object).append((int)object2[4]);
        ((StringBuffer)object).append("#");
        ((StringBuffer)object).append(" ");
        ((StringBuffer)object).append(b.a.h(76) + " ");
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
        this.k.a(1);
        this.k.b();
    }

    protected final void d() {
        this.k.a(2);
        this.k.b();
    }

    public final void e() {
        f f2 = this.a;
        byte by = b.f.w[this.g];
        c c2 = this;
        this.m = b.a.b(by);
        if (c2.m != null && c2.m.length > 0) {
            int[] nArray;
            int[] nArray2 = c2.m;
            if (c2.m == null || nArray2.length <= 0) {
                nArray = null;
            } else if (f2 == null) {
                nArray = nArray2;
            } else {
                Vector<Integer> vector = new Vector<Integer>();
                b b2 = null;
                b2 = null;
                for (int i2 = 0; i2 < nArray2.length; ++i2) {
                    b2 = b.a.e(nArray2[i2]);
                    if (b2 == null || !b2.c() || b.a.d(nArray2[i2])) continue;
                    b2 = (f.a)b2;
                    if ((((f.a)b2).g & 1 << f2.f) == 0) continue;
                    vector.addElement(new Integer(nArray2[i2]));
                }
                if (vector.isEmpty()) {
                    nArray = null;
                } else {
                    int[] nArray3 = new int[vector.size()];
                    for (int i3 = 0; i3 < nArray3.length; ++i3) {
                        nArray3[i3] = (Integer)vector.elementAt(i3);
                    }
                    nArray = c2.m = nArray3;
                }
            }
            if (c2.m != null && c2.m.length > 0) {
                c2.h = new f.a[c2.m.length];
                c2.l = new int[c2.m.length];
                for (int i4 = 0; i4 < c2.m.length; ++i4) {
                    c2.h[i4] = (f.a)b.a.e(c2.m[i4]);
                    c2.l[i4] = b.a.f(c2.m[i4]);
                }
            } else {
                c2.h = null;
                c2.l = null;
            }
            c2.b(c2.h == null ? 0 : c2.h.length);
        }
        this.f();
    }
}

