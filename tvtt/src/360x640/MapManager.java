/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
package src;

import javax.microedition.lcdui.Image;
import src.Control;
import src.GameCanvas;
import src.GameData;
import src.GameUI;
import src.Role;
import src.Util;

public class MapManager
implements GameData {
    GameCanvas canvas;
    int backColor;
    short[][] modulesdata;
    byte[][][] maptype;
    short[][][][] mapdata;
    short[][] collidedata;
    short[][] flushEnemyRect;
    Image[] mapImage;
    short titleSize;
    short titleHalfSize;
    short mapIndex = (short)168;
    short lastMapIndex;
    short nextMapIndex = (short)168;
    short lastdoorindex;
    short nextDoorIndex;
    short currentMapImageIndex;
    int areaopenstate = 4;
    short mapW;
    short mapH;
    byte[][] flyerData = new byte[][]{{0}, {1}, {-1}, {-1}};
    byte[] flyerBoxNum = new byte[]{1, 1, -1, -1, -1, -1};
    boolean isFreeMove;
    final byte[] maparea = new byte[]{21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 20, 20, 20, 20, 20, 20, 20, 20, 20, 23, 23, 23, 23, 23, 23, 23, 23, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 15, 15, 15, 15, 15, 15, 14, 14, 14, 14, 14, 14, 14, 14, 9, 9, 9, 9, 9, 9, 6, 6, 6, 6, 6, 6, 16, 16, 16, 16, 16, 16, 18, 18, 18, 18, 18, 18, 18, 18, 19, 19, 19, 19, 19, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 5, 22, 17, 13, 1, 24};
    final byte[] mapImageData = new byte[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5};
    final byte[][] imageData = new byte[][]{{0, 1, 2, 3, 4}, {5, 6, 7}, {8, 9, 10, 11}, {12, 13, 14, 15}, {16, 17, 18, 13, 19, 15}};
    int w;
    int h;
    Image[] universeMapImage;
    short[] universeMapModules;
    short[][] universeMapFrames;
    short[][] universeMapActions;
    byte universeMapActionIndex;
    byte[] universeMapActionFrameIndex;
    byte[] universeMapFrameDelay;
    byte mapinfotype;
    boolean universeMapActionOver;
    byte[] universeMapImgData = new byte[]{102, 103, 107, 0, 108, 109, 124, 122, 104, 105, 106, 100, 99};
    byte universeMapActionState;
    byte[] tempData = null;
    int[] position = new int[]{0};
    final byte W_ROT90 = (byte)82;
    final byte W_ROT270 = (byte)74;
    final byte H_ROT = (byte)9;
    int[][] universeinfodata = new int[][]{{2, 144, 14, 5, 226, 23}, {2, 495, 4, 4, 421, 13}, {1}, {5, 111, 149, 5, 193, 158}, {1}, {1}, {3, 236, 171, 5, 318, 180}, {4, 457, 101, 4, 383, 110}, {4, 510, 187, 4, 436, 196}};
    int universewaittime;
    boolean isdrawuniverselist;
    boolean isdrawuniverselistover;
    boolean isuniverseflushinfo;
    int[] universelistspeedy;
    byte[] missionicon = new byte[]{116, 116};
    public static final short UNIVERSE_MAP_OFFSET_X = 154;
    public static final short UNIVERSE_MAP_OFFSET_Y = 0;
    public static final short UNIVERSE_MAP_NAME_OFFSET_X = -50;
    public static final short UNIVERSE_MAP_NAME_OFFSET_Y = -2;
    public static final short UNIVERSE_MAP_STEP_Y = 46;
    short[][] universeMapPointerData = new short[][]{{560, 196}, {543, 225}, {526, 254}, {509, 283}, {492, 312}};
    public static byte universeMapStartIndex = 0;
    public static final byte universeMapItemShowAmount = 5;
    String[] citybuildname = new String[]{"\u661f\u9645\u795e\u6bbf", "\u5e02\u653f\u5385", "\u519b\u5de5\u5382", "\u8d38\u6613\u4e2d\u5fc3", "\u77ff\u573a", "\u51fa\u53e3"};
    byte[] citybuildnamenum = new byte[]{4, 3, 3, 4, 2, 2};
    String[] citybuildinfo = new String[]{"\u57ce\u5e02\u7684\u5fc3\u810f\u6027\u5efa\u7b51\uff0c\u5728\u8fd9\u91cc\u60a8\u53ef\u4ee5\u5347\u7ea7\u6240\u5728\u57ce\u5e02\u7684\u7b49\u7ea7\uff0c\u4e5f\u53ef\u4ee5\u8ba9\u6211\u4eec\u4fdd\u5b58\u4e0b\u60a8\u90a3\u4f1f\u5927\u7684\u5192\u9669\u7ecf\u5386\u3002", "\u4ea4\u63a5\u4efb\u52a1\u573a\u6240\uff0c\u8fd9\u91cc\u6709\u7740\u65e0\u6570\u53ef\u4ee5\u5c55\u793a\u60a8\u624d\u534e\u7684\u821e\u53f0\u3002", "\u4e00\u540d\u4f1f\u5927\u7684\u82f1\u96c4\u6ca1\u6709\u8db3\u591f\u597d\u7684\u88c5\u5907\u600e\u4e48\u53ef\u4ee5\uff1f\u5982\u679c\u60a8\u662f\u4e00\u540d\u5929\u751f\u7684\u6218\u58eb\uff0c\u90a3\u8bf7\u5728\u8fd9\u91cc\u6311\u8eab\u9002\u5408\u60a8\u7684\u88c5\u5907\u5427\u3002", "\u6574\u4e2a\u661f\u9645\u6700\u7545\u9500\u7684\u5546\u54c1\u5728\u8fd9\u91cc\u4e00\u5e94\u4ff1\u5168\uff0c\u5f53\u7136\u60a8\u8981\u4ed8\u5f97\u8d77\u8db3\u591f\u7684\u94b1\u624d\u884c\u3002", "\u91c7\u96c6\u6c34\u6676\u5e01\u4ee5\u53ca\u6750\u6599\u7684\u5730\u70b9\uff0c\u9700\u96c7\u4f63\u91c7\u96c6\u8005\u4e3a\u60a8\u6548\u529b\uff0c\u5f53\u7136\uff0c\u91c7\u96c6\u8005\u7684\u96c7\u4f63\u6570\u91cf\u8d8a\u591a\u60a8\u7684\u6536\u76ca\u4e5f\u4f1a\u8d8a\u5927\uff0c\u795d\u60a8\u5728\u8fd9\u83b7\u5f97\u597d\u8fd0\uff0c\u6211\u4f1f\u5927\u7684\u6218\u58eb\u3002", "\u8e0f\u51fa\u8fd9\u9053\u95e8\uff0c\u5916\u9762\u7684\u4e16\u754c\u4e0d\u518d\u5b89\u5168\uff01"};
    int weatherstate;
    short[][] universeMapAreaData = new short[][]{{0, 141, 14, 1, 0, 0, 5}, {1, 167, -1, 1, -1, 3, 1}, {0, 149, 15, 1, 1, 0, 4}, {0, 85, 8, 0, -1, 5, 2}, {0, 156, 16, 0, -1, 2, 2}, {1, 163, -1, 1, -1, 3, 1, 13}, {0, 116, 10, 1, -1, 3, 2}, {0, 29, 3, 2, -1, 3, 3}, {0, 39, 4, 0, 0, 3, 5}, {0, 110, 10, 1, -1, 3, 2}, {0, 73, 7, 0, -1, 4, 2}, {0, 61, 6, 1, -1, 6, 3}, {0, 51, 5, 2, -1, 6, 3}, {1, 166, -1, 2, -1, 6, 1, 15}, {0, 102, 9, 0, -1, 1, 2}, {0, 96, 9, 0, -1, 1, 4}, {0, 122, 11, 3, -1, 7, 2}, {1, 165, -1, 0, -1, 7, 1, 16}, {0, 128, 12, 3, 0, 7, 5}, {0, 136, 13, 3, -1, 7, 2}, {0, 12, 1, 3, 1, 8, 4}, {0, 0, 0, 1, 1, 8, 4}, {1, 164, -1, 3, -1, 8, 1, 14}, {0, 21, 2, 1, 0, 8, 5}};
    final byte[] cityLoadIcon = new byte[]{1, 3, 0, 2};
    byte[][] universeMapData = new byte[][]{{2, 0}, {15, 14}, {4}, {5, 9, 6, 8, 7}, {10}, {3}, {11, 12, 13}, {16, 17, 18, 19}, {20, 21, 22, 23}};
    byte[][] universeInfoBarMap = new byte[][]{{1, 3}, {2, 4}, {1}, {0, 1, 2, 3, 4}, {3}, {2}, {0, 2, 4}, {0, 1, 3, 4}, {0, 1, 2, 3}};
    byte[][] universeMapIconIndex = new byte[][]{{13, 15}, {13, 16}, {16}, {18, 17, 17, 15, 12}, {16}, {16}, {12, 12, 21}, {16, 20, 14, 17}, {13, 13, 19, 14}};
    String[][] universeinfo;
    String[][] mapinfo;
    int[][] universenamepostion = new int[][]{{92, 26}, {560, 16}, {182, 70}, {61, 160}, {315, 141}, {332, 26}, {185, 182}, {503, 113}, {580, 199}};
    int[][] universeAreaData = new int[][]{{17, 52, 20, 20}, {180, 35, 30, 30}, {81, 69, 20, 20}, {27, 111, 40, 40}, {102, 115, 30, 30}, {178, 131, 20, 20}, {14, 186, 40, 40}, {196, 180, 40, 40}, {152, 211, 50, 50}};
    int[] universeMousePosition = new int[]{26, 60, 196, 50, 90, 79, 50, 134, 120, 131, 188, 140, 40, 209, 209, 204, 183, 240};
    int[] universeMissioniconPos = new int[]{109, 68, 532, 53, 203, 133, 89, 239, 294, 198, 344, 78, 204, 237, 476, 176, 555, 255};
    int[] universeiconPos = new int[]{122, 74, 555, 64, 223, 144, 126, 269, 315, 201, 360, 83, 225, 244, 502, 193, 573, 269};
    byte universeMapState;
    byte universeMapIndex;
    byte universeMapSelectIndex;
    int universeMapSelectX;
    int universeMapSelectY;
    int universeMapSpeedX;
    int universeMapSpeedY;
    int hdirection;
    int vdirection;
    boolean universeMapSelectActionFlag = false;
    byte[][] universekeydata = new byte[][]{{-1, -1, -1, 0, 2, 1, 3, 5, 4}, {3, 5, 4, 6, 8, 7, -1, -1, -1}, {-1, 2, 0, -1, 3, 4, -1, 8, 6}, {2, -1, 1, 4, 5, -1, 8, -1, 7}};
    Image[] cityiconimages;
    short[] cityiconmodules;
    short[][] cityiconframes;
    short[][] cityiconactions;
    byte cityiconactionindex;
    byte cityiconactionframeindex;
    byte cityiconframedelay;
    boolean cityiconactionover;
    byte drawcityiconstate;
    Image[] cityMapImage;
    short[] cityMapModules;
    short[][] cityMapFrames;
    short[][] cityMapActions;
    boolean cityMapActionOver;
    byte cityMapActionIndex;
    byte cityMapActionFrameIndex;
    byte cityMapFrameDelay;
    short[][] allCityMapImgData = new short[][]{{110, 103, 123, 126, 112, 111}, {103, 114, 127, 115, 113}, {103, 116, 123, 128, 118, 117}, {103, 119, 129, 120, 121}, {110, 103, 123, 130, 112, 111}};
    Image cityMouse;
    Image cityInfoBG;
    int citybuildnum;
    int[][][] citybuildpos = new int[][][]{new int[][]{{320, 203}, {238, 182}, {130, 136}, {380, 280}, {70, 230}, {472, 65}}, new int[][]{{155, 222}, {335, 225}, {245, 225}, {240, 115}, {472, 65}}, new int[][]{{390, 325}, {245, 322}, {30, 300}, {355, 110}, {472, 65}}, new int[][]{{55, 265}, {240, 115}, {185, 180}, {375, 255}, {472, 65}}, new int[][]{{320, 203}, {238, 182}, {130, 136}, {380, 280}, {70, 230}, {472, 65}}};
    byte[][][] cityicondata = new byte[][][]{new byte[][]{{26, 21, 17, 19, 20, 18}, {25, 13, 9, 11, 12, 10}}, new byte[][]{{26, 22, 17, 19, 20}, {25, 14, 9, 11, 12}}, new byte[][]{{26, 24, 17, 19, 18}, {25, 16, 9, 11, 10}}, new byte[][]{{26, 23, 17, 19, 20}, {25, 15, 9, 11, 12}}, new byte[][]{{26, 21, 17, 19, 20, 18}, {25, 13, 9, 11, 12, 10}}};
    byte[][] allcitybuildindex = new byte[][]{{5, 0, 4, 3, 1, 2}, {4, 2, 3, 0, 1}, {4, 2, 3, 0, 1}, {4, 2, 0, 3, 1}, {5, 0, 4, 3, 1, 2}};
    byte[] currentbuildindex;
    byte[][] allcitybuildname = new byte[][]{{5, 0, 1, 2, 3, 4}, {5, 0, 1, 2, 3}, {5, 0, 1, 2, 4}, {5, 0, 1, 2, 3}, {5, 0, 1, 2, 3, 4}};
    public static final short CITY_MAP_BAR_BASE_X = 490;
    public static final short CITY_ICON_STEP = 44;
    boolean isshowrideloading;
    final String[] cityName = new String[]{"\u65b0\u9521\u5b89", "\u4e94\u56fd\u57ce", "\u6d77\u62c9\u5c14", "\u963f\u65af\u5854\u7eb3", "\u8d1d\u5c14\u7279"};
    byte[] cityMapLevel = new byte[]{0, 0, 0, 0, 0};
    byte cityMapIndex;
    byte cityBuildSelectIndex;
    byte cityMapSelectIndex;
    byte[] currentcitybuildname;
    boolean iscityflushinfo = true;
    short[][] minimapdata;
    byte minimapindex;
    byte minimapstate;
    int showtime;
    short minimapx;
    short minimapy;
    short minimapw;
    short minimaph;
    short[] currentminimaplist;
    int bossstate;
    final short[][] minimaplist = new short[][]{{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}, {12, 13, 14, 15, 16, 17, 18, 19, 20}, {21, 22, 23, 24, 25, 26, 27, 28}, {29, 30, 31, 32, 33, 34, 35, 36, 37, 38}, {39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50}, {51, 52, 53, 54, 55, 56, 57, 58, 59, 60}, {61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72}, {73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84}, {85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95}, {96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109}, {110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121}, {122, 123, 124, 125, 126, 127}, {128, 129, 130, 131, 132, 133, 134, 135}, {136, 137, 138, 139, 140}, {141, 142, 143, 144, 145, 146, 147, 148}, {149, 150, 151, 152, 153, 154, 155}, {156, 157, 158, 159, 160, 161, 162}};
    public static final short MINIMAP_CUTLINE_OFFSET_X = 200;
    public static final short MINIMAP_CUTLINE_OFFSET_Y = -25;
    public static final short MINIMAP_CUTLINE_STEP_OFFSET_Y = 7;
    int minimaptime;
    short[] eventState = new short[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    String[] talksdata;
    String[] npcname;
    short[][] npcData;
    short[][] chestData;
    short[][] doorData;
    short[][][] eventdata;
    boolean isEventFinished;
    boolean isEventPlaying;
    byte eventindex;
    byte lastEventIndex;
    byte eventstep;
    short eventtime;
    byte startNpcIndex;
    byte endNpcIndex;
    byte cameraMoveNum;
    byte cameraHdir;
    byte cameraVdir;
    byte cameraMoveSpeedH;
    byte cameraMoveSpeedV;
    byte lastCameraMovenum;
    byte lastCameraHdir;
    byte lastCameraVdir;
    byte lastCameraMoveSpeedH;
    byte lastCameraMoveSpeedV;
    short lastCameraMoveW;
    short lastCameraMoveH;
    boolean isEventContinue;
    byte eventcontinuemapindex;
    byte eventcontinueindex;
    boolean iseventAotoCamera;
    byte eventhappendcounter;
    int eventhappendindex;
    short[][] comebackeventdata = new short[][]{{142, 0}, {143, 0}};
    final byte \u4e0e\u57ce\u5e02NPC\u5bf9\u8bdd;
    final byte \u5230\u8fbe\u5730\u56fe;
    final byte \u8bbe\u7f6e\u4e3b\u89d2\u4f4d\u7f6e = (byte)2;
    final byte \u4e3b\u89d2\u79fb\u52a8 = (byte)3;
    final byte \u6e05\u7a7a\u654c\u4eba = (byte)4;
    final byte \u5237\u65b0\u5934\u76ee = (byte)5;
    final byte \u5237\u65b0\u5c0f\u5175 = (byte)6;
    final byte \u505c\u6b62\u5237\u5175 = (byte)7;
    final byte \u5bf9\u8bdd = (byte)8;
    final byte \u5c0f\u5175\u8f6c\u5411 = (byte)9;
    final byte \u654c\u4eba\u5168\u706d = (byte)10;
    final byte \u5c0f\u5175\u79fb\u52a8 = (byte)11;
    final byte \u9501\u95e8 = (byte)12;
    final byte \u81ea\u52a8\u5bf9\u8bdd = (byte)13;
    final byte \u8bbe\u7f6e\u5237\u5175\u6570\u91cf = (byte)14;
    final byte \u5207\u6362\u5730\u56fe = (byte)15;
    final byte \u7b49\u5f85 = (byte)16;
    final byte \u8fc7\u95e8 = (byte)17;
    final byte \u8bbe\u7f6e\u5175\u8868 = (byte)18;
    final byte \u8bbe\u7f6e\u5934\u76ee\u52a8\u4f5c = (byte)19;
    final byte \u8bbe\u7f6e\u5934\u76ee\u751f\u5b58\u72b6\u6001 = (byte)20;
    final byte \u8bbe\u7f6e\u5c0f\u5175\u751f\u5b58\u72b6\u6001 = (byte)21;
    final byte \u5f00\u59cb\u9707\u5c4f = (byte)22;
    final byte \u505c\u6b62\u9707\u5c4f = (byte)23;
    final byte \u4e3b\u7ebf\u4efb\u52a1\u5b8c\u6210 = (byte)24;
    final byte \u4e8b\u4ef6\u7ee7\u7eed = (byte)25;
    final byte \u5b9e\u73b0\u4e8b\u4ef6\u7ee7\u7eed = (byte)26;
    final byte \u8bbe\u7f6e\u6444\u50cf\u673a\u7126\u70b9 = (byte)27;
    final byte \u955c\u5934\u81ea\u52a8 = (byte)28;
    final byte \u63a5\u53d7\u4e3b\u7ebf\u4efb\u52a1 = (byte)29;
    final byte \u4e3b\u7ebf\u4efb\u52a1\u6761\u4ef6 = (byte)30;
    final byte \u4e3b\u89d2\u653b\u51fb = (byte)31;
    final byte \u5934\u76ee\u653b\u51fb = (byte)32;
    final byte \u5934\u76ee\u79fb\u52a8 = (byte)33;
    final byte \u63d0\u793a = (byte)34;
    final byte \u5934\u76ee\u8f6c\u5411 = (byte)35;
    final byte \u5f00\u542f\u533a\u57df = (byte)36;
    final byte \u8fdb\u95e8\u89e6\u53d1 = (byte)37;
    final byte \u65c1\u767d = (byte)38;
    final byte \u5f00\u542f\u6b66\u5668 = (byte)39;
    final byte \u8bbe\u7f6e\u5c0f\u5175\u52a8\u4f5c = (byte)40;
    final byte \u8bbe\u7f6e\u5934\u76ee\u7c7b\u578b = (byte)41;
    final byte \u4e3b\u89d2\u8f6c\u5411 = (byte)42;
    final byte \u4e3b\u7ebf\u4efb\u52a1\u533a\u95f4\u6761\u4ef6 = (byte)43;
    final byte \u5f00\u542f\u652f\u7ebf\u4efb\u52a1 = (byte)44;
    final byte \u8bbe\u7f6e\u4e3b\u89d2\u52a8\u4f5c = (byte)45;
    final byte \u901a\u5173 = (byte)46;
    final byte \u4e3b\u89d2\u6a2a\u5411\u79fb\u52a8 = (byte)47;
    final byte \u6e05\u7a7a\u4e3b\u89d2 = (byte)48;
    final byte \u5237\u65b0\u4e3b\u89d2 = (byte)49;
    final byte \u5c0f\u5175\u653b\u51fb = (byte)50;
    final byte \u5934\u76ee\u6b7b\u4ea1 = (byte)51;
    final byte \u5f00\u59cb\u56de\u5fc6 = (byte)52;
    final byte \u7ed3\u675f\u56de\u5fc6 = (byte)53;
    final byte \u5173\u5c4f = (byte)54;
    final byte \u5207\u6362\u6b66\u5668 = (byte)55;
    final byte \u5317\u6d77\u5de8\u5996\u79fb\u52a8 = (byte)56;
    final byte \u4ecb\u7ecd = (byte)57;
    final byte \u4e3b\u7ebf\u4efb\u52a1\u72b6\u6001 = (byte)58;
    final byte \u8bbe\u7f6eNPC\u4f4d\u7f6e = (byte)59;
    final byte \u5c55\u793a\u4e3b\u7ebf\u4efb\u52a1 = (byte)60;
    final byte \u83b7\u5f97\u4efb\u52a1\u5956\u52b1 = (byte)61;
    final byte \u65f6\u95f4\u9759\u6b62\u5f00\u59cb = (byte)62;
    final byte \u65f6\u95f4\u9759\u6b62\u7ed3\u675f = (byte)63;
    final byte \u8bbe\u7f6e\u5c0f\u5730\u56fe = (byte)64;
    final byte \u8bbe\u7f6e\u56de\u57ce\u72b6\u6001 = (byte)65;
    final byte \u8bbe\u7f6e\u4e3b\u89d2\u5217\u8868 = (byte)66;
    final byte \u8bbe\u7f6e\u76df\u53cb\u751f\u5b58\u72b6\u6001 = (byte)67;
    final byte \u8bbe\u7f6e\u662f\u5426\u7ed8\u5236\u4e3b\u89d2 = (byte)68;
    final byte \u76df\u53cb\u653b\u51fb = (byte)69;
    final byte \u4e0e\u6218\u6597NPC\u5bf9\u8bdd = (byte)70;
    final byte \u5faa\u73af\u4e8b\u4ef6 = (byte)71;
    final byte \u8fdb\u5165\u4fdd\u5b58 = (byte)72;
    final byte \u5f00\u59cb\u6559\u5b66 = (byte)73;
    final byte \u76df\u53cb\u8f6c\u5411 = (byte)74;
    final byte \u57ce\u5e02\u5347\u7ea7 = (byte)75;
    final byte \u8bbe\u7f6eNPC\u72b6\u6001 = (byte)76;
    final byte NPC\u79fb\u52a8 = (byte)77;
    final byte \u8bbe\u7f6e\u4e3b\u7ebf\u4efb\u52a1\u72b6\u6001 = (byte)78;
    final byte \u6307\u6325\u5b98\u88ab\u653b\u51fb = (byte)79;
    final byte \u5c0f\u5175\u663e\u793a\u8868\u60c5 = (byte)80;
    final byte NPC\u8f6c\u5411 = (byte)81;
    final byte \u5173\u95ed\u533a\u57df = (byte)82;
    final byte \u4e3b\u89d2\u663e\u793a\u8868\u60c5 = (byte)83;
    final byte \u8bbe\u7f6e\u5f71\u50cf\u72b6\u6001 = (byte)84;
    final byte \u8bbe\u7f6e\u79fb\u52a8\u901f\u5ea6 = (byte)85;
    final byte \u7280\u725b\u79fb\u52a8 = (byte)86;
    final byte \u8bbe\u7f6eNPC\u52a8\u4f5c = (byte)87;
    final byte \u8bbe\u7f6e\u654c\u4eba\u5168\u6b7b\u72b6\u6001 = (byte)88;
    final byte \u53d1\u51fa\u5149\u6548 = (byte)89;
    final byte \u6e05\u7a7a\u5149\u6548 = (byte)90;
    final byte \u4e3b\u89d2\u51b2\u950b = (byte)91;
    final byte \u76df\u53cb\u663e\u793a\u8868\u60c5 = (byte)92;
    final byte NPC\u663e\u793a\u8868\u60c5 = (byte)93;
    final byte \u652f\u7ebf\u4efb\u52a1\u6761\u4ef6 = (byte)94;
    final byte \u68c0\u6d4b\u4eba\u7269\u72b6\u6001 = (byte)95;
    final byte \u4e3b\u89d2\u548cNPC\u540c\u65f6\u663e\u793a\u8868\u60c5 = (byte)96;
    final byte \u8bbe\u7f6e\u76df\u53cb\u52a8\u4f5c = (byte)97;
    final byte \u8bbe\u7f6e\u5934\u76ee\u6b7b\u4ea1 = (byte)98;
    final byte \u4e0b\u5750\u9a91 = (byte)99;
    final byte \u6b63\u7248\u9a8c\u8bc1 = (byte)100;
    final byte \u81ea\u52a8\u4fdd\u5b58 = (byte)101;
    final byte \u5bf9\u8bdd1 = (byte)102;
    final byte \u56de\u57ce\u6559\u5b66\u5b8c\u6210 = (byte)103;
    final byte \u68c0\u6d4b\u662f\u5426\u6709\u56de\u57ce\u5377\u8f74 = (byte)104;
    final byte \u52a0\u8f7d\u5750\u9a91 = (byte)105;
    final byte \u5750\u9a91\u72b6\u6001\u8fd8\u539f = (byte)106;
    final byte \u5f00\u59cb\u56fa\u5b9a\u5237\u5175 = (byte)107;
    final byte \u7ed3\u675f\u56fa\u5b9a\u5237\u5175 = (byte)108;
    final byte \u968f\u673a\u5237\u5175 = (byte)109;
    final byte \u8bbe\u7f6e\u79fb\u52a8\u72b6\u6001 = (byte)110;
    byte autoeventstate;
    final short[][] autoeventdata = new short[][]{{24, 0}, {54, 5, 2, 6}, {2, 300, 300, 2}, {4}, {104}, {102, 0, 1}, {102, 1, 1}, {102, 2, 1}, {102, 3, 1}, {102, 4, 1}, {102, 5, 1}, {103}, {73, 6}};
    final String[] autotalksdata = new String[]{"\u5c3c\u5e03\u4e00\u4e16\uff1a\u8bfa\u5170\uff0c\u7b2c\u4e00\u6b21\u505a\u4efb\u52a1\u8fd8\u987a\u5229\u4e48\uff1f", "\u8bfa\u5170\uff1a\u5df2\u7ecf\u5b8c\u6210\u4e86\uff01\u6b63\u8981\u8fd4\u56de\u65b0\u9521\u5b89\uff0c\u4e0d\u8fc7\u56de\u53bb\u65f6\u6050\u6015\u53c8\u514d\u4e0d\u4e86\u548c\u654c\u4eba\u53d1\u751f\u78b0\u649e\u3002", "\u5c3c\u5e03\u4e00\u4e16\uff1a\u4f7f\u7528$2\u6298\u8dc3\u6676\u4f53$1\u5c31\u4f1a\u7acb\u5373\u8fd4\u56de\u592a\u7a7a\uff0c\u8fd9\u6837\u4f1a\u907f\u514d\u5f88\u591a\u9ebb\u70e6\u3002", "\u8bfa\u5170\uff1a$2\u6298\u8dc3\u6676\u4f53$1\uff1f", "\u5c3c\u5e03\u4e00\u4e16\uff1a\u5bf9\uff0c\u8fd9\u662f\u7528\u6700\u5148\u8fdb\u7684\u6298\u8dc3\u6280\u672f\u5236\u9020\u51fa\u7684\u7269\u54c1\uff0c\u53bb$2\u80cc\u5305$1\u4e2d\u627e\u5230$2\u6298\u8dc3\u6676\u4f53$1\u4f7f\u7528\u5b83\u5c31\u53ef\u4ee5\u4e86\u3002", "\u8bfa\u5170\uff1a\u597d\u7684\uff0c\u6211\u5373\u523b\u8fd4\u56de\uff01"};
    int changeRideImageTime;
    byte tempcurrentRideIndex;
    boolean isRideDown;
    byte savestate;
    short[][] weatherdata;
    boolean isshowweather;
    byte weatherindex;
    Image[] weatherimages;
    short[] weathermodules;
    short[][] weatherframes;
    short[][] weatheractions;
    boolean isTeachPlayering;
    byte teachindex;
    byte teachstep;
    short teachstate;
    static final short \u70b9\u51fb\u57ce\u5e02\u5efa\u7b51 = 0;
    static final short \u661f\u9645\u795e\u6bbf\u754c\u9762\u9009\u62e9 = 1;
    static final short \u5347\u7ea7\u754c\u9762 = 2;
    static final short \u5347\u7ea7\u754c\u9762Command = 3;
    static final short \u70b9\u51fb\u8fdb\u5165\u661f\u7403 = 4;
    static final short \u70b9\u51fb\u8fdb\u5165\u4e8c\u7ea7\u661f\u7403 = 5;
    static final short \u6309\u53f3\u8f6f\u952e = 6;
    static final short \u8fdb\u5165\u6280\u80fd\u754c\u9762 = 7;
    static final short \u9009\u62e9\u5251\u6280\u80fd = 8;
    static final short \u9009\u62e9\u6280\u80fd = 9;
    static final short \u5f00\u542f\u6280\u80fd = 10;
    static final short \u88c5\u5907\u6280\u80fd = 11;
    static final short \u88c5\u59077\u952e = 12;
    static final short \u4ecb\u7ecd\u5251\u62a4\u7532 = 13;
    static final short \u4ecb\u7ecd\u62f3\u5957\u62a4\u7532 = 14;
    static final short \u4ecb\u7ecd\u666e\u901a\u62a4\u7532 = 15;
    static final short \u8fdb\u5165\u4e8b\u4ef6 = 16;
    static final short \u9a91\u4e0a\u5750\u9a91 = 17;
    static final short \u5207\u6362\u6b66\u5668\u6559\u5b66 = 18;
    static final short \u9009\u62e9\u80cc\u5305 = 19;
    static final short \u9009\u62e9\u7269\u54c1\u80cc\u5305 = 20;
    static final short \u9009\u62e9\u56de\u57ce\u5377\u8f74 = 21;
    static final short \u4f7f\u7528\u56de\u57ce\u5377\u8f74 = 22;
    static final short \u9009\u62e9\u7269\u54c1 = 23;
    static final short \u9009\u62e9\u786e\u5b9a = 24;
    static final short \u8fdb\u5165\u4e8b\u4ef61 = 25;
    short[][][] teachdata = new short[][][]{new short[][]{{0, 4, 1, 0}, {1, 11, 0, 1}, {2, 11, 1}, {3, 11, 1, 0}}, new short[][]{{4, 0}, {5, 2}}, new short[][]{{6}, {7}, {8}, {9}, {10}, {9}, {11}, {12}, {25, 13}}, new short[][]{{13}, {16, 3}}, new short[][]{{17}, {16, 1}}, new short[][]{{18}, {16, 1}}, new short[][]{{6}, {19}, {20}, {21}, {22}, {24}}, new short[][]{{14}, {16, 3}}, new short[][]{{15}, {16, 4}}};

    public MapManager(GameCanvas canvas) {
        this.\u4e0e\u57ce\u5e02NPC\u5bf9\u8bdd = 0;
        this.\u5230\u8fbe\u5730\u56fe = 1;
        this.canvas = canvas;
        this.initweather();
    }

    private void initweather() {
        if (this.canvas.gameUi.currentmainmissionindex > -1) {
            for (int i = 0; i < this.universeMapAreaData.length; ++i) {
                if (this.universeMapAreaData[i][4] <= -1 || Util.getARandomInt(2) != 0) continue;
                this.weatherstate |= 1 << i;
            }
        } else {
            for (int i = 0; i < this.universeMapAreaData.length; ++i) {
                if (this.universeMapAreaData[i][4] <= -1) continue;
                this.weatherstate |= 1 << i;
            }
        }
    }

    public void readMapdata(int index) {
        this.mapIndex = (short)index;
        if (this.mapIndex < 163) {
            Util.readMapdata("/data/m" + index + ".data");
            this.getMapData();
            Util.clearmapdata();
        }
    }

    public void openarea(int index, int type) {
        this.areaopenstate |= 1 << index;
        switch (index) {
            case 22: {
                this.canvas.gameUi.openweapon((byte)1, (byte)0);
                break;
            }
            case 17: {
                this.canvas.gameUi.openweapon((byte)2, (byte)0);
                break;
            }
            case 13: {
                this.canvas.gameUi.openweapon((byte)3, (byte)0);
            }
        }
        switch (type) {
            case 0: {
                this.canvas.gameUi.settishi(this.mapinfo[index][0] + "\u5df2\u5f00\u542f\u3002", (byte)1);
            }
        }
    }

    public void closearea(int index) {
        this.areaopenstate ^= 1 << index;
    }

    public boolean isareaopen(byte index) {
        return (this.areaopenstate & 1 << index) != 0;
    }

    public boolean isdrawshadow(int index) {
        return this.mapImageData[index] == 3 || this.mapImageData[index] == 4;
    }

    public void changeMap(byte ldIndex, int mIndex, int ndIndex, int type, int screentype) {
        this.lastMapIndex = this.mapIndex;
        this.lastdoorindex = ldIndex;
        this.canvas.gameUi.finishfighttishiword();
        if (mIndex == 168) {
            this.inituniverse();
            this.canvas.startSwitchScrren(screentype, (byte)10, 0);
            this.canvas.createloadimage((byte)this.universeMapAreaData[this.maparea[this.nextMapIndex]][3]);
        } else if (mIndex >= 0 && mIndex < 163) {
            this.nextMapIndex = (short)mIndex;
            this.nextDoorIndex = (short)ndIndex;
            switch (type) {
                case 0: {
                    if (this.canvas.rolem.compareEnemyList(this.nextMapIndex) || this.currentMapImageIndex != this.mapImageData[this.nextMapIndex]) {
                        this.canvas.startSwitchScrren(screentype, (byte)5, 0);
                        break;
                    }
                    this.canvas.startSwitchScrren(screentype, (byte)5, 1);
                    break;
                }
                case 1: {
                    this.canvas.startSwitchScrren(screentype, (byte)5, 2);
                    break;
                }
                case 2: {
                    this.canvas.startSwitchScrren(screentype, (byte)5, 3);
                }
            }
        } else if (mIndex >= 163 && mIndex < 168) {
            this.nextMapIndex = (short)mIndex;
            this.cityMapIndex = (byte)(this.nextMapIndex - 163);
            this.citybuildnum = this.citybuildpos[this.cityMapIndex].length;
            this.iscityflushinfo = true;
            this.canvas.startSwitchScrren(screentype, (byte)4, 2);
            this.canvas.createloadimage((byte)this.universeMapAreaData[this.maparea[this.nextMapIndex]][3]);
        }
    }

    public void getMapImage(int index) {
        byte[] imgdata = this.imageData[this.mapImageData[index]];
        this.currentMapImageIndex = this.mapImageData[index];
        this.mapImage = new Image[imgdata.length];
        for (int i = 0; i < this.mapImage.length; ++i) {
            this.mapImage[i] = Util.createImage("/map/" + imgdata[i] + ".png");
        }
    }

    public void fillBack() {
        this.canvas.fillRect(this.backColor, 0, 0, 640, 360);
    }

    public void drawMapBack(int index) {
        this.drawLayer(index);
    }

    public void drawLayer(int i) {
        int j = 0;
        try {
            block8: for (j = 0; j < this.maptype[i].length; ++j) {
                switch (this.maptype[i][j][0]) {
                    case 0: {
                        this.drawNormalCell(this.mapdata[i][j]);
                        continue block8;
                    }
                    case 1: {
                        if (this.mapdata[i][j].length < this.maptype[i][j][1] + 1) continue block8;
                        this.drawAnimationCell(this.maptype[i][j], this.mapdata[i][j]);
                        continue block8;
                    }
                    case 2: {
                        this.drawTileCell(this.maptype[i][j], this.mapdata[i][j]);
                        continue block8;
                    }
                    case 3: {
                        this.drawMoveTileCell(this.maptype[i][j], this.mapdata[i][j]);
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.print("maptype[" + i + "][" + j + "][0] = " + this.maptype[i][j][0]);
            e.printStackTrace();
        }
    }

    public void drawNormalCell(short[][] data) {
        for (int i = 0; i < data.length; ++i) {
            this.drawCell(data[i][0], data[i][2], data[i][3], 20, data[i][1]);
        }
    }

    public void drawAnimationCell(byte[] type, short[][] data) {
        this.drawCell(data[type[1]][0], data[type[1]][2], data[type[1]][3], 20, data[type[1]][1]);
        if (this.canvas.paintType == 0 && (type[2] = (byte)(type[2] + 1)) == data[type[1]][4]) {
            type[2] = 0;
            type[1] = (byte)(type[1] + 1);
            if (type[1] > data.length - 1) {
                type[1] = 0;
            }
        }
    }

    public void drawTileCell(byte[] type, short[][] data) {
        for (int i = 0; i < data.length; ++i) {
            this.w = this.canvas.getModuleW(data[i][1], this.modulesdata[data[i][0]][3], this.modulesdata[data[i][0]][4]);
            this.h = this.canvas.getModuleH(data[i][1], this.modulesdata[data[i][0]][3], this.modulesdata[data[i][0]][4]);
            for (int j = 0; j < type[2]; ++j) {
                for (int k = 0; k < type[1]; ++k) {
                    this.drawCell(data[i][0], data[i][2] + k * this.w * type[3], data[i][3] + j * this.h * type[4], 20, data[i][1]);
                }
            }
        }
    }

    public void drawMoveTileCell(byte[] type, short[][] data) {
        for (int i = 0; i < data.length; ++i) {
            this.w = this.canvas.getModuleW(data[i][1], this.modulesdata[data[i][0]][3], this.modulesdata[data[i][0]][4]);
            this.h = this.canvas.getModuleH(data[i][1], this.modulesdata[data[i][0]][3], this.modulesdata[data[i][0]][4]);
            short[] sArray = data[i];
            sArray[2] = (short)(sArray[2] + type[5]);
            if (type[5] < 0) {
                data[i][2] = (short)(data[i][2] <= data[i][4] - this.w ? data[i][2] + this.w : data[i][2]);
            } else if (type[5] > 0) {
                data[i][2] = (short)(data[i][2] >= data[i][4] + this.w ? data[i][2] - this.w : data[i][2]);
            }
            short[] sArray2 = data[i];
            sArray2[3] = (short)(sArray2[3] + type[6]);
            if (type[6] < 0) {
                data[i][3] = (short)(data[i][3] <= data[i][5] - this.h ? data[i][3] + this.h : data[i][3]);
            } else if (type[6] > 0) {
                data[i][3] = (short)(data[i][3] >= data[i][5] + this.h ? data[i][3] - this.h : data[i][3]);
            }
            for (int j = 0; j < type[2]; ++j) {
                for (int k = 0; k < type[1]; ++k) {
                    this.drawCell(data[i][0], data[i][2] + k * this.w * type[3], data[i][3] + j * this.h * type[4], 20, data[i][1]);
                }
            }
        }
    }

    public void drawCell(int index, int x, int y, int anchor, int trans) {
        if (this.canvas.c.isinCamera(x, y, this.canvas.getModuleW(trans, this.modulesdata[index][3], this.modulesdata[index][4]), this.canvas.getModuleH(trans, this.modulesdata[index][3], this.modulesdata[index][4]))) {
            this.canvas.drawImage(this.mapImage, x, y, anchor, trans, this.modulesdata[index]);
        }
    }

    public void clearFight() {
        this.mapImage = null;
        this.talksdata = null;
        this.npcData = null;
        this.chestData = null;
        this.doorData = null;
        this.eventdata = null;
        this.modulesdata = null;
        this.maptype = null;
        this.mapdata = null;
        this.collidedata = null;
        this.canvas.rolem.poptime = 0;
        this.canvas.gameUi.addState = 0;
        for (int i = 0; i < this.canvas.rolem.fplayerbox[0].hpp.length; i = (int)((byte)(i + 1))) {
            this.canvas.rolem.fplayerbox[0].hpp[i].poptime = 0;
        }
    }

    public void getMapData() {
        this.backColor = Util.backColor;
        this.modulesdata = Util.modulesdata;
        this.maptype = Util.maptype;
        this.mapdata = Util.mapdata;
        this.collidedata = Util.collidedata;
        this.flushEnemyRect = Util.flushenemyrect;
        this.mapW = Util.mapinfo[0];
        this.mapH = Util.mapinfo[1];
    }

    public void initUniverseMap() {
        this.tempData = new byte[10000];
        this.position[0] = 0;
        Util.getStream(this.tempData, "/data/universe.data", 0);
        this.universeMapModules = Util.loadInfShort(this.tempData, this.universeMapModules, this.position);
        this.universeMapFrames = Util.loadInfShort(this.tempData, this.universeMapFrames, this.position);
        this.universeMapActions = Util.loadInfShort(this.tempData, this.universeMapActions, this.position);
        this.universeMapImage = new Image[this.universeMapImgData.length];
        for (int i = 0; i < this.universeMapImgData.length; ++i) {
            if (this.universeMapImgData[i] == 0) {
                if (this.canvas.gameUi.selectImages[0] == null) {
                    this.universeMapImage[i] = Util.createImage("/ui/" + this.universeMapImgData[i] + ".png");
                    continue;
                }
                this.universeMapImage[i] = this.canvas.gameUi.selectImages[0];
                continue;
            }
            this.universeMapImage[i] = Util.createImage("/map/" + this.universeMapImgData[i] + ".png");
        }
        this.tempData = null;
    }

    public void readuniverseinfo() {
        Util.readXls2DUTF("/data/universeinfo.data");
        this.universeinfo = Util.xls2DUTFdata;
        Util.readXls2DUTF("/data/mapinfo.data");
        this.mapinfo = Util.xls2DUTFdata;
        Util.clearXls2DUTF();
    }

    public void cleanuniverseinfo() {
    }

    public void cleanUniverseMap() {
        for (int i = 0; i < this.universeMapImage.length; ++i) {
            this.universeMapImage[i] = null;
        }
        this.universeMapImage = null;
        this.universeMapModules = null;
        this.universeMapFrames = null;
        this.universeMapActions = null;
    }

    public void inituniverselist() {
        this.isdrawuniverselist = true;
        this.universelistspeedy = new int[this.universeinfodata[this.universeMapIndex][0]];
        this.isdrawuniverselistover = false;
    }

    private void universelistProcess() {
        int state = 0;
        int[] speed = new int[]{0, 12, 24, 36, 48};
        int[] tempdata = new int[]{0, 24, 48, 72, 96};
        for (int i = 0; i < this.universeinfodata[this.universeMapIndex][0]; ++i) {
            if (this.universelistspeedy[i] >= tempdata[i]) {
                if ((state = (int)((byte)(state + 1))) < this.universeinfodata[this.universeMapIndex][0]) continue;
                this.isdrawuniverselistover = true;
                continue;
            }
            int n = i;
            this.universelistspeedy[n] = this.universelistspeedy[n] + speed[i];
        }
    }

    public void drawStarInfo(int universeMapIndex) {
        if (this.isdrawuniverselist && !this.isEventPlaying) {
            this.canvas.drawmodule(this.universeMapImage, this.universeMapModules, 30, this.universeinfodata[universeMapIndex][1], this.universeinfodata[universeMapIndex][2], 20, this.universeinfodata[universeMapIndex][3]);
            for (int i = this.universeinfodata[universeMapIndex][0] - 1; i >= 0; --i) {
                this.drawUniverseStaticUi((short)33, this.universeinfodata[universeMapIndex][4], this.universeinfodata[universeMapIndex][5] + this.universelistspeedy[i]);
                if (!this.isdrawuniverselistover) continue;
                if (this.isareaopen(this.canvas.mapm.maparea[this.universeMapAreaData[this.universeMapData[universeMapIndex][i + universeMapStartIndex]][1]])) {
                    this.canvas.drawColorString(this.mapinfo[this.universeMapData[universeMapIndex][i + universeMapStartIndex]][0], this.universeinfodata[universeMapIndex][4], this.universeinfodata[universeMapIndex][5] + 3 + this.universelistspeedy[i], 3, 0xFFFFFF, 0);
                    continue;
                }
                this.canvas.drawColorString(this.mapinfo[this.universeMapData[universeMapIndex][i + universeMapStartIndex]][0], this.universeinfodata[universeMapIndex][4], this.universeinfodata[universeMapIndex][5] + 3 + this.universelistspeedy[i], 3, 0x9F9F9F, 0);
            }
        }
    }

    public void drawUniverseMap(byte mapshowtype) {
        switch (this.universeMapState) {
            case 0: {
                if (mapshowtype == 1) {
                    this.drawUniverseStaticUi((short)9, 320, 180);
                    break;
                }
                if (mapshowtype != 0) break;
                this.drawUniverseStaticUi(this.universeMapIndex, 320, 180);
                this.drawUniverseStaticUi((short)32, 320, 180);
                block12: for (int i = 0; i < this.universeMapData.length; ++i) {
                    for (int j = 0; j < this.universeMapData[i].length; ++j) {
                        if (this.universeMapAreaData[this.universeMapData[i][j]][0] != 1 || !this.isareaopen(this.universeMapData[i][j])) continue;
                        this.canvas.drawmodule(this.cityiconimages, this.cityiconmodules, 27, this.universeiconPos[i * 2], this.universeiconPos[i * 2 + 1], 3, 0);
                        continue block12;
                    }
                }
                this.drawuniverseinfo();
                this.canvas.gameUi.drawStaticUi((short)74, 0, 360);
                this.drawStarInfo(this.universeMapIndex);
                if (this.canvas.gameUi.currentmainmissionindex > -1 && this.canvas.gameUi.missiondata0[this.canvas.gameUi.currentmainmissionindex][this.canvas.gameUi.mainmissionstate - 1] > -1) {
                    this.canvas.gameUi.drawUiModule(this.missionicon[this.canvas.gameUi.mainmissionstate - 1], this.universeMissioniconPos[this.universeMapAreaData[this.canvas.gameUi.missiondata0[this.canvas.gameUi.currentmainmissionindex][this.canvas.gameUi.mainmissionstate - 1]][5] * 2], this.universeMissioniconPos[this.universeMapAreaData[this.canvas.gameUi.missiondata0[this.canvas.gameUi.currentmainmissionindex][this.canvas.gameUi.mainmissionstate - 1]][5] * 2 + 1] + this.canvas.publiccounter / 2 % 2, 33, 0);
                }
                if (this.canvas.rolem.yingxiangstate != 0 || this.canvas.gameUi.isshowtishi) break;
                this.canvas.drawColorString("\u70b9\u51fb\u6b64\u5904\u8fdb\u5165\u5e2e\u52a9", 320, 358, 33, 15597319, 0);
                break;
            }
            case 1: {
                this.drawUniverseStaticUi((short)56, 320, 180);
                this.canvas.drawColorString(this.universeinfo[this.universeMapIndex][0], 60, 7, 17, 0xFFFFFF, 0);
                if (mapshowtype == 0) {
                    this.drawmapinfo();
                    this.canvas.gameUi.drawStaticUi((short)16, 0, 360);
                    this.drawUniverseStaticUi((short)32, 320, 180);
                }
                for (byte i = 0; i < (5 < this.universeMapData[this.universeMapIndex].length ? (byte)5 : this.universeMapData[this.universeMapIndex].length); i = (byte)(i + 1)) {
                    if (i == this.universeMapSelectIndex) {
                        if (this.isareaopen(this.canvas.mapm.maparea[this.universeMapAreaData[this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][1]])) {
                            switch (this.universeMapActionState) {
                                case 0: {
                                    if (this.universeMapSelectActionFlag) {
                                        this.drawUniverseMapSprite((byte)0, 174, 50 + this.universeInfoBarMap[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex] * 46);
                                        if (this.mapinfotype == 1) {
                                            if (mapshowtype == 0) {
                                                this.canvas.drawColorString(this.mapinfo[this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][0], 244, 55 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46, 17, 15597319, 0);
                                                break;
                                            }
                                            if (mapshowtype != 1) break;
                                            this.canvas.drawString(this.mapinfo[this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][0], 244, 55 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46, 17, 15597319);
                                            break;
                                        }
                                        if (mapshowtype == 0) {
                                            this.canvas.drawColorString(this.mapinfo[this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][0], 222, 55 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46, 17, 15597319, 0);
                                            break;
                                        }
                                        if (mapshowtype != 1) break;
                                        this.canvas.drawString(this.mapinfo[this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][0], 222, 55 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46, 17, 15597319);
                                        break;
                                    }
                                    this.drawUniverseStaticUi((short)(46 - this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex]), 174, 50 + this.universeInfoBarMap[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex] * 46);
                                    this.drawUniverseStaticUi(this.universeMapIconIndex[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex], 71, 50 + this.universeInfoBarMap[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex] * 46);
                                    this.drawUniverseMapSprite1((byte)1, 71, 50 + this.universeInfoBarMap[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex] * 46);
                                    if (mapshowtype == 0) {
                                        this.canvas.drawColorString(this.mapinfo[this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][0], 244, 55 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46, 17, 15597319, 0);
                                    } else if (mapshowtype == 1) {
                                        this.canvas.drawString(this.mapinfo[this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][0], 244, 55 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46, 17, 15597319);
                                    }
                                    this.drawUniverseMapSprite1((byte)3, this.universeMapPointerData[this.universeInfoBarMap[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][0], this.universeMapPointerData[this.universeInfoBarMap[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][1]);
                                    break;
                                }
                                case 1: {
                                    this.drawUniverseStaticUi((short)(46 - this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex]), 174, 50 + this.universeInfoBarMap[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex] * 46);
                                    this.drawUniverseStaticUi(this.universeMapIconIndex[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex], 71, 50 + this.universeInfoBarMap[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex] * 46);
                                    this.drawUniverseMapSprite1((byte)1, 71, 50 + this.universeInfoBarMap[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex] * 46);
                                    if (mapshowtype == 0) {
                                        this.canvas.drawColorString(this.mapinfo[this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][0], 244, 55 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46, 17, 15597319, 0);
                                    } else if (mapshowtype == 1) {
                                        this.canvas.drawString(this.mapinfo[this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][0], 244, 55 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46, 17, 15597319);
                                    }
                                    this.drawUniverseMapSprite1((byte)3, this.universeMapPointerData[this.universeInfoBarMap[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][0], this.universeMapPointerData[this.universeInfoBarMap[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][1]);
                                }
                            }
                        } else {
                            switch (this.universeMapActionState) {
                                case 0: {
                                    this.drawUniverseMapSprite((byte)2, 174, 50 + this.universeInfoBarMap[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex] * 46);
                                    if (this.mapinfotype == 1) {
                                        if (mapshowtype == 0) {
                                            this.canvas.drawColorString(this.mapinfo[this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][0], 244, 55 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46, 17, 0x9F9F9F, 0);
                                            break;
                                        }
                                        if (mapshowtype != 1) break;
                                        this.canvas.drawString(this.mapinfo[this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][0], 244, 55 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46, 17, 0x9F9F9F);
                                        break;
                                    }
                                    if (mapshowtype == 0) {
                                        this.canvas.drawColorString(this.mapinfo[this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][0], 222, 55 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46, 17, 0x9F9F9F, 0);
                                        break;
                                    }
                                    if (mapshowtype != 1) break;
                                    this.canvas.drawString(this.mapinfo[this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][0], 222, 55 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46, 17, 0x9F9F9F);
                                    break;
                                }
                                case 1: {
                                    this.drawUniverseStaticUi((short)(53 - this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex]), 174, 50 + this.universeInfoBarMap[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex] * 46);
                                    this.drawUniverseMapSprite1((byte)1, 71, 50 + this.universeInfoBarMap[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex] * 46);
                                    if (mapshowtype == 0) {
                                        this.canvas.drawColorString(this.mapinfo[this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][0], 244, 55 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46, 17, 0x9F9F9F, 0);
                                    } else if (mapshowtype == 1) {
                                        this.canvas.drawString(this.mapinfo[this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][0], 244, 55 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46, 17, 0x9F9F9F);
                                    }
                                    this.drawUniverseMapSprite1((byte)3, this.universeMapPointerData[this.universeInfoBarMap[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][0], this.universeMapPointerData[this.universeInfoBarMap[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][1]);
                                }
                            }
                        }
                        if (this.universeMapAreaData[this.universeMapData[this.universeMapIndex][i + universeMapStartIndex]][0] == 1) {
                            this.canvas.drawmodule(this.cityiconimages, this.cityiconmodules, 8, 169, 62 + this.universeInfoBarMap[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex] * 46, 6, 0);
                        }
                    } else {
                        this.drawUniverseStaticUi((short)11, 174, 50 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46);
                        if (this.isareaopen(this.canvas.mapm.maparea[this.universeMapAreaData[this.universeMapData[this.universeMapIndex][i + universeMapStartIndex]][1]])) {
                            if (mapshowtype == 0) {
                                this.canvas.drawColorString(this.mapinfo[this.universeMapData[this.universeMapIndex][i + universeMapStartIndex]][0], 222, 55 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46, 17, 0xFFFFFF, 0);
                            } else if (mapshowtype == 1) {
                                this.canvas.drawString(this.mapinfo[this.universeMapData[this.universeMapIndex][i + universeMapStartIndex]][0], 222, 55 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46, 17, 0xFFFFFF);
                            }
                        } else if (mapshowtype == 0) {
                            this.canvas.drawColorString(this.mapinfo[this.universeMapData[this.universeMapIndex][i + universeMapStartIndex]][0], 222, 55 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46, 17, 0x9F9F9F, 0);
                        } else if (mapshowtype == 1) {
                            this.canvas.drawString(this.mapinfo[this.universeMapData[this.universeMapIndex][i + universeMapStartIndex]][0], 222, 55 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46, 17, 0x9F9F9F);
                        }
                        if (this.universeMapAreaData[this.universeMapData[this.universeMapIndex][i + universeMapStartIndex]][0] == 1) {
                            this.canvas.drawmodule(this.cityiconimages, this.cityiconmodules, 8, 145, 62 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46, 6, 0);
                        }
                    }
                    if (mapshowtype != 0 || this.canvas.gameUi.currentmainmissionindex <= -1 || this.canvas.gameUi.missiondata0[this.canvas.gameUi.currentmainmissionindex][this.canvas.gameUi.mainmissionstate - 1] <= -1 || this.universeMapData[this.universeMapIndex][i + universeMapStartIndex] != this.canvas.gameUi.missiondata0[this.canvas.gameUi.currentmainmissionindex][this.canvas.gameUi.mainmissionstate - 1]) continue;
                    if (i == this.universeMapSelectIndex) {
                        this.canvas.gameUi.drawUiModule(this.missionicon[this.canvas.gameUi.mainmissionstate - 1], 108, 64 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46 + this.canvas.publiccounter / 2 % 2, 3, 0);
                        continue;
                    }
                    this.canvas.gameUi.drawUiModule(this.missionicon[this.canvas.gameUi.mainmissionstate - 1], 112, 62 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46 + this.canvas.publiccounter / 2 % 2, 3, 0);
                }
                break;
            }
        }
    }

    private void drawuniverseinfo() {
        this.canvas.drawColorString(this.universeinfo[this.universeMapIndex][0], this.universenamepostion[this.universeMapIndex][0], this.universenamepostion[this.universeMapIndex][1], 3, 15597319, 0);
        this.canvas.gameUi.scroll.drawscroll();
    }

    public void setuniverseinfo(byte name) {
        this.canvas.gameUi.scroll.close();
        if (name > -1) {
            StringBuffer strb = new StringBuffer("");
            strb.append("$1");
            strb.append(this.universeinfo[name][1]);
            this.canvas.gameUi.scroll.setscroll(strb.toString(), 75, 300, 490, 40, 1, 20);
        }
    }

    public void setcitybuildinfo(byte name) {
        this.canvas.gameUi.scroll.close();
        if (name > -1) {
            this.canvas.gameUi.labelstr = this.citybuildname[name];
            this.canvas.gameUi.labelwordnum = this.citybuildnamenum[name];
            StringBuffer strb = new StringBuffer("");
            strb.append("$1");
            strb.append(this.citybuildinfo[name]);
            this.canvas.gameUi.scroll.setscroll(strb.toString(), 500, 50, 130, 160, 1, 20);
        }
    }

    private void drawmapinfo() {
        this.canvas.gameUi.scroll.drawscroll();
    }

    public void setmapinfo(byte name) {
        this.canvas.gameUi.scroll.close();
        if (name > -1) {
            StringBuffer strb = new StringBuffer("");
            strb.append("$1");
            strb.append(this.mapinfo[name][1]);
            this.canvas.gameUi.scroll.setscroll(strb.toString(), 75, 300, 490, 40, 1, 20);
        }
    }

    public void setuniversepos() {
        this.universeMapIndex = (byte)this.universeMapAreaData[this.maparea[this.mapIndex]][5];
        this.universeMapSelectX = this.universeMissioniconPos[this.universeMapAreaData[this.maparea[this.mapIndex]][5] * 2];
        this.universeMapSelectY = this.universeMissioniconPos[this.universeMapAreaData[this.maparea[this.mapIndex]][5] * 2 + 1];
    }

    public void setuniversemousepos() {
        this.universeMapSelectX = this.universeMissioniconPos[this.universeMapIndex * 2];
        this.universeMapSelectY = this.universeMissioniconPos[this.universeMapIndex * 2 + 1];
    }

    public void drawUniverseArea() {
        this.canvas.g.setColor(0xFF0000);
        for (int i = 0; i < this.universeAreaData.length; ++i) {
            this.canvas.g.drawRect(this.universeAreaData[i][0], this.universeAreaData[i][1], this.universeAreaData[i][2], this.universeAreaData[i][3]);
            this.canvas.g.drawString("" + i, this.universeAreaData[i][0], this.universeAreaData[i][1], 20);
        }
    }

    public boolean isbigareaopen(byte index) {
        for (int i = 0; i < this.universeMapData[index].length; ++i) {
            if (!this.isareaopen(this.canvas.mapm.maparea[this.universeMapAreaData[this.universeMapData[index][i]][1]])) continue;
            return true;
        }
        return false;
    }

    public void inituniverseui(boolean defaultActionStateFlag) {
        int i;
        this.universeMapFrameDelay = new byte[this.universeMapActions.length];
        for (i = 0; i < this.universeMapActions.length; ++i) {
            this.universeMapFrameDelay[i] = 0;
        }
        this.universeMapActionState = (byte)(!defaultActionStateFlag ? 1 : 0);
        this.universeMapActionFrameIndex = new byte[this.universeMapActions.length];
        for (i = 0; i < this.universeMapActions.length; ++i) {
            this.universeMapActionFrameIndex[i] = 0;
        }
        this.mapinfotype = 0;
        this.universeMapActionOver = false;
    }

    public void inituniverse() {
        this.universeMapState = 0;
        this.setuniversepos();
        this.universewaittime = 5;
        this.isdrawuniverselist = false;
    }

    public void universeKeyProcess() {
        if (this.isEventPlaying) {
            return;
        }
        switch (this.universeMapState) {
            case 0: {
                if (Control.IsKeyDown(4100)) {
                    if (this.universekeydata[0][this.universeMapIndex] > -1) {
                        this.universeMapIndex = this.universekeydata[0][this.universeMapIndex];
                        this.setuniversemousepos();
                        this.isuniverseflushinfo = true;
                        this.universewaittime = 5;
                        this.isdrawuniverselist = false;
                    }
                } else if (Control.IsKeyDown(8448) && this.universekeydata[1][this.universeMapIndex] > -1) {
                    this.universeMapIndex = this.universekeydata[1][this.universeMapIndex];
                    this.setuniversemousepos();
                    this.isuniverseflushinfo = true;
                    this.universewaittime = 5;
                    this.isdrawuniverselist = false;
                }
                if (Control.IsKeyDown(16400)) {
                    if (this.universekeydata[2][this.universeMapIndex] > -1) {
                        this.universeMapIndex = this.universekeydata[2][this.universeMapIndex];
                        this.setuniversemousepos();
                        this.isuniverseflushinfo = true;
                        this.universewaittime = 5;
                        this.isdrawuniverselist = false;
                    }
                } else if (Control.IsKeyDown(32832) && this.universekeydata[3][this.universeMapIndex] > -1) {
                    this.universeMapIndex = this.universekeydata[3][this.universeMapIndex];
                    this.setuniversemousepos();
                    this.isuniverseflushinfo = true;
                    this.universewaittime = 5;
                    this.isdrawuniverselist = false;
                }
                if (Control.IsKeyDown(327712)) {
                    if (this.isbigareaopen(this.universeMapIndex)) {
                        this.universeMapSelectActionFlag = true;
                        this.inituniverseui(this.universeMapSelectActionFlag);
                        this.universeMapSelectIndex = 0;
                        universeMapStartIndex = 0;
                        this.universeMapState = 1;
                        this.setmapinfo(this.universeMapData[this.universeMapIndex][0]);
                        break;
                    }
                    this.canvas.gameUi.settishi("\u8be5\u661f\u7403\u672a\u5f00\u542f\uff0c\u65e0\u6cd5\u901a\u884c", (byte)0);
                    break;
                }
                if (!Control.IsKeyDown(1)) break;
                this.canvas.changeGameState(7);
                this.canvas.gameUi.changeUiState((byte)3);
                break;
            }
            case 1: {
                byte tempuniverseMapSelectIndex = this.universeMapSelectIndex;
                if (Control.IsKeyDown(4100)) {
                    if (this.universeMapSelectIndex > 0) {
                        this.universeMapSelectIndex = (byte)(this.universeMapSelectIndex - 1);
                    } else if (universeMapStartIndex > 0) {
                        universeMapStartIndex = (byte)(universeMapStartIndex - 1);
                    }
                    this.universeMapSelectActionFlag = true;
                    this.inituniverseui(tempuniverseMapSelectIndex != this.universeMapSelectIndex);
                    this.setmapinfo(this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]);
                    break;
                }
                if (Control.IsKeyDown(8448)) {
                    if (this.universeMapSelectIndex < (5 < this.universeMapData[this.universeMapIndex].length ? 5 : this.universeMapData[this.universeMapIndex].length) - 1) {
                        this.universeMapSelectIndex = (byte)(this.universeMapSelectIndex + 1);
                    } else if (5 < this.universeMapData[this.universeMapIndex].length && universeMapStartIndex < this.universeMapData[this.universeMapIndex].length - 5) {
                        universeMapStartIndex = (byte)(universeMapStartIndex + 1);
                    }
                    this.universeMapSelectActionFlag = true;
                    this.inituniverseui(tempuniverseMapSelectIndex != this.universeMapSelectIndex);
                    this.setmapinfo(this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]);
                    break;
                }
                if (Control.IsKeyDown(327712) && !this.canvas.isShowSwitchScreen) {
                    this.universeMapSelectActionFlag = false;
                    switch (this.universeMapAreaData[this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][0]) {
                        case 0: {
                            this.nextMapIndex = this.universeMapAreaData[this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][1];
                            this.nextDoorIndex = 0;
                            if (this.isareaopen(this.canvas.mapm.maparea[this.nextMapIndex])) {
                                this.isdrawuniverselist = false;
                                if (this.canvas.rolem.currentplayernameindex == this.canvas.rolem.lastplayernameindex) {
                                    this.canvas.startSwitchScrren(0, (byte)5, 2);
                                } else {
                                    this.canvas.startSwitchScrren(0, (byte)5, 8);
                                }
                                this.canvas.createloadimage((byte)this.universeMapAreaData[this.maparea[this.nextMapIndex]][3]);
                                break;
                            }
                            this.canvas.gameUi.settishi("\u8be5\u533a\u57df\u672a\u5f00\u542f\uff0c\u65e0\u6cd5\u901a\u884c", (byte)0);
                            break;
                        }
                        case 1: {
                            this.nextMapIndex = this.universeMapAreaData[this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][1];
                            this.cityMapIndex = (byte)(this.nextMapIndex - 163);
                            this.citybuildnum = this.citybuildpos[this.cityMapIndex].length;
                            this.mapIndex = this.nextMapIndex;
                            if (this.isareaopen(this.canvas.mapm.maparea[this.nextMapIndex])) {
                                this.isdrawuniverselist = false;
                                this.canvas.gameUi.scroll.close();
                                this.iscityflushinfo = true;
                                this.canvas.startSwitchScrren(0, (byte)4, 0);
                                this.canvas.createloadimage((byte)this.universeMapAreaData[this.maparea[this.nextMapIndex]][3]);
                                break;
                            }
                            this.canvas.gameUi.settishi("\u8be5\u533a\u57df\u672a\u5f00\u542f\uff0c\u65e0\u6cd5\u901a\u884c", (byte)0);
                        }
                    }
                    break;
                }
                if (!Control.IsKeyDown(131072)) break;
                this.iscityflushinfo = true;
                this.universeMapState = 0;
                this.isuniverseflushinfo = true;
            }
        }
    }

    public void universeProcess() {
        switch (this.universeMapState) {
            case 0: {
                if (this.universeinfodata[this.universeMapIndex][0] > 1 && this.universewaittime > 0 && --this.universewaittime == 0) {
                    this.inituniverselist();
                }
                if (this.isuniverseflushinfo) {
                    this.setuniverseinfo(this.universeMapIndex);
                    this.isuniverseflushinfo = false;
                }
                if (!this.isdrawuniverselist) break;
                this.universelistProcess();
            }
        }
    }

    public void drawUniverseStaticUi(short frameindex, int x, int y) {
        for (int i = 0; i < this.universeMapFrames[frameindex].length; i += 4) {
            short moduleIndex = this.universeMapFrames[frameindex][i];
            this.canvas.drawModule(this.universeMapImage[this.universeMapModules[moduleIndex * 5]], this.universeMapFrames[frameindex][i + 1], (int)this.universeMapFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.universeMapFrames[frameindex][i + 3]], x, y, this.universeMapModules, (int)moduleIndex);
        }
    }

    public void drawUniverseMapSprite1(byte actionIndex, int x, int y) {
        short frameindex = this.universeMapActions[actionIndex][this.universeMapActionFrameIndex[actionIndex] + 1];
        short moduleIndex = 0;
        for (int i = 0; i < this.universeMapFrames[frameindex].length; i += 4) {
            moduleIndex = this.universeMapFrames[frameindex][i];
            this.canvas.drawModule(this.universeMapImage[this.universeMapModules[moduleIndex * 5]], this.universeMapFrames[frameindex][i + 1], (int)this.universeMapFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.universeMapFrames[frameindex][i + 3]], x, y, this.universeMapModules, (int)moduleIndex);
        }
        this.nextUniverseMapFrame1(actionIndex);
    }

    public void nextUniverseMapFrame1(byte actionIndex) {
        byte by = actionIndex;
        this.universeMapFrameDelay[by] = (byte)(this.universeMapFrameDelay[by] + 1);
        if (this.universeMapFrameDelay[by] == this.universeMapActions[actionIndex][this.universeMapActionFrameIndex[actionIndex]]) {
            this.universeMapFrameDelay[actionIndex] = 0;
            byte by2 = actionIndex;
            this.universeMapActionFrameIndex[by2] = (byte)(this.universeMapActionFrameIndex[by2] + 2);
            if (this.universeMapActionFrameIndex[actionIndex] == this.universeMapActions[actionIndex].length) {
                this.universeMapActionFrameIndex[actionIndex] = 0;
            }
        }
    }

    public void drawUniverseMapSprite(byte actionIndex, int x, int y) {
        short frameindex = this.universeMapActions[actionIndex][this.universeMapActionFrameIndex[actionIndex] + 1];
        short moduleIndex = 0;
        for (int i = 0; i < this.universeMapFrames[frameindex].length; i += 4) {
            moduleIndex = this.universeMapFrames[frameindex][i];
            this.canvas.drawModule(this.universeMapImage[this.universeMapModules[moduleIndex * 5]], this.universeMapFrames[frameindex][i + 1], (int)this.universeMapFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.universeMapFrames[frameindex][i + 3]], x, y, this.universeMapModules, (int)moduleIndex);
        }
        this.nextUniverseMapFrame(actionIndex);
    }

    public void nextUniverseMapFrame(byte actionIndex) {
        byte by = actionIndex;
        this.universeMapFrameDelay[by] = (byte)(this.universeMapFrameDelay[by] + 1);
        if (this.universeMapFrameDelay[by] == this.universeMapActions[actionIndex][this.universeMapActionFrameIndex[actionIndex]]) {
            this.universeMapFrameDelay[actionIndex] = 0;
            byte by2 = actionIndex;
            this.universeMapActionFrameIndex[by2] = (byte)(this.universeMapActionFrameIndex[by2] + 2);
            if (this.universeMapActionFrameIndex[actionIndex] / 2 == 2) {
                this.mapinfotype = 1;
            }
            if (this.universeMapActionFrameIndex[actionIndex] == this.universeMapActions[actionIndex].length) {
                this.universeMapActionFrameIndex[actionIndex] = 0;
                this.universeMapActionOver = true;
                this.universeMapActionState = 1;
            }
        }
    }

    public void setcityiconaction(byte index) {
        this.cityiconactionindex = index;
        this.drawcityiconstate = index;
        this.cityiconactionframeindex = 0;
        this.cityiconframedelay = 0;
        this.cityiconactionover = false;
    }

    public void drawcityiconsprite(byte actionIndex, int x, int y) {
        short frameindex = this.cityiconactions[actionIndex][this.cityiconactionframeindex + 1];
        short moduleIndex = 0;
        for (int i = 0; i < this.cityiconframes[frameindex].length; i += 4) {
            moduleIndex = this.cityiconframes[frameindex][i];
            this.canvas.drawModule(this.cityiconimages[this.cityiconmodules[moduleIndex * 5]], this.cityiconframes[frameindex][i + 1], (int)this.cityiconframes[frameindex][i + 2], 20, TRANS_TO_DG[this.cityiconframes[frameindex][i + 3]], x, y, this.cityiconmodules, (int)moduleIndex);
        }
        this.nextcityiconframe(actionIndex);
    }

    public void nextcityiconframe(byte actionIndex) {
        this.cityiconframedelay = (byte)(this.cityiconframedelay + 1);
        if (this.cityiconframedelay == this.cityiconactions[actionIndex][this.cityiconactionframeindex]) {
            this.cityiconframedelay = 0;
            this.cityiconactionframeindex = (byte)(this.cityiconactionframeindex + 2);
            if (this.cityiconactionframeindex == this.cityiconactions[actionIndex].length) {
                this.cityiconactionframeindex = 0;
                this.cityiconactionover = true;
            }
        }
    }

    public void initcityicon() {
        Util.tempData = new byte[10000];
        Util.position[0] = 0;
        Util.getStream(Util.tempData, "/data/cityicon.data", 0);
        this.cityiconmodules = Util.loadInfShort(Util.tempData, this.cityiconmodules, Util.position);
        this.cityiconframes = Util.loadInfShort(Util.tempData, this.cityiconframes, Util.position);
        this.cityiconactions = Util.loadInfShort(Util.tempData, this.cityiconactions, Util.position);
        Util.tempData = null;
        this.cityiconimages = new Image[4];
        for (int i = 0; i < this.cityiconimages.length; ++i) {
            this.cityiconimages[i] = Util.createImage("/ui/cityicon" + i + ".png");
        }
        this.drawcityiconstate = 0;
    }

    public void cleancityicon() {
        for (int i = 0; i < this.cityiconimages.length; ++i) {
            this.cityiconimages[i] = null;
        }
        this.cityiconimages = null;
        this.cityiconmodules = null;
        this.cityiconframes = null;
        this.cityiconactions = null;
    }

    public void initCityMap(byte index) {
        short[] tempMapData = this.allCityMapImgData[index];
        this.tempData = new byte[10000];
        this.position[0] = 0;
        Util.getStream(this.tempData, "/data/cityMap" + index + ".data", 0);
        this.cityMapModules = Util.loadInfShort(this.tempData, this.cityMapModules, this.position);
        this.cityMapFrames = Util.loadInfShort(this.tempData, this.cityMapFrames, this.position);
        this.cityMapActions = Util.loadInfShort(this.tempData, this.cityMapActions, this.position);
        this.cityMapImage = new Image[tempMapData.length];
        for (int i = 0; i < tempMapData.length; ++i) {
            this.cityMapImage[i] = Util.createImage("/map/" + tempMapData[i] + ".png");
        }
        this.cityMouse = Util.createImage("/map/101.png");
        this.cityInfoBG = Util.createImage("/map/140.png");
        this.tempData = null;
    }

    public void cleanCityMap() {
        for (int i = 0; i < this.cityMapImage.length; ++i) {
            this.cityMapImage[i] = null;
        }
        this.cityMapImage = null;
        this.cityMapModules = null;
        this.cityMapFrames = null;
        this.cityMapActions = null;
        this.cityInfoBG = null;
    }

    public void drawCityMapSprite(short actionIndex) {
        short frameindex = this.cityMapActions[actionIndex][this.cityMapActionFrameIndex + 1];
        short moduleIndex = 0;
        for (int i = 0; i < this.cityMapFrames[frameindex].length; i += 4) {
            moduleIndex = this.cityMapFrames[frameindex][i];
            this.canvas.drawModule(this.cityMapImage[this.cityMapModules[moduleIndex * 5]], this.cityMapFrames[frameindex][i + 1], (int)this.cityMapFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.cityMapFrames[frameindex][i + 3]], 320, 180, this.cityMapModules, (int)moduleIndex);
        }
        this.nextUiBackFrame(actionIndex);
    }

    public void nextUiBackFrame(short actionIndex) {
        this.cityMapFrameDelay = (byte)(this.cityMapFrameDelay + 1);
        if (this.cityMapFrameDelay == this.cityMapActions[actionIndex][this.cityMapActionFrameIndex]) {
            this.cityMapFrameDelay = 0;
            this.cityMapActionFrameIndex = (byte)(this.cityMapActionFrameIndex + 2);
            if (this.cityMapActionFrameIndex == this.cityMapActions[actionIndex].length) {
                this.cityMapActionFrameIndex = 0;
                this.cityMapActionOver = true;
            }
        }
    }

    public boolean checkCityLevel(byte index) {
        return this.cityMapLevel[index] < 2;
    }

    public void drawCityInfoBG() {
        int step_temp = 10;
        int pos_temp_x = 640 - step_temp;
        for (int i = 0; i < 15; ++i) {
            this.canvas.g.drawImage(this.cityInfoBG, pos_temp_x, 0, 0);
            pos_temp_x -= step_temp;
        }
    }

    public void drawCityMap(byte mapshowtype) {
        this.canvas.gameUi.drawStaticUiBack((short)0);
        this.canvas.drawFrame(this.cityMapImage, this.cityMapModules, this.cityMapFrames, this.cityMapLevel[this.cityMapIndex] * this.citybuildnum + this.currentbuildindex[this.cityBuildSelectIndex], 320, 180, 20);
        switch (this.drawcityiconstate) {
            case 0: {
                this.drawcityiconsprite(this.cityMapLevel[this.cityMapIndex], 150, 7);
                break;
            }
            case 1: {
                this.drawcityiconsprite((byte)(this.cityMapLevel[this.cityMapIndex] + 2), 150, 7);
                if (!this.cityiconactionover) break;
                this.setcityiconaction((byte)0);
            }
        }
        this.drawCityInfoBG();
        if (mapshowtype == 0) {
            this.canvas.gameUi.drawStaticUi((short)72, 490, 18);
            for (int i = 0; i < this.citybuildnum; ++i) {
                if (this.cityBuildSelectIndex == i) {
                    this.canvas.drawmodule(this.cityiconimages, this.cityiconmodules, this.cityicondata[this.cityMapIndex][1][i], 470, 45 + i * 44, 17, 0);
                    continue;
                }
                this.canvas.drawmodule(this.cityiconimages, this.cityiconmodules, this.cityicondata[this.cityMapIndex][0][i], 470, 45 + i * 44, 17, 0);
            }
            this.canvas.g.drawImage(this.cityMouse, 479, 50 + this.cityBuildSelectIndex * 44, 17);
            this.canvas.gameUi.drawcitynews();
            if (this.canvas.gameUi.currentmainmissionindex > -1) {
                if (this.canvas.gameUi.mainmissionstate == 2 && this.canvas.gameUi.missiondata0[this.canvas.gameUi.currentmainmissionindex][3] > -1 && this.canvas.gameUi.missiondata0[this.canvas.gameUi.currentmainmissionindex][1] == this.maparea[this.mapIndex]) {
                    this.canvas.gameUi.drawUiModule(this.missionicon[this.canvas.gameUi.mainmissionstate - 1], this.citybuildpos[this.cityMapIndex][this.canvas.gameUi.missiondata0[this.canvas.gameUi.currentmainmissionindex][3]][0], this.citybuildpos[this.cityMapIndex][this.canvas.gameUi.missiondata0[this.canvas.gameUi.currentmainmissionindex][3]][1] + this.canvas.publiccounter / 2 % 2, 33, 0);
                }
                if (this.canvas.gameUi.mainmissionstate == 1) {
                    if (this.canvas.gameUi.missiondata0[this.canvas.gameUi.currentmainmissionindex][2] > -1 && this.canvas.gameUi.missiondata0[this.canvas.gameUi.currentmainmissionindex][1] == this.maparea[this.mapIndex]) {
                        this.canvas.gameUi.drawUiModule(this.missionicon[this.canvas.gameUi.mainmissionstate - 1], this.citybuildpos[this.cityMapIndex][this.canvas.gameUi.missiondata0[this.canvas.gameUi.currentmainmissionindex][2]][0], this.citybuildpos[this.cityMapIndex][this.canvas.gameUi.missiondata0[this.canvas.gameUi.currentmainmissionindex][2]][1] + this.canvas.publiccounter / 2 % 2, 33, 0);
                    }
                    if (this.canvas.gameUi.missiondata0[this.canvas.gameUi.currentmainmissionindex][0] != this.maparea[this.mapIndex]) {
                        this.canvas.gameUi.drawUiModule(116, 472, 48 + this.canvas.publiccounter / 2 % 2, 17, 0);
                    }
                }
            }
            this.canvas.gameUi.drawCityUi();
            this.drawcitybuildinfo();
        } else if (mapshowtype == 1 && this.isshowrideloading) {
            GameUI gameUI = this.canvas.gameUi;
            this.canvas.gameUi.getClass();
            gameUI.drawminiloading(this.changeRideImageTime, 3);
        }
    }

    private void drawcitybuildinfo() {
        this.canvas.gameUi.drawlabel(this.canvas.gameUi.labelstr, 500, 10, this.canvas.gameUi.labelwordnum, 2);
        this.canvas.gameUi.scroll.drawscroll();
    }

    public void setCityArea() {
        this.currentcitybuildname = this.allcitybuildname[this.cityMapIndex];
        this.cityBuildSelectIndex = 1;
        this.setcitybuildinfo(this.currentcitybuildname[this.cityBuildSelectIndex]);
        this.currentbuildindex = this.allcitybuildindex[this.cityMapIndex];
        this.canvas.rolem.isKeyFirePressed = false;
    }

    public void cityMapKeyProcess() {
        if (this.isEventPlaying) {
            return;
        }
        this.canvas.gameUi.cityUiProcess();
        if (Control.IsKeyDown(65536)) {
            this.canvas.changeGameState(7);
            this.canvas.gameUi.changeUiState((byte)0);
            this.canvas.gameUi.initSystem();
        } else if (Control.IsKeyDown(131072)) {
            this.canvas.changeGameState(7);
            this.canvas.gameUi.changeUiState((byte)1);
            this.canvas.gameUi.initGameMenu();
        }
        if (Control.IsKeyDown(4100)) {
            this.cityBuildSelectIndex = (byte)(this.cityBuildSelectIndex - 1);
            this.cityBuildSelectIndex = (byte)(this.cityBuildSelectIndex < 0 ? this.citybuildnum - 1 : this.cityBuildSelectIndex);
            this.iscityflushinfo = true;
        } else if (Control.IsKeyDown(8448)) {
            this.cityBuildSelectIndex = (byte)(this.cityBuildSelectIndex + 1);
            this.cityBuildSelectIndex = this.cityBuildSelectIndex > this.citybuildnum - 1 ? (byte)0 : this.cityBuildSelectIndex;
            this.iscityflushinfo = true;
        } else if (Control.IsKeyDown(2048)) {
            if (this.canvas.rolem.yingxiangstate == 0) {
                this.canvas.rolem.iskeystar = true;
                this.canvas.changeGameState(7);
                this.canvas.gameUi.initmissionuibox();
                this.canvas.gameUi.changeUiState((byte)7);
            } else if (this.canvas.gameUi.currentmainmissionindex > -1) {
                this.canvas.gameUi.settishi("\u8be5\u533a\u57df\u65e0\u6cd5\u67e5\u770b\uff01", (byte)0);
            } else {
                this.canvas.gameUi.settishi("\u8be5\u529f\u80fd\u672a\u5f00\u542f\uff01", (byte)0);
            }
        } else if (Control.IsKeyDown(262176)) {
            this.canvas.rolem.isKeyFirePressed = true;
        }
    }

    public void cityMapProcess() {
        if (this.iscityflushinfo) {
            this.setcitybuildinfo(this.currentcitybuildname[this.cityBuildSelectIndex]);
            this.iscityflushinfo = false;
        }
        if (this.canvas.rolem.isKeyFirePressed) {
            this.canvas.rolem.isKeyFirePressed = false;
            this.canvas.gameUi.checkcityshownews(this.cityMapIndex, this.cityBuildSelectIndex);
            block0 : switch (this.cityMapIndex) {
                case 0: {
                    switch (this.currentbuildindex[this.cityBuildSelectIndex]) {
                        case 0: {
                            this.canvas.gameUi.cityIntroBoxState = 0;
                            this.canvas.gameUi.cityIntroBoxIndex = 0;
                            this.canvas.gameUi.setCityIntroBossData(this.cityMapIndex);
                            this.canvas.gameUi.changeUiState((byte)11);
                            this.canvas.changeGameState(7);
                            break;
                        }
                        case 1: {
                            this.canvas.gameUi.initshopdata();
                            this.canvas.gameUi.changeUiState((byte)13);
                            this.canvas.changeGameState(7);
                            break;
                        }
                        case 2: {
                            this.canvas.gameUi.mineUiState = 0;
                            this.canvas.gameUi.mineUiIndex = 0;
                            this.canvas.gameUi.mineUiSeletedIndex = 0;
                            this.canvas.gameUi.mineUiStartIndex = 0;
                            this.canvas.gameUi.mineIndex = 0;
                            this.canvas.gameUi.changeUiState((byte)15);
                            this.canvas.changeGameState(7);
                            break;
                        }
                        case 3: {
                            this.canvas.gameUi.initforgebox();
                            this.canvas.gameUi.changeUiState((byte)14);
                            this.canvas.changeGameState(7);
                            break;
                        }
                        case 4: {
                            this.canvas.gameUi.townhouseBoxState = 0;
                            this.canvas.gameUi.townhouseBoxSeletedIndex = 0;
                            this.canvas.gameUi.townhouseBoxStartIndex = 0;
                            this.canvas.gameUi.setcurrentmission(this.cityMapIndex);
                            this.canvas.gameUi.changeUiState((byte)12);
                            this.canvas.changeGameState(7);
                            break;
                        }
                        case 5: {
                            this.canvas.startSwitchScrren(0, (byte)10, 1);
                        }
                    }
                    break;
                }
                case 1: {
                    switch (this.currentbuildindex[this.cityBuildSelectIndex]) {
                        case 0: {
                            this.canvas.gameUi.initforgebox();
                            this.canvas.gameUi.changeUiState((byte)14);
                            this.canvas.changeGameState(7);
                            break;
                        }
                        case 1: {
                            this.canvas.gameUi.initshopdata();
                            this.canvas.gameUi.changeUiState((byte)13);
                            this.canvas.changeGameState(7);
                            break;
                        }
                        case 2: {
                            this.canvas.gameUi.cityIntroBoxState = 0;
                            this.canvas.gameUi.cityIntroBoxIndex = 0;
                            this.canvas.gameUi.setCityIntroBossData(this.cityMapIndex);
                            this.canvas.gameUi.changeUiState((byte)11);
                            this.canvas.changeGameState(7);
                            break;
                        }
                        case 3: {
                            this.canvas.gameUi.townhouseBoxState = 0;
                            this.canvas.gameUi.townhouseBoxSeletedIndex = 0;
                            this.canvas.gameUi.townhouseBoxStartIndex = 0;
                            this.canvas.gameUi.setcurrentmission(this.cityMapIndex);
                            this.canvas.gameUi.changeUiState((byte)12);
                            this.canvas.changeGameState(7);
                            break;
                        }
                        case 4: {
                            this.inituniverse();
                            this.canvas.startSwitchScrren(0, (byte)10, 1);
                        }
                    }
                    break;
                }
                case 2: {
                    switch (this.currentbuildindex[this.cityBuildSelectIndex]) {
                        case 0: {
                            this.canvas.gameUi.initforgebox();
                            this.canvas.gameUi.changeUiState((byte)14);
                            this.canvas.changeGameState(7);
                            break;
                        }
                        case 1: {
                            this.canvas.gameUi.mineUiState = 0;
                            this.canvas.gameUi.mineUiIndex = 0;
                            this.canvas.gameUi.mineUiSeletedIndex = 0;
                            this.canvas.gameUi.mineUiStartIndex = 0;
                            this.canvas.gameUi.mineIndex = 1;
                            this.canvas.gameUi.changeUiState((byte)15);
                            this.canvas.changeGameState(7);
                            break;
                        }
                        case 2: {
                            this.canvas.gameUi.cityIntroBoxState = 0;
                            this.canvas.gameUi.cityIntroBoxIndex = 0;
                            this.canvas.gameUi.setCityIntroBossData(this.cityMapIndex);
                            this.canvas.gameUi.changeUiState((byte)11);
                            this.canvas.changeGameState(7);
                            break;
                        }
                        case 3: {
                            this.canvas.gameUi.townhouseBoxState = 0;
                            this.canvas.gameUi.townhouseBoxSeletedIndex = 0;
                            this.canvas.gameUi.townhouseBoxStartIndex = 0;
                            this.canvas.gameUi.setcurrentmission(this.cityMapIndex);
                            this.canvas.gameUi.changeUiState((byte)12);
                            this.canvas.changeGameState(7);
                            break;
                        }
                        case 4: {
                            this.inituniverse();
                            this.canvas.startSwitchScrren(0, (byte)10, 1);
                        }
                    }
                    break;
                }
                case 3: {
                    switch (this.currentbuildindex[this.cityBuildSelectIndex]) {
                        case 0: {
                            this.canvas.gameUi.townhouseBoxState = 0;
                            this.canvas.gameUi.townhouseBoxSeletedIndex = 0;
                            this.canvas.gameUi.townhouseBoxStartIndex = 0;
                            this.canvas.gameUi.setcurrentmission(this.cityMapIndex);
                            this.canvas.gameUi.changeUiState((byte)12);
                            this.canvas.changeGameState(7);
                            break block0;
                        }
                        case 1: {
                            this.canvas.gameUi.initshopdata();
                            this.canvas.gameUi.changeUiState((byte)13);
                            this.canvas.changeGameState(7);
                            break block0;
                        }
                        case 2: {
                            this.canvas.gameUi.cityIntroBoxState = 0;
                            this.canvas.gameUi.cityIntroBoxIndex = 0;
                            this.canvas.gameUi.setCityIntroBossData(this.cityMapIndex);
                            this.canvas.gameUi.changeUiState((byte)11);
                            this.canvas.changeGameState(7);
                            break block0;
                        }
                        case 3: {
                            this.canvas.gameUi.initforgebox();
                            this.canvas.gameUi.changeUiState((byte)14);
                            this.canvas.changeGameState(7);
                            break block0;
                        }
                        case 4: {
                            this.inituniverse();
                            this.canvas.startSwitchScrren(0, (byte)10, 1);
                        }
                    }
                }
            }
        }
    }

    public void readminimapdata() {
        Util.readXls2DSHORT("/data/miniMap.data");
        this.minimapdata = Util.xls2DSHORTdata;
        Util.clearXls2DSHORT();
    }

    public void initminimap() {
        this.minimapindex = (byte)this.universeMapAreaData[this.maparea[this.mapIndex]][2];
        if (this.minimapindex > -1) {
            this.currentminimaplist = this.minimaplist[this.minimapindex];
            this.initBossstate();
        }
        this.weatherindex = (this.weatherstate & 1 << this.maparea[this.mapIndex]) != 0 ? (byte)this.universeMapAreaData[this.maparea[this.mapIndex]][4] : (byte)-1;
        if (this.weatherindex > -1) {
            this.initWeather();
        }
        this.showminimap((byte)5, 30);
    }

    public void initminimap(byte index1, byte index2) {
        this.universeMapIndex = index1;
        this.universeMapSelectIndex = index2;
        if (index2 > 5) {
            this.universeMapSelectIndex = 0;
            universeMapStartIndex = (byte)(index2 - 5);
        }
    }

    public void initBossstate() {
        this.bossstate = 0;
        for (int i = 0; i < this.currentminimaplist.length; ++i) {
            if (!this.canvas.rolem.isbossarea(this.currentminimaplist[i])) continue;
            this.bossstate |= 1 << i;
        }
    }

    public void showminimap(byte state, int time) {
        this.showtime = time;
        this.minimapw = 0;
        this.minimaph = 0;
        for (int i = 0; i < this.currentminimaplist.length; ++i) {
            if (this.currentminimaplist[i] == this.mapIndex) {
                this.minimapx = this.minimapdata[this.currentminimaplist[i]][0];
                this.minimapy = this.minimapdata[this.currentminimaplist[i]][1];
            }
            if (this.minimapdata[this.currentminimaplist[i]][2] > this.minimapw) {
                this.minimapw = this.minimapdata[this.currentminimaplist[i]][2];
            }
            if (this.minimapdata[this.currentminimaplist[i]][3] <= this.minimaph) continue;
            this.minimaph = this.minimapdata[this.currentminimaplist[i]][3];
        }
        this.minimapw = (short)(this.minimapw + 120);
        this.minimaph = (short)(this.minimaph + 80);
        this.minimapstate = state;
    }

    public void minimapKeyProcess() {
        if (Control.IsKeyDown(4100) || Control.IsKeyHold(4100)) {
            this.minimapy = (short)(this.minimapy - 20);
            this.minimapy = this.minimapy < 0 ? (short)0 : this.minimapy;
            this.showtime = -1;
        } else if (Control.IsKeyDown(8448) || Control.IsKeyHold(8448)) {
            this.minimapy = (short)(this.minimapy + 20);
            this.minimapy = this.minimapy > this.minimaph ? this.minimaph : this.minimapy;
            this.showtime = -1;
        } else if (Control.IsKeyDown(16400) || Control.IsKeyHold(16400)) {
            this.minimapx = (short)(this.minimapx - 20);
            this.minimapx = this.minimapx < 0 ? (short)0 : this.minimapx;
            this.showtime = -1;
        } else if (Control.IsKeyDown(32832) || Control.IsKeyHold(32832)) {
            this.minimapx = (short)(this.minimapx + 20);
            this.minimapx = this.minimapx > this.minimapw ? this.minimapw : this.minimapx;
            this.showtime = -1;
        }
        if (Control.IsKeyDown(131073)) {
            this.showtime = -1;
            this.canvas.gamestate = this.minimapstate;
        }
    }

    public void minimapProcess() {
        if (this.showtime > 0 && --this.showtime == 0) {
            this.canvas.changeGameState(5);
        }
    }

    public void drawminimap(Role player) {
        int i;
        this.canvas.drawFight();
        this.canvas.gameUi.drawUiBack();
        this.minimapy = (short)50;
        this.minimapx = (short)40;
        this.canvas.g.translate(320 - this.minimapx - 32, 180 - this.minimapy - 15);
        for (i = 0; i < this.currentminimaplist.length; ++i) {
            if (this.minimapdata[this.currentminimaplist[i]][4] > -1) {
                this.canvas.fillRect(0xFFFFFF, this.minimapdata[this.currentminimaplist[i]][0] + this.minimapdata[this.currentminimaplist[i]][4], this.minimapdata[this.currentminimaplist[i]][1] - 10, 3, 10);
                this.canvas.fillRect(0xFFFFFF, this.minimapdata[this.currentminimaplist[i]][0] + this.minimapdata[this.currentminimaplist[i]][4] + 1, this.minimapdata[this.currentminimaplist[i]][1] - 10, 1, 10);
            }
            if (this.minimapdata[this.currentminimaplist[i]][5] > -1) {
                this.canvas.fillRect(0xFFFFFF, this.minimapdata[this.currentminimaplist[i]][0] + this.minimapdata[this.currentminimaplist[i]][5], this.minimapdata[this.currentminimaplist[i]][1] + this.minimapdata[this.currentminimaplist[i]][3], 3, 10);
                this.canvas.fillRect(0xFFFFFF, this.minimapdata[this.currentminimaplist[i]][0] + this.minimapdata[this.currentminimaplist[i]][5] + 1, this.minimapdata[this.currentminimaplist[i]][1] + this.minimapdata[this.currentminimaplist[i]][3], 1, 10);
            }
            if (this.minimapdata[this.currentminimaplist[i]][6] > -1) {
                this.canvas.fillRect(0xFFFFFF, this.minimapdata[this.currentminimaplist[i]][0] - 10, this.minimapdata[this.currentminimaplist[i]][1] + this.minimapdata[this.currentminimaplist[i]][6], 10, 3);
                this.canvas.fillRect(0xFFFFFF, this.minimapdata[this.currentminimaplist[i]][0] - 10, this.minimapdata[this.currentminimaplist[i]][1] + this.minimapdata[this.currentminimaplist[i]][6] + 1, 10, 1);
            }
            if (this.minimapdata[this.currentminimaplist[i]][7] <= -1) continue;
            this.canvas.fillRect(0xFFFFFF, this.minimapdata[this.currentminimaplist[i]][0] + this.minimapdata[this.currentminimaplist[i]][2], this.minimapdata[this.currentminimaplist[i]][1] + this.minimapdata[this.currentminimaplist[i]][7], 10, 3);
            this.canvas.fillRect(0xFFFFFF, this.minimapdata[this.currentminimaplist[i]][0] + this.minimapdata[this.currentminimaplist[i]][2], this.minimapdata[this.currentminimaplist[i]][1] + this.minimapdata[this.currentminimaplist[i]][7] + 1, 10, 1);
        }
        for (i = 0; i < this.currentminimaplist.length; ++i) {
            if (this.canvas.gameUi.mainmissionstate < 2 && this.canvas.gameUi.missiondata0[this.canvas.gameUi.currentmainmissionindex][9] == this.currentminimaplist[i]) {
                this.canvas.fillRect(0xFFFFFF, this.minimapdata[this.currentminimaplist[i]][0], this.minimapdata[this.currentminimaplist[i]][1], this.minimapdata[this.currentminimaplist[i]][2], this.minimapdata[this.currentminimaplist[i]][3]);
                this.canvas.fillRect(16656935, this.minimapdata[this.currentminimaplist[i]][0] + 2, this.minimapdata[this.currentminimaplist[i]][1] + 2, this.minimapdata[this.currentminimaplist[i]][2] - 4, this.minimapdata[this.currentminimaplist[i]][3] - 4);
            } else if (this.canvas.rolem.savestate[this.currentminimaplist[i]] == 1) {
                this.canvas.fillRect(0xFFFFFF, this.minimapdata[this.currentminimaplist[i]][0], this.minimapdata[this.currentminimaplist[i]][1], this.minimapdata[this.currentminimaplist[i]][2], this.minimapdata[this.currentminimaplist[i]][3]);
                this.canvas.fillRect(16438272, this.minimapdata[this.currentminimaplist[i]][0] + 2, this.minimapdata[this.currentminimaplist[i]][1] + 2, this.minimapdata[this.currentminimaplist[i]][2] - 4, this.minimapdata[this.currentminimaplist[i]][3] - 4);
            } else {
                this.canvas.fillRect(2551039, this.minimapdata[this.currentminimaplist[i]][0], this.minimapdata[this.currentminimaplist[i]][1], this.minimapdata[this.currentminimaplist[i]][2], this.minimapdata[this.currentminimaplist[i]][3]);
                this.canvas.fillRect(5345535, this.minimapdata[this.currentminimaplist[i]][0] + 2, this.minimapdata[this.currentminimaplist[i]][1] + 2, this.minimapdata[this.currentminimaplist[i]][2] - 4, this.minimapdata[this.currentminimaplist[i]][3] - 4);
            }
            for (int j = 0; j < this.canvas.gameUi.currentmissionindex.length; ++j) {
                if (this.canvas.gameUi.currentmissionindex[j] <= -1 || this.canvas.gameUi.missiondata1[this.canvas.gameUi.currentmissionindex[j]][7] != this.currentminimaplist[i]) continue;
                this.canvas.fillRect(0xFFFFFF, this.minimapdata[this.currentminimaplist[i]][0], this.minimapdata[this.currentminimaplist[i]][1], this.minimapdata[this.currentminimaplist[i]][2], this.minimapdata[this.currentminimaplist[i]][3]);
                this.canvas.fillRect(6815490, this.minimapdata[this.currentminimaplist[i]][0] + 2, this.minimapdata[this.currentminimaplist[i]][1] + 2, this.minimapdata[this.currentminimaplist[i]][2] - 4, this.minimapdata[this.currentminimaplist[i]][3] - 4);
            }
            if ((this.bossstate & 1 << i) == 0) continue;
            this.canvas.gameUi.drawFightUiModule(0, this.minimapdata[this.currentminimaplist[i]][0] + (this.minimapdata[this.currentminimaplist[i]][2] >> 1), this.minimapdata[this.currentminimaplist[i]][1] + (this.minimapdata[this.currentminimaplist[i]][3] >> 1), 3, 0);
        }
        int playerx = 0;
        int playery = 0;
        for (int i2 = 0; i2 < this.currentminimaplist.length; ++i2) {
            if (this.currentminimaplist[i2] != this.mapIndex) continue;
            playerx = this.minimapdata[this.currentminimaplist[i2]][0] + player.spritex / (this.mapW / 32);
            playery = this.minimapdata[this.currentminimaplist[i2]][1] + (player.spritez - 200) / 10;
        }
        this.drawPlayerInmap(playerx, playery);
        this.canvas.g.translate(-(320 - this.minimapx - 32), -(180 - this.minimapy - 15));
        this.canvas.gameUi.drawStaticUi((short)63);
        this.canvas.fillRect(0xFFFFFF, 230, 282, 32, 16);
        this.canvas.fillRect(16656935, 231, 283, 30, 14);
        this.canvas.drawColorString("\u4e3b\u7ebf", 267, 290, 6, 0xFFFFFF, 0);
        this.canvas.fillRect(0xFFFFFF, 230, 309, 32, 16);
        this.canvas.fillRect(6815490, 231, 310, 30, 14);
        this.canvas.drawColorString("\u652f\u7ebf", 267, 317, 6, 0xFFFFFF, 0);
        this.canvas.fillRect(0xFFFFFF, 344, 282, 32, 16);
        this.canvas.fillRect(16438272, 345, 283, 30, 14);
        this.canvas.drawColorString("\u5b58\u6863", 381, 290, 6, 0xFFFFFF, 0);
        this.canvas.gameUi.drawFightUiModule(0, 353, 317, 6, 0);
        this.canvas.drawColorString("\u5934\u76ee", 381, 317, 6, 0xFFFFFF, 0);
        this.canvas.drawColorString(this.mapinfo[this.maparea[this.mapIndex]][0], 320, 46, 3, 0xFFFFFF, 0);
        if (this.canvas.publiccounter / 10 % 2 == 0) {
            this.canvas.drawColorString("\u70b9\u51fb\u6b64\u5904\u8fd4\u56de", 320, 253, 33, 16770139, 1458234);
        }
    }

    public void drawPlayerInmap(int x, int y) {
        this.canvas.g.setColor(16712278);
        this.canvas.g.drawArc(x -= 2, y -= 2, 4, 4, 0, 360);
        this.canvas.g.drawArc(x - this.minimaptime, y - this.minimaptime, 4 + this.minimaptime * 2, 4 + this.minimaptime * 2, 0, 360);
        this.minimaptime = ++this.minimaptime == 10 ? 0 : this.minimaptime;
    }

    public void readEventdata() {
        Util.readEventData("/data/e" + this.mapIndex + ".data");
        this.getEventdata();
        Util.clearEventdata();
    }

    public void getEventdata() {
        this.talksdata = Util.talksdata;
        this.npcname = Util.npcname;
        this.npcData = Util.npcdata;
        this.chestData = Util.chestdata;
        this.doorData = Util.doordata;
        this.eventdata = Util.eventdata;
        this.addeventdata();
    }

    private void addeventdata() {
        if (this.canvas.gameUi.currentmainmissionindex == 0 && this.autoeventstate == 0) {
            int k;
            int j;
            int i;
            short[][][] tempdata = new short[this.eventdata.length + 1][][];
            for (i = 0; i < this.eventdata.length; ++i) {
                tempdata[i] = new short[this.eventdata[i].length][];
                for (j = 0; j < this.eventdata[i].length; ++j) {
                    tempdata[i][j] = new short[this.eventdata[i][j].length];
                    for (k = 0; k < this.eventdata[i][j].length; ++k) {
                        tempdata[i][j][k] = this.eventdata[i][j][k];
                    }
                }
            }
            tempdata[i] = new short[this.autoeventdata.length][];
            for (j = 0; j < tempdata[i].length; ++j) {
                tempdata[i][j] = new short[this.autoeventdata[j].length];
                for (k = 0; k < tempdata[i][j].length; ++k) {
                    tempdata[i][j][k] = this.autoeventdata[j][k];
                }
            }
            this.eventdata = tempdata;
        }
    }

    public void startEvent(Role player, int index) {
        this.eventindex = (byte)index;
        this.eventstep = 0;
        this.isEventPlaying = true;
        this.iseventAotoCamera = true;
        this.canvas.eventBackH = 0;
        this.nextStep(player);
    }

    private void eventfinish() {
        this.eventhappend(this.eventindex);
        this.isEventPlaying = false;
        this.isEventFinished = true;
        this.lastEventIndex = this.eventindex;
        if (this.isRideDown) {
            this.canvas.rolem.setPlayerState(this.savestate);
            this.canvas.rolem.setPlayerRideOn();
            this.isRideDown = false;
        }
    }

    private void comebackevent() {
        for (int i = 0; i < 2; i = (int)((byte)(i + 1))) {
            short s = this.comebackeventdata[i][0];
            this.eventState[s] = (short)(this.eventState[s] ^ 1 << this.comebackeventdata[i][1]);
        }
    }

    private void eventhappend(byte eindex) {
        short s = this.mapIndex;
        this.eventState[s] = (short)(this.eventState[s] | 1 << eindex);
    }

    private boolean iseventhappend(int i) {
        return (this.eventState[this.mapIndex] & 1 << i) != 0;
    }

    private boolean isautoeventhappend(int i) {
        return (this.autoeventstate & 1 << i) != 0;
    }

    public void checkEvent(Role player) {
        block14: {
            block13: {
                this.isEventFinished = false;
                if (this.eventhappendcounter <= 0) break block13;
                if (this.canvas.rolem.noitem()) {
                    this.eventhappendcounter = 1;
                }
                if ((this.eventhappendcounter = (byte)(this.eventhappendcounter - 1)) != 0) break block14;
                this.eventhappendcounter = 0;
                this.startEvent(player, this.eventhappendindex);
                break block14;
            }
            for (int i = 0; i < this.eventdata.length; ++i) {
                if (this.iseventhappend(i) || this.eventdata[i].length <= 0) continue;
                switch (this.eventdata[i][0][0]) {
                    case 51: {
                        if (this.lastEventIndex != this.eventdata[i][0][1] || !this.canvas.rolem.isBossDead((byte)this.eventdata[i][0][2])) break;
                        this.eventhappendindex = i;
                        this.eventhappendcounter = (byte)100;
                        break;
                    }
                    case 10: {
                        if (this.lastEventIndex != this.eventdata[i][0][1] || !this.canvas.rolem.isAllEnemyDead()) break;
                        this.eventhappendindex = i;
                        this.eventhappendcounter = (byte)100;
                        break;
                    }
                    case 37: {
                        if (!this.canvas.rolem.checkDoor((byte)this.eventdata[i][0][1])) break;
                        this.startEvent(player, i);
                        break;
                    }
                    case 26: {
                        if (!this.isEventContinue || this.mapIndex != this.eventcontinuemapindex || i != this.eventcontinueindex) break;
                        this.isEventContinue = false;
                        this.startEvent(player, i);
                        break;
                    }
                    case 24: {
                        if (this.canvas.gameUi.currentmainmissionindex != this.eventdata[i][0][1] || this.canvas.gameUi.mainmissionstate != 2) break;
                        this.startEvent(player, i);
                        break;
                    }
                    case 1: {
                        if (this.mapIndex != this.eventdata[i][0][1]) break;
                        this.startEvent(player, i);
                        break;
                    }
                    case 0: {
                        if (this.mapIndex != this.eventdata[i][0][1] || this.currentbuildindex[this.cityBuildSelectIndex] != this.eventdata[i][0][2] || !this.canvas.rolem.isKeyFirePressed) break;
                        this.startEvent(player, i);
                        break;
                    }
                    case 70: {
                        if (!this.canvas.rolem.iscloseNPC(this.eventdata[i][0][1]) || !this.canvas.rolem.isKeyFirePressed) break;
                        this.startEvent(player, i);
                        player.setAction((byte)0, (short)0, (short)0, 0);
                        break;
                    }
                    case 75: {
                        if (this.eventdata[i][0][1] != this.cityMapIndex || this.eventdata[i][0][2] != this.cityMapLevel[this.cityMapIndex]) break;
                        this.startEvent(player, i);
                    }
                }
                if (!this.isEventPlaying && !this.isEventFinished) continue;
                this.canvas.rolem.isKeyFirePressed = false;
                break;
            }
        }
    }

    public void eventProcess(Role player) {
        block122: {
            block121: {
                if (this.canvas.gameUi.isshowtishi) {
                    return;
                }
                if (!this.isEventPlaying) break block121;
                block0 : switch (this.eventdata[this.eventindex][this.eventstep][0]) {
                    case 106: {
                        this.canvas.gameUi.getClass();
                        if (this.changeRideImageTime == 3) {
                            this.canvas.rolem.setPlayerState((byte)0);
                            this.canvas.rolem.setPlayerRideDown();
                            this.isshowrideloading = false;
                            this.nextStep(player);
                            break;
                        }
                        this.changeRideImage(this.canvas.rolem.currentRideIndex, this.tempcurrentRideIndex);
                        break;
                    }
                    case 105: {
                        this.canvas.gameUi.getClass();
                        if (this.changeRideImageTime == 3) {
                            this.canvas.rolem.setPlayerState((byte)(this.tempcurrentRideIndex + 1));
                            this.canvas.rolem.setPlayerRideOn(this.tempcurrentRideIndex);
                            this.isshowrideloading = false;
                            this.nextStep(player);
                            break;
                        }
                        this.changeRideImage(this.tempcurrentRideIndex, this.canvas.rolem.lastRideIndex);
                        break;
                    }
                    case 60: {
                        this.nextStep(player);
                        break;
                    }
                    case 100: {
                        this.eventstep = (byte)(this.eventstep + 1);
                        this.nextStep(player);
                        break;
                    }
                    case 83: {
                        if (player.isdrawface) break;
                        this.nextStep(player);
                        break;
                    }
                    case 72: {
                        this.nextStep(player);
                        break;
                    }
                    case 56: {
                        switch (this.eventdata[this.eventindex][this.eventstep][4]) {
                            case 0: {
                                if (this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].spritez > this.eventdata[this.eventindex][this.eventstep][3]) break;
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].walkzspeed = 0;
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].zSpeed = 0;
                                this.canvas.c.stopShake();
                                player.setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                                break block0;
                            }
                            case 1: {
                                if (this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].spritez < this.eventdata[this.eventindex][this.eventstep][3]) break;
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].walkzspeed = 0;
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].zSpeed = 0;
                                this.canvas.c.stopShake();
                                player.setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                                break block0;
                            }
                            case 2: {
                                if (this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].spritex > this.eventdata[this.eventindex][this.eventstep][2]) break;
                                player.setAction((byte)0, (short)0, (short)0, 0);
                                this.canvas.c.stopShake();
                                this.nextStep(player);
                                break block0;
                            }
                            case 3: {
                                if (this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].spritex < this.eventdata[this.eventindex][this.eventstep][2]) break;
                                player.setAction((byte)0, (short)0, (short)0, 0);
                                this.canvas.c.stopShake();
                                this.nextStep(player);
                            }
                        }
                        break;
                    }
                    case 52: {
                        if (this.canvas.eventBackH < 20) break;
                        this.canvas.eventBackState = (byte)3;
                        this.nextStep(player);
                        break;
                    }
                    case 53: {
                        if (this.canvas.eventBackH != 0) break;
                        this.canvas.eventBackState = 0;
                        this.nextStep(player);
                        break;
                    }
                    case 77: {
                        switch (this.eventdata[this.eventindex][this.eventstep][4]) {
                            case 0: {
                                if (this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][1]].spritez > this.eventdata[this.eventindex][this.eventstep][3]) break;
                                this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][1]].setAction((byte)0, (byte)0, (byte)0);
                                this.nextStep(player);
                                break block0;
                            }
                            case 1: {
                                if (this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][1]].spritez < this.eventdata[this.eventindex][this.eventstep][3]) break;
                                this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][1]].setAction((byte)0, (byte)0, (byte)0);
                                this.nextStep(player);
                                break block0;
                            }
                            case 2: {
                                if (this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][1]].spritex > this.eventdata[this.eventindex][this.eventstep][2]) break;
                                this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][1]].setAction((byte)0, (byte)0, (byte)0);
                                this.nextStep(player);
                                break block0;
                            }
                            case 3: {
                                if (this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][1]].spritex < this.eventdata[this.eventindex][this.eventstep][2]) break;
                                this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][1]].setAction((byte)0, (byte)0, (byte)0);
                                this.nextStep(player);
                            }
                        }
                        break;
                    }
                    case 86: {
                        switch (this.eventdata[this.eventindex][this.eventstep][3]) {
                            case 0: {
                                if (this.canvas.rolem.bossbox[0].spritez > this.eventdata[this.eventindex][this.eventstep][2]) break;
                                this.canvas.rolem.bossbox[0].setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                                break block0;
                            }
                            case 1: {
                                if (this.canvas.rolem.bossbox[0].spritez < this.eventdata[this.eventindex][this.eventstep][2]) break;
                                this.canvas.rolem.bossbox[0].setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                                break block0;
                            }
                            case 2: {
                                if (this.canvas.rolem.bossbox[0].spritex > this.eventdata[this.eventindex][this.eventstep][1]) break;
                                this.canvas.rolem.bossbox[0].setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                                break block0;
                            }
                            case 3: {
                                if (this.canvas.rolem.bossbox[0].spritex < this.eventdata[this.eventindex][this.eventstep][1]) break;
                                this.canvas.rolem.bossbox[0].setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                            }
                        }
                        break;
                    }
                    case 91: {
                        switch (this.eventdata[this.eventindex][this.eventstep][3]) {
                            case 0: {
                                if (player.spritez > this.eventdata[this.eventindex][this.eventstep][2]) break;
                                player.setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                                break block0;
                            }
                            case 1: {
                                if (player.spritez < this.eventdata[this.eventindex][this.eventstep][2]) break;
                                player.setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                                break block0;
                            }
                            case 2: {
                                if (player.spritex > this.eventdata[this.eventindex][this.eventstep][1]) break;
                                player.setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                                break block0;
                            }
                            case 3: {
                                if (player.spritex < this.eventdata[this.eventindex][this.eventstep][1]) break;
                                player.setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                            }
                        }
                        break;
                    }
                    case 3: {
                        switch (this.eventdata[this.eventindex][this.eventstep][3]) {
                            case 0: {
                                if (player.spritez > this.eventdata[this.eventindex][this.eventstep][2]) break;
                                player.setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                                break block0;
                            }
                            case 1: {
                                if (player.spritez < this.eventdata[this.eventindex][this.eventstep][2]) break;
                                player.setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                                break block0;
                            }
                            case 2: {
                                if (player.spritex > this.eventdata[this.eventindex][this.eventstep][1]) break;
                                player.setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                                break block0;
                            }
                            case 3: {
                                if (player.spritex < this.eventdata[this.eventindex][this.eventstep][1]) break;
                                player.setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                            }
                        }
                        break;
                    }
                    case 47: {
                        switch (player.hdirection) {
                            case -1: {
                                if (player.spritex > this.eventdata[this.eventindex][this.eventstep][1]) break;
                                player.setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                                break block0;
                            }
                            case 1: {
                                if (player.spritex < this.eventdata[this.eventindex][this.eventstep][1]) break;
                                player.setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                            }
                        }
                        break;
                    }
                    case 57: {
                        this.nextStep(player);
                        break;
                    }
                    case 102: {
                        this.nextStep(player);
                        break;
                    }
                    case 8: {
                        this.nextStep(player);
                        break;
                    }
                    case 81: {
                        this.nextStep(player);
                        break;
                    }
                    case 9: {
                        this.nextStep(player);
                        break;
                    }
                    case 74: {
                        this.nextStep(player);
                        break;
                    }
                    case 11: {
                        switch (this.eventdata[this.eventindex][this.eventstep][3]) {
                            case 0: {
                                if (this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][4]].spritez > this.eventdata[this.eventindex][this.eventstep][2]) break;
                                for (int i = 0; i < 4; ++i) {
                                    if (this.eventdata[this.eventindex][this.eventstep][4 + i] <= -1) continue;
                                    this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setAction((byte)0, (short)0, (short)0, 0);
                                }
                                this.nextStep(player);
                                break block0;
                            }
                            case 1: {
                                if (this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][4]].spritez < this.eventdata[this.eventindex][this.eventstep][2]) break;
                                for (int i = 0; i < 4; ++i) {
                                    if (this.eventdata[this.eventindex][this.eventstep][4 + i] <= -1) continue;
                                    this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setAction((byte)0, (short)0, (short)0, 0);
                                }
                                this.nextStep(player);
                                break block0;
                            }
                            case 2: {
                                if (this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][4]].spritex > this.eventdata[this.eventindex][this.eventstep][1]) break;
                                for (int i = 0; i < 4; ++i) {
                                    if (this.eventdata[this.eventindex][this.eventstep][4 + i] <= -1) continue;
                                    this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setAction((byte)0, (short)0, (short)0, 0);
                                }
                                this.nextStep(player);
                                break block0;
                            }
                            case 3: {
                                if (this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][4]].spritex < this.eventdata[this.eventindex][this.eventstep][1]) break;
                                for (int i = 0; i < 4; ++i) {
                                    if (this.eventdata[this.eventindex][this.eventstep][4 + i] <= -1) continue;
                                    this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setAction((byte)0, (short)0, (short)0, 0);
                                }
                                this.nextStep(player);
                            }
                        }
                        break;
                    }
                    case 13: {
                        this.nextStep(player);
                        break;
                    }
                    case 16: {
                        this.eventtime = (short)(this.eventtime - 1);
                        if (this.eventtime > 0) break;
                        this.nextStep(player);
                        break;
                    }
                    case 17: {
                        if (!this.canvas.rolem.checkDoor()) break;
                        this.nextStep(player);
                        break;
                    }
                    case 29: {
                        this.nextStep(player);
                        break;
                    }
                    case 31: {
                        switch (this.eventdata[this.eventindex][this.eventstep][2]) {
                            case 0: {
                                if (player.actionState != 0) break;
                                this.nextStep(player);
                                break block0;
                            }
                            case 1: {
                                this.nextStep(player);
                            }
                        }
                        break;
                    }
                    case 32: {
                        switch (this.eventdata[this.eventindex][this.eventstep][3]) {
                            case 0: {
                                if (this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].actionState != 0) break;
                                this.nextStep(player);
                                break block0;
                            }
                            case 1: {
                                this.nextStep(player);
                            }
                        }
                        break;
                    }
                    case 50: {
                        switch (this.eventdata[this.eventindex][this.eventstep][3]) {
                            case 0: {
                                if (this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].actionState != 0) break;
                                this.nextStep(player);
                                break block0;
                            }
                            case 1: {
                                this.nextStep(player);
                            }
                        }
                        break;
                    }
                    case 69: {
                        switch (this.eventdata[this.eventindex][this.eventstep][3]) {
                            case 0: {
                                if (this.canvas.rolem.fplayerbox[this.eventdata[this.eventindex][this.eventstep][1]].actionState != 0) break;
                                this.nextStep(player);
                                break block0;
                            }
                            case 1: {
                                this.nextStep(player);
                            }
                        }
                        break;
                    }
                    case 33: {
                        switch (this.eventdata[this.eventindex][this.eventstep][3]) {
                            case 0: {
                                if (this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][4]].spritez > this.eventdata[this.eventindex][this.eventstep][2]) break;
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][4]].setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                                break block0;
                            }
                            case 1: {
                                if (this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][4]].spritez < this.eventdata[this.eventindex][this.eventstep][2]) break;
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][4]].setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                                break block0;
                            }
                            case 2: {
                                if (this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][4]].spritex > this.eventdata[this.eventindex][this.eventstep][1]) break;
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][4]].setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                                break block0;
                            }
                            case 3: {
                                if (this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][4]].spritex < this.eventdata[this.eventindex][this.eventstep][1]) break;
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][4]].setAction((byte)0, (short)0, (short)0, 0);
                                this.nextStep(player);
                            }
                        }
                        break;
                    }
                    case 34: {
                        this.nextStep(player);
                        break;
                    }
                    case 35: {
                        this.nextStep(player);
                        break;
                    }
                    case 36: {
                        this.nextStep(player);
                        break;
                    }
                    case 82: {
                        this.nextStep(player);
                        break;
                    }
                    case 38: {
                        this.nextStep(player);
                        break;
                    }
                    case 39: {
                        this.nextStep(player);
                        break;
                    }
                    case 42: {
                        this.nextStep(player);
                        break;
                    }
                    case 61: {
                        if (this.canvas.gameUi.isshowmissionreward) break;
                        this.nextStep(player);
                        break;
                    }
                    case 92: {
                        if (this.canvas.rolem.fplayerbox[this.eventdata[this.eventindex][this.eventstep][2]].isdrawface) break;
                        this.nextStep(player);
                        break;
                    }
                    case 93: {
                        if (this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][2]].isdrawface) break;
                        this.nextStep(player);
                        break;
                    }
                    case 96: {
                        if (this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][2]].isdrawface) break;
                        this.nextStep(player);
                        break;
                    }
                    case 80: {
                        if (this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][2]].isdrawface) break;
                        this.nextStep(player);
                        break;
                    }
                    case 98: {
                        switch (this.eventdata[this.eventindex][this.eventstep][2]) {
                            case 0: {
                                if (this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].aliveState == 0) {
                                    this.nextStep(player);
                                    break block0;
                                }
                                break block122;
                            }
                            case 1: {
                                this.nextStep(player);
                            }
                        }
                    }
                }
                break block122;
            }
            this.checkEvent(player);
            if (!this.isEventPlaying && !this.isEventFinished && this.eventhappendcounter == 0) {
                this.canvas.rolem.checkDoor();
                if (this.canvas.gamestate == 5) {
                    this.canvas.rolem.checkchuansong();
                }
            }
        }
    }

    public void changeRideImage(byte currentindex, byte lastindex) {
        switch (this.changeRideImageTime) {
            case 0: {
                this.canvas.rolem.clearRide(lastindex);
                break;
            }
            case 1: {
                this.canvas.rolem.initRide(currentindex);
                break;
            }
            case 2: {
                System.gc();
            }
        }
        ++this.changeRideImageTime;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void nextStep(Role player) {
        this.eventstep = (byte)(this.eventstep + 1);
        if (this.eventstep == this.eventdata[this.eventindex].length) {
            this.eventfinish();
            return;
        }
        switch (this.eventdata[this.eventindex][this.eventstep][0]) {
            case 110: {
                this.isFreeMove = this.eventdata[this.eventindex][this.eventstep][1] == 1;
                this.nextStep(player);
                return;
            }
            case 109: {
                this.canvas.rolem.flushEnemyProcess(this.eventdata[this.eventindex][this.eventstep][1]);
                this.nextStep(player);
                return;
            }
            case 107: {
                this.canvas.rolem.isgudingflushenemy = true;
                this.nextStep(player);
                return;
            }
            case 108: {
                this.canvas.rolem.isgudingflushenemy = false;
                this.nextStep(player);
                return;
            }
            case 106: {
                this.isshowrideloading = true;
                this.changeRideImageTime = 0;
                return;
            }
            case 105: {
                this.tempcurrentRideIndex = (byte)this.eventdata[this.eventindex][this.eventstep][1];
                this.changeRideImageTime = 0;
                this.isshowrideloading = true;
                return;
            }
            case 104: {
                if (this.canvas.gameUi.itemTotal[0] > 0) {
                    this.nextStep(player);
                    return;
                }
                this.canvas.gameUi.addItem((byte)0, (byte)1);
                this.nextStep(player);
                return;
            }
            case 103: {
                this.autoeventstate = 1;
                this.nextStep(player);
                return;
            }
            case 101: {
                this.canvas.gameUi.savegamestate = this.canvas.gamestate;
                this.canvas.gameUi.saveGame();
                this.nextStep(player);
                return;
            }
            case 100: {
                this.eventstep = (byte)(this.eventstep + 1);
                this.nextStep(player);
                return;
            }
            case 99: {
                if (this.canvas.rolem.roleState > 0) {
                    this.isRideDown = true;
                    this.savestate = this.canvas.rolem.roleState;
                    this.canvas.rolem.setPlayerRideDown();
                    this.canvas.rolem.setPlayerState((byte)0);
                }
                this.nextStep(player);
                return;
            }
            case 98: {
                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].life[0] = 0;
                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].setAction((byte)3, (short)0, (short)0, 0);
                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].actionIndex = (byte)4;
                switch (this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].hitflystate) {
                    case 0: {
                        this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].setBehurt(2);
                        break;
                    }
                    case 1: {
                        this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].setBehurt(14);
                        break;
                    }
                    case 2: {
                        this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].setBehurt(5);
                        break;
                    }
                    case 3: {
                        this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].setBehurt(5);
                        break;
                    }
                }
                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].hurtState = (byte)4;
                this.canvas.showBossDeath();
                return;
            }
            case 95: {
                if (this.canvas.rolem.roleState > 0) {
                    this.canvas.rolem.setPlayerRideDown();
                    this.canvas.rolem.setPlayerState((byte)0);
                }
                this.nextStep(player);
                return;
            }
            case 90: {
                int i = 0;
                while (true) {
                    if (i >= this.canvas.rolem.effbox.length) {
                        this.nextStep(player);
                        return;
                    }
                    this.canvas.rolem.effbox[i].setalivestate(0);
                    ++i;
                }
            }
            case 88: {
                this.canvas.rolem.isalldeath = (byte)this.eventdata[this.eventindex][this.eventstep][1];
                this.nextStep(player);
                return;
            }
            case 85: {
                switch (this.eventdata[this.eventindex][this.eventstep][1]) {
                    case 0: {
                        this.canvas.rolem.fplayerbox[this.eventdata[this.eventindex][this.eventstep][2]].walkxspeed = this.eventdata[this.eventindex][this.eventstep][3];
                        this.canvas.rolem.fplayerbox[this.eventdata[this.eventindex][this.eventstep][2]].walkzspeed = this.eventdata[this.eventindex][this.eventstep][4];
                        break;
                    }
                    case 1: {
                        this.canvas.rolem.fplayerbox[this.eventdata[this.eventindex][this.eventstep][2]].walkxspeed = this.eventdata[this.eventindex][this.eventstep][3];
                        this.canvas.rolem.fplayerbox[this.eventdata[this.eventindex][this.eventstep][2]].walkzspeed = this.eventdata[this.eventindex][this.eventstep][4];
                        break;
                    }
                    case 2: {
                        this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][2]].walkxspeed = this.eventdata[this.eventindex][this.eventstep][3];
                        this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][2]].walkzspeed = this.eventdata[this.eventindex][this.eventstep][4];
                        break;
                    }
                    case 6: {
                        this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][2]].walkxspeed = this.eventdata[this.eventindex][this.eventstep][3];
                        this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][2]].walkzspeed = this.eventdata[this.eventindex][this.eventstep][4];
                        break;
                    }
                    case 7: {
                        this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][2]].walkxspeed = this.eventdata[this.eventindex][this.eventstep][3];
                        this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][2]].walkzspeed = this.eventdata[this.eventindex][this.eventstep][4];
                        break;
                    }
                }
                this.nextStep(player);
                return;
            }
            case 84: {
                this.canvas.rolem.yingxiangstate = (byte)this.eventdata[this.eventindex][this.eventstep][1];
                this.nextStep(player);
                return;
            }
            case 83: {
                player.showface((byte)this.eventdata[this.eventindex][this.eventstep][1]);
                return;
            }
            case 92: {
                int i = 0;
                while (i < 5) {
                    if (this.eventdata[this.eventindex][this.eventstep][2 + i] > -1) {
                        this.canvas.rolem.fplayerbox[this.eventdata[this.eventindex][this.eventstep][2 + i]].showface((byte)this.eventdata[this.eventindex][this.eventstep][1]);
                    }
                    ++i;
                }
                return;
            }
            case 93: {
                int i = 0;
                while (i < 5) {
                    if (this.eventdata[this.eventindex][this.eventstep][2 + i] > -1) {
                        this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][2 + i]].showface((byte)this.eventdata[this.eventindex][this.eventstep][1]);
                    }
                    ++i;
                }
                return;
            }
            case 96: {
                int i = 0;
                while (true) {
                    if (i >= 5) {
                        player.showface((byte)this.eventdata[this.eventindex][this.eventstep][1]);
                        return;
                    }
                    if (this.eventdata[this.eventindex][this.eventstep][2 + i] > -1) {
                        this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][2 + i]].showface((byte)this.eventdata[this.eventindex][this.eventstep][1]);
                    }
                    ++i;
                }
            }
            case 80: {
                int i = 0;
                while (i < 5) {
                    if (this.eventdata[this.eventindex][this.eventstep][2 + i] > -1) {
                        this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][2 + i]].showface((byte)this.eventdata[this.eventindex][this.eventstep][1]);
                    }
                    ++i;
                }
                return;
            }
            case 89: {
                this.canvas.rolem.callEffect(this.eventdata[this.eventindex][this.eventstep][1], (int)this.eventdata[this.eventindex][this.eventstep][2], (int)this.eventdata[this.eventindex][this.eventstep][3], (int)this.eventdata[this.eventindex][this.eventstep][4], (byte)this.eventdata[this.eventindex][this.eventstep][5]);
                this.nextStep(player);
                return;
            }
            case 79: {
                this.canvas.rolem.enemybox[0].setAction((byte)0, (short)0, (short)0, 0);
                this.canvas.rolem.enemybox[0].actionIndex = (byte)23;
                this.canvas.rolem.callEffect(this.eventdata[this.eventindex][this.eventstep][1], (int)this.eventdata[this.eventindex][this.eventstep][2], (int)this.eventdata[this.eventindex][this.eventstep][3], (int)this.eventdata[this.eventindex][this.eventstep][4], (byte)this.eventdata[this.eventindex][this.eventstep][5]);
                this.nextStep(player);
                return;
            }
            case 78: {
                this.canvas.gameUi.mainmissionstate = (byte)this.eventdata[this.eventindex][this.eventstep][1];
                this.canvas.gameUi.showMissionPlan(0, this.canvas.gameUi.currentmainmissionindex, 0);
                this.nextStep(player);
                return;
            }
            case 76: {
                this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][1]].setalivestate(this.eventdata[this.eventindex][this.eventstep][2]);
                this.nextStep(player);
                return;
            }
            case 73: {
                if (this.eventdata[this.eventindex][this.eventstep][1] != 5) {
                    this.startTeach(player, this.eventdata[this.eventindex][this.eventstep][1]);
                    this.nextStep(player);
                    return;
                }
                if (this.canvas.rolem.ischijianzhuangtai()) {
                    this.startTeach(player, this.eventdata[this.eventindex][this.eventstep][1]);
                    this.nextStep(player);
                    return;
                }
                byte by = this.eventindex;
                this.eventindex = (byte)(by + 1);
                this.eventhappend(by);
                this.eventfinish();
                return;
            }
            case 72: {
                this.canvas.gameUi.initUiScreenData();
                if (player.ismanxiemanlan()) {
                    this.canvas.gameUi.savestate = (byte)4;
                } else {
                    this.canvas.gameUi.savestate = 0;
                    GameUI gameUI = this.canvas.gameUi;
                    this.canvas.gameUi.getClass();
                    gameUI.savemoney = 8 + (player.level - 1) * 3;
                }
                this.canvas.gameUi.savegamestate = (byte)5;
                this.canvas.gameUi.changeUiState((byte)18);
                this.canvas.changeGameState(7);
                return;
            }
            case 71: {
                this.isEventPlaying = false;
                this.isEventFinished = true;
                this.lastEventIndex = this.eventindex;
                return;
            }
            case 68: {
                switch (this.eventdata[this.eventindex][this.eventstep][1]) {
                    case 0: {
                        this.canvas.rolem.isdrawplayer = true;
                        break;
                    }
                    case 1: {
                        this.canvas.rolem.isdrawplayer = false;
                        break;
                    }
                }
                this.nextStep(player);
                return;
            }
            case 67: {
                this.canvas.rolem.fplayerbox[this.eventdata[this.eventindex][this.eventstep][2]].setalivestate((byte)this.eventdata[this.eventindex][this.eventstep][1]);
                this.nextStep(player);
                return;
            }
            case 66: {
                this.canvas.rolem.changeplayerimageindex((byte)this.eventdata[this.eventindex][this.eventstep][1]);
                this.nextStep(player);
                return;
            }
            case 65: {
                switch (this.eventdata[this.eventindex][this.eventstep][1]) {
                    case 0: {
                        this.canvas.gameUi.ishuicheng = true;
                        break;
                    }
                    case 1: {
                        this.canvas.gameUi.ishuicheng = false;
                        break;
                    }
                }
                this.nextStep(player);
                return;
            }
            case 64: {
                this.initminimap((byte)this.eventdata[this.eventindex][this.eventstep][1], (byte)this.eventdata[this.eventindex][this.eventstep][2]);
                this.nextStep(player);
                return;
            }
            case 62: {
                this.canvas.rolem.startTimeStop(this.eventdata[this.eventindex][this.eventstep][1]);
                this.nextStep(player);
                return;
            }
            case 63: {
                this.canvas.rolem.timeStopFinish();
                this.nextStep(player);
                return;
            }
            case 61: {
                switch (this.eventdata[this.eventindex][this.eventstep][1]) {
                    case 0: {
                        this.canvas.gameUi.postmainmission((byte)this.eventdata[this.eventindex][this.eventstep][2]);
                        return;
                    }
                    case 1: {
                        this.canvas.gameUi.postmission((byte)this.eventdata[this.eventindex][this.eventstep][2]);
                        return;
                    }
                }
                return;
            }
            case 60: {
                this.canvas.gameUi.showmissionlastgamestate = (byte)this.eventdata[this.eventindex][this.eventstep][1];
                this.canvas.gameUi.setmissionlist();
                this.canvas.gameUi.showmission();
                this.canvas.changeGameState(7);
                this.canvas.gameUi.changeUiState((byte)17);
                return;
            }
            case 56: {
                switch (this.eventdata[this.eventindex][this.eventstep][4]) {
                    case 0: {
                        this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].setFaceto((short)-1);
                        this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].walkzspeed = this.eventdata[this.eventindex][this.eventstep][5];
                        this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].setAction((byte)1, (short)0, (short)-1, 0);
                        break;
                    }
                    case 1: {
                        this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].setFaceto((short)-1);
                        this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].walkzspeed = this.eventdata[this.eventindex][this.eventstep][5];
                        this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].setAction((byte)1, (short)0, (short)1, 0);
                        break;
                    }
                    case 2: {
                        this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].setFaceto((short)-1);
                        this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].setAction((byte)1, (short)-1, (short)0, 0);
                        break;
                    }
                    case 3: {
                        this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].setFaceto((short)1);
                        this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].setAction((byte)1, (short)1, (short)0, 0);
                        break;
                    }
                }
                this.canvas.c.startShake((byte)5, (byte)5, (byte)0, (byte)0);
                return;
            }
            case 55: {
                this.canvas.rolem.changeCurrentWeapon(player, this.eventdata[this.eventindex][this.eventstep][1]);
                this.nextStep(player);
                return;
            }
            case 54: {
                this.canvas.startSwitchScrren(this.eventdata[this.eventindex][this.eventstep][2], (byte)this.eventdata[this.eventindex][this.eventstep][1], this.eventdata[this.eventindex][this.eventstep][3]);
                this.nextStep(player);
                return;
            }
            case 52: {
                this.canvas.gameUi.playImage = Util.createImage("/head/play.png");
                this.canvas.eventBackState = (byte)3;
                this.nextStep(player);
                return;
            }
            case 53: {
                this.canvas.gameUi.playImage = null;
                this.canvas.eventBackH = 0;
                this.canvas.eventBackState = 0;
                this.nextStep(player);
                return;
            }
            case 48: {
                this.canvas.rolem.allPlayerDead();
                this.nextStep(player);
                return;
            }
            case 49: {
                this.canvas.rolem.flushplayer((byte)this.eventdata[this.eventindex][this.eventstep][1], this.eventdata[this.eventindex][this.eventstep][2], this.eventdata[this.eventindex][this.eventstep][3], (byte)this.eventdata[this.eventindex][this.eventstep][4], (byte)this.eventdata[this.eventindex][this.eventstep][5]);
                this.nextStep(player);
                return;
            }
            case 59: {
                switch (this.eventdata[this.eventindex][this.eventstep][4]) {
                    case 0: 
                    case 1: 
                    case 2: {
                        this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][1]].setFaceto((short)-1);
                        break;
                    }
                    case 3: {
                        this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][1]].setFaceto((short)1);
                        break;
                    }
                }
                this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][1]].setposition(this.eventdata[this.eventindex][this.eventstep][2], 0, this.eventdata[this.eventindex][this.eventstep][3]);
                this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][1]].setAction((byte)0, (byte)0, (byte)0);
                this.nextStep(player);
                return;
            }
            case 2: {
                switch (this.eventdata[this.eventindex][this.eventstep][3]) {
                    case 0: 
                    case 1: 
                    case 2: {
                        player.setFaceto((short)-1);
                        break;
                    }
                    case 3: {
                        player.setFaceto((short)1);
                        break;
                    }
                }
                player.wuditime = 0;
                player.setposition(this.eventdata[this.eventindex][this.eventstep][1], 0, this.eventdata[this.eventindex][this.eventstep][2]);
                player.setAction((byte)0, (short)0, (short)0, 0);
                if (this.canvas.gamestate == 5) {
                    this.canvas.c.resetCamera();
                }
                this.nextStep(player);
                return;
            }
            case 77: {
                switch (this.eventdata[this.eventindex][this.eventstep][4]) {
                    case 0: {
                        this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][1]].setAction((byte)1, (byte)0, (byte)-1);
                        return;
                    }
                    case 1: {
                        this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][1]].setAction((byte)1, (byte)0, (byte)1);
                        return;
                    }
                    case 2: {
                        this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][1]].setFaceto((short)-1);
                        this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][1]].setAction((byte)1, (byte)-1, (byte)0);
                        return;
                    }
                    case 3: {
                        this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][1]].setFaceto((short)1);
                        this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][1]].setAction((byte)1, (byte)1, (byte)0);
                        return;
                    }
                }
                return;
            }
            case 86: {
                switch (this.eventdata[this.eventindex][this.eventstep][3]) {
                    case 0: {
                        this.canvas.rolem.bossbox[0].setAction((byte)1, (short)0, (short)-1, 0);
                        return;
                    }
                    case 1: {
                        this.canvas.rolem.bossbox[0].setAction((byte)1, (short)0, (short)1, 0);
                        return;
                    }
                    case 2: {
                        this.canvas.rolem.bossbox[0].setAction((byte)1, (short)-1, (short)0, 0);
                        this.canvas.rolem.bossbox[0].setFaceto(this.eventdata[this.eventindex][this.eventstep][4]);
                        return;
                    }
                    case 3: {
                        this.canvas.rolem.bossbox[0].setAction((byte)1, (short)1, (short)0, 0);
                        this.canvas.rolem.bossbox[0].setFaceto(this.eventdata[this.eventindex][this.eventstep][4]);
                        return;
                    }
                }
                return;
            }
            case 91: {
                switch (this.eventdata[this.eventindex][this.eventstep][3]) {
                    case 0: {
                        player.setAction((byte)30, (short)0, (short)-1, 0);
                        return;
                    }
                    case 1: {
                        player.setAction((byte)30, (short)0, (short)1, 0);
                        return;
                    }
                    case 2: {
                        player.setFaceto((short)-1);
                        player.setAction((byte)30, (short)-1, (short)0, 0);
                        return;
                    }
                    case 3: {
                        player.setFaceto((short)1);
                        player.setAction((byte)30, (short)1, (short)0, 0);
                        return;
                    }
                }
                return;
            }
            case 3: {
                switch (this.eventdata[this.eventindex][this.eventstep][3]) {
                    case 0: {
                        player.setAction((byte)1, (short)0, (short)-1, 0);
                        return;
                    }
                    case 1: {
                        player.setAction((byte)1, (short)0, (short)1, 0);
                        return;
                    }
                    case 2: {
                        player.setFaceto((short)-1);
                        player.setAction((byte)1, (short)-1, (short)0, 0);
                        return;
                    }
                    case 3: {
                        player.setFaceto((short)1);
                        player.setAction((byte)1, (short)1, (short)0, 0);
                        return;
                    }
                }
                return;
            }
            case 47: {
                if (player.spritex < this.eventdata[this.eventindex][this.eventstep][1]) {
                    player.setFaceto((short)1);
                    player.setAction((byte)1, (short)1, (short)0, 0);
                    return;
                }
                if (player.spritex > this.eventdata[this.eventindex][this.eventstep][1]) {
                    player.setFaceto((short)-1);
                    player.setAction((byte)1, (short)-1, (short)0, 0);
                    return;
                }
                this.nextStep(player);
                return;
            }
            case 4: {
                this.canvas.rolem.allEnemyDead();
                this.nextStep(player);
                return;
            }
            case 5: {
                this.canvas.rolem.flushBoss((byte)this.eventdata[this.eventindex][this.eventstep][1], this.eventdata[this.eventindex][this.eventstep][2], this.eventdata[this.eventindex][this.eventstep][3], (byte)this.eventdata[this.eventindex][this.eventstep][4], (byte)this.eventdata[this.eventindex][this.eventstep][5]);
                this.nextStep(player);
                return;
            }
            case 6: {
                this.canvas.rolem.flushEnemy((byte)this.eventdata[this.eventindex][this.eventstep][1], this.eventdata[this.eventindex][this.eventstep][2], this.eventdata[this.eventindex][this.eventstep][3], (byte)this.eventdata[this.eventindex][this.eventstep][4], (byte)this.eventdata[this.eventindex][this.eventstep][5]);
                this.nextStep(player);
                return;
            }
            case 7: {
                this.canvas.rolem.isflushEnemy = false;
                this.canvas.rolem.flushtotal = 0;
                this.nextStep(player);
                return;
            }
            case 57: {
                this.canvas.gameUi.setIntro(this.talksdata[this.eventdata[this.eventindex][this.eventstep][1]], (byte)this.eventdata[this.eventindex][this.eventstep][2], (byte)this.eventdata[this.eventindex][this.eventstep][3]);
                this.canvas.changeGameState(13);
                return;
            }
            case 102: {
                this.canvas.gameUi.setTalk(this.autotalksdata[this.eventdata[this.eventindex][this.eventstep][1]], this.eventdata[this.eventindex][this.eventstep][2], 0);
                this.canvas.changeGameState(6);
                return;
            }
            case 8: {
                this.canvas.gameUi.setTalk(this.talksdata[this.eventdata[this.eventindex][this.eventstep][1]], this.eventdata[this.eventindex][this.eventstep][2], this.eventdata[this.eventindex][this.eventstep][3]);
                this.canvas.changeGameState(6);
                return;
            }
            case 81: {
                int i = 0;
                while (i < 4) {
                    if (this.eventdata[this.eventindex][this.eventstep][2 + i] > -1) {
                        switch (this.eventdata[this.eventindex][this.eventstep][1]) {
                            case 0: 
                            case 1: 
                            case 2: {
                                this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][2 + i]].setFaceto((short)-1);
                                break;
                            }
                            case 3: {
                                this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][2 + i]].setFaceto((short)1);
                                break;
                            }
                        }
                    }
                    ++i;
                }
                return;
            }
            case 9: {
                int i = 0;
                while (i < 4) {
                    if (this.eventdata[this.eventindex][this.eventstep][2 + i] > -1) {
                        switch (this.eventdata[this.eventindex][this.eventstep][1]) {
                            case 0: 
                            case 1: 
                            case 2: {
                                this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][2 + i]].setFaceto((short)-1);
                                break;
                            }
                            case 3: {
                                this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][2 + i]].setFaceto((short)1);
                                break;
                            }
                        }
                    }
                    ++i;
                }
                return;
            }
            case 74: {
                switch (this.eventdata[this.eventindex][this.eventstep][2]) {
                    case 0: 
                    case 1: 
                    case 2: {
                        this.canvas.rolem.fplayerbox[this.eventdata[this.eventindex][this.eventstep][1]].setFaceto((short)-1);
                        return;
                    }
                    case 3: {
                        this.canvas.rolem.fplayerbox[this.eventdata[this.eventindex][this.eventstep][1]].setFaceto((short)1);
                        return;
                    }
                }
                return;
            }
            case 11: {
                int i = 0;
                while (i < 4) {
                    if (this.eventdata[this.eventindex][this.eventstep][4 + i] > -1) {
                        switch (this.eventdata[this.eventindex][this.eventstep][3]) {
                            case 0: {
                                if (this.eventdata[this.eventindex][this.eventstep][8] == 0) {
                                    this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setFaceto((short)-1);
                                } else {
                                    this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setFaceto((short)1);
                                }
                                this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setAction((byte)1, (short)0, (short)-1, 0);
                                break;
                            }
                            case 1: {
                                if (this.eventdata[this.eventindex][this.eventstep][8] == 0) {
                                    this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setFaceto((short)-1);
                                } else {
                                    this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setFaceto((short)1);
                                }
                                this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setAction((byte)1, (short)0, (short)1, 0);
                                break;
                            }
                            case 2: {
                                this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setFaceto((short)-1);
                                this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setAction((byte)1, (short)-1, (short)0, 0);
                                break;
                            }
                            case 3: {
                                this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setFaceto((short)1);
                                this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setAction((byte)1, (short)1, (short)0, 0);
                                break;
                            }
                        }
                    }
                    ++i;
                }
                return;
            }
            case 12: {
                switch (this.eventdata[this.eventindex][this.eventstep][1]) {
                    case 0: {
                        switch (this.eventdata[this.eventindex][this.eventstep][2]) {
                            case 0: {
                                this.canvas.rolem.doorlockstate = 0;
                                break;
                            }
                            case 1: 
                            case 2: 
                            case 3: 
                            case 4: {
                                this.canvas.rolem.doorlockstate = (byte)(this.canvas.rolem.doorlockstate ^ 1 << this.eventdata[this.eventindex][this.eventstep][2] - 1);
                                break;
                            }
                        }
                        break;
                    }
                    case 1: {
                        switch (this.eventdata[this.eventindex][this.eventstep][2]) {
                            case 0: {
                                for (int i = 0; i < 4; ++i) {
                                    this.canvas.rolem.doorlockstate = (byte)(this.canvas.rolem.doorlockstate | 1 << i);
                                }
                                break;
                            }
                            case 1: 
                            case 2: 
                            case 3: 
                            case 4: {
                                this.canvas.rolem.doorlockstate = (byte)(this.canvas.rolem.doorlockstate | 1 << this.eventdata[this.eventindex][this.eventstep][2] - 1);
                            }
                        }
                        break;
                    }
                }
                this.nextStep(player);
                return;
            }
            case 13: {
                this.canvas.gameUi.setTalk(this.talksdata[this.eventdata[this.eventindex][this.eventstep][1]], 0, 0);
                this.canvas.changeGameState(6);
                return;
            }
            case 14: {
                this.canvas.rolem.flushtotal = this.eventdata[this.eventindex][this.eventstep][1];
                this.nextStep(player);
                return;
            }
            case 15: {
                this.changeMap((byte)0, this.eventdata[this.eventindex][this.eventstep][1], this.eventdata[this.eventindex][this.eventstep][2], this.eventdata[this.eventindex][this.eventstep][3], this.eventdata[this.eventindex][this.eventstep][4]);
                this.nextStep(player);
                return;
            }
            case 16: {
                this.eventtime = this.eventdata[this.eventindex][this.eventstep][1];
                return;
            }
            case 17: {
                switch (this.eventdata[this.eventindex][this.eventstep][1]) {
                    case 0: {
                        player.spritey = 0;
                        player.setAction((byte)1, (short)0, (short)-1, 0);
                        return;
                    }
                    case 1: {
                        player.spritey = 0;
                        player.setAction((byte)1, (short)0, (short)1, 0);
                        return;
                    }
                    case 2: {
                        player.spritey = 0;
                        player.setFaceto((short)-1);
                        player.setAction((byte)1, (short)-1, (short)0, 0);
                        return;
                    }
                    case 3: {
                        player.spritey = 0;
                        player.setFaceto((short)1);
                        player.setAction((byte)1, (short)1, (short)0, 0);
                        return;
                    }
                }
                return;
            }
            case 18: {
                this.canvas.rolem.updateEnemyList(this.eventdata[this.eventindex][this.eventstep][1], this.eventdata[this.eventindex][this.eventstep][2]);
                this.nextStep(player);
                return;
            }
            case 87: {
                this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][2]].setAction((byte)this.eventdata[this.eventindex][this.eventstep][1], (byte)0, (byte)0);
                this.nextStep(player);
                return;
            }
            case 19: {
                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][2]].setAction((byte)0, (short)0, (short)0, 0);
                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][2]].actionIndex = (byte)this.eventdata[this.eventindex][this.eventstep][1];
                this.nextStep(player);
                return;
            }
            case 20: {
                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][2]].setalivestate((byte)this.eventdata[this.eventindex][this.eventstep][1]);
                this.canvas.rolem.currentenemynum = (byte)(this.canvas.rolem.currentenemynum - 1);
                this.nextStep(player);
                return;
            }
            case 21: {
                int i = 0;
                while (true) {
                    if (i >= 2) {
                        this.nextStep(player);
                        return;
                    }
                    if (this.eventdata[this.eventindex][this.eventstep][2 + i] > -1) {
                        this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][2 + i]].setalivestate((byte)this.eventdata[this.eventindex][this.eventstep][1]);
                        this.canvas.rolem.currentenemynum = (byte)(this.canvas.rolem.currentenemynum - 1);
                    }
                    ++i;
                }
            }
            case 22: {
                this.canvas.c.startShake((byte)this.eventdata[this.eventindex][this.eventstep][1], (byte)this.eventdata[this.eventindex][this.eventstep][2], (byte)this.eventdata[this.eventindex][this.eventstep][3], (byte)0);
                this.nextStep(player);
                return;
            }
            case 23: {
                this.canvas.c.stopShake();
                this.nextStep(player);
                return;
            }
            case 25: {
                this.eventcontinuemapindex = (byte)this.eventdata[this.eventindex][this.eventstep][1];
                this.eventcontinueindex = (byte)this.eventdata[this.eventindex][this.eventstep][2];
                this.isEventContinue = true;
                this.nextStep(player);
                return;
            }
            case 27: {
                switch (this.eventdata[this.eventindex][this.eventstep][1]) {
                    case 0: {
                        this.canvas.c.setCameraRole(player);
                        break;
                    }
                    case 7: {
                        this.canvas.c.setCameraRole(this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][2]]);
                        break;
                    }
                    case 6: {
                        this.canvas.c.setCameraRole(this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][2]]);
                        break;
                    }
                    case 2: {
                        this.canvas.c.setCameraRole(this.canvas.rolem.npcbox[this.eventdata[this.eventindex][this.eventstep][2]]);
                        break;
                    }
                    case 1: {
                        this.canvas.c.setCameraRole(this.canvas.rolem.fplayerbox[this.eventdata[this.eventindex][this.eventstep][2]]);
                        break;
                    }
                }
                switch (this.eventdata[this.eventindex][this.eventstep][3]) {
                    case 1: {
                        this.canvas.c.resetCamera();
                        break;
                    }
                }
                this.nextStep(player);
                return;
            }
            case 28: {
                switch (this.eventdata[this.eventindex][this.eventstep][1]) {
                    case 0: {
                        this.iseventAotoCamera = false;
                        break;
                    }
                    case 1: {
                        this.iseventAotoCamera = true;
                        break;
                    }
                }
                this.nextStep(player);
                return;
            }
            case 29: {
                this.canvas.gameUi.setmainmission(this.eventdata[this.eventindex][this.eventstep][1]);
                return;
            }
            case 58: {
                if (this.canvas.gameUi.mainmissionstate == this.eventdata[this.eventindex][this.eventstep][1]) {
                    this.nextStep(player);
                    return;
                }
                this.isEventPlaying = false;
                return;
            }
            case 30: {
                if (this.canvas.gameUi.currentmainmissionindex == this.eventdata[this.eventindex][this.eventstep][1]) {
                    this.nextStep(player);
                    return;
                }
                this.isEventPlaying = false;
                return;
            }
            case 94: {
                boolean state = false;
                for (int i = 0; i < this.canvas.gameUi.currentmissionindex.length; ++i) {
                    if (this.canvas.gameUi.currentmissionindex[i] != this.eventdata[this.eventindex][this.eventstep][1]) continue;
                    state = true;
                }
                if (state) {
                    this.nextStep(player);
                    return;
                }
                if (state) return;
                this.isEventPlaying = false;
                return;
            }
            case 31: {
                player.attack(this.eventdata[this.eventindex][this.eventstep][1], 0);
                this.canvas.rolem.initKey();
                return;
            }
            case 32: {
                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].setactonrole(this.canvas.rolem.fplayerbox[this.eventdata[this.eventindex][this.eventstep][4]]);
                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][1]].attack(this.eventdata[this.eventindex][this.eventstep][2], 0);
                return;
            }
            case 50: {
                this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][1]].attack(this.eventdata[this.eventindex][this.eventstep][2], 0);
                return;
            }
            case 69: {
                this.canvas.rolem.fplayerbox[this.eventdata[this.eventindex][this.eventstep][1]].attack(this.eventdata[this.eventindex][this.eventstep][2], 0);
                return;
            }
            case 33: {
                int i = 0;
                while (i < 4) {
                    if (this.eventdata[this.eventindex][this.eventstep][4 + i] > -1) {
                        switch (this.eventdata[this.eventindex][this.eventstep][3]) {
                            case 0: {
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setFaceto((short)-1);
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setAction((byte)1, (short)0, (short)-1, 0);
                                break;
                            }
                            case 1: {
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setFaceto((short)-1);
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setAction((byte)1, (short)0, (short)1, 0);
                                break;
                            }
                            case 2: {
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setFaceto((short)-1);
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setAction((byte)1, (short)-1, (short)0, 0);
                                break;
                            }
                            case 3: {
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setFaceto((short)1);
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][4 + i]].setAction((byte)1, (short)1, (short)0, 0);
                                break;
                            }
                        }
                    }
                    ++i;
                }
                return;
            }
            case 34: {
                if (player.actionState == 1) {
                    player.setAction((byte)0, (short)0, (short)0, 0);
                }
                this.canvas.gameUi.settishi(this.talksdata[this.eventdata[this.eventindex][this.eventstep][1]], (byte)1);
                return;
            }
            case 35: {
                int i = 0;
                while (i < 4) {
                    if (this.eventdata[this.eventindex][this.eventstep][2 + i] > -1) {
                        switch (this.eventdata[this.eventindex][this.eventstep][1]) {
                            case 0: 
                            case 1: 
                            case 2: {
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][2 + i]].setAction((byte)0, (short)0, (short)0, 0);
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][2 + i]].setFaceto((short)-1);
                                break;
                            }
                            case 3: {
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][2 + i]].setAction((byte)0, (short)0, (short)0, 0);
                                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][2 + i]].setFaceto((short)1);
                                break;
                            }
                        }
                    }
                    ++i;
                }
                return;
            }
            case 36: {
                this.openarea(this.eventdata[this.eventindex][this.eventstep][1], (byte)this.eventdata[this.eventindex][this.eventstep][2]);
                return;
            }
            case 82: {
                this.closearea(this.eventdata[this.eventindex][this.eventstep][1]);
                return;
            }
            case 38: {
                this.canvas.gameUi.setasideword(this.talksdata[this.eventdata[this.eventindex][this.eventstep][1]], this.eventdata[this.eventindex][this.eventstep][2]);
                this.canvas.changeGameState(12);
                return;
            }
            case 39: {
                this.canvas.gameUi.openWeapon((byte)this.eventdata[this.eventindex][this.eventstep][1]);
                return;
            }
            case 97: {
                this.canvas.rolem.fplayerbox[this.eventdata[this.eventindex][this.eventstep][2]].setAction((byte)0, (short)0, (short)0, 0);
                this.canvas.rolem.fplayerbox[this.eventdata[this.eventindex][this.eventstep][2]].actionIndex = (byte)this.eventdata[this.eventindex][this.eventstep][1];
                this.nextStep(player);
                return;
            }
            case 40: {
                this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][2]].setAction((byte)0, (short)0, (short)0, 0);
                this.canvas.rolem.enemybox[this.eventdata[this.eventindex][this.eventstep][2]].actionIndex = (byte)this.eventdata[this.eventindex][this.eventstep][1];
                this.nextStep(player);
                return;
            }
            case 41: {
                this.canvas.rolem.bossbox[this.eventdata[this.eventindex][this.eventstep][2]].spritetype = (byte)this.eventdata[this.eventindex][this.eventstep][1];
                this.nextStep(player);
                return;
            }
            case 42: {
                switch (this.eventdata[this.eventindex][this.eventstep][1]) {
                    case 0: 
                    case 1: 
                    case 2: {
                        player.setFaceto((short)-1);
                        return;
                    }
                    case 3: {
                        player.setFaceto((short)1);
                        return;
                    }
                }
                return;
            }
            case 43: {
                if (this.canvas.gameUi.currentmainmissionindex >= this.eventdata[this.eventindex][this.eventstep][1] && this.canvas.gameUi.currentmainmissionindex <= this.eventdata[this.eventindex][this.eventstep][2]) {
                    this.nextStep(player);
                    return;
                }
                this.isEventPlaying = false;
                return;
            }
            case 44: {
                this.canvas.gameUi.openMission(this.eventdata[this.eventindex][this.eventstep][1]);
                this.nextStep(player);
                return;
            }
            case 45: {
                player.setAction((byte)0, (short)0, (short)0, 0);
                player.actionIndex = (byte)this.eventdata[this.eventindex][this.eventstep][1];
                this.nextStep(player);
                return;
            }
            case 46: {
                this.canvas.gameUi.mainmissionstate = (byte)3;
                this.canvas.mapm.inituniverse();
                this.canvas.startSwitchScrren(0, (byte)10, 0);
                this.nextStep(player);
                return;
            }
        }
    }

    public void initWeather() {
        this.isshowweather = false;
        if (this.weatherindex > -1) {
            this.isshowweather = true;
            Util.tempData = new byte[10000];
            Util.position[0] = 0;
            Util.getStream(Util.tempData, "/data/weather" + this.weatherindex + ".data", 0);
            this.weathermodules = Util.loadInfShort(Util.tempData, this.weathermodules, Util.position);
            this.weatherframes = Util.loadInfShort(Util.tempData, this.weatherframes, Util.position);
            this.weatheractions = Util.loadInfShort(Util.tempData, this.weatheractions, Util.position);
            this.weatherimages = new Image[1];
            this.weatherimages[0] = Util.createImage("/ui/weather" + this.weatherindex + ".png");
        }
    }

    public void initWeatherdata() {
        if (this.weatherindex > -1) {
            switch (this.weatherindex) {
                case 0: {
                    this.weatherdata = new short[30][6];
                    for (int i = 0; i < this.weatherdata.length; ++i) {
                        this.weatherdata[i][0] = (short)(Util.getARandomInt(this.canvas.c.cameraw + 100) + this.canvas.c.camerax - 50);
                        this.weatherdata[i][1] = (short)(this.canvas.c.cameray + Util.getARandomInt(this.canvas.c.camerah));
                        this.weatherdata[i][2] = (short)Util.getBRandomInt(5);
                        this.weatherdata[i][3] = (short)(Util.getARandomInt(10) + 4);
                        this.weatherdata[i][4] = (short)Util.getARandomInt(3);
                        this.weatherdata[i][5] = (short)(Util.getARandomInt(200) + 130);
                    }
                    break;
                }
                case 1: {
                    this.weatherdata = new short[50][8];
                    for (int i = 0; i < this.weatherdata.length; ++i) {
                        this.weatherdata[i][0] = (short)(Util.getARandomInt(this.canvas.c.cameraw + 100) + this.canvas.c.camerax - 50);
                        this.weatherdata[i][1] = (short)(this.canvas.c.cameray + Util.getARandomInt(this.canvas.c.camerah));
                        this.weatherdata[i][2] = 0;
                        this.weatherdata[i][3] = (short)(Util.getARandomInt(10) + 15);
                        this.weatherdata[i][4] = (short)Util.getARandomInt(2);
                        this.weatherdata[i][5] = (short)(Util.getARandomInt(200) + 130);
                        this.weatherdata[i][6] = 0;
                        this.weatherdata[i][7] = 0;
                    }
                    break;
                }
                case 2: {
                    this.weatherdata = new short[5][8];
                    for (int i = 0; i < this.weatherdata.length; ++i) {
                        this.weatherdata[i][0] = (short)(Util.getARandomInt(this.canvas.c.cameraw) + this.canvas.c.camerax);
                        this.weatherdata[i][1] = (short)(this.canvas.c.cameray + Util.getARandomInt(this.canvas.c.camerah));
                        this.weatherdata[i][2] = (short)Util.getBRandomInt(5);
                        this.weatherdata[i][3] = 5;
                        this.weatherdata[i][4] = (short)Util.getARandomInt(this.weatheractions[0].length >> 1);
                        this.weatherdata[i][5] = (short)(Util.getARandomInt(160) + 200);
                        this.weatherdata[i][6] = 0;
                        this.weatherdata[i][7] = 0;
                    }
                    break;
                }
            }
        }
    }

    public void weatherProcess() {
        if (this.isshowweather) {
            switch (this.weatherindex) {
                case 0: {
                    for (int i = 0; i < this.weatherdata.length; ++i) {
                        if (this.weatherdata[i][1] >= this.weatherdata[i][5]) {
                            this.weatherdata[i][0] = (short)(Util.getARandomInt(this.canvas.c.cameraw + 100) + this.canvas.c.camerax - 50);
                            this.weatherdata[i][1] = (short)(this.canvas.c.cameray - Util.getARandomInt(10));
                            this.weatherdata[i][2] = (short)Util.getBRandomInt(5);
                            this.weatherdata[i][3] = (short)(Util.getARandomInt(8) + 4);
                            this.weatherdata[i][4] = (short)Util.getARandomInt(3);
                            this.weatherdata[i][5] = (short)(Util.getARandomInt(200) + 130);
                            continue;
                        }
                        if (this.canvas.paintType != 0) continue;
                        short[] sArray = this.weatherdata[i];
                        sArray[0] = (short)(sArray[0] + this.weatherdata[i][2]);
                        short[] sArray2 = this.weatherdata[i];
                        sArray2[1] = (short)(sArray2[1] + this.weatherdata[i][3]);
                    }
                    break;
                }
                case 1: {
                    block9: for (int i = 0; i < this.weatherdata.length; ++i) {
                        switch (this.weatherdata[i][6]) {
                            case 0: {
                                if (this.weatherdata[i][1] >= this.weatherdata[i][5]) {
                                    this.weatherdata[i][6] = 1;
                                    short[] sArray = this.weatherdata[i];
                                    sArray[4] = (short)(sArray[4] + 2);
                                    continue block9;
                                }
                                if (this.canvas.paintType != 0) continue block9;
                                short[] sArray = this.weatherdata[i];
                                sArray[0] = (short)(sArray[0] + this.weatherdata[i][2]);
                                short[] sArray3 = this.weatherdata[i];
                                sArray3[1] = (short)(sArray3[1] + this.weatherdata[i][3]);
                                continue block9;
                            }
                            case 1: {
                                if (this.canvas.paintType == 0) {
                                    short[] sArray = this.weatherdata[i];
                                    sArray[7] = (short)(sArray[7] + 2);
                                }
                                if (this.weatherdata[i][7] < this.weatheractions[this.weatherdata[i][4]].length) continue block9;
                                this.weatherdata[i][7] = 0;
                                this.weatherdata[i][0] = (short)(Util.getARandomInt(this.canvas.c.cameraw + 100) + this.canvas.c.camerax - 50);
                                this.weatherdata[i][1] = (short)(this.canvas.c.cameray - Util.getARandomInt(20));
                                this.weatherdata[i][2] = 0;
                                this.weatherdata[i][3] = (short)(Util.getARandomInt(10) + 15);
                                this.weatherdata[i][4] = (short)Util.getARandomInt(2);
                                this.weatherdata[i][5] = (short)(Util.getARandomInt(160) + 200);
                                this.weatherdata[i][6] = 0;
                                this.weatherdata[i][7] = 0;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    public void drawWeather(int state, Role player) {
        if (this.isshowweather) {
            switch (this.weatherindex) {
                case 0: {
                    block14: for (int i = 0; i < this.weatherdata.length; ++i) {
                        switch (this.weatherdata[i][4]) {
                            case 0: {
                                if (state != 0) continue block14;
                                this.canvas.drawFrame(this.weatherimages, this.weathermodules, this.weatherframes, this.weatherdata[i][4], this.weatherdata[i][0], this.weatherdata[i][1], 20);
                                continue block14;
                            }
                            case 1: {
                                if (state != 1 || this.weatherdata[i][5] > player.spritez) continue block14;
                                this.canvas.drawFrame(this.weatherimages, this.weathermodules, this.weatherframes, this.weatherdata[i][4], this.weatherdata[i][0], this.weatherdata[i][1], 20);
                                continue block14;
                            }
                            case 2: {
                                if (state != 2 || this.weatherdata[i][5] <= player.spritez) continue block14;
                                this.canvas.drawFrame(this.weatherimages, this.weathermodules, this.weatherframes, this.weatherdata[i][4], this.weatherdata[i][0], this.weatherdata[i][1], 20);
                            }
                        }
                    }
                    break;
                }
                case 1: {
                    block15: for (int i = 0; i < this.weatherdata.length; ++i) {
                        switch (this.weatherdata[i][4]) {
                            case 0: 
                            case 2: {
                                if (state != 0 && (state != 1 || this.weatherdata[i][5] > player.spritez)) continue block15;
                                this.canvas.drawFrame(this.weatherimages, this.weathermodules, this.weatherframes, this.weatheractions[this.weatherdata[i][4]][this.weatherdata[i][7] + 1], this.weatherdata[i][0], this.weatherdata[i][1], 20);
                                continue block15;
                            }
                            case 1: 
                            case 3: {
                                if (state != 2 || this.weatherdata[i][5] <= player.spritez) continue block15;
                                this.canvas.drawFrame(this.weatherimages, this.weathermodules, this.weatherframes, this.weatheractions[this.weatherdata[i][4]][this.weatherdata[i][7] + 1], this.weatherdata[i][0], this.weatherdata[i][1], 20);
                            }
                        }
                    }
                    break;
                }
                case 2: {
                    for (int i = 0; i < this.weatherdata.length; ++i) {
                        if ((state != 0 || this.weatherdata[i][5] > player.spritez) && (state != 1 || this.weatherdata[i][5] < player.spritez)) continue;
                        this.canvas.drawFrame(this.weatherimages, this.weathermodules, this.weatherframes, this.weatheractions[0][this.weatherdata[i][4] * 2], this.weatherdata[i][0], this.weatherdata[i][1], 20);
                    }
                    break;
                }
            }
        }
    }

    public void nextTeach(Role role) {
        this.teachstep = (byte)(this.teachstep + 1);
        if (this.teachstep == this.teachdata[this.teachindex].length) {
            this.teachfinish();
        }
    }

    private void teachfinish() {
        this.teachhappend(this.teachindex);
        this.isTeachPlayering = false;
    }

    private void teachhappend(byte eindex) {
        this.teachstate = (short)(this.teachstate | 1 << eindex);
    }

    public void startTeach(Role player, int index) {
        this.teachindex = (byte)index;
        this.teachstep = 0;
        this.isTeachPlayering = true;
        this.canvas.gameUi.initUiScreenData();
    }

    public void drawTeach() {
        if (this.isTeachPlayering) {
            switch (this.teachdata[this.teachindex][this.teachstep][0]) {
                case 19: {
                    this.canvas.drawTeachWord("\u8fdb\u5165\u80cc\u5305\u67e5\u770b\u754c\u9762", 320, 30);
                    break;
                }
                case 20: {
                    this.canvas.drawTeachWord("\u8fdb\u5165\u9053\u5177\u80cc\u5305\u754c\u9762", 320, 45);
                    break;
                }
                case 21: {
                    this.canvas.drawTeachWord("\u9009\u62e9\u6298\u8dc3\u6676\u4f53", 320, 50);
                    break;
                }
                case 22: {
                    this.canvas.drawTeachWord("\u4f7f\u7528\u6298\u8dc3\u6676\u4f53", 320, 50);
                    break;
                }
                case 18: {
                    this.canvas.drawTeachWord("\u6309\u56fe\u6807 1 \u5207\u6362\u6b66\u5668", 320, 50);
                    break;
                }
                case 17: {
                    this.canvas.drawTeachWord("\u6309\u56fe\u6807 # \u4e0a\u5750\u9a91", 320, 50);
                    break;
                }
                case 0: {
                    this.canvas.gameUi.drawUiModule(117, this.citybuildpos[this.teachdata[this.teachindex][this.teachstep][1]][0][0], this.citybuildpos[this.teachdata[this.teachindex][this.teachstep][1]][0][1] + this.canvas.publiccounter / 2 % 2, 33, 0);
                    this.canvas.drawTeachWord("\u8fdb\u5165\u661f\u9645\u795e\u6bbf", 320, 50);
                    break;
                }
                case 1: {
                    this.canvas.gameUi.drawUiModule(117, 193, 235 + this.canvas.publiccounter / 2 % 2, 3, 0);
                    this.canvas.drawTeachWord("\u9009\u62e9\u57ce\u5e02\u5347\u7ea7", 320, 34);
                    break;
                }
                case 2: {
                    this.canvas.drawTeachWord("\u70b9\u51fb\u9009\u9879\u56fe\u6807\uff01", 320, 34);
                    break;
                }
                case 3: {
                    this.canvas.drawTeachWord("\u9009\u62e9\u5347\u7ea7\u57ce\u5e02", 320, 34);
                    break;
                }
                case 4: {
                    this.canvas.gameUi.drawUiModule(117, this.universeMissioniconPos[this.teachdata[this.teachindex][this.teachstep][1] * 2], this.universeMissioniconPos[this.teachdata[this.teachindex][this.teachstep][1] * 2 + 1] + this.canvas.publiccounter / 2 % 2, 33, 0);
                    break;
                }
                case 5: {
                    for (int i = 0; i < this.universeMapData[this.universeMapIndex].length; i = (int)((byte)(i + 1))) {
                        if (this.universeMapData[this.universeMapIndex][i + universeMapStartIndex] != this.teachdata[this.teachindex][this.teachstep][1]) continue;
                        if (i == this.universeMapSelectIndex) {
                            this.canvas.gameUi.drawUiModule(117, 108, 64 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46 + this.canvas.publiccounter / 2 % 2, 3, 0);
                            continue;
                        }
                        this.canvas.gameUi.drawUiModule(117, 112, 62 + this.universeInfoBarMap[this.universeMapIndex][i + universeMapStartIndex] * 46 + this.canvas.publiccounter / 2 % 2, 3, 0);
                    }
                    break;
                }
                case 6: {
                    if (this.teachindex == 2) {
                        this.canvas.gameUi.commandY = 0;
                        this.canvas.drawTeachWord("\u70b9\u51fb\u5c4f\u5e55\u53f3\u4e0b\u65b9\u56fe\u6807", 320, 34);
                        break;
                    }
                    if (this.teachindex != 6) break;
                    this.canvas.drawTeachWord("\u70b9\u51fb\u5c4f\u5e55\u53f3\u4e0a\u65b9\u7b2c\u4e8c\u4e2a\u56fe\u6807", 320, 34);
                    break;
                }
                case 7: {
                    this.canvas.drawTeachWord("\u8fdb\u5165\u6280\u80fd\u5347\u7ea7\u754c\u9762", 320, 10);
                    break;
                }
                case 8: {
                    this.canvas.drawTeachWord("\u9009\u62e9\u5251\u6280\u80fd", 320, 34);
                    break;
                }
                case 9: {
                    this.canvas.drawTeachWord("\u9009\u62e9\u5251\u5203\u65a9", 320, 34);
                    break;
                }
                case 10: {
                    this.canvas.drawTeachWord("\u9009\u62e9\u5f00\u542f\u6280\u80fd", 320, 34);
                    break;
                }
                case 11: {
                    this.canvas.drawTeachWord("\u9009\u62e9\u6280\u80fd\u5e76\u88c5\u5907", 320, 34);
                    break;
                }
                case 12: {
                    this.canvas.drawTeachWord("\u88c5\u5907\u6280\u80fd", 320, 34);
                    break;
                }
                case 15: {
                    this.canvas.gameUi.drawteachjianhujia(2, 160);
                    this.canvas.drawTeachWord("\u6301\u5251\u3001\u62f3\u5957\u653b\u51fb\u5bf9\u9ec4\u8272\u62a4\u7532\u5747\u9020\u6210\u6b63\u5e38\u4f24\u5bb3", 320, 95);
                    if (this.canvas.publiccounter / 2 % 2 != 0) break;
                    this.canvas.drawColorString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 320, 270, 17, 15597319, 0);
                    break;
                }
                case 13: {
                    this.canvas.gameUi.drawteachjianhujia(0, 160);
                    this.canvas.drawTeachWord("\u6301\u5251\u653b\u51fb\u5bf9\u84dd\u8272\u62a4\u7532\u4f24\u5bb3\u66f4\u9ad8", 320, 95);
                    if (this.canvas.publiccounter / 2 % 2 != 0) break;
                    this.canvas.drawColorString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 320, 270, 17, 15597319, 0);
                    break;
                }
                case 14: {
                    this.canvas.gameUi.drawteachjianhujia(1, 160);
                    this.canvas.drawTeachWord("\u6301\u62f3\u5957\u653b\u51fb\u5bf9\u7ea2\u8272\u62a4\u7532\u4f24\u5bb3\u66f4\u9ad8", 320, 95);
                    if (this.canvas.publiccounter / 2 % 2 != 0) break;
                    this.canvas.drawColorString("\u70b9\u51fb\u5c4f\u5e55\u7ee7\u7eed", 320, 270, 17, 15597319, 0);
                    break;
                }
                case 23: {
                    this.canvas.drawTeachWord("\u9009\u62e9\u7a7a\u95f4\u5305", 320, 50);
                    break;
                }
                case 24: {
                    this.canvas.drawTeachWord("\u9009\u62e9\u5de6\u4e0b\u65b9\u56fe\u6807\u4f7f\u7528", 320, 50);
                }
            }
        }
    }

    public void teachKeyProcess(Role role) {
        if (this.isTeachPlayering) {
            switch (this.teachdata[this.teachindex][this.teachstep][0]) {
                case 19: {
                    if (Control.IsKeyDown(4100) || Control.IsKeyHold(4100, 10)) {
                        int n;
                        int n2;
                        if (this.canvas.gameUi.gameMenuSelectIndex > 0) {
                            this.canvas.gameUi.gameMenuSelectIndex = (byte)(this.canvas.gameUi.gameMenuSelectIndex - 1);
                            break;
                        }
                        GameUI cfr_ignored_0 = this.canvas.gameUi;
                        if (GameUI.gameMenuStartIndex > 0) {
                            GameUI cfr_ignored_1 = this.canvas.gameUi;
                            GameUI.gameMenuStartIndex = (byte)(GameUI.gameMenuStartIndex - 1);
                            break;
                        }
                        if (this.canvas.gameUi.gameMenuSelectIndex != 0) break;
                        GameUI cfr_ignored_2 = this.canvas.gameUi;
                        if (GameUI.gameMenuStartIndex != 0) break;
                        GameUI gameUI = this.canvas.gameUi;
                        GameUI cfr_ignored_3 = this.canvas.gameUi;
                        this.canvas.gameUi.getClass();
                        if (8 < 5) {
                            GameUI cfr_ignored_4 = this.canvas.gameUi;
                            n2 = 8;
                        } else {
                            this.canvas.gameUi.getClass();
                            n2 = 5;
                        }
                        gameUI.gameMenuSelectIndex = (byte)(n2 - 1);
                        GameUI cfr_ignored_5 = this.canvas.gameUi;
                        GameUI cfr_ignored_6 = this.canvas.gameUi;
                        this.canvas.gameUi.getClass();
                        if (8 < 5) {
                            this.canvas.gameUi.getClass();
                            GameUI cfr_ignored_7 = this.canvas.gameUi;
                            n = 5 - 8;
                        } else {
                            n = 0;
                        }
                        GameUI.gameMenuStartIndex = (byte)n;
                        break;
                    }
                    if (Control.IsKeyDown(8448) || Control.IsKeyHold(8448, 10)) {
                        int n;
                        int n3;
                        int n4;
                        byte by = this.canvas.gameUi.gameMenuSelectIndex;
                        GameUI cfr_ignored_8 = this.canvas.gameUi;
                        this.canvas.gameUi.getClass();
                        if (8 < 5) {
                            GameUI cfr_ignored_9 = this.canvas.gameUi;
                            n4 = 8;
                        } else {
                            this.canvas.gameUi.getClass();
                            n4 = 5;
                        }
                        if (by < n4 - 1) {
                            this.canvas.gameUi.gameMenuSelectIndex = (byte)(this.canvas.gameUi.gameMenuSelectIndex + 1);
                            break;
                        }
                        GameUI cfr_ignored_10 = this.canvas.gameUi;
                        this.canvas.gameUi.getClass();
                        if (8 < 5) {
                            GameUI cfr_ignored_11 = this.canvas.gameUi;
                            this.canvas.gameUi.getClass();
                            GameUI cfr_ignored_12 = this.canvas.gameUi;
                            if (GameUI.gameMenuStartIndex < 5 - 8) {
                                GameUI cfr_ignored_13 = this.canvas.gameUi;
                                GameUI.gameMenuStartIndex = (byte)(GameUI.gameMenuStartIndex + 1);
                                break;
                            }
                        }
                        byte by2 = this.canvas.gameUi.gameMenuSelectIndex;
                        GameUI cfr_ignored_14 = this.canvas.gameUi;
                        this.canvas.gameUi.getClass();
                        if (8 < 5) {
                            GameUI cfr_ignored_15 = this.canvas.gameUi;
                            n3 = 8;
                        } else {
                            this.canvas.gameUi.getClass();
                            n3 = 5;
                        }
                        if (by2 != n3 - 1) break;
                        GameUI cfr_ignored_16 = this.canvas.gameUi;
                        GameUI cfr_ignored_17 = this.canvas.gameUi;
                        this.canvas.gameUi.getClass();
                        if (8 < 5) {
                            this.canvas.gameUi.getClass();
                            GameUI cfr_ignored_18 = this.canvas.gameUi;
                            n = 5 - 8;
                        } else {
                            n = 0;
                        }
                        if (GameUI.gameMenuStartIndex != n) break;
                        this.canvas.gameUi.gameMenuSelectIndex = 0;
                        GameUI cfr_ignored_19 = this.canvas.gameUi;
                        GameUI.gameMenuStartIndex = 0;
                        break;
                    }
                    if (Control.IsKeyDown(327712)) {
                        GameUI cfr_ignored_20 = this.canvas.gameUi;
                        if (this.canvas.gameUi.gameMenuSelectIndex + GameUI.gameMenuStartIndex != 1) break;
                        this.canvas.gameUi.packboxindex = 0;
                        this.canvas.gameUi.setpackboxData();
                        this.canvas.gameUi.changeUiState((byte)20);
                        this.nextTeach(role);
                        break;
                    }
                    if (!Control.IsKeyDown(131072)) break;
                    this.canvas.gameUi.settishi("\u8bf7\u9009\u62e9\u80cc\u5305\u67e5\u770b\u754c\u9762", (byte)0);
                    break;
                }
                case 23: {
                    break;
                }
                case 20: {
                    if (Control.IsKeyDown(4100)) {
                        this.canvas.gameUi.packboxindex = (byte)(this.canvas.gameUi.packboxindex - 1);
                        this.canvas.gameUi.packboxindex = (byte)(this.canvas.gameUi.packboxindex < 0 ? 2 : (int)this.canvas.gameUi.packboxindex);
                        break;
                    }
                    if (Control.IsKeyDown(8448)) {
                        this.canvas.gameUi.packboxindex = (byte)(this.canvas.gameUi.packboxindex + 1);
                        this.canvas.gameUi.packboxindex = this.canvas.gameUi.packboxindex > 2 ? (byte)0 : this.canvas.gameUi.packboxindex;
                        break;
                    }
                    if (Control.IsKeyDown(327712)) {
                        if (this.canvas.gameUi.packboxindex != 0) break;
                        this.canvas.gameUi.itemBoxSeletedIndex = 0;
                        this.canvas.gameUi.itemBoxStartIndex = 0;
                        this.canvas.gameUi.itemBoxUseTotal = this.canvas.gameUi.itemPackNum;
                        if (this.canvas.gameUi.itemBoxSeletedIndex + this.canvas.gameUi.itemBoxStartIndex * 8 < this.canvas.gameUi.itemPack[0].length) {
                            this.canvas.gameUi.setItemInfo1(this.canvas.gameUi.itemPack[0][this.canvas.gameUi.itemBoxSeletedIndex + this.canvas.gameUi.itemBoxStartIndex * 8]);
                        } else {
                            this.canvas.gameUi.setItemInfo1((byte)-1);
                        }
                        this.canvas.gameUi.changeUiState((byte)5);
                        this.nextTeach(role);
                        break;
                    }
                    if (!Control.IsKeyDown(131072)) break;
                    this.canvas.gameUi.settishi("\u8bf7\u9009\u62e9\u7269\u54c1\u80cc\u5305", (byte)0);
                    break;
                }
                case 21: {
                    if (Control.IsKeyDown(4100) || Control.IsKeyHold(4100, 10)) {
                        if (this.canvas.gameUi.isCommand) break;
                        if (this.canvas.gameUi.itemBoxSeletedIndex / 8 > 0) {
                            this.canvas.gameUi.itemBoxSeletedIndex = (byte)(this.canvas.gameUi.itemBoxSeletedIndex - 8);
                        } else if (this.canvas.gameUi.itemBoxStartIndex > 0) {
                            this.canvas.gameUi.itemBoxStartIndex = (byte)(this.canvas.gameUi.itemBoxStartIndex - 1);
                        }
                        if (this.canvas.gameUi.itemBoxSeletedIndex + this.canvas.gameUi.itemBoxStartIndex * 8 >= this.canvas.gameUi.itemPack[0].length) break;
                        this.canvas.gameUi.setItemInfo(this.canvas.gameUi.itemPack[0][this.canvas.gameUi.itemBoxSeletedIndex + this.canvas.gameUi.itemBoxStartIndex * 8]);
                        break;
                    }
                    if (Control.IsKeyDown(8448) || Control.IsKeyHold(8448, 10)) {
                        if (this.canvas.gameUi.isCommand) break;
                        if (this.canvas.gameUi.itemBoxSeletedIndex / 8 < 1) {
                            this.canvas.gameUi.itemBoxSeletedIndex = (byte)(this.canvas.gameUi.itemBoxSeletedIndex + 8);
                        } else if (this.canvas.gameUi.itemBoxStartIndex < this.canvas.gameUi.itemBoxUseTotal / 8 - 2) {
                            this.canvas.gameUi.itemBoxStartIndex = (byte)(this.canvas.gameUi.itemBoxStartIndex + 1);
                        }
                        if (this.canvas.gameUi.itemBoxSeletedIndex + this.canvas.gameUi.itemBoxStartIndex * 8 >= this.canvas.gameUi.itemPack[0].length) break;
                        this.canvas.gameUi.setItemInfo(this.canvas.gameUi.itemPack[0][this.canvas.gameUi.itemBoxSeletedIndex + this.canvas.gameUi.itemBoxStartIndex * 8]);
                        break;
                    }
                    if (Control.IsKeyDown(16400) || Control.IsKeyHold(16400, 10)) {
                        if (this.canvas.gameUi.isCommand) {
                            switch (this.canvas.gameUi.commandState) {
                                case 0: {
                                    int n;
                                    GameUI gameUI = this.canvas.gameUi;
                                    this.canvas.gameUi.commandSeletedIndex = (byte)(this.canvas.gameUi.commandSeletedIndex - 1);
                                    if (this.canvas.gameUi.commandSeletedIndex < 0) {
                                        this.canvas.gameUi.getClass();
                                        n = 2;
                                    } else {
                                        n = this.canvas.gameUi.commandSeletedIndex;
                                    }
                                    gameUI.commandSeletedIndex = n;
                                }
                            }
                            break;
                        }
                        this.canvas.gameUi.itemBoxSeletedIndex = this.canvas.gameUi.itemBoxSeletedIndex % 8 == 0 ? (byte)(this.canvas.gameUi.itemBoxSeletedIndex + 7) : (byte)(this.canvas.gameUi.itemBoxSeletedIndex - 1);
                        if (this.canvas.gameUi.itemBoxSeletedIndex + this.canvas.gameUi.itemBoxStartIndex * 8 >= this.canvas.gameUi.itemPack[0].length) break;
                        this.canvas.gameUi.setItemInfo(this.canvas.gameUi.itemPack[0][this.canvas.gameUi.itemBoxSeletedIndex + this.canvas.gameUi.itemBoxStartIndex * 8]);
                        break;
                    }
                    if (Control.IsKeyDown(32832) || Control.IsKeyHold(32832, 10)) {
                        if (this.canvas.gameUi.isCommand) {
                            switch (this.canvas.gameUi.commandState) {
                                case 0: {
                                    GameUI gameUI = this.canvas.gameUi;
                                    this.canvas.gameUi.commandSeletedIndex = (byte)(this.canvas.gameUi.commandSeletedIndex + 1);
                                    this.canvas.gameUi.getClass();
                                    gameUI.commandSeletedIndex = this.canvas.gameUi.commandSeletedIndex > 2 ? (byte)0 : this.canvas.gameUi.commandSeletedIndex;
                                }
                            }
                            break;
                        }
                        this.canvas.gameUi.itemBoxSeletedIndex = this.canvas.gameUi.itemBoxSeletedIndex % 8 == 7 ? (byte)(this.canvas.gameUi.itemBoxSeletedIndex - 7) : (byte)(this.canvas.gameUi.itemBoxSeletedIndex + 1);
                        if (this.canvas.gameUi.itemBoxSeletedIndex + this.canvas.gameUi.itemBoxStartIndex * 8 >= this.canvas.gameUi.itemPack[0].length) break;
                        this.canvas.gameUi.setItemInfo(this.canvas.gameUi.itemPack[0][this.canvas.gameUi.itemBoxSeletedIndex + this.canvas.gameUi.itemBoxStartIndex * 8]);
                        break;
                    }
                    if (!Control.IsKeyDown(327712)) break;
                    if (this.canvas.gameUi.itemPack[0][this.canvas.gameUi.itemBoxSeletedIndex + this.canvas.gameUi.itemBoxStartIndex * 8] == 0) {
                        this.canvas.gameUi.startCommand();
                        this.canvas.gameUi.changeCommandState((byte)0);
                        this.canvas.gameUi.commandSeletedIndex = 0;
                        this.nextTeach(role);
                        break;
                    }
                    this.canvas.gameUi.settishi("\u8bf7\u9009\u62e9\u6298\u8dc3\u6676\u4f53", (byte)0);
                    break;
                }
                case 22: {
                    if (Control.IsKeyDown(16400) || Control.IsKeyHold(16400, 10)) {
                        if (!this.canvas.gameUi.isCommand) break;
                        switch (this.canvas.gameUi.commandState) {
                            case 0: {
                                int n;
                                GameUI gameUI = this.canvas.gameUi;
                                this.canvas.gameUi.commandSeletedIndex = (byte)(this.canvas.gameUi.commandSeletedIndex - 1);
                                if (this.canvas.gameUi.commandSeletedIndex < 0) {
                                    this.canvas.gameUi.getClass();
                                    n = 2;
                                } else {
                                    n = this.canvas.gameUi.commandSeletedIndex;
                                }
                                gameUI.commandSeletedIndex = n;
                            }
                        }
                        break;
                    }
                    if (Control.IsKeyDown(32832) || Control.IsKeyHold(32832, 10)) {
                        if (!this.canvas.gameUi.isCommand) break;
                        switch (this.canvas.gameUi.commandState) {
                            case 0: {
                                GameUI gameUI = this.canvas.gameUi;
                                this.canvas.gameUi.commandSeletedIndex = (byte)(this.canvas.gameUi.commandSeletedIndex + 1);
                                this.canvas.gameUi.getClass();
                                gameUI.commandSeletedIndex = this.canvas.gameUi.commandSeletedIndex > 2 ? (byte)0 : this.canvas.gameUi.commandSeletedIndex;
                            }
                        }
                        break;
                    }
                    if (!Control.IsKeyDown(327712) || !this.canvas.gameUi.isCommand || this.canvas.gameUi.commandState != 0 || this.canvas.gameUi.commandSeletedIndex != 0) break;
                    this.canvas.gameUi.changeCommandState((byte)4);
                    this.nextTeach(role);
                    break;
                }
                case 24: {
                    if (!Control.IsKeyDown(327712) || !this.canvas.gameUi.isCommand || this.canvas.gameUi.commandState != 4) break;
                    this.canvas.gameUi.commandStr = this.canvas.gameUi.useItem(this.canvas.rolem.fplayerbox[0], this.canvas.gameUi.itemPack[0][this.canvas.gameUi.itemBoxSeletedIndex + this.canvas.gameUi.itemBoxStartIndex * 8], this.canvas.gameUi.itemBoxSeletedIndex + this.canvas.gameUi.itemBoxStartIndex * 8);
                    if (this.canvas.gameUi.addState == -2 && this.canvas.gameUi.itemBoxSeletedIndex + this.canvas.gameUi.itemBoxStartIndex * 8 < this.canvas.gameUi.itemPack[0].length) {
                        this.canvas.gameUi.setItemInfo(this.canvas.gameUi.itemPack[0][this.canvas.gameUi.itemBoxSeletedIndex + this.canvas.gameUi.itemBoxStartIndex * 8]);
                    }
                    this.canvas.gameUi.setCommandShowTime();
                    this.canvas.gameUi.changeCommandState((byte)3);
                    this.nextTeach(role);
                    break;
                }
                case 18: {
                    if (!Control.IsKeyDown(2)) break;
                    this.canvas.rolem.changeCurrentWeapon(role);
                    this.nextTeach(role);
                    break;
                }
                case 17: {
                    if (!Control.IsKeyDown(1024)) break;
                    this.nextTeach(role);
                    break;
                }
                case 25: {
                    this.iscityflushinfo = true;
                    this.canvas.changeGameState(this.canvas.lastgamestate);
                    this.startEvent(role, this.teachdata[this.teachindex][this.teachstep][1]);
                    this.teachfinish();
                    break;
                }
                case 16: {
                    this.startEvent(role, this.teachdata[this.teachindex][this.teachstep][1]);
                    this.teachfinish();
                    break;
                }
                case 15: {
                    if (!Control.IsKeyDown(1)) break;
                    this.nextTeach(role);
                    break;
                }
                case 13: {
                    if (!Control.IsKeyDown(1)) break;
                    this.nextTeach(role);
                    break;
                }
                case 14: {
                    if (!Control.IsKeyDown(1)) break;
                    this.nextTeach(role);
                    break;
                }
                case 6: {
                    if (!Control.IsKeyDown(131072)) break;
                    this.canvas.changeGameState(7);
                    this.canvas.gameUi.changeUiState((byte)1);
                    this.canvas.gameUi.initGameMenu();
                    this.nextTeach(role);
                    break;
                }
                case 7: {
                    if (Control.IsKeyDown(4100) || Control.IsKeyHold(4100, 10)) {
                        int n;
                        GameUI gameUI = this.canvas.gameUi;
                        this.canvas.gameUi.gameMenuSelectIndex = (byte)(this.canvas.gameUi.gameMenuSelectIndex - 1);
                        if (this.canvas.gameUi.gameMenuSelectIndex < 0) {
                            this.canvas.gameUi.getClass();
                            n = 5 - 1;
                        } else {
                            n = this.canvas.gameUi.gameMenuSelectIndex;
                        }
                        gameUI.gameMenuSelectIndex = (byte)n;
                        break;
                    }
                    if (Control.IsKeyDown(8448) || Control.IsKeyHold(8448, 10)) {
                        GameUI gameUI = this.canvas.gameUi;
                        this.canvas.gameUi.gameMenuSelectIndex = (byte)(this.canvas.gameUi.gameMenuSelectIndex + 1);
                        this.canvas.gameUi.getClass();
                        gameUI.gameMenuSelectIndex = this.canvas.gameUi.gameMenuSelectIndex > 5 - 1 ? (byte)0 : this.canvas.gameUi.gameMenuSelectIndex;
                        break;
                    }
                    if (Control.IsKeyDown(327712)) {
                        if (this.canvas.gameUi.gameMenuSelectIndex != 2) break;
                        this.canvas.gameUi.skillBoxIndex = 0;
                        this.canvas.gameUi.changeUiState((byte)9);
                        this.nextTeach(role);
                        break;
                    }
                    if (!Control.IsKeyDown(131072)) break;
                    this.canvas.gameUi.settishi("\u8bf7\u9009\u62e9\u6280\u80fd\u5347\u7ea7\u754c\u9762", (byte)0);
                    break;
                }
                case 8: {
                    if (Control.IsKeyDown(16400)) {
                        this.canvas.gameUi.skillBoxIndex = (byte)(this.canvas.gameUi.skillBoxIndex - 1);
                        this.canvas.gameUi.skillBoxIndex = this.canvas.gameUi.skillBoxIndex < 0 ? (byte)0 : this.canvas.gameUi.skillBoxIndex;
                        break;
                    }
                    if (Control.IsKeyDown(32832)) {
                        this.canvas.gameUi.skillBoxIndex = (byte)(this.canvas.gameUi.skillBoxIndex + 1);
                        this.canvas.gameUi.skillBoxIndex = this.canvas.gameUi.skillBoxIndex > 1 ? (byte)1 : this.canvas.gameUi.skillBoxIndex;
                        break;
                    }
                    if (Control.IsKeyDown(262176)) {
                        if (this.canvas.gameUi.skillBoxIndex != 0) break;
                        this.canvas.gameUi.skillBoxState = 1;
                        this.canvas.gameUi.skillBoxSeletedIndex = 0;
                        this.canvas.gameUi.setSkillInfo1(this.canvas.gameUi.skillIndexData[this.canvas.gameUi.skillBoxIndex][this.canvas.gameUi.skillBoxSeletedIndex]);
                        this.canvas.gameUi.changeActionUiState((byte)9, (byte)7, (byte)0);
                        this.nextTeach(role);
                        break;
                    }
                    if (!Control.IsKeyDown(131072)) break;
                    this.canvas.gameUi.settishi("\u8bf7\u9009\u62e9\u5251\u6280\u80fd", (byte)0);
                    break;
                }
                case 10: {
                    if (Control.IsKeyDown(4100)) {
                        if (this.canvas.gameUi.commandState < 0 || this.canvas.gameUi.commandState >= this.canvas.gameUi.SKILLCOMMANDTOTAL.length) break;
                        this.canvas.gameUi.commandSeletedIndex = (byte)(this.canvas.gameUi.commandSeletedIndex - 1);
                        this.canvas.gameUi.commandSeletedIndex = (byte)(this.canvas.gameUi.commandSeletedIndex < 0 ? this.canvas.gameUi.SKILLCOMMANDTOTAL[this.canvas.gameUi.commandState] - 1 : this.canvas.gameUi.commandSeletedIndex);
                        break;
                    }
                    if (Control.IsKeyDown(8448)) {
                        if (this.canvas.gameUi.commandState < 0 || this.canvas.gameUi.commandState >= this.canvas.gameUi.SKILLCOMMANDTOTAL.length) break;
                        this.canvas.gameUi.commandSeletedIndex = (byte)(this.canvas.gameUi.commandSeletedIndex + 1);
                        this.canvas.gameUi.commandSeletedIndex = this.canvas.gameUi.commandSeletedIndex > this.canvas.gameUi.SKILLCOMMANDTOTAL[this.canvas.gameUi.commandState] - 1 ? (byte)0 : this.canvas.gameUi.commandSeletedIndex;
                        break;
                    }
                    if (Control.IsKeyDown(262176)) {
                        if (this.canvas.gameUi.commandSeletedIndex != 0) break;
                        this.canvas.rolem.skillState[this.canvas.gameUi.skillIndexData[this.canvas.gameUi.skillBoxIndex][this.canvas.gameUi.skillBoxSeletedIndex]][0] = 1;
                        byte[] byArray = this.canvas.rolem.skillState[this.canvas.gameUi.skillIndexData[this.canvas.gameUi.skillBoxIndex][this.canvas.gameUi.skillBoxSeletedIndex]];
                        byArray[1] = (byte)(byArray[1] + 1);
                        --this.canvas.rolem.playerCurrentSkillPoint;
                        ++this.canvas.rolem.playerUseedSkillPoint;
                        this.canvas.gameUi.finishCommand();
                        this.canvas.gameUi.skillcurrentactionstate = 1;
                        this.canvas.gameUi.skillframeindex = 0;
                        this.nextTeach(role);
                        break;
                    }
                    if (!Control.IsKeyDown(131072)) break;
                    this.canvas.gameUi.settishi("\u8bf7\u9009\u62e9\u5f00\u542f", (byte)0);
                    break;
                }
                case 11: {
                    if (Control.IsKeyDown(4100)) {
                        if (this.canvas.gameUi.commandState < 0 || this.canvas.gameUi.commandState >= this.canvas.gameUi.SKILLCOMMANDTOTAL.length) break;
                        this.canvas.gameUi.commandSeletedIndex = (byte)(this.canvas.gameUi.commandSeletedIndex - 1);
                        this.canvas.gameUi.commandSeletedIndex = (byte)(this.canvas.gameUi.commandSeletedIndex < 0 ? this.canvas.gameUi.SKILLCOMMANDTOTAL[this.canvas.gameUi.commandState] - 1 : this.canvas.gameUi.commandSeletedIndex);
                        break;
                    }
                    if (Control.IsKeyDown(8448)) {
                        if (this.canvas.gameUi.commandState < 0 || this.canvas.gameUi.commandState >= this.canvas.gameUi.SKILLCOMMANDTOTAL.length) break;
                        this.canvas.gameUi.commandSeletedIndex = (byte)(this.canvas.gameUi.commandSeletedIndex + 1);
                        this.canvas.gameUi.commandSeletedIndex = this.canvas.gameUi.commandSeletedIndex > this.canvas.gameUi.SKILLCOMMANDTOTAL[this.canvas.gameUi.commandState] - 1 ? (byte)0 : this.canvas.gameUi.commandSeletedIndex;
                        break;
                    }
                    if (Control.IsKeyDown(262176)) {
                        if (this.canvas.gameUi.commandSeletedIndex != 0) break;
                        this.canvas.gameUi.setSkillKey();
                        this.canvas.gameUi.commandSeletedIndex = 0;
                        this.nextTeach(role);
                        break;
                    }
                    if (!Control.IsKeyDown(131072)) break;
                    this.canvas.gameUi.settishi("\u8bf7\u9009\u62e9\u88c5\u5907", (byte)0);
                    break;
                }
                case 12: {
                    if (Control.IsKeyDown(4100)) {
                        if (this.canvas.gameUi.commandState < 0 || this.canvas.gameUi.commandState >= this.canvas.gameUi.SKILLCOMMANDTOTAL.length) break;
                        this.canvas.gameUi.commandSeletedIndex = (byte)(this.canvas.gameUi.commandSeletedIndex - 1);
                        this.canvas.gameUi.commandSeletedIndex = (byte)(this.canvas.gameUi.commandSeletedIndex < 0 ? this.canvas.gameUi.SKILLCOMMANDTOTAL[this.canvas.gameUi.commandState] - 1 : this.canvas.gameUi.commandSeletedIndex);
                        break;
                    }
                    if (Control.IsKeyDown(8448)) {
                        if (this.canvas.gameUi.commandState < 0 || this.canvas.gameUi.commandState >= this.canvas.gameUi.SKILLCOMMANDTOTAL.length) break;
                        this.canvas.gameUi.commandSeletedIndex = (byte)(this.canvas.gameUi.commandSeletedIndex + 1);
                        this.canvas.gameUi.commandSeletedIndex = this.canvas.gameUi.commandSeletedIndex > this.canvas.gameUi.SKILLCOMMANDTOTAL[this.canvas.gameUi.commandState] - 1 ? (byte)0 : this.canvas.gameUi.commandSeletedIndex;
                        break;
                    }
                    if (Control.IsKeyDown(262176)) {
                        switch (this.canvas.gameUi.commandSeletedIndex) {
                            case 0: {
                                this.canvas.rolem.weaponCriticalKey7[this.canvas.gameUi.skillBoxIndex][0] = this.canvas.gameUi.skillIndexData[this.canvas.gameUi.skillBoxIndex][this.canvas.gameUi.skillBoxSeletedIndex];
                                this.canvas.rolem.weaponCriticalKey7[this.canvas.gameUi.skillBoxIndex][1] = 0;
                                this.canvas.gameUi.finishCommand();
                                this.nextTeach(role);
                                break;
                            }
                            case 1: {
                                this.canvas.rolem.weaponCriticalKey9[this.canvas.gameUi.skillBoxIndex][0] = this.canvas.gameUi.skillIndexData[this.canvas.gameUi.skillBoxIndex][this.canvas.gameUi.skillBoxSeletedIndex];
                                this.canvas.rolem.weaponCriticalKey9[this.canvas.gameUi.skillBoxIndex][1] = 0;
                                this.canvas.gameUi.finishCommand();
                                this.nextTeach(role);
                            }
                        }
                        break;
                    }
                    if (!Control.IsKeyDown(131072)) break;
                    this.canvas.gameUi.settishi("\u8bf7\u88c5\u5907\u6280\u80fd", (byte)0);
                    break;
                }
                case 9: {
                    if (Control.IsKeyDown(4100)) {
                        if (this.canvas.gameUi.skillKeyData[this.canvas.gameUi.skillBoxIndex][0][this.canvas.gameUi.skillBoxSeletedIndex] == -1 || this.canvas.gameUi.skillcurrentactionstate != 0) break;
                        this.canvas.gameUi.skillBoxSeletedIndex = this.canvas.gameUi.skillKeyData[this.canvas.gameUi.skillBoxIndex][0][this.canvas.gameUi.skillBoxSeletedIndex];
                        this.canvas.gameUi.setSkillInfo1(this.canvas.gameUi.skillIndexData[this.canvas.gameUi.skillBoxIndex][this.canvas.gameUi.skillBoxSeletedIndex]);
                        break;
                    }
                    if (Control.IsKeyDown(8448)) {
                        if (this.canvas.gameUi.skillKeyData[this.canvas.gameUi.skillBoxIndex][1][this.canvas.gameUi.skillBoxSeletedIndex] == -1 || this.canvas.gameUi.skillcurrentactionstate != 0) break;
                        this.canvas.gameUi.skillBoxSeletedIndex = this.canvas.gameUi.skillKeyData[this.canvas.gameUi.skillBoxIndex][1][this.canvas.gameUi.skillBoxSeletedIndex];
                        this.canvas.gameUi.setSkillInfo1(this.canvas.gameUi.skillIndexData[this.canvas.gameUi.skillBoxIndex][this.canvas.gameUi.skillBoxSeletedIndex]);
                        break;
                    }
                    if (Control.IsKeyDown(16400)) {
                        if (this.canvas.gameUi.skillKeyData[this.canvas.gameUi.skillBoxIndex][2][this.canvas.gameUi.skillBoxSeletedIndex] == -1 || this.canvas.gameUi.skillcurrentactionstate != 0) break;
                        this.canvas.gameUi.skillBoxSeletedIndex = this.canvas.gameUi.skillKeyData[this.canvas.gameUi.skillBoxIndex][2][this.canvas.gameUi.skillBoxSeletedIndex];
                        this.canvas.gameUi.setSkillInfo1(this.canvas.gameUi.skillIndexData[this.canvas.gameUi.skillBoxIndex][this.canvas.gameUi.skillBoxSeletedIndex]);
                        break;
                    }
                    if (Control.IsKeyDown(32832)) {
                        if (this.canvas.gameUi.skillKeyData[this.canvas.gameUi.skillBoxIndex][3][this.canvas.gameUi.skillBoxSeletedIndex] == -1 || this.canvas.gameUi.skillcurrentactionstate != 0) break;
                        this.canvas.gameUi.skillBoxSeletedIndex = this.canvas.gameUi.skillKeyData[this.canvas.gameUi.skillBoxIndex][3][this.canvas.gameUi.skillBoxSeletedIndex];
                        this.canvas.gameUi.setSkillInfo1(this.canvas.gameUi.skillIndexData[this.canvas.gameUi.skillBoxIndex][this.canvas.gameUi.skillBoxSeletedIndex]);
                        break;
                    }
                    if (Control.IsKeyDown(262176)) {
                        if (this.canvas.gameUi.skillIndexData[this.canvas.gameUi.skillBoxIndex][this.canvas.gameUi.skillBoxSeletedIndex] != 0) break;
                        if (this.canvas.rolem.skillState[this.canvas.gameUi.skillIndexData[this.canvas.gameUi.skillBoxIndex][this.canvas.gameUi.skillBoxSeletedIndex]][0] == 0) {
                            this.canvas.gameUi.startCommand();
                            this.canvas.gameUi.changeCommandState((byte)0);
                            this.canvas.gameUi.commandSeletedIndex = 0;
                        } else {
                            this.canvas.gameUi.startCommand();
                            this.canvas.gameUi.changeCommandState((byte)2);
                            this.canvas.gameUi.commandSeletedIndex = 0;
                        }
                        this.nextTeach(role);
                        break;
                    }
                    if (!Control.IsKeyDown(131072)) break;
                    this.canvas.gameUi.settishi("\u8bf7\u9009\u62e9\u5251\u5203\u65a9", (byte)0);
                    break;
                }
                case 0: {
                    if (Control.IsKeyDown(4100)) {
                        this.cityBuildSelectIndex = (byte)(this.cityBuildSelectIndex - 1);
                        this.cityBuildSelectIndex = (byte)(this.cityBuildSelectIndex < 0 ? this.citybuildnum - 1 : this.cityBuildSelectIndex);
                        this.setcitybuildinfo(this.currentcitybuildname[this.cityBuildSelectIndex]);
                    } else if (Control.IsKeyDown(8448)) {
                        this.cityBuildSelectIndex = (byte)(this.cityBuildSelectIndex + 1);
                        this.cityBuildSelectIndex = this.cityBuildSelectIndex > this.citybuildnum - 1 ? (byte)0 : this.cityBuildSelectIndex;
                        this.setcitybuildinfo(this.currentcitybuildname[this.cityBuildSelectIndex]);
                    }
                    if (!Control.IsKeyDown(327712) || this.cityMapIndex != this.teachdata[this.teachindex][this.teachstep][1]) break;
                    if (this.currentbuildindex[this.cityBuildSelectIndex] == this.teachdata[this.teachindex][this.teachstep][3]) {
                        this.canvas.gameUi.cityIntroBoxState = 0;
                        this.canvas.gameUi.cityIntroBoxIndex = 0;
                        this.canvas.gameUi.setCityIntroBossData(this.cityMapIndex);
                        if (this.canvas.gameUi.playerMoney < 1600) {
                            this.canvas.gameUi.playerMoney = 1600;
                        }
                        this.canvas.gameUi.changeUiState((byte)11);
                        this.canvas.changeGameState(7);
                        this.nextTeach(role);
                        break;
                    }
                    this.canvas.gameUi.settishi("\u8bf7\u70b9\u51fb\u661f\u9645\u795e\u6bbf", (byte)0);
                    break;
                }
                case 1: {
                    if (Control.IsKeyDown(4100)) {
                        this.canvas.gameUi.cityIntroBoxIndex = (byte)(this.canvas.gameUi.cityIntroBoxIndex - 1);
                        this.canvas.gameUi.cityIntroBoxIndex = (byte)(this.canvas.gameUi.cityIntroBoxIndex < 0 ? 2 : (int)this.canvas.gameUi.cityIntroBoxIndex);
                    } else if (Control.IsKeyDown(8448)) {
                        this.canvas.gameUi.cityIntroBoxIndex = (byte)(this.canvas.gameUi.cityIntroBoxIndex + 1);
                        byte by = this.canvas.gameUi.cityIntroBoxIndex = this.canvas.gameUi.cityIntroBoxIndex > 2 ? (byte)0 : this.canvas.gameUi.cityIntroBoxIndex;
                    }
                    if (!Control.IsKeyDown(327712) || this.canvas.gameUi.uiState != this.teachdata[this.teachindex][this.teachstep][1] || this.canvas.gameUi.cityIntroBoxState != this.teachdata[this.teachindex][this.teachstep][2] || this.canvas.gameUi.cityIntroBoxIndex != this.teachdata[this.teachindex][this.teachstep][3]) break;
                    this.canvas.gameUi.cityIntroBoxState = 1;
                    this.nextTeach(role);
                    break;
                }
                case 2: {
                    if (!Control.IsKeyDown(327712) || this.canvas.gameUi.uiState != this.teachdata[this.teachindex][this.teachstep][1] || this.canvas.gameUi.cityIntroBoxState != this.teachdata[this.teachindex][this.teachstep][2] || this.canvas.gameUi.isCommand) break;
                    this.canvas.gameUi.startCommand();
                    this.canvas.gameUi.commandSeletedIndex = 0;
                    this.canvas.gameUi.changeCommandState((byte)0);
                    this.nextTeach(role);
                    break;
                }
                case 3: {
                    if (this.canvas.gameUi.isCommand) {
                        switch (this.canvas.gameUi.commandState) {
                            case 0: {
                                if (Control.IsKeyDown(4100)) {
                                    byte by;
                                    GameUI gameUI = this.canvas.gameUi;
                                    this.canvas.gameUi.commandSeletedIndex = (byte)(this.canvas.gameUi.commandSeletedIndex - 1);
                                    if (this.canvas.gameUi.commandSeletedIndex < 0) {
                                        this.canvas.gameUi.getClass();
                                        by = 1;
                                    } else {
                                        by = this.canvas.gameUi.commandSeletedIndex;
                                    }
                                    gameUI.commandSeletedIndex = by;
                                    break;
                                }
                                if (!Control.IsKeyDown(8448)) break;
                                GameUI gameUI = this.canvas.gameUi;
                                this.canvas.gameUi.commandSeletedIndex = (byte)(this.canvas.gameUi.commandSeletedIndex + 1);
                                this.canvas.gameUi.getClass();
                                byte by = gameUI.commandSeletedIndex = this.canvas.gameUi.commandSeletedIndex > 1 ? (byte)0 : this.canvas.gameUi.commandSeletedIndex;
                            }
                        }
                    }
                    if (!Control.IsKeyDown(327712) || this.canvas.gameUi.uiState != this.teachdata[this.teachindex][this.teachstep][1] || this.canvas.gameUi.cityIntroBoxState != this.teachdata[this.teachindex][this.teachstep][2] || !this.canvas.gameUi.isCommand || this.canvas.gameUi.commandSeletedIndex != this.teachdata[this.teachindex][this.teachstep][3]) break;
                    this.canvas.gameUi.addMoney(-1600);
                    this.canvas.gameUi.finishCommand();
                    this.canvas.startSwitchScrren(0, (byte)4, 1);
                    this.nextTeach(role);
                    break;
                }
                case 4: {
                    if (Control.IsKeyDown(4100)) {
                        if (this.universekeydata[0][this.universeMapIndex] > -1) {
                            this.universeMapIndex = this.universekeydata[0][this.universeMapIndex];
                            this.setuniversemousepos();
                            this.isuniverseflushinfo = true;
                            this.universewaittime = 5;
                            this.isdrawuniverselist = false;
                        }
                    } else if (Control.IsKeyDown(8448) && this.universekeydata[1][this.universeMapIndex] > -1) {
                        this.universeMapIndex = this.universekeydata[1][this.universeMapIndex];
                        this.setuniversemousepos();
                        this.isuniverseflushinfo = true;
                        this.universewaittime = 5;
                        this.isdrawuniverselist = false;
                    }
                    if (Control.IsKeyDown(16400)) {
                        if (this.universekeydata[2][this.universeMapIndex] > -1) {
                            this.universeMapIndex = this.universekeydata[2][this.universeMapIndex];
                            this.setuniversemousepos();
                            this.isuniverseflushinfo = true;
                            this.universewaittime = 5;
                            this.isdrawuniverselist = false;
                        }
                    } else if (Control.IsKeyDown(32832) && this.universekeydata[3][this.universeMapIndex] > -1) {
                        this.universeMapIndex = this.universekeydata[3][this.universeMapIndex];
                        this.setuniversemousepos();
                        this.isuniverseflushinfo = true;
                        this.universewaittime = 5;
                        this.isdrawuniverselist = false;
                    }
                    if (!Control.IsKeyDown(327712) || this.universeMapState != 0) break;
                    if (this.universeMapIndex == this.teachdata[this.teachindex][this.teachstep][1]) {
                        this.inituniverseui(false);
                        this.universeMapSelectIndex = 0;
                        universeMapStartIndex = 0;
                        this.universeMapState = 1;
                        this.setmapinfo(this.universeMapData[this.universeMapIndex][0]);
                        this.nextTeach(role);
                        break;
                    }
                    this.canvas.gameUi.setTalk("\u5c3c\u5e03\uff1a\u8fd8\u662f\u5feb\u53bb\u65af\u5361\u683c\u62c9\u514b\u4e18\u9675\u652f\u63f4\u5427\u3002", 0, 0);
                    this.canvas.changeGameState(6);
                    break;
                }
                case 5: {
                    byte tempuniverseMapSelectIndex = this.universeMapSelectIndex;
                    if (Control.IsKeyDown(4100)) {
                        if (this.universeMapSelectIndex > 0) {
                            this.universeMapSelectIndex = (byte)(this.universeMapSelectIndex - 1);
                        } else if (universeMapStartIndex > 0) {
                            universeMapStartIndex = (byte)(universeMapStartIndex - 1);
                        }
                        this.universeMapSelectActionFlag = true;
                        this.inituniverseui(tempuniverseMapSelectIndex != this.universeMapSelectIndex);
                        this.setmapinfo(this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]);
                        break;
                    }
                    if (Control.IsKeyDown(8448)) {
                        if (this.universeMapSelectIndex < (5 < this.universeMapData[this.universeMapIndex].length ? 5 : this.universeMapData[this.universeMapIndex].length) - 1) {
                            this.universeMapSelectIndex = (byte)(this.universeMapSelectIndex + 1);
                        } else if (5 < this.universeMapData[this.universeMapIndex].length && universeMapStartIndex < this.universeMapData[this.universeMapIndex].length - 5) {
                            universeMapStartIndex = (byte)(universeMapStartIndex + 1);
                        }
                        this.universeMapSelectActionFlag = true;
                        this.inituniverseui(tempuniverseMapSelectIndex != this.universeMapSelectIndex);
                        this.setmapinfo(this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]);
                        break;
                    }
                    if (Control.IsKeyDown(327712) && !this.canvas.isShowSwitchScreen) {
                        if (this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex] == this.teachdata[this.teachindex][this.teachstep][1]) {
                            this.universeMapSelectActionFlag = false;
                            this.nextMapIndex = this.universeMapAreaData[this.universeMapData[this.universeMapIndex][this.universeMapSelectIndex + universeMapStartIndex]][1];
                            this.nextDoorIndex = 0;
                            this.isdrawuniverselist = false;
                            if (this.canvas.rolem.currentplayernameindex == this.canvas.rolem.lastplayernameindex) {
                                this.canvas.startSwitchScrren(0, (byte)5, 2);
                            } else {
                                this.canvas.startSwitchScrren(0, (byte)5, 8);
                            }
                            universeMapStartIndex = 0;
                            this.nextTeach(role);
                            this.canvas.createloadimage((byte)this.universeMapAreaData[this.maparea[this.nextMapIndex]][3]);
                            break;
                        }
                        this.canvas.gameUi.setTalk("\u5c3c\u5e03\uff1a\u8fd8\u662f\u5feb\u53bb\u65af\u5361\u683c\u62c9\u514b\u4e18\u9675\u652f\u63f4\u5427\u3002", 0, 0);
                        this.canvas.changeGameState(6);
                        break;
                    }
                    if (!Control.IsKeyDown(131072)) break;
                    this.canvas.gameUi.setTalk("\u5c3c\u5e03\uff1a\u8fd8\u662f\u5feb\u53bb\u65af\u5361\u683c\u62c9\u514b\u4e18\u9675\u652f\u63f4\u5427\u3002", 0, 0);
                    this.canvas.changeGameState(6);
                }
            }
        }
    }
}

