/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Canvas
 *  javax.microedition.lcdui.Font
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 *  javax.microedition.media.Manager
 */
package src;

import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.media.Manager;
import src.GameMidlet;
import src.a;
import src.c;
import src.e;
import src.f;
import src.g;
import src.h;
import src.i;
import src.k;
import src.m;
import src.n;
import src.o;
import src.r;
import src.u;
import src.v;
import src.x;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class b
extends Canvas
implements Runnable,
x {
    private GameMidlet a;
    c a;
    i a;
    o a;
    f a;
    f b;
    a a;
    u a;
    g a;
    v a;
    private n[] a;
    Graphics a;
    int[] a;
    private int[] b;
    private boolean b;
    private long a;
    private long b;
    public byte a;
    private byte h;
    public byte b;
    private byte i;
    private Image b;
    private Graphics b;
    private String[] b;
    byte c;
    int a;
    private short a;
    private byte j;
    private byte k;
    private byte l;
    private byte[][] a;
    private int h;
    private int[] c;
    private int[] d;
    private int i;
    private int j;
    byte[] a;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    boolean a;
    private boolean c;
    private int p;
    private Image[] i;
    private short[] j;
    private short[][] k;
    private short[][] l;
    private byte m;
    private byte n;
    private byte o;
    private byte[] b;
    private byte p;
    private String[] c;
    private boolean d;
    private int q;
    private int r;
    private int s;
    private int t;
    int b;
    byte d;
    private byte q;
    int c;
    int d;
    private String[] d;
    private byte r;
    private int u;
    private byte[] c;
    private String[] e;
    private byte s;
    byte e;
    byte f;
    private byte t;
    private byte u;
    private byte v;
    private String[] f;
    private byte[] d;
    private byte w;
    String[] a;
    byte g;
    int e;
    Image[] a;
    short[] a;
    Image[] b;
    short[] b;
    short[][] a;
    short[][] b;
    Image[] c;
    short[] c;
    short[][] c;
    short[][] d;
    Image[] d;
    short[] d;
    short[][] e;
    short[][] f;
    Image[] e;
    short[] e;
    short[][] g;
    short[][] h;
    Image[] f;
    short[] f;
    private byte[] e;
    Image[] g;
    short[] g;
    short[][] i;
    short[][] j;
    short[] h;
    Image[] h;
    short[] i;
    private byte[][] b;
    int f;
    int g;
    private int v;
    private boolean e;
    private byte x;
    private int[][] a;
    private int[] e;
    private int[] f;
    private int[] g;
    Image a;

    public b(GameMidlet object) {
        int[][] nArrayArray = new int[][]{{0xFFFFFF}, {0}, {0, 16744743, 3, 16731672, 6, 16715781}};
        this.b = new String[]{"$2\u84dd\u8272$1\u62a4\u7532\u7c7b\u578b\u5e94\u8be5\u7528\u5251\u653b\u51fb\u3002", "$2\u7ea2\u8272$1\u62a4\u7532\u7c7b\u578b\u5e94\u8be5\u7528\u62f3\u5957\u653b\u51fb\u3002", "$2\u9ec4\u8272$1\u62a4\u7532\u7c7b\u578b\u5e94\u8be5\u7528\u5251\u3001\u62f3\u5957\u653b\u51fb\u5747\u53ef\u3002", "$1\u65e0\u62a4\u7532\u7c7b\u578b\u7684\u654c\u4eba\u5219\u65e0\u62a4\u7532\u663e\u793a\u3002", "$1\u5728$2\u6e38\u620f\u5e2e\u52a9$1\u4e2d\u53ef\u4ee5\u67e5\u770b$2\u6750\u6599\u7684\u6389\u843d$1\u60c5\u51b5\u3002", "$1\u5728$2\u5de6\u8f6f\u952e$1\u7684$2\u661f\u9645\u5546\u5e97$1\u4e2d\u4ef7\u683c\u4e70\u9ad8\u5356\u4f4e\u3002", "$1\u7075\u6d3b\u8fd0\u7528\u4f60\u7684$2\u5750\u9a91$1\u53ef\u4ee4\u4f60\u7545\u901a\u65e0\u963b\u3002", "$1\u5404\u4e2a$2\u57ce\u5e02$1\u6240\u953b\u9020\u7684\u88c5\u5907\u5747\u4e0d\u4e00\u6837\u3002", "$2\u5347\u7ea7\u57ce\u5e02$1\u53ef\u4ee4\u519b\u5de5\u5382\u7684\u953b\u9020\u88c5\u5907\u589e\u591a\u3002", "\u5728\u57ce\u5e02\u548c\u6218\u6597\u4e2d\u6309$2 * \u952e$1\u53ef\u8fdb\u5165\u4efb\u52a1\u67e5\u770b\u3002", "\u5347\u7ea7\u57ce\u5e02\u53ef\u589e\u52a0\u77ff\u5de5\u96c7\u4f63\u4e0a\u9650\u3002"};
        this.a = new byte[][]{{0, 1, 7, 8, 3, 5, 35, 11, 16, 34, 29, 44, 37, 51}, {4, 13, 12, 24, 20, 33, 52}, {27, 24, 52}, {26, 25, 14, 53}, {28, 24, 52}, {26, 28, 12, 10, 20, 15, 15, 15, 15, 15, 21, 22}, {28, 12, 10, 20, 15, 15, 15, 15, 15, 21, 22}, {32, 31, 7, 8, 11, 5, 3, 34, 44, 16, 37, 29, 51}, {27, 28, 12, 10, 20, 15, 15, 15, 15, 15, 21, 22}, {28, 25, 14, 53}, {4, 13, 39, 40, 12, 10, 20, 15, 15, 15, 15, 15, 43, 22}, {4, 13, 39, 40, 12, 25, 14, 53}, {28, 12, 10, 20, 45, 32, 38, 46, 30, 50, 15, 15, 15, 15, 15, 21, 22}, {28, 12, 10, 20, 45, 33, 32, 38, 49, 30, 50, 15, 15, 15, 15, 15, 21, 22}, {28, 12, 10, 20, 32, 38, 46, 30, 50, 15, 15, 15, 15, 15, 21, 22}, {28, 32, 47, 38, 49, 48, 50, 25, 14, 53}, {28, 25, 14, 32, 38, 46, 30, 50, 53}, {32, 31, 7, 8, 11, 5, 3, 34, 44, 16, 37, 29, 13, 39, 40, 12, 10, 20, 15, 15, 15, 15, 15, 43, 22}, {32, 31, 7, 8, 11, 5, 3, 34, 44, 16, 37, 29, 13, 39, 40, 12, 25, 14, 53}};
        this.h = 12;
        this.c = null;
        this.d = null;
        byte[] byArray = new byte[]{18, 20, 21, 22};
        this.o = 1;
        this.b = new byte[]{18, 17, 20, 19};
        this.s = 1;
        this.t = 2;
        this.c = 30;
        this.d = 30;
        this.c = new byte[]{12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 1, 2, 3, 4, 5, 6, 7, 8};
        this.e = new String[]{"\u65af\u5361\u683c\u62c9\u514b\u4e18\u9675", "\u65b0\u9521\u5b89\u3001\u4e4c\u62c9\u5c14\u5e73\u539f", "\u65af\u5361\u683c\u62c9\u514b\u4e18\u9675", "\u65b0\u9521\u5b89\u3001\u4e4c\u62c9\u5c14\u5e73\u539f", "\u5317\u90e8\u6d77\u6e7e", "\u6d77\u62c9\u5c14\u9a7f\u9053", "\u5df4\u4f10\u5229\u4e9a\u5821\u5792", "\u80af\u7279\u536b\u57ce\u3001\u7c73\u4e9a\u661f\u524d\u54e8\u7ad9", "\u5317\u90e8\u6d77\u6e7e", "\u963f\u73c2\u5821\u5792\u3001\u53f6\u5c3c\u585e\u5c71\u5cad\u3001\u6d77\u62c9\u5c14\u9a7f\u9053\u3001\u4e4c\u62c9\u5c14\u5e73\u539f\u3001\u7c73\u4e9a\u661f\u524d\u54e8\u7ad9", "\u5317\u90e8\u6d77\u6e7e", "\u963f\u73c2\u5821\u5792\u3001\u5c0f\u4e9a\u7ec6\u4e9a\u5821\u5792\u5916\u90e8", "\u65af\u5361\u683c\u62c9\u514b\u4e18\u9675", "\u963f\u73c2\u5821\u5792\u3001\u65b0\u9521\u5b89", "\u5df4\u4f10\u5229\u4e9a\u5821\u5792", "\u65b0\u9521\u5b89\u3001\u4e4c\u62c9\u5c14\u5e73\u539f", "\u5317\u90e8\u6d77\u6e7e", "\u963f\u73c2\u5821\u5792\u3001\u963f\u63d0\u62c9\u4f4e\u5730\u3001\u7c73\u4e9a\u661f\u524d\u54e8\u7ad9\u3001\u65b0\u9521\u5b89\u3001\u6208\u5170\u9ad8\u5730", "\u65af\u5361\u683c\u62c9\u514b\u4e18\u9675\u3001\u5317\u90e8\u6d77\u6e7e\u3001\u5df4\u4f10\u5229\u4e9a\u5821\u5792", "\u963f\u63d0\u62c9\u4f4e\u5730\u3001\u5c0f\u4e9a\u7ec6\u4e9a\u5821\u5792\u5916\u90e8\u3001\u7c73\u4e9a\u661f\u524d\u54e8\u7ad9\u3001\u65b0\u9521\u5b89\u3001\u6208\u5170\u9ad8\u5730", "\u5df4\u4f10\u5229\u4e9a\u5821\u5792", "\u5c0f\u4e9a\u7ec6\u4e9a\u5821\u5792\u5916\u90e8\u3001\u7c73\u4e9a\u661f\u524d\u54e8\u7ad9\u3001\u6208\u5170\u9ad8\u5730", "\u5927\u9ad8\u52a0\u7d22", "\u53f6\u5c3c\u585e\u5c71\u5cad", "\u5927\u9ad8\u52a0\u7d22", "\u53f6\u5c3c\u585e\u5c71\u5cad", "\u5927\u9ad8\u52a0\u7d22\u3001\u8d1d\u5c14\u96ea\u539f", "\u6d77\u62c9\u5c14\u9a7f\u9053\u3001\u80af\u7279\u536b\u57ce\u3001\u7279\u5c14\u83ab\u4e1c\u5ce1\u8c37\u3001\u5c0f\u4e9a\u7ec6\u4e9a\u5821\u5792\u5185\u90e8\u3001\u7c73\u4e9a\u661f\u524d\u54e8\u7ad9\u3001\u65b0\u9521\u5b89\u3001\u6208\u5170\u9ad8\u5730", "\u5927\u9ad8\u52a0\u7d22\u3001\u8d1d\u5c14\u96ea\u539f", "\u5927\u9ad8\u52a0\u7d22\u3001\u8d1d\u5c14\u96ea\u539f", "\u683c\u91cc\u65af\u6cb3\u8c37", "\u963f\u63d0\u62c9\u4f4e\u5730", "\u683c\u91cc\u65af\u6cb3\u8c37", "\u963f\u63d0\u62c9\u4f4e\u5730", "\u5df4\u4f10\u5229\u4e9a\u5821\u5792", "\u7279\u5c14\u83ab\u4e1c\u5ce1\u8c37\u3001\u5c0f\u4e9a\u7ec6\u4e9a\u5821\u5792\u5916\u90e8\u3001\u6208\u5170\u9ad8\u5730", "\u683c\u91cc\u65af\u6cb3\u8c37", "\u963f\u63d0\u62c9\u4f4e\u5730\u3001\u4e4c\u62c9\u5c14\u5e73\u539f", "\u5927\u9ad8\u52a0\u7d22", "\u53f6\u5c3c\u585e\u5c71\u5cad", "\u8d1d\u5c14\u96ea\u539f", "\u6d77\u62c9\u5c14\u9a7f\u9053\u3001\u80af\u7279\u536b\u57ce\u3001\u7279\u5c14\u83ab\u4e1c\u5ce1\u8c37\u3001\u5c0f\u4e9a\u7ec6\u4e9a\u5821\u5792\u5185\u90e8", "\u5df4\u4f10\u5229\u4e9a\u5821\u5792", "\u963f\u73c2\u5821\u5792\u3001\u65b0\u9521\u5b89\u3001\u53f6\u5c3c\u585e\u5c71\u5cad\u3001\u7c73\u4e9a\u661f\u524d\u54e8\u7ad9"};
        this.s = (byte)this.c.length;
        this.f = new String[]{"\u6e38\u620f\u64cd\u4f5c", "\u654c\u5175\u56fe\u9274"};
        this.e = new byte[]{0, 1, 2, 3};
        this.i = new short[]{0, 10, 0, 9, 10, 0, 20, 0, 9, 10, 0, 0, 0, 9, 10};
        this.b = new byte[][]{{0, 1, 2, 3, 4, 5, 6, 7}, {1, 0, 3, 2, 5, 4, 7, 6}, {2, 3, 0, 1, 6, 7, 4, 5}, {3, 2, 1, 0, 7, 6, 5, 4}, {4, 6, 5, 7, 0, 2, 1, 3}, {5, 7, 4, 6, 1, 3, 0, 2}, {6, 4, 7, 5, 2, 0, 3, 1}, {7, 5, 6, 4, 3, 1, 2, 0}};
        this.v = -1;
        this.a = new int[][]{{0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF}, {0xFFFFFF, 0xFFFFFF, 196354, 182015, 14156543, 16745730, 0xFF0000, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF}};
        this.e = new int[72];
        this.f = new int[144];
        this.g = new int[16];
        this.setFullScreenMode(true);
        this.a = object;
        this.a = new f("XJCS");
        this.b = new f("XJCS_SMS");
        this.a = new g();
        this.a = new a(this);
        this.a.c();
        this.a = new int[320];
        this.l();
        object = this;
        src.m.a = new byte[10000];
        src.m.a[0] = 0;
        src.m.a(src.m.a, "/data/loading.data", 0);
        ((b)object).j = src.m.a(src.m.a, src.m.a);
        ((b)object).k = src.m.a(src.m.a, src.m.a);
        ((b)object).l = src.m.a(src.m.a, src.m.a);
        ((b)object).i = new Image[1];
        ((b)object).i[0] = src.m.a("/ui/" + ((b)object).b[((b)object).o] + ".png");
        src.m.a = null;
        object = this;
        this.b = true;
        new Thread((Runnable)object).start();
        this.b = Image.createImage((int)320, (int)240);
        this.a = this.b.getGraphics();
        this.a("/data/hzk12index.data", 12);
        this.a = src.m.a("/data/hzk12.data");
        this.a(3, 0, (byte)0);
    }

    private void l() {
        this.b = new int[320];
        for (int i2 = 0; i2 < this.b.length; ++i2) {
            this.b[i2] = -1275068416;
        }
    }

    protected final void paint(Graphics object) {
        Object object2;
        this.b = object;
        this.a.setFont(Font.getFont((int)0, (int)0, (int)8));
        this.a();
        this.b(0, 0, 0, 320, 240);
        object = this;
        switch (((b)object).a) {
            case 14: {
                ((b)object).a.a();
                break;
            }
            case 0: {
                break;
            }
            case 1: {
                Object object3;
                object2 = object;
                switch (((b)object2).p) {
                    case 0: 
                    case 1: 
                    case 2: {
                        ((b)object2).a.setColor(0);
                        ((b)object2).a.fillRect(0, 0, 320, 240);
                        int n2 = 80;
                        n2 = 180;
                        n2 = 0;
                        object3 = object2;
                        short s2 = ((b)object3).l[0][((b)object3).m + 1];
                        for (int i2 = 0; i2 < ((b)object3).k[s2].length; i2 += 4) {
                            short s3 = ((b)object3).k[s2][i2];
                            ((b)object3).a(((b)object3).i[((b)object3).j[s3 * 5]], ((b)object3).k[s2][i2 + 1], (int)((b)object3).k[s2][i2 + 2], 20, (int)n[((b)object3).k[s2][i2 + 3]], 180, 80, ((b)object3).j, (int)s3);
                        }
                        ((b)object3).n = (byte)(((b)object3).n + 1);
                        s2 = 0;
                        if (((b)object3).n == ((b)object3).l[s2][((b)object3).m]) {
                            ((b)object3).n = 0;
                            ((b)object3).m = (byte)(((b)object3).m + 2);
                            if (((b)object3).m == ((b)object3).l[s2].length) {
                                ((b)object3).m = 0;
                            }
                        }
                        if (((b)object2).c == null) break;
                        ((b)object2).a(((b)object2).c, 160, 80, 17, 0, ((b)object2).c.length, 0xFFFFFF);
                    }
                }
                break;
            }
            case 2: {
                object2 = object;
                ((b)object2).b(0, 0, 0, 320, 240);
                ((b)object2).a("\u662f\u5426\u5f00\u542f\u58f0\u97f3", 160, 110, 33, 0xFFFFFF);
                ((b)object2).a("\u662f", 5, 235, 36, 0xFFFFFF);
                ((b)object2).a("\u5426", 315, 235, 40, 0xFFFFFF);
                break;
            }
            case 3: {
                ((b)object).a.a();
                break;
            }
            case 4: {
                object2 = object;
                if (((b)object2).a.d) {
                    ((b)object2).a.e((byte)1);
                } else {
                    ((b)object2).a.e((byte)0);
                }
                ((b)object).a.d();
                ((b)object).a.z();
                ((b)object).a.h();
                ((b)object).a.P();
                ((b)object).a.e(0);
                ((b)object).a.x();
                super.p();
                break;
            }
            case 5: {
                ((b)object).c();
                ((b)object).a.d();
                ((b)object).a.F();
                ((b)object).a.z();
                ((b)object).a.h();
                ((b)object).a.P();
                ((b)object).a.e(0);
                ((b)object).a.x();
                ((b)object).a.Y();
                super.p();
                break;
            }
            case 6: {
                switch (((b)object).b) {
                    case 4: {
                        ((b)object).a.e((byte)1);
                        super.p();
                        break;
                    }
                    case 5: {
                        ((b)object).c();
                        super.p();
                        break;
                    }
                    case 10: {
                        ((b)object).a.a((byte)1);
                        super.p();
                    }
                }
                ((b)object).a.e(0);
                ((b)object).a.Q();
                break;
            }
            case 7: {
                ((b)object).a.a();
                ((b)object).a.h();
                ((b)object).a.P();
                ((b)object).a.e(-60);
                ((b)object).a.z();
                ((b)object).a.x();
                break;
            }
            case 8: {
                object2 = object;
                ((b)object2).b(0, 0, 0, 320, 240);
                ((b)object2).a("\u6e38\u620f\u6682\u505c", 160, 120, 33, 0xFFFFFF);
                ((b)object2).a("\u8fd4\u56de", 310, 230, 40, 0xFFFFFF);
                break;
            }
            case 9: {
                object2 = object;
                ((b)object2).a.a();
                Object object3 = ((b)object2).a;
                ((b)object2).q = ((u)object3).a;
                object3 = ((b)object2).a;
                ((b)object2).r = ((u)object3).b;
                ((b)object2).a.a /= 3;
                ((b)object2).a.b /= 3;
                u u2 = ((b)object2).a;
                object3 = u2;
                object3 = ((b)object2).a;
                ((b)object2).a.translate(-u2.a, -((u)object3).b);
                ((b)object2).a.c(0);
                u u3 = ((b)object2).a;
                object3 = u3;
                object3 = ((b)object2).a;
                ((b)object2).a.translate(u3.a, ((u)object3).b);
                ((b)object2).a.a = ((b)object2).q;
                ((b)object2).a.b = ((b)object2).r;
                ((b)object2).a.a = (((b)object2).a.a << 1) / 3;
                ((b)object2).a.b = (((b)object2).a.b << 1) / 3;
                u u4 = ((b)object2).a;
                object3 = u4;
                object3 = ((b)object2).a;
                ((b)object2).a.translate(-u4.a, -((u)object3).b);
                ((b)object2).a.c(1);
                super.n();
                u u5 = ((b)object2).a;
                object3 = u5;
                object3 = ((b)object2).a;
                ((b)object2).a.translate(u5.a, ((u)object3).b);
                ((b)object2).a.a = ((b)object2).q;
                ((b)object2).a.b = ((b)object2).r;
                u u6 = ((b)object2).a;
                object3 = u6;
                object3 = ((b)object2).a;
                ((b)object2).a.translate(-u6.a, -((u)object3).b);
                ((b)object2).a.y();
                ((b)object2).a.c(2);
                ((b)object2).a.w();
                u u7 = ((b)object2).a;
                object3 = u7;
                object3 = ((b)object2).a;
                ((b)object2).a.translate(u7.a, ((u)object3).b);
                break;
            }
            case 10: {
                object2 = object;
                if (((b)object2).a.d) {
                    ((b)object2).a.a((byte)1);
                } else {
                    ((b)object2).a.a((byte)0);
                }
                ((b)object).a.h();
                ((b)object).a.P();
                ((b)object).a.z();
                super.p();
                ((b)object).a.x();
                break;
            }
            case 11: {
                ((b)object).a.a(((b)object).a.a[0]);
                break;
            }
            case 12: {
                ((b)object).a.U();
                break;
            }
            case 13: {
                switch (((b)object).b) {
                    case 4: {
                        ((b)object).a.e((byte)1);
                        break;
                    }
                    case 5: {
                        ((b)object).c();
                        break;
                    }
                    case 10: {
                        ((b)object).a.a((byte)1);
                    }
                }
                ((b)object).a.V();
                break;
            }
            case 52: {
                object2 = object;
                ((b)object2).a.drawImage(((b)object2).a, 0, 0, 20);
                ((b)object2).a.setColor(0xFFFB99);
                ((b)object2).a.drawString("\u786e\u8ba4\u9000\u51fa", 160, 120, 33);
                ((b)object2).a.drawString("\u662f", 0, 240, 36);
                ((b)object2).a.drawString("\u5426", 320, 240, 40);
                break;
            }
            case 54: {
                object2 = object;
                ((b)object2).a.drawImage(((b)object2).a, 0, 0, 20);
                ((b)object2).a.setColor(0xFFFB99);
                ((b)object2).a.drawString("\u66f4\u591a\u7cbe\u5f69\u6e38\u620f", 160, 110, 33);
                ((b)object2).a.drawString("\u5c3d\u5728\u6e38\u620f\u9891\u9053", 160, 130, 33);
                ((b)object2).a.drawString("wap.ttsy.org", 160, 150, 33);
                ((b)object2).a.drawString("\u786e\u8ba4", 0, 240, 36);
                ((b)object2).a.drawString("\u9000\u51fa", 320, 240, 40);
                break;
            }
            case 55: {
                object2 = object;
                ((b)object2).a.drawImage(((b)object2).a, 0, 0, 20);
                ((b)object2).a.setColor(0xFFFB99);
                ((b)object2).a.drawString("\u662f\u5426\u9000\u51fa\u6e38\u620f\u8054\u7f51", 160, 110, 33);
                ((b)object2).a.drawString("\u786e\u8ba4", 0, 240, 36);
                ((b)object2).a.drawString("\u8fd4\u56de", 320, 240, 40);
            }
        }
        object2 = object;
        if (((b)object2).a && ((b)object2).a != 1) {
            switch (((b)object2).l) {
                case 0: 
                case 1: 
                case 3: {
                    for (int i3 = 0; i3 < 240; ++i3) {
                        ((b)object2).a.drawRGB(((b)object2).a, 0, 320, 0, i3, 320, 1, true);
                    }
                    break;
                }
                case 2: {
                    for (int i4 = 0; i4 < 240; ++i4) {
                        ((b)object2).a.drawRGB(((b)object2).a, 0, 320, 0, i4, 320, 1, true);
                    }
                    break;
                }
            }
        }
        object = this;
        switch (((b)object).c) {
            case 0: 
            case 2: {
                ((b)object).b.drawImage(((b)object).b, 0, 0, 0);
                return;
            }
            case 1: {
                ((b)object).b.drawRGB(src.m.a(((b)object).b), 0, 320, 0, 0, 320, 240, false);
            }
        }
    }

    public final void a() {
        this.a.setClip(0, 0, 320, 240);
    }

    public final void run() {
        Object object;
        try {
            while (this.b) {
                int n2;
                Object object2;
                this.a = System.currentTimeMillis();
                object = this;
                if (((b)object).a && ((b)object).k == 0) {
                    src.k.a();
                } else {
                    if (src.k.a) {
                        src.k.b();
                    } else {
                        src.k.a();
                    }
                    switch (((b)object).a) {
                        case 14: {
                            ((b)object).a.b();
                            break;
                        }
                        case 0: {
                            break;
                        }
                        case 1: {
                            break;
                        }
                        case 2: {
                            object2 = object;
                            if (src.k.a(65536)) {
                                ((b)object2).a.a = 2;
                                ((b)object2).a(3, 0, (byte)0);
                                break;
                            }
                            if (!src.k.a(131072)) break;
                            ((b)object2).a.a = 0;
                            ((b)object2).a(3, 0, (byte)0);
                            break;
                        }
                        case 3: {
                            ((b)object).a.b();
                            break;
                        }
                        case 4: {
                            if (((b)object).a.b) {
                                ((b)object).a.g();
                                break;
                            }
                            if (((b)object).a.f) {
                                ((b)object).a.d(((b)object).a.a[0]);
                                break;
                            }
                            if (((b)object).a.d) {
                                ((b)object).a.A();
                                break;
                            }
                            ((b)object).a.o();
                            break;
                        }
                        case 5: {
                            if (((b)object).a.b) {
                                ((b)object).a.g();
                                break;
                            }
                            if (((b)object).a.f) {
                                ((b)object).a.d(((b)object).a.a[0]);
                                break;
                            }
                            if (((b)object).a.d) {
                                ((b)object).a.A();
                                break;
                            }
                            object2 = object;
                            ((b)object2).a.c(((b)object2).a.a[0]);
                            break;
                        }
                        case 6: {
                            ((b)object).a.R();
                            break;
                        }
                        case 7: {
                            if (((b)object).a.b) {
                                ((b)object).a.g();
                                break;
                            }
                            if (((b)object).a.f) {
                                ((b)object).a.d(((b)object).a.a[0]);
                                break;
                            }
                            if (((b)object).a.d) {
                                ((b)object).a.A();
                                break;
                            }
                            ((b)object).a.b();
                            break;
                        }
                        case 8: {
                            object2 = object;
                            if (!src.k.a(131072)) break;
                            ((b)object2).a = ((b)object2).i;
                            super.c(((b)object2).v);
                            ((b)object2).a.a();
                            ((b)object2).a.b();
                            break;
                        }
                        case 10: {
                            if (((b)object).a.b) {
                                ((b)object).a.g();
                                break;
                            }
                            if (((b)object).a.f) {
                                ((b)object).a.d(((b)object).a.a[0]);
                                break;
                            }
                            if (((b)object).a.d) {
                                ((b)object).a.A();
                                break;
                            }
                            ((b)object).a.i();
                            break;
                        }
                        case 11: {
                            ((b)object).a.s();
                            break;
                        }
                        case 12: {
                            ((b)object).a.T();
                            break;
                        }
                        case 13: {
                            ((b)object).a.W();
                            break;
                        }
                        case 52: {
                            object2 = object;
                            if (src.k.a(65536)) {
                                ((b)object2).a.c();
                                object = object2;
                                ((b)object2).b = false;
                                break;
                            }
                            if (!src.k.a(131072)) break;
                            object = object2;
                            ((b)object2).a = null;
                            ((b)object2).a(3);
                            break;
                        }
                        case 54: {
                            object2 = object;
                            if (src.k.a(65536)) {
                                super.b("http://wap.ttsy.org/gamecms/go/jpgd?cpId=701057&contentID=000048675000");
                                break;
                            }
                            if (!src.k.a(131072)) break;
                            ((b)object2).a.c();
                            object = object2;
                            ((b)object2).b = false;
                            break;
                        }
                        case 55: {
                            object2 = object;
                            if (src.k.a(65536)) {
                                ((b)object2).a("http://wap.ttsy.org/gamecms/go/jpgd?cpId=701057&contentID=000048675000");
                                break;
                            }
                            if (!src.k.a(131072)) break;
                            object = object2;
                            ((b)object2).a = null;
                            ((b)object2).a(3);
                        }
                    }
                }
                object2 = object = this;
                if (((b)object).a && ((b)object2).a != 1) {
                    switch (((b)object2).k) {
                        case 0: {
                            switch (((b)object2).l) {
                                case 0: {
                                    int n3 = ((b)object2).a[0];
                                    n3 += 0x22000000;
                                    for (n2 = 0; n2 < ((b)object2).a.length; ++n2) {
                                        ((b)object2).a[n2] = n3;
                                    }
                                    if (++((b)object2).m != 7) break;
                                    ((b)object2).m = 0;
                                    ((b)object2).k = 2;
                                    break;
                                }
                                case 1: {
                                    int n4 = ((b)object2).a[0];
                                    n4 += 0x22FFFFFF;
                                    for (int i2 = 0; i2 < ((b)object2).a.length; ++i2) {
                                        ((b)object2).a[i2] = n4;
                                    }
                                    if (++((b)object2).m != 7) break;
                                    ((b)object2).m = 0;
                                    ((b)object2).k = 2;
                                    break;
                                }
                                case 2: {
                                    ((b)object2).m = 0;
                                    ((b)object2).k = 2;
                                    break;
                                }
                                case 3: {
                                    int n5;
                                    int n6 = ((b)object2).a[0];
                                    n6 += 0x22000000;
                                    for (n5 = 0; n5 < ((b)object2).a.length; ++n5) {
                                        ((b)object2).a[n5] = n6;
                                    }
                                    if (++((b)object2).m != 7) break;
                                    for (n5 = 0; n5 < ((b)object2).a.length; ++n5) {
                                        ((b)object2).a[n5] = -16777216;
                                    }
                                    ((b)object2).m = 0;
                                    ((b)object2).a = false;
                                }
                            }
                            break;
                        }
                        case 1: {
                            switch (((b)object2).l) {
                                case 0: {
                                    int n7 = ((b)object2).a[0];
                                    n7 -= 0x22000000;
                                    for (int i3 = 0; i3 < ((b)object2).a.length; ++i3) {
                                        ((b)object2).a[i3] = n7;
                                    }
                                    if (++((b)object2).m != 7) break;
                                    ((b)object2).a = false;
                                    break;
                                }
                                case 1: {
                                    int n8 = ((b)object2).a[0];
                                    n8 -= 0x22FFFFFF;
                                    for (int i4 = 0; i4 < ((b)object2).a.length; ++i4) {
                                        ((b)object2).a[i4] = n8;
                                    }
                                    if (++((b)object2).m != 7) break;
                                    ((b)object2).a = false;
                                    break;
                                }
                                case 2: {
                                    int n9 = ((b)object2).a[0];
                                    n9 -= 0x22000000;
                                    for (int i5 = 0; i5 < ((b)object2).a.length; ++i5) {
                                        ((b)object2).a[i5] = n9;
                                    }
                                    if (++((b)object2).m != 7) break;
                                    ((b)object2).a = false;
                                    break;
                                }
                                case 3: {
                                    int n10 = ((b)object2).a[0];
                                    n10 -= 0x22000000;
                                    for (int i6 = 0; i6 < ((b)object2).a.length; ++i6) {
                                        ((b)object2).a[i6] = n10;
                                    }
                                    if (++((b)object2).m != 7) break;
                                    ((b)object2).a = false;
                                }
                            }
                            break;
                        }
                        case 2: {
                            block37 : switch (((b)object2).n) {
                                case 4: {
                                    switch (((b)object2).o) {
                                        case 0: {
                                            ((b)object2).a(4, 3, (byte)((b)object2).l);
                                            break;
                                        }
                                        case 1: {
                                            ((b)object2).m = 0;
                                            ((b)object2).n = 0;
                                            ((b)object2).a.c((byte)1);
                                            ((b)object2).a.a((int)((b)object2).a.b);
                                            ((b)object2).a.l((byte)3);
                                            ((b)object2).a.b(((b)object2).a.e[((b)object2).a.c]);
                                            ((b)object2).a.f(((b)object2).a.b);
                                            ((b)object2).a.a(6, (short)((b)object2).a.b, (short)((b)object2).a.d[((b)object2).a.b]);
                                            ((b)object2).a(4);
                                            break;
                                        }
                                        case 2: {
                                            ((b)object2).a(4, 9, (byte)((b)object2).l);
                                            break;
                                        }
                                        case 3: {
                                            ((b)object2).a.e = 1;
                                            ((b)object2).a.b = (short)163;
                                            ((b)object2).a.c = 0;
                                            ((b)object2).a(4, 16, (byte)((b)object2).l);
                                            break;
                                        }
                                        case 4: {
                                            if (((b)object2).a.l > 0) {
                                                ((b)object2).a.f();
                                                ((b)object2).a.f((byte)0);
                                            }
                                            ((b)object2).a.b = (short)163;
                                            ((b)object2).a.c = 0;
                                            ((b)object2).a.b = (byte)(((b)object2).a.b - 163);
                                            ((b)object2).a.c = ((b)object2).a.a[((b)object2).a.b].length;
                                            ((b)object2).a(4, 15, (byte)((b)object2).l);
                                        }
                                    }
                                    break;
                                }
                                case 5: {
                                    switch (((b)object2).o) {
                                        case 0: {
                                            ((b)object2).a(5, 6, (byte)((b)object2).l);
                                            break;
                                        }
                                        case 1: {
                                            ((b)object2).a.a(((b)object2).a.b);
                                            ((b)object2).a.u();
                                            ((b)object2).a.a(((b)object2).a.a);
                                            super.m();
                                            ((b)object2).a.a(0, 0, 320, 240, ((b)object2).a.d, ((b)object2).a.e);
                                            ((b)object2).a.m();
                                            ((b)object2).a.P();
                                            ((b)object2).a.L();
                                            ((b)object2).a.N();
                                            ((b)object2).a.c();
                                            ((b)object2).a.r();
                                            ((b)object2).a.Q();
                                            super.s();
                                            ((b)object2).a.a(0, ((b)object2).a.a, (short)0);
                                            ((b)object2).a.b(((b)object2).a.a[0]);
                                            break;
                                        }
                                        case 2: {
                                            ((b)object2).a(5, 5, (byte)((b)object2).l);
                                            break;
                                        }
                                        case 3: {
                                            ((b)object2).a(5, 8, (byte)((b)object2).l);
                                            break;
                                        }
                                        case 4: {
                                            ((b)object2).a.i((byte)0);
                                            ((b)object2).a.e(((b)object2).a.h);
                                            ((b)object2).a.f((byte)(((b)object2).a.h + 1));
                                            ((b)object2).a.e();
                                            break;
                                        }
                                        case 5: {
                                            break;
                                        }
                                        case 6: {
                                            break;
                                        }
                                        case 7: {
                                            ((b)object2).a.e = (byte)4;
                                            ((b)object2).a.b = (short)12;
                                            ((b)object2).a.c = 0;
                                            ((b)object2).a(5, 13, (byte)((b)object2).l);
                                            break;
                                        }
                                        case 8: {
                                            ((b)object2).a(5, 12, (byte)((b)object2).l);
                                            break;
                                        }
                                        case 9: {
                                            ((b)object2).a.e = 1;
                                            ((b)object2).a.b = (short)119;
                                            ((b)object2).a.c = 0;
                                            ((b)object2).a(5, 12, (byte)((b)object2).l);
                                            break;
                                        }
                                        case 10: {
                                            ((b)object2).a.e = (byte)3;
                                            ((b)object2).a.b = (short)120;
                                            ((b)object2).a.c = 0;
                                            ((b)object2).a(5, 12, (byte)((b)object2).l);
                                            break;
                                        }
                                        case 11: {
                                            ((b)object2).a.e = 1;
                                            ((b)object2).a.b = (short)118;
                                            ((b)object2).a.c = 0;
                                            ((b)object2).a(5, 12, (byte)((b)object2).l);
                                            break;
                                        }
                                        case 12: {
                                            ((b)object2).a.e = (byte)4;
                                            ((b)object2).a.b = (short)144;
                                            ((b)object2).a.c = 0;
                                            ((b)object2).a(5, 13, (byte)((b)object2).l);
                                            break;
                                        }
                                        case 13: {
                                            ((b)object2).a.e = 1;
                                            ((b)object2).a.b = (short)120;
                                            ((b)object2).a.c = 0;
                                            ((b)object2).a(5, 14, (byte)((b)object2).l);
                                        }
                                    }
                                    ((b)object2).a.l((byte)3);
                                    break;
                                }
                                case 10: {
                                    ((b)object2).a.h();
                                    ((b)object2).a.a = ((b)object2).a.b = (short)168;
                                    switch (((b)object2).o) {
                                        case 0: {
                                            ((b)object2).a.a = 0;
                                            ((b)object2).a.b = 5;
                                            ((b)object2).a.a = false;
                                            ((b)object2).a(10, 4, (byte)((b)object2).l);
                                            break block37;
                                        }
                                        case 1: {
                                            ((b)object2).a(10, 2, (byte)((b)object2).l);
                                            break block37;
                                        }
                                        case 2: {
                                            ((b)object2).a(10, 1, (byte)((b)object2).l);
                                        }
                                    }
                                }
                            }
                            ((b)object2).k = 1;
                        }
                    }
                }
                switch (((b)object).a) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        object2 = object;
                        if (((b)object2).j < ((b)object2).a[((b)object2).k].length) {
                            n2 = ((b)object2).a[((b)object2).k][((b)object2).j];
                            Object object3 = object2;
                            switch (n2) {
                                case 50: {
                                    ((b)object3).a.af();
                                    break;
                                }
                                case 49: {
                                    ((b)object3).a.k();
                                    break;
                                }
                                case 48: {
                                    ((b)object3).a.L();
                                    break;
                                }
                                case 47: {
                                    ((b)object3).a.K();
                                    break;
                                }
                                case 46: {
                                    ((b)object3).a.ad();
                                    break;
                                }
                                case 45: {
                                    ((b)object3).a.ac();
                                    break;
                                }
                                case 44: {
                                    int n11;
                                    object = object3;
                                    src.m.a = new byte[10000];
                                    src.m.a[0] = 0;
                                    src.m.a(src.m.a, "/data/face.data", 0);
                                    ((b)object).d = src.m.a(src.m.a, src.m.a);
                                    ((b)object).e = src.m.a(src.m.a, src.m.a);
                                    ((b)object).f = src.m.a(src.m.a, src.m.a);
                                    src.m.a = null;
                                    ((b)object).d = new Image[1];
                                    src.m.a = new byte[10000];
                                    src.m.a[0] = 0;
                                    src.m.a(src.m.a, "/data/skillicon.data", 0);
                                    ((b)object).e = src.m.a(src.m.a, src.m.a);
                                    ((b)object).g = src.m.a(src.m.a, src.m.a);
                                    ((b)object).h = src.m.a(src.m.a, src.m.a);
                                    src.m.a = null;
                                    src.m.a = new byte[10000];
                                    src.m.a[0] = 0;
                                    src.m.a(src.m.a, "/data/itemicon.data", 0);
                                    ((b)object).f = src.m.a(src.m.a, src.m.a);
                                    src.m.a = null;
                                    src.m.a = new byte[10000];
                                    src.m.a[0] = 0;
                                    src.m.a(src.m.a, "/data/dropitemaction.data", 0);
                                    ((b)object).g = src.m.a(src.m.a, src.m.a);
                                    ((b)object).i = src.m.a(src.m.a, src.m.a);
                                    ((b)object).j = src.m.a(src.m.a, src.m.a);
                                    ((b)object).h = src.m.a(src.m.a, src.m.a);
                                    src.m.a = null;
                                    ((b)object).d = new Image[1];
                                    ((b)object).d[0] = src.m.a("/ui/face.png");
                                    ((b)object).e = new Image[2];
                                    for (n11 = 0; n11 < ((b)object).e.length; ++n11) {
                                        ((b)object).e[n11] = src.m.a("/ui/skillicon" + n11 + ".png");
                                    }
                                    ((b)object).f = new Image[((b)object).e.length];
                                    for (n11 = 0; n11 < ((b)object).f.length; ++n11) {
                                        ((b)object).f[n11] = src.m.a("/ui/itemicon" + n11 + ".png");
                                    }
                                    ((b)object).g = new Image[1];
                                    ((b)object).g[0] = src.m.a("/ui/dropitem.png");
                                    ((b)object).h = new Image[1];
                                    ((b)object).h[0] = src.m.a("/ui/defaulttype.png");
                                    break;
                                }
                                case 43: {
                                    e e2 = ((b)object3).a.a[0];
                                    object = ((b)object3).a;
                                    ((b)object3).a.a = e2;
                                    ((b)object3).a.c();
                                    ((b)object3).a.v();
                                    ((b)object3).a.r();
                                    ((b)object3).a.Q();
                                    break;
                                }
                                case 41: {
                                    ((b)object3).a.aa();
                                    break;
                                }
                                case 40: {
                                    ((b)object3).a.ae();
                                    break;
                                }
                                case 39: {
                                    ((b)object3).a.ab();
                                    break;
                                }
                                case 38: {
                                    ((b)object3).a.a();
                                    break;
                                }
                                case 36: {
                                    ((b)object3).a.L();
                                    break;
                                }
                                case 0: {
                                    break;
                                }
                                case 1: {
                                    ((b)object3).a = new u((b)object3);
                                    break;
                                }
                                case 3: {
                                    ((b)object3).a = new o((b)object3);
                                    break;
                                }
                                case 4: {
                                    ((b)object3).a = null;
                                    break;
                                }
                                case 5: {
                                    ((b)object3).a = new i((b)object3);
                                    ((b)object3).a.d();
                                    break;
                                }
                                case 6: {
                                    ((b)object3).a = null;
                                    break;
                                }
                                case 7: {
                                    ((b)object3).a = new c((b)object3);
                                    ((b)object3).a.g();
                                    break;
                                }
                                case 8: {
                                    ((b)object3).a = new v((b)object3);
                                    break;
                                }
                                case 9: {
                                    ((b)object3).a = null;
                                    break;
                                }
                                case 10: {
                                    ((b)object3).a.a(((b)object3).a.b);
                                    ((b)object3).a.u();
                                    super.m();
                                    ((b)object3).a.a(0, 0, 320, 240, ((b)object3).a.d, ((b)object3).a.e);
                                    ((b)object3).a.q();
                                    ((b)object3).a.r();
                                    ((b)object3).a.b(((b)object3).a.a);
                                    ((b)object3).a.a(((b)object3).a.a);
                                    ((b)object3).a.l((byte)3);
                                    ((b)object3).a.a(0, ((b)object3).a.a, (short)0);
                                    break;
                                }
                                case 11: {
                                    ((b)object3).a.C();
                                    ((b)object3).a.D();
                                    ((b)object3).a.M();
                                    ((b)object3).a.C();
                                    break;
                                }
                                case 12: {
                                    ((b)object3).a.l();
                                    break;
                                }
                                case 13: {
                                    ((b)object3).a.c();
                                    ((b)object3).a.i();
                                    break;
                                }
                                case 14: {
                                    ((b)object3).a.q();
                                    break;
                                }
                                case 15: {
                                    ((b)object3).a.n();
                                    break;
                                }
                                case 16: {
                                    ((b)object3).a.E();
                                    break;
                                }
                                case 18: {
                                    ((b)object3).a.a(0, 0, 320, 240, 640, 360);
                                    e e3 = ((b)object3).a.a[0];
                                    object = ((b)object3).a;
                                    ((b)object3).a.a = e3;
                                    ((b)object3).a.c();
                                    break;
                                }
                                case 19: {
                                    ((b)object3).a.I();
                                    break;
                                }
                                case 20: {
                                    ((b)object3).a.P();
                                    ((b)object3).a.L();
                                    break;
                                }
                                case 21: {
                                    ((b)object3).a.N();
                                    e e4 = ((b)object3).a.a[0];
                                    object = ((b)object3).a;
                                    ((b)object3).a.a = e4;
                                    ((b)object3).a.c();
                                    ((b)object3).a.v();
                                    ((b)object3).a.r();
                                    ((b)object3).a.Q();
                                    super.s();
                                    break;
                                }
                                case 22: {
                                    if (((b)object3).a.b(((b)object3).a.a)) {
                                        ((b)object3).l = (byte)6;
                                        break;
                                    }
                                    ((b)object3).l = (byte)((b)object3).a.c[((b)object3).a.b[((b)object3).a.a]][6];
                                    break;
                                }
                                case 53: {
                                    ((b)object3).l = 1;
                                    break;
                                }
                                case 51: {
                                    ((b)object3).l = 0;
                                    break;
                                }
                                case 52: {
                                    ((b)object3).l = 1;
                                    break;
                                }
                                case 23: {
                                    break;
                                }
                                case 24: {
                                    ((b)object3).a.c();
                                    ((b)object3).a.u();
                                    ((b)object3).a.k();
                                    break;
                                }
                                case 25: {
                                    ((b)object3).a.a = ((b)object3).a.b;
                                    ((b)object3).a.c = ((b)object3).a.a[((b)object3).a.b].length;
                                    ((b)object3).a.d(((b)object3).a.b);
                                    ((b)object3).a.u();
                                    ((b)object3).a.n();
                                    ((b)object3).a.l((byte)3);
                                    ((b)object3).a.a(0, ((b)object3).a.a, (short)0);
                                    ((b)object3).a.k();
                                    break;
                                }
                                case 26: {
                                    ((b)object3).a.e();
                                    ((b)object3).a.l();
                                    break;
                                }
                                case 27: {
                                    ((b)object3).a.m();
                                    ((b)object3).a.r();
                                    ((b)object3).a.l();
                                    break;
                                }
                                case 28: {
                                    ((b)object3).a.b();
                                    ((b)object3).a.o();
                                    break;
                                }
                                case 29: {
                                    break;
                                }
                                case 30: {
                                    ((b)object3).a.b();
                                    break;
                                }
                                case 31: {
                                    ((b)object3).a = null;
                                    ((b)object3).a = null;
                                    ((b)object3).a = null;
                                    break;
                                }
                                case 32: {
                                    ((b)object3).a.d();
                                    break;
                                }
                                case 33: {
                                    ((b)object3).a.J();
                                    break;
                                }
                                case 34: {
                                    ((b)object3).a.K();
                                    ((b)object3).a.E();
                                    break;
                                }
                                case 35: {
                                    break;
                                }
                                case 37: {
                                    object = object3;
                                    src.m.a = new byte[10000];
                                    src.m.a[0] = 0;
                                    src.m.a(src.m.a, "/data/popnum.data", 0);
                                    ((b)object).a = src.m.a(src.m.a, src.m.a);
                                    src.m.a = null;
                                    src.m.a = new byte[10000];
                                    src.m.a[0] = 0;
                                    src.m.a(src.m.a, "/data/batternum.data", 0);
                                    ((b)object).b = src.m.a(src.m.a, src.m.a);
                                    ((b)object).a = src.m.a(src.m.a, src.m.a);
                                    ((b)object).b = src.m.a(src.m.a, src.m.a);
                                    src.m.a = null;
                                    src.m.a = new byte[10000];
                                    src.m.a[0] = 0;
                                    src.m.a(src.m.a, "/data/debuff.data", 0);
                                    ((b)object).c = src.m.a(src.m.a, src.m.a);
                                    ((b)object).c = src.m.a(src.m.a, src.m.a);
                                    ((b)object).d = src.m.a(src.m.a, src.m.a);
                                    src.m.a = null;
                                    ((b)object).a = new Image[1];
                                    ((b)object).a[0] = src.m.a("/ui/popnum.png");
                                    ((b)object).b = new Image[2];
                                    ((b)object).b[0] = src.m.a("/ui/batternum.png");
                                    ((b)object).b[1] = src.m.a("/ui/batternum1.png");
                                    ((b)object).c = new Image[1];
                                    ((b)object).c[0] = src.m.a("/ui/debuff.png");
                                }
                            }
                            ((b)object2).j = (byte)(((b)object2).j + 1);
                            break;
                        }
                        if (((b)object2).l >= 0) {
                            super.c(((b)object2).l);
                            ((b)object2).a.b();
                        }
                        switch (((b)object2).p) {
                            case 0: {
                                break;
                            }
                            case 1: {
                                ((b)object2).m = 0;
                                ((b)object2).n = 0;
                            }
                        }
                        ((b)object2).a((int)((b)object2).h);
                        switch (((b)object2).h) {
                            case 5: 
                            case 11: {
                                ((b)object2).a.b(((b)object2).a.a[0]);
                                ((b)object2).a.I();
                                break;
                            }
                            case 4: {
                                ((b)object2).a.b(((b)object2).a.a[0]);
                                break;
                            }
                            case 10: {
                                ((b)object2).a.b = true;
                                ((b)object2).a.b(((b)object2).a.a[0]);
                            }
                        }
                        System.gc();
                        break;
                    }
                    case 3: {
                        break;
                    }
                    case 4: {
                        ((b)object).a.c(((b)object).a.a[0]);
                        ((b)object).a.p();
                        ((b)object).a.O();
                        break;
                    }
                    case 5: {
                        object2 = object;
                        if (((b)object2).a) {
                            if (((b)object2).a.d || ((b)object2).a.f) {
                                super.q();
                                ((b)object2).a.A();
                                ((b)object2).a.d();
                            } else {
                                ((b)object2).a.z();
                                ((b)object2).a.s();
                                ((b)object2).a.H();
                                ((b)object2).a.b();
                            }
                        } else {
                            ((b)object2).a.c(((b)object2).a.a[0]);
                            if (((b)object2).a.d || ((b)object2).a.f) {
                                super.q();
                                ((b)object2).a.A();
                                ((b)object2).a.d();
                            } else {
                                ((b)object2).a.z();
                                ((b)object2).a.s();
                                ((b)object2).a.H();
                                ((b)object2).a.b();
                            }
                        }
                        if (((b)object).c == 0) {
                            super.o();
                            ((b)object).a.w();
                        }
                        ((b)object).a.O();
                        break;
                    }
                    case 6: {
                        switch (((b)object).b) {
                            case 4: {
                                break;
                            }
                            case 5: {
                                if (((b)object).c == 0) {
                                    super.o();
                                }
                                ((b)object).a.b();
                                ((b)object).a.w();
                            }
                        }
                        ((b)object).a.S();
                        ((b)object).a.O();
                        break;
                    }
                    case 7: {
                        ((b)object).a.c();
                        ((b)object).a.O();
                        break;
                    }
                    case 8: {
                        break;
                    }
                    case 9: {
                        object2 = object;
                        ((b)object).c = 1;
                        if (((b)object2).q < 5) {
                            ((b)object2).q = (byte)(((b)object2).q + 1);
                            break;
                        }
                        ((b)object2).c = 0;
                        Object object4 = object2;
                        ((b)object2).e = true;
                        ((b)object4).x = 0;
                        ((b)object2).a(5);
                        break;
                    }
                    case 10: {
                        ((b)object).a.c(((b)object).a.a[0]);
                        ((b)object).a.j();
                        break;
                    }
                    case 11: {
                        ((b)object).a.t();
                        break;
                    }
                    case 12: {
                        break;
                    }
                    case 13: {
                        ((b)object).a.X();
                    }
                }
                if (this.d && this.a != 1) {
                    this.d = false;
                    this.i = this.a;
                    this.a = (byte)8;
                    this.a.c();
                    src.k.d();
                }
                this.a = ++this.a > 99999 ? 0 : this.a;
                this.repaint();
                this.serviceRepaints();
                this.a = 0;
                if (this.e && this.a == 5) {
                    this.a = (short)200;
                }
                this.b = System.currentTimeMillis() - this.a;
                if (this.b >= (long)(80 + this.a)) continue;
                Thread.sleep((long)(80 + this.a) - this.b);
            }
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
        }
        object = this;
        ((b)object).a.c();
        ((b)object).a.destroyApp(true);
    }

    public final void a(int n2) {
        this.b = this.a;
        this.a = (byte)n2;
        if (this.a == 7) {
            this.a.m();
        }
    }

    public final void a(int n2, int n3, byte by) {
        this.b = this.a;
        this.h = (byte)n2;
        this.k = (byte)n3;
        this.a = 1;
        this.p = by;
        if (this.p == 0 || this.p == 2) {
            String string = this.b[src.m.a(this.b.length)];
            b b2 = this;
            this.c = null;
            if (b2.a != null) {
                b2.c = b2.a(string, 210, 0);
            }
        }
        this.j = 0;
    }

    private void a(String object, int n2) {
        this.i = 12;
        this.j = 24;
        try {
            object = new DataInputStream(new Object().getClass().getResourceAsStream((String)object));
            this.c = new int[((DataInputStream)object).readInt()];
            for (n2 = 0; n2 < this.c.length; ++n2) {
                this.c[n2] = ((DataInputStream)object).readInt();
            }
            this.d = new int[((DataInputStream)object).readInt()];
            for (n2 = 0; n2 < this.d.length; ++n2) {
                this.d[n2] = ((DataInputStream)object).readInt();
            }
            ((FilterInputStream)object).close();
            return;
        }
        catch (IOException iOException) {
            object = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public final void a(int n2, byte by, int n3) {
        this.l = n2;
        this.n = by;
        this.o = n3;
        this.m = 0;
        this.k = 0;
        this.m = 0;
        this.n = 0;
        if (this.c == 1) {
            this.a.D();
        }
        switch (this.l) {
            case 0: 
            case 3: {
                for (n2 = 0; n2 < this.a.length; ++n2) {
                    this.a[n2] = 0;
                }
                break;
            }
            case 1: {
                for (n2 = 0; n2 < this.a.length; ++n2) {
                    this.a[n2] = 0xFFFFFF;
                }
                break;
            }
            case 2: {
                for (n2 = 0; n2 < this.a.length; ++n2) {
                    this.a[n2] = -16777216;
                }
                break;
            }
        }
        this.a = true;
    }

    public final void b() {
        this.c = true;
        this.p = 0;
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            this.a[i2] = -1;
        }
    }

    public final void a(byte by) {
        if (by != this.o) {
            this.i[0] = null;
            this.o = by;
            this.i[0] = src.m.a("/ui/" + this.b[this.o] + ".png");
        }
    }

    protected final void hideNotify() {
        src.k.c();
        if (this.a != 8) {
            this.d = true;
        }
    }

    private void m() {
        if (this.a.a[this.a.c[this.a.a]] == -1) {
            this.a = null;
            return;
        }
        this.a = new n[this.a.a[this.a.c[this.a.a]]];
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            this.a[i2] = new n(this);
            this.a[i2].a = this.a.a[this.a.c[this.a.a]][src.m.a(this.a.a[this.a.c[this.a.a]].length)];
        }
    }

    private void n() {
        if (this.a != null) {
            for (int i2 = 0; i2 < this.a.length; ++i2) {
                n n2 = this.a[i2];
                if (n2.a == -1) continue;
                n2.a.a.drawRegion(n2.a.a.a[n2.a[n2.a][n2.b]], (int)n2.a[n2.a][n2.b + 1], (int)n2.a[n2.a][n2.b + 2], (int)n2.a[n2.a][n2.b + 3], (int)n2.a[n2.a][n2.b + 4], (int)n2.a, n2.a, n2.b, 20);
                if (n2.a.c != 0 || (n2.c = (short)(n2.c + 1)) < 2) continue;
                n2.c = 0;
                n2.b = (short)(n2.b + 5);
                if (n2.b < n2.a[n2.a].length) continue;
                n2.b = 0;
            }
        }
    }

    private void o() {
        if (this.a != null) {
            block8: for (int i2 = 0; i2 < this.a.length; ++i2) {
                n n2 = this.a[i2];
                if (n2.a) {
                    n2.a = n2.a[src.m.a(n2.a.length)];
                    switch (n2.a) {
                        case 0: {
                            n2.a = src.m.a(n2.a.a.e, n2.a.a.e + 100);
                            n2.b = src.m.a(n2.b[n2.a][0], n2.b[n2.a][1]);
                            break;
                        }
                        case 2: {
                            n2.a = src.m.a(-100, 0);
                            n2.b = src.m.a(n2.b[n2.a][0], n2.b[n2.a][1]);
                        }
                    }
                    n2.a = false;
                }
                if (n2.a) continue;
                switch (n2.a) {
                    case 0: {
                        if (--n2.a >= -100) continue block8;
                        n2.a = true;
                        continue block8;
                    }
                    case 2: {
                        if (++n2.a <= n2.a.a.e + 100) continue block8;
                        n2.a = true;
                    }
                }
            }
        }
    }

    public final void c() {
        x x2;
        if (this.d > 0) {
            this.a.translate(0, -this.b << 1);
        }
        if (this.e) {
            x2 = this;
            x2.x = (byte)(x2.x + 1);
            if (x2.x == 10) {
                x2.e = false;
            }
            if (x2.x % 2 == 1) {
                x2.a.setColor(0);
                x2.a.fillRect(0, 0, 320, 240);
            } else {
                x2.a.setColor(0xFFFFFF);
                x2.a.fillRect(0, 0, 320, 240);
            }
            u u2 = this.a;
            x2 = u2;
            x2 = this.a;
            this.a.translate(-u2.a, -((u)x2).b);
            this.a.x();
            this.a.a(1, this.a.a[0]);
            this.a.v();
            this.a.a(2, this.a.a[0]);
            this.a.f();
            u u3 = this.a;
            x2 = u3;
            x2 = this.a;
            this.a.translate(u3.a, ((u)x2).b);
        } else {
            this.a.a();
            x2 = this.a;
            this.q = ((u)x2).a;
            x2 = this.a;
            this.r = ((u)x2).b;
            this.a.a = this.a.a * this.s / 3;
            this.a.b = this.a.b * this.s / 3;
            u u4 = this.a;
            x2 = u4;
            x2 = this.a;
            this.a.translate(-u4.a, -((u)x2).b);
            this.a.c(0);
            u u5 = this.a;
            x2 = u5;
            x2 = this.a;
            this.a.translate(u5.a, ((u)x2).b);
            this.a.a = this.q;
            this.a.b = this.r;
            this.a.a = this.a.a * this.t / 3;
            this.a.b = this.a.b * this.t / 3;
            u u6 = this.a;
            x2 = u6;
            x2 = this.a;
            this.a.translate(-u6.a, -((u)x2).b);
            this.a.c(1);
            this.n();
            u u7 = this.a;
            x2 = u7;
            x2 = this.a;
            this.a.translate(u7.a, ((u)x2).b);
            this.a.a = this.q;
            this.a.b = this.r;
            u u8 = this.a;
            x2 = u8;
            x2 = this.a;
            this.a.translate(-u8.a, -((u)x2).b);
            this.a.a(0, this.a.a[0]);
            this.a.x();
            this.a.c(2);
            if (this.a.a(this.a.a)) {
                this.a.u();
                this.a.c(3);
            }
            this.a.M();
            this.a.a(1, this.a.a[0]);
            this.a.v();
            this.a.a(this.a.a[0]);
            this.a.k(this.a.c);
            this.a.f();
            this.a.a(2, this.a.a[0]);
            u u9 = this.a;
            x2 = u9;
            x2 = this.a;
            this.a.translate(u9.a, ((u)x2).b);
        }
        if (this.d > 0) {
            this.a.translate(0, this.b << 1);
        }
        if (!this.a.d) {
            this.a.R();
            x2 = this;
            if (x2.c) {
                int n2;
                for (n2 = 0; n2 < 240; ++n2) {
                    x2.a.drawRGB(x2.a, 0, 320, 0, n2, 320, 1, true);
                }
                int n3 = x2.a[0];
                n3 -= 0x11000000;
                for (n2 = 0; n2 < x2.a.length; ++n2) {
                    x2.a[n2] = n3;
                }
                if (++x2.p == 15) {
                    x2.c = false;
                }
            }
        }
    }

    private void p() {
        if (this.d > 0) {
            this.b(0, 0, 0, 320, this.b);
            this.b(0, 0, 240 - (this.b << 1), 320, this.b * 3);
            if (this.d == 3) {
                if (this.a % 10 == 0) {
                    this.a.drawRegion(this.a.a, 0, 1, 7, 11, 0, 280, 10, 10);
                }
                this.a.drawRegion(this.a.a, 10, 0, 25, 13, 0, 310, 10, 10);
            }
        }
    }

    private void q() {
        switch (this.d) {
            case 0: {
                return;
            }
            case 1: {
                this.b += 3;
                this.b = this.b > 20 ? 20 : this.b;
                return;
            }
            case 2: {
                this.b -= 5;
                this.b = this.b < 0 ? 0 : this.b;
            }
        }
    }

    public final void d() {
        this.q = 0;
        this.a(9);
    }

    public final void e() {
        switch (this.a) {
            case 7: {
                this.a.b((short)0);
                break;
            }
            case 3: {
                this.a.a((short)7);
                this.a.j();
            }
        }
        this.a.a((short)6);
        this.a.a(153, src.v.a[0][0] + -7, 26, 36, 0);
        this.a.a((short)24);
        this.a.a((short)16, 0, 240);
        this.b(0xFFFFFF, 129, 85, 132 * this.a.a / 4, 13);
    }

    public final void f() {
        if (src.k.a(16400) || src.k.a(16400, 10)) {
            this.a.a = --this.a.a < 0 ? 0 : this.a.a;
            this.c(this.v);
            this.a.a();
            this.a.b();
            return;
        }
        if (src.k.a(32832) || src.k.a(32832, 10)) {
            this.a.a = ++this.a.a > 4 ? 4 : this.a.a;
            this.c(this.v);
            this.a.a();
            this.a.b();
            return;
        }
        if (src.k.a(65536)) {
            if (this.a == 7) {
                this.a.a((byte)0);
                return;
            }
            if (this.a == 3) {
                this.a.a((byte)1);
                return;
            }
        } else if (src.k.a(131072)) {
            if (this.a == 7) {
                this.a.a((byte)0);
                return;
            }
            if (this.a == 3) {
                this.a.a((byte)1);
            }
        }
    }

    private void r() {
        int n2;
        int n3 = 0;
        for (n2 = 0; n2 < this.a.a[this.c[this.t]].length; n2 = (int)((byte)(n2 + 2))) {
            if (this.a.a[this.c[this.t]][n2] < 0) continue;
            n3 = (byte)(n3 + 1);
        }
        byte[] byArray = new byte[n3];
        for (n2 = 0; n2 < n3; n2 = (int)((byte)(n2 + 1))) {
            byArray[n2] = this.a.a[this.c[this.t]][n2 << 1];
        }
        this.d = byArray;
        this.w = (byte)n3;
        this.u = 0;
        this.v = 0;
        this.a.a.a(this.e[this.t], 116, 52, 140, 30, 1, 20);
    }

    public final void g() {
        switch (this.a) {
            case 7: {
                this.a.b((short)0);
                break;
            }
            case 3: {
                this.a.a((short)7);
                this.a.j();
            }
        }
        this.a.a((short)6);
        this.a.a(121, src.v.a[0][0] + -4, 26, 36, 0);
        switch (this.e) {
            case 0: {
                this.a.a((short)16, 0, 240);
                for (int i2 = 0; i2 < 2; ++i2) {
                    if (this.f == i2) {
                        this.a.a((short)38, 160, 100 + i2 * 37);
                        this.a(this.f[i2], 160, 107 + i2 * 37, 17, 15597319, 0);
                        continue;
                    }
                    this.a.a((short)37, 160, 100 + i2 * 37);
                    this.a(this.f[i2], 160, 107 + i2 * 37, 17, 10333123, 0);
                }
                break;
            }
            case 1: {
                this.a.a((short)28, 0, 240);
                this.a(this.d, 30, 50, 20, (int)this.r, 8, 0);
                if (this.u <= 8) break;
                this.a.a((short)45, 120, 120);
                this.a.b(7, 53, 8, 142, this.r, this.u, (byte)8);
                return;
            }
            case 2: {
                this.a.a((short)5);
                this.a.a((short)28, 0, 240);
                this.a.a(18, 60, 59, 24, 0);
                this.a.a(19, 100, 59, 20, 0);
                this.a.a("\u51fa\u73b0\u5730\u56fe", 117, 35, 4, 0);
                this.a.c(this.a.c[this.a.d[this.c[this.t]][17]], 265, 37, 24, 0);
                this.a.a(this.t + 1, (int)this.s);
                this.a.drawImage(this.a.b[this.a.b[this.c[this.t]]], 80, 82, 33);
                this.a(this.a.a[this.c[this.t]], 80, 37, 17, 15597319, 0);
                this.a.a.a();
                for (int i3 = 0; i3 < 3; ++i3) {
                    if (i3 >= this.w) continue;
                    if (i3 == this.v) {
                        this.a.a((short)66, 64, 103 + i3 * 42 + -8);
                    } else {
                        this.a.a((short)67, 65, 104 + i3 * 42 + -8);
                    }
                    this.a.b((int)this.d[this.u + i3], 66, 103 + i3 * 42 + -8);
                    if (i3 == this.v) {
                        this.a.c(65, 102 + i3 * 42 + -8, (int)this.d[this.u + i3]);
                        this.a(this.a.a[this.d[this.u + i3]][0], 185, 117 + i3 * 42 + -8, 17, 15597319, 0);
                        continue;
                    }
                    this.a.d(65, 102 + i3 * 42 + -8, this.d[this.u + i3]);
                    this.a(this.a.a[this.d[this.u + i3]][0], 185, 117 + i3 * 42 + -8, 17, 10333123, 0);
                }
                this.a.a(47, 108, 8, 94, this.u + this.v, this.w, (byte)1);
            }
        }
    }

    public final void h() {
        block0 : switch (this.e) {
            case 0: {
                if (src.k.a(4100) || src.k.a(4100, 10)) {
                    this.f = (byte)(this.f - 1);
                    this.f = this.f < 0 ? (byte)1 : this.f;
                    return;
                }
                if (src.k.a(8448) || src.k.a(8448, 10)) {
                    this.f = (byte)(this.f + 1);
                    this.f = this.f > 1 ? (byte)0 : this.f;
                    return;
                }
                if (src.k.a(327712)) {
                    switch (this.f) {
                        case 0: {
                            b b2;
                            b b3 = b2 = this;
                            String[] stringArray = new String[]{"\u6e38\u620f\u64cd\u4f5c", "\u6389\u843d\u5217\u8868", "\u654c\u4eba\u5206\u5e03"};
                            String string = " \u7cfb\u7edf\u548c\u57ce\u5e02\uff1a| 2\u30014\u30016\u30018 \u952e\u79fb\u52a8| 5 \u952e\u786e\u5b9a| \u5de6\u8f6f\u952e\u6253\u5f00\u7cfb\u7edf\u83dc\u5355\u3001\u6253\u5f00\u9009\u9879| \u53f3\u8f6f\u952e\u6253\u5f00\u6e38\u620f\u83dc\u5355\u3001\u8fd4\u56de| | \u6218\u6597\uff1a| 2\u30014\u30016\u30018 \u952e\u79fb\u52a8| \u53cc\u51fb 4\u30016 \u952e\u51b2\u523a| 5 \u952e\u653b\u51fb| 1 \u952e\u5207\u6362\u6b66\u5668| 3 \u952e\u4f7f\u7528\u67aa\u68b0| 7\u30019 \u952e\u53d1\u52a8\u6280\u80fd| * \u952e\u67e5\u770b\u4efb\u52a1\u5217\u8868| # \u952e\u4e0a\u4e0b\u5750\u9a91| 0 \u952e\u67e5\u770b\u5730\u56fe|";
                            b3.d = b3.a(string, 200, 0);
                            b2.r = 0;
                            b2.u = b2.d.length;
                            this.e = 1;
                            break block0;
                        }
                        case 1: {
                            this.e = (byte)2;
                            this.t = 0;
                            this.r();
                        }
                    }
                    return;
                }
                if (!src.k.a(131072)) break;
                h h2 = this.a.a;
                this.a.a.a = null;
                switch (this.a) {
                    case 3: {
                        this.a.a(this.a.a);
                        break block0;
                    }
                    case 7: {
                        if (this.b == 10) {
                            this.a((int)this.b);
                            break block0;
                        }
                        this.a.a((byte)0);
                    }
                }
                return;
            }
            case 1: {
                if (src.k.a(4100) || src.k.a(4100, 10)) {
                    this.r = (byte)(this.r - 1);
                    this.r = this.r < 0 ? (byte)0 : this.r;
                    return;
                }
                if (src.k.a(8448) || src.k.a(8448, 10)) {
                    byte by = (byte)(this.d.length - 8);
                    by = by < 0 ? (byte)0 : by;
                    this.r = (byte)(this.r + 1);
                    this.r = this.r > by ? by : this.r;
                    return;
                }
                if (!src.k.a(131072)) break;
                this.e = 0;
                this.d = null;
                return;
            }
            case 2: {
                if (src.k.a(16400) || src.k.a(16400, 10)) {
                    this.t = (byte)(this.t - 1);
                    this.t = (byte)(this.t < 0 ? this.c.length - 1 : this.t);
                    this.r();
                    return;
                }
                if (src.k.a(32832) || src.k.a(32832, 10)) {
                    this.t = (byte)(this.t + 1);
                    this.t = this.t > this.c.length - 1 ? (byte)0 : this.t;
                    this.r();
                    return;
                }
                if (src.k.a(4100) || src.k.a(4100, 10)) {
                    if (this.v > 0) {
                        this.v = (byte)(this.v - 1);
                        return;
                    }
                    if (this.u <= 0) break;
                    this.u = (byte)(this.u - 1);
                    return;
                }
                if (src.k.a(8448) || src.k.a(8448, 10)) {
                    int n2;
                    int n3 = n2 = this.w < 3 ? (int)this.w : 3;
                    if (this.v < n2 - 1) {
                        this.v = (byte)(this.v + 1);
                        break;
                    }
                    if (this.u < this.w - n2) {
                        this.u = (byte)(this.u + 1);
                    }
                    return;
                }
                if (!src.k.a(131072)) break;
                this.e = 0;
            }
        }
    }

    public final void i() {
        b b2 = this;
        String string = "\u5317\u4eac\u534e\u5a31\u65e0\u7ebf\u79d1\u6280\u6709\u9650\u516c\u53f8\u4e3a\u300a\u661f\u9645\u4f20\u8bf4-\u547d\u8fd0\u8f6e\u56de(\u534e\u5a31)\u300b\u6e38\u620f\u7684\u8f6f\u4ef6\u8457\u4f5c\u6743\u4eba\u3002\u5317\u4eac\u534e\u5a31\u65e0\u7ebf\u79d1\u6280\u6709\u9650\u516c\u53f8\u6388\u6743\u65b0\u6d6a\u65e0\u7ebf\u5728\u4e2d\u56fd\u5927\u9646\u4ece\u4e8b\u672c\u6e38\u620f\u7684\u5546\u4e1a\u8fd0\u8425\u3002\u65b0\u6d6a\u65e0\u7ebf\u540c\u65f6\u8d1f\u8d23\u5904\u7406\u672c\u6e38\u620f\u8fd0\u8425\u7684\u76f8\u5173\u5ba2\u6237\u670d\u52a1\u53ca\u6280\u672f\u652f\u6301\u3002|\u5ba2\u670d\u7535\u8bdd\uff1a4006900000|\u5ba2\u670dEMAIL\uff1a|bjkf@staff.sina.com.cn|\u5f00\u53d1\u5546\uff1a\u534e\u5a31\u65e0\u7ebf";
        b2.a = b2.a(string, 200, 0);
        this.g = 0;
        this.e = this.a.length;
    }

    protected final void keyPressed(int n2) {
        src.k.a(n2);
    }

    protected final void keyReleased(int n2) {
        src.k.b(n2);
    }

    public final byte a(int n2, int n3) {
        return this.b[n2][n3];
    }

    public final void a(Image image, int n2, int n3, int n4, int n5, int n6, int n7, int n8, short[] sArray, int n9) {
        if (n8 == -1) {
            n5 = this.a(n5, 2);
            this.f = n6 - n2 - src.b.a(n5, sArray[n9 * 5 + 3], sArray[n9 * 5 + 4]);
            this.g = n7 + n3;
            this.a(image, this.f, this.g, 20, n5, sArray, n9);
            return;
        }
        this.f = n6 + n2;
        this.g = n7 + n3;
        this.a(image, this.f, this.g, 20, n5, sArray, n9);
    }

    public final void a(Image image, short[] sArray, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        this.f = n7;
        this.g = 80;
        this.a(image, this.f, this.g, 10, 0, sArray, n2);
    }

    public final void a(Image image, int n2, int n3, int n4, int n5, int n6, int n7, short[] sArray, int n8) {
        this.f = n6 + n2;
        this.g = n7 + n3;
        this.a(image, this.f, this.g, n4, n5, sArray, n8);
    }

    public final void a(Image[] imageArray, short[] sArray, short[][] sArray2, int n2, int n3, int n4, int n5) {
        if (n2 >= sArray2.length) {
            return;
        }
        for (n5 = 0; n5 < sArray2[n2].length; n5 += 4) {
            short s2 = sArray2[n2][n5];
            this.a(imageArray[sArray[s2 * 5]], sArray2[n2][n5 + 1], (int)sArray2[n2][n5 + 2], 20, (int)n[sArray2[n2][n5 + 3]], n3, n4, sArray, (int)s2);
        }
    }

    public final void a(Image[] imageArray, short[] sArray, int n2, int n3, int n4, int n5, int n6) {
        this.a(imageArray[sArray[n2 * 5]], n3, n4, n5, n6, sArray, n2);
    }

    public static short a(int n2, short s2, short s3) {
        switch (n2) {
            case 4: 
            case 5: 
            case 6: 
            case 7: {
                return s3;
            }
        }
        return s2;
    }

    public static short b(int n2, short s2, short s3) {
        switch (n2) {
            case 4: 
            case 5: 
            case 6: 
            case 7: {
                return s2;
            }
        }
        return s3;
    }

    public final void a(int n2, int n3, int n4, int n5, int n6) {
        this.a.setColor(n2);
        this.a.drawRect(n3, n4, n5, n6);
    }

    public final void a(Image image, int n2, int n3, int n4, int n5) {
        if (n5 == 0) {
            this.a.drawImage(image, n2, n3, n4);
            return;
        }
        this.a.drawRegion(image, 0, 0, image.getWidth(), image.getHeight(), n5, n2, n3, n4);
    }

    public final void a(Image[] imageArray, int n2, int n3, int n4, int n5, short s2, short s3, short s4, short s5, short s6) {
        this.a.drawRegion(imageArray[s2], (int)s3, (int)s4, (int)s5, (int)s6, 0, n2, n3, n4);
    }

    public final void a(Image object, int n2, int n3, int n4, int n5, short[] sArray, int n6) {
        n6 *= 5;
        try {
            this.a.drawRegion((Image)object, (int)sArray[n6 + 1], (int)sArray[n6 + 2], (int)sArray[n6 + 3], (int)sArray[n6 + 4], n5, n2, n3, n4);
            return;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            System.out.print(((Throwable)object).toString());
            System.out.print("temp = " + n6);
            return;
        }
    }

    public final void b(int n2, int n3, int n4, int n5, int n6) {
        this.a.setColor(n2);
        this.a.fillRect(n3, n4, n5, n6);
    }

    public final void a(String string, int n2, int n3, int n4, int n5) {
        this.a(string, n2, n3, n4, this.a, n5);
    }

    public final void c(int n2, int n3, int n4, int n5, int n6) {
        this.a.setColor(n2);
        this.a.drawLine(n3, n4, n5, n6);
    }

    public final void a(String string, int n2, int n3) {
        this.b(n3);
        n2 = this.a / 2 % 2 == 0 ? 16743936 : 0xFFFFFF;
        this.a(string, 160, n3, 17, this.a, n2);
    }

    public final void b(String string, int n2, int n3) {
        this.b(65);
        if (this.a / 2 % 2 == 0) {
            this.a.setColor(16743936);
        } else {
            this.a.setColor(0xFFFFFF);
        }
        this.a(string, 160, 65, 17, this.a);
    }

    private void c(int n2) {
        this.v = n2;
        if (n2 >= 0) {
            Object object = "/data/" + n2 + ".mid";
            g g2 = this.a;
            g2.c();
            try {
                object = g2.getClass().getResourceAsStream((String)object);
                g2.a = Manager.createPlayer((InputStream)object, (String)"audio/midi");
                if (g2.a != null && g2.a.getState() != 0) {
                    g2.a.realize();
                    g2.a.prefetch();
                }
                g2.a();
                return;
            }
            catch (Exception exception) {
                object = exception;
                exception.printStackTrace();
            }
        }
    }

    private void s() {
        this.e = false;
        this.x = 0;
    }

    public final int a(String string, int n2, int n3, int n4, byte[] byArray) {
        int n5 = 0;
        switch (n4) {
            case 17: {
                n2 -= this.a(string) >> 1;
                break;
            }
            case 24: {
                n2 -= this.a(string);
            }
        }
        for (int i2 = 0; i2 < string.length(); ++i2) {
            char c2 = string.charAt(i2);
            n4 = c2;
            if (c2 == '$') {
                n4 = Integer.parseInt(string.charAt(++i2) + "");
                this.a(this.a[0][n4], this.a[1][n4]);
                continue;
            }
            this.a.setColor(0xFFFFFF);
            if (n4 < 128) {
                if ((n4 = src.b.a((char)n4, this.c)) == -1) continue;
                this.a(byArray, n4, this.h, n2, n3, 0, -16777216);
                n2 += this.h >> 1;
                n5 += this.h >> 1;
                continue;
            }
            if ((n4 = src.b.a((char)n4, this.d)) == -1) continue;
            this.a(byArray, n4, this.h, n2, n3, 1, -16777216);
            n2 += this.h;
            n5 += this.h;
        }
        return n5;
    }

    public final void a(String string, int n2, int n3, int n4, byte[] byArray, int n5) {
        int n6 = string.length();
        int n7 = n2;
        int n8 = n3;
        if ((n4 & 8) == 8) {
            n7 = n2 - this.c(string);
        } else if ((n4 & 1) == 1) {
            n7 = n2 - (this.c(string) >> 1);
        }
        if ((n4 & 0x20) == 32) {
            b b2 = this;
            n8 = n3 - b2.h;
        } else if ((n4 & 2) == 2) {
            b b3 = this;
            n8 = n3 - (b3.h >> 1);
        }
        n4 = 0;
        for (int i2 = 0; i2 < n6; ++i2) {
            if (string.charAt(i2) < '\u0080') {
                n3 = src.b.a(string.charAt(i2), this.c);
                if (n3 == -1) continue;
                this.a(byArray, n3, this.h, n7 + n4, n8, 0, -16777216, n5);
                n4 += this.h >> 1;
                continue;
            }
            n3 = src.b.a(string.charAt(i2), this.d);
            if (n3 == -1) continue;
            this.a(byArray, n3, this.h, n7 + n4, n8, 1, -16777216, n5);
            n4 += this.h;
        }
    }

    public final void a(String string, int n2, int n3, int n4, int n5, int n6) {
        this.a(string, n2, n3 - 1, n4, this.a, n6);
        this.a(string, n2, n3 + 1, n4, this.a, n6);
        this.a(string, n2 - 1, n3, n4, this.a, n6);
        this.a(string, n2 + 1, n3, n4, this.a, n6);
        this.a(string, n2, n3, n4, this.a, n5);
    }

    private void a(byte[] byArray, int n2, int n3, int n4, int n5, int n6, int n7) {
        int n8;
        int[] nArray;
        int n9;
        if (n6 == 0) {
            n2 *= 12;
            n9 = (n3 >> 1) + 7 >> 3;
            nArray = this.e;
            n8 = n3 >> 1;
        } else {
            n2 = this.c.length * 12 + n2 * 24;
            n9 = n3 + 7 >> 3;
            nArray = this.f;
            n8 = n3;
        }
        for (int i2 = 0; i2 < n3; ++i2) {
            int n10 = n7 | this.g[i2];
            int n11 = 0;
            block1: for (int i3 = 0; i3 < n9; ++i3) {
                n6 = (char)(byArray[n2 + i2 * n9 + i3] + 128);
                for (int i4 = 0; i4 < 8; ++i4) {
                    int n12 = (i3 << 3) + i4 + i2 * n8;
                    if (n11 >= n8) continue block1;
                    nArray[n12] = (n6 & 128 >> i4) != 0 ? n10 : 0;
                    ++n11;
                }
            }
        }
        this.a.drawRGB(nArray, 0, n8, n4, n5, n8, n3, true);
    }

    private void a(byte[] byArray, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        int n9;
        int[] nArray;
        int n10;
        if (n6 == 0) {
            n2 *= 12;
            n10 = (n3 >> 1) + 7 >> 3;
            nArray = this.e;
            n9 = n3 >> 1;
        } else {
            n2 = this.c.length * 12 + n2 * 24;
            n10 = n3 + 7 >> 3;
            nArray = this.f;
            n9 = n3;
        }
        for (int i2 = 0; i2 < n3; ++i2) {
            int n11 = 0xFF000000 | n8;
            int n12 = 0;
            block1: for (n7 = 0; n7 < n10; ++n7) {
                n6 = (char)(byArray[n2 + i2 * n10 + n7] + 128);
                for (int i3 = 0; i3 < 8; ++i3) {
                    int n13 = (n7 << 3) + i3 + i2 * n9;
                    if (n12 >= n9) continue block1;
                    nArray[n13] = (n6 & 128 >> i3) != 0 ? n11 : 0;
                    ++n12;
                }
            }
        }
        this.a.drawRGB(nArray, 0, n9, n4, n5, n9, n3, true);
    }

    private static int a(char c2, int[] nArray) {
        int n2 = 0;
        int n3 = nArray.length - 1;
        int n4 = n3 + 0 >> 1;
        while (nArray[n4] != c2 && n2 < n3) {
            if (c2 < nArray[n4]) {
                n3 = n4 - 1;
            } else if (c2 > nArray[n4]) {
                n2 = n4 + 1;
            }
            n4 = n2 + n3 >> 1;
        }
        if (nArray[n4] != c2) {
            return -1;
        }
        return n4;
    }

    private int c(String string) {
        int n2 = 0;
        for (int i2 = 0; i2 < string.length(); ++i2) {
            if (string.charAt(i2) < '\u0080') {
                n2 += this.h >> 1;
                continue;
            }
            n2 += this.h;
        }
        return n2;
    }

    public final String[] a(String stringArray, int n2, int n3) {
        r r2 = new r();
        char c2 = '0';
        int n4 = 0;
        int n5 = -1;
        int n6 = 0;
        while (n4 < stringArray.length()) {
            if (++n5 > stringArray.length() - 1) {
                String string = stringArray.substring(n4);
                src.b.a(r2, string, c2, 0);
                n6 = string.lastIndexOf(36);
                if (n6 < 0) break;
                string.charAt(n6 + 1);
                break;
            }
            if (stringArray.charAt(n5) == '|') {
                String string = stringArray.substring(n4, n5);
                src.b.a(r2, string, c2, 0);
                n6 = string.lastIndexOf(36);
                if (n6 >= 0) {
                    c2 = string.charAt(n6 + 1);
                }
                n4 = n5 + 1;
                n6 = 0;
                continue;
            }
            if (stringArray.charAt(n5) == '$') {
                ++n5;
                continue;
            }
            if (stringArray.charAt(n5) < '\u0080') {
                if ((n6 += this.h >> 1) <= n2) continue;
                String string = stringArray.substring(n4, n5);
                src.b.a(r2, string, c2, 0);
                n6 = string.lastIndexOf(36);
                if (n6 >= 0) {
                    c2 = string.charAt(n6 + 1);
                }
                n4 = n5--;
                n6 = 0;
                continue;
            }
            if ((n6 += this.h) <= n2) continue;
            String string = stringArray.substring(n4, n5);
            src.b.a(r2, string, c2, 0);
            n6 = string.lastIndexOf(36);
            if (n6 >= 0) {
                c2 = string.charAt(n6 + 1);
            }
            n4 = n5--;
            n6 = 0;
        }
        r r3 = r2;
        stringArray = new String[r3.a];
        n2 = 0;
        while (true) {
            r3 = r2;
            if (n2 >= r3.a) break;
            stringArray[n2] = (String)r2.a(n2);
            ++n2;
        }
        return stringArray;
    }

    private static void a(r r2, String string, char c2, int n2) {
        if (n2 == 0 && string.charAt(0) != '$') {
            StringBuffer stringBuffer = new StringBuffer("$");
            stringBuffer.append(c2);
            stringBuffer.append(string);
            string = stringBuffer.toString();
        }
        r2.a(string);
    }

    public final void a(String[] stringArray, int n2, int n3, int n4, int n5, int n6, int n7) {
        byte[] byArray = this.a;
        this.a.setColor(n7);
        for (int i2 = 0; i2 < n6; ++i2) {
            n7 = i2 + n5;
            if (n7 < 0 || n7 >= stringArray.length) continue;
            String string = stringArray[n7];
            this.a(string, n2, n3 + (i2 << 4), n4, byArray);
        }
    }

    public final void a(String[] stringArray, int n2, int n3, int n4, int n5, int n6, int n7, byte n8) {
        byte[] byArray = this.a;
        int n9 = n2;
        int n10 = 0;
        this.a.setColor(16252891);
        for (int i2 = 0; i2 < n6; ++i2) {
            n2 = i2 + n5;
            if (n2 < 0 || n2 >= stringArray.length) continue;
            String string = stringArray[n2];
            n2 = n9;
            for (int i3 = 0; i3 < string.length() && n10 < n8; ++i3) {
                int n11 = string.charAt(i3);
                if (n11 == 36) {
                    n11 = Integer.parseInt(string.charAt(++i3) + "");
                    this.a(this.a[0][n11], this.a[1][n11]);
                    continue;
                }
                if (n11 < 128) {
                    if ((n11 = src.b.a((char)n11, this.c)) != -1) {
                        this.a(byArray, n11, this.h, n2, n3 + (i2 << 4), 0, -16777216);
                        n2 += this.h >> 1;
                    }
                } else if ((n11 = src.b.a((char)n11, this.d)) != -1) {
                    this.a(byArray, n11, this.h, n2, n3 + (i2 << 4), 1, -16777216);
                    n2 += this.h;
                }
                ++n10;
            }
        }
    }

    public final void b(String[] stringArray, int n2, int n3, int n4, int n5, int n6, int n7) {
        byte[] byArray = this.a;
        for (int i2 = 0; i2 < n6; ++i2) {
            n2 = i2 + n5;
            if (n2 < 0 || n2 >= stringArray.length) continue;
            String string = stringArray[n2];
            n2 = 160 - (this.a(string) >> 1);
            n4 = -16777216;
            if (i2 == n6 - 1 && n7 >= 0) {
                n4 = n7 * 0x11000000;
            }
            for (int i3 = 0; i3 < string.length(); ++i3) {
                int n8 = string.charAt(i3);
                if (n8 == 36) {
                    n8 = Integer.parseInt(string.charAt(++i3) + "");
                    this.a(this.a[0][n8], this.a[1][n8]);
                    continue;
                }
                if (n8 < 128) {
                    if ((n8 = src.b.a((char)n8, this.c)) == -1) continue;
                    this.a(byArray, n8, this.h, n2, n3 + (i2 << 4), 0, n4);
                    n2 += this.h >> 1;
                    continue;
                }
                if ((n8 = src.b.a((char)n8, this.d)) == -1) continue;
                this.a(byArray, n8, this.h, n2, n3 + (i2 << 4), 1, n4);
                n2 += this.h;
            }
        }
    }

    public final int a(String string) {
        int n2 = 0;
        for (int i2 = 0; i2 < string.length(); ++i2) {
            if (string.charAt(i2) == '$') {
                ++i2;
                continue;
            }
            if (string.charAt(i2) < '\u0080') {
                n2 += this.h >> 1;
                continue;
            }
            n2 += this.h;
        }
        return n2;
    }

    public static int b(String string) {
        int n2 = 0;
        for (int i2 = 0; i2 < string.length(); ++i2) {
            if (string.charAt(i2) == '#') {
                ++i2;
                continue;
            }
            ++n2;
        }
        return n2;
    }

    private void a(int n2, int n3) {
        for (int i2 = 0; i2 < this.g.length; ++i2) {
            int n4 = i2 * ((n2 & 0xFF0000) - (n3 & 0xFF0000)) / 15 + n3 & 0xFF0000;
            int n5 = i2 * ((n2 & 0xFF00) - (n3 & 0xFF00)) / 15 + n3 & 0xFF00;
            int n6 = i2 * ((n2 & 0xFF) - (n3 & 0xFF)) / 15 + n3 & 0xFF;
            this.g[i2] = n4 | n5 | n6;
        }
    }

    public final void j() {
        if (this.a.a() != 10) {
            byte[] byArray = new byte[]{0};
            for (int i2 = 0; i2 < 10; ++i2) {
                this.a.a(byArray);
            }
        }
    }

    public final void a(byte[] byArray, int n2) {
        this.a.a(n2, byArray);
    }

    public final byte[] a(int n2) {
        return this.a.a(n2);
    }

    public final void b(int n2) {
        for (int i2 = 0; i2 < 14; ++i2) {
            this.a.drawRGB(this.b, 0, 320, 0, n2 - 1 + i2, 320, 1, true);
        }
    }

    public final void a(String string) {
        try {
            this.a.platformRequest(string);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private void b(String string) {
        try {
            this.a.c();
            b b2 = this;
            this.b = false;
            this.a.platformRequest(string);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    final void k() {
        try {
            this.a = Image.createImage((String)"/logo/exit.png");
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }
}

