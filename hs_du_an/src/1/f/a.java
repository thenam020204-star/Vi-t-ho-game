/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package f;

import f.c;
import f.d;
import h.b;
import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;

public final class a {
    private int a = -1;
    private int b = 0;
    private short[] c = null;
    private short[] d = null;
    private short[] e = null;
    private int f = 0;
    private int g = 0;
    private c[] h = null;
    private int i = 0;
    private int j = 0;
    private int k = 0;
    private boolean l = false;

    public final void a() {
        this.i = 0;
        this.k = 0;
    }

    public final void b() {
        if (this.i < 0) {
            return;
        }
        this.f += this.c[this.i];
        this.g += this.d[this.i];
        ++this.j;
        if (this.j > this.e[this.i]) {
            this.j = 0;
            ++this.i;
        }
        if (this.i >= this.h.length) {
            if (this.b != -1 && this.k >= this.b) {
                this.i = -1;
                return;
            }
            this.i = 0;
            ++this.k;
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.i == -1) {
            this.h[this.h.length - 1].a(graphics, n2, n3, this.l);
            return;
        }
        this.h[this.i].a(graphics, n2, n3, this.l);
    }

    public final c c() {
        if (this.h == null || this.i < -1 || this.i >= this.h.length) {
            return null;
        }
        if (this.i == -1) {
            return this.h[this.h.length - 1];
        }
        return this.h[this.i];
    }

    public final void a(DataInputStream dataInputStream, d d2) {
        try {
            this.a = h.b.a(dataInputStream.readByte());
            int n2 = dataInputStream.readShort();
            if (n2 <= 0) {
                return;
            }
            this.b = -1;
            this.e = new short[n2];
            this.c = new short[n2];
            this.d = new short[n2];
            short s2 = 0;
            this.h = new c[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                s2 = dataInputStream.readShort();
                this.h[i2] = d2.a((int)s2);
                this.e[i2] = dataInputStream.readShort();
                this.c[i2] = dataInputStream.readShort();
                this.d[i2] = dataInputStream.readShort();
            }
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public final int d() {
        return this.i;
    }

    public final void a(int n2) {
        this.i = n2;
    }

    public final void b(int n2) {
        this.b = n2;
    }

    public final int e() {
        return this.b;
    }

    public final int f() {
        return this.a;
    }

    public final a g() {
        a a2 = new a();
        new a().a = this.a;
        a2.b = this.b;
        a2.c = this.c;
        a2.d = this.d;
        a2.e = this.e;
        a2.f = this.f;
        a2.g = this.g;
        a2.i = this.i;
        a2.j = this.j;
        a2.k = this.k;
        a2.h = this.h;
        return a2;
    }

    public final void a(boolean bl) {
        this.l = bl;
    }

    public final boolean h() {
        return this.b < 0 || this.i == -1;
    }
}

