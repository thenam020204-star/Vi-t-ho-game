/*
 * Decompiled with CFR 0.152.
 */
package c;

import c.k;
import d.a;
import main.b;

public abstract class j
extends k {
    private int a = 0;
    private boolean b = false;

    public j(byte by) {
        super(by);
    }

    public final int f() {
        d.a.d(this);
        long l2 = 0L;
        long l3 = 0L;
        while (!this.b) {
            l2 = System.currentTimeMillis();
            this.a();
            int n2 = main.b.a.c();
            this.a(n2, main.b.a.c);
            main.b.a.a();
            l3 = System.currentTimeMillis() - l2;
            if (l3 < 33L) {
                e.a.b.a(33L - l3);
                continue;
            }
            e.a.b.a(2L);
        }
        d.a.e(this);
        return this.a;
    }

    protected final void a(boolean bl) {
        this.b = true;
    }

    protected final void a(int n2) {
        this.a = n2;
    }
}

