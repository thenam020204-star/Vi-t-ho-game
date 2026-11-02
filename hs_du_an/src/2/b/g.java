/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b;

import b.a;
import b.f;
import e.a.b;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.lcdui.Graphics;

public final class g {
    private String b = "";
    private int c = 0;
    private String d = null;
    private int e = 0;
    private int f = 0;
    private byte[] g = null;
    private int h = 0;
    private f[] i = null;
    private f[] j = null;
    private f.b[] k = new f.b[256];
    private int[] l = new int[256];
    private Hashtable m = new Hashtable();
    private int[][] n = null;
    public static int a = 0;
    private boolean o = true;
    private int[][] p = null;
    private boolean q = false;
    private boolean r = true;
    private boolean s = true;

    public final boolean a(DataInputStream dataInputStream) {
        try {
            int n2;
            int n3;
            int n4;
            this.q = dataInputStream.readBoolean();
            this.r = dataInputStream.readBoolean();
            this.s = dataInputStream.readBoolean();
            this.c = dataInputStream.readInt();
            this.d = dataInputStream.readUTF();
            this.e = dataInputStream.readInt();
            this.f = dataInputStream.readInt();
            short s2 = dataInputStream.readShort();
            this.g = new byte[s2];
            dataInputStream.read(this.g);
            this.h = dataInputStream.readInt();
            DataInputStream dataInputStream2 = dataInputStream;
            g g2 = this;
            int n5 = e.a.b.a(dataInputStream2.readByte());
            if (n5 > 0) {
                n4 = 0;
                for (n3 = 0; n3 < n5; ++n3) {
                    n4 = e.a.b.a((int)dataInputStream2.readByte());
                    g2.k[n4] = b.a.a((int)dataInputStream2.readShort());
                    g2.l[n4] = e.a.b.a((int)dataInputStream2.readByte());
                }
            }
            this.n = new int[dataInputStream.readByte()][b.f.u.length];
            for (n2 = 0; n2 < this.n.length; ++n2) {
                for (int i2 = 0; i2 < this.n[n2].length; ++i2) {
                    this.n[n2][i2] = dataInputStream.readShort();
                }
            }
            n2 = dataInputStream.readByte();
            this.j = new f[n2];
            for (int i3 = 0; i3 < n2; ++i3) {
                this.j[i3] = new f();
                this.j[i3].b(dataInputStream);
                this.j[i3].c = b.a.l[this.j[i3].f];
                this.j[i3].a(1, -1);
            }
            this.i = b.a.a(this.j);
            DataInputStream dataInputStream3 = dataInputStream;
            g g3 = this;
            n5 = dataInputStream3.readShort();
            for (n4 = 0; n4 < n5; ++n4) {
                n3 = e.a.b.a((int)dataInputStream3.readByte());
                short s3 = dataInputStream3.readShort();
                if (s3 <= 0) continue;
                byte[] byArray = new byte[s3];
                dataInputStream3.read(byArray);
                g3.m.put(new Integer(n3), byArray);
            }
            this.b = dataInputStream.readUTF();
            this.o = dataInputStream.readBoolean();
            this.p = b.a.a(dataInputStream);
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
            dataOutputStream.writeBoolean(this.q);
            dataOutputStream.writeBoolean(this.r);
            dataOutputStream.writeBoolean(this.s);
            dataOutputStream.writeInt(this.c);
            dataOutputStream.writeUTF(this.d);
            dataOutputStream.writeInt(this.e);
            dataOutputStream.writeInt(this.f);
            dataOutputStream.writeShort(this.g.length);
            ((OutputStream)dataOutputStream).write(this.g);
            dataOutputStream.writeInt(this.h);
            this.b(dataOutputStream);
            dataOutputStream.writeByte(this.n.length);
            for (n2 = 0; n2 < this.n.length; ++n2) {
                for (int i2 = 0; i2 < this.n[n2].length; ++i2) {
                    dataOutputStream.writeShort(this.n[n2][i2]);
                }
            }
            dataOutputStream.writeByte(this.j.length);
            for (n2 = 0; n2 < this.j.length; ++n2) {
                ((OutputStream)dataOutputStream).write(this.j[n2].x());
            }
            this.a(dataOutputStream);
            dataOutputStream.writeUTF(this.b);
            dataOutputStream.writeBoolean(this.o);
            ((OutputStream)dataOutputStream).write(b.a.a(this.p));
            byte[] byArray = byteArrayOutputStream.toByteArray();
            return byArray;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return null;
        }
        finally {
            e.a.b.a(byteArrayOutputStream);
            e.a.b.a(dataOutputStream);
        }
    }

