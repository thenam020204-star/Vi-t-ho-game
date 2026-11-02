/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package f;

import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class b {
    private String b = null;
    private Image c = null;
    private short[] d = null;
    private short[] e = null;
    private short[] f = null;
    public short[] a = null;
    private short[] g = null;

    public b(String string) {
        this.b = string;
    }

    public final void a(Graphics graphics, short s2, int n2, int n3, boolean bl) {
        int n4 = this.a(s2);
        s2 = (short)n4;
        if (n4 < 0) {
            return;
        }
        if (bl) {
            h.b.a(graphics, this.c, this.e[s2], this.f[s2], this.a[s2], this.g[s2], 2, n2, n3, 20);
            return;
        }
        h.b.a(graphics, this.c, this.e[s2], this.f[s2], this.a[s2], this.g[s2], n2, n3, 20);
    }

    public final int a(short s2) {
        if (this.d == null) {
            return -1;
        }
        for (int i2 = 0; i2 < this.d.length; ++i2) {
            if (this.d[i2] != s2) continue;
            return i2;
        }
        return -1;
    }

    public final void a(DataInputStream dataInputStream) {
        try {
            String string = dataInputStream.readUTF();
            this.c = h.b.a("/fra/" + string + ".png");
            int n2 = h.b.a(dataInputStream.readByte());
            if (n2 <= 0) {
                return;
            }
            this.d = new short[n2];
            this.e = new short[n2];
            this.f = new short[n2];
            this.a = new short[n2];
            this.g = new short[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                this.d[i2] = h.b.a(dataInputStream.readByte());
                this.e[i2] = h.b.a(dataInputStream.readByte());
                this.f[i2] = h.b.a(dataInputStream.readByte());
                this.a[i2] = h.b.a(dataInputStream.readByte());
                this.g[i2] = h.b.a(dataInputStream.readByte());
            }
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public final String a() {
        return this.b;
    }
}

