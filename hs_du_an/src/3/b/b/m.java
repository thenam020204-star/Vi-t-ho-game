/*
 * Decompiled with CFR 0.152.
 */
package b.b;

import b.b.c;
import b.e.a;
import d.k;

public abstract class m
extends c {
    private static int[] h = new int[]{3739672, 6373665, 6373665, 7885355};
    public int j = 1;
    private int i = 0;
    private boolean r = false;
    protected int[] k = h;
    protected int l = 0;
    protected int m = 0;
    protected int n = 0;
    protected int o = 0;
    protected int p = 0;
    protected int q = 0;

    protected abstract void b();

    public m(byte by) {
        super(by);
    }

    public final void d() {
        switch (this.j) {
            case 1: {
                v0.c = b.e.a.a(this.c, this.p);
                this.d = b.e.a.a(this.d, this.q);
                this.a = b.e.a.a(this.a, this.n);
                this.b = b.e.a.a(this.b, this.o);
                if (this.c == this.p && this.a == this.n && this.b == this.o && this.d == this.q) {
                    this.h();
                }
                return;
            }
            case 2: {
                this.b();
                return;
            }
            case 3: {
                v1.c = b.e.a.a(this.c, this.p);
                this.d = b.e.a.a(this.d, this.q);
                this.a = b.e.a.a(this.a, this.n);
                this.b = b.e.a.a(this.b, this.o);
                if (this.c != this.p || this.a != this.n || this.b != this.o || this.d != this.q) break;
                this.i();
            }
        }
    }

    public void c() {
        this.j = 3;
    }

    protected void h() {
        this.j = 2;
    }

    protected void i() {
        this.f();
        boolean bl = true;
        m m2 = this;
        this.r = true;
    }

    public final int k() {
        d.k.c(this);
        long l2 = 0L;
        long l3 = 0L;
        while (!this.r) {
            l2 = System.currentTimeMillis();
            this.d();
            if (this.j == 2) {
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
        d.k.d(this);
        return this.i;
    }

    protected final void a(boolean bl) {
        this.r = true;
    }

    protected final void b(int n2) {
        this.i = n2;
    }
}

