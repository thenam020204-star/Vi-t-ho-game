/*
 * Decompiled with CFR 0.152.
 */
import com.ea.sdk.SDKMIDlet;
import java.io.IOException;
import java.io.InputStream;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class d
extends InputStream {
    private static boolean a = false;
    private static int a;
    private static int b;
    private static int c;
    private static int[] a;
    private static int[][] a;
    private static int d;
    private static boolean[][] a;
    private static int[] b;
    private static int[][] b;
    private static InputStream a;
    private static int e;
    private static int f;
    private static int g;
    private static final byte[] a;
    private InputStream b;
    private int h = 0;
    private int i;
    private int j;
    private int k;
    private int l;
    private String a;

    private static int a(InputStream inputStream, int n2) {
        int n3 = 0;
        n2 <<= 3;
        for (int i2 = 0; i2 < n2; i2 += 8) {
            n3 |= (inputStream.read() & 0xFF) << i2;
        }
        return n3;
    }

    private static void b() {
        InputStream inputStream = SDKMIDlet.a.getClass().getResourceAsStream("/index");
        try {
            int n2;
            int n3;
            d.a(inputStream, 1);
            a = d.a(inputStream, 2);
            b = d.a(inputStream, 1);
            c = d.a(inputStream, 1);
            a = new int[a];
            a = new int[a][c + 1];
            for (n3 = 0; n3 < a; ++n3) {
                d.a[n3] = d.a(inputStream, 2);
                for (n2 = 0; n2 < c + 1; ++n2) {
                    d.a[n3][n2] = d.a(inputStream, b);
                }
                d.a(inputStream, b);
            }
            d = d.a(inputStream, 2);
            n3 = c / 8;
            if (c % 8 > 0) {
                ++n3;
            }
            a = new boolean[d][c];
            b = new int[d];
            b = new int[d][];
            for (int i2 = 0; i2 < d; ++i2) {
                int n4;
                int n5 = d.a(inputStream, n3);
                n2 = 0;
                for (n4 = 0; n4 < c; ++n4) {
                    boolean bl = d.a[i2][n4] = (n5 & 1 << n4) != 0;
                    if (!a[i2][n4]) continue;
                    ++n2;
                }
                d.b[i2] = d.a(inputStream, b);
                d.b[i2] = new int[n2];
                n4 = 0;
                for (n2 = 0; n2 < c; ++n2) {
                    if (!a[i2][n2]) continue;
                    d.b[i2][n4++] = d.a(inputStream, b);
                }
            }
        }
        catch (IOException iOException) {}
        a = true;
    }

    private static int[] a(int n2) {
        int n3;
        int n4 = b[n2];
        int n5 = 0;
        for (n3 = 0; n3 < a; ++n3) {
            if (a[n3] < n2 || n3 <= 0 || a[n3 - 1] >= n2) continue;
            n5 = n3;
            break;
        }
        n3 = 0;
        if (n2 == a[n5]) {
            n3 = a[n5][0] - n4;
        } else if (++n2 < d) {
            n3 = b[n2] - n4;
        }
        return new int[]{n5, n4, n3, 0};
    }

    public static void a() {
        if (a != null) {
            try {
                a.close();
            }
            catch (IOException iOException) {}
            a = null;
        }
    }

    public static byte[] a(int n2) {
        try {
            d d2 = new d(n2);
            byte[] byArray = null;
            byArray = new byte[d2.j];
            d2.read(byArray);
            d2.close();
            return byArray;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    public static h a(int n2) {
        try {
            d d2 = new d(n2);
            h h2 = null;
            h2 = o.a(d2);
            d2.close();
            return h2;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public static h a(int n2, int n3) {
        return d.a(n2, new int[]{n3})[0];
    }

    public static final h[] a(int n2, int[] nArray) {
        try {
            int n3;
            d d2 = new d(n2);
            byte[] byArray = null;
            byte[] byArray2 = new byte[d2.j];
            d2.read(byArray2);
            d2.close();
            h[] hArray = new h[nArray.length];
            for (n3 = 0; n3 < hArray.length; ++n3) {
                if (nArray[n3] != -1) continue;
                hArray[n3] = o.a(byArray2, 0, byArray2.length);
                break;
            }
            for (n3 = 0; n3 < hArray.length; ++n3) {
                if (nArray[n3] == -1) continue;
                d d3 = new d(nArray[n3]);
                byArray = new byte[d3.j];
                d3.read(byArray);
                d3.close();
                for (int i2 = 0; i2 < byArray2.length - 4 && (byArray2[i2] != a[0] || byArray2[i2 + 1] != a[1] || byArray2[i2 + 2] != a[2] || byArray2[i2 + 3] != a[3]); ++i2) {
                }
                System.arraycopy(byArray, 0, byArray2, i2 -= 4, byArray.length);
                hArray[n3] = o.a(byArray2, 0, byArray2.length);
            }
            return hArray;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    public d(int n2) {
        if (!a) {
            d.b();
        }
        int[] nArray = d.a(n2);
        this.k = nArray[0];
        this.i = nArray[1];
        this.j = nArray[2];
        this.l = nArray[3];
        this.a = "/df" + this.k + "_" + this.l;
        if (a != null && this.k == e && g == this.l && f <= this.i) {
            this.b = a;
            a = null;
            o.a(this.b, (long)(this.i - f));
        } else {
            this.b = SDKMIDlet.a.getClass().getResourceAsStream(this.a);
            if (this.i > 0) {
                o.a(this.b, (long)this.i);
            }
        }
        this.h = 0;
    }

    public final void close() {
        if (a != null && a != this.b) {
            a.close();
        }
        a = this.b;
        e = this.k;
        f = this.i + this.h;
        g = this.l;
    }

    public final int read() {
        if (this.h >= this.j) {
            return -1;
        }
        ++this.h;
        return this.b.read();
    }

    public final int read(byte[] byArray) {
        return this.read(byArray, 0, byArray.length);
    }

    public final int read(byte[] byArray, int n2, int n3) {
        if (this.h >= this.j) {
            return -1;
        }
        if (this.h + n3 > this.j) {
            n3 = this.j - this.h;
        }
        int n4 = 0;
        n4 = o.a(this.b, byArray, n2, n3);
        if (n4 != -1) {
            this.h += n4;
        }
        return n4;
    }

    public final short a() {
        return (short)(this.read() & 0xFF | (this.read() & 0xFF) << 8);
    }

    public final int a() {
        return this.read() | this.read() << 8 | this.read() << 16 | this.read() << 24;
    }

    public final int available() {
        return this.j - this.h;
    }

    public final long skip(long l2) {
        long l3 = o.a(this.b, l2);
        if (l3 > 0L) {
            this.h = (int)((long)this.h + l3);
        }
        return l3;
    }

    public final void reset() {
        this.b = SDKMIDlet.a.getClass().getResourceAsStream(this.a);
        o.a(this.b, (long)this.i);
        this.h = 0;
    }

    static {
        a = null;
        e = -1;
        f = -1;
        g = -1;
        a = new byte[]{80, 76, 84, 69};
    }
}

