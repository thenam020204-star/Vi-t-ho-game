/*
 * Decompiled with CFR 0.152.
 */
package b.a;

import a.f;
import b.a.a;
import b.a.b;
import b.a.d;
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

    public final a a(int n2) {
        return (a)this.c.get(new Integer(n2));
    }

    public final b a(short s) {
        c c2 = c2.b.get(new Short(s));
        if (c2 == null) {
            return null;
        }
        return (b)((Object)c2);
    }

    public final d b(int n2) {
        d d2 = (d)((c)((Object)d2)).d.get(new Integer(n2));
        if (d2 != null) {
            return d2.g();
        }
        return null;
    }

    private void a(DataInputStream dataInputStream) {
        int n2 = b.e.a.a((int)dataInputStream.readByte());
        if (n2 <= 0) {
            return;
        }
        d d2 = null;
        for (int i2 = 0; i2 < n2; ++i2) {
            d2 = new d();
            d2.a(dataInputStream, this);
            this.d.put(new Integer(d2.f()), d2);
        }
    }

    private void b(DataInputStream dataInputStream) {
        int n2 = b.e.a.a(dataInputStream.readByte());
        if (n2 <= 0) {
            return;
        }
        a a2 = null;
        int n3 = 0;
        for (int i2 = 0; i2 < n2; ++i2) {
            a2 = new a();
            n3 = b.e.a.a((int)dataInputStream.readByte());
            a2.a(dataInputStream, this);
            this.c.put(new Integer(n3), a2);
        }
    }

    public final b a(String string) {
        if (string == null || string.trim().length() == 0) {
            return null;
        }
        Enumeration enumeration = ((c)((Object)enumeration)).b.elements();
        b b2 = null;
        while (enumeration.hasMoreElements()) {
            b2 = (b)enumeration.nextElement();
            if (!b2.a.equals(string)) continue;
            return b2;
        }
        return null;
    }

    public static c b(String string) {
        c c2 = f.a(string);
        if (c2 != null) {
            return c2;
        }
        c2 = b.a.c.c(string);
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
            int n2 = b.e.a.a(dataInputStream3.readByte());
            if (n2 > 0) {
                short s = 0;
                Object object3 = null;
                object3 = null;
                for (int i2 = 0; i2 < n2; ++i2) {
                    s = b.e.a.a(dataInputStream3.readByte());
                    String string = dataInputStream3.readUTF();
                    object3 = string;
                    object3 = string;
                    b b2 = f.b(string);
                    object3 = b2 != null ? b2 : (b2 = b.a.b.a((String)object3));
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
            b.e.a.a(inputStream);
            b.e.a.a(dataInputStream);
        }
    }
}

