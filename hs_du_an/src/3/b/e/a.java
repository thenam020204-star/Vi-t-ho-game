/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Font
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 *  javax.microedition.rms.RecordStore
 */
package b.e;

import b.e.b;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.rms.RecordStore;

public final class a {
    public static final Font a = Font.getFont((int)0, (int)0, (int)8);
    public static final int b = a.getHeight();
    private static Random c = new Random();
    private static int[] d = new int[]{0xFF0000, 65280};
    private static short[] e = null;
    private static int f = 360;
    private static int g = 0;

    public static String[] a(String object, Font font, int n2) {
        if (object == null || font == null) {
            return new String[]{"BUG split: " + (String)object};
        }
        StringBuffer stringBuffer = new StringBuffer((String)object);
        char[] cArray = ((String)object).toCharArray();
        object = cArray;
        int n3 = cArray.length;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        for (int i2 = 0; i2 < n3; ++i2) {
            if (object[i2] == 10) {
                ++n5;
                n4 = 0;
                continue;
            }
            if ((n4 += font.charWidth((char)object[i2]) + 1) <= n2) continue;
            stringBuffer.insert(i2 + n6, '\n');
            ++n5;
            ++n6;
            n4 = font.charWidth((char)object[i2]) + 1;
        }
        stringBuffer.append('\n');
        return b.e.a.a(stringBuffer.toString(), '\n', ++n5);
    }

    private static String[] a(String string, char c2, int n2) {
        if (n2 <= 0) {
            n2 = 1;
            c2 = (char)-1;
            while (true) {
                int n3 = string.indexOf(10, c2 + '\u0001');
                c2 = (char)n3;
                if (n3 < 0) break;
                ++n2;
            }
            if (n2 < 0) {
                return new String[]{string};
            }
        }
        String[] stringArray = new String[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            int n4 = string.indexOf(10);
            if (n4 == -1) {
                stringArray[i2] = string;
                continue;
            }
            stringArray[i2] = string.substring(0, n4);
            string = string.substring(n4 + 1);
        }
        return stringArray;
    }

