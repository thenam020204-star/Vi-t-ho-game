/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
package f;

import b.a;
import b.d;
import b.f;
import f.b;
import java.io.DataInputStream;
import javax.microedition.lcdui.Image;

public final class c
extends b {
    public short[] a = new short[27];

    public c(byte by) {
        super(by);
        this.a[8] = 100;
        this.a[9] = 100;
        this.a[10] = 100;
    }

    public final void a(DataInputStream object) {
        block16: {
            Object object2;
            try {
                switch (((b)object2).s) {
                    case 1: {
                        super.b((DataInputStream)object);
                        ((c)object2).a[18] = ((DataInputStream)object).readShort();
                        ((b)object2).v = ((DataInputStream)object).readByte();
                        ((c)object2).a[2] = ((DataInputStream)object).readShort();
                        ((c)object2).a[5] = ((DataInputStream)object).readByte();
                        ((c)object2).a[3] = ((DataInputStream)object).readShort();
                        ((c)object2).a[6] = ((DataInputStream)object).readByte();
                        ((c)object2).a[4] = ((DataInputStream)object).readShort();
                        ((c)object2).a[7] = ((DataInputStream)object).readByte();
                        ((c)object2).a[11] = ((DataInputStream)object).readShort();
                        ((c)object2).a[12] = ((DataInputStream)object).readShort();
                        ((c)object2).a[13] = ((DataInputStream)object).readShort();
                        ((c)object2).a[14] = ((DataInputStream)object).readShort();
                        ((c)object2).a[15] = ((DataInputStream)object).readShort();
                        ((c)object2).a[17] = ((DataInputStream)object).readShort();
                        ((c)object2).a[16] = ((DataInputStream)object).readShort();
                        ((c)object2).a[26] = 100;
                        break block16;
                    }
                    case 5: {
                        super.b((DataInputStream)object);
                        ((b)object2).v = ((DataInputStream)object).readByte();
                        ((c)object2).a[19] = ((DataInputStream)object).readShort();
                        ((b)object2).w = ((DataInputStream)object).readByte();
                        ((DataInputStream)object).readByte();
                        ((DataInputStream)object).readByte();
                        ((c)object2).a[8] = ((DataInputStream)object).readByte();
                        ((c)object2).a[9] = ((DataInputStream)object).readByte();
                        ((c)object2).a[10] = ((DataInputStream)object).readByte();
                        ((c)object2).a[18] = ((DataInputStream)object).readShort();
                        ((c)object2).a[21] = ((DataInputStream)object).readByte();
                        break block16;
                    }
                    case 12: {
                        super.b((DataInputStream)object);
                        ((b)object2).w = ((DataInputStream)object).readByte();
                        ((b)object2).v = ((DataInputStream)object).readByte();
                        ((c)object2).a[24] = ((DataInputStream)object).readByte();
                        ((c)object2).a[8] = e.a.b.a(((DataInputStream)object).readByte());
                        ((c)object2).a[9] = e.a.b.a(((DataInputStream)object).readByte());
                        ((c)object2).a[10] = e.a.b.a(((DataInputStream)object).readByte());
                        ((c)object2).a[20] = ((DataInputStream)object).readByte();
                        ((c)object2).a[19] = ((DataInputStream)object).readShort();
                        ((c)object2).a[18] = ((DataInputStream)object).readShort();
                        ((c)object2).a[21] = ((DataInputStream)object).readByte();
                        ((c)object2).a[26] = ((DataInputStream)object).readByte();
                        ((c)object2).a[2] = ((DataInputStream)object).readShort();
                        ((c)object2).a[5] = ((DataInputStream)object).readByte();
                        ((c)object2).a[3] = ((DataInputStream)object).readShort();
                        ((c)object2).a[4] = ((DataInputStream)object).readShort();
                        ((c)object2).a[22] = ((DataInputStream)object).readShort();
                        ((c)object2).a[0] = ((DataInputStream)object).readShort();
                        ((c)object2).a[1] = ((DataInputStream)object).readShort();
                        object = object2;
                        object2 = new StringBuffer();
                        String string = null;
                        if (((c)object).a[19] > 0) {
                            ((StringBuffer)object2).append("\u9020\u6210");
                            ((StringBuffer)object2).append(c.a((int)((b)object).v));
                            ((StringBuffer)object2).append(((c)object).a[19] + "\u70b9");
                            string = f.m(((c)object).a[20]);
                            string = string.equals("") ? "\u6b63\u5e38" : string + "\u8c61";
                            ((StringBuffer)object2).append(string + "\u4f24\u5bb3,");
                            ((StringBuffer)object2).append(super.e());
                            ((b)object).a(((StringBuffer)object2).toString() + ((b)object).d());
                            break;
                        }
                        if (((c)object).a[2] > 0 || ((c)object).a[3] > 0 || ((c)object).a[4] > 0) {
                            ((StringBuffer)object2).append("\u6062\u590d");
                            ((StringBuffer)object2).append(c.a((int)((b)object).v));
                            if (((c)object).a[2] > 0) {
                                ((StringBuffer)object2).append("\u751f\u547d" + ((c)object).a[2]);
                            } else if (((c)object).a[3] > 0) {
                                ((StringBuffer)object2).append("\u7075\u529b" + ((c)object).a[3]);
                            } else if (((c)object).a[4] > 0) {
                                ((StringBuffer)object2).append("\u4f53\u529b" + ((c)object).a[4]);
                            }
                            ((StringBuffer)object2).append("\u70b9,");
                            ((StringBuffer)object2).append(super.e());
                            ((b)object).a(((StringBuffer)object2).toString());
                        }
                        break block16;
                    }
                    case 15: {
                        super.b((DataInputStream)object);
                        break block16;
                    }
                    case 14: {
                        super.b((DataInputStream)object);
                        ((b)object2).v = ((DataInputStream)object).readByte();
                        ((DataInputStream)object).readByte();
                        ((c)object2).a[19] = ((DataInputStream)object).readShort();
                        ((c)object2).a[22] = ((DataInputStream)object).readShort();
                        ((c)object2).a[25] = ((DataInputStream)object).readShort();
                        ((c)object2).a[23] = ((DataInputStream)object).readByte();
                        object = new StringBuffer();
                        ((StringBuffer)object).append("\u9020\u6210");
                        ((StringBuffer)object).append(c.a((int)((b)object2).v));
                        ((StringBuffer)object).append("\u6700\u5c11");
                        ((StringBuffer)object).append(((c)object2).a[19]);
                        ((StringBuffer)object).append("\u70b9\u4ee5\u4e0a\u7684\u6b63\u5e38\u4f24\u5bb3");
                        ((b)object2).a(((StringBuffer)object).toString());
                        break block16;
                    }
                    default: {
                        System.out.println("unknown type == " + ((b)object2).s);
                    }
                }
                return;
            }
            catch (Exception exception) {
                object2 = exception;
                exception.printStackTrace();
                System.out.println("\u52a0\u8f7d\u7269\u54c1\u51fa\u9519!");
            }
        }
    }

    public final b.b[] a(f f2, f f3) {
        if (f3 == null) {
            return null;
        }
        int n2 = 0;
        Image image = b.a.V;
        b.b[] bArray = null;
        int n3 = f3.j;
        int n4 = f3.k - f3.m / 2;
        if (this.c(f2, f3) != 0) {
            n2 = this.c(f2, f3);
            image = n2 < 0 ? b.a.W : b.a.V;
            n2 = Math.abs(n2);
        } else if (this.a[2] != 0) {
            n2 = this.a[2];
        } else if (this.a[5] != 0) {
            n2 = f3.c(3) * this.a[5] / 100;
        } else if (this.a[6] != 0) {
            n2 = f3.c(5) * this.a[6] / 100;
        } else if (this.a[7] != 0) {
            n2 = f3.c(7) * this.a[7] / 100;
        } else if (this.a[4] != 0) {
            n2 = this.a[4];
        } else if (this.a[3] != 0) {
            n2 = this.a[3];
        } else {
            if (this.a[18] > 0) {
                for (int i2 = 0; i2 < 15; ++i2) {
                    if ((this.a[18] & 1 << i2) == 0) continue;
                    bArray = new b.b[1];
                    n3 = f3.j - d.c.g.getWidth() / 13 / 2;
                    n4 = f3.k - f3.m / 2;
                    bArray[0] = d.a(d.c.g, n3, n4, (i2 - 1) * d.c.g.getWidth() / 13, 0, d.c.g.getWidth() / 13, d.c.g.getHeight());
                    break;
                }
            } else if (this.a[8] != 100) {
                int n5 = this.a[8] < 0 ? this.a[8] + 256 : this.a[8];
                b.b[] bArray2 = new b.b[2];
                bArray = bArray2;
                bArray2[0] = d.a(n5 >= 100 ? d.c.i : d.c.j, n3 - d.c.j.getWidth(), n4);
                bArray[1] = d.a(d.c.h[0], n3, n4);
            } else if (this.a[9] != 100) {
                int n6 = this.a[9] < 0 ? this.a[9] + 256 : this.a[9];
                b.b[] bArray3 = new b.b[2];
                bArray = bArray3;
                bArray3[0] = d.a(n6 >= 100 ? d.c.i : d.c.j, n3 - d.c.j.getWidth(), n4);
                bArray[1] = d.a(d.c.h[1], n3, n4);
            } else if (this.a[10] != 100) {
                int n7 = this.a[10] < 0 ? this.a[10] + 256 : this.a[10];
                b.b[] bArray4 = new b.b[2];
                bArray = bArray4;
                bArray4[0] = d.a(n7 >= 100 ? d.c.i : d.c.j, n3 - d.c.j.getWidth(), n4);
                bArray[1] = d.a(d.c.h[2], n3, n4);
            }
            return bArray;
        }
        bArray = new b.b[1];
        n3 = f3.j - String.valueOf(n2).length() * image.getWidth() / 10 / 2;
        n4 = f3.k - f3.m / 2;
        bArray[0] = d.a(n2, n3, n4, image);
        return bArray;
    }

    public final boolean a() {
        return this.a[18] < 0 && (this.a[18] & 1) != 0;
    }

    protected final void b(f f2) {
        if (f2 == null) {
            return;
        }
        f2.b((byte)4, (int)this.a[0]);
        f2.b((byte)6, (int)this.a[1]);
    }

    protected final void b(f f2, f f3) {
        if (f2 == null || f3 == null || f3.w() && !this.a()) {
            return;
        }
        f3.b((byte)2, (int)this.a[2]);
        f3.b((byte)2, f3.c(3) * this.a[5] / 100);
        f3.b((byte)4, (int)this.a[3]);
        f3.b((byte)4, f3.c(5) * this.a[6] / 100);
        f3.b((byte)6, (int)this.a[4]);
        f3.b((byte)6, f3.c(7) * this.a[7] / 100);
        int n2 = 0;
        if (this.a[8] != 100) {
            f3.k(0);
            n2 = f3.l() * this.a[8] / 100 - f3.l();
            f3.a(0, n2, (int)this.a[21]);
        }
        if (this.a[9] != 100) {
            f3.k(1);
            n2 = f3.m() * this.a[9] / 100 - f3.m();
            f3.a(1, n2, (int)this.a[21]);
        }
        if (this.a[10] != 100) {
            f3.k(2);
            n2 = f3.n() * this.a[10] / 100 - f3.n();
            f3.a(2, n2, (int)this.a[21]);
        }
        f3.b((byte)3, (int)this.a[11]);
        f3.b((byte)5, (int)this.a[12]);
        f3.b((byte)7, (int)this.a[13]);
        f3.b((byte)8, (int)this.a[14]);
        f3.b((byte)10, (int)this.a[15]);
        f3.b((byte)9, (int)this.a[16]);
        f3.b((byte)11, (int)this.a[17]);
        if (e.a.b.c(100) < this.a[26] && this.a[18] != 0) {
            f3.e(this.a[18]);
        }
        if (this.a[21] > 0) {
            f3.i(this.a[21]);
        }
        f3.b((byte)2, this.c(f2, f3));
    }

    private int c(f f2, f f3) {
        switch (this.s) {
            case 12: {
                f2 = f3;
                int n2 = f.a(this.a[20], this.a[19], f2);
                if (n2 < 0 && f2.G) {
                    n2 = n2 * (50 + e.a.b.c(50)) / 100;
                }
                return n2;
            }
            case 5: 
            case 14: {
                return -this.a[19];
            }
        }
        return 0;
    }

    private String e() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\u6d88\u8017");
        if (this.a[0] < 0) {
            stringBuffer.append(-this.a[0] + "\u7075\u529b ");
        }
        if (this.a[1] < 0) {
            stringBuffer.append(-this.a[1] + "\u4f53\u529b ");
        }
        return stringBuffer.toString();
    }

    private static String a(int n2) {
        switch (n2) {
            case 0: {
                return "\u5168\u4f53";
            }
            case 1: {
                return "\u5355\u4f53";
            }
        }
        return "";
    }
}

