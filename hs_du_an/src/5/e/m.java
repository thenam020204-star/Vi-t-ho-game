/*
 * Decompiled with CFR 0.152.
 */
package e;

import a.a.c;
import a.a.g;
import a.a.o;
import c.l;
import e.e;
import e.i;
import main.a;

public final class m
extends g {
    private static String[] h = new String[]{"\u8fd4\u56de", "\u8fd4\u56de\u6807\u9898"};
    private c i = null;

    public m() {
        super("\u8bbe\u7f6e");
        this.g = 1;
    }

    protected final void a() {
        this.i = new c();
        this.i.b = 0;
        this.i.c = 0;
        this.i.d = this.d;
        this.i.e = this.e;
        m m2 = this;
        m2.i.a("\u5b58\u6863", "");
        m2.i.a("\u8bfb\u53d6", "");
        if (b.a.m == 0) {
            m2.i.a("\u97f3\u4e50\u72b6\u6001", "< \u5173 >");
        } else {
            m2.i.a("\u97f3\u4e50\u72b6\u6001", "< " + b.a.m / 20 + " >");
        }
        m2.i.a("\u7b80\u5355\u6218\u6597", b.a.l[1] ? "\u5f00" : "\u5173");
        m2.i.a("\u52a8\u6001\u5730\u56fe", b.a.l[2] ? "\u5f00" : "\u5173");
        m2.i.a("\u83dc\u5355\u6548\u679c", b.a.l[3] ? "\u5f00" : "\u5173");
        m2.i.a("\u8fd4\u56de\u6807\u9898", "");
        this.a(this.i);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void a(int n2, int n3) {
        switch (n2) {
            case 50: {
                this.i.a();
                return;
            }
            case 56: {
                this.i.b();
                return;
            }
            case 52: 
            case 54: {
                this.c(n2);
                return;
            }
            case 53: {
                n2 = this.i.c();
                switch (n2) {
                    case 0: {
                        if (b.a.w != null && !b.a.w.h) {
                            a.a.l.a("\u6b64\u5730\u56fe\u65e0\u6cd5\u5b58\u6863!", 3000L).k();
                            return;
                        }
                        int n4 = (main.a.d - main.a.h) / 2;
                        n2 = (main.a.e - main.a.i) / 2;
                        o o2 = new o(n4, n2, main.a.h, main.a.i);
                        i i2 = new i();
                        o2.a(i2);
                        o2.k();
                        return;
                    }
                    case 1: {
                        n2 = (main.a.d - main.a.h) / 2;
                        n3 = (main.a.e - main.a.i) / 2;
                        o o3 = new o(n2, n3, main.a.h, main.a.i);
                        e e2 = new e();
                        new e().g = (byte)2;
                        o3.a(e2);
                        int n5 = o3.k();
                        this.a.b(1);
                        if (n5 != 1) return;
                        main.a.a.k.a(new l());
                        return;
                    }
                    case 2: {
                        this.c(54);
                        return;
                    }
                    case 3: {
                        b.a.l[1] = !b.a.l[1];
                        this.i.a(3, "\u7b80\u5355\u6218\u6597", b.a.l[1] ? "\u5f00" : "\u5173");
                        return;
                    }
                    case 4: {
                        b.a.l[2] = !b.a.l[2];
                        this.i.a(4, "\u52a8\u6001\u5730\u56fe", b.a.l[2] ? "\u5f00" : "\u5173");
                        return;
                    }
                    case 5: {
                        b.a.l[3] = !b.a.l[3];
                        this.i.a(5, "\u83dc\u5355\u6548\u679c", b.a.l[3] ? "\u5f00" : "\u5173");
                        return;
                    }
                    case 6: {
                        Object object = a.a.l.a(h, (byte)0, (byte)2, true);
                        n3 = ((a.a.m)object).k();
                        if (n3 != 1 || !((String)(object = ((a.a.l)object).c())).equals("\u8fd4\u56de\u6807\u9898")) return;
                        this.a.b(1);
                        main.a.a.k.a(new c.e());
                    }
                }
                return;
            }
        }
    }

    private void c(int n2) {
        int n3 = this.i.c();
        if (n3 != 2) {
            return;
        }
        switch (n2) {
            case 52: {
                b.a.b((byte)(b.a.m - 20));
                break;
            }
            case 54: {
                b.a.b((byte)(b.a.m + 20));
                break;
            }
            default: {
                return;
            }
        }
        if (b.a.m <= 0) {
            this.i.a(2, "\u97f3\u4e50\u72b6\u6001", "< \u5173 >");
            return;
        }
        this.i.a(2, "\u97f3\u4e50\u72b6\u6001", "< " + b.a.m / 20 + " >");
    }

    public final void b() {
    }

    static {
        String[] stringArray = new String[]{"\u8fd4\u56de", "\u4fdd\u5b58"};
    }
}

