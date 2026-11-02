/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Displayable
 */
package c;

import a.f;
import b.b.b;
import b.b.g;
import b.b.l;
import b.b.m;
import java.util.Vector;
import javax.microedition.lcdui.Displayable;
import main.MainMIDlet;
import main.a;

public final class i
extends l {
    public static Vector a = new Vector();
    public static final int[] b = new int[]{4, 5, 9, 10, 95, 13, 14, 15, 16, 19, 36};
    public static final String[] c = new String[]{"\u5151\u63623000\u91d1\u94b1", "\u8d2d\u4e70\u6c38\u4e45\u4e24\u500d\u91d1\u94b1(\u6240\u6709\u5b58\u6863)", "\u8d2d\u4e70\u6c38\u4e45\u4e24\u500d\u7ecf\u9a8c(\u6240\u6709\u5b58\u6863)", "\u4e0d\u9047\u654c,\u8d2d\u4e70\u540e\u63090\u952e\u53ef\u4ee5\u5f00\u5173\u6b64\u529f\u80fd", "\u4e00\u6b21\u6027\u4ed8\u8d39,\u6e38\u620f\u4e2d\u4e0d\u518d\u9700\u8981\u6263\u8d39,\u53ef\u4efb\u610f\u4f7f\u7528\u6e38\u620f\u4e2d\u6240\u6709\u589e\u503c\u670d\u52a1."};
    public static final String[] d = new String[]{"\u5151\u6362\u91d1\u94b1", "\u4e24\u500d\u91d1\u94b1", "\u4e24\u500d\u7ecf\u9a8c", "\u4e0d\u9047\u654c,\u8d2d\u4e70\u540e\u63090\u952e\u53ef\u4ee5\u5f00\u5173\u6b64\u529f\u80fd", "\u4e00\u6b21\u6027\u4ed8\u8d39"};
    private static String[] l = new String[]{"\u8d2d\u4e70", "\u8fd4\u56de"};
    private b.b.i m = null;
    private b n = null;

    public i() {
        super("\u670d\u52a1");
    }

    public final void a(int n2, int n3) {
        block0 : switch (n2) {
            case -7: {
                this.d(1);
                break;
            }
            case 50: {
                this.m.a();
                this.c();
                break;
            }
            case 56: {
                this.m.b();
                this.c();
                break;
            }
            case 53: {
                i i2 = this;
                int n4 = i2.m.c();
                Object object = b.b.g.a(l, (byte)0, (byte)1, true);
                if (((m)object).k() == 1) {
                    if (((String)(object = ((g)object).a())).equals("\u8d2d\u4e70")) {
                        int n5 = 0;
                        switch (n4) {
                            case 0: {
                                n5 = 3;
                                break;
                            }
                            case 1: {
                                if (a.f.l()) {
                                    b.b.g.a("\u60a8\u5df2\u7ecf\u8d2d\u4e70\u4e86\u53cc\u500d\u91d1\u94b1", 3000L).k();
                                    break block0;
                                }
                                n5 = 1;
                                break;
                            }
                            case 2: {
                                if (a.f.m()) {
                                    b.b.g.a("\u60a8\u5df2\u7ecf\u8d2d\u4e70\u4e86\u53cc\u500d\u7ecf\u9a8c", 3000L).k();
                                    break block0;
                                }
                                n5 = 7;
                                break;
                            }
                            case 3: {
                                if (a.f.w()) {
                                    if (a.f.a = !a.f.a) {
                                        b.b.g.a("\u5f00\u542f\u4e86\u4e0d\u9047\u654c\u529f\u80fd!", 3000L).k();
                                        break block0;
                                    }
                                    b.b.g.a("\u5173\u95ed\u4e86\u4e0d\u9047\u654c\u529f\u80fd!", 3000L).k();
                                    break block0;
                                }
                                n5 = 8;
                                break;
                            }
                            case 4: {
                                n5 = 2;
                                break;
                            }
                            default: {
                                break block0;
                            }
                        }
                        boolean bl = false;
                        bl = a.f.t() ? true : c.i.a(d[n4], n5, 0);
                        if (bl) {
                            // empty if block
                        }
                        b.b.g.a("\u8d2d\u4e70\u6210\u529f!", 3000L).k();
                        a.f.a(1001, 2);
                        switch (n4) {
                            case 0: {
                                a.f.C += 3000;
                                break;
                            }
                            case 1: {
                                a.f.m(2);
                                break;
                            }
                            case 2: {
                                a.f.n(2);
                                break;
                            }
                            case 3: {
                                a.f.c(true);
                                a.f.a = true;
                                b.b.g.a("\u63090\u952e\u53ef\u4ee5\u5f00\u5173\u6b64\u529f\u80fd!", 3000L).k();
                                break;
                            }
                            case 4: {
                                a.f.b(true);
                                a.f.a(1001, 18);
                            }
                        }
                        a.f.o();
                        g.a.a(String.valueOf(n5), 0);
                        break;
                    }
                    b.b.g.a("\u591a\u597d\u7684\u4e1c\u897f,\u4e0d\u4e70\u771f\u662f\u53ef\u60dc!", 3000L).k();
                    break;
                }
                b.b.g.a("\u591a\u597d\u7684\u4e1c\u897f,\u4e0d\u4e70\u771f\u662f\u53ef\u60dc!", 3000L).k();
            }
        }
        switch (n3) {
            case 51: {
                this.n.a();
                return;
            }
            case 57: {
                this.n.c();
            }
        }
    }

    private void c() {
        int n2 = this.m.c();
        if (n2 < 0 || n2 >= c.length) {
            return;
        }
        this.n.d();
        this.n.a(c[n2] + "\u8d60\u90012\u4e2a\u5143\u5b9d(\u7528\u4e8e\u6362\u9ad8\u7ea7\u88c5\u5907),\u8bf7\u8bb0\u5f97\u5b58\u6863!");
    }

    protected final void a() {
        boolean bl = false;
        this.m = new b.b.i();
        this.m.b = 0;
        this.m.c = 0;
        this.m.d = this.h;
        this.m.e = this.i - b.b.b.a - 4;
        i i2 = this;
        i2.m.a("\u5151\u6362\u91d1\u94b1", "2\u5143");
        i2.m.a("\u53cc\u500d\u91d1\u94b1", "2\u5143");
        i2.m.a("\u53cc\u500d\u7ecf\u9a8c", "2\u5143");
        i2.m.a("\u4e0d\u9047\u654c", "2\u5143");
        i2.m.a("\u4e00\u6b21\u6027\u4ed8\u8d39", "10\u5143");
        this.a(this.m);
        int n2 = 0 + this.m.e;
        this.c(n2);
        this.n = new b();
        this.n.b = 0;
        this.n.c = n2 += 4;
        this.n.d = this.h;
        this.n.e = b.b.b.a;
        this.a(this.n);
        this.c();
    }

    public final void b() {
        this.n.b();
    }

    public static boolean a(String string, int n2, int n3) {
        if (a.f.t()) {
            return true;
        }
        System.gc();
        b.e.a.a(200L);
        n3 = n2;
        n3 = n3 == 2 ? 1000 : (n3 == 12 ? 400 : 200);
        g.a.a = null;
        boolean bl = g.a.a(MainMIDlet.a, (Displayable)main.a.a, string, "0117", String.valueOf(n2), String.valueOf(0), n3, false);
        if (bl) {
            g.a.a(String.valueOf(n2), 0);
        }
        g.a.a = null;
        return bl;
    }

    public static void a(int n2) {
        if (!c.i.b(n2)) {
            a.addElement(new Integer(n2));
        }
    }

    public static boolean b(int n2) {
        Integer n3 = null;
        for (int i2 = a.size() - 1; i2 >= 0; --i2) {
            n3 = (Integer)a.elementAt(i2);
            if (n3 != n2) continue;
            return true;
        }
        return false;
    }
}

