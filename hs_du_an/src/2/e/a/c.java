/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
package e.a;

import e.a.b;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Hashtable;
import javax.microedition.lcdui.Image;

public final class c {
    private String[] a = null;
    private int[] b = null;
    private byte[][] c = null;
    private static Hashtable d = new Hashtable();

    public static c a(String string) {
        if (string == null) {
            return null;
        }
        c c2 = null;
        c2 = (c)d.get(string);
        if (c2 != null) {
            return c2;
        }
        c2 = new c(string);
        if (c2.a == null || c2.b == null || c2.c == null) {
            return null;
        }
        return c2;
    }

    private c(String string) {
        this.c(string);
    }

    private void c(String object) {
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
                e.a.b.a(inputStream);
                e.a.b.a(null);
                return;
            }
            dataInputStream = new DataInputStream(inputStream);
            n2 = dataInputStream.readShort();
            if (n2 > 0) break block12;
            e.a.b.a(inputStream);
            e.a.b.a(dataInputStream);
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
                e.a.b.a(inputStream);
                e.a.b.a(dataInputStream);
                throw throwable;
            }
            e.a.b.a(inputStream);
            e.a.b.a(dataInputStream);
            return;
        }
        e.a.b.a(inputStream);
        e.a.b.a(dataInputStream);
        return;
    }

    public final Image b(String string) {
        Object object;
        block4: {
            if (((c)object2).a == null || ((c)object2).c == null || string == null || ((c)object2).b == null) {
                object = null;
            } else {
                for (int i2 = 0; i2 < ((c)object2).a.length; ++i2) {
                    if (!((c)object2).a[i2].equals(string)) continue;
                    object = ((c)object2).c[i2];
                    break block4;
                }
                Object object2 = null;
                object = object2;
            }
        }
        if (object == null) {
            return null;
        }
        return Image.createImage((byte[])object2, (int)0, (int)((c)object2).length);
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

