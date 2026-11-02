/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Displayable
 */
package e;

import a.a.c;
import a.a.g;
import a.a.m;
import a.c.b;
import java.util.Vector;
import javax.microedition.lcdui.Displayable;
import main.MainMIDlet;
import main.a;

public final class l
extends g {
    public static Vector h = new Vector();
    public static final int[] i = new int[]{4, 5, 9, 10, 95, 13, 14, 15, 16, 19, 36};
    public static final String[] j = new String[]{"\u5151\u63623000\u91d1\u94b1", "\u8d2d\u4e70\u6c38\u4e45\u4e24\u500d\u91d1\u94b1(\u6240\u6709\u5b58\u6863)", "\u8d2d\u4e70\u6c38\u4e45\u4e24\u500d\u7ecf\u9a8c(\u6240\u6709\u5b58\u6863)", "\u4e0d\u9047\u654c,\u8d2d\u4e70\u540e\u63090\u952e\u53ef\u4ee5\u5f00\u5173\u6b64\u529f\u80fd", "\u4e00\u6b21\u6027\u4ed8\u8d39,\u6e38\u620f\u4e2d\u4e0d\u518d\u9700\u8981\u6263\u8d39,\u53ef\u4efb\u610f\u4f7f\u7528\u6e38\u620f\u4e2d\u6240\u6709\u589e\u503c\u670d\u52a1."};
    public static final String[] k = new String[]{"\u5151\u6362\u91d1\u94b1", "\u4e24\u500d\u91d1\u94b1", "\u4e24\u500d\u7ecf\u9a8c", "\u4e0d\u9047\u654c,\u8d2d\u4e70\u540e\u63090\u952e\u53ef\u4ee5\u5f00\u5173\u6b64\u529f\u80fd", "\u4e00\u6b21\u6027\u4ed8\u8d39"};
    private static String[] l = new String[]{"\u8d2d\u4e70", "\u8fd4\u56de"};
    private c m = null;
    private a.a.b n = null;

    public l() {
        super("\u670d\u52a1");
    }

    public final void a(int n2, int n3) {
        block0 : switch (n2) {
            case -7: {
                this.b(1);
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
                l l2 = this;
                int n4 = l2.m.c();
                Object object = a.a.l.a(l, (byte)0, (byte)1, true);
                if (((m)object).k() == 1) {
                    if (((String)(object = ((a.a.l)object).c())).equals("\u8d2d\u4e70")) {
                        int n5 = 0;
                        switch (n4) {
                            case 0: {
                                n5 = 3;
                                break;
                            }
                            case 1: {
                                if (b.a.l()) {
                                    a.a.l.a("\u60a8\u5df2\u7ecf\u8d2d\u4e70\u4e86\u53cc\u500d\u91d1\u94b1", 3000L).k();
                                    break block0;
                                }
                                n5 = 1;
                                break;
                            }
                            case 2: {
                                if (b.a.m()) {
                                    a.a.l.a("\u60a8\u5df2\u7ecf\u8d2d\u4e70\u4e86\u53cc\u500d\u7ecf\u9a8c", 3000L).k();
                                    break block0;
                                }
                                n5 = 7;
                                break;
                            }
                            case 3: {
                                if (b.a.w()) {
                                    if (b.a.a = !b.a.a) {
                                        a.a.l.a("\u5f00\u542f\u4e86\u4e0d\u9047\u654c\u529f\u80fd!", 3000L).k();
                                        break block0;
                                    }
                                    a.a.l.a("\u5173\u95ed\u4e86\u4e0d\u9047\u654c\u529f\u80fd!", 3000L).k();
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
                        bl = b.a.t() ? true : e.l.a(k[n4], n5, 0);
                        if (bl) {
                            a.a.l.a("[wap.91soyo.com\u6fc0\u6d3b]", 3000L).k();
                            b.a.a(1001, 2);
                            switch (n4) {
                                case 0: {
                                    b.a.C += 3000;
                                    break;
                                }
                                case 1: {
                                    b.a.m(2);
                                    break;
                                }
                                case 2: {
                                    b.a.n(2);
                                    break;
                                }
                                case 3: {
                                    b.a.c(true);
                                    b.a.a = true;
                                    a.a.l.a("\u63090\u952e\u53ef\u4ee5\u5f00\u5173\u6b64\u529f\u80fd!", 3000L).k();
                                    break;
                                }
                                case 4: {
                                    b.a.b(true);
                                    b.a.a(1001, 18);
                                }
                            }
                            b.a.o();
                            d.b.a(String.valueOf(n5), 0);
                            break;
                        }
                        a.a.l.a("\u591a\u597d\u7684\u4e1c\u897f,\u4e0d\u4e70\u771f\u662f\u53ef\u60dc!", 3000L).k();
                        break;
                    }
                    a.a.l.a("\u591a\u597d\u7684\u4e1c\u897f,\u4e0d\u4e70\u771f\u662f\u53ef\u60dc!", 3000L).k();
                    break;
                }
                a.a.l.a("\u591a\u597d\u7684\u4e1c\u897f,\u4e0d\u4e70\u771f\u662f\u53ef\u60dc!", 3000L).k();
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
        if (n2 < 0 || n2 >= j.length) {
            return;
        }
        this.n.d();
        this.n.a(j[n2] + "\u8d60\u90012\u4e2a\u5143\u5b9d(\u7528\u4e8e\u6362\u9ad8\u7ea7\u88c5\u5907),\u8bf7\u8bb0\u5f97\u5b58\u6863!");
    }

    protected final void a() {
        boolean bl = false;
        this.m = new c();
        this.m.b = 0;
        this.m.c = 0;
        this.m.d = this.d;
        this.m.e = this.e - a.a.b.a - 4;
        l l2 = this;
        l2.m.a("\u5151\u6362\u91d1\u94b1", "2\u5143");
        l2.m.a("\u53cc\u500d\u91d1\u94b1", "2\u5143");
        l2.m.a("\u53cc\u500d\u7ecf\u9a8c", "2\u5143");
        l2.m.a("\u4e0d\u9047\u654c", "2\u5143");
        l2.m.a("\u4e00\u6b21\u6027\u4ed8\u8d39", "10\u5143");
        this.a(this.m);
        int n2 = 0 + this.m.e;
        this.a(n2);
        this.n = new a.a.b();
        this.n.b = 0;
        this.n.c = n2 += 4;
        this.n.d = this.d;
        this.n.e = a.a.b.a;
        this.a(this.n);
        this.c();
    }

    public final void b() {
        this.n.b();
    }

    public static boolean a(String string, int n2, int n3) {
        if (b.a.t()) {
            return true;
        }
        System.gc();
        a.c.b.a(200L);
        n3 = n2;
        n3 = n3 == 2 ? 1000 : (n3 == 12 ? 400 : 200);
        d.b.b = null;
        boolean bl = d.b.a(MainMIDlet.a, (Displayable)main.a.a, string, "0118", String.valueOf(n2), String.valueOf(0), n3, false);
        if (bl) {
            d.b.a(String.valueOf(n2), 0);
        }
        d.b.b = null;
        return bl;
    }

    public static void c(int n2) {
        if (!e.l.d(n2)) {
            h.addElement(new Integer(n2));
        }
    }

    public static boolean d(int n2) {
        Integer n3 = null;
        for (int i2 = h.size() - 1; i2 >= 0; --i2) {
            n3 = (Integer)h.elementAt(i2);
            if (n3 != n2) continue;
            return true;
        }
        return false;
    }
}

