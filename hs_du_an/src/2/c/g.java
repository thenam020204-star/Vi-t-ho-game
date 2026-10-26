/*
 * Decompiled with CFR 0.152.
 */
package c;

import c.j;
import main.b;

public abstract class g
extends j {
    protected byte a = 0;
    protected int b = 0;
    protected int c = 0;

    public g(byte by) {
        super((byte)4);
        this.e = main.b.d;
        this.b = 0;
        this.j = 0;
        this.k = (byte)2;
    }

    public final void b() {
        this.a = (byte)2;
        this.b = main.b.d;
    }

    public final void a() {
        switch (this.a) {
            case 0: {
                int[] nArray = e.a.b.a(this.e, this.f, this.b, this.c);
                this.e = nArray[0];
                this.f = nArray[1];
                if (this.e == this.b && this.f == this.c) {
                    this.a = 1;
                }
                return;
            }
            case 1: {
                return;
            }
            case 2: {
                int[] nArray = e.a.b.a(this.e, this.f, this.b, this.c);
                this.e = nArray[0];
                this.f = nArray[1];
                if (this.e != this.b || this.f != this.c) break;
                this.h();
                this.e();
                this.a(true);
            }
        }
    }
}

