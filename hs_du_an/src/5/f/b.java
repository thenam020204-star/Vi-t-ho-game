/*
 * Decompiled with CFR 0.152.
 */
package f;

import b.f;
import java.io.DataInputStream;

public abstract class b {
    public static final byte[] o = new byte[]{1, 2, 3, 4, 5, 6};
    public static final String[] p = new String[]{"\u6062\u590d", "\u8f85\u52a9", "\u6cd5\u5b9d", "\u88c5\u5907", "\u6d3b\u7269", "\u5176\u5b83"};
    public short q = (short)-1;
    public byte r = (byte)-1;
    public String s = "";
    private String a = "";
    public short t = 0;
    public byte u = 1;
    public byte v = 0;

    public b(byte by) {
        this.r = by;
    }

    public abstract void a(DataInputStream var1);

    public abstract boolean a();

    public final void a(f f2, f f3, f[] fArray) {
        if (f2 == null || this.u == 0 && fArray == null || this.u == 1 && f3 == null) {
            return;
        }
        this.a(f2);
        switch (this.u) {
            case 0: {
                for (int i2 = 0; i2 < fArray.length; ++i2) {
                    this.b(f2, fArray[i2]);
                }
                return;
            }
            case 1: {
                this.b(f2, f3);
            }
        }
    }

    protected abstract void b(f var1, f var2);

    protected abstract void a(f var1);

    public abstract b.b[] a(f var1, f var2);

    protected final void b(DataInputStream dataInputStream) {
        this.q = dataInputStream.readShort();
        this.s = dataInputStream.readUTF();
        this.a = dataInputStream.readUTF();
        this.t = dataInputStream.readShort();
    }

    public final boolean c() {
        return this.r == 1 || this.r == 5 || this.q == 1001;
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

    public final int d() {
        return b.a(this.r);
    }

    public final boolean e() {
        return b.a(this.r) == 4 || this.r == 16 || this.r == 11;
    }

    public final String f() {
        return this.a;
    }

    public final void a(String string) {
        this.a = string;
    }
}

