/*
 * Decompiled with CFR 0.152.
 */
package c;

import d.b;
import d.g;
import java.io.DataInputStream;

public abstract class f {
    public short p = (short)-1;
    public byte q = (byte)-1;
    public String r = "";
    public String s = "";
    public short t = 0;
    public byte u = 1;
    public byte v = 0;

    public f(byte by) {
        this.q = by;
    }

    public abstract void a(DataInputStream var1);

    public abstract boolean a();

    public abstract void a(g var1, g var2, g[] var3);

    public abstract b[] a(g var1, g var2);

    protected final void b(DataInputStream dataInputStream) {
        this.p = dataInputStream.readShort();
        this.r = dataInputStream.readUTF();
        this.s = dataInputStream.readUTF();
        this.t = dataInputStream.readShort();
    }

    public final boolean b() {
        return this.q == 1 || this.q == 5 || this.p == 1001;
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
        return f.a(this.q) == 4 || this.q == 16 || this.q == 11;
    }
}

