/*
 * Decompiled with CFR 0.152.
 */
package a.e;

import a.e.a;
import a.e.b;
import a.e.d;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;

public final class c {
    public String a = null;
    private Hashtable b = null;
    private Hashtable c = null;
    private Hashtable d = null;

    private c(String string) {
        this.a = string;
        this.b = new Hashtable();
        this.c = new Hashtable();
        this.d = new Hashtable();
    }

    public final d a(int n2) {
        return (d)this.c.get(new Integer(n2));
    }

    public final a a(short s) {
        c c2 = c2.b.get(new Short(s));
        if (c2 == null) {
            return null;
        }
        return (a)((Object)c2);
    }

    public final b b(int n2) {
        b b2 = (b)((c)((Object)b2)).d.get(new Integer(n2));
        if (b2 != null) {
            return b2.f();
        }
        return null;
    }

    private void a(DataInputStream dataInputStream) {
        int n2 = a.c.b.a((int)dataInputStream.readByte());
        if (n2 <= 0) {
            return;
        }
        short s = 0;
        b b2 = null;
        for (int i2 = 0; i2 < n2; ++i2) {
            b2 = new b();
            s = a.c.b.a(dataInputStream.readByte());
            b2.a(dataInputStream, this);
            this.d.put(new Integer(s), b2);
        }
    }

    private void b(DataInputStream dataInputStream) {
        int n2 = a.c.b.a(dataInputStream.readByte());
        if (n2 <= 0) {
            return;
        }
        d d2 = null;
        int n3 = 0;
        for (int i2 = 0; i2 < n2; ++i2) {
            d2 = new d();
            n3 = a.c.b.a((int)dataInputStream.readByte());
            d2.a(dataInputStream, this);
            this.c.put(new Integer(n3), d2);
        }
    }

    public final a a(String string) {
        if (string == null || string.trim().length() == 0) {
            return null;
        }
        Enumeration enumeration = ((c)((Object)enumeration)).b.elements();
        a a2 = null;
        while (enumeration.hasMoreElements()) {
            a2 = (a)enumeration.nextElement();
            if (!a2.a.equals(string)) continue;
            return a2;
        }
        return null;
    }

    public static c b(String string) {
        c c2 = b.a.a(string);
        if (c2 != null) {
            return c2;
        }
        c2 = a.e.c.c(string);
        return c2;
    }

    private static c c(String object) {
        if (object == null || ((String)object).trim().length() == 0) {
            return null;
        }
        Object object2 = "/ani/" + (String)object + ".ani";
        InputStream inputStream = null;
        DataInputStream dataInputStream = null;
        inputStream = "".getClass().getResourceAsStream((String)object2);
        if (inputStream == null) {
            return null;
        }
        try {
            dataInputStream = new DataInputStream(inputStream);
            object = new c((String)object);
            DataInputStream dataInputStream2 = dataInputStream;
            object2 = object;
            DataInputStream dataInputStream3 = dataInputStream2;
            c c2 = object;
            int n2 = a.c.b.a(dataInputStream3.readByte());
            if (n2 > 0) {
                short s = 0;
                Object object3 = null;
                object3 = null;
                for (int i2 = 0; i2 < n2; ++i2) {
                    s = a.c.b.a(dataInputStream3.readByte());
                    String string = dataInputStream3.readUTF();
                    object3 = string;
                    object3 = string;
                    a a2 = b.a.b(string);
                    object3 = a2 != null ? a2 : (a2 = a.e.a.a((String)object3));
                    if (object3 == null) continue;
                    c2.b.put(new Short(s), object3);
                }
            }
            super.b(dataInputStream2);
            super.a(dataInputStream2);
            return object;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return null;
        }
        finally {
            a.c.b.a(inputStream);
            a.c.b.a(dataInputStream);
        }
    }
}

