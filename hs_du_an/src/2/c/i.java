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
import c.j;
import c.k;
import g.a.a;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import main.MainMIDlet;

public final class i
extends e {
    public static final int[] a = new int[]{4, 5, 9, 10, 13, 14, 15, 16, 19, 36};
    public static final String[] g = new String[]{"\u5151\u6362\u94f6\u4e24", "\u4e24\u500d\u91d1\u94b1", "\u4e24\u500d\u7ecf\u9a8c", "\u4fee\u6539\u5668"};
    private static String[] l = new String[]{"\u3000", "\u3000", "\u3000", "\u3000"};
    public static final String[] h = new String[]{"\u5151\u63623000\u94f6\u4e24,", "\u6fc0\u6d3b\u6c38\u4e45\u4e24\u500d\u91d1\u94b1(\u6240\u6709\u5b58\u6863),", "\u6fc0\u6d3b\u6c38\u4e45\u4e24\u500d\u7ecf\u9a8c(\u6240\u6709\u5b58\u6863),", "\u6fc0\u6d3b\u4fee\u6539\u5668,\u672c\u7ae0\u6e38\u620f\u8fc7\u7a0b\u4e2d\u53ef\u65e0\u9650\u6b21\u4f7f\u7528\u6240\u6709\u589e\u503c\u670d\u52a1.\u559c\u6b22\u539f\u6c41\u539f\u5473\u7684\u60a8,\u4e0d\u518d\u53d7\u4efb\u4f55\u5f71\u54cd\u5c3d\u60c5\u4eab\u53d7\u6e38\u620f."};

    protected final void a(Graphics graphics, int n2, int n3, int n4) {
        if (n2 < 0 || n2 >= g.length) {
            return;
        }
        graphics.drawString(g[n2], n3, n4, 20);
    }

    protected final void b(Graphics graphics, int n2, int n3, int n4) {
        if (n2 < 0 || n2 >= g.length) {
            return;
        }
        graphics.drawString(l[n2] + "\u3000", n3, n4, 24);
    }

    protected final String a() {
        if (this.d < 0 || this.d >= h.length) {
            return null;
        }
        return h[this.d] + "\u8d60\u90012\u4e2a\u5143\u5b9d(\u7528\u4e8e\u6362\u9ad8\u7ea7\u88c5\u5907),\u8bf7\u8bb0\u5f97\u5b58\u6863!";
    }

    protected final void c() {
        Object object = new String[]{b.a.h(46), b.a.h(2)};
        int n2 = ((j)(object = c.b.a((String[])object, (byte)0, (byte)1, true))).f();
        if (n2 == 1) {
            String string = ((k)object).d();
            ((k)object).b();
            if (string.equals(b.a.h(46))) {
                int n3 = 0;
                string = null;
                switch (this.d) {
                    case 0: {
                        n3 = 200;
                        string = "06";
                        break;
                    }
                    case 1: {
                        if (b.a.l()) {
                            b b2 = c.b.a("\u4f60\u5df2\u7ecf\u8d2d\u4e70\u8fc7\u4e24\u500d\u91d1\u94b1\u4e86", 3000L);
                            b2.f();
                            return;
                        }
                        n3 = 200;
                        string = "14";
                        break;
                    }
                    case 2: {
                        if (b.a.m()) {
                            b b3 = c.b.a("\u4f60\u5df2\u7ecf\u8d2d\u4e70\u8fc7\u4e24\u500d\u7ecf\u9a8c\u4e86", 3000L);
                            b3.f();
                            return;
                        }
                        n3 = 200;
                        string = "13";
                        break;
                    }
                    case 3: {
                        n3 = 1000;
                        string = "17";
                        break;
                    }
                    default: {
                        return;
                    }
                }
                boolean bl = false;
                bl = b.a.a ? true : g.a.a.a(MainMIDlet.a, (Displayable)main.b.a, g[this.d], string, string, n3);
                b b4 = null;
                if (bl) {
                    b4 = c.b.a(b.a.h(49), 3000L);
                    b.a.a(1001, 2);
                    switch (this.d) {
                        case 0: {
                            b.a.D += 3000;
                            break;
                        }
                        case 1: {
                            b.a.b = 2;
                            break;
                        }
                        case 2: {
                            b.a.c = 2;
                            break;
                        }
                        case 3: {
                            b.a.a = true;
                            b.a.a(1001, 18);
                        }
                    }
                    b.a.o();
                } else {
                    b4 = c.b.a("\u591a\u597d\u7684\u4e1c\u897f,\u4e0d\u4e70\u771f\u662f\u53ef\u60dc!", 3000L);
                }
                b4.f();
            }
        }
    }

    protected final void d() {
        this.k.b();
    }

    public final void e() {
        this.b(g == null ? 0 : g.length);
        this.f();
    }
}

