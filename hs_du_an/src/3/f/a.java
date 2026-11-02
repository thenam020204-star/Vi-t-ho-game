/*
 * Decompiled with CFR 0.152.
 */
package f;

import a.b;
import a.g;
import java.io.DataInputStream;

public abstract class a {
    public static final byte[] a = new byte[]{1, 2, 3, 4, 5, 6};
    public static final String[] b = new String[]{"\u6062\u590d", "\u8f85\u52a9", "\u6cd5\u5b9d", "\u88c5\u5907", "\u6d3b\u7269", "\u5176\u5b83"};
    public short c = (short)-1;
    public byte d = (byte)-1;
    public String e = "";
    private String i = "";
    public short f = 0;
    public byte g = 1;
    public byte h = 0;

    public a(byte by) {
        this.d = by;
    }

    public abstract void a(DataInputStream var1);

    public abstract boolean a();

    public final void a(g g2, g g3, g[] gArray) {
        if (g2 == null || this.g == 0 && gArray == null || this.g == 1 && g3 == null) {
            return;
        }
        this.a(g2);
        switch (this.g) {
            case 0: {
                for (int i2 = 0; i2 < gArray.length; ++i2) {
                    this.a(g2, gArray[i2]);
                }
                break;
            }
            case 1: {
                this.a(g2, g3);
            }
        }
    }

    protected abstract void a(g var1, g var2);

    protected abstract void a(g var1);

    public abstract b[] b(g var1, g var2);

    protected final void b(DataInputStream dataInputStream) {
        this.c = dataInputStream.readShort();
        this.e = dataInputStream.readUTF();
        this.i = dataInputStream.readUTF();
        this.f = dataInputStream.readShort();
    }

    public final boolean b() {
        return this.d == 1 || this.d == 5 || this.c == 1001;
    }

    private static byte a(byte by) {
        switch (by) {
            case 1: {
                return 1;
            }
            case 4: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: {
                return 4;
            }
            case 5: {
                return 2;
            }
            case 16: {
                return 5;
            }
            case 11: {
                return 3;
            }
        }
        return 6;
    }

    public final int c() {
        return f.a.a(this.d);
    }

    public final boolean d() {
        return f.a.a(this.d) == 4 || this.d == 16 || this.d == 11;
    }

    public final String e() {
        return this.i;
    }

    public final void a(String string) {
        this.i = string;
    }
}

