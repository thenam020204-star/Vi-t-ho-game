/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Canvas
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.midlet.MIDlet
 *  javax.microedition.rms.RecordStore
 *  javax.microedition.rms.RecordStoreException
 *  javax.microedition.rms.RecordStoreNotOpenException
 */
package g.a;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;
import main.b;

public final class a
extends Canvas
implements Runnable {
    private static Hashtable a = new Hashtable();
    private static a b = null;
    private boolean c = false;
    private boolean d = false;
    private int e = -1;
    private int f = 0;
    private boolean g = true;
    private String h = null;
    private String i = null;
    private byte j = 0;
    private byte k = 0;
    private boolean l = false;
    private int m = 200;
    private MIDlet n;
    private String o = "1";
    private int p;
    private String q = null;
    private static int r;
    private static int s;

    private a(MIDlet mIDlet) {
        this.setFullScreenMode(true);
        this.n = mIDlet;
        v0.h = null;
        this.i = null;
    }

    private void a() {
        this.e = 23;
        this.b();
        this.j = 1;
        this.k = (byte)2;
    }

    private void b() {
        this.j = 0;
        this.k = 0;
    }

    public final void run() {
        try {
            while (this.g && this.g) {
                switch (this.e) {
                    case 21: {
                        a a2 = this;
                        boolean bl = false;
                        String string = a2.o;
                        String string2 = a2.q;
                        String[] stringArray = a2.n;
                        StringBuffer stringBuffer = new StringBuffer();
                        new a.a.a.a((MIDlet)stringArray);
                        stringArray = a.a.a.a.a(2);
                        stringBuffer.append(stringArray[0]);
                        stringBuffer.append(' ');
                        stringBuffer.append("49");
                        stringBuffer.append(' ');
                        stringBuffer.append("021");
                        stringBuffer.append(' ');
                        stringBuffer.append("Z600");
                        stringBuffer.append(' ');
                        stringBuffer.append(a.a.a.a.a());
                        stringBuffer.append(' ');
                        stringBuffer.append(a.a.a.a.b());
                        stringBuffer.append(' ');
                        stringBuffer.append(a.a.a.a.d());
                        stringBuffer.append(' ');
                        stringBuffer.append(a.a.a.a.c());
                        stringBuffer.append(' ');
                        stringBuffer.append(a.a.a.a.e());
                        stringBuffer.append(' ');
                        stringBuffer.append(string2);
                        stringBuffer.append(' ');
                        stringBuffer.append(string);
                        stringBuffer.append(' ');
                        stringBuffer.append("Z600");
                        stringArray = new String[]{stringArray[1], stringBuffer.toString()};
                        System.out.println("smscontent == " + stringArray[1]);
                        int n2 = g.a.a.a(stringArray[0], stringArray[1]);
                        if (n2 != 0) {
                            a2.f += a2.m;
                            g.a.a.a(a2.o, a2.f);
                            n2 = a2.f / a2.m;
                            int n3 = (a2.p - a2.f) / a2.m;
                            if (a2.f < a2.p) {
                                a2.h = "\u77ed\u4fe1\u53d1\u9001\u6210\u529f" + n2 + "\u6761,\u8fd8\u9700\u518d\u53d1\u9001" + n3 + "\u6761,\u8d44\u8d39" + a2.m / 100 + "\u5143/\u6761(\u4e0d\u542b\u901a\u4fe1\u8d39)\n\n\u6309\u786e\u5b9a\u7ee7\u7eed.";
                                a2.a();
                                break;
                            }
                            a2.h = "\u6263\u8d39\u6210\u529f,\u5df2\u6263\u8d39" + a2.f / 100 + "\u5143.";
                            a a3 = a2;
                            a2.e = 2;
                            a3.b();
                            a3.j = 1;
                            g.a.a.a(a2.o, 0);
                            break;
                        }
                        v0.g = false;
                    }
                }
                this.repaint();
                this.serviceRepaints();
                g.a.a.a(300L);
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        this.c = true;
    }

    private static void a(long l2) {
        try {
            Thread.sleep(l2);
            return;
        }
        catch (InterruptedException interruptedException) {
            InterruptedException interruptedException2 = interruptedException;
            interruptedException.printStackTrace();
            return;
        }
    }

    private static int a(String string) {
        if ((string = a.get(string)) != null) {
            return (Integer)((Object)string);
        }
        return 0;
    }

    private static void a(RecordStore object) {
        if (object == null) {
            return;
        }
        try {
            object.closeRecordStore();
            return;
        }
        catch (RecordStoreNotOpenException recordStoreNotOpenException) {
            object = recordStoreNotOpenException;
            recordStoreNotOpenException.printStackTrace();
            return;
        }
        catch (RecordStoreException recordStoreException) {
            object = recordStoreException;
            recordStoreException.printStackTrace();
            return;
        }
    }

    private static byte[] a(int n2, byte[] byArray, String string) {
        RecordStore recordStore = null;
        byte[] byArray2 = null;
        recordStore = RecordStore.openRecordStore((String)string, (boolean)false);
        if (byArray == null) {
            int n3 = recordStore.getRecordSize(1);
            byArray2 = new byte[n3];
            recordStore.getRecord(1, byArray2, 0);
        } else {
            recordStore.setRecord(1, byArray, 0, byArray.length);
        }
        g.a.a.a(recordStore);
        return byArray2;
    }

    private static void c() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        int n2 = a.size();
        Enumeration enumeration = a.keys();
        try {
            dataOutputStream.writeInt(n2);
            while (enumeration.hasMoreElements()) {
                String string = (String)enumeration.nextElement();
                int n3 = (Integer)a.get(string);
                dataOutputStream.writeUTF(string);
                dataOutputStream.writeInt(n3);
            }
            byte[] byArray = byteArrayOutputStream.toByteArray();
            g.a.a.a(1, byArray, "sword2_qq_pay");
            dataOutputStream.close();
            byteArrayOutputStream.close();
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    private static void d() {
        Object object = g.a.a.a(1, null, "sword2_qq_pay");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])object);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        int n2 = dataInputStream.readInt();
        for (int i2 = 0; i2 < n2; ++i2) {
            object = dataInputStream.readUTF();
            int n3 = dataInputStream.readInt();
            a.put(object, new Integer(n3));
        }
        dataInputStream.close();
        byteArrayInputStream.close();
    }

    private static void a(String string, int n2) {
        a.put(string, new Integer(n2));
        g.a.a.c();
    }

    private static boolean a(String string, String string2) {
        return true;
    }

    public static boolean a(MIDlet mIDlet, Displayable displayable, String string, String string2, String string3, int n2) {
        int n3 = n2;
        String string4 = string3;
        if (g.a.a.a(string4) >= n3) {
            // empty if block
        }
        return true;
    }

    protected final void paint(Graphics graphics) {
        if (graphics.getFont() != e.a.b.a) {
            graphics.setFont(e.a.b.a);
        }
        graphics.setColor(0xFFFFFF);
        graphics.fillRect(0, 0, main.b.d, main.b.e);
        if (this.h != null) {
            e.a.b.a(graphics, this.h, main.b.d, 0, 0, 0);
        }
        g.a.a.a(graphics, this.j, 0, main.b.e, 36);
        g.a.a.a(graphics, this.k, main.b.d, main.b.e, 40);
    }

    private static void a(Graphics graphics, byte by, int n2, int n3, int n4) {
        String string = null;
        switch (by) {
            case 2: {
                string = "\u8fd4\u56de";
                break;
            }
            case 1: {
                string = "\u786e\u5b9a";
                break;
            }
            case 3: {
                string = "\u53d6\u6d88";
                break;
            }
            default: {
                return;
            }
        }
        graphics.drawString(string, n2, n3, n4);
    }

    protected final void keyPressed(int n2) {
        block14: {
            block13: {
                if (n2 != r) break block13;
                if (this.j != 1) break block14;
                switch (this.e) {
                    case 5: {
                        break block14;
                    }
                    case 2: {
                        this.d = true;
                        v0.g = false;
                        break block14;
                    }
                    case 23: {
                        v1.e = 21;
                        this.b();
                        n2 = this.f / this.m;
                        this.h = "\u4f60\u6b63\u5728\u53d1\u9001\u7b2c" + (n2 + 1) + "\u6761\u77ed\u4fe1\uff0c\u8d44\u8d39" + this.m / 100 + "\u5143/\u6761(\u4e0d\u542b\u901a\u4fe1\u8d39)";
                    }
                    default: {
                        return;
                    }
                }
            }
            if (n2 == s) {
                if (this.k == 3) {
                    switch (this.e) {
                        case 3: 
                        case 5: {
                            this.d = false;
                            v2.g = false;
                        }
                    }
                    return;
                }
                switch (this.e) {
                    case 3: 
                    case 23: {
                        this.d = false;
                        v3.g = false;
                    }
                }
            }
        }
    }

    protected final void keyReleased(int n2) {
        super.keyReleased(n2);
    }

    static {
        Object object = "sword2_qq_pay";
        boolean bl = false;
        RecordStore recordStore = null;
        try {
            g.a.a.d();
        }
        catch (Exception exception) {
            bl = true;
        }
        if (bl) {
            try {
                recordStore = RecordStore.openRecordStore((String)object, (boolean)true);
                recordStore.addRecord(new byte[]{0}, 0, 1);
            }
            catch (Exception exception) {
                object = exception;
                exception.printStackTrace();
            }
            g.a.a.a(recordStore);
            g.a.a.c();
        }
        r = -6;
        s = -7;
    }
}

