/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b;

import b.a;
import b.b;
import c.r;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import main.c;

public final class e
extends b {
    private Vector w = null;
    public boolean o = false;
    public boolean p = true;
    private byte x = (byte)-1;
    private byte y = 0;
    private short z = 0;
    private short A = 0;
    private int B = 0;
    private int C = 0;
    private long D = 0L;
    public boolean q = false;
    public byte r = 1;
    private int E = 0;
    public short s = 0;
    public short t = 0;
    public short u = 0;
    public boolean v = false;

    public e() {
        super((byte)7);
        this.w = new Vector();
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
        if (!this.p || b.a.r != null) {
            return;
        }
        Object object3 = d.a.h();
        if (object3 == null || object3 != b.a.x) {
            return;
        }
        object3 = this;
        if (((e)object3).w.isEmpty()) {
            bl = false;
        } else {
            boolean bl2;
            c c2 = (c)((e)object3).w.elementAt(0);
            c2.a();
            if (c2.b()) {
                ((e)object3).w.removeElementAt(0);
            }
            object = object2 = object3;
            if (((e)object2).x != 3 || ((b)object).j % 16 != 0 || ((b)object).k % 16 != 0) {
                bl2 = false;
            } else {
                n3 = b.a.B[0].f();
                int n4 = b.a.B[0].g();
                n2 = Math.abs(n3 - ((b)object).f());
                n4 = Math.abs(n4 - ((b)object).g());
                bl2 = n2 < 2 && n4 < 2 && n2 + n4 <= 2;
            }
            if (bl2) {
                if (((e)object2).r == 2) {
                    if (!((e)object2).v) {
                        main.b.a.i.a(d.c.a((e)object2));
                    }
                } else {
                    b.a.r = b.a.a(b.a.x.c, ((b)object2).f);
                }
            }
            if (((e)object3).w.isEmpty()) {
                super.q();
            }
            bl = true;
        }
        if (bl) {
            return;
        }
        object3 = this;
        if (((b)object3).a && ((e)object3).p && ((e)object3).y == 1 && ((e)object3).D < System.currentTimeMillis() && b.a.x.b((b)object3)) {
            boolean bl3 = false;
            object2 = new int[]{((b)object3).f(), ((b)object3).g()};
            object = null;
            n3 = 0;
            Object var2_5 = null;
            for (n2 = 0; n2 < 3; ++n2) {
                int n5 = e.a.b.c(4);
                object = c.r.b((int)object2[0], (int)object2[1], n5);
                if (!b.a.x.b((int)(object[0] << 4), (int)(object[1] << 4)) || (n3 = Math.abs((int)(object[0] - ((e)object3).B))) > ((e)object3).z || (n3 = Math.abs((int)(object[1] - ((e)object3).C))) > ((e)object3).A) continue;
                object2 = object;
                c c3 = main.c.a(((b)object3).f, (byte)n5, (short)1, (byte)n5, (short)2);
                ((e)object3).w.addElement(c3);
            }
            super.q();
        }
    }

    private void q() {
        this.D = System.currentTimeMillis() + 2L + (long)(e.a.b.c(3) * 1000);
    }

    public final void a(DataInputStream dataInputStream) {
        try {
            ((b)((Object)iOException2)).f = dataInputStream.readShort();
            ((b)((Object)iOException2)).b = dataInputStream.readUTF();
            ((b)((Object)iOException2)).j = dataInputStream.readShort() << 4;
            ((b)((Object)iOException2)).k = dataInputStream.readShort() << 4;
            ((e)((Object)iOException2)).x = dataInputStream.readByte();
            ((b)((Object)iOException2)).a = dataInputStream.readByte() == 1;
            ((e)((Object)iOException2)).o = dataInputStream.readByte() == 1;
            ((e)((Object)iOException2)).p = dataInputStream.readByte() == 1;
            ((e)((Object)iOException2)).y = dataInputStream.readByte();
            ((e)((Object)iOException2)).z = dataInputStream.readShort();
            ((e)((Object)iOException2)).A = dataInputStream.readShort();
            ((e)((Object)iOException2)).B = ((b)((Object)iOException2)).f();
            ((e)((Object)iOException2)).C = ((b)((Object)iOException2)).g();
            ((b)((Object)iOException2)).b(dataInputStream.readByte());
            ((e)((Object)iOException2)).q = dataInputStream.readBoolean();
            ((b)((Object)iOException2)).e = ((e)((Object)iOException2)).q ? 0 : b.b.a((int)((b)((Object)iOException2)).i, false);
            ((e)((Object)iOException2)).r = dataInputStream.readByte();
            if (((e)((Object)iOException2)).r == 2) {
                ((e)((Object)iOException2)).E = dataInputStream.readInt();
                ((e)((Object)iOException2)).s = dataInputStream.readShort();
                ((e)((Object)iOException2)).t = dataInputStream.readShort();
                ((e)((Object)iOException2)).u = dataInputStream.readShort();
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
        return (this.E >> 24 & 1) != 0;
    }

    public final boolean k() {
        return (this.E >> 24 & 2) != 0;
    }

    public final int l() {
        return this.E >> 16 & 0xFF;
    }

    public final int m() {
        return this.E >> 8 & 0xFF;
    }

    public final int n() {
        return this.E & 0xFF;
    }

    public final byte o() {
        return this.x;
    }

    public final byte[] p() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeShort(((b)object).f());
            dataOutputStream.writeShort(((b)object).g());
            int n2 = ((b)object).a ? 1 : 0;
            int n3 = ((e)object).o ? 1 : 0;
            int n4 = ((e)object).p ? 1 : 0;
            n2 = (byte)(n2 << 2 | n3 << 1 | n4);
            dataOutputStream.writeByte(n2);
            dataOutputStream.writeByte(((b)object).i);
            dataOutputStream.writeByte(((b)object).e);
            dataOutputStream.writeByte(((e)object).x);
            dataOutputStream.writeUTF(((b)object).b);
            Object object = byteArrayOutputStream.toByteArray();
            return object;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return null;
        }
        finally {
            e.a.b.a(byteArrayOutputStream);
            e.a.b.a(dataOutputStream);
        }
    }

    public final void b(DataInputStream dataInputStream) {
        try {
            short s2 = dataInputStream.readShort();
            this.j = s2 << 4;
            s2 = dataInputStream.readShort();
            this.k = s2 << 4;
            s2 = dataInputStream.readByte();
            this.a = (s2 & 4) != 0;
            this.o = (s2 & 2) != 0;
            this.p = (s2 & 1) != 0;
            this.b(dataInputStream.readByte());
            this.e = dataInputStream.readByte();
            this.x = dataInputStream.readByte();
            this.b = dataInputStream.readUTF();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public final void c(byte by) {
        this.x = by;
    }

    public final void d(byte by) {
        this.y = by;
    }

    public final void a(short s2) {
        this.z = s2;
    }

    public final void b(short s2) {
        this.A = s2;
    }
}

