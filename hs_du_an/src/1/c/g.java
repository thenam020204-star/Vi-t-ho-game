/*
 * Decompiled with CFR 0.152.
 */
package c;

import c.f;
import d.a;
import d.b;
import d.e;
import java.io.DataInputStream;
import java.io.IOException;

public final class g
extends f {
    private short a = 0;
    private byte b = 0;
    private byte c = 0;
    private byte d = 0;
    private byte e = 0;
    private byte f = 0;
    private short g = 0;
    private byte h = 0;

    public g() {
        super((byte)5);
    }

    public final void a(DataInputStream dataInputStream) {
        try {
            super.b(dataInputStream);
            ((f)((Object)iOException2)).u = dataInputStream.readByte();
            ((g)((Object)iOException2)).a = dataInputStream.readShort();
            ((f)((Object)iOException2)).v = dataInputStream.readByte();
            ((g)((Object)iOException2)).b = dataInputStream.readByte();
            ((g)((Object)iOException2)).c = dataInputStream.readByte();
            ((g)((Object)iOException2)).d = dataInputStream.readByte();
            ((g)((Object)iOException2)).e = dataInputStream.readByte();
            ((g)((Object)iOException2)).f = dataInputStream.readByte();
            ((g)((Object)iOException2)).g = dataInputStream.readShort();
            ((g)((Object)iOException2)).h = dataInputStream.readByte();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public final void a(d.g g2, d.g g3, d.g[] gArray) {
        if (this.u == 0 && gArray == null || this.u == 1 && g3 == null) {
            return;
        }
        switch (this.u) {
            case 0: {
                for (int i2 = 0; i2 < gArray.length; ++i2) {
                    this.a(gArray[i2]);
                }
                return;
            }
            case 1: {
                this.a(g3);
            }
        }
    }

    private void a(d.g g2) {
        int n2;
        if (g2 == null || g2.z()) {
            return;
        }
        g2.b((byte)2, -Math.abs(this.a));
        int n3 = 0;
        if (this.b != 0) {
            for (n2 = 0; n2 < 5; ++n2) {
                if ((this.b & 1 << n2) == 0) continue;
                g2.j(n2 + 3);
                n3 = g2.d(this.b) * this.c / 100;
                g2.a(n2 + 3, n3, (int)this.h);
            }
        }
        n2 = 0;
        if (this.d != 100) {
            n2 = this.d < 0 ? this.d + 256 : this.d;
            n3 = g2.D() * n2 / 100 - g2.D();
            g2.j(0);
            g2.a(0, n3, (int)this.h);
        }
        if (this.e != 100) {
            n2 = this.e < 0 ? this.e + 256 : this.e;
            n3 = g2.E() * n2 / 100 - g2.E();
            g2.j(1);
            g2.a(1, n3, (int)this.h);
        }
        if (this.f != 100) {
            n2 = this.f < 0 ? this.f + 256 : this.f;
            n3 = g2.J() * n2 / 100 - g2.J();
            g2.j(2);
            g2.a(2, n3, (int)this.h);
        }
        if (this.g != 0) {
            n3 = g2.c(this.g) ? 1 : 0;
            if (g2.L() == 0) {
                g2.h(0);
                return;
            }
            if (n3 != 0) {
                g2.h(this.h);
            }
        }
    }

    public final b[] a(d.g g2, d.g g3) {
        if (g3 == null) {
            return null;
        }
        int n2 = g3.l();
        int n3 = g3.m() - g3.o / 2;
        b[] bArray = null;
        if (this.a != 0) {
            bArray = new b[]{d.e.a(this.a, n2 -= String.valueOf(this.a).length() * d.a.X.getWidth() / 10 / 2, n3, d.a.X)};
        } else if (this.b != 0) {
            for (int i2 = 0; i2 < 5; ++i2) {
                if ((this.b & 1 << i2) == 0) continue;
                b[] bArray2 = new b[2];
                bArray = bArray2;
                bArray2[0] = d.e.a(this.c >= 100 ? g.b.f : g.b.g, n2 - g.b.g.getWidth(), n3);
                bArray[1] = d.e.a(d.a.V, n2, n3, i2 * d.a.V.getWidth() / 5, 0, d.a.V.getWidth() / 5, d.a.V.getHeight());
                break;
            }
        } else if (this.g != 0) {
            for (int i3 = 0; i3 < 15; ++i3) {
                if ((this.g & 1 << i3) == 0) continue;
                bArray = new b[]{d.e.a(g.b.d, n2 -= g.b.d.getWidth() / 13 / 2, n3, (i3 - 1) * g.b.d.getWidth() / 13, 0, g.b.d.getWidth() / 13, g.b.d.getHeight())};
                break;
            }
        } else if (this.d != 100) {
            int n4 = this.d < 0 ? this.d + 256 : this.d;
            b[] bArray3 = new b[2];
            bArray = bArray3;
            bArray3[0] = d.e.a(n4 >= 100 ? g.b.f : g.b.g, n2 - g.b.g.getWidth(), n3);
            bArray[1] = d.e.a(g.b.e[0], n2, n3);
        } else if (this.e != 100) {
            int n5 = this.e < 0 ? this.e + 256 : this.e;
            b[] bArray4 = new b[2];
            bArray = bArray4;
            bArray4[0] = d.e.a(n5 >= 100 ? g.b.f : g.b.g, n2 - g.b.g.getWidth(), n3);
            bArray[1] = d.e.a(g.b.e[1], n2, n3);
        } else if (this.f != 100) {
            int n6 = this.f < 0 ? this.f + 256 : this.f;
            b[] bArray5 = new b[2];
            bArray = bArray5;
            bArray5[0] = d.e.a(n6 >= 100 ? g.b.f : g.b.g, n2 - g.b.g.getWidth(), n3);
            bArray[1] = d.e.a(g.b.e[2], n2, n3);
        }
        return bArray;
    }

    public final boolean a() {
        return false;
    }
}

