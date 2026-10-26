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
    private String b = "";
    private int c = 0;
    private String d = null;
    private int e = 0;
    private int f = 0;
    private byte[] g = null;
    private int h = 0;
    private g[] i = null;
    private g[] j = null;
    private b.d.a[] k = new b.d.a[256];
    private Hashtable l = null;
    private int[][] m = null;
    public static int a = 0;
    private boolean n = true;
    private Vector o = new Vector();
    private boolean p = false;
    private boolean q = true;
    private boolean r = true;
    private short s = 0;
    private b t = null;
    private static final int[] u = new int[]{153, 158, 176, 1010, 1011, 1012, 1013, 1014, 1015, 1016};

    public c() {
        this.l = new Hashtable();
    }

    public final boolean a(DataInputStream dataInputStream) {
        try {
            int n2;
            this.p = dataInputStream.readBoolean();
            this.q = dataInputStream.readBoolean();
            this.r = dataInputStream.readBoolean();
            this.c = dataInputStream.readInt();
            this.d = dataInputStream.readUTF();
            this.e = dataInputStream.readInt();
            this.f = dataInputStream.readInt();
            int n3 = dataInputStream.readShort();
            this.g = new byte[n3];
            dataInputStream.read(this.g);
            this.h = dataInputStream.readInt();
            this.c(dataInputStream);
            this.m = new int[dataInputStream.readByte()][a.g.D.length];
            for (n3 = 0; n3 < this.m.length; ++n3) {
                for (n2 = 0; n2 < this.m[n3].length; ++n2) {
                    this.m[n3][n2] = dataInputStream.readShort();
                }
            }
            n3 = dataInputStream.readByte();
            this.j = new g[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                this.j[n2] = new g();
                this.j[n2].b(dataInputStream);
                this.j[n2].j = a.f.l[this.j[n2].n];
                this.j[n2].a(1, -1);
            }
            this.i = a.f.a(this.j);
            this.d(dataInputStream);
            this.b = dataInputStream.readUTF();
            this.n = dataInputStream.readBoolean();
            this.o = a.f.a(dataInputStream);
            if (dataInputStream.readByte() == 1) {
                this.t = b.f.b.a(dataInputStream, null);
            }
            this.s = dataInputStream.readShort();
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
            this.p = dataInputStream.readBoolean();
            this.q = dataInputStream.readBoolean();
            this.r = dataInputStream.readBoolean();
            this.c = dataInputStream.readInt();
            this.d = dataInputStream.readUTF();
            this.e = dataInputStream.readInt();
            this.f = dataInputStream.readInt();
            int n3 = dataInputStream.readShort();
            this.g = new byte[n3];
            dataInputStream.read(this.g);
            this.h = dataInputStream.readInt();
            this.c(dataInputStream);
            this.m = new int[dataInputStream.readByte()][a.g.D.length];
            for (n3 = 0; n3 < this.m.length; ++n3) {
                for (n2 = 0; n2 < this.m[n3].length; ++n2) {
                    this.m[n3][n2] = dataInputStream.readShort();
                }
            }
            n3 = dataInputStream.readByte();
            this.j = new g[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                this.j[n2] = new g();
                this.j[n2].b(dataInputStream);
                this.j[n2].j = a.f.l[this.j[n2].n];
                this.j[n2].a(1, -1);
            }
            this.i = a.f.a(this.j);
            this.d(dataInputStream);
            this.b = dataInputStream.readUTF();
            this.n = dataInputStream.readBoolean();
            this.o = a.f.a(dataInputStream);
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
            int n2;
            dataOutputStream.writeBoolean(this.p);
            dataOutputStream.writeBoolean(this.q);
            dataOutputStream.writeBoolean(this.r);
            dataOutputStream.writeInt(this.c);
            dataOutputStream.writeUTF(this.d);
            dataOutputStream.writeInt(this.e);
            dataOutputStream.writeInt(this.f);
            dataOutputStream.writeShort(this.g.length);
            ((OutputStream)dataOutputStream).write(this.g);
            dataOutputStream.writeInt(this.h);
            this.b(dataOutputStream);
            dataOutputStream.writeByte(this.m.length);
            for (n2 = 0; n2 < this.m.length; ++n2) {
                for (int i2 = 0; i2 < this.m[n2].length; ++i2) {
                    dataOutputStream.writeShort(this.m[n2][i2]);
                }
            }
            dataOutputStream.writeByte(this.j.length);
            for (n2 = 0; n2 < this.j.length; ++n2) {
                ((OutputStream)dataOutputStream).write(this.j[n2].x());
            }
            this.a(dataOutputStream);
            dataOutputStream.writeUTF(this.b);
            dataOutputStream.writeBoolean(this.n);
            ((OutputStream)dataOutputStream).write(a.f.a(this.o));
            byte[] byArray = null;
            if (this.t != null) {
                byArray = this.t.e();
            }
            if (byArray != null) {
                dataOutputStream.writeByte(1);
                ((OutputStream)dataOutputStream).write(byArray);
            } else {
                dataOutputStream.writeByte(0);
            }
            dataOutputStream.writeShort(this.s);
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
            dataOutputStream.writeBoolean(this.p);
            dataOutputStream.writeBoolean(this.q);
            dataOutputStream.writeBoolean(this.r);
            dataOutputStream.writeInt(this.c);
            dataOutputStream.writeUTF(this.d);
            dataOutputStream.writeInt(this.e);
            dataOutputStream.writeInt(this.f);
            dataOutputStream.writeShort(this.g.length);
            ((OutputStream)dataOutputStream).write(this.g);
            dataOutputStream.writeInt(this.h);
            this.b(dataOutputStream);
            dataOutputStream.writeByte(this.m.length);
            for (n2 = 0; n2 < this.m.length; ++n2) {
                for (int i2 = 0; i2 < this.m[n2].length; ++i2) {
                    dataOutputStream.writeShort(this.m[n2][i2]);
                }
            }
            dataOutputStream.writeByte(this.j.length);
            for (n2 = 0; n2 < this.j.length; ++n2) {
                ((OutputStream)dataOutputStream).write(this.j[n2].x());
            }
            dataOutputStream.writeShort(0);
            dataOutputStream.writeUTF(this.b);
            dataOutputStream.writeBoolean(this.n);
            ((OutputStream)dataOutputStream).write(a.f.a(this.o));
            byte[] byArray = null;
            if (this.t != null) {
                byArray = this.t.e();
            }
            if (byArray != null) {
                dataOutputStream.writeByte(1);
                ((OutputStream)dataOutputStream).write(byArray);
            } else {
                dataOutputStream.writeByte(0);
            }
            dataOutputStream.writeShort(this.s);
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
        short s = (short)this.l.size();
        dataOutputStream.writeShort(s);
        Enumeration enumeration = this.l.keys();
        Object object = null;
        object = null;
        while (enumeration.hasMoreElements()) {
            object = (Integer)enumeration.nextElement();
            dataOutputStream.writeByte(((Integer)object).shortValue());
            byte[] byArray = (byte[])this.l.get(object);
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
        if (this.k == null) {
            dataOutputStream.writeShort(0);
            return;
        }
        int n3 = 0;
        for (n2 = 0; n2 < this.k.length; ++n2) {
            if (this.k[n2] == null) continue;
            ++n3;
        }
        dataOutputStream.writeByte(b.e.a.b(n3));
        for (n2 = 0; n2 < this.k.length; ++n2) {
            if (this.k[n2] == null) continue;
            dataOutputStream.writeByte(n2);
            dataOutputStream.writeShort(this.k[n2].a.c);
            dataOutputStream.writeByte(this.k[n2].c);
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
            this.k[n3] = a2;
        }
    }

    private void d(DataInputStream dataInputStream) {
        int n2 = dataInputStream.readShort();
        for (int i2 = 0; i2 < n2; ++i2) {
            int n3 = b.e.a.a((int)dataInputStream.readByte());
            short s = dataInputStream.readShort();
            if (s <= 0) continue;
            byte[] byArray = new byte[s];
            dataInputStream.read(byArray);
            this.l.put(new Integer(n3), byArray);
        }
    }

    public final void c() {
        this.p = a.f.m[1];
        this.q = a.f.m[2];
        this.r = a.f.d;
        this.c = a.f.w.k;
        this.d = a.f.w.p();
        this.e = a.f.A[0].n();
        this.f = a.f.A[0].o();
        this.g = a.f.B;
        this.h = a.f.C;
        this.j = new g[a.f.j.length];
        for (int i2 = 0; i2 < a.f.j.length; ++i2) {
            this.j[i2] = a.f.j[i2].F();
            this.j[i2].j = a.f.l[this.j[i2].n];
            this.j[i2].a(1, -1);
        }
        this.i = a.f.a(this.j);
        this.k = a.f.q();
        this.l = a.f.p;
        this.b = b.e.a.a();
        this.m = a.f.k;
        this.n = a.f.i;
        this.o = a.f.t;
        this.t = a.f.c;
        this.s = a.f.b;
    }

    public final void d() {
        a.f.m[1] = this.p;
        a.f.m[2] = this.q;
        a.f.d = this.r;
        a.f.x = this.c;
        a.f.y = this.e;
        a.f.z = this.f;
        a.f.B = this.g;
        a.f.C = this.h;
        a.f.a(this.k);
        a.f.j = new g[this.j.length];
        for (int i2 = 0; i2 < a.f.j.length; ++i2) {
            a.f.j[i2] = this.j[i2].F();
            a.f.j[i2].j = this.j[i2].j;
        }
        a.f.A = a.f.a(a.f.j);
        a.f.p = this.l;
        a.f.k = this.m;
        a.f.i = this.n;
        a.f.t = this.o;
        a.f.c = this.t;
        a.f.b = this.s;
    }

    public final void e() {
        int n2;
        int n3;
        a.f.x = 169;
        a.f.y = 7;
        a.f.z = 10;
        c c2 = this;
        if (c2.k != null) {
            for (n3 = 0; n3 < c2.k.length; ++n3) {
                if (c2.k[n3] == null) continue;
                for (n2 = 0; n2 < u.length; ++n2) {
                    if (c2.k[n3].a == null || c2.k[n3].a.c != u[n2]) continue;
                    a.f.a(c2.k[n3].a.c, 1);
                }
            }
        }
        if (c2.m != null) {
            for (n3 = 0; n3 < c2.m.length; ++n3) {
                for (n2 = 0; n2 < c2.m[n3].length; ++n2) {
                    for (int i2 = 0; i2 < u.length; ++i2) {
                        if (c2.m[n3][n2] != u[i2]) continue;
                        a.f.a(c2.m[n3][n2], 1);
                    }
                }
            }
        }
        if (this.j != null) {
            int n4 = 0;
            n3 = 0;
            for (n2 = 0; n2 < this.j.length; ++n2) {
                n3 = this.j[n2].n;
                n4 = Math.max(this.j[n2].I, a.g.e[n3][9]);
                a.f.j[n2].I = a.g.e[n3][9];
                a.f.j[n2].O();
                a.f.j[n2].a((byte)0, Math.min(30, n4));
            }
        }
        a.f.A = a.f.a(a.f.j);
        a.f.t = this.o;
    }

    public final void a(Graphics graphics, int n2, int n3, int n4) {
        int n5 = n2 + n4 / 4;
        graphics.setColor(0xFFFFFF);
        graphics.drawString(this.d, n2, n3, 20);
        graphics.drawString(this.b, n2 + n4, n3, 24);
        graphics.drawImage(a.f.N, n2, n3 += b.e.a.b, 20);
        b.e.a.a(graphics, a.f.D, this.h, n2 + a.f.E + a.f.N.getWidth(), n3);
        n3 += b.e.a.b;
        n3 += 50;
        for (n2 = 0; n2 < this.i.length; ++n2) {
            this.i[n2].j();
            this.i[n2].a(graphics, n5 + n2 * n4 / 4, n3);
            b.e.a.a(graphics, a.f.F, this.i[n2].I, n5 + n2 * n4 / 4 + 10, n3 - a.f.F.getHeight() - a.f.H.getHeight());
            a.f.a(graphics, (byte)2, n5 + n2 * n4 / 4 + 10, n3 - a.f.H.getHeight());
        }
    }
}

