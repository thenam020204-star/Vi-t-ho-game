/*
 * Decompiled with CFR 0.152.
 */
package b.e;

public final class b {
    private static long[] a = new long[256];

    public static int a(byte[] byArray, int n2, int n3) {
        long l2 = 0xFFFFFFFFL;
        for (int i2 = n2; i2 < n2 + n3; ++i2) {
            l2 = a[(int)((l2 ^ (long)byArray[i2]) & 0xFFL)] ^ l2 >> 8;
        }
        return (int)(l2 ^ 0xFFFFFFFFL);
    }

    static {
        for (int i2 = 0; i2 < 256; ++i2) {
            long l2 = i2;
            for (int i3 = 0; i3 < 8; ++i3) {
                if ((l2 & 1L) != 0L) {
                    l2 = 0xEDB88320L ^ l2 >> 1;
                    continue;
                }
                l2 >>= 1;
            }
            b.a[i2] = l2;
        }
    }
}

