/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package b;

import b.j;
import d.f;
import e.a;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import main.b;
import main.d;

public final class q
extends j {
    public static Image a = null;
    public int b;
    public int c;
    private int e;
    private int f;
    private int g;
    private int h;
    public int d;
    private int r;
    private int s;
    private Image[] t;
    private int[][] u;
    private int[][] v;
    private String[] w;
    private int x;
    private f[] y;
    private long z;
    private Image[][] A;
    private byte[][] B;
    private byte[][] C;
    private long[] D;
    private int E;
    private boolean F;
    private int G;
    private int H;
    private Image I;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public q(int n2) {
        block10: {
            q q2;
            block9: {
                DataInputStream dataInputStream;
                InputStream inputStream;
                block8: {
                    block7: {
                        super((byte)2);
                        this.b = 0;
                        this.c = 0;
                        this.e = 0;
                        this.f = 0;
                        this.g = -999;
                        this.h = -999;
                        this.d = -1;
                        this.r = -1;
                        this.s = -1;
                        this.t = null;
                        this.u = null;
                        this.v = null;
                        this.w = null;
                        this.x = 1;
                        this.y = null;
                        this.z = 0L;
                        this.A = null;
                        this.B = null;
                        this.C = null;
                        this.D = null;
                        this.E = 0;
                        this.F = true;
                        this.G = 0;
                        this.H = 0;
                        this.I = null;
                        this.d = n2;
                        q2 = this;
                        inputStream = null;
                        dataInputStream = null;
                        try {
                            inputStream = "".getClass().getResourceAsStream("/map/" + q2.d + ".m");
                            if (inputStream == null) {
                                h.b.a(inputStream);
                                break block7;
                            }
                            dataInputStream = new DataInputStream(inputStream);
                            int n3 = dataInputStream.readInt();
                            dataInputStream.readInt();
                            dataInputStream.skip(n3);
                            q2.b(dataInputStream);
                            q2.y = b.q.a(dataInputStream);
                            h.b.a(inputStream);
                            break block8;
                        }
                        catch (Exception exception) {
                            Exception exception2 = exception;
                            exception.printStackTrace();
                            break block9;
                        }
                    }
                    h.b.a(null);
                    break block10;
                }
                h.b.a(dataInputStream);
                break block9;
                finally {
                    h.b.a(inputStream);
                    h.b.a(dataInputStream);
                }
            }
            q2.h();
        }
        this.n = main.b.c;
        this.o = main.b.d;
        this.p = (byte)3;
        this.q = (byte)5;
    }

    private static f[] a(DataInputStream dataInputStream) {
        if (dataInputStream.available() == 0) {
            return null;
        }
        int n2 = dataInputStream.readShort();
        if (n2 <= 0) {
            return null;
        }
        f[] fArray = new f[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            fArray[i2] = new f();
            fArray[i2].a(dataInputStream);
        }
        return fArray;
    }

    public final boolean b(int n2, int n3) {
        if ((n2 /= 16) < 0 || (n3 /= 16) < 0 || n3 >= this.r || n2 >= this.s) {
            return false;
        }
        f f2 = this.c(n2, n3);
        if (f2 != null && !f2.s()) {
            return false;
        }
        if ((n2 = this.C[n3][n2]) < 0) {
            n2 += 256;
        }
        if (n2 == 255) {
            return false;
        }
        n3 = n2 / 64;
        n2 %= 64;
        if (n3 < 0) {
            return false;
        }
        return (this.D[n3] & 1L << n2) == 0L;
    }

    public final f c(int n2, int n3) {
        if (this.y == null || this.y.length <= 0) {
            return null;
        }
        f f2 = null;
        for (int i2 = 0; i2 < this.y.length; ++i2) {
            f2 = this.y[i2];
            if (f2.i() != n2 || f2.j() != n3) continue;
            return f2;
        }
        return null;
    }

    public final d a(int n2, int n3, byte by) {
        if ((this = ((q)this).c(n2, n3)) != null && ((f)this).q() == 2 && ((f)this).t()) {
            short s2 = ((d.b)this).f;
            d d2 = d.a.a(d.a.y.d, s2);
            return d2;
        }
        return null;
    }

    public final int c() {
        return this.s << 4;
    }

    public final int f() {
        return this.r << 4;
    }

    public final void a() {
        int n2;
        if (System.currentTimeMillis() - this.z > 1000L) {
            this.z = System.currentTimeMillis();
        }
        if (this.y != null && this.y.length > 0) {
            for (n2 = 0; n2 < this.y.length; ++n2) {
                this.y[n2].c();
                this.y[n2].a();
            }
        }
        for (n2 = 0; n2 < d.a.C.length; ++n2) {
            d.a.C[n2].c();
            d.a.C[n2].a();
        }
    }

    public final f[] g() {
        return this.y;
    }

    public final d.b a(short s2) {
        if (d.a.C[0].f == s2) {
            return d.a.C[0];
        }
        return this.b(s2);
    }

    public final f b(short s2) {
        if (this.y == null) {
            return null;
        }
        for (int i2 = 0; i2 < this.y.length; ++i2) {
            if (this.y[i2].f != s2) continue;
            return this.y[i2];
        }
        return null;
    }

    private void b(DataInputStream dataInputStream) {
        int n2;
        int n3;
        this.x = dataInputStream.readByte();
        this.s = h.b.a((int)dataInputStream.readByte()) + 1;
        this.r = h.b.a((int)dataInputStream.readByte()) + 1;
        dataInputStream.readByte();
        dataInputStream.readByte();
        int n4 = h.b.a((int)dataInputStream.readByte());
        this.E = h.b.a((int)dataInputStream.readByte());
        this.w = new String[n4];
        this.v = new int[n4][2];
        for (n3 = 0; n3 < n4; ++n3) {
            this.v[n3][0] = h.b.a((int)dataInputStream.readByte());
            this.v[n3][1] = h.b.a((int)dataInputStream.readByte());
            this.w[n3] = dataInputStream.readUTF();
        }
        byte by = dataInputStream.readByte();
        n3 = by;
        if (by > 0) {
            this.A = new Image[n3][];
            this.B = new byte[n3][3];
            for (n4 = 0; n4 < n3; ++n4) {
                n2 = dataInputStream.readByte();
                byte by2 = dataInputStream.readByte();
                this.B[n4][0] = 0;
                this.B[n4][1] = by2;
                this.B[n4][2] = n2;
                this.A[n4] = new Image[by2];
            }
        }
        this.C = new byte[this.r][this.s];
        for (n4 = 0; n4 < this.r; ++n4) {
            for (n2 = 0; n2 < this.s; ++n2) {
                this.C[n4][n2] = dataInputStream.readByte();
            }
        }
        this.D = new long[4];
        for (n4 = 0; n4 < this.D.length; ++n4) {
            this.D[n4] = dataInputStream.readLong();
        }
    }

    public final void h() {
        if (this.d == 2) {
            a = h.b.a("/hill.png");
        }
        if (this.d == 0) {
            a = h.b.a("/4.png");
        }
        this.F = true;
        if (this.d != 0 && this.d != 2) {
            this.I = Image.createImage((int)main.b.c, (int)main.b.d);
        }
        if (this.w != null && this.v != null && this.w.length == this.v.length) {
            int n2;
            int n3;
            this.t = new Image[this.w.length];
            this.u = new int[this.E][3];
            int n4 = 0;
            a a2 = null;
            for (n3 = 0; n3 <= 5 && n4 == 0; ++n3) {
                n4 = 1;
                a2 = e.a.a("/mi" + n3 + ".pak");
                for (n2 = 0; n2 < this.w.length; ++n2) {
                    if (this.t[n2] != null) continue;
                    this.t[n2] = a2.c(this.w[n2] + ".png");
                    if (this.t[n2] != null) continue;
                    n4 = 0;
                }
            }
            n2 = 0;
            n4 = 0;
            int n5 = 0;
            for (int i2 = 0; i2 < this.w.length; ++i2) {
                if (this.t[i2] == null) continue;
                n2 = this.t[i2].getWidth() / this.v[i2][0];
                n3 = this.t[i2].getHeight() / this.v[i2][1];
                n5 = n3 * n2;
                for (n3 = 0; n3 < n5; ++n3) {
                    this.u[n4][0] = i2;
                    this.u[n4][1] = this.v[i2][0] * (n3 % n2);
                    this.u[n4][2] = this.v[i2][1] * (n3 / n2);
                    ++n4;
                }
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4;
        int n5;
        int n6;
        int n7;
        if (this.d == 2 || this.d == 0) {
            if (a != null) {
                graphics.drawImage(a, 0, 0, 20);
            } else if (this.d == 2) {
                graphics.setColor(0x2F82F8);
                graphics.fillRect(0, 0, main.b.c, main.b.d);
            }
            n2 = this.b / 16;
            n3 = this.c / 16;
            int n8 = 0;
            int n9 = n3;
            int[] nArray = null;
            n3 = 0;
            int n10 = 0;
            Image image = null;
            int n11 = 0;
            int n12 = 0;
            int n13 = 0;
            int n14 = 0;
            int n15 = 0;
            int n16 = 0;
            for (int i2 = 0; i2 < this.x; ++i2) {
                int n17 = 0;
                while (n17 < main.b.d + 64) {
                    n8 = n2;
                    int n18 = 0;
                    while (n18 < main.b.c + 16) {
                        if (n9 >= 0 && n9 < this.r && n8 >= 0 && n8 < this.s) {
                            n13 = this.C[n9][n8];
                            if (n13 < 0) {
                                n13 += 256;
                            }
                            if (n13 >= 0 && n13 != 255 && n13 < this.u.length && (n3 = (nArray = this.u[n13])[0]) >= 0 && n3 < this.t.length && (image = this.t[n3]) != null) {
                                n11 = (n8 << 4) - this.b;
                                n12 = (n9 << 4) - this.c + 16;
                                if (n12 <= main.b.d + 16 || n12 + image.getHeight() >= main.b.d) {
                                    n16 = this.v[n3][0];
                                    n15 = n13;
                                    while (n8 + 1 < this.s) {
                                        n14 = this.C[n9][n8 + 1];
                                        if (n14 < 0) {
                                            n14 += 256;
                                        }
                                        if (n14 == 255 || n14 < 0 || n14 >= this.u.length || n14 != n15 + 1) break;
                                        n15 = n14;
                                        n10 = this.u[n14][0];
                                        if (n10 != n3 || nArray[1] + n16 + this.v[n3][0] > image.getWidth()) break;
                                        n16 += this.v[n3][0];
                                        ++n8;
                                        n18 += 16;
                                    }
                                    ++main.b.b;
                                    h.b.a(graphics, image, nArray[1], nArray[2], n16, this.v[n3][1], n11, n12, 36);
                                }
                            }
                        }
                        n18 += 16;
                        ++n8;
                    }
                    if (this.y != null) {
                        b.q.a(graphics, n9, this.y, 0, this.y.length);
                    }
                    if (d.a.k) {
                        b.q.a(graphics, n9, d.a.C, 0, d.a.C.length);
                    } else {
                        b.q.a(graphics, n9, d.a.C, 0, 1);
                    }
                    n17 += 16;
                    ++n9;
                }
            }
            return;
        }
        n2 = this.I.getWidth();
        n3 = this.I.getHeight();
        int n19 = this.b - this.g;
        int n20 = this.c - this.h;
        if (this.F || Math.abs(n19) >= n2 || Math.abs(n20) >= n3) {
            Graphics graphics2 = this.I.getGraphics();
            if (a == null && this.d == 2) {
                graphics2.setColor(3182840);
            } else {
                graphics2.setColor(0);
            }
            graphics2.fillRect(0, 0, n2, n3);
            this.a(graphics2, this.b, this.c, n2, n3, 0, 0);
            this.G = 0;
            this.H = 0;
            graphics.drawImage(this.I, 0, 0, 20);
            this.F = false;
        } else {
            n7 = n3;
            n6 = n2;
            n5 = n20;
            int n21 = n19;
            q q2 = this;
            n4 = q2.b;
            int n22 = q2.c;
            int n23 = 0;
            if (n21 > 0 && q2.G + n21 > n6) {
                n23 = n6 - q2.G;
                q2.G = n6;
                q2.b -= n21 - n23;
                n5 = q2.c(n5, n7, n23);
                n21 -= n23;
                q2.G = 0;
            } else if (n21 < 0 && q2.G + n21 < 0) {
                n23 = -q2.G;
                q2.G = 0;
                q2.b -= n21 - n23;
                n5 = q2.c(n5, n7, n23);
                n21 -= n23;
                q2.G = n6;
            } else {
                q2.G += n21;
                n5 = q2.c(n5, n7, n21);
                n21 = 0;
            }
            q2.G += n21;
            q2.H += n5;
            q2.b = n4;
            q2.c = n22;
            q2.a(q2.I, q2.G, q2.H, n21, n5, q2.b, q2.c);
            h.b.a(graphics, this.I, 0, 0, this.G, this.H, main.b.c, main.b.d, 40);
            h.b.a(graphics, this.I, this.G, 0, main.b.c - this.G, this.H, 0, main.b.d, 36);
            h.b.a(graphics, this.I, 0, this.H, this.G, main.b.d - this.H, main.b.c, 0, 24);
            h.b.a(graphics, this.I, this.G, this.H, main.b.c - this.G, main.b.d - this.H, 0, 0, 20);
        }
        this.g = this.b;
        this.h = this.c;
        n6 = this.c;
        n5 = this.b;
        Graphics graphics3 = graphics;
        q q3 = this;
        n7 = n6 / 16 - 1;
        for (n4 = 0; n4 < main.b.d + 48; n4 += 16) {
            ++n7;
            if (q3.y != null) {
                q3.a(graphics3, q3.y, n7, 0, q3.y.length, n5, n6);
            }
            if (d.a.k) {
                q3.a(graphics3, d.a.C, n7, 0, d.a.C.length, n5, n6);
                continue;
            }
            q3.a(graphics3, d.a.C, n7, 0, 1, n5, n6);
        }
    }

    private int c(int n2, int n3, int n4) {
        int n5 = 0;
        if (n2 > 0 && this.H + n2 > n3) {
            n5 = n3 - this.H;
            this.H = n3;
            this.c -= n2 - n5;
            this.a(this.I, this.G, this.H, n4, n5, this.b, this.c);
            this.H = 0;
        } else if (n2 < 0 && this.H + n2 < 0) {
            n5 = -this.H;
            this.H = 0;
            this.c -= n2 - n5;
            this.a(this.I, this.G, this.H, n4, n5, this.b, this.c);
            this.H = n3;
        } else {
            n5 = n2;
            this.H += n5;
            this.a(this.I, this.G, this.H, n4, n5, this.b, this.c);
        }
        return n2 - n5;
    }

    private void a(Image image, int n2, int n3, int n4, int n5, int n6, int n7) {
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        int n12 = 0;
        int n13 = 0;
        int n14 = image.getWidth();
        int n15 = image.getHeight();
        image = image.getGraphics();
        int n16 = 0;
        if (this.d == 2 && a == null) {
            n16 = 3182840;
        }
        if (n4 != 0) {
            if (n4 > 0) {
                image.setColor(n16);
                image.fillRect(n2 - n4, 0, n4, n15);
                n8 = n6 + n14 - n4;
                n9 = n7;
                n10 = n4;
                n11 = n15 - n3;
                n12 = n2 - n4;
                n13 = n3;
                this.a((Graphics)image, n8, n9, n10, n11, n12, n13);
                n9 = n7 + n15 - n3;
                n11 = n3;
                this.a((Graphics)image, n8, n9, n10, n11, n12, 0);
            } else {
                image.setColor(n16);
                image.fillRect(n2, 0, -n4, n15);
                n8 = n6;
                n9 = n7;
                n10 = -n4;
                n11 = n15 - n3;
                n12 = n2;
                n13 = n3;
                this.a((Graphics)image, n8, n9, n10, n11, n12, n13);
                n9 = n7 + n15 - n3;
                n11 = n3;
                this.a((Graphics)image, n8, n9, n10, n11, n12, 0);
            }
        }
        if (n5 != 0) {
            if (n5 > 0) {
                image.setColor(n16);
                image.fillRect(0, n3 - n5, n14, n5);
                n8 = n6;
                n9 = n7 + n15 - n5;
                n10 = n14 - n2;
                n11 = n5;
                n12 = n2;
                n13 = n3 - n5;
                this.a((Graphics)image, n8, n9, n10, n11, n12, n13);
                n8 = n6 + n14 - n2;
                n10 = n2;
                this.a((Graphics)image, n8, n9, n10, n11, 0, n13);
                return;
            }
            image.setColor(n16);
            image.fillRect(0, n3, n14, -n5);
            n8 = n6;
            n9 = n7;
            n10 = n14 - n2;
            n11 = -n5;
            n12 = n2;
            n13 = n3;
            this.a((Graphics)image, n8, n9, n10, n11, n12, n13);
            n8 = n6 + n10;
            n10 = n2;
            this.a((Graphics)image, n8, n9, n10, n11, 0, n13);
        }
    }

    private void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7) {
        if (n4 == 0 || n5 == 0) {
            return;
        }
        int[] nArray = h.b.a(graphics);
        graphics.clipRect(n6, n7, n4, n5);
        int n8 = n2 / 16;
        int n9 = n3 / 16;
        boolean bl = false;
        boolean bl2 = false;
        Object var11_12 = null;
        Object var12_17 = null;
        boolean bl3 = false;
        int n10 = 0;
        int n11 = 0;
        int n12 = 0;
        n4 = n2 + n4;
        n5 = n3 + n5 + 80;
        n9 <<= 4;
        while (n9 < n5) {
            for (int i2 = n8 << 4; i2 < n4; i2 += 16) {
                Image image;
                int n13 = i2 / 16;
                int n14 = n9 / 16;
                if (n13 < 0 || n13 >= this.s || n14 < 0 || n14 >= this.r || (n13 = (n13 = this.C[n14][n13]) < 0 ? n13 + 256 : n13) == 255 || n13 < 0 || n13 >= this.u.length) continue;
                n11 = i2 - n2 + n6;
                n12 = n9 - n3 + n7 + 16;
                int[] nArray2 = this.u[n13];
                n10 = nArray2[0];
                if (n10 < 0 || n10 >= this.t.length || (image = this.t[n10]) == null || n12 - this.v[n10][1] >= main.b.d) continue;
                ++main.b.b;
                h.b.a(graphics, image, nArray2[1], nArray2[2], this.v[n10][0], this.v[n10][1], n11, n12, 36);
            }
            n9 += 16;
        }
        h.b.a(graphics, nArray);
    }

    private void a(Graphics graphics, d.b[] bArray, int n2, int n3, int n4, int n5, int n6) {
        if (bArray == null) {
            return;
        }
        n3 = 0;
        int n7 = 0;
        int n8 = 0;
        n7 = 0;
        n8 = 0;
        for (n4 = n4 + 0 - 1; n4 >= 0; --n4) {
            n3 = n4;
            if (n3 >= bArray.length) {
                return;
            }
            if (bArray[n3] == null || !bArray[n3].k() || bArray[n3].j() != n2) continue;
            n7 = bArray[n4].l() - n5 + 8;
            n8 = bArray[n4].m() - n6 + 16;
            bArray[n3].a(graphics, n7, n8);
            n7 = bArray[n3].i();
            n8 = bArray[n3].j();
            if (bArray[n3].m() % 16 != 0) {
                ++n8;
            }
            this.a(graphics, n7 - 1, n8, n5, n6);
            this.a(graphics, n7, n8, n5, n6);
            this.a(graphics, n7 + 1, n8, n5, n6);
        }
    }

    private void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        Object var6_6 = null;
        int[] nArray = null;
        boolean bl = false;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        for (int i2 = 1; i2 < 5; ++i2) {
            Image image;
            int n9;
            if (n2 < 0 || n2 >= this.s || n3 + i2 < 0 || n3 + i2 >= this.r || (n9 = (n9 = this.C[n3 + i2][n2]) < 0 ? n9 + 256 : n9) == 255 || n9 < 0 || n9 >= this.u.length || (n6 = (nArray = this.u[n9])[0]) < 0 || n6 >= this.t.length || (image = this.t[n6]) == null) continue;
            n7 = (n2 << 4) - n4;
            n8 = (n3 + i2 << 4) - n5 + 16;
            if (this.v[n6][1] <= i2 << 4 || n8 - this.v[n6][1] >= main.b.d) continue;
            ++main.b.b;
            h.b.a(graphics, image, nArray[1], nArray[2], this.v[n6][0], this.v[n6][1], n7, n8, 36);
            this.a(graphics, n2, n3 + i2, n4, n5);
        }
    }

    public final void a(d.b j2) {
        if (j2 == null) {
            return;
        }
        this.e = j2.l() + 8 - main.b.e;
        this.f = j2.m() - 8 - main.b.f;
        if (this.e < 0) {
            this.e = 0;
        }
        if (this.f < 0) {
            this.f = 0;
        }
        j2 = d.a.y;
        if (this.e + main.b.c > ((q)j2).s << 4) {
            j2 = d.a.y;
            this.e = (((q)j2).s << 4) - main.b.c;
        }
        j2 = d.a.y;
        if (this.f + main.b.d > ((q)j2).r << 4) {
            j2 = d.a.y;
            this.f = (((q)j2).r << 4) - main.b.d;
        }
        this.b = this.e;
        this.c = this.f;
    }

    public final void a(int n2, int n3) {
        d.b b2;
        switch (n2) {
            case -6: {
                if (this.i != null) {
                    this.i.b(this);
                }
                return;
            }
            case -7: {
                if (this.i != null) {
                    this.i.c(this);
                }
                return;
            }
        }
        d.a.C[0].a(n2, n3);
        if (d.a.w != 2 && (b2 = d.a.y.a(d.a.x)) != null) {
            this.a(b2);
        }
    }

    public final void d(int n2, int n3) {
        int[] nArray = h.b.a(this.b, this.c, n2, n3);
        this.b = nArray[0];
        this.c = nArray[1];
    }

    public final void a(int n2, int n3, int n4) {
        if (n2 != this.b) {
            if (n2 > this.b) {
                this.b += Math.abs(n4);
                if (this.b > n2) {
                    this.b = n2;
                }
            } else {
                this.b -= Math.abs(n4);
                if (this.b < n2) {
                    this.b = n2;
                }
            }
        }
        if (n3 != this.c) {
            if (n3 > this.c) {
                this.c += Math.abs(n4);
                if (this.c > n3) {
                    this.c = n3;
                    return;
                }
            } else {
                this.c -= Math.abs(n4);
                if (this.c < n3) {
                    this.c = n3;
                }
            }
        }
    }

    public final byte[] a(int n2, int n3, int n4, int n5) {
        if (n2 == n4 && n3 == n5) {
            return null;
        }
        Vector vector = new Vector();
        Vector<int[]> vector2 = new Vector<int[]>();
        Object object = new int[]{n2, n3, h.b.a(n2, n4, n5), 4};
        vector2.addElement((int[])object);
        do {
            Vector<int[]> vector3 = vector2;
            Vector vector4 = vector;
            int n6 = n5;
            int n7 = n4;
            int n8 = n3;
            int n9 = n2;
            int n10 = object[1];
            int n11 = object[0];
            object = this;
            int n12 = n11;
            int n13 = n10 - 1;
            super.a(n12, n13, n9, n8, n7, n6, vector4, vector3, (byte)2);
            n12 = n11;
            n13 = n10 + 1;
            super.a(n12, n13, n9, n8, n7, n6, vector4, vector3, (byte)3);
            n12 = n11 - 1;
            n13 = n10;
            super.a(n12, n13, n9, n8, n7, n6, vector4, vector3, (byte)0);
            n12 = n11 + 1;
            n13 = n10;
            super.a(n12, n13, n9, n8, n7, n6, vector4, vector3, (byte)1);
            int[] nArray = b.q.a(vector);
            object = nArray;
            if (nArray == null) {
                return null;
            }
            vector.removeElement(object);
            vector2.addElement((int[])object);
        } while (object[0] != n4 || object[1] != n5);
        return this.a(vector2, (int[])object, n2, n3);
    }

    private void a(int n2, int n3, int n4, int n5, int n6, int n7, Vector vector, Vector vector2, byte by) {
        if (!((q)object).b(n2 << 4, n3 << 4)) {
            return;
        }
        Object object = null;
        int[] nArray = b.q.a(n2, n3, vector);
        object = nArray;
        if (nArray != null) {
            return;
        }
        int[] nArray2 = b.q.a(n2, n3, vector2);
        object = nArray2;
        if (nArray2 != null) {
            return;
        }
        int[] nArray3 = new int[4];
        object = nArray3;
        nArray3[0] = n2;
        object[1] = n3;
        object[2] = h.b.a(n2, n4, n5) + h.b.a(n2, n6, n7);
        object[3] = by;
        vector.addElement(object);
    }

    private static int[] a(Vector vector) {
        if (vector.size() == 0) {
            return null;
        }
        int[] nArray = (int[])vector.elementAt(0);
        int[] nArray2 = null;
        for (int i2 = 1; i2 < vector.size(); ++i2) {
            nArray2 = (int[])vector.elementAt(i2);
            if (nArray2[2] >= nArray[2]) continue;
            nArray = nArray2;
        }
        return nArray;
    }

    private byte[] a(Vector vector, int[] nArray, int n2, int n3) {
        if (nArray == null || nArray[0] == n2 && nArray[1] == n3) {
            return null;
        }
        Object object = b.q.b(nArray[0], nArray[1], nArray[3]);
        Vector<Integer> vector2 = new Vector<Integer>();
        vector2.addElement(new Integer(d.b.a(nArray[3])));
        nArray = null;
        while (object[0] != n2 || object[1] != n3) {
            nArray = b.q.a((int)object[0], (int)object[1], vector);
            if (nArray == null) {
                return null;
            }
            object = b.q.b(nArray[0], nArray[1], nArray[3]);
            vector2.addElement(new Integer(d.b.a(nArray[3])));
        }
        object = new byte[vector2.size()];
        for (int i2 = 0; i2 < ((Object)object).length; ++i2) {
            object[i2] = ((Integer)vector2.elementAt(((Object)object).length - 1 - i2)).byteValue();
        }
        return object;
    }

    private static int[] a(int n2, int n3, Vector vector) {
        int[] nArray = null;
        for (int i2 = 0; i2 < vector.size(); ++i2) {
            nArray = (int[])vector.elementAt(i2);
            if (nArray[0] != n2 || nArray[1] != n3) continue;
            return nArray;
        }
        return null;
    }

    public static int[] b(int n2, int n3, int n4) {
        switch (n4) {
            case 2: {
                return new int[]{n2, n3 + 1};
            }
            case 3: {
                return new int[]{n2, n3 - 1};
            }
            case 1: {
                return new int[]{n2 - 1, n3};
            }
            case 0: {
                return new int[]{n2 + 1, n3};
            }
        }
        return null;
    }

    public final void i() {
        this.t = null;
        this.I = null;
    }

    public final void j() {
        if (this.y == null) {
            return;
        }
        for (int i2 = 0; i2 < this.y.length; ++i2) {
            f f2 = this.y[i2];
            this.y[i2].c = null;
            f2.d = null;
        }
    }

    public final void k() {
        if (this.y == null) {
            return;
        }
        for (int i2 = 0; i2 < this.y.length; ++i2) {
            this.y[i2].a((a)null);
        }
    }

    public final void q() {
        if (this.y == null) {
            return;
        }
        for (int i2 = 0; i2 < this.y.length; ++i2) {
            this.y[i2].b(this.y[i2].e, -1);
        }
    }

    public final void r() {
        if (this.y == null) {
            return;
        }
        for (int i2 = 0; i2 < this.y.length; ++i2) {
            if (this.y[i2].r) {
                this.y[i2].b(0, -1);
                continue;
            }
            this.y[i2].a(this.y[i2].h(), false, true);
        }
    }

    public final void a(byte[] object) {
        if (object == null) {
            return;
        }
        object = new ByteArrayInputStream((byte[])object);
        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
        try {
            int n2 = h.b.a(dataInputStream.readByte());
            if (n2 < 0) {
                return;
            }
            for (int i2 = 0; i2 < n2; ++i2) {
                byte by = dataInputStream.readByte();
                f f2 = this.b(by);
                if (f2 == null) continue;
                f2.b(dataInputStream);
            }
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
        finally {
            h.b.a((InputStream)object);
            h.b.a(dataInputStream);
        }
    }

    public final byte[] s() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            int n2 = this.y == null ? 0 : this.y.length;
            dataOutputStream.writeByte(n2);
            if (n2 > 0) {
                f f2 = null;
                for (int i2 = 0; i2 < this.y.length; ++i2) {
                    f2 = this.y[i2];
                    dataOutputStream.writeByte(f2.f);
                    ((OutputStream)dataOutputStream).write(f2.r());
                }
            }
            byte[] byArray = byteArrayOutputStream.toByteArray();
            return byArray;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return null;
        }
        finally {
            h.b.a(byteArrayOutputStream);
            h.b.a(dataOutputStream);
        }
    }

    public final String d() {
        return null;
    }

    public final f e(int n2, int n3) {
        if (this.y == null) {
            return null;
        }
        int n4 = 0;
        int n5 = 0;
        for (int i2 = 0; i2 < this.y.length; ++i2) {
            n4 = Math.abs(this.y[i2].i() - n2);
            n5 = Math.abs(this.y[i2].j() - n3);
            if (n4 >= 2 || n5 >= 2 || n4 + n5 > 2) continue;
            return this.y[i2];
        }
        return null;
    }

    public final d a(f f2) {
        if (f2 == null) {
            return null;
        }
        return d.a.a(this.d, f2.f);
    }

    public final void e() {
    }

    public final boolean b(d.b b2) {
        if (b2 == null) {
            return false;
        }
        int n2 = b2.l();
        int n3 = b2.m();
        boolean bl = h.b.a(n2 - b2.n / 2, n3 - b2.o, b2.n, b2.o, this.b, this.c, main.b.c, main.b.d);
        return bl;
    }

    public final void b() {
    }

    private static void a(Graphics graphics, int n2, d.b[] bArray, int n3, int n4) {
        if (bArray == null) {
            return;
        }
        n3 = 0;
        int n5 = 0;
        int n6 = 0;
        for (n4 = n4 + 0 - 1; n4 >= 0; --n4) {
            n3 = n4;
            if (n3 >= bArray.length) {
                return;
            }
            if (bArray[n3] == null || !bArray[n3].k() || (bArray[n3].m() % 16 != 0 || bArray[n3].j() != n2) && (bArray[n3].m() + 16 <= n2 << 4 || bArray[n3].m() + 16 >= n2 + 1 << 4)) continue;
            n5 = bArray[n4].l() - d.a.y.b + 8;
            n6 = bArray[n4].m() - d.a.y.c + 16;
            bArray[n3].a(graphics, n5, n6);
        }
    }

    public final f.d a(String string) {
        if (string == null || this.y == null) {
            return null;
        }
        for (int i2 = 0; i2 < this.y.length; ++i2) {
            if (this.y[i2] == null || !this.y[i2].b.equals(string) || this.y[i2].c == null) continue;
            return this.y[i2].c;
        }
        return null;
    }
}

