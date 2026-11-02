/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package c;

import b.a;
import b.f;
import c.b;
import c.e;
import c.j;
import f.c;
import javax.microedition.lcdui.Graphics;

public class p
extends e {
    private static String[] a = new String[]{b.a.p[28], b.a.p[2]};
    private c[] g = null;
    private f h = null;

    public p(f f2) {
        this.h = f2;
    }

    protected final void a(Graphics graphics, int n2, int n3, int n4) {
        if (this.g == null || n2 < 0 || n2 >= this.g.length || this.g[n2] == null) {
            return;
        }
        graphics.drawString(this.g[n2].t, n3, n4, 20);
    }

    protected final void b(Graphics graphics, int n2, int n3, int n4) {
        if (this.g == null || n2 < 0 || n2 >= this.g.length || this.g[n2] == null) {
            return;
        }
        graphics.drawString(String.valueOf(this.g[n2].a[0]), n3, n4, 24);
    }

    protected final String a() {
        if (this.g == null || this.d < 0 || this.d >= this.g.length || this.g[this.d] == null) {
            return null;
        }
        return this.g[this.d].d();
    }

    protected final Object b() {
        if (this.g == null || this.d < 0 || this.d >= this.g.length) {
            return null;
        }
        return this.g[this.d];
    }

    protected void c() {
        int n2;
        Object object = this.b();
        if (object == null) {
            return;
        }
        object = (c)object;
        if ((((c)object).a[24] & 1) != 0 && (n2 = ((j)(object = c.b.a(a, (byte)0, (byte)2, true))).f()) == 1) {
            object = ((b)object).d();
            if (object != null && ((String)object).equals(b.a.p[28]) && (object = this.b()) != null) {
                object = (c)object;
                if (Math.abs(((c)object).a[0]) > this.h.c(4)) {
                    b b2 = c.b.a(b.a.p[51], 3000L);
                    b2.f();
                    return;
                }
                String[] stringArray = null;
                switch (((f.b)object).v) {
                    case 0: {
                        stringArray = new String[]{b.a.p[50], b.a.p[2]};
                        break;
                    }
                    case 1: {
                        stringArray = new String[b.a.B.length + 1];
                        for (int i2 = 0; i2 < b.a.B.length; ++i2) {
                            stringArray[i2] = b.a.B[i2].z;
                        }
                        stringArray[stringArray.length - 1] = b.a.p[2];
                        break;
                    }
                    default: {
                        return;
                    }
                }
                Object object2 = c.b.a(stringArray, (byte)0, (byte)2, true);
                int n3 = ((j)object2).f();
                if (n3 == 1) {
                    object2 = ((b)object2).d();
                    if (object2 != null) {
                        if (((String)object2).equals(b.a.p[50])) {
                            object2 = this.b();
                            if (object2 != null) {
                                c c2 = (c)object2;
                                if (c2.v == 0) {
                                    c2.a(this.h, null, b.a.B);
                                }
                            }
                            return;
                        }
                        for (n3 = 0; n3 < b.a.B.length; ++n3) {
                            Object object3;
                            if (!((String)object2).equals(b.a.B[n3].z)) continue;
                            object2 = b.a.B[n3];
                            if (object2 != null && (object3 = this.b()) != null) {
                                object3 = (c)object3;
                                if (((f.b)object3).v == 1) {
                                    ((f.b)object3).a(this.h, (f)object2, b.a.B);
                                }
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    protected void d() {
        this.k.b();
    }

    public final void e() {
        p p2 = this;
        this.g = p2.h.z();
        p2.b(p2.g == null ? 0 : p2.g.length);
        this.f();
    }
}

