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
 */
package d;

import d.b;
import g.c;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
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

public final class a
extends Canvas
implements Runnable,
CommandListener {
    private String c = "";
    private String d = "portal/sns";
    private String e = "";
    private String f = "passport/phonebuddy.do";
    public static a a;
    private MIDlet g;
    private int h;
    private int i;
    private int j;
    private int k;
    private static boolean l;
    private String m;
    private String n;
    private String o = "10";
    private static boolean p;
    private boolean q = false;
    private static Display r;
    private static Displayable s;
    private static String t;
    private Vector u = new Vector();
    private final Font v = Font.getFont((int)0, (int)0, (int)8);
    private final Font w = Font.getFont((int)0, (int)4, (int)8);
    private final Font x = Font.getFont((int)0, (int)1, (int)8);
    private int y = -1;
    private int[] z;
    private Hashtable A = null;
    private Image B = null;
    private Image C = null;
    private int D = 0;
    private int E = 0;
    private int F = 0;
    private int G = 0;
    private String H = "";
    private String I = "";
    private String J = "";
    private String K;
    private static String L;
    private Hashtable M = null;
    private Vector N;
    private Vector O;
    private String P = "n";
    private int Q = -1;
    private byte R = 0;
    private long S = 0L;
    private long T = 100L;
    private int U = 0;
    private static Hashtable V;
    private byte W = 0;
    private Hashtable X = null;
    private Hashtable Y = null;
    private Hashtable Z = null;
    private Hashtable aa = null;
    private Hashtable ab = null;
    private Hashtable ac = null;
    private Hashtable ad = null;
    private Hashtable ae = null;
    private Hashtable af = null;
    private Hashtable ag = null;
    private Hashtable ah = null;
    private Hashtable ai = null;
    private Hashtable aj = null;
    private Hashtable ak = null;
    private Hashtable al = null;
    private Hashtable am = null;
    private String an = null;
    private static Hashtable ao;
    private String ap = null;
    private int aq = 0;
    private int ar = 0;
    private boolean as = false;
    private TextBox at;
    private Command au;
    private Command av;
    private Command aw;
    private Command ax;
    private String ay = "0";
    private String az = "";
    private int aA = 0;
    private String aB = "";
    private static String aC;
    private int aD;
    private int aE;
    public static byte b;
    private static boolean aF;
    private static boolean aG;
    private static boolean aH;
    private static Hashtable aI;
    private Hashtable aJ = null;
    private Vector aK = null;
    private Vector aL = null;
    private Vector aM = new Vector();
    private int aN = 0;
    private int aO = 20;
    private int aP = 0;
    private int aQ = 0;
    private String aR = "n";
    private int aS = 0;
    private int aT = 0;
    private int aU = 0;
    private int aV = 0;
    private int aW = 0;
    private String aX = "";
    private static int aY;
    private boolean aZ = true;
    private int ba = 0;
    private char[] bb = new char[]{'2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'Z', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y'};
    private static boolean[] bc;
    private static String bd;

    private a(MIDlet object, String object2, String string, String string2, int n2, int n3, int n4, int n5) {
        string3.setFullScreenMode(aH);
        ((a)((Object)string3)).g = object;
        ((a)((Object)string3)).m = object2;
        ((a)((Object)string3)).n = string;
        ((a)((Object)string3)).o = string2;
        ((a)((Object)string3)).j = n2;
        ((a)((Object)string3)).k = n3;
        if (((a)((Object)string3)).j == 0) {
            ((a)((Object)string3)).j = string3.getWidth();
        }
        if (((a)((Object)string3)).k == 0) {
            ((a)((Object)string3)).k = string3.getHeight();
        }
        ((a)((Object)string3)).h = n4;
        ((a)((Object)string3)).i = n5;
        object = string3;
        object2 = d.b.a(((a)((Object)string3)).g, object.m);
        object.H = object2[0];
        object.I = object2[1];
        if (object2[4] != null && object2[4].toLowerCase().equals("true")) {
            l = true;
        }
        t = d.b.e;
        d.b.a(object.g);
        object.az = d.b.d;
        object.ay = d.b.c;
        ((a)((Object)string3)).a();
        ((a)((Object)string3)).B = d.a.c("/sns_logo.png");
        ((a)((Object)string3)).C = d.a.c("/sns_pb.png");
        if (((a)((Object)string3)).D < ((a)((Object)string3)).x.getHeight()) {
            ((a)((Object)string3)).D = ((a)((Object)string3)).x.getHeight();
        }
        if (((a)((Object)string3)).D < ((a)((Object)string3)).w.getHeight()) {
            ((a)((Object)string3)).D = ((a)((Object)string3)).w.getHeight();
        }
        if (((a)((Object)string3)).D < ((a)((Object)string3)).v.getHeight()) {
            ((a)((Object)string3)).D = ((a)((Object)string3)).v.getHeight();
        }
        ((a)((Object)string3)).D += 2;
        ((a)((Object)string3)).G = ((a)((Object)string3)).D + 2;
        ((a)((Object)string3)).am = d.a.a("y", ((a)((Object)string3)).aK.elementAt(28).toString(), ((a)((Object)string3)).v.getHeight() / 2, "x", "", "1", "", ((a)((Object)string3)).w, ((a)((Object)string3)).z[6], ((a)((Object)string3)).y, ((a)((Object)string3)).y);
        ((a)((Object)string3)).Z = d.a.a("y", "[" + ((a)((Object)string3)).aK.elementAt(0) + "]", 0, "q", "plog", "1", "", ((a)((Object)string3)).w, ((a)((Object)string3)).z[6], ((a)((Object)string3)).y, ((a)((Object)string3)).y);
        ((a)((Object)string3)).aa = d.a.a("y", "\uff08" + ((a)((Object)string3)).aK.elementAt(1) + "\uff09", 0, "o", "n", "1", "", ((a)((Object)string3)).w, ((a)((Object)string3)).z[6], ((a)((Object)string3)).y, ((a)((Object)string3)).y);
        ((a)((Object)string3)).ag = d.a.a("y", "5" + ((a)((Object)string3)).aK.elementAt(4), 5, "p", "5", "1", "", ((a)((Object)string3)).w, ((a)((Object)string3)).z[6], ((a)((Object)string3)).y, ((a)((Object)string3)).y);
        ((a)((Object)string3)).af = d.a.a("y", "4" + ((a)((Object)string3)).aK.elementAt(4), 5 + (((a)((Object)string3)).w.stringWidth("5" + ((a)((Object)string3)).aK.elementAt(4)) + 5), "p", "4", "1", "", ((a)((Object)string3)).w, ((a)((Object)string3)).z[6], ((a)((Object)string3)).y, ((a)((Object)string3)).y);
        ((a)((Object)string3)).ae = d.a.a("y", "3" + ((a)((Object)string3)).aK.elementAt(4), 5 + (((a)((Object)string3)).w.stringWidth("5" + ((a)((Object)string3)).aK.elementAt(4)) + 5 << 1), "p", "3", "1", "", ((a)((Object)string3)).w, ((a)((Object)string3)).z[6], ((a)((Object)string3)).y, ((a)((Object)string3)).y);
        ((a)((Object)string3)).ad = d.a.a("y", "2" + ((a)((Object)string3)).aK.elementAt(4), 5 + (((a)((Object)string3)).w.stringWidth("5" + ((a)((Object)string3)).aK.elementAt(4)) + 5) * 3, "p", "2", "1", "", ((a)((Object)string3)).w, ((a)((Object)string3)).z[6], ((a)((Object)string3)).y, ((a)((Object)string3)).y);
        ((a)((Object)string3)).ac = d.a.a("y", "1" + ((a)((Object)string3)).aK.elementAt(4), 5 + (((a)((Object)string3)).w.stringWidth("5" + ((a)((Object)string3)).aK.elementAt(4)) + 5 << 2), "p", "4", "1", "", ((a)((Object)string3)).w, ((a)((Object)string3)).z[6], ((a)((Object)string3)).y, ((a)((Object)string3)).y);
        ((a)((Object)string3)).al = d.a.a("y", "[" + ((a)((Object)string3)).aK.elementAt(3) + "]", ((a)((Object)string3)).v.getHeight() / 2, "n", "", "1", "", ((a)((Object)string3)).w, ((a)((Object)string3)).z[6], ((a)((Object)string3)).y, ((a)((Object)string3)).y);
        ((a)((Object)string3)).aj = d.a.a("y", "[" + ((a)((Object)string3)).aK.elementAt(5) + "]", 0, "s", "0", "1", "", ((a)((Object)string3)).w, ((a)((Object)string3)).z[6], ((a)((Object)string3)).y, ((a)((Object)string3)).y);
        ((a)((Object)string3)).ak = d.a.a("y", "[" + ((a)((Object)string3)).aK.elementAt(6) + "]", ((a)((Object)string3)).j - 3 - ((a)((Object)string3)).w.stringWidth("[" + ((a)((Object)string3)).aK.elementAt(5) + "]"), "s", "0", "1", "", ((a)((Object)string3)).w, ((a)((Object)string3)).z[6], ((a)((Object)string3)).y, ((a)((Object)string3)).y);
        ((a)((Object)string3)).ah = d.a.a("y", "[>]", Integer.parseInt(((a)((Object)string3)).ak.get("T").toString()) - ((a)((Object)string3)).w.stringWidth("[>]") - 2, "s", "5", "1", "", ((a)((Object)string3)).w, ((a)((Object)string3)).z[6], ((a)((Object)string3)).y, ((a)((Object)string3)).y);
        ((a)((Object)string3)).ai = d.a.a("y", "[<]", 0, "s", "0", "1", "", ((a)((Object)string3)).w, ((a)((Object)string3)).z[6], ((a)((Object)string3)).y, ((a)((Object)string3)).y);
        ((a)((Object)string3)).aA = ((a)((Object)string3)).v.stringWidth(((a)((Object)string3)).d("idName") + ":");
        if (((a)((Object)string3)).v.stringWidth(((a)((Object)string3)).d("idName") + ":") < ((a)((Object)string3)).v.stringWidth(((a)((Object)string3)).aK.elementAt(9).toString() + ":")) {
            ((a)((Object)string3)).aA = ((a)((Object)string3)).v.stringWidth(((a)((Object)string3)).aK.elementAt(9).toString() + ":");
        }
        ((a)((Object)string3)).aA += 5;
        ((a)((Object)string3)).au = new Command(((a)((Object)string3)).aK.elementAt(7).toString(), 4, 0);
        ((a)((Object)string3)).aw = new Command(((a)((Object)string3)).aK.elementAt(7).toString(), 4, 0);
        ((a)((Object)string3)).av = new Command(((a)((Object)string3)).aK.elementAt(8).toString(), 2, 1);
        ((a)((Object)string3)).ap = "n";
        ((a)((Object)string3)).b();
        ((a)((Object)string3)).c();
        if (aI == null) {
            object2 = ((a)((Object)string3)).d("cpurl");
            object = "&";
            String string3 = ((a)((Object)string3)).d("cpname");
            aI = new Hashtable<String, Object>();
            aI.put("e", string3);
            aI.put("f", object);
            aI.put("j", object2);
        }
    }

    private void a() {
        String[] stringArray;
        this.aJ = new Hashtable();
        this.aK = new Vector();
        this.aL = new Vector();
        try {
            Object var1_1 = null;
            String[] stringArray2 = d.b.c(d.b.a("/snsInfo.ini"), "\r\n");
            int n2 = 0;
            block6: for (int i2 = 0; i2 < stringArray2.length; ++i2) {
                String string = stringArray2[i2];
                if (string == null || string.equals("")) continue;
                if (string.indexOf("[Settings]") >= 0) {
                    n2 = 1;
                    continue;
                }
                if (string.indexOf("[Language]") >= 0) {
                    n2 = 2;
                    continue;
                }
                switch (n2) {
                    case 1: {
                        int n3 = string.indexOf("=");
                        if (n3 < 0) continue block6;
                        String string2 = string.substring(0, n3);
                        string = string.substring(n3 + 1, string.length());
                        if (string2.equals("column")) {
                            this.aL.addElement(string);
                            continue block6;
                        }
                        this.aJ.put(string2, string);
                        continue block6;
                    }
                    case 2: {
                        this.aK.addElement(string);
                    }
                }
            }
        }
        catch (Exception exception) {}
        if (!this.d("key1").equals("")) {
            this.c = d.b.a("lakoo" + this.aJ.get("snsVer"), this.d("key1"));
        }
        if (!this.d("key2").equals("")) {
            this.e = d.b.a("lakoo" + this.aJ.get("snsVer"), this.d("key2"));
        }
        if (this.d("showMsg").equals("n")) {
            aC = "n";
        }
        this.z = new int[10];
        if (!this.d("colors").equals("") && (stringArray = d.b.c(this.d("colors"), ",")).length == 10) {
            for (int i3 = 0; i3 < stringArray.length; ++i3) {
                this.z[i3] = Integer.parseInt(stringArray[i3]);
            }
        }
    }

    private void b() {
        this.P = "y";
        if (this.ap == "y") {
            this.aq = this.j - 4;
            this.ar = this.k - 4;
            return;
        }
        this.aq = this.j / 4 * 3;
        this.ar = this.k / 4 * 3;
    }

    private static Hashtable a(String string, String string2, String string3, String string4, String string5, String string6, Vector vector, Image image, int n2, String string7) {
        Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
        hashtable.put("P", string);
        hashtable.put("X", string2);
        hashtable.put("e", string5);
        hashtable.put("f", string6);
        hashtable.put("g", string3);
        hashtable.put("h", string4);
        hashtable.put("S", vector);
        if (image != null) {
            hashtable.put("i", image);
        }
        hashtable.put("k", new Integer(0));
        hashtable.put("c", new Integer(0));
        hashtable.put("j", string7);
        return hashtable;
    }

    private static Hashtable a(String string, String string2, int n2, String string3, String string4, String string5, String string6, Font font, int n3, int n4, int n5) {
        Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
        hashtable.put("P", "J");
        hashtable.put("R", string);
        hashtable.put("S", string2);
        hashtable.put("T", new Integer(n2));
        hashtable.put("U", string3);
        hashtable.put("V", string4);
        hashtable.put("W", string5);
        hashtable.put("X", string6);
        hashtable.put("Y", font);
        hashtable.put("Z", new Integer(n3));
        hashtable.put("a", new Integer(n4));
        hashtable.put("b", new Integer(n5));
        return hashtable;
    }

    private static Hashtable a(String string, Image image, int n2, String string2, String string3, String string4, String string5, int n3) {
        Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
        hashtable.put("P", "K");
        hashtable.put("R", string);
        hashtable.put("S", image);
        hashtable.put("T", new Integer(n2));
        hashtable.put("U", string2);
        hashtable.put("V", string3);
        hashtable.put("W", string4);
        hashtable.put("X", string5);
        hashtable.put("b", new Integer(n3));
        return hashtable;
    }

    private Hashtable a(String string, String string2, int n2, String string3, String string4, String string5, String string6, Font font, int n3, int n4, int n5, int n6, int n7, int n8) {
        Hashtable hashtable = d.a.a(string, string2, n2, string3, string4, string5, string6, font, n3, n4, n5);
        hashtable.put("P", "L");
        hashtable.put("l", new Integer(n6));
        hashtable.put("k", new Integer(n7));
        hashtable.put("d", new Integer(n8));
        return hashtable;
    }

    private static Hashtable a(String string, int n2, int n3, int n4, int n5) {
        Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
        hashtable.put("P", "N");
        hashtable.put("W", string);
        hashtable.put("T", new Integer(0));
        hashtable.put("l", new Integer(n3));
        hashtable.put("k", new Integer(1));
        hashtable.put("a", new Integer(n4));
        hashtable.put("b", new Integer(n5));
        return hashtable;
    }

    private static Hashtable a(Vector vector, int n2, int n3, int n4, int n5, String string, String string2, String string3) {
        Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
        hashtable.put("S", vector);
        hashtable.put("l", new Integer(n2));
        hashtable.put("k", new Integer(n3));
        hashtable.put("a", new Integer(n4));
        hashtable.put("b", new Integer(n5));
        hashtable.put("R", string);
        hashtable.put("U", string2);
        hashtable.put("V", string3);
        return hashtable;
    }

    private static Hashtable a(String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8) {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("F", string);
        hashtable.put("G", string2);
        hashtable.put("A", string3);
        hashtable.put("B", string4);
        hashtable.put("C", string5);
        hashtable.put("D", string6);
        hashtable.put("E", string7);
        hashtable.put("j", string8);
        return hashtable;
    }

    private void a(Hashtable hashtable) {
        Vector<Hashtable> vector = new Vector<Hashtable>();
        if (this.J != null && !this.J.equals("")) {
            Vector<Hashtable> vector2 = new Vector<Hashtable>();
            vector2.addElement(d.a.a("n", this.d("idName") + "\uff1a" + this.H, this.aA - this.v.stringWidth(this.d("idName") + ":"), "", "", "1", "", this.v, this.z[1], this.y, this.y));
            vector.addElement(d.a.a(vector2, this.j, this.D + 3, this.z[2], this.y, "n", "", ""));
            vector2 = new Vector();
            this.ab = d.a.a("n", this.aK.elementAt(9).toString() + "\uff1a" + L, this.aA - this.v.stringWidth(this.aK.elementAt(9).toString() + ":"), "", "", "1", "", this.v, this.z[1], this.y, this.y);
            vector2.addElement(this.ab);
            this.aa.put("T", new Integer(this.aA + this.v.stringWidth(this.I) + 12));
            vector2.addElement(this.aa);
            vector.addElement(d.a.a(vector2, this.j, this.D + 3, this.z[2], this.y, "n", "", ""));
        } else {
            Vector<Hashtable> vector3 = new Vector<Hashtable>();
            int n2 = this.v.stringWidth("ABCDEABCDEAB");
            if (n2 + this.aA > this.j) {
                n2 = this.j - this.aA - 3;
            }
            this.X = this.a("y", this.H, this.aA + 6, "n", "", "1", "userName", this.v, this.z[1], this.z[0], this.z[1], n2, this.D, 12);
            vector3.addElement(d.a.a("n", this.d("idName") + "\uff1a", this.aA - this.v.stringWidth(this.d("idName") + ":"), "", "", "1", "", this.v, this.z[1], this.y, this.y));
            vector3.addElement(this.X);
            vector.addElement(d.a.a(vector3, this.j, this.D + 3, this.z[2], this.y, "n", "", ""));
            vector3 = new Vector<Hashtable>();
            vector3.addElement(d.a.a("n", this.aK.elementAt(9).toString() + "\uff1a", this.aA - this.v.stringWidth(this.aK.elementAt(9).toString() + ":"), "", "", "1", "", this.v, this.z[1], this.y, this.y));
            this.Y = this.a("y", this.I, this.aA + 6, "n", "", "W", "password", this.v, this.z[1], this.z[0], this.z[1], n2, this.D, 20);
            vector3.addElement(this.Y);
            vector.addElement(d.a.a(vector3, this.j, this.D + 3, this.z[2], this.y, "n", "", ""));
            if (this.H == null || this.H.equals("")) {
                vector3 = new Vector<Hashtable>();
                vector3.addElement(d.a.a("y", this.aK.elementAt(10).toString() + this.d("idName"), 5, "w", "", "1", "", this.v, this.z[6], this.y, this.y));
                vector.addElement(d.a.a(vector3, this.j, this.D, this.z[2], this.y, "n", "", ""));
            }
        }
        this.a(vector);
        hashtable.put("S", vector);
    }

    private void a(Hashtable hashtable, String string, String string2) {
        int n2 = 0;
        int n3 = 0;
        if (string.indexOf(".") > 0) {
            n2 = Integer.parseInt(string.substring(0, string.indexOf(".")));
            if ((string = string.substring(string.indexOf(".") + 1, string.length()) + "0").length() > 2) {
                string = string.substring(0, 2);
            }
            n3 = Integer.parseInt(string) * 15 / 100;
        } else {
            n2 = Integer.parseInt(string);
        }
        string = Image.createImage((int)(n2 * 13 + n3 + (n2 << 1)), (int)14);
        Graphics graphics = string.getGraphics();
        graphics.setColor(this.z[3]);
        graphics.fillRect(0, 0, string.getWidth(), string.getHeight());
        for (int i2 = 0; i2 < n2 + 1; ++i2) {
            graphics.setColor(0xFFFF66);
            graphics.fillTriangle(7 + i2 * 13 + (i2 << 1), 0, 0 + i2 * 13 + (i2 << 1), 13, 13 + i2 * 13 + (i2 << 1), 13);
            graphics.setColor(0x999900);
            graphics.drawLine(7 + i2 * 13 + (i2 << 1), 0, 0 + i2 * 13 + (i2 << 1), 13);
            graphics.drawLine(7 + i2 * 13 + (i2 << 1), 0, 13 + i2 * 13 + (i2 << 1), 13);
            graphics.drawLine(0 + i2 * 13 + (i2 << 1), 13, 13 + i2 * 13 + (i2 << 1), 13);
        }
        Vector vector = (Vector)hashtable.get("S");
        while (vector.size() > 3) {
            vector.removeElementAt(3);
        }
        Vector<Hashtable> vector2 = new Vector<Hashtable>();
        vector2.addElement(d.a.a("n", this.aK.elementAt(11).toString() + "\uff1a", 5, "", "", "1", "", this.v, this.z[1], this.y, this.y));
        vector2.addElement(d.a.a("n", (Image)string, 5 + this.v.stringWidth(this.aK.elementAt(11).toString() + "\uff1a"), "", "", "1", "", this.y));
        string2 = "\uff08" + string2 + this.aK.elementAt(34) + this.aK.elementAt(11).toString() + "\uff09";
        if (5 + this.v.stringWidth(this.aK.elementAt(11).toString() + "\uff1a") + string.getWidth() + this.v.stringWidth(string2) > this.j) {
            vector.addElement(d.a.a(vector2, this.j, this.D, this.z[3], this.y, "n", "", ""));
            this.a(vector);
            vector2 = new Vector<Hashtable>();
            vector2.addElement(d.a.a("n", string2, 5, "", "", "1", "", this.v, this.z[1], this.y, this.y));
        } else {
            vector2.addElement(d.a.a("n", string2, 8 + this.v.stringWidth(this.aK.elementAt(11).toString() + "\uff1a") + string.getWidth(), "", "", "1", "", this.v, this.z[1], this.y, this.y));
        }
        vector.addElement(d.a.a(vector2, this.j, this.D, this.z[3], this.y, "n", "", ""));
        this.a(vector);
        vector2 = new Vector<Hashtable>();
        vector2.addElement(this.ag);
        vector2.addElement(this.af);
        vector2.addElement(this.ae);
        vector2.addElement(this.ad);
        vector2.addElement(this.ac);
        vector.addElement(d.a.a(vector2, this.j, this.D, this.z[3], this.y, "n", "", ""));
        hashtable.put("S", vector);
    }

    private static void a(Hashtable hashtable, int n2, int n3) {
        hashtable.put("a", new Integer(n2));
        ((Hashtable)((Vector)hashtable.get("S")).elementAt(0)).put("Z", new Integer(n3));
    }

    private void a(Hashtable hashtable, String stringArray, String string, String string2, Image image) {
        Vector vector = (Vector)hashtable.get("S");
        d.a.a((Hashtable)vector.elementAt(1), this.z[6], this.z[0]);
        while (vector.size() > 3) {
            vector.removeElementAt(3);
        }
        Vector<Hashtable> vector2 = new Vector<Hashtable>();
        if (image != null) {
            vector2.addElement(d.a.a("n", image, 5, "", "", "1", "", this.y));
            vector.addElement(d.a.a(vector2, this.j, image.getHeight(), this.z[3], this.y, "n", "", ""));
        } else {
            vector2.addElement(d.a.a("y", this.aK.elementAt(12).toString(), 5, "q", "cre", "1", "", this.w, this.z[6], this.y, this.y));
            vector.addElement(d.a.a(vector2, this.j, this.D, this.z[3], this.y, "n", "", ""));
        }
        stringArray = d.a.a("y", (String)stringArray, 5, "&", string2, "1", "", this.w, this.z[6], this.y, this.y);
        if (this.Q == this.b("cre")) {
            this.M = stringArray;
            if (image != null) {
                this.U -= image.getHeight() / this.D * this.D;
            }
            this.U -= this.D;
        }
        vector2 = new Vector<String[]>();
        vector2.addElement((Hashtable)stringArray);
        vector.addElement(d.a.a(vector2, this.j, this.D, this.z[3], this.y, "n", "", ""));
        stringArray = d.a.a(string, this.v, this.j - 8);
        for (int i2 = 0; i2 < stringArray.length; ++i2) {
            vector2 = new Vector<Hashtable>();
            vector2.addElement(d.a.a("n", stringArray[i2], 5, "", "", "1", "", this.v, this.z[1], this.y, this.y));
            vector.addElement(d.a.a(vector2, this.j, this.D, this.z[3], this.y, "n", "", ""));
        }
        vector2 = new Vector<Hashtable>();
        vector2.addElement(d.a.a("y", this.aK.elementAt(13).toString(), 5, "&", string2, "1", "", this.w, this.z[6], this.y, this.y));
        vector.addElement(d.a.a(vector2, this.j, this.D, this.z[3], this.y, "n", "", ""));
        hashtable.put("S", vector);
    }

    private void a(Vector vector) {
        Vector<Hashtable> vector2 = new Vector<Hashtable>();
        vector2.addElement(d.a.a("1", 0, this.j, this.z[2], this.y));
        vector.addElement(d.a.a(vector2, this.j, 1, this.y, this.y, "n", "", ""));
    }

    private void c() {
        Vector<Hashtable> vector;
        Object object;
        int n2;
        Object object2 = d.a.a("", "top", "y", "y", "y", "n", "n", "");
        this.u.addElement(object2);
        for (n2 = 0; n2 < this.aL.size(); ++n2) {
            object = d.b.c(this.aL.elementAt(n2).toString(), ",");
            vector = d.b.a(object[0], "${G}", this.n);
            object2 = "";
            String string = "n";
            String string2 = "n";
            String string3 = "n";
            String string4 = "n";
            String string5 = "n";
            String string6 = "";
            if (object[2].equals("y")) {
                string = "y";
            }
            if (object[3].equals("y")) {
                string2 = "y";
            }
            if (object[4].equals("y")) {
                string3 = "y";
            }
            if (object[5].equals("y")) {
                string4 = "y";
            }
            if (((String)object[6]).equals("y")) {
                string5 = "y";
            }
            if (((String)object[1]).equals("login")) {
                object2 = "login";
            } else if (((String)object[1]).equals("loadscore")) {
                object2 = "loadscore";
            } else if (((String)object[1]).equals("gc")) {
                object2 = "gc";
            } else if (((String)object[1]).equals("gameuser")) {
                object2 = "gameuser";
            } else if (((String)object[1]).equals("willplay")) {
                object2 = "willplay";
            } else if (((String)object[1]).equals("playedgame")) {
                object2 = "playedgame";
            } else if (((String)object[1]).equals("hotgame")) {
                object2 = "hotgame";
            } else if (((String)object[1]).equals("plog")) {
                object2 = "plog";
            } else if (((String)object[1]).equals("cre")) {
                object2 = "cre";
            } else if (((String)object[1]).equals("login")) {
                object2 = "login";
            }
            object2 = d.a.a(vector, (String)object2, string, string2, string3, string4, string5, string6);
            this.u.addElement(object2);
        }
        for (n2 = 0; n2 < this.u.size(); ++n2) {
            object2 = (Hashtable)this.u.elementAt(n2);
            if (((Hashtable)object2).get("F").equals("")) continue;
            object = new Vector();
            this.a((Vector)object);
            vector = new Vector<Hashtable>();
            vector.addElement(d.a.a("n", ((Hashtable)object2).get("F").toString(), 5, "", "", "1", "", this.x, this.z[1], this.y, this.y));
            ((Vector)object).addElement(d.a.a(vector, this.j, this.D + 4, this.z[4], this.y, ((Hashtable)this.u.elementAt(n2)).get("C") == "y" ? "n" : "y", "m", ""));
            this.a((Vector)object);
            ((Hashtable)object2).put("S", object);
        }
        if (this.b("login") != -1) {
            this.a((Hashtable)this.u.elementAt(this.b("login")));
        }
        Hashtable hashtable = (Hashtable)this.u.elementAt(this.b("top"));
        object2 = this;
        object = new Vector();
        vector = new Vector<Hashtable>();
        vector.addElement(d.a.a("1", 0, ((a)object2).j, ((a)object2).y, ((a)object2).y));
        ((Vector)object).addElement(d.a.a(vector, ((a)object2).j, 1, ((a)object2).y, ((a)object2).y, "n", "", ""));
        vector = new Vector<Hashtable>();
        vector.addElement(d.a.a("n", ((a)object2).B, 15, "", "", "1", "", ((a)object2).y));
        vector.addElement(d.a.a("n", ((a)object2).C, ((a)object2).j - 5 - ((a)object2).C.getWidth(), "", "", "1", "", ((a)object2).y));
        ((Vector)object).addElement(d.a.a(vector, ((a)object2).j, ((a)object2).B.getHeight() > ((a)object2).C.getHeight() ? ((a)object2).B.getHeight() : ((a)object2).C.getHeight(), ((a)object2).y, ((a)object2).y, "n", "", ""));
        hashtable.put("S", object);
        object2 = this;
        hashtable = d.a.a("", "b", "y", "y", "y", "n", "n", "");
        object = new Vector();
        super.a((Vector)object);
        vector = new Vector<Hashtable>();
        vector.addElement(d.a.a("n", "v1.3.3", 5, "", "", "1", "", ((a)object2).v, ((a)object2).z[1], ((a)object2).y, ((a)object2).y));
        ((Vector)object).addElement(d.a.a(vector, ((a)object2).j, ((a)object2).D, ((a)object2).z[2], ((a)object2).y, "n", "", ""));
        hashtable.put("S", object);
        ((a)object2).u.addElement(hashtable);
        if (this.b("cre") != -1) {
            this.a((Hashtable)this.u.elementAt(this.u.size() - 2), this.d("creName"), this.d("creBody"), this.d("creUrl"), null);
        }
        this.aL = null;
    }

    public final void run() {
        while (p) {
            try {
                switch (this.W) {
                    case 1: {
                        this.R = (byte)-1;
                        if (this.Q < 0) {
                            d.a.a(33L);
                            break;
                        }
                        String[] stringArray = (Hashtable)this.u.elementAt(this.Q);
                        Object object = stringArray.get("A").toString();
                        stringArray.get("D").toString();
                        stringArray.get("E").toString();
                        int n2 = 0;
                        if (object == "n") {
                            if (stringArray.get("D") == "y" && this.d()) break;
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer = this.a(stringBuffer, stringArray.get("G").toString(), this.m);
                            n2 = this.a(this.a(this.Q), stringBuffer.toString());
                        }
                        if (object != "y" && n2 < 0) break;
                        for (n2 = 0; n2 < this.u.size(); ++n2) {
                            object = (Hashtable)this.u.elementAt(n2);
                            if (((Hashtable)object).get("B") != "y" || ((Hashtable)object).get("C") != "n") continue;
                            ((Hashtable)object).put("B", "n");
                            d.a.a((Hashtable)((Vector)((Hashtable)object).get("S")).elementAt(1), this.z[4], this.z[1]);
                        }
                        stringArray.put("B", "y");
                        d.a.a((Hashtable)((Vector)stringArray.get("S")).elementAt(1), this.z[6], this.z[0]);
                        this.R = (byte)4;
                        break;
                    }
                    case 2: 
                    case 8: {
                        String string;
                        Object object;
                        if (this.at != null) {
                            this.a((Displayable)this.at);
                        }
                        if ((string = this.at.getString()) != null && string.length() > 0) {
                            string = d.a.d(string, "UTF-8");
                        }
                        this.at = null;
                        if (this.ax == this.av) break;
                        if (this.ax == this.au) {
                            if (this.M.get("P") == "L") {
                                this.M.put("S", string);
                            }
                            this.H = this.X.get("S").toString();
                            this.I = this.Y.get("S").toString();
                        } else if (this.ax == this.aw) {
                            if (this.W == 8) {
                                object = new StringBuffer();
                                object = this.a((StringBuffer)object, "gc", this.m);
                                ((StringBuffer)object).append("&cnt=" + string);
                                ((StringBuffer)object).append("&t=r");
                                ((StringBuffer)object).append("&oid=" + this.M.get("V"));
                                this.a(this.a(this.Q), ((StringBuffer)object).toString());
                                this.M = this.al;
                            } else if (this.M == this.al) {
                                object = new StringBuffer();
                                object = this.a((StringBuffer)object, "gc", this.m);
                                ((StringBuffer)object).append("&cnt=" + string);
                                ((StringBuffer)object).append("&t=s");
                                this.a(this.a(this.Q), ((StringBuffer)object).toString());
                            }
                        }
                        this.R = (byte)4;
                        break;
                    }
                    case 3: {
                        if (this.d()) break;
                        Object object = new StringBuffer();
                        object = this.a((StringBuffer)object, "doscore", this.m);
                        ((StringBuffer)object).append("&score=" + this.M.get("V").toString());
                        this.a(this.a(this.Q), ((StringBuffer)object).toString());
                        this.R = (byte)4;
                        break;
                    }
                    case 4: {
                        Object object = new StringBuffer();
                        object = this.a((StringBuffer)object, this.M.get("V").toString(), this.m);
                        this.a(this.a(this.Q), ((StringBuffer)object).toString());
                        this.R = (byte)4;
                        break;
                    }
                    case 5: {
                        Object object = new StringBuffer();
                        object = this.a((StringBuffer)object, ((Hashtable)this.u.elementAt(this.Q)).get("G").toString(), this.m);
                        ((StringBuffer)object).append("&t=v");
                        ((StringBuffer)object).append("&pn=" + this.M.get("V").toString());
                        this.a(this.a(this.Q), ((StringBuffer)object).toString());
                        this.R = (byte)4;
                        break;
                    }
                    case 6: {
                        d.a.a(this.g, this.aB);
                        break;
                    }
                    case 7: {
                        String[] stringArray = ((Hashtable)this.u.elementAt(this.Q)).get("G").toString();
                        Object object = new StringBuffer();
                        if (stringArray == "gc") {
                            object = this.a((StringBuffer)object, ((Hashtable)this.u.elementAt(this.Q)).get("G").toString(), this.m);
                            ((StringBuffer)object).append("&t=d");
                            ((StringBuffer)object).append("&oid=" + this.M.get("V"));
                        } else if (stringArray == "willplay" || stringArray == "playedgame" || stringArray == "hotgame") {
                            object = new StringBuffer();
                            object = this.a((StringBuffer)object, "qgi", this.M.get("V").toString());
                            ((StringBuffer)object).append("&uid=" + this.K);
                        }
                        this.a(this.a(this.Q), ((StringBuffer)object).toString());
                        break;
                    }
                    case 9: {
                        if (this.f()) break;
                        d.b.a(this.g, (Displayable)this, this.m);
                        String[] stringArray = d.b.a(this.g, this.m);
                        if (stringArray[2] == null || stringArray[2].equals("")) break;
                        this.X.put("S", stringArray[0]);
                        this.Y.put("S", stringArray[1]);
                        this.H = this.X.get("S").toString();
                        this.I = this.Y.get("S").toString();
                        Object object = new StringBuffer();
                        object = this.a((StringBuffer)object, "hotgame", this.m);
                        this.a(this.a(this.Q), ((StringBuffer)object).toString());
                        this.R = (byte)4;
                        break;
                    }
                    case 10: {
                        this.H = this.X.get("S").toString();
                        this.I = this.Y.get("S").toString();
                        if (this.d()) break;
                        if (this.K != null && !this.K.equals("")) {
                            this.g();
                            break;
                        }
                        Object object = new StringBuffer();
                        object = this.a((StringBuffer)object, null, this.m);
                        this.a(this.a(this.Q), ((StringBuffer)object).toString());
                        if (this.K == null || this.K.equals("")) break;
                        this.g();
                        break;
                    }
                    case 11: {
                        String[] stringArray = new Vector();
                        for (int i2 = 0; i2 < this.aM.size(); ++i2) {
                            if (!((Hashtable)this.aM.elementAt(i2)).get("select").toString().equals("y")) continue;
                            boolean bl = false;
                            String string = ((Hashtable)this.aM.elementAt(i2)).get("tel").toString();
                            String string2 = this.a(Long.parseLong(d.b.a(string, "+", "")), 5);
                            if (!d.b.b(string, d.b.a(d.b.a(this.A.get("sms_context").toString(), "$(gamename)", this.n), "$(code)", string2))) continue;
                            Hashtable<String, String> hashtable = new Hashtable<String, String>();
                            hashtable.put("tel", string);
                            hashtable.put("code", string2);
                            stringArray.addElement(hashtable);
                        }
                        this.aM = null;
                        if (stringArray.size() > 0) {
                            StringBuffer stringBuffer = new StringBuffer();
                            for (int i3 = 0; i3 < stringArray.size(); ++i3) {
                                stringBuffer.append("{\"code\":\"" + ((Hashtable)stringArray.elementAt(i3)).get("code").toString() + "\"}");
                                if (i3 >= stringArray.size() - 1) continue;
                                stringBuffer.append(",");
                            }
                            StringBuffer stringBuffer2 = new StringBuffer();
                            stringBuffer2.append("uid=" + this.K);
                            stringBuffer2.append("&cp=" + this.ay);
                            stringBuffer2.append("&gameCode=" + this.m);
                            stringBuffer2.append("&keytext=cin");
                            stringBuffer2.append("&key=[" + stringBuffer.toString() + "]");
                            stringBuffer2.append("&locale=" + this.d("locale"));
                            this.a(this.a(this.Q), stringBuffer2.toString());
                            break;
                        }
                        this.an = this.aK.elementAt(33).toString();
                    }
                }
                this.W = 0;
                this.repaint();
                this.serviceRepaints();
                d.a.a(33L);
            }
            catch (Exception exception) {}
        }
        r.setCurrent(s);
    }

    private static void a(MIDlet mIDlet, String string) {
        try {
            if (mIDlet.platformRequest(string)) {
                mIDlet.notifyDestroyed();
                p = false;
            }
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    private String a(int n2) {
        String string = null;
        string = ((Hashtable)this.u.elementAt(n2)).get("j").toString();
        if (string == null || string.equals("")) {
            string = this.c + this.d;
        }
        return string;
    }

    private boolean d() {
        boolean bl = false;
        if ("".equals(this.H) || "".equals(this.I)) {
            if (this.b("login") == -1) {
                this.an = this.aK.elementAt(27).toString();
            } else {
                this.M = this.X;
                this.R = (byte)4;
                this.U = 0;
                this.an = this.aK.elementAt(14).toString();
            }
            bl = true;
        }
        return bl;
    }

    private StringBuffer a(StringBuffer stringBuffer, String string, String string2) {
        String string3 = "";
        stringBuffer.append("locale=" + this.d("locale"));
        stringBuffer.append("&gameCode=" + string2);
        stringBuffer.append("&cp=" + this.ay);
        stringBuffer.append("&ua=" + this.az);
        stringBuffer.append("&rop=" + this.o);
        if (!"".equals(this.H) && !"".equals(this.I)) {
            if (this.J != null && !this.J.equals("")) {
                stringBuffer.append("&key=" + this.J);
            } else {
                stringBuffer.append("&u=" + this.H);
                stringBuffer.append("&p=" + this.I);
                string3 = "userinfo";
            }
        }
        if (string != null && !"".equals(string)) {
            string2 = string;
            if (string == "gameuser") {
                string2 = "qwp:" + string;
            }
            string3 = "".equals(string3) ? string2 : string3 + ":" + string2;
        }
        for (int i2 = 0; i2 < this.u.size(); ++i2) {
            if (((Hashtable)this.u.elementAt(i2)).get("A") != "n" || string == ((Hashtable)this.u.elementAt(i2)).get("G") || ((Hashtable)this.u.elementAt(i2)).get("E") != "y" || ("".equals(this.H) || "".equals(this.I)) && ((Hashtable)this.u.elementAt(i2)).get("D") == "y") continue;
            String string4 = ((Hashtable)this.u.elementAt(i2)).get("G").toString();
            if (string4 == "gameuser") {
                string4 = "qwp:" + string4;
            }
            string3 = "".equals(string3) ? string3 + string4 : string3 + ":" + string4;
        }
        stringBuffer.append("&keytext=" + string3);
        return stringBuffer;
    }

    public final void commandAction(Command command, Displayable displayable) {
        if (displayable != this.at) {
            return;
        }
        if (this.at == null) {
            return;
        }
        if (command == this.av || command == this.au || command == this.aw) {
            this.ax = command;
            this.as = false;
            return;
        }
    }

    protected final void pointerPressed(int n2, int n3) {
        this.aD = n3;
        this.aE = 0;
        if (this.W != 0) {
            return;
        }
        this.P = "n";
        if (this.an != null) {
            this.an = null;
            this.aD = -1;
            this.P = "y";
            return;
        }
        if (ao != null) {
            String string = ao.get("P").toString();
            if (string == "3" && n2 >= (this.j - this.aq) / 2 && n2 <= this.j - (this.j - this.aq) / 2 && n3 >= (this.k - this.ar) / 2 && n3 <= this.k - (this.k - this.ar) / 2) {
                if (n3 >= this.k - (this.k - this.ar) / 2 - this.G && n2 <= this.j / 2) {
                    this.a(ao.get("h").toString());
                } else if (n3 >= this.k - (this.k - this.ar) / 2 - this.G && n2 > this.j / 2) {
                    this.a(ao.get("f").toString());
                }
                if (n3 <= (this.k - this.ar) / 2 + this.G) {
                    this.ap = this.ap == "y" ? "n" : "y";
                    this.b();
                }
            }
            if (string == "4" && n2 >= (this.j - this.aq) / 2 && n2 <= this.j - (this.j - this.aq) / 2 && n3 >= (this.k - this.ar) / 2 && n3 <= this.k - (this.k - this.ar) / 2) {
                if (n3 >= this.k - (this.k - this.ar) / 2 - this.G && n2 <= this.j / 2) {
                    this.a(ao.get("h").toString());
                } else if (n3 >= this.k - (this.k - this.ar) / 2 - this.G && n2 > this.j / 2) {
                    this.a(ao.get("f").toString());
                }
                if (n3 <= (this.k - this.ar) / 2 + this.G) {
                    this.q = !this.q;
                    this.i();
                }
            }
            return;
        }
        if (this.P == "y") {
            return;
        }
        if (n3 >= this.k - this.G && n2 <= this.j / 3) {
            this.a("!");
            return;
        }
        if (n3 >= this.k - this.G && n2 >= this.j - this.j / 3) {
            this.a((String)aI.get("f"));
        }
    }

    protected final void pointerReleased(int n2, int n3) {
        if (this.P == "n" && ao == null) {
            int n4;
            int n5;
            int n6 = n3;
            n3 = n2;
            a a2 = this;
            if (a2.O != null) {
                for (int i2 = 0; i2 < a2.O.size(); ++i2) {
                    Hashtable hashtable = (Hashtable)a2.O.elementAt(i2);
                    int n7 = Integer.parseInt(hashtable.get("T").toString());
                    int n8 = Integer.parseInt(hashtable.get("c").toString());
                    int n9 = Integer.parseInt(hashtable.get("l").toString());
                    int n10 = Integer.parseInt(hashtable.get("k").toString());
                    if (n3 < n7 || n3 > n7 + n9 || n6 < n8 || n6 > n8 + n10) continue;
                    n5 = i2;
                    break;
                }
            } else {
                n5 = n4 = -1;
            }
            if (n5 >= 0) {
                this.M = (Hashtable)this.N.elementAt(n4);
                this.P = "y";
                this.R = (byte)3;
            }
            return;
        }
    }

    public final void pointerDragged(int n2, int n3) {
        if (this.W != 0) {
            return;
        }
        this.aE = this.aD - n3;
        if (ao != null && ao.get("P") != null) {
            if (this.aE < -2 || this.aE > 2) {
                this.P = "y";
                n2 = Integer.parseInt(ao.get("k").toString());
                n3 = Integer.parseInt(ao.get("c").toString());
                if (this.aE <= 0 || n3 + n2 > this.ar - this.G * 3) {
                    n3 -= this.aE;
                }
                if (n3 > 0) {
                    n3 = 0;
                }
                ao.put("c", new Integer(n3));
            }
            return;
        }
        if (this.aE < -2 || this.aE > 2) {
            this.P = "y";
            this.U -= this.aE << 1;
            this.aD = n2;
            this.aD = n3;
            if (this.U > 0) {
                this.U = 0;
            }
            if (this.E + this.U < this.k - this.G) {
                this.U = this.k - this.G - this.E;
            }
            this.R = (byte)4;
        }
    }

    protected final void keyPressed(int n2) {
        int n3 = 0;
        try {
            n3 = this.getGameAction(n2);
        }
        catch (Exception exception) {}
        if (n2 == this.h) {
            n3 = 9;
        } else if (n2 == this.i) {
            n3 = 10;
        } else if (n2 == 35) {
            n3 = 35;
        } else if (n2 == 48) {
            n3 = 48;
        }
        if (this.W != 0) {
            return;
        }
        if (this.an != null) {
            this.an = null;
            return;
        }
        if (ao != null) {
            String string = ao.get("P").toString();
            if (string == "3") {
                int n4 = Integer.parseInt(ao.get("k").toString());
                int n5 = Integer.parseInt(ao.get("c").toString());
                String string2 = ao.get("f").toString();
                String string3 = ao.get("h").toString();
                switch (n3) {
                    case 6: {
                        if (n5 + n4 <= this.ar - this.G * 3) break;
                        n5 -= this.D;
                        break;
                    }
                    case 1: {
                        if (n5 < 0) {
                            n5 += this.D;
                        }
                        if (n5 <= 0) break;
                        n5 = 0;
                        break;
                    }
                    case 8: 
                    case 9: {
                        this.a(string3);
                        break;
                    }
                    case 10: {
                        this.a(string2);
                        break;
                    }
                    case 35: {
                        this.ap = this.ap == "y" ? "n" : "y";
                        this.b();
                    }
                }
                if (ao != null) {
                    ao.put("c", new Integer(n5));
                }
                return;
            }
            if (string == "4") {
                string = ao.get("f").toString();
                String string4 = ao.get("h").toString();
                switch (n3) {
                    case 9: {
                        this.a(string4);
                        break;
                    }
                    case 10: {
                        this.a(string);
                        break;
                    }
                    case 35: {
                        this.q = !this.q;
                        this.i();
                    }
                }
                if (n2 == 48) {
                    this.j();
                    return;
                }
                switch (this.getGameAction(n2)) {
                    case 6: {
                        ++this.aN;
                        if (this.aN >= this.aO - 1) {
                            this.aN = this.aO - 1;
                        }
                        if (this.aN + this.aP * this.aO <= this.aM.size() - 1) break;
                        this.aN = this.aM.size() % this.aO - 1;
                        return;
                    }
                    case 1: {
                        --this.aN;
                        if (this.aN > 0) break;
                        this.aN = 0;
                        return;
                    }
                    case 2: {
                        if (this.aP-- <= 0) {
                            this.aP = 0;
                        }
                        this.aN = 0;
                        aY = 0;
                        return;
                    }
                    case 5: {
                        ++this.aP;
                        if (this.aP >= this.aQ - 1) {
                            this.aP = this.aQ - 1;
                        }
                        this.aN = 0;
                        aY = 0;
                        return;
                    }
                    case 8: {
                        n2 = this.aN + this.aP * this.aO;
                        Object var2_4 = null;
                        if (((Hashtable)this.aM.elementAt(n2)).get("select").toString().equals("y")) {
                            ((Hashtable)this.aM.elementAt(n2)).put("select", "n");
                            return;
                        }
                        ((Hashtable)this.aM.elementAt(n2)).put("select", "y");
                    }
                }
                return;
            }
        }
        this.Q = -1;
        switch (n3) {
            case 6: {
                this.R = 1;
                break;
            }
            case 1: {
                this.R = (byte)2;
                break;
            }
            case 8: {
                this.R = (byte)3;
                break;
            }
            case 2: {
                this.R = (byte)7;
                break;
            }
            case 5: {
                this.R = (byte)6;
                break;
            }
            case 9: {
                this.a("!");
                break;
            }
            case 10: {
                this.a((String)aI.get("f"));
            }
        }
        this.S = System.currentTimeMillis();
    }

    private void a(String string) {
        this.aD = -1;
        this.P = "y";
        if (string == "u") {
            this.at = this.a("", this.aK.elementAt(20).toString() + this.aK.elementAt(15).toString(), 100, this.aw, this.av);
            this.W = (byte)8;
            ao = null;
            return;
        }
        if (string == "&") {
            this.aB = ao != null ? ao.get("j").toString() : aI.get("j").toString();
            this.W = (byte)6;
            ao = null;
            return;
        }
        if (string == "v") {
            this.R = (byte)3;
            ao = null;
            return;
        }
        if (string == "^") {
            ao = null;
            return;
        }
        if (string == "!") {
            ao = null;
            p = false;
            return;
        }
        if (string == "@") {
            ao = null;
            return;
        }
        if (string == "y") {
            ao = null;
            ao = d.a.a("4", "", "", "z", "", "^", new Vector(), null, 0, "");
            this.i();
            this.R = (byte)-1;
            return;
        }
        if (string == "z") {
            this.W = (byte)11;
            this.R = (byte)-1;
            ao = null;
        }
    }

    private int e() {
        if (this.N == null || this.N.size() == 0) {
            return -1;
        }
        for (int i2 = 0; i2 < this.N.size(); ++i2) {
            if (this.N.elementAt(i2) != this.M) continue;
            return i2;
        }
        return -1;
    }

    private int b(String string) {
        for (int i2 = 0; i2 < this.u.size(); ++i2) {
            if (!((Hashtable)this.u.elementAt(i2)).get("G").equals(string)) continue;
            return i2;
        }
        return -1;
    }

    private void a(Hashtable object, int n2, int n3, int n4, int n5, int n6) {
        if (this.N == null) {
            return;
        }
        if (this.R == 5 && object != null) {
            this.N.addElement(object);
            int n7 = n3;
            n3 = n4;
            n2 = n6;
            int n8 = n7;
            int n9 = n5;
            Hashtable<String, Integer> hashtable = new Hashtable<String, Integer>();
            hashtable.put("T", new Integer(n9));
            hashtable.put("c", new Integer(n8));
            hashtable.put("l", new Integer(n2));
            hashtable.put("k", new Integer(n3));
            this.O.addElement(hashtable);
            return;
        }
        if (this.N.size() == 0) {
            if (object == null) {
                if (this.R == 1) {
                    if (this.E + this.U > this.k - this.G) {
                        this.U -= this.D;
                    }
                    this.R = (byte)4;
                }
                if (this.R == 2) {
                    if (this.U < 0) {
                        this.U += this.D;
                    }
                    this.R = (byte)4;
                }
            }
            return;
        }
        if (this.M == null && this.N != null && this.N.size() > 0) {
            this.M = (Hashtable)this.N.elementAt(0);
        }
        if (this.M == object) {
            this.Q = n2;
        }
        if (this.M == this.am && this.am.get("X") != null && this.am.get("X").toString().length() > 0) {
            this.an = this.am.get("X").toString();
            this.am.put("X", "");
        }
        if (System.currentTimeMillis() - this.S < this.T) {
            return;
        }
        if (this.R == 1) {
            int n10 = this.e();
            if (n10 < this.N.size() - 1) {
                this.M = (Hashtable)this.N.elementAt(n10 + 1);
                this.R = (byte)4;
            }
            if (n10 == this.N.size() - 1) {
                if (this.E + this.U > this.k - this.G) {
                    this.U -= this.D;
                }
                this.R = (byte)4;
            }
            return;
        }
        if (this.R == 2) {
            int n11 = this.e();
            if (n11 == 0) {
                if (this.U < 0) {
                    this.U += this.D;
                }
                this.R = (byte)4;
            }
            if (n11 > 0) {
                this.M = (Hashtable)this.N.elementAt(n11 - 1);
                this.R = (byte)4;
            }
            return;
        }
        if (this.M != object) {
            return;
        }
        if (this.R == 3 || this.R == 6 || this.R == 7) {
            object = this.M.get("U").toString();
            if (object != "m") {
                if (this.R == 6) {
                    this.R = 1;
                    return;
                }
                if (this.R == 7) {
                    this.R = (byte)2;
                    return;
                }
            }
            if (object == "m") {
                object = (Hashtable)this.u.elementAt(n2);
                if (((Hashtable)object).get("B") == "y" && (this.R == 3 || this.R == 7)) {
                    ((Hashtable)object).put("B", "n");
                    d.a.a((Hashtable)((Vector)((Hashtable)object).get("S")).elementAt(1), this.z[4], this.z[1]);
                    this.R = (byte)4;
                    return;
                }
                if (((Hashtable)object).get("B") == "n" && (this.R == 3 || this.R == 6)) {
                    this.W = 1;
                    this.R = (byte)-1;
                }
                return;
            }
            if (object == "n") {
                if (this.M.get("P") == "L") {
                    this.at = this.a(this.M.get("S").toString(), this.M.get("X").toString(), Integer.parseInt(this.M.get("d").toString()), this.au, this.av);
                } else if (this.M == this.al) {
                    this.at = this.a("", this.M.get("X").toString(), 100, this.aw, this.av);
                }
                this.R = (byte)-1;
                this.W = (byte)2;
                return;
            }
            if (object == "o") {
                if (this.M.get("V") == "n") {
                    this.ab.put("S", this.aK.elementAt(9).toString() + "\uff1a" + this.I);
                    this.aa.put("S", "\uff08" + this.aK.elementAt(2) + "\uff09");
                    this.aa.put("V", "y");
                } else {
                    this.ab.put("S", this.aK.elementAt(9).toString() + "\uff1a" + L);
                    this.aa.put("S", "\uff08" + this.aK.elementAt(1) + "\uff09");
                    this.aa.put("V", "n");
                }
                this.R = (byte)-1;
                return;
            }
            if (object == "p") {
                this.W = (byte)3;
                this.R = (byte)-1;
                return;
            }
            if (object == "q") {
                this.W = (byte)4;
                this.R = (byte)-1;
                return;
            }
            if (object == "r") {
                this.an = this.M.get("X").toString();
                this.R = (byte)-1;
                return;
            }
            if (object == "s") {
                this.W = (byte)5;
                this.R = (byte)-1;
                return;
            }
            if (object == "&") {
                this.aB = this.M.get("V").toString();
                this.W = (byte)6;
                this.R = (byte)-1;
                return;
            }
            if (object == "t") {
                this.W = (byte)7;
                this.R = (byte)-1;
                return;
            }
            if (object == "w") {
                this.W = (byte)9;
                this.R = (byte)-1;
                return;
            }
            if (object == "x") {
                this.W = (byte)10;
                this.R = (byte)-1;
            }
        }
    }

    protected final void paint(Graphics graphics) {
        try {
            Object object;
            Object object2;
            Graphics graphics2;
            this.F = 0;
            if (ao == null || this.ap == "n") {
                graphics.setClip(0, 0, this.j, this.k);
                graphics.setColor(this.z[0]);
                graphics.fillRect(0, 0, this.j, this.k);
                graphics2 = graphics;
                object2 = this;
                if (((a)object2).N == null || ((a)object2).R == 4) {
                    ((a)object2).N = new Vector();
                    ((a)object2).O = new Vector();
                    ((a)object2).R = (byte)5;
                }
                if (((a)object2).u != null) {
                    for (int i2 = 0; i2 < ((a)object2).u.size(); ++i2) {
                        object = (Hashtable)((a)object2).u.elementAt(i2);
                        super.a(graphics2, (Hashtable)object, i2);
                    }
                }
                if (((a)object2).R == 5) {
                    ((a)object2).R = (byte)-1;
                }
                this.E = this.F;
                if (this.W != 0) {
                    object2 = "/";
                    if (System.currentTimeMillis() % 300L > 150L) {
                        object2 = "\\";
                    }
                    this.a(graphics, this.aK.elementAt(16).toString() + " " + (String)object2);
                }
            }
            if (ao != null) {
                if (ao.get("P") == "3") {
                    this.a(graphics);
                } else if (ao.get("P") == "4") {
                    object = this.aK.elementAt(8).toString();
                    String string = this.aK.elementAt(7).toString();
                    graphics2 = graphics;
                    object2 = this;
                    graphics2.setFont(((a)object2).x);
                    graphics2.setColor(((a)object2).z[7]);
                    graphics2.fillRect(((a)object2).aU - 2, ((a)object2).aV - 3 - ((a)object2).D, ((a)object2).aT + 4, ((a)object2).aS + 4 + (((a)object2).D << 1) + 3);
                    graphics2.setColor(0xECECEC);
                    graphics2.fillRect(((a)object2).aU, ((a)object2).aV, ((a)object2).aT, ((a)object2).aS);
                    graphics2.drawRect(((a)object2).aU, ((a)object2).aV + ((a)object2).aS + 1, ((a)object2).aT / 2, ((a)object2).D);
                    graphics2.drawRect(((a)object2).aU + ((a)object2).aT / 2, ((a)object2).aV + ((a)object2).aS + 1, ((a)object2).aT / 2 - 1, ((a)object2).D);
                    graphics2.drawRect(((a)object2).aU, ((a)object2).aV - ((a)object2).D - 1, ((a)object2).aT - 1, ((a)object2).D - 1);
                    graphics2.drawString(string, ((a)object2).aU + (((a)object2).aT / 2 - ((a)object2).x.stringWidth(string)) / 2, ((a)object2).aV + ((a)object2).aS + 1 + (((a)object2).D - ((a)object2).x.getHeight()) / 2, 0);
                    graphics2.setFont(((a)object2).v);
                    graphics2.drawString((String)object, ((a)object2).aU + (((a)object2).aT / 2 - ((a)object2).x.stringWidth((String)object)) / 2 + ((a)object2).aT / 2, ((a)object2).aV + ((a)object2).aS + 1 + (((a)object2).D - ((a)object2).x.getHeight()) / 2, 0);
                    graphics2.setClip(((a)object2).aU, ((a)object2).aV - ((a)object2).D - 1, ((a)object2).aT - 1, ((a)object2).D - 1);
                    graphics2.setFont(((a)object2).x);
                    if (((a)object2).x.stringWidth(((a)object2).aX) < ((a)object2).aT) {
                        graphics2.drawString(((a)object2).aX, ((a)object2).aU + (((a)object2).aT - ((a)object2).x.stringWidth(((a)object2).aX)) / 2, ((a)object2).aV - ((a)object2).D - 1 + (((a)object2).D - ((a)object2).x.getHeight()) / 2, 0);
                    } else {
                        graphics2.drawString(((a)object2).aX, ((a)object2).aU + 4 + ((a)object2).ba, ((a)object2).aV - ((a)object2).D - 1 + (((a)object2).D - ((a)object2).x.getHeight()) / 2, 0);
                        if (((a)object2).aZ) {
                            ((a)object2).ba -= 2;
                            if (((a)object2).ba + ((a)object2).x.stringWidth(((a)object2).aX) < ((a)object2).aT - 4) {
                                ((a)object2).aZ = !((a)object2).aZ;
                            }
                        } else {
                            ((a)object2).ba += 2;
                            if (((a)object2).ba >= 0) {
                                ((a)object2).aZ = !((a)object2).aZ;
                            }
                        }
                    }
                    graphics2.setClip(((a)object2).aU, ((a)object2).aV, ((a)object2).aT, ((a)object2).aS);
                    this.b(graphics);
                }
            }
            if (this.an != null && !this.an.equals("")) {
                this.a(graphics, this.an);
            }
            graphics2 = graphics;
            object2 = this;
            graphics2.setColor(((a)object2).z[7]);
            graphics2.fillRect(0, ((a)object2).k - ((a)object2).G, ((a)object2).j, ((a)object2).G);
            graphics2.setFont(((a)object2).x);
            graphics2.setColor(((a)object2).z[0]);
            String string = ((a)object2).aK.elementAt(8).toString();
            switch (b) {
                case 1: {
                    string = ((a)object2).aK.elementAt(17).toString();
                    break;
                }
                case 2: {
                    string = ((a)object2).aK.elementAt(18).toString();
                }
            }
            graphics2.drawString(string, 5, ((a)object2).k - ((a)object2).G + (((a)object2).G - ((a)object2).v.getHeight()) / 2, 0);
            graphics2.drawString(aI.get("e").toString(), ((a)object2).j - 5 - ((a)object2).x.stringWidth(aI.get("e").toString()), ((a)object2).k - ((a)object2).G + (((a)object2).G - ((a)object2).v.getHeight()) / 2, 0);
            graphics2 = graphics;
            object2 = this;
            int n2 = 0;
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            if (ao == null) {
                n2 = ((a)object2).U;
                n3 = ((a)object2).E;
                n6 = ((a)object2).k - ((a)object2).D;
                n4 = 0;
                n5 = ((a)object2).k - ((a)object2).D - 7;
            } else if (ao.get("P") != "1") {
                n2 = Integer.parseInt(ao.get("c").toString());
                n3 = Integer.parseInt(ao.get("k").toString());
                n6 = ((a)object2).ar - (((a)object2).G << 1);
                n4 = (((a)object2).k - ((a)object2).ar) / 2 + ((a)object2).G;
                n5 = (((a)object2).k - ((a)object2).ar) / 2 + ((a)object2).ar - ((a)object2).G - 7;
            }
            if (System.currentTimeMillis() % 400L > 200L) {
                n4 += 6;
                n5 -= 6;
            }
            if (n2 < 0) {
                graphics2.setColor(((a)object2).z[8]);
                graphics2.fillTriangle(((a)object2).j / 2, n4, ((a)object2).j / 2 - 7, n4 + 7, ((a)object2).j / 2 + 7, n4 + 7);
                graphics2.setColor(((a)object2).z[6]);
                graphics2.drawLine(((a)object2).j / 2, n4, ((a)object2).j / 2 - 7, n4 + 7);
                graphics2.drawLine(((a)object2).j / 2, n4, ((a)object2).j / 2 + 7, n4 + 7);
                graphics2.drawLine(((a)object2).j / 2 + 7, n4 + 7, ((a)object2).j / 2 - 7, n4 + 7);
            }
            if (n2 + n3 > n6) {
                graphics2.setColor(((a)object2).z[8]);
                graphics2.fillTriangle(((a)object2).j / 2 - 7, n5, ((a)object2).j / 2 + 7, n5, ((a)object2).j / 2, n5 + 7);
                graphics2.setColor(((a)object2).z[6]);
                graphics2.drawLine(((a)object2).j / 2 - 7, n5, ((a)object2).j / 2 + 7, n5);
                graphics2.drawLine(((a)object2).j / 2 + 7, n5, ((a)object2).j / 2, n5 + 7);
                graphics2.drawLine(((a)object2).j / 2 - 7, n5, ((a)object2).j / 2, n5 + 7);
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private void a(Graphics graphics) {
        graphics.setClip((this.j - this.aq) / 2 - 2, (this.k - this.ar) / 2 - 2, this.aq + 4, this.ar + 4);
        graphics.setColor(this.z[7]);
        graphics.fillRect((this.j - this.aq) / 2 - 2, (this.k - this.ar) / 2 - 2, this.aq + 4, this.ar + 4);
        graphics.setColor(this.z[0]);
        graphics.drawRect((this.j - this.aq) / 2, (this.k - this.ar) / 2, this.aq - 1, this.G);
        graphics.drawRect((this.j - this.aq) / 2, (this.k - this.ar) / 2 + this.ar - this.G - 1, this.aq - 1, this.G);
        graphics.drawRect((this.j - this.aq) / 2, (this.k - this.ar) / 2 + this.ar - this.G - 1, this.aq / 2, this.G);
        graphics.setColor(this.z[2]);
        graphics.fillRect((this.j - this.aq) / 2, (this.k - this.ar) / 2 + this.G + 2, this.aq, this.ar - (this.G << 1) - 4);
        graphics.setFont(this.x);
        graphics.drawString(ao.get("g").toString(), (this.j - this.aq) / 2 + (this.aq / 2 - this.x.stringWidth(ao.get("g").toString())) / 2, (this.k - this.ar) / 2 + this.ar - this.G - 1 + (this.G - this.x.getHeight()) / 2, 0);
        graphics.drawString(ao.get("e").toString(), (this.j - this.aq) / 2 + (this.aq / 2 - this.x.stringWidth(ao.get("e").toString())) / 2 + this.aq / 2, (this.k - this.ar) / 2 + this.ar - this.G - 1 + (this.G - this.x.getHeight()) / 2, 0);
        int n2 = 0;
        n2 = this.x.stringWidth(ao.get("X").toString()) < this.aq - 4 ? (this.j - this.aq) / 2 + (this.aq - this.x.stringWidth(ao.get("X").toString())) / 2 : (this.j - this.aq) / 2 + 3;
        graphics.setClip((this.j - this.aq) / 2 + 2, (this.k - this.ar) / 2, this.aq - 4, this.G);
        graphics.drawString(ao.get("X").toString(), n2, (this.k - this.ar) / 2 + (this.G - this.x.getHeight()) / 2, 0);
        graphics.setClip((this.j - this.aq) / 2, (this.k - this.ar) / 2 + this.G + 2, this.aq, this.ar - (this.G << 1) - 4);
        n2 = Integer.parseInt(ao.get("c").toString());
        graphics.setFont(this.v);
        int n3 = 0;
        if (ao.get("i") != null) {
            graphics.drawImage((Image)ao.get("i"), 2 + (this.j - this.aq) / 2, n2 + (this.k - this.ar) / 2 + this.G + 2, 0);
            n3 = ((Image)ao.get("i")).getHeight() + 2;
        }
        Vector vector = (Vector)ao.get("S");
        for (int i2 = 0; i2 < vector.size(); ++i2) {
            String[] stringArray = d.a.a(vector.elementAt(i2).toString(), this.v, this.aq - 5);
            graphics.setColor(this.z[1]);
            for (int i3 = 0; i3 < stringArray.length; ++i3) {
                if (n2 + (this.k - this.ar) / 2 + this.G + 2 + n3 > (this.k - this.ar) / 2 && n2 + (this.k - this.ar) / 2 + this.G + 2 + n3 < (this.k - this.ar) / 2 + this.ar) {
                    graphics.drawString(stringArray[i3], 2 + (this.j - this.aq) / 2, n2 + (this.k - this.ar) / 2 + this.G + 2 + (this.D - this.v.getHeight()) / 2 + n3, 0);
                }
                n3 += this.D;
            }
            if (n2 + (this.k - this.ar) / 2 + this.G + 2 + n3 > (this.k - this.ar) / 2 && n2 + (this.k - this.ar) / 2 + this.G + 2 + n3 < (this.k - this.ar) / 2 + this.ar) {
                graphics.setColor(this.z[4]);
                graphics.fillRect(2 + (this.j - this.aq) / 2, n2 + (this.k - this.ar) / 2 + this.G + 2 + n3, this.aq - 1, 1);
            }
            n3 += 3;
        }
        ao.put("k", new Integer(n3));
    }

    private void a(Graphics graphics, String stringArray) {
        int n2;
        stringArray = d.a.a((String)stringArray, this.v, this.j / 4 * 3);
        int n3 = 0;
        int n4 = stringArray.length * this.D + 4;
        for (n2 = 0; n2 < stringArray.length; ++n2) {
            if (n3 >= this.v.stringWidth(stringArray[n2])) continue;
            n3 = this.v.stringWidth(stringArray[n2]);
        }
        this.a(graphics, this.z[1], (this.j - (n3 += 12)) / 2, (this.k - n4 - this.G) / 2, n3, n4);
        graphics.setColor(this.z[0]);
        graphics.setFont(this.v);
        for (n2 = 0; n2 < stringArray.length; ++n2) {
            graphics.drawString(stringArray[n2], 3 + (this.j - n3) / 2, 2 + (this.k - n4 - this.G) / 2 + (this.D - this.v.getHeight()) / 2 + this.D * n2, 0);
        }
    }

    private void a(Graphics graphics, Hashtable hashtable, int n2) {
        Vector vector;
        if (hashtable != null && (vector = (Vector)hashtable.get("S")) != null) {
            int n3 = 2;
            if (hashtable.get("B") == "y") {
                n3 = vector.size();
            }
            for (int i2 = 0; i2 < n3; ++i2) {
                Hashtable hashtable2 = (Hashtable)vector.elementAt(i2);
                int n4 = Integer.parseInt(hashtable2.get("k").toString());
                if (this.F + this.U < this.k && this.F + this.U + n4 > 0) {
                    int n5 = n2;
                    Hashtable hashtable3 = hashtable2;
                    Graphics graphics2 = graphics;
                    a a2 = this;
                    int n6 = Integer.parseInt(hashtable3.get("l").toString());
                    int n7 = Integer.parseInt(hashtable3.get("a").toString());
                    int n8 = Integer.parseInt(hashtable3.get("b").toString());
                    int n9 = Integer.parseInt(hashtable3.get("k").toString());
                    if (a2.M == hashtable3) {
                        n7 = a2.z[5];
                        if (((Hashtable)a2.u.elementAt(n5)).get("B") == "y") {
                            n7 = a2.z[7];
                        }
                    }
                    a2.a(graphics2, n7, 0, a2.U + a2.F, n6, n9);
                    a2.b(graphics2, n8, 0, a2.U + a2.F, n6, n9);
                    a2.a(hashtable3.get("R") == "y" ? hashtable3 : null, n5, a2.U + a2.F, n9, 0, a2.j);
                    Vector vector2 = (Vector)hashtable2.get("S");
                    if (vector2 != null) {
                        for (int i3 = 0; i3 < vector2.size(); ++i3) {
                            int n10;
                            int n11;
                            int n12;
                            Hashtable hashtable4 = (Hashtable)vector2.elementAt(i3);
                            String string = hashtable4.get("P").toString();
                            if (string == "K") {
                                n6 = n2;
                                n5 = n4;
                                hashtable3 = hashtable4;
                                graphics2 = graphics;
                                a2 = this;
                                Image image = (Image)hashtable3.get("S");
                                n8 = Integer.parseInt(hashtable3.get("T").toString());
                                n9 = Integer.parseInt(hashtable3.get("b").toString());
                                n12 = d.a.a(hashtable3.get("W").toString(), image.getHeight(), n5);
                                graphics2.drawImage(image, n8, a2.U + n12 + a2.F, 0);
                                a2.b(graphics2, n9, n8 - 1, a2.U + n12 + a2.F - 1, image.getWidth() + 2, image.getHeight() + 2);
                                a2.a(hashtable3.get("R") == "y" ? hashtable3 : null, n6, a2.U + a2.F, image.getHeight(), n8, image.getWidth());
                            }
                            if (string == "N") {
                                n6 = n2;
                                n5 = n4;
                                hashtable3 = hashtable4;
                                graphics2 = graphics;
                                a2 = this;
                                int n13 = Integer.parseInt(hashtable3.get("l").toString());
                                n8 = Integer.parseInt(hashtable3.get("k").toString());
                                n9 = Integer.parseInt(hashtable3.get("a").toString());
                                n12 = Integer.parseInt(hashtable3.get("b").toString());
                                n11 = Integer.parseInt(hashtable3.get("T").toString());
                                n5 = d.a.a(hashtable3.get("W").toString(), n8, n5);
                                a2.a(graphics2, n9, n11, a2.U + n5 + a2.F, n13, n8);
                                a2.b(graphics2, n12, n11, a2.U + n5 + a2.F, n13, n8);
                                a2.a((Hashtable)null, n6, a2.U + a2.F, n8, n11, n13);
                            }
                            if (string == "J") {
                                Hashtable hashtable5 = hashtable2;
                                n6 = n2;
                                n5 = n4;
                                hashtable3 = hashtable4;
                                graphics2 = graphics;
                                a2 = this;
                                Font font = (Font)hashtable3.get("Y");
                                n9 = Integer.parseInt(hashtable3.get("T").toString());
                                n12 = d.a.a(hashtable3.get("W").toString(), font.getHeight(), n5);
                                n11 = Integer.parseInt(hashtable3.get("Z").toString());
                                n5 = Integer.parseInt(hashtable3.get("a").toString());
                                n10 = Integer.parseInt(hashtable3.get("b").toString());
                                String string2 = hashtable3.get("S").toString();
                                if (a2.M == hashtable3) {
                                    n5 = a2.z[9];
                                }
                                a2.a(graphics2, n5, n9 - 1, a2.U + n12 - 1 + a2.F, font.stringWidth(string2) + 2, font.getHeight() + 2);
                                graphics2.setColor(n11);
                                graphics2.setFont(font);
                                int n14 = 0;
                                if (a2.M == hashtable5) {
                                    n5 = (int)System.currentTimeMillis() % 1800;
                                    if (n9 + font.stringWidth(string2) > a2.j && n5 > 900) {
                                        n14 = a2.j - font.stringWidth(string2) - 10;
                                    }
                                }
                                graphics2.drawString(string2, n14 + n9, a2.U + n12 + a2.F, 0);
                                a2.b(graphics2, n10, n9 - 1, a2.U + n12 - 1 + a2.F, font.stringWidth(string2) + 2, font.getHeight() + 2);
                                a2.a(hashtable3.get("R") == "y" ? hashtable3 : null, n6, a2.U + a2.F, a2.D, n9, font.stringWidth(string2));
                            }
                            if (string != "L") continue;
                            n6 = n2;
                            n5 = n4;
                            hashtable3 = hashtable4;
                            graphics2 = graphics;
                            a2 = this;
                            Font font = (Font)hashtable3.get("Y");
                            int n15 = Integer.parseInt(hashtable3.get("T").toString());
                            n9 = d.a.a(hashtable3.get("W").toString(), font.getHeight(), n5);
                            n12 = Integer.parseInt(hashtable3.get("Z").toString());
                            n11 = Integer.parseInt(hashtable3.get("a").toString());
                            n5 = Integer.parseInt(hashtable3.get("b").toString());
                            n10 = Integer.parseInt(hashtable3.get("l").toString());
                            int n16 = Integer.parseInt(hashtable3.get("k").toString());
                            String string3 = hashtable3.get("S").toString();
                            if (a2.M == hashtable3) {
                                n11 = a2.z[9];
                            }
                            a2.a(graphics2, n11, n15, a2.U + n9 + a2.F, n10, n16);
                            graphics2.setFont(font);
                            graphics2.setColor(n12);
                            graphics2.setClip(n15, a2.U + n9 + a2.F, n10, n16);
                            graphics2.drawString(string3, n15 + 5, a2.U + n9 + a2.F + (n16 - font.getHeight()) / 2, 0);
                            graphics2.setClip(0, 0, a2.j, a2.k);
                            a2.b(graphics2, n5, n15, a2.U + n9 + a2.F, n10, n16);
                            a2.a(hashtable3.get("R") == "y" ? hashtable3 : null, n6, a2.U + a2.F, n16, n15, n10);
                        }
                    }
                }
                this.F += n4;
            }
        }
    }

    private void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        if (n2 != this.y) {
            graphics.setColor(n2);
            graphics.fillRect(n3, n4, n5, n6);
        }
    }

    private void b(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        if (n2 != this.y) {
            graphics.setColor(n2);
            graphics.drawRect(n3, n4, n5, n6);
        }
    }

    private static int a(String string, int n2, int n3) {
        if (string == "0") {
            return 0;
        }
        if (string == "2") {
            return n3 - n2;
        }
        return (n3 - n2) / 2;
    }

    private static Image c(String string) {
        Image image = null;
        try {
            image = Image.createImage((String)string);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        return image;
    }

    public static void a(MIDlet object, Displayable displayable, String string, String string2, String string3, int n2, int n3, int n4, int n5) {
        r = Display.getDisplay((MIDlet)object);
        s = displayable;
        if (a == null) {
            a = new a((MIDlet)object, string, string2, string3, n2, n3, -6, -7);
        }
        if (a.d("isjumpcp").toLowerCase().equals("y")) {
            d.a.a(object, a.d("cpurl"));
            return;
        }
        object = a;
        p = true;
        new Thread((Runnable)object).start();
        r.setCurrent((Displayable)a);
        while (p && aG) {
            try {
                a.repaint();
                a.serviceRepaints();
            }
            catch (Exception exception) {}
            d.a.a(33L);
        }
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
        return d.a.a(stringBuffer.toString(), '\n', ++n5);
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

    private boolean f() {
        if (aC == "y") {
            aC = "n";
            Vector<String> vector = new Vector<String>();
            String[] stringArray = d.b.c(this.aK.elementAt(25).toString(), "\\");
            for (int i2 = 0; i2 < stringArray.length; ++i2) {
                vector.addElement(stringArray[i2]);
            }
            ao = d.a.a("3", this.aK.elementAt(24).toString(), this.aK.elementAt(23).toString(), "v", this.aK.elementAt(8).toString(), "^", vector, null, 0, "");
            return true;
        }
        return false;
    }

    private int a(String string, String string2) {
        if (this.f()) {
            return -1;
        }
        int n2 = this.b(string, string2);
        if (n2 == -902) {
            n2 = this.b(string, string2);
        }
        if (n2 == -106) {
            l = !l;
            n2 = this.b(string, string2);
        }
        return n2;
    }

    private int b(String object, String object2) {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("Content-Type", "application/x-www-form-urlencoded");
        object = new h.a((String)object, t, (String)object2, hashtable);
        try {
            object2 = h.a.a((h.a)object, l, "");
            object = ((Hashtable)object2).get("contType").toString();
            if (object != null && ((String)object).indexOf("audio/midi") < 0) {
                return -902;
            }
        }
        catch (SecurityException securityException) {
            return -901;
        }
        catch (Exception exception) {
            return -106;
        }
        if (object2 == null) {
            return -4;
        }
        int n2 = Integer.parseInt(((Hashtable)object2).get("respCode").toString());
        if (n2 >= 200 && n2 <= 299 && ((Hashtable)object2).get("data") != null) {
            return this.a((byte[])((Hashtable)object2).get("data"));
        }
        return -5;
    }

    private int a(byte[] object) {
        int n2 = 0;
        if (object == null) {
            return -1;
        }
        try {
            object = new ByteArrayInputStream((byte[])object);
            DataInputStream dataInputStream = new DataInputStream((InputStream)object);
            object = dataInputStream;
            Object object2 = dataInputStream.readUTF();
            object2 = new g.b((String)object2);
            for (int i2 = 0; i2 < ((g.b)object2).a(); ++i2) {
                c c2 = (c)((g.b)object2).a(i2);
                String string = c2.b("q");
                String string2 = c2.b("d");
                int n3 = this.b(string);
                int n4 = Integer.parseInt(c2.b("r"));
                String string3 = ((DataInputStream)object).readUTF();
                if (n4 < 0) {
                    if (n3 == -1) {
                        if (string.equals("doscore")) {
                            this.an = string2;
                        }
                        if (!string.equals("userinfo") || this.M != this.am || !string.equals("cin")) continue;
                        this.an = string2;
                        n2 = -1;
                        continue;
                    }
                    if (n3 != this.Q) continue;
                    this.an = string2;
                    n2 = -1;
                    continue;
                }
                if (string3 == null || string3.equals("null")) {
                    if (n3 == this.Q) {
                        this.an = this.aK.elementAt(19).toString();
                        n2 = -1;
                    }
                    if (!string.equals("cin")) continue;
                    this.an = string2;
                    continue;
                }
                n2 = this.a(string3, n2, n3, string, (DataInputStream)object);
            }
            return n2;
        }
        catch (Exception exception) {
            return -1;
        }
    }

    /*
     * Unable to fully structure code
     */
    private int a(String var1_1, int var2_3, int var3_4, String var4_5, DataInputStream var5_6) {
        try {
            block40: {
                block41: {
                    var1_1 = new g.b((String)var1_1);
                    if (var4_5.equals("qwp")) {
                        this.A = new Hashtable<K, V>();
                        for (var6_7 = 0; var6_7 < var1_1.a(); ++var6_7) {
                            var7_17 = (c)var1_1.a(var6_7);
                            if (var7_17.b("title").equals("intro")) {
                                this.A.put("intro", var7_17.b("desc"));
                                continue;
                            }
                            if (var7_17.b("title").equals("sms_context")) {
                                this.A.put("sms_context", var7_17.b("desc"));
                                continue;
                            }
                            if (!var7_17.b("title").equals("prompt_context")) continue;
                            this.A.put("prompt_context", var7_17.b("desc"));
                        }
                    }
                    if (var4_5.equals("userinfo")) {
                        var6_8 = (c)var1_1.a(0);
                        this.J = var6_8.b("key");
                        this.K = var6_8.b("uid");
                        d.b.a(this.H, this.I, d.a.l);
                        if (this.b("login") >= 0) {
                            this.a((Hashtable)this.u.elementAt(this.b("login")));
                        }
                    }
                    if (var4_5.equals("loadscore") || var4_5.equals("doscore")) {
                        var6_9 = (c)var1_1.a(0);
                        this.a((Hashtable)this.u.elementAt(this.b("loadscore")), var6_9.b("score"), var6_9.b("count"));
                        if (var4_5.equals("doscore")) {
                            this.an = this.aK.elementAt(26).toString();
                        }
                        ((Hashtable)this.u.elementAt(this.b("loadscore"))).put("A", "y");
                    }
                    if (var4_5.equals("gameuser")) {
                        var6_10 = (Vector)((Hashtable)this.u.elementAt(var3_4)).get("S");
                        while (var6_10.size() > 3) {
                            var6_10.removeElementAt(3);
                        }
                        for (var7_18 = 0; var7_18 < var1_1.a(); ++var7_18) {
                            var8_26 = (c)var1_1.a(var7_18);
                            var9_29 = new Vector<Hashtable>();
                            var9_29.addElement(d.a.a("n", var8_26.b("name"), this.v.getHeight() / 2, "", "", "W", "", this.v, this.z[1], this.y, this.y));
                            var6_10.addElement(d.a.a(var9_29, this.j, this.D, this.z[3], this.y, "n", "", ""));
                            this.a(var6_10);
                        }
                        var7_17 = new Vector<E>();
                        this.am.put("X", this.A.get("intro"));
                        var7_17.addElement(this.am);
                        var7_17.addElement(d.a.a("y", "[?]", this.v.getHeight() / 2 + this.w.stringWidth(this.am.get("S").toString()) + 10, "r", "", "1", this.A.get("intro").toString(), this.w, this.z[6], this.y, this.y));
                        var6_10.addElement(d.a.a((Vector)var7_17, this.j, this.D, this.z[3], this.y, "n", "", ""));
                        this.a(var6_10);
                        ((Hashtable)this.u.elementAt(var3_4)).put("A", "y");
                    }
                    if (var4_5.equals("willplay") || var4_5.equals("playedgame") || var4_5.equals("hotgame")) {
                        var6_11 = (Vector)((Hashtable)this.u.elementAt(var3_4)).get("S");
                        while (var6_11.size() > 3) {
                            var6_11.removeElementAt(3);
                        }
                        for (var7_19 = 0; var7_19 < var1_1.a(); ++var7_19) {
                            var8_26 = (c)var1_1.a(var7_19);
                            var9_29 = new Vector<Hashtable>();
                            var9_29.addElement(d.a.a("y", var8_26.b("title"), this.v.getHeight() / 2, "t", var8_26.b("code"), "W", "", this.w, this.z[6], this.y, this.y));
                            var6_11.addElement(d.a.a(var9_29, this.j, this.D, this.z[3], this.y, "n", "", ""));
                            this.a(var6_11);
                        }
                        ((Hashtable)this.u.elementAt(var3_4)).put("A", "y");
                    }
                    if (var4_5.equals("plog")) {
                        var6_12 = (Vector)((Hashtable)this.u.elementAt(var3_4)).get("S");
                        while (var6_12.size() > 3) {
                            var6_12.removeElementAt(3);
                        }
                        for (var7_20 = 0; var7_20 < var1_1.a(); ++var7_20) {
                            var8_26 = (c)var1_1.a(var7_20);
                            var9_29 = new Vector<Hashtable>();
                            if (var8_26.a("desc") == null || var8_26.a("desc").equals("")) {
                                var9_29.addElement(d.a.a("n", var8_26.a("title").toString(), this.v.getHeight() / 2, "", "", "1", "", this.v, this.z[1], this.y, this.y));
                                if (var7_20 == 0) {
                                    this.Z.put("T", new Integer(this.v.stringWidth(var8_26.a("title").toString()) + this.v.getHeight() / 2 + 3));
                                    var9_29.addElement(this.Z);
                                }
                            } else {
                                var9_29.addElement(d.a.a("y", var8_26.a("title").toString(), this.v.getHeight() / 2, "r", "", "1", var8_26.a("desc").toString(), this.w, this.z[6], this.y, this.y));
                            }
                            var6_12.addElement(d.a.a((Vector)var9_29, this.j, this.D, this.z[3], this.y, "n", "", ""));
                            this.a(var6_12);
                        }
                        ((Hashtable)this.u.elementAt(var3_4)).put("A", "y");
                    }
                    if (!var4_5.equals("gc")) break block40;
                    if (this.W != 7) break block41;
                    var6_13 = new Vector<Object>();
                    for (var7_21 = 0; var7_21 < var1_1.a(); ++var7_21) {
                        var8_26 = (c)var1_1.a(var7_21);
                        var9_29 = "";
                        var10_32 = this.aK.elementAt(35).toString();
                        if (var8_26.b("uname") != null && !var8_26.b("uname").equals("")) {
                            var10_32 = var8_26.b("uname");
                        }
                        if (var7_21 != 0) {
                            var9_29 = "[#" + var8_26.b("floorNum") + "]";
                        }
                        var9_29 = var9_29 + var10_32 + "\uff1a" + var8_26.b("content");
                        var6_13.addElement(var9_29);
                    }
                    d.a.ao = d.a.a("3", this.aK.elementAt(21).toString() + this.aK.elementAt(15).toString(), this.aK.elementAt(20).toString(), "u", this.aK.elementAt(8).toString(), "^", var6_13, null, 0, "");
                    break block40;
                }
                var6_14 = (Vector)((Hashtable)this.u.elementAt(var3_4)).get("S");
                while (var6_14.size() > 3) {
                    var6_14.removeElementAt(3);
                }
                var7_22 = 0;
                var8_27 = 0;
                for (var9_30 = 0; var9_30 < var1_1.a(); ++var9_30) {
                    var10_33 = (c)var1_1.a(var9_30);
                    if (var9_30 == 0) {
                        var7_22 = Integer.parseInt(var10_33.a("total").toString());
                    }
                    var11_35 = new Vector<Hashtable>();
                    var11_35.addElement(d.a.a("y", var10_33.b("content"), this.v.getHeight() / 2, "t", var10_33.b("id"), "W", "", this.w, this.z[6], this.y, this.y));
                    var6_14.addElement(d.a.a(var11_35, this.j, this.D, this.z[3], this.y, "n", "", ""));
                    this.a(var6_14);
                }
                var9_31 = new Vector<Hashtable>();
                var9_31.addElement(this.al);
                var8_27 = var7_22 / 5;
                if (var7_22 % 5 > 0) {
                    ++var8_27;
                }
                this.ak.put("V", new Integer((var8_27 - 1) * 5));
                var10_34 = Integer.parseInt(this.ai.get("V").toString());
                var11_36 = Integer.parseInt(this.ah.get("V").toString());
                if (this.M == this.aj) ** GOTO lbl-1000
                if (this.M == this.ak) {
                    var11_36 = (var8_27 - 1) * 5;
                    var10_34 = var11_36 - 5;
                } else if (this.M == this.ah) {
                    var10_34 += 5;
                    var11_36 += 5;
                } else if (this.M == this.ai) {
                    var10_34 -= 5;
                    var11_36 -= 5;
                } else lbl-1000:
                // 2 sources

                {
                    var10_34 = 0;
                    var11_36 = 5;
                }
                if (var11_36 > (var8_27 - 1) * 5) {
                    var11_36 = (var8_27 - 1) * 5;
                    var10_34 = var11_36 - 5;
                }
                if (var10_34 < 0) {
                    var10_34 = 0;
                    var11_36 = 5;
                }
                var7_23 = var10_34 / 5 + 1 + "";
                var12_37 = Integer.parseInt(this.ah.get("T").toString());
                this.ak.put("V", new Integer((var8_27 - 1) * 5));
                this.ah.put("V", new Integer(var11_36));
                this.ai.put("T", new Integer(var12_37 - 6 - this.v.stringWidth(var7_23) - this.w.stringWidth(this.ai.get("S").toString())));
                this.ai.put("V", new Integer(var10_34));
                this.aj.put("T", new Integer(Integer.parseInt(this.ai.get("T").toString()) - 3 - this.w.stringWidth(this.aj.get("S").toString())));
                var9_31.addElement(this.aj);
                var9_31.addElement(this.ai);
                var9_31.addElement(d.a.a("n", var7_23, var12_37 - 3 - this.v.stringWidth(var7_23), "", "", "1", "", this.v, this.z[1], this.y, this.y));
                var9_31.addElement(this.ah);
                var9_31.addElement(this.ak);
                var6_14.addElement(d.a.a(var9_31, this.j, this.D, this.z[2], this.y, "n", "", ""));
                this.a(var6_14);
                ((Hashtable)this.u.elementAt(var3_4)).put("A", "y");
            }
            if (var4_5.equals("cre")) {
                var6_15 = (c)var1_1.a(0);
                var7_24 = d.a.a(var5_6, Integer.parseInt(var6_15.a("size").toString()));
                this.a((Hashtable)this.u.elementAt(var3_4), var6_15.a("title").toString(), var6_15.a("context").toString(), var6_15.a("url").toString(), var7_24);
                ((Hashtable)this.u.elementAt(var3_4)).put("A", "y");
            }
            if (var4_5.equals("qgi")) {
                var6_16 = (c)var1_1.a(0);
                var7_25 = new Vector<String>();
                var7_25.addElement(var6_16.b("context"));
                var8_28 = d.a.a(var5_6, Integer.parseInt(var6_16.a("size").toString()));
                d.a.ao = d.a.a("3", var6_16.b("title"), this.aK.elementAt(22).toString(), "&", this.aK.elementAt(8).toString(), "^", var7_25, var8_28, 0, var6_16.b("url"));
            }
            return var2_3;
        }
        catch (Exception var1_2) {
            if (var3_4 == this.Q) {
                var2_3 = -1;
            }
            var1_2.printStackTrace();
            return var2_3;
        }
    }

    private static Image a(DataInputStream dataInputStream, int n2) {
        Image image = null;
        if (n2 <= 0) {
            return null;
        }
        try {
            byte[] byArray = null;
            byArray = new byte[256];
            int n3 = 0;
            int n4 = 0;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while ((n4 = dataInputStream.read(byArray, n3, 256 - n3)) >= 0) {
                byteArrayOutputStream.write(byArray, n3, n4);
                if ((n3 += n4) < 256) continue;
                n3 = 0;
            }
            byArray = byteArrayOutputStream.toByteArray();
            if (byArray.length != n2) {
                return null;
            }
            image = Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return image;
    }

    private TextBox a(String string, String string2, int n2, Command command, Command command2) {
        string = new TextBox(string2, string, n2, 0);
        string.addCommand(command);
        string.addCommand(command2);
        string.setCommandListener((CommandListener)this);
        return string;
    }

    private void a(Displayable displayable) {
        Display display = Display.getDisplay((MIDlet)this.g);
        Displayable displayable2 = display.getCurrent();
        this.as = true;
        display.setCurrent(displayable);
        while (this.as) {
            d.a.a(60L);
        }
        display.setCurrent(displayable2);
    }

    private String d(String string) {
        a a2 = a2.aJ.get(string);
        if (a2 == null) {
            return "";
        }
        return a2.toString();
    }

    private void g() {
        try {
            Vector vector = d.a.h();
            Vector vector2 = new Vector();
            this.aM = new Vector();
            if (vector.size() > 0) {
                int n2 = 0;
                StringBuffer stringBuffer = new StringBuffer();
                for (int i2 = 0; i2 < vector.size(); ++i2) {
                    stringBuffer.append("{\"msisdn\":\"" + ((Hashtable)vector.elementAt(i2)).get("tel").toString() + "\",\"name\":\"" + ((Hashtable)vector.elementAt(i2)).get("name").toString() + "\"}");
                    if (++n2 == 500 || i2 == vector.size() - 1) {
                        vector2 = this.a(stringBuffer.toString(), vector2);
                        stringBuffer = new StringBuffer();
                        n2 = 0;
                        continue;
                    }
                    stringBuffer.append(",");
                }
                this.aM = d.a.a(vector, vector2);
                if (this.aM.size() > 0) {
                    Vector<String> vector3 = new Vector<String>();
                    vector3.addElement(d.b.a(d.b.a(this.A.get("prompt_context").toString(), "$(count1)", vector2.size() + ""), "$(count2)", this.aM.size() + ""));
                    ao = d.a.a("3", this.aK.elementAt(28).toString(), this.aK.elementAt(31).toString(), "y", this.aK.elementAt(8).toString(), "^", vector3, null, 0, "");
                    return;
                }
                this.an = d.b.a(this.aK.elementAt(30).toString(), "${str}", vector2.size() + "");
                return;
            }
            this.an = this.aK.elementAt(29).toString();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private static Vector a(Vector vector, Vector vector2) {
        block0: for (int i2 = 0; i2 < vector2.size(); ++i2) {
            String string = ((Hashtable)vector2.elementAt(i2)).get("tel").toString();
            for (int i3 = 0; i3 < vector.size(); ++i3) {
                if (!d.b.a(d.b.a(((Hashtable)vector.elementAt(i3)).get("tel").toString(), "+", ""), " ", "").equals(d.b.a(d.b.a(string, "+", ""), " ", ""))) continue;
                vector.removeElementAt(i3);
                continue block0;
            }
        }
        return vector;
    }

    private Vector a(String string, Vector vector) {
        try {
            Object object = new StringBuffer();
            ((StringBuffer)object).append("cmd=import");
            ((StringBuffer)object).append("&uid=" + ((a)object2).K);
            ((StringBuffer)object).append("&nickname=" + ((a)object2).H);
            ((StringBuffer)object).append("&gamecode=" + ((a)object2).m);
            ((StringBuffer)object).append("&rop=" + ((a)object2).o);
            ((StringBuffer)object).append("&phonebook=[" + string + "]");
            ((StringBuffer)object).append("&f=1");
            ((StringBuffer)object).append("&locale=" + ((a)object2).d("locale"));
            object = ((StringBuffer)object).toString();
            string = ((a)object2).e + ((a)object2).f;
            ((a)object2).an = null;
            byte[] byArray = d.a.c(string, (String)object);
            Object object2 = byArray;
            if (byArray == null) {
                object2 = d.a.c(string, (String)object);
            }
            if (object2 != null && ((Object)object2).length > 0) {
                object2 = new ByteArrayInputStream((byte[])object2);
                if (((String)(object2 = new DataInputStream((InputStream)object2).readUTF())).indexOf("[") != -1 && ((String)object2).indexOf("]") != -1) {
                    object2 = new g.b((String)object2);
                    for (int i2 = 0; i2 < ((g.b)object2).a(); ++i2) {
                        object = (c)((g.b)object2).a(i2);
                        Hashtable<String, String> hashtable = new Hashtable<String, String>();
                        hashtable.put("tel", ((c)object).b("msisdn"));
                        hashtable.put("name", ((c)object).b("nickname"));
                        vector.addElement(hashtable);
                    }
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return vector;
    }

    private static byte[] c(String object, String object2) {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("Content-Type", "application/x-www-form-urlencoded");
        object = new h.a((String)object, "POST", (String)object2, hashtable);
        try {
            object2 = h.a.a((h.a)object, l, "");
            object = ((Hashtable)object2).get("contType").toString();
            if (object != null && ((String)object).indexOf("audio/midi") < 0) {
                return null;
            }
        }
        catch (SecurityException securityException) {
            return null;
        }
        catch (Exception exception) {
            return null;
        }
        if (object2 == null) {
            return null;
        }
        int n2 = Integer.parseInt(((Hashtable)object2).get("respCode").toString());
        if (n2 >= 200 && n2 <= 299 && ((Hashtable)object2).get("data") != null) {
            return (byte[])((Hashtable)object2).get("data");
        }
        return null;
    }

    private static Vector h() {
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

    private void i() {
        a a2 = this;
        aY = 0;
        if (a2.q) {
            a2.aT = a2.j - 4;
            a2.aS = a2.k - (a2.D << 1) - 6;
            a2.aV = a2.D + 3;
            a2.aU = 2;
            a2.aO = a2.aS / a2.D;
        } else {
            a2.aT = a2.j > 240 ? a2.j - 80 : (a2.j == 240 ? a2.j - 60 : a2.j - 20);
            if (a2.k <= 208) {
                a2.aS = a2.k - (a2.D << 2);
                a2.aV = a2.D << 1;
            } else {
                a2.aS = a2.k - a2.D * 6;
                a2.aV = a2.D * 3;
            }
            a2.aW = a2.aS / a2.D;
            a2.aS = a2.aW * a2.D;
            a2.aU = (a2.j - a2.aT) / 2;
            a2.aO = a2.aW;
            a2.ar = a2.aS + (a2.D << 1);
            a2.aq = a2.aT;
        }
        this.aQ = this.aM.size() % this.aO > 0 ? this.aM.size() / this.aO + 1 : this.aM.size() / this.aO;
        this.aP = 0;
        this.aN = 0;
        aY = 0;
    }

    private void b(Graphics graphics) {
        this.aX = d.b.a(d.b.a(this.aK.elementAt(32).toString(), "${pageCount}", this.aQ + ""), "${page}", this.aP + 1 + "");
        graphics.setFont(this.v);
        if ((this.aN + 1) * this.D + aY > this.aS) {
            aY -= this.D;
        }
        if (this.aN * this.D + aY < 0) {
            aY += this.D;
        }
        for (int i2 = 0; i2 < this.aO; ++i2) {
            int n2 = i2 + this.aP * this.aO;
            if (n2 >= this.aM.size()) continue;
            String string = null;
            if (this.aN == i2) {
                graphics.setColor(this.z[8]);
                graphics.fillRect(this.aU, aY + this.aV + this.D * i2, this.aT - 1, this.D);
                graphics.setColor(this.z[9]);
                graphics.drawRect(this.aU, aY + this.aV + this.D * i2, this.aT - 1, this.D);
                string = ((Hashtable)this.aM.elementAt(n2)).get("tel").toString();
            } else {
                string = ((Hashtable)this.aM.elementAt(n2)).get("name").toString();
            }
            graphics.setColor(this.z[1]);
            graphics.drawRect(this.aU + 5, aY + this.aV + (this.D - 5) / 2 + this.D * i2 - 3, 10, 10);
            if (((Hashtable)this.aM.elementAt(n2)).get("select").toString().equals("y")) {
                graphics.setColor(this.z[6]);
                graphics.fillRect(this.aU + 8, aY + this.aV + (this.D - 5) / 2 + this.D * i2, 5, 5);
            }
            graphics.setColor(this.z[1]);
            graphics.drawString(string, this.aU + 20, aY + this.aV + (this.D - this.v.getHeight()) / 2 + this.D * i2, 0);
        }
    }

    private void j() {
        for (int i2 = 0; i2 < this.aM.size(); ++i2) {
            ((Hashtable)this.aM.elementAt(i2)).put("select", this.aR);
        }
        if (this.aR.equals("y")) {
            this.aR = "n";
            return;
        }
        this.aR = "y";
    }

    private String a(long l2, int n2) {
        StringBuffer stringBuffer = new StringBuffer();
        while (l2 != 0L) {
            stringBuffer.insert(0, this.bb[(int)(l2 & 0x1FL)]);
            l2 >>>= 5;
        }
        while (stringBuffer.length() < 5) {
            stringBuffer.insert(0, this.bb[0]);
        }
        return stringBuffer.toString();
    }

    private static String d(String string, String string2) {
        int n2 = 0;
        boolean bl = false;
        StringBuffer stringBuffer = new StringBuffer(string.length());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = new OutputStreamWriter((OutputStream)byteArrayOutputStream, string2);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            try {
                outputStreamWriter = new OutputStreamWriter((OutputStream)byteArrayOutputStream, bd);
            }
            catch (UnsupportedEncodingException unsupportedEncodingException2) {}
        }
        for (int i2 = 0; i2 < string.length(); ++i2) {
            int n3 = string.charAt(i2);
            if (n3 < 256 && bc[n3]) {
                if (n3 == 32) {
                    n3 = 43;
                    n2 = 1;
                }
                stringBuffer.append((char)n3);
                bl = true;
                continue;
            }
            try {
                if (bl) {
                    outputStreamWriter = new OutputStreamWriter((OutputStream)byteArrayOutputStream, string2);
                    bl = false;
                }
                if (outputStreamWriter != null) {
                    outputStreamWriter.write(n3);
                }
                if (n3 >= 55296 && n3 <= 56319 && i2 + 1 < string.length()) {
                    char c2 = string.charAt(i2 + 1);
                    n3 = c2;
                    if (c2 >= '\udc00' && n3 <= 57343) {
                        outputStreamWriter.write(n3);
                        ++i2;
                    }
                }
                outputStreamWriter.flush();
            }
            catch (IOException iOException) {
                byteArrayOutputStream.reset();
                continue;
            }
            byte[] byArray = byteArrayOutputStream.toByteArray();
            for (n2 = 0; n2 < byArray.length; ++n2) {
                stringBuffer.append('%');
                char c3 = d.a.b(byArray[n2] >> 4 & 0xF);
                if (d.a.a(c3)) {
                    c3 = (char)(c3 - 32);
                }
                stringBuffer.append(c3);
                c3 = d.a.b(byArray[n2] & 0xF);
                if (d.a.a(c3)) {
                    c3 = (char)(c3 - 32);
                }
                stringBuffer.append(c3);
            }
            byteArrayOutputStream.reset();
            n2 = 1;
        }
        if (n2 != 0) {
            return stringBuffer.toString();
        }
        return string;
    }

    private static boolean a(char c2) {
        return c2 >= 'a' && c2 <= 'z' || c2 >= 'A' && c2 <= 'Z';
    }

    private static char b(int n2) {
        if (n2 >= 16 || n2 < 0) {
            return '\u0000';
        }
        if (n2 < 10) {
            return (char)(n2 + 48);
        }
        return (char)(n2 + 87);
    }

    static {
        int n2;
        a = null;
        l = false;
        p = false;
        t = "POST";
        L = "******";
        V = null;
        ao = null;
        aC = "y";
        b = 0;
        aF = false;
        aG = true;
        aH = true;
        aI = null;
        aY = 0;
        bd = "";
        bc = new boolean[256];
        for (n2 = 97; n2 <= 122; ++n2) {
            d.a.bc[n2] = true;
        }
        for (n2 = 65; n2 <= 90; ++n2) {
            d.a.bc[n2] = true;
        }
        for (n2 = 48; n2 <= 57; ++n2) {
            d.a.bc[n2] = true;
        }
        d.a.bc[32] = true;
        d.a.bc[45] = true;
        d.a.bc[95] = true;
        d.a.bc[46] = true;
        d.a.bc[42] = true;
        bd = System.getProperty("microedition.encoding");
        if (bd == null || bd.trim().length() == 0) {
            bd = "UTF-8";
        }
    }
}

