/*
 * Decompiled with CFR 0.152.
 */
package e;

import e.e;
import java.util.Vector;

public final class a {
    private Vector a = new Vector();

    public a() {
    }

    public a(e e2) {
        this();
        if (e2.c() != '[') {
            throw e2.a("A JSONArray text must start with '['");
        }
        if (e2.c() == ']') {
            return;
        }
        e2.a();
        block4: while (true) {
            if (e2.c() == ',') {
                e2.a();
                this.a.addElement(null);
            } else {
                e2.a();
                this.a.addElement(e2.d());
            }
            switch (e2.c()) {
                case ',': 
                case ';': {
                    if (e2.c() == ']') {
                        return;
                    }
                    e2.a();
                    continue block4;
                }
                case ']': {
                    return;
                }
            }
            break;
        }
        throw e2.a("Expected a ',' or ']'");
    }

    public a(String string) {
        this(new e(string));
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
            v0 = this = (a)this.a.elementAt(var2_2);
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

