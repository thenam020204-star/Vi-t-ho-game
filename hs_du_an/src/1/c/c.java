/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
package c;

import c.f;
import d.a;
import d.b;
import d.e;
import d.g;
import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.Image;

public final class c
extends f {
    private short a = 0;
    private short b = 0;
    private short c = 0;
    private byte d = 0;
    private byte e = 0;
    private byte f = 0;
    private short g = 0;
    private short h = 0;
    private short i = 0;
    private short j = 0;
    private short k = 0;
    private short l = 0;
    private short m = 0;
    private short n = 0;

    public c() {
        super((byte)1);
    }

    public final void a(DataInputStream dataInputStream) {
        try {
            super.b(dataInputStream);
            ((c)((Object)iOException2)).n = dataInputStream.readShort();
            ((f)((Object)iOException2)).u = dataInputStream.readByte();
            ((c)((Object)iOException2)).a = dataInputStream.readShort();
            ((c)((Object)iOException2)).d = dataInputStream.readByte();
            ((c)((Object)iOException2)).b = dataInputStream.readShort();
            ((c)((Object)iOException2)).e = dataInputStream.readByte();
            ((c)((Object)iOException2)).c = dataInputStream.readShort();
            ((c)((Object)iOException2)).f = dataInputStream.readByte();
            ((c)((Object)iOException2)).g = dataInputStream.readShort();
            ((c)((Object)iOException2)).h = dataInputStream.readShort();
            ((c)((Object)iOException2)).i = dataInputStream.readShort();
            ((c)((Object)iOException2)).j = dataInputStream.readShort();
            ((c)((Object)iOException2)).k = dataInputStream.readShort();
            ((c)((Object)iOException2)).m = dataInputStream.readShort();
            ((c)((Object)iOException2)).l = dataInputStream.readShort();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    private void a(g g2) {
        if (g2 == null || g2.z() && !this.a()) {
            return;
        }
        g2.c(this.n);
        if (g2.L() == 0) {
            g2.h(0);
        }
        g2.b((byte)2, (int)this.a);
        g2.b((byte)2, g2.e(3) * this.d / 100);
        g2.b((byte)4, (int)this.b);
        g2.b((byte)4, g2.e(5) * this.e / 100);
        g2.b((byte)6, (int)this.a);
        g2.b((byte)6, g2.e(7) * this.f / 100);
        g2.b((byte)3, (int)this.g);
        g2.b((byte)5, (int)this.h);
        g2.b((byte)7, (int)this.i);
        g2.b((byte)8, (int)this.j);
        g2.b((byte)10, (int)this.k);
        g2.b((byte)11, (int)this.m);
        g2.b((byte)9, (int)this.l);
    }

    public final void a(g g2, g g3, g[] gArray) {
        if (this.u == 1 && g3 == null || this.u == 0 && gArray == null) {
            return;
        }
        if (this.u == 0) {
            for (int i2 = 0; i2 < gArray.length; ++i2) {
                this.a(gArray[i2]);
            }
            return;
        }
        this.a(g3);
    }

    public final b[] a(g g2, g g3) {
        if (g3 == null) {
            return null;
        }
        int n2 = 0;
        if (c2.a != 0) {
            n2 = c2.a;
        } else if (c2.d != 0) {
            n2 = g3.e(3) * c2.d / 100;
        } else if (c2.b != 0) {
            n2 = c2.b;
        } else if (c2.e != 0) {
            n2 = g3.e(5) * c2.e / 100;
        } else if (c2.c != 0) {
            n2 = c2.c;
        } else if (c2.f != 0) {
            n2 = g3.e(7) * c2.f / 100;
        } else if (c2.g != 0) {
            n2 = c2.g;
        } else if (c2.h != 0) {
            n2 = c2.h;
        } else if (c2.i != 0) {
            n2 = c2.i;
        } else if (c2.j != 0) {
            n2 = c2.j;
        } else if (c2.k != 0) {
            n2 = c2.k;
        } else if (c2.l != 0) {
            n2 = c2.l;
        } else if (c2.m != 0) {
            n2 = c2.m;
        } else {
            return null;
        }
        c c2 = d.a.W;
        int n3 = g3.l() - String.valueOf(n2).length() * c2.getWidth() / 10 / 2;
        int n4 = g3.m() - g3.o / 2;
        b[] bArray = new b[1];
        b[] bArray2 = bArray;
        bArray[0] = d.e.a(n2, n3, n4, (Image)c2);
        return bArray2;
    }

    public final boolean a() {
        return this.n < 0 && (this.n & 1) != 0;
    }
}

