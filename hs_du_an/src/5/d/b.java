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
 *  javax.wireless.messaging.Message
 *  javax.wireless.messaging.MessageConnection
 *  javax.wireless.messaging.TextMessage
 */
package d;

import h.a;
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
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

public final class b
extends Form
implements Runnable,
CommandListener,
ItemStateListener {
    public static byte a = 0;
    private static String f = "";
    private static Hashtable g = new Hashtable();
    private Hashtable h = new Hashtable();
    private Hashtable i = new Hashtable();
    private static Hashtable j = new Hashtable();
    private static String k = "";
    private static String l = "";
    private static String m = "";
    private static String n = "";
    private static boolean o = false;
    private static int p = 0;
    private static String q = "";
    private static String r = "";
    private static long s = 0L;
    public static b b = null;
    private static boolean t = true;
    private static byte u = 0;
    private static byte v = 1;
    private static byte w = (byte)2;
    private static int[] x = null;
    private static Hashtable y = new Hashtable();
    private static Vector z = null;
    private static Vector A = null;
    private static String[] B = null;
    private boolean C = false;
    private boolean D = false;
    private int E = -1;
    private int F = -1;
    private int G = -1;
    private int H = 0;
    private boolean I = true;
    private StringItem J = null;
    private StringItem K = null;
    private Command L = null;
    private Command M = null;
    private Command N = null;
    private Command O = null;
    private Command P = null;
    private TextField Q = null;
    private TextField R = null;
    private TextField S = null;
    private TextField T = null;
    private ChoiceGroup U = null;
    private ChoiceGroup V = null;
    private ChoiceGroup W = null;
    private TextField X = null;
    private String Y = "";
    private String Z = "";
    private String aa = "";
    public static String c;
    private static String ab;
    public static String d;
    private int ac = 1;
    private String ad = "";
    private String ae = "";
    private String af = "";
    private int ag = 0;
    private int ah = 0;
    private String ai = "";
    private String aj = "";
    private String ak = "";
    private int al;
    private int am = 0;
    private int an = 0;
    private int ao = 0;
    private int ap = 0;
    private int[] aq = null;
    private byte[] ar;
    private String as = "";
    private MIDlet at;
    private static String[] au;
    private static String av;
    private static int aw;
    private static String ax;
    private static String ay;
    private String az = "1";
    private String aA = "0";
    private int aB;
    private String aC = "";
    private String[] aD = null;
    private String[] aE = null;
    private String aF;
    private String aG;
    private static String aH;
    private String[] aI;
    public static String e;
    private static boolean aJ;
    private static String aK;
    private static String aL;
    private static String aM;
    private static String aN;
    private static String aO;
    private static String aP;
    private static String aQ;
    private static String aR;
    private boolean aS = false;
    private static char[] aT;
    private static boolean aU;
    private static boolean aV;

    private b(MIDlet mIDlet) {
        super("");
        this.at = mIDlet;
        this.E();
        this.B();
    }

    private void b() {
        this.I = true;
        new Thread(this).start();
    }

    private void c() {
        this.C();
        this.t();
        this.append((Item)this.J);
        this.append((Item)this.K);
    }

    private void d() {
        this.E = 1;
        this.C();
        this.t();
        this.ac = 2;
        if (k != null && !"".equals(k)) {
            if (!ay.equals("noid") && !ax.equals("")) {
                this.J.setText(this.Y + "\n " + ax + ":" + " " + k + "\n " + d.b.a(5, 1) + ":" + " " + l);
            } else {
                this.J.setText(this.Y);
            }
        } else {
            this.J.setText(this.Y);
        }
        this.append((Item)this.J);
        if (!ay.equals("noid")) {
            this.append((Item)this.U);
        }
        this.append("\nVer 1.5.8");
        this.addCommand(this.L);
        this.addCommand(this.M);
    }

    private void e() {
        this.E = 3;
        this.C();
        this.t();
        this.J.setText(d.b.a(4, 0) + "\n" + d.b.a("ContactUs", y) + "\n");
        this.append((Item)this.J);
        this.append((Item)this.K);
        this.addCommand(this.M);
    }

    private void f() {
        this.E = 2;
        this.C();
        this.t();
        this.append((Item)this.J);
        this.addCommand(this.L);
    }

    private void g() {
        if (ay.equals("noid")) {
            this.e();
            this.J.setText(d.b.a(4, 0) + "-101\n" + d.b.a("ContactUs", y) + "\n");
            return;
        }
        this.E = 8;
        this.C();
        this.t();
        this.J.setText(d.b.a(9, 1));
        this.append((Item)this.J);
        this.append((Item)this.Q);
        this.append((Item)this.R);
        this.addCommand(this.L);
        this.addCommand(this.P);
        this.addCommand(this.N);
    }

    private void h() {
        k = this.Q.getString();
        l = this.R.getString();
        if (k == null || "".equals(k)) {
            this.g();
            return;
        }
        if (this.G == 10) {
            if (m == null || "".equals(m)) {
                if (ay.equals("noid") || !aJ) {
                    this.l();
                    return;
                }
                this.F = 10;
                this.aS = false;
                this.k();
                return;
            }
            this.l();
            return;
        }
        if (m == null || "".equals(m)) {
            this.F = 6;
            this.aS = false;
            this.k();
            return;
        }
        q = "";
        this.E = 6;
        this.c();
    }

    private void i() {
        this.E = 5;
        this.C();
        this.t();
        this.J.setText(d.b.a(8, 1));
        this.append((Item)this.J);
        if (k == null || "".equals(k)) {
            this.append((Item)this.V);
            this.V.setSelectedIndex(1, true);
        }
        this.append((Item)this.Q);
        this.append((Item)this.R);
        this.addCommand(this.L);
        this.addCommand(this.M);
    }

    private void j() {
        k = "";
        l = "";
        this.Q.setString(k);
        this.R.setString(l);
        q = "";
        if (this.G == 10) {
            if (m == null || "".equals(m)) {
                this.F = 10;
                this.aS = true;
                this.k();
                return;
            }
            this.l();
            return;
        }
        if (m == null || "".equals(m)) {
            this.F = 9;
            this.aS = true;
            this.k();
            return;
        }
        this.E = 9;
        this.c();
    }

    private void k() {
        if (ay.equals("noid")) {
            this.l();
            return;
        }
        this.E = 22;
        this.c();
    }

    private void l() {
        this.E = 10;
        this.c();
    }

    private void m() {
        this.e();
        this.J.setText(d.b.a(13, 0));
        this.K.setText("");
    }

    private void n() {
        this.E = 15;
        this.C();
        this.t();
        this.append((Item)this.J);
        this.addCommand(this.L);
        this.addCommand(this.M);
    }

    private void o() {
        this.E = 16;
        this.C();
        this.t();
        this.append(d.b.a("msg", this.i));
        this.append((Item)this.W);
        this.J.setText(d.b.a("all_desc", this.i));
        this.append((Item)this.J);
        this.addCommand(this.L);
        this.addCommand(this.M);
    }

    private void p() {
        this.E = 19;
        this.q();
        this.append((Item)this.Q);
        this.append((Item)this.R);
        this.append(ax.equals("") ? "" : ax + d.b.a(59, 1));
    }

    private void q() {
        this.C();
        this.t();
        this.S.setLabel(d.b.a(10, 1));
        this.S.setMaxSize(21);
        this.T.setLabel(d.b.a(11, 1));
        this.T.setMaxSize(21);
        if (this.ac == 3) {
            this.J.setText(d.b.a(d.b.a(12, 1), "$(str)", this.ah + ""));
            this.append((Item)this.J);
        } else if (this.ac == 4) {
            this.J.setText(d.b.a("msg", this.i));
            this.append((Item)this.J);
            this.append((Item)this.X);
        }
        this.append((Item)this.S);
        this.append((Item)this.T);
        this.addCommand(this.L);
        this.addCommand(this.M);
        this.X.setString("30");
        this.S.setString("");
        this.T.setString("");
    }

    private void r() {
        this.G = 10;
        this.ac = 2;
        d.b.a(this.at, this.Z);
        this.Q.setString(k);
        this.R.setString(l);
        if (k == null || "".equals(k)) {
            if (ay.equals("noid") || !aJ) {
                this.l();
                return;
            }
            this.aS = false;
            this.k();
            return;
        }
        this.h();
    }

    private void s() {
        this.G = 17;
        d.b.a(this.at, this.Z);
        if (k == null || "".equals(k)) {
            v0.E = 18;
            this.q();
            this.append((Item)this.V);
            this.append(ax.equals("") ? "" : ax + d.b.a(59, 1));
            return;
        }
        this.Q.setString(k);
        this.R.setString(l);
        this.p();
    }

    private void t() {
        this.removeCommand(this.L);
        this.removeCommand(this.M);
        this.removeCommand(this.N);
        this.removeCommand(this.O);
        this.removeCommand(this.P);
    }

    public final void run() {
        try {
            while (this.I && this.I) {
                Object object;
                block5 : switch (this.E) {
                    case 27: {
                        Object object2;
                        object = this;
                        int n2 = 0;
                        if (d.b.b(((b)object).aI[4], ((b)object).aI[5])) {
                            object2 = object;
                            ((b)object).E = 28;
                            ((b)object2).C();
                            ((b)object2).t();
                            ((b)object2).J.setText(d.b.a(62, 1));
                            object2.append((Item)((b)object2).J);
                            object2.addCommand(((b)object2).L);
                            break;
                        }
                        object2 = object;
                        ((b)object).E = 29;
                        ((b)object2).C();
                        ((b)object2).t();
                        ((b)object2).J.setText(d.b.a(7, 0));
                        object2.append((Item)((b)object2).J);
                        object2.addCommand(((b)object2).L);
                        object2.addCommand(((b)object2).M);
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
                            ((b)object2).b(d.b.a(54, 1) + ax);
                            n3 = o;
                            String string = c;
                            object5 = ((b)object2).aa;
                            object4 = ((b)object2).Z;
                            object3 = m;
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
                            hashtable.put("api.url", f + "gs/reg.do");
                            hashtable.put("m", object3);
                            hashtable.put("c", object4);
                            hashtable.put("rop", object5);
                            hashtable.put("cp", string);
                            object5 = d.b.a(hashtable, n3 != 0, e);
                            if (d.b.h((String)object5) < 0) {
                                super.b(d.b.a(55, 1) + ": " + d.b.h((String)object5));
                                bl = false;
                            } else {
                                k = d.b.b((String)object5, 2);
                                l = d.b.b((String)object5, 3);
                                ((b)object2).Q.setString(k);
                                ((b)object2).R.setString(l);
                                bl = true;
                            }
                            if (bl) {
                                super.b(d.b.a(27, 1) + "\n" + ax + ":" + k + "\n" + d.b.a(5, 1) + ":" + l);
                                super.h();
                                break block5;
                            }
                            o = !o;
                        }
                        ((b)object).b(d.b.a(28, 1));
                        ((b)object).e();
                        ((b)object).J.setText(d.b.a("ContactUs", y) + "\n");
                        break;
                    }
                    case 6: {
                        this.x();
                        break;
                    }
                    case 10: {
                        this.y();
                        break;
                    }
                    case 17: {
                        this.z();
                        break;
                    }
                    case 13: {
                        Object object5;
                        Object object2;
                        object = this;
                        int n2 = 0;
                        if (d.b.b(((b)object).ai, ((b)object).aj + (((b)object).an + 1))) {
                            ++((b)object).ao;
                            ++((b)object).ap;
                            ((b)object).H += ((b)object).am;
                            d.b.a(((b)object).az, ((b)object).H);
                            ++((b)object).an;
                            if (((b)object).H < ((b)object).aB) {
                                object2 = d.b.a("msg2", ((b)object).i);
                                if (((String)object2).equals("")) {
                                    object2 = d.b.a(8, 0) + "\uff0c" + d.b.a(10, 0);
                                }
                                object5 = d.b.a(d.b.a(d.b.a((String)object2, "$(str3)", ((b)object).ak), "$(str2)", ((b)object).al - ((b)object).an + ""), "$(str1)", ((b)object).an + "") + "\n";
                                if (k != null && !"".equals(k) && !ay.equals("noid")) {
                                    object5 = (String)object5 + ax + ":" + " " + k + "\n" + d.b.a(5, 1) + ":" + " " + l + "\n";
                                }
                                ((b)object).J.setText((String)object5);
                                super.n();
                            } else {
                                p += ((b)object).H;
                                d.b.H();
                                ((b)object).h.remove(((b)object).az);
                                object2 = d.b.b(((b)object).aB);
                                if (!av.equals("") && !ay.equals("noid")) {
                                    try {
                                        aw = Integer.parseInt(d.b.a("coinExc", y));
                                        int n4 = ((b)object).aB * 100 / aw;
                                        object2 = n4 + av + "(" + (String)object2 + ")";
                                    }
                                    catch (Exception exception) {
                                        u = w;
                                        break;
                                    }
                                }
                                object5 = d.b.a(19, 0) + " " + (String)object2 + "\n";
                                if (!d.b.a("msg4", ((b)object).i).equals("")) {
                                    object5 = d.b.a("msg4", ((b)object).i) + "\n";
                                }
                                if (!ay.equals("noid")) {
                                    if (((b)object).H - ((b)object).aB > 0 && k != null && !"".equals(k)) {
                                        object5 = d.b.a((String)object5 + d.b.a(d.b.a(50, 1), "$(str)", ax + " " + k + " ") + "\n", "$(str1)", av);
                                    }
                                    object5 = (String)object5 + d.b.a("lidmsg", ((b)object).i);
                                }
                                ((b)object).J.setText((String)object5);
                                super.f();
                            }
                            String string = ((b)object).az;
                            try {
                                object2 = new ByteArrayOutputStream();
                                object5 = new DataOutputStream((OutputStream)object2);
                                ((DataOutputStream)object5).writeUTF(((b)object).ai);
                                ((DataOutputStream)object5).writeUTF(((b)object).aj);
                                ((DataOutputStream)object5).writeInt(((b)object).am);
                                ((DataOutputStream)object5).writeInt(((b)object).an);
                                ((DataOutputStream)object5).writeInt(((b)object).al);
                                ((DataOutputStream)object5).writeUTF(((b)object).ak);
                                ((b)object).h.put(string, ((ByteArrayOutputStream)object2).toByteArray());
                                ((FilterOutputStream)object5).close();
                                ((ByteArrayOutputStream)object2).close();
                                super.D();
                            }
                            catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;
                        }
                        ((b)object).J.setText(d.b.a(7, 0));
                        ((b)object).n();
                        break;
                    }
                    case 22: {
                        this.u();
                        break;
                    }
                    case 21: {
                        Object object4;
                        object = this;
                        if (((b)object).Z == null || ((b)object).Z.equals("") || ((b)object).as == null || ((b)object).as.equals("")) {
                            ((b)object).e();
                            ((b)object).J.setText(d.b.a(51, 1) + "\n" + d.b.a("ContactUs", y) + "\n");
                            break;
                        }
                        Hashtable<String, String> hashtable = new Hashtable<String, String>();
                        hashtable.put("Content-Type", "application/x-www-form-urlencoded");
                        Object object2 = "c=" + ((b)object).Z + "&r=" + ((b)object).as;
                        Object object5 = new a(f + "gs/down.do", e, (String)object2, hashtable);
                        new Hashtable();
                        Object object3 = null;
                        try {
                            object3 = h.a.a((a)object5, o, "");
                            object4 = ((Hashtable)object3).get("contType").toString();
                            if (object4 != null && ((String)object4).indexOf("audio/midi") < 0) {
                                object3 = h.a.a((a)object5, o, "");
                            }
                        }
                        catch (SecurityException securityException) {
                            super.b(d.b.a(40, 1));
                            super.e();
                            ((b)object).J.setText(d.b.a(51, 1) + "\n" + d.b.a("ContactUs", y) + "\n");
                            break;
                        }
                        catch (Exception exception) {
                            if (((b)object).ag < 1) {
                                ++((b)object).ag;
                                o = !o;
                                break;
                            }
                            super.e();
                            ((b)object).J.setText(d.b.a(51, 1) + "\n" + d.b.a("ContactUs", y) + "\n");
                            break;
                        }
                        byte[] byArray = (byte[])((Hashtable)object3).get("data");
                        object4 = byArray;
                        if (byArray == null) {
                            super.e();
                            ((b)object).J.setText(d.b.a(51, 1) + "\n" + d.b.a("ContactUs", y) + "\n");
                            ((b)object).ar = null;
                            break;
                        }
                        if (((Object)object4).length < 4 || object4[0] != false || object4[1] != false || object4[2] != false || object4[3] != false) {
                            super.e();
                            ((b)object).J.setText(d.b.a(51, 1) + "\n" + d.b.a("ContactUs", y) + "\n");
                            ((b)object).ar = null;
                            break;
                        }
                        ((b)object).ar = new byte[((Object)object4).length - 4];
                        System.arraycopy(object4, 4, ((b)object).ar, 0, ((Object)object4).length - 4);
                        ((b)object).I = false;
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
                        stringBuffer.append("&c=" + ((b)object).Z);
                        stringBuffer.append("&cp=" + c);
                        stringBuffer.append("&event=1");
                        stringBuffer.append("&score=" + ((b)object).aF);
                        stringBuffer.append("&msg=" + ((b)object).aG);
                        stringBuffer.append("&uid=" + r);
                        Object object2 = new Hashtable<String, String>();
                        ((Hashtable)object2).put("Content-Type", "application/x-www-form-urlencoded");
                        try {
                            object5 = new a(f + "passport/achieve.do", e, stringBuffer.toString(), (Hashtable)object2);
                            new Hashtable();
                            object3 = null;
                            object3 = h.a.a((a)object5, o, "");
                            object4 = new String((byte[])((Hashtable)object3).get("data"), "UTF-8");
                            object5 = "";
                            if (((String)object4).indexOf("code") > 0) {
                                n3 = ((String)object4).indexOf(":\"") + 2;
                                int n5 = ((String)object4).indexOf("\"", n3);
                                object5 = ((String)object4).substring(n3, n5);
                            } else {
                                super.e();
                                ((b)object).J.setText(d.b.a(d.b.a(52, 1), "$(str)", d.b.a("sentName", y)) + "\n" + d.b.a("ContactUs", y) + "\n");
                            }
                            if ("1".equals(object5) || "-56".equals(object5)) {
                                super.f();
                                ((b)object).J.setText(d.b.a(d.b.a(53, 1), "$(str)", d.b.a("sentName", y)));
                                break;
                            }
                            super.e();
                            ((b)object).J.setText(d.b.a(d.b.a(52, 1), "$(str)", d.b.a("sentName", y)) + "\n" + d.b.a("ContactUs", y) + "\n");
                            break;
                        }
                        catch (Exception exception) {
                            super.e();
                            ((b)object).J.setText(d.b.a(d.b.a(52, 1), "$(str)", d.b.a("sentName", y)) + "\n" + d.b.a("ContactUs", y) + "\n");
                            exception.printStackTrace();
                        }
                    }
                }
                this.J.getText();
                object = null;
                ax.equals("");
                if (u == 1) {
                    object = d.b.a(14, 0) + d.b.a(15, 0) + d.b.a(16, 0) + "\n" + d.b.a("ContactUs", y) + "\n";
                    if (!ax.equals("")) {
                        object = (String)object + ax + ":" + " " + k + "\n" + d.b.a(5, 1) + ":" + " " + l + "\n";
                    }
                    this.e();
                    this.J.setText((String)object);
                } else if (u == 2) {
                    object = d.b.a(17, 0) + d.b.a(15, 0) + "\n" + d.b.a("ContactUs", y) + "\n";
                    if (!ax.equals("")) {
                        object = (String)object + ax + ":" + " " + k + "\n" + d.b.a(5, 1) + ":" + " " + l + "\n";
                    }
                    this.e();
                    this.J.setText((String)object);
                }
                d.b.a(200L);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        this.aI = null;
        this.C = true;
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

    private void b(String string) {
        if (string != null && !string.equals("")) {
            string = "--" + string + "\n" + this.K.getText();
            this.K.setText(string);
        }
    }

    private void u() {
        if (!aJ) {
            if (this.F == 6) {
                q = "";
                this.E = this.F;
                this.c();
                return;
            }
            if (this.F == 9) {
                this.E = 9;
                this.c();
                return;
            }
        }
        String string = k;
        int n2 = this.v();
        if (n2 == -902) {
            n2 = this.v();
        }
        if (n2 < -100 && n2 > -110) {
            o = !o;
            n2 = this.v();
        }
        k = string;
        if (n2 == 0) {
            this.b(d.b.a(21, 1));
            string = null;
            for (int i2 = 0; i2 < this.aD.length; ++i2) {
                try {
                    string = RecordStore.openRecordStore((String)("wappay_gil_api" + d.b.a("rmsName", y)), (String)this.aE[i2], (String)this.aD[i2]);
                    Object object = string;
                    object = new ByteArrayInputStream(object.getRecord(2));
                    DataInputStream dataInputStream = new DataInputStream((InputStream)object);
                    k = dataInputStream.readUTF();
                    l = dataInputStream.readUTF();
                    o = dataInputStream.readBoolean();
                    dataInputStream.readBoolean();
                    m = dataInputStream.readUTF();
                    dataInputStream.readInt();
                    n = dataInputStream.readUTF();
                    dataInputStream.close();
                    ((ByteArrayInputStream)object).close();
                }
                catch (Exception exception) {}
                d.b.a((RecordStore)string);
                if (k == null || "".equals(k)) continue;
                d.b.H();
                break;
            }
            if ((m == null || "".equals(m)) && null != this.aC && !"".equals(this.aC)) {
                this.b(d.b.a(22, 1));
                if (this.w()) {
                    d.b.H();
                }
            }
        } else if (n2 == 1 && null != this.aC && !"".equals(this.aC)) {
            this.b(d.b.a(23, 1));
            if (this.w()) {
                d.b.H();
            }
        }
        if (n2 == -901) {
            if (this.F == 6) {
                this.e();
                this.J.setText(d.b.a(24, 1) + "\n" + d.b.a("ContactUs", y) + "\n");
                return;
            }
            this.m();
            return;
        }
        if (n2 == -7 || n2 == -9 || n2 == -12 || n2 == -13 || n2 == -14 || n2 == -15 || n2 == -16 || n2 == -17 || n2 == -18 || n2 == -19 || n2 == -20) {
            this.g();
            return;
        }
        if (n2 < 0) {
            this.e();
            this.J.setText(d.b.a(25, 1) + ":" + n2 + "\n" + d.b.a("ContactUs", y) + "\n");
            return;
        }
        if (this.F == 6) {
            q = "";
            this.E = this.F;
            this.c();
            return;
        }
        if (this.F == 9) {
            this.E = 9;
            this.c();
            return;
        }
        this.l();
    }

    private int v() {
        int n2;
        this.b(d.b.a(30, 1) + "\uff0c" + d.b.a(31, 1) + ":" + (o ? d.b.a(32, 1) : d.b.a(33, 1)));
        boolean bl = false;
        Object object = new StringBuffer();
        ((StringBuffer)object).append("gc=" + this.Z);
        ((StringBuffer)object).append("&mobile=" + m);
        ((StringBuffer)object).append("&lid=" + k);
        ((StringBuffer)object).append("&pwd=" + l);
        ((StringBuffer)object).append("&reg=" + this.aS);
        ((StringBuffer)object).append("&ua=" + d);
        ((StringBuffer)object).append("&ua2=" + n);
        ((StringBuffer)object).append("&locale=" + au[0]);
        Object object2 = new Hashtable();
        ((Hashtable)object2).put("Content-Type", "application/x-www-form-urlencoded");
        try {
            object = new a(f + "gs/getliveinfo.do", e, ((StringBuffer)object).toString(), (Hashtable)object2);
            new Hashtable();
            object2 = h.a.a((a)object, o, "");
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
                this.aC = dataInputStream.readUTF();
                this.aD = new String[vector.size()];
                this.aE = new String[vector2.size()];
                vector2.copyInto(this.aE);
                vector.copyInto(this.aD);
            } else if (n2 == 1) {
                this.aC = dataInputStream.readUTF();
            } else if (n2 == 2) {
                m = dataInputStream.readUTF();
                n = dataInputStream.readUTF();
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

    private boolean w() {
        Object object = new Hashtable<String, String>();
        ((Hashtable)object).put("Content-Type", "application/x-www-form-urlencoded");
        try {
            Object object2 = new a(((b)object2).aC, e, null, (Hashtable)object);
            new Hashtable();
            object2 = (byte[])h.a.a((a)object2, o, "").get("data");
            object2 = new ByteArrayInputStream((byte[])object2);
            object = new DataInputStream((InputStream)object2);
            if (((DataInputStream)object).readInt() >= 0) {
                m = ((DataInputStream)object).readUTF();
                n = ((DataInputStream)object).readUTF();
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

    private void x() {
        String string;
        if (q == null || "".equals(q)) {
            int n2 = 0;
            while (true) {
                int n3;
                String string2;
                this.b(d.b.a(26, 1));
                Object object = this;
                boolean bl = o;
                String string3 = d;
                String string4 = c;
                String string5 = ((b)object).Z;
                String string6 = l;
                string = k;
                if (string == null || string6 == null || string5 == null || string5.equals("") || string4 == null) {
                    string2 = "-1," + d.b.a(29, 1);
                } else {
                    if (string3 == null) {
                        string3 = "";
                    }
                    object = new Hashtable<String, String>();
                    ((Hashtable)object).put("api.url", f + "gs/login.do");
                    ((Hashtable)object).put("u", string);
                    ((Hashtable)object).put("p", string6);
                    ((Hashtable)object).put("c", string5);
                    ((Hashtable)object).put("cp", string4);
                    ((Hashtable)object).put("ua", string3);
                    string2 = d.b.a((Hashtable)object, bl, e);
                }
                string = string2;
                int n4 = d.b.h(string2);
                if (n4 < 0) {
                    q = "";
                    n3 = n4;
                } else {
                    string6 = d.b.b(string, 2);
                    if (string6 == null) {
                        q = "";
                        n3 = -1;
                    } else {
                        q = string6;
                        r = d.b.b(string, 3);
                        d.b.H();
                        n3 = n4 = n4;
                    }
                }
                if (n3 == -100 || n4 == -106) {
                    if (n2 >= 1) {
                        this.e();
                        this.J.setText(d.b.a(24, 1) + n4 + "\n" + d.b.a("ContactUs", y) + "\n");
                        return;
                    }
                } else {
                    if (n4 >= 0) break;
                    this.g();
                    return;
                }
                o = !o;
                ++n2;
            }
        }
        switch (this.G) {
            case 17: {
                b b2 = this;
                if (b2.ac == 4) {
                    b2.ah = 0;
                    try {
                        b2.ah = Integer.parseInt(b2.X.getString());
                    }
                    catch (Exception exception) {}
                    if (b2.ah <= 0) {
                        b2.J.setText(d.b.a(13, 1));
                        b2.s();
                        return;
                    }
                }
                b2.E = 17;
                b2.C();
                b2.t();
                string = d.b.a(14, 1) + "\n";
                if (b2.ac == 3) {
                    string = string + d.b.a(15, 1) + "\n" + "" + "\n" + d.b.a(16, 1) + "\n" + d.b.a(17, 1);
                }
                b2.J.setText(string);
                b2.append((Item)b2.J);
                b2.append((Item)b2.K);
                return;
            }
            case 14: {
                b b3 = this;
                this.E = 14;
                b3.C();
                b3.t();
                b3.K.setText("");
                b3.append((Item)b3.K);
                return;
            }
            case 2: {
                this.D = true;
                this.I = false;
                return;
            }
            case 24: {
                b b4 = this;
                this.E = 24;
                b4.C();
                b4.t();
                b4.J.setText(d.b.a(d.b.a(20, 1), "$(str)", d.b.a("sentName", y)));
                b4.append((Item)b4.J);
            }
        }
    }

    private int a(int n2) {
        d.b.a(this.az, this.H);
        Object object = new StringBuffer();
        ((StringBuffer)object).append("id=" + d.b.a("id", y));
        ((StringBuffer)object).append("&gc=" + this.Z);
        ((StringBuffer)object).append("&gv=" + ab);
        ((StringBuffer)object).append("&ua=" + d);
        ((StringBuffer)object).append("&ua2=" + n);
        ((StringBuffer)object).append("&cp=" + c);
        ((StringBuffer)object).append("&rop=" + this.aa);
        ((StringBuffer)object).append("&pv=1.5.8");
        ((StringBuffer)object).append("&op=" + this.az);
        ((StringBuffer)object).append("&opd=" + this.aA);
        ((StringBuffer)object).append("&lb=" + this.aB);
        ((StringBuffer)object).append("&ct=" + n2);
        ((StringBuffer)object).append("&lid=" + k);
        ((StringBuffer)object).append("&pwd=" + l);
        ((StringBuffer)object).append("&key=" + q);
        ((StringBuffer)object).append("&mobile=" + m);
        ((StringBuffer)object).append("&sms=" + this.ao);
        ((StringBuffer)object).append("&sid=" + s);
        ((StringBuffer)object).append("&lastct=" + this.ad);
        ((StringBuffer)object).append("&locale=" + au[0]);
        if (e.equals("POST")) {
            ((StringBuffer)object).append("&platform=" + aM);
            ((StringBuffer)object).append("&profiles=" + aN);
            ((StringBuffer)object).append("&configuration=" + aO);
            ((StringBuffer)object).append("&pimVer=" + aP);
            ((StringBuffer)object).append("&smsc=" + aQ);
            ((StringBuffer)object).append("&mmsc=" + aR);
        }
        if (aK != null && !aK.equals("")) {
            ((StringBuffer)object).append("&sms_type=" + aK);
            ((StringBuffer)object).append("&apid=" + aL);
        }
        Object object2 = new Hashtable<String, String>();
        ((Hashtable)object2).put("Content-Type", "application/x-www-form-urlencoded");
        object2 = new a(f + "gs/getinfo.do", e, ((StringBuffer)object).toString(), (Hashtable)object2);
        try {
            this.b(d.b.a(11, 0));
            object = h.a.a((a)object2, o, "");
            object2 = ((Hashtable)object).get("contType").toString();
            if (object2 != null && ((String)object2).indexOf("audio/midi") < 0) {
                return -902;
            }
        }
        catch (SecurityException securityException) {
            this.b(d.b.a(40, 1));
            return -901;
        }
        catch (Exception exception) {
            this.b(d.b.a(39, 1) + ": " + d.b.a(38, 1));
            return -106;
        }
        if (object == null) {
            this.b(d.b.a(39, 1) + ": -4 " + d.b.a(37, 1));
            return -4;
        }
        int n3 = Integer.parseInt(((Hashtable)object).get("respCode").toString());
        if (n3 >= 200 && n3 <= 299 && ((Hashtable)object).get("data") != null) {
            this.ao = 0;
            this.D();
            Object object3 = (byte[])((Hashtable)object).get("data");
            object3 = new ByteArrayInputStream((byte[])object3);
            object = new DataInputStream((InputStream)object3);
            try {
                String string;
                this.b(d.b.a(60, 1));
                int n4 = ((DataInputStream)object).readInt();
                while ((string = ((DataInputStream)object).readUTF()) != null && !"".equals(string)) {
                    String string2 = ((DataInputStream)object).readUTF();
                    this.i.put(string, string2);
                }
                ((FilterInputStream)object).close();
                ((ByteArrayInputStream)object3).close();
                this.b(d.b.a(36, 1));
                return n4;
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
                this.b(d.b.a(39, 1) + ": -6 " + d.b.a(35, 1) + ":" + exception2);
                return -6;
            }
        }
        this.b(d.b.a(39, 1) + ": -5 " + d.b.a(34, 1));
        this.b("respCode==" + n3);
        return -5;
    }

    private void y() {
        int n2 = this.a(this.ac);
        if (n2 == -902) {
            n2 = this.a(this.ac);
        }
        if (n2 == -106) {
            o = !o;
            n2 = this.a(this.ac);
        }
        String string = d.b.a("lid", this.i);
        Object object = d.b.a("pwd", this.i);
        String string2 = d.b.a("key", this.i);
        if (string != null && !"".equals(string)) {
            k = string;
            l = object;
            d.b.H();
            if (!ay.equals("noid")) {
                this.J.setText(ax + ":" + " " + k + "\n" + d.b.a(5, 1) + ":" + " " + l + "\n");
            }
        }
        q = string2;
        this.ad = "";
        this.ac = n2;
        switch (n2) {
            case 0: {
                if (Integer.parseInt((String)this.i.get("status")) >= 0) {
                    n2 = Integer.parseInt(d.b.a("consume", this.i));
                    string = d.b.a("balance", this.i);
                    p += n2;
                    s = System.currentTimeMillis();
                    d.b.H();
                    d.b.a(this.az, this.aB);
                    object = d.b.a(5, 0) + "\n";
                    if (!ay.equals("noid")) {
                        object = (String)object + ax + ":" + " " + k + "\n" + d.b.a(5, 1) + ":" + " " + l + "\n";
                        object = !av.equals("") ? (String)object + d.b.a(41, 1) + ":" + n2 + av + "\n" + d.b.a(42, 1) + ":" + string + av + "\n" : (String)object + d.b.a(41, 1) + ":" + d.b.b(n2) + "\n" + d.b.a(42, 1) + ":" + d.b.b(Integer.parseInt(string)) + "\n";
                        object = (String)object + d.b.a("lidmsg", this.i);
                    }
                    this.J.setText((String)object);
                    this.f();
                    return;
                }
                this.e();
                this.J.setText(d.b.a("msg", this.i) + "\n" + d.b.a("ContactUs", y) + "\n");
                return;
            }
            case 2: {
                this.ak = d.b.a("price", this.i);
                this.am = Integer.parseInt(d.b.a("lb", this.i));
                this.al = Integer.parseInt(d.b.a("times", this.i));
                this.ai = d.b.a("shortcode", this.i);
                this.aj = d.b.a("content", this.i);
                this.A();
                return;
            }
            case 4: {
                if (ay.equals("noid")) {
                    this.e();
                    this.J.setText(d.b.a(4, 0) + "\u3002" + n2 + "\n" + d.b.a("ContactUs", y) + "\n");
                    return;
                }
                this.ae = d.b.a("payurl", this.i);
                this.af = d.b.a("checkurl", this.i);
                this.J.setText("");
                this.S.setString("");
                this.T.setString("");
                this.s();
                return;
            }
            case 3: {
                if (ay.equals("noid")) {
                    this.e();
                    this.J.setText(d.b.a(4, 0) + "\u3002" + n2 + "\n" + d.b.a("ContactUs", y) + "\n");
                    return;
                }
                String[] stringArray = d.b.a("fee", this.i);
                string = d.b.a("fee_desc", this.i);
                object = d.b.c((String)stringArray, ",");
                stringArray = d.b.c(string, ",");
                this.ae = d.b.a("payurl", this.i);
                this.af = d.b.a("checkurl", this.i);
                this.aq = new int[((String[])object).length];
                for (int i2 = 0; i2 < ((String[])object).length; ++i2) {
                    this.aq[i2] = Integer.parseInt(object[i2]);
                }
                String[] stringArray2 = new String[stringArray.length + 1];
                System.arraycopy(stringArray, 0, stringArray2, 0, stringArray.length);
                stringArray2[stringArray.length] = d.b.a(2, 1);
                this.W = new ChoiceGroup(null, 1, stringArray2, null);
                this.S.setString("");
                this.T.setString("");
                this.o();
                return;
            }
            case 5: {
                d.b.H();
                d.b.a(this.az, this.aB);
                this.J.setText(d.b.a("msg", this.i));
                this.f();
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
                this.g();
                return;
            }
            case -901: {
                this.m();
                return;
            }
        }
        this.e();
        this.J.setText(d.b.a(4, 0) + "\u3002" + n2 + "\n" + d.b.a("ContactUs", y) + "\n");
    }

    private void z() {
        String string = ax + ":" + " " + k + "\n";
        string = string + d.b.a(5, 1) + ":" + " " + l + "\n";
        try {
            this.b(d.b.a(43, 1));
            Object object = d.b.a(q, this.Z, ab, c, "" + this.ah, this.S.getString(), this.T.getString(), this.ae, o, e);
            int n2 = Integer.parseInt(object[0]);
            String[] stringArray = object;
            if (this.ac == 3) {
                if (n2 == -425 || n2 == -417 || n2 == -422 || n2 == -415) {
                    this.p();
                    this.insert(0, (Item)new StringItem("", d.b.a(44, 1)));
                    return;
                }
                if (n2 < 0) {
                    this.b(d.b.a(45, 1) + ": " + n2 + "\n" + d.b.a(46, 1) + d.b.a("ContactUs", y) + "\n");
                    this.e();
                    this.J.setText(string);
                    return;
                }
                this.b(d.b.a(47, 1));
                object = object[2];
                do {
                    d.b.a(1000L);
                    n2 = Integer.parseInt(d.b.b((String)object, this.af, o)[0]);
                    if (n2 != 0) continue;
                    this.b(d.b.a(48, 1));
                    this.ad = this.ac + "";
                    this.ac = 1;
                    this.aI = stringArray;
                    this.l();
                    return;
                } while (n2 == 2 || n2 == -423 || n2 == -419);
                if (n2 == -425 || n2 == -417 || n2 == -422 || n2 == -415) {
                    this.p();
                    this.insert(0, (Item)new StringItem("", d.b.a(44, 1)));
                    return;
                }
                this.b(d.b.a(45, 1) + ": " + n2 + "\n" + d.b.a(46, 1) + d.b.a("ContactUs", y) + "\n");
                this.e();
                this.J.setText(string);
                return;
            }
            if (this.ac == 4) {
                if (n2 >= 0) {
                    this.b(d.b.a(48, 1));
                    this.ad = this.ac + "";
                    this.ac = 1;
                    this.aI = stringArray;
                    this.l();
                    return;
                }
                if (n2 == -425 || n2 == -417 || n2 == -422 || n2 == -415) {
                    this.p();
                    this.insert(0, (Item)new StringItem("", d.b.a(44, 1)));
                    return;
                }
                this.b(d.b.a(45, 1) + ": " + n2 + "\n" + d.b.a(49, 1) + d.b.a("ContactUs", y) + "\n");
                this.e();
                this.J.setText(string);
            }
            return;
        }
        catch (Exception exception) {
            this.b(d.b.a(25, 1));
            this.e();
            return;
        }
    }

    private void A() {
        String string = null;
        if (this.H > 0 && this.H < this.aB) {
            string = d.b.a("msg2", this.i);
            if (string.endsWith("")) {
                string = d.b.a(8, 0) + "\uff0c" + d.b.a(10, 0);
            }
            string = d.b.a(d.b.a(d.b.a(string, "$(str3)", this.ak), "$(str2)", this.al - this.an + ""), "$(str1)", this.an + "") + "\n";
        } else {
            string = d.b.a("msg1", this.i);
            if (string.equals("")) {
                string = d.b.a(9, 0) + "\uff0c" + d.b.a(10, 0);
            }
            string = d.b.a(d.b.a(string, "$(str2)", this.ak), "$(str1)", this.al + "") + "\n";
        }
        if (k != null && !"".equals(k) && !ay.equals("noid")) {
            string = string + ax + ":" + " " + k + "\n" + d.b.a(5, 1) + ":" + " " + l + "\n";
        }
        this.J.setText(string);
        this.n();
    }

    public final void itemStateChanged(Item item) {
        int n2;
        block13: {
            if (item != this.V || this.E != 18 && this.E != 23) break block13;
            try {
                n2 = this.size();
                switch (this.V.getSelectedIndex()) {
                    case 0: {
                        if (n2 > 1 && this.get(n2 - 2) == this.R) {
                            this.delete(this.size() - 2);
                            this.delete(this.size() - 2);
                        }
                        break;
                    }
                    case 1: {
                        if (n2 > 1 && this.get(n2 - 2) != this.R) {
                            this.insert(this.size() - 1, (Item)this.Q);
                            this.insert(this.size() - 1, (Item)this.R);
                        } else {
                            break;
                        }
                    }
                }
            }
            catch (Exception exception) {}
        }
        if (item == this.V && this.E == 5) {
            n2 = this.size();
            switch (this.V.getSelectedIndex()) {
                case 0: {
                    if (n2 <= 0 || this.get(n2 - 1) != this.R) break;
                    this.delete(n2 - 1);
                    this.delete(n2 - 2);
                    return;
                }
                case 1: {
                    if (n2 <= 0 || this.get(n2 - 1) == this.R) break;
                    this.append((Item)this.Q);
                    this.append((Item)this.R);
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
        if (object == this.L) {
            this.J.setText("");
            switch (this.E) {
                case 26: 
                case 29: {
                    v0.E = 27;
                    this.J.setText(d.b.a(61, 1));
                    this.K.setText("");
                    this.c();
                    return;
                }
                case 28: {
                    this.I = false;
                    return;
                }
                case 4: {
                    return;
                }
                case 5: {
                    this.K.setText("");
                    q = "";
                    if (this.get(1) != this.V) {
                        this.h();
                        return;
                    }
                    switch (this.V.getSelectedIndex()) {
                        case 0: {
                            this.j();
                            return;
                        }
                        case 1: {
                            this.h();
                            return;
                        }
                    }
                    return;
                }
                case 2: {
                    this.D = true;
                    if (this.aI != null && this.aI[3].equals("false")) {
                        v1.E = 26;
                        this.C();
                        this.t();
                        this.J.setText(this.aI[6]);
                        this.append((Item)this.J);
                        this.addCommand(this.L);
                        this.addCommand(this.M);
                        return;
                    }
                    this.I = false;
                    return;
                }
                case 7: {
                    this.D = true;
                    this.C = true;
                    this.I = false;
                    return;
                }
                case 1: {
                    this.J.setText("");
                    this.K.setText("");
                    if (ay.equals("noid")) {
                        this.ac = 2;
                        if (B != null && ay.equals("noid")) {
                            this.am = Integer.parseInt(B[2]);
                            if (this.am == 0) {
                                this.am = this.aB;
                            }
                            this.ak = d.b.b(this.am);
                            this.al = this.aB / this.am;
                            this.ai = B[0];
                            this.aj = d.b.a(d.b.a(d.b.a(B[1], "{gc}", this.Z), "{cp}", c), "{op}", this.az);
                            this.A();
                            return;
                        }
                        this.r();
                        return;
                    }
                    this.ac = x[this.U.getSelectedIndex()];
                    switch (this.ac) {
                        case 1: {
                            this.G = 10;
                            this.i();
                            return;
                        }
                        case 2: {
                            this.r();
                            return;
                        }
                        case 3: 
                        case 4: {
                            this.F = this.E;
                            if ((m == null || "".equals(m)) && aJ) {
                                this.k();
                                return;
                            }
                            this.l();
                            return;
                        }
                    }
                    return;
                }
                case 16: {
                    int n2 = this.W.getSelectedIndex();
                    if (n2 == this.W.size() - 1) {
                        this.K.setText("");
                        this.r();
                        return;
                    }
                    this.ah = this.aq[n2];
                    this.s();
                    return;
                }
                case 18: {
                    this.K.setText("");
                    this.G = 17;
                    switch (this.V.getSelectedIndex()) {
                        case 0: {
                            this.j();
                            return;
                        }
                        case 1: {
                            this.h();
                            return;
                        }
                    }
                    return;
                }
                case 19: {
                    this.G = 17;
                    this.K.setText("");
                    this.h();
                    return;
                }
                case 12: {
                    switch (null.getSelectedIndex()) {
                        case 0: {
                            o = !o;
                            this.r();
                            return;
                        }
                        case 1: {
                            this.K.setText("");
                            this.ac = 2;
                            this.l();
                            return;
                        }
                        case 2: {
                            this.F = this.E;
                            this.ac = 3;
                            this.l();
                            return;
                        }
                    }
                    return;
                }
                case 15: {
                    void var1_5;
                    v2.E = 13;
                    this.C();
                    this.t();
                    String string = d.b.a("msg3", this.i);
                    if (string.equals("")) {
                        String string2 = d.b.a(12, 0);
                    }
                    this.J.setText(d.b.a(d.b.a((String)var1_5, "$(str2)", this.ak), "$(str1)", Integer.toString(this.an + 1)));
                    this.append((Item)this.J);
                    return;
                }
                case 20: {
                    this.D = false;
                    this.I = false;
                    return;
                }
                case 8: {
                    this.K.setText("");
                    this.h();
                    return;
                }
                case 23: {
                    this.G = 24;
                    switch (this.V.getSelectedIndex()) {
                        case 0: {
                            this.j();
                            return;
                        }
                        case 1: {
                            this.h();
                            return;
                        }
                    }
                    return;
                }
                case 25: {
                    this.G = 24;
                    this.h();
                    return;
                }
            }
            return;
        }
        if (object == this.N) {
            switch (this.E) {
                case 3: 
                case 4: 
                case 5: 
                case 7: 
                case 8: {
                    this.D = false;
                    this.I = false;
                    return;
                }
            }
            return;
        }
        if (object == this.M) {
            int n3 = 0;
            block56: do {
                switch (n3 == 0 ? this.E : n3) {
                    case 26: 
                    case 29: {
                        this.I = false;
                        return;
                    }
                    case 7: {
                        this.D = false;
                        this.C = true;
                        this.I = false;
                        return;
                    }
                    case 5: {
                        switch (this.G) {
                            case 2: {
                                this.D = false;
                                this.I = false;
                                return;
                            }
                        }
                        String[] stringArray = d.b.a(this.at, this.Z);
                        this.Q.setString(stringArray[0]);
                        this.R.setString(stringArray[1]);
                        this.d();
                        return;
                    }
                    case 1: 
                    case 3: 
                    case 12: 
                    case 23: 
                    case 25: {
                        this.D = false;
                        this.I = false;
                        return;
                    }
                    case 16: {
                        if (this.F == 12) {
                            v3.E = 12;
                            this.C();
                            this.t();
                            this.append(null);
                            this.addCommand(this.L);
                            this.addCommand(this.M);
                            return;
                        }
                        this.d();
                        return;
                    }
                    case 18: 
                    case 19: {
                        if (this.ac == 3) {
                            this.o();
                            return;
                        }
                        if (this.ac != 4) return;
                        n3 = 14;
                        continue block56;
                    }
                    case 15: {
                        this.e();
                        return;
                    }
                    case 14: {
                        this.d();
                        return;
                    }
                }
                return;
            } while (true);
            return;
        }
        if (object != this.P) return;
        this.J.setText("");
        this.j();
    }

    public static String a(String object, String string) {
        String string2 = "";
        try {
            while (((String)object).getBytes().length < string.getBytes().length / 2) {
                object = (String)object + (String)object;
            }
            object = ((String)object).getBytes("UTF-8");
            for (int i2 = 0; i2 < string.length() - 1; i2 += 2) {
                int n2 = 0;
                n2 = (byte)d.b.f(string.substring(i2, i2 + 2)) ^ object[i2 / 2];
                string2 = string2 + (char)n2;
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return string2;
    }

    private void B() {
        String[] stringArray;
        Object object;
        this.L = new Command(d.b.a(0, 0), 4, 1);
        this.M = new Command(d.b.a(1, 0), 3, 1);
        this.N = new Command(d.b.a(2, 0), 3, 1);
        this.O = new Command(d.b.a(3, 0), 4, 1);
        new Command(d.b.a(6, 1), 8, 1);
        this.P = new Command(d.b.a(7, 1), 8, 1);
        this.J = new StringItem("", "");
        this.K = new StringItem("", "");
        this.Q = new TextField(ax, k, 10, 0);
        this.R = new TextField(d.b.a(5, 1), l, 20, 0);
        new TextField(d.b.a(56, 1), "", 20, 3);
        this.S = new TextField("", "", 20, 0);
        this.T = new TextField("", "", 20, 0);
        this.X = new TextField(d.b.a(57, 1), "30", 20, 5);
        this.setCommandListener(this);
        this.setItemStateListener(this);
        Object object2 = d.b.a("keyp", y);
        if (object2.equals("")) {
            u = w;
            return;
        }
        String string = d.b.a("payVer", y);
        if (string.equals("")) {
            u = w;
            return;
        }
        f = d.b.a("lakoo" + string, (String)object2);
        ay = d.b.a("idType", y);
        ax = d.b.a("idName", y);
        av = d.b.a("coinName", y);
        if (ax.equals("") || av.equals("")) {
            ay = "noid";
        }
        if (d.b.b(200).equals("")) {
            u = w;
            return;
        }
        object2 = d.b.a("isSearchID", y);
        if (!object2.equals("") && object2.toLowerCase().equals("false")) {
            aJ = false;
        }
        if ((object2 = d.b.a("payMethod", y)).equals("")) {
            u = w;
            return;
        }
        if (!ay.equals("noid")) {
            int n2;
            object = d.b.c((String)object2, ",");
            x = new int[((String[])object).length];
            object2 = new String[((String[])object).length];
            for (n2 = 0; n2 < ((Object)object).length; ++n2) {
                try {
                    int n3;
                    d.b.x[n2] = n3 = Integer.parseInt((String)object[n2]);
                    switch (n3) {
                        case 1: {
                            object2[n2] = d.b.a(d.b.a(1, 1), "$(str)", av);
                            break;
                        }
                        case 2: {
                            object2[n2] = d.b.a(2, 1);
                            break;
                        }
                        case 3: {
                            object2[n2] = d.b.a(3, 1);
                            break;
                        }
                        case 4: {
                            object2[n2] = d.b.a(4, 1);
                        }
                    }
                    continue;
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            this.U = new ChoiceGroup(d.b.a(0, 1), 1, object2, null);
            try {
                n2 = Integer.parseInt(d.b.a("payDefault", y));
                if (n2 > ((String[])object2).length - 1) {
                    n2 = 0;
                }
                this.U.setSelectedIndex(n2, true);
            }
            catch (Exception exception) {}
            object2 = new String[]{"", "", "", ""};
            this.W = new ChoiceGroup(null, 1, object2, null);
            object = new String[]{d.b.a(7, 1) + ax, d.b.a(d.b.a(58, 1), "$(str)", ax)};
            this.V = new ChoiceGroup("", 1, (String[])object, null);
        }
        if (ay.equals("noid") && (object = d.b.a("keys", y)) != null && ((String)object).length() > 0 && (object2 = d.b.a("lakoo" + string + "sms", (String)object)).length() > 0 && (stringArray = d.b.c((String)object2, ",")) != null && stringArray.length == 3) {
            B = stringArray;
        }
    }

    private void C() {
        int n2 = 0;
        for (n2 = this.size() - 1; n2 >= 0; --n2) {
            this.delete(n2);
        }
    }

    private void c(String stringArray) {
        this.G = 0;
        this.E = 0;
        this.C = false;
        this.D = false;
        this.an = 0;
        this.K.setText("");
        stringArray = d.b.a(this.at, (String)stringArray);
        this.Q.setString(stringArray[0]);
        this.R.setString(stringArray[1]);
    }

    private static String b(int n2) {
        String string;
        String[] stringArray = d.b.a("locale", y);
        if (stringArray.equals("")) {
            u = w;
            return "";
        }
        if ((stringArray = d.b.c((String)stringArray, ",")).length != 3) {
            u = w;
            return "";
        }
        au = stringArray;
        int n3 = 0;
        int n4 = 0;
        n3 = Integer.parseInt(au[2]);
        if (au[1].equals("\u5143")) {
            n3 = n2 / 100;
            n4 = n2 % 100;
            string = n4 < 10 ? n3 + ".0" + n4 + au[1] : n3 + "." + n4 + au[1];
        } else {
            n4 = n2 * n3;
            n3 = n4 / 100;
            if (n4 % 100 >= 50) {
                ++n3;
            }
            string = au[1] + " " + n3;
        }
        return string;
    }

    private static int d(String string) {
        if ((string = g.get(string)) != null) {
            return (Integer)((Object)string);
        }
        return 0;
    }

    private static long e(String string) {
        if ((string = j.get(string)) != null) {
            return (Long)((Object)string);
        }
        return System.currentTimeMillis();
    }

    public static String[] a() {
        try {
            d.b.I();
        }
        catch (Exception exception) {}
        return new String[]{k, l, q, r, "" + o, m, n};
    }

    public static void a(String string, String string2, boolean bl) {
        k = string;
        l = string2;
        o = bl;
        d.b.H();
    }

    public static String[] a(MIDlet object, String string) {
        try {
            d.b.I();
        }
        catch (Exception exception) {}
        try {
            if (k == null || k.equals("")) {
                d.b.a(object);
                if (aH != null && !aH.equals("") && string != null && !string.equals("")) {
                    object = string + c;
                    while (object.length() < aH.length() / 2) {
                        object = (String)object + string;
                    }
                    object = object.getBytes();
                    string = "";
                    for (int i2 = 0; i2 < aH.length() - 1; i2 += 2) {
                        int n2 = 0;
                        n2 = (byte)d.b.f(aH.substring(i2, i2 + 2)) ^ object[i2 / 2];
                        string = string + (char)n2;
                    }
                    k = string.substring(0, string.indexOf("@"));
                    l = string.substring(string.indexOf("@") + 1, string.length());
                }
            }
        }
        catch (Exception exception) {}
        return new String[]{k, l, q, r, "" + o, m, n};
    }

    private static int f(String string) {
        if (string == null || string.length() < 1) {
            return 0;
        }
        int n2 = 1;
        int n3 = 0;
        block0: for (int i2 = string.length() - 1; i2 > -1; --i2) {
            char c2 = string.charAt(i2);
            for (int i3 = 0; i3 < aT.length; ++i3) {
                if (aT[i3] != c2) continue;
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
        d.b.a(recordStore);
        return byArray2;
    }

    private void D() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        int n2 = ((b)object).h.size();
        Enumeration enumeration = ((b)object).h.keys();
        try {
            dataOutputStream.writeInt(n2);
            dataOutputStream.writeInt(((b)object).ao);
            dataOutputStream.writeInt(((b)object).ap);
            while (enumeration.hasMoreElements()) {
                String string = (String)enumeration.nextElement();
                byte[] byArray = (byte[])((b)object).h.get(string);
                dataOutputStream.writeUTF(string);
                dataOutputStream.writeInt(byArray.length);
                dataOutputStream.write(byArray);
            }
            Object object = byteArrayOutputStream.toByteArray();
            d.b.a(3, (byte[])object, "gil_api_private");
            dataOutputStream.close();
            byteArrayOutputStream.close();
            return;
        }
        catch (Exception exception) {
            u = v;
            return;
        }
    }

    private void E() {
        try {
            Object object = d.b.a(3, null, "gil_api_private");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])object);
            DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
            int n2 = dataInputStream.readInt();
            this.ao = dataInputStream.readInt();
            this.ap = dataInputStream.readInt();
            for (int i2 = 0; i2 < n2; ++i2) {
                object = dataInputStream.readUTF();
                boolean bl = false;
                byte[] byArray = new byte[dataInputStream.readInt()];
                dataInputStream.readFully(byArray);
                this.h.put(object, byArray);
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

    private boolean g(String object) {
        if ((object = this.h.get(object)) == null) {
            return false;
        }
        object = (byte[])object;
        try {
            object = new ByteArrayInputStream((byte[])object);
            DataInputStream dataInputStream = new DataInputStream((InputStream)object);
            this.ai = dataInputStream.readUTF();
            this.aj = dataInputStream.readUTF();
            this.am = dataInputStream.readInt();
            this.an = dataInputStream.readInt();
            this.al = dataInputStream.readInt();
            this.ak = dataInputStream.readUTF();
            dataInputStream.close();
            ((ByteArrayInputStream)object).close();
            return true;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    private static void F() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        int n2 = g.size();
        Enumeration enumeration = g.keys();
        try {
            dataOutputStream.writeInt(n2);
            while (enumeration.hasMoreElements()) {
                String string = (String)enumeration.nextElement();
                int n3 = d.b.d(string);
                long l2 = d.b.e(string);
                dataOutputStream.writeUTF(string);
                dataOutputStream.writeInt(n3);
                dataOutputStream.writeLong(l2);
            }
            byte[] byArray = byteArrayOutputStream.toByteArray();
            d.b.a(1, byArray, "gil_api_private");
            dataOutputStream.close();
            byteArrayOutputStream.close();
            return;
        }
        catch (Exception exception) {
            u = v;
            return;
        }
    }

    private static void G() {
        Object object = d.b.a(1, null, "gil_api_private");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])object);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        int n2 = dataInputStream.readInt();
        for (int i2 = 0; i2 < n2; ++i2) {
            object = dataInputStream.readUTF();
            int n3 = dataInputStream.readInt();
            long l2 = dataInputStream.readLong();
            g.put(object, new Integer(n3));
            j.put(object, new Long(l2));
        }
        dataInputStream.close();
        byteArrayInputStream.close();
    }

    public static void a(String string, int n2) {
        g.put(string, new Integer(n2));
        d.b.F();
    }

    private static void H() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Object object = new DataOutputStream(byteArrayOutputStream);
        Object object2 = null;
        try {
            if (k != null && k.length() > 0) {
                k.trim();
            }
            if (l != null && l.length() > 0) {
                l.trim();
            }
            ((DataOutputStream)object).writeUTF(k);
            ((DataOutputStream)object).writeUTF(l);
            ((DataOutputStream)object).writeBoolean(o);
            ((DataOutputStream)object).writeBoolean(o);
            ((DataOutputStream)object).writeUTF(m);
            ((DataOutputStream)object).writeInt(p);
            ((DataOutputStream)object).writeUTF(n);
            object2 = byteArrayOutputStream.toByteArray();
            d.b.a(2, (byte[])object2, "gil_api_private");
            ((FilterOutputStream)object).close();
            byteArrayOutputStream.close();
        }
        catch (Exception exception) {
            u = v;
        }
        try {
            if (t) {
                String string = object2;
                object2 = "wappay_gil_api" + d.b.a("rmsName", y);
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
                d.b.a(recordStore);
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
                    string = z.elementAt(n2).toString();
                    break;
                }
                case 1: {
                    string = A.elementAt(n2).toString();
                }
            }
        }
        catch (Exception exception) {}
        return string;
    }

    private static void I() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(d.b.a(2, null, "gil_api_private"));
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        k = dataInputStream.readUTF();
        l = dataInputStream.readUTF();
        o = dataInputStream.readBoolean();
        dataInputStream.readBoolean();
        m = dataInputStream.readUTF();
        p = dataInputStream.readInt();
        n = dataInputStream.readUTF();
        dataInputStream.close();
        byteArrayInputStream.close();
        try {
            if ((k == null || k.equals("")) && d.b.a("proxy", y).toLowerCase().equals("true")) {
                o = true;
            }
            if (d.b.a("method", y).toLowerCase().equals("get")) {
                e = "GET";
            }
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    public static String a(String object) {
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
                    byte[] byArray2 = byteArrayOutputStream.toByteArray();
                    string = new String(byArray2, "UTF-8");
                    inputStream.close();
                }
                dataOutputStream.close();
                byteArrayOutputStream.close();
            }
            catch (Exception exception) {
                u = w;
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
        c = mIDlet.getAppProperty("CP");
        c = c == null ? "0" : c.trim();
        d = mIDlet.getAppProperty("UA");
        if (d == null || d.equals("")) {
            int n2;
            int n3;
            d = mIDlet.getAppProperty("MIDlet-Install-Notify");
            d = d != null ? ((n3 = d.indexOf("ua=") + 3) >= 0 ? ((n2 = d.indexOf("&", n3)) >= 0 ? d.substring(n3, n2) : "untrust") : "untrust") : "untrust";
        }
        ab = (ab = mIDlet.getAppProperty("MIDlet-Version")) == null ? "1" : ab.trim();
        aH = mIDlet.getAppProperty("key");
        aM = System.getProperty("microedition.platform");
        aN = System.getProperty("microedition.profiles");
        aO = System.getProperty("microedition.configuration");
        aP = System.getProperty("microedition.pim.version");
        aQ = System.getProperty("wireless.messaging.sms.smsc");
        aK = mIDlet.getAppProperty("sms_type");
        aL = mIDlet.getAppProperty("ApplicationID");
    }

    /*
     * Loose catch block
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean b(String string, String string2) {
        if (string == null || "".equals(string.trim())) {
            return false;
        }
        if (string2 == null || "".equals(string2.trim())) {
            return false;
        }
        string = "sms://" + string;
        MessageConnection messageConnection = null;
        boolean bl = false;
        messageConnection = (MessageConnection)Connector.open((String)string);
        TextMessage textMessage = (TextMessage)messageConnection.newMessage("text");
        textMessage.setAddress(string);
        textMessage.setPayloadText(string2);
        messageConnection.send((Message)textMessage);
        try {
            messageConnection.send(null);
        }
        catch (Exception exception) {
            bl = true;
        }
        if (messageConnection == null) return bl;
        try {
            messageConnection.close();
            return bl;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return bl;
        catch (Throwable throwable) {
            try {
                throwable.printStackTrace();
                if (messageConnection == null) return bl;
            }
            catch (Throwable throwable2) {
                if (messageConnection == null) throw throwable2;
                try {
                    messageConnection.close();
                    throw throwable2;
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                throw throwable2;
            }
            try {
                messageConnection.close();
                return bl;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            return bl;
        }
    }

    public static void a(MIDlet mIDlet, Displayable displayable, String string) {
        Display display = Display.getDisplay((MIDlet)mIDlet);
        b = new b(mIDlet);
        b.c(string);
        d.b.a(mIDlet);
        if (ay.equals("noid")) {
            b.e();
            d.b.b.J.setText(d.b.a(18, 0) + "\n" + d.b.a("ContactUs", y));
        } else {
            b.getClass();
            d.b.b.G = 2;
            b.i();
        }
        d.b.b.I = true;
        d.b.b.Z = string;
        b.b();
        display.setCurrent((Displayable)b);
        while (!d.b.b.C) {
            d.b.a(300L);
        }
        display.setCurrent(displayable);
    }

    public static boolean a(MIDlet object, Displayable displayable, String string, String string2, String string3, String string4, int n2, boolean bl) {
        String string5;
        block12: {
            int n3 = n2;
            string5 = string3;
            if (d.b.d(string5) >= n3) {
                // empty if block
            }
            if (true) {
                return true;
            }
            string5 = Display.getDisplay((MIDlet)object);
            b = new b((MIDlet)object);
            d.b.a((MIDlet)object);
            int n4 = n2;
            String string6 = string4;
            String string7 = string3;
            string4 = string2;
            string2 = string;
            object = b;
            ((b)object).c(string4);
            ((b)object).I = true;
            ((b)object).az = string7;
            ((b)object).aA = string6;
            ((b)object).Z = string4;
            ((b)object).H = d.b.d(string7);
            s = d.b.e(string7);
            ((b)object).aB = n4;
            ((b)object).Y = string2;
            string2 = object;
            string4 = d.b.b(((b)((Object)string2)).aB);
            if (!av.equals("") && !ay.equals("noid")) {
                try {
                    aw = Integer.parseInt(d.b.a("coinExc", y));
                    int n5 = ((b)((Object)string2)).aB * 100 / aw;
                    string4 = n5 + av + "(" + string4 + ")";
                }
                catch (Throwable throwable) {
                    u = w;
                    break block12;
                }
            }
            ((b)((Object)string2)).Y = d.b.a("payMsg", y).equals("") ? ((b)((Object)string2)).Y + "\n" + d.b.a(d.b.a(6, 0), "$(str)", string4) : ((b)((Object)string2)).Y + "\n" + d.b.a("payMsg", y);
        }
        super.b();
        if (u > 0) {
            b.e();
        } else if (d.b.b.H > 0 && d.b.b.H < d.b.b.aB) {
            if (b.g(string3)) {
                b.A();
                d.b.b.J.setText(string + "\n" + d.b.b.J.getText());
            } else {
                b.d();
            }
        } else {
            b.d();
        }
        string5.setCurrent((Displayable)b);
        while (!d.b.b.C) {
            d.b.a(500L);
        }
        string5.setCurrent(displayable);
        return d.b.b.D;
    }

    public static boolean a(MIDlet object, Displayable displayable, String string, String string2, String string3, String string4) {
        Display display = Display.getDisplay((MIDlet)object);
        b = new b((MIDlet)object);
        b.c(string2);
        d.b.a((MIDlet)object);
        if (ay.equals("noid")) {
            b.e();
            d.b.b.J.setText(d.b.a(18, 0) + "\n" + d.b.a("ContactUs", y) + "\n");
        } else {
            d.b.b.Y = string;
            d.b.b.Z = string2;
            d.b.b.aF = string3;
            d.b.b.aG = string4;
            if (k != null && !"".equals(k)) {
                object = b;
                b.E = 25;
                super.C();
                super.t();
                ((b)object).J.setText(((b)object).Y);
                object.append((Item)((b)object).J);
                object.append((Item)((b)object).Q);
                object.append((Item)((b)object).R);
                object.append(ax.equals("") ? "" : ax + d.b.a(d.b.a(19, 1), "$(str)", d.b.a("sentName", y)));
                object.addCommand(((b)object).L);
                object.addCommand(((b)object).M);
            } else {
                object = b;
                b.E = 23;
                super.C();
                super.t();
                ((b)object).J.setText(((b)object).Y);
                object.append((Item)((b)object).J);
                object.append((Item)((b)object).V);
                object.append(ax.equals("") ? "" : ax + d.b.a(d.b.a(19, 1), "$(str)", d.b.a("sentName", y)));
                object.addCommand(((b)object).L);
                object.addCommand(((b)object).M);
            }
        }
        b.b();
        display.setCurrent((Displayable)b);
        while (!d.b.b.C) {
            d.b.a(500L);
        }
        display.setCurrent(displayable);
        return d.b.b.D;
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

    public static String[] c(String string, String string2) {
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
        if ((stringArray = d.b.c((String)stringArray, ",")) == null) {
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
            if ((string = d.b.b(string, 1)) == null) {
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
        string4 = d.b.i(string4);
        string5 = d.b.i(string5);
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
        object = new a((String)charSequence2, string6, (String)object, (Hashtable)object2);
        try {
            object = h.a.a((a)object, bl, null);
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
            return d.b.a((byte[])((Hashtable)object).get("data"));
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
        object = new a((String)object, "GET", null, null);
        try {
            object = h.a.a((a)object, bl, null);
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
            return d.b.b((byte[])((Hashtable)object).get("data"));
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
        String[] stringArray = d.b.a("/payInfo.ini");
        if (stringArray.equals("")) {
            u = w;
        } else {
            stringArray = d.b.c((String)stringArray, "\r\n");
            int n2 = 0;
            block15: for (int i2 = 0; i2 < stringArray.length; ++i2) {
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
                        if (stringArray2.equals("")) continue block15;
                        stringArray2 = d.b.c((String)stringArray2, "=");
                        y.put(stringArray2[0], stringArray2[1]);
                        continue block15;
                    }
                    case 3: {
                        if (z == null) {
                            z = new Vector();
                        }
                        z.addElement(stringArray2);
                        continue block15;
                    }
                    case 5: {
                        if (A == null) {
                            A = new Vector();
                        }
                        A.addElement(stringArray2);
                    }
                }
            }
        }
        boolean bl = false;
        RecordStore recordStore = null;
        try {
            d.b.G();
            d.b.I();
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
                u = v;
            }
            d.b.a(recordStore);
            try {
                RecordStore.deleteRecordStore((String)("wappay_gil_api" + d.b.a("rmsName", y)));
            }
            catch (Exception exception) {}
            try {
                recordStore = RecordStore.openRecordStore((String)("wappay_gil_api" + d.b.a("rmsName", y)), (boolean)true, (int)1, (boolean)false);
                recordStore.addRecord(byArray, 0, 1);
                recordStore.addRecord(byArray, 0, 100);
            }
            catch (Exception exception) {
                t = false;
            }
            d.b.a(recordStore);
            if (u == 0) {
                s = System.currentTimeMillis();
                d.b.F();
                d.b.H();
            }
        }
        c = "";
        ab = "";
        d = "";
        au = new String[3];
        av = "";
        aw = 0;
        ax = "";
        ay = "";
        aH = "";
        e = "POST";
        aJ = true;
        aK = "";
        aL = "";
        aM = "";
        aN = "";
        aO = "";
        aP = "";
        aQ = "";
        aR = "";
        aT = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        aU = true;
        aV = false;
    }
}

