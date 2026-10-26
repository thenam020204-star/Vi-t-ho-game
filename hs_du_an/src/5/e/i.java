/*
 * Decompiled with CFR 0.152.
 */
package e;

import a.a.e;
import a.a.g;
import a.a.l;
import a.a.m;
import b.a;

public final class i
extends g {
    private e h = null;
    private b.g[] i = null;
    private static String[] j = new String[]{"\u8fd4\u56de", "\u8986\u76d6"};

    public i() {
        super("");
    }

    protected final void a() {
        this.i = b.a.g();
        this.h = new e(this.i);
        this.h.b = 0;
        this.h.c = 0;
        this.h.d = this.d;
        this.h.e = this.e;
        this.a(this.h);
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
                n2 = this.h.c();
                if (this.i[n2] == null) {
                    b.a.a(n2);
                    this.c();
                    return;
                }
                Object object = l.a(j, (byte)0, (byte)1, true);
                if (((m)object).k() != 1 || !((String)(object = ((l)object).c())).equals("\u8986\u76d6")) break;
                b.a.a(n2);
                this.c();
            }
        }
    }

    public final void b() {
    }

    private void c() {
        this.i = b.a.g();
        this.h.a(this.i);
    }
}

