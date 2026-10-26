/*
 * Decompiled with CFR 0.152.
 */
package g;

import g.a;
import java.util.Vector;

public final class b {
    private Vector a = new Vector();

    public b() {
    }

    public b(a a2) {
        this();
        if (a2.c() != '[') {
            throw a2.a("A JSONArray text must start with '['");
        }
        if (a2.c() == ']') {
            return;
        }
        a2.a();
        block4: while (true) {
            if (a2.c() == ',') {
                a2.a();
                this.a.addElement(null);
            } else {
                a2.a();
                this.a.addElement(a2.d());
            }
            switch (a2.c()) {
                case ',': 
                case ';': {
                    if (a2.c() == ']') {
                        return;
                    }
                    a2.a();
                    continue block4;
                }
                case ']': {
                    return;
                }
            }
            break;
        }
        throw a2.a("Expected a ',' or ']'");
    }

    public b(String string) {
        this(new a(string));
    }

    /*
     * Unable to fully structure code
     */
    public final Object a(int var1_1) {
        var2_2 = var1_1;
        if (var2_2 < 0) ** GOTO lbl-1000
        var3_3 = this;
        if (var2_2 >= var3_3.a.size()) lbl-1000:
        // 2 sources

        {
            v0 = null;
        } else {
            v0 = this = (b)this.a.elementAt(var2_2);
        }
        if (v0 == null) {
            throw new Exception("JSONArray[" + var1_1 + "] not found.");
        }
        return this;
    }

    public final int a() {
        return this.a.size();
    }
}