    public static void a(long l2) {
        try {
            Thread.sleep(l2);
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public static void a(InputStream inputStream) {
        if (inputStream == null) {
            return;
        }
        try {
            inputStream.close();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static void a(OutputStream outputStream) {
        try {
            outputStream.close();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static byte[] a(String object) {
        if (object == null) {
            return null;
        }
        object = "".getClass().getResourceAsStream((String)object);
        if (object == null) {
            return null;
        }
        Object object2 = new byte[1000];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n2 = 0;
        try {
            while ((n2 = ((InputStream)object).read((byte[])object2)) != -1) {
                byteArrayOutputStream.write((byte[])object2, 0, n2);
            }
            object2 = byteArrayOutputStream.toByteArray();
            return object2;
        }
        catch (Exception exception) {
            object2 = exception;
            exception.printStackTrace();
            return null;
        }
        finally {
            b.e.a.a((InputStream)object);
            b.e.a.a(byteArrayOutputStream);
        }
    }

    public static void a(Graphics graphics, Image image, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        if (image == null) {
            return;
        }
        graphics.drawRegion(image, n2, n3, n4, n5, n6, n7, n8, n9);
    }

    public static boolean a(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        return n2 + n4 > n6 && n2 < n6 + n8 && n3 + n5 > n7 && n3 < n7 + n9;
    }

    public static int a(int n2, int n3, int n4) {
        return Math.abs(n2 - n3) + Math.abs(n3 - n4);
    }

    public static boolean a(int[] nArray, int[] nArray2) {
        return nArray[0] >= nArray2[0] && nArray[1] >= nArray2[1] && nArray[0] + nArray[2] <= nArray2[0] + nArray2[2] && nArray[1] + nArray[3] <= nArray2[1] + nArray2[3];
    }

    public static int[] a(Graphics graphics) {
        int[] nArray = new int[4];
        int[] nArray2 = nArray;
        nArray[0] = graphics.getClipX();
        nArray2[1] = graphics.getClipY();
        nArray2[2] = graphics.getClipWidth();
        nArray2[3] = graphics.getClipHeight();
        return nArray2;
    }

    public static void a(Graphics graphics, int[] nArray) {
        graphics.setClip(nArray[0], nArray[1], nArray[2], nArray[3]);
    }

    public static Image b(String string) {
        try {
            return Image.createImage((String)string);
        }
        catch (IOException iOException) {
            return null;
        }
    }

    public static Image a(String object, String object2) {
        if (object2 == null || ((String)object2).trim().length() == 0) {
            return b.e.a.b((String)object);
        }
        byte[] byArray = b.e.a.a((String)object2);
        object2 = byArray;
        if (byArray == null) {
            return b.e.a.b((String)object);
        }
        byte[] byArray2 = b.e.a.a((String)object);
        object = byArray2;
        if (byArray2 == null) {
            return null;
        }
        return b.e.a.a((byte[])object, (byte[])object2);
    }

    public static Image a(byte[] byArray, byte[] byArray2) {
        byte[] byArray3;
        if (byArray == null || byArray2 == null) {
            return null;
        }
        byte[] byArray4 = byArray2;
        byArray2 = byArray;
        if (byArray == null || byArray4 == null) {
            byArray3 = null;
        } else {
            int[] nArray;
            int[] nArray2;
            byte[] byArray5 = byArray2;
            if (byArray2 != null) {
                for (int i2 = 0; i2 < byArray5.length; ++i2) {
                    if (byArray5[i2] != 80 || byArray5[i2 + 1] != 76 || byArray5[i2 + 2] != 84 || byArray5[i2 + 3] != 69) continue;
                    int n2 = byArray5[i2 - 4] << 24 | byArray5[i2 - 3] << 16 | byArray5[i2 - 2] << 8 | byArray5[i2 - 1];
                    int[] nArray3 = new int[2];
                    nArray3[0] = i2 + 4;
                    nArray2 = nArray3;
                    nArray3[1] = n2;
                    break;
                }
            } else {
                nArray2 = nArray = null;
            }
            if (nArray2 == null) {
                byArray3 = null;
            } else {
                System.arraycopy(byArray4, 0, byArray2, (int)nArray[0], byArray4.length);
                int n3 = b.e.b.a(byArray2, (int)(nArray[0] - 4), (int)(nArray[1] + 4));
                void var3_7 = nArray[0] + nArray[1];
                byArray2[var3_7] = n3 >> 24;
                byArray2[var3_7 + true] = (byte)(n3 >> 16);
                byArray2[var3_7 + 2] = (byte)(n3 >> 8);
                byArray2[var3_7 + 3] = (byte)n3;
                byArray3 = byArray2;
            }
        }
        return Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
    }

    public static int a(int n2) {
        if (n2 < 0) {
            return n2 + 256;
        }
        return n2;
    }

    public static short a(byte n2) {
        return (short)(n2 < 0 ? n2 + 256 : n2);
    }

    public static byte b(int n2) {
        return (byte)((n2 &= 0xFF) > 127 ? -(256 - n2) : n2);
    }

    public static void a(Graphics graphics, Image image, int n2, int n3, int n4) {
        if (image == null) {
            return;
        }
        int[] nArray = b.e.a.a(graphics);
        int n5 = image.getWidth() / 10;
        int n6 = 1;
        int n7 = n2;
        while ((n7 /= 10) != 0) {
            ++n6;
        }
        for (n7 = 0; n7 < n6; ++n7) {
            int n8 = n2 % 10;
            n2 /= 10;
            int[] nArray2 = b.e.a.a(graphics);
            graphics.clipRect(n3 + n6 * n5 - (n7 + 1) * n5, n4, n5, image.getHeight());
            graphics.drawImage(image, n3 + n6 * n5 - (n7 + 1) * n5 - n8 * n5, n4, 20);
            b.e.a.a(graphics, nArray2);
        }
        b.e.a.a(graphics, nArray);
    }

    public static int c(int n2) {
        if (n2 == 0) {
            return 0;
        }
        return Math.abs(c.nextInt() % n2);
    }

    public static int d(int n2) {
        if (n2 == 0) {
            return 0;
        }
        return c.nextInt() % n2;
    }

    public static int a(short[] sArray, int n2) {
        if (sArray == null) {
            return -1;
        }
        int n3 = 0;
        int n4 = sArray.length - 1;
        int n5 = 0;
        while (n3 <= n4) {
            n5 = (n3 + n4) / 2;
            if (sArray[n5] == n2) {
                return n5;
            }
            if (sArray[n5] < n2) {
                n3 = n5 + 1;
                continue;
            }
            n4 = n5 - 1;
        }
        return -1;
    }

    public static String[] c(String object) {
        String[] stringArray;
        int n2;
        DataInputStream dataInputStream;
        InputStream inputStream;
        block8: {
            block7: {
                inputStream = null;
                dataInputStream = null;
                inputStream = "".getClass().getResourceAsStream((String)object);
                if (inputStream != null) break block7;
                object = null;
                b.e.a.a(inputStream);
                b.e.a.a(null);
                return null;
            }
            dataInputStream = new DataInputStream(inputStream);
            n2 = dataInputStream.readShort();
            if (n2 > 0) break block8;
            Object var3_5 = null;
            b.e.a.a(inputStream);
            b.e.a.a(dataInputStream);
            return null;
        }
        try {
            String[] stringArray2 = new String[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                stringArray2[i2] = dataInputStream.readUTF();
            }
            stringArray = stringArray2;
        }
        catch (Exception exception) {
            try {
                object = exception;
                exception.printStackTrace();
            }
            catch (Throwable throwable) {
                b.e.a.a(inputStream);
                b.e.a.a(dataInputStream);
                throw throwable;
            }
            b.e.a.a(inputStream);
            b.e.a.a(dataInputStream);
            return null;
        }
        b.e.a.a(inputStream);
        b.e.a.a(dataInputStream);
        return stringArray;
    }

    public static String d(String object) {
        String string;
        int n2;
        DataInputStream dataInputStream;
        InputStream inputStream;
        block8: {
            block7: {
                inputStream = null;
                dataInputStream = null;
                inputStream = "".getClass().getResourceAsStream((String)object);
                if (inputStream != null) break block7;
                object = null;
                b.e.a.a(inputStream);
                b.e.a.a(null);
                return null;
            }
            dataInputStream = new DataInputStream(inputStream);
            n2 = dataInputStream.readShort();
            if (n2 > 0) break block8;
            Object var3_5 = null;
            b.e.a.a(inputStream);
            b.e.a.a(dataInputStream);
            return null;
        }
        try {
            StringBuffer stringBuffer = new StringBuffer();
            String string2 = null;
            for (int i2 = 0; i2 < n2; ++i2) {
                string2 = dataInputStream.readUTF();
                stringBuffer.append(string2);
                if (i2 == n2 - 1) continue;
                stringBuffer.append("\n");
            }
            string = stringBuffer.toString();
        }
        catch (Exception exception) {
            try {
                object = exception;
                exception.printStackTrace();
            }
            catch (Throwable throwable) {
                b.e.a.a(inputStream);
                b.e.a.a(dataInputStream);
                throw throwable;
            }
            b.e.a.a(inputStream);
            b.e.a.a(dataInputStream);
            return null;
        }
        b.e.a.a(inputStream);
        b.e.a.a(dataInputStream);
        return string;
    }

    public static void a(String object, byte[] byArray) {
        RecordStore recordStore;
        block6: {
            if (byArray == null) {
                return;
            }
            recordStore = null;
            try {
                recordStore = RecordStore.openRecordStore((String)object, (boolean)true);
                int n2 = recordStore.getNumRecords();
                if (n2 == 0) {
                    recordStore.addRecord(byArray, 0, byArray.length);
                    break block6;
                }
                recordStore.setRecord(1, byArray, 0, byArray.length);
            }
            catch (Exception exception) {
                try {
                    object = exception;
                    exception.printStackTrace();
                }
                catch (Throwable throwable) {
                    b.e.a.a(recordStore);
                    throw throwable;
                }
                b.e.a.a(recordStore);
                return;
            }
        }
        b.e.a.a(recordStore);
        return;
    }

    private static void a(RecordStore object) {
        if (object == null) {
            return;
        }
        try {
            object.closeRecordStore();
            return;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return;
        }
    }

    public static byte[] e(String object) {
        RecordStore recordStore = null;
        try {
            recordStore = RecordStore.openRecordStore((String)object, (boolean)false);
            byte[] byArray = recordStore.getRecord(1);
            object = byArray;
            object = byArray;
        }
        catch (Exception exception) {
            b.e.a.a(recordStore);
            return null;
        }
        catch (Throwable throwable) {
            b.e.a.a(recordStore);
            throw throwable;
        }
        b.e.a.a(recordStore);
        return object;
    }

    public static int b(int n2, int n3, int n4) {
        n4 = Math.abs(n4);
        if (n2 > n3) {
            if ((n2 -= n4) < n3) {
                n2 = n3;
            }
        } else if (n2 < n3 && (n2 += n4) > n3) {
            n2 = n3;
        }
        return n2;
    }

    public static int a(int n2, int n3) {
        int n4 = n3;
        int n5 = 0;
        if (n2 != n3) {
            n5 = n3 - n2;
            n4 = Math.abs(n5) <= 2 ? n3 : n2 + n5 / 2;
        }
        return n4;
    }

    public static int[] a(int n2, int n3, int n4, int n5) {
        n2 = b.e.a.a(n2, n4);
        n3 = b.e.a.a(n3, n5);
        return new int[]{n2, n3};
    }

    public static final int a(Graphics graphics, String string, int n2, int n3, int n4, int n5) {
        char c2;
        int n6;
        char c3;
        String string2;
        if (string == null) {
            return -1;
        }
        String string3 = string;
        if (string3 == null) {
            string2 = null;
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            c3 = '\u0000';
            for (n6 = 0; n6 < string3.length(); ++n6) {
                c3 = string3.charAt(n6);
                if (c3 == '@' && n6 + 1 < string3.length()) {
                    c2 = string3.charAt(n6 + 1);
                    if (c2 >= '0' && c2 <= '9' && c2 - 48 < d.length) {
                        ++n6;
                        continue;
                    }
                } else if (c3 == '#') continue;
                stringBuffer.append(c3);
            }
            string2 = string3 = stringBuffer.toString();
        }
        if (a.stringWidth(string3) < n2) {
            n3 = n3 + n2 / 2 - a.stringWidth(string3) / 2;
        }
        int n7 = 0;
        int n8 = 0;
        c3 = '\u0000';
        graphics.setColor(n5);
        for (n6 = 0; n6 < string.length(); ++n6) {
            c3 = string.charAt(n6);
            if (c3 == '@' && n6 + 1 < string.length()) {
                c2 = string.charAt(n6 + 1);
                if (c2 >= '0' && c2 <= '9' && c2 - 48 < d.length) {
                    graphics.setColor(d[c2 - 48]);
                    ++n6;
                    continue;
                }
            } else if (c3 == '#') {
                graphics.setColor(n5);
                continue;
            }
            if (c3 == '\n') {
                n8 = 0;
                ++n7;
                continue;
            }
            if (n8 + a.charWidth(c3) > n2) {
                n8 = 0;
                graphics.drawChar(c3, n3, n4 + b * ++n7, 20);
            } else {
                graphics.drawChar(c3, n3 + n8, n4 + b * n7, 20);
            }
            n8 += a.charWidth(c3);
        }
        return n7 + 1;
    }

    public static void a(Graphics graphics, Image image, int n2, int n3, int n4, int n5, int n6, int n7) {
        graphics.setColor(4276520);
        graphics.fillRect(n5 + 1, n6, 2, n7);
        graphics.setColor(9607262);
        graphics.fillRect(n5 + 3, n6, 2, n7);
        graphics.setColor(5523743);
        graphics.drawRect(n5, n6, 5, n7);
        n3 = (n7 - 2) * n3 / n2;
        n2 = (n7 - 2) * n4 / n2;
        graphics.setColor(7564371);
        graphics.fillRect(n5 + 1, n6 + 1 + n3, 2, n2);
        graphics.setColor(0xFFFFFF);
        graphics.fillRect(n5 + 3, n6 + 1 + n3, 2, n2);
        if (image != null) {
            graphics.drawImage(image, n5, n6, 36);
            b.e.a.a(graphics, image, 0, 0, image.getWidth(), image.getHeight(), 3, n5, n6 + n7, 20);
        }
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7) {
        graphics.setColor(4276520);
        graphics.fillRect(n5 + 1, n6, 2, n7);
        graphics.setColor(9607262);
        graphics.fillRect(n5 + 3, n6, 2, n7);
        graphics.setColor(5523743);
        graphics.drawRect(n5, n6, 5, n7);
        n3 = (n7 - 2) * n3 / n2;
        n2 = (n7 - 2) * n4 / n2;
        graphics.setColor(7564371);
        graphics.fillRect(n5 + 1, n6 + 1 + n3, 2, n2);
        graphics.setColor(0xFFFFFF);
        graphics.fillRect(n5 + 3, n6 + 1 + n3, 2, n2);
    }

    public static int a(String string, int n2) {
        if (string == null) {
            return 0;
        }
        int n3 = 0;
        int n4 = 0;
        char c2 = '\u0000';
        for (int i2 = 0; i2 < string.length(); ++i2) {
            c2 = string.charAt(i2);
            if (n4 + a.charWidth(c2) > n2) {
                n4 = 0;
                ++n3;
                continue;
            }
            n4 += a.charWidth(c2);
        }
        return n3 + 1;
    }

    public static int b(int n2, int n3, int n4, int n5) {
        if ((n2 += n3) > n4) {
            n2 = n4;
        } else if (n2 < n5) {
            n2 = n5;
        }
        return n2;
    }

    public static int c(int n2, int n3, int n4) {
        return b.e.a.b(0, n2, n3, 0);
    }

    public static String a() {
        int n2 = 0;
        StringBuffer stringBuffer = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        stringBuffer.append(calendar.get(2) + 1);
        stringBuffer.append('/');
        stringBuffer.append(calendar.get(5));
        stringBuffer.append(' ');
        n2 = calendar.get(11);
        stringBuffer.append(n2 < 10 ? "0" : "");
        stringBuffer.append(n2);
        n2 = calendar.get(12);
        stringBuffer.append(':');
        stringBuffer.append(n2 < 10 ? "0" : "");
        stringBuffer.append(n2);
        return stringBuffer.toString();
    }

    public static int e(int n2) {
        switch (n2) {
            case 0: {
                return 2;
            }
            case 2: {
                return 0;
            }
            case 7: {
                return 6;
            }
            case 1: {
                return 3;
            }
            case 4: {
                return 5;
            }
            case 5: {
                return 4;
            }
            case 3: {
                return 1;
            }
            case 6: {
                return 7;
            }
        }
        return 0;
    }

    public static final void a(Graphics graphics, int[] nArray, int n2, int n3, int n4, int n5) {
        if (nArray == null) {
            return;
        }
        for (int i2 = 0; i2 < nArray.length; ++i2) {
            graphics.setColor(nArray[i2]);
            graphics.drawRect(n2 + i2, n3 + i2, n4 - 1 - i2 * 2, n5 - 1 - i2 * 2);
        }
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int[] nArray) {
        if (nArray == null) {
            return;
        }
        graphics.setColor(3739672);
        graphics.fillRect(n3, n4, n2, nArray.length + 2);
        if (n5 <= 0) {
            return;
        }
        if (n5 > 100) {
            n5 = 100;
        }
        n2 = (n2 - 2) * n5 / 100;
        for (n5 = 0; n5 < nArray.length; ++n5) {
            graphics.setColor(nArray[n5]);
            graphics.drawLine(n3 + 1, n4 + 1 + n5, n3 + n2, n4 + 1 + n5);
        }
    }

    static {
        g = f >> 1;
        e = new short[f];
        int n2 = f * 10000 / 2 / 31415;
        int n3 = n2 * 1024;
        int n4 = 0;
        for (int i2 = 0; i2 < 360; ++i2) {
            int n5 = n4 / n2;
            b.e.a.e[i2] = (short)n5;
            n4 += (n3 -= n5) / n2;
        }
        b.e.a.e[180] = 0;
        b.e.a.e[270] = -1024;
    }
}

