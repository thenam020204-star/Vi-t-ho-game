/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Font
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 *  javax.microedition.rms.RecordStore
 */
package e.a;

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

public final class b {
    public static final Font a = Font.getFont((int)0, (int)0, (int)8);
    public static final int b = a.getHeight();
    private static Random c = new Random();
    private static int[] d = new int[]{0xFF0000, 65280};

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
        return e.a.b.a(stringBuffer.toString(), '\n', ++n5);
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

    public static void a(Graphics graphics, Image image, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        if (graphics == null || image == null) {
            return;
        }
        graphics.drawRegion(image, n2, n3, n4, n5, 0, n6, n7, n8);
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

    public static Image a(String object) {
        try {
            return Image.createImage((String)object);
        }
        catch (IOException iOException) {
            object = iOException;
            iOException.printStackTrace();
            return null;
        }
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
        int[] nArray = e.a.b.a(graphics);
        int n5 = image.getWidth() / 10;
        int n6 = 1;
        int n7 = n2;
        while ((n7 /= 10) != 0) {
            ++n6;
        }
        for (n7 = 0; n7 < n6; ++n7) {
            int n8 = n2 % 10;
            n2 /= 10;
            int[] nArray2 = e.a.b.a(graphics);
            graphics.clipRect(n3 + n6 * n5 - (n7 + 1) * n5, n4, n5, image.getHeight());
            graphics.drawImage(image, n3 + n6 * n5 - (n7 + 1) * n5 - n8 * n5, n4, 20);
            e.a.b.a(graphics, nArray2);
        }
        e.a.b.a(graphics, nArray);
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

    public static String[] b(String object) {
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
                e.a.b.a(inputStream);
                e.a.b.a(null);
                return null;
            }
            dataInputStream = new DataInputStream(inputStream);
            n2 = dataInputStream.readShort();
            if (n2 > 0) break block8;
            Object var3_5 = null;
            e.a.b.a(inputStream);
            e.a.b.a(dataInputStream);
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
                e.a.b.a(inputStream);
                e.a.b.a(dataInputStream);
                throw throwable;
            }
            e.a.b.a(inputStream);
            e.a.b.a(dataInputStream);
            return null;
        }
        e.a.b.a(inputStream);
        e.a.b.a(dataInputStream);
        return stringArray;
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
                    e.a.b.a(recordStore);
                    throw throwable;
                }
                e.a.b.a(recordStore);
                return;
            }
        }
        e.a.b.a(recordStore);
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

    public static byte[] c(String object) {
        RecordStore recordStore = null;
        try {
            recordStore = RecordStore.openRecordStore((String)object, (boolean)false);
            byte[] byArray = recordStore.getRecord(1);
            object = byArray;
            object = byArray;
        }
        catch (Exception exception) {
            try {
                object = exception;
                exception.printStackTrace();
            }
            catch (Throwable throwable) {
                e.a.b.a(recordStore);
                throw throwable;
            }
            e.a.b.a(recordStore);
            return null;
        }
        e.a.b.a(recordStore);
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

    private static int a(int n2, int n3) {
        int n4 = n3;
        int n5 = 0;
        if (n2 != n3) {
            n5 = n3 - n2;
            n4 = Math.abs(n5) <= 2 ? n3 : n2 + n5 / 2;
        }
        return n4;
    }

    public static int[] a(int n2, int n3, int n4, int n5) {
        n2 = e.a.b.a(n2, n4);
        n3 = e.a.b.a(n3, n5);
        return new int[]{n2, n3};
    }

    public static final int a(Graphics graphics, String string, int n2, int n3, int n4, int n5) {
        if (string == null) {
            return -1;
        }
        String string2 = e.a.b.d(string);
        if (a.stringWidth(string2) < n2) {
            n3 = n3 + n2 / 2 - a.stringWidth(string2) / 2;
        }
        int n6 = 0;
        int n7 = 0;
        char c2 = '\u0000';
        graphics.setColor(n5);
        for (int i2 = 0; i2 < string.length(); ++i2) {
            c2 = string.charAt(i2);
            if (c2 == '@' && i2 + 1 < string.length()) {
                char c3 = string.charAt(i2 + 1);
                if (c3 >= '0' && c3 <= '9' && c3 - 48 < d.length) {
                    graphics.setColor(d[c3 - 48]);
                    ++i2;
                    continue;
                }
            } else if (c2 == '#') {
                graphics.setColor(n5);
                continue;
            }
            if (c2 == '\n') {
                n7 = 0;
                ++n6;
                continue;
            }
            if (n7 + a.charWidth(c2) > n2) {
                n7 = 0;
                graphics.drawChar(c2, n3, n4 + b * ++n6, 20);
            } else {
                graphics.drawChar(c2, n3 + n7, n4 + b * n6, 20);
            }
            n7 += a.charWidth(c2);
        }
        return n6 + 1;
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
            e.a.b.a(graphics, image, 0, 0, image.getWidth(), image.getHeight(), 3, n5, n6 + n7, 20);
        }
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

    public static String d(String string) {
        if (string == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        char c2 = '\u0000';
        for (int i2 = 0; i2 < string.length(); ++i2) {
            c2 = string.charAt(i2);
            if (c2 == '@' && i2 + 1 < string.length()) {
                char c3 = string.charAt(i2 + 1);
                if (c3 >= '0' && c3 <= '9' && c3 - 48 < d.length) {
                    ++i2;
                    continue;
                }
            } else if (c2 == '#') continue;
            stringBuffer.append(c2);
        }
        return stringBuffer.toString();
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
        return e.a.b.b(0, n2, n3, 0);
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
}

