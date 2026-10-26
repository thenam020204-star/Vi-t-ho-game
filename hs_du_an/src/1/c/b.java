/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
package c;

import c.f;
import d.a;
import d.e;
import d.g;
import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.Image;

public final class b
extends f {
    public byte a = 1;
    public byte b = (byte)2;
    private byte f = (byte)100;
    private byte g = (byte)100;
    private byte h = (byte)100;
    private byte i = 0;
    private short j = 0;
    private short k = 0;
    private byte l = 0;
    private short m = 0;
    private byte n = 0;
    private short o = 0;
    private short w = 0;
    public short c = 0;
    public short d = 0;
    public short e = (short)-1;

    public b() {
        super((byte)12);
    }

    public final void a(DataInputStream dataInputStream) {
        try {
            super.b(dataInputStream);
            ((f)((Object)iOException2)).v = dataInputStream.readByte();
            ((b)((Object)iOException2)).a = dataInputStream.readByte();
            ((b)((Object)iOException2)).b = dataInputStream.readByte();
            ((b)((Object)iOException2)).f = dataInputStream.readByte();
            ((b)((Object)iOException2)).g = dataInputStream.readByte();
            ((b)((Object)iOException2)).h = dataInputStream.readByte();
            ((b)((Object)iOException2)).i = dataInputStream.readByte();
            ((b)((Object)iOException2)).j = dataInputStream.readShort();
            ((b)((Object)iOException2)).k = dataInputStream.readShort();
            ((b)((Object)iOException2)).l = dataInputStream.readByte();
            dataInputStream.readByte();
            ((b)((Object)iOException2)).m = dataInputStream.readShort();
            ((b)((Object)iOException2)).n = dataInputStream.readByte();
            ((b)((Object)iOException2)).o = dataInputStream.readShort();
            ((b)((Object)iOException2)).w = dataInputStream.readShort();
            ((b)((Object)iOException2)).e = dataInputStream.readShort();
            ((b)((Object)iOException2)).c = dataInputStream.readShort();
            ((b)((Object)iOException2)).d = dataInputStream.readShort();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public final d.b[] a(g g2, g g3) {
        if (g3 == null) {
            return null;
        }
        int n2 = 0;
        Image image = d.a.W;
        d.b[] bArray = null;
        int n3 = g3.l();
        int n4 = g3.m() - g3.o / 2;
        if (this.a(g3) != 0) {
            n2 = this.a(g3);
            image = n2 < 0 ? d.a.X : d.a.W;
            n2 = Math.abs(n2);
        } else if (this.m != 0) {
            n2 = this.m;
        } else if (this.n != 0) {
            n2 = g3.e(3) * this.n;
        } else if (this.w != 0) {
            n2 = this.w;
        } else if (this.o != 0) {
            n2 = this.o;
        } else {
            if (this.k > 0) {
                for (n2 = 0; n2 < 15; ++n2) {
                    if ((this.k & 1 << n2) == 0) continue;
                    bArray = new d.b[1];
                    n3 = g3.l() - g.b.d.getWidth() / 13 / 2;
                    n4 = g3.m() - g3.o / 2;
                    bArray[0] = d.e.a(g.b.d, n3, n4, (n2 - 1) * g.b.d.getWidth() / 13, 0, g.b.d.getWidth() / 13, g.b.d.getHeight());
                    break;
                }
            } else if (this.f != 100) {
                n2 = this.f < 0 ? this.f + 256 : this.f;
                d.b[] bArray2 = new d.b[2];
                bArray = bArray2;
                bArray2[0] = d.e.a(n2 >= 100 ? g.b.f : g.b.g, n3 - g.b.g.getWidth(), n4);
                bArray[1] = d.e.a(g.b.e[0], n3, n4);
            } else if (this.g != 100) {
                n2 = this.g < 0 ? this.g + 256 : this.g;
                d.b[] bArray3 = new d.b[2];
                bArray = bArray3;
                bArray3[0] = d.e.a(n2 >= 100 ? g.b.f : g.b.g, n3 - g.b.g.getWidth(), n4);
                bArray[1] = d.e.a(g.b.e[1], n3, n4);
            } else if (this.h != 100) {
                n2 = this.h < 0 ? this.h + 256 : this.h;
                d.b[] bArray4 = new d.b[2];
                bArray = bArray4;
                bArray4[0] = d.e.a(n2 >= 100 ? g.b.f : g.b.g, n3 - g.b.g.getWidth(), n4);
                bArray[1] = d.e.a(g.b.e[2], n3, n4);
            }
            return bArray;
        }
        bArray = new d.b[1];
        n3 = g3.l() - String.valueOf(n2).length() * image.getWidth() / 10 / 2;
        n4 = g3.m() - g3.o / 2;
        bArray[0] = d.e.a(n2, n3, n4, image);
        return bArray;
    }

    public final void a(g g2, g g3, g[] gArray) {
        if (g2 == null || this.a == 0 && gArray == null || this.a == 1 && g3 == null) {
            return;
        }
        g2.b((byte)4, (int)this.c);
        g2.b((byte)6, (int)this.d);
        if (this.a == 0) {
            for (int i2 = 0; i2 < gArray.length; ++i2) {
                this.b(g2, gArray[i2]);
            }
            return;
        }
        this.b(g2, g3);
    }

    private void b(g g2, g g3) {
        if (g3 == null || g3.z() && !this.a()) {
            return;
        }
        int n2 = 0;
        n2 = 0;
        if (this.f != 100) {
            n2 = this.f < 0 ? this.f + 256 : this.f;
            n2 = g3.D() * n2 / 100 - g3.D();
            g3.j(0);
            g3.a(0, n2, (int)this.l);
        }
        if (this.g != 100) {
            n2 = this.g < 0 ? this.g + 256 : this.g;
            n2 = g3.E() * n2 / 100 - g3.E();
            g3.j(1);
            g3.a(1, n2, (int)this.l);
        }
        if (this.h != 100) {
            n2 = this.h < 0 ? this.h + 256 : this.h;
            n2 = g3.J() * n2 / 100 - g3.J();
            g3.j(2);
            g3.a(2, n2, (int)this.l);
        }
        n2 = this.a(g3);
        g3.b((byte)2, n2);
        n2 = n2 * 100 / g3.e(3);
        g3.b((byte)13, n2 <= 0 ? 1 : n2);
        if (this.k != 0) {
            n2 = g3.c(this.k) ? 1 : 0;
            if (g3.L() == 0) {
                g3.h(0);
            } else if (n2 != 0) {
                g3.h(this.l);
            }
        }
        g3.b((byte)2, (int)this.m);
        g3.b((byte)2, g3.e(3) * this.n / 100);
        g3.b((byte)4, (int)this.o);
        g3.b((byte)6, (int)this.w);
    }

    private int a(g g2) {
        int n2 = d.g.a(this.i, this.j, g2);
        if (n2 < 0 && g2.L) {
            n2 = n2 * (50 + h.b.c(50)) / 100;
        }
        return n2;
    }

    public final boolean a() {
        return this.k < 0 && (this.k & 1) != 0;
    }
}

