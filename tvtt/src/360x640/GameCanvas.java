/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Canvas
 *  javax.microedition.lcdui.Font
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package src;

import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import src.Camera;
import src.Control;
import src.Flyer;
import src.GameData;
import src.GameLogo;
import src.GameMenu;
import src.GameMidlet;
import src.GamePointer;
import src.GameRMS;
import src.GameSms;
import src.GameUI;
import src.MapManager;
import src.RoleManager;
import src.Sound;
import src.Util;
import src.Vector;

public class GameCanvas
extends Canvas
implements Runnable,
GameData {
    GameMidlet midlet;
    RoleManager rolem;
    MapManager mapm;
    GameMenu menu;
    GameRMS rms;
    GameRMS smsrms;
    GameSms sms;
    GameLogo logo;
    GamePointer pointer;
    Camera c;
    Sound sound;
    GameUI gameUi;
    Flyer[] flyer;
    Graphics g;
    int[] screenData;
    int[] linedata;
    boolean isRunning;
    long starttime;
    long endtime;
    public byte gamestate;
    public byte newgamestate;
    public byte lastgamestate;
    public byte pausestate;
    Image buffer;
    Graphics gB;
    int[][] colorData = new int[][]{{0xFFFFFF}, {0}, {0, 16744743, 3, 16731672, 6, 16715781}};
    final byte RMSNUM = (byte)10;
    short zkNum;
    int[] zkIndexData;
    public static final String ZK_PATH = "/data/hzk12.data";
    public static final String ZK_INDEX_PATH = "/data/hzk12index.data";
    public String[] publicword = new String[]{"$2\u84dd\u8272$1\u62a4\u7532\u7c7b\u578b\u5e94\u8be5\u7528\u5251\u653b\u51fb\u3002", "$2\u7ea2\u8272$1\u62a4\u7532\u7c7b\u578b\u5e94\u8be5\u7528\u62f3\u5957\u653b\u51fb\u3002", "$2\u9ec4\u8272$1\u62a4\u7532\u7c7b\u578b\u5e94\u8be5\u7528\u5251\u3001\u62f3\u5957\u653b\u51fb\u5747\u53ef\u3002", "$1\u65e0\u62a4\u7532\u7c7b\u578b\u7684\u654c\u4eba\u5219\u65e0\u62a4\u7532\u663e\u793a\u3002", "$1\u5728$2\u6e38\u620f\u5e2e\u52a9$1\u4e2d\u53ef\u4ee5\u67e5\u770b$2\u6750\u6599\u7684\u6389\u843d$1\u60c5\u51b5\u3002", "$1\u5728$2\u661f\u9645\u5546\u5e97$1\u4e2d\u4ef7\u683c\u4e70\u9ad8\u5356\u4f4e\u3002", "$1\u7075\u6d3b\u8fd0\u7528\u4f60\u7684$2\u5750\u9a91$1\u53ef\u4ee4\u4f60\u7545\u901a\u65e0\u963b\u3002", "$1\u5404\u4e2a$2\u57ce\u5e02$1\u6240\u953b\u9020\u7684\u88c5\u5907\u5747\u4e0d\u4e00\u6837\u3002", "$2\u5347\u7ea7\u57ce\u5e02$1\u53ef\u4ee4\u519b\u5de5\u5382\u7684\u953b\u9020\u88c5\u5907\u589e\u591a\u3002", "\u5728\u57ce\u5e02\u548c\u6218\u6597\u4e2d\u6309$2\u56fe\u6807 * $1\u53ef\u8fdb\u5165\u4efb\u52a1\u67e5\u770b\u3002", "\u5347\u7ea7\u57ce\u5e02\u53ef\u589e\u52a0\u77ff\u5de5\u96c7\u4f63\u4e0a\u9650\u3002"};
    byte paintType;
    int publiccounter;
    short flushdelay;
    byte loadingTime;
    byte loadIndex;
    byte loadMusicIndex;
    final byte \u6e05\u7a7alogo;
    final byte \u521d\u59cb\u5316Camera;
    final byte \u521d\u59cb\u5316Logic = (byte)2;
    final byte \u521d\u59cb\u5316Menu = (byte)3;
    final byte \u6e05\u7a7aMenu = (byte)4;
    final byte \u521d\u59cb\u5316mapm = (byte)5;
    final byte \u6e05\u7a7amapm = (byte)6;
    final byte \u521d\u59cb\u5316rolem = (byte)7;
    final byte \u521d\u59cb\u5316ui = (byte)8;
    final byte \u6e05\u7a7aui = (byte)9;
    final byte \u52a0\u8f7d\u6218\u6597\u5730\u56fe = (byte)10;
    final byte \u521d\u59cb\u5316gameUi\u6570\u636e = (byte)11;
    final byte \u521d\u59cb\u5316Box = (byte)12;
    final byte \u521b\u5efa\u4e3b\u89d2 = (byte)13;
    final byte \u521d\u59cb\u5316\u953b\u9020 = (byte)14;
    final byte \u521b\u5efa\u5f53\u524d\u654c\u4eba\u8d44\u6e90 = (byte)15;
    final byte \u521b\u5efa\u5149\u6548 = (byte)16;
    final byte \u6e05\u7a7a\u4e3b\u89d2 = (byte)17;
    final byte \u8bbe\u7f6e\u955c\u5934 = (byte)18;
    final byte \u6392\u5e8f = (byte)19;
    final byte \u521d\u59cb\u6218\u6597NPC = (byte)20;
    final byte \u521d\u59cb\u6218\u6597\u4eba\u7269\u4f4d\u7f6e = (byte)21;
    final byte \u6218\u6597\u97f3\u4e50 = (byte)22;
    final byte \u521d\u59cb\u5316\u70b9\u9635\u5b57\u5e93 = (byte)23;
    final byte \u521d\u59cb\u5316\u5927\u5730\u56fe = (byte)24;
    final byte \u521d\u59cb\u5316\u57ce\u5e02 = (byte)25;
    final byte \u6e05\u7a7a\u5927\u5730\u56fe = (byte)26;
    final byte \u6e05\u7a7a\u57ce\u5e02 = (byte)27;
    final byte \u6e05\u7a7a\u6218\u6597\u8d44\u6e90 = (byte)28;
    final byte \u8f7d\u5165\u7269\u54c1\u56fe\u7247 = (byte)29;
    final byte \u66f4\u6362\u6b66\u5668\u56fe\u7247 = (byte)30;
    final byte \u6e05\u7a7a\u7c7b\u8d44\u6e90 = (byte)31;
    final byte \u6e05\u7a7a\u4e3b\u89d2\u8d44\u6e90 = (byte)32;
    final byte \u65b0\u6e38\u620f = (byte)33;
    final byte \u8f7d\u5165\u95e8\u8d44\u6e90 = (byte)34;
    final byte \u521d\u59cb\u5316Text = (byte)35;
    final byte \u91cd\u65b0\u521d\u59cb\u5316\u4eba\u7269\u6570\u636e = (byte)36;
    final byte \u521d\u59cb\u5f39\u51fa\u6570\u5b57 = (byte)37;
    final byte \u91cd\u65b0\u521b\u5efa\u4e3b\u89d2 = (byte)38;
    final byte \u8bfb\u53d6\u5b58\u6863 = (byte)39;
    final byte \u91cd\u7f6e\u4e3b\u89d2\u6b66\u5668\u548c\u5750\u9a91\u56fe\u7247 = (byte)40;
    final byte \u5b58\u50a8\u6e38\u620f = (byte)41;
    final byte \u7ee7\u7eed\u6e38\u620f\u6218\u6597\u521d\u59cb = (byte)43;
    final byte \u52a0\u8f7d\u8868\u60c5 = (byte)44;
    final byte \u5b58\u50a8\u4e34\u65f6\u4e3b\u89d2\u6570\u636e = (byte)45;
    final byte \u8bfb\u53d6\u4eba\u7269\u4e34\u65f6\u6570\u636e = (byte)46;
    final byte \u521d\u59cb\u5316\u4e3b\u89d2\u6570\u636e = (byte)47;
    final byte \u91cd\u65b0\u6dfb\u52a0\u7269\u54c1 = (byte)48;
    final byte \u521d\u59cb\u5316\u4eba\u7269\u751f\u547d = (byte)49;
    final byte \u66f4\u6362\u5750\u9a91 = (byte)50;
    final byte \u4e3b\u83dc\u5355\u97f3\u4e50 = (byte)51;
    final byte \u5b87\u5b99\u97f3\u4e50 = (byte)52;
    final byte \u57ce\u5e02\u97f3\u4e50 = (byte)53;
    final byte[][] resdata = new byte[][]{{0, 1, 7, 8, 3, 5, 35, 11, 16, 34, 29, 44, 37, 51}, {4, 13, 12, 24, 20, 33, 52}, {27, 24, 52}, {26, 25, 14, 53}, {28, 24, 52}, {26, 28, 12, 10, 20, 15, 15, 15, 15, 15, 21, 22}, {28, 12, 10, 20, 15, 15, 15, 15, 15, 21, 22}, {32, 31, 7, 8, 11, 5, 3, 34, 44, 16, 37, 29, 51}, {27, 28, 12, 10, 20, 15, 15, 15, 15, 15, 21, 22}, {28, 25, 14, 53}, {4, 13, 39, 40, 12, 10, 20, 15, 15, 15, 15, 15, 43, 22}, {4, 13, 39, 40, 12, 25, 14, 53}, {28, 12, 10, 20, 45, 32, 38, 46, 30, 50, 15, 15, 15, 15, 15, 21, 22}, {28, 12, 10, 20, 45, 33, 32, 38, 49, 30, 50, 15, 15, 15, 15, 15, 21, 22}, {28, 12, 10, 20, 32, 38, 46, 30, 50, 15, 15, 15, 15, 15, 21, 22}, {28, 32, 47, 38, 49, 48, 50, 25, 14, 53}, {28, 25, 14, 32, 38, 46, 30, 50, 53}, {32, 31, 7, 8, 11, 5, 3, 34, 44, 16, 37, 29, 13, 39, 40, 12, 10, 20, 15, 15, 15, 15, 15, 43, 22}, {32, 31, 7, 8, 11, 5, 3, 34, 44, 16, 37, 29, 13, 39, 40, 12, 25, 14, 53}};
    private int font_size;
    private int[] asc12Index = null;
    private int[] hzk12Index = null;
    private int ascSize;
    private int hzkSize;
    final String path_index;
    final String path_hzk;
    byte[] zkData;
    int switchScreenState;
    int switchScreenType;
    int switchScreenTime;
    int switchScreenShowTime;
    int switchScreenGameState;
    int switchGameLoadState;
    boolean isShowSwitchScreen;
    byte loadIconIndex;
    byte[] loadicondata = new byte[]{18, 20, 21, 22};
    public static final int SWITCH_SCREEN_SHOW_TIME = 7;
    boolean isdrawwhite;
    int whitescreentime;
    final int rectW;
    final int rectH;
    int rectIndexW;
    int rectIndexH;
    int rectOffset = 16;
    byte changeScreenIndex;
    Image[] loadImage;
    short[] loadModules;
    short[][] loadFrames;
    short[][] loadActions;
    byte loadActionIndex;
    byte loadActionFrameIndex;
    byte loadFrameDelay;
    boolean loadActionOver;
    byte loadImgIndex = 1;
    byte[] loadImgData = new byte[]{18, 17, 20, 19};
    byte loadType;
    int loadBackGroundX;
    String[] loadingword;
    final int LOADINGWORDW;
    boolean isPause;
    int camerax;
    int cameray;
    int farPercent = 1;
    int middlePercent = 2;
    int eventBackH;
    byte eventBackState;
    byte bossDeathTime;
    byte bossDeathType;
    int playerLife = 30;
    int playerMagic = 30;
    String[] helpinfo;
    String[] helptitle;
    byte helpstartLine;
    final byte HELPSHOWLINE = (byte)11;
    int helpshowtotal;
    byte[] helpenemylist = new byte[]{12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 1, 2, 3, 4, 5, 6, 7, 8};
    String[] enemypos = new String[]{"\u65af\u5361\u683c\u62c9\u514b\u4e18\u9675", "\u65b0\u9521\u5b89\u3001\u4e4c\u62c9\u5c14\u5e73\u539f", "\u65af\u5361\u683c\u62c9\u514b\u4e18\u9675", "\u65b0\u9521\u5b89\u3001\u4e4c\u62c9\u5c14\u5e73\u539f", "\u5317\u90e8\u6d77\u6e7e", "\u6d77\u62c9\u5c14\u9a7f\u9053", "\u5df4\u4f10\u5229\u4e9a\u5821\u5792", "\u80af\u7279\u536b\u57ce\u3001\u7c73\u4e9a\u661f\u524d\u54e8\u7ad9", "\u5317\u90e8\u6d77\u6e7e", "\u963f\u73c2\u5821\u5792\u3001\u53f6\u5c3c\u585e\u5c71\u5cad\u3001\u6d77\u62c9\u5c14\u9a7f\u9053\u3001\u4e4c\u62c9\u5c14\u5e73\u539f\u3001\u7c73\u4e9a\u661f\u524d\u54e8\u7ad9", "\u5317\u90e8\u6d77\u6e7e", "\u963f\u73c2\u5821\u5792\u3001\u5c0f\u4e9a\u7ec6\u4e9a\u5821\u5792\u5916\u90e8", "\u65af\u5361\u683c\u62c9\u514b\u4e18\u9675", "\u963f\u73c2\u5821\u5792\u3001\u65b0\u9521\u5b89", "\u5df4\u4f10\u5229\u4e9a\u5821\u5792", "\u65b0\u9521\u5b89\u3001\u4e4c\u62c9\u5c14\u5e73\u539f", "\u5317\u90e8\u6d77\u6e7e", "\u963f\u73c2\u5821\u5792\u3001\u963f\u63d0\u62c9\u4f4e\u5730\u3001\u7c73\u4e9a\u661f\u524d\u54e8\u7ad9\u3001\u65b0\u9521\u5b89\u3001\u6208\u5170\u9ad8\u5730", "\u65af\u5361\u683c\u62c9\u514b\u4e18\u9675\u3001\u5317\u90e8\u6d77\u6e7e\u3001\u5df4\u4f10\u5229\u4e9a\u5821\u5792", "\u963f\u63d0\u62c9\u4f4e\u5730\u3001\u5c0f\u4e9a\u7ec6\u4e9a\u5821\u5792\u5916\u90e8\u3001\u7c73\u4e9a\u661f\u524d\u54e8\u7ad9\u3001\u65b0\u9521\u5b89\u3001\u6208\u5170\u9ad8\u5730", "\u5df4\u4f10\u5229\u4e9a\u5821\u5792", "\u5c0f\u4e9a\u7ec6\u4e9a\u5821\u5792\u5916\u90e8\u3001\u7c73\u4e9a\u661f\u524d\u54e8\u7ad9\u3001\u6208\u5170\u9ad8\u5730", "\u5927\u9ad8\u52a0\u7d22", "\u53f6\u5c3c\u585e\u5c71\u5cad", "\u5927\u9ad8\u52a0\u7d22", "\u53f6\u5c3c\u585e\u5c71\u5cad", "\u5927\u9ad8\u52a0\u7d22\u3001\u8d1d\u5c14\u96ea\u539f", "\u6d77\u62c9\u5c14\u9a7f\u9053\u3001\u80af\u7279\u536b\u57ce\u3001\u7279\u5c14\u83ab\u4e1c\u5ce1\u8c37\u3001\u5c0f\u4e9a\u7ec6\u4e9a\u5821\u5792\u5185\u90e8\u3001\u7c73\u4e9a\u661f\u524d\u54e8\u7ad9\u3001\u65b0\u9521\u5b89\u3001\u6208\u5170\u9ad8\u5730", "\u5927\u9ad8\u52a0\u7d22\u3001\u8d1d\u5c14\u96ea\u539f", "\u5927\u9ad8\u52a0\u7d22\u3001\u8d1d\u5c14\u96ea\u539f", "\u683c\u91cc\u65af\u6cb3\u8c37", "\u963f\u63d0\u62c9\u4f4e\u5730", "\u683c\u91cc\u65af\u6cb3\u8c37", "\u963f\u63d0\u62c9\u4f4e\u5730", "\u5df4\u4f10\u5229\u4e9a\u5821\u5792", "\u7279\u5c14\u83ab\u4e1c\u5ce1\u8c37\u3001\u5c0f\u4e9a\u7ec6\u4e9a\u5821\u5792\u5916\u90e8\u3001\u6208\u5170\u9ad8\u5730", "\u683c\u91cc\u65af\u6cb3\u8c37", "\u963f\u63d0\u62c9\u4f4e\u5730\u3001\u4e4c\u62c9\u5c14\u5e73\u539f", "\u5927\u9ad8\u52a0\u7d22", "\u53f6\u5c3c\u585e\u5c71\u5cad", "\u8d1d\u5c14\u96ea\u539f", "\u6d77\u62c9\u5c14\u9a7f\u9053\u3001\u80af\u7279\u536b\u57ce\u3001\u7279\u5c14\u83ab\u4e1c\u5ce1\u8c37\u3001\u5c0f\u4e9a\u7ec6\u4e9a\u5821\u5792\u5185\u90e8", "\u5df4\u4f10\u5229\u4e9a\u5821\u5792", "\u963f\u73c2\u5821\u5792\u3001\u65b0\u9521\u5b89\u3001\u53f6\u5c3c\u585e\u5c71\u5cad\u3001\u7c73\u4e9a\u661f\u524d\u54e8\u7ad9"};
    byte helpenemylisttotal = (byte)this.helpenemylist.length;
    byte gamehelpstate;
    byte gamehelpselectindex;
    byte gamehelpenemyselectindex;
    byte gamehelpmaterialstartindex;
    byte gamehelpmaterialselectindex;
    String[] gamehelpname = new String[]{"\u6e38\u620f\u64cd\u4f5c", "\u654c\u5175\u56fe\u9274"};
    byte[] enemymaterialdata;
    byte enemymaterialtotal;
    String[] enemyposword;
    public static final int ITEM_SHOW_STEP_X = 203;
    public static final int ITEM_SHOW_STEP_Y = 54;
    public static final int HELP_MENU_ITEM_STEP_Y = 70;
    String[] aboutinfo;
    byte aboutIndex;
    byte aboutstartLine;
    final byte ABOUTSHOWLINE = (byte)12;
    int aboutshowtotal;
    Image[] popnumimgs;
    short[] popnummodules;
    Image[] batterNumImages;
    short[] batterNummodules;
    short[][] batterNumframes;
    short[][] betterNumactions;
    Image[] debuffImages;
    short[] debuffmodules;
    short[][] debuffframes;
    short[][] debuffactions;
    Image[] faceimages;
    short[] facemodules;
    short[][] faceframes;
    short[][] faceactions;
    Image[] skilliconimage;
    short[] skilliconmodules;
    short[][] skilliconframes;
    short[][] skilliconactions;
    Image[] itemiconimages;
    short[] itemiconmodules;
    byte[] itemicondata = new byte[]{0, 1, 2, 3};
    Image[] dropitemimages;
    short[] dropitemmodules;
    short[][] dropitemframes;
    short[][] dropitemactions;
    short[] dropitembeatt;
    Image[] defaultimages;
    short[] defaultmodules = new short[]{0, 10, 0, 9, 10, 0, 20, 0, 9, 10, 0, 0, 0, 9, 10};
    final byte[][] transtable = new byte[][]{{0, 1, 2, 3, 4, 5, 6, 7}, {1, 0, 3, 2, 5, 4, 7, 6}, {2, 3, 0, 1, 6, 7, 4, 5}, {3, 2, 1, 0, 7, 6, 5, 4}, {4, 6, 5, 7, 0, 2, 1, 3}, {5, 7, 4, 6, 1, 3, 0, 2}, {6, 4, 7, 5, 2, 0, 3, 1}, {7, 5, 6, 4, 3, 1, 2, 0}};
    int modulex;
    int moduley;
    int musicIndex = -1;
    boolean istongguan;
    boolean isFlush;
    short flushDelay;
    byte flushCounter;
    final int[][] colorlist = new int[][]{{0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF}, {0xFFFFFF, 0xFFFFFF, 196354, 182015, 14156543, 16745730, 0xFF0000, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF}};
    int[] fontdata0 = new int[128];
    int[] fontdata1 = new int[256];
    int[] colordata = new int[20];
    public static final String CONTACT_ADDRESS = "http://wap.ttsy.org/gamecms/go/jpgd?cpId=701057&contentID=000048675000";
    Image quitImage;
    public static final String LOGO_PATH = "/logo/";

    public GameCanvas(GameMidlet midlet) {
        this.\u6e05\u7a7alogo = 0;
        this.\u521d\u59cb\u5316Camera = 1;
        this.path_index = ZK_INDEX_PATH;
        this.path_hzk = ZK_PATH;
        this.rectW = 16;
        this.rectH = 16;
        this.LOADINGWORDW = 480;
        this.setFullScreenMode(true);
        this.midlet = midlet;
        this.rms = new GameRMS("XJCS");
        this.smsrms = new GameRMS("XJCS_SMS");
        this.pointer = new GamePointer(this);
        this.sound = new Sound();
        this.sms = new GameSms(this);
        this.sms.smsRecord();
        this.screenData = new int[640];
        this.initlinedata();
        this.createLoadImage();
        this.startGame();
        this.buffer = Image.createImage((int)640, (int)360);
        this.g = this.buffer.getGraphics();
        this.initFonts(ZK_INDEX_PATH, 16);
        this.zkData = Util.readHZK(ZK_PATH);
        this.loadGameState(3, 0, (byte)0);
    }

    private void initlinedata() {
        this.linedata = new int[640];
        for (int i = 0; i < this.linedata.length; ++i) {
            this.linedata[i] = -1275068416;
        }
    }

    protected void paint(Graphics g1) {
        this.gB = g1;
        this.g.setFont(Font.getFont((int)0, (int)0, (int)8));
        this.setClip();
        this.fillRect(0, 0, 0, 640, 360);
        this.paint();
        this.paintBuffer();
    }

    public void setClip() {
        this.g.setClip(0, 0, 640, 360);
    }

    private void paintBuffer() {
        switch (this.paintType) {
            case 0: 
            case 2: {
                this.gB.drawImage(this.buffer, 0, 0, 0);
                break;
            }
            case 1: {
                this.gB.drawRGB(Util.effect_negative(this.buffer), 0, 640, 0, 0, 640, 360, false);
            }
        }
    }

    private void paint() {
        switch (this.gamestate) {
            case 14: {
                this.sms.drawSms();
                break;
            }
            case 0: {
                break;
            }
            case 1: {
                this.drawLoading();
                break;
            }
            case 2: {
                this.drawSwitchSound();
                break;
            }
            case 3: {
                this.menu.drawMenu();
                break;
            }
            case 4: {
                this.drawcitymap();
                this.gameUi.drawskillpointicon();
                this.gameUi.drawmissionreward();
                this.gameUi.drawtishi();
                this.gameUi.drawMinePlan();
                this.gameUi.drawMissionPlan(0);
                this.mapm.drawTeach();
                this.drawEventBack();
                break;
            }
            case 5: {
                this.drawFight();
                this.gameUi.drawskillpointicon();
                this.gameUi.drawFightUi();
                this.gameUi.drawVisualKeyboard();
                this.gameUi.drawmissionreward();
                this.gameUi.drawtishi();
                this.gameUi.drawMinePlan();
                this.gameUi.drawMissionPlan(0);
                this.mapm.drawTeach();
                this.gameUi.drawfighttishiword();
                this.drawEventBack();
                break;
            }
            case 6: {
                switch (this.lastgamestate) {
                    case 4: {
                        this.mapm.drawCityMap((byte)1);
                        this.drawEventBack();
                        break;
                    }
                    case 5: {
                        this.drawFight();
                        this.drawEventBack();
                        break;
                    }
                    case 10: {
                        this.mapm.drawUniverseMap((byte)1);
                        this.drawEventBack();
                    }
                }
                this.gameUi.drawMissionPlan(0);
                this.gameUi.drawskillpointicon();
                this.gameUi.drawTalk();
                break;
            }
            case 7: {
                this.gameUi.drawUi();
                this.gameUi.drawtishi();
                this.gameUi.drawMinePlan();
                this.gameUi.drawMissionPlan(-35);
                this.gameUi.drawmissionreward();
                this.mapm.drawTeach();
                break;
            }
            case 8: {
                this.drawPause();
                break;
            }
            case 9: {
                this.drawBossDeath();
                break;
            }
            case 10: {
                this.drawUniverseMap();
                this.gameUi.drawtishi();
                this.gameUi.drawMinePlan();
                this.gameUi.drawmissionreward();
                this.drawEventBack();
                this.mapm.drawTeach();
                break;
            }
            case 11: {
                this.mapm.drawminimap(this.rolem.fplayerbox[0]);
                break;
            }
            case 12: {
                this.gameUi.drawaside();
                break;
            }
            case 13: {
                switch (this.lastgamestate) {
                    case 4: {
                        this.mapm.drawCityMap((byte)1);
                        break;
                    }
                    case 5: {
                        this.drawFight();
                        break;
                    }
                    case 10: {
                        this.mapm.drawUniverseMap((byte)1);
                    }
                }
                this.gameUi.drawIntro();
                break;
            }
            case 52: {
                this.quitDataToPaint();
                break;
            }
            case 54: {
                this.quitConfirmToPaint();
                break;
            }
            case 55: {
                this.isContactToPaint();
            }
        }
        this.drawSwitchScreen();
        if (this.rolem != null && this.mapm != null && this.rolem.\u5f00\u542f\u79d8\u7c4d) {
            if (this.pointer != null) {
                this.pointer.drawKeyRect();
            }
            this.g.setColor(0xFF0000);
            this.g.drawString("Y s=" + this.gamestate + "  m=" + this.mapm.mapIndex + " p = " + GamePointer.pointerState, 10, 60, 0);
        }
    }

    public void drawcitymap() {
        if (this.mapm.isEventPlaying) {
            this.mapm.drawCityMap((byte)1);
        } else {
            this.mapm.drawCityMap((byte)0);
        }
    }

    public void drawUniverseMap() {
        if (this.mapm.isEventPlaying) {
            this.mapm.drawUniverseMap((byte)1);
        } else {
            this.mapm.drawUniverseMap((byte)0);
        }
    }

    public void run() {
        try {
            while (this.isRunning) {
                this.starttime = System.currentTimeMillis();
                this.pointer.pointerStateProcces();
                this.pointer.pointerProcces();
                this.gameKeyProcess();
                this.gameProcess();
                if (this.isPause && this.gamestate != 1) {
                    this.isPause = false;
                    this.pausestate = this.gamestate;
                    this.gamestate = (byte)8;
                    this.sound.closeMusic();
                    Control.keyReleased();
                }
                this.publiccounter = ++this.publiccounter > 99999 ? 0 : this.publiccounter;
                this.repaint();
                this.serviceRepaints();
                this.flushdelay = 0;
                if (this.isFlush && this.gamestate == 5) {
                    this.flushdelay = (short)200;
                }
                this.endtime = System.currentTimeMillis() - this.starttime;
                if (this.endtime >= (long)(80 + this.flushdelay)) continue;
                Thread.sleep((long)(80 + this.flushdelay) - this.endtime);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.destroyGame();
    }

    private void gameKeyProcess() {
        if (this.isShowSwitchScreen && this.switchScreenState == 0) {
            Control.KeyStateDestroy();
            return;
        }
        if (Control.KeyEnable) {
            Control.GetKey();
        } else {
            Control.KeyStateDestroy();
        }
        switch (this.gamestate) {
            case 14: {
                this.sms.smsKeyProcess();
                break;
            }
            case 0: {
                break;
            }
            case 1: {
                break;
            }
            case 2: {
                this.switchSoundKeyProcess();
                break;
            }
            case 3: {
                this.menu.menuKeyProcess();
                break;
            }
            case 4: {
                if (this.gameUi.isshowtishi) {
                    this.gameUi.tishiKeyProcess();
                    break;
                }
                if (this.mapm.isTeachPlayering) {
                    this.mapm.teachKeyProcess(this.rolem.fplayerbox[0]);
                    break;
                }
                if (this.gameUi.isshowmissionreward) {
                    this.gameUi.missionrewardkeyprocess();
                    break;
                }
                this.mapm.cityMapKeyProcess();
                break;
            }
            case 5: {
                if (this.gameUi.isshowtishi) {
                    this.gameUi.tishiKeyProcess();
                    break;
                }
                if (this.mapm.isTeachPlayering) {
                    this.mapm.teachKeyProcess(this.rolem.fplayerbox[0]);
                    break;
                }
                if (this.gameUi.isshowmissionreward) {
                    this.gameUi.missionrewardkeyprocess();
                    break;
                }
                this.fightKeyProcess();
                break;
            }
            case 6: {
                this.gameUi.talkKeyProcess();
                break;
            }
            case 7: {
                if (this.gameUi.isshowtishi) {
                    this.gameUi.tishiKeyProcess();
                    break;
                }
                if (this.mapm.isTeachPlayering) {
                    this.mapm.teachKeyProcess(this.rolem.fplayerbox[0]);
                    break;
                }
                if (this.gameUi.isshowmissionreward) {
                    this.gameUi.missionrewardkeyprocess();
                    break;
                }
                this.gameUi.GameUiKeyProcess();
                break;
            }
            case 8: {
                this.pauseKeyProcess();
                break;
            }
            case 10: {
                if (this.gameUi.isshowtishi) {
                    this.gameUi.tishiKeyProcess();
                    break;
                }
                if (this.mapm.isTeachPlayering) {
                    this.mapm.teachKeyProcess(this.rolem.fplayerbox[0]);
                    break;
                }
                if (this.gameUi.isshowmissionreward) {
                    this.gameUi.missionrewardkeyprocess();
                    break;
                }
                this.mapm.universeKeyProcess();
                break;
            }
            case 11: {
                this.mapm.minimapKeyProcess();
                break;
            }
            case 12: {
                this.gameUi.asideKeyProcess();
                break;
            }
            case 13: {
                this.gameUi.introKeyProcess();
                break;
            }
            case 52: {
                this.quitKeyAction();
                break;
            }
            case 54: {
                this.quitConfirmKeyAction();
                break;
            }
            case 55: {
                this.isContactKeyAction();
            }
        }
    }

    private void gameProcess() {
        this.switchScreenProcess();
        switch (this.gamestate) {
            case 0: {
                break;
            }
            case 1: {
                this.loadLogic();
                break;
            }
            case 3: {
                break;
            }
            case 4: {
                this.mapm.eventProcess(this.rolem.fplayerbox[0]);
                this.mapm.cityMapProcess();
                this.gameUi.gameTimeProcess();
                break;
            }
            case 5: {
                this.fightProcess();
                if (this.paintType == 0) {
                    this.flyerProcess();
                    this.mapm.weatherProcess();
                }
                this.gameUi.gameTimeProcess();
                break;
            }
            case 6: {
                switch (this.lastgamestate) {
                    case 4: {
                        break;
                    }
                    case 5: {
                        if (this.paintType == 0) {
                            this.flyerProcess();
                        }
                        this.c.cameraProcess();
                        this.mapm.weatherProcess();
                    }
                }
                this.gameUi.talkProcess();
                this.gameUi.gameTimeProcess();
                break;
            }
            case 7: {
                this.gameUi.GameUiProcess();
                this.gameUi.gameTimeProcess();
                break;
            }
            case 8: {
                break;
            }
            case 9: {
                this.bossDeathProcess();
                break;
            }
            case 10: {
                this.mapm.eventProcess(this.rolem.fplayerbox[0]);
                this.mapm.universeProcess();
                break;
            }
            case 11: {
                this.mapm.minimapProcess();
                break;
            }
            case 12: {
                break;
            }
            case 13: {
                this.gameUi.introProcess();
            }
        }
    }

    public void changeGameState(int state) {
        this.lastgamestate = this.gamestate;
        this.gamestate = (byte)state;
        if (this.gamestate == 7) {
            this.gameUi.initCommand();
        }
    }

    public void loadGameState(int state, int index, byte type) {
        this.lastgamestate = this.gamestate;
        this.newgamestate = (byte)state;
        this.loadIndex = (byte)index;
        this.gamestate = 1;
        this.loadType = type;
        if (this.loadType == 0 || this.loadType == 2) {
            this.setloadingword(this.publicword[Util.getARandomInt(this.publicword.length)]);
        }
        this.loadingTime = 0;
    }

    public void createres(byte i) {
        switch (i) {
            case 50: {
                this.gameUi.changeride();
                break;
            }
            case 49: {
                this.rolem.initPlayerInfo();
                break;
            }
            case 48: {
                this.gameUi.initadditem();
                break;
            }
            case 47: {
                this.gameUi.initnewgamedata();
                break;
            }
            case 46: {
                this.gameUi.loadtempplayerdata();
                break;
            }
            case 45: {
                this.gameUi.savetempplayerdata();
                break;
            }
            case 44: {
                this.initface();
                break;
            }
            case 43: {
                this.c.setCameraRole(this.rolem.fplayerbox[0]);
                this.c.resetCamera();
                this.mapm.initWeatherdata();
                this.rolem.flushAllEnemy();
                this.rolem.initBatterNum();
                break;
            }
            case 41: {
                this.gameUi.saveGame();
                break;
            }
            case 40: {
                this.gameUi.playerimage();
                break;
            }
            case 39: {
                this.gameUi.loadGame();
                break;
            }
            case 38: {
                this.rolem.changeplayerimage();
                break;
            }
            case 36: {
                this.gameUi.initadditem();
                break;
            }
            case 0: {
                break;
            }
            case 1: {
                this.c = new Camera(this);
                break;
            }
            case 3: {
                this.menu = new GameMenu(this);
                break;
            }
            case 4: {
                this.menu = null;
                break;
            }
            case 5: {
                this.mapm = new MapManager(this);
                this.mapm.readuniverseinfo();
                break;
            }
            case 6: {
                this.mapm = null;
                break;
            }
            case 7: {
                this.rolem = new RoleManager(this);
                this.rolem.getroledata();
                break;
            }
            case 8: {
                this.gameUi = new GameUI(this);
                break;
            }
            case 9: {
                this.gameUi = null;
                break;
            }
            case 10: {
                this.mapm.readMapdata(this.mapm.nextMapIndex);
                this.mapm.readEventdata();
                this.initFlyer();
                this.c.setCamera(0, 0, 640, 360, this.mapm.mapW, this.mapm.mapH);
                this.mapm.readminimapdata();
                this.mapm.initminimap();
                this.mapm.getMapImage(this.mapm.mapIndex);
                this.rolem.setflushlist(this.mapm.mapIndex);
                this.gameUi.changeFightUiState((byte)0);
                this.gameUi.checkmission(0, this.mapm.mapIndex, (short)0);
                break;
            }
            case 11: {
                this.gameUi.readData();
                this.gameUi.initFightUi();
                this.gameUi.initVisualKeyboard();
                this.gameUi.initRolePack();
                this.rolem.initPlayerCurrentArm();
                break;
            }
            case 12: {
                this.rolem.initBox();
                break;
            }
            case 13: {
                this.rolem.initFightPlayer();
                this.rolem.startFight();
                break;
            }
            case 14: {
                this.gameUi.initForgeImg();
                break;
            }
            case 15: {
                this.rolem.createCurrentRoleres();
                break;
            }
            case 16: {
                this.rolem.initEffect();
                break;
            }
            case 18: {
                this.c.setCamera(0, 0, 640, 360, 640, 360);
                this.c.setCameraRole(this.rolem.fplayerbox[0]);
                this.c.resetCamera();
                break;
            }
            case 19: {
                this.rolem.fpaixuProcess();
                break;
            }
            case 20: {
                this.rolem.initNPC();
                this.rolem.initDoor();
                break;
            }
            case 21: {
                this.rolem.setPlayerPosbyDoor();
                this.c.setCameraRole(this.rolem.fplayerbox[0]);
                this.c.resetCamera();
                this.mapm.initWeatherdata();
                this.rolem.flushAllEnemy();
                this.rolem.initBatterNum();
                this.initFlush();
                break;
            }
            case 22: {
                if (this.rolem.isbossarea(this.mapm.mapIndex)) {
                    this.loadMusicIndex = (byte)6;
                    break;
                }
                this.loadMusicIndex = (byte)this.mapm.universeMapAreaData[this.mapm.maparea[this.mapm.mapIndex]][6];
                break;
            }
            case 53: {
                this.loadMusicIndex = 1;
                break;
            }
            case 51: {
                this.loadMusicIndex = 0;
                break;
            }
            case 52: {
                this.loadMusicIndex = 1;
                break;
            }
            case 23: {
                break;
            }
            case 24: {
                this.mapm.initUniverseMap();
                this.mapm.readEventdata();
                this.mapm.initcityicon();
                break;
            }
            case 25: {
                this.mapm.mapIndex = this.mapm.nextMapIndex;
                this.mapm.citybuildnum = this.mapm.citybuildpos[this.mapm.cityMapIndex].length;
                this.mapm.initCityMap(this.mapm.cityMapIndex);
                this.mapm.readEventdata();
                this.mapm.setCityArea();
                this.gameUi.changeFightUiState((byte)3);
                this.gameUi.checkmission(0, this.mapm.mapIndex, (short)0);
                this.mapm.initcityicon();
                break;
            }
            case 26: {
                this.mapm.cleanuniverseinfo();
                this.mapm.cleanUniverseMap();
                this.mapm.cleancityicon();
                break;
            }
            case 27: {
                this.mapm.cleanCityMap();
                this.gameUi.cleanForge();
                this.mapm.cleancityicon();
                break;
            }
            case 28: {
                this.mapm.clearFight();
                this.rolem.cleanCurrentEnemyData();
                break;
            }
            case 29: {
                break;
            }
            case 30: {
                this.rolem.changeweaponimages();
                break;
            }
            case 31: {
                this.mapm = null;
                this.rolem = null;
                this.gameUi = null;
                break;
            }
            case 32: {
                this.rolem.clearFPlayer();
                break;
            }
            case 33: {
                this.gameUi.newGame();
                break;
            }
            case 34: {
                this.rolem.initDoorImage();
                this.gameUi.createFightHeadImage();
                break;
            }
            case 35: {
                break;
            }
            case 37: {
                this.initpopnum();
            }
        }
    }

    public void loadLogic() {
        if (this.loadingTime < this.resdata[this.loadIndex].length) {
            this.createres(this.resdata[this.loadIndex][this.loadingTime]);
            this.loadingTime = (byte)(this.loadingTime + 1);
        } else {
            if (this.loadMusicIndex > -1) {
                this.createMusic(this.loadMusicIndex);
                this.sound.playMusic();
            }
            this.reset();
            switch (this.loadType) {
                case 0: {
                    break;
                }
                case 1: {
                    this.loadActionFrameIndex = 0;
                    this.loadFrameDelay = 0;
                    this.loadActionOver = false;
                }
            }
            this.changeGameState(this.newgamestate);
            switch (this.newgamestate) {
                case 5: 
                case 11: {
                    this.mapm.checkEvent(this.rolem.fplayerbox[0]);
                    this.rolem.fpaixuProcess();
                    break;
                }
                case 4: {
                    this.mapm.checkEvent(this.rolem.fplayerbox[0]);
                    break;
                }
                case 10: {
                    this.mapm.isuniverseflushinfo = true;
                    this.mapm.checkEvent(this.rolem.fplayerbox[0]);
                }
            }
            System.gc();
        }
    }

    private void initFonts(String path, int fontsSize) {
        switch (fontsSize) {
            case 12: {
                this.font_size = 12;
                this.ascSize = 12;
                this.hzkSize = 24;
                break;
            }
            case 16: {
                this.font_size = 16;
                this.ascSize = 16;
                this.hzkSize = 32;
            }
        }
        try {
            int i;
            DataInputStream dis = new DataInputStream(new Object().getClass().getResourceAsStream(path));
            this.asc12Index = new int[dis.readInt()];
            for (i = 0; i < this.asc12Index.length; ++i) {
                this.asc12Index[i] = dis.readInt();
            }
            this.hzk12Index = new int[dis.readInt()];
            for (i = 0; i < this.hzk12Index.length; ++i) {
                this.hzk12Index[i] = dis.readInt();
            }
            dis.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void openSwitchScrren() {
        for (int i = 0; i < this.screenData.length; ++i) {
            this.screenData[i] = -16777216;
        }
        this.switchScreenState = 1;
        this.isShowSwitchScreen = true;
    }

    public void startSwitchScrren(int type, byte gstate, int loadstate) {
        this.switchScreenType = type;
        this.switchScreenGameState = gstate;
        this.switchGameLoadState = loadstate;
        this.switchScreenShowTime = 0;
        this.switchScreenState = 0;
        this.loadActionFrameIndex = 0;
        this.loadFrameDelay = 0;
        this.loadActionOver = false;
        if (this.paintType == 1) {
            this.rolem.timeStopFinish();
        }
        switch (this.switchScreenType) {
            case 0: 
            case 3: {
                for (int i = 0; i < this.screenData.length; ++i) {
                    this.screenData[i] = 0;
                }
                break;
            }
            case 1: {
                for (int i = 0; i < this.screenData.length; ++i) {
                    this.screenData[i] = 0xFFFFFF;
                }
                break;
            }
            case 2: {
                for (int i = 0; i < this.screenData.length; ++i) {
                    this.screenData[i] = -16777216;
                }
                break;
            }
        }
        this.isShowSwitchScreen = true;
    }

    public void switchScreenProcess() {
        if (this.isShowSwitchScreen && this.gamestate != 1) {
            switch (this.switchScreenState) {
                case 0: {
                    int argb = 0;
                    switch (this.switchScreenType) {
                        case 0: {
                            argb = this.screenData[0];
                            argb += 0x22000000;
                            for (int i = 0; i < this.screenData.length; ++i) {
                                this.screenData[i] = argb;
                            }
                            if (++this.switchScreenShowTime != 7) break;
                            this.switchScreenShowTime = 0;
                            this.switchScreenState = 2;
                            break;
                        }
                        case 1: {
                            argb = this.screenData[0];
                            argb += 0x22FFFFFF;
                            for (int i = 0; i < this.screenData.length; ++i) {
                                this.screenData[i] = argb;
                            }
                            if (++this.switchScreenShowTime != 7) break;
                            this.switchScreenShowTime = 0;
                            this.switchScreenState = 2;
                            break;
                        }
                        case 2: {
                            this.switchScreenShowTime = 0;
                            this.switchScreenState = 2;
                            break;
                        }
                        case 3: {
                            int i;
                            argb = this.screenData[0];
                            argb += 0x22000000;
                            for (i = 0; i < this.screenData.length; ++i) {
                                this.screenData[i] = argb;
                            }
                            if (++this.switchScreenShowTime != 7) break;
                            for (i = 0; i < this.screenData.length; ++i) {
                                this.screenData[i] = -16777216;
                            }
                            this.switchScreenShowTime = 0;
                            this.isShowSwitchScreen = false;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (this.switchScreenType) {
                        case 0: {
                            int argb = this.screenData[0];
                            argb -= 0x22000000;
                            for (int i = 0; i < this.screenData.length; ++i) {
                                this.screenData[i] = argb;
                            }
                            if (++this.switchScreenShowTime != 7) break;
                            this.isShowSwitchScreen = false;
                            break;
                        }
                        case 1: {
                            int argb = this.screenData[0];
                            argb -= 0x22FFFFFF;
                            for (int i = 0; i < this.screenData.length; ++i) {
                                this.screenData[i] = argb;
                            }
                            if (++this.switchScreenShowTime != 7) break;
                            this.isShowSwitchScreen = false;
                            break;
                        }
                        case 2: {
                            int argb = this.screenData[0];
                            argb -= 0x22000000;
                            for (int i = 0; i < this.screenData.length; ++i) {
                                this.screenData[i] = argb;
                            }
                            if (++this.switchScreenShowTime != 7) break;
                            this.isShowSwitchScreen = false;
                            break;
                        }
                        case 3: {
                            int argb = this.screenData[0];
                            argb -= 0x22000000;
                            for (int i = 0; i < this.screenData.length; ++i) {
                                this.screenData[i] = argb;
                            }
                            if (++this.switchScreenShowTime != 7) break;
                            this.isShowSwitchScreen = false;
                        }
                    }
                    break;
                }
                case 2: {
                    block17 : switch (this.switchScreenGameState) {
                        case 4: {
                            switch (this.switchGameLoadState) {
                                case 0: {
                                    this.loadGameState(4, 3, (byte)this.switchScreenType);
                                    break;
                                }
                                case 1: {
                                    this.loadActionFrameIndex = 0;
                                    this.loadFrameDelay = 0;
                                    this.loadActionOver = false;
                                    this.mapm.setcityiconaction((byte)1);
                                    this.gameUi.upgradeCity(this.mapm.cityMapIndex);
                                    this.gameUi.changeFightUiState((byte)3);
                                    this.mapm.setcitybuildinfo(this.mapm.currentcitybuildname[this.mapm.cityBuildSelectIndex]);
                                    this.gameUi.initcityshownews(this.mapm.cityMapIndex);
                                    this.gameUi.checkmission(6, this.mapm.cityMapIndex, this.mapm.cityMapLevel[this.mapm.cityMapIndex]);
                                    this.changeGameState(4);
                                    break;
                                }
                                case 2: {
                                    this.loadGameState(4, 9, (byte)this.switchScreenType);
                                    break;
                                }
                                case 3: {
                                    this.rolem.currentplayernameindex = 1;
                                    this.mapm.nextMapIndex = (short)163;
                                    this.mapm.nextDoorIndex = 0;
                                    this.loadGameState(4, 16, (byte)this.switchScreenType);
                                    break;
                                }
                                case 4: {
                                    if (this.rolem.roleState > 0) {
                                        this.rolem.setPlayerRideDown();
                                        this.rolem.setPlayerState((byte)0);
                                    }
                                    this.mapm.nextMapIndex = (short)163;
                                    this.mapm.nextDoorIndex = 0;
                                    this.mapm.cityMapIndex = (byte)(this.mapm.nextMapIndex - 163);
                                    this.mapm.citybuildnum = this.mapm.citybuildpos[this.mapm.cityMapIndex].length;
                                    this.loadGameState(4, 15, (byte)this.switchScreenType);
                                }
                            }
                            break;
                        }
                        case 5: {
                            switch (this.switchGameLoadState) {
                                case 0: {
                                    this.loadGameState(5, 6, (byte)this.switchScreenType);
                                    break;
                                }
                                case 1: {
                                    this.mapm.readMapdata(this.mapm.nextMapIndex);
                                    this.mapm.readEventdata();
                                    this.rolem.setflushlist(this.mapm.mapIndex);
                                    this.initFlyer();
                                    this.c.setCamera(0, 0, 640, 360, this.mapm.mapW, this.mapm.mapH);
                                    this.rolem.clearBox();
                                    this.rolem.initNPC();
                                    this.rolem.initDoor();
                                    this.rolem.setPlayerPosbyDoor();
                                    this.c.resetCamera();
                                    this.rolem.flushAllEnemy();
                                    this.rolem.initBatterNum();
                                    this.initFlush();
                                    this.gameUi.checkmission(0, this.mapm.mapIndex, (short)0);
                                    this.mapm.checkEvent(this.rolem.fplayerbox[0]);
                                    break;
                                }
                                case 2: {
                                    this.loadGameState(5, 5, (byte)this.switchScreenType);
                                    break;
                                }
                                case 3: {
                                    this.loadGameState(5, 8, (byte)this.switchScreenType);
                                    break;
                                }
                                case 4: {
                                    this.gameUi.changeRide((byte)0);
                                    this.rolem.initRide(this.rolem.currentRideIndex);
                                    this.rolem.setPlayerState((byte)(this.rolem.currentRideIndex + 1));
                                    this.rolem.setPlayerRideOn();
                                    break;
                                }
                                case 5: {
                                    break;
                                }
                                case 6: {
                                    break;
                                }
                                case 7: {
                                    this.rolem.currentplayernameindex = (byte)4;
                                    this.mapm.nextMapIndex = (short)12;
                                    this.mapm.nextDoorIndex = 0;
                                    this.loadGameState(5, 13, (byte)this.switchScreenType);
                                    break;
                                }
                                case 8: {
                                    this.loadGameState(5, 12, (byte)this.switchScreenType);
                                    break;
                                }
                                case 9: {
                                    this.rolem.currentplayernameindex = 1;
                                    this.mapm.nextMapIndex = (short)119;
                                    this.mapm.nextDoorIndex = 0;
                                    this.loadGameState(5, 12, (byte)this.switchScreenType);
                                    break;
                                }
                                case 10: {
                                    this.rolem.currentplayernameindex = (byte)3;
                                    this.mapm.nextMapIndex = (short)120;
                                    this.mapm.nextDoorIndex = 0;
                                    this.loadGameState(5, 12, (byte)this.switchScreenType);
                                    break;
                                }
                                case 11: {
                                    this.rolem.currentplayernameindex = 1;
                                    this.mapm.nextMapIndex = (short)118;
                                    this.mapm.nextDoorIndex = 0;
                                    this.loadGameState(5, 12, (byte)this.switchScreenType);
                                    break;
                                }
                                case 12: {
                                    this.rolem.currentplayernameindex = (byte)4;
                                    this.mapm.nextMapIndex = (short)144;
                                    this.mapm.nextDoorIndex = 0;
                                    this.loadGameState(5, 13, (byte)this.switchScreenType);
                                    break;
                                }
                                case 13: {
                                    this.rolem.currentplayernameindex = 1;
                                    this.mapm.nextMapIndex = (short)120;
                                    this.mapm.nextDoorIndex = 0;
                                    this.loadGameState(5, 14, (byte)this.switchScreenType);
                                }
                            }
                            this.gameUi.changeFightUiState((byte)0);
                            break;
                        }
                        case 10: {
                            this.mapm.inituniverse();
                            this.mapm.mapIndex = this.mapm.nextMapIndex = (short)168;
                            switch (this.switchGameLoadState) {
                                case 0: {
                                    this.mapm.universeMapState = 0;
                                    this.mapm.universewaittime = 5;
                                    this.mapm.isdrawuniverselist = false;
                                    this.loadGameState(10, 4, (byte)this.switchScreenType);
                                    break block17;
                                }
                                case 1: {
                                    this.loadGameState(10, 2, (byte)this.switchScreenType);
                                    break block17;
                                }
                                case 2: {
                                    this.loadGameState(10, 1, (byte)this.switchScreenType);
                                }
                            }
                        }
                    }
                    this.switchScreenState = 1;
                }
            }
        }
    }

    public void drawSwitchScreen() {
        if (this.isShowSwitchScreen && this.gamestate != 1) {
            switch (this.switchScreenType) {
                case 0: 
                case 1: 
                case 3: {
                    for (int i = 0; i < 360; ++i) {
                        this.g.drawRGB(this.screenData, 0, 640, 0, i, 640, 1, true);
                    }
                    break;
                }
                case 2: {
                    for (int i = 0; i < 360; ++i) {
                        this.g.drawRGB(this.screenData, 0, 640, 0, i, 640, 1, true);
                    }
                    break;
                }
            }
        }
    }

    public void initwhitescreen() {
        this.isdrawwhite = true;
        this.whitescreentime = 0;
        for (int i = 0; i < this.screenData.length; ++i) {
            this.screenData[i] = -1;
        }
    }

    public void drawwhitescreen() {
        int argb = 0;
        if (this.isdrawwhite) {
            int i;
            for (i = 0; i < 360; ++i) {
                this.g.drawRGB(this.screenData, 0, 640, 0, i, 640, 1, true);
            }
            argb = this.screenData[0];
            argb -= 0x11000000;
            for (i = 0; i < this.screenData.length; ++i) {
                this.screenData[i] = argb;
            }
            if (++this.whitescreentime == 15) {
                this.isdrawwhite = false;
            }
        }
    }

    private void reset() {
        this.rectIndexW = 0;
        this.rectIndexH = 0;
    }

    public void drawChangeScreen(boolean open, int doorDir) {
        if (open) {
            for (int i = 0; i < 41; ++i) {
                block13: for (int j = 0; j < 23; ++j) {
                    switch (doorDir) {
                        case 0: {
                            this.fillRect(640 - (i + 1) * 16, 360 - (j + 1) * 16, 16, 16, 8 - this.rectIndexW + j / 2 + i / 2, 8 - this.rectIndexH + j / 2 + i / 2);
                            continue block13;
                        }
                        case 1: {
                            this.fillRect(i * 16, j * 16, 16, 16, 8 - this.rectIndexW + j / 2 + i / 2, 8 - this.rectIndexH + j / 2 + i / 2);
                            continue block13;
                        }
                        case 2: {
                            this.fillRect(640 - (i + 1) * 16, 360 - (j + 1) * 16, 16, 16, 8 - this.rectIndexW + i / 2 + j / 2, 8 - this.rectIndexH + i / 2 + j / 2);
                            continue block13;
                        }
                        case 3: {
                            this.fillRect(i * 16, j * 16, 16, 16, 8 - this.rectIndexW + i / 2 + j / 2, 8 - this.rectIndexH + i / 2 + j / 2);
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < 41; ++i) {
                block15: for (int j = 0; j < 23; ++j) {
                    switch (doorDir) {
                        case 0: {
                            this.fillRect(i * 16, j * 16, 16, 16, this.rectIndexW + j - this.rectOffset, this.rectIndexH + j - this.rectOffset);
                            continue block15;
                        }
                        case 1: {
                            this.fillRect(640 - (i + 1) * 16, 360 - (j + 1) * 16, 16, 16, this.rectIndexW + j - this.rectOffset, this.rectIndexH + j - this.rectOffset);
                            continue block15;
                        }
                        case 2: {
                            this.fillRect(i * 16, j * 16, 16, 16, this.rectIndexW + i - this.rectOffset, this.rectIndexH + i - this.rectOffset);
                            continue block15;
                        }
                        case 3: {
                            this.fillRect(640 - (i + 1) * 16, 360 - (j + 1) * 16, 16, 16, this.rectIndexW + i - this.rectOffset, this.rectIndexH + i - this.rectOffset);
                        }
                    }
                }
            }
        }
    }

    public void changeScreenProcess() {
        this.rectIndexW += 2;
        this.rectIndexH += 2;
    }

    private void fillRect(int x, int y, int w, int h, int indexX, int indexY) {
        this.g.setColor(0);
        this.g.fillRect(x + (w >> 1) - indexX, y + (h >> 1) - indexY, indexX * 2, indexY * 2);
    }

    public void createLoadImage() {
        Util.tempData = new byte[10000];
        Util.position[0] = 0;
        Util.getStream(Util.tempData, "/data/loading.data", 0);
        this.loadModules = Util.loadInfShort(Util.tempData, this.loadModules, Util.position);
        this.loadFrames = Util.loadInfShort(Util.tempData, this.loadFrames, Util.position);
        this.loadActions = Util.loadInfShort(Util.tempData, this.loadActions, Util.position);
        this.loadImage = new Image[1];
        this.loadImage[0] = Util.createImage("/ui/" + this.loadImgData[this.loadImgIndex] + ".png");
        Util.tempData = null;
    }

    public void createloadimage(byte index) {
        if (index != this.loadImgIndex) {
            this.loadImage[0] = null;
            this.loadImgIndex = index;
            this.loadImage[0] = Util.createImage("/ui/" + this.loadImgData[this.loadImgIndex] + ".png");
        }
    }

    public void drawStaticLoading(short frameindex, int x, int y) {
        for (int i = 0; i < this.loadFrames[frameindex].length; i += 4) {
            short moduleIndex = this.loadFrames[frameindex][i];
            this.drawModule(this.loadImage[this.loadModules[moduleIndex * 5]], this.loadFrames[frameindex][i + 1], (int)this.loadFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.loadFrames[frameindex][i + 3]], x, y, this.loadModules, (int)moduleIndex);
        }
    }

    public void drawLoadingSprite(byte actionIndex, int x, int y) {
        short frameindex = this.loadActions[actionIndex][this.loadActionFrameIndex + 1];
        short moduleIndex = 0;
        for (int i = 0; i < this.loadFrames[frameindex].length; i += 4) {
            moduleIndex = this.loadFrames[frameindex][i];
            this.drawModule(this.loadImage[this.loadModules[moduleIndex * 5]], this.loadFrames[frameindex][i + 1], (int)this.loadFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.loadFrames[frameindex][i + 3]], x, y, this.loadModules, (int)moduleIndex);
        }
        this.nextLoadingFrame(actionIndex);
    }

    public void nextLoadingFrame(byte actionIndex) {
        this.loadFrameDelay = (byte)(this.loadFrameDelay + 1);
        if (this.loadFrameDelay == this.loadActions[actionIndex][this.loadActionFrameIndex]) {
            this.loadFrameDelay = 0;
            this.loadActionFrameIndex = (byte)(this.loadActionFrameIndex + 2);
            if (this.loadActionFrameIndex == this.loadActions[actionIndex].length) {
                this.loadActionFrameIndex = 0;
                this.loadActionOver = true;
            }
        }
    }

    public void drawLoading() {
        switch (this.loadType) {
            case 0: 
            case 1: 
            case 2: {
                this.g.setColor(0);
                this.g.fillRect(0, 0, 640, 360);
                this.drawLoadingSprite((byte)0, 490, 180);
                if (this.loadingword == null) break;
                this.drawZkStringArry(this.loadingword, 320, 130, 17, 0, this.loadingword.length, 0xFFFFFF);
            }
        }
    }

    public void setloadingword(String word) {
        this.loadingword = null;
        if (this.zkData != null) {
            this.loadingword = this.formatzkword(word, 480, 0);
        }
    }

    private void drawSwitchSound() {
        this.fillRect(0, 0, 0, 640, 360);
        this.drawString("\u662f\u5426\u5f00\u542f\u58f0\u97f3", 320, 180, 33, 0xFFFFFF);
        this.drawString("\u662f", 5, 355, 36, 0xFFFFFF);
        this.drawString("\u5426", 635, 355, 40, 0xFFFFFF);
    }

    private void switchSoundKeyProcess() {
        if (Control.IsKeyDown(65536)) {
            this.sound.volume = 2;
            this.loadGameState(3, 0, (byte)0);
        } else if (Control.IsKeyDown(131072)) {
            this.sound.volume = 0;
            this.loadGameState(3, 0, (byte)0);
        }
    }

    protected void hideNotify() {
        Control.ClearKey();
        if (this.gamestate != 8) {
            this.isPause = true;
        }
    }

    private void drawPause() {
        this.fillRect(0, 0, 0, 640, 360);
        this.drawString("\u6e38\u620f\u6682\u505c", 320, 180, 33, 0xFFFFFF);
        this.drawString("\u8fd4\u56de", 630, 350, 40, 0xFFFFFF);
    }

    private void pauseKeyProcess() {
        if (Control.IsKeyDown(131072)) {
            this.gamestate = this.pausestate;
            this.createMusic(this.musicIndex);
            this.sound.setVolume();
            this.sound.playMusic();
        }
    }

    public void drawMapCollidedata() {
        int i;
        this.g.setColor(0xFF0000);
        for (i = 0; i < this.mapm.collidedata.length; i = (int)((byte)(i + 1))) {
            this.g.drawRect((int)this.mapm.collidedata[i][0], (int)this.mapm.collidedata[i][1], (int)this.mapm.collidedata[i][2], (int)this.mapm.collidedata[i][3]);
        }
        this.g.setColor(0xFFFFFF);
        for (i = 0; i < this.mapm.flushEnemyRect.length; i = (int)((byte)(i + 1))) {
            this.g.drawRect((int)this.mapm.flushEnemyRect[i][0], (int)this.mapm.flushEnemyRect[i][1], (int)this.mapm.flushEnemyRect[i][2], (int)this.mapm.flushEnemyRect[i][3]);
        }
    }

    private void initFlyer() {
        if (this.mapm.flyerBoxNum[this.mapm.mapImageData[this.mapm.mapIndex]] == -1) {
            this.flyer = null;
        } else {
            this.flyer = new Flyer[this.mapm.flyerBoxNum[this.mapm.mapImageData[this.mapm.mapIndex]]];
            for (int j = 0; j < this.flyer.length; ++j) {
                this.flyer[j] = new Flyer(this);
                this.flyer[j].flyerIndex = this.mapm.flyerData[this.mapm.mapImageData[this.mapm.mapIndex]][Util.getARandomInt(this.mapm.flyerData[this.mapm.mapImageData[this.mapm.mapIndex]].length)];
            }
        }
    }

    private void drawFlyer() {
        if (this.flyer != null) {
            for (int i = 0; i < this.flyer.length; ++i) {
                this.flyer[i].drawFlyer();
            }
        }
    }

    private void flyerProcess() {
        if (this.flyer != null) {
            for (int i = 0; i < this.flyer.length; ++i) {
                this.flyer[i].flyerProcess();
            }
        }
    }

    public void drawFight() {
        if (this.eventBackState > 0) {
            this.g.translate(0, -this.eventBackH * 2);
        }
        if (this.isFlush) {
            this.drawFlush();
            this.g.translate(-this.c.getX(), -this.c.getY());
            this.rolem.drawfspritebox1();
            this.mapm.drawWeather(1, this.rolem.fplayerbox[0]);
            this.rolem.drawfspritebox();
            this.mapm.drawWeather(2, this.rolem.fplayerbox[0]);
            this.gameUi.drawKey5();
            this.g.translate(this.c.getX(), this.c.getY());
        } else {
            this.mapm.fillBack();
            this.camerax = this.c.getX();
            this.cameray = this.c.getY();
            this.c.x = this.c.x * this.farPercent / 3;
            this.c.y = this.c.y * this.farPercent / 3;
            this.g.translate(-this.c.getX(), -this.c.getY());
            this.mapm.drawMapBack(0);
            this.g.translate(this.c.getX(), this.c.getY());
            this.c.x = this.camerax;
            this.c.y = this.cameray;
            this.c.x = this.c.x * this.middlePercent / 3;
            this.c.y = this.c.y * this.middlePercent / 3;
            this.g.translate(-this.c.getX(), -this.c.getY());
            this.mapm.drawMapBack(1);
            this.drawFlyer();
            this.g.translate(this.c.getX(), this.c.getY());
            this.c.x = this.camerax;
            this.c.y = this.cameray;
            this.g.translate(-this.c.getX(), -this.c.getY());
            this.mapm.drawWeather(0, this.rolem.fplayerbox[0]);
            this.rolem.drawfspritebox1();
            this.mapm.drawMapBack(2);
            if (this.mapm.isdrawshadow(this.mapm.mapIndex)) {
                this.rolem.drawfspriteshadow();
                this.mapm.drawMapBack(3);
            }
            this.rolem.drawDoor();
            this.mapm.drawWeather(1, this.rolem.fplayerbox[0]);
            this.rolem.drawfspritebox();
            this.gameUi.drawchangeweaponicon(this.rolem.fplayerbox[0]);
            this.gameUi.nextskilliconframe(this.gameUi.actionIndex);
            this.gameUi.drawKey5();
            this.mapm.drawWeather(2, this.rolem.fplayerbox[0]);
            this.g.translate(this.c.getX(), this.c.getY());
        }
        if (this.eventBackState > 0) {
            this.g.translate(0, this.eventBackH * 2);
        }
        if (!this.mapm.isEventPlaying) {
            this.rolem.drawBatterNum();
            this.drawwhitescreen();
        }
    }

    private void fightKeyProcess() {
        this.rolem.keyProcess(this.rolem.fplayerbox[0]);
    }

    private void fightProcess() {
        if (this.isShowSwitchScreen) {
            if (this.mapm.isEventPlaying || this.mapm.isTeachPlayering) {
                this.eventBackProcess();
                this.rolem.fEventProcess();
                this.c.eventCameraProcess();
            } else {
                this.rolem.fProcess();
                this.rolem.flushEnemyProcess();
                this.gameUi.fightUiProcess();
                this.c.cameraProcess();
            }
        } else {
            this.mapm.eventProcess(this.rolem.fplayerbox[0]);
            if (this.mapm.isEventPlaying || this.mapm.isTeachPlayering) {
                this.eventBackProcess();
                this.rolem.fEventProcess();
                this.c.eventCameraProcess();
            } else {
                this.rolem.fProcess();
                this.rolem.flushEnemyProcess();
                this.gameUi.fightUiProcess();
                this.c.cameraProcess();
            }
        }
    }

    private void drawEventBack() {
        if (this.eventBackState > 0) {
            this.fillRect(0, 0, 0, 640, this.eventBackH);
            this.fillRect(0, 0, 360 - this.eventBackH * 2, 640, this.eventBackH * 3);
            if (this.eventBackState == 3) {
                if (this.publiccounter % 10 == 0) {
                    this.g.drawRegion(this.gameUi.playImage, 0, 1, 7, 11, 0, 600, 10, 10);
                }
                this.g.drawRegion(this.gameUi.playImage, 10, 0, 25, 13, 0, 630, 10, 10);
            }
        }
    }

    private void eventBackProcess() {
        switch (this.eventBackState) {
            case 0: {
                break;
            }
            case 1: {
                this.eventBackH += 3;
                this.eventBackH = this.eventBackH > 20 ? 20 : this.eventBackH;
                break;
            }
            case 2: {
                this.eventBackH -= 5;
                this.eventBackH = this.eventBackH < 0 ? 0 : this.eventBackH;
                break;
            }
        }
    }

    public void showBossDeath() {
        this.bossDeathType = 0;
        this.bossDeathTime = 0;
        this.changeGameState(9);
    }

    public void drawBossDeath() {
        this.mapm.fillBack();
        this.camerax = this.c.getX();
        this.cameray = this.c.getY();
        this.c.x /= 3;
        this.c.y /= 3;
        this.g.translate(-this.c.getX(), -this.c.getY());
        this.mapm.drawMapBack(0);
        this.g.translate(this.c.getX(), this.c.getY());
        this.c.x = this.camerax;
        this.c.y = this.cameray;
        this.c.x = this.c.x * 2 / 3;
        this.c.y = this.c.y * 2 / 3;
        this.g.translate(-this.c.getX(), -this.c.getY());
        this.mapm.drawMapBack(1);
        this.drawFlyer();
        this.g.translate(this.c.getX(), this.c.getY());
        this.c.x = this.camerax;
        this.c.y = this.cameray;
        this.g.translate(-this.c.getX(), -this.c.getY());
        this.rolem.drawfspriteframe1();
        this.mapm.drawMapBack(2);
        this.rolem.drawfspriteframe();
        this.g.translate(this.c.getX(), this.c.getY());
    }

    public void bossDeathProcess() {
        this.paintType = 1;
        if (this.bossDeathTime < 5) {
            this.bossDeathTime = (byte)(this.bossDeathTime + 1);
        } else {
            this.paintType = 0;
            this.startFlush();
            this.changeGameState(5);
        }
    }

    public void drawGameSet() {
        switch (this.gamestate) {
            case 7: {
                this.gameUi.drawStaticUiBack((short)0);
                break;
            }
            case 3: {
                this.menu.drawStaticUi((short)4);
                this.menu.drawextraInfo();
                this.gameUi.drawUiBack();
            }
        }
        this.gameUi.drawStaticUi((short)82);
        this.gameUi.drawUiModule(272, GameUI.LABEL_X[0][0] - 3, 43, 36, 0);
        this.gameUi.drawStaticUi((short)24);
        this.gameUi.drawStaticUi((short)16, 0, 360);
        this.fillRect(0xFFFFFF, 289, 145, 132 * this.sound.volume / 4, 13);
        this.drawColorString("\u5de6\u53f3\u6ed1\u52a8\u5c4f\u5e55\u8c03\u8282\u97f3\u91cf", 320, 220, 17, 15597319, 0);
    }

    public void gameSetKeyProcess() {
        if (Control.IsKeyDown(16400) || Control.IsKeyHold(16400, 10)) {
            this.sound.volume = --this.sound.volume < 0 ? 0 : this.sound.volume;
            this.createMusic(this.musicIndex);
            this.sound.setVolume();
            this.sound.playMusic();
        } else if (Control.IsKeyDown(32832) || Control.IsKeyHold(32832, 10)) {
            this.sound.volume = ++this.sound.volume > 4 ? 4 : this.sound.volume;
            this.createMusic(this.musicIndex);
            this.sound.setVolume();
            this.sound.playMusic();
        } else if (Control.IsKeyDown(65536)) {
            if (this.gamestate == 7) {
                this.gameUi.changeUiState((byte)0);
            } else if (this.gamestate == 3) {
                this.menu.changeMenuState((byte)1);
            }
        } else if (Control.IsKeyDown(131072)) {
            if (this.gamestate == 7) {
                this.gameUi.changeUiState((byte)0);
            } else if (this.gamestate == 3) {
                this.menu.changeMenuState((byte)1);
            }
        }
    }

    public void gethelpinfo() {
        String[] title = new String[]{"\u6e38\u620f\u64cd\u4f5c", "\u6389\u843d\u5217\u8868", "\u654c\u4eba\u5206\u5e03"};
        String info = " \u7cfb\u7edf\u548c\u57ce\u5e02\uff1a| \u70b9\u51fb\u89e6\u6478\u5c4f\u5de6\u4e0b\u89d2\u6253\u5f00\u7cfb\u7edf\u83dc\u5355\u3001\u6253\u5f00\u9009\u9879| \u70b9\u51fb\u89e6\u6478\u5c4f\u53f3\u4e0b\u89d2\u6253\u5f00\u6e38\u620f\u83dc\u5355\u3001\u8fd4\u56de| | \u6218\u6597\uff1a| \u70b9\u51fb\u5c4f\u5e55\u5de6\u4fa7\u7684\u4e0a\u4e0b\u5de6\u53f3\u56fe\u6807\u79fb\u52a8| \u53cc\u51fb\u5c4f\u5e55\u5de6\u4fa7\u7684\u5de6\u53f3\u56fe\u6807\u51b2\u523a| \u70b9\u51fb\u5c4f\u5e55\u53f3\u4fa7\u7684\u6b66\u5668\u653b\u51fb\u56fe\u6807\u653b\u51fb| \u70b9\u51fb\u5c4f\u5e55\u56fe\u6807 1 \u5207\u6362\u6b66\u5668| \u70b9\u51fb\u5c4f\u5e55\u56fe\u6807 3 \u4f7f\u7528\u67aa\u68b0| \u70b9\u51fb\u5c4f\u5e55\u56fe\u6807 7\u30019 \u53d1\u52a8\u6280\u80fd| \u70b9\u51fb\u5c4f\u5e55\u56fe\u6807 * \u67e5\u770b\u4efb\u52a1\u5217\u8868| \u70b9\u51fb\u5c4f\u5e55\u56fe\u6807 # \u4e0a\u4e0b\u5750\u9a91| \u70b9\u51fb\u5c4f\u5e55\u56fe\u6807 0 \u67e5\u770b\u5730\u56fe| \u70b9\u51fb\u5c4f\u5e55\u53f3\u4e0a\u65b9\u7b2c\u4e00\u4e2a\u56fe\u6807\u5f00\u542f\u7cfb\u7edf\u83dc\u5355| \u70b9\u51fb\u5c4f\u5e55\u53f3\u4e0a\u65b9\u7b2c\u4e8c\u4e2a\u56fe\u6807\u5f00\u542f\u6e38\u620f\u83dc\u5355| |\u7279\u522b\u63d0\u793a\uff1a|\u672c\u6e38\u620f\u53ea\u652f\u6301\u6a2a\u5c4f\u548c\u89e6\u5c4f\u64cd\u4f5c\u3002";
        this.helpinfo = this.formatzkword(info, 580, 0);
        this.helptitle = title;
    }

    public void inithelp() {
        this.gethelpinfo();
        this.helpstartLine = 0;
        this.helpshowtotal = this.helpinfo.length;
    }

    private void setenemymaterial() {
        int i;
        int total = 0;
        for (i = 0; i < this.rolem.dropItemData[this.helpenemylist[this.gamehelpenemyselectindex]].length; i = (int)((byte)(i + 2))) {
            if (this.rolem.dropItemData[this.helpenemylist[this.gamehelpenemyselectindex]][i] <= -1) continue;
            total = (byte)(total + 1);
        }
        byte[] tempdata = new byte[total];
        for (i = 0; i < total; i = (int)((byte)(i + 1))) {
            tempdata[i] = this.rolem.dropItemData[this.helpenemylist[this.gamehelpenemyselectindex]][i * 2];
        }
        this.enemymaterialdata = tempdata;
        this.enemymaterialtotal = (byte)total;
        this.gamehelpmaterialstartindex = 0;
        this.gamehelpmaterialselectindex = 0;
        this.gameUi.scroll.setscroll(this.enemypos[this.gamehelpenemyselectindex], 131, 89, 380, 50, 1, 20);
    }

    public void drawGameHelp() {
        switch (this.gamestate) {
            case 7: {
                this.gameUi.drawStaticUiBack((short)0);
                break;
            }
            case 3: {
                this.menu.drawStaticUi((short)4);
                this.menu.drawextraInfo();
                this.gameUi.drawUiBack();
            }
        }
        this.gameUi.drawStaticUi((short)82);
        this.gameUi.drawUiModule(267, GameUI.LABEL_X[0][0] - 7, 42, 36, 0);
        switch (this.gamehelpstate) {
            case 0: {
                this.gameUi.drawStaticUi((short)16, 0, 360);
                for (int i = 0; i < 2; ++i) {
                    if (this.gamehelpselectindex == i) {
                        this.gameUi.drawStaticUi((short)85, 320, 80 + i * 70);
                        this.drawColorString(this.gamehelpname[i], 320, 107 + i * 70, 17, 15597319, 0);
                        continue;
                    }
                    this.gameUi.drawStaticUi((short)86, 320, 80 + i * 70);
                    this.drawColorString(this.gamehelpname[i], 320, 107 + i * 70, 17, 10333123, 0);
                }
                break;
            }
            case 1: {
                this.gameUi.drawStaticUi((short)84, 0, 360);
                this.drawColorString(this.helptitle[0], 320, 45, 17, 0xFFFFFF, 0);
                this.drawZkStringArry(this.helpinfo, 30, 80, 20, this.helpstartLine, 11, 0);
                if (this.helpshowtotal <= 11) break;
                this.gameUi.drawStaticUi((short)41);
                this.gameUi.drawhelpjindutiaoV(8, 88, 8, 189, this.helpstartLine, this.helpshowtotal, (byte)11);
                break;
            }
            case 2: {
                this.gameUi.drawStaticUi((short)119);
                this.gameUi.drawStaticUi((short)84, 0, 360);
                this.gameUi.drawlabel("\u51fa\u73b0\u5730\u56fe", 132, 67, 4, 0);
                this.gameUi.drawDefaultModule(this.gameUi.defaultindex[this.rolem.roledata[this.helpenemylist[this.gamehelpenemyselectindex]][17]], 580, 69, 24, 0);
                this.gameUi.drawpacknum(this.gamehelpenemyselectindex + 1, this.helpenemylisttotal);
                this.g.drawImage(this.gameUi.fightEnemyHeadImage[this.gameUi.headImageIndex[this.helpenemylist[this.gamehelpenemyselectindex]]], 85, 130, 33);
                this.drawColorString(this.rolem.rolename[this.helpenemylist[this.gamehelpenemyselectindex]], 85, 69, 17, 15597319, 0);
                this.gameUi.scroll.drawscroll();
                for (int i = 0; i < 9; ++i) {
                    if (i >= this.enemymaterialtotal) continue;
                    if (i == this.gamehelpmaterialselectindex) {
                        this.gameUi.drawStaticUi((short)66, 24 + i % 3 * 203, 162 + i / 3 * 54);
                    } else {
                        this.gameUi.drawStaticUi((short)67, 25 + i % 3 * 203, 162 + i / 3 * 54);
                    }
                    this.gameUi.drawarmicon(this.enemymaterialdata[this.gamehelpmaterialstartindex + i], 26 + i % 3 * 203, 160 + i / 3 * 54);
                    if (i == this.gamehelpmaterialselectindex) {
                        this.gameUi.drawitemiconcheck(25 + i % 3 * 203, 160 + i / 3 * 54, this.enemymaterialdata[this.gamehelpmaterialstartindex + i]);
                        this.drawColorString(this.gameUi.iteminfo[this.enemymaterialdata[this.gamehelpmaterialstartindex + i]][0], 145 + i % 3 * 203, 175 + i / 3 * 54, 17, 15597319, 0);
                        continue;
                    }
                    this.gameUi.drawitemiconmembrane(25 + i % 3 * 203, 160 + i / 3 * 54, this.enemymaterialdata[this.gamehelpmaterialstartindex + i]);
                    this.drawColorString(this.gameUi.iteminfo[this.enemymaterialdata[this.gamehelpmaterialstartindex + i]][0], 145 + i % 3 * 203, 175 + i / 3 * 54, 17, 10333123, 0);
                }
                break;
            }
        }
    }

    public void helpKeyProcess() {
        switch (this.gamehelpstate) {
            case 0: {
                if (Control.IsKeyDown(4100) || Control.IsKeyHold(4100, 10)) {
                    this.gamehelpselectindex = (byte)(this.gamehelpselectindex - 1);
                    this.gamehelpselectindex = this.gamehelpselectindex < 0 ? (byte)1 : this.gamehelpselectindex;
                    break;
                }
                if (Control.IsKeyDown(8448) || Control.IsKeyHold(8448, 10)) {
                    this.gamehelpselectindex = (byte)(this.gamehelpselectindex + 1);
                    this.gamehelpselectindex = this.gamehelpselectindex > 1 ? (byte)0 : this.gamehelpselectindex;
                    break;
                }
                if (Control.IsKeyDown(327712)) {
                    switch (this.gamehelpselectindex) {
                        case 0: {
                            this.inithelp();
                            this.gamehelpstate = 1;
                            break;
                        }
                        case 1: {
                            this.gamehelpstate = (byte)2;
                            this.gamehelpenemyselectindex = 0;
                            this.setenemymaterial();
                        }
                    }
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                this.gameUi.scroll.close();
                switch (this.gamestate) {
                    case 3: {
                        this.menu.changeMenuState(this.menu.lastMenuState);
                        break;
                    }
                    case 7: {
                        if (this.lastgamestate == 10) {
                            this.changeGameState(this.lastgamestate);
                            break;
                        }
                        this.gameUi.changeUiState((byte)0);
                    }
                }
                break;
            }
            case 1: {
                if (Control.IsKeyDown(4100) || Control.IsKeyHold(4100, 10)) {
                    this.helpstartLine = (byte)(this.helpstartLine - 1);
                    this.helpstartLine = this.helpstartLine < 0 ? (byte)0 : this.helpstartLine;
                    break;
                }
                if (Control.IsKeyDown(8448) || Control.IsKeyHold(8448, 10)) {
                    byte temp = (byte)(this.helpinfo.length - 11);
                    temp = temp < 0 ? (byte)0 : temp;
                    this.helpstartLine = (byte)(this.helpstartLine + 1);
                    this.helpstartLine = this.helpstartLine > temp ? temp : this.helpstartLine;
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                this.gamehelpstate = 0;
                this.helptitle = null;
                this.helpinfo = null;
                break;
            }
            case 2: {
                if (Control.IsKeyDown(16400) || Control.IsKeyHold(16400, 10)) {
                    this.gamehelpenemyselectindex = (byte)(this.gamehelpenemyselectindex - 1);
                    this.gamehelpenemyselectindex = (byte)(this.gamehelpenemyselectindex < 0 ? this.helpenemylist.length - 1 : this.gamehelpenemyselectindex);
                    this.setenemymaterial();
                    break;
                }
                if (Control.IsKeyDown(32832) || Control.IsKeyHold(32832, 10)) {
                    this.gamehelpenemyselectindex = (byte)(this.gamehelpenemyselectindex + 1);
                    this.gamehelpenemyselectindex = this.gamehelpenemyselectindex > this.helpenemylist.length - 1 ? (byte)0 : this.gamehelpenemyselectindex;
                    this.setenemymaterial();
                    break;
                }
                if (Control.IsKeyDown(4100) || Control.IsKeyHold(4100, 10)) {
                    if (this.gamehelpmaterialselectindex > 0) {
                        this.gamehelpmaterialselectindex = (byte)(this.gamehelpmaterialselectindex - 1);
                        break;
                    }
                    if (this.gamehelpmaterialstartindex <= 0) break;
                    this.gamehelpmaterialstartindex = (byte)(this.gamehelpmaterialstartindex - 1);
                    break;
                }
                if (Control.IsKeyDown(8448) || Control.IsKeyHold(8448, 10)) {
                    int total;
                    int n = total = this.enemymaterialtotal < 9 ? (int)this.enemymaterialtotal : 9;
                    if (this.gamehelpmaterialselectindex < total - 1) {
                        this.gamehelpmaterialselectindex = (byte)(this.gamehelpmaterialselectindex + 1);
                        break;
                    }
                    if (this.gamehelpmaterialstartindex >= this.enemymaterialtotal - total) break;
                    this.gamehelpmaterialstartindex = (byte)(this.gamehelpmaterialstartindex + 1);
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                this.gamehelpstate = 0;
            }
        }
    }

    public void getaboutinfo() {
        String info = "\u5317\u4eac\u534e\u5a31\u65e0\u7ebf\u79d1\u6280\u6709\u9650\u516c\u53f8\u4e3a\u300a\u661f\u9645\u4f20\u8bf4-\u547d\u8fd0\u8f6e\u56de(\u534e\u5a31)\u300b\u6e38\u620f\u7684\u8f6f\u4ef6\u8457\u4f5c\u6743\u4eba\u3002\u5317\u4eac\u534e\u5a31\u65e0\u7ebf\u79d1\u6280\u6709\u9650\u516c\u53f8\u6388\u6743\u65b0\u6d6a\u65e0\u7ebf\u5728\u4e2d\u56fd\u5927\u9646\u4ece\u4e8b\u672c\u6e38\u620f\u7684\u5546\u4e1a\u8fd0\u8425\u3002\u65b0\u6d6a\u65e0\u7ebf\u540c\u65f6\u8d1f\u8d23\u5904\u7406\u672c\u6e38\u620f\u8fd0\u8425\u7684\u76f8\u5173\u5ba2\u6237\u670d\u52a1\u53ca\u6280\u672f\u652f\u6301\u3002|\u5ba2\u670d\u7535\u8bdd\uff1a4006900000|\u5ba2\u670dEMAIL\uff1a|bjkf@staff.sina.com.cn|\u5f00\u53d1\u5546\uff1a\u534e\u5a31\u65e0\u7ebf";
        this.aboutinfo = this.formatzkword(info, 200, 0);
    }

    public void initabout() {
        this.getaboutinfo();
        this.aboutIndex = 0;
        this.aboutstartLine = 0;
        this.aboutshowtotal = this.aboutinfo.length;
    }

    public void drawGameAbout() {
        this.menu.drawStaticUi((short)4);
        this.menu.drawextraInfo();
        this.gameUi.drawUiBack();
        this.gameUi.drawStaticUi((short)82);
        this.gameUi.drawUiModule(395, GameUI.LABEL_X[0][0], 42, 36, 0);
        this.gameUi.drawStaticUi((short)84, 0, 360);
        this.drawZkStringArry(this.aboutinfo, 30, 65, 20, this.aboutstartLine, 12, 0);
        if (this.aboutshowtotal > 12) {
            this.gameUi.drawStaticUi((short)41);
            this.gameUi.drawhelpjindutiaoV(8, 88, 8, 189, this.aboutstartLine, this.aboutshowtotal, (byte)12);
        }
    }

    public void aboutKeyProcess() {
        if (Control.IsKeyDown(4100) || Control.IsKeyHold(4100, 10)) {
            this.aboutstartLine = (byte)(this.aboutstartLine - 1);
            this.aboutstartLine = this.aboutstartLine < 0 ? (byte)0 : this.aboutstartLine;
        } else if (Control.IsKeyDown(8448) || Control.IsKeyHold(8448, 10)) {
            byte temp = (byte)(this.aboutinfo.length - 12);
            temp = temp < 0 ? (byte)0 : temp;
            this.aboutstartLine = (byte)(this.aboutstartLine + 1);
            this.aboutstartLine = this.aboutstartLine > temp ? temp : this.aboutstartLine;
        } else if (Control.IsKeyDown(65536)) {
            this.menu.changeMenuState(this.menu.lastMenuState);
        } else if (Control.IsKeyDown(131072)) {
            this.menu.changeMenuState(this.menu.lastMenuState);
        }
    }

    public void initpopnum() {
        Util.tempData = new byte[10000];
        Util.position[0] = 0;
        Util.getStream(Util.tempData, "/data/popnum.data", 0);
        this.popnummodules = Util.loadInfShort(Util.tempData, this.popnummodules, Util.position);
        Util.tempData = null;
        Util.tempData = new byte[10000];
        Util.position[0] = 0;
        Util.getStream(Util.tempData, "/data/batternum.data", 0);
        this.batterNummodules = Util.loadInfShort(Util.tempData, this.batterNummodules, Util.position);
        this.batterNumframes = Util.loadInfShort(Util.tempData, this.batterNumframes, Util.position);
        this.betterNumactions = Util.loadInfShort(Util.tempData, this.betterNumactions, Util.position);
        Util.tempData = null;
        Util.tempData = new byte[10000];
        Util.position[0] = 0;
        Util.getStream(Util.tempData, "/data/debuff.data", 0);
        this.debuffmodules = Util.loadInfShort(Util.tempData, this.debuffmodules, Util.position);
        this.debuffframes = Util.loadInfShort(Util.tempData, this.debuffframes, Util.position);
        this.debuffactions = Util.loadInfShort(Util.tempData, this.debuffactions, Util.position);
        Util.tempData = null;
        this.popnumimgs = new Image[1];
        this.popnumimgs[0] = Util.createImage("/ui/popnum.png");
        this.batterNumImages = new Image[2];
        this.batterNumImages[0] = Util.createImage("/ui/batternum.png");
        this.batterNumImages[1] = Util.createImage("/ui/batternum1.png");
        this.debuffImages = new Image[1];
        this.debuffImages[0] = Util.createImage("/ui/debuff.png");
    }

    public void initface() {
        int i;
        Util.tempData = new byte[10000];
        Util.position[0] = 0;
        Util.getStream(Util.tempData, "/data/face.data", 0);
        this.facemodules = Util.loadInfShort(Util.tempData, this.facemodules, Util.position);
        this.faceframes = Util.loadInfShort(Util.tempData, this.faceframes, Util.position);
        this.faceactions = Util.loadInfShort(Util.tempData, this.faceactions, Util.position);
        Util.tempData = null;
        this.faceimages = new Image[1];
        Util.tempData = new byte[10000];
        Util.position[0] = 0;
        Util.getStream(Util.tempData, "/data/skillicon.data", 0);
        this.skilliconmodules = Util.loadInfShort(Util.tempData, this.skilliconmodules, Util.position);
        this.skilliconframes = Util.loadInfShort(Util.tempData, this.skilliconframes, Util.position);
        this.skilliconactions = Util.loadInfShort(Util.tempData, this.skilliconactions, Util.position);
        Util.tempData = null;
        Util.tempData = new byte[10000];
        Util.position[0] = 0;
        Util.getStream(Util.tempData, "/data/itemicon.data", 0);
        this.itemiconmodules = Util.loadInfShort(Util.tempData, this.itemiconmodules, Util.position);
        Util.tempData = null;
        Util.tempData = new byte[10000];
        Util.position[0] = 0;
        Util.getStream(Util.tempData, "/data/dropitemaction.data", 0);
        this.dropitemmodules = Util.loadInfShort(Util.tempData, this.dropitemmodules, Util.position);
        this.dropitemframes = Util.loadInfShort(Util.tempData, this.dropitemframes, Util.position);
        this.dropitemactions = Util.loadInfShort(Util.tempData, this.dropitemactions, Util.position);
        this.dropitembeatt = Util.loadInfShort(Util.tempData, this.dropitembeatt, Util.position);
        Util.tempData = null;
        this.faceimages = new Image[1];
        this.faceimages[0] = Util.createImage("/ui/face.png");
        this.skilliconimage = new Image[2];
        for (i = 0; i < this.skilliconimage.length; ++i) {
            this.skilliconimage[i] = Util.createImage("/ui/skillicon" + i + ".png");
        }
        this.itemiconimages = new Image[this.itemicondata.length];
        for (i = 0; i < this.itemiconimages.length; ++i) {
            this.itemiconimages[i] = Util.createImage("/ui/itemicon" + i + ".png");
        }
        this.dropitemimages = new Image[1];
        this.dropitemimages[0] = Util.createImage("/ui/dropitem.png");
        this.defaultimages = new Image[1];
        this.defaultimages[0] = Util.createImage("/ui/defaulttype.png");
    }

    protected void keyPressed(int keyCode) {
        Control.keyPressed(keyCode);
    }

    protected void keyReleased(int keyCode) {
        Control.keyReleased(keyCode);
    }

    private void startGame() {
        this.isRunning = true;
        new Thread(this).start();
    }

    public void exitGame() {
        this.isRunning = false;
    }

    private void destroyGame() {
        this.sound.closeMusic();
        this.midlet.destroyApp(true);
    }

    public void drawBotton(byte i) {
        this.g.setColor(0xFFFFFF);
        switch (i) {
            case 0: {
                this.g.drawString("\u786e\u5b9a", 0, 360, 36);
                break;
            }
            case 1: {
                this.g.drawString("\u8fd4\u56de", 640, 360, 40);
            }
        }
    }

    public byte getTrans(int trans0, int trans1) {
        return this.transtable[trans0][trans1];
    }

    public void drawFModule(Image image, int x, int y, int anchor, int trans, int x1, int y1, int faceto, short[] modu, int i) {
        if (faceto == -1) {
            trans = this.getTrans(trans, 2);
            this.modulex = x1 - x - this.getModuleW(trans, modu[i * 5 + 3], modu[i * 5 + 4]);
            this.moduley = y1 + y;
            this.drawImage(image, this.modulex, this.moduley, anchor, trans, modu, i);
        } else {
            this.modulex = x1 + x;
            this.moduley = y1 + y;
            this.drawImage(image, this.modulex, this.moduley, anchor, trans, modu, i);
        }
    }

    public void drawFShadow(Image image, int x, int y, int anchor, int trans, int x1, int y1, int faceto, short[] modu, int i) {
        if (faceto == -1) {
            trans = this.getTrans(trans, 3);
            this.modulex = x1 - x - this.getModuleW(trans, modu[i * 5 + 3], modu[i * 5 + 4]);
            this.moduley = y1 - y - this.getModuleH(trans, modu[i * 5 + 3], modu[i * 5 + 4]);
            this.drawImage(image, this.modulex, this.moduley, anchor, trans, modu, i);
        } else {
            trans = this.getTrans(trans, 1);
            this.modulex = x1 + x;
            this.moduley = y1 - y - this.getModuleH(trans, modu[i * 5 + 3], modu[i * 5 + 4]);
            this.drawImage(image, this.modulex, this.moduley, anchor, trans, modu, i);
        }
    }

    public void drawModule(Image image, short[] modules, int mindex, int x, int y, int anchor, int trans, int x1, int y1) {
        this.modulex = x1 + x;
        this.moduley = y1 + y;
        this.drawImage(image, this.modulex, this.moduley, anchor, trans, modules, mindex);
    }

    public void drawModule(Image image, int x, int y, int anchor, int trans, int x1, int y1, short[] modu, int i) {
        this.modulex = x1 + x;
        this.moduley = y1 + y;
        this.drawImage(image, this.modulex, this.moduley, anchor, trans, modu, i);
    }

    public void drawFrame(Image[] images, short[] modules, short[][] frames, int frameindex, int x, int y, int anchor) {
        if (frameindex >= frames.length) {
            return;
        }
        for (int i = 0; i < frames[frameindex].length; i += 4) {
            short moduleIndex = frames[frameindex][i];
            this.drawModule(images[modules[moduleIndex * 5]], frames[frameindex][i + 1], (int)frames[frameindex][i + 2], anchor, TRANS_TO_DG[frames[frameindex][i + 3]], x, y, modules, (int)moduleIndex);
        }
    }

    public void drawmodule(Image[] img, short[] module, int mindex, int x, int y, int anchor, int trans) {
        this.drawImage(img[module[mindex * 5]], x, y, anchor, trans, module, mindex);
    }

    public short getModuleW(int trans, short w, short h) {
        switch (trans) {
            case 4: 
            case 5: 
            case 6: 
            case 7: {
                return h;
            }
        }
        return w;
    }

    public short getModuleH(int trans, short w, short h) {
        switch (trans) {
            case 4: 
            case 5: 
            case 6: 
            case 7: {
                return w;
            }
        }
        return h;
    }

    public void drawRect(int color, int x, int y, int w, int h) {
        this.g.setColor(color);
        this.g.drawRect(x, y, w, h);
    }

    public void drawImage(Image image, int x, int y, int anchor, int trans) {
        if (trans == 0) {
            this.g.drawImage(image, x, y, anchor);
        } else {
            this.g.drawRegion(image, 0, 0, image.getWidth(), image.getHeight(), trans, x, y, anchor);
        }
    }

    public void drawImage(Image img, int x, int y, int anchor, int trans, short[] modu) {
        this.g.drawRegion(img, (int)modu[1], (int)modu[2], (int)modu[3], (int)modu[4], trans, x, y, anchor);
    }

    public void drawImage(Image[] img, int x, int y, int anchor, int trans, short[] modu) {
        this.g.drawRegion(img[modu[0]], (int)modu[1], (int)modu[2], (int)modu[3], (int)modu[4], trans, x, y, anchor);
    }

    public void drawImage(Image[] img, int x, int y, int anchor, int trans, short modu0, short modu1, short modu2, short modu3, short modu4) {
        this.g.drawRegion(img[modu0], (int)modu1, (int)modu2, (int)modu3, (int)modu4, trans, x, y, anchor);
    }

    public void drawImage(Image img, int x, int y, int anchor, int trans, short[] modu, int i) {
        int temp = i * 5;
        try {
            this.g.drawRegion(img, (int)modu[temp + 1], (int)modu[temp + 2], (int)modu[temp + 3], (int)modu[temp + 4], trans, x, y, anchor);
        }
        catch (Exception e) {
            System.out.print(e.toString());
            System.out.print("temp = " + temp);
            e.printStackTrace();
        }
    }

    public void fillRect(int color, int x, int y, int w, int h) {
        this.g.setColor(color);
        this.g.fillRect(x, y, w, h);
    }

    public void drawString(String str, int x, int y, int anchor, int color) {
        this.drawZkString(str, x, y, anchor, this.zkData, color);
    }

    public void drawLine(int color, int x, int y, int x1, int y1) {
        this.g.setColor(color);
        this.g.drawLine(x, y, x1, y1);
    }

    public void drawTeachWord(String str, int x, int y) {
        int color = 0;
        this.drawlines(y);
        color = this.publiccounter / 2 % 2 == 0 ? 16743936 : 0xFFFFFF;
        this.drawZkString(str, x, y, 17, this.zkData, color);
    }

    public void drawTishiWord(String str, int x, int y) {
        this.drawlines(y);
        if (this.publiccounter / 2 % 2 == 0) {
            this.g.setColor(16743936);
        } else {
            this.g.setColor(0xFFFFFF);
        }
        this.drawZkString(str, x, y, 17, this.zkData);
    }

    public void createMusic(int index) {
        this.musicIndex = index;
        if (index > -1) {
            this.sound.createMusic("/data/" + index + ".mid");
        }
    }

    public void initFlush() {
        this.isFlush = false;
        this.flushCounter = 0;
    }

    public void startFlush() {
        this.isFlush = true;
        this.flushCounter = 0;
    }

    private void drawFlush() {
        this.flushCounter = (byte)(this.flushCounter + 1);
        if (this.flushCounter == 10) {
            this.isFlush = false;
        }
        if (this.flushCounter % 2 == 1) {
            this.g.setColor(0);
            this.g.fillRect(0, 0, 640, 360);
        } else {
            this.g.setColor(0xFFFFFF);
            this.g.fillRect(0, 0, 640, 360);
        }
    }

    public void drawColorString1(String str, int x, int y, int anchor, int colorindex, int bcolor) {
        this.drawZkString(str, x, y - 1, anchor, this.zkData, bcolor);
        this.drawZkString(str, x, y + 1, anchor, this.zkData, bcolor);
        this.drawZkString(str, x - 1, y, anchor, this.zkData, bcolor);
        this.drawZkString(str, x + 1, y, anchor, this.zkData, bcolor);
        this.drawZkString("$" + colorindex + str, x, y, anchor, this.zkData);
    }

    public int drawZkString(String str, int x, int y, int anchor, byte[] data) {
        int color = 0xFFFFFF;
        int colorindex = 0;
        int zkIndex = 0;
        int length = 0;
        switch (anchor) {
            case 17: {
                x -= this.getStringWidth(str) >> 1;
                break;
            }
            case 24: {
                x -= this.getStringWidth(str);
            }
        }
        for (int j = 0; j < str.length(); ++j) {
            char ch = str.charAt(j);
            if (ch == '$') {
                colorindex = Integer.parseInt(str.charAt(++j) + "");
                this.setColor(this.colorlist[0][colorindex], this.colorlist[1][colorindex]);
                continue;
            }
            this.g.setColor(color);
            if (ch < '\u0080') {
                zkIndex = this.getFontDataOffset(ch, this.asc12Index, this.ascSize);
                if (zkIndex == -1) continue;
                this.drawFont(data, zkIndex, this.font_size, x, y, 0, -16777216);
                x += this.font_size >> 1;
                length += this.font_size >> 1;
                continue;
            }
            zkIndex = this.getFontDataOffset(ch, this.hzk12Index, this.hzkSize);
            if (zkIndex == -1) continue;
            this.drawFont(data, zkIndex, this.font_size, x, y, 1, -16777216);
            x += this.font_size;
            length += this.font_size;
        }
        return length;
    }

    public void drawZkString(String str, int x, int y, int anthor, byte[] data, int color) {
        int len = str.length();
        int fontx = x;
        int fonty = y;
        if ((anthor & 8) == 8) {
            fontx = x - this.stringLength(str);
        } else if ((anthor & 1) == 1) {
            fontx = x - (this.stringLength(str) >> 1);
        }
        if ((anthor & 0x20) == 32) {
            fonty = y - this.stringHeight();
        } else if ((anthor & 2) == 2) {
            fonty = y - (this.stringHeight() >> 1);
        }
        int offset = 0;
        for (int i = 0; i < len; ++i) {
            int zkIndex;
            if (str.charAt(i) < '\u0080') {
                zkIndex = this.getFontDataOffset(str.charAt(i), this.asc12Index, this.ascSize);
                if (zkIndex == -1) continue;
                this.drawFont(data, zkIndex, this.font_size, fontx + offset, fonty, 0, -16777216, color);
                offset += this.font_size >> 1;
                continue;
            }
            zkIndex = this.getFontDataOffset(str.charAt(i), this.hzk12Index, this.hzkSize);
            if (zkIndex == -1) continue;
            this.drawFont(data, zkIndex, this.font_size, fontx + offset, fonty, 1, -16777216, color);
            offset += this.font_size;
        }
    }

    public void drawColorString(String str, int x, int y, int anchor, int color, int bcolor) {
        this.drawZkString(str, x, y - 1, anchor, this.zkData, bcolor);
        this.drawZkString(str, x, y + 1, anchor, this.zkData, bcolor);
        this.drawZkString(str, x - 1, y, anchor, this.zkData, bcolor);
        this.drawZkString(str, x + 1, y, anchor, this.zkData, bcolor);
        this.drawZkString(str, x, y, anchor, this.zkData, color);
    }

    private void drawFont(byte[] hzkData, int offset, int fontSize, int x, int y, int fontType, int A) {
        int size;
        int n;
        int color = 0;
        int index = 0;
        int skip = 0;
        int[] tempdata = null;
        if (fontType == 0) {
            offset *= this.ascSize;
            n = (fontSize >> 1) + 7 >> 3;
            tempdata = this.fontdata0;
            size = fontSize >> 1;
        } else {
            offset = this.asc12Index.length * this.ascSize + offset * this.hzkSize;
            n = fontSize + 7 >> 3;
            tempdata = this.fontdata1;
            size = fontSize;
        }
        for (int j = 0; j < fontSize; ++j) {
            color = A | this.colordata[j];
            skip = 0;
            block1: for (int i = 0; i < n; ++i) {
                char c = (char)(hzkData[offset + j * n + i] + 128);
                for (int k = 0; k < 8; ++k) {
                    index = i * 8 + k + j * size;
                    if (skip >= size) continue block1;
                    tempdata[index] = (c & 128 >> k) != 0 ? color : 0;
                    ++skip;
                }
            }
        }
        this.g.drawRGB(tempdata, 0, size, x, y, size, fontSize, true);
    }

    private void drawFont(byte[] hzkData, int offset, int fontSize, int x, int y, int fontType, int A, int RGB) {
        int size;
        int n;
        int color = 0;
        int index = 0;
        int skip = 0;
        int[] tempdata = null;
        if (fontType == 0) {
            offset *= this.ascSize;
            n = (fontSize >> 1) + 7 >> 3;
            tempdata = this.fontdata0;
            size = fontSize >> 1;
        } else {
            offset = this.asc12Index.length * this.ascSize + offset * this.hzkSize;
            n = fontSize + 7 >> 3;
            tempdata = this.fontdata1;
            size = fontSize;
        }
        for (int j = 0; j < fontSize; ++j) {
            color = A | RGB;
            skip = 0;
            block1: for (int i = 0; i < n; ++i) {
                char c = (char)(hzkData[offset + j * n + i] + 128);
                for (int k = 0; k < 8; ++k) {
                    index = i * 8 + k + j * size;
                    if (skip >= size) continue block1;
                    tempdata[index] = (c & 128 >> k) != 0 ? color : 0;
                    ++skip;
                }
            }
        }
        this.g.drawRGB(tempdata, 0, size, x, y, size, fontSize, true);
    }

    private int getFontDataOffset(char ch, int[] idx, int idx_size) {
        int startIndex = 0;
        int stopIndex = idx.length - 1;
        int middle = startIndex + stopIndex >> 1;
        while (idx[middle] != ch && startIndex < stopIndex) {
            if (ch < idx[middle]) {
                stopIndex = middle - 1;
            } else if (ch > idx[middle]) {
                startIndex = middle + 1;
            }
            middle = startIndex + stopIndex >> 1;
        }
        return idx[middle] != ch ? -1 : middle;
    }

    public int stringLength(String s) {
        int w = 0;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) < '\u0080') {
                w += this.font_size >> 1;
                continue;
            }
            w += this.font_size;
        }
        return w;
    }

    public int stringHeight() {
        return this.font_size;
    }

    public String[] formatzkword(String str, int pageW, int state) {
        Vector tempvector = new Vector();
        String source = str;
        char tempcolorinfo = '0';
        String[] result = null;
        int index0 = 0;
        int index1 = -1;
        int index2 = 0;
        int tempstringW = 0;
        while (index0 < source.length()) {
            String tempstr;
            if (++index1 > source.length() - 1) {
                tempstr = source.substring(index0);
                this.addStringToBox(tempvector, tempstr, tempcolorinfo, state);
                index2 = tempstr.lastIndexOf(36);
                if (index2 <= -1) break;
                tempcolorinfo = tempstr.charAt(index2 + 1);
                break;
            }
            if (source.charAt(index1) == '|') {
                tempstr = source.substring(index0, index1);
                this.addStringToBox(tempvector, tempstr, tempcolorinfo, state);
                index2 = tempstr.lastIndexOf(36);
                if (index2 > -1) {
                    tempcolorinfo = tempstr.charAt(index2 + 1);
                }
                index0 = index1 + 1;
                tempstringW = 0;
                continue;
            }
            if (source.charAt(index1) == '$') {
                ++index1;
                continue;
            }
            if (source.charAt(index1) < '\u0080') {
                if ((tempstringW += this.font_size >> 1) <= pageW) continue;
                tempstr = source.substring(index0, index1);
                this.addStringToBox(tempvector, tempstr, tempcolorinfo, state);
                index2 = tempstr.lastIndexOf(36);
                if (index2 > -1) {
                    tempcolorinfo = tempstr.charAt(index2 + 1);
                }
                index0 = index1--;
                tempstringW = 0;
                continue;
            }
            if ((tempstringW += this.font_size) <= pageW) continue;
            tempstr = source.substring(index0, index1);
            this.addStringToBox(tempvector, tempstr, tempcolorinfo, state);
            index2 = tempstr.lastIndexOf(36);
            if (index2 > -1) {
                tempcolorinfo = tempstr.charAt(index2 + 1);
            }
            index0 = index1--;
            tempstringW = 0;
        }
        result = new String[tempvector.size()];
        for (int i = 0; i < tempvector.size(); ++i) {
            result[i] = (String)tempvector.elementAt(i);
        }
        return result;
    }

    private void addStringToBox(Vector tempvector, String newstring, char colorinfo, int state) {
        if (state == 0 && newstring.charAt(0) != '$') {
            StringBuffer strb = new StringBuffer("$");
            strb.append(colorinfo);
            strb.append(newstring);
            newstring = strb.toString();
        }
        tempvector.addElement(newstring);
    }

    public void drawZkStringArry(String[] s, int x, int y, int anchor, int startline, int showline, int color) {
        String tempstr = "";
        byte[] tempZkData = this.zkData;
        int line = 0;
        int startx = x;
        this.g.setColor(color);
        for (int i = 0; i < showline; ++i) {
            line = i + startline;
            if (line <= -1 || line >= s.length) continue;
            tempstr = s[line];
            x = startx;
            this.drawZkString(tempstr, x, y + i * 20, anchor, tempZkData);
        }
    }

    public void drawZkStringArry1(String[] s, int x, int y, int anchor, int startline, int showline, int bcolor, byte len) {
        String tempstr = "";
        byte[] data = this.zkData;
        int zkIndex = 0;
        int line = 0;
        int startx = x;
        int templen = 0;
        int colorindex = 0;
        this.g.setColor(bcolor);
        for (int i = 0; i < showline; ++i) {
            line = i + startline;
            if (line <= -1 || line >= s.length) continue;
            tempstr = s[line];
            x = startx;
            switch (anchor) {
                case 17: {
                    x -= this.getStringWidth(tempstr) >> 1;
                }
            }
            for (int j = 0; j < tempstr.length() && templen < len; ++j) {
                char ch = tempstr.charAt(j);
                if (ch == '$') {
                    colorindex = Integer.parseInt(tempstr.charAt(++j) + "");
                    this.setColor(this.colorlist[0][colorindex], this.colorlist[1][colorindex]);
                    continue;
                }
                if (ch < '\u0080') {
                    zkIndex = this.getFontDataOffset(ch, this.asc12Index, this.ascSize);
                    if (zkIndex != -1) {
                        this.drawFont(data, zkIndex, this.font_size, x, y + i * 20, 0, -16777216);
                        x += this.font_size >> 1;
                    }
                } else {
                    zkIndex = this.getFontDataOffset(ch, this.hzk12Index, this.hzkSize);
                    if (zkIndex != -1) {
                        this.drawFont(data, zkIndex, this.font_size, x, y + i * 20, 1, -16777216);
                        x += this.font_size;
                    }
                }
                ++templen;
            }
        }
    }

    public void drawZkStringArry2(String[] s, int x, int y, int anchor, int startline, int showline, int counter) {
        int alpha = -16777216;
        int startx = x;
        String tempstr = "";
        byte[] data = this.zkData;
        int zkIndex = 0;
        int line = 0;
        int colorindex = 0;
        for (int i = 0; i < showline; ++i) {
            line = i + startline;
            if (line <= -1 || line >= s.length) continue;
            tempstr = s[line];
            x = startx;
            switch (anchor) {
                case 17: {
                    x -= this.getStringWidth(tempstr) >> 1;
                }
            }
            alpha = -16777216;
            if (i == showline - 1 && counter > -1) {
                alpha = counter * 0x11000000;
            }
            for (int j = 0; j < tempstr.length(); ++j) {
                char ch = tempstr.charAt(j);
                if (ch == '$') {
                    colorindex = Integer.parseInt(tempstr.charAt(++j) + "");
                    this.setColor(this.colorlist[0][colorindex], this.colorlist[1][colorindex]);
                    continue;
                }
                if (ch < '\u0080') {
                    zkIndex = this.getFontDataOffset(ch, this.asc12Index, this.ascSize);
                    if (zkIndex == -1) continue;
                    this.drawFont(data, zkIndex, this.font_size, x, y + i * 20, 0, alpha);
                    x += this.font_size >> 1;
                    continue;
                }
                zkIndex = this.getFontDataOffset(ch, this.hzk12Index, this.hzkSize);
                if (zkIndex == -1) continue;
                this.drawFont(data, zkIndex, this.font_size, x, y + i * 20, 1, alpha);
                x += this.font_size;
            }
        }
    }

    public int getStringWidth(String str) {
        int wid = 0;
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == '$') {
                ++i;
                continue;
            }
            if (str.charAt(i) < '\u0080') {
                wid += this.font_size >> 1;
                continue;
            }
            wid += this.font_size;
        }
        return wid;
    }

    public int getStringLength(String str) {
        int len = 0;
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == '#') {
                ++i;
                continue;
            }
            ++len;
        }
        return len;
    }

    public void setColor(int color1, int color0) {
        for (int i = 0; i < this.colordata.length; ++i) {
            int rr = i * ((color1 & 0xFF0000) - (color0 & 0xFF0000)) / 19 + color0 & 0xFF0000;
            int gg = i * ((color1 & 0xFF00) - (color0 & 0xFF00)) / 19 + color0 & 0xFF00;
            int bb = i * ((color1 & 0xFF) - (color0 & 0xFF)) / 19 + color0 & 0xFF;
            this.colordata[i] = rr | gg | bb;
        }
    }

    public void newRMS() {
        if (this.rms.getNumRMS() != 10) {
            byte[] data = new byte[]{0};
            for (int i = 0; i < 10; ++i) {
                this.rms.addRMS(data);
            }
        }
    }

    public void setRecord(byte[] data, int index) {
        this.rms.setRMS(index, data);
    }

    public byte[] getRecord(int index) {
        return this.rms.readRMS(index);
    }

    public void setsmsRecord(byte[] data, int index) {
        this.smsrms.setRMS(index, data);
    }

    public byte[] getsmsRecord(int index) {
        return this.smsrms.readRMS(index);
    }

    public void drawlines(int y) {
        for (int i = 0; i < 18; ++i) {
            this.g.drawRGB(this.linedata, 0, 640, 0, y - 1 + i, 640, 1, true);
        }
    }

    protected void pointerDragged(int i, int i1) {
        this.pointer.pointerDragged(i, i1);
    }

    protected void pointerPressed(int i, int i1) {
        this.pointer.pointerPressed(i, i1);
    }

    protected void pointerReleased(int i, int i1) {
        this.pointer.pointerReleased(i, i1);
    }

    public void contact(String address) {
        try {
            this.midlet.platformRequest(address);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void gameStop(String address) {
        try {
            this.midlet.platformRequest(address);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void gameStop1(String address) {
        try {
            this.sound.closeMusic();
            this.exitGame();
            this.midlet.platformRequest(address);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    void createExitImage() {
        try {
            this.quitImage = Image.createImage((String)"/logo/exit.png");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void cleanExitImage() {
        this.quitImage = null;
    }

    public void quitDataToPaint() {
        this.g.translate(140, 0);
        this.g.drawImage(this.quitImage, 0, 0, 4 | 0x10);
        this.g.translate(-140, 0);
        this.g.setColor(0xFFFB99);
        this.g.drawString("\u786e\u8ba4\u9000\u51fa", 320, 180, 33);
        this.g.drawString("\u662f", 0, 360, 36);
        this.g.drawString("\u5426", 640, 360, 40);
    }

    public void quitConfirmToPaint() {
        this.g.translate(140, 0);
        this.g.drawImage(this.quitImage, 0, 0, 4 | 0x10);
        this.g.translate(-140, 0);
        this.g.setColor(0xFFFB99);
        this.g.drawString("\u66f4\u591a\u7cbe\u5f69\u6e38\u620f", 320, 170, 33);
        this.g.drawString("\u5c3d\u5728\u6e38\u620f\u9891\u9053", 320, 190, 33);
        this.g.drawString("wap.ttsy.org", 320, 210, 33);
        this.g.drawString("\u786e\u8ba4", 0, 360, 36);
        this.g.drawString("\u9000\u51fa", 640, 360, 40);
    }

    public void isContactToPaint() {
        this.g.translate(140, 0);
        this.g.drawImage(this.quitImage, 0, 0, 4 | 0x10);
        this.g.translate(-140, 0);
        this.g.setColor(0xFFFB99);
        this.g.drawString("\u662f\u5426\u9000\u51fa\u6e38\u620f\u8054\u7f51", 320, 170, 33);
        this.g.drawString("\u786e\u8ba4", 0, 360, 36);
        this.g.drawString("\u8fd4\u56de", 640, 360, 40);
    }

    void quitKeyAction() {
        if (Control.IsKeyDown(65536)) {
            this.sound.closeMusic();
            this.exitGame();
        } else if (Control.IsKeyDown(131072)) {
            this.cleanExitImage();
            this.changeGameState(3);
        }
    }

    void quitConfirmKeyAction() {
        if (Control.IsKeyDown(65536)) {
            this.gameStop1(CONTACT_ADDRESS);
        } else if (Control.IsKeyDown(131072)) {
            this.sound.closeMusic();
            this.exitGame();
        }
    }

    void isContactKeyAction() {
        if (Control.IsKeyDown(65536)) {
            this.gameStop(CONTACT_ADDRESS);
        } else if (Control.IsKeyDown(131072)) {
            this.cleanExitImage();
            this.changeGameState(3);
        }
    }
}

