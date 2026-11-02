/*
 * Decompiled with CFR 0.152.
 */
package a.a.a.a;

public final class b {
    private byte[] a;

    public final byte[] a(String string) {
        int n2 = string.length();
        if (n2 >= 16) {
            string = string.substring(0, 16);
        } else {
            int n3 = 0;
            while (n3 < 16 - n2) {
                string = string.concat("0");
                ++n3;
            }
        }
        this.a = string.getBytes();
        return this.a;
    }

    public final String b(String string) {
        String string2 = "";
        try {
            Object object = new String(((b)object).a(((b)object).a, b.b(string.getBytes()), false));
            int n2 = ((String)object).length();
            object = ((String)object).substring(0, n2 - 6);
            return object;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return string2;
    }

    private static int a(byte[] byArray, int n2) {
        return (byArray[n2] << 8 & 0xFF00) + (byArray[n2 + 1] & 0xFF);
    }

    private static void a(int n2, byte[] byArray, int n3) {
        byArray[n3] = (byte)(n2 >>> 8);
        byArray[n3 + 1] = (byte)n2;
    }

    private static int a(int n2, int n3) {
        if (n2 == 0) {
            n2 = 65537 - n3;
        } else if (n3 == 0) {
            n2 = 65537 - n2;
        } else {
            n2 *= n3;
            n2 = n3 - n2 + ((n3 = n2 & 0xFFFF) < (n2 >>>= 16) ? 1 : 0);
        }
        return n2 & 0xFFFF;
    }

    private static int[] a(byte[] byArray) {
        int[] nArray = new int[52];
        if (byArray.length < 16) {
            byte[] byArray2 = new byte[16];
            System.arraycopy(byArray, 0, byArray2, byArray2.length - byArray.length, byArray.length);
            byArray = byArray2;
        }
        int n2 = 0;
        while (n2 < 8) {
            nArray[n2] = b.a(byArray, n2 << 1);
            ++n2;
        }
        n2 = 8;
        while (n2 < 52) {
            nArray[n2] = (n2 & 7) < 6 ? ((nArray[n2 - 7] & 0x7F) << 9 | nArray[n2 - 6] >> 7) & 0xFFFF : ((n2 & 7) == 6 ? ((nArray[n2 - 7] & 0x7F) << 9 | nArray[n2 - 14] >> 7) & 0xFFFF : ((nArray[n2 - 15] & 0x7F) << 9 | nArray[n2 - 14] >> 7) & 0xFFFF);
            ++n2;
        }
        return nArray;
    }

    private static int a(int n2) {
        if (n2 < 2) {
            return n2;
        }
        int n3 = 1;
        int n4 = 65537 / n2;
        int n5 = 65537 % n2;
        while (n5 != 1) {
            int n6 = n2 / n5;
            n3 = n3 + n4 * n6 & 0xFFFF;
            if ((n2 %= n5) == 1) {
                return n3;
            }
            n6 = n5 / n2;
            n5 %= n2;
            n4 = n4 + n3 * n6 & 0xFFFF;
        }
        return 1 - n4 & 0xFFFF;
    }

    private static byte[] b(byte[] byArray, int n2) {
        int n3 = byArray.length;
        n2 -= n3 % n2;
        int n4 = n3 + n2;
        byte[] byArray2 = new byte[n4];
        System.arraycopy(byArray, 0, byArray2, 0, n3);
        int n5 = n3;
        while (n5 < n4) {
            byArray2[n5] = (byte)n2;
            ++n5;
        }
        return byArray2;
    }

    private byte[] a(byte[] byArray, byte[] byArray2, boolean bl) {
        byArray = b.b(byArray, 16);
        byArray2 = b.b(byArray2, 8);
        int byArray3 = byArray2.length;
        int n2 = byArray3 / 8;
        byte[] byArray4 = new byte[byArray3];
        int n3 = 0;
        while (n3 < n2) {
            int n4;
            int n5;
            int n6;
            int n7;
            int n8;
            int[] nArray;
            Object n12 = new byte[16];
            byte[] n10 = new byte[8];
            System.arraycopy(byArray, 0, n12, 0, 16);
            System.arraycopy(byArray2, n3 << 3, n10, 0, 8);
            int byArray7 = 0;
            byte[] byArray5 = n10;
            n10 = n12;
            n12 = this;
            byte[] byArray6 = new byte[8];
            byte[] n11 = n10;
            int byArray9 = byArray7;
            if (byArray9 != 0) {
                nArray = b.a(n11);
            } else {
                int[] nArray2 = b.a(n11);
                n8 = 52;
                n7 = 0;
                int[] nArray3 = new int[52];
                ++n7;
                byArray9 = b.a(nArray2[0]);
                ++n7;
                int n9 = nArray2[1];
                n6 = 0 - n9 & 0xFFFF;
                ++n7;
                n9 = nArray2[2];
                n5 = 0 - n9 & 0xFFFF;
                ++n7;
                n4 = b.a(nArray2[3]);
                --n8;
                nArray3[51] = n4;
                --n8;
                nArray3[50] = n5;
                --n8;
                nArray3[49] = n6;
                --n8;
                nArray3[48] = byArray9;
                int n13 = 1;
                while (n13 < 8) {
                    byArray9 = nArray2[n7++];
                    n6 = nArray2[n7++];
                    nArray3[--n8] = n6;
                    nArray3[--n8] = byArray9;
                    byArray9 = b.a(nArray2[n7++]);
                    n9 = nArray2[n7++];
                    n6 = 0 - n9 & 0xFFFF;
                    n9 = nArray2[n7++];
                    n5 = 0 - n9 & 0xFFFF;
                    n4 = b.a(nArray2[n7++]);
                    nArray3[--n8] = n4;
                    nArray3[--n8] = n6;
                    nArray3[--n8] = n5;
                    nArray3[--n8] = byArray9;
                    ++n13;
                }
                byArray9 = nArray2[n7++];
                n6 = nArray2[n7++];
                nArray3[--n8] = n6;
                nArray3[--n8] = byArray9;
                byArray9 = b.a(nArray2[n7++]);
                n9 = nArray2[n7++];
                n6 = 0 - n9 & 0xFFFF;
                n9 = nArray2[n7++];
                n5 = 0 - n9 & 0xFFFF;
                n4 = b.a(nArray2[n7]);
                nArray3[--n8] = n4;
                nArray3[--n8] = n5;
                nArray3[--n8] = n6;
                nArray3[--n8] = byArray9;
                nArray = nArray3;
            }
            n12 = nArray;
            byte[] n9 = byArray6;
            byte[] byArray8 = byArray5;
            n12 = nArray;
            int nArray2 = 0;
            n8 = b.a(byArray8, 0);
            n7 = b.a(byArray8, 2);
            int nArray3 = b.a(byArray8, 4);
            int byArray42 = b.a(byArray8, 6);
            n6 = 0;
            while (n6 < 8) {
                n8 = b.a(n8, (int)n12[nArray2++]);
                n7 = n7 + n12[nArray2++] & 0xFFFF;
                nArray3 = nArray3 + n12[nArray2++] & 0xFFFF;
                byArray42 = b.a(byArray42, (int)n12[nArray2++]);
                n5 = n7;
                n4 = nArray3;
                nArray3 ^= n8;
                n7 ^= byArray42;
                nArray3 = b.a(nArray3, (int)n12[nArray2++]);
                n7 = b.a(n7 + nArray3 & 0xFFFF, (int)n12[nArray2++]);
                nArray3 = nArray3 + n7 & 0xFFFF;
                n8 ^= n7;
                byArray42 ^= nArray3;
                n7 ^= n4;
                nArray3 ^= n5;
                ++n6;
            }
            b.a(b.a(n8, (int)n12[nArray2++]), n9, 0);
            b.a(nArray3 + n12[nArray2++], n9, 2);
            b.a(n7 + n12[nArray2++], n9, 4);
            b.a(b.a(byArray42, (int)n12[nArray2]), n9, 6);
            System.arraycopy(byArray6, 0, byArray4, n3 << 3, 8);
            ++n3;
        }
        return byArray4;
    }

    private static byte[] b(byte[] byArray) {
        if (byArray.length % 2 != 0) {
            throw new IllegalArgumentException("Length Not Double Num");
        }
        byte[] byArray2 = new byte[byArray.length / 2];
        int n2 = 0;
        while (n2 < byArray.length) {
            String string = new String(byArray, n2, 2);
            byArray2[n2 / 2] = (byte)Integer.parseInt(string, 16);
            n2 += 2;
        }
        return byArray2;
    }
}

