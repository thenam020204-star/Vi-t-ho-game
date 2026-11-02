/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package d;

import c.f;
import d.a;
import d.g;
import h.b;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.lcdui.Graphics;

public final class h {
    private String b = "";
    private int c = 0;
    private String d = null;
    private int e = 0;
    private int f = 0;
    private byte[] g = null;
    private int h = 0;
    private g[] i = null;
    private g[] j = null;
    private f[] k = new f[256];
    private int[] l = new int[256];
    private Hashtable m = new Hashtable();
    private int[][] n = null;
    public static int a = 0;
    private boolean o = true;

    public final boolean a(DataInputStream dataInputStream) {
        try {
            int n2;
            int n3;
            int n4;
            this.c = dataInputStream.readInt();
            this.d = dataInputStream.readUTF();
            this.e = dataInputStream.readInt();
            this.f = dataInputStream.readInt();
            short s2 = dataInputStream.readShort();
            this.g = new byte[s2];
            dataInputStream.read(this.g);
            this.h = dataInputStream.readInt();
            DataInputStream dataInputStream2 = dataInputStream;
            h h2 = this;
            int n5 = h.b.a(dataInputStream2.readByte());
            if (n5 > 0) {
                n4 = 0;
                for (n3 = 0; n3 < n5; ++n3) {
                    n4 = h.b.a((int)dataInputStream2.readByte());
                    h2.k[n4] = d.a.a((int)dataInputStream2.readShort());
                    h2.l[n4] = h.b.a((int)dataInputStream2.readByte());
                }
            }
            this.n = new int[dataInputStream.readByte()][d.g.v.length];
            for (n2 = 0; n2 < this.n.length; ++n2) {
                for (int i2 = 0; i2 < this.n[n2].length; ++i2) {
                    this.n[n2][i2] = dataInputStream.readShort();
                }
            }
            n2 = dataInputStream.readByte();
            this.j = new g[n2];
            for (int i3 = 0; i3 < n2; ++i3) {
                this.j[i3] = new g();
                this.j[i3].b(dataInputStream);
                this.j[i3].A();
                this.j[i3].c = d.a.n[this.j[i3].f];
                this.j[i3].b(1, -1);
            }
            this.i = d.a.a(this.j);
            DataInputStream dataInputStream3 = dataInputStream;
            h h3 = this;
            n5 = dataInputStream3.readShort();
            for (n4 = 0; n4 < n5; ++n4) {
                n3 = h.b.a((int)dataInputStream3.readByte());
                short s3 = dataInputStream3.readShort();
                if (s3 <= 0) continue;
                byte[] byArray = new byte[s3];
                dataInputStream3.read(byArray);
                h3.m.put(new Integer(n3), byArray);
            }
            this.b = dataInputStream.readUTF();
            this.o = dataInputStream.readBoolean();
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
                ((OutputStream)dataOutputStream).write(this.j[n2].C());
            }
            this.a(dataOutputStream);
            dataOutputStream.writeUTF(this.b);
            dataOutputStream.writeBoolean(this.o);
            byte[] byArray = byteArrayOutputStream.toByteArray();
            return byArray;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return null;
        }
        finally {
            h.b.a(byteArrayOutputStream);
            h.b.a(dataOutputStream);
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
        dataOutputStream.writeByte(h.b.b(n3));
        for (n2 = 0; n2 < this.k.length; ++n2) {
            if (this.k[n2] == null) continue;
            dataOutputStream.writeByte(n2);
            dataOutputStream.writeShort(this.k[n2].p);
            dataOutputStream.writeByte(this.l[n2]);
        }
    }

    public final void b() {
        this.c = d.a.y.d;
        this.d = d.a.i(this.c);
        this.e = d.a.C[0].i();
        this.f = d.a.C[0].j();
        this.g = d.a.D;
        this.h = d.a.E;
        this.j = new g[d.a.l.length];
        for (int i2 = 0; i2 < d.a.l.length; ++i2) {
            this.j[i2] = d.a.l[i2].M();
            this.j[i2].c = d.a.n[this.j[i2].f];
            this.j[i2].b(1, -1);
        }
        this.i = d.a.a(this.j);
        this.k = d.a.F;
        this.l = d.a.G;
        this.m = d.a.r;
        this.b = h.b.a();
        this.n = d.a.m;
        this.o = d.a.k;
    }

    public final void c() {
        d.a.z = this.c;
        d.a.A = this.e;
        d.a.B = this.f;
        d.a.D = this.g;
        d.a.E = this.h;
        d.a.F = this.k;
        d.a.G = this.l;
        d.a.l = new g[this.j.length];
        for (int i2 = 0; i2 < d.a.l.length; ++i2) {
            d.a.l[i2] = this.j[i2].M();
            d.a.l[i2].c = this.j[i2].c;
            d.a.l[i2].G();
        }
        d.a.C = d.a.a(d.a.l);
        d.a.r = this.m;
        d.a.m = this.n;
        d.a.k = this.o;
    }

    public final void a(Graphics graphics, int n2, int n3, int n4) {
        int n5 = n2 + n4 / 4;
        graphics.setColor(0xFFFFFF);
        graphics.drawString(this.d, n2, n3, 20);
        graphics.drawString(this.b, n2 + n4, n3, 24);
        graphics.drawImage(d.a.R, n2, n3 += h.b.b, 20);
        h.b.a(graphics, d.a.H, this.h, n2 + d.a.I + d.a.R.getWidth(), n3);
        n3 += h.b.b;
        n3 += 50;
        for (n2 = 0; n2 < this.i.length; ++n2) {
            this.i[n2].c();
            this.i[n2].a(graphics, n5 + n2 * n4 / 4, n3);
            h.b.a(graphics, d.a.J, this.i[n2].C, n5 + n2 * n4 / 4 + 10, n3 - d.a.J.getHeight() - d.a.L.getHeight());
            d.a.a(graphics, (byte)2, n5 + n2 * n4 / 4 + 10, n3 - d.a.L.getHeight());
        }
    }
}

