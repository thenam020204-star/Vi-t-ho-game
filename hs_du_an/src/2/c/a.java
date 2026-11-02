/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package c;

import b.f;
import c.b;
import c.e;
import c.j;
import c.k;
import javax.microedition.lcdui.Graphics;

public class a
extends e
implements main.a {
    public static final byte[] a = new byte[]{0, 1, 2, 3, 4, 5, 6};
    private static String[] g = new String[]{b.a.p[2], b.a.p[28], b.a.p[29]};
    private static String[] h = new String[]{b.a.p[2], b.a.p[29]};
    private f.b[] l = null;
    private int[] m = null;
    private int[] n = null;
    private byte o = (byte)-1;
    private boolean p = false;

    public a(byte by, boolean bl) {
        this.o = by;
        this.p = true;
    }

    protected final void a(Graphics graphics, int n2, int n3, int n4) {
        if (this.l == null || n2 < 0 || n2 >= this.l.length) {
            return;
        }
        graphics.drawString(this.l[n2].t, n3, n4, 20);
    }

    protected final void b(Graphics graphics, int n2, int n3, int n4) {
        if (this.m == null || n2 < 0 || n2 >= this.m.length) {
            return;
        }
        graphics.drawString(String.valueOf(this.m[n2]), n3, n4, 24);
    }

    protected final String a() {
        if (this.l == null || this.d < 0 || this.d >= this.l.length || this.l[this.d] == null) {
            return null;
        }
        return this.l[this.d].d();
    }

    protected final Object b() {
        if (this.l == null || this.d < 0 || this.d >= this.l.length) {
            return null;
        }
        return this.l[this.d];
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void c() {
        f.b b2;
        String string;
        String string2;
        void var1_4;
        if (this.l == null) {
            return;
        }
        Object var1_1 = null;
        if (this.o == 1) {
            b b3 = c.b.a(g, (byte)0, (byte)2, true);
        } else {
            b b4 = c.b.a(h, (byte)0, (byte)2, true);
        }
        int n2 = var1_4.f();
        if (n2 != 1 || (string2 = (string = var1_4.d())) == null) return;
        if (string2.equals(b.a.p[28])) {
            f.b b5 = (f.b)this.b();
            if (b5 == null) return;
            switch (b5.v) {
                case 0: {
                    b5.a(b.a.B[0], null, b.a.B);
                    b.a.a(b5, 1);
                    this.a(this.o);
                    return;
                }
                case 1: {
                    String string3;
                    String string4;
                    f f2;
                    f f3;
                    String[] stringArray = new String[b.a.B.length + 1];
                    for (n2 = 0; n2 < b.a.B.length; ++n2) {
                        stringArray[n2] = b.a.B[n2].z;
                    }
                    stringArray[stringArray.length - 1] = b.a.p[2];
                    Object object = c.b.a(stringArray, (byte)0, (byte)2, true);
                    int n3 = ((j)object).f();
                    if (n3 != 1 || (f3 = (f2 = b.a.a(string4 = (string3 = ((b)object).d())))) == null || (object = (f.b)this.b()) == null) return;
                    ((f.b)object).a(f3, f3, null);
                    b.a.a((f.b)object, 1);
                    this.a(this.o);
                }
                default: {
                    return;
                }
            }
        }
        if (!string2.equals(b.a.p[29]) || (b2 = (f.b)this.b()) == null) return;
        b.a.a(b2, b2.b() ? 999 : 1);
        this.a(this.o);
    }

    protected void d() {
        this.k.b();
    }

    public final void e() {
        this.d = 0;
        this.e = 0;
        this.a(this.o);
        this.f();
    }

    private void a(byte by) {
        this.n = b.a.a(by, this.p);
        if (this.n == null) {
            this.l = null;
            this.m = null;
            this.b(0);
            return;
        }
        this.l = new f.b[this.n.length];
        this.m = new int[this.n.length];
        for (by = 0; by < this.n.length; by = (byte)(by + 1)) {
            this.l[by] = b.a.e(this.n[by]);
            this.m[by] = b.a.f(this.n[by]);
        }
        if (this.d < 0) {
            this.d = 0;
        } else if (this.d >= this.m.length) {
            this.d = this.m.length - 1;
        }
        this.b(this.l.length);
    }

    public final void a(k k2) {
    }

    public final void c(k k2) {
        d.a.e(k2);
    }

    public final void b(k k2) {
        k2.b();
    }
}

