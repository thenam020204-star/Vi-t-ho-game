/*
 * Decompiled with CFR 0.152.
 */
package c;

import b.b.g;
import b.b.h;
import b.b.i;
import b.b.l;
import c.e;
import c.m;
import d.j;
import main.a;

public final class f
extends l {
    private static String[] a = new String[]{"\u8fd4\u56de", "\u8fd4\u56de\u6807\u9898"};
    private i b = null;

    public f() {
        super("\u8bbe\u7f6e");
        this.j = 1;
    }

    protected final void a() {
        this.b = new i();
        this.b.b = 0;
        this.b.c = 0;
        this.b.d = this.g;
        this.b.e = this.h;
        f f2 = this;
        f2.b.a("\u5b58\u6863", "");
        f2.b.a("\u8bfb\u53d6", "");
        f2.b.a("\u97f3\u4e50\u72b6\u6001", a.f.m[0] ? "\u5f00" : "\u5173");
        f2.b.a("\u7b80\u5355\u6218\u6597", a.f.m[1] ? "\u5f00" : "\u5173");
        f2.b.a("\u52a8\u6001\u5730\u56fe", a.f.m[2] ? "\u5f00" : "\u5173");
        f2.b.a("\u8fd4\u56de\u6807\u9898", "");
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
            case 53: {
                n2 = this.b.c();
                switch (n2) {
                    case 0: {
                        if (a.f.w != null && !a.f.w.h) {
                            b.b.g.a("\u6b64\u5730\u56fe\u65e0\u6cd5\u5b58\u6863!", 3000L).k();
                            return;
                        }
                        int n4 = (main.a.d - main.a.h) / 2;
                        n2 = (main.a.e - main.a.i) / 2;
                        h h2 = new h(n4, n2, main.a.h, main.a.i);
                        m m2 = new m();
                        h2.a(m2);
                        h2.k();
                        return;
                    }
                    case 1: {
                        n2 = (main.a.d - main.a.h) / 2;
                        n3 = (main.a.e - main.a.i) / 2;
                        h h3 = new h(n2, n3, main.a.h, main.a.i);
                        e e2 = new e();
                        new e().j = (byte)2;
                        h3.a(e2);
                        int n5 = h3.k();
                        this.d.a(1);
                        if (n5 != 1) return;
                        main.a.a.k.a(new j());
                        return;
                    }
                    case 2: {
                        boolean bl = a.f.m[0] = !a.f.m[0];
                        if (a.f.m[0] && a.f.ap == -1) {
                            a.f.d(a.f.ao, -1);
                        } else {
                            a.f.j();
                        }
                        this.b.a(2, "\u97f3\u4e50\u72b6\u6001", a.f.m[0] ? "\u5f00" : "\u5173");
                        return;
                    }
                    case 3: {
                        a.f.m[1] = !a.f.m[1];
                        this.b.a(3, "\u7b80\u5355\u6218\u6597", a.f.m[1] ? "\u5f00" : "\u5173");
                        return;
                    }
                    case 4: {
                        a.f.m[2] = !a.f.m[2];
                        this.b.a(4, "\u52a8\u6001\u5730\u56fe", a.f.m[2] ? "\u5f00" : "\u5173");
                        return;
                    }
                    case 5: {
                        Object object = b.b.g.a(a, (byte)0, (byte)2, true);
                        n3 = ((b.b.m)object).k();
                        if (n3 != 1 || !((String)(object = ((g)object).a())).equals("\u8fd4\u56de\u6807\u9898")) return;
                        this.d.a(1);
                        main.a.a.k.a(new d.h());
                    }
                }
                return;
            }
        }
    }

    public final void b() {
    }

    static {
        String[] stringArray = new String[]{"\u8fd4\u56de", "\u4fdd\u5b58"};
    }
}

