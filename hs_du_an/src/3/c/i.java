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
import javax.microedition.lcdui.Displayable;
import main.MainMIDlet;
import main.a;

public final class i
extends l {
    public static final int[] a = new int[]{4, 5, 9, 10, 13, 14, 15, 16, 19, 36};
    public static final String[] b = new String[]{"\u5151\u636230000\u91d1\u94b1", "\u8d2d\u4e70\u6c38\u4e45\u4e24\u500d\u91d1\u94b1(\u6240\u6709\u5b58\u6863)", "\u8d2d\u4e70\u6c38\u4e45\u4e24\u500d\u7ecf\u9a8c(\u6240\u6709\u5b58\u6863)", "\u4e0d\u9047\u654c,\u8d2d\u4e70\u540e\u63090\u952e\u53ef\u4ee5\u5f00\u5173\u6b64\u529f\u80fd", "\u4e00\u6b21\u6027\u6fc0\u6d3b,\u6e38\u620f\u4e2d\u53ef\u4efb\u610f\u4f7f\u7528\u6e38\u620f\u4e2d\u6240\u6709\u589e\u503c\u670d\u52a1."};
    public static final String[] c = new String[]{"\u5151\u6362\u91d1\u94b1", "\u4e24\u500d\u91d1\u94b1", "\u4e24\u500d\u7ecf\u9a8c", "\u4e0d\u9047\u654c,\u8d2d\u4e70\u540e\u63090\u952e\u53ef\u4ee5\u5f00\u5173\u6b64\u529f\u80fd", "\u4e00\u6b21\u6027\u6fc0\u6d3b"};
    private static String[] k = new String[]{"\u8d2d\u4e70", "\u8fd4\u56de"};
    private b.b.i l = null;
    private b m = null;

    public i() {
        super("\u670d\u52a1");
    }

    public final void a(int n2, int n3) {
        block0 : switch (n2) {
            case -7: {
                this.b(1);
                break;
            }
            case 50: {
                this.l.a();
                this.c();
                break;
            }
            case 56: {
                this.l.b();
                this.c();
                break;
            }
            case 53: {
                i i2 = this;
                int n4 = i2.l.c();
                Object object = b.b.g.a(k, (byte)0, (byte)1, true);
                if (((m)object).k() == 1) {
                    if (((String)(object = ((g)object).a())).equals("\u8d2d\u4e70")) {
                        int n5 = 0;
                        int n6 = 0;
                        switch (n4) {
                            case 0: {
                                n6 = 3;
                                n5 = 200;
                                break;
                            }
                            case 1: {
                                if (a.f.l()) {
                                    b.b.g.a("\u60a8\u5df2\u7ecf\u8d2d\u4e70\u4e86\u53cc\u500d\u91d1\u94b1", 3000L).k();
                                    break block0;
                                }
                                n6 = 1;
                                n5 = 200;
                                break;
                            }
                            case 2: {
                                if (a.f.m()) {
                                    b.b.g.a("\u60a8\u5df2\u7ecf\u8d2d\u4e70\u4e86\u53cc\u500d\u7ecf\u9a8c", 3000L).k();
                                    break block0;
                                }
                                n6 = 7;
                                n5 = 200;
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
                                n6 = 8;
                                n5 = 200;
                                break;
                            }
                            case 4: {
                                n6 = 2;
                                n5 = 1000;
                                break;
                            }
                            default: {
                                break block0;
                            }
                        }
                        boolean bl = false;
                        if (a.f.t()) {
                            bl = true;
                        } else {
                            g.a.a = null;
                            bl = g.a.a(MainMIDlet.a, (Displayable)main.a.a, c[n4], "0110", String.valueOf(n6), "0", n5, false);
                        }
                        if (bl) {
                            b.b.g.a("[\u7531yc\u6fc0\u6d3b\u5206\u4eab]", 3000L).k();
                            a.f.a(1001, 2);
                            switch (n4) {
                                case 0: {
                                    a.f.C += 30000;
                                    break;
                                }
                                case 1: {
                                    a.f.l(2);
                                    break;
                                }
                                case 2: {
                                    a.f.m(2);
                                    break;
                                }
                                case 3: {
                                    a.f.d(true);
                                    a.f.a = true;
                                    b.b.g.a("\u63090\u952e\u53ef\u4ee5\u5f00\u5173\u6b64\u529f\u80fd!", 3000L).k();
                                    break;
                                }
                                case 4: {
                                    a.f.c(true);
                                    a.f.a(1001, 18);
                                }
                            }
                            a.f.o();
                            g.a.a(String.valueOf(n6), 0);
                            break;
                        }
                        b.b.g.a("\u591a\u597d\u7684\u4e1c\u897f,\u4e0d\u4e70\u771f\u662f\u53ef\u60dc!", 3000L).k();
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
                this.m.a();
                return;
            }
            case 57: {
                this.m.c();
            }
        }
    }

    private void c() {
        int n2 = this.l.c();
        if (n2 < 0 || n2 >= b.length) {
            return;
        }
        this.m.d();
        this.m.a(b[n2] + "\u8d60\u90012\u4e2a\u5143\u5b9d(\u7528\u4e8e\u6362\u9ad8\u7ea7\u88c5\u5907),\u8bf7\u8bb0\u5f97\u5b58\u6863!");
    }

    protected final void a() {
        boolean bl = false;
        this.l = new b.b.i();
        this.l.b = 0;
        this.l.c = 0;
        this.l.d = this.g;
        this.l.e = this.h - b.b.b.a - 4;
        i i2 = this;
        i2.l.a("\u5151\u6362\u91d1\u94b1", "\u3000");
        i2.l.a("\u53cc\u500d\u91d1\u94b1", "\u3000");
        i2.l.a("\u53cc\u500d\u7ecf\u9a8c", "\u3000");
        i2.l.a("\u4e0d\u9047\u654c", "\u3000");
        i2.l.a("\u4e00\u6b21\u6027\u6fc0\u6d3b", "\u3000");
        this.a(this.l);
        int n2 = 0 + this.l.e;
        this.a(n2);
        this.m = new b();
        this.m.b = 0;
        this.m.c = n2 += 4;
        this.m.d = this.g;
        this.m.e = b.b.b.a;
        this.a(this.m);
        this.c();
    }

    public final void b() {
        this.m.b();
    }
}

