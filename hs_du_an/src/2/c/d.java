/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package c;

import b.a;
import c.b;
import c.e;
import c.j;
import javax.microedition.lcdui.Graphics;

public final class d
extends e {
    private static String[] a = new String[]{b.a.p[54], b.a.p[2]};
    private f.b[] g = null;
    private int[] h = null;

    protected final void a(Graphics graphics, int n2, int n3, int n4) {
        if (this.g == null || n2 < 0 || n2 >= this.g.length) {
            return;
        }
        graphics.drawString(this.g[n2].t, n3, n4, 20);
    }

    protected final void b(Graphics graphics, int n2, int n3, int n4) {
        if (this.g == null || n2 < 0 || n2 >= this.g.length) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.g[n2].u / 2);
        stringBuffer.append("(");
        stringBuffer.append(this.h[n2]);
        stringBuffer.append(")");
        graphics.drawString(stringBuffer.toString(), n3, n4, 24);
    }

    protected final String a() {
        if (this.g == null || this.d < 0 || this.d >= this.g.length || this.g[this.d] == null) {
            return null;
        }
        return this.g[this.d].d();
    }

    private Object b() {
        if (this.g == null || this.d < 0 || this.d >= this.g.length) {
            return null;
        }
        return this.g[this.d];
    }

    protected final void c() {
        if (((d)((Object)b2)).b() == null) {
            return;
        }
        Object object = c.b.a(a, (byte)0, (byte)2, true);
        int n2 = ((j)object).f();
        if (n2 == 1) {
            object = ((b)object).d();
            if (object != null && ((String)object).equals(b.a.p[54])) {
                object = (f.b)((d)((Object)b2)).b();
                n2 = b.a.a((f.b)object, true);
                if (n2 == -1) {
                    b b2 = c.b.a(b.a.p[47], 3000L);
                    b2.f();
                    return;
                }
                b.a.D += ((f.b)object).u / 2;
                b.a.d(n2, 1);
                int n3 = ((e)((Object)b2)).d;
                ((d)((Object)b2)).h[n3] = ((d)((Object)b2)).h[n3] - 1;
                if (((d)((Object)b2)).h[((e)((Object)b2)).d] == 0) {
                    super.g();
                    if (((e)((Object)b2)).d >= ((d)((Object)b2)).g.length) {
                        ((e)((Object)b2)).d = ((d)((Object)b2)).g.length - 1;
                    }
                }
            }
        }
    }

    protected final void d() {
        this.k.b();
    }

    public final void e() {
        this.g();
        this.f();
    }

    private void g() {
        int[] nArray = b.a.a((byte)0, true);
        if (nArray == null) {
            this.g = null;
            this.h = null;
            this.b(0);
            return;
        }
        this.g = new f.b[nArray.length];
        this.h = new int[nArray.length];
        for (int i2 = 0; i2 < nArray.length; ++i2) {
            this.g[i2] = b.a.e(nArray[i2]);
            this.h[i2] = b.a.f(nArray[i2]);
        }
        this.b(this.g.length);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        super.a(graphics, n2, n3);
        graphics.drawImage(b.a.Q, n2 + this.i - String.valueOf(b.a.D).length() * b.a.H, n3 - b.a.M.getHeight() / 2, 40);
        e.a.b.a(graphics, b.a.G, b.a.D, n2 + this.i - String.valueOf(b.a.D).length() * b.a.H, n3 - b.a.M.getHeight() / 2 - b.a.G.getHeight());
    }
}

