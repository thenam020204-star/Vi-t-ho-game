/*
 * Decompiled with CFR 0.152.
 */
package e;

import a.a.g;
import a.a.m;
import c.l;
import main.a;

public final class e
extends g {
    private a.a.e h = null;
    private b.g[] i = null;
    private static String[] j = new String[]{"\u8fd4\u56de", "\u8bfb\u53d6"};

    public e() {
        super("");
    }

    public final void a(int n2, int n3) {
        switch (n2) {
            case 50: {
                this.h.a();
                return;
            }
            case 56: {
                this.h.b();
                return;
            }
            case 53: {
                Object object;
                n2 = this.h.c();
                if (this.i[n2] == null || ((m)(object = a.a.l.a(j, (byte)0, (byte)1, true))).k() != 1 || !((String)(object = ((a.a.l)object).c())).equals("\u8bfb\u53d6")) break;
                b.a.w = null;
                if (b.a.t()) {
                    main.a.a.k.a(new l());
                } else {
                    a.a.l.a("\u4f7f\u7528\u4e00\u6b21\u6027\u4ed8\u8d39,\n\u6e38\u620f\u4e2d\u4e0d\u518d\u9700\u8981\u6263\u8d39,\u8d85\u5f3a\u88c5\u5907,\u91d1\u94b1\u5b8c\u5168\u514d\u8d39\u7528,\n\u8fd8\u6709\u66f4\u591a\u60ca\u559c\u7b49\u7740\u4f60!\n\u6309\u53f3\u952e\u79d8\u7c4d\u83dc\u5355\u53ef\u67e5\u770b", -1L).k();
                    main.a.a.k.a(new l());
                }
                this.i[n2].d();
                this.a.b(1);
            }
        }
    }

    protected final void a() {
        this.i = b.a.g();
        this.h = new a.a.e(this.i);
        this.h.b = 0;
        this.h.c = 0;
        this.h.d = this.d;
        this.h.e = this.e;
        this.a(this.h);
    }

    public final void b() {
    }
}

