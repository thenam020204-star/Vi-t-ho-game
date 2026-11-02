/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package a.e;

import a.c.b;
import a.e.a;
import a.e.c;
import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;

public final class d {
    private a[] a = null;
    private byte[] b = null;
    private byte[] c = null;
    private byte[] d = null;
    private byte[] e = null;
    private boolean f = false;

    public final void a(Graphics graphics, int n2, int n3, boolean bl) {
        if (this.b == null || this.a == null) {
            return;
        }
        int n4 = 0;
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            if (bl) {
                n4 = this.a[i2].a(this.b[i2]);
                if (n4 < 0 || n4 >= this.a[i2].a().length) {
                    return;
                }
                n4 = n2 - this.c[i2] - this.a[i2].a(n4);
            } else {
                n4 = n2 + this.c[i2];
            }
            this.a[i2].a(graphics, this.b[i2], n4, n3 + this.d[i2], bl, this.e[i2]);
        }
    }

    public final void a(DataInputStream dataInputStream, c c2) {
        try {
            this.f = dataInputStream.readBoolean();
            int n2 = a.c.b.a(dataInputStream.readByte());
            if (n2 <= 0) {
                return;
            }
            short s = 0;
            this.b = new byte[n2];
            this.a = new a[n2];
            this.c = new byte[n2];
            this.d = new byte[n2];
            this.e = new byte[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                int n3;
                this.b[i2] = dataInputStream.readByte();
                s = a.c.b.a(dataInputStream.readByte());
                this.a[i2] = c2.a(s);
                this.c[i2] = dataInputStream.readByte();
                this.d[i2] = dataInputStream.readByte();
                byte by = dataInputStream.readByte();
                s = by;
                switch (by) {
                    case 1: {
                        n3 = 2;
                        break;
                    }
                    case 2: {
                        n3 = 1;
                        break;
                    }
                    case 3: {
                        n3 = 5;
                        break;
                    }
                    case 4: {
                        n3 = 3;
                        break;
                    }
                    case 5: {
                        n3 = 6;
                        break;
                    }
                    default: {
                        n3 = 0;
                    }
                }
                this.e[i2] = n3;
            }
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public final boolean a() {
        return this.f;
    }
}

