/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.midlet.MIDlet
 */
package a.a.a;

import a.a.a.a.b;
import java.util.Random;
import javax.microedition.midlet.MIDlet;

public final class a {
    private static String[][] a = new String[2][];
    private static String[][] b = new String[2][];
    private static String c = "Fr";
    private static String d = "Sec";
    private static String e = "N7610";
    private static String f = "Idx";
    private static String g = "0";
    private static int[][] h = new int[2][];

    public a(MIDlet mIDlet) {
        a.a.a.a.a(mIDlet);
    }

    private static final String[] a(String string, String string2) {
        int n2;
        if (string == null) {
            return null;
        }
        if (string.length() <= 0) {
            return new String[]{string};
        }
        if (string2.length() <= 0) {
            return new String[]{string};
        }
        String[] stringArray = null;
        String string3 = string;
        int[] nArray = new int[string3.length()];
        int[] nArray2 = new int[string3.length()];
        int n3 = 0;
        boolean bl = false;
        int n4 = 0;
        int n5 = 0;
        do {
            n2 = 0;
            n5 = n4 + string2.length();
            if (n5 <= string3.length() && string3.substring(n4, n5).equals(string2)) {
                n2 = 1;
            }
            if (n2 != 0) {
                if (bl) {
                    bl = false;
                    nArray2[n3++] = n4;
                }
                n4 = n5;
                continue;
            }
            if (!bl) {
                bl = true;
                nArray[n3] = n4;
            }
            ++n4;
        } while (n4 < string3.length());
        if (bl) {
            nArray2[n3++] = string3.length();
        }
        if (n3 > 0) {
            stringArray = new String[n3];
            n2 = 0;
            while (n2 < n3) {
                stringArray[n2] = string.substring(nArray[n2], nArray2[n2]);
                ++n2;
            }
        }
        return stringArray;
    }

