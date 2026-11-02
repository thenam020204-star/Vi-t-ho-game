/*
 * Decompiled with CFR 0.152.
 */
package c;

import c.f;
import d.b;
import d.g;
import java.io.DataInputStream;
import java.io.IOException;

public final class e
extends f {
    public e() {
        super((byte)15);
    }

    public final void a(DataInputStream dataInputStream) {
        try {
            super.b(dataInputStream);
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public final b[] a(g g2, g g3) {
        return null;
    }

    public final void a(g g2, g g3, g[] gArray) {
    }

    public final boolean a() {
        return false;
    }
}

