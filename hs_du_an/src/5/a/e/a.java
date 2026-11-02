/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package a.e;

import a.c.b;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class a {
    public String a = null;
    private Image b = null;
    private byte[] c = null;
    private byte[] d = null;
    private byte[] e = null;
    private byte[] f = null;
    private byte[] g = null;

    private a(String string) {
        this.a = string;
    }

    public final void a(Graphics graphics, short s, int n2, int n3, boolean bl, byte by) {
        int n4 = this.a(s);
        s = (short)n4;
        if (n4 < 0) {
            return;
        }
        int n5 = a.c.b.a((int)this.d[s]);
        int n6 = a.c.b.a((int)this.e[s]);
        int n7 = a.c.b.a((int)this.f[s]);
        s = (short)a.c.b.a((int)this.g[s]);
        if (bl) {
            a.c.b.a(graphics, this.b, n5, n6, n7, s, a.c.b.e(by), n2, n3, 20);
            return;
        }
        a.c.b.a(graphics, this.b, n5, n6, n7, s, by, n2, n3, 20);
    }

    public final int a(short s) {
        if (this.c == null) {
            return -1;
        }
        for (int i2 = 0; i2 < this.c.length; ++i2) {
            if (a.c.b.a(this.c[i2]) != s) continue;
            return i2;
        }
        return -1;
    }

    public final int a(int n2) {
        if (this.f == null || n2 < 0 || n2 >= this.f.length) {
            return 0;
        }
        return a.c.b.a((int)this.f[n2]);
    }

    public final byte[] a() {
        return this.f;
    }

    public static a a(String object) {
        Object object2 = "/fra/" + (String)object + ".fra";
        object2 = "".getClass().getResourceAsStream((String)object2);
        if (object2 == null) {
            return null;
        }
        DataInputStream dataInputStream = new DataInputStream((InputStream)object2);
        object = new a((String)object);
        DataInputStream dataInputStream2 = dataInputStream;
        Object object3 = object;
        try {
            DataInputStream dataInputStream3 = dataInputStream2;
            a a2 = object3;
            int n2 = a.c.b.a(dataInputStream3.readByte());
            if (n2 > 0) {
                a2.c = new byte[n2];
                a2.d = new byte[n2];
                a2.e = new byte[n2];
                a2.f = new byte[n2];
                a2.g = new byte[n2];
                for (int i2 = 0; i2 < n2; ++i2) {
                    a2.c[i2] = dataInputStream3.readByte();
                    a2.d[i2] = dataInputStream3.readByte();
                    a2.e[i2] = dataInputStream3.readByte();
                    a2.f[i2] = dataInputStream3.readByte();
                    a2.g[i2] = dataInputStream3.readByte();
                }
            }
            ((a)object3).b = Image.createImage((InputStream)dataInputStream2);
        }
        catch (IOException iOException) {
            object3 = iOException;
            iOException.printStackTrace();
        }
        a.c.b.a((InputStream)object2);
        a.c.b.a(dataInputStream);
        return object;
    }

    public static final Image b(String object) {
        if (object == null) {
            return null;
        }
        if ((object = a.e.a.a((String)object)) == null) {
            return null;
        }
        return ((a)object).b;
    }
}

