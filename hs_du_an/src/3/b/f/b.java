/*
 * Decompiled with CFR 0.152.
 */
package b.f;

import a.f;
import a.g;
import b.f.a;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

public abstract class b {
    public static byte a = 1;
    public static byte b = (byte)2;
    public int c = -1;
    public a.b d = null;
    public int e = -1;
    public int f = -1;
    public short g = (short)-1;
    public short h = (short)-1;
    public short i = (short)-1;
    public short j = (short)-1;
    public byte k = (byte)-1;
    public byte l = (byte)-1;
    public byte m = (byte)-2;
    protected int n = 0;
    protected int o = 0;
    public b[] p = null;
    public int q = 0;
    protected String r = null;
    protected String s = null;
    private int y = 2;
    protected b t = null;
    protected static int[] u = null;
    protected f.a v = null;
    protected g w = null;
    protected g[] x = null;

    protected b(int n2) {
        this.c = n2;
    }

    public static b a(short s, byte by, short s2, byte by2, short s3) {
        a a2 = new a(5);
        new a(5).g = s;
        a2.h = s3;
        a2.i = s2;
        a2.k = by;
        a2.l = by2;
        return a2;
    }

    public static b a(int n2, int n3, a.b b2, int n4, int n5) {
        a a2 = new a(29);
        new a(29).g = (short)n4;
        a2.h = (short)n2;
        a2.i = (short)n3;
        a2.d = b2;
        a2.k = (byte)n5;
        return a2;
    }

    public abstract void a();

