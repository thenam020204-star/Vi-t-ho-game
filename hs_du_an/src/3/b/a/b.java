/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package b.a;

import b.e.a;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class b {
    public String a = null;
    private Image b = null;
    private byte[] c = null;
    private byte[] d = null;
    private byte[] e = null;
    private byte[] f = null;
    private byte[] g = null;

    private b(String string) {
        this.a = string;
    }

    public final void a(Graphics graphics, short s, int n2, int n3, boolean bl, byte by) {
        int n4 = this.a(s);
        s = (short)n4;
        if (n4 < 0) {
            return;
        }
        int n5 = b.e.a.a((int)this.d[s]);
        int n6 = b.e.a.a((int)this.e[s]);
        int n7 = b.e.a.a((int)this.f[s]);
        s = (short)b.e.a.a((int)this.g[s]);
        if (bl) {
            b.e.a.a(graphics, this.b, n5, n6, n7, s, b.e.a.e(by), n2, n3, 20);
            return;
        }
        b.e.a.a(graphics, this.b, n5, n6, n7, s, by, n2, n3, 20);
    }

    public final int a(short s) {
        if (this.c == null) {
            return -1;
        }
        for (int i2 = 0; i2 < this.c.length; ++i2) {
            if (b.e.a.a(this.c[i2]) != s) continue;
            return i2;
        }
        return -1;
    }

    public final int a(int n2) {
        if (this.f == null || n2 < 0 || n2 >= this.f.length) {
            return 0;
        }
        return b.e.a.a((int)this.f[n2]);
    }

    public final byte[] a() {
        return this.f;
    }

    public static b a(String object) {
        Object object2 = "/fra/" + (String)object + ".fra";
        object2 = "".getClass().getResourceAsStream((String)object2);
        if (object2 == null) {
            return null;
        }
        DataInputStream dataInputStream = new DataInputStream((InputStream)object2);
        object = new b((String)object);
        DataInputStream dataInputStream2 = dataInputStream;
        Object object3 = object;
        try {
            DataInputStream dataInputStream3 = dataInputStream2;
            b b2 = object3;
            int n2 = b.e.a.a(dataInputStream3.readByte());
            if (n2 > 0) {
                b2.c = new byte[n2];
                b2.d = new byte[n2];
                b2.e = new byte[n2];
                b2.f = new byte[n2];
                b2.g = new byte[n2];
                for (int i2 = 0; i2 < n2; ++i2) {
                    b2.c[i2] = dataInputStream3.readByte();
                    b2.d[i2] = dataInputStream3.readByte();
                    b2.e[i2] = dataInputStream3.readByte();
                    b2.f[i2] = dataInputStream3.readByte();
                    b2.g[i2] = dataInputStream3.readByte();
                }
            }
            ((b)object3).b = Image.createImage((InputStream)dataInputStream2);
        }
        catch (IOException iOException) {
            object3 = iOException;
            iOException.printStackTrace();
        }
        b.e.a.a((InputStream)object2);
        b.e.a.a(dataInputStream);
        return object;
    }

    public static final Image b(String object) {
        if (object == null) {
            return null;
        }
        if ((object = b.a.b.a((String)object)) == null) {
            return null;
        }
        return ((b)object).b;
    }
}

