/*
 * Decompiled with CFR 0.152.
 */
package f;

import b.f;
import f.b;
import f.c;
import java.io.DataInputStream;
import java.io.IOException;

public final class a
extends b {
    public short a = 0;
    public short b = 0;
    public short c = 0;
    public byte d = 0;
    public byte e = 0;
    public byte f = 0;
    public byte g = 0;
    public byte h = 0;
    public short i = 0;
    public short j = 0;
    public short k = 0;
    public short l = 0;
    public short[] m = new short[5];
    public short n = 0;
    private short w = 0;
    private short x = (short)-1;
    private short y = (short)-1;
    private short z = (short)-1;

    public a(byte by) {
        super(by);
    }

    public final void a(DataInputStream object) {
        try {
            switch (((b)((Object)iOException2)).r) {
                case 4: {
                    super.b((DataInputStream)object);
                    ((a)((Object)iOException2)).a = ((DataInputStream)object).readShort();
                    ((a)((Object)iOException2)).b = ((DataInputStream)object).readShort();
                    ((a)((Object)iOException2)).c = ((DataInputStream)object).readShort();
                    ((a)((Object)iOException2)).h = ((DataInputStream)object).readByte();
                    ((a)((Object)iOException2)).i = ((DataInputStream)object).readShort();
                    ((DataInputStream)object).readShort();
                    ((DataInputStream)object).readByte();
                    ((a)((Object)iOException2)).g = ((DataInputStream)object).readByte();
                    object = new StringBuffer();
                    ((StringBuffer)object).append(f.a.a(((a)((Object)iOException2)).a, ((a)((Object)iOException2)).b, ((a)((Object)iOException2)).c));
                    String string = b.f.m(((a)((Object)iOException2)).h);
                    if (((a)((Object)iOException2)).h != 0 && !string.equals("") && ((a)((Object)iOException2)).i > 0) {
                        ((StringBuffer)object).append(",\u5e76\u5e26\u6709" + ((a)((Object)iOException2)).i + "\u70b9" + string + "\u50cf\u653b\u51fb");
                    }
                    ((StringBuffer)object).append(((b)((Object)iOException2)).f());
                    ((b)((Object)iOException2)).a(((StringBuffer)object).toString());
                    break;
                }
                case 7: {
                    super.b((DataInputStream)object);
                    ((a)((Object)iOException2)).a = ((DataInputStream)object).readShort();
                    ((a)((Object)iOException2)).b = ((DataInputStream)object).readShort();
                    ((a)((Object)iOException2)).c = ((DataInputStream)object).readShort();
                    ((a)((Object)iOException2)).d = ((DataInputStream)object).readByte();
                    ((a)((Object)iOException2)).e = ((DataInputStream)object).readByte();
                    ((a)((Object)iOException2)).f = ((DataInputStream)object).readByte();
                    ((a)((Object)iOException2)).g = ((DataInputStream)object).readByte();
                    object = new StringBuffer();
                    ((StringBuffer)object).append(f.a.a(((a)((Object)iOException2)).a, ((a)((Object)iOException2)).b, ((a)((Object)iOException2)).c));
                    ((StringBuffer)object).append(((b)((Object)iOException2)).f());
                    ((b)((Object)iOException2)).a(((StringBuffer)object).toString());
                    break;
                }
                case 8: {
                    super.b((DataInputStream)object);
                    ((a)((Object)iOException2)).a = ((DataInputStream)object).readShort();
                    ((a)((Object)iOException2)).b = ((DataInputStream)object).readShort();
                    ((a)((Object)iOException2)).c = ((DataInputStream)object).readShort();
                    ((a)((Object)iOException2)).d = ((DataInputStream)object).readByte();
                    ((a)((Object)iOException2)).e = ((DataInputStream)object).readByte();
                    ((a)((Object)iOException2)).f = ((DataInputStream)object).readByte();
                    ((a)((Object)iOException2)).g = ((DataInputStream)object).readByte();
                    object = new StringBuffer();
                    ((StringBuffer)object).append(f.a.a(((a)((Object)iOException2)).a, ((a)((Object)iOException2)).b, ((a)((Object)iOException2)).c));
                    ((StringBuffer)object).append(((b)((Object)iOException2)).f());
                    ((b)((Object)iOException2)).a(((StringBuffer)object).toString());
                    break;
                }
                case 6: {
                    super.b((DataInputStream)object);
                    ((a)((Object)iOException2)).a = ((DataInputStream)object).readShort();
                    ((a)((Object)iOException2)).b = ((DataInputStream)object).readShort();
                    ((a)((Object)iOException2)).c = ((DataInputStream)object).readShort();
                    ((a)((Object)iOException2)).g = ((DataInputStream)object).readByte();
                    object = new StringBuffer();
                    ((StringBuffer)object).append(f.a.a(((a)((Object)iOException2)).a, ((a)((Object)iOException2)).b, ((a)((Object)iOException2)).c));
                    ((StringBuffer)object).append(((b)((Object)iOException2)).f());
                    ((b)((Object)iOException2)).a(((StringBuffer)object).toString());
                    break;
                }
                case 9: {
                    super.d((DataInputStream)object);
                    break;
                }
                case 10: {
                    super.c((DataInputStream)object);
                    break;
                }
                case 16: {
                    super.b((DataInputStream)object);
                    ((a)((Object)iOException2)).j = ((DataInputStream)object).readShort();
                    ((a)((Object)iOException2)).k = ((DataInputStream)object).readShort();
                    ((a)((Object)iOException2)).l = ((DataInputStream)object).readShort();
                    ((a)((Object)iOException2)).a = ((DataInputStream)object).readShort();
                    ((a)((Object)iOException2)).b = ((DataInputStream)object).readShort();
                    ((a)((Object)iOException2)).c = ((DataInputStream)object).readShort();
                    ((a)((Object)iOException2)).n = ((DataInputStream)object).readShort();
                    ((b)((Object)iOException2)).a("\u6d88\u8017" + ((a)((Object)iOException2)).n + "\u70b9\u4f53\u529b," + (((a)((Object)iOException2)).j != 0 ? "\u547d" + (((a)((Object)iOException2)).j > 0 ? "+" : "") + ((a)((Object)iOException2)).j : "") + " " + (((a)((Object)iOException2)).k != 0 ? "\u7075" + (((a)((Object)iOException2)).k > 0 ? "+" : "") + ((a)((Object)iOException2)).k : "") + " " + (((a)((Object)iOException2)).l != 0 ? "\u4f53" + (((a)((Object)iOException2)).l > 0 ? "+" : "") + ((a)((Object)iOException2)).l : "") + " " + (((a)((Object)iOException2)).a != 0 ? "\u653b" + (((a)((Object)iOException2)).a > 0 ? "+" : "") + ((a)((Object)iOException2)).a : "") + " " + (((a)((Object)iOException2)).b != 0 ? "\u9632" + (((a)((Object)iOException2)).b > 0 ? "+" : "") + ((a)((Object)iOException2)).b : "") + " " + (((a)((Object)iOException2)).c != 0 ? "\u654f" + (((a)((Object)iOException2)).c > 0 ? "+" : "") + ((a)((Object)iOException2)).c : ""));
                    ((a)((Object)iOException2)).g = (byte)15;
                    break;
                }
                case 11: {
                    try {
                        super.b((DataInputStream)object);
                        ((a)((Object)iOException2)).w = ((DataInputStream)object).readShort();
                        ((a)((Object)iOException2)).x = ((DataInputStream)object).readShort();
                        ((a)((Object)iOException2)).y = ((DataInputStream)object).readShort();
                        ((a)((Object)iOException2)).z = ((DataInputStream)object).readShort();
                        ((a)((Object)iOException2)).g = (byte)15;
                        break;
                    }
                    catch (IOException iOException) {
                        object = iOException;
                        iOException.printStackTrace();
                    }
                }
                default: {
                    return;
                }
            }
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
        }
    }

    private void c(DataInputStream object) {
        super.b((DataInputStream)object);
        this.a = ((DataInputStream)object).readShort();
        this.b = ((DataInputStream)object).readShort();
        this.c = ((DataInputStream)object).readShort();
        for (int i2 = 0; i2 < 5; ++i2) {
            this.m[i2] = ((DataInputStream)object).readByte();
        }
        this.k = ((DataInputStream)object).readShort();
        this.g = ((DataInputStream)object).readByte();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(f.a.a(this.a, this.b, this.c));
        object = null;
        for (int i3 = 0; i3 < this.m.length; ++i3) {
            object = b.f.m(b.f.C[i3]);
            if (((String)object).equals("") || this.m[i3] == 0) continue;
            stringBuffer.append((String)object + "\u50cf" + (this.m[i3] >= 0 ? "+" : "") + this.m[i3] + "% ");
        }
        stringBuffer.append(this.f());
        this.a(stringBuffer.toString());
    }

    private void d(DataInputStream dataInputStream) {
        super.b(dataInputStream);
        this.a = dataInputStream.readShort();
        this.b = dataInputStream.readShort();
        this.c = dataInputStream.readShort();
        this.h = dataInputStream.readByte();
        byte by = dataInputStream.readByte();
        for (int i2 = 0; i2 < b.f.C.length; ++i2) {
            if ((this.h & b.f.C[i2]) == 0) continue;
            this.m[i2] = by;
        }
        this.d = dataInputStream.readByte();
        this.e = dataInputStream.readByte();
        this.l = dataInputStream.readShort();
        this.g = dataInputStream.readByte();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(f.a.a(this.a, this.b, this.c));
        stringBuffer.append(this.f());
        this.a(stringBuffer.toString());
    }

    private static String a(int n2, int n3, int n4) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\u653b" + (n2 >= 0 ? "+" : "") + n2 + " ");
        stringBuffer.append("\u9632" + (n3 >= 0 ? "+" : "") + n3 + " ");
        stringBuffer.append("\u654f" + (n4 >= 0 ? "+" : "") + n4 + " ");
        return stringBuffer.toString();
    }

    public final b.b[] a(f f2, f f3) {
        return null;
    }

    public final boolean a() {
        return false;
    }

    public final c b() {
        if (this.r == 11 && this.w != 0) {
            int n2 = b.a.l(this.q);
            if (n2 < 0) {
                return b.a.c((int)this.x);
            }
            switch (n2 / this.w) {
                case 0: {
                    return b.a.c((int)this.x);
                }
                case 1: {
                    return b.a.c((int)this.y);
                }
            }
            return b.a.c((int)this.z);
        }
        return null;
    }

    protected final void b(f f2, f f3) {
    }

    protected final void a(f f2) {
    }
}

