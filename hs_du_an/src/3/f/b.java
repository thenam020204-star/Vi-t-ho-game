/*
 * Decompiled with CFR 0.152.
 */
package f;

import a.f;
import a.g;
import f.a;
import f.c;
import java.io.DataInputStream;
import java.io.IOException;

public final class b
extends a {
    public short i = 0;
    public short j = 0;
    public short k = 0;
    public byte l = 0;
    public byte m = 0;
    public short n = 0;
    public short o = 0;
    public short p = 0;
    public short q = 0;
    public short[] r = new short[5];
    public short s = 0;
    private short t = 0;
    private short u = (short)-1;
    private short v = (short)-1;
    private short w = (short)-1;

    public b(byte by) {
        super(by);
    }

    public final void a(DataInputStream object) {
        try {
            switch (((a)((Object)iOException2)).d) {
                case 4: {
                    super.b((DataInputStream)object);
                    ((b)((Object)iOException2)).i = ((DataInputStream)object).readShort();
                    ((b)((Object)iOException2)).j = ((DataInputStream)object).readShort();
                    ((b)((Object)iOException2)).k = ((DataInputStream)object).readShort();
                    ((b)((Object)iOException2)).m = ((DataInputStream)object).readByte();
                    ((b)((Object)iOException2)).n = ((DataInputStream)object).readShort();
                    ((DataInputStream)object).readShort();
                    ((DataInputStream)object).readByte();
                    ((b)((Object)iOException2)).l = ((DataInputStream)object).readByte();
                    object = new StringBuffer();
                    ((StringBuffer)object).append(f.b.a(((b)((Object)iOException2)).i, ((b)((Object)iOException2)).j, ((b)((Object)iOException2)).k));
                    String string = a.g.m(((b)((Object)iOException2)).m);
                    if (((b)((Object)iOException2)).m != 0 && !string.equals("") && ((b)((Object)iOException2)).n > 0) {
                        ((StringBuffer)object).append(",\u5e76\u5e26\u6709" + ((b)((Object)iOException2)).n + "\u70b9" + string + "\u50cf\u653b\u51fb");
                    }
                    ((StringBuffer)object).append(((a)((Object)iOException2)).e());
                    ((a)((Object)iOException2)).a(((StringBuffer)object).toString());
                    break;
                }
                case 7: {
                    super.b((DataInputStream)object);
                    ((b)((Object)iOException2)).i = ((DataInputStream)object).readShort();
                    ((b)((Object)iOException2)).j = ((DataInputStream)object).readShort();
                    ((b)((Object)iOException2)).k = ((DataInputStream)object).readShort();
                    ((DataInputStream)object).readByte();
                    ((DataInputStream)object).readByte();
                    ((DataInputStream)object).readByte();
                    ((b)((Object)iOException2)).l = ((DataInputStream)object).readByte();
                    object = new StringBuffer();
                    ((StringBuffer)object).append(f.b.a(((b)((Object)iOException2)).i, ((b)((Object)iOException2)).j, ((b)((Object)iOException2)).k));
                    ((StringBuffer)object).append(((a)((Object)iOException2)).e());
                    ((a)((Object)iOException2)).a(((StringBuffer)object).toString());
                    break;
                }
                case 8: {
                    super.b((DataInputStream)object);
                    ((b)((Object)iOException2)).i = ((DataInputStream)object).readShort();
                    ((b)((Object)iOException2)).j = ((DataInputStream)object).readShort();
                    ((b)((Object)iOException2)).k = ((DataInputStream)object).readShort();
                    ((DataInputStream)object).readByte();
                    ((DataInputStream)object).readByte();
                    ((DataInputStream)object).readByte();
                    ((b)((Object)iOException2)).l = ((DataInputStream)object).readByte();
                    object = new StringBuffer();
                    ((StringBuffer)object).append(f.b.a(((b)((Object)iOException2)).i, ((b)((Object)iOException2)).j, ((b)((Object)iOException2)).k));
                    ((StringBuffer)object).append(((a)((Object)iOException2)).e());
                    ((a)((Object)iOException2)).a(((StringBuffer)object).toString());
                    break;
                }
                case 6: {
                    super.b((DataInputStream)object);
                    ((b)((Object)iOException2)).i = ((DataInputStream)object).readShort();
                    ((b)((Object)iOException2)).j = ((DataInputStream)object).readShort();
                    ((b)((Object)iOException2)).k = ((DataInputStream)object).readShort();
                    ((b)((Object)iOException2)).l = ((DataInputStream)object).readByte();
                    object = new StringBuffer();
                    ((StringBuffer)object).append(f.b.a(((b)((Object)iOException2)).i, ((b)((Object)iOException2)).j, ((b)((Object)iOException2)).k));
                    ((StringBuffer)object).append(((a)((Object)iOException2)).e());
                    ((a)((Object)iOException2)).a(((StringBuffer)object).toString());
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
                    ((b)((Object)iOException2)).o = ((DataInputStream)object).readShort();
                    ((b)((Object)iOException2)).p = ((DataInputStream)object).readShort();
                    ((b)((Object)iOException2)).q = ((DataInputStream)object).readShort();
                    ((b)((Object)iOException2)).i = ((DataInputStream)object).readShort();
                    ((b)((Object)iOException2)).j = ((DataInputStream)object).readShort();
                    ((b)((Object)iOException2)).k = ((DataInputStream)object).readShort();
                    ((b)((Object)iOException2)).s = ((DataInputStream)object).readShort();
                    ((a)((Object)iOException2)).a("\u6d88\u8017" + ((b)((Object)iOException2)).s + "\u70b9\u4f53\u529b," + (((b)((Object)iOException2)).o != 0 ? "\u547d" + (((b)((Object)iOException2)).o > 0 ? "+" : "") + ((b)((Object)iOException2)).o : "") + " " + (((b)((Object)iOException2)).p != 0 ? "\u7075" + (((b)((Object)iOException2)).p > 0 ? "+" : "") + ((b)((Object)iOException2)).p : "") + " " + (((b)((Object)iOException2)).q != 0 ? "\u4f53" + (((b)((Object)iOException2)).q > 0 ? "+" : "") + ((b)((Object)iOException2)).q : "") + " " + (((b)((Object)iOException2)).i != 0 ? "\u653b" + (((b)((Object)iOException2)).i > 0 ? "+" : "") + ((b)((Object)iOException2)).i : "") + " " + (((b)((Object)iOException2)).j != 0 ? "\u9632" + (((b)((Object)iOException2)).j > 0 ? "+" : "") + ((b)((Object)iOException2)).j : "") + " " + (((b)((Object)iOException2)).k != 0 ? "\u654f" + (((b)((Object)iOException2)).k > 0 ? "+" : "") + ((b)((Object)iOException2)).k : ""));
                    ((b)((Object)iOException2)).l = (byte)15;
                    break;
                }
                case 11: {
                    try {
                        super.b((DataInputStream)object);
                        ((b)((Object)iOException2)).t = ((DataInputStream)object).readShort();
                        ((b)((Object)iOException2)).u = ((DataInputStream)object).readShort();
                        ((b)((Object)iOException2)).v = ((DataInputStream)object).readShort();
                        ((b)((Object)iOException2)).w = ((DataInputStream)object).readShort();
                        ((b)((Object)iOException2)).l = (byte)15;
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
        this.i = ((DataInputStream)object).readShort();
        this.j = ((DataInputStream)object).readShort();
        this.k = ((DataInputStream)object).readShort();
        for (int i2 = 0; i2 < 5; ++i2) {
            this.r[i2] = ((DataInputStream)object).readByte();
        }
        this.p = ((DataInputStream)object).readShort();
        this.l = ((DataInputStream)object).readByte();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(f.b.a(this.i, this.j, this.k));
        object = null;
        for (int i3 = 0; i3 < this.r.length; ++i3) {
            object = a.g.m(a.g.C[i3]);
            if (((String)object).equals("") || this.r[i3] == 0) continue;
            stringBuffer.append((String)object + "\u50cf" + (this.r[i3] >= 0 ? "+" : "") + this.r[i3] + "% ");
        }
        stringBuffer.append(this.e());
        this.a(stringBuffer.toString());
    }

    private void d(DataInputStream dataInputStream) {
        super.b(dataInputStream);
        this.i = dataInputStream.readShort();
        this.j = dataInputStream.readShort();
        this.k = dataInputStream.readShort();
        this.m = dataInputStream.readByte();
        byte by = dataInputStream.readByte();
        for (int i2 = 0; i2 < a.g.C.length; ++i2) {
            if ((this.m & a.g.C[i2]) == 0) continue;
            this.r[i2] = by;
        }
        dataInputStream.readByte();
        dataInputStream.readByte();
        this.q = dataInputStream.readShort();
        this.l = dataInputStream.readByte();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(f.b.a(this.i, this.j, this.k));
        stringBuffer.append(this.e());
        this.a(stringBuffer.toString());
    }

    private static String a(int n2, int n3, int n4) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\u653b" + (n2 >= 0 ? "+" : "") + n2 + " ");
        stringBuffer.append("\u9632" + (n3 >= 0 ? "+" : "") + n3 + " ");
        stringBuffer.append("\u654f" + (n4 >= 0 ? "+" : "") + n4 + " ");
        return stringBuffer.toString();
    }

    public final a.b[] b(g g2, g g3) {
        return null;
    }

    public final boolean a() {
        return false;
    }

    public final c f() {
        if (this.d == 11 && this.t != 0) {
            int n2 = a.f.k(this.c);
            switch (n2 / this.t) {
                case 0: {
                    return a.f.c((int)this.u);
                }
                case 1: {
                    return a.f.c((int)this.v);
                }
            }
            return a.f.c((int)this.w);
        }
        return null;
    }

    protected final void a(g g2, g g3) {
    }

    protected final void a(g g2) {
    }
}

