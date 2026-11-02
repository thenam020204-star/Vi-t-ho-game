/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
package b;

import c.f;
import d.b;
import d.e;
import d.g;
import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.Image;

public final class a
extends f {
    public int a = 0;
    private int b = 0;

    public a() {
        super((byte)17);
    }

    private int b(g g2, g g3) {
        short s2;
        if (g2 == null) {
            return 0;
        }
        if (this.p < 0 || this.p >= g2.z.length) {
            return 0;
        }
        short s3 = s2 = g2.z[this.p];
        a a2 = this;
        int n2 = s3 * 100 / a2.b;
        switch (this.p) {
            case 0: {
                return n2 * 200 / 100 + 100;
            }
            case 1: {
                if (n2 <= 50) {
                    return h.b.c(45) + 55;
                }
                if (n2 > 50 && n2 < 100) {
                    return h.b.c(900) + 100;
                }
                if (n2 == 100) {
                    return h.b.c(9000) + 1000;
                }
                return 0;
            }
            case 2: {
                g g4 = g3;
                g g5 = g2;
                if (g4 == null) {
                    return 0;
                }
                int n3 = g5.a(g4, false);
                n3 += n3 * (h.b.c(60) + 40) / 100;
                return n3;
            }
        }
        return 0;
    }

    public final boolean equals(Object object) {
        return super.equals(object);
    }

    public final void a(DataInputStream dataInputStream) {
        try {
            super.b(dataInputStream);
            ((a)((Object)iOException2)).a = dataInputStream.readShort();
            ((a)((Object)iOException2)).b = dataInputStream.readShort();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public final b[] a(g g2, g g3) {
        b[] bArray = new b[1];
        Image image = d.a.X;
        int n2 = this.b(g2, g3);
        int n3 = g3.l() - String.valueOf(n2).length() * image.getWidth() / 10 / 2;
        int n4 = g3.m() - g3.o / 2;
        bArray[0] = e.a(n2, n3, n4, image);
        return bArray;
    }

    public final boolean a() {
        return false;
    }

    public final void a(g g2, g g3, g[] gArray) {
        if (g2 == null || g3 == null) {
            return;
        }
        g2.b((byte)6, -this.a);
        int n2 = this.b(g2, g3);
        g3.b((byte)2, -n2);
    }
}

