/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
package a.c;

import a.c.b;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Hashtable;
import javax.microedition.lcdui.Image;

public final class d {
    private String[] a = null;
    private int[] b = null;
    private byte[][] c = null;
    private static Hashtable d = new Hashtable();

    public static d a(String string) {
        if (string == null) {
            return null;
        }
        d d2 = null;
        d2 = (d)d.get(string);
        if (d2 != null) {
            return d2;
        }
        d2 = new d(string);
        if (d2.a == null || d2.b == null || d2.c == null) {
            return null;
        }
        return d2;
    }

    private d(String string) {
        this.d(string);
    }

    private void d(String object) {
        int n2;
        DataInputStream dataInputStream;
        InputStream inputStream;
        block12: {
            block11: {
                if (object == null) {
                    return;
                }
                inputStream = null;
                dataInputStream = null;
                inputStream = this.getClass().getResourceAsStream((String)object);
                if (inputStream != null) break block11;
                a.c.b.a(inputStream);
                a.c.b.a(null);
                return;
            }
            dataInputStream = new DataInputStream(inputStream);
            n2 = dataInputStream.readShort();
            if (n2 > 0) break block12;
            a.c.b.a(inputStream);
            a.c.b.a(dataInputStream);
            return;
        }
        try {
            int n3;
            this.a = new String[n2];
            this.b = new int[n2];
            for (n3 = 0; n3 < n2; ++n3) {
                this.a[n3] = dataInputStream.readUTF();
                this.b[n3] = dataInputStream.readInt();
            }
            this.c = new byte[n2][];
            for (n3 = 0; n3 < n2; ++n3) {
                this.c[n3] = new byte[this.b[n3]];
                dataInputStream.read(this.c[n3]);
            }
            for (n3 = 0; n3 < this.c.length; ++n3) {
                n2 = 0;
                while (n2 < this.c[n3].length) {
                    byte[] byArray = this.c[n3];
                    int n4 = n2++;
                    byArray[n4] = (byte)(byArray[n4] ^ 1);
                }
            }
        }
        catch (Exception exception) {
            try {
                object = exception;
                exception.printStackTrace();
            }
            catch (Throwable throwable) {
                a.c.b.a(inputStream);
                a.c.b.a(dataInputStream);
                throw throwable;
            }
            a.c.b.a(inputStream);
            a.c.b.a(dataInputStream);
            return;
        }
        a.c.b.a(inputStream);
        a.c.b.a(dataInputStream);
        return;
    }

    public final byte[] b(String string) {
        if (this.a == null || this.c == null || string == null || this.b == null) {
            return null;
        }
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            if (!this.a[i2].equals(string)) continue;
            return this.c[i2];
        }
        return null;
    }

    public final Image c(String string) {
        byte[] byArray = ((d)object).b(string);
        Object object = byArray;
        if (byArray == null) {
            return null;
        }
        return Image.createImage((byte[])object, (int)0, (int)((Object)object).length);
    }

    public final String toString() {
        String string = null;
        if (this.a != null) {
            for (int i2 = 0; i2 < this.a.length; ++i2) {
                string = string + this.a[i2] + "\n";
            }
        }
        return string;
    }
}

