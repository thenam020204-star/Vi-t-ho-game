/*
 * Decompiled with CFR 0.152.
 */
package c;

import a.f;
import b.b.g;
import b.b.i;
import b.b.l;
import b.b.m;
import d.h;

public final class a
extends l {
    private static String[] a = new String[]{"\u8fd4\u56de", "\u8fd4\u56de\u6807\u9898"};
    private i b = null;

    public a() {
        super("\u8bbe\u7f6e");
        this.k = 1;
    }

    protected final void a() {
        this.b = new i();
        this.b.b = 0;
        this.b.c = 0;
        this.b.d = this.h;
        this.b.e = this.i;
        a a2 = this;
        if (a.f.m <= 0) {
            a2.b.a("\u97f3\u4e50\u72b6\u6001", "< \u5173 >");
        } else {
            a2.b.a("\u97f3\u4e50\u72b6\u6001", "< " + a.f.m / 20 + " >");
        }
        a2.b.a("\u7b80\u5355\u6218\u6597", a.f.l[1] ? "\u5f00" : "\u5173");
        a2.b.a("\u52a8\u6001\u5730\u56fe", a.f.l[2] ? "\u5f00" : "\u5173");
        a2.b.a("\u83dc\u5355\u6548\u679c", a.f.l[3] ? "\u5f00" : "\u5173");
        a2.b.a("\u8fd4\u56de\u6807\u9898", "");
        this.a(this.b);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void a(int n2, int n3) {
        switch (n2) {
            case 50: {
                this.b.a();
                return;
            }
            case 56: {
                this.b.b();
                return;
            }
            case 52: 
            case 54: {
                this.a(n2);
                return;
            }
            case 53: {
                n2 = this.b.c();
                switch (n2) {
                    case 0: {
                        this.a(54);
                        return;
                    }
                    case 1: {
                        a.f.l[1] = !a.f.l[1];
                        this.b.a(n2, "\u7b80\u5355\u6218\u6597", a.f.l[1] ? "\u5f00" : "\u5173");
                        return;
                    }
                    case 2: {
                        a.f.l[2] = !a.f.l[2];
                        this.b.a(n2, "\u52a8\u6001\u5730\u56fe", a.f.l[2] ? "\u5f00" : "\u5173");
                        return;
                    }
                    case 3: {
                        a.f.l[3] = !a.f.l[3];
                        this.b.a(n2, "\u83dc\u5355\u6548\u679c", a.f.l[3] ? "\u5f00" : "\u5173");
                        return;
                    }
                    case 4: {
                        Object object = b.b.g.a(a, (byte)0, (byte)2, true);
                        n3 = ((m)object).k();
                        if (n3 != 1 || !((String)(object = ((g)object).a())).equals("\u8fd4\u56de\u6807\u9898")) return;
                        this.e.a(1);
                        main.a.a.k.a(new h());
                    }
                }
                return;
            }
        }
    }

    private void a(int n2) {
        int n3 = this.b.c();
        if (n3 != 0) {
            return;
        }
        switch (n2) {
            case 52: {
                a.f.b((byte)(a.f.m - 20));
                break;
            }
            case 54: {
                a.f.b((byte)(a.f.m + 20));
                break;
            }
            default: {
                return;
            }
        }
        String string = "< " + a.f.m / 20;
        if (a.f.m == 0) {
            string = "< \u5173";
        }
        string = string + " >";
        this.b.a(0, "\u97f3\u4e50\u72b6\u6001", string);
    }

    public final void b() {
    }

    static {
        String[] stringArray = new String[]{"\u8fd4\u56de", "\u4fdd\u5b58"};
    }
}

