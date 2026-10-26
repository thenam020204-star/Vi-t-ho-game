/*
 * Decompiled with CFR 0.152.
 */
package g;

import g.a;
import g.d;
import java.util.Hashtable;

public final class c {
    private Hashtable b = new Hashtable();
    public static final Object a = new d();

    public c() {
    }

    public c(a a2) {
        this();
        if (a2.c() != '{') {
            throw a2.a("A JSONObject text must begin with '{'");
        }
        block8: while (true) {
            char c2 = '\u0000';
            switch (a2.c()) {
                case '\u0000': {
                    throw a2.a("A JSONObject text must end with '}'");
                }
                case '}': {
                    return;
                }
            }
            a2.a();
            Object object = a2.d().toString();
            c2 = a2.c();
            if (c2 == '=') {
                if (a2.b() != '>') {
                    a2.a();
                }
            } else if (c2 != ':') {
                throw a2.a("Expected a ':' after a key");
            }
            Object object2 = a2.d();
            c c3 = this;
            if (object == null) {
                throw new Exception("Null key.");
            }
            if (object2 != null) {
                c3.b.put(object, object2);
            } else {
                object2 = object;
                object = c3;
                ((c)object).b.remove(object2);
            }
            switch (a2.c()) {
                case ',': 
                case ';': {
                    if (a2.c() == '}') {
                        return;
                    }
                    a2.a();
                    continue block8;
                }
                case '}': {
                    return;
                }
            }
            break;
        }
        throw a2.a("Expected a ',' or '}'");
    }

    public final Object a(String string) {
        c c2 = string == null ? null : (c)c2.b.get(string);
        if (c2 == null) {
            throw new Exception("JSONObject not found.");
        }
        return c2;
    }

    public final String b(String string) {
        return this.a(string).toString();
    }
}

