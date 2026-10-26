/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package d;

import b.j;
import b.q;
import d.a;
import d.b;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import main.d;

public final class f
extends b {
    private Vector s = null;
    private boolean t = false;
    private boolean u = true;
    private byte v = (byte)-1;
    private byte w = 0;
    private short x = 0;
    private short y = 0;
    private int z = 0;
    private int A = 0;
    private long B = 0L;
    public boolean r = false;

    public f() {
        super((byte)7);
        this.s = new Vector();
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.a) {
            return;
        }
        super.a(graphics, n2, n3);
    }

    public final void a(int n2, int n3) {
    }

    public final void a() {
        int n2;
        int n3;
        Object object;
        boolean bl;
        if (!this.u || d.a.s != null) {
            return;
        }
        j j2 = main.b.a.i.h();
        if (j2 == null || j2 != d.a.y) {
            return;
        }
        j2 = this;
        if (((f)j2).s.isEmpty()) {
            bl = false;
        } else {
            d d2;
            d d3 = (d)((f)j2).s.elementAt(0);
            d3.a();
            if (d3.b()) {
                ((f)j2).s.removeElementAt(0);
            }
            object = j2;
            if (((f)object).v != 3 || ((j)object).l() % 16 != 0 || ((j)object).m() % 16 != 0) {
                d2 = null;
            } else {
                int n4 = d.a.C[0].i();
                n3 = d.a.C[0].j();
                int n5 = Math.abs(n4 - ((b)object).i());
                n2 = Math.abs(n3 - ((b)object).j());
                d2 = d.a.s = n5 >= 2 || n2 >= 2 || n5 + n2 > 2 ? null : d.a.a(d.a.y.d, ((b)object).f);
            }
            if (((f)j2).s.isEmpty()) {
                super.u();
            }
            bl = true;
        }
        if (bl) {
            return;
        }
        j2 = this;
        if (((b)j2).a && ((f)j2).u && ((f)j2).w == 1 && ((f)j2).B < System.currentTimeMillis() && d.a.y.b((b)j2)) {
            boolean bl2 = false;
            object = new int[]{((b)j2).i(), ((b)j2).j()};
            int[] nArray = null;
            n3 = 0;
            Object var2_5 = null;
            for (n2 = 0; n2 < 3; ++n2) {
                int n6 = h.b.c(4);
                nArray = b.q.b((int)object[0], (int)object[1], n6);
                if (!d.a.y.b(nArray[0] << 4, nArray[1] << 4) || (n3 = Math.abs(nArray[0] - ((f)j2).z)) > ((f)j2).x || (n3 = Math.abs(nArray[1] - ((f)j2).A)) > ((f)j2).y) continue;
                object = nArray;
                d d4 = main.d.a(((b)j2).f, (byte)n6, (short)1, (byte)n6, (short)2);
                ((f)j2).s.addElement(d4);
            }
            super.u();
        }
    }

    private void u() {
        this.B = System.currentTimeMillis() + 2L + (long)(h.b.c(3) * 1000);
    }

    public final void a(DataInputStream dataInputStream) {
        try {
            ((b)((Object)iOException2)).f = dataInputStream.readShort();
            ((b)((Object)iOException2)).b = dataInputStream.readUTF();
            ((b)((Object)iOException2)).b(dataInputStream.readShort() << 4);
            ((b)((Object)iOException2)).c(dataInputStream.readShort() << 4);
            ((f)((Object)iOException2)).v = dataInputStream.readByte();
            ((b)((Object)iOException2)).a = dataInputStream.readByte() == 1;
            ((f)((Object)iOException2)).t = dataInputStream.readByte() == 1;
            ((f)((Object)iOException2)).u = dataInputStream.readByte() == 1;
            ((f)((Object)iOException2)).w = dataInputStream.readByte();
            ((f)((Object)iOException2)).x = dataInputStream.readShort();
            ((f)((Object)iOException2)).y = dataInputStream.readShort();
            ((f)((Object)iOException2)).z = ((b)((Object)iOException2)).i();
            ((f)((Object)iOException2)).A = ((b)((Object)iOException2)).j();
            ((b)((Object)iOException2)).b(dataInputStream.readByte());
            ((f)((Object)iOException2)).r = dataInputStream.readBoolean();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public final byte q() {
        return this.v;
    }

    public final byte[] r() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeShort(((b)object).i());
            dataOutputStream.writeShort(((b)object).j());
            int n2 = ((b)object).a ? 1 : 0;
            int n3 = ((f)object).t ? 1 : 0;
            int n4 = ((f)object).u ? 1 : 0;
            n2 = (byte)(n2 << 2 | n3 << 1 | n4);
            dataOutputStream.writeByte(n2);
            dataOutputStream.writeByte(((b)object).h());
            dataOutputStream.writeByte(((b)object).e);
            dataOutputStream.writeByte(((f)object).v);
            Object object = byteArrayOutputStream.toByteArray();
            return object;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return null;
        }
        finally {
            h.b.a(byteArrayOutputStream);
            h.b.a(dataOutputStream);
        }
    }

    public final void b(DataInputStream dataInputStream) {
        try {
            short s2 = dataInputStream.readShort();
            this.b(s2 << 4);
            s2 = dataInputStream.readShort();
            this.c(s2 << 4);
            s2 = dataInputStream.readByte();
            this.a = (s2 & 4) != 0;
            this.t = (s2 & 2) != 0;
            this.u = (s2 & 1) != 0;
            this.b(dataInputStream.readByte());
            this.e = dataInputStream.readByte();
            this.v = dataInputStream.readByte();
            this.b(this.e, -1);
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public final void c(byte by) {
        this.v = by;
    }

    public final void d(byte by) {
        this.w = by;
    }

    public final void a(short s2) {
        this.x = s2;
    }

    public final void b(short s2) {
        this.y = s2;
    }

    public final void e() {
    }

    public final void b() {
    }

    public final boolean s() {
        return this.t;
    }

    public final boolean t() {
        return this.u;
    }

    public final void b(boolean bl) {
        this.t = bl;
    }

    public final void c(boolean bl) {
        this.u = bl;
    }

    public final String d() {
        return null;
    }
}

