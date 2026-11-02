/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
package c;

import c.f;
import d.b;
import d.e;
import d.g;
import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.Image;

public final class a
extends f {
    private short d = 0;
    public short a = 0;
    public short b = 0;
    public byte c = 0;

    public a() {
        super((byte)14);
    }

    public final void a(DataInputStream dataInputStream) {
        try {
            super.b(dataInputStream);
            ((f)((Object)iOException2)).u = dataInputStream.readByte();
            dataInputStream.readByte();
            ((a)((Object)iOException2)).d = dataInputStream.readShort();
            ((a)((Object)iOException2)).a = dataInputStream.readShort();
            ((a)((Object)iOException2)).b = dataInputStream.readShort();
            ((a)((Object)iOException2)).c = dataInputStream.readByte();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public final void a(g g2, g g3, g[] gArray) {
        if (this.u == 0 && gArray == null || this.u == 1 && g3 == null) {
            return;
        }
        if (this.u == 0) {
            for (int i2 = 0; i2 < gArray.length; ++i2) {
                this.b(g2, gArray[i2]);
            }
            return;
        }
        this.b(g2, g3);
    }

    private void b(g g2, g g3) {
        if (g3 == null || g3.z()) {
            return;
        }
        g3.b((byte)2, (int)(-this.d));
    }

    public final b[] a(g bArray, g g2) {
        bArray = new b[1];
        Image image = d.a.X;
        short s2 = -this.d;
        int n2 = g2.l() - String.valueOf(s2).length() * image.getWidth() / 10 / 2;
        int n3 = g2.m() - g2.o / 2;
        bArray[0] = e.a(s2, n2, n3, image);
        return bArray;
    }

    public final boolean a() {
        return false;
    }
}

