/*
 * Decompiled with CFR 0.152.
 */
package b;

import a.c.b;
import b.a;
import b.f;
import java.io.DataInputStream;
import java.util.Enumeration;
import java.util.Hashtable;

public final class c
extends f {
    public int w = 0;
    public short x = 0;
    public byte y = 0;
    private Hashtable O = new Hashtable();
    private Hashtable P = new Hashtable();
    private int Q = 0;
    private int R = 0;

    public c() {
        this.l = 8;
    }

    public final void a(DataInputStream dataInputStream) {
        try {
            int n2;
            this.i = dataInputStream.readShort();
            this.k = dataInputStream.readUTF();
            this.p = dataInputStream.readShort();
            this.q = dataInputStream.readShort();
            dataInputStream.readByte();
            this.I = dataInputStream.readShort();
            this.B[2] = dataInputStream.readInt();
            this.B[3] = this.B[2];
            this.Q = dataInputStream.readShort();
            this.R = dataInputStream.readShort();
            this.B[8] = dataInputStream.readShort();
            this.B[9] = dataInputStream.readShort();
            this.B[11] = dataInputStream.readShort();
            this.B[10] = dataInputStream.readShort();
            this.J = dataInputStream.readInt();
            this.w = dataInputStream.readInt();
            this.x = dataInputStream.readShort();
            this.y = dataInputStream.readByte();
            dataInputStream.readShort();
            dataInputStream.readByte();
            this.L = dataInputStream.readByte();
            this.c(this.L, (int)dataInputStream.readByte());
            boolean bl = false;
            byte by = 0;
            f.c c2 = null;
            int n3 = dataInputStream.readByte();
            if (n3 > 0) {
                for (n2 = 0; n2 < n3; ++n2) {
                    short s = dataInputStream.readShort();
                    by = dataInputStream.readByte();
                    c2 = b.a.c((int)s);
                    if (c2 == null || by <= 0) continue;
                    this.O.put(c2, new Byte(by));
                }
            }
            if ((n3 = dataInputStream.readByte()) > 0) {
                for (n2 = 0; n2 < n3; ++n2) {
                    short s = dataInputStream.readShort();
                    by = dataInputStream.readByte();
                    c2 = b.a.c((int)s);
                    if (c2 == null || by <= 0) continue;
                    this.P.put(c2, new Byte(by));
                }
            }
            this.d = "r" + dataInputStream.readShort();
            this.N = dataInputStream.readShort();
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    private static f.c a(Hashtable hashtable) {
        if (hashtable == null || hashtable.isEmpty()) {
            return null;
        }
        int n2 = a.c.b.c(hashtable.size());
        Enumeration enumeration = hashtable.keys();
        Object var3_3 = null;
        int n3 = 0;
        Byte by = null;
        while (enumeration.hasMoreElements()) {
            var3_3 = enumeration.nextElement();
            by = (Byte)hashtable.get(var3_3);
            if (n3 == n2 && by > 0) {
                return var3_3;
            }
            ++n3;
        }
        return null;
    }

    public final f.c i() {
        return b.c.a(this.O);
    }

    public final f.c j() {
        return b.c.a(this.P);
    }

    public final void a(f.c c2, int n2) {
        if (c2 == null) {
            return;
        }
        b.c.a(this.O, c2, -1);
        b.c.a(this.P, c2, -1);
    }

    private static void a(Hashtable hashtable, f.c c2, int n2) {
        if (hashtable == null || c2 == null || n2 == 0) {
            return;
        }
        Byte by = (Byte)hashtable.get(c2);
        if (by == null) {
            return;
        }
        int n3 = by.byteValue();
        n3 = a.c.b.b(n3, n2, 127, 0);
        hashtable.put(c2, new Byte((byte)n3));
    }

    public final void k() {
        f.c c2 = null;
        Enumeration enumeration = this.O.keys();
        while (enumeration.hasMoreElements()) {
            c2 = (f.c)enumeration.nextElement();
            b.a.e(c2.q);
        }
        enumeration = this.P.keys();
        while (enumeration.hasMoreElements()) {
            c2 = (f.c)enumeration.nextElement();
            b.a.e(c2.q);
        }
    }

    public final int l() {
        return this.Q + this.G[0];
    }

    public final int m() {
        return this.R + this.G[1];
    }

    public final int n() {
        return this.B[11] + this.G[2];
    }
}

