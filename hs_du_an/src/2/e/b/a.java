/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package e.b;

import e.a.b;
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

    public final void a(Graphics graphics, short s2, int n2, int n3, boolean bl) {
        int n4 = this.a(s2);
        s2 = (short)n4;
        if (n4 < 0) {
            return;
        }
        int n5 = e.a.b.a((int)this.d[s2]);
        int n6 = e.a.b.a((int)this.e[s2]);
        int n7 = e.a.b.a((int)this.f[s2]);
        s2 = (short)e.a.b.a((int)this.g[s2]);
        if (bl) {
            e.a.b.a(graphics, this.b, n5, n6, n7, s2, 2, n2, n3, 20);
            return;
        }
        e.a.b.a(graphics, this.b, n5, n6, n7, s2, n2, n3, 20);
    }

    public final int a(short s2) {
        if (this.c == null) {
            return -1;
        }
        for (int i2 = 0; i2 < this.c.length; ++i2) {
            if (e.a.b.a(this.c[i2]) != s2) continue;
            return i2;
        }
        return -1;
    }

    public final int a(int n2) {
        if (this.f == null || n2 < 0 || n2 >= this.f.length) {
            return 0;
        }
        return e.a.b.a((int)this.f[n2]);
    }

    public final byte[] a() {
        return this.f;
    }

    public static a a(String object) {
        Object object2 = "/fra/" + (String)object + ".fra";
        InputStream inputStream = "".getClass().getResourceAsStream((String)object2);
        if (inputStream == null) {
            System.out.println((String)object2 + " == null!");
            return null;
        }
        object2 = new DataInputStream(inputStream);
        object = new a((String)object);
        Object object3 = object2;
        Object object4 = object;
        try {
            Object object5 = object3;
            a a2 = object4;
            int n2 = e.a.b.a(((DataInputStream)object5).readByte());
            if (n2 > 0) {
                a2.c = new byte[n2];
                a2.d = new byte[n2];
                a2.e = new byte[n2];
                a2.f = new byte[n2];
                a2.g = new byte[n2];
                for (int i2 = 0; i2 < n2; ++i2) {
                    a2.c[i2] = ((DataInputStream)object5).readByte();
                    a2.d[i2] = ((DataInputStream)object5).readByte();
                    a2.e[i2] = ((DataInputStream)object5).readByte();
                    a2.f[i2] = ((DataInputStream)object5).readByte();
                    a2.g[i2] = ((DataInputStream)object5).readByte();
                }
            }
            ((a)object4).b = Image.createImage((InputStream)object3);
        }
        catch (IOException iOException) {
            object4 = iOException;
            iOException.printStackTrace();
        }
        e.a.b.a(inputStream);
        e.a.b.a((InputStream)object2);
        return object;
    }

    public static final Image b(String object) {
        if (object == null) {
            return null;
        }
        if ((object = e.b.a.a((String)object)) == null) {
            return null;
        }
        return ((a)object).b;
    }
}

