/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
package f;

import a.b;
import a.e;
import a.f;
import a.g;
import d.d;
import f.a;
import java.io.DataInputStream;
import javax.microedition.lcdui.Image;

public final class c
extends a {
    public short[] i = new short[27];

    public c(byte by) {
        super(by);
        this.i[8] = 100;
        this.i[9] = 100;
        this.i[10] = 100;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void a(DataInputStream object) {
        Object object2;
        try {
            switch (((a)object2).d) {
                case 1: {
                    super.b((DataInputStream)object);
                    ((c)object2).i[18] = ((DataInputStream)object).readShort();
                    ((a)object2).g = ((DataInputStream)object).readByte();
                    ((c)object2).i[2] = ((DataInputStream)object).readShort();
                    ((c)object2).i[5] = ((DataInputStream)object).readByte();
                    ((c)object2).i[3] = ((DataInputStream)object).readShort();
                    ((c)object2).i[6] = ((DataInputStream)object).readByte();
                    ((c)object2).i[4] = ((DataInputStream)object).readShort();
                    ((c)object2).i[7] = ((DataInputStream)object).readByte();
                    ((c)object2).i[11] = ((DataInputStream)object).readShort();
                    ((c)object2).i[12] = ((DataInputStream)object).readShort();
                    ((c)object2).i[13] = ((DataInputStream)object).readShort();
                    ((c)object2).i[14] = ((DataInputStream)object).readShort();
                    ((c)object2).i[15] = ((DataInputStream)object).readShort();
                    ((c)object2).i[17] = ((DataInputStream)object).readShort();
                    ((c)object2).i[16] = ((DataInputStream)object).readShort();
                    ((c)object2).i[26] = 100;
                    return;
                }
                case 5: {
                    super.b((DataInputStream)object);
                    ((a)object2).g = ((DataInputStream)object).readByte();
                    ((c)object2).i[19] = ((DataInputStream)object).readShort();
                    ((a)object2).h = ((DataInputStream)object).readByte();
                    ((DataInputStream)object).readByte();
                    ((DataInputStream)object).readByte();
                    ((c)object2).i[8] = ((DataInputStream)object).readByte();
                    ((c)object2).i[9] = ((DataInputStream)object).readByte();
                    ((c)object2).i[10] = ((DataInputStream)object).readByte();
                    ((c)object2).i[18] = ((DataInputStream)object).readShort();
                    ((c)object2).i[21] = ((DataInputStream)object).readByte();
                    return;
                }
                case 12: {
                    super.b((DataInputStream)object);
                    ((a)object2).h = ((DataInputStream)object).readByte();
                    ((a)object2).g = ((DataInputStream)object).readByte();
                    ((c)object2).i[24] = ((DataInputStream)object).readByte();
                    ((c)object2).i[8] = b.e.a.a(((DataInputStream)object).readByte());
                    ((c)object2).i[9] = b.e.a.a(((DataInputStream)object).readByte());
                    ((c)object2).i[10] = b.e.a.a(((DataInputStream)object).readByte());
                    ((c)object2).i[20] = ((DataInputStream)object).readByte();
                    ((c)object2).i[19] = ((DataInputStream)object).readShort();
                    ((c)object2).i[18] = ((DataInputStream)object).readShort();
                    ((c)object2).i[21] = ((DataInputStream)object).readByte();
                    ((c)object2).i[26] = ((DataInputStream)object).readByte();
                    ((c)object2).i[2] = ((DataInputStream)object).readShort();
                    ((c)object2).i[5] = ((DataInputStream)object).readByte();
                    ((c)object2).i[3] = ((DataInputStream)object).readShort();
                    ((c)object2).i[4] = ((DataInputStream)object).readShort();
                    ((c)object2).i[22] = ((DataInputStream)object).readShort();
                    ((c)object2).i[0] = ((DataInputStream)object).readShort();
                    ((c)object2).i[1] = ((DataInputStream)object).readShort();
                    object = object2;
                    object2 = new StringBuffer();
                    String string = null;
                    if (((c)object).i[19] > 0) {
                        ((StringBuffer)object2).append("\u9020\u6210");
                        ((StringBuffer)object2).append(f.c.a(((a)object).g));
                        ((StringBuffer)object2).append(((c)object).i[19] + "\u70b9");
                        string = a.g.m(((c)object).i[20]);
                        string = string.equals("") ? "\u6b63\u5e38" : string + "\u8c61";
                        ((StringBuffer)object2).append(string + "\u4f24\u5bb3,");
                        ((StringBuffer)object2).append(super.f());
                        ((a)object).a(((StringBuffer)object2).toString() + ((a)object).e());
                        break;
                    }
                    if (((c)object).i[2] <= 0 && ((c)object).i[3] <= 0) {
                        if (((c)object).i[4] <= 0) return;
                    }
                    ((StringBuffer)object2).append("\u6062\u590d");
                    ((StringBuffer)object2).append(f.c.a(((a)object).g));
                    if (((c)object).i[2] > 0) {
                        ((StringBuffer)object2).append("\u751f\u547d" + ((c)object).i[2]);
                    } else if (((c)object).i[3] > 0) {
                        ((StringBuffer)object2).append("\u7075\u529b" + ((c)object).i[3]);
                    } else if (((c)object).i[4] > 0) {
                        ((StringBuffer)object2).append("\u4f53\u529b" + ((c)object).i[4]);
                    }
                    ((StringBuffer)object2).append("\u70b9,");
                    ((StringBuffer)object2).append(super.f());
                    ((a)object).a(((StringBuffer)object2).toString());
                    return;
                }
                case 15: {
                    super.b((DataInputStream)object);
                    return;
                }
                case 14: {
                    super.b((DataInputStream)object);
                    ((a)object2).g = ((DataInputStream)object).readByte();
                    ((DataInputStream)object).readByte();
                    ((c)object2).i[19] = ((DataInputStream)object).readShort();
                    ((c)object2).i[22] = ((DataInputStream)object).readShort();
                    ((c)object2).i[25] = ((DataInputStream)object).readShort();
                    ((c)object2).i[23] = ((DataInputStream)object).readByte();
                    object = new StringBuffer();
                    ((StringBuffer)object).append("\u9020\u6210");
                    ((StringBuffer)object).append(f.c.a(((a)object2).g));
                    ((StringBuffer)object).append("\u6700\u5c11");
                    ((StringBuffer)object).append(((c)object2).i[19]);
                    ((StringBuffer)object).append("\u70b9\u4ee5\u4e0a\u7684\u6b63\u5e38\u4f24\u5bb3");
                    ((a)object2).a(((StringBuffer)object).toString());
                }
            }
            return;
        }
        catch (Exception exception) {
            object2 = exception;
            exception.printStackTrace();
        }
    }

    public final b[] b(g g2, g g3) {
        if (g3 == null) {
            return null;
        }
        int n2 = 0;
        Image image = a.f.P;
        b[] bArray = null;
        int n3 = g3.s;
        int n4 = g3.t - g3.v / 2;
        if (this.c(g2, g3) != 0) {
            n2 = this.c(g2, g3);
            image = n2 < 0 ? a.f.Q : a.f.P;
            n2 = Math.abs(n2);
        } else if (this.i[2] != 0) {
            n2 = this.i[2];
        } else if (this.i[5] != 0) {
            n2 = g3.y() * this.i[5] / 100;
        } else if (this.i[6] != 0) {
            n2 = g3.z() * this.i[6] / 100;
        } else if (this.i[7] != 0) {
            n2 = g3.A() * this.i[7] / 100;
        } else if (this.i[4] != 0) {
            n2 = this.i[4];
        } else if (this.i[3] != 0) {
            n2 = this.i[3];
        } else {
            if (this.i[18] > 0) {
                for (int i2 = 0; i2 < 15; ++i2) {
                    if ((this.i[18] & 1 << i2) == 0) continue;
                    bArray = new b[1];
                    n3 = g3.s - d.d.a.getWidth() / 13 / 2;
                    n4 = g3.t - g3.v / 2;
                    bArray[0] = a.e.a(d.d.a, n3, n4, (i2 - 1) * d.d.a.getWidth() / 13, 0, d.d.a.getWidth() / 13, d.d.a.getHeight());
                    break;
                }
            } else if (this.i[8] != 100) {
                int n5 = this.i[8] < 0 ? this.i[8] + 256 : this.i[8];
                b[] bArray2 = new b[2];
                bArray = bArray2;
                bArray2[0] = a.e.a(n5 >= 100 ? d.d.c : d.d.d, n3 - d.d.d.getWidth(), n4);
                bArray[1] = a.e.a(d.d.b[0], n3, n4);
            } else if (this.i[9] != 100) {
                int n6 = this.i[9] < 0 ? this.i[9] + 256 : this.i[9];
                b[] bArray3 = new b[2];
                bArray = bArray3;
                bArray3[0] = a.e.a(n6 >= 100 ? d.d.c : d.d.d, n3 - d.d.d.getWidth(), n4);
                bArray[1] = a.e.a(d.d.b[1], n3, n4);
            } else if (this.i[10] != 100) {
                int n7 = this.i[10] < 0 ? this.i[10] + 256 : this.i[10];
                b[] bArray4 = new b[2];
                bArray = bArray4;
                bArray4[0] = a.e.a(n7 >= 100 ? d.d.c : d.d.d, n3 - d.d.d.getWidth(), n4);
                bArray[1] = a.e.a(d.d.b[2], n3, n4);
            }
            return bArray;
        }
        bArray = new b[1];
        n3 = g3.s - String.valueOf(n2).length() * image.getWidth() / 10 / 2;
        n4 = g3.t - g3.v / 2;
        bArray[0] = a.e.a(n2, n3, n4, image);
        return bArray;
    }

    public final boolean a() {
        return this.i[18] < 0 && (this.i[18] & 1) != 0;
    }

    protected final void a(g g2) {
        if (g2 == null) {
            return;
        }
        g2.b((byte)4, (int)this.i[0]);
        g2.b((byte)6, (int)this.i[1]);
    }

    protected final void a(g g2, g g3) {
        if (g2 == null || g3 == null || g3.w() && !this.a()) {
            return;
        }
        g3.b((byte)2, (int)this.i[2]);
        g3.b((byte)2, g3.y() * this.i[5] / 100);
        g3.b((byte)4, (int)this.i[3]);
        g3.b((byte)4, g3.z() * this.i[6] / 100);
        g3.b((byte)6, (int)this.i[4]);
        g3.b((byte)6, g3.A() * this.i[7] / 100);
        int n2 = 0;
        if (this.i[8] != 100) {
            g3.k(0);
            n2 = g3.d() * this.i[8] / 100 - g3.d();
            g3.a(0, n2, (int)this.i[21]);
        }
        if (this.i[9] != 100) {
            g3.k(1);
            n2 = g3.e() * this.i[9] / 100 - g3.e();
            g3.a(1, n2, (int)this.i[21]);
        }
        if (this.i[10] != 100) {
            g3.k(2);
            n2 = g3.f() * this.i[10] / 100 - g3.f();
            g3.a(2, n2, (int)this.i[21]);
        }
        g3.b((byte)3, (int)this.i[11]);
        g3.b((byte)5, (int)this.i[12]);
        g3.b((byte)7, (int)this.i[13]);
        g3.b((byte)8, (int)this.i[14]);
        g3.b((byte)10, (int)this.i[15]);
        g3.b((byte)9, (int)this.i[16]);
        g3.b((byte)11, (int)this.i[17]);
        if (b.e.a.c(100) < this.i[26] && this.i[18] != 0) {
            g3.c(this.i[18]);
        }
        if (this.i[21] > 0) {
            g3.i(this.i[21]);
        }
        g3.b((byte)2, this.c(g2, g3));
    }

    private int c(g g2, g g3) {
        switch (this.d) {
            case 12: {
                g2 = g3;
                int n2 = a.g.a(this.i[20], this.i[19], g2);
                if (n2 < 0 && g2.P) {
                    n2 = n2 * (50 + b.e.a.c(50)) / 100;
                }
                return n2;
            }
            case 5: 
            case 14: {
                return -this.i[19];
            }
        }
        return 0;
    }

    private String f() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\u6d88\u8017");
        if (this.i[0] < 0) {
            stringBuffer.append(-this.i[0] + "\u7075\u529b ");
        }
        if (this.i[1] < 0) {
            stringBuffer.append(-this.i[1] + "\u4f53\u529b ");
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

