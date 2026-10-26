/*
 * Decompiled with CFR 0.152.
 */
package c;

import c.f;
import d.b;
import d.g;
import java.io.DataInputStream;
import java.io.IOException;

public final class d
extends f {
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
    public int o = -1;

    public d(byte by) {
        super(by);
    }

    public final void a(DataInputStream dataInputStream) {
        try {
            switch (((f)((Object)iOException3)).q) {
                case 4: {
                    super.b(dataInputStream);
                    ((d)((Object)iOException3)).a = dataInputStream.readShort();
                    ((d)((Object)iOException3)).b = dataInputStream.readShort();
                    ((d)((Object)iOException3)).c = dataInputStream.readShort();
                    ((d)((Object)iOException3)).h = dataInputStream.readByte();
                    ((d)((Object)iOException3)).i = dataInputStream.readShort();
                    dataInputStream.readShort();
                    dataInputStream.readByte();
                    ((d)((Object)iOException3)).g = dataInputStream.readByte();
                    break;
                }
                case 7: {
                    super.b(dataInputStream);
                    ((d)((Object)iOException3)).a = dataInputStream.readShort();
                    ((d)((Object)iOException3)).b = dataInputStream.readShort();
                    ((d)((Object)iOException3)).c = dataInputStream.readShort();
                    ((d)((Object)iOException3)).h = dataInputStream.readByte();
                    byte by = dataInputStream.readByte();
                    for (int i2 = 0; i2 < d.g.u.length; ++i2) {
                        if ((((d)((Object)iOException3)).h & d.g.u[i2]) == 0) continue;
                        ((d)((Object)iOException3)).m[i2] = by;
                    }
                    ((d)((Object)iOException3)).d = dataInputStream.readByte();
                    ((d)((Object)iOException3)).e = dataInputStream.readByte();
                    ((d)((Object)iOException3)).f = dataInputStream.readByte();
                    ((d)((Object)iOException3)).g = dataInputStream.readByte();
                    break;
                }
                case 8: {
                    super.b(dataInputStream);
                    ((d)((Object)iOException3)).a = dataInputStream.readShort();
                    ((d)((Object)iOException3)).b = dataInputStream.readShort();
                    ((d)((Object)iOException3)).c = dataInputStream.readShort();
                    ((d)((Object)iOException3)).d = dataInputStream.readByte();
                    ((d)((Object)iOException3)).e = dataInputStream.readByte();
                    ((d)((Object)iOException3)).f = dataInputStream.readByte();
                    ((d)((Object)iOException3)).g = dataInputStream.readByte();
                    break;
                }
                case 6: {
                    super.b(dataInputStream);
                    ((d)((Object)iOException3)).a = dataInputStream.readShort();
                    ((d)((Object)iOException3)).b = dataInputStream.readShort();
                    ((d)((Object)iOException3)).c = dataInputStream.readShort();
                    ((d)((Object)iOException3)).g = dataInputStream.readByte();
                    break;
                }
                case 9: {
                    super.b(dataInputStream);
                    ((d)((Object)iOException3)).a = dataInputStream.readShort();
                    ((d)((Object)iOException3)).b = dataInputStream.readShort();
                    ((d)((Object)iOException3)).c = dataInputStream.readShort();
                    ((d)((Object)iOException3)).h = dataInputStream.readByte();
                    byte by = dataInputStream.readByte();
                    for (int i3 = 0; i3 < d.g.u.length; ++i3) {
                        if ((((d)((Object)iOException3)).h & d.g.u[i3]) == 0) continue;
                        ((d)((Object)iOException3)).m[i3] = by;
                    }
                    ((d)((Object)iOException3)).d = dataInputStream.readByte();
                    ((d)((Object)iOException3)).e = dataInputStream.readByte();
                    ((d)((Object)iOException3)).l = dataInputStream.readShort();
                    ((d)((Object)iOException3)).g = dataInputStream.readByte();
                    break;
                }
                case 10: {
                    super.b(dataInputStream);
                    ((d)((Object)iOException3)).a = dataInputStream.readShort();
                    ((d)((Object)iOException3)).b = dataInputStream.readShort();
                    ((d)((Object)iOException3)).c = dataInputStream.readShort();
                    for (int i4 = 0; i4 < 5; ++i4) {
                        ((d)((Object)iOException3)).m[i4] = dataInputStream.readByte();
                    }
                    ((d)((Object)iOException3)).k = dataInputStream.readShort();
                    ((d)((Object)iOException3)).g = dataInputStream.readByte();
                    break;
                }
                case 16: {
                    super.b(dataInputStream);
                    ((d)((Object)iOException3)).j = dataInputStream.readShort();
                    ((d)((Object)iOException3)).k = dataInputStream.readShort();
                    ((d)((Object)iOException3)).l = dataInputStream.readShort();
                    ((d)((Object)iOException3)).a = dataInputStream.readShort();
                    ((d)((Object)iOException3)).b = dataInputStream.readShort();
                    ((d)((Object)iOException3)).c = dataInputStream.readShort();
                    ((d)((Object)iOException3)).n = dataInputStream.readShort();
                    ((f)((Object)iOException3)).s = "\u6d88\u8017" + ((d)((Object)iOException3)).n + "\u70b9\u4f53\u529b," + (((d)((Object)iOException3)).j != 0 ? "\u547d" + (((d)((Object)iOException3)).j > 0 ? "+" : "") + ((d)((Object)iOException3)).j : "") + " " + (((d)((Object)iOException3)).k != 0 ? "\u7075" + (((d)((Object)iOException3)).k > 0 ? "+" : "") + ((d)((Object)iOException3)).k : "") + " " + (((d)((Object)iOException3)).l != 0 ? "\u4f53" + (((d)((Object)iOException3)).l > 0 ? "+" : "") + ((d)((Object)iOException3)).l : "") + " " + (((d)((Object)iOException3)).a != 0 ? "\u653b" + (((d)((Object)iOException3)).a > 0 ? "+" : "") + ((d)((Object)iOException3)).a : "") + " " + (((d)((Object)iOException3)).b != 0 ? "\u9632" + (((d)((Object)iOException3)).b > 0 ? "+" : "") + ((d)((Object)iOException3)).b : "") + " " + (((d)((Object)iOException3)).c != 0 ? "\u654f" + (((d)((Object)iOException3)).c > 0 ? "+" : "") + ((d)((Object)iOException3)).c : "");
                    ((d)((Object)iOException3)).g = (byte)15;
                    break;
                }
                case 11: {
                    try {
                        super.b(dataInputStream);
                        ((d)((Object)iOException3)).o = dataInputStream.readByte();
                        ((d)((Object)iOException3)).g = (byte)15;
                        break;
                    }
                    catch (IOException iOException) {
                        IOException iOException2 = iOException;
                        iOException.printStackTrace();
                    }
                }
                default: {
                    return;
                }
            }
        }
        catch (IOException iOException) {
            IOException iOException3 = iOException;
            iOException.printStackTrace();
        }
    }

    public final void a(g g2, g g3, g[] gArray) {
    }

    public final b[] a(g g2, g g3) {
        return null;
    }

    public final boolean a() {
        return false;
    }
}

