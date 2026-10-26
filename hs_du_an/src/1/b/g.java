/*
 * Decompiled with CFR 0.152.
 */
package b;

import b.j;
import main.b;

public abstract class g
extends j {
    protected byte a = 0;
    protected int b = 0;
    protected int c = 0;

    public g(byte by) {
        super((byte)4);
        this.b(main.b.c);
        this.b = 0;
        this.p = 0;
        this.q = (byte)2;
    }

    public final void b() {
        this.a = (byte)2;
        this.b = main.b.c;
    }

    public final void a() {
        switch (this.a) {
            case 0: {
                int[] nArray = h.b.a(this.l(), this.m(), this.b, this.c);
                this.b(nArray[0]);
                this.c(nArray[1]);
                if (this.l() == this.b && this.m() == this.c) {
                    this.a = 1;
                }
                return;
            }
            case 1: {
                return;
            }
            case 2: {
                int[] nArray = h.b.a(this.l(), this.m(), this.b, this.c);
                this.b(nArray[0]);
                this.c(nArray[1]);
                if (this.l() != this.b || this.m() != this.c) break;
                this.d(-1);
                this.e();
            }
        }
    }
}

