/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package e.b;

import e.b.c;
import e.b.d;
import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;

public final class b {
    private int a = -1;
    private int b = 0;
    private byte[] c = null;
    private byte[] d = null;
    private byte[] e = null;
    private int f = 0;
    private int g = 0;
    private d[] h = null;
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
        if (this.j > e.a.b.a((int)this.e[this.i])) {
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

    public final d c() {
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
            this.a = e.a.b.a(dataInputStream.readByte());
            int n2 = dataInputStream.readShort();
            if (n2 <= 0) {
                return;
            }
            this.b = -1;
            this.e = new byte[n2];
            this.c = new byte[n2];
            this.d = new byte[n2];
            int n3 = 0;
            this.h = new d[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                n3 = e.a.b.a((int)dataInputStream.readByte());
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

    public final b g() {
        b b2 = new b();
        new b().a = this.a;
        b2.b = this.b;
        b2.c = this.c;
        b2.d = this.d;
        b2.e = this.e;
        b2.f = this.f;
        b2.g = this.g;
        b2.i = this.i;
        b2.j = this.j;
        b2.k = this.k;
        b2.h = this.h;
        return b2;
    }

    public final void a(boolean bl) {
        this.l = bl;
    }

    public final boolean h() {
        return this.b < 0 || this.i == -1;
    }
}

