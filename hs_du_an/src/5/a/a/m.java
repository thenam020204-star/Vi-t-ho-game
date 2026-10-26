/*
 * Decompiled with CFR 0.152.
 */
package a.a;

import a.a.a;
import a.c.b;

public abstract class m
extends a {
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

    protected abstract void g();

    public m(byte by) {
        super(by);
    }

    public final void a() {
        switch (this.j) {
            case 1: {
                if (b.a.l[3]) {
                    this.c = a.c.b.a(this.c, this.p);
                    this.d = a.c.b.a(this.d, this.q);
                    this.a = a.c.b.a(this.a, this.n);
                    this.b = a.c.b.a(this.b, this.o);
                } else {
                    this.a = this.n;
                    this.b = this.o;
                    this.c = this.p;
                    this.d = this.q;
                }
                if (this.c == this.p && this.a == this.n && this.b == this.o && this.d == this.q) {
                    this.a_();
                }
                return;
            }
            case 2: {
                this.g();
                return;
            }
            case 3: {
                if (b.a.l[3]) {
                    this.c = a.c.b.a(this.c, this.p);
                    this.d = a.c.b.a(this.d, this.q);
                    this.a = a.c.b.a(this.a, this.n);
                    this.b = a.c.b.a(this.b, this.o);
                } else {
                    this.c = this.p;
                    this.d = this.q;
                    this.a = this.n;
                    this.b = this.o;
                }
                if (this.c != this.p || this.a != this.n || this.b != this.o || this.d != this.q) break;
                this.i();
            }
        }
    }

    public void b() {
        this.j = 3;
    }

    protected void a_() {
        this.j = 2;
    }

    protected void i() {
        this.e();
        boolean bl = true;
        m m2 = this;
        this.r = true;
    }

    public final int k() {
        c.a.a(this);
        long l2 = 0L;
        long l3 = 0L;
        while (!this.r) {
            l2 = System.currentTimeMillis();
            this.a();
            if (this.j == 2) {
                int n2 = main.a.a.c();
                this.a(n2, main.a.a.c);
            }
            main.a.a.a();
            l3 = System.currentTimeMillis() - l2;
            if (l3 < 33L) {
                a.c.b.a(33L - l3);
                continue;
            }
            a.c.b.a(2L);
        }
        c.a.b(this);
        return this.i;
    }

    protected final void a(boolean bl) {
        this.r = true;
    }

    protected final void a(int n2) {
        this.i = n2;
    }
}

