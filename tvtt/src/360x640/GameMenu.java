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
import src.Util;

public class GameMenu
implements GameData {
    GameCanvas canvas;
    byte menuState;
    byte lastMenuState;
    byte newMenuState;
    int rmsnum;
    byte[] menudata;
    byte savegamestate;
    byte menutotal;
    String[] commandstrs;
    boolean iscommand;
    byte commandstate;
    Image[] menuImage;
    short[] menuModules;
    short[][] menuFrames;
    short[][] menuActions;
    byte actionIndex;
    byte actionFrameIndex;
    byte frameDelay;
    boolean isActionOver;
    byte[] menuImgData = new byte[]{0, 1, 2};
    int[][] effectData;
    int[] effectWidth;
    int[] effectHeight;
    String[][] menuStr = new String[][]{{"\u7ee7\u7eed\u6e38\u620f", "Load Game"}, {"\u65b0\u7684\u6e38\u620f", "New Game"}, {"\u6e38\u620f\u8bbe\u7f6e", "Game Option"}, {"\u6e38\u620f\u5e2e\u52a9", "Game Help"}, {"\u6e38\u620f\u5173\u4e8e", "About"}, {"\u9000\u51fa\u6e38\u620f", "Quit Game"}, new String[0], new String[0], {"\u66f4\u591a\u6e38\u620f", "More Game"}, new String[0]};
    public static final int EXTRA_NAME_INFO_SHOW_X = 550;
    public static final int EXTRA_NAME_INFO_SHOW_Y = 170;
    byte menuIndex;
    byte menuCartoonState = (byte)-2;
    byte menuCartoonTime;
    private int[][] light;
    byte[] menuData = new byte[]{0, 1, 2, 3, 4, 5};
    int menuSpeedX;
    int hire = 1;
    int showextranameoffset_y = 0;
    public static final int MAIN_MENU_OFFSET_Y = -22;
    public static final int MAIN_MENU_OFFSET_X = 165;
    final byte switchscreenrectbsew = (byte)12;
    final byte switchscreenrectdw = (byte)3;
    byte switchscreencounter;
    byte[] switchscreenrectw = new byte[15];

    public GameMenu(GameCanvas canvas) {
        this.canvas = canvas;
        this.initMenu();
        this.initSwitchScreen();
        canvas.rms.openRMS();
        this.rmsnum = canvas.rms.getNumRMS();
        if (this.rmsnum == 0) {
            byte[] data = new byte[]{6, 14, 8, 9, 10, 11};
            this.menudata = data;
            this.menutotal = (byte)this.menudata.length;
            this.menuIndex = 0;
        } else {
            byte[] tempdata = canvas.getRecord(6);
            this.savegamestate = tempdata[0];
            canvas.rolem.currentplayernameindex = tempdata[1];
            byte[] data = new byte[]{6, 7, 14, 8, 9, 10, 11};
            this.menudata = data;
            this.menutotal = (byte)this.menudata.length;
            this.menuIndex = 1;
        }
        this.initLight();
    }

    public void initMenu() {
        Util.tempData = new byte[20000];
        Util.position[0] = 0;
        Util.getStream(Util.tempData, "/data/menu.data", 0);
        this.menuModules = Util.loadInfShort(Util.tempData, this.menuModules, Util.position);
        this.menuFrames = Util.loadInfShort(Util.tempData, this.menuFrames, Util.position);
        this.menuActions = Util.loadInfShort(Util.tempData, this.menuActions, Util.position);
        this.menuImage = new Image[this.menuImgData.length + 2];
        for (int i = 0; i < this.menuImgData.length; ++i) {
            this.menuImage[i] = Util.createImage("/menu/" + this.menuImgData[i] + ".png");
        }
        Util.tempData = null;
    }

    public void drawMenuModule(int mindex, int x, int y, int anchor, int trans) {
        this.canvas.drawImage(this.menuImage[this.menuModules[mindex * 5]], x, y, anchor, trans, this.menuModules, mindex);
    }

    public void drawStaticUi(short frameindex) {
        for (int i = 0; i < this.menuFrames[frameindex].length; i += 4) {
            short moduleIndex = this.menuFrames[frameindex][i];
            this.canvas.drawModule(this.menuImage[this.menuModules[moduleIndex * 5]], this.menuFrames[frameindex][i + 1], (int)this.menuFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.menuFrames[frameindex][i + 3]], 320, 180, this.menuModules, (int)moduleIndex);
        }
    }

    public void drawUiSprite(byte actionIndex) {
        short frameindex = this.menuActions[actionIndex][this.actionFrameIndex + 1];
        short moduleIndex = 0;
        for (int i = 0; i < this.menuFrames[frameindex].length; i += 4) {
            moduleIndex = this.menuFrames[frameindex][i];
            this.canvas.drawModule(this.menuImage[this.menuModules[moduleIndex * 5]], this.menuFrames[frameindex][i + 1], (int)this.menuFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.menuFrames[frameindex][i + 3]], 320, 180, this.menuModules, (int)moduleIndex);
        }
        this.nextUiFrame(actionIndex);
    }

    public void drawUiSprite(byte actionIndex, int x, int y) {
        short frameindex = this.menuActions[actionIndex][this.actionFrameIndex + 1];
        short moduleIndex = 0;
        for (int i = 0; i < this.menuFrames[frameindex].length; i += 4) {
            moduleIndex = this.menuFrames[frameindex][i];
            this.canvas.drawModule(this.menuImage[this.menuModules[moduleIndex * 5]], this.menuFrames[frameindex][i + 1], (int)this.menuFrames[frameindex][i + 2], 20, TRANS_TO_DG[this.menuFrames[frameindex][i + 3]], x, y, this.menuModules, (int)moduleIndex);
        }
        this.nextUiFrame(actionIndex);
    }

    public void nextUiFrame(byte actionIndex) {
        this.frameDelay = (byte)(this.frameDelay + 1);
        if (this.frameDelay == this.menuActions[actionIndex][this.actionFrameIndex]) {
            this.frameDelay = 0;
            this.actionFrameIndex = (byte)(this.actionFrameIndex + 2);
            if (this.actionFrameIndex == this.menuActions[actionIndex].length) {
                this.actionFrameIndex = 0;
                this.isActionOver = true;
            }
        }
    }

    public void initMenuCartoonBack() {
        for (int i = 0; i < this.canvas.screenData.length; ++i) {
            this.canvas.screenData[i] = -1;
        }
    }

    public void initMenuBack() {
        for (int i = 0; i < this.canvas.screenData.length; ++i) {
            this.canvas.screenData[i] = -939524096;
        }
    }

    public void startCommand() {
        this.iscommand = true;
        this.initMenuBack();
    }

    public void drawMenuBack() {
        for (int i = 0; i < 360; ++i) {
            this.canvas.g.drawRGB(this.canvas.screenData, 0, 640, 0, 0 + i, 640, 1, true);
        }
    }

    public void drawExtraNameInfo(int offsetx, int offsety) {
        this.canvas.drawZkString("$0\uff08\u534e\u5a31\uff09", 550 + offsetx, 170 + offsety, 0, this.canvas.zkData);
    }

    public void drawMenu() {
        this.canvas.fillRect(0, 0, 0, 640, 360);
        byte actionFrameIndex_Begin = 8;
        byte actionFrameIndex_End = 12;
        this.showextranameoffset_y = 4;
        switch (this.menuState) {
            case 0: {
                this.drawMenuCartoon();
                if ((this.menuCartoonState != 2 || this.actionFrameIndex < actionFrameIndex_Begin) && this.menuCartoonState <= 2) break;
                this.drawExtraNameInfo(0, this.menuCartoonState == 2 && this.actionFrameIndex >= actionFrameIndex_Begin && this.actionFrameIndex <= actionFrameIndex_End ? (this.actionFrameIndex / 2 % 2 == 1 ? this.showextranameoffset_y : -this.showextranameoffset_y) : -this.showextranameoffset_y);
                break;
            }
            case 1: {
                this.drawGameMenu();
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
                this.canvas.drawGameAbout();
            }
        }
    }

    public void menuKeyProcess() {
        switch (this.menuState) {
            case 0: {
                this.menuCartoonKeyProcess();
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
                this.canvas.aboutKeyProcess();
            }
        }
    }

    private void drawMenuCartoon() {
        switch (this.menuCartoonState) {
            case -2: {
                this.drawStaticUi((short)0);
                this.drawSwitchScreenUI();
                break;
            }
            case -1: {
                this.drawStaticUi((short)0);
                break;
            }
            case 0: {
                this.drawStaticUi((short)0);
                break;
            }
            case 1: {
                this.drawStaticUi((short)0);
                break;
            }
            case 2: {
                this.drawUiSprite((byte)0);
                break;
            }
            case 3: {
                this.drawStaticUi((short)5);
            }
        }
        this.menuCartoonProcess();
    }

    public void initLight() {
        this.light = new int[15][6];
    }

    public void lightUpDate() {
        for (int i = 0; i < this.light.length; ++i) {
            if (this.light[i][1] <= this.light[i][5]) {
                this.light[i][0] = (short)(Util.getARandomInt(740) + 0 - 50);
                this.light[i][1] = (short)(360 + Util.getARandomInt(50));
                this.light[i][2] = (short)Util.getBRandomInt(5);
                this.light[i][3] = -((short)(Util.getARandomInt(2) + 2));
                this.light[i][4] = (short)Util.getARandomInt(2);
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
            this.drawMenuModule(28 + this.light[i][4], this.light[i][0], this.light[i][1], 3, 0);
        }
    }

    private void menuCartoonProcess() {
        switch (this.menuCartoonState) {
            case -2: {
                this.switchScreenProcess();
                break;
            }
            case -1: {
                this.menuCartoonTime = (byte)(this.menuCartoonTime + 1);
                if (this.menuCartoonTime <= 1) break;
                this.menuCartoonTime = 0;
                this.menuCartoonState = 0;
                break;
            }
            case 0: {
                this.menuCartoonTime = (byte)(this.menuCartoonTime + 1);
                if (this.menuCartoonTime <= 7) break;
                this.menuCartoonTime = 0;
                this.menuCartoonState = 1;
                break;
            }
            case 1: {
                this.menuCartoonTime = (byte)(this.menuCartoonTime + 1);
                if (this.menuCartoonTime <= 1) break;
                this.menuCartoonTime = 0;
                this.menuCartoonState = (byte)2;
                break;
            }
            case 2: {
                if (!this.isActionOver) break;
                this.menuCartoonTime = 0;
                this.menuCartoonState = (byte)3;
                this.isActionOver = false;
                break;
            }
        }
    }

    private void menuCartoonKeyProcess() {
        if (Control.IsKeyDown(1)) {
            this.changeMenuState((byte)1);
        }
    }

    public void drawextraInfo() {
        this.drawExtraNameInfo(0, -this.showextranameoffset_y);
    }

    private void drawGameMenu() {
        this.drawStaticUi((short)6);
        this.drawextraInfo();
        this.drawLight();
        this.lightUpDate();
        this.drawMenuModule(this.menudata[this.menuIndex], 485, 268, 17, 0);
        this.drawMenuModule(this.menudata[this.menuIndex] + 9, 485, 308, 17, 0);
        this.canvas.gameUi.drawUiModule(178, 370 - this.menuSpeedX * this.hire, 281, 24, 0);
        this.canvas.gameUi.drawUiModule(178, 600 + this.menuSpeedX * this.hire, 281, 20, 3);
        if (++this.menuSpeedX == 2) {
            this.menuSpeedX = 0;
            this.hire *= -1;
        }
        if (this.iscommand) {
            this.drawMenuBack();
            this.canvas.gameUi.drawStaticUi((short)19);
            int tempy = 0;
            if (this.commandstrs.length > 1) {
                tempy = (this.commandstrs.length - 1) * (20 / this.commandstrs.length);
            }
            GameUI cfr_ignored_0 = this.canvas.gameUi;
            this.canvas.drawZkStringArry(this.commandstrs, 320, 133 - tempy, 17, 0, this.commandstrs.length, 0);
        }
    }

    private void gameMenuKeyProcess() {
        if (Control.IsKeyDown(16400)) {
            if (!this.iscommand) {
                this.menuIndex = (byte)(this.menuIndex - 1);
                this.menuIndex = (byte)(this.menuIndex < 0 ? this.menutotal - 1 : this.menuIndex);
            }
        } else if (Control.IsKeyDown(32832)) {
            if (!this.iscommand) {
                this.menuIndex = (byte)(this.menuIndex + 1);
                this.menuIndex = this.menuIndex > this.menutotal - 1 ? (byte)0 : this.menuIndex;
            }
        } else if (Control.IsKeyDown(327712)) {
            if (this.iscommand) {
                switch (this.commandstate) {
                    case 0: {
                        this.canvas.loadGameState(10, 1, (byte)0);
                        this.canvas.rolem.currentplayernameindex = 0;
                        break;
                    }
                    case 1: {
                        this.canvas.exitGame();
                    }
                }
            } else {
                block4 : switch (this.menudata[this.menuIndex]) {
                    case 7: {
                        switch (this.savegamestate) {
                            case 5: {
                                this.canvas.loadGameState(5, 10, (byte)0);
                                break block4;
                            }
                            case 4: {
                                this.canvas.loadGameState(4, 11, (byte)0);
                            }
                        }
                        break;
                    }
                    case 6: {
                        if (this.rmsnum == 0) {
                            this.canvas.loadGameState(10, 1, (byte)0);
                            this.canvas.rolem.currentplayernameindex = 0;
                            break;
                        }
                        this.startCommand();
                        this.commandstate = 0;
                        this.commandstrs = this.canvas.formatzkword("\u91cd\u65b0\u5f00\u59cb\u6e38\u620f\u4f1a\u6e05\u9664\u5b58\u6863\uff0c\u662f\u5426\u91cd\u65b0\u5f00\u59cb\u6e38\u620f\uff1f", 200, 0);
                        break;
                    }
                    case 8: {
                        this.canvas.gameUi.initUiScreenData();
                        this.changeMenuState((byte)2);
                        break;
                    }
                    case 9: {
                        this.canvas.gameUi.initUiScreenData();
                        this.canvas.gamehelpstate = 0;
                        this.canvas.gamehelpselectindex = 0;
                        this.changeMenuState((byte)3);
                        break;
                    }
                    case 10: {
                        this.canvas.gameUi.initUiScreenData();
                        this.canvas.initabout();
                        this.changeMenuState((byte)4);
                        break;
                    }
                    case 11: {
                        this.canvas.createExitImage();
                        this.canvas.changeGameState(52);
                        break;
                    }
                    case 14: {
                        this.canvas.gameStop("http://wap.ttsy.org/gamecms/go/jpgd?cpId=701057&contentID=000048675000");
                    }
                }
            }
        } else if (Control.IsKeyDown(131072) && this.iscommand) {
            this.iscommand = false;
        }
    }

    public void changeMenuState(byte state) {
        this.lastMenuState = this.menuState;
        this.menuState = state;
    }

    public void initSwitchScreen() {
        this.switchscreencounter = 0;
        for (int i = 0; i < this.switchscreenrectw.length; ++i) {
            this.switchscreenrectw[i] = 12;
        }
    }

    public void drawSwitchScreenUI() {
        for (int i = 0; i < this.switchscreenrectw.length; ++i) {
            this.canvas.fillRect(0, 0, 0 + i * 12 * 2 + 12 - this.switchscreenrectw[i], 640, this.switchscreenrectw[i] * 2);
        }
    }

    public void switchScreenProcess() {
        this.switchscreencounter = (byte)(this.switchscreencounter + 1);
        byte by = this.switchscreencounter = this.switchscreencounter > this.switchscreenrectw.length - 1 ? (byte)0 : this.switchscreencounter;
        if (this.switchscreenrectw[0] == 0) {
            this.menuCartoonState = (byte)-1;
        }
        for (int i = 0; i < this.switchscreenrectw.length; ++i) {
            int n = i;
            this.switchscreenrectw[n] = (byte)(this.switchscreenrectw[n] - 3);
            this.switchscreenrectw[i] = this.switchscreenrectw[i] < 0 ? (byte)0 : this.switchscreenrectw[i];
        }
    }
}