    public static b a(DataInputStream object, b b2) {
        if (object == null) {
            return null;
        }
        try {
            int n2 = ((DataInputStream)object).readByte();
            Object object2 = null;
            switch (n2) {
                case 4: {
                    a a2 = new a(4);
                    int n3 = ((DataInputStream)object).readInt();
                    if (n3 > 0) {
                        a2.p = new b[n3];
                        for (int i2 = 0; i2 < n3; ++i2) {
                            a2.p[i2] = b.f.b.a((DataInputStream)object, a2);
                        }
                    }
                    object2 = a2;
                    break;
                }
                case 1: {
                    a a3 = new a(1);
                    new a(1).r = ((DataInputStream)object).readUTF();
                    a3.g = ((DataInputStream)object).readByte();
                    if (a3.g == 1) {
                        a3.s = ((DataInputStream)object).readUTF();
                    } else {
                        a3.h = ((DataInputStream)object).readByte();
                    }
                    a3.k = ((DataInputStream)object).readByte();
                    a3.l = ((DataInputStream)object).readByte();
                    a3.m = ((DataInputStream)object).readByte();
                    object2 = a3;
                    break;
                }
                case 2: {
                    a a4 = new a(2);
                    int n4 = ((DataInputStream)object).readByte();
                    b[] bArray = null;
                    if (n4 > 0) {
                        bArray = new b[n4];
                        for (int i3 = 0; i3 < n4; ++i3) {
                            bArray[i3] = b.f.b.a((DataInputStream)object, a4);
                        }
                    }
                    a4.p = bArray;
                    object2 = a4;
                    break;
                }
                case 3: {
                    a a5 = new a(3);
                    new a(3).r = ((DataInputStream)object).readUTF();
                    int n5 = ((DataInputStream)object).readShort();
                    if (n5 > 0) {
                        a5.p = new b[n5];
                        for (int i4 = 0; i4 < n5; ++i4) {
                            a5.p[i4] = b.f.b.a((DataInputStream)object, a5);
                        }
                    }
                    object2 = a5;
                    break;
                }
                case 5: {
                    n2 = ((DataInputStream)object).readShort();
                    byte by = ((DataInputStream)object).readByte();
                    short s = ((DataInputStream)object).readShort();
                    byte by2 = ((DataInputStream)object).readByte();
                    short s2 = ((DataInputStream)object).readShort();
                    object2 = object = b.f.b.a((short)n2, by, s, by2, s2);
                    break;
                }
                case 7: {
                    n2 = ((DataInputStream)object).readByte();
                    int n6 = ((DataInputStream)object).readByte();
                    int n7 = ((DataInputStream)object).readInt();
                    int n8 = ((DataInputStream)object).readInt();
                    short s = ((DataInputStream)object).readShort();
                    int n9 = ((DataInputStream)object).readByte();
                    int n10 = n2;
                    int n11 = n7;
                    int n12 = n8;
                    short s3 = s;
                    s = (short)n9;
                    n8 = s3;
                    n7 = n12;
                    n2 = n11;
                    n9 = b.e.a.a(n6);
                    n6 = n10;
                    a a6 = new a(7);
                    new a(7).k = (byte)n6;
                    a6.g = (short)n9;
                    a6.e = n2;
                    a6.f = n7;
                    a6.h = (short)n8;
                    a6.l = (byte)s;
                    a a7 = a6;
                    object2 = a7;
                    break;
                }
                case 8: {
                    byte by = ((DataInputStream)object).readByte();
                    n2 = by;
                    byte by3 = by;
                    a a8 = new a(8);
                    new a(8).k = by3;
                    object2 = a8;
                    break;
                }
                case 6: {
                    Object object3;
                    byte by;
                    n2 = ((DataInputStream)object).readByte();
                    byte by4 = by = ((DataInputStream)object).readByte();
                    int n13 = n2;
                    object = new a(6);
                    new a(6).k = (byte)n13;
                    ((b)object).l = by4;
                    object2 = object3 = object;
                    break;
                }
                case 9: {
                    Object object4;
                    n2 = ((DataInputStream)object).readShort();
                    short s = ((DataInputStream)object).readShort();
                    int n14 = ((DataInputStream)object).readShort();
                    int n15 = n2;
                    n2 = n14;
                    short s4 = s;
                    int n16 = n15;
                    object2 = new a(9);
                    new a(9).i = n16;
                    ((b)object2).g = s4;
                    ((b)object2).h = (short)n2;
                    object2 = object4 = object2;
                    break;
                }
                case 10: {
                    a a9 = new a(10);
                    new a(10).g = ((DataInputStream)object).readShort();
                    a9.h = ((DataInputStream)object).readShort();
                    a9.k = ((DataInputStream)object).readByte();
                    a9.i = ((DataInputStream)object).readShort();
                    a9.e = ((DataInputStream)object).readBoolean() ? 1 : 0;
                    a9.f = ((DataInputStream)object).readBoolean() ? 1 : 0;
                    int n17 = ((DataInputStream)object).readShort();
                    if (n17 > 0) {
                        a9.p = new b[n17];
                        for (int i5 = 0; i5 < n17; ++i5) {
                            a9.p[i5] = b.f.b.a((DataInputStream)object, a9);
                        }
                    }
                    object2 = a9;
                    break;
                }
                case 12: {
                    Object object5;
                    byte by;
                    n2 = ((DataInputStream)object).readByte();
                    byte by5 = by = ((DataInputStream)object).readByte();
                    int n18 = n2;
                    object = new a(12);
                    new a(12).k = (byte)n18;
                    ((b)object).l = by5;
                    object2 = object5 = object;
                    break;
                }
                case 14: {
                    a a10 = new a(14);
                    new a(14).g = ((DataInputStream)object).readShort();
                    a10.k = ((DataInputStream)object).readByte();
                    a10.h = ((DataInputStream)object).readShort();
                    a10.i = ((DataInputStream)object).readShort();
                    a10.l = ((DataInputStream)object).readByte();
                    a10.m = ((DataInputStream)object).readByte();
                    if (a10.m == 1) {
                        a10.e = ((DataInputStream)object).readShort();
                        a10.f = ((DataInputStream)object).readShort();
                    }
                    a10.r = ((DataInputStream)object).readUTF();
                    object2 = a10;
                    break;
                }
                case 15: {
                    a a11 = new a(15);
                    new a(15).g = ((DataInputStream)object).readShort();
                    a11.k = ((DataInputStream)object).readByte();
                    a11.h = ((DataInputStream)object).readShort();
                    a11.l = ((DataInputStream)object).readByte();
                    object2 = a11;
                    break;
                }
                case 16: {
                    byte by;
                    a a12 = new a(16);
                    a12.k = by = ((DataInputStream)object).readByte();
                    switch (by) {
                        case 0: {
                            a12.l = ((DataInputStream)object).readByte();
                            break;
                        }
                        case 1: {
                            a12.g = ((DataInputStream)object).readShort();
                            a12.d = a.f.a(a12.g);
                            a12.l = ((DataInputStream)object).readByte();
                        }
                    }
                    a12.m = ((DataInputStream)object).readByte();
                    a12.e = ((DataInputStream)object).readShort();
                    a12.p = b.f.b.b((DataInputStream)object, a12);
                    object2 = a12;
                    break;
                }
                case 17: {
                    a a13 = new a(17);
                    int n19 = ((DataInputStream)object).readShort();
                    if (n19 >= 0) {
                        a13.p = new b[n19];
                        for (int i6 = 0; i6 < n19; ++i6) {
                            a13.p[i6] = b.f.b.a((DataInputStream)object, a13);
                        }
                    }
                    object2 = a13;
                    break;
                }
                case 18: {
                    a a14 = new a(18);
                    int n20 = ((DataInputStream)object).readShort();
                    if (n20 >= 0) {
                        a14.p = new b[n20];
                        for (int i7 = 0; i7 < n20; ++i7) {
                            a14.p[i7] = b.f.b.a((DataInputStream)object, a14);
                        }
                    }
                    object2 = a14;
                    break;
                }
                case 19: {
                    a a15 = new a(19);
                    new a(19).p = b.f.b.b((DataInputStream)object, a15);
                    object2 = a15;
                    break;
                }
                case 20: {
                    a a16 = new a(20);
                    new a(20).p = b.f.b.b((DataInputStream)object, a16);
                    object2 = a16;
                    break;
                }
                case 24: {
                    object2 = object = new a(24);
                    break;
                }
                case 25: {
                    byte by;
                    a a17 = new a(25);
                    a17.k = by = ((DataInputStream)object).readByte();
                    int n21 = b.e.a.a((int)((DataInputStream)object).readByte());
                    if (n21 > 0) {
                        a17.p = new b[n21];
                        for (int i8 = 0; i8 < n21; ++i8) {
                            a17.p[i8] = b.f.b.a((DataInputStream)object, a17);
                        }
                    }
                    object2 = a17;
                    break;
                }
                case 26: {
                    a a18 = new a(26);
                    new a(26).r = ((DataInputStream)object).readUTF();
                    a18.k = ((DataInputStream)object).readByte();
                    object2 = a18;
                    break;
                }
                case 28: {
                    object2 = new a(28);
                    break;
                }
                case 27: {
                    a a19 = new a(27);
                    new a(27).p = b.f.b.b((DataInputStream)object, a19);
                    object2 = a19;
                    break;
                }
                case 31: {
                    a a20 = new a(31);
                    new a(31).g = ((DataInputStream)object).readShort();
                    a20.h = ((DataInputStream)object).readShort();
                    a20.i = ((DataInputStream)object).readShort();
                    a20.k = ((DataInputStream)object).readByte();
                    a20.l = ((DataInputStream)object).readByte();
                    a20.m = ((DataInputStream)object).readByte();
                    a20.j = ((DataInputStream)object).readByte();
                    a20.p = b.f.b.b((DataInputStream)object, a20);
                    object2 = a20;
                    break;
                }
                case 32: {
                    a a21 = new a(32);
                    new a(32).g = ((DataInputStream)object).readShort();
                    a21.h = ((DataInputStream)object).readShort();
                    a21.i = ((DataInputStream)object).readShort();
                    a21.k = ((DataInputStream)object).readByte();
                    a21.l = ((DataInputStream)object).readByte();
                    object2 = a21;
                    break;
                }
                case 34: {
                    a a22 = new a(34);
                    new a(34).k = ((DataInputStream)object).readByte();
                    a22.l = ((DataInputStream)object).readByte();
                    object2 = a22;
                    break;
                }
                case 35: {
                    a a23 = new a(35);
                    new a(35).k = ((DataInputStream)object).readByte();
                    a23.l = ((DataInputStream)object).readByte();
                    object2 = a23;
                    break;
                }
                case 36: {
                    a a24 = new a(36);
                    new a(36).g = ((DataInputStream)object).readShort();
                    a24.k = ((DataInputStream)object).readByte();
                    a24.h = ((DataInputStream)object).readShort();
                    object2 = a24;
                    break;
                }
                case 37: {
                    a a25 = new a(37);
                    new a(37).k = ((DataInputStream)object).readByte();
                    object2 = a25;
                    break;
                }
                case 38: {
                    a a26 = new a(38);
                    new a(38).g = ((DataInputStream)object).readShort();
                    a26.h = ((DataInputStream)object).readShort();
                    a26.k = ((DataInputStream)object).readByte();
                    object2 = a26;
                    break;
                }
                case 39: {
                    a a27 = new a(39);
                    new a(39).r = ((DataInputStream)object).readUTF();
                    a27.k = ((DataInputStream)object).readByte();
                    a27.l = ((DataInputStream)object).readByte();
                    a27.h = ((DataInputStream)object).readShort();
                    a27.i = ((DataInputStream)object).readShort();
                    a27.m = ((DataInputStream)object).readByte();
                    object2 = a27;
                    break;
                }
                case 40: {
                    a a28 = new a(40);
                    int n22 = ((DataInputStream)object).readShort();
                    if (n22 > 0) {
                        u = new int[n22];
                        for (int i9 = 0; i9 < n22; ++i9) {
                            b.f.b.u[i9] = ((DataInputStream)object).readShort();
                        }
                    }
                    object2 = a28;
                    break;
                }
                case 42: {
                    a a29 = new a(42);
                    new a(42).k = (byte)(((DataInputStream)object).readBoolean() ? 1 : 0);
                    object2 = a29;
                    break;
                }
                case 43: {
                    a a30 = new a(43);
                    new a(43).g = ((DataInputStream)object).readShort();
                    a30.h = ((DataInputStream)object).readShort();
                    a30.p = b.f.b.b((DataInputStream)object, a30);
                    object2 = a30;
                    break;
                }
                case 44: {
                    object2 = new a(44);
                    break;
                }
                case 45: {
                    object2 = new a(45);
                    break;
                }
                case 46: {
                    object2 = new a(46);
                    break;
                }
                case 47: {
                    object2 = new a(47);
                    break;
                }
                case 48: {
                    a a31 = new a(48);
                    new a(48).g = ((DataInputStream)object).readShort();
                    a31.r = ((DataInputStream)object).readUTF();
                    a31.s = ((DataInputStream)object).readUTF();
                    object2 = a31;
                    break;
                }
                case 50: {
                    object2 = object = new a(50);
                    break;
                }
                case 51: {
                    object2 = new a(51);
                    break;
                }
                case 52: {
                    object2 = new a(52);
                    break;
                }
                case 53: {
                    a a32 = new a(53);
                    new a(53).g = ((DataInputStream)object).readShort();
                    object2 = a32;
                    break;
                }
                case 54: {
                    a a33 = new a(54);
                    new a(54).k = ((DataInputStream)object).readByte();
                    a33.l = ((DataInputStream)object).readByte();
                    a33.m = ((DataInputStream)object).readByte();
                    a33.g = ((DataInputStream)object).readByte();
                    object2 = a33;
                    break;
                }
                case 55: {
                    a a34 = new a(55);
                    new a(55).k = ((DataInputStream)object).readByte();
                    a34.l = ((DataInputStream)object).readByte();
                    a34.e = ((DataInputStream)object).readInt();
                    object2 = a34;
                    break;
                }
                case 56: {
                    object2 = new a(56);
                    break;
                }
                case 57: {
                    a a35 = new a(57);
                    new a(57).k = ((DataInputStream)object).readByte();
                    a35.l = ((DataInputStream)object).readByte();
                    a35.m = ((DataInputStream)object).readByte();
                    object2 = a35;
                    break;
                }
                case 58: {
                    object2 = new a(58);
                    break;
                }
                case 60: {
                    a a36 = new a(60);
                    new a(60).k = ((DataInputStream)object).readByte();
                    a36.l = ((DataInputStream)object).readByte();
                    a36.m = ((DataInputStream)object).readByte();
                    object2 = a36;
                    break;
                }
                case 61: {
                    a a37 = new a(61);
                    new a(61).r = ((DataInputStream)object).readUTF();
                    a37.s = ((DataInputStream)object).readUTF();
                    a37.e = ((DataInputStream)object).readInt();
                    object2 = a37;
                    break;
                }
                case 62: {
                    a a38 = new a(62);
                    new a(62).r = ((DataInputStream)object).readUTF();
                    object2 = a38;
                    break;
                }
                case 63: {
                    a a39 = new a(63);
                    new a(63).k = ((DataInputStream)object).readByte();
                    a39.e = ((DataInputStream)object).readBoolean() ? 1 : 0;
                    object2 = a39;
                    break;
                }
                case 64: {
                    a a40 = new a(64);
                    new a(64).k = ((DataInputStream)object).readByte();
                    a40.l = ((DataInputStream)object).readByte();
                    a40.m = ((DataInputStream)object).readByte();
                    a40.g = ((DataInputStream)object).readByte();
                    a40.h = ((DataInputStream)object).readByte();
                    a40.i = ((DataInputStream)object).readByte();
                    a40.j = ((DataInputStream)object).readByte();
                    object2 = a40;
                    break;
                }
                case 65: {
                    a a41 = new a(65);
                    new a(65).k = ((DataInputStream)object).readByte();
                    a41.l = ((DataInputStream)object).readByte();
                    object2 = a41;
                    break;
                }
                case 66: {
                    a a42 = new a(66);
                    new a(66).p = b.f.b.b((DataInputStream)object, a42);
                    object2 = a42;
                    break;
                }
                case 67: {
                    a a43 = new a(67);
                    new a(67).g = ((DataInputStream)object).readShort();
                    object2 = a43;
                    break;
                }
                case 68: {
                    object2 = new a(68);
                    break;
                }
                case 69: {
                    a a44 = new a(69);
                    new a(69).g = ((DataInputStream)object).readShort();
                    a44.h = ((DataInputStream)object).readShort();
                    a44.i = ((DataInputStream)object).readShort();
                    a44.j = ((DataInputStream)object).readShort();
                    object2 = a44;
                    break;
                }
                case 70: {
                    object2 = new a(70);
                    break;
                }
                default: {
                    return null;
                }
            }
            ((b)object2).t = b2;
            return object2;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return null;
        }
    }

