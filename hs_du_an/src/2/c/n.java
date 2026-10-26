/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package c;

import b.g;
import c.b;
import c.j;
import c.u;
import d.m;
import e.a.a;
import javax.microedition.lcdui.Graphics;

public final class n
extends u {
    private byte b = (byte)-1;
    public static g[] a = null;
    private boolean c = false;
    private static int d = 0;
    private static int e = 0;
    private static String[] f = new String[]{b.a.p[2], b.a.p[52]};
    private static String[] g = new String[]{b.a.p[2], b.a.p[53]};

    public n(byte by) {
        this.b = by;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int[] nArray = e.a.b.a(graphics);
        graphics.clipRect(n2, n3, this.i, this.j);
        int n4 = this.i - (this.c ? 6 : 0);
        int n5 = n3;
        int n6 = n2;
        Graphics graphics2 = graphics;
        n n7 = this;
        int n8 = 0;
        for (int i2 = 0; i2 < 2; ++i2) {
            if ((i2 + 1) * b.g.a <= e || i2 * b.g.a >= e + n7.j) continue;
            n8 = n5 + i2 * b.g.a - e;
            if (i2 == d) {
                e.a.a.a(graphics2, 5848857, 15966997, n6, n8, n4, b.g.a / 2);
                e.a.a.a(graphics2, 15966997, 3681308, n6, n8 + b.g.a / 2, n4, b.g.a / 2);
            }
            if (i2 < 0 || i2 >= a.length || a[i2] == null) continue;
            a[i2].a(graphics2, n6, n8, n4);
        }
        if (this.c) {
            e.a.b.a(graphics, b.a.O, 2 * b.g.a, e, this.j, n2 + this.i - 6, n3 + b.a.O.getHeight(), this.j - (b.a.O.getHeight() << 1));
        }
        e.a.b.a(graphics, nArray);
    }

    public final void a(int n2, int n3) {
        block0 : switch (n2) {
            case -7: {
                ((u)object).k.b();
                return;
            }
            case 50: {
                d = (d - 1 + 2) % 2;
                if (d == a.length - 1 && a.length > 1) {
                    e = 2 * b.g.a - ((u)object).j;
                    break;
                }
                if (d * b.g.a < e) {
                    e = d * b.g.a;
                }
                return;
            }
            case 56: {
                d = (d + 1) % 2;
                if (d == 0 && a.length > 1) {
                    e = 0;
                    break;
                }
                if ((d + 1) * b.g.a > e + ((u)object).j) {
                    e = (d + 1) * b.g.a - ((u)object).j;
                }
                return;
            }
            case -6: 
            case 53: {
                Object object;
                Object object2 = null;
                switch (((n)object).b) {
                    case 0: {
                        object2 = c.b.a(f, (byte)0, (byte)2, true);
                        break;
                    }
                    case 1: {
                        if (a[d] == null) break block0;
                        object2 = c.b.a(g, (byte)0, (byte)2, true);
                    }
                }
                n3 = ((j)object2).f();
                if (n3 != 1) break;
                object2 = ((b)object2).d();
                if (object2 == null) break;
                if (((String)object2).equals(b.a.p[52])) {
                    if (a[d] == null) {
                        n.a[n.d] = new g();
                    }
                    b.a.a(a[d]);
                    b.a.a(a);
                    object = c.b.a(b.a.p[15], 3000L);
                    ((j)object).f();
                    return;
                }
                if (!((String)object2).equals(b.a.p[53]) || a[d] == null) break;
                b.a.x = null;
                a[d].c();
                if (b.a.a) {
                    main.b.a.i.a(new m());
                    return;
                }
                object = c.b.a("\u4f7f\u7528\u4fee\u6539\u5668,\n\u53ef\u6253\u5f00\u672c\u7ae0\u6240\u6709\u5267\u60c5,\u8d85\u5f3a\u88c5\u5907,\u91d1\u94b1\u5b8c\u5168\u514d\u8d39\u7528,\n\u8fd8\u6709\u66f4\u591a\u60ca\u559c\u7b49\u7740\u4f60!\n\u6309\u53f3\u952e\u79d8\u7c4d\u83dc\u5355\u53ef\u67e5\u770b", -1L);
                ((j)object).f();
                main.b.a.i.a(new m());
            }
        }
    }

    public final void e() {
        this.c = this.j < 2 * b.g.a;
    }

    public static void a() {
        a = b.a.g();
        if (a == null) {
            a = new g[2];
        }
    }
}

