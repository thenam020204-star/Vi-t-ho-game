/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
package src;

import javax.microedition.lcdui.Image;
import src.b;
import src.k;
import src.m;
import src.v;
import src.x;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class o
implements x {
    private b a;
    private byte b;
    byte a;
    private int a;
    private byte[] a;
    private byte c;
    private byte d;
    private String[] a;
    private boolean a;
    private byte e;
    private Image[] a;
    private short[] a;
    private short[][] a;
    private short[][] b;
    private byte f;
    private byte g;
    private boolean b;
    private byte[] b;
    private int[][] a;
    private int[] a;
    private int[] b;
    private byte h;
    private byte i;
    private byte j;
    private int[][] b;
    private int b = new byte[]{0, 1, 2, 3, 4};
    private int c;
    private int d;
    private byte k;
    private byte[] c;

    public o(b object) {
        String[][] stringArrayArray = new String[][]{{"\u7ee7\u7eed\u6e38\u620f", "Load Game"}, {"\u65b0\u7684\u6e38\u620f", "New Game"}, {"\u6e38\u620f\u8bbe\u7f6e", "Game Option"}, {"\u6e38\u620f\u5e2e\u52a9", "Game Help"}, {"\u6e38\u620f\u5173\u4e8e", "About"}, {"\u9000\u51fa\u6e38\u620f", "Quit Game"}, new String[0], new String[0], {"\u66f4\u591a\u6e38\u620f", "More Game"}, new String[0]};
        this.i = (byte)-2;
        byte[] byArray = new byte[]{0, 1, 2, 3, 4, 5};
        this.c = 1;
        this.d = 0;
        this.c = new byte[13];
        this.a = object;
        this.c();
        this.d();
        ((b)object).a.a();
        this.a = ((b)object).a.a();
        if (this.a == 0) {
            byte[] byArray2 = new byte[]{1, 8, 2, 3, 4, 5};
            this.a = byArray2;
            this.d = (byte)this.a.length;
            this.h = 0;
        } else {
            byte[] byArray3 = ((b)object).a(6);
            this.c = byArray3[0];
            ((b)object).a.e = byArray3[1];
            object = new byte[]{1, 0, 8, 2, 3, 4, 5};
            this.a = (byte[])object;
            this.d = (byte)this.a.length;
            this.h = 1;
        }
        object = this;
        this.b = new int[15][6];
    }

    private void c() {
        int n2;
        m.a = new byte[20000];
        m.a[0] = 0;
        m.a(m.a, "/data/menu.data", 0);
        this.a = m.a(m.a, m.a);
        this.a = m.a(m.a, m.a);
        this.b = m.a(m.a, m.a);
        this.a = new Image[this.b.length + 2];
        for (n2 = 0; n2 < this.b.length; ++n2) {
            this.a[n2] = m.a("/menu/" + this.b[n2] + ".png");
        }
        this.a[5] = m.b("/role/20.png");
        this.a[6] = m.a("/menu/5.png");
        this.a = new int[2][];
        this.a = new int[2];
        this.b = new int[2];
        this.a[0] = m.b(this.a[4]);
        this.a[0] = this.a[4].getWidth();
        this.b[0] = this.a[4].getHeight();
        n2 = 0;
        while (n2 < this.a[0].length) {
            int[] nArray = this.a[0];
            int n3 = n2++;
            nArray[n3] = nArray[n3] & 0xFFFFFF;
        }
        m.a = null;
    }

    private void a(int n2, int n3, int n4, int n5, int n6) {
        this.a.a(this.a[this.a[n2 * 5]], n3, n4, n5, 0, this.a, n2);
    }

    public final void a(short s2) {
        for (int i2 = 0; i2 < this.a[s2].length; i2 += 4) {
            short s3 = this.a[s2][i2];
            this.a.a(this.a[this.a[s3 * 5]], this.a[s2][i2 + 1], (int)this.a[s2][i2 + 2], 20, n[this.a[s2][i2 + 3]], 120, 160, this.a, (int)s3);
        }
    }

    private void b(byte by) {
        short s2 = this.b[by][this.f + 1];
        for (int i2 = 0; i2 < this.a[s2].length; i2 += 4) {
            short s3 = this.a[s2][i2];
            this.a.a(this.a[this.a[s3 * 5]], this.a[s2][i2 + 1], (int)this.a[s2][i2 + 2], 20, n[this.a[s2][i2 + 3]], 120, 160, this.a, (int)s3);
        }
        this.c(by);
    }

    private void a(byte by, int n2, int n3) {
        by = (byte)this.b[2][this.f + 1];
        for (n3 = 0; n3 < this.a[by].length; n3 += 4) {
            n2 = this.a[by][n3];
            this.a.a(this.a[this.a[n2 * 5]], this.a[by][n3 + 1], (int)this.a[by][n3 + 2], 20, n[this.a[by][n3 + 3]], 120, 265, this.a, n2);
        }
        this.c((byte)2);
    }

    private void c(byte by) {
        this.g = (byte)(this.g + 1);
        if (this.g == this.b[by][this.f]) {
            this.g = 0;
            this.f = (byte)(this.f + 2);
            if (this.f == this.b[by].length) {
                this.f = 0;
                this.b = true;
            }
        }
    }

    private void a(int n2, int n3) {
        this.a.a("$0\uff08\u534e\u5a31\uff09", 170, n3 + 80, 0, this.a.a);
    }

    public final void a() {
        this.a.b(0, 0, 0, 240, 320);
        this.d = 1;
        switch (this.b) {
            case 0: {
                int n2;
                o o2;
                o o3 = this;
                switch (o3.i) {
                    case -2: {
                        o3.a((short)0);
                        o2 = o3;
                        for (n2 = 0; n2 < o2.c.length; ++n2) {
                            o2.a.b(0, 0, 0 + (n2 * 12 << 1) + 12 - o2.c[n2], 240, o2.c[n2] << 1);
                        }
                        break;
                    }
                    case -1: {
                        o3.a((short)0);
                        break;
                    }
                    case 0: {
                        o3.a((short)0);
                        o3.a.a.drawRGB(o3.a[0], 0, o3.a[0], 1, 85, o3.a[0], o3.b[0], true);
                        break;
                    }
                    case 1: {
                        o3.a((short)1);
                        break;
                    }
                    case 2: {
                        o3.b((byte)0);
                        break;
                    }
                    case 3: {
                        o3.a((short)4);
                        o3.a((byte)2, 120, 265);
                        if (o3.f / 2 < 3) break;
                        o3.a(o3.a[o3.h], 120, 254, 17, 0);
                        o3.a(o3.a[o3.h] + 9, 120, 281, 17, 0);
                    }
                }
                o2 = o3;
                switch (o2.i) {
                    case -2: {
                        o o4;
                        o2.k = (byte)(o2.k + 1);
                        byte by = o4.k = o2.k > o2.c.length - 1 ? (byte)0 : o2.k;
                        if (o2.c[0] == 0) {
                            o2.i = (byte)-1;
                        }
                        for (int i2 = 0; i2 < o2.c.length; ++i2) {
                            int n3 = i2;
                            o2.c[n3] = (byte)(o2.c[n3] - 3);
                            o2.c[i2] = o2.c[i2] < 0 ? (byte)0 : o2.c[i2];
                        }
                        break;
                    }
                    case -1: {
                        o2.j = (byte)(o2.j + 1);
                        if (o2.j <= 1) break;
                        o2.j = 0;
                        o2.i = 0;
                        break;
                    }
                    case 0: {
                        for (n2 = 0; n2 < o2.a[0].length; ++n2) {
                            if (o2.a[0][n2] == 0 || o2.a[0][n2] == 0xFFFFFF) continue;
                            int[] nArray = o2.a[0];
                            int n4 = n2;
                            nArray[n4] = nArray[n4] + 0x22000000;
                        }
                        o2.j = (byte)(o2.j + 1);
                        if (o2.j <= 7) break;
                        o2.j = 0;
                        o2.i = 1;
                        break;
                    }
                    case 1: {
                        o2.j = (byte)(o2.j + 1);
                        if (o2.j <= 1) break;
                        o2.j = 0;
                        o2.i = (byte)2;
                        break;
                    }
                    case 2: {
                        if (!o2.b) break;
                        o2.j = 0;
                        o2.i = (byte)3;
                        o2.b = false;
                        break;
                    }
                    case 3: {
                        if (o2.f / 2 < 6) break;
                        o2.f = 0;
                        o2.b = false;
                        o2.a((byte)1);
                    }
                }
                if ((this.i != 2 || this.f < 8) && this.i <= 2) break;
                this.a(0, this.i == 2 && this.f >= 8 && this.f <= 16 ? (this.f / 2 % 2 == 1 ? this.d : -this.d) : -this.d);
                return;
            }
            case 1: {
                int n5;
                o o5 = this;
                o5.b((byte)1);
                o o6 = o5;
                o6.a(0, -o6.d);
                o6 = o5;
                for (n5 = 0; n5 < o6.b.length; ++n5) {
                    o6.a(47 + o6.b[n5][4], o6.b[n5][0], o6.b[n5][1], 3, 0);
                }
                o6 = o5;
                for (n5 = 0; n5 < o6.b.length; ++n5) {
                    if (o6.b[n5][1] <= o6.b[n5][5]) {
                        o6.b[n5][0] = (short)(m.a(340) - 50);
                        o6.b[n5][1] = (short)(320 + m.a(50));
                        o6.b[n5][2] = (short)m.b(5);
                        o6.b[n5][3] = -((short)(m.a(2) + 2));
                        o6.b[n5][4] = (short)m.a(2);
                        o6.b[n5][5] = (short)(320 - (m.a(200) + 130));
                        continue;
                    }
                    int[] nArray = o6.b[n5];
                    nArray[0] = nArray[0] + o6.b[n5][2];
                    int[] nArray2 = o6.b[n5];
                    nArray2[1] = nArray2[1] + o6.b[n5][3];
                }
                o5.a(o5.a[o5.h], 120, 254, 17, 0);
                o5.a(o5.a[o5.h] + 9, 120, 281, 17, 0);
                o5.a.a.a(178, 53 - o5.b * o5.c, 258, 24, 0);
                o5.a.a.a(178, 187 + o5.b * o5.c, 258, 20, 3);
                if (++o5.b == 2) {
                    o5.b = 0;
                    o5.c = -o5.c;
                }
                if (o5.a) {
                    o6 = o5;
                    for (n5 = 0; n5 < 320; ++n5) {
                        o6.a.a.drawRGB(o6.a.a, 0, 240, 0, n5 + 0, 240, 1, true);
                    }
                    o5.a.a.a((short)19);
                    n5 = 0;
                    if (o5.a.length > 1) {
                        n5 = (o5.a.length - 1) * (16 / o5.a.length);
                    }
                    o5.a.a(o5.a, 120, 110 - n5, 17, 0, o5.a.length, 0);
                }
                return;
            }
            case 2: {
                this.a.e();
                return;
            }
            case 3: {
                this.a.g();
                return;
            }
            case 4: {
                b b2 = this.a;
                b2.a.a((short)1);
                b2.a.j();
                b2.a.a((short)6);
                b2.a.a(122, v.a[0][0], 31, 36, 0);
                b2.a.a((short)28, 0, 320);
                b2.a(b2.a, b2.e > 12 ? 30 : 20, 50, 20, (int)b2.g, 12, 0);
                if (b2.e <= 12) break;
                b2.a.a((short)41);
                b2.a.b(7, 69, 8, 189, b2.g, b2.e, (byte)12);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void b() {
        switch (this.b) {
            case 0: {
                o o2 = this;
                if (!src.k.a(262176)) return;
                o2.a((byte)1);
                return;
            }
            case 1: {
                o o3 = this;
                if (src.k.a(16400)) {
                    if (o3.a) return;
                    o3.h = (byte)(o3.h - 1);
                    o3.h = (byte)(o3.h < 0 ? o3.d - 1 : o3.h);
                    return;
                }
                if (src.k.a(32832)) {
                    if (o3.a) return;
                    o3.h = (byte)(o3.h + 1);
                    o3.h = o3.h > o3.d - 1 ? (byte)0 : o3.h;
                    return;
                }
                if (src.k.a(327712)) {
                    if (o3.a) {
                        o3.a.a(10, 1, (byte)0);
                        o3.a.a.e = 0;
                        return;
                    }
                    switch (o3.a[o3.h]) {
                        case 0: {
                            switch (o3.c) {
                                case 5: {
                                    o3.a.a(5, 10, (byte)0);
                                    return;
                                }
                                case 4: {
                                    o3.a.a(4, 11, (byte)0);
                                }
                            }
                            return;
                        }
                        case 1: {
                            if (o3.a == 0) {
                                o3.a.a(10, 1, (byte)0);
                                o3.a.a.e = 0;
                                return;
                            } else {
                                o o4 = o3;
                                o3.a = true;
                                for (int i2 = 0; i2 < o4.a.a.length; ++i2) {
                                    o4.a.a[i2] = -939524096;
                                }
                                o3.e = 0;
                                o3.a = o3.a.a("\u91cd\u65b0\u5f00\u59cb\u6e38\u620f\u4f1a\u6e05\u9664\u5b58\u6863\uff0c\u662f\u5426\u91cd\u65b0\u5f00\u59cb\u6e38\u620f\uff1f", 200, 0);
                            }
                            return;
                        }
                        case 2: {
                            o3.a.a.i();
                            o3.a((byte)2);
                            return;
                        }
                        case 3: {
                            o3.a.a.i();
                            o3.a.e = 0;
                            o3.a.f = 0;
                            o3.a((byte)3);
                            return;
                        }
                        case 4: {
                            o3.a.a.i();
                            o3.a.i();
                            o3.a((byte)4);
                            return;
                        }
                        case 5: {
                            o3.a.k();
                            o3.a.a(52);
                            return;
                        }
                        case 8: {
                            o3.a.a("http://wap.ttsy.org/gamecms/go/jpgd?cpId=701057&contentID=000048675000");
                        }
                        default: {
                            return;
                        }
                    }
                }
                if (!src.k.a(131072) || !o3.a) return;
                o3.a = false;
                return;
            }
            case 2: {
                this.a.f();
                return;
            }
            case 3: {
                this.a.h();
                return;
            }
            case 4: {
                b b2 = this.a;
                if (src.k.a(4100) || src.k.a(4100, 10)) {
                    b2.g = (byte)(b2.g - 1);
                    b2.g = b2.g < 0 ? (byte)0 : b2.g;
                    return;
                }
                if (src.k.a(8448) || src.k.a(8448, 10)) {
                    byte by = (byte)(b2.a.length - 12);
                    by = by < 0 ? (byte)0 : by;
                    b2.g = (byte)(b2.g + 1);
                    b2.g = b2.g > by ? by : b2.g;
                    return;
                }
                if (src.k.a(65536)) {
                    b2.a.a(b2.a.a);
                    return;
                }
                if (!src.k.a(131072)) return;
                b2.a.a(b2.a.a);
            }
        }
    }

    public final void a(byte by) {
        this.a = this.b;
        this.b = by;
    }

    private void d() {
        this.k = 0;
        for (int i2 = 0; i2 < this.c.length; ++i2) {
            this.c[i2] = 12;
        }
    }
}

