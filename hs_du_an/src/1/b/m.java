/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b;

import b.c;
import b.j;
import b.t;
import d.h;
import g.l;
import javax.microedition.lcdui.Graphics;
import main.a;
import main.b;

public final class m
extends t
implements a {
    private byte b = (byte)-1;
    public static h[] a = null;
    private boolean c = false;
    private static int d = 0;
    private static int e = 0;
    private static String[] f = new String[]{d.a.q[2], d.a.q[52]};
    private static String[] g = new String[]{d.a.q[2], d.a.q[53]};
    private c h = null;

    public m(byte by) {
        this.b = by;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int[] nArray = h.b.a(graphics);
        graphics.clipRect(n2, n3, this.i, this.j);
        int n4 = this.i - (this.c ? 6 : 0);
        int n5 = n3;
        int n6 = n2;
        Graphics graphics2 = graphics;
        m m2 = this;
        int n7 = 0;
        for (int i2 = 0; i2 < 3; ++i2) {
            if ((i2 + 1) * d.h.a <= e || i2 * d.h.a >= e + m2.j) continue;
            n7 = n5 + i2 * d.h.a - e;
            if (i2 == d) {
                h.a.a(graphics2, 5848857, 15966997, n6, n7, n4, d.h.a / 2);
                h.a.a(graphics2, 15966997, 3681308, n6, n7 + d.h.a / 2, n4, d.h.a / 2);
            }
            if (i2 < 0 || i2 >= a.length || a[i2] == null) continue;
            a[i2].a(graphics2, n6, n7, n4);
        }
        if (this.c) {
            h.b.a(graphics, 3 * d.h.a, e, this.j, n2 + this.i - 6, n3 + d.a.P.getHeight(), this.j - (d.a.P.getHeight() << 1));
        }
        h.b.a(graphics, nArray);
    }

    public final void a(int n2, int n3) {
        block0 : switch (n2) {
            case -7: {
                this.k.b();
                return;
            }
            case 50: {
                d = (d - 1 + 3) % 3;
                if (d == a.length - 1 && a.length > 1) {
                    e = 3 * d.h.a - this.j;
                    break;
                }
                if (d * d.h.a < e) {
                    e = d * d.h.a;
                }
                return;
            }
            case 56: {
                d = (d + 1) % 3;
                if (d == 0 && a.length > 1) {
                    e = 0;
                    break;
                }
                if ((d + 1) * d.h.a > e + this.j) {
                    e = (d + 1) * d.h.a - this.j;
                }
                return;
            }
            case -6: 
            case 53: {
                j j2 = null;
                switch (this.b) {
                    case 0: {
                        j2 = b.c.a(f, null, (byte)0, (byte)2);
                        break;
                    }
                    case 1: {
                        if (a[d] == null) break block0;
                        j2 = b.c.a(g, null, (byte)0, (byte)2);
                    }
                }
                j2.a(this);
                main.b.a.i.e(j2);
            }
        }
    }

    public final void e() {
        this.c = this.j < 3 * d.h.a;
    }

    public final void a(j j2) {
        Object object = j2.d();
        if (((String)object).equals(d.a.q[52])) {
            if (a[d] == null) {
                m.a[m.d] = new h();
            }
            d.a.a(a[d]);
            d.a.a(a);
            object = b.c.a(d.a.q[15], 3000L, null);
            ((j)object).a(this);
            main.b.a.i.e((j)object);
        } else if (((String)object).equals(d.a.q[53]) && a[d] != null) {
            d.a.y = null;
            a[d].c();
            if (d.a.a) {
                main.b.a.i.a(new l());
            } else {
                this.h = b.c.a("\u4f7f\u7528\u4fee\u6539\u5668,\n\u53ef\u6253\u5f00\u672c\u7ae0\u6240\u6709\u5267\u60c5,\u8d85\u5f3a\u88c5\u5907,\u91d1\u94b1\u5b8c\u5168\u514d\u8d39\u7528,\n\u8fd8\u6709\u66f4\u591a\u60ca\u559c\u7b49\u7740\u4f60!\n\u6309\u53f3\u952e\u79d8\u7c4d\u83dc\u5355\u53ef\u67e5\u770b", 5000L, null);
                this.h.a(this);
                main.b.a.i.e(this.h);
            }
        }
        j2.b();
    }

    public final void d(j j2) {
        main.b.a.i.f(j2);
        if (j2 == this.h) {
            main.b.a.i.a(new l());
        }
    }

    public final void b(j j2) {
    }

    public final void c(j j2) {
        j2.b();
    }

    public static void a() {
        a = d.a.h();
        if (a == null) {
            a = new h[3];
        }
    }
}