    public static b a(f.a a2, g g2, g g3, g[] gArray) {
        a a3 = new a(41);
        new a(41).v = a2;
        a3.d = g2;
        a3.w = g3;
        a3.x = gArray;
        return a3;
    }

    public static b a(String string, short s, short s2, int n2) {
        a a2 = new a(39);
        new a(39).r = string;
        a2.k = 0;
        a2.l = (byte)n2;
        a2.h = s;
        a2.i = s2;
        a2.m = 1;
        return a2;
    }

    public static b a(b b2) {
        a a2 = new a(28);
        new a(28).t = b2;
        return a2;
    }

    public static b a(String string, byte by) {
        a a2 = new a(26);
        new a(26).r = string;
        a2.k = 0;
        return a2;
    }

    public static b a(b[][] bArray) {
        a a2 = new a(17);
        if (bArray != null) {
            a2.p = new b[bArray.length];
            for (int i2 = 0; i2 < bArray.length; ++i2) {
                a a3 = new a(18);
                new a(18).p = bArray[i2];
                a2.p[i2] = a3;
            }
        }
        return a2;
    }

    public static b a(g g2, byte by, byte by2, int n2, b[] bArray) {
        a a2 = new a(16);
        new a(16).d = g2;
        a2.k = 1;
        a2.l = (byte)2;
        a2.m = by2;
        a2.e = n2;
        a2.p = null;
        return a2;
    }

