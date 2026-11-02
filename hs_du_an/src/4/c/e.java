/*
 * Decompiled with CFR 0.152.
 */
package c;

import a.c;
import a.f;
import b.b.g;
import b.b.l;
import b.b.m;
import b.b.o;
import d.j;
import main.a;

public final class e
extends l {
    private o a = null;
    private c[] b = null;
    private static String[] c = new String[]{"\u8fd4\u56de", "\u8bfb\u53d6"};

    public e() {
        super("");
    }

    public final void a(int n2, int n3) {
        switch (n2) {
            case 50: {
                this.a.a();
                return;
            }
            case 56: {
                this.a.b();
                return;
            }
            case 53: {
                Object object;
                n2 = this.a.c();
                if (this.b[n2] == null || ((m)(object = b.b.g.a(c, (byte)0, (byte)1, true))).k() != 1 || !((String)(object = ((g)object).a())).equals("\u8bfb\u53d6")) break;
                a.f.w = null;
                if (a.f.t()) {
                    main.a.a.k.a(new j());
                } else {
                    b.b.g.a("\u4f7f\u7528\u4e00\u6b21\u6027\u4ed8\u8d39,\n\u6e38\u620f\u4e2d\u4e0d\u518d\u9700\u8981\u6263\u8d39,\u8d85\u5f3a\u88c5\u5907,\u91d1\u94b1\u5b8c\u5168\u514d\u8d39\u7528,\n\u8fd8\u6709\u66f4\u591a\u60ca\u559c\u7b49\u7740\u4f60!\n\u6309\u53f3\u952e\u79d8\u7c4d\u83dc\u5355\u53ef\u67e5\u770b", -1L).k();
                    main.a.a.k.a(new j());
                }
                this.b[n2].d();
                this.e.a(1);
            }
        }
    }

    protected final void a() {
        this.b = a.f.g();
        this.a = new o(this.b);
        this.a.b = 0;
        this.a.c = 0;
        this.a.d = this.h;
        this.a.e = this.i;
        this.a(this.a);
    }

    public final void b() {
    }
}

