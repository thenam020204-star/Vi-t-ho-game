/*
 * Decompiled with CFR 0.152.
 */
package a;

import a.f;
import a.g;
import b.e.a;
import f.c;
import java.io.DataInputStream;
import java.util.Enumeration;
import java.util.Hashtable;

public final class d
extends g {
    public int a = 0;
    public short b = 0;
    public byte c = 0;
    private Hashtable O = new Hashtable();
    private Hashtable P = new Hashtable();
    private int Q = 0;
    private int R = 0;

    public d() {
        this.q = 8;
    }

    public final void a(DataInputStream dataInputStream) {
        try {
            int n2;
            this.n = dataInputStream.readShort();
            this.p = dataInputStream.readUTF();
            this.u = dataInputStream.readShort();
            this.v = dataInputStream.readShort();
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
            this.a = dataInputStream.readInt();
            this.b = dataInputStream.readShort();
            this.c = dataInputStream.readByte();
            dataInputStream.readShort();
            dataInputStream.readByte();
            this.L = dataInputStream.readByte();
            this.c(this.L, (int)dataInputStream.readByte());
            boolean bl = false;
            byte by = 0;
            c c2 = null;
            int n3 = dataInputStream.readByte();
            if (n3 > 0) {
                for (n2 = 0; n2 < n3; ++n2) {
                    short s = dataInputStream.readShort();
                    by = dataInputStream.readByte();
                    c2 = a.f.c((int)s);
                    if (c2 == null || by <= 0) continue;
                    this.O.put(c2, new Byte(by));
                }
            }
            if ((n3 = dataInputStream.readByte()) > 0) {
                for (n2 = 0; n2 < n3; ++n2) {
                    short s = dataInputStream.readShort();
                    by = dataInputStream.readByte();
                    c2 = a.f.c((int)s);
                    if (c2 == null || by <= 0) continue;
                    this.P.put(c2, new Byte(by));
                }
            }
            this.i = "r" + dataInputStream.readShort();
            this.N = dataInputStream.readShort();
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    private static c a(Hashtable hashtable) {
        if (hashtable == null || hashtable.isEmpty()) {
            return null;
        }
        int n2 = b.e.a.c(hashtable.size());
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

    public final c a() {
        return a.d.a(this.O);
    }

    public final c b() {
        return a.d.a(this.P);
    }

    public final void a(c c2, int n2) {
        if (c2 == null) {
            return;
        }
        a.d.a(this.O, c2, -1);
        a.d.a(this.P, c2, -1);
    }

    private static void a(Hashtable hashtable, c c2, int n2) {
        if (hashtable == null || c2 == null || n2 == 0) {
            return;
        }
        Byte by = (Byte)hashtable.get(c2);
        if (by == null) {
            return;
        }
        int n3 = by.byteValue();
        n3 = b.e.a.b(n3, n2, 127, 0);
        hashtable.put(c2, new Byte((byte)n3));
    }

    public final void c() {
        c c2 = null;
        Enumeration enumeration = this.O.keys();
        while (enumeration.hasMoreElements()) {
            c2 = (c)enumeration.nextElement();
            a.f.e(c2.c);
        }
        enumeration = this.P.keys();
        while (enumeration.hasMoreElements()) {
            c2 = (c)enumeration.nextElement();
            a.f.e(c2.c);
        }
    }

    public final int d() {
        return this.Q + this.G[0];
    }

    public final int e() {
        return this.R + this.G[1];
    }

    public final int f() {
        return this.B[11] + this.G[2];
    }
}

