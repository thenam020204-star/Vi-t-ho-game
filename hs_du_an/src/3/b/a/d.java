/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b.a;

import b.a.a;
import b.a.c;
import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;

public final class d {
    private int a = -1;
    private int b = 0;
    private byte[] c = null;
    private byte[] d = null;
    private byte[] e = null;
    private int f = 0;
    private int g = 0;
    private a[] h = null;
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
        if (this.j > b.e.a.a((int)this.e[this.i])) {
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

    public final a c() {
        if (this.h == null || this.i < -1 || this.i >= this.h.length) {
            return null;
        }
        if (this.i == -1) {
            return this.h[this.h.length - 1];
        }
        return this.h[this.i];
    }

    public final void a(DataInputStream dataInputStream, c c2) {
        try {
            this.a = b.e.a.a(dataInputStream.readByte());
            int n2 = dataInputStream.readShort();
            if (n2 <= 0) {
                return;
            }
            this.b = -1;
            this.e = new byte[n2];
            this.c = new byte[n2];
            this.d = new byte[n2];
            int n3 = 0;
            this.h = new a[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                n3 = b.e.a.a((int)dataInputStream.readByte());
                this.h[i2] = c2.a(n3);
                this.e[i2] = dataInputStream.readByte();
                this.c[i2] = dataInputStream.readByte();
                this.d[i2] = dataInputStream.readByte();
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

    public final d g() {
        d d2 = new d();
        new d().a = this.a;
        d2.b = this.b;
        d2.c = this.c;
        d2.d = this.d;
        d2.e = this.e;
        d2.f = this.f;
        d2.g = this.g;
        d2.i = this.i;
        d2.j = this.j;
        d2.k = this.k;
        d2.h = this.h;
        return d2;
    }

    public final void a(boolean bl) {
        this.l = bl;
    }

    public final boolean h() {
        return this.b < 0 || this.i == -1;
    }

    public final boolean i() {
        return this.l;
    }
}

