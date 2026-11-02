/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b;

import a.c.b;
import a.e.c;
import b.a;
import b.f;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class g {
    public static Hashtable a = new Hashtable();
    private String c = "";
    private int d = 0;
    private String e = null;
    private int f = 0;
    private int g = 0;
    private byte[] h = null;
    private int i = 0;
    private f[] j = null;
    private f[] k = null;
    private a.f.a[] l = new a.f.a[256];
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
    private a.d.b v = null;

    public g() {
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
            this.n = new int[dataInputStream.readByte()][b.f.D.length];
            for (n3 = 0; n3 < this.n.length; ++n3) {
                for (n2 = 0; n2 < this.n[n3].length; ++n2) {
                    this.n[n3][n2] = dataInputStream.readShort();
                }
            }
            n3 = dataInputStream.readByte();
            this.k = new f[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                this.k[n2] = new f();
                this.k[n2].b(dataInputStream);
                if (!this.k[n2].p()) continue;
                this.k[n2].e = b.g.a(this.k[n2].d);
                this.k[n2].a(1, -1);
            }
            this.j = b.a.a(this.k);
            DataInputStream dataInputStream2 = dataInputStream;
            g g2 = this;
            int n4 = dataInputStream2.readShort();
            for (int i2 = 0; i2 < n4; ++i2) {
                short s = dataInputStream2.readShort();
                short s2 = dataInputStream2.readShort();
                if (s2 <= 0) continue;
                byte[] byArray = new byte[s2];
                dataInputStream2.read(byArray);
                g2.m.put(new Integer(s), byArray);
            }
            this.c = dataInputStream.readUTF();
            this.o = dataInputStream.readBoolean();
            this.d(dataInputStream);
            if (dataInputStream.readByte() == 1) {
                this.v = a.d.b.a(dataInputStream, null);
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
            this.n = new int[dataInputStream.readByte()][b.f.D.length];
            for (n3 = 0; n3 < this.n.length; ++n3) {
                for (n2 = 0; n2 < this.n[n3].length; ++n2) {
                    this.n[n3][n2] = dataInputStream.readShort();
                }
            }
            n3 = dataInputStream.readByte();
            this.k = new f[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                this.k[n2] = new f();
                this.k[n2].b(dataInputStream);
                if (!this.k[n2].p()) continue;
                this.k[n2].e = b.g.a(this.k[n2].d);
                this.k[n2].a(1, -1);
            }
            this.j = b.a.a(this.k);
            DataInputStream dataInputStream2 = dataInputStream;
            g g2 = this;
            int n4 = dataInputStream2.readShort();
            for (int i2 = 0; i2 < n4; ++i2) {
                int n5 = a.c.b.a((int)dataInputStream2.readByte());
                short s = dataInputStream2.readShort();
                if (s <= 0) continue;
                byte[] byArray = new byte[s];
                dataInputStream2.read(byArray);
                g2.m.put(new Integer(n5), byArray);
            }
            this.c = dataInputStream.readUTF();
            this.o = dataInputStream.readBoolean();
            this.d(dataInputStream);
            if (dataInputStream.readByte() == 1) {
                this.v = a.d.b.a(dataInputStream, null);
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
            a.c.b.a(byteArrayOutputStream);
            a.c.b.a(dataOutputStream);
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
            a.c.b.a(byteArrayOutputStream);
            a.c.b.a(dataOutputStream);
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
        dataOutputStream.writeByte(a.c.b.b(n3));
        for (n2 = 0; n2 < this.l.length; ++n2) {
            if (this.l[n2] == null) continue;
            dataOutputStream.writeByte(n2);
            dataOutputStream.writeShort(this.l[n2].a.q);
            dataOutputStream.writeByte(this.l[n2].c);
        }
    }

    private void c(DataInputStream dataInputStream) {
        int n2 = a.c.b.a(dataInputStream.readByte());
        if (n2 <= 0) {
            return;
        }
        int n3 = 0;
        a.f.a a2 = null;
        for (int i2 = 0; i2 < n2; ++i2) {
            n3 = a.c.b.a((int)dataInputStream.readByte());
            a2 = new a.f.a();
            new a.f.a().a = b.a.b((int)dataInputStream.readShort());
            a2.c = a.c.b.a((int)dataInputStream.readByte());
            this.l[n3] = a2;
        }
    }

    public final void c() {
        this.q = b.a.l[1];
        this.r = b.a.l[2];
        this.t = b.a.l[3];
        this.s = b.a.d;
        this.d = b.a.w.k;
        this.e = b.a.w.o();
        this.f = b.a.A[0].f();
        this.g = b.a.A[0].g();
        this.h = b.a.B;
        this.i = b.a.C;
        this.k = new f[b.a.j.length];
        for (int i2 = 0; i2 < b.a.j.length; ++i2) {
            this.k[i2] = b.a.j[i2].I();
            if (!this.k[i2].p()) continue;
            this.k[i2].e = b.g.a(this.k[i2].d);
            this.k[i2].a(1, -1);
        }
        this.j = b.a.a(this.k);
        this.l = b.a.q();
        this.m = b.a.p;
        this.c = a.c.b.a();
        this.n = b.a.k;
        this.o = b.a.i;
        this.p = b.a.t;
        this.v = b.a.c;
        this.u = b.a.b;
    }

    public final void d() {
        b.a.l[1] = this.q;
        b.a.l[2] = this.r;
        b.a.l[3] = this.t;
        b.a.d = this.s;
        b.a.x = this.d;
        b.a.y = this.f;
        b.a.z = this.g;
        b.a.B = this.h;
        b.a.C = this.i;
        b.a.a(this.l);
        b.a.j = new f[this.k.length];
        for (int i2 = 0; i2 < b.a.j.length; ++i2) {
            b.a.j[i2] = this.k[i2].I();
            b.a.j[i2].e = this.k[i2].e;
        }
        b.a.A = b.a.a(b.a.j);
        b.a.p = this.m;
        b.a.k = this.n;
        b.a.i = this.o;
        b.a.t = this.p;
        b.a.c = this.v;
        b.a.b = this.u;
    }

    public final void e() {
        int n2;
        b.a.x = 401;
        b.a.y = 8;
        b.a.z = 35;
        g g2 = this;
        if (g2.l != null) {
            for (int i2 = 0; i2 < g2.l.length; ++i2) {
                if (g2.l[i2] == null || g2.l[i2].a == null || (g2.l[i2].a.r != 15 || g2.l[i2].a.q == 1001) && g2.l[i2].a.r != 11 || b.a.a(g2.l[i2].a) > 0) continue;
                b.a.a(g2.l[i2].a.q, 1);
            }
        }
        f.b b2 = null;
        if (g2.n != null) {
            for (n2 = 0; n2 < g2.n.length; ++n2) {
                if (b.a.j[n2].p()) continue;
                for (int i3 = 0; i3 < g2.n[n2].length; ++i3) {
                    b2 = b.a.b(g2.n[n2][i3]);
                    if (b2 == null || b2.r != 11 || b.a.i(g2.n[n2][i3]) > 0) continue;
                    b.a.a(g2.n[n2][i3], 1);
                }
            }
        }
        if (this.k != null) {
            int n3 = 0;
            short s = 0;
            for (n2 = 0; n2 < this.k.length; ++n2) {
                s = this.k[n2].i;
                n3 = Math.max(this.k[n2].I, b.f.A[s][9]);
                b.a.j[n2].I = b.f.A[s][9];
                b.a.j[n2].R();
                b.a.j[n2].a((byte)0, Math.min(65, n3));
                b.a.j[n2].a((byte)12, this.k[n2].c(12));
            }
        }
        b.a.A = b.a.a(b.a.j);
        b.a.t = this.p;
    }

    public final void a(Graphics graphics, int n2, int n3, int n4) {
        int n5 = n2 + n4 / 4;
        graphics.setColor(0xFFFFFF);
        graphics.drawString(this.e, n2, n3, 20);
        graphics.drawString(this.c, n2 + n4, n3, 24);
        graphics.drawImage(b.a.N, n2, n3 += a.c.b.b, 20);
        a.c.b.a(graphics, b.a.D, this.i, n2 + b.a.E + b.a.N.getWidth(), n3);
        n3 += a.c.b.b;
        n3 += 50;
        for (n2 = 0; n2 < this.j.length; ++n2) {
            this.j[n2].b();
            this.j[n2].a(graphics, n5 + n2 * n4 / 4, n3);
            a.c.b.a(graphics, b.a.F, this.j[n2].I, n5 + n2 * n4 / 4 + 10, n3 - b.a.F.getHeight() - b.a.H.getHeight());
            b.a.a(graphics, (byte)2, n5 + n2 * n4 / 4 + 10, n3 - b.a.H.getHeight());
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

    private static c a(String string) {
        if (string == null) {
            return null;
        }
        c c2 = (c)a.get(string);
        if (c2 == null && (c2 = a.e.c.b(string)) != null) {
            a.put(string, c2);
        }
        return c2;
    }

    public final void a(g g2) {
        if (g2 == null) {
            return;
        }
        int n2 = 0;
        for (int i2 = 0; i2 < this.k.length; ++i2) {
            n2 = g2.k[i2].c(12);
            this.k[i2].a((byte)12, n2);
        }
    }
}