    public static b a(b[] bArray) {
        a a2 = new a(27);
        new a(27).p = bArray;
        for (int i2 = 0; i2 < bArray.length; ++i2) {
            if (bArray[i2] == null) continue;
            bArray[i2].t = a2;
        }
        return a2;
    }

    public static b a(a.b b2, byte by, short s, byte by2) {
        a a2 = new a(15);
        new a(15).d = b2;
        a2.k = by;
        a2.h = s;
        a2.l = by2;
        return a2;
    }

    public static b a(a.b b2, int n2, int n3, int n4, b[] bArray, boolean bl) {
        boolean bl2 = false;
        bArray = null;
        a.b b3 = b2;
        a a2 = new a(10);
        new a(10).d = b3;
        a2.h = (short)n2;
        a2.k = (byte)n3;
        a2.i = (short)n4;
        a2.p = bArray;
        a2.e = bl ? 1 : 0;
        a2.f = 0;
        return a2;
    }

    public final boolean b() {
        return this.y == 1;
    }

    public final void c() {
        this.y = 1;
    }

    public final void d() {
        this.y = 2;
        this.q = 0;
        if (this.p != null) {
            for (int i2 = 0; i2 < this.p.length; ++i2) {
                this.p[i2].d();
            }
        }
    }

    private static b[] b(DataInputStream dataInputStream, b b2) {
        try {
            int n2 = dataInputStream.readShort();
            if (n2 <= 0) {
                return null;
            }
            b[] bArray = new b[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                bArray[i2] = b.f.b.a(dataInputStream, b2);
            }
            return bArray;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return null;
        }
    }

