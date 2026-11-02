/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.Connector
 *  javax.microedition.lcdui.ChoiceGroup
 *  javax.microedition.lcdui.Command
 *  javax.microedition.lcdui.CommandListener
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Form
 *  javax.microedition.lcdui.Item
 *  javax.microedition.lcdui.ItemStateListener
 *  javax.microedition.lcdui.StringItem
 *  javax.microedition.lcdui.TextField
 *  javax.microedition.midlet.MIDlet
 *  javax.microedition.rms.RecordStore
 *  javax.microedition.rms.RecordStoreException
 *  javax.microedition.rms.RecordStoreNotOpenException
 *  javax.wireless.messaging.MessageConnection
 *  javax.wireless.messaging.TextMessage
 */
package g;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

public final class a
extends Form
implements Runnable,
CommandListener,
ItemStateListener {
    private static String d = "";
    private static Hashtable e = new Hashtable();
    private Hashtable f = new Hashtable();
    private Hashtable g = new Hashtable();
    private static Hashtable h = new Hashtable();
    private static String i = "";
    private static String j = "";
    private static String k = "";
    private static String l = "";
    private static boolean m = false;
    private static int n = 0;
    private static String o = "";
    private static String p = "";
    private static long q = 0L;
    public static a a = null;
    private static boolean r = true;
    private static byte s = 0;
    private static byte t = 1;
    private static byte u = (byte)2;
    private static int[] v = null;
    private static Hashtable w = new Hashtable();
    private static String[] x = null;
    private static String[] y = null;
    private static String[] z = null;
    private boolean A = false;
    private boolean B = false;
    private int C = -1;
    private int D = -1;
    private int E = -1;
    private int F = 0;
    private boolean G = true;
    private StringItem H = null;
    private StringItem I = null;
    private Command J = null;
    private Command K = null;
    private Command L = null;
    private Command M = null;
    private Command N = null;
    private TextField O = null;
    private TextField P = null;
    private TextField Q = null;
    private TextField R = null;
    private ChoiceGroup S = null;
    private ChoiceGroup T = null;
    private ChoiceGroup U = null;
    private TextField V = null;
    private String W = "";
    private String X = "";
    private String Y = "";
    public static String b;
    private static String Z;
    public static String c;
    private int aa = 1;
    private String ab = "";
    private String ac = "";
    private String ad = "";
    private int ae = 0;
    private int af = 0;
    private String ag = "";
    private String ah = "";
    private String ai = "";
    private int aj;
    private int ak = 0;
    private int al = 0;
    private int am = 0;
    private int an = 0;
    private int[] ao = null;
    private byte[] ap;
    private String aq = "";
    private static MIDlet ar;
    private static String[] as;
    private static String at;
    private static int au;
    private static String av;
    private static String aw;
    private String ax = "1";
    private String ay = "0";
    private int az;
    private String aA = "";
    private String[] aB = null;
    private String[] aC = null;
    private String aD;
    private String aE;
    private static String aF;
    private String[] aG;
    private static String aH;
    private static boolean aI;
    private static String aJ;
    private static String aK;
    private static String aL;
    private static String aM;
    private static String aN;
    private static String aO;
    private static String aP;
    private static String aQ;
    private boolean aR = false;
    private static char[] aS;
    private static boolean aT;
    private static boolean aU;

    private static void b() {
        String[] stringArray = g.a.g("/payInfo.ini");
        w = new Hashtable();
        x = new String[25];
        y = new String[68];
        if (stringArray.equals("")) {
            s = u;
            return;
        }
        stringArray = g.a.c((String)stringArray, "\r\n");
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        block5: for (int i2 = 0; i2 < stringArray.length; ++i2) {
            String[] stringArray2 = stringArray[i2];
            if (stringArray2 == null) continue;
            if (stringArray2.indexOf("[Settings]") >= 0) {
                n2 = 2;
                continue;
            }
            if (stringArray2.indexOf("[Language1]") >= 0) {
                n2 = 3;
                continue;
            }
            if (stringArray2.indexOf("[Language2]") >= 0) {
                n2 = 5;
                continue;
            }
            switch (n2) {
                case 2: {
                    if (stringArray2.equals("")) continue block5;
                    stringArray2 = g.a.c((String)stringArray2, "=");
                    w.put(stringArray2[0], stringArray2[1]);
                    continue block5;
                }
                case 3: {
                    g.a.x[n3] = stringArray2;
                    ++n3;
                    continue block5;
                }
                case 5: {
                    g.a.y[n4] = stringArray2;
                    ++n4;
                }
            }
        }
    }

    private a(MIDlet mIDlet) {
        super("");
        if (w == null) {
            g.a.b();
        }
        ar = mIDlet;
        this.G();
        this.D();
    }

    private void c() {
        this.G = true;
        new Thread(this).start();
    }

    private void d() {
        this.E();
        this.u();
        this.append((Item)this.H);
        this.append((Item)this.I);
    }

    private void e() {
        this.C = 1;
        this.E();
        this.u();
        this.aa = 2;
        if (i != null && !"".equals(i)) {
            if (!aw.equals("noid") && !av.equals("")) {
                this.H.setText(this.W + "\n " + av + ":" + " " + i + "\n " + g.a.a(5, 1) + ":" + " " + j);
            } else {
                this.H.setText(this.W);
            }
        } else {
            this.H.setText(this.W);
        }
        this.append((Item)this.H);
        if (!aw.equals("noid")) {
            this.append((Item)this.S);
        }
        this.append("\nVer 1.5.10");
        this.addCommand(this.J);
        this.addCommand(this.K);
    }

    private void f() {
        this.C = 3;
        this.E();
        this.u();
        this.H.setText(g.a.a(4, 0) + "\n" + g.a.a("ContactUs", w) + "\n");
        this.append((Item)this.H);
        this.append((Item)this.I);
        this.addCommand(this.K);
    }

    private void g() {
        this.C = 2;
        this.E();
        this.u();
        this.append((Item)this.H);
        this.addCommand(this.J);
    }

    private void h() {
        if (aw.equals("noid")) {
            this.f();
            this.H.setText(g.a.a(4, 0) + "-101\n" + g.a.a("ContactUs", w) + "\n");
            return;
        }
        this.C = 8;
        this.E();
        this.u();
        this.H.setText(g.a.a(9, 1));
        this.append((Item)this.H);
        this.append((Item)this.O);
        this.append((Item)this.P);
        this.addCommand(this.J);
        this.addCommand(this.N);
        this.addCommand(this.L);
    }

    private void i() {
        i = this.O.getString();
        j = this.P.getString();
        if (i == null || "".equals(i)) {
            this.h();
            return;
        }
        if (this.E == 10) {
            if (k == null || "".equals(k)) {
                if (aw.equals("noid") || !aI) {
                    this.m();
                    return;
                }
                this.D = 10;
                this.aR = false;
                this.l();
                return;
            }
            this.m();
            return;
        }
        if (k == null || "".equals(k)) {
            this.D = 6;
            this.aR = false;
            this.l();
            return;
        }
        o = "";
        this.C = 6;
        this.d();
    }

    private void j() {
        this.C = 5;
        this.E();
        this.u();
        this.H.setText(g.a.a(8, 1));
        this.append((Item)this.H);
        if (i == null || "".equals(i)) {
            this.append((Item)this.T);
            this.T.setSelectedIndex(1, true);
        }
        this.append((Item)this.O);
        this.append((Item)this.P);
        this.addCommand(this.J);
        this.addCommand(this.K);
    }

    private void k() {
        i = "";
        j = "";
        this.O.setString(i);
        this.P.setString(j);
        o = "";
        if (this.E == 10) {
            if (k == null || "".equals(k)) {
                this.D = 10;
                this.aR = true;
                this.l();
                return;
            }
            this.m();
            return;
        }
        if (k == null || "".equals(k)) {
            this.D = 9;
            this.aR = true;
            this.l();
            return;
        }
        this.C = 9;
        this.d();
    }

    private void l() {
        if (aw.equals("noid")) {
            this.m();
            return;
        }
        this.C = 22;
        this.d();
    }

    private void m() {
        this.C = 10;
        this.d();
    }

    private void n() {
        this.f();
        this.H.setText(g.a.a(13, 0));
        this.I.setText("");
    }

    private void o() {
        this.C = 15;
        this.E();
        this.u();
        this.append((Item)this.H);
        this.addCommand(this.J);
        this.addCommand(this.K);
    }

    private void p() {
        this.C = 16;
        this.E();
        this.u();
        this.append(g.a.a("msg", this.g));
        this.append((Item)this.U);
        this.H.setText(g.a.a("all_desc", this.g));
        this.append((Item)this.H);
        this.addCommand(this.J);
        this.addCommand(this.K);
    }

    private void q() {
        this.C = 19;
        this.r();
        this.append((Item)this.O);
        this.append((Item)this.P);
        this.append(av.equals("") ? "" : av + g.a.a(59, 1));
    }

    private void r() {
        this.E();
        this.u();
        this.Q.setLabel(g.a.a(10, 1));
        this.Q.setMaxSize(21);
        this.R.setLabel(g.a.a(11, 1));
        this.R.setMaxSize(21);
        if (this.aa == 3) {
            this.H.setText(g.a.a(g.a.a(12, 1), "$(str)", this.af + ""));
            this.append((Item)this.H);
        } else if (this.aa == 4) {
            this.H.setText(g.a.a("msg", this.g));
            this.append((Item)this.H);
            this.append((Item)this.V);
        }
        this.append((Item)this.Q);
        this.append((Item)this.R);
        this.addCommand(this.J);
        this.addCommand(this.K);
        this.V.setString("30");
        this.Q.setString("");
        this.R.setString("");
    }

    private void s() {
        this.E = 10;
        this.aa = 2;
        g.a.a(ar, this.X);
        this.O.setString(i);
        this.P.setString(j);
        if (i == null || "".equals(i)) {
            if (aw.equals("noid") || !aI) {
                this.m();
                return;
            }
            this.aR = false;
            this.l();
            return;
        }
        this.i();
    }

    private void t() {
        this.E = 17;
        g.a.a(ar, this.X);
        if (i == null || "".equals(i)) {
            v0.C = 18;
            this.r();
            this.append((Item)this.T);
            this.append(av.equals("") ? "" : av + g.a.a(59, 1));
            return;
        }
        this.O.setString(i);
        this.P.setString(j);
        this.q();
    }

    private void u() {
        this.removeCommand(this.J);
        this.removeCommand(this.K);
        this.removeCommand(this.L);
        this.removeCommand(this.M);
        this.removeCommand(this.N);
    }

    public final void run() {
        try {
            while (this.G && this.G) {
                Object object;
                block5 : switch (this.C) {
                    case 27: {
                        Object object2;
                        object = this;
                        int n2 = 0;
                        if (g.a.a(((a)object).aG[4], ((a)object).aG[5])) {
                            object2 = object;
                            ((a)object).C = 28;
                            ((a)object2).E();
                            ((a)object2).u();
                            ((a)object2).H.setText(g.a.a(62, 1));
                            object2.append((Item)((a)object2).H);
                            object2.addCommand(((a)object2).J);
                            break;
                        }
                        object2 = object;
                        ((a)object).C = 29;
                        ((a)object2).E();
                        ((a)object2).u();
                        ((a)object2).H.setText(g.a.a(7, 0));
                        object2.append((Item)((a)object2).H);
                        object2.addCommand(((a)object2).J);
                        object2.addCommand(((a)object2).K);
                        break;
                    }
                    case 9: {
                        Object object3;
                        Object object4;
                        Object object5;
                        int n3;
                        Object object2;
                        int n2;
                        object = this;
                        for (n2 = 0; n2 < 2; ++n2) {
                            boolean bl;
                            object2 = object;
                            ((a)object2).a(g.a.a(54, 1) + av);
                            n3 = m;
                            String string = b;
                            object5 = ((a)object2).Y;
                            object4 = ((a)object2).X;
                            object3 = k;
                            if (object3 == null) {
                                object3 = "";
                            }
                            if (string == null) {
                                string = "0";
                            }
                            if (object5 == null) {
                                object5 = "";
                            }
                            Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
                            hashtable.put("api.url", d + "gs/reg.do");
                            hashtable.put("m", object3);
                            hashtable.put("c", object4);
                            hashtable.put("rop", object5);
                            hashtable.put("cp", string);
                            object5 = g.a.a(hashtable, n3 != 0, aH);
                            if (g.a.h((String)object5) < 0) {
                                super.a(g.a.a(55, 1) + ": " + g.a.h((String)object5));
                                bl = false;
                            } else {
                                i = g.a.b((String)object5, 2);
                                j = g.a.b((String)object5, 3);
                                ((a)object2).O.setString(i);
                                ((a)object2).P.setString(j);
                                bl = true;
                            }
                            if (bl) {
                                super.a(g.a.a(27, 1) + "\n" + av + ":" + i + "\n" + g.a.a(5, 1) + ":" + j);
                                super.i();
                                break block5;
                            }
                            m = !m;
                        }
                        ((a)object).a(g.a.a(28, 1));
                        ((a)object).f();
                        ((a)object).H.setText(g.a.a("ContactUs", w) + "\n");
                        break;
                    }
                    case 6: {
                        this.z();
                        break;
                    }
                    case 10: {
                        this.A();
                        break;
                    }
                    case 17: {
                        this.B();
                        break;
                    }
                    case 13: {
                        Object object5;
                        Object object2;
                        object = this;
                        int n2 = 0;
                        if (g.a.a(((a)object).ag, ((a)object).ah + (((a)object).al + 1))) {
                            ++((a)object).am;
                            ++((a)object).an;
                            ((a)object).F += ((a)object).ak;
                            g.a.a(((a)object).ax, ((a)object).F);
                            ++((a)object).al;
                            if (((a)object).F < ((a)object).az) {
                                object2 = g.a.a("msg2", ((a)object).g);
                                if (((String)object2).equals("")) {
                                    object2 = g.a.a(8, 0) + "\uff0c" + g.a.a(10, 0);
                                }
                                object5 = g.a.a(g.a.a(g.a.a((String)object2, "$(str3)", ((a)object).ai), "$(str2)", ((a)object).aj - ((a)object).al + ""), "$(str1)", ((a)object).al + "") + "\n";
                                if (i != null && !"".equals(i) && !aw.equals("noid")) {
                                    object5 = (String)object5 + av + ":" + " " + i + "\n" + g.a.a(5, 1) + ":" + " " + j + "\n";
                                }
                                ((a)object).H.setText((String)object5);
                                super.o();
                            } else {
                                n += ((a)object).F;
                                g.a.J();
                                ((a)object).f.remove(((a)object).ax);
                                object2 = g.a.b(((a)object).az);
                                if (!at.equals("") && !aw.equals("noid")) {
                                    try {
                                        au = Integer.parseInt(g.a.a("coinExc", w));
                                        int n4 = ((a)object).az * 100 / au;
                                        object2 = n4 + at + "(" + (String)object2 + ")";
                                    }
                                    catch (Exception exception) {
                                        s = u;
                                        break;
                                    }
                                }
                                object5 = g.a.a(19, 0) + " " + (String)object2 + "\n";
                                if (!g.a.a("msg4", ((a)object).g).equals("")) {
                                    object5 = g.a.a("msg4", ((a)object).g) + "\n";
                                }
                                if (!aw.equals("noid")) {
                                    if (((a)object).F - ((a)object).az > 0 && i != null && !"".equals(i)) {
                                        object5 = g.a.a((String)object5 + g.a.a(g.a.a(50, 1), "$(str)", av + " " + i + " ") + "\n", "$(str1)", at);
                                    }
                                    object5 = (String)object5 + g.a.a("lidmsg", ((a)object).g);
                                }
                                ((a)object).H.setText((String)object5);
                                super.g();
                            }
                            String string = ((a)object).ax;
                            try {
                                object2 = new ByteArrayOutputStream();
                                object5 = new DataOutputStream((OutputStream)object2);
                                ((DataOutputStream)object5).writeUTF(((a)object).ag);
                                ((DataOutputStream)object5).writeUTF(((a)object).ah);
                                ((DataOutputStream)object5).writeInt(((a)object).ak);
                                ((DataOutputStream)object5).writeInt(((a)object).al);
                                ((DataOutputStream)object5).writeInt(((a)object).aj);
                                ((DataOutputStream)object5).writeUTF(((a)object).ai);
                                ((a)object).f.put(string, ((ByteArrayOutputStream)object2).toByteArray());
                                ((FilterOutputStream)object5).close();
                                ((ByteArrayOutputStream)object2).close();
                                super.F();
                            }
                            catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;
                        }
                        ((a)object).H.setText(g.a.a(7, 0));
                        ((a)object).o();
                        break;
                    }
                    case 22: {
                        this.w();
                        break;
                    }
                    case 21: {
                        Object object4;
                        object = this;
                        if (((a)object).X == null || ((a)object).X.equals("") || ((a)object).aq == null || ((a)object).aq.equals("")) {
                            ((a)object).f();
                            ((a)object).H.setText(g.a.a(51, 1) + "\n" + g.a.a("ContactUs", w) + "\n");
                            break;
                        }
                        Hashtable<String, String> hashtable = new Hashtable<String, String>();
                        hashtable.put("Content-Type", "application/x-www-form-urlencoded");
                        Object object2 = "c=" + ((a)object).X + "&r=" + ((a)object).aq;
                        Object object5 = new h.a(d + "gs/down.do", aH, (String)object2, hashtable);
                        new Hashtable();
                        Object object3 = null;
                        try {
                            object3 = h.a.a((h.a)object5, m, "");
                            object4 = ((Hashtable)object3).get("contType").toString();
                            if (object4 != null && ((String)object4).indexOf("audio/midi") < 0) {
                                object3 = h.a.a((h.a)object5, m, "");
                            }
                        }
                        catch (SecurityException securityException) {
                            super.a(g.a.a(40, 1));
                            super.f();
                            ((a)object).H.setText(g.a.a(51, 1) + "\n" + g.a.a("ContactUs", w) + "\n");
                            break;
                        }
                        catch (Exception exception) {
                            if (((a)object).ae < 1) {
                                ++((a)object).ae;
                                m = !m;
                                break;
                            }
                            super.f();
                            ((a)object).H.setText(g.a.a(51, 1) + "\n" + g.a.a("ContactUs", w) + "\n");
                            break;
                        }
                        byte[] byArray = (byte[])((Hashtable)object3).get("data");
                        object4 = byArray;
                        if (byArray == null) {
                            super.f();
                            ((a)object).H.setText(g.a.a(51, 1) + "\n" + g.a.a("ContactUs", w) + "\n");
                            ((a)object).ap = null;
                            break;
                        }
                        if (((Object)object4).length < 4 || object4[0] != false || object4[1] != false || object4[2] != false || object4[3] != false) {
                            super.f();
                            ((a)object).H.setText(g.a.a(51, 1) + "\n" + g.a.a("ContactUs", w) + "\n");
                            ((a)object).ap = null;
                            break;
                        }
                        ((a)object).ap = new byte[((Object)object4).length - 4];
                        System.arraycopy(object4, 4, ((a)object).ap, 0, ((Object)object4).length - 4);
                        ((a)object).G = false;
                        break;
                    }
                    case 24: {
                        Object object3;
                        Object object4;
                        Object object5;
                        int n3;
                        object = this;
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("cmd=update");
                        stringBuffer.append("&c=" + ((a)object).X);
                        stringBuffer.append("&cp=" + b);
                        stringBuffer.append("&event=1");
                        stringBuffer.append("&score=" + ((a)object).aD);
                        stringBuffer.append("&msg=" + ((a)object).aE);
                        stringBuffer.append("&uid=" + p);
                        Object object2 = new Hashtable<String, String>();
                        ((Hashtable)object2).put("Content-Type", "application/x-www-form-urlencoded");
                        try {
                            object5 = new h.a(d + "passport/achieve.do", aH, stringBuffer.toString(), (Hashtable)object2);
                            new Hashtable();
                            object3 = null;
                            object3 = h.a.a((h.a)object5, m, "");
                            object4 = new String((byte[])((Hashtable)object3).get("data"), "UTF-8");
                            object5 = "";
                            if (((String)object4).indexOf("code") > 0) {
                                n3 = ((String)object4).indexOf(":\"") + 2;
                                int n5 = ((String)object4).indexOf("\"", n3);
                                object5 = ((String)object4).substring(n3, n5);
                            } else {
                                super.f();
                                ((a)object).H.setText(g.a.a(g.a.a(52, 1), "$(str)", g.a.a("sentName", w)) + "\n" + g.a.a("ContactUs", w) + "\n");
                            }
                            if ("1".equals(object5) || "-56".equals(object5)) {
                                super.g();
                                ((a)object).H.setText(g.a.a(g.a.a(53, 1), "$(str)", g.a.a("sentName", w)));
                                break;
                            }
                            super.f();
                            ((a)object).H.setText(g.a.a(g.a.a(52, 1), "$(str)", g.a.a("sentName", w)) + "\n" + g.a.a("ContactUs", w) + "\n");
                            break;
                        }
                        catch (Exception exception) {
                            super.f();
                            ((a)object).H.setText(g.a.a(g.a.a(52, 1), "$(str)", g.a.a("sentName", w)) + "\n" + g.a.a("ContactUs", w) + "\n");
                            exception.printStackTrace();
                        }
                    }
                }
                this.H.getText();
                object = null;
                av.equals("");
                if (s == 1) {
                    object = g.a.a(14, 0) + g.a.a(15, 0) + g.a.a(16, 0) + "\n" + g.a.a("ContactUs", w) + "\n";
                    if (!av.equals("")) {
                        object = (String)object + av + ":" + " " + i + "\n" + g.a.a(5, 1) + ":" + " " + j + "\n";
                    }
                    this.f();
                    this.H.setText((String)object);
                } else if (s == 2) {
                    object = g.a.a(17, 0) + g.a.a(15, 0) + "\n" + g.a.a("ContactUs", w) + "\n";
                    if (!av.equals("")) {
                        object = (String)object + av + ":" + " " + i + "\n" + g.a.a(5, 1) + ":" + " " + j + "\n";
                    }
                    this.f();
                    this.H.setText((String)object);
                }
                g.a.a(200L);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        this.aG = null;
        this.A = true;
        g.a.v();
        this.aB = null;
        this.aC = null;
    }

    private static void v() {
        z = null;
        x = null;
        y = null;
        w = null;
        ar = null;
    }

    private static void a(long l2) {
        try {
            Thread.sleep(l2);
            return;
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
            return;
        }
    }

    private void a(String string) {
        if (string != null && !string.equals("")) {
            string = "--" + string + "\n" + this.I.getText();
            this.I.setText(string);
        }
    }

    private void w() {
        if (!aI) {
            if (this.D == 6) {
                o = "";
                this.C = this.D;
                this.d();
                return;
            }
            if (this.D == 9) {
                this.C = 9;
                this.d();
                return;
            }
        }
        String string = i;
        int n2 = this.x();
        if (n2 == -902) {
            n2 = this.x();
        }
        if (n2 < -100 && n2 > -110) {
            m = !m;
            n2 = this.x();
        }
        i = string;
        if (n2 == 0) {
            this.a(g.a.a(21, 1));
            string = null;
            for (int i2 = 0; i2 < this.aB.length; ++i2) {
                try {
                    string = RecordStore.openRecordStore((String)("wappay_gil_api" + g.a.a("rmsName", w)), (String)this.aC[i2], (String)this.aB[i2]);
                    Object object = string;
                    object = new ByteArrayInputStream(object.getRecord(2));
                    DataInputStream dataInputStream = new DataInputStream((InputStream)object);
                    i = dataInputStream.readUTF();
                    j = dataInputStream.readUTF();
                    m = dataInputStream.readBoolean();
                    dataInputStream.readBoolean();
                    k = dataInputStream.readUTF();
                    dataInputStream.readInt();
                    l = dataInputStream.readUTF();
                    dataInputStream.close();
                    ((ByteArrayInputStream)object).close();
                }
                catch (Exception exception) {}
                g.a.a((RecordStore)string);
                if (i == null || "".equals(i)) continue;
                g.a.J();
                break;
            }
            if ((k == null || "".equals(k)) && null != this.aA && !"".equals(this.aA)) {
                this.a(g.a.a(22, 1));
                if (this.y()) {
                    g.a.J();
                }
            }
        } else if (n2 == 1 && null != this.aA && !"".equals(this.aA)) {
            this.a(g.a.a(23, 1));
            if (this.y()) {
                g.a.J();
            }
        }
        if (n2 == -901) {
            if (this.D == 6) {
                this.f();
                this.H.setText(g.a.a(24, 1) + "\n" + g.a.a("ContactUs", w) + "\n");
                return;
            }
            this.n();
            return;
        }
        if (n2 == -7 || n2 == -9 || n2 == -12 || n2 == -13 || n2 == -14 || n2 == -15 || n2 == -16 || n2 == -17 || n2 == -18 || n2 == -19 || n2 == -20) {
            this.h();
            return;
        }
        if (n2 < 0) {
            this.f();
            this.H.setText(g.a.a(25, 1) + ":" + n2 + "\n" + g.a.a("ContactUs", w) + "\n");
            return;
        }
        if (this.D == 6) {
            o = "";
            this.C = this.D;
            this.d();
            return;
        }
        if (this.D == 9) {
            this.C = 9;
            this.d();
            return;
        }
        this.m();
    }

    private int x() {
        int n2;
        this.a(g.a.a(30, 1) + "\uff0c" + g.a.a(31, 1) + ":" + (m ? g.a.a(32, 1) : g.a.a(33, 1)));
        boolean bl = false;
        Object object = new StringBuffer();
        ((StringBuffer)object).append("gc=" + this.X);
        ((StringBuffer)object).append("&mobile=" + k);
        ((StringBuffer)object).append("&lid=" + i);
        ((StringBuffer)object).append("&pwd=" + j);
        ((StringBuffer)object).append("&reg=" + this.aR);
        ((StringBuffer)object).append("&ua=" + c);
        ((StringBuffer)object).append("&ua2=" + l);
        ((StringBuffer)object).append("&locale=" + as[0]);
        Object object2 = new Hashtable();
        ((Hashtable)object2).put("Content-Type", "application/x-www-form-urlencoded");
        try {
            object = new h.a(d + "gs/getliveinfo.do", aH, ((StringBuffer)object).toString(), (Hashtable)object2);
            new Hashtable();
            object2 = h.a.a((h.a)object, m, "");
            object = ((Hashtable)object2).get("contType").toString();
            if (object != null && ((String)object).indexOf("audio/midi") < 0) {
                return -902;
            }
            object = (byte[])((Hashtable)object2).get("data");
            DataInputStream dataInputStream = new DataInputStream((InputStream)(object2 = new ByteArrayInputStream((byte[])object)));
            n2 = dataInputStream.readInt();
            if (n2 == 0) {
                Vector<String> vector = new Vector<String>();
                Vector<String> vector2 = new Vector<String>();
                String string = null;
                while ((string = dataInputStream.readUTF()) != null && !"".equals(string)) {
                    vector2.addElement(string);
                    string = dataInputStream.readUTF();
                    if (string == null || "".equals(string)) break;
                    vector.addElement(string);
                }
                this.aA = dataInputStream.readUTF();
                this.aB = new String[vector.size()];
                this.aC = new String[vector2.size()];
                vector2.copyInto(this.aC);
                vector.copyInto(this.aB);
            } else if (n2 == 1) {
                this.aA = dataInputStream.readUTF();
            } else if (n2 == 2) {
                k = dataInputStream.readUTF();
                l = dataInputStream.readUTF();
            }
            dataInputStream.close();
            ((ByteArrayInputStream)object2).close();
        }
        catch (SecurityException securityException) {
            return -901;
        }
        catch (Exception exception) {
            return -106;
        }
        return n2;
    }

    private boolean y() {
        Object object = new Hashtable<String, String>();
        ((Hashtable)object).put("Content-Type", "application/x-www-form-urlencoded");
        try {
            Object object2 = new h.a(((a)object2).aA, aH, null, (Hashtable)object);
            new Hashtable();
            object2 = (byte[])h.a.a((h.a)object2, m, "").get("data");
            object2 = new ByteArrayInputStream((byte[])object2);
            object = new DataInputStream((InputStream)object2);
            if (((DataInputStream)object).readInt() >= 0) {
                k = ((DataInputStream)object).readUTF();
                l = ((DataInputStream)object).readUTF();
                ((FilterInputStream)object).close();
                ((ByteArrayInputStream)object2).close();
                return true;
            }
            ((FilterInputStream)object).close();
            ((ByteArrayInputStream)object2).close();
        }
        catch (Exception exception) {}
        return false;
    }

    private void z() {
        String string;
        if (o == null || "".equals(o)) {
            int n2 = 0;
            while (true) {
                int n3;
                String string2;
                this.a(g.a.a(26, 1));
                Object object = this;
                boolean bl = m;
                String string3 = c;
                String string4 = b;
                String string5 = ((a)object).X;
                String string6 = j;
                string = i;
                if (string == null || string6 == null || string5 == null || string5.equals("") || string4 == null) {
                    string2 = "-1," + g.a.a(29, 1);
                } else {
                    if (string3 == null) {
                        string3 = "";
                    }
                    object = new Hashtable<String, String>();
                    ((Hashtable)object).put("api.url", d + "gs/login.do");
                    ((Hashtable)object).put("u", string);
                    ((Hashtable)object).put("p", string6);
                    ((Hashtable)object).put("c", string5);
                    ((Hashtable)object).put("cp", string4);
                    ((Hashtable)object).put("ua", string3);
                    string2 = g.a.a((Hashtable)object, bl, aH);
                }
                string = string2;
                int n4 = g.a.h(string2);
                if (n4 < 0) {
                    o = "";
                    n3 = n4;
                } else {
                    string6 = g.a.b(string, 2);
                    if (string6 == null) {
                        o = "";
                        n3 = -1;
                    } else {
                        o = string6;
                        p = g.a.b(string, 3);
                        g.a.J();
                        n3 = n4 = n4;
                    }
                }
                if (n3 == -100 || n4 == -106) {
                    if (n2 >= 1) {
                        this.f();
                        this.H.setText(g.a.a(24, 1) + n4 + "\n" + g.a.a("ContactUs", w) + "\n");
                        return;
                    }
                } else {
                    if (n4 >= 0) break;
                    this.h();
                    return;
                }
                m = !m;
                ++n2;
            }
        }
        switch (this.E) {
            case 17: {
                a a2 = this;
                if (a2.aa == 4) {
                    a2.af = 0;
                    try {
                        a2.af = Integer.parseInt(a2.V.getString());
                    }
                    catch (Exception exception) {}
                    if (a2.af <= 0) {
                        a2.H.setText(g.a.a(13, 1));
                        a2.t();
                        return;
                    }
                }
                a2.C = 17;
                a2.E();
                a2.u();
                string = g.a.a(14, 1) + "\n";
                if (a2.aa == 3) {
                    string = string + g.a.a(15, 1) + "\n" + "" + "\n" + g.a.a(16, 1) + "\n" + g.a.a(17, 1);
                }
                a2.H.setText(string);
                a2.append((Item)a2.H);
                a2.append((Item)a2.I);
                return;
            }
            case 14: {
                a a3 = this;
                this.C = 14;
                a3.E();
                a3.u();
                a3.I.setText("");
                a3.append((Item)a3.I);
                return;
            }
            case 2: {
                this.B = true;
                this.G = false;
                return;
            }
            case 24: {
                a a4 = this;
                this.C = 24;
                a4.E();
                a4.u();
                a4.H.setText(g.a.a(g.a.a(20, 1), "$(str)", g.a.a("sentName", w)));
                a4.append((Item)a4.H);
            }
        }
    }

    private int a(int n2) {
        g.a.a(this.ax, this.F);
        Object object = new StringBuffer();
        ((StringBuffer)object).append("id=" + g.a.a("id", w));
        ((StringBuffer)object).append("&gc=" + this.X);
        ((StringBuffer)object).append("&gv=" + Z);
        ((StringBuffer)object).append("&ua=" + c);
        ((StringBuffer)object).append("&ua2=" + l);
        ((StringBuffer)object).append("&cp=" + b);
        ((StringBuffer)object).append("&rop=" + this.Y);
        ((StringBuffer)object).append("&pv=1.5.10");
        ((StringBuffer)object).append("&op=" + this.ax);
        ((StringBuffer)object).append("&opd=" + this.ay);
        ((StringBuffer)object).append("&lb=" + this.az);
        ((StringBuffer)object).append("&ct=" + n2);
        ((StringBuffer)object).append("&lid=" + i);
        ((StringBuffer)object).append("&pwd=" + j);
        ((StringBuffer)object).append("&key=" + o);
        ((StringBuffer)object).append("&mobile=" + k);
        ((StringBuffer)object).append("&sms=" + this.am);
        ((StringBuffer)object).append("&sid=" + q);
        ((StringBuffer)object).append("&lastct=" + this.ab);
        ((StringBuffer)object).append("&locale=" + as[0]);
        if (aH.equals("POST")) {
            ((StringBuffer)object).append("&platform=" + aL);
            ((StringBuffer)object).append("&profiles=" + aM);
            ((StringBuffer)object).append("&configuration=" + aN);
            ((StringBuffer)object).append("&pimVer=" + aO);
            ((StringBuffer)object).append("&smsc=" + aP);
            ((StringBuffer)object).append("&mmsc=" + aQ);
        }
        if (aJ != null && !aJ.equals("")) {
            ((StringBuffer)object).append("&sms_type=" + aJ);
            ((StringBuffer)object).append("&apid=" + aK);
        }
        Object object2 = new Hashtable<String, String>();
        ((Hashtable)object2).put("Content-Type", "application/x-www-form-urlencoded");
        object2 = new h.a(d + "gs/getinfo.do", aH, ((StringBuffer)object).toString(), (Hashtable)object2);
        try {
            this.a(g.a.a(11, 0));
            object = h.a.a((h.a)object2, m, "");
            object2 = ((Hashtable)object).get("contType").toString();
            if (object2 != null && ((String)object2).indexOf("audio/midi") < 0) {
                return -902;
            }
        }
        catch (SecurityException securityException) {
            this.a(g.a.a(40, 1));
            return -901;
        }
        catch (Exception exception) {
            this.a(g.a.a(39, 1) + ": " + g.a.a(38, 1));
            return -106;
        }
        if (object == null) {
            this.a(g.a.a(39, 1) + ": -4 " + g.a.a(37, 1));
            return -4;
        }
        int n3 = Integer.parseInt(((Hashtable)object).get("respCode").toString());
        if (n3 >= 200 && n3 <= 299 && ((Hashtable)object).get("data") != null) {
            this.am = 0;
            this.F();
            Object object3 = (byte[])((Hashtable)object).get("data");
            object3 = new ByteArrayInputStream((byte[])object3);
            object = new DataInputStream((InputStream)object3);
            try {
                String string;
                this.a(g.a.a(60, 1));
                int n4 = ((DataInputStream)object).readInt();
                while ((string = ((DataInputStream)object).readUTF()) != null && !"".equals(string)) {
                    String string2 = ((DataInputStream)object).readUTF();
                    this.g.put(string, string2);
                }
                ((FilterInputStream)object).close();
                ((ByteArrayInputStream)object3).close();
                this.a(g.a.a(36, 1));
                return n4;
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
                this.a(g.a.a(39, 1) + ": -6 " + g.a.a(35, 1) + ":" + exception2);
                return -6;
            }
        }
        this.a(g.a.a(39, 1) + ": -5 " + g.a.a(34, 1));
        this.a("respCode==" + n3);
        return -5;
    }

    private void A() {
        int n2 = this.a(this.aa);
        if (n2 == -902) {
            n2 = this.a(this.aa);
        }
        if (n2 == -106) {
            m = !m;
            n2 = this.a(this.aa);
        }
        String string = g.a.a("lid", this.g);
        Object object = g.a.a("pwd", this.g);
        String string2 = g.a.a("key", this.g);
        if (string != null && !"".equals(string)) {
            i = string;
            j = object;
            g.a.J();
            if (!aw.equals("noid")) {
                this.H.setText(av + ":" + " " + i + "\n" + g.a.a(5, 1) + ":" + " " + j + "\n");
            }
        }
        o = string2;
        this.ab = "";
        this.aa = n2;
        switch (n2) {
            case 0: {
                if (Integer.parseInt((String)this.g.get("status")) >= 0) {
                    n2 = Integer.parseInt(g.a.a("consume", this.g));
                    string = g.a.a("balance", this.g);
                    n += n2;
                    q = System.currentTimeMillis();
                    g.a.J();
                    g.a.a(this.ax, this.az);
                    object = g.a.a(5, 0) + "\n";
                    if (!aw.equals("noid")) {
                        object = (String)object + av + ":" + " " + i + "\n" + g.a.a(5, 1) + ":" + " " + j + "\n";
                        object = !at.equals("") ? (String)object + g.a.a(41, 1) + ":" + n2 + at + "\n" + g.a.a(42, 1) + ":" + string + at + "\n" : (String)object + g.a.a(41, 1) + ":" + g.a.b(n2) + "\n" + g.a.a(42, 1) + ":" + g.a.b(Integer.parseInt(string)) + "\n";
                        object = (String)object + g.a.a("lidmsg", this.g);
                    }
                    this.H.setText((String)object);
                    this.g();
                    return;
                }
                this.f();
                this.H.setText(g.a.a("msg", this.g) + "\n" + g.a.a("ContactUs", w) + "\n");
                return;
            }
            case 2: {
                this.ai = g.a.a("price", this.g);
                this.ak = Integer.parseInt(g.a.a("lb", this.g));
                this.aj = Integer.parseInt(g.a.a("times", this.g));
                this.ag = g.a.a("shortcode", this.g);
                this.ah = g.a.a("content", this.g);
                this.C();
                return;
            }
            case 4: {
                if (aw.equals("noid")) {
                    this.f();
                    this.H.setText(g.a.a(4, 0) + "\u3002" + n2 + "\n" + g.a.a("ContactUs", w) + "\n");
                    return;
                }
                this.ac = g.a.a("payurl", this.g);
                this.ad = g.a.a("checkurl", this.g);
                this.H.setText("");
                this.Q.setString("");
                this.R.setString("");
                this.t();
                return;
            }
            case 3: {
                if (aw.equals("noid")) {
                    this.f();
                    this.H.setText(g.a.a(4, 0) + "\u3002" + n2 + "\n" + g.a.a("ContactUs", w) + "\n");
                    return;
                }
                String[] stringArray = g.a.a("fee", this.g);
                string = g.a.a("fee_desc", this.g);
                object = g.a.c((String)stringArray, ",");
                stringArray = g.a.c(string, ",");
                this.ac = g.a.a("payurl", this.g);
                this.ad = g.a.a("checkurl", this.g);
                this.ao = new int[((String[])object).length];
                for (int i2 = 0; i2 < ((String[])object).length; ++i2) {
                    this.ao[i2] = Integer.parseInt(object[i2]);
                }
                String[] stringArray2 = new String[stringArray.length + 1];
                System.arraycopy(stringArray, 0, stringArray2, 0, stringArray.length);
                stringArray2[stringArray.length] = g.a.a(2, 1);
                this.U = new ChoiceGroup(null, 1, stringArray2, null);
                this.Q.setString("");
                this.R.setString("");
                this.p();
                return;
            }
            case 5: {
                g.a.J();
                g.a.a(this.ax, this.az);
                this.H.setText(g.a.a("msg", this.g));
                this.g();
                return;
            }
            case -20: 
            case -19: 
            case -18: 
            case -17: 
            case -16: 
            case -15: 
            case -14: 
            case -13: 
            case -12: 
            case -9: 
            case -7: {
                this.h();
                return;
            }
            case -901: {
                this.n();
                return;
            }
        }
        this.f();
        this.H.setText(g.a.a(4, 0) + "\u3002" + n2 + "\n" + g.a.a("ContactUs", w) + "\n");
    }

    private void B() {
        String string = av + ":" + " " + i + "\n";
        string = string + g.a.a(5, 1) + ":" + " " + j + "\n";
        try {
            this.a(g.a.a(43, 1));
            Object object = g.a.a(o, this.X, Z, b, "" + this.af, this.Q.getString(), this.R.getString(), this.ac, m, aH);
            int n2 = Integer.parseInt(object[0]);
            String[] stringArray = object;
            if (this.aa == 3) {
                if (n2 == -425 || n2 == -417 || n2 == -422 || n2 == -415) {
                    this.q();
                    this.insert(0, (Item)new StringItem("", g.a.a(44, 1)));
                    return;
                }
                if (n2 < 0) {
                    this.a(g.a.a(45, 1) + ": " + n2 + "\n" + g.a.a(46, 1) + g.a.a("ContactUs", w) + "\n");
                    this.f();
                    this.H.setText(string);
                    return;
                }
                this.a(g.a.a(47, 1));
                object = object[2];
                do {
                    g.a.a(1000L);
                    n2 = Integer.parseInt(g.a.b((String)object, this.ad, m)[0]);
                    if (n2 != 0) continue;
                    this.a(g.a.a(48, 1));
                    this.ab = this.aa + "";
                    this.aa = 1;
                    this.aG = stringArray;
                    this.m();
                    return;
                } while (n2 == 2 || n2 == -423 || n2 == -419);
                if (n2 == -425 || n2 == -417 || n2 == -422 || n2 == -415) {
                    this.q();
                    this.insert(0, (Item)new StringItem("", g.a.a(44, 1)));
                    return;
                }
                this.a(g.a.a(45, 1) + ": " + n2 + "\n" + g.a.a(46, 1) + g.a.a("ContactUs", w) + "\n");
                this.f();
                this.H.setText(string);
                return;
            }
            if (this.aa == 4) {
                if (n2 >= 0) {
                    this.a(g.a.a(48, 1));
                    this.ab = this.aa + "";
                    this.aa = 1;
                    this.aG = stringArray;
                    this.m();
                    return;
                }
                if (n2 == -425 || n2 == -417 || n2 == -422 || n2 == -415) {
                    this.q();
                    this.insert(0, (Item)new StringItem("", g.a.a(44, 1)));
                    return;
                }
                this.a(g.a.a(45, 1) + ": " + n2 + "\n" + g.a.a(49, 1) + g.a.a("ContactUs", w) + "\n");
                this.f();
                this.H.setText(string);
            }
            return;
        }
        catch (Exception exception) {
            this.a(g.a.a(25, 1));
            this.f();
            return;
        }
    }

    private void C() {
        String string = null;
        if (this.F > 0 && this.F < this.az) {
            string = g.a.a("msg2", this.g);
            if (string.endsWith("")) {
                string = g.a.a(8, 0) + "\uff0c" + g.a.a(10, 0);
            }
            string = g.a.a(g.a.a(g.a.a(string, "$(str3)", this.ai), "$(str2)", this.aj - this.al + ""), "$(str1)", this.al + "") + "\n";
        } else {
            string = g.a.a("msg1", this.g);
            if (string.equals("")) {
                string = g.a.a(9, 0) + "\uff0c" + g.a.a(10, 0);
            }
            string = g.a.a(g.a.a(string, "$(str2)", this.ai), "$(str1)", this.aj + "") + "\n";
        }
        if (i != null && !"".equals(i) && !aw.equals("noid")) {
            string = string + av + ":" + " " + i + "\n" + g.a.a(5, 1) + ":" + " " + j + "\n";
        }
        this.H.setText(string);
        this.o();
    }

    public final void itemStateChanged(Item item) {
        int n2;
        block13: {
            if (item != this.T || this.C != 18 && this.C != 23) break block13;
            try {
                n2 = this.size();
                switch (this.T.getSelectedIndex()) {
                    case 0: {
                        if (n2 > 1 && this.get(n2 - 2) == this.P) {
                            this.delete(this.size() - 2);
                            this.delete(this.size() - 2);
                        }
                        break;
                    }
                    case 1: {
                        if (n2 > 1 && this.get(n2 - 2) != this.P) {
                            this.insert(this.size() - 1, (Item)this.O);
                            this.insert(this.size() - 1, (Item)this.P);
                        } else {
                            break;
                        }
                    }
                }
            }
            catch (Exception exception) {}
        }
        if (item == this.T && this.C == 5) {
            n2 = this.size();
            switch (this.T.getSelectedIndex()) {
                case 0: {
                    if (n2 <= 0 || this.get(n2 - 1) != this.P) break;
                    this.delete(n2 - 1);
                    this.delete(n2 - 2);
                    return;
                }
                case 1: {
                    if (n2 <= 0 || this.get(n2 - 1) == this.P) break;
                    this.append((Item)this.O);
                    this.append((Item)this.P);
                }
            }
        }
    }

    /*
     * Handled duff style switch with additional control
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public final void commandAction(Command object, Displayable displayable) {
        if (object == this.J) {
            this.H.setText("");
            switch (this.C) {
                case 26: 
                case 29: {
                    v0.C = 27;
                    this.H.setText(g.a.a(61, 1));
                    this.I.setText("");
                    this.d();
                    return;
                }
                case 28: {
                    this.G = false;
                    return;
                }
                case 4: {
                    return;
                }
                case 5: {
                    this.I.setText("");
                    o = "";
                    if (this.get(1) != this.T) {
                        this.i();
                        return;
                    }
                    switch (this.T.getSelectedIndex()) {
                        case 0: {
                            this.k();
                            return;
                        }
                        case 1: {
                            this.i();
                            return;
                        }
                    }
                    return;
                }
                case 2: {
                    this.B = true;
                    if (this.aG != null && this.aG[3].equals("false")) {
                        v1.C = 26;
                        this.E();
                        this.u();
                        this.H.setText(this.aG[6]);
                        this.append((Item)this.H);
                        this.addCommand(this.J);
                        this.addCommand(this.K);
                        return;
                    }
                    this.G = false;
                    return;
                }
                case 7: {
                    this.B = true;
                    this.A = true;
                    this.G = false;
                    return;
                }
                case 1: {
                    this.H.setText("");
                    this.I.setText("");
                    if (aw.equals("noid")) {
                        this.aa = 2;
                        if (z != null && aw.equals("noid")) {
                            this.ak = Integer.parseInt(z[2]);
                            if (this.ak == 0) {
                                this.ak = this.az;
                            }
                            this.ai = g.a.b(this.ak);
                            this.aj = this.az / this.ak;
                            this.ag = z[0];
                            this.ah = g.a.a(g.a.a(g.a.a(z[1], "{gc}", this.X), "{cp}", b), "{op}", this.ax);
                            this.C();
                            return;
                        }
                        this.s();
                        return;
                    }
                    this.aa = v[this.S.getSelectedIndex()];
                    switch (this.aa) {
                        case 1: {
                            this.E = 10;
                            this.j();
                            return;
                        }
                        case 2: {
                            this.s();
                            return;
                        }
                        case 3: 
                        case 4: {
                            this.D = this.C;
                            if ((k == null || "".equals(k)) && aI) {
                                this.l();
                                return;
                            }
                            this.m();
                            return;
                        }
                    }
                    return;
                }
                case 16: {
                    int n2 = this.U.getSelectedIndex();
                    if (n2 == this.U.size() - 1) {
                        this.I.setText("");
                        this.s();
                        return;
                    }
                    this.af = this.ao[n2];
                    this.t();
                    return;
                }
                case 18: {
                    this.I.setText("");
                    this.E = 17;
                    switch (this.T.getSelectedIndex()) {
                        case 0: {
                            this.k();
                            return;
                        }
                        case 1: {
                            this.i();
                            return;
                        }
                    }
                    return;
                }
                case 19: {
                    this.E = 17;
                    this.I.setText("");
                    this.i();
                    return;
                }
                case 12: {
                    switch (null.getSelectedIndex()) {
                        case 0: {
                            m = !m;
                            this.s();
                            return;
                        }
                        case 1: {
                            this.I.setText("");
                            this.aa = 2;
                            this.m();
                            return;
                        }
                        case 2: {
                            this.D = this.C;
                            this.aa = 3;
                            this.m();
                            return;
                        }
                    }
                    return;
                }
                case 15: {
                    void var1_5;
                    v2.C = 13;
                    this.E();
                    this.u();
                    String string = g.a.a("msg3", this.g);
                    if (string.equals("")) {
                        String string2 = g.a.a(12, 0);
                    }
                    this.H.setText(g.a.a(g.a.a((String)var1_5, "$(str2)", this.ai), "$(str1)", Integer.toString(this.al + 1)));
                    this.append((Item)this.H);
                    return;
                }
                case 20: {
                    this.B = false;
                    this.G = false;
                    return;
                }
                case 8: {
                    this.I.setText("");
                    this.i();
                    return;
                }
                case 23: {
                    this.E = 24;
                    switch (this.T.getSelectedIndex()) {
                        case 0: {
                            this.k();
                            return;
                        }
                        case 1: {
                            this.i();
                            return;
                        }
                    }
                    return;
                }
                case 25: {
                    this.E = 24;
                    this.i();
                    return;
                }
            }
            return;
        }
        if (object == this.L) {
            switch (this.C) {
                case 3: 
                case 4: 
                case 5: 
                case 7: 
                case 8: {
                    this.B = false;
                    this.G = false;
                    return;
                }
            }
            return;
        }
        if (object == this.K) {
            int n3 = 0;
            block56: do {
                switch (n3 == 0 ? this.C : n3) {
                    case 26: 
                    case 29: {
                        this.G = false;
                        return;
                    }
                    case 7: {
                        this.B = false;
                        this.A = true;
                        this.G = false;
                        return;
                    }
                    case 5: {
                        switch (this.E) {
                            case 2: {
                                this.B = false;
                                this.G = false;
                                return;
                            }
                        }
                        String[] stringArray = g.a.a(ar, this.X);
                        this.O.setString(stringArray[0]);
                        this.P.setString(stringArray[1]);
                        this.e();
                        return;
                    }
                    case 1: 
                    case 3: 
                    case 12: 
                    case 23: 
                    case 25: {
                        this.B = false;
                        this.G = false;
                        return;
                    }
                    case 16: {
                        if (this.D == 12) {
                            v3.C = 12;
                            this.E();
                            this.u();
                            this.append(null);
                            this.addCommand(this.J);
                            this.addCommand(this.K);
                            return;
                        }
                        this.e();
                        return;
                    }
                    case 18: 
                    case 19: {
                        if (this.aa == 3) {
                            this.p();
                            return;
                        }
                        if (this.aa != 4) return;
                        n3 = 14;
                        continue block56;
                    }
                    case 15: {
                        this.f();
                        return;
                    }
                    case 14: {
                        this.e();
                        return;
                    }
                }
                return;
            } while (true);
            return;
        }
        if (object != this.N) return;
        this.H.setText("");
        this.k();
    }

    private static String b(String object, String string) {
        String string2 = "";
        try {
            while (((String)object).getBytes().length < string.getBytes().length / 2) {
                object = (String)object + (String)object;
            }
            object = ((String)object).getBytes("UTF-8");
            for (int i2 = 0; i2 < string.length() - 1; i2 += 2) {
                int n2 = 0;
                n2 = (byte)g.a.e(string.substring(i2, i2 + 2)) ^ object[i2 / 2];
                string2 = string2 + (char)n2;
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return string2;
    }

    private void D() {
        String[] stringArray;
        Object object;
        this.J = new Command(g.a.a(0, 0), 4, 1);
        this.K = new Command(g.a.a(1, 0), 3, 1);
        this.L = new Command(g.a.a(2, 0), 3, 1);
        this.M = new Command(g.a.a(3, 0), 4, 1);
        new Command(g.a.a(6, 1), 8, 1);
        this.N = new Command(g.a.a(7, 1), 8, 1);
        this.H = new StringItem("", "");
        this.I = new StringItem("", "");
        this.O = new TextField(av, i, 10, 0);
        this.P = new TextField(g.a.a(5, 1), j, 20, 0);
        new TextField(g.a.a(56, 1), "", 20, 3);
        this.Q = new TextField("", "", 20, 0);
        this.R = new TextField("", "", 20, 0);
        this.V = new TextField(g.a.a(57, 1), "30", 20, 5);
        this.setCommandListener(this);
        this.setItemStateListener(this);
        Object object2 = g.a.a("keyp", w);
        if (object2.equals("")) {
            s = u;
            return;
        }
        String string = g.a.a("payVer", w);
        if (string.equals("")) {
            s = u;
            return;
        }
        d = g.a.b("lakoo" + string, (String)object2);
        aw = g.a.a("idType", w);
        av = g.a.a("idName", w);
        at = g.a.a("coinName", w);
        if (av.equals("") || at.equals("")) {
            aw = "noid";
        }
        if (g.a.b(200).equals("")) {
            s = u;
            return;
        }
        object2 = g.a.a("isSearchID", w);
        if (!object2.equals("") && object2.toLowerCase().equals("false")) {
            aI = false;
        }
        if ((object2 = g.a.a("payMethod", w)).equals("")) {
            s = u;
            return;
        }
        if (!aw.equals("noid")) {
            int n2;
            object = g.a.c((String)object2, ",");
            v = new int[((String[])object).length];
            object2 = new String[((String[])object).length];
            for (n2 = 0; n2 < ((Object)object).length; ++n2) {
                try {
                    int n3;
                    g.a.v[n2] = n3 = Integer.parseInt((String)object[n2]);
                    switch (n3) {
                        case 1: {
                            object2[n2] = g.a.a(g.a.a(1, 1), "$(str)", at);
                            break;
                        }
                        case 2: {
                            object2[n2] = g.a.a(2, 1);
                            break;
                        }
                        case 3: {
                            object2[n2] = g.a.a(3, 1);
                            break;
                        }
                        case 4: {
                            object2[n2] = g.a.a(4, 1);
                        }
                    }
                    continue;
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            this.S = new ChoiceGroup(g.a.a(0, 1), 1, object2, null);
            try {
                n2 = Integer.parseInt(g.a.a("payDefault", w));
                if (n2 > ((String[])object2).length - 1) {
                    n2 = 0;
                }
                this.S.setSelectedIndex(n2, true);
            }
            catch (Exception exception) {}
            object2 = new String[]{"", "", "", ""};
            this.U = new ChoiceGroup(null, 1, object2, null);
            object = new String[]{g.a.a(7, 1) + av, g.a.a(g.a.a(58, 1), "$(str)", av)};
            this.T = new ChoiceGroup("", 1, (String[])object, null);
        }
        if (aw.equals("noid") && (object = g.a.a("keys", w)) != null && ((String)object).length() > 0 && (object2 = g.a.b("lakoo" + string + "sms", (String)object)).length() > 0 && (stringArray = g.a.c((String)object2, ",")) != null && stringArray.length == 3) {
            z = stringArray;
        }
    }

    private void E() {
        int n2 = 0;
        for (n2 = this.size() - 1; n2 >= 0; --n2) {
            this.delete(n2);
        }
    }

    private void b(String stringArray) {
        this.E = 0;
        this.C = 0;
        this.A = false;
        this.B = false;
        this.al = 0;
        this.I.setText("");
        stringArray = g.a.a(ar, (String)stringArray);
        this.O.setString(stringArray[0]);
        this.P.setString(stringArray[1]);
    }

    private static String b(int n2) {
        String string;
        String[] stringArray;
        if (w == null) {
            g.a.b();
        }
        if ((stringArray = g.a.a("locale", w)).equals("")) {
            s = u;
            return "";
        }
        if ((stringArray = g.a.c((String)stringArray, ",")).length != 3) {
            s = u;
            return "";
        }
        as = stringArray;
        int n3 = 0;
        int n4 = 0;
        n3 = Integer.parseInt(as[2]);
        if (as[1].equals("\u5143")) {
            n3 = n2 / 100;
            n4 = n2 % 100;
            string = n4 < 10 ? n3 + ".0" + n4 + as[1] : n3 + "." + n4 + as[1];
        } else {
            n4 = n2 * n3;
            n3 = n4 / 100;
            if (n4 % 100 >= 50) {
                ++n3;
            }
            string = as[1] + " " + n3;
        }
        if (ar == null) {
            g.a.v();
        }
        return string;
    }

    private static int c(String string) {
        if ((string = e.get(string)) != null) {
            return (Integer)((Object)string);
        }
        return 0;
    }

    private static long d(String string) {
        if ((string = h.get(string)) != null) {
            return (Long)((Object)string);
        }
        return System.currentTimeMillis();
    }

    public static String[] a() {
        try {
            g.a.K();
        }
        catch (Exception exception) {}
        return new String[]{i, j, o, p, "" + m, k, l};
    }

    public static void a(String string, String string2, boolean bl) {
        i = string;
        j = string2;
        m = bl;
        g.a.J();
    }

    public static String[] a(MIDlet object, String string) {
        try {
            g.a.K();
        }
        catch (Exception exception) {}
        try {
            if (i == null || i.equals("")) {
                g.a.a(object);
                if (aF != null && !aF.equals("") && string != null && !string.equals("")) {
                    object = string + b;
                    while (object.length() < aF.length() / 2) {
                        object = (String)object + string;
                    }
                    object = object.getBytes();
                    string = "";
                    for (int i2 = 0; i2 < aF.length() - 1; i2 += 2) {
                        int n2 = 0;
                        n2 = (byte)g.a.e(aF.substring(i2, i2 + 2)) ^ object[i2 / 2];
                        string = string + (char)n2;
                    }
                    i = string.substring(0, string.indexOf("@"));
                    j = string.substring(string.indexOf("@") + 1, string.length());
                }
            }
        }
        catch (Exception exception) {}
        return new String[]{i, j, o, p, "" + m, k, l};
    }

    private static int e(String string) {
        if (string == null || string.length() < 1) {
            return 0;
        }
        int n2 = 1;
        int n3 = 0;
        block0: for (int i2 = string.length() - 1; i2 > -1; --i2) {
            char c2 = string.charAt(i2);
            for (int i3 = 0; i3 < aS.length; ++i3) {
                if (aS[i3] != c2) continue;
                n3 += n2 * i3;
                n2 <<= 4;
                continue block0;
            }
        }
        return n3;
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
            byArray2 = new byte[recordStore.getRecordSize(n2)];
            recordStore.getRecord(n2, byArray2, 0);
        } else {
            recordStore.setRecord(n2, byArray, 0, byArray.length);
        }
        g.a.a(recordStore);
        return byArray2;
    }

    private void F() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        int n2 = ((a)object).f.size();
        Enumeration enumeration = ((a)object).f.keys();
        try {
            dataOutputStream.writeInt(n2);
            dataOutputStream.writeInt(((a)object).am);
            dataOutputStream.writeInt(((a)object).an);
            while (enumeration.hasMoreElements()) {
                String string = (String)enumeration.nextElement();
                byte[] byArray = (byte[])((a)object).f.get(string);
                dataOutputStream.writeUTF(string);
                dataOutputStream.writeInt(byArray.length);
                dataOutputStream.write(byArray);
            }
            Object object = byteArrayOutputStream.toByteArray();
            g.a.a(3, (byte[])object, "gil_api_private");
            dataOutputStream.close();
            byteArrayOutputStream.close();
            return;
        }
        catch (Exception exception) {
            s = t;
            return;
        }
    }

    private void G() {
        try {
            Object object = g.a.a(3, null, "gil_api_private");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])object);
            DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
            int n2 = dataInputStream.readInt();
            this.am = dataInputStream.readInt();
            this.an = dataInputStream.readInt();
            for (int i2 = 0; i2 < n2; ++i2) {
                object = dataInputStream.readUTF();
                boolean bl = false;
                byte[] byArray = new byte[dataInputStream.readInt()];
                dataInputStream.readFully(byArray);
                this.f.put(object, byArray);
            }
            dataInputStream.close();
            byteArrayInputStream.close();
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    private boolean f(String object) {
        if ((object = this.f.get(object)) == null) {
            return false;
        }
        object = (byte[])object;
        try {
            object = new ByteArrayInputStream((byte[])object);
            DataInputStream dataInputStream = new DataInputStream((InputStream)object);
            this.ag = dataInputStream.readUTF();
            this.ah = dataInputStream.readUTF();
            this.ak = dataInputStream.readInt();
            this.al = dataInputStream.readInt();
            this.aj = dataInputStream.readInt();
            this.ai = dataInputStream.readUTF();
            dataInputStream.close();
            ((ByteArrayInputStream)object).close();
            return true;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    private static void H() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        int n2 = e.size();
        Enumeration enumeration = e.keys();
        try {
            dataOutputStream.writeInt(n2);
            while (enumeration.hasMoreElements()) {
                String string = (String)enumeration.nextElement();
                int n3 = g.a.c(string);
                long l2 = g.a.d(string);
                dataOutputStream.writeUTF(string);
                dataOutputStream.writeInt(n3);
                dataOutputStream.writeLong(l2);
            }
            byte[] byArray = byteArrayOutputStream.toByteArray();
            g.a.a(1, byArray, "gil_api_private");
            dataOutputStream.close();
            byteArrayOutputStream.close();
            return;
        }
        catch (Exception exception) {
            s = t;
            return;
        }
    }

    private static void I() {
        Object object = g.a.a(1, null, "gil_api_private");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])object);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        int n2 = dataInputStream.readInt();
        for (int i2 = 0; i2 < n2; ++i2) {
            object = dataInputStream.readUTF();
            int n3 = dataInputStream.readInt();
            long l2 = dataInputStream.readLong();
            e.put(object, new Integer(n3));
            h.put(object, new Long(l2));
        }
        dataInputStream.close();
        byteArrayInputStream.close();
    }

    public static void a(String string, int n2) {
        e.put(string, new Integer(n2));
        g.a.H();
    }

    private static void J() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Object object = new DataOutputStream(byteArrayOutputStream);
        Object object2 = null;
        try {
            if (i != null && i.length() > 0) {
                i.trim();
            }
            if (j != null && j.length() > 0) {
                j.trim();
            }
            ((DataOutputStream)object).writeUTF(i);
            ((DataOutputStream)object).writeUTF(j);
            ((DataOutputStream)object).writeBoolean(m);
            ((DataOutputStream)object).writeBoolean(m);
            ((DataOutputStream)object).writeUTF(k);
            ((DataOutputStream)object).writeInt(n);
            ((DataOutputStream)object).writeUTF(l);
            object2 = byteArrayOutputStream.toByteArray();
            g.a.a(2, (byte[])object2, "gil_api_private");
            ((FilterOutputStream)object).close();
            byteArrayOutputStream.close();
        }
        catch (Exception exception) {
            s = t;
        }
        try {
            if (r) {
                String string = object2;
                object2 = "wappay_gil_api" + g.a.a("rmsName", w);
                object = string;
                boolean bl = false;
                RecordStore recordStore = null;
                byte[] byArray = null;
                recordStore = RecordStore.openRecordStore((String)object2, (boolean)false, (int)1, (boolean)false);
                if (object == null) {
                    boolean bl2 = false;
                    byArray = new byte[recordStore.getRecordSize(2)];
                    recordStore.getRecord(2, byArray, 0);
                } else {
                    recordStore.setRecord(2, (byte[])object, 0, ((Object)object).length);
                }
                g.a.a(recordStore);
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private static String a(String string, Hashtable hashtable) {
        if ((string = hashtable.get(string)) == null) {
            return "";
        }
        return string;
    }

    private static String a(int n2, int n3) {
        String string = "";
        try {
            switch (n3) {
                case 0: {
                    string = x[n2];
                    break;
                }
                case 1: {
                    string = y[n2];
                }
            }
        }
        catch (Exception exception) {}
        return string;
    }

    private static void K() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(g.a.a(2, null, "gil_api_private"));
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        i = dataInputStream.readUTF();
        j = dataInputStream.readUTF();
        m = dataInputStream.readBoolean();
        dataInputStream.readBoolean();
        k = dataInputStream.readUTF();
        n = dataInputStream.readInt();
        l = dataInputStream.readUTF();
        dataInputStream.close();
        byteArrayInputStream.close();
        try {
            if ((i == null || i.equals("")) && g.a.a("proxy", w).toLowerCase().equals("true")) {
                m = true;
            }
            if (g.a.a("method", w).toLowerCase().equals("get")) {
                aH = "GET";
            }
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    private static String g(String object) {
        String string = "";
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        byte[] byArray = new byte[1024];
        try {
            try {
                inputStream = "".getClass().getResourceAsStream((String)object);
                if (inputStream != null) {
                    int n2;
                    while ((n2 = inputStream.read(byArray)) > 0) {
                        dataOutputStream.write(byArray, 0, n2);
                    }
                    string = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
                    inputStream.close();
                }
                dataOutputStream.close();
                byteArrayOutputStream.close();
            }
            catch (Exception exception) {
                s = u;
                exception.printStackTrace();
            }
        }
        catch (Throwable throwable) {
            object = throwable;
            throw throwable;
        }
        return string;
    }

    public static void a(MIDlet mIDlet) {
        b = mIDlet.getAppProperty("CP");
        b = b == null ? "0" : b.trim();
        c = mIDlet.getAppProperty("UA");
        if (c == null || c.equals("")) {
            int n2;
            int n3;
            c = mIDlet.getAppProperty("MIDlet-Install-Notify");
            c = c != null ? ((n3 = c.indexOf("ua=") + 3) >= 0 ? ((n2 = c.indexOf("&", n3)) >= 0 ? c.substring(n3, n2) : "untrust") : "untrust") : "untrust";
        }
        Z = (Z = mIDlet.getAppProperty("MIDlet-Version")) == null ? "1" : Z.trim();
        aF = mIDlet.getAppProperty("key");
        aL = System.getProperty("microedition.platform");
        aM = System.getProperty("microedition.profiles");
        aN = System.getProperty("microedition.configuration");
        aO = System.getProperty("microedition.pim.version");
        aP = System.getProperty("wireless.messaging.sms.smsc");
        aJ = mIDlet.getAppProperty("sms_type");
        aK = mIDlet.getAppProperty("ApplicationID");
    }

    /*
     * Loose catch block
     */
    public static boolean a(String string, String string2) {
        boolean bl;
        block20: {
            MessageConnection messageConnection;
            if (string == null || "".equals(string.trim())) {
                return false;
            }
            if (string2 == null || "".equals(string2.trim())) {
                return false;
            }
            string = "sms://" + string;
            MessageConnection messageConnection2 = null;
            bl = false;
            String string3 = string;
            MessageConnection messageConnection3 = null;
            Object object = null;
            XConnection xConnection = new XConnection();
            if (string3.startsWith("sms")) {
                object = xConnection;
            } else {
                String string4 = string3;
                messageConnection = null;
                XConnection xConnection2 = null;
                XConnection xConnection3 = new XConnection();
                xConnection2 = string4.startsWith("sms") ? xConnection3 : Connector.open((String)string4);
                object = xConnection2;
            }
            messageConnection2 = (MessageConnection)object;
            string3 = "text";
            messageConnection3 = messageConnection2;
            object = null;
            object = new XMessage();
            TextMessage textMessage = (TextMessage)object;
            textMessage.setAddress(string);
            textMessage.setPayloadText(string2);
            string3 = textMessage;
            messageConnection3 = messageConnection2;
            System.out.println("hardtodie cracked");
            try {
                string3 = null;
                messageConnection3 = messageConnection2;
                System.out.println("hardtodie cracked");
            }
            catch (Exception exception) {
                bl = true;
            }
            if (messageConnection2 == null) break block20;
            try {
                messageConnection3 = messageConnection2;
                if (messageConnection3 != null && (messageConnection = messageConnection3) != null) {
                    messageConnection.close();
                }
                break block20;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            break block20;
            catch (Throwable throwable) {
                try {
                    throwable.printStackTrace();
                    if (messageConnection2 == null) break block20;
                }
                catch (Throwable throwable2) {
                    if (messageConnection2 != null) {
                        try {
                            MessageConnection messageConnection4;
                            MessageConnection messageConnection5 = messageConnection2;
                            if (messageConnection5 != null && (messageConnection4 = messageConnection5) != null) {
                                messageConnection4.close();
                            }
                        }
                        catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                    throw throwable2;
                }
                try {
                    MessageConnection messageConnection6;
                    MessageConnection messageConnection7 = messageConnection2;
                    if (messageConnection7 != null && (messageConnection6 = messageConnection7) != null) {
                        messageConnection6.close();
                    }
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        return bl;
    }

    public static void a(MIDlet mIDlet, Displayable displayable, String string) {
        Display display = Display.getDisplay((MIDlet)mIDlet);
        a = new a(mIDlet);
        a.b(string);
        g.a.a(mIDlet);
        if (aw.equals("noid")) {
            a.f();
            g.a.a.H.setText(g.a.a(18, 0) + "\n" + g.a.a("ContactUs", w));
        } else {
            a.getClass();
            g.a.a.E = 2;
            a.j();
        }
        g.a.a.G = true;
        g.a.a.X = string;
        a.c();
        display.setCurrent((Displayable)a);
        while (!g.a.a.A) {
            g.a.a(300L);
        }
        display.setCurrent(displayable);
    }

    public static boolean a(MIDlet object, Displayable displayable, String string, String string2, String string3, String string4, int n2, boolean bl) {
        String string5;
        block11: {
            int n3 = n2;
            string5 = string3;
            if (g.a.c(string5) >= n3) {
                return true;
            }
            string5 = Display.getDisplay((MIDlet)object);
            a = new a((MIDlet)object);
            g.a.a((MIDlet)object);
            int n4 = n2;
            String string6 = string4;
            String string7 = string3;
            string4 = string2;
            string2 = string;
            object = a;
            ((a)object).b(string4);
            ((a)object).G = true;
            ((a)object).ax = string7;
            ((a)object).ay = string6;
            ((a)object).X = string4;
            ((a)object).F = g.a.c(string7);
            q = g.a.d(string7);
            ((a)object).az = n4;
            ((a)object).W = string2;
            string2 = object;
            string4 = g.a.b(((a)((Object)string2)).az);
            if (!at.equals("") && !aw.equals("noid")) {
                try {
                    au = Integer.parseInt(g.a.a("coinExc", w));
                    int n5 = ((a)((Object)string2)).az * 100 / au;
                    string4 = n5 + at + "(" + string4 + ")";
                }
                catch (Exception exception) {
                    s = u;
                    break block11;
                }
            }
            ((a)((Object)string2)).W = g.a.a("payMsg", w).equals("") ? ((a)((Object)string2)).W + "\n" + g.a.a(g.a.a(6, 0), "$(str)", string4) : ((a)((Object)string2)).W + "\n" + g.a.a("payMsg", w);
        }
        super.c();
        if (s > 0) {
            a.f();
        } else if (g.a.a.F > 0 && g.a.a.F < g.a.a.az) {
            if (a.f(string3)) {
                a.C();
                g.a.a.H.setText(string + "\n" + g.a.a.H.getText());
            } else {
                a.e();
            }
        } else {
            a.e();
        }
        string5.setCurrent((Displayable)a);
        while (!g.a.a.A) {
            g.a.a(500L);
        }
        string5.setCurrent(displayable);
        return g.a.a.B;
    }

    public static boolean a(MIDlet object, Displayable displayable, String string, String string2, String string3, String string4) {
        Display display = Display.getDisplay((MIDlet)object);
        a = new a((MIDlet)object);
        a.b(string2);
        g.a.a((MIDlet)object);
        if (aw.equals("noid")) {
            a.f();
            g.a.a.H.setText(g.a.a(18, 0) + "\n" + g.a.a("ContactUs", w) + "\n");
        } else {
            g.a.a.W = string;
            g.a.a.X = string2;
            g.a.a.aD = string3;
            g.a.a.aE = string4;
            if (i != null && !"".equals(i)) {
                object = a;
                a.C = 25;
                super.E();
                super.u();
                ((a)object).H.setText(((a)object).W);
                object.append((Item)((a)object).H);
                object.append((Item)((a)object).O);
                object.append((Item)((a)object).P);
                object.append(av.equals("") ? "" : av + g.a.a(g.a.a(19, 1), "$(str)", g.a.a("sentName", w)));
                object.addCommand(((a)object).J);
                object.addCommand(((a)object).K);
            } else {
                object = a;
                a.C = 23;
                super.E();
                super.u();
                ((a)object).H.setText(((a)object).W);
                object.append((Item)((a)object).H);
                object.append((Item)((a)object).T);
                object.append(av.equals("") ? "" : av + g.a.a(g.a.a(19, 1), "$(str)", g.a.a("sentName", w)));
                object.addCommand(((a)object).J);
                object.addCommand(((a)object).K);
            }
        }
        a.c();
        display.setCurrent((Displayable)a);
        while (!g.a.a.A) {
            g.a.a(500L);
        }
        display.setCurrent(displayable);
        return g.a.a.B;
    }

    public static String a(String string, String string2, String string3) {
        if (null == string || "".equals(string) || null == string2 || "".equals(string2)) {
            return string;
        }
        String string4 = "";
        int n2 = 0;
        int n3 = 0;
        while ((n3 = string.indexOf(string2, n2)) != -1) {
            string4 = string4 + string.substring(n2, n3);
            string4 = string4 + string3;
            n2 = n3 + string2.length();
        }
        if (n2 != string.length()) {
            string4 = string4 + string.substring(n2);
        }
        return string4;
    }

    /*
     * Exception decompiling
     */
    private static String a(Hashtable var0, boolean var1_1, String var2_2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static String[] c(String string, String string2) {
        if (string == null || string.trim().length() == 0) {
            return null;
        }
        if (string.equals("") || string2.equals("")) {
            return null;
        }
        try {
            boolean bl = false;
            Vector<String> vector = new Vector<String>();
            Object var2_3 = null;
            int n2 = 0;
            int n3 = string.indexOf(string2);
            if (n3 == -1) {
                return new String[]{string};
            }
            while (n3 < string.length() && n3 != -1) {
                String string3 = string.substring(n2, n3);
                vector.addElement(string3);
                n2 = n3 + string2.length();
                n3 = string.indexOf(string2, n3 + string2.length());
            }
            vector.addElement(string.substring(n2 + 1 - string2.length()));
            Object[] objectArray = new String[vector.size()];
            vector.copyInto(objectArray);
            return objectArray;
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static String b(String stringArray, int n2) {
        if (n2 <= 0) {
            return null;
        }
        if (stringArray.indexOf(",") < 0) {
            return null;
        }
        if ((stringArray = g.a.c((String)stringArray, ",")) == null) {
            return null;
        }
        if (n2 > stringArray.length) {
            return null;
        }
        return stringArray[n2 - 1];
    }

    private static int h(String string) {
        try {
            int n2;
            if (string.equals("") || string == null) {
                return -110;
            }
            if ((string = g.a.b(string, 1)) == null) {
                return -110;
            }
            try {
                n2 = Integer.parseInt(string);
            }
            catch (Exception exception) {
                return -110;
            }
            return n2;
        }
        catch (Exception exception) {
            return -110;
        }
    }

    private static String[] a(String object, String object2, String string, String string2, String string3, String string4, String string5, String charSequence, boolean bl, String string6) {
        String[] stringArray = new String[3];
        if (object == null || ((String)object).equals("") || object2 == null || ((String)object2).equals("") || string4 == null || string4.equals("") || string5 == null || string5.equals("")) {
            stringArray[0] = "-1";
            stringArray[1] = "params error";
            return stringArray;
        }
        CharSequence charSequence2 = null;
        charSequence2 = charSequence == null || ((String)charSequence).equals("") ? "http://59.42.254.3:8081/pay/largecharge.do" : charSequence;
        string4 = g.a.i(string4);
        string5 = g.a.i(string5);
        charSequence = new StringBuffer();
        ((StringBuffer)charSequence).append("k=" + (String)object).append("&");
        ((StringBuffer)charSequence).append("c=" + (String)object2).append("&");
        ((StringBuffer)charSequence).append("v=" + string).append("&");
        ((StringBuffer)charSequence).append("cp=" + string2).append("&");
        ((StringBuffer)charSequence).append("t=3&");
        ((StringBuffer)charSequence).append("goodsType=" + string3).append("&");
        ((StringBuffer)charSequence).append("sn=" + string4).append("&");
        ((StringBuffer)charSequence).append("pwd=" + string5);
        ((StringBuffer)charSequence).append("&dt=2");
        object = ((StringBuffer)charSequence).toString();
        object2 = new Hashtable<String, String>();
        ((Hashtable)object2).put("Content-Type", "application/x-www-form-urlencoded");
        object = new h.a((String)charSequence2, string6, (String)object, (Hashtable)object2);
        try {
            object = h.a.a((h.a)object, bl, null);
        }
        catch (Exception exception) {
            object2 = exception;
            exception.printStackTrace();
            stringArray[0] = "-1";
            stringArray[1] = ((Throwable)object2).toString();
            stringArray[2] = "-1";
            return stringArray;
        }
        if (object == null) {
            stringArray[0] = "-1";
            stringArray[1] = "Response==null";
            stringArray[2] = "-1";
            return stringArray;
        }
        int n2 = Integer.parseInt(((Hashtable)object).get("respCode").toString());
        if (n2 >= 200 && n2 <= 299 && ((Hashtable)object).get("data") != null) {
            return g.a.a((byte[])((Hashtable)object).get("data"));
        }
        stringArray[0] = "-1";
        stringArray[1] = "ResponseCode==" + n2;
        stringArray[2] = "-1";
        return stringArray;
    }

    private static String[] b(String object, String object2, boolean bl) {
        String[] stringArray = new String[2];
        if (object == null || ((String)object).equals("")) {
            stringArray[0] = "-1";
            stringArray[1] = "orderId is null";
            return stringArray;
        }
        Object object3 = null;
        object3 = object2 == null || ((String)object2).equals("") ? "http://59.42.254.3:8081/pay/largecheck.do" : object2;
        object = (String)object3 + "?payid=" + (String)object;
        object = new h.a((String)object, "GET", null, null);
        try {
            object = h.a.a((h.a)object, bl, null);
        }
        catch (Exception exception) {
            object2 = exception;
            exception.printStackTrace();
            stringArray[0] = "-1";
            stringArray[1] = ((Throwable)object2).toString();
            return stringArray;
        }
        if (object == null) {
            stringArray[0] = "-1";
            stringArray[1] = "Response==null";
            return stringArray;
        }
        int n2 = Integer.parseInt(((Hashtable)object).get("respCode").toString());
        if (n2 >= 200 && n2 <= 299 && ((Hashtable)object).get("data") != null) {
            return g.a.b((byte[])((Hashtable)object).get("data"));
        }
        stringArray[0] = "-1";
        stringArray[1] = "ResponseCode==" + n2;
        return stringArray;
    }

    private static String[] a(byte[] object) {
        String[] stringArray = new String[7];
        String[] stringArray2 = stringArray;
        stringArray[0] = "-1";
        object = new ByteArrayInputStream((byte[])object);
        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
        try {
            stringArray2[0] = Integer.toString(dataInputStream.readInt());
            stringArray2[1] = dataInputStream.readUTF();
            stringArray2[2] = dataInputStream.readUTF();
            stringArray2[3] = dataInputStream.readUTF();
            stringArray2[4] = dataInputStream.readUTF();
            stringArray2[5] = dataInputStream.readUTF();
            stringArray2[6] = dataInputStream.readUTF();
            ((ByteArrayInputStream)object).close();
            dataInputStream.close();
        }
        catch (Exception exception) {}
        return stringArray2;
    }

    private static String[] b(byte[] object) {
        String[] stringArray = new String[2];
        object = new ByteArrayInputStream((byte[])object);
        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
        try {
            int n2 = dataInputStream.readInt();
            String string = dataInputStream.readUTF();
            ((ByteArrayInputStream)object).close();
            dataInputStream.close();
            stringArray[0] = Integer.toString(n2);
            stringArray[1] = string;
            return stringArray;
        }
        catch (Exception exception) {
            stringArray[0] = "-1";
            stringArray[1] = "parseQueryResponse() ERR:" + exception.toString();
            return stringArray;
        }
    }

    private static String i(String string) {
        String string2 = "4vmgYafLZd";
        if (string == null || string.equals("") || string2.equals("")) {
            return null;
        }
        byte[] byArray = new byte[string.length()];
        String string3 = "";
        String string4 = null;
        int n2 = 0;
        for (int i2 = 0; i2 < string.length(); ++i2) {
            byte by = 0;
            by = (byte)string.charAt(i2);
            if (n2 == string2.length()) {
                n2 = 0;
            }
            byte by2 = 0;
            by2 = (byte)string2.charAt(n2);
            byArray[i2] = (byte)(by ^ by2);
            string4 = Integer.toHexString(byArray[i2]);
            for (by2 = (byte)string4.length(); by2 < 2; by2 = (byte)(by2 + 1)) {
                string4 = "0" + string4;
            }
            string3 = string3 + string4;
            ++n2;
        }
        return string3;
    }

    static {
        g.a.b();
        boolean bl = false;
        RecordStore recordStore = null;
        try {
            g.a.I();
            g.a.K();
        }
        catch (Exception exception) {
            bl = true;
        }
        if (bl) {
            byte[] byArray = new byte[1024];
            try {
                RecordStore.deleteRecordStore((String)"gil_api_private");
            }
            catch (Exception exception) {}
            try {
                recordStore = RecordStore.openRecordStore((String)"gil_api_private", (boolean)true);
                recordStore.addRecord(byArray, 0, 200);
                recordStore.addRecord(byArray, 0, 100);
                recordStore.addRecord(byArray, 0, 700);
            }
            catch (Exception exception) {
                s = t;
            }
            g.a.a(recordStore);
            try {
                RecordStore.deleteRecordStore((String)("wappay_gil_api" + g.a.a("rmsName", w)));
            }
            catch (Exception exception) {}
            try {
                recordStore = RecordStore.openRecordStore((String)("wappay_gil_api" + g.a.a("rmsName", w)), (boolean)true, (int)1, (boolean)false);
                recordStore.addRecord(byArray, 0, 1);
                recordStore.addRecord(byArray, 0, 100);
            }
            catch (Exception exception) {
                r = false;
            }
            g.a.a(recordStore);
            if (s == 0) {
                q = System.currentTimeMillis();
                g.a.H();
                g.a.J();
            }
        }
        b = "";
        Z = "";
        c = "";
        as = new String[3];
        at = "";
        au = 0;
        av = "";
        aw = "";
        aF = "";
        aH = "POST";
        aI = true;
        aJ = "";
        aK = "";
        aL = "";
        aM = "";
        aN = "";
        aO = "";
        aP = "";
        aQ = "";
        aS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        aT = true;
        aU = false;
    }
}

