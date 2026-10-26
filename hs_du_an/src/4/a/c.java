/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package a;

import a.f;
import a.g;
import b.e.a;
import b.f.b;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class c {
    public static Hashtable a = new Hashtable();
    private String c = "";
    private int d = 0;
    private String e = null;
    private int f = 0;
    private int g = 0;
    private byte[] h = null;
    private int i = 0;
    private g[] j = null;
    private g[] k = null;
    private b.d.a[] l = new b.d.a[256];
    private Hashtable m = null;
    private int[][] n = null;
    public static int b = 0;
    private boolean o = true;
    private Vector p = new Vector();
    private boolean q = false;
    private boolean r = true;
    private boolean s = true;
    private boolean t = true;
    private short u = 0;
    private b v = null;

    public c() {
        this.m = new Hashtable();
    }

    public final boolean a(DataInputStream dataInputStream) {
        try {
            int n2;
            this.q = dataInputStream.readBoolean();
            int n3 = dataInputStream.readByte();
            this.r = (n3 & 1) != 0;
            this.t = (n3 & 2) != 0;
            this.s = dataInputStream.readBoolean();
            this.d = dataInputStream.readInt();
            this.e = dataInputStream.readUTF();
            this.f = dataInputStream.readInt();
            this.g = dataInputStream.readInt();
            n3 = dataInputStream.readShort();
            this.h = new byte[n3];
            dataInputStream.read(this.h);
            this.i = dataInputStream.readInt();
            this.c(dataInputStream);
            this.n = new int[dataInputStream.readByte()][a.g.D.length];
            for (n3 = 0; n3 < this.n.length; ++n3) {
                for (n2 = 0; n2 < this.n[n3].length; ++n2) {
                    this.n[n3][n2] = dataInputStream.readShort();
                }
            }
            n3 = dataInputStream.readByte();
            this.k = new g[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                this.k[n2] = new g();
                this.k[n2].b(dataInputStream);
                if (!this.k[n2].h()) continue;
                this.k[n2].j = a.c.a(this.k[n2].i);
                this.k[n2].a(1, -1);
            }
            this.j = a.f.a(this.k);
            DataInputStream dataInputStream2 = dataInputStream;
            c c2 = this;
            int n4 = dataInputStream2.readShort();
            for (int i2 = 0; i2 < n4; ++i2) {
                short s = dataInputStream2.readShort();
                short s2 = dataInputStream2.readShort();
                if (s2 <= 0) continue;
                byte[] byArray = new byte[s2];
                dataInputStream2.read(byArray);
                c2.m.put(new Integer(s), byArray);
            }
            this.c = dataInputStream.readUTF();
            this.o = dataInputStream.readBoolean();
            this.d(dataInputStream);
            if (dataInputStream.readByte() == 1) {
                this.v = b.f.b.a(dataInputStream, null);
            }
            this.u = dataInputStream.readShort();
            return true;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return false;
        }
    }

    public final boolean b(DataInputStream dataInputStream) {
        try {
            int n2;
            this.q = dataInputStream.readBoolean();
            this.r = dataInputStream.readBoolean();
            this.s = dataInputStream.readBoolean();
            this.d = dataInputStream.readInt();
            this.e = dataInputStream.readUTF();
            this.f = dataInputStream.readInt();
            this.g = dataInputStream.readInt();
            int n3 = dataInputStream.readShort();
            this.h = new byte[n3];
            dataInputStream.read(this.h);
            this.i = dataInputStream.readInt();
            this.c(dataInputStream);
            this.n = new int[dataInputStream.readByte()][a.g.D.length];
            for (n3 = 0; n3 < this.n.length; ++n3) {
                for (n2 = 0; n2 < this.n[n3].length; ++n2) {
                    this.n[n3][n2] = dataInputStream.readShort();
                }
            }
            n3 = dataInputStream.readByte();
            this.k = new g[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                this.k[n2] = new g();
                this.k[n2].b(dataInputStream);
                if (!this.k[n2].h()) continue;
                this.k[n2].j = a.c.a(this.k[n2].i);
                this.k[n2].a(1, -1);
            }
            this.j = a.f.a(this.k);
            DataInputStream dataInputStream2 = dataInputStream;
            c c2 = this;
            int n4 = dataInputStream2.readShort();
            for (int i2 = 0; i2 < n4; ++i2) {
                int n5 = b.e.a.a((int)dataInputStream2.readByte());
                short s = dataInputStream2.readShort();
                if (s <= 0) continue;
                byte[] byArray = new byte[s];
                dataInputStream2.read(byArray);
                c2.m.put(new Integer(n5), byArray);
            }
            this.c = dataInputStream.readUTF();
            this.o = dataInputStream.readBoolean();
            this.d(dataInputStream);
            if (dataInputStream.readByte() == 1) {
                this.v = b.f.b.a(dataInputStream, null);
            }
            this.u = dataInputStream.readShort();
            return true;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return false;
        }
    }

    public final byte[] a() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeBoolean(this.q);
            int n2 = 0;
            n2 = (int)(0 | (this.r ? 1 : 0));
            n2 = (byte)(n2 | (this.t ? 2 : 0));
            dataOutputStream.writeByte(n2);
            dataOutputStream.writeBoolean(this.s);
            dataOutputStream.writeInt(this.d);
            dataOutputStream.writeUTF(this.e);
            dataOutputStream.writeInt(this.f);
            dataOutputStream.writeInt(this.g);
            dataOutputStream.writeShort(this.h.length);
            ((OutputStream)dataOutputStream).write(this.h);
            dataOutputStream.writeInt(this.i);
            this.b(dataOutputStream);
            dataOutputStream.writeByte(this.n.length);
            for (n2 = 0; n2 < this.n.length; ++n2) {
                for (int i2 = 0; i2 < this.n[n2].length; ++i2) {
                    dataOutputStream.writeShort(this.n[n2][i2]);
                }
            }
            dataOutputStream.writeByte(this.k.length);
            for (n2 = 0; n2 < this.k.length; ++n2) {
                ((OutputStream)dataOutputStream).write(this.k[n2].A());
            }
            this.a(dataOutputStream);
            dataOutputStream.writeUTF(this.c);
            dataOutputStream.writeBoolean(this.o);
            this.c(dataOutputStream);
            byte[] byArray = null;
            if (this.v != null) {
                byArray = this.v.e();
            }
            if (byArray != null) {
                dataOutputStream.writeByte(1);
                ((OutputStream)dataOutputStream).write(byArray);
            } else {
                dataOutputStream.writeByte(0);
            }
            dataOutputStream.writeShort(this.u);
            byte[] byArray2 = byteArrayOutputStream.toByteArray();
            return byArray2;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return null;
        }
        finally {
            b.e.a.a(byteArrayOutputStream);
            b.e.a.a(dataOutputStream);
        }
    }

    public final byte[] b() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            int n2;
            dataOutputStream.writeBoolean(this.q);
            dataOutputStream.writeBoolean(this.r);
            dataOutputStream.writeBoolean(this.s);
            dataOutputStream.writeInt(this.d);
            dataOutputStream.writeUTF(this.e);
            dataOutputStream.writeInt(this.f);
            dataOutputStream.writeInt(this.g);
            dataOutputStream.writeShort(this.h.length);
            ((OutputStream)dataOutputStream).write(this.h);
            dataOutputStream.writeInt(this.i);
            this.b(dataOutputStream);
            dataOutputStream.writeByte(this.n.length);
            for (n2 = 0; n2 < this.n.length; ++n2) {
                for (int i2 = 0; i2 < this.n[n2].length; ++i2) {
                    dataOutputStream.writeShort(this.n[n2][i2]);
                }
            }
            dataOutputStream.writeByte(this.k.length);
            for (n2 = 0; n2 < this.k.length; ++n2) {
                ((OutputStream)dataOutputStream).write(this.k[n2].A());
            }
            dataOutputStream.writeShort(0);
            dataOutputStream.writeUTF(this.c);
            dataOutputStream.writeBoolean(this.o);
            this.c(dataOutputStream);
            byte[] byArray = null;
            if (this.v != null) {
                byArray = this.v.e();
            }
            if (byArray != null) {
                dataOutputStream.writeByte(1);
                ((OutputStream)dataOutputStream).write(byArray);
            } else {
                dataOutputStream.writeByte(0);
            }
            dataOutputStream.writeShort(this.u);
            byte[] byArray2 = byteArrayOutputStream.toByteArray();
            return byArray2;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return null;
        }
        finally {
            b.e.a.a(byteArrayOutputStream);
            b.e.a.a(dataOutputStream);
        }
    }

    private void a(DataOutputStream dataOutputStream) {
        short s = (short)this.m.size();
        dataOutputStream.writeShort(s);
        Enumeration enumeration = this.m.keys();
        Object object = null;
        object = null;
        while (enumeration.hasMoreElements()) {
            object = (Integer)enumeration.nextElement();
            dataOutputStream.writeShort(((Integer)object).shortValue());
            byte[] byArray = (byte[])this.m.get(object);
            object = byArray;
            if (byArray == null) {
                dataOutputStream.writeShort(0);
                continue;
            }
            dataOutputStream.writeShort(((Object)object).length);
            ((OutputStream)dataOutputStream).write((byte[])object);
        }
    }

    private void b(DataOutputStream dataOutputStream) {
        int n2;
        if (this.l == null) {
            dataOutputStream.writeShort(0);
            return;
        }
        int n3 = 0;
        for (n2 = 0; n2 < this.l.length; ++n2) {
            if (this.l[n2] == null) continue;
            ++n3;
        }
        dataOutputStream.writeByte(b.e.a.b(n3));
        for (n2 = 0; n2 < this.l.length; ++n2) {
            if (this.l[n2] == null) continue;
            dataOutputStream.writeByte(n2);
            dataOutputStream.writeShort(this.l[n2].a.c);
            dataOutputStream.writeByte(this.l[n2].c);
        }
    }

    private void c(DataInputStream dataInputStream) {
        int n2 = b.e.a.a(dataInputStream.readByte());
        if (n2 <= 0) {
            return;
        }
        int n3 = 0;
        b.d.a a2 = null;
        for (int i2 = 0; i2 < n2; ++i2) {
            n3 = b.e.a.a((int)dataInputStream.readByte());
            a2 = new b.d.a();
            new b.d.a().a = a.f.b((int)dataInputStream.readShort());
            a2.c = b.e.a.a((int)dataInputStream.readByte());
            this.l[n3] = a2;
        }
    }

    public final void c() {
        this.q = a.f.l[1];
        this.r = a.f.l[2];
        this.t = a.f.l[3];
        this.s = a.f.d;
        this.d = a.f.w.k;
        this.e = a.f.w.p();
        this.f = a.f.A[0].n();
        this.g = a.f.A[0].o();
        this.h = a.f.B;
        this.i = a.f.C;
        this.k = new g[a.f.j.length];
        for (int i2 = 0; i2 < a.f.j.length; ++i2) {
            this.k[i2] = a.f.j[i2].I();
            if (!this.k[i2].h()) continue;
            this.k[i2].j = a.c.a(this.k[i2].i);
            this.k[i2].a(1, -1);
        }
        this.j = a.f.a(this.k);
        this.l = a.f.q();
        this.m = a.f.p;
        this.c = b.e.a.a();
        this.n = a.f.k;
        this.o = a.f.i;
        this.p = a.f.t;
        this.v = a.f.c;
        this.u = a.f.b;
    }

    public final void d() {
        a.f.l[1] = this.q;
        a.f.l[2] = this.r;
        a.f.l[3] = this.t;
        a.f.d = this.s;
        a.f.x = this.d;
        a.f.y = this.f;
        a.f.z = this.g;
        a.f.B = this.h;
        a.f.C = this.i;
        a.f.a(this.l);
        a.f.j = new g[this.k.length];
        for (int i2 = 0; i2 < a.f.j.length; ++i2) {
            a.f.j[i2] = this.k[i2].I();
            a.f.j[i2].j = this.k[i2].j;
        }
        a.f.A = a.f.a(a.f.j);
        a.f.p = this.m;
        a.f.k = this.n;
        a.f.i = this.o;
        a.f.t = this.p;
        a.f.c = this.v;
        a.f.b = this.u;
    }

    public final void e() {
        int n2;
        a.f.x = 223;
        a.f.y = 10;
        a.f.z = 14;
        c c2 = this;
        if (c2.l != null) {
            for (int i2 = 0; i2 < c2.l.length; ++i2) {
                if (c2.l[i2] == null || c2.l[i2].a == null || (c2.l[i2].a.d != 15 || c2.l[i2].a.c == 1001) && c2.l[i2].a.d != 11 || a.f.a(c2.l[i2].a) > 0) continue;
                a.f.a(c2.l[i2].a.c, 1);
            }
        }
        f.a a2 = null;
        if (c2.n != null) {
            for (n2 = 0; n2 < c2.n.length; ++n2) {
                if (a.f.j[n2].h()) continue;
                for (int i3 = 0; i3 < c2.n[n2].length; ++i3) {
                    a2 = a.f.b(c2.n[n2][i3]);
                    if (a2 == null || a2.d != 11 || a.f.i(c2.n[n2][i3]) > 0) continue;
                    a.f.a(c2.n[n2][i3], 1);
                }
            }
        }
        if (this.k != null) {
            int n3 = 0;
            short s = 0;
            for (n2 = 0; n2 < this.k.length; ++n2) {
                s = this.k[n2].n;
                n3 = Math.max(this.k[n2].I, a.g.e[s][9]);
                a.f.j[n2].I = a.g.e[s][9];
                a.f.j[n2].R();
                a.f.j[n2].a((byte)0, Math.min(50, n3));
            }
        }
        a.f.A = a.f.a(a.f.j);
        a.f.t = this.p;
    }

    public final void a(Graphics graphics, int n2, int n3, int n4) {
        int n5 = n2 + n4 / 4;
        graphics.setColor(0xFFFFFF);
        graphics.drawString(this.e, n2, n3, 20);
        graphics.drawString(this.c, n2 + n4, n3, 24);
        graphics.drawImage(a.f.N, n2, n3 += b.e.a.b, 20);
        b.e.a.a(graphics, a.f.D, this.i, n2 + a.f.E + a.f.N.getWidth(), n3);
        n3 += b.e.a.b;
        n3 += 50;
        for (n2 = 0; n2 < this.j.length; ++n2) {
            this.j[n2].j();
            this.j[n2].a(graphics, n5 + n2 * n4 / 4, n3);
            b.e.a.a(graphics, a.f.F, this.j[n2].I, n5 + n2 * n4 / 4 + 10, n3 - a.f.F.getHeight() - a.f.H.getHeight());
            a.f.a(graphics, (byte)2, n5 + n2 * n4 / 4 + 10, n3 - a.f.H.getHeight());
        }
    }

    private void c(DataOutputStream dataOutputStream) {
        if (this.p == null) {
            dataOutputStream.writeByte(0);
            return;
        }
        dataOutputStream.writeByte(this.p.size());
        int[] nArray = null;
        for (int i2 = 0; i2 < this.p.size(); ++i2) {
            nArray = (int[])this.p.elementAt(i2);
            dataOutputStream.writeInt(nArray[0]);
            dataOutputStream.writeInt(nArray[1]);
        }
    }

    private void d(DataInputStream dataInputStream) {
        this.p = new Vector();
        int n2 = dataInputStream.readByte();
        int[] nArray = null;
        for (int i2 = 0; i2 < n2; ++i2) {
            int[] nArray2 = new int[2];
            nArray = nArray2;
            nArray2[0] = dataInputStream.readInt();
            nArray[1] = dataInputStream.readInt();
            this.p.addElement(nArray);
        }
    }

    private static b.a.c a(String string) {
        if (string == null) {
            return null;
        }
        b.a.c c2 = (b.a.c)a.get(string);
        if (c2 == null && (c2 = b.a.c.b(string)) != null) {
            a.put(string, c2);
        }
        return c2;
    }
}

