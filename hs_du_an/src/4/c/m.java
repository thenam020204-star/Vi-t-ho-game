/*
 * Decompiled with CFR 0.152.
 */
package c;

import a.c;
import a.f;
import b.b.g;
import b.b.l;
import b.b.o;

public final class m
extends l {
    private o a = null;
    private c[] b = null;
    private static String[] c = new String[]{"\u8fd4\u56de", "\u8986\u76d6"};

    public m() {
        super("");
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
                n2 = this.a.c();
                if (this.b[n2] == null) {
                    a.f.a(n2);
                    this.c();
                    return;
                }
                Object object = b.b.g.a(c, (byte)0, (byte)1, true);
                if (((b.b.m)object).k() != 1 || !((String)(object = ((g)object).a())).equals("\u8986\u76d6")) break;
                a.f.a(n2);
                this.c();
            }
        }
    }

    public final void b() {
    }

    private void c() {
        this.b = a.f.g();
        this.a.a(this.b);
    }
}

