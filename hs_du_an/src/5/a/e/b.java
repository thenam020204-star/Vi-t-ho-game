/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package a.e;

import a.e.c;
import a.e.d;
import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;

public final class b {
    private int a = 0;
    private byte[] b = null;
    private byte[] c = null;
    private byte[] d = null;
    private int e = 0;
    private int f = 0;
    private d[] g = null;
    private int h = 0;
    private int i = 0;
    private int j = 0;
    private boolean k = false;

    public final void a() {
        this.h = 0;
        this.j = 0;
    }

    public final void b() {
        if (this.h < 0) {
            return;
        }
        this.e += this.b[this.h];
        this.f += this.c[this.h];
        ++this.i;
        if (this.i > a.c.b.a((int)this.d[this.h])) {
            this.i = 0;
            ++this.h;
        }
        if (this.h >= this.g.length) {
            if (this.a != -1 && this.j >= this.a) {
                this.h = -1;
                return;
            }
            this.h = 0;
            ++this.j;
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.h == -1) {
            this.g[this.g.length - 1].a(graphics, n2, n3, this.k);
            return;
        }
        this.g[this.h].a(graphics, n2, n3, this.k);
    }

    public final d c() {
        if (this.g == null || this.h < -1 || this.h >= this.g.length) {
            return null;
        }
        if (this.h == -1) {
            return this.g[this.g.length - 1];
        }
        return this.g[this.h];
    }

    public final void a(DataInputStream dataInputStream, c c2) {
        try {
            int n2 = dataInputStream.readShort();
            if (n2 <= 0) {
                return;
            }
            this.a = -1;
            this.d = new byte[n2];
            this.b = new byte[n2];
            this.c = new byte[n2];
            int n3 = 0;
            this.g = new d[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                n3 = a.c.b.a((int)dataInputStream.readByte());
                this.g[i2] = c2.a(n3);
                this.d[i2] = dataInputStream.readByte();
                this.b[i2] = dataInputStream.readByte();
                this.c[i2] = dataInputStream.readByte();
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
        return this.h;
    }

    public final void a(int n2) {
        this.h = n2;
    }

    public final void b(int n2) {
        this.a = n2;
    }

    public final int e() {
        return this.a;
    }

    public final b f() {
        b b2 = new b();
        new b().a = this.a;
        b2.b = this.b;
        b2.c = this.c;
        b2.d = this.d;
        b2.e = this.e;
        b2.f = this.f;
        b2.h = this.h;
        b2.i = this.i;
        b2.j = this.j;
        b2.g = this.g;
        return b2;
    }

    public final void a(boolean bl) {
        this.k = bl;
    }

    public final boolean g() {
        return this.a < 0 || this.h == -1;
    }

    public final boolean h() {
        return this.k;
    }
}

