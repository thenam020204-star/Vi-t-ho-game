/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.ConnectionNotFoundException
 *  javax.microedition.lcdui.Canvas
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 *  javax.microedition.media.Manager
 *  javax.microedition.media.Player
 *  javax.microedition.midlet.MIDlet
 *  javax.microedition.rms.RecordEnumeration
 *  javax.microedition.rms.RecordStore
 *  javax.microedition.rms.RecordStoreException
 */
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Random;
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class c
extends Canvas
implements i,
Runnable {
    private static g s;
    private static h t;
    private static h u;
    private static h v;
    private static h w;
    private static h x;
    private static h y;
    private static int z;
    private static int A;
    private static int B;
    private static int C;
    private static short D;
    private static short E;
    private static short F;
    private static short G;
    private static short H;
    private static short I;
    private static short J;
    private static short K;
    private static short L;
    private static short M;
    private static short N;
    private static short O;
    private static int P;
    private static int aK;
    private static int aL;
    private static int aM;
    private static int aN;
    private static short[] aO;
    private static a aP;
    static a a;
    private static a aQ;
    static int b;
    private static int aR;
    private static int aS;
    private static a aT;
    private static a aU;
    static a c;
    static b[] d;
    static b e;
    private static int aV;
    private static byte[] aW;
    private static byte[] aX;
    private static Graphics aY;
    private static int[] aZ;
    private static int[] ba;
    private static int[] bb;
    private static byte[] bc;
    private static short[] bd;
    private static int be;
    private static byte bf;
    private static int bg;
    private static int bh;
    private static int bi;
    private static int bj;
    private static int bk;
    private static int bl;
    private static int[] bm;
    private static int[] bn;
    private static byte[] bo;
    private static int[] bp;
    private static int[] bq;
    private static int[] br;
    private static int[] bs;
    private static int[] bt;
    private static short[] bu;
    private static short[] bv;
    private static short[] bw;
    private static short[] bx;
    private static int by;
    private static int bz;
    private static int bA;
    private static byte[] bB;
    private static short[] bC;
    private static int[] bD;
    private static int bE;
    private static long bF;
    private static int bG;
    private static byte[] bH;
    private static int bI;
    private static int bJ;
    private static boolean bK;
    private static Image bL;
    private static Graphics bM;
    private static Image[] bN;
    private static int[] bO;
    private static short[][] bP;
    private static byte[][] bQ;
    private static int bR;
    private static int bS;
    private static int bT;
    private static boolean bU;
    private static int bV;
    private static int bW;
    private static int bX;
    private static Random bY;
    private static DataInputStream bZ;
    static boolean f;
    private static boolean ca;
    private static int cb;
    private static byte cc;
    private static byte cd;
    private static byte ce;
    private static byte cf;
    private static int cg;
    private static int ch;
    private static int[] ci;
    private static int cj;
    private static a[] ck;
    private static Image[] cl;
    private static boolean[] cm;
    private static Image cn;
    private static Image co;
    private static Image cp;
    private static a cq;
    private static a cr;
    private static a cs;
    private static int ct;
    private static a[] cu;
    private static Image cv;
    private static int cw;
    private static int cx;
    private static a[] cy;
    static a[] g;
    private static a[] cz;
    private static Image cA;
    private static a cB;
    private static Image cC;
    private static String cD;
    private static a[] cE;
    private static int cF;
    private static a[] cG;
    private static String cH;
    private static boolean cI;
    private static int cJ;
    private static int cK;
    private static int cL;
    private static int cM;
    private static int[][] cN;
    private static int[][] cO;
    private static int[][][] cP;
    private static int[][] cQ;
    static int[][] h;
    private static int[][] cR;
    private static int[][] cS;
    private static int[][] cT;
    static int[][] i;
    private static int[][] cU;
    private static int[][] cV;
    private static int[][] cW;
    private static int[][] cX;
    private static int[] cY;
    private static Image[] cZ;
    private static Image[] da;
    private static Image db;
    private static Image dc;
    private static byte[] dd;
    static byte j;
    private static byte de;
    static int k;
    private static boolean df;
    private static int dg;
    private static long dh;
    private static long di;
    private static byte dj;
    private static boolean dk;
    private static boolean dl;
    private static int[] dm;
    private static boolean dn;
    private static int do;
    private static int dp;
    private static int dq;
    private static int dr;
    private static boolean ds;
    private static String dt;
    private static int[] du;
    private static int dv;
    private static DataInputStream dw;
    private static long dx;
    private static byte[] dy;
    private static byte[] dz;
    private static a dA;
    private static a dB;
    private static int[][] dC;
    private static int dD;
    private static int dE;
    private static int dF;
    private static int dG;
    private static int dH;
    private static int dI;
    private static int dJ;
    private static int dK;
    private static int dL;
    private static int dM;
    private static int dN;
    private static int dO;
    private static int dP;
    private static int dQ;
    private static int dR;
    private static int[] dS;
    private static int dT;
    private static int dU;
    private static int[] dV;
    private static int dW;
    private static int dX;
    private static int dY;
    private static int dZ;
    private static int ea;
    private static int eb;
    private static int ec;
    private static int ed;
    private static int ee;
    private static int ef;
    private static boolean eg;
    private static boolean eh;
    private static int[] ei;
    private static int[] ej;
    private static int ek;
    private static int el;
    private static int em;
    private static int en;
    private static char[] eo;
    private static int ep;
    private static int eq;
    private static int er;
    private static int es;
    private static int et;
    private static int eu;
    private static boolean ev;
    private static boolean ew;
    private static int[] ex;
    private static int ey;
    private static a ez;
    private static Image eA;
    private static int eB;
    private static int[] eC;
    private static int eD;
    private static int[] eE;
    private static int eF;
    private static a eG;
    private static int eH;
    private static byte[] eI;
    private static int eJ;
    private static int[] eK;
    private static int eL;
    private static a[] eM;
    private static a[] eN;
    private static a eO;
    private static int eP;
    private static int eQ;
    private static int eR;
    private static int eS;
    private static int eT;
    static boolean l;
    private static int eU;
    private static int[] eV;
    private static int eW;
    private static int eX;
    private static int eY;
    private static int eZ;
    private static int[] fa;
    private static int fb;
    private static int fc;
    private static int fd;
    private static int fe;
    private static boolean ff;
    private static boolean fg;
    private static short fh;
    private static short fi;
    private static boolean fj;
    static int[] m;
    static int n;
    static boolean o;
    private static a[] fk;
    static int p;
    private static int fl;
    static int[] q;
    private static int[] fm;
    private static int fn;
    private static Image fo;
    private static a fp;
    private static a fq;
    private static Image fr;
    private static a fs;
    private static int[] ft;
    private static int fu;
    private static int fv;
    private static byte[] fw;
    private static byte[][] fx;
    private static Player fy;
    private static int fz;
    private static short fA;
    private static int fB;
    private static int fC;
    private static byte[] fD;
    private static int[] fE;
    static int[] r;
    private static byte fF;
    private static byte fG;
    private static int fH;
    private static a[] fI;
    private static boolean fJ;
    private static int fK;
    private static int fL;
    private static String[] fM;

    private static void e(int n2, int n3) {
        c.cY[n2] = n3;
    }

    private static boolean b(int n2, int n3, int n4) {
        int n5 = n2;
        cY[n5] = cY[n5] + n3;
        if (cY[n2] > n4) {
            c.cY[n2] = n4;
            return true;
        }
        return false;
    }

    public c() {
        Display.getDisplay((MIDlet)IMARPG22.a);
        this.setFullScreenMode(true);
        aW = new byte[20];
        aX = new byte[20];
    }

    private static void k() {
        if (bF == 0L) {
            try {
                for (int i2 = 0; i2 < 4; ++i2) {
                    c.cZ[i2] = Image.createImage((String)("/p" + i2));
                }
                db = Image.createImage((String)"/logo_G");
                DataInputStream dataInputStream = new DataInputStream("".getClass().getResourceAsStream("/sound.mid"));
                dd = new byte[dataInputStream.available()];
                dataInputStream.read(dd);
                dataInputStream.close();
            }
            catch (IOException iOException) {
                IOException iOException2 = iOException;
                iOException.printStackTrace();
            }
        }
        if (bF > 20L) {
            if (c.b(264224)) {
                bU = true;
                k |= 1;
                c.b((byte)19);
                return;
            }
            if (c.b(524288)) {
                bU = false;
                k &= 1;
                c.b((byte)19);
            }
        }
    }

    private static void l() {
        int n2;
        for (n2 = 0; n2 < 4; ++n2) {
            c.cZ[n2] = null;
        }
        for (n2 = 0; n2 < 7; ++n2) {
            c.da[n2] = null;
        }
        cZ = null;
        da = null;
        db = null;
        dc = null;
        dd = null;
        System.gc();
    }

    private static void m() {
        int n2;
        if (dc == null) {
            try {
                for (n2 = 0; n2 < 7; ++n2) {
                    c.da[n2] = Image.createImage((String)("/m" + n2));
                }
                dc = Image.createImage((String)"/BG");
                if (bU) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(dd);
                    fy = Manager.createPlayer((InputStream)byteArrayInputStream, (String)"audio/midi");
                    fy.start();
                }
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
        }
        if (++cb > 80) {
            String string = "oIobHzifoyv9MXo8xeZd/GOps2k=";
            String string2 = "000036748000";
            n2 = 10382;
            at.a(10382, string2, string);
            int n3 = -7;
            n2 = -6;
            at.a(-6, -7);
            de = 0;
            c.b((byte)21);
        }
    }

    private static void n() {
        switch (j) {
            case 0: {
                if (c.b(264224)) {
                    j = 1;
                    return;
                }
                if (!c.b(524288)) break;
                c.b((byte)6);
                return;
            }
            case 1: {
                if (f.a) break;
                f.a();
                return;
            }
            case 2: {
                if (!c.b(524288)) break;
                c.b((byte)6);
                j = 0;
                return;
            }
            case 3: {
                if (!c.b(524288)) break;
                c.b((byte)6);
                j = 0;
            }
        }
    }

    private static void o() {
        block13: {
            block12: {
                if (!c.b(264224)) break block12;
                if (!e.a) {
                    e.a();
                }
                switch (de) {
                    case 0: {
                        c.b((byte)0);
                        break block13;
                    }
                    case 1: {
                        d d2 = new d();
                        IMARPG22 iMARPG22 = IMARPG22.a;
                        at.a(iMARPG22, d2);
                        c.b((byte)7);
                        break block13;
                    }
                    case 2: {
                        IMARPG22 iMARPG22 = IMARPG22.a;
                        at.a(iMARPG22);
                        c.b((byte)7);
                    }
                    default: {
                        return;
                    }
                }
            }
            if (c.b(524288)) {
                switch (de) {
                    case 0: {
                        c.b((byte)0);
                        return;
                    }
                    case 1: {
                        c.b((byte)7);
                        return;
                    }
                    case 2: {
                        c.b((byte)7);
                    }
                }
            }
        }
    }

    private static void p() {
        c.a(aY, 0, 0, 0, 240, 320);
        c.a(aY, "\u9700\u8981\u8054\u7f51\u624d\u80fd\u4f7f\u7528\u4e92\u52a8\u529f\u80fd\uff0c\u662f\u5426\u7acb\u523b\u8054\u7f51\uff1f", 120, 160 - i.R, 33, 0xFFFFFF, 0xFFFFFF, 12, 0, 0);
        c.a(aY, "\u662f", 4, i.aI, 20, 0xFFFFFF, 0xFFFFFF);
        c.a(aY, "\u5426", 236, i.aI, 24, 0xFFFFFF, 0xFFFFFF);
    }

    /*
     * Unable to fully structure code
     */
    public final void paint(Graphics var1_1) {
        c.aY = var1_1;
        c.aY.setFont(i.Q);
        try {
            block98: {
                if (c.bU && c.fy != null && c.fy.getState() == 200) {
                    c.C(c.fz);
                }
                for (var1_2 = 0; var1_2 < 20; ++var1_2) {
                    c.aW[var1_2] = c.aX[var1_2];
                    if (c.aX[var1_2] == 0) continue;
                    if (c.aX[var1_2] < 0) {
                        c.aX[var1_2] = 0;
                        continue;
                    }
                    if (c.aX[var1_2] >= 126) continue;
                    v0 = var1_2;
                    c.aX[v0] = (byte)(c.aX[v0] + 1);
                    if (var1_2 < 262144) continue;
                    v1 = var1_2;
                    c.aX[v1] = -c.aX[v1];
                }
                block2 : switch (c.cc) {
                    case 11: {
                        if (c.cI) {
                            switch (c.bf) {
                                case 0: {
                                    c.z(4);
                                    break;
                                }
                                case 1: {
                                    c.z(10);
                                    break;
                                }
                                case 2: {
                                    c.z(10);
                                    break;
                                }
                                case 3: {
                                    c.z(15);
                                    break;
                                }
                                case 4: {
                                    c.z(8);
                                    break;
                                }
                                case 5: {
                                    c.z(240);
                                    break;
                                }
                                case 6: {
                                    c.z(15);
                                }
                            }
                        }
                        block34 : switch (c.cd) {
                            case 1: {
                                c.D();
                                c.r(c.dJ);
                                c.ag();
                                if (c.dH != 0) {
                                    c.a(c.cf);
                                    break;
                                }
                                break block98;
                            }
                            case 0: {
                                for (var1_2 = 0; var1_2 < 16; ++var1_2) {
                                    if (c.bc[var1_2] != 1) continue;
                                    if (c.bb[var1_2] > 0) {
                                        v2 = var1_2;
                                        c.ba[v2] = c.ba[v2] - 1;
                                    } else {
                                        v3 = var1_2;
                                        c.ba[v3] = c.ba[v3] + 1;
                                    }
                                    if (c.bd[var1_2] < 0) continue;
                                    c.ci[c.bd[var1_2]] = c.ba[var1_2];
                                }
                                for (var1_2 = 0; var1_2 < c.bl; ++var1_2) {
                                    c.k(var1_2);
                                }
                                c.D();
                                c.af();
                                c.ag();
                                if (c.b >= 0) {
                                    c.j(c.b, 1, 0);
                                    c.b = -1;
                                }
                                if (c.ct >= 0) {
                                    c.cq.e();
                                }
                                if (c.a == null) break block2;
                                if (c.b(262144)) {
                                    c.c(1, 32, 146, 44, 28);
                                    break;
                                }
                                if (c.b(128)) {
                                    if (c.d[14].b == null) break block2;
                                    var1_3 = c.d[14].b;
                                    if (var1_3 != null) {
                                        var2_6 = b.b();
                                        c.ai();
                                        for (var3_10 = 0; var3_10 < c.fA; ++var3_10) {
                                            if (var1_3.b == null) {
                                                c.fA = 0;
                                                var1_3 = c.d[14].b;
                                                break;
                                            }
                                            var1_3 = var1_3.b;
                                        }
                                        var2_6.b(var1_3);
                                        c.e.a(var2_6);
                                        c.aQ = c.a;
                                        c.a = null;
                                    }
                                    c.a((byte)7);
                                    break;
                                }
                                if (c.b(32768)) {
                                    if (c.ci[122] > 0) {
                                        if (c.a.E[15] == 1) {
                                            var1_4 = c.a;
                                            var2_7 = var1_4.v;
                                            var3_11 = var1_4.w;
                                            var4_13 = var1_4.i % 4;
                                            var5_14 = var1_4.r[0];
                                            var6_15 = var1_4.r[1];
                                            var7_16 = var1_4.r[2];
                                            var1_5 = var1_4.r[3];
                                            c.dD = -1;
                                            c.dE = -1;
                                            for (var8_17 = 0; var8_17 < 3; ++var8_17) {
                                                if (c.f(var2_7 + var5_14, var3_11 + var6_15) < 0 && c.f(var2_7 + var5_14 + var7_16, var3_11 + var6_15) < 0 && c.f(var2_7 + var5_14, var3_11 + var6_15 + var1_5) < 0 && c.f(var2_7 + var5_14 + var7_16, var3_11 + var6_15 + var1_5) < 0) {
                                                    c.dD = var2_7;
                                                    c.dE = var3_11;
                                                    break block34;
                                                }
                                                switch (var4_13) {
                                                    case 0: {
                                                        var3_11 -= 16;
                                                        break;
                                                    }
                                                    case 1: {
                                                        var2_7 += 16;
                                                        break;
                                                    }
                                                    case 2: {
                                                        var3_11 += 16;
                                                        break;
                                                    }
                                                    case 3: {
                                                        var2_7 -= 16;
                                                    }
                                                }
                                                if (c.a(var2_7, var3_11)) break block2;
                                            }
                                            break block2;
                                        }
                                        c.c(c.a);
                                        c.dg = c.ci[22];
                                        c.ci[22] = 1;
                                        break;
                                    }
                                    c.t = h.g();
                                    c.t.a(57, 50, i.ao, 140, i.an, false);
                                    c.t.a(3, 4, 5, 16770483, 0);
                                    c.t.r = 20;
                                    c.t.h = true;
                                    c.a(c.t, 1144, 0);
                                    c.t.a(20);
                                    c.a((byte)4);
                                    break;
                                }
                                if (!c.b(524288)) break block2;
                                c.c(2, 32, 56, 176, 208);
                                break;
                            }
                            case 2: {
                                c.F();
                                break;
                            }
                            case 3: {
                                c.ff = false;
                                if ((c.fc & 2) != 0) {
                                    for (var1_2 = 0; var1_2 < c.eQ; ++var1_2) {
                                        c.eM[var1_2].e();
                                    }
                                }
                                if ((c.fc & 1) != 0) {
                                    c.cB.e();
                                }
                                var1_2 = 1;
                                for (var2_8 = 0; var2_8 < c.eQ; ++var2_8) {
                                    if (c.eN[var2_8].l) continue;
                                    c.eN[var2_8].e();
                                    var1_2 = 0;
                                }
                                if (!c.eO.l) {
                                    c.eO.e();
                                    var1_2 = 0;
                                }
                                c.ff = var1_2;
                                c.fc = 15;
                                switch (c.eL) {
                                    case 12: {
                                        for (var3_12 = 0; var3_12 < c.eJ; ++var3_12) {
                                            var1_2 = c.eI[var3_12];
                                            if (var1_2 < 0) continue;
                                            ++c.eU;
                                            c.eI[var3_12] = -1;
                                            if (c.eM[var1_2].F[30] < c.eH) continue;
                                            c.eF = var1_2;
                                            c.eG = c.eM[c.eF];
                                            c.w(0);
                                            ** GOTO lbl190
                                        }
                                        c.eJ = 0;
                                        for (var3_12 = 0; var3_12 < c.eQ; ++var3_12) {
                                            var2_9 = c.eM[var3_12];
                                            if ((var2_9.F[21] & 1) != 0) continue;
                                            var2_9.F[30] = var2_9.F[30] + (var2_9.F[14] + var2_9.H[21]);
                                            if (var2_9.F[30] < c.eH) continue;
                                            c.v(var3_12);
                                            var2_9.F[30] = c.eH;
                                        }
lbl190:
                                        // 2 sources

                                        c.fb = 287;
                                        break;
                                    }
                                    case 0: {
                                        if (!c.Q()) {
                                            c.R();
                                        }
                                        c.fb = 287;
                                        break;
                                    }
                                    case 10: {
                                        if (h.E == 1) {
                                            c.T();
                                        }
                                        c.w(1);
                                        c.fb = 287;
                                        break;
                                    }
                                    case 1: {
                                        c.Q();
                                        c.U();
                                        break;
                                    }
                                    case 2: {
                                        c.e(c.x);
                                        break;
                                    }
                                    case 3: {
                                        g.n = true;
                                        c.s.f();
                                        if (g.n) {
                                            c.S();
                                            c.a((byte)6);
                                        }
                                        c.fc = 7;
                                        break;
                                    }
                                    case 4: {
                                        c.W();
                                        break;
                                    }
                                    case 6: {
                                        c.Z();
                                        break;
                                    }
                                    case 5: {
                                        c.P();
                                        break;
                                    }
                                    case 11: {
                                        c.O();
                                    }
                                }
                                c.N();
                                h.o = false;
                                if (!c.dl) break block2;
                                c.dl = false;
                                c.fc |= 7;
                                break;
                            }
                            case 4: {
                                if (c.t != null) {
                                    if (c.ax()) {
                                        c.t.j();
                                    }
                                    c.cq.e();
                                    break;
                                }
                                c.a(c.ce);
                                break;
                            }
                            case 5: {
                                c.ab();
                                break;
                            }
                            case 6: {
                                c.X();
                                break;
                            }
                            case 7: {
                                c.aw();
                                c.D();
                            }
                        }
                        break;
                    }
                    case 2: {
                        c.b((byte)1);
                        break;
                    }
                    case 3: {
                        c.u();
                        break;
                    }
                    case 4: {
                        if (c.b(262144)) {
                            c.k |= 1;
                            c.b((byte)6);
                            break;
                        }
                        if (!c.b(524288)) break;
                        c.k &= -2;
                        c.b((byte)6);
                        break;
                    }
                    case 5: {
                        c.av();
                        break;
                    }
                    case 6: {
                        c.ak();
                        break;
                    }
                    case 7: {
                        if (c.u == null) break;
                        c.u.j();
                        break;
                    }
                    case 8: {
                        c.ao();
                        break;
                    }
                    case 10: {
                        c.cg = c.bk;
                        c.bj = 6;
                        c.aj();
                        c.u.d();
                        c.b((byte)8);
                        break;
                    }
                    case 12: {
                        break;
                    }
                    case 13: {
                        if (!c.b(1048575)) break;
                        c.b((byte)20);
                        break;
                    }
                    case 14: {
                        if (!c.b(264224)) break;
                        c.b((byte)20);
                        break;
                    }
                    case 15: {
                        try {
                            IMARPG22.a.platformRequest(c.cD);
                            c.r();
                        }
                        catch (ConnectionNotFoundException v4) {
                            c.r();
                        }
                        break;
                    }
                    case 0: {
                        c.l();
                        c.b((byte)2);
                        break;
                    }
                    case 1: {
                        c.t();
                        c.b((byte)3);
                        break;
                    }
                    case 16: {
                        c.s();
                        break;
                    }
                    case 17: {
                        c.r();
                        break;
                    }
                    case 18: {
                        c.k();
                        break;
                    }
                    case 19: {
                        c.m();
                        break;
                    }
                    case 20: {
                        c.n();
                        break;
                    }
                    case 21: {
                        c.o();
                    }
                }
            }
            c.q();
        }
        catch (Exception v5) {
            var1_1 = v5;
            v5.printStackTrace();
            c.cH = var1_1.getMessage();
            System.out.println("s_strErr: " + c.cH);
        }
        ++c.bF;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void q() {
        switch (cc) {
            case 0: 
            case 1: {
                Graphics graphics = aY;
                c.a(graphics, 0, 0, 0, 240, 320);
                c.a(graphics, "\u6b63\u5728\u8fdb\u5165\u6e38\u620f...", 120, 160, 33, 0xFFFFFF, 0xFFFFFF);
                return;
            }
            case 16: {
                c.a(aY, 0, 0, 0, 240, 320);
                c.a(aY, "\u6e38\u620f\u5df2\u6682\u505c...", 240 - i.Q.stringWidth("\u6e38\u620f\u5df2\u6682\u505c...") >> 1, 160, 0, 0xFFFFFF, 0xFFFFFF);
                c.a(aY, "\u8fd4\u56de", 236, i.aI, 24, 0xFFFFFF, 0xFFFFFF);
                return;
            }
            case 2: {
                return;
            }
            case 3: {
                Graphics graphics = aY;
                c.a(graphics, 0, 0, 0, 240, 320);
                c.a(graphics, "\u6b63\u5728\u52a0\u8f7d\u8d44\u6e90...", 120, 160, 33, 0xFFFFFF, 0xFFFFFF);
                return;
            }
            case 4: {
                c.a(aY, 0, 0, 0, 240, 320);
                c.c(aY, 1169, 120, 160, 33, 0xFFFFFF, 0xFFFFFF);
                c.c(aY, 1142, 4, i.aI, 20, 0xFFFFFF, 0xFFFFFF);
                c.c(aY, 1143, 236, i.aI, 24, 0xFFFFFF, 0xFFFFFF);
                return;
            }
            case 5: {
                return;
            }
            case 6: {
                Graphics graphics = aY;
                c.d(graphics, cb, 16);
                c.a(graphics, fv, 120, 40, 17, 0xFFFFFF, 0, 9, 0, 0);
                return;
            }
            case 7: {
                Graphics graphics = aY;
                if (u == null) return;
                u.a(graphics);
                return;
            }
            case 10: {
                return;
            }
            case 8: {
                Graphics graphics = aY;
                c.d(graphics, cb, 20);
                c.a(graphics, fv, 120, 40, 17, 0xFFFFFF, 0, 9, 0, 0);
                return;
            }
            case 11: {
                Graphics graphics = aY;
                int n2 = cd;
                switch (n2) {
                    case 1: {
                        c.e(graphics);
                        c.k(graphics);
                        c.a(graphics, dJ);
                        return;
                    }
                    case 0: {
                        c.e(graphics);
                        Graphics graphics2 = graphics;
                        if (bc[0] == 1 || bc[0] == 3) {
                            c.a(graphics2, ba[0] + "/" + bb[0], 237, 3, 24, 65280, 0);
                        }
                        if (a != null) {
                            c.b(graphics2, 49, 0, 320, 2);
                            c.b(graphics2, 23, 240, 320, 2);
                            if (ct >= 0) {
                                cq.b(graphics2, 0, 0);
                            }
                        }
                        c.k(graphics);
                        return;
                    }
                    case 2: {
                        n2 = Math.min(cb, 8);
                        c.a(graphics, 0, 0, 0, n2 * 240 / 8, 320);
                        return;
                    }
                    case 3: {
                        Graphics graphics3 = graphics;
                        a a2 = eG;
                        c.b(graphics3, (int)N, (int)O);
                        c.c(graphics3, (int)N, (int)O);
                        switch (eL) {
                            case 6: {
                                c.g(graphics3);
                                break;
                            }
                            case 0: {
                                break;
                            }
                            case 11: {
                                break;
                            }
                            case 10: {
                                Graphics graphics4 = graphics3;
                                if (c.eN[c.eF].l) break;
                                eN[eF].b(graphics4, 0, 0);
                                break;
                            }
                            case 1: {
                                if ((fc & 8) == 0) break;
                                cq.b(graphics3, (int)a2.t, a2.u - a2.F[27] + -20 + (int)bF % 2, 55);
                                break;
                            }
                            case 2: {
                                c.i(graphics3);
                                break;
                            }
                            case 5: {
                                c.h(graphics3);
                                break;
                            }
                            case 3: {
                                break;
                            }
                            case 4: {
                                Graphics graphics5 = graphics3;
                                c.a(graphics5, 1);
                            }
                        }
                        c.j(graphics3);
                        if (t == null) return;
                        t.a(graphics3);
                        return;
                    }
                    case 5: {
                        c.l(graphics);
                        c.k(graphics);
                        return;
                    }
                    case 4: {
                        if (dl) {
                            c.e(graphics);
                            dl = false;
                        }
                        if (t == null) return;
                        t.a(graphics);
                        c.m(graphics);
                        return;
                    }
                    case 6: {
                        n2 = Math.min(cb, 6);
                        c.a(graphics, 0, 0, 0, n2 * 240 / 6, 320);
                        return;
                    }
                    case 7: {
                        c.e(graphics);
                        c.k(graphics);
                    }
                    default: {
                        return;
                    }
                }
            }
            case 13: {
                c.a(aY, 0, 0, 0, 240, 320);
                return;
            }
            case 14: {
                Graphics graphics = aY;
                c.a(graphics, 0xFF0000, 0, 0, 240, 320);
                c.a(graphics, 1178, 120, 160, 33, 0xFFFFFF, 0, 8, 0, 0);
                c.c(graphics, 1193, 5, 315, 36, 0xFFFFFF, 0);
                return;
            }
            case 12: {
                return;
            }
            case 18: {
                Graphics graphics = aY;
                c.a(graphics, 0, 0, 0, 240, 320);
                if (cZ[0] != null) {
                    graphics.drawImage(cZ[0], 120, 30, 17);
                    graphics.drawImage(db, 120, 170, 3);
                }
                if (bF <= 15L) return;
                c.a(graphics, "\u662f\u5426\u5f00\u542f\u97f3\u6548", 120, 280, 33, 0xFFFFFF, 0xFFFFFF);
                graphics.drawImage(cZ[1], 0, 280, 20);
                graphics.drawImage(cZ[2], 5, 290, 20);
                graphics.drawImage(cZ[3], 235, 290, 24);
                return;
            }
            case 19: {
                Graphics graphics = aY;
                c.a(graphics, 0, 0, 0, 240, 320);
                if (dc == null) return;
                graphics.drawImage(dc, 32, 56, 20);
                if (cb < 20) {
                    graphics.drawImage(db, 68, 156, 3);
                    graphics.drawImage(da[0], 120, 156, 3);
                    graphics.drawImage(da[1], 172, 156, 3);
                    return;
                }
                if (cb < 40) {
                    if (cb == 20) {
                        graphics.drawImage(da[6], 68, 156, 3);
                    } else {
                        graphics.drawImage(da[4 + cb % 2], 42, 128, 20);
                    }
                    graphics.drawImage(da[4 + cb % 2], 94, 128, 20);
                    graphics.drawImage(da[4 + cb % 2], 146, 128, 20);
                    return;
                }
                if (cb == 40) {
                    graphics.drawImage(da[6], 120, 156, 3);
                } else {
                    graphics.drawImage(db, 120, 156, 3);
                }
                graphics.drawImage(da[2], 68, 156, 3);
                graphics.drawImage(da[3], 172, 156, 3);
                return;
            }
            case 20: {
                switch (j) {
                    case 0: {
                        c.a(aY, 0, 0, 0, 240, 320);
                        c.a(aY, "\u60a8\u5f53\u524d\u7684\u79ef\u5206\uff1a", 120, 160 - i.R, 33, 0xFFFFFF, 0xFFFFFF);
                        c.a(aY, f.b + "\u5206", 120, 160, 33, 0xFFFFFF, 0xFFFFFF);
                        c.a(aY, "\u662f\u5426\u63d0\u4ea4\u6210\u7ee9\uff1f", 120, 160 + i.R, 33, 0xFFFFFF, 0xFFFFFF);
                        c.a(aY, "\u662f", 4, i.aI, 20, 0xFFFFFF, 0xFFFFFF);
                        c.a(aY, "\u5426", 236, i.aI, 24, 0xFFFFFF, 0xFFFFFF);
                        return;
                    }
                    case 1: {
                        c.a(aY, 0, 0, 0, 240, 320);
                        c.a(aY, "\u63d0\u4ea4\u4e2d\u2026\u2026", 120, 160, 33, 0xFFFFFF, 0xFFFFFF);
                        return;
                    }
                    case 2: {
                        c.a(aY, 0, 0, 0, 240, 320);
                        c.a(aY, f.c[1], 120, 160, 33, 0xFFFFFF, 0xFFFFFF, 12, 0, 0);
                        c.a(aY, "\u8fd4\u56de", 236, i.aI, 24, 0xFFFFFF, 0xFFFFFF);
                        return;
                    }
                    case 3: {
                        c.a(aY, 0, 0, 0, 240, 320);
                        c.a(aY, f.c[1], 120, 160, 33, 0xFFFFFF, 0xFFFFFF, 12, 0, 0);
                        c.a(aY, "\u8fd4\u56de", 236, i.aI, 24, 0xFFFFFF, 0xFFFFFF);
                    }
                }
                return;
            }
            case 21: {
                c.p();
            }
        }
    }

    private static void a(byte by2) {
        ce = cd;
        cd = by2;
        cb = 0;
    }

    private static void b(byte by2) {
        cc = by2;
        switch (cc) {
            case 6: 
            case 8: 
            case 9: {
                fv = c.c(1183, 1189);
                for (by2 = 0; by2 < 3; by2 = (byte)(by2 + 1)) {
                    cu[by2].c(by2);
                }
                break;
            }
            case 16: {
                c.au();
                for (by2 = 0; by2 < 20; by2 = (byte)(by2 + 1)) {
                    c.aW[by2] = 0;
                    c.aX[by2] = 0;
                }
                f = false;
                break;
            }
            case 17: {
                dk = true;
            }
        }
        cb = 0;
    }

    protected final void hideNotify() {
        f = true;
    }

    protected final void showNotify() {
        f = false;
    }

    private static void r() {
        ca = false;
        IMARPG22.a.destroyApp(true);
    }

    public final void run() {
        while (ca) {
            dh = System.currentTimeMillis();
            if (!f) {
                this.repaint();
                this.serviceRepaints();
            } else if (!dk) {
                if (cc != 16) {
                    dj = cc;
                }
                c.b((byte)16);
            }
            System.currentTimeMillis();
            while (System.currentTimeMillis() - di < 50L) {
                Thread.yield();
            }
        }
    }

    private static void s() {
        if (c.b(524288)) {
            c.b(dj);
            if (cd == 4 || cd == 3) {
                dl = true;
            }
            if ((k & 1) != 0) {
                c.C(fz);
                return;
            }
            c.au();
        }
    }

    private static void t() {
        try {
            bZ = new DataInputStream("".getClass().getResourceAsStream("/anim.bin"));
            a.a = new short[122][];
            a.b = new short[122][];
            a.c = new short[122][];
            a.e = new short[122][];
            a.d = new byte[122][];
            a.f = new byte[122][];
            a.g = new byte[122][];
            while (c.b(bZ)) {
            }
            bZ.close();
            bZ = null;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
        }
        try {
            cv = Image.createImage((String)"/LOAD");
        }
        catch (IOException iOException) {}
        if (cu == null) {
            cu = new a[3];
            int n2 = 0;
            for (int i2 = 0; i2 <= 2; ++i2) {
                c.cu[n2] = new a(10, 10, i2, cv, false);
                cu[n2].b(0, 0);
                ++n2;
            }
        }
    }

    private static void u() {
        try {
            System.out.println("s_loadProgress=" + cb);
            switch (cb) {
                case 0: {
                    break;
                }
                case 1: {
                    b.a();
                    g.b();
                    h.f();
                    if (dC == null) {
                        dC = new int[2][12];
                    }
                    if (eK == null) {
                        eK = new int[4];
                    }
                    c.m(2);
                    if (u != null) break;
                    u = h.g();
                    break;
                }
                case 2: {
                    for (int i2 = 0; i2 < 15; ++i2) {
                        if (d[i2] != null) continue;
                        c.d[i2] = b.b();
                        c.d[i2].d = -100;
                    }
                    if (e == null) {
                        e = b.b();
                        b.b().d = -100;
                    }
                    if (cl != null) break;
                    cl = new Image[122];
                    break;
                }
                case 3: {
                    if (cn == null) {
                        cn = Image.createImage((String)"/png7");
                        cq = new a(7, 7, -1, cn, true);
                        aT = new a(7, 7, 2, cn, true);
                        aU = new a(7, 7, 6, cn, true);
                        c = new a(7, 7, 7, cn, true);
                    }
                    h.a(cq);
                    break;
                }
                case 4: {
                    fM = new String[10240];
                    fK = 0;
                    fL = 0;
                    c.a("/string.pak");
                    c.g(0);
                    c.c(dw);
                    fK = fL;
                    c.A();
                    break;
                }
                case 5: {
                    if (fr == null) {
                        fr = Image.createImage((String)"/png9");
                        fs = new a(9, 9, -1, fr, true);
                    }
                    if (co != null) break;
                    co = Image.createImage((String)"/png8");
                    cr = new a(8, 8, -1, co, true);
                    break;
                }
                case 6: {
                    if (cG != null) break;
                    c.a("/portrait.pak");
                    cG = new a[dv];
                    c.A();
                    break;
                }
                case 7: {
                    c.am();
                    System.out.println("\u8f7d\u5165DB\u6570\u636e\u5e93");
                    break;
                }
                case 8: {
                    c.at();
                    break;
                }
                case 10: {
                    c.a(false, -1);
                    break;
                }
                case 12: {
                    bX = 0;
                    bW = 240;
                    bS = 256;
                    bV = 320;
                    bT = (bV / 16 << 4) + 32;
                    break;
                }
                case 13: {
                    if (g != null) break;
                    g = new a[4];
                    for (int i3 = 0; i3 < 4; ++i3) {
                        c.g[i3] = new a(i3 + 0, i3 + 0, -1);
                        a a2 = g[i3];
                        g[i3].L = new a(61, 61, -1);
                        a2.F = new int[32];
                        a2.H = new int[71];
                        a2.G = new int[i.aJ.length];
                        a2.I = new int[9];
                        a2.J = b.b();
                        a2.J.d = -1;
                        a2.J.e = -1;
                        a2.K = b.b();
                        a2.K.d = -1;
                        a2.K.e = -1;
                        int n2 = cN[i3][2];
                        int n3 = cN[i3][1];
                        a2.r[0] = (short)(-(n3 >> 1));
                        a2.r[1] = (short)(-(n2 >> 1));
                        a2.r[2] = (short)n3;
                        a2.r[3] = (short)n2;
                    }
                    break;
                }
                case 14: {
                    for (int i4 = 0; i4 < fk.length; ++i4) {
                        c.fk[i4] = new a(7, 7, 0, cn, false);
                    }
                    break;
                }
                case 15: {
                    cz = new a[8];
                    break;
                }
                case 16: {
                    c.b((byte)6);
                }
            }
            ++cb;
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    static void a(h h2) {
        int n2 = m[h2.g];
        switch (n2) {
            case 0: {
                if (c.b(4112)) {
                    if ((k & 1) == 0) {
                        k |= 1;
                        c.C(fz);
                        break;
                    }
                    k &= 0xFFFFFFFE;
                    c.au();
                    break;
                }
                if (!c.b(1032)) break;
                if ((k & 1) == 1) {
                    k &= 0xFFFFFFFE;
                    c.au();
                    break;
                }
                k |= 1;
                c.C(fz);
                break;
            }
            case 3: {
                if (c.b(4112)) {
                    k &= 0xFFFFFFFD;
                    break;
                }
                if (!c.b(1032)) break;
                k |= 2;
                break;
            }
            case 1: {
                if (c.b(4112)) {
                    k &= 0xFFFFFFFD;
                    break;
                }
                if (!c.b(1032)) break;
                k |= 2;
            }
        }
        if (h2.w >= 0 || h2.j == -1) {
            h2.j = -1;
            h2.b();
        }
    }

    static void a(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        int n6 = n3 + 51;
        int n7 = m[h2.g];
        c.a(graphics, 13022093, n2 + 8, n3 + 21, 160, 164, 3, 4, 5);
        c.b(graphics, n2, n3, n4, n5, 30);
        for (n5 = 0; n5 < h2.u; ++n5) {
            cq.b(graphics, n2 + 42, n6, n5 + 62);
            cq.b(graphics, n2 + 93, n6, n5 + 65);
            cq.b(graphics, n2 + 128, n6, n5 + 68);
            cq.b(graphics, n2 + ((k & 1 << n5) == 0 ? 128 : 93), n6 + -11, 120);
            n6 += 30;
        }
        cq.b(graphics, n2 + 52, n3 + 55 + n7 * 30, 0);
        c.a(graphics, 1302, n2 + (n4 >> 1), n3 + 130, 17, 0, 0, 8, 0, 0);
    }

    static void b(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        int n6 = h2.t[1] + (i.R << 1);
        c.a(graphics, 0xFFFFFF, n2 + 4, n3 + 24, n4 + -8, n5 + -48, 3, 4, 5);
        c.c(graphics, h2.i, 120, n3 + 5, 17, 0xFFFFFF, 0);
        for (n2 = 1243; n2 < 1249 && n2 <= 1255; ++n2) {
            c.c(graphics, n2 + m[h2.g], 120, n6, 17, 0, 0);
            n6 += i.R;
        }
        if (m[h2.g] > 0) {
            cq.a(graphics, 70, 103, h2.t[1] + h2.t[3] - 19, 3);
        }
        if (m[h2.g] < 7) {
            cq.a(graphics, 70, 121, h2.t[1] + h2.t[3] - 19, 0);
        }
    }

    static void b(h h2) {
        c.a(h2.g, 8, 16388, 258, 0, false);
        if (c.b(524288)) {
            h2.j = -1;
            h2.b();
        }
    }

    static void c(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        c.a(graphics, 0xFFFFFF, n2 + 4, n3 + 24, n4 + -8, n5 + -48, 3, 4, 5);
        c.c(graphics, h2.i, 120, n3 + 5, 17, 0xFFFFFF, 0);
        n2 = h2.t[1] + (i.R << 1);
        for (n3 = 1216; n3 < 1222 && n3 <= 1240; ++n3) {
            c.c(graphics, n3 + m[h2.g], 120, n2, 17, 0, 0);
            n2 += i.R;
        }
        if (m[h2.g] > 0) {
            cq.a(graphics, 70, 103, h2.t[1] + h2.t[3] - 19, 3);
        }
        if (m[h2.g] < 19) {
            cq.a(graphics, 70, 121, h2.t[1] + h2.t[3] - 19, 0);
        }
    }

    static void c(h h2) {
        c.a(h2.g, 20, 16388, 258, 0, false);
        if (c.b(524288)) {
            h2.j = -1;
            h2.b();
        }
    }

    private static void v() {
        int n2;
        int n3;
        bZ = new DataInputStream("".getClass().getResourceAsStream("/map" + ch + ".bin"));
        bG = c.a(bZ, 0);
        bR = bG - 1;
        bH = new byte[bG];
        bN = new Image[bG];
        bO = new int[bG];
        bP = new short[bG][];
        bQ = new byte[bG][];
        for (n3 = 0; n3 < bG; ++n3) {
            c.bH[n3] = (byte)c.a(bZ, 0);
        }
        bI = c.a(bZ, 1);
        bJ = c.a(bZ, 1);
        for (n3 = 0; n3 < bG; ++n3) {
            c.bP[n3] = new short[bI * bJ];
            for (n2 = 0; n2 < bI * bJ; ++n2) {
                c.bP[n3][n2] = (short)c.a(bZ, 1);
            }
        }
        for (n3 = 0; n3 < bG; ++n3) {
            n2 = (bI * bJ >> 2) + (bI * bJ % 4 == 0 ? 0 : 1);
            c.bQ[n3] = new byte[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                c.bQ[n3][i2] = (byte)c.a(bZ, 0);
            }
        }
        try {
            bZ.close();
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
        }
        bZ = null;
        System.gc();
    }

    private static void w() {
        int n2;
        bH = null;
        bO = null;
        bP = new short[bG][];
        bQ = new byte[bG][];
        if (bN != null) {
            for (n2 = 0; n2 < bG; ++n2) {
                c.bN[n2] = null;
            }
            bN = null;
        }
        if (bP != null) {
            for (n2 = 0; n2 < bP.length; ++n2) {
                c.bP[n2] = null;
            }
            bP = null;
        }
        if (bQ != null) {
            for (n2 = 0; n2 < bQ.length; ++n2) {
                c.bQ[n2] = null;
            }
            bQ = null;
        }
        System.gc();
    }

    private static void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        int n6 = 0;
        int n7 = ((n2 << 4) + bS) % bS;
        int n8 = ((n3 << 4) + bT) % bT;
        for (int i2 = n3; i2 <= n5; ++i2) {
            n3 = n7;
            for (int i3 = n2; i3 <= n4; ++i3) {
                int n9 = i3 < 0 || i3 >= bI || i2 < 0 || i2 >= bJ ? -1 : i2 * bI + i3;
                if (n9 >= 0) {
                    for (int i4 = 0; i4 < bG - 2; ++i4) {
                        int n10 = bP[i4][n9];
                        if (n10 < 0) continue;
                        n6 = bO[i4];
                        int n11 = (n10 &= 0xFFF) / n6 << 4;
                        n10 = n10 % n6 << 4;
                        n6 = bQ[i4][n9 / 4] >> (3 - n9 % 4 << 1) & 3;
                        graphics.setClip(n3, n8, 16, 16);
                        graphics.drawRegion(bN[i4], n10, n11, 16, 16, n6, n3, n8, 0);
                    }
                }
                if ((n3 += 16) < bS) continue;
                n3 -= bS;
            }
            if ((n8 += 16) < bT) continue;
            n8 -= bT;
        }
    }

    private static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7) {
        int n8 = 0;
        int n9 = 0;
        int n10 = 0 + cL;
        int n11 = 0 + cM;
        n4 = n6 + n4;
        n5 = n7 + n5;
        if (n6 > 0) {
            n8 = n6;
        }
        if (n7 > 0) {
            n9 = n7;
        }
        if (n10 > n4) {
            n10 = n4;
        }
        if (n11 > n5) {
            n11 = n5;
        }
        graphics.setClip(n8, n9, n10 -= n8, n11 -= n9);
        graphics.drawImage(bL, n6 - n2, n7 - n3, 20);
    }

    private static int f(int n2, int n3) {
        int n4 = 16;
        n4 = n2;
        n2 = n4 /= 16;
        n4 = 16;
        n4 = n3;
        n3 = n4 /= 16;
        if ((n2 = n4 * bI + n2) < bP[bR].length) {
            return bP[bR][n2];
        }
        return -1;
    }

    private static int d(int n2) {
        if (n2 >= 0 && n2 < bP[bR].length) {
            return bP[bR][n2];
        }
        return -1;
    }

    private static int d(int n2, int n3, int n4, int n5) {
        if (n2 + n4 < 0 || n3 + n5 < 0 || n2 + n4 >= bI || n3 + n5 >= bJ || n2 < 0 || n2 >= bI || n3 < 0 || n3 >= bJ) {
            return -1;
        }
        n2 = (n3 + n5) * bI + n2 + n4;
        return n2;
    }

    private static int a(int n2, int n3, int n4, boolean bl2) {
        int n5 = Integer.MAX_VALUE;
        int n6 = n2 / 16;
        int n7 = n3 / 16;
        int n8 = -1;
        int n9 = -1;
        switch (n4) {
            case 0: {
                if (c.d(c.d(n6, n7, 0, -1)) < 0) {
                    return -1;
                }
                if (bl2) {
                    if (c.d(c.d(n6, n7, 1, 0)) >= 0) {
                        return Integer.MAX_VALUE;
                    }
                    n8 = n6 + 1;
                    n9 = n7 - 1;
                } else {
                    if (c.d(c.d(n6, n7, -1, 0)) >= 0) {
                        return Integer.MAX_VALUE;
                    }
                    n8 = n6 - 1;
                    n9 = n7 - 1;
                }
                n5 = Math.abs(n3 - (n9 << 4));
                break;
            }
            case 1: {
                if (c.d(c.d(n6, n7, 1, 0)) < 0) {
                    return -1;
                }
                if (bl2) {
                    if (c.d(c.d(n6, n7, 0, 1)) >= 0) {
                        return Integer.MAX_VALUE;
                    }
                    n8 = n6 + 1;
                    n9 = n7 + 1;
                } else {
                    if (c.d(c.d(n6, n7, 0, -1)) >= 0) {
                        return Integer.MAX_VALUE;
                    }
                    n8 = n6 + 1;
                    n9 = n7 - 1;
                }
                n5 = Math.abs(n2 - (n8 << 4));
                break;
            }
            case 2: {
                if (c.d(c.d(n6, n7, 0, 1)) < 0) {
                    return -1;
                }
                if (bl2) {
                    if (c.d(c.d(n6, n7, -1, 0)) >= 0) {
                        return Integer.MAX_VALUE;
                    }
                    n8 = n6 - 1;
                    n9 = n7 + 1;
                } else {
                    if (c.d(c.d(n6, n7, 1, 0)) >= 0) {
                        return Integer.MAX_VALUE;
                    }
                    n8 = n6 + 1;
                    n9 = n7 + 1;
                }
                n5 = Math.abs(n3 - (n9 << 4));
                break;
            }
            case 3: {
                if (c.d(c.d(n6, n7, -1, 0)) < 0) {
                    return -1;
                }
                if (bl2) {
                    if (c.d(c.d(n6, n7, 0, -1)) >= 0) {
                        return Integer.MAX_VALUE;
                    }
                    n8 = n6 - 1;
                    n9 = n7 - 1;
                } else {
                    if (c.d(c.d(n6, n7, 0, 1)) >= 0) {
                        return Integer.MAX_VALUE;
                    }
                    n8 = n6 - 1;
                    n9 = n7 + 1;
                }
                n5 = Math.abs(n2 - (n8 << 4));
            }
        }
        n2 = n9 * bI + n8;
        if (c.d(n2) >= 0) {
            n5 = Integer.MAX_VALUE;
        }
        return n5;
    }

    static int a(int n2, int n3, int n4, int n5, int n6) {
        int n7 = (n6 + 1) % 4;
        int n8 = (n6 + 3) % 4;
        int n9 = -1;
        int n10 = -1;
        int n11 = -1;
        int n12 = -1;
        switch (n6) {
            case 2: {
                n9 = n2;
                n10 = n3 + n5;
                n11 = n2 + n4;
                n12 = n10;
                break;
            }
            case 0: {
                n9 = n2 + n4;
                n10 = n3;
                n11 = n2;
                n12 = n3;
                break;
            }
            case 3: {
                n9 = n2;
                n10 = n3;
                n11 = n2;
                n12 = n3 + n5;
                break;
            }
            case 1: {
                n9 = n2 + n4;
                n10 = n3 + n5;
                n11 = n9;
                n12 = n3;
            }
        }
        n2 = c.a(n9, n10, n6, true);
        n3 = c.a(n11, n12, n6, false);
        if (n2 >= 17 && n3 >= 17) {
            return -1;
        }
        if (n2 <= n3) {
            return n7;
        }
        return n8;
    }

    static boolean a(int n2, int n3) {
        int n4 = bI << 4;
        int n5 = bJ << 4;
        return n2 <= 0 || n3 <= 0 || n2 >= n4 || n3 >= n5;
    }

    private static void x() {
        int n2;
        if (aP != null) {
            c.aP.E[4] = 0;
            aP = null;
        }
        cw = 0;
        cx = 0;
        dA = null;
        dB = null;
        for (n2 = 0; n2 < cy.length; ++n2) {
            c.cy[n2] = null;
        }
        if (cz != null) {
            for (n2 = 0; n2 < cz.length; ++n2) {
                short s2;
                if (cz[n2] == null || (s2 = c.cz[n2].E[16]) < 0) continue;
                c.u(n2);
            }
        }
        if (ck != null) {
            for (n2 = 0; n2 < ck.length; ++n2) {
                if (null == ck[n2]) continue;
                ck[n2].a();
                c.ck[n2] = null;
            }
            ck = null;
        }
        for (n2 = 0; n2 < cG.length; ++n2) {
            if (cG[n2] == null) continue;
            cG[n2].a();
            c.cG[n2] = null;
        }
        if (t != null) {
            t.h();
            t = null;
        }
        System.gc();
    }

    private static void y() {
        for (int i2 = 0; i2 < 4; ++i2) {
            c.cl[i2] = null;
            c.g[i2].B = null;
        }
        System.gc();
    }

    private static void z() {
        int n2;
        c.m(1);
        P = -16777216;
        aK = -16777216;
        aL = -1;
        aM = 0;
        z = 0;
        A = 0;
        B = 0;
        C = 0;
        for (n2 = 0; n2 < ck.length; ++n2) {
            c.ck[n2].E[1] = 1;
        }
        for (n2 = 0; n2 < 4; ++n2) {
            c.g[n2].l = false;
            c.g[n2].k = true;
            c.g[n2].E[2] = 0;
            c.g[n2].E[16] = (short)(-(n2 + 1));
        }
    }

    private static void e(Graphics graphics) {
        int n2;
        int n3;
        if (fj) {
            c.a(aY, 0, 0, 0, 240, 320);
        }
        Graphics graphics2 = graphics;
        int n4 = D / 16;
        if (D < 0 && D % 16 != 0) {
            --n4;
        }
        int n5 = n4 + 15;
        int n6 = E / 16;
        if (E < 0 && E % 16 != 0) {
            --n6;
        }
        int n7 = n6 + bV / 16 + 1;
        if (bK) {
            bK = false;
            c.a(bM, n4, n6, n5, n7);
            do = n4;
            dq = n6;
            dp = n5;
            dr = n7;
        }
        if (do != n4) {
            if (do < n4) {
                n3 = dp + 1;
                n2 = n5;
            } else {
                n3 = n4;
                n2 = do - 1;
            }
            c.a(bM, n3, n6, n2, n7);
            do = n4;
            dp = n5;
        }
        if (dq != n6) {
            if (dq < n6) {
                n3 = dr + 1;
                n2 = n7;
            } else {
                n3 = n6;
                n2 = dq - 1;
            }
            c.a(bM, n4, n3, n5, n2);
            dq = n6;
            dr = n7;
        }
        n6 = (D + bS) % bS;
        n5 = (E + bT) % bT;
        int n8 = (D + 240) % bS;
        int n9 = (E + bV) % bT;
        if (n8 > n6) {
            if (n9 > n5) {
                c.a(graphics2, n6, n5, 240, bV, 0, 0);
            } else {
                c.a(graphics2, n6, n5, 240, bV - n9, 0, 0);
                c.a(graphics2, n6, 0, 240, n9, 0, 0 + bV - n9);
            }
        } else if (n9 > n5) {
            c.a(graphics2, n6, n5, 240 - n8, bV, 0, 0);
            c.a(graphics2, 0, n5, n8, bV, 240 - n8, 0);
        } else {
            c.a(graphics2, n6, n5, 240 - n8, bV - n9, 0, 0);
            c.a(graphics2, n6, 0, 240 - n8, n9, 0, 0 + bV - n9);
            c.a(graphics2, 0, n5, n8, bV - n9, 240 - n8, 0);
            c.a(graphics2, 0, 0, n8, n9, 240 - n8, 0 + bV - n9);
        }
        graphics2 = graphics;
        graphics2.setClip(0, 0, 240, 320);
        graphics2 = graphics;
        n6 = 0;
        graphics2.setClip(0, 0, 240, 320);
        c.g(0, cw - 1);
        for (n5 = 0; n5 < cw; ++n5) {
            a a2 = cy[n5];
            n6 = a2.E[2];
            if (n6 >= 0) {
                ez.b(graphics2, a2.v - D, a2.w - E, n6 + 0);
            }
            a2.b(graphics2, D, E);
            a2.a(graphics2, (int)D, (int)E);
        }
        graphics2 = graphics;
        if (bO != null) {
            n4 = bO[bG - 2];
            n8 = D / 16;
            n5 = E / 16;
            n9 = c.b(D + 240, 16);
            int n10 = c.b(E + 320, 16);
            if (ds) {
                for (int i2 = n5; i2 <= n10; ++i2) {
                    n7 = (i2 << 4) - E;
                    for (int i3 = n8; i3 <= n9; ++i3) {
                        n6 = i3 < 0 || i3 >= bI || i2 < 0 || i2 >= bJ ? -1 : i2 * bI + i3;
                        if (n6 < 0 || bP[bG - 2][n6] < 0) continue;
                        n5 = (i3 << 4) - D;
                        n3 = bQ[bG - 2][n6 / 4] >> (3 - n6 % 4 << 1) & 3;
                        n2 = bP[bG - 2][n6] % n4 << 4;
                        n6 = bP[bG - 2][n6] / n4 << 4;
                        graphics2.setClip(n5, n7, 16, 16);
                        graphics2.drawRegion(bN[bG - 2], n2, n6, 16, 16, n3, n5, n7, 0);
                    }
                }
            }
            graphics2.setClip(0, 0, 240, 320);
        }
        graphics2 = graphics;
        n4 = 0;
        if (cx > 0) {
            c.g(128, 128 + cx - 1);
            for (n5 = 0; n5 < cx; ++n5) {
                a a3 = cy[n5 + 128];
                n4 = a3.E[2];
                if (n4 >= 0) {
                    ez.b(graphics2, a3.v - D, a3.w - E, n4 + 0);
                }
                a3.b(graphics2, D, E);
                a3.a(graphics2, (int)D, (int)E);
            }
        }
        if (z > 0) {
            if (A >= C) {
                c.a(graphics, B, 0, 0, 240, 320);
                --z;
                A = 0;
                return;
            }
            ++A;
        }
    }

    protected final void keyPressed(int n2) {
        if (aX[n2 = c.e(n2)] <= 0) {
            if (aX[n2] < 0) {
                c.aX[n2] = 0;
            }
            if (aX[n2] < 126) {
                int n3 = n2;
                aX[n3] = (byte)(aX[n3] + 1);
            }
        }
    }

    protected final void keyRepeated(int n2) {
    }

    protected final void keyReleased(int n2) {
        if (aX[n2 = c.e(n2)] > 0) {
            int n3 = n2;
            aX[n3] = -aX[n3];
        }
    }

    private static int e(int n2) {
        if ((n2 = Math.abs(n2)) == 1) {
            return 1;
        }
        if (n2 == 3) {
            return 3;
        }
        if (n2 == 4) {
            return 4;
        }
        if (n2 == 2) {
            return 2;
        }
        if (n2 == 35) {
            return 17;
        }
        if (n2 == 42) {
            return 16;
        }
        if (n2 == 48) {
            return 6;
        }
        if (n2 == 49) {
            return 7;
        }
        if (n2 == 50) {
            return 8;
        }
        if (n2 == 51) {
            return 9;
        }
        if (n2 == 52) {
            return 10;
        }
        if (n2 == 53) {
            return 11;
        }
        if (n2 == 54) {
            return 12;
        }
        if (n2 == 55) {
            return 13;
        }
        if (n2 == 56) {
            return 14;
        }
        if (n2 == 57) {
            return 15;
        }
        if (n2 == 6) {
            return 18;
        }
        if (n2 == 7) {
            return 19;
        }
        if (n2 == 5) {
            return 5;
        }
        return 0;
    }

    static final boolean a(int n2) {
        for (int i2 = 1; i2 < 20; ++i2) {
            if ((n2 & 1 << i2) == 0 || aW[i2] <= 0) continue;
            return true;
        }
        return false;
    }

    static final boolean b(int n2) {
        for (int i2 = 1; i2 < 20; ++i2) {
            if ((n2 & 1 << i2) == 0 || aW[i2] != 1) continue;
            return true;
        }
        return false;
    }

    private static void a(a a2) {
        if (a2.E[15] == 0) {
            c.cy[c.cw] = a2;
            ++cw;
            return;
        }
        c.cy[128 + c.cx] = a2;
        ++cx;
    }

    private static void g(int n2, int n3) {
        while (n2 < n3) {
            c.h(n2, n2 + n3 >> 1);
            int n4 = n2;
            for (int i2 = n2 + 1; i2 <= n3; ++i2) {
                short s2 = c.cy[n2].w;
                short s3 = c.cy[n2].E[5];
                short s4 = c.cy[i2].w;
                short s5 = c.cy[i2].E[5];
                if ((s5 < s3 ? -1 : (s5 > s3 ? 1 : (s4 > s2 ? 1 : (s4 <= s2 ? -1 : 0)))) != -1) continue;
                c.h(++n4, i2);
            }
            c.h(n2, n4);
            c.g(n2, n4 - 1);
            n2 = n4 + 1;
        }
        return;
    }

    private static void h(int n2, int n3) {
        a a2 = cy[n2];
        c.cy[n2] = cy[n3];
        c.cy[n3] = a2;
    }

    private static void a(String string) {
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        dx = 0L;
        try {
            dw = new DataInputStream("".getClass().getResourceAsStream(string));
            dv = dw.readByte();
            ++dx;
            ++n4;
            du = new int[dv + 1];
            for (int i2 = 0; i2 < dv; ++i2) {
                n3 = 0;
                do {
                    n2 = dw.readUnsignedByte() & 0xFF;
                    ++n4;
                    ++dx;
                    int n5 = i2 + 1;
                    du[n5] = du[n5] + ((n2 & 0x7F) << n3 * 7);
                    ++n3;
                } while ((n2 & 0x80) != 0);
            }
            c.du[0] = n4;
            dt = string;
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    private static byte[] f(int n2) {
        if (!c.g(n2)) {
            return null;
        }
        n2 = du[n2 + 1];
        byte[] byArray = new byte[n2];
        try {
            dw.read(byArray);
            dx += (long)n2;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
        }
        return byArray;
    }

    private static boolean g(int n2) {
        if (n2 >= dv) {
            return false;
        }
        long l2 = 0L;
        for (int i2 = 0; i2 < n2 + 1; ++i2) {
            l2 += (long)du[i2];
        }
        try {
            if (l2 != dx) {
                if (l2 - dx > 0L) {
                    dw.skip(l2 - dx);
                    dx += l2 - dx;
                } else {
                    c.A();
                    c.a(dt);
                    dw.skip(l2);
                    dx = l2;
                }
            }
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return false;
        }
        return true;
    }

    private static void A() {
        try {
            dw.close();
            dw = null;
            dv = 0;
            du = null;
            System.gc();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    private static void e(int n2, int n3, int n4, int n5) {
        c.bn[n2] = 2;
        c.bq[n2] = n3;
        c.br[n2] = 0;
        c.bs[n2] = 0;
    }

    private static int i(int n2, int n3) {
        int n4 = Integer.MAX_VALUE;
        int n5 = bm[n2] + 1;
        n5 = bt[n2] + n5;
        int n6 = n2;
        n6 = n6 == bl - 1 ? bw.length - bt[n6] : bt[n6 + 1] - bt[n6];
        n2 = bt[n2] + n6;
        n6 = 0;
        while (n5 < n2) {
            if (bw[n5] == n3) {
                if (n6 == 0) {
                    n4 = n5;
                    break;
                }
            } else if (bw[n5] == 0) {
                ++n6;
            }
            if (bw[n5] == 1) {
                --n6;
            }
            ++n5;
        }
        return n4;
    }

    private static boolean h(int n2) {
        a a2 = null;
        Object var4_4 = null;
        int n3 = bm[n2];
        n3 = bt[n2] + n3;
        int n4 = bw[n3];
        int n5 = i.T[n4];
        int n6 = bv[n3];
        if (n4 == Integer.MIN_VALUE) {
            System.out.println("if \u4e0e endif \u6570\u91cf\u4e0d\u5339\u914d");
        }
        for (n3 = 0; n3 < n5; ++n3) {
            c.dm[n3] = bu[n6 + n3];
        }
        switch (n4) {
            case 0: {
                n3 = c.a(dm[0], dm[1], dm[2], dm[3], dm[4], dm[5], dm[6]);
                if (n3 != 1) {
                    n4 = c.i(n2, 15);
                    c.bm[n2] = n4 < (n5 = c.i(n2, 1)) ? n4 - bt[n2] : n5 - bt[n2];
                }
                n6 = 1;
                break;
            }
            case 1: {
                n6 = 1;
                break;
            }
            case 2: {
                c.i(n2);
                n6 = 0;
                break;
            }
            case 3: {
                c.b(dm[0], dm[1], dm[2], dm[3], dm[4]);
                n6 = 1;
                break;
            }
            case 4: {
                c.aZ[2] = dm[0];
                c.aZ[3] = dm[3];
                c.aZ[0] = dm[1];
                c.aZ[1] = dm[2];
                c.i(n2);
                c.aa();
                c.a((byte)5);
                cg = aZ[2];
                n6 = 0;
                break;
            }
            case 5: {
                c.e(dm[0], dm[1], dm[2]);
                n6 = 1;
                break;
            }
            case 6: {
                n3 = dm[0];
                a2 = c.o(n3);
                a2.E[1] = (short)dm[1];
                n6 = 1;
                break;
            }
            case 7: {
                n3 = fK + dm[3];
                n4 = dm[1];
                n5 = dm[2];
                n6 = dm[0];
                int n7 = dm[4];
                int n8 = fK + dm[5];
                c.b(n3, n4, n5, n6, n7, n8);
                if (dA != null) {
                    dA.a(c.dB.v, (int)c.dB.w, 0);
                }
                c.a((byte)1);
                n6 = 0;
                break;
            }
            case 8: {
                c.f(dm[0], dm[1], dm[2]);
                c.a(dm[0], 1, true);
                n6 = 1;
                break;
            }
            case 9: {
                c.e(n2, dm[0], 0, 0);
                n6 = 0;
                break;
            }
            case 10: {
                c.a(3, dm[0], dm[1], null, dm[2]);
                if (dm[2] != 0) {
                    c.bn[n2] = 3;
                    n6 = 0;
                    break;
                }
                n6 = 1;
                break;
            }
            case 11: {
                n3 = dm[0];
                n4 = dm[1];
                a2 = c.o(n3);
                c.a(2, 0, 0, a2, n4);
                if (n4 != 0) {
                    c.bn[n2] = 3;
                    n6 = 0;
                    break;
                }
                n6 = 1;
                break;
            }
            case 12: {
                a2 = c.o(dm[0]);
                n3 = dm[3];
                if (n3 >= 0) {
                    n4 = a2.i % 4;
                    c.a(a2, (n3 << 2) + n4, dm[2] == 1);
                } else {
                    c.a(a2, dm[1], dm[2] == 1);
                }
                n6 = 1;
                break;
            }
            case 13: {
                n3 = dm[0];
                a2 = c.o(n3);
                a2.E[0] = 2;
                n4 = c.b(dm[2], dm[3]);
                switch (dm[1]) {
                    case 2: {
                        a2.C = a2.v;
                        a2.D = a2.w + dm[2];
                        a2.x = 0;
                        a2.y = (short)n4;
                        break;
                    }
                    case 0: {
                        a2.C = a2.v;
                        a2.D = a2.w - dm[2];
                        a2.x = 0;
                        a2.y = (short)(-n4);
                        break;
                    }
                    case 3: {
                        a2.C = a2.v - dm[2];
                        a2.D = a2.w;
                        a2.x = (short)(-n4);
                        a2.y = 0;
                        break;
                    }
                    case 1: {
                        a2.C = a2.v + dm[2];
                        a2.D = a2.w;
                        a2.x = (short)n4;
                        a2.y = 0;
                    }
                }
                n6 = 0;
                break;
            }
            case 14: {
                c.bq[n2] = n3 = c.n(dm[0]);
                c.bn[n2] = 1;
                n6 = 0;
                break;
            }
            case 15: {
                n3 = c.i(n2, 1);
                c.bm[n2] = n3 - bt[n2];
                System.out.println("unhandle cmdType: " + n4);
                n6 = 0;
                break;
            }
            case 16: {
                dK = fK + dm[1];
                dI = dm[0];
                for (n3 = 0; n3 < 5; ++n3) {
                    c.dS[n3] = fK + dm[n3 + 3];
                }
                dT = dm[2];
                c.h(2, 0, 0);
                c.a((byte)1);
                n6 = 0;
                break;
            }
            case 17: {
                n3 = c.p(dm[0]);
                n4 = dm[1];
                n5 = dm[2];
                c.k(n3, n4);
                c.l(n3, n4 == 1 ? n5 : 0);
                c.a();
                n6 = 1;
                break;
            }
            case 18: {
                n4 = dm[1];
                switch (dm[0]) {
                    case 3: {
                        n3 = 0;
                        break;
                    }
                    case 2: {
                        n3 = 1;
                        break;
                    }
                    case 1: {
                        n3 = 3;
                        break;
                    }
                    case 0: {
                        n3 = 2;
                        break;
                    }
                    default: {
                        n3 = -1;
                    }
                }
                n5 = dm[2];
                c.o(n3, n4);
                dn = n5 == 1;
                n6 = n5 == 1 ? 0 : 1;
                break;
            }
            case 19: {
                n3 = c.A(dm[0] & 0xFFFF);
                n4 = c.B(dm[0]);
                c.k(n4, n3, dm[1]);
                n6 = 0;
                break;
            }
            case 20: {
                n3 = dm[0];
                n4 = dm[1];
                n5 = dm[2];
                a2 = g[n3];
                a2.a(n4, n5);
                n6 = 0;
                break;
            }
            case 21: {
                c.j(dm[0], dm[1], dm[2]);
                n6 = 0;
                break;
            }
            case 22: {
                c.b((byte)20);
                n6 = 0;
                break;
            }
            case 23: {
                c.b((byte)14);
                System.out.println("unhandle cmdType: " + n4);
                n6 = 0;
                break;
            }
            case 24: {
                dL = fK + dm[0];
                ef = dm[1];
                c.h(1, 1, 0);
                c.a((byte)1);
                n6 = 0;
                break;
            }
            case 25: {
                n3 = dm[0] & 0xFFFF;
                n4 = c.B(n3);
                n3 = c.A(n3);
                c.ci[c.dm[1]] = c.p(n4, n3);
                n6 = 0;
                break;
            }
            case 26: {
                n3 = dm[3];
                n4 = dm[1];
                n5 = dm[0] == 1 ? 0 : 1;
                b b2 = d[6].c(n3);
                if (n4 == 1) {
                    if (b2 != null) {
                        ++b2.e;
                        f.b += 30;
                    } else {
                        b2 = b.b();
                        b.b().d = n3;
                        b2.c = (byte)n5;
                        b2.e = 1;
                        b2.g = cT[n3][0];
                        b2.f = cT[n3][1];
                        d[6].a(b2);
                    }
                } else if (b2 != null) {
                    b2.g();
                    b2.c();
                }
                n6 = 0;
                break;
            }
            case 27: {
                c.f(dm[0], dm[1], dm[2], 0xFFFF & dm[3]);
                n6 = 0;
                break;
            }
            case 28: {
                c.q(dm[0]);
                n6 = 0;
                break;
            }
            case 29: {
                c.d(dm[0], dm[1], dm[2]);
                n6 = 1;
                break;
            }
            case 30: {
                z = dm[3];
                A = 0;
                B = (dm[0] % 65535 << 16) + dm[1] % 65535;
                C = dm[2];
                n6 = 1;
                break;
            }
            case 31: {
                P = ((0xFFFF & dm[0]) << 16) + (0xFFFF & dm[1]);
                aK = ((0xFFFF & dm[2]) << 16) + (0xFFFF & dm[3]);
                aL = dm[4];
                aM = 0;
                n6 = 1;
                break;
            }
            case 32: {
                n3 = dm[0];
                a2 = c.o(n3);
                a2.E[5] = (short)dm[1];
                n6 = 1;
                break;
            }
            case 33: {
                n3 = dm[0];
                a2 = c.o(n3);
                c.o(n3).x = (short)dm[1];
                a2.y = (short)dm[2];
                a2.z = (short)dm[3];
                a2.A = (short)dm[4];
                n6 = 1;
                break;
            }
            case 34: {
                n3 = dm[0];
                a2 = c.o(n3);
                a2.E[6] = 1;
                a2.E[8] = (short)dm[1];
                a2.E[7] = (short)dm[2];
                switch (a2.E[7]) {
                    case 1: {
                        a2.E[0] = 4;
                        a2.C = a2.v;
                        a2.D = a2.w;
                        break;
                    }
                    case 2: {
                        a2.E[0] = 4;
                        a2.C = a2.v;
                        a2.D = a2.w;
                        break;
                    }
                    case 0: {
                        a2.E[0] = 0;
                        a2.C = a2.v;
                        a2.D = a2.w;
                    }
                }
                a2.t = a2.v;
                a2.u = a2.w;
                n6 = 1;
                break;
            }
            case 36: {
                for (n3 = 0; n3 < ck.length; ++n3) {
                    if (c.ck[n3].E[6] != 1) continue;
                    c.ck[n3].E[1] = (short)dm[0];
                }
                n6 = 1;
                break;
            }
            case 37: {
                n3 = dm[0];
                a2 = c.o(n3);
                a2.E[11] = (short)dm[1];
                a2.E[10] = (short)dm[2];
                a2.L.c(a2.E[11]);
                a2.L.E[1] = 1;
                a2.L.k = true;
                n6 = 1;
                break;
            }
            case 35: {
                c.d(dm[0], dm[1], dm[2]);
                n6 = 1;
                break;
            }
            case 41: {
                c.bB[c.bA] = (byte)n4;
                n3 = bA * 7;
                c.bD[n3] = dm[0];
                c.bD[n3 + 1] = dm[1];
                c.bD[n3 + 2] = dm[2];
                c.bD[n3 + 3] = fK + dm[3];
                c.bD[n3 + 4] = dm[4];
                c.bD[n3 + 5] = dm[5];
                c.bD[n3 + 6] = dm[6];
                c.bC[c.bA] = (short)dm[4];
                ++bA;
                n6 = 1;
                break;
            }
            case 45: {
                c.bB[c.bA] = (byte)n4;
                n3 = bA * 7;
                c.bD[n3] = dm[0];
                c.bD[n3 + 1] = dm[1];
                c.bD[n3 + 2] = dm[2];
                c.bD[n3 + 3] = fK + dm[3];
                c.bD[n3 + 4] = dm[4];
                c.bD[n3 + 5] = dm[5];
                c.bC[c.bA] = (short)dm[4];
                ++bA;
                n6 = 1;
                break;
            }
            case 43: {
                n3 = bA * 7;
                c.bB[c.bA] = (byte)n4;
                c.bD[n3] = dm[0];
                c.bD[n3 + 2] = dm[2];
                c.bD[n3 + 1] = dm[1];
                c.bD[n3 + 3] = dm[3];
                c.bD[n3 + 4] = dm[4];
                c.bC[c.bA] = (short)dm[0];
                ++bA;
                n6 = 1;
                break;
            }
            case 42: {
                n3 = bA * 7;
                c.bB[c.bA] = (byte)n4;
                c.bD[n3] = dm[0];
                c.bD[n3 + 1] = dm[1];
                c.bD[n3 + 2] = dm[2];
                c.bD[n3 + 3] = dm[3];
                c.bD[n3 + 4] = dm[4];
                c.bD[n3 + 5] = dm[5];
                c.bD[n3 + 6] = dm[6];
                c.bC[c.bA] = (short)dm[0];
                ++bA;
                n6 = 1;
                break;
            }
            case 44: {
                n3 = bA * 7;
                c.bB[c.bA] = (byte)n4;
                c.bD[n3] = dm[0];
                c.bD[n3 + 1] = dm[1];
                c.bD[n3 + 2] = dm[2];
                c.bD[n3 + 3] = dm[3];
                c.bD[n3 + 4] = dm[4];
                c.bD[n3 + 5] = dm[5];
                c.bD[n3 + 6] = dm[6];
                c.bC[c.bA] = (short)dm[0];
                ++bA;
                n6 = 1;
                break;
            }
            case 38: {
                n3 = dm[0];
                n4 = dm[1];
                n5 = dm[2];
                a2 = c.o(n3);
                c.ci[n5] = a2.F[n4];
                n6 = 1;
                break;
            }
            case 39: {
                c.g(dm[0], dm[1], dm[2]);
                n6 = 1;
                break;
            }
            case 40: {
                n3 = dm[0];
                n4 = dm[1];
                a2 = c.o(n3);
                switch (n4) {
                    case 0: {
                        c.d(a2);
                        break;
                    }
                    case 1: {
                        c.e(a2);
                    }
                }
                n6 = 0;
                break;
            }
            case 46: {
                c.a(false);
                n6 = 1;
                break;
            }
            case 47: {
                eS = dm[0];
                n6 = 1;
                break;
            }
            case 48: {
                n3 = dm[0];
                n4 = dm[1];
                n5 = dm[2];
                a2 = g[n3];
                c.ci[n5] = a2.G[n4];
                n6 = 1;
                break;
            }
            case 49: {
                n3 = dm[0];
                n4 = dm[1];
                n5 = c.B(dm[2]);
                c.b(n3, n4, n5, dm[3] == 1);
                c.t(n3);
                n6 = 1;
                break;
            }
            case 50: {
                n3 = dm[0];
                n4 = dm[1];
                n5 = dm[2];
                a2 = c.o(n3);
                c.ci[n5] = a2.E[n4];
                n6 = 1;
                break;
            }
            case 51: {
                n3 = dm[0];
                n4 = dm[1];
                n5 = dm[2];
                a2 = c.o(n3);
                n3 = 0;
                if (a2.E[14] > 0) {
                    n3 = 1;
                }
                a2.E[n4] = (short)n5;
                n6 = 1;
                if (n3 != 1 || n4 != 14 || n5 > 0) break;
                a2.i -= 8;
                break;
            }
            case 52: {
                n3 = dm[0];
                n4 = dm[1];
                c.fw[n4] = (byte)n3;
                if (n4 == 0) {
                    if ((k & 1) != 0 && cc == 11) {
                        c.au();
                        c.C(n3);
                    } else {
                        fz = n3;
                    }
                }
                n6 = 1;
                break;
            }
            case 53: {
                c.aO[2] = (short)dm[0];
                c.aO[3] = (short)dm[1];
                c.aO[4] = (short)dm[2];
                c.aO[5] = (short)dm[3];
                aN = dm[4];
                n6 = 1;
                break;
            }
            case 54: {
                n3 = dm[0];
                n4 = dm[1];
                n5 = dm[2];
                a2 = g[n3];
                int n9 = n4;
                a2.I[n9] = a2.I[n9] + n5;
                c.t(n3);
                n6 = 1;
                break;
            }
            case 55: {
                n3 = dm[0];
                n4 = dm[1];
                n6 = 1;
                switch (n3) {
                    case 0: {
                        if (n4 == 1) {
                            c.c(n4, 32, 146, 176, 28);
                        } else {
                            c.c(n4, 32, 56, 176, 208);
                        }
                        h.E = (byte)2;
                        ek = 0;
                        n6 = 0;
                        break;
                    }
                    case 1: {
                        c.m(2, 0);
                        break;
                    }
                    case 2: {
                        c.m(1, n4);
                        break;
                    }
                    case 3: {
                        c.m(3, 0);
                        break;
                    }
                    case 4: {
                        c.m(0, n4);
                        break;
                    }
                    case 5: {
                        c.m(5, 0);
                        break;
                    }
                    case 7: {
                        c.m(6, n4);
                    }
                }
                break;
            }
            case 57: {
                n3 = dm[0];
                n4 = dm[1];
                n5 = dm[2];
                c.k(n4, n3, n5);
                n6 = 1;
                break;
            }
            case 58: {
                n3 = dm[0];
                c.ci[c.dm[1]] = cY[n3];
                n6 = 1;
                break;
            }
            case 59: {
                n3 = dm[0];
                n4 = dm[1];
                a2 = g[n3];
                g[n3].s = n4;
                n6 = 1;
                break;
            }
            case 60: {
                n3 = fC * 5;
                c.fD[c.fC] = (byte)n4;
                c.fE[n3] = dm[0];
                c.fE[n3 + 1] = dm[1];
                c.fE[n3 + 2] = dm[2];
                c.fE[n3 + 3] = fK + dm[3];
                c.fE[n3 + 4] = dm[4];
                ++fC;
                n6 = 1;
                break;
            }
            case 61: {
                n3 = fC * 5;
                c.fD[c.fC] = (byte)n4;
                c.fE[n3] = dm[0];
                ++fC;
                n6 = 1;
                break;
            }
            case 62: {
                n3 = fC * 5;
                c.fD[c.fC] = (byte)n4;
                c.fE[n3] = dm[0];
                c.fE[n3 + 1] = dm[1];
                c.fE[n3 + 2] = dm[2];
                c.fE[n3 + 3] = fK + dm[3];
                c.fE[n3 + 4] = dm[4];
                ++fC;
                n6 = 1;
                break;
            }
            case 63: {
                n3 = fC * 5;
                c.fD[c.fC] = (byte)n4;
                c.fE[n3] = dm[0];
                c.fE[n3 + 1] = dm[1];
                c.fE[n3 + 2] = dm[2];
                c.fE[n3 + 3] = fK + dm[3];
                ++fC;
                n6 = 1;
                break;
            }
            case 64: {
                n3 = fC * 5;
                c.fD[c.fC] = (byte)n4;
                c.fE[n3] = dm[0];
                ++fC;
                n6 = 1;
                break;
            }
            case 65: {
                n3 = dm[0];
                n4 = dm[1];
                c.cm[n3 + 113] = n4 == 1;
                n6 = 1;
                break;
            }
            case 66: {
                n3 = fK + dm[0];
                n6 = dm[1] == 1 ? 1 : 0;
                boolean bl2 = dm[2] == 1;
                n4 = dm[3];
                n5 = dm[4];
                c.a(n3, n6 != 0, bl2, n4, n5);
                c.a((byte)1);
                n6 = 0;
                break;
            }
            case 67: {
                n3 = dm[0];
                n4 = dm[1];
                switch (n3) {
                    case 0: {
                        ct = n4;
                        c.b(cq, ct);
                    }
                }
                n6 = 1;
                break;
            }
            case 68: {
                n3 = dm[0] & 0xFFFF;
                n4 = dm[1];
                c.k(n3, 11, n4);
                n6 = 1;
                break;
            }
            case 69: {
                n3 = dm[0];
                if (n3 == 1) {
                    if (c.a.E[14] == 0) {
                        c.c(a);
                        dg = ci[22];
                        c.ci[22] = 1;
                    }
                } else if (c.a.E[14] == 1) {
                    c.b(a);
                }
                n6 = 1;
                break;
            }
            case 70: {
                if (dB != null) {
                    dB.a(c.dA.v, (int)c.dA.w, 0);
                }
                n6 = 1;
                break;
            }
            case 71: {
                n3 = dm[0];
                n4 = dm[1];
                c.bq[n2] = c.l(n3);
                if (n4 > 0) {
                    c.br[n2] = c.n(n4);
                    c.bs[n2] = 1;
                } else {
                    c.bs[n2] = 0;
                }
                c.bn[n2] = 4;
                c.ci[23] = 0;
                n6 = 0;
                break;
            }
            case 72: {
                n3 = dm[0];
                n4 = dm[1];
                n5 = dm[2];
                switch (n4) {
                    case 0: {
                        c.a(c.o(n3), n5);
                        break;
                    }
                    case 1: {
                        c.u(n5);
                    }
                }
                n6 = 1;
                break;
            }
            default: {
                System.out.println("unhandle cmdType: " + n4);
                n6 = 0;
            }
        }
        int n10 = n2;
        bm[n10] = bm[n10] + 1;
        if (bm[n2] + bt[n2] >= bt[n2 + 1]) {
            c.i(n2);
            n6 = 0;
        }
        return n6 != 0;
    }

    private static void i(int n2) {
        dA = null;
        dB = null;
        if (bp[n2] == 2) {
            c.bn[n2] = 7;
            return;
        }
        c.bn[n2] = 6;
    }

    private static void j(int n2) {
        c.bm[n2] = 0;
        c.bn[n2] = -1;
    }

    private static int a(int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        n2 = c.c(n2, n3, n4);
        n3 = c.c(n6, n7, n8);
        n2 = c.a(n2, n5, n3);
        return n2;
    }

    private static int c(int n2, int n3, int n4) {
        int n5 = 0;
        switch (n2) {
            case 0: {
                n5 = n3;
                break;
            }
            case 1: {
                n5 = ci[n3];
                break;
            }
            case 2: {
                n5 = c.c(n3, n4);
            }
        }
        return n5;
    }

    private static void k(int n2) {
        if (dn) {
            return;
        }
        int n3 = 1;
        while (n3 != 0) {
            switch (bn[n2]) {
                case -1: {
                    int n4 = n2;
                    short s2 = 0;
                    a a2 = null;
                    switch (bp[n4]) {
                        case 0: {
                            s2 = 1;
                            break;
                        }
                        case 1: {
                            if (a == null) break;
                            a2 = c.o(bx[n4]);
                            if (a2.E[1] == 0) {
                                s2 = 0;
                                break;
                            }
                            if (!c.a(a2.v + a2.q[0] - 1 - 6, a2.w + a2.q[1] - 1 - 6, a2.q[2] + 12 + 1, a2.q[3] + 12 + 1, c.a.v + c.a.r[0], c.a.w + c.a.r[1], (int)c.a.r[2], (int)c.a.r[3]) || !c.b(2080)) break;
                            dA = a2;
                            dB = a;
                            s2 = 1;
                            break;
                        }
                        case 2: {
                            if (a == null) break;
                            a2 = c.o(bx[n4]);
                            if (!c.a(a2.v + a2.q[0] - 6, a2.w + a2.q[1] - 6, a2.q[2] + 12, a2.q[3] + 12, c.a.v + c.a.r[0], c.a.w + c.a.r[1], (int)c.a.r[2], (int)c.a.r[3])) break;
                            dA = a2;
                            dB = a;
                            s2 = 1;
                        }
                    }
                    if (s2 != 0) {
                        c.bn[n2] = 0;
                        break;
                    }
                    n3 = 0;
                    break;
                }
                case 0: {
                    n3 = c.h(n2);
                    break;
                }
                case 1: {
                    n3 = bq[n2];
                    n3 = c.j(n2, n3) ? 1 : 0;
                    break;
                }
                case 3: {
                    if (aR != 3) {
                        c.bn[n2] = 0;
                        n3 = 1;
                        break;
                    }
                    n3 = 0;
                    break;
                }
                case 2: {
                    a a3 = c.o(bq[n2]);
                    if (a3.E[br[n2]] == bs[n2]) {
                        c.bn[n2] = 0;
                        n3 = 1;
                        break;
                    }
                    n3 = 0;
                    break;
                }
                case 4: {
                    int n4;
                    n3 = bq[n2];
                    if (c.b(n3)) {
                        c.bn[n2] = 0;
                        c.ci[23] = 1;
                        n4 = br[n2];
                        c.bc[n4] = 0;
                        n3 = 1;
                        break;
                    }
                    if (bs[n2] == 1) {
                        n3 = c.j(n2, br[n2]) ? 1 : 0;
                        break;
                    }
                    n3 = 0;
                    break;
                }
                case 5: 
                case 6: {
                    if (bo[n2] == 1) {
                        c.j(n2);
                    }
                    n3 = 0;
                    break;
                }
                case 7: {
                    short s2;
                    int n4;
                    a a4 = null;
                    if (a != null) {
                        a4 = c.o(bx[n2]);
                        short s3 = c.a.v;
                        int n5 = a4.q[3] + 2;
                        int n6 = a4.q[2] + 2;
                        int n7 = a4.w + a4.q[1] - 1;
                        int n8 = a4.v + a4.q[0] - 1;
                        s2 = c.a.w;
                        n4 = s3;
                        n4 = s3 - n8;
                        n7 = s2 - n7;
                        if (!(n4 >= 0 && n4 <= n6 && n7 >= 0 && n7 <= n5)) {
                            c.bn[n2] = 6;
                        }
                    }
                    n3 = 0;
                }
            }
        }
    }

    private static boolean j(int n2, int n3) {
        if (ba[n3] <= 0) {
            c.bn[n2] = 0;
            c.bc[n3] = 0;
            n2 = 1;
        } else {
            n2 = 0;
        }
        return n2 != 0;
    }

    private static int l(int n2) {
        int n3 = n2;
        switch (n2) {
            case 1: {
                n3 = 2080;
                break;
            }
            case 2: {
                n3 = 262144;
                break;
            }
            case 3: {
                n3 = 524288;
                break;
            }
            case 4: {
                n3 = 258;
                break;
            }
            case 5: {
                n3 = 4112;
                break;
            }
            case 6: {
                n3 = 16388;
                break;
            }
            case 7: {
                n3 = 1032;
            }
        }
        return n3;
    }

    private static void B() {
        try {
            int n2;
            int n3;
            int n4;
            c.a("/script.pak");
            c.g(cg);
            bZ = dw;
            bl = c.a(bZ, 1);
            bm = new int[bl];
            bp = new int[bl];
            bo = new byte[bl];
            bq = new int[bl];
            br = new int[bl];
            bs = new int[bl];
            bx = new short[bl];
            bt = new int[bl + 1];
            bn = new int[bl];
            by = -1;
            for (n4 = 0; n4 < bl; ++n4) {
                c.bx[n4] = (short)c.a(bZ, 1);
            }
            for (n4 = 0; n4 < bl; ++n4) {
                c.bp[n4] = c.a(bZ, 0);
                if (bp[n4] != 4) continue;
                by = n4;
                c.bn[c.by] = 0;
            }
            for (n4 = 0; n4 < bl; ++n4) {
                c.bo[n4] = (byte)c.a(bZ, 0);
            }
            n4 = 0;
            for (n3 = 0; n3 < bl; ++n3) {
                c.bt[n3] = n4;
                n4 += c.a(bZ, 1);
            }
            c.bt[c.bl] = n4;
            bw = new short[n4];
            bv = new short[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                c.bw[n3] = (short)c.a(bZ, 1);
            }
            n3 = 0;
            for (n2 = 0; n2 < n4; ++n2) {
                c.bv[n2] = n3;
                n3 = (short)(n3 + i.T[bw[n2]]);
            }
            bu = new short[n3];
            for (n2 = 0; n2 < bu.length; ++n2) {
                c.bu[n2] = (short)c.a(bZ, 1);
            }
            c.A();
            bZ = null;
            for (n2 = 0; n2 < bl; ++n2) {
                c.j(n2);
            }
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    private static void C() {
        bm = null;
        bp = null;
        bo = null;
        bq = null;
        br = null;
        bs = null;
        bx = null;
        bt = null;
        bn = null;
        fG = 0;
        for (int i2 = 0; i2 < 8; ++i2) {
            c.fI[i2] = null;
        }
        System.gc();
    }

    private static void b(int n2, int n3, int n4, int n5, int n6) {
        short s2 = 0;
        switch (n4) {
            case 1: {
                s2 = (short)ci[n5];
                break;
            }
            case 2: {
                s2 = (short)c.c(n5, n6);
                break;
            }
            case 0: {
                s2 = (short)n5;
            }
        }
        switch (n3) {
            case 6: {
                c.ci[n2] = s2;
                return;
            }
            case 7: {
                int n7 = n2;
                ci[n7] = ci[n7] + s2;
                return;
            }
            case 8: {
                int n8 = n2;
                ci[n8] = ci[n8] - s2;
                return;
            }
            case 9: {
                int n9 = n2;
                ci[n9] = ci[n9] * s2;
                return;
            }
            case 10: {
                int n10 = n2;
                ci[n10] = ci[n10] / s2;
                return;
            }
            case 11: {
                int n11 = n2;
                ci[n11] = ci[n11] % s2;
            }
        }
    }

    private static int a(boolean n2, int n3) {
        Exception exception;
        int n4;
        int n5 = 0;
        RecordStore recordStore = null;
        RecordEnumeration recordEnumeration = null;
        int n6 = 0;
        int n7 = n3 + 2;
        byte[] byArray = dy;
        byte[] byArray2 = dz;
        for (n4 = 0; n4 < byArray.length; ++n4) {
            byArray[n4] = 0;
        }
        for (n4 = 0; n4 < byArray2.length; ++n4) {
            byArray2[n4] = 0;
        }
        try {
            recordStore = RecordStore.openRecordStore((String)"GF_RPG22_N73", (boolean)true);
            recordEnumeration = recordStore.enumerateRecords(null, null, false);
        }
        catch (Exception exception2) {
            exception = exception2;
            exception2.printStackTrace();
            cH = cH + exception.getMessage();
        }
        try {
            if (!recordEnumeration.hasNextElement()) {
                recordStore.addRecord(byArray, 0, 1024);
                for (n4 = 1; n4 < 4; ++n4) {
                    recordStore.addRecord(byArray2, 0, byArray2.length);
                }
            } else {
                byArray = recordStore.getRecord(1);
                if (n3 < 2) {
                    for (n4 = 0; n4 < 2; ++n4) {
                        n6 = n4 * 12 << 2;
                        for (int i2 = 0; i2 < 12; ++i2) {
                            c.dC[n4][i2] = c.a(byArray, n6);
                            n6 += 4;
                        }
                    }
                }
            }
        }
        catch (Exception exception3) {
            exception = exception3;
            exception3.printStackTrace();
        }
        try {
            if (n3 >= 0) {
                if (n2 != 0) {
                    b b2;
                    n6 = 0;
                    byArray2[0] = (byte)cg;
                    ++n6;
                    c.a(byArray2, 1, bl);
                    n6 += 4;
                    for (n4 = 0; n4 < bl; ++n4) {
                        c.a(byArray2, n6, bm[n4]);
                        c.a(byArray2, n6 += 4, bn[n4]);
                        c.a(byArray2, n6 += 4, bq[n4]);
                        c.a(byArray2, n6 += 4, br[n4]);
                        c.a(byArray2, n6 += 4, bs[n4]);
                        n6 += 4;
                    }
                    for (n4 = 0; n4 < 16; ++n4) {
                        c.a(byArray2, n6, (int)bc[n4]);
                        c.a(byArray2, n6 += 4, ba[n4]);
                        c.a(byArray2, n6 += 4, bd[n4]);
                        c.a(byArray2, n6 += 2, bb[n4]);
                        n6 += 4;
                    }
                    for (n4 = 21; n4 < 123; ++n4) {
                        c.a(byArray2, n6, ci[n4]);
                        n6 += 4;
                    }
                    int n8 = c.E();
                    c.b(byArray2, n6, n8);
                    n6 += 4;
                    for (n4 = 0; n4 < n8; ++n4) {
                        c.a(byArray2, n6, c.cz[n4].E[16]);
                        c.b(byArray2, n6 += 2, c.cz[n4].i);
                        c.a(byArray2, n6 += 2, c.cz[n4].v);
                        c.a(byArray2, n6 += 2, c.cz[n4].w);
                        c.a(byArray2, n6 += 2, c.cz[n4].C);
                        c.a(byArray2, n6 += 4, c.cz[n4].D);
                        c.a(byArray2, n6 += 4, p);
                        n6 += 4;
                    }
                    for (n4 = 0; n4 < 4; ++n4) {
                        for (n8 = 0; n8 < c.g[n4].F.length; ++n8) {
                            c.a(byArray2, n6, c.g[n4].F[n8]);
                            n6 += 4;
                        }
                        for (n8 = 0; n8 < c.g[n4].G.length; ++n8) {
                            c.a(byArray2, n6, c.g[n4].G[n8]);
                            n6 += 4;
                        }
                        for (n8 = 0; n8 < c.g[n4].I.length; ++n8) {
                            c.a(byArray2, n6, c.g[n4].I[n8]);
                            n6 += 4;
                        }
                        if (null != c.g[n4].J.b) {
                            c.a(byArray2, n6, c.g[n4].J.b.e());
                            n6 += 4;
                            b2 = c.g[n4].J.b;
                            while (b2 != null) {
                                c.a(byArray2, n6, b2.d);
                                c.a(byArray2, n6 += 4, b2.e);
                                n6 += 4;
                                b2 = b2.b;
                            }
                        } else {
                            c.a(byArray2, n6, 0);
                            n6 += 4;
                        }
                        for (n8 = 0; n8 < c.g[n4].E.length; ++n8) {
                            c.a(byArray2, n6, c.g[n4].E[n8]);
                            n6 += 2;
                        }
                    }
                    c.a(byArray2, n6, ck.length);
                    n6 += 4;
                    for (n4 = 0; n4 < ck.length; ++n4) {
                        c.b(byArray2, n6, c.ck[n4].i);
                        c.a(byArray2, n6 += 2, c.ck[n4].v);
                        c.a(byArray2, n6 += 2, c.ck[n4].w);
                        n6 += 2;
                        for (n8 = 0; n8 < c.ck[n4].E.length; ++n8) {
                            c.a(byArray2, n6, c.ck[n4].E[n8]);
                            n6 += 2;
                        }
                        c.b(byArray2, n6, c.ck[n4].P);
                        n6 += 2;
                        for (n8 = 0; n8 < c.ck[n4].P; ++n8) {
                            c.b(byArray2, n6, c.ck[n4].O[n8 << 1]);
                            c.b(byArray2, n6 += 2, c.ck[n4].O[(n8 << 1) + 1]);
                            n6 += 2;
                        }
                    }
                    for (n4 = 0; n4 < 15; ++n4) {
                        n2 = d[n4].e() - 1;
                        c.a(byArray2, n6, n2);
                        n6 += 4;
                        b2 = c.d[n4].b;
                        while (b2 != null) {
                            c.a(byArray2, n6, b2.d);
                            c.a(byArray2, n6 += 4, b2.e);
                            c.a(byArray2, n6 += 4, b2.c);
                            c.a(byArray2, n6 += 2, b2.f);
                            c.a(byArray2, n6 += 4, b2.g);
                            n6 += 4;
                            b2 = b2.b;
                        }
                    }
                    for (n4 = 0; n4 < 10; ++n4) {
                        c.a(byArray2, n6, cY[n4]);
                        n6 += 4;
                    }
                    c.a(byArray2, n6, dF);
                    n6 += 4;
                    for (n4 = 0; n4 < cG.length; ++n4) {
                        c.a(byArray2, n6, cm[n4 + 113] ? (short)1 : 0);
                        n6 += 2;
                    }
                    c.a(byArray2, n6, ct);
                    n2 = fC;
                    c.a(byArray2, n6 += 4, n2);
                    n6 += 4;
                    for (n4 = 0; n4 < n2; ++n4) {
                        c.a(byArray2, n6, fD[n4]);
                        n6 += 2;
                        for (n8 = 0; n8 < 5; ++n8) {
                            c.a(byArray2, n6, fE[n8]);
                            n6 += 4;
                        }
                    }
                    c.a(byArray2, n6, dg);
                    c.a(byArray2, n6 += 4, fA);
                    c.a(byArray2, n6 += 2, f.b);
                    if ((n6 += 4) >= 10240) {
                        cH = "\u8fc7\u957f " + n6;
                    }
                    recordStore.setRecord(n7, byArray2, 0, 10240);
                    if (n3 < 2) {
                        int[] nArray = dC[n3];
                        nArray[0] = nArray[0] + 1;
                        c.dC[n3][6] = cg;
                        for (n4 = 0; n4 < 4; ++n4) {
                            c.dC[n3][n4 + 1] = (c.g[n4].E[12] & 1) != 0 ? c.g[n4].s : -1;
                        }
                        c.dC[n3][7] = cY[0];
                        c.dC[n3][8] = cY[1];
                        Calendar calendar = Calendar.getInstance();
                        c.dC[n3][9] = calendar.get(11);
                        c.dC[n3][10] = calendar.get(12);
                        c.dC[n3][11] = calendar.get(13);
                        n6 = n3 * 12 << 2;
                        for (n8 = 0; n8 < 12; ++n8) {
                            c.a(byArray, n6, dC[n3][n8]);
                            n6 += 4;
                        }
                        recordStore.setRecord(1, byArray, 0, 1024);
                    }
                } else {
                    byArray2 = recordStore.getRecord(n7);
                    n6 = 0;
                    if (byArray2[0] >= 0) {
                        cg = byArray2[0];
                        ++n6;
                        bl = c.a(byArray2, 1);
                        n6 += 4;
                        for (n4 = 0; n4 < bl; ++n4) {
                            c.bm[n4] = c.a(byArray2, n6);
                            c.bn[n4] = c.a(byArray2, n6 += 4);
                            c.bq[n4] = c.a(byArray2, n6 += 4);
                            c.br[n4] = c.a(byArray2, n6 += 4);
                            c.bs[n4] = c.a(byArray2, n6 += 4);
                            n6 += 4;
                        }
                        for (n4 = 0; n4 < 16; ++n4) {
                            c.bc[n4] = (byte)c.a(byArray2, n6);
                            c.ba[n4] = c.a(byArray2, n6 += 4);
                            c.bd[n4] = c.b(byArray2, n6 += 4);
                            c.bb[n4] = c.a(byArray2, n6 += 2);
                            n6 += 4;
                        }
                        for (n4 = 21; n4 < 123; ++n4) {
                            c.ci[n4] = c.a(byArray2, n6);
                            n6 += 4;
                        }
                        int n9 = c.b(byArray2, n6);
                        n6 += 4;
                        eB = 0;
                        for (n4 = 0; n4 < n9; ++n4) {
                            n2 = c.b(byArray2, n6);
                            n6 += 2;
                            if (n2 < 0) {
                                c.a(g[Math.abs(n2) - 1], n4);
                            } else {
                                c.a(ck[Math.abs(n2)], n4);
                            }
                            c.cz[n4].i = c.b(byArray2, n6);
                            c.cz[n4].v = c.b(byArray2, n6 += 2);
                            c.cz[n4].w = c.b(byArray2, n6 += 2);
                            c.cz[n4].C = c.a(byArray2, n6 += 2);
                            c.cz[n4].D = c.a(byArray2, n6 += 4);
                            p = c.a(byArray2, n6 += 4);
                            n6 += 4;
                        }
                        for (n4 = 0; n4 < 4; ++n4) {
                            for (n9 = 0; n9 < c.g[n4].F.length; ++n9) {
                                c.g[n4].F[n9] = c.a(byArray2, n6);
                                n6 += 4;
                            }
                            for (n9 = 0; n9 < c.g[n4].G.length; ++n9) {
                                c.g[n4].G[n9] = c.a(byArray2, n6);
                                n6 += 4;
                            }
                            for (n9 = 0; n9 < c.g[n4].I.length; ++n9) {
                                c.g[n4].I[n9] = c.a(byArray2, n6);
                                n6 += 4;
                            }
                            n9 = c.a(byArray2, n6);
                            n6 += 4;
                            b cfr_ignored_0 = c.g[n4].J;
                            g[n4].c();
                            for (n7 = 0; n7 < n9; ++n7) {
                                n2 = c.a(byArray2, n6);
                                n3 = c.a(byArray2, n6 += 4);
                                n6 += 4;
                                g[n4].a(n2, n3);
                            }
                            for (n9 = 0; n9 < c.g[n4].E.length; ++n9) {
                                c.g[n4].E[n9] = c.b(byArray2, n6);
                                n6 += 2;
                            }
                        }
                        n6 += 4;
                        for (n4 = 0; n4 < ck.length; ++n4) {
                            c.ck[n4].i = c.b(byArray2, n6);
                            c.ck[n4].v = c.b(byArray2, n6 += 2);
                            c.ck[n4].C = c.ck[n4].v;
                            c.ck[n4].w = c.b(byArray2, n6 += 2);
                            c.ck[n4].D = c.ck[n4].w;
                            n6 += 2;
                            for (n9 = 0; n9 < c.ck[n4].E.length; ++n9) {
                                c.ck[n4].E[n9] = c.b(byArray2, n6);
                                n6 += 2;
                            }
                            n7 = c.b(byArray2, n6);
                            n6 += 2;
                            for (n9 = 0; n9 < n7; ++n9) {
                                n2 = c.b(byArray2, n6);
                                n3 = c.b(byArray2, n6 += 2);
                                n6 += 2;
                                ck[n4].c(n2, n3);
                            }
                        }
                        c.ah();
                        for (n4 = 0; n4 < 15; ++n4) {
                            n2 = c.a(byArray2, n6);
                            n6 += 4;
                            b b3 = d[n4];
                            for (n9 = 0; n9 < n2; ++n9) {
                                b b4 = b.b();
                                b.b().d = (short)c.a(byArray2, n6);
                                b4.e = (byte)c.a(byArray2, n6 += 4);
                                b4.c = (byte)c.b(byArray2, n6 += 4);
                                b4.f = (byte)c.a(byArray2, n6 += 2);
                                b4.g = (byte)c.a(byArray2, n6 += 4);
                                n6 += 4;
                                b3.a(b4);
                                b3 = b4;
                            }
                        }
                        for (n4 = 0; n4 < 10; ++n4) {
                            c.e(n4, c.a(byArray2, n6));
                            n6 += 4;
                        }
                        dF = c.a(byArray2, n6);
                        n6 += 4;
                        c.q(dF);
                        for (n4 = 0; n4 < cG.length; ++n4) {
                            c.cm[n4 + 113] = c.b(byArray2, n6) == 1;
                            n6 += 2;
                        }
                        ct = c.a(byArray2, n6);
                        fC = c.a(byArray2, n6 += 4);
                        n6 += 4;
                        for (n4 = 0; n4 < fC; ++n4) {
                            c.fD[n4] = (byte)c.b(byArray2, n6);
                            n6 += 2;
                            for (n9 = 0; n9 < 5; ++n9) {
                                c.fE[n9] = c.a(byArray2, n6);
                                n6 += 4;
                            }
                        }
                        dg = c.a(byArray2, n6);
                        fA = c.b(byArray2, n6 += 4);
                        f.b = c.a(byArray2, n6 += 2);
                    }
                }
            }
            if (recordEnumeration != null) {
                recordEnumeration.destroy();
            }
            if (recordStore != null) {
                try {
                    recordStore.closeRecordStore();
                }
                catch (RecordStoreException recordStoreException) {
                    RecordStoreException recordStoreException2 = recordStoreException;
                    recordStoreException.printStackTrace();
                }
            }
            System.gc();
        }
        catch (Exception exception4) {
            Exception exception5 = exception4;
            exception4.printStackTrace();
            cH = exception5.getMessage();
            n5 = 1;
        }
        return n5;
    }

    private static void f(int n2, int n3, int n4, int n5) {
        switch (n3) {
            case 0: {
                c.ba[n2] = n5;
                c.bb[n2] = n5;
                c.bc[n2] = 1;
                c.bd[n2] = (short)n4;
                if (bd[n2] < 0) break;
                c.ci[c.bd[n2]] = n5;
                return;
            }
            case 1: {
                c.bc[n2] = 4;
                c.bd[n2] = (short)n4;
                return;
            }
            case 2: {
                c.bc[n2] = 3;
                c.bd[n2] = (short)n4;
                return;
            }
            case 3: {
                c.bc[n2] = 1;
                c.bd[n2] = (short)n4;
                return;
            }
            case 4: {
                c.bc[n2] = 0;
                c.ba[n2] = 0;
                c.bd[n2] = -1;
            }
        }
    }

    private static void m(int n2) {
        int n3 = 0;
        int n4 = 0;
        switch (n2) {
            case 0: {
                n3 = 0;
                n4 = 12;
                break;
            }
            case 1: {
                n3 = 12;
                n4 = 16;
                break;
            }
            case 2: {
                n3 = 0;
                n4 = 16;
            }
        }
        for (n2 = n3; n2 < n4; ++n2) {
            c.ba[n2] = 0;
            c.bb[n2] = 0;
            c.bc[n2] = 0;
            c.bd[n2] = -1;
        }
    }

    private static int n(int n2) {
        for (int i2 = 0; i2 < 4; ++i2) {
            if (bc[i2 + 12] != 0) continue;
            c.bc[i2 + 12] = 1;
            c.ba[i2 + 12] = n2;
            c.bb[i2 + 12] = n2;
            return i2 + 12;
        }
        return -1;
    }

    private static void a(a a2, int n2, boolean bl2) {
        a2.c(n2);
        a2.k = bl2;
        a2.j = n2;
    }

    private static void d(int n2, int n3, int n4) {
        a a2 = c.o(n2);
        a2.c(n3, n4);
    }

    private static void e(int n2, int n3, int n4) {
        a a2 = c.o(n2);
        a2.b(n3, n4);
        if (n2 < 0) {
            for (n2 = 0; n2 < g.length; ++n2) {
                c.g[n2].E[0] = 0;
            }
        }
        if (a2.E[4] == 1 && aR == 2) {
            c.af();
        }
    }

    private static void f(int n2, int n3, int n4) {
        short s2;
        short s3;
        a a2 = c.o(n2);
        a a3 = a2;
        n4 = (short)n4;
        n3 = (byte)n3;
        a3 = a2;
        if (a2.n == 0) {
            s3 = a3.v;
            s2 = a3.w;
        } else {
            s3 = a3.m[a3.n - 1 << 1];
            s2 = a3.m[(a3.n - 1 << 1) + 1];
        }
        if (a3.n < 32) {
            switch (n3) {
                case 2: {
                    s2 = (short)(s2 + n4);
                    break;
                }
                case 3: {
                    s3 = (short)(s3 - n4);
                    break;
                }
                case 0: {
                    s2 = (short)(s2 - n4);
                    break;
                }
                case 1: {
                    s3 = (short)(s3 + n4);
                }
            }
            a3.m[a3.n << 1] = s3;
            a3.m[(a3.n << 1) + 1] = s2;
            ++a3.n;
        }
    }

    private static void a(int n2, int n3, boolean bl2) {
        a a2 = c.o(n2);
        a2.E[0] = 1;
        a2.o = false;
    }

    private static a o(int n2) {
        a a2 = null;
        if (n2 < 0) {
            a2 = (n2 = Math.abs(n2) - 1) < 4 ? g[n2] : aQ;
        } else if (n2 < ck.length) {
            a2 = ck[n2];
        }
        return a2;
    }

    private static int p(int n2) {
        n2 = Math.abs(n2) - 1;
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void D() {
        int n2;
        a a2;
        int n3;
        cw = 0;
        cx = 0;
        for (n3 = 0; n3 < ck.length; ++n3) {
            a2 = ck[n3];
            if (a2.E[1] == 1 && a2.h != 13 && a2.a((int)D, (int)E, bW, bV)) {
                c.a(a2);
            }
            a2.d();
        }
        for (n3 = 0; n3 < ci[22] && n3 < 8; ++n3) {
            if (cz[n3] == null || c.cz[n3].E[16] >= 0) continue;
            a2 = cz[n3];
            if (a2.E[1] != 1 || !a2.a((int)D, (int)E, bW, bV)) continue;
            c.a(a2);
        }
        for (n3 = 0; n3 < ci[22]; ++n3) {
            if (null == cz[n3]) continue;
            cz[n3].d();
        }
        if (a == null) return;
        if (dD >= 0 || dE >= 0) {
            a2 = a;
            if (a2.v == dD && a2.w == dE) {
                boolean bl2 = false;
                for (int i2 = 0; i2 < ck.length; ++i2) {
                    a a3 = ck[i2];
                    if (a3.E[0] == 4 || a3.E[1] != 1 || !c.a(a3.v + a3.r[0], a3.w + a3.r[1], (int)a3.r[2], (int)a3.r[3], a2.v + a2.r[0], a2.w + a2.r[1], (int)a2.r[2], (int)a2.r[3])) continue;
                    bl2 = true;
                }
                if (!bl2) {
                    c.b(a2);
                    return;
                }
                dD = -1;
                dE = -1;
                return;
            }
            if (a2.v > dD) {
                a2.v = (short)(a2.v - Math.min(6, a2.v - dD));
            } else if (a2.v < dD) {
                a2.v = (short)(a2.v + Math.min(6, dD - a2.v));
            }
            if (a2.w > dE) {
                a2.w = (short)(a2.w - Math.min(6, a2.w - dE));
                return;
            }
            if (a2.w >= dE) return;
            a2.w = (short)(a2.w + Math.min(6, dE - a2.w));
            return;
        }
        a2 = a;
        n3 = -1;
        if (c.a(258)) {
            n3 = 2;
        } else if (c.a(16388)) {
            n3 = 4;
        } else if (c.a(1032)) {
            n3 = 8;
        } else if (c.a(4112)) {
            n3 = 16;
        }
        if (n3 == -1) {
            c.b();
            return;
        }
        switch (n3) {
            default: {
                n2 = 0;
                break;
            }
            case 4: {
                n2 = 2;
                break;
            }
            case 8: {
                n2 = 3;
                break;
            }
            case 16: {
                n2 = 1;
            }
        }
        int n4 = a2.E[14] == 1 ? 12 : 6;
        a2.a(n2, n4, true, true);
        if (a2.M == 0) return;
        if (a2.N == 0) return;
        int n5 = c.a.j % 4;
        a a4 = a;
        n2 = 0;
        while (n2 < cz.length) {
            a a5;
            if (cz[n2] != a && (a5 = cz[n2]) != null) {
                switch (n5) {
                    case 0: {
                        a5.C = a4.v;
                        a5.D = a4.w + 24;
                        break;
                    }
                    case 2: {
                        a5.C = a4.v;
                        a5.D = a4.w - 24;
                        break;
                    }
                    case 3: {
                        a5.C = a4.v + 24;
                        a5.D = a4.w;
                        break;
                    }
                    case 1: {
                        a5.C = a4.v - 24;
                        a5.D = a4.w;
                        break;
                    }
                }
                a4 = a5;
                int n6 = a5.C - a4.v;
                int n7 = a5.D - a4.w;
                int n8 = Math.abs(n6);
                int n9 = Math.abs(n7);
                if (n8 < n9) {
                    n5 = n7 > 0 ? 2 : 0;
                    a5.a(n5, Math.min(6, n9), true, false);
                } else if (n8 > n9) {
                    n5 = n6 > 0 ? 1 : 3;
                    a5.a(n5, Math.min(6, n8), true, false);
                } else if (a5.i >= 4 && a5.i <= 7) {
                    a5.c(a5.i - 4);
                    a5.j = a5.i - 4;
                    a5.k = true;
                }
            }
            ++n2;
        }
    }

    private static void b(a a2) {
        a2.E[14] = 0;
        a2.E[15] = 0;
        a2.i -= 8;
        dD = -1;
        dE = -1;
        c.ci[22] = dg;
        for (int i2 = 0; i2 < g.length; ++i2) {
            g[i2].b(a2.v, a2.w);
            short s2 = a2.w;
            short s3 = a2.v;
            a a3 = g[i2];
            g[i2].C = s3;
            a3.D = s2;
        }
    }

    private static void c(a a2) {
        a2.E[14] = 1;
        a2.E[15] = 1;
        if (a2.i < 4) {
            a2.i += 8;
            return;
        }
        if (a2.i < 8) {
            a2.i += 4;
        }
    }

    static int a(int n2, int n3, int n4, int n5, int n6, int n7) {
        int n8 = -1;
        int n9 = -1;
        int n10 = n2;
        int n11 = n3;
        int n12 = n4;
        int n13 = n5;
        switch (n6) {
            case 0: {
                n11 = n3 - n7;
                n13 = n5 + n7;
                break;
            }
            case 1: {
                n12 = n4 + n7;
                break;
            }
            case 2: {
                n13 = n5 + n7;
                break;
            }
            case 3: {
                n10 = n2 - n7;
                n12 = n4 + n7;
            }
        }
        for (int i2 = 0; i2 < ck.length; ++i2) {
            for (n7 = 0; n7 < cz.length; ++n7) {
                if (cz[n7] == null || c.cz[n7].E[16] != i2) continue;
                return -1;
            }
            a a2 = ck[i2];
            if (a2.E[1] != 1 || !c.a(n10, n11, n12, n13, a2.v + a2.r[0], a2.w + a2.r[1], (int)a2.r[2], (int)a2.r[3])) continue;
            switch (n6) {
                case 0: {
                    n9 = Math.max(n9, n3 - (a2.w + a2.r[1] + a2.r[3]));
                    break;
                }
                case 1: {
                    n9 = Math.max(n9, a2.v + a2.r[0] - (n2 + n4));
                    break;
                }
                case 2: {
                    n9 = Math.max(n9, a2.w + a2.r[1] - (n3 + n5));
                    break;
                }
                case 3: {
                    n9 = Math.max(n9, n2 - (a2.v + a2.r[0] + a2.r[2]));
                }
            }
            n8 = n9;
        }
        return n8;
    }

    private static void g(int n2, int n3, int n4) {
        a a2 = g[n2];
        switch (n3) {
            case 1: {
                a2.F[1] = n4;
                return;
            }
            case 4: {
                a2.F[4] = Math.min(n4, a2.F[7]);
                return;
            }
            case 7: {
                a2.F[7] = n4;
                return;
            }
            case 5: {
                a2.F[5] = Math.min(n4, a2.F[8]);
                return;
            }
            case 8: {
                a2.F[8] = n4;
                return;
            }
            case 9: {
                a2.F[9] = n4;
                return;
            }
            case 10: {
                a2.F[10] = n4;
                return;
            }
            case 11: {
                a2.F[11] = n4;
                return;
            }
            case 12: {
                a2.F[12] = n4;
                return;
            }
            case 13: {
                a2.F[13] = n4;
                return;
            }
            case 15: {
                a2.F[15] = n4 - 1;
                c.t(n2);
                a2.F[1] = a2.F[0];
                c.s(n2);
                return;
            }
            case 14: {
                a2.F[14] = n4;
                return;
            }
            case 16: {
                a2.F[16] = n4;
                return;
            }
            case 17: {
                a2.F[17] = n4;
            }
        }
    }

    private static void q(int n2) {
        dF = n2;
        if (n2 == -999) {
            if (a != null) {
                if (c.a.i >= 4 && c.a.i <= 7) {
                    c.a.i -= 4;
                    c.a.j = c.a.i;
                }
                c.b();
            }
            a = null;
            return;
        }
        a a2 = c.o(n2);
        if (a2 == null) {
            System.out.println("Err: ControlActor");
            return;
        }
        if (a != null) {
        }
        a = a2;
        if (-dF > 4) {
            dF = dG;
        }
        for (int i2 = 0; i2 < 4; ++i2) {
            if (c.g[i2].E[14] != 1) continue;
            c.g[i2].i = aZ[3] + 8;
        }
        aQ = a;
    }

    static void a() {
        p = 0;
        fl = 0;
        for (int i2 = 0; i2 < 4; ++i2) {
            if ((c.g[i2].E[12] & 1) != 0) {
                c.q[c.p] = i2;
                ++p;
            }
            if (c.g[i2].E[13] != 1) continue;
            c.fm[c.fl] = i2;
            ++fl;
        }
    }

    private static void k(int n2, int n3) {
        c.g[n2].E[12] = (short)n3;
    }

    private static void l(int n2, int n3) {
        c.g[n2].E[13] = (short)n3;
    }

    private static void a(boolean bl2) {
        for (int i2 = 0; i2 < 4; ++i2) {
            a a2 = g[i2];
            if (a2.F[4] <= 0) continue;
            a2.F[4] = a2.F[7];
            a2.F[5] = a2.F[8];
            a2.F[21] = 0;
        }
    }

    private static void b(int n2, int n3, int n4, int n5, int n6, int n7) {
        dL = n2;
        dN = n3;
        dO = n4;
        dM = n5;
        dP = n6;
        dR = c.c(n7).length();
        dQ = dR > 0 ? n7 : -1;
        dR = dR * i.S + 16;
        c.h(0, 2, 0);
    }

    private static void a(int n2, boolean bl2, boolean bl3, int n3, int n4) {
        dL = n2;
        eh = bl2;
        eg = bl3;
        dZ = n3;
        c.h(3, n4, 0);
    }

    private static void a(Graphics graphics, int n2) {
        switch (n2) {
            case 2: {
                n2 = 160 - (dW >> 1);
                c.a(graphics, 14543359, 0, n2 - dU - 8, 240, dU + 8, 3, 4, 5);
                c.a(graphics, 14543359, 0, n2, 240, dW - dU, 3, 4, 5);
                int n3 = n2 + dW - dU;
                c.a(graphics, dK, 8, n2 - dU - 2, 224, dU, 17, 0, 0xFFFFFF, 1, 0);
                n2 += 5;
                for (int i2 = 0; i2 < dT; ++i2) {
                    if (m[16] == i2) {
                        c.a(graphics, dS[i2], 8, n2, 224, dV[i2], 17, 0xFF0000, 16768274, 1, 0);
                        cq.b(graphics, 203, n2 - 1 + 10, 0);
                    } else {
                        c.a(graphics, dS[i2], 8, n2, 224, dV[i2], 17, 13022093, 0, 1, 0);
                    }
                    n2 += dV[i2];
                }
                c.a(graphics, 14543359, 58, n3 + 5, 124, 24, 201, 202, 203);
                cq.b(graphics, 120, n3 + 5 + 2, 136);
                return;
            }
            case 0: {
                c.f(graphics);
                return;
            }
            case 1: {
                c.a(graphics, 14876594, dX, dY, dZ, ea, 3, 4, 5);
                c.a(graphics, eb, ec);
                return;
            }
            case 3: {
                if (eh) {
                    c.a(graphics, 14543359, dX, dY, dZ, ea, 12, 13, 14);
                }
                graphics.setClip(eb - 5, ec, ed, ee);
                c.a(graphics, eb + 5, ec + ef);
                if (!eg) break;
                graphics.setClip(0, 0, 240, 320);
                c.c(graphics, 1170, 5, 315, 36, 0xFFFFFF, 0);
                c.c(graphics, 1171, 235, 315, 40, 0xFFFFFF, 0);
            }
        }
    }

    private static void f(Graphics graphics) {
        graphics.setClip(0, 0, 240, 320);
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        switch (dO) {
            case 5: {
                int n5 = 320 - i.aF;
                break;
            }
            case 4: {
                int n5 = 160 - (i.aF >> 1);
                break;
            }
            case 3: {
                int n5 = n2 = dM == -1 ? 0 : 91;
            }
        }
        if (dM >= 0) {
            cG[dM].b(graphics, 0, 0);
        }
        if (dQ >= 0) {
            if (dM >= 0 && dN == 0) {
                n3 = 240 - dR;
                n4 = 240 - dR + 8;
            } else {
                n3 = 0;
                n4 = 8;
            }
            c.a(aY, 14543359, n3, n2 - i.aG, dR, i.aG, 3, 4, 5);
            c.c(graphics, dQ, n4, n2 - i.aG + 6, 20, 0, 0);
        }
        c.a(aY, 14543359, 0, n2, 240, i.aF, 3, 4, 5);
        c.a(graphics, 25, n2 + 7);
    }

    private static void r(int n2) {
        int n3 = 0;
        boolean bl2 = c.b(264224);
        switch (n2) {
            case 2: {
                if (c.b(64)) {
                    c.ci[c.dI] = m[16];
                    dH = 1;
                    return;
                }
                c.a(16, dT, 16388, 258, 264224, true);
                return;
            }
            case 0: {
                if (dM >= 0) {
                    cG[dM].e();
                }
                n3 = c.b(bl2);
                if (!bl2) break;
                if (n3 == 0) {
                    dH = 1;
                    return;
                }
                dH = 0;
                return;
            }
            case 1: {
                n3 = c.b(bl2);
                if (n3 == 0) {
                    --ef;
                }
                if ((!bl2 || n3 != 0) && ef > 0) break;
                dH = 1;
                return;
            }
            case 3: {
                if (eg) {
                    n2 = c.a(264224) ? 4 : 1;
                    if (c.a(524288) || (ef -= n2) < -(eu * i.R)) {
                        dH = 1;
                    }
                    return;
                }
                n3 = c.b(bl2);
                if (!bl2 || n3 != 0) break;
                dH = 1;
            }
        }
    }

    private static void h(int n2, int n3, int n4) {
        dJ = n2;
        dH = 0;
        switch (n2) {
            case 2: {
                c.m[16] = 0;
                dU = c.a(c.c(dK), 224, 1);
                dW = dU + 10;
                for (n2 = 0; n2 < dT; ++n2) {
                    c.dV[n2] = c.a(c.c(dS[n2]), 224, 1);
                    dW += dV[n2];
                }
                break;
            }
            case 0: {
                c.i(dL, 200, n3);
                n2 = 0;
                n3 = 0;
                if (dM < 0) break;
                switch (dN) {
                    case 0: {
                        n2 = 0;
                        dP += 5;
                        break;
                    }
                    case 1: {
                        n2 = 120;
                        break;
                    }
                    case 2: {
                        n2 = 240;
                    }
                }
                switch (dO) {
                    case 5: {
                        n3 = 320 - i.aF;
                        break;
                    }
                    case 4: {
                        n3 = 160 - (i.aF >> 1);
                        break;
                    }
                    case 3: {
                        n3 = 320;
                    }
                }
                cG[dM].b(n2, n3);
                cG[dM].c(dP);
                break;
            }
            case 1: {
                c.i(dL, 240, n3);
                dZ = en + 30;
                ea = 40;
                dX = 120 - (dZ >> 1);
                dY = 160 - (ea >> 1);
                eb = 120 - (en >> 1);
                ec = 160 - (i.R >> 1);
                break;
            }
            case 3: {
                ea = i.R * n3 + 30;
                dX = 120 - (dZ >> 1);
                dY = 160 - (ea >> 1);
                eb = dX + 20;
                ec = dY + 15;
                ed = dZ - 40;
                ee = ea - 30;
                ef = eg ? ee : 0;
                c.i(dL, ed, n3);
                if (!eg) break;
                et = em;
            }
        }
        cf = (byte)n4;
    }

    private static void m(int n2, int n3) {
        c.ej[c.el << 1] = n2;
        c.ej[(c.el << 1) + 1] = n3;
        ++el;
    }

    static int d(h h2) {
        n = -1;
        if (el > 0 && ek < el && h2 != null) {
            h.l = true;
            h.m = ej[ek << 1];
            h.n = ej[(ek << 1) + 1];
            ++ek;
            h2.l();
            if (h.o) {
                n = m[h2.g];
            }
        }
        return m[h2.g];
    }

    private static void i(int n2, int n3, int n4) {
        String string = fM[n2];
        StringBuffer stringBuffer = new StringBuffer();
        int n5 = 0;
        int n6 = string.indexOf("/v");
        while (n6 >= 0) {
            stringBuffer.append(string.substring(n5, n6));
            String string2 = string.substring(n6 + 2, n6 + 5);
            n5 = Integer.parseInt(string2);
            stringBuffer.append(ci[n5]);
            n5 = n6 + 5;
            n6 = string.indexOf("/v", n5);
        }
        stringBuffer.append(string.substring(n5));
        string = stringBuffer.toString();
        string = c.b(string);
        eo = string.toCharArray();
        em = eo.length;
        er = n3;
        eq = n4;
        ep = 0;
        es = 0;
        et = 0;
        ey = 2;
        ev = false;
        en = c.D(n2);
    }

    private static String b(String string) {
        StringBuffer stringBuffer = new StringBuffer();
        ex = new int[string.length()];
        int n2 = 0;
        int n3 = 0;
        int n4 = string.indexOf("/i");
        while (n4 >= 0) {
            stringBuffer.append(string.substring(n2, n4));
            String string2 = string.substring(n4 + 2, n4 + 5);
            n2 = Integer.parseInt(string2);
            c.ex[n3] = ci[n2];
            n2 = n4 + 5 + 1;
            stringBuffer.append("/i");
            n4 = string.indexOf("/i", n2);
            ++n3;
        }
        stringBuffer.append(string.substring(n2));
        return stringBuffer.toString();
    }

    private static void a(Graphics graphics, int n2, int n3) {
        if (eo == null) {
            return;
        }
        graphics.setColor(0);
        int n4 = n2;
        int n5 = 0;
        int n6 = 0;
        ep = es;
        int n7 = 0;
        while (true) {
            if (ep < et) {
                if (eo[ep] == '/') {
                    switch (eo[++ep]) {
                        case 'b': {
                            graphics.setColor(0);
                            ++ep;
                            break;
                        }
                        case 'r': {
                            graphics.setColor(0xFF0000);
                            ++ep;
                            break;
                        }
                        case 'w': {
                            graphics.setColor(0xFFFFFF);
                            ++ep;
                            break;
                        }
                        case 'n': {
                            n4 = n2;
                            n3 += i.R;
                            ++ep;
                            if (++n6 != eq) break;
                            ey = 1;
                            ew = true;
                            break;
                        }
                        case 'i': {
                            c.b(graphics, 0 + ex[n7], n4, n3 + 10);
                            ++n7;
                            n4 += 16;
                            ++ep;
                        }
                    }
                    continue;
                }
                ew = false;
                graphics.drawChar(eo[ep], n4, n3, 0);
                n4 += i.Q.charWidth(eo[ep]);
                if (++ep >= em) {
                    ey = 0;
                    ep = em;
                    eu = n6;
                } else {
                    n5 = i.Q.charWidth(eo[ep]);
                    if (n4 + n5 < er + n2) continue;
                    n4 = n2;
                    n3 += i.R;
                    if (++n6 != eq) continue;
                    ey = 1;
                }
            }
            if (ey != 2) break;
            ++et;
            if (!ev) break;
        }
        if (ev) {
            ev = false;
        }
    }

    private static int b(boolean bl2) {
        if (bl2) {
            if (ey == 2) {
                ev = true;
            } else if (ey == 1) {
                ey = 2;
                es = ew ? et + 1 : et;
                ew = false;
            } else if (ey == 0) {
                eo = null;
                em = 0;
                er = 240;
                ep = 0;
                es = 0;
                et = 0;
                ey = 2;
                ev = false;
                return 0;
            }
        }
        return ey;
    }

    static int b(int n2, int n3) {
        int n4 = n2 > 0 ? 1 : -1;
        n2 = n2 / n3 + (n2 % n3 == 0 ? 0 : n4);
        return n2;
    }

    static int a(int n2, int n3, int n4) {
        int n5 = n2;
        switch (n3) {
            case 0: {
                n5 = n2 == n4 ? 1 : 0;
                break;
            }
            case 1: {
                n5 = n2 != n4 ? 1 : 0;
                break;
            }
            case 2: {
                n5 = n2 < n4 ? 1 : 0;
                break;
            }
            case 3: {
                n5 = n2 > n4 ? 1 : 0;
                break;
            }
            case 4: {
                n5 = n2 <= n4 ? 1 : 0;
                break;
            }
            case 5: {
                n5 = n2 >= n4 ? 1 : 0;
                break;
            }
            case 6: {
                n5 = n4;
                break;
            }
            case 7: {
                n5 = n2 + n4;
                break;
            }
            case 8: {
                n5 = n2 - n4;
                break;
            }
            case 9: {
                n5 = n2 * n4;
                break;
            }
            case 10: {
                n5 = n2 / n4;
                break;
            }
            case 11: {
                n5 = n2 % n4;
            }
        }
        return n5;
    }

    private static void a(byte[] byArray, int n2, int n3) {
        for (int i2 = 0; i2 < 4; ++i2) {
            byArray[n2 + 3 - i2] = (byte)n3;
            n3 >>>= 8;
        }
    }

    private static void a(byte[] byArray, int n2, short s2) {
        for (int i2 = 0; i2 < 2; ++i2) {
            byArray[n2 + 1 - i2] = (byte)s2;
            s2 = (short)(s2 >>> 8);
        }
    }

    static int a(int n2, int n3, int n4, int n5) {
        int n6 = -1;
        int n7 = 0;
        int n8 = 0;
        int n9 = n2 / 16;
        int n10 = n3 / 16;
        switch (n4) {
            case 0: {
                n6 = n3;
                n7 = n2;
                n8 = n3 - n5;
                break;
            }
            case 2: {
                n6 = n3;
                n7 = n2;
                n8 = n3 + n5;
                break;
            }
            case 3: {
                n6 = n2;
                n7 = n2 - n5;
                n8 = n3;
                break;
            }
            case 1: {
                n6 = n2;
                n7 = n2 + n5;
                n8 = n3;
            }
        }
        int n11 = n7 / 16;
        int n12 = n8 / 16;
        if ((n9 != n11 || n10 != n12) && (n7 = c.f(n7, n8)) >= 0) {
            switch (n4) {
                case 0: {
                    n6 = n3 - (n12 + 1 << 4);
                    break;
                }
                case 2: {
                    n6 = (n12 << 4) - 1 - n3;
                    break;
                }
                case 3: {
                    n6 = n2 - (n11 + 1 << 4);
                    break;
                }
                case 1: {
                    n6 = (n11 << 4) - 1 - n2;
                }
            }
        } else {
            n6 = n5;
        }
        return n6;
    }

    private static int a(byte[] byArray, int n2) {
        int n3 = 0;
        for (int i2 = 0; i2 < 4; ++i2) {
            n3 <<= 8;
            n3 |= 0xFF & byArray[n2 + i2];
        }
        return n3;
    }

    private static short b(byte[] byArray, int n2) {
        short s2 = 0;
        for (int i2 = 0; i2 < 2; ++i2) {
            s2 = (short)(s2 << 8);
            s2 = (short)(s2 | 0xFF & byArray[n2 + i2]);
        }
        return s2;
    }

    private static void b(byte[] byArray, int n2, int n3) {
        for (int i2 = 0; i2 < 2; ++i2) {
            byArray[n2 + 1 - i2] = (byte)n3;
            n3 >>>= 8;
        }
    }

    static boolean a(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        n2 = n2 < n6 ? (n2 + n4 <= n6 ? 0 : 1) : (n6 + n8 <= n2 ? 0 : 1);
        n3 = n3 < n7 ? (n3 + n5 <= n7 ? 0 : 1) : (n7 + n9 <= n3 ? 0 : 1);
        return n2 != 0 && n3 != 0;
    }

    private static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        int n10 = 0;
        int n11 = 0;
        int n12 = 0;
        int n13 = 0;
        if (n7 >= 0) {
            n10 = cq.a(n7);
            n11 = cq.b(n7);
        }
        if (n8 >= 0) {
            n12 = cq.b(n8);
        }
        if (n9 >= 0) {
            n13 = cq.a(n9);
        }
        c.a(graphics, n2, n3 + n13 - 1, n4 + n12 - 1, n5 - (n13 << 1) + 2, n6 - (n12 << 1) + 2);
        int n14 = 0;
        graphics.setClip(n3 + n10, n4, n5 - (n10 << 1), n6);
        int n15 = 0;
        if (n8 >= 0) {
            n15 = cq.a(n8);
            for (n2 = n3 + n10; n2 < n5 + n3 - n15; n2 += n15) {
                cq.a(graphics, n8, n2, n4, 0);
                cq.a(graphics, n8, n2, n4 + n6 - n12, 1);
            }
            cq.a(graphics, n8, n5 + n3 - n15 - 4, n4, 0);
            cq.a(graphics, n8, n5 + n3 - n15 - 4, n4 + n6 - n12, 1);
        }
        if (n9 >= 0) {
            n15 = cq.b(n9);
            for (n14 = n4 + n11; n14 < n4 + n6 - n15; n14 += n15) {
                cq.a(graphics, n9, n3, n14, 0);
                cq.a(graphics, n9, n3 + n5 - n13, n14, 2);
            }
            cq.a(graphics, n9, n3, n4 + n6 - n15 - 4, 0);
            cq.a(graphics, n9, n3 + n5 - n13, n4 + n6 - n15 - 4, 2);
        }
        if (n7 >= 0) {
            cq.a(graphics, n7, n3, n4, 0);
            cq.a(graphics, n7, n3 + n5 - n10, n4, 2);
            cq.a(graphics, n7, n3, n4 + n6 - n11, 1);
            cq.a(graphics, n7, n3 + n5 - n10, n4 + n6 - n11, 3);
        }
        graphics.setClip(0, 0, 240, 320);
    }

    static int c(int n2, int n3) {
        return Math.abs(bY.nextInt()) % (n3 - n2) + n2;
    }

    private static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        graphics.setColor(n2);
        graphics.setClip(n3, n4, n5, n6);
        graphics.fillRect(n3, n4, n5, n6);
        graphics.setClip(0, 0, 240, 320);
    }

    static int b(int n2, int n3, int n4, int n5) {
        return (n4 - n2) * (n4 - n2) + (n5 - n3) * (n5 - n3);
    }

    static int c(int n2, int n3, int n4, int n5) {
        if (n2 == n4 && n3 == n5) {
            return -1;
        }
        n3 = n5 - n3;
        n2 = n4 - n2;
        if (Math.abs(n3) < Math.abs(n2)) {
            if (n2 >= 0) {
                return 1;
            }
            return 3;
        }
        if (n3 >= 0) {
            return 2;
        }
        return 0;
    }

    private static int a(Graphics graphics, int n2, boolean bl2, int n3, int n4, int n5, int n6) {
        Object object = new StringBuffer(Integer.toString(n3));
        object = ((StringBuffer)object).toString().toCharArray();
        a a2 = cr;
        int n7 = ((Object)object).length;
        Object object2 = false;
        object2 = false;
        int n8 = 0;
        if (n6 == 0 || (n6 & 4) != 0) {
            for (n6 = 0; n6 < n7; ++n6) {
                object2 = object[n6];
                if (object2 == 43 || object2 == 45) continue;
                object2 = object2 - 48 + n2;
                n8 = a2.a((int)object2);
                a2.a(graphics, (int)object2, n4, n5, 0);
                n4 += n8;
            }
        } else {
            for (n6 = n7 - 1; n6 >= 0; --n6) {
                object2 = object[n6];
                if (object2 == 43 || object2 == 45) continue;
                object2 = object2 - 48 + n2;
                n8 = a2.a((int)object2);
                a2.a(graphics, (int)object2, n4 - n8, n5, 0);
                n4 -= n8;
            }
        }
        return n7;
    }

    private static int a(DataInputStream object, int n2) {
        int n3 = 0;
        try {
            switch (n2) {
                case 0: {
                    n3 = ((DataInputStream)object).readByte();
                    break;
                }
                case 1: {
                    n3 = ((DataInputStream)object).readShort();
                    break;
                }
                case 2: {
                    n3 = ((DataInputStream)object).readInt();
                    break;
                }
                case 3: {
                    n3 = ((DataInputStream)object).readUnsignedByte();
                    break;
                }
                default: {
                    n3 = ((DataInputStream)object).readUnsignedShort();
                    break;
                }
            }
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
        }
        return n3;
    }

    private static byte[] b(DataInputStream object, int n2) {
        byte[] byArray = new byte[n2];
        try {
            ((DataInputStream)object).read(byArray);
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
        }
        return byArray;
    }

    private static short[] c(DataInputStream dataInputStream, int n2) {
        short[] sArray = new short[n2];
        try {
            for (int i2 = 0; i2 < n2; ++i2) {
                sArray[i2] = dataInputStream.readShort();
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        return sArray;
    }

    /*
     * Unable to fully structure code
     */
    private static int s(int var0) {
        var1_1 = -1;
        var2_2 = c.g[var0];
        var3_3 = var2_2.F[15];
        for (var4_4 = 0; var4_4 <= var3_3; ++var4_4) {
            var1_1 = c.cP[var0][var4_4][0];
            if (var1_1 < 0) continue;
            var6_6 = var1_1;
            var5_5 = var2_2;
            if (var5_5.J.c(var6_6) == null) {
                while (c.h[var6_6][16] >= 0) {
                    if (var5_5.J.c(var6_6 = c.h[var6_6][16]) == null) continue;
                    ** break block4
                }
                v0 = false;
            } else lbl-1000:
            // 2 sources

            {
                v0 = true;
            }
            if (v0) continue;
            var2_2.a(var1_1, 1);
        }
        return var1_1;
    }

    private static void t(int n2) {
        a a2 = g[n2];
        int n3 = c.g[n2].F[15];
        int[] nArray = new int[12];
        for (int i2 = 0; i2 < 4; ++i2) {
            int n4 = a2.G[i2];
            if (n4 < 0) continue;
            for (int i3 = 1; i3 <= 5; i3 += 2) {
                if (cS[n4][i3] == 11) continue;
                int n5 = cS[n4][i3];
                nArray[n5] = nArray[n5] + cS[n4][i3 + 1];
            }
        }
        a2.F[0] = cP[n2][n3][1];
        a2.F[2] = n3 + 1 < cP[n2].length ? cP[n2][n3 + 1][1] : cP[n2][n3][1];
        a2.F[1] = Math.min(a2.F[1], a2.F[2]);
        a2.F[7] = cP[n2][n3][2] + nArray[1] + a2.I[1];
        a2.F[8] = cP[n2][n3][3] + nArray[2] + a2.I[2];
        a2.F[6] = cP[n2][n3][4] + nArray[0] + a2.I[0];
        a2.F[9] = cP[n2][n3][5] + nArray[3] + a2.I[3];
        a2.F[10] = cP[n2][n3][6] + nArray[4] + a2.I[4];
        a2.F[11] = cP[n2][n3][7] + nArray[5] + a2.I[5];
        a2.F[12] = cP[n2][n3][8] + nArray[6] + a2.I[6];
        a2.F[13] = cP[n2][n3][9] + nArray[7] + a2.I[7];
        a2.F[14] = cP[n2][n3][10] + nArray[8] + a2.I[8];
        a2.F[25] = nArray[9] > 0 ? nArray[9] : cN[n2][0];
        a2.F[16] = cP[n2][n3][11];
        a2.F[4] = Math.min(a2.F[4], a2.F[7]);
        a2.F[5] = Math.min(a2.F[5], a2.F[8]);
        a2.F[3] = Math.min(a2.F[3], a2.F[6]);
    }

    private static void d(a a2) {
        b b2 = d[3];
        b b3 = null;
        for (int i2 = 0; i2 < a2.P; ++i2) {
            b3 = b.b();
            b.b().c = (byte)c.A(a2.O[i2 << 1] & 0xFFFF);
            b3.d = c.B(a2.O[i2 << 1]);
            b3.e = a2.O[(i2 << 1) + 1];
            switch (b3.c) {
                case 0: {
                    b3.g = cQ[b3.d][19];
                    break;
                }
                case 1: {
                    b3.g = cS[b3.d][19];
                    break;
                }
                case 8: {
                    b3.g = cV[b3.d][17];
                }
            }
            b2.a(b3);
            b2 = b2.b;
        }
        c.c(32, 32, 56, 176, 208);
        t.b(8);
        c.t.p[0] = 0;
    }

    private static void e(a a2) {
        int n2;
        int n3 = 0;
        for (n2 = 0; n2 < a2.P; ++n2) {
            n3 += a2.O[(n2 << 1) + 1];
        }
        n3 = c.c(0, n3);
        for (n2 = 0; n2 < a2.P; ++n2) {
            if ((n3 -= a2.O[(n2 << 1) + 1]) > 0) continue;
            n3 = c.B(a2.O[n2 << 1]);
            int n4 = c.A(a2.O[n2 << 1]);
            int n5 = a2.O[(n2 << 1) + 1];
            c.k(n3, n4, n5);
            int n6 = n3;
            n2 = n5;
            n3 = n4;
            n5 = n6;
            String string = "\u83b7\u5f97";
            switch (n3) {
                case 2: {
                    string = string + "\u91d1\u94b1 " + n2;
                    break;
                }
                case 0: {
                    string = string + " " + c.c(n5 + 0) + "X" + n2;
                    break;
                }
                case 1: {
                    string = string + " " + c.c(n5 + 134) + "X" + n2;
                    break;
                }
                case 9: {
                    string = string + " \u6d77\u661fX" + n2;
                }
            }
            ef = 40;
            eo = string.toCharArray();
            em = eo.length;
            er = 200;
            eq = 1;
            ep = 0;
            es = 0;
            et = 0;
            ey = 2;
            ev = false;
            en = 0;
            for (n5 = 0; n5 < eo.length; ++n5) {
                if (eo[n5] == '/') {
                    ++n5;
                    continue;
                }
                en += i.Q.charWidth(eo[n5]);
            }
            dZ = en + 30;
            ea = 40;
            dX = 120 - (dZ >> 1);
            dY = 160 - (ea >> 1);
            eb = 120 - (en >> 1);
            ec = 160 - (i.R >> 1);
            dJ = 1;
            c.a((byte)1);
            return;
        }
    }

    private static void a(a aArray, int n2) {
        int n3;
        a[] aArray2 = new a[cz.length];
        int n4 = 0;
        if (eB > cz.length) {
            return;
        }
        for (n3 = 0; n3 < cz.length; ++n3) {
            if (cz[n3] == null || c.cz[n3].s != aArray.s) continue;
            return;
        }
        for (n3 = 0; n3 < cz.length; ++n3) {
            if (n3 == n2) {
                aArray2[n3] = aArray;
                continue;
            }
            aArray2[n3] = cz[n4];
            ++n4;
        }
        ++eB;
        cz = aArray2;
        aArray = new a[aArray2.length];
        n2 = 0;
        for (int i2 = 0; i2 < cz.length; ++i2) {
            if (cz[i2] == null) continue;
            aArray[n2] = cz[i2];
            ++n2;
        }
        cz = aArray;
    }

    private static void u(int n2) {
        if (cz.length == 0) {
            return;
        }
        int n3 = 0;
        while (n2 < cz.length) {
            int n4 = 0;
            while (n2 + n4 < cz.length - 1) {
                c.cz[n2 + n4] = cz[n2 + n4 + 1];
                ++n4;
            }
            c.cz[c.cz.length - ++n3] = null;
            ++n2;
        }
    }

    private static int E() {
        int n2 = 0;
        for (int i2 = 0; i2 < cz.length; ++i2) {
            if (cz[i2] == null) continue;
            ++n2;
        }
        return n2;
    }

    static void b() {
        a a2 = null;
        for (int i2 = 0; i2 < cz.length; ++i2) {
            if (cz[i2] == null) continue;
            a2 = cz[i2];
            if (a2.i < 4 || a2.i > 7) continue;
            a2.c(a2.i - 4);
            a2.j = a2.i - 4;
            a2.k = true;
        }
    }

    private static void F() {
        Object object = null;
        switch (cb) {
            case 0: {
                if ((k & 1) != 0) {
                    c.au();
                }
                fb = 0;
                eQ = 0;
                eF = 0;
                cF = 0;
                c.Y();
                break;
            }
            case 1: {
                if (bN != null) {
                    for (int i2 = 0; i2 < bG; ++i2) {
                        c.bN[i2] = null;
                    }
                }
                c.ae();
                break;
            }
            case 2: {
                if (v == null) {
                    v = h.g();
                    v.e(34);
                    c.v.f = true;
                }
                if (w == null) {
                    w = h.g();
                    w.e(0);
                    v.a(w);
                }
                if (x == null) {
                    x = h.g();
                    x.e(35);
                }
                if (y == null) {
                    y = h.g();
                    y.a(39, 0, 220, 240, i.ap, false);
                }
                c.r[0] = -1;
                break;
            }
            case 3: {
                int n2;
                for (n2 = 0; n2 < ei.length; ++n2) {
                    c.ei[n2] = 0;
                }
                for (n2 = 0; n2 < 5; ++n2) {
                    int n3 = cR[eK[2]][n2 + 1];
                    c.eE[n2] = n3 >= 0 ? 81 + cO[n3][1] : -1;
                    System.out.println("enemyID: " + n3);
                }
                N = 0;
                O = 0;
                aN = 0;
                aM = 0;
                c.ci[121] = 0;
                break;
            }
            case 4: {
                c.G();
                break;
            }
            case 5: {
                c.H();
                break;
            }
            case 6: {
                c.J();
                break;
            }
            case 7: {
                c.I();
                for (int i3 = 0; i3 < fl; ++i3) {
                    object = eM[i3];
                    ((a)object).a(cl[((a)object).h]);
                    if ((((a)object).F[21] & 1) != 0) {
                        ((a)object).c(4);
                    } else if (((a)object).F[4] < ((a)object).F[7] / 3) {
                        ((a)object).c(3);
                    } else {
                        ((a)object).c(0);
                    }
                    ((a)object).b(((a)object).t, ((a)object).u);
                    ((a)object).k = true;
                }
                break;
            }
            case 8: {
                c.K();
                break;
            }
            case 9: {
                for (int i4 = 0; i4 < 8; ++i4) {
                    c.eN[i4].l = true;
                    c.eN[i4].k = false;
                    eN[i4].b(c.eM[i4].t, c.eM[i4].u);
                }
                c.eO.l = true;
                c.eO.k = false;
                eO.b(120, 160);
                break;
            }
            case 10: {
                try {
                    cp = Image.createImage((String)"/png12");
                }
                catch (IOException iOException) {
                    object = iOException;
                    iOException.printStackTrace();
                }
                cs = new a(12, 12, -1, cp, false);
                break;
            }
            case 11: {
                if (eS >= 0 && cA == null) {
                    c.a("/battle.pak");
                    byte[] byArray = c.f(eS);
                    object = byArray;
                    cA = Image.createImage((byte[])byArray, (int)0, (int)((Object)object).length);
                    c.A();
                    cB = new a(109 + eS, 109 + eS, 0, cA, true);
                }
                System.out.println("\u8f7d\u5165\u6218\u6597\u80cc\u666f\u8d44\u6e90");
                break;
            }
            case 12: {
                cw = 0;
                cx = 0;
                for (int i5 = 0; i5 < eQ; ++i5) {
                    c.a(eM[i5]);
                }
                c.L();
                break;
            }
            case 13: {
                eU = -1;
                eF = -1;
                c.v.f = false;
                c.w(12);
                c.a((byte)3);
                if ((k & 1) != 0) {
                    c.C(fw[1]);
                    break;
                }
                c.au();
            }
        }
        ++cb;
    }

    private static void G() {
        b b2;
        b b3 = d[5];
        b b4 = b2 = c.d[0].b;
        while (b4 != null) {
            if (cQ[b4.d][7] == 1 || cQ[b4.d][7] == 0) {
                b2 = b2.b;
                b4.g();
                b4.a = null;
                b4.b = null;
                b3.a(b4);
                b3 = b3.b;
                b4 = b2;
                continue;
            }
            b4 = b2 = b2.b;
        }
    }

    private static void H() {
        for (int i2 = 81; i2 <= 107; ++i2) {
            boolean bl2 = false;
            for (int i3 = 0; i3 < 5; ++i3) {
                if (eE[i3] != i2) continue;
                bl2 = true;
                break;
            }
            if (!bl2 || null != cl[i2]) continue;
            try {
                c.cl[i2] = Image.createImage((String)("/png" + i2));
                continue;
            }
            catch (IOException iOException) {
                IOException iOException2 = iOException;
                iOException.printStackTrace();
            }
        }
    }

    private static void I() {
        int n2 = 0;
        for (int i2 = 0; i2 < 4; ++i2) {
            a a2 = g[i2];
            n2 = 75 + a2.h;
            if (a2.E[13] != 1 || cl[n2] != null) continue;
            try {
                c.cl[n2] = Image.createImage((String)("/png" + n2));
                continue;
            }
            catch (IOException iOException) {
                IOException iOException2 = iOException;
                iOException.printStackTrace();
            }
        }
        try {
            c.cl[79] = Image.createImage((String)"/png79");
            return;
        }
        catch (IOException iOException) {
            IOException iOException3 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    private static void J() {
        int n2 = 0;
        for (int i2 = 0; i2 < 4; ++i2) {
            a a2 = g[i2];
            if (a2.E[13] != 1) continue;
            n2 = fl > 1 ? (eQ == 0 ? 1 : (eQ == 1 ? 0 : eQ)) : eQ;
            a a3 = eM[n2];
            eM[n2].h = 75 + a2.h;
            a3.s = a3.h;
            a3.t = i.ay[fl > 1 ? n2 : 1];
            a3.u = i.az[fl > 1 ? n2 : 1];
            a a4 = a2;
            a a5 = a3;
            a3.F = null;
            a5.F = a4.F;
            System.arraycopy(a4.E, 0, a5.E, 0, 17);
            a3.E[15] = 0;
            for (n2 = 0; n2 < 71; ++n2) {
                a3.H[n2] = 0;
            }
            a3.F[21] = a3.F[4] <= 0 ? a3.F[21] | 1 : 0;
            a3.F[26] = i2;
            a3.J.b = a2.J.b;
            a3.K.b = a2.K.b;
            a3.E[1] = 1;
            a3.F[28] = 0;
            a3.g();
            if (++eQ >= 3) break;
        }
    }

    private static void K() {
        for (int i2 = 0; i2 < 5; ++i2) {
            int n2;
            int n3 = cR[eK[2]][i2 + 1];
            a a2 = eM[eQ];
            for (n2 = 0; n2 < 17; ++n2) {
                a2.E[n2] = 0;
            }
            if (n3 < 0) continue;
            a2.h = a2.s = 81 + cO[n3][1];
            a2.a(cl[81 + cO[n3][1]]);
            a2.c(0);
            a2.E[1] = 1;
            a2.t = i.aA[i2];
            a2.u = i.aB[i2];
            a2.b(a2.t, a2.u);
            a2.k = true;
            a2.F = new int[32];
            a2.F[4] = cO[n3][7];
            a2.F[7] = cO[n3][7];
            a2.F[5] = cO[n3][8];
            a2.F[8] = cO[n3][8];
            a2.F[6] = 0;
            a2.F[3] = 0;
            a2.F[9] = cO[n3][9];
            a2.F[10] = cO[n3][10];
            a2.F[11] = cO[n3][11];
            a2.F[12] = cO[n3][12];
            a2.F[13] = cO[n3][13];
            a2.F[14] = cO[n3][14];
            a2.F[24] = cO[n3][37];
            a2.F[25] = 1;
            for (n2 = 15; n2 <= 19; n2 += 2) {
                if (cO[n3][n2] < 0) continue;
                a2.a(cO[n3][n2], 1);
            }
            a2.F[20] = 1;
            a2.F[21] = 0;
            a2.F[26] = n3;
            a2.F[27] = cO[n3][35];
            a2.F[28] = 0;
            a2.F[17] = cO[n3][36];
            a2.g();
            ++eQ;
        }
    }

    private static void L() {
        int n2;
        eJ = 0;
        eH = 0;
        for (n2 = 0; n2 < 8; ++n2) {
            c.eI[n2] = -1;
        }
        for (n2 = 0; n2 < eQ; ++n2) {
            a a2 = eM[n2];
            a2.F[30] = 0;
            eH = Math.max(a2.F[14], eH);
        }
        eH *= 30;
    }

    private static void v(int n2) {
        a a2 = eM[n2];
        for (int i2 = 0; i2 < eJ; ++i2) {
            if (a2.F[30] <= c.eM[c.eI[i2]].F[30]) continue;
            c.eI[i2 + 1] = eI[i2];
            c.eI[i2] = (byte)n2;
            ++eJ;
            return;
        }
        c.eI[c.eJ] = (byte)n2;
        ++eJ;
    }

    private static void M() {
        c.eG.F[30] = c.eG.F[30] - eH;
        eF = -1;
        eG = null;
        c.v.f = false;
        c.w(12);
    }

    private static void j(int n2, int n3, int n4) {
        c.eK[2] = n2;
        c.eK[0] = n3;
        c.eK[1] = n4;
        c.eK[3] = 1;
        c.a((byte)2);
    }

    private static void w(int n2) {
        eL = n2;
    }

    private static void N() {
        if (aM > 0) {
            --aM;
            fc &= 0xFFFFFFFE;
        }
        if (aN > 0) {
            --aN;
            if (N <= 0 || N >= 4) {
                J = -J;
            }
            if (O <= 0 || O >= 4) {
                K = -K;
            }
            N = (short)(N + J);
            O = (short)(O + K);
            return;
        }
        N = 0;
        O = 0;
    }

    private static void O() {
        g.n = true;
        s.f();
        if (g.n) {
            c.S();
            c.w(g.o);
            c.eK[3] = 1;
        }
    }

    private static void P() {
        int n2;
        boolean bl2 = true;
        boolean bl3 = true;
        a a2 = eG;
        for (n2 = 40; n2 < 71; ++n2) {
            if (a2.H[n2] <= 0) continue;
            int n3 = n2;
            a2.H[n3] = a2.H[n3] - 1;
        }
        a2.f();
        if (ff) {
            fb = 287;
            for (n2 = 0; n2 < eQ; ++n2) {
                a2 = eM[n2];
                if (a2.F[20] == 1) {
                    if ((a2.F[21] & 1) != 0) {
                        a2.E[1] = 0;
                        continue;
                    }
                    bl2 = false;
                    continue;
                }
                if (a2.F[20] != 0 || (a2.F[21] & 1) != 0) continue;
                bl3 = false;
            }
            if (bl2) {
                eT = 0;
                s = g.c();
                s.a(13, 0);
                c.s.f = eT;
                f.b += eQ;
                c.w(3);
                return;
            }
            if (bl3) {
                eT = 1;
                s = g.c();
                s.a(13, 0);
                c.s.f = eT;
                c.w(3);
                return;
            }
            c.M();
        }
    }

    private static boolean Q() {
        int n2 = 1;
        boolean bl2 = false;
        while (n2 != 0) {
            if (bC[bz] != eU || bz >= bA) {
                n2 = 0;
                continue;
            }
            int n3 = bz * 7;
            switch (bB[bz]) {
                case 45: {
                    c.eV[3] = bz++;
                    break;
                }
                case 44: {
                    c.eV[0] = bD[n3 + 1];
                    c.eV[1] = bD[n3 + 3];
                    c.eV[2] = bD[n3 + 2];
                    ++bz;
                    break;
                }
                case 41: {
                    int n4 = -1;
                    switch (bD[n3 + 5]) {
                        case 1: {
                            n4 = 1;
                            break;
                        }
                        case 0: {
                            n4 = 0;
                        }
                    }
                    if (eL == n4) {
                        g g2 = g.c();
                        if (s == null) {
                            g2.a(11, 0);
                            s = g2;
                        } else {
                            g2.a(11, 0);
                            s.a().a(g2);
                        }
                        g2.m = bz++;
                        g.o = eL;
                        c.eK[3] = 0;
                        c.w(11);
                        bl2 = true;
                        break;
                    }
                    n2 = 0;
                    break;
                }
                case 43: {
                    a a2 = c.o(bD[n3 + 1]);
                    a2.c(bD[n3 + 2]);
                    a2.b(bD[n3 + 3], bD[n3 + 4]);
                    c.cE[c.cF] = a2;
                    ++cF;
                    ++bz;
                    break;
                }
                case 42: {
                    el = 0;
                    ek = 0;
                    c.m(4, 0);
                    switch (bD[n3 + 1]) {
                        case 0: {
                            c.m(1, 0);
                            c.m(2, bD[n3 + 3]);
                            break;
                        }
                        case 1: {
                            c.m(1, 1);
                            c.m(2, 0);
                            for (n2 = 0; n2 < bD[n3 + 3]; ++n2) {
                                c.m(1, n2 + 1);
                            }
                            c.m(2, 0);
                            c.m(1, bD[n3 + 2]);
                            c.m(2, 0);
                            if (bD[n3 + 5] < 0) break;
                            bE = bD[n3 + 5];
                        }
                    }
                    c.m(5, 0);
                    c.m[17] = 0;
                    h.E = (byte)2;
                    ++bz;
                    n2 = 0;
                }
            }
        }
        return bl2;
    }

    /*
     * Unable to fully structure code
     */
    private static void R() {
        c.bE = -1;
        c.m[c.v.g] = 0;
        for (var0 = 0; var0 < 4; ++var0) {
            c.eV[var0] = -1;
        }
        c.aV = 42;
        if (c.eG.H[48] > 0) ** GOTO lbl-1000
        if (c.eG.H[43] > 0) {
            h.E = (byte)3;
        } else if (c.eG.F[20] == 0) {
            h.E = 0;
        } else lbl-1000:
        // 2 sources

        {
            h.E = 1;
        }
        c.eG.F[28] = 0;
        if (c.eG.i == 6) {
            if (c.eG.F[4] < c.eG.F[7] / 3) {
                c.eG.c(3);
            } else {
                c.eG.c(0);
            }
        }
        var0_1 = c.eG;
        if (var0_1.H[25] <= 0) {
            var1_2 = var0_1.F[26];
            var0_1.F[17] = var0_1.F[20] == 0 ? c.cP[var1_2][var0_1.F[15]][12] : c.cO[var1_2][36];
        }
        var0_1 = c.eG;
        var0_1.F[29] = 100;
        c.w(10);
    }

    private static void S() {
        s.d();
        s = null;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private static void T() {
        block11: {
            block10: {
                var2 = -1;
                var4_1 = 0;
                var5_3 = 0;
                var0_4 = c.c(0, 100);
                var3_5 = c.eG.b();
                var1_6 = 0;
                if (c.eG.H[48] > 0) {
                    var1_6 = 0;
                    var5_3 = c.y(c.eG.F[20]);
                } else {
                    var6_7 = 0;
                    for (var7_8 = 0; var7_8 < var3_5; ++var7_8) {
                        var8_9 = var6_7 = c.cO[c.eG.F[26]][15 + 2 * var7_8];
                        var4_2 /* !! */  = c.eG;
                        var4_2 /* !! */  = var4_2 /* !! */ .J.c(var8_9);
                        v0 = var4_1 = var4_2 /* !! */ .h > 0 ? 0 : c.cO[c.eG.F[26]][16 + 2 * var7_8];
                        if ((var0_4 -= var4_1) >= 0) continue;
                        if (c.eG.F[5] >= c.h[var6_7][2]) {
                            var1_6 = 1;
                            var2 = var7_8;
                            break;
                        }
                        var0_4 += var4_1;
                    }
                }
                if (c.eG.F[20] != 0) break block10;
                if (c.eG.H[48] > 0 && var5_3 > 1) ** GOTO lbl-1000
                var0_4 = 1;
                break block11;
            }
            if (c.eG.H[48] > 0 && var5_3 > 1) {
                var0_4 = 1;
            } else lbl-1000:
            // 2 sources

            {
                var0_4 = 0;
            }
        }
        var0_4 = c.y(var0_4);
        var0_4 = c.c(0, var0_4);
        c.el = 0;
        c.ek = 0;
        switch (var1_6) {
            case 0: {
                c.m(4, 0);
                c.m(1, 0);
                c.m(2, 0);
                c.m(1, var0_4);
                c.m(2, 0);
                break;
            }
            case 1: {
                c.m(4, 0);
                c.m(1, 1);
                c.m(2, 0);
                c.m(1, var2);
                c.m(2, 0);
                c.m(1, var0_4);
                c.m(2, 0);
            }
        }
        c.m[17] = 0;
    }

    private static void U() {
        fb = 287;
        if (v != null && eL == 1) {
            v.j();
        }
        cq.e();
    }

    private static void V() {
        s = g.c();
        s.a(11, 0);
        c.s.m = eV[3];
        c.eK[3] = 0;
        g.o = 1;
        c.w(11);
    }

    private static int b(int n2, int n3, boolean bl2) {
        int n4 = n2;
        do {
            n4 += n3;
            n4 = (n4 + eQ) % eQ;
            if (bl2 || n4 != n2) continue;
            return -1;
        } while ((eP & 1 << n4) == 0);
        return n4;
    }

    private static void a(int n2, boolean bl2) {
        eR = c.b(eR, n2, true);
        c.y.q = 0;
        c.x.q = 0;
        n2 = c.y(c.eM[c.eR].F[20]);
        n2 = fd > 0 ? Math.min(fd, n2) : n2;
        boolean bl3 = false;
        int n3 = eR;
        a a2 = null;
        for (int i2 = 0; i2 < n2 && n3 >= 0; ++i2) {
            if ((eP & 1 << n3) != 0) {
                int n4;
                a2 = eM[n3];
                switch (a2.F[20]) {
                    case 0: {
                        n4 = 681;
                        break;
                    }
                    case 1: {
                        n4 = 400;
                    }
                }
                y.c(n4 + a2.F[26]);
                x.c(n3);
            }
            n3 = c.b(n3, 1, true);
        }
    }

    static void e(h h2) {
        Object var1_2 = null;
        boolean bl2 = false;
        boolean bl3 = false;
        if ((fb & 0x100) != 0) {
            aT.e();
        }
        switch (h.E) {
            case 1: 
            case 2: {
                fc = 7;
                c.d(h2);
                if (!h.o) break;
                eR = c.b(eR, m[h2.g], true);
                bl3 = true;
                break;
            }
            case 0: {
                if (c.b(264224)) {
                    bl3 = true;
                    break;
                }
                if (c.b(524288)) {
                    c.w(1);
                    c.y.f = false;
                    return;
                }
                if (c.b(1290)) {
                    c.a(-1, true);
                    break;
                }
                if (!c.b(20500)) break;
                c.a(1, true);
            }
        }
        if (bl3) {
            int n2 = 100;
            if (eV[2] >= 0 && eR != eV[2]) {
                c.V();
                return;
            }
            c.y.q = 0;
            switch (eW) {
                case 0: {
                    eY = c.eG.F[17];
                    y.c(1301);
                    bl2 = true;
                    break;
                }
                case 1: {
                    if (h[eX][2] >= 0) {
                        c.eG.F[5] = c.eG.F[5] - h[eX][2];
                    } else {
                        c.k(0, 2, h[eX][2]);
                    }
                    c.eG.F[3] = c.eG.F[3] - h[eX][3];
                    eY = h[eX][18];
                    n2 = h[eX][15];
                    y.c(590 + eX);
                    b b2 = c.c();
                    c.c().h = (short)(h[b2.d][5] + 1);
                    bl2 = true;
                    break;
                }
                case 2: {
                    int n3 = fd > 0 ? fd : 3;
                    int n4 = eR;
                    n3 = Math.min(n3, c.y(c.eG.F[20]));
                    int n5 = 0;
                    for (int i2 = 0; i2 < n3; ++i2) {
                        a a2 = eM[n4];
                        c.a(eX, a2);
                        if (c.al() > 0) {
                            ++n5;
                        }
                        c.Y();
                        n4 = c.b(n4, 1, true);
                    }
                    if (n5 > 0) {
                        c.k(eX, 3, -1);
                        eY = cQ[eX][14];
                        n2 = cQ[eX][18];
                        y.c(0 + eX);
                        bl2 = true;
                        break;
                    }
                    c.w(1);
                    w.a(57, 50, i.ao, 140, i.an, true);
                    w.c();
                    c.w.i = 1086;
                }
            }
            if (bl2) {
                c.m[17] = 0;
                s = g.c();
                s.a(0, 0);
                s.a(eF, eW, eX, eR, fd);
                c.s.a = eY;
                c.eG.F[29] = c.eG.F[29] - n2;
                c.y.f = true;
                c.w(6);
            }
        }
    }

    static void f(h h2) {
        int n2;
        int n3;
        int n4;
        int n5 = 0;
        int n6 = 0;
        for (int i2 = 0; i2 < 5; ++i2) {
            int n7 = cR[eK[2]][i2 + 1];
            if (n7 < 0) continue;
            n5 += cO[n7][3];
            n6 += cO[n7][4];
            for (n4 = 0; n4 < 3; ++n4) {
                int n8 = cO[n7][21 + (n4 << 1)];
                if (n8 < 0 || (n3 = cO[n7][22 + (n4 << 1)]) <= 0 || (n2 = c.c(0, 100)) > n3) continue;
                n2 = 1;
                n2 = n8;
                n3 = 0;
                for (n8 = 0; n8 < eD; ++n8) {
                    if (eC[n8 << 1] != n2) continue;
                    int n9 = (n8 << 1) + 1;
                    eC[n9] = eC[n9] + 1;
                    n3 = 1;
                    break;
                }
                if (n3 != 0) continue;
                c.eC[c.eD << 1] = n2;
                c.eC[(c.eD << 1) + 1] = 1;
                ++eD;
            }
        }
        h2.c(n5);
        h2.c(n6);
        c.k(0, 2, n5);
        c.k(0, 9, n6);
        for (n4 = 0; n4 < eD; ++n4) {
            n2 = c.A(eC[n4 << 1] & 0xFFFF);
            n3 = c.B(eC[n4 << 1]);
            n5 = eC[(n4 << 1) + 1];
            c.k(n3, n2, n5);
            switch (n2) {
                case 0: {
                    h2.c(n3 + 0);
                    h2.c(cQ[n3][19]);
                    break;
                }
                case 1: {
                    h2.c(n3 + 134);
                    h2.c(cS[n3][19]);
                    break;
                }
                case 8: {
                    h2.c(n3 + 732);
                    h2.c(cV[n3][17]);
                }
            }
            h2.c(n5);
        }
    }

    static void g(h h2) {
        int n2;
        int n3 = h2.t[0];
        int n4 = h2.t[1];
        int[] nArray = new int[4];
        int n5 = 0;
        a a2 = null;
        for (int i2 = 0; i2 < 5; ++i2) {
            int n6 = cR[eK[2]][i2 + 1];
            if (n6 < 0) continue;
            for (n2 = 0; n2 < 4; ++n2) {
                a2 = g[n2];
                n5 = 0;
                if ((a2.F[21] & 1) != 0 || a2.F[15] >= cP[n2].length - 1) continue;
                if (a2.E[13] == 1) {
                    n5 = 100;
                } else if ((a2.E[12] & 1) != 0) {
                    n5 = 100;
                }
                int n7 = a2.F[15] - cO[n6][2];
                n7 = Math.max(0, n7);
                n7 = Math.min(10, n7);
                n5 = n5 * (10 - n7) / 10;
                int n8 = n2;
                nArray[n8] = nArray[n8] + cO[n6][5] * n5 / 100;
            }
        }
        for (n2 = 0; n2 < 4; ++n2) {
            int n9 = n2;
            nArray[n9] = nArray[n9];
            if (c.g[n2].E[13] != 1) continue;
            h2.c(n2);
            h2.c(nArray[n2]);
            h2.c(nArray[n2]);
        }
        for (n2 = 0; n2 < fk.length; ++n2) {
            fk[n2].b(n3 + 106, n4 + 50 + n2 * 43);
            c.fk[n2].E[1] = 0;
        }
    }

    private static void W() {
        if (c.b(264224)) {
            switch (eW) {
                case 4: {
                    eT = 2;
                    s = g.c();
                    s.a(13, 0);
                    c.s.f = eT;
                    c.w(3);
                    return;
                }
                case 5: {
                    c.M();
                    return;
                }
                case 6: {
                    c.w(1);
                }
            }
        }
    }

    private static void X() {
        i i2 = null;
        switch (cb) {
            case 0: {
                if ((k & 1) != 0) {
                    c.au();
                }
                if (cA == null) break;
                cA = null;
                cB.a();
                cB = null;
                break;
            }
            case 1: {
                for (int i3 = 0; i3 < eQ; ++i3) {
                    i2 = eM[i3];
                    eM[i3].B = null;
                    if (!cm[((a)i2).h]) {
                        c.cl[((a)i2).h] = null;
                    }
                    for (int i4 = 0; i4 < 71; ++i4) {
                        ((a)i2).H[i4] = 0;
                    }
                }
                System.gc();
                break;
            }
            case 2: {
                cp = null;
                cs.a();
                cs = null;
                System.gc();
                break;
            }
            case 3: {
                for (int i5 = 0; i5 < eQ; ++i5) {
                    i2 = eM[i5];
                    if (((a)i2).F == null) continue;
                    if (((a)i2).F[20] == 0) {
                        ((a)i2).J.b = null;
                        ((a)i2).K.b = null;
                        continue;
                    }
                    ((a)i2).J.d();
                    ((a)i2).J.b = null;
                    ((a)i2).K.d();
                    ((a)i2).K.b = null;
                    ((a)i2).F = null;
                }
                break;
            }
            case 4: {
                b b2 = c.d[5].b;
                while (b2 != null) {
                    c.k(b2.d, 0, b2.e);
                    i2 = b2;
                    b2 = b2.b;
                    ((b)i2).g();
                    ((b)i2).c();
                }
                c.d[5].b = null;
                break;
            }
            case 5: {
                for (int i6 = 0; i6 < 4; ++i6) {
                    if (c.g[i6].E[13] != 1) continue;
                    c.g[i6].F[4] = Math.max(c.g[i6].F[4], 1);
                }
                eG = null;
                for (int i7 = 75; i7 <= 79; ++i7) {
                    if (cm[i7]) continue;
                    c.cl[i7] = null;
                }
                break;
            }
            case 6: {
                if (v != null) {
                    v.h();
                    v = null;
                }
                w = null;
                if (x != null) {
                    x.h();
                    x = null;
                }
                if (y != null) {
                    y.h();
                    y = null;
                }
                l = false;
                h.E = 0;
                el = 0;
                System.gc();
                break;
            }
            case 7: {
                c.ap();
                break;
            }
            case 8: {
                c.ad();
                bK = true;
                break;
            }
            case 9: {
                if ((k & 1) != 0) {
                    c.C(fw[0]);
                }
                switch (eT) {
                    case 0: {
                        c.a((byte)0);
                        break;
                    }
                    case 1: {
                        if (eK[1] == 1) {
                            c.ci[121] = 1;
                        } else {
                            c.b((byte)14);
                            break;
                        }
                    }
                    case 2: {
                        c.a((byte)0);
                    }
                }
                bz = 0;
                bA = 0;
                c.b(cq, ct);
            }
        }
        ++cb;
    }

    private static void b(Graphics graphics, int n2, int n3) {
        int n4 = 0;
        int n5 = 0;
        if ((fc & 1) != 0) {
            cB.b(graphics, n2, n3);
        } else if (aM > 0) {
            c.a(graphics, 0, 0, 0, 240, 240);
        }
        c.g(0, cw - 1);
        for (int i2 = 0; i2 < cw; ++i2) {
            Graphics graphics2;
            int n6;
            int n7;
            int n8;
            a a2 = cy[i2];
            if (a2.E[1] != 1) continue;
            if ((fc & 2) != 0) {
                a2.b(graphics, n2, n3);
            }
            if ((fc & 4) == 0) continue;
            n4 = a2.v - n2;
            n5 = a2.w - a2.F[27] - n3;
            if ((a2.F[21] & 1) == 0 && a2.F[20] == 1) {
                int n9 = 3;
                n8 = 39;
                n7 = n5 - 1;
                n6 = n4 + -19 - 1;
                int n10 = 5905164;
                graphics2 = graphics;
                graphics2.setColor(5905164);
                graphics2.setClip(0, 0, 240, 320);
                graphics2.drawRect(n6, n7, 39, 3);
                c.a(graphics, 0xFFFFFF, n4 + -19, n5, 38, 2);
                c.a(graphics, 0xFF0000, n4 + -19, n5, 38 * a2.F[4] / a2.F[7], 2);
            }
            n7 = n3;
            n6 = n2;
            a a3 = a2;
            graphics2 = graphics;
            for (n8 = 0; n8 < 5; ++n8) {
                if (a3.H[n8 + 35] <= 0) continue;
                fs.a(graphics2, n8 + 40, a3.v + (n8 << 3) - 16 - n6, a3.w - a3.F[27] + -12 - n7, 0);
            }
        }
    }

    private static void g(Graphics graphics) {
        int n2 = 0;
        int n3 = 0;
        for (int i2 = 0; i2 < eZ; ++i2) {
            n2 = i2 * 7;
            if (fa[n2 + 5] >= 5) continue;
            n3 = fa[n2 + 6];
            switch (fa[n2 + 4]) {
                case 1: {
                    cq.a(graphics, fa[n2 + 3], fa[n2], fa[n2 + 1], 0);
                    break;
                }
                case 0: {
                    c.a(graphics, fa[n2 + 3], false, fa[n2 + 2], fa[n2], fa[n2 + 1], n3);
                }
            }
            int n4 = n2 + 5;
            fa[n4] = fa[n4] + 1;
            int n5 = n2 + 1;
            fa[n5] = fa[n5] - 8;
        }
    }

    private static void h(Graphics graphics) {
        for (int i2 = 0; i2 < eN.length; ++i2) {
            if (c.eN[i2].l) continue;
            eN[i2].b(graphics, 0, 0);
        }
    }

    private static void i(Graphics graphics) {
        int n2 = 0;
        int n3 = 0;
        if ((fc & 8) != 0) {
            for (n2 = 0; n2 < c.x.q; ++n2) {
                a a2 = eM[c.x.p[n2]];
                if ((fb & 0x100) != 0) {
                    n3 = 42 + a2.F[30] * 127 / eH;
                    aT.b(n3, 25);
                    aT.b(graphics, 0, 0);
                }
                cq.b(graphics, (int)a2.t, a2.u - a2.F[27] + -20 + (int)bF % 2, 55);
            }
        }
    }

    private static void j(Graphics graphics) {
        int n2;
        int n3;
        if ((fb & 1) != 0) {
            c.a(graphics, 14543359, 0, 245, 240, 80, 3, 4, 5);
            for (n3 = 0; n3 < 3; ++n3) {
                c.a(graphics, 16770483, 6 + n3 * 76, 251, 76, 69, 3, 4, 5);
            }
        }
        if ((fb & 2) != 0) {
            n2 = 60;
            for (n3 = 0; n3 < fl; ++n3) {
                int n4 = fm[n3];
                a a2 = g[n4];
                int n5 = a2.h;
                cs.b(graphics, n2 + -20, 320, n5 + 0);
                if ((fb & 8) != 0) {
                    c.a(graphics, 0, a2, n2 + -32, 300, 51);
                    c.a(graphics, 1, a2, n2 + -32, 313, 51);
                }
                n2 += 76;
            }
        }
        Graphics graphics2 = graphics;
        if ((fb & 0x10) != 0) {
            cq.b(graphics2, 40, 25, 51);
            cq.b(graphics2, 169, 25, 52);
            for (n2 = 0; n2 < eQ; ++n2) {
                a a3 = eM[n2];
                if ((a3.F[21] & 1) != 0 || eF == n2) continue;
                int n6 = 42 + a3.F[30] * 127 / eH;
                cq.b(graphics2, n6, 25, a3.F[20] == 0 ? 53 : 54);
                a3.b(graphics2, n6, 25, 0);
            }
            if (eF >= 0) {
                int n7 = 42 + c.eG.F[30] * 127 / eH;
                a a4 = eM[eF];
                cq.b(graphics2, n7, 25, a4.F[20] == 0 ? 53 : 54);
                eG.b(graphics2, n7, 25, 0);
            }
        }
        v.a(graphics);
        y.a(graphics);
        if ((fb & 0x20) != 0) {
            c.f(graphics);
        }
        if (eK[3] == 1) {
            for (int i2 = 0; i2 < cF; ++i2) {
                cE[i2].d();
                cE[i2].b(graphics, 0, 0);
            }
        }
        if ((fb & 0x40) != 0) {
            t.a(graphics);
        }
    }

    private static int n(int n2, int n3) {
        int n4 = 0;
        block0 : switch (n2) {
            case 0: {
                n4 = 34;
                break;
            }
            case 1: {
                n4 = 0;
                switch (h[n3][0]) {
                    case 0: {
                        n4 = 33;
                        break;
                    }
                    case 1: {
                        n4 = 34;
                        break;
                    }
                    case 2: {
                        n4 = 36;
                    }
                }
                for (n2 = 6; n2 <= 10; n2 += 2) {
                    if (h[n3][n2] != 5) continue;
                    n4 |= 0x10;
                    n4 &= 0xFFFFFFDF;
                    break block0;
                }
                break;
            }
            case 2: {
                fe = 0;
                switch (cQ[n3][4]) {
                    case 0: {
                        n4 = 33;
                        break;
                    }
                    case 1: {
                        n4 = 34;
                    }
                }
                for (n2 = 8; n2 <= 10; n2 += 2) {
                    if (cQ[n3][n2] != 5) continue;
                    n4 |= 0x10;
                    n4 &= 0xFFFFFFDF;
                    break block0;
                }
                break;
            }
        }
        return n4;
    }

    private static int x(int n2) {
        int n3 = 0;
        int n4 = c.eM[c.eF].F[20];
        for (int i2 = 0; i2 < eQ; ++i2) {
            a a2 = eM[i2];
            n3 |= 1 << i2;
            if ((n2 & 0x20) != 0 && (a2.F[21] & 1) != 0) {
                n3 &= ~(1 << i2);
                continue;
            }
            if ((n2 & 4) != 0 && i2 != eF) {
                n3 &= ~(1 << i2);
                continue;
            }
            if ((n2 & 8) != 0 && i2 == eF) {
                n3 &= ~(1 << i2);
                continue;
            }
            if ((n2 & 1) != 0 && a2.F[20] != n4) {
                n3 &= ~(1 << i2);
                continue;
            }
            if ((n2 & 2) == 0 || a2.F[20] != n4) continue;
            n3 &= ~(1 << i2);
        }
        return n3;
    }

    private static int y(int n2) {
        int n3 = 0;
        for (int i2 = 0; i2 < eQ; ++i2) {
            if ((c.eM[i2].F[21] & 1) != 0 || c.eM[i2].F[20] != n2) continue;
            ++n3;
        }
        return n3;
    }

    private static void c(Graphics graphics, int n2, int n3) {
        for (int i2 = 0; i2 < eQ; ++i2) {
            if (c.eN[i2].l || c.eN[i2].E[1] != 1) continue;
            eN[i2].b(graphics, n2, n3);
        }
        if (!c.eO.l && c.eO.E[1] == 1) {
            eO.b(graphics, n2, n3);
        }
    }

    private static void Y() {
        for (int i2 = 0; i2 < 71; ++i2) {
            c.ft[i2] = 0;
        }
    }

    static void h(h i2) {
        int n2;
        boolean bl2 = true;
        aU.e();
        switch (h.E) {
            case 3: {
                c.w(5);
                return;
            }
            case 0: {
                n2 = ((h)i2).g;
                if (c.b(258)) {
                    switch (m[n2]) {
                        case 3: {
                            c.m[n2] = 0;
                        }
                    }
                }
                if (c.b(16388)) {
                    switch (m[n2]) {
                        case 0: {
                            c.m[n2] = 3;
                            break;
                        }
                        case 1: {
                            c.m[n2] = 3;
                            break;
                        }
                        case 2: {
                            c.m[n2] = 3;
                        }
                    }
                }
                if (c.b(1032)) {
                    switch (m[n2]) {
                        case 0: {
                            c.m[n2] = 1;
                            break;
                        }
                        case 3: {
                            c.m[n2] = 1;
                            break;
                        }
                        case 2: {
                            c.m[n2] = 0;
                            break;
                        }
                        case 1: {
                            c.m[n2] = 2;
                        }
                    }
                }
                if (c.b(4112)) {
                    switch (m[n2]) {
                        case 0: {
                            c.m[n2] = 2;
                            break;
                        }
                        case 1: {
                            c.m[n2] = 0;
                            break;
                        }
                        case 3: {
                            c.m[n2] = 2;
                            break;
                        }
                        case 2: {
                            c.m[n2] = 1;
                        }
                    }
                }
                ((h)i2).f = true;
                break;
            }
            case 1: 
            case 2: {
                fc = 7;
                bl2 = false;
            }
        }
        if (c.w.j == -1) {
            c.w.j = 0;
            w.d();
            w.e();
            c.w.a = v;
            return;
        }
        if (((h)i2).w >= 0) {
            ((h)i2).f = false;
            if (eV[0] >= 0 && ((h)i2).w != eV[0]) {
                c.V();
                return;
            }
            switch (((h)i2).w) {
                case 4: {
                    c.w(1);
                    return;
                }
                case 0: {
                    eW = 0;
                    if (c.eG.H[48] > 0) {
                        fe = 40;
                        int n3 = c.y(c.eG.F[20]) > 1 ? 1 : 2;
                        fe |= n3;
                    } else {
                        fe = 34;
                    }
                    if (c.eG.F[20] == 0) {
                        aT.c(2);
                    } else {
                        aT.c(1);
                    }
                    eP = c.x(fe);
                    eR = eF;
                    fd = c.eG.F[25];
                    c.eG.F[28] = 0;
                    c.a(1, true);
                    c.w(2);
                    return;
                }
                case 1: {
                    eW = 1;
                    n2 = eG.b();
                    if (n2 > 0) {
                        w.a(37, 32, 56, 176, 208, true);
                        c.w.u = n2;
                        w.c();
                        c.w.f = bl2;
                    } else {
                        w.a(57, 50, i.ao, 140, i.an, false);
                        c.a(w, 1145, 0);
                        w.a(20);
                    }
                    c.eG.F[28] = 1;
                    return;
                }
                case 3: {
                    i2 = d[3];
                    b b2 = null;
                    for (n2 = 0; n2 < cQ.length; ++n2) {
                        if (cQ[n2][7] != 1 && cQ[n2][7] != 0) continue;
                        b2 = b.b();
                        b.b().d = n2;
                        b2.c = (byte)3;
                        b2.e = 1000;
                        b2.g = cQ[n2][19];
                        ((b)i2).a(b2);
                        i2 = ((b)i2).b;
                    }
                    w.a(32, 32, 56, 176, 208, true);
                    w.b(8);
                    c.w.p[0] = 1;
                    c.m[c.w.g] = 0;
                    w.c();
                    return;
                }
                case 2: {
                    eW = 2;
                    n2 = d[5].e() - 1;
                    if (n2 > 0) {
                        w.a(38, 32, 56, 176, 208, true);
                        c.w.u = c.d[5].b.e();
                        w.c();
                        c.w.f = bl2;
                    } else {
                        w.a(57, 50, i.ao, 140, i.an, false);
                        c.a(w, 1146, 0);
                        w.c();
                    }
                    c.eG.F[28] = 2;
                }
            }
        }
    }

    static void d(Graphics graphics, h h2) {
        int n2 = m[h2.g];
        cq.b(graphics, 120, 105, 178);
        for (int i2 = 0; i2 < h2.v; ++i2) {
            cq.b(graphics, i.aC[i2], i.aD[i2], i2 + 34);
        }
        aU.b(i.aC[n2], i.aD[n2] + -2);
        c.cq.k = true;
        aU.b(graphics, 0, 0);
        cq.b(graphics, i.aC[n2], i.aD[n2] + -2, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    static void i(h h2) {
        h h3 = h2.b[0];
        h h4 = h2.b[1];
        i i2 = h2.b[2];
        fc = 0;
        c.b(h2, 90, 10);
        if (h3.w >= 0) {
            eX = h3.C.d;
            i i3 = i2 = c.eG.H[23] > 0 ? c.eG.K.a(eV[1] + 1) : c.eG.J.a(eV[1] + 1);
            if (eV[1] >= 0 && eX != ((b)i2).d) {
                c.V();
                return;
            }
            int n2 = h[eX][2];
            int n3 = h[eX][3];
            if (h3.C.h > 0) {
                c.a(h4, 1149, 0);
                h4.a(20);
                return;
            }
            if (c.eG.F[3] < n3) {
                c.a(h4, 1148, 0);
                h4.a(20);
                return;
            }
            if (n2 < 0) {
                if (cY[0] >= Math.abs(n2)) {
                    c.Q(h2);
                    return;
                }
                c.a(h4, 1109, 0);
                h4.a(20);
                return;
            }
            if (c.eG.F[5] >= n2) {
                c.Q(h2);
                return;
            }
            c.a(h4, 1147, 0);
            h4.a(20);
            return;
        }
        if (h3.j != 4 && h3.j != 5) {
            if (h3.j != -1) return;
            h2.b();
            h2.d();
            h2.i();
            return;
        }
        ((h)i2).p[1] = h3.C.d;
        h2.d(c.a(c.c(590 + h3.C.d), 90, 0));
        ((h)i2).d(c.a(c.c(325 + h3.C.d), ((h)i2).t[3] - 14, 1));
    }

    private static void Q(h h2) {
        fe = c.n(1, eX);
        eP = c.x(fe);
        fd = h[eX][1];
        eR = eF;
        int n2 = (fe & 2) == 0 ? 1 : 2;
        aT.c(n2);
        h2.b();
        h2.d();
        h2.i();
        c.a(1, true);
        c.w(2);
    }

    static b c() {
        b b2 = c.eG.H[23] > 0 ? c.eG.K.b : c.eG.J.b;
        return b2;
    }

    static void j(h h2) {
        h h3 = h2.b[0];
        h h4 = h2.b[1];
        h h5 = h2.b[2];
        fc = 0;
        c.b(h2, 164, 10);
        h4.p[1] = h3.C.d;
        if (h3.w >= 0) {
            eX = h3.C.d;
            fe = c.n(2, eX);
            eP = c.x(fe);
            if (eP == 0) {
                c.a(h5, 1086, 0);
                h5.c();
                return;
            }
            fd = cQ[h3.C.d][5];
            eR = eF;
            c.a(1, true);
            aT.c(1);
            c.w(2);
            c.a((byte)3);
            h2.b();
            h2.d();
            h2.i();
            c.w(2);
            return;
        }
        if (h3.j == -1) {
            h2.b();
            h2.d();
            h2.i();
        }
    }

    static void e(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        h h3 = h2.b[0];
        int n6 = m[h3.g] - h3.x;
        int n7 = n3 + 36;
        c.a(graphics, 14876594, n2 + 6, n3 + 17, 164, 100, 3, 4, 5);
        for (int i2 = 0; i2 < h3.v; ++i2) {
            cq.b(graphics, n2 + 22, n7, 112);
            n7 += 22;
        }
        c.a(graphics, h3.D, 590, true, h3.v, n6, 3, n2 + 37, n3 + 25, 115, 22, -1, 90, h2.y);
        c.b(graphics, 113, n2 + 158, n3 + 38, 60, n6, h2.u);
        cq.b(graphics, n2 + 116, n3 + 33 + n6 * 22, 0);
        c.b(graphics, n2, n3, n4, n5, 24);
    }

    static void f(Graphics graphics, h h2) {
        h[] cfr_ignored_0 = h2.b;
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        c.a(graphics, 14876594, n2 + 6, n3 + 17, 164, 100, 3, 4, 5);
        c.b(graphics, n2, n3, n4, n5, 21);
    }

    static void g(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        n4 = n4 / (h2.q + 1) + 5;
        n2 = n2 + n4 - 10;
        for (int i2 = 0; i2 < h2.q; ++i2) {
            c.c(graphics, h2.p[i2], n2, n3 + 3, 17, 0, 0);
            n2 += n4;
        }
    }

    static void a(g g2) {
        a a2 = eM[g2.k[0]];
        eM[g2.k[0]].k = false;
        int n2 = a2.F[23];
        switch (n2) {
            case 1: {
                a2.c(2);
                return;
            }
            case 5: {
                a2.c(1);
                return;
            }
            case 0: 
            case 2: 
            case 4: {
                if (g2.c[6] <= 0) break;
                if (a2.F[28] != 3) {
                    a2.c(1);
                    return;
                }
                a2.c(7);
                return;
            }
            case 3: {
                a2.c(0);
            }
        }
    }

    static void b(g g2) {
        g g3 = g.c();
        g3.a(g2.e, g2.f, g2.g, g2.k[0], g2.l);
        g3.a(2, 0);
        g3.a = g2.a;
        g2.a(g3);
        g3 = g.c();
        g3.a(9, 1);
        g3.a(g2.e);
        g3.j = 3;
        g2.a(g3);
    }

    static void c(g g2) {
        int n2;
        int n3 = g2.l == 0 ? 5 : g2.l;
        g g3 = g.c();
        g3.a(3, 1);
        g3.a(g2.e);
        g3.f = g2.f;
        g3.j = 3;
        g2.a(g3);
        g2.m = 0;
        n3 = Math.min(n3, c.y(c.eM[g2.k[0]].F[20]));
        for (n2 = 0; n2 < n3 - 1; ++n2) {
            g2.k[n2 + 1] = c.b(g2.k[n2], 1, true);
        }
        g g4 = g.c();
        g4.a(7, 1);
        if (g2.l > 0) {
            for (n2 = 0; n2 < n3; ++n2) {
                g4.a(g2.k[n2]);
            }
        } else {
            g4.a(-1);
        }
        g4.j = 3;
        g2.a(g4);
        for (n2 = 0; n2 < n3; ++n2) {
            g4 = g.c();
            g4.a(g2.e, g2.f, g2.g, g2.k[n2], 1);
            g4.a(5, 0);
            g2.a(g4);
        }
        g2.l = n3;
    }

    static void d(g g2) {
        boolean bl2 = false;
        int n2 = 0;
        g g3 = g2.b[0];
        g g4 = g2.b[1];
        g g5 = null;
        switch (g2.h) {
            case 0: {
                while (g2.i >= cU[g2.a][g2.m]) {
                    int n3 = cU[g2.a][g2.m + 1];
                    n2 = cU[g2.a][g2.m + 2];
                    switch (n3) {
                        case 0: {
                            int n4;
                            for (n4 = 0; n4 < g2.l; ++n4) {
                                g g6 = g2.b[n4 + 2];
                                g2.b[n4 + 2].h = 1;
                                g6.m = n2;
                            }
                            break;
                        }
                        case 1: {
                            aM = n2;
                            break;
                        }
                        case 2: {
                            int n5 = n2;
                            int n4 = 4;
                            n2 = 0;
                            n3 = n5;
                            if (aN > 0) break;
                            J = 0;
                            K = (short)4;
                            aN = n3;
                            break;
                        }
                        case 3: {
                            g4.h();
                            g4.g = n2;
                            break;
                        }
                        case 5: {
                            if (n2 > 0) {
                                fb &= 0xFFFFFFEF;
                                break;
                            }
                            fb |= 0x10;
                            break;
                        }
                        case 6: {
                            int n6 = n2;
                            int n4 = g2.l;
                            n2 = g2.k[0];
                            n3 = g2.e;
                            int n7 = 0;
                            int n8 = 0;
                            a a2 = eM[n3];
                            n4 = n4 > 0 ? n4 : 5;
                            switch (n6) {
                                case 0: {
                                    n7 = a2.t;
                                    n8 = a2.u;
                                    break;
                                }
                                case 1: {
                                    a a3 = eM[n2];
                                    n7 = a3.t + (a2.F[20] == 0 ? 50 : -50);
                                    n8 = a3.u;
                                    break;
                                }
                                case 2: {
                                    for (n7 = 0; n7 < n4; ++n7) {
                                        a a4 = eM[n2];
                                        n8 += a4.u;
                                        n2 = c.b(n2, 1, true);
                                    }
                                    n8 /= n4;
                                    n7 = 120;
                                }
                            }
                            a2.b(n7, n8 + 1);
                            break;
                        }
                        case 7: {
                            g3.h();
                            g3.g = n2;
                        }
                    }
                    g2.m += 3;
                    if (g2.m + 2 < cU[g2.a].length) continue;
                    g2.h = 1;
                    return;
                }
                break;
            }
            case 1: {
                g2.h = 2;
                for (int i2 = 0; i2 < g2.l; ++i2) {
                    g5 = g2.b[i2 + 2];
                    g2.b[i2 + 2].h = 2;
                }
                return;
            }
            case 2: {
                if (!g2.e()) break;
                g2.h = 3;
                g g7 = g.c();
                g7.a(8, 0);
                g7.a(g3.k[0]);
                g2.a(g7);
                eZ = 0;
                g2.h = 3;
                return;
            }
            case 3: {
                if (!g2.e()) break;
                g2.j = 3;
            }
        }
    }

    static void e(g g2) {
        boolean bl2 = true;
        for (int i2 = 0; i2 < g2.l; ++i2) {
            a a2 = g2.k[i2] >= 0 ? eN[g2.k[i2]] : eO;
            if (a2.l) continue;
            bl2 = false;
            break;
        }
        if (bl2) {
            g2.j = 3;
        }
    }

    static void f(g g2) {
        if (g2.e()) {
            g2.j = 3;
        }
    }

    static void g(g g2) {
        boolean bl2 = true;
        for (int i2 = 0; i2 < g2.l; ++i2) {
            a a2 = eM[g2.k[i2]];
            if (a2.l) continue;
            bl2 = false;
            break;
        }
        if (bl2) {
            g2.j = 3;
        }
    }

    static void h(g g2) {
        g g3 = g2.b[0];
        g g4 = g2.b[1];
        int n2 = g2.k[0];
        a a2 = eM[n2];
        switch (g2.h) {
            case 0: {
                return;
            }
            case 1: {
                int n3;
                c.a(a2, g2.m, g2.c);
                int n4 = g2.g;
                int n5 = g2.f;
                int n6 = g2.k[0];
                int n7 = g2.e;
                a a3 = eM[n7];
                a a4 = eM[n6];
                int n8 = c.c(90, 110);
                int n9 = bE >= 0 ? 100 : c.c(0, 100);
                int n10 = n3 = bE >= 0 ? 0 : c.c(0, 100);
                if (a4.F[22] >= 0) {
                    switch (n5) {
                        case 0: {
                            a4.F[23] = 0;
                            break;
                        }
                        case 1: {
                            a4.F[23] = 4;
                            n9 = 100;
                            if (ft[6] > 0) break;
                            n3 = 100;
                            break;
                        }
                        case 2: {
                            a4.F[23] = 3;
                            n9 = 100;
                            n3 = 100;
                        }
                    }
                    if (n9 < Math.min(99, a4.H[43] > 0 ? 0 : a4.F[13] + a4.H[17])) {
                        a4.F[22] = 0;
                        a4.F[23] = 1;
                    } else {
                        n8 /= a4.F[28] == 3 ? 2 : 1;
                        if (n3 < Math.min(99, a3.F[12] + a3.H[15]) + h[n4][4]) {
                            n8 = a4.F[20] == 1 ? (n8 <<= 2) : (n8 *= 3);
                            a4.F[23] = 2;
                        } else {
                            n8 <<= 1;
                        }
                        a4.F[22] = a4.F[22] * n8 / 200;
                        a4.F[4] = a4.F[4] - a4.F[22];
                        a4.F[4] = Math.max(a4.F[4], 0);
                        if (a4.H[24] == 0) {
                            a4.F[3] = a4.F[3] + a4.F[22] * a4.F[16] / a4.F[7];
                            a4.F[3] = Math.min(a4.F[3], a4.F[6]);
                        }
                        fg = a3.H[24] == 0;
                    }
                }
                g3.h();
                g4.h();
                g2.h = 0;
                if (g2.f != 0 || a2.F[23] == 1) break;
                eN[n2].c(15);
                return;
            }
            case 2: {
                if (!g2.e()) break;
                g g5 = g2;
                a a5 = a2;
                if (a5.F[4] > 0) {
                    for (int i2 = 40; i2 < 71; ++i2) {
                        if (g5.c[i2] > 0) {
                            if ((a5.F[24] & 1 << i2 - 40) != 0) continue;
                            a5.H[i2] = g5.c[i2];
                            continue;
                        }
                        if (g5.c[i2] >= 0) continue;
                        a5.H[i2] = 0;
                    }
                } else {
                    for (int i3 = 40; i3 < 71; ++i3) {
                        a5.H[i3] = 0;
                    }
                }
                a2.f();
                g4 = g.c();
                g4.k[0] = g3.k[0];
                g4.a(8, 0);
                g2.a(g4);
                g2.h = 3;
                return;
            }
            case 3: {
                if (!g2.e()) break;
                g2.j = 3;
            }
        }
    }

    static void i(g g2) {
        int n2 = g2.g;
        int n3 = g2.f;
        int n4 = g2.k[0];
        int n5 = g2.e;
        int n6 = 0;
        i i2 = eM[n5];
        a a2 = eM[n4];
        if (bE >= 0) {
            c.ft[6] = bE;
        } else {
            int n7 = a2.F[11] + a2.H[12];
            switch (n3) {
                case 0: {
                    n3 = ((a)i2).F[9] + ((a)i2).H[16];
                    c.ft[6] = n6 = n3 * 200 / (n7 + 200);
                    break;
                }
                case 1: {
                    n3 = ((a)i2).F[10] + ((a)i2).H[18];
                    a a3 = a2;
                    int n8 = eX;
                    n6 = 0;
                    int n9 = 0;
                    int n10 = 0;
                    c.Y();
                    for (int i3 = 6; i3 <= 12; i3 += 3) {
                        n6 = h[n8][i3];
                        n9 = h[n8][i3 + 1];
                        n10 = h[n8][i3 + 2];
                        int n11 = c.c(0, 100);
                        if (n6 == 0 || n11 >= n10) continue;
                        c.ft[n6] = n9;
                    }
                    if (ft[5] > 0 && (a3.F[21] & 1) == 0) {
                        c.ft[5] = 0;
                    }
                    if ((ft[1] > 0 || ft[2] > 0) && a3.F[4] == a3.F[7]) {
                        c.ft[1] = 0;
                        c.ft[2] = 0;
                    }
                    if ((ft[3] > 0 || ft[4] > 0) && a3.F[5] == a3.F[8]) {
                        c.ft[3] = 0;
                        c.ft[4] = 0;
                    }
                    if ((n6 = ft[6] + n3 * ft[8] / 100) <= 0) break;
                    c.ft[6] = n6 = n6 * 200 / (n7 + 200);
                    break;
                }
                case 2: {
                    c.Y();
                    c.a(n2, a2);
                }
            }
            ((a)i2).F[3] = Math.min(((a)i2).F[3], ((a)i2).F[6]);
        }
        g2.g();
        System.arraycopy(ft, 0, g2.c, 0, 71);
        i2 = g.c();
        ((g)i2).a(4, 1);
        ((g)i2).a(g2.k[0]);
        ((g)i2).g();
        System.arraycopy(ft, 0, ((g)i2).c, 0, 71);
        g2.a((g)i2);
        i2 = g.c();
        ((g)i2).a(6, 1);
        ((g)i2).a(g2.k[0]);
        ((g)i2).g();
        System.arraycopy(ft, 0, ((g)i2).c, 0, 71);
        g2.a((g)i2);
    }

    /*
     * Handled duff style switch with additional control
     * Enabled aggressive block sorting
     */
    static void j(g g2) {
        a a2 = eM[g2.k[0]];
        int n2 = a2.F[20];
        int n3 = a2.t + (n2 == 0 ? 30 : -20);
        int n4 = a2.u - 60;
        int n5 = n2 == 0 ? 24 : 20;
        int n6 = Integer.MIN_VALUE;
        block8: do {
            switch (n6 == Integer.MIN_VALUE ? a2.F[23] : n6) {
                case 2: {
                    c.c(n3 - 10, n4, -a2.F[22], 56, 0, n5);
                    return;
                }
                case 4: {
                    n2 = g2.c[1] + g2.c[2] * a2.F[7] / 100;
                    int n7 = g2.c[3] + g2.c[4] * a2.F[8] / 100;
                    if (n2 != 0) {
                        c.c(n3, n4 - 10, n2, 46, 0, n5);
                    }
                    if (n7 != 0) {
                        c.c(n3, n4, n7, 46, 0, n5);
                    }
                    if (g2.c[6] <= 0) return;
                    n6 = 5;
                    continue block8;
                }
                case 0: {
                    c.c(n3, n4, -a2.F[22], 36, 0, n5);
                    return;
                }
                case 3: {
                    n2 = g2.c[1] + g2.c[2] * a2.F[7] / 100;
                    int n8 = g2.c[3] + g2.c[4] * a2.F[8] / 100;
                    if (n2 != 0) {
                        c.c(n3, n4 - 10, n2, 46, 0, n5);
                    }
                    if (n8 == 0) return;
                    c.c(n3, n4, n8, 46, 0, n5);
                    return;
                }
                case 1: {
                    n3 = a2.t + -20;
                    c.c(n3 - 10, n4, 0, 40, 1, n5);
                    return;
                }
                case 5: {
                    c.c(n3, n4, -a2.F[22], 36, 0, n5);
                    return;
                }
            }
            return;
        } while (true);
    }

    static void k(g g2) {
        g2.j = 3;
    }

    private static void c(int n2, int n3, int n4, int n5, int n6, int n7) {
        if (eZ < 32) {
            int n8 = eZ * 7;
            c.fa[n8] = n2;
            c.fa[n8 + 1] = n3;
            c.fa[n8 + 2] = n4;
            c.fa[n8 + 3] = n5;
            c.fa[n8 + 4] = n6;
            c.fa[n8 + 5] = 0;
            c.fa[n8 + 6] = n7;
            ++eZ;
        }
    }

    static void l(g g2) {
        switch (g2.h) {
            case 0: {
                if (!g2.e()) break;
                g g3 = g.c();
                g3.k[0] = g2.k[0];
                g3.a(8, 0);
                g2.a(g3);
                g2.h = 1;
                return;
            }
            case 1: {
                if (!g2.e()) break;
                g2.j = 3;
            }
        }
    }

    static void m(g g2) {
        a a2 = eM[g2.k[0]];
        eM[g2.k[0]].k = false;
        a2.c(g2.g);
    }

    static void n(g g2) {
        for (int i2 = 0; i2 < g2.l; ++i2) {
            a a2 = g2.k[i2] >= 0 ? eN[g2.k[i2]] : eO;
            a2.c(g2.g);
            a2.k = false;
        }
    }

    static void o(g g2) {
        int n2 = g2.k[0];
        a a2 = eM[n2];
        eM[n2].v = a2.t;
        a2.w = a2.u;
        if (a2.F[4] <= 0) {
            a a3 = a2;
            a3.F[4] = 0;
            a3.F[5] = 0;
            a3.F[30] = 0;
            a3.F[21] = a3.F[21] | 1;
            a2.c(4);
            a2.k = false;
            if (a2.F[20] == 1) {
                a2.E[1] = 0;
                eN[n2].c(14);
                c.eN[n2].k = false;
                c.b(3, 1, 99999);
                return;
            }
        } else {
            if (a2.F[28] == 3) {
                a2.c(6);
                a2.k = true;
                return;
            }
            a2.k = true;
            if (a2.F[4] <= a2.F[7] / 3) {
                a2.c(3);
                return;
            }
            a2.c(0);
        }
    }

    static void p(g g2) {
        fc = 7;
        if (g2.i >= 5) {
            g2.j = 3;
            aV = 42;
            return;
        }
        aV -= 25;
    }

    static void d() {
        aV = 169;
        fc = 7;
    }

    static void q(g g2) {
        int n2 = g2.m * 7;
        dL = bD[n2 + 3];
        dN = bD[n2 + 1];
        dO = bD[n2 + 2];
        dM = bD[n2];
        dP = bD[n2 + 6];
        dQ = -1;
        c.h(0, 2, 3);
        fc = 3;
    }

    static void r(g g2) {
        fb = 48;
        fc = 3;
        if (dM >= 0) {
            cG[dM].e();
        }
        int n2 = c.b(264224);
        int n3 = c.b(n2 != 0);
        if (n2 != 0) {
            if (n3 == 0) {
                dH = 1;
                g2.j = 3;
                for (n2 = 0; n2 < g2.d; ++n2) {
                    g2.b[n2].j = 1;
                }
                return;
            }
            dH = 0;
        }
    }

    static void s(g g2) {
        int n2 = g2.k[0];
        a a2 = eM[n2];
        g g3 = g.c();
        g3.a(6, 0);
        g2.a(g3);
        g g4 = g.c();
        g4.a(4, 0);
        g2.a(g4);
        if (a2.H[9] != 0) {
            a2.F[4] = a2.F[4] + a2.H[9];
            a2.F[23] = 5;
            a2.F[22] = -a2.H[9];
            a2.F[4] = Math.max(a2.F[4], 1);
            g3.a(n2);
            g4.a(n2);
            return;
        }
        g3.j = 3;
        g4.j = 3;
        g2.j = 3;
    }

    static void t(g g2) {
        a a2 = eM[g2.k[0]];
        a2.F[23] = 0;
        a2.F[4] = a2.F[4] - a2.F[22];
        a2.c(g2.g);
        a2.k = false;
        a2.l = false;
    }

    static void u(g g2) {
        g g3 = g.c();
        g3.a(1, 0);
        g3.a(g2.e, g2.f, g2.g, g2.k[0], g2.l);
        g3.a = g2.a;
        g2.a(g3);
        fg = false;
    }

    static void v(g g2) {
        g g3 = g.c();
        g3.a(14, 0);
        g2.a(g3);
        g g4 = g.c();
        g4.a(15, 1);
        g2.a(g4);
        g g5 = g.c();
        g5.a(16, 0);
        g2.a(g5);
        switch (g2.f) {
            case 0: {
                g3.g = 5;
                g5.g = 6;
                for (int i2 = 0; i2 < fl; ++i2) {
                    if ((c.eM[i2].F[21] & 1) != 0) continue;
                    g3.a(i2);
                }
                g4.a(40);
                g4.a(41);
                return;
            }
            case 1: {
                g5.g = 7;
                g4.a(42);
            }
        }
    }

    static void w(g g2) {
        g g3 = g2.b[1];
        if (g2.i == 10) {
            g3.h();
        }
        if (g2.e()) {
            g2.j = 3;
        }
    }

    static void x(g g2) {
        a a2 = null;
        for (int i2 = 0; i2 < g2.l; ++i2) {
            a2 = eM[i2];
            a2.c(g2.g);
            a2.k = false;
        }
    }

    static void y(g g2) {
        boolean bl2 = true;
        for (int i2 = 0; i2 < g2.l; ++i2) {
            if (c.eM[i2].l) continue;
            bl2 = false;
            break;
        }
        if (bl2 && g2.e()) {
            g2.j = 3;
        }
    }

    static void z(g g2) {
        g2.m = 0;
    }

    static void A(g g2) {
        if (t == null) {
            if (g2.m < g2.l) {
                t = h.g();
                t.a(g2.k[g2.m], 32, 56, 176, 208, true);
                c.t.h = true;
                ++g2.m;
                return;
            }
            if (g2.e()) {
                g2.j = 3;
                return;
            }
        } else {
            t.j();
        }
    }

    static void B(g g2) {
        if ((k & 1) != 0) {
            c.au();
            c.r(g2.g, 1);
        }
    }

    static void C(g g2) {
        if (g2.e()) {
            g2.j = 3;
        }
    }

    private static void Z() {
        g.n = true;
        s.f();
        if (g.n) {
            for (int i2 = 0; i2 < eQ; ++i2) {
                c.eM[i2].F[22] = 0;
                eM[i2].b(c.eM[i2].t, c.eM[i2].u);
            }
            i i3 = eG;
            i3 = i3.J.b;
            while (i3 != null) {
                if (((b)i3).h > 0) {
                    ((b)i3).h = (short)(((b)i3).h - 1);
                }
                i3 = ((b)i3).b;
            }
            if (fg) {
                c.eG.F[3] = c.eG.F[3] + 5;
                fg = false;
            }
            c.S();
            c.Y();
            c.y.f = false;
            c.w(c.eG.F[29] > 0 ? 1 : 5);
        }
    }

    private static void z(int n2) {
        if (bg < bh) {
            if ((bg += n2) >= bh) {
                bg = bh;
                dn = false;
                return;
            }
        } else if (bg > bh && (bg -= n2) <= bh) {
            bg = bh;
            dn = false;
            cI = false;
        }
    }

    private static void k(Graphics graphics) {
        if (cI) {
            graphics.setClip(0, 0, 240, 320);
            switch (bf) {
                case 0: {
                    for (int i2 = 0; i2 < 6; ++i2) {
                        for (int i3 = 0; i3 < 8; ++i3) {
                            c.a(graphics, 0, i2 * 40, i3 * 40, bg, bg);
                        }
                    }
                    return;
                }
                case 1: {
                    c.a(graphics, 0, 0, 0, bg, 320);
                    c.a(graphics, 0, bg + 8, 0, 15, 320);
                    c.a(graphics, 0, bg + 29, 0, 10, 320);
                    c.a(graphics, 0, bg + 43, 0, 5, 320);
                    return;
                }
                case 2: {
                    int n2;
                    for (n2 = 0; n2 < 10; n2 += 2) {
                        c.a(graphics, 0, 0, n2 << 5, bg, 32);
                    }
                    for (n2 = 1; n2 < 10; n2 += 2) {
                        c.a(graphics, 0, 240 - bg, n2 << 5, bg, 32);
                    }
                    return;
                }
                case 3: {
                    graphics.setClip(0, 0, 240, 320);
                    graphics.setColor(0);
                    graphics.fillArc(-80, -40, 400, 400, 0, bg);
                    return;
                }
                case 4: {
                    c.a(graphics, 0, 0, 0, 240, bg);
                    c.a(graphics, 0, 0, bg + 8, 240, 15);
                    c.a(graphics, 0, 0, bg + 29, 240, 10);
                    c.a(graphics, 0, 0, bg + 43, 240, 5);
                    return;
                }
                case 5: {
                    c.a(graphics, 0, 0, 0, bg, 320);
                    return;
                }
                case 6: {
                    c.a(graphics, 0, 0, 0, 240, bg);
                    c.a(graphics, 0, 0, 320 - bg, 240, bg);
                }
            }
        }
    }

    /*
     * Handled duff style switch with additional control
     * Enabled aggressive block sorting
     */
    private static void o(int n2, int n3) {
        block23: while (true) {
            be = n2;
            switch (be) {
                case 2: {
                    bh = n3;
                    n3 = 7;
                    n2 = 1;
                    continue block23;
                }
                case 3: {
                    c.o(0, 7);
                    return;
                }
                case 1: {
                    bf = n3 > 0 ? (byte)(n3 - 1) : (byte)((bY.nextInt() >>> 1) % 4);
                    switch (bf) {
                        case 0: {
                            bg = 0;
                            bh = 40;
                            break;
                        }
                        case 1: {
                            bg = -43;
                            bh = 240;
                            break;
                        }
                        case 2: {
                            bg = 0;
                            bh = 240;
                            break;
                        }
                        case 3: {
                            bg = 0;
                            bh = 360;
                            break;
                        }
                        case 4: {
                            bg = -43;
                            bh = 160;
                            break;
                        }
                        case 5: {
                            bg = 240;
                            bh = 240;
                            break;
                        }
                    }
                    cI = true;
                    return;
                }
                case 0: {
                    int n4 = Integer.MIN_VALUE;
                    block24: do {
                        switch (n4 == Integer.MIN_VALUE ? bf : n4) {
                            case 0: {
                                bg = 40;
                                n4 = 6;
                                continue block24;
                            }
                            case 1: {
                                bg = 240;
                                bh = -43;
                                break;
                            }
                            case 2: {
                                bg = 240;
                                n4 = 6;
                                continue block24;
                            }
                            case 3: {
                                bg = 360;
                                n4 = 6;
                                continue block24;
                            }
                            case 4: {
                                bg = 160;
                                bh = -43;
                                break;
                            }
                            case 5: {
                                bg = 0;
                            }
                            case 6: {
                                bh = 0;
                                break;
                            }
                        }
                        break;
                    } while (true);
                    cI = true;
                    return;
                }
            }
            break;
        }
    }

    private static void aa() {
        fi = 0;
        fh = 0;
        fi = (short)16;
    }

    private static void ab() {
        if ((fh = (short)(fh + 1)) > fi) {
            bj = 4;
            c.b((byte)8);
        }
    }

    private static void l(Graphics graphics) {
        for (int i2 = 0; i2 < 15; ++i2) {
            c.a(graphics, 0, i2 * 16, 0, (int)fh, 320);
        }
    }

    private static void ac() {
        int n2;
        fj = bI << 4 < 240 && bI > 0 || bJ << 4 < 320 && bJ > 0;
        for (n2 = 0; n2 < bG; ++n2) {
            if (bN[n2] == null) continue;
            c.bO[n2] = bN[n2].getWidth() / 16;
        }
        L = (short)((bI << 4) - 240);
        M = (short)((bJ << 4) - bV);
        for (n2 = 0; n2 < 6; ++n2) {
            c.aO[n2] = 0;
        }
    }

    private static void ad() {
        bL = null;
        int n2 = bI << 4 < 240 ? bI << 4 : bS;
        int n3 = bJ << 4 < 320 ? bJ << 4 : bT;
        bL = Image.createImage((int)n2, (int)n3);
        bM = bL.getGraphics();
    }

    private static void ae() {
        bL = null;
        bM = null;
        System.gc();
    }

    /*
     * Unable to fully structure code
     */
    private static void af() {
        block26: {
            block25: {
                block24: {
                    block23: {
                        c.F = c.D;
                        c.G = c.E;
                        switch (c.aR) {
                            case 0: {
                                break;
                            }
                            case 1: {
                                break;
                            }
                            case 3: {
                                if (c.D < c.H) {
                                    c.D = (short)Math.min(c.H, c.D + c.J);
                                } else if (c.D > c.H) {
                                    c.D = (short)Math.max(c.H, c.D + c.J);
                                }
                                if (c.E < c.I) {
                                    c.E = (short)Math.min(c.I, c.E + c.K);
                                } else if (c.E > c.I) {
                                    c.E = (short)Math.max(c.I, c.E + c.K);
                                }
                                if (c.D != c.H || c.E != c.I) break;
                                c.aR = c.aS == -1 ? 1 : c.aS;
                                break;
                            }
                            case 2: {
                                c.D = (short)(c.aP.v - 120);
                                c.E = (short)(c.aP.w - 160);
                            }
                        }
                        if (c.D >= 0) break block23;
                        c.D = 0;
                        if (c.bI <= 0 || c.bI << 4 >= 240) break block24;
                        ** GOTO lbl-1000
                    }
                    if (c.D > c.L) {
                        c.D = 0;
                        if (c.bI > 0) {
                            ** if (c.bI << 4 < 240) goto lbl-1000
lbl-1000:
                            // 1 sources

                            {
                                c.D = c.L;
                                ** GOTO lbl38
                            }
                        }
                    }
                    break block24;
lbl-1000:
                    // 2 sources

                    {
                        c.D = (short)(-240 + (c.bI << 4) >> 1);
                    }
                }
                if (c.E >= 0) break block25;
                c.E = 0;
                if (c.bI <= 0 || c.bJ << 4 >= 320) break block26;
                ** GOTO lbl-1000
            }
            if (c.E > c.M) {
                c.E = 0;
                if (c.bI > 0) {
                    ** if (c.bJ << 4 < 320) goto lbl-1000
lbl-1000:
                    // 1 sources

                    {
                        c.E = c.M;
                        ** GOTO lbl52
                    }
                }
            }
            break block26;
lbl-1000:
            // 2 sources

            {
                c.E = (short)(-320 + (c.bJ << 4) >> 1);
            }
        }
        if (c.aR == 3 && c.D == c.F && c.E == c.G) {
            v0 = c.aR = c.aS == -1 ? 1 : c.aS;
        }
        if (c.aL >= 0) {
            if (c.aM >= c.aL) {
                c.aL = -1;
                c.aM = 0;
                return;
            }
            ++c.aM;
        }
    }

    private static void a(int n2, int n3, int n4, a a2, int n5) {
        block24: {
            block23: {
                aR = n2;
                if (n5 != 0) break block23;
                H = (short)(n3 - 120);
                I = (short)(n4 - 160);
                J = 0;
                K = 0;
                switch (aR) {
                    case 0: {
                        if (aP != null) {
                            c.aP.E[4] = 0;
                        }
                        aS = -1;
                        break block24;
                    }
                    case 1: {
                        if (aP != null) {
                            c.aP.E[4] = 0;
                        }
                        aS = -1;
                        break block24;
                    }
                    case 2: {
                        if (aP != null) {
                            c.aP.E[4] = 0;
                        }
                        aP = a2;
                        c.aP.E[4] = 1;
                        D = (short)(c.aP.v - 120);
                        E = (short)(c.aP.w - 160);
                        if (bI > 0) {
                            if (bI << 4 < 240) {
                                D = (short)(-240 + (bI << 4) >> 1);
                            }
                            if (bJ << 4 < 320) {
                                E = (short)(-320 + (bJ << 4) >> 1);
                            }
                        }
                        aS = -1;
                        break block24;
                    }
                    case 3: {
                        if (aP != null) {
                            c.aP.E[4] = 0;
                        }
                        aP = a2;
                        aS = -1;
                        D = (short)(c.aP.v - 120);
                        E = (short)(c.aP.w - 160);
                    }
                    default: {
                        return;
                    }
                }
            }
            H = (short)(n3 - 120);
            I = (short)(n4 - 160);
            J = (short)c.b(H - D, n5);
            K = (short)c.b(I - E, n5);
            switch (aR) {
                case 0: {
                    if (aP != null) {
                        c.aP.E[4] = 0;
                    }
                    aR = 3;
                    aS = 0;
                    return;
                }
                case 1: {
                    if (aP != null) {
                        c.aP.E[4] = 0;
                    }
                    aR = 3;
                    aS = 1;
                    return;
                }
                case 2: {
                    H = (short)(a2.v - 120);
                    I = (short)(a2.w - 160);
                    J = (short)c.b(H - D, n5);
                    K = (short)c.b(I - E, n5);
                    aP = a2;
                    if (aP != null) {
                        c.aP.E[4] = 0;
                    }
                    aP = a2;
                    c.aP.E[4] = 1;
                    aR = 3;
                    aS = 2;
                    return;
                }
                case 3: {
                    if (aP != null) {
                        c.aP.E[4] = 0;
                    }
                    aP = a2;
                    J = (short)c.b(H - D, n5);
                    K = (short)c.b(I - E, n5);
                    aR = 3;
                    aS = 1;
                }
            }
        }
    }

    private static void ag() {
        if (aN != 0) {
            for (int i2 = 0; i2 < 2; ++i2) {
                if (aO[i2 + 2] > 0) {
                    int n2 = i2 + 0;
                    aO[n2] = (short)(aO[n2] + aO[i2 + 4]);
                    if (Math.abs(aO[i2 + 0]) < aO[i2 + 2]) continue;
                    c.aO[i2 + 4] = -aO[i2 + 4];
                    if (aO[i2 + 0] > 0) {
                        c.aO[i2 + 0] = aO[i2 + 2];
                        continue;
                    }
                    c.aO[i2 + 0] = -aO[i2 + 2];
                    continue;
                }
                c.aO[i2 + 0] = 0;
            }
            if (aN > 0) {
                --aN;
            }
            D = (short)(D + aO[0]);
            E = (short)(E + aO[1]);
        }
    }

    private static void a(b b2, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        if (b2 == null) {
            b2 = b.b();
            b.b().c = (byte)n3;
            b2.d = n2;
            b2.e = n6;
            b2.f = n7;
            b2.g = n4;
            b b3 = d[n5].b(n2);
            b3.a(b2);
        } else {
            b2.e += n6;
        }
        if (b2.e <= 0) {
            b2.g();
            b2.c();
            return;
        }
        if (n8 > 0) {
            b2.e = Math.min(b2.e, n8);
        }
    }

    private static void k(int n2, int n3, int n4) {
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = n2;
        int n9 = n3;
        int n10 = 0;
        switch (n9) {
            case 2: {
                n10 = 9999999;
                break;
            }
            case 0: 
            case 3: {
                n10 = cQ[n8][15];
                break;
            }
            case 1: {
                n10 = cS[n8][15];
                break;
            }
            case 8: {
                n10 = 1;
            }
        }
        n9 = n10;
        switch (n3) {
            case 2: {
                c.b(0, n4, n9);
                if (n4 < 0) {
                    c.b(2, Math.abs(n4), 9999999);
                }
                return;
            }
            case 0: {
                n7 = 0;
                n3 = cQ[n2][0];
                n5 = i.X[cQ[n2][17]];
                n6 = cQ[n2][19];
                break;
            }
            case 1: {
                n7 = 2;
                n3 = cS[n2][0];
                n5 = i.X[cS[n2][16]];
                n6 = cS[n2][19];
                break;
            }
            case 3: {
                n7 = 5;
                n5 = i.X[cQ[n2][17]];
                n6 = cQ[n2][19];
                break;
            }
            case 6: {
                n7 = 6;
                n5 = 0;
                break;
            }
            case 8: {
                n7 = 10;
                n3 = cV[n2][0];
                n5 = i.X[cV[n2][16]];
                n6 = cV[n2][17];
                break;
            }
            case 9: {
                c.b(1, n4, 999);
                return;
            }
            case 10: {
                n7 = 12;
                n3 = -1;
                n5 = i.X[cW[n2][0]];
                n6 = cW[n2][1];
                break;
            }
            case 11: {
                n7 = 13;
                n3 = n4;
                n4 = 1;
                break;
            }
            case 12: {
                n7 = 14;
                n3 = cX[n2][0];
            }
        }
        b b2 = c.q(n7, n2);
        c.a(b2, n2, n3, n6, n7, n4, n5, n9);
    }

    private static void g(int n2, int n3, int n4, int n5) {
        int n6 = 0;
        b b2 = null;
        b b3 = null;
        switch (n3) {
            case 0: 
            case 3: {
                b3 = d[5].c(n2);
                n6 = cQ[n2][15];
                c.a(b3, n2, n3, n5, 5, n4, b3 == null ? 0 : b3.f, n6);
                return;
            }
            case 1: {
                b2 = c.d[2].b;
                n6 = cS[n2][15];
                while (b2 != null) {
                    if (b2.d == n2 && b2.c == n3) {
                        b3 = b2;
                    }
                    b2 = b2.b;
                }
                c.a(b3, n2, n3, n5, 2, n4, b3.f, n6);
            }
        }
    }

    private static int p(int n2, int n3) {
        int n4 = 0;
        int n5 = 0;
        switch (n3) {
            case 2: {
                n4 = cY[0];
                return n4;
            }
            case 1: {
                n5 = 2;
                break;
            }
            case 0: {
                n5 = 0;
                break;
            }
            case 6: {
                n5 = 6;
            }
        }
        b b2 = c.q(n5, n2);
        if (b2 != null) {
            n4 = b2.e;
        }
        return n4;
    }

    private static void ah() {
        for (int i2 = 0; i2 < 15; ++i2) {
            d[i2].d();
            c.d[i2].b = null;
        }
        c.ai();
    }

    private static void ai() {
        e.d();
        c.e.b = null;
    }

    static final int a(int n2, int n3, int n4, int n5, int n6, boolean bl2) {
        n = -1;
        o = false;
        if (m[n2] < n3) {
            if (c.b(n4)) {
                int n7 = n2;
                m[n7] = m[n7] + 1;
                if (!bl2) {
                    c.m[n2] = Math.min(m[n2], n3 - 1);
                }
            } else if (c.b(n5)) {
                int n8 = n2;
                m[n8] = m[n8] - 1;
                if (!bl2) {
                    c.m[n2] = Math.max(m[n2], 0);
                }
            }
        }
        if (c.b(n6)) {
            n = m[n2];
            if (r[0] >= 0) {
                if (r[0] != n) {
                    c.j();
                    n = -1;
                } else {
                    c.r[0] = -1;
                    fJ = true;
                }
            }
        }
        if (bl2 && (m[n2] < 0 || m[n2] >= n3)) {
            c.m[n2] = (m[n2] + n3) % n3;
            o = true;
        }
        return m[n2];
    }

    private static void aj() {
        if (fo != null) {
            fo = null;
        }
        if (fp != null) {
            fp = null;
        }
        if (fq != null) {
            fq = null;
        }
        if (cC != null) {
            cC = null;
        }
        System.gc();
    }

    private static void ak() {
        switch (cb) {
            case 0: {
                if ((k & 1) != 0) {
                    c.au();
                }
                if (eA != null) {
                    eA = null;
                    ez.a();
                    ez = null;
                }
                c.x();
                break;
            }
            case 1: {
                c.w();
                c.C();
                c.ar();
                break;
            }
            case 2: {
                c.ah();
                c.as();
                break;
            }
            case 3: {
                c.y();
                c.ae();
                break;
            }
            case 4: {
                cI = false;
                bg = 0;
                bh = 0;
                bG = 0;
                a = null;
                u.d();
                for (int i2 = 0; i2 < 4; ++i2) {
                    c.aZ[i2] = 0;
                }
                c.r[0] = -1;
                break;
            }
            case 6: {
                u.e(46);
                c.u.f = true;
                c.u.h = true;
                int n2 = 0;
                while (n2 < 10) {
                    int n3 = n2 == 6 ? 100 : 0;
                    int n4 = n2++;
                    c.cY[n4] = n3;
                }
                break;
            }
            case 8: {
                if (fo != null) break;
                try {
                    fo = Image.createImage((String)"/splashAnim");
                }
                catch (IOException iOException) {
                    IOException iOException2 = iOException;
                    iOException.printStackTrace();
                }
                fp = new a(11, 11, 0, fo, false);
                fq = new a(11, 11, 0, fo, true);
                break;
            }
            case 10: {
                for (int i3 = 0; i3 < 4; ++i3) {
                    g[i3].c();
                }
                break;
            }
            case 14: {
                try {
                    cC = Image.createImage((String)"/link");
                }
                catch (IOException iOException) {}
                System.gc();
                break;
            }
            case 16: {
                if ((k & 1) != 0) {
                    c.C(0);
                } else {
                    fz = 0;
                    c.au();
                }
                c.b((byte)5);
                for (int i4 = 0; i4 < 2; ++i4) {
                    if (dC[i4][0] <= 0) continue;
                    c.m[1] = 1;
                }
                break;
            }
        }
        ++cb;
    }

    private static void c(int n2, int n3, int n4, int n5, int n6) {
        t = h.g();
        t.a(n2, 32, n4, n5, n6, true);
        c.t.h = true;
        fB = 0;
        c.r[0] = -1;
        fJ = true;
        c.a((byte)4);
    }

    static int d(int n2, int n3) {
        n2 = 0;
        b b2 = c.d[12].b;
        while (b2 != null) {
            if (b2.c == -1) {
                b b3 = b.b();
                b3.b(b2);
                e.f().a(b3);
                ++n2;
            }
            b2 = b2.b;
        }
        return n2;
    }

    private static void b(a a2, int n2) {
        if (n2 >= 0) {
            a2.c(n2);
            a2.b(0, 0);
            a2.k = true;
        }
    }

    static void h(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        c.a(graphics, h2.i, n2 + 5, n3 + 5, n4 - 10, h2.z, 20, 0, 0, 1, 0);
    }

    static void e() {
        if (t != null) {
            t.h();
            t = null;
            el = 0;
            fC = 0;
            fG = 0;
        }
    }

    private static void a(h h2, int n2, int n3) {
        int n4 = c.a(c.c(n2), h2.t[2] - 10, 1);
        h2.i = n2;
        h2.d(n4);
        h2.t[3] = n4 + 10 + n3;
        h2.t[1] = 160 - (h2.t[3] >> 1);
    }

    static void k(h h2) {
        fc = 0;
        if (c.b(264224) || h2.j == -1) {
            h2.j = -1;
            h2.a();
            h2.f = false;
            if (cd == 3 && c.y.f) {
                c.y.f = false;
            }
        }
    }

    static void l(h h2) {
        if (c.b(264224)) {
            h2.j = 1;
            h2.b();
        }
    }

    static void m(h h2) {
        if (c.b(258)) {
            --h2.x;
            h2.x = Math.max(h2.x, 0);
            return;
        }
        if (c.b(16388)) {
            ++h2.x;
            h2.x = Math.min(h2.x, Math.max(h2.u - h2.v, 0));
            return;
        }
        if (c.b(788512)) {
            h2.b();
        }
    }

    static void n(h h2) {
        int n2;
        h h3 = h2.b[0];
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        for (n2 = 0; n2 < h2.u; ++n2) {
            if (c.fk[n2].E[1] != 1) continue;
            fk[n2].e();
        }
        if (h2.y < 15) {
            for (n2 = 0; n2 < h2.u; ++n2) {
                n5 = h2.p[n3];
                n4 = h2.y == 14 ? h2.p[n3 + 1] : h2.p[n3 + 2] / 15;
                n6 = 0;
                int n7 = n4;
                n6 = n5;
                a a2 = g[n6];
                int n8 = 0;
                a2.F[1] = a2.F[1] + n7;
                while (a2.F[1] >= a2.F[2] && a2.F[15] < cN[n6][6] - 1) {
                    a2.F[15] = a2.F[15] + 1;
                    c.t(n6);
                    n8 = 1;
                }
                if (n8 != 0) {
                    a2.F[4] = a2.F[7];
                    a2.F[5] = a2.F[8];
                }
                n6 = n8;
                int n9 = n3 + 1;
                h2.p[n9] = h2.p[n9] - n4;
                n3 += 3;
                if (n6 == 0) continue;
                n4 = c.s(n5);
                fk[n2].c(0);
                c.fk[n2].E[1] = 1;
                if (n4 < 0) continue;
                h3.c(n5);
                h3.c(n4);
            }
            ++h2.y;
            return;
        }
        if (h3.j == -1) {
            h3.j = 0;
            h2.b();
            return;
        }
        if (c.b(788512)) {
            if (h3.q > 0) {
                h3.c();
                return;
            }
            h2.b();
        }
    }

    static void o(h h2) {
        if (c.b(788512)) {
            if (h2.u < h2.q / 2 - 1) {
                ++h2.u;
                return;
            }
            h2.j = -1;
            h2.b();
        }
    }

    static void a(int n2, h h2, b b2) {
        b b3 = null;
        b b4 = e;
        int n3 = 0;
        while (b2.b != null) {
            b2 = b2.b;
            if (n2 >= 0 && b2.c != n2) continue;
            b3 = b.b();
            b3.b(b2);
            b4.a(b3);
            b4 = b3;
            ++n3;
        }
        h2.u = n3;
        h2.D = h2.C = c.e.b;
        h2.x = 0;
        c.m[h2.g] = 0;
    }

    static void a(int n2, h h2, int n3) {
        int n4;
        b b2 = d[2];
        b b3 = null;
        int n5 = n4 = n3 >= 0 ? 1 : 0;
        if (n3 >= 0) {
            b3 = b.b();
            b.b().d = -1;
            b3.f = 0;
            b3.e = 1000;
            b3.g = 22;
            e.a(b3);
        }
        while (b2.b != null) {
            b2 = b2.b;
            if (b2.c != n2 || n3 >= 0 && (cS[b2.d][11] & 1 << n3) == 0) continue;
            b3 = b.b();
            b3.b(b2);
            e.a(b3);
            ++n4;
        }
        h2.D = h2.C = c.e.b;
        h2.x = 0;
        c.m[h2.g] = 0;
        h2.u = n4;
    }

    static void f() {
    }

    static void i(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        c.b(graphics, 9, n2, n3 + n5 - 16, 2);
        c.b(graphics, 10, n2 + n4 - 20, n3 + n5 - 16, 2);
    }

    static void p(h h2) {
        switch (h2.w) {
            case 0: {
                h2.b();
                h2.j = 1;
                break;
            }
            case 1: {
                h2.b();
                h2.j = 2;
            }
        }
        if (h2.j == -1) {
            h2.b();
            h2.j = 2;
        }
    }

    static void j(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = 0;
        int n6 = h2.z + 5 + i.R;
        int n7 = n6 - i.R - 1;
        if (h2.i >= 0) {
            c.a(graphics, h2.i, n2 + 5, n3 + 5, n4 - 10, h2.z, 17, 0, 0, 1, 0);
        }
        for (n4 = 0; n4 < h2.u; ++n4) {
            if (n4 == m[h2.g]) {
                c.a(graphics, 811621, n2 + i.aq + n5, n3 + n7, i.ar, i.as);
            }
            c.c(graphics, n4 + 1142, n2 + 40 + n5, n3 + n6, 33, 0, 0);
            n5 += 81;
        }
    }

    static void q(h h2) {
        h h3 = h2.b[0];
        if (h3.j == -1) {
            h3.j = 0;
            h3.d();
            h3.f = false;
            h3.e();
            h3.a = h2;
            dl = true;
            return;
        }
        if (h2.j == 4 || h2.j == 5 || h2.j == 6) {
            dl = true;
        }
        if (h2.w >= 0) {
            c.ai();
            int n2 = m[2];
            int n3 = -1;
            switch (n2) {
                case 0: {
                    n3 = 5;
                    break;
                }
                case 1: {
                    n3 = 6;
                    break;
                }
                case 3: {
                    n3 = 7;
                    break;
                }
                case 2: {
                    n3 = 9;
                    break;
                }
                case 4: {
                    n3 = 10;
                    break;
                }
                case 7: {
                    n3 = 8;
                    break;
                }
                case 5: {
                    n3 = 11;
                }
            }
            n2 = n3;
            h3.a(n2, 32, 56, 176, 208, true);
            h3.c();
        } else if (h2.j == -1) {
            c.b(cq, ct);
            h2.b();
            h.E = 0;
            return;
        }
        if (h2.t[2] < 176) {
            h2.t[2] = h2.t[2] + 44;
            dl = true;
        }
    }

    static void k(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = m[h2.g];
        int n6 = 11;
        for (int i2 = 0; i2 < h2.u; ++i2) {
            if (n6 < n4) {
                cq.b(graphics, n2 + n6, n3 + 8, i2 + 98);
            }
            n6 += 23;
        }
        n6 = n2 + 19 + n5 * 23;
        cq.b(graphics, n6, n3 + -9, n5 + 105);
        cq.b(graphics, n6, n3 + 15, 0);
    }

    private static void b(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        cq.b(graphics, n2 + (n4 >> 1), n3, n6);
        cq.b(graphics, n2 + (n4 >> 1), n3, 33);
        c.a(graphics, 0, n2 + 35, n3 + n5 - 15);
        c.a(graphics, 1, n2 + 100, n3 + n5 - 15);
    }

    private static void a(Graphics graphics, int n2, int n3, int n4) {
        cq.a(graphics, n2 + 30, n3, n4);
        c.a(graphics, 2, false, cY[n2], n3 + 13, n4 + 2, 20);
    }

    private static void b(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7) {
        cq.b(graphics, n3, n4, 113);
        cq.b(graphics, n3, n4 + n5, 114);
        graphics.setColor(7376558);
        graphics.drawLine(n3, n4, n3, n4 + n5);
        graphics.setColor(11914452);
        graphics.drawLine(n3 + 1, n4, n3 + 1, n4 + n5);
        if (n7 > 1) {
            cq.b(graphics, n3, n4 + n6 * n5 / (n7 - 1), 115);
        }
    }

    static void r(h h2) {
        int n2 = h2.t[3];
        c.b(h2, n2 - 14, 15);
    }

    static void l(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        int n6 = -1;
        int n7 = h2.p[0];
        if (h2.p[2] > 0) {
            int n8 = h2.p[1];
            if (n8 >= 0) {
                switch (n7) {
                    case 0: {
                        n6 = 67;
                        break;
                    }
                    case 1: {
                        n6 = 325;
                        break;
                    }
                    case 2: {
                        n6 = 194;
                        break;
                    }
                    case 10: {
                        n6 = 749;
                        break;
                    }
                    case 6: {
                        n6 = 522;
                    }
                }
                c.a(graphics, n6 + n8, n2 + 5, n3 + 7, n4 - 10, n5 - 14, 17, 0, 0, 1, h2.y);
                return;
            }
            switch (n7) {
                case 2: {
                    n6 = 1138;
                }
            }
            if (n6 >= 0) {
                c.a(graphics, 1138, n2 + (n4 >> 1), n3 + 7, 17, 0, 0, 8, 0, 2);
            }
        }
    }

    static void s(h h2) {
        int n2 = h2.t[3];
        c.b(h2, n2 - 14, 10);
    }

    static void m(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        int n6 = 0;
        int n7 = h2.p[1];
        switch (h2.p[0]) {
            case 5: {
                n6 = 67;
                break;
            }
            case 1: {
                n6 = 325;
            }
        }
        if (n7 >= 0) {
            c.a(graphics, n6 + n7, n2 + 5, n3 + 7, n4 - 10, n5 - 14, 17, 0, 0, 1, h2.y);
        }
    }

    static void t(h h2) {
        c.b(h2, 80, 15);
        if (h2.j == -1) {
            h2.p[2] = 0;
            h2.b();
            return;
        }
    }

    static void n(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        int n6 = m[h2.g];
        c.a(graphics, 1304 + q[n6], n2 + 5, n3 + 7, n4 - 10, n5 - 14, 17, 0, 0, 1, h2.y);
    }

    static void u(h h2) {
        int n2;
        h h3 = h2.b[h2.d - 3];
        h[] cfr_ignored_0 = h2.b;
        int cfr_ignored_1 = h2.d;
        int n3 = m[h2.g];
        for (n2 = 0; n2 < h2.u; ++n2) {
            h2.b[n2].p[1] = n2 == n3 ? 1 : 0;
        }
        if (h2.w >= 0) {
            if (h2.p[0] == 0) {
                h3.c();
                h3.d(c.a(c.c(n3 + 1304), h3.t[2], 1));
                return;
            }
            h2.j = -1;
            h2.a();
            return;
        }
        if (h2.j == -1) {
            h2.a();
            c.m[h2.g] = 0;
            for (n2 = 0; n2 < h2.u; ++n2) {
                h2.b[n2].p[1] = 0;
            }
            if (h2.p[0] == 1) {
                h2.j = -1;
            }
        }
    }

    static void o(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        c.b(graphics, n2, n3, n4, n5, 22);
    }

    static void v(h h2) {
        if (h2.j == -1) {
            h2.b();
            return;
        }
    }

    static void p(Graphics graphics, h h2) {
        int n2 = h2.s;
        int n3 = h2.t[0];
        int n4 = h2.t[1];
        int n5 = h2.p[0];
        int n6 = h2.p[1];
        a a2 = g[n5];
        graphics.setClip(0, 0, 240, 320);
        cq.b(graphics, n3 + 3, n4 + 7, 11 + a2.s);
        c.a(graphics, 68, false, a2.F[15] + 1, n3 + 41, n4 + 107, 20);
        if (n6 == 1 && n2 == m[3]) {
            cq.b(graphics, n3 + 38, n4 + 83, 0);
        }
        for (n6 = 0; n6 < 3; ++n6) {
            c.a(graphics, n6, a2, n3 + 2, n4 + 127, 51);
            n4 += 14;
        }
    }

    private static void a(Graphics graphics, int n2, a a2, int n3, int n4, int n5) {
        n5 = 0;
        int n6 = 0;
        switch (n2) {
            case 0: {
                n5 = a2.F[4];
                n6 = a2.F[7];
                break;
            }
            case 1: {
                n5 = a2.F[5];
                n6 = a2.F[8];
                break;
            }
            case 2: {
                n5 = a2.F[1];
                n6 = a2.F[2];
            }
        }
        c.c(graphics, n2, n3, n4, 51, n5 * 100 / n6);
        cq.a(graphics, n2 + 27, n3 + 1, n4 + -9);
        c.a(graphics, 2, false, n5, n3 + 51 - 5, n4 - 7, 24);
    }

    static void w(h h2) {
        if (c.b(258)) {
            if (h2.x > 0) {
                --h2.x;
            }
            return;
        }
        if (c.b(16388)) {
            if (h2.x <= h2.u - h2.v - 1) {
                ++h2.x;
            }
            return;
        }
        if (c.b(788512)) {
            h2.j = -1;
            h2.b();
            return;
        }
    }

    static void q(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.x;
        c.c(graphics, h2.i, n2 + (n4 >> 1), n3 + 8, 17, 0, 0);
        c.b(graphics, 113, n2 + n4 - 15, n3 + i.ai, 70, n5, h2.u - h2.v + 1);
        for (n4 = n5; n4 < h2.v + n5; ++n4) {
            cq.a(graphics, n4 + 16, n2 + 10, n3 + i.ag);
            c.a(graphics, 2, false, h2.p[n4 << 1], n2 + 60, n3 + i.ah, 24);
            c.a(graphics, 2, false, h2.p[(n4 << 1) + 1], n2 + 120, n3 + i.ah, 24);
            n3 += 16;
        }
    }

    static void r(Graphics graphics, h h2) {
        int n2;
        h h3 = h2.b[0];
        int n3 = h2.t[0];
        int n4 = h2.t[1];
        int n5 = h2.t[2];
        int n6 = h2.t[3];
        int n7 = m[h2.g];
        int n8 = q[m[h3.g]];
        int n9 = 18;
        int n10 = 0;
        int n11 = n4 + 114;
        a a2 = g[n8];
        for (n2 = 0; n2 < h2.u; ++n2) {
            cq.b(graphics, n3 + i.Y[n2], n4 + i.Z[n2], 112);
            if (a2.G[n2] < 0) continue;
            c.b(graphics, cS[a2.G[n2]][19], n3 + i.Y[n2], n4 + i.Z[n2]);
        }
        if (h2.k < 0) {
            cq.b(graphics, n3 + i.Y[n7] + 5, n4 + i.Z[n7], 0);
        }
        c.a(graphics, 14876594, n3 + 6, n4 + 110, 164, 50, 3, 4, 5);
        c.t(n8);
        for (n2 = 0; n2 < 3; ++n2) {
            n10 = n3 + 12;
            for (int i2 = 0; i2 < 2; ++i2) {
                cq.a(graphics, n9, n10, n11);
                c.a(graphics, 2, false, a2.F[9 + (n2 << 1) + i2], n10 + 60, n11 + 3, 24);
                ++n9;
                n10 += 75;
            }
            n11 += 14;
        }
        c.b(graphics, n3, n4, n5, n6, 18);
    }

    static void s(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = q[m[h2.g]];
        a a2 = g[n5];
        a2.b(graphics, n2 + (n4 >> 1) + 32, n3 + 77, 0);
        if (h2.u > 1) {
            cq.b(graphics, n2 + 61 + 32 + -21, n3 + 40, 3);
            cq.b(graphics, n2 + 61 + 32 + 54 - -15, n3 + 40, 4);
        }
    }

    static void x(h h2) {
        h h3 = h2.b[0];
        h h4 = h2.b[1];
        if (h4.j == 4 || h4.j == 5) {
            h3.p[1] = h4.C.d;
            c.b(h4, h2.p[0]);
            h3.d(c.a(c.c(194 + h3.p[1]), h3.t[2], 1));
            return;
        }
        if (h4.w >= 0) {
            h4.w = -1;
            h2.j = 1;
            h2.w = h4.C.d;
            h2.b();
            return;
        }
        if (h2.j == -1) {
            c.ai();
            h2.b();
        }
    }

    static void y(h h2) {
        h h3 = h2.b[0];
        h h4 = h2.b[1];
        h h5 = h2.b[2];
        h h6 = h2.b[3];
        int n2 = m[h2.g];
        if (h6.j == -1) {
            h6.j = 0;
            h4.c();
            return;
        }
        if (h5.j == 1) {
            h5.j = 0;
            c.k(h5.i, 1, -1);
            h5.i = cS[h5.i][14];
            c.k(h5.i, 1, 1);
            c.ai();
            c.a(i.aJ[n2], h4, -1);
            if (cS[h5.i][14] < 0) {
                h4.f = true;
                h3.f = true;
                h5.f = false;
                c.a(h6, 1118, 0);
                h6.a(20);
                return;
            }
            h5.c();
            c.a(h5, h5.i);
            return;
        }
        if (h5.j == -1) {
            h5.j = 0;
            h4.c();
            h3.f = true;
            return;
        }
        if (h4.j == -1) {
            h4.j = 0;
            h4.i = 0;
            h3.p[2] = 0;
            return;
        }
        if (h4.j == 4 || h4.j == 5) {
            h3.p[1] = h4.C.d;
            c.b(h4, -1);
            h3.d(c.a(c.c(194 + h3.p[1]), h3.t[2], 1));
            h4.c();
            return;
        }
        if (h4.w >= 0) {
            h4.w = -1;
            if (cS[h4.C.d][14] < 0) {
                c.a(h6, 1118, 0);
                h6.a(20);
                return;
            }
            h5.c();
            h5.i = h4.C.d;
            c.a(h5, h5.i);
            h4.f = false;
            h3.f = false;
            return;
        }
        if (h2.j == 4 || h2.j == 5 || h2.j == 6) {
            c.ai();
            c.a(i.aJ[n2], h4, -1);
            return;
        }
        if (h5.j == -1) {
            h4.f = true;
            h3.f = true;
            return;
        }
        if (h2.w >= 0) {
            if (h4.u > 0) {
                h4.c();
                c.b(h4, -1);
                h4.i = 1;
                h3.p[1] = h4.C.d;
                h3.d(c.a(c.c(194 + h3.p[1]), h3.t[2], 1));
                h3.p[2] = 1;
            }
            return;
        }
        if (h2.j == -1) {
            c.ai();
            h2.b();
        }
    }

    static void t(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        int n6 = n3 + 22;
        int n7 = m[h2.g];
        c.a(graphics, 14876594, n2 + 44, n3 + 19, 125, 97, 3, 4, 5);
        c.a(graphics, 75, n2 + 43, n3 + 20, 0, 19, h2.u, n7);
        c.b(graphics, n2, n3, n4, n5, 21);
        for (n3 = 0; n3 < h2.u; ++n3) {
            cq.b(graphics, n2 + 28, n6, n3 + 87);
            n6 += 19;
        }
    }

    static void z(h h2) {
        if (h2.w >= 0) {
            h2.b();
            return;
        }
        if (h2.j == -1) {
            h2.b();
        }
    }

    static void u(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = m[h2.g];
        int n5 = n3;
        for (int i2 = 0; i2 < h2.u; ++i2) {
            cq.b(graphics, n2, n5, i2 + 45);
            n5 += 20;
        }
        cq.b(graphics, n2 + 40, n3 + 5 + n4 * 20, 0);
    }

    private static void a(h h2, int n2) {
        int n3;
        int[][] nArray = new int[2][11];
        h2.q = 0;
        for (n3 = 0; n3 < 2; ++n3) {
            for (int i2 = 1; i2 <= 5; i2 += 2) {
                int n4 = cS[n2][i2];
                if (n4 == 11 || n4 == 10) continue;
                nArray[n3][n4] = cS[n2][i2 + 1];
            }
            n2 = cS[n2][14];
        }
        for (n3 = 0; n3 < 11; ++n3) {
            if (nArray[0][n3] == nArray[1][n3]) continue;
            h2.c(n3);
            h2.c(nArray[0][n3]);
            h2.c(nArray[1][n3]);
        }
    }

    private static void b(h h2, int n2) {
        int n3;
        h h3 = h2.c;
        int n4 = m[h2.g] % h2.B;
        int n5 = h2.t[1] - 15;
        int n6 = h2.C.d;
        int[] nArray = new int[11];
        n4 = n4 >= h2.B >> 1 ? h2.t[0] + h2.t[2] * n4 - h3.t[2] + -10 : h2.t[0] + h2.t[2] * n4 + 10;
        h3.q = 0;
        if (n6 >= 0) {
            for (int i2 = 1; i2 <= 5; i2 += 2) {
                n3 = cS[n6][i2];
                if (n3 >= 10) continue;
                int n7 = n3;
                nArray[n7] = nArray[n7] + cS[n6][i2 + 1];
            }
        }
        if (n2 >= 0) {
            for (int i3 = 1; i3 <= 5; i3 += 2) {
                n3 = cS[n2][i3];
                if (n3 >= 10) continue;
                int n8 = n3;
                nArray[n8] = nArray[n8] - cS[n2][i3 + 1];
            }
        }
        for (int i4 = 0; i4 < nArray.length; ++i4) {
            if (nArray[i4] == 0) continue;
            h3.c(i4);
            h3.c(nArray[i4]);
        }
        h3.a(n4, n5, h3.t[2], h3.t[3]);
    }

    static void v(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        n3 += 6;
        int n5 = 0;
        for (int i2 = 0; i2 < h2.q; i2 += 2) {
            if (h2.p[i2 + 1] == 0) continue;
            n5 = h2.p[i2 + 1] > 0 ? 26 : 14;
            cq.a(graphics, 15 + h2.p[i2], n2 + 6, n3);
            c.a(graphics, n5, false, h2.p[i2 + 1], n2 + n4 - 6, n3 + 4, 24);
            n3 += 15;
        }
    }

    static void A(h h2) {
        h h3 = h2.b[0];
        int n2 = h2.i;
        if (h2.w >= 0) {
            h2.w = -1;
            if (cS[n2][14] < 0) {
                c.a(h3, 1118, 0);
                h3.a(20);
                return;
            }
            if (cY[0] < cS[n2][7]) {
                c.a(h3, 1109, 0);
                h3.a(20);
                return;
            }
            if (cY[1] < cS[n2][9]) {
                c.a(h3, 1298, 0);
                h3.a(20);
                return;
            }
            c.k(0, 2, -cS[n2][7]);
            c.k(0, 9, -cS[n2][9]);
            h2.j = 1;
            h2.a();
            return;
        }
        if (h2.j == -1) {
            h2.b();
        }
    }

    static void w(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        int n6 = h2.q / 3;
        int n7 = n3 + i.ab;
        int n8 = 0;
        c.a(graphics, 14876594, n2 + 8, n3 + 20, 160, 120, 3, 4, 5);
        c.c(graphics, 134 + h2.i, n2 + (n4 >> 1), n3 + 24, 17, 0, 0);
        for (int i2 = 0; i2 < n6; ++i2) {
            cq.a(graphics, 15 + h2.p[n8], n2 + 14, n7 - 4);
            cq.b(graphics, n2 + 103, n7, 95);
            c.a(graphics, 2, false, h2.p[n8 + 1], n2 + 64, n7, 20);
            c.a(graphics, 2, false, h2.p[n8 + 2], n2 + 148, n7, 24);
            n7 += 16;
            n8 += 3;
        }
        cq.a(graphics, 38, n2 + 8, n3 + 143);
        cq.a(graphics, 30, n2 + 38, n3 + 145);
        c.a(graphics, 2, false, cS[h2.i][7], n2 + 53, n3 + 145, 20);
        cq.a(graphics, 32, n2 + 108, n3 + 145);
        c.a(graphics, 2, false, cS[h2.i][9], n2 + 123, n3 + 145, 20);
        cq.b(graphics, n2 + (n4 >> 1), n3 + 163, 94);
        c.b(graphics, n2, n3, n4, n5, 27);
    }

    private static void c(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        n6 = (n5 - 4) * n6 / 100;
        graphics.setColor(i.V[n2]);
        graphics.drawLine(n3 + 1, n4, n3 + n5 - 1, n4);
        graphics.drawLine(n3 + 2, n4 + 2, n3 + n6 + 2, n4 + 2);
        graphics.drawLine(n3 + 1, n4 + 1, n3 + 1, n4 + 2);
        graphics.drawLine(n3 + n5 - 1, n4 + 1, n3 + n5 - 1, n4 + 2);
        graphics.setColor(i.U[n2]);
        graphics.drawLine(n3 + 1, n4 + 3, n3 + n5 - 1, n4 + 3);
        graphics.drawLine(n3, n4 + 1, n3, n4 + 2);
        graphics.drawLine(n3 + n5, n4 + 1, n3 + n5, n4 + 2);
        graphics.drawLine(n3 + 1, n4, n3 + 1, n4);
        graphics.drawLine(n3 + n5 - 1, n4, n3 + n5 - 1, n4);
        graphics.setColor(i.W[n2]);
        graphics.drawLine(n3 + 2, n4 + 1, n3 + 2 + n6, n4 + 1);
    }

    static void x(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = m[h2.g];
        int n5 = n2 + 22;
        c.a(graphics, 73, n2 + -4, n3, 55, 0, h2.u, n4);
        for (n2 = 0; n2 < h2.u; ++n2) {
            cq.b(graphics, n5, n3 + -15, 79 + q[n2]);
            n5 += 55;
        }
    }

    static void B(h h2) {
        h h3 = h2.b[0];
        h h4 = h2.b[1];
        h h5 = h2.b[2];
        h h6 = h2.b[4];
        int n2 = m[h3.g];
        c.b(h2, 100, 15);
        if (h4.j == 1) {
            h4.j = 0;
            c.a(h6, 1173, 0);
            h6.a(20);
            h5.p[2] = 1;
            h5.p[1] = h2.C.d;
            h2.d(c.a(c.c(590 + h2.C.d), 100, 0));
            h5.d(c.a(c.c(325 + h2.C.d), h5.t[2] - 10, 1));
            h5.f = true;
            return;
        }
        if (h4.j == -1) {
            h4.j = 0;
            h5.f = true;
            return;
        }
        if (h3.j == 4 || h3.j == 5 || h3.j == 6) {
            h2.D = h2.C = c.g[c.q[n2]].J.b;
            h2.u = h2.C == null ? 0 : h2.C.e();
            h2.x = 0;
            c.m[h2.g] = 0;
            if (h2.D != null) {
                h4.i = n2;
                h5.p[2] = 1;
                h5.p[1] = h2.C.d;
                h2.d(c.a(c.c(590 + h2.C.d), 100, 0));
                h5.d(c.a(c.c(325 + h2.C.d), h5.t[2] - 10, 1));
                return;
            }
            h5.p[2] = 0;
            return;
        }
        h2.k();
        if (h2.j == 4 || h2.j == 5) {
            h5.p[1] = h2.C.d;
            h2.d(c.a(c.c(590 + h2.C.d), 100, 0));
            h5.d(c.a(c.c(325 + h2.C.d), h5.t[2] - 10, 1));
            return;
        }
        if (h2.w >= 0) {
            int n3;
            if (h2.C == null) {
                return;
            }
            if (h[h2.C.d][16] < 0) {
                c.a(h6, 1116, 0);
                h6.a(20);
                return;
            }
            h4.c();
            h4.C = h2.C;
            int n4 = h2.C.d;
            h2 = h4;
            int[][] nArray = new int[2][71];
            int[] nArray2 = new int[2];
            int[] nArray3 = new int[2];
            h2.q = 0;
            for (n3 = 0; n3 < 2; ++n3) {
                nArray2[n3] = h[n4][2];
                nArray3[n3] = h[n4][1] > 0 ? h[n4][1] : 5;
                for (n2 = 6; n2 <= 12; n2 += 3) {
                    int n5 = h[n4][n2];
                    if (n5 == 0) continue;
                    nArray[n3][n5] = h[n4][n2 + (n5 < 35 ? 1 : 2)];
                }
                n4 = h[n4][16];
            }
            h2.c(131);
            h2.c(nArray2[0]);
            h2.c(nArray2[1]);
            h2.c(132);
            h2.c(nArray3[0]);
            h2.c(nArray3[1]);
            for (n3 = 0; n3 < 71; ++n3) {
                n4 = i[n3][6];
                if (n4 < 0 || nArray[0][n3] == nArray[1][n3]) continue;
                h2.c(n4 + 133);
                h2.c(nArray[0][n3]);
                h2.c(nArray[1][n3]);
            }
            h5.f = false;
            return;
        }
        if (h2.j == -1) {
            h2.b();
            return;
        }
    }

    static void y(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        int n6 = m[h2.g] - h2.x;
        int n7 = n3 + 55;
        int n8 = m[h2.g];
        c.b(graphics, n2, n3, n4, n5, 24);
        c.a(graphics, 14876594, n2 + 6, n3 + 36, 164, 80, 3, 4, 5);
        for (n4 = 0; n4 < h2.v; ++n4) {
            cq.b(graphics, n2 + 25, n7, 112);
            n7 += 22;
        }
        c.a(graphics, h2.D, 590, true, h2.v, n6, -1, n2 + 40, n3 + 44, 120, 22, -1, 100, h2.y);
        c.b(graphics, 113, n2 + 158, n3 + 50, 50, n8, h2.u);
        if (h2.u > 0) {
            cq.b(graphics, n2 + 116, n3 + 52 + (n8 - h2.x) * 22, 0);
        }
    }

    static void C(h h2) {
        h h3 = h2.b[0];
        a a2 = g[q[h2.i]];
        int n2 = h2.C.d;
        c.b(h2, 44, 50);
        if (h2.w >= 0) {
            if (a2.F[15] < h[n2][20]) {
                c.a(h3, 1110, 0);
                h3.a(20);
                return;
            }
            if (cY[0] < h[n2][21]) {
                c.a(h3, 1109, 0);
                h3.a(20);
                return;
            }
            h2.j = 1;
            a2.J.c((int)n2).d = h[n2][16];
            cY[0] = cY[0] - h[n2][21];
            h2.b();
            return;
        }
        if (h2.j == -1) {
            h2.b();
        }
    }

    static void z(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.C.d;
        int cfr_ignored_0 = h2.q;
        c.a(graphics, 0x226688, n2 + 10, n3 + 10, 156, 130, 6, 7, 8);
        c.c(graphics, n5 + 590, n2 + (n4 >> 1), n3 + 12, 17, 6151391, 6151391);
        c.a(graphics, n5 + 325, n2 + 10, n3 + 30, 156, 44, 17, 6151391, 6151391, 1, h2.y);
        c.a(graphics, 0x226688, n2 + 10, n3 + 75, 156, 66, 6, 7, 8);
        c.c(graphics, n5 + 590 + 1, n2 + (n4 >> 1), n3 + 77, 17, 6151391, 6151391);
        c.a(graphics, n5 + 325 + 1, n2 + 10, n3 + 95, 156, 44, 17, 6151391, 6151391, 1, h2.y);
        cq.a(graphics, 38, n2 + 8, n3 + 143);
        cq.a(graphics, 30, n2 + 38, n3 + 145);
        c.a(graphics, 2, false, h[n5][21], n2 + 53, n3 + 145, 20);
        cq.a(graphics, 32, n2 + 108, n3 + 145);
        c.a(graphics, 2, false, h[n5][22], n2 + 123, n3 + 145, 20);
        cq.b(graphics, n2 + (n4 >> 1), n3 + 157, 94);
    }

    static void g() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static void D(h h2) {
        h h3 = h2.b[0];
        h h4 = h2.b[1];
        h h5 = h2.b[2];
        h h6 = h2.b[3];
        h h7 = h2.b[4];
        h h8 = h5.b[h5.d - 2];
        if (h7.j == -1) {
            h7.j = 0;
            h4.c();
            h5.f = false;
            h3.p[2] = 1;
            return;
        }
        if (h4.j == -1) {
            h4.j = 0;
            h4.i = 0;
            return;
        }
        if (h4.j == 4 || h4.j == 5) {
            h4.j = 0;
            h3.p[1] = h4.C.d;
            h3.d(c.a(c.c(67 + h4.C.d), h3.t[2], 1));
            h4.c();
            return;
        }
        if (h4.w >= 0) {
            h4.w = -1;
            int n2 = cQ[h4.C.d][7];
            if (n2 == 0 || n2 == 2) {
                h3.f = false;
                h5.c();
                h2.p[0] = h4.C.d;
                h5.p[0] = 1;
                h3.p[2] = 0;
                return;
            }
            h3.f = true;
            h3.p[2] = 1;
            h4.c();
            return;
        }
        if (h5.j == -1) {
            h3.f = true;
            h5.j = 0;
            if (h5.w >= 0) {
                a a2 = g[q[h5.w]];
                int n3 = h2.p[0];
                if ((cQ[n3][16] & 1 << h5.w) == 0) {
                    c.a(h6, 1300, 0);
                    h6.a(20);
                    return;
                }
                c.a(n3, a2);
                if (c.al() > 0) {
                    c.a(a2, 100, ft);
                    if (cY[1] < cQ[h4.C.d][3]) {
                        c.a(h6, 1307, 0);
                        h6.a(20);
                        h6.j = 0;
                        return;
                    }
                    cY[1] = cY[1] - cQ[h4.C.d][3];
                    c.k(h4.C.d, 0, -1);
                    --h4.C.e;
                    if (h4.C.e > 0) {
                        int n4 = cQ[h4.C.d][20];
                        switch (n4) {
                            case 0: {
                                h5.c();
                                return;
                            }
                            case 1: {
                                int n5 = h4.C.d;
                                a a3 = a2;
                                h h9 = h7;
                                h7.i = n5;
                                for (n5 = 0; n5 < 8; ++n5) {
                                    h9.c(a3.F[n5 + 7] - ft[n5 + 27]);
                                    h9.c(a3.F[n5 + 7]);
                                }
                                h7.c();
                            }
                            default: {
                                return;
                            }
                        }
                    }
                    c.Y();
                    h5.f = false;
                    h3.p[2] = 1;
                    c.ai();
                    c.a(0, h4, d[0]);
                    h4.c();
                    h3.p[1] = h4.C.d;
                    return;
                }
                h3.f = false;
                h5.c();
                h8.a(20);
                switch (fu) {
                    case 2: {
                        c.a(h8, 1166, 0);
                        return;
                    }
                    case 4: {
                        c.a(h8, 1167, 0);
                        return;
                    }
                    case 3: {
                        c.a(h8, 1168, 0);
                    }
                    default: {
                        return;
                    }
                }
            }
            h4.c();
            h5.f = false;
            h3.p[2] = 1;
            return;
        }
        if (h6.j == -1) {
            h6.j = 0;
            h4.c();
            h5.f = false;
            return;
        }
        h3.p[2] = 0;
        if (h2.j == 6 || h2.j == 4 || h2.j == 5) {
            c.ai();
            c.a(m[h2.g], h4, d[0]);
            return;
        }
        if (h2.w >= 0) {
            if (h4.u <= 0) {
                return;
            }
            c.m[h4.g] = 0;
            h4.c();
            h3.d(c.a(c.c(67 + h4.C.d), h3.t[2], 1));
            h3.p[2] = 1;
            h3.p[1] = h4.C.d;
            h4.i = 1;
            return;
        }
        if (h2.j != -1) return;
        c.ai();
        h2.a();
    }

    private static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        for (int i2 = 0; i2 < n7; ++i2) {
            if (i2 == n8) {
                cq.b(graphics, n3, n4, n2 + 1);
            } else {
                cq.b(graphics, n3, n4, n2);
            }
            n3 += n5;
            n4 += n6;
        }
    }

    static void A(Graphics graphics, h h2) {
        int[] cfr_ignored_0 = h2.t;
        int n2 = 0;
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        int n6 = m[h2.g];
        int n7 = n6 / h2.B;
        int n8 = h2.u / h2.B + (h2.u % h2.B == 0 ? 0 : 1);
        b b2 = h2.D;
        graphics.setClip(0, 0, 240, 320);
        for (int i2 = 0; i2 < h2.v; ++i2) {
            n2 = h2.t[0];
            for (int i3 = 0; i3 < h2.B; ++i3) {
                cq.b(graphics, n2, n3, 112);
                if (b2 != null) {
                    c.b(graphics, b2.g, n2, n3);
                    if (b2.e < 1000) {
                        c.a(graphics, 2, false, b2.e, n2 + 10, n3 + 2, 24);
                    }
                    b2 = b2.b;
                }
                n2 += n4;
            }
            n3 += n5;
        }
        n2 = h2.t[0];
        n3 = h2.t[1];
        if (h2.i > 0) {
            cq.b(graphics, n2 + m[h2.g] % h2.B * n4, n3 + (n6 / h2.B - h2.x) * n5, 0);
        }
        c.b(graphics, 113, n2 + 20 + (h2.B - 1) * n4, n3 + 5, 60, n7, n8);
    }

    static void E(h h2) {
        if (c.b(262144)) {
            cD = "http://3g.tysis.cn";
            c.b((byte)15);
            return;
        }
        if (c.b(524288)) {
            h2.b();
        }
    }

    static void a(Graphics graphics) {
        graphics.drawImage(cC, 120, 160, 3);
        c.a(graphics, 1257, 120, 160 - i.R, 33, 0xFFFFFF, 0xFFFFFF, 7, 0, 0);
        c.a(graphics, "\u662f", 5, 315, 36, 0xFFFFFF, 0xFFFFFF);
        c.a(graphics, "\u5426", 235, 315, 40, 0xFFFFFF, 0xFFFFFF);
    }

    static void B(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        int n6 = m[h2.g];
        int n7 = n3 + 24;
        c.b(graphics, n2, n3, n4, n5, 16);
        c.a(graphics, 14876594, n2 + 44, n3 + 18, 125, 97, 3, 4, 5);
        c.a(graphics, 75, n2 + 43, n3 + 21, 0, 20, h2.u, n6);
        for (n3 = 0; n3 < h2.u; ++n3) {
            cq.b(graphics, n2 + 26, n7, n3 + 84);
            n7 += 20;
        }
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private static void a(Graphics var0, b var1_1, int var2_2, boolean var3_3, int var4_4, int var5_5, int var6_6, int var7_7, int var8_8, int var9_9, int var10_10, int var11_11, int var12_12, int var13_13) {
        var3_3 = false;
        var14_14 = 0;
        var15_15 = "";
        for (var16_16 = 0; var16_16 < var4_4; ++var16_16) {
            block13: {
                block12: {
                    if (var1_1 == null) {
                        return;
                    }
                    if (var5_5 != var16_16) break block12;
                    var3_3 = 0xFF0000;
                    var14_14 = var13_13;
                    break block13;
                }
                switch (var11_11) {
                    case -1: {
                        v0 = var1_1.f;
                        ** GOTO lbl18
                    }
                    case 0: {
                        v0 = c.g[0].F[19] >= c.cV[var1_1.d][2] ? var1_1.f : 0x929292;
lbl18:
                        // 2 sources

                        var3_3 = v0;
                    }
                }
                var14_14 = 0;
            }
            switch (var6_6) {
                case 0: {
                    var15_15 = "" + var1_1.e;
                    break;
                }
                case 1: {
                    var15_15 = "" + c.h[var1_1.d][2];
                    break;
                }
                case 2: {
                    var15_15 = "" + c.h[var1_1.d][19];
                    if (c.h[var1_1.d][19] != 0) break;
                    var15_15 = "--";
                }
            }
            if (var1_1.d >= 0) {
                c.b(var0, var1_1.g, var7_7 + -15, var8_8 + 11);
                c.a(var0, var1_1.d + var2_2, var7_7, var8_8, var12_12, i.R, 20, (int)var3_3, (int)var3_3, 0, var14_14);
                c.a(var0, var15_15, var7_7 + var9_9, var8_8, 24, (int)var3_3, (int)var3_3);
            }
            var1_1 = var1_1.b;
            var8_8 += var10_10;
        }
    }

    private static void b(Graphics graphics, int n2, int n3, int n4, int n5) {
        if (n2 != -1) {
            cq.b(graphics, n3, n4, n2);
        }
    }

    static void F(h h2) {
        h h3 = h2.b[0];
        h h4 = h2.b[1];
        h h5 = h2.b[2];
        h h6 = h2.b[3];
        h h7 = h2.b[4];
        h h8 = h2.b[5];
        h h9 = h5.b[1];
        int n2 = m[h3.g];
        int n3 = m[h2.g];
        a a2 = g[q[n2]];
        if (h3.j == 6 || h3.j == 4 || h3.j == 5) {
            int n4 = a2.G[n3];
            h5.p[0] = q[n2];
            h7.p[1] = n4;
            h7.p[2] = n4 >= 0 ? 1 : 0;
            h7.d(c.a(c.c(n4 + 194), h7.t[2], 1));
            return;
        }
        if (h2.j == 6 || h2.j == 4 || h2.j == 5) {
            int n5 = a2.G[n3];
            h3.p[0] = i.aJ[n3];
            h7.p[1] = n5;
            h7.p[2] = n5 >= 0 ? 1 : 0;
            h7.d(c.a(c.c(n5 + 194), h7.t[2], 1));
            return;
        }
        if (h4.w >= 0) {
            h4.w = -1;
            switch (m[h4.g]) {
                case 0: {
                    if (a2.F[31] == 1) {
                        c.a(h8, 1085, 0);
                        h8.a(20);
                        return;
                    }
                    c.a(a2, h5, h9, n3, n2);
                    h3.f = false;
                    h7.f = false;
                    return;
                }
                case 1: {
                    int n6 = a2.G[n3];
                    if (cS[n6][14] < 0) {
                        c.a(h8, 1118, 0);
                        h8.a(20);
                        return;
                    }
                    h6.i = n6;
                    c.a(h6, n6);
                    h6.c();
                    h4.f = false;
                    h7.f = false;
                }
            }
            return;
        }
        if (h6.j == 1) {
            h6.j = 0;
            a2.G[n3] = h6.i = cS[h6.i][14];
            c.t(n2);
            h7.f = true;
            h6.f = false;
            c.a(h8, 1132, 0);
            h7.p[1] = h6.i;
            h8.a(20);
            return;
        }
        if (h6.j == -1) {
            h6.j = 0;
            h4.c();
            h7.f = true;
            return;
        }
        if (h2.w >= 0) {
            if (a2.G[h2.w] >= 0) {
                h4.c();
                return;
            }
            if (a2.F[31] == 1) {
                c.a(h8, 1085, 0);
                h8.a(20);
                return;
            }
            c.a(a2, h5, h9, n3, n2);
            h3.f = false;
            h7.f = false;
            return;
        }
        if (h5.j == 1) {
            h5.j = 0;
            h3.f = true;
            h7.f = true;
            int n7 = h5.w;
            int n8 = 0;
            h5.w = -1;
            if (n7 >= 0 && a2.F[15] + 1 + cY[5] < (n8 = cS[n7][10])) {
                c.a(h8, 1083, 0);
                h8.a(20);
            } else {
                c.b(q[n2], n3, n7, false);
            }
            c.t(q[n2]);
            c.ai();
            n7 = a2.G[n3];
            h7.p[2] = n7 >= 0 ? 1 : 0;
            h7.p[1] = n7;
            h7.d(c.a(c.c(n7 + 194), h7.t[2], 1));
            return;
        }
        if (h5.j == -1) {
            h5.j = 0;
            h3.f = true;
            h7.f = true;
            c.ai();
            return;
        }
        if (h2.j == -1) {
            c.m[5] = 0;
            c.ai();
            h2.b();
        }
    }

    private static void a(a a2, h h2, h h3, int n2, int n3) {
        h h4 = h2.b[0];
        c.ai();
        c.a(i.aJ[n2], h3, q[n3]);
        h2.c();
        h4.p[1] = h3.C.d;
        h4.d(c.a(c.c(194 + h4.p[1]), h4.t[2], 1));
        h2.p[0] = a2.G[n2];
        c.m[h2.g] = i.aJ[n2];
        c.b(h3, a2.G[n2]);
    }

    static void h() {
    }

    static void G(h h2) {
        h h3 = h2.b[0];
        h h4 = h2.b[1];
        h h5 = h2.b[2];
        h h6 = h2.b[3];
        h2.b[3].f = true;
        if (h4.j == 1) {
            h4.j = 0;
            t.b();
            c.b((byte)20);
            return;
        }
        if (h4.j == 2) {
            h4.j = 0;
            h4.b();
            return;
        }
        if (h3.j == -1) {
            h3.j = 0;
            h3.d();
            h3.e();
            h3.a = h2;
            dl = true;
            return;
        }
        switch (h2.w) {
            case 0: {
                if (ci[21] == 1) {
                    h3.a(13, 32, 56, 176, 208, true);
                    h3.c();
                    break;
                }
                c.a(h5, 1181, 0);
                h5.a(20);
                break;
            }
            case 1: {
                h3.a(12, 32, 56, 176, 208, true);
                h3.c();
                break;
            }
            case 2: {
                h3.a(47, 32, 56, 176, 208, true);
                h3.c();
                break;
            }
            case 3: {
                h3.a(48, 32, 56, 176, 208, true);
                h3.c();
                int n2 = c.b(c.c(1241), h3.t[2] - 10, h3.t[3] - 10 - (i.R << 1));
                h3.d(n2);
                break;
            }
            case 4: {
                c.a(h4, 1134, i.R);
                h4.c();
            }
        }
        if (h2.j == -1) {
            h2.a();
        }
    }

    static void i() {
        if (c.b(262144)) {
            cD = "http://3g.tysis.cn";
            u.h();
            u = null;
            c.b((byte)15);
            return;
        }
        if (c.b(524288)) {
            c.b((byte)17);
        }
    }

    static void C(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        int n6 = m[h2.g];
        int n7 = n3 + 24;
        c.a(graphics, 13022093, n2 + 4, n3 + 18, n4 + -8, n5 + -36, 3, 4, 5);
        for (int i2 = 0; i2 < h2.u; ++i2) {
            cq.b(graphics, n2 + (n4 >> 1), n7, i2 + 39);
            n7 += 24;
        }
        cq.b(graphics, n2 + 120, n3 + 30 + n6 * 24, 0);
        c.b(graphics, n2, n3, n4, n5, 25);
    }

    static void H(h h2) {
        h h3 = h2.b[0];
        h h4 = h3.b[0];
        h2.k();
        if (h2.w >= 0) {
            int n2 = h2.C.d;
            b b2 = c.d[13].b;
            b b3 = d[3];
            b b4 = null;
            d[3].d();
            c.d[3].b = null;
            while (b2 != null) {
                int n3 = c.A(b2.d);
                int n4 = c.B(b2.d);
                if (b2.c == n2) {
                    b4 = b.b();
                    b.b().d = n4;
                    b4.c = (byte)n3;
                    switch (n3) {
                        case 0: {
                            b4.g = cQ[n4][19];
                            break;
                        }
                        case 1: {
                            b4.g = cS[n4][19];
                        }
                    }
                    b3.a(b4);
                    b3 = b4;
                }
                b2 = b2.b;
            }
            h4.D = h4.C = c.d[3].b;
            h4.u = h4.C == null ? 0 : h4.C.e();
            c.m[h3.g] = 0;
            h3.c();
            return;
        }
        if (h2.j == -1) {
            c.ai();
            h2.b();
        }
    }

    static void D(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = m[h2.g];
        c.a(graphics, h2.D, 766, true, h2.v, n4, -1, n2 + 40, n3 + 30, 0, 25, -1, 80, h2.y);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static void I(h h2) {
        h h3 = h2.b[2];
        h h4 = h2.b[3];
        for (int i2 = 0; i2 < h2.u; ++i2) {
            h2.b[i2].i = i2 == m[h2.g] ? 1 : 0;
        }
        if (h4.j == 1) {
            h4.j = 0;
            c.a(true, m[15]);
            c.a(h4, 1090, 0);
            h4.a(20);
            return;
        }
        if (h3.j == 1) {
            h3.j = 0;
            switch (h2.e) {
                case 12: {
                    bk = dC[m[15]][6];
                    bi = 3;
                    h2.b();
                    c.b((byte)10);
                    return;
                }
                case 13: {
                    h4.f = true;
                    h4.j = 1;
                    c.a(h4, 1091, 0);
                }
            }
            return;
        }
        if (h3.j == 2) {
            h3.j = 0;
            c.m[h3.g] = 0;
            return;
        }
        if (h2.w >= 0) {
            switch (h2.e) {
                case 12: {
                    if (dC[m[15]][0] > 0) {
                        c.a(h3, 1087, i.R);
                        h3.c();
                        return;
                    } else {
                        c.a(h4, 1165, 0);
                        h4.a(20);
                    }
                    return;
                }
                case 13: {
                    h3.c();
                    if (dC[m[15]][0] == 0) {
                        c.a(h3, 1089, i.R);
                        return;
                    }
                    c.a(h3, 1088, i.R);
                }
                default: {
                    return;
                }
            }
        }
        if (h2.j != -1) return;
        h2.b();
    }

    static void E(Graphics graphics, h h2) {
        int n2 = h2.s;
        int n3 = h2.t[0];
        int n4 = h2.t[1];
        int n5 = h2.t[2];
        int n6 = h2.t[3];
        int n7 = 13022093;
        if (h2.i == 1) {
            n7 = 0xFFFFFF;
        }
        c.a(graphics, n7, n3, n4, n5, n6, 3, 4, 5);
        cq.a(graphics, n2 + 34, n3 + 15, n4 + 8);
        if (dC[n2][0] > 0) {
            graphics.setColor(16625779);
            graphics.fillRoundRect(n3 + 15, n4 + 40, 134, 13, 5, 5);
            graphics.fillRoundRect(n3 + 15, n4 + 60, 134, 13, 5, 5);
            cq.b(graphics, n3 + 23, n4 + 41, 116);
            cq.b(graphics, n3 + 82, n4 + 41, 117);
            cq.b(graphics, n3 + 23, n4 + 61, 118);
            c.a(graphics, 2, false, dC[n2][7], n3 + 40, n4 + 43, 20);
            c.a(graphics, 2, false, dC[n2][8], n3 + 99, n4 + 43, 20);
            c.a(graphics, 2, false, dC[n2][9], n3 + 55, n4 + 63, 24);
            cq.a(graphics, 36, n3 + 58, n4 + 63);
            c.a(graphics, 2, false, dC[n2][10], n3 + 75, n4 + 63, 24);
            cq.a(graphics, 36, n3 + 78, n4 + 63);
            c.a(graphics, 2, false, dC[n2][11], n3 + 95, n4 + 63, 24);
            for (n7 = 0; n7 < 4; ++n7) {
                if (dC[n2][n7 + 1] < 0) continue;
                cq.b(graphics, n3 + 80 + n7 * 25, n4 + 18, 112);
                cq.b(graphics, n3 + 72 + n7 * 25, n4 + 7, 124 + dC[n2][n7 + 1]);
            }
        }
        if (h2.i == 1) {
            cq.b(graphics, n3 + n5 - 20, n4 + n6 - 18, 0);
        }
    }

    static void F(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        c.b(graphics, n2, n3, n4, n5, h2.e == 12 ? 28 : 29);
    }

    static void b(Graphics graphics) {
        c.a(graphics, 0, 0, 0, 240, 320);
        graphics.drawImage(cC, 120, 160, 3);
        c.a(graphics, "\u66f4\u591a\u7cbe\u5f69\u6e38\u620f", 120, 160 - i.R, 17, 0xFFFFFF, 0xFFFFFF);
        c.a(graphics, "\u5c3d\u5728\u6e38\u620f\u9891\u9053", 120, 160, 17, 0xFFFFFF, 0xFFFFFF);
        c.a(graphics, "3g.tysis.cn", 120, 160 + i.R, 17, 0xFFFFFF, 0xFFFFFF);
        c.a(graphics, "\u786e\u8ba4", 5, 315, 36, 0xFFFFFF, 0xFFFFFF);
        c.a(graphics, "\u9000\u51fa", 235, 315, 40, 0xFFFFFF, 0xFFFFFF);
    }

    static void G(Graphics graphics, h h2) {
        fq.b(graphics, 0, 0);
        fp.b(graphics, 0, 0);
        fp.b(graphics, 120, 290, 13 + m[h2.g]);
    }

    static void J(h i2) {
        boolean bl2 = false;
        Object object = i2.b[0];
        h h2 = i2.b[1];
        h h3 = i2.b[2];
        h h4 = i2.b[3];
        h h5 = i2.b[6];
        fp.e();
        fq.e();
        if (h4.j == 1) {
            h4.j = 0;
            h4.b();
            h3.c();
            i2.f = false;
            return;
        }
        switch (i2.w) {
            case 0: {
                bj = 6;
                bi = 0;
                i2.b();
                c.b((byte)8);
                i2.d();
                c.aj();
                return;
            }
            case 2: {
                if (j.a()) {
                    object = new d();
                    i2 = IMARPG22.a;
                    at.a((MIDlet)i2, (cr)object);
                    return;
                }
                de = 1;
                c.b((byte)21);
                return;
            }
            case 3: {
                if (j.a()) {
                    i2 = IMARPG22.a;
                    at.a((MIDlet)i2);
                    return;
                }
                de = (byte)2;
                c.b((byte)21);
                return;
            }
            case 5: {
                h2.a(47, 32, 56, 176, 208, true);
                h2.c();
                return;
            }
            case 6: {
                h2.a(48, 32, 56, 176, 208, true);
                h2.c();
                int n2 = c.b(c.c(1241), h2.t[2] - 10, h2.t[3] - 10 - (i.R << 1));
                h2.d(n2);
                return;
            }
            case 7: {
                h2.a(49, 32, 56, 176, 208, true);
                h2.c();
                int n3 = c.b(c.c(1256), h2.t[2] - 10, h2.t[3] - 10 - (i.R << 1));
                h2.d(n3);
                return;
            }
            case 8: {
                c.a(h4, 1133, i.R);
                h4.c();
                return;
            }
            case 1: {
                ((h)object).c();
                return;
            }
            case 4: {
                h5.c();
            }
        }
    }

    static void H(Graphics graphics, h i2) {
        h h2 = i2.b[0];
        int n2 = i2.t[0];
        int n3 = i2.t[1];
        int n4 = i2.t[2];
        int n5 = i2.t[3];
        int n6 = m[i2.g];
        int n7 = m[h2.g];
        int n8 = n3 + 41 + 11;
        c.a(graphics, 71, n2 + 21, n3 + 36, 70, 0, i2.u, n6);
        for (n6 = 0; n6 < i2.u; ++n6) {
            cq.b(graphics, n2 + 53 + n6 * 70, n3 + 20, n6 + 122);
        }
        c.a(graphics, 14876594, n2 + 6, n3 + 36, 164, 80, 3, 4, 5);
        for (n6 = 0; n6 < h2.v; ++n6) {
            cq.b(graphics, n2 + 23, n8, 112);
            n8 += 22;
        }
        c.a(graphics, h2.D, 454, true, h2.v, n7 - h2.x, -1, n2 + 38, n3 + 41, 0, 22, -1, 120, i2.y);
        i2 = h2.D;
        n8 = n3 + 41 + 11;
        for (n6 = 0; n6 < h2.v && i2 != null; ++n6) {
            if (((b)i2).e > 1) {
                cq.b(graphics, n2 + 138, n8, 121);
            }
            i2 = ((b)i2).b;
            n8 += 22;
        }
        c.b(graphics, 113, n2 + 158, n3 + 50, 50, n7, h2.u);
        c.b(graphics, n2, n3, n4, n5, 20);
    }

    static void K(h h2) {
        h h3 = h2.b[0];
        h h4 = h2.b[1];
        int n2 = m[h2.g];
        c.b(h2, 120, 15);
        if (h2.j == 4 || h2.j == 5) {
            c.ai();
            c.a(n2, h3, d[6]);
            if (h3.C != null) {
                h4.p[1] = h3.C.d;
                h2.d(c.a(c.c(454 + h3.C.d), 120, 1));
                h4.d(c.a(c.c(522 + h3.C.d), h4.t[2], 1));
                return;
            }
            h4.p[1] = -1;
            return;
        }
        if (h3.j == 4 || h3.j == 5) {
            h4.p[1] = h3.C.d;
            h2.d(c.a(c.c(522 + h3.C.d), 120, 1));
            h4.d(c.a(c.c(522 + h3.C.d), h4.t[2], 1));
            return;
        }
        if (h2.j == -1) {
            c.ai();
            h2.b();
        }
    }

    private static int l(int n2, int n3, int n4) {
        int n5 = 0;
        switch (n3) {
            case 0: 
            case 3: {
                if (n2 == 0) {
                    n5 = cQ[n4][1];
                    break;
                }
                n5 = cQ[n4][2];
                break;
            }
            case 1: {
                n5 = n2 == 0 ? cS[n4][7] : cS[n4][8];
            }
        }
        return n5;
    }

    static void I(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int[] cfr_ignored_0 = h2.t;
        int[] cfr_ignored_1 = h2.t;
        n3 += 20;
        int n4 = 0;
        boolean bl2 = false;
        Object var5_6 = null;
        for (int i2 = 0; i2 < 3; ++i2) {
            c.a(graphics, 14876594, n2 + 6, n3, 138, 43, 3, 4, 5);
            if (i2 < h2.u) {
                a a2 = g[h2.p[n4]];
                cq.b(graphics, n2 + 10, n3 + 5, 124 + a2.s);
                c.a(graphics, 2, false, h2.p[n4 + 2], n2 + 41, n3 + 12, 20);
                int n5 = n3 + 28;
                int n6 = n2 + 10;
                a a3 = a2;
                Graphics graphics2 = graphics;
                cq.a(graphics2, 24, n6, n5);
                c.a(graphics2, 2, false, a3.F[15] + 1, n6 + 25, n5, 24);
                int n7 = a2.F[2] > a2.F[0] ? (a2.F[1] - a2.F[0]) * 100 / (a2.F[2] - a2.F[0]) : 0;
                c.c(graphics, 1, n2 + 41, n3 + 30, 95, n7);
                if (c.fk[i2].E[1] == 1) {
                    fk[i2].b(graphics, 0, 0);
                }
                n4 += 3;
            }
            n3 += 43;
        }
    }

    static void J(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.u << 1;
        c.c(graphics, 681 + h2.p[n5], n2 + (n4 >> 1), n3 + 6, 17, 0, 0);
        c.c(graphics, 1117, n2 + (n4 >> 1), n3 + 6 + i.R, 17, 0, 0);
        c.c(graphics, 590 + h2.p[n5 + 1], n2 + (n4 >> 1), n3 + 6 + (i.R << 1), 17, 0, 0);
    }

    static void K(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        c.c(graphics, 1151, n2 + (n4 >> 1), n3 + 5, 17, 0, 0);
        c.c(graphics, 1152, n2 + (n4 >> 1), n3 + 5 + i.R, 17, 0, 0);
    }

    static void L(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        int n6 = h2.x * 3 + 2;
        int n7 = 0;
        c.a(graphics, 14876594, n2 + 6, n3 + 20, 164, 115, 3, 4, 5);
        for (int i2 = 0; i2 < h2.v && n6 < h2.q; ++i2) {
            c.c(graphics, h2.p[n6], n2 + 39, n3 + 25 + n7, 20, 0, 0);
            c.b(graphics, h2.p[n6 + 1], n2 + 24, n3 + 36 + n7);
            c.a(graphics, 2, false, h2.p[n6 + 2], n2 + 150, n3 + 30 + n7, 24);
            n6 += 3;
            n7 += 23;
        }
        cq.b(graphics, n2 + (n4 >> 1), n3, 16);
        cq.b(graphics, n2 + (n4 >> 1), n3, 33);
        cq.a(graphics, 30, n2 + 35, n3 + n5 - 15);
        c.a(graphics, 2, false, h2.p[0], n2 + 35 + 13, n3 + n5 - 15 + 2, 20);
        cq.a(graphics, 32, n2 + 100, n3 + n5 - 15);
        c.a(graphics, 2, false, h2.p[1], n2 + 100 + 13, n3 + n5 - 15 + 2, 20);
        c.b(graphics, 113, n2 + 160, n3 + 37, 80, h2.x, h2.u - h2.v);
    }

    static void M(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        int n6 = m[h2.g];
        c.a(graphics, 0xFFFFFF, n2 + 3, n3 + 17, n4 + -6, n5 + -34, 3, 4, 5);
        c.a(graphics, 73, n2 + 35, n3 + 37, 55, 0, h2.u, n6);
        for (n6 = 0; n6 < h2.u; ++n6) {
            cq.b(graphics, n2 + n6 * 53 + 47, 77, n6 + 129);
        }
        c.b(graphics, n2, n3, n4, n5, 32);
    }

    static void L(h i2) {
        h h2 = i2.b[0];
        h h3 = i2.b[1];
        h h4 = i2.b[2];
        h h5 = i2.b[3];
        int n2 = m[i2.g];
        fc = 0;
        if (i2.j == 6 || i2.j == 4 || i2.j == 5) {
            switch (n2) {
                case 0: {
                    h2.D = h2.C = c.d[3].b;
                    h2.x = 0;
                    h2.u = h2.C == null ? 0 : h2.C.e();
                    c.m[h2.g] = 0;
                    return;
                }
                case 1: {
                    c.m[h2.g] = 0;
                    c.a(h2, i2.p[0] == 1 ? c.d[5].b : c.d[0].b);
                }
            }
            return;
        }
        if (i2.w >= 0) {
            if (h2.u > 0) {
                h2.c();
                h2.i = 1;
                h3.p[2] = 1;
                h3.p[0] = c.a(h2.C);
                h3.p[1] = h2.C.d;
                int n3 = c.b(h2.C);
                h3.d(c.a(c.c(n3 + h2.C.d), h3.t[2], 1));
            }
            return;
        }
        if (h2.w >= 0) {
            h2.w = -1;
            h4.c();
            c.m[h4.g] = 0;
            switch (n2) {
                case 0: {
                    h4.u = 99;
                    h4.i = 1175;
                    fn = c.l(0, h2.C.c, h2.C.d) * cY[6] / 100;
                    return;
                }
                case 1: {
                    h4.u = h2.C.e;
                    h4.i = 1176;
                    fn = c.l(1, h2.C.c, h2.C.d);
                }
            }
            return;
        }
        if (h4.j == 1) {
            h2.i = 0;
            h4.j = 0;
            b b2 = h2.C;
            switch (n2) {
                case 0: {
                    if (cY[0] >= fn) {
                        switch (i2.p[0]) {
                            case 0: {
                                c.k(b2.d, b2.c, m[h4.g] + 1);
                                break;
                            }
                            case 1: {
                                c.g(b2.d, b2.c, m[h4.g] + 1, b2.g);
                            }
                        }
                        c.k(0, 2, -(fn * (m[h4.g] + 1)));
                        c.a(h5, 1136, 0);
                        h5.a(20);
                        return;
                    }
                    c.a(h5, 1109, 0);
                    h5.a(20);
                    return;
                }
                case 1: {
                    switch (i2.p[0]) {
                        case 1: {
                            c.g(b2.d, b2.c, -(m[h4.g] + 1), b2.g);
                            c.a(h2, c.d[5].b);
                            break;
                        }
                        case 0: {
                            c.k(b2.d, b2.c, -(m[h4.g] + 1));
                            c.a(h2, c.d[0].b);
                            c.m[h2.g] = 0;
                        }
                    }
                    c.k(0, 2, fn * (m[h4.g] + 1));
                    if (null != c.d[4].b) {
                        i2 = h2.C;
                        h2.c();
                        h2.i = 1;
                        h3.p[0] = c.a((b)i2);
                        h3.p[1] = ((b)i2).d;
                        return;
                    }
                    h3.p[2] = 0;
                }
            }
            return;
        }
        if (h2.j == 4 || h2.j == 5) {
            b b3 = h2.C;
            h2.c();
            h3.p[0] = c.a(b3);
            h3.p[1] = b3.d;
            int n4 = c.b(b3);
            h3.d(c.a(c.c(n4 + b3.d), h3.t[2], 1));
            return;
        }
        if (h2.j == -1) {
            h2.i = 0;
            h2.j = 0;
            h3.p[2] = 0;
            return;
        }
        if (h4.j == -1) {
            h2.i = 0;
            h2.j = 0;
            h3.p[2] = 0;
            h4.j = 0;
            return;
        }
        if (i2.j == -1) {
            c.m[h2.g] = 0;
            i2.b();
            d[3].d();
            c.d[3].b = null;
            d[4].d();
            c.d[4].b = null;
        }
    }

    private static int a(b b2) {
        int n2 = 0;
        switch (b2.c) {
            case 0: 
            case 3: {
                n2 = 0;
                break;
            }
            case 1: {
                n2 = 2;
            }
        }
        return n2;
    }

    private static int b(b b2) {
        int n2 = 0;
        switch (b2.c) {
            case 0: 
            case 3: {
                n2 = 67;
                break;
            }
            case 1: {
                n2 = 194;
            }
        }
        return n2;
    }

    private static void a(h h2, b b2) {
        d[4].d();
        c.d[4].b = null;
        h2.u = 0;
        b b3 = d[4];
        b b4 = c.d[2].b;
        b b5 = null;
        while (b4 != null) {
            if (cS[b4.d][12] == 0) {
                ++h2.u;
                b5 = b.b();
                b5.b(b4);
                b5.c = 1;
                b3.a(b5);
                b3 = b3.b;
            }
            b4 = b4.b;
        }
        while (b2 != null) {
            if (cQ[b2.d][6] == 0) {
                ++h2.u;
                b5 = b.b();
                b5.b(b2);
                b5.c = 0;
                b3.a(b5);
                b3 = b3.b;
            }
            b2 = b2.b;
        }
        h2.D = h2.C = c.d[4].b;
    }

    static void N(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        c.c(graphics, h2.i, n2 + 15, n3 + 20, 20, 0, 0);
        c.c(graphics, 1177, n2 + 15, n3 + 50, 20, 0, 0);
        c.a(graphics, "" + (m[h2.g] + 1), n2 + 60, n3 + 20, 20, 0, 0);
        c.a(graphics, "" + fn * (m[h2.g] + 1), n2 + 60, n3 + 50, 20, 0, 0);
    }

    static void M(h h2) {
        if (h2.w >= 0) {
            h2.j = 1;
            h2.b();
            return;
        }
        if (h2.j == -1) {
            h2.j = -1;
            h2.b();
        }
    }

    static void c(Graphics graphics) {
        cq.b(graphics, 0, 0);
    }

    static void d(Graphics graphics) {
        c.b(graphics, 0, 0);
    }

    private static void b(Graphics graphics, int n2, int n3, int n4) {
        if (n2 >= 0) {
            fs.b(graphics, n3, n4, n2);
        }
    }

    static void N(h h2) {
        h h3 = h2.b[0];
        h h4 = h2.b[1];
        h h5 = h2.b[2];
        h h6 = h2.b[3];
        if (h5.j == -1) {
            h5.j = 0;
            c.ai();
            c.a(m[h2.g], h3, d[10]);
            return;
        }
        if (h3.j == 4 || h3.j == 5) {
            h4.p[1] = h3.C.d;
            h4.d(c.a(c.c(749 + h4.i), 100, 1));
            return;
        }
        if (h2.j == 4 || h2.j == 5 || h2.j == 6) {
            c.ai();
            c.a(m[h2.g], h3, d[10]);
            if (h3.C != null) {
                h4.p[2] = 1;
                h4.p[1] = h3.C.d;
                h4.d(c.a(c.c(749 + h4.i), 100, 1));
                return;
            }
            h4.p[2] = 0;
            return;
        }
        if (h3.w >= 0 && h3.C != null) {
            if (c.g[0].F[19] >= cV[h3.C.d][2]) {
                h5.c();
                h5.C = h3.C;
                h5.i = h3.C.d;
                c.c(h5, h3.C.d);
                return;
            }
            c.a(h6, 1297, 0);
            h6.a(20);
            return;
        }
        if (h2.j == -1) {
            c.ai();
            h2.b();
        }
    }

    static void O(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        int n6 = m[h2.g];
        int n7 = n2 + 54;
        c.a(graphics, 14876594, n2 + 7, n3 + 35, 162, 80, 3, 4, 5);
        c.a(graphics, 71, n2 + 22, n3 + 35, 69, 0, h2.u, n6);
        for (n6 = 0; n6 < h2.u; ++n6) {
            cq.b(graphics, n7, n3 + 20, n6 + 77);
            n7 += 69;
        }
        c.b(graphics, n2, n3, n4, n5, 26);
    }

    static void P(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = n3 + 20;
        int n5 = m[h2.g];
        if (h2.u > 0) {
            for (int i2 = 0; i2 < h2.v; ++i2) {
                cq.b(graphics, n2 + 24, n4, 112);
                n4 += 22;
            }
            c.a(graphics, h2.D, 732, true, h2.v, n5 - h2.x, -1, n2 + 40, n3 + 8, 0, 22, 0, 95, h2.y);
            cq.b(graphics, n2 + 120, n3 + 18 + (n5 - h2.x) * 22, 0);
        }
        c.b(graphics, 113, n2 + 151, n3 + 16, 50, n5, h2.u);
    }

    static void O(h h2) {
        h h3 = h2.b[0];
        h h4 = h2.b[1];
        int n2 = h2.i;
        if (h4.j == -1) {
            int n3;
            int n4;
            int n5;
            h4.j = 0;
            h3.q = 0;
            for (n5 = 12; n5 <= 14; n5 += 2) {
                int n6 = c.B(cV[n2][n5]);
                if (n6 < 0) continue;
                n4 = c.A(cV[n2][n5]);
                n3 = cV[n2][n5 + 1];
                c.k(n6, n4, n3);
                switch (n4) {
                    case 0: {
                        h3.c(n6 + 0);
                        break;
                    }
                    case 1: {
                        h3.c(n6 + 134);
                    }
                }
                h3.c(n3);
            }
            for (n5 = 4; n5 <= 10; n5 += 2) {
                int n7 = c.B(cV[n2][n5]);
                if (n7 < 0) continue;
                n4 = c.A(cV[n2][n5]);
                n3 = cV[n2][n5 + 1];
                c.k(n7, n4, -n3);
            }
            c.k(0, 9, -cV[n2][1]);
            n3 = cV[n2][3];
            n4 = 0;
            a a2 = g[0];
            int n8 = a2.F[19];
            if (n8 < 6) {
                a2.F[18] = a2.F[18] + n3;
                a2.F[18] = Math.min(a2.F[18], cN[0][13]);
                if (a2.F[18] >= cN[0][n8 + 8]) {
                    a2.F[19] = a2.F[19] + 1;
                }
            }
            c.c(h2, n2);
            h3.i = 1293;
            h3.c();
            return;
        }
        if (h2.w >= 0) {
            h h5 = h2;
            boolean bl2 = true;
            for (int i2 = 0; i2 < h5.q; i2 += 3) {
                if (h5.p[i2 + 2] >= h5.p[i2 + 1]) continue;
                bl2 = false;
                break;
            }
            if (bl2) {
                h4.c();
                cq.c(3);
                return;
            }
            h3.q = 0;
            for (int i3 = 0; i3 < h2.q; i3 += 3) {
                if (h2.p[i3 + 2] >= h2.p[i3 + 1]) continue;
                h3.c(h2.p[i3]);
                h3.c(h2.p[i3 + 1] - h2.p[i3 + 2]);
            }
            h3.i = 1294;
            h3.c();
            return;
        }
        if (h2.j == -1) {
            h2.b();
        }
    }

    static void Q(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = h2.t[2];
        int n5 = h2.t[3];
        int n6 = n3 + 68;
        c.a(graphics, 14876594, n2 + 6, n3 + 63, 164, 105, 3, 4, 5);
        c.b(graphics, h2.C.g, n2 + 20, n3 + 28);
        c.c(graphics, 732 + h2.C.d, n2 + 35, n3 + 17, 20, h2.C.f, h2.C.f);
        cq.b(graphics, n2 + (n4 >> 1), n3 + 43, 93);
        c.b(graphics, n2, n3, n4, n5, 26);
        for (int i2 = 0; i2 < h2.q; i2 += 3) {
            n5 = h2.p[i2 + 1] > h2.p[i2 + 2] ? 0xFF0000 : 65280;
            c.c(graphics, h2.p[i2], n2 + 16, n6, 20, 0, 0);
            c.a(graphics, "x" + h2.p[i2 + 1], n2 + i.ad, n6, 20, 0, 0);
            c.a(graphics, "(" + h2.p[i2 + 2] + ")", n2 + 160, n6, 24, n5, n5);
            n6 += i.R;
        }
        cq.b(graphics, n2 + (n4 >> 1), n3 + 168, 92);
    }

    static void P(h h2) {
        h[] cfr_ignored_0 = h2.b;
        if (c.b(264224)) {
            switch (h2.i) {
                case 1293: {
                    h2.b();
                    return;
                }
                case 1294: {
                    h2.b();
                }
            }
        }
    }

    static void R(Graphics graphics, h h2) {
        int n2 = h2.t[0];
        int n3 = h2.t[1];
        int n4 = n3 + i.af;
        c.c(graphics, h2.i, n2 + 10, n3 + 10, 20, 0, 0);
        for (n3 = 0; n3 < h2.q; n3 += 2) {
            c.c(graphics, h2.p[n3], n2 + 10, n4, 20, 0, 0);
            c.a(graphics, "x" + h2.p[n3 + 1], n2 + 166, n4, 24, 0, 0);
            n4 += i.R;
        }
    }

    private static void c(h h2, int n2) {
        b b2 = null;
        h2.q = 0;
        h2.c(1066);
        h2.c(cV[n2][1]);
        h2.c(cY[1]);
        for (int i2 = 4; i2 <= 10; i2 += 2) {
            int n3 = c.B(cV[n2][i2]);
            if (n3 < 0) continue;
            int n4 = c.A(cV[n2][i2]);
            int n5 = cV[n2][i2 + 1];
            switch (n4) {
                case 0: {
                    h2.c(n3 + 0);
                    b2 = d[0].c(n3);
                    break;
                }
                case 1: {
                    h2.c(n3 + 134);
                    b2 = d[2].c(n3);
                }
            }
            h2.c(n5);
            h2.c(b2 == null ? 0 : b2.e);
        }
    }

    private static int al() {
        int n2 = 0;
        for (int i2 = 0; i2 < 71; ++i2) {
            if (ft[i2] <= 0) continue;
            ++n2;
        }
        return n2;
    }

    private static void a(a a2, int n2, int[] nArray) {
        int n3 = a2.F[7];
        int n4 = a2.F[8];
        int n5 = a2.F[4];
        int n6 = a2.F[5];
        int n7 = 0;
        int n8 = 0;
        block12: for (int i2 = 0; i2 < 35; ++i2) {
            if (nArray[i2] <= 0) continue;
            switch (i2) {
                case 1: {
                    n7 += nArray[i2] * n2 / 100;
                    continue block12;
                }
                case 2: {
                    n7 += nArray[i2] * n3 / 100 * n2 / 100;
                    continue block12;
                }
                case 3: {
                    n8 += nArray[i2] * n2 / 100;
                    continue block12;
                }
                case 4: {
                    n8 += nArray[i2] * n4 / 100 * n2 / 100;
                    continue block12;
                }
                case 5: {
                    a2.F[21] = 0;
                    continue block12;
                }
                case 6: {
                    a2.F[22] = nArray[i2] * n2 / 100;
                    continue block12;
                }
                case 19: {
                    for (i2 = 40; i2 < 71; ++i2) {
                        a2.H[i2] = 0;
                    }
                    continue block12;
                }
                case 7: {
                    a2.h = 75 + nArray[i2] - 1;
                    a2.a(cl[75 + nArray[i2] - 1]);
                    a2.c(a2.i);
                    continue block12;
                }
                case 25: {
                    a2.F[17] = nArray[i2];
                    continue block12;
                }
                case 27: 
                case 28: 
                case 29: 
                case 30: 
                case 31: 
                case 32: 
                case 33: 
                case 34: {
                    int n9 = i2 - 26;
                    a2.I[n9] = a2.I[n9] + nArray[i2];
                    c.t(a2.F[26]);
                }
            }
        }
        a2.F[4] = Math.max(0, Math.min(n5 + n7, n3));
        a2.F[5] = Math.max(0, Math.min(n6 + n8, n4));
    }

    private static void a(int n2, a a2) {
        int n3;
        int n4 = 0;
        int n5 = 0;
        fu = 0;
        for (n3 = 8; n3 <= 10; n3 += 2) {
            n4 = cQ[n2][n3];
            c.ft[n4] = n5 = cQ[n2][n3 + 1];
        }
        if (ft[5] > 0 && (a2.F[21] & 1) == 0) {
            c.ft[5] = 0;
            fu |= 1;
        }
        if (ft[1] > 0 || ft[2] > 0) {
            n3 = ft[1] + ft[2] * a2.F[7] / 100;
            if (n3 + a2.F[4] > a2.F[7]) {
                c.ft[1] = a2.F[7] - a2.F[4];
                c.ft[2] = 0;
            }
            if (a2.F[4] >= a2.F[7]) {
                c.ft[1] = 0;
                c.ft[2] = 0;
                fu |= 2;
            }
        }
        if (ft[3] > 0 || ft[4] > 0) {
            n3 = ft[3] + ft[4] * a2.F[8] / 100;
            if (n3 + a2.F[5] > a2.F[8]) {
                c.ft[3] = a2.F[8] - a2.F[5];
                c.ft[4] = 0;
            }
            if (a2.F[5] >= a2.F[8]) {
                c.ft[3] = 0;
                c.ft[4] = 0;
                fu |= 4;
            }
        }
    }

    private static void b(int n2, int n3, int n4, boolean bl2) {
        a a2 = g[n2];
        b b2 = c.q(2, n4);
        if (n4 >= 0 && !bl2) {
            if (b2 == null) {
                return;
            }
            c.b(a2, n4, true);
            c.k(n4, 1, -1);
        }
        int n5 = a2.G[n3];
        a2.G[n3] = n4;
        if (n5 >= 0 && !bl2) {
            c.b(a2, n5, false);
            c.k(n5, 1, 1);
        }
        n3 = a2.G.length - 1;
        int[] nArray = new int[n3];
        int[] nArray2 = new int[n3];
        int n6 = 0;
        for (int i2 = 0; i2 < n3; ++i2) {
            n4 = a2.G[i2];
            if (n4 < 0 || (n4 = cS[n4][17]) < 0) continue;
            if (n6 > 0 && n4 == nArray[0]) {
                nArray2[0] = nArray2[0] + 1;
            }
            if (n6 != 0) continue;
            nArray[n6] = n4;
            int n7 = n6++;
            nArray2[n7] = nArray2[n7] + 1;
        }
    }

    private static void b(a a2, int n2, boolean bl2) {
        if (n2 >= 0) {
            for (int i2 = 1; i2 <= 5; i2 += 2) {
                if (cS[n2][i2] != 10) continue;
                a2.a(cS[n2][i2 + 1], bl2 ? 1 : 0);
            }
        }
    }

    private static b q(int n2, int n3) {
        b b2 = d[n2].c(n3);
        return b2;
    }

    private static int[][] a(DataInputStream dataInputStream) {
        int n2 = c.a(dataInputStream, 1);
        int n3 = c.a(dataInputStream, 1);
        int[][] nArray = new int[n3][n2];
        byte[] byArray = new byte[n2];
        try {
            dataInputStream.read(byArray);
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
        }
        for (int i2 = 0; i2 < n2; ++i2) {
            block8: for (int i3 = 0; i3 < n3; ++i3) {
                switch (byArray[i2]) {
                    case 0: {
                        nArray[i3][i2] = c.a(dataInputStream, 0);
                        continue block8;
                    }
                    case 1: {
                        nArray[i3][i2] = c.a(dataInputStream, 1);
                        continue block8;
                    }
                    case 2: {
                        nArray[i3][i2] = c.a(dataInputStream, 2);
                    }
                }
            }
        }
        return nArray;
    }

    private static void am() {
        int n2;
        if (cN != null) {
            return;
        }
        c.a("/db.pak");
        c.g(0);
        cN = c.a(dw);
        cO = c.a(dw);
        Object object = c.a(dw);
        cP = new int[4][][];
        for (n2 = 0; n2 < 4; ++n2) {
            c.cP[n2] = new int[cN[n2][6]][];
        }
        int n3 = 0;
        int n4 = 0;
        for (n2 = 0; n2 < ((int[][])object).length; ++n2) {
            c.cP[n3][n4] = object[n2];
            object[n2] = null;
            if (++n4 != cP[n3].length) continue;
            ++n3;
            n4 = 0;
        }
        cQ = c.a(dw);
        h = c.a(dw);
        cR = c.a(dw);
        i = c.a(dw);
        cS = c.a(dw);
        cT = c.a(dw);
        DataInputStream dataInputStream = dw;
        object = dataInputStream;
        n2 = c.a(dataInputStream, 1);
        n3 = 0;
        int[][] nArrayArray = new int[n2][];
        for (int i2 = 0; i2 < n2; ++i2) {
            n3 = c.a((DataInputStream)object, 1);
            nArrayArray[i2] = new int[n3];
            for (int i3 = 0; i3 < n3; ++i3) {
                nArrayArray[i2][i3] = c.a((DataInputStream)object, 1);
            }
        }
        cU = nArrayArray;
        cV = c.a(dw);
        cW = c.a(dw);
        c.a(dw);
        cX = c.a(dw);
        c.A();
    }

    private static int A(int n2) {
        int n3 = -1;
        if (n2 == 65535) {
            n3 = 2;
        } else if (n2 < 67) {
            n3 = 0;
        } else if (n2 < 127) {
            n3 = 1;
        } else if (n2 < 195) {
            n3 = 6;
        } else if (n2 < 212) {
            n3 = 8;
        } else if (n2 < 218) {
            n3 = 10;
        }
        return n3;
    }

    private static int B(int n2) {
        if (n2 == 65535) {
            n2 = 0;
        } else if (n2 >= 67) {
            n2 = n2 < 127 ? (n2 -= 67) : (n2 < 195 ? n2 - 67 - 60 : (n2 < 212 ? n2 - 67 - 60 - 68 : (n2 < 218 ? n2 - 67 - 60 - 68 - 17 : n2 - 67 - 60 - 68 - 17 - 6)));
        }
        return n2;
    }

    private static void an() {
        int n2;
        for (n2 = 0; n2 < 4; ++n2) {
            int n3 = c.g[n2].h;
            if (n3 >= 0) {
                g[n2].a(cl[c.g[n2].h]);
            }
            c.g[n2].L.a(cl[61]);
        }
        for (n2 = 0; n2 < cj; ++n2) {
            ck[n2].a(cl[c.ck[n2].h]);
            c.ck[n2].L.a(cl[61]);
        }
    }

    private static void ao() {
        try {
            switch (cb) {
                case 0: {
                    if ((k & 1) != 0) {
                        c.au();
                    }
                    c.x();
                    dn = false;
                    System.out.println("\u91ca\u653e\u8d44\u6e90");
                    break;
                }
                case 1: {
                    c.w();
                    c.C();
                    c.ar();
                    System.out.println("\u91ca\u653e\u8d44\u6e90 ");
                    break;
                }
                case 2: {
                    fL = fK;
                    c.as();
                    c.y();
                    break;
                }
                case 3: {
                    switch (bi) {
                        case 0: {
                            int n2;
                            for (n2 = 0; n2 < cz.length; ++n2) {
                                if (cz[n2] == null) continue;
                                c.cz[n2] = null;
                            }
                            cg = 2;
                            fC = 0;
                            if (ci != null) {
                                for (n2 = 21; n2 < 123; ++n2) {
                                    c.ci[n2] = 0;
                                }
                            }
                            for (n2 = 0; n2 < 4; ++n2) {
                                if (n2 == 0) {
                                    c.k(0, 1);
                                    c.l(0, 1);
                                    continue;
                                }
                                c.k(n2, 0);
                                c.l(n2, 0);
                            }
                            break;
                        }
                        case 3: {
                            for (int i2 = 0; i2 < 4; ++i2) {
                                int n3 = dC[m[15]][i2 + 1];
                                if (n3 < 0) continue;
                                c.g[i2].s = dC[m[15]][i2 + 1];
                            }
                            break;
                        }
                    }
                    System.out.println("\u521d\u59cb\u5316\u65b0\u6e38\u620f ");
                    break;
                }
                case 4: {
                    if (eA != null) break;
                    try {
                        eA = Image.createImage((String)"/png108");
                    }
                    catch (IOException iOException) {
                        IOException iOException2 = iOException;
                        iOException.printStackTrace();
                    }
                    ez = new a(108, 108, -1, eA, false);
                    break;
                }
                case 5: {
                    if ((bj & 2) != 0) {
                        for (int i3 = 0; i3 < 4; ++i3) {
                            int n4;
                            a a2 = g[i3];
                            a2.F[15] = 0;
                            for (n4 = 0; n4 < a2.G.length; ++n4) {
                                a2.G[n4] = -1;
                            }
                            for (n4 = 0; n4 < 9; ++n4) {
                                a2.I[n4] = 0;
                            }
                            c.t(i3);
                            a2.F[1] = 0;
                            a2.F[4] = a2.F[7];
                            a2.F[5] = a2.F[8];
                            a2.F[3] = 0;
                            a2.F[20] = 0;
                            a2.F[27] = cN[i3][5];
                            a2.F[17] = cP[i3][0][12];
                            a2.F[31] = cN[i3][7];
                            c.s(i3);
                        }
                    }
                    System.out.println("\u521d\u59cb\u5316\u4e3b\u89d2 ");
                    break;
                }
                case 6: {
                    dD = -1;
                    dE = -1;
                    if ((bj & 2) == 0 || cl[6] != null) break;
                    c.cl[6] = Image.createImage((String)"/png6");
                    break;
                }
                case 7: {
                    int n5;
                    for (n5 = 0; n5 < 113; ++n5) {
                        c.cm[n5] = false;
                    }
                    for (n5 = 0; n5 < 4; ++n5) {
                        c.g[n5].E[1] = 1;
                    }
                    c.cm[61] = true;
                    for (int i4 = 0; i4 < 4; ++i4) {
                        a a3 = g[i4];
                        g[i4].h = a3.s;
                        if (a3.h < 0) continue;
                        c.cm[a3.h] = true;
                    }
                    break;
                }
                case 8: {
                    c.aq();
                    System.out.println("\u521d\u59cb\u5316\u573a\u666factor ");
                    break;
                }
                case 9: {
                    if (ch >= 0) {
                        c.v();
                    } else {
                        bG = 0;
                    }
                    System.out.println("\u8f7d\u5165\u5730\u56fe");
                    break;
                }
                case 10: {
                    c.ap();
                    System.out.println("\u8f7d\u5165tile\u8d44\u6e90");
                    break;
                }
                case 11: {
                    for (int i5 = 0; i5 < 113; ++i5) {
                        if (!cm[i5] || null != cl[i5]) continue;
                        try {
                            c.cl[i5] = Image.createImage((String)("/png" + i5));
                            continue;
                        }
                        catch (IOException iOException) {
                            IOException iOException3 = iOException;
                            iOException.printStackTrace();
                        }
                    }
                    System.out.println("\u8f7d\u5165\u56fe\u7247\u8d44\u6e90");
                    break;
                }
                case 12: {
                    c.an();
                    break;
                }
                case 13: {
                    c.ac();
                    c.ad();
                    System.out.println("\u521d\u59cb\u5316\u955c\u5934");
                    break;
                }
                case 14: {
                    int n6 = cg + 1;
                    c.a("/string.pak");
                    c.g(n6);
                    c.c(dw);
                    c.A();
                    System.out.println("\u8f7d\u5165\u573a\u666f\u6587\u5b57");
                    break;
                }
                case 15: {
                    eM = new a[8];
                    eN = new a[8];
                    for (int i6 = 0; i6 < 8; ++i6) {
                        c.eM[i6] = new a(81, 81, -1);
                        c.eN[i6] = new a(6, 6, -1, cl[6], false);
                        c.eM[i6].J = b.b();
                        c.eM[i6].J.d = -1;
                        c.eM[i6].J.e = -1;
                        c.eM[i6].K = b.b();
                        c.eM[i6].K.d = -1;
                        c.eM[i6].K.e = -1;
                        c.eM[i6].H = new int[71];
                    }
                    eO = new a(6, 6, -1, cl[6], false);
                    System.out.println("\u521d\u59cb\u5316\u6218\u6597actor");
                    break;
                }
                case 16: {
                    c.z();
                    c.B();
                    if (by >= 0) {
                        c.bn[c.by] = 0;
                        while (bn[by] != 6) {
                            c.k(by);
                        }
                    }
                    System.out.println("\u8fd0\u884c\u521d\u59cb\u5316\u811a\u672c");
                    break;
                }
                case 17: {
                    int n7;
                    a a4 = a == null ? g[0] : a;
                    D = 0;
                    E = 0;
                    switch (bi) {
                        case 3: {
                            for (n7 = 0; n7 < cz.length; ++n7) {
                                if (cz[n7] == null) continue;
                                c.cz[n7] = null;
                            }
                            c.a(false, m[15]);
                            break;
                        }
                        case 2: {
                            a4.b(aZ[0], aZ[1]);
                            a4.c(aZ[3]);
                            a4.j = aZ[3];
                            for (n7 = 0; n7 < cz.length; ++n7) {
                                if (cz[n7] == null) continue;
                                switch (aZ[3]) {
                                    case 0: {
                                        cz[n7].b(aZ[0], aZ[1] + (n7 << 1));
                                        break;
                                    }
                                    case 2: {
                                        cz[n7].b(aZ[0], aZ[1] - (n7 << 1));
                                        break;
                                    }
                                    case 3: {
                                        cz[n7].b(aZ[0] + (n7 << 1), aZ[1]);
                                        break;
                                    }
                                    case 1: {
                                        cz[n7].b(aZ[0] - (n7 << 1), aZ[1]);
                                    }
                                }
                                cz[n7].c(aZ[3]);
                                c.cz[n7].j = aZ[3];
                            }
                            break;
                        }
                    }
                    c.a(2, (int)a4.v, (int)a4.w, a4, 0);
                    c.af();
                    for (n7 = 0; n7 < 4; ++n7) {
                        if (c.g[n7].E[14] != 1) continue;
                        c.g[n7].i = aZ[3] + 8;
                    }
                    break;
                }
                case 18: {
                    c.a("/portrait.pak");
                    for (int i7 = 0; i7 < dv; ++i7) {
                        int n8 = i7 + 113;
                        if (!cm[n8]) continue;
                        c.cG[i7] = new a(n8, n8, -1);
                        byte[] byArray = c.f(i7);
                        c.cl[n8] = Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
                        cG[i7].a(cl[n8]);
                    }
                    c.A();
                    break;
                }
                case 19: {
                    c.b(cq, ct);
                    break;
                }
                case 20: {
                    c.a();
                    bi = 2;
                    bK = true;
                    c.b((byte)11);
                    c.a((byte)0);
                    if ((k & 1) != 0) {
                        c.C(fw[0]);
                        break;
                    }
                    c.au();
                }
            }
            ++cb;
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private static void ap() {
        if (ch < 0) {
            return;
        }
        for (int i2 = 0; i2 < bG - 1; ++i2) {
            c.a("/tile.pak");
            boolean bl2 = false;
            for (int i3 = 0; i3 < i2; ++i3) {
                if (bH[i2] != bH[i3]) continue;
                c.bN[i2] = bN[i3];
                bl2 = true;
                break;
            }
            if (!bl2 && bN[i2] == null) {
                c.g(bH[i2]);
                try {
                    c.bN[i2] = Image.createImage((InputStream)dw);
                }
                catch (IOException iOException) {
                    IOException iOException2 = iOException;
                    iOException.printStackTrace();
                }
            }
            c.A();
        }
    }

    private static void aq() {
        c.a("/scene.pak");
        c.g(cg);
        bZ = dw;
        ch = c.a(bZ, 0);
        cj = c.a(bZ, 0);
        ck = new a[cj];
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        n3 = 0;
        n4 = 0;
        n5 = 0;
        for (int i2 = 0; i2 < cj; ++i2) {
            int n6;
            n2 = c.a(bZ, 0);
            n3 = c.a(bZ, 0);
            c.cm[n3] = true;
            n5 = c.a(bZ, 1);
            n4 = c.a(bZ, 1);
            c.ck[i2] = new a(n2, n3, n5);
            a a2 = ck[i2];
            ck[i2].L = new a(61, 61, 0);
            a2.E[2] = (short)n4;
            a2.E[16] = (short)i2;
            n3 = c.a(bZ, 1);
            n4 = c.a(bZ, 1);
            for (n6 = 0; n6 < 4; ++n6) {
                a2.q[n6] = (short)c.a(bZ, 1);
            }
            for (n6 = 0; n6 < 4; ++n6) {
                a2.r[n6] = (short)c.a(bZ, 1);
            }
            n5 = c.a(bZ, 0);
            a2.b(n3, n4);
            if (n5 == 0) continue;
            a2.p = new short[n5];
            for (n6 = 0; n6 < n5; ++n6) {
                a2.p[n6] = (short)c.a(bZ, 1);
            }
        }
        c.A();
        bZ = null;
    }

    private static void ar() {
        if (cl != null) {
            for (int i2 = 0; i2 < cl.length; ++i2) {
                if (null == cl[i2] || i2 == 6) continue;
                c.cl[i2] = null;
            }
        }
        System.gc();
    }

    private static void as() {
        if (eM != null) {
            for (int i2 = 0; i2 < 8; ++i2) {
                if (eM[i2] != null) {
                    eM[i2].a();
                    c.eM[i2] = null;
                }
                if (eN[i2] == null) continue;
                eN[i2].a();
                c.eN[i2] = null;
            }
            eM = null;
            eN = null;
        }
        if (eO != null) {
            eO.a();
            eO = null;
        }
        System.gc();
    }

    private static void d(Graphics graphics, int n2, int n3) {
        c.a(graphics, 0, 0, 0, 240, 320);
        for (int i2 = 0; i2 < 3; ++i2) {
            cu[i2].b(graphics, -120, -160);
            cu[i2].e();
        }
        c.a(graphics, 0xFF0000, 28, 275, n2 * 113 / n3, 3);
        c.a(graphics, n2 * 100 / n3 + "%", 90, 270, 33, 0xFFFFFF, 0xFFFFFF);
    }

    private static void at() {
        if (fx == null) {
            c.a("/sound.pak");
            fx = new byte[dv][];
            for (int i2 = 0; i2 < dv; ++i2) {
                c.fx[i2] = c.f(i2);
            }
            c.A();
        }
    }

    private static void au() {
        try {
            fy.stop();
            fy.deallocate();
            fy.close();
            fy = null;
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private static void C(int n2) {
        c.r(n2, -1);
        fz = n2;
    }

    private static void r(int n2, int n3) {
        if (n2 >= fx.length) {
            System.out.println("\u8d85\u51fa\u97f3\u4e50\u6587\u4ef6\u6570\u91cf");
            return;
        }
        if (n3 < 0) {
            n3 = 100;
        }
        try {
            if (fy != null) {
                if (fy.getState() == 400) {
                    return;
                }
                c.au();
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fx[n2]);
            fy = Manager.createPlayer((InputStream)byteArrayInputStream, (String)"audio/midi");
            fy.prefetch();
            fy.setLoopCount(n3);
            fy.start();
            fz = n2;
            byteArrayInputStream.close();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private static boolean b(DataInputStream dataInputStream) {
        try {
            int n2;
            if (dataInputStream.available() <= 0) {
                return false;
            }
            int n3 = c.a(dataInputStream, 3);
            if (n3 == 255) {
                return false;
            }
            int n4 = c.a(dataInputStream, 4);
            a.a[n3] = c.c(dataInputStream, n4 << 2);
            n4 = c.a(dataInputStream, 3);
            a.c[n3] = new short[n4 + 1];
            int n5 = 0;
            for (n2 = 1; n2 <= n4; ++n2) {
                a.c[n3][n2] = (short)(n5 += c.a(dataInputStream, 3) << 2);
            }
            a.b[n3] = c.c(dataInputStream, n5);
            System.gc();
            n2 = c.a(dataInputStream, 3);
            a.e[n3] = new short[n2 + 1];
            n5 = 0;
            for (n4 = 1; n4 <= n2; ++n4) {
                a.e[n3][n4] = (short)(n5 += c.a(dataInputStream, 3));
            }
            a.d[n3] = c.b(dataInputStream, n5);
            a.f[n3] = c.b(dataInputStream, n5);
            a.g[n3] = c.b(dataInputStream, n5 << 1);
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    private static void av() {
        fp.b(120, 290);
        fp.c(2);
        c.fp.k = true;
        c.b((byte)7);
        u.c();
    }

    private static void aw() {
        b b2 = c.e.b;
        int n2 = b2.d;
        int n3 = b2.c;
        int n4 = cX[n2][3];
        int n5 = cX[n2][1];
        int n6 = cX[n2][2];
        switch (n3) {
            case 0: {
                c.b(n2 + 772, 0, 5, n5, n6, -1);
                c.a((byte)1);
                cf = (byte)7;
                break;
            }
            case 1: {
                c.b(n2 + 772, 2, 5, n5, n6, -1);
                c.a((byte)1);
                cf = (byte)7;
                break;
            }
            case 2: {
                bh = n5;
                c.o(1, 7);
                break;
            }
            case 3: {
                c.o(0, 7);
                break;
            }
            case 4: {
                c.aO[2] = (short)n5;
                c.aO[3] = (short)n6;
                c.aO[4] = aO[2];
                c.aO[5] = aO[3];
                aN = 10;
                break;
            }
            case 5: {
                aN = 0;
                break;
            }
            case 6: {
                break;
            }
            case 7: {
                c.ci[n5] = (short)n6;
                break;
            }
            case 8: {
                b b3 = c.d[14].b;
                for (n3 = 0; n3 < fA; ++n3) {
                    b3 = b3.b;
                }
                b3.g();
                b3.c();
                fA = (short)(fA - 1);
            }
        }
        if (n4 >= 0) {
            b2.d = n4;
            b2.c = (byte)cX[b2.d][0];
            return;
        }
        fA = (short)(fA + 1);
        a = aQ;
        c.a((byte)0);
    }

    private static boolean ax() {
        int n2;
        int n3 = 1;
        int n4 = 1;
        int n5 = 0;
        for (n2 = 0; n2 < fG; ++n2) {
            fI[n2].e();
        }
        switch (fF) {
            case 0: {
                while (n3 != 0) {
                    if (fB >= fC || !fJ) {
                        n3 = 0;
                        continue;
                    }
                    n5 = fB * 5;
                    switch (fD[fB]) {
                        case 60: {
                            n2 = fE[n5 + 3];
                            n3 = fE[n5 + 1];
                            n4 = fE[n5 + 2];
                            int n6 = fE[n5];
                            n5 = fE[n5 + 4];
                            c.b(n2, n3, n4, n6, n5, -1);
                            fF = 1;
                            dJ = 0;
                            n4 = 0;
                            n3 = 0;
                            break;
                        }
                        case 61: {
                            c.r[0] = (byte)fE[n5];
                            fJ = false;
                            n3 = 0;
                            break;
                        }
                        case 62: {
                            c.r[1] = fE[n5];
                            c.r[2] = fE[n5 + 1];
                            c.r[3] = fE[n5 + 2];
                            c.r[4] = fE[n5 + 3];
                            c.r[5] = fE[n5 + 4];
                            n3 = 1;
                            break;
                        }
                        case 63: {
                            a a2 = c.o(fE[n5]);
                            a2.c(fE[n5 + 1]);
                            a2.b(fE[n5 + 2], fE[n5 + 3]);
                            c.fI[c.fG] = a2;
                            fG = (byte)(fG + 1);
                            n3 = 1;
                            break;
                        }
                        case 64: {
                            fH = fE[n5];
                            fF = (byte)2;
                            n3 = 0;
                        }
                    }
                    ++fB;
                }
                break;
            }
            case 1: {
                c.r(dJ);
                if (dH != 0) {
                    fF = 0;
                    dl = true;
                }
                n4 = 0;
                break;
            }
            case 2: {
                if (fH > 0) {
                    --fH;
                } else {
                    fF = 0;
                }
                n4 = 1;
            }
        }
        return n4 != 0;
    }

    private static void m(Graphics graphics) {
        switch (fF) {
            case 1: {
                c.a(graphics, dJ);
            }
        }
        for (int i2 = 0; i2 < fG; ++i2) {
            fI[i2].b(graphics, 0, 0);
        }
    }

    static void j() {
        c.b(r[4], r[2], r[3], r[1], r[5], -1);
        fF = 1;
        dJ = 0;
    }

    private static void a(Graphics graphics, String string, int n2, int n3, int n4, int n5, int n6) {
        if (n5 != n6) {
            graphics.setColor(n6);
            graphics.drawString(string, n2 + 1, n3, n4);
            graphics.drawString(string, n2, n3 + 1, n4);
            graphics.drawString(string, n2 - 1, n3, n4);
            graphics.drawString(string, n2, n3 - 1, n4);
        }
        graphics.setColor(n5);
        graphics.drawString(string, n2, n3, n4);
    }

    private static void a(Graphics graphics, String string, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        int n10 = string.length();
        n9 = n9 > 0 ? n9 : n10 / n7 + 1;
        for (int i2 = 0; i2 < n9 && n8 < n10; n8 += n7, ++i2) {
            if (n8 + n7 >= n10) {
                n7 = n10 - n8;
            }
            if (n5 != n6) {
                graphics.setColor(n6);
                graphics.drawSubstring(string, n8, n7, n2 - 1, n3 + i2 * i.R, n4);
                graphics.drawSubstring(string, n8, n7, n2 + 1, n3 + i2 * i.R, n4);
                graphics.drawSubstring(string, n8, n7, n2, n3 + i2 * i.R - 1, n4);
                graphics.drawSubstring(string, n8, n7, n2, n3 + i2 * i.R + 1, n4);
            }
            graphics.setColor(n5);
            graphics.drawSubstring(string, n8, n7, n2, n3 + i2 * i.R, n4);
        }
    }

    static int a(String string, int n2, int n3) {
        int n4 = 0;
        n4 = 0;
        n4 = 0;
        int n5 = string.length();
        switch (n3) {
            case 0: {
                n4 = n5 * i.S;
                break;
            }
            case 1: {
                n4 = n2 / i.S;
                n4 = n5 / n4 + (n5 % n4 == 0 ? 0 : 1);
                n4 *= i.R;
            }
        }
        return n4;
    }

    private static int b(String string, int n2, int n3) {
        n2 /= i.S;
        int n4 = string.length();
        n4 = n4 / (n2 *= (n3 /= i.R)) + (n4 % n2 == 0 ? 0 : 1);
        return n4;
    }

    private static void b(h h2, int n2, int n3) {
        if (h2.A <= n3) {
            ++h2.A;
            return;
        }
        if (h2.z > n2) {
            if (h2.y < h2.z) {
                ++h2.y;
                return;
            }
            h2.y = -n2;
        }
    }

    private static void c(DataInputStream dataInputStream) {
        short s2 = 0;
        try {
            int n2 = dataInputStream.readShort();
            dataInputStream.read(dy, 0, n2 << 1);
            for (int i2 = 0; i2 < n2; ++i2) {
                s2 = c.b(dy, i2 << 1);
                dataInputStream.read(dz, 0, s2);
                c.fM[c.fL] = new String(dz, 0, (int)s2, "UTF-8");
                ++fL;
            }
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    static String c(int n2) {
        String string = "";
        if (n2 >= 0) {
            string = fM[n2];
        }
        return string;
    }

    private static void c(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7) {
        String string = fM[n2];
        c.a(graphics, string, n3, n4, n5, n6, n7);
    }

    private static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
        String string = fM[n2];
        c.a(graphics, string, n3, n4, n5, n6, n7, n8, 0, n10);
    }

    private static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11) {
        String string = fM[n2];
        graphics.setClip(n3, n4, n5, n6);
        if ((n7 & 8) != 0) {
            n3 += n5;
        } else if ((n7 & 1) != 0) {
            n3 += n5 >> 1;
        }
        if ((n7 & 0x20) != 0) {
            n4 += n6;
        } else if ((n7 & 2) != 0) {
            n4 += n6 >> 1;
        }
        switch (n10) {
            case 0: {
                c.a(graphics, string, n3 - n11, n4, n7, n8, n9);
                break;
            }
            case 1: {
                c.a(graphics, string, n3, n4 - n11, n7, n8, n9, n5 /= i.S, 0, 0);
            }
        }
        graphics.setClip(0, 0, 240, 320);
    }

    private static int D(int n2) {
        Object object = fM[n2];
        object = ((String)object).toCharArray();
        int n3 = 0;
        for (int i2 = 0; i2 < ((Object)object).length; ++i2) {
            if (object[i2] == 47) {
                ++i2;
                continue;
            }
            n3 += i.Q.charWidth((char)object[i2]);
        }
        return n3;
    }

    static {
        t = null;
        u = null;
        v = null;
        w = null;
        x = null;
        y = null;
        aO = new short[6];
        aP = null;
        a = null;
        aQ = null;
        b = -1;
        aR = 0;
        aS = -1;
        aT = null;
        aU = null;
        c = null;
        d = new b[15];
        aV = 0;
        aY = null;
        aZ = new int[4];
        ba = new int[16];
        bb = new int[16];
        bc = new byte[16];
        bd = new short[16];
        be = 0;
        bi = 0;
        bj = 0;
        bk = 0;
        bl = 0;
        bm = null;
        bn = null;
        bo = null;
        bp = null;
        bq = null;
        br = null;
        bs = null;
        bt = null;
        bu = null;
        bv = null;
        bw = null;
        bx = null;
        by = -1;
        bz = 0;
        bA = 0;
        bB = new byte[64];
        bC = new short[64];
        bD = new int[448];
        bE = -1;
        bF = 0L;
        bG = 0;
        bR = -1;
        bS = 0;
        bT = 0;
        bU = false;
        bV = 0;
        bW = 0;
        bX = 0;
        bY = new Random();
        f = false;
        ca = true;
        cb = 0;
        cc = (byte)18;
        cd = 0;
        ce = 0;
        cf = 0;
        cg = -1;
        ch = -1;
        ci = new int[123];
        cj = -1;
        ck = null;
        cl = null;
        cm = new boolean[122];
        cn = null;
        co = null;
        cp = null;
        cq = null;
        cr = null;
        cs = null;
        ct = -1;
        cv = null;
        cw = 0;
        cx = 0;
        cy = new a[192];
        g = null;
        cz = null;
        cA = null;
        cB = null;
        cC = null;
        cD = "";
        cE = new a[8];
        cF = 0;
        cG = null;
        cH = "";
        cJ = 0;
        cK = 0;
        cL = 240;
        cM = 320;
        cY = new int[10];
        cZ = new Image[4];
        da = new Image[7];
        j = 0;
        de = 0;
        k = 2;
        df = false;
        dg = 0;
        di = dh = System.currentTimeMillis();
        dj = 0;
        dk = false;
        dl = false;
        dm = new int[8];
        dn = false;
        ds = true;
        dx = 0L;
        dy = new byte[10240];
        dz = new byte[10240];
        dD = -1;
        dE = -1;
        dF = -1;
        dG = -1;
        dH = 0;
        dI = 0;
        dJ = 0;
        dK = 0;
        dL = 0;
        dM = 0;
        dN = 0;
        dO = 0;
        dP = 0;
        dQ = 0;
        dR = 0;
        dS = new int[5];
        dV = new int[5];
        dX = 0;
        dY = 0;
        dZ = 0;
        ea = 0;
        eb = 0;
        ec = 0;
        ed = 0;
        ee = 0;
        ef = 0;
        eg = false;
        eh = true;
        ei = new int[64];
        ej = new int[128];
        ek = 0;
        el = 0;
        em = 0;
        en = 0;
        ep = 0;
        eq = 2;
        er = 240;
        es = 0;
        et = 0;
        eu = 0;
        ev = false;
        ex = null;
        ey = 0;
        ez = null;
        eA = null;
        eB = 0;
        eC = new int[128];
        eD = 0;
        eE = new int[5];
        eF = 0;
        eG = null;
        eI = new byte[8];
        eJ = 0;
        eK = null;
        eL = 0;
        eM = null;
        eN = null;
        eO = null;
        eP = 0;
        eR = 0;
        eS = 0;
        eT = 0;
        l = false;
        eU = 0;
        eV = new int[4];
        eW = 0;
        eX = 0;
        eY = 0;
        fa = new int[224];
        fb = 0;
        fc = 0;
        fd = 0;
        fe = 0;
        fg = false;
        fj = false;
        m = new int[37];
        n = -1;
        fk = new a[3];
        p = 0;
        fl = 0;
        q = new int[4];
        fm = new int[3];
        fr = null;
        fs = null;
        ft = new int[71];
        fu = 0;
        fv = 0;
        fw = new byte[2];
        fA = 0;
        fB = 0;
        fC = 0;
        fD = new byte[32];
        fE = new int[160];
        r = new int[6];
        fF = 0;
        fG = 0;
        fH = 0;
        fI = new a[8];
        fJ = true;
        fK = 0;
        fL = 0;
    }
}

