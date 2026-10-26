/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package c;

import b.e;
import c.a;
import c.b;
import c.h;
import c.j;
import c.k;
import c.m;
import c.n;
import c.o;
import c.p;
import c.s;
import c.t;
import c.u;
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
import main.c;

public final class r
extends k
implements main.a {
    private t l;
    private s m;
    private Image n;
    private String o;
    private int p;
    private String q;
    public int a;
    public int b;
    private int r;
    private int s;
    public int c;
    private int t;
    private int u;
    private Image[] v;
    private int[][] w;
    private int[][] x;
    private String[] y;
    private int z;
    private e[] A;
    private long B;
    private Image[][] C;
    private byte[][] D;
    private byte[][] E;
    private long[] F;
    private int G;
    private int H;
    private int I;
    private boolean J;
    private int K;
    private int L;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public r(int n2) {
        block10: {
            r r2;
            block9: {
                DataInputStream dataInputStream;
                InputStream inputStream;
                block8: {
                    block7: {
                        super((byte)2);
                        this.l = null;
                        this.m = null;
                        this.n = null;
                        this.o = null;
                        this.p = 0;
                        this.q = "";
                        this.a = 0;
                        this.b = 0;
                        this.r = 0;
                        this.s = 0;
                        this.c = -1;
                        this.t = -1;
                        this.u = -1;
                        this.v = null;
                        this.w = null;
                        this.x = null;
                        this.y = null;
                        this.z = 1;
                        this.A = null;
                        this.B = 0L;
                        this.C = null;
                        this.D = null;
                        this.E = null;
                        this.F = null;
                        this.G = 0;
                        this.H = 0;
                        this.I = 0;
                        this.J = true;
                        this.K = 0;
                        this.L = 0;
                        this.c = n2;
                        r2 = this;
                        inputStream = null;
                        dataInputStream = null;
                        try {
                            inputStream = "".getClass().getResourceAsStream("/map/" + r2.c + ".m");
                            if (inputStream == null) {
                                e.a.b.a(inputStream);
                                break block7;
                            }
                            dataInputStream = new DataInputStream(inputStream);
                            int n3 = dataInputStream.readInt();
                            dataInputStream.readInt();
                            dataInputStream.skip(n3);
                            r2.b(dataInputStream);
                            r2.A = c.r.a(dataInputStream);
                            e.a.b.a(inputStream);
                            break block8;
                        }
                        catch (Exception exception) {
                            Exception exception2 = exception;
                            exception.printStackTrace();
                            break block9;
                        }
                    }
                    e.a.b.a(null);
                    break block10;
                }
                e.a.b.a(dataInputStream);
                break block9;
                finally {
                    e.a.b.a(inputStream);
                    e.a.b.a(dataInputStream);
                }
            }
            r2.l();
        }
        this.g = main.b.d;
        this.h = main.b.e;
        this.j = (byte)3;
        this.k = (byte)5;
    }

    private static e[] a(DataInputStream dataInputStream) {
        if (dataInputStream.available() == 0) {
            return null;
        }
        int n2 = dataInputStream.readShort();
        if (n2 <= 0) {
            return null;
        }
        e[] eArray = new e[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            eArray[i2] = new e();
            eArray[i2].a(dataInputStream);
        }
        return eArray;
    }

    public final boolean b(int n2, int n3) {
        if ((n2 /= 16) < 0 || (n3 /= 16) < 0 || n3 >= this.t || n2 >= this.u) {
            return false;
        }
        e e2 = this.c(n2, n3);
        if (e2 != null && !e2.o) {
            return false;
        }
        if ((n2 = this.E[n3][n2]) < 0) {
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
        return (this.F[n3] & 1L << n2) == 0L;
    }

    public final e c(int n2, int n3) {
        if (this.A == null || this.A.length <= 0) {
            return null;
        }
        e e2 = null;
        for (int i2 = 0; i2 < this.A.length; ++i2) {
            e2 = this.A[i2];
            if (e2.f() != n2 || e2.g() != n3) continue;
            return e2;
        }
        return null;
    }

    public final c a(int n2, int n3, byte by) {
        if ((this = ((r)this).c(n2, n3)) != null && ((e)this).o() == 2 && ((e)this).p) {
            short s2 = ((b.b)this).f;
            c c2 = b.a.a(b.a.x.c, s2);
            return c2;
        }
        return null;
    }

    public final int c() {
        return this.u << 4;
    }

    public final int f() {
        return this.t << 4;
    }

    public final void a() {
        b.b b2;
        r r2;
        if (b.a.n[2] && System.currentTimeMillis() - this.B > 300L) {
            this.B = System.currentTimeMillis();
            r2 = this;
            if (r2.C != null && r2.D != null && r2.C.length != 0 && r2.C.length == r2.D.length) {
                for (int i2 = 0; i2 < r2.C.length; ++i2) {
                    int n2 = r2.D[i2][1];
                    byte by = r2.D[i2][2];
                    r r3 = r2;
                    if (n2 <= 1 || by < 0 || by >= r3.w.length || by + n2 - 1 >= r3.w.length) continue;
                    int[] nArray = null;
                    nArray = r3.w[by + n2 - 1];
                    for (int i3 = 1; i3 < n2; ++i3) {
                        r3.w[by + i3] = r3.w[by + i3 - 1];
                    }
                    r3.w[by] = nArray;
                }
            }
        }
        if (this.A != null && this.A.length > 0) {
            for (int i4 = 0; i4 < this.A.length; ++i4) {
                this.A[i4].i();
            }
        }
        r2 = this;
        if (b.a.v != 2 && (b2 = b.a.x.a(b.a.w)) != null) {
            r2.a(b2);
        }
    }

    public final e[] k() {
        return this.A;
    }

    public final b.b a(short s2) {
        if (b.a.B[0].f == s2) {
            return b.a.B[0];
        }
        return this.b(s2);
    }

    public final e b(short s2) {
        if (this.A == null) {
            return null;
        }
        for (int i2 = 0; i2 < this.A.length; ++i2) {
            if (this.A[i2].f != s2) continue;
            return this.A[i2];
        }
        return null;
    }

    private void b(DataInputStream dataInputStream) {
        int n2;
        int n3;
        this.z = dataInputStream.readByte();
        this.u = e.a.b.a((int)dataInputStream.readByte()) + 1;
        this.t = e.a.b.a((int)dataInputStream.readByte()) + 1;
        dataInputStream.readByte();
        dataInputStream.readByte();
        int n4 = e.a.b.a((int)dataInputStream.readByte());
        this.G = e.a.b.a((int)dataInputStream.readByte());
        this.y = new String[n4];
        this.x = new int[n4][2];
        for (n3 = 0; n3 < n4; ++n3) {
            this.x[n3][0] = e.a.b.a((int)dataInputStream.readByte());
            this.x[n3][1] = e.a.b.a((int)dataInputStream.readByte());
            this.y[n3] = dataInputStream.readUTF();
        }
        byte by = dataInputStream.readByte();
        n3 = by;
        if (by > 0) {
            this.C = new Image[n3][];
            this.D = new byte[n3][3];
            for (n4 = 0; n4 < n3; ++n4) {
                n2 = dataInputStream.readByte();
                byte by2 = dataInputStream.readByte();
                this.D[n4][0] = 0;
                this.D[n4][1] = by2;
                this.D[n4][2] = n2;
                this.C[n4] = new Image[by2];
            }
        }
        this.E = new byte[this.t][this.u];
        for (n4 = 0; n4 < this.t; ++n4) {
            for (n2 = 0; n2 < this.u; ++n2) {
                this.E[n4][n2] = dataInputStream.readByte();
            }
        }
        this.F = new long[4];
        for (n4 = 0; n4 < this.F.length; ++n4) {
            this.F[n4] = dataInputStream.readLong();
        }
    }

    public final void l() {
        if (this.y != null && this.x != null && this.y.length == this.x.length) {
            int n2;
            int n3;
            this.v = new Image[this.y.length];
            this.w = new int[this.G][3];
            int n4 = 0;
            e.a.c c2 = null;
            for (n3 = 0; n3 <= 7 && n4 == 0; ++n3) {
                n4 = 1;
                c2 = e.a.c.a("/m" + n3 + ".pak");
                for (n2 = 0; n2 < this.y.length; ++n2) {
                    if (this.v[n2] != null) continue;
                    this.v[n2] = c2.b(this.y[n2] + ".png");
                    if (this.v[n2] != null) continue;
                    n4 = 0;
                }
            }
            n2 = 0;
            n4 = 0;
            int n5 = 0;
            for (int i2 = 0; i2 < this.y.length; ++i2) {
                if (this.v[i2] == null) continue;
                n2 = this.v[i2].getWidth() / this.x[i2][0];
                n3 = this.v[i2].getHeight() / this.x[i2][1];
                n5 = n3 * n2;
                for (n3 = 0; n3 < n5; ++n3) {
                    this.w[n4][0] = i2;
                    this.w[n4][1] = this.x[i2][0] * (n3 % n2);
                    this.w[n4][2] = this.x[i2][1] * (n3 / n2);
                    ++n4;
                }
            }
        }
        this.t();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void a(Graphics graphics, int n2, int n3) {
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        Graphics graphics2 = graphics;
        r r2 = this;
        if (b.a.ah > 0) {
            if (r2.I <= b.a.ah) {
                boolean bl = r2.J = !r2.J;
                if (r2.J) {
                    graphics2.setColor(b.a.af);
                    graphics2.fillRect(0, 0, main.b.d, main.b.e);
                    ++r2.I;
                    if (r2.I <= b.a.ah) return;
                    r2.H = 0;
                    return;
                }
            }
            if (r2.H >= b.a.ag) {
                r2.I = 0;
                r2.H = 0;
            } else {
                ++r2.H;
            }
        }
        boolean bl = false;
        if (bl) {
            return;
        }
        graphics2 = graphics;
        r2 = this;
        graphics2.translate(-graphics2.getTranslateX(), -graphics2.getTranslateY());
        if (b.a.aj > 0 && b.a.ak > 0) {
            if (r2.L <= b.a.aj) {
                ++r2.L;
                n8 = e.a.b.d(b.a.ak);
                n7 = e.a.b.d(b.a.ak);
                graphics2.translate(n8, n7);
                if (r2.L > b.a.aj) {
                    r2.K = 0;
                }
            } else if (r2.K >= b.a.ai) {
                r2.L = 0;
                r2.K = 0;
            } else {
                ++r2.K;
            }
        }
        graphics2 = graphics;
        r2 = this;
        if (r2.n != null) {
            graphics2.drawImage(r2.n, 0, 0, 20);
        } else {
            graphics2.setColor(r2.p);
            graphics2.fillRect(0, 0, main.b.d, main.b.e);
        }
        n8 = r2.a / 16;
        n7 = r2.b / 16;
        int n9 = 0;
        int n10 = n7;
        int[] nArray = null;
        int n11 = 0;
        int n12 = 0;
        Image image = null;
        int n13 = 0;
        int n14 = 0;
        n12 = 0;
        n12 = 0;
        int n15 = 0;
        int n16 = 0;
        for (n6 = 0; n6 < r2.z; ++n6) {
            n5 = 0;
            while (n5 < main.b.e + 64) {
                n9 = n8;
                n4 = 0;
                while (n4 < main.b.d + 16) {
                    if (n10 >= 0 && n10 < r2.t && n9 >= 0 && n9 < r2.u) {
                        n12 = r2.E[n10][n9];
                        if (n12 < 0) {
                            n12 += 256;
                        }
                        if (n12 >= 0 && n12 != 255 && n12 < r2.w.length && (n11 = (nArray = r2.w[n12])[0]) >= 0 && n11 < r2.v.length && (image = r2.v[n11]) != null) {
                            n13 = (n9 << 4) - r2.a;
                            n14 = (n10 << 4) - r2.b + 16;
                            if ((n14 <= main.b.e + 16 || n14 + image.getHeight() >= main.b.e) && r2.x[n11][1] <= 16) {
                                n16 = r2.x[n11][0];
                                n15 = n12;
                                while (n9 + 1 < r2.u) {
                                    n12 = r2.E[n10][n9 + 1];
                                    if (n12 < 0) {
                                        n12 += 256;
                                    }
                                    if (n12 == 255 || n12 < 0 || n12 >= r2.w.length || n12 != n15 + 1) break;
                                    n15 = n12;
                                    if ((n12 = r2.w[n12][0]) != n11 || nArray[1] + n16 + r2.x[n11][0] > image.getWidth()) break;
                                    n16 += r2.x[n11][0];
                                    ++n9;
                                    n4 += 16;
                                }
                                ++main.b.b;
                                e.a.b.a(graphics2, image, nArray[1], nArray[2], n16, r2.x[n11][1], n13, n14, 36);
                            }
                        }
                    }
                    n4 += 16;
                    ++n9;
                }
                n5 += 16;
                ++n10;
            }
        }
        n10 = n7;
        for (n6 = 0; n6 < r2.z; ++n6) {
            n5 = 0;
            while (n5 < main.b.e + 64) {
                n9 = n8;
                n4 = 0;
                while (n4 < main.b.d + 16) {
                    if (n10 >= 0 && n10 < r2.t && n9 >= 0 && n9 < r2.u) {
                        n12 = r2.E[n10][n9];
                        if (n12 < 0) {
                            n12 += 256;
                        }
                        if (n12 >= 0 && n12 != 255 && n12 < r2.w.length && (n11 = (nArray = r2.w[n12])[0]) >= 0 && n11 < r2.v.length && (image = r2.v[n11]) != null) {
                            n13 = (n9 << 4) - r2.a;
                            n14 = (n10 << 4) - r2.b + 16;
                            if ((n14 <= main.b.e + 16 || n14 + image.getHeight() >= main.b.e) && r2.x[n11][1] > 16) {
                                n16 = r2.x[n11][0];
                                n15 = n12;
                                while (n9 + 1 < r2.u) {
                                    n12 = r2.E[n10][n9 + 1];
                                    if (n12 < 0) {
                                        n12 += 256;
                                    }
                                    if (n12 == 255 || n12 < 0 || n12 >= r2.w.length || n12 != n15 + 1) break;
                                    n15 = n12;
                                    if ((n12 = r2.w[n12][0]) != n11 || nArray[1] + n16 + r2.x[n11][0] > image.getWidth()) break;
                                    n16 += r2.x[n11][0];
                                    ++n9;
                                    n4 += 16;
                                }
                                ++main.b.b;
                                e.a.b.a(graphics2, image, nArray[1], nArray[2], n16, r2.x[n11][1], n13, n14, 36);
                            }
                        }
                    }
                    n4 += 16;
                    ++n9;
                }
                if (r2.A != null) {
                    c.r.a(graphics2, n10, r2.A, 0, r2.A.length);
                }
                if (b.a.i) {
                    c.r.a(graphics2, n10, b.a.B, 0, b.a.B.length);
                } else {
                    c.r.a(graphics2, n10, b.a.B, 0, 1);
                }
                n5 += 16;
                ++n10;
            }
        }
        if (!b.a.ae) return;
        graphics.setColor(0);
        graphics.fillRect(0, 0, main.b.d, main.b.e);
    }

    public final void a(b.b object) {
        if (object == null) {
            return;
        }
        this.r = ((b.b)object).j + 8 - main.b.f;
        this.s = ((b.b)object).k - 8 - main.b.g;
        if (this.r < 0) {
            this.r = 0;
        }
        if (this.s < 0) {
            this.s = 0;
        }
        object = b.a.x;
        if (this.r + main.b.d > ((r)object).u << 4) {
            object = b.a.x;
            this.r = (((r)object).u << 4) - main.b.d;
        }
        object = b.a.x;
        if (this.s + main.b.e > ((r)object).t << 4) {
            object = b.a.x;
            this.s = (((r)object).t << 4) - main.b.e;
        }
        this.a = this.r;
        this.b = this.s;
    }

    public final void a(int n2, int n3) {
        if (b.a.r != null) {
            return;
        }
        switch (n2) {
            case -6: {
                v0.m = new s();
                this.m.a(this);
                d.a.d(this.m);
                return;
            }
            case -7: {
                this.i();
                return;
            }
        }
        b.a.B[0].b(n2, n3);
    }

    public final void d(int n2, int n3) {
        int[] nArray = e.a.b.a(this.a, this.b, n2, n3);
        this.a = nArray[0];
        this.b = nArray[1];
    }

    public final void a(int n2, int n3, int n4) {
        if (n2 != this.a) {
            if (n2 > this.a) {
                this.a += Math.abs(n4);
                if (this.a > n2) {
                    this.a = n2;
                }
            } else {
                this.a -= Math.abs(n4);
                if (this.a < n2) {
                    this.a = n2;
                }
            }
        }
        if (n3 != this.b) {
            if (n3 > this.b) {
                this.b += Math.abs(n4);
                if (this.b > n3) {
                    this.b = n3;
                    return;
                }
            } else {
                this.b -= Math.abs(n4);
                if (this.b < n3) {
                    this.b = n3;
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
        Object object = new int[]{n2, n3, e.a.b.a(n2, n4, n5), 4};
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
            int[] nArray = c.r.a(vector);
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
        if (!((r)object).b(n2 << 4, n3 << 4)) {
            return;
        }
        Object object = null;
        int[] nArray = c.r.a(n2, n3, vector);
        object = nArray;
        if (nArray != null) {
            return;
        }
        int[] nArray2 = c.r.a(n2, n3, vector2);
        object = nArray2;
        if (nArray2 != null) {
            return;
        }
        int[] nArray3 = new int[4];
        object = nArray3;
        nArray3[0] = n2;
        object[1] = n3;
        object[2] = e.a.b.a(n2, n4, n5) + e.a.b.a(n2, n6, n7);
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
        Object object = c.r.b(nArray[0], nArray[1], nArray[3]);
        Vector<Integer> vector2 = new Vector<Integer>();
        vector2.addElement(new Integer(b.b.b(nArray[3])));
        nArray = null;
        while (object[0] != n2 || object[1] != n3) {
            nArray = c.r.a((int)object[0], (int)object[1], vector);
            if (nArray == null) {
                return null;
            }
            object = c.r.b(nArray[0], nArray[1], nArray[3]);
            vector2.addElement(new Integer(b.b.b(nArray[3])));
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

    public final void m() {
        this.v = null;
        this.n = null;
    }

    public final void n() {
        if (this.A == null) {
            return;
        }
        for (int i2 = 0; i2 < this.A.length; ++i2) {
            this.A[i2].h();
        }
    }

    public final void o() {
        if (this.A == null) {
            return;
        }
        for (int i2 = 0; i2 < this.A.length; ++i2) {
            if (!this.A[i2].a && (this.A[i2].r != 2 || !this.A[i2].p)) continue;
            this.A[i2].a();
        }
    }

    public final void p() {
        if (this.A == null) {
            return;
        }
        for (int i2 = 0; i2 < this.A.length; ++i2) {
            this.A[i2].a(this.A[i2].e, this.A[i2].e());
            this.A[i2].a(this.A[i2].d());
        }
    }

    public final void a(byte[] object) {
        if (object == null) {
            return;
        }
        object = new ByteArrayInputStream((byte[])object);
        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
        try {
            int n2 = e.a.b.a(dataInputStream.readByte());
            if (n2 < 0) {
                return;
            }
            for (int i2 = 0; i2 < n2; ++i2) {
                byte by = dataInputStream.readByte();
                e e2 = this.b(by);
                if (e2 == null) continue;
                e2.b(dataInputStream);
            }
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
        finally {
            e.a.b.a((InputStream)object);
            e.a.b.a(dataInputStream);
        }
    }

    public final byte[] q() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            int n2 = this.A == null ? 0 : this.A.length;
            dataOutputStream.writeByte(n2);
            if (n2 > 0) {
                e e2 = null;
                for (int i2 = 0; i2 < this.A.length; ++i2) {
                    e2 = this.A[i2];
                    dataOutputStream.writeByte(e2.f);
                    ((OutputStream)dataOutputStream).write(e2.p());
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
            e.a.b.a(byteArrayOutputStream);
            e.a.b.a(dataOutputStream);
        }
    }

    public final String d() {
        return null;
    }

    public final e a(int n2, int n3, int n4, int n5, int n6) {
        if (this.A == null) {
            return null;
        }
        for (n4 = 0; n4 < this.A.length; ++n4) {
            if (this.A[n4] == null || !this.A[n4].p || this.A[n4].o() != 3 || !e.a.b.a(n2, n3, 48, 48, this.A[n4].j, this.A[n4].k, 16, 16)) continue;
            return this.A[n4];
        }
        return null;
    }

    public final c a(e e2) {
        if (e2 == null) {
            return null;
        }
        return b.a.a(this.c, e2.f);
    }

    public final void e() {
        this.m = null;
    }

    public final boolean b(b.b b2) {
        if (b2 == null) {
            return false;
        }
        int n2 = b2.j;
        int n3 = b2.k;
        boolean bl = e.a.b.a(n2 - b2.l / 2, n3 - b2.m + 16, b2.l, b2.m, this.a, this.b, main.b.d, main.b.e);
        return bl;
    }

    public final void b() {
    }

    private static void a(Graphics graphics, int n2, b.b[] bArray, int n3, int n4) {
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
            if (bArray[n3] == null || !bArray[n3].a || (bArray[n3].k % 16 != 0 || bArray[n3].g() != n2) && (bArray[n3].k + 16 <= n2 << 4 || bArray[n3].k + 16 >= n2 + 1 << 4)) continue;
            n5 = bArray[n4].j - b.a.x.a + 8;
            n6 = bArray[n4].k - b.a.x.b + 16;
            bArray[n3].a(graphics, n5, n6);
        }
    }

    public final e.b.c a(String string) {
        if (string == null || this.A == null) {
            return null;
        }
        for (int i2 = 0; i2 < this.A.length; ++i2) {
            if (this.A[i2] == null || !this.A[i2].b.equals(string) || this.A[i2].c == null) continue;
            return this.A[i2].c;
        }
        return null;
    }

    public final void a(int n2) {
        this.p = n2;
    }

    public final void b(String string) {
        if (string != null) {
            this.q = string;
        }
    }

    public final String r() {
        return this.q;
    }

    public final void c(String string) {
        if (string == null) {
            return;
        }
        if (this.o == null || !this.o.equals(string)) {
            this.o = string;
            this.t();
        }
    }

    private void t() {
        if (this.o == null || this.o.trim().length() == 0) {
            return;
        }
        this.n = e.a.b.a("/" + this.o + ".png");
    }

    public final void a(k uArray) {
        u[] uArray2 = uArray.d();
        if (uArray == ((r)k2).m) {
            uArray = uArray2;
            if (uArray.equals(b.a.p[11])) {
                c.n.a();
                u[] uArray3 = new u[3];
                uArray = uArray3;
                uArray3[0] = new n(0);
                uArray[1] = new n(1);
                uArray[2] = new h();
                uArray2 = e.a.c.a("/c.pak");
                Image[] imageArray = new Image[3];
                Image[] imageArray2 = imageArray;
                imageArray[0] = uArray2.b("save.png");
                imageArray2[1] = uArray2.b("load.png");
                imageArray2[2] = uArray2.b("set.png");
                ((r)k2).l = new t(imageArray2, uArray, main.b.d, main.b.e);
                c.t.a(e.a.b.a("/bg.png"));
                ((r)k2).l.a((main.a)((Object)k2));
                d.a.d(((r)k2).l);
                return;
            }
            if (uArray.equals(b.a.p[9])) {
                uArray = new Image[2];
                uArray2 = e.a.c.a("/c.pak");
                uArray[0] = uArray2.b("pt0.png");
                uArray[1] = uArray2.b("pt1.png");
                Image image = null;
                uArray2 = new m[b.a.B.length];
                for (int i2 = 0; i2 < uArray2.length; ++i2) {
                    image = e.b.a.b("r" + b.a.B[i2].f);
                    uArray2[i2] = new m(b.a.B[i2], (Image[])uArray, image);
                }
                Image[] imageArray = new Image[b.a.B.length];
                for (int i3 = 0; i3 < imageArray.length; ++i3) {
                    imageArray[i3] = b.a.R[b.a.B[i3].f];
                }
                t t2 = new t(imageArray, uArray2, main.b.d, main.b.e);
                c.t.a(e.a.b.a("/bg.png"));
                t2.a((main.a)((Object)k2));
                d.a.d(t2);
                return;
            }
            if (uArray.equals(b.a.p[8])) {
                ((r)k2).u();
                return;
            }
            if (uArray.equals(b.a.p[7])) {
                ((r)k2).w();
                return;
            }
            if (uArray.equals(b.a.p[10])) {
                k k2 = c.b.a(b.a.p[69], 3000L);
                ((j)k2).f();
                return;
            }
            if (uArray.equals(b.a.p[0])) {
                ((r)k2).v();
            }
        }
    }

    public final void c(k k2) {
        d.a.e(k2);
        if (k2 == this.l) {
            this.l = null;
            c.n.a = null;
        }
        if (!d.a.f() && d.a.h() == b.a.x) {
            c.t.a(null);
        }
    }

    public final void b(k k2) {
    }

    private void u() {
        u[] uArray = new a[c.a.a.length];
        for (int i2 = 0; i2 < uArray.length; ++i2) {
            uArray[i2] = new a(c.a.a[i2], true);
        }
        Image[] imageArray = new Image[c.a.a.length];
        for (int i3 = 0; i3 < imageArray.length; ++i3) {
            imageArray[i3] = b.a.S[c.a.a[i3]];
        }
        t t2 = new t(imageArray, uArray, main.b.d, main.b.e);
        c.t.a(e.a.b.a("/bg.png"));
        t2.a(this);
        d.a.d(t2);
    }

    private void v() {
        u[] uArray = new o[b.a.B.length];
        for (int i2 = 0; i2 < uArray.length; ++i2) {
            uArray[i2] = new o(b.a.B[i2]);
        }
        Image[] imageArray = new Image[b.a.B.length];
        for (int i3 = 0; i3 < imageArray.length; ++i3) {
            imageArray[i3] = b.a.R[b.a.B[i3].f];
        }
        t t2 = new t(imageArray, uArray, main.b.d, main.b.e);
        c.t.a(e.a.b.a("/bg.png"));
        t2.a(this);
        d.a.d(t2);
    }

    private void w() {
        u[] uArray = new p[b.a.B.length];
        for (int i2 = 0; i2 < uArray.length; ++i2) {
            uArray[i2] = new p(b.a.B[i2]);
        }
        Image[] imageArray = new Image[b.a.B.length];
        for (int i3 = 0; i3 < imageArray.length; ++i3) {
            imageArray[i3] = b.a.R[b.a.B[i3].f];
        }
        t t2 = new t(imageArray, uArray, main.b.d, main.b.e);
        c.t.a(e.a.b.a("/bg.png"));
        t2.a(this);
        d.a.d(t2);
    }

    public final void s() {
        if (this.A == null) {
            return;
        }
        for (int i2 = 0; i2 < this.A.length; ++i2) {
            if (this.A[i2] == null || !this.A[i2].p || this.A[i2].r != 2) continue;
            this.A[i2].o = true;
            this.A[i2].v = false;
            this.A[i2].a = true;
        }
    }
}