    public static b[] a(byte[] byArray, a.b b2, short s) {
        if (byArray == null || byArray.length == 0 || b2 == null) {
            return null;
        }
        Vector<b> vector = new Vector<b>();
        Object var4_4 = null;
        boolean bl = false;
        byte by = 0;
        for (int i2 = 0; i2 < byArray.length; ++i2) {
            by = byArray[i2];
            short s2 = 1;
            while (i2 + 1 < byArray.length && by == byArray[i2 + 1]) {
                s2 = (short)(s2 + 1);
                ++i2;
            }
            b b3 = b.f.b.a(b2.n, by, s2, by, s);
            vector.addElement(b3);
        }
        if (vector.isEmpty()) {
            return null;
        }
        Object[] objectArray = new b[vector.size()];
        vector.copyInto(objectArray);
        return objectArray;
    }

    public final byte[] e() {
        try {
            switch (this.c) {
                case 66: {
                    return this.g();
                }
                case 1: {
                    return this.f();
                }
            }
            return null;
        }
        catch (Exception exception) {
            return null;
        }
    }

    private byte[] f() {
        Object object;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeByte(1);
            dataOutputStream.writeUTF(((b)object).r == null ? "" : ((b)object).r);
            dataOutputStream.writeByte(((b)object).g);
            if (((b)object).g == 1) {
                dataOutputStream.writeUTF(((b)object).s == null ? "" : ((b)object).s);
            } else {
                dataOutputStream.writeByte(((b)object).h);
            }
            dataOutputStream.writeByte(((b)object).k);
            dataOutputStream.writeByte(((b)object).l);
            dataOutputStream.writeByte(((b)object).m);
            object = byteArrayOutputStream.toByteArray();
            return object;
        }
        catch (Exception exception) {
            object = exception;
            throw exception;
        }
        finally {
            b.e.a.a(byteArrayOutputStream);
            b.e.a.a(dataOutputStream);
        }
    }

    private byte[] g() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeByte(66);
            if (this.p != null) {
                dataOutputStream.writeShort(this.p.length);
                for (int i2 = 0; i2 < this.p.length; ++i2) {
                    ((OutputStream)dataOutputStream).write(this.p[i2].e());
                }
            } else {
                dataOutputStream.writeShort(0);
            }
            byte[] byArray = byteArrayOutputStream.toByteArray();
            return byArray;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            throw exception;
        }
        finally {
            b.e.a.a(byteArrayOutputStream);
            b.e.a.a(dataOutputStream);
        }
    }
}

