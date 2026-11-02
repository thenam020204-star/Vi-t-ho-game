/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package e.b;

import e.a.b;
import e.b.a;
import e.b.c;
import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;

public final class d {
    private a[] a = null;
    private byte[] b = null;
    private byte[] c = null;
    private byte[] d = null;
    private boolean e = false;

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
            this.a[i2].a(graphics, this.b[i2], n4, n3 + this.d[i2], bl);
        }
    }

    public final void a(DataInputStream dataInputStream, c c2) {
        try {
            this.e = dataInputStream.readBoolean();
            int n2 = e.a.b.a(dataInputStream.readByte());
            if (n2 <= 0) {
                return;
            }
            short s2 = 0;
            this.b = new byte[n2];
            this.a = new a[n2];
            this.c = new byte[n2];
            this.d = new byte[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                this.b[i2] = dataInputStream.readByte();
                s2 = e.a.b.a(dataInputStream.readByte());
                this.a[i2] = c2.a(s2);
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

    public final boolean a() {
        return this.e;
    }
}

