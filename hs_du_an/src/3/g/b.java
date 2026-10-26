/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Canvas
 *  javax.microedition.lcdui.Command
 *  javax.microedition.lcdui.CommandListener
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Font
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 *  javax.microedition.lcdui.TextBox
 *  javax.microedition.midlet.MIDlet
 *  javax.microedition.pim.Contact
 *  javax.microedition.pim.ContactList
 *  javax.microedition.pim.PIM
 *  javax.microedition.rms.RecordStore
 *  javax.microedition.rms.RecordStoreException
 *  javax.microedition.rms.RecordStoreNotOpenException
 */
package g;

import e.c;
import g.a;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.TextBox;
import javax.microedition.midlet.MIDlet;
import javax.microedition.pim.Contact;
import javax.microedition.pim.ContactList;
import javax.microedition.pim.PIM;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

public final class b
extends Canvas
implements Runnable,
CommandListener {
    public static b a = null;
    private MIDlet c;
    public static int b = 1;
    private static String d = "";
    private static boolean e = false;
    private static boolean f = true;
    private String g = "cn";
    private static boolean h = false;
    private static boolean i = false;
    private static boolean j = true;
    private static boolean k = true;
    private int l;
    private int m;
    private int n;
    private int o;
    private Image p;
    private int q = 0;
    private Object[] r = new Object[9];
    private Object[] s = new Object[9];
    private String t = "";
    private String u = "";
    private String v;
    private String w;
    private static String x = "http://59.42.254.4:8081/portal/sns";
    private static String y = "http://59.42.254.3:8081/passport/phonebuddy.do";
    private boolean z = false;
    private boolean A = false;
    private String B;
    private String C;
    private String D = "10";
    private static boolean E = false;
    private int F = 0;
    private int G = 0;
    private Object[] H = null;
    private Object[] I = null;
    private Object[] J = null;
    private Hashtable K = null;
    private int L = -1;
    private int M = 1;
    private int N = 2;
    private int O = 3;
    private int P = 0xFFFFFF;
    private static int Q = 0x333333;
    private int R = 0xECECEC;
    private int S = 0xCFCFCF;
    private int T = 0xBFBEBE;
    private int U = 16316832;
    private int V = 16312377;
    private int W = 14075140;
    private static int X = 0xC00000;
    private static int Y = 0x990000;
    private int Z = 0x999999;
    private Font aa = Font.getFont((int)0, (int)0, (int)8);
    private Font ab = Font.getFont((int)0, (int)4, (int)8);
    private Font ac = Font.getFont((int)0, (int)1, (int)8);
    private int ad = 0;
    private int ae = 0;
    private int af = 0;
    private boolean ag = true;
    private int ah = 0;
    private int ai = 0;
    private int aj = 500;
    private long ak = 0L;
    private long al = 800L;
    private int am = 0;
    private String[] an;
    private static int ao = 0;
    private int ap = 0;
    private int aq = 0;
    private String ar = "";
    private TextBox as;
    private Object[] at;
    private Object[] au;
    private boolean av = false;
    private int aw = 0;
    private String[] ax;
    private String[] ay;
    private long az = 0L;
    private String aA = " /";
    private static boolean aB = false;
    private boolean aC = false;
    private static Display aD;
    private static Displayable aE;
    private Object[] aF;
    private String aG = "";
    private Command aH;
    private static String aI;
    private int aJ = 0;
    private int aK = 0;
    private int aL = 0;
    private int aM = 0;
    private int aN = 0;
    private String aO = "";
    private static int aP;
    private static int aQ;
    private int aR = 0;
    private boolean aS = true;
    private int aT = 0;
    private boolean aU = true;
    private String aV = "";
    private String aW = "";
    private String aX = "";
    private char[] aY = new char[]{'2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'Z', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y'};
    private Vector aZ = new Vector();
    private int ba = 0;
    private int bb = 20;
    private int bc = 0;
    private int bd = 0;
    private String be = "n";
    private static Hashtable bf;
    private static Vector bg;
    private Command bh;
    private Command bi;
    private String[] bj;
    private Image bk;
    private String bl = "";
    private String bm = "";
    private String bn = "";
    private String bo = "";

    private b(MIDlet object, String string, String object2, String objectArray, int n2, int n3, int n4, int n5) {
        int n6;
        this.setFullScreenMode(f);
        this.c = object;
        this.B = string;
        this.C = object2;
        this.D = objectArray;
        this.n = n2;
        this.o = n3;
        if (this.n == 0) {
            this.n = this.getWidth();
        }
        if (this.o == 0) {
            this.o = this.getHeight();
        }
        this.l = n4;
        this.m = n5;
        this.p = g.b.e("/live_top.png");
        this.p.getHeight();
        this.j();
        object = new String[]{"", "loadscore", "gc", "gameuser", "willplay", "playedgame", "hotgame", "plog", "cre"};
        for (int i2 = 0; i2 < this.s.length; ++i2) {
            boolean bl = false;
            objectArray = object[i2];
            object2 = this.ay[i2];
            Object[] objectArray2 = new Object[5];
            Object[] objectArray3 = objectArray2;
            objectArray2[0] = object2;
            objectArray3[1] = objectArray;
            objectArray3[2] = new Boolean(false);
            objectArray3[3] = new Boolean(false);
            objectArray3[4] = new Boolean(false);
            this.s[i2] = objectArray3;
        }
        ((Object[])this.s[0])[2] = new Boolean(true);
        ((Object[])this.s[this.s.length - 1])[2] = new Boolean(true);
        ((Object[])this.s[this.c((String)"plog")])[4] = new Boolean(true);
        ((Object[])this.s[this.c((String)"playedgame")])[4] = new Boolean(true);
        this.ay = null;
        this.ai = this.q = this.aa.getHeight() + 4;
        this.i();
        object2 = this;
        try {
            g.a.a(((b)object2).c);
            ((b)object2).bn = g.a.b;
            ((b)object2).bo = g.a.c;
            ((b)object2).bl = ((b)object2).c.getAppProperty("cpname");
            ((b)object2).bm = ((b)object2).c.getAppProperty("cpurl");
            objectArray = ((b)object2).c.getAppProperty("method");
            if (objectArray != null && objectArray.length() > 0) {
                aI = objectArray.trim().equals("GET") ? "GET" : "POST";
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (((b)object2).bl == null || ((b)object2).bl.equals("") || ((b)object2).bm == null || ((b)object2).bm.equals("")) {
            ((b)object2).bl = "lakoo.cn";
            ((b)object2).bm = "http://lakoo.cn/";
        }
        object2 = this;
        objectArray = "lakooRms";
        n2 = 0;
        RecordStore recordStore = null;
        try {
            Object object3 = object2;
            object = new ByteArrayInputStream(g.b.a(1, null, "lakooRms"));
            DataInputStream dataInputStream = new DataInputStream((InputStream)object);
            ((b)object3).bj[0] = dataInputStream.readUTF();
            ((b)object3).bj[1] = dataInputStream.readUTF();
            ((b)object3).bj[2] = dataInputStream.readUTF();
            ((b)object3).bj[3] = dataInputStream.readUTF();
            dataInputStream.close();
            ((ByteArrayInputStream)object).close();
        }
        catch (Exception exception) {
            n2 = 1;
        }
        if (n2 != 0) {
            try {
                recordStore = RecordStore.openRecordStore((String)objectArray, (boolean)true);
                recordStore.addRecord(new byte[]{0}, 0, 1);
                recordStore.addRecord(new byte[]{0}, 0, 1);
                recordStore.addRecord(new byte[]{0}, 0, 1);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            g.b.a(recordStore);
        }
        Vector<Object[]> vector = new Vector<Object[]>();
        object = new Vector();
        int n7 = 0;
        vector.addElement(g.b.a(0, this.p, 23, 1));
        vector.addElement(g.b.a(1, false, "v 1.2.5", this.p.getWidth() + 28, 2, null, this.aa, 0xAEAEAE, ""));
        ((Vector)object).addElement(g.b.a(0, vector, 30, this.P));
        vector = new Vector();
        objectArray = g.b.a(3, this.ax[0], "userName", 5, this.aa.stringWidth("ABCDEABCDEAB"), this.aa.getHeight() + 2, this.P, "${username}", this.aa, Q);
        vector.addElement(objectArray);
        ((Vector)object).addElement(g.b.a(0, vector, this.aa.getHeight() + 4, -1));
        this.at = objectArray;
        vector = new Vector();
        Object[] objectArray4 = g.b.a(3, this.ax[1], "password", 5 + this.aa.stringWidth(this.ax[0]) - this.aa.stringWidth(this.ax[1]), this.aa.stringWidth("ABCDEABCDEAB"), this.aa.getHeight() + 2, this.P, "${password}", this.aa, Q);
        vector.addElement(objectArray4);
        ((Vector)object).addElement(g.b.a(0, vector, this.aa.getHeight() + 4, -1));
        this.au = objectArray4;
        if (this.t == null || this.t.equals("")) {
            vector = new Vector<Object[]>();
            vector.addElement(g.b.a(1, true, this.ax[2], 5, 1, "REG_ID", this.ab, X, ""));
            ((Vector)object).addElement(g.b.a(0, vector, this.q, -1));
        }
        this.r[0] = object;
        ((Object[])this.s[0])[3] = new Boolean(true);
        for (n6 = 1; n6 < this.s.length - 1; ++n6) {
            vector = new Vector();
            object = new Vector();
            vector.addElement(g.b.a(1, true, ((Object[])this.s[n6])[0].toString(), 5, 1, "TITLE", this.ac, Q, ""));
            ((Vector)object).addElement(g.b.a(n6, vector, this.q, this.T));
            vector = new Vector<Object>();
            vector.addElement((Object[])g.b.a(2, this.n, 1, this.R));
            ((Vector)object).addElement(g.b.a(n6, vector, 1, this.R));
            ((Vector)object).addElement(g.b.a(n6, vector, 1, this.R));
            this.r[n6] = object;
        }
        vector = new Vector();
        object = new Vector();
        vector.addElement(g.b.a(1, false, ((Object[])this.s[this.s.length - 1])[0].toString(), 5, 1, null, this.ac, this.P, ""));
        ((Vector)object).addElement(g.b.a(this.s.length - 1, vector, this.q, X));
        this.r[this.s.length - 1] = object;
        n7 = this.c("loadscore");
        object = (Vector)this.r[n7];
        vector = new Vector<Object[]>();
        vector.addElement(g.b.a(1, false, this.ax[3], 5, 1, null, this.aa, Q, ""));
        ((Vector)object).addElement(g.b.a(n7, vector, this.q, this.S));
        vector = new Vector<Object[]>();
        vector.addElement(g.b.a(1, false, "", 5, 1, null, this.aa, Q, ""));
        ((Vector)object).addElement(g.b.a(n7, vector, 0, this.S));
        vector = new Vector<Object>();
        vector.addElement((Object[])g.b.a(2, this.n, 1, this.R));
        ((Vector)object).addElement(g.b.a(n7, vector, 1, this.R));
        vector = new Vector();
        n6 = 5;
        for (int i3 = 5; i3 > 0; --i3) {
            vector.addElement(g.b.a(1, true, i3 + this.ax[4], n6, 1, "poll:" + i3, this.ab, X, ""));
            n6 = n6 + this.ab.stringWidth(i3 + this.ax[4]) + 4;
        }
        ((Vector)object).addElement(g.b.a(n7, vector, this.q, this.S));
        vector = new Vector<Object>();
        vector.addElement((Object[])g.b.a(2, this.n, 2, this.R));
        ((Vector)object).addElement(g.b.a(n7, vector, 2, this.R));
        this.a();
        this.b();
    }

    private void a() {
        aP = 0;
        aQ = 0;
        if (this.aC) {
            this.aK = this.n - 4;
            this.aJ = this.o - (this.q << 1) - 6;
            this.aM = this.q + 3;
            this.aL = 2;
            this.bb = this.aJ / this.q;
            return;
        }
        this.aK = this.n > 240 ? this.n - 80 : (this.n == 240 ? this.n - 60 : this.n - 20);
        if (this.o <= 208) {
            this.aJ = this.o - (this.q << 2);
            this.aM = this.q << 1;
        } else {
            this.aJ = this.o - this.q * 6;
            this.aM = this.q * 3;
        }
        this.aN = this.aJ / this.q;
        this.aJ = this.aN * this.q;
        this.aL = (this.n - this.aK) / 2;
        this.bb = this.aN;
    }

    private void a(int n2) {
        if (this.H != null && Integer.parseInt(this.H[4].toString()) == n2) {
            this.b(n2);
        }
    }

    private void b() {
        Vector vector = (Vector)this.r[this.s.length - 1];
        while (vector.size() > 1) {
            vector.removeElementAt(1);
        }
        Vector<Object[]> vector2 = new Vector<Object[]>();
        if (this.bk != null) {
            vector2.addElement(g.b.a(0, this.bk, 5, 2));
            vector.addElement(g.b.a(this.s.length - 1, vector2, this.bk.getHeight() + 2, -1));
        } else {
            vector2.addElement(g.b.a(1, true, this.ax[46], 5, 1, "REST_CRE", this.ab, X, ""));
            vector.addElement(g.b.a(this.s.length - 1, vector2, this.q, -1));
        }
        vector2 = new Vector<Object[]>();
        vector2.addElement(g.b.a(1, true, this.bj[1], 5, 1, this.bj[2], this.ab, X, ""));
        vector.addElement(g.b.a(this.s.length - 1, vector2, this.q, -1));
        String[] stringArray = g.b.a(this.bj[3], this.aa, this.n - 4);
        for (int i2 = 0; i2 < stringArray.length; ++i2) {
            vector2 = new Vector<Object[]>();
            vector2.addElement(g.b.a(1, false, stringArray[i2], 5, 1, null, this.aa, Q, ""));
            vector.addElement(g.b.a(this.s.length - 1, vector2, this.q, -1));
        }
        vector2 = new Vector<Object[]>();
        vector2.addElement(g.b.a(1, true, this.ax[5], 5, 1, this.bj[2], this.ab, X, ""));
        vector.addElement(g.b.a(this.s.length - 1, vector2, this.q, -1));
    }

    private void a(int n2, String object, int n3, String object2) {
        try {
            Vector vector = (Vector)this.r[this.c("loadscore")];
            Vector vector2 = (Vector)((Object[])vector.elementAt(3))[1];
            if (n3 >= 0) {
                if (!((String)object).equals("null")) {
                    object = new e.a((String)object);
                    String string = "";
                    object2 = "";
                    int n4 = 0;
                    int n5 = 0;
                    String string2 = null;
                    while (vector2.size() > 0) {
                        vector2.removeElementAt(0);
                    }
                    for (int i2 = 0; i2 < ((e.a)object).a(); ++i2) {
                        object2 = (c)((e.a)object).a(i2);
                        string = ((c)object2).a("score");
                        object2 = ((c)object2).a("count");
                    }
                    string2 = "\uff08" + (String)object2 + this.ax[7] + "\uff09";
                    if (string.indexOf(".") > 0) {
                        n4 = Integer.parseInt(string.substring(0, string.indexOf(".")));
                        String string3 = string.substring(string.indexOf(".") + 1, string.length()) + "0";
                        if (string3.length() > 2) {
                            string3 = string3.substring(0, 2);
                        }
                        n5 = Integer.parseInt(string3) * 15 / 100;
                    } else {
                        n4 = Integer.parseInt(string);
                    }
                    Image image = Image.createImage((int)(n4 * 13 + n5 + (n4 << 1)), (int)14);
                    object2 = image.getGraphics();
                    object2.setColor(this.S);
                    object2.fillRect(0, 0, image.getWidth(), image.getHeight());
                    for (int i3 = 0; i3 < n4 + 1; ++i3) {
                        object2.setColor(0xFFFF66);
                        object2.fillTriangle(7 + i3 * 13 + (i3 << 1), 0, 0 + i3 * 13 + (i3 << 1), 13, 13 + i3 * 13 + (i3 << 1), 13);
                        object2.setColor(0x999900);
                        object2.drawLine(7 + i3 * 13 + (i3 << 1), 0, 0 + i3 * 13 + (i3 << 1), 13);
                        object2.drawLine(7 + i3 * 13 + (i3 << 1), 0, 13 + i3 * 13 + (i3 << 1), 13);
                        object2.drawLine(0 + i3 * 13 + (i3 << 1), 13, 13 + i3 * 13 + (i3 << 1), 13);
                    }
                    vector2.addElement(g.b.a(1, false, this.ax[6], 5, 1, "", this.aa, Q, ""));
                    n4 = 5 + this.aa.stringWidth(this.ax[6]);
                    vector2.addElement(g.b.a(0, image, n4, 1));
                    if (this.n - 8 - (n4 += image.getWidth() + 3) >= this.aa.stringWidth(string2)) {
                        vector2.addElement(g.b.a(1, false, string2, n4, 1, "", this.aa, Q, ""));
                    } else {
                        ((Object[])((Vector)((Object[])vector.elementAt((int)4))[1]).elementAt((int)0))[2] = string2;
                        ((Object[])vector.elementAt((int)4))[2] = this.q + "";
                    }
                }
                if (n2 == 998) {
                    this.a(this.ax[8], 1);
                }
                this.a(n2);
            } else {
                this.a((String)object2, 1);
            }
            if (n2 == 998) {
                this.a(n2);
            }
            ((Object[])this.s[this.c((String)"loadscore")])[3] = new Boolean(true);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    private void b(int n2, String object, int n3, String string) {
        try {
            new Vector();
            Vector<Object[]> vector = null;
            Vector vector2 = (Vector)this.r[n2];
            while (vector2.size() > 3) {
                vector2.removeElementAt(3);
            }
            if (n3 >= 0) {
                if (!((String)object).equals("null")) {
                    object = new e.a((String)object);
                    for (int i2 = 0; i2 < ((e.a)object).a(); ++i2) {
                        c c2 = (c)((e.a)object).a(i2);
                        vector = new Vector<Object[]>();
                        vector.addElement(g.b.a(1, true, c2.a("title"), this.ab.stringWidth("\u706b") + 5, 1, "MSGBOX=" + c2.a("code"), this.ab, X, ""));
                        vector2.addElement(g.b.a(n2, vector, this.q, this.S));
                        vector = new Vector<Object>();
                        vector.addElement((Object[])g.b.a(2, this.n, 1, this.R));
                        vector2.addElement(g.b.a(n2, vector, 1, this.R));
                    }
                } else {
                    vector = new Vector<Object[]>();
                    vector.addElement(g.b.a(1, false, this.ax[3], this.aa.stringWidth("\u706b") + 5, 1, "", this.aa, Q, ""));
                    vector2.addElement(g.b.a(n2, vector, this.q, this.S));
                }
            } else {
                vector = new Vector<Object[]>();
                vector.addElement(g.b.a(1, false, string, this.aa.stringWidth("\u706b") + 5, 1, "", this.aa, Q, ""));
                vector2.addElement(g.b.a(n2, vector, this.q, this.S));
            }
            if (n3 != -777) {
                this.a(n2);
                ((Object[])this.s[n2])[3] = new Boolean(true);
            }
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    private void c(int n2, String object, int n3, String vector) {
        if (this.am == 12) {
            if (n3 >= 0) {
                if (!((String)object).equals("null")) {
                    try {
                        vector = new e.a((String)object);
                        bg = new Vector();
                        for (int i2 = 0; i2 < ((e.a)((Object)vector)).a(); ++i2) {
                            StringBuffer stringBuffer = new StringBuffer();
                            object = (c)((e.a)((Object)vector)).a(i2);
                            String string = ((c)object).a("uname");
                            if (string == null || string.equals("")) {
                                string = this.ax[55];
                            }
                            if (!((c)object).a("floorNum").equals("")) {
                                stringBuffer.append("[#" + ((c)object).a("floorNum") + "]");
                            }
                            stringBuffer.append(string + "\uff1a" + ((c)object).a("content"));
                            bg.addElement(g.b.a(1, stringBuffer.toString(), Q));
                            bg.addElement(g.b.a(2, "2", this.S));
                        }
                        bf = g.b.a(this.ax[51], this.ax[32], "doing", "cancel", "13", "", this.ax[52]);
                        return;
                    }
                    catch (Exception exception) {
                        this.a(this.ax[26], 1);
                        return;
                    }
                }
                this.a(this.ax[50], 1);
                return;
            }
            this.a((String)((Object)vector), 1);
            return;
        }
        try {
            new Vector();
            Vector vector2 = (Vector)this.r[n2];
            while (vector2.size() > 3) {
                vector2.removeElementAt(3);
            }
            int n4 = 0;
            if (n3 >= 0 && !((String)object).equals("null")) {
                object = new e.a((String)object);
                for (n3 = 0; n3 < ((e.a)object).a(); ++n3) {
                    c c2 = (c)((e.a)object).a(n3);
                    n4 = Integer.parseInt(c2.a("total"));
                    vector = new Vector();
                    String string = c2.a("content");
                    if (this.ab.stringWidth(string) + this.ab.stringWidth("\u706b") + 5 > this.n) {
                        int n5 = this.ab.stringWidth("\u706b\u2026\u2026") + 5;
                        StringBuffer stringBuffer = new StringBuffer();
                        int n6 = 0;
                        while (n3 <= string.length() && n5 + this.ab.stringWidth(stringBuffer.toString()) < this.n) {
                            stringBuffer.append(string.substring(n6, n6 + 1));
                            ++n6;
                        }
                        stringBuffer.append("\u2026");
                        string = stringBuffer.toString();
                    }
                    vector.addElement(g.b.a(1, true, string, this.ab.stringWidth("\u706b") + 5, 1, "GETPL=" + c2.a("id"), this.ab, X, ""));
                    vector2.addElement(g.b.a(n2, vector, this.q, this.S));
                    vector = new Vector<Object>();
                    vector.addElement((Object[])g.b.a(2, this.n, 1, this.R));
                    vector2.addElement(g.b.a(n2, vector, 1, this.R));
                }
            }
            if (this.aF == null) {
                this.aF = g.b.a(1, true, this.ax[49], this.ab.stringWidth("\u706b") + 5, 1, "sendPL", this.ab, Q, "");
            }
            vector = new Vector<Object[]>();
            vector.addElement(this.aF);
            int n7 = this.ab.stringWidth("\u706b" + this.ax[49]) + 5 + 3;
            int n8 = n4 / 5;
            if (n4 % 5 > 0) {
                ++n8;
            }
            if (n8 > 0) {
                String string;
                for (int i3 = 0; i3 < n8 && n7 + this.ab.stringWidth(string = "[" + (i3 + 1) + "]") + 3 < this.n; ++i3) {
                    vector.addElement(g.b.a(1, true, string, n7, 1, "plPage=" + i3 * 5, this.ab, Q, ""));
                    n7 += this.ab.stringWidth(string) + 5;
                }
            }
            vector2.addElement(g.b.a(n2, vector, this.q, this.R));
            if (this.am == 13 || this.am == 14 || this.am == 15) {
                this.H = g.b.a(9999, this.aF, 0, 0, n2);
                return;
            }
            this.a(n2);
            ((Object[])this.s[n2])[3] = new Boolean(true);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    private static Hashtable a(String object) {
        Hashtable<String, String> hashtable = null;
        try {
            if (!((String)object).equals("null")) {
                object = new e.a((String)object);
                for (int i2 = 0; i2 < ((e.a)object).a(); ++i2) {
                    hashtable = new Hashtable<String, String>();
                    c c2 = (c)((e.a)object).a(i2);
                    hashtable.put("newGameName", c2.a("title"));
                    hashtable.put("newGameInfo", c2.a("context"));
                    hashtable.put("newGameLink", c2.a("url"));
                    hashtable.put("imgSize", c2.a("size"));
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return hashtable;
    }

    private void d(int n2, String objectArray, int n3, String string) {
        try {
            new Vector();
            Vector<Object[]> vector = null;
            Vector vector2 = (Vector)this.r[n2];
            while (vector2.size() > 3) {
                vector2.removeElementAt(3);
            }
            if (n3 >= 0) {
                if (!objectArray.equals("null")) {
                    objectArray = new e.a((String)objectArray);
                    for (int i2 = 0; i2 < objectArray.a(); ++i2) {
                        c c2 = (c)objectArray.a(i2);
                        vector = new Vector<Object[]>();
                        vector.addElement(g.b.a(1, false, c2.a("name"), this.ab.stringWidth("\u706b") + 5, 1, null, this.aa, Q, ""));
                        vector2.addElement(g.b.a(4, vector, this.q, this.S));
                        vector = new Vector<Object>();
                        vector.addElement((Object[])g.b.a(2, this.n, 1, this.R));
                        vector2.addElement(g.b.a(4, vector, 1, this.R));
                    }
                } else {
                    vector = new Vector<Object[]>();
                    vector.addElement(g.b.a(1, false, this.ax[3], this.aa.stringWidth("\u706b") + 5, 1, "", this.aa, Q, ""));
                    vector2.addElement(g.b.a(n2, vector, this.q, this.S));
                }
            } else {
                vector = new Vector<Object[]>();
                vector.addElement(g.b.a(1, false, string, 5, 1, "", this.aa, Q, ""));
                vector2.addElement(g.b.a(n2, vector, this.q, this.S));
            }
            if (this.w != null && !this.w.equals("")) {
                vector = new Vector();
                objectArray = g.b.a(1, true, this.ax[9], this.ab.stringWidth("\u706b") + 5, 1, "upPhoneMumber", this.ab, X, this.aV);
                vector.addElement(objectArray);
                this.K = new Hashtable();
                this.K.put("count", "0");
                this.K.put("cont", objectArray);
                vector.addElement(g.b.a(1, true, "[?]", 10 + this.ab.stringWidth("character[9]"), 1, "OST", this.ab, X, this.aV));
                vector2.addElement(g.b.a(1, vector, this.q, this.S));
            }
            if (n3 != -777) {
                this.a(n2);
                ((Object[])this.s[n2])[3] = new Boolean(true);
            }
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    private void e(int n2, String object, int n3, String string) {
        try {
            new Vector();
            Vector<Object[]> vector = null;
            Vector vector2 = (Vector)this.r[n2];
            while (vector2.size() > 3) {
                vector2.removeElementAt(3);
            }
            if (n3 >= 0) {
                if (!((String)object).equals("null")) {
                    object = new e.a((String)object);
                    for (int i2 = 0; i2 < ((e.a)object).a(); ++i2) {
                        vector = (c)((e.a)object).a(i2);
                        String string2 = ((c)((Object)vector)).a("title");
                        String string3 = ((c)((Object)vector)).a("desc");
                        vector = new Vector<Object[]>();
                        if (string3 != null && !string3.equals("")) {
                            if (string3.indexOf("http://") == 0) {
                                vector.addElement(g.b.a(1, true, string2, 5, 1, string3, this.ab, X, string3));
                            } else {
                                vector.addElement(g.b.a(1, true, string2, 5, 1, "OST", this.ab, X, string3));
                            }
                        } else {
                            vector.addElement(g.b.a(1, false, string2, 5, 1, null, this.aa, Q, ""));
                            vector.addElement(g.b.a(1, true, this.ax[45], this.aa.stringWidth(string2) + 8, 1, "REST", this.ab, X, ""));
                        }
                        vector2.addElement(g.b.a(n2, vector, this.q, this.S));
                        vector = new Vector<Object>();
                        vector.addElement((Object[])g.b.a(2, this.n, 1, this.R));
                        vector2.addElement(g.b.a(n2, vector, 1, this.R));
                    }
                } else {
                    vector = new Vector<Object[]>();
                    vector.addElement(g.b.a(1, false, this.ax[3], this.aa.stringWidth("\u706b") + 5, 1, "", this.aa, Q, ""));
                    vector2.addElement(g.b.a(n2, vector, this.q, this.S));
                }
            } else {
                vector = new Vector<Object[]>();
                vector.addElement(g.b.a(1, false, string, this.aa.stringWidth("\u706b") + 5, 1, "", this.aa, Q, ""));
                vector2.addElement(g.b.a(n2, vector, this.q, this.S));
            }
            if (n3 != -777) {
                if (this.am != 9) {
                    this.a(n2);
                } else {
                    this.J = null;
                }
                ((Object[])this.s[n2])[3] = new Boolean(true);
            }
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    private void a(String object, int n2, String object2) {
        try {
            if (n2 >= 0) {
                object = new e.a((String)object);
                for (n2 = 0; n2 < ((e.a)object).a(); ++n2) {
                    object2 = (c)((e.a)object).a(n2);
                    this.v = ((c)object2).a("key");
                    this.w = ((c)object2).a("uid");
                }
                if (this.v != null && !this.v.equals("")) {
                    new Vector();
                    Vector<Object[]> vector = null;
                    new Vector();
                    object2 = (Vector)this.r[0];
                    while (((Vector)object2).size() > 1) {
                        ((Vector)object2).removeElementAt(1);
                    }
                    vector = new Vector<Object[]>();
                    vector.addElement(g.b.a(1, false, this.ax[0] + this.t, 5, 1, null, this.aa, Q, ""));
                    ((Vector)object2).addElement(g.b.a(0, vector, this.q, -1));
                    vector = new Vector<Object[]>();
                    vector.addElement(g.b.a(1, false, this.ax[1] + "******", 5, 1, null, this.aa, Q, ""));
                    vector.addElement(g.b.a(1, true, this.ax[11], 8 + this.aa.stringWidth(this.ax[1] + this.u), 1, "SHOW_PSW", this.ab, X, ""));
                    ((Vector)object2).addElement(g.b.a(0, vector, this.q, -1));
                    g.a.a(this.t, this.u, this.z);
                    return;
                }
                this.a(this.ax[13], 1);
                return;
            }
            this.a((String)object2, 1);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    private void b(int n2) {
        boolean bl;
        Vector vector;
        if (((Boolean)((Object[])this.s[this.G])[2]).booleanValue() && n2 != this.G && this.G != 0 && this.G != this.s.length - 1) {
            Vector vector2 = (Vector)this.r[this.G];
            vector = null;
            ((Object[])((Vector)((Object[])vector2.elementAt((int)0))[1]).elementAt((int)0))[7] = Q + "";
            ((Object[])vector2.elementAt((int)0))[3] = this.T + "";
            bl = (Boolean)((Object[])this.s[this.G])[2];
            ((Object[])this.s[this.G])[2] = new Boolean(!bl);
        }
        this.G = n2;
        if (this.G != 0 && this.G != this.s.length - 1) {
            bl = (Boolean)((Object[])this.s[this.G])[2];
            ((Object[])this.s[this.G])[2] = new Boolean(!bl);
            vector = (Vector)this.r[this.G];
            Vector vector3 = (Vector)((Object[])vector.elementAt(0))[1];
            if (((Boolean)((Object[])this.s[this.G])[2]).booleanValue()) {
                ((Object[])vector3.elementAt((int)0))[7] = this.P + "";
                ((Object[])vector.elementAt((int)0))[3] = X + "";
            } else {
                ((Object[])vector3.elementAt((int)0))[7] = Q + "";
                ((Object[])vector.elementAt((int)0))[3] = this.T + "";
            }
        }
        this.ae = 0;
        if ((this.G - 1) * this.q + this.p.getHeight() + (this.q << 1) > this.o - (this.q << 2)) {
            int n3 = this.o / this.q / 2;
            if (n3 % 2 > 0) {
                ++n3;
            }
            this.ae = -this.q * n3;
        }
        if (this.H != null) {
            this.H[0] = "9999";
        }
    }

    private static Object[] a(int n2, Object[] objectArray, int n3, int n4, int n5) {
        Object[] objectArray2 = new Object[5];
        Object[] objectArray3 = objectArray2;
        objectArray2[0] = n2 + "";
        objectArray3[1] = objectArray;
        objectArray3[2] = n3 + "";
        objectArray3[3] = n4 + "";
        objectArray3[4] = n5 + "";
        return objectArray3;
    }

    private static Object[] a(int n2, Vector vector, int n3, int n4) {
        Object[] objectArray = new Object[4];
        Object[] objectArray2 = objectArray;
        objectArray[0] = n2 + "";
        objectArray2[1] = vector;
        objectArray2[2] = n3 + "";
        objectArray2[3] = n4 + "";
        return objectArray2;
    }

    private static Object[] a(int n2, Image image, int n3, int n4) {
        Object[] objectArray = new Object[6];
        Object[] objectArray2 = objectArray;
        objectArray[0] = 0 + "";
        objectArray2[1] = new Boolean(false);
        objectArray2[2] = image;
        objectArray2[3] = n3 + "";
        objectArray2[4] = n4 + "";
        objectArray2[5] = null;
        return objectArray2;
    }

    private static Object[] a(int n2, String string, String string2, int n3, int n4, int n5, int n6, String string3, Font font, int n7) {
        Object[] objectArray = new Object[12];
        Object[] objectArray2 = objectArray;
        objectArray[0] = 3 + "";
        objectArray2[1] = new Boolean(true);
        objectArray2[2] = string;
        objectArray2[3] = string2;
        objectArray2[4] = n3 + "";
        objectArray2[5] = n4 + "";
        objectArray2[6] = n5 + "";
        objectArray2[7] = n6 + "";
        objectArray2[8] = 1 + "";
        objectArray2[9] = string3;
        objectArray2[10] = font;
        objectArray2[11] = n7 + "";
        return objectArray2;
    }

    private static Object[] a(int n2, boolean bl, String string, int n3, int n4, String string2, Font font, int n5, String string3) {
        Object[] objectArray = new Object[9];
        Object[] objectArray2 = objectArray;
        objectArray[0] = 1 + "";
        objectArray2[1] = new Boolean(bl);
        objectArray2[2] = string;
        objectArray2[3] = n3 + "";
        objectArray2[4] = n4 + "";
        objectArray2[5] = string2;
        objectArray2[6] = font;
        objectArray2[7] = n5 + "";
        objectArray2[8] = string3;
        return objectArray2;
    }

    private static Object a(int n2, int n3, int n4, int n5) {
        Object[] objectArray = new Object[5];
        Object[] objectArray2 = objectArray;
        objectArray[0] = 2 + "";
        objectArray2[1] = 0 + "";
        objectArray2[2] = n3 + "";
        objectArray2[3] = n4 + "";
        objectArray2[4] = n5 + "";
        return objectArray2;
    }

    public final void run() {
        while (E) {
            this.repaint();
            this.serviceRepaints();
            this.c();
            g.b.a(33L);
        }
        aD.setCurrent(aE);
    }

    private void c() {
        switch (this.am) {
            case 1: {
                Object object;
                Object object2 = object = ((Object[])this.H[1])[2].toString();
                object = this;
                int n2 = 0;
                for (int i2 = 0; i2 < ((b)object).s.length; ++i2) {
                    Object[] objectArray = (Object[])((b)object).s[i2];
                    if (!((String)object2).equals(objectArray[0].toString())) continue;
                    n2 = i2;
                    break;
                }
                int n3 = n2;
                n3 = ((Boolean)((Object[])this.s[n3])[4]).booleanValue() ? 1 : 0;
                object2 = new Vector();
                if (n3 != 0) {
                    if (this.t == null || this.t.equals("")) {
                        this.a(this.ax[14], 1);
                        this.H = g.b.a(9999, this.at, 0, 0, 0);
                        this.ae = 0;
                    } else if (this.u == null || this.u.equals("")) {
                        this.a(this.ax[15], 1);
                        this.H = g.b.a(9999, this.au, 0, 0, 0);
                        this.ae = 0;
                    } else {
                        object2 = this.a(true);
                    }
                } else {
                    object2 = this.t == null || this.t.equals("") || this.u == null || this.u.equals("") ? this.a(false) : this.a(true);
                }
                if (((Vector)object2).size() <= 0) break;
                this.b((Vector)object2);
                break;
            }
            case 2: {
                if (this.t == null || this.t.equals("")) {
                    this.a(this.ax[14], 1);
                    this.H = g.b.a(9999, this.at, 0, 0, 0);
                    this.ae = 0;
                    break;
                }
                if (this.u == null || this.u.equals("")) {
                    this.a(this.ax[15], 1);
                    this.H = g.b.a(9999, this.au, 0, 0, 0);
                    this.ae = 0;
                    break;
                }
                b b2 = this;
                StringBuffer stringBuffer = new StringBuffer();
                String string = "";
                if (b2.t != null && !b2.t.equals("")) {
                    if (b2.v != null && !b2.v.equals("")) {
                        stringBuffer.append("key=" + b2.v);
                    } else {
                        stringBuffer.append("u=" + b2.t);
                        stringBuffer.append("&p=" + b2.u);
                        string = "userinfo:gameuser:playedgame:plog:";
                    }
                }
                stringBuffer.append("&gameCode=" + b2.B);
                stringBuffer.append("&keytext=" + string + "doscore");
                String string2 = ((Object[])b2.H[1])[5].toString();
                if (string2.indexOf("poll:") != -1) {
                    string2 = string2.substring(5, string2.length());
                }
                stringBuffer.append("&score=" + string2);
                stringBuffer.append("&locale=" + b2.g);
                Hashtable hashtable = b2.a(x, stringBuffer.toString());
                int n4 = Integer.parseInt(hashtable.get("code").toString());
                if (n4 == 0) {
                    b2.a((byte[])hashtable.get("data"));
                } else {
                    b2.a(b2.ax[25] + "(" + n4 + ")", 1);
                }
                b2.am = 0;
                break;
            }
            case 3: {
                if (this.w != null && !this.w.equals("")) {
                    this.e();
                    break;
                }
                this.a(this.ax[16], 1);
                break;
            }
            case 4: {
                if (this.as == null) break;
                this.a((Displayable)this.as);
                break;
            }
            case 5: {
                try {
                    boolean bl = false;
                    if (!this.c.platformRequest(this.ar)) break;
                    this.c.notifyDestroyed();
                    E = false;
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case 6: {
                Vector vector = new Vector();
                for (int i3 = 0; i3 < this.aZ.size(); ++i3) {
                    if (!((Hashtable)this.aZ.elementAt(i3)).get("select").toString().equals("y")) continue;
                    boolean bl = false;
                    String string = ((Hashtable)this.aZ.elementAt(i3)).get("tel").toString();
                    String string3 = this.a(Long.parseLong(g.a.a(string, "+", "")), 5);
                    if (!g.a.a(string, g.a.a(g.a.a(this.aX, "$(gamename)", this.C), "$(code)", string3))) continue;
                    Hashtable<String, String> hashtable = new Hashtable<String, String>();
                    hashtable.put("tel", string);
                    hashtable.put("code", string3);
                    vector.addElement(hashtable);
                }
                this.aZ = null;
                if (vector.size() > 0) {
                    this.a(vector);
                    break;
                }
                this.a(this.ax[42], 1);
                break;
            }
            case 7: {
                b b3 = this;
                String string = ((Object[])b3.H[1])[5].toString();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("gameCode=" + string.substring("MSGBOX=".length(), string.length()));
                stringBuffer.append("&ua=" + b3.bo);
                stringBuffer.append("&cp=" + b3.bn);
                stringBuffer.append("&keytext=qgi");
                if (b3.w != null && !b3.w.equals("")) {
                    stringBuffer.append("&uid=" + b3.w);
                }
                stringBuffer.append("&locale=" + b3.g);
                Hashtable hashtable = b3.a(x, stringBuffer.toString());
                int n5 = Integer.parseInt(hashtable.get("code").toString());
                if (n5 == 0) {
                    b3.a((byte[])hashtable.get("data"));
                } else {
                    b3.a(b3.ax[25] + "(" + n5 + ")", 1);
                }
                b3.am = 0;
                break;
            }
            case 8: {
                boolean bl = false;
                if (this.h()) {
                    this.am = 0;
                    return;
                }
                g.a.a(this.c, (Displayable)this, this.B);
                String string = g.a.a(this.c, this.B)[2];
                if (string != null && !string.equals("")) {
                    this.i();
                    this.a(this.ax[18] + this.aA, 0);
                    this.am = 1;
                    this.c();
                }
                this.H = null;
                break;
            }
            case 9: {
                Vector<String> vector = new Vector<String>();
                vector.addElement("plog");
                this.b(vector);
                break;
            }
            case 10: {
                Vector<String> vector = new Vector<String>();
                vector.addElement("cre");
                this.b(vector);
                break;
            }
            case 11: {
                this.f();
                break;
            }
            case 12: {
                Vector<String> vector = new Vector<String>();
                vector.addElement("gc");
                if (this.d()) {
                    vector.addElement("userinfo");
                }
                this.a(this.ax[18] + this.aA, 0);
                this.b(vector);
                break;
            }
            case 13: {
                this.as = null;
                this.as = this.a("", this.ax[54], 100);
                if (this.as != null) {
                    this.a((Displayable)this.as);
                }
                if (this.aH != null && this.aH.equals(this.bh)) {
                    this.aG = this.as.getString();
                    if (this.aG == null || this.aG.equals("")) {
                        this.a(this.ax[53], 1);
                    } else {
                        Vector<String> vector = new Vector<String>();
                        vector.addElement("gc");
                        if (this.d()) {
                            vector.addElement("userinfo");
                        }
                        this.a(this.ax[18] + this.aA, 0);
                        this.b(vector);
                    }
                }
                this.as = null;
                break;
            }
            case 14: {
                this.as = this.a("", this.ax[49], 100);
                if (this.as != null) {
                    this.a((Displayable)this.as);
                }
                if (this.aH != null && this.aH.equals(this.bh)) {
                    this.aG = this.as.getString();
                    if (this.aG == null || this.aG.equals("")) {
                        this.a(this.ax[53], 1);
                    } else {
                        Vector<String> vector = new Vector<String>();
                        vector.addElement("gc");
                        if (this.d()) {
                            vector.addElement("userinfo");
                        }
                        this.a(this.ax[18] + this.aA, 0);
                        this.b(vector);
                    }
                }
                this.as = null;
                break;
            }
            case 15: {
                Vector<String> vector = new Vector<String>();
                vector.addElement("gc");
                if (this.d()) {
                    vector.addElement("userinfo");
                }
                this.a(this.ax[18] + this.aA, 0);
                this.b(vector);
            }
        }
        aB = false;
        this.am = 0;
    }

    private boolean d() {
        boolean bl = true;
        if (this.v != null && this.v.length() > 0 || this.t == null || this.t.equals("") || this.u == null || this.u.equals("")) {
            bl = false;
        }
        return bl;
    }

    private Vector a(boolean bl) {
        Vector<String> vector = new Vector<String>();
        if (bl) {
            vector.addElement("userinfo");
        }
        vector.addElement("qwp");
        for (int i2 = 0; i2 < this.s.length - 1; ++i2) {
            String string = ((Object[])this.s[i2])[1].toString();
            boolean bl2 = (Boolean)((Object[])this.s[i2])[4];
            if (string == null || string.equals("")) continue;
            if (bl) {
                vector.addElement(string);
                continue;
            }
            if (bl2) continue;
            vector.addElement(string);
        }
        if (!((Boolean)((Object[])this.s[this.s.length - 1])[3]).booleanValue()) {
            vector.addElement("cre");
        }
        return vector;
    }

    private void a(Graphics graphics, String string, String string2) {
        graphics.setFont(this.ac);
        graphics.setColor(Y);
        graphics.fillRect(this.aL - 2, this.aM - 3 - this.q, this.aK + 4, this.aJ + 4 + (this.q << 1) + 3);
        graphics.setColor(0xECECEC);
        graphics.fillRect(this.aL, this.aM, this.aK, this.aJ);
        graphics.drawRect(this.aL, this.aM + this.aJ + 1, this.aK / 2, this.q);
        graphics.drawRect(this.aL + this.aK / 2, this.aM + this.aJ + 1, this.aK / 2 - 1, this.q);
        graphics.drawRect(this.aL, this.aM - this.q - 1, this.aK - 1, this.q - 1);
        graphics.drawString(string, this.aL + (this.aK / 2 - this.ac.stringWidth(string)) / 2, this.aM + this.aJ + 1 + (this.q - this.ac.getHeight()) / 2, 0);
        graphics.setFont(this.aa);
        graphics.drawString(string2, this.aL + (this.aK / 2 - this.ac.stringWidth(string2)) / 2 + this.aK / 2, this.aM + this.aJ + 1 + (this.q - this.ac.getHeight()) / 2, 0);
        graphics.setClip(this.aL, this.aM - this.q - 1, this.aK - 1, this.q - 1);
        graphics.setFont(this.ac);
        if (this.ac.stringWidth(this.aO) < this.aK) {
            graphics.drawString(this.aO, this.aL + (this.aK - this.ac.stringWidth(this.aO)) / 2, this.aM - this.q - 1 + (this.q - this.ac.getHeight()) / 2, 0);
        } else {
            graphics.drawString(this.aO, this.aL + 4 + this.aT, this.aM - this.q - 1 + (this.q - this.ac.getHeight()) / 2, 0);
            if (this.aS) {
                this.aT -= 2;
                if (this.aT + this.ac.stringWidth(this.aO) < this.aK - 4) {
                    this.aS = !this.aS;
                }
            } else {
                this.aT += 2;
                if (this.aT >= 0) {
                    this.aS = !this.aS;
                }
            }
        }
        graphics.setClip(this.aL, this.aM, this.aK, this.aJ);
    }

    private void a(Graphics graphics) {
        int n2 = 0;
        graphics.setFont(this.aa);
        block5: for (int i2 = 0; i2 < bg.size(); ++i2) {
            Object[] objectArray = (Object[])bg.elementAt(i2);
            int n3 = 0;
            switch (Integer.parseInt(objectArray[0].toString())) {
                case 1: {
                    graphics.setColor(Integer.parseInt(objectArray[2].toString()));
                    objectArray = g.b.a(objectArray[1].toString(), this.aa, this.aK - 4);
                    for (n3 = 0; n3 < objectArray.length; ++n3) {
                        graphics.drawString((String)objectArray[n3], this.aL + 4, aP + this.aM + n2 + (this.q - this.aa.getHeight()) / 2, 0);
                        n2 += this.q;
                    }
                    continue block5;
                }
                case 0: {
                    Image image = (Image)objectArray[1];
                    graphics.drawImage(image, aQ + this.aL + 4, n2 + aP + this.aM + 2, 0);
                    n2 += image.getHeight();
                    if (image.getWidth() + 4 <= this.aK) continue block5;
                    if (this.aU) {
                        if ((aQ -= 2) + image.getWidth() + 4 >= this.aK - 4) continue block5;
                        this.aU = !this.aU;
                        continue block5;
                    }
                    if ((aQ += 2) < 0) continue block5;
                    this.aU = !this.aU;
                    continue block5;
                }
                case 2: {
                    graphics.setColor(Integer.parseInt(objectArray[2].toString()));
                    int n4 = Integer.parseInt(objectArray[1].toString());
                    graphics.fillRect(this.aL + 2, n2 + aP + this.aM, this.aK - 4, n4);
                    n2 += n4;
                }
            }
        }
        this.aw = n2;
    }

    private void b(String object) {
        try {
            if (!((String)object).equals("null")) {
                object = new e.a((String)object);
                for (int i2 = 0; i2 < ((e.a)object).a(); ++i2) {
                    c c2 = (c)((e.a)object).a(i2);
                    if (c2.a("title").equals("intro")) {
                        this.aV = c2.a("desc");
                        continue;
                    }
                    if (c2.a("title").equals("sms_context")) {
                        this.aX = c2.a("desc");
                        continue;
                    }
                    if (!c2.a("title").equals("prompt_context")) continue;
                    this.aW = c2.a("desc");
                }
            }
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    private String a(long l2, int n2) {
        StringBuffer stringBuffer = new StringBuffer();
        while (l2 != 0L) {
            stringBuffer.insert(0, this.aY[(int)(l2 & 0x1FL)]);
            l2 >>>= 5;
        }
        while (stringBuffer.length() < 5) {
            stringBuffer.insert(0, this.aY[0]);
        }
        return stringBuffer.toString();
    }

    private void e() {
        try {
            Vector vector = g.b.g();
            Vector vector2 = new Vector();
            this.aZ = new Vector();
            if (vector.size() > 0) {
                int n2 = 0;
                StringBuffer stringBuffer = new StringBuffer();
                for (int i2 = 0; i2 < vector.size(); ++i2) {
                    stringBuffer.append("{\"msisdn\":\"" + ((Hashtable)vector.elementAt(i2)).get("tel").toString() + "\",\"name\":\"" + ((Hashtable)vector.elementAt(i2)).get("name").toString() + "\"}");
                    if (++n2 == this.aj || i2 == vector.size() - 1) {
                        vector2 = this.a(stringBuffer.toString(), vector2);
                        stringBuffer = new StringBuffer();
                        n2 = 0;
                        continue;
                    }
                    stringBuffer.append(",");
                }
                this.aZ = g.b.a(vector, vector2);
                if (this.aZ.size() > 0) {
                    this.bc = 0;
                    this.ba = 0;
                    aP = 0;
                    bg = new Vector<Object[]>();
                    bg.addElement(g.b.a(1, g.a.a(g.a.a(this.aW, "$(count1)", vector2.size() + ""), "$(count2)", this.aZ.size() + ""), Q));
                    bf = g.b.a(this.ax[41], this.ax[32], "doing", "cancel", "11", "", this.ax[24]);
                    return;
                }
                this.a(this.ax[19] + vector2.size() + this.ax[20], 1);
                return;
            }
            this.a(this.ax[21], 1);
            return;
        }
        catch (Exception exception) {
            this.a(this.ax[22], 1);
            return;
        }
    }

    private void f() {
        this.bd = this.aZ.size() % this.bb > 0 ? this.aZ.size() / this.bb + 1 : this.aZ.size() / this.bb;
        this.bc = 0;
        this.ba = 0;
        aP = 0;
        ao = 2;
    }

    private void a(Vector cloneable) {
        aB = true;
        this.a(this.ax[47] + this.aA, 1);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < ((Vector)cloneable).size(); ++i2) {
            stringBuffer.append("{\"code\":\"" + ((Hashtable)((Vector)cloneable).elementAt(i2)).get("code").toString() + "\"}");
            if (i2 >= ((Vector)cloneable).size() - 1) continue;
            stringBuffer.append(",");
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("uid=" + this.w);
        stringBuffer2.append("&cp=" + this.bn);
        stringBuffer2.append("&gameCode=" + this.B);
        stringBuffer2.append("&keytext=cin");
        stringBuffer2.append("&key=[" + stringBuffer.toString() + "]");
        stringBuffer2.append("&locale=" + this.g);
        cloneable = this.a(x, stringBuffer2.toString());
        int n2 = Integer.parseInt(((Hashtable)cloneable).get("code").toString());
        if (n2 == 0) {
            this.a((byte[])((Hashtable)cloneable).get("data"));
        } else {
            this.a(this.ax[25] + "(" + n2 + ")", 1);
        }
        aB = true;
    }

    private Vector a(String string, Vector vector) {
        aB = true;
        try {
            ((b)object2).a(((b)object2).ax[40] + ((b)object2).aA, 0);
            Object object = new StringBuffer();
            ((StringBuffer)object).append("cmd=import");
            ((StringBuffer)object).append("&uid=" + ((b)object2).w);
            ((StringBuffer)object).append("&nickname=" + ((b)object2).t);
            ((StringBuffer)object).append("&gamecode=" + ((b)object2).B);
            ((StringBuffer)object).append("&rop=" + ((b)object2).D);
            ((StringBuffer)object).append("&phonebook=[" + string + "]");
            ((StringBuffer)object).append("&f=1");
            ((StringBuffer)object).append("&locale=" + ((b)object2).g);
            Object object2 = ((b)object2).a(y, ((StringBuffer)object).toString());
            int n2 = 0;
            if (Integer.parseInt(((Hashtable)object2).get("code").toString()) == 0) {
                object2 = new ByteArrayInputStream((byte[])((Hashtable)object2).get("data"));
                if (((String)(object2 = new DataInputStream((InputStream)object2).readUTF())).indexOf("[") != -1 && ((String)object2).indexOf("]") != -1) {
                    object2 = new e.a((String)object2);
                    for (n2 = 0; n2 < ((e.a)object2).a(); ++n2) {
                        object = (c)((e.a)object2).a(n2);
                        Hashtable<String, String> hashtable = new Hashtable<String, String>();
                        hashtable.put("tel", ((c)object).a("msisdn"));
                        hashtable.put("name", ((c)object).a("nickname"));
                        vector.addElement(hashtable);
                    }
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        aB = false;
        return vector;
    }

    private static Vector a(Vector vector, Vector vector2) {
        block0: for (int i2 = 0; i2 < vector2.size(); ++i2) {
            String string = ((Hashtable)vector2.elementAt(i2)).get("tel").toString();
            for (int i3 = 0; i3 < vector.size(); ++i3) {
                if (!g.a.a(g.a.a(((Hashtable)vector.elementAt(i3)).get("tel").toString(), "+", ""), " ", "").equals(g.a.a(g.a.a(string, "+", ""), " ", ""))) continue;
                vector.removeElementAt(i3);
                continue block0;
            }
        }
        return vector;
    }

    private static Vector g() {
        Vector vector = new Vector();
        try {
            ContactList contactList = null;
            String[] stringArray = PIM.getInstance().listPIMLists(1);
            for (int i2 = 0; i2 < stringArray.length && i2 < 1000; ++i2) {
                contactList = (ContactList)PIM.getInstance().openPIMList(1, 1, stringArray[i2]);
                Enumeration enumeration = contactList.items();
                while (enumeration.hasMoreElements()) {
                    String string = null;
                    Contact contact = (Contact)enumeration.nextElement();
                    string = contact.getString(105, 0);
                    if (string == null) continue;
                    for (int i3 = 0; i3 < 5; ++i3) {
                        String string2 = null;
                        try {
                            string2 = contact.getString(115, i3);
                        }
                        catch (Exception exception) {
                            break;
                        }
                        if (string2 == null) continue;
                        Hashtable<String, String> hashtable = new Hashtable<String, String>();
                        hashtable.put("select", "y");
                        hashtable.put("name", string);
                        hashtable.put("tel", string2);
                        vector.addElement(hashtable);
                    }
                    if (vector.size() < 999) continue;
                }
                if (contactList == null) continue;
                contactList.close();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return vector;
    }

    private Hashtable a(String string, String string2) {
        Hashtable hashtable = null;
        try {
            hashtable = this.b(string, string2);
            int n2 = Integer.parseInt(hashtable.get("code").toString());
            if (n2 == -3 && !this.A) {
                boolean bl = this.z = !this.z;
            }
            if (n2 < 0) {
                hashtable = this.b(string, string2);
            }
            if (Integer.parseInt(hashtable.get("code").toString()) == -5) {
                hashtable = this.b(string, string2);
            }
            return hashtable;
        }
        catch (Exception exception) {
            hashtable.put("code", "-7");
            return hashtable;
        }
    }

    private Hashtable b(String object, String object2) {
        Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
        try {
            Hashtable<String, String> hashtable2 = new Hashtable<String, String>();
            hashtable2.put("Content-Type", "application/x-www-form-urlencoded");
            object = new h.a((String)object, aI, (String)object2, hashtable2);
            new Hashtable();
            try {
                object2 = h.a.a((h.a)object, ((b)((Object)string)).z, "");
            }
            catch (Exception exception) {
                hashtable.put("code", "-3");
                return hashtable;
            }
            if (object2 == null) {
                hashtable.put("code", "-4");
                return hashtable;
            }
            int n2 = Integer.parseInt(((Hashtable)object2).get("respCode").toString());
            if (n2 >= 200 && n2 <= 299 && ((Hashtable)object2).get("data") != null) {
                ((b)((Object)string)).A = true;
                String string = ((Hashtable)object2).get("contType").toString();
                if (string.indexOf("audio/midi") != -1 || string.indexOf("image/") != -1) {
                    hashtable.put("code", "0");
                    hashtable.put("data", (byte[])((Hashtable)object2).get("data"));
                    return hashtable;
                }
                hashtable.put("code", "-5");
                return hashtable;
            }
            hashtable.put("code", "-6");
            return hashtable;
        }
        catch (Exception exception) {
            hashtable.put("code", "-8");
            return hashtable;
        }
    }

    private boolean h() {
        if (k) {
            k = false;
            bg = new Vector<Object[]>();
            bg.addElement(g.b.a(1, this.ax[48], Q));
            bf = g.b.a(this.ax[30], this.ax[32], "doing", "cancel", this.am + "", "", this.ax[24] + this.ax[56]);
            return true;
        }
        return false;
    }

    private static Hashtable a(String string, String string2, String string3, String string4, String string5, String string6, String string7) {
        aP = 0;
        aQ = 0;
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("L_Txt", string);
        hashtable.put("R_Txt", string2);
        hashtable.put("L_Act", string3);
        hashtable.put("R_Act", string4);
        hashtable.put("L_Value", string5);
        hashtable.put("R_Value", string6);
        hashtable.put("title", string7);
        ao = 10;
        return hashtable;
    }

    private static Object[] a(int n2, Object object, int n3) {
        Object[] objectArray = new Object[3];
        Object[] objectArray2 = objectArray;
        objectArray[0] = n2 + "";
        objectArray2[1] = object;
        objectArray2[2] = n3 + "";
        return objectArray2;
    }

    private void b(Vector object) {
        if (this.h()) {
            return;
        }
        String string = "";
        for (int i2 = 0; i2 < ((Vector)object).size(); ++i2) {
            string = string.equals("") ? ((Vector)object).elementAt(i2).toString() : string + ":" + ((Vector)object).elementAt(i2).toString();
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (this.t != null && !this.t.equals("")) {
            if (this.v != null && !this.v.equals("")) {
                stringBuffer.append("key=" + this.v);
            } else {
                stringBuffer.append("u=" + this.t);
                stringBuffer.append("&p=" + this.u);
            }
        }
        if (this.am == 12) {
            object = ((Object[])this.H[1])[5].toString();
            stringBuffer.append("&t=d");
            stringBuffer.append("&oid=" + ((String)object).substring("GETPL=".length(), ((String)object).length()));
        } else if (this.am == 13) {
            object = ((Object[])this.H[1])[5].toString();
            stringBuffer.append("&t=r");
            stringBuffer.append("&oid=" + ((String)object).substring("GETPL=".length(), ((String)object).length()));
            stringBuffer.append("&cnt=" + this.aG);
        } else if (this.am == 14) {
            stringBuffer.append("&t=s");
            stringBuffer.append("&cnt=" + this.aG);
        } else if (this.am == 15) {
            object = ((Object[])this.H[1])[5].toString();
            stringBuffer.append("&t=v");
            stringBuffer.append("&pn=" + ((String)object).substring("plPage=".length(), ((String)object).length()));
        } else {
            stringBuffer.append("&t=v");
        }
        stringBuffer.append("&cp=" + this.bn);
        stringBuffer.append("&ua=" + this.bo);
        stringBuffer.append("&gameCode=" + this.B);
        stringBuffer.append("&keytext=" + string);
        stringBuffer.append("&locale=" + this.g);
        object = this.a(x, stringBuffer.toString());
        int n2 = Integer.parseInt(((Hashtable)object).get("code").toString());
        if (n2 == 0) {
            this.a((byte[])((Hashtable)object).get("data"));
        } else {
            this.a(this.ax[25] + "(" + n2 + ")", 1);
        }
        this.am = 0;
    }

    private int c(String string) {
        int n2 = -1;
        if (string.equals("userinfo")) {
            return 999;
        }
        if (string.equals("doscore")) {
            return 998;
        }
        if (string.equals("qgi")) {
            return 997;
        }
        if (string.equals("qwp")) {
            return 996;
        }
        if (string.equals("cin")) {
            return 995;
        }
        for (int i2 = 0; i2 < this.s.length; ++i2) {
            if (!((Object[])this.s[i2])[1].toString().equals(string)) continue;
            n2 = i2;
            break;
        }
        return n2;
    }

    private void a(byte[] object) {
        if (this.am == 0) {
            return;
        }
        try {
            int n2;
            boolean bl = false;
            Object object2 = null;
            object = new ByteArrayInputStream((byte[])object);
            DataInputStream dataInputStream = new DataInputStream((InputStream)object);
            Object object3 = dataInputStream.readUTF();
            object3 = new e.a((String)object3);
            block20: for (int i2 = 0; i2 < ((e.a)object3).a(); ++i2) {
                c c2 = (c)((e.a)object3).a(i2);
                int n3 = this.c(c2.a("q"));
                String string = dataInputStream.readUTF();
                n2 = Integer.parseInt(c2.a("r"));
                switch (n3) {
                    case 999: {
                        this.a(string, n2, c2.a("d"));
                        continue block20;
                    }
                    case 0: {
                        continue block20;
                    }
                    case 4: 
                    case 5: 
                    case 6: {
                        this.b(n3, string, n2, c2.a("d"));
                        continue block20;
                    }
                    case 7: {
                        this.e(n3, string, n2, c2.a("d"));
                        continue block20;
                    }
                    case 1: 
                    case 998: {
                        this.a(n3, string, n2, c2.a("d"));
                        continue block20;
                    }
                    case 3: {
                        this.d(n3, string, n2, c2.a("d"));
                        continue block20;
                    }
                    case 8: 
                    case 997: {
                        c2.a("d");
                        object2 = g.b.a(string);
                        continue block20;
                    }
                    case 2: {
                        this.c(n3, string, n2, c2.a("d"));
                        continue block20;
                    }
                    case 996: {
                        c2.a("d");
                        this.b(string);
                        continue block20;
                    }
                    case 995: {
                        this.a(c2.a("d"), 1);
                    }
                }
            }
            if (object2 == null) {
                if (this.am == 7) {
                    this.a(this.ax[25] + "(-9)", 1);
                }
                return;
            }
            int n4 = Integer.parseInt(((Hashtable)object2).get("imgSize").toString());
            Object object4 = null;
            if (n4 > 0) {
                boolean bl2 = false;
                byte[] byArray = new byte[256];
                int n5 = 0;
                n2 = 0;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                while ((n2 = dataInputStream.read(byArray, n5, 256 - n5)) >= 0) {
                    byteArrayOutputStream.write(byArray, n5, n2);
                    if ((n5 += n2) < 256) continue;
                    n5 = 0;
                }
                object4 = byteArrayOutputStream.toByteArray();
            }
            if (this.am == 1) {
                this.bj[0] = "0";
                this.bj[1] = ((Hashtable)object2).get("newGameName").toString();
                this.bj[2] = ((Hashtable)object2).get("newGameLink").toString();
                this.bj[3] = ((Hashtable)object2).get("newGameInfo").toString();
                try {
                    if (object4 != null) {
                        this.bk = Image.createImage((byte[])object4, (int)0, (int)((byte[])object4).length);
                    }
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                Object object5 = this;
                object2 = new ByteArrayOutputStream();
                object4 = new DataOutputStream((OutputStream)object2);
                try {
                    ((DataOutputStream)object4).writeUTF(((b)object5).bj[0]);
                    ((DataOutputStream)object4).writeUTF(((b)object5).bj[1]);
                    ((DataOutputStream)object4).writeUTF(((b)object5).bj[2]);
                    ((DataOutputStream)object4).writeUTF(((b)object5).bj[3]);
                    object5 = ((ByteArrayOutputStream)object2).toByteArray();
                    g.b.a(1, (byte[])object5, "lakooRms");
                    ((FilterOutputStream)object4).close();
                    ((ByteArrayOutputStream)object2).close();
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                this.b();
                ((Object[])this.s[this.c((String)"cre")])[3] = new Boolean(true);
            } else {
                aP = 0;
                aQ = 0;
                this.aU = true;
                bg = new Vector();
                try {
                    if (object4 != null) {
                        bg.addElement(g.b.a(0, Image.createImage(object4, (int)0, (int)(object4).length), Q));
                    }
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                bg.addElement(g.b.a(1, ((Hashtable)object2).get("newGameInfo").toString(), Q));
                bf = g.b.a(this.ax[31], this.ax[32], "jump_wap", "cancel", ((Hashtable)object2).get("newGameLink").toString(), "", ((Hashtable)object2).get("newGameName").toString());
            }
            dataInputStream.close();
            ((ByteArrayInputStream)object).close();
            this.J = null;
            return;
        }
        catch (Exception exception) {
            this.a(this.ax[27] + "(-7)", 1);
            return;
        }
    }

    protected final void paint(Graphics graphics) {
        int n2;
        int n3;
        int n4;
        Object[] objectArray;
        if (!this.aC || ao != 10) {
            this.F = 0;
            graphics.setClip(0, 0, this.n, this.o);
            g.b.a(graphics, this.R, 0, this.n, this.o + 30);
            this.ad = 0;
            for (int i2 = 0; i2 < this.r.length; ++i2) {
                if (this.r[i2] == null) continue;
                Vector vector = (Vector)this.r[i2];
                int n5 = vector.size();
                int n6 = 0;
                if (!((Boolean)((Object[])this.s[i2])[2]).booleanValue() && n5 > 2) {
                    n5 = 2;
                }
                for (n6 = 0; n6 < n5; ++n6) {
                    Object object = (Object[])vector.elementAt(n6);
                    int n7 = Integer.parseInt(object[0].toString());
                    int n8 = Integer.parseInt(object[3].toString());
                    int n9 = Integer.parseInt(object[2].toString());
                    g.b.a(graphics, n8, this.ae + this.F, this.n, n9);
                    object = (Vector)object[1];
                    block17: for (n8 = 0; n8 < ((Vector)object).size(); ++n8) {
                        objectArray = (Object[])((Vector)object).elementAt(n8);
                        int n10 = Integer.parseInt(objectArray[0].toString());
                        if (n10 == 0 || n10 == 1 || n10 == 3) {
                            n4 = 0;
                            if (((Boolean)objectArray[1]).booleanValue()) {
                                ++this.ad;
                            }
                        }
                        if (this.ae + this.F >= this.o - this.q || this.ae + this.F + n9 <= 0) continue;
                        switch (n10) {
                            case 0: {
                                n3 = n7;
                                n2 = n9;
                                Object[] objectArray2 = objectArray;
                                Graphics graphics2 = graphics;
                                objectArray = this;
                                boolean bl = (Boolean)objectArray2[1];
                                Object object2 = (Image)objectArray2[2];
                                int n11 = objectArray.F + g.b.a(Integer.parseInt(objectArray2[4].toString()), n2, object2.getHeight());
                                int n12 = Integer.parseInt(objectArray2[3].toString());
                                super.a(0, bl, objectArray2, n11, n2, n3);
                                graphics2.drawImage(object2, n12, objectArray.ae + n11, 0);
                                if (objectArray.H == null || objectArray2 != objectArray.H[1]) continue block17;
                                graphics2.setColor(objectArray.V);
                                graphics2.drawRect(n12, objectArray.ae + n11, object2.getWidth(), object2.getHeight());
                                continue block17;
                            }
                            case 1: {
                                int n13;
                                n3 = n7;
                                n2 = n9;
                                Object[] objectArray3 = objectArray;
                                Graphics graphics3 = graphics;
                                objectArray = this;
                                boolean bl = (Boolean)objectArray3[1];
                                Object object2 = (Font)objectArray3[6];
                                int n14 = objectArray.F + g.b.a(Integer.parseInt(objectArray3[4].toString()), n2, object2.getHeight());
                                int n12 = Integer.parseInt(objectArray3[7].toString());
                                int n15 = Integer.parseInt(objectArray3[3].toString());
                                String string = g.a.a(objectArray3[2].toString(), "${GAME}", objectArray.C);
                                super.a(1, bl, objectArray3, n14, n2, n3);
                                int n16 = 0;
                                if (objectArray.H != null && objectArray3 == objectArray.H[1]) {
                                    if (objectArray3[5].toString().equals("TITLE")) {
                                        n13 = 0;
                                        if (!((Boolean)((Object[])objectArray.s[n3])[2]).booleanValue() || n3 == objectArray.s.length - 1) {
                                            g.b.a(graphics3, objectArray.Z, objectArray.ae + objectArray.F, objectArray.n, n2);
                                            n12 = Q;
                                        } else {
                                            g.b.a(graphics3, Y, objectArray.ae + objectArray.F, objectArray.n, n2);
                                            n12 = objectArray.P;
                                        }
                                        n16 = 1;
                                    } else {
                                        graphics3.setColor(objectArray.U);
                                        graphics3.fillRect(n15 - 1, objectArray.ae + n14 - 1, object2.stringWidth(string) + 2, object2.getHeight() + 2);
                                        graphics3.setColor(objectArray.V);
                                        graphics3.drawRect(n15 - 1, objectArray.ae + n14 - 1, object2.stringWidth(string) + 2, object2.getHeight() + 2);
                                    }
                                }
                                graphics3.setFont((Font)object2);
                                graphics3.setColor(n12);
                                if (n16 != 0) {
                                    if (n15 + object2.stringWidth(string) > objectArray.n) {
                                        if (objectArray.ag) {
                                            --objectArray.af;
                                            if (objectArray.af + n15 + object2.stringWidth(string) < objectArray.n - 10) {
                                                objectArray.ag = false;
                                            }
                                        } else {
                                            ++objectArray.af;
                                            if (objectArray.af > 0) {
                                                objectArray.ag = true;
                                            }
                                        }
                                    }
                                    graphics3.drawString(string, objectArray.af + n15, objectArray.ae + n14, 0);
                                    continue block17;
                                }
                                graphics3.drawString(string, n15, objectArray.ae + n14, 0);
                                continue block17;
                            }
                            case 2: {
                                Object[] objectArray4 = objectArray;
                                Graphics graphics4 = graphics;
                                objectArray = this;
                                n2 = Integer.parseInt(objectArray4[4].toString());
                                graphics4.setColor(n2);
                                graphics4.fillRect(Integer.parseInt(objectArray4[1].toString()), objectArray.ae + objectArray.F, Integer.parseInt(objectArray4[2].toString()), Integer.parseInt(objectArray4[3].toString()));
                                continue block17;
                            }
                            case 3: {
                                n3 = n7;
                                n2 = n9;
                                Object[] objectArray5 = objectArray;
                                Graphics graphics5 = graphics;
                                objectArray = this;
                                boolean bl = (Boolean)objectArray5[1];
                                Object object2 = objectArray5[2].toString();
                                String string = objectArray5[3].toString();
                                int n12 = Integer.parseInt(objectArray5[4].toString());
                                int n15 = Integer.parseInt(objectArray5[5].toString());
                                int n17 = Integer.parseInt(objectArray5[6].toString());
                                int n16 = Integer.parseInt(objectArray5[7].toString());
                                int n13 = Integer.parseInt(objectArray5[8].toString());
                                String string2 = objectArray5[9].toString();
                                Font font = (Font)objectArray5[10];
                                int n18 = Integer.parseInt(objectArray5[11].toString());
                                string2 = g.a.a(g.a.a(string2, "${username}", objectArray.t), "${password}", objectArray.u);
                                int n19 = objectArray.F + g.b.a(n13, n2, n2);
                                super.a(3, bl, objectArray5, n19, n2, n3);
                                graphics5.setColor(n16);
                                if (objectArray.H != null && objectArray5 == objectArray.H[1]) {
                                    graphics5.setColor(objectArray.V);
                                }
                                graphics5.fillRect(n12 + font.stringWidth((String)object2) + 2, objectArray.ae + n19, n15, n17);
                                graphics5.setColor(Q);
                                graphics5.drawRect(n12 + font.stringWidth((String)object2) + 2, objectArray.ae + n19, n15, n17);
                                n19 = objectArray.F + g.b.a(n13, n2, font.getHeight());
                                graphics5.setFont(font);
                                graphics5.setColor(n18);
                                graphics5.drawString((String)object2, n12, objectArray.ae + n19, 0);
                                if (string.equals("password")) {
                                    if (objectArray.u != null && !objectArray.u.equals("")) {
                                        graphics5.drawString("******", n12 + font.stringWidth((String)object2) + 5, objectArray.ae + n19, 0);
                                        continue block17;
                                    }
                                    graphics5.drawString("", n12 + font.stringWidth((String)object2) + 5, objectArray.ae + n19, 0);
                                    continue block17;
                                }
                                graphics5.drawString(string2, n12 + font.stringWidth((String)object2) + 5, objectArray.ae + n19, 0);
                            }
                        }
                    }
                    this.F += n9;
                }
            }
        }
        if (this.am > 0 || ao == 1) {
            Graphics graphics6 = graphics;
            objectArray = this;
            graphics6.setFont(objectArray.aa);
            graphics6.setColor(0x333333);
            graphics6.fillRect((objectArray.n - objectArray.ap) / 2 - 5, (objectArray.o - objectArray.aq) / 2 - 3, objectArray.ap + 10, objectArray.aq + 6);
            graphics6.setColor(objectArray.P);
            for (n4 = 0; n4 < objectArray.an.length; ++n4) {
                graphics6.drawString(objectArray.an[n4], (objectArray.n - objectArray.ap) / 2, (objectArray.o - objectArray.aq) / 2 + objectArray.q * n4 + (objectArray.q - objectArray.aa.getHeight()) / 2, 0);
            }
        }
        if (ao == 2) {
            this.a(graphics, this.ax[28], this.ax[32]);
            Graphics graphics7 = graphics;
            objectArray = this;
            this.aO = g.a.a(g.a.a(objectArray.ax[38], "${pageCount}", objectArray.bd + ""), "${page}", objectArray.bc + 1 + "");
            graphics7.setFont(objectArray.aa);
            if ((objectArray.ba + 1) * objectArray.q + aP > objectArray.aJ) {
                aP -= objectArray.q;
            }
            if (objectArray.ba * objectArray.q + aP < 0) {
                aP += objectArray.q;
            }
            n4 = 0;
            for (n2 = 0; n2 < objectArray.bb; ++n2) {
                n3 = n2 + objectArray.bc * objectArray.bb;
                if (n3 >= objectArray.aZ.size()) continue;
                String string = null;
                if (objectArray.ba == n2) {
                    graphics7.setColor(objectArray.U);
                    graphics7.fillRect(objectArray.aL, aP + objectArray.aM + objectArray.q * n2, objectArray.aK - 1, objectArray.q);
                    graphics7.setColor(objectArray.W);
                    graphics7.drawRect(objectArray.aL, aP + objectArray.aM + objectArray.q * n2, objectArray.aK - 1, objectArray.q);
                    string = ((Hashtable)objectArray.aZ.elementAt(n3)).get("tel").toString();
                } else {
                    string = ((Hashtable)objectArray.aZ.elementAt(n3)).get("name").toString();
                }
                graphics7.setColor(Q);
                graphics7.drawRect(objectArray.aL + 5, aP + objectArray.aM + (objectArray.q - 5) / 2 + objectArray.q * n2 - 3, 10, 10);
                if (((Hashtable)objectArray.aZ.elementAt(n3)).get("select").toString().equals("y")) {
                    graphics7.setColor(X);
                    graphics7.fillRect(objectArray.aL + 8, aP + objectArray.aM + (objectArray.q - 5) / 2 + objectArray.q * n2, 5, 5);
                }
                graphics7.setColor(Q);
                graphics7.drawString(string, objectArray.aL + 20, aP + objectArray.aM + (objectArray.q - objectArray.aa.getHeight()) / 2 + objectArray.q * n2, 0);
                n4 += objectArray.q;
            }
            objectArray.aR = n4;
        }
        if (ao == 10) {
            this.a(graphics, bf.get("L_Txt").toString(), bf.get("R_Txt").toString());
            this.aO = bf.get("title").toString();
            this.a(graphics);
        }
        this.ah = this.F;
        Graphics graphics8 = graphics;
        objectArray = this;
        graphics8.setColor(Y);
        graphics8.fillRect(0, objectArray.o - objectArray.q, objectArray.n, objectArray.q);
        graphics8.setFont(objectArray.aa);
        graphics8.setColor(objectArray.P);
        String string = objectArray.ax[32];
        switch (b) {
            case 1: {
                string = objectArray.ax[33];
                break;
            }
            case 2: {
                string = objectArray.ax[39];
            }
        }
        graphics8.drawString(string, 5, objectArray.o - objectArray.q + (objectArray.q - objectArray.aa.getHeight()) / 2, 0);
        graphics8.drawString(objectArray.bl, objectArray.n - 5 - objectArray.aa.stringWidth(objectArray.bl), objectArray.o - objectArray.q + (objectArray.q - objectArray.aa.getHeight()) / 2, 0);
        graphics8 = graphics;
        objectArray = this;
        int n20 = 0;
        n2 = 0;
        n3 = 0;
        int n21 = 0;
        int n22 = 0;
        switch (ao) {
            case 0: {
                n20 = objectArray.ae;
                n2 = objectArray.ah;
                n22 = objectArray.o - objectArray.q;
                n3 = 0;
                n21 = objectArray.o - objectArray.q - 7;
                break;
            }
            case 2: {
                n20 = aP;
                n2 = objectArray.aR;
                n22 = objectArray.aJ;
                n3 = objectArray.aM;
                n21 = objectArray.aM + n22 - 7;
                break;
            }
            case 10: {
                n20 = aP;
                n2 = objectArray.aw;
                n22 = objectArray.aJ;
                n3 = objectArray.aM;
                n21 = objectArray.aM + n22 - 7;
            }
        }
        if (System.currentTimeMillis() - objectArray.ak > objectArray.al) {
            n3 += 6;
            n21 -= 6;
            objectArray.ak = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - objectArray.ak > objectArray.al) {
            objectArray.ak = System.currentTimeMillis();
        }
        if (n20 < 0) {
            graphics8.setColor(0xFFFF66);
            graphics8.fillTriangle(objectArray.n / 2, n3, objectArray.n / 2 - 7, n3 + 7, objectArray.n / 2 + 7, n3 + 7);
            graphics8.setColor(0x999900);
            graphics8.drawLine(objectArray.n / 2, n3, objectArray.n / 2 - 7, n3 + 7);
            graphics8.drawLine(objectArray.n / 2, n3, objectArray.n / 2 + 7, n3 + 7);
            graphics8.drawLine(objectArray.n / 2 + 7, n3 + 7, objectArray.n / 2 - 7, n3 + 7);
        }
        if (n20 + n2 > n22) {
            graphics8.setColor(0xFFFF66);
            graphics8.fillTriangle(objectArray.n / 2 - 7, n21, objectArray.n / 2 + 7, n21, objectArray.n / 2, n21 + 7);
            graphics8.setColor(0x999900);
            graphics8.drawLine(objectArray.n / 2 - 7, n21, objectArray.n / 2 + 7, n21);
            graphics8.drawLine(objectArray.n / 2 + 7, n21, objectArray.n / 2, n21 + 7);
            graphics8.drawLine(objectArray.n / 2 - 7, n21, objectArray.n / 2, n21 + 7);
        }
    }

    private static void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        if (n2 >= 0) {
            graphics.setColor(n2);
            graphics.fillRect(0, n3, n4, n5);
        }
    }

    private void a(String string, int n2) {
        this.an = g.b.a(string, this.aa, this.n / 4 * 3);
        this.ap = 0;
        this.aq = 0;
        for (int i2 = 0; i2 < this.an.length; ++i2) {
            int n3 = this.aa.stringWidth(this.an[i2]);
            if (n3 <= this.ap) continue;
            this.ap = n3;
        }
        this.aq = this.q * this.an.length;
        ao = n2;
    }

    private void a(int n2, boolean bl, Object[] objectArray, int n3, int n4, int n5) {
        if (bl) {
            if (this.H == null) {
                this.H = g.b.a(this.ad, objectArray, n3, n4, n5);
            } else if (((Object[])this.H[1]).equals(objectArray)) {
                this.H[0] = this.ad + "";
                this.H[2] = n3 + "";
                this.H[3] = n4 + "";
                this.H[4] = n5 + "";
            }
            if (this.H != null) {
                if (Integer.parseInt(this.H[0].toString()) + 1 == this.ad && this.J == null) {
                    this.J = g.b.a(this.ad, objectArray, n3, n4, n5);
                }
                if (this.J != null && Integer.parseInt(this.H[0].toString()) + 1 != Integer.parseInt(this.J[0].toString())) {
                    this.J = null;
                }
                if (Integer.parseInt(this.H[0].toString()) - 1 == this.ad && this.I == null) {
                    this.I = g.b.a(this.ad, objectArray, n3, n4, n5);
                }
                if (this.I != null && Integer.parseInt(this.H[0].toString()) - 1 != Integer.parseInt(this.I[0].toString())) {
                    this.I = null;
                }
            }
        }
        if (this.L == this.N) {
            if (this.J != null) {
                if (((Object[])this.J[1]).equals(objectArray)) {
                    this.H = g.b.a(this.ad, objectArray, n3, n4, n5);
                    this.L = 0;
                    this.J = null;
                    this.I = null;
                }
            } else if (this.ae + this.ah > this.o - (this.q << 1)) {
                this.ae -= this.ai;
                this.L = 0;
                this.J = null;
                this.I = null;
            }
        }
        if (this.L == this.M) {
            if (this.I != null) {
                if (((Object[])this.I[1]).equals(objectArray)) {
                    this.H = g.b.a(this.ad, objectArray, n3, n4, n5);
                    this.L = 0;
                    this.J = null;
                    this.I = null;
                }
            } else {
                if (this.ae < 0) {
                    this.ae += this.ai;
                    this.J = null;
                    this.I = null;
                }
                if (this.ae > 0) {
                    this.ae = 0;
                }
                this.L = 0;
            }
        }
        if (this.L == this.O && this.H != null && this.H[1].equals(objectArray)) {
            switch (n2) {
                case 1: {
                    Object object = ((Object[])this.H[1])[5].toString();
                    if (((String)object).equals("TITLE")) {
                        boolean bl2 = false;
                        if (!((Boolean)((Object[])this.s[n5])[3]).booleanValue()) {
                            this.a(this.ax[18] + this.aA, 0);
                            this.am = 1;
                        } else {
                            this.b(Integer.parseInt(this.H[4].toString()));
                        }
                    } else if (((String)object).equals("REST")) {
                        this.H = this.I;
                        this.a(this.ax[18] + this.aA, 0);
                        this.am = 9;
                    } else if (((String)object).equals("REST_CRE")) {
                        this.a(this.ax[18] + this.aA, 0);
                        boolean bl3 = false;
                        this.am = !((Boolean)((Object[])this.s[this.c("loadscore")])[2]).booleanValue() ? 1 : 10;
                    } else if (((String)object).equals("SHOW_PSW")) {
                        object = this;
                        Vector vector = null;
                        vector = (Vector)((Object[])((Vector)((b)object).r[0]).elementAt(2))[1];
                        if (((Object[])vector.elementAt(0))[2].toString().indexOf("****") > 0) {
                            ((Object[])vector.elementAt((int)0))[2] = ((b)object).ax[1] + ((b)object).u;
                            ((Object[])vector.elementAt((int)1))[2] = ((b)object).ax[12];
                        } else {
                            ((Object[])vector.elementAt((int)0))[2] = ((b)object).ax[1] + "******";
                            ((Object[])vector.elementAt((int)1))[2] = ((b)object).ax[11];
                        }
                    } else if (((String)object).equals("REG_ID")) {
                        this.a(this.ax[36], 0);
                        this.am = 8;
                    } else if (((String)object).equals("OST")) {
                        object = ((Object[])this.H[1])[8].toString();
                        if (((String)object).length() > 0) {
                            this.a((String)object, 1);
                        }
                    } else if (((String)object).indexOf("MSGBOX=") == 0) {
                        this.a(this.ax[18] + this.aA, 0);
                        this.am = 7;
                    } else if (((String)object).indexOf("http://") == 0) {
                        this.f((String)object);
                    } else if (((String)object).indexOf("poll:") != -1) {
                        this.a(this.ax[34] + this.aA, 0);
                        this.am = 2;
                    } else if (((String)object).endsWith("upPhoneMumber")) {
                        this.a(this.ax[35] + this.aA, 0);
                        this.am = 3;
                    } else if (((String)object).indexOf("GETPL=") != -1) {
                        this.a(this.ax[18] + this.aA, 0);
                        this.am = 12;
                    } else if (((String)object).equals("sendPL")) {
                        this.am = 14;
                    } else if (((String)object).indexOf("plPage=") != -1) {
                        this.am = 15;
                    }
                    this.L = 0;
                    break;
                }
                case 0: {
                    break;
                }
                case 3: {
                    this.a(this.ax[36], 0);
                    String string = null;
                    string = g.a.a(g.a.a(((Object[])this.H[1])[9].toString(), "${username}", this.t), "${password}", this.u);
                    this.as = this.a(string, ((Object[])this.H[1])[3].toString(), 12);
                    this.am = 4;
                    this.L = 0;
                }
            }
        }
        if (this.H != null && ao == 0 && this.K != null && this.H[1].equals(this.K.get("cont")) && this.K.get("count").equals("0")) {
            this.a(((Object[])this.K.get("cont"))[8].toString(), 1);
            this.K.put("count", "1");
        }
    }

    private static int a(int n2, int n3, int n4) {
        int n5 = 0;
        switch (n2) {
            case 0: {
                n5 = 0;
                break;
            }
            case 1: {
                n5 = (n3 - n4) / 2;
                break;
            }
            case 2: {
                n5 = n3 - n4;
            }
        }
        return n5;
    }

    protected final void pointerPressed(int n2, int n3) {
        if (this.am > 0) {
            return;
        }
        if (ao == 1) {
            ao = 0;
            return;
        }
        if (ao == 2) {
            if (n2 > this.aL && n2 < this.aL + this.aK / 2 && n3 > this.aM + this.aJ && n3 < this.aM + this.aJ + this.q) {
                this.a(this.ax[23], 0);
                this.am = 6;
                return;
            }
            if (n2 > this.aL + this.aK / 2 && n2 < this.aL + this.aK && n3 > this.aM + this.aJ && n3 < this.aM + this.aJ + this.q) {
                ao = 0;
                return;
            }
            if (n2 > this.aL && n2 < this.aL + this.aK && n3 > this.aM - this.q && n3 < this.aM + this.aJ) {
                this.aC = !this.aC;
                this.a();
                this.f();
            }
            return;
        }
        if (ao == 10) {
            if (n2 > this.aL && n2 < this.aL + this.aK / 2 && n3 > this.aM + this.aJ && n3 < this.aM + this.aJ + this.q) {
                this.d("left");
                return;
            }
            if (n2 > this.aL + this.aK / 2 && n2 < this.aL + this.aK && n3 > this.aM + this.aJ && n3 < this.aM + this.aJ + this.q) {
                this.d("right");
                return;
            }
            if (n2 > this.aL && n2 < this.aL + this.aK && n3 > this.aM - this.q && n3 < this.aM + this.aJ) {
                this.aC = !this.aC;
                this.a();
            }
            return;
        }
        if (n2 >= 0 && n2 <= this.aa.stringWidth(this.ax[39]) && n3 >= this.o - this.q) {
            E = false;
            return;
        }
        if (n2 >= this.n - this.aa.stringWidth("lakoo.cn") - 5 && n2 <= this.n && n3 >= this.o - this.q) {
            this.f(this.bm);
            return;
        }
    }

    private void d(String string) {
        String string2 = null;
        String string3 = null;
        if (string.equals("right")) {
            string2 = bf.get("R_Act").toString();
            string3 = bf.get("R_Value").toString();
        } else {
            string2 = bf.get("L_Act").toString();
            string3 = bf.get("L_Value").toString();
        }
        if (string2.equals("doing")) {
            this.am = Integer.parseInt(string3);
            ao = 0;
            bg = null;
            bf = null;
            return;
        }
        if (string2.equals("cancel")) {
            ao = 0;
            bg = null;
            bf = null;
            return;
        }
        if (string2.equals("jump_wap")) {
            this.f(string3);
            bg = null;
            bf = null;
            return;
        }
        if (string2.equals("exit")) {
            ao = 0;
            E = false;
        }
    }

    protected final void keyPressed(int n2) {
        if (this.am > 0) {
            return;
        }
        if (ao == 1) {
            ao = 0;
            return;
        }
        if (ao == 2) {
            if (n2 == this.l) {
                this.a(this.ax[23], 0);
                this.am = 6;
                return;
            }
            if (n2 == this.m) {
                this.aZ = null;
                ao = 0;
                return;
            }
            if (n2 == 48) {
                for (n2 = 0; n2 < this.aZ.size(); ++n2) {
                    ((Hashtable)this.aZ.elementAt(n2)).put("select", this.be);
                }
                if (this.be.equals("y")) {
                    this.be = "n";
                    return;
                }
                this.be = "y";
                return;
            }
            if (n2 == 35) {
                this.aC = !this.aC;
                this.a();
                this.f();
                return;
            }
            switch (this.getGameAction(n2)) {
                case 6: {
                    ++this.ba;
                    if (this.ba >= this.bb - 1) {
                        this.ba = this.bb - 1;
                    }
                    if (this.ba + this.bc * this.bb <= this.aZ.size() - 1) break;
                    this.ba = this.aZ.size() % this.bb - 1;
                    return;
                }
                case 1: {
                    --this.ba;
                    if (this.ba > 0) break;
                    this.ba = 0;
                    return;
                }
                case 2: {
                    if (this.bc-- <= 0) {
                        this.bc = 0;
                    }
                    this.ba = 0;
                    aP = 0;
                    return;
                }
                case 5: {
                    ++this.bc;
                    if (this.bc >= this.bd - 1) {
                        this.bc = this.bd - 1;
                    }
                    this.ba = 0;
                    aP = 0;
                    return;
                }
                case 8: {
                    n2 = this.ba + this.bc * this.bb;
                    Object var2_2 = null;
                    if (((Hashtable)this.aZ.elementAt(n2)).get("select").toString().equals("y")) {
                        ((Hashtable)this.aZ.elementAt(n2)).put("select", "n");
                        return;
                    }
                    ((Hashtable)this.aZ.elementAt(n2)).put("select", "y");
                }
            }
            return;
        }
        if (ao == 10) {
            if (n2 == this.l) {
                this.d("left");
                return;
            }
            if (n2 == this.m) {
                this.d("right");
                return;
            }
            if (n2 == 35) {
                this.aC = !this.aC;
                this.a();
            }
            switch (this.getGameAction(n2)) {
                case 6: {
                    if (aP + this.aw <= this.aJ) break;
                    aP -= this.q;
                    return;
                }
                case 1: {
                    if (aP >= 0) break;
                    aP += this.q;
                    return;
                }
                case 2: {
                    return;
                }
                case 5: {
                    return;
                }
                case 8: {
                    this.d("ok");
                }
            }
            return;
        }
        if (n2 == this.l) {
            E = false;
            return;
        }
        if (n2 == this.m) {
            this.f(this.bm);
            return;
        }
        this.af = 0;
        n2 = this.getGameAction(n2);
        switch (n2) {
            case 6: {
                this.L = this.N;
                return;
            }
            case 1: {
                this.L = this.M;
                return;
            }
            case 8: {
                this.L = this.O;
                return;
            }
            case 2: 
            case 5: {
                int n3 = 0;
                if (((Object[])this.H[1])[5].toString().equals("TITLE")) {
                    n3 = Integer.parseInt(this.H[4].toString());
                    boolean bl = (Boolean)((Object[])this.s[n3])[2];
                    n3 = bl ? 1 : 0;
                    if (bl && n2 == 2) {
                        this.L = this.O;
                        return;
                    }
                    if (n3 == 0 && n2 == 5) {
                        this.L = this.O;
                    }
                    return;
                }
                if (n2 == 2) {
                    this.L = this.M;
                    return;
                }
                this.L = this.N;
            }
        }
    }

    public final void commandAction(Command command, Displayable displayable) {
        if (displayable == this.as) {
            if (command == this.bi) {
                this.aH = this.bi;
            } else {
                this.aH = this.bh;
                if (this.as.getTitle().equals("userName")) {
                    this.t = this.as.getString();
                } else if (this.as.getTitle().equals("password")) {
                    this.u = this.as.getString();
                }
            }
            this.av = false;
        }
    }

    private static Image e(String string) {
        Image image = null;
        try {
            image = Image.createImage((String)string);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        return image;
    }

    private static void a(long l2) {
        try {
            Thread.sleep(l2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    private void f(String string) {
        this.a(this.ax[37], 0);
        this.am = 5;
        this.ar = string;
    }

    private void i() {
        try {
            Object object = g.a.a(this.c, this.B);
            this.t = object[0];
            this.u = object[1];
            object = object[4];
            if (object != null && !((String)object).equals("") && ((String)object).toLowerCase().equals("true")) {
                this.z = true;
                return;
            }
            this.z = false;
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    private TextBox a(String string, String string2, int n2) {
        if (this.bh == null) {
            this.bh = new Command(this.ax[28], 4, 0);
            this.bi = new Command(this.ax[32], 2, 1);
        }
        string = new TextBox(string2, string, n2, 0);
        string.addCommand(this.bh);
        string.addCommand(this.bi);
        string.setCommandListener((CommandListener)this);
        return string;
    }

    private void a(Displayable displayable) {
        Display display = Display.getDisplay((MIDlet)this.c);
        Displayable displayable2 = display.getCurrent();
        this.av = true;
        display.setCurrent(displayable);
        while (this.av) {
            g.b.a(60L);
        }
        display.setCurrent(displayable2);
    }

    public static void a(MIDlet object, Displayable displayable, String string, String string2, String string3, int n2, int n3, int n4, int n5) {
        aD = Display.getDisplay((MIDlet)object);
        aE = displayable;
        if (a == null) {
            a = new b((MIDlet)object, string, string2, string3, n2, n3, -6, -7);
        }
        object = a;
        E = true;
        new Thread((Runnable)object).start();
        if (((b)object).v == null || ((b)object).v.equals("")) {
            super.i();
        }
        aD.setCurrent((Displayable)a);
        while (E && j) {
            if (a != null) {
                if (g.b.a.am == 1 || g.b.a.am == 2 || g.b.a.am == 7 || g.b.a.am == 9 || g.b.a.am == 10 || aB) {
                    if (System.currentTimeMillis() - g.b.a.az > 90000L) {
                        g.b.a.am = 0;
                        aB = false;
                        a.a(g.b.a.ax[44], 1);
                    }
                } else {
                    g.b.a.az = System.currentTimeMillis();
                }
                if (g.b.a.an != null && g.b.a.am > 0) {
                    if (g.b.a.aA.equals(" /")) {
                        g.b.a.an[g.b.a.an.length - 1] = g.a.a(g.b.a.an[g.b.a.an.length - 1], g.b.a.aA, " \\");
                        g.b.a.aA = " \\";
                    } else {
                        g.b.a.an[g.b.a.an.length - 1] = g.a.a(g.b.a.an[g.b.a.an.length - 1], g.b.a.aA, " /");
                        g.b.a.aA = " /";
                    }
                    a.repaint();
                    a.serviceRepaints();
                }
            }
            g.b.a(100L);
        }
    }

    private static void a(RecordStore recordStore) {
        if (recordStore == null) {
            return;
        }
        try {
            recordStore.closeRecordStore();
            return;
        }
        catch (RecordStoreNotOpenException recordStoreNotOpenException) {
            recordStoreNotOpenException.printStackTrace();
            return;
        }
        catch (RecordStoreException recordStoreException) {
            recordStoreException.printStackTrace();
            return;
        }
    }

    private static byte[] a(int n2, byte[] byArray, String string) {
        RecordStore recordStore = null;
        byte[] byArray2 = null;
        recordStore = RecordStore.openRecordStore((String)string, (boolean)false);
        if (byArray == null) {
            byArray2 = new byte[recordStore.getRecordSize(1)];
            recordStore.getRecord(1, byArray2, 0);
        } else {
            recordStore.setRecord(1, byArray, 0, byArray.length);
        }
        g.b.a(recordStore);
        return byArray2;
    }

    private static String[] a(String object, Font font, int n2) {
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
            if ((n4 += font.charWidth((char)object[i2])) <= n2) continue;
            stringBuffer.insert(i2 + n6, '\n');
            ++n5;
            ++n6;
            n4 = font.charWidth((char)object[i2]);
        }
        stringBuffer.append('\n');
        return g.b.a(stringBuffer.toString(), '\n', ++n5);
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

    private void j() {
        DataInputStream dataInputStream = new DataInputStream(this.getClass().getResourceAsStream("/lakoo.cn"));
        try {
            this.g = dataInputStream.readUTF();
            int n2 = dataInputStream.readInt();
            block7: for (int i2 = 0; i2 < n2; ++i2) {
                switch (i2) {
                    case 0: {
                        int n3;
                        int n4 = dataInputStream.readInt();
                        this.ax = new String[n4];
                        for (n3 = 0; n3 < n4; ++n3) {
                            this.ax[n3] = dataInputStream.readUTF();
                        }
                        continue block7;
                    }
                    case 1: {
                        int n3;
                        int n4 = dataInputStream.readInt();
                        this.ay = new String[n4];
                        for (n3 = 0; n3 < n4; ++n3) {
                            this.ay[n3] = dataInputStream.readUTF();
                        }
                        continue block7;
                    }
                    case 2: {
                        int n3;
                        int n4 = dataInputStream.readInt();
                        this.bj = new String[n4];
                        for (n3 = 0; n3 < n4; ++n3) {
                            this.bj[n3] = dataInputStream.readUTF();
                        }
                        continue block7;
                    }
                }
            }
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return;
        }
    }

    static {
        aI = "POST";
        aP = 0;
        aQ = 0;
    }
}

