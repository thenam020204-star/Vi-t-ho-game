/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Graphics
 */
package b;

import b.c;
import b.f;
import b.j;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import main.MainMIDlet;
import main.a;
import main.b;

public final class i
extends f
implements a {
    public static final int[] a = new int[]{4, 5, 9, 10, 11, 14, 15, 17, 19, 44};
    public static final String[] g = new String[]{"\u5151\u6362\u94f6\u4e24", "\u4e24\u500d\u91d1\u94b1", "\u4e09\u500d\u7ecf\u9a8c", "\u4fee\u6539\u5668"};
    private static String[] l = new String[]{"2", "2", "2", "10"};
    public static final String[] h = new String[]{"\u5151\u63623000\u94f6\u4e24.", "\u6fc0\u6d3b\u6c38\u4e45\u4e24\u500d\u91d1\u94b1(\u6240\u6709\u5b58\u6863).", "\u6fc0\u6d3b\u6c38\u4e45\u4e09\u500d\u7ecf\u9a8c(\u6240\u6709\u5b58\u6863).", "\u6fc0\u6d3b\u4fee\u6539\u5668,\u672c\u7ae0\u6e38\u620f\u8fc7\u7a0b\u4e2d\u53ef\u65e0\u9650\u6b21\u4f7f\u7528\u6240\u6709\u589e\u503c\u670d\u52a1.\u559c\u6b22\u539f\u6c41\u539f\u5473\u7684\u60a8,\u4e0d\u518d\u53d7\u4efb\u4f55\u5f71\u54cd\u5c3d\u60c5\u4eab\u53d7\u6e38\u620f."};

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
        Object object = new String[]{d.a.j(2), d.a.j(46)};
        object = b.c.a(object, null, (byte)0, (byte)1);
        object.a(this);
        main.b.a.i.e((j)object);
    }

    protected final void d() {
        this.k.b();
    }

    public final void e() {
        this.b(g == null ? 0 : g.length);
        this.f();
    }

    public final void a(j j2) {
        String string = j2.d();
        j2.b();
        if (string.equals(d.a.j(46))) {
            int n2 = 0;
            string = null;
            switch (this.d) {
                case 0: {
                    n2 = 200;
                    string = "06";
                    break;
                }
                case 1: {
                    if (d.a.l()) {
                        c c2 = b.c.a("\u4f60\u5df2\u7ecf\u6fc0\u6d3b\u4e86\u4e24\u500d\u91d1\u94b1!", 3000L, null);
                        c2.a(this);
                        main.b.a.i.e(c2);
                        return;
                    }
                    n2 = 200;
                    string = "14";
                    break;
                }
                case 2: {
                    if (d.a.m()) {
                        c c3 = b.c.a("\u4f60\u5df2\u7ecf\u6fc0\u6d3b\u4e86\u53cc\u500d\u7ecf\u9a8c!", 3000L, null);
                        c3.a(this);
                        main.b.a.i.e(c3);
                        return;
                    }
                    n2 = 200;
                    string = "13";
                    break;
                }
                case 3: {
                    n2 = 1000;
                    string = "17";
                    break;
                }
                default: {
                    return;
                }
            }
            boolean bl = false;
            bl = d.a.a ? true : i.a.a(MainMIDlet.a, (Displayable)main.b.a, g[this.d], string, string, n2);
            c c4 = null;
            if (bl) {
                c4 = b.c.a("\u6fc0\u6d3b\u6210\u529f!", 3000L, null);
                d.a.a(1001, 2);
                switch (this.d) {
                    case 0: {
                        d.a.E += 3000;
                        break;
                    }
                    case 1: {
                        d.a.b = 2;
                        break;
                    }
                    case 2: {
                        d.a.c = 3;
                        break;
                    }
                    case 3: {
                        d.a.a = true;
                        d.a.a(1001, 18);
                    }
                }
                d.a.o();
            } else {
                c4 = b.c.a("\u6fc0\u6d3b\u5931\u8d25!", 3000L, null);
            }
            c4.a(this);
            main.b.a.i.e(c4);
        }
    }

    public final void d(j j2) {
        main.b.a.i.f(j2);
    }

    public final void b(j j2) {
        this.a(j2);
    }

    public final void c(j j2) {
        j2.b();
    }
}

