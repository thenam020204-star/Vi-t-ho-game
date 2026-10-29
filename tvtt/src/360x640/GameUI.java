/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
package src;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.microedition.lcdui.Image;
import src.Control;
import src.FSprite;
import src.GameCanvas;
import src.GameData;
import src.Role;
import src.Scroll;
import src.Util;

public class GameUI
implements GameData {
    GameCanvas canvas;
    byte savegamestate;
    byte savecityindex;
    Scroll scroll;
    Image[] selectImages;
    Image[] backImages;
    short[] selectModules;
    short[] backModules;
    short[][] selectFrames;
    short[][] backFrames;
    short[][] selectActions;
    short[][] backActions;
    byte uiState;
    byte uiLastState;
    byte uiNewState;
    byte actionIndex;
    byte actionFrameIndex;
    byte frameDelay;
    byte backActionIndex;
    byte backActionFrameIndex;
    byte backFrameDelay;
    byte numberbit;
    byte[] uinumberindex = new byte[6];
    boolean isActionOver;
    byte uiActionType;
    short uiActionBackIndex;
    short gamehour;
    short gameminute;
    short gamesecond;
    short[] mineminute = new short[2];
    short[] minesecond = new short[2];
    byte gameseconddelay;
    final String[][] skillCommand = new String[][]{{"\u5f00\u542f", "\u67e5\u770b", "\u8fd4\u56de"}, {"\u5378\u4e0b", "\u67e5\u770b", "\u8fd4\u56de"}, {"\u88c5\u5907", "\u67e5\u770b", "\u8fd4\u56de"}, {"\u5347\u7ea7", "\u5378\u4e0b", "\u67e5\u770b", "\u8fd4\u56de"}, {"\u5347\u7ea7", "\u88c5\u5907", "\u67e5\u770b", "\u8fd4\u56de"}, {"\u56fe\u6807 7", "\u56fe\u6807 9", "\u8fd4\u56de"}, {"\u5347\u7ea7", "\u67e5\u770b", "\u8fd4\u56de"}, {"\u67e5\u770b", "\u8fd4\u56de"}};
    final byte[] SKILLCOMMANDTOTAL = new byte[]{3, 3, 3, 4, 4, 3, 3, 2, 0};
    final String[] itemBoxCommand = new String[]{"\u4f7f\u7528", "\u4e22\u5f03", "\u8fd4\u56de"};
    final byte ITEMBOXCOMMANDTOTAL = (byte)2;
    final String[] itemBoxCommand1 = new String[]{"\u4e22\u5f03", "\u8fd4\u56de"};
    final byte ITEMBOXCOMMANDTOTAL1;
    final String[] mineCommand = new String[]{"\u9886\u53d6\u6536\u76ca", "\u96c7\u4f63\u77ff\u5de5", "\u8fd4\u56de"};
    final byte MINECOMMANDTOTAL = (byte)3;
    final String[] cityIntroBoxCommand = new String[]{"\u5347\u7ea7\u57ce\u5e02", "\u8fd4\u56de"};
    final byte CITYINTROBOXCOMMANDTOTAL;
    final String[][] armBoxCommand = new String[][]{{"\u88c5\u5907", "\u4e22\u5f03", "\u8fd4\u56de"}, {"\u5378\u4e0b", "\u8fd4\u56de"}, {"\u4f4d\u7f6e1", "\u4f4d\u7f6e2", "\u8fd4\u56de"}, {"\u8fd4\u56de"}};
    final byte[] ARMBOXCOMMANDTOTAL = new byte[]{2, 1, 2, 0};
    final String[][] rideBoxCommand = new String[][]{{"\u5378\u4e0b", "\u8865\u7ed9", "\u8fd4\u56de"}, {"\u88c5\u5907", "\u8865\u7ed9", "\u8fd4\u56de"}};
    final byte[] RIDEBOXCOMMANDTOTAL = new byte[]{2, 2};
    final String[] forgeBoxCommand = new String[]{"\u4f4d\u7f6e\u4e00", "\u4f4d\u7f6e\u4e8c", "\u8fd4\u56de"};
    final String[][] propertyBoxCommand = new String[][]{{"\u88c5\u5907", "\u8fd4\u56de"}, {"\u5378\u4e0b", "\u8fd4\u56de"}, {"\u4f4d\u7f6e1", "\u4f4d\u7f6e2", "\u8fd4\u56de"}};
    final byte[] PROPERTYBOXCOMMANDTOTAL = new byte[]{1, 1, 2};
    final byte FORGEBOXCOMMANDTOTAL = (byte)2;
    int skillKeyStart;
    int skillKeyEnd;
    final short MISSIONLISTSHOWNUM = (short)6;
    final short MISSIONINFOWIDTH = (short)250;
    final short SHOWASIDEWIDTH = (short)300;
    final short SHOWASIDEWIDTH1 = (short)150;
    final short TALKWORDWIDTH = (short)520;
    final short TISHIWORDWIDTH = (short)300;
    final short TALKSHOWNUM = (short)2;
    final short TALKSHOWNUM1 = (short)2;
    final short INTROSHOWNUM = (short)6;
    final short MISSIONWORDWIDTH = (short)100;
    final short MISSIONINTROSHWONUM = (short)5;
    final short MISSIONINTROWORDWIDTH = (short)200;
    final short WEAPONINFOWIDTH = (short)505;
    final short WEAPONINFOHEIGHT = (short)100;
    final short WEAPON1INFOHEIGHT = (short)80;
    final short SKILLINFOHEIGHT = (short)160;
    final short ITEMINFOHEIGHT = (short)60;
    final byte[][] missionrewarddata = new byte[][]{new byte[0], {25, 5}, new byte[0], {13, 1}, new byte[0], new byte[0], new byte[0], {84, 1}, {86, 1}, {123, 1}, {11, 1}, {41, 1}, {87, 1}, {77, 1}};
    final byte[][] missionrewardtype = new byte[][]{new byte[0], {0}, new byte[0], {0}, new byte[0], new byte[0], new byte[0], {1}, {1}, {2}, {0}, {0}, {1}, {1}};
    final short[] missionmoneydata = new short[]{240, 300, 260, 330, 360, 400, 450, 0, 0, 500, 0, 800, 1000, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    final byte[][] mainmissionrewarddata = new byte[][]{new byte[0], {17, 2}, {95, 1}, {16, 3}, {32, 2}, {79, 1}, new byte[0], {1, 1}, {35, 2}, {80, 1}, {34, 1, 2, 1}, {39, 1}, {75, 1}, {81, 1}, {11, 1}, {36, 2}, {37, 1}, new byte[0], new byte[0], new byte[0], {12, 1}, {82, 1}, {76, 1}, {29, 10}, {12, 1}, {83, 1}, {38, 1}, {85, 1}, new byte[0], {11, 1}, new byte[0], new byte[0], new byte[0], new byte[0], new byte[0]};
    final byte[][] mainmissionrewardtype = new byte[][]{new byte[0], {0}, {1}, {0}, {0}, {1}, new byte[0], {3}, {0}, {1}, {0, 3}, {0}, {1}, {1}, {0}, {0}, {0}, new byte[0], new byte[0], new byte[0], {0}, {1}, {1}, {0}, {0}, {1}, {0}, {1}, new byte[0], {0}, new byte[0], new byte[0], new byte[0], new byte[0], new byte[0]};
    final short[] mainmissionmoneydata = new short[]{200, 300, 0, 0, 0, 0, 400, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 620, 0, 0, 0, 0, 0, 0, 0, 0, 1500, 0, 0, 0, 0, 350, 500};
    public static final int UI_MENU_ITEM_STEP_X = 42;
    public static final int UI_MENU_ITEM_STEP_Y = 42;
    public static short UI_ICON_ITEM_STEP_X = (short)45;
    public static short UI_ICON_ITEM_STEP_Y = (short)50;
    public static final int UI_MENU_STEP_X = 0;
    public static final int UI_MENU_STEP_Y = 37;
    public static final short JIANTOU_OFFSET_X = 213;
    public static final short JIANTOU_OFFSET_Y = 27;
    public static final short JIANTOU_OFFSET_X1 = 88;
    public static final short JIANTOU_OFFSET_Y1 = 22;
    public static final short JIANTOU_OFFSET_STEP = 220;
    byte[] uiImgData = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 14, 24, 25, 26, 27, 29, 32, 35, 21, 36, 41, 43, 42, 44, 39};
    static boolean beAttackStateHintFlag = false;
    boolean isdrawkey5;
    int key5X;
    int key5Y;
    int key5Z;
    byte[] uiBackImgData = new byte[]{11};
    boolean isshowtishi;
    int tishistate;
    String[] tishiword;
    byte tishitype;
    static final int SYSTEMMENUX = 215;
    static final int SYSTEMMENUY = 64;
    short[][] systemdata = new short[][]{{408, 77, 78, 79, 80}, {409, 66, 67, 68, 69}};
    byte systemMenuSelectIndex;
    public static byte systemMenuStartIndex = 0;
    public static final byte systemMenuItemShowAmount = 8;
    final byte SYSTEMNUMTOTAL = (byte)5;
    int[] systemSpeedY;
    boolean isSystemActionOver;
    int[] systemspeed = new int[]{0, 10, 20, 30, 40, 50, 60};
    int[] systemtempdata = new int[]{0, 40, 80, 120, 160, 200, 240};
    final int GAMEMENUX;
    final int GAMEMENUY;
    byte[][] menudata = new byte[][]{{81, 82, 83, 84, 85}, {70, 71, 72, 73, 74}};
    public static final short GAME_MENU_OFFSET_Y = 0;
    static int menuSpeedX;
    static int hire;
    byte gameMenuSelectIndex;
    public static byte gameMenuStartIndex;
    public static final byte gameMenuItemShowAmount = 8;
    final byte GAMEMENUTOTAL = (byte)5;
    int[] gameMenuSpeedY;
    boolean isMenuActionOver;
    int[] menuspeed = new int[]{0, 12, 24, 36, 48};
    int[] menutempdata = new int[]{0, 48, 96, 144, 192};
    int changeWeaponTime;
    byte changeWeaponName;
    final byte LOADTOTAL = (byte)2;
    byte loadweaponimagetime = (byte)2;
    byte loadweaponname;
    int showweaponspeedy = 1;
    int showweapony;
    int showweaponvdir;
    final int ARM_X;
    final int ARM_Y;
    static final int[][] LABEL_X;
    static final int LABEL_Y = 31;
    short[][] armboxdata = new short[][]{{256, 277, 278}, {276, 257, 278}, {276, 277, 258}};
    public static final int MINI_LOADING_OFFSET_X = 200;
    public static final int MINI_LOADING_OFFSET_Y = 20;
    String[] arminfolabel = new String[]{"\u6b66\u5668\u63cf\u8ff0", "\u88c5\u5907\u63cf\u8ff0", "\u63d2\u4ef6\u63cf\u8ff0"};
    byte armBoxState;
    byte armBoxIndex;
    byte armBoxSeletedIndex;
    byte armBoxStartIndex;
    int commandShowTime;
    byte commandState;
    byte lastCommandState;
    boolean isCommand;
    byte commandSeletedIndex;
    String commandStr;
    String[] commandStrs;
    int commandFontColor;
    final int[] commandColorData = new int[]{0xFFFFFF, 15597319};
    byte armBoxUseTotal;
    public static final int BOX_HDIR_NUM = 8;
    public static final int BOX_VDIR_NUM = 2;
    public static final int ROCT_DISTANCE = 53;
    public static final int ITEM_X = 23;
    public static final int ITEM_Y = 107;
    public static final int ACTION_BOXX = 182;
    public static final int ACTION_BOXY = 56;
    short[][] itemboxdata = new short[][]{{98, 57}, {94, 59}};
    byte itemBoxSeletedIndex;
    byte itemBoxStartIndex;
    byte itemBoxUseTotal = (byte)13;
    int[][] itemPackBox;
    int needNum;
    Image[] forgeImage;
    short[] forgeModules;
    short[][] forgeFrames;
    short[][] forgeActions;
    byte forgeActionIndex;
    byte forgeActionFrameIndex;
    byte forgeFrameDelay;
    boolean forgeActionOver;
    byte[] forgeImgData = new byte[]{23, 20};
    private int[][] light;
    public static final short DATA_STEP_OFFSET_X = 120;
    public static final short DATA_STEP_OFFSET_Y = 30;
    public static final short DATA_STEP_X = 50;
    public static final short DATA_STEP_Y = 15;
    public static final short DATA_BUTTON_STEP_X = 10;
    public static final short DATA_BUTTON_STEP_Y = 15;
    public static final short DATA_UP_OR_DOWN_SIGN_OFFSET_X = 5;
    public static final short DATA_UP_OR_DOWN_SIGN_OFFSET_Y = 2;
    String[] currentplusdatastr;
    final String[] plusdatastr = new String[]{"\u589e\u52a0\u57fa\u7840\u4f24\u5bb3", "\u589e\u52a0\u5fc5\u6740\u4f24\u5bb3", "\u964d\u4f4e\u80fd\u91cf\u6d88\u8017", "\u589e\u52a0\u66b4\u51fb\u51e0\u7387", "\u589e\u52a0\u66b4\u51fb\u4f24\u5bb3", "\u589e\u52a0\u95ea\u907f\u51e0\u7387", "\u589e\u52a0\u7729\u6655\u51e0\u7387", "\u964d\u4f4e\u8d1f\u9762\u6548\u679c\u65f6\u95f4", "\u964d\u4f4e\u654c\u4eba\u79fb\u52a8\u901f\u5ea6", "\u589e\u52a0\u81ea\u8eab\u79fb\u52a8\u901f\u5ea6", "\u653b\u51fb\u5438\u8840", "\u653b\u51fb\u5438\u84dd", "\u589e\u52a0\u7269\u54c1\u6389\u843d\u51e0\u7387", "\u53cd\u5f39\u4f24\u5bb3", "\u589e\u52a0\u9632\u5fa1", "\u6301\u7eed\u6062\u590d\u751f\u547d", "\u65f6\u95f4\u9759\u6b62"};
    Image weaponImage;
    short[] quanModule = new short[]{0, 0, 0, 33, 18};
    short[] jianModule = new short[]{0, 0, 138, 82, 54};
    short[] qiangModule = new short[]{0, 0, 0, 60, 27};
    short[] shownewsactiondata = new short[]{76, 77, 78, 79, 80, 81};
    byte shownewsframeindex;
    byte[] isshowcitynews = new byte[5];
    byte[] isshowforgenews = new byte[5];
    short[][] isshowforgearmnews = new short[5][3];
    int[] isshowsalematerialnews = new int[5];
    short isshowmissionnews;
    final byte[] citynewsdata = new byte[]{40, 24, 24, 24, 40};
    final byte forgenewsdata = (byte)5;
    byte[][] currentForgeArm;
    byte[][] currentForgeArmlevel;
    byte[] currentForgeArmTotal;
    public static final int FORGE_ARM_AMOUNT = 12;
    byte[] currentSaleMaterial;
    final String[][] citytalk = new String[][]{{"\u8bf7\u8bb0\u4f4f\u8fd9\u4e9b\u6b66\u5668\u4e0d\u8981\u975e\u6cd5\u51fa\u552e\u7ed9\u4efb\u4f55\u4eba\u3002", "\u55e8\uff01\u6709\u4ec0\u4e48\u9700\u8981\u5417\uff1f\u6211\u80fd\u6ee1\u8db3\u4f60\u4efb\u4f55\u4e8b\u3002", "\u4f60\u53ef\u4ee5\u5728\u8fd9\u91cc\u5efa\u8bbe\u57ce\u5e02\uff0c\u4e5f\u53ef\u4ee5\u8ba9\u6211\u4eec\u8bb0\u5f55\u4e0b\u60a8\u4f1f\u5927\u7684\u5192\u9669\u7ecf\u5386\u3002"}, {"\u4eba\u7c7b\uff0c\u9009\u8d2d\u4f60\u559c\u6b22\u7684\u6b66\u5668\u5427\uff01", "\u6211\u4eec\u7684\u836f\u54c1\u90fd\u662f\u5929\u7136\u7684\u3002", "\u4e94\u56fd\u57ce\u5982\u4eca\u662f\u661f\u7cfb\u4e2d\u6700\u5927\u7684\u5de5\u4e1a\u5316\u57ce\u5e02\uff0c\u4f60\u5c06\u5982\u4f55\u9a7e\u9a6d\u8fd9\u4e2a\u5e9e\u7136\u5927\u7269\uff1f"}, {"\u6bd4\u8d77\u8d5a\u53d6\u4f60\u7684\u91d1\u94b1\uff0c\u6211\u66f4\u5411\u5f80\u81ea\u7531\uff01", "\u8fd9\u91cc\u4ec0\u4e48\u90fd\u6709\u2026\u2026\u5c31\u50cf\u6211\u4eec\u4ec0\u4e48\u90fd\u4e0d\u66fe\u62e5\u6709\u3002", "\u5e26\u9886\u6211\u4eec\u8d70\u5411\u7e41\u8363\uff0c\u8fd9\u91cc\u5c06\u4f1a\u662f\u5f81\u670d\u5b87\u5b99\u7684\u8d77\u70b9\uff01"}, {"\u60f3\u5728\u654c\u4eba\u8eab\u4e0a\u591a\u589e\u6dfb\u4e00\u4e9b\u4f24\u53e3\u5417\uff1f\u4f60\u6765\u5bf9\u5730\u65b9\u4e86\uff01", "\u8981\u77e5\u9053\u8fd9\u4e9b\u73a9\u610f\u6211\u4eec\u5927\u90fd\u51fa\u552e\u7ed9\u4eba\u7c7b\u4e86\uff0c\u4e0e\u8fd9\u4e9b\u9053\u5177\u76f8\u6bd4\u6211\u66f4\u76f8\u4fe1\u8428\u6ee1\u7684\u9b54\u529b\u3002", "\u6211\u4e00\u5411\u4e0d\u6b22\u8fce\u4eba\u7c7b\uff01\u4e0d\u8fc7\u6211\u770b\u4f60\u6709\u610f\u8981\u6539\u5584\u4e00\u4e0b\u8fd9\u91cc\u5608\u6742\u7684\u73af\u5883\u2026\u2026"}, {"\u67e5\u770b\u7269\u54c1\u7684\u8be6\u5c3d\u4fe1\u606f\u3001\u88c5\u5907\u6b66\u5668\u3001\u4f7f\u7528\u9053\u5177\u3002", "\u8fd9\u91cc\u867d\u7136\u4ef7\u683c\u9ad8\u6602\uff0c\u4e0d\u8fc7\u6709\u53ef\u80fd\u4f1a\u6551\u4f60\u4e00\u547d\u54e6\u3002", "\u968f\u65f6\u66f4\u65b0\u5404\u9053\u5177\u7684\u8be6\u5c3d\u8d44\u6599\u3002"}};
    byte[] forgebossimagedata = new byte[]{3, 12, 9, 13};
    byte[] shopbossimagedata = new byte[]{8, 12, 12, 14};
    byte[] cityIntrobossimagedata = new byte[]{3, 12, 10, 13};
    public static final short FORGE_BOOK_DATA_OFFSET_X = 325;
    public static final short FORGE_BOOK_DATA_OFFSET_Y = 20;
    public static final short FORGE_DATA_OFFSET_X = 24;
    public static final short FORGE_DATA_OFFSET_Y = 195;
    String[] forgeboxoption = new String[]{"\u953b\u9020\u88c5\u5907", "\u51fa\u552e\u88c5\u5907", "\u4e70\u5356\u6750\u6599"};
    Image forgeBossImage;
    Image shopBossImage;
    Image cityIntroBossImage;
    short[][] forgeboxdata = new short[][]{{260, 281, 278}, {280, 261, 278}, {280, 281, 258}};
    short[][] materialsaledata = new short[][]{{255, 275}, {274, 264}};
    public static final short FORGE_OFFSET_X = 199;
    public static final short FORGE_OFFSET_Y = 19;
    short[] laohujiframe = new short[]{198, 199, 200, 201, 202, 203, 204, 205, 206};
    short[] laohujioffsety = new short[]{3, -2, 1, 0};
    byte laohujioffsetindex;
    byte laohujiframeindex;
    byte laohujistate;
    byte laohujitype;
    byte laohujitime;
    int laohujix;
    final int LAOHUJIX1;
    final int LAOHUJIX2;
    final int LAOHUJIX3;
    int needMoney;
    public static final int LAOHUJI_OFFSET_X = 210;
    public static final int LAOHUJI_OFFSET_Y = 0;
    byte forgeBoxState;
    byte factoryIndex;
    byte forgeBoxIndex;
    byte forgeShopIndex;
    byte materialShopIndex;
    byte forgeBoxSeletedIndex;
    byte[] forgeBoxStartIndex = new byte[3];
    byte armSaleSeletedIndex;
    byte armSaleStartIndex;
    byte materialBuySeletedIndex;
    byte materialBuyStartIndex;
    byte materialSaleSeletedIndex;
    byte materialSaleStartIndex;
    int seletedBoxX;
    int seletedBoxY;
    byte forgeUseTotal;
    byte forgeSaleWeaponUseTotal;
    byte forgeSaleSuitUseTotal;
    byte forgeSalePlusUseTotal;
    byte materialSaleUseTotal;
    byte materialBuyUseTotal;
    byte currentForgeArmName;
    byte totalamount;
    int propertyboxattack;
    int propertyboxdefence;
    int propertyboxbaoji;
    int propertyboxbaojiattack;
    final short PLAYERBASELIFEX = (short)385;
    final short PLAYERBASELIFEY = (short)108;
    final short PLAYERBASELIFEW = (short)60;
    final short PLAYERBASELIFEH = (short)5;
    final short PLAYERBASEEXPW = (short)170;
    short[][] propertyboxdata = new short[][]{{256}, {257}, {258}};
    byte[] propertyarmdata = new byte[6];
    Image[] playerimages;
    short[] playermodules;
    short[][] playerframes;
    short[][] playeractions;
    byte playeractionindex;
    byte playeractiontype;
    byte playeractionframeindex;
    byte playerframedelay;
    byte[] playeractiontypedata = new byte[]{0, 8};
    public static final int PROPERTY_ICON_ITEM_STEP_X = 169;
    public static final int PROPERTY_ICON_ITEM_STEP_Y = 93;
    byte propertyBoxState;
    byte propertyBoxSeletedIndex;
    byte propertySeletedIndex;
    byte propertyStartIndex;
    byte propertyUseTotal;
    int[] propertyPack;
    byte propertyarmindex;
    byte[] currentmissionlist;
    static final byte currentmissionlisttotal = 6;
    final byte[] missionstatedata = new byte[]{119, 117, 118, 116};
    public static final short TOWN_HOUSE_BASE_OFFSET_X = 200;
    public static final short TOWN_HOUSE_BASE_OFFSET_Y = 20;
    byte townhouseBoxState;
    byte townhouseBoxSeletedIndex;
    byte townhouseBoxStartIndex;
    byte townhouseBoxUseTotal = (byte)6;
    static final byte TYPE1SHOWNUM = 12;
    static final byte TYPE2SHOWNUM = 6;
    static final byte TYPE3SHOWNUM = 5;
    static final byte TYPE4HOWNUM = 4;
    static final byte TYPE5HOWNUM = 3;
    static final byte TYPE3SHOWNUM_4 = 5;
    static final byte TYPE5HOWNUM_3 = 3;
    static final byte TYPE0SHOWNUM = 9;
    byte[] currentsaledata;
    short[][] convenientdata = new short[][]{{255, 275}, {274, 264}};
    public static final int UI_ITEM_STEP_X = 50;
    public static final int UI_ITEM_STEP_Y = 49;
    public static final int UI_ITEM_OFFSET_X = 45;
    public static final int UI_ITEM_OFFSET_Y = 20;
    boolean isDrawKuoBaoTishi;
    boolean isShowKuoBaoTishi;
    byte kuoBaoState;
    String kuoBaoStr = null;
    public static final short TISHI_KEY_ACTION_INFO_OFFSET_Y0 = -5;
    public static final short TISHI_KEY_ACTION_INFO_OFFSET_Y1 = 20;
    public int kuobaoTime = 0;
    byte convenientState;
    byte convenientIndex;
    byte convenientBuySeletedIndex;
    byte convenientBuyStartIndex;
    byte convenientSaleSeletedIndex;
    byte convenientSaleStartIndex;
    byte convenientBuyUseTotal;
    byte convenientSaleUseTotal;
    String[] shopboxoption = new String[]{"\u8d2d\u4e70\u9053\u5177", "\u51fa\u552e\u9053\u5177"};
    int offsetx;
    final int OFFSETSPEEDX;
    boolean isoffsetactionover = true;
    byte currentBuyItemName;
    byte currentSaleItemName;
    byte[] shopSaleData = new byte[]{0, 1, 2, 3, 4, 5, 6, 8, 9, 11, 12, 13};
    byte[] convenientSaleData = new byte[]{0, 1, 2, 3, 4, 5, 6, 8, 9, 11, 12, 13};
    byte[][] saleMaterialLevel = new byte[][]{{0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2}, {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2}, {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2}, {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}};
    byte[][] allSaleMaterial = new byte[][]{{14, 15, 23, 16, 17, 19, 18, 28, 22, 32, 25, 29, 20, 27, 35, 37, 31, 30, 38, 43, 42, 41}, {15, 26, 17, 16, 14, 23, 25, 32, 19, 29, 18, 27, 33, 36, 30, 20, 31, 39, 34, 35, 38, 42, 41}, {14, 23, 15, 25, 22, 26, 16, 35, 21, 32, 18, 19, 27, 37, 38, 39, 31}, {14, 15, 23, 17, 25, 26, 19, 18, 24, 22, 32, 30, 33, 29, 35, 36, 37, 39, 31, 38}};
    int currentBuyItemMoney;
    int currentSaleItemMoney;
    byte shopState;
    byte shopIndex;
    byte shopBuySeletedIndex;
    byte shopBuyStartIndex;
    byte shopSaleSeletedIndex;
    byte shopSaleStartIndex;
    byte shopBuyUseTotal;
    byte shopSaleUseTotal;
    byte showmissionindex;
    String[] missionaimword;
    String[] missionrewardword;
    String missionmapword;
    int[] adornlinecolor = new int[]{16406378, 16360710, 9765067, 371508};
    String labelstr;
    int labelwordnum;
    public static final short MISSION_DATA_OFFSET_X = 80;
    public static final short MISSION_DATA_OFFSET_Y = 25;
    public static final short MISSION_DATA_STEP = 2;
    public static final int MISSION_NAME_OFFSET_Y = 12;
    short[][] missionuiboxdata = new short[][]{{262, 283}, {282, 263}};
    public static final short SUB_MISSION_DATA_OFFSET_X = 35;
    public static final short SUB_MISSION_DATA_OFFSET_Y = 19;
    byte missionUiBoxState;
    byte missionUiBoxIndex;
    byte missionUiBoxSeletedIndex;
    byte missionUiBoxStartIndex;
    byte missionUiUseTotal;
    byte showmissionlastgamestate;
    int savemoney;
    final int PLAYERSAVEMONEY;
    byte savestate;
    int savetime;
    boolean isshowsave;
    String[] forgebookoption = new String[]{"\u5251\u56fe\u9274", "\u62f3\u5957\u56fe\u9274", "\u9970\u54c1\u56fe\u9274"};
    byte[][] forgebookdata = new byte[][]{{44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58}, {59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73}, {88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123}};
    byte[] currentforgebookdata;
    short[] forgebook = new short[]{260, 261, 258};
    public static final int UI_LEVEL_1_MENU_ITEM_OFFSET_Y = 0;
    String armforgecity;
    final String[] citynames = new String[]{"\u65b0\u9521\u5b89", "\u4e94\u56fd\u57ce", "\u6d77\u62c9\u5c14", "\u963f\u65af\u5854\u7eb3"};
    byte forgebookstate;
    byte forgebookindex;
    byte forgebookselectindex;
    byte forgebookstartindex;
    byte forgebookusetotal;
    byte currentseleckarmname;
    byte packboxindex;
    String[] packboxoption = new String[]{"\u9053\u5177\u80cc\u5305", "\u88c5\u5907\u80cc\u5305", "\u5750\u9a91\u67e5\u770b"};
    byte missionrewardstate;
    boolean isshowmissionreward;
    String showmissionrewardname;
    byte[] showmissionrewarddata;
    byte[] showmissionrewardtype;
    int showmissionrewardmoney;
    int showmissionrewardtime;
    byte[] rideiconindex = new byte[]{48, 50, 49};
    boolean isshowmissioncommand;
    byte missioncommandstate;
    String missioncommandStr;
    byte showmissioncommandstate;
    byte showmissioncommandindex;
    String[] showmissioncommanddata = new String[]{"\u6269\u5145\u80cc\u5305", "\u6574\u7406\u80cc\u5305", "\u653e\u5f03\u5956\u52b1"};
    public static final short MISSION_REWARD_INFO_OFFSET_X = -30;
    public static final short MISSION_REWARD_INFO_OFFSET_Y = 10;
    public static final int MISSION_KUOBAO_MENU_ITEM_STEP = 70;
    public static final boolean \u63d0\u793a\u534a\u900f\u906e\u76d6 = true;
    byte cleaneventlastgamestate;
    public static final short CUR_ITEM_ICON_INFO_X = 508;
    public static final short CUR_ITEM_ICON_INFO_Y = 143;
    public static final short CUR_ITEM_NAME_INFO_X = 530;
    public static final short CUR_ITEM_NAME_INFO_Y = 186;
    public static final short CITY_INFO_OFFSET_X = 70;
    public static final short CITY_INFO_OFFSET_Y = 14;
    String[] cityintrooption = new String[]{"\u5b58\u50a8\u8fdb\u5ea6", "\u57ce\u5e02\u5347\u7ea7", "\u91cd\u7f6e\u6280\u80fd\u70b9"};
    public static final short CITY_INTRO_OFFSET_X = 0;
    public static final short CITY_INTRO_OFFSET_Y = 0;
    byte cityIntroBoxState;
    byte cityIntroBoxIndex;
    byte cityIntroUseTotal = (byte)13;
    int resetmoney;
    final short[][] upgradeCityMoney = new short[][]{{800, 3000}, {1400, 2600}, {1500, 2800}, {1400, 2500}, {1600, 3000}};
    byte[][][] mineDropData = new byte[][][]{new byte[][]{{24, 5, 34, 5, 37, 10, 35, 10, 29, 10, -1, 0}, {42, 5, 38, 10, 39, 10, 31, 10, 24, 10, -1, 0}}, new byte[][]{{30, 5, 25, 5, 36, 10, 32, 10, 27, 10, -1, 0}, {43, 5, 41, 5, 40, 10, 33, 10, 30, 10, 34, 10}}};
    public static final short MINE_DATA_OFFSET_X = 30;
    public static final short MINE_DATA_OFFSET_Y = 30;
    public static final short MINE_DATA_LEFT_OFFSET_X = 20;
    public static final short MINE_DATA_RIGHT_OFFSET_X = 10;
    byte mineUiState;
    byte mineUiIndex;
    byte mineUiSeletedIndex;
    byte mineUiStartIndex;
    final byte[][] mineWorkerTotal = new byte[][]{{3, 6, 10}, {4, 7, 10}};
    byte[] mineWorkerNum = new byte[2];
    final short WORKERPRICE = (short)200;
    final short INCOME = (short)3;
    final short GETINTIME = (short)10;
    short[] currentGetMoney = new short[2];
    short[] tempGetMoney = new short[2];
    byte mineIndex;
    byte[] currentMineMaterialName = new byte[2];
    byte[] tempMineMaterialName = new byte[2];
    byte[] isCanRandom = new byte[2];
    byte[][] skillStructure = new byte[][]{{0, 1, 2, 3, 4, 8, 9, 10, 13, 14, 16}, {0, 1, 2, 3, 4, 5, 6, 7, 11, 12, 16}};
    byte[][] skillIndexData = new byte[][]{{0, 13, 3, 1, 14, -1, -1, -1, 4, 2, 15, -1, -1, 16, 5, -1, 20, -1}, {6, 17, 10, 7, 18, 11, 8, 19, -1, -1, -1, 12, 9, -1, -1, -1, 20, -1}};
    int choiceX;
    int choiceSpeedX = 1;
    static final int CHOICEY = 152;
    static final int TISHI_Y = 133;
    short[][] skillboxdata = new short[][]{{260, 281}, {280, 261}};
    int[][] skillpointpos = new int[][]{{25, 252}, {160, 252}};
    byte[] skillicondata = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 49, 20};
    byte[] fightskillicon = new byte[]{25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, -1, -1, -1, -1, -1, -1, -1, 48};
    byte[] fightrideicon = new byte[]{43, 44, 45};
    byte[] skillopenaction = new byte[]{20, 39, 40, 41};
    byte[] skillupaction = new byte[]{39, 40, 41, 41};
    public static final int SKILL_ITEM_STEP_X = 110;
    public static final int SKILL_ITEM_STEP_Y = 30;
    public static final short COMMAND_CHOICE_STRING_OFFSET_X = 0;
    public static final short COMMAND_CHOICE_STRING_OFFSET_Y = -12;
    public static final short COMMAND_CHOICE_STEP_X = 0;
    public static final short COMMAND_CHOICE_STEP_Y = 45;
    public static final int SKILL_ICON_OFFSET_X = 0;
    public static final int SKILL_ICON_OFFSET_Y = 0;
    public static final int SKILL_ICON_X = 195;
    public static final int SKILL_ICON_Y = 50;
    public static final short SKILL_DATA_INFO_OFFSET_X = 70;
    public static final short SKILL_DATA_INFO_OFFSET_Y = 10;
    short[][] skillLineData = new short[][]{{205, 74, 5, 6, 315, 74, 5, 6, 425, 74, 5, 39, 205, 103, 5, 39, 315, 164, 5, 6, 425, 133, 5, 39, 221, 151, 83, 5}, {205, 74, 5, 6, 315, 74, 5, 6, 425, 74, 5, 6, 205, 104, 5, 6, 425, 103, 5, 39, 205, 133, 5, 39, 221, 120, 83, 5, 331, 60, 83, 5}};
    byte skillBoxState = 0;
    byte skillBoxIndex;
    byte skillBoxSeletedIndex;
    byte skillcurrentactionstate;
    byte skillframeindex;
    byte[][][] skillKeyData = new byte[][][]{new byte[][]{{-1, -1, -1, 0, 1, -1, -1, -1, 2, 3, 4, -1, -1, 10, 8, -1, 13, -1}, {3, 4, 8, 9, 10, -1, -1, -1, 14, 13, 13, -1, -1, 16, -1, -1, -1, -1}, {-1, 0, 1, -1, 3, -1, -1, -1, 4, -1, 9, -1, -1, 9, 13, -1, 9, -1}, {1, 2, -1, 4, 8, -1, -1, -1, -1, 10, 14, -1, -1, 14, -1, -1, 14, -1}}, new byte[][]{{-1, -1, -1, 0, 1, 2, 3, 4, -1, -1, -1, 5, 6, -1, -1, -1, 7, -1}, {3, 4, 5, 6, 7, 11, 12, 16, -1, -1, -1, 16, 16, -1, -1, -1, -1, -1}, {-1, 0, 1, -1, 3, 4, -1, 6, -1, -1, -1, 7, -1, -1, -1, -1, 12, -1}, {1, 2, -1, 4, 5, -1, 7, 11, -1, -1, -1, -1, 7, -1, -1, -1, 11, -1}}};
    public static final short RIDE_STEP = 150;
    public static final short RIDE_OFFSET_X = 70;
    public static final short RIDE_OFFSET_Y = 30;
    final int RIDEBASELIFEX;
    final int RIDEBASELIFEY;
    final int RIDEBASELIFEW;
    final int RIDEBASELIFEH;
    String[] rideinfo = new String[]{"\u72fc\uff1a\u66fe\u7ecf\u662f\u5c3c\u5e03\u4e00\u4e16\u5750\u9a91\u7684\u540e\u4ee3\uff0c\u53ea\u4f1a\u628a\u62e5\u6709\u5b9e\u529b\u5f3a\u5927\u7684\u5929\u624d\u8ba4\u4f5c\u81ea\u5df1\u7684\u4e3b\u4eba\uff0c\u62e5\u6709\u51b2\u649e\u4e0e\u547c\u5524\u72fc\u7fa4\u6280\u80fd\u3002", "\u673a\u68b0\u88c5\u7532\uff1a\u6218\u4e89\u4e2d\u622a\u83b7\u7684\u67d0\u56e2\u4f53\u7532\u80c4\uff0c\u79d1\u6280\u542b\u91cf\u975e\u5e38\u9ad8\uff0c\u62e5\u6709\u5e03\u96f7\u4e0e\u5149\u901f\u7a7f\u523a\u6280\u80fd\u3002", "\u706b\u9f99\uff1a\u4f01\u4e1a\u8054\u76df\u660e\u4ee4\u7981\u6b62\u4e70\u5356\u6b64\u7269\u79cd\uff0c\u56e0\u4e3a\u5b83\u7684\u80fd\u91cf\u8fdc\u8fdc\u8d85\u8fc7\u60f3\u8c61\uff0c\u62e5\u6709\u51b2\u649e\u4e0e\u5730\u72f1\u706b\u6280\u80fd\u3002"};
    byte rideBoxState;
    byte rideBoxSeletedIndex;
    byte changeRideImageTime;
    final byte CHANGERIDETOTAL = (byte)3;
    final byte CLEARRIDETOTAL = (byte)2;
    String[][] iteminfo;
    String[][] skillinfo;
    final byte[] skillLevel = new byte[]{2, 3, 5, 2, 4, 6, 2, 3, 4, 6, 2, 3, 5, 2, 3, 4, 5, 2, 3, 4};
    byte[] plusData = new byte[]{0, 0, 0, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 8, 15, 15, 13, 13, 13, 11, 10, 10, 9, 9, 12, 14, 14, 16};
    Image[] visualKeyboardImage;
    short[] visualKeyboardModules;
    short[][] visualKeyboardFrames;
    short[][] visualKeyboardActions;
    byte visualKeyboardActionIndex;
    byte visualKeyboardActionFrameIndex;
    byte visualKeyboardFrameDelay;
    boolean isVisualKeyboardActionOver;
    byte[] visualKeyboardImgData = new byte[]{45, 46, 47};
    public static int[][] visualKeyActionMap;
    public static int[][] visualDirectionKeyActionMap;
    public static final short VISUAL_KEY_VALUE_INDEX = 0;
    public static final short VISUAL_KEY_POSX_INDEX = 1;
    public static final short VISUAL_KEY_POSY_INDEX = 2;
    public static final short VISUAL_KEY_TARNS_INDEX = 3;
    public static final short VISUAL_KEY_PRESSED_INDEX = 4;
    public static final short VISUAL_KEY_RELEASED_INDEX = 5;
    public static final short VISUAL_KEY_HOLD_INDEX = 6;
    public static final short VISUAL_KEY_NO_FLAG = 0;
    public static final short VISUAL_KEY_YES_FLAG = 1;
    public static final short NULL = 0;
    Image[] fightUiImage;
    short[] fightUiModules;
    short[][] fightUiFrames;
    short[][] fightUiActions;
    byte fightUiActionIndex;
    byte fightUiActionFrameIndex;
    byte fightUiFrameDelay;
    boolean isFightUiActionOver;
    byte skilliconActionIndex;
    byte skilliconActionFrameIndex;
    byte skilliconFrameDelay;
    boolean isskilliconactionover;
    byte[] fightUiImgData = new byte[]{15, 16, 13, 28, 30, 31, 33, 34, 37, 38, 40};
    Image[] fightEnemyHeadImage = new Image[23];
    byte[] headImageIndex = new byte[]{-1, 0, 0, 1, 1, 2, 2, 3, 3, 16, 4, 4, 7, 7, 7, 7, 6, 6, 5, 5, 7, 7, 7, 7, 6, 6, 5, 5, 8, 8, 9, 9, 10, 10, 11, 11, 11, 11, 12, 12, 17, 17, 13, 13, 14, 14, 15, 15, -1, -1, -1, -1, 22, 19, 20, 21, -1, 18};
    int skill7cliph;
    int skill9cliph;
    int lifecliph;
    int magiccliph;
    final int SKILLCDH;
    byte playerskill7state;
    byte playerskill9state;
    byte[] defaultindex = new byte[]{-1, 0, 1, 2};
    byte changeweaponactionstate;
    public static final short PLAYER_CURRENT_WEAPON_CHANGE_OFFSET_X = 400;
    public static final short PLAYER_CURRENT_WEAPON_CHANGE_OFFSET_Y = 40;
    int changeweaponshowtime;
    boolean isshowchangeicon;
    int commandY;
    int commandwaittime;
    boolean showStaticCommandFlag = false;
    public static final int FIGHT_UI_ENEMY_OFFSET_X = 173;
    public static final int FIGHT_UI_ENEMY_OFFSET_Y = -260;
    public static final int FIGHT_UI_NAME_OFFSET_X = -20;
    int ridelifeBackgroundY;
    final byte RIDELIFEY = (byte)29;
    final short PLAYERLIFEW = (short)95;
    final short PLAYERLIFEX = (short)48;
    final short PLAYERLIFEY = (short)25;
    final short PLAYERMAGICW = (short)85;
    final short PLAYERMAGICX = (short)48;
    final short PLAYERMAGICY = (short)34;
    final short PLAYEREXPW = (short)81;
    final short PLAYEREXPX = (short)48;
    final short PLAYEREXPY = (short)41;
    final short ENEMYLIFEW = (short)70;
    final short BOSSLIFEW = (short)106;
    final short ENEMYLIFEX = (short)287;
    final short BOSSLIFEX = (short)251;
    final short INITLIFEY = (short)48;
    byte fightUiState;
    int fightUiStateTime;
    byte beHurtIndex;
    byte beHurtType;
    byte[] forgeState = new byte[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    final byte[][][] needcitylevel = new byte[][][]{new byte[][]{{0, 0, 0, 1, 1, 2}, {0, 0, 0, 0, 1, 2, 2}, {0, 0, 1, 1, 1, 2, 2, 2, 2}}, new byte[][]{{0, 1, 1, 2}, {0, 0, 1, 2, 2}, {0, 0, 1, 1, 1, 1, 2, 2, 2}}, new byte[][]{{0, 1, 2}, {1, 1, 2}, {0, 0, 0, 1, 1, 1, 1, 1, 2}}, new byte[][]{{1, 2, 2}, {0, 0, 1, 1}, {0, 0, 1, 1, 2, 2, 2, 2, 2, 2}}};
    byte[][][] allForgeArm = new byte[][][]{new byte[][]{{44, 45, 46, 49, 52, 58}, {59, 60, 61, 62, 67, 72, 73}, {88, 95, 110, 93, 121, 94, 109, 115, 120}}, new byte[][]{{46, 50, 53, 55}, {60, 62, 64, 69, 71}, {98, 103, 89, 96, 106, 113, 117, 122, 123}}, new byte[][]{{47, 51, 57}, {63, 68, 70}, {93, 105, 112, 91, 101, 108, 116, 119, 102}}, new byte[][]{{48, 54, 56}, {60, 62, 65, 66}, {107, 118, 99, 104, 90, 92, 97, 100, 111, 114}}};
    final byte[] needBook = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 123};
    final byte[][] forgeMaterial = new byte[][]{{14, 15}, {14, 15}, {15, 16}, {14, 23}, {23, 25}, {19, 18}, {29, 27}, {21, 32}, {28, 22}, {20, 30}, {35, 36}, {36, 39}, {35, 33}, {18, 38}, {41, 43}, {14, 15}, {14, 15}, {15, 16}, {23, 17}, {16, 25}, {26, 30}, {19, 24}, {18, 25}, {28, 29}, {19, 27}, {29, 34}, {35, 39}, {32, 33}, {31, 38}, {41, 42}, new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], {14, 15}, {19, 16}, {33, 35}, {23, 26}, {18, 29}, {15, 25}, {35, 29}, {14, 23}, {18, 16}, {36, 39}, {23, 25}, {22, 32}, {38, 31}, {14, 26}, {37, 31}, {32, 19}, {40, 30}, {22, 26}, {27, 36}, {25, 15}, {22, 18}, {27, 20}, {32, 29}, {31, 37}, {16, 15}, {17, 32}, {35, 36}, {41, 30}, {18, 32}, {39, 31}, {23, 26}, {26, 35}, {42, 38}, {25, 16}, {35, 31}, {43, 42}};
    final byte[][] forgeMaterialNum = new byte[][]{{5, 5}, {7, 7}, {20, 5}, {30, 8}, {15, 10}, {12, 15}, {10, 10}, {15, 15}, {15, 15}, {25, 5}, {7, 7}, {10, 7}, {10, 9}, {40, 5}, {5, 5}, {4, 4}, {10, 10}, {15, 5}, {15, 4}, {20, 5}, {15, 3}, {18, 3}, {20, 7}, {15, 7}, {20, 15}, {10, 5}, {5, 3}, {22, 7}, {10, 3}, {3, 4}, new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], new byte[0], {5, 2}, {5, 3}, {2, 3}, {5, 3}, {6, 3}, {5, 2}, {2, 3}, {5, 3}, {5, 2}, {2, 1}, {3, 2}, {3, 2}, {1, 3}, {5, 5}, {3, 5}, {4, 5}, {2, 3}, {1, 3}, {3, 2}, {3, 5}, {4, 6}, {10, 7}, {10, 20}, {15, 15}, {3, 5}, {5, 3}, {3, 3}, {7, 10}, {7, 7}, {10, 7}, {3, 3}, {5, 3}, {3, 5}, {3, 5}, {5, 3}, {7, 5}};
    final byte ITEMPACKMAXNUM = (byte)56;
    final byte INFORMATIONNUM = (byte)40;
    byte itemPackNum = (byte)16;
    byte[][] itemPack;
    byte itemPackUseNum;
    short[] itemTotal;
    byte[] itemLevel = new byte[]{12, 11, 11, 11, 10, 10, 10, 9, 9, 8, 8, 8, 7, 7, 1, 1, 2, 2, 3, 3, 4, 3, 3, 1, 3, 3, 2, 3, 4, 3, 4, 4, 3, 5, 5, 4, 4, 4, 5, 4, 5, 6, 6, 6, 1, 2, 2, 2, 3, 3, 4, 3, 3, 4, 4, 5, 5, 5, 6, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 1, 2, 4, 5, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 2, 3, 4, 3, 4, 2, 4, 2, 3, 4, 2, 3, 4, 3, 4, 3, 4, 2, 4, 2, 3, 4, 4, 5, 2, 3, 4, 5, 4, 5, 2, 4, 5, 2, 4, 6};
    byte[][] informationPack;
    byte[][] weaponPack;
    byte weaponPackUseNum;
    short[] addforeverdata = new short[3];
    final short[] itemUsedData = new short[]{0, 10000, 20000, 30000, 1400, 2200, 4400, 0, 0, 10, 8, 100, 600, 150};
    byte[][] suitPack;
    byte suitPackUseNum;
    byte[][] plusPack;
    byte plusPackUseNum;
    int playerMoney;
    byte addState;
    byte useitemstate;
    boolean ishuicheng = true;
    byte useplayerindex;
    byte forgeIndex = 0;
    byte changeArmIndex;
    int tempplusdata;
    String[][] mainmissioninfo;
    String[][] missioninfo;
    short[][] missiondata0;
    short[][] missiondata1;
    byte currentmainmissionindex = (byte)-1;
    byte[] currentmissionindex;
    byte mainmissionstate = 1;
    byte[] missionstate = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    byte[] missioncity = new byte[]{0, 0, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3};
    byte[][] allmissionlist = new byte[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8, 9}, {10, 11, 12, 13}};
    short[] mainmissionplan;
    short[][] missionplan;
    byte missionfinishedindex = (byte)-1;
    String[] missionlist;
    byte[] missionindexlist;
    static final byte mlistshownum = 6;
    boolean isjieshou;
    byte[] currentreward;
    byte[] currentrewardtype;
    short currentrewardmoney;
    boolean isshowmissionplan;
    byte showplantime;
    byte showplantype;
    byte[] showplanindex = new byte[2];
    short showframeindex;
    byte[] showframedata = new byte[]{31, 32, 33, 35, 33, 34};
    boolean isshowframeover;
    public static final int MAIN_MISSION_TYPE = 0;
    public static final int EXTR_MISSION_TYPE = 1;
    public static final short MISSION_JINDU_INFO_STEP_OFFSET_Y = 3;
    public static final int MISSION_PLAN_OFFSET_X = 0;
    public static final int MISSION_PLAN_OFFSET_Y = 25;
    final short missionPlanLength = (short)160;
    int[] missionPlanScreenData = new int[160];
    public static final int MISSION_FINISH_INFO_OFFSET_Y = -45;
    boolean isshowmineplan;
    byte mineplantime;
    byte showmineindex;
    short[] showminemoney = new short[2];
    byte[] showminematerial = new byte[2];
    final String[] cityname = new String[]{"\u65b0\u9521\u5b89", "\u6d77\u62c9\u5c14"};
    int wordmovex;
    int wordw;
    String currentmoveword;
    byte[] showminestate = new byte[2];
    int[] minePlanScreenData = new int[640];
    String[] talkWord;
    String talkname = null;
    byte talkStart;
    byte talkHeadIndex;
    byte talkWordLen;
    byte talkWordTotal;
    byte talkWaitTime;
    byte talkstate;
    byte talkFaceTo;
    Image playImage;
    Image headImage;
    int[] heady = new int[]{276, 256, 256, 256, 256, 256, 256, 256};
    int talkframetime;
    public static final int TALK_UI_OFFSET_X = 40;
    String[] asideword;
    byte asideindex;
    byte asidecounter;
    byte asidestate;
    byte showtime;
    byte asideStart;
    byte asideTotal;
    short asidestarty;
    Image chatuimage;
    final byte ASIDETOTAL = (byte)5;
    public static final short ASIDE_START_Y = 120;
    int asideTime;
    Image introImage;
    String[] introWord;
    byte introStart;
    byte introHeadIndex;
    byte introWordLen;
    byte introWordTotal;
    byte introtype;
    public static final int INFO_OFFSET_Y = 30;
    int keynum;
    boolean isshowfighttishi;
    byte drawtishistate;
    byte[][] currentItemBox;
    byte[][] currentWeaponPack;
    byte[][] currentSuitPack;
    byte[][] currentPlusPack;
    byte[] tempPlayerCurrentWeapon;
    byte tempPlayerCurrentWeaponType;
    byte tempPlayerCurrentSuit;
    byte[] tempPlayerCurrentPlus;
    byte[] tempPlayerCurrentWeaponIndex;
    short[] tempPlayerCurrentWeaponAttack;
    short[] tempPlayerCurrentWeaponBaojiOdds;
    short[] tempPlayerCurrentWeaponBaojiAttack;
    byte[][] tempSkillState;
    short[][] tempweaponCriticalKey7;
    short[][] tempweaponCriticalKey9;
    int[] tempweaponActionIndex;
    int[] playertempdata;
    int tempmoney;
    byte temprideindex;
    short[] tempridelife;
    byte temprolestate;

    public GameUI(GameCanvas canvas) {
        this.ITEMBOXCOMMANDTOTAL1 = 1;
        this.CITYINTROBOXCOMMANDTOTAL = 1;
        this.GAMEMENUX = 215;
        this.GAMEMENUY = 54;
        this.ARM_X = 220;
        this.ARM_Y = 60;
        this.LAOHUJIX1 = 120;
        this.LAOHUJIX2 = 135;
        this.LAOHUJIX3 = 150;
        this.OFFSETSPEEDX = 80;
        this.PLAYERSAVEMONEY = 8;
        this.RIDEBASELIFEX = 70;
        this.RIDEBASELIFEY = 138;
        this.RIDEBASELIFEW = 140;
        this.RIDEBASELIFEH = 10;
        this.SKILLCDH = 11;
        this.canvas = canvas;
        this.initUi();
        this.initUiBack();
    }

    public void drawUi() {
        switch (this.uiState) {
            case 0: {
                this.drawSystem();
                this.drawPlayerMoney();
                break;
            }
            case 1: {
                this.drawGameMenu();
                this.drawPlayerMoney();
                break;
            }
            case 2: {
                this.canvas.drawGameSet();
                break;
            }
            case 3: {
                this.canvas.drawGameHelp();
                break;
            }
            case 4: {
                this.drawPropertyBox();
                this.drawPlayerMoney();
                break;
            }
            case 5: {
                this.drawItemBox();
                this.drawPlayerMoney();
                break;
            }
            case 6: {
                this.drawArmBox();
                this.drawPlayerMoney();
                break;
            }
            case 7: {
                this.drawMissionUiBox();
                this.drawPlayerMoney();
                break;
            }
            case 8: {
                this.drawConvenient();
                this.drawPlayerMoney();
                break;
            }
            case 9: {
                this.drawSkillBox();
                this.drawPlayerMoney();
                break;
            }
            case 10: {
                this.drawRideBox();
                this.drawPlayerMoney();
                break;
            }
            case 11: {
                this.drawCityIntroBox();
                this.drawPlayerMoney();
                break;
            }
            case 12: {
                this.drawTownhouseBox();
                this.drawPlayerMoney();
                break;
            }
            case 13: {
                this.drawShop();
                this.drawPlayerMoney();
                break;
            }
            case 14: {
                this.drawForgeBox();
                this.drawforgeboxshownews();
                this.drawPlayerMoney();
                break;
            }
            case 15: {
                this.drawMineUi();
                this.drawPlayerMoney();
                break;
            }
            case 17: {
                this.drawshowmission();
                break;
            }
            case 18: {
                this.drawsaveui();
                break;
            }
            case 19: {
                this.drawforgebook();
                break;
            }
            case 20: {
                this.drawpackbox();
                break;
            }
            case 21: {
                this.drawCleanItemBox();
                break;
            }
            case 22: {
                this.drawCleanArmBox();
                break;
            }
            case 16: {
                this.drawStaticUiBack((short)0);
                switch (this.uiActionType) {
                    case 0: {
                        switch (this.uiLastState) {
                            case 0: {
                                break;
                            }
                            case 1: {
                                break;
                            }
                            case 2: {
                                break;
                            }
                            case 3: {
                                break;
                            }
                            case 4: {
                                break;
                            }
                            case 5: {
                                this.drawStaticUi((short)88);
                                this.drawlabels(this.itemboxdata, (byte)0);
                                this.drawStaticUi((short)16, 0, 360);
                                break;
                            }
                            case 6: {
                                this.drawStaticUi(this.armBoxIndex);
                                this.drawlabels1(this.armboxdata, this.armBoxIndex);
                                this.drawStaticUi((short)16, 0, 360);
                                break;
                            }
                            case 7: {
                                this.drawStaticUi((byte)(this.missionUiBoxIndex + 8));
                                this.drawlabels(this.missionuiboxdata, this.missionUiBoxIndex);
                                switch (this.missionUiBoxState) {
                                    case 0: {
                                        this.drawStaticUi((short)16, 0, 360);
                                        break;
                                    }
                                    case 1: {
                                        this.drawStaticUi((short)84, 0, 360);
                                        break;
                                    }
                                    case 2: {
                                        this.drawStaticUi((short)29, 0, 360);
                                        break;
                                    }
                                    case 3: {
                                        this.drawStaticUi((short)30, 0, 360);
                                    }
                                }
                                break;
                            }
                            case 8: {
                                this.drawStaticUi((short)88);
                                this.drawUiModule(this.convenientIndex + 46, LABEL_X[0][0], 31, 36, 0);
                                this.drawStaticUi((short)16, 0, 360);
                                break;
                            }
                            case 9: {
                                this.drawStaticUi((short)(this.skillBoxIndex + 92));
                                this.drawlabels(this.skillboxdata, this.skillBoxIndex);
                                this.drawStaticUi((short)16, 0, 360);
                                this.drawPlayerCurrentSkillPoint();
                                break;
                            }
                            case 11: {
                                switch (this.cityIntroBoxIndex) {
                                    case 0: {
                                        this.drawStaticUi((short)88);
                                        this.drawUiModule(58, 11, 31, 36, 0);
                                        this.drawStaticUi((short)117, 0, 360);
                                        break;
                                    }
                                    case 2: {
                                        this.drawStaticUi((short)88);
                                        this.drawUiModule(59, LABEL_X[0][0], 31, 36, 0);
                                        this.drawStaticUi((short)16, 0, 360);
                                    }
                                }
                                break;
                            }
                            case 12: {
                                break;
                            }
                            case 13: {
                                this.drawStaticUi((short)88);
                                this.drawUiModule(this.shopIndex + 46, LABEL_X[0][0], 31, 36, 0);
                                this.drawStaticUi((short)16, 0, 360);
                                break;
                            }
                            case 14: {
                                switch (this.forgeBoxState) {
                                    case 1: 
                                    case 2: 
                                    case 3: {
                                        this.drawStaticUi((short)88);
                                        this.drawUiModule(256, LABEL_X[0][0] - 2, 42, 36, 0);
                                        this.drawStaticUi((short)16, 0, 360);
                                        break;
                                    }
                                    case 4: {
                                        this.drawStaticUi(this.forgeShopIndex);
                                        this.drawlabels(this.armboxdata, this.forgeShopIndex);
                                        this.drawStaticUi((short)16, 0, 360);
                                        break;
                                    }
                                    case 5: 
                                    case 6: {
                                        this.drawStaticUi((short)(this.materialShopIndex + 8));
                                        this.drawlabels(this.materialsaledata, this.materialShopIndex);
                                        this.drawStaticUi((short)16, 0, 360);
                                    }
                                }
                                break;
                            }
                        }
                        this.drawUiSprite();
                        break;
                    }
                    case 1: {
                        this.drawStaticUi(this.forgeBoxIndex);
                        this.drawlabels(this.forgeboxdata, this.forgeBoxIndex);
                        this.drawStaticUi((short)16, 0, 360);
                        this.drawUiModule(0, this.seletedBoxX, this.seletedBoxY, 20, 0);
                    }
                }
                this.drawPlayerMoney();
            }
        }
        this.drawKuoBaoTishi();
    }

    public void GameUiKeyProcess() {
        if (this.isDrawKuoBaoTishi) {
            this.kuoBaoKeyProcess();
        } else {
            switch (this.uiState) {
                case 0: {
                    this.systemKeyProcess();
                    break;
                }
                case 1: {
                    this.gameMenuKeyProcess();
                    break;
                }
                case 2: {
                    this.canvas.gameSetKeyProcess();
                    break;
                }
                case 3: {
                    this.canvas.helpKeyProcess();
                    break;
                }
                case 4: {
                    this.propertyBoxKeyProcess();
                    break;
                }
                case 5: {
                    this.itemBoxKeyProcess();
                    break;
                }
                case 6: {
                    this.armKeyProcess();
                    break;
                }
                case 7: {
                    this.missionUiBoxKeyProcess();
                    break;
                }
                case 8: {
                    this.convenientKeyProcess();
                    break;
                }
                case 9: {
                    this.skillBoxKeyProcess();
                    break;
                }
                case 10: {
                    this.rideBoxKeyProcess();
                    break;
                }
                case 11: {
                    this.cityIntroBoxKeyProcess();
                    break;
                }
                case 12: {
                    this.townhouseBoxKeyProcess();
                    break;
                }
                case 13: {
                    this.shopKeyProcess();
                    break;
                }
                case 14: {
                    this.forgeBoxKeyProcess();
                    break;
                }
                case 15: {
                    this.mineUiKeyProcess();
                    break;
                }
                case 17: {
                    this.showmissionkeyprocess();
                    break;
                }
                case 18: {
                    this.saveuikeyprocess();
                    break;
                }
                case 19: {
                    this.forgebookprocess();
                    break;
                }
                case 20: {
                    this.packboxkeyprocess();
                    break;
                }
                case 21: {
                    this.cleanItemBoxKeyProcess();
                    break;
                }
                case 22: {
                    this.cleanArmBoxKeyProcess();
                }
            }
        }
    }

    public void GameUiProcess() {
        block28: {
            this.commandProcess();
            block0 : switch (this.uiState) {
                case 0: {
                    this.systemProcess();
                    break;
                }
                case 1: {
                    this.gameMenuProcess();
                    break;
                }
                case 2: {
                    break;
                }
                case 3: {
                    break;
                }
                case 4: {
                    break;
                }
                case 5: {
                    break;
                }
                case 6: {
                    break;
                }
                case 7: {
                    break;
                }
                case 8: {
                    break;
                }
                case 9: {
                    break;
                }
                case 11: {
                    break;
                }
                case 12: {
                    break;
                }
                case 13: {
                    break;
                }
                case 14: {
                    break;
                }
                case 15: {
                    break;
                }
                case 16: {
                    switch (this.uiActionType) {
                        case 0: {
                            if (this.isActionOver) {
                                this.changeUiState(this.uiNewState);
                                break block0;
                            }
                            break block28;
                        }
                        case 1: {
                            if (this.isActionOver) {
                                this.changeUiState(this.uiNewState);
                                break block0;
                            }
                            switch (this.actionIndex) {
                                case 0: {
                                    if (this.seletedBoxX >= 180 && this.seletedBoxY <= 58) {
                                        this.seletedBoxX = 182;
                                        this.seletedBoxY = 56;
                                        this.isActionOver = true;
                                        break block0;
                                    }
                                    this.seletedBoxX = this.seletedBoxX + (182 - this.seletedBoxX) / 3 > 182 ? 182 : this.seletedBoxX + (182 - this.seletedBoxX) / 3;
                                    this.seletedBoxY = this.seletedBoxY - Math.abs(56 - this.seletedBoxY) / 3 < 56 ? 56 : this.seletedBoxY - Math.abs(56 - this.seletedBoxY) / 3;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void changeUiState(byte state) {
        this.uiLastState = this.uiState;
        this.uiState = state;
        switch (this.uiState) {
            case 0: {
                break;
            }
            case 1: {
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: {
                this.backActionIndex = 1;
                this.backActionFrameIndex = 0;
                this.backFrameDelay = 0;
                break;
            }
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: {
                this.backActionIndex = 0;
                this.backActionFrameIndex = 0;
                this.backFrameDelay = 0;
                break;
            }
        }
    }

    public void changeActionUiState(byte state, byte index, byte type) {
        this.uiLastState = this.uiState;
        this.uiState = (byte)16;
        this.uiNewState = state;
        this.actionIndex = index;
        this.uiActionType = type;
        this.actionFrameIndex = 0;
        this.frameDelay = 0;
        this.isActionOver = false;
    }

    public void drawStaticUi(short frameindex) {
        for (int i = 0; i < this.selectFrames[frameindex].length; i += 4) {
            short moduleIndex = this.selectFrames[frameindex][i];
            this.canvas.drawModule(this.selectImages[this.selectModules[moduleIndex * 5]], this.selectFrames[frameindex][i + 1], (int)this.selectFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.selectFrames[frameindex][i + 3]], 320, 180, this.selectModules, (int)moduleIndex);
        }
    }

    public void drawStaticUi(short frameindex, int faceto) {
        for (int i = 0; i < this.selectFrames[frameindex].length; i += 4) {
            short moduleIndex = this.selectFrames[frameindex][i];
            this.canvas.drawFModule(this.selectImages[this.selectModules[moduleIndex * 5]], this.selectFrames[frameindex][i + 1], this.selectFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.selectFrames[frameindex][i + 3]], 320, 180, faceto, this.selectModules, moduleIndex);
        }
    }

    public void drawStaticUi(short frameindex, int x, int y) {
        for (int i = 0; i < this.selectFrames[frameindex].length; i += 4) {
            short moduleIndex = this.selectFrames[frameindex][i];
            this.canvas.drawModule(this.selectImages[this.selectModules[moduleIndex * 5]], this.selectFrames[frameindex][i + 1], (int)this.selectFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.selectFrames[frameindex][i + 3]], x, y, this.selectModules, (int)moduleIndex);
        }
    }

    public void drawUiSprite() {
        short frameindex = this.selectActions[this.actionIndex][this.actionFrameIndex + 1];
        short moduleIndex = 0;
        for (int i = 0; i < this.selectFrames[frameindex].length; i += 4) {
            moduleIndex = this.selectFrames[frameindex][i];
            this.canvas.drawModule(this.selectImages[this.selectModules[moduleIndex * 5]], this.selectFrames[frameindex][i + 1], (int)this.selectFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.selectFrames[frameindex][i + 3]], 320, 180, this.selectModules, (int)moduleIndex);
        }
        this.nextUiFrame();
    }

    public void drawUiSprite(byte actionIndex, int x, int y) {
        short frameindex = this.selectActions[actionIndex][this.actionFrameIndex + 1];
        short moduleIndex = 0;
        for (int i = 0; i < this.selectFrames[frameindex].length; i += 4) {
            moduleIndex = this.selectFrames[frameindex][i];
            this.canvas.drawModule(this.selectImages[this.selectModules[moduleIndex * 5]], this.selectFrames[frameindex][i + 1], (int)this.selectFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.selectFrames[frameindex][i + 3]], x, y, this.selectModules, (int)moduleIndex);
        }
        this.nextUiFrame(actionIndex);
    }

    public void nextUiFrame(byte actionIndex) {
        this.frameDelay = (byte)(this.frameDelay + 1);
        if (this.frameDelay == this.selectActions[actionIndex][this.actionFrameIndex]) {
            this.frameDelay = 0;
            this.actionFrameIndex = (byte)(this.actionFrameIndex + 2);
            if (this.actionFrameIndex == this.selectActions[actionIndex].length) {
                this.actionFrameIndex = 0;
                this.isActionOver = true;
            }
        }
    }

    public void nextUiFrame() {
        this.frameDelay = (byte)(this.frameDelay + 1);
        if (this.frameDelay == this.selectActions[this.actionIndex][this.actionFrameIndex]) {
            this.frameDelay = 0;
            this.actionFrameIndex = (byte)(this.actionFrameIndex + 2);
            if (this.actionFrameIndex == this.selectActions[this.actionIndex].length) {
                this.actionFrameIndex = 0;
                this.isActionOver = true;
            }
        }
    }

    public void drawStaticUiBack(short frameindex) {
        short moduleIndex = 0;
        for (int i = 0; i < this.backFrames[frameindex].length; i += 4) {
            moduleIndex = this.backFrames[frameindex][i];
            this.canvas.drawModule(this.backImages[this.backModules[moduleIndex * 5]], this.backFrames[frameindex][i + 1], (int)this.backFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.backFrames[frameindex][i + 3]], 320, 180, this.backModules, (int)moduleIndex);
        }
    }

    public void nextUiBackFrame(short actionIndex) {
        this.backFrameDelay = (byte)(this.backFrameDelay + 1);
        if (this.backFrameDelay == this.backActions[actionIndex][this.backActionFrameIndex]) {
            this.backFrameDelay = 0;
            this.backActionFrameIndex = (byte)(this.backActionFrameIndex + 2);
            if (this.backActionFrameIndex == this.backActions[actionIndex].length) {
                this.backActionFrameIndex = 0;
            }
        }
    }

    public void drawUiModule(int mindex, int x, int y, int anchor, int trans) {
        this.canvas.drawImage(this.selectImages[this.selectModules[mindex * 5]], x, y, anchor, trans, this.selectModules, mindex);
    }

    public void drawskillpointicon() {
        if (this.canvas.rolem.playerCurrentSkillPoint > 0 && !this.canvas.mapm.isEventPlaying) {
            this.drawUiModule(222, 10, 75, 3, 0);
        }
    }

    int drawNumber(int number, int x, int y, boolean isdrawbaifenhao, int trans) {
        int i;
        int modindex = 0;
        this.numberbit = 1;
        number = number > 999999 ? 999999 : number;
        this.uinumberindex[5] = (byte)((number %= 100000) / 100000);
        this.uinumberindex[4] = (byte)(number / 10000);
        this.uinumberindex[3] = (byte)(number % 10000 / 1000);
        this.uinumberindex[2] = (byte)(number % 1000 / 100);
        this.uinumberindex[1] = (byte)(number % 100 / 10);
        this.uinumberindex[0] = (byte)(number % 10);
        for (i = 0; i < this.uinumberindex.length; i = (int)((byte)(i + 1))) {
            if (this.uinumberindex[i] <= 0) continue;
            this.numberbit = (byte)(i + 1);
        }
        for (i = 0; i < this.numberbit; i = (int)((byte)(i + 1))) {
            modindex = (181 + this.uinumberindex[i]) * 5;
            this.canvas.drawImage(this.selectImages, x + (this.numberbit - 1) * this.selectModules[modindex + 3] - i * this.selectModules[modindex + 3], y, trans, 0, this.selectModules[modindex], this.selectModules[modindex + 1], this.selectModules[modindex + 2], this.selectModules[modindex + 3], this.selectModules[modindex + 4]);
        }
        if (isdrawbaifenhao) {
            modindex = 955;
            this.canvas.drawImage(this.selectImages, x + this.numberbit * 10, y, trans, 0, this.selectModules[modindex], this.selectModules[modindex + 1], this.selectModules[modindex + 2], this.selectModules[modindex + 3], this.selectModules[modindex + 4]);
        }
        this.drawUiModule(377, x - 40, y + 8, trans, 0);
        return (this.numberbit + (isdrawbaifenhao ? (byte)1 : 0)) * 10;
    }

    public void drawjiantou(int type, int faceto, int x, int y, int anthor) {
        switch (faceto) {
            case 0: {
                this.drawUiModule(type + 178, x, y - this.canvas.publiccounter / 2 % 2, anthor, 5);
                break;
            }
            case 1: {
                this.drawUiModule(type + 178, x, y + this.canvas.publiccounter / 2 % 2, anthor, 6);
                break;
            }
            case 2: {
                this.drawUiModule(type + 178, x - this.canvas.publiccounter / 2 % 2, y, anthor, 0);
                break;
            }
            case 3: {
                this.drawUiModule(type + 178, x + this.canvas.publiccounter / 2 % 2, y, anthor, 3);
                break;
            }
            case 4: {
                this.drawUiModule(248, x - this.canvas.publiccounter / 2 % 2, y, anthor, 3);
                break;
            }
            case 5: {
                this.drawUiModule(248, x + this.canvas.publiccounter / 2 % 2, y, anthor, 0);
                break;
            }
            case 6: {
                this.drawUiModule(248, x, y - this.canvas.publiccounter / 2 % 2, anthor, 6);
                break;
            }
            case 7: {
                this.drawUiModule(248, x, y + this.canvas.publiccounter / 2 % 2, anthor, 5);
            }
        }
    }

    public void setKey5(int x, int y, int z, boolean beAttackStateHintFlagtmp) {
        this.key5X = x;
        this.key5Y = y;
        this.key5Z = z;
        this.actionFrameIndex = 0;
        this.frameDelay = 0;
        this.isActionOver = false;
        this.isdrawkey5 = true;
        beAttackStateHintFlag = beAttackStateHintFlagtmp;
    }

    public void finishshowkey5() {
        this.isdrawkey5 = false;
        beAttackStateHintFlag = false;
    }

    public void drawKey5() {
        if (this.isdrawkey5 && !beAttackStateHintFlag) {
            this.canvas.gameUi.drawVisualKeyboardModule(this.canvas.rolem.playerCurrentWeaponType == 0 ? 27 : 28, this.key5X, this.key5Z - this.key5Y + this.canvas.publiccounter / 2 % 2, 33, 0);
        }
    }

    private void initUi() {
        Util.tempData = new byte[110000];
        Util.position[0] = 0;
        Util.getStream(Util.tempData, "/data/ui.data", 0);
        this.selectModules = Util.loadInfShort(Util.tempData, this.selectModules, Util.position);
        this.selectFrames = Util.loadInfShort(Util.tempData, this.selectFrames, Util.position);
        this.selectActions = Util.loadInfShort(Util.tempData, this.selectActions, Util.position);
        this.selectImages = this.createUiImage(this.uiImgData);
        Util.tempData = null;
    }

    private void initUiBack() {
        Util.tempData = new byte[10000];
        Util.position[0] = 0;
        Util.getStream(Util.tempData, "/data/uiBack.data", 0);
        this.backModules = Util.loadInfShort(Util.tempData, this.selectModules, Util.position);
        this.backFrames = Util.loadInfShort(Util.tempData, this.selectFrames, Util.position);
        this.backActions = Util.loadInfShort(Util.tempData, this.selectActions, Util.position);
        this.backImages = this.createUiImage(this.uiBackImgData);
        Util.tempData = null;
    }

    public Image[] createUiImage(byte[] data) {
        Image[] tempimg = new Image[data.length];
        for (int i = 0; i < data.length; ++i) {
            tempimg[i] = Util.createImage("/ui/" + data[i] + ".png");
        }
        return tempimg;
    }

    public void settishi(String word, byte type) {
        this.tishiword = this.canvas.formatzkword(word, 300, 0);
        this.tishistate = 0;
        this.tishitype = type;
        this.isshowtishi = true;
        this.initUiScreenData();
        this.frameDelay = 0;
        this.actionFrameIndex = 0;
        this.isActionOver = false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void tishiKeyProcess() {
        switch (this.tishitype) {
            case 0: {
                if (!Control.IsKeyDown(1) && !Control.IsKeyDown(-1)) return;
                this.isshowtishi = false;
                return;
            }
            case 1: {
                switch (this.tishistate) {
                    case 1: {
                        if (!Control.IsKeyDown(1) && !Control.IsKeyDown(-1)) return;
                        this.isshowtishi = false;
                    }
                }
            }
        }
    }

    public void drawtishi() {
        block11: {
            int tempy = 0;
            if (!this.isshowtishi) break block11;
            this.drawUiBack();
            block0 : switch (this.tishitype) {
                case 0: {
                    this.drawStaticUi((short)51);
                    if (this.tishiword.length > 1) {
                        tempy = (this.tishiword.length - 1) * (20 / this.tishiword.length);
                    }
                    this.canvas.drawZkStringArry(this.tishiword, 320, 133 - tempy - 5, 17, 0, this.tishiword.length, 0);
                    if (this.canvas.publiccounter / 10 % 2 != 0) break;
                    this.canvas.drawColorString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 320, 183, 33, 15597319, 0);
                    break;
                }
                case 1: {
                    switch (this.tishistate) {
                        case 0: {
                            this.canvas.gameUi.drawUiSprite((byte)3, 320, 180);
                            if (this.isActionOver) {
                                this.tishistate = 1;
                                break block0;
                            }
                            break block11;
                        }
                        case 1: {
                            this.drawStaticUi((short)52);
                            this.canvas.drawColorString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 320, 203, 17, 16775789, 0);
                            if (this.tishiword.length > 1) {
                                tempy = (this.tishiword.length - 1) * (20 / this.tishiword.length);
                            }
                            this.canvas.drawZkStringArry(this.tishiword, 320, 133 - tempy + 5, 17, 0, this.tishiword.length, 0);
                        }
                    }
                }
            }
        }
    }

    public void initUiScreenData() {
        for (int i = 0; i < this.canvas.screenData.length; ++i) {
            this.canvas.screenData[i] = -939524096;
        }
    }

    public void drawUiBack() {
        for (int i = 0; i < 360; ++i) {
            this.canvas.g.drawRGB(this.canvas.screenData, 0, 640, 0, 0 + i, 640, 1, true);
        }
    }

    private void drawSystem() {
        this.drawStaticUiBack((short)0);
        this.drawhintjiantou(320, 90, 36, 8, 5, this.systemMenuSelectIndex, systemMenuStartIndex, 10);
        for (int i = 4; i >= 0; --i) {
            if (i == this.systemMenuSelectIndex) {
                this.drawStaticUi((short)22, 215, 64 + this.systemSpeedY[this.systemMenuSelectIndex]);
            } else {
                this.drawStaticUi((short)21, 215, 64 + this.systemSpeedY[i]);
            }
            if (!this.isSystemActionOver) continue;
            if (i == this.systemMenuSelectIndex) {
                this.drawUiModule(this.systemdata[0][this.systemMenuSelectIndex + systemMenuStartIndex], 276, 69 + this.systemSpeedY[this.systemMenuSelectIndex], 20, 0);
                continue;
            }
            this.drawUiModule(this.systemdata[1][i + systemMenuStartIndex], 282, 69 + this.systemSpeedY[i], 20, 0);
        }
        this.drawStaticUi((short)16, 0, 360);
        if (this.isCommand) {
            this.drawUiBack();
            switch (this.commandState) {
                case 0: {
                    this.drawStaticUi((short)19);
                    this.canvas.drawColorString("\u662f\u5426\u8fd4\u56de\u4e3b\u83dc\u5355\uff1f", 320, 133, 17, 0xFFFFFF, 0);
                }
            }
        }
    }

    private void systemKeyProcess() {
        if (this.isSystemActionOver) {
            if (Control.IsKeyDown(4100) || Control.IsKeyHold(4100, 10)) {
                if (!this.isCommand) {
                    if (this.systemMenuSelectIndex > 0) {
                        this.systemMenuSelectIndex = (byte)(this.systemMenuSelectIndex - 1);
                    } else if (systemMenuStartIndex > 0) {
                        systemMenuStartIndex = (byte)(systemMenuStartIndex - 1);
                    } else if (this.systemMenuSelectIndex == 0 && systemMenuStartIndex == 0) {
                        this.systemMenuSelectIndex = (byte)4;
                        systemMenuStartIndex = 0;
                    }
                }
            } else if (Control.IsKeyDown(8448) || Control.IsKeyHold(8448, 10)) {
                if (!this.isCommand) {
                    if (this.systemMenuSelectIndex < 4) {
                        this.systemMenuSelectIndex = (byte)(this.systemMenuSelectIndex + 1);
                    } else if (this.systemMenuSelectIndex == 4 && systemMenuStartIndex == 0) {
                        this.systemMenuSelectIndex = 0;
                        systemMenuStartIndex = 0;
                    }
                }
            } else if (Control.IsKeyDown(327712)) {
                if (this.isCommand) {
                    switch (this.commandState) {
                        case 0: {
                            this.canvas.loadGameState(3, 7, (byte)0);
                            this.finishCommand();
                        }
                    }
                } else {
                    switch (this.systemMenuSelectIndex + systemMenuStartIndex) {
                        case 0: {
                            this.canvas.mapm.iscityflushinfo = true;
                            this.canvas.changeGameState(this.canvas.lastgamestate);
                            break;
                        }
                        case 1: {
                            if (this.canvas.rolem.yingxiangstate == 0) {
                                this.initConvenient();
                                this.changeUiState((byte)8);
                                break;
                            }
                            this.canvas.gameUi.settishi("\u5f53\u524d\u72b6\u6001\u65e0\u6cd5\u8fdb\u5165\u661f\u9645\u5546\u5e97\uff01", (byte)0);
                            break;
                        }
                        case 2: {
                            this.changeUiState((byte)2);
                            break;
                        }
                        case 3: {
                            this.canvas.gamehelpstate = 0;
                            this.canvas.gamehelpselectindex = 0;
                            this.changeUiState((byte)3);
                            break;
                        }
                        case 4: {
                            this.canvas.eventBackState = 0;
                            this.changeCommandState((byte)0);
                            this.startCommand();
                        }
                    }
                }
            } else if (Control.IsKeyDown(131072)) {
                if (this.isCommand) {
                    this.finishCommand();
                } else {
                    this.canvas.mapm.iscityflushinfo = true;
                    this.canvas.changeGameState(this.canvas.lastgamestate);
                }
            }
        }
    }

    public void initSystem() {
        this.systemSpeedY = new int[5];
        this.isSystemActionOver = false;
        this.systemMenuSelectIndex = 0;
        systemMenuStartIndex = 0;
        this.initUiScreenData();
    }

    private void systemProcess() {
        int state = 0;
        for (int i = 0; i < 5; ++i) {
            if (this.systemSpeedY[i] >= this.systemtempdata[i]) {
                if ((state = (int)((byte)(state + 1))) < 5) continue;
                this.isSystemActionOver = true;
                continue;
            }
            int n = i;
            this.systemSpeedY[n] = this.systemSpeedY[n] + this.systemspeed[i];
        }
    }

    private void drawGameMenu() {
        this.drawStaticUiBack((short)0);
        this.drawhintjiantou(320, 90, 36, 8, 5, this.gameMenuSelectIndex, gameMenuStartIndex, 10);
        for (int i = 4; i >= 0; --i) {
            if (i == this.gameMenuSelectIndex) {
                this.drawStaticUi((short)22, 215, 54 + this.gameMenuSpeedY[this.gameMenuSelectIndex] + 0);
            } else {
                this.drawStaticUi((short)21, 215, 54 + this.gameMenuSpeedY[i] + 0);
            }
            if (!this.isMenuActionOver) continue;
            if (i == this.gameMenuSelectIndex) {
                this.drawUiModule(this.menudata[0][this.gameMenuSelectIndex + gameMenuStartIndex], 276, 59 + this.gameMenuSpeedY[this.gameMenuSelectIndex] + 0, 20, 0);
                continue;
            }
            this.drawUiModule(this.menudata[1][i + gameMenuStartIndex], 282, 59 + this.gameMenuSpeedY[i] + 0, 20, 0);
        }
        this.drawStaticUi((short)16, 0, 360);
    }

    public void drawhintjiantou(int x, int y, int itemstep, int showitemamount, int totalitemamount, int startindex, int selectindex, int tiaozheng_y) {
        if (showitemamount < totalitemamount) {
            if (startindex > 0 || selectindex > 0) {
                this.canvas.gameUi.drawUiModule(178, x, y - menuSpeedX * hire, 17, 5);
            }
            if (startindex + selectindex < totalitemamount - 1) {
                this.canvas.gameUi.drawUiModule(178, x, y + itemstep * showitemamount + menuSpeedX * hire + tiaozheng_y, 17, 6);
            }
            if (++menuSpeedX == 2) {
                menuSpeedX = 0;
                hire *= -1;
            }
        }
    }

    public void initGameMenu() {
        this.gameMenuSpeedY = new int[5];
        this.isMenuActionOver = false;
        this.gameMenuSelectIndex = 0;
        gameMenuStartIndex = 0;
        this.initUiScreenData();
    }

    private void gameMenuKeyProcess() {
        if (this.isMenuActionOver) {
            if (Control.IsKeyDown(4100) || Control.IsKeyHold(4100, 10)) {
                if (this.gameMenuSelectIndex > 0) {
                    this.gameMenuSelectIndex = (byte)(this.gameMenuSelectIndex - 1);
                } else if (gameMenuStartIndex > 0) {
                    gameMenuStartIndex = (byte)(gameMenuStartIndex - 1);
                } else if (this.gameMenuSelectIndex == 0 && gameMenuStartIndex == 0) {
                    this.gameMenuSelectIndex = (byte)4;
                    gameMenuStartIndex = 0;
                }
            } else if (Control.IsKeyDown(8448) || Control.IsKeyHold(8448, 10)) {
                if (this.gameMenuSelectIndex < 4) {
                    this.gameMenuSelectIndex = (byte)(this.gameMenuSelectIndex + 1);
                } else if (this.gameMenuSelectIndex == 4 && gameMenuStartIndex == 0) {
                    this.gameMenuSelectIndex = 0;
                    gameMenuStartIndex = 0;
                }
            } else if (Control.IsKeyDown(327712)) {
                switch (this.gameMenuSelectIndex + gameMenuStartIndex) {
                    case 0: {
                        this.propertyBoxSeletedIndex = 0;
                        this.initpropertyarmdata();
                        this.initplayeraction();
                        this.setplayerproperty(this.canvas.rolem.fplayerbox[0], this.playeractiontype);
                        this.changeUiState((byte)4);
                        break;
                    }
                    case 1: {
                        this.packboxindex = 0;
                        this.setpackboxData();
                        this.changeUiState((byte)20);
                        break;
                    }
                    case 2: {
                        this.skillBoxIndex = 0;
                        this.skillBoxState = 0;
                        this.changeUiState((byte)9);
                        break;
                    }
                    case 3: {
                        this.initmissionuibox();
                        this.changeUiState((byte)7);
                        break;
                    }
                    case 4: {
                        this.forgebookstate = 0;
                        this.forgebookindex = 0;
                        this.setforgebookData();
                        this.changeUiState((byte)19);
                    }
                }
            } else if (Control.IsKeyDown(131072)) {
                this.canvas.mapm.iscityflushinfo = true;
                this.canvas.changeGameState(this.canvas.lastgamestate);
            }
        }
    }

    private void gameMenuProcess() {
        int state = 0;
        for (int i = 0; i < 5; ++i) {
            if (this.gameMenuSpeedY[i] >= this.menutempdata[i]) {
                if ((state = (int)((byte)(state + 1))) < 5) continue;
                this.isMenuActionOver = true;
                continue;
            }
            int n = i;
            this.gameMenuSpeedY[n] = this.gameMenuSpeedY[n] + this.menuspeed[i];
        }
    }

    public void changeWeaponImage(byte name) {
        switch (this.changeWeaponTime) {
            case 0: {
                this.canvas.rolem.cleanWeaponImage(name);
                break;
            }
            case 1: {
                this.canvas.rolem.createWeaponImage(name);
                break;
            }
            case 2: {
                System.gc();
            }
        }
        ++this.changeWeaponTime;
    }

    public void drawWeapon(int x, int y, byte index) {
        if (this.loadweaponimagetime == 2) {
            this.drawWeapon(this.weaponImage, x, y, index);
        } else {
            int h = (this.loadweaponimagetime + 1) * 100 / 2 * 50 / 100;
            this.canvas.fillRect(1376250, x, y, 2, h);
            this.canvas.drawRect(1376250, x - 2, y - 2, 5, 54);
        }
    }

    public void drawWeapon(Image weaponimage, int x, int y, byte index) {
        switch (index) {
            case 0: {
                this.canvas.drawImage(weaponimage, x, y - 15 + this.showweapony, 17, 6, this.jianModule, 0);
                break;
            }
            case 1: {
                this.canvas.drawImage(weaponimage, x, y + 5 + this.showweapony, 17, 6, this.quanModule, 0);
                break;
            }
            case 2: {
                this.canvas.drawImage(weaponimage, x, y - 5 + this.showweapony, 17, 6, this.qiangModule, 0);
            }
        }
        this.showweapony += this.showweaponspeedy * this.showweaponvdir;
        if (Math.abs(this.showweapony) > 2) {
            this.showweaponvdir *= -1;
        }
    }

    public void clearWeaponImages() {
        this.weaponImage = null;
        this.loadweaponimagetime = (byte)2;
    }

    public void loadWeaponImages() {
        if (this.loadweaponname < 0) {
            this.loadweaponimagetime = (byte)2;
        }
        if (this.loadweaponimagetime < 2) {
            switch (this.loadweaponimagetime) {
                case 0: {
                    this.createWeaponImage(this.loadweaponname);
                    break;
                }
                case 1: {
                    System.gc();
                }
            }
            this.loadweaponimagetime = (byte)(this.loadweaponimagetime + 1);
        }
    }

    public void drawlabels(short[][] data, byte index) {
        for (int i = 0; i < data[index].length; ++i) {
            this.drawUiModule(data[index][i], LABEL_X[index][i], 42, 36, 0);
        }
    }

    public void drawlabels1(short[][] data, byte index) {
        for (int i = 0; i < data[index].length; ++i) {
            this.drawUiModule(data[index][i], LABEL_X[index][i] - 4, 42, 36, 0);
        }
    }

    public void drawarmframe() {
        for (int i = 0; i < 9; i = (int)((byte)(i + 1))) {
            this.drawUiModule(3, 220 + i * UI_ICON_ITEM_STEP_X - 100, 60, 20, 0);
        }
    }

    public void drawforgeframe() {
        for (int i = 0; i < 3; i = (int)((byte)(i + 1))) {
            for (int j = 0; j < 9; j = (int)((byte)(j + 1))) {
                this.drawUiModule(3, 220 + j * UI_ICON_ITEM_STEP_X - 100, 60 + i * UI_ICON_ITEM_STEP_Y + 2, 20, 0);
            }
        }
    }

    public void drawarmicon(int name, int x, int y) {
        if (name > -1) {
            this.canvas.drawImage(this.canvas.itemiconimages[this.canvas.itemiconmodules[name * 5]], x + 2, y + 4, 20, 0, this.canvas.itemiconmodules, name);
        }
    }

    public void drawArmBoxItem() {
        this.drawStaticUi((short)112);
        this.drawStaticUi((short)16, 0, 360);
        this.drawarmframe();
        this.drawUiModule(0, 219 + this.armBoxSeletedIndex * UI_ICON_ITEM_STEP_X - 100, 61, 20, 0);
        for (byte i = 0; i < 9; i = (byte)((byte)(i + 1))) {
            if (this.armBoxUseTotal > 0) {
                switch (this.armBoxIndex) {
                    case 0: {
                        this.drawarmicon(this.weaponPack[0][this.armBoxStartIndex + i], 220 + i * UI_ICON_ITEM_STEP_X - 100, 60);
                        if (i == this.armBoxSeletedIndex) {
                            this.drawitemiconcheck(220 + i * UI_ICON_ITEM_STEP_X - 100, 60, this.weaponPack[0][this.armBoxStartIndex + this.armBoxSeletedIndex]);
                        } else {
                            this.drawitemiconmembrane(220 + i * UI_ICON_ITEM_STEP_X - 100, 60, this.weaponPack[0][this.armBoxStartIndex + i]);
                        }
                        if (this.weaponPack[1][this.armBoxStartIndex + i] <= -1) break;
                        this.drawUiModule(180, 245 + i * UI_ICON_ITEM_STEP_X - 100, 87, 20, 0);
                        break;
                    }
                    case 1: {
                        this.drawarmicon(this.suitPack[0][this.armBoxStartIndex + i], 220 + i * UI_ICON_ITEM_STEP_X - 100, 60);
                        if (i == this.armBoxSeletedIndex) {
                            this.drawitemiconcheck(220 + i * UI_ICON_ITEM_STEP_X - 100, 60, this.suitPack[0][this.armBoxStartIndex + this.armBoxSeletedIndex]);
                        } else {
                            this.drawitemiconmembrane(220 + i * UI_ICON_ITEM_STEP_X - 100, 60, this.suitPack[0][this.armBoxStartIndex + i]);
                        }
                        if (this.suitPack[1][this.armBoxStartIndex + i] <= -1) break;
                        this.drawUiModule(180, 245 + i * UI_ICON_ITEM_STEP_X - 100, 87, 20, 0);
                        break;
                    }
                    case 2: {
                        this.drawarmicon(this.plusPack[0][this.armBoxStartIndex + i], 220 + i * UI_ICON_ITEM_STEP_X - 100, 60);
                        if (i == this.armBoxSeletedIndex) {
                            this.drawitemiconcheck(220 + i * UI_ICON_ITEM_STEP_X - 100, 60, this.plusPack[0][this.armBoxStartIndex + this.armBoxSeletedIndex]);
                        } else {
                            this.drawitemiconmembrane(220 + i * UI_ICON_ITEM_STEP_X - 100, 60, this.plusPack[0][this.armBoxStartIndex + i]);
                        }
                        if (this.plusPack[1][this.armBoxStartIndex + i] <= -1) break;
                        this.drawUiModule(180, 245 + i * UI_ICON_ITEM_STEP_X - 100, 87, 20, 0);
                    }
                }
                continue;
            }
            this.canvas.drawColorString("\u6ca1\u6709\u88c5\u5907\u3002", 320, 180, 33, 0xFFFFFF, 0);
        }
        if (this.armBoxStartIndex > 0) {
            this.drawjiantou(0, 4, 92, 70, 20);
        }
        if (this.armBoxStartIndex + 9 < this.armBoxUseTotal) {
            this.drawjiantou(0, 5, 532, 70, 20);
        }
        switch (this.armBoxIndex) {
            case 0: {
                this.drawpacknum(this.weaponPackUseNum, this.itemPackNum);
                if (this.armBoxUseTotal <= 0 || this.armBoxStartIndex + this.armBoxSeletedIndex >= this.armBoxUseTotal) break;
                this.drawCurrentItemInfo(0, this.armBoxStartIndex + this.armBoxSeletedIndex);
                break;
            }
            case 1: {
                this.drawpacknum(this.suitPackUseNum, this.itemPackNum);
                if (this.armBoxUseTotal <= 0 || this.armBoxStartIndex + this.armBoxSeletedIndex >= this.armBoxUseTotal) break;
                this.drawCurrentItemInfo(1, this.armBoxStartIndex + this.armBoxSeletedIndex);
                break;
            }
            case 2: {
                this.drawpacknum(this.plusPackUseNum, this.itemPackNum);
                if (this.armBoxUseTotal <= 0 || this.armBoxStartIndex + this.armBoxSeletedIndex >= this.armBoxUseTotal) break;
                this.drawCurrentItemInfo(2, this.armBoxStartIndex + this.armBoxSeletedIndex);
            }
        }
        if (this.armBoxUseTotal > 0) {
            this.drawarminfo(this.armBoxIndex);
        }
    }

    private void drawArmBox() {
        this.drawStaticUiBack((short)0);
        block0 : switch (this.armBoxState) {
            case 0: {
                this.drawStaticUi(this.armBoxIndex);
                this.drawlabels1(this.armboxdata, this.armBoxIndex);
                this.drawUiModule(242, 1, 44, 20, 0);
                this.drawStaticUi((short)112);
                this.drawStaticUi((short)16, 0, 360);
                break;
            }
            case 1: {
                this.drawStaticUi(this.armBoxIndex);
                if (this.armBoxUseTotal > 0) {
                    if (this.armBoxIndex == 0) {
                        this.drawStaticUi((short)123);
                    } else {
                        this.drawStaticUi((short)123);
                    }
                }
                this.drawlabels1(this.armboxdata, this.armBoxIndex);
                this.drawArmBoxItem();
                if (!this.isCommand) break;
                this.drawUiBack();
                switch (this.commandState) {
                    case 0: 
                    case 2: {
                        this.drawStaticUi((short)126);
                        for (int i = 0; i < this.armBoxCommand[this.commandState].length; i = (int)((byte)(i + 1))) {
                            if (i == this.commandSeletedIndex) {
                                this.drawChoiceBox(320, 125 + this.commandSeletedIndex * 45);
                                this.canvas.drawColorString(this.armBoxCommand[this.commandState][i], 320, 128 + this.commandSeletedIndex * 45, 17, 15597319, 0);
                                continue;
                            }
                            this.canvas.drawColorString(this.armBoxCommand[this.commandState][i], 320, 128 + i * 45, 17, 0xFFFFFF, 0);
                        }
                        break block0;
                    }
                    case 1: {
                        this.drawStaticUi((short)126);
                        for (int i = 0; i < this.armBoxCommand[this.commandState].length; i = (int)((byte)(i + 1))) {
                            if (i == this.commandSeletedIndex) {
                                this.drawChoiceBox(320, 144 + this.commandSeletedIndex * 45);
                                this.canvas.drawColorString(this.armBoxCommand[this.commandState][i], 320, 147 + this.commandSeletedIndex * 45, 17, 15597319, 0);
                                continue;
                            }
                            this.canvas.drawColorString(this.armBoxCommand[this.commandState][i], 320, 147 + i * 45, 17, 0xFFFFFF, 0);
                        }
                        break block0;
                    }
                    case 3: {
                        this.drawStaticUi((short)19);
                        this.canvas.drawColorString("\u662f\u5426\u4e22\u5f03\uff1f", 320, 133, 17, 0xFFFFFF, 0);
                        break;
                    }
                    case 4: {
                        this.drawStaticUi((short)26);
                        this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                        break;
                    }
                    case 5: {
                        this.changeWeaponImage(this.changeWeaponName);
                        this.drawminiloading(this.changeWeaponTime, 3);
                        if (this.changeWeaponTime == 3) {
                            this.commandStr = "\u88c5\u5907\u6210\u529f\u3002";
                            this.setCommandShowTime();
                            this.changeCommandState((byte)4);
                        } else {
                            break;
                        }
                    }
                }
                break;
            }
        }
    }

    private void drawarminfo(byte index) {
        this.drawlabel(this.arminfolabel[index], 65, 232, 4, 2);
        this.scroll.drawscroll();
    }

    public void initCommand() {
        this.commandState = 0;
        this.lastCommandState = 0;
        this.isCommand = false;
        this.commandShowTime = 0;
        this.commandSeletedIndex = 0;
    }

    public void startCommand() {
        this.isCommand = true;
        this.initUiScreenData();
    }

    public void finishCommand() {
        this.isCommand = false;
        this.commandShowTime = 0;
        if (this.canvas.sms.isshowsmsshop) {
            switch (this.canvas.sms.showsmsshoptype) {
                case 0: {
                    this.canvas.sms.isshowsmsshop = false;
                    this.canvas.sms.smsShopSelectIndex = 0;
                    this.canvas.sms.changeSmsstate(0, this.uiState);
                    break;
                }
                case 1: {
                    if (this.canvas.rolem.playerLevel >= 50) break;
                    this.canvas.sms.smsShopSelectIndex = (byte)2;
                    this.canvas.sms.isshowsmsshop = false;
                    this.canvas.sms.changeSmsstate(0, this.uiState);
                }
            }
        }
        if (this.isShowKuoBaoTishi) {
            this.initKuoBaoTishi();
        }
    }

    public void setCommandShowTime() {
        this.commandShowTime = 20;
    }

    public void changeCommandState(byte state) {
        this.lastCommandState = this.commandState;
        this.commandState = state;
    }

    private void armKeyProcess() {
        this.loadWeaponImages();
        switch (this.armBoxState) {
            case 0: {
                if (Control.IsKeyDown(16400)) {
                    this.armBoxIndex = (byte)(this.armBoxIndex - 1);
                    this.armBoxIndex = this.armBoxIndex < 0 ? (byte)0 : this.armBoxIndex;
                    break;
                }
                if (Control.IsKeyDown(32832)) {
                    this.armBoxIndex = (byte)(this.armBoxIndex + 1);
                    this.armBoxIndex = (byte)(this.armBoxIndex > 2 ? 2 : (int)this.armBoxIndex);
                    break;
                }
                if (Control.IsKeyDown(327712)) {
                    this.armBoxSeletedIndex = 0;
                    this.armBoxStartIndex = 0;
                    this.armBoxState = 1;
                    switch (this.armBoxIndex) {
                        case 0: {
                            this.armBoxUseTotal = this.weaponPackUseNum;
                            this.setWeaponInfo(this.weaponPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                            this.showweapony = 0;
                            this.showweaponvdir = 1;
                            this.loadweaponimagetime = 0;
                            this.loadweaponname = this.weaponPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex];
                            break;
                        }
                        case 1: {
                            this.armBoxUseTotal = this.suitPackUseNum;
                            this.setWeaponInfo(this.suitPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                            break;
                        }
                        case 2: {
                            this.armBoxUseTotal = this.plusPackUseNum;
                            this.setWeaponInfo(this.plusPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                        }
                    }
                    this.changeActionUiState((byte)6, (byte)7, (byte)0);
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                this.setpackboxData();
                this.changeUiState((byte)20);
                break;
            }
            case 1: {
                if (Control.IsKeyDown(4100)) {
                    if (!this.isCommand || this.commandState < 0 || this.commandState >= this.ARMBOXCOMMANDTOTAL.length) break;
                    this.commandSeletedIndex = (byte)(this.commandSeletedIndex - 1);
                    this.commandSeletedIndex = this.commandSeletedIndex < 0 ? this.ARMBOXCOMMANDTOTAL[this.commandState] : this.commandSeletedIndex;
                    break;
                }
                if (Control.IsKeyDown(8448)) {
                    if (!this.isCommand || this.commandState < 0 || this.commandState >= this.ARMBOXCOMMANDTOTAL.length) break;
                    this.commandSeletedIndex = (byte)(this.commandSeletedIndex + 1);
                    this.commandSeletedIndex = this.commandSeletedIndex > this.ARMBOXCOMMANDTOTAL[this.commandState] ? (byte)0 : this.commandSeletedIndex;
                    break;
                }
                if (Control.IsKeyDown(16400) || Control.IsKeyHold(16400, 10)) {
                    if (this.isCommand) break;
                    if (this.armBoxSeletedIndex > 0) {
                        this.armBoxSeletedIndex = (byte)(this.armBoxSeletedIndex - 1);
                    } else if (this.armBoxStartIndex > 0) {
                        this.armBoxStartIndex = (byte)(this.armBoxStartIndex - 1);
                    }
                    this.showweapony = 0;
                    this.showweaponvdir = 1;
                    this.loadweaponimagetime = 0;
                    this.loadweaponname = this.weaponPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex];
                    if (this.armBoxSeletedIndex + this.armBoxStartIndex >= this.armBoxUseTotal) break;
                    switch (this.armBoxIndex) {
                        case 0: {
                            this.setWeaponInfo(this.weaponPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                            break;
                        }
                        case 1: {
                            this.setWeaponInfo(this.suitPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                            break;
                        }
                        case 2: {
                            this.setWeaponInfo(this.plusPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(32832) || Control.IsKeyHold(32832, 10)) {
                    int total;
                    if (this.isCommand) break;
                    int n = total = this.armBoxUseTotal < 9 ? (int)this.armBoxUseTotal : 9;
                    if (this.armBoxSeletedIndex < total - 1) {
                        this.armBoxSeletedIndex = (byte)(this.armBoxSeletedIndex + 1);
                    } else if (this.armBoxStartIndex < this.armBoxUseTotal - total) {
                        this.armBoxStartIndex = (byte)(this.armBoxStartIndex + 1);
                    }
                    this.showweapony = 0;
                    this.showweaponvdir = 1;
                    this.loadweaponimagetime = 0;
                    this.loadweaponname = this.weaponPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex];
                    if (this.armBoxSeletedIndex + this.armBoxStartIndex < this.armBoxUseTotal) {
                        switch (this.armBoxIndex) {
                            case 0: {
                                this.setWeaponInfo(this.weaponPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                                break;
                            }
                            case 1: {
                                this.setWeaponInfo(this.suitPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                                break;
                            }
                            case 2: {
                                this.setWeaponInfo(this.plusPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                            }
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(327712)) {
                    if (this.isCommand) {
                        switch (this.commandState) {
                            case 0: {
                                block26 : switch (this.armBoxIndex) {
                                    case 0: {
                                        switch (this.commandSeletedIndex) {
                                            case 0: {
                                                this.changeWeapon((byte)(this.armBoxStartIndex + this.armBoxSeletedIndex));
                                                this.changeWeaponName = this.weaponPack[0][this.armBoxStartIndex + this.armBoxSeletedIndex];
                                                this.changeWeaponTime = 0;
                                                this.changeCommandState((byte)5);
                                                break;
                                            }
                                            case 1: {
                                                this.changeCommandState((byte)3);
                                                break;
                                            }
                                            case 2: {
                                                this.finishCommand();
                                            }
                                        }
                                        break;
                                    }
                                    case 1: {
                                        switch (this.commandSeletedIndex) {
                                            case 0: {
                                                this.changeSuit((byte)(this.armBoxStartIndex + this.armBoxSeletedIndex));
                                                this.setCommandShowTime();
                                                this.commandStr = "\u88c5\u5907\u6210\u529f";
                                                this.changeCommandState((byte)4);
                                                break;
                                            }
                                            case 1: {
                                                this.changeCommandState((byte)3);
                                                break;
                                            }
                                            case 2: {
                                                this.finishCommand();
                                            }
                                        }
                                        break;
                                    }
                                    case 2: {
                                        switch (this.commandSeletedIndex) {
                                            case 0: {
                                                this.commandSeletedIndex = 0;
                                                this.changeCommandState((byte)2);
                                                break block26;
                                            }
                                            case 1: {
                                                this.changeCommandState((byte)3);
                                                break block26;
                                            }
                                            case 2: {
                                                this.finishCommand();
                                            }
                                        }
                                    }
                                }
                                break;
                            }
                            case 1: {
                                switch (this.commandSeletedIndex) {
                                    case 0: {
                                        this.putOffPlus(this.plusPack[1][this.armBoxStartIndex + this.armBoxSeletedIndex]);
                                        this.setCommandShowTime();
                                        this.commandStr = "\u5378\u4e0b\u6210\u529f";
                                        this.changeCommandState((byte)4);
                                        break;
                                    }
                                    case 1: {
                                        this.finishCommand();
                                    }
                                }
                                break;
                            }
                            case 2: {
                                switch (this.commandSeletedIndex) {
                                    case 0: 
                                    case 1: {
                                        if (this.isPutOnPlus(this.commandSeletedIndex)) {
                                            this.changePlus(this.commandSeletedIndex, (byte)(this.armBoxStartIndex + this.armBoxSeletedIndex));
                                        } else {
                                            this.putOnPlus(this.commandSeletedIndex, (byte)(this.armBoxStartIndex + this.armBoxSeletedIndex));
                                        }
                                        this.setCommandShowTime();
                                        this.commandStr = "\u88c5\u5907\u6210\u529f";
                                        this.changeCommandState((byte)4);
                                        break;
                                    }
                                    case 2: {
                                        this.changeCommandState((byte)0);
                                    }
                                }
                                break;
                            }
                            case 3: {
                                switch (this.armBoxIndex) {
                                    case 0: {
                                        this.subtractWeapon(this.armBoxStartIndex + this.armBoxSeletedIndex);
                                        this.armBoxUseTotal = this.weaponPackUseNum;
                                        this.setCommandShowTime();
                                        this.commandStr = "\u4e22\u5f03\u6210\u529f";
                                        this.changeCommandState((byte)4);
                                        break;
                                    }
                                    case 1: {
                                        this.subtractSuit(this.armBoxStartIndex + this.armBoxSeletedIndex);
                                        this.armBoxUseTotal = this.suitPackUseNum;
                                        this.setCommandShowTime();
                                        this.commandStr = "\u4e22\u5f03\u6210\u529f";
                                        this.changeCommandState((byte)4);
                                        break;
                                    }
                                    case 2: {
                                        this.subtractPlus(this.armBoxStartIndex + this.armBoxSeletedIndex);
                                        this.armBoxUseTotal = this.plusPackUseNum;
                                        this.setCommandShowTime();
                                        this.commandStr = "\u4e22\u5f03\u6210\u529f";
                                        this.changeCommandState((byte)4);
                                    }
                                }
                                this.showweapony = 0;
                                this.showweaponvdir = 1;
                                this.loadweaponimagetime = 0;
                                switch (this.armBoxIndex) {
                                    case 0: {
                                        this.setWeaponInfo(this.weaponPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                                        this.loadweaponname = this.weaponPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex];
                                        break;
                                    }
                                    case 1: {
                                        this.setWeaponInfo(this.suitPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                                        break;
                                    }
                                    case 2: {
                                        this.setWeaponInfo(this.plusPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                                    }
                                }
                                break;
                            }
                            case 4: {
                                this.finishCommand();
                            }
                        }
                        break;
                    }
                    switch (this.armBoxIndex) {
                        case 0: {
                            if (this.weaponPack[0][this.armBoxStartIndex + this.armBoxSeletedIndex] == -1) break;
                            if (this.weaponPack[1][this.armBoxStartIndex + this.armBoxSeletedIndex] == -1) {
                                this.startCommand();
                                this.commandSeletedIndex = 0;
                                this.changeCommandState((byte)0);
                                break;
                            }
                            this.setCommandShowTime();
                            this.commandStr = "\u5df2\u88c5\u5907";
                            this.changeCommandState((byte)4);
                            this.startCommand();
                            break;
                        }
                        case 1: {
                            if (this.suitPack[0][this.armBoxStartIndex + this.armBoxSeletedIndex] == -1) break;
                            if (this.suitPack[1][this.armBoxStartIndex + this.armBoxSeletedIndex] == -1) {
                                this.startCommand();
                                this.commandSeletedIndex = 0;
                                this.changeCommandState((byte)0);
                                break;
                            }
                            this.setCommandShowTime();
                            this.commandStr = "\u5df2\u88c5\u5907";
                            this.changeCommandState((byte)4);
                            this.startCommand();
                            break;
                        }
                        case 2: {
                            if (this.plusPack[0][this.armBoxStartIndex + this.armBoxSeletedIndex] == -1) break;
                            if (this.plusPack[1][this.armBoxStartIndex + this.armBoxSeletedIndex] == -1) {
                                this.startCommand();
                                this.commandSeletedIndex = 0;
                                this.changeCommandState((byte)0);
                                break;
                            }
                            this.startCommand();
                            this.commandSeletedIndex = 0;
                            this.changeCommandState((byte)1);
                        }
                    }
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                if (this.isCommand) {
                    switch (this.commandState) {
                        case 0: 
                        case 1: {
                            this.finishCommand();
                            break;
                        }
                        case 4: {
                            this.finishCommand();
                            break;
                        }
                        case 2: {
                            this.changeCommandState((byte)0);
                            break;
                        }
                        case 3: {
                            this.changeCommandState((byte)0);
                        }
                    }
                    break;
                }
                this.armBoxState = 0;
                this.clearWeaponImages();
                this.changeActionUiState((byte)6, (byte)8, (byte)0);
            }
        }
    }

    public void drawitemicon(int name, int num, int x, int y) {
        int total = 0;
        if (name > -1) {
            this.canvas.drawImage(this.canvas.itemiconimages[this.canvas.itemiconmodules[name * 5]], x + 2, y + 4, 20, 0, this.canvas.itemiconmodules, name);
            total = num / 10 > 0 ? 12 : 6;
            this.canvas.fillRect(0, x + 34 - total, y + 26, total, 11);
            this.canvas.drawString("" + num, x + 34, y + 38, 40, 0xFFFFFF);
        }
    }

    public void drawitemiconcheck(int x, int y, int index) {
        if (index > -1) {
            this.canvas.drawImage(this.canvas.itemiconimages[this.canvas.itemiconmodules[635]], x + 1, y + 3, 20, 0, this.canvas.itemiconmodules, 127);
        }
    }

    public void drawitemiconmembrane(int x, int y, int index) {
        if (index > -1) {
            this.canvas.drawImage(this.canvas.itemiconimages[this.canvas.itemiconmodules[640]], x + 2, y + 4, 20, 0, this.canvas.itemiconmodules, 128);
        }
    }

    public void drawitemframe() {
        for (int i = 0; i < 8; i = (int)((byte)(i + 1))) {
            for (int j = 0; j < 2; j = (int)((byte)(j + 1))) {
                this.drawUiModule(3, 28 + i * 53 + 88, 114 + j * 53 - 40, 20, 0);
            }
        }
    }

    public void drawpacknum(int num, int total) {
        this.canvas.drawColorString("" + num + " / " + total, 557, 42, 33, 15597319, 0);
    }

    public void drawItemBoxItem() {
        this.drawStaticUiBack((short)0);
        this.drawStaticUi((short)88);
        this.drawUiModule(288, LABEL_X[0][0] - 18, 42, 36, 0);
        this.drawStaticUi((short)113, 320, 310);
        this.drawStaticUi((short)122);
        this.drawStaticUi((short)16, 0, 360);
        this.drawpacknum(this.itemPackUseNum, this.itemPackNum);
        this.drawitemframe();
        this.drawUiModule(0, 27 + this.itemBoxSeletedIndex % 8 * 53 + 88, 115 + this.itemBoxSeletedIndex / 8 * 53 - 40, 20, 0);
        for (int i = 0; i < 8; i = (int)((byte)(i + 1))) {
            for (int j = 0; j < 2; j = (int)((byte)(j + 1))) {
                this.drawitemicon(this.itemPack[0][(j + this.itemBoxStartIndex) * 8 + i], this.itemPack[1][(j + this.itemBoxStartIndex) * 8 + i], 28 + i * 53 + 88, 114 + j * 53 - 40);
                if (i + j * 8 == this.itemBoxSeletedIndex) {
                    this.drawitemiconcheck(28 + i * 53 + 88, 114 + j * 53 - 40, this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                    continue;
                }
                this.drawitemiconmembrane(28 + i * 53 + 88, 114 + j * 53 - 40, this.itemPack[0][(j + this.itemBoxStartIndex) * 8 + i]);
            }
        }
        this.scroll.drawscroll();
        this.drawjindutiaoV(18, 68, 8, 233, this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8, this.itemBoxUseTotal, (byte)2);
    }

    public void drawItemBoxCommand() {
        this.drawUiBack();
        switch (this.commandState) {
            case 0: {
                this.drawStaticUi((short)126);
                for (int i = 0; i < this.itemBoxCommand.length; i = (int)((byte)(i + 1))) {
                    if (i == this.commandSeletedIndex) {
                        this.drawChoiceBox(320, 125 + this.commandSeletedIndex * 45);
                        this.canvas.drawColorString(this.itemBoxCommand[i], 320, 128 + this.commandSeletedIndex * 45, 17, 15597319, 0);
                        continue;
                    }
                    this.canvas.drawColorString(this.itemBoxCommand[i], 320, 128 + i * 45, 17, 0xFFFFFF, 0);
                }
                break;
            }
            case 1: {
                this.drawStaticUi((short)19);
                this.drawjiantou(0, 0, 375, 132, 33);
                this.drawjiantou(0, 1, 375, 150, 17);
                this.canvas.drawColorString("\u9009\u62e9\u4e22\u5f03\u6570\u91cf\uff1a" + this.needNum, 320, 133, 17, 0xFFFFFF, 0);
                break;
            }
            case 2: {
                this.drawStaticUi((short)19);
                this.canvas.drawColorString("\u662f\u5426\u4e22\u5f03\uff1f", 320, 133, 17, 0xFFFFFF, 0);
                break;
            }
            case 3: {
                this.drawStaticUi((short)26);
                this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                break;
            }
            case 4: {
                this.drawStaticUi((short)19);
                this.canvas.drawColorString("\u786e\u5b9a\u4f7f\u7528\u5417\uff1f", 320, 133, 17, 15597319, 0);
                break;
            }
            case 5: {
                this.drawStaticUi((short)126);
                for (int i = 0; i < this.itemBoxCommand1.length; i = (int)((byte)(i + 1))) {
                    if (i == this.commandSeletedIndex) {
                        this.drawChoiceBox(320, 144 + this.commandSeletedIndex * 45);
                        this.canvas.drawColorString(this.itemBoxCommand1[i], 320, 147 + this.commandSeletedIndex * 45, 17, 15597319, 0);
                        continue;
                    }
                    this.canvas.drawColorString(this.itemBoxCommand1[i], 320, 147 + i * 45, 17, 0xFFFFFF, 0);
                }
                break;
            }
            case 6: {
                this.drawStaticUi((short)19);
                for (byte i = 0; i < 3; i = (byte)(i + 1)) {
                    if (i == this.commandSeletedIndex) {
                        this.drawUiModule(0, 229 + this.commandSeletedIndex * 150, 119, 20, 0);
                    } else {
                        this.drawUiModule(3, 230 + i * 150, 118, 20, 0);
                    }
                    if (!this.canvas.rolem.isrideopen(i)) continue;
                    this.drawarmicon(76 + this.rideiconindex[i], 230 + i * 150, 118);
                    if (i == this.commandSeletedIndex) {
                        this.drawitemiconcheck(230 + i * 150, 118, 128);
                    } else {
                        this.drawitemiconmembrane(230 + i * 150, 118, 127);
                    }
                    if (this.canvas.rolem.currentRideIndex <= -1) continue;
                    this.drawUiModule(180, 255 + this.canvas.rolem.currentRideIndex * 150, 145, 20, 0);
                }
                break;
            }
        }
    }

    private void drawItemBox() {
        this.drawItemBoxItem();
        if (this.isCommand) {
            this.drawItemBoxCommand();
        }
    }

    public int getItemLevelColorIndex(int itemleveltmp) {
        return itemleveltmp > 7 ? 7 : itemleveltmp;
    }

    public void setItemInfo(byte itemname) {
        this.scroll.close();
        if (itemname > -1) {
            StringBuffer strb = new StringBuffer("");
            strb.append("$" + this.getItemLevelColorIndex(this.itemLevel[itemname]));
            strb.append(this.iteminfo[itemname][0]);
            strb.append("$1");
            strb.append("\uff1a");
            strb.append(this.iteminfo[itemname][1]);
            this.scroll.setscroll(strb.toString(), 340, 124, 215, 60, 1, 20);
        }
    }

    public void setItemInfo1(byte itemname) {
        this.scroll.close();
        if (itemname > -1) {
            StringBuffer strb = new StringBuffer("");
            strb.append("$" + this.getItemLevelColorIndex(this.itemLevel[itemname]));
            strb.append(this.iteminfo[itemname][0]);
            strb.append("$1");
            strb.append("\uff1a");
            strb.append(this.iteminfo[itemname][1]);
            this.scroll.setscroll(strb.toString(), 80, 200, 470, 80, 1, 20);
        }
    }

    private void setForgeInfo(byte itemname) {
        this.scroll.close();
        if (itemname > -1) {
            StringBuffer strb = new StringBuffer("");
            strb.append("$" + this.getItemLevelColorIndex(this.itemLevel[itemname]));
            strb.append(this.iteminfo[itemname][0]);
            strb.append("$1");
            strb.append("\uff1a");
            strb.append(this.iteminfo[itemname][1]);
            this.scroll.setscroll(strb.toString(), 80, 200, 470, 80, 1, 20);
        }
    }

    public void setWeaponInfo(byte itemname, byte showtype) {
        this.scroll.close();
        if (itemname > -1) {
            StringBuffer strb = new StringBuffer("");
            strb.append("$" + this.getItemLevelColorIndex(this.itemLevel[itemname]));
            strb.append(this.iteminfo[itemname][0]);
            strb.append("$1");
            strb.append("\uff1a");
            strb.append(this.iteminfo[itemname][1]);
            if (showtype == 0) {
                this.scroll.setscroll(strb.toString(), 75, 255, 505, 100, 1, 20);
            } else if (showtype == 1) {
                this.scroll.setscroll(strb.toString(), 75, 255, 505, 100, 1, 20);
            }
        }
    }

    private void setForgeInfo1(byte itemname) {
        this.scroll.close();
        if (itemname > -1) {
            StringBuffer strb = new StringBuffer("");
            strb.append("$" + this.getItemLevelColorIndex(this.itemLevel[itemname]));
            strb.append(this.iteminfo[itemname][0]);
            strb.append("$1");
            strb.append("\uff1a");
            strb.append(this.iteminfo[itemname][1]);
            this.scroll.setscroll(strb.toString(), 20, 46, 505, 60, 1, 20);
        }
    }

    public void setForgeCondition(byte itemname, byte showtype) {
        this.scroll.close();
        if (itemname >= 44) {
            StringBuffer strb = new StringBuffer("");
            strb.append("$" + this.getItemLevelColorIndex(this.itemLevel[this.forgeMaterial[itemname - 44][0]]));
            strb.append(this.iteminfo[this.forgeMaterial[itemname - 44][0]][0]);
            strb.append("$1: ");
            if (this.itemTotal[this.forgeMaterial[itemname - 44][0]] < this.forgeMaterialNum[itemname - 44][0]) {
                strb.append("$6");
            }
            strb.append(this.itemTotal[this.forgeMaterial[itemname - 44][0]]);
            strb.append("$1");
            strb.append("/");
            strb.append(this.forgeMaterialNum[itemname - 44][0]);
            strb.append("|");
            strb.append("$" + this.getItemLevelColorIndex(this.itemLevel[this.forgeMaterial[itemname - 44][1]]));
            strb.append(this.iteminfo[this.forgeMaterial[itemname - 44][1]][0]);
            strb.append("$1: ");
            if (this.itemTotal[this.forgeMaterial[itemname - 44][1]] < this.forgeMaterialNum[itemname - 44][1]) {
                strb.append("$6");
            }
            strb.append(this.itemTotal[this.forgeMaterial[itemname - 44][1]]);
            strb.append("$1");
            strb.append("/");
            strb.append(this.forgeMaterialNum[itemname - 44][1] + "  ");
            if (showtype == 0) {
                strb.append("|");
            }
            strb.append("\u91d1\u94b1\uff1a");
            strb.append(this.canvas.rolem.itemBusinessData[itemname][0]);
            if (this.canvas.rolem.armData[itemname - 44][4] > -1) {
                strb.append("|");
                strb.append("$1\u6240\u9700\u6b66\u5668\uff1a");
                if (showtype == 0) {
                    strb.append("|");
                }
                strb.append("$" + this.getItemLevelColorIndex(this.itemLevel[this.canvas.rolem.armData[itemname - 44][4]]));
                strb.append(this.iteminfo[this.canvas.rolem.armData[itemname - 44][4]][0]);
                strb.append("$1");
                strb.append("(");
                strb.append(this.canvas.rolem.armData[this.canvas.rolem.armData[itemname - 44][4] - 44][8] + "\u7ea7");
                strb.append(this.canvas.mapm.cityName[this.canvas.rolem.armData[this.canvas.rolem.armData[itemname - 44][4] - 44][7]]);
                strb.append(")");
                if (this.canvas.rolem.armData[itemname - 44][5] > -1) {
                    strb.append("\u6216");
                    strb.append("|");
                    strb.append("$" + this.getItemLevelColorIndex(this.itemLevel[this.canvas.rolem.armData[itemname - 44][5]]));
                    strb.append(this.iteminfo[this.canvas.rolem.armData[itemname - 44][5]][0]);
                    strb.append("$1");
                    strb.append("(");
                    strb.append(this.canvas.rolem.armData[this.canvas.rolem.armData[itemname - 44][5] - 44][8] + "\u7ea7");
                    strb.append(this.canvas.mapm.cityName[this.canvas.rolem.armData[this.canvas.rolem.armData[itemname - 44][5] - 44][7]]);
                    strb.append(")");
                    if (this.canvas.rolem.armData[itemname - 44][6] > -1) {
                        strb.append("\u6216");
                        strb.append("|");
                        strb.append("$" + this.getItemLevelColorIndex(this.itemLevel[this.canvas.rolem.armData[itemname - 44][6]]));
                        strb.append(this.iteminfo[this.canvas.rolem.armData[itemname - 44][6]][0]);
                        strb.append("$1");
                        strb.append("(");
                        strb.append(this.canvas.rolem.armData[this.canvas.rolem.armData[itemname - 44][6] - 44][8] + "\u7ea7");
                        strb.append(this.canvas.mapm.cityName[this.canvas.rolem.armData[this.canvas.rolem.armData[itemname - 44][6] - 44][7]]);
                        strb.append(")");
                    }
                }
            }
            if (showtype != 0) {
                if (showtype == 1) {
                    this.scroll.setscroll(strb.toString(), 390, 257, 210, 54, 1, 20);
                } else if (showtype == 2) {
                    this.scroll.setscroll(strb.toString(), 345, 182, 210, 62, 1, 20);
                }
            }
        }
    }

    public void setSkillInfo1(byte skillname) {
        this.scroll.close();
        if (skillname > -1) {
            if (this.canvas.rolem.skillBaseData[skillname][7] > -1) {
                if (this.canvas.rolem.skillState[this.canvas.rolem.skillBaseData[skillname][7]][1] >= this.canvas.rolem.skillBaseData[skillname][8]) {
                    StringBuffer strb = new StringBuffer("");
                    strb.append("$1");
                    strb.append(this.skillinfo[skillname][1]);
                    this.scroll.setscroll(strb.toString(), 70, 265, 450, 40, 1, 20);
                }
            } else {
                StringBuffer strb = new StringBuffer("");
                strb.append("$1");
                strb.append(this.skillinfo[skillname][1]);
                this.scroll.setscroll(strb.toString(), 70, 265, 450, 40, 1, 20);
            }
        }
    }

    private void setSkillInfo(byte skillname) {
        this.scroll.close();
        if (skillname > -1) {
            StringBuffer strb = new StringBuffer("");
            strb.append("$1");
            strb.append(this.skillinfo[skillname][1]);
            this.scroll.setscroll(strb.toString(), 85, 267, 400, 40, 1, 20);
        }
    }

    private void itemBoxKeyProcess() {
        if (Control.IsKeyDown(4100) || Control.IsKeyHold(4100, 10)) {
            if (this.isCommand) {
                switch (this.commandState) {
                    case 0: {
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex - 1);
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex < 0 ? 2 : (int)this.commandSeletedIndex);
                        break;
                    }
                    case 1: {
                        this.processNeedNum(1, this.itemPack[1][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                        break;
                    }
                    case 5: {
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex - 1);
                        this.commandSeletedIndex = this.commandSeletedIndex < 0 ? (byte)1 : this.commandSeletedIndex;
                    }
                }
            } else {
                if (this.itemBoxSeletedIndex / 8 > 0) {
                    this.itemBoxSeletedIndex = (byte)(this.itemBoxSeletedIndex - 8);
                } else if (this.itemBoxStartIndex > 0) {
                    this.itemBoxStartIndex = (byte)(this.itemBoxStartIndex - 1);
                }
                if (this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8 < this.itemPack[0].length) {
                    this.setItemInfo1(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                } else {
                    this.setItemInfo1((byte)-1);
                }
            }
        } else if (Control.IsKeyDown(8448) || Control.IsKeyHold(8448, 10)) {
            if (this.isCommand) {
                switch (this.commandState) {
                    case 0: {
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex + 1);
                        this.commandSeletedIndex = this.commandSeletedIndex > 2 ? (byte)0 : this.commandSeletedIndex;
                        break;
                    }
                    case 1: {
                        this.processNeedNum(-1, this.itemPack[1][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                        break;
                    }
                    case 5: {
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex + 1);
                        this.commandSeletedIndex = this.commandSeletedIndex > 1 ? (byte)0 : this.commandSeletedIndex;
                    }
                }
            } else {
                if (this.itemBoxSeletedIndex / 8 < 1) {
                    this.itemBoxSeletedIndex = (byte)(this.itemBoxSeletedIndex + 8);
                } else if (this.itemBoxStartIndex < this.itemBoxUseTotal / 8 - 2) {
                    this.itemBoxStartIndex = (byte)(this.itemBoxStartIndex + 1);
                }
                if (this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8 < this.itemPack[0].length) {
                    this.setItemInfo1(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                } else {
                    this.setItemInfo1((byte)-1);
                }
            }
        } else if (Control.IsKeyDown(16400) || Control.IsKeyHold(16400, 10)) {
            if (this.isCommand) {
                switch (this.commandState) {
                    case 6: {
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex - 1);
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex < 0 ? 2 : (int)this.commandSeletedIndex);
                    }
                }
            } else {
                this.itemBoxSeletedIndex = this.itemBoxSeletedIndex % 8 == 0 ? (byte)(this.itemBoxSeletedIndex + 7) : (byte)(this.itemBoxSeletedIndex - 1);
                if (this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8 < this.itemPack[0].length) {
                    this.setItemInfo1(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                } else {
                    this.setItemInfo1((byte)-1);
                }
            }
        } else if (Control.IsKeyDown(32832) || Control.IsKeyHold(32832, 10)) {
            if (this.isCommand) {
                switch (this.commandState) {
                    case 6: {
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex + 1);
                        this.commandSeletedIndex = this.commandSeletedIndex > 2 ? (byte)0 : this.commandSeletedIndex;
                    }
                }
            } else {
                this.itemBoxSeletedIndex = this.itemBoxSeletedIndex % 8 == 7 ? (byte)(this.itemBoxSeletedIndex - 7) : (byte)(this.itemBoxSeletedIndex + 1);
                if (this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8 < this.itemPack[0].length) {
                    this.setItemInfo1(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                } else {
                    this.setItemInfo1((byte)-1);
                }
            }
        } else if (Control.IsKeyDown(327712)) {
            if (this.isCommand) {
                block16 : switch (this.commandState) {
                    case 0: {
                        switch (this.commandSeletedIndex) {
                            case 0: {
                                if (this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8] == 9) {
                                    this.commandSeletedIndex = 0;
                                    this.changeCommandState((byte)6);
                                    break block16;
                                }
                                this.changeCommandState((byte)4);
                                break block16;
                            }
                            case 1: {
                                this.needNum = 1;
                                this.changeCommandState((byte)1);
                                break block16;
                            }
                            case 2: {
                                this.finishCommand();
                            }
                        }
                        break;
                    }
                    case 1: {
                        this.changeCommandState((byte)2);
                        break;
                    }
                    case 2: {
                        this.subtractItem(this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8, this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8], (byte)this.needNum);
                        if (this.addState == -2) {
                            if (this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8 < this.itemPack[0].length) {
                                this.setItemInfo1(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                            } else {
                                this.setItemInfo1((byte)-1);
                            }
                        }
                        this.setCommandShowTime();
                        this.commandStr = "\u5df2\u4e22\u5f03\uff01";
                        this.changeCommandState((byte)3);
                        break;
                    }
                    case 3: {
                        this.finishCommand();
                        break;
                    }
                    case 4: {
                        this.commandStr = this.useItem(this.canvas.rolem.fplayerbox[0], this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8], this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8);
                        if (this.addState == -2) {
                            if (this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8 < this.itemPack[0].length) {
                                this.setItemInfo1(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                            } else {
                                this.setItemInfo1((byte)-1);
                            }
                        }
                        this.setCommandShowTime();
                        this.changeCommandState((byte)3);
                        break;
                    }
                    case 5: {
                        switch (this.commandSeletedIndex) {
                            case 0: {
                                this.needNum = 1;
                                this.changeCommandState((byte)1);
                                break block16;
                            }
                            case 1: {
                                this.finishCommand();
                            }
                        }
                        break;
                    }
                    case 6: {
                        if (!this.canvas.rolem.isrideopen(this.commandSeletedIndex)) break;
                        this.useplayerindex = this.commandSeletedIndex;
                        this.commandStr = this.useItem(this.canvas.rolem.fplayerbox[0], this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8], this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8);
                        if (this.addState == -2) {
                            if (this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8 < this.itemPack[0].length) {
                                this.setItemInfo1(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                            } else {
                                this.setItemInfo1((byte)-1);
                            }
                        }
                        this.setCommandShowTime();
                        this.changeCommandState((byte)3);
                    }
                }
            } else if (this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8] != -1) {
                if (this.iscanuse(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8])) {
                    this.startCommand();
                    this.changeCommandState((byte)0);
                    this.commandSeletedIndex = 0;
                } else {
                    this.startCommand();
                    this.changeCommandState((byte)5);
                    this.commandSeletedIndex = 0;
                }
            }
        } else if (Control.IsKeyDown(131072)) {
            if (this.isCommand) {
                switch (this.commandState) {
                    case 0: 
                    case 3: 
                    case 4: 
                    case 5: {
                        this.finishCommand();
                        break;
                    }
                    case 1: {
                        if (this.iscanuse(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8])) {
                            this.changeCommandState((byte)0);
                            this.commandSeletedIndex = 0;
                            break;
                        }
                        this.changeCommandState((byte)5);
                        this.commandSeletedIndex = 0;
                        break;
                    }
                    case 2: {
                        this.changeCommandState((byte)1);
                        break;
                    }
                    case 6: {
                        this.commandSeletedIndex = 0;
                        this.changeCommandState((byte)0);
                    }
                }
            } else {
                this.setpackboxData();
                this.changeUiState((byte)20);
            }
        }
    }

    private void processNeedNum(int dnum, int max) {
        this.needNum += dnum;
        if (this.needNum > max) {
            this.needNum = 1;
        } else if (this.needNum < 1) {
            this.needNum = (byte)max;
        }
    }

    public void initForgeImg() {
        Util.tempData = new byte[10000];
        Util.position[0] = 0;
        Util.getStream(Util.tempData, "/data/forge.data", 0);
        this.forgeModules = Util.loadInfShort(Util.tempData, this.forgeModules, Util.position);
        this.forgeFrames = Util.loadInfShort(Util.tempData, this.forgeFrames, Util.position);
        this.forgeActions = Util.loadInfShort(Util.tempData, this.forgeActions, Util.position);
        this.forgeImage = new Image[this.forgeImgData.length];
        this.forgeImage[0] = Util.createImage("/ui/" + this.forgeImgData[0] + ".png");
        this.forgeImage[1] = Util.createImage_1("/role/" + this.forgeImgData[1] + ".png");
        Util.tempData = null;
    }

    public void cleanForge() {
        for (int i = 0; i < this.forgeImage.length; ++i) {
            this.forgeImage[i] = null;
        }
        this.forgeImage = null;
        this.forgeModules = null;
        this.forgeFrames = null;
        this.forgeActions = null;
    }

    public void drawForgeSprite(byte actionIndex, int x, int y) {
        short frameindex = this.forgeActions[actionIndex][this.forgeActionFrameIndex + 1];
        short moduleIndex = 0;
        for (int i = 0; i < this.forgeFrames[frameindex].length; i += 4) {
            moduleIndex = this.forgeFrames[frameindex][i];
            this.canvas.drawModule(this.forgeImage[this.forgeModules[moduleIndex * 5]], this.forgeFrames[frameindex][i + 1], (int)this.forgeFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.forgeFrames[frameindex][i + 3]], x, y, this.forgeModules, (int)moduleIndex);
        }
        this.nextForgeFrame(actionIndex);
    }

    public void nextForgeFrame(byte actionIndex) {
        this.forgeFrameDelay = (byte)(this.forgeFrameDelay + 1);
        if (this.forgeFrameDelay == this.forgeActions[actionIndex][this.forgeActionFrameIndex]) {
            this.forgeFrameDelay = 0;
            if (!this.forgeActionOver) {
                this.forgeActionFrameIndex = (byte)(this.forgeActionFrameIndex + 2);
            }
            if (!this.forgeActionOver && this.forgeActionFrameIndex == this.forgeActions[actionIndex].length) {
                this.forgeActionFrameIndex = (byte)(this.forgeActions[actionIndex].length - 2);
                this.forgeActionOver = true;
                this.forgeArm(this.currentForgeArmName, this.needMoney);
                this.setForgeCondition(this.currentForgeArmName, (byte)1);
            }
        }
    }

    public void drawForgeModule(int mindex, int x, int y, int anchor, int trans) {
        this.canvas.drawImage(this.forgeImage[this.forgeModules[mindex * 5]], x, y, anchor, trans, this.forgeModules, mindex);
    }

    public void initLight() {
        this.light = new int[30][6];
        for (int i = 0; i < this.light.length; ++i) {
            this.light[i][0] = (short)(Util.getARandomInt(460) + 0 - 50);
            this.light[i][1] = (short)Util.getARandomInt(360);
            this.light[i][2] = (short)Util.getBRandomInt(5);
            this.light[i][3] = -((short)(Util.getARandomInt(4) + 4));
            this.light[i][4] = (short)Util.getARandomInt(4);
            this.light[i][5] = (short)(Util.getARandomInt(200) + 130);
        }
    }

    public void lightUpDate() {
        for (int i = 0; i < this.light.length; ++i) {
            if (this.light[i][1] <= this.light[i][5]) {
                this.light[i][0] = (short)(Util.getARandomInt(460) + 0 - 50);
                this.light[i][1] = (short)(360 + Util.getARandomInt(10));
                this.light[i][2] = (short)Util.getBRandomInt(5);
                this.light[i][3] = -((short)(Util.getARandomInt(4) + 4));
                this.light[i][4] = (short)Util.getARandomInt(4);
                this.light[i][5] = (short)(320 - (Util.getARandomInt(200) + 130));
                continue;
            }
            int[] nArray = this.light[i];
            nArray[0] = nArray[0] + this.light[i][2];
            int[] nArray2 = this.light[i];
            nArray2[1] = nArray2[1] + this.light[i][3];
        }
    }

    public void drawLight() {
        for (int i = 0; i < this.light.length; ++i) {
            this.drawForgeModule(this.light[i][4], this.light[i][0], this.light[i][1], 3, 0);
        }
    }

    private void drawWeaponData(byte name, byte weaponIndex, int x, int y) {
        int colorIndex = 0;
        if (name - 44 > -1) {
            colorIndex = this.canvas.rolem.armData[name - 44][0] > this.canvas.rolem.armData[this.canvas.rolem.playerCurrentWeapon[weaponIndex] - 44][0] ? 2 : (this.canvas.rolem.armData[name - 44][0] < this.canvas.rolem.armData[this.canvas.rolem.playerCurrentWeapon[weaponIndex] - 44][0] ? 6 : 1);
            this.drawUiModule(128, x, y, 20, 0);
            int len = this.drawNumber(this.canvas.rolem.armData[name - 44][0], x + 50, y + 15, false, 36);
            switch (colorIndex) {
                case 2: {
                    this.drawUiModule(134, x + 5 + len + 50, y + 15 + 2 - 1, 36, 0);
                    break;
                }
                case 6: {
                    this.drawUiModule(135, x + 5 + len + 50, y + 15 + 2 - 1, 36, 0);
                }
            }
            colorIndex = this.canvas.rolem.armData[name - 44][1] > this.canvas.rolem.armData[this.canvas.rolem.playerCurrentWeapon[weaponIndex] - 44][1] ? 2 : (this.canvas.rolem.armData[name - 44][1] < this.canvas.rolem.armData[this.canvas.rolem.playerCurrentWeapon[weaponIndex] - 44][1] ? 6 : 1);
            this.drawUiModule(132, x, y + 30, 20, 0);
            len = this.drawNumber(this.canvas.rolem.armData[name - 44][1], x + 50, y + 15 + 30, true, 36);
            switch (colorIndex) {
                case 2: {
                    this.drawUiModule(134, x + 5 + len + 50, y + 15 + 30 + 2 - 1, 36, 0);
                    break;
                }
                case 6: {
                    this.drawUiModule(135, x + 5 + len + 50, y + 15 + 30 + 2 - 1, 36, 0);
                }
            }
            colorIndex = this.canvas.rolem.armData[name - 44][2] > this.canvas.rolem.armData[this.canvas.rolem.playerCurrentWeapon[weaponIndex] - 44][2] ? 2 : (this.canvas.rolem.armData[name - 44][2] < this.canvas.rolem.armData[this.canvas.rolem.playerCurrentWeapon[weaponIndex] - 44][2] ? 6 : 1);
            this.drawUiModule(133, x + 120, y, 20, 0);
            len = this.drawNumber(this.canvas.rolem.armData[name - 44][2] + 100, x + 120 + 50, y + 15, false, 36);
            switch (colorIndex) {
                case 2: {
                    this.drawUiModule(134, x + 120 + 5 + len + 50, y + 15 + 2 - 1, 36, 0);
                    break;
                }
                case 6: {
                    this.drawUiModule(135, x + 120 + 5 + len + 50, y + 15 + 2 - 1, 36, 0);
                }
            }
        }
    }

    private void drawWeaponData1(byte name, byte weaponIndex, boolean offsetflag) {
        this.drawlabel("\u6b66\u5668\u6570\u636e", 65, 128, 4, 1);
        int colorIndex = 0;
        int len = 200;
        int y = 167;
        int x = 75;
        if (name - 44 > -1) {
            if (this.canvas.rolem.playerCurrentWeapon[weaponIndex] > -1) {
                colorIndex = this.canvas.rolem.armData[name - 44][0] > this.canvas.rolem.armData[this.canvas.rolem.playerCurrentWeapon[weaponIndex] - 44][0] ? 2 : (this.canvas.rolem.armData[name - 44][0] < this.canvas.rolem.armData[this.canvas.rolem.playerCurrentWeapon[weaponIndex] - 44][0] ? 6 : 1);
            }
            this.drawUiModule(128, x, y, 20, 0);
            len = this.drawNumber(this.canvas.rolem.armData[name - 44][0], x + 50, y + 15, false, 36);
            switch (colorIndex) {
                case 2: {
                    this.drawUiModule(134, x + 5 + len + 50, y + 15 + 2 - 1 + (offsetflag ? -12 : 0), 20, 0);
                    break;
                }
                case 6: {
                    this.drawUiModule(135, x + 5 + len + 50, y + 15 + 2 - 1 + (offsetflag ? -12 : 0), 20, 0);
                }
            }
            if (this.canvas.rolem.playerCurrentWeapon[weaponIndex] > -1) {
                colorIndex = this.canvas.rolem.armData[name - 44][1] > this.canvas.rolem.armData[this.canvas.rolem.playerCurrentWeapon[weaponIndex] - 44][1] ? 2 : (this.canvas.rolem.armData[name - 44][1] < this.canvas.rolem.armData[this.canvas.rolem.playerCurrentWeapon[weaponIndex] - 44][1] ? 6 : 1);
            }
            this.drawUiModule(132, x + 120, y, 20, 0);
            len = this.drawNumber(this.canvas.rolem.armData[name - 44][1], x + 120 + 50, y + 15, true, 36);
            switch (colorIndex) {
                case 2: {
                    this.drawUiModule(134, x + 120 + 5 + len + 50, y + 15 + 2 - 1 + (offsetflag ? -12 : 0), 20, 0);
                    break;
                }
                case 6: {
                    this.drawUiModule(135, x + 120 + 5 + len + 50, y + 15 + 2 - 1 + (offsetflag ? -12 : 0), 20, 0);
                }
            }
            if (this.canvas.rolem.playerCurrentWeapon[weaponIndex] > -1) {
                colorIndex = this.canvas.rolem.armData[name - 44][2] > this.canvas.rolem.armData[this.canvas.rolem.playerCurrentWeapon[weaponIndex] - 44][2] ? 2 : (this.canvas.rolem.armData[name - 44][2] < this.canvas.rolem.armData[this.canvas.rolem.playerCurrentWeapon[weaponIndex] - 44][2] ? 6 : 1);
            }
            this.drawUiModule(133, x + 240, y, 20, 0);
            len = this.drawNumber(this.canvas.rolem.armData[name - 44][2] + 100, x + 240 + 50, y + 15, false, 36);
            switch (colorIndex) {
                case 2: {
                    this.drawUiModule(134, x + 240 + 5 + len + 50, y + 15 + 2 - 1 + (offsetflag ? -12 : 0), 20, 0);
                    break;
                }
                case 6: {
                    this.drawUiModule(135, x + 240 + 5 + len + 50, y + 15 + 2 - 1 + (offsetflag ? -12 : 0), 20, 0);
                }
            }
        }
    }

    private void drawSuitData(byte name, boolean offsetflag) {
        this.drawlabel("\u88c5\u5907\u6570\u636e", 65, 128, 4, 1);
        int colorIndex = 0;
        int len = 200;
        int y = 167;
        int x = 75;
        if (name - 44 > -1) {
            if (this.canvas.rolem.playerCurrentSuit > -1) {
                colorIndex = this.canvas.rolem.armData[name - 44][0] > this.canvas.rolem.armData[this.canvas.rolem.playerCurrentSuit - 44][0] ? 2 : (this.canvas.rolem.armData[name - 44][0] < this.canvas.rolem.armData[this.canvas.rolem.playerCurrentSuit - 44][0] ? 6 : 1);
            }
            this.drawUiModule(33, x, y, 20, 0);
            len = this.drawNumber(this.canvas.rolem.armData[name - 44][0], x + 50, y + 15, false, 36);
            switch (colorIndex) {
                case 2: {
                    this.drawUiModule(134, x + 5 + len + 50, y + 15 + 2 - 1 + (offsetflag ? -12 : 0), 20, 0);
                    break;
                }
                case 6: {
                    this.drawUiModule(135, x + 5 + len + 50, y + 15 + 2 - 1 + (offsetflag ? -12 : 0), 20, 0);
                }
            }
            if (this.canvas.rolem.playerCurrentSuit > -1) {
                colorIndex = this.canvas.rolem.armData[name - 44][1] > this.canvas.rolem.armData[this.canvas.rolem.playerCurrentSuit - 44][1] ? 2 : (this.canvas.rolem.armData[name - 44][1] < this.canvas.rolem.armData[this.canvas.rolem.playerCurrentSuit - 44][1] ? 6 : 1);
            }
            this.drawUiModule(32, x + 120, y, 20, 0);
            len = this.drawNumber(this.canvas.rolem.armData[name - 44][1], x + 120 + 50, y + 15, false, 36);
            switch (colorIndex) {
                case 2: {
                    this.drawUiModule(134, x + 120 + 5 + len + 50, y + 15 + 2 - 1 + (offsetflag ? -12 : 0), 20, 0);
                    break;
                }
                case 6: {
                    this.drawUiModule(135, x + 120 + 5 + len + 50, y + 15 + 2 - 1 + (offsetflag ? -12 : 0), 20, 0);
                }
            }
            if (this.canvas.rolem.playerCurrentSuit > -1) {
                colorIndex = this.canvas.rolem.armData[name - 44][2] > this.canvas.rolem.armData[this.canvas.rolem.playerCurrentSuit - 44][2] ? 2 : (this.canvas.rolem.armData[name - 44][2] < this.canvas.rolem.armData[this.canvas.rolem.playerCurrentSuit - 44][2] ? 6 : 1);
            }
            this.drawUiModule(129, x + 240, y, 20, 0);
            len = this.drawNumber(this.canvas.rolem.armData[name - 44][2], x + 240 + 50, y + 15, false, 36);
            switch (colorIndex) {
                case 2: {
                    this.drawUiModule(134, x + 240 + 5 + len + 50, y + 15 + 2 - 1 + (offsetflag ? -12 : 0), 20, 0);
                    break;
                }
                case 6: {
                    this.drawUiModule(135, x + 240 + 5 + len + 50, y + 15 + 2 - 1 + (offsetflag ? -12 : 0), 20, 0);
                }
            }
        }
    }

    public void setplusdatastr(byte name) {
        String str = null;
        if (name == 123) {
            str = "$1" + this.plusdatastr[this.plusData[name - 88]] + ": " + this.canvas.rolem.armData[name - 44][0] / 10 + " \u79d2";
        } else {
            if (name < 44) {
                return;
            }
            str = this.canvas.rolem.armData[name - 44][0] == this.canvas.rolem.armData[name - 44][1] ? "$1" + this.plusdatastr[this.plusData[name - 88]] + ": " + this.canvas.rolem.armData[name - 44][0] + " %" : "$1" + this.plusdatastr[this.plusData[name - 88]] + ": " + this.canvas.rolem.armData[name - 44][0] + " %" + "--" + this.canvas.rolem.armData[name - 44][1] + " %";
        }
        this.currentplusdatastr = this.canvas.formatzkword(str.toString(), 200, 0);
    }

    private void drawPlusData(int x, int y) {
        if (this.currentplusdatastr != null) {
            this.canvas.drawZkStringArry(this.currentplusdatastr, x, y, 20, 0, this.currentplusdatastr.length, 0);
        }
    }

    private void drawPlusData1(int index) {
        byte name;
        this.drawlabel("\u63d2\u4ef6\u6570\u636e", 65, 128, 4, 1);
        if (index > -1 && (name = this.plusPack[0][index]) >= 88) {
            if (name == 123) {
                this.canvas.drawZkString("$1" + this.plusdatastr[this.plusData[name - 88]] + ": " + this.plusPack[2][index] / 10 + " \u79d2", 75, 155, 0, this.canvas.zkData);
            } else {
                this.canvas.drawZkString("$1" + this.plusdatastr[this.plusData[name - 88]] + ": " + this.plusPack[2][index] + " \uff05", 75, 155, 0, this.canvas.zkData);
            }
        }
    }

    private void drawForgeCondition() {
        this.drawlabel("\u953b\u9020\u6750\u6599", 337, 160, 4, 1);
        this.scroll.drawscroll();
    }

    private void drawForgeCondition1() {
        this.drawlabel("\u953b\u9020\u6750\u6599", 379, 237, 4, 1);
        this.scroll.drawscroll();
    }

    public void createWeaponImage(byte name) {
        byte type = this.canvas.rolem.setWeaponType(name);
        switch (type) {
            case 0: {
                this.weaponImage = Util.createImage_1("/role/" + this.canvas.rolem.jianImageNum[name - 44] + ".png");
                break;
            }
            case 1: {
                this.weaponImage = Util.createImage_1("/role/" + this.canvas.rolem.quanImageNum[name - 59] + ".png");
                break;
            }
            case 2: {
                this.weaponImage = Util.createImage_1("/role/" + this.canvas.rolem.qiangImageNum[name - 74] + ".png");
            }
        }
    }

    public void initcityshownews(byte cityindex) {
        byte by = cityindex;
        this.isshowcitynews[by] = (byte)(this.isshowcitynews[by] | this.citynewsdata[cityindex]);
        byte by2 = cityindex;
        this.isshowforgenews[by2] = (byte)(this.isshowforgenews[by2] | 5);
        for (int i = 0; i < this.saleMaterialLevel[cityindex].length; ++i) {
            if (this.saleMaterialLevel[cityindex][i] != this.canvas.mapm.cityMapLevel[cityindex]) continue;
            byte by3 = cityindex;
            this.isshowsalematerialnews[by3] = this.isshowsalematerialnews[by3] | 1 << i;
        }
    }

    public void checkforgeboxshownews(byte cityindex, byte selectindex) {
        switch (this.forgeBoxState) {
            case 0: {
                if (this.isshowforgenews[cityindex] <= 0 || (this.isshowforgenews[cityindex] & 1 << selectindex) == 0) break;
                byte by = cityindex;
                this.isshowforgenews[by] = (byte)(this.isshowforgenews[by] ^ 1 << selectindex);
                break;
            }
            case 2: {
                if (this.isshowforgearmnews[this.canvas.mapm.cityMapIndex][this.forgeBoxIndex] <= 0 || (this.isshowforgearmnews[this.canvas.mapm.cityMapIndex][this.forgeBoxIndex] & 1 << selectindex) == 0) break;
                short[] sArray = this.isshowforgearmnews[this.canvas.mapm.cityMapIndex];
                byte by = this.forgeBoxIndex;
                sArray[by] = (short)(sArray[by] ^ 1 << selectindex);
                break;
            }
            case 5: {
                if (this.isshowsalematerialnews[cityindex] <= 0 || (this.isshowsalematerialnews[cityindex] & 1 << selectindex) == 0) break;
                byte by = cityindex;
                this.isshowsalematerialnews[by] = this.isshowsalematerialnews[by] ^ 1 << selectindex;
            }
        }
    }

    public void drawforgeboxshownews() {
        if (!this.isCommand) {
            switch (this.forgeBoxState) {
                case 0: {
                    if (this.isshowforgenews[this.canvas.mapm.cityMapIndex] <= 0) break;
                    for (int i = 0; i < 3; ++i) {
                        if ((this.isshowforgenews[this.canvas.mapm.cityMapIndex] & 1 << i) == 0) continue;
                        this.drawStaticUi(this.shownewsactiondata[this.shownewsframeindex], 377, 140 + i * 42 + 55);
                    }
                    break;
                }
                case 2: {
                    for (int i = 0; i < 3; i = (int)((byte)(i + 1))) {
                        if (this.isshowforgearmnews[this.canvas.mapm.cityMapIndex][i] <= 0) continue;
                        for (int j = 0; j < 9; j = (int)((byte)(j + 1))) {
                            if ((this.isshowforgearmnews[this.canvas.mapm.cityMapIndex][i] & 1 << j + this.forgeBoxStartIndex[this.forgeBoxIndex]) == 0) continue;
                            this.drawStaticUi(this.shownewsactiondata[this.shownewsframeindex], 33 + j * UI_ICON_ITEM_STEP_X + 97, 75 + i * UI_ICON_ITEM_STEP_Y);
                        }
                    }
                    break;
                }
                case 5: {
                    if (this.isshowsalematerialnews[this.canvas.mapm.cityMapIndex] <= 0 || this.materialShopIndex != 0 || !this.isoffsetactionover) break;
                    for (int i = 0; i < 5; ++i) {
                        if ((this.isshowsalematerialnews[this.canvas.mapm.cityMapIndex] & 1 << this.materialBuyStartIndex + i) == 0) continue;
                        this.drawStaticUi(this.shownewsactiondata[this.shownewsframeindex], 95, 60 + i * 49 + 20);
                    }
                    break;
                }
            }
            this.shownewsframeprocess();
        }
    }

    private void shownewsframeprocess() {
        this.shownewsframeindex = (byte)(this.shownewsframeindex + 1);
        if (this.shownewsframeindex >= 6) {
            this.shownewsframeindex = 0;
        }
    }

    public void checkcityshownews(byte cityindex, byte buildindex) {
        if (this.isshowcitynews[cityindex] > 0 && (this.isshowcitynews[cityindex] & 1 << buildindex) != 0) {
            byte by = cityindex;
            this.isshowcitynews[by] = (byte)(this.isshowcitynews[by] ^ 1 << buildindex);
        }
    }

    public void checkshowmissionnews(byte missionindex) {
        if (this.isshowmissionnews > 0 && (this.isshowmissionnews & 1 << missionindex) != 0) {
            this.isshowmissionnews = (short)(this.isshowmissionnews ^ 1 << missionindex);
        }
    }

    public void drawshowmissionnews() {
        for (int i = 0; i < (6 < this.townhouseBoxUseTotal ? 6 : (int)this.townhouseBoxUseTotal); ++i) {
            if ((this.isshowmissionnews & 1 << this.currentmissionlist[i + this.townhouseBoxStartIndex]) == 0) continue;
            this.drawStaticUi(this.shownewsactiondata[this.shownewsframeindex], 430, 92 + i * 37);
        }
        this.shownewsframeprocess();
    }

    public void drawcitynews() {
        if (this.isshowcitynews[this.canvas.mapm.cityMapIndex] > 0) {
            for (int i = 0; i < this.canvas.mapm.citybuildnum; ++i) {
                if ((this.isshowcitynews[this.canvas.mapm.cityMapIndex] & 1 << i) == 0) continue;
                this.drawStaticUi(this.shownewsactiondata[this.shownewsframeindex], 455, 57 + i * 44);
            }
            this.shownewsframeprocess();
        }
    }

    public void initforgearmdata() {
        int j;
        int i;
        this.currentForgeArm = new byte[3][12];
        this.currentForgeArmlevel = new byte[3][12];
        this.currentForgeArmTotal = new byte[3];
        for (i = 0; i < 3; i = (int)((byte)(i + 1))) {
            for (j = 0; j < 12; j = (int)((byte)(j + 1))) {
                this.currentForgeArm[i][j] = -1;
                this.currentForgeArmlevel[i][j] = -1;
            }
        }
        for (i = 0; i < this.needcitylevel[this.canvas.mapm.cityMapIndex].length; i = (int)((byte)(i + 1))) {
            for (j = 0; j < this.needcitylevel[this.canvas.mapm.cityMapIndex][i].length; j = (int)((byte)(j + 1))) {
                if (this.canvas.mapm.cityMapLevel[this.canvas.mapm.cityMapIndex] < this.needcitylevel[this.canvas.mapm.cityMapIndex][i][j] - 1) continue;
                this.currentForgeArm[i][j] = this.allForgeArm[this.canvas.mapm.cityMapIndex][i][j];
                this.currentForgeArmlevel[i][j] = this.needcitylevel[this.canvas.mapm.cityMapIndex][i][j];
            }
        }
        for (i = 0; i < 3; i = (int)((byte)(i + 1))) {
            for (j = 0; j < 12; j = (int)((byte)(j + 1))) {
                if (this.currentForgeArm[i][j] <= -1) continue;
                int n = i;
                this.currentForgeArmTotal[n] = (byte)(this.currentForgeArmTotal[n] + 1);
            }
        }
    }

    public void initmaterialdata() {
        int i;
        byte[] tempdata = new byte[this.saleMaterialLevel[this.canvas.mapm.cityMapIndex].length];
        int skip = 0;
        for (i = 0; i < this.saleMaterialLevel[this.canvas.mapm.cityMapIndex].length; ++i) {
            if (this.canvas.mapm.cityMapLevel[this.canvas.mapm.cityMapIndex] < this.saleMaterialLevel[this.canvas.mapm.cityMapIndex][i]) continue;
            tempdata[i] = this.allSaleMaterial[this.canvas.mapm.cityMapIndex][i];
            skip = (byte)(skip + 1);
        }
        this.currentSaleMaterial = new byte[skip];
        for (i = 0; i < this.currentSaleMaterial.length; ++i) {
            this.currentSaleMaterial[i] = tempdata[i];
        }
    }

    public void openweapon(byte cityindex, byte citylevel) {
        for (int i = 0; i < this.needcitylevel[cityindex].length; ++i) {
            for (int j = 0; j < this.needcitylevel[cityindex][i].length; ++j) {
                if (citylevel < this.needcitylevel[cityindex][i][j] || this.needBook[this.allForgeArm[cityindex][i][j] - 44] != -1) continue;
                this.forgeState[this.allForgeArm[cityindex][i][j] - 44] = 0;
                if (citylevel != this.needcitylevel[cityindex][i][j]) continue;
                short[] sArray = this.isshowforgearmnews[cityindex];
                int n = i;
                sArray[n] = (short)(sArray[n] | 1 << j);
            }
        }
    }

    public void openWeapon(byte wname) {
        this.forgeState[wname - 44] = 0;
    }

    public void setforgebossdata(byte cityindex) {
        this.scroll.close();
        this.scroll.setscroll(this.citytalk[cityindex][0], 115, 80, 115, 140, 435, 60, -1, 2, 20);
        if (this.forgeBossImage == null) {
            this.forgeBossImage = Util.createImage("/fightHead/" + this.forgebossimagedata[cityindex] + ".png");
        }
    }

    public void setshopbossdata(byte cityindex) {
        this.scroll.close();
        if (cityindex == 4) {
            this.scroll.setscroll(this.citytalk[cityindex][1], 20, 60, 20, 100, 200, 40, -1, 2, 20);
        } else {
            this.scroll.setscroll(this.citytalk[cityindex][1], 65, 60, 65, 100, 160, 40, -1, 2, 20);
            if (this.shopBossImage == null) {
                this.shopBossImage = Util.createImage("/fightHead/" + this.shopbossimagedata[cityindex] + ".png");
            }
        }
    }

    public void setCityIntroBossData(byte cityindex) {
        if (cityindex > 3) {
            cityindex = 0;
        }
        this.scroll.close();
        this.scroll.setscroll(this.citytalk[cityindex][2], 115, 80, 115, 140, 435, 60, -1, 2, 20);
        if (this.cityIntroBossImage == null) {
            this.cityIntroBossImage = Util.createImage("/fightHead/" + this.cityIntrobossimagedata[cityindex] + ".png");
        }
    }

    public void setforgebookData() {
        this.scroll.close();
        this.scroll.setscroll(this.citytalk[4][2], 115, 80, 115, 120, 400, 60, -1, 2, 20);
    }

    public void setpackboxData() {
        this.scroll.close();
        this.scroll.setscroll(this.citytalk[4][0], 115, 80, 115, 120, 400, 60, -1, 2, 20);
    }

    public void clearCityIntroBossData() {
        this.scroll.close();
        this.cityIntroBossImage = null;
    }

    private void drawforgedata(byte index, byte name) {
        this.drawlabel("\u88c5\u5907\u6570\u636e", 336, 64, 4, 0);
        switch (index) {
            case 0: 
            case 1: {
                this.drawWeaponData(name, index, 335, 85);
                break;
            }
            case 2: {
                this.drawPlusData(345, 85);
            }
        }
    }

    private void drawforgedata1(byte index, byte name) {
        this.drawlabel("\u88c5\u5907\u6570\u636e", 35, 237, 4, 0);
        switch (index) {
            case 0: 
            case 1: {
                this.drawWeaponData(name, index, 34, 260);
                break;
            }
            case 2: {
                this.drawPlusData(44, 258);
            }
        }
    }

    private void drawarmshopdata(int j) {
        switch (j) {
            case 0: {
                if (this.forgeSaleWeaponUseTotal == 0) {
                    this.canvas.drawColorString("\u6ca1\u6709\u6b66\u5668\u3002", 320 + this.offsetx + 640 * j, 180, 3, 0xFFFFFF, 0);
                    break;
                }
                for (int i = 0; i < 5; ++i) {
                    if (i == this.armSaleSeletedIndex) {
                        this.drawStaticUi((short)39, 29 + this.offsetx + 640 * j + 18 + 45, 50 + this.armSaleSeletedIndex * 49 + 20);
                    } else {
                        this.drawStaticUi((short)40, 30 + this.offsetx + 640 * j + 18 + 45, 51 + i * 49 + 20);
                    }
                    this.drawarmicon(this.weaponPack[0][this.armSaleStartIndex + i], 30 + this.offsetx + 640 * j + 18 + 45, 48 + i * 49 + 20);
                    if (i == this.armSaleSeletedIndex) {
                        this.drawiteminfo(this.weaponPack[0][this.armSaleStartIndex + i], 50 + this.offsetx + 640 * j + 51 + 45, 61 + i * 49 + 20, 15597319, 1);
                        this.drawitemiconcheck(30 + this.offsetx + 640 * j + 18 + 45, 49 + i * 49 + 20, this.weaponPack[0][this.armSaleStartIndex + this.armSaleSeletedIndex]);
                    } else {
                        this.drawiteminfo(this.weaponPack[0][this.armSaleStartIndex + i], 50 + this.offsetx + 640 * j + 51 + 45, 61 + i * 49 + 20, 10333123, 1);
                        this.drawitemiconmembrane(30 + this.offsetx + 640 * j + 18 + 45, 48 + i * 49 + 20, this.weaponPack[0][this.armSaleStartIndex + i]);
                    }
                    if (this.weaponPack[1][this.armSaleStartIndex + i] <= -1) continue;
                    this.drawUiModule(180, 55 + this.offsetx + 640 * j + 18 + 45, 76 + i * 49 + 20, 20, 0);
                }
                this.drawStaticUi((short)122, 320 + this.offsetx + 640 * j + 45, 180);
                this.drawjindutiaoV(7 + this.offsetx + 640 * j + 11 + 45, 69, 8, 233, this.armSaleSeletedIndex + this.armSaleStartIndex, this.forgeSaleWeaponUseTotal, (byte)1);
                break;
            }
            case 1: {
                if (this.forgeSaleSuitUseTotal == 0) {
                    this.canvas.drawColorString("\u6ca1\u6709\u9632\u5177\u3002", 320 + this.offsetx + 640 * j, 180, 3, 0xFFFFFF, 0);
                    break;
                }
                for (int i = 0; i < 5; ++i) {
                    if (i == this.armSaleSeletedIndex) {
                        this.drawStaticUi((short)39, 29 + this.offsetx + 640 * j + 18 + 45, 50 + this.armSaleSeletedIndex * 49 + 20);
                    } else {
                        this.drawStaticUi((short)40, 30 + this.offsetx + 640 * j + 18 + 45, 51 + i * 49 + 20);
                    }
                    this.drawarmicon(this.suitPack[0][this.armSaleStartIndex + i], 30 + this.offsetx + 640 * j + 18 + 45, 48 + i * 49 + 20);
                    if (i == this.armSaleSeletedIndex) {
                        this.drawiteminfo(this.suitPack[0][this.armSaleStartIndex + i], 50 + this.offsetx + 640 * j + 51 + 45, 61 + i * 49 + 20, 15597319, 1);
                        this.drawitemiconcheck(30 + this.offsetx + 640 * j + 18 + 45, 49 + i * 49 + 20, this.suitPack[0][this.armSaleStartIndex + this.armSaleSeletedIndex]);
                    } else {
                        this.drawiteminfo(this.suitPack[0][this.armSaleStartIndex + i], 50 + this.offsetx + 640 * j + 51 + 45, 61 + i * 49 + 20, 10333123, 1);
                        this.drawitemiconmembrane(30 + this.offsetx + 640 * j + 18 + 45, 48 + i * 49 + 20, this.suitPack[0][this.armSaleStartIndex + i]);
                    }
                    if (this.suitPack[1][this.armSaleStartIndex + i] <= -1) continue;
                    this.drawUiModule(180, 55 + this.offsetx + 640 * j + 18 + 45, 76 + i * 49 + 20, 20, 0);
                }
                this.drawStaticUi((short)122, 320 + this.offsetx + 640 * j + 45, 180);
                this.drawjindutiaoV(7 + this.offsetx + 640 * j + 11 + 45, 69, 8, 233, this.armSaleSeletedIndex + this.armSaleStartIndex, this.forgeSaleSuitUseTotal, (byte)1);
                break;
            }
            case 2: {
                if (this.forgeSalePlusUseTotal == 0) {
                    this.canvas.drawColorString("\u6ca1\u6709\u9970\u54c1\u3002", 320 + this.offsetx + 640 * j, 180, 3, 0xFFFFFF, 0);
                    break;
                }
                for (int i = 0; i < 5; ++i) {
                    if (i == this.armSaleSeletedIndex) {
                        this.drawStaticUi((short)39, 29 + this.offsetx + 640 * j + 18 + 45, 50 + this.armSaleSeletedIndex * 49 + 20);
                    } else {
                        this.drawStaticUi((short)40, 30 + this.offsetx + 640 * j + 18 + 45, 51 + i * 49 + 20);
                    }
                    this.drawarmicon(this.plusPack[0][this.armSaleStartIndex + i], 30 + this.offsetx + 640 * j + 18 + 45, 48 + i * 49 + 20);
                    if (i == this.armSaleSeletedIndex) {
                        this.drawiteminfo(this.plusPack[0][this.armSaleStartIndex + i], 50 + this.offsetx + 640 * j + 51 + 45, 61 + i * 49 + 20, 15597319, 1);
                        this.drawitemiconcheck(30 + this.offsetx + 640 * j + 18 + 45, 49 + i * 49 + 20, this.plusPack[0][this.armSaleStartIndex + this.armSaleSeletedIndex]);
                    } else {
                        this.drawiteminfo(this.plusPack[0][this.armSaleStartIndex + i], 50 + this.offsetx + 640 * j + 51 + 45, 61 + i * 49 + 20, 10333123, 1);
                        this.drawitemiconmembrane(30 + this.offsetx + 640 * j + 18 + 45, 48 + i * 49 + 20, this.plusPack[0][this.armSaleStartIndex + i]);
                    }
                    if (this.plusPack[1][this.armSaleStartIndex + i] <= -1) continue;
                    this.drawUiModule(180, 55 + this.offsetx + 640 * j + 18 + 45, 76 + i * 49 + 20, 20, 0);
                }
                this.drawStaticUi((short)122, 320 + this.offsetx + 640 * j + 45, 180);
                this.drawjindutiaoV(7 + this.offsetx + 640 * j + 11 + 45, 69, 8, 233, this.armSaleSeletedIndex + this.armSaleStartIndex, this.forgeSalePlusUseTotal, (byte)1);
            }
        }
    }

    private void drawmaterialshopdata(int j) {
        switch (j) {
            case 0: {
                if (this.materialBuyUseTotal == 0) {
                    this.canvas.drawColorString("\u6ca1\u6709\u6750\u6599\u3002", 320 + this.offsetx + 640 * j, 180, 3, 0xFFFFFF, 0);
                    break;
                }
                for (int i = 0; i < 5; ++i) {
                    if (i == this.materialBuySeletedIndex) {
                        this.drawStaticUi((short)39, 29 + this.offsetx + 640 * j + 18 + 45, 50 + this.materialBuySeletedIndex * 49 + 20);
                    } else {
                        this.drawStaticUi((short)40, 30 + this.offsetx + 640 * j + 18 + 45, 51 + i * 49 + 20);
                    }
                    this.drawarmicon(this.currentSaleMaterial[this.materialBuyStartIndex + i], 30 + this.offsetx + 640 * j + 18 + 45, 50 + i * 49 + 20);
                    if (i == this.materialBuySeletedIndex) {
                        this.drawiteminfo(this.currentSaleMaterial[this.materialBuyStartIndex + i], 50 + this.offsetx + 640 * j + 51 + 45, 61 + i * 49 + 20, 15597319, 0);
                        this.drawitemiconcheck(30 + this.offsetx + 640 * j + 18 + 45, 49 + i * 49 + 20, this.currentSaleMaterial[this.materialBuyStartIndex + this.materialBuySeletedIndex]);
                        continue;
                    }
                    this.drawiteminfo(this.currentSaleMaterial[this.materialBuyStartIndex + i], 49 + this.offsetx + 640 * j + 51 + 45, 61 + i * 49 + 20, 10333123, 0);
                    this.drawitemiconmembrane(30 + this.offsetx + 640 * j + 18 + 45, 48 + i * 49 + 20, this.currentSaleMaterial[this.materialBuyStartIndex + i]);
                }
                this.drawStaticUi((short)122, 320 + this.offsetx + 640 * j + 45, 180);
                this.drawjindutiaoV(7 + this.offsetx + 640 * j + 11 + 45, 69, 8, 233, this.materialBuySeletedIndex + this.materialBuyStartIndex, this.materialBuyUseTotal, (byte)1);
                break;
            }
            case 1: {
                if (this.materialSaleUseTotal == 0) {
                    this.canvas.drawColorString("\u6ca1\u6709\u6750\u6599\u3002", 320 + this.offsetx + 640 * j, 180, 3, 0xFFFFFF, 0);
                    break;
                }
                for (int i = 0; i < 5; ++i) {
                    if (i == this.materialSaleSeletedIndex) {
                        this.drawStaticUi((short)39, 29 + this.offsetx + 640 * j + 18 + 45, 50 + this.materialSaleSeletedIndex * 49 + 20);
                    } else {
                        this.drawStaticUi((short)40, 30 + this.offsetx + 640 * j + 18 + 45, 51 + i * 49 + 20);
                    }
                    this.drawitemicon(this.itemPack[0][this.materialSaleStartIndex + i], this.itemPack[1][this.materialSaleStartIndex + i], 30 + this.offsetx + 640 * j + 18 + 45, 50 + i * 49 + 20);
                    if (i == this.materialSaleSeletedIndex) {
                        this.drawiteminfo(this.itemPack[0][this.materialSaleStartIndex + i], 50 + this.offsetx + 640 * j + 51 + 45, 61 + i * 49 + 20, 15597319, 1);
                        this.drawitemiconcheck(30 + this.offsetx + 640 * j + 18 + 45, 49 + i * 49 + 20, this.itemPack[0][this.materialSaleStartIndex + this.materialSaleSeletedIndex]);
                        continue;
                    }
                    this.drawiteminfo(this.itemPack[0][this.materialSaleStartIndex + i], 49 + this.offsetx + 640 * j + 51 + 45, 61 + i * 49 + 20, 10333123, 1);
                    this.drawitemiconmembrane(30 + this.offsetx + 640 * j + 18 + 45, 48 + i * 49 + 20, this.itemPack[0][this.materialSaleStartIndex + i]);
                }
                this.drawStaticUi((short)122, 320 + this.offsetx + 640 * j + 45, 180);
                this.drawjindutiaoV(7 + this.offsetx + 640 * j + 11 + 45, 69, 8, 233, this.materialSaleSeletedIndex + this.materialSaleStartIndex, this.materialSaleUseTotal, (byte)1);
            }
        }
    }

    private void drawForgeBox() {
        block73: {
            this.drawStaticUiBack((short)0);
            block0 : switch (this.forgeBoxState) {
                case 0: {
                    this.drawStaticUi((short)88);
                    this.drawUiModule(290, LABEL_X[0][0] - 20, 41, 36, 0);
                    this.drawStaticUi((short)112);
                    this.drawStaticUi((short)113);
                    this.drawStaticUi((short)16, 0, 360);
                    this.canvas.drawImage(this.forgeBossImage, 70, 105, 36, 2);
                    this.scroll.drawscroll();
                    for (byte i = 0; i < 3; i = (byte)(i + 1)) {
                        if (i == this.factoryIndex) {
                            this.drawStaticUi((short)115, 320, 175 + this.factoryIndex * 42);
                            this.canvas.drawColorString(this.forgeboxoption[i], 320, 185 + i * 42, 17, 15597319, 0);
                            continue;
                        }
                        this.drawStaticUi((short)114, 320, 175 + i * 42);
                        this.canvas.drawColorString(this.forgeboxoption[i], 320, 185 + i * 42, 17, 10333123, 0);
                    }
                    break;
                }
                case 1: {
                    this.drawStaticUi((short)88);
                    this.drawUiModule(256, LABEL_X[0][0] - 2, 42, 36, 0);
                    this.drawStaticUi((short)16, 0, 360);
                    this.drawUiModule(242, 1, 44, 20, 0);
                    break;
                }
                case 2: {
                    this.drawStaticUi((short)88);
                    this.drawUiModule(256, LABEL_X[0][0] - 2, 42, 36, 0);
                    this.drawStaticUi((short)112);
                    this.drawStaticUi((short)90);
                    this.drawStaticUi((short)116, 0, 360);
                    this.drawforgeframe();
                    this.drawUiModule(0, 27 + this.forgeBoxSeletedIndex % 9 * UI_ICON_ITEM_STEP_X + 92, 63 + this.forgeBoxIndex * UI_ICON_ITEM_STEP_Y, 20, 0);
                    for (int i = 0; i < 3; i = (int)((byte)(i + 1))) {
                        for (int j = 0; j < 9; j = (int)((byte)(j + 1))) {
                            if (this.currentForgeArm[i][j + this.forgeBoxStartIndex[i]] > -1) {
                                if (this.forgeState[this.currentForgeArm[i][j + this.forgeBoxStartIndex[i]] - 44] == 0) {
                                    this.drawarmicon(this.currentForgeArm[i][j + this.forgeBoxStartIndex[i]], 28 + j * UI_ICON_ITEM_STEP_X + 92, 62 + i * UI_ICON_ITEM_STEP_Y);
                                } else {
                                    this.drawarmicon(this.currentForgeArm[i][j + this.forgeBoxStartIndex[i]], 28 + j * UI_ICON_ITEM_STEP_X + 92, 62 + i * UI_ICON_ITEM_STEP_Y);
                                    this.drawitemiconmembrane(28 + j * UI_ICON_ITEM_STEP_X + 92, 62 + i * UI_ICON_ITEM_STEP_Y, this.currentForgeArm[i][j + this.forgeBoxStartIndex[i]]);
                                    this.drawUiModule(116, 47 + j * UI_ICON_ITEM_STEP_X + 92, 82 + i * UI_ICON_ITEM_STEP_Y, 3, 0);
                                }
                            }
                            if (i * 9 + j == this.forgeBoxSeletedIndex) {
                                this.drawitemiconcheck(28 + j * UI_ICON_ITEM_STEP_X + 92, 62 + i * UI_ICON_ITEM_STEP_Y, this.currentForgeArm[i][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[i]]);
                                continue;
                            }
                            this.drawitemiconmembrane(28 + j * UI_ICON_ITEM_STEP_X + 92, 62 + i * UI_ICON_ITEM_STEP_Y, this.currentForgeArm[i][j + this.forgeBoxStartIndex[i]]);
                        }
                    }
                    this.drawjiantou(0, 4, 96, 73 + 1 * UI_ICON_ITEM_STEP_Y, 20);
                    this.drawjiantou(0, 5, 528, 73 + 1 * UI_ICON_ITEM_STEP_Y, 20);
                    if (this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]] > -1) {
                        this.drawforgedata1(this.forgeBoxIndex, this.currentForgeArmName);
                        this.drawForgeCondition1();
                        switch (this.forgeBoxIndex) {
                            case 0: 
                            case 1: {
                                this.loadweaponname = this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]];
                                this.loadWeaponImages();
                                this.drawWeapon(320, 240, this.canvas.rolem.setWeaponType(this.loadweaponname));
                                break;
                            }
                            case 2: {
                                this.drawUiModule(0, 301, 255, 20, 0);
                                this.drawarmicon(this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]], 302, 254);
                                this.drawitemiconcheck(302, 254, this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]]);
                            }
                        }
                        String itemNameTemp = this.iteminfo[this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]]][0];
                        int nameinfooffsety = -45;
                        this.drawadornline(320 + ((itemNameTemp.length() <= 2 ? -4 : 4) - ((itemNameTemp.length() <= 2 ? itemNameTemp.length() : itemNameTemp.length()) * 17 >> 1)), 360 + nameinfooffsety - 17, itemNameTemp.length(), this.forgeBoxIndex);
                        int itemIndexTemp = -1;
                        if (this.forgeBoxSeletedIndex / 9 == 0) {
                            itemIndexTemp = this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]] + 291 - 44;
                        } else if (this.forgeBoxSeletedIndex / 9 == 1) {
                            itemIndexTemp = this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]] + 306 - 59;
                        } else if (this.forgeBoxSeletedIndex / 9 == 2) {
                            itemIndexTemp = this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]] + 335 - 88;
                        }
                        if (itemIndexTemp > -1) {
                            this.drawUiModule(itemIndexTemp, 320 + (itemNameTemp.length() <= 2 ? -4 : 4) - (itemNameTemp.length() * 16 >> 1), 360 + nameinfooffsety, 36, 0);
                        }
                    }
                    if (!this.isCommand) break;
                    this.drawUiBack();
                    switch (this.commandState) {
                        case 0: {
                            this.drawStaticUi((short)19);
                            this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                            break;
                        }
                        case 1: {
                            this.drawStaticUi((short)19);
                            int tempy = 0;
                            if (this.commandStrs.length > 1) {
                                tempy = (this.commandStrs.length - 1) * (20 / this.commandStrs.length);
                            }
                            this.canvas.drawZkStringArry(this.commandStrs, 320, 133 - tempy, 17, 0, this.commandStrs.length, 0xFFFFFF);
                            break;
                        }
                        case 2: {
                            this.drawForgeSprite((byte)0, 320, 180);
                            switch (this.forgeBoxIndex) {
                                case 0: {
                                    this.canvas.drawImage(this.weaponImage, 292, 122, 20, 6, this.jianModule, 0);
                                    break;
                                }
                                case 1: {
                                    this.canvas.drawImage(this.weaponImage, 312, 148, 20, 6, this.quanModule, 0);
                                }
                            }
                            this.drawLight();
                            this.lightUpDate();
                            this.drawStaticUi((short)19);
                            this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                            break;
                        }
                        case 3: {
                            this.drawStaticUi((short)126);
                            for (int i = 0; i < this.forgeBoxCommand.length; i = (int)((byte)(i + 1))) {
                                if (i == this.commandSeletedIndex) {
                                    this.drawChoiceBox(320, 125 + this.commandSeletedIndex * 45);
                                    this.canvas.drawColorString(this.forgeBoxCommand[i], 320, 128 + this.commandSeletedIndex * 45, 17, 15597319, 0);
                                    continue;
                                }
                                this.canvas.drawColorString(this.forgeBoxCommand[i], 320, 128 + i * 45, 17, 0xFFFFFF, 0);
                            }
                            break block0;
                        }
                        case 4: {
                            this.drawStaticUi((short)26);
                            this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                            break;
                        }
                        case 5: {
                            if (this.forgeActionFrameIndex / 2 == 19) {
                                this.createWeaponImage(this.currentForgeArmName);
                            }
                            this.drawForgeSprite((byte)0, 320, 180);
                            if (this.forgeActionFrameIndex / 2 >= 24) {
                                switch (this.forgeBoxIndex) {
                                    case 0: {
                                        this.canvas.drawImage(this.weaponImage, 292, 122, 20, 6, this.jianModule, 0);
                                        break;
                                    }
                                    case 1: {
                                        this.canvas.drawImage(this.weaponImage, 312, 148, 20, 6, this.quanModule, 0);
                                    }
                                }
                                this.drawLight();
                            }
                            this.lightUpDate();
                            if (this.forgeActionOver) {
                                this.canvas.drawColorString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 320, 340, 33, 0xFFFFFF, 0);
                                break;
                            }
                            break block73;
                        }
                        case 6: {
                            this.changeWeaponImage(this.currentForgeArmName);
                            this.drawminiloading(this.changeWeaponTime, 3);
                            if (this.changeWeaponTime == 3) {
                                if (this.lastCommandState == 2) {
                                    this.commandStr = "\u88c5\u5907\u6210\u529f\u3002";
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)4);
                                    break;
                                }
                                if (this.lastCommandState == 5) {
                                    this.commandStr = "\u953b\u9020\u6210\u529f\uff01\u5df2\u88c5\u5907\u6b66\u5668";
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)4);
                                    break;
                                }
                            }
                            break block73;
                        }
                        case 7: {
                            this.drawStaticUi((short)19);
                            this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                            break;
                        }
                        case 8: {
                            this.drawStaticUi((short)26);
                            this.drawlaohuji(this.currentForgeArmName);
                        }
                    }
                    break;
                }
                case 3: {
                    break;
                }
                case 4: {
                    this.drawStaticUi(this.forgeShopIndex);
                    this.drawlabels(this.armboxdata, this.forgeShopIndex);
                    this.drawStaticUi((short)112);
                    this.drawStaticUi((short)44);
                    this.drawStaticUi((short)16, 0, 360);
                    if (this.isoffsetactionover) {
                        this.drawarmshopdata(this.forgeShopIndex);
                    } else {
                        for (int j = 0; j < 3; ++j) {
                            this.drawarmshopdata(j);
                        }
                    }
                    this.scroll.drawscroll();
                    this.offsetactionprocess(this.forgeShopIndex);
                    if (!this.isCommand) break;
                    this.drawUiBack();
                    switch (this.commandState) {
                        case 0: {
                            this.drawStaticUi((short)19);
                            this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                            break;
                        }
                        case 1: {
                            this.drawStaticUi((short)26);
                            this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                            break;
                        }
                        case 2: {
                            this.drawStaticUi((short)19);
                            this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                        }
                    }
                    break;
                }
                case 5: {
                    this.drawStaticUi((short)(this.materialShopIndex + 92));
                    this.drawlabels(this.materialsaledata, this.materialShopIndex);
                    this.drawStaticUi((short)112);
                    this.drawStaticUi((short)44);
                    this.drawStaticUi((short)16, 0, 360);
                    if (this.isoffsetactionover) {
                        this.drawmaterialshopdata(this.materialShopIndex);
                    } else {
                        for (int j = 0; j < 2; ++j) {
                            this.drawmaterialshopdata(j);
                        }
                    }
                    this.scroll.drawscroll();
                    this.offsetactionprocess(this.materialShopIndex);
                    if (!this.isCommand) break;
                    this.drawUiBack();
                    switch (this.commandState) {
                        case 0: {
                            this.drawStaticUi((short)19);
                            this.drawjiantou(0, 0, 375, 132, 33);
                            this.drawjiantou(0, 1, 375, 150, 17);
                            this.canvas.drawColorString("\u9009\u62e9\u4e70\u5165\u6570\u91cf\uff1a" + this.needNum, 320, 133, 17, 0xFFFFFF, 0);
                            break block0;
                        }
                        case 1: {
                            this.drawStaticUi((short)19);
                            this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                            break block0;
                        }
                        case 2: {
                            this.drawStaticUi((short)19);
                            this.drawjiantou(0, 0, 375, 132, 33);
                            this.drawjiantou(0, 1, 375, 150, 17);
                            this.canvas.drawColorString("\u9009\u62e9\u5356\u51fa\u6570\u91cf\uff1a" + this.needNum, 320, 133, 17, 0xFFFFFF, 0);
                            break block0;
                        }
                        case 3: {
                            this.drawStaticUi((short)19);
                            this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                            break block0;
                        }
                        case 4: {
                            this.drawStaticUi((short)26);
                            this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                        }
                    }
                }
            }
        }
    }

    private void initlaohuji(byte name) {
        this.laohujiframeindex = 0;
        this.laohujitime = 0;
        this.laohujistate = 0;
        switch (this.plusdatastr[this.plusData[name - 88]].length()) {
            case 4: {
                this.laohujix = 120;
                break;
            }
            case 6: {
                this.laohujix = 135;
                break;
            }
            case 8: {
                this.laohujix = 150;
            }
        }
    }

    private void drawlaohuji(byte name) {
        this.canvas.drawZkString("$1" + this.plusdatastr[this.plusData[name - 88]] + "\uff1a", this.laohujix - 10 + 210, 133, 24, this.canvas.zkData);
        switch (this.laohujitype) {
            case 0: {
                switch (this.laohujistate) {
                    case 0: {
                        for (int i = 0; i < 2; i = (int)((byte)(i + 1))) {
                            this.drawUiModule(this.laohujiframe[this.laohujiframeindex], this.laohujix + i * 25 + 210, 138, 3, 0);
                        }
                        this.laohujiframeindex = (byte)(this.laohujiframeindex + 1);
                        if (this.laohujiframeindex >= this.laohujiframe.length) {
                            this.laohujiframeindex = 0;
                        }
                        this.canvas.drawColorString("\u70b9\u51fb\u5c4f\u5e55\u505c\u6b62", 320, 193, 17, 15597319, 0);
                        break;
                    }
                    case 1: {
                        this.drawUiModule(this.laohujiframe[this.laohujiframeindex], this.laohujix + 210, 138, 3, 0);
                        this.drawUiModule(207, this.laohujix + 25 + 210, 138, 3, 0);
                        this.drawUiModule(this.tempplusdata % 10 + 181, this.laohujix + 25 + 210, 138 + this.laohujioffsety[this.laohujioffsetindex], 3, 0);
                        this.laohujiframeindex = (byte)(this.laohujiframeindex + 1);
                        if (this.laohujiframeindex >= this.laohujiframe.length) {
                            this.laohujiframeindex = 0;
                        }
                        this.laohujioffsetindex = (byte)((this.laohujioffsetindex = (byte)(this.laohujioffsetindex + 1)) > 3 ? 3 : (int)this.laohujioffsetindex);
                        this.laohujitime = (byte)(this.laohujitime + 1);
                        if (this.laohujitime == 10) {
                            this.laohujistate = (byte)2;
                            this.laohujioffsetindex = 0;
                        }
                        this.canvas.drawColorString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 320, 193, 17, 15597319, 0);
                        break;
                    }
                    case 2: {
                        this.drawUiModule(207, this.laohujix + 210, 138, 3, 0);
                        this.drawUiModule(this.tempplusdata / 10 + 181, this.laohujix + 210, 138 + this.laohujioffsety[this.laohujioffsetindex], 3, 0);
                        this.drawUiModule(207, this.laohujix + 25 + 210, 138, 3, 0);
                        this.drawUiModule(this.tempplusdata % 10 + 181, this.laohujix + 25 + 210, 138, 3, 0);
                        this.laohujiframeindex = (byte)(this.laohujiframeindex + 1);
                        if (this.laohujiframeindex >= this.laohujiframe.length) {
                            this.laohujiframeindex = 0;
                        }
                        this.laohujioffsetindex = (byte)((this.laohujioffsetindex = (byte)(this.laohujioffsetindex + 1)) > 3 ? 3 : (int)this.laohujioffsetindex);
                        this.canvas.drawColorString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 320, 193, 17, 15597319, 0);
                    }
                }
                this.drawUiModule(191, this.laohujix + 50 + 210, 138, 3, 0);
                break;
            }
            case 1: {
                if (name == 123) {
                    this.drawUiModule(182, this.laohujix + 210, 138, 3, 0);
                    this.drawUiModule(181, this.laohujix + 25 + 210, 138, 3, 0);
                    this.canvas.drawZkString("$1\u79d2", this.laohujix + 50 + 210, 133, 20, this.canvas.zkData);
                } else {
                    this.drawUiModule(this.tempplusdata / 10 + 181, this.laohujix + 210, 138, 3, 0);
                    this.drawUiModule(this.tempplusdata % 10 + 181, this.laohujix + 25 + 210, 138, 3, 0);
                    this.drawUiModule(191, this.laohujix + 50 + 210, 138, 3, 0);
                }
                this.canvas.drawColorString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 320, 193, 17, 15597319, 0);
            }
        }
    }

    private void laohujikeyprocess(byte name) {
        switch (this.laohujitype) {
            case 0: {
                switch (this.laohujistate) {
                    case 0: {
                        this.laohujistate = 1;
                        this.laohujioffsetindex = 0;
                        this.laohujitime = 0;
                        break;
                    }
                    case 2: {
                        this.commandStr = "\u953b\u9020\u6210\u529f\uff0c\u6b66\u5668\u662f\u5426\u88c5\u5907\uff1f";
                        this.changeCommandState((byte)7);
                    }
                }
                break;
            }
            case 1: {
                this.commandStr = "\u953b\u9020\u6210\u529f\uff0c\u6b66\u5668\u662f\u5426\u88c5\u5907\uff1f";
                this.changeCommandState((byte)7);
            }
        }
    }

    public void initForgeBoxStartIndex() {
        for (int i = 0; i < this.forgeBoxStartIndex.length; ++i) {
            this.forgeBoxStartIndex[i] = 0;
        }
    }

    public void initforgebox() {
        this.forgeBoxState = 0;
        this.forgeBoxIndex = 0;
        this.forgeBoxSeletedIndex = 0;
        this.initForgeBoxStartIndex();
        this.factoryIndex = 0;
        this.setforgebossdata(this.canvas.mapm.cityMapIndex);
    }

    private void forgeBoxKeyProcess() {
        this.loadWeaponImages();
        switch (this.forgeBoxState) {
            case 0: {
                if (Control.IsKeyDown(4100)) {
                    this.factoryIndex = (byte)(this.factoryIndex - 1);
                    this.factoryIndex = (byte)(this.factoryIndex < 0 ? 2 : (int)this.factoryIndex);
                    break;
                }
                if (Control.IsKeyDown(8448)) {
                    this.factoryIndex = (byte)(this.factoryIndex + 1);
                    this.factoryIndex = this.factoryIndex > 2 ? (byte)0 : this.factoryIndex;
                    break;
                }
                if (Control.IsKeyDown(327712)) {
                    this.checkforgeboxshownews(this.canvas.mapm.cityMapIndex, this.factoryIndex);
                    block8 : switch (this.factoryIndex) {
                        case 0: {
                            this.forgeBoxIndex = 0;
                            this.forgeBoxState = 1;
                            this.initforgearmdata();
                            this.forgeBoxSeletedIndex = 0;
                            this.initForgeBoxStartIndex();
                            this.forgeBoxState = (byte)2;
                            for (int i = 0; i < 3; ++i) {
                                this.checkforgeboxshownews(this.canvas.mapm.cityMapIndex, (byte)(this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[i]));
                            }
                            this.currentForgeArmName = this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]];
                            this.setForgeCondition(this.currentForgeArmName, (byte)1);
                            if (this.forgeBoxIndex == 2) {
                                this.setplusdatastr(this.currentForgeArmName);
                            }
                            if (this.forgeBoxIndex != 0 && this.forgeBoxIndex != 1) break;
                            this.showweapony = 0;
                            this.showweaponvdir = 1;
                            this.loadweaponimagetime = 0;
                            this.loadweaponname = this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]];
                            break;
                        }
                        case 1: {
                            this.forgeShopIndex = 0;
                            this.forgeBoxState = (byte)4;
                            this.armSaleSeletedIndex = 0;
                            this.armSaleStartIndex = 0;
                            this.initoffset();
                            switch (this.forgeShopIndex) {
                                case 0: {
                                    this.forgeSaleWeaponUseTotal = this.weaponPackUseNum;
                                    this.setItemInfo(this.weaponPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]);
                                    break;
                                }
                                case 1: {
                                    this.forgeSaleSuitUseTotal = this.suitPackUseNum;
                                    this.setItemInfo(this.suitPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]);
                                    break;
                                }
                                case 2: {
                                    this.forgeSalePlusUseTotal = this.plusPackUseNum;
                                    this.setItemInfo(this.plusPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]);
                                }
                            }
                            break;
                        }
                        case 2: {
                            this.materialShopIndex = 0;
                            this.forgeBoxState = (byte)5;
                            this.initoffset();
                            switch (this.materialShopIndex) {
                                case 0: {
                                    this.initmaterialdata();
                                    this.materialBuySeletedIndex = 0;
                                    this.materialBuyStartIndex = 0;
                                    this.materialBuyUseTotal = (byte)this.currentSaleMaterial.length;
                                    this.setItemInfo(this.currentSaleMaterial[this.materialBuySeletedIndex + this.materialBuyStartIndex]);
                                    break block8;
                                }
                                case 1: {
                                    this.materialSaleSeletedIndex = 0;
                                    this.materialSaleStartIndex = 0;
                                    this.materialSaleUseTotal = this.itemPackUseNum;
                                    this.setItemInfo(this.itemPack[0][this.materialSaleSeletedIndex + this.materialSaleStartIndex]);
                                }
                            }
                        }
                    }
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                this.canvas.mapm.iscityflushinfo = true;
                this.changeFightUiState((byte)3);
                this.forgeBossImage = null;
                this.canvas.changeGameState(4);
                break;
            }
            case 1: {
                if (Control.IsKeyDown(327712)) {
                    this.forgeBoxSeletedIndex = 0;
                    this.initForgeBoxStartIndex();
                    this.forgeBoxState = (byte)2;
                    for (int i = 0; i < 3; ++i) {
                        this.checkforgeboxshownews(this.canvas.mapm.cityMapIndex, (byte)(this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[i]));
                    }
                    this.currentForgeArmName = this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]];
                    this.setForgeCondition(this.currentForgeArmName, (byte)1);
                    if (this.forgeBoxIndex == 2) {
                        this.setplusdatastr(this.currentForgeArmName);
                    }
                    this.changeActionUiState((byte)14, (byte)7, (byte)0);
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                this.forgeBoxState = 0;
                this.setforgebossdata(this.canvas.mapm.cityMapIndex);
                break;
            }
            case 2: {
                if (Control.IsKeyDown(4100) || Control.IsKeyHold(4100, 10)) {
                    if (this.isCommand || this.forgeBoxSeletedIndex / 9 <= 0) break;
                    this.forgeBoxSeletedIndex = (byte)(this.forgeBoxSeletedIndex - 9);
                    this.forgeBoxIndex = (byte)(this.forgeBoxSeletedIndex / 9);
                    if (this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]] <= -1) {
                        this.forgeBoxSeletedIndex = (byte)(this.forgeBoxSeletedIndex - this.forgeBoxSeletedIndex % 9);
                    }
                    if (this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]] <= -1) break;
                    if (this.forgeBoxIndex == 0 || this.forgeBoxIndex == 1) {
                        this.showweapony = 0;
                        this.showweaponvdir = 1;
                        this.loadweaponimagetime = 0;
                        this.loadweaponname = this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]];
                    }
                    this.checkforgeboxshownews(this.canvas.mapm.cityMapIndex, (byte)(this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]));
                    this.currentForgeArmName = this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]];
                    this.setForgeCondition(this.currentForgeArmName, (byte)1);
                    if (this.forgeBoxIndex != 2) break;
                    this.setplusdatastr(this.currentForgeArmName);
                    break;
                }
                if (Control.IsKeyDown(8448) || Control.IsKeyHold(8448, 10)) {
                    if (this.isCommand || this.forgeBoxSeletedIndex / 9 >= 2) break;
                    this.forgeBoxSeletedIndex = (byte)(this.forgeBoxSeletedIndex + 9);
                    this.forgeBoxIndex = (byte)(this.forgeBoxSeletedIndex / 9);
                    if (this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]] <= -1) {
                        this.forgeBoxSeletedIndex = (byte)(this.forgeBoxSeletedIndex - this.forgeBoxSeletedIndex % 9);
                    }
                    if (this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]] <= -1) break;
                    if (this.forgeBoxIndex == 0 || this.forgeBoxIndex == 1) {
                        this.showweapony = 0;
                        this.showweaponvdir = 1;
                        this.loadweaponimagetime = 0;
                        this.loadweaponname = this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]];
                    }
                    this.checkforgeboxshownews(this.canvas.mapm.cityMapIndex, (byte)(this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]));
                    this.currentForgeArmName = this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]];
                    this.setForgeCondition(this.currentForgeArmName, (byte)1);
                    if (this.forgeBoxIndex != 2) break;
                    this.setplusdatastr(this.currentForgeArmName);
                    break;
                }
                if (Control.IsKeyDown(16400) || Control.IsKeyHold(16400, 10)) {
                    if (this.isCommand) break;
                    byte tempforgeBoxSeletedIndex = this.forgeBoxSeletedIndex;
                    if (this.forgeBoxSeletedIndex % 9 == 0) {
                        if (this.forgeBoxStartIndex[this.forgeBoxIndex] > 0) {
                            byte by = this.forgeBoxIndex;
                            this.forgeBoxStartIndex[by] = (byte)(this.forgeBoxStartIndex[by] - 1);
                        } else {
                            this.forgeBoxSeletedIndex = (byte)(this.forgeBoxSeletedIndex + 8);
                        }
                    } else {
                        this.forgeBoxSeletedIndex = (byte)(this.forgeBoxSeletedIndex - 1);
                    }
                    if (this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]] > -1) {
                        if (this.forgeBoxIndex == 0 || this.forgeBoxIndex == 1) {
                            this.showweapony = 0;
                            this.showweaponvdir = 1;
                            this.loadweaponimagetime = 0;
                            this.loadweaponname = this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]];
                        }
                        this.checkforgeboxshownews(this.canvas.mapm.cityMapIndex, (byte)(this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]));
                        this.currentForgeArmName = this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]];
                        this.setForgeCondition(this.currentForgeArmName, (byte)1);
                        if (this.forgeBoxIndex != 2) break;
                        this.setplusdatastr(this.currentForgeArmName);
                        break;
                    }
                    this.forgeBoxSeletedIndex = tempforgeBoxSeletedIndex;
                    break;
                }
                if (Control.IsKeyDown(32832) || Control.IsKeyHold(32832, 10)) {
                    if (this.isCommand) break;
                    byte tempforgeBoxSeletedIndex = this.forgeBoxSeletedIndex;
                    if (this.forgeBoxSeletedIndex % 9 == 8) {
                        if (this.forgeBoxStartIndex[this.forgeBoxIndex] < 3) {
                            byte by = this.forgeBoxIndex;
                            this.forgeBoxStartIndex[by] = (byte)(this.forgeBoxStartIndex[by] + 1);
                        } else {
                            this.forgeBoxSeletedIndex = (byte)(this.forgeBoxSeletedIndex - 8);
                        }
                    } else {
                        this.forgeBoxSeletedIndex = (byte)(this.forgeBoxSeletedIndex + 1);
                    }
                    if (this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]] > -1) {
                        if (this.forgeBoxIndex == 0 || this.forgeBoxIndex == 1) {
                            this.showweapony = 0;
                            this.showweaponvdir = 1;
                            this.loadweaponimagetime = 0;
                            this.loadweaponname = this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]];
                        }
                        this.checkforgeboxshownews(this.canvas.mapm.cityMapIndex, (byte)(this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]));
                        this.currentForgeArmName = this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]];
                        this.setForgeCondition(this.currentForgeArmName, (byte)1);
                        if (this.forgeBoxIndex != 2) break;
                        this.setplusdatastr(this.currentForgeArmName);
                        break;
                    }
                    this.forgeBoxSeletedIndex = tempforgeBoxSeletedIndex;
                    break;
                }
                if (Control.IsKeyDown(327712)) {
                    if (this.isCommand) {
                        switch (this.commandState) {
                            case 9: {
                                this.finishCommand();
                                break;
                            }
                            case 0: {
                                if (this.forgeBoxIndex == 2) {
                                    this.forgeArm(this.currentForgeArmName, this.needMoney);
                                    this.setForgeCondition(this.currentForgeArmName, (byte)1);
                                    this.initlaohuji(this.currentForgeArmName);
                                    this.changeCommandState((byte)8);
                                    break;
                                }
                                this.initLight();
                                this.changeWeaponTime = 0;
                                this.forgeActionFrameIndex = 0;
                                this.forgeFrameDelay = 0;
                                this.forgeActionOver = false;
                                this.changeCommandState((byte)5);
                                break;
                            }
                            case 1: {
                                if (this.playerMoney < this.needMoney) {
                                    this.canvas.sms.setshowsmsshop(0);
                                    this.commandStr = "\u91d1\u94b1\u4e0d\u8db3";
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)4);
                                    break;
                                }
                                if (this.forgeBoxIndex == 2) {
                                    this.forgeArm(this.currentForgeArmName, this.needMoney);
                                    this.setForgeCondition(this.currentForgeArmName, (byte)1);
                                    this.initlaohuji(this.currentForgeArmName);
                                    this.changeCommandState((byte)8);
                                    break;
                                }
                                this.initLight();
                                this.changeWeaponTime = 0;
                                this.forgeActionFrameIndex = 0;
                                this.forgeFrameDelay = 0;
                                this.forgeActionOver = false;
                                this.changeCommandState((byte)5);
                                break;
                            }
                            case 2: {
                                switch (this.forgeBoxIndex) {
                                    case 0: 
                                    case 1: {
                                        this.changeWeapon(this.changeArmIndex);
                                        this.commandStr = "\u88c5\u5907\u6210\u529f";
                                        this.light = null;
                                        this.setCommandShowTime();
                                        this.changeCommandState((byte)6);
                                        break;
                                    }
                                    case 2: {
                                        this.commandSeletedIndex = 0;
                                        this.changeCommandState((byte)3);
                                    }
                                }
                                break;
                            }
                            case 3: {
                                if (this.commandSeletedIndex < 2) {
                                    if (this.isPutOnPlus(this.commandSeletedIndex)) {
                                        this.changePlus(this.commandSeletedIndex, this.changeArmIndex);
                                    } else {
                                        this.putOnPlus(this.commandSeletedIndex, this.changeArmIndex);
                                    }
                                    this.commandStr = "\u88c5\u5907\u6210\u529f";
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)4);
                                    break;
                                }
                                this.finishCommand();
                                break;
                            }
                            case 4: {
                                this.finishCommand();
                                break;
                            }
                            case 5: {
                                if (!this.forgeActionOver) break;
                                this.commandStr = "\u953b\u9020\u6210\u529f\uff0c\u6b66\u5668\u662f\u5426\u88c5\u5907\uff1f";
                                this.changeCommandState((byte)2);
                                break;
                            }
                            case 7: {
                                this.commandSeletedIndex = 0;
                                this.changeCommandState((byte)3);
                                break;
                            }
                            case 8: {
                                this.laohujikeyprocess(this.currentForgeArmName);
                            }
                        }
                        break;
                    }
                    this.currentForgeArmName = this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]];
                    if (this.currentForgeArmName <= -1) break;
                    if (this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]] > -1 && this.forgeState[this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]] - 44] == 1) {
                        this.commandStr = this.needBook[this.currentForgeArm[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]] - 44] > -1 ? "\u953b\u9020\u6b64\u88c5\u5907\u9700\u8981\u953b\u9020\u4e66" : "\u953b\u9020\u6b64\u88c5\u5907\u9700\u8981 " + (this.currentForgeArmlevel[this.forgeBoxIndex][this.forgeBoxSeletedIndex % 9 + this.forgeBoxStartIndex[this.forgeBoxIndex]] + 1) + " \u7ea7\u57ce\u5e02";
                        this.startCommand();
                        this.setCommandShowTime();
                        this.changeCommandState((byte)4);
                        return;
                    }
                    if (!this.checkArmBox(this.currentForgeArmName)) {
                        this.commandStr = "\u80cc\u5305\u5df2\u6ee1\uff0c\u53ef\u5728\u5546\u5e97\u6269\u5145\u80cc\u5305";
                        this.isShowKuoBaoTishi = true;
                        this.setCommandShowTime();
                        this.changeCommandState((byte)4);
                        this.startCommand();
                        return;
                    }
                    if (!this.checkWeapon(this.currentForgeArmName)) {
                        this.setCommandShowTime();
                        this.changeCommandState((byte)4);
                        this.startCommand();
                        return;
                    }
                    this.needMoney = this.canvas.rolem.itemBusinessData[this.currentForgeArmName][0];
                    if (!this.checkMaterial(this.currentForgeArmName)) {
                        this.needMoney += this.jisuanchajia(this.currentForgeArmName);
                        this.commandStrs = this.canvas.formatzkword("\u6750\u6599\u4e0d\u8db3\uff0c\u8d2d\u4e70\u6750\u6599\u548c\u953b\u9020\u6b66\u5668\u5171\u9700\u82b1\u8d39 " + this.needMoney + " \u91d1\u94b1\uff0c\u662f\u5426\u953b\u9020\uff1f", 200, 0);
                        this.changeCommandState((byte)1);
                        this.startCommand();
                        break;
                    }
                    if (!this.checkMoney(this.currentForgeArmName)) {
                        this.canvas.sms.setshowsmsshop(0);
                        this.commandStr = "\u91d1\u94b1\u4e0d\u8db3";
                        this.setCommandShowTime();
                        this.changeCommandState((byte)4);
                        this.startCommand();
                        break;
                    }
                    this.changeCommandState((byte)0);
                    this.commandStr = "\u662f\u5426\u953b\u9020\uff1f";
                    this.startCommand();
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                if (this.isCommand) {
                    this.finishCommand();
                    break;
                }
                this.forgeBoxState = 0;
                this.setforgebossdata(this.canvas.mapm.cityMapIndex);
                break;
            }
            case 3: {
                break;
            }
            case 4: {
                if (Control.IsKeyDown(16400)) {
                    if (this.isCommand || this.forgeShopIndex <= 0) break;
                    this.forgeShopIndex = (byte)(this.forgeShopIndex - 1);
                    this.forgeShopIndex = this.forgeShopIndex < 0 ? (byte)0 : this.forgeShopIndex;
                    this.isoffsetactionover = false;
                    this.armSaleSeletedIndex = 0;
                    this.armSaleStartIndex = 0;
                    switch (this.forgeShopIndex) {
                        case 0: {
                            this.forgeSaleWeaponUseTotal = this.weaponPackUseNum;
                            this.setItemInfo(this.weaponPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]);
                            break;
                        }
                        case 1: {
                            this.forgeSaleSuitUseTotal = this.suitPackUseNum;
                            this.setItemInfo(this.suitPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]);
                            break;
                        }
                        case 2: {
                            this.forgeSalePlusUseTotal = this.plusPackUseNum;
                            this.setItemInfo(this.plusPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]);
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(32832)) {
                    if (this.isCommand || this.forgeShopIndex >= 2) break;
                    this.forgeShopIndex = (byte)(this.forgeShopIndex + 1);
                    this.forgeShopIndex = (byte)(this.forgeShopIndex > 2 ? 2 : (int)this.forgeShopIndex);
                    this.isoffsetactionover = false;
                    this.armSaleSeletedIndex = 0;
                    this.armSaleStartIndex = 0;
                    switch (this.forgeShopIndex) {
                        case 0: {
                            this.forgeSaleWeaponUseTotal = this.weaponPackUseNum;
                            this.setItemInfo(this.weaponPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]);
                            break;
                        }
                        case 1: {
                            this.forgeSaleSuitUseTotal = this.suitPackUseNum;
                            this.setItemInfo(this.suitPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]);
                            break;
                        }
                        case 2: {
                            this.forgeSalePlusUseTotal = this.plusPackUseNum;
                            this.setItemInfo(this.plusPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]);
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(4100) || Control.IsKeyHold(4100, 10)) {
                    if (this.isCommand) break;
                    if (this.armSaleSeletedIndex > 0) {
                        this.armSaleSeletedIndex = (byte)(this.armSaleSeletedIndex - 1);
                    } else if (this.armSaleStartIndex > 0) {
                        this.armSaleStartIndex = (byte)(this.armSaleStartIndex - 1);
                    }
                    switch (this.forgeShopIndex) {
                        case 0: {
                            this.setItemInfo(this.weaponPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]);
                            break;
                        }
                        case 1: {
                            this.setItemInfo(this.suitPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]);
                            break;
                        }
                        case 2: {
                            this.setItemInfo(this.plusPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]);
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(8448) || Control.IsKeyHold(8448, 10)) {
                    if (this.isCommand) break;
                    switch (this.forgeShopIndex) {
                        case 0: {
                            this.totalamount = (byte)(this.forgeSaleWeaponUseTotal < 5 ? (int)this.forgeSaleWeaponUseTotal : 5);
                            if (this.armSaleSeletedIndex < this.totalamount - 1) {
                                this.armSaleSeletedIndex = (byte)(this.armSaleSeletedIndex + 1);
                            } else if (this.armSaleStartIndex < this.forgeSaleWeaponUseTotal - this.totalamount) {
                                this.armSaleStartIndex = (byte)(this.armSaleStartIndex + 1);
                            }
                            this.setItemInfo(this.weaponPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]);
                            break;
                        }
                        case 1: {
                            this.totalamount = (byte)(this.forgeSaleSuitUseTotal < 5 ? (int)this.forgeSaleSuitUseTotal : 5);
                            if (this.armSaleSeletedIndex < this.totalamount - 1) {
                                this.armSaleSeletedIndex = (byte)(this.armSaleSeletedIndex + 1);
                            } else if (this.armSaleStartIndex < this.forgeSaleSuitUseTotal - this.totalamount) {
                                this.armSaleStartIndex = (byte)(this.armSaleStartIndex + 1);
                            }
                            this.setItemInfo(this.suitPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]);
                            break;
                        }
                        case 2: {
                            this.totalamount = (byte)(this.forgeSalePlusUseTotal < 5 ? (int)this.forgeSalePlusUseTotal : 5);
                            if (this.armSaleSeletedIndex < this.totalamount - 1) {
                                this.armSaleSeletedIndex = (byte)(this.armSaleSeletedIndex + 1);
                            } else if (this.armSaleStartIndex < this.forgeSalePlusUseTotal - this.totalamount) {
                                this.armSaleStartIndex = (byte)(this.armSaleStartIndex + 1);
                            }
                            this.setItemInfo(this.plusPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]);
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(327712)) {
                    if (this.isCommand) {
                        switch (this.commandState) {
                            case 0: {
                                switch (this.forgeShopIndex) {
                                    case 0: {
                                        this.subtractWeapon(this.armSaleSeletedIndex + this.armSaleStartIndex);
                                        this.addMoney(this.currentSaleItemMoney);
                                        if (this.addState == -2) {
                                            if (this.armSaleStartIndex > 0) {
                                                this.armSaleStartIndex = (byte)(this.armSaleStartIndex - 1);
                                            } else if (this.armSaleSeletedIndex > 0) {
                                                this.armSaleSeletedIndex = (byte)(this.armSaleSeletedIndex - 1);
                                            }
                                            this.forgeSaleWeaponUseTotal = this.weaponPackUseNum;
                                            this.setItemInfo(this.weaponPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]);
                                        }
                                        this.setItemInfo(this.weaponPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]);
                                        this.changeCommandState((byte)1);
                                        this.setCommandShowTime();
                                        this.commandStr = "\u51fa\u552e\u6210\u529f";
                                        break;
                                    }
                                    case 1: {
                                        this.changeCommandState((byte)2);
                                        this.commandStr = "\u6b64\u88c5\u5907\u65e0\u6cd5\u518d\u6b21\u83b7\u5f97\uff0c\u786e\u5b9a\u51fa\u552e\u5417\uff1f";
                                        break;
                                    }
                                    case 2: {
                                        this.subtractPlus(this.armSaleSeletedIndex + this.armSaleStartIndex);
                                        this.addMoney(this.currentSaleItemMoney);
                                        if (this.addState == -2) {
                                            if (this.armSaleStartIndex > 0) {
                                                this.armSaleStartIndex = (byte)(this.armSaleStartIndex - 1);
                                            } else if (this.armSaleSeletedIndex > 0) {
                                                this.armSaleSeletedIndex = (byte)(this.armSaleSeletedIndex - 1);
                                            }
                                            this.forgeSalePlusUseTotal = this.plusPackUseNum;
                                            this.setItemInfo(this.plusPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]);
                                        }
                                        this.changeCommandState((byte)1);
                                        this.setCommandShowTime();
                                        this.commandStr = "\u51fa\u552e\u6210\u529f";
                                    }
                                }
                                break;
                            }
                            case 1: {
                                this.finishCommand();
                                break;
                            }
                            case 2: {
                                this.subtractSuit(this.armSaleSeletedIndex + this.armSaleStartIndex);
                                this.addMoney(this.currentSaleItemMoney);
                                if (this.addState == -2) {
                                    if (this.armSaleStartIndex > 0) {
                                        this.armSaleStartIndex = (byte)(this.armSaleStartIndex - 1);
                                    } else if (this.armSaleSeletedIndex > 0) {
                                        this.armSaleSeletedIndex = (byte)(this.armSaleSeletedIndex - 1);
                                    }
                                    this.forgeSaleSuitUseTotal = this.suitPackUseNum;
                                    this.setItemInfo(this.suitPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]);
                                }
                                this.changeCommandState((byte)1);
                                this.setCommandShowTime();
                                this.commandStr = "\u51fa\u552e\u6210\u529f";
                            }
                        }
                        break;
                    }
                    switch (this.forgeShopIndex) {
                        case 0: {
                            if (this.weaponPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex] <= -1) break;
                            if (this.weaponPack[1][this.armSaleSeletedIndex + this.armSaleStartIndex] > -1) {
                                this.changeCommandState((byte)1);
                                this.setCommandShowTime();
                                this.commandStr = "\u5df2\u88c5\u5907\uff0c\u65e0\u6cd5\u51fa\u552e";
                                this.startCommand();
                                break;
                            }
                            this.currentSaleItemMoney = this.canvas.rolem.itemBusinessData[this.weaponPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]][1];
                            this.changeCommandState((byte)0);
                            this.commandStr = "\u91d1\u989d\u4e3a " + this.currentSaleItemMoney + " \u786e\u5b9a\u51fa\u552e\u5417\uff1f";
                            this.startCommand();
                            break;
                        }
                        case 1: {
                            if (this.suitPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex] <= -1) break;
                            if (this.suitPack[1][this.armSaleSeletedIndex + this.armSaleStartIndex] > -1) {
                                this.changeCommandState((byte)1);
                                this.setCommandShowTime();
                                this.commandStr = "\u5df2\u88c5\u5907\uff0c\u65e0\u6cd5\u51fa\u552e";
                                this.startCommand();
                                break;
                            }
                            this.currentSaleItemMoney = this.canvas.rolem.itemBusinessData[this.suitPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]][1];
                            this.changeCommandState((byte)0);
                            this.commandStr = "\u91d1\u989d\u4e3a " + this.currentSaleItemMoney + " \u786e\u5b9a\u51fa\u552e\u5417\uff1f";
                            this.startCommand();
                            break;
                        }
                        case 2: {
                            if (this.plusPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex] <= -1) break;
                            if (this.plusPack[1][this.armSaleSeletedIndex + this.armSaleStartIndex] > -1) {
                                this.changeCommandState((byte)1);
                                this.setCommandShowTime();
                                this.commandStr = "\u5df2\u88c5\u5907\uff0c\u65e0\u6cd5\u51fa\u552e";
                                this.startCommand();
                                break;
                            }
                            this.currentSaleItemMoney = this.canvas.rolem.itemBusinessData[this.plusPack[0][this.armSaleSeletedIndex + this.armSaleStartIndex]][1];
                            this.changeCommandState((byte)0);
                            this.commandStr = "\u91d1\u989d\u4e3a " + this.currentSaleItemMoney + " \u786e\u5b9a\u51fa\u552e\u5417\uff1f";
                            this.startCommand();
                        }
                    }
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                if (this.isCommand) {
                    this.finishCommand();
                    break;
                }
                this.forgeBoxState = 0;
                this.setforgebossdata(this.canvas.mapm.cityMapIndex);
                break;
            }
            case 5: {
                if (Control.IsKeyDown(16400)) {
                    if (this.isCommand || this.materialShopIndex != 1) break;
                    this.materialShopIndex = (byte)(this.materialShopIndex - 1);
                    this.materialShopIndex = this.materialShopIndex < 0 ? (byte)0 : this.materialShopIndex;
                    this.isoffsetactionover = false;
                    switch (this.materialShopIndex) {
                        case 0: {
                            this.materialBuySeletedIndex = 0;
                            this.materialBuyStartIndex = 0;
                            this.initmaterialdata();
                            this.materialBuyUseTotal = (byte)this.currentSaleMaterial.length;
                            this.setItemInfo(this.currentSaleMaterial[this.materialBuySeletedIndex + this.materialBuyStartIndex]);
                            break;
                        }
                        case 1: {
                            this.materialSaleSeletedIndex = 0;
                            this.materialSaleStartIndex = 0;
                            this.materialSaleUseTotal = this.itemPackUseNum;
                            this.setItemInfo(this.itemPack[0][this.materialSaleSeletedIndex + this.materialSaleStartIndex]);
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(32832)) {
                    if (this.isCommand || this.materialShopIndex != 0) break;
                    this.materialShopIndex = (byte)(this.materialShopIndex + 1);
                    this.materialShopIndex = this.materialShopIndex > 1 ? (byte)1 : this.materialShopIndex;
                    this.isoffsetactionover = false;
                    switch (this.materialShopIndex) {
                        case 0: {
                            this.materialBuySeletedIndex = 0;
                            this.materialBuyStartIndex = 0;
                            this.initmaterialdata();
                            this.materialBuyUseTotal = (byte)this.currentSaleMaterial.length;
                            this.setItemInfo(this.currentSaleMaterial[this.materialBuySeletedIndex + this.materialBuyStartIndex]);
                            break;
                        }
                        case 1: {
                            this.materialSaleSeletedIndex = 0;
                            this.materialSaleStartIndex = 0;
                            this.materialSaleUseTotal = this.itemPackUseNum;
                            this.setItemInfo(this.itemPack[0][this.materialSaleSeletedIndex + this.materialSaleStartIndex]);
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(4100) || Control.IsKeyHold(4100, 10)) {
                    if (this.isCommand) {
                        switch (this.commandState) {
                            case 0: {
                                this.processNeedNum(1, 99);
                                break;
                            }
                            case 2: {
                                this.processNeedNum(1, this.itemPack[1][this.materialSaleSeletedIndex + this.materialSaleStartIndex]);
                            }
                        }
                        break;
                    }
                    switch (this.materialShopIndex) {
                        case 0: {
                            if (this.materialBuySeletedIndex > 0) {
                                this.materialBuySeletedIndex = (byte)(this.materialBuySeletedIndex - 1);
                            } else if (this.materialBuyStartIndex > 0) {
                                this.materialBuyStartIndex = (byte)(this.materialBuyStartIndex - 1);
                            }
                            this.setItemInfo(this.currentSaleMaterial[this.materialBuySeletedIndex + this.materialBuyStartIndex]);
                            break;
                        }
                        case 1: {
                            if (this.materialSaleSeletedIndex > 0) {
                                this.materialSaleSeletedIndex = (byte)(this.materialSaleSeletedIndex - 1);
                            } else if (this.materialSaleStartIndex > 0) {
                                this.materialSaleStartIndex = (byte)(this.materialSaleStartIndex - 1);
                            }
                            this.setItemInfo(this.itemPack[0][this.materialSaleSeletedIndex + this.materialSaleStartIndex]);
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(8448) || Control.IsKeyHold(8448, 10)) {
                    if (this.isCommand) {
                        switch (this.commandState) {
                            case 0: {
                                this.processNeedNum(-1, 99);
                                break;
                            }
                            case 2: {
                                this.processNeedNum(-1, this.itemPack[1][this.materialSaleSeletedIndex + this.materialSaleStartIndex]);
                            }
                        }
                        break;
                    }
                    switch (this.materialShopIndex) {
                        case 0: {
                            this.totalamount = (byte)(this.materialBuyUseTotal < 5 ? (int)this.materialBuyUseTotal : 5);
                            if (this.materialBuySeletedIndex < this.totalamount - 1) {
                                this.materialBuySeletedIndex = (byte)(this.materialBuySeletedIndex + 1);
                            } else if (this.materialBuyStartIndex < this.materialBuyUseTotal - this.totalamount) {
                                this.materialBuyStartIndex = (byte)(this.materialBuyStartIndex + 1);
                            }
                            this.setItemInfo(this.currentSaleMaterial[this.materialBuySeletedIndex + this.materialBuyStartIndex]);
                            break;
                        }
                        case 1: {
                            this.totalamount = (byte)(this.materialSaleUseTotal < 5 ? (int)this.materialSaleUseTotal : 5);
                            if (this.materialSaleSeletedIndex < this.totalamount - 1) {
                                this.materialSaleSeletedIndex = (byte)(this.materialSaleSeletedIndex + 1);
                            } else if (this.materialSaleStartIndex < this.materialSaleUseTotal - this.totalamount) {
                                this.materialSaleStartIndex = (byte)(this.materialSaleStartIndex + 1);
                            }
                            this.setItemInfo(this.itemPack[0][this.materialSaleSeletedIndex + this.materialSaleStartIndex]);
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(327712)) {
                    if (this.isCommand) {
                        switch (this.commandState) {
                            case 0: {
                                this.commandStr = "\u91d1\u989d\u4e3a " + this.currentBuyItemMoney * this.needNum + " \u786e\u5b9a\u8d2d\u4e70\u5417\uff1f";
                                this.changeCommandState((byte)1);
                                break;
                            }
                            case 1: {
                                if (!this.checkItemBox(this.itemPack, this.currentBuyItemName, (byte)this.needNum)) {
                                    this.commandStr = "\u80cc\u5305\u5df2\u6ee1\uff0c\u53ef\u5728\u5546\u5e97\u6269\u5145\u80cc\u5305";
                                    this.isShowKuoBaoTishi = true;
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)4);
                                    break;
                                }
                                if (!this.checkMoney(this.currentBuyItemMoney * this.needNum)) {
                                    this.canvas.sms.setshowsmsshop(0);
                                    this.commandStr = "\u91d1\u94b1\u4e0d\u8db3";
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)4);
                                    break;
                                }
                                this.addMoney(-this.currentBuyItemMoney * this.needNum);
                                this.addItem(this.currentBuyItemName, (byte)this.needNum);
                                this.commandStr = "\u8d2d\u4e70\u6210\u529f";
                                this.setCommandShowTime();
                                this.changeCommandState((byte)4);
                                break;
                            }
                            case 2: {
                                this.commandStr = "\u91d1\u989d\u4e3a " + this.currentSaleItemMoney * this.needNum + " \u786e\u5b9a\u51fa\u552e\u5417\uff1f";
                                this.changeCommandState((byte)3);
                                break;
                            }
                            case 3: {
                                this.subtractItem(this.materialSaleSeletedIndex + this.materialSaleStartIndex, this.itemPack[0][this.materialSaleSeletedIndex + this.materialSaleStartIndex], (byte)this.needNum);
                                this.addMoney(this.currentSaleItemMoney * this.needNum);
                                if (this.addState == -2) {
                                    if (this.materialSaleStartIndex > 0) {
                                        this.materialSaleStartIndex = (byte)(this.materialSaleStartIndex - 1);
                                    } else if (this.materialSaleSeletedIndex > 0) {
                                        this.materialSaleSeletedIndex = (byte)(this.materialSaleSeletedIndex - 1);
                                    }
                                    this.materialSaleUseTotal = this.itemPackUseNum;
                                }
                                this.setItemInfo(this.itemPack[0][this.materialSaleSeletedIndex + this.materialSaleStartIndex]);
                                this.commandStr = "\u51fa\u552e\u6210\u529f";
                                this.setCommandShowTime();
                                this.changeCommandState((byte)4);
                                break;
                            }
                            case 4: {
                                this.finishCommand();
                            }
                        }
                        break;
                    }
                    switch (this.materialShopIndex) {
                        case 0: {
                            if (this.currentSaleMaterial[this.materialBuySeletedIndex + this.materialBuyStartIndex] <= -1) break;
                            this.checkforgeboxshownews(this.canvas.mapm.cityMapIndex, (byte)(this.materialBuySeletedIndex + this.materialBuyStartIndex));
                            this.currentBuyItemName = this.currentSaleMaterial[this.materialBuySeletedIndex + this.materialBuyStartIndex];
                            this.currentBuyItemMoney = this.canvas.rolem.itemBusinessData[this.currentBuyItemName][0];
                            this.needNum = 1;
                            this.changeCommandState((byte)0);
                            this.startCommand();
                            break;
                        }
                        case 1: {
                            if (this.itemPack[0][this.materialSaleSeletedIndex + this.materialSaleStartIndex] <= -1) break;
                            this.currentSaleItemName = this.itemPack[0][this.materialSaleSeletedIndex + this.materialSaleStartIndex];
                            this.currentSaleItemMoney = this.canvas.rolem.itemBusinessData[this.currentSaleItemName][1];
                            this.needNum = 1;
                            this.changeCommandState((byte)2);
                            this.startCommand();
                        }
                    }
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                if (this.isCommand) {
                    switch (this.commandState) {
                        case 0: 
                        case 2: 
                        case 4: {
                            this.finishCommand();
                            break;
                        }
                        case 1: {
                            this.changeCommandState((byte)0);
                            break;
                        }
                        case 3: {
                            this.changeCommandState((byte)2);
                        }
                    }
                    break;
                }
                this.forgeBoxState = 0;
                this.setforgebossdata(this.canvas.mapm.cityMapIndex);
            }
        }
    }

    private void setplayerproperty(Role role, byte type) {
        this.propertyboxattack = (short)(role.attack[0] + role.attack[1]);
        this.propertyboxbaoji = (short)(role.baoji[0] + role.baoji[1]);
        this.propertyboxdefence = (short)(role.defence[0] + role.defence[1]);
        this.propertyboxbaojiattack = 5;
        switch (this.canvas.rolem.roleState) {
            case 0: {
                this.propertyboxattack += this.canvas.rolem.playerCurrentWeaponAttack[type];
                this.propertyboxattack += this.propertyboxattack * this.canvas.rolem.passiveSkillData[0][this.canvas.rolem.skillState[13][1]] / 100;
                if (this.canvas.rolem.playerCurrentWeaponType == 0) {
                    this.propertyboxattack += this.propertyboxattack * this.canvas.rolem.passiveSkillData[3][this.canvas.rolem.skillState[16][1]] / 100;
                }
                this.canvas.rolem.setPlusCurrentData((byte)0);
                if (this.canvas.rolem.plusCurrentData > 0) {
                    this.propertyboxattack += this.propertyboxattack * this.canvas.rolem.plusCurrentData / 100;
                }
                this.propertyboxbaoji += this.canvas.rolem.playerCurrentWeaponBaojiOdds[type];
                this.canvas.rolem.setPlusCurrentData((byte)3);
                if (this.canvas.rolem.plusCurrentData > 0) {
                    this.propertyboxbaoji += this.canvas.rolem.plusCurrentData;
                }
                this.propertyboxbaoji += this.canvas.rolem.passiveSkillData[4][this.canvas.rolem.skillState[17][1]];
                this.propertyboxbaojiattack += this.canvas.rolem.playerCurrentWeaponBaojiAttack[type];
                this.propertyboxbaojiattack += this.canvas.rolem.passiveSkillData[5][this.canvas.rolem.skillState[18][1]];
                this.canvas.rolem.setPlusCurrentData((byte)4);
                if (this.canvas.rolem.plusCurrentData > 0) {
                    this.propertyboxbaojiattack += this.canvas.rolem.plusCurrentData;
                }
                this.propertyboxdefence += this.propertyboxdefence * this.canvas.rolem.passiveSkillData[1][this.canvas.rolem.skillState[14][1]] / 100;
                this.canvas.rolem.setPlusCurrentData((byte)14);
                if (this.canvas.rolem.plusCurrentData <= 0) break;
                this.propertyboxdefence += this.propertyboxdefence * this.canvas.rolem.plusCurrentData / 100;
                break;
            }
            case 1: 
            case 2: 
            case 3: {
                this.propertyboxattack = (short)(this.propertyboxattack * this.canvas.rolem.rideData[this.canvas.rolem.currentRideIndex][0] / 10);
                this.propertyboxbaoji += this.canvas.rolem.rideData[this.canvas.rolem.currentRideIndex][2];
                this.propertyboxdefence += this.propertyboxdefence * this.canvas.rolem.rideData[this.canvas.rolem.currentRideIndex][1];
                this.propertyboxbaojiattack = (short)(this.propertyboxbaojiattack * this.canvas.rolem.rideData[this.canvas.rolem.currentRideIndex][8]);
            }
        }
        this.propertyboxbaoji = this.propertyboxbaoji > 100 ? 100 : this.propertyboxbaoji;
    }

    private void drawplayerbaselife(Role role) {
        int w = role.life[0];
        int bw = role.baseLife[0];
        this.canvas.drawRect(1114107, 384, 107, 61, 6);
        this.canvas.fillRect(2873389, 385, 108, 60 * w / bw, 1);
        this.canvas.fillRect(7405362, 385, 109, 60 * w / bw, 2);
        this.canvas.fillRect(2873389, 385, 111, 60 * w / bw, 2);
        this.canvas.drawColorString("" + w + "/" + bw, 415, 105, 33, 0xFFFFFF, 0);
    }

    private void drawplayerbasemagic(Role role) {
        int w = role.magic[0];
        int bw = role.baseMagic[0];
        this.canvas.drawRect(1114107, 515, 107, 61, 6);
        this.canvas.fillRect(3131052, 516, 108, 60 * w / bw, 1);
        this.canvas.fillRect(6422499, 516, 109, 60 * w / bw, 2);
        this.canvas.fillRect(3131052, 516, 111, 60 * w / bw, 2);
        this.canvas.drawColorString("" + w + "/" + bw, 545, 105, 33, 0xFFFFFF, 0);
    }

    private void drawplayerbaseexp() {
        int w = this.canvas.rolem.playerCurrentExp;
        int bw = this.canvas.rolem.playerNeedExp;
        this.canvas.drawRect(1114107, 384, 142, 171, 6);
        this.canvas.fillRect(16240686, 385, 143, 170 * w / bw, 1);
        this.canvas.fillRect(0xFFFB5B, 385, 144, 170 * w / bw, 2);
        this.canvas.fillRect(16240686, 385, 146, 170 * w / bw, 2);
        this.canvas.drawColorString("" + this.canvas.rolem.playerCurrentExp + "/" + this.canvas.rolem.playerNeedExp, 465, 140, 33, 0xFFFFFF, 0);
    }

    private void drawplayerbaseinfo() {
        this.drawplayerbaselife(this.canvas.rolem.fplayerbox[0]);
        this.drawplayerbasemagic(this.canvas.rolem.fplayerbox[0]);
        this.drawplayerbaseexp();
        this.drawNumber(this.propertyboxattack, 380, 242, false, 36);
        this.drawNumber(this.propertyboxbaojiattack + 100, 523, 242, false, 36);
        this.drawNumber(this.propertyboxdefence, 380, 277, false, 36);
        this.drawNumber(this.propertyboxbaoji, 523, 277, true, 36);
    }

    public void initpropertyarmdata() {
        this.propertyarmdata[0] = this.canvas.rolem.playerCurrentWeapon[0];
        this.propertyarmdata[1] = this.canvas.rolem.playerCurrentWeapon[1];
        this.propertyarmdata[2] = this.canvas.rolem.playerCurrentWeapon[2];
        this.propertyarmdata[3] = this.canvas.rolem.playerCurrentSuit;
        this.propertyarmdata[4] = this.canvas.rolem.playerCurrentPlus[0];
        this.propertyarmdata[5] = this.canvas.rolem.playerCurrentPlus[1];
    }

    private void drawplayerlevel() {
        if (this.canvas.rolem.playerLevel > 9) {
            this.drawUiModule(102 + this.canvas.rolem.playerLevel / 10, 119, 141, 20, 0);
        }
        this.drawUiModule(102 + this.canvas.rolem.playerLevel % 10, 125, 141, 20, 0);
    }

    public void initplayeraction() {
        this.playeractiontype = this.canvas.rolem.fplayerbox[0].actionType;
        this.playerimages = this.canvas.rolem.fplayerbox[0].images;
        this.playermodules = this.canvas.rolem.fplayerbox[0].modules;
        this.playerframes = this.canvas.rolem.fplayerbox[0].frames;
        this.playeractions = this.canvas.rolem.fplayerbox[0].actions;
        if (this.canvas.rolem.roleState == 0) {
            this.playeractionindex = this.playeractiontypedata[this.playeractiontype];
        } else if (this.canvas.rolem.roleState > 0) {
            this.playeractionindex = 0;
        }
        this.playeractionframeindex = 0;
    }

    public void setplayeraction() {
        this.playeractiontype = (byte)(this.playeractiontype == 0 ? 1 : 0);
        this.playeractionindex = this.playeractiontypedata[this.playeractiontype];
        this.playeractionframeindex = 0;
        this.playerframedelay = 0;
    }

    public void drawplayer(byte playertypetemp) {
        short frameindex = this.playeractions[this.playeractionindex][this.playeractionframeindex + 1];
        short moduleIndex = 0;
        for (int i = 0; i < this.playerframes[frameindex].length; i += 4) {
            moduleIndex = this.playerframes[frameindex][i];
            this.canvas.drawFModule(this.playerimages[this.playermodules[moduleIndex * 5]], this.playerframes[frameindex][i + 1], this.playerframes[frameindex][i + 2], 20, TRANS_TO_DG[this.playerframes[frameindex][i + 3]], 320 - (playertypetemp == 0 ? 165 : 5), 220 - (playertypetemp == 0 ? 0 : 50), 1, this.playermodules, moduleIndex);
        }
        this.staticNextFrame(this.playeractionindex);
    }

    public void staticNextFrame(short actionIndex) {
        this.playerframedelay = (byte)(this.playerframedelay + 1);
        if (this.playerframedelay >= this.playeractions[actionIndex][this.playeractionframeindex]) {
            this.playerframedelay = 0;
            this.playeractionframeindex = (byte)(this.playeractionframeindex + 2);
            if (this.playeractionframeindex >= this.playeractions[actionIndex].length) {
                this.playeractionframeindex = 0;
            }
        }
    }

    private void drawPropertyBox() {
        block42: {
            this.drawStaticUiBack((short)0);
            block0 : switch (this.propertyBoxState) {
                case 0: {
                    this.drawStaticUi((short)82);
                    this.drawStaticUi((short)89);
                    this.drawUiModule(265, LABEL_X[0][0] - 3, 42, 36, 0);
                    this.drawStaticUi((short)16, 0, 360);
                    this.drawplayer((byte)0);
                    this.drawplayerlevel();
                    this.drawUiModule(0, 220 + this.propertyBoxSeletedIndex % 2 * 169 - 165, 76 + this.propertyBoxSeletedIndex / 2 * 93, 20, 0);
                    for (int i = 0; i < 2; ++i) {
                        for (int j = 0; j < 3; ++j) {
                            this.drawarmicon(this.propertyarmdata[j * 2 + i], 222 + i * 169 - 166, 75 + j * 93);
                            if (j * 2 + i == this.propertyBoxSeletedIndex) {
                                this.drawitemiconcheck(222 + i * 169 - 166, 75 + j * 93, this.propertyarmdata[this.propertyBoxSeletedIndex]);
                                continue;
                            }
                            this.drawitemiconmembrane(222 + i * 169 - 166, 75 + j * 93, this.propertyarmdata[j * 2 + i]);
                        }
                    }
                    this.drawskillicon(126, 298, this.canvas.rolem.weaponCriticalKey7[this.canvas.rolem.playerCurrentWeaponType][0]);
                    this.drawskillicon(169, 298, this.canvas.rolem.weaponCriticalKey9[this.canvas.rolem.playerCurrentWeaponType][0]);
                    this.drawplayerbaseinfo();
                    break;
                }
                case 1: {
                    int i;
                    this.drawStaticUi((short)82);
                    this.drawStaticUi((short)16, 0, 360);
                    this.drawlabels(this.propertyboxdata, this.propertyarmindex);
                    if (this.propertyUseTotal > 0) {
                        if (this.propertyarmindex == 0) {
                            this.drawStaticUi((short)123);
                        } else {
                            this.drawStaticUi((short)123);
                        }
                    }
                    if (this.propertyStartIndex > 0) {
                        this.drawjiantou(0, 4, 92, 70, 20);
                    }
                    if (this.propertyStartIndex + 9 < this.propertyUseTotal) {
                        this.drawjiantou(0, 5, 532, 70, 20);
                    }
                    this.drawarmframe();
                    this.drawUiModule(0, 219 + this.propertySeletedIndex * UI_ICON_ITEM_STEP_X - 100, 61, 20, 0);
                    for (i = 0; i < 9; i = (int)((byte)(i + 1))) {
                        if (this.propertyUseTotal > 0) {
                            switch (this.propertyarmindex) {
                                case 0: {
                                    if (this.propertyPack[this.propertyStartIndex + i] <= -1) break;
                                    this.drawarmicon(this.weaponPack[0][this.propertyPack[this.propertyStartIndex + i]], 220 + i * UI_ICON_ITEM_STEP_X - 100, 60);
                                    if (i == this.propertySeletedIndex) {
                                        this.drawitemiconcheck(220 + i * UI_ICON_ITEM_STEP_X - 100, 60, this.weaponPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]]);
                                    } else {
                                        this.drawitemiconmembrane(220 + i * UI_ICON_ITEM_STEP_X - 100, 60, this.weaponPack[0][this.propertyPack[this.propertyStartIndex + i]]);
                                    }
                                    if (this.weaponPack[1][this.propertyPack[this.propertyStartIndex + i]] <= -1) break;
                                    this.drawUiModule(180, 245 + i * UI_ICON_ITEM_STEP_X - 100, 87, 20, 0);
                                    break;
                                }
                                case 1: {
                                    if (this.propertyPack[this.propertyStartIndex + i] <= -1) break;
                                    this.drawarmicon(this.suitPack[0][this.propertyPack[this.propertyStartIndex + i]], 220 + i * UI_ICON_ITEM_STEP_X - 100, 60);
                                    if (i == this.propertySeletedIndex) {
                                        this.drawitemiconcheck(220 + i * UI_ICON_ITEM_STEP_X - 100, 60, this.suitPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]]);
                                    } else {
                                        this.drawitemiconmembrane(220 + i * UI_ICON_ITEM_STEP_X - 100, 60, this.suitPack[0][this.propertyPack[this.propertyStartIndex + i]]);
                                    }
                                    if (this.suitPack[1][this.propertyPack[this.propertyStartIndex + i]] <= -1) break;
                                    this.drawUiModule(180, 245 + i * UI_ICON_ITEM_STEP_X - 100, 87, 20, 0);
                                    break;
                                }
                                case 2: {
                                    if (this.propertyPack[this.propertyStartIndex + i] <= -1) break;
                                    this.drawarmicon(this.plusPack[0][this.propertyPack[this.propertyStartIndex + i]], 220 + i * UI_ICON_ITEM_STEP_X - 100, 60);
                                    if (i == this.propertySeletedIndex) {
                                        this.drawitemiconcheck(220 + i * UI_ICON_ITEM_STEP_X - 100, 60, this.plusPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]]);
                                    } else {
                                        this.drawitemiconmembrane(220 + i * UI_ICON_ITEM_STEP_X - 100, 60, this.plusPack[0][this.propertyPack[this.propertyStartIndex + i]]);
                                    }
                                    if (this.plusPack[1][this.propertyPack[this.propertyStartIndex + i]] <= -1) break;
                                    this.drawUiModule(180, 245 + i * UI_ICON_ITEM_STEP_X - 100, 87, 20, 0);
                                }
                            }
                            continue;
                        }
                        this.canvas.drawColorString("\u6ca1\u6709\u88c5\u5907\u3002", 320, 180, 33, 0xFFFFFF, 0);
                    }
                    switch (this.propertyarmindex) {
                        case 0: {
                            if (this.propertyUseTotal <= 0 || this.propertyStartIndex + this.propertySeletedIndex >= this.propertyUseTotal) break;
                            this.drawCurrentItemInfo(0, this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]);
                            break;
                        }
                        case 1: {
                            if (this.propertyUseTotal <= 0 || this.propertyStartIndex + this.propertySeletedIndex >= this.propertyUseTotal) break;
                            this.drawCurrentItemInfo(1, this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]);
                            break;
                        }
                        case 2: {
                            if (this.propertyUseTotal <= 0 || this.propertyStartIndex + this.propertySeletedIndex >= this.propertyUseTotal) break;
                            this.drawCurrentItemInfo(2, this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]);
                        }
                    }
                    if (this.propertyUseTotal > 0) {
                        this.drawarminfo(this.propertyarmindex);
                    }
                    if (!this.isCommand) break;
                    this.drawUiBack();
                    switch (this.commandState) {
                        case 0: 
                        case 1: {
                            this.drawStaticUi((short)126);
                            for (i = 0; i < this.propertyBoxCommand[this.commandState].length; i = (int)((byte)(i + 1))) {
                                if (i == this.commandSeletedIndex) {
                                    this.drawChoiceBox(320, 144 + this.commandSeletedIndex * 45);
                                    this.canvas.drawColorString(this.propertyBoxCommand[this.commandState][i], 320, 147 + this.commandSeletedIndex * 45, 17, 15597319, 0);
                                    continue;
                                }
                                this.canvas.drawColorString(this.propertyBoxCommand[this.commandState][i], 320, 147 + i * 45, 17, 0xFFFFFF, 0);
                            }
                            break block42;
                        }
                        case 2: {
                            this.drawStaticUi((short)126);
                            for (i = 0; i < this.propertyBoxCommand[this.commandState].length; i = (int)((byte)(i + 1))) {
                                if (i == this.commandSeletedIndex) {
                                    this.drawChoiceBox(320, 125 + this.commandSeletedIndex * 45);
                                    this.canvas.drawColorString(this.propertyBoxCommand[this.commandState][i], 320, 128 + this.commandSeletedIndex * 45, 17, 15597319, 0);
                                    continue;
                                }
                                this.canvas.drawColorString(this.propertyBoxCommand[this.commandState][i], 320, 128 + i * 45, 17, 0xFFFFFF, 0);
                            }
                            break block42;
                        }
                        case 3: {
                            this.changeWeaponImage(this.changeWeaponName);
                            this.drawminiloading(this.changeWeaponTime, 3);
                            if (this.changeWeaponTime == 3) {
                                this.commandStr = "\u88c5\u5907\u6210\u529f\u3002";
                                this.setCommandShowTime();
                                this.changeCommandState((byte)4);
                                break block0;
                            }
                            break block42;
                        }
                        case 4: {
                            this.drawStaticUi((short)26);
                            this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                        }
                    }
                }
            }
        }
    }

    private void propertyBoxKeyProcess() {
        this.loadWeaponImages();
        block0 : switch (this.propertyBoxState) {
            case 0: {
                if (Control.IsKeyDown(4100)) {
                    if (this.propertyBoxSeletedIndex / 2 <= 0) break;
                    this.propertyBoxSeletedIndex = (byte)(this.propertyBoxSeletedIndex - 2);
                    break;
                }
                if (Control.IsKeyDown(8448)) {
                    if (this.propertyBoxSeletedIndex / 2 >= 2) break;
                    this.propertyBoxSeletedIndex = (byte)(this.propertyBoxSeletedIndex + 2);
                    break;
                }
                if (Control.IsKeyDown(16400)) {
                    if (this.propertyBoxSeletedIndex % 2 == 0) {
                        this.propertyBoxSeletedIndex = (byte)(this.propertyBoxSeletedIndex + 1);
                        break;
                    }
                    this.propertyBoxSeletedIndex = (byte)(this.propertyBoxSeletedIndex - 1);
                    break;
                }
                if (Control.IsKeyDown(32832)) {
                    if (this.propertyBoxSeletedIndex % 2 == 1) {
                        this.propertyBoxSeletedIndex = (byte)(this.propertyBoxSeletedIndex - 1);
                        break;
                    }
                    this.propertyBoxSeletedIndex = (byte)(this.propertyBoxSeletedIndex + 1);
                    break;
                }
                if (Control.IsKeyDown(2)) {
                    this.setplayeraction();
                    this.canvas.rolem.changeCurrentWeapon(this.canvas.rolem.fplayerbox[0]);
                    this.setplayerproperty(this.canvas.rolem.fplayerbox[0], this.playeractiontype);
                    break;
                }
                if (Control.IsKeyDown(327712)) {
                    byte[][] temppropertyPack = null;
                    this.propertyBoxState = 1;
                    switch (this.propertyBoxSeletedIndex) {
                        case 0: {
                            this.setPropertyPack(this.weaponPack, 44, 59);
                            temppropertyPack = this.weaponPack;
                            this.propertyarmindex = 0;
                            break;
                        }
                        case 1: {
                            this.setPropertyPack(this.weaponPack, 59, 74);
                            temppropertyPack = this.weaponPack;
                            this.propertyarmindex = 0;
                            break;
                        }
                        case 2: {
                            this.setPropertyPack(this.weaponPack, 74, 78);
                            temppropertyPack = this.weaponPack;
                            this.propertyarmindex = 0;
                            break;
                        }
                        case 3: {
                            this.setPropertyPack(this.suitPack, 78, 88);
                            temppropertyPack = this.suitPack;
                            this.propertyarmindex = 1;
                            break;
                        }
                        case 4: 
                        case 5: {
                            this.setPropertyPack(this.plusPack, 88, 124);
                            temppropertyPack = this.plusPack;
                            this.propertyarmindex = (byte)2;
                        }
                    }
                    if (this.propertyarmdata[this.propertyBoxSeletedIndex] > -1) {
                        int temparmindex = 0;
                        int i = 0;
                        for (i = 0; i < this.propertyPack.length; i = (int)((byte)(i + 1))) {
                            if (temppropertyPack[0][this.propertyPack[i]] != this.propertyarmdata[this.propertyBoxSeletedIndex] || temppropertyPack[1][this.propertyPack[i]] <= -1) continue;
                            temparmindex = i;
                            break;
                        }
                        if (temparmindex > 4) {
                            this.propertySeletedIndex = (byte)(temparmindex % 5);
                            this.propertyStartIndex = (byte)(temparmindex / 5 * 5);
                        } else {
                            this.propertySeletedIndex = (byte)temparmindex;
                            this.propertyStartIndex = 0;
                        }
                        if (i == this.propertyPack.length) {
                            this.propertySeletedIndex = 0;
                            this.propertyStartIndex = 0;
                        }
                    } else {
                        this.propertySeletedIndex = 0;
                        this.propertyStartIndex = 0;
                    }
                    this.scroll.close();
                    if (this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex] <= -1) {
                        return;
                    }
                    switch (this.propertyBoxSeletedIndex) {
                        case 0: 
                        case 1: 
                        case 2: {
                            this.setWeaponInfo(this.weaponPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]], (byte)1);
                            this.showweapony = 0;
                            this.showweaponvdir = 1;
                            this.loadweaponimagetime = 0;
                            this.loadweaponname = this.weaponPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]];
                            break;
                        }
                        case 3: {
                            this.setWeaponInfo(this.suitPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]], (byte)1);
                            break;
                        }
                        case 4: 
                        case 5: {
                            this.setWeaponInfo(this.plusPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]], (byte)1);
                        }
                    }
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                this.changeUiState((byte)1);
                break;
            }
            case 1: {
                if (Control.IsKeyDown(16400) || Control.IsKeyHold(16400, 10)) {
                    if (this.isCommand) break;
                    if (this.propertySeletedIndex > 0) {
                        this.propertySeletedIndex = (byte)(this.propertySeletedIndex - 1);
                    } else if (this.propertyStartIndex > 0) {
                        this.propertyStartIndex = (byte)(this.propertyStartIndex - 1);
                    }
                    if (this.propertyarmindex == 0) {
                        this.showweapony = 0;
                        this.showweaponvdir = 1;
                        this.loadweaponimagetime = 0;
                        this.loadweaponname = this.weaponPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]];
                    }
                    if (this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex] <= -1) break;
                    switch (this.propertyarmindex) {
                        case 0: {
                            this.setWeaponInfo(this.weaponPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]], (byte)1);
                            break;
                        }
                        case 1: {
                            this.setWeaponInfo(this.suitPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]], (byte)1);
                            break;
                        }
                        case 2: {
                            this.setWeaponInfo(this.plusPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]], (byte)1);
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(32832) || Control.IsKeyHold(32832, 10)) {
                    int total;
                    if (this.isCommand) break;
                    int n = total = this.propertyUseTotal < 9 ? (int)this.propertyUseTotal : 9;
                    if (this.propertySeletedIndex < total - 1) {
                        this.propertySeletedIndex = (byte)(this.propertySeletedIndex + 1);
                    } else if (this.propertyStartIndex < this.propertyUseTotal - total) {
                        this.propertyStartIndex = (byte)(this.propertyStartIndex + 1);
                    }
                    if (this.propertyarmindex == 0) {
                        this.showweapony = 0;
                        this.showweaponvdir = 1;
                        this.loadweaponimagetime = 0;
                        this.loadweaponname = this.weaponPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]];
                    }
                    if (this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex] > -1) {
                        switch (this.propertyarmindex) {
                            case 0: {
                                this.setWeaponInfo(this.weaponPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]], (byte)1);
                                break;
                            }
                            case 1: {
                                this.setWeaponInfo(this.suitPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]], (byte)1);
                                break;
                            }
                            case 2: {
                                this.setWeaponInfo(this.plusPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]], (byte)1);
                            }
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(4100)) {
                    if (!this.isCommand || this.commandState < 0 || this.commandState >= this.PROPERTYBOXCOMMANDTOTAL.length) break;
                    this.commandSeletedIndex = (byte)(this.commandSeletedIndex - 1);
                    this.commandSeletedIndex = this.commandSeletedIndex < 0 ? this.PROPERTYBOXCOMMANDTOTAL[this.commandState] : this.commandSeletedIndex;
                    break;
                }
                if (Control.IsKeyDown(8448)) {
                    if (!this.isCommand || this.commandState < 0 || this.commandState >= this.PROPERTYBOXCOMMANDTOTAL.length) break;
                    this.commandSeletedIndex = (byte)(this.commandSeletedIndex + 1);
                    this.commandSeletedIndex = this.commandSeletedIndex > this.PROPERTYBOXCOMMANDTOTAL[this.commandState] ? (byte)0 : this.commandSeletedIndex;
                    break;
                }
                if (Control.IsKeyDown(327712)) {
                    if (this.isCommand) {
                        switch (this.commandState) {
                            case 0: {
                                switch (this.commandSeletedIndex) {
                                    case 0: {
                                        block37 : switch (this.propertyarmindex) {
                                            case 0: {
                                                this.changeWeapon((byte)this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]);
                                                this.changeWeaponName = this.weaponPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]];
                                                this.changeWeaponTime = 0;
                                                this.changeCommandState((byte)3);
                                                break;
                                            }
                                            case 1: {
                                                this.changeSuit((byte)(this.propertyStartIndex + this.propertySeletedIndex));
                                                this.commandStr = "\u88c5\u5907\u6210\u529f";
                                                this.setCommandShowTime();
                                                this.changeCommandState((byte)4);
                                                break;
                                            }
                                            case 2: {
                                                switch (this.propertyBoxSeletedIndex) {
                                                    case 4: {
                                                        if (this.isPutOnPlus((byte)0)) {
                                                            this.changePlus((byte)0, (byte)this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]);
                                                        } else {
                                                            this.putOnPlus((byte)0, (byte)this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]);
                                                        }
                                                        this.commandStr = "\u88c5\u5907\u6210\u529f";
                                                        this.setCommandShowTime();
                                                        this.changeCommandState((byte)4);
                                                        break block37;
                                                    }
                                                    case 5: {
                                                        if (this.isPutOnPlus((byte)1)) {
                                                            this.changePlus((byte)1, (byte)this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]);
                                                        } else {
                                                            this.putOnPlus((byte)1, (byte)this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]);
                                                        }
                                                        this.commandStr = "\u88c5\u5907\u6210\u529f";
                                                        this.setCommandShowTime();
                                                        this.changeCommandState((byte)4);
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                    }
                                    case 1: {
                                        this.finishCommand();
                                    }
                                }
                                break;
                            }
                            case 1: {
                                switch (this.commandSeletedIndex) {
                                    case 0: {
                                        this.putOffPlus(this.plusPack[1][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]]);
                                        this.commandStr = "\u5378\u4e0b\u6210\u529f";
                                        this.setCommandShowTime();
                                        this.changeCommandState((byte)4);
                                        break;
                                    }
                                    case 1: {
                                        this.finishCommand();
                                    }
                                }
                                break;
                            }
                            case 2: {
                                break;
                            }
                            case 3: {
                                this.weaponImage = null;
                                this.finishCommand();
                                break;
                            }
                            case 4: {
                                this.finishCommand();
                            }
                        }
                        break;
                    }
                    switch (this.propertyBoxSeletedIndex) {
                        case 0: {
                            if (this.weaponPack[1][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]] == 0) {
                                this.commandStr = "\u5df2\u88c5\u5907";
                                this.setCommandShowTime();
                                this.changeCommandState((byte)4);
                                this.startCommand();
                                break;
                            }
                            if (this.weaponPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]] == -1) {
                                this.commandStr = "\u6ca1\u6709\u6b66\u5668";
                                this.setCommandShowTime();
                                this.changeCommandState((byte)4);
                                this.startCommand();
                                break;
                            }
                            this.commandSeletedIndex = 0;
                            this.changeCommandState((byte)0);
                            this.startCommand();
                            break;
                        }
                        case 1: {
                            if (this.weaponPack[1][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]] == 1) {
                                this.commandStr = "\u5df2\u88c5\u5907";
                                this.setCommandShowTime();
                                this.changeCommandState((byte)4);
                                this.startCommand();
                                break;
                            }
                            if (this.weaponPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]] == -1) {
                                this.commandStr = "\u6ca1\u6709\u6b66\u5668";
                                this.setCommandShowTime();
                                this.changeCommandState((byte)4);
                                this.startCommand();
                                break;
                            }
                            this.commandSeletedIndex = 0;
                            this.changeCommandState((byte)0);
                            this.startCommand();
                            break;
                        }
                        case 2: {
                            if (this.weaponPack[1][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]] == 2) {
                                this.commandStr = "\u5df2\u88c5\u5907";
                                this.setCommandShowTime();
                                this.changeCommandState((byte)4);
                                this.startCommand();
                                break;
                            }
                            if (this.weaponPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]] == -1) {
                                this.commandStr = "\u6ca1\u6709\u6b66\u5668";
                                this.setCommandShowTime();
                                this.changeCommandState((byte)4);
                                this.startCommand();
                                break;
                            }
                            this.commandSeletedIndex = 0;
                            this.changeCommandState((byte)0);
                            this.startCommand();
                            break;
                        }
                        case 3: {
                            if (this.suitPack[1][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]] == 0) {
                                this.commandStr = "\u5df2\u88c5\u5907";
                                this.setCommandShowTime();
                                this.changeCommandState((byte)4);
                                this.startCommand();
                                break;
                            }
                            if (this.suitPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]] == -1) {
                                this.commandStr = "\u6ca1\u6709\u88c5\u5907";
                                this.setCommandShowTime();
                                this.changeCommandState((byte)4);
                                this.startCommand();
                                break;
                            }
                            this.commandSeletedIndex = 0;
                            this.changeCommandState((byte)0);
                            this.startCommand();
                            break;
                        }
                        case 4: {
                            if (this.plusPack[1][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]] > -1) {
                                this.commandSeletedIndex = 0;
                                this.changeCommandState((byte)1);
                                this.startCommand();
                                break;
                            }
                            if (this.plusPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]] == -1) {
                                this.commandStr = "\u6ca1\u6709\u88c5\u5907";
                                this.setCommandShowTime();
                                this.changeCommandState((byte)4);
                                this.startCommand();
                                break;
                            }
                            this.commandSeletedIndex = 0;
                            this.changeCommandState((byte)0);
                            this.startCommand();
                            break;
                        }
                        case 5: {
                            if (this.plusPack[1][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]] > -1) {
                                this.commandSeletedIndex = 0;
                                this.changeCommandState((byte)1);
                                this.startCommand();
                                break;
                            }
                            if (this.plusPack[0][this.propertyPack[this.propertyStartIndex + this.propertySeletedIndex]] == -1) {
                                this.commandStr = "\u6ca1\u6709\u88c5\u5907";
                                this.setCommandShowTime();
                                this.changeCommandState((byte)4);
                                this.startCommand();
                                break;
                            }
                            this.commandSeletedIndex = 0;
                            this.changeCommandState((byte)0);
                            this.startCommand();
                        }
                    }
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                if (this.isCommand) {
                    switch (this.commandState) {
                        case 0: 
                        case 1: 
                        case 4: {
                            this.finishCommand();
                            break block0;
                        }
                    }
                    break;
                }
                this.initpropertyarmdata();
                this.setplayerproperty(this.canvas.rolem.fplayerbox[0], this.playeractiontype);
                this.weaponImage = null;
                this.propertyBoxState = 0;
            }
        }
    }

    private void setPropertyPack(byte[][] data, int index, int total) {
        this.propertyPack = new int[data[0].length];
        int skip = 0;
        for (int i = 0; i < this.propertyPack.length; ++i) {
            this.propertyPack[i] = -1;
            if (data[0][i] < index || data[0][i] >= total) continue;
            this.propertyPack[skip] = i;
            ++skip;
        }
        this.propertyUseTotal = (byte)skip;
    }

    private void drawTownhouseBox() {
        this.drawStaticUiBack((short)0);
        this.drawStaticUi((short)112);
        this.drawStaticUi((short)88);
        block0 : switch (this.townhouseBoxState) {
            case 0: {
                byte i;
                this.drawUiModule(289, LABEL_X[0][0] - 20, 41, 36, 0);
                this.drawStaticUi((short)122, 485, 180);
                this.drawStaticUi((short)16, 0, 360);
                for (i = 0; i < 6; i = (byte)(i + 1)) {
                    if (i == this.townhouseBoxSeletedIndex) {
                        this.drawStaticUi((short)22, 225, 77 + this.townhouseBoxSeletedIndex * 37);
                        continue;
                    }
                    this.drawStaticUi((short)21, 225, 77 + i * 37);
                }
                for (i = 0; i < (6 < this.townhouseBoxUseTotal ? (byte)6 : (byte)this.townhouseBoxUseTotal); i = (byte)(i + 1)) {
                    this.drawUiModule(this.missionstatedata[this.missionstate[this.currentmissionlist[i + this.townhouseBoxStartIndex]] + 1], 238, 89 + i * 37, 3, 0);
                    if (this.missionstate[this.currentmissionlist[i + this.townhouseBoxStartIndex]] == -1) {
                        this.canvas.drawColorString(this.missioninfo[this.currentmissionlist[i + this.townhouseBoxStartIndex]][0], 280, 83 + i * 37, 20, 0x9F9F9F, 0);
                        continue;
                    }
                    this.canvas.drawColorString(this.missioninfo[this.currentmissionlist[i + this.townhouseBoxStartIndex]][0], 280, 83 + i * 37, 20, 0xFFFFFF, 0);
                }
                this.drawjindutiaoV(183, 69, 8, 233, this.townhouseBoxSeletedIndex + this.townhouseBoxStartIndex, this.townhouseBoxUseTotal, (byte)1);
                if (this.isCommand) {
                    this.drawUiBack();
                    switch (this.commandState) {
                        case 0: {
                            this.drawStaticUi((short)26);
                            this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                        }
                    }
                    break;
                }
                this.drawshowmissionnews();
                break;
            }
            case 1: {
                this.drawUiModule(262, LABEL_X[0][0], 42, 36, 0);
                this.drawStaticUi((short)13);
                switch (this.missionstate[this.currentmissionlist[this.townhouseBoxSeletedIndex + this.townhouseBoxStartIndex]]) {
                    case 0: {
                        this.drawStaticUi((short)35, 0, 360);
                        break;
                    }
                    case 1: 
                    case 2: {
                        this.drawStaticUi((short)36, 0, 360);
                    }
                }
                this.canvas.drawColorString(this.missioninfo[this.currentmissionlist[this.townhouseBoxSeletedIndex + this.townhouseBoxStartIndex]][0], 320, 62, 17, 16372740, 0);
                this.drawmissiondata(0);
                this.drawmissioninfo(0);
                if (!this.isCommand) break;
                this.drawUiBack();
                switch (this.commandState) {
                    case 0: {
                        this.drawStaticUi((short)19);
                        this.canvas.drawColorString("\u662f\u5426\u63a5\u53d7\u4efb\u52a1\uff1f", 320, 133, 17, 0xFFFFFF, 0);
                        break block0;
                    }
                    case 1: {
                        this.drawStaticUi((short)19);
                        this.canvas.drawColorString("\u662f\u5426\u63d0\u4ea4\u4efb\u52a1\uff1f", 320, 133, 17, 0xFFFFFF, 0);
                        break block0;
                    }
                    case 2: {
                        this.drawStaticUi((short)26);
                        this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                    }
                }
            }
        }
    }

    private void townhouseBoxKeyProcess() {
        switch (this.townhouseBoxState) {
            case 0: {
                if (Control.IsKeyDown(4100) || Control.IsKeyHold(4100, 10)) {
                    if (this.townhouseBoxUseTotal <= 0) break;
                    if (this.townhouseBoxSeletedIndex > 0) {
                        this.townhouseBoxSeletedIndex = (byte)(this.townhouseBoxSeletedIndex - 1);
                        break;
                    }
                    if (this.townhouseBoxStartIndex <= 0) break;
                    this.townhouseBoxStartIndex = (byte)(this.townhouseBoxStartIndex - 1);
                    break;
                }
                if (Control.IsKeyDown(8448) || Control.IsKeyHold(8448, 10)) {
                    int total;
                    int n = total = this.townhouseBoxUseTotal < 6 ? (int)this.townhouseBoxUseTotal : 6;
                    if (this.townhouseBoxSeletedIndex < total - 1) {
                        this.townhouseBoxSeletedIndex = (byte)(this.townhouseBoxSeletedIndex + 1);
                        break;
                    }
                    if (this.townhouseBoxStartIndex >= this.townhouseBoxUseTotal - total) break;
                    this.townhouseBoxStartIndex = (byte)(this.townhouseBoxStartIndex + 1);
                    break;
                }
                if (Control.IsKeyDown(327712)) {
                    if (this.isCommand) {
                        this.finishCommand();
                        break;
                    }
                    if (this.currentmissionlist == null || this.currentmissionlist[this.townhouseBoxSeletedIndex + this.townhouseBoxStartIndex] <= -1) break;
                    if (this.missionstate[this.currentmissionlist[this.townhouseBoxSeletedIndex + this.townhouseBoxStartIndex]] > -1) {
                        this.checkshowmissionnews(this.currentmissionlist[this.townhouseBoxSeletedIndex + this.townhouseBoxStartIndex]);
                        this.showtownhousemission();
                        this.townhouseBoxState = 1;
                        break;
                    }
                    this.startCommand();
                    this.commandStr = "\u8be5\u4efb\u52a1\u672a\u5f00\u542f\uff01";
                    this.setCommandShowTime();
                    this.changeCommandState((byte)0);
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                if (this.isCommand) {
                    this.finishCommand();
                    break;
                }
                this.canvas.mapm.iscityflushinfo = true;
                this.changeFightUiState((byte)3);
                this.canvas.changeGameState(4);
                break;
            }
            case 1: {
                if (Control.IsKeyDown(327712)) {
                    if (this.isCommand) {
                        switch (this.commandState) {
                            case 0: {
                                this.setmission(this.currentmissionlist[this.townhouseBoxSeletedIndex + this.townhouseBoxStartIndex]);
                                this.finishCommand();
                                this.townhouseBoxState = 0;
                                break;
                            }
                            case 1: {
                                if (this.checkbox((byte)1, this.currentmissionlist[this.townhouseBoxSeletedIndex + this.townhouseBoxStartIndex]) > 0) {
                                    this.startCommand();
                                    this.commandStr = "\u80cc\u5305\u5df2\u6ee1\uff0c\u65e0\u6cd5\u63d0\u4ea4\u4efb\u52a1";
                                    this.isShowKuoBaoTishi = true;
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)2);
                                    break;
                                }
                                this.postmission(this.currentmissionlist[this.townhouseBoxSeletedIndex + this.townhouseBoxStartIndex]);
                                this.setcurrentmission(this.canvas.mapm.cityMapIndex);
                                this.finishCommand();
                                if (this.townhouseBoxUseTotal > 0) {
                                    if (this.townhouseBoxSeletedIndex > 0) {
                                        this.townhouseBoxSeletedIndex = (byte)(this.townhouseBoxSeletedIndex - 1);
                                    } else if (this.townhouseBoxStartIndex > 0) {
                                        this.townhouseBoxStartIndex = (byte)(this.townhouseBoxStartIndex - 1);
                                    }
                                } else {
                                    this.townhouseBoxSeletedIndex = 0;
                                    this.townhouseBoxStartIndex = 0;
                                }
                                this.townhouseBoxState = 0;
                                break;
                            }
                            case 2: {
                                this.finishCommand();
                            }
                        }
                        break;
                    }
                    if (this.currentmissionlist[this.townhouseBoxSeletedIndex + this.townhouseBoxStartIndex] <= -1) break;
                    switch (this.missionstate[this.currentmissionlist[this.townhouseBoxSeletedIndex + this.townhouseBoxStartIndex]]) {
                        case 0: {
                            this.startCommand();
                            this.changeCommandState((byte)0);
                            break;
                        }
                        case 1: {
                            this.startCommand();
                            this.commandStr = "\u4efb\u52a1\u6761\u4ef6\u4e0d\u6ee1\u8db3\uff0c\u65e0\u6cd5\u63d0\u4ea4";
                            this.setCommandShowTime();
                            this.changeCommandState((byte)2);
                            break;
                        }
                        case 2: {
                            this.startCommand();
                            this.changeCommandState((byte)1);
                        }
                    }
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                if (this.isCommand) {
                    this.finishCommand();
                    break;
                }
                this.townhouseBoxState = 0;
            }
        }
    }

    public void initconvenientbox() {
        if (this.itemPackNum < 56) {
            byte[] tempdata = new byte[this.convenientSaleData.length + 1];
            tempdata[0] = 10;
            for (int i = 1; i < tempdata.length; ++i) {
                tempdata[i] = this.convenientSaleData[i - 1];
            }
            this.currentsaledata = tempdata;
        } else {
            this.currentsaledata = this.convenientSaleData;
        }
    }

    public void initConvenient() {
        this.convenientIndex = 0;
        this.initconvenientbox();
        this.initoffset();
        switch (this.convenientIndex) {
            case 0: {
                this.convenientBuySeletedIndex = 0;
                this.convenientBuyStartIndex = 0;
                this.convenientBuyUseTotal = (byte)this.currentsaledata.length;
                this.setItemInfo(this.currentsaledata[this.convenientBuySeletedIndex + this.convenientBuyStartIndex]);
                break;
            }
            case 1: {
                this.convenientSaleSeletedIndex = 0;
                this.convenientSaleStartIndex = 0;
                this.convenientBuyUseTotal = this.itemPackUseNum;
                this.setItemInfo(this.itemPack[0][this.convenientBuySeletedIndex + this.convenientBuyStartIndex]);
            }
        }
    }

    private void drawconvenientdata(int j) {
        switch (j) {
            case 0: {
                if (this.convenientBuyUseTotal == 0) {
                    this.canvas.drawColorString("\u6ca1\u6709\u9053\u5177\u3002", 304 + this.offsetx + 640 * j + 200, 155, 3, 0xFFFFFF, 0);
                    break;
                }
                for (int i = 0; i < 5; ++i) {
                    if (i == this.convenientBuySeletedIndex) {
                        this.drawStaticUi((short)39, 29 + this.offsetx + 640 * j + 18 + 45, 50 + this.convenientBuySeletedIndex * 49 + 20);
                    } else {
                        this.drawStaticUi((short)40, 30 + this.offsetx + 640 * j + 18 + 45, 51 + i * 49 + 20);
                    }
                    this.drawarmicon(this.currentsaledata[this.convenientBuyStartIndex + i], 30 + this.offsetx + 640 * j + 18 + 45, 50 + i * 49 + 20);
                    if (i == this.convenientBuySeletedIndex) {
                        this.drawiteminfo(this.currentsaledata[this.convenientBuyStartIndex + i], 50 + this.offsetx + 640 * j + 51 + 45, 61 + i * 49 + 20, 15597319, 2);
                        this.drawitemiconcheck(30 + this.offsetx + 640 * j + 18 + 45, 49 + i * 49 + 20, this.currentsaledata[this.convenientBuyStartIndex + this.convenientBuySeletedIndex]);
                        continue;
                    }
                    this.drawiteminfo(this.currentsaledata[this.convenientBuyStartIndex + i], 50 + this.offsetx + 640 * j + 51 + 45, 61 + i * 49 + 20, 10333123, 2);
                    this.drawitemiconmembrane(30 + this.offsetx + 640 * j + 18 + 45, 48 + i * 49 + 20, this.currentsaledata[this.convenientBuyStartIndex + i]);
                }
                this.drawStaticUi((short)122, 320 + this.offsetx + 640 * j + 45, 180);
                this.drawjindutiaoV(7 + this.offsetx + 640 * j + 11 + 45, 69, 8, 233, this.convenientBuySeletedIndex + this.convenientBuyStartIndex, this.convenientBuyUseTotal, (byte)1);
                break;
            }
            case 1: {
                if (this.convenientSaleUseTotal == 0) {
                    this.canvas.drawColorString("\u6ca1\u6709\u9053\u5177\u3002", 304 + this.offsetx + 640 * j + 200, 155, 3, 0xFFFFFF, 0);
                    break;
                }
                for (int i = 0; i < 5; ++i) {
                    if (i == this.convenientSaleSeletedIndex) {
                        this.drawStaticUi((short)39, 29 + this.offsetx + 640 * j + 18 + 45, 50 + this.convenientSaleSeletedIndex * 49 + 20);
                    } else {
                        this.drawStaticUi((short)40, 30 + this.offsetx + 640 * j + 18 + 45, 51 + i * 49 + 20);
                    }
                    this.drawitemicon(this.itemPack[0][this.convenientSaleStartIndex + i], this.itemPack[1][this.convenientSaleStartIndex + i], 30 + this.offsetx + 640 * j + 18 + 45, 50 + i * 49 + 20);
                    if (i == this.convenientSaleSeletedIndex) {
                        this.drawiteminfo(this.itemPack[0][this.convenientSaleStartIndex + i], 50 + this.offsetx + 640 * j + 51 + 45, 61 + i * 49 + 20, 15597319, 3);
                        this.drawitemiconcheck(30 + this.offsetx + 640 * j + 18 + 45, 49 + i * 49 + 20, this.itemPack[0][this.convenientSaleStartIndex + this.convenientSaleSeletedIndex]);
                        continue;
                    }
                    this.drawiteminfo(this.itemPack[0][this.convenientSaleStartIndex + i], 50 + this.offsetx + 640 * j + 51 + 45, 61 + i * 49 + 20, 10333123, 3);
                    this.drawitemiconmembrane(30 + this.offsetx + 640 * j + 18 + 45, 49 + i * 49 + 20, this.itemPack[0][this.convenientSaleStartIndex + i]);
                }
                this.drawStaticUi((short)122, 320 + this.offsetx + 640 * j + 45, 180);
                this.drawjindutiaoV(7 + this.offsetx + 640 * j + 11 + 45, 69, 8, 233, this.convenientSaleSeletedIndex + this.convenientSaleStartIndex, this.convenientSaleUseTotal, (byte)1);
            }
        }
    }

    public void initKuoBaoTishi() {
        this.isShowKuoBaoTishi = false;
        this.isDrawKuoBaoTishi = true;
        this.kuoBaoState = 0;
    }

    public void drawKuoBaoTishi() {
        if (this.isDrawKuoBaoTishi) {
            this.drawUiBack();
            switch (this.kuoBaoState) {
                case 0: {
                    this.drawStaticUi((short)19);
                    this.canvas.drawColorString("\u662f\u5426\u82b1\u8d391500 \u91d1\u94b1\u6269\u5145\u80cc\u5305\uff1f", 320, 133, 17, 0xFFFFFF, 0);
                    break;
                }
                case 1: {
                    this.drawStaticUi((short)26);
                    this.canvas.drawColorString(this.kuoBaoStr, 320, 128, 17, 0xFFFFFF, 0);
                    this.canvas.drawColorString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 320, 153, 17, 16775789, 0);
                }
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void kuoBaoKeyProcess() {
        if (this.kuoBaoState == 1 && --this.kuobaoTime <= 0) {
            this.isDrawKuoBaoTishi = false;
            if (this.canvas.sms.isshowsmsshop) {
                switch (this.canvas.sms.showsmsshoptype) {
                    case 0: {
                        this.canvas.sms.isshowsmsshop = false;
                        this.canvas.sms.smsShopSelectIndex = 0;
                        this.canvas.sms.changeSmsstate(0, this.uiState);
                        break;
                    }
                    case 1: {
                        if (this.canvas.rolem.playerLevel >= 50) break;
                        this.canvas.sms.smsShopSelectIndex = (byte)2;
                        this.canvas.sms.isshowsmsshop = false;
                        this.canvas.sms.changeSmsstate(0, this.uiState);
                    }
                }
            }
        }
        block4 : switch (this.kuoBaoState) {
            case 0: {
                if (Control.IsKeyDown(327712)) {
                    if (this.itemPackNum >= 56) {
                        this.kuoBaoStr = "\u80cc\u5305\u5df2\u589e\u81f3\u6700\u5927\u3002";
                        this.kuobaoTime = 20;
                        this.kuoBaoState = 1;
                        return;
                    }
                    if (!this.checkMoney(1500)) {
                        this.canvas.sms.setshowsmsshop(0);
                        this.kuoBaoStr = "\u91d1\u94b1\u4e0d\u8db3";
                        this.kuobaoTime = 20;
                        this.kuoBaoState = 1;
                        return;
                    }
                    this.addMoney(-1500);
                    this.kuoBaoStr = this.useItem(this.canvas.rolem.fplayerbox[0]);
                    this.kuobaoTime = 20;
                    this.kuoBaoState = 1;
                    return;
                }
                if (!Control.IsKeyDown(131072)) return;
                this.isDrawKuoBaoTishi = false;
                return;
            }
            case 1: {
                if (!Control.IsKeyDown(-1)) return;
                this.isDrawKuoBaoTishi = false;
                if (!this.canvas.sms.isshowsmsshop) return;
                switch (this.canvas.sms.showsmsshoptype) {
                    case 0: {
                        this.canvas.sms.isshowsmsshop = false;
                        this.canvas.sms.smsShopSelectIndex = 0;
                        this.canvas.sms.changeSmsstate(0, this.uiState);
                        break block4;
                    }
                    case 1: {
                        if (this.canvas.rolem.playerLevel >= 50) return;
                        this.canvas.sms.smsShopSelectIndex = (byte)2;
                        this.canvas.sms.isshowsmsshop = false;
                        this.canvas.sms.changeSmsstate(0, this.uiState);
                    }
                }
            }
        }
    }

    private void drawConvenient() {
        this.drawStaticUiBack((short)0);
        block0 : switch (this.convenientState) {
            case 0: {
                this.drawStaticUi((short)(this.convenientIndex + 92));
                this.drawlabels(this.convenientdata, this.convenientIndex);
                this.drawStaticUi((short)44);
                this.drawStaticUi((short)16, 0, 360);
                if (this.isoffsetactionover) {
                    this.drawconvenientdata(this.convenientIndex);
                } else {
                    for (int j = 0; j < 2; ++j) {
                        this.drawconvenientdata(j);
                    }
                }
                this.scroll.drawscroll();
                this.offsetactionprocess(this.convenientIndex);
                if (!this.isCommand) break;
                this.drawUiBack();
                switch (this.commandState) {
                    case 0: {
                        this.drawStaticUi((short)19);
                        this.drawjiantou(0, 0, 375, 132, 33);
                        this.drawjiantou(0, 1, 375, 150, 17);
                        this.canvas.drawColorString("\u9009\u62e9\u5356\u51fa\u6570\u91cf\uff1a" + this.needNum, 320, 133, 17, 0xFFFFFF, 0);
                        break block0;
                    }
                    case 1: {
                        this.drawStaticUi((short)19);
                        this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                        break block0;
                    }
                    case 2: {
                        this.drawStaticUi((short)26);
                        this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                        break block0;
                    }
                    case 3: {
                        this.drawStaticUi((short)19);
                        this.drawjiantou(0, 0, 375, 132, 33);
                        this.drawjiantou(0, 1, 375, 150, 17);
                        this.canvas.drawColorString("\u9009\u62e9\u4e70\u5165\u6570\u91cf\uff1a" + this.needNum, 320, 133, 17, 0xFFFFFF, 0);
                        break block0;
                    }
                    case 4: {
                        this.drawStaticUi((short)19);
                        this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                    }
                }
            }
        }
    }

    private void convenientKeyProcess() {
        switch (this.convenientState) {
            case 0: {
                if (Control.IsKeyDown(16400)) {
                    if (this.isCommand || this.convenientIndex != 1) break;
                    this.convenientIndex = (byte)(this.convenientIndex - 1);
                    this.convenientIndex = this.convenientIndex < 0 ? (byte)0 : this.convenientIndex;
                    this.isoffsetactionover = false;
                    switch (this.convenientIndex) {
                        case 0: {
                            this.convenientBuySeletedIndex = 0;
                            this.convenientBuyStartIndex = 0;
                            this.convenientBuyUseTotal = (byte)this.currentsaledata.length;
                            this.setItemInfo(this.currentsaledata[this.convenientBuySeletedIndex + this.convenientBuyStartIndex]);
                            break;
                        }
                        case 1: {
                            this.convenientSaleSeletedIndex = 0;
                            this.convenientSaleStartIndex = 0;
                            this.convenientSaleUseTotal = this.itemPackUseNum;
                            this.setItemInfo(this.itemPack[0][this.convenientSaleSeletedIndex + this.convenientSaleStartIndex]);
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(32832)) {
                    if (this.isCommand || this.convenientIndex != 0) break;
                    this.convenientIndex = (byte)(this.convenientIndex + 1);
                    this.convenientIndex = this.convenientIndex > 1 ? (byte)1 : this.convenientIndex;
                    this.isoffsetactionover = false;
                    switch (this.convenientIndex) {
                        case 0: {
                            this.convenientBuySeletedIndex = 0;
                            this.convenientBuyStartIndex = 0;
                            this.convenientBuyUseTotal = (byte)this.currentsaledata.length;
                            this.setItemInfo(this.currentsaledata[this.convenientBuySeletedIndex + this.convenientBuyStartIndex]);
                            break;
                        }
                        case 1: {
                            this.convenientSaleSeletedIndex = 0;
                            this.convenientSaleStartIndex = 0;
                            this.convenientSaleUseTotal = this.itemPackUseNum;
                            this.setItemInfo(this.itemPack[0][this.convenientSaleSeletedIndex + this.convenientSaleStartIndex]);
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(4100) || Control.IsKeyHold(4100, 10)) {
                    if (this.isCommand) {
                        switch (this.commandState) {
                            case 0: {
                                this.processNeedNum(1, this.itemPack[1][this.convenientSaleSeletedIndex + this.convenientSaleStartIndex]);
                                break;
                            }
                            case 3: {
                                if (this.currentBuyItemName == 10) {
                                    this.processNeedNum(1, 1);
                                    break;
                                }
                                this.processNeedNum(1, 99);
                            }
                        }
                        break;
                    }
                    switch (this.convenientIndex) {
                        case 0: {
                            if (this.convenientBuySeletedIndex > 0) {
                                this.convenientBuySeletedIndex = (byte)(this.convenientBuySeletedIndex - 1);
                            } else if (this.convenientBuyStartIndex > 0) {
                                this.convenientBuyStartIndex = (byte)(this.convenientBuyStartIndex - 1);
                            }
                            this.setItemInfo(this.currentsaledata[this.convenientBuySeletedIndex + this.convenientBuyStartIndex]);
                            break;
                        }
                        case 1: {
                            if (this.convenientSaleSeletedIndex > 0) {
                                this.convenientSaleSeletedIndex = (byte)(this.convenientSaleSeletedIndex - 1);
                            } else if (this.convenientSaleStartIndex > 0) {
                                this.convenientSaleStartIndex = (byte)(this.convenientSaleStartIndex - 1);
                            }
                            this.setItemInfo(this.itemPack[0][this.convenientSaleSeletedIndex + this.convenientSaleStartIndex]);
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(8448) || Control.IsKeyHold(8448, 10)) {
                    if (this.isCommand) {
                        switch (this.commandState) {
                            case 0: {
                                this.processNeedNum(-1, this.itemPack[1][this.convenientSaleSeletedIndex + this.convenientSaleStartIndex]);
                                break;
                            }
                            case 3: {
                                if (this.currentBuyItemName == 10) {
                                    this.processNeedNum(-1, 1);
                                    break;
                                }
                                this.processNeedNum(-1, 99);
                            }
                        }
                        break;
                    }
                    switch (this.convenientIndex) {
                        case 0: {
                            this.totalamount = (byte)(this.convenientBuyUseTotal < 5 ? (int)this.convenientBuyUseTotal : 5);
                            if (this.convenientBuySeletedIndex < this.totalamount - 1) {
                                this.convenientBuySeletedIndex = (byte)(this.convenientBuySeletedIndex + 1);
                            } else if (this.convenientBuyStartIndex < this.convenientBuyUseTotal - this.totalamount) {
                                this.convenientBuyStartIndex = (byte)(this.convenientBuyStartIndex + 1);
                            }
                            this.setItemInfo(this.currentsaledata[this.convenientBuySeletedIndex + this.convenientBuyStartIndex]);
                            break;
                        }
                        case 1: {
                            this.totalamount = (byte)(this.convenientSaleUseTotal < 5 ? (int)this.convenientSaleUseTotal : 5);
                            if (this.convenientSaleSeletedIndex < this.totalamount - 1) {
                                this.convenientSaleSeletedIndex = (byte)(this.convenientSaleSeletedIndex + 1);
                            } else if (this.convenientSaleStartIndex < this.convenientSaleUseTotal - this.totalamount) {
                                this.convenientSaleStartIndex = (byte)(this.convenientSaleStartIndex + 1);
                            }
                            this.setItemInfo(this.itemPack[0][this.convenientSaleSeletedIndex + this.convenientSaleStartIndex]);
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(327712)) {
                    if (this.isCommand) {
                        switch (this.commandState) {
                            case 0: {
                                this.commandStr = "\u91d1\u989d\u4e3a " + this.currentSaleItemMoney * this.needNum + " \u786e\u5b9a\u51fa\u552e\u5417\uff1f";
                                this.changeCommandState((byte)1);
                                break;
                            }
                            case 1: {
                                this.subtractItem(this.convenientSaleSeletedIndex + this.convenientSaleStartIndex, this.itemPack[0][this.convenientSaleSeletedIndex + this.convenientSaleStartIndex], (byte)this.needNum);
                                this.addMoney(this.currentSaleItemMoney * this.needNum);
                                if (this.addState == -2) {
                                    if (this.convenientSaleStartIndex > 0) {
                                        this.convenientSaleStartIndex = (byte)(this.convenientSaleStartIndex - 1);
                                    } else if (this.convenientSaleSeletedIndex > 0) {
                                        this.convenientSaleSeletedIndex = (byte)(this.convenientSaleSeletedIndex - 1);
                                    }
                                    this.convenientSaleUseTotal = this.itemPackUseNum;
                                }
                                this.setItemInfo(this.itemPack[0][this.convenientSaleSeletedIndex + this.convenientSaleStartIndex]);
                                this.setCommandShowTime();
                                this.commandStr = "\u5356\u51fa\u6210\u529f";
                                this.changeCommandState((byte)2);
                                break;
                            }
                            case 2: {
                                this.finishCommand();
                                break;
                            }
                            case 3: {
                                this.commandStr = "\u91d1\u989d\u4e3a " + this.currentBuyItemMoney * this.needNum + " \u786e\u5b9a\u8d2d\u4e70\u5417\uff1f";
                                this.changeCommandState((byte)4);
                                break;
                            }
                            case 4: {
                                if (this.currentBuyItemName == 10) {
                                    if (this.itemPackNum >= 56) {
                                        this.commandStr = "\u80cc\u5305\u5df2\u589e\u81f3\u6700\u5927\u3002";
                                        this.setCommandShowTime();
                                        this.changeCommandState((byte)2);
                                        break;
                                    }
                                    if (!this.checkMoney(this.currentBuyItemMoney * this.needNum)) {
                                        this.canvas.sms.setshowsmsshop(0);
                                        this.commandStr = "\u91d1\u94b1\u4e0d\u8db3";
                                        this.setCommandShowTime();
                                        this.changeCommandState((byte)2);
                                        break;
                                    }
                                    this.addMoney(-this.currentBuyItemMoney * this.needNum);
                                    this.commandStr = this.useItem(this.canvas.rolem.fplayerbox[0]);
                                    if (this.itemPackNum >= 56) {
                                        this.initconvenientbox();
                                        this.convenientBuyUseTotal = (byte)this.currentsaledata.length;
                                        this.setItemInfo(this.currentsaledata[this.convenientBuySeletedIndex + this.convenientBuyStartIndex]);
                                    }
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)2);
                                    break;
                                }
                                if (!this.checkItemBox(this.itemPack, this.currentBuyItemName, (byte)this.needNum)) {
                                    this.commandStr = "\u80cc\u5305\u5df2\u6ee1\uff0c\u53ef\u5728\u5546\u5e97\u6269\u5145\u80cc\u5305";
                                    this.isShowKuoBaoTishi = true;
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)2);
                                    break;
                                }
                                if (!this.checkMoney(this.currentBuyItemMoney * this.needNum)) {
                                    this.canvas.sms.setshowsmsshop(0);
                                    this.commandStr = "\u91d1\u94b1\u4e0d\u8db3";
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)2);
                                    break;
                                }
                                this.addMoney(-this.currentBuyItemMoney * this.needNum);
                                this.addItem(this.currentBuyItemName, (byte)this.needNum);
                                this.commandStr = "\u8d2d\u4e70\u6210\u529f";
                                this.setCommandShowTime();
                                this.changeCommandState((byte)2);
                            }
                        }
                        break;
                    }
                    switch (this.convenientIndex) {
                        case 0: {
                            if (this.currentsaledata[this.convenientBuySeletedIndex + this.convenientBuyStartIndex] <= -1) break;
                            this.currentBuyItemName = this.currentsaledata[this.convenientBuySeletedIndex + this.convenientBuyStartIndex];
                            this.currentBuyItemMoney = this.canvas.rolem.itemBusinessData[this.currentBuyItemName][2];
                            this.needNum = 1;
                            this.startCommand();
                            if (this.currentBuyItemName == 10) {
                                this.commandStr = "\u91d1\u989d\u4e3a " + this.currentBuyItemMoney * 1 + " \u786e\u5b9a\u8d2d\u4e70\u5417\uff1f";
                                this.changeCommandState((byte)4);
                                break;
                            }
                            this.changeCommandState((byte)3);
                            break;
                        }
                        case 1: {
                            if (this.itemPack[0][this.convenientSaleSeletedIndex + this.convenientSaleStartIndex] == -1) break;
                            this.currentSaleItemName = this.itemPack[0][this.convenientSaleSeletedIndex + this.convenientSaleStartIndex];
                            this.currentSaleItemMoney = this.canvas.rolem.itemBusinessData[this.currentSaleItemName][3];
                            this.needNum = 1;
                            this.startCommand();
                            this.changeCommandState((byte)0);
                        }
                    }
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                if (this.isCommand) {
                    switch (this.commandState) {
                        case 0: 
                        case 2: 
                        case 3: {
                            this.finishCommand();
                            break;
                        }
                        case 1: {
                            this.changeCommandState((byte)0);
                            break;
                        }
                        case 4: {
                            if (this.currentBuyItemName == 10) {
                                this.finishCommand();
                                break;
                            }
                            this.changeCommandState((byte)3);
                        }
                    }
                    break;
                }
                this.changeUiState((byte)0);
            }
        }
    }

    private int drawmoney(int num, int x, int y, int anchor) {
        int w = 6;
        this.canvas.drawColorString("" + num, x, y, anchor, 0xFFFFFF, 0);
        String s = Integer.toString(num);
        return x -= s.length() * w >> 1;
    }

    public void drawPlayerMoney() {
        int x = this.drawmoney(this.playerMoney, 320, 359, 33);
        this.drawUiModule(120, x - 4, 360, 40, 0);
    }

    private void drawiteminfo(int name, int x, int y, int color, int type) {
        if (name > -1) {
            this.canvas.drawColorString(this.iteminfo[name][0], x, y, 20, color, 0);
            this.canvas.drawColorString("" + this.canvas.rolem.itemBusinessData[name][type], x + 112, y, 24, 0xFFFFFF, 0);
        }
    }

    public void initshopbox() {
        if (this.itemPackNum < 56) {
            byte[] tempdata = new byte[this.shopSaleData.length + 1];
            tempdata[0] = 10;
            for (int i = 1; i < tempdata.length; ++i) {
                tempdata[i] = this.shopSaleData[i - 1];
            }
            this.currentsaledata = tempdata;
        } else {
            this.currentsaledata = this.shopSaleData;
        }
    }

    public void initshopdata() {
        this.shopState = 0;
        this.shopIndex = 0;
        this.shopBuySeletedIndex = 0;
        this.shopBuyStartIndex = 0;
        this.initoffset();
        this.initshopbox();
        switch (this.shopIndex) {
            case 0: {
                this.shopBuyUseTotal = (byte)this.currentsaledata.length;
                this.setItemInfo(this.currentsaledata[this.shopBuySeletedIndex + this.shopBuyStartIndex]);
                break;
            }
            case 1: {
                this.shopSaleUseTotal = this.itemPackUseNum;
                this.setItemInfo(this.itemPack[0][this.shopBuySeletedIndex + this.shopBuyStartIndex]);
            }
        }
    }

    public void initoffset() {
        this.offsetx = 0;
        this.isoffsetactionover = true;
    }

    private void offsetactionprocess(byte index) {
        switch (index) {
            case 0: {
                if (this.offsetx == 0) {
                    this.isoffsetactionover = true;
                    break;
                }
                if (this.offsetx >= 0) break;
                this.offsetx += 80;
                break;
            }
            case 1: {
                if (this.offsetx == -640) {
                    this.isoffsetactionover = true;
                    break;
                }
                if (this.offsetx > -640) {
                    this.offsetx -= 80;
                    break;
                }
                if (this.offsetx >= -640) break;
                this.offsetx += 80;
                break;
            }
            case 2: {
                if (this.offsetx == -1280) {
                    this.isoffsetactionover = true;
                    break;
                }
                if (this.offsetx <= -1280) break;
                this.offsetx -= 80;
            }
        }
    }

    private void drawshopdata(int j) {
        switch (j) {
            case 0: {
                if (this.shopBuyUseTotal == 0) {
                    this.canvas.drawColorString("\u6ca1\u6709\u9053\u5177\u3002", 304 + this.offsetx + 640 * j + 200, 155, 3, 0xFFFFFF, 0);
                    break;
                }
                for (int i = 0; i < 5; ++i) {
                    if (i == this.shopBuySeletedIndex) {
                        this.drawStaticUi((short)39, 29 + this.offsetx + 640 * j + 18 + 45, 50 + this.shopBuySeletedIndex * 49 + 20);
                    } else {
                        this.drawStaticUi((short)40, 30 + this.offsetx + 640 * j + 18 + 45, 51 + i * 49 + 20);
                    }
                    this.drawarmicon(this.currentsaledata[this.shopBuyStartIndex + i], 30 + this.offsetx + 640 * j + 18 + 45, 50 + i * 49 + 20);
                    if (i == this.shopBuySeletedIndex) {
                        this.drawiteminfo(this.currentsaledata[this.shopBuyStartIndex + i], 50 + this.offsetx + 640 * j + 51 + 45, 61 + i * 49 + 20, 15597319, 2);
                        this.drawitemiconcheck(30 + this.offsetx + 640 * j + 18 + 45, 49 + i * 49 + 20, this.currentsaledata[this.shopBuyStartIndex + this.shopBuySeletedIndex]);
                        continue;
                    }
                    this.drawiteminfo(this.currentsaledata[this.shopBuyStartIndex + i], 50 + this.offsetx + 640 * j + 51 + 45, 61 + i * 49 + 20, 10333123, 2);
                    this.drawitemiconmembrane(30 + this.offsetx + 640 * j + 18 + 45, 48 + i * 49 + 20, this.currentsaledata[this.shopBuyStartIndex + i]);
                }
                this.drawStaticUi((short)122, 320 + this.offsetx + 640 * j + 45, 180);
                this.drawjindutiaoV(7 + this.offsetx + 640 * j + 11 + 45, 69, 8, 233, this.shopBuySeletedIndex + this.shopBuyStartIndex, this.shopBuyUseTotal, (byte)1);
                break;
            }
            case 1: {
                if (this.shopSaleUseTotal == 0) {
                    this.canvas.drawColorString("\u6ca1\u6709\u9053\u5177\u3002", 304 + this.offsetx + 640 * j + 200, 155, 3, 0xFFFFFF, 0);
                    break;
                }
                for (int i = 0; i < 5; ++i) {
                    if (i == this.shopSaleSeletedIndex) {
                        this.drawStaticUi((short)39, 29 + this.offsetx + 640 * j + 18 + 45, 50 + this.shopSaleSeletedIndex * 49 + 20);
                    } else {
                        this.drawStaticUi((short)40, 30 + this.offsetx + 640 * j + 18 + 45, 51 + i * 49 + 20);
                    }
                    this.drawitemicon(this.itemPack[0][this.shopSaleStartIndex + i], this.itemPack[1][this.shopSaleStartIndex + i], 30 + this.offsetx + 640 * j + 18 + 45, 50 + i * 49 + 20);
                    if (i == this.shopSaleSeletedIndex) {
                        this.drawiteminfo(this.itemPack[0][this.shopSaleStartIndex + i], 50 + this.offsetx + 640 * j + 51 + 45, 61 + i * 49 + 20, 15597319, 3);
                        this.drawitemiconcheck(30 + this.offsetx + 640 * j + 18 + 45, 49 + i * 49 + 20, this.itemPack[0][this.shopSaleStartIndex + this.shopSaleSeletedIndex]);
                        continue;
                    }
                    this.drawiteminfo(this.itemPack[0][this.shopSaleStartIndex + i], 50 + this.offsetx + 640 * j + 51 + 45, 61 + i * 49 + 20, 10333123, 3);
                    this.drawitemiconmembrane(30 + this.offsetx + 640 * j + 18 + 45, 49 + i * 49 + 20, this.itemPack[0][this.shopSaleStartIndex + i]);
                }
                this.drawStaticUi((short)122, 320 + this.offsetx + 640 * j + 45, 180);
                this.drawjindutiaoV(7 + this.offsetx + 640 * j + 11 + 45, 69, 8, 233, this.shopSaleSeletedIndex + this.shopSaleStartIndex, this.shopSaleUseTotal, (byte)1);
            }
        }
    }

    private void drawShop() {
        this.drawStaticUiBack((short)0);
        block0 : switch (this.shopState) {
            case 0: {
                this.drawStaticUi((short)(this.shopIndex + 92));
                this.drawlabels(this.convenientdata, this.shopIndex);
                this.drawStaticUi((short)44);
                this.drawStaticUi((short)16, 0, 360);
                if (this.isoffsetactionover) {
                    this.drawshopdata(this.shopIndex);
                } else {
                    for (int j = 0; j < 2; ++j) {
                        this.drawshopdata(j);
                    }
                }
                this.scroll.drawscroll();
                this.offsetactionprocess(this.shopIndex);
                if (!this.isCommand) break;
                this.drawUiBack();
                switch (this.commandState) {
                    case 0: {
                        this.drawStaticUi((short)19);
                        this.drawjiantou(0, 0, 375, 132, 33);
                        this.drawjiantou(0, 1, 375, 150, 17);
                        this.canvas.drawColorString("\u9009\u62e9\u5356\u51fa\u6570\u91cf\uff1a" + this.needNum, 320, 133, 17, 0xFFFFFF, 0);
                        break block0;
                    }
                    case 1: {
                        this.drawStaticUi((short)19);
                        this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                        break block0;
                    }
                    case 2: {
                        this.drawStaticUi((short)26);
                        this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                        break block0;
                    }
                    case 3: {
                        this.drawStaticUi((short)19);
                        this.drawjiantou(0, 0, 375, 132, 33);
                        this.drawjiantou(0, 1, 375, 150, 17);
                        this.canvas.drawColorString("\u9009\u62e9\u4e70\u5165\u6570\u91cf\uff1a" + this.needNum, 320, 133, 17, 0xFFFFFF, 0);
                        break block0;
                    }
                    case 4: {
                        this.drawStaticUi((short)19);
                        this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                    }
                }
            }
        }
    }

    private void shopKeyProcess() {
        switch (this.shopState) {
            case 0: {
                if (Control.IsKeyDown(16400)) {
                    if (this.isCommand || this.shopIndex != 1) break;
                    this.shopIndex = (byte)(this.shopIndex - 1);
                    this.shopIndex = this.shopIndex < 0 ? (byte)0 : this.shopIndex;
                    this.isoffsetactionover = false;
                    switch (this.shopIndex) {
                        case 0: {
                            this.shopBuySeletedIndex = 0;
                            this.shopBuyStartIndex = 0;
                            this.shopBuyUseTotal = (byte)this.currentsaledata.length;
                            this.setItemInfo(this.currentsaledata[this.shopBuySeletedIndex + this.shopBuyStartIndex]);
                            break;
                        }
                        case 1: {
                            this.shopSaleSeletedIndex = 0;
                            this.shopSaleStartIndex = 0;
                            this.shopSaleUseTotal = this.itemPackUseNum;
                            this.setItemInfo(this.itemPack[0][this.shopSaleSeletedIndex + this.shopSaleStartIndex]);
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(32832)) {
                    if (this.isCommand || this.shopIndex != 0) break;
                    this.shopIndex = (byte)(this.shopIndex + 1);
                    this.shopIndex = this.shopIndex > 1 ? (byte)0 : this.shopIndex;
                    this.isoffsetactionover = false;
                    switch (this.shopIndex) {
                        case 0: {
                            this.shopBuySeletedIndex = 0;
                            this.shopBuyStartIndex = 0;
                            this.shopBuyUseTotal = (byte)this.currentsaledata.length;
                            this.setItemInfo(this.currentsaledata[this.shopBuySeletedIndex + this.shopBuyStartIndex]);
                            break;
                        }
                        case 1: {
                            this.shopSaleSeletedIndex = 0;
                            this.shopSaleStartIndex = 0;
                            this.shopSaleUseTotal = this.itemPackUseNum;
                            this.setItemInfo(this.itemPack[0][this.shopSaleSeletedIndex + this.shopSaleStartIndex]);
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(4100) || Control.IsKeyHold(4100, 10)) {
                    if (this.isCommand) {
                        switch (this.commandState) {
                            case 0: {
                                this.processNeedNum(1, this.itemPack[1][this.shopSaleSeletedIndex + this.shopSaleStartIndex]);
                                break;
                            }
                            case 3: {
                                if (this.currentBuyItemName == 10) {
                                    this.processNeedNum(1, 1);
                                    break;
                                }
                                this.processNeedNum(1, 99);
                            }
                        }
                        break;
                    }
                    switch (this.shopIndex) {
                        case 0: {
                            if (this.shopBuySeletedIndex > 0) {
                                this.shopBuySeletedIndex = (byte)(this.shopBuySeletedIndex - 1);
                            } else if (this.shopBuyStartIndex > 0) {
                                this.shopBuyStartIndex = (byte)(this.shopBuyStartIndex - 1);
                            }
                            this.setItemInfo(this.currentsaledata[this.shopBuySeletedIndex + this.shopBuyStartIndex]);
                            break;
                        }
                        case 1: {
                            if (this.shopSaleSeletedIndex > 0) {
                                this.shopSaleSeletedIndex = (byte)(this.shopSaleSeletedIndex - 1);
                            } else if (this.shopSaleStartIndex > 0) {
                                this.shopSaleStartIndex = (byte)(this.shopSaleStartIndex - 1);
                            }
                            this.setItemInfo(this.itemPack[0][this.shopSaleSeletedIndex + this.shopSaleStartIndex]);
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(8448) || Control.IsKeyHold(8448, 10)) {
                    if (this.isCommand) {
                        switch (this.commandState) {
                            case 0: {
                                this.processNeedNum(-1, this.itemPack[1][this.shopSaleSeletedIndex + this.shopSaleStartIndex]);
                                break;
                            }
                            case 3: {
                                if (this.currentBuyItemName == 10) {
                                    this.processNeedNum(-1, 1);
                                    break;
                                }
                                this.processNeedNum(-1, 99);
                            }
                        }
                        break;
                    }
                    switch (this.shopIndex) {
                        case 0: {
                            this.totalamount = (byte)(this.shopBuyUseTotal < 5 ? (int)this.shopBuyUseTotal : 5);
                            if (this.shopBuySeletedIndex < this.totalamount - 1) {
                                this.shopBuySeletedIndex = (byte)(this.shopBuySeletedIndex + 1);
                            } else if (this.shopBuyStartIndex < this.shopBuyUseTotal - this.totalamount) {
                                this.shopBuyStartIndex = (byte)(this.shopBuyStartIndex + 1);
                            }
                            this.setItemInfo(this.currentsaledata[this.shopBuySeletedIndex + this.shopBuyStartIndex]);
                            break;
                        }
                        case 1: {
                            this.totalamount = (byte)(this.shopSaleUseTotal < 5 ? (int)this.shopSaleUseTotal : 5);
                            if (this.shopSaleSeletedIndex < this.totalamount - 1) {
                                this.shopSaleSeletedIndex = (byte)(this.shopSaleSeletedIndex + 1);
                            } else if (this.shopSaleStartIndex < this.shopSaleUseTotal - this.totalamount) {
                                this.shopSaleStartIndex = (byte)(this.shopSaleStartIndex + 1);
                            }
                            this.setItemInfo(this.itemPack[0][this.shopSaleSeletedIndex + this.shopSaleStartIndex]);
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(327712)) {
                    if (this.isCommand) {
                        switch (this.commandState) {
                            case 0: {
                                this.commandStr = "\u91d1\u989d\u4e3a " + this.currentSaleItemMoney * this.needNum + " \u786e\u5b9a\u51fa\u552e\u5417\uff1f";
                                this.changeCommandState((byte)1);
                                break;
                            }
                            case 1: {
                                this.subtractItem(this.shopSaleSeletedIndex + this.shopSaleStartIndex, this.itemPack[0][this.shopSaleSeletedIndex + this.shopSaleStartIndex], (byte)this.needNum);
                                this.addMoney(this.currentSaleItemMoney * this.needNum);
                                if (this.addState == -2) {
                                    if (this.shopSaleStartIndex > 0) {
                                        this.shopSaleStartIndex = (byte)(this.shopSaleStartIndex - 1);
                                    } else if (this.shopSaleSeletedIndex > 0) {
                                        this.shopSaleSeletedIndex = (byte)(this.shopSaleSeletedIndex - 1);
                                    }
                                    this.shopSaleUseTotal = this.itemPackUseNum;
                                }
                                this.setItemInfo(this.itemPack[0][this.shopSaleSeletedIndex + this.shopSaleStartIndex]);
                                this.commandStr = "\u5356\u51fa\u6210\u529f";
                                this.setCommandShowTime();
                                this.changeCommandState((byte)2);
                                break;
                            }
                            case 2: {
                                this.finishCommand();
                                break;
                            }
                            case 3: {
                                this.commandStr = "\u91d1\u989d\u4e3a " + this.currentBuyItemMoney * this.needNum + " \u786e\u5b9a\u8d2d\u4e70\u5417\uff1f";
                                this.changeCommandState((byte)4);
                                break;
                            }
                            case 4: {
                                if (this.currentBuyItemName == 10) {
                                    if (this.itemPackNum >= 56) {
                                        this.commandStr = "\u80cc\u5305\u5df2\u589e\u81f3\u6700\u5927\u3002";
                                        this.setCommandShowTime();
                                        this.changeCommandState((byte)2);
                                        break;
                                    }
                                    if (!this.checkMoney(this.currentBuyItemMoney * this.needNum)) {
                                        this.canvas.sms.setshowsmsshop(0);
                                        this.commandStr = "\u91d1\u94b1\u4e0d\u8db3";
                                        this.setCommandShowTime();
                                        this.changeCommandState((byte)2);
                                        break;
                                    }
                                    this.addMoney(-this.currentBuyItemMoney * this.needNum);
                                    this.commandStr = this.useItem(this.canvas.rolem.fplayerbox[0]);
                                    if (this.itemPackNum >= 56) {
                                        this.initshopbox();
                                        this.shopBuyUseTotal = (byte)this.currentsaledata.length;
                                        this.setItemInfo(this.currentsaledata[this.shopBuySeletedIndex + this.shopBuyStartIndex]);
                                    }
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)2);
                                    break;
                                }
                                if (!this.checkItemBox(this.itemPack, this.currentBuyItemName, (byte)this.needNum)) {
                                    this.commandStr = "\u80cc\u5305\u5df2\u6ee1\uff0c\u53ef\u5728\u5546\u5e97\u6269\u5145\u80cc\u5305";
                                    this.isShowKuoBaoTishi = true;
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)2);
                                    break;
                                }
                                if (!this.checkMoney(this.currentBuyItemMoney * this.needNum)) {
                                    this.canvas.sms.setshowsmsshop(0);
                                    this.commandStr = "\u91d1\u94b1\u4e0d\u8db3";
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)2);
                                    break;
                                }
                                this.addMoney(-this.currentBuyItemMoney * this.needNum);
                                this.addItem(this.currentBuyItemName, (byte)this.needNum);
                                this.commandStr = "\u4e70\u5165\u6210\u529f";
                                this.setCommandShowTime();
                                this.changeCommandState((byte)2);
                            }
                        }
                        break;
                    }
                    switch (this.shopIndex) {
                        case 0: {
                            if (this.currentsaledata[this.shopBuySeletedIndex + this.shopBuyStartIndex] <= -1) break;
                            this.currentBuyItemName = this.currentsaledata[this.shopBuySeletedIndex + this.shopBuyStartIndex];
                            this.currentBuyItemMoney = this.canvas.rolem.itemBusinessData[this.currentBuyItemName][0];
                            this.needNum = 1;
                            this.startCommand();
                            if (this.currentBuyItemName == 10) {
                                this.commandStr = "\u91d1\u989d\u4e3a " + this.currentBuyItemMoney * 1 + " \u786e\u5b9a\u8d2d\u4e70\u5417\uff1f";
                                this.changeCommandState((byte)4);
                                break;
                            }
                            this.changeCommandState((byte)3);
                            break;
                        }
                        case 1: {
                            if (this.itemPack[0][this.shopSaleSeletedIndex + this.shopSaleStartIndex] == -1) break;
                            this.currentSaleItemName = this.itemPack[0][this.shopSaleSeletedIndex + this.shopSaleStartIndex];
                            this.currentSaleItemMoney = this.canvas.rolem.itemBusinessData[this.currentSaleItemName][1];
                            this.needNum = 1;
                            this.startCommand();
                            this.changeCommandState((byte)0);
                        }
                    }
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                if (this.isCommand) {
                    switch (this.commandState) {
                        case 1: {
                            this.changeCommandState((byte)0);
                            break;
                        }
                        case 0: 
                        case 2: 
                        case 3: {
                            this.finishCommand();
                            break;
                        }
                        case 4: {
                            if (this.currentBuyItemName == 10) {
                                this.finishCommand();
                                break;
                            }
                            this.changeCommandState((byte)3);
                        }
                    }
                    break;
                }
                this.canvas.mapm.iscityflushinfo = true;
                this.changeFightUiState((byte)3);
                this.shopBossImage = null;
                this.canvas.changeGameState(4);
            }
        }
    }

    public void showmission() {
        this.scroll.close();
        if (this.missionindexlist != null) {
            switch (this.missionUiBoxIndex) {
                case 0: {
                    this.showmission(this.missionUiBoxIndex, this.missionindexlist[this.missionUiBoxSeletedIndex + this.missionUiBoxStartIndex], this.mainmissionstate);
                    break;
                }
                case 1: {
                    this.showmission(this.missionUiBoxIndex, this.missionindexlist[this.missionUiBoxSeletedIndex + this.missionUiBoxStartIndex], this.missionstate[this.missionindexlist[this.missionUiBoxSeletedIndex + this.missionUiBoxStartIndex]]);
                }
            }
        }
    }

    public void showtownhousemission() {
        this.scroll.close();
        if (this.currentmissionlist != null) {
            this.showmission(1, this.currentmissionlist[this.townhouseBoxSeletedIndex + this.townhouseBoxStartIndex], this.missionstate[this.currentmissionlist[this.townhouseBoxSeletedIndex + this.townhouseBoxStartIndex]]);
        }
    }

    public boolean checkreward() {
        if (this.currentrewardmoney > 0) {
            return true;
        }
        return this.currentreward.length > 0;
    }

    public void showmission(int type, byte index, int state) {
        this.showmissionindex = index;
        this.scroll.close();
        StringBuffer strb = new StringBuffer();
        switch (type) {
            case 0: {
                this.currentreward = this.mainmissionrewarddata[this.showmissionindex];
                this.currentrewardtype = this.mainmissionrewardtype[this.showmissionindex];
                this.currentrewardmoney = this.mainmissionmoneydata[this.showmissionindex];
                strb.append(this.mainmissioninfo[index][1]);
                break;
            }
            case 1: {
                this.currentreward = this.missionrewarddata[this.showmissionindex];
                this.currentrewardtype = this.missionrewardtype[this.showmissionindex];
                this.currentrewardmoney = this.missionmoneydata[this.showmissionindex];
                strb.append(this.missioninfo[index][1]);
            }
        }
        this.scroll.setscroll(strb.toString(), 100, 255, 450, 48, 1, 20);
        strb = new StringBuffer();
        strb.append("$1");
        if (this.checkreward()) {
            if (this.currentrewardmoney > 0) {
                strb.append("$1\u91d1\u94b1 x ");
                strb.append(this.currentrewardmoney);
                strb.append("  ");
            }
            block31: for (int i = 0; i < this.currentreward.length; i += 2) {
                switch (this.currentrewardtype[i / 2]) {
                    case 0: {
                        strb.append("$" + this.getItemLevelColorIndex(this.itemLevel[this.currentreward[i]]));
                        strb.append(this.iteminfo[this.currentreward[i]][0]);
                        strb.append("$1 x ");
                        strb.append(this.currentreward[i + 1]);
                        strb.append("  ");
                        continue block31;
                    }
                    case 1: {
                        strb.append("$" + this.getItemLevelColorIndex(this.itemLevel[this.currentreward[i]]));
                        strb.append(this.iteminfo[this.currentreward[i]][0]);
                        strb.append("$1 x ");
                        strb.append(this.currentreward[i + 1]);
                        strb.append("  ");
                        continue block31;
                    }
                    case 2: {
                        strb.append("$" + this.getItemLevelColorIndex(this.itemLevel[this.currentreward[i]]));
                        strb.append(this.iteminfo[this.currentreward[i]][0] + "\u953b\u9020\u4e66");
                        strb.append("$1 x ");
                        strb.append(this.currentreward[i + 1]);
                        strb.append("  ");
                        continue block31;
                    }
                    case 3: {
                        strb.append("$1\u5750\u9a91\uff1a" + this.canvas.rolem.rolename[this.rideiconindex[this.currentreward[i]]]);
                        strb.append("  ");
                    }
                }
            }
            this.missionrewardword = this.canvas.formatzkword(strb.toString(), 250, 0);
        } else {
            this.missionrewardword = this.canvas.formatzkword("\u65e0", 250, 0);
        }
        strb = new StringBuffer();
        switch (type) {
            case 0: {
                if (state < 2) {
                    strb.append("$1");
                    switch (this.missiondata0[index][4]) {
                        case 0: {
                            strb.append("\u5230\u8fbe\u4efb\u52a1\u5730\u70b9");
                            break;
                        }
                        case 1: {
                            strb.append(this.canvas.rolem.rolename[this.missiondata0[index][5]]);
                            strb.append("\uff1a");
                            strb.append(this.mainmissionplan[0]);
                            strb.append("/");
                            strb.append(this.missiondata0[index][6]);
                            if (this.missiondata0[index][7] <= 0) break;
                            strb.append("|");
                            strb.append(this.canvas.rolem.rolename[this.missiondata0[index][7]]);
                            strb.append("\uff1a");
                            strb.append(this.mainmissionplan[1]);
                            strb.append("/");
                            strb.append(this.missiondata0[index][8]);
                            break;
                        }
                        case 2: {
                            strb.append(this.iteminfo[this.missiondata0[index][5]][0]);
                            strb.append("\uff1a");
                            strb.append(this.mainmissionplan[0]);
                            strb.append("/");
                            strb.append(this.missiondata0[index][6]);
                            if (this.missiondata0[index][7] <= 0) break;
                            strb.append("|");
                            strb.append(this.iteminfo[this.missiondata0[index][7]][0]);
                            strb.append("\uff1a");
                            strb.append(this.mainmissionplan[1]);
                            strb.append("/");
                            strb.append(this.missiondata0[index][8]);
                            break;
                        }
                        case 3: {
                            strb.append("\u96c7\u4f63\u77ff\u5de5");
                            strb.append("\uff1a");
                            strb.append(this.mainmissionplan[0]);
                            strb.append("/");
                            strb.append(this.missiondata0[index][6]);
                            break;
                        }
                        case 4: {
                            strb.append(this.canvas.rolem.rolename[this.missiondata0[index][5]]);
                            break;
                        }
                        case 5: {
                            strb.append(this.iteminfo[this.missiondata0[index][5]][0]);
                            strb.append("\uff1a");
                            strb.append(this.mainmissionplan[0]);
                            strb.append("/");
                            strb.append(this.missiondata0[index][6]);
                            break;
                        }
                        case 6: {
                            strb.append("\u5347\u7ea7\u65b0\u9521\u5b89\u57ce\u5e02\u7b49\u7ea7\uff1a");
                            strb.append(this.mainmissionplan[0]);
                            strb.append("/");
                            strb.append(this.missiondata0[index][6]);
                        }
                    }
                    break;
                }
                strb.append("$2");
                strb.append("\u5df2\u5b8c\u6210");
                break;
            }
            case 1: {
                if (state < 2) {
                    strb.append("$1");
                    switch (this.missiondata1[index][2]) {
                        case 2: {
                            strb.append(this.iteminfo[this.missiondata1[index][3]][0]);
                            strb.append("\uff1a");
                            strb.append(this.missionplan[index][0]);
                            strb.append("/");
                            strb.append(this.missiondata1[index][4]);
                            if (this.missiondata1[index][5] <= 0) break;
                            strb.append("|");
                            strb.append(this.iteminfo[this.missiondata1[index][5]][0]);
                            strb.append("\uff1a");
                            strb.append(this.missionplan[index][1]);
                            strb.append("/");
                            strb.append(this.missiondata1[index][6]);
                            break;
                        }
                        case 1: {
                            strb.append(this.canvas.rolem.rolename[this.missiondata1[index][3]].equals("\u98ce\u8bed\u8005\u4f0a\u8428") ? "\u8fdc\u53e4\u9690\u4fee\u8005" : this.canvas.rolem.rolename[this.missiondata1[index][3]]);
                            strb.append("\uff1a");
                            strb.append(this.missionplan[index][0]);
                            strb.append("/");
                            strb.append(this.missiondata1[index][4]);
                            if (this.missiondata1[index][5] <= 0) break;
                            strb.append("|");
                            strb.append(this.canvas.rolem.rolename[this.missiondata1[index][5]]);
                            strb.append("\uff1a");
                            strb.append(this.missionplan[index][1]);
                            strb.append("/");
                            strb.append(this.missiondata1[index][6]);
                        }
                    }
                    break;
                }
                strb.append("$2");
                strb.append("\u5df2\u5b8c\u6210");
            }
        }
        this.missionaimword = this.canvas.formatzkword(strb.toString(), 250, 0);
        switch (type) {
            case 0: {
                this.missionmapword = "$1" + this.canvas.mapm.universeinfo[this.canvas.mapm.universeMapAreaData[this.missiondata0[index][0]][5]][0] + "- " + this.canvas.mapm.mapinfo[this.missiondata0[index][0]][0];
                break;
            }
            case 1: {
                this.missionmapword = "$1" + this.canvas.mapm.universeinfo[this.canvas.mapm.universeMapAreaData[this.missiondata1[index][1]][5]][0] + "- " + this.canvas.mapm.mapinfo[this.missiondata1[index][1]][0];
            }
        }
    }

    private void drawadornline(int x, int y, int wordnum, int colorindex) {
        this.canvas.fillRect(this.adornlinecolor[colorindex], x - 1, y + 7 + 4, wordnum * 15 + 6, 8);
    }

    public void drawlabel(String str, int x, int y, int wordnum, int colorindex) {
        this.drawadornline(x, y, wordnum, colorindex);
        this.canvas.drawColorString(str, x, y, 20, 0xFFFFFF, 0);
    }

    private void drawmissiondata(int x) {
        this.drawlabel("\u4efb\u52a1\u5956\u52b1", 10 + x + 80, 107, 4, 3);
        if (this.missionrewardword != null) {
            this.canvas.drawZkStringArry(this.missionrewardword, 10 + x + 80, 128, 20, 0, 1, 0xFFFFFF);
        }
        this.drawlabel("\u4efb\u52a1\u76ee\u6807", 10 + x + 80 + 245, 107, 4, 1);
        if (this.missionaimword != null) {
            this.canvas.drawZkStringArry(this.missionaimword, 10 + x + 80 + 245, 128, 20, 0, 2, 0xFFFFFF);
        }
        this.drawlabel("\u4efb\u52a1\u5730\u70b9", 10 + x + 80, 165, 4, 0);
        if (this.missionmapword != null) {
            this.canvas.drawZkString(this.missionmapword, 10 + x + 80, 186, 20, this.canvas.zkData);
        }
    }

    private void drawmissioninfo(int x) {
        this.drawlabel("\u4efb\u52a1\u63cf\u8ff0", 10 + x + 80, 233, 4, 2);
        this.scroll.drawscroll(x);
    }

    private void drawmissionuidata(int j) {
        switch (j) {
            case 0: {
                this.drawStaticUi((short)13, 320 + this.offsetx + 640 * j, 180);
                if (this.missionlist == null) break;
                if (this.currentmainmissionindex == 32 && this.mainmissionstate == 3) {
                    this.canvas.drawColorString("\u6e38\u620f\u5df2\u901a\u5173", 320 + this.offsetx + 640 * j, 62, 17, 16372740, 0);
                    break;
                }
                String str = "";
                str = this.missionlist[0];
                this.canvas.drawColorString(str, 320 + this.offsetx + 640 * j, 62, 17, 16372740, 0);
                this.drawmissiondata(this.offsetx + 640 * j);
                this.drawmissioninfo(this.offsetx + 640 * j);
                break;
            }
            case 1: {
                int i;
                this.drawStaticUi((short)122, 320 + this.offsetx + 640 * j + 165, 180);
                for (i = 0; i < 6; i = (byte)(i + 1)) {
                    if (i == this.missionUiBoxSeletedIndex) {
                        this.drawStaticUi((short)22, 25 + this.offsetx + 640 * j + 35 + 165, 57 + this.missionUiBoxSeletedIndex * 37 + 19);
                        continue;
                    }
                    this.drawStaticUi((short)21, 25 + this.offsetx + 640 * j + 35 + 165, 57 + i * 37 + 19);
                }
                this.drawjindutiaoV(7 + this.offsetx + 640 * j + 11 + 165, 69, 8, 233, this.missionUiBoxSeletedIndex + this.missionUiBoxStartIndex, this.missionUiUseTotal, (byte)1);
                if (this.missionUiBoxIndex != 1 || this.missionlist == null) break;
                for (i = 0; i < (6 > this.missionUiUseTotal ? (int)this.missionUiUseTotal : 6); ++i) {
                    this.drawUiModule(this.missionstatedata[this.missionstate[this.missionindexlist[i + this.missionUiBoxStartIndex]] + 1], 38 + this.offsetx + 640 * j + 35 + 165, 69 + i * 37 + 19, 3, 0);
                    this.canvas.drawColorString(this.missionlist[i + this.missionUiBoxStartIndex], 80 + this.offsetx + 640 * j + 35 + 165, 63 + i * 37 + 19, 20, 0xFFFFFF, 0);
                }
                break;
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void drawMissionUiBox() {
        this.drawStaticUiBack((short)0);
        this.drawStaticUi((byte)(this.missionUiBoxIndex + 92));
        this.drawlabels(this.missionuiboxdata, this.missionUiBoxIndex);
        switch (this.missionUiBoxState) {
            case 0: {
                switch (this.missionUiBoxIndex) {
                    case 0: {
                        this.drawStaticUi((short)84, 0, 360);
                        break;
                    }
                    case 1: {
                        this.drawStaticUi((short)117, 0, 360);
                    }
                }
                break;
            }
            case 1: {
                switch (this.missionUiBoxIndex) {
                    case 1: {
                        this.drawStaticUi((short)13);
                        this.drawStaticUi((short)30, 0, 360);
                    }
                }
            }
        }
        switch (this.missionUiBoxState) {
            case 0: {
                if (this.isoffsetactionover) {
                    this.drawmissionuidata(this.missionUiBoxIndex);
                } else {
                    for (int j = 0; j < 2; ++j) {
                        this.drawmissionuidata(j);
                    }
                }
                this.offsetactionprocess(this.missionUiBoxIndex);
                return;
            }
            case 1: {
                switch (this.missionUiBoxIndex) {
                    case 1: {
                        if (this.missionlist != null) {
                            this.canvas.drawColorString(this.missionlist[this.missionUiBoxSeletedIndex + this.missionUiBoxStartIndex], 320, 62, 17, 16372740, 0);
                            this.drawmissiondata(0);
                            this.drawmissioninfo(0);
                        }
                        if (!this.isCommand) return;
                        this.drawUiBack();
                        switch (this.commandState) {
                            case 0: {
                                this.drawStaticUi((short)19);
                                this.canvas.drawColorString("\u662f\u5426\u653e\u5f03\u4efb\u52a1\uff1f", 320, 133, 17, 15597319, 0);
                            }
                        }
                    }
                }
            }
        }
    }

    public void initmissionuibox() {
        this.missionUiBoxIndex = 0;
        this.missionUiBoxState = 0;
        this.missionUiBoxStartIndex = 0;
        this.missionUiBoxSeletedIndex = 0;
        this.initoffset();
        this.setmissionlist();
        this.showmission();
    }

    private void missionUiBoxKeyProcess() {
        block46: {
            block50: {
                block49: {
                    block48: {
                        block47: {
                            block45: {
                                if (!Control.IsKeyDown(16400) && !Control.IsKeyHold(16400, 10)) break block45;
                                if (this.missionUiBoxState == 0 && this.missionUiBoxIndex == 1) {
                                    this.missionUiBoxIndex = (byte)(this.missionUiBoxIndex - 1);
                                    this.missionUiBoxIndex = this.missionUiBoxIndex < 0 ? (byte)0 : this.missionUiBoxIndex;
                                    this.isoffsetactionover = false;
                                    switch (this.missionUiBoxIndex) {
                                        case 0: {
                                            this.missionUiBoxState = 0;
                                            this.missionUiBoxSeletedIndex = 0;
                                            this.missionUiBoxStartIndex = 0;
                                            this.setmissionlist();
                                            this.showmission();
                                            break;
                                        }
                                        case 1: {
                                            this.missionUiBoxState = 0;
                                            this.missionUiBoxSeletedIndex = 0;
                                            this.missionUiBoxStartIndex = 0;
                                            this.setmissionlist();
                                            this.showmission();
                                            if (this.missionlist == null) break;
                                            this.missionUiUseTotal = (byte)this.missionlist.length;
                                        }
                                    }
                                }
                                break block46;
                            }
                            if (!Control.IsKeyDown(32832) && !Control.IsKeyHold(32832, 10)) break block47;
                            if (this.missionUiBoxState == 0 && this.missionUiBoxIndex == 0) {
                                this.missionUiBoxIndex = (byte)(this.missionUiBoxIndex + 1);
                                this.missionUiBoxIndex = this.missionUiBoxIndex > 1 ? (byte)1 : this.missionUiBoxIndex;
                                this.isoffsetactionover = false;
                                switch (this.missionUiBoxIndex) {
                                    case 0: {
                                        this.missionUiBoxState = 0;
                                        this.missionUiBoxSeletedIndex = 0;
                                        this.missionUiBoxStartIndex = 0;
                                        this.setmissionlist();
                                        this.showmission();
                                        break;
                                    }
                                    case 1: {
                                        this.missionUiBoxState = 0;
                                        this.missionUiBoxSeletedIndex = 0;
                                        this.missionUiBoxStartIndex = 0;
                                        this.setmissionlist();
                                        this.showmission();
                                        if (this.missionlist == null) break;
                                        this.missionUiUseTotal = (byte)this.missionlist.length;
                                    }
                                }
                            }
                            break block46;
                        }
                        if (!Control.IsKeyDown(4100) && !Control.IsKeyHold(4100, 10)) break block48;
                        if (!this.isCommand && this.missionUiBoxIndex == 1 && this.missionUiBoxState == 0) {
                            if (this.missionUiBoxSeletedIndex > 0) {
                                this.missionUiBoxSeletedIndex = (byte)(this.missionUiBoxSeletedIndex - 1);
                            } else if (this.missionUiBoxStartIndex > 0) {
                                this.missionUiBoxStartIndex = (byte)(this.missionUiBoxStartIndex - 1);
                            }
                        }
                        break block46;
                    }
                    if (!Control.IsKeyDown(8448) && !Control.IsKeyHold(8448, 10)) break block49;
                    if (!this.isCommand && this.missionUiBoxIndex == 1 && this.missionUiBoxState == 0) {
                        int total;
                        int n = total = this.missionUiUseTotal < 6 ? (int)this.missionUiUseTotal : 6;
                        if (this.missionUiBoxSeletedIndex < total - 1) {
                            this.missionUiBoxSeletedIndex = (byte)(this.missionUiBoxSeletedIndex + 1);
                        } else if (this.missionUiBoxStartIndex < this.missionUiUseTotal - total) {
                            this.missionUiBoxStartIndex = (byte)(this.missionUiBoxStartIndex + 1);
                        }
                    }
                    break block46;
                }
                if (!Control.IsKeyDown(327712)) break block50;
                block8 : switch (this.missionUiBoxIndex) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        switch (this.missionUiBoxState) {
                            case 0: {
                                if (this.missionindexlist != null) {
                                    this.missionUiBoxState = 1;
                                    this.showmission();
                                    break block8;
                                }
                                break block46;
                            }
                            case 1: {
                                if (this.isCommand) {
                                    switch (this.commandState) {
                                        case 0: {
                                            this.giveupmission(this.missionUiBoxSeletedIndex, this.missionUiBoxStartIndex);
                                            this.finishCommand();
                                            this.missionUiBoxState = 0;
                                        }
                                    }
                                    break block8;
                                }
                                this.startCommand();
                                this.changeCommandState((byte)0);
                            }
                        }
                    }
                }
                break block46;
            }
            if (Control.IsKeyDown(131072)) {
                block19 : switch (this.missionUiBoxIndex) {
                    case 0: {
                        if (this.canvas.rolem.iskeystar) {
                            this.canvas.rolem.iskeystar = false;
                            if (this.canvas.lastgamestate == 4) {
                                this.canvas.mapm.iscityflushinfo = true;
                            }
                            this.canvas.changeGameState(this.canvas.lastgamestate);
                            break;
                        }
                        this.changeUiState((byte)1);
                        break;
                    }
                    case 1: {
                        switch (this.missionUiBoxState) {
                            case 0: {
                                if (this.canvas.rolem.iskeystar) {
                                    this.canvas.rolem.iskeystar = false;
                                    if (this.canvas.lastgamestate == 4) {
                                        this.canvas.mapm.iscityflushinfo = true;
                                    }
                                    this.canvas.changeGameState(this.canvas.lastgamestate);
                                    break block19;
                                }
                                this.changeUiState((byte)1);
                                break block19;
                            }
                            case 1: {
                                if (this.isCommand) {
                                    this.finishCommand();
                                    break block19;
                                }
                                this.missionUiBoxState = 0;
                            }
                        }
                    }
                }
            }
        }
    }

    private void drawshowmission() {
        this.drawStaticUiBack((short)0);
        this.drawStaticUi((short)88);
        this.drawUiModule(262, LABEL_X[0][0], 42, 36, 0);
        this.drawStaticUi((short)13);
        this.drawStaticUi((short)84, 0, 360);
        if (this.missionlist != null) {
            String str = "";
            str = this.missionlist[0];
            this.canvas.drawColorString(str, 320, 62, 17, 16372740, 0);
            this.drawmissiondata(0);
            this.drawmissioninfo(0);
        }
    }

    private void showmissionkeyprocess() {
        if (Control.IsKeyDown(131072)) {
            this.scroll.close();
            switch (this.showmissionlastgamestate) {
                case 4: {
                    this.canvas.mapm.iscityflushinfo = true;
                    break;
                }
                case 10: {
                    this.canvas.mapm.universeMapState = 0;
                    this.canvas.mapm.isuniverseflushinfo = true;
                }
            }
            this.canvas.changeGameState(this.showmissionlastgamestate);
        }
    }

    public void drawtishikuang() {
        this.drawStaticUi((short)26);
    }

    private void drawsaveui() {
        this.canvas.drawFight();
        this.drawUiBack();
        switch (this.savestate) {
            case 0: {
                this.drawStaticUi((short)19);
                this.canvas.drawColorString("\u662f\u5426\u82b1\u8d39\u91d1\u94b1 " + this.savemoney, 320, 123, 17, 0xFFFFFF, 0);
                this.canvas.drawColorString("\u6062\u590d\u751f\u547d\u548c\u80fd\u91cf\uff1f", 320, 143, 17, 0xFFFFFF, 0);
                break;
            }
            case 1: {
                this.drawtishikuang();
                this.canvas.drawColorString("\u5df2\u6062\u590d\u3002", 320, 133, 17, 0xFFFFFF, 0);
                break;
            }
            case 2: {
                this.drawtishikuang();
                this.canvas.drawColorString("\u751f\u547d\u548c\u80fd\u91cf\u5df2\u6ee1\u65e0\u9700\u6062\u590d\u3002", 320, 133, 17, 0xFFFFFF, 0);
                break;
            }
            case 3: {
                this.drawtishikuang();
                this.canvas.drawColorString("\u91d1\u94b1\u4e0d\u8db3", 320, 133, 17, 0xFFFFFF, 0);
                break;
            }
            case 4: {
                this.drawStaticUi((short)19);
                this.canvas.drawColorString("\u662f\u5426\u5b58\u50a8\u6e38\u620f\u8fdb\u5ea6\uff1f", 320, 133, 17, 0xFFFFFF, 0);
                break;
            }
            case 5: {
                this.drawStaticUi((short)26);
                this.canvas.drawColorString("\u5b58\u50a8\u4e2d\u2026\u2026", 320, 133, 17, 0xFFFFFF, 0);
                break;
            }
            case 6: {
                if (this.isshowsave) {
                    this.drawtishikuang();
                    this.canvas.drawColorString("\u5df2\u5b58\u50a8\u3002", 320, 133, 17, 0xFFFFFF, 0);
                }
                if (this.savetime <= 0 || --this.savetime != 0) break;
                this.isshowsave = false;
                this.canvas.changeGameState(this.canvas.lastgamestate);
            }
        }
        if ((this.savestate == 1 || this.savestate == 2 || this.savestate == 3) && this.savetime > 0 && --this.savetime == 0) {
            this.isshowsave = false;
            this.savestate = (byte)4;
        }
    }

    private void saveuikeyprocess() {
        if (this.savestate == 5) {
            this.saveGame();
            this.savestate = (byte)6;
            this.savetime = 20;
            this.isshowsave = true;
            return;
        }
        if (!(this.isshowsave || this.savestate != 1 && this.savestate != 2 && this.savestate != 3)) {
            this.savetime = 20;
            this.isshowsave = true;
            return;
        }
        if (Control.IsKeyDown(327712)) {
            switch (this.savestate) {
                case 0: {
                    if (this.checkMoney(this.savemoney)) {
                        if (this.canvas.rolem.fplayerbox[0].ismanxiemanlan()) {
                            this.savestate = (byte)2;
                            break;
                        }
                        this.canvas.gameUi.addMoney(-this.savemoney);
                        this.canvas.rolem.huifu();
                        this.savestate = 1;
                        break;
                    }
                    this.savestate = (byte)3;
                    break;
                }
                case 1: {
                    this.savestate = (byte)4;
                    break;
                }
                case 2: {
                    this.savestate = (byte)4;
                    break;
                }
                case 3: {
                    this.savestate = (byte)4;
                    break;
                }
                case 4: {
                    this.savestate = (byte)5;
                    break;
                }
                case 5: {
                    break;
                }
                case 6: {
                    this.savetime = 0;
                    this.isshowsave = false;
                    this.canvas.changeGameState(this.canvas.lastgamestate);
                }
            }
        } else if (Control.IsKeyDown(131072)) {
            switch (this.savestate) {
                case 0: 
                case 1: 
                case 2: 
                case 3: {
                    this.savestate = (byte)4;
                    break;
                }
                case 4: 
                case 6: {
                    this.savetime = 0;
                    this.isshowsave = false;
                    this.canvas.changeGameState(this.canvas.lastgamestate);
                }
            }
        }
    }

    private void drawforgebook() {
        this.drawStaticUiBack((short)0);
        switch (this.forgebookstate) {
            case 0: {
                this.drawStaticUi((short)88);
                this.drawUiModule(287, LABEL_X[0][0] - 25, 41, 36, 0);
                this.drawStaticUi((short)112);
                this.drawStaticUi((short)124, 320, 10);
                this.drawStaticUi((short)16, 0, 360);
                this.scroll.drawscroll();
                for (byte i = 0; i < 3; i = (byte)(i + 1)) {
                    if (i == this.forgebookindex) {
                        this.drawStaticUi((short)115, 320, 175 + this.forgebookindex * 42);
                        this.canvas.drawColorString(this.forgebookoption[i], 320, 185 + i * 42, 17, 15597319, 0);
                        continue;
                    }
                    this.drawStaticUi((short)114, 320, 175 + i * 42);
                    this.canvas.drawColorString(this.forgebookoption[i], 320, 185 + i * 42, 17, 10333123, 0);
                }
                break;
            }
            case 1: {
                this.drawStaticUi((short)88);
                this.drawUiModule(this.forgebook[this.forgebookindex], LABEL_X[0][0] - 2, 42, 36, 0);
                this.drawStaticUi((short)122, 365, 180);
                this.drawStaticUi((short)84, 0, 360);
                for (int i = 0; i < 5; ++i) {
                    if (i == this.forgebookselectindex) {
                        this.drawStaticUi((short)39, 92, 50 + this.forgebookselectindex * 49 + 20);
                    } else {
                        this.drawStaticUi((short)40, 93, 51 + i * 49 + 20);
                    }
                    if (this.forgeState[this.currentforgebookdata[this.forgebookstartindex + i] - 44] == 0) {
                        this.drawarmicon(this.currentforgebookdata[this.forgebookstartindex + i], 93, 48 + i * 49 + 20);
                        if (i == this.forgebookselectindex) {
                            this.canvas.drawColorString("" + this.iteminfo[this.currentforgebookdata[this.forgebookstartindex + this.forgebookselectindex]][0], 181, 61 + i * 49 + 20, 17, 15597319, 0);
                            this.drawitemiconcheck(93, 49 + i * 49 + 20, this.currentforgebookdata[this.forgebookstartindex + this.forgebookselectindex]);
                            continue;
                        }
                        this.canvas.drawColorString("" + this.iteminfo[this.currentforgebookdata[this.forgebookstartindex + i]][0], 181, 61 + i * 49 + 20, 17, 10333123, 0);
                        this.drawitemiconmembrane(93, 48 + i * 49 + 20, this.currentforgebookdata[this.forgebookstartindex + i]);
                        continue;
                    }
                    if (i == this.forgebookselectindex) {
                        this.canvas.drawColorString("\u672a\u5f00\u542f\u3002", 189, 30 + i * 49 + 49, 17, 15597319, 0);
                        continue;
                    }
                    this.canvas.drawColorString("\u672a\u5f00\u542f\u3002", 189, 30 + i * 49 + 49, 17, 10333123, 0);
                }
                this.drawjindutiaoV(63, 69, 8, 233, this.forgebookselectindex + this.forgebookstartindex, this.forgebookusetotal, (byte)1);
                this.drawStaticUi((short)125);
                if (this.forgeState[this.currentforgebookdata[this.forgebookstartindex + this.forgebookselectindex] - 44] == 0) {
                    this.drawforgedata(this.forgebookindex, this.currentseleckarmname);
                    this.drawForgeCondition();
                    this.drawlabel("\u953b\u9020\u57ce\u5e02", 337, 250, 4, 2);
                    if (this.armforgecity != null) {
                        this.canvas.drawColorString(this.armforgecity, 345, 271, 20, 0xFFFFFF, 0);
                    }
                }
                if (!this.isCommand) break;
                this.drawUiBack();
                switch (this.commandState) {
                    case 0: {
                        this.drawStaticUi((short)26);
                        this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                    }
                }
                break;
            }
        }
    }

    public void getforgecity(byte wname, byte index) {
        int i;
        this.armforgecity = "";
        int temp = 0;
        int total = 0;
        int skip = 0;
        for (i = 0; i < 4; ++i) {
            for (int k = 0; k < this.allForgeArm[i][index].length; ++k) {
                if (this.allForgeArm[i][index][k] != wname) continue;
                temp = (byte)(temp | 1 << i);
                total = (byte)(total + 1);
            }
        }
        if (total > 0) {
            for (i = 0; i < 4; ++i) {
                if ((temp & 1 << i) == 0) continue;
                if (skip > 0) {
                    this.armforgecity = this.armforgecity + "\u3001";
                }
                this.armforgecity = this.armforgecity + this.citynames[i];
                skip = (byte)(skip + 1);
            }
        }
    }

    private void forgebookprocess() {
        switch (this.forgebookstate) {
            case 0: {
                if (Control.IsKeyDown(4100)) {
                    this.forgebookindex = (byte)(this.forgebookindex - 1);
                    this.forgebookindex = (byte)(this.forgebookindex < 0 ? 2 : (int)this.forgebookindex);
                    break;
                }
                if (Control.IsKeyDown(8448)) {
                    this.forgebookindex = (byte)(this.forgebookindex + 1);
                    this.forgebookindex = this.forgebookindex > 2 ? (byte)0 : this.forgebookindex;
                    break;
                }
                if (Control.IsKeyDown(327712)) {
                    this.forgebookstate = 1;
                    this.forgebookstartindex = 0;
                    this.forgebookselectindex = 0;
                    this.currentforgebookdata = this.forgebookdata[this.forgebookindex];
                    if (this.forgeState[this.currentforgebookdata[this.forgebookstartindex + this.forgebookselectindex] - 44] == 0) {
                        this.currentseleckarmname = this.currentforgebookdata[this.forgebookstartindex + this.forgebookselectindex];
                        this.setForgeCondition(this.currentseleckarmname, (byte)2);
                        this.getforgecity(this.currentseleckarmname, this.forgebookindex);
                        if (this.forgebookindex == 2) {
                            this.setplusdatastr(this.currentseleckarmname);
                        }
                    }
                    switch (this.forgebookindex) {
                        case 0: 
                        case 1: {
                            this.forgebookusetotal = (byte)15;
                            break;
                        }
                        case 2: {
                            this.forgebookusetotal = (byte)36;
                        }
                    }
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                this.changeUiState((byte)1);
                break;
            }
            case 1: {
                if (!this.isCommand && (Control.IsKeyDown(4100) || Control.IsKeyHold(4100, 10))) {
                    if (this.forgebookselectindex > 0) {
                        this.forgebookselectindex = (byte)(this.forgebookselectindex - 1);
                    } else if (this.forgebookstartindex > 0) {
                        this.forgebookstartindex = (byte)(this.forgebookstartindex - 1);
                    }
                    this.currentforgebookdata = this.forgebookdata[this.forgebookindex];
                    if (this.forgeState[this.currentforgebookdata[this.forgebookstartindex + this.forgebookselectindex] - 44] != 0) break;
                    this.currentseleckarmname = this.currentforgebookdata[this.forgebookstartindex + this.forgebookselectindex];
                    this.setForgeCondition(this.currentseleckarmname, (byte)2);
                    this.getforgecity(this.currentseleckarmname, this.forgebookindex);
                    if (this.forgebookindex != 2) break;
                    this.setplusdatastr(this.currentseleckarmname);
                    break;
                }
                if (!this.isCommand && (Control.IsKeyDown(8448) || Control.IsKeyHold(8448, 10))) {
                    int total;
                    int n = total = this.forgebookusetotal < 5 ? (int)this.forgebookusetotal : 5;
                    if (this.forgebookselectindex < total - 1) {
                        this.forgebookselectindex = (byte)(this.forgebookselectindex + 1);
                    } else if (this.forgebookstartindex < this.forgebookusetotal - total) {
                        this.forgebookstartindex = (byte)(this.forgebookstartindex + 1);
                    }
                    this.currentforgebookdata = this.forgebookdata[this.forgebookindex];
                    if (this.forgeState[this.currentforgebookdata[this.forgebookstartindex + this.forgebookselectindex] - 44] != 0) break;
                    this.currentseleckarmname = this.currentforgebookdata[this.forgebookstartindex + this.forgebookselectindex];
                    this.setForgeCondition(this.currentseleckarmname, (byte)2);
                    this.getforgecity(this.currentseleckarmname, this.forgebookindex);
                    if (this.forgebookindex != 2) break;
                    this.setplusdatastr(this.currentseleckarmname);
                    break;
                }
                if (Control.IsKeyDown(327712)) {
                    if (this.isCommand) {
                        switch (this.commandState) {
                            case 0: {
                                this.finishCommand();
                            }
                        }
                        break;
                    }
                    this.currentforgebookdata = this.forgebookdata[this.forgebookindex];
                    if (this.forgeState[this.currentforgebookdata[this.forgebookstartindex + this.forgebookselectindex] - 44] == 0) {
                        this.currentseleckarmname = this.currentforgebookdata[this.forgebookstartindex + this.forgebookselectindex];
                        this.setForgeCondition(this.currentseleckarmname, (byte)2);
                        this.getforgecity(this.currentseleckarmname, this.forgebookindex);
                        if (this.forgebookindex != 2) break;
                        this.setplusdatastr(this.currentseleckarmname);
                        break;
                    }
                    this.commandStr = "\u88c5\u5907\u672a\u5f00\u542f";
                    this.setCommandShowTime();
                    this.startCommand();
                    this.changeCommandState((byte)0);
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                if (this.isCommand) {
                    switch (this.commandState) {
                        case 0: {
                            this.finishCommand();
                        }
                    }
                    break;
                }
                this.setforgebookData();
                this.forgebookstate = 0;
                break;
            }
            case 2: {
                if (!Control.IsKeyDown(131072)) break;
                this.forgebookstate = 1;
            }
        }
    }

    private void drawpackbox() {
        this.drawStaticUiBack((short)0);
        this.drawStaticUi((short)88);
        this.drawUiModule(266, LABEL_X[0][0] - 4, 41, 36, 0);
        this.drawStaticUi((short)124, 320, 10);
        this.drawStaticUi((short)16, 0, 360);
        this.scroll.drawscroll();
        for (byte i = 0; i < 3; i = (byte)(i + 1)) {
            if (i == this.packboxindex) {
                this.drawStaticUi((short)115, 320, 175 + this.packboxindex * 42);
                this.canvas.drawColorString(this.packboxoption[i], 320, 185 + i * 42, 17, 15597319, 0);
                continue;
            }
            this.drawStaticUi((short)114, 320, 175 + i * 42);
            this.canvas.drawColorString(this.packboxoption[i], 320, 185 + i * 42, 17, 10333123, 0);
        }
    }

    private void packboxkeyprocess() {
        if (Control.IsKeyDown(4100)) {
            this.packboxindex = (byte)(this.packboxindex - 1);
            this.packboxindex = (byte)(this.packboxindex < 0 ? 2 : (int)this.packboxindex);
        } else if (Control.IsKeyDown(8448)) {
            this.packboxindex = (byte)(this.packboxindex + 1);
            this.packboxindex = this.packboxindex > 2 ? (byte)0 : this.packboxindex;
        } else if (Control.IsKeyDown(327712)) {
            switch (this.packboxindex) {
                case 0: {
                    this.itemBoxSeletedIndex = 0;
                    this.itemBoxStartIndex = 0;
                    this.itemBoxUseTotal = this.itemPackNum;
                    if (this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8 < this.itemPack[0].length) {
                        this.setItemInfo1(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                    } else {
                        this.setItemInfo1((byte)-1);
                    }
                    this.changeUiState((byte)5);
                    break;
                }
                case 1: {
                    this.armBoxIndex = 0;
                    this.changeUiState((byte)6);
                    break;
                }
                case 2: {
                    this.rideBoxSeletedIndex = 0;
                    this.setRideInfo(this.rideBoxSeletedIndex);
                    this.changeUiState((byte)10);
                }
            }
        } else if (Control.IsKeyDown(131072)) {
            this.changeUiState((byte)1);
        }
    }

    public void drawmissionreward() {
        if (this.isshowmissionreward) {
            this.drawUiBack();
            if (this.isshowmissioncommand) {
                switch (this.missioncommandstate) {
                    case 0: {
                        this.drawStaticUi((short)26);
                        switch (this.showmissioncommandstate) {
                            case 0: 
                            case 1: {
                                this.canvas.drawZkString("$2\u80cc\u5305\u5df2\u6ee1", 320, 113, 17, this.canvas.zkData);
                                for (int i = 0; i < 3; ++i) {
                                    if (this.showmissioncommandindex == i) {
                                        this.drawChoiceBox(250 + i * 70, 142);
                                        this.canvas.drawColorString(this.showmissioncommanddata[i], 250 + i * 70, 143, 17, 15597319, 0);
                                        continue;
                                    }
                                    this.canvas.drawColorString(this.showmissioncommanddata[i], 250 + i * 70, 143, 17, 0xFFFFFF, 0);
                                }
                                this.canvas.drawColorString("\u70b9\u51fb\u9009\u9879", 320, 193, 17, 16775789, 0);
                            }
                        }
                        break;
                    }
                    case 1: {
                        this.drawStaticUi((short)19);
                        this.canvas.drawColorString("\u662f\u5426\u82b1\u8d391500 \u91d1\u94b1\u6269\u5145\u80cc\u5305\uff1f", 320, 133, 17, 0xFFFFFF, 0);
                        break;
                    }
                    case 2: {
                        this.drawStaticUi((short)26);
                        this.canvas.drawColorString(this.missioncommandStr, 320, 133, 17, 0xFFFFFF, 0);
                    }
                }
            } else {
                switch (this.missionrewardstate) {
                    case -1: {
                        this.canvas.gameUi.drawUiSprite((byte)3, 320, 180);
                        if (!this.isActionOver) break;
                        this.missionrewardstate = 0;
                        break;
                    }
                    case 0: {
                        this.drawStaticUi((short)52);
                        this.canvas.drawColorString(this.showmissionrewardname, 320, 133, 3, 16775789, 0);
                        this.canvas.drawColorString("\u5df2\u5b8c\u6210", 320, 163, 3, 0xFFFFFF, 0);
                        this.canvas.drawColorString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 320, 203, 17, 16775789, 0);
                        break;
                    }
                    case 1: {
                        this.drawStaticUi((short)52);
                        this.canvas.drawColorString("\u83b7\u5f97\u4efb\u52a1\u5956\u52b1\uff1a", 320, 148, 3, 16775789, 0);
                        this.canvas.drawColorString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 320, 203, 17, 16775789, 0);
                        break;
                    }
                    case 2: {
                        this.drawStaticUi((short)52);
                        switch (this.showmissionrewardtype[0]) {
                            case 0: 
                            case 1: {
                                this.drawarmicon(this.showmissionrewarddata[0], 240, 127);
                                this.canvas.drawColorString(this.iteminfo[this.showmissionrewarddata[0]][0] + " X " + this.showmissionrewarddata[1], 290, 148, 6, 0xFFFFFF, 0);
                                break;
                            }
                            case 2: {
                                this.drawarmicon(this.showmissionrewarddata[0], 240, 127);
                                this.canvas.drawColorString(this.iteminfo[this.showmissionrewarddata[0]][0] + " \u953b\u9020\u4e66", 290, 148, 6, 0xFFFFFF, 0);
                                break;
                            }
                            case 3: {
                                this.drawarmicon(76 + this.rideiconindex[this.showmissionrewarddata[0]], 240, 127);
                                this.canvas.drawColorString("\u5750\u9a91\uff1a" + this.canvas.rolem.rolename[this.rideiconindex[this.showmissionrewarddata[0]]], 290, 148, 6, 0xFFFFFF, 0);
                            }
                        }
                        this.canvas.drawColorString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 320, 203, 17, 16775789, 0);
                        break;
                    }
                    case 3: {
                        this.drawStaticUi((short)52);
                        switch (this.showmissionrewardtype[1]) {
                            case 0: 
                            case 1: {
                                this.drawarmicon(this.showmissionrewarddata[2], 240, 127);
                                this.canvas.drawColorString(this.iteminfo[this.showmissionrewarddata[2]][0] + " X " + this.showmissionrewarddata[3], 290, 148, 6, 0xFFFFFF, 0);
                                break;
                            }
                            case 2: {
                                this.drawarmicon(this.showmissionrewarddata[2], 240, 127);
                                this.canvas.drawColorString(this.iteminfo[this.showmissionrewarddata[2]][0] + " \u953b\u9020\u4e66", 290, 148, 6, 0xFFFFFF, 0);
                                break;
                            }
                            case 3: {
                                this.drawarmicon(76 + this.rideiconindex[this.showmissionrewarddata[2]], 240, 127);
                                this.canvas.drawColorString("\u5750\u9a91\uff1a" + this.canvas.rolem.rolename[this.rideiconindex[this.showmissionrewarddata[2]]], 290, 148, 6, 0xFFFFFF, 0);
                            }
                        }
                        this.canvas.drawColorString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 320, 203, 17, 16775789, 0);
                        break;
                    }
                    case 4: {
                        this.drawStaticUi((short)52);
                        int x = this.drawmoney(this.showmissionrewardmoney, 320, 143, 3);
                        this.drawUiModule(120, x - 2, 143, 10, 0);
                        this.canvas.drawColorString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 320, 203, 17, 16775789, 0);
                    }
                }
            }
            this.drawPlayerMoney();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void missionrewardkeyprocess() {
        if (this.isshowmissioncommand && this.missioncommandstate == 2 && --this.showmissionrewardtime <= 0) {
            this.isshowmissioncommand = false;
            if (this.canvas.sms.isshowsmsshop) {
                switch (this.canvas.sms.showsmsshoptype) {
                    case 0: {
                        this.canvas.sms.isshowsmsshop = false;
                        this.canvas.sms.smsShopSelectIndex = 0;
                        this.canvas.sms.changeSmsstate(0, this.uiState);
                        break;
                    }
                    case 1: {
                        if (this.canvas.rolem.playerLevel >= 50) break;
                        this.canvas.sms.smsShopSelectIndex = (byte)2;
                        this.canvas.sms.isshowsmsshop = false;
                        this.canvas.sms.changeSmsstate(0, this.uiState);
                    }
                }
            }
        }
        if (Control.IsKeyDown(1)) {
            if (this.isshowmissioncommand) return;
            switch (this.missionrewardstate) {
                case 0: {
                    if (this.showmissionrewarddata.length >= 2) {
                        this.missionrewardstate = 1;
                        return;
                    }
                    if (this.showmissionrewardmoney > 0) {
                        this.missionrewardstate = (byte)4;
                        return;
                    }
                    this.getcurrentreward();
                    this.isshowmissionreward = false;
                    return;
                }
                case 1: {
                    if (this.showmissionrewarddata.length >= 2) {
                        this.missionrewardstate = (byte)2;
                        return;
                    }
                    if (this.showmissionrewardmoney <= 0) return;
                    this.missionrewardstate = (byte)4;
                    return;
                }
                case 2: {
                    if (this.showmissionrewardtype[0] == 0) {
                        if (this.checkItemBox(this.itemPack, this.showmissionrewarddata[0], this.showmissionrewarddata[1])) {
                            if (this.showmissionrewarddata.length == 4) {
                                this.missionrewardstate = (byte)3;
                                return;
                            }
                            if (this.showmissionrewardmoney > 0) {
                                this.missionrewardstate = (byte)4;
                                return;
                            }
                            this.getcurrentreward();
                            this.isshowmissionreward = false;
                            return;
                        }
                        this.showmissioncommandindex = 0;
                        this.showmissioncommandstate = 0;
                        this.missioncommandstate = 0;
                        this.isshowmissioncommand = true;
                        return;
                    }
                    if (this.showmissionrewardtype[0] == 1) {
                        if (this.checkArmBox(this.showmissionrewarddata[0])) {
                            if (this.showmissionrewarddata.length == 4) {
                                this.missionrewardstate = (byte)3;
                                return;
                            }
                            if (this.showmissionrewardmoney > 0) {
                                this.missionrewardstate = (byte)4;
                                return;
                            }
                            this.getcurrentreward();
                            this.isshowmissionreward = false;
                            return;
                        }
                        this.showmissioncommandindex = 0;
                        this.showmissioncommandstate = 1;
                        this.missioncommandstate = 0;
                        this.isshowmissioncommand = true;
                        return;
                    }
                    if (this.showmissionrewarddata.length == 4) {
                        this.missionrewardstate = (byte)3;
                        return;
                    }
                    if (this.showmissionrewardmoney > 0) {
                        this.missionrewardstate = (byte)4;
                        return;
                    }
                    this.getcurrentreward();
                    this.isshowmissionreward = false;
                    return;
                }
                case 3: {
                    if (this.showmissionrewardtype[1] == 0) {
                        if (this.checkItemBox(this.itemPack, this.showmissionrewarddata[2], this.showmissionrewarddata[3])) {
                            if (this.showmissionrewardmoney > 0) {
                                this.missionrewardstate = (byte)4;
                                return;
                            }
                            this.getcurrentreward();
                            this.isshowmissionreward = false;
                            return;
                        }
                        this.showmissioncommandindex = 0;
                        this.showmissioncommandstate = 0;
                        this.missioncommandstate = 0;
                        this.isshowmissioncommand = true;
                        return;
                    }
                    if (this.showmissionrewardtype[1] == 1) {
                        if (this.checkArmBox(this.showmissionrewarddata[2])) {
                            if (this.showmissionrewardmoney > 0) {
                                this.missionrewardstate = (byte)4;
                                return;
                            }
                            this.getcurrentreward();
                            this.isshowmissionreward = false;
                            return;
                        }
                        this.showmissioncommandindex = 0;
                        this.showmissioncommandstate = 1;
                        this.missioncommandstate = 0;
                        this.isshowmissioncommand = true;
                        return;
                    }
                    if (this.showmissionrewardmoney > 0) {
                        this.missionrewardstate = (byte)4;
                        return;
                    }
                    this.getcurrentreward();
                    this.isshowmissionreward = false;
                    return;
                }
                case 4: {
                    this.getcurrentreward();
                    this.isshowmissionreward = false;
                }
            }
            return;
        } else if (Control.IsKeyDown(262176)) {
            if (!this.isshowmissioncommand) return;
            block11 : switch (this.missioncommandstate) {
                case 0: {
                    switch (this.showmissioncommandindex) {
                        case 0: {
                            this.missioncommandstate = 1;
                            return;
                        }
                        case 1: {
                            switch (this.showmissioncommandstate) {
                                case 0: {
                                    this.cleaneventlastgamestate = this.canvas.gamestate;
                                    this.canvas.changeGameState(7);
                                    this.changeUiState((byte)21);
                                    this.itemBoxSeletedIndex = 0;
                                    this.itemBoxStartIndex = 0;
                                    this.itemBoxUseTotal = this.itemPackNum;
                                    if (this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8 < this.itemPack[0].length) {
                                        this.setItemInfo1(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                                    } else {
                                        this.setItemInfo1((byte)-1);
                                    }
                                    this.isshowmissionreward = false;
                                    return;
                                }
                                case 1: {
                                    this.cleaneventlastgamestate = this.canvas.gamestate;
                                    this.canvas.changeGameState(7);
                                    this.changeUiState((byte)22);
                                    block24 : switch (this.missionrewardstate) {
                                        case 2: {
                                            switch (this.canvas.rolem.setArmType(this.showmissionrewarddata[0])) {
                                                case 0: {
                                                    this.armBoxIndex = 0;
                                                    this.armBoxSeletedIndex = 0;
                                                    this.armBoxStartIndex = 0;
                                                    this.armBoxUseTotal = this.weaponPackUseNum;
                                                    this.setWeaponInfo(this.weaponPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                                                    this.showweapony = 0;
                                                    this.showweaponvdir = 1;
                                                    this.loadweaponimagetime = 0;
                                                    this.loadweaponname = this.weaponPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex];
                                                    break;
                                                }
                                                case 1: {
                                                    this.armBoxIndex = 1;
                                                    this.armBoxSeletedIndex = 0;
                                                    this.armBoxStartIndex = 0;
                                                    this.armBoxUseTotal = this.suitPackUseNum;
                                                    this.setWeaponInfo(this.suitPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                                                    break;
                                                }
                                                case 2: {
                                                    this.armBoxIndex = (byte)2;
                                                    this.armBoxSeletedIndex = 0;
                                                    this.armBoxStartIndex = 0;
                                                    this.armBoxUseTotal = this.plusPackUseNum;
                                                    this.setWeaponInfo(this.plusPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                                                }
                                            }
                                            break;
                                        }
                                        case 3: {
                                            switch (this.canvas.rolem.setArmType(this.showmissionrewarddata[2])) {
                                                case 0: {
                                                    this.armBoxIndex = 0;
                                                    this.armBoxSeletedIndex = 0;
                                                    this.armBoxStartIndex = 0;
                                                    this.armBoxUseTotal = this.weaponPackUseNum;
                                                    this.setWeaponInfo(this.weaponPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                                                    this.showweapony = 0;
                                                    this.showweaponvdir = 1;
                                                    this.loadweaponimagetime = 0;
                                                    this.loadweaponname = this.weaponPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex];
                                                    break block24;
                                                }
                                                case 1: {
                                                    this.armBoxIndex = 1;
                                                    this.armBoxSeletedIndex = 0;
                                                    this.armBoxStartIndex = 0;
                                                    this.armBoxUseTotal = this.suitPackUseNum;
                                                    this.setWeaponInfo(this.suitPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                                                    break block24;
                                                }
                                                case 2: {
                                                    this.armBoxIndex = (byte)2;
                                                    this.armBoxSeletedIndex = 0;
                                                    this.armBoxStartIndex = 0;
                                                    this.armBoxUseTotal = this.plusPackUseNum;
                                                    this.setWeaponInfo(this.plusPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                                                }
                                            }
                                        }
                                    }
                                    this.isshowmissionreward = false;
                                }
                            }
                            return;
                        }
                        case 2: {
                            this.isshowmissioncommand = false;
                            switch (this.missionrewardstate) {
                                case 2: {
                                    if (this.showmissionrewarddata.length == 4) {
                                        this.missionrewardstate = (byte)3;
                                        return;
                                    }
                                    if (this.showmissionrewardmoney > 0) {
                                        this.missionrewardstate = (byte)4;
                                        return;
                                    }
                                    this.getcurrentreward();
                                    this.isshowmissionreward = false;
                                    return;
                                }
                                case 3: {
                                    if (this.showmissionrewardmoney > 0) {
                                        this.missionrewardstate = (byte)4;
                                        return;
                                    }
                                    this.getcurrentreward();
                                    this.isshowmissionreward = false;
                                }
                            }
                        }
                    }
                    return;
                }
                case 2: {
                    this.isshowmissioncommand = false;
                    if (!this.canvas.sms.isshowsmsshop) return;
                    switch (this.canvas.sms.showsmsshoptype) {
                        case 0: {
                            this.canvas.sms.isshowsmsshop = false;
                            this.canvas.sms.smsShopSelectIndex = 0;
                            this.canvas.sms.changeSmsstate(0, this.uiState);
                            break block11;
                        }
                        case 1: {
                            if (this.canvas.rolem.playerLevel >= 50) return;
                            this.canvas.sms.smsShopSelectIndex = (byte)2;
                            this.canvas.sms.isshowsmsshop = false;
                            this.canvas.sms.changeSmsstate(0, this.uiState);
                        }
                    }
                }
            }
            return;
        } else if (Control.IsKeyDown(65536)) {
            if (!this.isshowmissioncommand) return;
            block46 : switch (this.missioncommandstate) {
                case 1: {
                    if (this.itemPackNum >= 56) {
                        this.missioncommandStr = "\u80cc\u5305\u5df2\u589e\u81f3\u6700\u5927\u3002";
                        this.showmissionrewardtime = 20;
                        this.missioncommandstate = (byte)2;
                        return;
                    }
                    if (!this.checkMoney(1500)) {
                        this.canvas.sms.setshowsmsshop(0);
                        this.missioncommandStr = "\u91d1\u94b1\u4e0d\u8db3";
                        this.showmissionrewardtime = 20;
                        this.missioncommandstate = (byte)2;
                        return;
                    }
                    this.addMoney(-1500);
                    this.missioncommandStr = this.useItem(this.canvas.rolem.fplayerbox[0]);
                    this.showmissionrewardtime = 20;
                    this.missioncommandstate = (byte)2;
                    return;
                }
                case 2: {
                    this.isshowmissioncommand = false;
                    if (!this.canvas.sms.isshowsmsshop) return;
                    switch (this.canvas.sms.showsmsshoptype) {
                        case 0: {
                            this.canvas.sms.isshowsmsshop = false;
                            this.canvas.sms.smsShopSelectIndex = 0;
                            this.canvas.sms.changeSmsstate(0, this.uiState);
                            break block46;
                        }
                        case 1: {
                            if (this.canvas.rolem.playerLevel >= 50) return;
                            this.canvas.sms.smsShopSelectIndex = (byte)2;
                            this.canvas.sms.isshowsmsshop = false;
                            this.canvas.sms.changeSmsstate(0, this.uiState);
                        }
                    }
                }
            }
            return;
        } else if (Control.IsKeyDown(131072)) {
            block54 : switch (this.missioncommandstate) {
                case 1: {
                    this.missioncommandstate = 0;
                    return;
                }
                case 2: {
                    this.isshowmissioncommand = false;
                    if (!this.canvas.sms.isshowsmsshop) return;
                    switch (this.canvas.sms.showsmsshoptype) {
                        case 0: {
                            this.canvas.sms.isshowsmsshop = false;
                            this.canvas.sms.smsShopSelectIndex = 0;
                            this.canvas.sms.changeSmsstate(0, this.uiState);
                            break block54;
                        }
                        case 1: {
                            if (this.canvas.rolem.playerLevel >= 50) return;
                            this.canvas.sms.smsShopSelectIndex = (byte)2;
                            this.canvas.sms.isshowsmsshop = false;
                            this.canvas.sms.changeSmsstate(0, this.uiState);
                        }
                    }
                }
            }
            return;
        } else if (Control.IsKeyDown(16400)) {
            this.showmissioncommandindex = (byte)(this.showmissioncommandindex - 1);
            this.showmissioncommandindex = (byte)(this.showmissioncommandindex < 0 ? 2 : (int)this.showmissioncommandindex);
            return;
        } else {
            if (!Control.IsKeyDown(32832)) return;
            this.showmissioncommandindex = (byte)(this.showmissioncommandindex + 1);
            this.showmissioncommandindex = this.showmissioncommandindex > 2 ? (byte)0 : this.showmissioncommandindex;
        }
    }

    private void drawCleanItemBox() {
        this.drawItemBoxItem();
        if (this.isCommand) {
            this.drawItemBoxCommand();
        }
    }

    private void cleanItemBoxKeyProcess() {
        if (Control.IsKeyDown(4100) || Control.IsKeyHold(4100, 10)) {
            if (this.isCommand) {
                switch (this.commandState) {
                    case 0: {
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex - 1);
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex < 0 ? 2 : (int)this.commandSeletedIndex);
                        break;
                    }
                    case 1: {
                        this.processNeedNum(1, this.itemPack[1][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                        break;
                    }
                    case 5: {
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex - 1);
                        this.commandSeletedIndex = this.commandSeletedIndex < 0 ? (byte)1 : this.commandSeletedIndex;
                    }
                }
            } else {
                byte tempitemBoxSeletedIndex = this.itemBoxSeletedIndex;
                byte tempitemBoxStartIndex = this.itemBoxStartIndex;
                if (this.itemBoxSeletedIndex / 8 > 0) {
                    this.itemBoxSeletedIndex = (byte)(this.itemBoxSeletedIndex - 8);
                } else if (this.itemBoxStartIndex > 0) {
                    this.itemBoxStartIndex = (byte)(this.itemBoxStartIndex - 1);
                }
                if (this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8] <= -1) {
                    this.itemBoxSeletedIndex = tempitemBoxSeletedIndex;
                    this.itemBoxStartIndex = tempitemBoxStartIndex;
                }
                if (this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8 < this.itemPack[0].length) {
                    this.setItemInfo1(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                } else {
                    this.setItemInfo1((byte)-1);
                }
            }
        } else if (Control.IsKeyDown(8448) || Control.IsKeyHold(8448, 10)) {
            if (this.isCommand) {
                switch (this.commandState) {
                    case 0: {
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex + 1);
                        this.commandSeletedIndex = this.commandSeletedIndex > 2 ? (byte)0 : this.commandSeletedIndex;
                        break;
                    }
                    case 1: {
                        this.processNeedNum(-1, this.itemPack[1][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                        break;
                    }
                    case 5: {
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex + 1);
                        this.commandSeletedIndex = this.commandSeletedIndex > 1 ? (byte)0 : this.commandSeletedIndex;
                    }
                }
            } else {
                byte tempitemBoxSeletedIndex = this.itemBoxSeletedIndex;
                byte tempitemBoxStartIndex = this.itemBoxStartIndex;
                if (this.itemBoxSeletedIndex / 8 < 1) {
                    this.itemBoxSeletedIndex = (byte)(this.itemBoxSeletedIndex + 8);
                } else if (this.itemBoxStartIndex < this.itemBoxUseTotal / 8 - 2) {
                    this.itemBoxStartIndex = (byte)(this.itemBoxStartIndex + 1);
                }
                if (this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8] <= -1) {
                    this.itemBoxSeletedIndex = tempitemBoxSeletedIndex;
                    this.itemBoxStartIndex = tempitemBoxStartIndex;
                }
                if (this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8 < this.itemPack[0].length) {
                    this.setItemInfo1(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                } else {
                    this.setItemInfo1((byte)-1);
                }
            }
        } else if (Control.IsKeyDown(16400) || Control.IsKeyHold(16400, 10)) {
            if (this.isCommand) {
                switch (this.commandState) {
                    case 6: {
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex - 1);
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex < 0 ? 2 : (int)this.commandSeletedIndex);
                    }
                }
            } else {
                byte tempitemBoxSeletedIndex = this.itemBoxSeletedIndex;
                byte tempitemBoxStartIndex = this.itemBoxStartIndex;
                this.itemBoxSeletedIndex = this.itemBoxSeletedIndex % 8 == 0 ? (byte)(this.itemBoxSeletedIndex + 7) : (byte)(this.itemBoxSeletedIndex - 1);
                if (this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8] <= -1) {
                    this.itemBoxSeletedIndex = tempitemBoxSeletedIndex;
                    this.itemBoxStartIndex = tempitemBoxStartIndex;
                }
                if (this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8 < this.itemPack[0].length) {
                    this.setItemInfo1(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                } else {
                    this.setItemInfo1((byte)-1);
                }
            }
        } else if (Control.IsKeyDown(32832) || Control.IsKeyHold(32832, 10)) {
            if (this.isCommand) {
                switch (this.commandState) {
                    case 6: {
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex + 1);
                        this.commandSeletedIndex = this.commandSeletedIndex > 2 ? (byte)0 : this.commandSeletedIndex;
                    }
                }
            } else {
                byte tempitemBoxSeletedIndex = this.itemBoxSeletedIndex;
                byte tempitemBoxStartIndex = this.itemBoxStartIndex;
                this.itemBoxSeletedIndex = this.itemBoxSeletedIndex % 8 == 7 ? (byte)(this.itemBoxSeletedIndex - 7) : (byte)(this.itemBoxSeletedIndex + 1);
                if (this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8] <= -1) {
                    this.itemBoxSeletedIndex = tempitemBoxSeletedIndex;
                    this.itemBoxStartIndex = tempitemBoxStartIndex;
                }
                if (this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8 < this.itemPack[0].length) {
                    this.setItemInfo1(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                } else {
                    this.setItemInfo1((byte)-1);
                }
            }
        } else if (Control.IsKeyDown(327712)) {
            if (this.isCommand) {
                block16 : switch (this.commandState) {
                    case 0: {
                        switch (this.commandSeletedIndex) {
                            case 0: {
                                if (this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8] == 9) {
                                    this.commandSeletedIndex = 0;
                                    this.changeCommandState((byte)6);
                                    break block16;
                                }
                                this.changeCommandState((byte)4);
                                break block16;
                            }
                            case 1: {
                                this.needNum = 1;
                                this.changeCommandState((byte)1);
                                break block16;
                            }
                            case 2: {
                                this.finishCommand();
                            }
                        }
                        break;
                    }
                    case 1: {
                        this.changeCommandState((byte)2);
                        break;
                    }
                    case 2: {
                        this.subtractItem(this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8, this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8], (byte)this.needNum);
                        if (this.addState == -2) {
                            if (this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8 < this.itemPack[0].length) {
                                this.setItemInfo1(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                            } else {
                                this.setItemInfo1((byte)-1);
                            }
                        }
                        this.setCommandShowTime();
                        this.commandStr = "\u5df2\u4e22\u5f03\uff01";
                        this.changeCommandState((byte)3);
                        break;
                    }
                    case 3: {
                        this.finishCommand();
                        break;
                    }
                    case 4: {
                        this.commandStr = this.useItem(this.canvas.rolem.fplayerbox[0], this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8], this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8);
                        if (this.addState == -2) {
                            if (this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8 < this.itemPack[0].length) {
                                this.setItemInfo1(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                            } else {
                                this.setItemInfo1((byte)-1);
                            }
                        }
                        this.setCommandShowTime();
                        this.changeCommandState((byte)3);
                        break;
                    }
                    case 5: {
                        switch (this.commandSeletedIndex) {
                            case 0: {
                                this.needNum = 1;
                                this.changeCommandState((byte)1);
                                break block16;
                            }
                            case 1: {
                                this.finishCommand();
                            }
                        }
                        break;
                    }
                    case 6: {
                        if (!this.canvas.rolem.isrideopen(this.commandSeletedIndex)) break;
                        this.useplayerindex = this.commandSeletedIndex;
                        this.commandStr = this.useItem(this.canvas.rolem.fplayerbox[0], this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8], this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8);
                        if (this.addState == -2) {
                            if (this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8 < this.itemPack[0].length) {
                                this.setItemInfo1(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8]);
                            } else {
                                this.setItemInfo1((byte)-1);
                            }
                        }
                        this.setCommandShowTime();
                        this.changeCommandState((byte)3);
                    }
                }
            } else if (this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8] != -1) {
                if (this.iscanuse(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8])) {
                    this.startCommand();
                    this.changeCommandState((byte)0);
                    this.commandSeletedIndex = 0;
                } else {
                    this.startCommand();
                    this.changeCommandState((byte)5);
                    this.commandSeletedIndex = 0;
                }
            }
        } else if (Control.IsKeyDown(131072)) {
            if (this.isCommand) {
                switch (this.commandState) {
                    case 0: 
                    case 3: 
                    case 4: 
                    case 5: {
                        this.finishCommand();
                        break;
                    }
                    case 1: {
                        if (this.iscanuse(this.itemPack[0][this.itemBoxSeletedIndex + this.itemBoxStartIndex * 8])) {
                            this.changeCommandState((byte)0);
                            this.commandSeletedIndex = 0;
                            break;
                        }
                        this.changeCommandState((byte)5);
                        this.commandSeletedIndex = 0;
                        break;
                    }
                    case 2: {
                        this.changeCommandState((byte)1);
                        break;
                    }
                    case 6: {
                        this.commandSeletedIndex = 0;
                        this.changeCommandState((byte)0);
                    }
                }
            } else {
                this.canvas.changeGameState(this.cleaneventlastgamestate);
                this.isshowmissionreward = true;
                if (this.itemPackUseNum < this.itemPackNum) {
                    this.isshowmissioncommand = false;
                }
            }
        }
    }

    private void drawCleanArmBox() {
        this.drawStaticUiBack((short)0);
        this.drawStaticUi(this.armBoxIndex);
        if (this.armBoxUseTotal > 0) {
            if (this.armBoxIndex == 0) {
                this.drawStaticUi((short)123);
            } else {
                this.drawStaticUi((short)123);
            }
        }
        this.drawlabels(this.armboxdata, this.armBoxIndex);
        this.drawArmBoxItem();
        if (this.isCommand) {
            this.drawUiBack();
            switch (this.commandState) {
                case 0: 
                case 2: {
                    this.drawStaticUi((short)126);
                    for (int i = 0; i < this.armBoxCommand[this.commandState].length; i = (int)((byte)(i + 1))) {
                        if (i == this.commandSeletedIndex) {
                            this.drawChoiceBox(320, 125 + this.commandSeletedIndex * 45);
                            this.canvas.drawColorString(this.armBoxCommand[this.commandState][i], 320, 128 + this.commandSeletedIndex * 45, 17, 15597319, 0);
                            continue;
                        }
                        this.canvas.drawColorString(this.armBoxCommand[this.commandState][i], 320, 128 + i * 45, 17, 0xFFFFFF, 0);
                    }
                    break;
                }
                case 1: {
                    this.drawStaticUi((short)126);
                    for (int i = 0; i < this.armBoxCommand[this.commandState].length; i = (int)((byte)(i + 1))) {
                        if (i == this.commandSeletedIndex) {
                            this.drawChoiceBox(320, 144 + this.commandSeletedIndex * 45);
                            this.canvas.drawColorString(this.armBoxCommand[this.commandState][i], 320, 147 + this.commandSeletedIndex * 45, 17, 15597319, 0);
                            continue;
                        }
                        this.canvas.drawColorString(this.armBoxCommand[this.commandState][i], 320, 147 + i * 45, 17, 0xFFFFFF, 0);
                    }
                    break;
                }
                case 3: {
                    this.drawStaticUi((short)126);
                    this.canvas.drawColorString("\u5df2\u88c5\u5907", 320, 144, 33, 15597319, 0);
                    for (int i = 0; i < this.armBoxCommand[this.commandState].length; i = (int)((byte)(i + 1))) {
                        if (i == this.commandSeletedIndex) {
                            this.drawChoiceBox(320, 190 + this.commandSeletedIndex * 45);
                            this.canvas.drawColorString(this.armBoxCommand[this.commandState][i], 320, 193 + this.commandSeletedIndex * 45, 17, 15597319, 0);
                            continue;
                        }
                        this.canvas.drawColorString(this.armBoxCommand[this.commandState][i], 320, 193 + i * 45, 17, 0xFFFFFF, 0);
                    }
                    break;
                }
                case 4: {
                    this.drawStaticUi((short)26);
                    this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                    break;
                }
                case 5: {
                    this.changeWeaponImage(this.changeWeaponName);
                    this.drawminiloading(this.changeWeaponTime, 3);
                    if (this.changeWeaponTime != 3) break;
                    this.commandStr = "\u88c5\u5907\u6210\u529f\u3002";
                    this.setCommandShowTime();
                    this.changeCommandState((byte)4);
                }
            }
        }
    }

    public void drawCurrentItemInfo(int itemType, int itemIndex) {
        switch (itemType) {
            case 0: {
                this.drawWeaponData1(this.weaponPack[0][itemIndex], this.canvas.rolem.setWeaponType(this.weaponPack[0][itemIndex]), this.uiState == 4 || this.uiState == 6 || this.uiState == 22);
                this.loadweaponname = this.weaponPack[0][itemIndex];
                this.drawWeapon(530, 129, this.canvas.rolem.setWeaponType(this.loadweaponname));
                String itemNameTemp = this.iteminfo[this.weaponPack[0][itemIndex]][0];
                int weaponIndeTemp = 0;
                this.drawadornline(530 + (itemNameTemp.length() <= 2 ? -4 : 4) - ((itemNameTemp.length() <= 2 ? itemNameTemp.length() : itemNameTemp.length()) * 17 >> 1), 186, itemNameTemp.length() <= 2 ? itemNameTemp.length() + 1 : itemNameTemp.length(), 0);
                byte type = this.canvas.rolem.setWeaponType(this.weaponPack[0][itemIndex]);
                switch (type) {
                    case 0: {
                        weaponIndeTemp = this.weaponPack[0][itemIndex] + 291 - 44;
                        break;
                    }
                    case 1: {
                        weaponIndeTemp = this.weaponPack[0][itemIndex] + 306 - 59;
                        break;
                    }
                    case 2: {
                        weaponIndeTemp = this.weaponPack[0][itemIndex] + 321 - 74;
                    }
                }
                this.drawUiModule(weaponIndeTemp, 530 + (itemNameTemp.length() <= 2 ? -4 : 4) - (itemNameTemp.length() * 16 >> 1), 204, 36, 0);
                break;
            }
            case 1: {
                this.drawSuitData(this.suitPack[0][itemIndex], this.uiState == 4 || this.uiState == 6 || this.uiState == 22);
                this.drawUiModule(3, 508, 143, 20, 0);
                this.drawUiModule(0, 509, 144, 20, 0);
                this.drawarmicon(this.suitPack[0][itemIndex], 510, 143);
                this.drawitemiconcheck(510, 143, this.suitPack[0][itemIndex]);
                String itemNameTemp = this.iteminfo[this.suitPack[0][itemIndex]][0];
                this.drawadornline(530 + (itemNameTemp.length() <= 2 ? -4 : 4) - ((itemNameTemp.length() <= 2 ? itemNameTemp.length() : itemNameTemp.length()) * 17 >> 1), 186, itemNameTemp.length() <= 2 ? itemNameTemp.length() + 1 : itemNameTemp.length(), 0);
                this.drawUiModule(this.suitPack[0][itemIndex] + 325 - 78, 530 + (itemNameTemp.length() <= 2 ? -4 : 4) - (itemNameTemp.length() * 16 >> 1), 204, 36, 0);
                break;
            }
            case 2: {
                this.drawPlusData1(itemIndex);
                this.drawUiModule(3, 508, 143, 20, 0);
                this.drawUiModule(0, 509, 144, 20, 0);
                this.drawarmicon(this.plusPack[0][itemIndex], 510, 143);
                this.drawitemiconcheck(510, 143, this.plusPack[0][itemIndex]);
                String itemNameTemp = this.iteminfo[this.plusPack[0][itemIndex]][0];
                this.drawadornline(530 + (itemNameTemp.length() <= 2 ? -4 : 4) - ((itemNameTemp.length() <= 2 ? itemNameTemp.length() : itemNameTemp.length()) * 17 >> 1), 186, itemNameTemp.length() <= 2 ? itemNameTemp.length() + 1 : itemNameTemp.length(), 0);
                this.drawUiModule(this.plusPack[0][itemIndex] + 335 - 88, 530 + (itemNameTemp.length() <= 2 ? -4 : 4) - (itemNameTemp.length() * 16 >> 1), 204, 36, 0);
            }
        }
    }

    private void cleanArmBoxKeyProcess() {
        this.loadWeaponImages();
        if (Control.IsKeyDown(4100)) {
            if (this.isCommand && this.commandState >= 0 && this.commandState < this.ARMBOXCOMMANDTOTAL.length) {
                this.commandSeletedIndex = (byte)(this.commandSeletedIndex - 1);
                this.commandSeletedIndex = this.commandSeletedIndex < 0 ? this.ARMBOXCOMMANDTOTAL[this.commandState] : this.commandSeletedIndex;
            }
        } else if (Control.IsKeyDown(8448)) {
            if (this.isCommand && this.commandState >= 0 && this.commandState < this.ARMBOXCOMMANDTOTAL.length) {
                this.commandSeletedIndex = (byte)(this.commandSeletedIndex + 1);
                this.commandSeletedIndex = this.commandSeletedIndex > this.ARMBOXCOMMANDTOTAL[this.commandState] ? (byte)0 : this.commandSeletedIndex;
            }
        } else if (Control.IsKeyDown(16400) || Control.IsKeyHold(16400, 10)) {
            if (!this.isCommand) {
                if (this.armBoxSeletedIndex > 0) {
                    this.armBoxSeletedIndex = (byte)(this.armBoxSeletedIndex - 1);
                } else if (this.armBoxStartIndex > 0) {
                    this.armBoxStartIndex = (byte)(this.armBoxStartIndex - 1);
                }
                this.showweapony = 0;
                this.showweaponvdir = 1;
                this.loadweaponimagetime = 0;
                this.loadweaponname = this.weaponPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex];
                if (this.armBoxSeletedIndex + this.armBoxStartIndex < this.armBoxUseTotal) {
                    switch (this.armBoxIndex) {
                        case 0: {
                            this.setWeaponInfo(this.weaponPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                            break;
                        }
                        case 1: {
                            this.setWeaponInfo(this.suitPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                            break;
                        }
                        case 2: {
                            this.setWeaponInfo(this.plusPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                        }
                    }
                }
            }
        } else if (Control.IsKeyDown(32832) || Control.IsKeyHold(32832, 10)) {
            if (!this.isCommand) {
                int total;
                int n = total = this.armBoxUseTotal < 9 ? (int)this.armBoxUseTotal : 9;
                if (this.armBoxSeletedIndex < total - 1) {
                    this.armBoxSeletedIndex = (byte)(this.armBoxSeletedIndex + 1);
                } else if (this.armBoxStartIndex < this.armBoxUseTotal - total) {
                    this.armBoxStartIndex = (byte)(this.armBoxStartIndex + 1);
                }
                this.showweapony = 0;
                this.showweaponvdir = 1;
                this.loadweaponimagetime = 0;
                this.loadweaponname = this.weaponPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex];
                if (this.armBoxSeletedIndex + this.armBoxStartIndex < this.armBoxUseTotal) {
                    switch (this.armBoxIndex) {
                        case 0: {
                            this.setWeaponInfo(this.weaponPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                            break;
                        }
                        case 1: {
                            this.setWeaponInfo(this.suitPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                            break;
                        }
                        case 2: {
                            this.setWeaponInfo(this.plusPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                        }
                    }
                }
            }
        } else if (Control.IsKeyDown(327712)) {
            if (this.isCommand) {
                block10 : switch (this.commandState) {
                    case 0: {
                        block16 : switch (this.armBoxIndex) {
                            case 0: {
                                switch (this.commandSeletedIndex) {
                                    case 0: {
                                        this.changeWeapon((byte)(this.armBoxStartIndex + this.armBoxSeletedIndex));
                                        this.changeWeaponName = this.weaponPack[0][this.armBoxStartIndex + this.armBoxSeletedIndex];
                                        this.changeWeaponTime = 0;
                                        this.changeCommandState((byte)5);
                                        break;
                                    }
                                    case 1: {
                                        this.subtractWeapon(this.armBoxStartIndex + this.armBoxSeletedIndex);
                                        this.armBoxUseTotal = this.weaponPackUseNum;
                                        this.setCommandShowTime();
                                        this.commandStr = "\u4e22\u5f03\u6210\u529f";
                                        this.changeCommandState((byte)4);
                                        break;
                                    }
                                    case 2: {
                                        this.finishCommand();
                                    }
                                }
                                break;
                            }
                            case 1: {
                                switch (this.commandSeletedIndex) {
                                    case 0: {
                                        this.changeSuit((byte)(this.armBoxStartIndex + this.armBoxSeletedIndex));
                                        this.setCommandShowTime();
                                        this.commandStr = "\u88c5\u5907\u6210\u529f";
                                        this.changeCommandState((byte)4);
                                        break;
                                    }
                                    case 1: {
                                        this.subtractSuit(this.armBoxStartIndex + this.armBoxSeletedIndex);
                                        this.armBoxUseTotal = this.suitPackUseNum;
                                        this.setCommandShowTime();
                                        this.commandStr = "\u4e22\u5f03\u6210\u529f";
                                        this.changeCommandState((byte)4);
                                        break;
                                    }
                                    case 2: {
                                        this.finishCommand();
                                    }
                                }
                                break;
                            }
                            case 2: {
                                switch (this.commandSeletedIndex) {
                                    case 0: {
                                        this.commandSeletedIndex = 0;
                                        this.changeCommandState((byte)2);
                                        break block16;
                                    }
                                    case 1: {
                                        this.subtractPlus(this.armBoxStartIndex + this.armBoxSeletedIndex);
                                        this.armBoxUseTotal = this.plusPackUseNum;
                                        this.setCommandShowTime();
                                        this.commandStr = "\u4e22\u5f03\u6210\u529f";
                                        this.changeCommandState((byte)4);
                                        break block16;
                                    }
                                    case 2: {
                                        this.finishCommand();
                                    }
                                }
                            }
                        }
                        this.showweapony = 0;
                        this.showweaponvdir = 1;
                        this.loadweaponimagetime = 0;
                        switch (this.armBoxIndex) {
                            case 0: {
                                this.setWeaponInfo(this.weaponPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                                this.loadweaponname = this.weaponPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex];
                                break block10;
                            }
                            case 1: {
                                this.setWeaponInfo(this.suitPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                                break block10;
                            }
                            case 2: {
                                this.setWeaponInfo(this.plusPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex], (byte)1);
                            }
                        }
                        break;
                    }
                    case 1: {
                        switch (this.commandSeletedIndex) {
                            case 0: {
                                this.putOffPlus(this.plusPack[1][this.armBoxStartIndex + this.armBoxSeletedIndex]);
                                this.setCommandShowTime();
                                this.commandStr = "\u5378\u4e0b\u6210\u529f";
                                this.changeCommandState((byte)4);
                                break block10;
                            }
                            case 1: {
                                this.finishCommand();
                            }
                        }
                        break;
                    }
                    case 2: {
                        switch (this.commandSeletedIndex) {
                            case 0: 
                            case 1: {
                                if (this.isPutOnPlus(this.commandSeletedIndex)) {
                                    this.changePlus(this.commandSeletedIndex, (byte)(this.armBoxStartIndex + this.armBoxSeletedIndex));
                                } else {
                                    this.putOnPlus(this.commandSeletedIndex, (byte)(this.armBoxStartIndex + this.armBoxSeletedIndex));
                                }
                                this.setCommandShowTime();
                                this.commandStr = "\u88c5\u5907\u6210\u529f";
                                this.changeCommandState((byte)4);
                                break block10;
                            }
                            case 2: {
                                this.changeCommandState((byte)0);
                            }
                        }
                        break;
                    }
                    case 3: 
                    case 4: {
                        this.finishCommand();
                    }
                }
            } else {
                switch (this.armBoxIndex) {
                    case 0: {
                        if (this.weaponPack[0][this.armBoxStartIndex + this.armBoxSeletedIndex] == -1) break;
                        this.loadweaponname = this.weaponPack[0][this.armBoxSeletedIndex + this.armBoxStartIndex];
                        if (this.weaponPack[1][this.armBoxStartIndex + this.armBoxSeletedIndex] == -1) {
                            this.startCommand();
                            this.commandSeletedIndex = 0;
                            this.changeCommandState((byte)0);
                            break;
                        }
                        this.startCommand();
                        this.commandSeletedIndex = 0;
                        this.changeCommandState((byte)3);
                        break;
                    }
                    case 1: {
                        if (this.suitPack[0][this.armBoxStartIndex + this.armBoxSeletedIndex] == -1) break;
                        if (this.suitPack[1][this.armBoxStartIndex + this.armBoxSeletedIndex] == -1) {
                            this.startCommand();
                            this.commandSeletedIndex = 0;
                            this.changeCommandState((byte)0);
                            break;
                        }
                        this.startCommand();
                        this.commandSeletedIndex = 0;
                        this.changeCommandState((byte)3);
                        break;
                    }
                    case 2: {
                        if (this.plusPack[0][this.armBoxStartIndex + this.armBoxSeletedIndex] == -1) break;
                        if (this.plusPack[1][this.armBoxStartIndex + this.armBoxSeletedIndex] == -1) {
                            this.startCommand();
                            this.commandSeletedIndex = 0;
                            this.changeCommandState((byte)0);
                            break;
                        }
                        this.startCommand();
                        this.commandSeletedIndex = 0;
                        this.changeCommandState((byte)1);
                    }
                }
            }
        } else if (Control.IsKeyDown(131072)) {
            if (this.isCommand) {
                switch (this.commandState) {
                    case 0: 
                    case 1: 
                    case 3: 
                    case 4: {
                        this.finishCommand();
                        break;
                    }
                    case 2: {
                        this.changeCommandState((byte)0);
                    }
                }
            } else {
                this.canvas.changeGameState(this.cleaneventlastgamestate);
                this.isshowmissionreward = true;
                if (this.itemPackUseNum < this.itemPackNum) {
                    this.isshowmissioncommand = false;
                }
            }
        }
    }

    private void drawcitycurrentlevel(byte cname) {
        this.canvas.gameUi.drawlabel("\u57ce\u5e02\u4fe1\u606f", 80, 57, 4, 0);
        this.canvas.drawZkString("\u57ce\u5e02\u5f53\u524d\u7b49\u7ea7\uff1a" + (this.canvas.mapm.cityMapLevel[cname] + 1) + " \u7ea7", 90, 79, 20, this.canvas.zkData);
        this.canvas.drawZkString("\u57ce\u5e02\u7b49\u7ea7\u4e0a\u9650\uff1a3 \u7ea7", 90, 99, 20, this.canvas.zkData);
    }

    private void drawcityupgradeneed(byte cname, byte clevel) {
        this.canvas.gameUi.drawlabel("\u5347\u7ea7\u6761\u4ef6", 80, 149, 4, 1);
        if (clevel < 2) {
            this.canvas.drawZkString("\u91d1\u94b1\uff1a" + this.upgradeCityMoney[cname][clevel], 90, 176, 20, this.canvas.zkData);
        } else {
            this.canvas.drawZkString("\u57ce\u5e02\u7b49\u7ea7\u5df2\u8fbe\u5230\u4e0a\u9650", 90, 176, 20, this.canvas.zkData);
        }
    }

    private void drawshowmissiondirect() {
        if (this.currentmainmissionindex == 34 && this.mainmissionstate < 2) {
            this.drawUiModule(116, 273, 235 + this.canvas.publiccounter / 2 % 2, 3, 0);
        }
    }

    private void drawCityIntroBox() {
        block24: {
            this.drawStaticUiBack((short)0);
            this.drawStaticUi((short)112);
            switch (this.cityIntroBoxState) {
                case 0: {
                    this.drawStaticUi((short)88);
                    this.drawUiModule(286, -1, 39, 36, 0);
                    this.drawStaticUi((short)113);
                    this.drawStaticUi((short)16, 0, 360);
                    this.canvas.drawImage(this.cityIntroBossImage, 70, 105, 36, 2);
                    this.scroll.drawscroll();
                    for (byte i = 0; i < 3; i = (byte)(i + 1)) {
                        if (i == this.cityIntroBoxIndex) {
                            this.drawStaticUi((short)115, 320, 175 + this.cityIntroBoxIndex * 42);
                            this.canvas.drawColorString(this.cityintrooption[i], 320, 185 + i * 42, 17, 15597319, 0);
                            continue;
                        }
                        this.drawStaticUi((short)114, 320, 175 + i * 42);
                        this.canvas.drawColorString(this.cityintrooption[i], 320, 185 + i * 42, 17, 10333123, 0);
                    }
                    this.drawshowmissiondirect();
                    if (!this.isCommand) break;
                    this.drawUiBack();
                    switch (this.commandState) {
                        case 0: {
                            this.drawStaticUi((short)19);
                            this.canvas.drawColorString("\u82b1\u8d39\u91d1\u94b1 " + this.savemoney + " \u6062\u590d\u751f\u547d\u548c\u80fd\u91cf\uff1f", 320, 133, 17, 0xFFFFFF, 0);
                            break;
                        }
                        case 1: {
                            this.drawtishikuang();
                            this.canvas.drawColorString("\u5df2\u6062\u590d\u3002", 320, 133, 17, 0xFFFFFF, 0);
                            if (!this.isshowsave || this.savetime <= 0 || --this.savetime != 0) break;
                            this.isshowsave = false;
                            this.changeCommandState((byte)4);
                            break;
                        }
                        case 2: {
                            this.drawtishikuang();
                            this.canvas.drawColorString("\u751f\u547d\u548c\u80fd\u91cf\u5df2\u6ee1\u65e0\u9700\u6062\u590d\u3002", 320, 133, 17, 0xFFFFFF, 0);
                            break;
                        }
                        case 3: {
                            this.drawtishikuang();
                            this.canvas.drawColorString("\u91d1\u94b1\u4e0d\u8db3", 320, 133, 17, 0xFFFFFF, 0);
                            break;
                        }
                        case 4: {
                            this.drawStaticUi((short)19);
                            this.canvas.drawColorString("\u662f\u5426\u5b58\u50a8\u6e38\u620f\u8fdb\u5ea6\uff1f", 320, 133, 17, 0xFFFFFF, 0);
                            break;
                        }
                        case 5: {
                            this.drawStaticUi((short)26);
                            this.canvas.drawColorString("\u5b58\u50a8\u4e2d\u2026\u2026", 320, 133, 17, 0xFFFFFF, 0);
                            break;
                        }
                        case 6: {
                            this.drawtishikuang();
                            this.canvas.drawColorString("\u5df2\u5b58\u50a8\u3002", 320, 133, 17, 0xFFFFFF, 0);
                            break;
                        }
                        case 7: {
                            this.drawStaticUi((short)19);
                            this.canvas.drawColorString("\u662f\u5426\u82b1\u8d39 " + this.resetmoney + " \u91cd\u7f6e\u6280\u80fd\u70b9\uff1f", 320, 133, 17, 0xFFFFFF, 0);
                            break;
                        }
                        case 8: {
                            this.drawtishikuang();
                            this.canvas.drawColorString("\u6280\u80fd\u70b9\u5df2\u91cd\u7f6e\u3002", 320, 133, 17, 0xFFFFFF, 0);
                            break;
                        }
                        case 9: {
                            this.drawtishikuang();
                            this.canvas.drawColorString("\u91d1\u94b1\u4e0d\u8db3", 320, 133, 17, 0xFFFFFF, 0);
                        }
                    }
                    break;
                }
                case 1: {
                    this.drawStaticUi((short)88);
                    this.drawUiModule(285, -1, 39, 36, 0);
                    this.drawStaticUi((short)118);
                    this.drawStaticUi((short)117, 0, 360);
                    this.drawcitycurrentlevel(this.canvas.mapm.cityMapIndex);
                    this.drawcityupgradeneed(this.canvas.mapm.cityMapIndex, this.canvas.mapm.cityMapLevel[this.canvas.mapm.cityMapIndex]);
                    if (!this.isCommand) break;
                    this.drawUiBack();
                    switch (this.commandState) {
                        case 0: {
                            this.drawStaticUi((short)126);
                            for (int i = 0; i < this.cityIntroBoxCommand.length; i = (int)((byte)(i + 1))) {
                                if (i == this.commandSeletedIndex) {
                                    this.drawChoiceBox(320, 144 + this.commandSeletedIndex * 45);
                                    this.canvas.drawColorString(this.cityIntroBoxCommand[i], 320, 147 + this.commandSeletedIndex * 45, 17, 15597319, 0);
                                    continue;
                                }
                                this.canvas.drawColorString(this.cityIntroBoxCommand[i], 320, 147 + i * 45, 17, 0xFFFFFF, 0);
                            }
                            break block24;
                        }
                        case 1: {
                            this.drawStaticUi((short)26);
                            this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                        }
                    }
                }
            }
        }
    }

    private void cityIntroBoxKeyProcess() {
        switch (this.cityIntroBoxState) {
            case 0: {
                if (this.isCommand) {
                    if (this.commandState == 5) {
                        this.saveGame();
                        this.setCommandShowTime();
                        this.changeCommandState((byte)6);
                        return;
                    }
                    if (!this.isshowsave && this.commandState == 1) {
                        this.savetime = 20;
                        this.isshowsave = true;
                        return;
                    }
                }
                if (Control.IsKeyDown(4100)) {
                    if (this.isCommand) break;
                    this.cityIntroBoxIndex = (byte)(this.cityIntroBoxIndex - 1);
                    this.cityIntroBoxIndex = (byte)(this.cityIntroBoxIndex < 0 ? 2 : (int)this.cityIntroBoxIndex);
                    break;
                }
                if (Control.IsKeyDown(8448)) {
                    if (this.isCommand) break;
                    this.cityIntroBoxIndex = (byte)(this.cityIntroBoxIndex + 1);
                    this.cityIntroBoxIndex = this.cityIntroBoxIndex > 2 ? (byte)0 : this.cityIntroBoxIndex;
                    break;
                }
                if (Control.IsKeyDown(327712)) {
                    if (this.isCommand) {
                        switch (this.commandState) {
                            case 0: {
                                if (this.checkMoney(this.savemoney)) {
                                    this.canvas.gameUi.addMoney(-this.savemoney);
                                    this.canvas.rolem.huifu();
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)1);
                                    break;
                                }
                                this.setCommandShowTime();
                                this.changeCommandState((byte)3);
                                break;
                            }
                            case 1: {
                                this.changeCommandState((byte)4);
                                break;
                            }
                            case 2: {
                                this.changeCommandState((byte)4);
                                break;
                            }
                            case 3: {
                                this.changeCommandState((byte)4);
                                break;
                            }
                            case 4: {
                                this.savegamestate = (byte)4;
                                this.savecityindex = this.canvas.mapm.cityMapIndex;
                                this.setCommandShowTime();
                                this.changeCommandState((byte)5);
                                break;
                            }
                            case 5: {
                                break;
                            }
                            case 6: 
                            case 8: 
                            case 9: {
                                this.finishCommand();
                                break;
                            }
                            case 7: {
                                if (!this.checkMoney(this.resetmoney)) {
                                    this.canvas.sms.setshowsmsshop(0);
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)9);
                                    break;
                                }
                                this.addMoney(-this.resetmoney);
                                this.resetSkill();
                                this.setCommandShowTime();
                                this.changeCommandState((byte)8);
                            }
                        }
                        break;
                    }
                    switch (this.cityIntroBoxIndex) {
                        case 0: {
                            if (this.canvas.rolem.fplayerbox[0].ismanxiemanlan()) {
                                this.startCommand();
                                this.changeCommandState((byte)4);
                                break;
                            }
                            this.savemoney = 8 + (this.canvas.rolem.fplayerbox[0].level - 1) * 3;
                            this.startCommand();
                            this.changeCommandState((byte)0);
                            break;
                        }
                        case 1: {
                            this.cityIntroBoxState = 1;
                            break;
                        }
                        case 2: {
                            this.resetmoney = this.canvas.rolem.playerLevel * 113 * 3 / 2;
                            this.startCommand();
                            this.changeCommandState((byte)7);
                        }
                    }
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                if (this.isCommand) {
                    switch (this.commandState) {
                        case 0: 
                        case 1: 
                        case 2: 
                        case 3: {
                            this.changeCommandState((byte)4);
                            break;
                        }
                        case 4: 
                        case 6: 
                        case 7: 
                        case 8: 
                        case 9: {
                            this.finishCommand();
                        }
                    }
                    break;
                }
                this.clearCityIntroBossData();
                this.canvas.mapm.iscityflushinfo = true;
                this.changeFightUiState((byte)3);
                this.canvas.changeGameState(4);
                break;
            }
            case 1: {
                if (Control.IsKeyDown(4100)) {
                    if (!this.isCommand) break;
                    switch (this.commandState) {
                        case 0: {
                            this.commandSeletedIndex = (byte)(this.commandSeletedIndex - 1);
                            this.commandSeletedIndex = this.commandSeletedIndex < 0 ? (byte)1 : this.commandSeletedIndex;
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(8448)) {
                    if (!this.isCommand) break;
                    switch (this.commandState) {
                        case 0: {
                            this.commandSeletedIndex = (byte)(this.commandSeletedIndex + 1);
                            this.commandSeletedIndex = this.commandSeletedIndex > 1 ? (byte)0 : this.commandSeletedIndex;
                        }
                    }
                    break;
                }
                if (Control.IsKeyDown(327712)) {
                    if (this.isCommand) {
                        switch (this.commandState) {
                            case 0: {
                                switch (this.commandSeletedIndex) {
                                    case 0: {
                                        if (!this.canvas.mapm.checkCityLevel(this.canvas.mapm.cityMapIndex)) {
                                            this.commandStr = "\u57ce\u5e02\u7b49\u7ea7\u5df2\u8fbe\u5230\u4e0a\u9650";
                                            this.setCommandShowTime();
                                            this.changeCommandState((byte)1);
                                            break;
                                        }
                                        if (!this.checkMoney(this.canvas.mapm.cityMapIndex, this.canvas.mapm.cityMapLevel[this.canvas.mapm.cityMapIndex])) {
                                            this.canvas.sms.setshowsmsshop(0);
                                            this.setCommandShowTime();
                                            this.changeCommandState((byte)1);
                                            break;
                                        }
                                        this.addMoney(-this.upgradeCityMoney[this.canvas.mapm.cityMapIndex][this.canvas.mapm.cityMapLevel[this.canvas.mapm.cityMapIndex]]);
                                        this.finishCommand();
                                        this.canvas.startSwitchScrren(1, (byte)4, 1);
                                        break;
                                    }
                                    case 1: {
                                        this.finishCommand();
                                    }
                                }
                                break;
                            }
                            case 1: {
                                this.finishCommand();
                            }
                        }
                        break;
                    }
                    this.startCommand();
                    this.commandSeletedIndex = 0;
                    this.changeCommandState((byte)0);
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                if (this.isCommand) {
                    this.finishCommand();
                    break;
                }
                this.setCityIntroBossData(this.canvas.mapm.cityMapIndex);
                this.cityIntroBoxState = 0;
                this.changeUiState((byte)11);
            }
        }
    }

    private void resetSkill() {
        int i;
        for (i = 0; i < this.canvas.rolem.weaponCriticalKey7.length; ++i) {
            this.canvas.rolem.weaponCriticalKey7[i][0] = -1;
        }
        for (i = 0; i < this.canvas.rolem.weaponCriticalKey9.length; ++i) {
            this.canvas.rolem.weaponCriticalKey9[i][0] = -1;
        }
        this.canvas.rolem.skillState = new byte[21][2];
        this.canvas.rolem.setroledata();
        this.canvas.rolem.playerCurrentSkillPoint += this.canvas.rolem.playerUseedSkillPoint;
        this.canvas.rolem.playerUseedSkillPoint = 0;
    }

    public boolean checkMoney(byte cname, byte clevel) {
        if (this.playerMoney < this.upgradeCityMoney[cname][clevel]) {
            this.commandStr = "\u91d1\u94b1\u4e0d\u8db3";
            return false;
        }
        return true;
    }

    public void upgradeCity(int cname) {
        int n = cname;
        byte by = (byte)(this.canvas.mapm.cityMapLevel[n] + 1);
        this.canvas.mapm.cityMapLevel[n] = by;
        int n2 = this.canvas.mapm.cityMapLevel[cname] = by > 2 ? 2 : this.canvas.mapm.cityMapLevel[cname];
        if (cname < 4) {
            this.openweapon((byte)cname, this.canvas.mapm.cityMapLevel[cname]);
        }
        this.commandStr = "\u57ce\u5e02\u5df2\u7ecf\u5347\u4e3a " + (this.canvas.mapm.cityMapLevel[cname] + 1) + " \u7ea7";
    }

    private void drawMineUi() {
        this.drawStaticUiBack((short)0);
        this.drawStaticUi((short)88);
        this.drawStaticUi((short)112);
        this.drawUiModule(271, LABEL_X[0][0], 42, 36, 0);
        this.drawStaticUi((short)15);
        this.drawStaticUi((short)117, 0, 360);
        this.drawminedata();
        this.drawmineincome();
        this.drawmineworker();
        this.drawmineinfo();
        if (this.isCommand) {
            this.drawUiBack();
            switch (this.commandState) {
                case 0: {
                    this.drawStaticUi((short)126);
                    for (int i = 0; i < this.mineCommand.length; i = (int)((byte)(i + 1))) {
                        if (i == this.commandSeletedIndex) {
                            this.drawChoiceBox(320, 125 + this.commandSeletedIndex * 45);
                            this.canvas.drawColorString(this.mineCommand[i], 320, 128 + this.commandSeletedIndex * 45, 17, 15597319, 0);
                            continue;
                        }
                        this.canvas.drawColorString(this.mineCommand[i], 320, 128 + i * 45, 17, 0xFFFFFF, 0);
                    }
                    break;
                }
                case 1: {
                    this.drawStaticUi((short)26);
                    this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                    break;
                }
                case 2: {
                    this.drawStaticUi((short)26);
                    int tempy = 0;
                    if (this.commandStrs.length > 1) {
                        tempy = (this.commandStrs.length - 1) * (20 / this.commandStrs.length);
                    }
                    this.canvas.drawZkStringArry(this.commandStrs, 320, 133 - tempy, 17, 0, this.commandStrs.length, 0xFFFFFF);
                    break;
                }
                case 3: {
                    this.drawStaticUi((short)19);
                    this.drawjiantou(0, 0, 375, 132, 33);
                    this.drawjiantou(0, 1, 375, 150, 17);
                    this.canvas.drawColorString("\u9009\u62e9\u96c7\u4f63\u6570\u91cf\uff1a" + this.needNum, 320, 133, 17, 0xFFFFFF, 0);
                }
            }
        }
    }

    private void drawminedata() {
        this.canvas.gameUi.drawlabel("\u77ff\u4e95\u72b6\u6001", 60, 73, 4, 2);
        this.canvas.drawmodule(this.canvas.mapm.cityMapImage, this.canvas.mapm.cityMapModules, 0, 60, 100, 20, 0);
        if (this.mineminute[this.mineIndex] < 10) {
            if (this.mineWorkerNum[this.mineIndex] > 0) {
                this.canvas.drawZkString("\u5269\u4f59\u65f6\u95f4\uff1a" + (9 - this.mineminute[this.mineIndex]) / 10 + (9 - this.mineminute[this.mineIndex]) % 10 + ":" + (59 - this.minesecond[this.mineIndex]) / 10 + (59 - this.minesecond[this.mineIndex]) % 10, 150, 100, 20, this.canvas.zkData);
            } else {
                this.canvas.drawZkString("\u6682\u65f6\u65e0\u6cd5\u5f00\u91c7", 150, 100, 20, this.canvas.zkData);
            }
        } else {
            this.canvas.drawZkString("\u5269\u4f59\u65f6\u95f4\uff1a00:00", 150, 100, 20, this.canvas.zkData);
        }
        this.canvas.drawColorString("\u5355\u4e2a\u96c7\u7528\u4ef7\u683c\uff1a200", 150, 125, 20, 15597319, 0);
    }

    private void drawmineincome() {
        this.canvas.gameUi.drawlabel("\u77ff\u4e95\u6536\u76ca", 350, 200, 4, 1);
        this.canvas.drawZkString("\u53ef\u9886\u53d6$7\u91d1\u94b1$1\uff1a" + this.currentGetMoney[this.mineIndex], 360, 225, 20, this.canvas.zkData);
        if (this.currentMineMaterialName[this.mineIndex] > 0) {
            this.canvas.drawZkString("\u53ef\u9886\u53d6$2\u6750\u6599$1\uff1a$" + this.getItemLevelColorIndex(this.itemLevel[this.currentMineMaterialName[this.mineIndex]]) + this.iteminfo[this.currentMineMaterialName[this.mineIndex]][0] + "$11\u4e2a", 360, 253, 20, this.canvas.zkData);
        } else {
            this.canvas.drawZkString("\u53ef\u9886\u53d6$2\u6750\u6599$1\uff1a\u65e0", 360, 253, 20, this.canvas.zkData);
        }
    }

    private void drawmineworker() {
        this.canvas.gameUi.drawlabel("\u77ff\u4e95\u5de5\u4eba", 60, 203, 4, 2);
        this.canvas.drawmodule(this.canvas.mapm.cityMapImage, this.canvas.mapm.cityMapModules, 1, 65, 225, 20, 0);
        this.canvas.drawmodule(this.canvas.mapm.cityMapImage, this.canvas.mapm.cityMapModules, 2, 185, 225, 20, 0);
        this.canvas.drawColorString("X " + this.mineWorkerNum[this.mineIndex], 218, 223, 36, 0xFFFFFF, 0);
        this.canvas.drawColorString("\u8fd8\u80fd\u8d2d\u4e70 " + (this.mineWorkerTotal[this.mineIndex][this.canvas.mapm.cityMapLevel[this.canvas.mapm.cityMapIndex]] - this.mineWorkerNum[this.mineIndex]) + " \u4e2a", 220, 260, 17, 16775789, 0);
    }

    private void drawmineinfo() {
        this.canvas.gameUi.drawlabel("\u77ff\u4e95\u63cf\u8ff0", 350, 73, 4, 1);
        this.scroll.close();
        StringBuffer strb = new StringBuffer("\u96c7\u4f63\u5de5\u4eba\u8d8a\u591a\uff0c\u6bcf\u4e2a\u5355\u4f4d\u65f6\u95f4\u91c7\u53d6\u7684\u91d1\u94b1\u548c\u6750\u6599\u8d8a\u591a\u3002");
        this.scroll.setscroll(strb.toString(), 360, 95, 210, 50, 1, 20);
        this.scroll.drawscroll();
    }

    public void addminework(byte index, byte num) {
        byte by = index;
        this.mineWorkerNum[by] = (byte)(this.mineWorkerNum[by] + num);
    }

    private void mineUiKeyProcess() {
        if (Control.IsKeyDown(4100)) {
            if (this.isCommand) {
                switch (this.commandState) {
                    case 0: {
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex - 1);
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex < 0 ? 2 : (int)this.commandSeletedIndex);
                        break;
                    }
                    case 3: {
                        this.processNeedNum(1, this.mineWorkerTotal[this.mineIndex][this.canvas.mapm.cityMapLevel[this.canvas.mapm.cityMapIndex]] - this.mineWorkerNum[this.mineIndex]);
                    }
                }
            }
        } else if (Control.IsKeyDown(8448)) {
            if (this.isCommand) {
                switch (this.commandState) {
                    case 0: {
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex + 1);
                        this.commandSeletedIndex = this.commandSeletedIndex > 2 ? (byte)0 : this.commandSeletedIndex;
                        break;
                    }
                    case 3: {
                        this.processNeedNum(-1, this.mineWorkerTotal[this.mineIndex][this.canvas.mapm.cityMapLevel[this.canvas.mapm.cityMapIndex]] - this.mineWorkerNum[this.mineIndex]);
                    }
                }
            }
        } else if (Control.IsKeyDown(327712)) {
            if (this.isCommand) {
                block8 : switch (this.commandState) {
                    case 0: {
                        switch (this.commandSeletedIndex) {
                            case 0: {
                                if (this.currentGetMoney[this.mineIndex] == 0 && this.currentMineMaterialName[this.mineIndex] == 0) {
                                    this.commandStr = "\u5f53\u524d\u77ff\u4e95\u6ca1\u6709\u53ef\u9886\u7684\u6536\u76ca";
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)1);
                                    break block8;
                                }
                                if (this.currentMineMaterialName[this.mineIndex] > 0 && !this.checkItemBox(this.itemPack, this.currentMineMaterialName[this.mineIndex], (byte)1)) {
                                    this.commandStr = "\u80cc\u5305\u5df2\u6ee1\uff0c\u8bf7\u5148\u6e05\u7406\u80cc\u5305\uff01";
                                    this.isShowKuoBaoTishi = true;
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)1);
                                    break block8;
                                }
                                StringBuffer tempstr = new StringBuffer("\u9886\u53d6\u6210\u529f");
                                if (this.currentGetMoney[this.mineIndex] > 0) {
                                    this.playerMoney += this.currentGetMoney[this.mineIndex];
                                    tempstr.append("\uff0c\u83b7\u5f97\u91d1\u94b1 " + this.currentGetMoney[this.mineIndex]);
                                    this.currentGetMoney[this.mineIndex] = 0;
                                }
                                if (this.currentMineMaterialName[this.mineIndex] > 0) {
                                    this.addItem(this.currentMineMaterialName[this.mineIndex], (byte)1);
                                    tempstr.append("\uff0c\u5e76\u83b7\u5f97$" + this.getItemLevelColorIndex(this.itemLevel[this.currentMineMaterialName[this.mineIndex]]) + this.iteminfo[this.currentMineMaterialName[this.mineIndex]][0] + "$1" + " 1 \u4e2a");
                                    this.currentMineMaterialName[this.mineIndex] = 0;
                                }
                                this.isCanRandom[this.mineIndex] = 0;
                                this.mineminute[this.mineIndex] = 0;
                                this.commandStrs = this.canvas.formatzkword(tempstr.toString(), 200, 0);
                                this.setCommandShowTime();
                                this.changeCommandState((byte)2);
                                break block8;
                            }
                            case 1: {
                                if (this.mineWorkerNum[this.mineIndex] >= this.mineWorkerTotal[this.mineIndex][this.canvas.mapm.cityMapLevel[this.canvas.mapm.cityMapIndex]]) {
                                    this.commandStr = "\u77ff\u5de5\u6570\u91cf\u5df2\u8fbe\u4e0a\u9650\uff0c\u65e0\u6cd5\u96c7\u4f63";
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)1);
                                    break block8;
                                }
                                this.needNum = 1;
                                this.changeCommandState((byte)3);
                                break block8;
                            }
                            case 2: {
                                this.finishCommand();
                            }
                        }
                        break;
                    }
                    case 1: 
                    case 2: {
                        this.finishCommand();
                        break;
                    }
                    case 3: {
                        if (this.playerMoney < 200 * this.needNum) {
                            this.canvas.sms.setshowsmsshop(0);
                            this.commandStr = "\u91d1\u94b1\u4e0d\u8db3";
                            this.setCommandShowTime();
                            this.changeCommandState((byte)1);
                            break;
                        }
                        this.addMoney(-(200 * this.needNum));
                        this.addminework(this.mineIndex, (byte)this.needNum);
                        this.checkmission(3, this.canvas.mapm.mapIndex, (short)this.needNum);
                        this.commandStr = "\u96c7\u4f63\u77ff\u5de5\u6210\u529f";
                        this.setCommandShowTime();
                        this.changeCommandState((byte)1);
                    }
                }
            } else {
                this.commandSeletedIndex = 0;
                this.startCommand();
                this.changeCommandState((byte)0);
            }
        } else if (Control.IsKeyDown(131072)) {
            if (this.isCommand) {
                this.finishCommand();
            } else {
                this.canvas.mapm.iscityflushinfo = true;
                this.changeFightUiState((byte)3);
                this.canvas.changeGameState(4);
            }
        }
    }

    public void getminereward() {
        for (int i = 0; i < 2; ++i) {
            if (this.currentGetMoney[i] <= 0 && this.currentMineMaterialName[i] <= 0) continue;
            if (this.currentGetMoney[i] > 0) {
                this.playerMoney += this.currentGetMoney[i];
                this.currentGetMoney[i] = 0;
            }
            if (this.currentMineMaterialName[i] > 0) {
                this.addItem(this.currentMineMaterialName[i], (byte)1);
                this.currentMineMaterialName[i] = 0;
            }
            this.isCanRandom[i] = 0;
            this.mineminute[i] = 0;
        }
    }

    private void drawChoiceBox(int x, int y) {
        this.drawStaticUi((short)25, x, y);
        this.drawUiModule(93, x + this.choiceX, y, 0, 0);
        this.choiceX += this.choiceSpeedX;
        if (this.choiceX > 6 || this.choiceX < -17) {
            this.choiceSpeedX = -this.choiceSpeedX;
        }
    }

    public void drawskillicon(int x, int y, int name) {
        if (name > -1) {
            if (this.canvas.rolem.skillState[name][0] == 0) {
                this.canvas.drawImage(this.canvas.skilliconimage[0], x + 1, y + 1, 20, 0, this.canvas.skilliconmodules, 20);
            } else if (this.canvas.rolem.skillState[name][0] > 0) {
                this.canvas.drawImage(this.canvas.skilliconimage[0], x + 1, y + 1, 20, 0, this.canvas.skilliconmodules, this.skillicondata[name]);
            }
        }
    }

    public void drawfightskillicon(int x, int y, int name) {
        if (name > -1) {
            this.canvas.drawImage(this.canvas.skilliconimage[0], x + 1, y + 1, 20, 0, this.canvas.skilliconmodules, this.fightskillicon[name]);
        }
    }

    public void drawfighticon(int x, int y, int name) {
        if (name > -1) {
            this.canvas.drawImage(this.canvas.skilliconimage[0], x + 1, y + 1, 20, 0, this.canvas.skilliconmodules, name);
        }
    }

    private void drawskillaction() {
        if (this.skillcurrentactionstate == 1) {
            this.canvas.drawImage(this.canvas.skilliconimage[0], 196 + this.skillBoxSeletedIndex % 3 * 110, 51 + this.skillBoxSeletedIndex / 3 * 30, 20, 0, this.canvas.skilliconmodules, this.skillopenaction[this.skillframeindex]);
            this.skillframeindex = (byte)(this.skillframeindex + 1);
            if (this.skillframeindex >= this.skillopenaction.length) {
                this.skillframeindex = 0;
                this.skillcurrentactionstate = 0;
            }
        } else if (this.skillcurrentactionstate == 2) {
            this.canvas.drawImage(this.canvas.skilliconimage[0], 196 + this.skillBoxSeletedIndex % 3 * 110, 51 + this.skillBoxSeletedIndex / 3 * 30, 20, 0, this.canvas.skilliconmodules, this.skillupaction[this.skillframeindex]);
            this.skillframeindex = (byte)(this.skillframeindex + 1);
            if (this.skillframeindex >= this.skillupaction.length) {
                this.skillframeindex = 0;
                this.skillcurrentactionstate = 0;
            }
        }
    }

    private void drawSkillBox() {
        this.drawStaticUiBack((short)0);
        block0 : switch (this.skillBoxState) {
            case 0: {
                this.drawStaticUi((short)(this.skillBoxIndex + 92));
                this.drawlabels(this.skillboxdata, this.skillBoxIndex);
                this.drawUiModule(242, 1, 44, 20, 0);
                this.drawStaticUi((short)16, 0, 360);
                this.drawPlayerCurrentSkillPoint();
                break;
            }
            case 1: {
                byte i;
                this.drawStaticUi((short)(this.skillBoxIndex + 92));
                this.drawlabels(this.skillboxdata, this.skillBoxIndex);
                this.drawStaticUi((short)124);
                this.drawStaticUi((short)117, 0, 360);
                this.drawPlayerCurrentSkillPoint();
                int startX = 0;
                int startY = 0;
                int sx = 195;
                int sy = 50;
                this.drawSkillLine();
                for (i = 0; i < 18; i = (byte)(i + 1)) {
                    for (int j = 0; j < this.skillStructure[this.skillBoxIndex].length; j = (int)((byte)(j + 1))) {
                        if (i != this.skillStructure[this.skillBoxIndex][j]) continue;
                        if (i == this.skillBoxSeletedIndex) {
                            this.drawUiModule(5, sx + this.skillBoxSeletedIndex % 3 * 110, sy + this.skillBoxSeletedIndex / 3 * 30, 20, 0);
                        } else {
                            this.drawUiModule(6, sx + startX, sy + startY, 20, 0);
                        }
                        this.drawskillicon(sx + startX, sy + startY, this.skillIndexData[this.skillBoxIndex][i]);
                        this.canvas.g.setColor(0xFFFFFF);
                        this.canvas.drawZkString("$1" + this.canvas.rolem.skillState[this.skillIndexData[this.skillBoxIndex][i]][1] + "/" + this.canvas.rolem.skillBaseData[this.skillIndexData[this.skillBoxIndex][i]][6], sx + 44 + startX - 10, sy + startY + 14, 20, this.canvas.zkData);
                    }
                    startX += 110;
                    if (i % 3 != 2) continue;
                    startY += 30;
                    startX = 0;
                }
                this.drawskillaction();
                this.drawSkillKey7(this.skillBoxIndex);
                this.drawSkillKey9(this.skillBoxIndex);
                this.drawskillinfo(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                if (!this.isCommand) break;
                this.drawUiBack();
                switch (this.commandState) {
                    case 7: {
                        this.drawStaticUi((short)126);
                        for (i = 0; i < this.skillCommand[this.commandState].length; i = (byte)(i + 1)) {
                            if (i == this.commandSeletedIndex) {
                                this.drawChoiceBox(320, 144 + this.commandSeletedIndex * 45);
                                this.canvas.drawColorString(this.skillCommand[this.commandState][i], 320, 147 + this.commandSeletedIndex * 45, 17, 15597319, 0);
                                continue;
                            }
                            this.canvas.drawColorString(this.skillCommand[this.commandState][i], 320, 147 + i * 45, 17, 0xFFFFFF, 0);
                        }
                        break block0;
                    }
                    case 0: 
                    case 1: 
                    case 2: 
                    case 5: 
                    case 6: {
                        this.drawStaticUi((short)126);
                        for (i = 0; i < this.skillCommand[this.commandState].length; i = (byte)(i + 1)) {
                            if (i == this.commandSeletedIndex) {
                                this.drawChoiceBox(320, 125 + this.commandSeletedIndex * 45);
                                this.canvas.drawColorString(this.skillCommand[this.commandState][i], 320, 128 + this.commandSeletedIndex * 45, 17, 15597319, 0);
                                continue;
                            }
                            this.canvas.drawColorString(this.skillCommand[this.commandState][i], 320, 128 + i * 45, 17, 0xFFFFFF, 0);
                        }
                        break block0;
                    }
                    case 3: 
                    case 4: {
                        this.drawStaticUi((short)27);
                        for (i = 0; i < this.skillCommand[this.commandState].length; i = (byte)(i + 1)) {
                            if (i == this.commandSeletedIndex) {
                                this.drawChoiceBox(320, 98 + this.commandSeletedIndex * 45);
                                this.canvas.drawColorString(this.skillCommand[this.commandState][i], 320, 101 + this.commandSeletedIndex * 45, 17, 15597319, 0);
                                continue;
                            }
                            this.canvas.drawColorString(this.skillCommand[this.commandState][i], 320, 101 + i * 45, 17, 0xFFFFFF, 0);
                        }
                        break block0;
                    }
                    case 8: {
                        this.drawStaticUi((short)26);
                        this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                    }
                }
                break;
            }
            case 2: {
                this.drawStaticUi((short)(this.skillBoxIndex + 92));
                this.drawlabels(this.skillboxdata, this.skillBoxIndex);
                this.drawStaticUi((short)118);
                this.drawUiModule(5, 400, 77, 20, 0);
                this.drawStaticUi((short)84, 0, 360);
                this.drawskillcondition(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                this.drawskillicon(400, 77, this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                this.drawskillbasedata(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                this.drawskillinfo();
            }
        }
    }

    public void drawPlayerCurrentSkillPoint() {
        this.canvas.drawColorString("\u6280\u80fd\u70b9:" + this.canvas.rolem.playerCurrentSkillPoint, 543, 38, 33, 15597319, 0);
    }

    private void drawSkillKey7(byte index) {
        if (this.canvas.rolem.weaponCriticalKey7[index][0] != -1) {
            for (int i = 0; i < this.skillIndexData[index].length; ++i) {
                if (this.skillIndexData[index][i] != this.canvas.rolem.weaponCriticalKey7[index][0]) continue;
                this.drawStaticUi((short)23, 189 + i % 3 * 110, 44 + i / 3 * 30);
                this.drawUiModule(7, 203 + i % 3 * 110, 70 + i / 3 * 30, 20, 0);
                break;
            }
        }
    }

    private void drawSkillKey9(byte index) {
        if (this.canvas.rolem.weaponCriticalKey9[index][0] != -1) {
            for (int i = 0; i < this.skillIndexData[index].length; ++i) {
                if (this.skillIndexData[index][i] != this.canvas.rolem.weaponCriticalKey9[index][0]) continue;
                this.drawStaticUi((short)23, 189 + i % 3 * 110, 44 + i / 3 * 30);
                this.drawUiModule(8, 203 + i % 3 * 110, 70 + i / 3 * 30, 20, 0);
                break;
            }
        }
    }

    private void drawskillcondition(byte skillname) {
        this.drawlabel(this.skillinfo[skillname][0], 80, 57, this.skillinfo[skillname][0].length(), 0);
        if (skillname == 20) {
            if (this.canvas.rolem.skillState[20][0] > 0) {
                this.canvas.drawZkString("$2\u5df2\u5f00\u542f", 90, 78, 20, this.canvas.zkData);
            } else {
                this.canvas.drawZkString("$1\u9700\u8981\u88c5\u5907$6\u65f6\u7a7a\u4e4b\u77b3", 90, 78, 20, this.canvas.zkData);
            }
        } else if (this.canvas.rolem.skillBaseData[skillname][7] > -1) {
            if (this.canvas.rolem.skillState[this.canvas.rolem.skillBaseData[skillname][7]][1] < this.canvas.rolem.skillBaseData[skillname][8]) {
                this.canvas.drawZkString("$6\u9700\u6c42\u6280\u80fd\uff1a" + this.skillinfo[this.canvas.rolem.skillBaseData[skillname][7]][0] + "\u7b49\u7ea7 " + this.canvas.rolem.skillBaseData[skillname][8], 90, 78, 20, this.canvas.zkData);
            } else if (this.canvas.rolem.skillState[skillname][0] > 0) {
                this.canvas.drawZkString("$2\u5df2\u5f00\u542f", 90, 78, 20, this.canvas.zkData);
            } else {
                this.canvas.drawZkString("$1\u7b26\u5408\u6761\u4ef6\u53ef\u5f00\u542f", 90, 78, 20, this.canvas.zkData);
            }
        } else if (this.canvas.rolem.skillState[skillname][0] > 0) {
            this.canvas.drawZkString("$2\u5df2\u5f00\u542f", 90, 78, 20, this.canvas.zkData);
        } else {
            this.canvas.drawZkString("$1\u7b26\u5408\u6761\u4ef6\u53ef\u5f00\u542f", 90, 78, 20, this.canvas.zkData);
        }
    }

    private void drawskillinfo(byte skillname) {
        this.drawlabel(this.skillinfo[skillname][0], 60, 242, this.skillinfo[skillname][0].length(), 0);
        if (skillname == 20) {
            if (this.canvas.rolem.skillState[20][0] > 0) {
                this.scroll.drawscroll();
            } else {
                this.canvas.drawZkString("$1\u9700\u8981\u88c5\u5907$6\u65f6\u7a7a\u4e4b\u77b3", 70, 263, 20, this.canvas.zkData);
            }
        } else if (this.canvas.rolem.skillBaseData[skillname][7] > -1) {
            if (this.canvas.rolem.skillState[this.canvas.rolem.skillBaseData[skillname][7]][1] < this.canvas.rolem.skillBaseData[skillname][8]) {
                this.canvas.drawZkString("$6\u9700\u6c42\u6280\u80fd\uff1a" + this.skillinfo[this.canvas.rolem.skillBaseData[skillname][7]][0] + "\u7b49\u7ea7 " + this.canvas.rolem.skillBaseData[skillname][8], 70, 263, 20, this.canvas.zkData);
            } else {
                this.scroll.drawscroll();
            }
        } else {
            this.scroll.drawscroll();
        }
    }

    private void setSkillBaseData(byte skillname) {
        if (skillname > -1) {
            this.scroll.close(1);
            StringBuffer strb = new StringBuffer("");
            strb.append("$1");
            if (skillname <= 9) {
                if (this.canvas.rolem.skillState[skillname][1] > 0) {
                    strb.append("\u5f53\u524d\u7b49\u7ea7\u4f24\u5bb3\uff1a" + (this.canvas.rolem.skillBaseData[skillname][0] + this.canvas.rolem.skillState[skillname][1] * this.canvas.rolem.skillBaseData[skillname][3]) + " \uff05");
                } else {
                    strb.append("\u4e0b\u4e00\u7b49\u7ea7\u4f24\u5bb3\uff1a" + (this.canvas.rolem.skillBaseData[skillname][0] + this.canvas.rolem.skillBaseData[skillname][3]) + " \uff05");
                }
                if (this.canvas.rolem.skillState[skillname][0] == 1 && this.canvas.rolem.skillState[skillname][1] < this.canvas.rolem.skillBaseData[skillname][6]) {
                    strb.append("|");
                    strb.append("\u4e0b\u4e00\u7b49\u7ea7\u4f24\u5bb3\uff1a\u57fa\u7840\u653b\u51fb\u7684 " + (this.canvas.rolem.skillBaseData[skillname][0] + (this.canvas.rolem.skillState[skillname][1] + 1) * this.canvas.rolem.skillBaseData[skillname][3]) + " \uff05");
                }
                strb.append("|");
                strb.append("\u80fd\u91cf\u6d88\u8017\uff1a" + this.canvas.rolem.getMagicUseed(skillname));
            } else {
                switch (skillname) {
                    case 10: {
                        strb.append("\u6280\u80fd\u53d1\u52a8\u65f6\u5904\u4e8e\u65e0\u654c\u72b6\u6001\u3002");
                        strb.append("|");
                        strb.append("\u80fd\u91cf\u6d88\u8017\uff1a" + this.canvas.rolem.getMagicUseed(skillname));
                        break;
                    }
                    case 11: {
                        strb.append("\u5f53\u524d\u6062\u590d\u751f\u547d\uff1a" + this.canvas.rolem.skillState[skillname][1] + " \uff05\u6bcf\u79d2");
                        if (this.canvas.rolem.skillState[skillname][0] == 1 && this.canvas.rolem.skillState[skillname][1] < this.canvas.rolem.skillBaseData[skillname][6]) {
                            strb.append("|");
                            strb.append("\u4e0b\u4e00\u7b49\u7ea7\uff1a\u6062\u590d\u751f\u547d " + (this.canvas.rolem.skillState[skillname][1] + 1) + " \uff05\u6bcf\u79d2");
                        }
                        strb.append("|");
                        strb.append("\u80fd\u91cf\u6d88\u8017\uff1a" + this.canvas.rolem.getMagicUseed(skillname));
                        break;
                    }
                    case 12: {
                        strb.append("\u5f53\u524d\u6062\u590d\u751f\u547d\uff1a" + this.canvas.rolem.skillState[skillname][1] + " \uff05\u6bcf\u79d2");
                        if (this.canvas.rolem.skillState[skillname][0] == 1 && this.canvas.rolem.skillState[skillname][1] < this.canvas.rolem.skillBaseData[skillname][6]) {
                            strb.append("|");
                            strb.append("\u4e0b\u4e00\u7b49\u7ea7\uff1a\u6062\u590d\u751f\u547d " + (this.canvas.rolem.skillState[skillname][1] + 1) + " \uff05\u6bcf\u79d2");
                        }
                        strb.append("|");
                        strb.append("\u80fd\u91cf\u6d88\u8017\uff1a" + this.canvas.rolem.getMagicUseed(skillname));
                        break;
                    }
                    case 13: {
                        if (this.canvas.rolem.skillState[skillname][0] == 0) {
                            strb.append("\u4e0b\u4e00\u7b49\u7ea7\uff1a\u589e\u52a0\u57fa\u7840\u4f24\u5bb3 " + this.canvas.rolem.passiveSkillData[0][this.canvas.rolem.skillState[skillname][1] + 1] + " \uff05");
                            break;
                        }
                        strb.append("\u5f53\u524d\u589e\u52a0\u57fa\u7840\u4f24\u5bb3\uff1a" + this.canvas.rolem.passiveSkillData[0][this.canvas.rolem.skillState[skillname][1]] + " \uff05");
                        if (this.canvas.rolem.skillState[skillname][1] >= this.canvas.rolem.skillBaseData[skillname][6]) break;
                        strb.append("|");
                        strb.append("\u4e0b\u4e00\u7b49\u7ea7\uff1a\u589e\u52a0\u57fa\u7840\u4f24\u5bb3 " + this.canvas.rolem.passiveSkillData[0][this.canvas.rolem.skillState[skillname][1] + 1] + " \uff05");
                        break;
                    }
                    case 14: {
                        if (this.canvas.rolem.skillState[skillname][0] == 0) {
                            strb.append("\u4e0b\u4e00\u7b49\u7ea7\uff1a\u589e\u52a0\u9632\u5fa1 " + this.canvas.rolem.passiveSkillData[1][this.canvas.rolem.skillState[skillname][1] + 1] + " \uff05");
                            break;
                        }
                        strb.append("\u5f53\u524d\u589e\u52a0\u9632\u5fa1\uff1a" + this.canvas.rolem.passiveSkillData[1][this.canvas.rolem.skillState[skillname][1]] + " \uff05");
                        if (this.canvas.rolem.skillState[skillname][1] >= this.canvas.rolem.skillBaseData[skillname][6]) break;
                        strb.append("|");
                        strb.append("\u4e0b\u4e00\u7b49\u7ea7\uff1a\u589e\u52a0\u9632\u5fa1 " + this.canvas.rolem.passiveSkillData[1][this.canvas.rolem.skillState[skillname][1] + 1] + " \uff05");
                        break;
                    }
                    case 15: {
                        if (this.canvas.rolem.skillState[skillname][0] == 0) {
                            strb.append("\u4e0b\u4e00\u7b49\u7ea7\uff1a\u589e\u52a0\u5fc5\u6740\u4f24\u5bb3 " + this.canvas.rolem.passiveSkillData[2][this.canvas.rolem.skillState[skillname][1] + 1] + " \uff05");
                            break;
                        }
                        strb.append("\u5f53\u524d\u589e\u52a0\u5fc5\u6740\u4f24\u5bb3\uff1a" + this.canvas.rolem.passiveSkillData[2][this.canvas.rolem.skillState[skillname][1]] + " \uff05");
                        if (this.canvas.rolem.skillState[skillname][1] >= this.canvas.rolem.skillBaseData[skillname][6]) break;
                        strb.append("|");
                        strb.append("\u4e0b\u4e00\u7b49\u7ea7\uff1a\u589e\u52a0\u5fc5\u6740\u4f24\u5bb3 " + this.canvas.rolem.passiveSkillData[2][this.canvas.rolem.skillState[skillname][1] + 1] + " \uff05");
                        break;
                    }
                    case 16: {
                        if (this.canvas.rolem.skillState[skillname][0] == 0) {
                            strb.append("\u4e0b\u4e00\u7b49\u7ea7\uff1a\u589e\u52a0\u6301\u5251\u4f24\u5bb3 " + this.canvas.rolem.passiveSkillData[3][this.canvas.rolem.skillState[skillname][1] + 1] + " \uff05");
                            break;
                        }
                        strb.append("\u5f53\u524d\u589e\u52a0\u6301\u5251\u4f24\u5bb3\uff1a" + this.canvas.rolem.passiveSkillData[3][this.canvas.rolem.skillState[skillname][1]] + " \uff05");
                        if (this.canvas.rolem.skillState[skillname][1] >= this.canvas.rolem.skillBaseData[skillname][6]) break;
                        strb.append("|");
                        strb.append("\u4e0b\u4e00\u7b49\u7ea7\uff1a\u589e\u52a0\u6301\u5251\u4f24\u5bb3 " + this.canvas.rolem.passiveSkillData[3][this.canvas.rolem.skillState[skillname][1] + 1] + " \uff05");
                        break;
                    }
                    case 17: {
                        if (this.canvas.rolem.skillState[skillname][0] == 0) {
                            strb.append("\u4e0b\u4e00\u7b49\u7ea7\uff1a\u589e\u52a0\u66b4\u51fb\u51e0\u7387 " + this.canvas.rolem.passiveSkillData[4][this.canvas.rolem.skillState[skillname][1] + 1] + " \uff05");
                            break;
                        }
                        strb.append("\u5f53\u524d\u589e\u52a0\u66b4\u51fb\u51e0\u7387\uff1a" + this.canvas.rolem.passiveSkillData[4][this.canvas.rolem.skillState[skillname][1]] + " \uff05");
                        if (this.canvas.rolem.skillState[skillname][1] >= this.canvas.rolem.skillBaseData[skillname][6]) break;
                        strb.append("|");
                        strb.append("\u4e0b\u4e00\u7b49\u7ea7\uff1a\u589e\u52a0\u66b4\u51fb\u51e0\u7387 " + this.canvas.rolem.passiveSkillData[4][this.canvas.rolem.skillState[skillname][1] + 1] + " \uff05");
                        break;
                    }
                    case 18: {
                        if (this.canvas.rolem.skillState[skillname][0] == 0) {
                            strb.append("\u4e0b\u4e00\u7b49\u7ea7\uff1a\u589e\u52a0\u66b4\u51fb\u4f24\u5bb3 " + this.canvas.rolem.passiveSkillData[5][this.canvas.rolem.skillState[skillname][1] + 1] + " \uff05");
                            break;
                        }
                        strb.append("\u5f53\u524d\u589e\u52a0\u66b4\u51fb\u4f24\u5bb3\uff1a" + this.canvas.rolem.passiveSkillData[5][this.canvas.rolem.skillState[skillname][1]] + " \uff05");
                        if (this.canvas.rolem.skillState[skillname][1] >= this.canvas.rolem.skillBaseData[skillname][6]) break;
                        strb.append("|");
                        strb.append("\u4e0b\u4e00\u7b49\u7ea7\uff1a\u589e\u52a0\u66b4\u51fb\u4f24\u5bb3 " + this.canvas.rolem.passiveSkillData[5][this.canvas.rolem.skillState[skillname][1] + 1] + " \uff05");
                        break;
                    }
                    case 19: {
                        if (this.canvas.rolem.skillState[skillname][0] == 0) {
                            strb.append("\u4e0b\u4e00\u7b49\u7ea7\uff1a");
                            strb.append("|");
                            strb.append("\u589e\u52a0\u751f\u547d\u4e0a\u9650 " + this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[skillname][1] + 1) * 2] + " \uff05");
                            strb.append("|");
                            strb.append("\u589e\u52a0\u80fd\u91cf\u4e0a\u9650 " + this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[skillname][1] + 1) * 2 + 1] + " \uff05");
                            break;
                        }
                        strb.append("\u5f53\u524d\u589e\u52a0\u751f\u547d\u4e0a\u9650 " + this.canvas.rolem.passiveSkillData[6][this.canvas.rolem.skillState[skillname][1] * 2] + " \uff05");
                        strb.append("|");
                        strb.append("\u5f53\u524d\u589e\u52a0\u80fd\u91cf\u4e0a\u9650 " + this.canvas.rolem.passiveSkillData[6][this.canvas.rolem.skillState[skillname][1] * 2 + 1] + " \uff05");
                        if (this.canvas.rolem.skillState[skillname][1] >= this.canvas.rolem.skillBaseData[skillname][6]) break;
                        strb.append("|");
                        strb.append("\u4e0b\u4e00\u7b49\u7ea7\uff1a");
                        strb.append("|");
                        strb.append("\u589e\u52a0\u751f\u547d\u4e0a\u9650 " + this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[skillname][1] + 1) * 2] + " \uff05");
                        strb.append("|");
                        strb.append("\u589e\u52a0\u80fd\u91cf\u4e0a\u9650 " + this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[skillname][1] + 1) * 2 + 1] + " \uff05");
                        break;
                    }
                    case 20: {
                        strb.append("\u65f6\u95f4\u505c\u6b62 10 \u79d2");
                        strb.append("|");
                        strb.append("\u80fd\u91cf\u6d88\u8017\uff1a" + this.canvas.rolem.getMagicUseed(skillname));
                    }
                }
            }
            this.scroll.setscroll(strb.toString(), 1, 85, 175, 505, 44, 1, 20);
        }
    }

    private void drawskillbasedata(byte skillname) {
        this.drawlabel("\u6280\u80fd\u6570\u636e", 80, 148, 4, 1);
        this.scroll.drawscroll(0, 1);
    }

    private void drawskillinfo() {
        this.drawlabel("\u6280\u80fd\u63cf\u8ff0", 80, 243, 4, 2);
        this.scroll.drawscroll();
    }

    public void setSkillKey() {
        this.changeCommandState((byte)5);
    }

    private void drawSkillLine() {
        for (int i = 0; i < this.skillLineData[this.skillBoxIndex].length; i += 4) {
            this.canvas.fillRect(2785936, this.skillLineData[this.skillBoxIndex][i], this.skillLineData[this.skillBoxIndex][i + 1], this.skillLineData[this.skillBoxIndex][i + 2], this.skillLineData[this.skillBoxIndex][i + 3]);
        }
    }

    private void skillBoxKeyProcess() {
        switch (this.skillBoxState) {
            case 0: {
                if (Control.IsKeyDown(16400)) {
                    this.skillBoxIndex = (byte)(this.skillBoxIndex - 1);
                    this.skillBoxIndex = this.skillBoxIndex < 0 ? (byte)0 : this.skillBoxIndex;
                    break;
                }
                if (Control.IsKeyDown(32832)) {
                    this.skillBoxIndex = (byte)(this.skillBoxIndex + 1);
                    this.skillBoxIndex = this.skillBoxIndex > 1 ? (byte)1 : this.skillBoxIndex;
                    break;
                }
                if (Control.IsKeyDown(327712)) {
                    this.skillBoxSeletedIndex = 0;
                    this.skillBoxState = 1;
                    this.setSkillInfo1(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                    this.changeActionUiState((byte)9, (byte)7, (byte)0);
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                if (this.canvas.pointer.skillFromState == 1) {
                    this.canvas.pointer.skillFromState = 0;
                    this.canvas.changeGameState(5);
                    break;
                }
                if (this.canvas.pointer.skillFromState == 2) {
                    this.canvas.pointer.skillFromState = 0;
                    this.canvas.mapm.iscityflushinfo = true;
                    this.canvas.changeGameState(4);
                    break;
                }
                this.changeUiState((byte)1);
                break;
            }
            case 1: {
                if (Control.IsKeyDown(4100)) {
                    if (this.isCommand) {
                        if (this.commandState < 0 || this.commandState >= this.SKILLCOMMANDTOTAL.length) break;
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex - 1);
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex < 0 ? this.SKILLCOMMANDTOTAL[this.commandState] - 1 : this.commandSeletedIndex);
                        break;
                    }
                    if (this.skillKeyData[this.skillBoxIndex][0][this.skillBoxSeletedIndex] == -1 || this.skillcurrentactionstate != 0) break;
                    this.skillBoxSeletedIndex = this.skillKeyData[this.skillBoxIndex][0][this.skillBoxSeletedIndex];
                    this.setSkillInfo1(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                    break;
                }
                if (Control.IsKeyDown(8448)) {
                    if (this.isCommand) {
                        if (this.commandState < 0 || this.commandState >= this.SKILLCOMMANDTOTAL.length) break;
                        this.commandSeletedIndex = (byte)(this.commandSeletedIndex + 1);
                        this.commandSeletedIndex = this.commandSeletedIndex > this.SKILLCOMMANDTOTAL[this.commandState] - 1 ? (byte)0 : this.commandSeletedIndex;
                        break;
                    }
                    if (this.skillKeyData[this.skillBoxIndex][1][this.skillBoxSeletedIndex] == -1 || this.skillcurrentactionstate != 0) break;
                    this.skillBoxSeletedIndex = this.skillKeyData[this.skillBoxIndex][1][this.skillBoxSeletedIndex];
                    this.setSkillInfo1(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                    break;
                }
                if (Control.IsKeyDown(16400)) {
                    if (this.skillKeyData[this.skillBoxIndex][2][this.skillBoxSeletedIndex] == -1 || this.isCommand || this.skillcurrentactionstate != 0) break;
                    this.skillBoxSeletedIndex = this.skillKeyData[this.skillBoxIndex][2][this.skillBoxSeletedIndex];
                    this.setSkillInfo1(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                    break;
                }
                if (Control.IsKeyDown(32832)) {
                    if (this.skillKeyData[this.skillBoxIndex][3][this.skillBoxSeletedIndex] == -1 || this.isCommand || this.skillcurrentactionstate != 0) break;
                    this.skillBoxSeletedIndex = this.skillKeyData[this.skillBoxIndex][3][this.skillBoxSeletedIndex];
                    this.setSkillInfo1(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                    break;
                }
                if (Control.IsKeyDown(327712)) {
                    if (this.isCommand) {
                        switch (this.commandState) {
                            case 0: {
                                switch (this.commandSeletedIndex) {
                                    case 0: {
                                        boolean tempstate = false;
                                        if (this.canvas.rolem.playerCurrentSkillPoint < 1) {
                                            this.setCommandShowTime();
                                            this.changeCommandState((byte)8);
                                            this.commandStr = "\u5f53\u524d\u6ca1\u6709\u53ef\u7528\u7684\u6280\u80fd\u70b9";
                                            tempstate = true;
                                            break;
                                        }
                                        if (!tempstate && this.canvas.rolem.skillBaseData[this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]][7] > -1 && this.canvas.rolem.skillState[this.canvas.rolem.skillBaseData[this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]][7]][1] < this.canvas.rolem.skillBaseData[this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]][8]) {
                                            this.setCommandShowTime();
                                            this.changeCommandState((byte)8);
                                            this.commandStr = "\u9700\u8981\u4e0a\u4e00\u7ea7\u522b\u6280\u80fd\u7b49\u7ea7\u672a\u8fbe\u5230";
                                            tempstate = true;
                                        }
                                        if (tempstate) break;
                                        this.canvas.rolem.skillState[this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]][0] = 1;
                                        byte[] byArray = this.canvas.rolem.skillState[this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]];
                                        byArray[1] = (byte)(byArray[1] + 1);
                                        --this.canvas.rolem.playerCurrentSkillPoint;
                                        ++this.canvas.rolem.playerUseedSkillPoint;
                                        if (this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex] == 19) {
                                            this.canvas.rolem.fplayerbox[0].baseLife[0] = this.canvas.rolem.fplayerbox[0].baseLife[0] + (this.canvas.rolem.passiveSkillData[6][this.canvas.rolem.skillState[19][1] * 2] - this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[19][1] - 1) * 2]) * this.canvas.rolem.getRoleLife((byte)0, this.canvas.rolem.playerLevel) / 100;
                                            this.canvas.rolem.fplayerbox[0].life[0] = this.canvas.rolem.fplayerbox[0].life[0] + (this.canvas.rolem.passiveSkillData[6][this.canvas.rolem.skillState[19][1] * 2] - this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[19][1] - 1) * 2]) * this.canvas.rolem.getRoleLife((byte)0, this.canvas.rolem.playerLevel) / 100;
                                            this.canvas.rolem.fplayerbox[0].baseMagic[0] = this.canvas.rolem.fplayerbox[0].baseMagic[0] + (this.canvas.rolem.passiveSkillData[6][this.canvas.rolem.skillState[19][1] * 2] - this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[19][1] - 1) * 2]) * this.canvas.rolem.getRoleMagic((byte)0, this.canvas.rolem.playerLevel) / 100;
                                            this.canvas.rolem.fplayerbox[0].magic[0] = this.canvas.rolem.fplayerbox[0].magic[0] + (this.canvas.rolem.passiveSkillData[6][this.canvas.rolem.skillState[19][1] * 2] - this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[19][1] - 1) * 2]) * this.canvas.rolem.getRoleMagic((byte)0, this.canvas.rolem.playerLevel) / 100;
                                        }
                                        this.finishCommand();
                                        this.skillcurrentactionstate = 1;
                                        this.skillframeindex = 0;
                                        break;
                                    }
                                    case 1: {
                                        this.skillBoxState = (byte)2;
                                        this.setSkillInfo(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                                        this.setSkillBaseData(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                                        this.finishCommand();
                                        break;
                                    }
                                    case 2: {
                                        this.finishCommand();
                                    }
                                }
                                break;
                            }
                            case 1: {
                                switch (this.commandSeletedIndex) {
                                    case 0: {
                                        if (this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex] == this.canvas.rolem.weaponCriticalKey7[this.skillBoxIndex][0]) {
                                            this.canvas.rolem.weaponCriticalKey7[this.skillBoxIndex][0] = -1;
                                        } else if (this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex] == this.canvas.rolem.weaponCriticalKey9[this.skillBoxIndex][0]) {
                                            this.canvas.rolem.weaponCriticalKey9[this.skillBoxIndex][0] = -1;
                                        }
                                        this.finishCommand();
                                        break;
                                    }
                                    case 1: {
                                        this.skillBoxState = (byte)2;
                                        this.setSkillInfo(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                                        this.setSkillBaseData(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                                        this.finishCommand();
                                        break;
                                    }
                                    case 2: {
                                        this.finishCommand();
                                    }
                                }
                                break;
                            }
                            case 2: {
                                switch (this.commandSeletedIndex) {
                                    case 0: {
                                        this.setSkillKey();
                                        this.commandSeletedIndex = 0;
                                        break;
                                    }
                                    case 1: {
                                        this.skillBoxState = (byte)2;
                                        this.setSkillInfo(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                                        this.setSkillBaseData(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                                        this.finishCommand();
                                        break;
                                    }
                                    case 2: {
                                        this.finishCommand();
                                    }
                                }
                                break;
                            }
                            case 3: {
                                switch (this.commandSeletedIndex) {
                                    case 0: {
                                        if (this.canvas.rolem.playerCurrentSkillPoint < 1) {
                                            this.setCommandShowTime();
                                            this.changeCommandState((byte)8);
                                            this.canvas.sms.setshowsmsshop(1);
                                            this.commandStr = "\u6ca1\u6709\u6280\u80fd\u70b9\u6570";
                                            break;
                                        }
                                        if (this.canvas.rolem.skillState[this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]][1] >= this.canvas.rolem.skillBaseData[this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]][6]) break;
                                        byte[] byArray = this.canvas.rolem.skillState[this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]];
                                        byArray[1] = (byte)(byArray[1] + 1);
                                        --this.canvas.rolem.playerCurrentSkillPoint;
                                        ++this.canvas.rolem.playerUseedSkillPoint;
                                        if (this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex] == 19) {
                                            this.canvas.rolem.fplayerbox[0].baseLife[0] = this.canvas.rolem.fplayerbox[0].baseLife[0] + (this.canvas.rolem.passiveSkillData[6][this.canvas.rolem.skillState[19][1] * 2] - this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[19][1] - 1) * 2]) * this.canvas.rolem.getRoleLife((byte)0, this.canvas.rolem.playerLevel) / 100;
                                            this.canvas.rolem.fplayerbox[0].life[0] = this.canvas.rolem.fplayerbox[0].life[0] + (this.canvas.rolem.passiveSkillData[6][this.canvas.rolem.skillState[19][1] * 2] - this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[19][1] - 1) * 2]) * this.canvas.rolem.getRoleLife((byte)0, this.canvas.rolem.playerLevel) / 100;
                                            this.canvas.rolem.fplayerbox[0].baseMagic[0] = this.canvas.rolem.fplayerbox[0].baseMagic[0] + (this.canvas.rolem.passiveSkillData[6][this.canvas.rolem.skillState[19][1] * 2] - this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[19][1] - 1) * 2]) * this.canvas.rolem.getRoleMagic((byte)0, this.canvas.rolem.playerLevel) / 100;
                                            this.canvas.rolem.fplayerbox[0].magic[0] = this.canvas.rolem.fplayerbox[0].magic[0] + (this.canvas.rolem.passiveSkillData[6][this.canvas.rolem.skillState[19][1] * 2] - this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[19][1] - 1) * 2]) * this.canvas.rolem.getRoleMagic((byte)0, this.canvas.rolem.playerLevel) / 100;
                                        }
                                        this.skillcurrentactionstate = (byte)2;
                                        this.finishCommand();
                                        break;
                                    }
                                    case 1: {
                                        if (this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex] == this.canvas.rolem.weaponCriticalKey7[this.skillBoxIndex][0]) {
                                            this.canvas.rolem.weaponCriticalKey7[this.skillBoxIndex][0] = -1;
                                        } else if (this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex] == this.canvas.rolem.weaponCriticalKey9[this.skillBoxIndex][0]) {
                                            this.canvas.rolem.weaponCriticalKey9[this.skillBoxIndex][0] = -1;
                                        }
                                        this.finishCommand();
                                        break;
                                    }
                                    case 2: {
                                        this.skillBoxState = (byte)2;
                                        this.setSkillInfo(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                                        this.setSkillBaseData(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                                        this.finishCommand();
                                        break;
                                    }
                                    case 3: {
                                        this.finishCommand();
                                    }
                                }
                                break;
                            }
                            case 4: {
                                switch (this.commandSeletedIndex) {
                                    case 0: {
                                        if (this.canvas.rolem.playerCurrentSkillPoint < 1) {
                                            this.setCommandShowTime();
                                            this.changeCommandState((byte)8);
                                            this.canvas.sms.setshowsmsshop(1);
                                            this.commandStr = "\u6ca1\u6709\u6280\u80fd\u70b9\u6570";
                                            break;
                                        }
                                        if (this.canvas.rolem.skillState[this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]][1] >= this.canvas.rolem.skillBaseData[this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]][6]) break;
                                        byte[] byArray = this.canvas.rolem.skillState[this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]];
                                        byArray[1] = (byte)(byArray[1] + 1);
                                        --this.canvas.rolem.playerCurrentSkillPoint;
                                        ++this.canvas.rolem.playerUseedSkillPoint;
                                        if (this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex] == 19) {
                                            this.canvas.rolem.fplayerbox[0].baseLife[0] = this.canvas.rolem.fplayerbox[0].baseLife[0] + (this.canvas.rolem.passiveSkillData[6][this.canvas.rolem.skillState[19][1] * 2] - this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[19][1] - 1) * 2]) * this.canvas.rolem.getRoleLife((byte)0, this.canvas.rolem.playerLevel) / 100;
                                            this.canvas.rolem.fplayerbox[0].life[0] = this.canvas.rolem.fplayerbox[0].life[0] + (this.canvas.rolem.passiveSkillData[6][this.canvas.rolem.skillState[19][1] * 2] - this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[19][1] - 1) * 2]) * this.canvas.rolem.getRoleLife((byte)0, this.canvas.rolem.playerLevel) / 100;
                                            this.canvas.rolem.fplayerbox[0].baseMagic[0] = this.canvas.rolem.fplayerbox[0].baseMagic[0] + (this.canvas.rolem.passiveSkillData[6][this.canvas.rolem.skillState[19][1] * 2] - this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[19][1] - 1) * 2]) * this.canvas.rolem.getRoleMagic((byte)0, this.canvas.rolem.playerLevel) / 100;
                                            this.canvas.rolem.fplayerbox[0].magic[0] = this.canvas.rolem.fplayerbox[0].magic[0] + (this.canvas.rolem.passiveSkillData[6][this.canvas.rolem.skillState[19][1] * 2] - this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[19][1] - 1) * 2]) * this.canvas.rolem.getRoleMagic((byte)0, this.canvas.rolem.playerLevel) / 100;
                                        }
                                        this.skillcurrentactionstate = (byte)2;
                                        this.finishCommand();
                                        break;
                                    }
                                    case 1: {
                                        this.setSkillKey();
                                        this.commandSeletedIndex = 0;
                                        break;
                                    }
                                    case 2: {
                                        this.skillBoxState = (byte)2;
                                        this.setSkillInfo(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                                        this.setSkillBaseData(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                                        this.finishCommand();
                                        break;
                                    }
                                    case 3: {
                                        this.finishCommand();
                                    }
                                }
                                break;
                            }
                            case 5: {
                                switch (this.commandSeletedIndex) {
                                    case 0: {
                                        this.canvas.rolem.weaponCriticalKey7[this.skillBoxIndex][0] = this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex];
                                        this.canvas.rolem.weaponCriticalKey7[this.skillBoxIndex][1] = 0;
                                        this.finishCommand();
                                        break;
                                    }
                                    case 1: {
                                        this.canvas.rolem.weaponCriticalKey9[this.skillBoxIndex][0] = this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex];
                                        this.canvas.rolem.weaponCriticalKey9[this.skillBoxIndex][1] = 0;
                                        this.finishCommand();
                                        break;
                                    }
                                    case 2: {
                                        this.finishCommand();
                                    }
                                }
                                break;
                            }
                            case 6: {
                                switch (this.commandSeletedIndex) {
                                    case 0: {
                                        if (this.canvas.rolem.playerCurrentSkillPoint < 1) {
                                            this.setCommandShowTime();
                                            this.changeCommandState((byte)8);
                                            this.canvas.sms.setshowsmsshop(1);
                                            this.commandStr = "\u6ca1\u6709\u6280\u80fd\u70b9\u6570";
                                            break;
                                        }
                                        if (this.canvas.rolem.skillState[this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]][1] >= this.canvas.rolem.skillBaseData[this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]][6]) break;
                                        byte[] byArray = this.canvas.rolem.skillState[this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]];
                                        byArray[1] = (byte)(byArray[1] + 1);
                                        --this.canvas.rolem.playerCurrentSkillPoint;
                                        ++this.canvas.rolem.playerUseedSkillPoint;
                                        if (this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex] == 19) {
                                            this.canvas.rolem.fplayerbox[0].baseLife[0] = this.canvas.rolem.fplayerbox[0].baseLife[0] + (this.canvas.rolem.passiveSkillData[6][this.canvas.rolem.skillState[19][1] * 2] - this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[19][1] - 1) * 2]) * this.canvas.rolem.getRoleLife((byte)0, this.canvas.rolem.playerLevel) / 100;
                                            this.canvas.rolem.fplayerbox[0].life[0] = this.canvas.rolem.fplayerbox[0].life[0] + (this.canvas.rolem.passiveSkillData[6][this.canvas.rolem.skillState[19][1] * 2] - this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[19][1] - 1) * 2]) * this.canvas.rolem.getRoleLife((byte)0, this.canvas.rolem.playerLevel) / 100;
                                            this.canvas.rolem.fplayerbox[0].baseMagic[0] = this.canvas.rolem.fplayerbox[0].baseMagic[0] + (this.canvas.rolem.passiveSkillData[6][this.canvas.rolem.skillState[19][1] * 2] - this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[19][1] - 1) * 2]) * this.canvas.rolem.getRoleMagic((byte)0, this.canvas.rolem.playerLevel) / 100;
                                            this.canvas.rolem.fplayerbox[0].magic[0] = this.canvas.rolem.fplayerbox[0].magic[0] + (this.canvas.rolem.passiveSkillData[6][this.canvas.rolem.skillState[19][1] * 2] - this.canvas.rolem.passiveSkillData[6][(this.canvas.rolem.skillState[19][1] - 1) * 2]) * this.canvas.rolem.getRoleMagic((byte)0, this.canvas.rolem.playerLevel) / 100;
                                        }
                                        this.skillcurrentactionstate = (byte)2;
                                        this.finishCommand();
                                        break;
                                    }
                                    case 1: {
                                        this.skillBoxState = (byte)2;
                                        this.setSkillInfo(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                                        this.setSkillBaseData(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                                        this.finishCommand();
                                        break;
                                    }
                                    case 2: {
                                        this.finishCommand();
                                    }
                                }
                                break;
                            }
                            case 7: {
                                switch (this.commandSeletedIndex) {
                                    case 0: {
                                        this.skillBoxState = (byte)2;
                                        this.setSkillInfo(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                                        this.setSkillBaseData(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
                                        this.finishCommand();
                                        break;
                                    }
                                    case 1: {
                                        this.finishCommand();
                                    }
                                }
                                break;
                            }
                            case 8: {
                                this.finishCommand();
                            }
                        }
                        break;
                    }
                    if (this.canvas.rolem.skillState[this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]][0] == 0) {
                        if (this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex] == 20) {
                            this.startCommand();
                            this.changeCommandState((byte)7);
                            this.commandSeletedIndex = 0;
                            break;
                        }
                        this.startCommand();
                        this.changeCommandState((byte)0);
                        this.commandSeletedIndex = 0;
                        break;
                    }
                    if (this.canvas.rolem.skillState[this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]][1] >= this.canvas.rolem.skillBaseData[this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]][6]) {
                        if (this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex] == this.canvas.rolem.weaponCriticalKey7[this.skillBoxIndex][0] || this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex] == this.canvas.rolem.weaponCriticalKey9[this.skillBoxIndex][0]) {
                            this.startCommand();
                            this.changeCommandState((byte)1);
                            this.commandSeletedIndex = 0;
                            break;
                        }
                        if (this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex] == 20) {
                            this.startCommand();
                            this.changeCommandState((byte)2);
                            this.commandSeletedIndex = 0;
                            break;
                        }
                        if (this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex] > 12 && this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex] < 20) {
                            this.startCommand();
                            this.changeCommandState((byte)7);
                            this.commandSeletedIndex = 0;
                            break;
                        }
                        this.startCommand();
                        this.changeCommandState((byte)2);
                        this.commandSeletedIndex = 0;
                        break;
                    }
                    if (this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex] == this.canvas.rolem.weaponCriticalKey7[this.skillBoxIndex][0] || this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex] == this.canvas.rolem.weaponCriticalKey9[this.skillBoxIndex][0]) {
                        this.startCommand();
                        this.changeCommandState((byte)3);
                        this.commandSeletedIndex = 0;
                        break;
                    }
                    if (this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex] > 12 && this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex] < 20) {
                        this.startCommand();
                        this.changeCommandState((byte)6);
                        this.commandSeletedIndex = 0;
                        break;
                    }
                    this.startCommand();
                    this.changeCommandState((byte)4);
                    this.commandSeletedIndex = 0;
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                if (this.isCommand) {
                    switch (this.commandState) {
                        case 0: 
                        case 1: 
                        case 2: 
                        case 3: 
                        case 4: 
                        case 6: 
                        case 7: 
                        case 8: {
                            this.finishCommand();
                            break;
                        }
                        case 5: {
                            this.changeCommandState(this.lastCommandState);
                        }
                    }
                    break;
                }
                this.skillBoxState = 0;
                this.changeActionUiState((byte)9, (byte)8, (byte)0);
                break;
            }
            case 2: {
                if (!Control.IsKeyDown(131072)) break;
                this.skillBoxState = 1;
                this.setSkillInfo1(this.skillIndexData[this.skillBoxIndex][this.skillBoxSeletedIndex]);
            }
        }
    }

    private void commandProcess() {
        if (this.isCommand && this.commandShowTime > 0 && --this.commandShowTime <= 0) {
            if ((this.commandState == 1 || this.commandState == 2 || this.commandState == 3) && this.canvas.gamestate == 7 && this.uiState == 11 && this.cityIntroBoxState == 0) {
                this.changeCommandState((byte)4);
            } else {
                this.finishCommand();
            }
        }
    }

    public void changeRideImage() {
        switch (this.changeRideImageTime) {
            case 0: {
                this.canvas.rolem.clearRide(this.canvas.rolem.lastRideIndex);
                break;
            }
            case 1: {
                this.canvas.rolem.initRide(this.canvas.rolem.currentRideIndex);
                break;
            }
            case 2: {
                System.gc();
            }
        }
        this.changeRideImageTime = (byte)(this.changeRideImageTime + 1);
    }

    private void clearRideImage() {
        switch (this.changeRideImageTime) {
            case 0: {
                this.canvas.rolem.clearRide(this.canvas.rolem.lastRideIndex);
                break;
            }
            case 1: {
                System.gc();
            }
        }
        this.changeRideImageTime = (byte)(this.changeRideImageTime + 1);
    }

    private void drawRideBox() {
        byte i;
        this.drawStaticUiBack((short)0);
        this.drawStaticUi((short)88);
        this.drawUiModule(259, LABEL_X[0][0] - 3, 41, 36, 0);
        this.drawStaticUi((short)112);
        this.drawStaticUi((short)124, 320, 55);
        this.drawStaticUi((short)113, 320, 330);
        this.drawjiantou(0, 4, 92, 70, 20);
        this.drawjiantou(0, 5, 532, 70, 20);
        this.drawStaticUi((short)16, 0, 360);
        for (i = 0; i < 3; i = (byte)(i + 1)) {
            if (i == this.rideBoxSeletedIndex) {
                this.drawUiModule(0, 229 + this.rideBoxSeletedIndex * 150 - 80, 61, 20, 0);
            } else {
                this.drawUiModule(3, 230 + i * 150 - 80, 60, 20, 0);
            }
            if (!this.canvas.rolem.isrideopen(i)) continue;
            this.drawarmicon(76 + this.rideiconindex[i], 230 + i * 150 - 80, 60);
            if (i == this.rideBoxSeletedIndex) {
                this.drawitemiconcheck(230 + i * 150 - 80, 60, 128);
            } else {
                this.drawitemiconmembrane(230 + i * 150 - 80, 60, 127);
            }
            if (this.canvas.rolem.currentRideIndex <= -1) continue;
            this.drawUiModule(180, 255 + this.canvas.rolem.currentRideIndex * 150 - 80, 87, 20, 0);
        }
        this.drawridedata(this.rideBoxSeletedIndex);
        this.drawrideinfo();
        if (this.isCommand) {
            this.drawUiBack();
            switch (this.commandState) {
                case 0: 
                case 1: {
                    this.drawStaticUi((short)126);
                    for (i = 0; i < this.rideBoxCommand[this.commandState].length; i = (byte)(i + 1)) {
                        if (i == this.commandSeletedIndex) {
                            this.drawChoiceBox(320, 125 + this.commandSeletedIndex * 45);
                            this.canvas.drawColorString(this.rideBoxCommand[this.commandState][i], 320, 128 + this.commandSeletedIndex * 45, 17, 15597319, 0);
                            continue;
                        }
                        this.canvas.drawColorString(this.rideBoxCommand[this.commandState][i], 320, 128 + i * 45, 17, 0xFFFFFF, 0);
                    }
                    break;
                }
                case 2: {
                    this.drawStaticUi((short)26);
                    this.canvas.drawColorString(this.commandStr, 320, 133, 17, 0xFFFFFF, 0);
                    break;
                }
                case 3: {
                    this.changeRideImage();
                    this.drawminiloading(this.changeRideImageTime, 3);
                    if (this.changeRideImageTime != 3) break;
                    this.commandStr = "\u88c5\u5907\u6210\u529f\u3002";
                    this.setCommandShowTime();
                    this.changeCommandState((byte)2);
                    break;
                }
                case 4: {
                    this.clearRideImage();
                    this.drawminiloading(this.changeRideImageTime, 3);
                    if (this.changeRideImageTime != 2) break;
                    this.commandStr = "\u5378\u4e0b\u6210\u529f\u3002";
                    this.setCommandShowTime();
                    this.changeCommandState((byte)2);
                }
            }
        }
    }

    public void drawminiloading(int time, int total) {
        int w = this.changeRideImageTime * 100 / 3 * 50 / 100;
        this.canvas.fillRect(15597319, 300, 132, w, 2);
        this.canvas.drawRect(15597319, 298, 130, 54, 5);
    }

    private void drawridedata(int index) {
        this.canvas.gameUi.drawlabel("\u5750\u9a91\u6570\u636e", 80, 118, 4, 1);
        if (this.canvas.rolem.isrideopen((byte)index)) {
            this.canvas.drawString("\u9644\u52a0\u653b\u51fb\uff1a" + this.canvas.rolem.rideData[index][0] * 10 + " %", 90, 138, 20, 0xFFFFFF);
            this.canvas.drawString("\u9644\u52a0\u9632\u5fa1\uff1a" + this.canvas.rolem.rideData[index][1] * 100 + " %", 305, 138, 20, 0xFFFFFF);
            this.canvas.drawString("\u9644\u52a0\u66b4\u51fb\u7387\uff1a" + this.canvas.rolem.rideData[index][7] + " %", 90, 155, 20, 0xFFFFFF);
            this.canvas.drawString("\u9644\u52a0\u66b4\u51fb\u503c\uff1a" + this.canvas.rolem.rideData[index][8] * 100 + " %", 305, 155, 20, 0xFFFFFF);
            this.canvas.drawString("\u8010\u4e45\u5ea6\uff1a", 90, 172, 20, 0xFFFFFF);
            this.drawridenaijiu(this.canvas.rolem.rideLife[index], this.canvas.rolem.rideData[index][2]);
        }
    }

    private void drawridenaijiu(int life, int baselife) {
        int w = life;
        int bw = baselife;
        this.canvas.drawRect(1114107, 154, 175, 141, 11);
        this.canvas.fillRect(2873389, 155, 176, 140 * w / bw, 2);
        this.canvas.fillRect(7405362, 155, 178, 140 * w / bw, 4);
        this.canvas.fillRect(2873389, 155, 182, 140 * w / bw, 4);
        this.canvas.drawColorString("" + life + "/" + baselife, 225, 188, 33, 0xFFFFFF, 0);
    }

    public void drawrideinfo() {
        this.canvas.gameUi.drawlabel("\u5750\u9a91\u63cf\u8ff0", 80, 216, 4, 2);
        this.scroll.drawscroll();
    }

    public void setRideInfo(byte ridename) {
        this.scroll.close();
        if (this.canvas.rolem.isrideopen(ridename)) {
            StringBuffer strb = new StringBuffer("");
            strb.append("$1");
            strb.append(this.rideinfo[ridename]);
            this.scroll.setscroll(strb.toString(), 90, 238, 400, 80, 1, 20);
        }
    }

    private void rideBoxKeyProcess() {
        if (Control.IsKeyDown(4100)) {
            if (this.isCommand && this.commandState >= 0 && this.commandState < this.RIDEBOXCOMMANDTOTAL.length) {
                this.commandSeletedIndex = (byte)(this.commandSeletedIndex - 1);
                this.commandSeletedIndex = this.commandSeletedIndex < 0 ? this.RIDEBOXCOMMANDTOTAL[this.commandState] : this.commandSeletedIndex;
            }
        } else if (Control.IsKeyDown(8448)) {
            if (this.isCommand && this.commandState >= 0 && this.commandState < this.RIDEBOXCOMMANDTOTAL.length) {
                this.commandSeletedIndex = (byte)(this.commandSeletedIndex + 1);
                this.commandSeletedIndex = this.commandSeletedIndex > this.RIDEBOXCOMMANDTOTAL[this.commandState] ? (byte)0 : this.commandSeletedIndex;
            }
        } else if (Control.IsKeyDown(16400)) {
            if (!this.isCommand) {
                this.rideBoxSeletedIndex = (byte)(this.rideBoxSeletedIndex - 1);
                this.rideBoxSeletedIndex = this.rideBoxSeletedIndex < 0 ? (byte)0 : this.rideBoxSeletedIndex;
                this.setRideInfo(this.rideBoxSeletedIndex);
            }
        } else if (Control.IsKeyDown(32832)) {
            if (!this.isCommand) {
                this.rideBoxSeletedIndex = (byte)(this.rideBoxSeletedIndex + 1);
                this.rideBoxSeletedIndex = (byte)(this.rideBoxSeletedIndex > 2 ? 2 : (int)this.rideBoxSeletedIndex);
                this.setRideInfo(this.rideBoxSeletedIndex);
            }
        } else if (Control.IsKeyDown(327712)) {
            if (this.isCommand) {
                block0 : switch (this.commandState) {
                    case 0: {
                        switch (this.commandSeletedIndex) {
                            case 0: {
                                if (this.canvas.rolem.roleState > 0) {
                                    this.canvas.rolem.setPlayerState((byte)0);
                                    this.canvas.rolem.setPlayerRideDown();
                                }
                                this.changeRide((byte)-1);
                                this.changeRideImageTime = 0;
                                this.changeCommandState((byte)4);
                                break block0;
                            }
                            case 1: {
                                if (this.itemTotal[9] > 0) {
                                    this.useplayerindex = this.rideBoxSeletedIndex;
                                    this.commandStr = this.useItem(this.canvas.rolem.fplayerbox[0], (byte)9);
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)2);
                                    break block0;
                                }
                                this.commandStr = "\u80cc\u5305\u4e2d\u6ca1\u6709\u8865\u7ed9\u54c1\u3002";
                                this.setCommandShowTime();
                                this.changeCommandState((byte)2);
                                break block0;
                            }
                            case 2: {
                                this.finishCommand();
                            }
                        }
                        break;
                    }
                    case 1: {
                        switch (this.commandSeletedIndex) {
                            case 0: {
                                if (this.canvas.rolem.roleState == 0) {
                                    this.changeRide(this.rideBoxSeletedIndex);
                                    this.changeRideImageTime = 0;
                                    this.changeCommandState((byte)3);
                                    break block0;
                                }
                                this.canvas.rolem.setPlayerState((byte)0);
                                this.canvas.rolem.setPlayerRideDown();
                                this.changeRide(this.rideBoxSeletedIndex);
                                this.changeRideImageTime = 0;
                                this.changeCommandState((byte)3);
                                break block0;
                            }
                            case 1: {
                                if (this.itemTotal[9] > 0) {
                                    this.useplayerindex = this.rideBoxSeletedIndex;
                                    this.commandStr = this.useItem(this.canvas.rolem.fplayerbox[0], (byte)9);
                                    this.setCommandShowTime();
                                    this.changeCommandState((byte)2);
                                    break block0;
                                }
                                this.commandStr = "\u80cc\u5305\u4e2d\u6ca1\u6709\u8865\u7ed9\u54c1\u3002";
                                this.setCommandShowTime();
                                this.changeCommandState((byte)2);
                                break block0;
                            }
                            case 2: {
                                this.finishCommand();
                            }
                        }
                        break;
                    }
                    case 2: {
                        this.finishCommand();
                    }
                }
            } else if (this.canvas.rolem.isrideopen(this.rideBoxSeletedIndex)) {
                if (this.rideBoxSeletedIndex == this.canvas.rolem.currentRideIndex) {
                    this.startCommand();
                    this.commandSeletedIndex = 0;
                    this.changeCommandState((byte)0);
                } else {
                    this.startCommand();
                    this.commandSeletedIndex = 0;
                    this.changeCommandState((byte)1);
                }
            }
        } else if (Control.IsKeyDown(131072)) {
            if (this.isCommand) {
                switch (this.commandState) {
                    case 0: 
                    case 1: 
                    case 2: {
                        this.finishCommand();
                    }
                }
            } else {
                this.setpackboxData();
                this.changeUiState((byte)20);
            }
        }
    }

    public void changeRide(byte index) {
        this.canvas.rolem.lastRideIndex = this.canvas.rolem.currentRideIndex;
        this.canvas.rolem.currentRideIndex = index;
    }

    public void drawjindutiaoV(int x, int y, int w, int h, int selectedindex, int total, byte jindutiaotype) {
        int sy = 0;
        int yy = 0;
        switch (jindutiaotype) {
            case 0: {
                total = total < 12 ? 12 : total;
                yy = h / ((total - 1) / 8 + 1);
                break;
            }
            case 1: {
                total = total < 1 ? 1 : total;
                yy = h / total;
                break;
            }
            case 2: {
                total = total < 16 ? 16 : total;
                yy = h / ((total - 1) / 1 + 1);
            }
        }
        yy = yy < 1 ? 1 : yy;
        switch (jindutiaotype) {
            case 0: {
                if (selectedindex / 8 == (total - 1) / 8) {
                    sy = y + h - yy;
                    break;
                }
                sy = y + selectedindex / 8 * yy;
                break;
            }
            case 1: {
                if (selectedindex == total - 1) {
                    sy = y + h - yy;
                    break;
                }
                sy = y + selectedindex * yy;
                break;
            }
            case 2: {
                sy = selectedindex / 1 == (total - 1) / 1 ? y + h - yy : y + selectedindex / 1 * yy;
            }
        }
        this.canvas.fillRect(62715, x, sy, w, yy);
    }

    public void drawhelpjindutiaoV(int x, int y, int w, int h, int selectedindex, int total, byte helpline) {
        int sy = 0;
        int yy = 0;
        int total1 = 0;
        int speedy = 0;
        total = total < 1 ? 1 : total;
        yy = h / total * helpline;
        yy = yy > h ? h : yy;
        yy = yy < 1 ? 1 : yy;
        speedy = (h - yy) / (total - helpline);
        speedy = speedy < 1 ? 1 : speedy;
        total1 = total - helpline;
        total1 = total1 < 0 ? 0 : total1;
        sy = selectedindex == total1 ? y + h - yy : y + selectedindex * speedy;
        this.canvas.fillRect(62715, x, sy, w, yy);
    }

    public void readData() {
        Util.readXls2DUTF("/data/iteminfo.data");
        this.iteminfo = Util.xls2DUTFdata;
        Util.readXls2DUTF("/data/skillinfo.data");
        this.skillinfo = Util.xls2DUTFdata;
        Util.clearXls2DUTF();
        this.initmission();
    }

    public void putOnWeapon(byte index) {
        int weaponIndex = -1;
        byte weaponName = this.weaponPack[0][index];
        weaponIndex = this.setCurrentWeaponType(index);
        this.canvas.rolem.playerCurrentWeapon[weaponIndex] = weaponName;
        this.canvas.rolem.playerCurrentWeaponAttack[weaponIndex] = this.canvas.rolem.armData[weaponName - 44][0];
        this.canvas.rolem.playerCurrentWeaponBaojiOdds[weaponIndex] = this.canvas.rolem.armData[weaponName - 44][1];
        this.canvas.rolem.playerCurrentWeaponBaojiAttack[weaponIndex] = this.canvas.rolem.armData[weaponName - 44][2];
        this.canvas.rolem.weaponActionIndex[weaponIndex] = this.canvas.rolem.armData[weaponName - 44][3];
        this.weaponPack[1][index] = weaponIndex;
    }

    public void putOffWeapon(byte weaponType) {
        this.canvas.rolem.playerCurrentWeapon[weaponType] = 0;
        this.canvas.rolem.playerCurrentWeaponAttack[weaponType] = 0;
        this.canvas.rolem.playerCurrentWeaponBaojiOdds[weaponType] = 0;
        this.canvas.rolem.weaponActionIndex[weaponType] = 0;
        for (byte i = 0; i < this.itemPackNum; i = (byte)(i + 1)) {
            if (this.weaponPack[1][i] != weaponType) continue;
            this.weaponPack[1][i] = -1;
        }
    }

    public void putOnSuit(byte index) {
        byte weaponName;
        this.canvas.rolem.playerCurrentSuit = weaponName = this.suitPack[0][index];
        this.canvas.rolem.fplayerbox[0].baseLife[0] = this.canvas.rolem.fplayerbox[0].baseLife[0] + this.canvas.rolem.armData[weaponName - 44][0];
        this.canvas.rolem.fplayerbox[0].baseMagic[0] = this.canvas.rolem.fplayerbox[0].baseMagic[0] + this.canvas.rolem.armData[weaponName - 44][1];
        this.canvas.rolem.fplayerbox[0].baseDefence[0] = this.canvas.rolem.fplayerbox[0].baseDefence[0] + this.canvas.rolem.armData[weaponName - 44][2];
        this.canvas.rolem.fplayerbox[0].life[0] = this.canvas.rolem.fplayerbox[0].life[0] + this.canvas.rolem.armData[weaponName - 44][0];
        this.canvas.rolem.fplayerbox[0].life[0] = this.canvas.rolem.fplayerbox[0].life[0] > this.canvas.rolem.fplayerbox[0].baseLife[0] ? this.canvas.rolem.fplayerbox[0].baseLife[0] : this.canvas.rolem.fplayerbox[0].life[0];
        this.canvas.rolem.fplayerbox[0].magic[0] = this.canvas.rolem.fplayerbox[0].magic[0] + this.canvas.rolem.armData[weaponName - 44][1];
        this.canvas.rolem.fplayerbox[0].magic[0] = this.canvas.rolem.fplayerbox[0].magic[0] > this.canvas.rolem.fplayerbox[0].baseMagic[0] ? this.canvas.rolem.fplayerbox[0].baseMagic[0] : this.canvas.rolem.fplayerbox[0].magic[0];
        this.canvas.rolem.fplayerbox[0].defence[0] = this.canvas.rolem.fplayerbox[0].baseDefence[0];
        this.suitPack[1][index] = 0;
    }

    public void putOffSuit() {
        int i;
        for (i = 0; i < this.suitPack[1].length; i = (int)((byte)(i + 1))) {
            if (this.suitPack[1][i] != 0) continue;
            this.suitPack[1][i] = -1;
            break;
        }
        byte weaponName = this.suitPack[0][i];
        this.canvas.rolem.playerCurrentSuit = (byte)-1;
        this.canvas.rolem.fplayerbox[0].baseLife[0] = this.canvas.rolem.fplayerbox[0].baseLife[0] - this.canvas.rolem.armData[weaponName - 44][0];
        this.canvas.rolem.fplayerbox[0].baseMagic[0] = this.canvas.rolem.fplayerbox[0].baseMagic[0] - this.canvas.rolem.armData[weaponName - 44][1];
        this.canvas.rolem.fplayerbox[0].baseDefence[0] = this.canvas.rolem.fplayerbox[0].baseDefence[0] - this.canvas.rolem.armData[weaponName - 44][2];
        this.canvas.rolem.fplayerbox[0].defence[0] = this.canvas.rolem.fplayerbox[0].baseDefence[0];
        this.canvas.rolem.fplayerbox[0].life[0] = this.canvas.rolem.fplayerbox[0].life[0] > this.canvas.rolem.fplayerbox[0].baseLife[0] ? this.canvas.rolem.fplayerbox[0].baseLife[0] : this.canvas.rolem.fplayerbox[0].life[0];
        this.canvas.rolem.fplayerbox[0].magic[0] = this.canvas.rolem.fplayerbox[0].magic[0] > this.canvas.rolem.fplayerbox[0].baseMagic[0] ? this.canvas.rolem.fplayerbox[0].baseMagic[0] : this.canvas.rolem.fplayerbox[0].magic[0];
    }

    public void changeSuit(byte index) {
        this.putOffSuit();
        this.putOnSuit(index);
    }

    public void putOnPlus(byte plusIndex, byte index) {
        byte weaponName = this.plusPack[0][index];
        this.canvas.rolem.plusData[plusIndex] = this.plusPack[2][index];
        this.canvas.rolem.playerCurrentPlus[plusIndex] = weaponName;
        byte by = plusIndex;
        this.canvas.rolem.plusState[by] = (short)(this.canvas.rolem.plusState[by] | 1 << this.plusData[weaponName - 88]);
        this.plusPack[1][index] = plusIndex;
        if ((this.canvas.rolem.playerCurrentPlus[0] == 123 || this.canvas.rolem.playerCurrentPlus[1] == 123) && this.canvas.rolem.skillState[20][0] == 0) {
            this.canvas.rolem.skillState[20][0] = 1;
            this.canvas.rolem.skillState[20][1] = 1;
        }
    }

    public void putOffPlus(byte plusIndex) {
        int i;
        for (i = 0; i < this.plusPack[1].length; i = (int)((byte)(i + 1))) {
            if (this.plusPack[1][i] != plusIndex) continue;
            this.plusPack[1][i] = -1;
            break;
        }
        byte weaponName = this.plusPack[0][i];
        this.canvas.rolem.playerCurrentPlus[plusIndex] = -1;
        this.canvas.rolem.plusData[plusIndex] = 0;
        byte by = plusIndex;
        this.canvas.rolem.plusState[by] = (short)(this.canvas.rolem.plusState[by] ^ 1 << this.plusData[weaponName - 88]);
        if (this.canvas.rolem.playerCurrentPlus[0] != 123 && this.canvas.rolem.playerCurrentPlus[1] != 123 && this.canvas.rolem.skillState[20][0] == 1) {
            this.canvas.rolem.skillState[20][0] = 0;
            this.canvas.rolem.skillState[20][1] = 0;
            this.putoffStopTime();
        }
    }

    public void putoffStopTime() {
        int i;
        for (i = 0; i < this.canvas.rolem.weaponCriticalKey7.length; i = (int)((byte)(i + 1))) {
            if (this.canvas.rolem.weaponCriticalKey7[i][0] != 20) continue;
            this.canvas.rolem.weaponCriticalKey7[i][0] = -1;
        }
        for (i = 0; i < this.canvas.rolem.weaponCriticalKey9.length; i = (int)((byte)(i + 1))) {
            if (this.canvas.rolem.weaponCriticalKey9[i][0] != 20) continue;
            this.canvas.rolem.weaponCriticalKey9[i][0] = -1;
        }
    }

    public void changePlus(byte plusIndex, byte index) {
        this.putOffPlus(plusIndex);
        this.putOnPlus(plusIndex, index);
    }

    public boolean isPutOnPlus(byte plusIndex) {
        return this.canvas.rolem.playerCurrentPlus[plusIndex] != -1;
    }

    public void initVisualKeyboard() {
        Util.tempData = new byte[1500];
        Util.position[0] = 0;
        Util.getStream(Util.tempData, "/data/visualkeyboard.data", 0);
        this.visualKeyboardModules = Util.loadInfShort(Util.tempData, this.visualKeyboardModules, Util.position);
        this.visualKeyboardFrames = Util.loadInfShort(Util.tempData, this.visualKeyboardFrames, Util.position);
        this.visualKeyboardActions = Util.loadInfShort(Util.tempData, this.visualKeyboardActions, Util.position);
        this.visualKeyboardImage = this.createUiImage(this.visualKeyboardImgData);
        Util.tempData = null;
    }

    public void drawVisualKeyboardStaticFrame(int frameindex) {
        for (int i = 0; i < this.visualKeyboardFrames[frameindex].length; i += 4) {
            short moduleIndex = this.visualKeyboardFrames[frameindex][i];
            this.canvas.drawModule(this.visualKeyboardImage[this.visualKeyboardModules[moduleIndex * 5]], this.visualKeyboardFrames[frameindex][i + 1], (int)this.visualKeyboardFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.visualKeyboardFrames[frameindex][i + 3]], 320, 180, this.visualKeyboardModules, (int)moduleIndex);
        }
    }

    public void drawVisualKeyboardStaticFrame(int frameindex, int x, int y) {
        for (int i = 0; i < this.visualKeyboardFrames[frameindex].length; i += 4) {
            short moduleIndex = this.visualKeyboardFrames[frameindex][i];
            this.canvas.drawModule(this.visualKeyboardImage[this.visualKeyboardModules[moduleIndex * 5]], this.visualKeyboardFrames[frameindex][i + 1], (int)this.visualKeyboardFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.visualKeyboardFrames[frameindex][i + 3]], x, y, this.visualKeyboardModules, (int)moduleIndex);
        }
    }

    public void drawVisualKeyboardStaticFrame(int frameindex, int x, int y, int trans) {
        for (int i = 0; i < this.visualKeyboardFrames[frameindex].length; i += 4) {
            short moduleIndex = this.visualKeyboardFrames[frameindex][i];
            this.canvas.drawModule(this.visualKeyboardImage[this.visualKeyboardModules[moduleIndex * 5]], this.visualKeyboardFrames[frameindex][i + 1], (int)this.visualKeyboardFrames[frameindex][i + 2], 36, trans, x, y, this.visualKeyboardModules, (int)moduleIndex);
        }
    }

    public void drawVisualKeyboardModule(int mindex, int x, int y, int anchor, int trans) {
        this.canvas.drawImage(this.visualKeyboardImage[this.visualKeyboardModules[mindex * 5]], x, y, anchor, trans, this.visualKeyboardModules, mindex);
    }

    public void drawVisualKeyboardSprite(byte actionIndex, int x, int y, int trans) {
        if (this.isVisualKeyboardActionOver) {
            this.visualKeyboardActionFrameIndex = 0;
            this.visualKeyboardFrameDelay = 0;
            this.isVisualKeyboardActionOver = false;
            return;
        }
        if (this.visualKeyboardActions[actionIndex].length - 1 <= this.visualKeyboardActionFrameIndex + 1) {
            this.visualKeyboardActionFrameIndex = 0;
            this.visualKeyboardFrameDelay = 0;
            return;
        }
        short frameindex = this.visualKeyboardActions[actionIndex][this.visualKeyboardActionFrameIndex + 1];
        short moduleIndex = 0;
        for (int i = 0; i < this.visualKeyboardFrames[frameindex].length; i += 4) {
            moduleIndex = this.visualKeyboardFrames[frameindex][i];
            this.canvas.drawModule(this.visualKeyboardImage[this.visualKeyboardModules[moduleIndex * 5]], this.visualKeyboardFrames[frameindex][i + 1], (int)this.visualKeyboardFrames[frameindex][i + 2], 36, trans, x, y, this.visualKeyboardModules, (int)moduleIndex);
        }
        this.nextVisualKeyboardFrame(actionIndex);
    }

    public void drawVisualKeyboardSprite(byte actionIndex, int x, int y) {
        if (this.isVisualKeyboardActionOver) {
            this.visualKeyboardActionFrameIndex = 0;
            this.visualKeyboardFrameDelay = 0;
            this.isVisualKeyboardActionOver = false;
            return;
        }
        if (this.visualKeyboardActions[actionIndex].length - 1 <= this.visualKeyboardActionFrameIndex + 1) {
            this.visualKeyboardActionFrameIndex = 0;
            this.visualKeyboardFrameDelay = 0;
            return;
        }
        short frameindex = this.visualKeyboardActions[actionIndex][this.visualKeyboardActionFrameIndex + 1];
        short moduleIndex = 0;
        for (int i = 0; i < this.visualKeyboardFrames[frameindex].length; i += 4) {
            moduleIndex = this.visualKeyboardFrames[frameindex][i];
            this.canvas.drawModule(this.visualKeyboardImage[this.visualKeyboardModules[moduleIndex * 5]], this.visualKeyboardFrames[frameindex][i + 1], (int)this.visualKeyboardFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.visualKeyboardFrames[frameindex][i + 3]], x, y, this.visualKeyboardModules, (int)moduleIndex);
        }
        this.nextVisualKeyboardFrame(actionIndex);
    }

    public void nextVisualKeyboardFrame(byte actionIndex) {
        this.visualKeyboardFrameDelay = (byte)(this.visualKeyboardFrameDelay + 1);
        if (this.visualKeyboardFrameDelay == this.visualKeyboardActions[actionIndex][this.visualKeyboardActionFrameIndex]) {
            this.visualKeyboardFrameDelay = 0;
            this.visualKeyboardActionFrameIndex = (byte)(this.visualKeyboardActionFrameIndex + 2);
            if (this.visualKeyboardActionFrameIndex >= this.visualKeyboardActions[actionIndex].length) {
                this.visualKeyboardActionFrameIndex = (byte)(this.visualKeyboardActions[actionIndex].length - 2);
                this.isVisualKeyboardActionOver = true;
            }
        }
    }

    public void visualKeyProcess() {
        if (this.canvas.gamestate == 5) {
            int i;
            for (i = 0; i < visualKeyActionMap.length; ++i) {
                GameUI.visualKeyActionMap[i][4] = Control.IsKeyDown(visualKeyActionMap[i][0]) ? 1 : 0;
                GameUI.visualKeyActionMap[i][6] = Control.IsKeyHold(visualKeyActionMap[i][0], 10) ? 1 : 0;
            }
            for (i = 0; i < visualDirectionKeyActionMap.length; ++i) {
                GameUI.visualDirectionKeyActionMap[i][4] = Control.IsKeyDown(visualDirectionKeyActionMap[i][0]) ? 1 : 0;
                GameUI.visualDirectionKeyActionMap[i][6] = Control.IsKeyHold(visualDirectionKeyActionMap[i][0], 10) ? 1 : 0;
            }
        }
    }

    public void drawVisualKeyboardItem() {
        int i;
        this.canvas.gameUi.visualKeyProcess();
        this.drawplayerskillvk();
        this.drawVisualKeyboardStaticFrame(0);
        for (i = 0; i < visualKeyActionMap.length; ++i) {
            if ((i == 2 || i == 3) && this.canvas.rolem.roleState != 0 || i == 4 && this.canvas.rolem.weaponCriticalKey7[this.canvas.rolem.playerCurrentWeaponType][0] <= -1 || i == 5 && this.canvas.rolem.weaponCriticalKey9[this.canvas.rolem.playerCurrentWeaponType][0] <= -1 || i == 7 && this.canvas.rolem.currentRideIndex == -1) continue;
            if (visualKeyActionMap[i][6] == 1) {
                if (i != 0) {
                    this.drawVisualKeyboardStaticFrame(2, visualKeyActionMap[i][1] + 17, visualKeyActionMap[i][2] + 16);
                    this.drawVisualKeyboardStaticFrame(10, visualKeyActionMap[i][1] + 17, visualKeyActionMap[i][2] + 16);
                } else {
                    this.drawVisualKeyboardStaticFrame(5, visualKeyActionMap[i][1] + 38, visualKeyActionMap[i][2] + 38);
                }
            }
            if (visualKeyActionMap[i][4] != 1) continue;
            if (i != 0) {
                this.drawVisualKeyboardSprite((byte)0, visualKeyActionMap[i][1] + 17, visualKeyActionMap[i][2] + 16);
                this.drawVisualKeyboardSprite((byte)2, visualKeyActionMap[i][1] + 17, visualKeyActionMap[i][2] + 16);
                continue;
            }
            this.drawVisualKeyboardSprite((byte)1, visualKeyActionMap[i][1] + 38, visualKeyActionMap[i][2] + 38);
        }
        for (i = 0; i < visualDirectionKeyActionMap.length; ++i) {
            if (visualDirectionKeyActionMap[i][6] == 1) {
                this.drawVisualKeyboardStaticFrame(12, visualDirectionKeyActionMap[i][1], visualDirectionKeyActionMap[i][2], visualDirectionKeyActionMap[i][3]);
                continue;
            }
            if (visualDirectionKeyActionMap[i][4] != 1) continue;
            this.drawVisualKeyboardSprite((byte)3, visualDirectionKeyActionMap[i][1], visualDirectionKeyActionMap[i][2], visualDirectionKeyActionMap[i][3]);
        }
        this.drawVisualKeyboardModule(this.canvas.rolem.playerCurrentWeaponType == 0 ? 27 : 28, 536 + (this.canvas.rolem.playerCurrentWeaponType == 0 ? 8 : 6), 262 + (this.canvas.rolem.playerCurrentWeaponType == 0 ? 8 : 8), 20, 0);
    }

    public void drawplayerskillvk() {
        switch (this.canvas.rolem.roleState) {
            case 0: {
                if (this.canvas.rolem.weaponCriticalKey7[this.canvas.rolem.playerCurrentWeaponType][0] > -1) {
                    this.drawVisualKeyboardModule(this.canvas.rolem.weaponCriticalKey7[this.canvas.rolem.playerCurrentWeaponType][0] + 29, 535, 224, 3, 0);
                } else {
                    this.drawVisualKeyboardModule(24, 535, 224, 3, 0);
                }
                if (this.canvas.rolem.weaponCriticalKey9[this.canvas.rolem.playerCurrentWeaponType][0] > -1) {
                    this.drawVisualKeyboardModule(this.canvas.rolem.weaponCriticalKey9[this.canvas.rolem.playerCurrentWeaponType][0] + 29, 490, 269, 3, 0);
                } else {
                    this.drawVisualKeyboardModule(24, 490, 269, 3, 0);
                }
                if (this.canvas.rolem.currentRideIndex == -1) {
                    this.drawVisualKeyboardModule(24, 35, 190, 3, 0);
                } else {
                    this.drawVisualKeyboardModule(this.canvas.rolem.currentRideIndex + 49, 35, 190, 3, 0);
                }
                this.drawVisualKeyboardModule(54, 491, 331, 3, 0);
                break;
            }
            case 1: 
            case 2: 
            case 3: {
                this.drawVisualKeyboardModule((this.canvas.rolem.roleState == 2 ? 1 : 0) + 61, 535, 224, 3, 0);
                this.drawVisualKeyboardModule(this.canvas.rolem.roleState - 1 + 58, 490, 269, 3, 0);
                this.drawVisualKeyboardModule((this.canvas.rolem.fplayerbox[0].spritename == 0 ? 0 : 1) + 56, 35, 190, 3, 0);
                this.drawVisualKeyboardModule(24, 491, 331, 3, 0);
            }
        }
        this.drawVisualKeyboardModule(this.canvas.rolem.playerCurrentWeaponType == 0 ? 13 : 14, 582 + (this.canvas.rolem.playerCurrentWeaponType == 0 ? -4 : -4), 200 + (this.canvas.rolem.playerCurrentWeaponType == 0 ? -3 : -7), 20, 0);
        if (this.canvas.rolem.roleState != 0) {
            this.drawVisualKeyboardModule(24, 584, 201, 20, 0);
        }
        this.drawVisualKeyboardModule(52, 285, 327, 3, 0);
        this.drawVisualKeyboardModule(53, 355, 327, 3, 0);
    }

    public void drawVisualKeyboard() {
        if (this.canvas.mapm.isEventPlaying) {
            return;
        }
        if (this.canvas.mapm.isTeachPlayering) {
            switch (this.canvas.mapm.teachdata[this.canvas.mapm.teachindex][this.canvas.mapm.teachstep][0]) {
                case 17: 
                case 18: {
                    this.drawVisualKeyboardItem();
                    return;
                }
                case 6: {
                    if (this.canvas.mapm.teachindex == 6) {
                        this.drawVisualKeyboardItem();
                    }
                    return;
                }
            }
        }
        switch (this.fightUiState) {
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: {
                this.drawVisualKeyboardItem();
            }
        }
    }

    public void initFightUi() {
        Util.tempData = new byte[15000];
        Util.position[0] = 0;
        Util.getStream(Util.tempData, "/data/fightUi.data", 0);
        this.fightUiModules = Util.loadInfShort(Util.tempData, this.fightUiModules, Util.position);
        this.fightUiFrames = Util.loadInfShort(Util.tempData, this.fightUiFrames, Util.position);
        this.fightUiActions = Util.loadInfShort(Util.tempData, this.fightUiActions, Util.position);
        this.fightUiImage = this.createUiImage(this.fightUiImgData);
        Util.tempData = null;
    }

    public void createFightHeadImage() {
        for (int i = 0; i < this.fightEnemyHeadImage.length; ++i) {
            this.fightEnemyHeadImage[i] = Util.createImage("/fightHead/" + i + ".png");
        }
    }

    public void cleanFightHeadImage() {
        for (int i = 0; i < this.fightEnemyHeadImage.length; i = (int)((byte)(i + 1))) {
            this.fightEnemyHeadImage[i] = null;
        }
    }

    public void drawFightEnemyHead(Role role) {
        if (this.headImageIndex[role.spritename] != -1) {
            this.canvas.g.drawImage(this.fightEnemyHeadImage[this.headImageIndex[role.spritename]], 379, 57, 36);
        }
    }

    public void drawFightUiModule(int mindex, int x, int y, int anchor, int trans) {
        this.canvas.drawImage(this.fightUiImage[this.fightUiModules[mindex * 5]], x, y, anchor, trans, this.fightUiModules, mindex);
    }

    public void drawFightUiSprite(byte actionIndex, int x, int y) {
        short frameindex = this.fightUiActions[actionIndex][this.fightUiActionFrameIndex + 1];
        short moduleIndex = 0;
        for (int i = 0; i < this.fightUiFrames[frameindex].length; i += 4) {
            moduleIndex = this.fightUiFrames[frameindex][i];
            this.canvas.drawModule(this.fightUiImage[this.fightUiModules[moduleIndex * 5]], this.fightUiFrames[frameindex][i + 1], (int)this.fightUiFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.fightUiFrames[frameindex][i + 3]], x, y, this.fightUiModules, (int)moduleIndex);
        }
        this.nextFightUiFrame(actionIndex);
    }

    public void nextFightUiFrame(byte actionIndex) {
        this.fightUiFrameDelay = (byte)(this.fightUiFrameDelay + 1);
        if (this.fightUiFrameDelay == this.fightUiActions[actionIndex][this.fightUiActionFrameIndex]) {
            this.fightUiFrameDelay = 0;
            this.fightUiActionFrameIndex = (byte)(this.fightUiActionFrameIndex + 2);
            if (this.fightUiActionFrameIndex == this.fightUiActions[actionIndex].length) {
                this.fightUiActionFrameIndex = (byte)(this.fightUiActions[actionIndex].length - 2);
                this.isFightUiActionOver = true;
            }
        }
    }

    public void setfightuiaction(byte actionindex) {
        this.skilliconActionIndex = actionindex;
        this.skilliconActionFrameIndex = 0;
        this.skilliconFrameDelay = 0;
        this.isskilliconactionover = false;
        this.changeweaponactionstate = 1;
        this.isshowchangeicon = true;
        this.changeweaponshowtime = 0;
    }

    public void drawskilliconsprite(byte actionIndex, int x, int y) {
        short frameindex = this.canvas.skilliconactions[actionIndex][this.skilliconActionFrameIndex + 1];
        short moduleIndex = 0;
        for (int i = 0; i < this.canvas.skilliconframes[frameindex].length; i += 4) {
            moduleIndex = this.canvas.skilliconframes[frameindex][i];
            this.canvas.drawModule(this.canvas.skilliconimage[this.canvas.skilliconmodules[moduleIndex * 5]], this.canvas.skilliconframes[frameindex][i + 1], (int)this.canvas.skilliconframes[frameindex][i + 2], 20, TRANS_TO_DG[this.canvas.skilliconframes[frameindex][i + 3]], x, y, this.canvas.skilliconmodules, (int)moduleIndex);
        }
    }

    public void nextskilliconframe(byte actionIndex) {
        if (actionIndex >= this.canvas.skilliconactions.length - 1) {
            actionIndex = 0;
        }
        if ((this.skilliconFrameDelay = (byte)(this.skilliconFrameDelay + 1)) == this.canvas.skilliconactions[actionIndex][this.skilliconActionFrameIndex]) {
            this.skilliconFrameDelay = 0;
            this.skilliconActionFrameIndex = (byte)(this.skilliconActionFrameIndex + 2);
            if (this.skilliconActionFrameIndex == 6) {
                this.playerskill7state = (byte)(this.playerskill7state | 1 << this.canvas.rolem.playerCurrentWeaponType);
                this.playerskill9state = (byte)(this.playerskill9state | 1 << this.canvas.rolem.playerCurrentWeaponType);
            }
            if (this.skilliconActionFrameIndex == this.canvas.skilliconactions[actionIndex].length) {
                this.skilliconActionFrameIndex = (byte)(this.canvas.skilliconactions[actionIndex].length - 2);
                this.isskilliconactionover = true;
            }
        }
    }

    public boolean ishavelifedrug() {
        for (int i = 1; i < 4; ++i) {
            if (this.itemTotal[i] <= 0) continue;
            return true;
        }
        return false;
    }

    public boolean ishavemagicdrug() {
        for (int i = 4; i < 7; ++i) {
            if (this.itemTotal[i] <= 0) continue;
            return true;
        }
        return false;
    }

    public void drawplayerstateicon() {
        if (this.ishavelifedrug()) {
            this.drawfighticon(53, 47, 21);
            if (this.canvas.rolem.ai.lifecounter > 0) {
                int n = this.canvas.rolem.ai.lifecounter * 100;
                this.canvas.rolem.ai.getClass();
                this.lifecliph = 11 * (n / 10) / 100;
                this.canvas.g.setClip(54, 47 + (11 - this.lifecliph), 11, this.lifecliph);
                this.drawFightUiModule(17, 54, 47, 20, 0);
                this.canvas.setClip();
            }
        } else {
            this.drawfighticon(53, 47, 22);
        }
        if (this.ishavemagicdrug()) {
            this.drawfighticon(69, 47, 23);
            if (this.canvas.rolem.ai.magiccounter > 0) {
                int n = this.canvas.rolem.ai.magiccounter * 100;
                this.canvas.rolem.ai.getClass();
                this.magiccliph = 11 * (n / 10) / 100;
                this.canvas.g.setClip(70, 47 + (11 - this.magiccliph), 11, this.magiccliph);
                this.drawFightUiModule(17, 70, 47, 20, 0);
                this.canvas.setClip();
            }
        } else {
            this.drawfighticon(69, 47, 24);
        }
        switch (this.canvas.rolem.roleState) {
            case 0: {
                if (this.canvas.rolem.weaponCriticalKey7[this.canvas.rolem.playerCurrentWeaponType][0] > -1) {
                    this.drawfightskillicon(101, 47, this.canvas.rolem.weaponCriticalKey7[this.canvas.rolem.playerCurrentWeaponType][0]);
                    if (this.canvas.rolem.weaponCriticalKey7[this.canvas.rolem.playerCurrentWeaponType][1] > 0) {
                        this.skill7cliph = 11 * (this.canvas.rolem.weaponCriticalKey7[this.canvas.rolem.playerCurrentWeaponType][1] * 100 / this.canvas.rolem.skillBaseData[this.canvas.rolem.weaponCriticalKey7[this.canvas.rolem.playerCurrentWeaponType][0]][10]) / 100;
                        if (this.skill7cliph > 0) {
                            this.canvas.g.setClip(102, 47 + (11 - this.skill7cliph), 11, this.skill7cliph);
                            this.drawFightUiModule(17, 102, 47, 20, 0);
                            this.canvas.setClip();
                        }
                    } else if (this.canvas.rolem.weaponCriticalKey7[this.canvas.rolem.playerCurrentWeaponType][1] == 0 && (this.playerskill7state & 1 << this.canvas.rolem.playerCurrentWeaponType) != 0) {
                        this.drawFightUiModule(21, 102, 47, 20, 0);
                        if (this.canvas.rolem.weaponCriticalKey7[this.canvas.rolem.playerCurrentWeaponType][1] == 0 && (this.canvas.gameUi.playerskill7state & 1 << this.canvas.rolem.playerCurrentWeaponType) != 0) {
                            this.canvas.gameUi.playerskill7state = (byte)(this.canvas.gameUi.playerskill7state ^ 1 << this.canvas.rolem.playerCurrentWeaponType);
                        }
                    }
                    this.drawFightUiModule(18, 114, 63, 40, 0);
                }
                if (this.canvas.rolem.weaponCriticalKey9[this.canvas.rolem.playerCurrentWeaponType][0] > -1) {
                    this.drawfightskillicon(117, 47, this.canvas.rolem.weaponCriticalKey9[this.canvas.rolem.playerCurrentWeaponType][0]);
                    if (this.canvas.rolem.weaponCriticalKey9[this.canvas.rolem.playerCurrentWeaponType][1] > 0) {
                        this.skill9cliph = 11 * (this.canvas.rolem.weaponCriticalKey9[this.canvas.rolem.playerCurrentWeaponType][1] * 100 / this.canvas.rolem.skillBaseData[this.canvas.rolem.weaponCriticalKey9[this.canvas.rolem.playerCurrentWeaponType][0]][10]) / 100;
                        if (this.skill9cliph > 0) {
                            this.canvas.g.setClip(118, 47 + (11 - this.skill9cliph), 11, this.skill9cliph);
                            this.drawFightUiModule(17, 118, 47, 20, 0);
                            this.canvas.setClip();
                        } else if (this.canvas.rolem.weaponCriticalKey9[this.canvas.rolem.playerCurrentWeaponType][1] == 0) {
                            this.drawFightUiModule(21, 118, 47, 20, 0);
                        }
                    } else if (this.canvas.rolem.weaponCriticalKey9[this.canvas.rolem.playerCurrentWeaponType][1] == 0 && (this.playerskill9state & 1 << this.canvas.rolem.playerCurrentWeaponType) != 0) {
                        this.drawFightUiModule(21, 118, 47, 20, 0);
                        if (this.canvas.rolem.weaponCriticalKey9[this.canvas.rolem.playerCurrentWeaponType][1] == 0 && (this.canvas.gameUi.playerskill9state & 1 << this.canvas.rolem.playerCurrentWeaponType) != 0) {
                            this.canvas.gameUi.playerskill9state = (byte)(this.canvas.gameUi.playerskill9state ^ 1 << this.canvas.rolem.playerCurrentWeaponType);
                        }
                    }
                    this.drawFightUiModule(19, 130, 63, 40, 0);
                }
                if (this.canvas.rolem.currentRideIndex > -1) {
                    this.drawfighticon(85, 47, this.canvas.rolem.currentRideIndex + 43);
                }
                this.drawFightUiModule(20, 98, 63, 40, 0);
                break;
            }
            case 1: 
            case 2: 
            case 3: {
                this.drawfighticon(85, 47, 42);
                switch (this.canvas.rolem.currentRideIndex) {
                    case 0: {
                        this.drawfighticon(101, 47, 46);
                        this.drawfighticon(117, 47, 60);
                        break;
                    }
                    case 2: {
                        this.drawfighticon(101, 47, 46);
                        this.drawfighticon(117, 47, 58);
                        break;
                    }
                    case 1: {
                        this.drawfighticon(101, 47, 47);
                        this.drawfighticon(117, 47, 59);
                    }
                }
                this.drawFightUiModule(20, 98, 63, 40, 0);
                this.drawFightUiModule(19, 130, 63, 40, 0);
                this.drawFightUiModule(18, 114, 63, 40, 0);
            }
        }
    }

    public void drawDefaultModule(int mindex, int x, int y, int anchor, int trans) {
        if (mindex > -1) {
            this.canvas.drawImage(this.canvas.defaultimages[this.canvas.defaultmodules[mindex * 5]], x, y, anchor, trans, this.canvas.defaultmodules, mindex);
        }
    }

    public void drawName(Role role, boolean isBossFlag) {
        String tempname = this.canvas.rolem.rolename[role.spritename];
        if (this.canvas.mapm.mapIndex == 19 && (role.spritename == 5 || role.spritename == 6)) {
            tempname = "\u8fdc\u53e4\u9690\u4fee\u8005";
        }
        this.canvas.drawColorString(tempname, (isBossFlag ? -20 : 0) + 110 + 173, 39, 36, 0xFFFFFF, 0);
        this.canvas.drawColorString("Lv" + role.level, (isBossFlag ? 0 : 0) + 200 + 173 + 75, 55, 40, 0xFFFFFF, 0);
        this.drawDefaultModule(this.defaultindex[role.defenceType[0]], 365, 55, 33, 0);
    }

    public void drawplayerdebuffstate(Role role) {
        int i;
        byte[] data = new byte[]{0, 1, 5, 6};
        byte[] index = new byte[]{-1, -1, -1, -1};
        int skip = 0;
        for (i = 0; i < 4; ++i) {
            if ((role.rolestate & 1 << data[i]) == 0) continue;
            index[skip++] = (byte)i;
        }
        for (i = 0; i < 4; ++i) {
            if (index[i] <= -1) continue;
            this.canvas.drawmodule(this.fightUiImage, this.fightUiModules, 25 + index[i], 150 + i * 20, 5, 20, 0);
        }
    }

    private void drawplayerback(Role role) {
        if (role.spritename == 0) {
            this.drawFightUiModule(30, 0, 0, 20, 0);
        } else {
            this.drawFightUiModule(29, 0, 0, 20, 0);
        }
        switch (this.canvas.rolem.roleState) {
            case 0: {
                this.canvas.drawFrame(this.fightUiImage, this.fightUiModules, this.fightUiFrames, 0, 320, 180, 20);
                break;
            }
            case 1: 
            case 2: 
            case 3: {
                if (this.canvas.rolem.rideLife[this.canvas.rolem.roleState - 1] < this.canvas.rolem.rideData[this.canvas.rolem.roleState - 1][2] * 3 / 10) {
                    if (this.canvas.publiccounter % 2 == 0) {
                        this.canvas.drawFrame(this.fightUiImage, this.fightUiModules, this.fightUiFrames, 11, 320, 180, 20);
                        break;
                    }
                    this.canvas.drawFrame(this.fightUiImage, this.fightUiModules, this.fightUiFrames, 12, 320, 180, 20);
                    break;
                }
                this.canvas.drawFrame(this.fightUiImage, this.fightUiModules, this.fightUiFrames, 11, 320, 180, 20);
            }
        }
    }

    public void drawplayerstate(Role role) {
        this.drawplayerback(role);
        this.drawPlayerInfo(role);
        this.drawplayerstateicon();
        this.canvas.drawFrame(this.fightUiImage, this.fightUiModules, this.fightUiFrames, 1, 320, 180, 20);
        this.canvas.drawFrame(this.fightUiImage, this.fightUiModules, this.fightUiFrames, 2, 320, 180, 20);
        this.canvas.drawFrame(this.fightUiImage, this.fightUiModules, this.fightUiFrames, 3, 320, 180, 20);
        switch (this.changeweaponactionstate) {
            case 0: {
                switch (this.canvas.rolem.playerCurrentWeaponType) {
                    case 0: {
                        this.canvas.drawFrame(this.canvas.skilliconimage, this.canvas.skilliconmodules, this.canvas.skilliconframes, 0, 120, 160, 20);
                        break;
                    }
                    case 1: {
                        this.canvas.drawFrame(this.canvas.skilliconimage, this.canvas.skilliconmodules, this.canvas.skilliconframes, 2, 120, 160, 20);
                    }
                }
                break;
            }
            case 1: {
                this.drawskilliconsprite(this.skilliconActionIndex, 120, 160);
                if (!this.isskilliconactionover) break;
                this.changeweaponactionstate = 0;
            }
        }
        this.drawplayerdebuffstate(role);
    }

    public void drawchangeweaponicon(Role role) {
        if (this.isshowchangeicon) {
            switch (this.changeweaponactionstate) {
                case 0: {
                    switch (this.canvas.rolem.playerCurrentWeaponType) {
                        case 0: {
                            this.canvas.drawFrame(this.canvas.skilliconimage, this.canvas.skilliconmodules, this.canvas.skilliconframes, 0, role.spritex + 80, role.spritez - role.spritey + 50, 20);
                            break;
                        }
                        case 1: {
                            this.canvas.drawFrame(this.canvas.skilliconimage, this.canvas.skilliconmodules, this.canvas.skilliconframes, 2, role.spritex + 80, role.spritez - role.spritey + 50, 20);
                        }
                    }
                    if (++this.changeweaponshowtime <= 10) break;
                    this.isshowchangeicon = false;
                    break;
                }
                case 1: {
                    this.drawskilliconsprite(this.skilliconActionIndex, role.spritex + 80, role.spritez - role.spritey + 50);
                }
            }
        }
    }

    public void drawteachjianhujia(int type, int y) {
        this.drawStaticUi((short)73, 256, 298 - y + 20);
        this.canvas.drawFrame(this.fightUiImage, this.fightUiModules, this.fightUiFrames, 4, 200, 180 - y - 20, 20);
        this.canvas.drawFrame(this.fightUiImage, this.fightUiModules, this.fightUiFrames, 6, 200, 180 - y - 20, 20);
        switch (type) {
            case 0: {
                this.drawDefaultModule(this.defaultindex[3], 345, 315 - y + 20, 33, 0);
                break;
            }
            case 1: {
                this.drawDefaultModule(this.defaultindex[2], 345, 315 - y + 20, 33, 0);
                break;
            }
            case 2: {
                this.drawDefaultModule(this.defaultindex[1], 345, 315 - y + 20, 33, 0);
            }
        }
        this.canvas.drawRect(0xFF0000, 335, 315 - y - 16 + 20, 20, 20);
        this.canvas.drawLine(0xFF0000, 345, 315 - y - 45 + 20 - 18, 345, 315 - y - 16 + 20);
    }

    private void commandmove() {
    }

    public void drawFightUi() {
        if (this.canvas.mapm.isEventPlaying || this.canvas.mapm.isTeachPlayering) {
            return;
        }
        this.drawplayerstate(this.canvas.rolem.fplayerbox[0]);
        switch (this.fightUiState) {
            case 0: {
                break;
            }
            case 1: {
                if (this.beHurtIndex <= -1) break;
                this.drawStaticUi((short)73, 277, 38);
                this.canvas.drawFrame(this.fightUiImage, this.fightUiModules, this.fightUiFrames, 4, 220, -120, 20);
                this.drawEnemyLife(this.canvas.rolem.enemybox[this.beHurtIndex]);
                this.canvas.drawFrame(this.fightUiImage, this.fightUiModules, this.fightUiFrames, 6, 220, -120, 20);
                this.drawFightEnemyHead(this.canvas.rolem.enemybox[this.beHurtIndex]);
                this.drawName(this.canvas.rolem.enemybox[this.beHurtIndex], false);
                break;
            }
            case 2: {
                if (this.beHurtIndex <= -1) break;
                this.drawStaticUi((short)73, 257, 38);
                this.canvas.drawFrame(this.fightUiImage, this.fightUiModules, this.fightUiFrames, 5, 220, -120, 20);
                this.drawBossLife(this.canvas.rolem.bossbox[this.beHurtIndex]);
                this.canvas.drawFrame(this.fightUiImage, this.fightUiModules, this.fightUiFrames, 7, 220, -120, 20);
                this.drawFightEnemyHead(this.canvas.rolem.bossbox[this.beHurtIndex]);
                this.drawName(this.canvas.rolem.bossbox[this.beHurtIndex], true);
                break;
            }
            case 3: {
                if (this.canvas.rolem.yingxiangstate == 0) {
                    this.canvas.drawlines(343 + this.commandY);
                    this.canvas.drawFrame(this.fightUiImage, this.fightUiModules, this.fightUiFrames, 9, 320, 360 + this.commandY, 20);
                    break;
                }
                this.canvas.drawFrame(this.fightUiImage, this.fightUiModules, this.fightUiFrames, 8, 320, 360 + this.commandY, 20);
                break;
            }
            case 4: {
                if (this.canvas.rolem.yingxiangstate == 0) {
                    this.canvas.drawlines(343 + this.commandY);
                    this.canvas.drawFrame(this.fightUiImage, this.fightUiModules, this.fightUiFrames, 9, 320, 360 + this.commandY, 20);
                    break;
                }
                this.canvas.drawFrame(this.fightUiImage, this.fightUiModules, this.fightUiFrames, 8, 320, 360 + this.commandY, 20);
            }
        }
    }

    public void drawCityUi() {
        switch (this.fightUiState) {
            case 0: {
                break;
            }
            case 3: {
                if (this.canvas.rolem.yingxiangstate == 0) {
                    this.canvas.drawlines(343 + this.commandY);
                    this.canvas.drawFrame(this.fightUiImage, this.fightUiModules, this.fightUiFrames, 10, 320, 360 + this.commandY, 20);
                    break;
                }
                this.canvas.drawFrame(this.fightUiImage, this.fightUiModules, this.fightUiFrames, 8, 320, 360 + this.commandY, 20);
                break;
            }
        }
    }

    private void drawPlayerInfo(Role role) {
        this.drawPlayerLife(role);
        this.drawPlayerMagic(role);
        this.drawPlayerExp();
        this.drawridelife(this.canvas.rolem.roleState);
        this.canvas.drawColorString("" + this.canvas.rolem.playerLevel, 134, 24, 33, 15597319, 0);
        this.canvas.drawColorString(this.canvas.rolem.rolename[role.spritename], 86, 20, 33, 15597319, 0);
    }

    private void drawridelife(byte index) {
        if (index > 0) {
            short w = this.canvas.rolem.rideLife[index - 1];
            short bw = this.canvas.rolem.rideData[index - 1][2];
            this.canvas.g.setClip(3, 37 - 29 * w / bw, 5, 29 * w / bw);
            this.drawFightUiModule(22, 3, 8, 20, 0);
            this.canvas.setClip();
        }
    }

    private void drawPlayerLife(Role role) {
        int w = role.life[0];
        int bw = role.baseLife[0];
        role.lifeBackgroundX = role.lifeBackgroundX > 95 * w / bw ? --role.lifeBackgroundX : 95 * w / bw;
        if (role.life[0] > 0) {
            this.canvas.fillRect(242169, 48, 25, role.lifeBackgroundX, 6);
        }
        this.canvas.fillRect(65386, 48, 25, 95 * w / bw, 1);
        this.canvas.fillRect(8454069, 48, 26, 95 * w / bw, 1);
        this.canvas.fillRect(65386, 48, 27, 95 * w / bw, 2);
        this.canvas.fillRect(52821, 48, 29, 95 * w / bw, 1);
        this.canvas.fillRect(45642, 48, 30, 95 * w / bw, 1);
    }

    private void drawPlayerMagic(Role role) {
        int w = role.magic[0];
        int bw = role.baseMagic[0];
        int index = role.baseData[9] - 1 - role.phase;
        role.magicBackgroudX = role.magicBackgroudX > 85 * w / bw ? --role.magicBackgroudX : 85 * w / bw;
        if (role.magic[0] > 0) {
            this.canvas.fillRect(217246, 48, 34, role.magicBackgroudX, 6);
        }
        this.canvas.fillRect(41983, 48, 34, 85 * w / bw, 1);
        this.canvas.fillRect(12905215, 48, 35, 85 * w / bw, 1);
        this.canvas.fillRect(41983, 48, 36, 85 * w / bw, 2);
        this.canvas.fillRect(33742, 48, 38, 85 * w / bw, 1);
        this.canvas.fillRect(29106, 48, 39, 85 * w / bw, 1);
    }

    private void drawPlayerExp() {
        int w = this.canvas.rolem.playerCurrentExp;
        int bw = this.canvas.rolem.playerNeedExp;
        if (bw > 0) {
            this.canvas.fillRect(16318265, 48, 41, 81 * w / bw, 2);
        }
    }

    public void drawEnemyLife(Role role) {
        int w = role.life[0];
        int bw = role.baseLife[0];
        int index = role.baseData[9] - 1 - role.phase;
        role.lifeBackgroundX = role.lifeBackgroundX > 70 * w / bw ? --role.lifeBackgroundX : 70 * w / bw;
        if (role.life[0] > 0) {
            this.canvas.fillRect(0xFF0000, 184 - role.lifeBackgroundX + 173, 48, role.lifeBackgroundX, 6);
        }
        this.canvas.fillRect(65386, 357 - 70 * w / bw, 48, 70 * w / bw, 1);
        this.canvas.fillRect(8454069, 357 - 70 * w / bw, 49, 70 * w / bw, 1);
        this.canvas.fillRect(65386, 357 - 70 * w / bw, 50, 70 * w / bw, 2);
        this.canvas.fillRect(52821, 357 - 70 * w / bw, 52, 70 * w / bw, 1);
        this.canvas.fillRect(45642, 357 - 70 * w / bw, 53, 70 * w / bw, 1);
    }

    public void drawBossLife(Role role) {
        int w = role.life[0];
        int bw = role.baseLife[0];
        role.lifeBackgroundX = role.lifeBackgroundX > 106 * w / bw ? --role.lifeBackgroundX : 106 * w / bw;
        if (role.life[0] > 0) {
            this.canvas.fillRect(242169, 184 - role.lifeBackgroundX + 173, 48, role.lifeBackgroundX, 6);
        }
        this.canvas.fillRect(65386, 357 - 106 * w / bw, 48, 106 * w / bw, 1);
        this.canvas.fillRect(8454069, 357 - 106 * w / bw, 49, 106 * w / bw, 1);
        this.canvas.fillRect(65386, 357 - 106 * w / bw, 50, 106 * w / bw, 2);
        this.canvas.fillRect(52821, 357 - 106 * w / bw, 52, 106 * w / bw, 1);
        this.canvas.fillRect(45642, 357 - 106 * w / bw, 53, 106 * w / bw, 1);
    }

    public void drawFightEnemyInfo(Role role) {
        this.drawFightEnemyHead(role);
    }

    public void fightUiProcess() {
        switch (this.fightUiState) {
            case 0: {
                if (this.fightUiStateTime <= 0) break;
                switch (this.beHurtType) {
                    case 6: {
                        this.changeFightUiState((byte)1);
                        break;
                    }
                    case 7: {
                        this.changeFightUiState((byte)2);
                        break;
                    }
                    case 5: {
                        this.changeFightUiState((byte)1);
                    }
                }
                break;
            }
            case 1: {
                if (this.fightUiStateTime <= 0 || --this.fightUiStateTime > 0) break;
                this.changeFightUiState((byte)0);
                break;
            }
            case 2: {
                if (this.fightUiStateTime <= 0 || --this.fightUiStateTime > 0) break;
                this.changeFightUiState((byte)0);
                break;
            }
            case 3: {
                if (this.commandY > 0) {
                    this.commandY -= 4;
                    break;
                }
                if (this.commandwaittime <= 0 || --this.commandwaittime != 0) break;
                this.showStaticCommandFlag = true;
                break;
            }
            case 4: {
                if (this.commandY < 32) {
                    this.commandY += 4;
                    break;
                }
                this.changeFightUiState((byte)0);
            }
        }
    }

    public void cityUiProcess() {
        switch (this.fightUiState) {
            case 0: {
                break;
            }
            case 3: {
                if (this.commandY <= 0) break;
                this.commandY -= 4;
                break;
            }
        }
    }

    public void changeFightUiState(byte state) {
        this.fightUiState = state;
        this.fightUiActionFrameIndex = 0;
        this.fightUiFrameDelay = 0;
        this.isFightUiActionOver = false;
        if (state == 3) {
            this.commandY = 32;
            this.commandwaittime = 40;
            this.showStaticCommandFlag = false;
        }
    }

    public boolean iscanuse(byte name) {
        switch (name) {
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 11: 
            case 12: 
            case 13: {
                return true;
            }
        }
        return false;
    }

    public void newGame() {
        this.canvas.gameUi.initRolePack();
        this.canvas.rolem.playerLevel = (byte)50;
        this.canvas.rolem.playerCurrentExp = 0;
        this.canvas.rolem.playerNeedExp = 1091;
        this.canvas.rolem.playerCurrentSkillPoint = 0;
        this.canvas.rolem.weaponCriticalKey7[0][0] = 1;
        this.canvas.rolem.weaponCriticalKey7[1][0] = 8;
        this.canvas.rolem.weaponCriticalKey9[0][0] = 4;
        this.canvas.rolem.weaponCriticalKey9[1][0] = 11;
        this.canvas.rolem.skillState[1][1] = 5;
        this.canvas.rolem.skillState[8][1] = 5;
        this.canvas.rolem.skillState[4][1] = 3;
        this.canvas.rolem.skillState[11][1] = 3;
        this.canvas.rolem.rideLife[0] = this.canvas.rolem.rideData[0][2];
        this.canvas.rolem.initPlayerInfo();
        this.canvas.rolem.fplayerbox[0].baseAttack[0] = this.canvas.rolem.fplayerbox[0].baseAttack[0] + this.itemUsedData[11] * 15;
        this.canvas.rolem.fplayerbox[0].attack[0] = this.canvas.rolem.fplayerbox[0].baseAttack[0];
        this.addWeapon((byte)56, (byte)-1);
        this.addWeapon((byte)72, (byte)-1);
        this.addWeapon((byte)75, (byte)-1);
        this.addSuit((byte)78, (byte)-1);
        this.putOnWeapon((byte)0);
        this.putOnWeapon((byte)1);
        this.putOnWeapon((byte)2);
        this.putOnSuit((byte)0);
        this.canvas.mapm.setuniversemousepos();
    }

    public void initnewgamedata() {
        int i;
        this.canvas.gameUi.initRolePack();
        this.canvas.rolem.playerLevel = 1;
        this.canvas.rolem.playerCurrentExp = 0;
        this.canvas.rolem.playerCurrentSkillPoint = 1;
        this.canvas.rolem.playerNeedExp = 61;
        this.canvas.rolem.yingxiangstate = 0;
        this.playerMoney = 800;
        this.canvas.mapm.areaopenstate = 16777252;
        for (i = 0; i < this.canvas.rolem.weaponCriticalKey7.length; ++i) {
            this.canvas.rolem.weaponCriticalKey7[i][0] = -1;
        }
        for (i = 0; i < this.canvas.rolem.weaponCriticalKey9.length; ++i) {
            this.canvas.rolem.weaponCriticalKey9[i][0] = -1;
        }
        this.canvas.rolem.playerCurrentWeapon = new byte[3];
        this.canvas.rolem.playerCurrentWeaponAttack = new short[3];
        this.canvas.rolem.playerCurrentWeaponBaojiOdds = new short[3];
        this.canvas.rolem.playerCurrentWeaponBaojiAttack = new short[3];
        this.canvas.rolem.skillState = new byte[21][2];
        this.canvas.rolem.changeCurrentWeapon(this.canvas.rolem.fplayerbox[0], 0);
    }

    public void initadditem() {
        this.addItem((byte)0, (byte)5);
        this.addItem((byte)1, (byte)20);
        this.addItem((byte)4, (byte)20);
        this.addWeapon((byte)44, (byte)-1);
        this.addWeapon((byte)59, (byte)-1);
        this.addWeapon((byte)74, (byte)-1);
        this.addSuit((byte)78, (byte)-1);
        this.addPlus((byte)88, (byte)-1, (byte)3);
        this.putOnWeapon((byte)0);
        this.putOnWeapon((byte)1);
        this.putOnWeapon((byte)2);
        this.putOnSuit((byte)0);
        this.putOnPlus((byte)0, (byte)0);
    }

    public void initRolePack() {
        int i;
        this.scroll = new Scroll(this.canvas);
        this.itemPackUseNum = 0;
        this.weaponPackUseNum = 0;
        this.suitPackUseNum = 0;
        this.plusPackUseNum = 0;
        this.itemPack = new byte[2][this.itemPackNum];
        for (i = 0; i < this.itemPack[0].length; ++i) {
            this.itemPack[0][i] = -1;
            this.itemPack[1][i] = -1;
        }
        this.itemTotal = new short[128];
        this.informationPack = new byte[2][40];
        for (i = 0; i < this.informationPack[0].length; ++i) {
            this.informationPack[0][i] = -1;
            this.informationPack[1][i] = -1;
        }
        this.weaponPack = new byte[2][this.itemPackNum];
        for (i = 0; i < this.weaponPack[0].length; ++i) {
            this.weaponPack[0][i] = -1;
            this.weaponPack[1][i] = -1;
        }
        this.suitPack = new byte[2][this.itemPackNum];
        for (i = 0; i < this.suitPack[0].length; ++i) {
            this.suitPack[0][i] = -1;
            this.suitPack[1][i] = -1;
        }
        this.plusPack = new byte[3][this.itemPackNum];
        for (i = 0; i < this.plusPack[0].length; ++i) {
            this.plusPack[0][i] = -1;
            this.plusPack[1][i] = -1;
        }
    }

    public void initarmpack() {
        int i;
        this.itemPack = new byte[2][this.itemPackNum];
        for (i = 0; i < this.itemPack[0].length; ++i) {
            this.itemPack[0][i] = -1;
            this.itemPack[1][i] = -1;
        }
        this.weaponPack = new byte[2][this.itemPackNum];
        for (i = 0; i < this.weaponPack[0].length; ++i) {
            this.weaponPack[0][i] = -1;
            this.weaponPack[1][i] = -1;
        }
        this.suitPack = new byte[2][this.itemPackNum];
        for (i = 0; i < this.suitPack[0].length; ++i) {
            this.suitPack[0][i] = -1;
            this.suitPack[1][i] = -1;
        }
        this.plusPack = new byte[3][this.itemPackNum];
        for (i = 0; i < this.plusPack[0].length; ++i) {
            this.plusPack[0][i] = -1;
            this.plusPack[1][i] = -1;
        }
    }

    public String useItem(Role user, byte name) {
        String tempStr = "";
        this.useitemstate = (byte)-1;
        byte itempos = this.canvas.gameUi.getItemPos(this.canvas.gameUi.itemPack, name);
        if (itempos > -1) {
            tempStr = this.useItem(user, name, itempos);
            if (this.useitemstate == 0) {
                switch (name) {
                    case 1: {
                        user.setHeadString("\u519b\u5907\u836f\u5242", 15);
                        user.setHeadString("\u751f\u547d+" + this.itemUsedData[name], 15);
                        break;
                    }
                    case 2: {
                        user.setHeadString("\u6839\u830e\u836f\u5242", 15);
                        user.setHeadString("\u751f\u547d+" + this.itemUsedData[name], 15);
                        break;
                    }
                    case 3: {
                        user.setHeadString("\u5723\u6218\u836f\u5242", 15);
                        user.setHeadString("\u751f\u547d+" + this.itemUsedData[name], 15);
                        break;
                    }
                    case 4: {
                        user.setHeadString("\u5145\u80fd\u836f\u5242", 15);
                        user.setHeadString("\u80fd\u91cf+" + this.itemUsedData[name], 15);
                        break;
                    }
                    case 5: {
                        user.setHeadString("\u7075\u80fd\u836f\u5242", 15);
                        user.setHeadString("\u80fd\u91cf+" + this.itemUsedData[name], 15);
                        break;
                    }
                    case 6: {
                        user.setHeadString("\u5723\u7075\u836f\u5242", 15);
                        user.setHeadString("\u80fd\u91cf+" + this.itemUsedData[name], 15);
                    }
                }
            }
        }
        return tempStr;
    }

    public String useItem(Role role, byte name, int index) {
        this.useitemstate = 0;
        String tempstr = "";
        if (role.life[0] < 1) {
            tempstr = "\u89d2\u8272\u5df2\u6b7b\u4ea1\uff0c\u65e0\u6cd5\u4f7f\u7528\u9053\u5177\u3002";
            this.useitemstate = 1;
        } else {
            switch (name) {
                case 0: {
                    if (this.canvas.lastgamestate == 5) {
                        if (this.ishuicheng) {
                            this.finishCommand();
                            this.canvas.mapm.setuniversepos();
                            this.canvas.startSwitchScrren(0, (byte)10, 0);
                        } else {
                            tempstr = "\u5f53\u524d\u65e0\u6cd5\u4f7f\u7528\u6298\u8dc3\u6676\u4f53\u3002";
                            this.useitemstate = 1;
                        }
                    } else {
                        tempstr = "\u5f53\u524d\u65e0\u6cd5\u4f7f\u7528\u6298\u8dc3\u6676\u4f53\u3002";
                        this.useitemstate = 1;
                    }
                    this.canvas.createloadimage((byte)this.canvas.mapm.universeMapAreaData[this.canvas.mapm.maparea[this.canvas.mapm.nextMapIndex]][3]);
                    break;
                }
                case 1: 
                case 2: 
                case 3: {
                    if (this.canvas.rolem.isLifefull()) {
                        tempstr = "\u751f\u547d\u5df2\u6ee1\uff0c\u65e0\u6cd5\u4f7f\u7528\u9053\u5177\u3002";
                        this.useitemstate = 1;
                        break;
                    }
                    role.addLife(this.itemUsedData[name]);
                    tempstr = "\u4f7f\u7528\u6210\u529f\u3002\u751f\u547d+" + this.itemUsedData[name] + "\u3002";
                    break;
                }
                case 4: 
                case 5: 
                case 6: {
                    if (this.canvas.rolem.isMagicfull()) {
                        tempstr = "\u80fd\u91cf\u5df2\u6ee1\uff0c\u65e0\u6cd5\u4f7f\u7528\u9053\u5177\u3002";
                        this.useitemstate = 1;
                        break;
                    }
                    role.addMagic(this.itemUsedData[name]);
                    tempstr = "\u4f7f\u7528\u6210\u529f\uff0c\u80fd\u91cf+" + this.itemUsedData[name] + "\u3002";
                    break;
                }
                case 7: {
                    if (this.canvas.rolem.isLifefull() && this.canvas.rolem.isMagicfull()) {
                        tempstr = "\u751f\u547d\u548c\u80fd\u91cf\u5df2\u6ee1\uff0c\u65e0\u6cd5\u4f7f\u7528\u9053\u5177\u3002";
                        this.useitemstate = 1;
                        break;
                    }
                    role.addLife(this.itemUsedData[name]);
                    role.addMagic(this.itemUsedData[name]);
                    tempstr = "\u4f7f\u7528\u6210\u529f\uff0c\u751f\u547d+" + this.itemUsedData[name] + ",\u5185\u529b+" + this.itemUsedData[name] + "\u3002";
                    break;
                }
                case 8: {
                    if ((role.rolestate & 0x20) != 0 || (role.rolestate & 0x40) != 0) {
                        role.rolestate = (short)(role.rolestate ^ 0x20);
                        role.rolestate = (short)(role.rolestate ^ 0x40);
                        tempstr = "\u4f7f\u7528\u6210\u529f\uff0c\u4eba\u7269\u72b6\u6001\u5df2\u6062\u590d\u6b63\u5e38";
                        break;
                    }
                    tempstr = "\u72b6\u6001\u6b63\u5e38\uff0c\u65e0\u6cd5\u4f7f\u7528\u9053\u5177\u3002";
                    this.useitemstate = 1;
                    break;
                }
                case 9: {
                    if (this.canvas.rolem.rideLife[this.useplayerindex] == this.canvas.rolem.rideData[this.useplayerindex][2]) {
                        tempstr = "\u5750\u9a91\u8010\u4e45\u5ea6\u5df2\u6ee1\uff0c\u65e0\u6cd5\u4f7f\u7528\u9053\u5177";
                        this.useitemstate = 1;
                        break;
                    }
                    this.canvas.rolem.rideLife[this.useplayerindex] = this.canvas.rolem.rideData[this.useplayerindex][2];
                    tempstr = "\u4f7f\u7528\u6210\u529f\uff0c\u5750\u9a91\u8010\u4e45\u5ea6\u5df2\u6ee1\u3002";
                    break;
                }
                case 10: {
                    if (this.itemPackNum >= 56) {
                        tempstr = "\u80cc\u5305\u5df2\u589e\u81f3\u6700\u5927\u3002";
                        this.useitemstate = 1;
                        break;
                    }
                    this.itemPackNum = (byte)(this.itemPackNum + this.itemUsedData[name]);
                    this.upgradeItemPack(this.itemPack, this.itemPackNum);
                    this.upgradeWeaponPack(this.weaponPack, this.itemPackNum);
                    this.upgradeSuitPack(this.suitPack, this.itemPackNum);
                    this.upgradePlusPack(this.plusPack, this.itemPackNum);
                    tempstr = "\u4f7f\u7528\u6210\u529f\uff0c\u80cc\u5305\u6570\u91cf\u5df2\u589e\u81f3" + this.itemPackNum;
                    break;
                }
                case 11: {
                    role.addBaseAttack(this.itemUsedData[name]);
                    this.addforeverdata[0] = (short)(this.addforeverdata[0] + this.itemUsedData[name]);
                    tempstr = "\u653b\u51fb\u6c38\u4e45+" + this.itemUsedData[name];
                    break;
                }
                case 12: {
                    role.addBaseLife(this.itemUsedData[name]);
                    this.addforeverdata[1] = (short)(this.addforeverdata[1] + this.itemUsedData[name]);
                    tempstr = "\u751f\u547d\u6c38\u4e45+" + this.itemUsedData[name];
                    break;
                }
                case 13: {
                    role.addBaseMagic(this.itemUsedData[name]);
                    this.addforeverdata[2] = (short)(this.addforeverdata[2] + this.itemUsedData[name]);
                    tempstr = "\u80fd\u91cf\u6c38\u4e45+" + this.itemUsedData[name];
                }
            }
        }
        if (this.useitemstate == 0) {
            this.subtractItem(index, name, (byte)1);
        }
        return tempstr;
    }

    public String useItem(Role role) {
        this.useitemstate = 0;
        String tempstr = "";
        if (role.life[0] < 1) {
            tempstr = "\u89d2\u8272\u5df2\u6b7b\u4ea1\uff0c\u65e0\u6cd5\u4f7f\u7528\u9053\u5177\u3002";
            this.useitemstate = 1;
        } else if (this.itemPackNum >= 56) {
            tempstr = "\u80cc\u5305\u5df2\u589e\u81f3\u6700\u5927\u3002";
            this.useitemstate = 1;
        } else {
            this.itemPackNum = (byte)(this.itemPackNum + this.itemUsedData[10]);
            this.upgradeItemPack(this.itemPack, this.itemPackNum);
            this.upgradeWeaponPack(this.weaponPack, this.itemPackNum);
            this.upgradeSuitPack(this.suitPack, this.itemPackNum);
            this.upgradePlusPack(this.plusPack, this.itemPackNum);
            tempstr = "\u4f7f\u7528\u6210\u529f\uff0c\u80cc\u5305\u6570\u91cf\u5df2\u589e\u81f3" + this.itemPackNum;
        }
        return tempstr;
    }

    public void upgradeItemPack(byte[][] data, int num) {
        int i;
        byte[][] tempdata = new byte[2][num];
        for (i = 0; i < tempdata[0].length; i = (int)((byte)(i + 1))) {
            tempdata[0][i] = -1;
            tempdata[1][i] = -1;
        }
        for (i = 0; i < this.itemPack.length; i = (int)((byte)(i + 1))) {
            for (int j = 0; j < this.itemPack[0].length; j = (int)((byte)(j + 1))) {
                tempdata[i][j] = this.itemPack[i][j];
            }
        }
        this.itemPack = tempdata;
    }

    public void upgradeWeaponPack(byte[][] data, int num) {
        int i;
        byte[][] tempdata = new byte[2][num];
        for (i = 0; i < tempdata[0].length; i = (int)((byte)(i + 1))) {
            tempdata[0][i] = -1;
            tempdata[1][i] = -1;
        }
        for (i = 0; i < this.weaponPack.length; i = (int)((byte)(i + 1))) {
            for (int j = 0; j < this.weaponPack[0].length; j = (int)((byte)(j + 1))) {
                tempdata[i][j] = this.weaponPack[i][j];
            }
        }
        this.weaponPack = tempdata;
    }

    public void upgradeSuitPack(byte[][] data, int num) {
        int i;
        byte[][] tempdata = new byte[2][num];
        for (i = 0; i < tempdata[0].length; i = (int)((byte)(i + 1))) {
            tempdata[0][i] = -1;
            tempdata[1][i] = -1;
        }
        for (i = 0; i < this.suitPack.length; i = (int)((byte)(i + 1))) {
            for (int j = 0; j < this.suitPack[0].length; j = (int)((byte)(j + 1))) {
                tempdata[i][j] = this.suitPack[i][j];
            }
        }
        this.suitPack = tempdata;
    }

    public void upgradePlusPack(byte[][] data, int num) {
        int i;
        byte[][] tempdata = new byte[3][num];
        for (i = 0; i < tempdata[0].length; i = (int)((byte)(i + 1))) {
            tempdata[0][i] = -1;
            tempdata[1][i] = -1;
            tempdata[2][i] = -1;
        }
        for (i = 0; i < this.plusPack.length; i = (int)((byte)(i + 1))) {
            for (int j = 0; j < this.plusPack[0].length; j = (int)((byte)(j + 1))) {
                tempdata[i][j] = this.plusPack[i][j];
            }
        }
        this.plusPack = tempdata;
    }

    public void addItem(byte itemname, byte addnum) {
        byte tempnum = addnum;
        byte num = this.itemPackUseNum;
        byte[][] tempdata = null;
        tempdata = this.copydata(this.itemPack);
        this.addState = (byte)-1;
        int j = 0;
        for (int i = 0; i < this.itemPack[0].length; i = (int)((byte)(i + 1))) {
            if (tempdata[0][i] == -1) {
                tempdata[0][i] = itemname;
                tempdata[1][i] = addnum;
                num = (byte)(num + 1);
                addnum = 0;
                break;
            }
            if (tempdata[0][i] != itemname || tempdata[1][i] >= 99) continue;
            for (j = i; addnum > 0 && j < this.itemPack[0].length; j = (int)((byte)(j + 1))) {
                if (tempdata[0][j] == -1) {
                    tempdata[0][j] = itemname;
                    tempdata[1][j] = addnum;
                    num = (byte)(num + 1);
                    addnum = 0;
                    continue;
                }
                if (tempdata[0][j] != itemname || tempdata[1][j] >= 99) continue;
                if (tempdata[1][j] + addnum > 99) {
                    addnum = (byte)(tempdata[1][j] + addnum - 99);
                    tempdata[1][j] = 99;
                    continue;
                }
                byte[] byArray = tempdata[1];
                int n = j;
                byArray[n] = (byte)(byArray[n] + addnum);
                addnum = 0;
            }
            break;
        }
        if (addnum == 0) {
            this.addState = 0;
            byte by = itemname;
            this.itemTotal[by] = (short)(this.itemTotal[by] + tempnum);
            this.itemPack = tempdata;
            this.itemPackUseNum = num;
            this.checkmission(2, itemname, this.itemTotal[itemname]);
            this.flushItemBox();
        }
    }

    public byte[][] addItem(byte[][] data, byte itemname, byte addnum) {
        byte[][] tempdata = null;
        tempdata = this.copydata(data);
        int j = 0;
        for (int i = 0; i < data[0].length; i = (int)((byte)(i + 1))) {
            if (tempdata[0][i] == -1) {
                tempdata[0][i] = itemname;
                tempdata[1][i] = addnum;
                addnum = 0;
                break;
            }
            if (tempdata[0][i] != itemname || tempdata[1][i] >= 99) continue;
            for (j = i; addnum > 0 && j < data[0].length; j = (int)((byte)(j + 1))) {
                if (tempdata[0][j] == -1) {
                    tempdata[0][j] = itemname;
                    tempdata[1][j] = addnum;
                    addnum = 0;
                    continue;
                }
                if (tempdata[0][j] != itemname || tempdata[1][j] >= 99) continue;
                if (tempdata[1][j] + addnum > 99) {
                    addnum = (byte)(tempdata[1][j] + addnum - 99);
                    tempdata[1][j] = 99;
                    continue;
                }
                byte[] byArray = tempdata[1];
                int n = j;
                byArray[n] = (byte)(byArray[n] + addnum);
                addnum = 0;
            }
            break;
        }
        return tempdata;
    }

    private void flushItemBox() {
        byte[] tempdata = new byte[2];
        for (int i = 0; i < this.itemPack[0].length - 1; ++i) {
            for (int j = i + 1; j < this.itemPack[0].length; ++j) {
                if (this.itemPack[0][j] <= -1) continue;
                if (this.itemPack[0][i] == -1) {
                    tempdata[0] = this.itemPack[0][i];
                    tempdata[1] = this.itemPack[1][i];
                    this.itemPack[0][i] = this.itemPack[0][j];
                    this.itemPack[1][i] = this.itemPack[1][j];
                    this.itemPack[0][j] = tempdata[0];
                    this.itemPack[1][j] = tempdata[1];
                    continue;
                }
                if (this.itemLevel[this.itemPack[0][i]] == this.itemLevel[this.itemPack[0][j]]) {
                    if (this.itemPack[0][i] == this.itemPack[0][j]) {
                        if (this.itemPack[1][i] >= 99) continue;
                        if (this.itemPack[1][i] + this.itemPack[1][j] > 99) {
                            byte[] byArray = this.itemPack[1];
                            int n = j;
                            byArray[n] = (byte)(byArray[n] - (99 - this.itemPack[1][i]));
                            this.itemPack[1][i] = 99;
                            continue;
                        }
                        byte[] byArray = this.itemPack[1];
                        int n = i;
                        byArray[n] = (byte)(byArray[n] + this.itemPack[1][j]);
                        this.itemPack[0][j] = -1;
                        this.itemPackUseNum = (byte)(this.itemPackUseNum - 1);
                        continue;
                    }
                    if (this.itemPack[0][i] >= this.itemPack[0][j]) continue;
                    tempdata[0] = this.itemPack[0][i];
                    tempdata[1] = this.itemPack[1][i];
                    this.itemPack[0][i] = this.itemPack[0][j];
                    this.itemPack[1][i] = this.itemPack[1][j];
                    this.itemPack[0][j] = tempdata[0];
                    this.itemPack[1][j] = tempdata[1];
                    continue;
                }
                if (this.itemLevel[this.itemPack[0][i]] >= this.itemLevel[this.itemPack[0][j]]) continue;
                tempdata[0] = this.itemPack[0][i];
                tempdata[1] = this.itemPack[1][i];
                this.itemPack[0][i] = this.itemPack[0][j];
                this.itemPack[1][i] = this.itemPack[1][j];
                this.itemPack[0][j] = tempdata[0];
                this.itemPack[1][j] = tempdata[1];
            }
        }
    }

    public void subtractItem(int itempos, byte itemname, byte reducenum) {
        byte tempnun = reducenum;
        byte[][] tempdata = null;
        tempdata = this.itemPack;
        this.addState = (byte)-1;
        while (reducenum > 0) {
            byte[] byArray = tempdata[1];
            int n = itempos;
            byArray[n] = (byte)(byArray[n] - reducenum);
            reducenum = 0;
            if (tempdata[1][itempos] >= 1) continue;
            reducenum = -tempdata[1][itempos];
            tempdata[0][itempos] = -1;
            this.itemPackUseNum = (byte)(this.itemPackUseNum - 1);
            this.addState = (byte)-2;
            itempos = this.getItemPos(tempdata, itemname);
        }
        byte by = itemname;
        this.itemTotal[by] = (short)(this.itemTotal[by] - tempnun);
        this.checkmission(2, itemname, this.itemTotal[itemname]);
        this.flushItemBox();
    }

    public boolean checkMoney(int money) {
        return this.playerMoney >= money;
    }

    public boolean checkpack(byte[] reward) {
        byte[][] tempdata = this.copydata(this.itemPack);
        for (int i = 0; i < reward.length; i += 2) {
            if (!this.checkItemBox(tempdata, reward[i], reward[i + 1])) {
                return false;
            }
            tempdata = this.addItem(tempdata, reward[i], reward[i + 1]);
        }
        return true;
    }

    public boolean checkItemBox(byte[][] data, byte itemname, byte addnum) {
        int i;
        byte[][] tempdata = new byte[data.length][data[0].length];
        boolean bl = false;
        int j = 0;
        for (i = 0; i < tempdata.length; i = (int)((byte)(i + 1))) {
            for (j = 0; j < tempdata[i].length; j = (int)((byte)(j + 1))) {
                tempdata[i][j] = data[i][j];
            }
        }
        for (i = 0; i < tempdata[0].length; i = (int)((byte)(i + 1))) {
            if (tempdata[0][i] == -1) {
                bl = true;
                break;
            }
            if (tempdata[0][i] != itemname || tempdata[1][i] >= 99) continue;
            for (j = i; addnum > 0 && j < tempdata[0].length; j = (int)((byte)(j + 1))) {
                if (tempdata[0][j] == -1) {
                    addnum = 0;
                    bl = true;
                    continue;
                }
                if (tempdata[0][j] != itemname || tempdata[1][j] >= 99) continue;
                if (tempdata[1][j] + addnum > 99) {
                    addnum = (byte)(tempdata[1][j] + addnum - 99);
                    tempdata[1][j] = 99;
                    continue;
                }
                addnum = 0;
                bl = true;
            }
            break;
        }
        return bl;
    }

    public byte getItemPos(byte[][] data, byte itemname) {
        for (byte i = (byte)(data[0].length - 1); i >= 0; i = (byte)(i - 1)) {
            if (data[0][i] != itemname) continue;
            return i;
        }
        return -1;
    }

    public void addWeapon(byte weaponname, byte state) {
        byte num = this.weaponPackUseNum;
        byte[][] tempdata = null;
        tempdata = this.copydata(this.weaponPack);
        this.addState = (byte)-1;
        for (int i = 0; i < this.weaponPack[0].length; i = (int)((byte)(i + 1))) {
            if (tempdata[0][i] != -1) continue;
            tempdata[0][i] = weaponname;
            tempdata[1][i] = state;
            num = (byte)(num + 1);
            this.addState = 0;
            break;
        }
        if (this.addState == 0) {
            this.weaponPack = tempdata;
            byte by = weaponname;
            this.itemTotal[by] = (short)(this.itemTotal[by] + 1);
            this.weaponPackUseNum = num;
            this.flushWeaponBox();
            this.checkmission(5, weaponname, this.itemTotal[weaponname]);
        }
    }

    public boolean checkArmBox(byte weaponname) {
        byte checkType = this.canvas.rolem.setArmType(weaponname);
        boolean bl = false;
        byte[][] tempdata = null;
        switch (checkType) {
            case 0: {
                tempdata = this.copydata(this.weaponPack);
                break;
            }
            case 1: {
                tempdata = this.copydata(this.suitPack);
                break;
            }
            case 2: {
                tempdata = this.copydata(this.plusPack);
            }
        }
        for (int i = 0; i < tempdata[0].length; i = (int)((byte)(i + 1))) {
            if (tempdata[0][i] != -1) continue;
            bl = true;
            break;
        }
        return bl;
    }

    public void subtractWeapon(int weaponpos) {
        byte weaponname = this.weaponPack[0][weaponpos];
        this.weaponPack[0][weaponpos] = -1;
        this.weaponPack[1][weaponpos] = -1;
        byte by = weaponname;
        this.itemTotal[by] = (short)(this.itemTotal[by] - 1);
        this.weaponPackUseNum = (byte)(this.weaponPackUseNum - 1);
        this.addState = (byte)-2;
        this.flushWeaponBox();
    }

    private void flushWeaponBox() {
        byte[] tempdata = new byte[2];
        for (int i = 0; i < this.weaponPack[0].length - 1; ++i) {
            for (int j = i + 1; j < this.weaponPack[0].length; ++j) {
                if (this.weaponPack[0][j] <= -1) continue;
                if (this.weaponPack[0][i] == -1) {
                    tempdata[0] = this.weaponPack[0][i];
                    tempdata[1] = this.weaponPack[1][i];
                    this.weaponPack[0][i] = this.weaponPack[0][j];
                    this.weaponPack[1][i] = this.weaponPack[1][j];
                    this.weaponPack[0][j] = tempdata[0];
                    this.weaponPack[1][j] = tempdata[1];
                    continue;
                }
                if (this.itemLevel[this.weaponPack[0][i]] == this.itemLevel[this.weaponPack[0][j]]) {
                    if (this.weaponPack[0][i] >= this.weaponPack[0][j]) continue;
                    tempdata[0] = this.weaponPack[0][i];
                    tempdata[1] = this.weaponPack[1][i];
                    this.weaponPack[0][i] = this.weaponPack[0][j];
                    this.weaponPack[1][i] = this.weaponPack[1][j];
                    this.weaponPack[0][j] = tempdata[0];
                    this.weaponPack[1][j] = tempdata[1];
                    continue;
                }
                if (this.itemLevel[this.weaponPack[0][i]] >= this.itemLevel[this.weaponPack[0][j]]) continue;
                tempdata[0] = this.weaponPack[0][i];
                tempdata[1] = this.weaponPack[1][i];
                this.weaponPack[0][i] = this.weaponPack[0][j];
                this.weaponPack[1][i] = this.weaponPack[1][j];
                this.weaponPack[0][j] = tempdata[0];
                this.weaponPack[1][j] = tempdata[1];
            }
        }
    }

    public void addSuit(byte weaponname, byte state) {
        byte num = this.suitPackUseNum;
        byte[][] tempdata = null;
        tempdata = this.copydata(this.suitPack);
        this.addState = (byte)-1;
        for (int i = 0; i < this.suitPack[0].length; i = (int)((byte)(i + 1))) {
            if (tempdata[0][i] != -1) continue;
            tempdata[0][i] = weaponname;
            tempdata[1][i] = state;
            num = (byte)(num + 1);
            this.addState = 0;
            break;
        }
        if (this.addState == 0) {
            this.suitPack = tempdata;
            byte by = weaponname;
            this.itemTotal[by] = (short)(this.itemTotal[by] + 1);
            this.suitPackUseNum = num;
            this.flushSuitBox();
        }
    }

    public void subtractSuit(int weaponpos) {
        byte weaponname = this.suitPack[0][weaponpos];
        this.suitPack[0][weaponpos] = -1;
        this.suitPack[1][weaponpos] = -1;
        byte by = weaponname;
        this.itemTotal[by] = (short)(this.itemTotal[by] - 1);
        this.suitPackUseNum = (byte)(this.suitPackUseNum - 1);
        this.addState = (byte)-2;
        this.flushSuitBox();
    }

    private void flushSuitBox() {
        byte[] tempdata = new byte[2];
        for (int i = 0; i < this.suitPack[0].length - 1; ++i) {
            for (int j = i + 1; j < this.suitPack[0].length; ++j) {
                if (this.suitPack[0][j] <= -1) continue;
                if (this.suitPack[0][i] == -1) {
                    tempdata[0] = this.suitPack[0][i];
                    tempdata[1] = this.suitPack[1][i];
                    this.suitPack[0][i] = this.suitPack[0][j];
                    this.suitPack[1][i] = this.suitPack[1][j];
                    this.suitPack[0][j] = tempdata[0];
                    this.suitPack[1][j] = tempdata[1];
                    continue;
                }
                if (this.itemLevel[this.suitPack[0][i]] == this.itemLevel[this.suitPack[0][j]]) {
                    if (this.suitPack[0][i] >= this.suitPack[0][j]) continue;
                    tempdata[0] = this.suitPack[0][i];
                    tempdata[1] = this.suitPack[1][i];
                    this.suitPack[0][i] = this.suitPack[0][j];
                    this.suitPack[1][i] = this.suitPack[1][j];
                    this.suitPack[0][j] = tempdata[0];
                    this.suitPack[1][j] = tempdata[1];
                    continue;
                }
                if (this.itemLevel[this.suitPack[0][i]] >= this.itemLevel[this.suitPack[0][j]]) continue;
                tempdata[0] = this.suitPack[0][i];
                tempdata[1] = this.suitPack[1][i];
                this.suitPack[0][i] = this.suitPack[0][j];
                this.suitPack[1][i] = this.suitPack[1][j];
                this.suitPack[0][j] = tempdata[0];
                this.suitPack[1][j] = tempdata[1];
            }
        }
    }

    public void addPlus(byte weaponname, byte state, byte data) {
        byte num = this.plusPackUseNum;
        byte[][] tempdata = null;
        tempdata = this.copydata(this.plusPack);
        this.addState = (byte)-1;
        for (int i = 0; i < this.plusPack[0].length; i = (int)((byte)(i + 1))) {
            if (tempdata[0][i] != -1) continue;
            tempdata[0][i] = weaponname;
            tempdata[1][i] = state;
            tempdata[2][i] = data;
            num = (byte)(num + 1);
            this.addState = 0;
            break;
        }
        if (this.addState == 0) {
            this.plusPack = tempdata;
            byte by = weaponname;
            this.itemTotal[by] = (short)(this.itemTotal[by] + 1);
            this.plusPackUseNum = num;
            this.flushPlusBox();
        }
    }

    public boolean checkPlusBox(byte weaponname) {
        boolean bl = false;
        byte[][] tempdata = null;
        tempdata = this.copydata(this.plusPack);
        for (int i = 0; i < this.plusPack[0].length; i = (int)((byte)(i + 1))) {
            if (tempdata[0][i] != -1) continue;
            bl = true;
            break;
        }
        return bl;
    }

    public void subtractPlus(int weaponpos) {
        byte weaponname = this.plusPack[0][weaponpos];
        this.plusPack[0][weaponpos] = -1;
        this.plusPack[1][weaponpos] = -1;
        byte by = weaponname;
        this.itemTotal[by] = (short)(this.itemTotal[by] - 1);
        this.plusPackUseNum = (byte)(this.plusPackUseNum - 1);
        this.addState = (byte)-2;
        this.flushPlusBox();
    }

    private void flushPlusBox() {
        byte[] tempdata = new byte[3];
        for (int i = 0; i < this.plusPack[0].length - 1; ++i) {
            for (int j = i + 1; j < this.plusPack[0].length; ++j) {
                if (this.plusPack[0][j] <= -1) continue;
                if (this.plusPack[0][i] == -1) {
                    tempdata[0] = this.plusPack[0][i];
                    tempdata[1] = this.plusPack[1][i];
                    tempdata[2] = this.plusPack[2][i];
                    this.plusPack[0][i] = this.plusPack[0][j];
                    this.plusPack[1][i] = this.plusPack[1][j];
                    this.plusPack[2][i] = this.plusPack[2][j];
                    this.plusPack[0][j] = tempdata[0];
                    this.plusPack[1][j] = tempdata[1];
                    this.plusPack[2][j] = tempdata[2];
                    continue;
                }
                if (this.itemLevel[this.plusPack[0][i]] == this.itemLevel[this.plusPack[0][j]]) {
                    if (this.plusPack[0][i] >= this.plusPack[0][j]) continue;
                    tempdata[0] = this.plusPack[0][i];
                    tempdata[1] = this.plusPack[1][i];
                    tempdata[2] = this.plusPack[2][i];
                    this.plusPack[0][i] = this.plusPack[0][j];
                    this.plusPack[1][i] = this.plusPack[1][j];
                    this.plusPack[2][i] = this.plusPack[2][j];
                    this.plusPack[0][j] = tempdata[0];
                    this.plusPack[1][j] = tempdata[1];
                    this.plusPack[2][j] = tempdata[2];
                    continue;
                }
                if (this.itemLevel[this.plusPack[0][i]] >= this.itemLevel[this.plusPack[0][j]]) continue;
                tempdata[0] = this.plusPack[0][i];
                tempdata[1] = this.plusPack[1][i];
                tempdata[2] = this.plusPack[2][i];
                this.plusPack[0][i] = this.plusPack[0][j];
                this.plusPack[1][i] = this.plusPack[1][j];
                this.plusPack[2][i] = this.plusPack[2][j];
                this.plusPack[0][j] = tempdata[0];
                this.plusPack[1][j] = tempdata[1];
                this.plusPack[2][j] = tempdata[2];
            }
        }
    }

    private byte[][] copydata(byte[][] data) {
        byte[][] tempdata = new byte[data.length][];
        for (int i = 0; i < data.length; i = (int)((byte)(i + 1))) {
            tempdata[i] = new byte[data[i].length];
            for (int j = 0; j < tempdata[i].length; ++j) {
                tempdata[i][j] = data[i][j];
            }
        }
        return tempdata;
    }

    private short[][] copydata(short[][] data) {
        short[][] tempdata = new short[data.length][];
        for (int i = 0; i < data.length; i = (int)((byte)(i + 1))) {
            tempdata[i] = new short[data[i].length];
            for (int j = 0; j < tempdata[i].length; ++j) {
                tempdata[i][j] = data[i][j];
            }
        }
        return tempdata;
    }

    private byte[] copydata(byte[] data) {
        byte[] tempdata = new byte[data.length];
        for (int i = 0; i < data.length; i = (int)((byte)(i + 1))) {
            tempdata[i] = data[i];
        }
        return tempdata;
    }

    private int[] copydata(int[] data) {
        int[] tempdata = new int[data.length];
        for (int i = 0; i < data.length; i = (int)((byte)(i + 1))) {
            tempdata[i] = data[i];
        }
        return tempdata;
    }

    private short[] copydata(short[] data) {
        short[] tempdata = new short[data.length];
        for (int i = 0; i < data.length; i = (int)((byte)(i + 1))) {
            tempdata[i] = data[i];
        }
        return tempdata;
    }

    public void forgeArm(byte armName, int money) {
        int i;
        this.tempplusdata = 0;
        for (i = 0; i < this.forgeMaterial[armName - 44].length; ++i) {
            if (this.itemTotal[this.forgeMaterial[armName - 44][i]] < this.forgeMaterialNum[armName - 44][i]) {
                this.subtractItem(this.getItemPos(this.itemPack, this.forgeMaterial[armName - 44][i]), this.forgeMaterial[armName - 44][i], (byte)this.itemTotal[this.forgeMaterial[armName - 44][i]]);
                continue;
            }
            this.subtractItem(this.getItemPos(this.itemPack, this.forgeMaterial[armName - 44][i]), this.forgeMaterial[armName - 44][i], this.forgeMaterialNum[armName - 44][i]);
        }
        if (armName >= 88 && armName <= 123) {
            if (this.canvas.rolem.armData[armName - 44][0] == this.canvas.rolem.armData[armName - 44][1]) {
                this.tempplusdata = this.canvas.rolem.armData[armName - 44][0];
                this.laohujitype = 1;
            } else {
                this.tempplusdata = Util.getARandomInt(this.canvas.rolem.armData[armName - 44][0], this.canvas.rolem.armData[armName - 44][1] + 1);
                if (this.tempplusdata <= 0) {
                    this.tempplusdata = this.canvas.rolem.armData[armName - 44][0];
                }
                this.laohujitype = 0;
            }
        }
        this.addMoney(-money);
        this.addArm(armName, (byte)3, (byte)this.tempplusdata);
        switch (this.canvas.rolem.setArmType(armName)) {
            case 0: {
                for (i = 0; i < this.weaponPack[0].length; ++i) {
                    if (this.weaponPack[1][i] != 3) continue;
                    this.weaponPack[1][i] = -1;
                    this.changeArmIndex = (byte)i;
                }
                break;
            }
            case 2: {
                for (i = 0; i < this.plusPack[0].length; ++i) {
                    if (this.plusPack[1][i] != 3) continue;
                    this.plusPack[1][i] = -1;
                    this.changeArmIndex = (byte)i;
                }
                break;
            }
        }
    }

    public void changeWeapon(byte index) {
        this.putOffWeapon(this.setCurrentWeaponType(index));
        this.putOnWeapon(index);
    }

    public byte setCurrentWeaponType(byte index) {
        return this.canvas.rolem.setWeaponType(this.weaponPack[0][index]);
    }

    public boolean checkMoney(byte wname) {
        return this.playerMoney >= this.canvas.rolem.itemBusinessData[wname][0];
    }

    public boolean checkjialanyao(Role role) {
        if (this.itemTotal[6] > 0) {
            this.useItem(role, (byte)6);
            this.canvas.rolem.callDebuff((FSprite)role, 13, (int)role.spritex, role.spritey + 50, (int)role.spritez);
            return true;
        }
        if (this.itemTotal[5] > 0) {
            this.useItem(role, (byte)5);
            this.canvas.rolem.callDebuff((FSprite)role, 13, (int)role.spritex, role.spritey + 50, (int)role.spritez);
            return true;
        }
        if (this.itemTotal[4] > 0) {
            this.useItem(role, (byte)4);
            this.canvas.rolem.callDebuff((FSprite)role, 13, (int)role.spritex, role.spritey + 50, (int)role.spritez);
            return true;
        }
        return false;
    }

    public boolean checkWeapon(byte wname) {
        if (this.canvas.rolem.armData[wname - 44][4] > -1) {
            for (int i = 0; i < 3; ++i) {
                if (this.canvas.rolem.armData[wname - 44][4 + i] <= -1 || this.itemTotal[this.canvas.rolem.armData[wname - 44][4 + i]] <= 0) continue;
                return true;
            }
            this.commandStr = "\u7f3a\u5c11\u953b\u9020\u9700\u8981\u7684\u6b66\u5668\u3002";
            return false;
        }
        return true;
    }

    public boolean checkMaterial(byte wname) {
        for (int i = 0; i < this.forgeMaterial[wname - 44].length; ++i) {
            if (this.itemTotal[this.forgeMaterial[wname - 44][i]] >= this.forgeMaterialNum[wname - 44][i]) continue;
            return false;
        }
        return true;
    }

    public int jisuanchajia(byte wname) {
        int tempmoney = 0;
        for (int i = 0; i < this.forgeMaterial[wname - 44].length; ++i) {
            if (this.itemTotal[this.forgeMaterial[wname - 44][i]] >= this.forgeMaterialNum[wname - 44][i]) continue;
            tempmoney += (this.forgeMaterialNum[wname - 44][i] - this.itemTotal[this.forgeMaterial[wname - 44][i]]) * this.canvas.rolem.itemBusinessData[this.forgeMaterial[wname - 44][i]][0];
        }
        return tempmoney;
    }

    public void buqicailiao(byte wname) {
        int tempmoney = 0;
        int tempnum = 0;
        for (int i = 0; i < this.forgeMaterial[wname - 44].length; ++i) {
            if (this.itemTotal[this.forgeMaterial[wname - 44][i]] >= this.forgeMaterialNum[wname - 44][i]) continue;
            tempnum = this.forgeMaterialNum[wname - 44][i] - this.itemTotal[this.forgeMaterial[wname - 44][i]];
            tempmoney += (this.forgeMaterialNum[wname - 44][i] - this.itemTotal[this.forgeMaterial[wname - 44][i]]) * this.canvas.rolem.itemBusinessData[this.forgeMaterial[wname - 44][i]][0];
            this.addItem(this.forgeMaterial[wname - 44][i], (byte)tempnum);
        }
        this.addMoney(-tempmoney);
    }

    public boolean checkweaponputon(byte wname) {
        return this.weaponPack[0][this.getItemPos(this.weaponPack, wname)] == this.canvas.rolem.setWeaponType(wname);
    }

    public void autoAddItem(byte itemName, byte num) {
        if (itemName < 0 || itemName > 77) {
            return;
        }
        if (itemName >= 0 && itemName <= 43) {
            this.addItem(itemName, num);
        } else if (itemName > 43 && itemName <= 77) {
            this.addArm(itemName, (byte)-1, (byte)0);
        }
    }

    public void addArm(byte armName, byte state, byte data) {
        if (armName < 44 || armName > 123) {
            return;
        }
        switch (this.canvas.rolem.setArmType(armName)) {
            case 0: {
                this.addWeapon(armName, state);
                break;
            }
            case 1: {
                this.addSuit(armName, state);
                break;
            }
            case 2: {
                this.addPlus(armName, state, data);
            }
        }
    }

    public void addMoney(int num) {
        this.playerMoney += num;
        this.playerMoney = this.playerMoney < 0 ? 0 : this.playerMoney;
        this.playerMoney = this.playerMoney > 9999999 ? 9999999 : this.playerMoney;
    }

    public void replaceWeapon(byte Index, byte weaponName) {
        this.weaponPack[0][Index] = weaponName;
    }

    public void initmission() {
        Util.readXls2DUTF("/data/missioninfo0.data");
        this.mainmissioninfo = Util.xls2DUTFdata;
        Util.readXls2DUTF("/data/missioninfo1.data");
        this.missioninfo = Util.xls2DUTFdata;
        Util.clearXls2DUTF();
        Util.readXls2DSHORT("/data/missiondata0.data");
        this.missiondata0 = Util.xls2DSHORTdata;
        Util.readXls2DSHORT("/data/missiondata1.data");
        this.missiondata1 = Util.xls2DSHORTdata;
        Util.clearXls2DSHORT();
        this.currentmissionindex = new byte[22];
        this.missionplan = new short[22][2];
        this.mainmissionplan = new short[2];
        for (int i = 0; i < this.currentmissionindex.length; ++i) {
            this.currentmissionindex[i] = -1;
            this.missionplan[i][0] = 0;
            this.missionplan[i][1] = 0;
        }
    }

    public void cleancurrentmissionlist() {
        this.currentmissionlist = null;
    }

    public void setcurrentmission(byte cityindex) {
        byte skip = 0;
        byte[] tempdata = new byte[this.allmissionlist[cityindex].length];
        this.cleancurrentmissionlist();
        for (int i = 0; i < this.allmissionlist[cityindex].length; ++i) {
            if (this.missionstate[this.allmissionlist[cityindex][i]] >= 3) continue;
            byte by = skip;
            skip = (byte)(skip + 1);
            tempdata[by] = this.allmissionlist[cityindex][i];
        }
        this.townhouseBoxUseTotal = skip;
        if (skip > 0) {
            this.currentmissionlist = new byte[skip];
            System.arraycopy(tempdata, 0, this.currentmissionlist, 0, skip);
        }
    }

    public void openMission(int index) {
        this.missionstate[index] = 0;
        this.isshowmissionnews = (short)(this.isshowmissionnews | 1 << index);
        byte by = this.missioncity[index];
        this.isshowcitynews[by] = (byte)(this.isshowcitynews[by] | 4);
    }

    public void setmissionlist() {
        this.missionlist = null;
        this.missionindexlist = null;
        int i = 0;
        switch (this.missionUiBoxIndex) {
            case 0: {
                if (this.currentmainmissionindex <= -1) break;
                this.missionindexlist = new byte[1];
                this.missionindexlist[0] = this.currentmainmissionindex;
                this.missionlist = new String[this.missionindexlist.length];
                for (i = 0; i < this.missionindexlist.length; ++i) {
                    this.missionlist[i] = this.mainmissioninfo[this.missionindexlist[i]][0];
                }
                break;
            }
            case 1: {
                int num = 0;
                for (i = 0; i < this.currentmissionindex.length; ++i) {
                    if (this.currentmissionindex[i] <= -1) continue;
                    ++num;
                }
                if (num <= 0) break;
                this.missionindexlist = new byte[num];
                num = 0;
                for (i = 0; i < this.currentmissionindex.length; ++i) {
                    if (this.currentmissionindex[i] <= -1) continue;
                    this.missionindexlist[num++] = this.currentmissionindex[i];
                }
                this.missionlist = new String[this.missionindexlist.length];
                for (i = 0; i < this.missionindexlist.length; ++i) {
                    this.missionlist[i] = this.missioninfo[this.missionindexlist[i]][0];
                }
                break;
            }
        }
        if (this.missionlist != null) {
            if (this.missionUiBoxIndex == 1) {
                this.missionUiUseTotal = (byte)this.missionlist.length;
            }
            this.missionUiBoxSeletedIndex = 0;
            this.missionUiBoxStartIndex = 0;
        }
    }

    public void setmainmission(int mindex) {
        this.currentmainmissionindex = (byte)mindex;
        this.missionUiBoxIndex = 0;
        this.mainmissionstate = 1;
        this.mainmissionplan[0] = 0;
        this.mainmissionplan[1] = 0;
        switch (this.missiondata0[mindex][4]) {
            case 0: {
                break;
            }
            case 1: {
                break;
            }
            case 2: {
                for (int i = 0; i < 2; i = (int)((byte)(i + 1))) {
                    if (this.missiondata0[this.currentmainmissionindex][5 + i * 2] <= -1) continue;
                    this.checkmission(2, this.missiondata0[this.currentmainmissionindex][5 + i * 2], this.itemTotal[this.missiondata0[this.currentmainmissionindex][5 + i * 2]]);
                }
                break;
            }
            case 3: {
                this.checkmission(3, this.canvas.mapm.mapIndex, this.mineWorkerNum[this.mineIndex]);
                break;
            }
            case 5: {
                this.checkmission(5, this.missiondata0[this.currentmainmissionindex][5], this.itemTotal[this.missiondata0[this.currentmainmissionindex][5]]);
                break;
            }
            case 6: {
                this.checkmission(6, this.missiondata0[this.currentmainmissionindex][5], this.canvas.mapm.cityMapLevel[this.missiondata0[this.currentmainmissionindex][5]]);
            }
        }
        this.showplantime = 0;
        this.setmissionlist();
        this.showmission();
    }

    public void setmission(byte mindex) {
        this.isjieshou = false;
        this.showmissionindex = mindex;
        this.setmission();
    }

    public void setmission() {
        block6: {
            block5: {
                int i;
                for (i = 0; i < this.currentmissionindex.length; i = (int)((byte)(i + 1))) {
                    if (this.currentmissionindex[i] != -1) continue;
                    this.currentmissionindex[i] = this.showmissionindex;
                    this.missionplan[this.showmissionindex][0] = 0;
                    this.missionplan[this.showmissionindex][0] = 0;
                    this.isjieshou = true;
                    break;
                }
                if (!this.isjieshou) break block5;
                this.missionstate[this.showmissionindex] = 1;
                switch (this.missiondata1[this.showmissionindex][2]) {
                    case 2: {
                        for (i = 0; i < 2; i = (int)((byte)(i + 1))) {
                            if (this.missiondata1[this.showmissionindex][3 + i * 2] <= -1) continue;
                            this.checkmission(2, this.missiondata1[this.showmissionindex][3 + i * 2], this.itemTotal[this.missiondata1[this.showmissionindex][3 + i * 2]]);
                        }
                    }
                }
                break block6;
            }
            this.commandStr = "\u4efb\u52a1\u6570\u91cf\u5df2\u6ee1\uff0c\u65e0\u6cd5\u63a5\u53d7\u4efb\u52a1";
            this.startCommand();
            this.setCommandShowTime();
            this.changeCommandState((byte)2);
        }
    }

    public void postmission(byte mindex) {
        this.missionfinishedindex = mindex;
        this.currentreward = this.missionrewarddata[this.missionfinishedindex];
        this.currentrewardtype = this.missionrewardtype[this.missionfinishedindex];
        this.currentrewardmoney = this.missionmoneydata[this.missionfinishedindex];
        this.showmissionrewardname = this.missioninfo[this.missionfinishedindex][0];
        this.showmissionrewarddata = this.currentreward;
        this.showmissionrewardtype = this.currentrewardtype;
        this.showmissionrewardmoney = this.currentrewardmoney;
        this.initUiScreenData();
        this.isshowmissionreward = true;
        this.frameDelay = 0;
        this.actionFrameIndex = 0;
        this.isActionOver = false;
        this.postmission();
    }

    public void postmainmission(byte mindex) {
        this.missionfinishedindex = mindex;
        this.currentreward = this.mainmissionrewarddata[this.missionfinishedindex];
        this.currentrewardtype = this.mainmissionrewardtype[this.missionfinishedindex];
        this.currentrewardmoney = this.mainmissionmoneydata[this.missionfinishedindex];
        this.showmissionrewardname = this.mainmissioninfo[this.missionfinishedindex][0];
        this.showmissionrewarddata = this.currentreward;
        this.showmissionrewardtype = this.currentrewardtype;
        this.showmissionrewardmoney = this.currentrewardmoney;
        this.initUiScreenData();
        this.isshowmissionreward = true;
        this.frameDelay = 0;
        this.actionFrameIndex = 0;
        this.isActionOver = false;
        this.postmainmission();
    }

    public void postmission() {
        this.missionstate[this.missionfinishedindex] = 3;
        switch (this.missiondata1[this.missionfinishedindex][2]) {
            case 2: {
                for (int i = 0; i < 2; i = (int)((byte)(i + 1))) {
                    if (this.missiondata1[this.missionfinishedindex][3 + i * 2] <= -1) continue;
                    this.subtractItem(this.getItemPos(this.itemPack, (byte)this.missiondata1[this.missionfinishedindex][3 + i * 2]), (byte)this.missiondata1[this.missionfinishedindex][3 + i * 2], (byte)this.missiondata1[this.missionfinishedindex][4 + i * 2]);
                }
                break;
            }
        }
        byte index = this.getmissionindex(this.missionfinishedindex);
        this.currentmissionindex[index] = -1;
        this.missionfinishedindex = (byte)-1;
        this.flushmission();
    }

    public void postmainmission() {
        if (this.missiondata0[this.currentmainmissionindex][4] == 2) {
            for (int i = 0; i < 2; i = (int)((byte)(i + 1))) {
                if (this.missiondata0[this.currentmainmissionindex][5 + i * 2] <= -1) continue;
                this.subtractItem(this.getItemPos(this.itemPack, (byte)this.missiondata0[this.currentmainmissionindex][5 + i * 2]), (byte)this.missiondata0[this.currentmainmissionindex][5 + i * 2], (byte)this.missiondata0[this.currentmainmissionindex][6 + i * 2]);
            }
        }
        this.missionfinishedindex = (byte)-1;
    }

    public void giveupmission(byte seletedindex, byte startindex) {
        this.missionstate[this.currentmissionindex[seletedindex + startindex]] = 0;
        this.missionplan[this.currentmissionindex[seletedindex + startindex]][0] = 0;
        this.missionplan[this.currentmissionindex[seletedindex + startindex]][1] = 0;
        this.currentmissionindex[seletedindex + startindex] = -1;
        this.flushmission();
        this.setmissionlist();
        if (this.missionindexlist != null) {
            this.missionUiBoxSeletedIndex = seletedindex;
            this.missionUiBoxStartIndex = startindex;
            if (this.missionUiBoxSeletedIndex + this.missionUiBoxStartIndex > this.missionindexlist.length - 1) {
                if (this.missionUiBoxStartIndex > 0) {
                    this.missionUiBoxStartIndex = (byte)(this.missionUiBoxStartIndex - 1);
                } else if (this.missionUiBoxSeletedIndex > 0) {
                    this.missionUiBoxSeletedIndex = (byte)(this.missionUiBoxSeletedIndex - 1);
                }
            }
        }
    }

    public void flushmission() {
        for (int i = 0; i < this.currentmissionindex.length; ++i) {
            for (int j = i; j < this.currentmissionindex.length; ++j) {
                if (this.currentmissionindex[j] <= -1 || this.currentmissionindex[i] != -1) continue;
                byte temp = this.currentmissionindex[i];
                this.currentmissionindex[i] = this.currentmissionindex[j];
                this.currentmissionindex[j] = temp;
            }
        }
    }

    private byte getmissionindex(byte mindex) {
        for (byte i = 0; i < this.currentmissionindex.length; i = (byte)(i + 1)) {
            if (this.currentmissionindex[i] != mindex) continue;
            return i;
        }
        return -1;
    }

    private void showframeprocess() {
        this.showframeindex = (short)(this.showframeindex + 1);
        if (this.showframeindex >= this.showframedata.length) {
            this.isshowframeover = true;
        }
    }

    public void showMissionPlan(int type, byte index, int missiontype) {
        this.showplantype = (byte)type;
        if (this.showplantype == 2) {
            this.showplanindex[0] = this.currentmainmissionindex;
            this.showplanindex[1] = index;
        } else {
            this.showplanindex[missiontype] = index;
        }
        switch (this.showplantype) {
            case 0: {
                if (this.mainmissionstate != 2) break;
                this.showframeindex = 0;
                this.isshowframeover = false;
                break;
            }
            case 1: {
                if (this.missionstate[this.showplanindex[missiontype]] != 2) break;
                this.showframeindex = 0;
                this.isshowframeover = false;
                break;
            }
            case 2: {
                if (this.mainmissionstate != 2 && this.missionstate[this.showplanindex[missiontype]] != 2) break;
                this.showframeindex = 0;
                this.isshowframeover = false;
            }
        }
        this.initMissionPlanScreenData();
        this.showplantime = (byte)20;
    }

    public void initMissionPlanScreenData() {
        int i;
        for (i = 0; i < this.missionPlanScreenData.length; ++i) {
            this.missionPlanScreenData[i] = -939524096;
        }
        for (i = 0; i < this.minePlanScreenData.length; ++i) {
            this.minePlanScreenData[i] = -939524096;
        }
    }

    public void drawMissionPlanScreenData(int type, int line, int offsety) {
        switch (type) {
            case 0: {
                for (int i = 0; i < 30 + line * 30; ++i) {
                    this.canvas.g.drawRGB(this.missionPlanScreenData, 0, 160, 0, 85 + i + offsety, 160, 1, true);
                }
                break;
            }
            case 1: {
                for (int i = 0; i < 19; ++i) {
                    this.canvas.g.drawRGB(this.minePlanScreenData, 0, 640, 0, 109 + i + -45, 640, 1, true);
                }
                break;
            }
            case 2: {
                for (int i = 0; i < 30 + line * 30; ++i) {
                    this.canvas.g.drawRGB(this.missionPlanScreenData, 0, 160, 0, 32 + line * 30 + 85 + i + offsety, 160, 1, true);
                }
                break;
            }
        }
    }

    public int showmainmissionplandatainfo(int offsety) {
        int state = 0;
        boolean state1 = false;
        String str = "";
        String str1 = "";
        switch (this.mainmissionstate) {
            case 1: {
                switch (this.missiondata0[this.showplanindex[0]][4]) {
                    case 1: {
                        str = this.canvas.rolem.rolename[this.missiondata0[this.showplanindex[0]][5]] + " " + this.mainmissionplan[0] + "/" + this.missiondata0[this.showplanindex[0]][6];
                        if (this.missiondata0[this.showplanindex[0]][7] <= -1) break;
                        str1 = str1 + this.canvas.rolem.rolename[this.missiondata0[this.showplanindex[0]][7]] + " " + this.mainmissionplan[1] + "/" + this.missiondata0[this.showplanindex[0]][8];
                        state1 = true;
                        break;
                    }
                    case 2: {
                        str = this.iteminfo[this.missiondata0[this.currentmainmissionindex][5]][0] + " " + this.mainmissionplan[0] + "/" + this.missiondata0[this.currentmainmissionindex][6];
                        if (this.missiondata0[this.currentmainmissionindex][7] <= -1) break;
                        str1 = str1 + this.iteminfo[this.missiondata0[this.currentmainmissionindex][7]][0] + " " + this.mainmissionplan[1] + "/" + this.missiondata0[this.currentmainmissionindex][8];
                        state1 = true;
                        break;
                    }
                    case 3: {
                        str = "\u77ff\u5de5  " + this.mainmissionplan[0] + "/" + this.missiondata0[this.showplanindex[0]][6];
                        break;
                    }
                    case 5: {
                        str = this.iteminfo[this.missiondata0[this.showplanindex[0]][5]][0] + " " + this.mainmissionplan[0] + "/" + this.missiondata0[this.showplanindex[0]][6];
                        break;
                    }
                    case 6: {
                        str = "\u65b0\u9521\u5b89\u57ce\u5e02\u7b49\u7ea7  " + this.mainmissionplan[0] + "/" + this.missiondata0[this.showplanindex[0]][6];
                    }
                }
                if (state1) {
                    this.drawMissionPlanScreenData(0, 2, offsety);
                } else {
                    this.drawMissionPlanScreenData(0, 1, offsety);
                }
                this.canvas.gameUi.drawlabel("\u4e3b\u7ebf\u4efb\u52a1", 5, 90 + offsety + 0, 4, 0);
                this.canvas.drawColorString(str, 10, 115 + offsety + 3, 20, 0xFFFFFF, 0);
                this.canvas.drawColorString(str1, 10, 135 + offsety + 6, 20, 0xFFFFFF, 0);
                break;
            }
            case 2: {
                if (!this.isshowframeover) {
                    this.canvas.drawFrame(this.canvas.rolem.effImages, this.canvas.rolem.effModules, this.canvas.rolem.effFrames, this.showframedata[this.showframeindex], 320, 71, 20);
                    this.showframeprocess();
                }
                if (this.showframeindex <= 4) break;
                this.drawMissionPlanScreenData(1, 0, offsety);
                str = this.mainmissioninfo[this.showplanindex[0]][0] + "\u5b8c\u6210";
                this.canvas.drawColorString(str, 320, 65, 17, 16770139, 1458234);
            }
        }
        state = 1;
        return state;
    }

    public void showextrmissionplandatainfo(int offsety) {
        boolean state = false;
        boolean state1 = false;
        String str = "";
        String str1 = "";
        switch (this.missionstate[this.showplanindex[1]]) {
            case 1: {
                switch (this.missiondata1[this.showplanindex[1]][2]) {
                    case 2: {
                        str = this.iteminfo[this.missiondata1[this.showplanindex[1]][3]][0] + " " + this.missionplan[this.showplanindex[1]][0] + "/" + this.missiondata1[this.showplanindex[1]][4];
                        if (this.missiondata1[this.showplanindex[1]][5] <= -1) break;
                        str1 = str1 + this.iteminfo[this.missiondata1[this.showplanindex[1]][5]][0] + " " + this.missionplan[this.showplanindex[1]][1] + "/" + this.missiondata1[this.showplanindex[1]][6];
                        state1 = true;
                        break;
                    }
                    case 1: {
                        str = this.canvas.rolem.rolename[this.missiondata1[this.showplanindex[1]][3]] + " " + this.missionplan[this.showplanindex[1]][0] + "/" + this.missiondata1[this.showplanindex[1]][4];
                        if (this.missiondata1[this.showplanindex[1]][5] <= -1) break;
                        str1 = str1 + this.canvas.rolem.rolename[this.missiondata1[this.showplanindex[1]][5]] + " " + this.missionplan[this.showplanindex[1]][1] + "/" + this.missiondata1[this.showplanindex[1]][6];
                        state1 = true;
                    }
                }
                if (state) break;
                if (state1) {
                    this.drawMissionPlanScreenData(this.showplantype == 2 ? 2 : 0, 2, offsety);
                } else {
                    this.drawMissionPlanScreenData(this.showplantype == 2 ? 2 : 0, 1, offsety);
                }
                this.canvas.gameUi.drawlabel("\u652f\u7ebf\u4efb\u52a1", 5, (this.showplantype == 2 ? (state1 ? 2 : 1) * 30 + 30 : 0) + 25 + 65 + offsety + 0, 4, 2);
                this.canvas.drawColorString(str, 10, (this.showplantype == 2 ? (state1 ? 2 : 1) * 30 + 30 : 0) + 25 + 85 + offsety + 3, 20, 0xFFFFFF, 0);
                this.canvas.drawColorString(str1, 10, (this.showplantype == 2 ? (state1 ? 2 : 1) * 30 + 30 : 0) + 25 + 100 + offsety + 6, 20, 0xFFFFFF, 0);
                break;
            }
            case 2: {
                if (state) break;
                if (!this.isshowframeover) {
                    this.canvas.drawFrame(this.canvas.rolem.effImages, this.canvas.rolem.effModules, this.canvas.rolem.effFrames, this.showframedata[this.showframeindex], 320, 71, 20);
                    this.showframeprocess();
                }
                if (this.showframeindex <= 4) break;
                this.drawMissionPlanScreenData(1, 0, offsety);
                str = this.missioninfo[this.showplanindex[1]][0] + "\u5b8c\u6210";
                this.canvas.drawColorString(str, 320, 65, 17, 16770139, 1458234);
            }
        }
    }

    public void drawMissionPlan(int offsety) {
        if (this.showplantime > 0 && !this.isshowmissionreward) {
            int state = 0;
            boolean state1 = false;
            this.showplantime = (byte)(this.showplantime - 1);
            String str = "";
            String str1 = "";
            switch (this.showplantype) {
                case 0: {
                    state = this.showmainmissionplandatainfo(offsety);
                    break;
                }
                case 1: {
                    this.showextrmissionplandatainfo(offsety);
                    break;
                }
                case 2: {
                    state = this.showmainmissionplandatainfo(offsety);
                    this.showextrmissionplandatainfo(offsety);
                }
            }
        }
    }

    public void checkmission(int type, short name, short num) {
        boolean checkshowflag = false;
        boolean checkshowextraFlag = false;
        byte currentmissionindextemp = -1;
        num = (short)(num > 127 ? 127 : (int)num);
        if (this.currentmainmissionindex > -1) {
            switch (type) {
                case 0: {
                    if (this.mainmissionstate != 1 || type != this.missiondata0[this.currentmainmissionindex][4] || name != this.missiondata0[this.currentmainmissionindex][9]) break;
                    this.mainmissionstate = (byte)2;
                    checkshowflag = true;
                    break;
                }
                case 1: {
                    if (this.mainmissionstate != 1 || type != this.missiondata0[this.currentmainmissionindex][4]) break;
                    if (this.missiondata0[this.currentmainmissionindex][5] > -1 && name == this.missiondata0[this.currentmainmissionindex][5] && this.mainmissionplan[0] < this.missiondata0[this.currentmainmissionindex][6]) {
                        this.mainmissionplan[0] = (short)(this.mainmissionplan[0] + num);
                        checkshowflag = true;
                    } else if (this.missiondata0[this.currentmainmissionindex][7] > -1 && name == this.missiondata0[this.currentmainmissionindex][7] && this.mainmissionplan[1] < this.missiondata0[this.currentmainmissionindex][8]) {
                        this.mainmissionplan[1] = (short)(this.mainmissionplan[1] + num);
                        checkshowflag = true;
                    }
                    if (this.missiondata0[this.currentmainmissionindex][5] > -1 && this.mainmissionplan[0] < this.missiondata0[this.currentmainmissionindex][6]) {
                        this.mainmissionstate = 1;
                        break;
                    }
                    if (this.missiondata0[this.currentmainmissionindex][7] > -1 && this.mainmissionplan[1] < this.missiondata0[this.currentmainmissionindex][8]) {
                        this.mainmissionstate = 1;
                        break;
                    }
                    this.mainmissionplan[0] = this.missiondata0[this.currentmainmissionindex][6];
                    this.mainmissionplan[1] = this.missiondata0[this.currentmainmissionindex][8];
                    this.mainmissionstate = (byte)2;
                    checkshowflag = true;
                    break;
                }
                case 2: {
                    if (this.mainmissionstate >= 3 || this.missionfinishedindex == this.currentmainmissionindex || type != this.missiondata0[this.currentmainmissionindex][4]) break;
                    if (this.missiondata0[this.currentmainmissionindex][5] > -1 && name == this.missiondata0[this.currentmainmissionindex][5]) {
                        this.mainmissionplan[0] = num < this.missiondata0[this.currentmainmissionindex][6] ? (short)((byte)num) : (short)((byte)this.missiondata0[this.currentmainmissionindex][6]);
                        if (this.mainmissionstate < 2) {
                            checkshowflag = true;
                        }
                    } else if (this.missiondata0[this.currentmainmissionindex][7] > -1 && name == this.missiondata0[this.currentmainmissionindex][7]) {
                        this.mainmissionplan[1] = num < this.missiondata0[this.currentmainmissionindex][8] ? (short)((byte)num) : this.missiondata0[this.currentmainmissionindex][8];
                        if (this.mainmissionstate < 2) {
                            checkshowflag = true;
                        }
                    }
                    if (this.missiondata0[this.currentmainmissionindex][5] > -1 && this.mainmissionplan[0] < this.missiondata0[this.currentmainmissionindex][6]) {
                        this.mainmissionstate = 1;
                        break;
                    }
                    if (this.missiondata0[this.currentmainmissionindex][7] > -1 && this.mainmissionplan[1] < this.missiondata0[this.currentmainmissionindex][8]) {
                        this.mainmissionstate = 1;
                        break;
                    }
                    if (this.mainmissionstate < 2) {
                        checkshowflag = true;
                    }
                    this.mainmissionplan[0] = this.missiondata0[this.currentmainmissionindex][6];
                    this.mainmissionplan[1] = this.missiondata0[this.currentmainmissionindex][8];
                    this.mainmissionstate = (byte)2;
                    break;
                }
                case 3: {
                    if (this.mainmissionstate != 1 || type != this.missiondata0[this.currentmainmissionindex][4] || name != this.canvas.mapm.universeMapAreaData[this.missiondata0[this.currentmainmissionindex][0]][1]) break;
                    this.mainmissionplan[0] = (short)(this.mainmissionplan[0] + num);
                    if (this.mainmissionplan[0] < this.missiondata0[this.currentmainmissionindex][6]) {
                        checkshowflag = true;
                        break;
                    }
                    this.mainmissionstate = (byte)2;
                    checkshowflag = true;
                    break;
                }
                case 5: {
                    if (this.mainmissionstate != 1 || type != this.missiondata0[this.currentmainmissionindex][4] || name != this.missiondata0[this.currentmainmissionindex][5]) break;
                    this.mainmissionplan[0] = num;
                    if (this.mainmissionplan[0] < this.missiondata0[this.currentmainmissionindex][6]) {
                        checkshowflag = true;
                        break;
                    }
                    this.mainmissionstate = (byte)2;
                    checkshowflag = true;
                    break;
                }
                case 6: {
                    if (this.mainmissionstate != 1 || type != this.missiondata0[this.currentmainmissionindex][4] || name != this.missiondata0[this.currentmainmissionindex][5]) break;
                    this.mainmissionplan[0] = num;
                    if (this.mainmissionplan[0] < this.missiondata0[this.currentmainmissionindex][6]) {
                        checkshowflag = true;
                        break;
                    }
                    this.mainmissionstate = (byte)2;
                    checkshowflag = true;
                }
            }
        }
        block12: for (byte i = 0; i < this.currentmissionindex.length; i = (byte)((byte)(i + 1))) {
            if (this.currentmissionindex[i] <= -1) continue;
            switch (type) {
                case 2: {
                    if (this.missionstate[this.currentmissionindex[i]] >= 3 || this.missionfinishedindex == i || type != this.missiondata1[this.currentmissionindex[i]][2]) continue block12;
                    if (this.missiondata1[this.currentmissionindex[i]][3] > -1 && name == this.missiondata1[this.currentmissionindex[i]][3]) {
                        this.missionplan[this.currentmissionindex[i]][0] = num < this.missiondata1[this.currentmissionindex[i]][4] ? (short)((byte)num) : (short)((byte)this.missiondata1[this.currentmissionindex[i]][4]);
                        if (this.missionstate[this.currentmissionindex[i]] < 2) {
                            checkshowextraFlag = true;
                            currentmissionindextemp = i;
                        }
                    } else if (this.missiondata1[this.currentmissionindex[i]][5] > -1 && name == this.missiondata1[this.currentmissionindex[i]][5]) {
                        this.missionplan[this.currentmissionindex[i]][1] = num < this.missiondata1[this.currentmissionindex[i]][6] ? (short)((byte)num) : (short)((byte)this.missiondata1[this.currentmissionindex[i]][6]);
                        if (this.missionstate[this.currentmissionindex[i]] < 2) {
                            checkshowextraFlag = true;
                            currentmissionindextemp = i;
                        }
                    }
                    if (this.missiondata1[this.currentmissionindex[i]][3] > -1 && this.missionplan[this.currentmissionindex[i]][0] < this.missiondata1[this.currentmissionindex[i]][4]) {
                        this.missionstate[this.currentmissionindex[i]] = 1;
                        continue block12;
                    }
                    if (this.missiondata1[this.currentmissionindex[i]][5] > -1 && this.missionplan[this.currentmissionindex[i]][1] < this.missiondata1[this.currentmissionindex[i]][6]) {
                        this.missionstate[this.currentmissionindex[i]] = 1;
                        continue block12;
                    }
                    if (this.missionstate[this.currentmissionindex[i]] < 2) {
                        checkshowextraFlag = true;
                        currentmissionindextemp = i;
                    }
                    this.missionplan[this.currentmissionindex[i]][0] = this.missiondata1[this.currentmissionindex[i]][4];
                    this.missionplan[this.currentmissionindex[i]][1] = this.missiondata1[this.currentmissionindex[i]][6];
                    this.missionstate[this.currentmissionindex[i]] = 2;
                    continue block12;
                }
                case 1: {
                    if (this.missionstate[this.currentmissionindex[i]] != 1 || type != this.missiondata1[this.currentmissionindex[i]][2]) continue block12;
                    if (this.missiondata1[this.currentmissionindex[i]][3] > -1 && name == this.missiondata1[this.currentmissionindex[i]][3] && this.missionplan[this.currentmissionindex[i]][0] < this.missiondata1[this.currentmissionindex[i]][4]) {
                        if (name == 6) {
                            if (this.canvas.mapm.mapIndex == this.missiondata1[this.currentmissionindex[i]][7]) {
                                short[] sArray = this.missionplan[this.currentmissionindex[i]];
                                sArray[0] = (short)(sArray[0] + num);
                                checkshowextraFlag = true;
                                currentmissionindextemp = i;
                            }
                        } else {
                            short[] sArray = this.missionplan[this.currentmissionindex[i]];
                            sArray[0] = (short)(sArray[0] + num);
                            checkshowextraFlag = true;
                            currentmissionindextemp = i;
                        }
                    }
                    if (this.missiondata1[this.currentmissionindex[i]][5] > -1 && name == this.missiondata1[this.currentmissionindex[i]][5] && this.missionplan[this.currentmissionindex[i]][1] < this.missiondata1[this.currentmissionindex[i]][6]) {
                        short[] sArray = this.missionplan[this.currentmissionindex[i]];
                        sArray[1] = (short)(sArray[1] + num);
                        checkshowextraFlag = true;
                        currentmissionindextemp = i;
                    }
                    if (this.missiondata1[this.currentmissionindex[i]][3] > -1 && this.missionplan[this.currentmissionindex[i]][0] < this.missiondata1[this.currentmissionindex[i]][4]) {
                        this.missionstate[this.currentmissionindex[i]] = 1;
                        continue block12;
                    }
                    if (this.missiondata1[this.currentmissionindex[i]][5] > -1 && this.missionplan[this.currentmissionindex[i]][1] < this.missiondata1[this.currentmissionindex[i]][6]) {
                        this.missionstate[this.currentmissionindex[i]] = 1;
                        continue block12;
                    }
                    this.missionplan[this.currentmissionindex[i]][0] = this.missiondata1[this.currentmissionindex[i]][4];
                    this.missionplan[this.currentmissionindex[i]][1] = this.missiondata1[this.currentmissionindex[i]][6];
                    checkshowextraFlag = true;
                    currentmissionindextemp = i;
                    this.missionstate[this.currentmissionindex[i]] = 2;
                }
            }
        }
        if (checkshowextraFlag || checkshowflag) {
            if (checkshowextraFlag && checkshowflag) {
                this.showMissionPlan(2, this.currentmissionindex[currentmissionindextemp], 1);
            } else if (!checkshowextraFlag && checkshowflag) {
                this.showMissionPlan(0, this.currentmainmissionindex, 0);
            } else if (checkshowextraFlag && !checkshowflag) {
                this.showMissionPlan(1, this.currentmissionindex[currentmissionindextemp], 1);
            }
        }
    }

    public byte checkbox(byte type, byte index) {
        switch (type) {
            case 0: {
                this.currentreward = this.mainmissionrewarddata[index];
                this.currentrewardtype = this.mainmissionrewardtype[index];
                break;
            }
            case 1: {
                this.currentreward = this.missionrewarddata[index];
                this.currentrewardtype = this.missionrewardtype[index];
            }
        }
        if (this.currentreward != null) {
            block8: for (int i = 0; i < this.currentreward.length; i += 2) {
                switch (this.currentrewardtype[i / 2]) {
                    case 0: {
                        if (this.checkItemBox(this.itemPack, this.currentreward[i], this.currentreward[i + 1])) continue block8;
                        return 1;
                    }
                    case 1: {
                        if (this.checkArmBox(this.currentreward[i])) continue block8;
                        return 2;
                    }
                }
            }
        }
        return 0;
    }

    public void getcurrentreward() {
        if (this.currentreward != null) {
            block6: for (int i = 0; i < this.currentreward.length; i += 2) {
                switch (this.currentrewardtype[i / 2]) {
                    case 0: {
                        this.addItem(this.currentreward[i], this.currentreward[i + 1]);
                        continue block6;
                    }
                    case 1: {
                        byte armType = this.canvas.rolem.setArmType(this.currentreward[i]);
                        if (armType == 2) {
                            this.addArm(this.currentreward[i], (byte)-1, this.currentreward[i + 1]);
                            continue block6;
                        }
                        this.addArm(this.currentreward[i], (byte)-1, (byte)0);
                        continue block6;
                    }
                    case 2: {
                        this.openWeapon(this.currentreward[i]);
                        continue block6;
                    }
                    case 3: {
                        this.canvas.rolem.openride(this.currentreward[i]);
                    }
                }
            }
        }
        this.addMoney(this.currentrewardmoney);
        this.missionrewardstate = (byte)-1;
    }

    public void getcurrentreward(byte[] reward) {
        for (int i = 0; i < reward.length; i += 2) {
            this.addItem(reward[i], reward[i + 1]);
        }
    }

    public void gameTimeProcess() {
        this.gameseconddelay = (byte)(this.gameseconddelay + 1);
        if (this.gameseconddelay == 10) {
            this.gameseconddelay = 0;
            this.gamesecond = (short)(this.gamesecond + 1);
            if (this.gamesecond == 60) {
                this.gamesecond = 0;
                this.gameminute = (short)(this.gameminute + 1);
                if (this.gameminute == 60) {
                    this.gameminute = 0;
                    this.gamehour = (short)(this.gamehour + 1);
                    this.gamehour = (short)(this.gamehour > 999 ? 999 : (int)this.gamehour);
                }
            }
            this.mineTimeProcess();
        }
    }

    public void mineTimeProcess() {
        for (int i = 0; i < 2; i = (int)((byte)(i + 1))) {
            if (this.currentGetMoney[i] != 0 || this.currentMineMaterialName[i] != 0 || this.mineWorkerNum[i] <= 0) continue;
            this.randomMaterial(i);
            int n = i;
            this.minesecond[n] = (short)(this.minesecond[n] + 1);
            if (this.minesecond[n] != 60) continue;
            this.minesecond[i] = 0;
            int n2 = i;
            this.tempGetMoney[n2] = (short)(this.tempGetMoney[n2] + 3 * this.mineWorkerNum[i]);
            int n3 = i;
            this.mineminute[n3] = (short)(this.mineminute[n3] + 1);
            if (this.mineminute[n3] != 10) continue;
            this.currentGetMoney[i] = this.tempGetMoney[i];
            this.tempGetMoney[i] = 0;
            this.currentMineMaterialName[i] = this.tempMineMaterialName[i];
            this.tempMineMaterialName[i] = 0;
            if (this.canvas.sms.minemanager == 1) {
                if (!this.checkItemBox(this.itemPack, this.currentMineMaterialName[i], (byte)1)) {
                    this.showMinePlan(i, this.currentGetMoney[i], this.currentMineMaterialName[i], (byte)1);
                    continue;
                }
                this.showMinePlan(i, this.currentGetMoney[i], this.currentMineMaterialName[i], (byte)0);
                this.addMoney(this.currentGetMoney[i]);
                this.currentGetMoney[i] = 0;
                this.addItem(this.currentMineMaterialName[i], (byte)1);
                this.currentMineMaterialName[i] = 0;
                this.isCanRandom[i] = 0;
                this.mineminute[i] = 0;
                continue;
            }
            this.showMinePlan(i, this.currentGetMoney[i], this.currentMineMaterialName[i], (byte)0);
        }
    }

    public void randomMaterial(int i) {
        if (this.canvas.mapm.cityMapLevel[i * 2] == 0) {
            return;
        }
        if (this.isCanRandom[i] == 1) {
            return;
        }
        int random = Util.getARandomInt(100);
        int rate = 0;
        for (int j = 0; j < this.mineDropData[this.canvas.mapm.cityMapLevel[i * 2] - 1][i].length && this.mineDropData[this.canvas.mapm.cityMapLevel[i * 2] - 1][i][j] > -1; j += 2) {
            if (random >= (rate += this.mineDropData[this.canvas.mapm.cityMapLevel[i * 2] - 1][i][j + 1])) continue;
            this.tempMineMaterialName[i] = this.mineDropData[this.canvas.mapm.cityMapLevel[i * 2] - 1][i][j];
            break;
        }
        this.isCanRandom[i] = 1;
    }

    public void showMinePlan(int cityindex, short money, byte material, byte state) {
        this.mineplantime = (byte)100;
        this.showmineindex = (byte)cityindex;
        this.showminemoney[this.showmineindex] = money;
        this.showminematerial[this.showmineindex] = material;
        this.showminestate[this.showmineindex] = state;
        this.setMinePlanWord(this.showmineindex);
        for (int i = 0; i < this.minePlanScreenData.length; ++i) {
            this.minePlanScreenData[i] = -939524096;
        }
        this.isshowmineplan = true;
    }

    public void drawMinePlan() {
        if (this.canvas.mapm.isTeachPlayering || this.isshowfighttishi) {
            return;
        }
        if (this.isshowmineplan) {
            for (int i = 0; i < 18; ++i) {
                this.canvas.g.drawRGB(this.minePlanScreenData, 0, 640, 0, 64 + i, 640, 1, true);
            }
            this.canvas.drawZkString(this.currentmoveword, this.wordmovex, 65, 20, this.canvas.zkData);
            this.wordmovex -= 4;
            if (this.wordmovex < -this.wordw) {
                this.wordmovex = 640;
                this.isshowmineplan = false;
            }
        }
    }

    private void setMinePlanWord(int index) {
        StringBuffer tempstr = new StringBuffer("$5");
        tempstr.append(this.cityname[index]);
        tempstr.append("$1\u77ff\u5c71\u6316\u6398\u5b8c\u6210\uff0c\u83b7\u5f97 ");
        tempstr.append(this.showminemoney[index]);
        tempstr.append(" \u6c34\u6676\u5e01\uff0c");
        if (this.showminematerial[index] > 0) {
            tempstr.append("\u83b7\u5f97\u6750\u6599");
            tempstr.append("$" + this.getItemLevelColorIndex(this.itemLevel[this.showminematerial[index]]));
            tempstr.append(this.iteminfo[this.showminematerial[index]][0]);
            tempstr.append("$1");
            tempstr.append("x1\uff0c");
        }
        if (this.canvas.sms.minemanager == 1) {
            if (this.showminestate[index] == 1) {
                tempstr.append("\u80cc\u5305\u5df2\u6ee1\uff0c\u8bf7\u6e05\u7406\u5b8c\u80cc\u5305\u4ee5\u540e\u6765\u77ff\u5c71\u9886\u53d6\uff01");
            } else {
                tempstr.append("\u5df2\u81ea\u52a8\u6536\u53d6\uff01");
            }
        } else {
            tempstr.append("\u5feb\u6765\u9886\u53d6\u5427\uff01");
        }
        this.currentmoveword = tempstr.toString();
        this.wordw = this.canvas.getStringWidth(this.currentmoveword);
        this.wordmovex = 640;
    }

    public void setTalk(String word, int state, int faceto) {
        int index = word.indexOf(65306);
        this.talkname = word.substring(0, index);
        this.talkWord = this.canvas.formatzkword(word.substring(index + 1), 520, 0);
        this.talkHeadIndex = this.talkname.equals("\u8bfa\u5170") ? (byte)0 : (this.talkname.equals("\u7f8e\u8fbe") ? (byte)1 : (this.talkname.equals("\u5e0c\u6ce2\u5415\u7279") ? (byte)2 : (this.talkname.equals("\u5c0f\u5c3c\u5e03") ? (byte)3 : (this.talkname.equals("\u65bd\u74e6\u5e03\u516c\u7235") ? (byte)4 : (this.talkname.equals("\u5c3c\u5e03\u4e00\u4e16") ? (byte)5 : (this.talkname.equals("\u5c3c\u5e03") ? (byte)6 : (this.talkname.equals("\u83f2\u7279\u70c8") ? (byte)7 : (byte)-1)))))));
        this.talkStart = 0;
        this.talkstate = (byte)state;
        this.talkFaceTo = (byte)faceto;
        this.talkframetime = 0;
        this.talkWaitTime = 0;
        this.actionFrameIndex = 0;
        this.frameDelay = 0;
        this.isActionOver = false;
        this.settalkwordtotal();
        this.loadHead(this.talkHeadIndex);
    }

    public void loadHead(int index) {
        if (index > -1) {
            this.headImage = Util.createImage("/head/" + index + ".png");
        }
    }

    public void drawHead() {
        if (this.talkHeadIndex > -1) {
            switch (this.talkFaceTo) {
                case 0: {
                    this.canvas.drawImage(this.headImage, 640, this.heady[this.talkHeadIndex] + 40, 40, 0);
                    break;
                }
                case 1: {
                    this.canvas.drawImage(this.headImage, 0, this.heady[this.talkHeadIndex] + 40, 36, 2);
                }
            }
        }
    }

    public void drawTalk() {
        int[] data = new int[]{1, -1};
        this.drawStaticUi((short)31, data[this.talkFaceTo]);
        this.drawHead();
        block0 : switch (this.talkFaceTo) {
            case 0: {
                this.canvas.drawString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 635, 358, 40, 0xFFFFFF);
                this.canvas.drawString(this.talkname, 12, 284, 20, 0);
                this.canvas.drawZkStringArry1(this.talkWord, 10, 306, 20, this.talkStart, 2, 16252891, this.talkWordLen);
                break;
            }
            case 1: {
                this.canvas.drawString(this.talkname, 628, 284, 24, 0);
                switch (this.talkHeadIndex) {
                    case 0: {
                        this.canvas.drawString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 5, 358, 36, 0xFFFFFF);
                        this.canvas.drawZkStringArry1(this.talkWord, 110, 306, 20, this.talkStart, 2, 16252891, this.talkWordLen);
                        break block0;
                    }
                }
                this.canvas.drawString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 635, 358, 40, 0xFFFFFF);
                this.canvas.drawZkStringArry1(this.talkWord, 10, 306, 20, this.talkStart, 2, 16252891, this.talkWordLen);
            }
        }
        this.drawtalkframe();
    }

    private void drawtalkframe() {
        if (this.talkstate == 1) {
            switch (this.talkFaceTo) {
                case 0: {
                    if (this.talkframetime > 5) {
                        this.drawUiModule(209, 5, 295, 33, 0);
                    }
                    if (this.talkframetime > 10) {
                        this.drawUiModule(210, 5, 292, 33, 0);
                    }
                    if (this.talkframetime > 15) {
                        this.drawUiModule(208, 5, 289, 33, 0);
                    }
                    this.drawUiModule(211, 5, 298, 33, 0);
                    if (this.talkframetime >= 30 || ++this.talkframetime != 30) break;
                    this.talkframetime = 0;
                    break;
                }
                case 1: {
                    if (this.talkframetime > 5) {
                        this.drawUiModule(209, 635, 295, 33, 2);
                    }
                    if (this.talkframetime > 10) {
                        this.drawUiModule(210, 635, 292, 33, 2);
                    }
                    if (this.talkframetime > 15) {
                        this.drawUiModule(208, 635, 289, 33, 2);
                    }
                    this.drawUiModule(211, 635, 298, 33, 2);
                    if (this.talkframetime >= 30 || ++this.talkframetime != 30) break;
                    this.talkframetime = 0;
                }
            }
        }
    }

    public void talkKeyProcess() {
        if (Control.IsKeyDown(262176)) {
            if (this.talkWordLen == this.talkWordTotal) {
                this.talkStart = (byte)(this.talkStart + 2);
                if (this.talkStart > this.talkWord.length - 1) {
                    this.headImage = null;
                    this.canvas.changeGameState(this.canvas.lastgamestate);
                } else {
                    this.settalkwordtotal();
                }
            } else {
                this.talkWordLen = this.talkWordTotal;
            }
        }
    }

    public void talkProcess() {
        if (this.talkWordLen < this.talkWordTotal) {
            this.talkWordLen = (byte)(this.talkWordLen + 1);
        }
    }

    public void settalkwordtotal() {
        this.talkWordLen = 0;
        this.talkWordTotal = 0;
        for (int i = this.talkStart; i < this.talkStart + 2; ++i) {
            if (i >= this.talkWord.length) continue;
            this.talkWordTotal = (byte)(this.talkWordTotal + this.canvas.getStringLength(this.talkWord[i]));
        }
    }

    public void setasideword(String word, int index) {
        StringBuffer strb = new StringBuffer("");
        strb.append(word);
        this.asidestate = 0;
        this.asideStart = 0;
        if (index > -1) {
            this.asidestate = 1;
            this.chatuimage = Util.createImage("/ui/a" + index + ".png");
        } else if (index == -1) {
            this.asidestate = 0;
        }
        switch (this.asidestate) {
            case 0: {
                this.asideword = this.canvas.formatzkword(strb.toString(), 300, 0);
                this.asidestarty = (short)160;
                this.asidestarty = (short)(this.asidestarty - (this.asideword.length >> 1) * 20);
                this.asideindex = 1;
                this.asidecounter = 0;
                break;
            }
            case 1: {
                this.asideword = this.canvas.formatzkword(strb.toString(), 140, 0);
                this.asidestarty = (short)120;
                this.asideindex = 1;
                this.asidecounter = 0;
            }
        }
    }

    public void asideKeyProcess() {
        switch (this.asidestate) {
            case 0: {
                if (!Control.IsKeyDown(1)) break;
                if (this.asidecounter == -1) {
                    this.canvas.changeGameState(this.canvas.lastgamestate);
                    break;
                }
                this.asidecounter = (byte)-1;
                this.asideindex = (byte)this.asideword.length;
                break;
            }
            case 1: {
                if (!Control.IsKeyDown(1)) break;
                if (this.asidecounter == -1) {
                    this.asideStart = (byte)(this.asideStart + this.asideindex);
                    if (this.asideStart > this.asideword.length - 1) {
                        this.chatuimage = null;
                        System.gc();
                        this.canvas.changeGameState(this.canvas.lastgamestate);
                        break;
                    }
                    this.asidestarty = (short)120;
                    this.asideindex = 1;
                    this.asidecounter = 0;
                    break;
                }
                this.asidecounter = (byte)-1;
                this.asideindex = (byte)5;
            }
        }
    }

    public void drawaside() {
        switch (this.asidestate) {
            case 0: {
                this.canvas.fillRect(0, 0, 0, 640, 360);
                this.canvas.drawZkStringArry2(this.asideword, 320, this.asidestarty, 17, this.asideStart, this.asideindex, this.asidecounter);
                if (this.asidecounter == -1) {
                    this.canvas.drawString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 320, 355, 33, 0xFFFFFF);
                }
                if (this.asidecounter <= -1 || (this.asidecounter = (byte)(this.asidecounter + 1)) != 10) break;
                this.asidecounter = 0;
                this.asideindex = (byte)(this.asideindex + 1);
                if (this.asideindex <= this.asideword.length) break;
                this.asideindex = (byte)this.asideword.length;
                this.asidecounter = (byte)-1;
                break;
            }
            case 1: {
                this.canvas.fillRect(0, 0, 0, 640, 360);
                this.canvas.drawImage(this.chatuimage, 0, 0, 20, 0);
                this.canvas.drawZkStringArry2(this.asideword, 560, this.asidestarty, 17, this.asideStart, this.asideindex, this.asidecounter);
                if (this.asidecounter == -1) {
                    this.canvas.drawString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 320, 355, 33, 0xFFFFFF);
                }
                if (this.asidecounter <= -1 || (this.asidecounter = (byte)(this.asidecounter + 1)) != 10) break;
                this.asidecounter = 0;
                this.asideindex = (byte)(this.asideindex + 1);
                if (this.asideindex <= 5) break;
                this.asideindex = (byte)5;
                this.asidecounter = (byte)-1;
            }
        }
    }

    public void setIntro(String word, byte headIndex, byte type) {
        this.introtype = type;
        switch (this.introtype) {
            case 0: {
                this.introWord = this.canvas.formatzkword(word, 300, 0);
                this.introHeadIndex = headIndex;
                this.loadIntroHead(this.introHeadIndex);
                break;
            }
            case 1: {
                this.introWord = this.canvas.formatzkword(this.rideinfo[headIndex], 300, 0);
                this.initplayeraction();
            }
        }
        this.introStart = 0;
        this.initUiScreenData();
        this.setintrowordtotal();
    }

    public void loadIntroHead(int index) {
        if (index > -1) {
            this.introImage = Util.createImage("/head/" + index + ".png");
        }
    }

    public void setintrowordtotal() {
        this.introWordLen = 0;
        this.introWordTotal = 0;
        for (int i = this.introStart; i < this.introStart + 6; ++i) {
            if (i >= this.introWord.length) continue;
            this.introWordTotal = (byte)(this.introWordTotal + this.canvas.getStringLength(this.introWord[i]));
        }
    }

    public void drawIntro() {
        this.drawUiBack();
        this.canvas.drawString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 320, 355, 33, 0xFFFFFF);
        switch (this.introtype) {
            case 0: {
                this.drawIntroHead();
                break;
            }
            case 1: {
                this.drawplayer((byte)1);
            }
        }
        this.canvas.drawZkStringArry1(this.introWord, 320, 250, 17, this.introStart, 6, 16252891, this.introWordLen);
    }

    public void drawIntroHead() {
        if (this.introHeadIndex > -1) {
            this.canvas.drawImage(this.introImage, 320, 240, 33, 0);
        }
    }

    public void introKeyProcess() {
        if (Control.IsKeyDown(262176)) {
            if (this.introWordLen == this.introWordTotal) {
                this.introStart = (byte)(this.introStart + 6);
                if (this.introStart > this.introWord.length - 1) {
                    this.introImage = null;
                    this.canvas.changeGameState(this.canvas.lastgamestate);
                } else {
                    this.setintrowordtotal();
                }
            } else {
                this.introWordLen = this.introWordTotal;
            }
        }
    }

    public void introProcess() {
        if (this.introWordLen < this.introWordTotal) {
            this.introWordLen = (byte)(this.introWordLen + 1);
        }
    }

    public void drawfighttishiword() {
        if (this.isshowfighttishi) {
            switch (this.drawtishistate) {
                case 0: {
                    switch (this.keynum) {
                        case 128: {
                            this.canvas.drawTishiWord("$1\u8bf7\u6309\u4f4f$2\u56fe\u6807 7 $1", 320, 65);
                            break;
                        }
                        case 512: {
                            this.canvas.drawTishiWord("$1\u8bf7\u6309\u4f4f$2\u56fe\u6807 9 $1", 320, 65);
                        }
                    }
                    break;
                }
                case 1: {
                    this.canvas.drawTishiWord("$1\u7528$2\u62f3\u5957$1\u653b\u51fb\u53ef\u6253\u65ad\u6280\u80fd", 320, 65);
                    break;
                }
                case 2: {
                    this.canvas.drawTishiWord("$1\u7528$2\u62f3\u5957$1\u653b\u51fb\u66f4\u6709\u6548\u679c", 320, 65);
                    break;
                }
                case 3: {
                    this.canvas.drawTishiWord("$1\u6253\u65ad$2\u89e6\u987b$1\u53ef\u4f7f\u5de8\u5996\u8fdb\u5165\u865a\u5f31\u72b6\u6001", 320, 65);
                    break;
                }
                case 4: {
                    this.canvas.drawTishiWord("$2\u5de8\u5996$1\u5904\u4e8e\u865a\u5f31\u72b6\u6001\uff0c\u8bf7\u6293\u7d27\u65f6\u95f4\u653b\u51fb", 320, 65);
                }
            }
        }
    }

    public void setfighttishiword(byte state, int key) {
        this.drawtishistate = state;
        this.keynum = key;
        this.isshowfighttishi = true;
    }

    public void finishfighttishiword() {
        this.drawtishistate = 0;
        this.keynum = 0;
        this.isshowfighttishi = false;
    }

    public void saveGame() {
        this.canvas.rms.openRMS();
        this.canvas.newRMS();
        this.saveItemPackData();
        this.saveArmBoxData();
        this.saveMapData();
        this.saveMissionData();
        this.saveOtherData();
        this.savePlayerPos();
        this.saveMoney();
        this.saveMine();
        this.canvas.sms.savedata();
        this.savePlayerData();
        this.canvas.rms.closeRMS();
    }

    public void savesmsmoney() {
        this.canvas.rms.openRMS();
        this.canvas.newRMS();
        this.saveMoney();
        this.canvas.rms.closeRMS();
    }

    public void savesmsplayerdata() {
        this.canvas.rms.openRMS();
        this.canvas.newRMS();
        this.savePlayerData();
        this.canvas.rms.closeRMS();
    }

    public void loadGame() {
        this.canvas.rms.openRMS();
        this.loadItemPackData();
        this.loadArmBoxData();
        this.loadMapData();
        this.loadMissionData();
        this.loadOtherData();
        this.loadPlayerPos();
        this.loadMoney();
        this.loadMine();
        this.canvas.sms.loaddata();
        this.loadPlayerData();
        this.canvas.rms.closeRMS();
    }

    public void savetempplayerdata() {
        this.playertempdata = new int[13];
        this.playertempdata[0] = this.canvas.rolem.playerLevel;
        this.playertempdata[1] = this.canvas.rolem.playerCurrentExp;
        this.playertempdata[2] = this.canvas.rolem.playerNeedExp;
        this.playertempdata[3] = this.canvas.rolem.playerCurrentSkillPoint;
        this.playertempdata[4] = this.canvas.rolem.fplayerbox[0].baseLife[0];
        this.playertempdata[5] = this.canvas.rolem.fplayerbox[0].baseMagic[0];
        this.playertempdata[6] = this.canvas.rolem.fplayerbox[0].life[0];
        this.playertempdata[7] = this.canvas.rolem.fplayerbox[0].magic[0];
        this.playertempdata[8] = this.canvas.rolem.fplayerbox[0].baseAgility[0];
        this.playertempdata[9] = this.canvas.rolem.fplayerbox[0].baseDefence[0];
        this.playertempdata[10] = this.canvas.rolem.fplayerbox[0].baseAttack[0];
        this.playertempdata[11] = this.canvas.rolem.fplayerbox[0].basebaoji[0];
        this.playertempdata[12] = this.canvas.rolem.playerUseedSkillPoint;
        this.currentItemBox = this.copydata(this.itemPack);
        this.currentWeaponPack = this.copydata(this.weaponPack);
        this.currentSuitPack = this.copydata(this.suitPack);
        this.currentPlusPack = this.copydata(this.plusPack);
        this.tempPlayerCurrentWeapon = this.copydata(this.canvas.rolem.playerCurrentWeapon);
        this.tempPlayerCurrentWeaponType = this.canvas.rolem.playerCurrentWeaponType;
        this.tempPlayerCurrentSuit = this.canvas.rolem.playerCurrentSuit;
        this.tempPlayerCurrentPlus = this.copydata(this.canvas.rolem.playerCurrentPlus);
        this.tempPlayerCurrentWeaponAttack = this.copydata(this.canvas.rolem.playerCurrentWeaponAttack);
        this.tempPlayerCurrentWeaponBaojiOdds = this.copydata(this.canvas.rolem.playerCurrentWeaponBaojiOdds);
        this.tempPlayerCurrentWeaponBaojiAttack = this.copydata(this.canvas.rolem.playerCurrentWeaponBaojiAttack);
        this.tempSkillState = this.copydata(this.canvas.rolem.skillState);
        this.tempweaponCriticalKey7 = this.copydata(this.canvas.rolem.weaponCriticalKey7);
        this.tempweaponCriticalKey9 = this.copydata(this.canvas.rolem.weaponCriticalKey9);
        this.tempweaponActionIndex = this.copydata(this.canvas.rolem.weaponActionIndex);
        this.tempmoney = this.playerMoney;
        this.temprideindex = this.canvas.rolem.currentRideIndex;
        this.temprolestate = this.canvas.rolem.roleState;
        if (this.canvas.rolem.roleState > 0) {
            this.canvas.rolem.setPlayerState((byte)0);
            this.canvas.rolem.setPlayerRideDown();
        }
        this.changeRide((byte)0);
        this.tempridelife = this.copydata(this.canvas.rolem.rideLife);
    }

    public void loadtempplayerdata() {
        int j;
        int i;
        this.canvas.rolem.playerLevel = (byte)this.playertempdata[0];
        this.canvas.rolem.playerCurrentExp = this.playertempdata[1];
        this.canvas.rolem.playerNeedExp = this.playertempdata[2];
        this.canvas.rolem.playerCurrentSkillPoint = this.playertempdata[3];
        this.canvas.rolem.fplayerbox[0].baseLife[0] = this.playertempdata[4];
        this.canvas.rolem.fplayerbox[0].baseMagic[0] = this.playertempdata[5];
        this.canvas.rolem.fplayerbox[0].life[0] = this.playertempdata[6];
        this.canvas.rolem.fplayerbox[0].magic[0] = this.playertempdata[7];
        this.canvas.rolem.fplayerbox[0].baseAgility[0] = this.playertempdata[8];
        this.canvas.rolem.fplayerbox[0].baseDefence[0] = this.playertempdata[9];
        this.canvas.rolem.fplayerbox[0].baseAttack[0] = this.playertempdata[10];
        this.canvas.rolem.fplayerbox[0].basebaoji[0] = this.playertempdata[11];
        this.canvas.rolem.playerUseedSkillPoint = (byte)this.playertempdata[12];
        this.canvas.rolem.fplayerbox[0].agility[0] = this.canvas.rolem.fplayerbox[0].baseAgility[0];
        this.canvas.rolem.fplayerbox[0].defence[0] = this.canvas.rolem.fplayerbox[0].baseDefence[0];
        this.canvas.rolem.fplayerbox[0].attack[0] = this.canvas.rolem.fplayerbox[0].baseAttack[0];
        this.canvas.rolem.fplayerbox[0].baoji[0] = this.canvas.rolem.fplayerbox[0].basebaoji[0];
        this.itemPack = this.copydata(this.currentItemBox);
        this.itemPackUseNum = 0;
        for (i = 0; i < this.itemPack.length; ++i) {
            for (j = 0; j < this.itemPack[i].length; ++j) {
                if (this.itemPack[0][j] <= -1 || i != 0) continue;
                byte by = this.itemPack[0][j];
                this.itemTotal[by] = (short)(this.itemTotal[by] + this.itemPack[1][j]);
                this.itemPackUseNum = (byte)(this.itemPackUseNum + 1);
            }
        }
        this.weaponPack = this.copydata(this.currentWeaponPack);
        this.suitPack = this.copydata(this.currentSuitPack);
        this.plusPack = this.copydata(this.currentPlusPack);
        this.weaponPackUseNum = 0;
        this.suitPackUseNum = 0;
        this.plusPackUseNum = 0;
        for (i = 0; i < this.weaponPack.length; ++i) {
            for (j = 0; j < this.weaponPack[i].length; ++j) {
                if (this.weaponPack[0][j] <= -1 || i != 0) continue;
                byte by = this.weaponPack[0][j];
                this.itemTotal[by] = (short)(this.itemTotal[by] + 1);
                this.weaponPackUseNum = (byte)(this.weaponPackUseNum + 1);
            }
        }
        for (i = 0; i < this.suitPack.length; ++i) {
            for (j = 0; j < this.suitPack[i].length; ++j) {
                if (this.suitPack[0][j] <= -1 || i != 0) continue;
                byte by = this.suitPack[0][j];
                this.itemTotal[by] = (short)(this.itemTotal[by] + 1);
                this.suitPackUseNum = (byte)(this.suitPackUseNum + 1);
            }
        }
        for (i = 0; i < this.plusPack.length; ++i) {
            for (j = 0; j < this.plusPack[i].length; ++j) {
                if (this.plusPack[0][j] <= -1 || i != 0) continue;
                byte by = this.plusPack[0][j];
                this.itemTotal[by] = (short)(this.itemTotal[by] + 1);
                this.plusPackUseNum = (byte)(this.plusPackUseNum + 1);
            }
        }
        this.canvas.rolem.playerCurrentWeapon = this.copydata(this.tempPlayerCurrentWeapon);
        this.canvas.rolem.playerCurrentWeaponType = this.tempPlayerCurrentWeaponType;
        this.canvas.rolem.playerCurrentSuit = this.tempPlayerCurrentSuit;
        this.canvas.rolem.playerCurrentPlus = this.copydata(this.tempPlayerCurrentPlus);
        this.canvas.rolem.playerCurrentWeaponAttack = this.copydata(this.tempPlayerCurrentWeaponAttack);
        this.canvas.rolem.playerCurrentWeaponBaojiOdds = this.copydata(this.tempPlayerCurrentWeaponBaojiOdds);
        this.canvas.rolem.playerCurrentWeaponBaojiAttack = this.copydata(this.tempPlayerCurrentWeaponBaojiAttack);
        this.canvas.rolem.skillState = this.copydata(this.tempSkillState);
        this.canvas.rolem.weaponCriticalKey7 = this.copydata(this.tempweaponCriticalKey7);
        this.canvas.rolem.weaponCriticalKey9 = this.copydata(this.tempweaponCriticalKey9);
        this.canvas.rolem.weaponActionIndex = this.copydata(this.tempweaponActionIndex);
        this.playerMoney = this.tempmoney;
        this.canvas.rolem.currentRideIndex = this.temprideindex;
        this.canvas.rolem.roleState = this.temprolestate;
        this.changeRide(this.canvas.rolem.currentRideIndex);
        this.canvas.rolem.rideLife = this.copydata(this.tempridelife);
    }

    public void playerimage() {
        this.changeWeaponTime = 0;
        while (this.changeWeaponTime < 3) {
            this.canvas.gameUi.changeWeaponImage(this.canvas.rolem.playerCurrentWeapon[0]);
        }
        this.changeWeaponTime = 0;
        while (this.changeWeaponTime < 3) {
            this.canvas.gameUi.changeWeaponImage(this.canvas.rolem.playerCurrentWeapon[1]);
        }
        this.changeWeaponTime = 0;
        while (this.changeWeaponTime < 3) {
            this.canvas.gameUi.changeWeaponImage(this.canvas.rolem.playerCurrentWeapon[2]);
        }
        if (this.canvas.rolem.currentRideIndex > -1) {
            this.canvas.rolem.initRide(this.canvas.rolem.currentRideIndex);
        }
    }

    public void changeride() {
        this.changeWeaponTime = 0;
        while (this.changeWeaponTime < 3) {
            switch (this.changeWeaponTime) {
                case 0: {
                    this.canvas.rolem.clearRide(this.canvas.rolem.lastRideIndex);
                    break;
                }
                case 1: {
                    this.canvas.rolem.initRide(this.canvas.rolem.currentRideIndex);
                    break;
                }
                case 2: {
                    System.gc();
                }
            }
            ++this.changeWeaponTime;
        }
        if (this.canvas.rolem.roleState > 0 && this.canvas.rolem.rideLife[this.canvas.rolem.roleState - 1] > 0) {
            this.canvas.rolem.setPlayerRideOn();
        }
    }

    public void saveItemPackData() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeByte(this.itemPackNum);
            for (int i = 0; i < this.itemPack.length; ++i) {
                for (int j = 0; j < this.itemPack[i].length; ++j) {
                    dos.writeByte(this.itemPack[i][j]);
                }
            }
            this.canvas.setRecord(bos.toByteArray(), 1);
            dos.close();
            bos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadItemPackData() {
        byte[] data = this.canvas.getRecord(1);
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);
        try {
            this.itemPackNum = dis.readByte();
            this.initarmpack();
            for (int i = 0; i < this.itemPack.length; ++i) {
                for (int j = 0; j < this.itemPack[i].length; ++j) {
                    this.itemPack[i][j] = dis.readByte();
                    if (this.itemPack[0][j] <= -1 || i != 1) continue;
                    byte by = this.itemPack[0][j];
                    this.itemTotal[by] = (short)(this.itemTotal[by] + this.itemPack[1][j]);
                    this.itemPackUseNum = (byte)(this.itemPackUseNum + 1);
                }
            }
            dis.close();
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveArmBoxData() {
        try {
            int j;
            int i;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            for (i = 0; i < this.weaponPack.length; ++i) {
                for (j = 0; j < this.weaponPack[i].length; ++j) {
                    dos.writeByte(this.weaponPack[i][j]);
                }
            }
            for (i = 0; i < this.suitPack.length; ++i) {
                for (j = 0; j < this.suitPack[i].length; ++j) {
                    dos.writeByte(this.suitPack[i][j]);
                }
            }
            for (i = 0; i < this.plusPack.length; ++i) {
                for (j = 0; j < this.plusPack[i].length; ++j) {
                    dos.writeByte(this.plusPack[i][j]);
                }
            }
            this.canvas.setRecord(bos.toByteArray(), 2);
            dos.close();
            bos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadArmBoxData() {
        byte[] data = this.canvas.getRecord(2);
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);
        try {
            int j;
            int i;
            for (i = 0; i < this.weaponPack.length; ++i) {
                for (j = 0; j < this.weaponPack[i].length; ++j) {
                    this.weaponPack[i][j] = dis.readByte();
                    if (this.weaponPack[0][j] <= -1 || i != 0) continue;
                    byte by = this.weaponPack[0][j];
                    this.itemTotal[by] = (short)(this.itemTotal[by] + 1);
                    this.weaponPackUseNum = (byte)(this.weaponPackUseNum + 1);
                }
            }
            for (i = 0; i < this.suitPack.length; ++i) {
                for (j = 0; j < this.suitPack[i].length; ++j) {
                    this.suitPack[i][j] = dis.readByte();
                    if (this.suitPack[0][j] <= -1 || i != 0) continue;
                    byte by = this.suitPack[0][j];
                    this.itemTotal[by] = (short)(this.itemTotal[by] + 1);
                    this.suitPackUseNum = (byte)(this.suitPackUseNum + 1);
                }
            }
            for (i = 0; i < this.plusPack.length; ++i) {
                for (j = 0; j < this.plusPack[i].length; ++j) {
                    this.plusPack[i][j] = dis.readByte();
                    if (this.plusPack[0][j] <= -1 || i != 0) continue;
                    byte by = this.plusPack[0][j];
                    this.itemTotal[by] = (short)(this.itemTotal[by] + 1);
                    this.plusPackUseNum = (byte)(this.plusPackUseNum + 1);
                }
            }
            dis.close();
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveMapData() {
        try {
            int i;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            for (i = 0; i < this.canvas.mapm.eventState.length; ++i) {
                dos.writeShort(this.canvas.mapm.eventState[i]);
            }
            dos.writeShort(this.canvas.mapm.teachstate);
            for (i = 0; i < this.canvas.mapm.cityMapLevel.length; ++i) {
                dos.writeByte(this.canvas.mapm.cityMapLevel[i]);
            }
            dos.writeInt(this.canvas.mapm.areaopenstate);
            dos.writeShort(this.canvas.mapm.nextMapIndex);
            for (i = 0; i < this.canvas.rolem.mapenemylistindex.length; ++i) {
                dos.writeByte(this.canvas.rolem.mapenemylistindex[i]);
            }
            this.canvas.setRecord(bos.toByteArray(), 3);
            dos.close();
            bos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMapData() {
        byte[] data = this.canvas.getRecord(3);
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);
        try {
            int i;
            for (i = 0; i < this.canvas.mapm.eventState.length; ++i) {
                this.canvas.mapm.eventState[i] = dis.readShort();
            }
            this.canvas.mapm.teachstate = dis.readShort();
            for (i = 0; i < this.canvas.mapm.cityMapLevel.length; ++i) {
                this.canvas.mapm.cityMapLevel[i] = dis.readByte();
            }
            this.canvas.mapm.areaopenstate = dis.readInt();
            this.canvas.mapm.nextMapIndex = dis.readShort();
            for (i = 0; i < this.canvas.rolem.mapenemylistindex.length; ++i) {
                this.canvas.rolem.mapenemylistindex[i] = dis.readByte();
            }
            dis.close();
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveMissionData() {
        try {
            int i;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            for (i = 0; i < this.currentmissionindex.length; ++i) {
                dos.writeByte(this.currentmissionindex[i]);
            }
            for (i = 0; i < this.missionstate.length; ++i) {
                dos.writeByte(this.missionstate[i]);
            }
            for (i = 0; i < this.missionplan.length; ++i) {
                for (int j = 0; j < this.missionplan[i].length; ++j) {
                    dos.writeShort(this.missionplan[i][j]);
                }
            }
            dos.writeByte(this.currentmainmissionindex);
            dos.writeByte(this.mainmissionstate);
            for (i = 0; i < this.mainmissionplan.length; ++i) {
                dos.writeShort(this.mainmissionplan[i]);
            }
            dos.writeByte(this.canvas.mapm.autoeventstate);
            this.canvas.setRecord(bos.toByteArray(), 4);
            dos.close();
            bos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMissionData() {
        byte[] data = this.canvas.getRecord(4);
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);
        try {
            int i;
            for (i = 0; i < this.currentmissionindex.length; ++i) {
                this.currentmissionindex[i] = dis.readByte();
            }
            for (i = 0; i < this.missionstate.length; ++i) {
                this.missionstate[i] = dis.readByte();
            }
            for (i = 0; i < this.missionplan.length; ++i) {
                for (int j = 0; j < this.missionplan[i].length; ++j) {
                    this.missionplan[i][j] = dis.readShort();
                }
            }
            this.currentmainmissionindex = dis.readByte();
            this.mainmissionstate = dis.readByte();
            for (i = 0; i < this.mainmissionplan.length; ++i) {
                this.mainmissionplan[i] = dis.readShort();
            }
            this.canvas.mapm.autoeventstate = dis.readByte();
            dis.close();
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveOtherData() {
        try {
            int j;
            int i;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeByte(this.canvas.rolem.playerLevel);
            dos.writeInt(this.canvas.rolem.playerCurrentExp);
            dos.writeInt(this.canvas.rolem.playerNeedExp);
            dos.writeInt(this.canvas.rolem.fplayerbox[0].baseLife[0]);
            dos.writeInt(this.canvas.rolem.fplayerbox[0].baseMagic[0]);
            dos.writeInt(this.canvas.rolem.fplayerbox[0].baseAgility[0]);
            dos.writeInt(this.canvas.rolem.fplayerbox[0].baseDefence[0]);
            dos.writeInt(this.canvas.rolem.fplayerbox[0].baseAttack[0]);
            dos.writeInt(this.canvas.rolem.fplayerbox[0].basebaoji[0]);
            dos.writeInt(this.canvas.rolem.fplayerbox[0].life[0]);
            dos.writeInt(this.canvas.rolem.fplayerbox[0].magic[0]);
            for (i = 0; i < this.forgeState.length; ++i) {
                dos.writeByte(this.forgeState[i]);
            }
            for (i = 0; i < this.canvas.rolem.playerCurrentWeapon.length; ++i) {
                dos.writeByte(this.canvas.rolem.playerCurrentWeapon[i]);
            }
            for (i = 0; i < this.canvas.rolem.playerCurrentPlus.length; ++i) {
                dos.writeByte(this.canvas.rolem.playerCurrentPlus[i]);
            }
            for (i = 0; i < this.canvas.rolem.plusState.length; ++i) {
                dos.writeShort(this.canvas.rolem.plusState[i]);
            }
            for (i = 0; i < this.canvas.rolem.plusData.length; ++i) {
                dos.writeShort(this.canvas.rolem.plusData[i]);
            }
            dos.writeByte(this.canvas.rolem.playerCurrentSuit);
            for (i = 0; i < this.canvas.rolem.playerCurrentWeaponAttack.length; ++i) {
                dos.writeShort(this.canvas.rolem.playerCurrentWeaponAttack[i]);
            }
            for (i = 0; i < this.canvas.rolem.playerCurrentWeaponBaojiOdds.length; ++i) {
                dos.writeShort(this.canvas.rolem.playerCurrentWeaponBaojiOdds[i]);
            }
            for (i = 0; i < this.canvas.rolem.playerCurrentWeaponBaojiAttack.length; ++i) {
                dos.writeShort(this.canvas.rolem.playerCurrentWeaponBaojiAttack[i]);
            }
            for (i = 0; i < this.canvas.rolem.skillState.length; ++i) {
                for (j = 0; j < this.canvas.rolem.skillState[i].length; ++j) {
                    dos.writeByte(this.canvas.rolem.skillState[i][j]);
                }
            }
            for (i = 0; i < this.canvas.rolem.weaponCriticalKey7.length; ++i) {
                for (j = 0; j < this.canvas.rolem.weaponCriticalKey7[i].length; ++j) {
                    dos.writeShort(this.canvas.rolem.weaponCriticalKey7[i][j]);
                }
            }
            for (i = 0; i < this.canvas.rolem.weaponCriticalKey9.length; ++i) {
                for (j = 0; j < this.canvas.rolem.weaponCriticalKey9[i].length; ++j) {
                    dos.writeShort(this.canvas.rolem.weaponCriticalKey9[i][j]);
                }
            }
            for (i = 0; i < this.canvas.rolem.weaponActionIndex.length; ++i) {
                dos.writeInt(this.canvas.rolem.weaponActionIndex[i]);
            }
            dos.writeByte(this.canvas.rolem.rideState);
            dos.writeByte(this.canvas.rolem.currentRideIndex);
            for (i = 0; i < 3; ++i) {
                dos.writeShort(this.canvas.rolem.rideLife[i]);
            }
            for (i = 0; i < 3; ++i) {
                dos.writeShort(this.addforeverdata[i]);
            }
            dos.writeShort(this.gamehour);
            dos.writeShort(this.gameminute);
            dos.writeShort(this.gamesecond);
            this.canvas.setRecord(bos.toByteArray(), 5);
            dos.close();
            bos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadOtherData() {
        byte[] data = this.canvas.getRecord(5);
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);
        try {
            int j;
            int i;
            this.canvas.rolem.fplayerbox[0].level = this.canvas.rolem.playerLevel = dis.readByte();
            this.canvas.rolem.playerCurrentExp = dis.readInt();
            this.canvas.rolem.playerNeedExp = dis.readInt();
            this.canvas.rolem.fplayerbox[0].baseLife[0] = dis.readInt();
            this.canvas.rolem.fplayerbox[0].baseMagic[0] = dis.readInt();
            this.canvas.rolem.fplayerbox[0].baseAgility[0] = dis.readInt();
            this.canvas.rolem.fplayerbox[0].baseDefence[0] = dis.readInt();
            this.canvas.rolem.fplayerbox[0].baseAttack[0] = dis.readInt();
            this.canvas.rolem.fplayerbox[0].basebaoji[0] = dis.readInt();
            this.canvas.rolem.fplayerbox[0].life[0] = dis.readInt();
            this.canvas.rolem.fplayerbox[0].magic[0] = dis.readInt();
            this.canvas.rolem.fplayerbox[0].attack[0] = this.canvas.rolem.fplayerbox[0].baseAttack[0];
            this.canvas.rolem.fplayerbox[0].defence[0] = this.canvas.rolem.fplayerbox[0].baseDefence[0];
            this.canvas.rolem.fplayerbox[0].baoji[0] = this.canvas.rolem.fplayerbox[0].basebaoji[0];
            for (i = 0; i < this.forgeState.length; ++i) {
                this.forgeState[i] = dis.readByte();
            }
            for (i = 0; i < this.canvas.rolem.playerCurrentWeapon.length; ++i) {
                this.canvas.rolem.playerCurrentWeapon[i] = dis.readByte();
            }
            for (i = 0; i < this.canvas.rolem.playerCurrentPlus.length; ++i) {
                this.canvas.rolem.playerCurrentPlus[i] = dis.readByte();
            }
            for (i = 0; i < this.canvas.rolem.plusState.length; ++i) {
                this.canvas.rolem.plusState[i] = dis.readShort();
            }
            for (i = 0; i < this.canvas.rolem.plusData.length; ++i) {
                this.canvas.rolem.plusData[i] = dis.readShort();
            }
            this.canvas.rolem.playerCurrentSuit = dis.readByte();
            for (i = 0; i < this.canvas.rolem.playerCurrentWeaponAttack.length; ++i) {
                this.canvas.rolem.playerCurrentWeaponAttack[i] = dis.readShort();
            }
            for (i = 0; i < this.canvas.rolem.playerCurrentWeaponBaojiOdds.length; ++i) {
                this.canvas.rolem.playerCurrentWeaponBaojiOdds[i] = dis.readShort();
            }
            for (i = 0; i < this.canvas.rolem.playerCurrentWeaponBaojiAttack.length; ++i) {
                this.canvas.rolem.playerCurrentWeaponBaojiAttack[i] = dis.readShort();
            }
            for (i = 0; i < this.canvas.rolem.skillState.length; ++i) {
                for (j = 0; j < this.canvas.rolem.skillState[i].length; ++j) {
                    this.canvas.rolem.skillState[i][j] = dis.readByte();
                }
            }
            for (i = 0; i < this.canvas.rolem.weaponCriticalKey7.length; ++i) {
                for (j = 0; j < this.canvas.rolem.weaponCriticalKey7[i].length; ++j) {
                    this.canvas.rolem.weaponCriticalKey7[i][j] = dis.readShort();
                }
            }
            for (i = 0; i < this.canvas.rolem.weaponCriticalKey9.length; ++i) {
                for (j = 0; j < this.canvas.rolem.weaponCriticalKey9[i].length; ++j) {
                    this.canvas.rolem.weaponCriticalKey9[i][j] = dis.readShort();
                }
            }
            for (i = 0; i < this.canvas.rolem.weaponActionIndex.length; ++i) {
                this.canvas.rolem.weaponActionIndex[i] = dis.readInt();
            }
            this.canvas.rolem.rideState = dis.readByte();
            this.canvas.rolem.currentRideIndex = dis.readByte();
            for (i = 0; i < 3; ++i) {
                this.canvas.rolem.rideLife[i] = dis.readShort();
            }
            for (i = 0; i < 3; ++i) {
                this.addforeverdata[i] = dis.readShort();
            }
            this.gamehour = dis.readShort();
            this.gameminute = dis.readShort();
            this.gamesecond = dis.readShort();
            dis.close();
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savePlayerPos() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeByte(this.savegamestate);
            dos.writeByte(this.canvas.rolem.currentplayernameindex);
            switch (this.savegamestate) {
                case 5: {
                    dos.writeShort(this.canvas.rolem.fplayerbox[0].spritex);
                    dos.writeShort(this.canvas.rolem.fplayerbox[0].spritez);
                    dos.writeByte(this.canvas.rolem.fplayerbox[0].faceto);
                    break;
                }
                case 4: {
                    dos.writeByte(this.savecityindex);
                }
            }
            this.canvas.setRecord(bos.toByteArray(), 6);
            dos.close();
            bos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPlayerPos() {
        byte[] data = this.canvas.getRecord(6);
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);
        try {
            this.savegamestate = dis.readByte();
            this.canvas.rolem.lastplayernameindex = this.canvas.rolem.currentplayernameindex = dis.readByte();
            switch (this.savegamestate) {
                case 5: {
                    this.canvas.rolem.fplayerbox[0].spritex = dis.readShort();
                    this.canvas.rolem.fplayerbox[0].spritez = dis.readShort();
                    this.canvas.rolem.fplayerbox[0].faceto = dis.readByte();
                    break;
                }
                case 4: {
                    this.canvas.mapm.cityMapIndex = this.savecityindex = dis.readByte();
                    this.canvas.mapm.citybuildnum = this.canvas.mapm.citybuildpos[this.canvas.mapm.cityMapIndex].length;
                }
            }
            dis.close();
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveMoney() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeInt(this.playerMoney);
            this.canvas.setRecord(bos.toByteArray(), 7);
            dos.close();
            bos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMoney() {
        byte[] data = this.canvas.getRecord(7);
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);
        try {
            this.playerMoney = dis.readInt();
            dis.close();
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveMine() {
        try {
            int i;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            for (i = 0; i < this.mineminute.length; ++i) {
                dos.writeShort(this.mineminute[i]);
            }
            for (i = 0; i < this.minesecond.length; ++i) {
                dos.writeShort(this.minesecond[i]);
            }
            for (i = 0; i < this.mineWorkerNum.length; ++i) {
                dos.writeByte(this.mineWorkerNum[i]);
            }
            for (i = 0; i < this.tempGetMoney.length; ++i) {
                dos.writeShort(this.tempGetMoney[i]);
            }
            for (i = 0; i < this.currentGetMoney.length; ++i) {
                dos.writeShort(this.currentGetMoney[i]);
            }
            for (i = 0; i < this.tempMineMaterialName.length; ++i) {
                dos.writeByte(this.tempMineMaterialName[i]);
            }
            for (i = 0; i < this.currentMineMaterialName.length; ++i) {
                dos.writeByte(this.currentMineMaterialName[i]);
            }
            for (i = 0; i < this.isCanRandom.length; ++i) {
                dos.writeByte(this.isCanRandom[i]);
            }
            for (i = 0; i < this.isshowcitynews.length; ++i) {
                dos.writeByte(this.isshowcitynews[i]);
            }
            for (i = 0; i < this.isshowforgenews.length; ++i) {
                dos.writeByte(this.isshowforgenews[i]);
            }
            for (i = 0; i < this.isshowforgearmnews.length; ++i) {
                for (int j = 0; j < this.isshowforgearmnews[i].length; ++j) {
                    dos.writeShort(this.isshowforgearmnews[i][j]);
                }
            }
            for (i = 0; i < this.isshowsalematerialnews.length; ++i) {
                dos.writeInt(this.isshowsalematerialnews[i]);
            }
            dos.writeShort(this.isshowmissionnews);
            this.canvas.setRecord(bos.toByteArray(), 8);
            dos.close();
            bos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMine() {
        byte[] data = this.canvas.getRecord(8);
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);
        try {
            int i;
            for (i = 0; i < this.mineminute.length; ++i) {
                this.mineminute[i] = dis.readShort();
            }
            for (i = 0; i < this.minesecond.length; ++i) {
                this.minesecond[i] = dis.readShort();
            }
            for (i = 0; i < this.mineWorkerNum.length; ++i) {
                this.mineWorkerNum[i] = dis.readByte();
            }
            for (i = 0; i < this.tempGetMoney.length; ++i) {
                this.tempGetMoney[i] = dis.readShort();
            }
            for (i = 0; i < this.currentGetMoney.length; ++i) {
                this.currentGetMoney[i] = dis.readShort();
            }
            for (i = 0; i < this.tempMineMaterialName.length; ++i) {
                this.tempMineMaterialName[i] = dis.readByte();
            }
            for (i = 0; i < this.currentMineMaterialName.length; ++i) {
                this.currentMineMaterialName[i] = dis.readByte();
            }
            for (i = 0; i < this.isCanRandom.length; ++i) {
                this.isCanRandom[i] = dis.readByte();
            }
            for (i = 0; i < this.isshowcitynews.length; ++i) {
                this.isshowcitynews[i] = dis.readByte();
            }
            for (i = 0; i < this.isshowforgenews.length; ++i) {
                this.isshowforgenews[i] = dis.readByte();
            }
            for (i = 0; i < this.isshowforgearmnews.length; ++i) {
                for (int j = 0; j < this.isshowforgearmnews[i].length; ++j) {
                    this.isshowforgearmnews[i][j] = dis.readShort();
                }
            }
            for (i = 0; i < this.isshowsalematerialnews.length; ++i) {
                this.isshowsalematerialnews[i] = dis.readInt();
            }
            this.isshowmissionnews = dis.readShort();
            dis.close();
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savePlayerData() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeByte(this.canvas.rolem.playerLevel);
            dos.writeInt(this.canvas.rolem.playerCurrentExp);
            dos.writeInt(this.canvas.rolem.playerNeedExp);
            dos.writeInt(this.canvas.rolem.fplayerbox[0].baseLife[0]);
            dos.writeInt(this.canvas.rolem.fplayerbox[0].baseMagic[0]);
            dos.writeInt(this.canvas.rolem.fplayerbox[0].baseAgility[0]);
            dos.writeInt(this.canvas.rolem.fplayerbox[0].baseDefence[0]);
            dos.writeInt(this.canvas.rolem.fplayerbox[0].baseAttack[0]);
            dos.writeInt(this.canvas.rolem.fplayerbox[0].basebaoji[0]);
            dos.writeInt(this.canvas.rolem.fplayerbox[0].life[0]);
            dos.writeInt(this.canvas.rolem.fplayerbox[0].magic[0]);
            dos.writeInt(this.canvas.rolem.playerCurrentSkillPoint);
            dos.writeInt(this.canvas.rolem.playerUseedSkillPoint);
            this.canvas.setRecord(bos.toByteArray(), 10);
            dos.close();
            bos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPlayerData() {
        byte[] data = this.canvas.getRecord(10);
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);
        try {
            this.canvas.rolem.fplayerbox[0].level = this.canvas.rolem.playerLevel = dis.readByte();
            this.canvas.rolem.playerCurrentExp = dis.readInt();
            this.canvas.rolem.playerNeedExp = dis.readInt();
            this.canvas.rolem.fplayerbox[0].baseLife[0] = dis.readInt();
            this.canvas.rolem.fplayerbox[0].baseMagic[0] = dis.readInt();
            this.canvas.rolem.fplayerbox[0].baseAgility[0] = dis.readInt();
            this.canvas.rolem.fplayerbox[0].baseDefence[0] = dis.readInt();
            this.canvas.rolem.fplayerbox[0].baseAttack[0] = dis.readInt();
            this.canvas.rolem.fplayerbox[0].basebaoji[0] = dis.readInt();
            this.canvas.rolem.fplayerbox[0].life[0] = dis.readInt();
            this.canvas.rolem.fplayerbox[0].magic[0] = dis.readInt();
            this.canvas.rolem.fplayerbox[0].attack[0] = this.canvas.rolem.fplayerbox[0].baseAttack[0];
            this.canvas.rolem.fplayerbox[0].defence[0] = this.canvas.rolem.fplayerbox[0].baseDefence[0];
            this.canvas.rolem.fplayerbox[0].baoji[0] = this.canvas.rolem.fplayerbox[0].basebaoji[0];
            this.canvas.rolem.playerCurrentSkillPoint = dis.readInt();
            this.canvas.rolem.playerUseedSkillPoint = dis.readInt();
            dis.close();
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        hire = 1;
        gameMenuStartIndex = 0;
        LABEL_X = new int[][]{{24, 123, 209}, {24, 113, 209}, {24, 113, 206}};
        visualKeyActionMap = new int[][]{{262176, 536, 262, 0, 0, 0, 0, 0}, {1, 338, 310, 0, 0, 0, 0, 0}, {2, 582, 200, 0, 0, 0, 0, 0}, {8, 474, 314, 0, 0, 0, 0, 0}, {128, 519, 207, 0, 0, 0, 0, 0}, {512, 474, 252, 0, 0, 0, 0, 0}, {2048, 267, 310, 0, 0, 0, 0, 0}, {1024, 18, 173, 0, 0, 0, 0, 0}};
        visualDirectionKeyActionMap = new int[][]{{4100, 91, 296, 3, 0, 0, 0, 0}, {8448, 97, 360, 0, 0, 0, 0, 0}, {16400, 73, 341, 5, 0, 0, 0, 0}, {32832, 136, 335, 6, 0, 0, 0, 0}};
    }
}

