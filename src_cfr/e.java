/*
 * Decompiled with CFR 0.152.
 */
/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class e {
    byte[] a;
    int a;
    short a;
    byte a;
    private byte[] b;

    private static int a(byte[] byArray, int n2, byte[] byArray2, int n3, short n4, int n5, int n6) {
        int n7;
        int n8 = n7 = n6 == 0 ? n4 << 1 : n4;
        if (n5 == n6 || n5 == 1 && n6 != 0) {
            System.arraycopy(byArray, n2, byArray2, n3, n7);
        } else {
            for (n5 = n2; n5 < n2 + n4; ++n5) {
                short s2 = (short)(byArray[n5] & 0xFF);
                n6 = s2;
                if (s2 > 127) {
                    n6 = o.b((short)n6);
                }
                byArray2[n3++] = (byte)(n6 >> 8);
                byArray2[n3++] = (byte)n6;
            }
        }
        return n7;
    }

    public e() {
    }

    public e(String string) {
        this.a = 1;
        this.a = 0;
        this.a = (short)string.length();
        this.a = new byte[this.a];
        for (int i2 = 0; i2 < this.a; ++i2) {
            this.a[i2] = (byte)string.charAt(i2);
            string.charAt(i2);
        }
    }

    public e(int n2) {
        this.a = (byte)-127;
        this.a = new byte[64];
        this.a = 0;
        this.a = 0;
    }

    private boolean a() {
        return (this.a & 0xFFFFFF80) != 0;
    }

    public final e a(String string) {
        if (!this.a()) {
            return this;
        }
        short s2 = this.a;
        this.a = (short)(this.a + string.length());
        for (int i2 = 0; i2 < string.length(); ++i2) {
            short s3 = string.charAt(i2);
            int n2 = s2 + i2;
            e e2 = this;
            if (!e2.a()) continue;
            Object var7_8 = null;
            byte by = (byte)(e2.a & 0x7F);
            if ((s3 = (short)s3) > 127 && by != 0) {
                s3 = o.a(s3);
            }
            n2 = e2.a + (by == 0 ? n2 << 1 : n2);
            if (by == 0) {
                e2.a[n2++] = (byte)(s3 >> 8);
            }
            e2.a[n2] = (byte)s3;
        }
        return this;
    }

    public final e a(int n2) {
        short s2;
        if (!this.a()) {
            return this;
        }
        if (this.b == null) {
            this.b = new byte[11];
        }
        short s3 = 10;
        long l2 = n2;
        if (n2 < 0) {
            l2 = -l2;
        }
        for (s2 = 10; s2 >= 0; --s2) {
            this.b[s2] = (byte)(l2 % 10L | 0x30L);
            l2 /= 10L;
            if (this.b[s2] == 48) continue;
            s3 = s2;
        }
        if (n2 < 0) {
            this.b[--s3] = 45;
        }
        s2 = (short)(11 - s3);
        Object var1_2 = null;
        byte by = (byte)(this.a & 0x7F);
        int n3 = this.a + (by == 0 ? this.a << 1 : this.a);
        e.a(this.b, s3, this.a, n3, s2, 1, by);
        this.a = (short)(this.a + s2);
        return this;
    }

    public final char a(int n2) {
        short s2 = this.a(n2);
        n2 = s2;
        if (s2 > 127 && (this.a & 0x7F) != 0) {
            return (char)o.b((short)n2);
        }
        return (char)n2;
    }

    final short a(int n2) {
        if ((this.a & 0x7F) == 0) {
            n2 = this.a + (n2 << 1);
            return (short)((this.a[n2] & 0xFF) << 8 | this.a[n2 + 1] & 0xFF);
        }
        return (short)(this.a[this.a + n2] & 0xFF);
    }

    public final e a(e e2) {
        Object var3_3 = null;
        byte by = (byte)(this.a & 0x7F);
        byte by2 = (byte)(e2.a & 0x7F);
        e e3 = new e();
        new e().a = (short)(this.a + e2.a);
        e3.a = 0;
        e3.a = by == by2 || by2 == 1 ? by : (byte)0;
        e3.a = new byte[e3.a == 0 ? e3.a << 1 : e3.a];
        int n2 = e.a(this.a, this.a, e3.a, 0, this.a, by, e3.a);
        e.a(e2.a, e2.a, e3.a, n2, e2.a, by2, e3.a);
        return e3;
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer(this.a);
        for (int i2 = 0; i2 < this.a; ++i2) {
            stringBuffer.append(this.a(i2));
        }
        return stringBuffer.toString();
    }

    public final int a() {
        return this.a;
    }

    public final int b() {
        return this.a & 0x7F;
    }

    public final e b(int n2) {
        return this.a(new e("" + n2));
    }

    public final e b(String string) {
        return this.a(new e(string));
    }

    public final e c(String object) {
        short s2;
        int n2;
        int n3;
        e[] eArray = new e[]{new e((String)object)};
        object = this;
        byte by = (byte)(((e)object).a & 0x7F);
        e e2 = new e();
        new e().a = by;
        e2.a = 0;
        int n4 = 0;
        for (n3 = 0; n3 < eArray.length; ++n3) {
            if (eArray[n3] == null) continue;
            Object var5_6 = null;
            n4 += eArray[n3].a - 2;
            n2 = eArray[n3].a & 0x7F;
            if (n2 == e2.a || n2 == 1) continue;
            e2.a = 0;
        }
        int n5 = n3 = e2.a == 0 ? 1 : 0;
        if (n4 < 0) {
            n4 = 0;
        }
        e2.a = new byte[n4 + ((e)object).a << n3];
        n2 = 0;
        n4 = 0;
        int n6 = by == 0 ? 1 : 0;
        for (int i2 = 0; i2 < ((e)object).a; ++i2) {
            int n7 = ((e)object).a + (i2 << n6);
            s2 = by == 0 ? (short)((((e)object).a[n7] & 0xFF) << 8 | ((e)object).a[n7 + 1] & 0xFF) : (short)(((e)object).a[n7] & 0xFF);
            if (s2 != 92) continue;
            s2 = (short)(i2 - n4);
            if (n2 + (s2 << n3) > e2.a.length) {
                byte[] byArray = new byte[n2 + (s2 << n3)];
                System.arraycopy(e2.a, 0, byArray, 0, n2);
                e2.a = byArray;
            }
            n2 += e.a(((e)object).a, ((e)object).a + (n4 << n6), e2.a, n2, s2, by, e2.a);
            n4 = i2++;
            if (i2 >= ((e)object).a) continue;
            s2 = by == 0 ? (short)((((e)object).a[n7] & 0xFF) << 8 | ((e)object).a[n7 + 1] & 0xFF) : (short)(((e)object).a[n7 += 1 << n6] & 0xFF);
            if (s2 == 92) {
                n4 = i2;
                continue;
            }
            if (s2 < 48 || s2 > 90) continue;
            int n8 = s2 - 48;
            if (n2 + (eArray[n8].a << n3) > e2.a.length) {
                byte[] byArray = new byte[n2 + (eArray[n8].a << n3)];
                System.arraycopy(e2.a, 0, byArray, 0, n2);
                e2.a = byArray;
            }
            n2 += e.a(eArray[n8].a, eArray[n8].a, e2.a, n2, eArray[n8].a, (byte)(eArray[n8].a & 0x7F), e2.a);
            n4 = i2 + 1;
        }
        s2 = (short)(((e)object).a - n4);
        if (n2 + (s2 << n3) > e2.a.length) {
            byte[] byArray = new byte[n2 + (s2 << n3)];
            System.arraycopy(e2.a, 0, byArray, 0, n2);
            e2.a = byArray;
        }
        if ((n2 += e.a(((e)object).a, ((e)object).a + (n4 << n6), e2.a, n2, s2, by, e2.a)) < e2.a.length) {
            byte[] byArray = new byte[n2];
            System.arraycopy(e2.a, 0, byArray, 0, n2);
            e2.a = byArray;
        }
        e2.a = (short)(n2 >> n3);
        return e2;
    }

    public final void a() {
        if (!this.a()) {
            return;
        }
        this.a = 0;
    }
}

