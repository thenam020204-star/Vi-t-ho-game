/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package a;

import a.b;
import a.f;
import b.c.c;
import d.d;
import d.k;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class a
extends b {
    private Vector B = null;
    private byte C = (byte)-1;
    private byte D = 0;
    private short E = 0;
    private short F = 0;
    private int G = 0;
    private int H = 0;
    private long I = 0L;
    public byte a = 1;
    private int J = 0;
    public short b = 0;
    public short c = 0;
    public short d = 0;
    public boolean e = false;

    public a() {
        super((byte)7);
        this.B = new Vector();
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.f) {
            return;
        }
        super.a(graphics, n2, n3);
    }

    public final void a() {
        int n2;
        int n3;
        Object object;
        Object object2;
        boolean bl;
        if (!this.h || a.f.q != null) {
            return;
        }
        Object object3 = d.k.i();
        if (object3 == null || object3 != a.f.w) {
            return;
        }
        object3 = this;
        if (((a)object3).B.isEmpty()) {
            bl = false;
        } else {
            boolean bl2;
            b.f.b b2 = (b.f.b)((a)object3).B.elementAt(0);
            b2.a();
            if (b2.b()) {
                ((a)object3).B.removeElementAt(0);
            }
            object = object2 = object3;
            if (((a)object2).C != 3 || ((b)object).s % 16 != 0 || ((b)object).t % 16 != 0) {
                bl2 = false;
            } else {
                n3 = a.f.A[0].n();
                int n4 = a.f.A[0].o();
                n2 = Math.abs(n3 - ((b)object).n());
                n4 = Math.abs(n4 - ((b)object).o());
                bl2 = n2 < 2 && n4 < 2 && n2 + n4 <= 2;
            }
            if (bl2) {
                if (((a)object2).a == 2) {
                    if (!((a)object2).e && !a.f.a) {
                        main.a.a.k.a(d.d.a((a)object2));
                    }
                } else {
                    a.f.q = a.f.w.c(((b)object2).n);
                }
            }
            if (((a)object3).B.isEmpty()) {
                super.q();
            }
            bl = true;
        }
        if (bl) {
            return;
        }
        object3 = this;
        if (((b)object3).f && ((b)object3).h && ((a)object3).D == 1 && ((a)object3).I < System.currentTimeMillis() && a.f.w.b((b)object3)) {
            boolean bl3 = false;
            object2 = new int[]{((b)object3).n(), ((b)object3).o()};
            object = null;
            n3 = 0;
            Object var2_5 = null;
            for (n2 = 0; n2 < 3; ++n2) {
                int n5 = b.e.a.c(4);
                object = b.c.c.b((int)object2[0], (int)object2[1], n5);
                if (!a.f.w.b((int)(object[0] << 4), (int)(object[1] << 4)) || (n3 = Math.abs((int)(object[0] - ((a)object3).G))) > ((a)object3).E || (n3 = Math.abs((int)(object[1] - ((a)object3).H))) > ((a)object3).F) continue;
                object2 = object;
                b.f.b b3 = b.f.b.a(((b)object3).n, (byte)n5, (short)1, (byte)n5, (short)2);
                ((a)object3).B.addElement(b3);
            }
            super.q();
        }
    }

    private void q() {
        this.I = System.currentTimeMillis() + 2L + (long)(b.e.a.c(3) * 1000);
    }

    public final void a(DataInputStream dataInputStream) {
        try {
            ((b)((Object)iOException2)).n = dataInputStream.readShort();
            ((b)((Object)iOException2)).p = dataInputStream.readUTF();
            ((b)((Object)iOException2)).i = dataInputStream.readUTF();
            ((b)((Object)iOException2)).s = dataInputStream.readShort() << 4;
            ((b)((Object)iOException2)).t = dataInputStream.readShort() << 4;
            ((a)((Object)iOException2)).C = dataInputStream.readByte();
            ((b)((Object)iOException2)).f = dataInputStream.readByte() == 1;
            ((b)((Object)iOException2)).g = dataInputStream.readByte() == 1;
            ((b)((Object)iOException2)).h = dataInputStream.readByte() == 1;
            ((a)((Object)iOException2)).D = dataInputStream.readByte();
            ((a)((Object)iOException2)).E = dataInputStream.readShort();
            ((a)((Object)iOException2)).F = dataInputStream.readShort();
            ((a)((Object)iOException2)).G = ((b)((Object)iOException2)).n();
            ((a)((Object)iOException2)).H = ((b)((Object)iOException2)).o();
            ((b)((Object)iOException2)).d(dataInputStream.readByte());
            ((b)((Object)iOException2)).A = dataInputStream.readBoolean();
            ((b)((Object)iOException2)).l = ((b)((Object)iOException2)).A ? 0 : a.b.a((int)((b)((Object)iOException2)).r, false);
            ((a)((Object)iOException2)).a = dataInputStream.readByte();
            if (((a)((Object)iOException2)).a == 2) {
                ((a)((Object)iOException2)).J = dataInputStream.readInt();
                ((a)((Object)iOException2)).b = dataInputStream.readShort();
                ((a)((Object)iOException2)).c = dataInputStream.readShort();
                ((a)((Object)iOException2)).d = dataInputStream.readShort();
            }
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public final boolean b() {
        return (this.J >> 24 & 1) != 0;
    }

    public final boolean c() {
        return (this.J >> 24 & 2) != 0;
    }

    public final int d() {
        return this.J >> 16 & 0xFF;
    }

    public final int e() {
        return this.J >> 8 & 0xFF;
    }

    public final int f() {
        return this.J & 0xFF;
    }

    public final byte g() {
        return this.C;
    }

    public final byte[] h() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeShort(((b)object).n());
            dataOutputStream.writeShort(((b)object).o());
            int n2 = ((b)object).f ? 1 : 0;
            int n3 = ((b)object).g ? 1 : 0;
            int n4 = ((b)object).h ? 1 : 0;
            n2 = (byte)(n2 << 2 | n3 << 1 | n4);
            dataOutputStream.writeByte(n2);
            dataOutputStream.writeByte(((b)object).r);
            dataOutputStream.writeByte(((b)object).l);
            dataOutputStream.writeByte(((a)object).C);
            dataOutputStream.writeUTF(((b)object).i == null ? "" : ((b)object).i);
            dataOutputStream.writeByte(((b)object).k == null ? -1 : ((b)object).k.d());
            dataOutputStream.writeBoolean(((b)object).k == null ? false : ((b)object).k.h());
            Object object = byteArrayOutputStream.toByteArray();
            return object;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return null;
        }
        finally {
            b.e.a.a(byteArrayOutputStream);
            b.e.a.a(dataOutputStream);
        }
    }

    public final void b(DataInputStream dataInputStream) {
        try {
            short s = dataInputStream.readShort();
            this.s = s << 4;
            s = dataInputStream.readShort();
            this.t = s << 4;
            s = dataInputStream.readByte();
            this.f = (s & 4) != 0;
            this.g = (s & 2) != 0;
            this.h = (s & 1) != 0;
            this.d(dataInputStream.readByte());
            this.l = dataInputStream.readByte();
            this.C = dataInputStream.readByte();
            this.i = dataInputStream.readUTF();
            this.m = dataInputStream.readByte();
            this.x = dataInputStream.readBoolean();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public final void a(byte by) {
        this.C = by;
    }

    public final void b(byte by) {
        this.D = by;
    }

    public final void a(short s) {
        this.E = s;
    }

    public final void b(short s) {
        this.F = s;
    }
}

