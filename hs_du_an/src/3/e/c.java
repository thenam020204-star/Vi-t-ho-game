/*
 * Decompiled with CFR 0.152.
 */
package e;

import e.b;
import e.e;
import java.util.Hashtable;

public final class c {
    private Hashtable b = new Hashtable();
    public static final Object a = new b(null);

    public c() {
    }

    public c(e e2) {
        this();
        if (e2.c() != '{') {
            throw e2.a("A JSONObject text must begin with '{'");
        }
        block8: while (true) {
            char c2 = '\u0000';
            switch (e2.c()) {
                case '\u0000': {
                    throw e2.a("A JSONObject text must end with '}'");
                }
                case '}': {
                    return;
                }
            }
            e2.a();
            Object object = e2.d().toString();
            c2 = e2.c();
            if (c2 == '=') {
                if (e2.b() != '>') {
                    e2.a();
                }
            } else if (c2 != ':') {
                throw e2.a("Expected a ':' after a key");
            }
            Object object2 = e2.d();
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
            switch (e2.c()) {
                case ',': 
                case ';': {
                    if (e2.c() == '}') {
                        return;
                    }
                    e2.a();
                    continue block8;
                }
                case '}': {
                    return;
                }
            }
            break;
        }
        throw e2.a("Expected a ',' or '}'");
    }

    public final String a(String string) {
        c c2 = string == null ? null : (c)c2.b.get(string);
        if (c2 == null) {
            throw new Exception("JSONObject not found.");
        }
        return c2.toString();
    }
}

