/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package c;

import a.f;
import a.g;
import b.b.b;
import b.b.i;
import b.d.a;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class l
extends b.b.l {
    private f.b a = null;
    private byte b = (byte)-1;
    private i c = null;
    private b d = null;
    private Vector l = new Vector();
    private Vector m = new Vector();
    private g n = null;

    public l(f.b b2, byte by, g g2) {
        super("\u88c5\u5907");
        this.a = b2;
        this.b = by;
        this.n = g2;
    }

    protected final void a() {
        boolean bl = false;
        this.c = new i();
        this.c.b = 0;
        this.c.c = 0;
        this.c.d = this.h;
        this.c.e = this.i - b.b.b.a - 4;
        l l2 = this;
        Object object = a.f.b(l2.b, true);
        if (object != null) {
            int[] nArray;
            int n2;
            Object object2;
            int[] nArray2;
            Object object3 = l2.n;
            Object object4 = object;
            if (object == null || ((int[])object4).length <= 0) {
                nArray2 = null;
            } else if (object3 == null) {
                nArray2 = object4;
            } else {
                object2 = new Vector<Integer>();
                object = null;
                object = null;
                for (n2 = 0; n2 < ((int[])object4).length; ++n2) {
                    f.a a2 = a.f.f(object4[n2]);
                    object = a2;
                    if (a2 == null || !object.d() || a.f.e(object4[n2])) continue;
                    object = (f.b)object;
                    if ((object.o & 1 << ((a.b)object3).n) == 0) continue;
                    ((Vector)object2).addElement(new Integer(object4[n2]));
                }
                if (((Vector)object2).isEmpty()) {
                    nArray2 = null;
                } else {
                    int[] nArray3 = new int[((Vector)object2).size()];
                    for (int i2 = 0; i2 < nArray3.length; ++i2) {
                        nArray3[i2] = (Integer)((Vector)object2).elementAt(i2);
                    }
                    nArray2 = nArray = nArray3;
                }
            }
            if (nArray2 != null) {
                object4 = null;
                object3 = null;
                for (n2 = 0; n2 < nArray.length; ++n2) {
                    String string;
                    object3 = a.f.g(nArray[n2]);
                    if (object3 == null) continue;
                    object4 = new a();
                    new a().a = object3;
                    object4.b = nArray[n2];
                    l2.l.addElement(object4);
                    l2.c.a(((f.a)object3).e, "");
                    Vector vector = l2.m;
                    Object object5 = object3;
                    object3 = l2.a;
                    object4 = object5;
                    object2 = new int[6];
                    if (object3 == null) {
                        object2[0] = object4.i;
                        object2[1] = object4.j;
                        object2[2] = object4.k;
                        object2[3] = object4.r;
                        object2[4] = object4.s;
                        object2[5] = object4.t;
                    } else {
                        object2[0] = object4.i - ((f.b)object3).i;
                        object2[1] = object4.j - ((f.b)object3).j;
                        object2[2] = object4.k - ((f.b)object3).k;
                        object2[3] = object4.r - ((f.b)object3).r;
                        object2[4] = object4.s - ((f.b)object3).s;
                        object2[5] = object4.t - ((f.b)object3).t;
                    }
                    Object object6 = object2;
                    object4 = object6;
                    if (object6 == null || ((int[])object4).length != 6) {
                        string = null;
                    } else {
                        object3 = new StringBuffer();
                        ((StringBuffer)object3).append("\u653b ");
                        ((StringBuffer)object3).append(object4[0] >= 0 ? "@1+" : "@0");
                        ((StringBuffer)object3).append(object4[0]);
                        ((StringBuffer)object3).append("#");
                        ((StringBuffer)object3).append(" ");
                        ((StringBuffer)object3).append("\u9632 ");
                        ((StringBuffer)object3).append(object4[1] >= 0 ? "@1+" : "@0");
                        ((StringBuffer)object3).append(object4[1]);
                        ((StringBuffer)object3).append("#");
                        ((StringBuffer)object3).append(" ");
                        ((StringBuffer)object3).append("\u654f ");
                        ((StringBuffer)object3).append(object4[2] >= 0 ? "@1+" : "@0");
                        ((StringBuffer)object3).append(object4[2]);
                        ((StringBuffer)object3).append("#");
                        ((StringBuffer)object3).append(" ");
                        ((StringBuffer)object3).append("\u547d ");
                        ((StringBuffer)object3).append(object4[3] >= 0 ? "@1+" : "@0");
                        ((StringBuffer)object3).append(object4[3]);
                        ((StringBuffer)object3).append("#");
                        ((StringBuffer)object3).append(" ");
                        ((StringBuffer)object3).append("\u7075 ");
                        ((StringBuffer)object3).append(object4[4] >= 0 ? "@1+" : "@0");
                        ((StringBuffer)object3).append(object4[4]);
                        ((StringBuffer)object3).append("#");
                        ((StringBuffer)object3).append(" ");
                        ((StringBuffer)object3).append(a.f.k(76) + " ");
                        ((StringBuffer)object3).append(object4[5] >= 0 ? "@1+" : "@0");
                        ((StringBuffer)object3).append(object4[5]);
                        ((StringBuffer)object3).append("#");
                        string = ((StringBuffer)object3).toString();
                    }
                    vector.addElement(string);
                }
            }
        }
        this.a(this.c);
        int n3 = 0 + this.c.e;
        this.c(n3);
        this.d = new b();
        this.d.b = 0;
        this.d.c = n3 += 4;
        this.d.d = this.h;
        this.d.e = b.b.b.a;
        this.a(this.d);
        this.d();
    }

    public final void a(int n2, int n3) {
        switch (n2) {
            case 50: {
                this.c.a();
                this.d();
                break;
            }
            case 56: {
                this.c.b();
                this.d();
                break;
            }
            case 53: {
                this.e.a(1);
            }
        }
        switch (n3) {
            case 51: {
                this.d.a();
                return;
            }
            case 57: {
                this.d.c();
            }
        }
    }

    private void d() {
        int n2 = this.c.c();
        if (n2 < 0 || n2 >= this.m.size()) {
            return;
        }
        this.d.a((String)this.m.elementAt(n2));
        this.d.d();
    }

    public final void b() {
        this.d.b();
    }

    public final int c() {
        int n2 = ((l)((Object)a2)).c.c();
        if (n2 < 0 || n2 >= ((l)((Object)a2)).l.size()) {
            return -1;
        }
        a a2 = (a)((l)((Object)a2)).l.elementAt(n2);
        return a2.b;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        super.a(graphics, n2, n3);
    }
}

