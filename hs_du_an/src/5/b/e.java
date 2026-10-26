/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b;

import b.a;
import b.b;
import c.c;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class e
extends b {
    private Vector B = null;
    private byte C = (byte)-1;
    private byte D = 0;
    private short E = 0;
    private short F = 0;
    private int G = 0;
    private int H = 0;
    private long I = 0L;
    public byte w = 1;
    private int J = 0;
    public short x = 0;
    public short y = 0;
    public short z = 0;
    public boolean A = false;

    public e() {
        super((byte)7);
        this.B = new Vector();
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.a) {
            return;
        }
        super.a(graphics, n2, n3);
    }

    public final void i() {
        int n2;
        int n3;
        Object object;
        Object object2;
        boolean bl;
        if (!this.c || b.a.q != null) {
            return;
        }
        Object object3 = c.a.h();
        if (object3 == null || object3 != b.a.w) {
            return;
        }
        object3 = this;
        if (((e)object3).B.isEmpty()) {
            bl = false;
        } else {
            boolean bl2;
            a.d.b b2 = (a.d.b)((e)object3).B.elementAt(0);
            b2.a();
            if (b2.b()) {
                ((e)object3).B.removeElementAt(0);
            }
            object = object2 = object3;
            if (((e)object2).C != 3 || ((b)object).n % 16 != 0 || ((b)object).o % 16 != 0) {
                bl2 = false;
            } else {
                n3 = b.a.A[0].f();
                int n4 = b.a.A[0].g();
                n2 = Math.abs(n3 - ((b)object).f());
                n4 = Math.abs(n4 - ((b)object).g());
                bl2 = n2 < 2 && n4 < 2 && n2 + n4 <= 2;
            }
            if (bl2) {
                if (((e)object2).w == 2) {
                    if (!((e)object2).A && !b.a.a) {
                        main.a.a.k.a(c.c.a((e)object2));
                    }
                } else {
                    b.a.q = b.a.w.c(((b)object2).i);
                }
            }
            if (((e)object3).B.isEmpty()) {
                super.q();
            }
            bl = true;
        }
        if (bl) {
            return;
        }
        object3 = this;
        if (((b)object3).a && ((b)object3).c && ((e)object3).D == 1 && ((e)object3).I < System.currentTimeMillis() && b.a.w.b((b)object3)) {
            boolean bl3 = false;
            object2 = new int[]{((b)object3).f(), ((b)object3).g()};
            object = null;
            n3 = 0;
            Object var2_5 = null;
            for (n2 = 0; n2 < 3; ++n2) {
                int n5 = a.c.b.c(4);
                object = a.b.a.b((int)object2[0], (int)object2[1], n5);
                if (!b.a.w.b((int)(object[0] << 4), (int)(object[1] << 4)) || (n3 = Math.abs((int)(object[0] - ((e)object3).G))) > ((e)object3).E || (n3 = Math.abs((int)(object[1] - ((e)object3).H))) > ((e)object3).F) continue;
                object2 = object;
                a.d.b b3 = a.d.b.a(((b)object3).i, (byte)n5, (short)1, (byte)n5, (short)2);
                ((e)object3).B.addElement(b3);
            }
            super.q();
        }
    }

    private void q() {
        this.I = System.currentTimeMillis() + 2L + (long)(a.c.b.c(3) * 1000);
    }

    public final void a(DataInputStream dataInputStream) {
        try {
            ((b)((Object)iOException2)).i = dataInputStream.readShort();
            ((b)((Object)iOException2)).k = dataInputStream.readUTF();
            ((b)((Object)iOException2)).d = dataInputStream.readUTF();
            ((b)((Object)iOException2)).n = dataInputStream.readShort() << 4;
            ((b)((Object)iOException2)).o = dataInputStream.readShort() << 4;
            ((e)((Object)iOException2)).C = dataInputStream.readByte();
            ((b)((Object)iOException2)).a = dataInputStream.readByte() == 1;
            ((b)((Object)iOException2)).b = dataInputStream.readByte() == 1;
            ((b)((Object)iOException2)).c = dataInputStream.readByte() == 1;
            ((e)((Object)iOException2)).D = dataInputStream.readByte();
            ((e)((Object)iOException2)).E = dataInputStream.readShort();
            ((e)((Object)iOException2)).F = dataInputStream.readShort();
            ((e)((Object)iOException2)).G = ((b)((Object)iOException2)).f();
            ((e)((Object)iOException2)).H = ((b)((Object)iOException2)).g();
            ((b)((Object)iOException2)).b(dataInputStream.readByte());
            ((b)((Object)iOException2)).v = dataInputStream.readBoolean();
            ((b)((Object)iOException2)).g = ((b)((Object)iOException2)).v ? 0 : b.b.a((int)((b)((Object)iOException2)).m, false);
            ((e)((Object)iOException2)).w = dataInputStream.readByte();
            if (((e)((Object)iOException2)).w == 2) {
                ((e)((Object)iOException2)).J = dataInputStream.readInt();
                ((e)((Object)iOException2)).x = dataInputStream.readShort();
                ((e)((Object)iOException2)).y = dataInputStream.readShort();
                ((e)((Object)iOException2)).z = dataInputStream.readShort();
            }
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public final boolean j() {
        return (this.J >> 24 & 1) != 0;
    }

    public final boolean k() {
        return (this.J >> 24 & 2) != 0;
    }

    public final int l() {
        return this.J >> 16 & 0xFF;
    }

    public final int m() {
        return this.J >> 8 & 0xFF;
    }

    public final int n() {
        return this.J & 0xFF;
    }

    public final byte o() {
        return this.C;
    }

    public final byte[] p() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeShort(((b)object).f());
            dataOutputStream.writeShort(((b)object).g());
            int n2 = ((b)object).a ? 1 : 0;
            int n3 = ((b)object).b ? 1 : 0;
            int n4 = ((b)object).c ? 1 : 0;
            n2 = (byte)(n2 << 2 | n3 << 1 | n4);
            dataOutputStream.writeByte(n2);
            dataOutputStream.writeByte(((b)object).m);
            dataOutputStream.writeByte(((b)object).g);
            dataOutputStream.writeByte(((e)object).C);
            dataOutputStream.writeUTF(((b)object).d == null ? "" : ((b)object).d);
            dataOutputStream.writeByte(((b)object).f == null ? -1 : ((b)object).f.d());
            dataOutputStream.writeBoolean(((b)object).f == null ? false : ((b)object).f.h());
            Object object = byteArrayOutputStream.toByteArray();
            return object;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return null;
        }
        finally {
            a.c.b.a(byteArrayOutputStream);
            a.c.b.a(dataOutputStream);
        }
    }

    public final void b(DataInputStream dataInputStream) {
        try {
            short s = dataInputStream.readShort();
            this.n = s << 4;
            s = dataInputStream.readShort();
            this.o = s << 4;
            s = dataInputStream.readByte();
            this.a = (s & 4) != 0;
            this.b = (s & 2) != 0;
            this.c = (s & 1) != 0;
            this.b(dataInputStream.readByte());
            this.g = dataInputStream.readByte();
            this.C = dataInputStream.readByte();
            this.d = dataInputStream.readUTF();
            this.h = dataInputStream.readByte();
            this.s = dataInputStream.readBoolean();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public final void c(byte by) {
        this.C = by;
    }

    public final void d(byte by) {
        this.D = by;
    }

    public final void a(short s) {
        this.E = s;
    }

    public final void b(short s) {
        this.F = s;
    }
}

