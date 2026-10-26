/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package e;

import a.a.b;
import a.a.c;
import a.a.g;
import a.f.a;
import b.f;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class j
extends g {
    private f.a h = null;
    private byte i = (byte)-1;
    private c j = null;
    private b k = null;
    private Vector l = new Vector();
    private Vector m = new Vector();
    private f n = null;

    public j(f.a a2, byte by, f f2) {
        super("\u88c5\u5907");
        this.h = a2;
        this.i = by;
        this.n = f2;
    }

    protected final void a() {
        boolean bl = false;
        this.j = new c();
        this.j.b = 0;
        this.j.c = 0;
        this.j.d = this.d;
        this.j.e = this.e - a.a.b.a - 4;
        j j2 = this;
        Object object = b.a.b(j2.i, true);
        if (object != null) {
            int[] nArray;
            int n2;
            Object object2;
            int[] nArray2;
            Object object3 = j2.n;
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
                    f.b b2 = b.a.f(object4[n2]);
                    object = b2;
                    if (b2 == null || !object.e() || b.a.e(object4[n2])) continue;
                    object = (f.a)object;
                    if ((object.g & 1 << ((b.b)object3).i) == 0) continue;
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
                    object3 = b.a.g(nArray[n2]);
                    if (object3 == null) continue;
                    object4 = new a();
                    new a().a = object3;
                    object4.b = nArray[n2];
                    j2.l.addElement(object4);
                    j2.j.a(((f.b)object3).s, "");
                    Vector vector = j2.m;
                    Object object5 = object3;
                    object3 = j2.h;
                    object4 = object5;
                    object2 = new int[6];
                    if (object3 == null) {
                        object2[0] = object4.a;
                        object2[1] = object4.b;
                        object2[2] = object4.c;
                        object2[3] = object4.j;
                        object2[4] = object4.k;
                        object2[5] = object4.l;
                    } else {
                        object2[0] = object4.a - ((f.a)object3).a;
                        object2[1] = object4.b - ((f.a)object3).b;
                        object2[2] = object4.c - ((f.a)object3).c;
                        object2[3] = object4.j - ((f.a)object3).j;
                        object2[4] = object4.k - ((f.a)object3).k;
                        object2[5] = object4.l - ((f.a)object3).l;
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
                        ((StringBuffer)object3).append(b.a.k(76) + " ");
                        ((StringBuffer)object3).append(object4[5] >= 0 ? "@1+" : "@0");
                        ((StringBuffer)object3).append(object4[5]);
                        ((StringBuffer)object3).append("#");
                        string = ((StringBuffer)object3).toString();
                    }
                    vector.addElement(string);
                }
            }
        }
        this.a(this.j);
        int n3 = 0 + this.j.e;
        this.a(n3);
        this.k = new b();
        this.k.b = 0;
        this.k.c = n3 += 4;
        this.k.d = this.d;
        this.k.e = a.a.b.a;
        this.a(this.k);
        this.d();
    }

    public final void a(int n2, int n3) {
        switch (n2) {
            case 50: {
                this.j.a();
                this.d();
                break;
            }
            case 56: {
                this.j.b();
                this.d();
                break;
            }
            case 53: {
                this.a.b(1);
            }
        }
        switch (n3) {
            case 51: {
                this.k.a();
                return;
            }
            case 57: {
                this.k.c();
            }
        }
    }

    private void d() {
        int n2 = this.j.c();
        if (n2 < 0 || n2 >= this.m.size()) {
            return;
        }
        this.k.a((String)this.m.elementAt(n2));
        this.k.d();
    }

    public final void b() {
        this.k.b();
    }

    public final int c() {
        int n2 = ((j)((Object)a2)).j.c();
        if (n2 < 0 || n2 >= ((j)((Object)a2)).l.size()) {
            return -1;
        }
        a a2 = (a)((j)((Object)a2)).l.elementAt(n2);
        return a2.b;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        super.a(graphics, n2, n3);
    }
}