    private void a(DataOutputStream dataOutputStream) {
        short s2 = (short)this.m.size();
        dataOutputStream.writeShort(s2);
        Enumeration enumeration = this.m.keys();
        Object object = null;
        object = null;
        while (enumeration.hasMoreElements()) {
            object = (Integer)enumeration.nextElement();
            dataOutputStream.writeByte(((Integer)object).shortValue());
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
        if (this.k == null || this.l == null || this.k.length != this.l.length) {
            dataOutputStream.writeShort(0);
            return;
        }
        int n3 = 0;
        for (n2 = 0; n2 < this.k.length; ++n2) {
            if (this.k[n2] == null) continue;
            ++n3;
        }
        dataOutputStream.writeByte(e.a.b.b(n3));
        for (n2 = 0; n2 < this.k.length; ++n2) {
            if (this.k[n2] == null) continue;
            dataOutputStream.writeByte(n2);
            dataOutputStream.writeShort(this.k[n2].r);
            dataOutputStream.writeByte(this.l[n2]);
        }
    }

    public final void b() {
        this.q = b.a.n[1];
        this.r = b.a.n[2];
        this.s = b.a.d;
        this.c = b.a.x.c;
        this.d = b.a.x.r();
        this.e = b.a.B[0].f();
        this.f = b.a.B[0].g();
        this.g = b.a.C;
        this.h = b.a.D;
        this.j = new f[b.a.j.length];
        for (int i2 = 0; i2 < b.a.j.length; ++i2) {
            this.j[i2] = b.a.j[i2].E();
            this.j[i2].c = b.a.l[this.j[i2].f];
            this.j[i2].a(1, -1);
        }
        this.i = b.a.a(this.j);
        this.k = b.a.E;
        this.l = b.a.F;
        this.m = b.a.q;
        this.b = e.a.b.a();
        this.n = b.a.k;
        this.o = b.a.i;
        this.p = b.a.u;
    }

    public final void c() {
        b.a.n[1] = this.q;
        b.a.n[2] = this.r;
        b.a.d = this.s;
        b.a.y = this.c;
        b.a.z = this.e;
        b.a.A = this.f;
        b.a.C = this.g;
        b.a.D = this.h;
        b.a.E = this.k;
        b.a.F = this.l;
        b.a.j = new f[this.j.length];
        for (int i2 = 0; i2 < b.a.j.length; ++i2) {
            b.a.j[i2] = this.j[i2].E();
            b.a.j[i2].c = this.j[i2].c;
        }
        b.a.B = b.a.a(b.a.j);
        b.a.q = this.m;
        b.a.k = this.n;
        b.a.i = this.o;
        b.a.u = this.p;
    }

    public final void a(Graphics graphics, int n2, int n3, int n4) {
        int n5 = n2 + n4 / 4;
        graphics.setColor(0xFFFFFF);
        graphics.drawString(this.d, n2, n3, 20);
        graphics.drawString(this.b, n2 + n4, n3, 24);
        graphics.drawImage(b.a.Q, n2, n3 += e.a.b.b, 20);
        e.a.b.a(graphics, b.a.G, this.h, n2 + b.a.H + b.a.Q.getWidth(), n3);
        n3 += e.a.b.b;
        n3 += 50;
        for (n2 = 0; n2 < this.i.length; ++n2) {
            this.i[n2].a(graphics, n5 + n2 * n4 / 4, n3);
            e.a.b.a(graphics, b.a.I, this.i[n2].A, n5 + n2 * n4 / 4 + 10, n3 - b.a.I.getHeight() - b.a.K.getHeight());
            b.a.a(graphics, (byte)2, n5 + n2 * n4 / 4 + 10, n3 - b.a.K.getHeight());
        }
    }

    static {
        int[] nArray = new int[]{153, 158, 176};
    }
}

