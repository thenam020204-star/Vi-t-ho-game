/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package a.b;

import a.b.b;
import a.c.d;
import a.e.c;
import b.e;
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

public abstract class a
extends a.a.a {
    private int m;
    private String n;
    public boolean h;
    public int i;
    public int j;
    private int o;
    private int p;
    public int k;
    private int q;
    private int r;
    private int s;
    private byte[] t;
    private b[] u;
    protected e[] l;
    private long v;
    private byte[] w;
    private int x;
    private int y;
    private boolean z;
    private int A;
    private int B;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected a(int n2) {
        block11: {
            a a2;
            block10: {
                DataInputStream dataInputStream;
                ByteArrayInputStream byteArrayInputStream;
                block9: {
                    block8: {
                        super((byte)2);
                        this.m = 0;
                        this.n = "";
                        this.h = true;
                        this.i = 0;
                        this.j = 0;
                        this.o = 0;
                        this.p = 0;
                        this.k = -1;
                        this.q = -1;
                        this.r = -1;
                        this.s = 1;
                        this.t = null;
                        this.u = null;
                        this.l = null;
                        this.v = 0L;
                        this.w = null;
                        this.x = 0;
                        this.y = 0;
                        this.z = true;
                        this.A = 0;
                        this.B = 0;
                        this.k = n2;
                        a2 = this;
                        byteArrayInputStream = null;
                        dataInputStream = null;
                        try {
                            if (a2.w == null) {
                                a2.w = a.c.b.a("/map/" + a2.k + ".m2");
                            }
                            if (a2.w == null) {
                                a.c.b.a(null);
                                break block8;
                            }
                            byteArrayInputStream = new ByteArrayInputStream(a2.w);
                            dataInputStream = new DataInputStream(byteArrayInputStream);
                            a2.n = dataInputStream.readUTF();
                            a2.h = dataInputStream.readByte() != 1;
                            int n3 = dataInputStream.readInt();
                            dataInputStream.readInt();
                            dataInputStream.skip(n3);
                            a2.b(dataInputStream);
                            a2.l = a.b.a.a(dataInputStream);
                            a.c.b.a(byteArrayInputStream);
                            break block9;
                        }
                        catch (Exception exception) {
                            Exception exception2 = exception;
                            exception.printStackTrace();
                            break block10;
                        }
                    }
                    a.c.b.a(null);
                    break block11;
                }
                a.c.b.a(dataInputStream);
                break block10;
                finally {
                    a.c.b.a(byteArrayInputStream);
                    a.c.b.a(dataInputStream);
                }
            }
            a2.p();
        }
        this.c = main.a.d;
        this.d = main.a.e;
        this.e = (byte)3;
        this.f = (byte)5;
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
        if ((n2 /= 16) < 0 || (n3 /= 16) < 0 || n3 >= this.q || n2 >= this.r) {
            return false;
        }
        int n4 = n3;
        int n5 = n2;
        Object object = this;
        if ((object = ((a)object).a(n5, n4, false)) != null && !((b.b)object).b) {
            return false;
        }
        boolean bl = false;
        for (n5 = 0; n5 < this.s; ++n5) {
            int n6 = (this.r * this.q << 1) * n5;
            n4 = a.c.b.a((int)this.t[n6 += n3 * this.r + n2 << 1]);
            if (n4 == 255) {
                if (n5 != 0) continue;
                return false;
            }
            if (n4 >= this.u.length) {
                return false;
            }
            b b2 = this.u[n4];
            n4 = this.t[n6 + 1];
            b b3 = b2;
            if ((n4 = a.c.b.a(n4)) != 255 && (b3.j & 1L << n4) == 0L) continue;
            return false;
        }
        return true;
    }

    public final e c(int n2, int n3) {
        return this.a(n2, n3, false);
    }

    private e a(int n2, int n3, boolean bl) {
        if (this.l == null || this.l.length <= 0) {
            return null;
        }
        e e2 = null;
        for (int i2 = 0; i2 < this.l.length; ++i2) {
            e2 = this.l[i2];
            if (e2.f() != n2 || e2.g() != n3 || bl && !e2.c) continue;
            return e2;
        }
        return null;
    }

    public final a.d.b a(int n2, int n3, byte by) {
        e e2 = ((a)((Object)b2)).a(n2, n3, true);
        if (e2 != null && e2.o() == 2 && e2.c) {
            short s = e2.i;
            a.d.b b2 = ((a)((Object)b2)).c(s);
            return b2;
        }
        return null;
    }

    public final int g() {
        return this.r << 4;
    }

    public final int h() {
        return this.q << 4;
    }

    public final void a() {
        if (b.a.l[2] && System.currentTimeMillis() - ((a)this).v > 300L) {
            ((a)this).v = System.currentTimeMillis();
            a a2 = this;
            for (int i2 = 0; i2 < a2.u.length; ++i2) {
                b b2 = a2.u[i2];
                if (b2.k != null) {
                    for (int i3 = 0; i3 < b2.k.length; i3 += 3) {
                        if (b2.k[i3 + 1] <= 1) continue;
                        b2.k[i3 + 2] = (byte)((b2.k[i3 + 2] + 1) % b2.k[i3 + 1]);
                    }
                }
            }
        }
        if (((a)this).l != null && ((a)this).l.length > 0) {
            for (int i4 = 0; i4 < ((a)this).l.length; ++i4) {
                ((a)this).l[i4].b();
                ((a)this).l[i4].i();
            }
        }
        for (int i5 = 0; i5 < b.a.A.length; ++i5) {
            b.a.A[i5].b();
        }
        a a3 = this;
        if (b.a.u != 2 && (this = b.a.w.a(b.a.v)) != null) {
            a3.a((b.b)this);
        }
    }

    public final e[] i() {
        return this.l;
    }

    public final b.b a(short s) {
        if (b.a.A[0].i == s) {
            return b.a.A[0];
        }
        return this.b(s);
    }

    public final e b(short s) {
        if (this.l == null) {
            return null;
        }
        for (int i2 = 0; i2 < this.l.length; ++i2) {
            if (this.l[i2].i != s) continue;
            return this.l[i2];
        }
        return null;
    }

    private void b(DataInputStream dataInputStream) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        Object object;
        this.s = dataInputStream.readByte();
        this.r = a.c.b.a((int)dataInputStream.readByte());
        this.q = a.c.b.a((int)dataInputStream.readByte());
        int n8 = a.c.b.a((int)dataInputStream.readByte());
        this.u = new b[n8];
        n8 = 0;
        for (int i2 = 0; i2 < this.u.length; ++i2) {
            this.u[i2] = new b(this);
            this.u[i2].d = n8;
            DataInputStream dataInputStream2 = dataInputStream;
            object = this.u[i2];
            this.u[i2].b = dataInputStream2.readUTF();
            ((b)object).e = a.c.b.a((int)dataInputStream2.readByte());
            ((b)object).f = dataInputStream2.readShort();
            ((b)object).h = dataInputStream2.readByte();
            n7 = ((b)object).h & 0xF;
            n5 = n6 = ((b)object).h >> 4 & 0xF;
            n4 = n7;
            n3 = 0;
            block0 : switch (n4) {
                case 2: {
                    switch (n5) {
                        case 1: {
                            n3 = 7;
                            break block0;
                        }
                        case 2: {
                            n3 = 1;
                            break block0;
                        }
                        case 4: {
                            n3 = 4;
                            break block0;
                        }
                    }
                    n3 = 2;
                    break;
                }
                case 1: {
                    switch (n5) {
                        case 1: {
                            n3 = 4;
                            break block0;
                        }
                        case 2: {
                            n3 = 2;
                            break block0;
                        }
                        case 4: {
                            n3 = 7;
                            break block0;
                        }
                    }
                    n3 = 1;
                    break;
                }
                default: {
                    switch (n5) {
                        case 1: {
                            n3 = 5;
                            break block0;
                        }
                        case 2: {
                            n3 = 3;
                            break block0;
                        }
                        case 4: {
                            n3 = 6;
                            break block0;
                        }
                    }
                    n3 = 0;
                }
            }
            ((b)object).g = n3;
            ((b)object).i = dataInputStream2.readUTF();
            ((b)object).j = dataInputStream2.readLong();
            n2 = a.c.b.a((int)dataInputStream2.readByte());
            if (n2 > 0) {
                ((b)object).k = new byte[n2 * 3];
                for (n4 = 0; n4 < n2; ++n4) {
                    ((b)object).k[n4 * 3] = dataInputStream2.readByte();
                    ((b)object).k[n4 * 3 + 1] = dataInputStream2.readByte();
                    ((b)object).k[n4 * 3 + 2] = 0;
                }
            }
            n8 += this.u[i2].e;
        }
        this.t = new byte[(this.q * this.r << 1) * this.s];
        byte[] byArray = null;
        n8 = 0;
        n4 = 0;
        for (n5 = 0; n5 < this.s; ++n5) {
            n4 = n5 * this.q * this.r << 1;
            for (n3 = 0; n3 < this.q; ++n3) {
                for (int i3 = 0; i3 < this.r; ++i3) {
                    byte[] byArray2;
                    int n9 = n8 = (int)dataInputStream.readByte();
                    object = this;
                    n7 = a.c.b.a(n9);
                    if (n7 != 255) {
                        n6 = 0;
                        for (n2 = 0; n2 < ((a)object).u.length; ++n2) {
                            if (n7 < ((a)object).u[n2].d || n7 >= ((a)object).u[n2].d + ((a)object).u[n2].e) continue;
                            n6 = n7 - ((a)object).u[n2].d;
                            byte[] byArray3 = new byte[2];
                            byArray3[0] = (byte)n2;
                            byArray2 = byArray3;
                            byArray3[1] = a.c.b.b(n6);
                            break;
                        }
                    } else {
                        byte[] byArray4 = new byte[2];
                        byArray4[0] = -1;
                        byArray2 = byArray4;
                        byArray4[1] = -1;
                    }
                    byArray = byArray2;
                    this.t[n4 + (n3 * this.r + i3 << 1)] = byArray[0];
                    this.t[n4 + (n3 * this.r + i3 << 1) + 1] = byArray[1];
                }
            }
        }
    }

    private void p() {
        d d2 = null;
        boolean bl = false;
        for (int i2 = 0; i2 < 5; ++i2) {
            d2 = a.c.d.a("/m" + i2 + ".pak");
            bl = true;
            for (int i3 = 0; i3 < this.u.length; ++i3) {
                Object object;
                if (this.u[i3].c != null) continue;
                bl = false;
                Object object2 = d2;
                b b2 = this.u[i3];
                d d3 = object2;
                String string = b2.i;
                Object object3 = b2.b;
                object2 = b2.a;
                if (object3 == null || ((String)object3).length() == 0) {
                    object = null;
                } else if ((object2 = super.a((String)object3, string)) != null) {
                    object = object2;
                } else if (string == null || string.trim().length() == 0) {
                    object = d3 != null ? d3.c((String)object3 + ".png") : a.c.b.b("/map/" + (String)object3 + ".png");
                } else if (d3 != null) {
                    byte[] byArray = d3.b((String)object3 + ".png");
                    object2 = byArray;
                    if (byArray == null) {
                        object = null;
                    } else {
                        object3 = new byte[((Object)object2).length];
                        System.arraycopy(object2, 0, object3, 0, ((Object)object2).length);
                        object2 = a.c.b.a("/map/" + string + ".pl");
                        object = a.c.b.a((byte[])object3, (byte[])object2);
                    }
                } else {
                    object = this.u[i3].c = a.c.b.a("/map/" + (String)object3 + ".png", "/map/" + string + ".pl");
                }
                if (b2.c == null) continue;
                int n2 = b2.h >> 4 & 0xF;
                if (n2 == 1 || n2 == 4) {
                    b2.l = b2.c.getHeight() / 16;
                    b2.c.getWidth();
                    continue;
                }
                b2.l = b2.c.getWidth() / 16;
                b2.c.getHeight();
            }
            if (bl) break;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void a(Graphics graphics, int n2, int n3) {
        int n4;
        Graphics graphics2 = graphics;
        a a2 = a3;
        if (b.a.ak > 0) {
            if (a2.y <= b.a.ak) {
                boolean bl = a2.z = !a2.z;
                if (a2.z) {
                    graphics2.setColor(b.a.ai);
                    graphics2.fillRect(0, 0, main.a.d, main.a.e);
                    ++a2.y;
                    if (a2.y <= b.a.ak) return;
                    a2.x = 0;
                    return;
                }
            }
            if (a2.x >= b.a.aj) {
                a2.y = 0;
                a2.x = 0;
            } else {
                ++a2.x;
            }
        }
        boolean bl = false;
        if (bl) {
            return;
        }
        graphics2 = graphics;
        a2 = a3;
        graphics2.translate(-graphics2.getTranslateX(), -graphics2.getTranslateY());
        if (b.a.am > 0 && b.a.an > 0) {
            if (a2.B <= b.a.am) {
                ++a2.B;
                n4 = a.c.b.d(b.a.an);
                int n5 = a.c.b.d(b.a.an);
                graphics2.translate(n4, n5);
                if (a2.B > b.a.am) {
                    a2.A = 0;
                }
            } else if (a2.A >= b.a.al) {
                a2.B = 0;
                a2.A = 0;
            } else {
                ++a2.A;
            }
        }
        graphics2 = graphics;
        a2 = a3;
        graphics2.setColor(0);
        graphics2.fillRect(0, 0, main.a.d, main.a.e);
        for (n4 = 0; n4 < a2.s; ++n4) {
            boolean bl2 = n4 == a2.s - 1;
            int n6 = n4;
            a a3 = graphics2;
            a a4 = a2;
            int n7 = a4.i / 16;
            int n8 = a4.j / 16;
            int n9 = 0;
            int n10 = 0;
            int n11 = 0;
            int n12 = 0;
            n11 = 0;
            int n13 = 0;
            int n14 = 0;
            int n15 = 0;
            int n16 = 0;
            n9 = main.a.d / 16 + 1;
            int n17 = Math.min(n7 + n9, a4.r);
            n9 = main.a.e / 16 + 5;
            int n18 = Math.min(n8 + n9, a4.q);
            int n19 = 0;
            int n20 = 0;
            n16 = n6 * a4.q * a4.r << 1;
            for (n6 = n8; n6 < n18; ++n6) {
                n10 = (n6 << 4) - a4.j + 16;
                for (n8 = n7; n8 < n17; ++n8) {
                    if (n6 < 0 || n6 >= a4.q || n8 < 0 || n8 >= a4.r || (n12 = a.c.b.a((int)a4.t[n11 = n16 + (n6 * a4.r + n8 << 1)])) == 255 || n12 >= a4.u.length || n10 - a4.u[n12].f >= main.a.e) continue;
                    n11 = a4.t[n11 + 1];
                    if (a4.u[n12].c == null) continue;
                    n11 = (byte)(n11 + a4.u[n12].a(n11));
                    n9 = (n8 << 4) - a4.i;
                    n15 = 1;
                    n13 = n12;
                    n14 = n11;
                    n19 = n11 / a4.u[n13].l;
                    n20 = n8;
                    while (n8 + 1 < a4.r && (n11 = n16 + (n6 * a4.r + n20 + n15 << 1)) < a4.t.length && (n12 = a.c.b.a((int)a4.t[n11])) != 255 && n12 < a4.u.length && n12 == n13) {
                        n11 = a4.t[n11 + 1];
                        byte by = (byte)(n11 + a4.u[n12].a(n11));
                        n11 = by;
                        if (by >= a4.u[n12].e || n11 != n14 + n15 || n11 / a4.u[n12].l != n19) break;
                        ++n15;
                        ++n8;
                        a4.a += 16;
                    }
                    ++main.a.b;
                    b b2 = a4.u[n13];
                    int n21 = n14;
                    int n22 = n15;
                    n15 = n10;
                    n14 = n9;
                    n13 = n22;
                    n12 = n21;
                    a a5 = a3;
                    b b3 = b2;
                    if (b2.c == null || n13 == 0 || (n12 = a.c.b.a(n12)) == 255 || n12 >= b3.e) continue;
                    n19 = n12 % b3.l << 4;
                    n12 = n12 / b3.l * b3.f;
                    n13 *= 16;
                    n20 = b3.f;
                    if (b3.g == 0) {
                        a.c.b.a((Graphics)a5, b3.c, n19, n12, n13, n20, 0, n14, n15, 36);
                        continue;
                    }
                    int[] nArray = a.c.b.a((Graphics)a5);
                    a5.clipRect(n14, n15 - n20, n13, n20);
                    a.c.b.a((Graphics)a5, b3.c, 0, 0, b3.c.getWidth(), b3.c.getHeight(), b3.g, n14 - n19, n15 - n12 - n20, 20);
                    a.c.b.a((Graphics)a5, nArray);
                }
                if (!bl2) continue;
                if (a4.l != null) {
                    a4.a((Graphics)a3, n6, a4.l, 0, a4.l.length);
                }
                if (b.a.i) {
                    a4.a((Graphics)a3, n6, b.a.A, 0, b.a.A.length);
                    continue;
                }
                a4.a((Graphics)a3, n6, b.a.A, 0, 1);
            }
        }
        if (!b.a.ah) return;
        graphics.setColor(0);
        graphics.fillRect(0, 0, main.a.d, main.a.e);
    }

    public final void a(b.b object) {
        if (object == null) {
            return;
        }
        this.o = ((b.b)object).n + 8 - main.a.f;
        this.p = ((b.b)object).o - 8 - main.a.g;
        if (this.o < 0) {
            this.o = 0;
        }
        if (this.p < 0) {
            this.p = 0;
        }
        object = b.a.w;
        if (this.o + main.a.d > ((a)object).r << 4) {
            object = b.a.w;
            this.o = (((a)object).r << 4) - main.a.d;
        }
        object = b.a.w;
        if (this.p + main.a.e > ((a)object).q << 4) {
            object = b.a.w;
            this.p = (((a)object).q << 4) - main.a.e;
        }
        this.i = this.o;
        this.j = this.p;
    }

    public final void d(int n2, int n3) {
        int[] nArray = a.c.b.a(this.i, this.j, n2, n3);
        this.i = nArray[0];
        this.j = nArray[1];
    }

    public final void a(int n2, int n3, int n4) {
        if (n2 != this.i) {
            if (n2 > this.i) {
                this.i += Math.abs(n4);
                if (this.i > n2) {
                    this.i = n2;
                }
            } else {
                this.i -= Math.abs(n4);
                if (this.i < n2) {
                    this.i = n2;
                }
            }
        }
        if (n3 != this.j) {
            if (n3 > this.j) {
                this.j += Math.abs(n4);
                if (this.j > n3) {
                    this.j = n3;
                    return;
                }
            } else {
                this.j -= Math.abs(n4);
                if (this.j < n3) {
                    this.j = n3;
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
        Object object = new int[]{n2, n3, a.c.b.a(n2, n4, n5), 4};
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
            int[] nArray = a.b.a.a(vector);
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
        if (!((a)object).b(n2 << 4, n3 << 4)) {
            return;
        }
        Object object = null;
        int[] nArray = a.b.a.a(n2, n3, vector);
        object = nArray;
        if (nArray != null) {
            return;
        }
        int[] nArray2 = a.b.a.a(n2, n3, vector2);
        object = nArray2;
        if (nArray2 != null) {
            return;
        }
        int[] nArray3 = new int[4];
        object = nArray3;
        nArray3[0] = n2;
        object[1] = n3;
        object[2] = a.c.b.a(n2, n4, n5) + a.c.b.a(n2, n6, n7);
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
        Object object = a.b.a.b(nArray[0], nArray[1], nArray[3]);
        Vector<Integer> vector2 = new Vector<Integer>();
        vector2.addElement(new Integer(b.b.b(nArray[3])));
        nArray = null;
        while (object[0] != n2 || object[1] != n3) {
            nArray = a.b.a.a((int)object[0], (int)object[1], vector);
            if (nArray == null) {
                return null;
            }
            object = a.b.a.b(nArray[0], nArray[1], nArray[3]);
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

    public final void j() {
        for (int i2 = 0; i2 < this.u.length; ++i2) {
            b b2 = this.u[i2];
            this.u[i2].c = null;
        }
    }

    public final void k() {
        if (this.l == null) {
            return;
        }
        for (int i2 = 0; i2 < this.l.length; ++i2) {
            this.l[i2].h();
        }
    }

    public final void l() {
        if (this.l == null) {
            return;
        }
        for (int i2 = 0; i2 < this.l.length; ++i2) {
            if (!this.l[i2].a && (this.l[i2].w != 2 || !this.l[i2].c)) continue;
            this.l[i2].a();
        }
    }

    public final void m() {
        if (this.l == null) {
            return;
        }
        for (int i2 = 0; i2 < this.l.length; ++i2) {
            this.l[i2].a(this.l[i2].g, this.l[i2].e());
            this.l[i2].a(this.l[i2].h);
            if (this.l[i2].f != null && this.l[i2].s) {
                this.l[i2].f.a(true);
            }
            this.l[i2].s = false;
        }
    }

    public final void a(byte[] object) {
        if (object == null) {
            return;
        }
        object = new ByteArrayInputStream((byte[])object);
        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
        try {
            int n2 = a.c.b.a(dataInputStream.readByte());
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
            a.c.b.a((InputStream)object);
            a.c.b.a(dataInputStream);
        }
    }

    public final byte[] n() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            int n2 = this.l == null ? 0 : this.l.length;
            dataOutputStream.writeByte(n2);
            if (n2 > 0) {
                e e2 = null;
                for (int i2 = 0; i2 < this.l.length; ++i2) {
                    e2 = this.l[i2];
                    dataOutputStream.writeByte(e2.i);
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
            a.c.b.a(byteArrayOutputStream);
            a.c.b.a(dataOutputStream);
        }
    }

    public final e a(int n2, int n3, int n4, int n5, int n6) {
        if (this.l == null) {
            return null;
        }
        for (n4 = 0; n4 < this.l.length; ++n4) {
            if (this.l[n4] == null || !this.l[n4].c || this.l[n4].o() != 3 || !a.c.b.a(n2, n3, 48, 48, this.l[n4].n, this.l[n4].o, 16, 16)) continue;
            return this.l[n4];
        }
        return null;
    }

    public final a.d.b a(e e2) {
        if (e2 == null) {
            return null;
        }
        return this.c(e2.i);
    }

    public final boolean b(b.b b2) {
        if (b2 == null) {
            return false;
        }
        int n2 = b2.n;
        int n3 = b2.o;
        boolean bl = a.c.b.a(n2 - b2.p / 2, n3 - b2.q + 16, b2.p, b2.q, this.i, this.j, main.a.d, main.a.e);
        return bl;
    }

    private void a(Graphics graphics, int n2, b.b[] bArray, int n3, int n4) {
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
            if (bArray[n3] == null || !bArray[n3].a || (bArray[n3].o % 16 != 0 || bArray[n3].g() != n2) && (bArray[n3].o + 16 <= n2 << 4 || bArray[n3].o + 16 >= n2 + 1 << 4)) continue;
            n5 = bArray[n4].n - this.i + 8;
            n6 = bArray[n4].o - this.j + 16;
            bArray[n3].a(graphics, n5, n6);
        }
    }

    public final c a(String string) {
        if (string == null || this.l == null) {
            return null;
        }
        for (int i2 = 0; i2 < this.l.length; ++i2) {
            if (this.l[i2] == null || !this.l[i2].d.equals(string) || this.l[i2].e == null) continue;
            return this.l[i2].e;
        }
        return null;
    }

    public final String o() {
        return this.n;
    }

    private Image a(String string, String string2) {
        if (this.u == null || string == null || string.trim().length() == 0) {
            return null;
        }
        String string3 = null;
        for (int i2 = 0; i2 < this.u.length; ++i2) {
            if (this.u[i2] == null || this.u[i2].b == null || this.u[i2].c == null || !this.u[i2].b.equals(string)) continue;
            string3 = this.u[i2].i;
            if ((string2 != null && string2.trim().length() != 0 || string3 != null && string3.trim().length() != 0) && (string2 == null || string3 == null || !string3.equals(string2))) continue;
            return this.u[i2].c;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final a.d.b c(short s) {
        a.d.b b2;
        DataInputStream dataInputStream;
        ByteArrayInputStream byteArrayInputStream;
        block17: {
            block16: {
                block15: {
                    block14: {
                        Object object;
                        byteArrayInputStream = null;
                        dataInputStream = null;
                        try {
                            if (((a)object).w == null) {
                                ((a)object).w = a.c.b.a("/map/" + ((a)object).k + ".m2");
                            }
                            if (((a)object).w == null) {
                                object = null;
                                a.c.b.a(null);
                                break block14;
                            }
                            byteArrayInputStream = new ByteArrayInputStream(((a)object).w);
                            dataInputStream = new DataInputStream(byteArrayInputStream);
                            dataInputStream.readUTF();
                            dataInputStream.readByte();
                            dataInputStream.skip(8L);
                            int n2 = a.c.b.a((int)dataInputStream.readByte());
                            if (n2 <= 0) {
                                Object var4_7 = null;
                                a.c.b.a(dataInputStream);
                                break block15;
                            }
                            int n3 = 0;
                            byte by = 0;
                            int n4 = 0;
                            boolean bl = false;
                            for (int i2 = 0; i2 < n2; ++i2) {
                                by = dataInputStream.readByte();
                                n4 = dataInputStream.readInt();
                                if (bl) continue;
                                if (s == by) {
                                    bl = true;
                                    continue;
                                }
                                n3 += n4;
                            }
                            if (!bl) {
                                Object var8_13 = null;
                                a.c.b.a(dataInputStream);
                                break block16;
                            }
                            dataInputStream.skip(n3);
                            a.d.b b3 = a.d.b.a(dataInputStream, null);
                            b.a.r = s;
                            b2 = b3;
                            a.c.b.a(dataInputStream);
                            break block17;
                        }
                        catch (IOException iOException) {
                            object = iOException;
                            iOException.printStackTrace();
                            return null;
                        }
                    }
                    a.c.b.a(null);
                    return null;
                }
                a.c.b.a(byteArrayInputStream);
                return null;
            }
            a.c.b.a(byteArrayInputStream);
            return null;
        }
        a.c.b.a(byteArrayInputStream);
        return b2;
        finally {
            a.c.b.a(dataInputStream);
            a.c.b.a(byteArrayInputStream);
        }
    }
}

