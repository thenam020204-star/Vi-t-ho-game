/*
 * Decompiled with CFR 0.152.
 */
package b.b;

import a.f;
import b.b.c;
import b.e.a;
import d.k;

public abstract class m
extends c {
    private static int[] a = new int[]{3739672, 6373665, 6373665, 7885355};
    public int l = 1;
    private int b = 0;
    private boolean j = false;
    protected int[] m = a;
    protected int n = 0;
    protected int o = 0;
    protected int p = 0;
    protected int q = 0;
    protected int r = 0;
    protected int s = 0;

    protected abstract void b();

    public m(byte by) {
        super(by);
    }

    public final void d() {
        switch (this.l) {
            case 1: {
                if (a.f.l[3]) {
                    this.e = b.e.a.a(this.e, this.r);
                    this.f = b.e.a.a(this.f, this.s);
                    this.c = b.e.a.a(this.c, this.p);
                    this.d = b.e.a.a(this.d, this.q);
                } else {
                    this.c = this.p;
                    this.d = this.q;
                    this.e = this.r;
                    this.f = this.s;
                }
                if (this.e == this.r && this.c == this.p && this.d == this.q && this.f == this.s) {
                    this.h();
                }
                return;
            }
            case 2: {
                this.b();
                return;
            }
            case 3: {
                if (a.f.l[3]) {
                    this.e = b.e.a.a(this.e, this.r);
                    this.f = b.e.a.a(this.f, this.s);
                    this.c = b.e.a.a(this.c, this.p);
                    this.d = b.e.a.a(this.d, this.q);
                } else {
                    this.e = this.r;
                    this.f = this.s;
                    this.c = this.p;
                    this.d = this.q;
                }
                if (this.e != this.r || this.c != this.p || this.d != this.q || this.f != this.s) break;
                this.i();
            }
        }
    }

    public void c() {
        this.l = 3;
    }

    protected void h() {
        this.l = 2;
    }

    protected void i() {
        this.f();
        boolean bl = true;
        m m2 = this;
        this.j = true;
    }

    public final int k() {
        k.c(this);
        long l2 = 0L;
        long l3 = 0L;
        while (!this.j) {
            l2 = System.currentTimeMillis();
            this.d();
            if (this.l == 2) {
                int n2 = main.a.a.c();
                this.a(n2, main.a.a.c);
            }
            main.a.a.a();
            l3 = System.currentTimeMillis() - l2;
            if (l3 < 33L) {
                b.e.a.a(33L - l3);
                continue;
            }
            b.e.a.a(2L);
        }
        k.d(this);
        return this.b;
    }

    protected final void a(boolean bl) {
        this.j = true;
    }

    protected final void b(int n2) {
        this.b = n2;
    }
}

