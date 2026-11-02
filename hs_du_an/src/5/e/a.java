/*
 * Decompiled with CFR 0.152.
 */
package e;

import a.a.c;
import a.a.g;
import a.a.l;
import a.a.m;
import c.e;

public final class a
extends g {
    private static String[] h = new String[]{"\u8fd4\u56de", "\u8fd4\u56de\u6807\u9898"};
    private c i = null;

    public a() {
        super("\u8bbe\u7f6e");
        this.g = 1;
    }

    protected final void a() {
        this.i = new c();
        this.i.b = 0;
        this.i.c = 0;
        this.i.d = this.d;
        this.i.e = this.e;
        a a2 = this;
        if (b.a.m <= 0) {
            a2.i.a("\u97f3\u4e50\u72b6\u6001", "< \u5173 >");
        } else {
            a2.i.a("\u97f3\u4e50\u72b6\u6001", "< " + b.a.m / 20 + " >");
        }
        a2.i.a("\u7b80\u5355\u6218\u6597", b.a.l[1] ? "\u5f00" : "\u5173");
        a2.i.a("\u52a8\u6001\u5730\u56fe", b.a.l[2] ? "\u5f00" : "\u5173");
        a2.i.a("\u83dc\u5355\u6548\u679c", b.a.l[3] ? "\u5f00" : "\u5173");
        a2.i.a("\u8fd4\u56de\u6807\u9898", "");
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
                        this.c(54);
                        return;
                    }
                    case 1: {
                        b.a.l[1] = !b.a.l[1];
                        this.i.a(n2, "\u7b80\u5355\u6218\u6597", b.a.l[1] ? "\u5f00" : "\u5173");
                        return;
                    }
                    case 2: {
                        b.a.l[2] = !b.a.l[2];
                        this.i.a(n2, "\u52a8\u6001\u5730\u56fe", b.a.l[2] ? "\u5f00" : "\u5173");
                        return;
                    }
                    case 3: {
                        b.a.l[3] = !b.a.l[3];
                        this.i.a(n2, "\u83dc\u5355\u6548\u679c", b.a.l[3] ? "\u5f00" : "\u5173");
                        return;
                    }
                    case 4: {
                        Object object = l.a(h, (byte)0, (byte)2, true);
                        n3 = ((m)object).k();
                        if (n3 != 1 || !((String)(object = ((l)object).c())).equals("\u8fd4\u56de\u6807\u9898")) return;
                        this.a.b(1);
                        main.a.a.k.a(new e());
                    }
                }
                return;
            }
        }
    }

    private void c(int n2) {
        int n3 = this.i.c();
        if (n3 != 0) {
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
        String string = "< " + b.a.m / 20;
        if (b.a.m == 0) {
            string = "< \u5173";
        }
        string = string + " >";
        this.i.a(0, "\u97f3\u4e50\u72b6\u6001", string);
    }

    public final void b() {
    }

    static {
        String[] stringArray = new String[]{"\u8fd4\u56de", "\u4fdd\u5b58"};
    }
}