    private static final String a(String string) {
        try {
            System.out.println("step 0:aStr = " + string);
            Object object = null;
            object = new b();
            ((b)object).a("a4s3d5f1");
            object = ((b)object).b(string.substring(0, string.length() - 32));
            System.out.println("step 1:strDes = " + (String)object);
            int n2 = 0;
            int n3 = 0;
            while (n3 < ((String)object).length()) {
                if (!(((String)object).charAt(n3) >= 'a' && ((String)object).charAt(n3) <= 'z' || ((String)object).charAt(n3) >= 'A' && ((String)object).charAt(n3) <= 'Z' || ((String)object).charAt(n3) >= '0' && ((String)object).charAt(n3) <= '9' || ((String)object).charAt(n3) == ';')) {
                    n2 = n3;
                    break;
                }
                ++n3;
            }
            object = ((String)object).substring(0, n2);
            System.out.println("step 2:sResult = " + (String)object);
            if (new a.a.a.a.a().a((String)object).equals(string.substring(string.length() - 32))) {
                return object;
            }
            return null;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private static void a(MIDlet var0) {
        try {
            try {
                var1_2 = var0 /* !! */ .getAppProperty("ChannelFirst");
                if (var1_2 != null) {
                    a.a.a.a.c = var1_2;
                }
                if ((var1_2 = var0 /* !! */ .getAppProperty("ChannelSecond")) != null) {
                    a.a.a.a.d = var1_2;
                }
                if ((var1_2 = var0 /* !! */ .getAppProperty("InterChannel")) != null) {
                    a.a.a.a.f = var1_2;
                }
                if ((var1_2 = var0 /* !! */ .getAppProperty("Term")) != null) {
                    a.a.a.a.e = var1_2;
                }
                if ((var1_2 = var0 /* !! */ .getAppProperty("QQ")) != null) {
                    a.a.a.a.g = var1_2;
                }
                a.a.a.a.a(var0 /* !! */ .getAppProperty("FreeCode"));
                a.a.a.a.a(var0 /* !! */ .getAppProperty("FreeDest"));
                var1_2 = a.a.a.a.a(var0 /* !! */ .getAppProperty("SmsCode1"));
                var2_5 = a.a.a.a.a(var0 /* !! */ .getAppProperty("SmsCode2"));
                var3_8 = a.a.a.a.a(var0 /* !! */ .getAppProperty("SmsDest1"));
                var0 /* !! */  = a.a.a.a.a(var0 /* !! */ .getAppProperty("SmsDest2"));
                a.a.a.a.a[0] = a.a.a.a.a(var1_2, ";");
                a.a.a.a.a[1] = a.a.a.a.a(var2_5, ";");
                a.a.a.a.b[0] = a.a.a.a.a(var3_8, ";");
                a.a.a.a.b[1] = a.a.a.a.a((String)var0 /* !! */ , ";");
            }
            catch (NullPointerException v0) {
                v0.printStackTrace();
            }
        }
        finally {
            var1_4 = 1;
            if (a.a.a.a.a[0] == null || a.a.a.a.b[0] == null) {
                var1_4 = 0;
            } else if (a.a.a.a.a[0].length != a.a.a.a.b[0].length) {
                var1_4 = 0;
            }
            if (var1_4 == 0) {
                a.a.a.a.a[0] = null;
                a.a.a.a.b[0] = null;
                a.a.a.a.a[0] = new String[2];
                a.a.a.a.b[0] = new String[2];
                a.a.a.a.a[0][0] = "62";
                a.a.a.a.a[0][1] = "63";
                a.a.a.a.b[0][0] = "106662267";
                a.a.a.a.b[0][1] = "106662269";
            }
            var1_4 = 1;
            if (a.a.a.a.a[1] == null || a.a.a.a.b[1] == null) {
                var1_4 = 0;
            } else if (a.a.a.a.a[1].length != a.a.a.a.b[1].length) {
                var1_4 = 0;
            }
            if (var1_4 == 0) {
                a.a.a.a.a[1] = null;
                a.a.a.a.b[1] = null;
                a.a.a.a.a[1] = new String[1];
                a.a.a.a.b[1] = new String[1];
                a.a.a.a.a[1][0] = "JG";
                a.a.a.a.b[1][0] = "1066170073";
            }
            var1_4 = 0;
            ** while (var1_4 < 2)
        }
lbl-1000:
        // 1 sources

        {
            a.a.a.a.h[var1_4] = new int[a.a.a.a.a[var1_4].length];
            var2_7 = 0;
            while (var2_7 < a.a.a.a.a[var1_4].length) {
                a.a.a.a.h[var1_4][var2_7] = 0;
                ++var2_7;
            }
            ++var1_4;
            continue;
        }
lbl70:
        // 1 sources

    }

    public static String[] a(int n2) {
        Object var0_1 = null;
        int n3 = 0;
        int n4 = 100000;
        boolean bl = true;
        int n5 = 0;
        while (n5 < h[1].length) {
            if (h[1][n5] <= n4) {
                n3 = n5;
                n4 = h[1][n5];
            }
            if (h[1][n5] != 0) {
                bl = false;
            }
            ++n5;
        }
        if (bl) {
            n3 = Math.abs(new Random().nextInt()) % 100 % a[1].length;
            System.out.println("Random minId :" + n3);
        }
        if (n3 < 0 || n3 >= a[1].length) {
            n3 = 0;
        }
        String[] stringArray = new String[2];
        String[] stringArray2 = stringArray;
        stringArray[0] = a[1][n3];
        stringArray2[1] = b[1][n3];
        int[] nArray = h[1];
        int n6 = n3;
        nArray[n6] = nArray[n6] + 1;
        return stringArray2;
    }

    public static String a() {
        return c;
    }

    public static String b() {
        return d;
    }

    public static String c() {
        return e;
    }

    public static String d() {
        return f;
    }

    public static String e() {
        return g;
    }
}

