/*
 * Decompiled with CFR 0.152.
 */
package f;

import f.a;
import f.b;
import f.c;
import java.io.DataInputStream;
import java.util.Enumeration;
import java.util.Hashtable;

public final class d {
    private String a = null;
    private Hashtable b = null;
    private Hashtable c = null;
    private Hashtable d = null;

    public d(String string) {
        this.a = string;
        this.b = new Hashtable();
        this.c = new Hashtable();
        this.d = new Hashtable();
    }

    public final c a(int n2) {
        return (c)this.c.get(new Integer(n2));
    }

    public final b a(short s2) {
        d d2 = d2.b.get(new Short(s2));
        if (d2 == null) {
            return null;
        }
        return (b)((Object)d2);
    }

    public final a b(int n2) {
        a a2 = (a)((d)((Object)a2)).d.get(new Integer(n2));
        if (a2 != null) {
            return a2.g();
        }
        return null;
    }

    public final void a(DataInputStream dataInputStream) {
        DataInputStream dataInputStream2 = dataInputStream;
        d d2 = this;
        int n2 = h.b.a(dataInputStream2.readByte());
        if (n2 > 0) {
            short s2 = 0;
            Object object = null;
            object = null;
            for (int i2 = 0; i2 < n2; ++i2) {
                s2 = h.b.a(dataInputStream2.readByte());
                object = dataInputStream2.readUTF();
                if ((object = d.a.a((String)object)) == null) continue;
                d2.b.put(new Short(s2), object);
            }
        }
        this.c(dataInputStream);
        this.b(dataInputStream);
    }

    private void b(DataInputStream dataInputStream) {
        int n2 = h.b.a(dataInputStream.readByte());
        if (n2 <= 0) {
            return;
        }
        a a2 = null;
        for (int i2 = 0; i2 < n2; ++i2) {
            a2 = new a();
            a2.a(dataInputStream, this);
            this.d.put(new Integer(a2.f()), a2);
        }
    }

    private void c(DataInputStream dataInputStream) {
        int n2 = h.b.a(dataInputStream.readByte());
        if (n2 <= 0) {
            return;
        }
        c c2 = null;
        int n3 = 0;
        for (int i2 = 0; i2 < n2; ++i2) {
            c2 = new c();
            n3 = h.b.a((int)dataInputStream.readByte());
            c2.a(dataInputStream, this);
            this.c.put(new Integer(n3), c2);
        }
    }

    public final String a() {
        return this.a;
    }

    public final b a(String string) {
        if (string == null || string.trim().length() == 0) {
            return null;
        }
        Enumeration enumeration = ((d)((Object)enumeration)).b.elements();
        b b2 = null;
        while (enumeration.hasMoreElements()) {
            b2 = (b)enumeration.nextElement();
            if (!b2.a().equals(string)) continue;
            return b2;
        }
        return null;
    }
}

