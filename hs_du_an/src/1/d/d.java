/*
 * Decompiled with CFR 0.152.
 */
package d;

import c.b;
import d.a;
import d.g;
import java.io.DataInputStream;
import java.util.Enumeration;
import java.util.Hashtable;

public final class d
extends g {
    public int r = 0;
    public short s = 0;
    public byte t = 0;
    private Hashtable N = new Hashtable();
    private Hashtable O = new Hashtable();

    public d() {
        this.k = 8;
    }

    public final void a(DataInputStream dataInputStream) {
        try {
            int n2;
            this.f = dataInputStream.readShort();
            this.B = dataInputStream.readUTF();
            this.n = dataInputStream.readShort();
            this.o = dataInputStream.readShort();
            dataInputStream.readByte();
            this.C = dataInputStream.readShort();
            this.J[2] = dataInputStream.readInt();
            this.J[3] = this.J[2];
            this.E = dataInputStream.readShort();
            this.F = dataInputStream.readShort();
            this.J[8] = dataInputStream.readShort();
            this.J[9] = dataInputStream.readShort();
            this.J[11] = dataInputStream.readShort();
            this.J[10] = dataInputStream.readShort();
            this.D = dataInputStream.readInt();
            this.r = dataInputStream.readInt();
            this.s = dataInputStream.readShort();
            this.t = dataInputStream.readByte();
            dataInputStream.readShort();
            dataInputStream.readByte();
            this.K = dataInputStream.readByte();
            this.c(this.K, (int)dataInputStream.readByte());
            boolean bl = false;
            byte by = 0;
            b b2 = null;
            int n3 = dataInputStream.readByte();
            if (n3 > 0) {
                for (n2 = 0; n2 < n3; ++n2) {
                    short s2 = dataInputStream.readShort();
                    by = dataInputStream.readByte();
                    b2 = d.a.b((int)s2);
                    if (b2 == null || by <= 0) continue;
                    this.N.put(b2, new Byte(by));
                }
            }
            if ((n3 = dataInputStream.readByte()) > 0) {
                for (n2 = 0; n2 < n3; ++n2) {
                    short s3 = dataInputStream.readShort();
                    by = dataInputStream.readByte();
                    b2 = d.a.b((int)s3);
                    if (b2 == null || by <= 0) continue;
                    this.O.put(b2, new Byte(by));
                }
            }
            this.b = "r" + dataInputStream.readShort();
            this.M = dataInputStream.readShort();
            super.B();
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final b q() {
        if (((d)((Object)enumeration)).N.isEmpty()) {
            return null;
        }
        int n2 = h.b.c(((d)((Object)enumeration)).N.size());
        Enumeration enumeration = ((d)((Object)enumeration)).N.keys();
        Object var2_2 = null;
        int n3 = 0;
        while (enumeration.hasMoreElements()) {
            var2_2 = enumeration.nextElement();
            if (n3 == n2) {
                return var2_2;
            }
            ++n3;
        }
        return null;
    }

    public final void a(b b2, int n2) {
        if (b2 == null) {
            return;
        }
        d.d.a(this.N, b2, -1);
        d.d.a(this.O, b2, -1);
    }

    private static void a(Hashtable hashtable, b b2, int n2) {
        if (hashtable == null || b2 == null || n2 == 0) {
            return;
        }
        Byte by = (Byte)hashtable.get(b2);
        if (by == null) {
            return;
        }
        int n3 = by.byteValue();
        if ((n3 = h.b.b(n3, n2, 127, 0)) <= 0) {
            hashtable.remove(b2);
            return;
        }
        hashtable.put(b2, new Byte((byte)n3));
    }

    public final void r() {
        b b2 = null;
        Enumeration enumeration = this.N.keys();
        while (enumeration.hasMoreElements()) {
            b2 = (b)enumeration.nextElement();
            d.a.d(b2.p);
        }
        enumeration = this.O.keys();
        while (enumeration.hasMoreElements()) {
            b2 = (b)enumeration.nextElement();
            d.a.d(b2.p);
        }
    }
}

