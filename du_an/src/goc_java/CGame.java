/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

//@#if SMS
//@#if SAMSUNG_SMS
//@import com.samsung.util.SMS;
//@import com.samsung.util.SM;
//@#endif
//@#if Channel == "QQ"
//@import com.qq.sms.QQSMS;
//@import javax.wireless.messaging.MessageConnection;
//@import javax.microedition.io.Connector;
//@import javax.wireless.messaging.TextMessage;
//@#endif
//@#if TS_SMS
//@import com.sohu.smsmo.as;
//@#endif
//@#endif
//@#if NOKIA_LIB
//@import com.nokia.mid.ui.*;
//@#endif
import java.io.*;
import java.util.*;

import javax.microedition.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.media.*;
import javax.microedition.rms.*;
import javax.wireless.messaging.*;

//@#endif
//@#if QQ_FORUM
//@import com.tencent.mbox.*;
//@import com.tencent.mbox.cp.*;
//@#endif
//@#if Channel == "GF"
//@#if GF_PACKAGE in ("CONNECT")
import com.emag.open.ChallengeBack;
//@#endif
import  com.emag.open.GameCenter;
//@#endif

public class CGame
//@#if QQ_FORUM
//@        extends Canvas implements Runnable, Const, InfoListener
//@#else
        extends Canvas implements Runnable, Const
//@#endif
{
//@@..\CGame\CGame_Variables.h
    //----战斗并行任务----
    static CParallel s_parallel;

    //----窗口----
    static CWnd s_wnd = null;

    //----菜单----
    /**
     * 注意,以下CWnd对象的静态声明不是为了能够在程序/父窗口中能够直接访问子窗口,而是出于节省内存,避免频繁实例化/释放CWnd对象.
     * 所以在使用中注意,不要跳过父窗口,使用这些静态对象直接访问子窗口,而要经由父窗口自身的逻辑调用子窗口逻辑.
     */
    static CWnd s_wndMainMenu = null;
    static CWnd s_wndBattleMain = null;
    static CWnd s_wndBattleAction = null;
    static CWnd s_wndBattleAim = null;
    static CWnd s_wndBattleDesc = null;
    //----闪屏----
    static int s_flashCount;
    static int s_flashCur;
    static int s_flashColor;
    static int s_flashInterval;
    static int s_flashFadeIn;
    static int s_flashFadeOut;

    //----镜头----
    //镜头当前的XY坐标
    static short s_cameraX;
    static short s_cameraY;
    static short s_cameraXOld;
    static short s_cameraYOld;
    static short s_cameraDestX;
    static short s_cameraDestY;
    static short s_cameraVX;
    static short s_cameraVY;
    static short s_cameraXMax;
    static short s_cameraYMax;
    static short s_battleCameraX;
    static short s_battleCameraY;
    static int s_cameraColor;
    static int s_cameraColorStart;
    static int s_cameraColorEnd;
    static int s_cameraColorCycle;
    static int s_cameraColorTimer;
    static int s_cameraShockTimer;
    static short[] s_cameraShock = new short[AMOUNT_CAMERA_SHOCK];
    static CActor s_actorCameraHook = null;
    static CActor s_actorHumanControl = null;
    static CActor s_actorLastControl = null;
    static int s_engage = -1;
    static int s_cameraState = CAMERA_STATE_FREE;
    static int s_cameraStateNext = -1;
    static CActor s_actorTarget = null;
    static CActor s_actorHalo = null;
    static CActor s_actorAchievement = null;
//@#if ALPHA_EFFECT
//@    //RGB操作
//@    static int[] s_argbData;
//@#endif

    //----实体链表----
    static CEntity s_entityList[] = new CEntity[AMOUNT_ENTITY_LIST];
    static CEntity s_tempList;

    //----战斗UI----
    static int s_uiActorQueueX = 0;

    //----战斗----
    static int s_enemyTeamID = 0;

    //----按键----
    static byte s_keyState[];
    static byte s_keyStateRealTime[];

    /**当前画笔*/
    static Graphics s_g = null;

    //----预读取数据----
    static int[] s_preLoadData = new int[AMOUNT_PRELOAD];


    static int[] s_timer = new int[TIMER_POOL_SIZE];
    static int[] s_timerInitValue = new int[TIMER_POOL_SIZE];
    static byte[] s_timerState = new byte[TIMER_POOL_SIZE];
    static short[] s_timerAttach = new short[TIMER_POOL_SIZE];

    //----特效----
    static int s_effectTime = 0;
    static int s_screenControlType = 0;
    static byte s_effectType;
    static int s_effectRange;
    static int s_effectRangeTarget;

    //----读取参数----

    /**
     * 读取场景的方式,包括4种:
     * SCENELOAD_TYPE_NEW 新游戏
     * SCENELOAD_TYPE_NORMAL 游戏中读取场景
     * SCENELOAD_TYPE_LOAD 读取玩家进度
     * SCENELOAD_TYPE_DEBUG 调试跳关
     */
    static int s_sceneLoadType = SCENELOAD_TYPE_NEW;
    static int s_loadData = LOAD_DATA_NONE;
    static int s_loadScene = 0;

    //脚本数量,读取自资源
    static int s_scriptNum = 0;

    /**
     * 执行到第几条命令,runtime
     */
    static int[] s_scriptCmdPoint = null;

    /**
     * 状态,runtime
     */
    static int[] s_scriptState = null;
    /**
     * 重复,读取自资源
     */
    static byte[] s_scriptRepeat = null;
    /**
     * 触发条件,读取自资源
     */
    static int[] s_scriptCondition = null;
    /**
     * 暂存数据0,runtime
     */
    static int[] s_scriptTag0 = null;
    /**
     * 暂存数据1,runtime
     */
    static int[] s_scriptTag1 = null;
    /**
     * 暂存数据2,runtime
     */
    static int[] s_scriptTag2 = null;
    /**
     *  脚本集合在命令数据中的偏移,读取自资源
     */
    static int[] s_scriptCmdSetDataOffset = null;
    /**
     *  数据,读取自资源
     */
    static short[] s_scriptCmdData = null;
    /**
     *  命令在参数中的偏移,读取自资源
     */
    static short[] s_scriptCmdDataOffset = null;
    /**
     *  脚本命令类型数据,读取自资源
     */
    static short[] s_scriptCmdType = null;
    /**
     *  脚本附着的ACTORID,读取自资源
     */
    static short[] s_scriptActorID = null;
    /**
     * 如果有初始化脚本,存放初始化脚本的序号;否则等于-1
     */
    static int s_scriptInit = -1;

    static int s_battleScriptCmdCur = 0;
    static int s_battleScriptNum = 0;
    static byte[] s_battleScriptType = new byte[BATTLE_SCRIPT_SIZE];
    static short[] s_battleScriptRound = new short[BATTLE_SCRIPT_SIZE];
    static int[] s_battleScriptData = new int[BATTLE_SCRIPT_SIZE * 7];
    static int s_battleScriptDamageValue = -1;

    /**游戏的计时器，按帧计算*/
    static long s_frameTick = 0;

    /**地图图块文件名*/
    static int s_layerCount = 0;
    static byte[] s_tileFilesNum;
    /**地图中横向图块数量*/
    static int s_mapWidthByTile;
    /**地图中纵向图块数量*/
    static int s_mapHeightByTile;
    static boolean s_redrawMapBuffer;

    /**两层背景层的缓冲区*/
    static Image s_imgMapBuffer;
    /**背景层的专用画笔*/


//@#if NOKIA_LIB
//@    static DirectGraphics s_gfxMapBuffer;
//@    static Graphics s_gfxMapBufferSrc;
//@#else
    static Graphics s_gfxMapBuffer;
//@#endif

    /**地图图块文件*/
    static Image[] s_imgTile;
    static int[] s_tilePerLine;
    /**地图文件中图块顺序（地图文件）*/
    static short[][] s_mapTileLayer;
    static byte[][] s_mapTileFlip;

    static int s_phyLayerID = -1;

    /**地图图块换算前真实的图块编号的标签（地图索引文件,只包括背景层的）*/
    /**地图缓冲区的宽*/
    static int s_mapBufferWidth = 0;
    /**地图缓冲区的高*/
    static int s_mapBufferHeight = 0;
    /**地图缓冲区宽度能容纳最大图块数量*/
    static int s_mapTileMaxNumX = 0;
    /**地图缓冲区高度能容纳最大图块数量*/
    static int s_mapTileMaxNumY = 0;
    static int s_sceneMusic = 0;
    static int s_battleMusic = 0;
    static boolean s_playSound = false;
    static int s_viewHeight = 0;
    static int s_viewWidth = 0;
    static int s_viewHeightHalf = 0;
    static int s_viewWidthHalf = 0;
    static int s_viewTop = 0;

    static Random s_random = new Random();
    static DataInputStream s_dataInputStream;
    static boolean s_pauseRun = false;
    static boolean s_running = true;
    static int s_loadProgress = 0;
    static long s_loadTotalTime = 0;
//@#if Channel == "GF"
    static byte s_gameState = GS_PREVIEW;
//@#else
//@    static byte s_gameState = GS_INIT;
//@#endif
    static byte s_oldGameState = -1;
    static byte s_nextGameState = -1;
    static byte s_curGameEP = EP_NORMAL;
    static byte s_oldGameEP = EP_NORMAL;
    static byte s_EPNext = EP_NORMAL;
    /**场景ID*/
    static int s_sceneID = -1;
    /**地图ID*/
    static int s_mapID = -1;
    /**游戏变量*/
    static int[] s_variables = new int[AMOUNT_SCRIPT_VARIABLES];

    static int s_actorCount = -1;
    static CActor[] s_actors = null;
//@#if IMG_RES
    static Image[] s_imgs = null;
//@#else
//@    static CPBG[] s_pbgs = null;
//@#endif
    static boolean[] s_resInUsed = new boolean[ANIM_NUMBER];
//@#if IMG_UI
    static Image s_imgUI = null;
    static Image s_imgNumber = null;
    static Image s_imgBattlePortrait = null;
//@#else
//@    static CPBG s_pbgUI = null;
//@    static CPBG s_pbgNumber = null;
//@    static CPBG s_pbgBattlePortrait = null;
//@#endif
    static CActor s_actorUI = null;
    static CActor s_actorNumber = null;
    static CActor s_actorBattlePortrait = null;
    static int s_UIAction = -1;
//@#if SMS
//@    static Image s_imgSMS = null;
//@    static CActor s_actorSMS = null;
//@    static int s_SMSAction = -1;
//@#endif

//@#if LOADING_ANIM
    static CActor s_actorLoad[];
    static Image s_imgLoad = null;
//@#endif

//@#if SHOW_LOGO
//@    static Image[] s_imgLogo;
//@#endif
    static int s_drawlistGroundCount = 0;
    static int s_drawlistFlyCount = 0;
    static CActor[] s_actorsDrawList = new CActor[DRAWLIST_ALL_LEN];

    static CActor[] s_actorCommon = null;
    static CActor[] s_followToTeam = null;

    static Image s_imgBattleUIArrow_ANIM = null;
    static Image s_imgBattleBG = null;
    static CActor s_actorBattleBG = null;
    static CActor s_actorBattleUIArrow = null;

//@#if MORE_GAME_LINK
    static Image s_imgMoreGame = null;
    static String s_url = "";
//@#endif

//@#if HIDE_NOTIFY
//@        static long s_lastTime;
//@#endif

    /**
     * 战斗中用于显示UI动画的ACTOR数组。这些ACTOR被战斗脚本所控制，用于实现教学等特殊效果。
     * 该数组只在被战斗脚本影响的战斗中起作用。
     */
    static CActor[] s_actorBattleUI = new CActor[BATTLE_UI_ACTOR_COUNT];
    /**
     * 当前战斗中用于显示UI动画的ACTOR个数
     */
    static int s_actorBattleUICount = 0;

    static CActor[] s_actorPortraits = null;

    static String s_strErr = "";
    static boolean s_isEverythingOK = false;
    static boolean s_isRenderEffect;

    static int s_screenX = 0;
    static int s_screenY = 0;
    static int s_screenW = SCREEN_WIDTH;
    static int s_screenH = SCREEN_HEIGHT;
//@@
//@@..\CGame\CGame_Variables_Project.h
    static int[][] s_dbGameInfo;
    static int[][] s_dbEnemies;
    static int[][][] s_heroes;
    static int[][] s_dbItems;
    static int[][] s_dbSkills;
    static int[][] s_dbEnemyTeams;
    static int[][] s_dbEquips;
    static int[][] s_dbTasks;
    static int[][] s_dbEffectSet;
    static int[][] s_dbFrameInfo;
    static int[][] s_dbSMS;
    static int[][] s_dbEffort;
    static int[][] s_dbMixtrue;
    static int[][] s_dbShop;
    static int[][] s_dbEvent;

    static int[] s_gameData = new int[DEFINE_AMOUNT_GAME_DATA];

    static void SetGameData(int type, int value)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Variables_Project.h
        s_gameData[type] = value;
    }

    static boolean PlusGameData(int type, int value, int max)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Variables_Project.h
        s_gameData[type] += value;
        if (s_gameData[type] > max)
        {
            s_gameData[type] = max;
            return true;
        }
        return false;
    }

//@#if PHONE_TRANS_90
//@    static int Screen_Width = 0;
//@    static int Screen_Height = 0;
//@#endif

//@@
    static Display s_display;

    public CGame()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CGame.jpp
        s_display = Display.getDisplay(IMARPG22.s_midlet);
//@#if SMS
//@#if Channel == "QQ"
//@        s_qqSms = new QQSMS(IMARPG22.s_midlet);
//@#endif
//@#if QQ_FORUM
//@        s_qqForum = new MBoxClient(IMARPG22.s_midlet, s_display, this, this, SCREEN_WIDTH, SCREEN_HEIGHT, -KEYVALUE_LEFTSOFT, -KEYVALUE_RIGHTSOFT,
//@                                   SMS_GAME_ID, SMS_CP_ID, "");
//@#endif
//@#endif
        setFullScreenMode(true);
        s_keyState = new byte[KEYSLOT_AMOUNT];
        s_keyStateRealTime = new byte[KEYSLOT_AMOUNT];
    }

//@#if NOKIA_LIB
//@#include "..\CGame\CGame_NokiaLib.h"
//@#endif
//@#if Channel == "GF"
//@@..\CGame\CGame_Preview.h
    final static byte PREV_IMAGE_PREVIEW = 0;
    final static byte PREV_IMAGE_LINE = 1;
    final static byte PREV_IMAGE_YES = 2;
    final static byte PREV_IMAGE_NO = 3;
    final static byte AMOUNT_PREV_IMAGE = 4;

    final static byte MACH_IMAGE_LOGO_MOBILE = 0;
    final static byte MACH_IMAGE_LOGO_GAME = 1;
    final static byte MACH_IMAGE_LOGO_CP = 2;
    final static byte MACH_IMAGE_LOGO_SP = 3;
    final static byte MACH_IMAGE_SPIN_0 = 4;
    final static byte MACH_IMAGE_SPIN_1 = 5;
    final static byte MACH_IMAGE_SPIN_G = 6;
    final static byte AMOUNT_MACH_IMAGE = 7;

    static Image[] s_imgPreview = new Image[AMOUNT_PREV_IMAGE];
    static Image[] s_imgMachine = new Image[AMOUNT_MACH_IMAGE];
    static Image s_imgLogoG;
    static Image s_imgMachBG;
    static byte[] s_soundG;

    static void UpdatePreview()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Preview.h
//@#if PHONE_TOUCH
//@            if (IsTouchInPoint(0, SCREEN_HEIGHT - POINTER_RANGE_H, POINTER_RANGE_W, POINTER_RANGE_H))
//@            {
//@                KeyHandlePressed(KEYVALUE_LEFTSOFT);
//@            }
//@            else if (IsTouchInPoint(SCREEN_WIDTH - POINTER_RANGE_W, SCREEN_HEIGHT - POINTER_RANGE_H, POINTER_RANGE_W, POINTER_RANGE_H))
//@            {
//@                KeyHandlePressed(KEYVALUE_RIGHTSOFT);
//@            }
//@#endif
        if (s_frameTick == 0)
        {
            try
            {
                for (int i = 0; i < AMOUNT_PREV_IMAGE; i++)
                {
                    s_imgPreview[i] = Image.createImage("/p" + i);
                }
                s_imgLogoG = Image.createImage("/logo_G");
//@#if EXIST_SOUND
                DataInputStream dis = new DataInputStream("".getClass().getResourceAsStream("/sound.mid"));
                s_soundG = new byte[dis.available()];
                dis.read(s_soundG);
                dis.close();
//@#endif
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        if (s_frameTick > FRAME_LIMIT)
        {
//@#if EXIST_SOUND
            if (IsKeySinglePressed(GKEY_OK))
            {
                s_playSound = true;
                s_optionFlag |= OPTION_FLAG_SOUND;
                SetGameState(GS_SLOT_MACHINE);
            }
            else if (IsKeySinglePressed(GKEY_SOFTRIGHT))
            {
                s_playSound = false;
                s_optionFlag &= OPTION_FLAG_SOUND;
                SetGameState(GS_SLOT_MACHINE);
            }
//@#else
//@            SetGameState(GS_SLOT_MACHINE);
//@#endif
        }
    }

    static void RenderPreview(Graphics g)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Preview.h
        UtilFillRect(g, COLOR_BLACK, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        if (s_imgPreview[0] != null)
        { //绘制游戏预览图
            g.drawImage(s_imgPreview[PREV_IMAGE_PREVIEW], SCREEN_WIDTH_HALF, PREVIEW_IMG_Y, ALIGN_HCENTER_TOP);
            g.drawImage(s_imgLogoG, SCREEN_WIDTH_HALF, PREVIEW_LOGO_Y, ALIGN_CENTER);
        }
//@#if EXIST_SOUND
        if (s_frameTick > 15)
        { //绘制开启声音选项
            DrawSystemString(g, "是否开启音效", SCREEN_WIDTH_HALF, SCREEN_HEIGHT - 40, ALIGN_HCENTER_BOTTOM, COLOR_WHITE, COLOR_WHITE);
            g.drawImage(s_imgPreview[PREV_IMAGE_LINE], 0, SCREEN_HEIGHT - 40, ALIGN_LEFT_TOP);
            g.drawImage(s_imgPreview[PREV_IMAGE_YES], 5, SCREEN_HEIGHT - 30, ALIGN_LEFT_TOP);
            g.drawImage(s_imgPreview[PREV_IMAGE_NO], SCREEN_WIDTH - 5, SCREEN_HEIGHT - 30, ALIGN_RIGHT_TOP);
        }
//@#endif
    }

    static void UnloadRreview()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Preview.h
        for (int i = 0; i < AMOUNT_PREV_IMAGE; i++)
        {
            s_imgPreview[i] = null;
        }
        for (int i = 0; i < AMOUNT_MACH_IMAGE; i++)
        {
            s_imgMachine[i] = null;
        }
        s_imgPreview = null;
        s_imgMachine = null;
        s_imgLogoG = null;
        s_imgMachBG = null;
        s_soundG = null;
        System.gc();
    }

    static void UpdateSlotMachine()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Preview.h
        if (s_imgMachBG == null)
        {
            try
            {
                for (int i = 0; i < AMOUNT_MACH_IMAGE; i++)
                {
                    s_imgMachine[i] = Image.createImage("/m" + i);
                }
                s_imgMachBG = Image.createImage("/BG");
                if (s_playSound)
                {
                    ByteArrayInputStream bis = new ByteArrayInputStream(s_soundG);
                    s_sndPlayer = javax.microedition.media.Manager.createPlayer(bis, "audio/midi");
                    s_sndPlayer.start();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        s_loadProgress++;
        if (s_loadProgress > FRAME_LIMIT * 4)
        {
            GFInit();
//@#if GF_PACKAGE in ("CONNECT","SMS")
            CONNECT_ID = CONNECT_ENTRANCE;
                SetGameState(GS_CONNECT);
//@#else
//@            SetGameState(GS_INIT);
//@#endif
        }
    }

    static void RenderSlotMachine(Graphics g)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Preview.h
        UtilFillRect(g, COLOR_BLACK, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        if (s_imgMachBG != null)
        {
            g.drawImage(s_imgMachBG, MACHINE_IMG_X, MACHINE_IMG_Y, ALIGN_LEFT_TOP);
            if (s_loadProgress < FRAME_LIMIT)
            {
                g.drawImage(s_imgLogoG, MACHINE_SLOT_X0, MACHINE_SLOT_Y, ALIGN_CENTER);
                g.drawImage(s_imgMachine[MACH_IMAGE_LOGO_MOBILE], MACHINE_SLOT_X1, MACHINE_SLOT_Y, ALIGN_CENTER);
                g.drawImage(s_imgMachine[MACH_IMAGE_LOGO_GAME], MACHINE_SLOT_X2, MACHINE_SLOT_Y, ALIGN_CENTER);
            }
            else if (s_loadProgress < FRAME_LIMIT * 2)
            {
                if (s_loadProgress == FRAME_LIMIT)
                {
                    g.drawImage(s_imgMachine[MACH_IMAGE_SPIN_G], MACHINE_SLOT_X0, MACHINE_SLOT_Y, ALIGN_CENTER);
                }
                else
                {
                    g.drawImage(s_imgMachine[MACH_IMAGE_SPIN_0 + s_loadProgress % 2], MACHINE_SPIN_X0, MACHINE_SPIN_Y, ALIGN_LEFT_TOP);
                }
                g.drawImage(s_imgMachine[MACH_IMAGE_SPIN_0 + s_loadProgress % 2], MACHINE_SPIN_X1, MACHINE_SPIN_Y, ALIGN_LEFT_TOP);
                g.drawImage(s_imgMachine[MACH_IMAGE_SPIN_0 + s_loadProgress % 2], MACHINE_SPIN_X2, MACHINE_SPIN_Y, ALIGN_LEFT_TOP);
            }
            else
            {
                if (s_loadProgress == FRAME_LIMIT * 2)
                {
                    g.drawImage(s_imgMachine[MACH_IMAGE_SPIN_G], MACHINE_SLOT_X1, MACHINE_SLOT_Y, ALIGN_CENTER);
                }
                else
                {
                    g.drawImage(s_imgLogoG, MACHINE_SLOT_X1, MACHINE_SLOT_Y, ALIGN_CENTER);
                }
                g.drawImage(s_imgMachine[MACH_IMAGE_LOGO_CP], MACHINE_SLOT_X0, MACHINE_SLOT_Y, ALIGN_CENTER);
                g.drawImage(s_imgMachine[MACH_IMAGE_LOGO_SP], MACHINE_SLOT_X2, MACHINE_SLOT_Y, ALIGN_CENTER);
            }
        }
    }
//@@
//@@..\CGame\CGame_GF_Upload.h
    static byte s_scoreState = 0;
    final static byte SCORE_READY = 0;
    final static byte SCORE_WAIT = 1;
    final static byte SCORE_SUCC = 2;
    final static byte SCORE_FAIL = 3;
static void UpdateScore()
{//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_GF_Upload.h
//@#if PHONE_TOUCH
//@            if (IsTouchInPoint(0, SCREEN_HEIGHT - POINTER_RANGE_H, POINTER_RANGE_W, POINTER_RANGE_H))
//@            {
//@                KeyHandlePressed(KEYVALUE_LEFTSOFT);
//@            }
//@            else if (IsTouchInPoint(SCREEN_WIDTH - POINTER_RANGE_W, SCREEN_HEIGHT - POINTER_RANGE_H, POINTER_RANGE_W, POINTER_RANGE_H))
//@            {
//@                KeyHandlePressed(KEYVALUE_RIGHTSOFT);
//@            }
//@#endif
switch(s_scoreState)
    {
    case SCORE_READY:
        if (IsKeySinglePressed(GKEY_OK))
        {
            s_scoreState = SCORE_WAIT;
        }
        else if (IsKeySinglePressed(GKEY_SOFTRIGHT))
        {
            SetGameState(GS_LOADMENU);
        }
        break;
    case SCORE_WAIT:
        if (!GFUploadScore.isActived)
            GFUploadScore.upload();
        break;
    case SCORE_SUCC:
        if (IsKeySinglePressed(GKEY_SOFTRIGHT))
        {
            SetGameState(GS_LOADMENU);
            s_scoreState = SCORE_READY;
//@#if RELEASED_KEY
//@         SetAllKeyReleased();
//@#endif

        }
        break;
    case SCORE_FAIL:
        if (IsKeySinglePressed(GKEY_SOFTRIGHT))
        {
            SetGameState(GS_LOADMENU);
            s_scoreState = SCORE_READY;
//@#if RELEASED_KEY
//@         SetAllKeyReleased();
//@#endif

        }
        break;
    }
}
static void RenderScore()
{//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_GF_Upload.h
    switch (s_scoreState)
    {
    case SCORE_READY:
        UtilFillRect(s_g, 0x000000, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        DrawSystemString(s_g, "您当前的积分：", SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF - FONT_HEIGHT, ALIGN_HCENTER_BOTTOM, COLOR_WHITE, COLOR_WHITE);
        DrawSystemString(s_g, GFUploadScore.s_score + "分", SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF, ALIGN_HCENTER_BOTTOM, COLOR_WHITE, COLOR_WHITE);
        DrawSystemString(s_g, "是否提交成绩？", SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF + FONT_HEIGHT, ALIGN_HCENTER_BOTTOM, COLOR_WHITE, COLOR_WHITE);
        DrawSystemString(s_g, "是", SOFT_SELECT_X, SOFT_Y, SOFT_SELECT_ALIGN, COLOR_WHITE, COLOR_WHITE);
        DrawSystemString(s_g, "否", SOFT_CANCEL_X, SOFT_Y, SOFT_CANCEL_ALIGN, COLOR_WHITE, COLOR_WHITE);
        break;
    case SCORE_WAIT:
	UtilFillRect(s_g, 0x000000, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        DrawSystemString(s_g, "提交中……", SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF, ALIGN_HCENTER_BOTTOM, COLOR_WHITE, COLOR_WHITE);
        break;
    case SCORE_SUCC:
	UtilFillRect(s_g, 0x000000, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
//@#if GF_PACKAGE == "CONNECT"
        DrawLineString(s_g, GFUploadScore.result[1], SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF, ALIGN_HCENTER_BOTTOM, COLOR_WHITE, COLOR_WHITE, SMS_TXT_CHAR_LINE, 0, 0);
//@#else
//@        DrawSystemString(s_g, "提交成功！", SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF, ALIGN_HCENTER_BOTTOM, COLOR_WHITE, COLOR_WHITE);
//@#endif
	DrawSystemString(s_g, "返回", SOFT_CANCEL_X, SOFT_Y, SOFT_CANCEL_ALIGN, COLOR_WHITE, COLOR_WHITE);
        break;
    case SCORE_FAIL:
	UtilFillRect(s_g, 0x000000, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
//@#if GF_PACKAGE == "CONNECT"
        DrawLineString(s_g, GFUploadScore.result[1], SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF, ALIGN_HCENTER_BOTTOM, COLOR_WHITE, COLOR_WHITE, SMS_TXT_CHAR_LINE, 0, 0);
//@#else
//@        DrawSystemString(s_g, "提交失败！", SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF, ALIGN_HCENTER_BOTTOM, COLOR_WHITE, COLOR_WHITE);
//@#endif
        DrawSystemString(s_g, "返回", SOFT_CANCEL_X, SOFT_Y, SOFT_CANCEL_ALIGN, COLOR_WHITE, COLOR_WHITE);
        break;
    }
}
static class GFUploadScore implements Runnable
{//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_GF_Upload.h
    static boolean isActived = false;
    static int s_score;
//@#if GF_PACKAGE == "CONNECT"
    static String[] result;
//@#else
//@    static boolean result = false;
//@#endif
    static void upload()
    {
        isActived = true;
        new Thread(new GFUploadScore()).start();
    }
    public void run()
    {
//@#if GF_PACKAGE == "CONNECT"
        if (!GameCenter.isLogin())
        {
            GameCenter.login();
        }
        result = GameCenter.uploadScore(s_score);
        if("0".equals(result[0]))
        {
            s_scoreState = SCORE_SUCC;
        }
        else
        {
            s_scoreState = SCORE_FAIL;
        }
//@#else
//@        result = GameCenter.uploadScore_sms(s_score);
//@        if (result)
//@        {
//@            s_scoreState = SCORE_SUCC;
//@        }
//@        else
//@        {
//@            s_scoreState = SCORE_FAIL;
//@        }
//@#endif
        isActived = false;
    }
}
//@@
//@#if GF_PACKAGE in ("CONNECT","SMS")
//@@..\CGame\CGame_GF_Connect.h
    static byte CONNECT_ID = 0;
    final static byte CONNECT_ENTRANCE = 0;
    final static byte CONNECT_GAMEZONE = 1;
    final static byte CONNECT_RECOMMEND = 2;
static void UpdateConnect()
{//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_GF_Connect.h
//@#if PHONE_TOUCH
//@            if (IsTouchInPoint(0, SCREEN_HEIGHT - POINTER_RANGE_H, POINTER_RANGE_W, POINTER_RANGE_H))
//@            {
//@                KeyHandlePressed(KEYVALUE_LEFTSOFT);
//@            }
//@            else if (IsTouchInPoint(SCREEN_WIDTH - POINTER_RANGE_W, SCREEN_HEIGHT - POINTER_RANGE_H, POINTER_RANGE_W, POINTER_RANGE_H))
//@            {
//@                KeyHandlePressed(KEYVALUE_RIGHTSOFT);
//@            }
//@#endif

    if (IsKeySinglePressed(GKEY_OK))
    {
        if (!GFConnect.isActived)
        {
            GFConnect.connect();
        }
        switch (CONNECT_ID)
        {
        case CONNECT_ENTRANCE:
            SetGameState(GS_INIT);
            break;
//@#switch (GF_PACKAGE)
//@#{
//@#case "CONNECT":
        case CONNECT_GAMEZONE:
            GameCenter.call(IMARPG22.s_midlet, new GFChallenge());
            SetGameState(GS_MAINMENU);
            break;
//@#case "SMS":
        case CONNECT_RECOMMEND:
            GameCenter.call_MoreGame(IMARPG22.s_midlet);
            SetGameState(GS_MAINMENU);
            break;
//@#}
        }
    }
    else if (IsKeySinglePressed(GKEY_SOFTRIGHT))
    {
        switch (CONNECT_ID)
        {
        case CONNECT_ENTRANCE:
            SetGameState(GS_INIT);
            break;
        case CONNECT_GAMEZONE:
            SetGameState(GS_MAINMENU);
            break;
        case CONNECT_RECOMMEND:
            SetGameState(GS_MAINMENU);
            break;
        }
    }
}
static void RenderConnect()
{//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_GF_Connect.h
    UtilFillRect(s_g, 0x000000, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    DrawLineString(s_g, "需要联网才能使用互动功能，是否立刻联网？", SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF - FONT_HEIGHT, ALIGN_HCENTER_BOTTOM, COLOR_WHITE, COLOR_WHITE, SMS_TXT_CHAR_LINE,
                   0, 0);
    DrawSystemString(s_g, "是", SOFT_SELECT_X, SOFT_Y, SOFT_SELECT_ALIGN, COLOR_WHITE, COLOR_WHITE);
    DrawSystemString(s_g, "否", SOFT_CANCEL_X, SOFT_Y, SOFT_CANCEL_ALIGN, COLOR_WHITE, COLOR_WHITE);
}
//@#if GF_PACKAGE in ("CONNECT","SMS")
static class GFConnect implements Runnable
{
    static boolean isActived = false;
    static void connect()
    {
        isActived = true;
       new Thread(new GFConnect()).start();
    }
    public void run()
    {
        GameCenter.login();
        isActived = false;
    }
}
//@#endif
//@#if GF_PACKAGE in ("CONNECT")
static class GFChallenge implements ChallengeBack
{
    public void doChallenge(String levelId) {

    }
}
//@#endif

//@@
//@#endif
//@@..\CGame\CGame_GF_Init.h
static void GFInit()
{ //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_GF_Init.h
//@#if GF_TEST
//@    GameCenter.setConfig(10381, "000036748000", "rU646aCDstlM+aKLQhFaVgv2SCg=");
//@//    GameCenter.setTestPhoneNum("15811463454");
//@#else
//@#if TS_SMS
//@    GameCenter. setConfig(10516,"000038431000","fmdQOJ9EmPw/uj+9zNTdCU6/l4o=");
//@#else
    GameCenter. setConfig(10382,"000036748000","oIobHzifoyv9MXo8xeZd/GOps2k=");
//@#endif
//@#endif
//@#if GF_PACKAGE in ("CONNECT","SMS")
//@#switch (KEY_VALUE)
//@#{
//@#case "NOKIA":
//@#case "SONY_ERISSION":
//@#case "SAMSUNG":
//@#case "LG":
    GameCenter. setKeyCode(-6,-7);
//@#break
//@#case "MOTO_S":
//@            GameCenter. setKeyCode(-21,-22);
//@#break
//@#case "MOTO_B_C":
//@            GameCenter. setKeyCode(-22,-21);
//@#break
//@#case "MOTO_B":
//@            GameCenter. setKeyCode(-21,-22);
//@#break
//@#}
//@#endif
}
//@@
//@#endif
//@@..\CGame\CGame_Run.h
    static int s_optionFlag = OPTION_FLAG_ALL;

    public void paint(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Run.h
        s_g = g;
        s_g.setFont(FONT_SMALL);

//@#if NOKIA_LIB
//@        GetDG(s_g);
//@#endif
//@#if HIDE_NOTIFY
//@//                if ((s_lastTime != 0) && (System.currentTimeMillis() - s_lastTime) >= 800)
//@//                {
//@//                    s_pauseRun = true;
//@//                }
//@//                s_lastTime = System.currentTimeMillis();
//@#endif
//@#if PHONE_TRANS_90
//@        Screen_Width = getWidth();
//@        Screen_Height = getHeight();
//@        if(Screen_Width != SCREEN_WIDTH)
//@        {
//@            UtilFillRect(s_g, COLOR_BLACK, 0, 0, Screen_Width, Screen_Height);
//@            s_g.setColor(COLOR_WHITE);
//@            s_g.drawString("该应用不支持横屏显示，", SCREEN_WIDTH >> 1,
//@                           SCREEN_WIDTH_HALF - FONT_HEIGHT, ALIGN_HCENTER_TOP);
//@            s_g.drawString("请切换竖屏继续游戏。", SCREEN_WIDTH >> 1,
//@                           SCREEN_WIDTH_HALF + FONT_HEIGHT, ALIGN_HCENTER_TOP);
//@            return;
//@        }
//@#endif
        try
        {
            UpdateGS();
            RenderGS();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            s_isEverythingOK = false;
            s_strErr = ex.getMessage();
            System.out.println("s_strErr: " + s_strErr);
        }
//@#if DEBUG_SHOW_MEM
//@        RenderDebugMem();
//@#endif
        s_frameTick++;
//@#if CHEAT_ENABLE
//@        if (s_isCheatedKey)
//@        {
//@            s_uiResult = WND_STATE_YES;
//@        }
//@#endif
    }

    static void UpdateGS()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Run.h
//@#if AVOID_SOUND_INTERRUPT
        if (s_playSound && s_sndPlayer != null && s_sndPlayer.getState() == Player.REALIZED)
        {
            SndPlay(s_curSnd);
        }
//@#endif
        UpdateKeySlot();
        switch (s_gameState)
        {
        case GS_SCENE:
            UpdateGame();
            break;
        case GS_LOGO:

//@#if SHOW_LOGO
//@            UpdateLogo();
//@#else
            SetGameState(GS_LOAD_INIT);

//@#endif
            break;
        case GS_LOAD_COMMON:
            LoadGlobeData();
            break;
        case GS_SOUND:

//@#if PHONE_TOUCH
//@            if (IsTouchInPoint(0, SCREEN_HEIGHT - POINTER_RANGE_H, POINTER_RANGE_W, POINTER_RANGE_H))
//@            {
//@                KeyHandlePressed(KEYVALUE_LEFTSOFT);
//@            }
//@            else if (IsTouchInPoint(SCREEN_WIDTH - POINTER_RANGE_W, SCREEN_HEIGHT - POINTER_RANGE_H, POINTER_RANGE_W, POINTER_RANGE_H))
//@            {
//@                KeyHandlePressed(KEYVALUE_RIGHTSOFT);
//@            }
//@#endif
//@#if EXIST_SOUND
            if (IsKeySinglePressed(GKEY_SOFTLEFT))
            {
                s_optionFlag |= OPTION_FLAG_SOUND;
                SetGameState(GS_LOADMENU);
            }
            else if (IsKeySinglePressed(GKEY_SOFTRIGHT))
            {
                s_optionFlag &= ~OPTION_FLAG_SOUND;
                SetGameState(GS_LOADMENU);
            }

//@#endif
            break;
        case GS_SPLASH:
            UpdateSplash();
            break;
        case GS_LOADMENU:
            UpdateLoadMenu();
            break;
        case GS_MAINMENU:

//@#if SMS
//@            if (null != s_wnd)
//@            {
//@                s_wnd.Update();
//@            }
//@            else
//@            {
//@                if (null != s_wndMainMenu)
//@                {
//@                    s_wndMainMenu.Update();
//@                }
//@            }
//@
//@#else
            if (null != s_wndMainMenu)
            {
                s_wndMainMenu.Update();
            }
//@#endif
            break;
        case GS_LOADSCENE:
            LoadScene();
            break;
        case GS_CONTINUE:
            s_sceneID = s_loadScene;
            s_loadData = LOAD_DATA_COMMON | LOAD_DATA_OTHER;
            FreeMainMenu();
            s_wndMainMenu.RemoveAllChildren();
            SetGameState(GS_LOADSCENE);
            break;
        case GS_RETRY_LEVEL:
            break;
        case GS_THE_END:
            if (IsKeySinglePressed(GKEY_ALL))
            {
//@#if Channel == "GF"
            SetGameState(GS_UPLOADSCORE);
//@#else
//@                SetGameState(GS_LOADMENU);
//@#endif
            }
            break;
        case GS_GAME_OVER:

//@#if PHONE_TOUCH
//@            if (IsTouchInPoint(0, SCREEN_HEIGHT - POINTER_RANGE_H, POINTER_RANGE_W, POINTER_RANGE_H))
//@            {
//@                KeyHandlePressed(KEYVALUE_LEFTSOFT);
//@            }
//@#endif
            if (IsKeySinglePressed(GKEY_OK))
            {
//@#if Channel == "GF"
            SetGameState(GS_UPLOADSCORE);
//@#else
//@                SetGameState(GS_LOADMENU);
//@#endif
            }
            break;
//@#if MORE_GAME_LINK
        case GS_GAME_LINK:
            try
            {
//@#if UES_THREAD_GAME_LINK
//@                CLink.startVisit(s_url);
//@#else
                IMARPG22.s_midlet.platformRequest(s_url);
//@#endif
                Destroy();
            }
            catch (ConnectionNotFoundException ex)
            {
                Destroy();
            }
            break;
//@#endif
        case GS_INIT:

//@#if Channel == "GF"
            UnloadRreview();
//@#else
//@#if SHOW_LOGO
//@            InitLogo();
//@#endif
//@#endif
            SetGameState(GS_LOGO);
            break;
        case GS_LOAD_INIT:
            Init();
            SetGameState(GS_LOAD_COMMON);
            break;
        case GS_PAUSE:
            UpdatePause();
            break;
        case GS_QUIT:
            Destroy();
            break;
//@#if Channel == "GF"
        case GS_PREVIEW:
            UpdatePreview();
            break;
        case GS_SLOT_MACHINE:
            UpdateSlotMachine();
            break;
        case GS_UPLOADSCORE:
            UpdateScore();
            break;
//@#if GF_PACKAGE in ("CONNECT","SMS")
        case GS_CONNECT:
            UpdateConnect();
            break;
//@#endif
//@#endif
        }
//@#if RELEASED_KEY
//@        if(IsKeySinglePressed(GKEY_RELEASEDKEY))
//@        {
//@            SetAllKeyReleased();
//@        }
//@#endif
    }

    static void RenderGS()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Run.h
        switch (s_gameState)
        {
        case GS_INIT:
        case GS_LOAD_INIT:
            RenderInit(s_g);
            break;
        case GS_PAUSE:
            RenderPause();
            break;
        case GS_LOGO:

//@#if SHOW_LOGO
//@            RenderLogo(s_g);
//@#endif
            break;
        case GS_LOAD_COMMON: // load some basic data
            RenderLoadCommon(s_g);
            break;
        case GS_SOUND:
            RenderSound();
            break;
        case GS_SPLASH:
            RenderSplash(s_g);
            break;
        case GS_LOADMENU:
            RenderLoadMenu(s_g);
            break;
        case GS_MAINMENU:
            RenderMainMenu(s_g);
            break;
        case GS_CONTINUE:
            break;
        case GS_LOADSCENE:
            RenderLoadScene(s_g);
            break;
        case GS_SCENE:
            RenderGame(s_g);
            break;
        case GS_THE_END:
            UtilFillRect(s_g, COLOR_BLACK, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            break;
        case GS_GAME_OVER:
            RenderGameOver(s_g);
            break;
        case GS_RETRY_LEVEL:
            break;
//@#if Channel == "GF"
        case GS_PREVIEW:
            RenderPreview(s_g);
            break;
        case GS_SLOT_MACHINE:
            RenderSlotMachine(s_g);
            break;
        case GS_UPLOADSCORE:
            RenderScore();
            break;
//@#if GF_PACKAGE in ("CONNECT","SMS")
        case GS_CONNECT:
            RenderConnect();
            break;
//@#endif
//@#endif
        }
    }

    private static void RenderLoadCommon(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Run.h
        UtilFillRect(g, COLOR_BLACK, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        DrawSystemString(g, "正在加载资源...", SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF, ALIGN_HCENTER_BOTTOM, COLOR_WHITE, COLOR_WHITE);
    }

    private static void RenderGameOver(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Run.h
        UtilFillRect(g, COLOR_RED, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        DrawLineString(g, STR_SYSTEM_GAME_OVER, SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF, ALIGN_HCENTER_BOTTOM, COLOR_WHITE, COLOR_BLACK,
                       GAME_OVER_NUM_PERLINE, 0, 0);
        DrawString(g, STR_SYSTEM_FIRE, 5, SCREEN_HEIGHT - 5, ALIGN_LEFT_BOTTOM, COLOR_WHITE, COLOR_BLACK);
    }

    private static void RenderMainMenu(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Run.h
        if (null != s_wndMainMenu)
        {
            s_wndMainMenu.Render(g);
        }
    }

    private static void RenderSound()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Run.h
        UtilFillRect(s_g, COLOR_BLACK, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        DrawString(s_g, STR_SYSTEM_SOUND, SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF, ALIGN_HCENTER_BOTTOM, COLOR_WHITE, COLOR_WHITE);
        DrawString(s_g, STR_SYSTEM_YES, SOFT_SELECT_X, SOFT_Y, SOFT_SELECT_ALIGN, COLOR_WHITE, COLOR_WHITE);
        DrawString(s_g, STR_SYSTEM_NO, SOFT_CANCEL_X, SOFT_Y, SOFT_CANCEL_ALIGN, COLOR_WHITE, COLOR_WHITE);
    }

    static void SetGameEP(byte ep)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Run.h
        s_oldGameEP = s_curGameEP;
        s_curGameEP = ep;
        s_loadProgress = 0;
    }

    static void SetGameState(byte newState, byte nextState)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Run.h
        SetGameState(newState);
        s_nextGameState = nextState;
    }

    static void SetGameState(byte newState)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Run.h
        s_oldGameState = s_gameState;
        s_gameState = newState;

        switch (s_gameState)
        {
        case GS_LOADSCENE:
        case GS_LOADMENU:
        case GS_LOADCOMMON:
            s_randWord = UtilRandom(STR_SYSTEM_LOADING_NEWS_0, STR_SYSTEM_LOADING_NEWS_END + 1);

//@#if LOADING_ANIM
            for (int i = 0; i < LOADING_ANIM_NUMBER; i++)
            {
                s_actorLoad[i].SetAction(i);
            }

//@#endif
            break;
        case GS_PAUSE:
            SndStop();
            SetAllKeyReleased();
            s_pauseRun = false;
            break;
        case GS_QUIT:
            s_cancelPause = true;
            break;
        }
        s_loadProgress = 0;
    }

    static boolean s_igmReflashScene = false;

    static void RenderGame(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Run.h
        byte gameEP = 0;
        int progress;
        if (s_curGameEP == EP_IGM && s_igmReflashScene)
        {
            s_igmReflashScene = false;
            gameEP = s_oldGameEP;
        }
        else
        {
            gameEP = s_curGameEP;
        }
        switch (gameEP)
        {
        case EP_DIALOG:
            RenderScene(g);
            RenderEffect(g);
            RenderUI(g, s_uiType);
            break;
        case EP_NORMAL:
            RenderScene(g);
            RenderSceneInformation(g);
            RenderEffect(g);
            break;
        case EP_LOADBATTLE:

//@#if BATTLE_LOADING
//@            RenderLoading(s_g, s_loadProgress, LOADING_TIME_BATTLE);
//@            UtilDrawLineString(s_g, s_randWord, SCREEN_WIDTH_HALF, LOADING_TIP_Y, ALIGN_HCENTER_TOP, COLOR_WHITE, COLOR_BLACK, IGM_DESC_CHARS_PERLINE, 0);
//@            UtilDrawStringNum(s_g, s_loadProgress, 20, 30, ALIGN_LEFT_BOTTOM, COLOR_WHITE, COLOR_BLACK);
//@#else
            progress = Math.min(s_loadProgress, LOADING_TIME_BATTLE);
            UtilFillRect(g, COLOR_BLACK, 0, 0, progress * SCREEN_WIDTH / LOADING_TIME_BATTLE, SCREEN_HEIGHT);

//@#endif
            break;
        case EP_BATTLE:
            RenderBattle(g);
            break;
        case EP_INTERLUDE:
            RenderInterlude(g);
            RenderEffect(g);
            break;
        case EP_IGM:
            if (s_needRefreshBG)
            {
                RenderScene(g);
                s_needRefreshBG = false;
            }
            if (null != s_wnd)
            {
                s_wnd.Render(g);
//@#if RUN_SCRIPT_IN_IGM
                RenderUIScript(g);
//@#endif
            }
            break;
        case EP_FREEBATTLE:
            progress = Math.min(s_loadProgress, FREE_TIME_BATTLE);
            UtilFillRect(g, COLOR_BLACK, 0, 0, progress * SCREEN_WIDTH / FREE_TIME_BATTLE, SCREEN_HEIGHT);
            break;
        case EP_EVENT:
            RenderScene(g);
            RenderEffect(g);
            break;
        default:
            break;
        }
//@#if CHEAT_ENABLE
//@        RenderCheat();
//@#endif
//@#if DEBUG_SHOW_MEM
//@        RenderDebug();
//@#endif
    }

    static void RenderMoreGame(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Run.h
        UtilFillRect(g, COLOR_BLACK, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    static int s_showTeamNumOld = 0;

    static void UpdatePlayerCommand()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Run.h
//@#if PHONE_TOUCH
//@        if (IsTouchInPoint(0, SCREEN_HEIGHT - POINTER_RANGE_H, POINTER_RANGE_W, POINTER_RANGE_H))
//@        {
//@            KeyHandlePressed(KEYVALUE_LEFTSOFT);
//@        }
//@        else if (IsTouchInPoint(SCREEN_WIDTH - POINTER_RANGE_W, SCREEN_HEIGHT - POINTER_RANGE_H, POINTER_RANGE_W, POINTER_RANGE_H))
//@        {
//@            KeyHandlePressed(KEYVALUE_RIGHTSOFT);
//@        }
//@        else if (IsTouchInPoint(SCREEN_WIDTH - (FONT_WIDTH << 2), 5, POINTER_RANGE_W, POINTER_RANGE_H))
//@        {
//@            KeyHandlePressed(KEY_VALUE_NUM9);
//@        }
//@#endif
        if (s_UIAction >= 0)
        {
            s_actorUI.UpdateAnimation();
        }
//@#if SMS
//@        if (s_SMSAction >= 0)
//@        {
//@            s_actorSMS.UpdateAnimation();
//@        }
//@#endif
        //呼叫IGM
        if (null != s_actorHumanControl)
        {
//@#if MENU_RIGHT
//@            if (IsKeySinglePressed(GKEY_SOFTRIGHT))
//@#else
            if (IsKeySinglePressed(GKEY_SOFTLEFT))
//@#endif
            {
                OpenTopWnd(WND_IGM, IGM_TOP_X, IGM_TOP_Y, IGM_TOP_W_INIT, IGM_TOP_H);
            }
            else if (IsKeySinglePressed(GKEY_1))
            {
                if (s_entityList[ENTITY_LIST_EVENT].m_next != null)
                {
                    GetCurrentEvent();
                    SetGameEP(EP_EVENT);
                }
            }
//@#if FLY_ON
//@#if PHONE_TOUCH
//@            /**
//@             * 触摸飞行，为测试方便临时加的。
//@             */
//@            else if (IsTouchInPoint(0, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1, 100))
//@#else
//@            else if (IsKeySinglePressed(GKEY_9))
//@#endif
//@#else
//@#if PHONE_TOUCH
//@            else if (IsTouchInPoint(SCREEN_WIDTH - 80, 0, 80, 80))
//@#else
            else if (IsKeySinglePressed(GKEY_9))
//@#endif
//@#endif
            {
//@#if FLY_ON
//@                if(true)
//@#else
                if (s_variables[SCRIPT_VARIABLE_TRANSPORT] > 0) //有飞马
//@#endif
                {
                    if (s_actorHumanControl.m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISFLY] == T_TRUE)
                    {
                        SearchLandingPoint(s_actorHumanControl);
                    }
                    else
                    {
                        StartToFly(s_actorHumanControl);
                        s_showTeamNumOld = s_variables[SCRIPT_VARIABLE_TEAM_SHOW_NUM];
                        s_variables[SCRIPT_VARIABLE_TEAM_SHOW_NUM] = 1;
                    }
                }

                else //没有飞马
                {
                    s_wnd = CWnd.GetInstance();
                    s_wnd.Init(WND_MESSAGE, IGM_MESSAGE_X, IGM_MESSAGE_Y, IGM_MESSAGE_W, IGM_MESSAGE_H, false);
                    s_wnd.SetSkin(MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1, COLOR_BEIGE, T_FALSE);
                    s_wnd.m_showTimer = MESSAGE_SHOW_TIME;
                    s_wnd.m_isTop = true;
                    ResizeWndWithTitle(s_wnd, STR_SYSTEM_CANNOTSKILL, 0);
                    s_wnd.DoModule(MESSAGE_SHOW_TIME);
                    SetGameEP(EP_IGM);
                }

            }
//@#if EXIST_KEY0_SHOP
//@            else if (IsKeySinglePressed(GKEY_0))
//@            {
//@                //随身商店
//@                if (s_entityList[ENTITY_LIST_PORTABLE_SHOP].m_next != null)
//@                {
//@                    ReleaseTempEntity();
//@                    OpenTopWnd(WND_PORTABLE_SHOP, IGM_X, IGM_Y, IGM_W, IGM_H);
//@                }
//@            }
//@#endif
//@#if EXIST_EFFORT
//@            else if (IsKeySinglePressed(GKEY_3))
//@            {
//@                OpenTopWnd(WND_EFFORT, IGM_X, IGM_Y, IGM_W, IGM_H);
//@            }
//@#endif
//@#if MENU_RIGHT
//@            else if (IsKeySinglePressed(GKEY_SOFTLEFT))
//@#else
            else if (IsKeySinglePressed(GKEY_SOFTRIGHT))
//@#endif
            {
                OpenTopWnd(WND_SYSTEM, IGM_X, IGM_Y, IGM_W, IGM_H);
            }
        }
    }

    static void UpdateGame()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Run.h
//@#if CHEAT_ENABLE
//@        UpdateCheat();
//@#endif
        UpdateEffect();

        switch (s_curGameEP)
        {
        case EP_DIALOG:
            UpdateAllActors();
            UpdateUI(s_uiType);
            UpdateShock();
            if (s_uiResult != WND_STATE_WAIT)
            {
                SetGameEP(s_EPNext);
            }
            break;
        case EP_NORMAL:
            UpdateTimer();
            UpdateScript();
            UpdateAllActors();
            UpdateCamera();
            UpdateShock();
            UpdateEncounter();
            UpdatePlayerCommand();
            break;
        case EP_LOADBATTLE:
            UpdateLoadBattle();
            break;
        case EP_BATTLE:
            UpdateBattle();
            break;
        case EP_IGM:
            if (null != s_wnd)
            {
//@#if RUN_SCRIPT_IN_IGM
                if (UpdateUIScript())
//@#endif
                {
                    s_wnd.Update();
                }
                s_actorUI.UpdateAnimation();
            }
            else
            {
                SetGameEP(s_oldGameEP);
            }
            break;
        case EP_INTERLUDE:
            UpdateInterlude();
            break;
        case EP_FREEBATTLE:
            FreeBattleRes();
            break;
        case EP_EVENT:
            UpdateEvent();
            UpdateAllActors();
            break;
        default:
            break;
        }
    }

    protected void hideNotify()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Run.h
        s_pauseRun = true;
    }

    protected void showNotify()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Run.h
        s_pauseRun = false;
    }

    static void Destroy()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Run.h
        s_running = false;
        IMARPG22.s_midlet.destroyApp(true);
    }

    static long s_frameStartTime = System.currentTimeMillis();
    static long s_frameEndTime = s_frameStartTime;
    static int s_frameEstTime = 1;

    static long s_memMin = 0L;

    public void run()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Run.h
        while (s_running)
        {
            if (FRAME_LIMIT != 0)
            {
                s_frameStartTime = System.currentTimeMillis();
            }

            if (!s_pauseRun)
            {
                repaint();
//@#if USE_SERVICE_REPAINT
                serviceRepaints();
//@#endif
            }
            /**游戏中断**/
            else
            {
                if (!s_cancelPause)
                {
                    if (s_gameState != GS_PAUSE)
                    {
                        s_pauseGs = s_gameState;
                    }
                    SetGameState(GS_PAUSE);
                }
            }
            s_frameEndTime = System.currentTimeMillis();
            s_frameEstTime = (int) (s_frameEndTime - s_frameStartTime);
            if (FRAME_LIMIT != 0)
            {
                while (System.currentTimeMillis() - s_frameEndTime < TIME_IN_FRAME)
                {
                    Thread.yield();
                }
            }
        }
    }

//@@
//@@..\CGame\CGame_Pause.h
    static byte s_pauseGs = 0; //暂停前状态
    static boolean s_cancelPause = false; //取消暂停
    private static void UpdatePause()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Pause.h
//@#if PHONE_TOUCH
//@        if (IsTouchInPoint(SCREEN_WIDTH - POINTER_RANGE_W, SCREEN_HEIGHT - POINTER_RANGE_H, POINTER_RANGE_W, POINTER_RANGE_H))
//@        {
//@            KeyHandlePressed(KEYVALUE_RIGHTSOFT);
//@        }
//@#endif
        if (IsKeySinglePressed(GKEY_SOFTRIGHT))
        {
            SetGameState(s_pauseGs);
            if (s_curGameEP == EP_IGM || s_curGameEP == EP_BATTLE)
            {
                s_needRefreshBG = true;
            }
//@#if EXIST_SOUND
            if ((s_optionFlag & OPTION_FLAG_SOUND) != 0)
            {
                SndPlay(s_curSnd);
            }
            else
            {
                SndStop();
            }
//@#endif
        }
    }

    private static void RenderPause()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Pause.h
        UtilFillRect(s_g, COLOR_BLACK, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        DrawSystemString(s_g, "游戏已暂停...", (SCREEN_WIDTH - FONT_SMALL.stringWidth("游戏已暂停...")) >> 1, SCREEN_HEIGHT_HALF, 0, COLOR_WHITE, COLOR_WHITE);
        DrawSystemString(s_g, "返回", SOFT_CANCEL_X, SOFT_Y,
                         SOFT_CANCEL_ALIGN, COLOR_WHITE, COLOR_WHITE);
    }

//@@
//@@..\CGame\CGame_Init.h
    /**
     * 初始化函数
     * 完成应用程序的初始化
     */
    static void Init()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Init.h
//@#if ALPHA_EFFECT
//@        //分配ALPHA特效内存
//@        s_argbData = new int[SCREEN_WIDTH * 32];
//@#endif

        //读入动画体系，初始化菜单动画
        try
        {
            s_dataInputStream = new DataInputStream("".getClass().getResourceAsStream("/anim.bin"));

//@#if SUPERBIG_DATA
            CActor.s_moduleData = new short[ANIM_NUMBER][];
            CActor.s_animsFramesSet = new short[ANIM_NUMBER][];
//@#else
//@            CActor.s_moduleData = new byte[ANIM_NUMBER][];
//@            CActor.s_animsFramesSet = new byte[ANIM_NUMBER][];
//@#endif

            CActor.s_animsFrameSetOffset = new short[ANIM_NUMBER][];
            CActor.s_animsActionsFramesOffset = new short[ANIM_NUMBER][];
            CActor.s_animsActionsFrames = new byte[ANIM_NUMBER][];
            CActor.s_actionsDuration = new byte[ANIM_NUMBER][];
            CActor.s_animsActionsPos = new byte[ANIM_NUMBER][];
//@#if ANIM_FLAG
//@            CActor.s_animsActionFlag = new byte[ANIM_NUMBER][];
//@#endif
            while (LoadAnimationData(s_dataInputStream))
            {
            }
            s_dataInputStream.close();
            s_dataInputStream = null;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
//@#if LOADING_ANIM
        try
        {
            s_imgLoad = Image.createImage("/LOAD");
        }
        catch (IOException ex1)
        {
        }

        if (s_actorLoad == null)
        {
            s_actorLoad = new CActor[LOADING_ANIM_NUMBER];
            int j = 0;
            for (int i = 0; i <= ACTION_LOAD_LOADING_LAST; i++)
            {
                s_actorLoad[j] = new CActor(ANIM_LOAD, ANIM_LOAD, i, s_imgLoad, false);
                s_actorLoad[j].SetPos(0, 0);
                j++;
            }
        }
//@#endif
    }

    static void LoadCommonData()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Init.h
        try
        {
            switch (s_loadProgress)
            {

            }
            s_loadProgress++;
        }
        catch (Exception e)
        {
            if (DEBUG_PRINT_OUT)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取基本信息
     */
    static void LoadGlobeData()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Init.h
        try
        {
            System.out.println("s_loadProgress=" + s_loadProgress);
            switch (s_loadProgress)
            {
            case 0:

//@#if SHOW_LOGO
//@                if (s_imgLogo != null)
//@                {
//@                    for (int i = 0; i < s_imgLogo.length; i++)
//@                    {
//@                        s_imgLogo[i] = null;
//@                    }
//@                    s_imgLogo = null;
//@                }
//@#endif
                break;
            case 1:

                //初始化缓存池
                CEntity.InitPool();
                CParallel.InitPool();
                CWnd.InitPool();

                //初始化RMS
                if (s_rmsInfo == null)
                {
                    s_rmsInfo = new int[AMOUNT_MI_IGM_RMS][AMOUNT_RMS_MESSAGE];
                }

                //初始化战斗属性
                if (s_battlePara == null)
                {
                    s_battlePara = new int[AMOUNT_BATTLE_PARA];
                }

                //初始化时钟
                InitTimer(TIMER_RANGE_ALL);
                if (s_wndMainMenu == null)
                {
                    s_wndMainMenu = CWnd.GetInstance();
                }
                break;
            case 2:
                for (int i = 0; i < AMOUNT_ENTITY_LIST; i++)
                {
                    if (s_entityList[i] == null)
                    {
                        s_entityList[i] = CEntity.GetInstance();
                        s_entityList[i].m_id = -100;
                    }
                }
                if (s_tempList == null)
                {
                    s_tempList = CEntity.GetInstance();
                    s_tempList.m_id = -100;
                }

//@#if IMG_RES
                if (s_imgs == null)
                {
                    s_imgs = new Image[ANIM_NUMBER];
                }

//@#else
//@                s_pbgs = new CPBG[ANIM_NUMBER];
//@#endif
                break;
            case 3:

//@#if IMG_UI
                if (s_imgUI == null)
                {
                    s_imgUI = Image.createImage("/png" + ANIM_UI);
                    s_actorUI = new CActor(ANIM_UI, ANIM_UI, -1, s_imgUI, true);
                    s_actorTarget = new CActor(ANIM_UI, ANIM_UI, ACTION_UI_TARGET_FOE, s_imgUI, true);
                    s_actorHalo = new CActor(ANIM_UI, ANIM_UI, ACTION_UI_BATTLEUIHALO, s_imgUI, true);
                    s_actorAchievement = new CActor(ANIM_UI, ANIM_UI, ACTION_UI_ACHIEVEMENT, s_imgUI, true);
                }

//@#if SMS
//@                if (s_imgSMS == null)
//@//                {
//@//                    s_imgSMS = Image.createImage("/png" + ANIM_UI_SMS);
//@//                    s_actorSMS = new CActor(ANIM_UI_SMS, ANIM_UI_SMS, -1, s_imgSMS, true);
//@//                }
//@#endif
//@#else
//@                s_resUI = new CPBG();
//@                s_resUI.load("/pbg" + ANIM_UI);
//@                s_resUI.EnablePrecalcImage(0, PBG_PRECALC_UI);
//@                s_actorUI = new CActor(ANIM_UI, ANIM_UI, -1, s_resUI, false);
//@#endif
                    CWnd.GetSkinRes(s_actorUI);
                break;
            case 4:
                int strID = 0;

                /**
                 * 无效判断
                 */
                //                if (s_strings == null)

                {
                    InitStringSystem();
//@#if USE_PAK_RESOURCES
                    OpenTrunk("/string.pak");
                    SkipTrunk(strID);
//@#else
//@                s_trunkDis = new DataInputStream("".getClass().getResourceAsStream("/StringBin" + strID + ".bin"));
//@#endif
                    LoadString(s_trunkDis);
                    s_commonStringCount = s_stringCount;
                    CloseTrunk();
                }
                break;
            case 5:
                if (s_imgIcon == null)
                {
                    s_imgIcon = Image.createImage("/png" + ANIM_ICON);
                    s_actorIcon = new CActor(ANIM_ICON, ANIM_ICON, -1, s_imgIcon, true);
                }
                if (s_imgNumber == null)
                {
                    s_imgNumber = Image.createImage("/png" + ANIM_NUMBERS);
                    s_actorNumber = new CActor(ANIM_NUMBERS, ANIM_NUMBERS, -1, s_imgNumber, true);
                }
                break;
            case 6:

                //头像
                if (s_actorPortraits == null)
                {
                    OpenTrunk("/portrait.pak");
                    s_actorPortraits = new CActor[s_trunkSize];
                    CloseTrunk();
                }
                break;
            case 7:
                LoadDB();

//@#if SHOW_DEBUG_INFO
                System.out.println("载入DB数据库");

//@#endif
                break;
            case 8:

                //加载声音
                SndLoadAll();
                break;
            case 10:

                //读档
                RMS(false, -1);
                break;
            case 12:
                InitView();
                break;
            case 13:
                CActor actor;
                if (s_actorCommon == null)
                {
                    s_actorCommon = new CActor[TEAM_SIZE];
                    for (int i = 0; i < TEAM_SIZE; i++)
                    {
                        s_actorCommon[i] = new CActor(ANIM_FIRST_LA + i, ANIM_FIRST_LA + i, -1);
                        actor = s_actorCommon[i];
                        actor.m_actorEff = new CActor(ANIM_EMOTION, ANIM_EMOTION, -1);
                        actor.m_value = new int[AMOUNT_BV];
                        actor.m_buff = new int[AMOUNT_DB_EFFECT_SET];
                        actor.m_equips = new int[EQUIP_TYPE.length];
                        actor.m_extraValue = new int[DEFINE_AMOUNT_EXTRA_VALUE];
                        actor.m_entitySkill = CEntity.GetInstance();
                        actor.m_entitySkill.m_id = -1;
                        actor.m_entitySkill.m_count = -1;
                        actor.m_entitySkillEx = CEntity.GetInstance();
                        actor.m_entitySkillEx.m_id = -1;
                        actor.m_entitySkillEx.m_count = -1;
                        actor.SetCollideRect(s_dbGameInfo[i][DB_GAMEINFO_WIDTH], s_dbGameInfo[i][DB_GAMEINFO_HEIGHT]);
                    }
                }
                break;
            case 14:
                for (int i = 0; i < s_actorLevelUp.length; i++)
                {
                    s_actorLevelUp[i] = new CActor(ANIM_UI, ANIM_UI, ACTION_UI_LEVEL_UP, s_imgUI, false);
                }
                break;
            case 15:
                s_followToTeam = new CActor[FOLLOW_TO_TEAM_SIZE];
                break;
            case 16:

//@#if EXIST_SOUND
//@#if Channel == "GF"
                SetGameState(GS_LOADMENU);
//@#else
//@                SetGameState(GS_SOUND);
//@
//@#endif
//@#else
//@                SetGameState(GS_LOADMENU);
//@#endif
                break;
            }
            s_loadProgress++;
        }
        catch (Exception e)
        {
            if (DEBUG_PRINT_OUT)
            {
                e.printStackTrace();
            }
        }
    }

//@@
//@@..\CGame\CGame_Option.h
    static void UpdateOption(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Option.h
        int item = s_menuSlot[wnd.m_menuSlot];

        switch (item)
        {
        case MI_SYSTEM_OPTION_SOUND:

//@#if JAR_LIMIT_300K
//@            if (IsKeySinglePressed(GKEY_MOVEDOWN))
//@#else
            if (IsKeySinglePressed(GKEY_MOVERIGHT))
//@#endif
            {
                if ((s_optionFlag & (1 << 0)) == 0)
                {
                    s_optionFlag |= OPTION_FLAG_SOUND;
                    SndPlay(s_curSnd);
                }
                else
                {
                    s_optionFlag &= ~OPTION_FLAG_SOUND;
                    SndStop();
                }
            }
//@#if JAR_LIMIT_300K
//@            else if (IsKeySinglePressed(GKEY_MOVEUP))
//@#else
            else if (IsKeySinglePressed(GKEY_MOVELEFT))
//@#endif
            {
                if ((s_optionFlag & (1 << 0)) == 1)
                {
                    s_optionFlag &= ~OPTION_FLAG_SOUND;
                    SndStop();
                }
                else
                {
                    s_optionFlag |= OPTION_FLAG_SOUND;
                    SndPlay(s_curSnd);
                }
            }
            break;
        case MI_SYSTEM_OPTION_VIBRATE:
            if (IsKeySinglePressed(GKEY_MOVERIGHT))
            {
                s_optionFlag &= ~OPTION_FLAG_VIBRATE;
            }
            else if (IsKeySinglePressed(GKEY_MOVELEFT))
            {
                s_optionFlag |= OPTION_FLAG_VIBRATE;
//@#if EXIST_VIBRATE
//@                DeviceVibrate(300);
//@#endif
            }
            break;
        case MI_SYSTEM_OPTION_SUSPEN:
            if (IsKeySinglePressed(GKEY_MOVERIGHT))
            {
                s_optionFlag &= ~OPTION_FLAG_SUSPEN;
            }
            else if (IsKeySinglePressed(GKEY_MOVELEFT))
            {
                s_optionFlag |= OPTION_FLAG_SUSPEN;
            }
            break;
        }

        if (wnd.m_select >= 0 || wnd.m_state == WND_STATE_RETURN)
        {
            wnd.m_state = WND_STATE_RETURN;
            wnd.Close();
        }
    }

    static void RenderOption(Graphics g, CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Option.h
        //声音设置
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        int curY = y + SYSTEM_OPTION_ITEM_Y;
        int curItem = s_menuSlot[wnd.m_menuSlot];
        int i;
        UtilDrawMenuBox(g, COLOR_HOARY, x + SYSTEM_OPTION_RECT_X, y + SYSTEM_OPTION_RECT_Y, SYSTEM_OPTION_RECT_W, SYSTEM_OPTION_RECT_H,
                        MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1);
//@#if JAR_LIMIT_300K
//@        DrawString(g, STR_SYSTEM_GS_OPTION_NAME_0, SCREEN_WIDTH_HALF, y + OPTION_TEXT_Y, ALIGN_HCENTER_TOP, COLOR_WHITE, COLOR_BLACK);
//@        DrawString(g, STR_SYSTEM_GS_OPTION_NAME_1, SCREEN_WIDTH_HALF, SYSTEM_OPTION_TEXT_Y, ALIGN_HCENTER_TOP, COLOR_WHITE, COLOR_BLACK);
//@        int color1 = (s_optionFlag & (1 << 0)) == 0 ? (int) COLOR_RED : COLOR_BLACK;
//@        int color2 = (s_optionFlag & (1 << 0)) == 0 ? (int) COLOR_BLACK : COLOR_RED;
//@        DrawString(g, STR_SYSTEM_GS_OPTION_NAME_OFF, SCREEN_WIDTH_HALF, SYSTEM_OPTION_ON_Y, ALIGN_HCENTER_TOP, COLOR_WHITE, color1);
//@        DrawString(g, STR_SYSTEM_GS_OPTION_NAME_ON, SCREEN_WIDTH_HALF, SYSTEM_OPTION_OFF_Y, ALIGN_HCENTER_TOP, COLOR_WHITE, color2);
//@#else
        RenderIGMCommon(g, x, y, w, h, FRAME_UI_TITLE_OPTION);
        for (i = 0; i < wnd.m_count; i++)
        {
            s_actorUI.DrawFrame(g, x + SYSTEM_OPTION_NAME_X, curY, FRAME_UI_OPTION_ITEM0 + i);
            s_actorUI.DrawFrame(g, x + SYSTEM_OPTION_OPEN_X, curY, FRAME_UI_OPTION_ITEM0_OPEN + i);
            s_actorUI.DrawFrame(g, x + SYSTEM_OPTION_CLOSE_X, curY, FRAME_UI_OPTION_ITEM0_CLOSE + i);
            s_actorUI.DrawFrame(g, x + ((s_optionFlag & (1 << i)) == 0 ? SYSTEM_OPTION_CLOSE_X : SYSTEM_OPTION_OPEN_X), curY + SYSTEM_OPTION_FLAG_Y,
                                FRAME_UI_OPTION_FLAG);
            curY += SYSTEM_OPTION_ITEM_OFFY;
        }
        s_actorUI.DrawFrame(g, x + SYSTEM_OPTION_CURSOR_X, y + SYSTEM_OPTION_CURSOR_Y + curItem * SYSTEM_OPTION_ITEM_OFFY, FRAME_UI_CURSOR);
        DrawLineString(g, STR_SYSTEM_OPTION_HELP, x + (w >> 1), y + SYSTEM_OPTION_DESC_Y, ALIGN_HCENTER_TOP, COLOR_BLACK, COLOR_BLACK, OPTION_WORD_NUM_PERLINE,
                       0, 0);
//@#endif
    }

//@@
//@@..\CGame\CGame_About.h
    static void RenderAbout(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_About.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        int textW = w - (ABOUT_TEXT_X << 1);
        int textH = h - (ABOUT_TEXT_Y << 1) - (FONT_HEIGHT << 1);

        int downY = wnd.m_rect[RECT_Y] + (FONT_HEIGHT<<1);

        UtilDrawMenuBox(g, COLOR_WHITE, x + ABOUT_BOX_X, y + ABOUT_BOX_Y, w + ABOUT_BOX_W, h + ABOUT_BOX_H, MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1,
                        MODULE_UI_VERTI_1);
        DrawString(g, wnd.m_title, SCREEN_WIDTH_HALF, y + ABOUT_TEXT_Y, ALIGN_HCENTER_TOP, COLOR_WHITE, COLOR_BLACK);
        for (int i = STR_SYSTEM_GS_ABOUT_NAME_0; i < STR_SYSTEM_GS_ABOUT_NAME_0 + ABOUT_STR_LINE; i++)
        {
            if (i > STR_SYSTEM_GS_ABOUT_NAME_END)
            {
                break;
            }
            DrawString(g, i + s_menuSlot[wnd.m_menuSlot], SCREEN_WIDTH_HALF, downY, ALIGN_HCENTER_TOP, COLOR_BLACK, COLOR_BLACK);
            downY += FONT_HEIGHT;
        }

        if (s_menuSlot[wnd.m_menuSlot] > 0)
        {
            s_actorUI.DrawModule(g, MODULE_UI_ARROW, SCREEN_WIDTH_HALF - ABOUT_ARROW_UP_X, wnd.m_rect[RECT_Y] + wnd.m_rect[RECT_H] - ABOUT_ARROW_OFFECT_Y,
                                 TRANS_ROT180);
        }
        if (s_menuSlot[wnd.m_menuSlot] < ABOUT_COUNT - 1)
        {
            s_actorUI.DrawModule(g, MODULE_UI_ARROW, SCREEN_WIDTH_HALF - ABOUT_ARROW_DOWN_X, wnd.m_rect[RECT_Y] + wnd.m_rect[RECT_H] - ABOUT_ARROW_OFFECT_Y,
                                 TRANS_NONE);
        }
//        DrawTextInRectByPage(g, STR_SYSTEM_GS_ABOUT_CONTENT, x + ABOUT_TEXT_X, y + ABOUT_TEXT_Y + FONT_HEIGHT, textW, textH, ALIGN_LEFT_TOP, COLOR_BLACK,
//                             COLOR_BLACK, wnd.m_timer);
//        DrawPageStatus(g, SCREEN_WIDTH_HALF, y + h - HELP_PAGE_Y, ALIGN_HCENTER_BOTTOM, COLOR_BLACK, COLOR_BLACK, wnd.m_timer, wnd.m_textPosMax);
    }

    static void UpdateAbout(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_About.h
//        UpdateWndTextPage(wnd);
        if (ABOUT_COUNT > 0)
        {
            UpdateMenuSlot(wnd.m_menuSlot,
                           ABOUT_COUNT,
                           GKEY_MOVEDOWN,
                           GKEY_MOVEUP,
                           0,
                           false);
        }

        if (IsKeySinglePressed(GKEY_SOFTRIGHT))
        {
            wnd.m_state = WND_STATE_RETURN;
            wnd.Close();
        }
    }

//@@
//@@..\CGame\CGame_Help.h
    static void RenderHelp(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Help.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        int textW = w - (HELP_TEXT_X << 1);
        int textH = h - (HELP_TEXT_Y << 1) - (FONT_HEIGHT << 1);
        UtilDrawMenuBox(g, COLOR_WHITE, x + HELP_BOX_X, y + HELP_BOX_Y, w + HELP_BOX_W, h + HELP_BOX_H, MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1,
                        MODULE_UI_VERTI_1);
        DrawString(g, wnd.m_title, SCREEN_WIDTH_HALF, y + HELP_TEXT_Y, ALIGN_HCENTER_TOP, COLOR_WHITE, COLOR_BLACK);
        int downY = wnd.m_rect[RECT_Y] + (FONT_HEIGHT<<1);

        for (int i = STR_SYSTEM_GS_HELP_NAME_0; i < STR_SYSTEM_GS_HELP_NAME_0 + HELP_STR_LINE; i++)
        {
//@#if Channel == "GF"
//@#if GF_PACKAGE in ("CONNECT", "SMS")
            if (i > STR_SYSTEM_GS_HELP_CONNECT_END)
//@#else
//@            if (i > STR_SYSTEM_GS_HELP_NAME_END)
//@#endif
//@#else
//@            if (i > STR_SYSTEM_GS_HELP_NAME_END)
//@#endif
            {
                break;
            }
            DrawString(g, i + s_menuSlot[wnd.m_menuSlot], SCREEN_WIDTH_HALF, downY, ALIGN_HCENTER_TOP, COLOR_BLACK, COLOR_BLACK);
            downY += FONT_HEIGHT;
        }

        if (s_menuSlot[wnd.m_menuSlot] > 0)
        {
            s_actorUI.DrawModule(g, MODULE_UI_ARROW, SCREEN_WIDTH_HALF - HELP_ARROW_UP_X, wnd.m_rect[RECT_Y] + wnd.m_rect[RECT_H] - HELP_ARROW_OFFECT_Y,
                                 TRANS_ROT180);
        }

        if (s_menuSlot[wnd.m_menuSlot] < HELP_COUNT - 1)
        {
            s_actorUI.DrawModule(g, MODULE_UI_ARROW, SCREEN_WIDTH_HALF - HELP_ARROW_DOWN_X, wnd.m_rect[RECT_Y] + wnd.m_rect[RECT_H] - HELP_ARROW_OFFECT_Y,
                                 TRANS_NONE);
        }

        //翻页方法
//        DrawTextInRectByPage(g, STR_SYSTEM_GS_HELP_CONTENT, x + HELP_TEXT_X, y + HELP_TEXT_Y + FONT_HEIGHT, textW, textH, ALIGN_LEFT_TOP, COLOR_BLACK,
//                             COLOR_BLACK, wnd.m_timer);
        //显示页数
//        DrawPageStatus(g, SCREEN_WIDTH_HALF, y + h - HELP_PAGE_Y, ALIGN_HCENTER_BOTTOM, COLOR_BLACK, COLOR_BLACK, wnd.m_timer, wnd.m_textPosMax);
//        RenderScrollBar(g, FRAME_UI_SCROLL_BAR_TOP, x + 165, y + 36, 136, 0, 15);
    }

    static void UpdateHelp(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Help.h
        if (HELP_COUNT > 0)
        {
            UpdateMenuSlot(wnd.m_menuSlot,
                           HELP_COUNT,
                           GKEY_MOVEDOWN,
                           GKEY_MOVEUP,
                           0,
                           false);
        }

//        UpdateWndTextPage(wnd);
        if (IsKeySinglePressed(GKEY_SOFTRIGHT))
        {
            wnd.m_state = WND_STATE_RETURN;
            wnd.Close();
        }
    }

//@@
//@#if SHOW_LOGO
//@#include "..\CGame\CGame_Logo.h"
//@#endif

    static boolean s_needRefreshBG = false;
    static int[] s_cmdPara = new int[8];
    static byte[][] s_mapActorCollide;
    static boolean s_bScriptPause = false;

//@@..\CGame\CGame_Map.h
    /**
     * 读取地图资源
     */
    static void LoadMap()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Map.h
        s_dataInputStream = new DataInputStream("".getClass().getResourceAsStream("/map" + s_mapID + ".bin"));
        if (DEBUG_LOADINFO)
        {
            System.out.println("打开地图文件： " + "/map" + s_mapID + ".bin");
        }
        //有几层
        s_layerCount = UtilReadNumber(s_dataInputStream, NUMBER_BYTE);
        s_phyLayerID = s_layerCount - 1;
        s_tileFilesNum = new byte[s_layerCount];
        s_imgTile = new Image[s_layerCount];
        s_tilePerLine = new int[s_layerCount];
        s_mapTileLayer = new short[s_layerCount][];
        s_mapTileFlip = new byte[s_layerCount][];

        for (int i = 0; i < s_layerCount; i++)
        {
            s_tileFilesNum[i] = (byte) UtilReadNumber(s_dataInputStream, NUMBER_BYTE);
        }

        s_mapWidthByTile = UtilReadNumber(s_dataInputStream, NUMBER_SHORT);
        s_mapHeightByTile = UtilReadNumber(s_dataInputStream, NUMBER_SHORT);

        for (int i = 0; i < s_layerCount; i++)
        {
            s_mapTileLayer[i] = new short[s_mapWidthByTile * s_mapHeightByTile];
            for (int j = 0; j < s_mapWidthByTile * s_mapHeightByTile; j++)
            {
                s_mapTileLayer[i][j] = (short) UtilReadNumber(s_dataInputStream, NUMBER_SHORT);
            }
        }
        s_mapActorCollide = new byte[s_mapHeightByTile + 1][s_mapWidthByTile + 1];
        for (int i = 0; i < s_layerCount; i++)
        {
            int flipLen = ((s_mapWidthByTile * s_mapHeightByTile) >> 2) + ((s_mapWidthByTile * s_mapHeightByTile) % 4 == 0 ? 0 : 1);
            s_mapTileFlip[i] = new byte[flipLen];
            for (int j = 0; j < flipLen; j++)
            {
                s_mapTileFlip[i][j] = (byte) UtilReadNumber(s_dataInputStream, NUMBER_BYTE);
            }
        }

        // 关闭地图文件流
        try
        {
            s_dataInputStream.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        s_dataInputStream = null;
        System.gc();
    }

    /**
     * 释放地图资源
     */
    static void FreeMap()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Map.h
        s_tileFilesNum = null;
        s_tilePerLine = null;
        s_mapTileLayer = new short[s_layerCount][];
        s_mapTileFlip = new byte[s_layerCount][];

        if (s_imgTile != null)
        {
            for (int i = 0; i < s_layerCount; i++)
            {
                s_imgTile[i] = null;
            }
            s_imgTile = null;
        }

        if (null != s_mapTileLayer)
        {
            for (int i = 0; i < s_mapTileLayer.length; i++)
            {
                s_mapTileLayer[i] = null;
            }
            s_mapTileLayer = null;
        }

        if (null != s_mapTileFlip)
        {
            for (int i = 0; i < s_mapTileFlip.length; i++)
            {
                s_mapTileFlip[i] = null;
            }
            s_mapTileFlip = null;
        }
        System.gc();
    }

    static int s_prevX0; // X left position of last screen in background
    static int s_prevX1; // X right position of last screen in background
    static int s_prevY0; // X top position of last screen in background
    static int s_prevY1; // X bottom position of last screen in background
    static boolean s_isDrawTopMap = true;

    static void RenderMap(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Map.h
        int alignedX0, alignedY0, alignedX1, alignedY1, start, end; // tile
        int modX0, modX1, modY0, modY1; // pixel
        alignedX0 = s_cameraX / TILE_SIZE;
        if (s_cameraX < 0 && (s_cameraX % TILE_SIZE) != 0)
        {
            alignedX0 -= 1;
        }
        alignedX1 = alignedX0 + (VIEW_WIDTH / TILE_SIZE) + VIEW_OFFSET_X;
        alignedY0 = s_cameraY / TILE_SIZE;
        if (s_cameraY < 0 && (s_cameraY % TILE_SIZE) != 0)
        {
            alignedY0 -= 1;
        }
        alignedY1 = alignedY0 + s_viewHeight / TILE_SIZE + 1;
//@#if NOKIA_LIB
//@        GetDG(s_gfxMapBufferSrc);
//@#endif
        if (s_redrawMapBuffer)
        {
            s_redrawMapBuffer = false;
            MappedDraw(s_gfxMapBuffer, alignedX0, alignedY0, alignedX1, alignedY1);
            s_prevX0 = alignedX0;
            s_prevY0 = alignedY0;
            s_prevX1 = alignedX1;
            s_prevY1 = alignedY1;
        }
        if (s_prevX0 != alignedX0)
        {
            if (s_prevX0 < alignedX0)
            {
                start = s_prevX1 + 1;
                end = alignedX1;
            }
            else
            {
                start = alignedX0;
                end = s_prevX0 - 1;
            }
            MappedDraw(s_gfxMapBuffer, start, alignedY0, end, alignedY1);
            s_prevX0 = alignedX0;
            s_prevX1 = alignedX1;
        }
        if (s_prevY0 != alignedY0)
        {
            if (s_prevY0 < alignedY0)
            {
                start = s_prevY1 + 1;
                end = alignedY1;
            }
            else
            {
                start = alignedY0;
                end = s_prevY0 - 1;
            }
            MappedDraw(s_gfxMapBuffer, alignedX0, start, alignedX1, end);
            s_prevY0 = alignedY0;
            s_prevY1 = alignedY1;
        }
        modX0 = (s_cameraX + s_mapBufferWidth) % s_mapBufferWidth;
        modY0 = (s_cameraY + s_mapBufferHeight) % s_mapBufferHeight;
        modX1 = (s_cameraX + VIEW_WIDTH) % s_mapBufferWidth;
        modY1 = (s_cameraY + s_viewHeight) % s_mapBufferHeight;
//@#if NOKIA_LIB
//@        GetDG(g);
//@#endif
        if (modX1 > modX0)
        {
            if (modY1 > modY0)
            {
                CopyFromBackImage(g, modX0, modY0, VIEW_WIDTH, s_viewHeight, 0, s_viewTop);
            }
            else
            {
                CopyFromBackImage(g, modX0, modY0, VIEW_WIDTH, s_viewHeight - modY1, 0, s_viewTop);
                CopyFromBackImage(g, modX0, 0, VIEW_WIDTH, modY1, 0, s_viewTop + s_viewHeight - modY1);
            }
        }
        else
        {
            if (modY1 > modY0)
            {
                CopyFromBackImage(g, modX0, modY0, VIEW_WIDTH - modX1, s_viewHeight, 0, s_viewTop);
                CopyFromBackImage(g, 0, modY0, modX1, s_viewHeight, VIEW_WIDTH - modX1, s_viewTop);
            }
            else
            {
                CopyFromBackImage(g, modX0, modY0, VIEW_WIDTH - modX1, s_viewHeight - modY1, 0, s_viewTop); // Top-Left
                CopyFromBackImage(g, modX0, 0, VIEW_WIDTH - modX1, modY1, 0, s_viewTop + s_viewHeight - modY1); // Bottom-Left
                CopyFromBackImage(g, 0, modY0, modX1, s_viewHeight - modY1, VIEW_WIDTH - modX1, s_viewTop); // Top-Right
                CopyFromBackImage(g, 0, 0, modX1, modY1, VIEW_WIDTH - modX1, s_viewTop + s_viewHeight - modY1); // ottom-Right
            }
        }
    }

    static void RenderTopMap(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Map.h
        if (null == s_tilePerLine)
        {
            return;
        }

        int tilePerLine = s_tilePerLine[s_layerCount - 2];
        int id, posX, posY, flag, drawX, drawY;
        int tilex0 = s_cameraX / TILE_SIZE;
        int tiley0 = s_cameraY / TILE_SIZE;
        int tilex1 = UtilCeiling(s_cameraX + SCREEN_WIDTH, TILE_SIZE);
        int tiley1 = UtilCeiling(s_cameraY + SCREEN_HEIGHT, TILE_SIZE);
        if (s_isDrawTopMap)
        {
            for (int j = tiley0; j <= tiley1; j++)
            {
                posY = j * TILE_SIZE - s_cameraY;
                for (int i = tilex0; i <= tilex1; i++)
                {
                    if (i < 0 || i >= s_mapWidthByTile || j < 0 || j >= s_mapHeightByTile)
                    {
                        id = -1;
                    }
                    else
                    {
                        id = (j * s_mapWidthByTile + i);
                    }
                    if (id >= 0 && s_mapTileLayer[s_layerCount - 2][id] >= 0)
                    {
                        posX = i * TILE_SIZE - s_cameraX;
                        flag = (s_mapTileFlip[s_layerCount - 2][id / 4] >> ((3 - id % 4) * 2)) & 0x3;
                        drawX = (s_mapTileLayer[s_layerCount - 2][id] % tilePerLine) * TILE_SIZE;
                        drawY = (s_mapTileLayer[s_layerCount - 2][id] / tilePerLine) * TILE_SIZE;
//@#if NOKIA_LIB
//@                        s_gfxMapBufferSrc.setClip(posX, posY,TILE_SIZE,TILE_SIZE);
//@                        DrawRegion(s_imgTile[s_layerCount - 2], drawX, drawY, TILE_SIZE, TILE_SIZE, flag, posX, posY, 0);
//@#else
                        g.setClip(posX, posY, TILE_SIZE, TILE_SIZE);
                        g.drawRegion(s_imgTile[s_layerCount - 2], drawX, drawY, TILE_SIZE, TILE_SIZE, flag, posX, posY, 0);
//@#endif
                    }
                }
            }
        }
        g.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    /**
     * 生成地图的方法
     *
     * @param gb
     * @param tileX0
     * @param tileY0
     * @param tileX1
     * @param tileY1
     */
//@#if NOKIA_LIB
//@    static void MappedDraw(DirectGraphics g, int tileX0, int tileY0, int tileX1, int tileY1)
//@#else
    static void MappedDraw(Graphics g, int tileX0, int tileY0, int tileX1, int tileY1)
//@#endif

    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Map.h
        int posX, posY, drawX, drawY, i, j, id, flag = 0;
        int temp_posX;
        temp_posX = ((tileX0 * TILE_SIZE) + s_mapBufferWidth) % s_mapBufferWidth;
        posY = ((tileY0 * TILE_SIZE) + s_mapBufferHeight) % s_mapBufferHeight;
//@#if NOKIA_LIB
//@        s_dg = g;
//@#endif
        for (j = tileY0; j <= tileY1; j++)
        {
            posX = temp_posX;

            for (i = tileX0; i <= tileX1; i++)
            {
                if (i < 0 || i >= s_mapWidthByTile || j < 0 || j >= s_mapHeightByTile)
                {
                    id = -1;
                }
                else
                {
                    id = (j * s_mapWidthByTile + i);
                }

                if (id >= 0)
                {
                    for (int k = 0; k < s_layerCount - 2; k++)
                    {
                        if ((drawX = s_mapTileLayer[k][id]) > -1)
                        {
                            drawX &= 0xfff;
                            int tilePerLine = s_tilePerLine[k];
                            drawY = (drawX / tilePerLine) * TILE_SIZE;
                            drawX = (drawX % tilePerLine) * TILE_SIZE;
                            flag = 0;
                            flag = (s_mapTileFlip[k][id / 4] >> ((3 - id % 4) * 2)) & 0x3;
//@#if NOKIA_LIB
//@                            s_gfxMapBufferSrc.setClip(posX, posY,
//@                                    Math.max(Math.min(s_mapBufferWidth - (posX + 0), TILE_SIZE), 0),
//@                                    Math.max(Math.min(s_mapBufferHeight - (posY + 0), TILE_SIZE), 0));
//@                            DrawRegion(s_imgTile[k], drawX, drawY, TILE_SIZE, TILE_SIZE, flag, posX, posY, 0);
//@#else
                            g.setClip(posX, posY, TILE_SIZE, TILE_SIZE);
                            g.drawRegion(s_imgTile[k], drawX, drawY, TILE_SIZE, TILE_SIZE, flag, posX, posY, 0);
//@#endif
//@#if DEBUG_SHOW_GRID
//@                            g.setColor(0);
//@                            g.drawRect(posX, posY, TILE_SIZE, TILE_SIZE);
//@#endif
                        }
                    }
//@#if DEBUG_SHOW_PHY
//@                    /** @todo 扩展功能,目前只显示一个类型 */
//@                    if (s_mapTileLayer[s_phyLayerID][id] >= 0)
//@                    {
//@                        g.setColor(0xff0000);
//@                        g.fillRect(posX, posY, TILE_SIZE, TILE_SIZE);
//@                    }
//@#endif

                }
//@#if DEBUG_SHOW_GRID
//@                g.setColor(0);
//@                if (id >= 0 && s_mapTileLayer[s_layerCount - 1][id] > 0)
//@                {
//@                    g.drawRect(posX, posY, TILE_SIZE, TILE_SIZE);
//@                    g.drawLine(posX, posY, posX + TILE_SIZE, posY + TILE_SIZE);
//@                    g.drawLine(posX + TILE_SIZE, posY, posX, posY + TILE_SIZE);
//@                }
//@#endif
                posX += TILE_SIZE;
                if (posX >= s_mapBufferWidth)
                {
                    posX -= s_mapBufferWidth;
                }
            }
            posY += TILE_SIZE;
            if (posY >= s_mapBufferHeight)
            {
                posY -= s_mapBufferHeight;
            }
        }
    }

    /**
     * 在规定的区域内更新地图
     * @param g
     * @param modX
     * @param modY
     * @param w
     * @param h
     * @param screenX
     * @param screenY
     */
    static void CopyFromBackImage(Graphics g, int rectX, int rectY, int w, int h, int scrX, int scrY)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Map.h
//@#if NOKIA_LIB
//@        int clipW = w, clipH = h;
//@        int clipX = scrX, clipY = scrY;
//@        clipX = Math.max(scrX, s_screenX);
//@        clipY = Math.max(scrY, s_screenY);
//@        clipW = Math.min(s_screenX + s_screenW, scrX + w) - clipX;
//@        clipH = Math.min(s_screenY + s_screenH, scrY + h) - clipY;
//@        g.setClip(clipX, clipY, clipW, clipH);
//@        DrawRegion(s_imgMapBuffer,
//@                   rectX, rectY, clipW, clipH, 0, scrX, scrY, 0);
//@#else
        int tx1 = s_screenX;
        int ty1 = s_screenY;
        int tx2 = s_screenX + s_screenW;
        int ty2 = s_screenY + s_screenH;
        int rx2 = scrX + w;
        int ry2 = scrY + h;
        if (tx1 < scrX)
        {
            tx1 = scrX;
        }
        if (ty1 < scrY)
        {
            ty1 = scrY;
        }
        if (tx2 > rx2)
        {
            tx2 = rx2;
        }
        if (ty2 > ry2)
        {
            ty2 = ry2;
        }
        tx2 -= tx1;
        ty2 -= ty1;

        g.setClip(tx1, ty1, tx2, ty2);
        g.drawImage(s_imgMapBuffer, scrX - rectX, scrY - rectY, ALIGN_LEFT_TOP);
//@#endif

    }

    /**
     * 得到制定像素坐标所在tile的物理层信息
     *
     * @param posX int
     * @param posY int
     * @return int
     */
    static int GetPhyType(int posX, int posY)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Map.h
        int tileX, tileY, id;
        tileX = UtilFloor(posX, TILE_SIZE);
        tileY = UtilFloor(posY, TILE_SIZE);
        id = (tileY * s_mapWidthByTile + tileX);
        if (id < s_mapTileLayer[s_phyLayerID].length)
        {
            return s_mapTileLayer[s_phyLayerID][id];
        }
        else
        {
            return -1;
        }
    }

    static int GetPhyTypeByTile(int tileX, int tileY)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Map.h
        int id = (tileY * s_mapWidthByTile + tileX);
        if (id < s_mapTileLayer[s_phyLayerID].length)
        {
            return s_mapTileLayer[s_phyLayerID][id];
        }
        else
        {
            return -1;
        }
    }

    static int GetPhyType(int tileID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Map.h
        if (tileID >= 0 && tileID < s_mapTileLayer[s_phyLayerID].length)
        {
            return s_mapTileLayer[s_phyLayerID][tileID];
        }
        else
        {
            return -1;
        }
    }

    static int GetNextTileID(int tileX, int tileY, int offsetX, int offsetY)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Map.h
        int res = -1;
        if (tileX + offsetX < 0 || tileY + offsetY < 0 ||
            tileX + offsetX >= s_mapWidthByTile || tileY + offsetY >= s_mapHeightByTile ||
            tileX < 0 || tileX >= s_mapWidthByTile ||
            tileY < 0 || tileY >= s_mapHeightByTile)
        {
            return res;
        }
        res = (tileY + offsetY) * s_mapWidthByTile + tileX + offsetX;
        return res;
    }

    /**
     * 判断往一个方向能否滑动
     *
     * @param x int
     * @param y int
     * @param dir int
     * @return boolean
     */
    static int GetSlideDistance(int posX, int posY, int dir, boolean isClockwise)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Map.h
        int res = Integer.MAX_VALUE;
        int tileX = posX / TILE_SIZE;
        int tileY = posY / TILE_SIZE;
        int newTileX = -1, newTileY = -1, newTileID;
        int newdir = dir + (isClockwise ? 1 : 3) % 4;
        switch (dir)
        {
        case DIRECTION_UP:
            if (GetPhyType(GetNextTileID(tileX, tileY, 0, -1)) < 0)
            {
                //这个点前面没障碍
                return -1;
            }
            if (isClockwise)
            {
                if (GetPhyType(GetNextTileID(tileX, tileY, 1, 0)) >= 0)
                {
                    //转向之后有障碍 不能通行
                    return Integer.MAX_VALUE;
                }
                newTileX = tileX + 1;
                newTileY = tileY - 1;
            }
            else
            {
                if (GetPhyType(GetNextTileID(tileX, tileY, -1, 0)) >= 0)
                {
                    //转向之后有障碍 不能通行
                    return Integer.MAX_VALUE;
                }
                newTileX = tileX - 1;
                newTileY = tileY - 1;
            }
            res = Math.abs(posY - newTileY * TILE_SIZE);
            break;
        case DIRECTION_RIGHT:
            if (GetPhyType(GetNextTileID(tileX, tileY, 1, 0)) < 0)
            {
                //这个点前面没障碍
                return -1;
            }
            if (isClockwise)
            {
                if (GetPhyType(GetNextTileID(tileX, tileY, 0, 1)) >= 0)
                {
                    //转向之后有障碍 不能通行
                    return Integer.MAX_VALUE;
                }
                newTileX = tileX + 1;
                newTileY = tileY + 1;
            }
            else
            {
                if (GetPhyType(GetNextTileID(tileX, tileY, 0, -1)) >= 0)
                {
                    //转向之后有障碍 不能通行
                    return Integer.MAX_VALUE;
                }
                newTileX = tileX + 1;
                newTileY = tileY - 1;
            }
            res = Math.abs(posX - newTileX * TILE_SIZE);
            break;
        case DIRECTION_DOWN:
            if (GetPhyType(GetNextTileID(tileX, tileY, 0, 1)) < 0)
            {
                //这个点前面没障碍
                return -1;
            }
            if (isClockwise)
            {
                if (GetPhyType(GetNextTileID(tileX, tileY, -1, 0)) >= 0)
                {
                    //转向之后有障碍 不能通行
                    return Integer.MAX_VALUE;
                }
                newTileX = tileX - 1;
                newTileY = tileY + 1;
            }
            else
            {
                if (GetPhyType(GetNextTileID(tileX, tileY, 1, 0)) >= 0)
                {
                    //转向之后有障碍 不能通行
                    return Integer.MAX_VALUE;
                }
                newTileX = tileX + 1;
                newTileY = tileY + 1;
            }
            res = Math.abs(posY - newTileY * TILE_SIZE);
            break;
        case DIRECTION_LEFT:
            if (GetPhyType(GetNextTileID(tileX, tileY, -1, 0)) < 0)
            {
                //这个点前面没障碍
                return -1;
            }
            if (isClockwise)
            {
                if (GetPhyType(GetNextTileID(tileX, tileY, 0, -1)) >= 0)
                {
                    //转向之后有障碍 不能通行
                    return Integer.MAX_VALUE;
                }
                newTileX = tileX - 1;
                newTileY = tileY - 1;
            }
            else
            {
                if (GetPhyType(GetNextTileID(tileX, tileY, 0, 1)) >= 0)
                {
                    //转向之后有障碍 不能通行
                    return Integer.MAX_VALUE;
                }
                newTileX = tileX - 1;
                newTileY = tileY + 1;
            }
            res = Math.abs(posX - newTileX * TILE_SIZE);
            break;
        }

        newTileID = newTileY * s_mapWidthByTile + newTileX;
        if (GetPhyType(newTileID) >= 0)
        {
            res = Integer.MAX_VALUE;
        }
        return res;
    }


    /**
     * 获得滑动方向
     *
     * @param x int
     * @param y int
     * @param dir int
     * @return int 移动的方向
     */
    static int GetSlideDir(int x, int y, int w, int h, int dir)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Map.h

        //
        //    ---------       ---------
        //    |       |  |    |       |
        //    |   D   |  |    |   C   |
        //    |       |  V    |       |
        // ----------------------------
        //    |       |       |       |
        //    |  E    |   A   |   B   |
        //    |       |       |       |
        // ---------------------------
        //
        //有几种可能,
        //1. CD都有物理层阻挡的时候,不滑动
        //2. C有物理层,D没有物理层阻挡,向左滑动.
        //3. D有物理层,C没有物理层阻挡,向右滑动.
        //4. CD没有物理层,A不是阻碍的最边缘（E,B都有物理层阻挡），不滑动
        //5. CD没有物理层,A是阻碍的最边缘，向边缘滑动。
        //5. CD没有物理层,A是两侧都是阻碍的最边缘，向较近的一侧边缘滑动。

        int dirClockwise = (dir + 1) % 4;
        int dirAntiClockwise = (dir + 3) % 4;

        int checkPointClockwiseX = -1;
        int checkPointClockwiseY = -1;
        int checkPointAntiClockwiseX = -1;
        int checkPointAntiClockwiseY = -1;

        int slideDistanceClockwise = -1;
        int slideDistanceAntiClockwise = -1;

        //计算四个关键的tileID,(BCDE)
        switch (dir)
        {
        case DIRECTION_DOWN:
            checkPointClockwiseX = x;
            checkPointClockwiseY = y + h;
            checkPointAntiClockwiseX = x + w;
            checkPointAntiClockwiseY = y + h;
            break;
        case DIRECTION_UP:
            checkPointClockwiseX = x + w;
            checkPointClockwiseY = y;
            checkPointAntiClockwiseX = x + 0;
            checkPointAntiClockwiseY = y;
            break;
        case DIRECTION_LEFT:
            checkPointClockwiseX = x;
            checkPointClockwiseY = y + 0;
            checkPointAntiClockwiseX = x;
            checkPointAntiClockwiseY = y + h;
            break;
        case DIRECTION_RIGHT:
            checkPointClockwiseX = x + w;
            checkPointClockwiseY = y + h;
            checkPointAntiClockwiseX = x + w;
            checkPointAntiClockwiseY = y;
            break;
        }
        slideDistanceClockwise = GetSlideDistance(checkPointClockwiseX, checkPointClockwiseY, dir, true);
        slideDistanceAntiClockwise = GetSlideDistance(checkPointAntiClockwiseX, checkPointAntiClockwiseY, dir, false);

        if (slideDistanceClockwise >= (TILE_SIZE + 1) && slideDistanceAntiClockwise >= (TILE_SIZE + 1))
        {
            return DIRECTION_OVERLAP;
        }
        if (slideDistanceClockwise <= slideDistanceAntiClockwise)
        {
            return dirClockwise;
        }
        else
        {
            return dirAntiClockwise;
        }
    }

    static boolean CheckMapBoundary(int x, int y)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Map.h
        int w = s_mapWidthByTile * TILE_SIZE;
        int h = s_mapHeightByTile * TILE_SIZE;

        if (x <= 0 || y <= 0 || x >= w || y >= h)
        {
            return true;
        }
        return false;
    }

//@@
//@@..\CGame\CGame_Scene.h
    /**
     * 释放场景对象和内存
     */
    static void FreeScene()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Scene.h
        if (s_actorCameraHook != null)
        {
            s_actorCameraHook.m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISCAMERA] = T_FALSE;
            s_actorCameraHook = null;
        }
        s_drawlistGroundCount = 0;
        s_drawlistFlyCount = 0;
        s_scriptActor = null;
        s_triggerActor = null;
        for (int i = 0; i < s_actorsDrawList.length; i++)
        {
            s_actorsDrawList[i] = null;
        }
        //在场景Actot清空之前清空队伍中的场景Actor
        ClearSceneActorTeam();
        if (null != s_actors)
        {
            for (int i = 0; i < s_actors.length; i++)
            {
                if (null != s_actors[i])
                {
                    s_actors[i].Free();
                    s_actors[i] = null;
                }
            }
            s_actors = null;
        }
        for (int i = 0; i < s_actorPortraits.length; i++)
        {
            if (s_actorPortraits[i] != null)
            {
                s_actorPortraits[i].Free();
                s_actorPortraits[i] = null;
            }
        }
        if (s_wnd != null)
        {
            s_wnd.Release();
            s_wnd = null;
        }
        System.gc();
    }

    static void FreeHeroesRes()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Scene.h
        for (int i = 0; i < TEAM_SIZE; i++)
        {
            s_imgs[i] = null;
            s_actorCommon[i].m_refImage = null;
        }
        System.gc();
    }

    /**
     * 初始化场景
     * 初始价计时器,镜头,为了提供默认镜头位置,这写数据要在读取存档和读取存档和脚本之前完成.
     *
     */
    static void InitScene()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Scene.h
        InitTimer(TIMER_RANGE_SYSTEM);

        //初始化镜头
        s_cameraColor = 0xff000000;
        s_cameraColorStart = 0xff000000;
        s_cameraColorEnd = 0xff000000;
        s_cameraColorCycle = -1;
        s_cameraColorTimer = 0;

        s_flashCount = 0;
        s_flashCur = 0;
        s_flashColor = 0;
        s_flashInterval = 0;
        s_flashFadeIn = 0;
        s_flashFadeOut = 0;

        for (int i = 0; i < s_actors.length; i++)
        {
            s_actors[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] = T_TRUE;
        }

        for (int i = 0; i < TEAM_SIZE; i++)
        {
            s_actorCommon[i].m_end = false;
            s_actorCommon[i].m_repeat = true;
            s_actorCommon[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHADOW] = FRAME_SHADOW_STANDARD;
            s_actorCommon[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_FOLLOWTEAM] = (short) - (i + 1);
        }
    }

    /**
     * 绘制游戏的地图和Actor
     * @param g
     */
    static void RenderScene(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Scene.h
//@ #if IMG_BUFFER
//@        Graphics t_g = s_imgBuffer.getGraphics();
//@ #else
        Graphics t_g = g;
//@ #endif
        if (s_bMapSmallThenScreen)
        {
            UtilFillRect(s_g, COLOR_BLACK, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        }
        RenderMap(t_g);
//@#if NOKIA_LIB
//@        GetDG(t_g);
//@ #endif
        UtilResetClip(t_g);
        RenderGroundActors(t_g);
        RenderTopMap(t_g);
        RenderFlyActors(t_g);
//@#if ALPHA_EFFECT
//@        if ((s_cameraColor & 0xffffff) != 0)
//@        {
//@            for (int i = 0; i < 10; i++)
//@            {
//@                t_g.drawRGB(s_argbData, 0, SCREEN_WIDTH, 0, 32 * i, SCREEN_WIDTH, 32, true);
//@            }
//@        }
//@#endif

        if (s_flashCount > 0)
        {
            if (s_flashCur >= s_flashInterval)
            {
                UtilFillRect(t_g, s_flashColor, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                s_flashCount--;
                s_flashCur = 0;
            }
            else
            {
                s_flashCur++;
            }
        }

//@ #if IMG_BUFFER
//@        s_imgBuffer.getRGB(s_bufferRGB, 0, SCREEN_WIDTH, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
//@        TranslationEffects();
//@        g.drawRGB(s_bufferRGB, 0, SCREEN_WIDTH, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, true);
//@ #endif
    }

    static void RenderSceneInformation(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Scene.h
        if (s_timerState[0] == TIMER_STATE_START || s_timerState[0] == TIMER_STATE_PAUSE)
        {
            DrawSystemString(g, "" + s_timer[0] + "/" + s_timerInitValue[0], SCREEN_WIDTH - 3, 3, ALIGN_RIGHT_TOP, COLOR_GREEN, COLOR_BLACK);
        }
//@#if PHONE_TOUCH
//@            if (s_variables[SCRIPT_VARIABLE_TRANSPORT] > 0)
//@            {
//@                DrawString(g,STR_SYSTEM_FLY , SCREEN_WIDTH - (FONT_WIDTH << 2), 5, ALIGN_LEFT_TOP, COLOR_RED, COLOR_BLACK);
//@            }
//@#endif
        if (s_actorHumanControl != null)
        {
            // mission 暂时还没有
//          RenderInformation(g, MISSION_UI_ID, 0, 0, MISSION_UI_STATE);
//@#if JAR_LIMIT_300K
//@#if MENU_RIGHT
//@            DrawString(g, STR_SYSTEM_SCENCE_SYSTEM, 0, SCREEN_HEIGHT - FONT_HEIGHT, 0, COLOR_WHITE, COLOR_BLACK);
//@            DrawString(g, STR_SYSTEM_SCENCE_MENU, SCREEN_WIDTH - (FONT_HEIGHT << 1), SCREEN_HEIGHT - FONT_HEIGHT, 0, COLOR_WHITE, COLOR_BLACK);
//@#else
//@           DrawString(g, STR_SYSTEM_SCENCE_MENU, 0, SCREEN_HEIGHT-FONT_HEIGHT, 0, COLOR_WHITE, COLOR_BLACK);
//@           DrawString(g, STR_SYSTEM_SCENCE_SYSTEM, SCREEN_WIDTH-(FONT_HEIGHT<<1), SCREEN_HEIGHT-FONT_HEIGHT, 0, COLOR_WHITE, COLOR_BLACK);
//@#endif
//@#else
//@#if MENU_RIGHT
//@            RenderInformation(g, IGM_SYSTEM_ID, 0, SCREEN_HEIGHT, IGM_UI_STATE);
//@            RenderInformation(g, IGM_UI_ID, SCREEN_WIDTH, SCREEN_HEIGHT, IGM_SYSTEM_STATE);
//@#else
            RenderInformation(g, IGM_UI_ID, 0, SCREEN_HEIGHT, IGM_UI_STATE);
            RenderInformation(g, IGM_SYSTEM_ID, SCREEN_WIDTH, SCREEN_HEIGHT, IGM_SYSTEM_STATE);
//@#endif
//@#endif
//@#if EXIST_EFFORT
//@            RenderInformation(g, IGM_ACH_ID, SCREEN_WIDTH, 0, IGM_SYSTEM_STATE);
//@#endif
            //FRAME_UI_TITLE_SMSSHOP
            if (s_UIAction >= 0)
            {
                s_actorUI.DrawAnimation(g, 0, 0);
            }
//@#if SMS
//@#switch (SHOW_SMS_ICON)
//@#{
//@#case "MODULE":
//@                    s_actorSMS.DrawModule(g, MODULE_UI_SMS_ICON, SMS_X, SMS_Y, 0);
//@#break
//@#case "ANIM":
//@            if (s_SMSAction >= 0)
//@            {
//@                s_actorSMS.DrawAnimation(g, 0, 0);
//@            }
//@#break
//@#}
//@#endif
        }
    }


    static void SetGlobeRes()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Scene.h
        CActor actor;
        s_resInUsed[ANIM_EMOTION] = true;
        for (int i = 0; i < TEAM_SIZE; i++)
        {
            actor = s_actorCommon[i];
            actor.m_animationID = actor.m_classID;
            if (actor.m_animationID >= 0)
            {
                s_resInUsed[actor.m_animationID] = true;
            }
        }
    }

    /**
     * 初始化新游戏
     */
    static void InitNewGame()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Scene.h
        int i;
        s_sceneID = NEW_GAME_MAP_SCENE;
//@#if RUN_SCRIPT_IN_IGM
        s_UIScriptNum = 0;
//@#endif
        if (s_variables != null)
        {
            //全局变量不清除
            for (i = SCRIPT_VARIABLE_SAVE_ON; i < AMOUNT_SCRIPT_VARIABLES; i++)
            {
                s_variables[i] = 0;
            }
        }
        for (i = 0; i < TEAM_SIZE; i++)
        {
            if (i == 0)
            {
                SetTeammate(i, T_TRUE);
                SetFormation(i, T_TRUE);
            }
            else
            {
                SetTeammate(i, T_FALSE);
                SetFormation(i, T_FALSE);
            }
        }
    }

    static void InitLoadGame()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Scene.h
        int classID;
        for (int i = 0; i < TEAM_SIZE; i++)
        {
            classID = s_rmsInfo[s_menuSlot[MENU_SLOT_RMS]][RMS_INFO_ALLY0 + i];
            if (classID >= 0)
            {
                s_actorCommon[i].m_classID = s_rmsInfo[s_menuSlot[MENU_SLOT_RMS]][RMS_INFO_ALLY0 + i];
            }
        }
    }

    static void UpdateEncounter()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Scene.h
        if (s_engage >= 0)
        {
            StartBattle(s_engage, 1, 0);
            s_engage = -1;
            return;
        }
    }

//@@
//@#if PHONE_TOUCH
//@#include "..\CGame\CGame_Touch.h"
//@#endif
//@@..\CGame\CGame_Key.h
    protected void keyPressed(int keyCode)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Key.h
        KeyHandlePressed(keyCode);
    }

    static void KeyHandlePressed(int keyCode)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Key.h
        int i = KeyValueToKeySlot(keyCode);
        if (s_keyStateRealTime[i] > 0)
        {
            return; // let the auto update work
        }

        if (s_keyStateRealTime[i] < 0)
        {
            s_keyStateRealTime[i] = 0;
        }

        // set key as pressed
        if (s_keyStateRealTime[i] < 126)
        {
            s_keyStateRealTime[i]++;
        }
    }

    protected void keyRepeated(int keyCode)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Key.h
        //keyPressed(keyCode);
    }

    protected void keyReleased(int keyCode)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Key.h
        KeyHandleRelaeased(keyCode);
    }

    static void KeyHandleRelaeased(int keyCode)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Key.h
        int i = KeyValueToKeySlot(keyCode);

        if (s_keyStateRealTime[i] > 0)
        {
            s_keyStateRealTime[i] *= -1;
        }
    }

    static void KeyHandleRelaeasedAll()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Key.h
        for (int i = 0; i < s_keyStateRealTime.length; i++)
        {
            s_keyStateRealTime[i] = -1;
        }
    }

    static int GKeyToKeySlot(int keyCode)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Key.h
        if (keyCode == GKEY_UP)
        {
            return KEYVALUE_UP;
        } //1
        if (keyCode == GKEY_LEFT)
        {
            return KEYVALUE_LEFT;
        } //2
        if (keyCode == GKEY_RIGHT)
        {
            return KEYVALUE_RIGHT;
        } //5
        if (keyCode == GKEY_DOWN)
        {
            return KEYVALUE_DOWN;
        } //6
        if (keyCode == GKEY_POUND)
        {
            return Canvas.KEY_POUND;
        } //35
        if (keyCode == GKEY_STAR)
        {
            return Canvas.KEY_STAR;
        } //42
        if (keyCode == GKEY_0)
        {
            return Canvas.KEY_NUM0;
        }
        if (keyCode == GKEY_1)
        {
            return Canvas.KEY_NUM1;
        }
        if (keyCode == GKEY_2)
        {
            return Canvas.KEY_NUM2;
        }
        if (keyCode == GKEY_3)
        {
            return Canvas.KEY_NUM3;
        }
        if (keyCode == GKEY_4)
        {
            return Canvas.KEY_NUM4;
        }
        if (keyCode == GKEY_5)
        {
            return Canvas.KEY_NUM5;
        }
        if (keyCode == GKEY_6)
        {
            return Canvas.KEY_NUM6;
        }
        if (keyCode == GKEY_7)
        {
            return Canvas.KEY_NUM7;
        }
        if (keyCode == GKEY_8)
        {
            return Canvas.KEY_NUM8;
        }
        if (keyCode == GKEY_9)
        {
            return Canvas.KEY_NUM9;
        }
        if (keyCode == GKEY_SOFTLEFT)
        {
            return KEYVALUE_LEFTSOFT;
        }
        if (keyCode == GKEY_SOFTRIGHT)
        {
            return KEYVALUE_RIGHTSOFT;
        }
        if (keyCode == GKEY_FIRE)
        {
            return KEYVALUE_MIDDLE;
        }
        return KEYSLOT_DUMMY;
    }

    static int KeyValueToKeySlot(int keyCode)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Key.h
        keyCode = Math.abs(keyCode);
        if (keyCode == KEYVALUE_UP)
        {
            return KEYSLOT_UP;
        } //1
        if (keyCode == KEYVALUE_LEFT)
        {
            return KEYSLOT_LEFT;
        } //2
        if (keyCode == KEYVALUE_RIGHT)
        {
            return KEYSLOT_RIGHT;
        } //5
        if (keyCode == KEYVALUE_DOWN)
        {
            return KEYSLOT_DOWN;
        } //6
        if (keyCode == KEY_VALUE_POUND)
        {
            return KEYSLOT_POUND;
        } //35
        if (keyCode == KEY_VALUE_STAR)
        {
            return KEYSLOT_STAR;
        } //42
        if (keyCode == KEY_VALUE_NUM0)
        {
            return KEYSLOT_0;
        } //48
        if (keyCode == KEY_VALUE_NUM1)
        {
            return KEYSLOT_1;
        } //49
        if (keyCode == KEY_VALUE_NUM2)
        {
            return KEYSLOT_2;
        } //50
        if (keyCode == KEY_VALUE_NUM3)
        {
            return KEYSLOT_3;
        } //51
        if (keyCode == KEY_VALUE_NUM4)
        {
            return KEYSLOT_4;
        } //52
        if (keyCode == KEY_VALUE_NUM5)
        {
            return KEYSLOT_5;
        } //53
        if (keyCode == KEY_VALUE_NUM6)
        {
            return KEYSLOT_6;
        } //54
        if (keyCode == KEY_VALUE_NUM7)
        {
            return KEYSLOT_7;
        } //55
        if (keyCode == KEY_VALUE_NUM8)
        {
            return KEYSLOT_8;
        } //56
        if (keyCode == KEY_VALUE_NUM9)
        {
            return KEYSLOT_9;
        } //57
        if (keyCode == KEYVALUE_LEFTSOFT)
        {
            return KEYSLOT_SOFTLEFT;
        }
        if (keyCode == KEYVALUE_RIGHTSOFT)
        {
            return KEYSLOT_SOFTRIGHT;
        }
        if (keyCode == KEYVALUE_MIDDLE)
        {
            return KEYSLOT_FIRE;
        }
        return KEYSLOT_DUMMY;
    }

    static int KeyValueToGKey(int keyCode)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Key.h
        keyCode = Math.abs(keyCode);
        if (keyCode == KEYVALUE_UP)
        {
            return GKEY_UP;
        } //1
        if (keyCode == KEYVALUE_LEFT)
        {
            return GKEY_LEFT;
        } //2
        if (keyCode == KEYVALUE_RIGHT)
        {
            return GKEY_RIGHT;
        } //5
        if (keyCode == KEYVALUE_DOWN)
        {
            return GKEY_DOWN;
        } //6
        if (keyCode == Canvas.KEY_POUND)
        {
            return GKEY_POUND;
        } //35
        if (keyCode == Canvas.KEY_STAR)
        {
            return GKEY_STAR;
        } //42
        if (keyCode == Canvas.KEY_NUM0)
        {
            return GKEY_0;
        } //48
        if (keyCode == Canvas.KEY_NUM1)
        {
            return GKEY_1;
        } //49
        if (keyCode == Canvas.KEY_NUM2)
        {
            return GKEY_2;
        } //50
        if (keyCode == Canvas.KEY_NUM3)
        {
            return GKEY_3;
        } //51
        if (keyCode == Canvas.KEY_NUM4)
        {
            return GKEY_4;
        } //52
        if (keyCode == Canvas.KEY_NUM5)
        {
            return GKEY_5;
        } //53
        if (keyCode == Canvas.KEY_NUM6)
        {
            return GKEY_6;
        } //54
        if (keyCode == Canvas.KEY_NUM7)
        {
            return GKEY_7;
        } //55
        if (keyCode == Canvas.KEY_NUM8)
        {
            return GKEY_8;
        } //56
        if (keyCode == Canvas.KEY_NUM9)
        {
            return GKEY_9;
        } //57
        if (keyCode == KEYVALUE_LEFTSOFT)
        {
            return GKEY_SOFTLEFT;
        }
        if (keyCode == KEYVALUE_RIGHTSOFT)
        {
            return GKEY_SOFTRIGHT;
        }
        if (keyCode == KEYVALUE_MIDDLE)
        {
            return GKEY_FIRE;
        }
        return GKEY_DUMMY;
    }

    /**
     * 更新按键存储区
     */
    static void UpdateKeySlot()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Key.h
        for (int i = 0; i < KEYSLOT_AMOUNT; i++)
        {
            s_keyState[i] = s_keyStateRealTime[i];
            if (s_keyStateRealTime[i] != 0)
            {
                if (s_keyStateRealTime[i] < 0)
                {
                    s_keyStateRealTime[i] = 0;
                }
                else if (s_keyStateRealTime[i] < 126)
                {
                    s_keyStateRealTime[i]++;
                    if (i >= GKEY_SOFTLEFT)
                    {
                        s_keyStateRealTime[i] *= -1;
                    }
                }
            }
        }
    }

    //--------------------------------------------------------------------------
    /// set all key status as released
    //--------------------------------------------------------------------------
    static void SetAllKeyReleased()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Key.h
        for (int i = 0; i < KEYSLOT_AMOUNT; i++)
        {
            s_keyState[i] = 0;
            s_keyStateRealTime[i] = 0;
        }
    }

    final static boolean IsAnyKeyPressed()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Key.h
        for (int i = KEYSLOT_UP; i < (KEYSLOT_AMOUNT); i++)
        {
            if (s_keyState[i] < 0)
            {
                return true;
            }
        }
        return false;
    }

    final static boolean IsKeyPressed(int GKeyCode)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Key.h
        for (int i = KEYSLOT_UP; i < KEYSLOT_AMOUNT; i++)
        {
            if ((GKeyCode & (1 << i)) != 0)
            {
                if (s_keyState[i] > 0)
                {
                    return true;
                }
            }
        }
        return false;
    }

    final static boolean IsKeySinglePressed(int GKeyCode)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Key.h
        for (int i = KEYSLOT_UP; i < KEYSLOT_AMOUNT; i++)
        {
            if ((GKeyCode & (1 << i)) != 0)
            {
                if (s_keyState[i] == 1)
                {
                    return true;
                }
            }
        }
        return false;
    }

    final static boolean IsKeySingleReleased(int GKeyCode)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Key.h
        for (int i = KEYSLOT_UP; i < KEYSLOT_AMOUNT; i++)
        {
            if ((GKeyCode & (1 << i)) != 0)
            {
                if (s_keyState[i] < 0)
                {
                    return true;
                }
            }
        }
        return false;
    }

//@@

    static void AddToDrawList(CActor actor)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CGame.jpp
        if (actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISFLY] == T_FALSE)
        {
            s_actorsDrawList[s_drawlistGroundCount] = actor;
            s_drawlistGroundCount++;
        }
        else
        {
            s_actorsDrawList[GROUND_DRAWLIST_MAX + s_drawlistFlyCount] = actor;
            s_drawlistFlyCount++;
        }
    }

//@@..\CGame\CGame_QuickSort.h
    static int QuickSortCalc(int z0, int y0, int z1, int y1)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_QuickSort.h
        if (z0 < z1)
        {
            return -1;
        }
        if (z0 > z1)
        {
            return 1;
        }
        if (y0 > y1)
        {
            return 1;
        }
        if (y0 <= y1)
        {
            return -1;
        }
        return 0;
    }

    static void QuickSort(int low, int high)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_QuickSort.h
        int i, mark;
        if (low >= high)
        {
            return;
        }
        QSSwap(low, (low + high) >> 1);
        mark = low;
        for (i = low + 1; i <= high; i++)
        {
            if (QuickSortCalc(
                    s_actorsDrawList[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_Z],
                    s_actorsDrawList[i].m_curY,
                    s_actorsDrawList[low].m_properties[DEFINE_ACTOR_PROPERTY_PARA_Z],
                    s_actorsDrawList[low].m_curY) == -1)
            {
                QSSwap(++mark, i);
            }
        }
        QSSwap(low, mark);
        QuickSort(low, mark - 1);
        QuickSort(mark + 1, high);
    }

    static void QSSwap(int i, int j)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_QuickSort.h
        CActor mark = s_actorsDrawList[i];
        s_actorsDrawList[i] = s_actorsDrawList[j];
        s_actorsDrawList[j] = mark;
    }


//@@
//@@..\CGame\CGame_Trunk.h

    static String s_trunkName;
    static int[] s_trunkIndex;
    static int s_trunkSize;
    static DataInputStream s_trunkDis;
    static long s_disPos = 0;

    /**
     * 用于大量小数据读取的缓存，不适用于大块数据。
     */
    static byte[] s_trunkBuffer0 = new byte[1024 * 10];
    static byte[] s_trunkBuffer1 = new byte[1024 * 10];

    final static void OpenTrunk(String filename)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Trunk.h
        int buf = 0;
        int tmpi = 0;
        int pos = 0;
        s_disPos = 0;
        try
        {
            s_trunkDis = new DataInputStream("".getClass().getResourceAsStream(filename));
            s_trunkSize = s_trunkDis.readByte();
            s_disPos++;
            pos++;
            s_trunkIndex = new int[s_trunkSize + 1];
            for (int i = 0; i < s_trunkSize; i++)
            {
                buf = 0;
                tmpi = 0;
                do
                {
                    buf = (s_trunkDis.readUnsignedByte() & 0xff);
                    pos++;
                    s_disPos++;
                    //TrunkIndex[i] = (TrunkIndex[i] << 7) + buf;
                    s_trunkIndex[i + 1] += ((buf & 0x7f) << (7 * tmpi));
                    tmpi++;
                }
                while ((buf & 0x80) != 0);
            }
            s_trunkIndex[0] = pos;
            s_trunkName = filename;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    final static byte[] ReadTrunk(int index)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Trunk.h
        if (!SkipTrunk(index))
        {
            return null;
        }
        int len = s_trunkIndex[index + 1];
        byte[] res = new byte[len];
        try
        {
            s_trunkDis.read(res);
            s_disPos += len;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return res;
    }

    final static boolean SkipTrunk(int index)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Trunk.h
        if (index >= s_trunkSize)
        {
            return false;
        }
        long offset = 0;
        for (int i = 0; i < index + 1; i++)
        {
            offset += s_trunkIndex[i];
        }
        try
        {
            //dis.reset();
            if (offset == s_disPos)
            {
            }
            else if (offset - s_disPos > 0)
            {
                s_trunkDis.skip(offset - s_disPos);
                s_disPos += (offset - s_disPos);
            }
            else
            {
                /** @todo 这里可能有严重问题 */
                CloseTrunk();
                OpenTrunk(s_trunkName);
                s_trunkDis.skip(offset);
                s_disPos = offset;
            }

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    final static void CloseTrunk()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Trunk.h
        try
        {
            s_trunkDis.close();
            s_trunkDis = null;
            s_trunkSize = 0;
            s_trunkIndex = null;
            System.gc();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

//@@

//@@..\CGame\CGame_Script.h
    //
    static void ScriptWaitActor(int scriptID, int actorID, int paraID, int target)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Script.h
        s_scriptState[scriptID] = SCRIPT_STATE_WAIT_ACTOR_PARA;
        s_scriptTag0[scriptID] = actorID;
        s_scriptTag1[scriptID] = paraID;
        s_scriptTag2[scriptID] = target;
    }

    static int ScriptFindTerminator(int scriptID, int endID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Script.h
        int res = Integer.MAX_VALUE;
        int cur = s_scriptCmdPoint[scriptID] + 1;
        int offset = s_scriptCmdSetDataOffset[scriptID] + cur;
        int len = GetScriptLen(scriptID);
        int end = s_scriptCmdSetDataOffset[scriptID] + len;
        int stack = 0;
        for (int i = offset; i < end; i++)
        {
            if (s_scriptCmdType[i] == endID)
            {
                if (stack == 0)
                {
                    res = i;
                    break;
                }
            }
            else if (s_scriptCmdType[i] == SCHEMA_IF)
            {
                stack++;
            }
            if (s_scriptCmdType[i] == SCHEMA_ENDIF)
            {
                stack--;
            }
        }
        return res;
    }

    /**
     * RunScriptCmd
     *
     * @param index int
     * @return boolean 为True继续执行下一条命令,为False停止本次循环
     */
    static boolean ScriptRunCmd(int scriptID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Script.h
        int i, j, k;
        //debug
        boolean res = false;
        CActor actor = null;
        CEntity entity0 = null;
        //step1.摘取数据
        int cur = s_scriptCmdPoint[scriptID];
        int offset = s_scriptCmdSetDataOffset[scriptID] + cur;
        int cmdType = s_scriptCmdType[offset];
        int paraLen = CMD_PARALEN[cmdType];
        int cmddataOffset = s_scriptCmdDataOffset[offset];
//@#if SHOW_DEBUG_INFO
        if (cmdType == -2147483648)
        {
            System.out.println("if 与 endif 数量不匹配");
        }
//@#endif
        for (i = 0; i < paraLen; i++)
        {
            s_cmdPara[i] = s_scriptCmdData[cmddataOffset + i];
        }
        switch (cmdType)
        {
        case SCHEMA_IF:
            i = CheckScriptCmdCondition(s_cmdPara[CMD_IF_LEFT_TYPE], s_cmdPara[CMD_IF_LEFT_VALUE0], s_cmdPara[CMD_IF_LEFT_VALUE1], s_cmdPara[CMD_IF_OPERATOR],
                                        s_cmdPara[CMD_IF_RIGHT_TYPE], s_cmdPara[CMD_IF_RIGHT_VALUE0], s_cmdPara[CMD_IF_RIGHT_VALUE1]);
            if (i == T_TRUE)
            {
                //继续执行
            }
            else
            {
                //拨动(可以理解成跳到else分支 或者endif分支)
                j = ScriptFindTerminator(scriptID, SCHEMA_ELSE);
                k = ScriptFindTerminator(scriptID, SCHEMA_ENDIF);
                if (j < k)
                {
                    s_scriptCmdPoint[scriptID] = j - s_scriptCmdSetDataOffset[scriptID];
                }
                else
                {
                    s_scriptCmdPoint[scriptID] = k - s_scriptCmdSetDataOffset[scriptID];
                }
            }
            res = true;
            break;
        case SCHEMA_ENDIF:
            res = true;
            break;
        case SCHEMA_RETURN:
            ScriptEndCmdSet(scriptID);
            res = false;
            break;
        case SCHEMA_SET:
            SetVariable(s_cmdPara[CMD_SET_PARA], s_cmdPara[CMD_SET_NUMBERCONTROL], s_cmdPara[CMD_SET_DATA_TYPE], s_cmdPara[CMD_SET_VALUE1],
                        s_cmdPara[CMD_SET_VALUE2]);
            res = true;
            break;
        case SCHEMA_LOADMAP:
            s_preLoadData[PRELOAD_MAPID] = s_cmdPara[CMD_LOADMAP_ID];
            s_preLoadData[PRELOAD_DIR] = s_cmdPara[CMD_LOADMAP_DIR];
            s_preLoadData[PRELOAD_TILEX] = s_cmdPara[CMD_LOADMAP_TILE_X];
            s_preLoadData[PRELOAD_TILEY] = s_cmdPara[CMD_LOADMAP_TILE_Y];
            ScriptEndCmdSet(scriptID);
            InitInterlude();
            SetGameEP(EP_INTERLUDE);
            s_sceneID = s_preLoadData[PRELOAD_MAPID];
            res = false;
            break;
        case SCHEMA_SETACTORPOS:

            //4个参数 actorid,x,y,dir
            SetActorPos(s_cmdPara[CMD_SETACTORPOS_ID], s_cmdPara[CMD_SETACTORPOS_TILE_X], s_cmdPara[CMD_SETACTORPOS_TILE_Y]);
            res = true;
            break;
        case SCHEMA_CONTROLACTORSHOW:
            i = s_cmdPara[CMD_CONTROLACTORSHOW_ID];
            actor = GetActor(i);
            actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] = (short) s_cmdPara[CMD_CONTROLACTORSHOW_SHOW];
            res = true;
            break;
        case SCHEMA_DIALOGUE:

//@#if DEBUG_SKIP_DLG
//@            res = true;
//@#else
            i = s_commonStringCount + s_cmdPara[CMD_DIALOGUE_MESSAGE];
            j = s_cmdPara[CMD_DIALOGUE_PROTRAIT_POS_H];
            k = s_cmdPara[CMD_DIALOGUE_PROTRAIT_POS_V];
            int head = s_cmdPara[CMD_DIALOGUE_PROTRAIT_ID];
            int brow = s_cmdPara[CMD_DIALOGUE_EXPRESSION];
            int name = s_commonStringCount + s_cmdPara[CMD_DIALOGUE_ACTOR_NAME];
            InitDialog(i, j, k, head, brow, name);
            if (s_scriptActor != null)
            {
                s_scriptActor.FaceTo(s_triggerActor.m_curX, s_triggerActor.m_curY, ACTION_UP);
            }
            SetGameEP(EP_DIALOG);
            res = false;

//@#endif
            break;
        case SCHEMA_MOVE:
            AddActorTravelLine(s_cmdPara[CMD_MOVE_ID], s_cmdPara[CMD_MOVE_DIR], s_cmdPara[CMD_MOVE_STEP]);
            ChangeActorMoveState(s_cmdPara[CMD_MOVE_ID], ACTOR_MOVE_TYPE_TRAVELLINE, true);
            res = true;
            break;
        case SCHEMA_WAITMOVEFINISH:

            /** @todo 探讨一下这个命令的存在意义,这个功能的存在形式,需要的数据 */
            ScriptWaitActor(scriptID, s_cmdPara[CMD_WAITMOVEFINISH_ID], DEFINE_ACTOR_PROPERTY_PARA_MOVETYPE, ACTOR_MOVE_TYPE_STAND);
            res = false;
            break;
        case SCHEMA_MOVESCREEN:
            SetCamera(CAMERA_STATE_MOVE, s_cmdPara[CMD_MOVESCREEN_TAR_X], s_cmdPara[CMD_MOVESCREEN_TAR_Y], null, s_cmdPara[CMD_MOVESCREEN_TIME]);
            if (s_cmdPara[CMD_MOVESCREEN_TIME] != 0)
            {
                s_scriptState[scriptID] = SCRIPT_STATE_WAIT_CAMERA;
                res = false;
            }
            else
            {
                res = true;
            }
            break;
        case SCHEMA_SETCAMERAFOCUES:

            //移动镜头
            i = s_cmdPara[CMD_SETCAMERAFOCUES_ID];
            j = s_cmdPara[CMD_SETCAMERAFOCUES_TIME];
            actor = GetActor(i);
            SetCamera(CAMERA_STATE_ACTOR, 0, 0, actor, j);
            if (j != 0)
            {
                s_scriptState[scriptID] = SCRIPT_STATE_WAIT_CAMERA;
                res = false;
            }
            else
            {
                res = true;
            }
            break;
        case SCHEMA_CHANGEACTION:
            actor = GetActor(s_cmdPara[CMD_CHANGEACTION_ACTOR]);
            i = s_cmdPara[CMD_CHANGEACTION_ACTION_TYPE];
            if (i >= 0)
            {
                j = actor.m_actionID % ACTION_DEVIATION;
                SetActorAction(actor, i * ACTION_DEVIATION + j, s_cmdPara[CMD_CHANGEACTION_REPEAT] == T_TRUE);
            }
            else
            {
                SetActorAction(actor, s_cmdPara[CMD_CHANGEACTION_ACTION], s_cmdPara[CMD_CHANGEACTION_REPEAT] == T_TRUE);
            }
            res = true;
            break;
        case SCHEMA_MOVEACTOR:
            i = s_cmdPara[CMD_MOVEACTOR_ACTORNAME];
            actor = GetActor(i);
            actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_MOVETYPE] = ACTOR_MOVE_TYPE_AUTOMOVE;
            j = UtilCeiling(s_cmdPara[CMD_MOVEACTOR_DISTANCE], s_cmdPara[CMD_MOVEACTOR_TIME]);
            switch (s_cmdPara[CMD_MOVEACTOR_DIR])
            {
            case DEFINE_DIRECTION_DOWN:
                actor.m_destX = actor.m_curX;
                actor.m_destY = actor.m_curY + s_cmdPara[CMD_MOVEACTOR_DISTANCE];
                actor.m_vX = 0;
                actor.m_vY = (short) j;
                break;
            case DEFINE_DIRECTION_UP:
                actor.m_destX = actor.m_curX;
                actor.m_destY = actor.m_curY - s_cmdPara[CMD_MOVEACTOR_DISTANCE];
                actor.m_vX = 0;
                actor.m_vY = (short) - j;
                break;
            case DEFINE_DIRECTION_LEFT:
                actor.m_destX = actor.m_curX - s_cmdPara[CMD_MOVEACTOR_DISTANCE];
                actor.m_destY = actor.m_curY;
                actor.m_vX = (short) - j;
                actor.m_vY = 0;
                break;
            case DEFINE_DIRECTION_RIGHT:
                actor.m_destX = actor.m_curX + s_cmdPara[CMD_MOVEACTOR_DISTANCE];
                actor.m_destY = actor.m_curY;
                actor.m_vX = (short) (j);
                actor.m_vY = 0;
                break;
            }
            res = false;
            break;
        case SCHEMA_WAIT:
            i = StartSystemTimer(s_cmdPara[CMD_WAIT_TIME]);
            s_scriptTag0[scriptID] = i;
            s_scriptState[scriptID] = SCRIPT_STATE_WAIT_TIMER;
            res = false;
            break;
        case SCHEMA_ELSE:
            i = ScriptFindTerminator(scriptID, SCHEMA_ENDIF);
            s_scriptCmdPoint[scriptID] = i - s_scriptCmdSetDataOffset[scriptID];
            System.out.println("unhandle cmdType: " + cmdType);
            res = false;
            break;
        case SCHEMA_CHOOSEDIALOGUE:
            s_uiTitle = s_commonStringCount + s_cmdPara[CMD_CHOOSEDIALOGUE_TITLE];
            s_uiVariable = s_cmdPara[CMD_CHOOSEDIALOGUE_PARA];
            for (i = 0; i < DIALOG_CHOOSE_MAX; i++)
            {
                s_uiChooseItem[i] = s_commonStringCount + s_cmdPara[CMD_CHOOSEDIALOGUE_CHOOSE_A + i];
            }
            s_uiChooseAmount = s_cmdPara[CMD_CHOOSEDIALOGUE_CHOOSE_COUNT];
            InitUI(UI_TYPE_CHOOSE, 0, EP_NORMAL);
            SetGameEP(EP_DIALOG);
            res = false;
            break;
        case SCHEMA_TEAMCONTROL:
            i = CalcTeammateID(s_cmdPara[CMD_TEAMCONTROL_ACTOR_ID]);
            j = s_cmdPara[CMD_TEAMCONTROL_ISINTEAM];
            k = s_cmdPara[CMD_TEAMCONTROL_ISINFORMATION];
            SetTeammate(i, j);
            SetFormation(i, j == T_TRUE ? k : T_FALSE);
            RefreshTeam();
            res = true;
            break;
        case SCHEMA_SCREENCONTROL:
            j = s_cmdPara[CMD_SCREENCONTROL_HEIGHT];
            switch (s_cmdPara[CMD_SCREENCONTROL_CONTROL])
            {
            case DEFINE_CURTAIN_OPERATE_OPEN:
                i = EFFECT_TYPE_CURTAIN_OPEN;
                break;
            case DEFINE_CURTAIN_OPERATE_CLOSE:
                i = EFFECT_TYPE_CURTAIN_CLOSE;
                break;
            case DEFINE_CURTAIN_OPERATE_MOVIE_OFF:
                i = EFFECT_TYPE_CURTAIN_MOVIE_OFF;
                break;
            case DEFINE_CURTAIN_OPERATE_MOVIE_ON:
                i = EFFECT_TYPE_CURTAIN_MOVIE_ON;
                break;
            default:
                i = -1;
                break;
            }
            k = s_cmdPara[CMD_SCREENCONTROL_WAIT];
            SetCurtainEffect(i, j);
            s_bScriptPause = (k == T_TRUE ? true : false);
            res = (k == T_TRUE ? false : true);
            break;
        case SCHEMA_PACKAGEITEM:
            i = GetArticalType(s_cmdPara[CMD_PACKAGEITEM_ITEMID] & 0xffff);
            j = GetArticalID(s_cmdPara[CMD_PACKAGEITEM_ITEMID]);
            PackageModify(j, i, s_cmdPara[CMD_PACKAGEITEM_COUNT]);
            res = false;
            break;
        case SCHEMA_SKILLCONTROL:
            i = s_cmdPara[CMD_SKILLCONTROL_ACTOR];
            j = s_cmdPara[CMD_SKILLCONTROL_SKILL_ID];
            k = s_cmdPara[CMD_SKILLCONTROL_EXP];
            actor = s_actorCommon[i];
            actor.SetSkill(j, k);
            res = false;
            break;
        case SCHEMA_FIGHT:
            StartBattle(s_cmdPara[CMD_FIGHT_ENEMY_TEAM_ID], s_cmdPara[CMD_FIGHT_CAN_RUN], s_cmdPara[CMD_FIGHT_LOSE_CONTINUE]);
            res = false;
            break;
        case SCHEMA_END_GAMG:
//@#if Channel == "GF"
            SetGameState(GS_UPLOADSCORE);
//@#else
//@            SetGameState(GS_LOADMENU);
//@#endif
            res = false;
            break;
        case SCHEMA_GAME_OVER:
            SetGameState(GS_GAME_OVER);
            System.out.println("unhandle cmdType: " + cmdType);
            res = false;
            break;
        case SCHEMA_MESSAGE:
            s_uiContentID = s_commonStringCount + s_cmdPara[CMD_MESSAGE_MESSAGE];
            s_uiMessageTimer = s_cmdPara[CMD_MESSAGE_TIME];
            InitUI(UI_TYPE_MESSAGEBOX, UI_MESSAGE_MAX_LINE, EP_NORMAL);
            SetGameEP(EP_DIALOG);
            res = false;
            break;
        case SCHEMA_GETPACKAGEINFO:
            i = s_cmdPara[CMD_GETPACKAGEINFO_ITEMID] & 0xffff;
            j = GetArticalID(i);
            i = GetArticalType(i);
            s_variables[s_cmdPara[CMD_GETPACKAGEINFO_PARA]] = PackageGet(j, i);
            res = false;
            break;
        case SCHEMA_MISSION:
            i = s_cmdPara[CMD_MISSION_TASKS_ID];
            j = s_cmdPara[CMD_MISSION_CONTROL];
            k = s_cmdPara[CMD_MISSION_MAINMISSION] == T_TRUE ? 0 : 1;
            entity0 = s_entityList[ENTITY_LIST_MISSION].SearchObject(i);
            if (j == T_TRUE)
            {
                if (entity0 != null)
                {
                    entity0.m_count++;
//@#if Channel == "GF"
                    GFUploadScore.s_score += 30;
//@#endif
                }
                else
                {
                    entity0 = CEntity.GetInstance();
                    entity0.m_id = i;
                    entity0.m_type = (byte) k;
                    entity0.m_count = 1;
                    entity0.m_icon = s_dbTasks[i][DB_TASKS_ICON];
                    entity0.m_color = s_dbTasks[i][DB_TASKS_COLOR];
                    s_entityList[ENTITY_LIST_MISSION].InsertAfter(entity0);
                }
            }
            else
            {
                if (entity0 != null)
                {
                    entity0.RemoveSelf();
                    entity0.Release();
                }
            }
            res = false;
            break;
        case SCHEMA_TIMERCONTROL:
            TimerControl(s_cmdPara[CMD_TIMERCONTROL_ID], s_cmdPara[CMD_TIMERCONTROL_CONTROL], s_cmdPara[CMD_TIMERCONTROL_PARA],
                         0xffff & s_cmdPara[CMD_TIMERCONTROL_INITVALUE]);
            res = false;
            break;
        case SCHEMA_TAKECONTROL:
            ControlActor(s_cmdPara[CMD_TAKECONTROL_ID]);
            res = false;
            break;
        case SCHEMA_CHEST:
            AddObjToActor(s_cmdPara[CMD_CHEST_ACTOR_ID], s_cmdPara[CMD_CHEST_ITEM], s_cmdPara[CMD_CHEST_QUANTITY]);
            res = true;
            break;
        case SCHEMA_FLASH:
            s_flashCount = s_cmdPara[CMD_FLASH_TIME];
            s_flashCur = 0;
            s_flashColor = ((s_cmdPara[CMD_FLASH_COLOR_H] % 0xffff) << 16) + (s_cmdPara[CMD_FLASH_COLOR_L] % 0xffff);
            s_flashInterval = s_cmdPara[CMD_FLASH_INTERVAL];
            s_flashFadeIn = s_cmdPara[CMD_FLASH_FADEIN];
            s_flashFadeOut = s_cmdPara[CMD_FLASH_FADEOUT];
            res = true;
            break;
        case SCHEMA_CAMERA_COLOR:
            s_cameraColorStart = ((0xffff & s_cmdPara[CMD_CAMERA_COLOR_COLOR0_H]) << 16) + (0xffff & s_cmdPara[CMD_CAMERA_COLOR_COLOR0_L]);
            s_cameraColorEnd = ((0xffff & s_cmdPara[CMD_CAMERA_COLOR_COLOR1_H]) << 16) + (0xffff & s_cmdPara[CMD_CAMERA_COLOR_COLOR1_L]);
            s_cameraColor = s_cameraColorStart;
            s_cameraColorCycle = s_cmdPara[CMD_CAMERA_COLOR_TIME];
            s_cameraColorTimer = 0;
            res = true;
            break;
        case SCHEMA_SET_Z:
            i = s_cmdPara[CMD_CONTROLACTORSHOW_ID];
            actor = GetActor(i);
            actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_Z] = (short) s_cmdPara[CMD_SET_Z_Z];
            res = true;
            break;
        case SCHEMA_SET_VELOCITY:
            i = s_cmdPara[CMD_SET_VELOCITY_ACTOR_ID];
            actor = GetActor(i);
            actor.m_vX = (short) (s_cmdPara[CMD_SET_VELOCITY_VX]);
            actor.m_vY = (short) (s_cmdPara[CMD_SET_VELOCITY_VY]);
            actor.m_aX = (short) (s_cmdPara[CMD_SET_VELOCITY_AX]);
            actor.m_aY = (short) (s_cmdPara[CMD_SET_VELOCITY_AY]);
            res = true;
            break;
        case SCHEMA_SET_ENEMY:
            i = s_cmdPara[CMD_SET_ENEMY_ACTOR_ID];
            actor = GetActor(i);
            actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISENEMY] = T_TRUE;
            actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_SPAWNTIME] = (short) s_cmdPara[CMD_SET_ENEMY_SPAWN_TIME];
            actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_RANGETYPE] = (short) s_cmdPara[CMD_SET_ENEMY_RANGE];
            switch (actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_RANGETYPE])
            {
            case DEFINE_RANGE_TYPE_RANDOM:
                actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_MOVETYPE] = ACTOR_MOVE_TYPE_RANGERANDOM;
                actor.m_destX = actor.m_curX;
                actor.m_destY = actor.m_curY;
                break;
            case DEFINE_RANGE_TYPE_FLY:
                actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_MOVETYPE] = ACTOR_MOVE_TYPE_RANGERANDOM;
                actor.m_destX = actor.m_curX;
                actor.m_destY = actor.m_curY;
                break;
            case DEFINE_RANGE_TYPE_STAND:
                actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_MOVETYPE] = ACTOR_MOVE_TYPE_STAND;
                actor.m_destX = actor.m_curX;
                actor.m_destY = actor.m_curY;
                break;
            }
            actor.m_anchorX = actor.m_curX;
            actor.m_anchorY = actor.m_curY;
            res = true;
            break;
        case SCHEMA_ACTIVE_ENEMY:
            for (i = 0; i < s_actors.length; i++)
            {
                if (s_actors[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISENEMY] == T_TRUE)
                {
                    s_actors[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] = (short) s_cmdPara[CMD_ACTIVE_ENEMY_ONOFF];
                }
            }
            res = true;
            break;
        case SCHEMA_SET_EMOTION:
            i = s_cmdPara[CMD_SET_EMOTION_ACTOR_ID];
            actor = GetActor(i);
            actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_EMOTION_TYPE] = (short) s_cmdPara[CMD_SET_EMOTION_EMO];
            actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_EMOTION_TIME] = (short) s_cmdPara[CMD_SET_EMOTION_TIME];
            actor.m_actorEff.SetAction(actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_EMOTION_TYPE]);
            actor.m_actorEff.m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] = T_TRUE;
            actor.m_actorEff.m_repeat = true;
            res = true;
            break;
        case SCHEMA_ADD_ENEMYTEAM:
            AddObjToActor(
                    s_cmdPara[CMD_ADD_ENEMYTEAM_ACTOR_ID],
                    s_cmdPara[CMD_ADD_ENEMYTEAM_ENEMY_TEAM],
                    s_cmdPara[CMD_ADD_ENEMYTEAM_WEIGHT]);
            res = true;
            break;
        case SCHEMA_BC_DIALOG:
            s_battleScriptType[s_battleScriptNum] = (byte) cmdType;
            i = s_battleScriptNum * 7;
            s_battleScriptData[i + CMD_BC_DIALOG_PROTRAIT_ID] = s_cmdPara[CMD_BC_DIALOG_PROTRAIT_ID];
            s_battleScriptData[i + CMD_BC_DIALOG_PROTRAIT_POS_H] = s_cmdPara[CMD_BC_DIALOG_PROTRAIT_POS_H];
            s_battleScriptData[i + CMD_BC_DIALOG_PROTRAIT_POS_V] = s_cmdPara[CMD_BC_DIALOG_PROTRAIT_POS_V];
            s_battleScriptData[i + CMD_BC_DIALOG_MESSAGE] = s_commonStringCount + s_cmdPara[CMD_BC_DIALOG_MESSAGE];
            s_battleScriptData[i + CMD_BC_DIALOG_ROUND] = s_cmdPara[CMD_BC_DIALOG_ROUND];
            s_battleScriptData[i + CMD_BC_DIALOG_STATE] = s_cmdPara[CMD_BC_DIALOG_STATE];
            s_battleScriptData[i + CMD_BC_DIALOG_EXPRESSION] = s_cmdPara[CMD_BC_DIALOG_EXPRESSION];
            s_battleScriptRound[s_battleScriptNum] = (short) s_cmdPara[CMD_BC_DIALOG_ROUND];
            s_battleScriptNum++;
            res = true;
            break;
        case SCHEMA_BC_LIMIT_INFO:
            s_battleScriptType[s_battleScriptNum] = (byte) cmdType;
            i = s_battleScriptNum * 7;
            s_battleScriptData[i + CMD_BC_LIMIT_INFO_PROTRAIT_ID] = s_cmdPara[CMD_BC_LIMIT_INFO_PROTRAIT_ID];
            s_battleScriptData[i + CMD_BC_LIMIT_INFO_PROTRAIT_POS_H] = s_cmdPara[CMD_BC_LIMIT_INFO_PROTRAIT_POS_H];
            s_battleScriptData[i + CMD_BC_LIMIT_INFO_PROTRAIT_POS_V] = s_cmdPara[CMD_BC_LIMIT_INFO_PROTRAIT_POS_V];
            s_battleScriptData[i + CMD_BC_LIMIT_INFO_MESSAGE] = s_commonStringCount + s_cmdPara[CMD_BC_LIMIT_INFO_MESSAGE];
            s_battleScriptData[i + CMD_BC_LIMIT_INFO_ROUND] = s_cmdPara[CMD_BC_LIMIT_INFO_ROUND];
            s_battleScriptData[i + CMD_BC_LIMIT_INFO_STATE] = s_cmdPara[CMD_BC_LIMIT_INFO_STATE];
            s_battleScriptRound[s_battleScriptNum] = (short) s_cmdPara[CMD_BC_LIMIT_INFO_ROUND];
            s_battleScriptNum++;
            res = true;
            break;
        case SCHEMA_BC_ANIM:
            i = s_battleScriptNum * 7;
            s_battleScriptType[s_battleScriptNum] = (byte) cmdType;
            s_battleScriptData[i + CMD_BC_ANIM_ROUND] = s_cmdPara[CMD_BC_ANIM_ROUND];
            s_battleScriptData[i + CMD_BC_ANIM_ACTION] = s_cmdPara[CMD_BC_ANIM_ACTION];
            s_battleScriptData[i + CMD_BC_ANIM_ACTOR] = s_cmdPara[CMD_BC_ANIM_ACTOR];
            s_battleScriptData[i + CMD_BC_ANIM_X] = s_cmdPara[CMD_BC_ANIM_X];
            s_battleScriptData[i + CMD_BC_ANIM_Y] = s_cmdPara[CMD_BC_ANIM_Y];
            s_battleScriptRound[s_battleScriptNum] = (short) s_cmdPara[CMD_BC_ACTION_ROUND];
            s_battleScriptNum++;
            res = true;
            break;
        case SCHEMA_BC_ACTION:
            i = s_battleScriptNum * 7;
            s_battleScriptType[s_battleScriptNum] = (byte) cmdType;
            s_battleScriptData[i + CMD_BC_ACTION_ROUND] = s_cmdPara[CMD_BC_ACTION_ROUND];
            s_battleScriptData[i + CMD_BC_ACTION_ACTION] = s_cmdPara[CMD_BC_ACTION_ACTION];
            s_battleScriptData[i + CMD_BC_ACTION_AIM] = s_cmdPara[CMD_BC_ACTION_AIM];
            s_battleScriptData[i + CMD_BC_ACTION_PARA0] = s_cmdPara[CMD_BC_ACTION_PARA0];
            s_battleScriptData[i + CMD_BC_ACTION_PARA1] = s_cmdPara[CMD_BC_ACTION_PARA1];
            s_battleScriptData[i + CMD_BC_ACTION_PARA2] = s_cmdPara[CMD_BC_ACTION_PARA2];
            s_battleScriptData[i + CMD_BC_ACTION_PARA3] = s_cmdPara[CMD_BC_ACTION_PARA3];
            s_battleScriptRound[s_battleScriptNum] = (short) s_cmdPara[CMD_BC_ACTION_ROUND];
            s_battleScriptNum++;
            res = true;
            break;
        case SCHEMA_BC_LIMIT:
            i = s_battleScriptNum * 7;
            s_battleScriptType[s_battleScriptNum] = (byte) cmdType;
            s_battleScriptData[i + CMD_BC_ACTION_ROUND] = s_cmdPara[CMD_BC_LIMIT_ROUND];
            s_battleScriptData[i + CMD_BC_ACTION_ACTION] = s_cmdPara[CMD_BC_LIMIT_ACTION];
            s_battleScriptData[i + CMD_BC_ACTION_AIM] = s_cmdPara[CMD_BC_LIMIT_AIM];
            s_battleScriptData[i + CMD_BC_LIMIT_ACTIONID] = s_cmdPara[CMD_BC_LIMIT_ACTIONID];
            s_battleScriptData[i + CMD_BC_ACTION_PARA1] = s_cmdPara[CMD_BC_LIMIT_PARA1];
            s_battleScriptData[i + CMD_BC_ACTION_PARA2] = s_cmdPara[CMD_BC_LIMIT_PARA2];
            s_battleScriptData[i + CMD_BC_ACTION_PARA3] = s_cmdPara[CMD_BC_LIMIT_PARA3];
            s_battleScriptRound[s_battleScriptNum] = (short) s_cmdPara[CMD_BC_ACTION_ROUND];
            s_battleScriptNum++;
            res = true;
            break;
        case SCHEMA_GET_ACTOR_BV:
            i = s_cmdPara[CMD_GET_ACTOR_BV_ACTOR_ID];
            j = s_cmdPara[CMD_GET_ACTOR_BV_PROPERTY];
            k = s_cmdPara[CMD_GET_ACTOR_BV_RESULT];
            actor = GetActor(i);
            s_variables[k] = actor.m_value[j];
            res = true;
            break;
        case SCHEMA_SET_ACTORP_BV:
            ApplyActorBV(s_cmdPara[CMD_SET_ACTORP_BV_ACTOR_ID], s_cmdPara[CMD_SET_ACTORP_BV_PROPERTY], s_cmdPara[CMD_SET_ACTORP_BV_VALUE]);
            res = true;
            break;
        case SCHEMA_OPENCHEST:
            i = s_cmdPara[CMD_OPENCHEST_ACTOR_ID];
            j = s_cmdPara[CMD_OPENCHEST_CHEST_TYPE];
            actor = GetActor(i);
            switch (j)
            {
            case DEFINE_CHEST_TYPE_SHOP:
                CreateSceneShop(actor);
                break;
            case DEFINE_CHEST_TYPE_ITEM:
                GetRandomItem(actor);
                break;
            }
            res = false;
            break;
        case SCHEMA_RECOVERY:
            TeamRecovery(false);
            res = true;
            break;
        case SCHEMA_CHANGE_BATTLE_BG:
            s_battleBGID = s_cmdPara[CMD_CHANGE_BATTLE_BG_BG_ID];
            res = true;
            break;
        case SCHEMA_GET_EQUIP:
            i = s_cmdPara[CMD_GET_EQUIP_LA];
            j = s_cmdPara[CMD_GET_EQUIP_PART];
            k = s_cmdPara[CMD_GET_EQUIP_RESULT];
            actor = s_actorCommon[i];
            s_variables[k] = actor.m_equips[j];
            res = true;
            break;
        case SCHEMA_SET_EQUIP:
            i = s_cmdPara[CMD_SET_EQUIP_LA];
            j = s_cmdPara[CMD_SET_EQUIP_PART];
            k = GetArticalID(s_cmdPara[CMD_SET_EQUIP_EQUIP]);
            ApplyEquip(i, j, k, s_cmdPara[CMD_SET_EQUIP_CREATE] == T_TRUE);
            RefreshActorBV(i);
            res = true;
            break;
        case SCHEMA_GET_ACTOR_PROPERTY:
            i = s_cmdPara[CMD_GET_ACTOR_PROPERTY_ACTOR_ID];
            j = s_cmdPara[CMD_GET_ACTOR_PROPERTY_PROPERTY];
            k = s_cmdPara[CMD_GET_ACTOR_PROPERTY_RESULT];
            actor = GetActor(i);
            s_variables[k] = actor.m_properties[j];
            res = true;
            break;
        case SCHEMA_SET_ACTOR_PROPERTY:
            i = s_cmdPara[CMD_SET_ACTOR_PROPERTY_ACTOR_ID];
            j = s_cmdPara[CMD_SET_ACTOR_PROPERTY_PROPERTY];
            k = s_cmdPara[CMD_SET_ACTOR_PROPERTY_VALUE];
            actor = GetActor(i);

            /** @todo 解决问题:轻功剧情时角色状态不及时刷新 */
            /** @todo 这么写不太妥当，有空再看看吧…… */
            boolean f = false;
            if (actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_TRANSPORT] > 0) //之前是否为轻功状态
            {
                f = true;
            }
            actor.m_properties[j] = (short) k;
            res = true;
            if (f == true && j == DEFINE_ACTOR_PROPERTY_PARA_TRANSPORT && k <= 0)
            {
                actor.m_actionID -= ACTION_TRANSPORT_UP; //手动将人物动作从轻功状态变为站立
            }
            break;
        case SCHEMA_SET_MUSIC:
            i = s_cmdPara[CMD_SET_MUSIC_ID];
            j = s_cmdPara[CMD_SET_MUSIC_TYPE];
            s_soundSlot[j] = (byte) i;
            if (j == DEFINE_MUSIC_SLOT_SCENE)
            {
                if ((s_optionFlag & OPTION_FLAG_SOUND) != 0 && s_gameState == GS_SCENE)
                {
                    SndStop();
                    SndPlay(i);
                }
                else
                {
                    s_curSnd = i;
                }
            }
            res = true;
            break;
        case SCHEMA_SHOCK_SCREEN:
            s_cameraShock[CAMERA_SHOCK_MX] = (short) s_cmdPara[CMD_SHOCK_SCREEN_MX];
            s_cameraShock[CAMERA_SHOCK_MY] = (short) s_cmdPara[CMD_SHOCK_SCREEN_MY];
            s_cameraShock[CAMERA_SHOCK_VX] = (short) s_cmdPara[CMD_SHOCK_SCREEN_VX];
            s_cameraShock[CAMERA_SHOCK_VY] = (short) s_cmdPara[CMD_SHOCK_SCREEN_VY];
            s_cameraShockTimer = s_cmdPara[CMD_SHOCK_SCREEN_TIME];
            res = true;
            break;
        case SCHEMA_CHANGE_PROPERTY:
            i = s_cmdPara[CMD_CHANGE_PROPERTY_ACTOR];
            j = s_cmdPara[CMD_CHANGE_PROPERTY_PARA];
            k = s_cmdPara[CMD_CHANGE_PROPERTY_VALUE];
            actor = s_actorCommon[i];
            actor.m_extraValue[j] += k;
            RefreshActorBV(i);
            res = true;
            break;
        case SCHEMA_MENU_CONTROL:
            i = s_cmdPara[CMD_MENU_CONTROL_ACTION];
            j = s_cmdPara[CMD_MENU_CONTROL_PARA];
            res = true;
            switch (i)
            {
            case DEFINE_MENU_ACTION_OPEN:
                if (j == WND_IGM)
                {
                    OpenTopWnd(j, IGM_TOP_X, IGM_TOP_Y, IGM_TOP_W, IGM_TOP_H);
                }
                else
                {
                    OpenTopWnd(j, IGM_X, IGM_Y, IGM_W, IGM_H);
                }
                CWnd.s_UICtrlMode = UI_CONTROL_SCRIPT;
                s_autoUICur = 0;
                res = false;
                break;
            case DEFINE_MENU_ACTION_CONFIRM:
                AddAutoUICmd(UI_AUTO_CONFIRM, 0);
                break;
            case DEFINE_MENU_ACTION_CURSOR:
                AddAutoUICmd(UI_AUTO_CURSOR, j);
                break;
            case DEFINE_MENU_ACTION_RETURN:
                AddAutoUICmd(UI_AUTO_RETURN, 0);
                break;
            case DEFINE_MENU_ACTION_WAIT:
                AddAutoUICmd(UI_AUTO_PAUSE, j);
                break;
            case DEFINE_MENU_ACTION_CONTROL:
                AddAutoUICmd(UI_AUTO_CONTROL, UI_CONTROL_MANUAL);
                break;
            case DEFINE_MENU_ACTION_ACTIVE_WND:
                AddAutoUICmd(UI_AUTO_CLOSE_ACTIVE, j);
                break;
//@#if SMS
//@            case DEFINE_MENU_ACTION_SMS_UI:
//@                ReleaseTempEntity();
//@                CEntity entity = CEntity.GetInstance();
//@                entity.m_id = j;
//@                entity.m_count = s_sendTime[j] + 1;
//@                s_tempList.InsertAfter(entity);
//@                s_wnd = CWnd.GetInstance();
//@                s_wnd.Init(WND_SMS_MESSAGE, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, true);
//@                s_wnd.m_isTop = true;
//@                s_wnd.m_curSelect = entity;
//@                SetGameEP(EP_IGM);
//@                res = false;
//@                break;
//@#endif
            }
            break;
//@#if EXIST_VIBRATE
//@        case SCHEMA_DEVICE_VIBRATE:
//@            i = s_cmdPara[CMD_DEVICE_VIBRATE_TIME];
//@            DeviceVibrate(i);
//@            res = true;
//@            break;
//@#endif
        case SCHEMA_PACKAGE_CONTROL:
            i = s_cmdPara[CMD_PACKAGE_CONTROL_TYPE];
            j = s_cmdPara[CMD_PACKAGE_CONTROL_ID];
            k = s_cmdPara[CMD_PACKAGE_CONTROL_COUNT];

//@#if SMS
//@            if (i == DEFINE_HOLD_TYPE_SMS)
//@            {
//@                k = s_sendTime[j] > 0 ? (s_sendTime[j] + 1) : k;
//@            }
//@
//@#endif
            PackageModify(j, i, k);
            res = true;
            break;
        case SCHEMA_GET_GAMEDATA:
            i = s_cmdPara[CMD_GET_GAMEDATA_TYPE];
            s_variables[s_cmdPara[CMD_GET_GAMEDATA_RESULT]] = s_gameData[i];
            res = true;
            break;
        case SCHEMA_SET_HERO_ANIM:
            i = s_cmdPara[CMD_SET_HERO_ANIM_ACTOR];
            j = s_cmdPara[CMD_SET_HERO_ANIM_ANIM];
            actor = s_actorCommon[i];
            actor.m_classID = j;
            res = true;
            break;
//@#if RUN_SCRIPT_IN_IGM
        case SCHEMA_UI_DIALOG:
            i = s_UIScriptNum * UI_SCRIPT_PARA;
            s_UIScriptType[s_UIScriptNum] = (byte) cmdType;
            s_UIScriptData[i + CMD_UI_DIALOG_PROTRAIT_ID] = s_cmdPara[CMD_UI_DIALOG_PROTRAIT_ID];
            s_UIScriptData[i + CMD_UI_DIALOG_PROTRAIT_POS_H] = s_cmdPara[CMD_UI_DIALOG_PROTRAIT_POS_H];
            s_UIScriptData[i + CMD_UI_DIALOG_PROTRAIT_POS_V] = s_cmdPara[CMD_UI_DIALOG_PROTRAIT_POS_V];
            s_UIScriptData[i + CMD_UI_DIALOG_MESSAGE] = s_commonStringCount + s_cmdPara[CMD_UI_DIALOG_MESSAGE];
            s_UIScriptData[i + CMD_UI_DIALOG_EXPRESSION] = s_cmdPara[CMD_UI_DIALOG_EXPRESSION];
            s_UIScriptNum++;
            res = true;
            break;
        case SCHEMA_UI_LIMIT:
            i = s_UIScriptNum * UI_SCRIPT_PARA;
            s_UIScriptType[s_UIScriptNum] = (byte) cmdType;
            s_UIScriptData[i + CMD_UI_LIMIT_CURSOR] = s_cmdPara[CMD_UI_LIMIT_CURSOR];
            s_UIScriptNum++;
            res = true;
            break;
        case SCHEMA_UI_LIMIT_INFO:
            i = s_UIScriptNum * UI_SCRIPT_PARA;
            s_UIScriptType[s_UIScriptNum] = (byte) cmdType;
            s_UIScriptData[i + CMD_UI_LIMIT_INFO_PROTRAIT_ID] = s_cmdPara[CMD_UI_LIMIT_INFO_PROTRAIT_ID];
            s_UIScriptData[i + CMD_UI_LIMIT_INFO_PROTRAIT_POS_H] = s_cmdPara[CMD_UI_LIMIT_INFO_PROTRAIT_POS_H];
            s_UIScriptData[i + CMD_UI_LIMIT_INFO_PROTRAIT_POS_V] = s_cmdPara[CMD_UI_LIMIT_INFO_PROTRAIT_POS_V];
            s_UIScriptData[i + CMD_UI_LIMIT_INFO_MESSAGE] = s_commonStringCount + s_cmdPara[CMD_UI_LIMIT_INFO_MESSAGE];
            s_UIScriptData[i + CMD_UI_LIMIT_INFO_EXPRESSION] = s_cmdPara[CMD_UI_LIMIT_INFO_EXPRESSION];
            s_UIScriptNum++;
            res = true;
            break;
        case SCHEMA_UI_ANIM:
            i = s_UIScriptNum * UI_SCRIPT_PARA;
            s_UIScriptType[s_UIScriptNum] = (byte) cmdType;
            s_UIScriptData[i + CMD_UI_ANIM_ACTOR] = s_cmdPara[CMD_UI_ANIM_ACTOR];
            s_UIScriptData[i + CMD_UI_ANIM_ACTION] = s_cmdPara[CMD_UI_ANIM_ACTION];
            s_UIScriptData[i + CMD_UI_ANIM_X] = s_cmdPara[CMD_UI_ANIM_X];
            s_UIScriptData[i + CMD_UI_ANIM_Y] = s_commonStringCount + s_cmdPara[CMD_UI_ANIM_Y];
            s_UIScriptNum++;
            res = true;
            break;
        case SCHEMA_UI_WAIT:
            i = s_UIScriptNum * UI_SCRIPT_PARA;
            s_UIScriptType[s_UIScriptNum] = (byte) cmdType;
            s_UIScriptData[i + CMD_UI_WAIT_TIME] = s_cmdPara[CMD_UI_WAIT_TIME];
            s_UIScriptNum++;
            res = true;
            break;
//@#endif
        case SCHEMA_SET_PORTRAIT:
            i = s_cmdPara[CMD_SET_PORTRAIT_ID];
            j = s_cmdPara[CMD_SET_PORTRAIT_SHOW];
            s_resInUsed[ANIM_PORTRAIT_0 + i] = (j == T_TRUE);
            res = true;
            break;
        case SCHEMA_BOARD:
            i = s_commonStringCount + s_cmdPara[CMD_BOARD_MESSAGE];
            boolean showBG = s_cmdPara[CMD_BOARD_SHOW_BG] == T_TRUE;
            boolean scroll = s_cmdPara[CMD_BOARD_SCROLL] == T_TRUE;
            j = s_cmdPara[CMD_BOARD_WIDTH];
            k = s_cmdPara[CMD_BOARD_LINE];
            InitBoard(i, showBG, scroll, j, k);
            SetGameEP(EP_DIALOG);
            res = false;
            break;
        case SCHEMA_SET_UI_ACTION:
            i = s_cmdPara[CMD_SET_UI_ACTION_TYPE];
            j = s_cmdPara[CMD_SET_UI_ACTION_ACTION];
            switch (i)
            {
            case DEFINE_UI_TYPE_NORMAL:
                s_UIAction = j;
                SetSceneUIAction(s_actorUI, s_UIAction);
                break;
//@#if SMS
//@            case DEFINE_UI_TYPE_SMS:
//@                s_SMSAction = j;
//@                SetSceneUIAction(s_actorSMS, s_SMSAction);
//@                break;
//@#endif
            }
            res = true;
            break;
        case SCHEMA_PORTABLE_SHOP:
            i = s_cmdPara[CMD_PORTABLE_SHOP_ID] & 0xffff;
            j = s_cmdPara[CMD_PORTABLE_SHOP_TYPE];
            PackageModify(i, DEFINE_HOLD_TYPE_GOODS, j);
            res = true;
            break;
        case SCHEMA_TRANSPORT:
            i = s_cmdPara[CMD_TRANSPORT_STATE];
            if (i == T_TRUE)
            {
                if (s_actorHumanControl.m_properties[DEFINE_ACTOR_PROPERTY_PARA_TRANSPORT] == T_FALSE)
                {
                    StartToFly(s_actorHumanControl);
                    s_showTeamNumOld = s_variables[SCRIPT_VARIABLE_TEAM_SHOW_NUM];
                    s_variables[SCRIPT_VARIABLE_TEAM_SHOW_NUM] = 1;
                }
            }
            else
            {
                if (s_actorHumanControl.m_properties[DEFINE_ACTOR_PROPERTY_PARA_TRANSPORT] == T_TRUE)
                {
                    LandingToGround(s_actorHumanControl);
                }
            }
            res = true;
            break;
        case SCHEMA_FACE_TO:
            if (s_triggerActor != null)
            {
                s_triggerActor.FaceTo(s_scriptActor.m_curX, s_scriptActor.m_curY, ACTION_UP);
            }
            res = true;
            break;
        case SCHEMA_WAIT_KEY:
            i = s_cmdPara[CMD_WAIT_KEY_KEY];
            j = s_cmdPara[CMD_WAIT_KEY_TIMER];
            s_scriptTag0[scriptID] = GetScriprtWaitKey(i);
            if (j > 0)
            {
                s_scriptTag1[scriptID] = StartSystemTimer(j);
                s_scriptTag2[scriptID] = T_TRUE;
            }
            else
            {
                s_scriptTag2[scriptID] = T_FALSE;
            }
            s_scriptState[scriptID] = SCRIPT_STATE_WAIT_KEY;
            s_variables[SCRIPT_VARIABLE_KEY_RESPONSE] = T_FALSE;
            res = false;
            break;
        case SCHEMA_SET_FOLLOW_TEAM:
            i = s_cmdPara[CMD_SET_FOLLOW_TEAM_ACTOR]; //加入哪一个actor
            j = s_cmdPara[CMD_SET_FOLLOW_TEAM_STATE]; //加入或者删除指定的actor
            k = s_cmdPara[CMD_SET_FOLLOW_TEAM_LOCATION]; //actor所在位置
            switch (j)
            {
            case DEFINE_TYPE_FOLLOW_INCREASE:
                AddFollowActor(GetActor(i), k); //将actor 放在队伍指定的位置

//                System.out.println(GetActor(i) + "   GetActor(i)");
                break;
            case DEFINE_TYPE_FOLLOW_DELETE:
                DelFollowActor(k); //将指定位置的actor删除
                break;
            }
            res = true;
            break;
        default:
            System.out.println("unhandle cmdType: " + cmdType);
            res = false;
            break;
        }
        s_scriptCmdPoint[scriptID]++;

        //末尾控制,预防没有return指令的脚本执行越界
        if (s_scriptCmdPoint[scriptID] + s_scriptCmdSetDataOffset[scriptID] >= s_scriptCmdSetDataOffset[scriptID + 1])
        {
            ScriptEndCmdSet(scriptID);
            res = false;
        }
        return res;
    }

    static CActor s_scriptActor;
    static CActor s_triggerActor;

    static void ScriptEndCmdSet(int cmdSetID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Script.h
        s_scriptActor = null;
        s_triggerActor = null;
        if (s_scriptCondition[cmdSetID] == CONDITION_COLLIDE)
        {
            s_scriptState[cmdSetID] = SCRIPT_STATE_COLLIDE_END;
        }
        else
        {
            s_scriptState[cmdSetID] = SCRIPT_STATE_END;
        }
    }

    /** @todo 这里实现的笨拙 */
    static boolean ScriptCheckCondition(int index)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Script.h
        boolean res = false;
        CActor actorRef = null;

        switch (s_scriptCondition[index])
        {
        case CONDITION_AUTO:
            res = true;
            break;
        case CONDITION_KEY:
            if ((null != s_actorHumanControl))
            {
                actorRef = GetActor(s_scriptActorID[index]);
                if (actorRef.m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] == T_FALSE)
                {
                    res = false;
                }
                else if (UtilIsRectsCollide(actorRef.m_curX + actorRef.m_activatedBox[RECT_X] - 1 - ACTOR_STEP_LEN,
                                            actorRef.m_curY + actorRef.m_activatedBox[RECT_Y] - 1 - ACTOR_STEP_LEN,
                                            actorRef.m_activatedBox[RECT_W] + (ACTOR_STEP_LEN << 1) + 1,
                                            actorRef.m_activatedBox[RECT_H] + (ACTOR_STEP_LEN << 1) + 1,
                                            s_actorHumanControl.m_curX + s_actorHumanControl.m_collideBox[RECT_X],
                                            s_actorHumanControl.m_curY + s_actorHumanControl.m_collideBox[RECT_Y],
                                            s_actorHumanControl.m_collideBox[RECT_W],
                                            s_actorHumanControl.m_collideBox[RECT_H])
                        )
                {
                    if (IsKeySinglePressed(GKEY_MIDLLE))
                    {
                        //FaceTo
                        s_scriptActor = actorRef;
                        s_triggerActor = s_actorHumanControl;
                        res = true;
                    }
                }
            }
            break;
        case CONDITION_COLLIDE:
            if ((null != s_actorHumanControl))
            {
                actorRef = GetActor(s_scriptActorID[index]);
                if (UtilIsRectsCollide(actorRef.m_curX + actorRef.m_activatedBox[RECT_X] - ACTOR_STEP_LEN,
                                       actorRef.m_curY + actorRef.m_activatedBox[RECT_Y] - ACTOR_STEP_LEN,
                                       actorRef.m_activatedBox[RECT_W] + ACTOR_STEP_LEN * 2,
                                       actorRef.m_activatedBox[RECT_H] + ACTOR_STEP_LEN * 2,
                                       s_actorHumanControl.m_curX + s_actorHumanControl.m_collideBox[RECT_X],
                                       s_actorHumanControl.m_curY + s_actorHumanControl.m_collideBox[RECT_Y],
                                       s_actorHumanControl.m_collideBox[RECT_W],
                                       s_actorHumanControl.m_collideBox[RECT_H]))
                {
                    s_scriptActor = actorRef;
                    s_triggerActor = s_actorHumanControl;
                    res = true;
                }
            }
            break;
        case CONDITION_TIMER:
            break;
        }
        return res;
    }

    static void ScriptReset(int index)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Script.h
        s_scriptCmdPoint[index] = 0;
        s_scriptState[index] = SCRIPT_STATE_DEACTIVATED;
    }

    static void UpdateScript()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Script.h
        for (int i = 0; i < s_scriptNum; i++)
        {
//@#if CHEAT_ENABLE
//@            if (s_isCheated)
//@            {
//@                if ((s_cheatTag & CHEAT_TAG_PAUSE_SCRIPT) == 0)
//@                {
//@                    //s_isCheated
//@                    if ((s_cheatTag & CHEAT_TAG_STEP_SCRIPT) != 0)
//@                    {
//@                        if (s_cheatInput == 1)
//@                        {
//@                            //执行一步
//@                            UpdateSingleScript(i);
//@                            s_cheatInput = 0;
//@                        }
//@                    }
//@                    else
//@                    {
//@                        UpdateSingleScript(i);
//@                    }
//@                }
//@            }
//@            else
//@#endif
            {
                UpdateSingleScript(i);
            }
        }
    }

    static int CheckScriptCmdCondition(int leftType, int leftV0, int leftV1, int op, int rightType, int rightV0, int rightV1)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Script.h
        int res = 0;
        int lv = GetScriptDataValue(leftType, leftV0, leftV1);
        int rv = GetScriptDataValue(rightType, rightV0, rightV1);
        res = UtilCalc(lv, op, rv);
        return res;
    }


    static int GetScriptDataValue(int dataType, int v0, int v1)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Script.h
        int res = 0;
        switch (dataType)
        {
        case DEFINE_DATA_TYPE_CONST:
            res = v0;
            break;
        case DEFINE_DATA_TYPE_VARIABLE:
            res = s_variables[v0];
            break;
        case DEFINE_DATA_TYPE_RANDOM:
            res = UtilRandom(v0, v1);
            break;
        }
        return res;
    }

    static void UpdateSingleScript(int scriptID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Script.h
        if (s_bScriptPause)
        {
            return;
        }
        int i;
        CActor a;
        boolean isContinue = true;

        while (isContinue)
        {
            //查询脚本状态
            switch (s_scriptState[scriptID])
            {
            case SCRIPT_STATE_DEACTIVATED:
                if (ScriptCheckCondition(scriptID))
                {
                    s_scriptState[scriptID] = SCRIPT_STATE_READY;
                }
                else
                {
                    isContinue = false;
                }

                //检查条件
                break;
            case SCRIPT_STATE_READY:
                isContinue = ScriptRunCmd(scriptID);
                break;
            case SCRIPT_STATE_WAIT_TIMER:
                i = s_scriptTag0[scriptID];
                isContinue = CheckScriptTimer(scriptID, i);
                break;
            case SCRIPT_STATE_WAIT_CAMERA:
                if (s_cameraState != CAMERA_STATE_MOVE)
                {
                    s_scriptState[scriptID] = SCRIPT_STATE_READY;
                    isContinue = true;
                }
                else
                {
                    isContinue = false;
                }
                break;
            case SCRIPT_STATE_WAIT_ACTOR_PARA:
                a = GetActor(s_scriptTag0[scriptID]);
                if (a.m_properties[s_scriptTag1[scriptID]] == s_scriptTag2[scriptID])
                {
                    s_scriptState[scriptID] = SCRIPT_STATE_READY;
                    isContinue = true;
                }
                else
                {
                    isContinue = false;
                }
                break;
            case SCRIPT_STATE_WAIT_KEY:
                i = s_scriptTag0[scriptID];
                if (IsKeySinglePressed(i))
                {
                    s_scriptState[scriptID] = SCRIPT_STATE_READY;
                    s_variables[SCRIPT_VARIABLE_KEY_RESPONSE] = T_TRUE;
                    ReturnTimer(s_scriptTag1[scriptID]);
                    isContinue = true;
                }
                else if (s_scriptTag2[scriptID] == T_TRUE)
                {
                    isContinue = CheckScriptTimer(scriptID, s_scriptTag1[scriptID]);
                }
                else
                {
                    isContinue = false;
                }
                break;
            case SCRIPT_STATE_PAUSE:
            case SCRIPT_STATE_END:
                if (s_scriptRepeat[scriptID] == SCRIPT_REPEAT_ALWAYS)
                {
                    ScriptReset(scriptID);
                }
                isContinue = false;
                break;
            case SCRIPT_STATE_COLLIDE_END:
                CActor actorRef = null;
                if ((null != s_actorHumanControl))
                {
                    actorRef = GetActor(s_scriptActorID[scriptID]);
                    if (!UtilIsPointInRect(s_actorHumanControl.m_curX,
                                           s_actorHumanControl.m_curY,
                                           actorRef.m_curX + actorRef.m_activatedBox[RECT_X] - 1,
                                           actorRef.m_curY + actorRef.m_activatedBox[RECT_Y] - 1,
                                           actorRef.m_activatedBox[RECT_W] + 2,
                                           actorRef.m_activatedBox[RECT_H] + 2))
                    {
                        s_scriptState[scriptID] = SCRIPT_STATE_END;
                    }
                }
                isContinue = false;
                break;
            default:
                break;
            }
//@#if CHEAT_ENABLE
//@            if (s_isCheated)
//@            {
//@                if ((s_cheatTag & CHEAT_TAG_STEP_SCRIPT) != 0)
//@                {
//@                    isContinue = false;
//@                }
//@            }
//@#endif
        }
    }

    static boolean CheckScriptTimer(int scriptID, int index)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Script.h
        boolean res = false;
        if (s_timer[index] <= 0)
        {
            s_scriptState[scriptID] = SCRIPT_STATE_READY;
            s_timerState[index] = TIMER_STATE_IDLE;
            res = true;
        }
        else
        {
            res = false;
        }
        return res;
    }

    static int GetScriprtWaitKey(int value)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Script.h
        int res = value;
        switch (value)
        {
        case DEFINE_KEY_VALUE_MIDDLE:
            res = GKEY_MIDLLE;
            break;
        case DEFINE_KEY_VALUE_DECIDE:
            res = GKEY_SOFTLEFT;
            break;
        case DEFINE_KEY_VALUE_CHANCEL:
            res = GKEY_SOFTRIGHT;
            break;
        case DEFINE_KEY_VALUE_UP:
            res = GKEY_MOVEUP;
            break;
        case DEFINE_KEY_VALUE_RIGHT:
            res = GKEY_MOVERIGHT;
            break;
        case DEFINE_KEY_VALUE_DOWN:
            res = GKEY_MOVEDOWN;
            break;
        case DEFINE_KEY_VALUE_LEFT:
            res = GKEY_MOVELEFT;
            break;
        }
        return res;
    }

    static int GetScriptLen(int scriptID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Script.h
        int res = 0;
        if (scriptID == s_scriptNum - 1)
        {
            res = s_scriptCmdType.length - s_scriptCmdSetDataOffset[scriptID];
        }
        else
        {
            res = s_scriptCmdSetDataOffset[scriptID + 1] - s_scriptCmdSetDataOffset[scriptID];
        }
        return res;
    }

    /**
     * 读取脚本数据
     * 影响的存储单元包括
     * s_scriptNum                   脚本数量
     * s_scriptCmdPoint              每个脚本的执行指针(当前指向哪一条命令)
     * s_scriptCondition             每个脚本的触发条件
     * s_scriptCmdSetDataOffset      每个脚本集合的数据偏移,逻辑上等于这个脚本集合的命令数累计偏移
     * s_scriptState                 每个脚本的状态
     * s_scriptCmdType               每个命令的类型
     * s_scriptCmdDataOffset         每个命令的数据偏移,逻辑上等于某条命令在全部命令数据里的偏移
     *
     *
     */
    static void LoadScript()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Script.h
        try
        {
//@#if USE_PAK_RESOURCES
            OpenTrunk("/script.pak");
            SkipTrunk(s_sceneID);
//@#else
//@            s_trunkDis = new DataInputStream("".getClass().getResourceAsStream("/script" + s_sceneID + ".bin"));
//@#endif
            s_dataInputStream = s_trunkDis;
            s_scriptNum = UtilReadNumber(s_dataInputStream, NUMBER_SHORT);
            s_scriptCmdPoint = new int[s_scriptNum];
            s_scriptCondition = new int[s_scriptNum];
            s_scriptRepeat = new byte[s_scriptNum];
            s_scriptTag0 = new int[s_scriptNum];
            s_scriptTag1 = new int[s_scriptNum];
            s_scriptTag2 = new int[s_scriptNum];
            s_scriptActorID = new short[s_scriptNum];
            s_scriptCmdSetDataOffset = new int[s_scriptNum + 1];
            s_scriptState = new int[s_scriptNum];

            s_scriptInit = -1;

            for (int i = 0; i < s_scriptNum; i++)
            {
                s_scriptActorID[i] = (short) UtilReadNumber(s_dataInputStream, NUMBER_SHORT);
            }
            //脚本的触发条件
            for (int i = 0; i < s_scriptNum; i++)
            {
                s_scriptCondition[i] = UtilReadNumber(s_dataInputStream, NUMBER_BYTE);
                if (s_scriptCondition[i] == CONDITION_INIT)
                {
                    s_scriptInit = i;
                    s_scriptState[s_scriptInit] = SCRIPT_STATE_READY;
                }
            }
            //脚本的重复
            for (int i = 0; i < s_scriptNum; i++)
            {
                s_scriptRepeat[i] = (byte) UtilReadNumber(s_dataInputStream, NUMBER_BYTE);
            }

            //每个脚本的命令偏移
            int sum = 0;
            for (int i = 0; i < s_scriptNum; i++)
            {
                s_scriptCmdSetDataOffset[i] = sum;
                sum += UtilReadNumber(s_dataInputStream, NUMBER_SHORT);
            }
            //补一个位
            s_scriptCmdSetDataOffset[s_scriptNum] = sum;

            s_scriptCmdType = new short[sum];
            s_scriptCmdDataOffset = new short[sum];
            for (int i = 0; i < sum; i++)
            {
                s_scriptCmdType[i] = (short) UtilReadNumber(s_dataInputStream, NUMBER_SHORT);
            }
            short len_sum = 0;
            for (int i = 0; i < sum; i++)
            {
                s_scriptCmdDataOffset[i] = len_sum;
                len_sum += CMD_PARALEN[s_scriptCmdType[i]];
            }
            s_scriptCmdData = new short[len_sum];
            for (int i = 0; i < s_scriptCmdData.length; i++)
            {
                s_scriptCmdData[i] = (short) UtilReadNumber(s_dataInputStream, NUMBER_SHORT);
            }
            CloseTrunk();
            s_dataInputStream = null;

            for (int i = 0; i < s_scriptNum; i++)
            {
                ScriptReset(i);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    static void FreeScript()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Script.h
        s_scriptCmdPoint = null;
        s_scriptCondition = null;
        s_scriptRepeat = null;
        s_scriptTag0 = null;
        s_scriptTag1 = null;
        s_scriptTag2 = null;
        s_scriptActorID = null;
        s_scriptCmdSetDataOffset = null;
        s_scriptState = null;
//@#if RUN_SCRIPT_IN_IGM
        s_actorMenuUICount = 0;
        for (int i = 0; i < BATTLE_UI_ACTOR_COUNT; i++)
        {
            s_actorMenuUI[i] = null;
        }
//@#endif
        System.gc();
    }

    static void SetVariable(int variableID, int operator, int dataType, int v0, int v1)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Script.h
        //step1 确定操作数
        short value = 0;
        switch (dataType)
        {
        case DEFINE_DATA_TYPE_VARIABLE:
            value = (short) s_variables[v0];
            break;
        case DEFINE_DATA_TYPE_RANDOM:
            value = (short) UtilRandom(v0, v1);
            break;
        case DEFINE_DATA_TYPE_CONST:
            value = (short) v0;
            break;
        }
        switch (operator)
        {
        case DEFINE_OPERATOR_SET:
            s_variables[variableID] = value;
            break;
        case DEFINE_OPERATOR_PLUS:
            s_variables[variableID] += value;
            break;
        case DEFINE_OPERATOR_SUB:
            s_variables[variableID] -= value;
            break;
        case DEFINE_OPERATOR_MULTIPLY:
            s_variables[variableID] *= value;
            break;
        case DEFINE_OPERATOR_DIVIDE:
            s_variables[variableID] /= value;
            break;
        case DEFINE_OPERATOR_MOD:
            s_variables[variableID] %= value;
            break;
        }
    }

//@@
//@@..\CGame\CGame_RMS.h
    /*
     * ==================================================  存档管理方法  ==============================
     */
    static int[][] s_rmsInfo;
    static int[] s_rmsScene = new int[SAVESLOT_MAX];

    static int RMS(boolean isSave, int num)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RMS.h
        int res = RMS_RETURN_STATE_OK;
        RecordStore rs = null;
        RecordEnumeration re = null;
        int offset = 0;
//@#if USE_API_NEXTRECORDID
//@        int commonRecordID = -1;
//@        int sceneRecordID = -1;
//@#else
        int commonRecordID = RMS_COMMONRECORD_ID;
        int sceneRecordID = num + RMS_SCENERECORD_ID_OFFSET;
//@#endif
        int i, j, k, l, m;
        CEntity entity0, entity1;

        byte[] dataCommon = s_trunkBuffer0;
        byte[] dataThread = s_trunkBuffer1;

        for (i = 0; i < dataCommon.length; i++)
        {
            dataCommon[i] = 0;
        }

        for (i = 0; i < dataThread.length; i++)
        {
            dataThread[i] = 0;
        }

        //打开记录
        try
        {
            rs = RecordStore.openRecordStore(RMS_NAME, true);
            re = rs.enumerateRecords(null, null, false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            s_strErr += e.getMessage();
        }
        try
        {
            if (!re.hasNextElement())
            {
                //如果是初次读写存档,建立新档案
                offset = AMOUNT_MI_IGM_RMS * AMOUNT_RMS_MESSAGE * 4;
//@#if SMS
//@                UtilIntToArray(dataThread, offset, 0);
//@                offset += 4;
//@#endif
                rs.addRecord(dataCommon, 0, RMS_COMMON_SIZE);
                for (i = 1; i < SAVESLOT_MAX; i++)
                {
                    rs.addRecord(dataThread, 0, dataThread.length);
                }
            }
            else
            {
                //如果不是初次读写存档, 则取得每个存档的ID
//@#if USE_API_NEXTRECORDID
//@                commonRecordID = re.nextRecordId();
//@#endif
                //读取公共数据
                dataCommon = rs.getRecord(commonRecordID);
                if (num < AMOUNT_MI_IGM_RMS)
                {
                    for (i = 0; i < AMOUNT_MI_IGM_RMS; i++)
                    {
                        offset = i * AMOUNT_RMS_MESSAGE * 4;
                        for (j = 0; j < AMOUNT_RMS_MESSAGE; j++)
                        {
                            s_rmsInfo[i][j] = UtilArrayToInt(dataCommon, offset);
                            offset += 4;
                        }
                    }

                    offset = AMOUNT_MI_IGM_RMS * AMOUNT_RMS_MESSAGE * 4;
//@#if SMS
//@                    for (j = 0; j <= SCRIPT_VARIABLE_SMS_NEED_ACTIVE; j++)
//@                    {
//@                        if (!isSave)
//@                        {
//@                            s_variables[SCRIPT_VARIABLE_SMS_PARA0 + j] = UtilArrayToInt(dataCommon, offset);
//@                        }
//@                        offset += 4;
//@                    }
//@
//@                    for (j = 0; j < s_sendTime.length; j++)
//@                    {
//@                        s_sendTime[j] = UtilArrayToInt(dataCommon, offset);
//@                        offset += 4;
//@                    }
//@#endif
                }
//@#if USE_API_NEXTRECORDID
//@                for (i = 0; i <= num; i++)
//@                {
//@                    sceneRecordID = re.nextRecordId();
//@                }
//@#endif
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            if (num >= 0)
            {
                //SL玩家存档
                if (isSave)
                {
                    //写场景数据
                    //是否使用
                    offset = 0;
                    dataThread[offset] = (byte) s_sceneID;
                    offset++;
                    UtilIntToArray(dataThread, offset, s_scriptNum);
                    offset += 4;

                    for (i = 0; i < s_scriptNum; i++)
                    {
                        UtilIntToArray(dataThread, offset, s_scriptCmdPoint[i]);
                        offset += 4;
                        UtilIntToArray(dataThread, offset, s_scriptState[i]);
                        offset += 4;
                        UtilIntToArray(dataThread, offset, s_scriptTag0[i]);
                        offset += 4;
                        UtilIntToArray(dataThread, offset, s_scriptTag1[i]);
                        offset += 4;
                        UtilIntToArray(dataThread, offset, s_scriptTag2[i]);
                        offset += 4;
                    }
                    //计时器
                    for (i = 0; i < TIMER_POOL_SIZE; i++)
                    {
                        UtilIntToArray(dataThread, offset, s_timerState[i]);
                        offset += 4;
                        UtilIntToArray(dataThread, offset, s_timer[i]);
                        offset += 4;
                        UtilShortToArray(dataThread, offset, s_timerAttach[i]);
                        offset += 2;
                        UtilIntToArray(dataThread, offset, s_timerInitValue[i]);
                        offset += 4;
                    }

                    for (i = SCRIPT_VARIABLE_SAVE_ON; i < AMOUNT_SCRIPT_VARIABLES; i++)
                    {
                        UtilIntToArray(dataThread, offset, s_variables[i]);
                        offset += 4;
                    }
                    int followToTeamCount = GetFollowCount();
                    UtilShortToArray(dataThread, offset, followToTeamCount);
                    offset += 4;
                    for (i = 0; i < followToTeamCount; i++)
                    {
//                        System.out.println(s_followToTeam[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_FOLLOWTEAM] + " v  " );
                        UtilShortToArray(dataThread, offset, s_followToTeam[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_FOLLOWTEAM]);
                        offset += 2;
                        //动画
                        UtilShortToArray(dataThread, offset, s_followToTeam[i].m_actionID);
                        offset += 2;
                        //位置X
                        UtilShortToArray(dataThread, offset, s_followToTeam[i].m_curX);
                        offset += 2;
                        //位置Y
                        UtilShortToArray(dataThread, offset, s_followToTeam[i].m_curY);
                        offset += 2;
                        //目标位置X
                        UtilIntToArray(dataThread, offset, s_followToTeam[i].m_destX);
                        offset += 4;
                        //目标位置Y
                        UtilIntToArray(dataThread, offset, s_followToTeam[i].m_destY);
                        offset += 4;
                        UtilIntToArray(dataThread, offset, s_teamMateCount);
                        offset += 4;

                    }
                    for (i = 0; i < TEAM_SIZE; i++)
                    {
                        //整个m_value
                        for (j = 0; j < s_actorCommon[i].m_value.length; j++)
                        {
                            UtilIntToArray(dataThread, offset, s_actorCommon[i].m_value[j]);
                            offset += 4;
                        }
                        //装备
                        for (j = 0; j < s_actorCommon[i].m_equips.length; j++)
                        {
                            UtilIntToArray(dataThread, offset, s_actorCommon[i].m_equips[j]);
                            offset += 4;
                        }

                        //提升属性
                        for (j = 0; j < s_actorCommon[i].m_extraValue.length; j++)
                        {
                            UtilIntToArray(dataThread, offset, s_actorCommon[i].m_extraValue[j]);
                            offset += 4;
                        }
                        //整个m_skill
                        if (null != s_actorCommon[i].m_entitySkill.m_next)
                        {
                            UtilIntToArray(dataThread, offset, s_actorCommon[i].m_entitySkill.m_next.GetCount());
                            offset += 4;
                            entity0 = s_actorCommon[i].m_entitySkill.m_next;
                            while (entity0 != null)
                            {
                                UtilIntToArray(dataThread, offset, entity0.m_id);
                                offset += 4;
                                UtilIntToArray(dataThread, offset, entity0.m_count);
                                offset += 4;
                                entity0 = entity0.m_next;
                            }
                        }
                        else
                        {
                            UtilIntToArray(dataThread, offset, 0);
                            offset += 4;
                        }

                        //整个m_properties
                        for (j = 0; j < s_actorCommon[i].m_properties.length; j++)
                        {
                            UtilShortToArray(dataThread, offset, s_actorCommon[i].m_properties[j]);
                            offset += 2;
                        }
                    }
                    UtilIntToArray(dataThread, offset, s_actors.length);
                    offset += 4;
                    for (i = 0; i < s_actors.length; i++)
                    {
                        //动画
                        UtilShortToArray(dataThread, offset, s_actors[i].m_actionID);
                        offset += 2;
                        //位置X
                        UtilShortToArray(dataThread, offset, s_actors[i].m_curX);
                        offset += 2;
                        //位置Y
                        UtilShortToArray(dataThread, offset, s_actors[i].m_curY);
                        offset += 2;
                        //整个m_properties
                        for (j = 0; j < s_actors[i].m_properties.length; j++)
                        {
                            UtilShortToArray(dataThread, offset, s_actors[i].m_properties[j]);
                            offset += 2;
                        }
                        //m_chest
                        UtilShortToArray(dataThread, offset, s_actors[i].m_chestAmount);
                        offset += 2;
                        for (j = 0; j < s_actors[i].m_chestAmount; j++)
                        {
                            UtilShortToArray(dataThread, offset, s_actors[i].m_chest[j * 2]);
                            offset += 2;
                            UtilShortToArray(dataThread, offset, s_actors[i].m_chest[j * 2 + 1]);
                            offset += 2;
                        }
                    }
                    for (i = 0; i < AMOUNT_ENTITY_LIST; i++)
                    {
                        k = s_entityList[i].GetCount() - 1;
                        UtilIntToArray(dataThread, offset, k);
                        offset += 4;
                        entity0 = s_entityList[i].m_next;
                        while (null != entity0)
                        {
                            UtilIntToArray(dataThread, offset, entity0.m_id);
                            offset += 4;
                            UtilIntToArray(dataThread, offset, entity0.m_count);
                            offset += 4;
                            UtilShortToArray(dataThread, offset, entity0.m_type);
                            offset += 2;
                            UtilIntToArray(dataThread, offset, entity0.m_color);
                            offset += 4;
                            UtilIntToArray(dataThread, offset, entity0.m_icon);
                            offset += 4;
                            entity0 = entity0.m_next;
                        }
                    }

                    //游戏数据
                    for (i = 0; i < DEFINE_AMOUNT_GAME_DATA; i++)
                    {
                        UtilIntToArray(dataThread, offset, s_gameData[i]);
                        offset += 4;
                    }

                    //控制权
                    UtilIntToArray(dataThread, offset, s_controlActorID);
                    offset += 4;
                    //头像使用
                    for (i = 0; i < s_actorPortraits.length; i++)
                    {
                        UtilShortToArray(dataThread, offset, s_resInUsed[ANIM_PORTRAIT_0 + i] ? T_TRUE : T_FALSE);
                        offset += 2;
                    }

                    UtilIntToArray(dataThread, offset, s_UIAction);
                    offset += 4;
//@#if SMS
//@                    UtilIntToArray(dataThread, offset, s_SMSAction);
//@                    offset += 4;
//@#endif
                    //UI脚本
//@#if RUN_SCRIPT_IN_IGM
                    k = s_UIScriptNum;
                    UtilIntToArray(dataThread, offset, k);
                    offset += 4;
                    for (i = 0; i < k; i++)
                    {
                        UtilShortToArray(dataThread, offset, s_UIScriptType[i]);
                        offset += 2;
                        for (j = 0; j < UI_SCRIPT_PARA; j++)
                        {
                            UtilIntToArray(dataThread, offset, s_UIScriptData[j]);
                            offset += 4;
                        }
                    }

                    UtilIntToArray(dataThread, offset, s_showTeamNumOld);
                    offset += 4;

                    UtilShortToArray(dataThread, offset, s_eventCount);
                    offset += 2;
//@#if Channel == "GF"
                    UtilIntToArray(dataThread, offset, GFUploadScore.s_score);
                    offset += 4;
//@#endif
//@#endif
                    if (offset >= RMS_SCENE_SIZE)
                    {
                        s_strErr = "过长 " + offset;
                    }

                    //写场景数据
                    rs.setRecord(sceneRecordID, dataThread, 0, RMS_SCENE_SIZE);

                    if (num < AMOUNT_MI_IGM_RMS)
                    {
                        s_rmsInfo[num][RMS_INFO_RMS]++;
                        s_rmsInfo[num][RMS_INFO_SCENEID] = s_sceneID;
                        for (i = 0; i < TEAM_SIZE; i++)
                        {
                            if ((s_actorCommon[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_INTEAM] & ACTOR_INTEAM_FLAG_STATUS) != 0)
                            {
                                s_rmsInfo[num][RMS_INFO_ALLY0 + i] = s_actorCommon[i].m_classID;
                            }
                            else
                            {
                                s_rmsInfo[num][RMS_INFO_ALLY0 + i] = -1;
                            }
                        }
                        s_rmsInfo[num][RMS_INFO_MONEY] = s_gameData[DEFINE_GAME_DATA_MONEY];
                        s_rmsInfo[num][RMS_INFO_TOKEN] = s_gameData[DEFINE_GAME_DATA_TOKEN];
                        Calendar aCalendar = Calendar.getInstance();
                        s_rmsInfo[num][RMS_INFO_HOUR] = aCalendar.get(Calendar.HOUR_OF_DAY);
                        s_rmsInfo[num][RMS_INFO_MIN] = aCalendar.get(Calendar.MINUTE);
                        s_rmsInfo[num][RMS_INFO_SECOND] = aCalendar.get(Calendar.SECOND);

                        //写公共数据
                        offset = num * AMOUNT_RMS_MESSAGE * 4;
                        for (j = 0; j < AMOUNT_RMS_MESSAGE; j++)
                        {
                            UtilIntToArray(dataCommon, offset, s_rmsInfo[num][j]);
                            offset += 4;
                        }

                        offset = AMOUNT_MI_IGM_RMS * AMOUNT_RMS_MESSAGE * 4;
//@#if SMS
//@                        for (j = 0; j <= SCRIPT_VARIABLE_SMS_NEED_ACTIVE; j++)
//@                        {
//@                            UtilIntToArray(dataCommon, offset, s_variables[SCRIPT_VARIABLE_SMS_PARA0 + j]);
//@                            offset += 4;
//@                        }
//@#endif
                        rs.setRecord(commonRecordID, dataCommon, 0, RMS_COMMON_SIZE);
                    }
                }
                else
                {
                    //LOAD
                    dataThread = rs.getRecord(sceneRecordID);
                    offset = 0;
                    if (dataThread[offset] >= 0)
                    {
                        s_sceneID = dataThread[offset];
                        offset++;
                        //是否使用
                        s_scriptNum = UtilArrayToInt(dataThread, offset);
                        offset += 4;
                        for (i = 0; i < s_scriptNum; i++)
                        {
                            s_scriptCmdPoint[i] = UtilArrayToInt(dataThread, offset);
                            offset += 4;
                            s_scriptState[i] = UtilArrayToInt(dataThread, offset);
                            offset += 4;
                            s_scriptTag0[i] = UtilArrayToInt(dataThread, offset);
                            offset += 4;
                            s_scriptTag1[i] = UtilArrayToInt(dataThread, offset);
                            offset += 4;
                            s_scriptTag2[i] = UtilArrayToInt(dataThread, offset);
                            offset += 4;
                        }
                        //计时器
                        for (i = 0; i < TIMER_POOL_SIZE; i++)
                        {
                            s_timerState[i] = (byte) UtilArrayToInt(dataThread, offset);
                            offset += 4;
                            s_timer[i] = UtilArrayToInt(dataThread, offset);
                            offset += 4;
                            s_timerAttach[i] = UtilArrayToShort(dataThread, offset);
                            offset += 2;
                            s_timerInitValue[i] = UtilArrayToInt(dataThread, offset);
                            offset += 4;
                        }

                        for (i = SCRIPT_VARIABLE_SAVE_ON; i < AMOUNT_SCRIPT_VARIABLES; i++)
                        {
                            s_variables[i] = UtilArrayToInt(dataThread, offset);
                            offset += 4;
                        }
                        int followCount = 0;
                        followCount = UtilArrayToShort(dataThread, offset);
                        offset += 4;
                        s_actorTempCount = 0; //反复读存档 主角人物消失
                        for (i = 0; i < followCount; i++)
                        {
                            short x = -99;
                            x = UtilArrayToShort(dataThread, offset);
                            offset += 2;
                            if ((int) x < 0)
                            {
                                AddFollowActor(s_actorCommon[Math.abs(x) - 1], i);
                            }
                            else
                            {
                                AddFollowActor(s_actors[Math.abs(x)], i);
                            }
                            //动画
                            s_followToTeam[i].m_actionID = UtilArrayToShort(dataThread, offset);
                            offset += 2;
                            //位置X
                            s_followToTeam[i].m_curX = UtilArrayToShort(dataThread, offset);
                            offset += 2;
                            //位置Y
                            s_followToTeam[i].m_curY = UtilArrayToShort(dataThread, offset);
                            offset += 2;
                            //目标位置X
                            s_followToTeam[i].m_destX = UtilArrayToInt(dataThread, offset);
                            offset += 4;
                            //目标位置Y
                            s_followToTeam[i].m_destY = UtilArrayToInt(dataThread, offset);
                            offset += 4;
                            s_teamMateCount = UtilArrayToInt(dataThread, offset);
                            offset += 4;
                        }
                        for (i = 0; i < TEAM_SIZE; i++)
                        {
                            //整个m_value
                            for (j = 0; j < s_actorCommon[i].m_value.length; j++)
                            {
                                s_actorCommon[i].m_value[j] = UtilArrayToInt(dataThread, offset);
                                offset += 4;
                            }
                            //装备
                            for (j = 0; j < s_actorCommon[i].m_equips.length; j++)
                            {
                                s_actorCommon[i].m_equips[j] = UtilArrayToInt(dataThread, offset);
                                offset += 4;
                            }

                            //提升属性
                            for (j = 0; j < s_actorCommon[i].m_extraValue.length; j++)
                            {
                                s_actorCommon[i].m_extraValue[j] = UtilArrayToInt(dataThread, offset);
                                offset += 4;
                            }
                            //技能
                            j = UtilArrayToInt(dataThread, offset);
                            offset += 4;
                            entity1 = s_actorCommon[i].m_entitySkill;
                            s_actorCommon[i].ReleaseAllSkill(); //解决读档 技能叠加问题
                            int id, count;
                            for (int skill = 0; skill < j; skill++)
                            {
                                id = UtilArrayToInt(dataThread, offset);
                                offset += 4;
                                count = UtilArrayToInt(dataThread, offset);
                                offset += 4;
                                s_actorCommon[i].SetSkill(id, count);
                            }

                            for (j = 0; j < s_actorCommon[i].m_properties.length; j++)
                            {
                                s_actorCommon[i].m_properties[j] = UtilArrayToShort(dataThread, offset);
                                offset += 2;
                            }
                        }
                        //actor数量
                        int t = UtilArrayToInt(dataThread, offset);
                        offset += 4;
                        for (i = 0; i < s_actors.length; i++)
                        {
                            //动画
                            s_actors[i].m_actionID = UtilArrayToShort(dataThread, offset);
                            offset += 2;
                            //位置X
                            s_actors[i].m_curX = UtilArrayToShort(dataThread, offset);
                            s_actors[i].m_destX = s_actors[i].m_curX;
                            offset += 2;
                            //位置Y
                            s_actors[i].m_curY = UtilArrayToShort(dataThread, offset);
                            s_actors[i].m_destY = s_actors[i].m_curY;
                            offset += 2;
                            for (j = 0; j < s_actors[i].m_properties.length; j++)
                            {
                                s_actors[i].m_properties[j] = UtilArrayToShort(dataThread, offset);
                                offset += 2;
                            }
                            m = UtilArrayToShort(dataThread, offset);
                            offset += 2;
                            for (j = 0; j < m; j++)
                            {
                                k = UtilArrayToShort(dataThread, offset);
                                offset += 2;
                                l = UtilArrayToShort(dataThread, offset);
                                offset += 2;
                                s_actors[i].AddChest(k, l);
                            }
                        }
                        ReleaseAllEntity(); //清空链表
                        for (i = 0; i < AMOUNT_ENTITY_LIST; i++)
                        {
                            k = UtilArrayToInt(dataThread, offset);
                            offset += 4;
                            entity1 = s_entityList[i];
                            for (j = 0; j < k; j++)
                            {
                                entity0 = CEntity.GetInstance();
                                entity0.m_id = (short) UtilArrayToInt(dataThread, offset);
                                offset += 4;
                                entity0.m_count = (byte) UtilArrayToInt(dataThread, offset);
                                offset += 4;
                                entity0.m_type = (byte) UtilArrayToShort(dataThread, offset);
                                offset += 2;
                                entity0.m_color = (byte) UtilArrayToInt(dataThread, offset);
                                offset += 4;
                                entity0.m_icon = (byte) UtilArrayToInt(dataThread, offset);
                                offset += 4;
                                entity1.InsertAfter(entity0);
                                entity1 = entity0;
                            }
                        }

                        //游戏数据
                        for (i = 0; i < DEFINE_AMOUNT_GAME_DATA; i++)
                        {
                            SetGameData(i, UtilArrayToInt(dataThread, offset));
                            offset += 4;
                        }

                        s_controlActorID = UtilArrayToInt(dataThread, offset);
                        offset += 4;
                        ControlActor(s_controlActorID);

                        //头像使用
                        for (i = 0; i < s_actorPortraits.length; i++)
                        {
                            s_resInUsed[ANIM_PORTRAIT_0 + i] = (UtilArrayToShort(dataThread, offset) == T_TRUE ? true : false);
                            offset += 2;
                        }
                        //UI动画
                        s_UIAction = UtilArrayToInt(dataThread, offset);
                        offset += 4;
//@#if SMS
//@                        s_SMSAction = UtilArrayToInt(dataThread, offset);
//@                        offset += 4;
//@#endif
//@#if RUN_SCRIPT_IN_IGM
                        //UI脚本
                        s_UIScriptNum = UtilArrayToInt(dataThread, offset);
                        offset += 4;
                        for (i = 0; i < s_UIScriptNum; i++)
                        {
                            s_UIScriptType[i] = (byte) UtilArrayToShort(dataThread, offset);
                            offset += 2;
                            for (j = 0; j < UI_SCRIPT_PARA; j++)
                            {
                                s_UIScriptData[j] = UtilArrayToInt(dataThread, offset);
                                offset += 4;
                            }
                        }
//@#endif
                        s_showTeamNumOld = UtilArrayToInt(dataThread, offset);
                        offset += 4;

                        s_eventCount = UtilArrayToShort(dataThread, offset);
                        offset += 2;
//@#if Channel == "GF"
                        GFUploadScore.s_score = UtilArrayToInt(dataThread, offset);
                        offset += 4;
//@#endif
//@#if SMS
//@                        entity0 = s_entityList[ENTITY_LIST_SMS].m_next;
//@                        while (entity0 != null)
//@                        {
//@                            entity0.m_count = s_sendTime[entity0.m_id] + 1;
//@                            entity0 = entity0.m_next;
//@                        }
//@#endif
                    }
                }
            }

            if (re != null)
            {
                re.destroy();
                re = null;
            }
            if (rs != null)
            {
                try
                {
                    rs.closeRecordStore();
                }
                catch (RecordStoreException e)
                {
                    e.printStackTrace();
                }
                rs = null;
            }
            System.gc();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            s_strErr = ex.getMessage();
            res = RMS_RETURN_STATE_ERROR;
        }
        return res;
    }

//@@
//@@..\CGame\CGame_Timer.h
    static void TimerControl(int timerID, int operator, int variableID, int initValue)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Timer.h
        switch (operator)
        {
        case DEFINE_TIMER_CONTROL_START:
            s_timer[timerID] = initValue;
            s_timerInitValue[timerID] = initValue;
            s_timerState[timerID] = TIMER_STATE_START;
            s_timerAttach[timerID] = (short) variableID;
            if (s_timerAttach[timerID] >= 0)
            {
                s_variables[s_timerAttach[timerID]] = initValue;
            }
            break;
        case DEFINE_TIMER_CONTROL_STOP:
            s_timerState[timerID] = TIMER_STATE_STOP;
            s_timerAttach[timerID] = (short) variableID;
            break;
        case DEFINE_TIMER_CONTROL_PAUSE:
            s_timerState[timerID] = TIMER_STATE_PAUSE;
            s_timerAttach[timerID] = (short) variableID;
            break;
        case DEFINE_TIMER_CONTROL_RESUME:
            s_timerState[timerID] = TIMER_STATE_START;
            s_timerAttach[timerID] = (short) variableID;
            break;
        case DEFINE_TIMER_CONTROL_RESET:
            s_timerState[timerID] = TIMER_STATE_IDLE;
            s_timer[timerID] = 0;
            s_timerAttach[timerID] = -1;
            break;
        }
    }

    static void UpdateTimer()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Timer.h
        for (int i = 0; i < TIMER_POOL_SIZE; i++)
        {
            if (s_timerState[i] == TIMER_STATE_START)
            {
                if (s_timerInitValue[i] > 0)
                {
                    s_timer[i]--;
                }
                else
                {
                    s_timer[i]++;
                }
                if (s_timerAttach[i] >= 0)
                {
                    s_variables[s_timerAttach[i]] = s_timer[i];
                }
            }
        }
    }

    static void InitTimer(int range)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Timer.h
        int start = 0, end = 0;
        switch (range)
        {
        case TIMER_RANGE_USER:
            start = 0;
            end = TIMER_POOL_USER_SIZE;
            break;
        case TIMER_RANGE_SYSTEM:
            start = TIMER_POOL_SYSTEM_START;
            end = TIMER_POOL_SYSTEM_START + TIMER_POOL_SYSTEM_SIZE;
            break;
        case TIMER_RANGE_ALL:
            start = 0;
            end = TIMER_POOL_SYSTEM_START + TIMER_POOL_SYSTEM_SIZE;
            break;
        }

        for (int i = start; i < end; i++)
        {
            s_timer[i] = 0;
            s_timerInitValue[i] = 0;
            s_timerState[i] = TIMER_STATE_IDLE;
            s_timerAttach[i] = -1;
        }
    }

    static int StartSystemTimer(int value)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Timer.h
        for (int i = 0; i < TIMER_POOL_SYSTEM_SIZE; i++)
        {
            if (s_timerState[TIMER_POOL_USER_SIZE + i] == TIMER_STATE_IDLE)
            {
                s_timerState[TIMER_POOL_USER_SIZE + i] = TIMER_STATE_START;
                s_timer[TIMER_POOL_USER_SIZE + i] = value;
                s_timerInitValue[TIMER_POOL_USER_SIZE + i] = value;
                return TIMER_POOL_USER_SIZE + i;
            }
        }
        return -1;
    }

    static void ReturnTimer(int index)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Timer.h
        s_timerState[index] = TIMER_STATE_IDLE;
    }

//@@
//@@..\CGame\CGame_Actor.h
    static int s_landingPointX = -1;
    static int s_landingPointY = -1;

    static void RecoverActor(CActor actor)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Actor.h
        actor.m_value[BV_HP] = actor.m_value[BV_HPMAX];
        actor.m_value[BV_MP] = actor.m_value[BV_MPMAX];
        actor.m_value[BV_STATE] = 0;
        for (int i = DB_EFFECT_SET_ENUMERATE_BUF_POISON; i <= AMOUNT_DB_EFFECT_SET; i++)
        {
            actor.m_buff[i] = 0;
        }
    }

    static void ActorDie(CActor actor)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Actor.h
        actor.m_value[BV_HP] = 0;
        actor.m_value[BV_MP] = 0;
        actor.m_value[BV_QUEUE] = 0;
        actor.m_value[BV_STATE] |= BAS_DEAD;
    }

    static void SetActorAction(CActor actor, int action, boolean repeat)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Actor.h
        actor.SetAction(action);
        actor.m_repeat = repeat;
        actor.m_actionIDOld = action;
    }

    static void AddObjToActor(int actorID, int type, int quantity)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Actor.h
        CActor a = null;
        a = GetActor(actorID);
        a.AddChest(type, quantity);
    }

    static void SetActorPos(int actorID, int x, int y)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Actor.h
        CActor a = null;
        a = GetActor(actorID);
        a.SetPos(x, y);
        if (actorID < 0)
        {
            for (int i = 0; i < s_actorCommon.length; i++)
            {
                s_actorCommon[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_MOVETYPE] = ACTOR_MOVE_TYPE_STAND;
            }
        }
        //判断镜头,如果是附着
        if (a.m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISCAMERA] == T_TRUE && s_cameraState == CAMERA_STATE_ACTOR)
        {
            UpdateCamera();
        }
    }

    static void AddActorTravelLine(int actorID, int dir, int len)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Actor.h
        CActor a = null;
        a = GetActor(actorID);
        a.TravelLineAddData((byte) dir, (short) len);
    }

    static void ChangeActorMoveState(int actorID, int newState, boolean start)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Actor.h
        CActor a = null;
        a = GetActor(actorID);
        a.m_properties[DEFINE_ACTOR_PROPERTY_PARA_MOVETYPE] = (short) newState;
        if (start)
        {
            a.m_travelLineEnd = false;
        }
    }

    static CActor GetActor(int id)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Actor.h
        CActor res = null;
        if (id < 0)
        {
            id = Math.abs(id) - 1;
            if (id < TEAM_SIZE)
            {
                res = s_actorCommon[id];
            }
            else
            {
                res = s_actorLastControl;
            }
        }
        else
        {
            if (id < s_actors.length)
            {
                res = s_actors[id];
            }
        }
        return res;
    }

    static int CalcTeammateID(int actorID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Actor.h
        int res = Math.abs(actorID) - 1;
        return res;
    }

    static void UpdateAllActors()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Actor.h
        CActor actor;
        int id;
        s_drawlistGroundCount = 0;
        s_drawlistFlyCount = 0;
        for (int i = 0; i < s_actors.length; i++)
        {
            actor = s_actors[i];
            if (actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] == T_TRUE && actor.m_animationID != ANIM_EVENT_ZONE &&
                actor.IsInRect(s_cameraX, s_cameraY, s_viewWidth, s_viewHeight))
            {
                AddToDrawList(actor);
            }
            actor.Update();
        }
        for (int i = 0; i < s_variables[SCRIPT_VARIABLE_TEAM_SHOW_NUM]; i++)
        {
            if (i >= FOLLOW_TO_TEAM_SIZE)
            {
                break;
            }
            if (s_followToTeam[i] != null && s_followToTeam[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_FOLLOWTEAM] < 0)
            {
                actor = s_followToTeam[i];
            }
            else
            {
                continue;
            }
            if (actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] == T_TRUE && actor.IsInRect(s_cameraX, s_cameraY, s_viewWidth, s_viewHeight))
            {
                AddToDrawList(actor);
            }
        }
        for (int i = 0; i < s_variables[SCRIPT_VARIABLE_TEAM_SHOW_NUM]; i++)
        {

            if (null != s_followToTeam[i])
            {
                s_followToTeam[i].Update();
            }
        }
        if (null != s_actorHumanControl)
        {
            if (s_landingPointX >= 0 || s_landingPointY >= 0)
            {
                MoveToLandingPoint(s_actorHumanControl);
                return;
            }
            s_actorHumanControl.UpdateControl();
        }
    }

    static void SearchLandingPoint(CActor actor)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Actor.h
        int x = actor.m_curX;
        int y = actor.m_curY;
        int dir = actor.m_actionID % 4;
        int rectX = actor.m_collideBox[RECT_X];
        int rectY = actor.m_collideBox[RECT_Y];
        int rectW = actor.m_collideBox[RECT_W];
        int rectH = actor.m_collideBox[RECT_H];
        s_landingPointX = -1;
        s_landingPointY = -1;
        for (int i = 0; i < LANDING_SMOOTH_RANGE; i++)
        {
            if (GetPhyType(x + rectX, y + rectY) < 0 &&
                GetPhyType(x + rectX + rectW, y + rectY) < 0 &&
                GetPhyType(x + rectX, y + rectY + rectH) < 0 &&
                GetPhyType(x + rectX + rectW, y + rectY + rectH) < 0)
            {
                s_landingPointX = x;
                s_landingPointY = y;
                break;
            }
            switch (dir)
            {
            case DIRECTION_UP:
                y -= TILE_SIZE;
                break;
            case DIRECTION_RIGHT:
                x += TILE_SIZE;
                break;
            case DIRECTION_DOWN:
                y += TILE_SIZE;
                break;
            case DIRECTION_LEFT:
                x -= TILE_SIZE;
                break;
            }
            if (CheckMapBoundary(x, y))
            {
                return;
            }
        }
    }

    static void MoveToLandingPoint(CActor actor)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Actor.h
        if (actor.m_curX == s_landingPointX && actor.m_curY == s_landingPointY)
        {
            CActor a;
            boolean coll = false;
            for (int i = 0; i < s_actors.length; i++)
            {
                a = s_actors[i];
                if (a.m_properties[DEFINE_ACTOR_PROPERTY_PARA_MOVETYPE] != ACTOR_MOVE_TYPE_RANGERANDOM
                    && a.m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] == T_TRUE)
                {
                    if (UtilIsRectsCollide(
                            a.m_curX + a.m_collideBox[RECT_X],
                            a.m_curY + a.m_collideBox[RECT_Y],
                            a.m_collideBox[RECT_W],
                            a.m_collideBox[RECT_H],
                            actor.m_curX + actor.m_collideBox[RECT_X],
                            actor.m_curY + actor.m_collideBox[RECT_Y],
                            actor.m_collideBox[RECT_W],
                            actor.m_collideBox[RECT_H]))
                    {
                        coll = true;
                    }
                }
            }
            if (!coll)
            {
                LandingToGround(actor);
            }
            else
            {
                s_landingPointX = -1;
                s_landingPointY = -1;
            }
            return;
        }
        /** @todo 复杂 */
        if (actor.m_curX > s_landingPointX)
        {
            actor.m_curX -= Math.min(ACTOR_STEP_LEN, actor.m_curX - s_landingPointX);
        }
        else if (actor.m_curX < s_landingPointX)
        {
            actor.m_curX += Math.min(ACTOR_STEP_LEN, s_landingPointX - actor.m_curX);
        }
        if (actor.m_curY > s_landingPointY)
        {
            actor.m_curY -= Math.min(ACTOR_STEP_LEN, actor.m_curY - s_landingPointY);
        }
        else if (actor.m_curY < s_landingPointY)
        {
            actor.m_curY += Math.min(ACTOR_STEP_LEN, s_landingPointY - actor.m_curY);
        }
    }

    static void LandingToGround(CActor actor)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Actor.h
        actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_TRANSPORT] = T_FALSE;
        actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISFLY] = T_FALSE;
        actor.m_actionID -= ACTION_TRANSPORT_UP;
        s_landingPointX = -1;
        s_landingPointY = -1;
        s_variables[SCRIPT_VARIABLE_TEAM_SHOW_NUM] = s_showTeamNumOld;
        for (int i = 0; i < s_actorCommon.length; i++)
        {
            s_actorCommon[i].SetPos(actor.m_curX, actor.m_curY);
            s_actorCommon[i].SetDestPos(actor.m_curX, actor.m_curY);
        }
    }

    static void StartToFly(CActor actor)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Actor.h
        actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_TRANSPORT] = T_TRUE;
        actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISFLY] = T_TRUE;
        if (actor.m_actionID < ACTION_MOVE_UP)
        {
            actor.m_actionID += ACTION_TRANSPORT_UP;
        }
        else if (actor.m_actionID < ACTION_TRANSPORT_UP)
        {
            actor.m_actionID += ACTION_MOVE_UP;
        }
    }

    static int IsRectCollideWithAnyActor(int x, int y, int w, int h, int dir, int distance)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Actor.h
        int res = -1;
        CActor a;
        int max = -1;

        int fixX = x, fixY = y, fixW = w, fixH = h;
        //根据矩形的移动距离,算出一条线段,穿过这条线段的矩形都是碰撞矩形

        switch (dir)
        {
        case DIRECTION_UP:
            fixY -= distance;
            fixH += distance;
            break;
        case DIRECTION_RIGHT:
            fixW += distance;
            break;
        case DIRECTION_DOWN:
            fixH += distance;
            break;
        case DIRECTION_LEFT:
            fixX -= distance;
            fixW += distance;
            break;
        }

        for (int i = 0; i < s_actors.length; i++)
        {
            for (int j = 0; j < s_followToTeam.length; j++)
            {
                if (s_followToTeam[j] != null)
                {
                    if (s_followToTeam[j].m_properties[DEFINE_ACTOR_PROPERTY_PARA_FOLLOWTEAM] == i)
                    {
                        return -1;
                    }
                }
            }
            a = s_actors[i];
            if (a.m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] == T_TRUE)
            {
                if (UtilIsRectsCollide(
                        fixX, fixY, fixW, fixH,
                        a.m_curX + a.m_collideBox[RECT_X], a.m_curY + a.m_collideBox[RECT_Y],
                        a.m_collideBox[RECT_W], a.m_collideBox[RECT_H]))
                {
                    switch (dir)
                    {
                    case DIRECTION_UP:
                        max = Math.max(max, y - (a.m_curY + a.m_collideBox[RECT_Y] + a.m_collideBox[RECT_H]));
                        break;
                    case DIRECTION_RIGHT:
                        max = Math.max(max, (a.m_curX + a.m_collideBox[RECT_X] - (x + w)));
                        break;
                    case DIRECTION_DOWN:
                        max = Math.max(max, a.m_curY + a.m_collideBox[RECT_Y] - (y + h));
                        break;
                    case DIRECTION_LEFT:
                        max = Math.max(max, x - (a.m_curX + a.m_collideBox[RECT_X] + a.m_collideBox[RECT_W]));
                        break;
                    }
                    res = max;
                }
            }
        }
        return res;
    }

    static void ApplyActorBV(int actorID, int type, int value)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Actor.h
        CActor actor = s_actorCommon[actorID];
        switch (type)
        {
        case DEFINE_ACTOR_VALUE_BV_EXP:
            actor.m_value[BV_EXP] = value;
            break;
        case DEFINE_ACTOR_VALUE_BV_HP:
            actor.m_value[BV_HP] = Math.min(value, actor.m_value[BV_HPMAX]);
            break;
        case DEFINE_ACTOR_VALUE_BV_MAXHP:
            actor.m_value[BV_HPMAX] = value;
            break;
        case DEFINE_ACTOR_VALUE_BV_MP:
            actor.m_value[BV_MP] = Math.min(value, actor.m_value[BV_MPMAX]);
            break;
        case DEFINE_ACTOR_VALUE_BV_MAXMP:
            actor.m_value[BV_MPMAX] = value;
            break;
        case DEFINE_ACTOR_VALUE_BV_PHYDMG:
            actor.m_value[BV_PHYDMG] = value;
            break;
        case DEFINE_ACTOR_VALUE_BV_PSYDMG:
            actor.m_value[BV_PSYDMG] = value;
            break;
        case DEFINE_ACTOR_VALUE_BV_DP:
            actor.m_value[BV_DP] = value;
            break;
        case DEFINE_ACTOR_VALUE_BV_CH:
            actor.m_value[BV_CH] = value;
            break;
        case DEFINE_ACTOR_VALUE_BV_DODGE:
            actor.m_value[BV_DODGE] = value;
            break;
        case DEFINE_ACTOR_VALUE_BV_LV:
            actor.m_value[BV_LV] = value - 1;
            RefreshActorBV(actorID);
            actor.m_value[BV_EXP] = actor.m_value[BV_LASTEXP];
            LearnNewSkill(actorID);
            break;
        case DEFINE_ACTOR_VALUE_BV_SPEED:
            actor.m_value[BV_SPEED] = value;
            break;
        case DEFINE_ACTOR_VALUE_BV_SPINCREASE:
            actor.m_value[BV_SPINCREASE] = value;
            break;
        case DEFINE_ACTOR_VALUE_BV_FRAME:
            actor.m_value[BV_FRAME_INFO] = value;
            break;
        }
    }

    /**
     * 绘制飞行Actor，根据Y坐标大小决定绘制先后顺序
     */
    static void RenderFlyActors(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Actor.h
        int shadowFrame = -1;
        if (s_drawlistFlyCount > 0)
        {
            CActor actor;
            QuickSort(GROUND_DRAWLIST_MAX, GROUND_DRAWLIST_MAX + s_drawlistFlyCount - 1);
            for (int i = 0; i < s_drawlistFlyCount; i++)
            {
                actor = s_actorsDrawList[GROUND_DRAWLIST_MAX + i];
                shadowFrame = actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHADOW];
                if (shadowFrame >= 0)
                {
                    s_actorsShadow.DrawFrame(g, actor.m_curX - s_cameraX, actor.m_curY - s_cameraY, FRAME_SHADOW_STANDARD + shadowFrame);
                }
                actor.DrawAnimation(g, s_cameraX, s_cameraY);
                actor.DrawEffectAnimation(g, s_cameraX, s_cameraY);
//@#if DEBUG_SHOW_SCRIPT_RECT
//@                UtilDrawRect(g, COLOR_PURPLE, actor.m_curX + actor.m_activatedBox[RECT_X], actor.m_curY + actor.m_activatedBox[RECT_Y],
//@                             actor.m_activatedBox[RECT_W], actor.m_activatedBox[RECT_H]);
//@#endif
            }
        }
    }

    /**
     * 绘制地面Actor，根据Y坐标大小决定绘制先后顺序
     */
    static void RenderGroundActors(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Actor.h
        CActor actor;
        int shadowFrame = -1;
        g.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        QuickSort(0, s_drawlistGroundCount - 1);

        for (int i = 0; i < s_drawlistGroundCount; i++)
        {
            actor = s_actorsDrawList[i];
            shadowFrame = actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHADOW];
            if (shadowFrame >= 0)
            {
                s_actorsShadow.DrawFrame(g, actor.m_curX - s_cameraX, actor.m_curY - s_cameraY, FRAME_SHADOW_STANDARD + shadowFrame);
            }
            actor.DrawAnimation(g, s_cameraX, s_cameraY);
            actor.DrawEffectAnimation(g, s_cameraX, s_cameraY);
//@#if DEBUG_SHOW_SCRIPT_RECT
//@            UtilDrawRect(g, COLOR_PURPLE, actor.m_curX + actor.m_activatedBox[RECT_X], actor.m_curY + actor.m_activatedBox[RECT_Y],
//@                         actor.m_activatedBox[RECT_W], actor.m_activatedBox[RECT_H]);
//@#endif
        }
    }

//@@
//@@..\CGame\CGame_ControlActor.h
    static int s_controlActorID = -1;
    static int s_controlLastID = -1;
    static int s_controlHeroID = 0;

    /** @todo 结构复杂 */
    static void ControlActor(int actorID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_ControlActor.h
        s_controlActorID = actorID;
        if (actorID == -999)
        {
            if (s_actorHumanControl != null)
            {
                s_actorHumanControl.m_isHumanControl = false;
                /*临时打的补丁，修正对话时踏步的bug，以后肯定要改机制*/
                if (s_actorHumanControl.m_actionID >= ACTION_MOVE_UP && s_actorHumanControl.m_actionID <= ACTION_MOVE_LEFT)
                {
                    s_actorHumanControl.m_actionID -= ACTION_DEVIATION;
                    s_actorHumanControl.m_actionIDOld = s_actorHumanControl.m_actionID;
                }
                SetFollowActorAction();
            }
            s_actorHumanControl = null;
        }
        else
        {
            CActor a = null;
            a = GetActor(actorID);
            if (null == a)
            {
                System.out.println("Err: ControlActor");
                return;
            }
            if (s_actorHumanControl != null)
            {
                s_actorHumanControl.m_isHumanControl = false;
            }
            s_actorHumanControl = a;
            a.m_isHumanControl = true;
            if ( -s_controlActorID > TEAM_SIZE)
            {
                s_controlActorID = s_controlLastID;
            }
//            else if (s_controlActorID < 0)
//            {
//                s_controlHeroID = ( -s_controlActorID) - 1;
//            }
            for (int i = 0; i < TEAM_SIZE; i++) //之前如果为轻功状态剧情后继续使用轻功状态
            {
                if (s_actorCommon[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_TRANSPORT] == T_TRUE)
                {
                    s_actorCommon[i].m_actionID = s_preLoadData[PRELOAD_DIR] + ACTION_TRANSPORT_UP;
                }
            }
            //s_controlLastID = s_controlActorID;
            s_actorLastControl = s_actorHumanControl;
        }
    }

    static void UncontrolActor()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_ControlActor.h
        s_actorHumanControl = null;
    }

//@@
//@@..\CGame\CGame_ActorDB.h
    static boolean ActorAddExp(int actorID, int exp, boolean learnSkill)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_ActorDB.h
        CActor actor = s_actorCommon[actorID];
        boolean levelUp = false;

        actor.m_value[BV_EXP] += exp;

        while (actor.m_value[BV_EXP] >= actor.m_value[BV_NEXTEXP])
        {
            if (actor.m_value[BV_LV] < s_dbGameInfo[actorID][DB_GAMEINFO_LEVEL] - 1)
            {
                actor.m_value[BV_LV]++;
                RefreshActorBV(actorID);
                levelUp = true;
            }
            else
            {
                break;
            }
        }

        if (levelUp)
        {
            actor.m_value[BV_HP] = actor.m_value[BV_HPMAX];
            actor.m_value[BV_MP] = actor.m_value[BV_MPMAX];
            if (learnSkill)
            {
                LearnNewSkill(actorID);
            }
        }

//        for (int m = 0; m < s_teamMateCount; m++) //是否有可升级技能
//        {
//            for (int n = 0; n < actor.m_entitySkill.GetCount() - 1; n++)
//            {
//                if (s_dbSkills[actor.GetSkill(1 + n)][DB_SKILLS_NEXTLVNAME] >= 0
//                    && actor.m_value[BV_LV] + 1 >= s_dbSkills[actor.GetSkill(1 + n) + 1][DB_SKILLS_LEARN_LV])
//                //技能有下一等级(NEXTLVNAME >= 0) && (人物等级 > 下一级的学习等级)
//                {
//
//                    break;
//                }
//            }
//            break;
//        }

        return levelUp;
    }

//@@
//@@..\CGame\CGame_Team.h

    /**
     * 筛选队伍成员
     *
     * @param offset int
     * @param index int
     * @param fliter int
     * @param value int
     * @return int
     */
    static int GetLAID(int offset, int index, int fliter, int value)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Team.h
        int count0 = index;
        for (int i = offset; i < TEAM_SIZE; i++)
        {
            if (s_actorCommon[i].m_properties[fliter] == value)
            {
                if (count0 == 0)
                {
                    return i;
                }
                else
                {
                    count0--;
                }
            }
        }
        return -1;
    }

    static void RefreshTeam()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Team.h
        s_teamMateCount = 0;
        s_formationMateCount = 0;
        for (int i = 0; i < TEAM_SIZE; i++)
        {
            if ((s_actorCommon[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_INTEAM] & ACTOR_INTEAM_FLAG_STATUS) != 0)
            {
                s_teamMateActorID[s_teamMateCount] = i;
                s_teamMateCount++;
            }
            if (s_actorCommon[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_INFORMATION] == T_TRUE)
            {
                s_formationMateActorID[s_formationMateCount] = i;
                s_formationMateCount++;
            }
        }
    }

    static void SetTeammate(int actorID, int isJoin)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Team.h
        s_actorCommon[actorID].m_properties[DEFINE_ACTOR_PROPERTY_PARA_INTEAM] = (short) isJoin;
    }

    static void SetFormation(int actorID, int isJoin)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Team.h
        s_actorCommon[actorID].m_properties[DEFINE_ACTOR_PROPERTY_PARA_INFORMATION] = (short) isJoin;
    }

    static byte ChangeFormation(int actorID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Team.h
        byte res = TEAM_FEEDBACK_OK;
        CActor actor = s_actorCommon[actorID];
        if (actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_INFORMATION] == T_TRUE)
        {
            if (s_formationMateCount <= 1)
            {
                res = TEAM_FEEDBACK_LESS;
            }
            else
            {
                s_actorCommon[actorID].m_properties[DEFINE_ACTOR_PROPERTY_PARA_INFORMATION] = T_FALSE;
                res = TEAM_FEEDBACK_OK;
            }
        }
        else
        {
            if ((actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_INTEAM] & ACTOR_INTEAM_FLAG_ENABLE) != 0)
            {
                return TEAM_FEEDBACK_INGURE;
            }
            if (s_formationMateCount > 2)
            {
                res = TEAM_FEEDBACK_MORE;
            }
            else
            {
                actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_INFORMATION] = T_TRUE;
                res = TEAM_FEEDBACK_OK;
            }
        }
        return res;
    }

    static void TeamRecovery(boolean isRelife)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Team.h
        CActor actor;

        for (int i = 0; i < TEAM_SIZE; i++)
        {
            actor = s_actorCommon[i];
            if (actor.m_value[BV_HP] > 0 || isRelife)
            {
                actor.m_value[BV_HP] = actor.m_value[BV_HPMAX];
                actor.m_value[BV_MP] = actor.m_value[BV_MPMAX];
                actor.m_value[BV_STATE] = 0;
            }
        }
    }

    static int FixBattleTeamPos()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Team.h
        int count = 0;
        if (s_formationMateCount > 1)
        {
            if (s_battleActorCount == 0)
            {
                count = 1;
            }
            else if (s_battleActorCount == 1)
            {
                count = 0;
            }
            else
            {
                count = s_battleActorCount;
            }
        }
        else
        {
            count = s_battleActorCount;
        }
        return count;
    }


//@@
//@@..\CGame\CGame_UI.h
    static int s_uiResult = 0;
    static int s_uiVariable = 0;
    static int s_uiType = 0;
    static int s_uiTitle = 0;
    static int s_uiContentID = 0;
    static int s_uiLine = 0;
    static int s_uiDlgHeadID = 0;
    static int s_uiDlgPosH = 0;
    static int s_uiDlgPosV = 0;
    static int s_uiExpression = 0;
    static int s_uiDlgName = 0;
    static int s_uiDlgNameWidth = 0;
    static int[] s_uiChooseItem = new int[DIALOG_CHOOSE_MAX];
    static int s_uiChooseAmount;
    static int s_uiChooseTextH;
    static int[] s_uiChooseItemH = new int[DIALOG_CHOOSE_MAX];
    static int s_uiChooseHeight;
    static int s_uiMessageX = 0;
    static int s_uiMessageY = 0;
    static int s_uiMessageW = 0;
    static int s_uiMessageH = 0;
    static int s_uiMessageInfoX = 0;
    static int s_uiMessageInfoY = 0;
    static int s_uiMessageInfoW = 0;
    static int s_uiMessageInfoH = 0;
    static int s_uiMessageTimer = 0;
    static boolean s_uiScroll = false;
    static boolean s_uiShowBG = true;

    static int[] s_uiData = new int[UI_DATA_SIZE];
    static int s_uiDataLen = 0;

    static void InitDialog(int content, int posH, int posV, int head, int brow, int name)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_UI.h
        s_uiContentID = content;
        s_uiDlgPosH = posH;
        s_uiDlgPosV = posV;
        s_uiDlgHeadID = head;
        s_uiExpression = brow;
        s_uiDlgNameWidth = GetString(name).length();
        s_uiDlgName = s_uiDlgNameWidth > 0 ? name : -1;
        s_uiDlgNameWidth = s_uiDlgNameWidth * FONT_WIDTH + (UI_DIALOG_NAME_TEXT_X << 1);
        InitUI(UI_TYPE_DIALOG, UI_DIALOG_MAX_LINE, EP_NORMAL);
    }

    static void InitBoard(int content, boolean showBG, boolean scroll, int width, int line)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_UI.h
        s_uiContentID = content;
        s_uiShowBG = showBG;
        s_uiScroll = scroll;
        s_uiMessageW = width;
        InitUI(UI_TYPE_BOARD, line, EP_NORMAL);
    }

    static void AddUIData(int value)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_UI.h
        s_uiData[s_uiDataLen] = value;
        s_uiDataLen++;
    }

    static void RenderUI(Graphics g, int type)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_UI.h
        switch (type)
        {
        case UI_TYPE_CHOOSE:
            RenderChooseDialogue(g);
            break;
        case UI_TYPE_DIALOG:
            RenderDialog(g);
            break;
        case UI_TYPE_MESSAGEBOX:
            RenderMessageBox(g);
            break;
        case UI_TYPE_BOARD:
            RenderBoard(g);
            break;
        }
    }

    static void RenderDialog(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_UI.h
        g.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        int y = 0;
        int dlgMenuBoxX = 0;
        int dlgNameX = UI_DIALOG_NAME_TEXT_X;
        switch (s_uiDlgPosV)
        {
        case DEFINE_ALIGN_BOTTOM: //靠下
            y = SCREEN_HEIGHT - UI_DIALOG_BOX_HEIGHT;
            break;
        case DEFINE_ALIGN_VCENTER: //居中
            y = SCREEN_HEIGHT_HALF - (UI_DIALOG_BOX_HEIGHT >> 1);
            break;
        case DEFINE_ALIGN_TOP: //靠上
            y = (s_uiDlgHeadID == -1 ? 0 : UI_DIALOGUE_IMG_H);
            break;
        }
        if (s_uiDlgHeadID >= 0)
        {
            //对话人物头像
            s_actorPortraits[s_uiDlgHeadID].DrawAnimation(g, 0, 0);
        }
        if (s_uiDlgName >= 0)
        {
            if (s_uiDlgHeadID >= 0 && s_uiDlgPosH == DEFINE_ALIGN_LEFT)
            {
                //人物头像在左边时 人物名称显示在右边
                dlgMenuBoxX = SCREEN_WIDTH - s_uiDlgNameWidth;
                dlgNameX = SCREEN_WIDTH - s_uiDlgNameWidth + UI_DIALOG_NAME_TEXT_X;
            }
            else
            {
                //人物头像在右边或没头像 人物名称显示在左边
                dlgMenuBoxX = 0;
                dlgNameX = UI_DIALOG_NAME_TEXT_X;
            }
            UtilDrawMenuBox(s_g, UI_DIALOG_COLOR, dlgMenuBoxX, y - UI_DIALOG_NAME_RECT_H, s_uiDlgNameWidth, UI_DIALOG_NAME_RECT_H,
                            UI_DIALOG_CONNER, UI_DIALOG_HORIZ,
                            UI_DIALOG_VERTI);
            DrawString(g, s_uiDlgName, dlgNameX, y - UI_DIALOG_NAME_RECT_H + UI_DIALOG_NAME_TEXT_Y, ALIGN_LEFT_TOP, COLOR_BLACK,
                       COLOR_BLACK);
        }
        //对话内容边框
        UtilDrawMenuBox(s_g, UI_DIALOG_COLOR, 0, y, SCREEN_WIDTH, UI_DIALOG_BOX_HEIGHT, UI_DIALOG_CONNER, UI_DIALOG_HORIZ, UI_DIALOG_VERTI);
        UtilDrawStringByWord(g, UI_DIALOG_POS_X, y + UI_DIALOG_POS_Y_OFF); //对话内容
//@#if TOUCH_DIALOG_NEXT
//@        if (s_uiDlgType == DEFINE_DIALOG_TYPE_DIALOG_NEXT)
//@        {
//@            UtilDrawString(g, STR_SYSTEM_ZERO_BUTTON, (SCREEN_WIDTH - UtilGetStringWidth(STR_SYSTEM_ZERO_BUTTON)) >> 1, SCREEN_HEIGHT - FONT_HEIGHT, ALIGN_HCENTER_TOP,
//@                           COLOR_RED, COLOR_BLACK);
//@        }
//@#endif
    }

    static void RenderChooseDialogue(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_UI.h
        int tempCounter = (int) (s_frameTick % 6);
        int color = tempCounter < 3 ? COLOR_DARK_PINK : COLOR_PURPLE;
        int posY = UI_CHOOSEDIALOG_POS_Y - (s_uiChooseHeight >> 1);
        //0键选择框 保持在对话框下方改变位置 临时添加的
        int uiTitleChooseY = 0;
        UtilDrawMenuBox(g, UI_DIALOG_COLOR, 0, posY - s_uiChooseTextH - UI_CHOOSEDIALOG_POS_H, UI_CHOOSEDIALOG_POS_W, s_uiChooseTextH + UI_CHOOSEDIALOG_POS_H,
                        MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1,
                        MODULE_UI_VERTI_1);
        UtilDrawMenuBox(g, UI_DIALOG_COLOR, 0, posY, UI_CHOOSEDIALOG_POS_W, s_uiChooseHeight - s_uiChooseTextH, MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1,
                        MODULE_UI_VERTI_1);
        uiTitleChooseY = posY + s_uiChooseHeight - s_uiChooseTextH;
        DrawTextInRectByScroll(g, s_uiTitle, UI_CHOOSEDIALOG_POS_OFF_X, posY - s_uiChooseTextH - UI_CHOOSEDIALOG_TITLE_OFFY, UI_CHOOSEDIALOG_TEXT_W,
                               s_uiChooseTextH,
                               ALIGN_HCENTER_TOP, COLOR_BLACK, COLOR_WHITE, SCROLL_DIR_UP, 0);
//        posY += (s_uiChooseTextH + UI_CHOOSEDIALOG_POS_OFF_Y);
        posY += UI_CHOOSEDIALOG_POS_OFF_Y;
        for (int i = 0; i < s_uiChooseAmount; i++)
        {
            if (s_menuSlot[MENU_SLOT_CHOOSE] == i)
            {
                DrawTextInRectByScroll(g, s_uiChooseItem[i], UI_CHOOSEDIALOG_POS_OFF_X, posY, UI_CHOOSEDIALOG_TEXT_W, s_uiChooseItemH[i],
                                       ALIGN_HCENTER_TOP, COLOR_RED, COLOR_YELLOW, SCROLL_DIR_UP, 0);
//                UtilDrawRect(g, color, UI_CHOOSEDIALOG_POS_OFF_X, posY - UI_CHOOSEDIALOG_RECT_OFFY, UI_CHOOSEDIALOG_WIDTH,
//                             s_uiChooseItemH[i] + (UI_CHOOSEDIALOG_RECT_OFFY << 1));
                s_actorUI.DrawFrame(g, UI_CHOOSEDIALOG_POS_OFF_X + 195, posY - UI_CHOOSEDIALOG_RECT_OFFY + 10, FRAME_UI_CURSOR);
            }
            else
            {
                DrawTextInRectByScroll(g, s_uiChooseItem[i], UI_CHOOSEDIALOG_POS_OFF_X, posY, UI_CHOOSEDIALOG_TEXT_W, s_uiChooseItemH[i],
                                       ALIGN_HCENTER_TOP, COLOR_HOARY, COLOR_BLACK, SCROLL_DIR_UP, 0);
            }
            posY += s_uiChooseItemH[i];
        }
        // 按"0"键选择
//@#if JAR_LIMIT_300K
//@#if not PHONE_TOUCH
//@        UtilDrawMenuBox(g, UI_DIALOG_COLOR, (SCREEN_WIDTH - UI_TITLECHOOSEBOX_W) >> 1, uiTitleChooseY + UI_TITLECHOOSE_OFFY, UI_TITLECHOOSEBOX_W,
//@                        UI_TITLECHOOSEBOX_H, MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1,
//@                        MODULE_UI_VERTI_1);
//@        DrawString(g, STR_SYSTEM_PRESS_KEY_0, SCREEN_WIDTH_HALF, uiTitleChooseY + UI_TITLECHOOSE_OFFY + UI_TITLECHOOSEBOX_OFFY, ALIGN_HCENTER_TOP, COLOR_BLACK,
//@                   COLOR_WHITE);
//@#endif
//@#else
//@#if not PHONE_TOUCH
        UtilDrawMenuBox(g, UI_DIALOG_COLOR, (SCREEN_WIDTH - UI_TITLECHOOSEBOX_W)>>1, uiTitleChooseY+UI_TITLECHOOSE_OFFY, UI_TITLECHOOSEBOX_W, UI_TITLECHOOSEBOX_H, MODULE_UI_CONNER_6, MODULE_UI_HORIZ_6,
                        MODULE_UI_VERTI_6);
        s_actorUI.DrawFrame(g, SCREEN_WIDTH_HALF, uiTitleChooseY +UI_TITLECHOOSE_OFFY+UI_TITLECHOOSEBOX_OFFY, FRAME_UI_TITLE_CHOOSE);
//@#endif
//@#endif
    }

    static void RenderMessageBox(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_UI.h
        //弹出消息框
        UtilDrawMenuBox(g, COLOR_LIGHT_GREEN, s_uiMessageX, s_uiMessageY, s_uiMessageW, s_uiMessageH, MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1);
        UtilDrawStringByWord(g, s_uiMessageInfoX, s_uiMessageInfoY + UI_MESSAGE_POS_Y_OFF);
    }

    static void RenderBoard(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_UI.h
        if (s_uiShowBG)
        {
            UtilDrawMenuBox(g, COLOR_LIGHT_BLUE, s_uiMessageX, s_uiMessageY, s_uiMessageW, s_uiMessageH, MODULE_UI_CONNER_4, MODULE_UI_HORIZ_4,
                            MODULE_UI_VERTI_4);
        }
        g.setClip(s_uiMessageInfoX - UI_BOARD_ICON_CLIP_X, s_uiMessageInfoY, s_uiMessageInfoW, s_uiMessageInfoH);
        UtilDrawStringByWord(g, s_uiMessageInfoX + UI_BOARD_ICON_CLIP_X, s_uiMessageInfoY + s_uiMessageTimer);
        if (s_uiScroll)
        {
            g.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            DrawString(g, STR_SYSTEM_QUICKEN, UI_BOARD_QUICKEN_X, UI_BOARD_TIP_Y, ALIGN_LEFT_BOTTOM, COLOR_WHITE, COLOR_BLACK);
            DrawString(g, STR_SYSTEM_SKIP, UI_BOARD_SKIP_X, UI_BOARD_TIP_Y, ALIGN_RIGHT_BOTTOM, COLOR_WHITE, COLOR_BLACK);
        }
    }

    static void RenderAnimation(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_UI.h
        UtilDrawMenuBox(g, UI_ANIMATION_COLOR, s_uiMessageX, s_uiMessageY, s_uiMessageW, s_uiMessageH, UI_ANIMATION_CONNER, UI_ANIMATION_HORIZ,
                        UI_ANIMATION_VERTI);
    }

    static void UpdateUI(int type)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_UI.h
        int returnValue = 0;
        boolean fireKeyPress = IsKeySinglePressed(GKEY_OK);
        switch (type)
        {
        case UI_TYPE_CHOOSE:
//@#if PHONE_TOUCH
//@            if (IsKeySinglePressed(GKEY_OK))
//@#else
            if (IsKeySinglePressed(GKEY_0))
//@#endif
            {
                s_variables[s_uiVariable] = s_menuSlot[MENU_SLOT_CHOOSE];
                s_uiLine = 0;
                s_uiResult = WND_STATE_YES;
            }
            else
            {
                UpdateMenuSlot(MENU_SLOT_CHOOSE, s_uiChooseAmount, GKEY_MOVEDOWN, GKEY_MOVEUP, GKEY_OK, true);
            }
            break;
        case UI_TYPE_DIALOG:
            if (s_uiDlgHeadID >= 0)
            {
                s_actorPortraits[s_uiDlgHeadID].UpdateAnimation();
            }
            returnValue = UtilUpdateStringByWord(fireKeyPress);
            if (fireKeyPress)
            {
                if (returnValue == UTIL_STATE_OK)
                {
                    s_uiResult = WND_STATE_YES;
                }
                else
                {
                    s_uiResult = WND_STATE_WAIT;
                }
            }
            break;
        case UI_TYPE_MESSAGEBOX:
            returnValue = UtilUpdateStringByWord(fireKeyPress);

            if (returnValue == UTIL_STATE_OK)
            {
                s_uiMessageTimer--;
            }

            if ((fireKeyPress && returnValue == UTIL_STATE_OK) || s_uiMessageTimer <= 0)
            {
                s_uiResult = WND_STATE_YES;
            }
            break;
        case UI_TYPE_BOARD:
            if (s_uiScroll)
            {
                int speed = IsKeyPressed(GKEY_OK) ? 4 : 1;
                s_uiMessageTimer -= speed;
                if (IsKeyPressed(GKEY_SOFTRIGHT) || s_uiMessageTimer < -(s_utilStringLine * FONT_HEIGHT))
                {
                    s_uiResult = WND_STATE_YES;
                }
            }
            else
            {
                returnValue = UtilUpdateStringByWord(fireKeyPress);
                if (fireKeyPress && returnValue == UTIL_STATE_OK)
                {
                    s_uiResult = WND_STATE_YES;
                }
            }
            break;
        default:
            break;
        }
    }

    static void InitUI(int type, int line, int nextEP)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_UI.h
        s_uiType = type;
        s_uiResult = WND_STATE_WAIT;
        switch (type)
        {
        case UI_TYPE_CHOOSE:
            s_menuSlot[MENU_SLOT_CHOOSE] = 0;
            s_uiChooseTextH = CalcTextPos(GetString(s_uiTitle), UI_CHOOSEDIALOG_TEXT_W, SCROLL_DIR_UP);
            s_uiChooseHeight = s_uiChooseTextH + (UI_CHOOSEDIALOG_POS_OFF_Y << 1);
            for (int i = 0; i < s_uiChooseAmount; i++)
            {
                s_uiChooseItemH[i] = CalcTextPos(GetString(s_uiChooseItem[i]), UI_CHOOSEDIALOG_TEXT_W, SCROLL_DIR_UP);
                s_uiChooseHeight += s_uiChooseItemH[i];
            }
            break;
        case UI_TYPE_DIALOG:
            UtilCreateCharArray(s_uiContentID, UI_DIALOG_WIDTH, line);
            InitUIPortrait();
            break;
        case UI_TYPE_MESSAGEBOX:
            UtilCreateCharArray(s_uiContentID, SCREEN_WIDTH, line);
            s_uiMessageW = s_utilStringWidth + UI_MESSAGE_W_OFF;
            s_uiMessageH = UI_MESSAGE_H;
            s_uiMessageX = SCREEN_WIDTH_HALF - (s_uiMessageW >> 1);
            s_uiMessageY = SCREEN_HEIGHT_HALF - (s_uiMessageH >> 1);
            s_uiMessageInfoX = SCREEN_WIDTH_HALF - (s_utilStringWidth >> 1);
            s_uiMessageInfoY = SCREEN_HEIGHT_HALF - (FONT_HEIGHT >> 1);
            break;
        case UI_TYPE_BOARD:
            s_uiMessageH = FONT_HEIGHT * line + UI_BOARD_H_OFF;
            s_uiMessageX = SCREEN_WIDTH_HALF - (s_uiMessageW >> 1);
            s_uiMessageY = SCREEN_HEIGHT_HALF - (s_uiMessageH >> 1);
            s_uiMessageInfoX = s_uiMessageX + UI_BOARD_INFO_X;
            s_uiMessageInfoY = s_uiMessageY + UI_BOARD_INFO_Y;
            s_uiMessageInfoW = s_uiMessageW - UI_BOARD_W_OFF;
            s_uiMessageInfoH = s_uiMessageH - UI_BOARD_H_OFF;
            s_uiMessageTimer = s_uiScroll ? s_uiMessageInfoH : 0;
            UtilCreateCharArray(s_uiContentID, s_uiMessageInfoW, line);
            if (s_uiScroll)
            {
                s_utilStringEnd = s_utilStringLen;
            }
            break;
        }
        s_EPNext = (byte) nextEP;
    }

    static void InitUIPortrait()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_UI.h
        int x = 0, y = 0;
        if (s_uiDlgHeadID >= 0)
        {
            switch (s_uiDlgPosH)
            {
            case DEFINE_ALIGN_LEFT:
                x = 0;
                s_uiExpression += AMOUNT_PORTRAIT_EXPRESSION;
                break;
            case DEFINE_ALIGN_HCENTER:
                x = SCREEN_WIDTH_HALF;
                break;
            case DEFINE_ALIGN_RIGHT:
                x = SCREEN_WIDTH;
                break;
            }
            switch (s_uiDlgPosV)
            {
            case DEFINE_ALIGN_BOTTOM:
                y = SCREEN_HEIGHT - UI_DIALOG_BOX_HEIGHT;
                break;
            case DEFINE_ALIGN_VCENTER:
                y = SCREEN_HEIGHT_HALF - (UI_DIALOG_BOX_HEIGHT >> 1);
                break;
            case DEFINE_ALIGN_TOP:
                y = SCREEN_HEIGHT;
                break;
            }
            s_actorPortraits[s_uiDlgHeadID].SetPos(x, y);
            s_actorPortraits[s_uiDlgHeadID].SetAction(s_uiExpression);
        }
    }

//@@
//@@..\CGame\CGame_AutoUI.h
    static int[] s_autoUICmds = new int[128];
    static int s_autoUICur = 0;
    static int s_autoUICmdsCount = 0;

    static void AddAutoUICmd(int cmdType, int para)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_AutoUI.h
        s_autoUICmds[s_autoUICmdsCount * 2] = cmdType;
        s_autoUICmds[s_autoUICmdsCount * 2 + 1] = para;
        s_autoUICmdsCount++;
    }

    static int UpdateAIControl(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_AutoUI.h
        s_menuConfirmID = -1;
        if (s_autoUICmdsCount > 0 && s_autoUICur < s_autoUICmdsCount && null != wnd)
        {
            CWnd.s_autoCmd = true;
            CWnd.s_autoCmdType = s_autoUICmds[s_autoUICur * 2];
            CWnd.s_autoCmdPara = s_autoUICmds[s_autoUICur * 2 + 1];
            s_autoUICur++;
            wnd.AutoCmd();
            if (CWnd.s_autoCmdConfirmed)
            {
                s_menuConfirmID = s_menuSlot[wnd.m_menuSlot];
            }
        }
        return s_menuSlot[wnd.m_menuSlot];
    }

//@@
//@@..\CGame\CGame_Util.h
    /**需要显示的字符串长度*/
    static int s_utilStringLen = 0;
    /**需要显示的字符串长度(像素)*/
    static int s_utilStringWidth = 0;
    /**字符串拆成的字符数组*/
    static char[] s_utilStringCharArray;
    /**需要显示的字符串当前位置*/
    static int s_utilStringPoint = 0;
    /**需要显示的字符串当需要分段显示时一次性最大显示的行数*/
    static int s_utilStringShowLineMax = UI_DIALOG_MAX_LINE;
    /**每行显示文字最大的长度*/
    static int s_utilStringWidthMax = SCREEN_WIDTH;
    static int s_utilStringHead = 0;
    static int s_utilStringEnd = 0;
    static int s_utilStringLine = 0;
    static int UTIL_STRING_MAX = 200;
    static boolean s_utilStringStep = false;
    /**字符串功能标记*/
    static final char UTIL_STRING_MARK = '/';
    /**字符串功能 -- 红色字*/
    static final char UTIL_STRING_MARK_COLOR_RED = 'r';
    /**字符串功能 -- 黑色字*/
    static final char UTIL_STRING_MARK_COLOR_BLACK = 'b';
    /**字符串功能 -- 白色字*/
    static final char UTIL_STRING_MARK_COLOR_WHITE = 'w';
    /**字符串功能 -- 剧情值*/
    static final String UTIL_STRING_MARK_VARIABLE = "/v";
    /**字符串功能 -- 回车*/
    static final char UTIL_STRING_MARK_ENTER = 'n';
    /**字符串功能 -- ICON*/
    static final String UTIL_STRING_MARK_ICON = "/i";
    static final char UTIL_CHAR_MARK_ICON = 'i';

    static boolean s_dialogEnter;

    /**
     * 逐字显示文字的开始的设置
     * @param strID 文字ID
     * @param width 单行最大宽度（像素）
     * @param showLineMax 分行显示时最大行数
     */
    static void UtilCreateCharArray(int strID, int width, int showLineMax)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        String str;
//@#switch (STRING_FORMAT)
//@#{
//@#case "STRING_FORMAT_CHARS":
//@        BuildString(strID);
//@        str = s_strReady;
//@#break
//@#case "STRING_FORMAT_UTF8":
//@#default:
                str = s_strings[strID];
//@#break
//@#}
//@#if SHOW_VARIABLE_IN_DLG
        str = UtilSearchVariablaInString(str);
        str = UtilSearchVariablaInIcon(str);
//@#endif
//@#if SHOW_VARIABLE_ICON
//@        str = UtilSearchVariablaInIcon(str);
//@#endif
        s_utilStringCharArray = str.toCharArray();
        s_utilStringLen = s_utilStringCharArray.length;
        s_utilStringWidthMax = width;
        s_utilStringShowLineMax = showLineMax;
        s_utilStringPoint = 0;
        s_utilStringHead = 0;
        s_utilStringEnd = 0;
        s_utilState = UTIL_STATE_SHOWING;
        s_utilStringStep = false;
        s_utilStringWidth = GetStringWidth(strID);
    }

    static String UtilSearchVariablaInString(String str)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        String variable;
        StringBuffer strbuf = new StringBuffer();
        int start = 0, offset = 0, id;
        offset = str.indexOf(UTIL_STRING_MARK_VARIABLE);
        while (offset >= 0)
        {
            strbuf.append(str.substring(start, offset));
            variable = str.substring(offset + MESSAGE_MARK_LEN, offset + MESSAGE_MARK_VARIABLE_LEN);
            id = Integer.parseInt(variable);
            strbuf.append(s_variables[id]);
            start = offset + MESSAGE_MARK_VARIABLE_LEN;
            offset = str.indexOf(UTIL_STRING_MARK_VARIABLE, start);
        }
        strbuf.append(str.substring(start));
        return strbuf.toString();
    }

    static int[] s_dialogDrawIcon = null;
    static String UtilSearchVariablaInIcon(String str)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        String variable;
        StringBuffer strbuf = new StringBuffer();
        s_dialogDrawIcon = new int[str.length()];
        int start = 0, offset = 0, id, iconCount = 0;
        offset = str.indexOf(UTIL_STRING_MARK_ICON);
        while (offset >= 0)
        {
            strbuf.append(str.substring(start, offset));
            variable = str.substring(offset + MESSAGE_ICON_LEN, offset + MESSAGE_ICON_VARIABLE_LEN);
            id = Integer.parseInt(variable);
            s_dialogDrawIcon[iconCount] = s_variables[id];
            start = offset + MESSAGE_ICON_VARIABLE_LEN + MESSAGE_VARIABLE_ICON_OFFSET;
            strbuf.append("/i");
            offset = str.indexOf(UTIL_STRING_MARK_ICON, start);
            iconCount++;
        }
        strbuf.append(str.substring(start));
        return strbuf.toString();
    }

    /**
     * 逐字显示文字的绘画方法
     * @param g
     * @param x 坐标X
     * @param y 坐标Y
     */
    static void UtilDrawStringByWord(Graphics g, int x, int y)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        if (s_utilStringCharArray == null)
        {
            return;
        }
        g.setColor(COLOR_BLACK);

        int curX = x;
        int curY = y;
        int nextFontSize = 0;
        int lineCount = 0;
        s_utilStringPoint = s_utilStringHead;
        int iconId = 0;
        do
        {
            while (s_utilStringPoint < s_utilStringEnd)
            {
                if (s_utilStringCharArray[s_utilStringPoint] == UTIL_STRING_MARK)
                {
                    s_utilStringPoint++;
                    switch (s_utilStringCharArray[s_utilStringPoint])
                    {
                    case UTIL_STRING_MARK_COLOR_BLACK:
                        g.setColor(COLOR_BLACK);
                        s_utilStringPoint++;
                        break;
                    case UTIL_STRING_MARK_COLOR_RED:
                        g.setColor(COLOR_RED);
                        s_utilStringPoint++;
                        break;
                    case UTIL_STRING_MARK_COLOR_WHITE:
                        g.setColor(COLOR_WHITE);
                        s_utilStringPoint++;
                        break;
                    case UTIL_STRING_MARK_ENTER:
                        curX = x;
                        curY += FONT_HEIGHT;
                        s_utilStringPoint++;
                        lineCount++;
                        if (lineCount == s_utilStringShowLineMax)
                        {
                            s_utilState = UTIL_STATE_OVER;
                            s_dialogEnter = true;
                        }
                        break;
                    case UTIL_CHAR_MARK_ICON:
                        RenderIcon(g, FRAME_ICON_0 + s_dialogDrawIcon[iconId], curX, curY + DIALOG_ICON_Y);
                        iconId++;
                        curX += DIALOG_ICON_W;
                        s_utilStringPoint++;
                        break;
                    }
                }
                else
                {
                    s_dialogEnter = false;
                    g.drawChar(s_utilStringCharArray[s_utilStringPoint], curX, curY, 0);
                    curX += FONT_SMALL.charWidth(s_utilStringCharArray[s_utilStringPoint]);
                    s_utilStringPoint++;
                    if (s_utilStringPoint < s_utilStringLen)
                    {
                        nextFontSize = FONT_SMALL.charWidth(s_utilStringCharArray[s_utilStringPoint]);
                    }
                    else
                    {
                        s_utilState = UTIL_STATE_OK;
                        s_utilStringPoint = s_utilStringLen;
                        s_utilStringLine = lineCount;
                        break;
                    }
                    if (curX + nextFontSize >= s_utilStringWidthMax + x)
                    {
                        curX = x;
                        curY += FONT_HEIGHT;
                        lineCount++;
                        if (lineCount == s_utilStringShowLineMax)
                        {
                            s_utilState = UTIL_STATE_OVER;
                            break;
                        }
                    }
                }
            }
            if (s_utilState == UTIL_STATE_SHOWING)
            {
                s_utilStringEnd++;
            }
            else
            {
                break;
            }
        }
        while (s_utilStringStep);

        if (s_utilStringStep)
        {
            s_utilStringStep = false;
        }
    }

    static int s_utilState = UTIL_STATE_OK;

    /**
     * 逐字显示文字的操作方法
     * @param isKey 是否有按键触发
     * @return
     */
    static int UtilUpdateStringByWord(boolean isKeyPress)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        if (isKeyPress)
        {
            if (s_utilState == UTIL_STATE_SHOWING)
            {
                s_utilStringStep = true;
            }
            else if (s_utilState == UTIL_STATE_OVER)
            {
                s_utilState = UTIL_STATE_SHOWING;
                s_utilStringHead = s_dialogEnter ? (s_utilStringEnd + 1) : s_utilStringEnd;
                s_dialogEnter = false;
            }
            else if (s_utilState == UTIL_STATE_OK)
            {
                s_utilStringCharArray = null;
                s_utilStringLen = 0;
                s_utilStringWidthMax = SCREEN_WIDTH;
                s_utilStringPoint = 0;
                s_utilStringHead = 0;
                s_utilStringEnd = 0;
                s_utilState = UTIL_STATE_SHOWING;
                s_utilStringStep = false;
                return UTIL_STATE_OK;
            }
        }
        return s_utilState;
    }

    static int UtilCeiling(int dividend, int divisor)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        int res = 0;
        int minV = dividend > 0 ? 1 : -1;
        res = dividend / divisor + (dividend % divisor == 0 ? 0 : minV);
        return res;
    }

    static int UtilCalc(int v0, int op, int v1)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        int res = v0;
        switch (op)
        {
        case DEFINE_OPERATOR_EQUALITY:
            res = (v0 == v1) ? T_TRUE : T_FALSE;
            break;
        case DEFINE_OPERATOR_NOT_EQUAL:
            res = (v0 != v1) ? T_TRUE : T_FALSE;
            break;
        case DEFINE_OPERATOR_LESS_THAN:
            res = (v0 < v1) ? T_TRUE : T_FALSE;
            break;
        case DEFINE_OPERATOR_GREATER_THAN:
            res = (v0 > v1) ? T_TRUE : T_FALSE;
            break;
        case DEFINE_OPERATOR_NOT_MORE_THAN:
            res = (v0 <= v1) ? T_TRUE : T_FALSE;
            break;
        case DEFINE_OPERATOR_NOT_LESS_THAN:
            res = (v0 >= v1) ? T_TRUE : T_FALSE;
            break;
        case DEFINE_OPERATOR_SET:
            res = v1;
            break;
        case DEFINE_OPERATOR_PLUS:
            res = v0 + v1;
            break;
        case DEFINE_OPERATOR_SUB:
            res = v0 - v1;
            break;
        case DEFINE_OPERATOR_MULTIPLY:
            res = v0 * v1;
            break;
        case DEFINE_OPERATOR_DIVIDE:
            res = v0 / v1;
            break;
        case DEFINE_OPERATOR_MOD:
            res = v0 % v1;
            break;
        }
        return res;
    }

    static void UtilIntToArray(byte[] arr, int offset, int value)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        for (int i = 0; i < 4; i++)
        {
            arr[offset + 3 - i] = (byte) ((value & 0xff));
            value >>>= 8;
        }
    }

    static void UtilShortToArray(byte[] arr, int offset, short value)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        for (int i = 0; i < 2; i++)
        {
            arr[offset + 1 - i] = (byte) ((value & 0xff));
            value >>>= 8;
        }
    }

    static int UtilCalcPercent(int base, int per)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        int res = base * per / 100;
        return res;
    }

    static int UtilPointCollideWhitPhy(int pointX, int pointY, int dir, int distance)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        int res = -1;
        int newX = 0, newY = 0;
        int tileOldX, tileOldY, tileNewX, tileNewY;
        tileOldX = pointX / TILE_SIZE;
        tileOldY = pointY / TILE_SIZE;
        switch (dir)
        {
        case DIRECTION_UP:
            res = pointY;
            newX = pointX;
            newY = pointY - distance;
            break;
        case DIRECTION_DOWN:
            res = pointY;
            newX = pointX;
            newY = pointY + distance;
            break;
        case DIRECTION_LEFT:
            res = pointX;
            newX = pointX - distance;
            newY = pointY;
            break;
        case DIRECTION_RIGHT:
            res = pointX;
            newX = pointX + distance;
            newY = pointY;
            break;
        }
        tileNewX = newX / TILE_SIZE;
        tileNewY = newY / TILE_SIZE;
        if (tileOldX == tileNewX && tileOldY == tileNewY)
        {
            //没有离开当前的tile块，可以移动到预定目的地.
            res = distance;
        }
        else
        {
            //查询新的tile能否通过
            int phyType = GetPhyType(newX, newY);
            if (phyType >= 0)
            {
                //计算最大可以达到目的地(最大距离)
                switch (dir)
                {
                case DIRECTION_UP:
                    res = pointY - (tileNewY + 1) * TILE_SIZE;
                    break;
                case DIRECTION_DOWN:
                    res = tileNewY * TILE_SIZE - 1 - pointY;
                    break;
                case DIRECTION_LEFT:
                    res = pointX - (tileNewX + 1) * TILE_SIZE;
                    break;
                case DIRECTION_RIGHT:
                    res = tileNewX * TILE_SIZE - 1 - pointX;
                    break;
                }
            }
            else
            {
                res = distance;
            }
        }
        return res;

    }

    static int UtilArrayToInt(byte[] arr, int offset)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        int res = 0;
        for (int i = 0; i < 4; i++)
        {
            res <<= 8;
            res |= (0xff & (arr[offset + i]));
        }
        return res;
    }

    static short UtilArrayToShort(byte[] arr, int offset)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        short res = 0;
        for (int i = 0; i < 2; i++)
        {
            res <<= 8;
            res |= (0xff & (arr[offset + i]));
        }
        return res;
    }

    static void UtilLongToArray(byte[] arr, int offset, long value)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        //System.out.println("写入: " + value);
        for (int i = 0; i < 8; i++)
        {
            arr[offset + 7 - i] = (byte) ((value & 0xff));
            value >>>= 8;
        }
    }

    static void UtilShortToArray(byte[] arr, int offset, int value)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        for (int i = 0; i < 2; i++)
        {
            arr[offset + 1 - i] = (byte) ((value & 0xff));
            value >>>= 8;
        }
    }

    static int UitlArrayToShort(byte[] arr, int offset)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        int res = 0;
        for (int i = 0; i < 2; i++)
        {
            res |= (arr[offset + i] << (8 * i));
        }
        return res;
    }

    static boolean UtilIsPointInRect(int px, int py, int rx, int ry, int rw, int rh)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        int dx = px - rx;
        int dy = py - ry;

        if (dx >= 0 && dx <= rw && dy >= 0 && dy <= rh)
        {
            return true;
        }
        return false;
    }

    static boolean UtilIsRectsCollide(int x0, int y0, int w0, int h0, int x1, int y1, int w1, int h1)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        boolean xCollide = false;
        boolean yCollide = false;
        if (x0 < x1)
        {
            if (x0 + w0 <= x1)
            {
                xCollide = false;
            }
            else
            {
                xCollide = true;
            }
        }
        else
        {
            if (x1 + w1 <= x0)
            {
                xCollide = false;
            }
            else
            {
                xCollide = true;
            }
        }

        if (y0 < y1)
        {
            if (y0 + h0 <= y1)
            {
                yCollide = false;
            }
            else
            {
                yCollide = true;
            }
        }
        else
        {
            if (y1 + h1 <= y0)
            {
                yCollide = false;
            }
            else
            {
                yCollide = true;
            }
        }
        return xCollide && yCollide;
    }

    static int UtilFloor(int dividend, int divisor)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        int res = 0;
        //res = dividend / divisor + (dividend % divisor != 0 ? 0 : 1);
        res = dividend / divisor;
        return res;
    }

    static void UtilDrawModuleList(Graphics g,
                                   int x, int y, int dx, int dy,
                                   int moduleID, int num, int moduleInterval,
                                   int focusID, int focusOffset, int flag)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        int j;
        UtilResetClip(g);
        CActor actor = s_actorUI;
        for (int i = 0; i < num; i++)
        {
            j = moduleID + i * moduleInterval + (i == focusID ? focusOffset : 0);
            actor.DrawModule(g, j, x, y, flag);
            x += dx;
            y += dy;
        }
    }

    static void UtilDrawFrameList(Graphics g,
                                  int x, int y, int dx, int dy,
                                  CActor actor, int frameID, int num, int frameInterval,
                                  int focusID, int focusOffset)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        int j;
        UtilResetClip(g);
        for (int i = 0; i < num; i++)
        {
            j = frameID + i * frameInterval + (i == focusID ? focusOffset : 0);
            actor.DrawFrame(g, x, y, j);
            x += dx;
            y += dy;
        }
    }

    static void UtilDrawMenuList(Graphics g, int strFirstID, int state, int len, int off, int x, int y, int dx, int dy, int focusID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        for (int i = 0; i < len; i++)
        {
            if (focusID == i)
            {

//@#if PROGRAM_FONT
//@                UtilDrawString(g, strFirstID + i, x , y, ALIGN_HCENTER_TOP, COLOR_RED, COLOR_BLACK);
//@#else
                RenderInformation(g, strFirstID + i, x, y, state);
//@#endif
            }
            else
            {
//@#if PROGRAM_FONT
//@                UtilDrawString(g, strFirstID + i + off, x , y, ALIGN_HCENTER_TOP, COLOR_BLACK, COLOR_BLACK);
//@#else
                RenderInformation(g, strFirstID + i + off, x, y, state);
//@#endif
            }
            x += dx;
            y += dy;
        }
    }

    static void UtilResetClip(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        g.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    /**
     * 画一个菜单框
     * @param g
     * @param bgColor 底色/图片
     * @param bImage 图片填充
     * @param x
     * @param y
     * @param w
     * @param h
     * @param conner 角的Module0
     * @param conner 角的Module1
     * @param conner 角的Module2
     * @param conner 角的Module3
     * @param row 行的Module
     * @param cow 列的Module
     */
    static void UtilDrawMenuBox(Graphics g, int color, int x, int y, int w, int h, int connerID, int rowID, int cowID) //圆角方框
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        int connerW = 0;
        int connerH = 0;
        int rowH = 0;
        int cowW = 0;
        if (connerID >= 0)
        {
            connerW = s_actorUI.GetModuleWidth(connerID);
            connerH = s_actorUI.GetModuleHeight(connerID);
        }
        if (rowID >= 0)
        {
            rowH = s_actorUI.GetModuleHeight(rowID);
        }
        if (cowID >= 0)
        {
            cowW = s_actorUI.GetModuleWidth(cowID);
        }
//绘制面
//@#if SHOW_PROGRAM_SIDE
//@
//@        if (rowID == -1 && cowID == -1)
//@        {
//@            UtilFillRect(g, bgColor, x, y, w, h);
//@            UtilDrawRect(g, COLOR_LINE_1, x + 1, y + 1, w - 3, h - 3);
//@            UtilDrawRect(g, COLOR_LINE_2, x + 2, y + 2, w - 5, h - 5);
//@            UtilDrawRect(g, COLOR_LINE_3, x + 3, y + 3, w - 7, h - 7);
//@        }
//@        if (rowID == -2 && cowID == -2)
//@        {
//@            UtilFillRect(g, bgColor, x, y, w, h);
//@            UtilDrawRect(g, COLOR_LINE_1, x +1, y +1, w -3 , h - 3);
//@        }
//@
//@#else
        UtilFillRect(g, color, x + cowW - 1, y + rowH - 1, w - cowW * 2 + 2, h - rowH * 2 + 2);
        //绘制边
        int posX = x + connerW;
        int posY = y;
        g.setClip(x + connerW, y, w - connerW * 2, h);
        int length = 0;
        if (rowID >= 0)
        {
            length = s_actorUI.GetModuleWidth(rowID);
            while (posX < (w + x - length))
            {
                s_actorUI.DrawModule(g, rowID, posX, posY, TRANS_NONE);
                s_actorUI.DrawModule(g, rowID, posX, posY + h - rowH, TRANS_MIRROR_ROT180);
                posX += length;
            }
            s_actorUI.DrawModule(g, rowID, w + x - length - 4, posY, TRANS_NONE);
            s_actorUI.DrawModule(g, rowID, w + x - length - 4, posY + h - rowH, TRANS_MIRROR_ROT180);
        }
        if (cowID >= 0)
        {
            posX = x;
            posY = y + connerH;
            length = s_actorUI.GetModuleHeight(cowID);
            while (posY < y + h - length)
            {
                s_actorUI.DrawModule(g, cowID, posX, posY, TRANS_NONE);
                s_actorUI.DrawModule(g, cowID, posX + w - cowW, posY, TRANS_MIRROR);
                posY += length;
            }
            s_actorUI.DrawModule(g, cowID, posX, y + h - length - 4, TRANS_NONE);
            s_actorUI.DrawModule(g, cowID, posX + w - cowW, y + h - length - 4, TRANS_MIRROR);
        }

//@#endif
        //绘制角
        if (connerID >= 0)
        {
            s_actorUI.DrawModule(g, connerID, x, y, TRANS_NONE);
            s_actorUI.DrawModule(g, connerID, x + w - connerW, y, TRANS_MIRROR);
            s_actorUI.DrawModule(g, connerID, x, y + h - connerH, TRANS_MIRROR_ROT180);
            s_actorUI.DrawModule(g, connerID, x + w - connerW, y + h - connerH, TRANS_ROT180);
        }

        g.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    /**
     * 返回一个随机数
     * @param botton
     * @param top
     * @return
     */
    static int UtilRandom(int botton, int top)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        return ((Math.abs(s_random.nextInt()) % (top - botton)) + botton);
    }

    static void UtilFillRect(Graphics g, int color, int x, int y, int w, int h)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        g.setColor(color);
        g.setClip(x, y, w, h);
        g.fillRect(x, y, w, h);
        g.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    static void UtilDrawRect(Graphics g, int color, int x, int y, int w, int h)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        g.setColor(color);
        g.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        g.drawRect(x, y, w, h);
    }

    static int UtilGetDistance(int x0, int y0, int x1, int y1)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        return (x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0);
    }

    static int UtilCalcDirection(int x0, int y0, int x1, int y1)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        if (x0 == x1 && y0 == y1)
        {
            return DIRECTION_OVERLAP;
        }
        int dy = y1 - y0;
        int dx = x1 - x0;
        if (Math.abs(dy) < Math.abs(dx))
        {
            if (dx >= 0)
            {
                return DIRECTION_RIGHT;
            }
            else
            {
                return DIRECTION_LEFT;
            }
        }
        else
        {
            if (dy >= 0)
            {
                return DIRECTION_DOWN;
            }
            else
            {
                return DIRECTION_UP;
            }
        }
    }

    /**
     * 画能够带符号的美术数字串（需要美术资源配合）
     * @param g
     * @param pbg 装有数字的资源
     * @param moudleNum0 定位数字 0 的位置
     * @param showSign 如果数字为正，是否显示“+”号
     * @param number 需要显示的数字串
     * @param x 坐标
     * @param y 坐标
     * @param align 锚点
     */
    static int UtilRenderUINumber(Graphics g, int moudleNum0, boolean showSign, int number, int x, int y, int align)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        StringBuffer numberString = new StringBuffer(Integer.toString(number));
        if (showSign && number > 0)
        {
            numberString.insert(0, "+");
        }
        char[] numberCharArray = numberString.toString().toCharArray();
        numberString = null;
        CActor actor = s_actorNumber;
        int amount = numberCharArray.length;
        int curCharValue = 0;
        int curMoudle = 0;
        int sizeWidth = 0;
        if (align == 0 || (align & Graphics.LEFT) != 0)
        {
            for (int i = 0; i < amount; i++)
            {
                curCharValue = (int) numberCharArray[i];
                if (curCharValue == 43) //定位加号
                {
                    if (showSign)
                    {
                        curMoudle = moudleNum0 - 2;
                    }
                    else
                    {
                        continue;
                    }
                }
                else if (curCharValue == 45) //定位减号
                {
                    if (showSign)
                    {
                        curMoudle = moudleNum0 - 1;
                    }
                    else
                    {
                        continue;
                    }
                }
                else
                {
                    curMoudle = curCharValue - 48 + moudleNum0;
                }
                sizeWidth = actor.GetModuleWidth(curMoudle);
                actor.DrawModule(g, curMoudle, x, y, 0);
                x += sizeWidth;
            }
        }
        else
        {
            for (int i = amount - 1; i >= 0; i--)
            {
                curCharValue = (int) numberCharArray[i];
                if (curCharValue == 43) //定位加号
                {
                    if (showSign)
                    {
                        curMoudle = moudleNum0 - 2;
                    }
                    else
                    {
                        continue;
                    }
                }
                else if (curCharValue == 45) //定位减号
                {
                    if (showSign)
                    {
                        curMoudle = moudleNum0 - 1;
                    }
                    else
                    {
                        continue;
                    }
                }
                else
                {
                    curMoudle = curCharValue - 48 + moudleNum0;
                }
                sizeWidth = actor.GetModuleWidth(curMoudle);
                actor.DrawModule(g, curMoudle, x - sizeWidth, y, 0);
                x -= sizeWidth;
            }
        }
        return amount;
    }

    static void UtilDrawSoltKeyString(Graphics g, int leftText, int rightText, int x1, int y1, int x2, int y2, int color0, int color1)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        if (leftText >= 0)
        {
            DrawString(g, leftText, x1, y1, SOFT_SELECT_ALIGN, color0, color1);
        }
        if (rightText >= 0)
        {
            DrawString(g, rightText, x2, y2, SOFT_CANCEL_ALIGN, color0, color1);
        }
    }

    static int UtilReadNumber(DataInputStream dataInputStream, int numberFlag)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        int res = 0;
        try
        {
            switch (numberFlag)
            {
            case NUMBER_BYTE:
                res = dataInputStream.readByte();
                break;
            case NUMBER_SHORT:
                res = dataInputStream.readShort();
                break;
            case NUMBER_INT:
                res = dataInputStream.readInt();
                break;
            case NUMBER_UNSIGNED_BYTE:
                res = dataInputStream.readUnsignedByte();
                break;
            default:
                res = dataInputStream.readUnsignedShort();
                break;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return res;
    }

    static byte[] UtilReadSizedBytes(DataInputStream dataInputStream, int size)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        byte[] data = new byte[size];
        try
        {
            dataInputStream.read(data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return data;
    }

    static short[] UtilReadSizedShorts(DataInputStream dataInputStream, int size)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Util.h
        short[] data = new short[size];
        try
        {
            for (int i = 0; i < size; i++)
            {
                data[i] = dataInputStream.readShort();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return data;
    }

//@@
//@@..\CGame\CGame_CharacterData.h
    /**
     * 初始化人物的信息
     */
    static void InitCharacterData()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_CharacterData.h
        CActor actor;
        for (int i = 0; i < TEAM_SIZE; i++)
        {
            actor = s_actorCommon[i];
            //初始人物等级
            actor.m_value[BV_LV] = 0;
            for (int j = 0; j < actor.m_equips.length; j++)
            {
                actor.m_equips[j] = -1;
            }
            for (int j = 0; j < DEFINE_AMOUNT_EXTRA_VALUE; j++)
            {
                actor.m_extraValue[j] = 0;
            }
            RefreshActorBV(i);
            actor.m_value[BV_EXP] = 0;
            actor.m_value[BV_HP] = actor.m_value[BV_HPMAX];
            actor.m_value[BV_MP] = actor.m_value[BV_MPMAX];
            actor.m_value[BV_SP] = 0;
            actor.m_value[BV_CAMP] = CAMP_ALLY;
            actor.m_value[BV_HEIGHT] = s_dbGameInfo[i][DB_GAMEINFO_BA_HEIGHT];
            actor.m_value[BV_FRAME_INFO] = s_heroes[i][0][DB_HEROES_FRAMEINFO];
            actor.m_value[BV_EQUIP_CHANGE] = s_dbGameInfo[i][DB_GAMEINFO_TAKE_OFF];
            LearnNewSkill(i);
        }
    }

    static int LearnNewSkill(int actorID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_CharacterData.h
        int skillID = -1;
        CActor actor = s_actorCommon[actorID];
        int lv = actor.m_value[BV_LV];

        for (int i = 0; i <= lv; i++)
        {
            skillID = s_heroes[actorID][i][DB_HEROES_SKILL];
            if (skillID >= 0)
            {
                if (!actor.CheckSkill(skillID))
                {
                    actor.SetSkill(skillID, 1);
                }
            }
        }
        return skillID;
    }

    static void RefreshActorBV(int id)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_CharacterData.h
        int i, j;
        CActor actor = s_actorCommon[id];
        int lv = s_actorCommon[id].m_value[BV_LV];
        int equipID;
        int[] add = new int[AMOUNT_ENUM_EQUIPS_PURPOSE0];

        for (j = 0; j < AMOUNT_ENUM_EQUIPS_TYPE; j++)
        {
            equipID = actor.m_equips[j];
            if (equipID >= 0)
            {
                for (i = DB_EQUIPS_PURPOSE0; i <= DB_EQUIPS_PURPOSE_LAST; i += 2)
                {
                    if (s_dbEquips[equipID][i] != ENUM_EQUIPS_PURPOSE0_NONE)
                    {
                        add[s_dbEquips[equipID][i]] += s_dbEquips[equipID][i + 1];
                    }
                }
            }
        }

        actor.m_value[BV_LASTEXP] = s_heroes[id][lv][DB_HEROES_EXP];
        actor.m_value[BV_NEXTEXP] = (lv + 1 < s_heroes[id].length ? s_heroes[id][lv + 1][DB_HEROES_EXP] : s_heroes[id][lv][DB_HEROES_EXP]);
        actor.m_value[BV_EXP] = Math.min(actor.m_value[BV_EXP], actor.m_value[BV_NEXTEXP]);
        actor.m_value[BV_HPMAX] = s_heroes[id][lv][DB_HEROES_HP] + add[ENUM_EQUIPS_PURPOSE0_HP] + actor.m_extraValue[DEFINE_EXTRA_VALUE_HP];
        actor.m_value[BV_MPMAX] = s_heroes[id][lv][DB_HEROES_MP] + add[ENUM_EQUIPS_PURPOSE0_MP] + actor.m_extraValue[DEFINE_EXTRA_VALUE_MP];
        actor.m_value[BV_SPMAX] = s_heroes[id][lv][DB_HEROES_SP] + add[ENUM_EQUIPS_PURPOSE0_SP] + actor.m_extraValue[DEFINE_EXTRA_VALUE_SP];
        actor.m_value[BV_PHYDMG] = s_heroes[id][lv][DB_HEROES_NORMAL_AP] + add[ENUM_EQUIPS_PURPOSE0_NORMAL_AP] + actor.m_extraValue[DEFINE_EXTRA_VALUE_PHY];
        actor.m_value[BV_PSYDMG] = s_heroes[id][lv][DB_HEROES_SKILL_AP] + add[ENUM_EQUIPS_PURPOSE0_SKILL_AP] + actor.m_extraValue[DEFINE_EXTRA_VALUE_PSY];
        actor.m_value[BV_DP] = s_heroes[id][lv][DB_HEROES_DEFENSE] + add[ENUM_EQUIPS_PURPOSE0_DEFENSE] + actor.m_extraValue[DEFINE_EXTRA_VALUE_DP];
        actor.m_value[BV_CH] = s_heroes[id][lv][DB_HEROES_CRITICAL] + add[ENUM_EQUIPS_PURPOSE0_CRITICAL] + actor.m_extraValue[DEFINE_EXTRA_VALUE_CH];
        actor.m_value[BV_DODGE] = s_heroes[id][lv][DB_HEROES_DODGE] + add[ENUM_EQUIPS_PURPOSE0_DODGE] + actor.m_extraValue[DEFINE_EXTRA_VALUE_DODGE];
        actor.m_value[BV_SPEED] = s_heroes[id][lv][DB_HEROES_SPEED] + add[ENUM_EQUIPS_PURPOSE0_SPEED] + actor.m_extraValue[DEFINE_EXTRA_VALUE_SPEED];
        actor.m_value[BV_TARGET_COUNT] = add[ENUM_EQUIPS_PURPOSE0_TARGET_COUNT] > 0 ? add[ENUM_EQUIPS_PURPOSE0_TARGET_COUNT]
                                         : s_dbGameInfo[id][DB_GAMEINFO_TARGET_COUNT];
        actor.m_value[BV_SPINCREASE] = s_heroes[id][lv][DB_HEROES_SP_INCREASE];
        //HP、MP不能超过上限
        actor.m_value[BV_HP] = Math.min(actor.m_value[BV_HP], actor.m_value[BV_HPMAX]);
        actor.m_value[BV_MP] = Math.min(actor.m_value[BV_MP], actor.m_value[BV_MPMAX]);
        actor.m_value[BV_SP] = Math.min(actor.m_value[BV_SP], actor.m_value[BV_SPMAX]);
    }

//@@
//@@..\CGame\CGame_Shadow.h
    static CActor s_actorsShadow = null;
    static Image s_imgShadow = null;

    static void CreateShadowRes()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Shadow.h
        if (null == s_imgShadow)
        {
            try
            {
                s_imgShadow = Image.createImage("/png" + ANIM_SHADOW);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
            s_actorsShadow = new CActor(ANIM_SHADOW, ANIM_SHADOW, -1, s_imgShadow, false);
        }
    }

    static void FreeShadowRes()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Shadow.h
        if (null != s_imgShadow)
        {
            s_imgShadow = null;
            s_actorsShadow.Free();
            s_actorsShadow = null;
        }
    }

//@@
//@@..\CGame\CGame_Chest.h
    static void CreateSceneShop(CActor actor)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Chest.h
        CEntity entity0 = s_entityList[ENTITY_LIST_BUY];
        CEntity entity1 = null;
        for (int i = 0; i < actor.m_chestAmount; i++)
        {
            entity1 = CEntity.GetInstance();
            entity1.m_type = (byte) GetArticalType(actor.m_chest[i << 1] & 0xffff);
            entity1.m_id = GetArticalID(actor.m_chest[i << 1]);
            entity1.m_count = actor.m_chest[(i << 1) + 1];
            switch (entity1.m_type)
            {
            case DEFINE_HOLD_TYPE_ITEM:
                entity1.m_icon = s_dbItems[entity1.m_id][DB_ITEMS_ICON];
                break;
            case DEFINE_HOLD_TYPE_EQUIP:
                entity1.m_icon = s_dbEquips[entity1.m_id][DB_EQUIPS_ICON];
                break;
            case DEFINE_HOLD_TYPE_MIXTRUE:
                entity1.m_icon = s_dbMixtrue[entity1.m_id][DB_MIXTRUE_ICON];
                break;
            }
            entity0.InsertAfter(entity1);
            entity0 = entity0.m_next;
        }
        OpenTopWnd(WND_SHOP, IGM_X, IGM_Y, IGM_W, IGM_H);
        s_wnd.InitData(WND_DATA_SIZE);
        s_wnd.m_data[SHOP_DATA_TYPE] = IGM_NORMAL_SHOP;
    }

    static void GetRandomItem(CActor actor)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Chest.h
        int random = 0, quantity = 0, i;
        int id, type, count;
        for (i = 0; i < actor.m_chestAmount; i++)
        {
            quantity += actor.m_chest[i * 2 + 1];
        }
        random = UtilRandom(0, quantity);
        for (i = 0; i < actor.m_chestAmount; i++)
        {
            random -= actor.m_chest[i * 2 + 1];
            if (random <= 0)
            {
                id = GetArticalID(actor.m_chest[i << 1]);
                type = GetArticalType(actor.m_chest[i << 1]);
                count = actor.m_chest[(i << 1) + 1];
                PackageModify(id, type, count);
                createRandomItemMessage(id, type, count);
                return;
            }
        }
    }

    static void createRandomItemMessage(int id, int type, int count)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Chest.h
        String str = "获得";
        switch (type)
        {
        case DEFINE_HOLD_TYPE_GOLD:
            str = str + "金钱 " + count;
            break;
        case DEFINE_HOLD_TYPE_ITEM:
            str = str + " " + GetString(STR_SYSTEM_ITEMS_NAME_0 + id) + "X" + count;
            break;
        case DEFINE_HOLD_TYPE_EQUIP:
            str = str + " " + GetString(STR_SYSTEM_EQUIPS_NAME_0 + id) + "X" + count;
            break;
        case DEFINE_HOLD_TYPE_TOKEN:
            str = str + " 海星X" + count;
            break;
        }
        s_uiMessageTimer = FRAME_LIMIT * 2;
        s_utilStringCharArray = str.toCharArray();
        s_utilStringLen = s_utilStringCharArray.length;
        s_utilStringWidthMax = UI_DIALOG_WIDTH;
        s_utilStringShowLineMax = 1;
        s_utilStringPoint = 0;
        s_utilStringHead = 0;
        s_utilStringEnd = 0;
        s_utilState = UTIL_STATE_SHOWING;
        s_utilStringStep = false;
        s_utilStringWidth = 0;
        for (int i = 0; i < s_utilStringCharArray.length; i++)
        {
            if (s_utilStringCharArray[i] == UTIL_STRING_MARK)
            {
                i++;
            }
            else
            {
                s_utilStringWidth += FONT_SMALL.charWidth(s_utilStringCharArray[i]);
            }
        }
        s_uiMessageW = s_utilStringWidth + UI_MESSAGE_W_OFF;
        s_uiMessageH = UI_MESSAGE_H;
        s_uiMessageX = SCREEN_WIDTH_HALF - (s_uiMessageW >> 1);
        s_uiMessageY = SCREEN_HEIGHT_HALF - (s_uiMessageH >> 1);
        s_uiMessageInfoX = SCREEN_WIDTH_HALF - (s_utilStringWidth >> 1);
        s_uiMessageInfoY = SCREEN_HEIGHT_HALF - (FONT_HEIGHT >> 1);
        s_uiType = UI_TYPE_MESSAGEBOX;
        SetGameEP(EP_DIALOG);
    }

//@@
//@@..\CGame\CGame_FollowToTeam.h
    static void SynchTeamPos()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_FollowToTeam.h
        int dir = s_actorHumanControl.m_actionIDOld % ACTION_DEVIATION;
        CActor actor0 = s_actorHumanControl, actor1;

        for (int i = 0; i < s_followToTeam.length; i++)
        {
            if (s_followToTeam[i] != s_actorHumanControl)
            {
//                System.out.println("s_followToTeam   " + s_followToTeam[i]);
                actor1 = s_followToTeam[i];
                if (actor1 == null)
                {

                }
                else
                {
                    switch (dir)
                    {
                    case DIRECTION_UP:
                        actor1.m_destX = actor0.m_curX;
                        actor1.m_destY = actor0.m_curY + HERO_DISTANCE_IN_TEAM;
                        break;
                    case DIRECTION_DOWN:
                        actor1.m_destX = actor0.m_curX;
                        actor1.m_destY = actor0.m_curY - HERO_DISTANCE_IN_TEAM;
                        break;
                    case DIRECTION_LEFT:
                        actor1.m_destX = actor0.m_curX + HERO_DISTANCE_IN_TEAM;
                        actor1.m_destY = actor0.m_curY;
                        break;
                    case DIRECTION_RIGHT:
                        actor1.m_destX = actor0.m_curX - HERO_DISTANCE_IN_TEAM;
                        actor1.m_destY = actor0.m_curY;
                        break;
                    }
                    actor0 = actor1;
                    int dx = actor1.m_destX - actor0.m_curX;
                    int dy = actor1.m_destY - actor0.m_curY;
                    int absX = Math.abs(dx), absY = Math.abs(dy);
                    if (absX < absY)
                    {
                        dir = dy > 0 ? DIRECTION_DOWN : DIRECTION_UP;
                        actor1.UpdateOrientedMove(dir, Math.min(ACTOR_STEP_LEN, absY), true, false);
                    }
                    else if (absX > absY)
                    {
                        dir = dx > 0 ? DIRECTION_RIGHT : DIRECTION_LEFT;
                        actor1.UpdateOrientedMove(dir, Math.min(ACTOR_STEP_LEN, absX), true, false);
                    }
                    else
                    {
                        if (actor1.m_actionID >= ACTION_MOVE_UP && actor1.m_actionID <= ACTION_MOVE_LEFT)
                        {
                            actor1.SetAction(actor1.m_actionID - ACTION_DEVIATION);
                            actor1.m_actionIDOld = actor1.m_actionID - ACTION_DEVIATION;
                            actor1.m_repeat = true;
                        }
                    }
                }
            }
        }
    }

    //  将Actor添加到指定的位置
    static int s_actorTempCount = 0;
    static void AddFollowActor(CActor actor1, int index)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_FollowToTeam.h
        CActor[] actor = new CActor[s_followToTeam.length];
        int j = 0;

        if (s_actorTempCount > s_followToTeam.length)
        {
            return;
        }
        for (int i = 0; i < s_followToTeam.length; i++)
        {
            if (s_followToTeam[i] != null)
            {
                if (s_followToTeam[i].m_classID == actor1.m_classID)
                {
                    return;
                }
            }
        }
        for (int i = 0; i < s_followToTeam.length; i++)
        {

            if (i == index)
            {
                actor[i] = actor1;
            }
            else
            {
                actor[i] = s_followToTeam[j];
                j++;
            }
        }
        s_actorTempCount++;
        s_followToTeam = actor;
        RefreshFollowTeam();
    }

    //删除指定的Actor
    static void DelFollowActor(int index)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_FollowToTeam.h
        if (s_followToTeam.length == 0)
        {
            return;
        }
        int i, j;
        int k = 0; //记录删除数据的个数
        for (i = index; i < s_followToTeam.length; i++)
        {
            for (j = 0; i + j < s_followToTeam.length - 1; j++)
            {
                s_followToTeam[i + j] = s_followToTeam[i + j + 1];
            }
            k++; //删除数据的个数加1
            s_followToTeam[s_followToTeam.length - k] = null; //清除最后一个已经前移过的数据
        }
    }

    //取得队伍中现有成员的数量
    static int GetFollowCount()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_FollowToTeam.h
        int followCount = 0;
        for (int i = 0; i < s_followToTeam.length; i++)
        {
            if (s_followToTeam[i] != null)
            {
                followCount++;
            }
        }
        return followCount;
    }

    //重新设置跟随者行动状态
    static void SetFollowActorAction()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_FollowToTeam.h
        CActor actor = null;
        for (int i = 0; i < s_followToTeam.length; i++)
        {
            if (s_followToTeam[i] == null)
            {
                continue;
            }
//            System.out.println("s_followToTeam   " + s_followToTeam[i]);
            actor = s_followToTeam[i];
            if (actor.m_actionID >= ACTION_MOVE_UP && actor.m_actionID <= ACTION_MOVE_LEFT)
            {
                actor.SetAction(actor.m_actionID - ACTION_DEVIATION);
                actor.m_actionIDOld = actor.m_actionID - ACTION_DEVIATION;
                actor.m_repeat = true;
            }
        }
    }

    //刷新队伍内人员的位置  将空位置提出放到后面
    public static void RefreshFollowTeam()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_FollowToTeam.h
        CActor[] temp = new CActor[s_followToTeam.length];
        int j = 0;
        for (int i = 0; i < s_followToTeam.length; i++)
        {
            if (s_followToTeam[i] != null)
            {
                temp[j] = s_followToTeam[i];
                j++;
            }
            else
            {
            }
        }
        s_followToTeam = temp;
    }

    //--------Loading地图时清空存在队伍中前一个场景的场景Actor--------
    static void ClearSceneActorTeam()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_FollowToTeam.h
        //清空队伍中的场景Actor
        if (s_followToTeam != null)
        {
            for (int i = 0; i < s_followToTeam.length; i++)
            {
                if (s_followToTeam[i] != null)
                {

                    short index = s_followToTeam[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_FOLLOWTEAM];
                    if ((int) index < 0)
                    {
                        continue;
                    }
                    else
                    {
                        DelFollowActor(i);
                    }
                }
            }
        }
        ///---------------end--------
    }

//@@
//@#if CHEAT_ENABLE
//@#include "..\CGame\CGame_Cheat.h"
//@#endif

//@@..\CGame\CGame_Debug.h

    /**
     * RenderDebug
     * 显示GS_GAME状态下的调试信息
     */
    static void RenderDebug()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Debug.h
        String debugInfo = "";
        debugInfo = "SCN:" + s_sceneID;
        int y = SCREEN_HEIGHT - (FONT_HEIGHT + 2);
        int w = FONT_SMALL.stringWidth(debugInfo) + 2;
        UtilFillRect(s_g, COLOR_BLACK, 0, y, w, FONT_HEIGHT + 2);
        s_g.setColor(COLOR_WHITE);
        s_g.drawString(debugInfo, 0, y, ALIGN_LEFT_TOP);
        if (DEBUG_SCRIPT_DETAIL)
        {
            for (int i = s_scriptNum - 1; i >= 0; i--)
            {
                debugInfo = "SET" + i + ":" + s_scriptCmdPoint[i];
                w = FONT_SMALL.stringWidth(debugInfo) + 2;
                y = SCREEN_HEIGHT - (s_scriptNum - i) * (FONT_HEIGHT + 2);
                UtilFillRect(s_g, COLOR_BLACK, SCREEN_WIDTH - w - 2, y, w, FONT_HEIGHT + 2);
                s_g.setColor(COLOR_WHITE);
                s_g.drawString(debugInfo, SCREEN_WIDTH - w - 2, y, ALIGN_LEFT_TOP);
            }
        }

    }

    static void RenderDebugMem()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Debug.h
        //s_memMin
        UtilFillRect(s_g, COLOR_BLACK, 0, SCREEN_HEIGHT - FONT_HEIGHT * 4, 80, FONT_HEIGHT * 4);
        s_g.setColor(COLOR_WHITE);
        long totalmem = Runtime.getRuntime().totalMemory();
        long freemem = Runtime.getRuntime().freeMemory();
        s_g.drawString("" + (s_memMin), 0, SCREEN_HEIGHT - FONT_HEIGHT * 4, ALIGN_LEFT_TOP);
        s_g.drawString("" + (totalmem - freemem), 0, SCREEN_HEIGHT - FONT_HEIGHT * 3, ALIGN_LEFT_TOP);
        s_g.drawString("" + totalmem, 0, SCREEN_HEIGHT - FONT_HEIGHT * 2, ALIGN_LEFT_TOP);

        if (freemem < 10240)
        {
            System.gc();
            freemem = Runtime.getRuntime().freeMemory();
            s_memMin = totalmem - freemem;
        }

        int fpsdDt = s_frameEstTime;
        if (fpsdDt != 0)
        {
            s_g.drawString("" + (1000 / fpsdDt), 0, SCREEN_HEIGHT - FONT_HEIGHT, ALIGN_LEFT_TOP);
        }

    }

//@@
//@@..\CGame\CGame_Basket.h
    static int[] s_basket = new int[128];
    static int s_basketCount = 0;

    static void AddItemToBasket(int itemID, int count)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Basket.h
        boolean being = false;
        for (int i = 0; i < s_basketCount; i++)
        {
            if (s_basket[i << 1] == itemID)
            {
                s_basket[(i << 1) + 1] += count;
                being = true;
                break;
            }
        }
        if (!being)
        {
            s_basket[s_basketCount << 1] = itemID;
            s_basket[(s_basketCount << 1) + 1] = count;
            s_basketCount++;
        }
    }

//@@
//@@..\CGame\CGame_LoadBattle.h
    static int[] s_battlePreLoad = new int[BATTLE_ENEMY_MAX];
    /**
     * 初始化战斗数据
     */
    static void UpdateLoadBattle()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadBattle.h
        CActor actor0 = null;
        int i, enemyID;
        switch (s_loadProgress)
        {
        case 0:
            if ((s_optionFlag & OPTION_FLAG_SOUND) != 0)
            {
                SndStop();
            }
            s_battleUIFalg = BATTLE_UI_NONE;
            s_battleActorCount = 0;
            s_battleActiveActorID = 0;
            s_actorBattleUICount = 0;
            ClearEffectValue();
            break;
        case 1:
            if (s_imgTile != null)
            {
                for (i = 0; i < s_layerCount; i++)
                {
                    s_imgTile[i] = null;
                }
            }
            FreeBufferImage();
            break;
        case 2:
            if (s_wndBattleMain == null)
            {
                s_wndBattleMain = CWnd.GetInstance();
                s_wndBattleMain.Init(WND_BATTLE_MAIN);
                s_wndBattleMain.m_show = true;
//@#if SMS
//@                CEntity entitySMS = s_entityList[ENTITY_LIST_BATTLE_SMS].SearchObject(SMS_ELIMINATE_ID);
//@                if (entitySMS != null)
//@                {
//@                    s_wndBattleMain.m_count = AMOUNT_BATTLE_MI;
//@                    s_wndBattleMain.m_showNum = AMOUNT_BATTLE_MI;
//@                }
//@                else
//@                {
//@                    s_wndBattleMain.m_count = AMOUNT_BATTLE_MI_NO_SP;
//@                    s_wndBattleMain.m_showNum = AMOUNT_BATTLE_MI_NO_SP;
//@                }
//@#endif
            }
            if (s_wndBattleAction == null)
            {
                s_wndBattleAction = CWnd.GetInstance();
                s_wndBattleAction.Init(WND_ABSTRACT);
                s_wndBattleMain.AppendChild(s_wndBattleAction);
            }
            if (s_wndBattleAim == null)
            {
                s_wndBattleAim = CWnd.GetInstance();
                s_wndBattleAim.Init(WND_BATTLE_AIM);
            }
            if (s_wndBattleDesc == null)
            {
                s_wndBattleDesc = CWnd.GetInstance();
                s_wndBattleDesc.Init(WND_BATTLE_TIP, BATTLE_TIP_X, BATTLE_TIP_Y, BATTLE_TIP_W, BATTLE_TIP_H, false);
            }

//@#if RUN_SCRIPT_IN_IGM
            s_UILimit[UI_LIMIT_CUSOR] = -1;

//@#endif
            break;
        case 3:
            for (i = 0; i < s_uiData.length; i++)
            {
                s_uiData[i] = 0;
            }
            for (i = 0; i < BATTLE_ENEMY_MAX; i++)
            {
                enemyID = s_dbEnemyTeams[s_battlePara[BATTLE_PARA_ENEMY_TEAM_ID]][DB_ENEMYTEAMS_MEMBER0 + i];
                if (enemyID >= 0)
                {
                    s_battlePreLoad[i] = ANIM_FIRST_ENEMY + s_dbEnemies[enemyID][DB_ENEMIES_ACTORNAME];
                }
                else
                {
                    s_battlePreLoad[i] = -1;
                }
                System.out.println("enemyID: " + enemyID);
            }
            s_battleCameraX = 0;
            s_battleCameraY = 0;
            s_cameraShockTimer = 0;
            s_cameraColorTimer = 0;

            //将失败标志清0
            s_variables[SCRIPT_VARIABLE_BATTLE_LOSE] = T_FALSE;
            break;
        case 4:

            //从包裹中筛取战斗物品
            FilterBattlePackage();
            break;
        case 5:
            LoadEnemyRes();
            break;
        case 6: //我方队伍
            InitBattleHero();
            break;
        case 7:
            LoadHeroRes();

            for (i = 0; i < s_formationMateCount; i++)
            {
                actor0 = s_actorInBattle[i];
                actor0.SetImage(s_imgs[actor0.m_animationID]);
                if ((actor0.m_value[BV_STATE] & BAS_DEAD) != 0)
                {
                    actor0.SetAction(ACTION_BATTLE_DIE);
                }
                else if ((actor0.m_value[BV_HP] < actor0.m_value[BV_HPMAX] / BATTLE_WEAK))
                {
                    actor0.SetAction(ACTION_BATTLE_WEAK);
                }
                else
                {
                    actor0.SetAction(ACTION_BATTLE_WAIT);
                }
                actor0.SetPos(actor0.m_anchorX, actor0.m_anchorY);
                actor0.m_repeat = true;
            }
            break;
        case 8: //敌人
            InitBattleEnemy();
            break;
        case 9:

            //特效
            for (i = 0; i < BATTLE_ACTOR_MAX; i++)
            {
                s_actorEffInBattle[i].m_end = true;
                s_actorEffInBattle[i].m_repeat = false;
                s_actorEffInBattle[i].SetPos(s_actorInBattle[i].m_anchorX, s_actorInBattle[i].m_anchorY);
            }
            s_actorEffFullScreen.m_end = true;
            s_actorEffFullScreen.m_repeat = false;
            s_actorEffFullScreen.SetPos(SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF);
            break;
        case 10:
            try
            {
                s_imgBattlePortrait = Image.createImage("/png" + ANIM_BATTLE_PORTRAIT);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
            s_actorBattlePortrait = new CActor(ANIM_BATTLE_PORTRAIT, ANIM_BATTLE_PORTRAIT, -1, s_imgBattlePortrait, false);
            break;
        case 11:
            LoadBattleBG();

//@#if SHOW_DEBUG_INFO
            System.out.println("载入战斗背景资源");

//@#endif
            break;
        case 12:

            //计算渲染的顺序
            s_drawlistGroundCount = 0;
            s_drawlistFlyCount = 0;
            for (i = 0; i < s_battleActorCount; i++)
            {
                AddToDrawList(s_actorInBattle[i]);
            }

            //根据速度排行动的顺序
            CalcBattleQueue();
            break;
        case 13:
            s_battleRound = -1;
            GetFirstBattleActiveActor();
            s_wndBattleMain.m_show = false;
            SetRoundState(RS_ROUNDCOUNT);
            SetGameEP(EP_BATTLE);
            if ((s_optionFlag & OPTION_FLAG_SOUND) != 0)
            {
                SndPlay(s_soundSlot[DEFINE_MUSIC_SLOT_BATTLE]);
            }
            else
            {
                SndStop();
            }
            break;
        }
        s_loadProgress++;
    }

    static void FilterBattlePackage()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadBattle.h
        CEntity entityBattle = s_entityList[ENTITY_LIST_BATTLE];
        CEntity entityPackage = s_entityList[ENTITY_LIST_ITEM].m_next;
        CEntity entityCur = entityPackage;
        while (null != entityCur)
        {
            if (s_dbItems[entityCur.m_id][DB_ITEMS_WHENCANUSE] == ENUM_ITEMS_WHENCANUSE_FIGHT
                || s_dbItems[entityCur.m_id][DB_ITEMS_WHENCANUSE] == ENUM_ITEMS_WHENCANUSE_ALL)
            {
                entityPackage = entityPackage.m_next;
                entityCur.RemoveSelf();
                entityCur.m_prev = null;
                entityCur.m_next = null;
                entityBattle.InsertAfter(entityCur);
                entityBattle = entityBattle.m_next;
                entityCur = entityPackage;
            }
            else
            {
                entityPackage = entityPackage.m_next;
                entityCur = entityPackage;
            }
        }
    }

    static void LoadEnemyRes()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadBattle.h
        boolean used;
        for (int i = ANIM_FIRST_ENEMY; i <= ANIM_LAST_ENEMY; i++)
        {
            used = false;
            for (int j = 0; j < BATTLE_ENEMY_MAX; j++)
            {
                if (s_battlePreLoad[j] == i)
                {
                    used = true;
                    break;
                }
            }
            if (used)
            {
                if (null == s_imgs[i])
                {
                    try
                    {
                        s_imgs[i] = Image.createImage("/png" + i);
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    static void LoadHeroRes()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadBattle.h
        int id = 0;
        CActor actor;

        for (int i = 0; i < TEAM_SIZE; i++)
        {
            actor = s_actorCommon[i];
            id = ANIM_FIRST_BA + actor.m_animationID - ANIM_FIRST_LA;
            if (actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_INFORMATION] == T_TRUE && s_imgs[id] == null)
            {
                try
                {
                    s_imgs[id] = Image.createImage("/png" + id);
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        LoadHeroExRes();
    }

    static void LoadHeroExRes()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadBattle.h
        try
        {
            s_imgs[ANIM_FIRST_BA + TEAM_SIZE] = Image.createImage("/png" + (ANIM_FIRST_BA + TEAM_SIZE));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    static void FreeHeroRes()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadBattle.h
        for (int i = ANIM_FIRST_BA; i <= ANIM_FIRST_BA + TEAM_SIZE; i++)
        {
            if (!s_resInUsed[i])
            {
                s_imgs[i] = null;
            }
        }
    }

    static void InitBattleHero()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadBattle.h
        int count = 0;
        CActor actorTeammate, actor0;

        for (int i = 0; i < TEAM_SIZE; i++)
        {
            actorTeammate = s_actorCommon[i];
            if (actorTeammate.m_properties[DEFINE_ACTOR_PROPERTY_PARA_INFORMATION] != T_TRUE)
            {
                continue;
            }
            count = FixBattleTeamPos();
            actor0 = s_actorInBattle[count];
            actor0.m_animationID = ANIM_FIRST_BA - ANIM_FIRST_LA + actorTeammate.m_animationID;
            actor0.m_classID = actor0.m_animationID;
            actor0.m_anchorX = BATTLE_HERO_POS_X[s_formationMateCount > 1 ? count : 1];
            actor0.m_anchorY = BATTLE_HERO_POS_Y[s_formationMateCount > 1 ? count : 1];
            //战斗克隆,将数据复制到战斗中的actor
            actor0.BattleClone(actorTeammate);
            actor0.m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISFLY] = T_FALSE;
            for (int j = 0; j < AMOUNT_DB_EFFECT_SET; j++)
            {
                actor0.m_buff[j] = 0;
            }
            if (actor0.m_value[BV_HP] <= (short) 0)
            {
                actor0.m_value[BV_STATE] |= BAS_DEAD;
            }
            else
            {
                actor0.m_value[BV_STATE] = BAS_NORMAL;
            }
            actor0.m_value[BV_ID] = i;
            actor0.m_entitySkill.m_next = actorTeammate.m_entitySkill.m_next;
            actor0.m_entitySkillEx.m_next = actorTeammate.m_entitySkillEx.m_next;
            actor0.m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] = T_TRUE;
            actor0.m_value[BV_ACTION] = BATTLE_ACTION_TYPE_NORMAL;
            actor0.InitCoolDown();
            s_battleActorCount++;
            if (s_battleActorCount >= FORMATION_SIZE)
            {
                break;
            }
        }
    }

    static void InitBattleEnemy()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadBattle.h
        int enemyID;
        CActor actor;

        for (int i = 0; i < BATTLE_ENEMY_MAX; i++)
        {
            enemyID = s_dbEnemyTeams[s_battlePara[BATTLE_PARA_ENEMY_TEAM_ID]][DB_ENEMYTEAMS_MEMBER0 + i];
            actor = s_actorInBattle[s_battleActorCount];
            for (int j = 0; j < DEFINE_AMOUNT_ACTOR_PROPERTY; j++)
            {
                actor.m_properties[j] = 0;
            }
            if (enemyID >= 0)
            {
                actor.m_classID = ANIM_FIRST_ENEMY + s_dbEnemies[enemyID][DB_ENEMIES_ACTORNAME];
                actor.m_animationID = actor.m_classID;
                actor.SetImage(s_imgs[ANIM_FIRST_ENEMY + s_dbEnemies[enemyID][DB_ENEMIES_ACTORNAME]]);
                actor.SetAction(ACTION_BATTLE_WAIT);
                actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] = T_TRUE;
                actor.m_anchorX = BATTLE_ENEMY_POS_X[i];
                actor.m_anchorY = BATTLE_ENEMY_POS_Y[i];
                actor.SetPos(actor.m_anchorX, actor.m_anchorY);
                actor.m_repeat = true;
                actor.m_value = new int[AMOUNT_BV];
                actor.m_value[BV_HP] = s_dbEnemies[enemyID][DB_ENEMIES_HP];
                actor.m_value[BV_HPMAX] = s_dbEnemies[enemyID][DB_ENEMIES_HP];
                actor.m_value[BV_MP] = s_dbEnemies[enemyID][DB_ENEMIES_MP];
                actor.m_value[BV_MPMAX] = s_dbEnemies[enemyID][DB_ENEMIES_MP];
                actor.m_value[BV_SPMAX] = 0;
                actor.m_value[BV_SP] = 0;
                actor.m_value[BV_PHYDMG] = s_dbEnemies[enemyID][DB_ENEMIES_NORMAL_AP];
                actor.m_value[BV_PSYDMG] = s_dbEnemies[enemyID][DB_ENEMIES_SKILL_AP];
                actor.m_value[BV_DP] = s_dbEnemies[enemyID][DB_ENEMIES_DEFENSE];
                actor.m_value[BV_CH] = s_dbEnemies[enemyID][DB_ENEMIES_CRITICAL];
                actor.m_value[BV_DODGE] = s_dbEnemies[enemyID][DB_ENEMIES_DODGE];
                actor.m_value[BV_SPEED] = s_dbEnemies[enemyID][DB_ENEMIES_SPEED];
                actor.m_value[BV_AVOID_BUFF] = s_dbEnemies[enemyID][DB_ENEMIES_AVOIDBUFF];
                actor.m_value[BV_TARGET_COUNT] = 1;
                //技能
                for (int k = DB_ENEMIES_SKILL1; k <= DB_ENEMIES_SKILL3; k += 2)
                {
                    if (s_dbEnemies[enemyID][k] >= 0)
                    {
                        actor.SetSkill(s_dbEnemies[enemyID][k], 1);
                    }
                }
                actor.m_value[BV_CAMP] = CAMP_FOE;
                actor.m_value[BV_STATE] = BAS_NORMAL;
                actor.m_value[BV_ID] = enemyID;
                actor.m_value[BV_HEIGHT] = s_dbEnemies[enemyID][DB_ENEMIES_HEIGHT];
                actor.m_value[BV_ACTION] = BATTLE_ACTION_TYPE_NORMAL;
                actor.m_value[BV_FRAME_INFO] = s_dbEnemies[enemyID][DB_ENEMIES_FRAMEINFO];
                actor.InitCoolDown();
                s_battleActorCount++;
            }
        }
    }

    static void LoadBattleBG()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadBattle.h
        if (s_battleBGID >= 0)
        {
            if (s_imgBattleBG == null)
            {
//@#if USE_PAK_RESOURCES
                OpenTrunk("/battle.pak");
                byte[] tbuf = ReadTrunk(s_battleBGID);
                s_imgBattleBG = Image.createImage(tbuf, 0, tbuf.length);
                CloseTrunk();
//@#else
//@            try
//@            {
//@                s_imgBattleBG = Image.createImage("/BATTLE_BG_" + s_battleBGID);
//@            }
//@            catch (IOException e)
//@            {
//@                e.printStackTrace();
//@            }
//@#endif
                s_actorBattleBG = new CActor(ANIM_BATTLE_BG0 + s_battleBGID, ANIM_BATTLE_BG0 + s_battleBGID, 0, s_imgBattleBG, true);
            }
        }
    }

    static void FreeBattleBG()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadBattle.h
        if (s_imgBattleBG != null)
        {
            s_imgBattleBG = null;
            s_actorBattleBG.Free();
            s_actorBattleBG = null;
        }
    }

//@@
//@#switch (BATTLE_QUEUE)
//@#{
//@#case "ROTATION":
//@#include "..\CGame\CGame_RotationQueue.h"
//@#break
//@#case "COMPET":
//@@..\CGame\CGame_CompetQueue.h
    /**行动方*/
    static int s_battleActiveActorID = 0;
    static CActor s_actorBattleActivated = null;
    static int s_battleQueueGoal;
    /**可以行动的actor序列*/
    static byte[] s_battleSequence = new byte[BATTLE_ACTOR_MAX];
    static int s_battleActiveCount = 0;

    static void BattleActiveNext()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_CompetQueue.h
        int actorID;
        CActor actor;
        //遍历所有达到行动条件的人，按顺序逐个行动
        for (int i = 0; i < s_battleActiveCount; i++)
        {
            actorID = s_battleSequence[i];
            if (actorID >= 0)
            {
                s_battleRound++;
                s_battleSequence[i] = -1;
                if (s_actorInBattle[actorID].m_value[BV_QUEUE] >= s_battleQueueGoal)
                { //判断此角色是否真的可以行动(actor加入序列后，可能会被打死或中其他的buff)
                    s_battleActiveActorID = actorID;
                    s_actorBattleActivated = s_actorInBattle[s_battleActiveActorID];
                    SetRoundState(RS_INIT);
                    return;
                }
            }
        }

        s_battleActiveCount = 0;

        for (int i = 0; i < s_battleActorCount; i++)
        {
            actor = s_actorInBattle[i];
            if ((actor.m_value[BV_STATE] & BAS_DEAD) == 0)
            { //累计行动点数
                actor.m_value[BV_QUEUE] += (actor.m_value[BV_SPEED] + actor.m_buff[DB_EFFECT_SET_ENUMERATE_SPEED]);
                if (actor.m_value[BV_QUEUE] >= s_battleQueueGoal)
                { //点数达到行动条件，则加入到行动队列中
                    InsertActiveToSequence(i);
                    actor.m_value[BV_QUEUE] = s_battleQueueGoal;
                }
            }
        }
    }

    static void CalcBattleQueue()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_CompetQueue.h
        CActor actor;
        s_battleActiveCount = 0;
        s_battleQueueGoal = 0;
        for (int i = 0; i < BATTLE_ACTOR_MAX; i++)
        {
            s_battleSequence[i] = -1;
        }
        for (int i = 0; i < s_battleActorCount; i++)
        {
            actor = s_actorInBattle[i];
            actor.m_value[BV_QUEUE] = 0;
            s_battleQueueGoal = Math.max(actor.m_value[BV_SPEED], s_battleQueueGoal);
        }
        s_battleQueueGoal *= (FRAME_LIMIT - FRAME_LIMIT_NUM);
    }

    static void GetFirstBattleActiveActor()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_CompetQueue.h
        s_battleActiveActorID = -1;
    }

    static void InsertActiveToSequence(int id)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_CompetQueue.h
        CActor actor = s_actorInBattle[id];
        for (int i = 0; i < s_battleActiveCount; i++)
        {
            if (actor.m_value[BV_QUEUE] > s_actorInBattle[s_battleSequence[i]].m_value[BV_QUEUE])
            {
                s_battleSequence[i + 1] = s_battleSequence[i];
                s_battleSequence[i] = (byte) id;
                s_battleActiveCount++;
                return;
            }
        }
        s_battleSequence[s_battleActiveCount] = (byte) id;
        s_battleActiveCount++;
    }

    static void RenderBattleQueue(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_CompetQueue.h
        if ((s_battleUIFalg & BATTLE_UI_QUEUE) != 0)
        {
            int x, i, j = 0;
            CActor actor;
//@#if FILL_RECT_BATTLE_BG
//@            UtilFillRect(g, COLOR_EARTH, BU_QUEUE_RECT_X, BU_QUEUE_RECT_Y, BU_QUEUE_RECT_W, BU_QUEUE_RECT_H);
//@            UtilFillRect(g, COLOR_UI_EDGE, BU_QUEUE_RECT_X, BU_QUEUE_RECT_H, BU_QUEUE_RECT_W, 2);
//@#else
            s_actorUI.DrawFrame(g, BU_QUEUE_RECT_X, BU_QUEUE_RECT_Y, FRAME_UI_QUEUE_BAR);
            s_actorUI.DrawFrame(g, BU_QUEUE_GOAL_X, BU_QUEUE_RECT_Y, FRAME_UI_QUEUE_GOAL);
//@#endif
            //等待队列
            for (i = 0; i < s_battleActorCount; i++)
            {
                actor = s_actorInBattle[i];

                if ((actor.m_value[BV_STATE] & BAS_DEAD) == 0 && s_battleActiveActorID != i)
                {
                    x = BU_QUEUE_X + (actor.m_value[BV_QUEUE] * BU_QUEUE_W / s_battleQueueGoal);
//@#if not FILL_RECT_BATTLE_BG
                   s_actorUI.DrawFrame(g, x, BU_QUEUE_Y, actor.m_value[BV_CAMP] == CAMP_ALLY ? FRAME_UI_QUEUE_ALLY : FRAME_UI_QUEUE_FOE);
//@#endif
                    actor.DrawFrame(g, x, BU_QUEUE_Y, 0);
                }
            }

            if (s_battleActiveActorID >= 0)
            {
                //行动者icon
                x = BU_QUEUE_X + (s_actorBattleActivated.m_value[BV_QUEUE] * BU_QUEUE_W / s_battleQueueGoal);
                actor = s_actorInBattle[s_battleActiveActorID];
//@#if not FILL_RECT_BATTLE_BG
                s_actorUI.DrawFrame(g, x, BU_QUEUE_Y, actor.m_value[BV_CAMP] == CAMP_ALLY ? FRAME_UI_QUEUE_ALLY : FRAME_UI_QUEUE_FOE);
//@#endif
                s_actorBattleActivated.DrawFrame(g, x, BU_QUEUE_Y, 0);
            }
        }
    }

    static void StartNextRound()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_CompetQueue.h
        s_actorBattleActivated.m_value[BV_QUEUE] -= s_battleQueueGoal;
        s_battleActiveActorID = -1;
        s_actorBattleActivated = null;
        s_wndBattleMain.m_show = false;
        SetRoundState(RS_ROUNDCOUNT);
    }

    static void UpdateBattleQueueIcon()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_CompetQueue.h
        SetRoundState(RS_READY);
    }

//@@
//@#break
//@#}
//@@..\CGame\CGame_Fight.h

    static int[] s_battlePara = null;
    /**战斗子状态*/
    static int s_rs = RS_INIT;
    static int s_rsOld = -1;
    static int s_rsNext = -1;

    /**战斗人员*/
    static CActor[] s_actorInBattle = null;
    /**战斗光效*/
    static CActor[] s_actorEffInBattle = null;
    static CActor s_actorEffFullScreen = null;
    /**目标范围*/
    static int s_battleAimSet = 0;

    /**战斗中对象的数量*/
    static int s_battleActorCount;

    /**选择目标站位*/
    static int s_battleAimID = 0;

    /**战斗背景图ID*/
    static int s_battleBGID = 0;

    /**最后一次的战斗结果*/
    static int s_battleResult = 0;

    /** @todo  处理的不好*/
    static boolean s_battleStart = false;

    static int s_battleRound = 0;

    /**
     * 开始战斗
     * @param team 敌方队伍
     * @param escape 可否逃跑
     * @param goon 失败后继续
     */
    static void StartBattle(int team, int escape, int goon)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        if (DEBUG_SKIP_BATTLE)
        {
            s_battleStart = false;
            return;
        }
        s_battlePara[BATTLE_PARA_ENEMY_TEAM_ID] = team;
        s_battlePara[BATTLE_PARA_CAN_ESCAPE] = escape;
        s_battlePara[BATTLE_PARA_LOSE_CONTINUE] = goon;
        s_battlePara[BATTLE_PARA_SHOW_UI] = T_TRUE;
        SetGameEP(EP_LOADBATTLE);
    }

    static void SetRoundState(int newRoundState)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        if (DEBUG_BATTLE_INFO)
        {
            System.out.println("New Round State: " + newRoundState);
        }
        s_rsOld = s_rs;
        s_rs = newRoundState;
    }

    static void SetBattleActorPos(int attackerID, int feedbackID, int count, int rushType)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        int x = 0, y = 0;
        CActor actorAttack = s_actorInBattle[attackerID];
        CActor actorFeedback;
        count = count > 0 ? count : BATTLE_ENEMY_MAX;

        switch (rushType)
        {
        case BATTLE_RUSH_TYPE_STAND:
            x = actorAttack.m_anchorX;
            y = actorAttack.m_anchorY;
            break;
        case BATTLE_RUSH_TYPE_NEAR:
            actorFeedback = s_actorInBattle[feedbackID];
            x = actorFeedback.m_anchorX + (actorAttack.m_value[BV_CAMP] == CAMP_ALLY ? BATTLE_ACTOR_OFF_X : -BATTLE_ACTOR_OFF_X);
            y = actorFeedback.m_anchorY;
            break;
        case BATTLE_RUSH_TYPE_CENTER:
            for (int i = 0; i < count; i++)
            {
                actorFeedback = s_actorInBattle[feedbackID];
                y += actorFeedback.m_anchorY;
                feedbackID = GetNextAimedID(feedbackID, 1, true);
            }
            y /= count;
            x = SCREEN_WIDTH_HALF;
            break;
        }
        actorAttack.SetPos(x, y + 1);
    }

    static void UpdateBattle()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        s_rsEffectEnd = false;
        UpdateRSScreen();
        s_rsEffectEnd = UpdateRSEffect();
        s_battleAnimFalg = BATTLE_ANIM_ALL;

        switch (s_rs)
        {
        case RS_ROUNDCOUNT:
            BattleActiveNext();
            s_battleUIFalg = BATTLE_UI_ALL;
            break;
        case RS_INIT:
            if (CheckBattleScript())
            {
            }
            else
            {
                UpdateRSInit();
            }
            s_battleUIFalg = BATTLE_UI_ALL;
            break;
        case RS_READY:
            UpdateRSReady();
            s_battleUIFalg = BATTLE_UI_ALL;
            break;
        case RS_UI:
            CheckBattleScript();
            UpdateRSUI();
            break;
        case RS_AIM:
            UpdateRSAim(s_wndBattleAim);
            break;
        case RS_LOOT:
            UpdateRSLoot();
            s_battleAnimFalg = BATTLE_ANIM_BG | BATTLE_ANIM_ACTOR | BATTLE_ANIM_INFO;
            break;
        case RS_ESCAPE:
            UpdateRSEscape();
            break;
        case RS_PARALLEL:
            UpdateRSParallel();
            break;
        case RS_ROUNDEND:
            UpdateRSRoundEnd();
            break;
        case RS_PARALLEL_SHOW:
            UpdateRSParallelShow();
            break;
//@#if SMS
//@        case RS_ALL_DIE:
//@            UpdateRSRelife();
//@            break;
//@        case RS_KILL_ALL:
//@            UpdateRSKillAll();
//@            break;
//@#endif
        }
        UpdateBattleCamera();
        CWnd.s_autoCmdConfirmed = false;
        if (s_needRefreshBG)
        {
            s_needRefreshBG = false;
            s_battleAnimFalg |= (BATTLE_ANIM_BG | BATTLE_ANIM_ACTOR | BATTLE_ANIM_INFO);
        }
    }

    static void UpdateBattleCamera()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        if (s_cameraColorTimer > 0)
        {
            s_cameraColorTimer--;
            s_battleAnimFalg &= ~BATTLE_ANIM_BG;
        }
        if (s_cameraShockTimer > 0)
        {
            s_cameraShockTimer--;
            if (s_battleCameraX <= 0 || s_battleCameraX >= BATTLE_CAMERA_SHOCK_MAX)
            {
                s_cameraVX *= -1;
            }
            if (s_battleCameraY <= 0 || s_battleCameraY >= BATTLE_CAMERA_SHOCK_MAX)
            {
                s_cameraVY *= -1;
            }
            s_battleCameraX += s_cameraVX;
            s_battleCameraY += s_cameraVY;
        }
        else
        {
            s_battleCameraX = 0;
            s_battleCameraY = 0;
        }
    }

    static void ShockBattleCamera(int time, short vx, short vy)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        if (s_cameraShockTimer <= 0)
        {
            s_cameraVX = vx;
            s_cameraVY = vy;
            s_cameraShockTimer = time;
        }
    }

    static void UpdateRSParallelShow()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        CParallel.s_isAllDone = true;
        s_parallel.Update();
        if (CParallel.s_isAllDone)
        {
            //计算结果，归位
            RSParallelReset();
            SetRoundState(CParallel.s_nextRS);
            s_battlePara[BATTLE_PARA_SHOW_UI] = T_TRUE;
        }
    }

    static void UpdateRSRoundEnd()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        CActor actor;
        int i;
        boolean win = true;
        boolean lose = true;
        /**
         * 麻痹 状态
         * 引自CalcResult();由于增加麻痹后不执行updateBuff()在这里强制执行一次。
         */
        s_actorBattleActivated.UpdateBuff();
        if (s_rsEffectEnd)
        {
            s_battleUIFalg = BATTLE_UI_ALL;
            for (i = 0; i < s_battleActorCount; i++)
            {
                actor = s_actorInBattle[i];
                if (actor.m_value[BV_CAMP] == CAMP_FOE)
                {
                    if ((actor.m_value[BV_STATE] & BAS_DEAD) != 0)
                    {
                        actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] = T_FALSE;
                    }
                    else
                    {
                        win = false;
                    }
                }
                else if (actor.m_value[BV_CAMP] == CAMP_ALLY)
                {
                    if ((actor.m_value[BV_STATE] & BAS_DEAD) == 0)
                    {
                        lose = false;
                    }
                }
            }

            //是否胜利?
            if (win)
            {
                s_battleResult = BATTLE_RESULT_WIN;
                s_parallel = CParallel.GetInstance();
                s_parallel.Init(PARALLEL_TYPE_BATTLE_END, PARALLEL_CONDITION_AUTO);
                s_parallel.m_actionType = s_battleResult;
//@#if Channel == "GF"
                GFUploadScore.s_score += s_battleActorCount;
//@#endif
                SetRoundState(RS_LOOT);
            }
            else if (lose)
            {
//@#if SMS
//@                if (s_battlePara[BATTLE_PARA_LOSE_CONTINUE] == T_TRUE)
//@                {
//@                    s_battleResult = BATTLE_RESULT_LOSE;
//@                    s_parallel = CParallel.GetInstance();
//@                    s_parallel.Init(PARALLEL_TYPE_BATTLE_END, PARALLEL_CONDITION_AUTO);
//@                    s_parallel.m_actionType = s_battleResult;
//@                    SetRoundState(RS_LOOT);
//@                }
//@                else
//@                {
//@                    ReleaseTempEntity();
//@                    CEntity entitySMS = s_entityList[ENTITY_LIST_BATTLE_SMS].SearchObject(SMS_RELIFE_ID);
//@                    if (entitySMS != null)
//@                    {
//@                        CEntity entity = CEntity.GetInstance();
//@                        entity.Clone(entitySMS);
//@                        s_tempList.InsertAfter(entity);
//@                        s_wnd = CWnd.GetInstance();
//@                        s_wnd.Init(WND_SMS_MESSAGE, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, true);
//@                        s_wnd.m_isTop = true;
//@                        s_wnd.m_curSelect = entity;
//@                        s_wnd.DoModule();
//@                        SetRoundState(RS_ALL_DIE);
//@                        s_smsType = ENTITY_LIST_BATTLE_SMS;
//@                    }
//@                    else
//@                    {
//@                        s_battleResult = BATTLE_RESULT_LOSE;
//@                        s_parallel = CParallel.GetInstance();
//@                        s_parallel.Init(PARALLEL_TYPE_BATTLE_END, PARALLEL_CONDITION_AUTO);
//@                        s_parallel.m_actionType = s_battleResult;
//@                        SetRoundState(RS_LOOT);
//@                        s_battleUIFalg = BATTLE_UI_ALL;
//@                    }
//@                }
//@#else
                s_battleResult = BATTLE_RESULT_LOSE;
                s_parallel = CParallel.GetInstance();
                s_parallel.Init(PARALLEL_TYPE_BATTLE_END, PARALLEL_CONDITION_AUTO);
                s_parallel.m_actionType = s_battleResult;
                SetRoundState(RS_LOOT);
//@#endif
            }
            else
            {
                StartNextRound();
            }
        }
    }

    static int[] s_battleLimit = new int[AMOUNT_BATTLE_LIMIT];

    static boolean CheckBattleScript()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        boolean isContinue = true;
        boolean res = false;

        while (isContinue)
        {
            if (s_battleScriptRound[s_battleScriptCmdCur] != s_battleRound || s_battleScriptCmdCur >= s_battleScriptNum)
            {
                isContinue = false;
            }
            else
            {
                //向下步进
                int offset = s_battleScriptCmdCur * 7;
                switch (s_battleScriptType[s_battleScriptCmdCur])
                {
                case SCHEMA_BC_LIMIT_INFO:
                    s_battleLimit[BATTLE_LIMIT_SCRIPT_ID] = s_battleScriptCmdCur;
                    s_battleScriptCmdCur++;
                    break;
                case SCHEMA_BC_LIMIT:
                    s_battleLimit[BATTLE_LIMIT_ACTIONTYPE] = s_battleScriptData[offset + CMD_BC_LIMIT_ACTION];
                    s_battleLimit[BATTLE_LIMIT_ACTIONID] = s_battleScriptData[offset + CMD_BC_LIMIT_ACTIONID];
                    s_battleLimit[BATTLE_LIMIT_AIM] = s_battleScriptData[offset + CMD_BC_LIMIT_AIM];
                    s_battleScriptCmdCur++;
                    break;
                case SCHEMA_BC_DIALOG:
                    int rs = -1;
                    switch (s_battleScriptData[offset + CMD_BC_DIALOG_STATE])
                    {
                    case DEFINE_BATTLE_STATE_RS_UI:
                        rs = RS_UI;
                        break;
                    case DEFINE_BATTLE_STATE_RS_INIT:
                        rs = RS_INIT;
                        break;
                    }
                    if (s_rs == rs)
                    {
                        CParallel aParallel;
                        aParallel = CParallel.GetInstance();
                        if (null == s_parallel)
                        {
                            aParallel.Init(PARALLEL_TYPE_DIALOG, PARALLEL_CONDITION_AUTO);
                            s_parallel = aParallel;
                        }
                        else
                        {
                            aParallel.Init(PARALLEL_TYPE_DIALOG, PARALLEL_CONDITION_AUTO); //等待前置任务的信号
                            s_parallel.GetDirectLineDescent().AddChild(aParallel);
                        }
                        aParallel.m_tag = s_battleScriptCmdCur;
                        CParallel.s_nextRS = s_rs;
                        s_battleScriptCmdCur++;
                        s_battlePara[BATTLE_PARA_SHOW_UI] = T_FALSE;
                        SetRoundState(RS_PARALLEL_SHOW);
                        res = true;
                    }
                    else
                    {
                        isContinue = false;
                    }
                    break;
                case SCHEMA_BC_ANIM:
                    CActor actor = GetActor(s_battleScriptData[offset + CMD_BC_ANIM_ACTOR]);
                    actor.SetAction(s_battleScriptData[offset + CMD_BC_ANIM_ACTION]);
                    actor.SetPos(s_battleScriptData[offset + CMD_BC_ANIM_X], s_battleScriptData[offset + CMD_BC_ANIM_Y]);
                    s_actorBattleUI[s_actorBattleUICount] = actor;
                    s_actorBattleUICount++;
                    s_battleScriptCmdCur++;
                    break;
                case SCHEMA_BC_ACTION:
                    s_autoUICmdsCount = 0;
                    s_autoUICur = 0;
                    AddAutoUICmd(UI_AUTO_RESET, 0);

                    //选择行动类型
                    switch (s_battleScriptData[offset + CMD_BC_ACTION_ACTION])
                    {
                    case 0: //物理攻击
                        AddAutoUICmd(UI_AUTO_CURSOR, BATTLE_MI_ATT);

                        //确定目标
                        AddAutoUICmd(UI_AUTO_CONFIRM, s_battleScriptData[offset + CMD_BC_ACTION_PARA0]);
                        break;
                    case 1: //技能攻击

                        //移动光标到技能
                        AddAutoUICmd(UI_AUTO_CURSOR, BATTLE_MI_SKILL);

                        //确定使用技能
                        AddAutoUICmd(UI_AUTO_CONFIRM, 0);

                        //移动光标到所使用的技能
                        for (int i = 0; i < s_battleScriptData[offset + CMD_BC_ACTION_PARA0]; i++)
                        {
                            AddAutoUICmd(UI_AUTO_CURSOR, i + 1);
                        }

                        //确定技能ID
                        AddAutoUICmd(UI_AUTO_CONFIRM, 0);

                        //移动光标到指定目标
                        AddAutoUICmd(UI_AUTO_CURSOR, s_battleScriptData[offset + CMD_BC_ACTION_AIM]);

                        //确定目标
                        AddAutoUICmd(UI_AUTO_CONFIRM, 0);

                        //伤害
                        if (s_battleScriptData[offset + CMD_BC_ACTION_PARA2] >= 0)
                        {
                            s_battleScriptDamageValue = s_battleScriptData[offset + CMD_BC_ACTION_PARA2];
                        }
                        break;
                    }
                    AddAutoUICmd(UI_AUTO_CONTROL, 0);
                    s_menuSlot[MENU_SLOT_BATTLE_MAIN] = 0;
                    CWnd.s_UICtrlMode = UI_CONTROL_SCRIPT;
                    s_battleScriptCmdCur++;
                    isContinue = false;
                    break;
                default:
                    break;
                }
            }
        }
        return res;
    }

    static void UpdateRSInit()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        s_battleScriptDamageValue = -1;
        s_menuSlot[s_wndBattleMain.m_menuSlot] = BATTLE_MI_ATT;
        for (int i = 0; i < AMOUNT_BATTLE_LIMIT; i++)
        {
            s_battleLimit[i] = -1;
        }
        s_uiActorQueueX = BU_QUEUE_X;
        //计算控制方式
        CalcBattleControlType();
        s_actorBattleActivated.m_value[BV_ACTION] = BATTLE_ACTION_TYPE_NORMAL;
        if (s_actorBattleActivated.m_actionID == ACTION_BATTLE_GUARD)
        {
            if (s_actorBattleActivated.m_value[BV_HP] < s_actorBattleActivated.m_value[BV_HPMAX] / BATTLE_WEAK)
            {
                s_actorBattleActivated.SetAction(ACTION_BATTLE_WEAK);
            }
            else
            {
                s_actorBattleActivated.SetAction(ACTION_BATTLE_WAIT);
            }
        }
        CalcBattlerFrameInfo(s_actorBattleActivated);
        //重置actor行动力
        ResetActorAP(s_actorBattleActivated);
        UpdateBattleQueueIcon();
    }

    static void ResetActorAP(CActor actor)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        actor.m_value[BV_AP] = DEFAULT_BATTLR_ACTOR_AP;
    }

    static void UpdateRSReady()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        if (CWnd.s_UICtrlMode == UI_CONTROL_AI)
        {
            AIThink();
        }
        SetRoundState(RS_UI);
    }

    /**
     * 清除Parallel过程中的动作和位置变化.
     */
    static void RSParallelReset()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        s_parallel.Release();
        s_parallel = null;
    }

    static int AIThinkCalcSide()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        int res = -1;
        return res;
    }

    static void AIThink()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        int rand = 0;
        int count = 0;
        int actionType = -1;
        int actionID = -1;
        int actionChoiceCount = 1;
        int coef = 0;
        int targetSide = 0;
        int allySideCount = 0;

        //第一步,投骰,决定行动
        rand = UtilRandom(0, 100);
        //选择的数量,技能数+物理攻击
        actionChoiceCount = s_actorBattleActivated.GetSkillCount();
        //因为涉及到技能魔法值的问题,所以先筛选技能
        actionType = BATTLE_ACTION_TYPE_NORMAL;
        if (s_actorBattleActivated.m_buff[DB_EFFECT_SET_ENUMERATE_BUF_CHAOS] > 0)
        {
            //如果混乱,物理攻击
            actionType = BATTLE_ACTION_TYPE_NORMAL;
            allySideCount = GetCampNumber(s_actorBattleActivated.m_value[BV_CAMP]);
        }
        else
        {
            int skillID = 0;
            for (int i = 0; i < actionChoiceCount; i++)
            {
                skillID = s_dbEnemies[s_actorBattleActivated.m_value[BV_ID]][DB_ENEMIES_SKILL1 + 2 * i];
                coef = s_actorBattleActivated.GetSkillEntity(skillID).m_coolDown > 0 ?
                       0 : s_dbEnemies[s_actorBattleActivated.m_value[BV_ID]][DB_ENEMIES_SKILLPRO1 + 2 * i];
                rand -= coef;
                if (rand < 0)
                {
                    //判断MP是否够用,是否能使用
                    if (s_actorBattleActivated.m_value[BV_MP] >= s_dbSkills[skillID][DB_SKILLS_USEMP])
                    {
                        actionType = BATTLE_ACTION_TYPE_SKILL;
                        actionID = i;
                        break;
                    }
                    else
                    {
                        rand += coef;
                    }
                }
            }
        }

        //选攻击方向
        if (s_actorBattleActivated.m_value[BV_CAMP] == CAMP_ALLY)
        {
            //我方AI
            if (s_actorBattleActivated.m_buff[DB_EFFECT_SET_ENUMERATE_BUF_CHAOS] > 0 && allySideCount > 1)
            {
                targetSide = CAMP_ALLY;
            }
            else
            {
                targetSide = CAMP_FOE;
            }
        }
        else
        {
            if (s_actorBattleActivated.m_buff[DB_EFFECT_SET_ENUMERATE_BUF_CHAOS] > 0 && allySideCount > 1)
            {
                targetSide = CAMP_FOE;
            }
            else
            {
                targetSide = CAMP_ALLY;
            }
        }
        count = GetCampNumber(targetSide);

        //第二次投骰,决定目标
        rand = UtilRandom(0, count);

        //填充指令
        s_autoUICmdsCount = 0;
        s_autoUICur = 0;

        switch (actionType)
        {
        case BATTLE_ACTION_TYPE_NORMAL:

            //普通攻击
            //复位
            AddAutoUICmd(UI_AUTO_RESET, 0);

            //选择物理攻击
            AddAutoUICmd(UI_AUTO_CURSOR, BATTLE_MI_ATT);

            //确定攻击
            AddAutoUICmd(UI_AUTO_CONFIRM, 0);

            //选定目标
            AddAutoUICmd(UI_AUTO_CURSOR, rand);

            //确定目标
            AddAutoUICmd(UI_AUTO_CONFIRM, 0);
            break;
        case BATTLE_ACTION_TYPE_SKILL:

            //普通攻击
            //复位
            AddAutoUICmd(UI_AUTO_RESET, 0);

            //选择技能
            AddAutoUICmd(UI_AUTO_CURSOR, BATTLE_MI_SKILL);

            //确定技能
            AddAutoUICmd(UI_AUTO_CONFIRM, 0);

            //选定具体的项
            AddAutoUICmd(UI_AUTO_CURSOR, actionID);

            //确定技能
            AddAutoUICmd(UI_AUTO_CONFIRM, 0);

            //选定目标
            AddAutoUICmd(UI_AUTO_CURSOR, rand);

            //确定目标
            AddAutoUICmd(UI_AUTO_CONFIRM, 0);
            break;
        default:
            break;
        }

        s_menuSlot[MENU_SLOT_BATTLE_MAIN] = 0;
    }

    static void UpdateRSUI()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
//@#if PHONE_TOUCH
//@        if (IsTouchInPoint(POINTER_SHOP_X, POINTER_SHOP_Y, POINTER_RANGE_W, POINTER_RANGE_H))
//@        {
//@            KeyHandlePressed(KEY_VALUE_NUM0);
//@        }
//@#endif
        s_battleUIFalg = BATTLE_UI_ALL;
        if (null != s_wndBattleMain)
        {
            if (s_rs == RS_UI)
            {
                s_wndBattleMain.Update();
            }
        }
        s_actorUI.UpdateAnimation();
    }

    static void ShowBattleLimitInfo()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        s_parallel = CParallel.GetInstance();
        s_parallel.Init(PARALLEL_TYPE_DIALOG, PARALLEL_CONDITION_AUTO);
        s_parallel.m_tag = s_battleLimit[BATTLE_LIMIT_SCRIPT_ID];
        s_battlePara[BATTLE_PARA_SHOW_UI] = T_FALSE;
        CParallel.s_nextRS = RS_UI;
        SetRoundState(RS_PARALLEL_SHOW);
    }

    static int GetNextAimedID(int now, int step, boolean canWrap)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        int res = now;
        do
        {
            res += step;
            res = (res + s_battleActorCount) % s_battleActorCount;
            if ((!canWrap) && res == now)
            {
                return -1;
            }
        }
        while ((s_battleAimSet & (1 << res)) == 0);
        return res;
    }

    static int GetTeamAimedID()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        int res = 0;
        CActor ActiveActor = s_actorInBattle[s_battleActiveActorID];
        CActor FreedActor = s_actorInBattle[s_battleAimID];
        int ActiveNum = GetAllCampNum(ActiveActor.m_value[BV_CAMP]);
        int FreedNum = GetAllCampNum(FreedActor.m_value[BV_CAMP]);
        res = s_battleAimID - ActiveNum;
        if (FreedNum == 1)
        {
            res = 1;
        }
        if (FreedNum == 2)
        {
            if (res == 1)
            {
                res++;
            }
        }
        return res;
    }

    static int GetTeamALLID(int i)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        int res = 0;
        CActor ActiveActor = s_actorInBattle[s_battleActiveActorID];
        CActor FreedActor = s_actorInBattle[s_battleAimID];
        int ActiveNum = GetAllCampNum(ActiveActor.m_value[BV_CAMP]);
        int FreedNum = GetAllCampNum(FreedActor.m_value[BV_CAMP]);
        if (FreedNum == 2 && i == 1)
        {
            res++;
        }
        return res;
    }

    static void RefreshAim(int step, boolean canWrap)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        s_battleAimID = GetNextAimedID(s_battleAimID, step, canWrap);
        s_wndBattleDesc.m_dataCount = 0;
        s_wndBattleAim.m_dataCount = 0;
        int aimMax = GetCampNumber(s_actorInBattle[s_battleAimID].m_value[BV_CAMP]);
        int count = s_battleAimCount > 0 ? Math.min(s_battleAimCount, aimMax) : aimMax;
        int strID = 0, i, j = s_battleAimID;
        CActor actor = null;

        for (i = 0; i < count; i++)
        {
            if (j < 0)
            {
                break;
            }
            else
            {
                if ((s_battleAimSet & (1 << j)) != 0)
                {
                    actor = s_actorInBattle[j];
                    switch (actor.m_value[BV_CAMP])
                    {
                    case CAMP_ALLY:
                        strID = STR_SYSTEM_GAMEINFO_NAME_0;
                        break;
                    case CAMP_FOE:
                        strID = STR_SYSTEM_ENEMIES_DESC_0;
                        break;
                    }
                    s_wndBattleDesc.AddData(strID + actor.m_value[BV_ID]);
                    s_wndBattleAim.AddData(j);
                }
            }
            j = GetNextAimedID(j, 1, canWrap); //OK
        }
    }

    static void UpdateRSAim(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        CActor actor = null;
        boolean isContinue = false;
        boolean isSelect = false;
        if ((s_battleUIFalg & BATTLE_UI_TARGET) != 0)
        {
            s_actorTarget.UpdateAnimation();
        }

        switch (CWnd.s_UICtrlMode)
        {
        case UI_CONTROL_AI:
        case UI_CONTROL_SCRIPT:
            s_battleAnimFalg = BATTLE_ANIM_BG | BATTLE_ANIM_ACTOR | BATTLE_ANIM_INFO;
            UpdateAIControl(wnd);
            if (CWnd.s_autoCmdConfirmed)
            {
                s_battleAimID = GetNextAimedID(s_battleAimID, s_menuSlot[wnd.m_menuSlot], true); //OK
                isSelect = true;
            }
            break;
        case UI_CONTROL_MANUAL:

//@#if PHONE_TOUCH
//@            if (IsTouchInPoint(SCREEN_WIDTH - POINTER_RANGE_W, POINTER_BATTLE_CHANCEL_Y, POINTER_RANGE_W, POINTER_RANGE_H))
//@            {
//@                KeyHandlePressed(KEYVALUE_RIGHTSOFT);
//@            }
//@#endif
//            s_wndBattleDesc.m_show = true;
            if (IsKeySinglePressed(GKEY_OK))
            {
                isSelect = true;
            }
            else if (IsKeySinglePressed(GKEY_SOFTRIGHT))
            {
                SetRoundState(RS_UI);
                s_wndBattleDesc.m_show = false;
                return;
            }
            else if (IsKeySinglePressed(GKEY_MOVEUP | GKEY_MOVELEFT))
            {
                RefreshAim( -1, true);
            }
            else if (IsKeySinglePressed(GKEY_MOVEDOWN | GKEY_MOVERIGHT))
            {
                RefreshAim(1, true);
            }
            break;
        }

        if (isSelect)
        {
            int costAP = DEFAULT_BATTLR_ACTOR_AP;
            if (s_battleLimit[BATTLE_LIMIT_AIM] >= 0 && s_battleAimID != s_battleLimit[BATTLE_LIMIT_AIM])
            {
                //不正确进入提示
                ShowBattleLimitInfo();
            }
            else
            {
                s_wndBattleDesc.m_dataCount = 0;
                switch (s_battleActionType)
                {
                case BATTLE_ACTION_TYPE_NORMAL:
                    s_battleActionInfo = s_actorBattleActivated.m_value[BV_FRAME_INFO];
                    s_wndBattleDesc.AddData(STR_SYSTEM_NORMAL_ATTACK);
                    isContinue = true;
                    break;
                case BATTLE_ACTION_TYPE_SKILL:
                    if (s_dbSkills[s_battleActionID][DB_SKILLS_USEMP] >= 0)
                    {
                        s_actorBattleActivated.m_value[BV_MP] -= s_dbSkills[s_battleActionID][DB_SKILLS_USEMP];
                    }
                    else
                    {
                        PackageModify(0, DEFINE_HOLD_TYPE_GOLD, s_dbSkills[s_battleActionID][DB_SKILLS_USEMP]);
                    }
                    s_actorBattleActivated.m_value[BV_SP] -= s_dbSkills[s_battleActionID][DB_SKILLS_USESP];
                    s_battleActionInfo = s_dbSkills[s_battleActionID][DB_SKILLS_FRAMEINFO];
                    costAP = s_dbSkills[s_battleActionID][DB_SKILLS_ACT_TIME];
                    s_wndBattleDesc.AddData(STR_SYSTEM_SKILL_NAME_DESC_0 + s_battleActionID);
                    CEntity entitySkill = GetBattleSkill();
                    entitySkill.m_coolDown = (short) (s_dbSkills[entitySkill.m_id][DB_SKILLS_COOLDOWN] + 1);
                    isContinue = true;
                    break;
                case BATTLE_ACTION_TYPE_ITEM:
                    int aimNum = s_battleAimCount > 0 ? s_battleAimCount : BATTLE_ALLY_MAX;
                    int aimID = s_battleAimID;
                    aimNum = Math.min(aimNum, GetCampNumber(s_actorBattleActivated.m_value[BV_CAMP]));
                    int count = 0;
                    for (int i = 0; i < aimNum; i++)
                    {
                        actor = s_actorInBattle[aimID];
                        CalcItemUsed(s_battleActionID, actor);
                        if (CalcUsedEffectCount() > 0)
                        {
                            count++;
                        }
                        ClearEffectValue();
                        aimID = GetNextAimedID(aimID, 1, true);
                    }
                    if (count > 0)
                    {
                        PackageModify(s_battleActionID, DEFINE_HOLD_TYPE_BATTLE, -1);
                        s_battleActionInfo = s_dbItems[s_battleActionID][DB_ITEMS_FRAMEINFO];
                        costAP = s_dbItems[s_battleActionID][DB_ITEMS_ACT_TIME];
                        s_wndBattleDesc.AddData(STR_SYSTEM_ITEMS_NAME_0 + s_battleActionID);
                        isContinue = true;
                    }
                    else
                    {
                        SetRoundState(RS_UI);
                        s_wndBattleAction.Init(WND_MESSAGE, IGM_MESSAGE_X, IGM_MESSAGE_Y, IGM_MESSAGE_W, IGM_MESSAGE_H, true);
                        s_wndBattleAction.DoModule();
                        s_wndBattleAction.m_title = STR_SYSTEM_BATTLE_USE_ONLY;
                    }
                    break;
                }

                if (isContinue)
                {
                    s_menuSlot[MENU_SLOT_BATTLE_MAIN] = 0;
                    s_parallel = CParallel.GetInstance();
                    s_parallel.Init(PARALLEL_TYPE_RUSH, PARALLEL_CONDITION_AUTO);
                    s_parallel.SetActionInfo(s_battleActiveActorID, s_battleActionType, s_battleActionID, s_battleAimID, s_battleAimCount);
                    s_parallel.m_infoID = s_battleActionInfo;
                    s_actorBattleActivated.m_value[BV_AP] -= costAP;
                    s_wndBattleDesc.m_show = true;
                    SetRoundState(RS_PARALLEL);
                }
            }
        }
    }

    static void CalcResult()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        for (int i = 0; i < s_battleActorCount; i++)
        {
            s_actorInBattle[i].m_value[BV_DMG_VALUE] = 0;
            s_actorInBattle[i].SetPos(s_actorInBattle[i].m_anchorX, s_actorInBattle[i].m_anchorY);
        }

        //~~ 根据 需求, 决定是在UpdateRSRoundEnd() 中还是在这里.
//        s_actorBattleActivated.UpdateBuff();
        s_actorBattleActivated.UpdateCoolDown();
        if (s_bSPIncrease)
        {
            s_actorBattleActivated.m_value[BV_SP] += BATTLE_SP_INCREASE;
            s_bSPIncrease = false;
        }
    }

    static void BattleResetAction(int side)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        for (int i = 0; i < s_battleActorCount; i++)
        {
            if (s_actorInBattle[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] == T_TRUE &&
                (s_actorInBattle[i].m_value[BV_STATE] & BAS_DEAD) == 0 && (s_actorInBattle[i].m_value[BV_CAMP] & side) != 0)
            {
                if (s_actorInBattle[i].m_value[BV_HP] < s_actorInBattle[i].m_value[BV_HPMAX] / BATTLE_WEAK)
                {
                    s_actorInBattle[i].SetAction(ACTION_BATTLE_WEAK);
                }
                else
                {
                    s_actorInBattle[i].SetAction(ACTION_BATTLE_WAIT);
                }
            }
        }
    }

    static void CalcLootItem(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        int random, threshold;
        int moneySum = 0, tokenSum = 0;
        int enemyID, actorID, itemID;
        int type, id, count;
//@#if SMS
//@        int goldRate = s_variables[SCRIPT_VARIABLE_SMS_PARA0 + ENUM_SMS_PURPOSE0_GOLD_RATE];
//@#else
        int goldRate = 1;
//@#endif
//@#if EXIST_SCORE
//@        int scoreSum = 0;
//@#endif
        for (actorID = 0; actorID < BATTLE_ENEMY_MAX; actorID++)
        {
            enemyID = s_dbEnemyTeams[s_battlePara[BATTLE_PARA_ENEMY_TEAM_ID]][DB_ENEMYTEAMS_MEMBER0 + actorID];
            if (enemyID >= 0)
            {
                moneySum += s_dbEnemies[enemyID][DB_ENEMIES_GOLD]; //金钱
                tokenSum += s_dbEnemies[enemyID][DB_ENEMIES_TOKEN]; //代币
//@#if EXIST_SCORE
//@                scoreSum += s_dbEnemies[enemyID][DB_ENEMIES_SCORE]; //积分
//@#endif
                for (int i = 0; i < 3; i++)
                { //道具
                    itemID = s_dbEnemies[enemyID][DB_ENEMIES_DROP0 + (i << 1)];
                    if (itemID >= 0)
                    {
                        threshold = s_dbEnemies[enemyID][DB_ENEMIES_DEOPPRO0 + (i << 1)];
                        if (threshold > 0)
                        {
                            random = UtilRandom(0, 100);
                            if (random <= threshold)
                            {
                                AddItemToBasket(itemID, 1);
                            }
                        }
                    }
                }
            }
        }
        moneySum *= goldRate;
        wnd.AddData(moneySum);
        wnd.AddData(tokenSum);
        PackageModify(0, DEFINE_HOLD_TYPE_GOLD, moneySum);
        PackageModify(0, DEFINE_HOLD_TYPE_TOKEN, tokenSum);
//@#if EXIST_SCORE
//@        wnd.AddData(STR_SYSTEM_GAME_DATA_SCORE);
//@        wnd.AddData(0);
//@        wnd.AddData(scoreSum);
//@        PackageModify(0, DEFINE_HOLD_TYPE_SCORE, scoreSum);
//@#endif
        for (int i = 0; i < s_basketCount; i++)
        {
            type = GetArticalType(s_basket[i << 1] & 0xffff);
            id = GetArticalID(s_basket[i << 1]);
            count = s_basket[(i << 1) + 1];
            PackageModify(id, type, count);
            switch (type)
            {
            case DEFINE_HOLD_TYPE_ITEM:
                wnd.AddData(STR_SYSTEM_ITEMS_NAME_0 + id);
                wnd.AddData(s_dbItems[id][DB_ITEMS_ICON]);
                break;
            case DEFINE_HOLD_TYPE_EQUIP:
                wnd.AddData(STR_SYSTEM_EQUIPS_NAME_0 + id);
                wnd.AddData(s_dbEquips[id][DB_EQUIPS_ICON]);
                break;
            case DEFINE_HOLD_TYPE_MIXTRUE:
                wnd.AddData(STR_SYSTEM_MIXTRUE_NAME_0 + id);
                wnd.AddData(s_dbMixtrue[id][DB_MIXTRUE_ICON]);
                break;
            }
            wnd.AddData(count);
        }
    }

    static void CalcLootExp(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int[] expSum = new int[TEAM_SIZE];
        int coef = 0, lvDiff;
        int enemyID, actorID;
        boolean overLimit = false;
        CActor actor = null;
//@#if SMS
//@        int expRate = s_variables[SCRIPT_VARIABLE_SMS_PARA0 + ENUM_SMS_PURPOSE0_EXP_RATE];
//@#else
        int expRate = 1;
//@#endif
        for (actorID = 0; actorID < BATTLE_ENEMY_MAX; actorID++)
        {
            enemyID = s_dbEnemyTeams[s_battlePara[BATTLE_PARA_ENEMY_TEAM_ID]][DB_ENEMYTEAMS_MEMBER0 + actorID];
            if (enemyID >= 0)
            {
                for (int i = 0; i < TEAM_SIZE; i++)
                {
                    actor = s_actorCommon[i];
                    coef = 0;
//@#if SMS
//@                    overLimit = actor.m_value[BV_LV] >= s_variables[SCRIPT_VARIABLE_SMS_PARA0 + ENUM_SMS_PURPOSE0_LEVEL_LIMIT];
//@#endif
                    if (overLimit || (actor.m_value[BV_STATE] & BAS_DEAD) != 0 || actor.m_value[BV_LV] >= s_heroes[i].length - 1)
                    {
                        continue;
                    }
                    if (actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_INFORMATION] == T_TRUE)
                    {
                        coef = 100;
                    }
                    else if ((actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_INTEAM] & ACTOR_INTEAM_FLAG_STATUS) != 0)
                    {
                        coef = 100;
                    }
                    lvDiff = actor.m_value[BV_LV] - s_dbEnemies[enemyID][DB_ENEMIES_LEVEL];
                    lvDiff = Math.max(0, lvDiff);
                    lvDiff = Math.min(10, lvDiff);
                    coef = coef * (10 - lvDiff) / 10;
                    expSum[i] += s_dbEnemies[enemyID][DB_ENEMIES_EXP] * coef / 100;
                }
            }
        }
        for (int i = 0; i < TEAM_SIZE; i++)
        {
            expSum[i] *= expRate;
            if (s_actorCommon[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_INFORMATION] == T_TRUE)
            {
                wnd.AddData(i);
                wnd.AddData(expSum[i]);
                wnd.AddData(expSum[i]);
            }
        }

        for (int i = 0; i < s_actorLevelUp.length; i++)
        {
            s_actorLevelUp[i].SetPos(x + IGM_LOOT_EXP_ANIM_X, y + IGM_LOOT_EXP_ANIM_Y + IGM_LOOT_EXP_RECT_OFFY * i);
            s_actorLevelUp[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] = T_FALSE;
        }
    }

    static void CalcBattleControlType()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        if (s_actorBattleActivated.m_buff[DB_EFFECT_SET_ENUMERATE_BUF_CHAOS] > 0)
        {
            CWnd.s_UICtrlMode = UI_CONTROL_AI;
        }
        else if (s_actorBattleActivated.m_buff[DB_EFFECT_SET_ENUMERATE_BUF_PARALYZE] > 0)
        {
            CWnd.s_UICtrlMode = UI_CONTROL_NONE;
        }
        else if (s_actorBattleActivated.m_value[BV_CAMP] == CAMP_ALLY)
        {
            CWnd.s_UICtrlMode = UI_CONTROL_MANUAL;
        }
        else
        {
            CWnd.s_UICtrlMode = UI_CONTROL_AI;
        }
    }

    static void CalcBattlerFrameInfo(CActor actor)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        if (actor.m_buff[DB_EFFECT_SET_ENUMERATE_FRAME_INFO] <= 0)
        {
            int id = actor.m_value[BV_ID];
            actor.m_value[BV_FRAME_INFO] = actor.m_value[BV_CAMP] == CAMP_ALLY ?
                                           s_heroes[id][actor.m_value[BV_LV]][DB_HEROES_FRAMEINFO] : s_dbEnemies[id][DB_ENEMIES_FRAMEINFO];
        }
    }

    static int s_battleActionType = 0;
    static int s_battleActionID = 0;
    static int s_battleActionInfo = 0;

    static void UpdateRSLoot()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        CParallel.s_isAllDone = true;
        s_parallel.Update();
        if (CParallel.s_isAllDone)
        {
            RSParallelReset();
            SetGameEP(EP_FREEBATTLE);
        }
    }

    static void UpdateRSEscape()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        if (IsKeySinglePressed(GKEY_OK))
        {
            switch (s_battleActionType)
            {
            case BATTLE_ACTION_ESCAPE_SCUU:
                s_battleResult = BATTLE_RESULT_ESCAPE;
                s_parallel = CParallel.GetInstance();
                s_parallel.Init(PARALLEL_TYPE_BATTLE_END, PARALLEL_CONDITION_AUTO);
                s_parallel.m_actionType = s_battleResult;
                SetRoundState(RS_LOOT);
                break;
            case BATTLE_ACTION_ESCAPE_FAIL:
                StartNextRound();
                break;
            case BATTLE_ACTION_ESCAPE_DISABLE:
                SetRoundState(RS_UI);
                break;
            }
        }
    }

    static void FreeBattleRes()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        CEntity entity0, entity1;
        CActor actor = null;

        switch (s_loadProgress)
        {
        case 0:
            if ((s_optionFlag & OPTION_FLAG_SOUND) != 0)
            {
                SndStop();
            }
            FreeBattleBG();
            break;
        case 1:

            //释放战斗actor的数据
            for (int i = 0; i < s_battleActorCount; i++)
            {
                actor = s_actorInBattle[i];
                actor.m_refImage = null;
                if (!s_resInUsed[actor.m_animationID])
                {
                    s_imgs[actor.m_animationID] = null;
                }
                for (int j = 0; j < AMOUNT_DB_EFFECT_SET; j++)
                {
                    actor.m_buff[j] = 0;
                }
            }
            System.gc();
            break;
        case 2:
            s_imgBattlePortrait = null;
            s_actorBattlePortrait.Free();
            s_actorBattlePortrait = null;
            System.gc();
            break;
        case 3:

            //释放战斗actor的技能
            for (int i = 0; i < s_battleActorCount; i++)
            {
                actor = s_actorInBattle[i];
                if (actor.m_value != null)
                {
                    if (actor.m_value[BV_CAMP] == CAMP_ALLY)
                    {
                        actor.m_entitySkill.m_next = null;
                        actor.m_entitySkillEx.m_next = null;
                    }
                    else
                    {
                        actor.m_entitySkill.ReleaseAll();
                        actor.m_entitySkill.m_next = null;
                        actor.m_entitySkillEx.ReleaseAll();
                        actor.m_entitySkillEx.m_next = null;
                        actor.m_value = null;
                    }
                }
            }
            break;
        case 4:

            //将战斗中可以使用的物品归还给普通包裹
            entity1 = s_entityList[ENTITY_LIST_BATTLE].m_next;
            while (null != entity1)
            {
                PackageModify(entity1.m_id, DEFINE_HOLD_TYPE_ITEM, entity1.m_count);
                entity0 = entity1;
                entity1 = entity1.m_next;
                entity0.RemoveSelf();
                entity0.Release();
            }
            s_entityList[ENTITY_LIST_BATTLE].m_next = null;
            break;
        case 5:

            //遍历队伍成员如死亡将其HP改为1
            for (int i = 0; i < TEAM_SIZE; i++)
            {
                if (s_actorCommon[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_INFORMATION] == T_TRUE)
                {
                    s_actorCommon[i].m_value[BV_HP] = Math.max(s_actorCommon[i].m_value[BV_HP], 1);
                }
            }
            s_actorBattleActivated = null;
            FreeHeroRes();
            break;
        case 6:
            if (s_wndBattleMain != null)
            {
                s_wndBattleMain.Release();
                s_wndBattleMain = null;
            }

            //s_wndBattleAction是s_wndBattleMain的子窗口,已经被释放
            s_wndBattleAction = null;
            if (s_wndBattleAim != null)
            {
                s_wndBattleAim.Release();
                s_wndBattleAim = null;
            }
            if (s_wndBattleDesc != null)
            {
                s_wndBattleDesc.Release();
                s_wndBattleDesc = null;
            }
            s_battleStart = false;
            CWnd.s_UICtrlMode = UI_CONTROL_MANUAL;
            s_autoUICmdsCount = 0;
            System.gc();
            break;
        case 7:
            LoadTile();
            break;
        case 8:
            CreateMapBufferImage();
            s_redrawMapBuffer = true;
            break;
        case 9:
            if ((s_optionFlag & OPTION_FLAG_SOUND) != 0)
            {
                SndPlay(s_soundSlot[DEFINE_MUSIC_SLOT_SCENE]);
            }
            switch (s_battleResult)
            {
            case BATTLE_RESULT_WIN:
                SetGameEP(EP_NORMAL);
                break;
            case BATTLE_RESULT_LOSE:
                if (s_battlePara[BATTLE_PARA_LOSE_CONTINUE] == T_TRUE)
                { //失败继续
                    s_variables[SCRIPT_VARIABLE_BATTLE_LOSE] = T_TRUE;
                    SetGameEP(EP_NORMAL);
                }
                else
                {
                    SetGameState(GS_GAME_OVER);
                }
                break;
            case BATTLE_RESULT_ESCAPE:
                SetGameEP(EP_NORMAL);
                break;
            }
            s_battleScriptCmdCur = 0;
            s_battleScriptNum = 0;
            SetSceneUIAction(s_actorUI, s_UIAction);

//@#if SMS
//@            SetSceneUIAction(s_actorSMS, s_SMSAction);
//@
//@#endif
            break;
        }
        s_loadProgress++;
    }

    static void UpdateRSScreen()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        if ((s_battleAnimFalg & BATTLE_ANIM_ACTOR) != 0)
        {
            for (int i = 0; i < s_battleActorCount; i++)
            {
                s_actorInBattle[i].UpdateAnimation();
            }
        }
        if ((s_battleAnimFalg & BATTLE_ANIM_BG) != 0)
        {
            s_actorBattleBG.UpdateAnimation();
        }
    }
    /**
     * 300K战斗背景用几个图拼起来的,下面定义 一些变量来.
     */
    private static final int s_bgCount = 4;  //画几个

    static void RenderRSScreen(Graphics g, int cameraX, int cameraY)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        int x = 0, y = 0;
        CActor actor;

        if ((s_battleAnimFalg & BATTLE_ANIM_BG) != 0)
        {
//@#if BATTLE_BG_COUNT_NUM
//@            for (int i = 0; i < s_bgCount; i++)
//@            {
//@                g.drawImage(s_imgBattleBG,cameraX+i*s_imgBattleBG.getWidth(), cameraY,ALIGN_LEFT_TOP);
//@            }
//@#else
            s_actorBattleBG.DrawAnimation(g, cameraX, cameraY);
//@#endif
        }
        else if (s_cameraColorTimer > 0)
        {
            UtilFillRect(g, COLOR_BLACK, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT - BATTLE_UI_INFORMATION_H);
        }

        QuickSort(0, s_drawlistGroundCount - 1);

        for (int i = 0; i < s_drawlistGroundCount; i++)
        {
            actor = s_actorsDrawList[i];
            if (actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] == T_TRUE)
            {
                if ((s_battleAnimFalg & BATTLE_ANIM_ACTOR) != 0)
                {
                    actor.DrawAnimation(g, cameraX, cameraY);
                }
                if ((s_battleAnimFalg & BATTLE_ANIM_INFO) != 0)
                {
                    x = actor.m_curX - cameraX;
                    y = actor.m_curY - actor.m_value[BV_HEIGHT] - cameraY;
                    if ((actor.m_value[BV_STATE] & BAS_DEAD) == 0 && (actor.m_value[BV_CAMP] == CAMP_FOE))
                    {
                        UtilDrawRect(g, COLOR_DARK_RED, x + BATTLE_ACTOR_HPBAR_X - 1, y - 1, BATTLE_ACTOR_HPBAR_W + 1, BATTLE_ACTOR_HPBAR_H + 1);
                        UtilFillRect(g, COLOR_WHITE, x + BATTLE_ACTOR_HPBAR_X, y, BATTLE_ACTOR_HPBAR_W, BATTLE_ACTOR_HPBAR_H);
                        UtilFillRect(g, COLOR_RED, x + BATTLE_ACTOR_HPBAR_X, y, BATTLE_ACTOR_HPBAR_W * actor.m_value[BV_HP] / actor.m_value[BV_HPMAX],
                                     BATTLE_ACTOR_HPBAR_H);
                    }
                    RenderActorBuff(g, actor, cameraX, cameraY);
                }
            }
        }
    }

    static void RenderActorBuff(Graphics g, CActor actor, int cameraX, int cameraY)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        for (int j = 0; j < AMOUNT_BUFF_ICON; j++)
        {
            if (actor.m_buff[DB_EFFECT_SET_ENUMERATE_ICON_WEAK + j] > 0)
            {
                s_actorIcon.DrawModule(g, MODULE_ICON_BUFF_0 + j, actor.m_curX + j * 8 - (AMOUNT_BUFF_ICON >> 1) * 8 - cameraX,
                                       actor.m_curY - actor.m_value[BV_HEIGHT] + BATTLE_ACTOR_BUFF_Y - cameraY, 0);
            }
        }
    }

    static int s_damageValueCount;
    static int[] s_damageValueData = new int[AMOUNT_DAMAGE_VALUE_OFFSET * DAMAGE_VALUE_COUNT_MAX];

    static void RenderDamageValue(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        int offset = 0, align = 0;

        for (int i = 0; i < s_damageValueCount; i++)
        {
            offset = i * AMOUNT_DAMAGE_VALUE_OFFSET;
            if (s_damageValueData[offset + DAMAGE_VALUE_OFFSET_TIME] < AMOUNT_DV)
            {
                align = s_damageValueData[offset + DAMAGE_VALUE_OFFSET_ALIGN];
                switch (s_damageValueData[offset + DAMAGE_VALUE_OFFSET_TYPE])
                {
                case DAMAGE_VALUE_TYPE_STR:

//@#if BATTLE_PROGRAM_DAMAGE_INFO
//@                    DrawString(g, s_damageValueData[offset + DAMAGE_VALUE_OFFSET_MODULEID], s_damageValueData[offset + DAMAGE_VALUE_OFFSET_X],
//@                               s_damageValueData[offset + DAMAGE_VALUE_OFFSET_Y], ALIGN_LEFT_TOP, COLOR_WHITE, COLOR_BLACK);
//@
//@#else
                    s_actorUI.DrawModule(g, s_damageValueData[offset + DAMAGE_VALUE_OFFSET_MODULEID], s_damageValueData[offset + DAMAGE_VALUE_OFFSET_X],
                                         s_damageValueData[offset + DAMAGE_VALUE_OFFSET_Y], 0);
//@#endif
                    break;
                case DAMAGE_VALUE_TYPE_NUM:

//@#if BATTLE_PROGRAM_DAMAGE_VALUE
//@                int color = 0;
//@                if(s_damageValueData[offset + DAMAGE_VALUE_OFFSET_NUM] > 0)
//@                {
//@                     color =  COLOR_GREEN;
//@                }
//@                else
//@                {
//@                    color =  COLOR_RED;
//@                }
//@                DrawSystemString(g, String.valueOf(s_damageValueData[offset + DAMAGE_VALUE_OFFSET_NUM]), s_damageValueData[offset + DAMAGE_VALUE_OFFSET_X],
//@                                     s_damageValueData[offset + DAMAGE_VALUE_OFFSET_Y], ALIGN_LEFT_BOTTOM, color, COLOR_BLACK);
//@
//@
//@#else
                    UtilRenderUINumber(g,
                                       s_damageValueData[offset + DAMAGE_VALUE_OFFSET_MODULEID], false,
                                       s_damageValueData[offset + DAMAGE_VALUE_OFFSET_NUM],
                                       s_damageValueData[offset + DAMAGE_VALUE_OFFSET_X],
                                       s_damageValueData[offset + DAMAGE_VALUE_OFFSET_Y],
                                       align);

//@#endif
                    break;
                }
                s_damageValueData[offset + DAMAGE_VALUE_OFFSET_TIME]++;
                s_damageValueData[offset + DAMAGE_VALUE_OFFSET_Y] -= DV_SPEED;
            }
        }
    }

    static void RenderRSEnd(Graphics g)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        for (int i = 0; i < s_actorEffInBattle.length; i++)
        {
            if (!s_actorEffInBattle[i].m_end)
            {
                s_actorEffInBattle[i].DrawAnimation(g, 0, 0);
            }
        }
    }

    /**
     * 当轮到出手时如果有自损伤的情况时，由此显示损血
     * @param g
     */
    static void RenderBattleBuffer(Graphics g)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        if (!s_actorEffInBattle[s_battleActiveActorID].m_end)
        {
            s_actorEffInBattle[s_battleActiveActorID].DrawAnimation(g, 0, 0);
        }
    }

    static void RenderBattle(Graphics g)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        CActor actor = s_actorBattleActivated;
        RenderRSScreen(g, s_battleCameraX, s_battleCameraY);
        RenderRSEffect(g, s_battleCameraX, s_battleCameraY);
        switch (s_rs)
        {
        case RS_PARALLEL:
            RenderDamageValue(g);
            break;
        case RS_INIT:
            break;
        case RS_PARALLEL_SHOW:
            break;
        case RS_READY:
            RenderBattleBuffer(g);
            break;
        case RS_UI:
            if ((s_battleAnimFalg & BATTLE_ANIM_CURSOR) != 0)
            {
                s_actorUI.DrawFrame(g, actor.m_anchorX + BATTLE_UI_FINGER_OFFX, actor.m_anchorY - actor.m_value[BV_HEIGHT] + BATTLE_UI_FINGER_OFFY
                                    + ((int) s_frameTick % 2), FRAME_UI_BATTLE_CURSOR);
            }
            break;
        case RS_AIM:
            RenderBattleAim(actor, g);
            break;
        case RS_ROUNDEND:
            RenderRSEnd(g);
            break;
        case RS_LOOT:
            break;
        case RS_ESCAPE:
            RenderRSEscape(g);
            break;
//@#if SMS
//@        case RS_ALL_DIE:
//@            if (s_wnd != null)
//@            {
//@                s_wnd.Render(g);
//@            }
//@            break;
//@        case RS_KILL_ALL:
//@            if (s_wnd != null)
//@            {
//@                s_wnd.Render(g);
//@            }
//@            break;
//@#endif
        }
        RenderBattleUI(g);
        if (s_wnd != null)
        {
            s_wnd.Render(g);
        }

    }

    private static void RenderBattleAim(CActor actor, Graphics g)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        int i = 0, x = 0;
        if ((s_battleAnimFalg & BATTLE_ANIM_CURSOR) != 0)
        {
            for (i = 0; i < s_wndBattleAim.m_dataCount; i++)
            {
                actor = s_actorInBattle[s_wndBattleAim.m_data[i]];
//@#if BATTLE_QUEUE == "COMPET"
                if ((s_battleUIFalg & BATTLE_UI_TARGET) != 0)
                {
                    x = BU_QUEUE_X + (actor.m_value[BV_QUEUE] * BU_QUEUE_W / s_battleQueueGoal);
                    s_actorTarget.SetPos(x, BU_QUEUE_Y);
                    s_actorTarget.DrawAnimation(g, 0, 0);
                }
//@#endif
                s_actorUI.DrawFrame(g, actor.m_anchorX + BATTLE_UI_FINGER_OFFX, actor.m_anchorY - actor.m_value[BV_HEIGHT] + BATTLE_UI_FINGER_OFFY
                                    + ((int) s_frameTick % 2), FRAME_UI_BATTLE_CURSOR);
            }
        }
//@#if PHONE_TOUCH
//@        RenderInformation(g, SOFTKEY_UI_CANCEL, POINTER_BATTLE_CHANCEL_SHOWX, POINTER_BATTLE_CHANCEL_SHOWY, SOFTKEY_UI_STATE);
//@#endif
    }

    static void RenderRSEscape(Graphics g)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        RenderUI(g, UI_TYPE_MESSAGEBOX);
    }

    static int s_battleUIFalg = 0;
    static int s_battleAnimFalg = 0;

    static void RenderBattleUI(Graphics g)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        int i, j, x, y;
        CActor actor;

        //UI背景
        if ((s_battleUIFalg & BATTLE_UI_BG) != 0)
        {
//@#if FILL_RECT_BATTLE_BG
//@            UtilFillRect(g, COLOR_EARTH, BATTLE_UI_INFORMATION_X, BATTLE_UI_INFORMATION_Y, SCREEN_WIDTH, BATTLE_UI_INFORMATION_H);
//@            UtilFillRect(g, COLOR_UI_EDGE, BATTLE_UI_INFORMATION_X, BATTLE_UI_INFORMATION_Y - 3, SCREEN_WIDTH, 3);
//@#else
            //UI显示人物背景边框
            UtilDrawMenuBox(g, COLOR_LIGHT_BLUE, BATTLE_UI_INFORMATION_X, BATTLE_UI_INFORMATION_Y, BATTLE_UI_INFORMATION_W, BATTLE_UI_INFORMATION_H,
                            MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1);
//@#endif
            for (i = 0; i < BATTLE_ALLY_MAX; i++)
            {
//@#if FILL_RECT_BATTLE_BG
//@#else
                UtilDrawMenuBox(g, COLOR_BEIGE, BATTLE_UI_INFORMATION_SUB_X + i * BATTLE_UI_INFORMATION_OFFX, BATTLE_UI_INFORMATION_SUN_Y,
                                BATTLE_UI_INFORMATION_SUB_W, BATTLE_UI_INFORMATION_SUN_H, MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1);
//@#endif
            }
        }

        if ((s_battleUIFalg & BATTLE_UI_PROTRAIT) != 0)
        {
            x = BATTLE_UI_HERO_X;
            for (i = 0; i < s_formationMateCount; i++)
            {
                j = s_formationMateActorID[i];
                actor = s_actorCommon[j];
//@#switch (BATTLE_PORTRAIT)
//@#{
//@#case "STANDARD":
                 int a = actor.m_animationID - ANIM_FIRST_LA;
                 s_actorBattlePortrait.DrawFrame(g, x + BATTLE_UI_HEROICON_OFFX, SCREEN_HEIGHT, FRAME_BATTLE_PORTRAIT_HERO_0 + a);
//@#break;
//@#case "STRING":
//@                DrawString(g, STR_SYSTEM_ALLY0 + actor.m_value[BV_ID], x + BATTLE_UI_HERO_ICON_X, BATTLE_UI_HERO_ICON_Y, ALIGN_LEFT_TOP,
//@                           COLOR_BLACK, COLOR_BLACK);
//@#break;
//@#}

                if ((s_battleUIFalg & BATTLE_UI_VALUE) != 0)
                {
                    RenderActorData(g, ACTOR_DATA_TYPE_HP, actor, x + BATTLE_UI_HERO_HP_X, BATTLE_UI_HERO_HP_Y, UI_ACTOR_VALUEBAR_W);
                    RenderActorData(g, ACTOR_DATA_TYPE_MP, actor, x + BATTLE_UI_HERO_MP_X, BATTLE_UI_HERO_MP_Y, UI_ACTOR_VALUEBAR_W);
//@#if NO_SP
//@#else
//@                    RenderActorSP(g, actor, x + BATTLE_UI_HERO_SP_X, BATTLE_UI_HERO_SP_Y);
//@#endif
                }
                x += BATTLE_UI_HERO_OFFX;
            }
        }
//@#if SHOW_BATTLE_UI_QUEUE
        RenderBattleQueue(g);
//@#endif
        s_wndBattleMain.Render(g);
        s_wndBattleDesc.Render(g);

        if ((s_battleUIFalg & BATTLE_UI_DIALOG) != 0)
        {
//@#if BATTLE_DIALOG_FILL_COLOR
//@            UtilFillRect(g, COLOR_BLACK, 0, SCREEN_HEIGHT - BATTLE_UI_HEIGHT, SCREEN_WIDTH, BATTLE_UI_HEIGHT);
//@#endif
            RenderDialog(g);
        }

        if (s_battlePara[BATTLE_PARA_SHOW_UI] == T_TRUE)
        {
            for (i = 0; i < s_actorBattleUICount; i++)
            {
                s_actorBattleUI[i].Update();
                s_actorBattleUI[i].DrawAnimation(g, 0, 0);
            }
        }

        if ((s_battleUIFalg & BATTLE_UI_WINDOW) != 0)
        {
            s_wnd.Render(g);
        }
    }

    static int s_battleAimCount = 0;
    static int s_battleAimLimit = 0;

    static int CalcAimLimit(int actionType, int actionID)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        int res = 0;
        switch (actionType)
        {
        case BATTLE_ACTION_TYPE_NORMAL:
            res = BATTLE_AIM_FOE | BATTLE_AIM_ALIVE;
            break;
        case BATTLE_ACTION_TYPE_SKILL:
            res = 0;
            switch (s_dbSkills[actionID][DB_SKILLS_USETARGET])
            {
            case ENUM_SKILLS_USETARGET_ALLY:
                res = BATTLE_AIM_ALLY | BATTLE_AIM_ALIVE;
                break;
            case ENUM_SKILLS_USETARGET_FOE:
                res = BATTLE_AIM_FOE | BATTLE_AIM_ALIVE;
                break;
            case ENUM_SKILLS_USETARGET_SELF:
                res = BATTLE_AIM_SELF | BATTLE_AIM_ALIVE;
                break;
            }
            for (int i = DB_SKILLS_PURPOSE0; i <= DB_ITEMS_PURPOSE1; i += 2)
            {
                if (s_dbSkills[actionID][i] == DB_EFFECT_SET_ENUMERATE_RISE)
                {
                    res |= BATTLE_AIM_DEAD;
                    res &= (~BATTLE_AIM_ALIVE);
                    break;
                }
            }
            break;
        case BATTLE_ACTION_TYPE_ITEM:
            s_battleAimLimit = 0;
            switch (s_dbItems[actionID][DB_ITEMS_USETARGET])
            {
            case ENUM_ITEMS_USETARGET_ALLY:

                //res = BATTLE_AIM_ALLY | BATTLE_AIM_SELF | BATTLE_AIM_ALIVE;
                res = BATTLE_AIM_ALLY | BATTLE_AIM_ALIVE;
                break;
            case ENUM_ITEMS_USETARGET_FOE:
                res = BATTLE_AIM_FOE | BATTLE_AIM_ALIVE;
                break;
            }
            for (int i = DB_ITEMS_PURPOSE0; i <= DB_ITEMS_PURPOSE1; i += 2)
            {
                if (s_dbItems[actionID][i] == DB_EFFECT_SET_ENUMERATE_RISE)
                {
                    res |= BATTLE_AIM_DEAD;
                    res &= (~BATTLE_AIM_ALIVE);
                    break;
                }
            }
            break;
        }
        return res;
    }

    static int GetSideCount(int side, boolean liveOnly)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        int res = 0;
        CActor actor;
        for (int i = 0; i < s_battleActorCount; i++)
        {
            actor = s_actorInBattle[i];
            if (liveOnly && ((actor.m_value[BV_STATE] & BAS_DEAD) != 0))
            {
                continue;
            }
            if (actor.m_value[BV_CAMP] == side)
            {
                res++;
            }

        }
        return res;
    }

    /**
     * 计算目标限定目标的,目标限定的类型存储在s_battleAimLimit变量中,
     * 目标限定类型可以是BATTLE_AIM_ALLY,BATTLE_AIM_FOE,BATTLE_AIM_SELF,
     * BATTLE_AIM_DEAD,BATTLE_AIM_ALIVE几种.
     * 这里所使用的目标限定是一个排除限制机制,当s_battleAimLimit包含了某一个限定,
     * 那么满足这个限定的actor不见得最终不被排除,但不满足的actor一定被排除.最终得到
     * 的集合是去掉了所有不满足限制条件的ACTOR合集后的补集.
     *
     * 举例:
     * 如现在只想选择活着的,我方队员.有组合A,B和C
     * A.BATTLE_AIM_ALLY,那么对方actor一定不能被选择,而我方ACTOR不管生死都可以被选择.
     * B.BATTLE_AIM_ALLY|BATTLE_AIM_ALIVE,那么敌方ACTOR,死亡ACTOR被排除,剩下的补集就是我们所需要.
     * C.BATTLE_AIM_ALLY|BATTLE_AIM_ALIVE|BATTLE_AIM_DEAD,由于BATTLE_AIM_ALIVE和
     *   BATTLE_AIM_DEAD的合集已经等于全集,那么无论BATTLE_AIM_ALLY起到的作用,这个规则
     *   行驶结束后的补集已经是一个空集.
     * @param type 类型
     * @param count 数量
     * @param bIncludeDead 包括死亡的
     */
    static int CalcAimSet(int aimLimit)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        int res = 0;
        CActor actor;

        int attackerSide = s_actorInBattle[s_battleActiveActorID].m_value[BV_CAMP];
        for (int i = 0; i < s_battleActorCount; i++)
        {
            actor = s_actorInBattle[i];
            res |= (1 << i);
            //逐个筛选条件
            //是否包含死亡
//@#if DEAD_CANNOT_USE_RELIFE_ITEM
//@            if ((aimLimit & BATTLE_AIM_DEAD) != 0)
//@            {
//@                if ((actor.m_value[BV_STATE] & BAS_DEAD) == 0)
//@                {
//@                    res &= ~(1 << i);
//@                    continue;
//@                }
//@            }
//@#endif
            //是否包含生存
            if ((aimLimit & BATTLE_AIM_ALIVE) != 0)
            {
                if ((actor.m_value[BV_STATE] & BAS_DEAD) != 0)
                {
                    res &= ~(1 << i);
                    continue;
                }
            }

            //是否可以选择自己
            if ((aimLimit & BATTLE_AIM_SELF) != 0)
            {
                if (i != s_battleActiveActorID)
                {
                    res &= ~(1 << i);
                    continue;
                }
            }

            //是否排除自己
            if ((aimLimit & BATTLE_AIM_EXCLUDE_SELF) != 0)
            {
                if (i == s_battleActiveActorID)
                {
                    res &= ~(1 << i);
                    continue;
                }
            }

            //是否和行动者同队
            if ((aimLimit & BATTLE_AIM_ALLY) != 0)
            {
                if (actor.m_value[BV_CAMP] != attackerSide)
                {
                    res &= ~(1 << i);
                    continue;
                }
            }

            //是否和行动者异队
            if ((aimLimit & BATTLE_AIM_FOE) != 0)
            {
                if (actor.m_value[BV_CAMP] == attackerSide)
                {
                    res &= ~(1 << i);
                    continue;
                }
            }
        }

        return res;
    }

    static int GetCampNumber(int camp)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        int res = 0;
        for (int i = 0; i < s_battleActorCount; i++)
        {
            if ((s_actorInBattle[i].m_value[BV_STATE] & BAS_DEAD) == 0)
            {
                if (s_actorInBattle[i].m_value[BV_CAMP] == camp)
                {
                    res++;
                }
            }
        }
        return res;
    }

    static int GetAllCampNum(int camp)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        int res = 0;
        for (int i = 0; i < s_battleActorCount; i++)
        {
            if (s_actorInBattle[i].m_value[BV_CAMP] == camp)
            {
                res++;
            }
        }
        return res;
    }

    static boolean s_rsEffectEnd;

    static boolean UpdateRSEffect()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        boolean end = true;
        for (int i = 0; i < s_battleActorCount; i++)
        {
            if (!s_actorEffInBattle[i].m_end)
            {
                s_actorEffInBattle[i].UpdateAnimation();
                end = false;
            }
        }
        if (!s_actorEffFullScreen.m_end)
        {
            s_actorEffFullScreen.UpdateAnimation();
            end = false;
        }
        return end;
    }

    static void RenderRSEffect(Graphics g, int cameraX, int cameraY)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Fight.h
        for (int i = 0; i < s_battleActorCount; i++)
        {
            if (!s_actorEffInBattle[i].m_end && s_actorEffInBattle[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] == T_TRUE)
            {
                s_actorEffInBattle[i].DrawAnimation(g, cameraX, cameraY);
            }
        }
        if (!s_actorEffFullScreen.m_end && s_actorEffFullScreen.m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] == T_TRUE)
        {
            s_actorEffFullScreen.DrawAnimation(g, cameraX, cameraY);
        }
    }

//@@
//@@..\CGame\CGame_FightData.h

    static void ClearEffectValue()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_FightData.h
        for (int i = 0; i < AMOUNT_DB_EFFECT_SET; i++)
        {
            s_effectValue[i] = 0;
        }
    }

    /**
     * 计算技能的伤害效果
     */
    static void CalcSkillEffect(int skillID, CActor actor)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_FightData.h
        int effectID = 0;
        int effectValue = 0;
        int effectPro = 0;
        int random;

        ClearEffectValue();

        for (int i = DB_SKILLS_PURPOSE0; i <= DB_SKILLS_PURPOSE_LAST; i += 3)
        {
            effectID = s_dbSkills[skillID][i];
            effectValue = s_dbSkills[skillID][i + 1];
            effectPro = s_dbSkills[skillID][i + 2];
            random = UtilRandom(0, 100);
            if (effectID != DB_EFFECT_SET_ENUMERATE_NONE && random < effectPro)
            {
                s_effectValue[effectID] = effectValue;
            }
        }

        //筛选
        //复活?
        if (s_effectValue[DB_EFFECT_SET_ENUMERATE_RISE] > 0)
        {
            if ((actor.m_value[BV_STATE] & BAS_DEAD) == 0)
            {
                //没死
                s_effectValue[DB_EFFECT_SET_ENUMERATE_RISE] = 0;
            }
        }

        //回HP?
        if (s_effectValue[DB_EFFECT_SET_ENUMERATE_HP] > 0 || s_effectValue[DB_EFFECT_SET_ENUMERATE_HPP] > 0)
        {
            if (actor.m_value[BV_HP] == actor.m_value[BV_HPMAX])
            {
                //没受伤
                s_effectValue[DB_EFFECT_SET_ENUMERATE_HP] = 0;
                s_effectValue[DB_EFFECT_SET_ENUMERATE_HPP] = 0;
            }
        }

        //回MP?
        if (s_effectValue[DB_EFFECT_SET_ENUMERATE_MP] > 0 || s_effectValue[DB_EFFECT_SET_ENUMERATE_MPP] > 0)
        {
            if (actor.m_value[BV_MP] == actor.m_value[BV_MPMAX])
            {
                //没受伤
                s_effectValue[DB_EFFECT_SET_ENUMERATE_MP] = 0;
                s_effectValue[DB_EFFECT_SET_ENUMERATE_MPP] = 0;
            }
        }
    }

    static void CalcDamageValue(int activeActorID, int feedbackActorID, int actionType, int actionID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_FightData.h
        CActor actorActive, actorFeedback;
        int tmp = 0;
        actorActive = s_actorInBattle[activeActorID]; //加害方
        actorFeedback = s_actorInBattle[feedbackActorID]; //受害方

        if (s_battleScriptDamageValue >= 0)
        {
            s_effectValue[DB_EFFECT_SET_ENUMERATE_DAMAGE] = s_battleScriptDamageValue;
        }
        else
        {
            int ap, dp = actorFeedback.m_value[BV_DP] + actorFeedback.m_buff[DB_EFFECT_SET_ENUMERATE_DEFENSE];
            switch (actionType)
            {
            case BATTLE_ACTION_TYPE_NORMAL:
                ap = actorActive.m_value[BV_PHYDMG] + actorActive.m_buff[DB_EFFECT_SET_ENUMERATE_NAP];
                tmp = ap * 200 / (200 + dp);
                //数值写入s_effectValue
                s_effectValue[DB_EFFECT_SET_ENUMERATE_DAMAGE] = tmp;
                break;
            case BATTLE_ACTION_TYPE_SKILL:
                ap = actorActive.m_value[BV_PSYDMG] + actorActive.m_buff[DB_EFFECT_SET_ENUMERATE_SAP];
                //计算伤害
                CalcSkillEffect(s_battleActionID, actorFeedback);
                tmp = s_effectValue[DB_EFFECT_SET_ENUMERATE_DAMAGE] + ap * s_effectValue[DB_EFFECT_SET_ENUMERATE_DAMAGEP] / 100;
                if (tmp > 0)
                {
                    tmp = tmp * 200 / (200 + dp);
                    s_effectValue[DB_EFFECT_SET_ENUMERATE_DAMAGE] = tmp;
                }
                break;
            case BATTLE_ACTION_TYPE_ITEM:
                ClearEffectValue();
                CalcItemUsed(actionID, actorFeedback);
                break;
//            case BATTLE_ACTION_TYPE_REFLECT:
//                ClearSkillEffect();
//                s_effectValue[DB_EFFECT_SET_ENUMERATE_DAMAGE] = actorActive.m_value[BV_DMG_VALUE] * actorActive.m_buff[DB_EFFECT_SET_ENUMERATE_REFLECT] / 100;
//                break;
            }
            actorActive.m_value[BV_SP] = Math.min(actorActive.m_value[BV_SP], actorActive.m_value[BV_SPMAX]);
        }
    }

    static boolean s_bSPIncrease = false;

    static void CalcDamageRate(int activeActorID, int feedbackActorID, int actionType, int skillID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_FightData.h
        CActor actorA = s_actorInBattle[activeActorID];
        CActor actorD = s_actorInBattle[feedbackActorID];
        int damageMultiple = UtilRandom(DAMAGE_RANDOM_MIN, DAMAGE_RANDOM_MAX);
        int hitRate = s_battleScriptDamageValue >= 0 ? 100 : UtilRandom(0, 100); //命中
        int criticalRate = s_battleScriptDamageValue >= 0 ? 0 : UtilRandom(0, 100); //暴击
        if (actorD.m_value[BV_DMG_VALUE] < 0)
        {
            return;
        }
        switch (actionType)
        {
        case BATTLE_ACTION_TYPE_NORMAL:
            actorD.m_value[BV_DMG_TYPE] = DAMAGE_TYPE_NORMAL;
            break;
        case BATTLE_ACTION_TYPE_SKILL:
            actorD.m_value[BV_DMG_TYPE] = DAMAGE_TYPE_SKILL;
            hitRate = 100;
            if (s_effectValue[DB_EFFECT_SET_ENUMERATE_DAMAGE] <= 0)
            {
                criticalRate = 100;
            }
            break;
        case BATTLE_ACTION_TYPE_ITEM:
            actorD.m_value[BV_DMG_TYPE] = DAMAGE_TYPE_ITEM;
            hitRate = 100;
            criticalRate = 100;
            break;
        }
        if (hitRate < Math.min(99, (actorD.m_buff[DB_EFFECT_SET_ENUMERATE_BUF_PARALYZE] > 0 ?
                                    0 : actorD.m_value[BV_DODGE] + actorD.m_buff[DB_EFFECT_SET_ENUMERATE_DODGE])))
        { //攻击miss
            actorD.m_value[BV_DMG_VALUE] = 0;
            actorD.m_value[BV_DMG_TYPE] = DAMAGE_TYPE_MISS;
        }
        else
        { //命中
            damageMultiple /= (actorD.m_value[BV_ACTION] == BATTLE_ACTION_GUARD ? 2 : 1);
            if (criticalRate < Math.min(99, actorA.m_value[BV_CH] + actorA.m_buff[DB_EFFECT_SET_ENUMERATE_CHP]) + s_dbSkills[skillID][DB_SKILLS_CRITICAL])
            { //暴击
                if (actorD.m_value[BV_CAMP] == CAMP_FOE)
                {
                    damageMultiple *= 4;
                }
                else
                {
                    damageMultiple *= 3;
                }
                actorD.m_value[BV_DMG_TYPE] = DAMAGE_TYPE_CRITICAL;
//                ShockBattleCamera(3, BATTLE_CAMERA_CRITICAL_SPEED_X, BATTLE_CAMERA_CRITICAL_SPEED_Y); //取消暴击震屏
            }
            else
            {
                damageMultiple *= 2;
            }
            actorD.m_value[BV_DMG_VALUE] = actorD.m_value[BV_DMG_VALUE] * damageMultiple / 200;
            actorD.m_value[BV_HP] -= actorD.m_value[BV_DMG_VALUE];
            actorD.m_value[BV_HP] = Math.max(actorD.m_value[BV_HP], 0);
            actorD.CalcSPIncrease();
            s_bSPIncrease = (actorA.m_buff[DB_EFFECT_SET_ENUMERATE_SP_FORBID] == 0);
        }
    }

//@@
//@@..\CGame\CGame_BattleUI.h
    static void UpdateIGMBattleMain(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_BattleUI.h
        boolean showUI = true;
        s_actorHalo.UpdateAnimation();
        switch (CWnd.s_UICtrlMode)
        {
        case UI_CONTROL_NONE:
            SetRoundState(RS_ROUNDEND);
            return;
        case UI_CONTROL_MANUAL:
            int menuSlot = wnd.m_menuSlot;
            if (IsKeySinglePressed(GKEY_MOVEUP))
            {
                switch (s_menuSlot[menuSlot])
                {
//@#if EXIST_SPECIAL
//@                case BATTLE_MI_SPECIAL:
//@                    s_menuSlot[menuSlot] = BATTLE_MI_SHOP;
//@                    break;
//@
//@                case BATTLE_MI_ATT:
//@                    s_menuSlot[menuSlot] = BATTLE_MI_SPECIAL;
//@                    break;
//@                case BATTLE_MI_SKILL:
//@                    s_menuSlot[menuSlot] = BATTLE_MI_SPECIAL;
//@                    break;
//@                case BATTLE_MI_ITEM:
//@                    s_menuSlot[menuSlot] = BATTLE_MI_SPECIAL;
//@                    break;
//@#endif
                case BATTLE_MI_SHOP:
                    s_menuSlot[menuSlot] = BATTLE_MI_ATT;
                    break;
                }
            }
             if (IsKeySinglePressed(GKEY_MOVEDOWN))
            {
                switch (s_menuSlot[menuSlot])
                {
//@#if EXIST_SPECIAL
//@                case BATTLE_MI_SHOP:
//@                    s_menuSlot[menuSlot] = BATTLE_MI_SPECIAL;
//@                    break;
//@                case BATTLE_MI_SPECIAL:
//@                    s_menuSlot[menuSlot] = BATTLE_MI_ATT;
//@                    break;
//@
//@#endif
                case BATTLE_MI_ATT:
                    s_menuSlot[menuSlot] = BATTLE_MI_SHOP;
                    break;
                case BATTLE_MI_SKILL:
                    s_menuSlot[menuSlot] = BATTLE_MI_SHOP;
                    break;
                case BATTLE_MI_ITEM:
                    s_menuSlot[menuSlot] = BATTLE_MI_SHOP;
                    break;
                }
            }
             if (IsKeySinglePressed(GKEY_MOVELEFT))
            {
                switch (s_menuSlot[menuSlot])
                {
                case BATTLE_MI_ATT:
                    s_menuSlot[menuSlot] = BATTLE_MI_SKILL;
                    break;
                case BATTLE_MI_SHOP:
                    s_menuSlot[menuSlot] = BATTLE_MI_SKILL;
                    break;
//@#if EXIST_SPECIAL
//@                case BATTLE_MI_SPECIAL:
//@                    s_menuSlot[menuSlot] = BATTLE_MI_SKILL;
//@                    break;
//@#endif
                case BATTLE_MI_ITEM:
                    s_menuSlot[menuSlot] = BATTLE_MI_ATT;
                    break;
                case BATTLE_MI_SKILL:
                    s_menuSlot[menuSlot] = BATTLE_MI_ITEM;
                    break;
                }
            }
             if (IsKeySinglePressed(GKEY_MOVERIGHT))
            {
                switch (s_menuSlot[menuSlot])
                {
                case BATTLE_MI_ATT:
                    s_menuSlot[menuSlot] = BATTLE_MI_ITEM;
                    break;
                case BATTLE_MI_SKILL:
                    s_menuSlot[menuSlot] = BATTLE_MI_ATT;
                    break;
//@#if EXIST_SPECIAL
//@                case BATTLE_MI_SPECIAL:
//@                    s_menuSlot[menuSlot] = BATTLE_MI_ITEM;
//@                    break;
//@#endif
                case BATTLE_MI_SHOP:
                    s_menuSlot[menuSlot] = BATTLE_MI_ITEM;
                    break;
                case BATTLE_MI_ITEM:
                    s_menuSlot[menuSlot] = BATTLE_MI_SKILL;
                    break;
                }
            }
//@#if EXIST_KEY0_SHOP
//@            if (IsKeySinglePressed(GKEY_0))
//@            { //0键进入商店
//@                wnd.m_select = BATTLE_MI_SHOP;
//@            }
//@#endif
            wnd.m_show = true;
            break;
        case UI_CONTROL_AI:
        case UI_CONTROL_SCRIPT:
            s_battleAnimFalg = BATTLE_ANIM_BG | BATTLE_ANIM_ACTOR | BATTLE_ANIM_INFO;
            showUI = false;
            break;
        }

        if (s_wndBattleAction.m_state == WND_STATE_RETURN)
        {
            s_wndBattleAction.m_state = WND_STATE_WAIT;
            s_wndBattleAction.RemoveAllChildren();
            s_wndBattleAction.Free();
            s_wndBattleAction.m_parent = s_wndBattleMain;
            return;
        }

        if (wnd.m_select >= 0)
        {
            wnd.m_show = false;
            if (s_battleLimit[BATTLE_LIMIT_ACTIONTYPE] >= 0 && wnd.m_select != s_battleLimit[BATTLE_LIMIT_ACTIONTYPE])
            {
                //不正确进入提示
                ShowBattleLimitInfo();
            }
            else
            {
                //对操作的响应
                int skillCount = 0;
                switch (wnd.m_select)
                {
                case BATTLE_MI_SPECIAL:
//@#if SMS
//@                    ReleaseTempEntity();
//@                    CEntity entity = CEntity.GetInstance();
//@                    entity.m_id = SMS_ELIMINATE_ID;
//@                    entity.m_count = 1;
//@                    s_tempList.InsertAfter(entity);
//@                    s_wnd = CWnd.GetInstance();
//@                    s_wnd.Init(WND_SMS_MESSAGE, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, true);
//@                    s_wnd.m_isTop = true;
//@                    s_wnd.m_curSelect = entity;
//@                    s_wnd.DoModule();
//@                    SetRoundState(RS_KILL_ALL);
//@#else
                    SetRoundState(RS_UI);
//@#endif
                    break;
                case BATTLE_MI_ATT:
                    s_battleActionType = BATTLE_ACTION_TYPE_NORMAL;
                    if (s_actorBattleActivated.m_buff[DB_EFFECT_SET_ENUMERATE_BUF_CHAOS] > 0)
                    {
                        int aimCamp = 0;
                        s_battleAimLimit = BATTLE_AIM_EXCLUDE_SELF | BATTLE_AIM_ALIVE;
                        if (GetCampNumber(s_actorBattleActivated.m_value[BV_CAMP]) > 1)
                        {
                            aimCamp = BATTLE_AIM_ALLY;
                        }
                        else
                        {
                            aimCamp = BATTLE_AIM_FOE;
                        }
                        s_battleAimLimit |= aimCamp;
                    }
                    else
                    {
                        s_battleAimLimit = BATTLE_AIM_FOE | BATTLE_AIM_ALIVE;
                    }
                    if (s_actorBattleActivated.m_value[BV_CAMP] == CAMP_ALLY)
                    {
                        s_actorTarget.SetAction(ACTION_UI_TARGET_FOE);
                    }
                    else
                    {
                        s_actorTarget.SetAction(ACTION_UI_TARGET_ALLY);
                    }
                    s_battleAimSet = CalcAimSet(s_battleAimLimit);
                    s_battleAimID = s_battleActiveActorID;
                    s_battleAimCount = s_actorBattleActivated.m_value[BV_TARGET_COUNT];
                    s_actorBattleActivated.m_value[BV_ACTION] = BATTLE_ACTION_TYPE_NORMAL;
                    RefreshAim(1, true);
                    SetRoundState(RS_AIM);
                    break;
                case BATTLE_MI_SKILL:
                    s_battleActionType = BATTLE_ACTION_TYPE_SKILL;
                    skillCount = s_actorBattleActivated.GetSkillCount();
                    if (skillCount > 0)
                    {
                        s_wndBattleAction.Init(WND_BATTLE_SKILL, BATTLE_UI_SUBMENU_X, BATTLE_UI_SUBMENU_Y, BATTLE_UI_SUBMENU_W, BATTLE_UI_SUBMENU_H, true);
                        s_wndBattleAction.m_count = skillCount;
                        s_wndBattleAction.DoModule();
                        s_wndBattleAction.m_show = showUI;
                    }
                    else
                    {
                        s_wndBattleAction.Init(WND_MESSAGE, IGM_MESSAGE_X, IGM_MESSAGE_Y, IGM_MESSAGE_W, IGM_MESSAGE_H, false);
                        ResizeWndWithTitle(s_wndBattleAction, STR_SYSTEM_NOSKILL, 0);
                        s_wndBattleAction.DoModule(MESSAGE_SHOW_TIME);
                    }
                    s_actorBattleActivated.m_value[BV_ACTION] = BATTLE_ACTION_TYPE_SKILL;
                    break;
                case BATTLE_MI_SHOP:
                    CEntity entity0 = s_entityList[ENTITY_LIST_BUY];
                    CEntity entity1 = null;
                    for (int i = 0; i < s_dbItems.length; i++)
                    {
                        if (s_dbItems[i][DB_ITEMS_WHENCANUSE] == ENUM_ITEMS_WHENCANUSE_FIGHT
                            || s_dbItems[i][DB_ITEMS_WHENCANUSE] == ENUM_ITEMS_WHENCANUSE_ALL)
                        {
                            entity1 = CEntity.GetInstance();
                            entity1.m_id = i;
                            entity1.m_type = DEFINE_HOLD_TYPE_BATTLE;
                            entity1.m_count = GAME_ICON_NUM_MAX;
                            entity1.m_icon = s_dbItems[i][DB_ITEMS_ICON];
                            entity0.InsertAfter(entity1);
                            entity0 = entity0.m_next;
                        }
                    }
                    s_wndBattleAction.Init(WND_SHOP, IGM_X, IGM_Y, IGM_W, IGM_H, true);
                    s_wndBattleAction.InitData(WND_DATA_SIZE);
                    s_wndBattleAction.m_data[SHOP_DATA_TYPE] = IGM_BATTLE_SHOP;
                    s_menuSlot[s_wndBattleAction.m_menuSlot] = 0;
                    s_wndBattleAction.DoModule();
                    break;
                case BATTLE_MI_ITEM:
                    s_battleActionType = BATTLE_ACTION_TYPE_ITEM;
                    int itemCount = s_entityList[ENTITY_LIST_BATTLE].GetCount() - 1;
                    if (itemCount > 0)
                    {
                        s_wndBattleAction.Init(WND_BATTLE_ITEM, BATTLE_UI_SUBMENU_X, BATTLE_UI_SUBMENU_Y, BATTLE_UI_SUBMENU_W, BATTLE_UI_SUBMENU_H, true);
                        s_wndBattleAction.m_count = s_entityList[ENTITY_LIST_BATTLE].m_next.GetCount();
                        s_wndBattleAction.DoModule();
                        s_wndBattleAction.m_show = showUI;
                    }
                    else
                    {
                        s_wndBattleAction.Init(WND_MESSAGE, IGM_MESSAGE_X, IGM_MESSAGE_Y, IGM_MESSAGE_W, IGM_MESSAGE_H, false);
                        ResizeWndWithTitle(s_wndBattleAction, STR_SYSTEM_NOITEM, 0);
                        s_wndBattleAction.DoModule();
                    }
                    s_actorBattleActivated.m_value[BV_ACTION] = BATTLE_ACTION_TYPE_ITEM;
                    break;
//                case BATTLE_MI_GUARD:
//                    s_actorBattleActivated.m_value[BV_ACTION] = BATTLE_ACTION_GUARD;
//                    s_actorBattleActivated.SetAction(ACTION_BATTLE_GUARD);
//                    SetRoundState(RS_ROUNDEND);
//                    break;
//                case BATTLE_MI_ESCAPE:
//                    if (s_battlePara[BATTLE_PARA_CAN_ESCAPE] > 0)
//                    {
//                        if (UtilRandom(0, 100) >= BATTLE_ESCAPE_PERCENT)
//                        {
//                            s_battleActionType = BATTLE_ACTION_ESCAPE_SCUU;
//                            s_uiContentID = STR_SYSTEM_ESCAPE_WIN;
//                        }
//                        else
//                        {
//                            s_battleActionType = BATTLE_ACTION_ESCAPE_FAIL;
//                            s_uiContentID = STR_SYSTEM_ESCAPE_LOSE;
//                        }
//                        s_menuSlot[wnd.m_menuSlot] = BATTLE_MI_ATT;
//                    }
//                    else
//                    {
//                        s_battleActionType = BATTLE_ACTION_ESCAPE_DISABLE;
//                        s_uiContentID = STR_SYSTEM_ESCAPE_CANNOT;
//                    }
//                    s_actorBattleActivated.m_value[BV_ACTION] = s_battleActionType;
//                    s_uiMessageTimer = 20;
//                    InitUI(UI_TYPE_MESSAGEBOX, UI_MESSAGE_MAX_LINE, EP_BATTLE);
//                    SetRoundState(RS_ESCAPE);
//                    break;
                }
            }
        }
    }

    static void RenderIGMBattleMain(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_BattleUI.h
        int curItem = s_menuSlot[wnd.m_menuSlot];
//@#if FILL_BATTLE_MAIN_MENU
//@        for (int i = 0; i < wnd.m_showNum; i++)
//@        {
//@            DrawString(g, STR_SYSTEM_BATTLE_MAIN_ATT+i,BATTLE_UI_MAINMENU_X[i], BATTLE_UI_MAINMENU_Y[i], ALIGN_LEFT_TOP,
//@                              COLOR_YELLOW, COLOR_BLACK);
//@        }
//@#else
        s_actorUI.DrawFrame(g, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT - BATTLE_UI_SKILLUIBOX_Y) >> 1,
                            FRAME_UI_BATTLESKILLUI);
        for (int i = 0; i < wnd.m_showNum; i++)
        {
            s_actorUI.DrawFrame(g, BATTLE_UI_MAINMENU_X[i], BATTLE_UI_MAINMENU_Y[i], FRAME_UI_BC_ATTACK + i);
        }
        s_actorHalo.SetPos(BATTLE_UI_MAINMENU_X[curItem] + BATTLE_UI_MAINMENU_CUESOR_X, BATTLE_UI_MAINMENU_Y[curItem] + BATTLE_UI_MAINMENU_CUESOR_Y);
        s_actorUI.m_repeat = true;
        s_actorHalo.DrawAnimation(g, 0, 0);
//@#endif
        s_actorUI.DrawFrame(g, BATTLE_UI_MAINMENU_X[curItem] + BATTLE_UI_MAINMENU_CUESOR_X, BATTLE_UI_MAINMENU_Y[curItem] + BATTLE_UI_MAINMENU_CUESOR_Y,
                            FRAME_UI_CURSOR);
    }

    /**
     * 战斗技能
     *
     * @param wnd CWnd
     */
    static void UpdateBattleSkill(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_BattleUI.h
        CWnd wndList = wnd.m_children[0];
        CWnd wndMessage = wnd.m_children[1];
        CWnd wndDesc = wnd.m_children[2];
        s_battleAnimFalg = BATTLE_ANIM_NONE;

        UpdateWndTextPos(wnd, BATTLE_SKILL_NAME_W, IGM_LIST_TEXT_WAIT_TIME);

        if (wndList.m_select >= 0)
        {
            s_battleActionID = wndList.m_curSelect.m_id;
            CEntity limitEntity = s_actorBattleActivated.m_buff[DB_EFFECT_SET_ENUMERATE_SKILL_EX] > 0 ?
                                  s_actorBattleActivated.m_entitySkillEx.GetNext(s_battleLimit[BATTLE_LIMIT_ACTIONID] + 1) :
                                  s_actorBattleActivated.m_entitySkill.GetNext(s_battleLimit[BATTLE_LIMIT_ACTIONID] + 1);
            if (s_battleLimit[BATTLE_LIMIT_ACTIONID] >= 0 && s_battleActionID != limitEntity.m_id)
            {
                ShowBattleLimitInfo();
            }
            else
            {
                int mpCost = s_dbSkills[s_battleActionID][DB_SKILLS_USEMP];
                int spCost = s_dbSkills[s_battleActionID][DB_SKILLS_USESP];
                if (wndList.m_curSelect.m_coolDown > 0)
                {
                    ResizeWndWithTitle(wndMessage, STR_SYSTEM_CD_TIME, 0);
                    wndMessage.DoModule(MESSAGE_SHOW_TIME);
                    return;
                }
                if (s_actorBattleActivated.m_value[BV_SP] < spCost)
                {
                    ResizeWndWithTitle(wndMessage, STR_SYSTEM_LESSSP, 0);
                    wndMessage.DoModule(MESSAGE_SHOW_TIME);
                    return;
                }
                if (mpCost < 0)
                {
                    if (s_gameData[DEFINE_GAME_DATA_MONEY] >= Math.abs(mpCost))
                    {
                        SelectSkillSucc(wnd, wndList);
                    }
                    else
                    {
                        ResizeWndWithTitle(wndMessage, STR_SYSTEM_LESSMONEY, 0);
                        wndMessage.DoModule(MESSAGE_SHOW_TIME);
                    }
                }
                else
                {
                    if (s_actorBattleActivated.m_value[BV_MP] >= mpCost)
                    {
                        SelectSkillSucc(wnd, wndList);
                    }
                    else
                    {
                        ResizeWndWithTitle(wndMessage, STR_SYSTEM_LESSMP, 0);
                        wndMessage.DoModule(MESSAGE_SHOW_TIME);
                    }
                }
            }
        }
        else if (wndList.m_state == WND_STATE_PAGEUP || wndList.m_state == WND_STATE_PAGEDOWN)
        {
            wndDesc.m_data[1] = wndList.m_curSelect.m_id;
            wnd.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_SKILL_NAME_DESC_0 + wndList.m_curSelect.m_id), BATTLE_SKILL_NAME_W, SCROLL_DIR_LEFT));
            wndDesc.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_SKILLS_DESC_0 + wndList.m_curSelect.m_id), wndDesc.m_rect[RECT_H] - (IGM_DESC_TEXT_Y << 1),
                                             SCROLL_DIR_UP));
            return;
        }
        else if (wndList.m_state == WND_STATE_RETURN)
        {
            wnd.Close();
            wnd.RemoveAllChildren();
            wnd.DeleteActiveWnd();
            return;
        }
//@#if SMS
//@#if TB_SMS
//@#else
//@        if (wndMessage.m_state == WND_STATE_RETURN && wndMessage.m_title == STR_SYSTEM_LESSMONEY
//@            && s_variables[SCRIPT_VARIABLE_SMS_PARA0 + ENUM_SMS_PURPOSE0_ACTIVE] > 0)
//@        {
//@            wndMessage.m_state = WND_STATE_WAIT;
//@            ReleaseTempEntity();
//@            CEntity entity = CEntity.GetInstance();
//@            entity.m_id = 2;
//@            entity.m_count = 1;
//@            s_tempList.InsertAfter(entity);
//@            s_wnd = CWnd.GetInstance();
//@            s_wnd.Init(WND_SMS_MESSAGE, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, true);
//@            s_wnd.m_isTop = true;
//@            s_wnd.m_curSelect = entity;
//@            s_wnd.DoModule();
//@            SetRoundState(RS_UI);
//@        }
//@#endif
//@#endif
    }

    static void SelectSkillSucc(CWnd wnd, CWnd wndList)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_BattleUI.h
        s_battleAimLimit = CalcAimLimit(BATTLE_ACTION_TYPE_SKILL, s_battleActionID);
        s_battleAimSet = CalcAimSet(s_battleAimLimit);
        s_battleAimCount = s_dbSkills[s_battleActionID][DB_SKILLS_TARGETCOUNT];
        s_battleAimID = s_battleActiveActorID;
        int action = (s_battleAimLimit & BATTLE_AIM_FOE) == 0 ? ACTION_UI_TARGET_ALLY : ACTION_UI_TARGET_FOE;
        s_actorTarget.SetAction(action);
        wnd.Close();
        wnd.RemoveAllChildren();
        wnd.DeleteActiveWnd();
        RefreshAim(1, true);
        SetRoundState(RS_AIM);
    }

    static CEntity GetBattleSkill()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_BattleUI.h
        CEntity res = s_actorBattleActivated.m_buff[DB_EFFECT_SET_ENUMERATE_SKILL_EX] > 0 ?
                      CGame.s_actorBattleActivated.m_entitySkillEx.m_next : CGame.s_actorBattleActivated.m_entitySkill.m_next;
        return res;
    }

    static void UpdateBattleItem(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_BattleUI.h
        CWnd wndItemList = wnd.m_children[0];
        CWnd wndDesc = wnd.m_children[1];
        CWnd wndMessage = wnd.m_children[2];
        s_battleAnimFalg = BATTLE_ANIM_NONE;

        UpdateWndTextPos(wnd, BATTLE_ITEM_RECT_W, IGM_LIST_TEXT_WAIT_TIME);

        wndDesc.m_data[1] = wndItemList.m_curSelect.m_id;

        if (wndItemList.m_select >= 0)
        {
            s_battleActionID = wndItemList.m_curSelect.m_id;
            s_battleAimLimit = CalcAimLimit(BATTLE_ACTION_TYPE_ITEM, s_battleActionID);
            s_battleAimSet = CalcAimSet(s_battleAimLimit);
            if (s_battleAimSet == 0)
            {
                //没有可用的对象
                ResizeWndWithTitle(wndMessage, STR_SYSTEM_BATTLE_USE_ONLY, 0);
                wndMessage.DoModule();
            }
            else
            {
                s_battleAimCount = s_dbItems[wndItemList.m_curSelect.m_id][DB_ITEMS_TARGETCOUNT];
                s_battleAimID = s_battleActiveActorID;
                RefreshAim(1, true);
                s_actorTarget.SetAction(ACTION_UI_TARGET_ALLY);
                SetRoundState(RS_AIM);
                SetGameEP(EP_BATTLE);
                wnd.Close();
                wnd.RemoveAllChildren();
                wnd.DeleteActiveWnd();
                SetRoundState(RS_AIM);
            }
            return;
        }
        else if (wndItemList.m_state == WND_STATE_RETURN)
        {
            wnd.Close();
            wnd.RemoveAllChildren();
            wnd.DeleteActiveWnd();
        }
    }

    static void RenderBattleSkill(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_BattleUI.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        CWnd wndList = wnd.m_children[0];
        // ~~ 原来没有减
        int curItem = s_menuSlot[wndList.m_menuSlot] - wndList.m_listStartPos;
        int curY = y + BATTLE_SKILL_ICON_Y;

        UtilDrawMenuBox(g, COLOR_LIGHT_GREEN, x + BATTLE_ITEM_RECT_X, y + BATTLE_ITEM_RECT_Y, BATTLE_ITEM_RECT_W, BATTLE_ITEM_RECT_H,
                        MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1);
        for (int i = 0; i < wndList.m_showNum; i++)
        {
            s_actorUI.DrawFrame(g, x + BATTLE_SKILL_ICON_X, curY, FRAME_UI_ICON_BG);
            curY += BATTLE_SUB_NAME_H;
        }
        RenderEntityList(g, wndList.m_showFirst, STR_SYSTEM_SKILL_NAME_DESC_0, true, wndList.m_showNum, curItem, ENTITY_INFO_TYPE_COUNT_NULL,
                         x + BATTLE_SUB_NAME_X, y + BATTLE_SUB_NAME_Y, BATTLE_SUB_NAME_W, BATTLE_SUB_NAME_H, ENTITY_ENABLE_TYPE_NONE, BATTLE_SKILL_NAME_W,
                         wnd.m_timer);
        RenderScrollBar(g, FRAME_UI_SCROLL_BAR_TOP, x + BATTLE_SKILL_SCROLL_BAR_X, y + BATTLE_SKILL_SCROLL_BAR_Y, BATTLE_SKILL_SCROLL_BAR_H, curItem,
                        wnd.m_count);
        s_actorUI.DrawFrame(g, x + BATTLE_SKILL_CURSOR_X, y + BATTLE_SKILL_CURSOR_Y + (curItem) * BATTLE_SUB_NAME_H, FRAME_UI_CURSOR);
//@#if JAR_LIMIT_300K
//@        RenderIGMCommon(g, x, y, w, h, STR_SYSTEM_IGM_SK);
//@#else
        RenderIGMCommon(g, x, y, w, h, FRAME_UI_TITLE_SKILL);
//@#endif
    }

    static void RenderBattleItem(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_BattleUI.h
        CWnd itemList = wnd.m_children[0];
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
//        int foucsID = s_menuSlot[wnd.m_menuSlot] - itemList.m_listStartPos;
        UtilDrawMenuBox(g, COLOR_LIGHT_GREEN, x + BATTLE_ITEM_RECT_X, y + BATTLE_ITEM_RECT_Y, BATTLE_ITEM_RECT_W, BATTLE_ITEM_RECT_H,
                        MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1);
//@#if JAR_LIMIT_300K
//@        RenderIGMCommon(g, x, y, w, h, STR_SYSTEM_IGM_ITEM);
//@#else
        RenderIGMCommon(g, x, y, w, h, FRAME_UI_TITLE_ITEM);
//@#endif
    }

    static void RenderBattleTip(Graphics g, CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_BattleUI.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int offX = w / (wnd.m_dataCount+1)+5;
        int itemX = x+offX-10;

        for (int i = 0; i < wnd.m_dataCount; i++)
        {
            DrawString(g, wnd.m_data[i], itemX, y + BATTLE_TIP_TEXT_Y, ALIGN_HCENTER_TOP, COLOR_BLACK, COLOR_BLACK);
            itemX += offX;
        }
    }
//@@
//@@..\CGame\CGame_RoundParallel.h

    /**
     * RPHurtAnimStart
     * 战斗动画中被攻击动画的部分,在被攻击动画开始之前,计算伤害,根据伤害的类型决定动画.
     * 动画开始后,这个并行任务产生并观察被攻击actor,伤害数字,攻击特效等子并行任务,当这些子并行任务全部
     * 完成,该并行任务完成.
     *
     * @param parallel CParallel
     * @param attackerID int
     * @param actionType int
     * @param actionID int
     * @param aimID int
     * @param aimCount int
     */
    static void RPHurtAnimStart(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        int damageType;
        CActor feedbackActor = s_actorInBattle[parallel.m_objectID[0]];
        feedbackActor.m_repeat = false;
        damageType = feedbackActor.m_value[BV_DMG_TYPE];
        switch (damageType)
        {
        case DAMAGE_TYPE_MISS:
            feedbackActor.SetAction(ACTION_BATTLE_DODGE);
            break;
        case DAMAGE_TYPE_AUTO:
            feedbackActor.SetAction(ACTION_BATTLE_HURT);
            break;
        case DAMAGE_TYPE_CRITICAL:
        case DAMAGE_TYPE_NORMAL:
        case DAMAGE_TYPE_SKILL:
            if (parallel.m_effectValue[DB_EFFECT_SET_ENUMERATE_DAMAGE] > 0)
            {
                if (feedbackActor.m_value[BV_ACTION] != BATTLE_ACTION_GUARD)
                {
                    feedbackActor.SetAction(ACTION_BATTLE_HURT);
                }
                else
                {
                    feedbackActor.SetAction(ACTION_BATTLE_G_HURT);
                }
            }
            break;
        case DAMAGE_TYPE_ITEM:
            feedbackActor.SetAction(ACTION_BATTLE_WAIT);
            break;
        }
    }

    /**
     * AttackSet并行任务的启动工作
     * AttackSet并行任务会开始若干个SingleAttack子并行任务
     * AttackSet并行任务等待这些子并行任务结束.
     *
     * @param parallel CParallel
     */
    static void RPAttackSetStart(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        CParallel childPara, childBuff;
        childPara = CParallel.GetInstance();
        childPara.SetActionInfo(parallel.m_attackerID, parallel.m_actionType, parallel.m_actionID, parallel.m_objectID[0], parallel.m_objectCount);
        childPara.Init(PARALLEL_TYPE_SINGLE_ATTACK, PARALLEL_CONDITION_AUTO);
        childPara.m_infoID = parallel.m_infoID;
        parallel.AddChild(childPara);
        childBuff = CParallel.GetInstance();
        childBuff.Init(PARALLEL_TYPE_BUFFSET, PARALLEL_CONDITION_MANUAL);
        childBuff.AddObject(parallel.m_attackerID);
        childBuff.m_state = PARALLEL_STATE_END;
        parallel.AddChild(childBuff);
    }

    /**
     * 在这个并行任务里,开始攻击动画,FeedbackSet等几个子并行.
     *
     * @param parallel CParallel
     */
    static void RPSingleAttackStart(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        //加入并开始一个攻击动画子任务.
        int i;
        int count = parallel.m_objectCount == 0 ? BATTLE_ENEMY_MAX : parallel.m_objectCount;
        CParallel childAttackAnim = CParallel.GetInstance();
        childAttackAnim.Init(PARALLEL_TYPE_ATTACK_ANIM, PARALLEL_CONDITION_MANUAL);
        childAttackAnim.AddObject(parallel.m_attackerID);
        childAttackAnim.m_actionType = parallel.m_actionType;
        childAttackAnim.m_state = PARALLEL_STATE_END;
        parallel.AddChild(childAttackAnim);
        parallel.m_tag = 0;
        count = Math.min(count, GetCampNumber(s_actorInBattle[parallel.m_objectID[0]].m_value[BV_CAMP]));
        for (i = 0; i < count - 1; i++)
        {
            parallel.m_objectID[i + 1] = GetNextAimedID(parallel.m_objectID[i], 1, true);
        }
        //加入effect子任务,不开始执行
        CParallel childEffect = CParallel.GetInstance();
        childEffect.Init(PARALLEL_TYPE_EFFECT_ANIM, PARALLEL_CONDITION_MANUAL);
        if (parallel.m_objectCount > 0)
        {
            for (i = 0; i < count; i++)
            {
                childEffect.AddObject(parallel.m_objectID[i]);
            }
        }
        else
        {
            childEffect.AddObject( -1);
        }
        childEffect.m_state = PARALLEL_STATE_END;
        parallel.AddChild(childEffect);
        //加入feedback子任务,不开始执行
        for (i = 0; i < count; i++)
        {
            CParallel childFeedback = CParallel.GetInstance();
            childFeedback.SetActionInfo(parallel.m_attackerID, parallel.m_actionType, parallel.m_actionID, parallel.m_objectID[i], 1);
            childFeedback.Init(PARALLEL_TYPE_FEEDBACKSET, PARALLEL_CONDITION_AUTO);
            parallel.AddChild(childFeedback);
        }

        parallel.m_objectCount = count;
    }

    /**
     * 第一个子窗口是动画窗口
     *
     * @param parallel CParallel
     */
    static void RPSingleAttackRun(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        int frameType = -1;
        int frameValue = 0;
        CParallel childAttackAnim = parallel.m_children[0];
        CParallel childAttackEff = parallel.m_children[1];
        CParallel childFeedback = null;

        switch (parallel.m_step)
        {
        case PARALLEL_STEP_ATTACK_DOING:
            while (parallel.m_timer >= s_dbFrameInfo[parallel.m_infoID][parallel.m_tag])
            {
                frameType = s_dbFrameInfo[parallel.m_infoID][parallel.m_tag + 1];
                frameValue = s_dbFrameInfo[parallel.m_infoID][parallel.m_tag + 2];
                switch (frameType)
                {
                case FRAME_ENUM_PURPOSE_DAMAGE:
                    for (int i = 0; i < parallel.m_objectCount; i++)
                    {
                        childFeedback = parallel.m_children[2 + i];
                        childFeedback.m_step = PARALLEL_STEP_FEEDBACK_HURT;
                        childFeedback.m_tag = frameValue;
                    }
                    break;
                case FRAME_ENUM_PURPOSE_SCREEN_COLOR:
                    s_cameraColorTimer = frameValue;
                    break;
                case FRAME_ENUM_PURPOSE_SHOCK:
                    ShockBattleCamera(frameValue, BATTLE_CAMERA_SHOCK_SPEED_X, BATTLE_CAMERA_SHOCK_SPEED_Y);
                    break;
                case FRAME_ENUM_PURPOSE_EFFECT:
                    childAttackEff.ReStart();
                    childAttackEff.m_actionID = frameValue;
                    break;
                case FRAME_ENUM_PURPOSE_HIDE_UI:
                    if (frameValue > 0)
                    {
                        s_battleUIFalg &= ~BATTLE_UI_FRAME_HIDE;
                    }
                    else
                    {
                        s_battleUIFalg |= BATTLE_UI_FRAME_HIDE;
                    }
                    break;
                case FRAME_ENUM_PURPOSE_RUSH:
                    SetBattleActorPos(parallel.m_attackerID, parallel.m_objectID[0], parallel.m_objectCount, frameValue);
                    break;
                case FRAME_ENUM_PURPOSE_ATTACK_ANIM:
                    childAttackAnim.ReStart();
                    childAttackAnim.m_actionID = frameValue;
                    break;
//@#if EXIST_VIBRATE
//@                case FRAME_ENUM_PURPOSE_VIBRATE:
//@                    DeviceVibrate(frameValue);
//@                    break;
//@#endif
                }
                parallel.m_tag += 3;
                if (parallel.m_tag + 2 >= s_dbFrameInfo[parallel.m_infoID].length)
                {
                    parallel.m_step = PARALLEL_STEP_ATTACK_FRAME_END;
                    return;
                }
            }
            break;
        case PARALLEL_STEP_ATTACK_FRAME_END:
            parallel.m_step = PARALLEL_STEP_ATTACK_WAITEND;
            for (int i = 0; i < parallel.m_objectCount; i++)
            {
                childFeedback = parallel.m_children[2 + i];
                childFeedback.m_step = PARALLEL_STEP_FEEDBACK_APPLY;
            }
            break;
        case PARALLEL_STEP_ATTACK_WAITEND:
            if (parallel.IsAllChildrenEnd())
            {
                parallel.m_step = PARALLEL_STEP_ATTACK_RESET;
                CParallel paraReset = CParallel.GetInstance();
                paraReset.Init(PARALLEL_TYPE_ACTOR_RESET, PARALLEL_CONDITION_AUTO);
                paraReset.AddObject(childAttackAnim.m_objectID[0]);
                parallel.AddChild(paraReset);
                s_damageValueCount = 0;
                parallel.m_step = PARALLEL_STEP_ATTACK_RESET;
            }
            break;
        case PARALLEL_STEP_ATTACK_RESET:
            if (parallel.IsAllChildrenEnd())
            {
                parallel.m_state = PARALLEL_STATE_END;
            }
            break;
        }
    }

    static void RPEffectAnimRun(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        CActor actor;
        boolean isEnd = true;

        for (int i = 0; i < parallel.m_objectCount; i++)
        {
            if (parallel.m_objectID[i] >= 0)
            {
                actor = s_actorEffInBattle[parallel.m_objectID[i]];
            }
            else
            {
                actor = s_actorEffFullScreen;
            }
            if (!actor.m_end)
            {
                isEnd = false;
                break;
            }
        }
        if (isEnd)
        {
            parallel.m_state = PARALLEL_STATE_END;
        }
    }

    static void RPAttackAnimRun(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        if (parallel.IsAllChildrenEnd())
        {
            parallel.m_state = PARALLEL_STATE_END;
        }
    }

    static void RPHurtAnimRun(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        CActor actor;
        boolean isEnd = true;
        for (int i = 0; i < parallel.m_objectCount; i++)
        {
            actor = s_actorInBattle[parallel.m_objectID[i]];
            if (!actor.m_end)
            {
                isEnd = false;
                break;
            }
        }
        if (isEnd)
        {
            parallel.m_state = PARALLEL_STATE_END;
        }
    }

    static void RPFeedbackSetRun(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        CParallel childParaHurtAnim = parallel.m_children[0];
        CParallel childParaDamageNum = parallel.m_children[1];
        int actorID = parallel.m_objectID[0];
        CActor actor = s_actorInBattle[actorID];

        switch (parallel.m_step)
        {
        case PARALLEL_STEP_FEEDBACK_WAIT:
            break;
        case PARALLEL_STEP_FEEDBACK_HURT:
            ApplyEffect(actor, parallel.m_tag, parallel.m_effectValue);
            CalcDamageRate(parallel.m_attackerID, parallel.m_objectID[0], parallel.m_actionType, parallel.m_actionID);
            childParaHurtAnim.ReStart();
            childParaDamageNum.ReStart();
            parallel.m_step = PARALLEL_STEP_FEEDBACK_WAIT;
            if (parallel.m_actionType == BATTLE_ACTION_TYPE_NORMAL && actor.m_value[BV_DMG_TYPE] != DAMAGE_TYPE_MISS)
            {
//@#if ONE_SKILL_EF
//@                s_actorEffInBattle[actorID].SetAction(ACTION_BUFFLIGHT_E_COLUMN);
//@#else
                s_actorEffInBattle[actorID].SetAction(ACTION_BUFFLIGHT_HURT);
//@#endif
            }
            break;
        case PARALLEL_STEP_FEEDBACK_APPLY:
            if (parallel.IsAllChildrenEnd())
            {
                ApplyBuff(actor, parallel);
                actor.CalcBuff();
                CParallel calcAnimParallel = CParallel.GetInstance();
                calcAnimParallel.m_objectID[0] = childParaHurtAnim.m_objectID[0];
                calcAnimParallel.Init(PARALLEL_TYPE_ACTOR_RESET, PARALLEL_CONDITION_AUTO);
                parallel.AddChild(calcAnimParallel);
                parallel.m_step = PARALLEL_STEP_FEEDBACK_END;
            }
            break;
        case PARALLEL_STEP_FEEDBACK_END:
            if (parallel.IsAllChildrenEnd())
            {
                parallel.m_state = PARALLEL_STATE_END;
            }
            break;
        }
    }

    /**
     * 一个FeedbackSet只针对一个目标
     *
     * @param parallel CParallel
     */
    static void RPFeedbackSetStart(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        CParallel hurtAnimPara, damageAnimPara;
        //计算伤害
        CalcDamageValue(parallel.m_attackerID, parallel.m_objectID[0], parallel.m_actionType, parallel.m_actionID);
        parallel.InitEffectValue();
        System.arraycopy(s_effectValue, 0, parallel.m_effectValue, 0, AMOUNT_DB_EFFECT_SET);

        //开始伤害动画子任务
        hurtAnimPara = CParallel.GetInstance();
        hurtAnimPara.Init(PARALLEL_TYPE_HURT_ANIM, PARALLEL_CONDITION_MANUAL);
        hurtAnimPara.AddObject(parallel.m_objectID[0]);
        hurtAnimPara.InitEffectValue();
        System.arraycopy(s_effectValue, 0, hurtAnimPara.m_effectValue, 0, AMOUNT_DB_EFFECT_SET);
        parallel.AddChild(hurtAnimPara);

        damageAnimPara = CParallel.GetInstance();
        damageAnimPara.Init(PARALLEL_TYPE_DAMAGEVALUE, PARALLEL_CONDITION_MANUAL);
        damageAnimPara.AddObject(parallel.m_objectID[0]);
        damageAnimPara.InitEffectValue();
        System.arraycopy(s_effectValue, 0, damageAnimPara.m_effectValue, 0, AMOUNT_DB_EFFECT_SET);
        parallel.AddChild(damageAnimPara);
    }

    static void RPDamageValueStart(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        int x, y, dHP, dMP;
        CActor feedbackActor = s_actorInBattle[parallel.m_objectID[0]];
        int camp = feedbackActor.m_value[BV_CAMP];

        x = feedbackActor.m_anchorX + (camp == CAMP_ALLY ? DAMAGE_INFO_ALLY_X : DAMAGE_INFO_FOE_X);
        y = feedbackActor.m_anchorY - DAMAGE_INFO_OFFSET_Y;
        int align = (camp == CAMP_ALLY ? ALIGN_RIGHT_TOP : ALIGN_LEFT_TOP);
        switch (feedbackActor.m_value[BV_DMG_TYPE])
        {
        case DAMAGE_TYPE_CRITICAL:
            AddDamageValue(x - DAMAGE_CH_NUM_OFFSET_X, y - DAMAGE_CH_NUM_OFFSET_Y, -feedbackActor.m_value[BV_DMG_VALUE],
                           BATTLE_UI_DAMAGE_CRITICAL_NUM, DAMAGE_VALUE_TYPE_NUM, align);
            break;
        case DAMAGE_TYPE_SKILL:
            dHP = parallel.m_effectValue[DB_EFFECT_SET_ENUMERATE_HP] +
                  (parallel.m_effectValue[DB_EFFECT_SET_ENUMERATE_HPP] * feedbackActor.m_value[BV_HPMAX]) / 100;
            dMP = parallel.m_effectValue[DB_EFFECT_SET_ENUMERATE_MP] +
                  (parallel.m_effectValue[DB_EFFECT_SET_ENUMERATE_MPP] * feedbackActor.m_value[BV_MPMAX]) / 100;
            if (dHP != 0)
            {
                AddDamageValue(x - DAMAGE_HP_NUM_OFFSET_X, y - DAMAGE_HP_NUM_OFFSET_Y, dHP, BATTLE_UI_DAMAGE_RECOVERHP_NUM, DAMAGE_VALUE_TYPE_NUM, align);
            }
            if (dMP != 0)
            {
                AddDamageValue(x - DAMAGE_MP_NUM_OFFSET_X, y - DAMAGE_MP_NUM_OFFSET_Y, dMP, BATTLE_UI_DAMAGE_RECOVERMP_NUM, DAMAGE_VALUE_TYPE_NUM, align);
            }
            if (parallel.m_effectValue[DB_EFFECT_SET_ENUMERATE_DAMAGE] > 0)
            {
                AddDamageValue(x - DAMAGE_SKILL_NUM_OFFSET_X, y - DAMAGE_SKILL_NUM_OFFSET_Y, -feedbackActor.m_value[BV_DMG_VALUE],
                               BATTLE_UI_DAMAGE_SKILL_NUM, DAMAGE_VALUE_TYPE_NUM, align);
            }
            break;
        case DAMAGE_TYPE_NORMAL:
            AddDamageValue(x - DAMAGE_NORMAL_OFFSET_X, y - DAMAGE_NORMAL_OFFSET_Y, -feedbackActor.m_value[BV_DMG_VALUE],
                           BATTLE_UI_DAMAGE_NORMAL_NUM, DAMAGE_VALUE_TYPE_NUM, align);
            break;
        case DAMAGE_TYPE_ITEM:
            dHP = parallel.m_effectValue[DB_EFFECT_SET_ENUMERATE_HP] +
                  (parallel.m_effectValue[DB_EFFECT_SET_ENUMERATE_HPP] * feedbackActor.m_value[BV_HPMAX]) / 100;
            dMP = parallel.m_effectValue[DB_EFFECT_SET_ENUMERATE_MP] +
                  (parallel.m_effectValue[DB_EFFECT_SET_ENUMERATE_MPP] * feedbackActor.m_value[BV_MPMAX]) / 100;
            if (dHP != 0)
            {
                AddDamageValue(x - DAMAGE_HP_NUM_OFFSET_X, y - DAMAGE_HP_NUM_OFFSET_Y, dHP, BATTLE_UI_DAMAGE_RECOVERHP_NUM, DAMAGE_VALUE_TYPE_NUM, align);
            }
            if (dMP != 0)
            {
                AddDamageValue(x - DAMAGE_MP_NUM_OFFSET_X, y - DAMAGE_MP_NUM_OFFSET_Y, dMP, BATTLE_UI_DAMAGE_RECOVERMP_NUM, DAMAGE_VALUE_TYPE_NUM, align);
            }
            break;
        case DAMAGE_TYPE_MISS:
            x = feedbackActor.m_anchorX + DAMAGE_INFO_FOE_X;
            AddDamageValue(x - DAMAGE_MISS_OFFSET_X, y - DAMAGE_MISS_OFFSET_Y, 0, BATTLE_UI_DAMAGE_DODGE_WORD, DAMAGE_VALUE_TYPE_STR, align);
            break;
        case DAMAGE_TYPE_AUTO:
            AddDamageValue(x - DAMAGE_NORMAL_OFFSET_X, y - DAMAGE_NORMAL_OFFSET_Y, -feedbackActor.m_value[BV_DMG_VALUE], BATTLE_UI_DAMAGE_NORMAL_NUM,
                           DAMAGE_VALUE_TYPE_NUM, align);
            break;
        }
    }

    static void RPDamageValueRun(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        parallel.m_state = PARALLEL_STATE_END;
    }

    static void AddDamageValue(int offX, int offY, int num, int module, int type, int align)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        if (s_damageValueCount < DAMAGE_VALUE_COUNT_MAX)
        {
            int offset = s_damageValueCount * AMOUNT_DAMAGE_VALUE_OFFSET;
            s_damageValueData[offset + DAMAGE_VALUE_OFFSET_X] = offX;
            s_damageValueData[offset + DAMAGE_VALUE_OFFSET_Y] = offY;
            s_damageValueData[offset + DAMAGE_VALUE_OFFSET_NUM] = num;
            s_damageValueData[offset + DAMAGE_VALUE_OFFSET_MODULEID] = module;
            s_damageValueData[offset + DAMAGE_VALUE_OFFSET_TYPE] = type;
            s_damageValueData[offset + DAMAGE_VALUE_OFFSET_TIME] = 0;
            s_damageValueData[offset + DAMAGE_VALUE_OFFSET_ALIGN] = align;
            s_damageValueCount++;
        }
    }

    static void RPBuffSetRun(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        CParallel childPara0;

        switch (parallel.m_step)
        {
        case PARALLEL_STEP_BUFFSET_JUDGE:
            if (parallel.IsAllChildrenEnd())
            { //恢复动画
                childPara0 = CParallel.GetInstance();
                childPara0.m_objectID[0] = parallel.m_objectID[0];
                childPara0.Init(PARALLEL_TYPE_ACTOR_RESET, PARALLEL_CONDITION_AUTO);
                parallel.AddChild(childPara0);
                parallel.m_step = PARALLEL_STEP_BUFFSET_RESET;
            }
            break;
        case PARALLEL_STEP_BUFFSET_RESET:
            if (parallel.IsAllChildrenEnd())
            {
                parallel.m_state = PARALLEL_STATE_END;
            }
            break;
        }
    }

    static void RPAttackAnimStart(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        CActor actor = s_actorInBattle[parallel.m_objectID[0]];
        actor.m_repeat = false;
        actor.SetAction(parallel.m_actionID);
    }

    static void RPEffectAnimStart(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        CActor actor;

        for (int i = 0; i < parallel.m_objectCount; i++)
        {
            if (parallel.m_objectID[i] >= 0)
            {
                actor = s_actorEffInBattle[parallel.m_objectID[i]];
            }
            else
            {
                actor = s_actorEffFullScreen;
            }
            actor.SetAction(parallel.m_actionID);
            actor.m_repeat = false;
        }
    }

    static void RPActorRsetStart(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        int actorID = parallel.m_objectID[0];
        CActor actor = s_actorInBattle[actorID];
        actor.m_curX = actor.m_anchorX;
        actor.m_curY = actor.m_anchorY;

        if (actor.m_value[BV_HP] <= 0)
        {
            ActorDie(actor);
            actor.SetAction(ACTION_BATTLE_DIE);
            actor.m_repeat = false;
            if (actor.m_value[BV_CAMP] == CAMP_FOE)
            {
                actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] = T_FALSE;
                s_actorEffInBattle[actorID].SetAction(ACTION_BATTLE_DIE_EFFECT);
                s_actorEffInBattle[actorID].m_repeat = false;
                PlusGameData(DEFINE_GAME_DATA_KILL_COUNT, 1, GAME_KILLS_MAX);
            }
        }
        else if (actor.m_value[BV_ACTION] == BATTLE_ACTION_GUARD)
        {
            actor.SetAction(ACTION_BATTLE_GUARD);
            actor.m_repeat = true;
        }
        else
        {
            actor.m_repeat = true;
            if (actor.m_value[BV_HP] <= actor.m_value[BV_HPMAX] / BATTLE_WEAK)
            {
                actor.SetAction(ACTION_BATTLE_WEAK);
            }
            else
            {
                actor.SetAction(ACTION_BATTLE_WAIT);
            }
        }
    }

    static void RPUIAnimRun(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        s_battleAnimFalg = BATTLE_ANIM_BG | BATTLE_ANIM_ACTOR | BATTLE_ANIM_INFO;
        if (parallel.m_timer >= BU_QUEUE_ANIM_TIME)
        {
            parallel.m_state = PARALLEL_STATE_END;
            s_uiActorQueueX = BU_QUEUE_X;
        }
        else
        {
            s_uiActorQueueX -= BU_QUEUE_ANIM_VX;
        }
    }


    static void RPUIAnimStart(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        s_uiActorQueueX = BU_QUEUE_X + BU_QUEUE_W;
        s_battleAnimFalg = BATTLE_ANIM_BG | BATTLE_ANIM_ACTOR | BATTLE_ANIM_INFO;
    }

    static void RPDialogStart(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        int offset = parallel.m_tag * 7;
        s_uiContentID = s_battleScriptData[offset + CMD_BC_DIALOG_MESSAGE];
        s_uiDlgPosH = s_battleScriptData[offset + CMD_BC_DIALOG_PROTRAIT_POS_H];
        s_uiDlgPosV = s_battleScriptData[offset + CMD_BC_DIALOG_PROTRAIT_POS_V];
        s_uiDlgHeadID = s_battleScriptData[offset + CMD_BC_DIALOG_PROTRAIT_ID];
        s_uiExpression = s_battleScriptData[offset + CMD_BC_DIALOG_EXPRESSION];
        s_uiDlgName = -1;
        InitUI(UI_TYPE_DIALOG, UI_DIALOG_MAX_LINE, EP_BATTLE);
        s_battleAnimFalg = BATTLE_ANIM_BG | BATTLE_ANIM_ACTOR;
    }

    static void RPDialogRun(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        s_battleUIFalg = BATTLE_UI_DIALOG | BATTLE_UI_QUEUE;
        s_battleAnimFalg = BATTLE_ANIM_BG | BATTLE_ANIM_ACTOR;
        if (s_uiDlgHeadID >= 0)
        {
            s_actorPortraits[s_uiDlgHeadID].UpdateAnimation();
        }
        boolean fireKeyPress =IsKeySinglePressed(GKEY_OK);
        int returnValue = UtilUpdateStringByWord(fireKeyPress);
        if (fireKeyPress)
        {
            if (returnValue == UTIL_STATE_OK)
            {
                s_uiResult = WND_STATE_YES;
                parallel.m_state = PARALLEL_STATE_END;
                //如果有子进程,开始子进程
                for (int i = 0; i < parallel.m_childCount; i++)
                {
                    parallel.m_children[i].m_state = PARALLEL_STATE_START;
                }
            }
            else
            {
                s_uiResult = WND_STATE_WAIT;
            }
        }
    }

    static void RPBuffSetStart(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        CParallel childPara0, childPara1;
        int actorID = parallel.m_objectID[0];
        CActor actor = s_actorInBattle[actorID];

        childPara0 = CParallel.GetInstance();
        childPara0.Init(PARALLEL_TYPE_DAMAGEVALUE, PARALLEL_CONDITION_AUTO);
        parallel.AddChild(childPara0);
        childPara1 = CParallel.GetInstance();
        childPara1.Init(PARALLEL_TYPE_HURT_ANIM, PARALLEL_CONDITION_AUTO);
        parallel.AddChild(childPara1);

        if (actor.m_buff[DB_EFFECT_SET_ENUMERATE_POISON] != 0)
        {
            actor.m_value[BV_HP] += actor.m_buff[DB_EFFECT_SET_ENUMERATE_POISON];
            actor.m_value[BV_DMG_TYPE] = DAMAGE_TYPE_AUTO;
            actor.m_value[BV_DMG_VALUE] = -actor.m_buff[DB_EFFECT_SET_ENUMERATE_POISON];
            actor.m_value[BV_HP] = Math.max(actor.m_value[BV_HP], 1);
            childPara0.AddObject(actorID);
            childPara1.AddObject(actorID);
        }
        else
        {
            childPara0.m_state = PARALLEL_STATE_END;
            childPara1.m_state = PARALLEL_STATE_END;
            parallel.m_state = PARALLEL_STATE_END;
        }
    }

    static void RPBuffAnimStart(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        CActor actor = s_actorInBattle[parallel.m_objectID[0]];
        actor.m_value[BV_DMG_TYPE] = DAMAGE_TYPE_NORMAL;
        actor.m_value[BV_HP] -= actor.m_value[BV_DMG_VALUE];
        actor.SetAction(parallel.m_actionID);
        actor.m_repeat = false;
        actor.m_end = false;
    }

    static void RPRushStart(CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        CParallel paraAttackSet = CParallel.GetInstance();
        paraAttackSet.Init(PARALLEL_TYPE_ATTACK_SET, PARALLEL_CONDITION_AUTO);
        paraAttackSet.SetActionInfo(parallel.m_attackerID, parallel.m_actionType, parallel.m_actionID, parallel.m_objectID[0], parallel.m_objectCount);
        paraAttackSet.m_infoID = parallel.m_infoID;
        parallel.AddChild(paraAttackSet);
        s_bSPIncrease = false;
    }

    static void RPBattleEndStart(CParallel parallel)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        CParallel paraPose, paraWindow,paraSound;
        paraPose = CParallel.GetInstance();
        paraPose.Init(PARALLEL_TYPE_POSE, PARALLEL_CONDITION_AUTO);
        parallel.AddChild(paraPose);
        paraWindow = CParallel.GetInstance();
        paraWindow.Init(PARALLEL_TYPE_WINDOW, PARALLEL_CONDITION_MANUAL);
        parallel.AddChild(paraWindow);
        paraSound = CParallel.GetInstance();
        paraSound.Init(PARALLEL_TYPE_SOUND,PARALLEL_CONDITION_AUTO);
        parallel.AddChild(paraSound);

        switch (parallel.m_actionType)
        {
        case BATTLE_RESULT_WIN:
            paraPose.m_actionID = ACTION_BATTLE_WIN;
            paraSound.m_actionID = SOUND_WIN;
            for (int i = 0; i < s_formationMateCount; i++)
            {
                if ((s_actorInBattle[i].m_value[BV_STATE] & BAS_DEAD) == 0)
                {
                    paraPose.AddObject(i);
                }
            }
            paraWindow.AddObject(WND_LOOT_ITEM);
            paraWindow.AddObject(WND_LOOT_EXP);
            break;
        case BATTLE_RESULT_LOSE:
            paraSound.m_actionID = SOUND_LOSE;
            paraWindow.AddObject(WND_LOOT_FAIL);
            break;
        case BATTLE_RESULT_ESCAPE:
            break;
        }
    }

    static void RPBattleEndRun(CParallel parallel)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        CParallel paraWindow = parallel.m_children[1];

        if (parallel.m_timer == PARALLEL_WINDOW_START_TIME)
        {
            paraWindow.ReStart();
        }

        if (parallel.IsAllChildrenEnd())
        {
            parallel.m_state = PARALLEL_STATE_END;
        }
    }

    static void RPPoseStart(CParallel parallel)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        CActor actor = null;

        for (int i = 0; i < parallel.m_objectCount; i++)
        {
            actor = s_actorInBattle[i];
            actor.SetAction(parallel.m_actionID);
            actor.m_repeat = false;
        }
    }

    static void RPPoseRun(CParallel parallel)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        boolean end = true;

        for (int i = 0; i < parallel.m_objectCount; i++)
        {
            if (!s_actorInBattle[i].m_end)
            {
                end = false;
                break;
            }
        }

        if (end && parallel.IsAllChildrenEnd())
        {
            parallel.m_state = PARALLEL_STATE_END;
        }
    }

    static void RPWindowStart(CParallel parallel)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        parallel.m_tag = 0;
    }

    static void RPWindowRun(CParallel parallel)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        if (s_wnd == null)
        {
            if (parallel.m_tag < parallel.m_objectCount)
            {
                s_wnd = CWnd.GetInstance();
                s_wnd.Init(parallel.m_objectID[parallel.m_tag], IGM_X, IGM_Y, IGM_W, IGM_H, true);
                s_wnd.m_isTop = true;
                parallel.m_tag++;
            }
            else
            {
                if (parallel.IsAllChildrenEnd())
                {
                    parallel.m_state = PARALLEL_STATE_END;
                }
            }
        }
        else
        {
            s_wnd.Update();
        }
    }
    static void RPSoundStart(CParallel parallel)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h

        if ((s_optionFlag & OPTION_FLAG_SOUND) != 0)
        {
            SndStop();
            SndPlay(parallel.m_actionID,1);
        }
    }

    static void RPSoundRun(CParallel parallel)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h

        {
            if (parallel.IsAllChildrenEnd())
            {
                parallel.m_state = PARALLEL_STATE_END;
            }
        }
    }

    static void UpdateRSParallel()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_RoundParallel.h
        CParallel.s_isAllDone = true;
        s_parallel.Update();
        if (CParallel.s_isAllDone)
        {
            //计算结果，归位
            CalcResult();
            RSParallelReset();
            ClearEffectValue();
            s_wndBattleDesc.m_show = false;
            SetRoundState(s_actorBattleActivated.m_value[BV_AP] > 0 ? RS_UI : RS_ROUNDEND);
        }
    }

//@@
//@@..\CGame\CGame_Effect.h
    static void UpdateEffect()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Effect.h
        if (s_isRenderEffect)
        {
            switch (s_effectType)
            {
            case RANDOM_EFFECT_TYPE_SQUARE:
                UpdataEffectRange(EFFECT_RANGE_PERFRAME_0);
                break;
            case RANDOM_EFFECT_TYPE_SHADOW:
                UpdataEffectRange(EFFECT_RANGE_PERFRAME_1);
                break;
//@#if USE_ALL_SCREEN_EFFECT
            case RANDOM_EFFECT_TYPE_CROSS:
                UpdataEffectRange(EFFECT_RANGE_PERFRAME_2);
                break;
            case RANDOM_EFFECT_TYPE_SECTOR:
                UpdataEffectRange(EFFECT_RANGE_PERFRAME_3);
                break;
//@#endif
            case RANDOM_EFFECT_TYPE_SPECIAL0:
                UpdataEffectRange(EFFECT_RANGE_PERFRAME_SP0);
                break;
            case RANDOM_EFFECT_TYPE_SPECIAL1:
                UpdataEffectRange(EFFECT_RANGE_PERFRAME_SP1);
                break;
            case RANDOM_EFFECT_TYPE_MOVIE:
                UpdataEffectRange(EFFECT_RANGE_PERFRAME_MOVIE);
                break;
            }
        }
    }

    static void UpdataEffectRange(int range)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Effect.h
        if (s_effectRange < s_effectRangeTarget)
        {
            s_effectRange += range;
            if (s_effectRange >= s_effectRangeTarget)
            {
                s_effectRange = s_effectRangeTarget;
                s_bScriptPause = false;
            }
        }
        else if (s_effectRange > s_effectRangeTarget)
        {
            s_effectRange -= range;
            if (s_effectRange <= s_effectRangeTarget)
            {
                s_effectRange = s_effectRangeTarget;
                s_bScriptPause = false;
                s_isRenderEffect = false;
            }
        }
    }

    static void RenderEffect(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Effect.h
        if (s_isRenderEffect)
        {
            g.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            switch (s_effectType)
            {
            case RANDOM_EFFECT_TYPE_SQUARE:
                for (int i = 0; i < RANDOM_TYPE_0_ROW; i++)
                {
                    for (int j = 0; j < RANDOM_TYPE_0_COL; j++)
                    {
                        UtilFillRect(g, COLOR_BLACK, i * RANDOM_TYPE_0_RANGE, j * RANDOM_TYPE_0_RANGE, s_effectRange, s_effectRange);
                    }
                }
                break;
            case RANDOM_EFFECT_TYPE_SHADOW:
                UtilFillRect(g, COLOR_BLACK, 0, 0, s_effectRange, SCREEN_HEIGHT);
                UtilFillRect(g, COLOR_BLACK, s_effectRange + RANDOM_TYPE_1_SPACE1, 0, RANDOM_TYPE_1_RANGE1, SCREEN_HEIGHT);
                UtilFillRect(g, COLOR_BLACK, s_effectRange + RANDOM_TYPE_1_SPACE2, 0, RANDOM_TYPE_1_RANGE2, SCREEN_HEIGHT);
                UtilFillRect(g, COLOR_BLACK, s_effectRange + RANDOM_TYPE_1_SPACE3, 0, RANDOM_TYPE_1_RANGE3, SCREEN_HEIGHT);
                break;
//@#if USE_ALL_SCREEN_EFFECT
            case RANDOM_EFFECT_TYPE_CROSS:
                for (int i = 0; i < RANDOM_TYPE_2_ROW; i += 2)
                {
                    UtilFillRect(g, COLOR_BLACK, 0, i * RANDOM_TYPE_2_RANGE, s_effectRange, RANDOM_TYPE_2_RANGE);
                }
                for (int i = 1; i < RANDOM_TYPE_2_ROW; i += 2)
                {
                    UtilFillRect(g, COLOR_BLACK, SCREEN_WIDTH - s_effectRange, i * RANDOM_TYPE_2_RANGE, s_effectRange, RANDOM_TYPE_2_RANGE);
                }
                break;
            case RANDOM_EFFECT_TYPE_SECTOR:
                g.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                g.setColor(COLOR_BLACK);
                g.fillArc(SCREEN_WIDTH_HALF - RANDOM_TYPE_3_RANGE_HALF, SCREEN_HEIGHT_HALF - RANDOM_TYPE_3_RANGE_HALF, RANDOM_TYPE_3_RANGE,
                            RANDOM_TYPE_3_RANGE, 0, s_effectRange);
                break;
//@#endif
            case RANDOM_EFFECT_TYPE_SPECIAL0:
                UtilFillRect(g, COLOR_BLACK, 0, 0, SCREEN_WIDTH, s_effectRange);
                UtilFillRect(g, COLOR_BLACK, 0, s_effectRange + RANDOM_TYPE_1_SPACE1, SCREEN_WIDTH, RANDOM_TYPE_1_RANGE1);
                UtilFillRect(g, COLOR_BLACK, 0, s_effectRange + RANDOM_TYPE_1_SPACE2, SCREEN_WIDTH, RANDOM_TYPE_1_RANGE2);
                UtilFillRect(g, COLOR_BLACK, 0, s_effectRange + RANDOM_TYPE_1_SPACE3, SCREEN_WIDTH, RANDOM_TYPE_1_RANGE3);
                break;
            case RANDOM_EFFECT_TYPE_SPECIAL1:
                UtilFillRect(g, COLOR_BLACK, 0, 0, s_effectRange, SCREEN_HEIGHT);
                break;
            case RANDOM_EFFECT_TYPE_MOVIE:
                UtilFillRect(g, COLOR_BLACK, 0, 0, SCREEN_WIDTH, s_effectRange);
                UtilFillRect(g, COLOR_BLACK, 0, SCREEN_HEIGHT - s_effectRange, SCREEN_WIDTH, s_effectRange);
                break;
            }
        }
    }

    static void SetCurtainEffect(int controlType, int effectType)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Effect.h
        s_screenControlType = controlType;
        switch (s_screenControlType)
        {
        case EFFECT_TYPE_CURTAIN_MOVIE_ON:
            s_effectRangeTarget = effectType;
            SetCurtainEffect(EFFECT_TYPE_CURTAIN_CLOSE, RANDOM_EFFECT_TYPE_MOVIE + 1);
            return;
        case EFFECT_TYPE_CURTAIN_MOVIE_OFF:
            SetCurtainEffect(EFFECT_TYPE_CURTAIN_OPEN, RANDOM_EFFECT_TYPE_MOVIE + 1);
            break;
        case EFFECT_TYPE_CURTAIN_CLOSE:
            if (effectType > 0)
            {
                s_effectType = (byte) (effectType - 1);
            }
            else
            {
                s_effectType = (byte) ((s_random.nextInt() >>> 1) % AMOUNT_RANDOM_EFFECT);
            }
            switch (s_effectType)
            {
            case RANDOM_EFFECT_TYPE_SQUARE:
                s_effectRange = 0;
                s_effectRangeTarget = RANDOM_TYPE_0_RANGE;
                break;
            case RANDOM_EFFECT_TYPE_SHADOW:
                s_effectRange = -RANDOM_TYPE_1_SPACE3;
                s_effectRangeTarget = SCREEN_WIDTH;
                break;
//@#if USE_ALL_SCREEN_EFFECT
            case RANDOM_EFFECT_TYPE_CROSS:
                s_effectRange = 0;
                s_effectRangeTarget = SCREEN_WIDTH;
                break;
            case RANDOM_EFFECT_TYPE_SECTOR:
                s_effectRange = 0;
                s_effectRangeTarget = 360;
                break;
//@#endif
            case RANDOM_EFFECT_TYPE_SPECIAL0:
                s_effectRange = -RANDOM_TYPE_1_SPACE3;
                s_effectRangeTarget = SCREEN_HEIGHT_HALF;
                break;
            case RANDOM_EFFECT_TYPE_SPECIAL1:
                s_effectRange = EFFECT_RANGE_PERFRAME_SP1;
                s_effectRangeTarget = EFFECT_RANGE_PERFRAME_SP1;
                break;
            case RANDOM_EFFECT_TYPE_MOVIE:
                break;
            }
            s_isRenderEffect = true;
            break;
        case EFFECT_TYPE_CURTAIN_OPEN:
            switch (s_effectType)
            {
            case RANDOM_EFFECT_TYPE_SQUARE:
                s_effectRange = RANDOM_TYPE_0_RANGE;
                s_effectRangeTarget = 0;
                break;
            case RANDOM_EFFECT_TYPE_SHADOW:
                s_effectRange = SCREEN_WIDTH;
                s_effectRangeTarget = -RANDOM_TYPE_1_SPACE3;
                break;
//@#if USE_ALL_SCREEN_EFFECT
            case RANDOM_EFFECT_TYPE_CROSS:
                s_effectRange = SCREEN_WIDTH;
                s_effectRangeTarget = 0;
                break;
            case RANDOM_EFFECT_TYPE_SECTOR:
                s_effectRange = 360;
                s_effectRangeTarget = 0;
                break;
//@#endif
            case RANDOM_EFFECT_TYPE_SPECIAL0:
                s_effectRange = SCREEN_HEIGHT_HALF;
                s_effectRangeTarget = -RANDOM_TYPE_1_SPACE3;
                break;
            case RANDOM_EFFECT_TYPE_SPECIAL1:
                s_effectRange = 0;
                s_effectRangeTarget = 0;
                break;
            case RANDOM_EFFECT_TYPE_MOVIE:
                s_effectRangeTarget = 0;
                break;
            }
            s_isRenderEffect = true;
            break;
        }
    }

//@@
//@@..\CGame\CGame_Interlude.h
    static short s_interludeCur;
    static short s_interludeLimit;
    static byte s_interludeType;

    static void InitInterlude()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Interlude.h
        s_interludeLimit = 0;
        s_interludeCur = 0;
        s_interludeLimit = 16;
    }

    static void UpdateInterlude()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Interlude.h
        s_interludeCur++;
        if (s_interludeCur > s_interludeLimit)
        {
            s_loadData = LOAD_DATA_OTHER;
            SetGameState(GS_LOADSCENE);
        }
    }

    static void RenderInterlude(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Interlude.h
        for (int i = 0; i < SCREEN_WIDTH / 16; i++)
        {
            UtilFillRect(g, COLOR_BLACK, 16 * i, 0, s_interludeCur, SCREEN_HEIGHT);
        }
    }

//@@
//@@..\CGame\CGame_Camera.h
    static boolean s_bMapSmallThenScreen = false;

    static void InitView()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Camera.h
        s_viewTop = VIEW_TOP;
        s_viewWidth = VIEW_WIDTH;
        s_mapBufferWidth = ((VIEW_WIDTH / TILE_SIZE) + VIEW_OFFSET_X) * TILE_SIZE + TILE_SIZE;

        s_viewHeight = ViEW_HEIGHT;
        s_viewHeightHalf = (s_viewHeight >> 1);
        s_viewWidthHalf = (s_viewWidth >> 1);

        s_mapBufferHeight = (s_viewHeight / TILE_SIZE) * TILE_SIZE + (TILE_SIZE << 1);
        s_mapTileMaxNumX = SCREEN_WIDTH / TILE_SIZE + 1;
        s_mapTileMaxNumY = SCREEN_HEIGHT / TILE_SIZE + 1;
    }

    /**
     * 初始化镜头
     */
    static void InitCamera()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Camera.h
        s_bMapSmallThenScreen =
                ((s_mapWidthByTile * TILE_SIZE < SCREEN_WIDTH && s_mapWidthByTile > 0)
                 || (s_mapHeightByTile * TILE_SIZE < SCREEN_HEIGHT && s_mapHeightByTile > 0));

        for (int i = 0; i < s_layerCount; i++)
        {
            if (s_imgTile[i] != null)
            {
                s_tilePerLine[i] = s_imgTile[i].getWidth() / TILE_SIZE;
            }
        }
        s_cameraXMax = (short) ((s_mapWidthByTile * TILE_SIZE) - VIEW_WIDTH);
        s_cameraYMax = (short) ((s_mapHeightByTile * TILE_SIZE) - s_viewHeight);

        for (int i = 0; i < AMOUNT_CAMERA_SHOCK; i++)
        {
            s_cameraShock[i] = 0;
        }
    }

    static void CreateMapBufferImage()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Camera.h
        s_imgMapBuffer = null;
        int w = 0, h = 0;
        if (s_mapWidthByTile * TILE_SIZE < SCREEN_WIDTH)
        {
            w = s_mapWidthByTile * TILE_SIZE;
        }
        else
        {
            w = s_mapBufferWidth;
        }
        if (s_mapHeightByTile * TILE_SIZE < SCREEN_HEIGHT)
        {
            h = s_mapHeightByTile * TILE_SIZE;
        }
        else
        {
            h = s_mapBufferHeight;
        }
        s_imgMapBuffer = Image.createImage(w, h);
//@#if NOKIA_LIB
//@        s_gfxMapBufferSrc = s_imgMapBuffer.getGraphics();
//@        s_gfxMapBuffer = DirectUtils.getDirectGraphics(s_gfxMapBufferSrc);
//@#else
        s_gfxMapBuffer = s_imgMapBuffer.getGraphics();
//@#endif
    }

    static void FreeBufferImage()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Camera.h
        s_imgMapBuffer = null;
        s_gfxMapBuffer = null;
        System.gc();
    }

    static void UpdateCamera()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Camera.h
        s_cameraXOld = s_cameraX;
        s_cameraYOld = s_cameraY;
        switch (s_cameraState)
        {
        case CAMERA_STATE_FREE:
            break;
        case CAMERA_STATE_STAND:
            break;
        case CAMERA_STATE_MOVE:
            if (s_cameraX < s_cameraDestX)
            {
                s_cameraX = (short) Math.min(s_cameraDestX, s_cameraX + s_cameraVX);
            }
            else if (s_cameraX > s_cameraDestX)
            {
                s_cameraX = (short) Math.max(s_cameraDestX, s_cameraX + s_cameraVX);
            }
            if (s_cameraY < s_cameraDestY)
            {
                s_cameraY = (short) Math.min(s_cameraDestY, s_cameraY + s_cameraVY);
            }
            else if (s_cameraY > s_cameraDestY)
            {
                s_cameraY = (short) Math.max(s_cameraDestY, s_cameraY + s_cameraVY);
            }
            if (s_cameraX == s_cameraDestX && s_cameraY == s_cameraDestY)
            {
                s_cameraState = (s_cameraStateNext == -1) ? CAMERA_STATE_STAND : s_cameraStateNext;
            }
            break;
        case CAMERA_STATE_ACTOR:
            s_cameraX = (short) (s_actorCameraHook.m_curX - SCREEN_WIDTH_HALF);
            s_cameraY = (short) (s_actorCameraHook.m_curY - SCREEN_HEIGHT_HALF);
            break;
        default:
            break;
        }
        //镜头范围
        LimitCameraPos();
        //镜头颜色
        UpdateCameraColor();
    }

    static void LimitCameraPos()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Camera.h
        if (s_cameraX < 0)
        {
            s_cameraX = 0;
            if (s_mapWidthByTile > 0)
            {
                if (s_mapWidthByTile * TILE_SIZE < SCREEN_WIDTH)
                {
                    s_cameraX = (short) (( -SCREEN_WIDTH + s_mapWidthByTile * TILE_SIZE) >> 1);
                }
            }
        }
        else if (s_cameraX > s_cameraXMax)
        {
            s_cameraX = 0;
            if (s_mapWidthByTile > 0)
            {
                if (s_mapWidthByTile * TILE_SIZE >= SCREEN_WIDTH)
                {
                    s_cameraX = s_cameraXMax;
                }
                else
                {
                    s_cameraX = (short) (( -SCREEN_WIDTH + s_mapWidthByTile * TILE_SIZE) >> 1);
                }
            }
        }

        if (s_cameraY < 0)
        {
            s_cameraY = 0;
            if (s_mapWidthByTile > 0)
            {
                if (s_mapHeightByTile * TILE_SIZE < SCREEN_HEIGHT)
                {
                    s_cameraY = (short) (( -SCREEN_HEIGHT + s_mapHeightByTile * TILE_SIZE) >> 1);
                }
            }
        }
        else if (s_cameraY > s_cameraYMax)
        {
            s_cameraY = 0;
            if (s_mapWidthByTile > 0)
            {
                if (s_mapHeightByTile * TILE_SIZE >= SCREEN_HEIGHT)
                {
                    s_cameraY = s_cameraYMax;
                }
                else
                {
                    s_cameraY = (short) (( -SCREEN_HEIGHT + s_mapHeightByTile * TILE_SIZE) >> 1);
                }
            }
        }
        //死锁检查
        if (s_cameraState == CAMERA_STATE_MOVE)
        {
            if (s_cameraX == s_cameraXOld && s_cameraY == s_cameraYOld)
            {
                s_cameraState = (s_cameraStateNext == -1) ? CAMERA_STATE_STAND : s_cameraStateNext;
            }
        }
    }

    static void UpdateCameraColor()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Camera.h
        if (s_cameraColorCycle >= 0)
        {
            int i, j, k = 0;
            i = ((0xff000000 & s_cameraColorStart) >>> 24);
            j = ((0xff000000 & s_cameraColorEnd) >>> 24);
            k += ((i + (j - i) * s_cameraColorTimer / s_cameraColorCycle) << 24);

            i = ((0x00ff0000 & s_cameraColorStart) >> 16);
            j = ((0x00ff0000 & s_cameraColorEnd) >> 16);
            k += ((i + (j - i) * s_cameraColorTimer / s_cameraColorCycle) << 16);

            i = ((0x0000ff00 & s_cameraColorStart) >> 8);
            j = ((0x0000ff00 & s_cameraColorEnd) >> 8);
            k += ((i + (j - i) * s_cameraColorTimer / s_cameraColorCycle) << 8);

            i = ((0x000000ff & s_cameraColorStart) >> 0);
            j = ((0x000000ff & s_cameraColorEnd) >> 0);
            k += ((i + (j - i) * s_cameraColorTimer / s_cameraColorCycle));
            s_cameraColor = k;
            if (s_cameraColorTimer >= s_cameraColorCycle)
            {
                s_cameraColorCycle = -1;
                s_cameraColorTimer = 0;
                s_cameraColor = s_cameraColorEnd;
            }
            else
            {
                s_cameraColorTimer++;
            }
//@#if ALPHA_EFFECT
//@            for (int m = 0; m < s_argbData.length; m++)
//@            {
//@                s_argbData[m] = s_cameraColor;
//@            }
//@#endif
        }
    }

    static void SetCamera(int type, int destX, int destY, CActor a, int time)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Camera.h
        s_cameraState = type;
        if (time == 0)
        {
            s_cameraDestX = (short) (destX - SCREEN_WIDTH_HALF);
            s_cameraDestY = (short) (destY - SCREEN_HEIGHT_HALF);
            s_cameraVX = 0;
            s_cameraVY = 0;
            switch (s_cameraState)
            {
            case CAMERA_STATE_FREE:
                if (null != s_actorCameraHook)
                {
                    s_actorCameraHook.m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISCAMERA] = T_FALSE;
                }
                s_cameraStateNext = -1;
                break;
            case CAMERA_STATE_STAND:
                if (null != s_actorCameraHook)
                {
                    s_actorCameraHook.m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISCAMERA] = T_FALSE;
                }
                s_cameraStateNext = -1;
                break;
            case CAMERA_STATE_ACTOR:
                if (null != s_actorCameraHook)
                {
                    s_actorCameraHook.m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISCAMERA] = T_FALSE;
                }
                s_actorCameraHook = a;
                s_actorCameraHook.m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISCAMERA] = T_TRUE;
                s_cameraX = (short) (s_actorCameraHook.m_curX - SCREEN_WIDTH_HALF);
                s_cameraY = (short) (s_actorCameraHook.m_curY - SCREEN_HEIGHT_HALF);
                if (s_mapWidthByTile > 0)
                {
                    if (s_mapWidthByTile * TILE_SIZE < SCREEN_WIDTH)
                    {
                        s_cameraX = (short) (( -SCREEN_WIDTH + s_mapWidthByTile * TILE_SIZE) >> 1);
                    }
                    if (s_mapHeightByTile * TILE_SIZE < SCREEN_HEIGHT)
                    {
                        s_cameraY = (short) (( -SCREEN_HEIGHT + s_mapHeightByTile * TILE_SIZE) >> 1);
                    }
                }
                s_cameraStateNext = -1;
                break;
            case CAMERA_STATE_MOVE:
                if (null != s_actorCameraHook)
                {
                    s_actorCameraHook.m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISCAMERA] = T_FALSE;
                }
                s_actorCameraHook = a;
                s_cameraStateNext = -1;
                s_cameraX = (short) (s_actorCameraHook.m_curX - SCREEN_WIDTH_HALF);
                s_cameraY = (short) (s_actorCameraHook.m_curY - SCREEN_HEIGHT_HALF);
                break;
            }
        }
        else
        {
            s_cameraDestX = (short) (destX - SCREEN_WIDTH_HALF);
            s_cameraDestY = (short) (destY - SCREEN_HEIGHT_HALF);

            s_cameraVX = (short) UtilCeiling(s_cameraDestX - s_cameraX, time);
            s_cameraVY = (short) UtilCeiling(s_cameraDestY - s_cameraY, time);
            switch (s_cameraState)
            {
            case CAMERA_STATE_FREE:
                if (null != s_actorCameraHook)
                {
                    s_actorCameraHook.m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISCAMERA] = T_FALSE;
                }
                s_cameraState = CAMERA_STATE_MOVE;
                s_cameraStateNext = CAMERA_STATE_FREE;
                break;
            case CAMERA_STATE_STAND:
                if (null != s_actorCameraHook)
                {
                    s_actorCameraHook.m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISCAMERA] = T_FALSE;
                }
                s_cameraState = CAMERA_STATE_MOVE;
                s_cameraStateNext = CAMERA_STATE_STAND;
                break;
            case CAMERA_STATE_ACTOR:
                s_cameraDestX = (short) (a.m_curX - SCREEN_WIDTH_HALF);
                s_cameraDestY = (short) (a.m_curY - SCREEN_HEIGHT_HALF);
                s_cameraVX = (short) UtilCeiling(s_cameraDestX - s_cameraX, time);
                s_cameraVY = (short) UtilCeiling(s_cameraDestY - s_cameraY, time);

                s_actorCameraHook = a;
                if (null != s_actorCameraHook)
                {
                    s_actorCameraHook.m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISCAMERA] = T_FALSE;
                }
                s_actorCameraHook = a;
                s_actorCameraHook.m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISCAMERA] = T_TRUE;

                s_cameraState = CAMERA_STATE_MOVE;
                s_cameraStateNext = CAMERA_STATE_ACTOR;
                break;
            case CAMERA_STATE_MOVE:
                if (null != s_actorCameraHook)
                {
                    s_actorCameraHook.m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISCAMERA] = T_FALSE;
                }
                s_actorCameraHook = a;
                s_cameraVX = (short) UtilCeiling(s_cameraDestX - s_cameraX, time);
                s_cameraVY = (short) UtilCeiling(s_cameraDestY - s_cameraY, time);
                s_cameraState = CAMERA_STATE_MOVE;
                s_cameraStateNext = CAMERA_STATE_STAND;
                break;
            }
        }
    }

    static void UpdateShock()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Camera.h
        if (s_cameraShockTimer != 0)
        {
            for (int i = 0; i < 2; i++)
            {
                if (s_cameraShock[CAMERA_SHOCK_MX + i] > 0)
                {
                    s_cameraShock[CAMERA_SHOCK_X + i] += s_cameraShock[CAMERA_SHOCK_VX + i];
                    if (Math.abs(s_cameraShock[CAMERA_SHOCK_X + i]) >= s_cameraShock[CAMERA_SHOCK_MX + i])
                    {
                        s_cameraShock[CAMERA_SHOCK_VX + i] = (short) (s_cameraShock[CAMERA_SHOCK_VX + i] * -1);
                        if (s_cameraShock[CAMERA_SHOCK_X + i] > 0)
                        {
                            s_cameraShock[CAMERA_SHOCK_X + i] = s_cameraShock[CAMERA_SHOCK_MX + i];
                        }
                        else
                        {
                            s_cameraShock[CAMERA_SHOCK_X + i] = (short) (s_cameraShock[CAMERA_SHOCK_MX + i] * -1);
                        }
                    }
                }
                else
                {
                    s_cameraShock[CAMERA_SHOCK_X + i] = 0;
                }
            }
            if (s_cameraShockTimer > 0)
            {
                s_cameraShockTimer--;
            }
            s_cameraX += s_cameraShock[CAMERA_SHOCK_X];
            s_cameraY += s_cameraShock[CAMERA_SHOCK_Y];
        }
    }

//@@
//@@..\CGame\CGame_Package.h
    static void ModifyPackageCount(CEntity entity0, CEntity entity1, int id, int type, int icon, int list, int count, int color, int max)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Package.h
        if (null == entity0)
        { //没有同样的物品
            entity0 = CEntity.GetInstance();
            entity0.m_type = (byte) type;
            entity0.m_id = id;
            entity0.m_count = count;
            entity0.m_color = color;
            entity0.m_icon = icon;
            entity1 = s_entityList[list].SearchArticalPrev(id); //按id排列
            entity1.InsertAfter(entity0);
        }
        else
        { //有同样的物品
            entity0.m_count += count;
        }
        if (entity0.m_count <= 0)
        { //去除物品
            entity0.RemoveSelf();
            entity0.Release();
        }
        else if (max > 0)
        { //有个数限制
            entity0.m_count = Math.min(entity0.m_count, max);
        }
    }

    /**
     * 往包裹里放入东西
     * @param id
     * @param type
     * @param count
     */
    static void PackageModify(int id, int type, int count)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Package.h
        int max = 0, color = 0, icon = 0, list = ENTITY_LIST_ITEM;
        CEntity entity0 = null, entity1 = null;

        max = GetPackageLimit(type, id);
        switch (type)
        {
        case DEFINE_HOLD_TYPE_GOLD:
            PlusGameData(DEFINE_GAME_DATA_MONEY, count, max);
            if (count < 0)
            { //消费数量
                PlusGameData(DEFINE_GAME_DATA_CONSUMPTION, Math.abs(count), GAME_CONSUMP_MAX);
            }
            return;
        case DEFINE_HOLD_TYPE_ITEM:
            list = ENTITY_LIST_ITEM;
            type = s_dbItems[id][DB_ITEMS_TYPE];
            color = COLOR_DB[s_dbItems[id][DB_ITEMS_COLOR]];
            icon = s_dbItems[id][DB_ITEMS_ICON];
            break;
        case DEFINE_HOLD_TYPE_EQUIP:
            list = ENTITY_LIST_EQUIP;
            type = s_dbEquips[id][DB_EQUIPS_TYPE];
            color = COLOR_DB[s_dbEquips[id][DB_EQUIPS_COLOR]];
            icon = s_dbEquips[id][DB_EQUIPS_ICON];
            break;
        case DEFINE_HOLD_TYPE_BATTLE:
            list = ENTITY_LIST_BATTLE;
            color = COLOR_DB[s_dbItems[id][DB_ITEMS_COLOR]];
            icon = s_dbItems[id][DB_ITEMS_ICON];
            break;
        case DEFINE_HOLD_TYPE_TASK:
            list = ENTITY_LIST_MISSION;
            color = COLOR_BLACK;
            break;
        case DEFINE_HOLD_TYPE_MIXTRUE:
            list = ENTITY_LIST_MIXTRUE;
            type = s_dbMixtrue[id][DB_MIXTRUE_TYPE];
            color = COLOR_DB[s_dbMixtrue[id][DB_MIXTRUE_COLOR]];
            icon = s_dbMixtrue[id][DB_MIXTRUE_ICON];
            break;
        case DEFINE_HOLD_TYPE_TOKEN:
            PlusGameData(DEFINE_GAME_DATA_TOKEN, count, GAME_TOKEN_MAX);
            return;
        case DEFINE_HOLD_TYPE_SHOP:
            list = ENTITY_LIST_PORTABLE_SHOP;
            type = -1;
            color = COLOR_DB[s_dbShop[id][DB_SHOP_COLOR]];
            icon = s_dbShop[id][DB_SHOP_ICON];
            break;
        case DEFINE_HOLD_TYPE_GOODS:
            list = ENTITY_LIST_PORTABLE_GOODS;
            type = count;
            count = 1;
            break;
//@#if EXIST_EFFORT
//@        case DEFINE_HOLD_TYPE_EFFORT:
//@            list = ENTITY_LIST_MIXTRUE;
//@            type = s_dbEffort[id][DB_EFFORT_TYPE];
//@            color = COLOR_DB[s_dbEffort[id][DB_EFFORT_COLOR]];
//@            icon = s_dbEffort[id][DB_EFFORT_ICON];
//@            break;
//@#endif
        case DEFINE_HOLD_TYPE_EVENT:
            list = ENTITY_LIST_EVENT;
            type = s_dbEvent[id][DB_EVENT_PURPOSE];
            break;
//@#if SMS
//@        case DEFINE_HOLD_TYPE_SMS:
//@            list = ENTITY_LIST_SMS;
//@            type = s_dbSMS[id][DB_SMS_TYPE];
//@            color = COLOR_BLACK;
//@            break;
//@        case DEFINE_HOLD_TYPE_BATTLE_SMS:
//@            list = ENTITY_LIST_BATTLE_SMS;
//@            type = s_dbSMS[id][DB_SMS_TYPE];
//@            color = COLOR_BLACK;
//@            break;
//@#endif
//@#if EXIST_SCORE
//@        case DEFINE_HOLD_TYPE_SCORE:
//@            PlusGameData(DEFINE_GAME_DATA_SCORE, count, max);
//@            break;
//@#endif
        default:
            break;
        }
        //先判断有没有这个物品
        entity0 = GetEntity(list, id);
        ModifyPackageCount(entity0, entity1, id, type, icon, list, count, color, max);
    }

    static void PackageBattle(int id, int type, int count, int icon)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Package.h
        int max = 0;
        CEntity pEntity = null;
        CEntity entity0 = null, entity1 = null;
        switch (type)
        {
        case DEFINE_HOLD_TYPE_BATTLE:
        case DEFINE_HOLD_TYPE_ITEM:
            entity0 = s_entityList[ENTITY_LIST_BATTLE].SearchObject(id);
            max = s_dbItems[id][DB_ITEMS_LIMIT];
            ModifyPackageCount(entity0, entity1, id, type, icon, ENTITY_LIST_BATTLE, count, (entity0 == null ? COLOR_BLACK : entity0.m_color), max);
            break;
        case DEFINE_HOLD_TYPE_EQUIP:
            pEntity = s_entityList[ENTITY_LIST_EQUIP].m_next;
            max = s_dbEquips[id][DB_EQUIPS_LIMIT];
            while (null != pEntity)
            {
                if (pEntity.m_id == id && pEntity.m_type == type)
                {
                    entity0 = pEntity;
                }
                pEntity = pEntity.m_next;
            }
            ModifyPackageCount(entity0, entity1, id, type, icon, ENTITY_LIST_EQUIP, count, entity0.m_color, max);
            break;
        }
    }

    static int PackageGet(int id, int type)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Package.h
        int res = 0, list = 0;
        CEntity entity = null;
        switch (type)
        {
        case DEFINE_HOLD_TYPE_GOLD:
            res = s_gameData[DEFINE_GAME_DATA_MONEY];
            return res;
        case DEFINE_HOLD_TYPE_EQUIP:
            list = ENTITY_LIST_EQUIP;
            break;
        case DEFINE_HOLD_TYPE_ITEM:
            list = ENTITY_LIST_ITEM;
            break;
        case DEFINE_HOLD_TYPE_TASK:
            list = ENTITY_LIST_MISSION;
            break;
//@#if EXIST_EFFORT
//@        case DEFINE_HOLD_TYPE_EFFORT:
//@            list = ENTITY_LIST_EFFORT;
//@            break;
//@#endif
        }
        entity = GetEntity(list, id);
        if (null != entity)
        {
            res = entity.m_count;
        }
        return res;
    }

    static int GetPackageLimit(int type, int id)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Package.h
        int res = 0;
        switch (type)
        {
        case DEFINE_HOLD_TYPE_GOLD:
            res = GAME_MONEY_MAX;
            break;
        case DEFINE_HOLD_TYPE_ITEM:
        case DEFINE_HOLD_TYPE_BATTLE:
            res = s_dbItems[id][DB_ITEMS_LIMIT];
            break;
        case DEFINE_HOLD_TYPE_EQUIP:
            res = s_dbEquips[id][DB_EQUIPS_LIMIT];
            break;
        case DEFINE_HOLD_TYPE_MIXTRUE:
            res = 1;
            break;
//@#if EXIST_SCORE
//@        case DEFINE_HOLD_TYPE_SCORE:
//@            res = GAME_SCORE_MAX;
//@            break;
//@#endif
        }
        return res;
    }

    static void ReleaseAllEntity()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Package.h
        for (int i = 0; i < AMOUNT_ENTITY_LIST; i++)
        {
            s_entityList[i].ReleaseAll();
            s_entityList[i].m_next = null;
        }
        ReleaseTempEntity();
    }

    static void ReleaseTempEntity()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Package.h
        s_tempList.ReleaseAll();
        s_tempList.m_next = null;
    }

//@@
//@@..\CGame\CGame_Menu.h

    static int[] s_menuSlot = new int[AMOUNT_MENU_SLOT];

    /**这个数值不为负数说明菜单确定*/
    static int s_menuConfirmID = -1;
    static boolean s_menuLoop;

    /** @todo 调整 */
    final static int UpdateMenuSlot(int slotID, int max, int next, int prev, int confirm, boolean loop)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Menu.h
        s_menuConfirmID = -1;
        s_menuLoop = false;
        if (s_menuSlot[slotID] < max)
        {
            if (IsKeySinglePressed(next)) //add键一次按键完成
            {
                s_menuSlot[slotID]++;
                if (!loop)
                {
                    s_menuSlot[slotID] = Math.min(s_menuSlot[slotID], max - 1);
                }
            }
            else if (IsKeySinglePressed(prev)) //minus键一次按键完成
            {
                s_menuSlot[slotID]--;
                if (!loop)
                {
                    s_menuSlot[slotID] = Math.max(s_menuSlot[slotID], 0);
                }
            }
        }
        if (IsKeySinglePressed(confirm))
        {
            s_menuConfirmID = s_menuSlot[slotID];
//@#if RUN_SCRIPT_IN_IGM
            CheckUILimit();
//@#endif
        }
        if (loop)
        {
            if (s_menuSlot[slotID] < 0 || s_menuSlot[slotID] >= max)
            {
                s_menuSlot[slotID] = (s_menuSlot[slotID] + max) % max;
                s_menuLoop = true;
            }
        }
        return s_menuSlot[slotID];
    }

//@@
//@@..\CGame\CGame_MainMenu.h
    static void FreeMainMenu()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_MainMenu.h
        if (s_imgSplash != null)
        {
            s_imgSplash = null;
        }
        if (s_actorSplash != null)
        {
            s_actorSplash = null;
        }
        if (s_actorMainMenu != null)
        {
            s_actorMainMenu = null;
        }
//@#if MORE_GAME_LINK
        if (s_imgMoreGame != null)
        {
            s_imgMoreGame = null;
        }
//@#endif
        System.gc();
    }

    /**
     * 读取主菜单资源
     */
    static void UpdateLoadMenu()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_MainMenu.h
        switch (s_loadProgress)
        {
        case 0:
            if ((s_optionFlag & OPTION_FLAG_SOUND) != 0)
            {
                SndStop();
            }
            //释放一些关卡资源
            FreeShadowRes();
            FreeScene();
            break;
        case 1:
            FreeMap();
            FreeScript();
            FreeImage();
            break;
        case 2:
            ReleaseAllEntity();
            FreeBattleActor();
            break;
        case 3:
            FreeHeroesRes();
            FreeBufferImage();
            break;
        case 4:
            s_isRenderEffect = false;
            s_effectRange = 0;
            s_effectRangeTarget = 0;
            s_layerCount = 0;
            UncontrolActor();
            s_wndMainMenu.RemoveAllChildren();
            for (int i = 0; i < AMOUNT_PRELOAD; i++)
            {
                s_preLoadData[i] = 0;
            }
//@#if RUN_SCRIPT_IN_IGM
            s_UILimit[UI_LIMIT_CUSOR] = -1;
//@#endif
            break;
        case 6:
            //初始化主菜单窗口
            s_wndMainMenu.Init(WND_MAINMENU);
            s_wndMainMenu.m_show = true;
            s_wndMainMenu.m_isTop = true;
            for (int i = 0; i < DEFINE_AMOUNT_GAME_DATA; i++)
            {
                SetGameData(i, i == DEFINE_GAME_DATA_DISCOUNT ? 100 : 0);
            }
            break;
        case 8:
            if (s_imgSplash == null)
            {
                try
                {
                    s_imgSplash = Image.createImage("/splashAnim");
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
                s_actorSplash = new CActor(ANIM_SPLASH_ANIM, ANIM_SPLASH_ANIM, ACTION_SPLASH_ANIM_SPLASH, s_imgSplash, false);
                s_actorMainMenu = new CActor(ANIM_SPLASH_ANIM, ANIM_SPLASH_ANIM, ACTION_SPLASH_ANIM_SPLASH, s_imgSplash, true);
            }

//@#if RENDER_ANIM_MAIN_MENU
//@            s_skipSplash = false;
//@#endif
            break;
        case 10:
            for (int i = 0; i < TEAM_SIZE; i++)
            {
                s_actorCommon[i].ReleaseAllSkill();
            }
            break;
        case 14:

//@#if Channel == "GF"
//@#if MORE_GAME_CONFIRM
            try
            {

                s_imgMoreGame = Image.createImage("/link");

            }
            catch (IOException ex1)
            {
            }
//@#endif
//@#endif
            System.gc();
            break;
        case 16:
            if ((s_optionFlag & OPTION_FLAG_SOUND) != 0)
            {
                SndPlay(SOUND_MENU);
            }
            else
            {
                s_curSnd = SOUND_MENU;
                SndStop();
            }
            SetGameState(GS_SPLASH);
            for (int i = 0; i < AMOUNT_MI_IGM_RMS; i++)
            {
                if (s_rmsInfo[i][RMS_INFO_RMS] > 0)
                {
                    //有存档 主菜单默认显示继续游戏
                    s_menuSlot[MENU_SLOT_MAINMENU] = 1;
                }
            }
            break;
        }
        s_loadProgress++;
    }
//@@
//@@..\CGame\CGame_IGM.h
    static void OpenTopWnd(int type, int x, int y, int w, int h)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        s_wnd = CWnd.GetInstance();
        s_wnd.Init(type, x, y, w, h, true);
        s_wnd.m_isTop = true;
//@#if RUN_SCRIPT_IN_IGM
        s_UIScriptCmdCur = 0;
        s_UILimit[UI_LIMIT_CUSOR] = -1;
        s_isRunScript = true;
//@#endif
        SetGameEP(EP_IGM);
    }

    static int GetMenuItem(int index, int type)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int count = 0;
        CEntity entity = s_entityList[index].m_next;
        CEntity newEntity;

        while (entity != null)
        {
            if (entity.m_type == type)
            {
                newEntity = CEntity.GetInstance();
                newEntity.Clone(entity);
                s_tempList.GetLast().InsertAfter(newEntity);
                count++;
            }
            entity = entity.m_next;
        }
        return count;
    }

    static void SetSceneUIAction(CActor actor, int action)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        if (action >= 0)
        {
            actor.SetAction(action);
            actor.SetPos(0, 0);
            actor.m_repeat = true;
        }
    }

    static void RenderMessage(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];

        DrawTextInRectByScroll(g, wnd.m_title, x + IGM_MESSAGE_INFO_X, y + IGM_MESSAGE_INFO_Y, w - (IGM_MESSAGE_OFF_X << 1), wnd.m_textPosMax,
                               ALIGN_LEFT_TOP, COLOR_BLACK, COLOR_BLACK, SCROLL_DIR_UP, 0);
    }

    static void CloseAllWnd()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        if (null != s_wnd)
        {
            s_wnd.Release();
            s_wnd = null;
            s_autoUICmdsCount = 0;
//@#if RUN_SCRIPT_IN_IGM
            s_UIScriptNum = 0;
            s_actorMenuUICount = 0;
//@#endif
        }
    }

    static void ResizeWndWithTitle(CWnd wnd, int title, int extraPos)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int posMax = CalcTextPos(GetString(title), wnd.m_rect[RECT_W] - (IGM_CONFIRM_OFF_X << 1), SCROLL_DIR_UP);
        wnd.m_title = title;
        wnd.ResetTextPos(posMax);
        wnd.m_rect[RECT_H] = posMax + (IGM_CONFIRM_OFF_Y << 1) + extraPos;
        wnd.m_rect[RECT_Y] = SCREEN_HEIGHT_HALF - (wnd.m_rect[RECT_H] >> 1);
    }

    static void UpdateMessageQueue(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        s_battleAnimFalg = BATTLE_ANIM_NONE;
        if (IsKeySinglePressed(GKEY_OK) || wnd.m_state == WND_STATE_RETURN)
        {
            wnd.m_state = WND_STATE_RETURN;
            wnd.ReturnFocus();
            wnd.m_show = false;
            if (s_curGameEP == EP_BATTLE && s_wndBattleDesc.m_show)
            {
                s_wndBattleDesc.m_show = false;
            }
        }
    }

    static void UpdateMessage(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        if (IsKeySinglePressed(GKEY_OK))
        {
            wnd.m_state = WND_STATE_YES;
            wnd.Close();
//            if (null != wnd.m_parent)
//            {
//                wnd.m_parent.m_focusID = -1;
//            }
//            if (wnd.m_isTop)
//            {
//                CloseAllWnd();
//            }
        }
    }

    static void UpdateLootItem(CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        if (IsKeySinglePressed(GKEY_MOVEUP))
        {
            wnd.m_listStartPos--;
            wnd.m_listStartPos = Math.max(wnd.m_listStartPos, 0);
        }
        else if (IsKeySinglePressed(GKEY_MOVEDOWN))
        {
            wnd.m_listStartPos++;
            wnd.m_listStartPos = Math.min(wnd.m_listStartPos, Math.max(wnd.m_count - wnd.m_showNum, 0));
        }
        else if (IsKeySinglePressed(GKEY_OK | GKEY_SOFTRIGHT))
        {
            wnd.Close();
        }
    }

    static CActor[] s_actorLevelUp = new CActor[BATTLE_ALLY_MAX];

    static void UpdateLootExp(CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CWnd wndLearn = wnd.m_children[0];
        int dataID = 0, curExp = 0, actorID = 0;
        boolean levelUP = false;

        for (int i = 0; i < wnd.m_count; i++)
        {
            if (s_actorLevelUp[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] == T_TRUE)
            {
                s_actorLevelUp[i].UpdateAnimation();
            }
        }

        if (wnd.m_timer < LOOT_EXP_INCREASE_TIME)
        {
            for (int i = 0; i < wnd.m_count; i++)
            {
                actorID = wnd.m_data[dataID];
                curExp = wnd.m_timer == LOOT_EXP_INCREASE_TIME - 1 ? wnd.m_data[dataID + 1] : wnd.m_data[dataID + 2] / LOOT_EXP_INCREASE_TIME;
                levelUP = ActorAddExp(actorID, curExp, false);
                wnd.m_data[dataID + 1] -= curExp;
                dataID += WND_LOOT_EXP_DATA_PER;
                if (levelUP)
                {
                    int skillID = LearnNewSkill(actorID);
                    s_actorLevelUp[i].SetAction(ACTION_UI_LEVEL_UP);
                    s_actorLevelUp[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] = T_TRUE;
                    if (skillID >= 0)
                    {
                        wndLearn.AddData(actorID);
                        wndLearn.AddData(skillID);
                    }
                }
            }
            wnd.m_timer++;
            return;
        }

        if (wndLearn.m_state == WND_STATE_RETURN)
        {
            wndLearn.m_state = WND_STATE_WAIT;
            wnd.Close();
            return;
        }

        if (IsKeySinglePressed(GKEY_OK | GKEY_SOFTRIGHT))
        {
            if (wndLearn.m_dataCount > 0)
            {
                wndLearn.DoModule();
            }
            else
            {
                wnd.Close();
            }
        }
    }

    static void UpdateLootNewSkill(CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        if (IsKeySinglePressed(GKEY_OK | GKEY_SOFTRIGHT))
        {
            if (wnd.m_count < wnd.m_dataCount / WND_LOOT_LEARN_DATA_PER - 1)
            {
                wnd.m_count++;
            }
            else
            {
                wnd.m_state = WND_STATE_RETURN;
                wnd.Close();
            }
        }
    }

    static void GetEntityIndex(int type, CWnd wnd, CEntity entity)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CEntity newEntity = null;
        CEntity lastEntity = s_tempList;
        int count = 0;
        while (entity.m_next != null)
        {
            entity = entity.m_next;
            if (type < 0 || entity.m_type == type)
            {
                newEntity = CEntity.GetInstance();
                newEntity.Clone(entity);
                lastEntity.InsertAfter(newEntity);
                lastEntity = newEntity;
                count++;
            }
        }
        wnd.m_count = count;
        wnd.m_curSelect = s_tempList.m_next;
        wnd.m_showFirst = wnd.m_curSelect;
        wnd.m_listStartPos = 0;
        s_menuSlot[wnd.m_menuSlot] = 0;
    }

    static void GetEquipList(int type, CWnd wnd, int actorID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CEntity entity = s_entityList[ENTITY_LIST_EQUIP];
        CEntity newEntity = null;
        int count = actorID >= 0 ? 1 : 0;
        if (actorID >= 0)
        {
            newEntity = CEntity.GetInstance();
            newEntity.m_id = -1;
            newEntity.m_color = ENUM_EQUIPS_COLOR_BLACK;
            newEntity.m_count = GAME_ICON_NUM_MAX;
            newEntity.m_icon = FRAME_ICON_TAKE_OFF;
            s_tempList.InsertAfter(newEntity);
        }
        while (entity.m_next != null)
        {
            entity = entity.m_next;
            if (entity.m_type != type)
            {
                continue;
            }
            if (actorID < 0 || (s_dbEquips[entity.m_id][DB_EQUIPS_SUITABLE] & (1 << actorID)) != 0)
            {
                newEntity = CEntity.GetInstance();
                newEntity.Clone(entity);
                s_tempList.InsertAfter(newEntity);
                count++;
            }
        }
        wnd.m_curSelect = s_tempList.m_next;
        wnd.m_showFirst = wnd.m_curSelect;
        wnd.m_listStartPos = 0;
        s_menuSlot[wnd.m_menuSlot] = 0;
        wnd.m_count = count;
    }

    static void UpdateSoftKey(CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h

    }

    static void RenderSoftKey(Graphics g, CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];

        RenderInformation(g, SOFTKEY_UI_CONFIRM, x, y + h - UI_SOFTKE_Y, MI_ART_FRAME_FONT);
        RenderInformation(g, SOFTKEY_UI_CANCEL, x + w - UI_SOFTKE_X, y + h - UI_SOFTKE_Y, MI_ART_FRAME_FONT);

    }
    static void UpdateConfirm(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        switch (wnd.m_select)
        {
        case MI_CONFIRM_YES:
            wnd.Close();
            wnd.m_state = WND_STATE_YES;
            break;
        case MI_CONFIRM_NO:
            wnd.Close();
            wnd.m_state = WND_STATE_NO;
            break;
        }

        if (wnd.m_state == WND_STATE_RETURN)
        {
            wnd.Close();
            wnd.m_state = WND_STATE_NO;
        }
    }


    static void RenderConfirm(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int offX = 0;
        int itemY = wnd.m_textPosMax + IGM_CONFIRM_OFF_Y + FONT_HEIGHT;
        int rectY = itemY - FONT_HEIGHT - 1;

        if (wnd.m_title >= 0)
        {
            DrawTextInRectByScroll(g, wnd.m_title, x + IGM_CONFIRM_TITLE_X, y + IGM_CONFIRM_TITLE_Y, w - (IGM_CONFIRM_OFF_X << 1), wnd.m_textPosMax,
                                   ALIGN_HCENTER_TOP, COLOR_BLACK, COLOR_BLACK, SCROLL_DIR_UP, 0);
        }

        for (int i = 0; i < wnd.m_count; i++)
        {
            if (i == s_menuSlot[wnd.m_menuSlot])
            {
                UtilFillRect(g, IGM_MENUTIP_COLOR, x + IGM_CONFIRM_RECT_X + offX, y + rectY, IGM_CONFIRM_RECT_W, IGM_CONFIRM_RECT_H);
            }
            DrawString(g, STR_SYSTEM_YES + i, x + IGM_CONFIRM_ITEM_X + offX, y + itemY, ALIGN_HCENTER_BOTTOM, COLOR_BLACK, COLOR_BLACK);
            offX += IGM_CONFIRM_ITEM_OFFX;
        }
    }

    /**游戏中菜单活动的部分*/
    static int s_teamMateCount = 0;
    static int s_formationMateCount = 0;
    static int[] s_teamMateActorID = new int[TEAM_SIZE];
    static int[] s_formationMateActorID = new int[FORMATION_SIZE];

    static void UpdateIGM(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int type;
        CWnd child = wnd.m_children[0];
//@#if PHONE_TOUCH
//@        CWnd soft = wnd.m_children[1];
//@#endif
        if (child.m_state == WND_STATE_RETURN)
        {
//@#if PHONE_TOUCH
//@            soft.m_show = true;
//@#endif
            child.m_state = WND_STATE_WAIT;
            child.RemoveAllChildren();
            child.m_show = false;
            child.Free();
            child.m_parent = wnd;
            s_needRefreshBG = true;
            return;
        }

        if (wnd.m_state == WND_STATE_PAGEUP || wnd.m_state == WND_STATE_PAGEDOWN || wnd.m_state == WND_STATE_CYCEL)
        {
            s_needRefreshBG = true;
        }

        if (wnd.m_select >= 0)
        {
            ReleaseTempEntity();
            type = GetIGMPage(s_menuSlot[MENU_SLOT_IGM]);
//@#if PHONE_TOUCH
//@            soft.m_show = true;
//@#endif
            child.Init(type, IGM_X, IGM_Y, IGM_W, IGM_H, true);
            child.DoModule();
        }
        else if (wnd.m_state == WND_STATE_RETURN)
        {
            SetSceneUIAction(s_actorUI, s_UIAction);
            wnd.Close();
            wnd.s_UICtrlMode = UI_CONTROL_MANUAL; //解决自动脚本 无法再把控制权交给玩家 导致调出菜单卡
            return;
        }

        if (wnd.m_rect[RECT_W] < IGM_TOP_W)
        {
            wnd.m_rect[RECT_W] += IGM_TOP_W_INIT;
            s_needRefreshBG = true;
        }
    }

    static void RenderIGM(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int curItem = s_menuSlot[wnd.m_menuSlot];
        int curX = IGM_ICON_X;

//@#if JAR_LIMIT_300K
//@//        s_actorUI.DrawFrame(g,SCREEN_WIDTH_HALF,y+IGM_ICON_Y,FRAME_UI_IGM_ITEM0_NAME+curItem);
//@        DrawString(g,STR_SYSTEM_IGM_ROLE+curItem,SCREEN_WIDTH_HALF,y+IGM_ICON_Y,ALIGN_HCENTER_TOP,COLOR_BLACK,COLOR_WHITE);
//@        s_actorUI.DrawModule(g, MODULE_UI_ARROW, SCREEN_WIDTH_HALF - 50, y + IGM_ICON_Y, TRANS_ROT90);
//@        s_actorUI.DrawModule(g, MODULE_UI_ARROW, SCREEN_WIDTH_HALF + 35, y + IGM_ICON_Y, TRANS_ROT270);
//@        if (wnd.m_state == WND_STATE_PAGEDOWN)
//@        {
//@            s_actorUI.DrawModule(g, MODULE_UI_ARROW, SCREEN_WIDTH_HALF - 50, y + IGM_ICON_Y, TRANS_ROT90);
//@            s_actorUI.DrawModule(g, MODULE_UI_ARROW, SCREEN_WIDTH_HALF + 37, y + IGM_ICON_Y, TRANS_ROT270);
//@        }
//@        else if (wnd.m_state == WND_STATE_PAGEUP)
//@        {
//@            s_actorUI.DrawModule(g, MODULE_UI_ARROW, SCREEN_WIDTH_HALF - 52, y + IGM_ICON_Y, TRANS_ROT90);
//@            s_actorUI.DrawModule(g, MODULE_UI_ARROW, SCREEN_WIDTH_HALF + 35, y + IGM_ICON_Y, TRANS_ROT270);
//@        }
//@#else
        for (int i = 0; i < wnd.m_count; i++)
        {
            if (curX < w)
            {
                s_actorUI.DrawFrame(g, x + curX, y + IGM_ICON_Y, FRAME_UI_IGM_ITEM0 + i);
            }
            curX += IGM_ICON_OFFX;
        }
        curX = x + IGM_CUSOR_X + curItem * IGM_ICON_OFFX;
        s_actorUI.DrawFrame(g, curX, y + IGM_ITEM_NAME_Y, FRAME_UI_IGM_ITEM0_NAME + curItem);
//@#if AMIN_IGM_CURSOR
//@        s_actorUI.DrawAnimation(g, -5, -5);
//@#else
        s_actorUI.DrawFrame(g, curX, y + IGM_CUSOR_Y, FRAME_UI_CURSOR);
//@#endif
//@#endif
    }

    static void RenderIGMCommon(Graphics g, int x, int y, int w, int h, int title)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
//@#if JAR_LIMIT_300K
//@        DrawString(g,title,x + (w >> 1), y ,ALIGN_HCENTER_TOP,COLOR_BLACK,COLOR_WHITE);
//@#else
        s_actorUI.DrawFrame(g, x + (w >> 1), y, title);
        s_actorUI.DrawFrame(g, x + (w >> 1), y, FRAME_UI_IGM_TITLE_DECO);
//@#endif
        RenderGold(g, DEFINE_GAME_DATA_MONEY, x + IGM_MONEY_X, y + h - IGM_INFO_Y);
        RenderGold(g, DEFINE_GAME_DATA_TOKEN, x + IGM_TOKEN_X, y + h - IGM_INFO_Y);
    }

    static void RenderGold(Graphics g, int type, int x, int y)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        s_actorUI.DrawModule(g, MODULE_UI_ICON_GOLD + type, x, y);
        UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, s_gameData[type], x + IGM_INFO_NUM_X, y + IGM_INFO_NUM_Y, ALIGN_LEFT_TOP);
    }

    static void RenderScrollBar(Graphics g, int skin, int x, int y, int h, int cur, int max)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        s_actorUI.DrawFrame(g, x, y, skin);
        s_actorUI.DrawFrame(g, x, y + h, skin + 1);
        g.setColor(SCROLL_BAR_COLOR0);
        g.drawLine(x, y, x, y + h);
        g.setColor(SCROLL_BAR_COLOR1);
        g.drawLine(x + 1, y, x + 1, y + h);
        if (max > 1)
        {
            s_actorUI.DrawFrame(g, x, y + (cur * h / (max - 1)), skin + 2);
        }
    }

    static void UpdateIGMDesc(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int h = wnd.m_rect[RECT_H];
        UpdateWndTextPos(wnd, h - (IGM_DESC_TEXT_Y << 1), IGM_DESC_TEXT_WAIT_TIME);
    }

    static void RenderIGMDesc(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        int strID = -1, type = wnd.m_data[IGM_DESC_DATA_TYPE];

        if(wnd.m_data[IGM_DESC_SHOW_CHAR] > 0)//如果显示描述文字
        {
            int id = wnd.m_data[IGM_DESC_DATA_ID];
            if (id >= 0)
            {
                switch (type)
                {
                case ENTITY_LIST_ITEM:
                    strID = STR_SYSTEM_ITEMS_DESC_0;
                    break;
                case ENTITY_LIST_SKILL:
                    strID = STR_SYSTEM_SKILLS_DESC_0;
                    break;
                case ENTITY_LIST_EQUIP:
                    strID = STR_SYSTEM_EQUIPS_DESC_0;
                    break;
                case ENTITY_LIST_MIXTRUE:
                    strID = STR_SYSTEM_MIXTRUE_DESC_0;
                    break;
                case ENTITY_LIST_MISSION:
                    strID = STR_SYSTEM_TASKS_DESC_0;
                    break;
                }
                DrawTextInRectByScroll(g, strID + id, x + IGM_DESC_TEXT_X, y + IGM_DESC_TEXT_Y, w - (IGM_DESC_TEXT_X << 1), h - (IGM_DESC_TEXT_Y << 1),
                                       ALIGN_HCENTER_TOP, COLOR_BLACK, COLOR_BLACK, SCROLL_DIR_UP, wnd.m_timer);
            }
            else
            {
                switch (type)
                {
                case ENTITY_LIST_EQUIP:
                    strID = STR_SYSTEM_TAKE_OFF;
                    id = 0;
                    break;
                default:
                    break;
                }
                if (strID >= 0)
                {
                    DrawLineString(g, strID, x + (w >> 1), y + IGM_DESC_TEXT_Y, ALIGN_HCENTER_TOP, COLOR_BLACK, COLOR_BLACK, 8, 0, 2);
                }
            }
        }
    }

    static void UpdateBattleDesc(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int h = wnd.m_rect[RECT_H];
        UpdateWndTextPos(wnd, h - (IGM_DESC_TEXT_Y << 1), BATTLE_DESC_TEXT_WAIT_TIME);
    }

    static void RenderBattleDesc(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        int strID = 0;
        int id = wnd.m_data[BATTLE_DESC_DATA_ID];
        switch (wnd.m_data[BATTLE_DESC_DATA_TYPE])
        {
        case ENTITY_LIST_BATTLE:
            strID = STR_SYSTEM_ITEMS_DESC_0;
            break;
        case ENTITY_LIST_SKILL:
            strID = STR_SYSTEM_SKILLS_DESC_0;
            break;
        }
        if (id >= 0)
        {
            DrawTextInRectByScroll(g, strID + id, x + IGM_DESC_TEXT_X, y + IGM_DESC_TEXT_Y, w - (IGM_DESC_TEXT_X << 1), h - (IGM_DESC_TEXT_Y << 1),
                                   ALIGN_HCENTER_TOP, COLOR_BLACK, COLOR_BLACK, SCROLL_DIR_UP, wnd.m_timer);
        }
    }

    static void UpdateIGMStatusDesc(CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        UpdateWndTextPos(wnd, IGM_STATE_DESC_H - 10, IGM_DESC_TEXT_WAIT_TIME);
        if (wnd.m_state == WND_STATE_RETURN)
        {
            wnd.m_data[IGM_DESC_SHOW_CHAR] = T_FALSE;
            wnd.Close();
            return;
        }
    }

    static void RenderIGMStatusDesc(Graphics g, CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        int curItem = s_menuSlot[wnd.m_menuSlot];
        DrawTextInRectByScroll(g, STR_SYSTEM_HERO_0_STATUS_DESC + s_teamMateActorID[curItem], x + IGM_DESC_TEXT_X, y + IGM_DESC_TEXT_Y, w - (IGM_DESC_TEXT_X << 1),
                               h - (IGM_DESC_TEXT_Y << 1),
                               ALIGN_HCENTER_TOP, COLOR_BLACK, COLOR_BLACK, SCROLL_DIR_UP, wnd.m_timer);
    }
    static void UpdateIGMStatus(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CWnd wndDesc = wnd.m_children[wnd.m_childCount - 3];
        CWnd wndMessage = wnd.m_children[wnd.m_childCount - 2];
        byte feedback;
        int curItem = s_menuSlot[wnd.m_menuSlot];

        for (int i = 0; i < wnd.m_count; i++)
        {
            if (i == curItem)
            {
                wnd.m_children[i].m_data[IGM_STATUS_DATA_SHOWFOUCS] = T_TRUE;
            }
            else
            {
                wnd.m_children[i].m_data[IGM_STATUS_DATA_SHOWFOUCS] = T_FALSE;
            }
        }

        if (wnd.m_select >= 0)
        {
            if (wnd.m_data[IGM_STATUS_ISTARGET] == T_FALSE)
            {
//@#if CAN_CHANGE_FORMATION
//@                feedback = ChangeFormation(s_teamMateActorID[wnd.m_select]);
//@                if (feedback > 0)
//@                {
//@                    ResizeWndWithTitle(wndMessage, STR_SYSTEM_TEAM_EMPTY + feedback - 1, 0);
//@                    wndMessage.DoModule(FRAME_LIMIT);
//@                }
//@                else
//@                {
//@                    RefreshTeam();
//@                }
//@#else
                wndDesc.DoModule();
                wndDesc.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_HERO_0_STATUS_DESC + curItem), wndDesc.m_rect[RECT_W], SCROLL_DIR_UP));
//@#endif
            }
            else
            {
                wnd.m_state = WND_STATE_RETURN;
                wnd.ReturnFocus();
            }
        }
        else if (wnd.m_state == WND_STATE_RETURN)
        {
            wnd.ReturnFocus();
            s_menuSlot[wnd.m_menuSlot] = 0;
            for (int i = 0; i < wnd.m_count; i++)
            {
                wnd.m_children[i].m_data[IGM_STATUS_DATA_SHOWFOUCS] = T_FALSE;
            }
            if (wnd.m_data[IGM_STATUS_ISTARGET] == T_TRUE)
            {
                wnd.m_state = WND_STATE_RETURN;
            }
        }
    }

    static void RenderIGMStatus(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
//@#if JAR_LIMIT_300K
//@        RenderIGMCommon(g, x, y, w, h, STR_SYSTEM_IGM_ROLE);
//@#else
        RenderIGMCommon(g, x, y, w, h, FRAME_UI_TITLE_STATUS);
//@#endif

    }

    static void UpdateIGMStatusRole(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        if (wnd.m_state == WND_STATE_RETURN)
        {
            wnd.Close();
            return;
        }

    }

    static void RenderIGMStatusRole(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int id = wnd.m_id;
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int actorID = wnd.m_data[IGM_STATUS_DATA_ACTORID];
        int showFoucs = wnd.m_data[IGM_STATUS_DATA_SHOWFOUCS];
        CActor actor = s_actorCommon[actorID];
        g.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
//@#if JAR_LIMIT_300K
//@        DrawString(g,STR_SYSTEM_ALLY0 + actor.m_classID,x + IGM_STATE_ICON_X, y + IGM_STATE_ICON_Y,ALIGN_LEFT_TOP,COLOR_BLACK,COLOR_WHITE);
//@        s_actorUI.DrawModule(g, MODULE_UI_LEVEL, x + IGM_STATE_ICON_X, y + IGM_STATE_LV_Y, TRANS_NONE);
//@        UtilRenderUINumber(g, MODULE_NUMBERS_0_G, false, actor.m_value[BV_LV]+1, x + IGM_STATE_LV_X, y + IGM_STATE_LV_Y,
//@                             ALIGN_LEFT_TOP);
//@        if (showFoucs == T_TRUE && id == s_menuSlot[MENU_SLOT_STATUS])
//@        {
//@            s_actorUI.DrawFrame(g, x + IGM_STATE_CURSOR_X, y + IGM_STATE_CURSOR_Y, FRAME_UI_CURSOR);
//@        }
//@        for (int i = 0; i < IGM_STATUS_VALUE_SHOWNUM; i++)
//@        {
//@            RenderActorData(g, i, actor, x + IGM_STATE_VALUENAME_X, y + IGM_STATE_VALUENAME_Y, UI_ACTOR_VALUEBAR_W);
//@            y += IGM_STATE_VALUENAME_OFFSET;
//@        }
//@
//@#else
        s_actorUI.DrawFrame(g, x + IGM_STATE_ICON_X, y + IGM_STATE_ICON_Y, FRAME_UI_IGM_ROLE_0 + actor.m_classID);

        UtilRenderUINumber(g, MODULE_NUMBERS_0_G, false, actor.m_value[BV_LV]+1, x + IGM_STATE_LV_X, y + IGM_STATE_LV_Y,
                               ALIGN_LEFT_TOP);
        if (showFoucs == T_TRUE && id == s_menuSlot[MENU_SLOT_STATUS])
        {
            s_actorUI.DrawFrame(g, x + IGM_STATE_CURSOR_X, y + IGM_STATE_CURSOR_Y, FRAME_UI_CURSOR);
        }
//@#if CAN_CHANGE_FORMATION
//@        RenderInformation(g, actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_INFORMATION] == T_TRUE ? IGM_UI_STATUS_INFORMATION_0 : IGM_UI_STATUS_INFORMATION_1,
//@                          x + IGM_ROLESTATE_INFORMATION_X, y + IGM_ROLESTATE_TEAM_OFFY + IGM_ROLESTATE_INFORMATION_Y, IGM_UI_STATUS_INFORMATION);
//@#endif
//        RenderActorLV(g, actor, x + IGM_STATE_LV_X, y + IGM_STATE_LV_Y);
        for (int i = 0; i < IGM_STATUS_VALUE_SHOWNUM; i++)
        {
            RenderActorData(g, i, actor, x + IGM_STATE_VALUENAME_X, y + IGM_STATE_VALUENAME_Y, UI_ACTOR_VALUEBAR_W);
            y += IGM_STATE_VALUENAME_OFFSET;
        }
//@#endif
    }

    static void RenderActorData(Graphics g, int type, CActor actor, int x, int y, int w)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int dataCur = 0, dataMax = 0;

        switch (type)
        {
        case ACTOR_DATA_TYPE_HP:
            dataCur = actor.m_value[BV_HP];
            dataMax = actor.m_value[BV_HPMAX];
            break;
        case ACTOR_DATA_TYPE_MP:
            dataCur = actor.m_value[BV_MP];
            dataMax = actor.m_value[BV_MPMAX];
            break;
        case ACTOR_DATA_TYPE_EXP:
            dataCur = actor.m_value[BV_EXP];
            dataMax = actor.m_value[BV_NEXTEXP];
            break;
        }
        RenderValueBar(g, type, x, y, UI_ACTOR_VALUEBAR_W, dataCur * 100 / dataMax);
        s_actorUI.DrawModule(g, MODULE_UI_ICON_HP + type, x + 1, y + UI_ACTOR_VALUE_NAME_Y);
        UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, dataCur, x + w - UI_ACTOR_VALUECUR_X, y - UI_ACTOR_VALUE_Y, ALIGN_RIGHT_TOP);
    }

    static void RenderActorSP(Graphics g, CActor actor, int x, int y)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int sp = actor.m_value[BV_SP], spMax = actor.m_value[BV_SPMAX];
        int h;
        if (spMax <= 0)
        {
            return;
        }
        if (sp < spMax)
        {
            s_actorUI.DrawFrame(g, x, y, FRAME_UI_SP_EMPTY);
            h = sp * UI_ACTOR_SP_CUR_H / spMax;
            g.setClip(x - UI_ACTOR_SP_CUR_X, y - UI_ACTOR_SP_CUR_Y + UI_ACTOR_SP_CUR_H - h, UI_ACTOR_SP_CUR_W, h);
            s_actorUI.DrawModule(g, MODULE_UI_CUR_SP, x - UI_ACTOR_SP_CUR_X, y - UI_ACTOR_SP_CUR_Y);
        }
        else
        {
            s_actorUI.DrawFrame(g, x, y, FRAME_UI_SP_FULL);
        }
    }

    static void RenderActorLV(Graphics g, CActor actor, int x, int y)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        s_actorUI.DrawModule(g, MODULE_UI_LEVEL, x, y);
        UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, actor.m_value[BV_LV]+1 ,x + 25, y, ALIGN_RIGHT_TOP);
    }

    static void CalcRoleValue(CWnd wnd, CActor actor, int id)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        wnd.m_title = id;
        int count = BV_SPEED - BV_HPMAX + 1;
        for (int i = 0; i < count; i++)
        {
            wnd.AddData(actor.m_value[BV_HPMAX + i] - s_effectValue[DB_EFFECT_SET_ENUMERATE_EXTRA_HP + i]);
            wnd.AddData(actor.m_value[BV_HPMAX + i]);
        }
    }

    static void UpdateIGMRoleValue(CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        if (IsKeySinglePressed(GKEY_MOVEUP))
        {
            if (wnd.m_listStartPos > 0)
            {
                wnd.m_listStartPos--;
            }
            return;
        }
        else if (IsKeySinglePressed(GKEY_MOVEDOWN))
        {
            if (wnd.m_listStartPos <= wnd.m_count - wnd.m_showNum - 1)
            {
                wnd.m_listStartPos++;
            }
            return;
        }
        else if (IsKeySinglePressed(GKEY_OK | GKEY_SOFTRIGHT))
        {
            wnd.m_state = WND_STATE_RETURN;
            wnd.Close();
            return;
        }
    }

    static void RenderIGMRoleValue(Graphics g, CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int start = wnd.m_listStartPos;

        DrawString(g, wnd.m_title, x + (w >> 1), y + IGM_ROLE_VALUE_TITLE_Y, ALIGN_HCENTER_TOP, COLOR_BLACK, COLOR_BLACK);
        RenderScrollBar(g, FRAME_UI_SCROLL_BAR_TOP, x + w - IGM_ROLE_VALUE_SCROLL_X, y + IGM_ROLE_VALUE_SCROLL_Y, IGM_ROLE_VALUE_SCROLL_H, start,
                        wnd.m_count - wnd.m_showNum + 1);
        for (int i = start; i < wnd.m_showNum + start; i++)
        {
            s_actorUI.DrawModule(g, MODULE_UI_VALUE_HP + i, x + IGM_ROLE_VALUE_NAME_X, y + IGM_ROLE_VALUE_NAME_Y);
            UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, wnd.m_data[i * WND_ROLE_VALUE_DATA_PER], x + IGM_ROLE_VALUE_OLD_X, y + IGM_ROLE_VALUE_NUM_Y,
                               ALIGN_RIGHT_TOP);
            UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, wnd.m_data[i * WND_ROLE_VALUE_DATA_PER + 1], x + IGM_ROLE_VALUE_NEW_X, y + IGM_ROLE_VALUE_NUM_Y,
                               ALIGN_RIGHT_TOP);
            y += IGM_ROLE_VALUE_OFF_Y;
        }
    }

    static void RenderIGMEquip(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CWnd wndRole = wnd.m_children[0];
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        int i, j, curItem = s_menuSlot[wnd.m_menuSlot];
        int id = s_teamMateActorID[s_menuSlot[wndRole.m_menuSlot]];
        int moduleID = MODULE_UI_VALUE_ATTACK;
        int curX = x + IGM_ROLEEQUIP_VALUE_X;
        int curY = y + IGM_ROLEEQUIP_VALUE_Y;
        CActor actor = s_actorCommon[id];

        for (i = 0; i < wnd.m_count; i++)
        {
            //装备 选项框 和装备icon
            s_actorUI.DrawFrame(g, x + IGM_ROLEEQUIP_ICON_X[i], y + IGM_ROLEEQUIP_ICON_Y[i], FRAME_UI_ICON_BG);
            if (actor.m_equips[i] >= 0)
            {
                RenderIcon(g, s_dbEquips[actor.m_equips[i]][DB_EQUIPS_ICON], x + IGM_ROLEEQUIP_ICON_X[i], y + IGM_ROLEEQUIP_ICON_Y[i]);
            }
        }
        if (wnd.m_focusID < 0)
        {
            //箭头
            s_actorUI.DrawFrame(g, x + IGM_ROLEEQUIP_ICON_X[curItem] + IGM_ROLEEQUIP_CURSOR_X, y + IGM_ROLEEQUIP_ICON_Y[curItem] + IGM_ROLEEQUIP_CURSOR_Y,
                                FRAME_UI_CURSOR);
        }

        UtilDrawMenuBox(g, COLOR_LIGHT_GREEN, x + IGM_ROLEEQUIP_RECT_X, y + IGM_ROLEEQUIP_RECT_Y , IGM_ROLEEQUIP_RECT_W, IGM_ROLEEQUIP_RECT_H, MODULE_UI_CONNER_1,
                        MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1);
        /** @todo 解决装备属性描述不对问题，只有第一次不对，刷新一下就对了。 没找出真正原因。*/
        RefreshActorBV(id);
//@#if JAR_LIMIT_300K
//@        moduleID = STR_SYSTEM_EQUIP_PURPOSE2;
//@#endif
        for (i = 0; i < IGM_ROLEEQUIP_ROW_NUM; i++)
        {
            curX = x + IGM_ROLEEQUIP_VALUE_X;
            for (j = 0; j < IGM_ROLEEQUIP_COL_NUM; j++)
            {
//@#if JAR_LIMIT_300K
//@                DrawString(g, moduleID, curX, curY, ALIGN_LEFT_TOP, COLOR_BLACK, COLOR_WHITE);
//@#else
                s_actorUI.DrawModule(g, moduleID, curX, curY);
//@#endif
                UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, actor.m_value[BV_PHYDMG + i * IGM_ROLEEQUIP_COL_NUM + j], curX + IGM_ROLEEQUIP_VALUE_NUM_X,
                                   curY + IGM_ROLEEQUIP_VALUE_NUM_Y, ALIGN_RIGHT_TOP);
                moduleID++;
                curX += IGM_ROLEEQUIP_VALUE_OFFX;
            }
            curY += IGM_ROLEEQUIP_VALUE_OFFY;
        }
//        //金钱和代币
//@#if JAR_LIMIT_300K
//@        RenderIGMCommon(g, x, y, w, h, STR_SYSTEM_IGM_EQUIL);
//@#else
        RenderIGMCommon(g, x, y, w, h, FRAME_UI_TITLE_EQUIP);
//@#endif

    }

    static void RenderIGMEquipRole(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int id = s_teamMateActorID[s_menuSlot[wnd.m_menuSlot]];
        CActor actor = s_actorCommon[id];

//        UtilDrawMenuBox(g, COLOR_LIGHT_GREEN, x + IGM_ROLEEQUIP_ROLE_RECT_X +IGM_ROLEEQUIP_ROLE_RECT_OFFX, y + IGM_ROLEEQUIP_ROLE_RECT_Y, IGM_ROLEEQUIP_ROLE_RECT_W, IGM_ROLEEQUIP_ROLE_RECT_H,
//                        MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1);
        actor.DrawFrame(g, x + (w >> 1) + IGM_ROLEEQUIP_ROLE_RECT_OFFX, y + IGM_ROLEEQUIP_ROLE_ACTOR_Y, 0);
        if (wnd.m_count > 1)
        {
//@#if JAR_LIMIT_300K
//@            s_actorUI.DrawModule(g, MODULE_UI_ARROW, x + IGM_ROLEEQUIP_ROLE_RECT_X +IGM_ROLEEQUIP_ROLE_RECT_OFFX + IGM_ROLEEQUIP_ROLE_ARROW_OFFX1, y + IGM_ROLEEQUIP_ROLE_ARROW_Y,
//@                                 TRANS_ROT90);
//@            s_actorUI.DrawModule(g, MODULE_UI_ARROW, x + IGM_ROLEEQUIP_ROLE_RECT_X +IGM_ROLEEQUIP_ROLE_RECT_OFFX + IGM_ROLEEQUIP_ROLE_RECT_W - IGM_ROLEEQUIP_ROLE_ARROW_OFFX2, y + IGM_ROLEEQUIP_ROLE_ARROW_Y,
//@                                 TRANS_ROT270);
//@#else
            s_actorUI.DrawFrame(g, x + IGM_ROLEEQUIP_ROLE_RECT_X +IGM_ROLEEQUIP_ROLE_RECT_OFFX + IGM_ROLEEQUIP_ROLE_ARROW_OFFX1, y + IGM_ROLEEQUIP_ROLE_ARROW_Y, FRAME_UI_ARROW_0_LEFT);
            s_actorUI.DrawFrame(g, x + IGM_ROLEEQUIP_ROLE_RECT_X +IGM_ROLEEQUIP_ROLE_RECT_OFFX +IGM_ROLEEQUIP_ROLE_RECT_W- IGM_ROLEEQUIP_ROLE_ARROW_OFFX2, y + IGM_ROLEEQUIP_ROLE_ARROW_Y, FRAME_UI_ARROW_0_RIGHT);
//@#endif
        }
    }

    static void UpdateIGMEquipStandby(CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CWnd wndDesc = wnd.m_children[0];
        CWnd wndList = wnd.m_children[1];

        if (wndList.m_state == WND_STATE_PAGEUP || wndList.m_state == WND_STATE_PAGEDOWN)
        {
            wndDesc.m_data[IGM_DESC_DATA_ID] = wndList.m_curSelect.m_id;
            UpdateIGMEquipCompare(wndList, wnd.m_data[IGM_EQUIPSTANDBY_DATA_ID]);
            wndDesc.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_EQUIPS_DESC_0 + wndDesc.m_data[IGM_DESC_DATA_ID]), wndDesc.m_rect[RECT_W], SCROLL_DIR_UP));
            return;
        }

        if (wndList.m_select >= 0)
        {
            wndList.m_select = -1;
            wnd.m_state = WND_STATE_YES;
            wnd.m_select = wndList.m_curSelect.m_id;
            wnd.Close();
            return;
        }

        if (wnd.m_state == WND_STATE_RETURN)
        {
            ReleaseTempEntity();
            wnd.Close();
        }
    }

    static void UpdateIGMPackageEquip(CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CWnd wndDesc = wnd.m_children[0];
        CWnd wndList = wnd.m_children[1];
        CWnd wndAdvance = wnd.m_children[2];
        CWnd wndMessage = wnd.m_children[3];
        int equipType = s_menuSlot[wnd.m_menuSlot];

        if (wndMessage.m_state == WND_STATE_RETURN)
        {
            wndMessage.m_state = WND_STATE_WAIT;
            wndList.DoModule();
            return;
        }

        if (wndAdvance.m_state == WND_STATE_YES)
        {
            wndAdvance.m_state = WND_STATE_WAIT;
            PackageModify(wndAdvance.m_title, DEFINE_HOLD_TYPE_EQUIP, -1);
            wndAdvance.m_title = s_dbEquips[wndAdvance.m_title][DB_EQUIPS_NEXTLVNAME];
            PackageModify(wndAdvance.m_title, DEFINE_HOLD_TYPE_EQUIP, 1);
            ReleaseTempEntity();
            GetEquipList(EQUIP_TYPE[equipType], wndList, -1);
            if (s_dbEquips[wndAdvance.m_title][DB_EQUIPS_NEXTLVNAME] < 0)
            {
                wndList.m_show = true;
                wndDesc.m_show = true;
                wndAdvance.m_show = false;
                ResizeWndWithTitle(wndMessage, STR_SYSTEM_ADVANCE_MAX, 0);
                wndMessage.DoModule(MESSAGE_SHOW_TIME);
            }
            else
            {
                wndAdvance.DoModule();
                CalcEquipLevelUp(wndAdvance, wndAdvance.m_title);
            }
            return;
        }
        else if (wndAdvance.m_state == WND_STATE_RETURN)
        {
            wndAdvance.m_state = WND_STATE_WAIT;
            wndList.DoModule();
            wndDesc.m_show = true;
            return;
        }

        if (wndList.m_state == WND_STATE_RETURN)
        {
            wndList.m_state = WND_STATE_WAIT;
            wndList.m_title = 0;
            wndDesc.m_data[IGM_DESC_SHOW_CHAR] = T_FALSE;
            return;
        }

        if (wndList.m_state == WND_STATE_PAGEUP || wndList.m_state == WND_STATE_PAGEDOWN)
        {
            wndDesc.m_data[IGM_DESC_DATA_ID] = wndList.m_curSelect.m_id;
            UpdateIGMEquipCompare(wndList, -1);
            wndDesc.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_EQUIPS_DESC_0 + wndDesc.m_data[IGM_DESC_DATA_ID]), wndDesc.m_rect[RECT_W], SCROLL_DIR_UP));
            wndList.DoModule();
            return;
        }

        if (wndList.m_select >= 0)
        {
            wndList.m_select = -1;
            if (s_dbEquips[wndList.m_curSelect.m_id][DB_EQUIPS_NEXTLVNAME] < 0)
            {
                ResizeWndWithTitle(wndMessage, STR_SYSTEM_ADVANCE_MAX, 0);
                wndMessage.DoModule(MESSAGE_SHOW_TIME);
                return;
            }
            wndAdvance.DoModule();
            wndAdvance.m_title = wndList.m_curSelect.m_id;
            CalcEquipLevelUp(wndAdvance, wndAdvance.m_title);
            wndList.m_show = false;
            wndDesc.m_show = false;
            return;
        }

        if (wnd.m_state == WND_STATE_PAGEUP || wnd.m_state == WND_STATE_PAGEDOWN || wnd.m_state == WND_STATE_CYCEL)
        {
            ReleaseTempEntity();
            GetEquipList(EQUIP_TYPE[equipType], wndList, -1);
            return;
        }

        if (wndAdvance.m_state == WND_STATE_RETURN)
        {
            wndList.m_show = true;
            wndDesc.m_show = true;
            return;
        }
        if (wnd.m_select >= 0)
        {
            if (wndList.m_count > 0)
            {
                wndList.DoModule();
                UpdateIGMEquipCompare(wndList, -1);
                wndList.m_title = 1;
                wndDesc.m_data[IGM_DESC_DATA_ID] = wndList.m_curSelect.m_id;
                wndDesc.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_EQUIPS_DESC_0 + wndDesc.m_data[IGM_DESC_DATA_ID]), wndDesc.m_rect[RECT_W], SCROLL_DIR_UP));
                wndDesc.m_data[IGM_DESC_SHOW_CHAR] = T_TRUE;
            }
            return;
        }
        if (wnd.m_state == WND_STATE_RETURN)
        {
            ReleaseTempEntity();
            wnd.Close();
        }
    }

    static void RenderIGMPackageEquip(Graphics g, CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        int curY = y + IGM_EQUIPSTANDBY_TABEL_NAME_Y;
        int type = s_menuSlot[wnd.m_menuSlot];

        UtilDrawMenuBox(g, COLOR_LIGHT_GREEN, x + IGM_EQUIPSTANDBY_RECT_X, y + IGM_EQUIPSTANDBY_RECT_Y, IGM_EQUIPSTANDBY_RECT_W, IGM_EQUIPSTANDBY_RECT_H,
                        MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1);
        RenderTabel(g, FRAME_UI_TABEL2, x + IGM_EQUIPSTANDBY_TABEL_X, y + IGM_EQUIPSTANDBY_TABEL_Y, 0, IGM_EQUIPSTANDBY_TABEL_DY, wnd.m_count, type);

//@#if JAR_LIMIT_300K
//@        RenderIGMCommon(g, x, y, w, h, STR_SYSTEM_IGM_ITEM);
//@#else
        RenderIGMCommon(g, x, y, w, h, FRAME_UI_TITLE_ITEM);
//@#endif

        for (int i = 0; i < wnd.m_count; i++)
        {
//@#if JAR_LIMIT_300K
//@            DrawString(g,STR_SYSTEM_SCENESHOP_0 + i,x + IGM_EQUIPSTANDBY_TABEL_NAME_X, curY, ALIGN_LEFT_TOP, COLOR_BLACK, COLOR_WHITE);
//@#else
            s_actorUI.DrawFrame(g, x + IGM_EQUIPSTANDBY_TABEL_NAME_X, curY, FRAME_UI_EQUIP_ITEM0 + i);
//@#endif
            curY += IGM_EQUIPSTANDBY_TABEL_DY;
        }
    }

    static void UpdateIGMEquipPropose(CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        if (wnd.m_select >= 0)
        {
            wnd.Close();
            return;
        }

        if (wnd.m_state == WND_STATE_RETURN)
        {
            wnd.Close();
        }
    }

    static void RenderIGMEquipPropose(Graphics g, CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int curItem = s_menuSlot[wnd.m_menuSlot], curY = y;

        for (int i = 0; i < wnd.m_count; i++)
        {
//@#if JAR_LIMIT_300K
//@            g.fillRect(x, curY, FONT_WIDTH << 1, FONT_HEIGHT);
//@            DrawString(g, STR_SYSTEM_EQUIP_CHANGE + i, x, curY, ALIGN_LEFT_TOP, COLOR_BLACK, COLOR_HOARY);
//@#else
            s_actorUI.DrawFrame(g, x, curY, FRAME_UI_EQUIP_ACTION_CHANGE + i);
//@#endif
            curY += IGM_EQUIPPURPOSE_OFFY;
        }
        s_actorUI.DrawFrame(g, x + IGM_EQUIPPURPOSE_CURSOR_X, y + IGM_EQUIPPURPOSE_CURSOR_Y + curItem * IGM_EQUIPPURPOSE_OFFY, FRAME_UI_CURSOR);
    }

    static void CalcEquipLevelUp(CWnd wnd, int equipID)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        //装备升级
        int purpose;
        int i, j;
        int[][] arrValue = new int[EQUIP_ADVANCE_SHOW_NUM][AMOUNT_ENUM_EQUIPS_PURPOSE0 - 1];
        wnd.m_dataCount = 0;

        for (i = 0; i < EQUIP_ADVANCE_SHOW_NUM; i++)
        {
            for (j = DB_EQUIPS_PURPOSE0; j <= DB_EQUIPS_PURPOSE_LAST; j += 2)
            {
                purpose = s_dbEquips[equipID][j];
                if (purpose != ENUM_EQUIPS_PURPOSE0_NONE && purpose != ENUM_EQUIPS_PURPOSE0_SKILL)
                {
                    arrValue[i][purpose] = s_dbEquips[equipID][j + 1];
                }
            }
            equipID = s_dbEquips[equipID][DB_EQUIPS_NEXTLVNAME];
        }

        for (i = 0; i < AMOUNT_ENUM_EQUIPS_PURPOSE0 - 1; i++)
        {
            if (arrValue[0][i] != arrValue[1][i])
            {
                wnd.AddData(i);
                wnd.AddData(arrValue[0][i]);
                wnd.AddData(arrValue[1][i]);
            }
        }
    }

    static void UpdateIGMEquipCompare(CWnd wndList, int equipOld)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CWnd wndSuspen = wndList.m_suspen;
        int x = s_menuSlot[wndList.m_menuSlot] % wndList.m_column;
        int y = wndList.m_rect[RECT_Y] - IGM_EQUIPSTANDBY_SUSPEN_Y;
        int equipNew = wndList.m_curSelect.m_id;
        int[] arrCompare = new int[AMOUNT_ENUM_EQUIPS_PURPOSE0 - 1];
        int i, purpose;

        if (x >= (wndList.m_column >> 1))
        {
            x = wndList.m_rect[RECT_X] + wndList.m_rect[RECT_W] * x - wndSuspen.m_rect[RECT_W] + IGM_EQUIPSTANDBY_COMPARE_OFFX_RIGHT;
        }
        else
        {
            x = wndList.m_rect[RECT_X] + wndList.m_rect[RECT_W] * x + IGM_EQUIPSTANDBY_COMPARE_OFFX_LEFT;
        }

        wndSuspen.m_dataCount = 0;
        if (equipNew >= 0)
        {
            for (i = DB_EQUIPS_PURPOSE0; i <= DB_EQUIPS_PURPOSE_LAST; i += 2)
            {
                purpose = s_dbEquips[equipNew][i];
                if (purpose < ENUM_EQUIPS_PURPOSE0_SKILL)
                {
                    arrCompare[purpose] += s_dbEquips[equipNew][i + 1];
                }
            }
        }
        if (equipOld >= 0)
        {
            for (i = DB_EQUIPS_PURPOSE0; i <= DB_EQUIPS_PURPOSE_LAST; i += 2)
            {
                purpose = s_dbEquips[equipOld][i];
                if (purpose < ENUM_EQUIPS_PURPOSE0_SKILL)
                {
                    arrCompare[purpose] -= s_dbEquips[equipOld][i + 1];
                }
            }
        }
        for (i = 0; i < arrCompare.length; i++)
        {
            if (arrCompare[i] != 0)
            {
                wndSuspen.AddData(i);
                wndSuspen.AddData(arrCompare[i]);
            }
        }
        wndSuspen.SetRect(x, y, wndSuspen.m_rect[RECT_W], wndSuspen.m_rect[RECT_H]);
    }

    static void RenderIGMEquipCompare(Graphics g, CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int itemY = y + IGM_EQUIPSTANDBY_SUSPEN_TEXT_Y;
        int module = MODULE_NUMBERS_0_A;

        for (int i = 0; i < wnd.m_dataCount; i += WND_COMPARE_DATA_PER)
        {
            if (wnd.m_data[i + 1] != 0)
            {
                module = wnd.m_data[i + 1] > 0 ? MODULE_NUMBERS_0_C : MODULE_NUMBERS_0_B;
//@#if JAR_LIMIT_300K
//@                DrawString(g,STR_SYSTEM_EQUIP_VALUE_SP + wnd.m_data[i],x + IGM_EQUIPSTANDBY_SUSPEN_TEXT_X, itemY,ALIGN_LEFT_TOP,COLOR_BLACK,COLOR_WHITE);
//@#else
                s_actorUI.DrawModule(g, MODULE_UI_VALUE_SP + wnd.m_data[i], x + IGM_EQUIPSTANDBY_SUSPEN_TEXT_X, itemY);
//@#endif
                UtilRenderUINumber(g, module, false, wnd.m_data[i + 1], x + w - IGM_EQUIPSTANDBY_SUSPEN_TEXT_X, itemY + IGM_EQUIPSTANDBY_SUSPEN_NUM_Y,
                                   ALIGN_RIGHT_TOP);
                itemY += IGM_EQUIPSTANDBY_SUSPEN_TEXT_OFFY;
            }
        }
    }

    static void UpdateIGMEquipAdvance(CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CWnd wndMessage = wnd.m_children[0];
        int id = wnd.m_title;
        if (wnd.m_select >= 0)
        {
            wnd.m_select = -1;
            // 等级已经最高
            if (s_dbEquips[id][DB_EQUIPS_NEXTLVNAME] < 0)
            {
                ResizeWndWithTitle(wndMessage, STR_SYSTEM_ADVANCE_MAX, 0);
                wndMessage.DoModule(MESSAGE_SHOW_TIME);
                return;
            }
            // 金钱不足
            if (s_gameData[DEFINE_GAME_DATA_MONEY] < s_dbEquips[id][DB_EQUIPS_BUY])
            {
                ResizeWndWithTitle(wndMessage, STR_SYSTEM_LESSMONEY, 0);
                wndMessage.DoModule(MESSAGE_SHOW_TIME);
                return;
            }
            // 缺少魂力
            if (s_gameData[DEFINE_GAME_DATA_TOKEN] < s_dbEquips[id][DB_EQUIPS_TOKEN])
            {
                ResizeWndWithTitle(wndMessage, STR_SYSTEM_LESS_TOKEN, 0);
                wndMessage.DoModule(MESSAGE_SHOW_TIME);
                return;
            }
            //金钱
            PackageModify(0, DEFINE_HOLD_TYPE_GOLD, -s_dbEquips[id][DB_EQUIPS_BUY]);
            //代币
            PackageModify(0, DEFINE_HOLD_TYPE_TOKEN, -s_dbEquips[id][DB_EQUIPS_TOKEN]);
            wnd.m_state = WND_STATE_YES;
            wnd.ReturnFocus();
            return;
        }

        if (wnd.m_state == WND_STATE_RETURN)
        {
            wnd.Close();
        }
    }

    static void RenderIGMEquipAdvance(Graphics g, CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        int count = wnd.m_dataCount / 3;
        int curY = y + IGM_EQUIPADVANCE_VALUE_NUM_Y;
        int curItem = 0;
        UtilDrawMenuBox(g, COLOR_LIGHT_GREEN, x + IGM_EQUIPADVANCE_VALUE_RECT_X, y + IGM_EQUIPADVANCE_VALUE_RECT_Y, IGM_EQUIPADVANCE_VALUE_RECT_W,
                        IGM_EQUIPADVANCE_VALUE_RECT_H, MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1);
        DrawString(g, STR_SYSTEM_EQUIPS_NAME_0 + wnd.m_title, x + (w >> 1), y + IGM_EQUIPADVANCE_NAME_Y, ALIGN_HCENTER_TOP, COLOR_BLACK, COLOR_BLACK);
        for (int i = 0; i < count; i++)
        {
//@#if JAR_LIMIT_300K
//@                DrawString(g,STR_SYSTEM_EQUIP_VALUE_SP + wnd.m_data[curItem], x + IGM_EQUIPADVANCE_VALUE_NAME_X, curY - IGM_EQUIPADVANCE_VALUE_NAME_Y,ALIGN_LEFT_TOP,COLOR_BLACK,COLOR_WHITE);
//@                DrawString(g,STR_SYSTEM_HEGNXIAN,x + IGM_EQUIPADVANCE_ARROW_X, curY - IGM_EQUIPADVANCE_VALUE_NAME_Y,ALIGN_HCENTER_TOP,COLOR_BLACK,COLOR_WHITE);
//@#else
          s_actorUI.DrawModule(g, MODULE_UI_VALUE_SP + wnd.m_data[curItem], x + IGM_EQUIPADVANCE_VALUE_NAME_X, curY - IGM_EQUIPADVANCE_VALUE_NAME_Y);
          s_actorUI.DrawFrame(g, x + IGM_EQUIPADVANCE_ARROW_X, curY, FRAME_UI_ADVANCE_ARROW);
//@#endif
            UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, wnd.m_data[curItem + 1], x + IGM_EQUIPADVANCE_VALUE_NUM0_X, curY, ALIGN_LEFT_TOP);
            UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, wnd.m_data[curItem + 2], x + IGM_EQUIPADVANCE_VALUE_NUM1_X, curY, ALIGN_RIGHT_TOP);
            curY += IGM_EQUIPADVANCE_VALUE_TEXT_OFFY;
            curItem += WND_EQUIP_ADVANCE_PER;
        }
//@#if JAR_LIMIT_300K
//@        DrawString(g,STR_SYSTEM_SKILL_COST,x + IGM_EQUIPADVANCE_EXPEND_X, y + IGM_EQUIPADVANCE_EXPEND_Y,ALIGN_LEFT_TOP,COLOR_BLACK,COLOR_WHITE);
//@#else
        s_actorUI.DrawModule(g, MODULE_UI_EXPEND, x + IGM_EQUIPADVANCE_EXPEND_X, y + IGM_EQUIPADVANCE_EXPEND_Y);
//@#endif
        s_actorUI.DrawModule(g, MODULE_UI_ICON_GOLD, x + IGM_EQUIPADVANCE_ICON_GOLD_X, y + IGM_EQUIPADVANCE_COST_Y);
        UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, s_dbEquips[wnd.m_title][DB_EQUIPS_BUY], x + IGM_EQUIPADVANCE_COST_GOLD_X, y + IGM_EQUIPADVANCE_COST_Y,
                           ALIGN_LEFT_TOP);
        s_actorUI.DrawModule(g, MODULE_UI_ICON_TOKEN, x + IGM_EQUIPADVANCE_ICON_TOKEN_X, y + IGM_EQUIPADVANCE_COST_Y);
        UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, s_dbEquips[wnd.m_title][DB_EQUIPS_TOKEN], x + IGM_EQUIPADVANCE_COST_TOKEN_X, y + IGM_EQUIPADVANCE_COST_Y,
                           ALIGN_LEFT_TOP);
//@#if JAR_LIMIT_300K
//@        DrawString(g,STR_SYSTEM_SKILL_ADVANCE_START,x + (w >> 1), y + IGM_EQUIPADVANCE_BUTTON_Y,ALIGN_HCENTER_TOP,COLOR_RED,COLOR_BLACK);
//@#else
        s_actorUI.DrawFrame(g, x + (w >> 1), y + IGM_EQUIPADVANCE_BUTTON_Y, FRAME_UI_ADVANCE_START);
//@#endif
//@#if JAR_LIMIT_300K
//@        RenderIGMCommon(g, x, y, w, h, STR_SYSTEM_IGM_EQUIL);
//@#else
     RenderIGMCommon(g, x, y, w, h, FRAME_UI_TITLE_EQUIPADVANCE);
//@#endif

    }

    static void RenderValueBar(Graphics g, int type, int x, int y, int w, int percent)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int length = (w - 4) * percent / 100;
        g.setColor(COLOR_BAR_SHADOW[type]);
        g.drawLine(x + 1, y, x + w - 1, y);
        g.drawLine(x + 2, y + 2, x + length + 2, y + 2);
        g.drawLine(x + 1, y + 1, x + 1, y + 2);
        g.drawLine(x + w - 1, y + 1, x + w -1, y + 2);
        g.setColor(COLOR_BAR_EDGE[type]);
        g.drawLine(x + 1, y + 3, x + w - 1, y + 3);
        g.drawLine(x, y + 1, x, y + 2);
        g.drawLine(x + w, y + 1, x + w, y + 2);
        g.drawLine(x + 1, y, x + 1, y);
        g.drawLine(x + w - 1, y, x + w - 1, y);
        g.setColor(COLOR_BAR_LIGHT[type]);
        g.drawLine(x + 2, y + 1, x + 2 + length, y + 1);
    }

    static void RenderIGMSkillRole(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int foucsID = s_menuSlot[wnd.m_menuSlot];
        int curX = x + IGM_SKILL_ROLE_NAME_X;

        RenderTabel(g, FRAME_UI_TABEL1, x+IGM_SKILL_ROLE_BOX_OFFX, y, IGM_SKILL_TABEL_OFFX, 0, wnd.m_count, foucsID);
        for (int i = 0; i < wnd.m_count; i++)
        {
//@#if JAR_LIMIT_300K
//@            DrawString(g,STR_SYSTEM_ALLY0+s_teamMateActorID[i],curX,y + IGM_SKILL_ROLE_NAME_Y,ALIGN_LEFT_TOP,COLOR_BLACK,COLOR_WHITE);
//@#else
            s_actorUI.DrawFrame(g, curX, y + IGM_SKILL_ROLE_NAME_Y, FRAME_UI_SKILL_ITEM0 +s_teamMateActorID[i]);
//@#endif
            curX += IGM_SKILL_TABEL_OFFX;
        }
    }

    static void UpdateIGMSkill(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CWnd wndRole = wnd.m_children[0];
        CWnd wndAdvance = wnd.m_children[1];
        CWnd wndDesc = wnd.m_children[2];
        CWnd wndMessage = wnd.m_children[4];
        int curRole = s_menuSlot[wndRole.m_menuSlot];

        UpdateWndTextPos(wnd, IGM_SKILL_LIST_W, IGM_DESC_TEXT_WAIT_TIME);

        if (wndAdvance.m_state == WND_STATE_YES)
        {
            wndAdvance.m_state = WND_STATE_WAIT;
            ResizeWndWithTitle(wndMessage, STR_SYSTEM_LEARN_SUCCESS, 0);
            wndMessage.DoModule(MESSAGE_SHOW_TIME);

            // 升级完成后回来描述要刷新下。
            wndDesc.m_data[IGM_DESC_SHOW_CHAR] = T_TRUE;
            wndDesc.m_data[IGM_DESC_DATA_ID] = wnd.m_curSelect.m_id;
            wnd.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_SKILL_NAME_DESC_0 + wnd.m_curSelect.m_id), IGM_SKILL_LIST_W, SCROLL_DIR_LEFT));
            wndDesc.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_SKILLS_DESC_0 + wnd.m_curSelect.m_id), wndDesc.m_rect[RECT_W] - (IGM_DESC_TEXT_X << 1),
                                             SCROLL_DIR_UP));
            //~~end
            wndDesc.m_show = true;
            return;
        }
        else if (wndAdvance.m_state == WND_STATE_RETURN)
        {
            wndAdvance.m_state = WND_STATE_WAIT;
            wndDesc.m_show = true;
            return;
        }

        if (wndRole.m_state == WND_STATE_PAGEUP || wndRole.m_state == WND_STATE_PAGEDOWN || wndRole.m_state == WND_STATE_CYCEL)
        {
            wnd.m_curSelect = s_actorCommon[s_teamMateActorID[curRole]].m_entitySkill.m_next;
            wnd.m_showFirst = wnd.m_curSelect;
            wnd.m_count = wnd.m_curSelect == null ? 0 : wnd.m_curSelect.GetCount();
            wnd.m_listStartPos = 0;
            s_menuSlot[wnd.m_menuSlot] = 0;
            if (wnd.m_showFirst != null)
            {
                wndAdvance.m_title = curRole;
                wndDesc.m_data[IGM_DESC_SHOW_CHAR] = T_TRUE;
                wndDesc.m_data[IGM_DESC_DATA_ID] = wnd.m_curSelect.m_id;
                wnd.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_SKILL_NAME_DESC_0 + wnd.m_curSelect.m_id), IGM_SKILL_LIST_W, SCROLL_DIR_LEFT));
                wndDesc.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_SKILLS_DESC_0 + wnd.m_curSelect.m_id), wndDesc.m_rect[RECT_W] - (IGM_DESC_TEXT_X << 1),
                                                 SCROLL_DIR_UP));
            }
            else
            {
                wndDesc.m_data[IGM_DESC_SHOW_CHAR] = T_FALSE;
            }
            return;
        }

        wnd.UpdateList();
        if (wnd.m_state == WND_STATE_PAGEUP || wnd.m_state == WND_STATE_PAGEDOWN)
        {
            wndDesc.m_data[IGM_DESC_DATA_ID] = wnd.m_curSelect.m_id;
            wnd.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_SKILL_NAME_DESC_0 + wnd.m_curSelect.m_id), IGM_SKILL_LIST_W, SCROLL_DIR_LEFT));
            wndDesc.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_SKILLS_DESC_0 + wnd.m_curSelect.m_id), wndDesc.m_rect[RECT_W] - (IGM_DESC_TEXT_X << 1),
                                                 SCROLL_DIR_UP));
            return;
        }

        //按下确定键并且该类型存在数据开启子选择物品窗口
        if (wnd.m_select >= 0)
        {
            if (wnd.m_curSelect == null) //如果该包裹没有物品直接返回
            {
                return;
            }
            if (s_dbSkills[wnd.m_curSelect.m_id][DB_SKILLS_NEXTLVNAME] < 0)
            {
                ResizeWndWithTitle(wndMessage, STR_SYSTEM_SKILL_MAX, 0);
                wndMessage.DoModule(MESSAGE_SHOW_TIME);
                return;
            }
            wndAdvance.DoModule(); //将控制权交给子窗口
            wndAdvance.m_curSelect = wnd.m_curSelect;
            CalcSkillLevelUp(wndAdvance, wnd.m_curSelect.m_id);
            wndDesc.m_show = false;
            return;
        }

        if (wnd.m_state == WND_STATE_RETURN)
        {
            wnd.Close();
            return;
        }
    }

    static void RenderIGMSkill(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        int foucsID = s_menuSlot[wnd.m_menuSlot] - wnd.m_listStartPos;
        int curY = y + IGM_SKILL_ICONBG_Y;
        int curItem = s_menuSlot[wnd.m_menuSlot];
//@#if JAR_LIMIT_300K
//@        RenderIGMCommon(g, x, y, w, h, STR_SYSTEM_IGM_SK);
//@#else
     RenderIGMCommon(g, x, y, w, h, FRAME_UI_TITLE_SKILL);
//@#endif
        UtilDrawMenuBox(g, COLOR_LIGHT_GREEN, x + IGM_SKILL_RECT_X, y + IGM_SKILL_RECT_Y, IGM_SKILL_RECT_W, IGM_SKILL_RECT_H, MODULE_UI_CONNER_1,
                        MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1);
        for (int i = 0; i < wnd.m_showNum; i++)
        {
            s_actorUI.DrawFrame(g, x + IGM_SKILL_ICONBG_X, curY, FRAME_UI_ICON_BG);
            curY += IGM_SKILL_LIST_DY;
        }
        RenderEntityList(g, wnd.m_showFirst, STR_SYSTEM_SKILL_NAME_DESC_0, true, wnd.m_showNum, foucsID, ENTITY_INFO_TYPE_NONE, x + IGM_SKILL_LIST_X,
                         y + IGM_SKILL_LIST_Y, IGM_SKILL_LIST_DX, IGM_SKILL_LIST_DY, ENTITY_ENABLE_TYPE_NONE, IGM_SKILL_LIST_W, wnd.m_timer);
        RenderScrollBar(g, FRAME_UI_SCROLL_BAR_TOP, x + IGM_SKILL_SCROLL_BAR_X, y + IGM_SKILL_SCROLL_BAR_Y, IGM_SKILL_SCROLL_BAR_H, curItem, wnd.m_count);
        if (wnd.m_count > 0)
        {
            s_actorUI.DrawFrame(g, x + IGM_SKILL_CURSOR_X, y + IGM_SKILL_CURSOR_Y + (curItem - wnd.m_listStartPos) * IGM_SKILL_LIST_DY, FRAME_UI_CURSOR);
        }
    }

    static void CalcSkillLevelUp(CWnd wnd, int skillID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int effect, icon;
        int i, j;
        int[][] arrValue = new int[SKILL_ADVANCE_SHOW_NUM][AMOUNT_DB_EFFECT_SET];
        int[] cost = new int[SKILL_ADVANCE_SHOW_NUM];
        int[] target = new int[SKILL_ADVANCE_SHOW_NUM];
        wnd.m_dataCount = 0;

        for (i = 0; i < SKILL_ADVANCE_SHOW_NUM; i++)
        {
            cost[i] = s_dbSkills[skillID][DB_SKILLS_USEMP];
            target[i] = s_dbSkills[skillID][DB_SKILLS_TARGETCOUNT] > 0 ? s_dbSkills[skillID][DB_SKILLS_TARGETCOUNT] : BATTLE_ENEMY_MAX;
            for (j = DB_SKILLS_PURPOSE0; j <= DB_SKILLS_PURPOSE_LAST; j += 3)
            {
                effect = s_dbSkills[skillID][j];
                if (effect != DB_EFFECT_SET_ENUMERATE_NONE)
                {
                    arrValue[i][effect] = s_dbSkills[skillID][j + (effect < AMOUNT_BUFF_EFFECT ? 1 : 2)];
                }
            }
            skillID = s_dbSkills[skillID][DB_SKILLS_NEXTLVNAME];
        }

        wnd.AddData(FRAME_UI_SKILL_COST);
        wnd.AddData(cost[0]);
        wnd.AddData(cost[1]);
        wnd.AddData(FRAME_UI_SKILL_TAGET);
        wnd.AddData(target[0]);
        wnd.AddData(target[1]);

        for (i = 0; i < AMOUNT_DB_EFFECT_SET; i++)
        {
            icon = s_dbEffectSet[i][DB_EFFECT_SET_ICON];
            if (icon >= 0 && arrValue[0][i] != arrValue[1][i])
            {
                wnd.AddData(FRAME_UI_SKILL_ATTACK + icon);
                wnd.AddData(arrValue[0][i]);
                wnd.AddData(arrValue[1][i]);
            }
        }
    }

    static void UpdateIGMSkillAdvance(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CWnd wndMessage = wnd.m_children[0];
        CActor actor = s_actorCommon[s_teamMateActorID[wnd.m_title]];
        int id = wnd.m_curSelect.m_id;

        UpdateWndTextPos(wnd, IGM_SKILL_ADVANCE_TEXT1_H, 50);

        if (wnd.m_select >= 0)
        {
            if (actor.m_value[BV_LV] < s_dbSkills[id][DB_SKILLS_LEARN_LV])
            {
                ResizeWndWithTitle(wndMessage, STR_SYSTEM_LESSLV, 0);
                wndMessage.DoModule(MESSAGE_SHOW_TIME);
            }
            else if (s_gameData[DEFINE_GAME_DATA_MONEY] < s_dbSkills[id][DB_SKILLS_MONEY])
            {
                ResizeWndWithTitle(wndMessage, STR_SYSTEM_LESSMONEY, 0);
                wndMessage.DoModule(MESSAGE_SHOW_TIME);
            }
            else
            {
                wnd.m_state = WND_STATE_YES;
                actor.m_entitySkill.SearchObject(id).m_id = s_dbSkills[id][DB_SKILLS_NEXTLVNAME];
                s_gameData[DEFINE_GAME_DATA_MONEY] -= s_dbSkills[id][DB_SKILLS_MONEY];
                wnd.Close();
            }
            return;
        }

        if (wnd.m_state == WND_STATE_RETURN)
        {
            wnd.Close();
        }
//@#if SMS
//@        if (wndMessage.m_state == WND_STATE_RETURN && wndMessage.m_title == STR_SYSTEM_LESSMONEY
//@#if TS_SMS
//@#else
//@            && s_variables[SCRIPT_VARIABLE_SMS_PARA0 + ENUM_SMS_PURPOSE0_ACTIVE] > 0
//@#endif
//@                )
//@        {
//@            wndMessage.m_state = WND_STATE_WAIT;
//@            ReleaseTempEntity();
//@            CEntity entity = CEntity.GetInstance();
//@#if TS_SMS
//@            entity.m_id = 4;
//@#else
//@            entity.m_id = 2;
//@#endif
//@            entity.m_count = 1;
//@            s_tempList.InsertAfter(entity);
//@            s_wnd = CWnd.GetInstance();
//@            s_wnd.Init(WND_SMS_MESSAGE, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, true);
//@            s_wnd.m_isTop = true;
//@            s_wnd.m_curSelect = entity;
//@            s_wnd.DoModule();
//@        }
//@#endif
    }

    static void RenderIGMSkillAdvance(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int id = wnd.m_curSelect.m_id;
        int count = wnd.m_dataCount / 3;
        int curY = y + IGM_SKILL_ADVANCE_COMPARE_ITEM_Y;
        UtilDrawMenuBox(g, COLOR_DEEP_BLUER, x + IGM_SKILL_ADVANCE_RECT_X, y + IGM_SKILL_ADVANCE_RECT_Y, IGM_SKILL_ADVANCE_RECT_W, IGM_SKILL_ADVANCE_RECT_H,
                        MODULE_UI_CONNER_2, MODULE_UI_HORIZ_2, MODULE_UI_VERTI_2); //Box1
        DrawString(g, STR_SYSTEM_SKILL_NAME_DESC_0 + id, x + (w >> 1), y + IGM_SKILL_ADVANCE_NAME_Y, ALIGN_HCENTER_TOP, COLOR_GRAY_BLUER, COLOR_GRAY_BLUER); //标题1 升级前
        DrawTextInRectByScroll(g, STR_SYSTEM_SKILLS_DESC_0 +id, x + IGM_SKILL_ADVANCE_TEXT_X, y + IGM_SKILL_ADVANCE_TEXT_Y, IGM_SKILL_ADVANCE_RECT_W,
                               IGM_SKILL_ADVANCE_TEXT_H, ALIGN_HCENTER_TOP, COLOR_GRAY_BLUER, COLOR_GRAY_BLUER, SCROLL_DIR_UP, wnd.m_timer);

        UtilDrawMenuBox(g, COLOR_DEEP_BLUER, x + IGM_SKILL_ADVANCE_RECT1_X, y + IGM_SKILL_ADVANCE_RECT1_Y, IGM_SKILL_ADVANCE_RECT1_W, IGM_SKILL_ADVANCE_RECT1_H,
                        MODULE_UI_CONNER_2, MODULE_UI_HORIZ_2, MODULE_UI_VERTI_2); //Box2
        DrawString(g, STR_SYSTEM_SKILL_NAME_DESC_0 + id+ 1, x + (w >> 1), y + IGM_SKILL_ADVANCE_NAME1_Y, ALIGN_HCENTER_TOP, COLOR_GRAY_BLUER, COLOR_GRAY_BLUER); //标题2 升级后
        DrawTextInRectByScroll(g, STR_SYSTEM_SKILLS_DESC_0 + id + 1, x + IGM_SKILL_ADVANCE_TEXT1_X, y + IGM_SKILL_ADVANCE_TEXT1_Y, IGM_SKILL_ADVANCE_RECT1_W,
                               IGM_SKILL_ADVANCE_TEXT1_H, ALIGN_HCENTER_TOP, COLOR_GRAY_BLUER, COLOR_GRAY_BLUER, SCROLL_DIR_UP, wnd.m_timer);
//        DrawSystemString(g, "所需" + " " + s_dbSkills[id][DB_SKILLS_LEARN_LV] + " " + "级",
//                         x + IGM_SKILL_ADVANCE_X, y + IGM_SKILL_ADVANCE_Y, ALIGN_RIGHT_TOP, COLOR_GRAY_BLUER, COLOR_BLACK); //所需等级
//@#if JAR_LIMIT_300K
//@        DrawString(g,STR_SYSTEM_SKILL_COST,x + IGM_SKILL_EXPEND_X - 2, y + IGM_SKILL_EXPEND_Y,ALIGN_LEFT_TOP,COLOR_WHITE,COLOR_BLACK);
//@#else
        s_actorUI.DrawModule(g, MODULE_UI_EXPEND, x + IGM_SKILL_EXPEND_X, y + IGM_SKILL_EXPEND_Y); //消耗
//@#endif
        s_actorUI.DrawModule(g, MODULE_UI_ICON_GOLD, x + IGM_SKILL_ICON_GOLD_X, y + IGM_SKILL_COST_Y); //金钱图标
        UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, s_dbSkills[id][DB_SKILLS_MONEY], x + IGM_SKILL_COST_GOLD_X, y + IGM_SKILL_COST_Y, ALIGN_LEFT_TOP); //金钱数量
        s_actorUI.DrawModule(g, MODULE_UI_ICON_TOKEN, x + IGM_SKILL_ICON_TOKEN_X, y + IGM_SKILL_COST_Y); //代币图标
        UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, s_dbSkills[id][DB_SKILLS_TOKEN], x + IGM_SKILL_COST_TOKEN_X, y + IGM_SKILL_COST_Y, ALIGN_LEFT_TOP); //代币数量
//@#if JAR_LIMIT_300K
//@        DrawString(g,STR_SYSTEM_SKILL_ADVANCE_START,x + (w >> 1),y + IGM_SKILL_BUTTON_Y, ALIGN_HCENTER_TOP,COLOR_RED,COLOR_BLACK);
//@#else
        s_actorUI.DrawFrame(g, x + (w >> 1), y + IGM_SKILL_BUTTON_Y, FRAME_UI_ADVANCE_START); //开始强化
//@#endif
//        UtilDrawMenuBox(g, COLOR_WHITE, x + IGM_SKILL_ADVANCE_RECT_X, y + IGM_SKILL_ADVANCE_RECT_Y, IGM_SKILL_ADVANCE_RECT_W, IGM_SKILL_ADVANCE_RECT_H,
//                        MODULE_UI_CONNER_2, MODULE_UI_HORIZ_2, MODULE_UI_VERTI_2);
//        DrawString(g, STR_SYSTEM_SKILL_NAME_DESC_0 + id, x + (w >> 1), y + IGM_SKILL_ADVANCE_NAME_Y, ALIGN_HCENTER_TOP, COLOR_BLACK, COLOR_BLACK);
//        for (int i = 0; i < count; i++)
//        {
//            s_actorUI.DrawFrame(g, x + IGM_SKILL_ADVANCE_COMPARE_ITEM_X, curY, wnd.m_data[i * WND_SKILL_ADVANCE_PER]);
//            s_actorUI.DrawFrame(g, x + IGM_SKILL_ADVANCE_COMPARE_ARROW_X, curY, FRAME_UI_ADVANCE_ARROW);
//            UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, wnd.m_data[i * WND_SKILL_ADVANCE_PER + 1], x + IGM_SKILL_ADVANCE_COMPARE_VALUE0_X, curY,
//                               ALIGN_RIGHT_TOP);
//            UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, wnd.m_data[i * WND_SKILL_ADVANCE_PER + 2], x + IGM_SKILL_ADVANCE_COMPARE_VALUE1_X, curY,
//                               ALIGN_RIGHT_TOP);
//            curY += IGM_SKILL_ADVANCE_COMPARE_ITEM_OFFY;
//        }
//        s_actorUI.DrawModule(g, MODULE_UI_EXPEND, x + IGM_SKILL_EXPEND_X, y + IGM_SKILL_EXPEND_Y);
//        s_actorUI.DrawModule(g, MODULE_UI_ICON_GOLD, x + IGM_SKILL_ICON_GOLD_X, y + IGM_SKILL_COST_Y);
//        UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, s_dbSkills[id][DB_SKILLS_MONEY], x + IGM_SKILL_COST_GOLD_X,
//                           y + IGM_SKILL_COST_Y, ALIGN_LEFT_TOP);
//        s_actorUI.DrawModule(g, MODULE_UI_ICON_TOKEN, x + IGM_SKILL_ICON_TOKEN_X, y + IGM_SKILL_COST_Y);
//        UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, s_dbSkills[id][DB_SKILLS_TOKEN], x + IGM_SKILL_COST_TOKEN_X,
//                           y + IGM_SKILL_COST_Y, ALIGN_LEFT_TOP);
//        s_actorUI.DrawFrame(g, x + (w >> 1), y + IGM_SKILL_BUTTON_Y, FRAME_UI_ADVANCE_START);
    }

    static void UpdateIGMSkillRole(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h

    }

    static void RenderUILAName(Graphics g, int actorID, int x, int y)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        DrawString(g, STR_SYSTEM_GAMEINFO_NAME_0 + actorID, x + (UI_NAME_RECT_W >> 1), y + UI_NAME_STR_Y_OFFSET, ALIGN_HCENTER_TOP, COLOR_BLACK, COLOR_WHITE);
    }

    /**游戏中菜单中包裹菜单活动的部分*/
    static int s_buyListNum;

    static void UpdateIGMPackageItem(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CWnd wndDesc = wnd.m_children[0];
        CWnd wndList = wnd.m_children[1];
        CWnd wndRole = wnd.m_children[2];
        CWnd wndMessage = wnd.m_children[3];
        CWnd wndValue = wnd.m_children[4];
        CWnd wndRoleMessage = wndRole.m_children[wndRole.m_childCount - 2];
        CActor actor;

        if (wndValue.m_state == WND_STATE_RETURN)
        {
            wndValue.m_state = WND_STATE_WAIT;
            wndList.DoModule();
            wndRole.m_show = false;
            wndDesc.m_data[IGM_DESC_SHOW_CHAR] = T_TRUE;
            return;
        }

        if (wndList.m_state == WND_STATE_RETURN)
        {
            wndList.m_state = WND_STATE_WAIT;
            wndList.m_title = 0;
            return;
        }

        if (wndList.m_state == WND_STATE_PAGEUP || wndList.m_state == WND_STATE_PAGEDOWN)
        {
            wndList.m_state = WND_STATE_WAIT;
            wndDesc.m_data[IGM_DESC_DATA_ID] = wndList.m_curSelect.m_id;
            wndDesc.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_ITEMS_DESC_0 + wndList.m_curSelect.m_id), wndDesc.m_rect[RECT_W], SCROLL_DIR_UP));
            wndList.DoModule();
            return;
        }

        if (wndList.m_select >= 0)
        {
            wndList.m_select = -1;
            int whenCanUse = s_dbItems[wndList.m_curSelect.m_id][DB_ITEMS_WHENCANUSE];
            if (whenCanUse == ENUM_ITEMS_WHENCANUSE_ALL || whenCanUse == ENUM_ITEMS_WHENCANUSE_MENU)
            {
                wndDesc.m_show = false;
                wndRole.DoModule();
                wnd.m_data[IGM_ARTICAL_DATA_ID] = wndList.m_curSelect.m_id;
                wndRole.m_data[IGM_STATUS_ISTARGET] = T_TRUE;
                wndDesc.m_data[IGM_DESC_SHOW_CHAR] = T_FALSE;
            }
            else
            {
                wndDesc.m_show = true;
                wndDesc.m_data[IGM_DESC_SHOW_CHAR] = T_TRUE;
                wndList.DoModule();
            }
            return;
        }

        if (wndRole.m_state == WND_STATE_RETURN)
        {
            wndDesc.m_show = true;
            wndRole.m_state = WND_STATE_WAIT;
            if (wndRole.m_select >= 0)
            {
                actor = s_actorCommon[s_teamMateActorID[wndRole.m_select]];
                int itemID = wnd.m_data[IGM_ARTICAL_DATA_ID];
                if ((s_dbItems[itemID][DB_ITEMS_SUITABLE] & (1 << wndRole.m_select)) == 0)
                {
                    ResizeWndWithTitle(wndMessage, STR_SYSTEM_ITEM_SUITABLE, 0);
                    wndMessage.DoModule(MESSAGE_SHOW_TIME);
                    return;
                }
                CalcItemUsed(itemID, actor);
                if (CalcUsedEffectCount() > 0)
                {
                    ApplyEffect(actor, 100, s_effectValue);
                    if(s_gameData[DEFINE_GAME_DATA_TOKEN] < s_dbItems[wndList.m_curSelect.m_id][DB_ITEMS_TOKEN])
                    {
                        ResizeWndWithTitle(wndMessage, STR_SYSTEM_LESSTOKEN, 0);
                        wndMessage.DoModule(MESSAGE_SHOW_TIME);
                        wndMessage.m_state = WND_STATE_WAIT;
//@#if SMS
//@#if TB_SMS
//@#else
//@                        if (wndMessage.m_title == STR_SYSTEM_LESSTOKEN
//@                            && s_variables[SCRIPT_VARIABLE_SMS_PARA0 + ENUM_SMS_PURPOSE0_ACTIVE] > 0)
//@                        {
//@                            ReleaseTempEntity();
//@                            CEntity entity = CEntity.GetInstance();
//@                            entity.m_id = ENUM_SMS_PURPOSE0_GET_MONEY;
//@                            entity.m_count = 1;
//@                            s_tempList.InsertAfter(entity);
//@                            s_wnd = CWnd.GetInstance();
//@                            s_wnd.Init(WND_SMS_MESSAGE, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, true);
//@                            s_wnd.m_isTop = true;
//@                            s_wnd.m_curSelect = entity;
//@                            s_wnd.DoModule();
//@                        }
//@#endif
//@#endif
                        return;
                    }
                    s_gameData[DEFINE_GAME_DATA_TOKEN] -= s_dbItems[wndList.m_curSelect.m_id][DB_ITEMS_TOKEN];
                    PackageModify(wndList.m_curSelect.m_id, DEFINE_HOLD_TYPE_ITEM, -1);
                    wndList.m_curSelect.m_count--;
                    if (wndList.m_curSelect.m_count > 0)
                    {
                        int message = s_dbItems[wndList.m_curSelect.m_id][DB_ITEMS_MESSAGE];
                        switch (message)
                        {
                        case ENUM_ITEMS_MESSAGE_NONE:
                            wndRole.DoModule();
                            break;
                        case ENUM_ITEMS_MESSAGE_VALUE:
                            CalcRoleValue(wndValue, actor, wndList.m_curSelect.m_id);
                            wndValue.DoModule();
                            break;
                        }
                    }
                    else
                    {
                        ClearEffectValue();
                        wndRole.m_show = false;
                        wndDesc.m_data[IGM_DESC_SHOW_CHAR] = T_TRUE;
                        ReleaseTempEntity();
                        GetEntityIndex(ENUM_ITEMS_TYPE_ITEM, wndList, s_entityList[ENTITY_LIST_ITEM]);
                        wndList.DoModule();
                        wndDesc.m_data[IGM_DESC_DATA_ID] = wndList.m_curSelect.m_id;
                    }
                }
                else
                {
                    wndDesc.m_show = false;
                    wndRole.DoModule();
                    wndRoleMessage.DoModule(MESSAGE_SHOW_TIME);
                    switch (s_avoidRecover)
                    {
                    case AVOID_RECOVER_HP:
                        ResizeWndWithTitle(wndRoleMessage, STR_SYSTEM_HP_FULL, 0);
                        break;
                    case AVOID_RECOVER_MP:
                        ResizeWndWithTitle(wndRoleMessage, STR_SYSTEM_MP_FULL, 0);
                        break;
                    case AVOID_RECOVER_HPMP:
                        ResizeWndWithTitle(wndRoleMessage, STR_SYSTEM_HPMP_FULL, 0);
                        break;
                    }
                }
            }
            else
            {
                wndList.DoModule();
                wndRole.m_show = false;
                wndDesc.m_data[IGM_DESC_SHOW_CHAR] = T_TRUE;
            }
            return;
        }

        if (wndMessage.m_state == WND_STATE_RETURN)
        {
            wndMessage.m_state = WND_STATE_WAIT;
            wndList.DoModule();
            wndRole.m_show = false;
            return;
        }

        wndDesc.m_data[IGM_DESC_SHOW_CHAR] = T_FALSE;

        if (wnd.m_state == WND_STATE_CYCEL || wnd.m_state == WND_STATE_PAGEUP || wnd.m_state == WND_STATE_PAGEDOWN)
        {
            ReleaseTempEntity();
            GetEntityIndex(s_menuSlot[wnd.m_menuSlot], wndList, s_entityList[ENTITY_LIST_ITEM]);
            return;
        }
        //按下确定键并且该类型存在数据开启子选择物品窗口
        if (wnd.m_select >= 0)
        {
            if (wndList.m_count <= 0) //如果该包裹没有物品直接返回
            {
                return;
            }
            s_menuSlot[wndList.m_menuSlot] = 0;
            wndList.DoModule(); //将控制权交给子窗口
            wndDesc.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_ITEMS_DESC_0 + wndList.m_curSelect.m_id), wndDesc.m_rect[RECT_W], SCROLL_DIR_UP));
            wndDesc.m_data[IGM_DESC_SHOW_CHAR] = T_TRUE;
            wndDesc.m_data[IGM_DESC_DATA_ID] = wndList.m_curSelect.m_id;
            wndList.m_title = 1;
            return;
        }
        //确认窗口返回
        if (wnd.m_state == WND_STATE_RETURN)
        {
            ReleaseTempEntity();
            wnd.ReturnFocus();
        }
    }

    static void ResetWndList(CWnd wnd, CWnd wndList, int index)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        if (index >= 0)
        {
            wndList.m_curSelect = s_entityList[index].m_next;
        }
        else
        {
            wndList.m_curSelect = null;
        }
        wndList.m_showFirst = wndList.m_curSelect;
        wndList.m_listStartPos = 0;
        s_menuSlot[wnd.m_menuSlot] = 0;
    }

    static void RenderTabel(Graphics g, int frame, int x, int y, int dx, int dy, int count, int foucs)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        for (int i = 0; i < count; i++)
        {
            if (i == foucs)
            {
                s_actorUI.DrawFrame(g, x, y, frame + 1);
            }
            else
            {
                s_actorUI.DrawFrame(g, x, y, frame);
            }
            x += dx;
            y += dy;
        }
    }

    static void Render2DList(Graphics g, CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int dx = wnd.m_rect[RECT_W];
        int dy = wnd.m_rect[RECT_H];
        int curItem = s_menuSlot[wnd.m_menuSlot];
        int curRow = curItem / wnd.m_column;
        int rowNum = wnd.m_count / wnd.m_column + (wnd.m_count % wnd.m_column == 0 ? 0 : 1);
        CEntity entity = wnd.m_showFirst;

        g.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        for (int i = 0; i < wnd.m_showNum; i++)
        {
            x = wnd.m_rect[RECT_X];
            for (int j = 0; j < wnd.m_column; j++)
            {
                s_actorUI.DrawFrame(g, x, y, FRAME_UI_ICON_BG);
                if (entity != null)
                {
                    RenderIcon(g, entity.m_icon, x, y);
                    if (entity.m_count < GAME_ICON_NUM_MAX)
                    {
                        UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, entity.m_count, x + LIST_2D_NUM_OFFX, y + LIST_2D_NUM_OFFY, ALIGN_RIGHT_TOP);
                    }
                    entity = entity.m_next;
                }
                x += dx;
            }
            y += dy;
        }
        x = wnd.m_rect[RECT_X];
        y = wnd.m_rect[RECT_Y];
        if (wnd.m_title > 0)
        {
            s_actorUI.DrawFrame(g, x + (s_menuSlot[wnd.m_menuSlot] % wnd.m_column) * dx, y + (curItem / wnd.m_column - wnd.m_listStartPos) * dy,
                                FRAME_UI_CURSOR);
        }
        RenderScrollBar(g, FRAME_UI_SCROLL_BAR_TOP, x + LIST_SCROLL_BAR_X + (wnd.m_column - 1) * dx, y + LIST_SCROLL_BAR_Y, LIST_SCROLL_BAR_H, curRow, rowNum);
    }

//@#if MORE_GAME_LINK
    static void UpdateLinkConfirm(CWnd wnd)
    {
//@#if MORE_GAME_CONFIRM
        if (IsKeySinglePressed(GKEY_SOFTLEFT))
//@#endif
        {
//@#switch(Channel)
//@#{
//@#case "GF":
            s_url = "http://gamepie.g188.net/gamecms/go/jpgd";
//@#break
//@#case "QQ":
//@            s_url = IMARPG22.s_midlet.getAppProperty("GameCenterUrl");
//@#break
//@#}
            SetGameState(GS_GAME_LINK);
        }
//@#if MORE_GAME_CONFIRM
        else if (IsKeySinglePressed(GKEY_SOFTRIGHT))
        {
            wnd.Close();
        }
//@#endif
    }

    static void RenderLinkConfirm(Graphics g, CWnd wnd)
    {
//@#if MORE_GAME_CONFIRM
        g.drawImage(s_imgMoreGame, SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF, ALIGN_CENTER);
        DrawLineString(g, STR_SYSTEM_MORE_GAME_TIP, SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF - FONT_HEIGHT, ALIGN_HCENTER_BOTTOM, COLOR_WHITE, COLOR_WHITE,
                       7, 0, 0);
        DrawSystemString(g, "是", 5, SCREEN_HEIGHT - 5, ALIGN_LEFT_BOTTOM, COLOR_WHITE, COLOR_WHITE);
        DrawSystemString(g, "否", SCREEN_WIDTH - 5, SCREEN_HEIGHT - 5, ALIGN_RIGHT_BOTTOM, COLOR_WHITE, COLOR_WHITE);
//@#endif
    }

//@#endif

    static void RenderIGMPackageItem(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        int foucsID = s_menuSlot[wnd.m_menuSlot];
        int tabelY = y + IGM_ARTICAL_TABEL_NAME_Y;


//@#if JAR_LIMIT_300K
//@        RenderIGMCommon(g, x, y, w, h, STR_SYSTEM_IGM_TH);
//@#else
    RenderIGMCommon(g, x, y, w, h, FRAME_UI_TITLE_GETITEM);
//@#endif

        UtilDrawMenuBox(g, COLOR_LIGHT_GREEN, x + IGM_ARTICAL_LIST_RECT_X, y + IGM_ARTICAL_LIST_RECT_Y, IGM_ARTICAL_LIST_RECT_W, IGM_ARTICAL_LIST_RECT_H,
                        MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1);
        RenderTabel(g, FRAME_UI_TABEL2, x + IGM_ARTICAL_TABEL_X, y + IGM_ARTICAL_TABEL_Y, 0, IGM_ARTICAL_TABEL_DY, wnd.m_count, foucsID);
        for (int i = 0; i < wnd.m_count; i++)
        {
//@#if JAR_LIMIT_300K
//@            DrawString(g,STR_SYSTEM_IGM_TABLE_ITEM_PH + i, x + IGM_ARTICAL_TABEL_NAME_X, tabelY, ALIGN_LEFT_TOP,COLOR_BLACK,COLOR_WHITE);
//@#else
            s_actorUI.DrawFrame(g, x + IGM_ARTICAL_TABEL_NAME_X, tabelY, FRAME_UI_PACKAGE_ITEM0 + i);
//@#endif
            tabelY += IGM_ARTICAL_TABEL_DY;
        }
    }

    static void RenderEntityList(Graphics g, CEntity entity, int strID, boolean showIcon, int count, int foucsID, int infoType, int x, int y, int dx, int dy,
                                 int enableType, int rectW, int pos)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int color = 0, curPos = 0;
        String str = "";
        for (int i = 0; i < count; i++)
        {
            if (entity == null)
            {
                return;
            }
            if (foucsID == i)
            {
                color = COLOR_RED;
                curPos = pos;
            }
            else
            {
                switch (enableType)
                {
                case ENTITY_ENABLE_TYPE_NONE:
                    color = entity.m_color;
                    break;
                case ENTITY_ENABLE_TYPE_PROFIC:
                    color = s_actorCommon[0].m_value[BV_PROFIC_LV] >= s_dbMixtrue[entity.m_id][DB_MIXTRUE_LEVEL] ? entity.m_color : COLOR_GRAY;
                    break;
                }
                curPos = 0;
            }
            switch (infoType)
            {
            case ENTITY_INFO_TYPE_COUNT:
                str = "" + entity.m_count;
                break;
            case ENTITY_INFO_TYPE_COST_MP:
                str = "" + s_dbSkills[entity.m_id][DB_SKILLS_USEMP];
                break;
            case ENTITY_INFO_TYPE_SKILL_LV:
                str = "" + s_dbSkills[entity.m_id][DB_SKILLS_SKILL_LV];
                if (s_dbSkills[entity.m_id][DB_SKILLS_SKILL_LV] == 0)
                {
                    str = "--";
                }
                break;
            case ENTITY_INFO_TYPE_COUNT_NULL:

                break;
            }
            if (entity.m_id >= 0)
            {
                if (showIcon)
                {
                    RenderIcon(g, entity.m_icon, x + ICON_X_OFFSET, y + ICON_Y_OFFSET);
                }
                DrawTextInRectByScroll(g, entity.m_id + strID, x, y, rectW, FONT_HEIGHT, ALIGN_LEFT_TOP, color, color, SCROLL_DIR_LEFT, curPos);
                DrawSystemString(g, str, x + dx, y, ALIGN_RIGHT_TOP, color, color);
            }
            entity = entity.m_next;
            y += dy;
        }
    }

    static void RenderInformation(Graphics g, int keyID, int x, int y, int state)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        if (keyID != -1)
        {
            switch (state)
            {
            case MI_PROGRAM_FONT:
                DrawString(g, keyID, x, y, ALIGN_LEFT_TOP, COLOR_BLACK, COLOR_BLACK);
                break;
            case MI_ART_MODULE_FONT:
                s_actorUI.DrawModule(g, keyID, x, y);
                break;
            case MI_ART_FRAME_FONT:
                s_actorUI.DrawFrame(g, x, y, keyID);
                break;
            }
        }
    }

    static void UpdateIGMEquip(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CWnd wndRole = wnd.m_children[0];
        CWnd wndPropose = wnd.m_children[1];
        CWnd wndStandby = wnd.m_children[2];
        CWnd wndAdvance = wnd.m_children[3];
        CWnd wndDesc = wnd.m_children[4];
        CWnd wndMessage = wnd.m_children[5];
        CWnd wndEquipList = wndStandby.m_children[1];
        int roleID = s_menuSlot[wndRole.m_menuSlot];
        int typeID = s_menuSlot[wnd.m_menuSlot];
        int equipID;
        CActor actor = s_actorCommon[s_teamMateActorID[roleID]];

        //切换角色
        if (wndRole.m_state == WND_STATE_CYCEL || wndRole.m_state == WND_STATE_PAGEUP || wndRole.m_state == WND_STATE_PAGEDOWN)
        {
            equipID = actor.m_equips[typeID];
            wndStandby.m_data[IGM_EQUIPDATA_DATA_ACTOR] = s_teamMateActorID[roleID];
            wndDesc.m_data[IGM_DESC_DATA_ID] = equipID;
            wndDesc.m_data[IGM_DESC_SHOW_CHAR] = equipID >= 0 ? T_TRUE : T_FALSE;
            wndDesc.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_EQUIPS_DESC_0 + equipID), wndDesc.m_rect[RECT_W], SCROLL_DIR_UP));
            return;
        }
        //切换装备类型
        if (wnd.m_state == WND_STATE_CYCEL || wnd.m_state == WND_STATE_PAGEUP || wnd.m_state == WND_STATE_PAGEDOWN)
        {
            equipID = actor.m_equips[typeID];
            wndRole.m_data[IGM_EQUIPRLOE_DATA_TYPE] = EQUIP_TYPE[typeID];
            wndDesc.m_data[IGM_DESC_DATA_ID] = equipID;
            wndDesc.m_data[IGM_DESC_SHOW_CHAR] = equipID >= 0 ? T_TRUE : T_FALSE;
            wndDesc.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_EQUIPS_DESC_0 + equipID), wndDesc.m_rect[RECT_W], SCROLL_DIR_UP));
            return;
        }
        //选择行为
        if (wndPropose.m_select >= 0)
        {
            wndPropose.m_select = -1;
            switch (s_menuSlot[wndPropose.m_menuSlot])
            {
            case MI_IGM_EQUIP_PROPOSE_CHANGE:
                if (actor.m_value[BV_EQUIP_CHANGE] == ENUM_GAMEINFO_TAKE_OFF_FALSE)
                {
                    ResizeWndWithTitle(wndMessage, STR_SYSTEM_EQUIP_ERROR, 0);
                    wndMessage.DoModule(MESSAGE_SHOW_TIME);
                    return;
                }
                EnterEquipStandby(actor, wndStandby, wndEquipList, typeID, roleID);
                wndRole.m_show = false;
                wndDesc.m_show = false;
                break;
            case MI_IGM_EQUIP_PROPOSE_ADVANCE:
                equipID = actor.m_equips[typeID];
                if (s_dbEquips[equipID][DB_EQUIPS_NEXTLVNAME] < 0)
                {
                    ResizeWndWithTitle(wndMessage, STR_SYSTEM_ADVANCE_MAX, 0);
                    wndMessage.DoModule(MESSAGE_SHOW_TIME);
                    return;
                }
                wndAdvance.m_title = equipID;
                CalcEquipLevelUp(wndAdvance, equipID);
                wndAdvance.DoModule();
                wndPropose.m_show = false;
                wndDesc.m_show = false;
                break;
            }
            return;
        }
        //确认锻造
        if (wndAdvance.m_state == WND_STATE_YES)
        {
            wndAdvance.m_state = WND_STATE_WAIT;
            wndAdvance.m_title = s_dbEquips[wndAdvance.m_title][DB_EQUIPS_NEXTLVNAME];
            actor.m_equips[typeID] = wndAdvance.m_title;
            RefreshActorBV(roleID);
            wndDesc.m_show = true;
            wndAdvance.m_show = false;
            ResizeWndWithTitle(wndMessage, STR_SYSTEM_EQU_ADV_SUC, 0);
            // 升级完成后回来描述要刷新下。
            wndDesc.m_data[IGM_DESC_DATA_ID] = wndAdvance.m_title;
            //~~end
            wndMessage.DoModule(MESSAGE_SHOW_TIME);

//            if (s_dbEquips[wndAdvance.m_title][DB_EQUIPS_NEXTLVNAME] < 0)
//            {
//                wndDesc.m_show = true;
//                wndAdvance.m_show = false;
//                ResizeWndWithTitle(wndMessage, STR_SYSTEM_ADVANCE_MAX, 0);
//                wndMessage.DoModule(MESSAGE_SHOW_TIME);
//            }
//            else
//            {
//                wndAdvance.DoModule();
//                CalcEquipLevelUp(wndAdvance, wndAdvance.m_title);
//            }
            return;
        }
        else if (wndAdvance.m_state == WND_STATE_RETURN)
        {
            wndAdvance.m_state = WND_STATE_WAIT;
            wndPropose.DoModule();
            wndDesc.m_show = true;
            return;
        }
        //确定装备类型
        if (wnd.m_select >= 0)
        {
//            if (EQUIP_TYPE[wnd.m_select] == ENUM_EQUIPS_TYPE_PORTFOLIO)
//            {
//                return;
//            }
            if (actor.m_equips[wnd.m_select] >= 0)
            {
                wndPropose.DoModule();
            }
            else
            {
                if (actor.m_value[BV_EQUIP_CHANGE] == ENUM_GAMEINFO_TAKE_OFF_FALSE)
                {
                    ResizeWndWithTitle(wndMessage, STR_SYSTEM_EQUIP_ERROR, 0);
                    wndMessage.DoModule(MESSAGE_SHOW_TIME);
                    return;
                }
                EnterEquipStandby(actor, wndStandby, wndEquipList, typeID, roleID);
                wndRole.m_show = false;
                wndDesc.m_show = false;
            }
            return;
        }
        //选择了一件新装备
        if (wndStandby.m_state == WND_STATE_YES)
        {
            wndStandby.m_state = WND_STATE_WAIT;
            wndRole.m_show = true;
            wndDesc.m_show = true;
            int id = wndStandby.m_select;
            int level = 0;
            wndStandby.m_select = -1;
            if (id >= 0)
            { //新装备不为空
                level = s_dbEquips[id][DB_EQUIPS_NEEDLV];
                if (actor.m_value[BV_LV] + 1 + s_gameData[DEFINE_GAME_DATA_EQUIP_LV] >= level)
                {
                    ApplyEquip(s_teamMateActorID[roleID], typeID, id, false);
                }
                else
                {
                    ResizeWndWithTitle(wndMessage, STR_SYSTEM_LV_LESS, 0);
                    wndMessage.DoModule(MESSAGE_SHOW_TIME);
                }
            }
            else
            { //卸下
                ApplyEquip(s_teamMateActorID[roleID], typeID, id, false);
            }
            RefreshActorBV(s_teamMateActorID[roleID]);
            ReleaseTempEntity();
            equipID = actor.m_equips[typeID];
            wndDesc.m_data[IGM_DESC_SHOW_CHAR] = equipID >= 0 ? T_TRUE : T_FALSE;
            wndDesc.m_data[IGM_DESC_DATA_ID] = equipID;
            wndDesc.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_EQUIPS_DESC_0 + equipID), wndDesc.m_rect[RECT_W], SCROLL_DIR_UP));
            return;
        }
        else if (wndStandby.m_state == WND_STATE_RETURN)
        { //从装备选择界面返回
            wndStandby.m_state = WND_STATE_WAIT;
            wndRole.m_show = true;
            wndDesc.m_show = true;
            ReleaseTempEntity();
            return;
        }
//@#if SMS
//@#if TB_SMS
//@#else
//@        if (wndMessage.m_state == WND_STATE_RETURN && wndMessage.m_title == STR_SYSTEM_LESSMONEY
//@#if TS_SMS
//@#else
//@            && s_variables[SCRIPT_VARIABLE_SMS_PARA0 + ENUM_SMS_PURPOSE0_ACTIVE] > 0
//@#endif
//@                )
//@        {
//@            wndMessage.m_state = WND_STATE_WAIT;
//@            ReleaseTempEntity();
//@            CEntity entity = CEntity.GetInstance();
//@#if TS_SMS
//@            entity.m_id = 4;
//@#else
//@            entity.m_id = 2;
//@#endif
//@            entity.m_count = 1;
//@            s_tempList.InsertAfter(entity);
//@            s_wnd = CWnd.GetInstance();
//@            s_wnd.Init(WND_SMS_MESSAGE, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, true);
//@            s_wnd.m_isTop = true;
//@            s_wnd.m_curSelect = entity;
//@            s_wnd.DoModule();
//@            return;
//@        }
//@#endif
//@#endif
        //退出
        if (wnd.m_state == WND_STATE_RETURN)
        {
            s_menuSlot[MENU_SLOT_EQUIP_TYPE] = 0;
            ReleaseTempEntity();
            wnd.Close();
        }
    }

    static void EnterEquipStandby(CActor actor, CWnd wndStandby, CWnd wndEquipList, int typeID, int roleID)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CWnd wndStandbyDesc = wndStandby.m_children[0];
        ReleaseTempEntity();
        GetEquipList(EQUIP_TYPE[typeID], wndEquipList, s_teamMateActorID[roleID]);
        wndStandby.DoModule();
        wndStandbyDesc.m_data[IGM_DESC_DATA_ID] = wndEquipList.m_curSelect.m_id;
        wndStandbyDesc.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_EQUIPS_DESC_0 + wndStandbyDesc.m_data[IGM_DESC_DATA_ID]), wndStandbyDesc.m_rect[RECT_W],
                                                SCROLL_DIR_UP));
        wndStandby.m_data[IGM_EQUIPSTANDBY_DATA_ID] = actor.m_equips[typeID];
        s_menuSlot[wndStandby.m_menuSlot] = EQUIP_TYPE[typeID];
        UpdateIGMEquipCompare(wndEquipList, actor.m_equips[typeID]);
    }

    static void UpdateIGMEquipRole(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h

    }

    static int s_costMoneyTotal;

    static void UpdateIGMSystem(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CWnd wndChild = wnd.m_children[0];
        CWnd wndConfirm = wnd.m_children[1];
        CWnd wndMessage = wnd.m_children[2];
        CWnd wndSoftkey = wnd.m_children[3];
        wndSoftkey.m_show = true;
        if (wndConfirm.m_state == WND_STATE_YES)
        {
            wndConfirm.m_state = WND_STATE_WAIT;
            s_wnd.Close();
//@#if Channel == "GF"
            SetGameState(GS_UPLOADSCORE);
//@#else
//@            SetGameState(GS_LOADMENU);
//@#endif
            return;
        }
        else if (wndConfirm.m_state == WND_STATE_NO)
        {
            wndConfirm.m_state = WND_STATE_WAIT;
            wndConfirm.Close();
            return;
        }

        if (wndChild.m_state == WND_STATE_RETURN)
        {
            wndChild.m_state = WND_STATE_WAIT;
            wndChild.RemoveAllChildren();
            wndChild.Free();
            wndChild.m_parent = wnd;
            s_needRefreshBG = true;
            return;
        }

        switch (wnd.m_select)
        {
//@#if SMS
//@        case MI_IGM_SYSTEM_SMS_SHOP:
//@            wndSoftkey.m_show = false;
//@#if TS_SMS
//@            ReleaseTempEntity();
//@            wndChild.Init(WND_SMS_MENU, IGM_X, IGM_Y, IGM_W, IGM_H, true);
//@            wndChild.DoModule();
//@#else
//@            if (s_entityList[ENTITY_LIST_SMS].m_next != null)
//@            {
//@                ReleaseTempEntity();
//@                wndChild.Init(WND_SMS_MENU, IGM_X, IGM_Y, IGM_W, IGM_H, true);
//@                wndChild.DoModule();
//@            }
//@            else
//@            {
//@                ResizeWndWithTitle(wndMessage, STR_SYSTEM_SMS_SHOP_INACTIVE, 0);
//@                wndMessage.DoModule(MESSAGE_SHOW_TIME);
//@            }
//@#endif
//@            break;
//@#endif
        case MI_IGM_SYSTEM_SAVE:
            if (s_variables[SCRIPT_VARIABLE_SAVE_ON] == T_TRUE)
            {
                wndChild.Init(WND_SAVE, IGM_X, IGM_Y, IGM_W, IGM_H, true);
                wndChild.DoModule();
            }
            else
            {
                ResizeWndWithTitle(wndMessage, STR_SYSTEM_SAVE_ON, 0);
                wndMessage.DoModule(MESSAGE_SHOW_TIME);
            }
            break;
        case MI_IGM_SYSTEM_LOAD:
            wndChild.Init(WND_LOAD, IGM_X, IGM_Y, IGM_W, IGM_H, true);
            wndChild.DoModule();
            break;
        case MI_IGM_SYSTEM_OPTION:
            wndChild.Init(WND_OPTION, IGM_X, IGM_Y, IGM_W, IGM_H, true);
            wndChild.DoModule();
            break;
        case MI_IGM_SYSTEM_HELP:
            wndChild.Init(WND_HELP, IGM_X, IGM_Y, IGM_W, IGM_H, true);
            wndChild.DoModule();
            int posMax = CalcTextPage(GetString(STR_SYSTEM_GS_HELP_CONTENT), wndChild.m_rect[RECT_W] - (HELP_TEXT_X << 1),
                                  wndChild.m_rect[RECT_H] - (HELP_TEXT_Y << 1) - (FONT_HEIGHT << 1));
            wndChild.ResetTextPos(posMax);
            break;
        case MI_IGM_SYSTEM_EXIT:
            ResizeWndWithTitle(wndConfirm, STR_SYSTEM_ISRETURN, FONT_HEIGHT);
            wndConfirm.DoModule();
            break;
//@#if QQ_FORUM
//@        case MI_IGM_SYSTEM_SCORE:
//@            wndChild.Init(WND_QQ_SCORE, IGM_X, IGM_Y, IGM_W, IGM_H, true);
//@            wndChild.DoModule();
//@            break;
//@#endif
        }

        if (wnd.m_state == WND_STATE_RETURN)
        {
            wnd.ReturnFocus();
        }
    }

    static void UpdateExitGame(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
//@#if PHONE_TOUCH
//@        if (IsTouchInPoint(0, SCREEN_HEIGHT - POINTER_RANGE_H, POINTER_RANGE_W, POINTER_RANGE_H))
//@        {
//@            KeyHandlePressed(KEYVALUE_LEFTSOFT);
//@        }
//@        else if (IsTouchInPoint(SCREEN_WIDTH - POINTER_RANGE_W, SCREEN_HEIGHT - POINTER_RANGE_H, POINTER_RANGE_W, POINTER_RANGE_H))
//@        {
//@            KeyHandlePressed(KEYVALUE_RIGHTSOFT);
//@        }
//@#endif
//@#if MORE_GAME_LINK
        if (IsKeySinglePressed(GKEY_SOFTLEFT))
        {
            s_url = "http://gamepie.g188.net/gamecms/go/jpgd";
            s_wndMainMenu.Release();
            s_wndMainMenu = null;
            SetGameState(GS_GAME_LINK);
        }
        else if (IsKeySinglePressed(GKEY_SOFTRIGHT))
        {
            SetGameState(GS_QUIT);
        }
//@#endif
    }

    static void RenderIGMSystem(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        int foucs = s_menuSlot[wnd.m_menuSlot];
        int curY = y + IGM_SYSTEM_FRAME_Y;
        UtilDrawMenuBox(g, COLOR_HOARY, x + SYSTEM_BOX_X, y + SYSTEM_BOX_Y, w + SYSTEM_BOX_W, h + SYSTEM_BOX_H, MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1);
        for (int i = 0; i < wnd.m_count; i++)
        {
//@#if JAR_LIMIT_300K
//@#if SMS
//@            DrawString(g, STR_SYSTEM_SYSTEM_SMS_SHOP + i, x + (w >> 1), curY, ALIGN_HCENTER_TOP,
//@#else
//@            DrawString(g, STR_SYSTEM_SYSTEM_SAVE + i, x + (w >> 1), curY, ALIGN_HCENTER_TOP,
//@#endif
//@                              COLOR_DARK_GRAY, COLOR_BLACK);
//@#else
            s_actorUI.DrawFrame(g, x + (w >> 1), curY, FRAME_UI_SYSTEM_ITEM_0 + i);
//@#endif
            curY += IGM_SYSTEM_FRAME_OFFY;
        }
        s_actorUI.DrawFrame(g, x + IGM_SYSTEM_CURSOR_X, y + IGM_SYSTEM_CURSOR_Y + foucs * IGM_SYSTEM_FRAME_OFFY, FRAME_UI_CURSOR);

//@#if JAR_LIMIT_300K
//@        RenderIGMCommon(g, x, y, w, h, STR_SYSTEM_SCENCE_SYSTEM);
//@#else
     RenderIGMCommon(g, x, y, w, h, FRAME_UI_TITLE_SYSTEM);
//@#endif

    }

    static void UpdatePortableShop(CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CWnd wndShop = wnd.m_children[0];
        CWnd wndShopList = wndShop.m_children[0];

        wnd.UpdateList();

        if (wnd.m_select >= 0)
        {
            GetPortableShopGoods(wnd.m_curSelect.m_id);
            wndShopList.m_curSelect = CGame.s_entityList[ENTITY_LIST_BUY].m_next;
            wndShopList.m_showFirst = wndShopList.m_curSelect;
            wndShopList.m_count = wndShopList.m_curSelect == null ? 0 : wndShopList.m_curSelect.GetCount();
            s_menuSlot[wndShop.m_menuSlot] = 0;
            wndShop.DoModule();
            return;
        }

        if (wnd.m_state == WND_STATE_RETURN)
        {
            ReleaseTempEntity();
            wnd.Close();
        }
    }

    static void RenderPortableShop(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int foucsID = s_menuSlot[wnd.m_menuSlot];

        RenderEntityList(g, wnd.m_showFirst, STR_SYSTEM_SHOP_NAME_0, true, wnd.m_showNum, foucsID, ENTITY_INFO_TYPE_NONE, x + PORTABLE_SHOP_LIST_X,
                         y + PORTABLE_SHOP_LIST_Y, 0, PORTABLE_SHOP_LIST_DY, ENTITY_ENABLE_TYPE_NONE, PORTABLE_SHOP_LIST_W, wnd.m_timer);
    }

    static void GetPortableShopGoods(int goodsType)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CEntity entity = s_entityList[ENTITY_LIST_PORTABLE_GOODS].m_next;
        CEntity entityBuy = s_entityList[ENTITY_LIST_BUY];
        CEntity newEntity = null;
        int id, type;

        s_entityList[ENTITY_LIST_BUY].ReleaseAll();
        s_entityList[ENTITY_LIST_BUY].m_next = null;
        while (entity != null)
        {
            type = GetArticalType(entity.m_id);
            id = GetArticalID(entity.m_id);
            if (entity.m_type == goodsType)
            {
                newEntity = CEntity.GetInstance();
                newEntity.m_id = id;
                newEntity.m_type = (byte) type;
                switch (type)
                {
                case DEFINE_HOLD_TYPE_ITEM:
                    newEntity.m_icon = s_dbItems[id][DB_ITEMS_ICON];
                    break;
                case DEFINE_HOLD_TYPE_EQUIP:
                    newEntity.m_icon = s_dbEquips[id][DB_EQUIPS_ICON];
                    break;
                }
                entityBuy.InsertAfter(newEntity);
                entityBuy = newEntity;
            }
            entity = entity.m_next;
        }
    }

    /**
     * 更新存档界面,显示存档列表,玩家选择一个存档后,弹出确定窗口,YES保存;NO返回.
     *
     * @param wnd CWnd 窗口引用的CWnd对象.
     */
    static void UpdateRMS(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CWnd wndConfirm = wnd.m_children[2];
        CWnd wndMessage = wnd.m_children[3];

        for (int i = 0; i < wnd.m_count; i++)
        {
            //判断箭头停留在那个窗口
            wnd.m_children[i].m_title = i == s_menuSlot[wnd.m_menuSlot] ? T_TRUE : T_FALSE;
        }

        if (wndMessage.m_state == WND_STATE_YES)
        {
            wndMessage.m_state = WND_STATE_WAIT;
            RMS(true, s_menuSlot[MENU_SLOT_RMS]);
            ResizeWndWithTitle(wndMessage, STR_SYSTEM_SAVE_SUCCESS, 0);
            wndMessage.DoModule(20);
            return;
        }

        if (wndConfirm.m_state == WND_STATE_YES)
        {
            wndConfirm.m_state = WND_STATE_WAIT;
            switch (wnd.m_type)
            {
            case WND_LOAD:
                s_loadScene = s_rmsInfo[s_menuSlot[MENU_SLOT_RMS]][RMS_INFO_SCENEID];
                s_sceneLoadType = SCENELOAD_TYPE_LOAD;
                wnd.Close();
                SetGameState(GS_CONTINUE);
                break;
            case WND_SAVE:
                wndMessage.m_show = true;
                wndMessage.m_state = WND_STATE_YES;
                ResizeWndWithTitle(wndMessage, STR_SYSTEM_SAVEING, 0);
                break;
            }
            return;
        }
        else if (wndConfirm.m_state == WND_STATE_NO)
        {
            wndConfirm.m_state = WND_STATE_WAIT;
            s_menuSlot[wndConfirm.m_menuSlot] = 0;
            return;
        }

        if (wnd.m_select >= 0)
        {
            switch (wnd.m_type)
            {
            case WND_LOAD:
                if (s_rmsInfo[s_menuSlot[MENU_SLOT_RMS]][RMS_INFO_RMS] > 0)
                {
                    ResizeWndWithTitle(wndConfirm, STR_SYSTEM_UI_SAVE_MENU_RMS_LOAD, FONT_HEIGHT);
                    wndConfirm.DoModule();
                }
                else
                {
                    ResizeWndWithTitle(wndMessage, STR_SYSTEM_EMPTYRMS, 0);
                    wndMessage.DoModule(20);
                }
                break;
            case WND_SAVE:
                wndConfirm.DoModule();
                if (s_rmsInfo[s_menuSlot[MENU_SLOT_RMS]][RMS_INFO_RMS] == 0)
                {
                    ResizeWndWithTitle(wndConfirm, STR_SYSTEM_UI_SAVE_MENU_RMS_SAVE, FONT_HEIGHT);
                }
                else
                {
                    ResizeWndWithTitle(wndConfirm, STR_SYSTEM_UI_SAVE_MENU_RMS_COVER, FONT_HEIGHT);
                }
                break;
            }
        }
        else if (wnd.m_state == WND_STATE_RETURN)
        {
            wnd.Close();
        }
    }

    static void RenderRMSSlot(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int slot = wnd.m_id;
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        int color = COLOR_HOARY;
        if (wnd.m_title == T_TRUE)
        {
            color = COLOR_WHITE;
        }
        //覆盖初始化颜色 从新更改
        UtilDrawMenuBox(g, color, x, y, w, h, MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1,
                        MODULE_UI_VERTI_1);

//@#if SHOW_RMS_ID
//@#if JAR_LIMIT_300K
//@         DrawString(g, STR_SYSTEM_UI_SAVE_MENU_RMS0+slot, x + IGM_RMS_ID_X, y + LOADING_TITLE_Y, ALIGN_HCENTER_TOP, COLOR_WHITE, COLOR_BLACK);
//@#else
        s_actorUI.DrawModule(g, MODULE_UI_RMS_0 + slot, x + IGM_RMS_ID_X, y + IGM_RMS_ID_Y);
//@#endif
//@#endif
        if (s_rmsInfo[slot][RMS_INFO_RMS] > 0)
        {
            //时间
            g.setColor(COLOR_ORANGE);
            g.fillRoundRect(x + IGM_RMS_INFO_RECT_X, y + IGM_RMS_INFO_RECT_Y, IGM_RMS_INFO_RECT_W, IGM_RMS_INFO_RECT_H, 5, 5);
            g.fillRoundRect(x + IGM_RMS_TIME_RECT_X, y + IGM_RMS_TIME_RECT_Y, IGM_RMS_TIME_RECT_W, IGM_RMS_TIME_RECT_H, 5, 5);
            s_actorUI.DrawFrame(g, x + IGM_RMS_INFO_MONEY_X, y + IGM_RMS_INFO_ICON_Y, FRAME_UI_RMS_MONEY);
            s_actorUI.DrawFrame(g, x + IGM_RMS_INFO_TOKEN_X, y + IGM_RMS_INFO_ICON_Y, FRAME_UI_RMS_TOKEN);
            s_actorUI.DrawFrame(g, x + IGM_RMS_TIME_ICON_X, y + IGM_RMS_TIME_ICON_Y, FRAME_UI_RMS_CLOCK);
            UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, s_rmsInfo[slot][RMS_INFO_MONEY], x + IGM_RMS_INFO_MONEY_VALUE_X, y + IGM_RMS_INFO_VALUE_Y, ALIGN_LEFT_TOP);
            UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, s_rmsInfo[slot][RMS_INFO_TOKEN], x + IGM_RMS_INFO_TOKEN_VALUE_X, y + IGM_RMS_INFO_VALUE_Y, ALIGN_LEFT_TOP);
            UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, s_rmsInfo[slot][RMS_INFO_HOUR], x + IGM_RMS_TIME_HOUR_X, y + IGM_RMS_TIME_VALUE_Y, ALIGN_RIGHT_TOP);
            s_actorUI.DrawModule(g, MODULE_UI_COLON, x + IGM_RMS_TIME_HOUR_COLON_X, y + IGM_RMS_TIME_VALUE_Y);
            UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, s_rmsInfo[slot][RMS_INFO_MIN], x + IGM_RMS_TIME_MIN_X, y + IGM_RMS_TIME_VALUE_Y, ALIGN_RIGHT_TOP);
            s_actorUI.DrawModule(g, MODULE_UI_COLON, x + IGM_RMS_TIME_MIN_COLON_X, y + IGM_RMS_TIME_VALUE_Y);
            UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, s_rmsInfo[slot][RMS_INFO_SECOND], x + IGM_RMS_TIME_SECOND_X, y + IGM_RMS_TIME_VALUE_Y, ALIGN_RIGHT_TOP);
            for (int i = 0; i < TEAM_SIZE ; i++)  // TEAM_SIZE 头像icon数量
            {
                if (s_rmsInfo[slot][RMS_INFO_ALLY0 + i] >= 0)
                {
                    s_actorUI.DrawFrame(g, x + IGM_RMS_PORTRAIT_BG_X + i * IGM_RMS_PORTRAIT_BG_OFFX, y + IGM_RMS_PORTRAIT_BG_Y, FRAME_UI_ICON_BG);
                    s_actorUI.DrawFrame(g, x + IGM_RMS_PORTRAIT_X + i * IGM_RMS_PORTRAIT_OFFX, y + IGM_RMS_PORTRAIT_Y,
                                        FRAME_UI_HERO_ICON_0 + s_rmsInfo[slot][RMS_INFO_ALLY0 + i]);
                }
            }
        }
        if (wnd.m_title == T_TRUE)
        {
           s_actorUI.DrawFrame(g, x + w - IGM_RMS_CURSOR_X, y + h - IGM_RMS_CURSOR_Y, FRAME_UI_CURSOR);
        }
    }

    static void RenderRMS(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
//@#if JAR_LIMIT_300K
//@        DrawString(g, STR_SYSTEM_UI_SAVE_MENU_RMS_TITLE, SCREEN_WIDTH_HALF, y + LOADING_TITLE_Y, ALIGN_HCENTER_TOP, COLOR_WHITE, COLOR_BLACK);
//@#else
        RenderIGMCommon(g, x, y, w, h, wnd.m_type == WND_LOAD ? FRAME_UI_TITLE_LOAD : FRAME_UI_TITLE_SAVE);
//@#endif
    }

    static void RenderExit(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        UtilFillRect(g, COLOR_BLACK, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
//@#if MORE_GAME_LINK
        g.drawImage(s_imgMoreGame, SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF, ALIGN_CENTER);
//@#if Channel == "GF"
        DrawSystemString(g, "更多精彩游戏", SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF - FONT_HEIGHT, ALIGN_HCENTER_TOP, COLOR_WHITE, COLOR_WHITE);
        DrawSystemString(g, "尽在游戏频道", SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF, ALIGN_HCENTER_TOP, COLOR_WHITE, COLOR_WHITE);
        DrawSystemString(g, "wap.xjoys.com", SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF + FONT_HEIGHT, ALIGN_HCENTER_TOP, COLOR_WHITE, COLOR_WHITE);
        DrawSystemString(g, "确认", 5, SCREEN_HEIGHT - 5, ALIGN_LEFT_BOTTOM, COLOR_WHITE, COLOR_WHITE);
        DrawSystemString(g, "退出", SCREEN_WIDTH - 5, SCREEN_HEIGHT - 5, ALIGN_RIGHT_BOTTOM, COLOR_WHITE, COLOR_WHITE);
//@#endif
//@#endif
    }

    static Image s_imgSplash;
    static CActor s_actorSplash;
    static CActor s_actorMainMenu;

    static void RenderMainMenu(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
//@#if JAR_LIMIT_300K
//@        s_actorMainMenu.DrawAnimation(g, 0, 0);
//@        s_actorSplash.DrawAnimation(g, 0, 0);
//@#if MORE_GAME_LINK
//@        DrawString(g,STR_SYSTEM_MAIN_MENU_0 + s_menuSlot[wnd.m_menuSlot],SCREEN_WIDTH_HALF,IGM_MIAN_MENU_STR_Y,ALIGN_HCENTER_TOP, s_menuSlot[wnd.m_menuSlot] == MI_MAINMENU_MORE ? COLOR_RED : COLOR_WHITE,COLOR_BLACK);
//@#else
//@        DrawString(g,STR_SYSTEM_MAIN_MENU_0 + s_menuSlot[wnd.m_menuSlot],SCREEN_WIDTH_HALF,IGM_MIAN_MENU_STR_Y,ALIGN_HCENTER_TOP,COLOR_WHITE,COLOR_BLACK);
//@#endif
//@#else
        s_actorMainMenu.DrawAnimation(g, 0, 0);
        s_actorSplash.DrawAnimation(g, 0, 0);
//@#switch(platform)
//@#{
//@#case "NOKIA_N5800":
//@        s_actorSplash.DrawFrame(g, SCREEN_WIDTH_HALF - 10, IGM_MIAN_MENU_STR_Y, FRAME_SPLASH_ANIM_NEW_GAME + s_menuSlot[wnd.m_menuSlot]);
//@#break
//@#default:
        s_actorSplash.DrawFrame(g, SCREEN_WIDTH_HALF, IGM_MIAN_MENU_STR_Y, FRAME_SPLASH_ANIM_NEW_GAME + s_menuSlot[wnd.m_menuSlot]);
//@#}
//@#endif
    }

    static void UpdateMainMenu(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int posMax = 0;
        CWnd wndRMS = wnd.m_children[0];
        CWnd wndFree = wnd.m_children[1];
        CWnd wndExit = wnd.m_children[2];
        CWnd wndConfirm = wnd.m_children[3];
//@#if SMS
//@        CWnd wndSMS = wnd.m_children[4];
//@#if QQ_FORUM
//@        CWnd wndForum = wnd.m_children[5];
//@#endif
//@#endif
//@#if MORE_GAME_LINK
        CWnd wndLink = wnd.m_children[6];
//@#endif
        s_actorSplash.UpdateAnimation();
        s_actorMainMenu.UpdateAnimation();
        if (wndConfirm.m_state == WND_STATE_YES)
        {
            wndConfirm.m_state = WND_STATE_WAIT;
            wndConfirm.Close();
//@#if MORE_GAME_LINK
            wndExit.DoModule();
            wnd.m_show = false;
//@#else
//@            SetGameState(GS_QUIT);
//@#endif
            return;
        }
//@#if SMS
//@        if (wndSMS.m_state == WND_STATE_RETURN)
//@        {
//@            wndSMS.m_state = WND_STATE_WAIT;
//@            switch (s_menuSlot[wnd.m_menuSlot])
//@            {
//@            case MI_MAINMENU_NEW:
//@                s_loadData = LOAD_DATA_COMMON | LOAD_DATA_OTHER;
//@                s_sceneLoadType = SCENELOAD_TYPE_NEW;
//@                wnd.Close();
//@                SetGameState(GS_LOADSCENE);
//@                wnd.RemoveAllChildren();
//@                FreeMainMenu();
//@                break;
//@            case MI_MAINMENU_LOAD:
//@                wnd.m_children[s_menuConfirmID - 1].DoModule();
//@                break;
//@            }
//@        }
//@
//@        if (wndFree.m_state == WND_STATE_RETURN)
//@        {
//@            wndFree.m_state = WND_STATE_WAIT;
//@            wndFree.RemoveAllChildren();
//@            wndFree.Free();
//@            wndFree.m_show = false;
//@            wndFree.m_parent = wnd;
//@            return;
//@        }
//@#endif
        switch (wnd.m_select)
        {
        case MI_MAINMENU_NEW:
//@#if SMS
//@#if not TS_SMS
//@            if (s_variables[SCRIPT_VARIABLE_SMS_PARA0 + ENUM_SMS_PURPOSE0_ACTIVE] == T_FALSE && s_variables[SCRIPT_VARIABLE_SMS_NEED_ACTIVE] == T_TRUE)
//@            { //提示需要激活游戏
//@                wndSMS.DoModule();
//@                ReleaseTempEntity();
//@                CEntity entity = CEntity.GetInstance();
//@                entity.m_id = 5;
//@                entity.m_count = 1;
//@                s_tempList.InsertAfter(entity);
//@                wndSMS.m_curSelect = entity;
//@            }
//@            else
//@#endif
//@#endif
            {
                //开始新游戏
                //本身失去焦点
                s_loadData = LOAD_DATA_COMMON | LOAD_DATA_OTHER;
                s_sceneLoadType = SCENELOAD_TYPE_NEW;
                wnd.Close();
                SetGameState(GS_LOADSCENE);
                wnd.RemoveAllChildren();
                FreeMainMenu();
            }
            break;
//@#if Channel == "GF"
//@#switch (GF_PACKAGE)
//@#{
//@#case "CONNECT":
            case MI_MAINMENU_GAMEZONE:
                if(GameCenter.isLogin())
                {
                    GameCenter.call(IMARPG22.s_midlet, new GFChallenge());
                }
                else
                {
                    CONNECT_ID = CONNECT_GAMEZONE;
                    SetGameState(GS_CONNECT);
                }
                break;
//@#case "SMS":
            case MI_MAINMENU_RECOMMEND:
                if (GameCenter.isLogin())
                {
                    GameCenter.call_MoreGame(IMARPG22.s_midlet);
                }
                else
                {
                    CONNECT_ID = CONNECT_RECOMMEND;
                    SetGameState(GS_CONNECT);
                }
                break;
//@#}
//@#endif
//@#if EXIST_SOUND
        case MI_MAINMENU_OPTION:
            wndFree.Init(WND_OPTION, IGM_X, IGM_Y, IGM_W, IGM_H, true);
            wndFree.DoModule();
            break;
//@#endif
        case MI_MAINMENU_HELP:
            wndFree.Init(WND_HELP, IGM_X, IGM_Y, IGM_W, IGM_H, true);
            wndFree.DoModule();
            posMax = CalcTextPage(GetString(STR_SYSTEM_GS_HELP_CONTENT), wndFree.m_rect[RECT_W] - (HELP_TEXT_X << 1),
                                  wndFree.m_rect[RECT_H] - (HELP_TEXT_Y << 1) - (FONT_HEIGHT << 1));
            wndFree.ResetTextPos(posMax);
            break;
        case MI_MAINMENU_ABOUT:
            wndFree.Init(WND_ABOUT, IGM_X, IGM_Y, IGM_W, IGM_H, true);
            wndFree.DoModule();
            posMax = CalcTextPage(GetString(STR_SYSTEM_GS_ABOUT_CONTENT), wndFree.m_rect[RECT_W] - (ABOUT_TEXT_X << 1),
                                  wndFree.m_rect[RECT_H] - (ABOUT_TEXT_Y << 1) - (FONT_HEIGHT << 1));
            wndFree.ResetTextPos(posMax);
            break;
        case MI_MAINMENU_EXIT:
            ResizeWndWithTitle(wndConfirm, STR_SYSTEM_ISEIXT, FONT_HEIGHT);
            wndConfirm.DoModule();
            break;
        case MI_MAINMENU_LOAD:
//@#if SMS
//@#if not TS_SMS
//@            if (s_variables[SCRIPT_VARIABLE_SMS_PARA0 + ENUM_SMS_PURPOSE0_ACTIVE] == T_FALSE && s_variables[SCRIPT_VARIABLE_SMS_NEED_ACTIVE] == T_TRUE)
//@            { //提示需要激活游戏
//@                wndSMS.DoModule();
//@                ReleaseTempEntity();
//@                CEntity entity = CEntity.GetInstance();
//@                entity.m_id = 5;
//@                entity.m_count = 1;
//@                s_tempList.InsertAfter(entity);
//@                wndSMS.m_curSelect = entity;
//@            }
//@            else
//@#endif
//@#endif
            {
                wndRMS.DoModule();
            }
            break;
//@#if MORE_GAME_LINK
        case MI_MAINMENU_MORE:
            wndLink.DoModule();
            break;
//@#endif
//@#if QQ_FORUM
//@        case MI_MAINMENU_FORUM:
//@            wndForum.DoModule();
//@            InitQQForum(MBoxClient.PAGE_MAIN);
//@            break;
//@        case MI_MAINMENU_TOP:
//@            wndForum.DoModule();
//@            InitQQForum(MBoxClient.PAGE_SHOWSCORES);
//@            break;
//@#endif
        }
    }

    static void RenderIGMMission(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CWnd wndList = wnd.m_children[0];
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        int type = s_menuSlot[wnd.m_menuSlot];
        int foucsID = s_menuSlot[wndList.m_menuSlot];
        int i, curY = y + IGM_MISSION_LIST_Y + ICON_Y_OFFSET;

        RenderTabel(g, FRAME_UI_TABEL0, x + IGM_MISSION_TABEL_X, y + IGM_MISSION_TABEL_Y, IGM_MISSION_TABEL_DX, 0, wnd.m_count, type);
        for (i = 0; i < wnd.m_count; i++)
        {
//@#if JAR_LIMIT_300K
//@            DrawString(g,STR_SYSTEM_MAIN_MISSION+i,x + IGM_MISSION_TABEL_NAME_X + (IGM_MISSION_TABEL_DX >> 1) + IGM_MISSION_TABEL_DX * i, y + IGM_MISSION_TABEL_NAME_Y, ALIGN_HCENTER_TOP,COLOR_BLACK,COLOR_WHITE);;
//@#else
            s_actorUI.DrawFrame(g, x + IGM_MISSION_TABEL_NAME_X + IGM_MISSION_TABEL_DX * i, y + IGM_MISSION_TABEL_NAME_Y, FRAME_UI_MISSION_ITEM0 + i);
//@#endif
        }
        UtilDrawMenuBox(g, COLOR_LIGHT_GREEN, x + IGM_MISSION_RECT_X, y + IGM_MISSION_RECT_Y, IGM_MISSION_RECT_W, IGM_MISSION_RECT_H, MODULE_UI_CONNER_1,
                        MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1);

        for (i = 0; i < wndList.m_showNum; i++)
        {
            s_actorUI.DrawFrame(g, x + IGM_MISSION_ICON_X, curY, FRAME_UI_ICON_BG);
            curY += IGM_MISSION_LIST_DY;
        }
        RenderEntityList(g, wndList.m_showFirst, STR_SYSTEM_TASKS_NAME_0, true, wndList.m_showNum, foucsID - wndList.m_listStartPos, ENTITY_INFO_TYPE_NONE,
                         x + IGM_MISSION_LIST_X, y + IGM_MISSION_LIST_Y, 0, IGM_MISSION_LIST_DY, ENTITY_ENABLE_TYPE_NONE, IGM_MISSION_TXT_W, wnd.m_timer);
        CEntity entity = wndList.m_showFirst;
        curY = y + IGM_MISSION_LIST_Y + ICON_Y_OFFSET;
        for (i = 0; i < wndList.m_showNum; i++)
        {
            if (entity == null)
            {
                break;
            }
            if (entity.m_count > 1)
            {
//@#if JAR_LIMIT_300K
//@                DrawString(g,STR_SYSTEM_IGM_TABLE_MI_COMPLETE,x + IGM_MISSION_LIST_COMPLETE_X,curY-8,ALIGN_LEFT_TOP,COLOR_BLACK,COLOR_RED);
//@#else
                s_actorUI.DrawFrame(g, x + IGM_MISSION_LIST_COMPLETE_X, curY, FRAME_UI_MISSION_COMPLETE);
//@#endif
            }
            entity = entity.m_next;
            curY += IGM_MISSION_LIST_DY;
        }
        RenderScrollBar(g, FRAME_UI_SCROLL_BAR_TOP, x + IGM_SKILL_SCROLL_BAR_X, y + IGM_SKILL_SCROLL_BAR_Y, IGM_SKILL_SCROLL_BAR_H, foucsID, wndList.m_count);
//@#if JAR_LIMIT_300K
//@        RenderIGMCommon(g, x, y, w, h, STR_SYSTEM_IGM_TITLE_MISSION);
//@#else
     RenderIGMCommon(g, x, y, w, h, FRAME_UI_TITLE_MISSION);
//@#endif

    }

    static void UpdateIGMMission(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CWnd wndList = wnd.m_children[0];
        CWnd wndDesc = wnd.m_children[1];
        int type = s_menuSlot[wnd.m_menuSlot];

        UpdateWndTextPos(wnd, IGM_MISSION_TXT_W, IGM_TASK_TEXT_WAIT_TIME);
        if (wnd.m_state == WND_STATE_PAGEUP || wnd.m_state == WND_STATE_PAGEDOWN)
        {
            ReleaseTempEntity();
            GetEntityIndex(type, wndList, s_entityList[ENTITY_LIST_MISSION]);
            if (wndList.m_curSelect != null)
            {
                wndDesc.m_data[IGM_DESC_DATA_ID] = wndList.m_curSelect.m_id;
                wnd.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_TASKS_NAME_0 + wndList.m_curSelect.m_id), IGM_MISSION_TXT_W, SCROLL_DIR_UP));
                wndDesc.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_TASKS_DESC_0 + wndList.m_curSelect.m_id), wndDesc.m_rect[RECT_W], SCROLL_DIR_UP));
            }
            else
            {
                wndDesc.m_data[IGM_DESC_DATA_ID] = -1;
            }
            return;
        }

        if (wndList.m_state == WND_STATE_PAGEUP || wndList.m_state == WND_STATE_PAGEDOWN)
        {
            wndDesc.m_data[IGM_DESC_DATA_ID] = wndList.m_curSelect.m_id;
            wnd.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_TASKS_DESC_0 + wndList.m_curSelect.m_id), IGM_MISSION_TXT_W, SCROLL_DIR_UP));
            wndDesc.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_TASKS_DESC_0 + wndList.m_curSelect.m_id), wndDesc.m_rect[RECT_W], SCROLL_DIR_UP));
            return;
        }

        if (wnd.m_state == WND_STATE_RETURN)
        {
            ReleaseTempEntity();
            wnd.Close();
        }
    }

    static int GetPrice(int mode, int type, int id)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int costMoney = 0;
        switch (type)
        {
        case DEFINE_HOLD_TYPE_ITEM:
        case DEFINE_HOLD_TYPE_BATTLE:
            if (mode == MI_IGM_SHOP_BUY)
            {
                costMoney = s_dbItems[id][DB_ITEMS_BUY];
            }
            else
            {
                costMoney = s_dbItems[id][DB_ITEMS_SELL];
            }
            break;
        case DEFINE_HOLD_TYPE_EQUIP:
            if (mode == MI_IGM_SHOP_BUY)
            {
                costMoney = s_dbEquips[id][DB_EQUIPS_BUY];
            }
            else
            {
                costMoney = s_dbEquips[id][DB_EQUIPS_SELL];
            }
            break;
        }
        return costMoney;
    }

    static void RenderLootExp(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        int curY = y + IGM_LOOT_EXP_RECT_Y;
        int dataID = 0, pro = 0;
        CActor actor = null;

        for (int i = 0; i < BATTLE_ALLY_MAX; i++)
        {
            UtilDrawMenuBox(g, COLOR_LIGHT_GREEN, x + IGM_LOOT_EXP_RECT_X, curY, IGM_LOOT_EXP_RECT_W, IGM_LOOT_EXP_RECT_H, MODULE_UI_CONNER_1,
                            MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1);
            if (i < wnd.m_count)
            {
                actor = s_actorCommon[wnd.m_data[dataID]];
                s_actorUI.DrawFrame(g, x + IGM_LOOT_EXP_ICON_X, curY + IGM_LOOT_EXP_ICON_Y, FRAME_UI_HERO_ICON_0 + actor.m_classID);
                UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, wnd.m_data[dataID + 2], x + IGM_LOOT_EXP_NUM_X, curY + IGM_LOOT_EXP_NUM_Y, ALIGN_LEFT_TOP);
                RenderActorLV(g, actor, x + IGM_LOOT_EXP_LEVEL_X, curY + IGM_LOOT_EXP_LEVEL_Y);
                pro = actor.m_value[BV_NEXTEXP] > actor.m_value[BV_LASTEXP] ?
                      ((actor.m_value[BV_EXP] - actor.m_value[BV_LASTEXP]) * 100 / (actor.m_value[BV_NEXTEXP] - actor.m_value[BV_LASTEXP])) : 0;
                RenderValueBar(g, ACTOR_DATA_TYPE_MP, x + IGM_LOOT_EXP_BAR_X, curY + IGM_LOOT_EXP_BAR_Y, IGM_LOOT_EXP_BAR_W, pro);
                if (s_actorLevelUp[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] == T_TRUE)
                {
                    s_actorLevelUp[i].DrawAnimation(g, 0, 0);
                }
                dataID += WND_LOOT_EXP_DATA_PER;
            }
            curY += IGM_LOOT_EXP_RECT_OFFY;
        }
//        RenderIGMCommon(g, x, y, w, h, FRAME_UI_TITLE_GETITEM);
    }

    static void RenderLootNewSkill(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int dataID = wnd.m_count * WND_LOOT_LEARN_DATA_PER;

        DrawString(g, STR_SYSTEM_GAMEINFO_NAME_0 + wnd.m_data[dataID], x + (w >> 1), y + IGM_LOOT_SKILL_TEXT_Y, ALIGN_HCENTER_TOP, COLOR_BLACK, COLOR_BLACK);
        DrawString(g, STR_SYSTEM_HAVA_SKILL_LVUP, x + (w >> 1), y + IGM_LOOT_SKILL_TEXT_Y + FONT_HEIGHT, ALIGN_HCENTER_TOP, COLOR_BLACK, COLOR_BLACK);
        DrawString(g, STR_SYSTEM_SKILL_NAME_DESC_0 + wnd.m_data[dataID + 1], x + (w >> 1), y + IGM_LOOT_SKILL_TEXT_Y + FONT_HEIGHT * 2, ALIGN_HCENTER_TOP,
                   COLOR_BLACK, COLOR_BLACK);
    }

    static void RenderLootFail(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];

        DrawString(g, STR_SYSTEM_LOSE, x + (w >> 1), y + IGM_LOOT_OFF_Y, ALIGN_HCENTER_TOP, COLOR_BLACK, COLOR_BLACK);
        DrawString(g, STR_SYSTEM_ALL_DIE, x + (w >> 1), y + IGM_LOOT_OFF_Y + FONT_HEIGHT, ALIGN_HCENTER_TOP, COLOR_BLACK, COLOR_BLACK);
    }

    static void RenderLootItem(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        int dataID = wnd.m_listStartPos * WND_LOOT_ITEM_DATA_PER + WND_LOOT_ITEM_DATA_BASE;
        int offY = 0;

        UtilDrawMenuBox(g, COLOR_LIGHT_GREEN, x + IGM_LOOT_ITEM_RECT_X, y + IGM_LOOT_ITEM_RECT_Y, IGM_LOOT_ITEM_RECT_W, IGM_LOOT_ITEM_RECT_H,
                        MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1);
        for (int i = 0; i < wnd.m_showNum; i++)
        {
            if (dataID >= wnd.m_dataCount)
            {
                break;
            }
            DrawString(g, wnd.m_data[dataID], x + IGM_LOOT_ITEM_NAME_X, y + IGM_LOOT_ITEM_NAME_Y + offY, ALIGN_LEFT_TOP, COLOR_BLACK, COLOR_BLACK);
            RenderIcon(g, wnd.m_data[dataID + 1], x + IGM_LOOT_ITEM_ICON_X, y + IGM_LOOT_ITEM_ICON_Y + offY);
            UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, wnd.m_data[dataID + 2], x + IGM_LOOT_ITEM_NUM_X, y + IGM_LOOT_ITEM_NUM_Y + offY, ALIGN_RIGHT_TOP);
            dataID += WND_LOOT_ITEM_DATA_PER;
            offY += IGM_LOOT_ITEM_OFF_Y;
        }
        s_actorUI.DrawFrame(g, x + (w >> 1), y, FRAME_UI_TITLE_GETITEM);
        s_actorUI.DrawFrame(g, x + (w >> 1), y, FRAME_UI_IGM_TITLE_DECO);
        s_actorUI.DrawModule(g, MODULE_UI_ICON_GOLD, x + IGM_MONEY_X, y + h - IGM_INFO_Y);
        UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, wnd.m_data[0], x + IGM_MONEY_X + IGM_INFO_NUM_X, y + h - IGM_INFO_Y + IGM_INFO_NUM_Y, ALIGN_LEFT_TOP);
        s_actorUI.DrawModule(g, MODULE_UI_ICON_TOKEN, x + IGM_TOKEN_X, y + h - IGM_INFO_Y);
        UtilRenderUINumber(g, MODULE_NUMBERS_0_A, false, wnd.m_data[1], x + IGM_TOKEN_X + IGM_INFO_NUM_X, y + h - IGM_INFO_Y + IGM_INFO_NUM_Y, ALIGN_LEFT_TOP);
        RenderScrollBar(g, FRAME_UI_SCROLL_BAR_TOP, x + LOOT_SCROLL_BAR_X, y + LOOT_SCROLL_BAR_Y, LOOT_SCROLL_BAR_H, wnd.m_listStartPos,
                        wnd.m_count - wnd.m_showNum);
    }

    static void RenderShop(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        int foucsID = s_menuSlot[wnd.m_menuSlot];
        UtilDrawMenuBox(g, COLOR_WHITE, x + SHOP_BOX_X, y + SHOP_BOX_Y, w + SHOP_BOX_W, h + SHOP_BOX_H, MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1);
        //------商店标题----
//        s_actorUI.DrawFrame(g, x + (w >> 1), y + IGM_SHOP_TITLE_OFFY, FRAME_UI_TITLE_SHOP);
        //------买卖选择------
        RenderTabel(g, FRAME_UI_TABEL1, x + IGM_SHOP_TABEL_X, y + IGM_SHOP_TABEL_Y, IGM_SHOP_TABEL_OFFX, 0, wnd.m_count, foucsID);
        for (int i = 0; i < wnd.m_count; i++)
        {
//@#if JAR_LIMIT_300K
//@            DrawString(g,STR_SYSTEM_IGM_SHOP_BUY + i,x + IGM_SHOP_TABEL_X + (IGM_SHOP_NAME_SPACE >> 1) + i*IGM_SHOP_NAME_SPACE,IGM_SHOP_ICON_Y - IGM_SHOP_NAME_OFF_Y,ALIGN_HCENTER_TOP,COLOR_BLACK,COLOR_WHITE);
//@#else
            s_actorUI.DrawFrame(g, x + i * IGM_SHOP_NAME_SPACE + IGM_SHOP_NAME_OFF_X, IGM_SHOP_ICON_Y - IGM_SHOP_NAME_OFF_Y, FRAME_UI_SHOP_BUY + i);
//@#endif
        }
//@#if JAR_LIMIT_300K
//@        RenderIGMCommon(g, x, y, w, h, STR_SYSTEM_IGM_SHOP);
//@#else
        RenderIGMCommon(g, x, y, w, h, FRAME_UI_TITLE_SHOP);
//@#endif
//@#if AMIN_IGM_CURSOR
//@        s_actorUI.DrawAnimation(g, 0, 0);
//@#endif
    }

    static void UpdateShop(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        CWnd wndList = wnd.m_children[0];
        CWnd wndDesc = wnd.m_children[1];
        CWnd wndConfirm = wnd.m_children[2];
        CWnd wndMessage = wnd.m_children[3];
        int curItem = s_menuSlot[wnd.m_menuSlot];
        s_battleAnimFalg = BATTLE_ANIM_NONE;
        //换左右键切换浏览窗口
        if (wnd.m_state == WND_STATE_CYCEL || wnd.m_state == WND_STATE_PAGEUP || wnd.m_state == WND_STATE_PAGEDOWN)
        {
//@#if AMIN_IGM_CURSOR
//@            s_actorUI.SetAction(ACTION_UI_CURSOR);
//@            s_actorUI.SetPos(wnd.m_rect[RECT_X] + IGM_SHOP_CURSOR_X + s_menuSlot[wnd.m_menuSlot] * IGM_SHOP_CURSOR_OFFX, wnd.m_rect[RECT_Y] + IGM_SHOP_CURSOR_Y);
//@#endif
            switch (curItem)
            {
            case MI_IGM_SHOP_BUY:
                wndList.m_curSelect = s_entityList[ENTITY_LIST_BUY].m_next;
                wndList.m_showFirst = wndList.m_curSelect;
                wndList.m_listStartPos = 0;
                wndList.m_count = wndList.m_curSelect == null ? 0 : wndList.m_curSelect.GetCount();
                s_menuSlot[wndList.m_menuSlot] = 0;
                break;
            case MI_IGM_SHOP_SELL:
                 s_menuSlot[wndList.m_menuSlot] = 0;
                InitShopSell(wndList, wnd.m_data[SHOP_DATA_TYPE] == IGM_BATTLE_SHOP ? s_entityList[ENTITY_LIST_BATTLE].m_next : s_entityList[ENTITY_LIST_ITEM].m_next);
                break;
            }
            return;
        }

        if (wnd.m_select >= 0)
        {
            if (wndList.m_count > 0)
            {
                wndList.DoModule();
                wndList.m_title = 1;
                wndDesc.m_data[IGM_DESC_SHOW_CHAR] = T_TRUE;
                wndDesc.m_data[IGM_DESC_DATA_TYPE] = GetShopType(wndList.m_curSelect);
                wndDesc.m_data[IGM_DESC_DATA_ID] = wndList.m_curSelect.m_id;
                int strID = GetShopDesc(wndList.m_curSelect);
                wndDesc.ResetTextPos(CalcTextPos(GetString(strID + wndList.m_curSelect.m_id), wndDesc.m_rect[RECT_W], SCROLL_DIR_UP));
            }
            return;
        }
        //购买
        if (wndList.m_select >= 0)
        {
            wndList.m_select = -1;
            wndConfirm.DoModule();
            s_menuSlot[wndConfirm.m_menuSlot] = 0;
            switch (curItem)
            {
            case MI_IGM_SHOP_BUY:
                wndConfirm.m_count = SHOP_DEAL_NUM_LIMIT;
                wndConfirm.m_title = STR_SYSTEM_SELECT_NUM_BUY;
                s_costMoneyTotal = GetPrice(MI_IGM_SHOP_BUY, wndList.m_curSelect.m_type, wndList.m_curSelect.m_id) * s_gameData[DEFINE_GAME_DATA_DISCOUNT]
                                   / 100;
                break;
            case MI_IGM_SHOP_SELL:
                wndConfirm.m_count = wndList.m_curSelect.m_count;
                wndConfirm.m_title = STR_SYSTEM_SELECT_NUM_SELL;
                s_costMoneyTotal = GetPrice(MI_IGM_SHOP_SELL, wndList.m_curSelect.m_type, wndList.m_curSelect.m_id);
                break;
            }
            return;
        }

        if (wndConfirm.m_state == WND_STATE_YES)
        {
            wndList.m_title = 0;
            wndConfirm.m_state = WND_STATE_WAIT;
            CEntity entity = wndList.m_curSelect;
            switch (curItem)
            {
            case MI_IGM_SHOP_BUY:
                if (s_gameData[DEFINE_GAME_DATA_MONEY] >= s_costMoneyTotal)
                {
                    switch (wnd.m_data[SHOP_DATA_TYPE])
                    {
                    case IGM_NORMAL_SHOP:
                        PackageModify(entity.m_id, entity.m_type, s_menuSlot[wndConfirm.m_menuSlot] + 1);
                        break;
                    case IGM_BATTLE_SHOP:
                        PackageBattle(entity.m_id,entity.m_type, s_menuSlot[wndConfirm.m_menuSlot] + 1, entity.m_icon);
                        break;
                    }
                    PackageModify(0, DEFINE_HOLD_TYPE_GOLD, -(s_costMoneyTotal*(s_menuSlot[wndConfirm.m_menuSlot] + 1)));
                    ResizeWndWithTitle(wndMessage, STR_SYSTEM_GAME_BUY_SUCCESS, 0);
                    wndMessage.DoModule(MESSAGE_SHOW_TIME);
                }
                else
                { //金钱不足
                    ResizeWndWithTitle(wndMessage, STR_SYSTEM_LESSMONEY, 0);
                    wndMessage.DoModule(MESSAGE_SHOW_TIME);
                }
                break;
            case MI_IGM_SHOP_SELL:
                switch (wnd.m_data[SHOP_DATA_TYPE])
                {
                case IGM_BATTLE_SHOP:
                    PackageBattle(entity.m_id, entity.m_type, -(s_menuSlot[wndConfirm.m_menuSlot] + 1), entity.m_icon);
                    InitShopSell(wndList, s_entityList[ENTITY_LIST_BATTLE].m_next);
                    break;
                case IGM_NORMAL_SHOP:
                    PackageModify(entity.m_id, entity.m_type, -(s_menuSlot[wndConfirm.m_menuSlot] + 1));
                    InitShopSell(wndList, s_entityList[ENTITY_LIST_ITEM].m_next);
                     s_menuSlot[wndList.m_menuSlot] = 0;
                    break;
                }
                PackageModify(0, DEFINE_HOLD_TYPE_GOLD, s_costMoneyTotal * (s_menuSlot[wndConfirm.m_menuSlot] + 1));
                if (null != s_entityList[ENTITY_LIST_SELL].m_next)
                {
                    CEntity entityGoods = wndList.m_curSelect;
                    wndList.DoModule();
                    wndList.m_title = T_TRUE;
                    wndDesc.m_data[IGM_DESC_DATA_TYPE] = GetShopType(entityGoods);
                    wndDesc.m_data[IGM_DESC_DATA_ID] = entityGoods.m_id;
                }
                else
                {
                    wndDesc.m_data[IGM_DESC_SHOW_CHAR] = T_FALSE;
                }
                break;
            }
            return;
        }
//@#if SMS
//@#if TB_SMS
//@#else
//@        if (wndMessage.m_state == WND_STATE_RETURN && wndMessage.m_title == STR_SYSTEM_LESSMONEY
//@#if TS_SMS
//@#else
//@            && s_variables[SCRIPT_VARIABLE_SMS_PARA0 + ENUM_SMS_PURPOSE0_ACTIVE] > 0
//@#endif
//@                )
//@        {
//@            wndMessage.m_state = WND_STATE_WAIT;
//@            ReleaseTempEntity();
//@            CEntity entity = CEntity.GetInstance();
//@#if TS_SMS
//@            entity.m_id = 4;
//@#else
//@            entity.m_id = 2;
//@#endif
//@            entity.m_count = 1;
//@            s_tempList.InsertAfter(entity);
//@            s_wnd = CWnd.GetInstance();
//@            s_wnd.Init(WND_SMS_MESSAGE, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, true);
//@            s_wnd.m_isTop = true;
//@            s_wnd.m_curSelect = entity;
//@            s_wnd.DoModule();
//@            return;
//@        }
//@#endif
//@#endif
        if (wndList.m_state == WND_STATE_PAGEUP || wndList.m_state == WND_STATE_PAGEDOWN)
        {
            CEntity entity = wndList.m_curSelect;
            wndList.DoModule();
            wndDesc.m_data[IGM_DESC_DATA_TYPE] = GetShopType(entity);
            wndDesc.m_data[IGM_DESC_DATA_ID] = entity.m_id;
            int strID = GetShopDesc(entity);
            wndDesc.ResetTextPos(CalcTextPos(GetString(strID + entity.m_id), wndDesc.m_rect[RECT_W], SCROLL_DIR_UP));
            return;
        }

        if (wndList.m_state == WND_STATE_RETURN)
        {
            wndList.m_title = 0;
            wndList.m_state = WND_STATE_WAIT;
            wndDesc.m_data[IGM_DESC_SHOW_CHAR] = T_FALSE;
            return;
        }
        if (wndConfirm.m_state == WND_STATE_RETURN)
        {
//            s_menuSlot[wndList.m_menuSlot] = 0;
            wndList.m_title = 0;
            wndList.m_state = WND_STATE_WAIT;
            wndDesc.m_data[IGM_DESC_SHOW_CHAR] = T_FALSE;
            wndConfirm.m_state = WND_STATE_WAIT;
            return;
        }

        if (wnd.m_state == WND_STATE_RETURN)
        {
            s_menuSlot[wndList.m_menuSlot] = 0;
            wnd.Close();
            UpdateIGMShopExit();
        }
    }

    static int GetShopType(CEntity entity)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int type = 0;
        switch (entity.m_type)
        {
        case DEFINE_HOLD_TYPE_ITEM:
        case DEFINE_HOLD_TYPE_BATTLE:
            type = ENTITY_LIST_ITEM;
            break;
        case DEFINE_HOLD_TYPE_EQUIP:
            type = ENTITY_LIST_EQUIP;
            break;
        }
        return type;
    }

    static int GetShopDesc(CEntity entity)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int strID = 0;
        switch (entity.m_type)
        {
        case DEFINE_HOLD_TYPE_ITEM:
        case DEFINE_HOLD_TYPE_BATTLE:
            strID = STR_SYSTEM_ITEMS_DESC_0;
            break;
        case DEFINE_HOLD_TYPE_EQUIP:
            strID = STR_SYSTEM_EQUIPS_DESC_0;
            break;
        }
        return strID;
    }

    static void InitShopSell(CWnd sellList, CEntity entityItem)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        s_entityList[ENTITY_LIST_SELL].ReleaseAll();
        s_entityList[ENTITY_LIST_SELL].m_next = null;
        sellList.m_count = 0;
        CEntity entitySell = s_entityList[ENTITY_LIST_SELL];
        CEntity entityEquip = s_entityList[ENTITY_LIST_EQUIP].m_next;
        CEntity entityTemp = null;
		//第一个物品不让卖  就不会获取物品
        while (null != entityEquip)
        {
            if (s_dbEquips[entityEquip.m_id][DB_EQUIPS_CANSELL] == ENUM_EQUIPS_CANSELL_YES)
            {
                sellList.m_count++;
                entityTemp = CEntity.GetInstance();
                entityTemp.Clone(entityEquip);
                entityTemp.m_type = DEFINE_HOLD_TYPE_EQUIP;
                entitySell.InsertAfter(entityTemp);
                entitySell = entitySell.m_next;
            }
            entityEquip = entityEquip.m_next;
        }
        while (null != entityItem)
        {
            if (s_dbItems[entityItem.m_id][DB_ITEMS_CANSELL] == ENUM_ITEMS_CANSELL_YES)
            {
                sellList.m_count++;
                entityTemp = CEntity.GetInstance();
                entityTemp.Clone(entityItem);
                entityTemp.m_type = DEFINE_HOLD_TYPE_ITEM;
                entitySell.InsertAfter(entityTemp);
                entitySell = entitySell.m_next;
            }
            entityItem = entityItem.m_next;
        }
        sellList.m_curSelect = s_entityList[ENTITY_LIST_SELL].m_next;
        sellList.m_showFirst = sellList.m_curSelect;
    }

    static void UpdateIGMShopExit()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        s_entityList[ENTITY_LIST_BUY].ReleaseAll();
        s_entityList[ENTITY_LIST_BUY].m_next = null;
        s_entityList[ENTITY_LIST_SELL].ReleaseAll();
        s_entityList[ENTITY_LIST_SELL].m_next = null;
    }

//end IGM_DEFINE_SHOP_BUY_SELL

    static void RenderIGMShopConfirm(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        DrawString(g, wnd.m_title, x + IGM_SHOP_CONFIRM_OFF_X, y + IGM_SHOP_CONFIRM_BUY_OFF_Y, SOFT_SELECT_ALIGN, COLOR_BLACK, COLOR_BLACK);
        DrawString(g, STR_SYSTEM_COST_MONEY, x + IGM_SHOP_CONFIRM_OFF_X, y + IGM_SHOP_CONFIRM_COUNT_OFF_Y, SOFT_SELECT_ALIGN, COLOR_BLACK, COLOR_BLACK);
        DrawSystemString(g, (s_menuSlot[wnd.m_menuSlot] + 1) + "", x + IGM_SHOP_CONFIRM_NUM_OFF_X, y + IGM_SHOP_CONFIRM_BUY_OFF_Y, SOFT_SELECT_ALIGN,
                         COLOR_BLACK, COLOR_BLACK);
        DrawSystemString(g, (s_costMoneyTotal * (s_menuSlot[wnd.m_menuSlot] + 1)) + "", x + IGM_SHOP_CONFIRM_NUM_OFF_X, y + IGM_SHOP_CONFIRM_COUNT_OFF_Y,
                         SOFT_SELECT_ALIGN, COLOR_BLACK, COLOR_BLACK);
    }

    static void UpdateShopConfirm(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        if (wnd.m_select >= 0)
        {
            wnd.m_state = WND_STATE_YES;
            wnd.Close();
            return;
        }
        if (wnd.m_state == WND_STATE_RETURN)
        {
            wnd.m_state = WND_STATE_RETURN;
            wnd.Close();
        }
    }

    static void RenderWndAnimation(Graphics g, CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        s_actorUI.DrawAnimation(g, 0, 0);
    }

    static void RenderWndUiAnimation(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        s_actorAchievement.DrawAnimation(g, 0, 0);
    }
    static int GetIGMPage(int menuItem)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_IGM.h
        int res = -1;
        switch (menuItem)
        {
        case MI_IGM_STATUS:
            res = WND_STATUS;
            break;
        case MI_IGM_EQUIP:
            res = WND_EQUIP;
            break;
        case MI_IGM_ITEM:
            res = WND_PACKAGE_ITEM;
            break;
        case MI_IGM_ADVANCE:
            res = WND_PACKAGE_EQUIP;
            break;
        case MI_IGM_SKILL:
            res = WND_SKILL;
            break;
        case MI_IGM_MIXTRUE:
            res = WND_MIXTRUE;
            break;
        case MI_IGM_SYSTEM:
            res = WND_MISSION;
            break;
        }
        return res;
    }

//@@
//@@..\CGame\CGame_Icon.h
    static Image s_imgIcon = null;
    static CActor s_actorIcon = null;

    static void RenderIcon(Graphics g, int id, int x, int y)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Icon.h
        if (id >= 0)
        {
            s_actorIcon.DrawFrame(g, x, y, id);
        }
    }
//@@
//@@..\CGame\CGame_Mixtrue.h
    static void UpdateWndMixtrue(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Mixtrue.h
        CWnd wndList = wnd.m_children[0];
        CWnd wndDesc = wnd.m_children[1];
        CWnd wndInfo = wnd.m_children[2];
        CWnd wndMessage = wnd.m_children[3];

        if (wndInfo.m_state == WND_STATE_RETURN)
        {
            wndInfo.m_state = WND_STATE_WAIT;
            ReleaseTempEntity();
            GetEntityIndex(s_menuSlot[wnd.m_menuSlot], wndList, s_entityList[ENTITY_LIST_MIXTRUE]);
            return;
        }
        //切换合成药方
        if (wndList.m_state == WND_STATE_PAGEUP || wndList.m_state == WND_STATE_PAGEDOWN)
        {
            wndDesc.m_data[IGM_DESC_DATA_ID] = wndList.m_curSelect.m_id;
            wndDesc.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_MIXTRUE_DESC_0 + wndDesc.m_title), EFFORT_DESC_RECT_W, SCROLL_DIR_UP));
            return;
        }
        //切换合成类型
        if (wnd.m_state == WND_STATE_PAGEUP || wnd.m_state == WND_STATE_PAGEDOWN || wnd.m_state == WND_STATE_CYCEL)
        {
            ReleaseTempEntity();
            GetEntityIndex(s_menuSlot[wnd.m_menuSlot], wndList, s_entityList[ENTITY_LIST_MIXTRUE]);
            if (wndList.m_curSelect != null)
            {
                wndDesc.m_data[IGM_DESC_SHOW_CHAR] = T_TRUE;
                wndDesc.m_data[IGM_DESC_DATA_ID] = wndList.m_curSelect.m_id;
                wndDesc.ResetTextPos(CalcTextPos(GetString(STR_SYSTEM_MIXTRUE_DESC_0 + wndDesc.m_title), EFFORT_DESC_RECT_W, SCROLL_DIR_UP));
            }
            else
            {
                wndDesc.m_data[IGM_DESC_SHOW_CHAR] = T_FALSE;
            }
            return;
        }
        //开始合成
        if (wndList.m_select >= 0 && wndList.m_curSelect != null)
        {
            if (s_actorCommon[0].m_value[BV_PROFIC_LV] >= s_dbMixtrue[wndList.m_curSelect.m_id][DB_MIXTRUE_LEVEL])
            {
                wndInfo.DoModule();
                wndInfo.m_curSelect = wndList.m_curSelect;
                wndInfo.m_title = wndList.m_curSelect.m_id;
                CalcMixtrueInfo(wndInfo, wndList.m_curSelect.m_id);
            }
            else
            {
                ResizeWndWithTitle(wndMessage, STR_SYSTEM_LESS_PROFIC, 0);
                wndMessage.DoModule(MESSAGE_SHOW_TIME);
            }
            return;
        }
        //关闭窗口
        if (wnd.m_state == WND_STATE_RETURN)
        {
            ReleaseTempEntity();
            wnd.Close();
        }
    }

    static void RenderWndMixtrue(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Mixtrue.h
        //炼丹
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        int foucsID = s_menuSlot[wnd.m_menuSlot];
        int curX = x + MIXTRUE_ITEM_X;

        UtilDrawMenuBox(g, COLOR_LIGHT_GREEN, x + MIXTRUE_RECT_X, y + MIXTRUE_RECT_Y, MIXTRUE_RECT_W, MIXTRUE_RECT_H, MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1,
                        MODULE_UI_VERTI_1);
        RenderTabel(g, FRAME_UI_TABEL0, x + MIXTRUE_TABEL_X, y + MIXTRUE_TABEL_Y, MIXTRUE_TABEL_DX, 0, wnd.m_count, foucsID);
        for (int i = 0; i < wnd.m_count; i++)
        {
            s_actorUI.DrawFrame(g, curX, y + MIXTRUE_ITEM_Y, FRAME_UI_MIXTRUE_ITEM0 + i);
            curX += MIXTRUE_TABEL_DX;
        }
        RenderIGMCommon(g, x, y, w, h, FRAME_UI_TITLE_COOK);
    }

    static void RenderWndMixtrueList(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Mixtrue.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int curY = y + MIXTRUE_LIST_ICON_Y;
        int curItem = s_menuSlot[wnd.m_menuSlot];

        if (wnd.m_count > 0)
        {
            for (int i = 0; i < wnd.m_showNum; i++)
            {
                s_actorUI.DrawFrame(g, x + MIXTRUE_LIST_ICON_X, curY, FRAME_UI_ICON_BG);
                curY += MIXTRUE_LIST_OFFY;
            }
            RenderEntityList(g, wnd.m_showFirst, STR_SYSTEM_MIXTRUE_NAME_0, true, wnd.m_showNum, curItem - wnd.m_listStartPos, ENTITY_INFO_TYPE_NONE,
                             x + MIXTRUE_LIST_NAME_X, y + MIXTRUE_LIST_NAME_Y, 0, MIXTRUE_LIST_OFFY, ENTITY_ENABLE_TYPE_PROFIC, EFFORT_LIST_RECT_W, wnd.m_timer);
            s_actorUI.DrawFrame(g, x + MIXTRUE_LIST_CURSOR_X, y + MIXTRUE_LIST_CURSOR_Y + (curItem - wnd.m_listStartPos) * MIXTRUE_LIST_OFFY, FRAME_UI_CURSOR);
        }
        RenderScrollBar(g, FRAME_UI_SCROLL_BAR_TOP, x + MIXTRUE_SCROLL_BAR_X, y + MIXTRUE_SCROLL_BAR_Y, MIXTRUE_SCROLL_BAR_H, curItem, wnd.m_count);
    }

    static void UpdateWndMixtrueInfo(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Mixtrue.h
        CWnd wndMessage = wnd.m_children[0];
        CWnd wndAnim = wnd.m_children[1];
        int id = wnd.m_title;

        if (wndAnim.m_state == WND_STATE_RETURN)
        {
            wndAnim.m_state = WND_STATE_WAIT;
            int entityID, type, count, entityType;
            wndMessage.m_dataCount = 0;
            for (int i = DB_MIXTRUE_AWARD_0; i <= DB_MIXTRUE_AWARD_LAST; i += 2)
            {
                entityID = GetArticalID(s_dbMixtrue[id][i]);
                if (entityID >= 0)
                {
                    type = GetArticalType(s_dbMixtrue[id][i]);
                    count = s_dbMixtrue[id][i + 1];
                    PackageModify(entityID, type, count);
                    switch (type)
                    {
                    case DEFINE_HOLD_TYPE_ITEM:
                        wndMessage.AddData(STR_SYSTEM_ITEMS_NAME_0 + entityID);
                        break;
                    case DEFINE_HOLD_TYPE_EQUIP:
                        wndMessage.AddData(STR_SYSTEM_EQUIPS_NAME_0 + entityID);
                        break;
                    }
                    wndMessage.AddData(count);
                }
            }
            for (int i = DB_MIXTRUE_ENTITY_0; i <= DB_MIXTRUE_ENTITY_LAST; i += 2)
            {
                entityID = GetArticalID(s_dbMixtrue[id][i]);
                if (entityID < 0)
                {
                    continue;
                }
                entityType = GetArticalType(s_dbMixtrue[id][i]);
                count = s_dbMixtrue[id][i + 1];
                PackageModify(entityID, entityType, -count);
            }
            PackageModify(0, DEFINE_HOLD_TYPE_TOKEN, -s_dbMixtrue[id][DB_MIXTRUE_COST]);
            AddProficiency(0, s_dbMixtrue[id][DB_MIXTRUE_EXP]);
            CalcMixtrueInfo(wnd, id);
            wndMessage.m_title = STR_SYSTEM_MIXTRUE_SUCCESS;
            wndMessage.DoModule();
            return;
        }

        if (wnd.m_select >= 0)
        {
            if (CheckMixtrueCondition(wnd))
            {
                wndAnim.DoModule();
                s_actorUI.SetAction(ACTION_UI_MIXTRUE_START);
            }
            else
            {
                wndMessage.m_dataCount = 0;
                for (int i = 0; i < wnd.m_dataCount; i += WND_MIXTRUE_INFO_PER)
                {
                    if (wnd.m_data[i + 2] < wnd.m_data[i + 1])
                    {
                        wndMessage.AddData(wnd.m_data[i]);
                        wndMessage.AddData(wnd.m_data[i + 1] - wnd.m_data[i + 2]);
                    }
                }
                wndMessage.m_title = STR_SYSTEM_MIXTRUE_FAIL;
                wndMessage.DoModule();
            }
            return;
        }

        if (wnd.m_state == WND_STATE_RETURN)
        {
            wnd.Close();
        }
    }

    static void RenderWndMixtrueInfo(Graphics g, CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Mixtrue.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int w = wnd.m_rect[RECT_W];
        int h = wnd.m_rect[RECT_H];
        int itemY = y + MIXTRUE_INFO_ITEM_Y;
        int color;

        UtilDrawMenuBox(g, COLOR_LIGHT_GREEN, x + MIXTRUE_INFO_MESSAGE_X, y + MIXTRUE_INFO_MESSAGE_Y, MIXTRUE_INFO_MESSAGE_W, MIXTRUE_INFO_MESSAGE_H,
                        MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1);
        RenderIcon(g, wnd.m_curSelect.m_icon, x + MIXTRUE_INFO_ITEM_ICON_X, y + MIXTRUE_INFO_ITEM_ICON_Y);
        DrawString(g, STR_SYSTEM_MIXTRUE_NAME_0 + wnd.m_curSelect.m_id, x + MIXTRUE_INFO_ITEM_NAME_X, y + MIXTRUE_INFO_ITEM_NAME_Y, ALIGN_LEFT_TOP,
                   wnd.m_curSelect.m_color, wnd.m_curSelect.m_color);
        s_actorUI.DrawFrame(g, x + (w >> 1), y + MIXTRUE_INFO_TITLE_Y, FRAME_UI_MIXTRUE_STUFF);
        RenderIGMCommon(g, x, y, w, h, FRAME_UI_TITLE_COOK);
        for (int i = 0; i < wnd.m_dataCount; i += WND_MIXTRUE_INFO_PER)
        {
            color = wnd.m_data[i + 1] > wnd.m_data[i + 2] ? COLOR_RED : COLOR_GREEN;
            DrawString(g, wnd.m_data[i], x + MIXTRUE_INFO_STUFF_X, itemY, ALIGN_LEFT_TOP, COLOR_BLACK, COLOR_BLACK);
            DrawSystemString(g, "x" + wnd.m_data[i + 1], x + MIXTRUE_INFO_COUNT_X, itemY, ALIGN_LEFT_TOP, COLOR_BLACK, COLOR_BLACK);
            DrawSystemString(g, "(" + wnd.m_data[i + 2] + ")", x + MIXTRUE_INFO_NUM_X, itemY, ALIGN_RIGHT_TOP, color, color);
            itemY += FONT_HEIGHT;
        }
        s_actorUI.DrawFrame(g, x + (w >> 1), y + MIXTRUE_INFO_START_Y, FRAME_UI_MIXTRUE_START);
    }

    static void UpdateWndMixtrueMessage(CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Mixtrue.h
        CWnd wndSMS = wnd.m_children[0];

//@#if SMS
//@        if (wndSMS.m_state == WND_STATE_RETURN)
//@        {
//@            wndSMS.m_state = WND_STATE_WAIT;
//@            wnd.m_state = WND_STATE_YES;
//@            wnd.Close();
//@            return;
//@        }
//@#endif

        if (IsKeySinglePressed(GKEY_OK))
        {
            switch (wnd.m_title)
            {
            case STR_SYSTEM_MIXTRUE_SUCCESS:
                wnd.Close();
                break;
            case STR_SYSTEM_MIXTRUE_FAIL:
//@#if SMS
//@#if SMS_MIXTRUE_FALL
//@                if (wnd.m_dataCount <= 2)
//@                {
//@                    s_smsState = SMS_NONE;
//@                    wndSMS.DoModule();
//@                    ReleaseTempEntity();
//@                    CEntity entity = CEntity.GetInstance();
//@                    entity.m_id = 3;
//@                    entity.m_count = 1;
//@                    s_tempList.InsertAfter(entity);
//@                    wndSMS.m_curSelect = entity;
//@                }
//@                else
//@#endif
//@#endif
                {
                    wnd.Close();
                }
                break;
            }
        }
    }

    static void RenderWndMixtrueMessage(Graphics g, CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Mixtrue.h
        int x = wnd.m_rect[RECT_X];
        int y = wnd.m_rect[RECT_Y];
        int contentY = y + MIXTRUE_MESSAGE_CONTENT_Y;

        DrawString(g, wnd.m_title, x + MIXTRUE_MESSAGE_TITLE_X, y + MIXTRUE_MESSAGE_TITLE_X, ALIGN_LEFT_TOP, COLOR_BLACK, COLOR_BLACK);
        for (int i = 0; i < wnd.m_dataCount; i += WND_MIXTRUE_MESSAGE_PER)
        {
            DrawString(g, wnd.m_data[i], x + MIXTRUE_MESSAGE_ITEM_X, contentY, ALIGN_LEFT_TOP, COLOR_BLACK, COLOR_BLACK);
            DrawSystemString(g, "x" + wnd.m_data[i + 1], x + MIXTRUE_MESSAGE_NUM_X, contentY, ALIGN_RIGHT_TOP, COLOR_BLACK, COLOR_BLACK);
            contentY += FONT_HEIGHT;
        }
    }

    static void CalcMixtrueInfo(CWnd wnd, int id)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Mixtrue.h
        int entityID, entityType, count;
        CEntity entity = null;
        wnd.m_dataCount = 0;

        wnd.AddData(STR_SYSTEM_GAME_DATA_TOKEN);
        wnd.AddData(s_dbMixtrue[id][DB_MIXTRUE_COST]);
        wnd.AddData(s_gameData[DEFINE_GAME_DATA_TOKEN]);

        for (int i = DB_MIXTRUE_ENTITY_0; i <= DB_MIXTRUE_ENTITY_LAST; i += 2)
        {
            entityID = GetArticalID(s_dbMixtrue[id][i]);
            if (entityID < 0)
            {
                continue;
            }
            entityType = GetArticalType(s_dbMixtrue[id][i]);
            count = s_dbMixtrue[id][i + 1];
            switch (entityType)
            {
            case DEFINE_HOLD_TYPE_ITEM:
                wnd.AddData(STR_SYSTEM_ITEMS_NAME_0 + entityID);
                entity = s_entityList[ENTITY_LIST_ITEM].SearchObject(entityID);
                break;
            case DEFINE_HOLD_TYPE_EQUIP:
                wnd.AddData(STR_SYSTEM_EQUIPS_NAME_0 + entityID);
                entity = s_entityList[ENTITY_LIST_EQUIP].SearchObject(entityID);
                break;
            }
            wnd.AddData(count);
            wnd.AddData(entity == null ? 0 : entity.m_count);
        }
    }

    static boolean CheckMixtrueCondition(CWnd wnd)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Mixtrue.h
        boolean res = true;
        for (int i = 0; i < wnd.m_dataCount; i += WND_MIXTRUE_INFO_PER)
        {
            if (wnd.m_data[i + 2] < wnd.m_data[i + 1])
            {
                res = false;
                break;
            }
        }
        return res;
    }

    static void AddProficiency(int actorID, int value)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Mixtrue.h
        CActor actor = s_actorCommon[actorID];
        int lv = actor.m_value[BV_PROFIC_LV];

        if (lv >= (DB_GAMEINFO_PROFIC_LAST - DB_GAMEINFO_PROFIC0 + 1))
        {
            return;
        }
        actor.m_value[BV_PROFIC_EXP] += value;
        actor.m_value[BV_PROFIC_EXP] = Math.min(actor.m_value[BV_PROFIC_EXP], s_dbGameInfo[actorID][DB_GAMEINFO_PROFIC_LAST]);
        if (actor.m_value[BV_PROFIC_EXP] >= s_dbGameInfo[actorID][DB_GAMEINFO_PROFIC0 + lv])
        {
            actor.m_value[BV_PROFIC_LV]++;
        }
    }
//@@
//@@..\CGame\CGame_Item.h
    static int[] s_effectValue = new int[AMOUNT_DB_EFFECT_SET];

    static int CalcUsedEffectCount()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Item.h
        int res = 0;
        for (int i = 0; i < AMOUNT_DB_EFFECT_SET; i++)
        {
            if (s_effectValue[i] > 0)
            {
                res++;
            }
        }
        return res;
    }


    /**
     * 将暂存于s_effectValue的效果应用到Actor上,这个函数执行前要先经由CalcItemUsed计算一次
     * 物品使用效果,并且两个函数所调用的actor要一致.
     *
     * @param actor CActor
     */
    static void ApplyEffect(CActor actor, int percent, int[] effectValue)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Item.h
        int maxHP = actor.m_value[BV_HPMAX];
        int maxMP = actor.m_value[BV_MPMAX];
        int curHP = actor.m_value[BV_HP];
        int curMP = actor.m_value[BV_MP];
        int modHP = 0;
        int modMP = 0;
        for (int i = 0; i < AMOUNT_BUFF_EFFECT; i++)
        {
            if (effectValue[i] > 0)
            {
                switch (i)
                {
                case DB_EFFECT_SET_ENUMERATE_HP:
                    modHP += (effectValue[i] * percent / 100);
                    break;
                case DB_EFFECT_SET_ENUMERATE_HPP:
                    modHP += (effectValue[i] * maxHP / 100 * percent / 100);
                    break;
                case DB_EFFECT_SET_ENUMERATE_MP:
                    modMP += (effectValue[i] * percent / 100);
                    break;
                case DB_EFFECT_SET_ENUMERATE_MPP:
                    modMP += (effectValue[i] * maxMP / 100 * percent / 100);
                    break;
                case DB_EFFECT_SET_ENUMERATE_RISE:
                    actor.m_value[BV_STATE] = BAS_NORMAL;
                    break;
                case DB_EFFECT_SET_ENUMERATE_DAMAGE:
                    actor.m_value[BV_DMG_VALUE] = effectValue[i] * percent / 100;
                    break;
                case DB_EFFECT_SET_ENUMERATE_CLEAR:
                    //清除buff
                    for (i = AMOUNT_BUFF_EFFECT + AMOUNT_BUFF_ICON; i < AMOUNT_DB_EFFECT_SET; i++)
                    {
                        actor.m_buff[i] = 0;
                    }
                    break;
                case DB_EFFECT_SET_ENUMERATE_ANIMATION:
                    actor.m_animationID = ANIM_FIRST_BA + effectValue[i] - 1;
                    actor.SetImage(s_imgs[ANIM_FIRST_BA + effectValue[i] - 1]);
                    actor.SetAction(actor.m_actionID);
                    break;
                case DB_EFFECT_SET_ENUMERATE_FRAME_INFO:
                    actor.m_value[BV_FRAME_INFO] = effectValue[i];
                    break;
                case DB_EFFECT_SET_ENUMERATE_EXTRA_HP:
                case DB_EFFECT_SET_ENUMERATE_EXTRA_MP:
                case DB_EFFECT_SET_ENUMERATE_EXTRA_NAP:
                case DB_EFFECT_SET_ENUMERATE_EXTRA_SAP:
                case DB_EFFECT_SET_ENUMERATE_EXTRA_DEFENSE:
                case DB_EFFECT_SET_ENUMERATE_EXTRA_CRITICAL:
                case DB_EFFECT_SET_ENUMERATE_EXTRA_DODGE:
                case DB_EFFECT_SET_ENUMERATE_EXTRA_SPEED:
                    actor.m_extraValue[i - DB_EFFECT_SET_ENUMERATE_EXTRA_SP] += effectValue[i];
                    RefreshActorBV(actor.m_value[BV_ID]);
                    break;
                }
            }
        }
        actor.m_value[BV_HP] = Math.max(0, Math.min(curHP + modHP, maxHP));
        actor.m_value[BV_MP] = Math.max(0, Math.min(curMP + modMP, maxMP));
    }

    static void ApplyBuff(CActor actor, CParallel parallel)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Item.h
        if (actor.m_value[BV_HP] > 0)
        {
            //更新持久状态给actor
            for (int i = AMOUNT_BUFF_EFFECT + AMOUNT_BUFF_ICON; i < AMOUNT_DB_EFFECT_SET; i++)
            {
                if (parallel.m_effectValue[i] > 0)
                {
                    if ((actor.m_value[BV_AVOID_BUFF] & (1 << (i - AMOUNT_BUFF_BASIC))) == 0)
                    {
                        actor.m_buff[i] = parallel.m_effectValue[i];
                    }
                }
                else if (parallel.m_effectValue[i] < 0)
                {
                    actor.m_buff[i] = 0;
                }
            }
        }
        else
        {
            //清除buff
            for (int i = AMOUNT_BUFF_EFFECT + AMOUNT_BUFF_ICON; i < AMOUNT_DB_EFFECT_SET; i++)
            {
                actor.m_buff[i] = 0;
            }
        }
    }

    static int s_avoidRecover = 0;

    static void CalcItemUsed(int itemID, CActor actor)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Item.h
        int effectID = 0;
        int effectValue = 0;
        s_avoidRecover = AVOID_RECOVER_NONE;

        for (int i = DB_ITEMS_PURPOSE0; i <= DB_ITEMS_PURPOSE1; i += 2)
        {
            effectID = s_dbItems[itemID][i]; //目的
            effectValue = s_dbItems[itemID][i + 1]; //数值
            s_effectValue[effectID] = effectValue;
        }
        //筛选
        //复活?
        if (s_effectValue[DB_EFFECT_SET_ENUMERATE_RISE] > 0)
        {
            if ((actor.m_value[BV_STATE] & BAS_DEAD) == 0)
            {
                //没死
                s_effectValue[DB_EFFECT_SET_ENUMERATE_RISE] = 0;
                s_avoidRecover |= AVOID_RECOVER_RELIFE;
            }
        }

        //回HP?
        if (s_effectValue[DB_EFFECT_SET_ENUMERATE_HP] > 0 || s_effectValue[DB_EFFECT_SET_ENUMERATE_HPP] > 0)
        {
            //回血量
            int dHp = s_effectValue[DB_EFFECT_SET_ENUMERATE_HP] + (s_effectValue[DB_EFFECT_SET_ENUMERATE_HPP] * actor.m_value[BV_HPMAX]) / 100;

            if ((dHp + actor.m_value[BV_HP]) > actor.m_value[BV_HPMAX])
            {
                //回血量 少于 最大血量
                s_effectValue[DB_EFFECT_SET_ENUMERATE_HP] = actor.m_value[BV_HPMAX] - actor.m_value[BV_HP];
                s_effectValue[DB_EFFECT_SET_ENUMERATE_HPP] = 0;
            }

            if ((actor.m_value[BV_HP] >= actor.m_value[BV_HPMAX]))
            {
                //没受伤
                s_effectValue[DB_EFFECT_SET_ENUMERATE_HP] = 0;
                s_effectValue[DB_EFFECT_SET_ENUMERATE_HPP] = 0;
                s_avoidRecover |= AVOID_RECOVER_HP;
            }
        }
        //回MP?
        if ((s_effectValue[DB_EFFECT_SET_ENUMERATE_MP] > 0 || s_effectValue[DB_EFFECT_SET_ENUMERATE_MPP] > 0))
        {
            //回魔量
            int dMp = s_effectValue[DB_EFFECT_SET_ENUMERATE_MP] + (s_effectValue[DB_EFFECT_SET_ENUMERATE_MPP] * actor.m_value[BV_MPMAX]) / 100;

            if ((dMp + actor.m_value[BV_MP]) > actor.m_value[BV_MPMAX])
            {
                //回魔量 少于 最大魔量
                s_effectValue[DB_EFFECT_SET_ENUMERATE_MP] = actor.m_value[BV_MPMAX] - actor.m_value[BV_MP];
                s_effectValue[DB_EFFECT_SET_ENUMERATE_MPP] = 0;
            }

            if (actor.m_value[BV_MP] >= actor.m_value[BV_MPMAX])
            {
                //没受伤
                s_effectValue[DB_EFFECT_SET_ENUMERATE_MP] = 0;
                s_effectValue[DB_EFFECT_SET_ENUMERATE_MPP] = 0;
                s_avoidRecover |= AVOID_RECOVER_MP;
            }
        }
    }

    static void ApplyEquip(int actorID, int slotID, int equipID, boolean isCreateEquip)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Item.h
        CActor actor = s_actorCommon[actorID];
        CEntity entity0 = GetEntity(ENTITY_LIST_EQUIP, equipID);
        if (equipID >= 0 && !isCreateEquip)
        { //判断有没有可用装备
            if (null == entity0)
            {
                return;
            }
            else
            {
                GetEquipSkill(actor, equipID, true);
                PackageModify(equipID, DEFINE_HOLD_TYPE_EQUIP, -1);
            }
        }
        //找到现有装备
        int oldEquip = actor.m_equips[slotID];
        actor.m_equips[slotID] = equipID;
        if (oldEquip >= 0 && !isCreateEquip)
        {
            GetEquipSkill(actor, oldEquip, false);
            PackageModify(oldEquip, DEFINE_HOLD_TYPE_EQUIP, 1);
        }
        // 计算装备是否满足套装(增加套装属性)
        CheckPortfolio(actor);
    }

    static void GetEquipSkill(CActor actor, int equipID, boolean isGet)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Item.h
        if (equipID >= 0)
        {
            for (int i = DB_EQUIPS_PURPOSE0; i <= DB_EQUIPS_PURPOSE_LAST; i += 2)
            {
                if (s_dbEquips[equipID][i] == ENUM_EQUIPS_PURPOSE0_SKILL)
                {
                    actor.SetSkill(s_dbEquips[equipID][i + 1], isGet ? 1 : 0);
                }
            }
        }
    }

    static void CheckPortfolio(CActor actor)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Item.h
        int equipNum = actor.m_equips.length - 1;
        int equipID, portfolio;
        int[] portID = new int[equipNum];
        int[] portNum = new int[equipNum];
        int portCount = 0;
        int i, j;

        for (i = 0; i < equipNum; i++)
        {
            equipID = actor.m_equips[i];
            if (equipID >= 0)
            {
                portfolio = s_dbEquips[equipID][DB_EQUIPS_PORTFOLIO];
                if (portfolio >= 0)
                {
                    for (j = 0; j < portCount; j++)
                    {
                        if (portfolio == portID[j])
                        {
                            portNum[j]++;
                        }
                        break;
                    }
                    if (j == portCount)
                    {
                        portID[portCount] = portfolio;
                        portNum[portCount]++;
                        portCount++;
                    }
                }
            }
        }
//        for (i = 0; i < portCount; i++)
//        {
//            if (portNum[i] >= s_dbEquips[portID[i]][DB_EQUIPS_COUNT])
//            {
//                actor.m_equips[ENUM_EQUIPS_TYPE_PORTFOLIO] = portID[i];
//                break;
//            }
//        }
    }

    static CEntity SearchArticalPrev(int type, int id)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Item.h
        CEntity pEntity = null;
        switch (type)
        {
        case DEFINE_HOLD_TYPE_ITEM:
            pEntity = s_entityList[ENTITY_LIST_ITEM];
            break;
        case DEFINE_HOLD_TYPE_EQUIP:
            pEntity = s_entityList[ENTITY_LIST_EQUIP];
            break;
        case DEFINE_HOLD_TYPE_BATTLE:
            pEntity = s_entityList[ENTITY_LIST_BATTLE];
            break;
        }
        return pEntity.SearchArticalPrev(id);
    }

    static CEntity GetEntity(int list, int id)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Item.h
        CEntity pEntity = s_entityList[list].SearchObject(id);
        return pEntity;
    }

//@@
//@@..\CGame\CGame_DB.h
    static final int[][] ReadDB(DataInputStream dis)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_DB.h
        int row = UtilReadNumber(dis, NUMBER_SHORT);
        int col = UtilReadNumber(dis, NUMBER_SHORT);
        int[][] outArray = new int[col][row];

        byte[] rowType = new byte[row];
        try
        {
            dis.read(rowType);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {
                switch (rowType[i])
                {
                case NUMBER_BYTE:
                    outArray[j][i] = UtilReadNumber(dis, NUMBER_BYTE);
                    break;
                case NUMBER_SHORT:
                    outArray[j][i] = UtilReadNumber(dis, NUMBER_SHORT);
                    break;
                case NUMBER_INT:
                    outArray[j][i] = UtilReadNumber(dis, NUMBER_INT);
                    break;
                }

            }
        }
        return outArray;
    }

    static final int[][] ReadDynamicDB(DataInputStream dis)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_DB.h
        int row = CGame.UtilReadNumber(dis, NUMBER_SHORT), col = 0;
        int[][] outArray = new int[row][];
        int i, j;

        for (i = 0; i < row; i++)
        {
            col = UtilReadNumber(dis, NUMBER_SHORT);
            outArray[i] = new int[col];
            for (j = 0; j < col; j++)
            {
                outArray[i][j] = UtilReadNumber(dis, NUMBER_SHORT);
            }
        }
        return outArray;
    }

    static void LoadDB()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_DB.h
        if (s_dbGameInfo != null)
        {
            return;
        }
//@#if USE_PAK_RESOURCES
        OpenTrunk("/db.pak");
        SkipTrunk(0);
//@#endif

//全局信息
//@#if USE_PAK_RESOURCES
        s_dbGameInfo = ReadDB(s_trunkDis);
//@#else
//@        s_trunkDis = new DataInputStream("".getClass().getResourceAsStream("/GameInfo.bin"));
//@        s_dbGameInfo = ReadDB(s_trunkDis);
//@        CloseTrunk();
//@#endif

//敌人
//@#if USE_PAK_RESOURCES
        s_dbEnemies = ReadDB(s_trunkDis);
//@#else
//@        s_trunkDis = new DataInputStream("".getClass().getResourceAsStream("/enemies.bin"));
//@        s_dbEnemies = ReadDB(s_trunkDis);
//@        CloseTrunk();
//@#endif


//英雄
//@#if USE_PAK_RESOURCES
        int[][] heroes = ReadDB(s_trunkDis);
//@#else
//@        s_trunkDis = new DataInputStream("".getClass().getResourceAsStream("/heroes.bin"));
//@        int[][] heroes = ReadDB(s_trunkDis);
//@        CloseTrunk();
//@#endif

        s_heroes = new int[TEAM_SIZE][][];
        for (int i = 0; i < TEAM_SIZE; i++)
        {
            s_heroes[i] = new int[s_dbGameInfo[i][DB_GAMEINFO_LEVEL]][];
        }
        for (int i = 0, r1 = 0, r2 = 0; i < heroes.length; i++)
        {
            s_heroes[r1][r2] = heroes[i];
            heroes[i] = null;
            r2++;
            if (r2 == s_heroes[r1].length)
            {
                r1++;
                r2 = 0;
            }
        }
        heroes = null;

//物品
//@#if USE_PAK_RESOURCES
        s_dbItems = ReadDB(s_trunkDis);
//@#else
//@        s_trunkDis = new DataInputStream("".getClass().getResourceAsStream("/items.bin"));
//@        s_dbItems = ReadDB(s_trunkDis);
//@        CloseTrunk();
//@#endif

//技能
//@#if USE_PAK_RESOURCES
        s_dbSkills = ReadDB(s_trunkDis);
//@#else
//@        s_trunkDis = new DataInputStream("".getClass().getResourceAsStream("/skills.bin"));
//@        s_dbSkills = ReadDB(s_trunkDis);
//@        CloseTrunk();
//@#endif

//敌人队伍
//@#if USE_PAK_RESOURCES
        s_dbEnemyTeams = ReadDB(s_trunkDis);
//@#else
//@        s_trunkDis = new DataInputStream("".getClass().getResourceAsStream("/enemyTeams.bin"));
//@        s_dbEnemyTeams = ReadDB(s_trunkDis);
//@        CloseTrunk();
//@#endif

//战斗特效BUFF
//@#if USE_PAK_RESOURCES
        s_dbEffectSet = ReadDB(s_trunkDis);
//@#else
//@        s_trunkDis = new DataInputStream("".getClass().getResourceAsStream("/EFFECT_SET.bin"));
//@        s_dbEffectSet = ReadDB(s_trunkDis);
//@        CloseTrunk();
//@#endif

//武器
//@#if USE_PAK_RESOURCES
        s_dbEquips = ReadDB(s_trunkDis);
//@#else
//@        s_trunkDis = new DataInputStream("".getClass().getResourceAsStream("/equips.bin"));
//@        s_dbEquips = ReadDB(s_trunkDis);
//@        CloseTrunk();
//@#endif

//任务
//@#if USE_PAK_RESOURCES
        s_dbTasks = ReadDB(s_trunkDis);
//@#else
//@        s_trunkDis = new DataInputStream("".getClass().getResourceAsStream("/tasks.bin"));
//@        s_dbTasks = ReadDB(s_trunkDis);
//@        CloseTrunk();
//@#endif

//帧信息
//@#if USE_PAK_RESOURCES
        s_dbFrameInfo = ReadDynamicDB(s_trunkDis);
//@#else
//@        s_trunkDis = new DataInputStream("".getClass().getResourceAsStream("/FrameInfo.bin"));
//@        s_dbShadow = ReadDynamicDB(s_trunkDis);
//@        CloseTrunk();
//@#endif

//合成
//@#if USE_PAK_RESOURCES
        s_dbMixtrue = ReadDB(s_trunkDis);
//@#else
//@        s_trunkDis = new DataInputStream("".getClass().getResourceAsStream("/mixtrue.bin"));
//@        s_dbShadow = ReadDynamicDB(s_trunkDis);
//@        CloseTrunk();
//@#endif

//随身商店
//@#if USE_PAK_RESOURCES
        s_dbShop = ReadDB(s_trunkDis);
//@#else
//@        s_trunkDis = new DataInputStream("".getClass().getResourceAsStream("/shop.bin"));
//@        s_dbShadow = ReadDynamicDB(s_trunkDis);
//@        CloseTrunk();
//@#endif

//成就
//@#if USE_PAK_RESOURCES
        s_dbEffort = ReadDB(s_trunkDis);
//@#else
//@        s_trunkDis = new DataInputStream("".getClass().getResourceAsStream("/effort.bin"));
//@        s_dbSMS = ReadDB(s_trunkDis);
//@        CloseTrunk();
//@#endif

//事件
//@#if USE_PAK_RESOURCES
        s_dbEvent = ReadDB(s_trunkDis);
//@#else
//@        s_trunkDis = new DataInputStream("".getClass().getResourceAsStream("/event.bin"));
//@        s_dbEvent = ReadDB(s_trunkDis);
//@        CloseTrunk();
//@#endif

//短代点
//@#if SMS
//@#if USE_PAK_RESOURCES
//@        s_dbSMS = ReadDB(s_trunkDis);
//@#else
//@        s_trunkDis = new DataInputStream("".getClass().getResourceAsStream("/SMS.bin"));
//@        s_dbSMS = ReadDB(s_trunkDis);
//@        CloseTrunk();
//@#endif
//@#endif
//@#if USE_PAK_RESOURCES
        CloseTrunk();
//@#endif
    }

    static int GetArticalType(int articalID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_DB.h
        int res = -1;
        if (articalID == 0xffff)
        {
            res = DEFINE_HOLD_TYPE_GOLD;
        }
        else if (articalID < AMOUNT_DB_ITEMS)
        {
            res = DEFINE_HOLD_TYPE_ITEM;
        }
        else if (articalID < AMOUNT_DB_ITEMS + AMOUNT_DB_EQUIPS)
        {
            res = DEFINE_HOLD_TYPE_EQUIP;
        }
        else if (articalID < AMOUNT_DB_ITEMS + AMOUNT_DB_EQUIPS + AMOUNT_DB_TASKS)
        {
            res = DEFINE_HOLD_TYPE_TASK;
        }
        else if (articalID < AMOUNT_DB_ITEMS + AMOUNT_DB_EQUIPS + AMOUNT_DB_TASKS + AMOUNT_DB_MIXTRUE)
        {
            res = DEFINE_HOLD_TYPE_MIXTRUE;
        }
        else if (articalID < AMOUNT_DB_ITEMS + AMOUNT_DB_EQUIPS + AMOUNT_DB_TASKS + AMOUNT_DB_MIXTRUE + AMOUNT_DB_SHOP)
        {
            res = DEFINE_HOLD_TYPE_SHOP;
        }
        else
        {
//@#if EXIST_EFFORT
//@            res = DEFINE_HOLD_TYPE_EFFORT;
//@#endif
        }
        return res;
    }

    static int GetArticalID(int articalID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_DB.h
        int res = -1;
        if (articalID == 0xffff)
        {
            res = 0;
        }
        else if (articalID < AMOUNT_DB_ITEMS)
        {
            res = articalID;
        }
        else if (articalID < AMOUNT_DB_ITEMS + AMOUNT_DB_EQUIPS)
        {
            res = articalID - AMOUNT_DB_ITEMS;
        }
        else if (articalID < AMOUNT_DB_ITEMS + AMOUNT_DB_EQUIPS + AMOUNT_DB_TASKS)
        {
            res = articalID - AMOUNT_DB_ITEMS - AMOUNT_DB_EQUIPS;
        }
        else if (articalID < AMOUNT_DB_ITEMS + AMOUNT_DB_EQUIPS + AMOUNT_DB_TASKS + AMOUNT_DB_MIXTRUE)
        {
            res = articalID - AMOUNT_DB_ITEMS - AMOUNT_DB_EQUIPS - AMOUNT_DB_TASKS;
        }
        else if (articalID < AMOUNT_DB_ITEMS + AMOUNT_DB_EQUIPS + AMOUNT_DB_TASKS + AMOUNT_DB_MIXTRUE + AMOUNT_DB_SHOP)
        {
            res = articalID - AMOUNT_DB_ITEMS - AMOUNT_DB_EQUIPS - AMOUNT_DB_TASKS - AMOUNT_DB_MIXTRUE;
        }
        else
        {
            res = articalID - AMOUNT_DB_ITEMS - AMOUNT_DB_EQUIPS - AMOUNT_DB_TASKS - AMOUNT_DB_MIXTRUE - AMOUNT_DB_SHOP;
        }
        return res;
    }

//@@
//@@..\CGame\CGame_Calendar.h
    /**
     * 得到当前时间日期
     */
//    static String GetCalendar()
//    {
//        Date localDate = new Date();
//        StringBuffer localStringBuffer = new StringBuffer();
//        Calendar localCalendar = Calendar.getInstance();
//        localCalendar.setTime(localDate);
//        localStringBuffer.append(localCalendar.get(Calendar.MONTH) + 1);
//        localStringBuffer.append("/");
//        if (localCalendar.get(Calendar.DAY_OF_MONTH) <= 9)
//        {
//            localStringBuffer.append("0");
//        }
//        localStringBuffer.append(localCalendar.get(Calendar.DAY_OF_MONTH));
//        localStringBuffer.append(" ");
//        if (localCalendar.get(Calendar.HOUR_OF_DAY) <= 9)
//        {
//            localStringBuffer.append("0");
//        }
//        localStringBuffer.append(localCalendar.get(Calendar.HOUR_OF_DAY));
//        localStringBuffer.append(":");
//        if (localCalendar.get(Calendar.MINUTE) <= 9)
//        {
//            localStringBuffer.append("0");
//        }
//        localStringBuffer.append(localCalendar.get(Calendar.MINUTE));
//        return localStringBuffer.toString();
//    }

//@@
//@@..\CGame\CGame_LoadGame.h
    static void LoadSceneActorRes()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadGame.h
        for (int i = 0; i < ANIM_PORTRAIT_0; i++)
        {
            if (s_resInUsed[i])
            {
//@#if IMG_RES
                if (null == s_imgs[i])
                {
                    try
                    {
                        s_imgs[i] = Image.createImage("/png" + i);
                    }
                    catch (IOException ex1)
                    {
                        ex1.printStackTrace();
                    }
                }
//@#else
//@                    if (null == s_pbgs[i])
//@                    {
//@                        s_pbgs[i] = new CPBG();
//@                        s_pbgs[i].load("/pbg" + i);
//@                        s_pbgs[i].EnablePrecalcImage(0, PBG_PRECALC_SCENE_ACTOR);
//@                    }
//@#endif
            }
        }
    }

    static void AttachSceneActorRes()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadGame.h
        int animationID;
        for (int i = 0; i < TEAM_SIZE; i++)
        {
            animationID = s_actorCommon[i].m_animationID;
//@#if IMG_RES
            if (animationID >= 0)
            {
                s_actorCommon[i].SetImage(s_imgs[s_actorCommon[i].m_animationID]);
            }
            s_actorCommon[i].m_actorEff.SetImage(s_imgs[ANIM_EMOTION]);
//@#else
//@                    s_actorCommon[i].SetPBG(s_pbgs[a]);
//@                    s_actorCommon[i].m_actorEff.SetPBG(s_pbgs[ANIM_EMOTION]);
//@#endif
        }

        for (int i = 0; i < s_actorCount; i++)
        {
//@#if IMG_RES
            s_actors[i].SetImage(s_imgs[s_actors[i].m_animationID]);
            s_actors[i].m_actorEff.SetImage(s_imgs[ANIM_EMOTION]);
//@#else
//@                    s_actors[i].SetPBG(s_pbgs[s_actors[i].m_animationID]);
//@                    s_actors[i].m_actorEff.SetPBG(s_pbgs[ANIM_EMOTION]);
//@#endif
        }
    }

    /**
     * 根据场景编号寻找整个地图的信息
     *  1、读取 mission.xml 打包生成的文件，确定需要载入的地图场景序号
     *  2、读取对应地图的地图图块，并创建地图
     *  3、读取场景信息，确定场景脚本内容
     */
    static void LoadScene()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadGame.h
        try
        {
            if (DEBUG_LOADINFO)
            {
                System.out.println("s_loadProgress " + s_loadProgress);
            }
            CActor actor;
            switch (s_loadProgress)
            {
            case 0:
                //停止音乐,释放原有scene资源
                if ((s_optionFlag & OPTION_FLAG_SOUND) != 0)
                {
                    SndStop();
                }
                if (DEBUG_MEM_CHECK)
                {
                    s_loadTotalTime = System.currentTimeMillis();
                }
                FreeScene();
                s_bScriptPause = false;
//@#if SHOW_DEBUG_INFO
                System.out.println("释放资源");
//@#endif
                break;
            case 1:
                FreeMap();
                FreeScript();
                FreeImage();
//@#if SHOW_DEBUG_INFO
                System.out.println("释放资源 ");
//@#endif
                break;
            case 2:
                FreeSceneString();
                FreeBattleActor();
                FreeHeroesRes();
                break;
            case 3:
                switch (s_sceneLoadType)
                {
                case SCENELOAD_TYPE_NEW:
                    // 在开始新游戏前清空跟随Actor
                    for (int i = 0; i < s_followToTeam.length; i++)
                    {
                        if (s_followToTeam[i] != null)
                        {
                            s_followToTeam[i] = null;
                        }
                    }
                    InitNewGame();
                    break;
                case SCENELOAD_TYPE_LOAD:
                    InitLoadGame();
                    break;
                }
//@#if SHOW_DEBUG_INFO
                System.out.println("初始化新游戏 ");
//@#endif
                break;
            case 4:
                CreateShadowRes();
                break;
            case 5:
                if ((s_loadData & LOAD_DATA_COMMON) != 0)
                {
                    /** @todo 出现的时间不合适 */
                    InitCharacterData();
                }
//@#if SHOW_DEBUG_INFO
                System.out.println("初始化主角 ");
//@#endif
                break;
            case 6:
                //切换场景后自动着陆
                s_mapActorCollide = null;
                s_landingPointX = -1;
                s_landingPointY = -1;
                if ((s_loadData & LOAD_DATA_COMMON) != 0)
                {
                    if (s_imgs[ANIM_BUFFLIGHT] == null)
                    {
                        s_imgs[ANIM_BUFFLIGHT] = Image.createImage("/png" + ANIM_BUFFLIGHT);
                    }
                }
                break;
            case 7:
                for (int i = 0; i < ANIM_PORTRAIT_0; i++)
                {
                    s_resInUsed[i] = false;
                }
                for (int i = 0; i < TEAM_SIZE; i++)
                {
                    s_actorCommon[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] = T_TRUE;
                }
                SetGlobeRes();
                break;
            case 8:
                // 打开场景文件
                LoadActors();
//@#if SHOW_DEBUG_INFO
                System.out.println("初始化场景actor ");
//@#endif
                break;
            case 9:
                if (s_mapID >= 0)
                {
                    LoadMap();
                }
                else
                {
                    s_layerCount = 0;
                }

//@#if SHOW_DEBUG_INFO
                System.out.println("载入地图");
//@#endif
                break;
            case 10:

                //读取tile图像文件
                LoadTile();

//@#if SHOW_DEBUG_INFO
                System.out.println("载入tile资源");
//@#endif
                break;
            case 11:
                LoadSceneActorRes();

//@#if SHOW_DEBUG_INFO
                System.out.println("载入图片资源");
//@#endif
                break;
            case 12:
                AttachSceneActorRes();
//@#if SMS
//@#switch (SHOW_SMS_ICON)
//@#{
//@#case "MODULE":
//@#break
//@#case "ANIM":
//@//                s_actorUI.SetAction(ACTION_UI_HINT);
//@//                s_actorUI.SetPos(SMS_X, SMS_Y);
//@//                s_actorUI.m_repeat = true;
//@#break
//@#}
//@#endif
                break;
            case 13:
                InitCamera();
                CreateMapBufferImage();
//@#if SHOW_DEBUG_INFO
                System.out.println("初始化镜头");
//@#endif
                break;
            case 14:
                int strID = s_sceneID + 1;
//字符串
//@#if USE_PAK_RESOURCES
                OpenTrunk("/string.pak");
                SkipTrunk(strID);
//@#else
//@                s_trunkDis = new DataInputStream("".getClass().getResourceAsStream("/StringBin" + strID+ ".bin"));
//@#endif
                LoadString(s_trunkDis);
                CloseTrunk();
//@#if SHOW_DEBUG_INFO
                System.out.println("载入场景文字");
//@#endif
                break;
            case 15:
                s_actorInBattle = new CActor[BATTLE_ACTOR_MAX];
                s_actorEffInBattle = new CActor[BATTLE_ACTOR_MAX];
                for (int i = 0; i < BATTLE_ACTOR_MAX; i++)
                {
                    s_actorInBattle[i] = new CActor(ANIM_FIRST_ENEMY, ANIM_FIRST_ENEMY, -1);
                    s_actorEffInBattle[i] = new CActor(ANIM_BUFFLIGHT, ANIM_BUFFLIGHT, -1, s_imgs[ANIM_BUFFLIGHT], false);
                    s_actorInBattle[i].m_entitySkill = CEntity.GetInstance();
                    s_actorInBattle[i].m_entitySkill.m_id = -1;
                    s_actorInBattle[i].m_entitySkill.m_count = -1;
                    s_actorInBattle[i].m_entitySkillEx = CEntity.GetInstance();
                    s_actorInBattle[i].m_entitySkillEx.m_id = -1;
                    s_actorInBattle[i].m_entitySkillEx.m_count = -1;
                    s_actorInBattle[i].m_buff = new int[AMOUNT_DB_EFFECT_SET];
                }
                s_actorEffFullScreen = new CActor(ANIM_BUFFLIGHT, ANIM_BUFFLIGHT, -1, s_imgs[ANIM_BUFFLIGHT], false);
//@#if SHOW_DEBUG_INFO
                System.out.println("初始化战斗actor");
//@#endif
                break;
            case 16:

                //初始化场景
                if (DEBUG_MEM_CHECK)
                {
                    long tempTime = System.currentTimeMillis();
                    s_loadTotalTime = tempTime - s_loadTotalTime;
                }
                InitScene();
                LoadScript();

                //执行初始化脚本
                if (s_scriptInit >= 0)
                {
                    s_scriptState[s_scriptInit] = SCRIPT_STATE_READY;
                    while (s_scriptState[s_scriptInit] != SCRIPT_STATE_END)
                    {
                        UpdateSingleScript(s_scriptInit);
                    }
                }

//@#if SHOW_DEBUG_INFO
                System.out.println("运行初始化脚本");
//@#endif
                break;
            case 17:
                actor = s_actorHumanControl == null ? s_actorCommon[0] : s_actorHumanControl;
                s_cameraX = 0;
                s_cameraY = 0;

                //如果是读取存档,恢复脚本和actor状态
                switch (s_sceneLoadType)
                {
                case SCENELOAD_TYPE_LOAD:
                    // 在读取存档前清空跟随Actor
                    for (int i = 0; i < s_followToTeam.length; i++)
                    {
                        if (s_followToTeam[i] != null)
                        {
                            s_followToTeam[i] = null;
                        }
                    }
                    RMS(false, s_menuSlot[MENU_SLOT_RMS]);
                    break;
                case SCENELOAD_TYPE_NORMAL:
                    actor.SetPos(s_preLoadData[PRELOAD_TILEX], s_preLoadData[PRELOAD_TILEY]);
                    actor.SetAction(s_preLoadData[PRELOAD_DIR]);
                    actor.m_actionIDOld = s_preLoadData[PRELOAD_DIR];
                    for (int i = 0; i < s_followToTeam.length; i++)
                    {
                        if(s_followToTeam[i] == null)
                        {
                            continue;
                        }
                        switch (s_preLoadData[PRELOAD_DIR])
                        {
                        case DIRECTION_UP:
                            s_followToTeam[i].SetPos(s_preLoadData[PRELOAD_TILEX], s_preLoadData[PRELOAD_TILEY] + i * 2);
                            break;
                        case DIRECTION_DOWN:
                            s_followToTeam[i].SetPos(s_preLoadData[PRELOAD_TILEX], s_preLoadData[PRELOAD_TILEY] - i * 2);
                            break;
                        case DIRECTION_LEFT:
                            s_followToTeam[i].SetPos(s_preLoadData[PRELOAD_TILEX] + i * 2, s_preLoadData[PRELOAD_TILEY]);
                            break;
                        case DIRECTION_RIGHT:
                            s_followToTeam[i].SetPos(s_preLoadData[PRELOAD_TILEX] - i * 2, s_preLoadData[PRELOAD_TILEY]);
                            break;
                        }
                        s_followToTeam[i].SetAction(s_preLoadData[PRELOAD_DIR]);
                        s_followToTeam[i].m_actionIDOld = s_preLoadData[PRELOAD_DIR];
                    }
                    break;
                }
                SetCamera(CAMERA_STATE_ACTOR, actor.m_curX, actor.m_curY, actor, 0);
                UpdateCamera();

                for (int i = 0; i < TEAM_SIZE; i++) //初始化脚本给角色初始朝向,如果切换地图时开启着轻功动画重新给一下,继续轻功状态
                {
                    if (s_actorCommon[i].m_properties[DEFINE_ACTOR_PROPERTY_PARA_TRANSPORT] == T_TRUE)
                    {
                        s_actorCommon[i].m_actionID = s_preLoadData[PRELOAD_DIR] + ACTION_TRANSPORT_UP;
                    }
                }
                break;
            case 18:
                int id;
                byte[] tbuf;
                OpenTrunk("/portrait.pak");
                for (int i = 0; i < s_trunkSize; i++)
                {
                    id = ANIM_PORTRAIT_0 + i;
                    if (s_resInUsed[id])
                    {
                        s_actorPortraits[i] = new CActor(id, id, -1);
                        tbuf = ReadTrunk(i);
                        s_imgs[id] = Image.createImage(tbuf, 0, tbuf.length);
                        s_actorPortraits[i].SetImage(s_imgs[id]);
                        tbuf = null;
                    }
                }
                CloseTrunk();
                break;
            case 19:
                SetSceneUIAction(s_actorUI, s_UIAction);
//@#if SMS
//@                SetSceneUIAction(s_actorSMS, s_SMSAction);
//@#endif
                break;
            case 20:
                RefreshTeam();
                s_sceneLoadType = SCENELOAD_TYPE_NORMAL;
                s_redrawMapBuffer = true;
                SetGameState(GS_SCENE);
                SetGameEP(EP_NORMAL);
                if ((s_optionFlag & OPTION_FLAG_SOUND) != 0)
                {
                    SndPlay(s_soundSlot[DEFINE_MUSIC_SLOT_SCENE]);
                }
                else
                {
                    SndStop();
                }
                break;
            }
            s_loadProgress++;
        }
        catch (Exception e)
        {
            if (DEBUG_PRINT_OUT)
            {
                e.printStackTrace();
            }
        }
    }

    private static void LoadTile()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadGame.h
        boolean hasCreate;

        if (s_mapID < 0)
        {
            return;
        }

        for (int i = 0; i < s_layerCount - 1; i++)
        {
//@#if USE_PAK_RESOURCES
            OpenTrunk("/tile.pak");
//@#endif
            hasCreate = false;
            for (int j = 0; j < i; j++)
            {
                if (s_tileFilesNum[i] == s_tileFilesNum[j])
                {
                    s_imgTile[i] = s_imgTile[j];
                    hasCreate = true;
                    break;
                }
            }
            if (!hasCreate && s_imgTile[i] == null)
            {
//@#if USE_PAK_RESOURCES
                SkipTrunk(s_tileFilesNum[i]);
                try
                {
                    s_imgTile[i] = Image.createImage(s_trunkDis);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
//@#else
//@                try
//@                {
//@                    s_imgTile[i] = Image.createImage("/MapTileRes" + s_tileFilesNum[i] + ".png");
//@                }
//@                catch (IOException e)
//@                {
//@                    e.printStackTrace();
//@                }
//@#endif
            }
//@#if USE_PAK_RESOURCES
            CloseTrunk();
//@#endif
        }
    }

    static void LoadActors()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadGame.h
        CActor actor;
//@#if USE_PAK_RESOURCES
        OpenTrunk("/scene.pak");
        SkipTrunk(s_sceneID);
//@#else
//@            s_trunkDis = new DataInputStream("".getClass().getResourceAsStream("/scene" + s_sceneID + ".bin"));
//@#endif
        s_dataInputStream = s_trunkDis;
        s_mapID = UtilReadNumber(s_dataInputStream, NUMBER_BYTE);
        s_actorCount = UtilReadNumber(s_dataInputStream, NUMBER_BYTE);
        s_actors = new CActor[s_actorCount];
        int actorClass = 0;
        int animID = 0;
        int shadow = -1;
        int actionID = 0;
        int posx = 0;
        int posy = 0;
        int acParameterCount = 0;
        for (int i = 0; i < s_actorCount; i++)
        {
            actorClass = UtilReadNumber(s_dataInputStream, NUMBER_BYTE);
            animID = UtilReadNumber(s_dataInputStream, NUMBER_BYTE);
            s_resInUsed[animID] = true;
            actionID = UtilReadNumber(s_dataInputStream, NUMBER_SHORT);
            shadow = UtilReadNumber(s_dataInputStream, NUMBER_SHORT);
            s_actors[i] = new CActor(actorClass, animID, actionID);
            actor = s_actors[i];
            actor.m_actorEff = new CActor(ANIM_EMOTION, ANIM_EMOTION, 0);
            actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHADOW] = (short) shadow;
            actor.m_properties[DEFINE_ACTOR_PROPERTY_PARA_FOLLOWTEAM] = (short)i;
            posx = UtilReadNumber(s_dataInputStream, NUMBER_SHORT);
            posy = UtilReadNumber(s_dataInputStream, NUMBER_SHORT);

            for (int j = 0; j < AMOUNT_RECT; j++)
            {
                actor.m_activatedBox[j] = (short) UtilReadNumber(s_dataInputStream, NUMBER_SHORT);
            }
            for (int j = 0; j < AMOUNT_RECT; j++)
            {
                actor.m_collideBox[j] = (short) UtilReadNumber(s_dataInputStream, NUMBER_SHORT);
            }

            acParameterCount = UtilReadNumber(s_dataInputStream, NUMBER_BYTE);
            actor.SetPos(posx, posy);
            if (0 != acParameterCount)
            {
                actor.m_parameters = new short[acParameterCount];
                for (int j = 0; j < acParameterCount; j++)
                {
                    actor.m_parameters[j] = (short) UtilReadNumber(s_dataInputStream, NUMBER_SHORT);
                }
            }
        }
        CloseTrunk();
        s_dataInputStream = null;
    }

    static void FreeImage()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadGame.h
        if (null != s_imgs)
        {
            for (int i = 0; i < s_imgs.length; ++i)
            {
                if ((null != s_imgs[i]) && (i != ANIM_BUFFLIGHT))
                {
                    s_imgs[i] = null;
                }
            }
        }
        System.gc();
    }

    static void FreeBattleActor()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadGame.h
        if (s_actorInBattle != null)
        {
            for (int i = 0; i < BATTLE_ACTOR_MAX; i++)
            {
                if (s_actorInBattle[i] != null)
                {
                    s_actorInBattle[i].Free();
                    s_actorInBattle[i] = null;
                }
                if (s_actorEffInBattle[i] != null)
                {
                    s_actorEffInBattle[i].Free();
                    s_actorEffInBattle[i] = null;
                }
            }
            s_actorInBattle = null;
            s_actorEffInBattle = null;
        }

        if (s_actorEffFullScreen != null)
        {
            s_actorEffFullScreen.Free();
            s_actorEffFullScreen = null;
        }

        System.gc();
    }

    static int s_randWord = 0;
    static void RenderLoading(Graphics g, int step, int max)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadGame.h
        UtilFillRect(g, COLOR_BLACK, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
//@#if LOADING_ANIM
        for (int i = 0; i < LOADING_ANIM_NUMBER; i++)
        {
            s_actorLoad[i].DrawAnimation(g, -120, -160);
            s_actorLoad[i].UpdateAnimation();
        }
        UtilFillRect(g, COLOR_RED, LOAD_PROG_BAR_X, LOAD_PROG_BAR_Y, LOAD_PROG_BAR_W * step / max, LOAD_PROG_BAR_H); //进度条
//@#else
//@        UtilFillRect(s_g, COLOR_BLUE, 0, LOAD_Y, SCREEN_WIDTH * step / max, LOAD_H);
//@#endif
        DrawSystemString(g, (step * 100 / max) + "%", SCREEN_WIDTH_HALF - LOAD_PROG_OFFX, LOAD_PROG_Y, ALIGN_HCENTER_BOTTOM, COLOR_WHITE, COLOR_WHITE);
    }

    static void RenderInit(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadGame.h
        UtilFillRect(g, COLOR_BLACK, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        DrawSystemString(g, "正在进入游戏...", SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF, ALIGN_HCENTER_BOTTOM, COLOR_WHITE, COLOR_WHITE);
    }

    private static void RenderLoadScene(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadGame.h
        RenderLoading(g, s_loadProgress, LOADING_TIME_SCENE);
        DrawLineString(g, s_randWord, SCREEN_WIDTH_HALF, LOADING_TIP_Y, ALIGN_HCENTER_TOP, COLOR_WHITE, COLOR_BLACK, LOADING_NUM_PERLINE, 0, 0);
    }

    private static void RenderLoadMenu(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_LoadGame.h
        RenderLoading(g, s_loadProgress, LOADING_TIME_MENU);
        DrawLineString(g, s_randWord, SCREEN_WIDTH_HALF, LOADING_TIP_Y, ALIGN_HCENTER_TOP, COLOR_WHITE, COLOR_BLACK, LOADING_NUM_PERLINE, 0, 0);
    }

//@@
//@@..\CGame\CGame_Sound.h
    static byte[] s_soundSlot = new byte[2];
    final static int SOUND_MENU = 0;
//@#if JAR_LIMIT_300K
//@    final static int SOUND_WIN = 1;
//@    final static int SOUND_LOSE = 2;
//@#else
    final static int SOUND_WIN = 6;
    final static int SOUND_LOSE = 7;
//@#endif
    static byte[][] s_sndSlot;
    static Player s_sndPlayer;
    static int s_curSnd;
    static int s_curSoundLoop;

    static void SndLoadAll()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Sound.h
//@#if EXIST_SOUND
        if (s_sndSlot == null)
        {
            OpenTrunk("/sound.pak");
            s_sndSlot = new byte[s_trunkSize][];
            for (int i = 0; i < s_trunkSize; i++)
            {
                s_sndSlot[i] = ReadTrunk(i);
            }
            CloseTrunk();
        }
//@#endif
    }

    static void SndUnload(int index)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Sound.h
//@#if EXIST_SOUND
        s_sndSlot[index] = null;
//@#endif
    }

    static void SndStop()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Sound.h
//@#if EXIST_SOUND
        try
        {
            s_sndPlayer.stop();
            s_sndPlayer.deallocate();
            s_sndPlayer.close();
            s_sndPlayer = null;
        }
        catch (Exception ex)
        {
        }
//@#endif
    }

    static void SndPlay(int index)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Sound.h
//@#if EXIST_SOUND
        SndPlay(index, -1);
        s_curSnd = index;
//@#endif
    }

    static void SndPlay(int index, int loop)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Sound.h
//@#if EXIST_SOUND
//        if (s_sndSlot !=null && index >= s_sndSlot.length)  //暂停出现空指针异常可能没添加音乐文件
        if (index >= s_sndSlot.length)
        {
            System.out.println("超出音乐文件数量");
            return;
        }
        if (loop < 0)
        {
            loop = 100;
        }
        try
        {
            if (s_sndPlayer != null)
            {
                if (s_sndPlayer.getState() == Player.STARTED)
                {
                    return;
                }
                else
                {
                    SndStop();
                }
            }
            ByteArrayInputStream bis = new ByteArrayInputStream(s_sndSlot[index]);
            s_sndPlayer = javax.microedition.media.Manager.createPlayer(bis, "audio/midi");
            s_sndPlayer.prefetch();
            s_sndPlayer.setLoopCount(loop);
            s_sndPlayer.start();
            s_curSnd = index;
            s_curSoundLoop = loop;
            bis.close();
            bis = null;
        }
        catch (Exception ex)
        {
        }
//@#endif
    }

//@@
//@@..\CGame\CGame_AnimationData.h
    /*
     * 以下为动画系统相应方法
     */
    static boolean LoadAnimationData(DataInputStream dis)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_AnimationData.h
        try
        {
//@#if AVOID_STREAM_AVAIL
//@#else
            if (dis.available() <= 0)
            {
                return false;
            }
//@#endif
            int id = UtilReadNumber(dis, NUMBER_UNSIGNED_BYTE);
            if (id == 0xFF)
            {
                return false;
            }
            int modulesNumber = 0, framesize = 4;
            modulesNumber = UtilReadNumber(dis, NUMBER_UNSIGNED_SHORT);

//@#if SUPERBIG_DATA
            CActor.s_moduleData[id] = UtilReadSizedShorts(dis, modulesNumber << 2);
//@#else
//@            CActor.s_moduleData[id] = UtilReadSizedBytes(dis, modulesNumber << 2);
//@#endif
            int framesNumber = UtilReadNumber(dis, NUMBER_UNSIGNED_BYTE);
            CActor.s_animsFrameSetOffset[id] = new short[framesNumber + 1];
            int size = 0;
            for (int i = 1; i <= framesNumber; i++)
            {
                size += UtilReadNumber(dis, NUMBER_UNSIGNED_BYTE) * framesize;
                CActor.s_animsFrameSetOffset[id][i] = (short) size;
            }
//@#if SUPERBIG_DATA
            CActor.s_animsFramesSet[id] = UtilReadSizedShorts(dis, size);
//@#else
//@            CActor.s_animsFramesSet[id] = UtilReadSizedBytes(dis, size);
//@#endif

            System.gc();

            int actionsNumber = UtilReadNumber(dis, NUMBER_UNSIGNED_BYTE);
            CActor.s_animsActionsFramesOffset[id] = new short[actionsNumber + 1];
            size = 0;
            for (int i = 1; i <= actionsNumber; i++)
            {
                size += UtilReadNumber(dis, NUMBER_UNSIGNED_BYTE);
                CActor.s_animsActionsFramesOffset[id][i] = (short) size;
            }
            CActor.s_animsActionsFrames[id] = UtilReadSizedBytes(dis, size);
            //add
            CActor.s_actionsDuration[id] = UtilReadSizedBytes(dis, size);
            CActor.s_animsActionsPos[id] = UtilReadSizedBytes(dis, size << 1);
//@#if ANIM_FLAG
//@            CActor.s_animsActionFlag[id] = ReadByteArrayWithSize(dis, size);
//@#endif
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }


//@@
//@@..\CGame\CGame_Splash.h
    /**
     * 渲染SPLASH画面
     *
     * @param g Graphics
     */
    static void RenderSplash(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Splash.h
//        if (!s_skipSplash)
//        {
////            s_actorSplash.DrawAnimation(g, 0, 0); // 异世邪君 白色光效
//        }
    }

    static boolean s_skipSplash = false;

    static void UpdateSplash()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Splash.h
//        if (s_actorSplash.m_end || IsKeySinglePressed(GKEY_SOFTRIGHT))
//        {
//            s_skipSplash = true;
//        }
//        if (s_skipSplash)
//        {
//@#switch(platform)
//@#{
//@#case "NOKIA_N5800":
//@        s_actorSplash.SetPos(SCREEN_WIDTH_HALF - 10, IGM_MIAN_MENU_STR_Y);
//@#break
//@#default:
//@#if JAR_LIMIT_300K
//@        s_actorSplash.SetPos(SCREEN_WIDTH_HALF, IGM_MIAN_MENU_STR_Y + 7);
//@#else
            s_actorSplash.SetPos(SCREEN_WIDTH_HALF, IGM_MIAN_MENU_STR_Y);
//@#endif
//@#}
            s_actorSplash.SetAction(ACTION_SPLASH_ANIM_EFFECT);
            s_actorSplash.m_repeat = true;
            SetGameState(GS_MAINMENU);
            s_wndMainMenu.DoModule();
//        }
//        else
//        {
//            s_actorSplash.UpdateAnimation();
//        }
    }

//@@
//@@..\CGame\CGame_Event.h
    static short s_eventCount = 0;

    static void UpdateEvent()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Event.h
        CEntity entity = s_tempList.m_next;
        int id = entity.m_id;
        int type = entity.m_type;
        int nextID = s_dbEvent[id][DB_EVENT_NEXT];
        int value0 = s_dbEvent[id][DB_EVENT_VALUE0];
        int value1 = s_dbEvent[id][DB_EVENT_VALUE1];

        switch (type)
        {
        case ENUM_EVENT_PURPOSE_DIALOG_L:
            InitDialog(STR_SYSTEM_EVENT_DESC_0 + id, DEFINE_ALIGN_LEFT, DEFINE_ALIGN_BOTTOM, value0, value1, -1);
            SetGameEP(EP_DIALOG);
            s_EPNext = EP_EVENT;
            break;
        case ENUM_EVENT_PURPOSE_DIALOG_R:
            InitDialog(STR_SYSTEM_EVENT_DESC_0 + id, DEFINE_ALIGN_RIGHT, DEFINE_ALIGN_BOTTOM, value0, value1, -1);
            SetGameEP(EP_DIALOG);
            s_EPNext = EP_EVENT;
            break;
        case ENUM_EVENT_PURPOSE_MOVIE_ON:
            s_effectRangeTarget = value0;
            SetCurtainEffect(EFFECT_TYPE_CURTAIN_CLOSE, RANDOM_EFFECT_TYPE_MOVIE + 1);
            break;
        case ENUM_EVENT_PURPOSE_MOVIE_OFF:
            SetCurtainEffect(EFFECT_TYPE_CURTAIN_OPEN, RANDOM_EFFECT_TYPE_MOVIE + 1);
            break;
        case ENUM_EVENT_PURPOSE_SHOCK_ON:
            s_cameraShock[CAMERA_SHOCK_MX] = (short) value0;
            s_cameraShock[CAMERA_SHOCK_MY] = (short) value1;
            s_cameraShock[CAMERA_SHOCK_VX] = s_cameraShock[CAMERA_SHOCK_MX];
            s_cameraShock[CAMERA_SHOCK_VY] = s_cameraShock[CAMERA_SHOCK_MY];
            s_cameraShockTimer = 10;
            break;
        case ENUM_EVENT_PURPOSE_SHOCK_OFF:
            s_cameraShockTimer = 0;
            break;
        case ENUM_EVENT_PURPOSE_VIBRATE:
//@#if EXIST_VIBRATE
//@            DeviceVibrate(value0);
//@#endif
            break;
        case ENUM_EVENT_PURPOSE_VARIABLE:
            s_variables[value0] = (short) value1;
            break;
        case ENUM_EVENT_PURPOSE_DELETE:
            CEntity entityEvent = s_entityList[ENTITY_LIST_EVENT].m_next;
            for (int i = 0; i < s_eventCount; i++)
            {
                entityEvent = entityEvent.m_next;
            }
            entityEvent.RemoveSelf();
            entityEvent.Release();
            s_eventCount--;
            break;
        }
        if (nextID >= 0)
        {
            entity.m_id = nextID;
            entity.m_type = (byte) s_dbEvent[entity.m_id][DB_EVENT_PURPOSE];
        }
        else
        {

            s_eventCount++;
            s_actorHumanControl = s_actorLastControl;
            SetGameEP(EP_NORMAL);
        }
    }

    static void GetCurrentEvent()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_Event.h
        CEntity entity0 = s_entityList[ENTITY_LIST_EVENT].m_next;

        if (entity0 == null)
        {
            return;
        }

        CEntity entity1 = CEntity.GetInstance();
        ReleaseTempEntity();
        for (int i = 0; i < s_eventCount; i++)
        {
            if (entity0.m_next != null)
            {
                entity0 = entity0.m_next;
            }
            else
            {
                s_eventCount = 0;
                entity0 = s_entityList[ENTITY_LIST_EVENT].m_next;
                break;
            }
        }
        entity1.Clone(entity0);
        s_tempList.InsertAfter(entity1);
        s_actorLastControl = s_actorHumanControl;
        s_actorHumanControl = null;
    }
//@@

//@#if RUN_SCRIPT_IN_IGM
//@@..\CGame\CGame_UIScript.h
    static int s_UIScriptCmdCur = 0;
    static int s_UIScriptNum = 0;
    static byte[] s_UIScriptType = new byte[UI_SCRIPT_SIZE];
    static int[] s_UIScriptData = new int[UI_SCRIPT_SIZE * UI_SCRIPT_PARA];
    static int[] s_UILimit = new int[AMOUNT_UI_LIMIT];
    static byte s_UIScriptState = UI_SCRIPT_STATE_RUN;
    static byte s_actorMenuUICount = 0;
    static int s_UIScriptWaitTime = 0;
    static CActor[] s_actorMenuUI = new CActor[BATTLE_UI_ACTOR_COUNT];
    static boolean s_isRunScript = true;

    static boolean UpdateUIScript()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_UIScript.h
        boolean isContinue = true;
        boolean res = true;
        int scriptOffset = 0;

        for (int i = 0; i < s_actorMenuUICount; i++)
        {
            s_actorMenuUI[i].UpdateAnimation();
        }

        switch (s_UIScriptState)
        {
        case UI_SCRIPT_STATE_RUN:
            while (isContinue)
            {
                if (s_UIScriptCmdCur >= s_UIScriptNum || !s_isRunScript)
                {
                    isContinue = false;
                }
                else
                {
                    scriptOffset = s_UIScriptCmdCur * UI_SCRIPT_PARA;
                    switch (s_UIScriptType[s_UIScriptCmdCur])
                    {
                    case SCHEMA_UI_DIALOG:
                        int content = s_UIScriptData[scriptOffset + CMD_UI_DIALOG_MESSAGE];
                        int posH = s_UIScriptData[scriptOffset + CMD_UI_DIALOG_PROTRAIT_POS_H];
                        int posV = s_UIScriptData[scriptOffset + CMD_UI_DIALOG_PROTRAIT_POS_V];
                        int head = s_UIScriptData[scriptOffset + CMD_UI_DIALOG_PROTRAIT_ID];
                        int brow = s_UIScriptData[scriptOffset + CMD_UI_DIALOG_EXPRESSION];
                        InitDialog(content, posH, posV, head, brow, -1);
                        s_UIScriptState = UI_SCRIPT_STATE_DIALOG;
                        s_uiType = UI_TYPE_DIALOG;
                        res = false;
                        isContinue = false;
                        break;
                    case SCHEMA_UI_LIMIT:
                        s_UILimit[UI_LIMIT_CUSOR] = (byte) s_UIScriptData[scriptOffset + CMD_UI_LIMIT_CURSOR];
                        PauseUIScript();
                        isContinue = false;
                        break;
                    case SCHEMA_UI_LIMIT_INFO:
                        s_UILimit[UI_LIMIT_PROTRAIT] = s_UIScriptData[scriptOffset + CMD_UI_LIMIT_INFO_PROTRAIT_ID];
                        s_UILimit[UI_LIMIT_POS_H] = s_UIScriptData[scriptOffset + CMD_UI_LIMIT_INFO_PROTRAIT_POS_H];
                        s_UILimit[UI_LIMIT_POS_V] = s_UIScriptData[scriptOffset + CMD_UI_LIMIT_INFO_PROTRAIT_POS_V];
                        s_UILimit[UI_LIMIT_MEESAGE] = s_UIScriptData[scriptOffset + CMD_UI_LIMIT_INFO_MESSAGE];
                        s_UILimit[UI_LIMIT_EXPRESSION] = s_UIScriptData[scriptOffset + CMD_UI_LIMIT_INFO_EXPRESSION];
                        isContinue = true;
                        break;
                    case SCHEMA_UI_ANIM:
                        CActor actor = GetActor(s_UIScriptData[scriptOffset + CMD_UI_ANIM_ACTOR]);
                        actor.SetAction(s_UIScriptData[scriptOffset + CMD_UI_ANIM_ACTION]);
                        actor.SetPos(s_UIScriptData[scriptOffset + CMD_UI_ANIM_X], s_UIScriptData[scriptOffset + CMD_UI_ANIM_Y]);
                        AddScriptUIAnim(actor);
                        isContinue = true;
                        break;
                    case SCHEMA_UI_WAIT:
                        s_UIScriptWaitTime = s_UIScriptData[scriptOffset + CMD_UI_WAIT_TIME];
                        s_UIScriptState = UI_SCRIPT_STATE_WAIT;
                        isContinue = false;
                        break;
                    }
                    s_UIScriptCmdCur++;
                }
            }
            break;
        case UI_SCRIPT_STATE_DIALOG:
            UpdateUI(s_uiType);
            if (s_uiResult != WND_STATE_WAIT)
            {
                s_UIScriptState = UI_SCRIPT_STATE_RUN;
                s_needRefreshBG = true;
            }
            res = false;
            break;
        case UI_SCRIPT_STATE_WAIT:
            if (s_UIScriptWaitTime > 0)
            {
                s_UIScriptWaitTime--;
            }
            else
            {
                s_UIScriptState = UI_SCRIPT_STATE_RUN;
            }
            res = true;
            break;
        }
        return res;
    }

    static void RenderUIScript(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_UIScript.h
        switch (s_UIScriptState)
        {
        case UI_SCRIPT_STATE_DIALOG:
            RenderUI(g, s_uiType);
            break;
        }

        for (int i = 0; i < s_actorMenuUICount; i++)
        {
            s_actorMenuUI[i].DrawAnimation(g, 0, 0);
        }
    }

    static void CheckUILimit()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_UIScript.h
        if (s_UILimit[UI_LIMIT_CUSOR] >= 0)
        {
            if (s_UILimit[UI_LIMIT_CUSOR] != s_menuConfirmID)
            {
                ShowUILimitInfo();
                s_menuConfirmID = -1;
            }
            else
            {
                s_UILimit[UI_LIMIT_CUSOR] = -1;
                StartUIScript();
            }
        }
    }

    static void ShowUILimitInfo()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_UIScript.h
        InitDialog(s_UILimit[UI_LIMIT_MEESAGE], s_UILimit[UI_LIMIT_POS_H], s_UILimit[UI_LIMIT_POS_V], s_UILimit[UI_LIMIT_PROTRAIT],
                   s_UILimit[UI_LIMIT_EXPRESSION], -1);
        s_UIScriptState = UI_SCRIPT_STATE_DIALOG;
        s_uiType = UI_TYPE_DIALOG;
    }

    static void AddScriptUIAnim(CActor actor)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_UIScript.h
        s_actorMenuUI[s_actorMenuUICount] = actor;
        s_actorMenuUICount++;
    }

    static void PauseUIScript()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_UIScript.h
        s_isRunScript = false;
    }

    static void StartUIScript()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_UIScript.h
        s_isRunScript = true;
    }

//@@
//@#endif

//@#if EXIST_EFFORT
//@#include "..\CGame\CGame_Effort.h"
//@#endif

//@#if SMS
//@#if TS_SMS
//@#include "..\CGame\CGame_TS_SMS.h"
//@#else
//@#include "..\CGame\CGame_SMS.h"
//@#endif
//@#if QQ_FORUM
//@#include "..\CGame\CGame_QQForum.h"
//@#endif
//@#endif

//@#if EXIST_VIBRATE
//@#include "..\CGame\CGame_Vibrate.h"
//@#endif

//@@..\CGame\CGame_StringCommon.h
    static int s_commonStringCount = 0;
    static int s_stringCount = 0;
    static int[] s_sentanceOffset = new int[1024 * 3];

    static void DrawSystemString(Graphics g, String str, int x, int y, int anchor, int color0, int color1)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_StringCommon.h
//@#if RENDER_WORD_EDGE
        if (color0 != color1)
        {
            g.setColor(color1);
            g.drawString(str, x + 1, y, anchor);
            g.drawString(str, x, y + 1, anchor);
            g.drawString(str, x - 1, y, anchor);
            g.drawString(str, x, y - 1, anchor);
        }
//@#endif
        g.setColor(color0);
        g.drawString(str, x, y, anchor);
    }

    static void DrawLineString(Graphics g, String str, int x, int y, int anchor, int color0, int color1, int num, int offSet, int line)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_StringCommon.h
        int textLength = str.length();
        line = line > 0 ? line : (textLength / num + 1);

        for (int i = 0; i < line; i++)
        {
            if (offSet >= textLength)
            {
                break;
            }
            if (offSet + num >= textLength)
            {
                num = textLength - offSet;
            }
//@#if RENDER_WORD_EDGE
            if (color0 != color1)
            {
                g.setColor(color1);
                g.drawSubstring(str, offSet, num, x - 1, y + i * FONT_HEIGHT, anchor);
                g.drawSubstring(str, offSet, num, x + 1, y + i * FONT_HEIGHT, anchor);
                g.drawSubstring(str, offSet, num, x, y + i * FONT_HEIGHT - 1, anchor);
                g.drawSubstring(str, offSet, num, x, y + i * FONT_HEIGHT + 1, anchor);
            }
//@#endif
            g.setColor(color0);
            g.drawSubstring(str, offSet, num, x, y + i * FONT_HEIGHT, anchor);
            offSet += num;
        }
    }

    static void DrawTextInRectByScroll(Graphics g, String str, int x, int y, int w, int h, int anchor, int color0, int color1, int scrollDir, int offset)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_StringCommon.h
        g.setClip(x, y, w, h);
        if ((anchor & Graphics.RIGHT) != 0)
        {
            x += w;
        }
        else if ((anchor & Graphics.HCENTER) != 0)
        {
            x += (w >> 1);
        }
        if ((anchor & Graphics.BOTTOM) != 0)
        {
            y += h;
        }
        else if ((anchor & Graphics.VCENTER) != 0)
        {
            y += (h >> 1);
        }
        switch (scrollDir)
        {
        case SCROLL_DIR_LEFT:
            DrawSystemString(g, str, x - offset, y, anchor, color0, color1);
            break;
        case SCROLL_DIR_UP:
            int num = w / FONT_WIDTH;
            DrawLineString(g, str, x, y - offset, anchor, color0, color1, num, 0, 0);
            break;
        }
        g.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    static void DrawTextInRectByPage(Graphics g, String str, int x, int y, int w, int h, int anchor, int color0, int color1, int page)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_StringCommon.h
        int num = w / FONT_WIDTH;
        int line = h / FONT_HEIGHT;
        int offset = num * line * page;
        g.setClip(x, y, w, h);
        if ((anchor & Graphics.RIGHT) != 0)
        {
            x += w;
        }
        else if ((anchor & Graphics.HCENTER) != 0)
        {
            x += (w >> 1);
        }
        if ((anchor & Graphics.BOTTOM) != 0)
        {
            y += h;
        }
        else if ((anchor & Graphics.VCENTER) != 0)
        {
            y += (h >> 1);
        }
        DrawLineString(g, str, x, y, anchor, color0, color1, num, offset, line);
        g.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    static void DrawPageStatus(Graphics g, int x, int y, int anchor, int color0, int color1, int curPage, int pageMax)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_StringCommon.h
        String str = "(" + (curPage + 1) + "/" + pageMax + ")";
        DrawSystemString(g, str, x, y, anchor, color0, color1);
    }

    static int CalcTextPos(String str, int w, int scrollDir)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_StringCommon.h
        int num = 0, line = 0, pos = 0;
        int length = str.length();
        switch (scrollDir)
        {
        case SCROLL_DIR_LEFT:
            pos = length * FONT_WIDTH;
            break;
        case SCROLL_DIR_UP:
            num = w / FONT_WIDTH;
            line = length / num + (length % num == 0 ? 0 : 1);
            pos = line * FONT_HEIGHT;
            break;
        }
        return pos;
    }

    static int CalcTextPage(String str, int w, int h)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_StringCommon.h
        int num = w / FONT_WIDTH;
        int line = h / FONT_HEIGHT;
        int length = str.length();
        int sum = num * line;
        int page = length / sum + (length % sum == 0 ? 0 : 1);
        return page;
    }

    static void UpdateWndTextPos(CWnd wnd, int limitPos, int time)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_StringCommon.h
        if (wnd.m_timeBuffer <= time)
        {
            wnd.m_timeBuffer++;
            return;
        }
        if (wnd.m_textPosMax > limitPos)
        {
            if (wnd.m_timer < wnd.m_textPosMax)
            {
                wnd.m_timer++;
            }
            else
            {
                wnd.m_timer = -limitPos;
            }
        }
    }

    static void UpdateWndTextPage(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_StringCommon.h
        if (IsKeySinglePressed(GKEY_OK))
        {
            wnd.m_timer++;
            wnd.m_timer %= wnd.m_textPosMax;
        }
    }

    static void FreeSceneString()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_StringCommon.h
        s_stringCount = s_commonStringCount;
    }

//@@

//@#switch (STRING_FORMAT)
//@#{
//@#case "STRING_FORMAT_CHARS":
//@#include "..\CGame\CGame_StringFormatChars.h"
//@#break
//@#case "STRING_FORMAT_UTF8":
//@#default:
//@@..\CGame\CGame_StringFormatUTF8.h
    static String s_strings[];

    static void LoadString(DataInputStream dis)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_StringFormatUTF8.h
        short sentenceLen = 0;
        try
        {
            int sentenceCount = dis.readShort();
            dis.read(s_trunkBuffer0, 0, sentenceCount * 2);
            for (int i = 0; i < sentenceCount; i++)
            {
                sentenceLen = UtilArrayToShort(s_trunkBuffer0, (i << 1));
                dis.read(s_trunkBuffer1, 0, sentenceLen);
                s_strings[s_stringCount] = new String(s_trunkBuffer1, 0, sentenceLen, "UTF-8");
                s_stringCount++;
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    static void InitStringSystem()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_StringFormatUTF8.h
        s_strings = new String[1024 * 10];
        s_commonStringCount = 0;
        s_stringCount = 0;
    }

    static int GetStringLength(int strID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_StringFormatUTF8.h
        return (GetStringWidth(strID) / FONT_WIDTH);
    }

    static String GetString(int id)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_StringFormatUTF8.h
        String res = "";
        if (id >= 0)
        {
            res = s_strings[id];
        }
        return res;
    }

    static void DrawString(Graphics g, int strID, int x, int y, int anchor, int color0, int color1)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_StringFormatUTF8.h
        String str = s_strings[strID];
        DrawSystemString(g, str, x, y, anchor, color0, color1);
    }

    static void DrawLineString(Graphics g, int strID, int x, int y, int anchor, int color0, int color1, int num, int offSet, int line)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_StringFormatUTF8.h
        String str = s_strings[strID];
        DrawLineString(g, str, x, y, anchor, color0, color1, num, offSet, line);
    }

    static void DrawTextInRectByScroll(Graphics g, int strID, int x, int y, int w, int h, int anchor, int color0, int color1, int scrollDir, int offset)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_StringFormatUTF8.h
        String str = s_strings[strID];
        DrawTextInRectByScroll(g, str, x, y, w, h, anchor, color0, color1, scrollDir, offset);
    }

    static void DrawTextInRectByPage(Graphics g, int strID, int x, int y, int w, int h, int anchor, int color0, int color1, int page)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_StringFormatUTF8.h
        String str = s_strings[strID];
        DrawTextInRectByPage(g, str, x, y, w, h, anchor, color0, color1, page);
    }

    /**
     * 得到一个字符串的宽度
     * @param font
     * @param strID
     * @return
     */
    static int GetStringWidth(int strID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CGame\CGame_StringFormatUTF8.h
        String str = s_strings[strID];
        char[] charArray = str.toCharArray();
        int len = 0;
        for (int i = 0; i < charArray.length; i++)
        {
            if (charArray[i] == UTIL_STRING_MARK)
            {
                i++;
            }
            else
            {
                len += FONT_SMALL.charWidth(charArray[i]);
            }
        }
        return len;
    }
//@@
//@#break
//@#}

//@@..\CGame\CGame_RenderBuffer.h

//@#if IMG_BUFFER
//@    /**缓冲图*/
//@    static Image s_imgBuffer = Image.createImage(SCREEN_WIDTH, SCREEN_HEIGHT);
//@    static int s_bufferRGBWidth = SCREEN_WIDTH;
//@    static int[] s_bufferRGB = new int[SCREEN_WIDTH * SCREEN_HEIGHT];
//@    static int s_translationEffectType = TRANSLATION_EFFECT_NONE;
//@    static void ClearTranslationEffectType()
//@    {
//@        s_translationEffectType = 0;
//@    }
//@
//@    /**
//@     * 设置模式,再次设置是取消某种设置
//@     * @param type
//@     */
//@    static void SetTranslationEffectType(int type)
//@    {
//@        s_translationEffectType ^= type;
//@    }
//@
//@    /**
//@     * 特效渲染函数
//@     */
//@    static void TranslationEffects()
//@    {
//@        //镜面
//@        if ((s_translationEffectType & TRANSLATION_EFFECT_MIRROR) != 0)
//@        {
//@            int tempRPG = 0;
//@            for (int i = 0; i < SCREEN_WIDTH_HALF; i++)
//@            {
//@                for (int j = 0; j < SCREEN_HEIGHT; j++)
//@                {
//@                    tempRPG = s_bufferRGB[j * s_bufferRGBWidth + i];
//@                    s_bufferRGB[j * s_bufferRGBWidth + i] = s_bufferRGB[j * s_bufferRGBWidth + s_bufferRGBWidth - i - 1];
//@                    s_bufferRGB[j * s_bufferRGBWidth + s_bufferRGBWidth - i - 1] = tempRPG;
//@                }
//@            }
//@        }
//@
//@        //临时的
//@        if ((s_translationEffectType & TRANSLATION_EFFECT_TEMP) != 0)
//@        {
//@            int colorR = 0;
//@            int colorG = 0;
//@            int colorB = 0;
//@
//@            for (int i = 0; i < s_bufferRGB.length; i++)
//@            {
//@                colorR = ((s_bufferRGB[i] & 0x00ff0000) >> 16) >> 1;
//@                colorG = ((s_bufferRGB[i] & 0x0000ff00) >> 8) >> 1;
//@                colorB = (s_bufferRGB[i] & 0x000000ff) >> 1;
//@                s_bufferRGB[i] = (colorR << 16) | (colorG << 8) | colorB | 0xff000000;
//@            }
//@        }
//@    }
//@
//@#endif
//@@
}

