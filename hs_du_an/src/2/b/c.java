/*
 * Decompiled with CFR 0.152.
 */
package b;

import b.a;
import b.f;
import e.a.b;
import java.io.DataInputStream;
import java.util.Enumeration;
import java.util.Hashtable;

public final class c
extends f {
    public int o = 0;
    public short p = 0;
    public byte q = 0;
    private Hashtable I = new Hashtable();
    private Hashtable J = new Hashtable();
    private int K = 0;
    private int L = 0;

    public c() {
        this.h = 8;
    }

    public final void a(DataInputStream dataInputStream) {
        try {
            int n2;
            this.f = dataInputStream.readShort();
            this.z = dataInputStream.readUTF();
            this.l = dataInputStream.readShort();
            this.m = dataInputStream.readShort();
            dataInputStream.readByte();
            this.A = dataInputStream.readShort();
            this.s[2] = dataInputStream.readInt();
            this.s[3] = this.s[2];
            this.K = dataInputStream.readShort();
            this.L = dataInputStream.readShort();
            this.s[8] = dataInputStream.readShort();
            this.s[9] = dataInputStream.readShort();
            this.s[11] = dataInputStream.readShort();
            this.s[10] = dataInputStream.readShort();
            this.B = dataInputStream.readInt();
            this.o = dataInputStream.readInt();
            this.p = dataInputStream.readShort();
            this.q = dataInputStream.readByte();
            dataInputStream.readShort();
            dataInputStream.readByte();
            this.F = dataInputStream.readByte();
            this.c(this.F, (int)dataInputStream.readByte());
            boolean bl = false;
            byte by = 0;
            f.c c2 = null;
            int n3 = dataInputStream.readByte();
            if (n3 > 0) {
                for (n2 = 0; n2 < n3; ++n2) {
                    short s2 = dataInputStream.readShort();
                    by = dataInputStream.readByte();
                    c2 = b.a.b((int)s2);
                    if (c2 == null || by <= 0) continue;
                    this.I.put(c2, new Byte(by));
                }
            }
            if ((n3 = dataInputStream.readByte()) > 0) {
                for (n2 = 0; n2 < n3; ++n2) {
                    short s3 = dataInputStream.readShort();
                    by = dataInputStream.readByte();
                    c2 = b.a.b((int)s3);
                    if (c2 == null || by <= 0) continue;
                    this.J.put(c2, new Byte(by));
                }
            }
            this.b = "r" + dataInputStream.readShort();
            this.H = dataInputStream.readShort();
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
        int n2 = e.a.b.c(hashtable.size());
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
        return b.c.a(this.I);
    }

    public final f.c j() {
        return b.c.a(this.J);
    }

    public final void a(f.c c2, int n2) {
        if (c2 == null) {
            return;
        }
        b.c.a(this.I, c2, -1);
        b.c.a(this.J, c2, -1);
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
        n3 = e.a.b.b(n3, n2, 127, 0);
        hashtable.put(c2, new Byte((byte)n3));
    }

    public final void k() {
        f.c c2 = null;
        Enumeration enumeration = this.I.keys();
        while (enumeration.hasMoreElements()) {
            c2 = (f.c)enumeration.nextElement();
            b.a.d(c2.r);
        }
        enumeration = this.J.keys();
        while (enumeration.hasMoreElements()) {
            c2 = (f.c)enumeration.nextElement();
            b.a.d(c2.r);
        }
    }

    public final int l() {
        return this.K + this.x[0];
    }

    public final int m() {
        return this.L + this.x[1];
    }

    public final int n() {
        return this.s[11] + this.x[2];
    }
}

