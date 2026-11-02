/*
 * Decompiled with CFR 0.152.
 */
package f;

import b.f;
import java.io.DataInputStream;

public abstract class b {
    public short r = (short)-1;
    public byte s = (byte)-1;
    public String t = "";
    private String a = "";
    public short u = 0;
    public byte v = 1;
    public byte w = 0;

    public b(byte by) {
        this.s = by;
    }

    public abstract void a(DataInputStream var1);

    public abstract boolean a();

    public final void a(f f2, f f3, f[] fArray) {
        if (f2 == null || this.v == 0 && fArray == null || this.v == 1 && f3 == null) {
            return;
        }
        this.b(f2);
        switch (this.v) {
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

    protected abstract void b(f var1);

    public abstract b.b[] a(f var1, f var2);

    protected final void b(DataInputStream dataInputStream) {
        this.r = dataInputStream.readShort();
        this.t = dataInputStream.readUTF();
        this.a = dataInputStream.readUTF();
        this.u = dataInputStream.readShort();
    }

    public final boolean b() {
        return this.s == 1 || this.s == 5 || this.r == 1001;
    }

    public static byte a(byte by) {
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

    public final boolean c() {
        return b.a(this.s) == 4 || this.s == 16 || this.s == 11;
    }

    public final String d() {
        return this.a;
    }

    public final void a(String string) {
        this.a = string;
    }
}

