import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Canvas;

interface Const
{

//@#if NOKIA_LIB
//@#include "../Const/Const_NokiaLib.h"
//@#endif

    //----地图----
    final int TILE_SIZE = 16;

    //---字体------
    final static int MI_PROGRAM_FONT = 0; //程序字
    final static int MI_ART_MODULE_FONT = 1; //美术MODULE字
    final static int MI_ART_FRAME_FONT = 2; //美术FRAME字

//@@../Const/Const_String.h
    static final Font FONT_SMALL = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
    static final int FONT_HEIGHT = FONT_SMALL.getHeight(); //一个字的高度
    static final int FONT_WIDTH = FONT_SMALL.stringWidth("宽"); //一个字的宽度

    static final byte SCROLL_DIR_LEFT = 0;
    static final byte SCROLL_DIR_UP = 1;

    static final int ALIGN_LEFT_TOP = Graphics.LEFT | Graphics.TOP;
    static final int ALIGN_LEFT_BOTTOM = Graphics.LEFT | Graphics.BOTTOM;
    static final int ALIGN_RIGHT_TOP = Graphics.RIGHT | Graphics.TOP;
    static final int ALIGN_RIGHT_BOTTOM = Graphics.RIGHT | Graphics.BOTTOM;
    static final int ALIGN_HCENTER_TOP = Graphics.HCENTER | Graphics.TOP;
    static final int ALIGN_HCENTER_BOTTOM = Graphics.HCENTER | Graphics.BOTTOM;
    static final int ALIGN_CENTER = Graphics.VCENTER | Graphics.HCENTER;

    static final char UTIL_STRING_MARK = '/';
//@@
//@@../Const/Const_Key.h
//----按键----
//@#switch (KEY_VALUE)
//@#{
//@#case "NOKIA":
//@#case "SONY_ERISSION":
//@#case "SAMSUNG":
//@#case "LG":
    final static int KEYVALUE_UP = 1;
    final static int KEYVALUE_DOWN = 2;
    final static int KEYVALUE_LEFT = 3;
    final static int KEYVALUE_RIGHT = 4;
    final static int KEYVALUE_MIDDLE = 5;
    final static int KEYVALUE_LEFTSOFT = 6;
    final static int KEYVALUE_RIGHTSOFT = 7;
//@#break
//@#case "MOTO_S":
//@    final static int KEYVALUE_UP = 1;
//@    final static int KEYVALUE_DOWN = 2;
//@    final static int KEYVALUE_LEFT = 3;
//@    final static int KEYVALUE_RIGHT = 4;
//@    final static int KEYVALUE_MIDDLE = 5;
//@    final static int KEYVALUE_LEFTSOFT = 21;
//@    final static int KEYVALUE_RIGHTSOFT = 22;
//@#break
//@#case "MOTO_B_C":
//@    final static int KEYVALUE_UP = 1;
//@    final static int KEYVALUE_DOWN = 6;
//@    final static int KEYVALUE_LEFT = 2;
//@    final static int KEYVALUE_RIGHT = 5;
//@    final static int KEYVALUE_MIDDLE = 20;
//@    final static int KEYVALUE_LEFTSOFT = 22;
//@    final static int KEYVALUE_RIGHTSOFT = 21;
//@#break
//@#case "MOTO_B":
//@	final static int KEYVALUE_UP = 1;
//@    final static int KEYVALUE_DOWN = 6;
//@    final static int KEYVALUE_LEFT = 2;
//@    final static int KEYVALUE_RIGHT = 5;
//@    final static int KEYVALUE_MIDDLE = 20;
//@    final static int KEYVALUE_LEFTSOFT = 21;
//@    final static int KEYVALUE_RIGHTSOFT = 22;
//@#break
//@#}

//@#switch (KEY_NUM_VALUE)
//@#{
//@#case "NOKIA_E62":
//@    final static int KEY_VALUE_NUM0 = 109;
//@    final static int KEY_VALUE_NUM1 = 114;
//@    final static int KEY_VALUE_NUM2 = 116;
//@    final static int KEY_VALUE_NUM3 = 121;
//@    final static int KEY_VALUE_NUM4 = 102;
//@    final static int KEY_VALUE_NUM5 = 103;
//@    final static int KEY_VALUE_NUM6 = 104;
//@    final static int KEY_VALUE_NUM7 = 118;
//@    final static int KEY_VALUE_NUM8 = 98;
//@    final static int KEY_VALUE_NUM9 = 110;
//@    final static int KEY_VALUE_POUND = 106;
//@    final static int KEY_VALUE_STAR = 117;
//@#break
//@#case "NOKIA_N97":
//@    final static int KEY_VALUE_NUM0 = 42;
//@    final static int KEY_VALUE_NUM7 = 55;
//@    final static int KEY_VALUE_NUM9 = 0;
//@    final static int KEY_VALUE_NUM1 = 0;
//@    final static int KEY_VALUE_NUM2 = 0;
//@    final static int KEY_VALUE_NUM3 = 0;
//@    final static int KEY_VALUE_NUM4 = 0;
//@    final static int KEY_VALUE_NUM5 = 0;
//@    final static int KEY_VALUE_NUM6 = 0;
//@    final static int KEY_VALUE_NUM8 = 0;
//@    final static int KEY_VALUE_POUND = 0;
//@    final static int KEY_VALUE_STAR = 0;
//@#break
//@#default:
    final static int KEY_VALUE_NUM0 = Canvas.KEY_NUM0;
    final static int KEY_VALUE_NUM1 = Canvas.KEY_NUM1;
    final static int KEY_VALUE_NUM2 = Canvas.KEY_NUM2;
    final static int KEY_VALUE_NUM3 = Canvas.KEY_NUM3;
    final static int KEY_VALUE_NUM4 = Canvas.KEY_NUM4;
    final static int KEY_VALUE_NUM5 = Canvas.KEY_NUM5;
    final static int KEY_VALUE_NUM6 = Canvas.KEY_NUM6;
    final static int KEY_VALUE_NUM7 = Canvas.KEY_NUM7;
    final static int KEY_VALUE_NUM8 = Canvas.KEY_NUM8;
    final static int KEY_VALUE_NUM9 = Canvas.KEY_NUM9;
    final static int KEY_VALUE_POUND = Canvas.KEY_POUND;
    final static int KEY_VALUE_STAR = Canvas.KEY_STAR;
//@#break
//@#}

    final static int KEYSLOT_DUMMY = 0;
    final static int KEYSLOT_UP = 1;
    final static int KEYSLOT_DOWN = 2;
    final static int KEYSLOT_LEFT = 3;
    final static int KEYSLOT_RIGHT = 4;
    final static int KEYSLOT_FIRE = 5;
    final static int KEYSLOT_0 = 6;
    final static int KEYSLOT_1 = 7;
    final static int KEYSLOT_2 = 8;
    final static int KEYSLOT_3 = 9;
    final static int KEYSLOT_4 = 10;
    final static int KEYSLOT_5 = 11;
    final static int KEYSLOT_6 = 12;
    final static int KEYSLOT_7 = 13;
    final static int KEYSLOT_8 = 14;
    final static int KEYSLOT_9 = 15;
    final static int KEYSLOT_STAR = 16;
    final static int KEYSLOT_POUND = 17;
    final static int KEYSLOT_SOFTLEFT = 18;
    final static int KEYSLOT_SOFTRIGHT = 19;
    final static int KEYSLOT_AMOUNT = 20;

    final static int KEY_PRESS_NEXT = 0;
    final static int KEY_PRESS_PREV = 1;
    final static int KEY_PRESS_CONF = 2;
    final static int AMOUNT_KEY_PRESS = 3;

    final static int GKEY_DUMMY = 1 << 0;
    final static int GKEY_UP = 1 << 1;
    final static int GKEY_DOWN = 1 << 2;
    final static int GKEY_LEFT = 1 << 3;
    final static int GKEY_RIGHT = 1 << 4;
    final static int GKEY_FIRE = 1 << 5;
    final static int GKEY_0 = 1 << 6;
    final static int GKEY_1 = 1 << 7;
    final static int GKEY_2 = 1 << 8;
    final static int GKEY_3 = 1 << 9;
    final static int GKEY_4 = 1 << 10;
    final static int GKEY_5 = 1 << 11;
    final static int GKEY_6 = 1 << 12;
    final static int GKEY_7 = 1 << 13;
    final static int GKEY_8 = 1 << 14;
    final static int GKEY_9 = 1 << 15;
    final static int GKEY_STAR = 1 << 16;
    final static int GKEY_POUND = 1 << 17;
    final static int GKEY_SOFTLEFT = 1 << 18;
    final static int GKEY_SOFTRIGHT = 1 << 19;
    final static int GKEY_AMOUNT = 1 << 20;

    final static int GKEY_MOVEUP = GKEY_UP | GKEY_2;
    final static int GKEY_MOVEDOWN = GKEY_DOWN | GKEY_8;
    final static int GKEY_MOVELEFT = GKEY_LEFT | GKEY_4;
    final static int GKEY_MOVERIGHT = GKEY_RIGHT | GKEY_6;
    final static int GKEY_ALL = GKEY_AMOUNT - 1;
    final static int GKEY_MIDLLE = GKEY_FIRE | GKEY_5;
    final static int GKEY_OK = GKEY_FIRE | GKEY_5 | GKEY_SOFTLEFT;

    final static int GKEY_RELEASEDKEY = GKEY_SOFTLEFT | GKEY_SOFTRIGHT;
//@@
//@@../Const/Const_Channel.h
//@#switch (Channel)
//@#{
//@#case "TB":
//@        final static String CHANNEL_NAME = "TB";
//@#break
//@#case "GF":
	final static String CHANNEL_NAME = "GF";
//@#break
//@#case "QQ":
//@    final static String CHANNEL_NAME = "QQ";
//@#break
//@#case "UC":
//@	final static String CHANNEL_NAME = "UC";
//@#break
//@#case "DCN":
//@	final static String CHANNEL_NAME = "DCN";
//@#break
//@#case "NCD":
//@	final static String CHANNEL_NAME = "NCD";
//@#break
//@#case "PJ":
//@	final static String CHANNEL_NAME = "PJ";
//@#break
//@#case "TS":
//@	final static String CHANNEL_NAME = "TS";
//@#break
//@#case "TB":
//@	final static String CHANNEL_NAME = "TB";
//@#break
//@#defualt:
//@	final static String CHANNEL_NAME = "";
//@#break
//@#}
//@@
    //导入生成.h文件
//@@../Const/MapMessage.h


    //ACTOR LIST (动画文件名称)
    public static final String[]     ARR_STR_ACTOR_FILE_NAME      = {//@in X:\RPG22_CMCC\trunk\SourceCode\Const\MapMessage.h
                                                                  };

    public static final int DEFINE_HERO = 0;
    public static final int DEFINE_HERO_LA_HERO_00 = 0;
    public static final int DEFINE_HERO_LA_HERO_01 = 1;
    public static final int DEFINE_HERO_LA_HERO_02 = 2;
    public static final int DEFINE_HERO_LA_HERO_03 = 3;
    public static final int DEFINE_HERO_LA_HERO_04 = 4;
    public static final int DEFINE_AMOUNT_HERO = 5;

    public static final int DEFINE_DIRECTION = 1;
    public static final int DEFINE_DIRECTION_UP = 0;
    public static final int DEFINE_DIRECTION_RIGHT = 1;
    public static final int DEFINE_DIRECTION_DOWN = 2;
    public static final int DEFINE_DIRECTION_LEFT = 3;
    public static final int DEFINE_AMOUNT_DIRECTION = 4;

    public static final int DEFINE_BOOLEAN = 2;
    public static final int DEFINE_BOOLEAN_FALSE = 0;
    public static final int DEFINE_BOOLEAN_TRUE = 1;
    public static final int DEFINE_AMOUNT_BOOLEAN = 2;

    public static final int DEFINE_FLOOR = 3;
    public static final int DEFINE_FLOOR_LIGHT_FRONT = 0;
    public static final int DEFINE_FLOOR_LIGHT_BEHIND = 1;
    public static final int DEFINE_AMOUNT_FLOOR = 2;

    public static final int DEFINE_CURTAIN_OPERATE = 4;
    public static final int DEFINE_CURTAIN_OPERATE_MOVIE_ON = 0;
    public static final int DEFINE_CURTAIN_OPERATE_MOVIE_OFF = 1;
    public static final int DEFINE_CURTAIN_OPERATE_CLOSE = 2;
    public static final int DEFINE_CURTAIN_OPERATE_OPEN = 3;
    public static final int DEFINE_AMOUNT_CURTAIN_OPERATE = 4;

    public static final int DEFINE_ICON = 5;
    public static final int DEFINE_ICON_JUNMOXIE = 0;
    public static final int DEFINE_ICON_DUGUXIAOYI = 1;
    public static final int DEFINE_ICON_GUANQINGHAN = 2;
    public static final int DEFINE_ICON_YINGBOKONG = 3;
    public static final int DEFINE_ICON_JUNZHANTIAN = 4;
    public static final int DEFINE_ICON_EMPEROR = 5;
    public static final int DEFINE_ICON_JUNWUYI = 6;
    public static final int DEFINE_ICON_LEILA = 7;
    public static final int DEFINE_ICON_XIAOHAN = 8;
    public static final int DEFINE_AMOUNT_ICON = 9;

    public static final int DEFINE_BROW = 6;
    public static final int DEFINE_BROW_STANDARD = 0;
    public static final int DEFINE_BROW_HAPPY = 1;
    public static final int DEFINE_BROW_ANGER = 2;
    public static final int DEFINE_BROW_SAD = 3;
    public static final int DEFINE_BROW_SHY = 4;
    public static final int DEFINE_AMOUNT_BROW = 5;

    public static final int DEFINE_LIGHT = 7;
    public static final int DEFINE_LIGHT_E_Q0 = 0;
    public static final int DEFINE_LIGHT_E_Q1 = 1;
    public static final int DEFINE_LIGHT_E_Q2 = 2;
    public static final int DEFINE_LIGHT_MAGIC0 = 3;
    public static final int DEFINE_LIGHT_MAGIC1 = 4;
    public static final int DEFINE_LIGHT_SCENE0 = 5;
    public static final int DEFINE_AMOUNT_LIGHT = 6;

    public static final int DEFINE_OPERATOR = 8;
    public static final int DEFINE_OPERATOR_EQUALITY = 0;
    public static final int DEFINE_OPERATOR_NOT_EQUAL = 1;
    public static final int DEFINE_OPERATOR_LESS_THAN = 2;
    public static final int DEFINE_OPERATOR_GREATER_THAN = 3;
    public static final int DEFINE_OPERATOR_NOT_MORE_THAN = 4;
    public static final int DEFINE_OPERATOR_NOT_LESS_THAN = 5;
    public static final int DEFINE_OPERATOR_SET = 6;
    public static final int DEFINE_OPERATOR_PLUS = 7;
    public static final int DEFINE_OPERATOR_SUB = 8;
    public static final int DEFINE_OPERATOR_MULTIPLY = 9;
    public static final int DEFINE_OPERATOR_DIVIDE = 10;
    public static final int DEFINE_OPERATOR_MOD = 11;
    public static final int DEFINE_AMOUNT_OPERATOR = 12;

    public static final int DEFINE_WEATHER = 9;
    public static final int DEFINE_WEATHER_NONE = 0;
    public static final int DEFINE_WEATHER_RING = 1;
    public static final int DEFINE_AMOUNT_WEATHER = 2;

    public static final int DEFINE_ALIGN = 10;
    public static final int DEFINE_ALIGN_LEFT = 0;
    public static final int DEFINE_ALIGN_HCENTER = 1;
    public static final int DEFINE_ALIGN_RIGHT = 2;
    public static final int DEFINE_ALIGN_TOP = 3;
    public static final int DEFINE_ALIGN_VCENTER = 4;
    public static final int DEFINE_ALIGN_BOTTOM = 5;
    public static final int DEFINE_AMOUNT_ALIGN = 6;

    public static final int DEFINE_HOLD_TYPE = 11;
    public static final int DEFINE_HOLD_TYPE_ITEM = 0;
    public static final int DEFINE_HOLD_TYPE_EQUIP = 1;
    public static final int DEFINE_HOLD_TYPE_GOLD = 2;
    public static final int DEFINE_HOLD_TYPE_BATTLE = 3;
    public static final int DEFINE_HOLD_TYPE_SMS = 4;
    public static final int DEFINE_HOLD_TYPE_BATTLE_SMS = 5;
    public static final int DEFINE_HOLD_TYPE_TASK = 6;
    public static final int DEFINE_HOLD_TYPE_EFFORT = 7;
    public static final int DEFINE_HOLD_TYPE_MIXTRUE = 8;
    public static final int DEFINE_HOLD_TYPE_TOKEN = 9;
    public static final int DEFINE_HOLD_TYPE_SHOP = 10;
    public static final int DEFINE_HOLD_TYPE_GOODS = 11;
    public static final int DEFINE_HOLD_TYPE_EVENT = 12;
    public static final int DEFINE_HOLD_TYPE_SCORE = 13;
    public static final int DEFINE_AMOUNT_HOLD_TYPE = 14;

    public static final int DEFINE_DATA_TYPE = 12;
    public static final int DEFINE_DATA_TYPE_CONST = 0;
    public static final int DEFINE_DATA_TYPE_VARIABLE = 1;
    public static final int DEFINE_DATA_TYPE_RANDOM = 2;
    public static final int DEFINE_AMOUNT_DATA_TYPE = 3;

    public static final int DEFINE_RANGE_TYPE = 13;
    public static final int DEFINE_RANGE_TYPE_STAND = 0;
    public static final int DEFINE_RANGE_TYPE_RANDOM = 1;
    public static final int DEFINE_RANGE_TYPE_FLY = 2;
    public static final int DEFINE_AMOUNT_RANGE_TYPE = 3;

    public static final int DEFINE_EQUIP_SLOT = 14;
    public static final int DEFINE_EQUIP_SLOT_WEAPON = 0;
    public static final int DEFINE_EQUIP_SLOT_ARMOR = 1;
    public static final int DEFINE_EQUIP_SLOT_OTHER = 2;
    public static final int DEFINE_AMOUNT_EQUIP_SLOT = 3;

    public static final int DEFINE_TIMER_CONTROL = 15;
    public static final int DEFINE_TIMER_CONTROL_START = 0;
    public static final int DEFINE_TIMER_CONTROL_STOP = 1;
    public static final int DEFINE_TIMER_CONTROL_PAUSE = 2;
    public static final int DEFINE_TIMER_CONTROL_RESUME = 3;
    public static final int DEFINE_TIMER_CONTROL_RESET = 4;
    public static final int DEFINE_AMOUNT_TIMER_CONTROL = 5;

    public static final int DEFINE_ACTOR_VALUE = 16;
    public static final int DEFINE_ACTOR_VALUE_BV_LASTEXP = 0;
    public static final int DEFINE_ACTOR_VALUE_BV_EXP = 1;
    public static final int DEFINE_ACTOR_VALUE_BV_NEXTEXP = 2;
    public static final int DEFINE_ACTOR_VALUE_BV_SP = 3;
    public static final int DEFINE_ACTOR_VALUE_BV_HP = 4;
    public static final int DEFINE_ACTOR_VALUE_BV_MP = 5;
    public static final int DEFINE_ACTOR_VALUE_BV_MAXSP = 6;
    public static final int DEFINE_ACTOR_VALUE_BV_MAXHP = 7;
    public static final int DEFINE_ACTOR_VALUE_BV_MAXMP = 8;
    public static final int DEFINE_ACTOR_VALUE_BV_PHYDMG = 9;
    public static final int DEFINE_ACTOR_VALUE_BV_PSYDMG = 10;
    public static final int DEFINE_ACTOR_VALUE_BV_DP = 11;
    public static final int DEFINE_ACTOR_VALUE_BV_CH = 12;
    public static final int DEFINE_ACTOR_VALUE_BV_DODGE = 13;
    public static final int DEFINE_ACTOR_VALUE_BV_SPEED = 14;
    public static final int DEFINE_ACTOR_VALUE_BV_LV = 15;
    public static final int DEFINE_ACTOR_VALUE_BV_SPINCREASE = 16;
    public static final int DEFINE_ACTOR_VALUE_BV_FRAME = 17;
    public static final int DEFINE_AMOUNT_ACTOR_VALUE = 18;

    public static final int DEFINE_ACTOR_PROPERTY = 17;
    public static final int DEFINE_ACTOR_PROPERTY_PARA_MOVETYPE = 0;
    public static final int DEFINE_ACTOR_PROPERTY_PARA_SHOW = 1;
    public static final int DEFINE_ACTOR_PROPERTY_PARA_SHADOW = 2;
    public static final int DEFINE_ACTOR_PROPERTY_PARA_HEADIMG = 3;
    public static final int DEFINE_ACTOR_PROPERTY_PARA_ISCAMERA = 4;
    public static final int DEFINE_ACTOR_PROPERTY_PARA_Z = 5;
    public static final int DEFINE_ACTOR_PROPERTY_PARA_ISENEMY = 6;
    public static final int DEFINE_ACTOR_PROPERTY_PARA_RANGETYPE = 7;
    public static final int DEFINE_ACTOR_PROPERTY_PARA_SPAWNTIME = 8;
    public static final int DEFINE_ACTOR_PROPERTY_PARA_SPAWNTIMER = 9;
    public static final int DEFINE_ACTOR_PROPERTY_PARA_EMOTION_TIME = 10;
    public static final int DEFINE_ACTOR_PROPERTY_PARA_EMOTION_TYPE = 11;
    public static final int DEFINE_ACTOR_PROPERTY_PARA_INTEAM = 12;
    public static final int DEFINE_ACTOR_PROPERTY_PARA_INFORMATION = 13;
    public static final int DEFINE_ACTOR_PROPERTY_PARA_TRANSPORT = 14;
    public static final int DEFINE_ACTOR_PROPERTY_PARA_ISFLY = 15;
    public static final int DEFINE_ACTOR_PROPERTY_PARA_FOLLOWTEAM = 16;
    public static final int DEFINE_AMOUNT_ACTOR_PROPERTY = 17;

    public static final int DEFINE_BC_ACTIONS = 18;
    public static final int DEFINE_BC_ACTIONS_BCA_PHY = 0;
    public static final int DEFINE_BC_ACTIONS_BCA_SKILL = 1;
    public static final int DEFINE_BC_ACTIONS_BCA_ITEM = 2;
    public static final int DEFINE_BC_ACTIONS_BCA_LIMIT = 3;
    public static final int DEFINE_BC_ACTIONS_BCA_WAIT = 4;
    public static final int DEFINE_AMOUNT_BC_ACTIONS = 5;

    public static final int DEFINE_BATTLE_STATE = 19;
    public static final int DEFINE_BATTLE_STATE_RS_INIT = 0;
    public static final int DEFINE_BATTLE_STATE_RS_UI = 1;
    public static final int DEFINE_BATTLE_STATE_RS_AIM = 2;
    public static final int DEFINE_BATTLE_STATE_RS_ACTION = 3;
    public static final int DEFINE_BATTLE_STATE_RS_LOOT = 4;
    public static final int DEFINE_BATTLE_STATE_RS_END = 5;
    public static final int DEFINE_AMOUNT_BATTLE_STATE = 6;

    public static final int DEFINE_SMS_TYPE = 20;
    public static final int DEFINE_SMS_TYPE_MORE_EXP = 0;
    public static final int DEFINE_SMS_TYPE_FREE_SAVE = 1;
    public static final int DEFINE_SMS_TYPE_EQUIP_ADV = 2;
    public static final int DEFINE_SMS_TYPE_LEVEL_LIMIT = 3;
    public static final int DEFINE_SMS_TYPE_MORE_GOLD = 4;
    public static final int DEFINE_SMS_TYPE_GET_KEY = 5;
    public static final int DEFINE_SMS_TYPE_GET_MONEY = 6;
    public static final int DEFINE_SMS_TYPE_ACTIVE = 7;
    public static final int DEFINE_SMS_TYPE_RELIFE = 8;
    public static final int DEFINE_AMOUNT_SMS_TYPE = 9;

    public static final int DEFINE_MUSIC_SLOT = 21;
    public static final int DEFINE_MUSIC_SLOT_SCENE = 0;
    public static final int DEFINE_MUSIC_SLOT_BATTLE = 1;
    public static final int DEFINE_AMOUNT_MUSIC_SLOT = 2;

    public static final int DEFINE_DIALOG_TYPE = 22;
    public static final int DEFINE_DIALOG_TYPE_DIALOG_NEXT = 0;
    public static final int DEFINE_AMOUNT_DIALOG_TYPE = 1;

    public static final int DEFINE_MENU_ACTION = 23;
    public static final int DEFINE_MENU_ACTION_OPEN = 0;
    public static final int DEFINE_MENU_ACTION_CONFIRM = 1;
    public static final int DEFINE_MENU_ACTION_CURSOR = 2;
    public static final int DEFINE_MENU_ACTION_RETURN = 3;
    public static final int DEFINE_MENU_ACTION_WAIT = 4;
    public static final int DEFINE_MENU_ACTION_CONTROL = 5;
    public static final int DEFINE_MENU_ACTION_SMS_UI = 6;
    public static final int DEFINE_MENU_ACTION_ACTIVE_WND = 7;
    public static final int DEFINE_AMOUNT_MENU_ACTION = 8;

    public static final int DEFINE_GAME_DATA = 24;
    public static final int DEFINE_GAME_DATA_MONEY = 0;
    public static final int DEFINE_GAME_DATA_TOKEN = 1;
    public static final int DEFINE_GAME_DATA_CONSUMPTION = 2;
    public static final int DEFINE_GAME_DATA_KILL_COUNT = 3;
    public static final int DEFINE_GAME_DATA_WIN_COUNT = 4;
    public static final int DEFINE_GAME_DATA_EQUIP_LV = 5;
    public static final int DEFINE_GAME_DATA_DISCOUNT = 6;
    public static final int DEFINE_GAME_DATA_SCORE = 7;
    public static final int DEFINE_GAME_DATA_EFFORT_NUM = 8;
    public static final int DEFINE_GAME_DATA_DESIGH_NUM = 9;
    public static final int DEFINE_AMOUNT_GAME_DATA = 10;

    public static final int DEFINE_UI_TYPE = 25;
    public static final int DEFINE_UI_TYPE_NORMAL = 0;
    public static final int DEFINE_UI_TYPE_SMS = 1;
    public static final int DEFINE_AMOUNT_UI_TYPE = 2;

    public static final int DEFINE_EXTRA_VALUE = 26;
    public static final int DEFINE_EXTRA_VALUE_SP = 0;
    public static final int DEFINE_EXTRA_VALUE_HP = 1;
    public static final int DEFINE_EXTRA_VALUE_MP = 2;
    public static final int DEFINE_EXTRA_VALUE_PHY = 3;
    public static final int DEFINE_EXTRA_VALUE_PSY = 4;
    public static final int DEFINE_EXTRA_VALUE_DP = 5;
    public static final int DEFINE_EXTRA_VALUE_CH = 6;
    public static final int DEFINE_EXTRA_VALUE_DODGE = 7;
    public static final int DEFINE_EXTRA_VALUE_SPEED = 8;
    public static final int DEFINE_AMOUNT_EXTRA_VALUE = 9;

    public static final int DEFINE_ACTION_TYPE = 27;
    public static final int DEFINE_ACTION_TYPE_STAND = 0;
    public static final int DEFINE_ACTION_TYPE_WALK = 1;
    public static final int DEFINE_ACTION_TYPE_TRANSPORT = 2;
    public static final int DEFINE_ACTION_TYPE_TRANSPORT_WALK = 3;
    public static final int DEFINE_ACTION_TYPE_FISHING = 4;
    public static final int DEFINE_ACTION_TYPE_FISHING1 = 5;
    public static final int DEFINE_AMOUNT_ACTION_TYPE = 6;

    public static final int DEFINE_CHEST_TYPE = 28;
    public static final int DEFINE_CHEST_TYPE_SHOP = 0;
    public static final int DEFINE_CHEST_TYPE_ITEM = 1;
    public static final int DEFINE_AMOUNT_CHEST_TYPE = 2;

    public static final int DEFINE_KEY_VALUE = 29;
    public static final int DEFINE_KEY_VALUE_NONE = 0;
    public static final int DEFINE_KEY_VALUE_MIDDLE = 1;
    public static final int DEFINE_KEY_VALUE_DECIDE = 2;
    public static final int DEFINE_KEY_VALUE_CHANCEL = 3;
    public static final int DEFINE_KEY_VALUE_UP = 4;
    public static final int DEFINE_KEY_VALUE_RIGHT = 5;
    public static final int DEFINE_KEY_VALUE_DOWN = 6;
    public static final int DEFINE_KEY_VALUE_LEFT = 7;
    public static final int DEFINE_AMOUNT_KEY_VALUE = 8;

    public static final int DEFINE_TYPE_FOLLOW = 30;
    public static final int DEFINE_TYPE_FOLLOW_INCREASE = 0;
    public static final int DEFINE_TYPE_FOLLOW_DELETE = 1;
    public static final int DEFINE_AMOUNT_TYPE_FOLLOW = 2;

    public static final int AMOUNT_SCRIPT_VARIABLES = 123;
    public static final int SCRIPT_VARIABLE_SMS_PARA0 = 0;
    public static final int SCRIPT_VARIABLE_SMS_PARA1 = 1;
    public static final int SCRIPT_VARIABLE_SMS_PARA2 = 2;
    public static final int SCRIPT_VARIABLE_SMS_PARA3 = 3;
    public static final int SCRIPT_VARIABLE_SMS_PARA4 = 4;
    public static final int SCRIPT_VARIABLE_SMS_PARA5 = 5;
    public static final int SCRIPT_VARIABLE_SMS_PARA6 = 6;
    public static final int SCRIPT_VARIABLE_SMS_PARA7 = 7;
    public static final int SCRIPT_VARIABLE_SMS_PARA8 = 8;
    public static final int SCRIPT_VARIABLE_SMS_PARA9 = 9;
    public static final int SCRIPT_VARIABLE_SMS_PARA10 = 10;
    public static final int SCRIPT_VARIABLE_SMS_PARA11 = 11;
    public static final int SCRIPT_VARIABLE_SMS_PARA12 = 12;
    public static final int SCRIPT_VARIABLE_SMS_PARA13 = 13;
    public static final int SCRIPT_VARIABLE_SMS_PARA14 = 14;
    public static final int SCRIPT_VARIABLE_SMS_NEED_ACTIVE = 15;
    public static final int SCRIPT_VARIABLE_GLOBAL_0 = 16;
    public static final int SCRIPT_VARIABLE_GLOBAL_1 = 17;
    public static final int SCRIPT_VARIABLE_GLOBAL_2 = 18;
    public static final int SCRIPT_VARIABLE_GLOBAL_3 = 19;
    public static final int SCRIPT_VARIABLE_GLOBAL_LAST = 20;
    public static final int SCRIPT_VARIABLE_SAVE_ON = 21;
    public static final int SCRIPT_VARIABLE_TEAM_SHOW_NUM = 22;
    public static final int SCRIPT_VARIABLE_KEY_RESPONSE = 23;
    public static final int SCRIPT_VARIABLE_MAIN = 24;
    public static final int SCRIPT_VARIABLE_ZHUXIAN = 25;
    public static final int SCRIPT_VARIABLE_ZHURENWU = 26;
    public static final int SCRIPT_VARIABLE_FUZHU1 = 27;
    public static final int SCRIPT_VARIABLE_FUZHU2 = 28;
    public static final int SCRIPT_VARIABLE_FUZHU3 = 29;
    public static final int SCRIPT_VARIABLE_FUZHU4 = 30;
    public static final int SCRIPT_VARIABLE_FUZHU5 = 31;
    public static final int SCRIPT_VARIABLE_FUZHU6 = 32;
    public static final int SCRIPT_VARIABLE_FUZHU7 = 33;
    public static final int SCRIPT_VARIABLE_FUZHU8 = 34;
    public static final int SCRIPT_VARIABLE_FUZHU9 = 35;
    public static final int SCRIPT_VARIABLE_FUZHU10 = 36;
    public static final int SCRIPT_VARIABLE_DAIBI = 37;
    public static final int SCRIPT_VARIABLE_CHOOSE = 38;
    public static final int SCRIPT_VARIABLE_ZIDONG = 39;
    public static final int SCRIPT_VARIABLE_DIAOYU1 = 40;
    public static final int SCRIPT_VARIABLE_DIAOYU2 = 41;
    public static final int SCRIPT_VARIABLE_DIAOYU3 = 42;
    public static final int SCRIPT_VARIABLE_DIAOYU4 = 43;
    public static final int SCRIPT_VARIABLE_DIAOYU5 = 44;
    public static final int SCRIPT_VARIABLE_CANGBAOTU = 45;
    public static final int SCRIPT_VARIABLE_SNBOX = 46;
    public static final int SCRIPT_VARIABLE_SN1BOX = 47;
    public static final int SCRIPT_VARIABLE_HY2BOX1 = 48;
    public static final int SCRIPT_VARIABLE_HY2BOX2 = 49;
    public static final int SCRIPT_VARIABLE_HY2BOX3 = 50;
    public static final int SCRIPT_VARIABLE_HY2BOX4 = 51;
    public static final int SCRIPT_VARIABLE_HY2BOX5 = 52;
    public static final int SCRIPT_VARIABLE_ST1BOX = 53;
    public static final int SCRIPT_VARIABLE_CZBOX = 54;
    public static final int SCRIPT_VARIABLE_ST2BOX = 55;
    public static final int SCRIPT_VARIABLE_HY3BOX = 56;
    public static final int SCRIPT_VARIABLE_HY3BOX1 = 57;
    public static final int SCRIPT_VARIABLE_HY3BOX2 = 58;
    public static final int SCRIPT_VARIABLE_HY3BOX3 = 59;
    public static final int SCRIPT_VARIABLE_HY3BOX4 = 60;
    public static final int SCRIPT_VARIABLE_CZ1BOX = 61;
    public static final int SCRIPT_VARIABLE_CZ1BOX1 = 62;
    public static final int SCRIPT_VARIABLE_BOX1 = 63;
    public static final int SCRIPT_VARIABLE_BOX2 = 64;
    public static final int SCRIPT_VARIABLE_BOX3 = 65;
    public static final int SCRIPT_VARIABLE_BOX4 = 66;
    public static final int SCRIPT_VARIABLE_BOX5 = 67;
    public static final int SCRIPT_VARIABLE_BOX6 = 68;
    public static final int SCRIPT_VARIABLE_BOX7 = 69;
    public static final int SCRIPT_VARIABLE_CZ1SHU1 = 70;
    public static final int SCRIPT_VARIABLE_CZ1SHU2 = 71;
    public static final int SCRIPT_VARIABLE_CZ1SHU3 = 72;
    public static final int SCRIPT_VARIABLE_RYD1SHU1 = 73;
    public static final int SCRIPT_VARIABLE_RYD1SHU2 = 74;
    public static final int SCRIPT_VARIABLE_RYD1SHU3 = 75;
    public static final int SCRIPT_VARIABLE_RYD1SHU4 = 76;
    public static final int SCRIPT_VARIABLE_RYD1SHU5 = 77;
    public static final int SCRIPT_VARIABLE_SD3SHU1 = 78;
    public static final int SCRIPT_VARIABLE_SD3SHU2 = 79;
    public static final int SCRIPT_VARIABLE_SD3SHU3 = 80;
    public static final int SCRIPT_VARIABLE_HYT1SHU1 = 81;
    public static final int SCRIPT_VARIABLE_HYT1SHU2 = 82;
    public static final int SCRIPT_VARIABLE_HYT1SHU3 = 83;
    public static final int SCRIPT_VARIABLE_BIWU3GUAN = 84;
    public static final int SCRIPT_VARIABLE_CANSHU_0 = 85;
    public static final int SCRIPT_VARIABLE_CANSHU_1 = 86;
    public static final int SCRIPT_VARIABLE_CANSHU_2 = 87;
    public static final int SCRIPT_VARIABLE_CANSHU_3 = 88;
    public static final int SCRIPT_VARIABLE_CANSHU_4 = 89;
    public static final int SCRIPT_VARIABLE_CANSHU_5 = 90;
    public static final int SCRIPT_VARIABLE_CANSHU_6 = 91;
    public static final int SCRIPT_VARIABLE_MAINQUESTFIX = 92;
    public static final int SCRIPT_VARIABLE_ZHIXIAN_1 = 93;
    public static final int SCRIPT_VARIABLE_ZHIXIAN_2 = 94;
    public static final int SCRIPT_VARIABLE_ZHIXIAN_3 = 95;
    public static final int SCRIPT_VARIABLE_ZHIXIAN_4 = 96;
    public static final int SCRIPT_VARIABLE_ZHIXIAN_5 = 97;
    public static final int SCRIPT_VARIABLE_ZHIXIAN_6 = 98;
    public static final int SCRIPT_VARIABLE_CZ14JUQING = 99;
    public static final int SCRIPT_VARIABLE_CZ14_0 = 100;
    public static final int SCRIPT_VARIABLE_CZ14_1 = 101;
    public static final int SCRIPT_VARIABLE_ICON0 = 102;
    public static final int SCRIPT_VARIABLE_ICON1 = 103;
    public static final int SCRIPT_VARIABLE_ICON2 = 104;
    public static final int SCRIPT_VARIABLE_ICON3 = 105;
    public static final int SCRIPT_VARIABLE_ICON4 = 106;
    public static final int SCRIPT_VARIABLE_ICON5 = 107;
    public static final int SCRIPT_VARIABLE_ICON6 = 108;
    public static final int SCRIPT_VARIABLE_TIWEN_0 = 109;
    public static final int SCRIPT_VARIABLE_TIWEN_1 = 110;
    public static final int SCRIPT_VARIABLE_TIWEN_2 = 111;
    public static final int SCRIPT_VARIABLE_TIWEN_3 = 112;
    public static final int SCRIPT_VARIABLE_TIWEN_4 = 113;
    public static final int SCRIPT_VARIABLE_JIAOXUE1 = 114;
    public static final int SCRIPT_VARIABLE_JIAOXUE2 = 115;
    public static final int SCRIPT_VARIABLE_JIAOXUE3 = 116;
    public static final int SCRIPT_VARIABLE_JIAOXUE4 = 117;
    public static final int SCRIPT_VARIABLE_JIAOXUE5 = 118;
    public static final int SCRIPT_VARIABLE_JIAOXUE6 = 119;
    public static final int SCRIPT_VARIABLE_JIAOXUE7 = 120;
    public static final int SCRIPT_VARIABLE_BATTLE_LOSE = 121;
    public static final int SCRIPT_VARIABLE_TRANSPORT = 122;
    //IF
    public static final int SCHEMA_IF = 0;
    public static final int CMD_IF_LEFT_TYPE = 0;
    public static final int CMD_IF_LEFT_VALUE0 = 1;
    public static final int CMD_IF_LEFT_VALUE1 = 2;
    public static final int CMD_IF_OPERATOR = 3;
    public static final int CMD_IF_RIGHT_TYPE = 4;
    public static final int CMD_IF_RIGHT_VALUE0 = 5;
    public static final int CMD_IF_RIGHT_VALUE1 = 6;
    public static final int CMD_COUNT_IF = 7;

    //ENDIF
    public static final int SCHEMA_ENDIF = 1;
    public static final int CMD_COUNT_ENDIF = 0;

    //RETURN
    public static final int SCHEMA_RETURN = 2;
    public static final int CMD_COUNT_RETURN = 0;

    //SET
    public static final int SCHEMA_SET = 3;
    public static final int CMD_SET_PARA = 0;
    public static final int CMD_SET_NUMBERCONTROL = 1;
    public static final int CMD_SET_DATA_TYPE = 2;
    public static final int CMD_SET_VALUE1 = 3;
    public static final int CMD_SET_VALUE2 = 4;
    public static final int CMD_COUNT_SET = 5;

    //LOADMAP
    public static final int SCHEMA_LOADMAP = 4;
    public static final int CMD_LOADMAP_ID = 0;
    public static final int CMD_LOADMAP_TILE_X = 1;
    public static final int CMD_LOADMAP_TILE_Y = 2;
    public static final int CMD_LOADMAP_DIR = 3;
    public static final int CMD_COUNT_LOADMAP = 4;

    //SETACTORPOS
    public static final int SCHEMA_SETACTORPOS = 5;
    public static final int CMD_SETACTORPOS_ID = 0;
    public static final int CMD_SETACTORPOS_TILE_X = 1;
    public static final int CMD_SETACTORPOS_TILE_Y = 2;
    public static final int CMD_COUNT_SETACTORPOS = 3;

    //CONTROLACTORSHOW
    public static final int SCHEMA_CONTROLACTORSHOW = 6;
    public static final int CMD_CONTROLACTORSHOW_ID = 0;
    public static final int CMD_CONTROLACTORSHOW_SHOW = 1;
    public static final int CMD_COUNT_CONTROLACTORSHOW = 2;

    //DIALOGUE
    public static final int SCHEMA_DIALOGUE = 7;
    public static final int CMD_DIALOGUE_PROTRAIT_ID = 0;
    public static final int CMD_DIALOGUE_PROTRAIT_POS_H = 1;
    public static final int CMD_DIALOGUE_PROTRAIT_POS_V = 2;
    public static final int CMD_DIALOGUE_MESSAGE = 3;
    public static final int CMD_DIALOGUE_EXPRESSION = 4;
    public static final int CMD_DIALOGUE_ACTOR_NAME = 5;
    public static final int CMD_COUNT_DIALOGUE = 6;

    //MOVE
    public static final int SCHEMA_MOVE = 8;
    public static final int CMD_MOVE_ID = 0;
    public static final int CMD_MOVE_DIR = 1;
    public static final int CMD_MOVE_STEP = 2;
    public static final int CMD_COUNT_MOVE = 3;

    //WAITMOVEFINISH
    public static final int SCHEMA_WAITMOVEFINISH = 9;
    public static final int CMD_WAITMOVEFINISH_ID = 0;
    public static final int CMD_COUNT_WAITMOVEFINISH = 1;

    //MOVESCREEN
    public static final int SCHEMA_MOVESCREEN = 10;
    public static final int CMD_MOVESCREEN_TAR_X = 0;
    public static final int CMD_MOVESCREEN_TAR_Y = 1;
    public static final int CMD_MOVESCREEN_TIME = 2;
    public static final int CMD_COUNT_MOVESCREEN = 3;

    //SETCAMERAFOCUES
    public static final int SCHEMA_SETCAMERAFOCUES = 11;
    public static final int CMD_SETCAMERAFOCUES_ID = 0;
    public static final int CMD_SETCAMERAFOCUES_TIME = 1;
    public static final int CMD_COUNT_SETCAMERAFOCUES = 2;

    //CHANGEACTION
    public static final int SCHEMA_CHANGEACTION = 12;
    public static final int CMD_CHANGEACTION_ACTOR = 0;
    public static final int CMD_CHANGEACTION_ACTION = 1;
    public static final int CMD_CHANGEACTION_REPEAT = 2;
    public static final int CMD_CHANGEACTION_ACTION_TYPE = 3;
    public static final int CMD_COUNT_CHANGEACTION = 4;

    //MOVEACTOR
    public static final int SCHEMA_MOVEACTOR = 13;
    public static final int CMD_MOVEACTOR_ACTORNAME = 0;
    public static final int CMD_MOVEACTOR_DIR = 1;
    public static final int CMD_MOVEACTOR_DISTANCE = 2;
    public static final int CMD_MOVEACTOR_TIME = 3;
    public static final int CMD_COUNT_MOVEACTOR = 4;

    //WAIT
    public static final int SCHEMA_WAIT = 14;
    public static final int CMD_WAIT_TIME = 0;
    public static final int CMD_COUNT_WAIT = 1;

    //ELSE
    public static final int SCHEMA_ELSE = 15;
    public static final int CMD_COUNT_ELSE = 0;

    //CHOOSEDIALOGUE
    public static final int SCHEMA_CHOOSEDIALOGUE = 16;
    public static final int CMD_CHOOSEDIALOGUE_PARA = 0;
    public static final int CMD_CHOOSEDIALOGUE_TITLE = 1;
    public static final int CMD_CHOOSEDIALOGUE_CHOOSE_COUNT = 2;
    public static final int CMD_CHOOSEDIALOGUE_CHOOSE_A = 3;
    public static final int CMD_CHOOSEDIALOGUE_CHOOSE_B = 4;
    public static final int CMD_CHOOSEDIALOGUE_CHOOSE_C = 5;
    public static final int CMD_CHOOSEDIALOGUE_CHOOSE_D = 6;
    public static final int CMD_CHOOSEDIALOGUE_CHOOSE_E = 7;
    public static final int CMD_COUNT_CHOOSEDIALOGUE = 8;

    //TEAMCONTROL
    public static final int SCHEMA_TEAMCONTROL = 17;
    public static final int CMD_TEAMCONTROL_ACTOR_ID = 0;
    public static final int CMD_TEAMCONTROL_ISINTEAM = 1;
    public static final int CMD_TEAMCONTROL_ISINFORMATION = 2;
    public static final int CMD_COUNT_TEAMCONTROL = 3;

    //SCREENCONTROL
    public static final int SCHEMA_SCREENCONTROL = 18;
    public static final int CMD_SCREENCONTROL_CONTROL = 0;
    public static final int CMD_SCREENCONTROL_HEIGHT = 1;
    public static final int CMD_SCREENCONTROL_WAIT = 2;
    public static final int CMD_COUNT_SCREENCONTROL = 3;

    //PACKAGEITEM
    public static final int SCHEMA_PACKAGEITEM = 19;
    public static final int CMD_PACKAGEITEM_ITEMID = 0;
    public static final int CMD_PACKAGEITEM_COUNT = 1;
    public static final int CMD_COUNT_PACKAGEITEM = 2;

    //SKILLCONTROL
    public static final int SCHEMA_SKILLCONTROL = 20;
    public static final int CMD_SKILLCONTROL_ACTOR = 0;
    public static final int CMD_SKILLCONTROL_SKILL_ID = 1;
    public static final int CMD_SKILLCONTROL_EXP = 2;
    public static final int CMD_COUNT_SKILLCONTROL = 3;

    //FIGHT
    public static final int SCHEMA_FIGHT = 21;
    public static final int CMD_FIGHT_ENEMY_TEAM_ID = 0;
    public static final int CMD_FIGHT_CAN_RUN = 1;
    public static final int CMD_FIGHT_LOSE_CONTINUE = 2;
    public static final int CMD_COUNT_FIGHT = 3;

    //END_GAMG
    public static final int SCHEMA_END_GAMG = 22;
    public static final int CMD_COUNT_END_GAMG = 0;

    //GAME_OVER
    public static final int SCHEMA_GAME_OVER = 23;
    public static final int CMD_COUNT_GAME_OVER = 0;

    //MESSAGE
    public static final int SCHEMA_MESSAGE = 24;
    public static final int CMD_MESSAGE_MESSAGE = 0;
    public static final int CMD_MESSAGE_TIME = 1;
    public static final int CMD_COUNT_MESSAGE = 2;

    //GETPACKAGEINFO
    public static final int SCHEMA_GETPACKAGEINFO = 25;
    public static final int CMD_GETPACKAGEINFO_ITEMID = 0;
    public static final int CMD_GETPACKAGEINFO_PARA = 1;
    public static final int CMD_COUNT_GETPACKAGEINFO = 2;

    //MISSION
    public static final int SCHEMA_MISSION = 26;
    public static final int CMD_MISSION_MAINMISSION = 0;
    public static final int CMD_MISSION_CONTROL = 1;
    public static final int CMD_MISSION_PARA = 2;
    public static final int CMD_MISSION_TASKS_ID = 3;
    public static final int CMD_COUNT_MISSION = 4;

    //TIMERCONTROL
    public static final int SCHEMA_TIMERCONTROL = 27;
    public static final int CMD_TIMERCONTROL_ID = 0;
    public static final int CMD_TIMERCONTROL_CONTROL = 1;
    public static final int CMD_TIMERCONTROL_PARA = 2;
    public static final int CMD_TIMERCONTROL_INITVALUE = 3;
    public static final int CMD_COUNT_TIMERCONTROL = 4;

    //TAKECONTROL
    public static final int SCHEMA_TAKECONTROL = 28;
    public static final int CMD_TAKECONTROL_ID = 0;
    public static final int CMD_COUNT_TAKECONTROL = 1;

    //CHEST
    public static final int SCHEMA_CHEST = 29;
    public static final int CMD_CHEST_ACTOR_ID = 0;
    public static final int CMD_CHEST_ITEM = 1;
    public static final int CMD_CHEST_QUANTITY = 2;
    public static final int CMD_COUNT_CHEST = 3;

    //FLASH
    public static final int SCHEMA_FLASH = 30;
    public static final int CMD_FLASH_COLOR_H = 0;
    public static final int CMD_FLASH_COLOR_L = 1;
    public static final int CMD_FLASH_INTERVAL = 2;
    public static final int CMD_FLASH_TIME = 3;
    public static final int CMD_FLASH_FADEIN = 4;
    public static final int CMD_FLASH_FADEOUT = 5;
    public static final int CMD_COUNT_FLASH = 6;

    //CAMERA_COLOR
    public static final int SCHEMA_CAMERA_COLOR = 31;
    public static final int CMD_CAMERA_COLOR_COLOR0_H = 0;
    public static final int CMD_CAMERA_COLOR_COLOR0_L = 1;
    public static final int CMD_CAMERA_COLOR_COLOR1_H = 2;
    public static final int CMD_CAMERA_COLOR_COLOR1_L = 3;
    public static final int CMD_CAMERA_COLOR_TIME = 4;
    public static final int CMD_COUNT_CAMERA_COLOR = 5;

    //SET_Z
    public static final int SCHEMA_SET_Z = 32;
    public static final int CMD_SET_Z_ACTOR_ID = 0;
    public static final int CMD_SET_Z_Z = 1;
    public static final int CMD_COUNT_SET_Z = 2;

    //SET_VELOCITY
    public static final int SCHEMA_SET_VELOCITY = 33;
    public static final int CMD_SET_VELOCITY_ACTOR_ID = 0;
    public static final int CMD_SET_VELOCITY_VX = 1;
    public static final int CMD_SET_VELOCITY_VY = 2;
    public static final int CMD_SET_VELOCITY_AX = 3;
    public static final int CMD_SET_VELOCITY_AY = 4;
    public static final int CMD_COUNT_SET_VELOCITY = 5;

    //SET_ENEMY
    public static final int SCHEMA_SET_ENEMY = 34;
    public static final int CMD_SET_ENEMY_ACTOR_ID = 0;
    public static final int CMD_SET_ENEMY_SPAWN_TIME = 1;
    public static final int CMD_SET_ENEMY_RANGE = 2;
    public static final int CMD_COUNT_SET_ENEMY = 3;

    //ADD_ENEMYTEAM
    public static final int SCHEMA_ADD_ENEMYTEAM = 35;
    public static final int CMD_ADD_ENEMYTEAM_ACTOR_ID = 0;
    public static final int CMD_ADD_ENEMYTEAM_ENEMY_TEAM = 1;
    public static final int CMD_ADD_ENEMYTEAM_WEIGHT = 2;
    public static final int CMD_COUNT_ADD_ENEMYTEAM = 3;

    //ACTIVE_ENEMY
    public static final int SCHEMA_ACTIVE_ENEMY = 36;
    public static final int CMD_ACTIVE_ENEMY_ONOFF = 0;
    public static final int CMD_COUNT_ACTIVE_ENEMY = 1;

    //SET_EMOTION
    public static final int SCHEMA_SET_EMOTION = 37;
    public static final int CMD_SET_EMOTION_ACTOR_ID = 0;
    public static final int CMD_SET_EMOTION_EMO = 1;
    public static final int CMD_SET_EMOTION_TIME = 2;
    public static final int CMD_COUNT_SET_EMOTION = 3;

    //GET_ACTOR_BV
    public static final int SCHEMA_GET_ACTOR_BV = 38;
    public static final int CMD_GET_ACTOR_BV_ACTOR_ID = 0;
    public static final int CMD_GET_ACTOR_BV_PROPERTY = 1;
    public static final int CMD_GET_ACTOR_BV_RESULT = 2;
    public static final int CMD_COUNT_GET_ACTOR_BV = 3;

    //SET_ACTORP_BV
    public static final int SCHEMA_SET_ACTORP_BV = 39;
    public static final int CMD_SET_ACTORP_BV_ACTOR_ID = 0;
    public static final int CMD_SET_ACTORP_BV_PROPERTY = 1;
    public static final int CMD_SET_ACTORP_BV_VALUE = 2;
    public static final int CMD_COUNT_SET_ACTORP_BV = 3;

    //OPENCHEST
    public static final int SCHEMA_OPENCHEST = 40;
    public static final int CMD_OPENCHEST_ACTOR_ID = 0;
    public static final int CMD_OPENCHEST_CHEST_TYPE = 1;
    public static final int CMD_COUNT_OPENCHEST = 2;

    //BC_DIALOG
    public static final int SCHEMA_BC_DIALOG = 41;
    public static final int CMD_BC_DIALOG_PROTRAIT_ID = 0;
    public static final int CMD_BC_DIALOG_PROTRAIT_POS_H = 1;
    public static final int CMD_BC_DIALOG_PROTRAIT_POS_V = 2;
    public static final int CMD_BC_DIALOG_MESSAGE = 3;
    public static final int CMD_BC_DIALOG_ROUND = 4;
    public static final int CMD_BC_DIALOG_STATE = 5;
    public static final int CMD_BC_DIALOG_EXPRESSION = 6;
    public static final int CMD_COUNT_BC_DIALOG = 7;

    //BC_ACTION
    public static final int SCHEMA_BC_ACTION = 42;
    public static final int CMD_BC_ACTION_ROUND = 0;
    public static final int CMD_BC_ACTION_ACTION = 1;
    public static final int CMD_BC_ACTION_AIM = 2;
    public static final int CMD_BC_ACTION_PARA0 = 3;
    public static final int CMD_BC_ACTION_PARA1 = 4;
    public static final int CMD_BC_ACTION_PARA2 = 5;
    public static final int CMD_BC_ACTION_PARA3 = 6;
    public static final int CMD_COUNT_BC_ACTION = 7;

    //BC_ANIM
    public static final int SCHEMA_BC_ANIM = 43;
    public static final int CMD_BC_ANIM_ROUND = 0;
    public static final int CMD_BC_ANIM_ACTOR = 1;
    public static final int CMD_BC_ANIM_ACTION = 2;
    public static final int CMD_BC_ANIM_X = 3;
    public static final int CMD_BC_ANIM_Y = 4;
    public static final int CMD_COUNT_BC_ANIM = 5;

    //BC_LIMIT
    public static final int SCHEMA_BC_LIMIT = 44;
    public static final int CMD_BC_LIMIT_ROUND = 0;
    public static final int CMD_BC_LIMIT_ACTION = 1;
    public static final int CMD_BC_LIMIT_AIM = 2;
    public static final int CMD_BC_LIMIT_ACTIONID = 3;
    public static final int CMD_BC_LIMIT_PARA1 = 4;
    public static final int CMD_BC_LIMIT_PARA2 = 5;
    public static final int CMD_BC_LIMIT_PARA3 = 6;
    public static final int CMD_COUNT_BC_LIMIT = 7;

    //BC_LIMIT_INFO
    public static final int SCHEMA_BC_LIMIT_INFO = 45;
    public static final int CMD_BC_LIMIT_INFO_PROTRAIT_ID = 0;
    public static final int CMD_BC_LIMIT_INFO_PROTRAIT_POS_H = 1;
    public static final int CMD_BC_LIMIT_INFO_PROTRAIT_POS_V = 2;
    public static final int CMD_BC_LIMIT_INFO_MESSAGE = 3;
    public static final int CMD_BC_LIMIT_INFO_ROUND = 4;
    public static final int CMD_BC_LIMIT_INFO_STATE = 5;
    public static final int CMD_BC_LIMIT_INFO_EXPRESSION = 6;
    public static final int CMD_COUNT_BC_LIMIT_INFO = 7;

    //RECOVERY
    public static final int SCHEMA_RECOVERY = 46;
    public static final int CMD_COUNT_RECOVERY = 0;

    //CHANGE_BATTLE_BG
    public static final int SCHEMA_CHANGE_BATTLE_BG = 47;
    public static final int CMD_CHANGE_BATTLE_BG_BG_ID = 0;
    public static final int CMD_COUNT_CHANGE_BATTLE_BG = 1;

    //GET_EQUIP
    public static final int SCHEMA_GET_EQUIP = 48;
    public static final int CMD_GET_EQUIP_LA = 0;
    public static final int CMD_GET_EQUIP_PART = 1;
    public static final int CMD_GET_EQUIP_RESULT = 2;
    public static final int CMD_COUNT_GET_EQUIP = 3;

    //SET_EQUIP
    public static final int SCHEMA_SET_EQUIP = 49;
    public static final int CMD_SET_EQUIP_LA = 0;
    public static final int CMD_SET_EQUIP_PART = 1;
    public static final int CMD_SET_EQUIP_EQUIP = 2;
    public static final int CMD_SET_EQUIP_CREATE = 3;
    public static final int CMD_COUNT_SET_EQUIP = 4;

    //GET_ACTOR_PROPERTY
    public static final int SCHEMA_GET_ACTOR_PROPERTY = 50;
    public static final int CMD_GET_ACTOR_PROPERTY_ACTOR_ID = 0;
    public static final int CMD_GET_ACTOR_PROPERTY_PROPERTY = 1;
    public static final int CMD_GET_ACTOR_PROPERTY_RESULT = 2;
    public static final int CMD_COUNT_GET_ACTOR_PROPERTY = 3;

    //SET_ACTOR_PROPERTY
    public static final int SCHEMA_SET_ACTOR_PROPERTY = 51;
    public static final int CMD_SET_ACTOR_PROPERTY_ACTOR_ID = 0;
    public static final int CMD_SET_ACTOR_PROPERTY_PROPERTY = 1;
    public static final int CMD_SET_ACTOR_PROPERTY_VALUE = 2;
    public static final int CMD_COUNT_SET_ACTOR_PROPERTY = 3;

    //SET_MUSIC
    public static final int SCHEMA_SET_MUSIC = 52;
    public static final int CMD_SET_MUSIC_ID = 0;
    public static final int CMD_SET_MUSIC_TYPE = 1;
    public static final int CMD_COUNT_SET_MUSIC = 2;

    //SHOCK_SCREEN
    public static final int SCHEMA_SHOCK_SCREEN = 53;
    public static final int CMD_SHOCK_SCREEN_MX = 0;
    public static final int CMD_SHOCK_SCREEN_MY = 1;
    public static final int CMD_SHOCK_SCREEN_VX = 2;
    public static final int CMD_SHOCK_SCREEN_VY = 3;
    public static final int CMD_SHOCK_SCREEN_TIME = 4;
    public static final int CMD_COUNT_SHOCK_SCREEN = 5;

    //CHANGE_PROPERTY
    public static final int SCHEMA_CHANGE_PROPERTY = 54;
    public static final int CMD_CHANGE_PROPERTY_ACTOR = 0;
    public static final int CMD_CHANGE_PROPERTY_PARA = 1;
    public static final int CMD_CHANGE_PROPERTY_VALUE = 2;
    public static final int CMD_COUNT_CHANGE_PROPERTY = 3;

    //MENU_CONTROL
    public static final int SCHEMA_MENU_CONTROL = 55;
    public static final int CMD_MENU_CONTROL_ACTION = 0;
    public static final int CMD_MENU_CONTROL_PARA = 1;
    public static final int CMD_COUNT_MENU_CONTROL = 2;

    //DEVICE_VIBRATE
    public static final int SCHEMA_DEVICE_VIBRATE = 56;
    public static final int CMD_DEVICE_VIBRATE_TIME = 0;
    public static final int CMD_COUNT_DEVICE_VIBRATE = 1;

    //PACKAGE_CONTROL
    public static final int SCHEMA_PACKAGE_CONTROL = 57;
    public static final int CMD_PACKAGE_CONTROL_TYPE = 0;
    public static final int CMD_PACKAGE_CONTROL_ID = 1;
    public static final int CMD_PACKAGE_CONTROL_COUNT = 2;
    public static final int CMD_COUNT_PACKAGE_CONTROL = 3;

    //GET_GAMEDATA
    public static final int SCHEMA_GET_GAMEDATA = 58;
    public static final int CMD_GET_GAMEDATA_TYPE = 0;
    public static final int CMD_GET_GAMEDATA_RESULT = 1;
    public static final int CMD_COUNT_GET_GAMEDATA = 2;

    //SET_HERO_ANIM
    public static final int SCHEMA_SET_HERO_ANIM = 59;
    public static final int CMD_SET_HERO_ANIM_ACTOR = 0;
    public static final int CMD_SET_HERO_ANIM_ANIM = 1;
    public static final int CMD_COUNT_SET_HERO_ANIM = 2;

    //UI_DIALOG
    public static final int SCHEMA_UI_DIALOG = 60;
    public static final int CMD_UI_DIALOG_PROTRAIT_ID = 0;
    public static final int CMD_UI_DIALOG_PROTRAIT_POS_H = 1;
    public static final int CMD_UI_DIALOG_PROTRAIT_POS_V = 2;
    public static final int CMD_UI_DIALOG_MESSAGE = 3;
    public static final int CMD_UI_DIALOG_EXPRESSION = 4;
    public static final int CMD_COUNT_UI_DIALOG = 5;

    //UI_LIMIT
    public static final int SCHEMA_UI_LIMIT = 61;
    public static final int CMD_UI_LIMIT_CURSOR = 0;
    public static final int CMD_COUNT_UI_LIMIT = 1;

    //UI_LIMIT_INFO
    public static final int SCHEMA_UI_LIMIT_INFO = 62;
    public static final int CMD_UI_LIMIT_INFO_PROTRAIT_ID = 0;
    public static final int CMD_UI_LIMIT_INFO_PROTRAIT_POS_H = 1;
    public static final int CMD_UI_LIMIT_INFO_PROTRAIT_POS_V = 2;
    public static final int CMD_UI_LIMIT_INFO_MESSAGE = 3;
    public static final int CMD_UI_LIMIT_INFO_EXPRESSION = 4;
    public static final int CMD_COUNT_UI_LIMIT_INFO = 5;

    //UI_ANIM
    public static final int SCHEMA_UI_ANIM = 63;
    public static final int CMD_UI_ANIM_ACTOR = 0;
    public static final int CMD_UI_ANIM_ACTION = 1;
    public static final int CMD_UI_ANIM_X = 2;
    public static final int CMD_UI_ANIM_Y = 3;
    public static final int CMD_COUNT_UI_ANIM = 4;

    //UI_WAIT
    public static final int SCHEMA_UI_WAIT = 64;
    public static final int CMD_UI_WAIT_TIME = 0;
    public static final int CMD_COUNT_UI_WAIT = 1;

    //SET_PORTRAIT
    public static final int SCHEMA_SET_PORTRAIT = 65;
    public static final int CMD_SET_PORTRAIT_ID = 0;
    public static final int CMD_SET_PORTRAIT_SHOW = 1;
    public static final int CMD_COUNT_SET_PORTRAIT = 2;

    //BOARD
    public static final int SCHEMA_BOARD = 66;
    public static final int CMD_BOARD_MESSAGE = 0;
    public static final int CMD_BOARD_SHOW_BG = 1;
    public static final int CMD_BOARD_SCROLL = 2;
    public static final int CMD_BOARD_WIDTH = 3;
    public static final int CMD_BOARD_LINE = 4;
    public static final int CMD_COUNT_BOARD = 5;

    //SET_UI_ACTION
    public static final int SCHEMA_SET_UI_ACTION = 67;
    public static final int CMD_SET_UI_ACTION_TYPE = 0;
    public static final int CMD_SET_UI_ACTION_ACTION = 1;
    public static final int CMD_COUNT_SET_UI_ACTION = 2;

    //PORTABLE_SHOP
    public static final int SCHEMA_PORTABLE_SHOP = 68;
    public static final int CMD_PORTABLE_SHOP_ID = 0;
    public static final int CMD_PORTABLE_SHOP_TYPE = 1;
    public static final int CMD_COUNT_PORTABLE_SHOP = 2;

    //TRANSPORT
    public static final int SCHEMA_TRANSPORT = 69;
    public static final int CMD_TRANSPORT_STATE = 0;
    public static final int CMD_COUNT_TRANSPORT = 1;

    //FACE_TO
    public static final int SCHEMA_FACE_TO = 70;
    public static final int CMD_COUNT_FACE_TO = 0;

    //WAIT_KEY
    public static final int SCHEMA_WAIT_KEY = 71;
    public static final int CMD_WAIT_KEY_KEY = 0;
    public static final int CMD_WAIT_KEY_TIMER = 1;
    public static final int CMD_COUNT_WAIT_KEY = 2;

    //SET_FOLLOW_TEAM
    public static final int SCHEMA_SET_FOLLOW_TEAM = 72;
    public static final int CMD_SET_FOLLOW_TEAM_ACTOR = 0;
    public static final int CMD_SET_FOLLOW_TEAM_STATE = 1;
    public static final int CMD_SET_FOLLOW_TEAM_LOCATION = 2;
    public static final int CMD_COUNT_SET_FOLLOW_TEAM = 3;


    public static final int[] CMD_PARALEN = {//@in X:\RPG22_CMCC\trunk\SourceCode\Const\MapMessage.h
    7, // IF
    0, // ENDIF
    0, // RETURN
    5, // SET
    4, // LOADMAP
    3, // SETACTORPOS
    2, // CONTROLACTORSHOW
    6, // DIALOGUE
    3, // MOVE
    1, // WAITMOVEFINISH
    3, // MOVESCREEN
    2, // SETCAMERAFOCUES
    4, // CHANGEACTION
    4, // MOVEACTOR
    1, // WAIT
    0, // ELSE
    8, // CHOOSEDIALOGUE
    3, // TEAMCONTROL
    3, // SCREENCONTROL
    2, // PACKAGEITEM
    3, // SKILLCONTROL
    3, // FIGHT
    0, // END_GAMG
    0, // GAME_OVER
    2, // MESSAGE
    2, // GETPACKAGEINFO
    4, // MISSION
    4, // TIMERCONTROL
    1, // TAKECONTROL
    3, // CHEST
    6, // FLASH
    5, // CAMERA_COLOR
    2, // SET_Z
    5, // SET_VELOCITY
    3, // SET_ENEMY
    3, // ADD_ENEMYTEAM
    1, // ACTIVE_ENEMY
    3, // SET_EMOTION
    3, // GET_ACTOR_BV
    3, // SET_ACTORP_BV
    2, // OPENCHEST
    7, // BC_DIALOG
    7, // BC_ACTION
    5, // BC_ANIM
    7, // BC_LIMIT
    7, // BC_LIMIT_INFO
    0, // RECOVERY
    1, // CHANGE_BATTLE_BG
    3, // GET_EQUIP
    4, // SET_EQUIP
    3, // GET_ACTOR_PROPERTY
    3, // SET_ACTOR_PROPERTY
    2, // SET_MUSIC
    5, // SHOCK_SCREEN
    3, // CHANGE_PROPERTY
    2, // MENU_CONTROL
    1, // DEVICE_VIBRATE
    3, // PACKAGE_CONTROL
    2, // GET_GAMEDATA
    2, // SET_HERO_ANIM
    5, // UI_DIALOG
    1, // UI_LIMIT
    5, // UI_LIMIT_INFO
    4, // UI_ANIM
    1, // UI_WAIT
    2, // SET_PORTRAIT
    5, // BOARD
    2, // SET_UI_ACTION
    2, // PORTABLE_SHOP
    1, // TRANSPORT
    0, // FACE_TO
    2, // WAIT_KEY
    3, // SET_FOLLOW_TEAM
    };
//@@

//@@../Const/items.h
    final static int ENUM_ITEMS_TYPE_ITEM = 0;
    final static int ENUM_ITEMS_TYPE_BOOK = 1;
    final static int ENUM_ITEMS_TYPE_SPECIAL = 2;
    final static int AMOUNT_ENUM_ITEMS_TYPE = 3;

    final static int ENUM_ITEMS_USETARGET_ALLY = 0;
    final static int ENUM_ITEMS_USETARGET_FOE = 1;
    final static int AMOUNT_ENUM_ITEMS_USETARGET = 2;

    final static int ENUM_ITEMS_CANSELL_YES = 0;
    final static int ENUM_ITEMS_CANSELL_NO = 1;
    final static int AMOUNT_ENUM_ITEMS_CANSELL = 2;

    final static int ENUM_ITEMS_WHENCANUSE_ALL = 0;
    final static int ENUM_ITEMS_WHENCANUSE_FIGHT = 1;
    final static int ENUM_ITEMS_WHENCANUSE_MENU = 2;
    final static int ENUM_ITEMS_WHENCANUSE_NONE = 3;
    final static int AMOUNT_ENUM_ITEMS_WHENCANUSE = 4;

    final static int ENUM_ITEMS_COLOR_BLACK = 0;
    final static int ENUM_ITEMS_COLOR_WHITE = 1;
    final static int ENUM_ITEMS_COLOR_RED = 2;
    final static int AMOUNT_ENUM_ITEMS_COLOR = 3;

    final static int ENUM_ITEMS_MESSAGE_NONE = 0;
    final static int ENUM_ITEMS_MESSAGE_VALUE = 1;
    final static int AMOUNT_ENUM_ITEMS_MESSAGE = 2;

    final static int DB_ITEMS_TYPE = 0;
    final static int DB_ITEMS_BUY = 1;
    final static int DB_ITEMS_SELL = 2;
    final static int DB_ITEMS_TOKEN = 3;
    final static int DB_ITEMS_USETARGET = 4;
    final static int DB_ITEMS_TARGETCOUNT = 5;
    final static int DB_ITEMS_CANSELL = 6;
    final static int DB_ITEMS_WHENCANUSE = 7;
    final static int DB_ITEMS_PURPOSE0 = 8;
    final static int DB_ITEMS_VALUE0 = 9;
    final static int DB_ITEMS_PURPOSE1 = 10;
    final static int DB_ITEMS_VALUE1 = 11;
    final static int DB_ITEMS_PURPOSE_LAST = 12;
    final static int DB_ITEMS_VALUE2 = 13;
    final static int DB_ITEMS_FRAMEINFO = 14;
    final static int DB_ITEMS_LIMIT = 15;
    final static int DB_ITEMS_SUITABLE = 16;
    final static int DB_ITEMS_COLOR = 17;
    final static int DB_ITEMS_ACT_TIME = 18;
    final static int DB_ITEMS_ICON = 19;
    final static int DB_ITEMS_MESSAGE = 20;

    final static int AMOUNT_DB_ITEMS = 67;
//@@
//@@../Const/equips.h
    final static int ENUM_EQUIPS_TYPE_WEAPON = 0;
    final static int ENUM_EQUIPS_TYPE_ARMOR = 1;
    final static int ENUM_EQUIPS_TYPE_BOOT = 2;
    final static int ENUM_EQUIPS_TYPE_OTHER = 3;
    final static int AMOUNT_ENUM_EQUIPS_TYPE = 4;

    final static int ENUM_EQUIPS_PURPOSE0_SP = 0;
    final static int ENUM_EQUIPS_PURPOSE0_HP = 1;
    final static int ENUM_EQUIPS_PURPOSE0_MP = 2;
    final static int ENUM_EQUIPS_PURPOSE0_NORMAL_AP = 3;
    final static int ENUM_EQUIPS_PURPOSE0_SKILL_AP = 4;
    final static int ENUM_EQUIPS_PURPOSE0_DEFENSE = 5;
    final static int ENUM_EQUIPS_PURPOSE0_CRITICAL = 6;
    final static int ENUM_EQUIPS_PURPOSE0_DODGE = 7;
    final static int ENUM_EQUIPS_PURPOSE0_SPEED = 8;
    final static int ENUM_EQUIPS_PURPOSE0_TARGET_COUNT = 9;
    final static int ENUM_EQUIPS_PURPOSE0_SKILL = 10;
    final static int ENUM_EQUIPS_PURPOSE0_NONE = 11;
    final static int AMOUNT_ENUM_EQUIPS_PURPOSE0 = 12;

    final static int ENUM_EQUIPS_CANSELL_YES = 0;
    final static int ENUM_EQUIPS_CANSELL_NO = 1;
    final static int AMOUNT_ENUM_EQUIPS_CANSELL = 2;

    final static int ENUM_EQUIPS_COLOR_BLACK = 0;
    final static int ENUM_EQUIPS_COLOR_WHITE = 1;
    final static int ENUM_EQUIPS_COLOR_RED = 2;
    final static int AMOUNT_ENUM_EQUIPS_COLOR = 3;

    final static int ENUM_EQUIPS_DISAPPEAR_TRUE = 0;
    final static int ENUM_EQUIPS_DISAPPEAR_FALSE = 1;
    final static int AMOUNT_ENUM_EQUIPS_DISAPPEAR = 2;

    final static int DB_EQUIPS_TYPE = 0;
    final static int DB_EQUIPS_PURPOSE0 = 1;
    final static int DB_EQUIPS_VALUE0 = 2;
    final static int DB_EQUIPS_PURPOSE1 = 3;
    final static int DB_EQUIPS_VALUE1 = 4;
    final static int DB_EQUIPS_PURPOSE_LAST = 5;
    final static int DB_EQUIPS_VALUE2 = 6;
    final static int DB_EQUIPS_BUY = 7;
    final static int DB_EQUIPS_SELL = 8;
    final static int DB_EQUIPS_TOKEN = 9;
    final static int DB_EQUIPS_NEEDLV = 10;
    final static int DB_EQUIPS_SUITABLE = 11;
    final static int DB_EQUIPS_CANSELL = 12;
    final static int DB_EQUIPS_LEVEL = 13;
    final static int DB_EQUIPS_NEXTLVNAME = 14;
    final static int DB_EQUIPS_LIMIT = 15;
    final static int DB_EQUIPS_COLOR = 16;
    final static int DB_EQUIPS_PORTFOLIO = 17;
    final static int DB_EQUIPS_COUNT = 18;
    final static int DB_EQUIPS_ICON = 19;
    final static int DB_EQUIPS_DISAPPEAR = 20;

    final static int AMOUNT_DB_EQUIPS = 60;
//@@
//@@../Const/tasks.h
    final static int ENUM_TASKS_COLOR_BLACK = 0;
    final static int ENUM_TASKS_COLOR_WHITE = 1;
    final static int ENUM_TASKS_COLOR_RED = 2;
    final static int AMOUNT_ENUM_TASKS_COLOR = 3;

    final static int DB_TASKS_ICON = 0;
    final static int DB_TASKS_COLOR = 1;

    final static int AMOUNT_DB_TASKS = 68;
//@@
//@@../Const/skills.h
    final static int ENUM_SKILLS_USETARGET_ALLY = 0;
    final static int ENUM_SKILLS_USETARGET_FOE = 1;
    final static int ENUM_SKILLS_USETARGET_SELF = 2;
    final static int AMOUNT_ENUM_SKILLS_USETARGET = 3;

    final static int ENUM_SKILLS_COLOR_BLACK = 0;
    final static int ENUM_SKILLS_COLOR_WHITE = 1;
    final static int ENUM_SKILLS_COLOR_RED = 2;
    final static int AMOUNT_ENUM_SKILLS_COLOR = 3;

    final static int DB_SKILLS_USETARGET = 0;
    final static int DB_SKILLS_TARGETCOUNT = 1;
    final static int DB_SKILLS_USEMP = 2;
    final static int DB_SKILLS_USESP = 3;
    final static int DB_SKILLS_CRITICAL = 4;
    final static int DB_SKILLS_COOLDOWN = 5;
    final static int DB_SKILLS_PURPOSE0 = 6;
    final static int DB_SKILLS_VALUE0 = 7;
    final static int DB_SKILLS_PRO0 = 8;
    final static int DB_SKILLS_PURPOSE1 = 9;
    final static int DB_SKILLS_VALUE1 = 10;
    final static int DB_SKILLS_PRO1 = 11;
    final static int DB_SKILLS_PURPOSE_LAST = 12;
    final static int DB_SKILLS_VALUE2 = 13;
    final static int DB_SKILLS_PRO2 = 14;
    final static int DB_SKILLS_ACT_TIME = 15;
    final static int DB_SKILLS_NEXTLVNAME = 16;
    final static int DB_SKILLS_SKILL_EX = 17;
    final static int DB_SKILLS_FRAMEINFO = 18;
    final static int DB_SKILLS_SKILL_LV = 19;
    final static int DB_SKILLS_LEARN_LV = 20;
    final static int DB_SKILLS_MONEY = 21;
    final static int DB_SKILLS_TOKEN = 22;
    final static int DB_SKILLS_COLOR = 23;
    final static int DB_SKILLS_ICON = 24;

    final static int AMOUNT_DB_SKILLS = 75;
//@@
//@@../Const/heroes.h
    final static int DB_HEROES_SKILL = 0;
    final static int DB_HEROES_EXP = 1;
    final static int DB_HEROES_HP = 2;
    final static int DB_HEROES_MP = 3;
    final static int DB_HEROES_SP = 4;
    final static int DB_HEROES_NORMAL_AP = 5;
    final static int DB_HEROES_SKILL_AP = 6;
    final static int DB_HEROES_DEFENSE = 7;
    final static int DB_HEROES_CRITICAL = 8;
    final static int DB_HEROES_DODGE = 9;
    final static int DB_HEROES_SPEED = 10;
    final static int DB_HEROES_SP_INCREASE = 11;
    final static int DB_HEROES_FRAMEINFO = 12;

    final static int AMOUNT_DB_HEROES = 298;
//@@
//@@../Const/GameInfo.h
    final static int ENUM_GAMEINFO_TAKE_OFF_TRUE = 0;
    final static int ENUM_GAMEINFO_TAKE_OFF_FALSE = 1;
    final static int AMOUNT_ENUM_GAMEINFO_TAKE_OFF = 2;

    final static int DB_GAMEINFO_TARGET_COUNT = 0;
    final static int DB_GAMEINFO_WIDTH = 1;
    final static int DB_GAMEINFO_HEIGHT = 2;
    final static int DB_GAMEINFO_TRANS_WIDTH = 3;
    final static int DB_GAMEINFO_TRANS_HEIGHT = 4;
    final static int DB_GAMEINFO_BA_HEIGHT = 5;
    final static int DB_GAMEINFO_LEVEL = 6;
    final static int DB_GAMEINFO_TAKE_OFF = 7;
    final static int DB_GAMEINFO_PROFIC0 = 8;
    final static int DB_GAMEINFO_PROFIC1 = 9;
    final static int DB_GAMEINFO_PROFIC2 = 10;
    final static int DB_GAMEINFO_PROFIC3 = 11;
    final static int DB_GAMEINFO_PROFIC4 = 12;
    final static int DB_GAMEINFO_PROFIC_LAST = 13;

    final static int AMOUNT_DB_GAMEINFO = 4;
//@@
//@@../Const/enemies.h
    final static int ENUM_ENEMIES_DEOPPRO2_ = 0;
    final static int AMOUNT_ENUM_ENEMIES_DEOPPRO2 = 1;

    final static int DB_ENEMIES_TYPE = 0;
    final static int DB_ENEMIES_ACTORNAME = 1;
    final static int DB_ENEMIES_LEVEL = 2;
    final static int DB_ENEMIES_GOLD = 3;
    final static int DB_ENEMIES_TOKEN = 4;
    final static int DB_ENEMIES_EXP = 5;
    final static int DB_ENEMIES_SCORE = 6;
    final static int DB_ENEMIES_HP = 7;
    final static int DB_ENEMIES_MP = 8;
    final static int DB_ENEMIES_NORMAL_AP = 9;
    final static int DB_ENEMIES_SKILL_AP = 10;
    final static int DB_ENEMIES_DEFENSE = 11;
    final static int DB_ENEMIES_CRITICAL = 12;
    final static int DB_ENEMIES_DODGE = 13;
    final static int DB_ENEMIES_SPEED = 14;
    final static int DB_ENEMIES_SKILL1 = 15;
    final static int DB_ENEMIES_SKILLPRO1 = 16;
    final static int DB_ENEMIES_SKILL2 = 17;
    final static int DB_ENEMIES_SKILL1PRO2 = 18;
    final static int DB_ENEMIES_SKILL3 = 19;
    final static int DB_ENEMIES_SKILL1PRO3 = 20;
    final static int DB_ENEMIES_DROP0 = 21;
    final static int DB_ENEMIES_DEOPPRO0 = 22;
    final static int DB_ENEMIES_DROP1 = 23;
    final static int DB_ENEMIES_DEOPPRO1 = 24;
    final static int DB_ENEMIES_DROP2 = 25;
    final static int DB_ENEMIES_DEOPPRO2 = 26;
    final static int DB_ENEMIES_DROP3 = 27;
    final static int DB_ENEMIES_DEOPPRO3 = 28;
    final static int DB_ENEMIES_DROP4 = 29;
    final static int DB_ENEMIES_DEOPPRO4 = 30;
    final static int DB_ENEMIES_DROP5 = 31;
    final static int DB_ENEMIES_DEOPPRO5 = 32;
    final static int DB_ENEMIES_DROP6 = 33;
    final static int DB_ENEMIES_DEOPPRO6 = 34;
    final static int DB_ENEMIES_HEIGHT = 35;
    final static int DB_ENEMIES_FRAMEINFO = 36;
    final static int DB_ENEMIES_AVOIDBUFF = 37;

    final static int DB_ENEMIES_TYPE_P = 0;
    final static int DB_ENEMIES_TYPE_A = 1;
    final static int AMOUNT_DB_ENEMIES_TYPE = 2;

    final static int AMOUNT_DB_ENEMIES = 54;
//@@
//@@../Const/enemyTeams.h
    final static int DB_ENEMYTEAMS_TYPE = 0;
    final static int DB_ENEMYTEAMS_MEMBER0 = 1;
    final static int DB_ENEMYTEAMS_MEMBER1 = 2;
    final static int DB_ENEMYTEAMS_MEMBER2 = 3;
    final static int DB_ENEMYTEAMS_MEMBER3 = 4;
    final static int DB_ENEMYTEAMS_MEMBER4 = 5;

    final static int AMOUNT_DB_ENEMYTEAMS = 104;
//@@
//@@../Const/EFFECT_SET.h
    final static int DB_EFFECT_SET_PURPOSE0 = 0;
    final static int DB_EFFECT_SET_VALUE0 = 1;
    final static int DB_EFFECT_SET_PURPOSE1 = 2;
    final static int DB_EFFECT_SET_VALUE1 = 3;
    final static int DB_EFFECT_SET_PURPOSE_LAST = 4;
    final static int DB_EFFECT_SET_VALUE2 = 5;
    final static int DB_EFFECT_SET_ICON = 6;
    final static int DB_EFFECT_SET_ENUMERATE = 7;

    final static int DB_EFFECT_SET_ENUMERATE_NONE = 0;
    final static int DB_EFFECT_SET_ENUMERATE_HP = 1;
    final static int DB_EFFECT_SET_ENUMERATE_HPP = 2;
    final static int DB_EFFECT_SET_ENUMERATE_MP = 3;
    final static int DB_EFFECT_SET_ENUMERATE_MPP = 4;
    final static int DB_EFFECT_SET_ENUMERATE_RISE = 5;
    final static int DB_EFFECT_SET_ENUMERATE_DAMAGE = 6;
    final static int DB_EFFECT_SET_ENUMERATE_ANIMATION = 7;
    final static int DB_EFFECT_SET_ENUMERATE_DAMAGEP = 8;
    final static int DB_EFFECT_SET_ENUMERATE_POISON = 9;
    final static int DB_EFFECT_SET_ENUMERATE_HP_TOP = 10;
    final static int DB_EFFECT_SET_ENUMERATE_MP_TOP = 11;
    final static int DB_EFFECT_SET_ENUMERATE_DEFENSE = 12;
    final static int DB_EFFECT_SET_ENUMERATE_CHAOS = 13;
    final static int DB_EFFECT_SET_ENUMERATE_PARALYZE = 14;
    final static int DB_EFFECT_SET_ENUMERATE_CHP = 15;
    final static int DB_EFFECT_SET_ENUMERATE_NAP = 16;
    final static int DB_EFFECT_SET_ENUMERATE_DODGE = 17;
    final static int DB_EFFECT_SET_ENUMERATE_SAP = 18;
    final static int DB_EFFECT_SET_ENUMERATE_CLEAR = 19;
    final static int DB_EFFECT_SET_ENUMERATE_DISPERSE = 20;
    final static int DB_EFFECT_SET_ENUMERATE_SPEED = 21;
    final static int DB_EFFECT_SET_ENUMERATE_SKILL = 22;
    final static int DB_EFFECT_SET_ENUMERATE_SKILL_EX = 23;
    final static int DB_EFFECT_SET_ENUMERATE_SP_FORBID = 24;
    final static int DB_EFFECT_SET_ENUMERATE_FRAME_INFO = 25;
    final static int DB_EFFECT_SET_ENUMERATE_EXTRA_SP = 26;
    final static int DB_EFFECT_SET_ENUMERATE_EXTRA_HP = 27;
    final static int DB_EFFECT_SET_ENUMERATE_EXTRA_MP = 28;
    final static int DB_EFFECT_SET_ENUMERATE_EXTRA_NAP = 29;
    final static int DB_EFFECT_SET_ENUMERATE_EXTRA_SAP = 30;
    final static int DB_EFFECT_SET_ENUMERATE_EXTRA_DEFENSE = 31;
    final static int DB_EFFECT_SET_ENUMERATE_EXTRA_CRITICAL = 32;
    final static int DB_EFFECT_SET_ENUMERATE_EXTRA_DODGE = 33;
    final static int DB_EFFECT_SET_ENUMERATE_EXTRA_SPEED = 34;
    final static int DB_EFFECT_SET_ENUMERATE_ICON_WEAK = 35;
    final static int DB_EFFECT_SET_ENUMERATE_ICON_POISON = 36;
    final static int DB_EFFECT_SET_ENUMERATE_ICON_DIZZINESS = 37;
    final static int DB_EFFECT_SET_ENUMERATE_ICON_STRONG = 38;
    final static int DB_EFFECT_SET_ENUMERATE_ICON_KILL = 39;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_POISON = 40;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_WEAK = 41;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_PENETRATION = 42;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_PARALYZE = 43;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_SLOW1 = 44;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_SLOW2 = 45;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_SLOW3 = 46;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_SLOW4 = 47;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_CHAOS = 48;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_STRONG1 = 49;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_STRONG2 = 50;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_STRONG3 = 51;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_SOLID = 52;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_CRIT1 = 53;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_CRIT2 = 54;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_CRIT3 = 55;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_DODGE1 = 56;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_DODGE2 = 57;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_SPEED = 58;
    final static int DB_EFFECT_SET_ENUMERATE_BUF_WAKE = 59;
    final static int AMOUNT_DB_EFFECT_SET_ENUMERATE = 60;

    final static int AMOUNT_DB_EFFECT_SET = 71;
//@@
//@@../Const/FrameInfo.h
    final static int FRAME_ENUM_PURPOSE_DAMAGE = 0;
    final static int FRAME_ENUM_PURPOSE_SCREEN_COLOR = 1;
    final static int FRAME_ENUM_PURPOSE_SHOCK = 2;
    final static int FRAME_ENUM_PURPOSE_EFFECT = 3;
    final static int FRAME_ENUM_PURPOSE_VIBRATE = 4;
    final static int FRAME_ENUM_PURPOSE_HIDE_UI = 5;
    final static int FRAME_ENUM_PURPOSE_RUSH = 6;
    final static int FRAME_ENUM_PURPOSE_ATTACK_ANIM = 7;
//@@
//@@../Const/mixtrue.h
    final static int ENUM_MIXTRUE_TYPE_NORMAL = 0;
    final static int ENUM_MIXTRUE_TYPE_STAR = 1;
    final static int AMOUNT_ENUM_MIXTRUE_TYPE = 2;

    final static int ENUM_MIXTRUE_COLOR_BLACK = 0;
    final static int ENUM_MIXTRUE_COLOR_WHITE = 1;
    final static int ENUM_MIXTRUE_COLOR_RED = 2;
    final static int AMOUNT_ENUM_MIXTRUE_COLOR = 3;

    final static int DB_MIXTRUE_TYPE = 0;
    final static int DB_MIXTRUE_COST = 1;
    final static int DB_MIXTRUE_LEVEL = 2;
    final static int DB_MIXTRUE_EXP = 3;
    final static int DB_MIXTRUE_ENTITY_0 = 4;
    final static int DB_MIXTRUE_VALUE_0 = 5;
    final static int DB_MIXTRUE_ENTITY_1 = 6;
    final static int DB_MIXTRUE_VALUE_1 = 7;
    final static int DB_MIXTRUE_ENTITY_2 = 8;
    final static int DB_MIXTRUE_VALUE_2 = 9;
    final static int DB_MIXTRUE_ENTITY_LAST = 10;
    final static int DB_MIXTRUE_VALUE_LAST = 11;
    final static int DB_MIXTRUE_AWARD_0 = 12;
    final static int DB_MIXTRUE_COUNT_0 = 13;
    final static int DB_MIXTRUE_AWARD_LAST = 14;
    final static int DB_MIXTRUE_COUNT_LAST = 15;
    final static int DB_MIXTRUE_COLOR = 16;
    final static int DB_MIXTRUE_ICON = 17;

    final static int AMOUNT_DB_MIXTRUE = 17;
//@@
//@@../Const/shop.h
    final static int ENUM_SHOP_COLOR_BLACK = 0;
    final static int ENUM_SHOP_COLOR_WHITE = 1;
    final static int ENUM_SHOP_COLOR_RED = 2;
    final static int AMOUNT_ENUM_SHOP_COLOR = 3;

    final static int DB_SHOP_COLOR = 0;
    final static int DB_SHOP_ICON = 1;

    final static int AMOUNT_DB_SHOP = 6;
//@@
//@@../Const/event.h
    final static int ENUM_EVENT_PURPOSE_DIALOG_L = 0;
    final static int ENUM_EVENT_PURPOSE_DIALOG_R = 1;
    final static int ENUM_EVENT_PURPOSE_MOVIE_ON = 2;
    final static int ENUM_EVENT_PURPOSE_MOVIE_OFF = 3;
    final static int ENUM_EVENT_PURPOSE_SHOCK_ON = 4;
    final static int ENUM_EVENT_PURPOSE_SHOCK_OFF = 5;
    final static int ENUM_EVENT_PURPOSE_VIBRATE = 6;
    final static int ENUM_EVENT_PURPOSE_VARIABLE = 7;
    final static int ENUM_EVENT_PURPOSE_DELETE = 8;
    final static int AMOUNT_ENUM_EVENT_PURPOSE = 9;

    final static int DB_EVENT_PURPOSE = 0;
    final static int DB_EVENT_VALUE0 = 1;
    final static int DB_EVENT_VALUE1 = 2;
    final static int DB_EVENT_NEXT = 3;

    final static int AMOUNT_DB_EVENT = 293;
//@@
//@#if EXIST_EFFORT
//@#include "../Const/effort.h"
//@#endif
//@#if SMS
//@#include "../Const/SMS.h"
//@#endif
//@@../Const/Anim.h
    //Animations count
    public static final int ANIM_NUMBER = 122;
    //animation:HERO_00
    public final static int ANIM_HERO_00 = 0;
    public final static int ACTION_HERO_00_STAND_UP = 0;
    public final static int ACTION_HERO_00_STAND_RIGHT = 1;
    public final static int ACTION_HERO_00_STAND_DOWN = 2;
    public final static int ACTION_HERO_00_STAND_LEFT = 3;
    public final static int ACTION_HERO_00_WALK_UP = 4;
    public final static int ACTION_HERO_00_WALK_RIGHT = 5;
    public final static int ACTION_HERO_00_WALK_DOWN = 6;
    public final static int ACTION_HERO_00_WALK_LEFT = 7;
    public final static int ACTION_HERO_00_TRANSPORT_UP = 8;
    public final static int ACTION_HERO_00_TRANSPORT_RIGHT = 9;
    public final static int ACTION_HERO_00_TRANSPORT_DOWN = 10;
    public final static int ACTION_HERO_00_TRANSPORT_LEFT = 11;
    public final static int ACTION_HERO_00_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_HERO_00_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_HERO_00_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_HERO_00_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_HERO_00_DIZZY = 16;
    public final static int ACTION_HERO_00_PUSHED = 17;
    public final static int ACTION_HERO_00_PULLED = 18;
    public final static int ACTION_HERO_00_HITWALL = 19;
    public final static int ACTION_HERO_00_SB = 20;
    public final static int ACTION_HERO_00_KILL = 21;
    public final static int ACTION_HERO_00_KILL_LEFT = 22;
    public final static int ACTION_HERO_00_READY = 23;
    public final static int ACTION_HERO_00_READY_RIGHT = 24;
    public final static int ACTION_HERO_00_EAT = 25;
    public final static int ACTION_HERO_00_PEE = 26;
    public final static int ACTION_HERO_00_LIE = 27;
    public final static int ACTION_HERO_00_LIE_LEFT = 28;
    public final static int ACTION_HERO_00_DEAD = 29;
    public final static int ACTION_HERO_00_DEAD_DISAPPEAR = 30;
    public final static int ACTION_HERO_00_JUMP_RIGHT_ONCE = 31;
    public final static int ACTION_HERO_00_JUMP_RIGHT_TWICE = 32;
    public final static int ACTION_HERO_00_RELEASED_LEFT = 33;
    public final static int ACTION_HERO_00_ANGRY_RIGHT = 34;
    public final static int ACTION_HERO_00_PUSHED_STAY = 35;
    public final static int ACTION_HERO_00_TEACH = 36;
    //animation:HERO_01
    public final static int ANIM_HERO_01 = 1;
    public final static int ACTION_HERO_01_STAND_UP = 0;
    public final static int ACTION_HERO_01_STAND_RIGHT = 1;
    public final static int ACTION_HERO_01_STAND_DOWN = 2;
    public final static int ACTION_HERO_01_STAND_LEFT = 3;
    public final static int ACTION_HERO_01_WALK_UP = 4;
    public final static int ACTION_HERO_01_WALK_RIGHT = 5;
    public final static int ACTION_HERO_01_WALK_DOWN = 6;
    public final static int ACTION_HERO_01_WALK_LEFT = 7;
    public final static int ACTION_HERO_01_TRANSPORT_UP = 8;
    public final static int ACTION_HERO_01_TRANSPORT_RIGHT = 9;
    public final static int ACTION_HERO_01_TRANSPORT_DOWN = 10;
    public final static int ACTION_HERO_01_TRANSPORT_LEFT = 11;
    public final static int ACTION_HERO_01_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_HERO_01_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_HERO_01_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_HERO_01_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_HERO_01_CATCHEAR = 16;
    public final static int ACTION_HERO_01_THROW = 17;
    public final static int ACTION_HERO_01_EAT = 18;
    public final static int ACTION_HERO_01_JUMP_TWICE_LEFT = 19;
    public final static int ACTION_HERO_01_JUMP_TWICE_RIGHT = 20;
    public final static int ACTION_HERO_01_JUMP_TWICE_DOWN = 21;
    public final static int ACTION_HERO_01_READY_LEFT = 22;
    public final static int ACTION_HERO_01_READY_RIGHT = 23;
    public final static int ACTION_HERO_01_ANGRY_RIGHT = 24;
    public final static int ACTION_HERO_01_ANGRY_LEFT = 25;
    public final static int ACTION_HERO_01_LAUGH = 26;
    public final static int ACTION_HERO_01_LAUGH_READY = 27;
    //animation:HERO_02
    public final static int ANIM_HERO_02 = 2;
    public final static int ACTION_HERO_02_STAND_UP = 0;
    public final static int ACTION_HERO_02_STAND_RIGHT = 1;
    public final static int ACTION_HERO_02_STAND_DOWN = 2;
    public final static int ACTION_HERO_02_STAND_LEFT = 3;
    public final static int ACTION_HERO_02_WALK_UP = 4;
    public final static int ACTION_HERO_02_WALK_RIGHT = 5;
    public final static int ACTION_HERO_02_WALK_DOWN = 6;
    public final static int ACTION_HERO_02_WALK_LEFT = 7;
    public final static int ACTION_HERO_02_TRANSPORT_UP = 8;
    public final static int ACTION_HERO_02_TRANSPORT_RIGHT = 9;
    public final static int ACTION_HERO_02_TRANSPORT_DOWN = 10;
    public final static int ACTION_HERO_02_TRANSPORT_LEFT = 11;
    public final static int ACTION_HERO_02_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_HERO_02_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_HERO_02_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_HERO_02_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_HERO_02_READY = 16;
    public final static int ACTION_HERO_02_READY_RIGHT = 17;
    public final static int ACTION_HERO_02_EAT = 18;
    public final static int ACTION_HERO_02_PLAY = 19;
    public final static int ACTION_HERO_02_SHAKE = 20;
    public final static int ACTION_HERO_02_SHAKE_STOP = 21;
    //animation:HERO_03
    public final static int ANIM_HERO_03 = 3;
    public final static int ACTION_HERO_03_STAND_UP = 0;
    public final static int ACTION_HERO_03_STAND_RIGHT = 1;
    public final static int ACTION_HERO_03_STAND_DOWN = 2;
    public final static int ACTION_HERO_03_STAND_LEFT = 3;
    public final static int ACTION_HERO_03_WALK_UP = 4;
    public final static int ACTION_HERO_03_WALK_RIGHT = 5;
    public final static int ACTION_HERO_03_WALK_DOWN = 6;
    public final static int ACTION_HERO_03_WALK_LEFT = 7;
    public final static int ACTION_HERO_03_TRANSPORT_UP = 8;
    public final static int ACTION_HERO_03_TRANSPORT_RIGHT = 9;
    public final static int ACTION_HERO_03_TRANSPORT_DOWN = 10;
    public final static int ACTION_HERO_03_TRANSPORT_LEFT = 11;
    public final static int ACTION_HERO_03_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_HERO_03_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_HERO_03_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_HERO_03_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_HERO_03_ANGRY_UP = 16;
    //animation:HERO_04
    public final static int ANIM_HERO_04 = 4;
    public final static int ACTION_HERO_04_0 = 0;
    //animation:HERO_05
    public final static int ANIM_HERO_05 = 5;
    public final static int ACTION_HERO_05_STAND_UP = 0;
    public final static int ACTION_HERO_05_STAND_RIGHT = 1;
    public final static int ACTION_HERO_05_STAND_DOWN = 2;
    public final static int ACTION_HERO_05_STAND_LEFT = 3;
    public final static int ACTION_HERO_05_WALK_UP = 4;
    public final static int ACTION_HERO_05_WALK_RIGHT = 5;
    public final static int ACTION_HERO_05_WALK_DOWN = 6;
    public final static int ACTION_HERO_05_WALK_LEFT = 7;
    public final static int ACTION_HERO_05_TRANSPORT_UP = 8;
    public final static int ACTION_HERO_05_TRANSPORT_RIGHT = 9;
    public final static int ACTION_HERO_05_TRANSPORT_DOWN = 10;
    public final static int ACTION_HERO_05_TRANSPORT_LEFT = 11;
    public final static int ACTION_HERO_05_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_HERO_05_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_HERO_05_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_HERO_05_TRANSPORT_MOVE_LEFT = 15;
    //animation:BUFFLIGHT
    public final static int ANIM_BUFFLIGHT = 6;
    public final static int ACTION_BUFFLIGHT_ICE_WALL = 0;
    public final static int ACTION_BUFFLIGHT_ICE_RAIN = 1;
    public final static int ACTION_BUFFLIGHT_NIMABI = 2;
    public final static int ACTION_BUFFLIGHT_POWER_COLUMN = 3;
    public final static int ACTION_BUFFLIGHT_RESURRECT = 4;
    public final static int ACTION_BUFFLIGHT_SLASH_1 = 5;
    public final static int ACTION_BUFFLIGHT_BUFF_LIGHT = 6;
    public final static int ACTION_BUFFLIGHT_GATHERING_POWER = 7;
    public final static int ACTION_BUFFLIGHT_CURING_ELF = 8;
    public final static int ACTION_BUFFLIGHT_CIRCLING_POWER = 9;
    public final static int ACTION_BUFFLIGHT_RISING_WIND = 10;
    public final static int ACTION_BUFFLIGHT_FALLEN_STAR = 11;
    public final static int ACTION_BUFFLIGHT_EXPLODE = 12;
    public final static int ACTION_BUFFLIGHT_E_COLUMN = 13;
    public final static int ACTION_BUFFLIGHT_DIE = 14;
    public final static int ACTION_BUFFLIGHT_HURT = 15;
    //animation:UI
    public final static int ANIM_UI = 7;
    public final static int MODULE_UI_CONNER_0 = 0;
    public final static int MODULE_UI_HORIZ_0 = 1;
    public final static int MODULE_UI_VERTI_0 = 2;
    public final static int MODULE_UI_CONNER_1 = 3;
    public final static int MODULE_UI_HORIZ_1 = 4;
    public final static int MODULE_UI_VERTI_1 = 5;
    public final static int MODULE_UI_CONNER_2 = 6;
    public final static int MODULE_UI_HORIZ_2 = 7;
    public final static int MODULE_UI_VERTI_2 = 8;
    public final static int MODULE_UI_CONNER_3 = 9;
    public final static int MODULE_UI_HORIZ_3 = 10;
    public final static int MODULE_UI_VERTI_3 = 11;
    public final static int MODULE_UI_CONNER_4 = 12;
    public final static int MODULE_UI_HORIZ_4 = 13;
    public final static int MODULE_UI_VERTI_4 = 14;
    public final static int MODULE_UI_VALUE_SP = 15;
    public final static int MODULE_UI_VALUE_HP = 16;
    public final static int MODULE_UI_VALUE_MP = 17;
    public final static int MODULE_UI_VALUE_ATTACK = 18;
    public final static int MODULE_UI_VALUE_SKILL = 19;
    public final static int MODULE_UI_VALUE_DEFENSE = 20;
    public final static int MODULE_UI_VALUE_CRITICAL = 21;
    public final static int MODULE_UI_VALUE_DODGE = 22;
    public final static int MODULE_UI_VALUE_SPEED = 23;
    public final static int MODULE_UI_LEVEL = 24;
    public final static int MODULE_UI_VALUE_SUDU = 25;
    public final static int MODULE_UI_VALUE_TARGET = 26;
    public final static int MODULE_UI_ICON_HP = 27;
    public final static int MODULE_UI_ICON_MP = 28;
    public final static int MODULE_UI_ICON_EXP = 29;
    public final static int MODULE_UI_ICON_GOLD = 30;
    public final static int MODULE_UI_ICON_GOLD1 = 31;
    public final static int MODULE_UI_ICON_TOKEN = 32;
    public final static int MODULE_UI_ICON_CLOCK = 33;
    public final static int MODULE_UI_RMS_0 = 34;
    public final static int MODULE_UI_RMS_1 = 35;
    public final static int MODULE_UI_COLON = 36;
    public final static int MODULE_UI_POINT = 37;
    public final static int MODULE_UI_EXPEND = 38;
    public final static int MODULE_UI_CUR_SP = 39;
    public final static int MODULE_UI_DODGE = 40;
    public final static int MODULE_UI_SLASH = 41;
    public final static int MODULE_UI_42 = 42;
    public final static int MODULE_UI_43 = 43;
    public final static int MODULE_UI_44 = 44;
    public final static int MODULE_UI_45 = 45;
    public final static int MODULE_UI_46 = 46;
    public final static int MODULE_UI_47 = 47;
    public final static int MODULE_UI_48 = 48;
    public final static int MODULE_UI_49 = 49;
    public final static int MODULE_UI_50 = 50;
    public final static int MODULE_UI_51 = 51;
    public final static int MODULE_UI_52 = 52;
    public final static int MODULE_UI_53 = 53;
    public final static int MODULE_UI_54 = 54;
    public final static int MODULE_UI_55 = 55;
    public final static int MODULE_UI_56 = 56;
    public final static int MODULE_UI_57 = 57;
    public final static int MODULE_UI_58 = 58;
    public final static int MODULE_UI_59 = 59;
    public final static int MODULE_UI_60 = 60;
    public final static int MODULE_UI_61 = 61;
    public final static int MODULE_UI_62 = 62;
    public final static int MODULE_UI_63 = 63;
    public final static int MODULE_UI_64 = 64;
    public final static int MODULE_UI_65 = 65;
    public final static int MODULE_UI_66 = 66;
    public final static int MODULE_UI_67 = 67;
    public final static int MODULE_UI_68 = 68;
    public final static int MODULE_UI_69 = 69;
    public final static int MODULE_UI_ARROW = 70;
    public final static int MODULE_UI_71 = 71;
    public final static int MODULE_UI_72 = 72;
    public final static int MODULE_UI_73 = 73;
    public final static int MODULE_UI_74 = 74;
    public final static int MODULE_UI_75 = 75;
    public final static int MODULE_UI_76 = 76;
    public final static int MODULE_UI_77 = 77;
    public final static int MODULE_UI_78 = 78;
    public final static int MODULE_UI_79 = 79;
    public final static int MODULE_UI_80 = 80;
    public final static int MODULE_UI_81 = 81;
    public final static int MODULE_UI_82 = 82;
    public final static int MODULE_UI_83 = 83;
    public final static int MODULE_UI_84 = 84;
    public final static int MODULE_UI_85 = 85;
    public final static int MODULE_UI_86 = 86;
    public final static int MODULE_UI_87 = 87;
    public final static int MODULE_UI_88 = 88;
    public final static int MODULE_UI_89 = 89;
    public final static int MODULE_UI_90 = 90;
    public final static int MODULE_UI_91 = 91;
    public final static int MODULE_UI_92 = 92;
    public final static int MODULE_UI_93 = 93;
    public final static int MODULE_UI_94 = 94;
    public final static int MODULE_UI_95 = 95;
    public final static int MODULE_UI_96 = 96;
    public final static int MODULE_UI_97 = 97;
    public final static int MODULE_UI_98 = 98;
    public final static int MODULE_UI_99 = 99;
    public final static int MODULE_UI_100 = 100;
    public final static int MODULE_UI_101 = 101;
    public final static int MODULE_UI_102 = 102;
    public final static int MODULE_UI_103 = 103;
    public final static int MODULE_UI_104 = 104;
    public final static int MODULE_UI_105 = 105;
    public final static int MODULE_UI_106 = 106;
    public final static int MODULE_UI_107 = 107;
    public final static int MODULE_UI_108 = 108;
    public final static int MODULE_UI_109 = 109;
    public final static int MODULE_UI_110 = 110;
    public final static int MODULE_UI_111 = 111;
    public final static int MODULE_UI_112 = 112;
    public final static int MODULE_UI_113 = 113;
    public final static int MODULE_UI_114 = 114;
    public final static int MODULE_UI_115 = 115;
    public final static int MODULE_UI_116 = 116;
    public final static int MODULE_UI_117 = 117;
    public final static int MODULE_UI_118 = 118;
    public final static int MODULE_UI_119 = 119;
    public final static int MODULE_UI_120 = 120;
    public final static int MODULE_UI_121 = 121;
    public final static int MODULE_UI_122 = 122;
    public final static int MODULE_UI_123 = 123;
    public final static int MODULE_UI_124 = 124;
    public final static int MODULE_UI_125 = 125;
    public final static int MODULE_UI_126 = 126;
    public final static int MODULE_UI_127 = 127;
    public final static int MODULE_UI_128 = 128;
    public final static int MODULE_UI_129 = 129;
    public final static int MODULE_UI_130 = 130;
    public final static int MODULE_UI_131 = 131;
    public final static int MODULE_UI_132 = 132;
    public final static int MODULE_UI_133 = 133;
    public final static int MODULE_UI_134 = 134;
    public final static int MODULE_UI_135 = 135;
    public final static int MODULE_UI_136 = 136;
    public final static int MODULE_UI_137 = 137;
    public final static int MODULE_UI_138 = 138;
    public final static int MODULE_UI_139 = 139;
    public final static int MODULE_UI_140 = 140;
    public final static int MODULE_UI_141 = 141;
    public final static int MODULE_UI_142 = 142;
    public final static int MODULE_UI_143 = 143;
    public final static int MODULE_UI_144 = 144;
    public final static int MODULE_UI_145 = 145;
    public final static int MODULE_UI_146 = 146;
    public final static int MODULE_UI_147 = 147;
    public final static int MODULE_UI_148 = 148;
    public final static int MODULE_UI_149 = 149;
    public final static int MODULE_UI_150 = 150;
    public final static int MODULE_UI_151 = 151;
    public final static int MODULE_UI_152 = 152;
    public final static int MODULE_UI_153 = 153;
    public final static int MODULE_UI_154 = 154;
    public final static int MODULE_UI_155 = 155;
    public final static int MODULE_UI_156 = 156;
    public final static int MODULE_UI_157 = 157;
    public final static int MODULE_UI_158 = 158;
    public final static int MODULE_UI_159 = 159;
    public final static int MODULE_UI_160 = 160;
    public final static int MODULE_UI_161 = 161;
    public final static int MODULE_UI_162 = 162;
    public final static int MODULE_UI_163 = 163;
    public final static int MODULE_UI_164 = 164;
    public final static int MODULE_UI_165 = 165;
    public final static int MODULE_UI_166 = 166;
    public final static int MODULE_UI_167 = 167;
    public final static int MODULE_UI_168 = 168;
    public final static int MODULE_UI_169 = 169;
    public final static int MODULE_UI_170 = 170;
    public final static int MODULE_UI_171 = 171;
    public final static int MODULE_UI_172 = 172;
    public final static int MODULE_UI_173 = 173;
    public final static int MODULE_UI_174 = 174;
    public final static int MODULE_UI_175 = 175;
    public final static int MODULE_UI_176 = 176;
    public final static int MODULE_UI_177 = 177;
    public final static int MODULE_UI_178 = 178;
    public final static int MODULE_UI_179 = 179;
    public final static int MODULE_UI_180 = 180;
    public final static int MODULE_UI_181 = 181;
    public final static int MODULE_UI_182 = 182;
    public final static int MODULE_UI_183 = 183;
    public final static int MODULE_UI_184 = 184;
    public final static int MODULE_UI_185 = 185;
    public final static int MODULE_UI_186 = 186;
    public final static int MODULE_UI_187 = 187;
    public final static int MODULE_UI_188 = 188;
    public final static int MODULE_UI_189 = 189;
    public final static int MODULE_UI_190 = 190;
    public final static int MODULE_UI_191 = 191;
    public final static int MODULE_UI_192 = 192;
    public final static int MODULE_UI_193 = 193;
    public final static int MODULE_UI_194 = 194;
    public final static int MODULE_UI_195 = 195;
    public final static int MODULE_UI_196 = 196;
    public final static int MODULE_UI_197 = 197;
    public final static int MODULE_UI_CONNER_5 = 198;
    public final static int MODULE_UI_HORIZ_5 = 199;
    public final static int MODULE_UI_VERTI_5 = 200;
    public final static int MODULE_UI_CONNER_6 = 201;
    public final static int MODULE_UI_HORIZ_6 = 202;
    public final static int MODULE_UI_VERTI_6 = 203;
    public final static int MODULE_UI_204 = 204;
    public final static int MODULE_UI_205 = 205;
    public final static int MODULE_UI_206 = 206;
    public final static int MODULE_UI_207 = 207;
    public final static int MODULE_UI_208 = 208;
    public final static int MODULE_UI_209 = 209;
    public final static int MODULE_UI_210 = 210;
    public final static int MODULE_UI_211 = 211;
    public final static int MODULE_UI_212 = 212;
    public final static int MODULE_UI_213 = 213;
    public final static int MODULE_UI_214 = 214;
    public final static int MODULE_UI_215 = 215;
    public final static int MODULE_UI_216 = 216;
    public final static int MODULE_UI_217 = 217;
    public final static int MODULE_UI_218 = 218;
    public final static int MODULE_UI_219 = 219;
    public final static int MODULE_UI_220 = 220;
    public final static int MODULE_UI_221 = 221;
    public final static int FRAME_UI_CURSOR = 0;
    public final static int FRAME_UI_TITLE_DECO = 1;
    public final static int FRAME_UI_ARROW_0_DOWN = 2;
    public final static int FRAME_UI_ARROW_0_LEFT = 3;
    public final static int FRAME_UI_ARROW_0_RIGHT = 4;
    public final static int FRAME_UI_SOUND_OPEN = 5;
    public final static int FRAME_UI_SOUND_CLOSE = 6;
    public final static int FRAME_UI_VIBRATE_OPEN = 7;
    public final static int FRAME_UI_VIBRATE_CLOSE = 8;
    public final static int FRAME_UI_KEY_ENTER = 9;
    public final static int FRAME_UI_KEY_CANCEL = 10;
    public final static int FRAME_UI_IGM_ROLE_0 = 11;
    public final static int FRAME_UI_IGM_ROLE_1 = 12;
    public final static int FRAME_UI_IGM_ROLE_2 = 13;
    public final static int FRAME_UI_IGM_ROLE_3 = 14;
    public final static int FRAME_UI_IGM_ROLE_4 = 15;
    public final static int FRAME_UI_TITLE_GETITEM = 16;
    public final static int FRAME_UI_TITLE_PACKAGE = 17;
    public final static int FRAME_UI_TITLE_EQUIP = 18;
    public final static int FRAME_UI_TITLE_SKILLADVANCE = 19;
    public final static int FRAME_UI_TITLE_MISSION = 20;
    public final static int FRAME_UI_TITLE_ITEM = 21;
    public final static int FRAME_UI_TITLE_STATUS = 22;
    public final static int FRAME_UI_TITLE_SMSSHOP = 23;
    public final static int FRAME_UI_TITLE_SKILL = 24;
    public final static int FRAME_UI_TITLE_SYSTEM = 25;
    public final static int FRAME_UI_TITLE_COOK = 26;
    public final static int FRAME_UI_TITLE_EQUIPADVANCE = 27;
    public final static int FRAME_UI_TITLE_LOAD = 28;
    public final static int FRAME_UI_TITLE_SAVE = 29;
    public final static int FRAME_UI_TITLE_OPTION = 30;
    public final static int FRAME_UI_TITLE_SCORE = 31;
    public final static int FRAME_UI_TITLE_SHOP = 32;
    public final static int FRAME_UI_IGM_TITLE_DECO = 33;
    public final static int FRAME_UI_BC_ATTACK = 34;
    public final static int FRAME_UI_BC_SKILL = 35;
    public final static int FRAME_UI_BC_ITEM = 36;
    public final static int FRAME_UI_BC_SHOP = 37;
    public final static int FRAME_UI_BC_ELIMINATE = 38;
    public final static int FRAME_UI_SYSTEM_ITEM_0 = 39;
    public final static int FRAME_UI_SYSTEM_ITEM_1 = 40;
    public final static int FRAME_UI_SYSTEM_ITEM_2 = 41;
    public final static int FRAME_UI_SYSTEM_ITEM_3 = 42;
    public final static int FRAME_UI_SYSTEM_ITEM_4 = 43;
    public final static int FRAME_UI_SYSTEM_ITEM_5 = 44;
    public final static int FRAME_UI_EQUIP_ACTION_CHANGE = 45;
    public final static int FRAME_UI_EQUIP_ACTION_ADVANCE = 46;
    public final static int FRAME_UI_EQUIP_ACTION_CANCEL = 47;
    public final static int FRAME_UI_BUTTON_MISSION = 48;
    public final static int FRAME_UI_BUTTON_IGM = 49;
    public final static int FRAME_UI_BUTTON_SHOP = 50;
    public final static int FRAME_UI_QUEUE_BAR = 51;
    public final static int FRAME_UI_QUEUE_GOAL = 52;
    public final static int FRAME_UI_QUEUE_ALLY = 53;
    public final static int FRAME_UI_QUEUE_FOE = 54;
    public final static int FRAME_UI_BATTLE_CURSOR = 55;
    public final static int FRAME_UI_EFFORT_DATA0 = 56;
    public final static int FRAME_UI_EFFORT_DATA1 = 57;
    public final static int FRAME_UI_EFFORT_DATA2 = 58;
    public final static int FRAME_UI_EFFORT_STATUS0 = 59;
    public final static int FRAME_UI_EFFORT_STATUS1 = 60;
    public final static int FRAME_UI_EFFORT_STATUS2 = 61;
    public final static int FRAME_UI_OPTION_ITEM0 = 62;
    public final static int FRAME_UI_OPTION_ITEM2 = 63;
    public final static int FRAME_UI_OPTION_ITEM1 = 64;
    public final static int FRAME_UI_OPTION_ITEM0_OPEN = 65;
    public final static int FRAME_UI_OPTION_ITEM2_OPEN = 66;
    public final static int FRAME_UI_OPTION_ITEM1_OPEN = 67;
    public final static int FRAME_UI_OPTION_ITEM0_CLOSE = 68;
    public final static int FRAME_UI_OPTION_ITEM2_CLOSE = 69;
    public final static int FRAME_UI_OPTION_ITEM1_CLOSE = 70;
    public final static int FRAME_UI_TABEL0 = 71;
    public final static int FRAME_UI_TABEL0_FOUCS = 72;
    public final static int FRAME_UI_TABEL1 = 73;
    public final static int FRAME_UI_TABEL1_FOUCS = 74;
    public final static int FRAME_UI_TABEL2 = 75;
    public final static int FRAME_UI_TABEL2_FOUCS = 76;
    public final static int FRAME_UI_MIXTRUE_ITEM0 = 77;
    public final static int FRAME_UI_MIXTRUE_ITEM1 = 78;
    public final static int FRAME_UI_SKILL_ITEM0 = 79;
    public final static int FRAME_UI_SKILL_ITEM1 = 80;
    public final static int FRAME_UI_SKILL_ITEM2 = 81;
    public final static int FRAME_UI_SKILL_ITEM3 = 82;
    public final static int FRAME_UI_SKILL_ITEM4 = 83;
    public final static int FRAME_UI_PACKAGE_ITEM0 = 84;
    public final static int FRAME_UI_PACKAGE_ITEM1 = 85;
    public final static int FRAME_UI_PACKAGE_ITEM2 = 86;
    public final static int FRAME_UI_EQUIP_ITEM0 = 87;
    public final static int FRAME_UI_EQUIP_ITEM1 = 88;
    public final static int FRAME_UI_EQUIP_ITEM2 = 89;
    public final static int FRAME_UI_EQUIP_ITEM3 = 90;
    public final static int FRAME_UI_EQUIP_ITEM4 = 91;
    public final static int FRAME_UI_MIXTRUE_START = 92;
    public final static int FRAME_UI_MIXTRUE_STUFF = 93;
    public final static int FRAME_UI_ADVANCE_START = 94;
    public final static int FRAME_UI_ADVANCE_ARROW = 95;
    public final static int FRAME_UI_SP_EMPTY = 96;
    public final static int FRAME_UI_SP_FULL = 97;
    public final static int FRAME_UI_IGM_ITEM0 = 98;
    public final static int FRAME_UI_IGM_ITEM1 = 99;
    public final static int FRAME_UI_IGM_ITEM2 = 100;
    public final static int FRAME_UI_IGM_ITEM4 = 101;
    public final static int FRAME_UI_IGM_ITEM3 = 102;
    public final static int FRAME_UI_IGM_ITEM5 = 103;
    public final static int FRAME_UI_IGM_ITEM6 = 104;
    public final static int FRAME_UI_IGM_ITEM0_NAME = 105;
    public final static int FRAME_UI_IGM_ITEM1_NAME = 106;
    public final static int FRAME_UI_IGM_ITEM2_NAME = 107;
    public final static int FRAME_UI_IGM_ITEM4_NAME = 108;
    public final static int FRAME_UI_IGM_ITEM3_NAME = 109;
    public final static int FRAME_UI_IGM_ITEM5_NAME = 110;
    public final static int FRAME_UI_IGM_ITEM6_NAME = 111;
    public final static int FRAME_UI_ICON_BG = 112;
    public final static int FRAME_UI_SCROLL_BAR_TOP = 113;
    public final static int FRAME_UI_SCROLL_BAR_BOTTOM = 114;
    public final static int FRAME_UI_SCROOL_BAR_POINT = 115;
    public final static int FRAME_UI_RMS_MONEY = 116;
    public final static int FRAME_UI_RMS_TOKEN = 117;
    public final static int FRAME_UI_RMS_CLOCK = 118;
    public final static int FRAME_UI_SYSTEM_ITEM_BG = 119;
    public final static int FRAME_UI_OPTION_FLAG = 120;
    public final static int FRAME_UI_MISSION_COMPLETE = 121;
    public final static int FRAME_UI_MISSION_ITEM0 = 122;
    public final static int FRAME_UI_MISSION_ITEM1 = 123;
    public final static int FRAME_UI_HERO_ICON_0 = 124;
    public final static int FRAME_UI_HERO_ICON_1 = 125;
    public final static int FRAME_UI_HERO_ICON_2 = 126;
    public final static int FRAME_UI_HERO_ICON_3 = 127;
    public final static int FRAME_UI_HERO_ICON_4 = 128;
    public final static int FRAME_UI_SHOP_BUY = 129;
    public final static int FRAME_UI_SHOP_SHELL = 130;
    public final static int FRAME_UI_SKILL_COST = 131;
    public final static int FRAME_UI_SKILL_TAGET = 132;
    public final static int FRAME_UI_SKILL_ATTACK = 133;
    public final static int FRAME_UI_SKILL_EFFECT = 134;
    public final static int FRAME_UI_SKILL_RECOVER = 135;
    public final static int FRAME_UI_TITLE_CHOOSE = 136;
    public final static int FRAME_UI_137 = 137;
    public final static int FRAME_UI_138 = 138;
    public final static int FRAME_UI_139 = 139;
    public final static int FRAME_UI_140 = 140;
    public final static int FRAME_UI_141 = 141;
    public final static int FRAME_UI_142 = 142;
    public final static int FRAME_UI_143 = 143;
    public final static int FRAME_UI_144 = 144;
    public final static int FRAME_UI_145 = 145;
    public final static int FRAME_UI_146 = 146;
    public final static int FRAME_UI_147 = 147;
    public final static int FRAME_UI_148 = 148;
    public final static int FRAME_UI_149 = 149;
    public final static int FRAME_UI_150 = 150;
    public final static int FRAME_UI_151 = 151;
    public final static int FRAME_UI_152 = 152;
    public final static int FRAME_UI_153 = 153;
    public final static int FRAME_UI_154 = 154;
    public final static int FRAME_UI_155 = 155;
    public final static int FRAME_UI_156 = 156;
    public final static int FRAME_UI_157 = 157;
    public final static int FRAME_UI_158 = 158;
    public final static int FRAME_UI_159 = 159;
    public final static int FRAME_UI_160 = 160;
    public final static int FRAME_UI_161 = 161;
    public final static int FRAME_UI_162 = 162;
    public final static int FRAME_UI_163 = 163;
    public final static int FRAME_UI_164 = 164;
    public final static int FRAME_UI_165 = 165;
    public final static int FRAME_UI_166 = 166;
    public final static int FRAME_UI_167 = 167;
    public final static int FRAME_UI_168 = 168;
    public final static int FRAME_UI_169 = 169;
    public final static int FRAME_UI_170 = 170;
    public final static int FRAME_UI_171 = 171;
    public final static int FRAME_UI_172 = 172;
    public final static int FRAME_UI_JUQING = 173;
    public final static int FRAME_UI_174 = 174;
    public final static int FRAME_UI_175 = 175;
    public final static int FRAME_UI_176 = 176;
    public final static int FRAME_UI_177 = 177;
    public final static int FRAME_UI_BATTLESKILLUI = 178;
    public final static int FRAME_UI_179 = 179;
    public final static int FRAME_UI_180 = 180;
    public final static int FRAME_UI_181 = 181;
    public final static int FRAME_UI_TITLE_ACHIEVEMENT = 182;
    public final static int FRAME_UI_TABEL3 = 183;
    public final static int FRAME_UI_TABEL3_FOUCS = 184;
    public final static int FRAME_UI_BUTTON_ACH = 185;
    public final static int FRAME_UI_EFFORT_ITEM0 = 186;
    public final static int FRAME_UI_EFFORT_ITEM1 = 187;
    public final static int FRAME_UI_EFFORT_ITEM2 = 188;
    public final static int FRAME_UI_EFFORT_ITEM3 = 189;
    public final static int ACTION_UI_LEVEL_UP = 0;
    public final static int ACTION_UI_TARGET_ALLY = 1;
    public final static int ACTION_UI_TARGET_FOE = 2;
    public final static int ACTION_UI_MIXTRUE_START = 3;
    public final static int ACTION_UI_ADVANCE_START = 4;
    public final static int ACTION_UI_JUQING = 5;
    public final static int ACTION_UI_BATTLEUIHALO = 6;
    public final static int ACTION_UI_ACHIEVEMENT = 7;
    //animation:NUMBERS
    public final static int ANIM_NUMBERS = 8;
    public final static int MODULE_NUMBERS_PLUS_A = 0;
    public final static int MODULE_NUMBERS_MINUS_A = 1;
    public final static int MODULE_NUMBERS_0_A = 2;
    public final static int MODULE_NUMBERS_1_A = 3;
    public final static int MODULE_NUMBERS_2_A = 4;
    public final static int MODULE_NUMBERS_3_A = 5;
    public final static int MODULE_NUMBERS_4_A = 6;
    public final static int MODULE_NUMBERS_5_A = 7;
    public final static int MODULE_NUMBERS_6_A = 8;
    public final static int MODULE_NUMBERS_7_A = 9;
    public final static int MODULE_NUMBERS_8_A = 10;
    public final static int MODULE_NUMBERS_9_A = 11;
    public final static int MODULE_NUMBERS_PLUS_B = 12;
    public final static int MODULE_NUMBERS_MINUS_B = 13;
    public final static int MODULE_NUMBERS_0_B = 14;
    public final static int MODULE_NUMBERS_1_B = 15;
    public final static int MODULE_NUMBERS_2_B = 16;
    public final static int MODULE_NUMBERS_3_B = 17;
    public final static int MODULE_NUMBERS_4_B = 18;
    public final static int MODULE_NUMBERS_5_B = 19;
    public final static int MODULE_NUMBERS_6_B = 20;
    public final static int MODULE_NUMBERS_7_B = 21;
    public final static int MODULE_NUMBERS_8_B = 22;
    public final static int MODULE_NUMBERS_9_B = 23;
    public final static int MODULE_NUMBERS_PLUS_C = 24;
    public final static int MODULE_NUMBERS_MINUS_C = 25;
    public final static int MODULE_NUMBERS_0_C = 26;
    public final static int MODULE_NUMBERS_1_C = 27;
    public final static int MODULE_NUMBERS_2_C = 28;
    public final static int MODULE_NUMBERS_3_C = 29;
    public final static int MODULE_NUMBERS_4_C = 30;
    public final static int MODULE_NUMBERS_5_C = 31;
    public final static int MODULE_NUMBERS_6_C = 32;
    public final static int MODULE_NUMBERS_7_C = 33;
    public final static int MODULE_NUMBERS_8_C = 34;
    public final static int MODULE_NUMBERS_9_C = 35;
    public final static int MODULE_NUMBERS_0_D = 36;
    public final static int MODULE_NUMBERS_1_D = 37;
    public final static int MODULE_NUMBERS_2_D = 38;
    public final static int MODULE_NUMBERS_3_D = 39;
    public final static int MODULE_NUMBERS_4_D = 40;
    public final static int MODULE_NUMBERS_5_D = 41;
    public final static int MODULE_NUMBERS_6_D = 42;
    public final static int MODULE_NUMBERS_7_D = 43;
    public final static int MODULE_NUMBERS_8_D = 44;
    public final static int MODULE_NUMBERS_9_D = 45;
    public final static int MODULE_NUMBERS_0_E = 46;
    public final static int MODULE_NUMBERS_1_E = 47;
    public final static int MODULE_NUMBERS_2_E = 48;
    public final static int MODULE_NUMBERS_3_E = 49;
    public final static int MODULE_NUMBERS_4_E = 50;
    public final static int MODULE_NUMBERS_5_E = 51;
    public final static int MODULE_NUMBERS_6_E = 52;
    public final static int MODULE_NUMBERS_7_E = 53;
    public final static int MODULE_NUMBERS_8_E = 54;
    public final static int MODULE_NUMBERS_9_E = 55;
    public final static int MODULE_NUMBERS_0_F = 56;
    public final static int MODULE_NUMBERS_1_F = 57;
    public final static int MODULE_NUMBERS_2_F = 58;
    public final static int MODULE_NUMBERS_3_F = 59;
    public final static int MODULE_NUMBERS_4_F = 60;
    public final static int MODULE_NUMBERS_5_F = 61;
    public final static int MODULE_NUMBERS_6_F = 62;
    public final static int MODULE_NUMBERS_7_F = 63;
    public final static int MODULE_NUMBERS_8_F = 64;
    public final static int MODULE_NUMBERS_9_F = 65;
    public final static int MODULE_NUMBERS_PLUS_G = 66;
    public final static int MODULE_NUMBERS_MINUS_G = 67;
    public final static int MODULE_NUMBERS_0_G = 68;
    public final static int MODULE_NUMBERS_1_G = 69;
    public final static int MODULE_NUMBERS_2_G = 70;
    public final static int MODULE_NUMBERS_3_G = 71;
    public final static int MODULE_NUMBERS_4_G = 72;
    public final static int MODULE_NUMBERS_5_G = 73;
    public final static int MODULE_NUMBERS_6_G = 74;
    public final static int MODULE_NUMBERS_7_G = 75;
    public final static int MODULE_NUMBERS_8_G = 76;
    public final static int MODULE_NUMBERS_9_G = 77;
    public final static int ACTION_NUMBERS_1 = 0;
    public final static int ACTION_NUMBERS_2 = 1;
    //animation:ICON
    public final static int ANIM_ICON = 9;
    public final static int MODULE_ICON_0 = 0;
    public final static int MODULE_ICON_1 = 1;
    public final static int MODULE_ICON_2 = 2;
    public final static int MODULE_ICON_3 = 3;
    public final static int MODULE_ICON_4 = 4;
    public final static int MODULE_ICON_5 = 5;
    public final static int MODULE_ICON_6 = 6;
    public final static int MODULE_ICON_7 = 7;
    public final static int MODULE_ICON_8 = 8;
    public final static int MODULE_ICON_9 = 9;
    public final static int MODULE_ICON_10 = 10;
    public final static int MODULE_ICON_11 = 11;
    public final static int MODULE_ICON_12 = 12;
    public final static int MODULE_ICON_13 = 13;
    public final static int MODULE_ICON_14 = 14;
    public final static int MODULE_ICON_15 = 15;
    public final static int MODULE_ICON_16 = 16;
    public final static int MODULE_ICON_17 = 17;
    public final static int MODULE_ICON_18 = 18;
    public final static int MODULE_ICON_19 = 19;
    public final static int MODULE_ICON_20 = 20;
    public final static int MODULE_ICON_21 = 21;
    public final static int MODULE_ICON_22 = 22;
    public final static int MODULE_ICON_23 = 23;
    public final static int MODULE_ICON_24 = 24;
    public final static int MODULE_ICON_25 = 25;
    public final static int MODULE_ICON_26 = 26;
    public final static int MODULE_ICON_27 = 27;
    public final static int MODULE_ICON_28 = 28;
    public final static int MODULE_ICON_29 = 29;
    public final static int MODULE_ICON_30 = 30;
    public final static int MODULE_ICON_31 = 31;
    public final static int MODULE_ICON_32 = 32;
    public final static int MODULE_ICON_33 = 33;
    public final static int MODULE_ICON_34 = 34;
    public final static int MODULE_ICON_35 = 35;
    public final static int MODULE_ICON_36 = 36;
    public final static int MODULE_ICON_37 = 37;
    public final static int MODULE_ICON_38 = 38;
    public final static int MODULE_ICON_39 = 39;
    public final static int MODULE_ICON_BUFF_0 = 40;
    public final static int MODULE_ICON_BUFF_1 = 41;
    public final static int MODULE_ICON_BUFF_2 = 42;
    public final static int MODULE_ICON_BUFF_3 = 43;
    public final static int MODULE_ICON_BUFF_4 = 44;
    public final static int MODULE_ICON_BUFF_5 = 45;
    public final static int MODULE_ICON_BUFF_6 = 46;
    public final static int MODULE_ICON_BUFF_7 = 47;
    public final static int FRAME_ICON_0 = 0;
    public final static int FRAME_ICON_1 = 1;
    public final static int FRAME_ICON_2 = 2;
    public final static int FRAME_ICON_3 = 3;
    public final static int FRAME_ICON_4 = 4;
    public final static int FRAME_ICON_5 = 5;
    public final static int FRAME_ICON_6 = 6;
    public final static int FRAME_ICON_7 = 7;
    public final static int FRAME_ICON_8 = 8;
    public final static int FRAME_ICON_9 = 9;
    public final static int FRAME_ICON_10 = 10;
    public final static int FRAME_ICON_11 = 11;
    public final static int FRAME_ICON_12 = 12;
    public final static int FRAME_ICON_13 = 13;
    public final static int FRAME_ICON_14 = 14;
    public final static int FRAME_ICON_15 = 15;
    public final static int FRAME_ICON_16 = 16;
    public final static int FRAME_ICON_17 = 17;
    public final static int FRAME_ICON_18 = 18;
    public final static int FRAME_ICON_19 = 19;
    public final static int FRAME_ICON_20 = 20;
    public final static int FRAME_ICON_21 = 21;
    public final static int FRAME_ICON_TAKE_OFF = 22;
    public final static int FRAME_ICON_23 = 23;
    public final static int FRAME_ICON_24 = 24;
    public final static int FRAME_ICON_25 = 25;
    public final static int FRAME_ICON_26 = 26;
    public final static int FRAME_ICON_27 = 27;
    public final static int FRAME_ICON_28 = 28;
    public final static int FRAME_ICON_29 = 29;
    public final static int FRAME_ICON_30 = 30;
    public final static int FRAME_ICON_31 = 31;
    public final static int FRAME_ICON_32 = 32;
    public final static int FRAME_ICON_33 = 33;
    public final static int FRAME_ICON_34 = 34;
    public final static int FRAME_ICON_35 = 35;
    public final static int FRAME_ICON_36 = 36;
    public final static int FRAME_ICON_37 = 37;
    public final static int FRAME_ICON_38 = 38;
    public final static int FRAME_ICON_39 = 39;
    public final static int FRAME_ICON_40 = 40;
    public final static int FRAME_ICON_41 = 41;
    public final static int FRAME_ICON_42 = 42;
    public final static int FRAME_ICON_43 = 43;
    public final static int FRAME_ICON_44 = 44;
    public final static int FRAME_ICON_45 = 45;
    public final static int FRAME_ICON_46 = 46;
    public final static int FRAME_ICON_47 = 47;
    public final static int ACTION_ICON_1 = 0;
    public final static int ACTION_ICON_2 = 1;
    public final static int ACTION_ICON_3 = 2;
    public final static int ACTION_ICON_4 = 3;
    public final static int ACTION_ICON_5 = 4;
    public final static int ACTION_ICON_6 = 5;
    public final static int ACTION_ICON_7 = 6;
    public final static int ACTION_ICON_8 = 7;
    public final static int ACTION_ICON_9 = 8;
    public final static int ACTION_ICON_0 = 9;
    public final static int ACTION_ICON_10 = 10;
    public final static int ACTION_ICON_11 = 11;
    public final static int ACTION_ICON_12 = 12;
    public final static int ACTION_ICON_13 = 13;
    public final static int ACTION_ICON_14 = 14;
    public final static int ACTION_ICON_15 = 15;
    public final static int ACTION_ICON_16 = 16;
    public final static int ACTION_ICON_17 = 17;
    public final static int ACTION_ICON_18 = 18;
    public final static int ACTION_ICON_19 = 19;
    public final static int ACTION_ICON_20 = 20;
    public final static int ACTION_ICON_21 = 21;
    public final static int ACTION_ICON_22 = 22;
    public final static int ACTION_ICON_23 = 23;
    public final static int ACTION_ICON_24 = 24;
    public final static int ACTION_ICON_25 = 25;
    public final static int ACTION_ICON_26 = 26;
    public final static int ACTION_ICON_27 = 27;
    public final static int ACTION_ICON_28 = 28;
    public final static int ACTION_ICON_29 = 29;
    public final static int ACTION_ICON_30 = 30;
    //animation:LOAD
    public final static int ANIM_LOAD = 10;
    public final static int FRAME_LOAD_0 = 0;
    public final static int FRAME_LOAD_1 = 1;
    public final static int FRAME_LOAD_2 = 2;
    public final static int FRAME_LOAD_3 = 3;
    public final static int FRAME_LOAD_4 = 4;
    public final static int FRAME_LOAD_5 = 5;
    public final static int FRAME_LOAD_6 = 6;
    public final static int FRAME_LOAD_7 = 7;
    public final static int FRAME_LOAD_8 = 8;
    public final static int FRAME_LOAD_9 = 9;
    public final static int FRAME_LOAD_10 = 10;
    public final static int FRAME_LOAD_11 = 11;
    public final static int FRAME_LOAD_12 = 12;
    public final static int FRAME_LOAD_13 = 13;
    public final static int FRAME_LOAD_14 = 14;
    public final static int FRAME_LOAD_15 = 15;
    public final static int FRAME_LOAD_16 = 16;
    public final static int FRAME_LOAD_17 = 17;
    public final static int FRAME_LOAD_18 = 18;
    public final static int FRAME_LOAD_19 = 19;
    public final static int FRAME_LOAD_20 = 20;
    public final static int FRAME_LOAD_21 = 21;
    public final static int FRAME_LOAD_22 = 22;
    public final static int FRAME_LOAD_23 = 23;
    public final static int FRAME_LOAD_24 = 24;
    public final static int FRAME_LOAD_25 = 25;
    public final static int FRAME_LOAD_26 = 26;
    public final static int FRAME_LOAD_27 = 27;
    public final static int FRAME_LOAD_28 = 28;
    public final static int FRAME_LOAD_29 = 29;
    public final static int FRAME_LOAD_30 = 30;
    public final static int FRAME_LOAD_31 = 31;
    public final static int FRAME_LOAD_32 = 32;
    public final static int FRAME_LOAD_33 = 33;
    public final static int FRAME_LOAD_34 = 34;
    public final static int FRAME_LOAD_35 = 35;
    public final static int FRAME_LOAD_36 = 36;
    public final static int FRAME_LOAD_37 = 37;
    public final static int FRAME_LOAD_38 = 38;
    public final static int FRAME_LOAD_39 = 39;
    public final static int FRAME_LOAD_40 = 40;
    public final static int FRAME_LOAD_41 = 41;
    public final static int FRAME_LOAD_42 = 42;
    public final static int FRAME_LOAD_43 = 43;
    public final static int FRAME_LOAD_44 = 44;
    public final static int FRAME_LOAD_45 = 45;
    public final static int FRAME_LOAD_46 = 46;
    public final static int FRAME_LOAD_47 = 47;
    public final static int FRAME_LOAD_48 = 48;
    public final static int FRAME_LOAD_49 = 49;
    public final static int FRAME_LOAD_50 = 50;
    public final static int FRAME_LOAD_51 = 51;
    public final static int FRAME_LOAD_52 = 52;
    public final static int FRAME_LOAD_53 = 53;
    public final static int FRAME_LOAD_54 = 54;
    public final static int FRAME_LOAD_55 = 55;
    public final static int FRAME_LOAD_56 = 56;
    public final static int FRAME_LOAD_57 = 57;
    public final static int FRAME_LOAD_58 = 58;
    public final static int FRAME_LOAD_59 = 59;
    public final static int FRAME_LOAD_60 = 60;
    public final static int FRAME_LOAD_61 = 61;
    public final static int FRAME_LOAD_62 = 62;
    public final static int FRAME_LOAD_63 = 63;
    public final static int FRAME_LOAD_64 = 64;
    public final static int FRAME_LOAD_65 = 65;
    public final static int FRAME_LOAD_66 = 66;
    public final static int FRAME_LOAD_67 = 67;
    public final static int FRAME_LOAD_68 = 68;
    public final static int FRAME_LOAD_69 = 69;
    public final static int FRAME_LOAD_70 = 70;
    public final static int FRAME_LOAD_71 = 71;
    public final static int FRAME_LOAD_72 = 72;
    public final static int FRAME_LOAD_73 = 73;
    public final static int FRAME_LOAD_74 = 74;
    public final static int FRAME_LOAD_75 = 75;
    public final static int FRAME_LOAD_76 = 76;
    public final static int FRAME_LOAD_77 = 77;
    public final static int FRAME_LOAD_78 = 78;
    public final static int FRAME_LOAD_79 = 79;
    public final static int FRAME_LOAD_80 = 80;
    public final static int FRAME_LOAD_81 = 81;
    public final static int FRAME_LOAD_82 = 82;
    public final static int FRAME_LOAD_83 = 83;
    public final static int FRAME_LOAD_84 = 84;
    public final static int FRAME_LOAD_85 = 85;
    public final static int FRAME_LOAD_86 = 86;
    public final static int FRAME_LOAD_87 = 87;
    public final static int FRAME_LOAD_88 = 88;
    public final static int FRAME_LOAD_89 = 89;
    public final static int FRAME_LOAD_90 = 90;
    public final static int FRAME_LOAD_91 = 91;
    public final static int FRAME_LOAD_92 = 92;
    public final static int FRAME_LOAD_93 = 93;
    public final static int FRAME_LOAD_94 = 94;
    public final static int FRAME_LOAD_95 = 95;
    public final static int FRAME_LOAD_96 = 96;
    public final static int FRAME_LOAD_97 = 97;
    public final static int FRAME_LOAD_98 = 98;
    public final static int FRAME_LOAD_99 = 99;
    public final static int FRAME_LOAD_100 = 100;
    public final static int FRAME_LOAD_101 = 101;
    public final static int FRAME_LOAD_102 = 102;
    public final static int FRAME_LOAD_103 = 103;
    public final static int FRAME_LOAD_104 = 104;
    public final static int FRAME_LOAD_105 = 105;
    public final static int FRAME_LOAD_106 = 106;
    public final static int FRAME_LOAD_107 = 107;
    public final static int FRAME_LOAD_108 = 108;
    public final static int FRAME_LOAD_109 = 109;
    public final static int FRAME_LOAD_110 = 110;
    public final static int FRAME_LOAD_111 = 111;
    public final static int FRAME_LOAD_112 = 112;
    public final static int FRAME_LOAD_113 = 113;
    public final static int FRAME_LOAD_114 = 114;
    public final static int FRAME_LOAD_115 = 115;
    public final static int FRAME_LOAD_116 = 116;
    public final static int FRAME_LOAD_117 = 117;
    public final static int FRAME_LOAD_118 = 118;
    public final static int FRAME_LOAD_119 = 119;
    public final static int FRAME_LOAD_120 = 120;
    public final static int FRAME_LOAD_121 = 121;
    public final static int FRAME_LOAD_122 = 122;
    public final static int FRAME_LOAD_123 = 123;
    public final static int FRAME_LOAD_124 = 124;
    public final static int FRAME_LOAD_125 = 125;
    public final static int FRAME_LOAD_126 = 126;
    public final static int FRAME_LOAD_127 = 127;
    public final static int FRAME_LOAD_128 = 128;
    public final static int FRAME_LOAD_129 = 129;
    public final static int FRAME_LOAD_130 = 130;
    public final static int FRAME_LOAD_131 = 131;
    public final static int FRAME_LOAD_132 = 132;
    public final static int FRAME_LOAD_133 = 133;
    public final static int FRAME_LOAD_134 = 134;
    public final static int FRAME_LOAD_135 = 135;
    public final static int FRAME_LOAD_136 = 136;
    public final static int FRAME_LOAD_137 = 137;
    public final static int FRAME_LOAD_138 = 138;
    public final static int ACTION_LOAD_LOADING_FIRST = 0;
    public final static int ACTION_LOAD_LOADING_1 = 1;
    public final static int ACTION_LOAD_LOADING_LAST = 2;
    //animation:SPLASH_ANIM
    public final static int ANIM_SPLASH_ANIM = 11;
    public final static int FRAME_SPLASH_ANIM_0 = 0;
    public final static int FRAME_SPLASH_ANIM_1 = 1;
    public final static int FRAME_SPLASH_ANIM_2 = 2;
    public final static int FRAME_SPLASH_ANIM_3 = 3;
    public final static int FRAME_SPLASH_ANIM_4 = 4;
    public final static int FRAME_SPLASH_ANIM_5 = 5;
    public final static int FRAME_SPLASH_ANIM_6 = 6;
    public final static int FRAME_SPLASH_ANIM_7 = 7;
    public final static int FRAME_SPLASH_ANIM_8 = 8;
    public final static int FRAME_SPLASH_ANIM_9 = 9;
    public final static int FRAME_SPLASH_ANIM_10 = 10;
    public final static int FRAME_SPLASH_ANIM_11 = 11;
    public final static int FRAME_SPLASH_ANIM_MAIN_BG = 12;
    public final static int FRAME_SPLASH_ANIM_NEW_GAME = 13;
    public final static int FRAME_SPLASH_ANIM_LOAD_GAME = 14;
    public final static int FRAME_SPLASH_ANIM_GF = 15;
    public final static int FRAME_SPLASH_ANIM_JINGPIN = 16;
    public final static int FRAME_SPLASH_ANIM_MORE = 17;
    public final static int FRAME_SPLASH_ANIM_OPTION = 18;
    public final static int FRAME_SPLASH_ANIM_HELP = 19;
    public final static int FRAME_SPLASH_ANIM_ABOUT = 20;
    public final static int FRAME_SPLASH_ANIM_EXIT_GAME = 21;
    public final static int FRAME_SPLASH_ANIM_QQ_FORUM = 22;
    public final static int FRAME_SPLASH_ANIM_QQ_SCORE = 23;
    public final static int FRAME_SPLASH_ANIM_MORE_GAME = 24;
    public final static int ACTION_SPLASH_ANIM_SPLASH = 0;
    public final static int ACTION_SPLASH_ANIM_MAINMENU = 1;
    public final static int ACTION_SPLASH_ANIM_EFFECT = 2;
    //animation:BATTLE_PORTRAIT
    public final static int ANIM_BATTLE_PORTRAIT = 12;
    public final static int FRAME_BATTLE_PORTRAIT_HERO_0 = 0;
    public final static int FRAME_BATTLE_PORTRAIT_HERO_1 = 1;
    public final static int FRAME_BATTLE_PORTRAIT_HERO_2 = 2;
    //animation:EVENT_ZONE
    public final static int ANIM_EVENT_ZONE = 13;
    public final static int ACTION_EVENT_ZONE_ = 0;
    public final static int ACTION_EVENT_ZONE_1 = 1;
    public final static int ACTION_EVENT_ZONE_2 = 2;
    public final static int ACTION_EVENT_ZONE_3 = 3;
    //animation:NPC_01_CITIZENM
    public final static int ANIM_NPC_01_CITIZENM = 14;
    public final static int ACTION_NPC_01_CITIZENM_STAND_UP = 0;
    public final static int ACTION_NPC_01_CITIZENM_STAND_RIGHT = 1;
    public final static int ACTION_NPC_01_CITIZENM_STAND_DOWN = 2;
    public final static int ACTION_NPC_01_CITIZENM_STAND_LEFT = 3;
    public final static int ACTION_NPC_01_CITIZENM_WALK_UP = 4;
    public final static int ACTION_NPC_01_CITIZENM_WALK_RIGHT = 5;
    public final static int ACTION_NPC_01_CITIZENM_WALK_DOWN = 6;
    public final static int ACTION_NPC_01_CITIZENM_WALK_LEFT = 7;
    public final static int ACTION_NPC_01_CITIZENM_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_01_CITIZENM_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_01_CITIZENM_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_01_CITIZENM_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_01_CITIZENM_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_01_CITIZENM_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_01_CITIZENM_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_01_CITIZENM_TRANSPORT_MOVE_LEFT = 15;
    //animation:NPC_02_JUNWUYI
    public final static int ANIM_NPC_02_JUNWUYI = 15;
    public final static int ACTION_NPC_02_JUNWUYI_STAND_UP = 0;
    public final static int ACTION_NPC_02_JUNWUYI_STAND_RIGHT = 1;
    public final static int ACTION_NPC_02_JUNWUYI_STAND_DOWN = 2;
    public final static int ACTION_NPC_02_JUNWUYI_STAND_LEFT = 3;
    public final static int ACTION_NPC_02_JUNWUYI_WALK_UP = 4;
    public final static int ACTION_NPC_02_JUNWUYI_WALK_RIGHT = 5;
    public final static int ACTION_NPC_02_JUNWUYI_WALK_DOWN = 6;
    public final static int ACTION_NPC_02_JUNWUYI_WALK_LEFT = 7;
    public final static int ACTION_NPC_02_JUNWUYI_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_02_JUNWUYI_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_02_JUNWUYI_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_02_JUNWUYI_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_02_JUNWUYI_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_02_JUNWUYI_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_02_JUNWUYI_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_02_JUNWUYI_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_NPC_02_JUNWUYI_ANGRY = 16;
    public final static int ACTION_NPC_02_JUNWUYI_RIGHT = 17;
    public final static int ACTION_NPC_02_JUNWUYI_SHAKE_RIGHT = 18;
    //animation:NPC_03_CITIZENF
    public final static int ANIM_NPC_03_CITIZENF = 16;
    public final static int ACTION_NPC_03_CITIZENF_STAND_UP = 0;
    public final static int ACTION_NPC_03_CITIZENF_STAND_RIGHT = 1;
    public final static int ACTION_NPC_03_CITIZENF_STAND_DOWN = 2;
    public final static int ACTION_NPC_03_CITIZENF_STAND_LEFT = 3;
    public final static int ACTION_NPC_03_CITIZENF_WALK_UP = 4;
    public final static int ACTION_NPC_03_CITIZENF_WALK_RIGHT = 5;
    public final static int ACTION_NPC_03_CITIZENF_WALK_DOWN = 6;
    public final static int ACTION_NPC_03_CITIZENF_WALK_LEFT = 7;
    public final static int ACTION_NPC_03_CITIZENF_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_03_CITIZENF_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_03_CITIZENF_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_03_CITIZENF_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_03_CITIZENF_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_03_CITIZENF_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_03_CITIZENF_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_03_CITIZENF_TRANSPORT_MOVE_LEFT = 15;
    //animation:NPC_04_GONGZI
    public final static int ANIM_NPC_04_GONGZI = 17;
    public final static int ACTION_NPC_04_GONGZI_STAND_UP = 0;
    public final static int ACTION_NPC_04_GONGZI_STAND_RIGHT = 1;
    public final static int ACTION_NPC_04_GONGZI_STAND_DOWN = 2;
    public final static int ACTION_NPC_04_GONGZI_STAND_LEFT = 3;
    public final static int ACTION_NPC_04_GONGZI_WALK_UP = 4;
    public final static int ACTION_NPC_04_GONGZI_WALK_RIGHT = 5;
    public final static int ACTION_NPC_04_GONGZI_WALK_DOWN = 6;
    public final static int ACTION_NPC_04_GONGZI_WALK_LEFT = 7;
    public final static int ACTION_NPC_04_GONGZI_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_04_GONGZI_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_04_GONGZI_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_04_GONGZI_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_04_GONGZI_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_04_GONGZI_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_04_GONGZI_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_04_GONGZI_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_NPC_04_GONGZI_ANGRY_RIGHT = 16;
    //animation:NPC_05_SMITH
    public final static int ANIM_NPC_05_SMITH = 18;
    public final static int ACTION_NPC_05_SMITH_STAND_UP = 0;
    public final static int ACTION_NPC_05_SMITH_STAND_RIGHT = 1;
    public final static int ACTION_NPC_05_SMITH_STAND_DOWN = 2;
    public final static int ACTION_NPC_05_SMITH_STAND_LEFT = 3;
    public final static int ACTION_NPC_05_SMITH_WALK_UP = 4;
    public final static int ACTION_NPC_05_SMITH_WALK_RIGHT = 5;
    public final static int ACTION_NPC_05_SMITH_WALK_DOWN = 6;
    public final static int ACTION_NPC_05_SMITH_WALK_LEFT = 7;
    public final static int ACTION_NPC_05_SMITH_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_05_SMITH_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_05_SMITH_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_05_SMITH_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_05_SMITH_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_05_SMITH_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_05_SMITH_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_05_SMITH_TRANSPORT_MOVE_LEFT = 15;
    //animation:NPC_06_LITTLEBROTHER
    public final static int ANIM_NPC_06_LITTLEBROTHER = 19;
    public final static int ACTION_NPC_06_LITTLEBROTHER_STAND_UP = 0;
    public final static int ACTION_NPC_06_LITTLEBROTHER_STAND_RIGHT = 1;
    public final static int ACTION_NPC_06_LITTLEBROTHER_STAND_DOWN = 2;
    public final static int ACTION_NPC_06_LITTLEBROTHER_STAND_LEFT = 3;
    public final static int ACTION_NPC_06_LITTLEBROTHER_WALK_UP = 4;
    public final static int ACTION_NPC_06_LITTLEBROTHER_WALK_RIGHT = 5;
    public final static int ACTION_NPC_06_LITTLEBROTHER_WALK_DOWN = 6;
    public final static int ACTION_NPC_06_LITTLEBROTHER_WALK_LEFT = 7;
    public final static int ACTION_NPC_06_LITTLEBROTHER_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_06_LITTLEBROTHER_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_06_LITTLEBROTHER_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_06_LITTLEBROTHER_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_06_LITTLEBROTHER_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_06_LITTLEBROTHER_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_06_LITTLEBROTHER_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_06_LITTLEBROTHER_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_NPC_06_LITTLEBROTHER_DEAD = 16;
    public final static int ACTION_NPC_06_LITTLEBROTHER_STRUGGLE = 17;
    public final static int ACTION_NPC_06_LITTLEBROTHER_SHAKE_V = 18;
    //animation:NPC_07_LITTLESISTER
    public final static int ANIM_NPC_07_LITTLESISTER = 20;
    public final static int ACTION_NPC_07_LITTLESISTER_STAND_UP = 0;
    public final static int ACTION_NPC_07_LITTLESISTER_STAND_RIGHT = 1;
    public final static int ACTION_NPC_07_LITTLESISTER_STAND_DOWN = 2;
    public final static int ACTION_NPC_07_LITTLESISTER_STAND_LEFT = 3;
    public final static int ACTION_NPC_07_LITTLESISTER_WALK_UP = 4;
    public final static int ACTION_NPC_07_LITTLESISTER_WALK_RIGHT = 5;
    public final static int ACTION_NPC_07_LITTLESISTER_WALK_DOWN = 6;
    public final static int ACTION_NPC_07_LITTLESISTER_WALK_LEFT = 7;
    public final static int ACTION_NPC_07_LITTLESISTER_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_07_LITTLESISTER_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_07_LITTLESISTER_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_07_LITTLESISTER_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_07_LITTLESISTER_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_07_LITTLESISTER_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_07_LITTLESISTER_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_07_LITTLESISTER_TRANSPORT_MOVE_LEFT = 15;
    //animation:NPC_08_MAIDEN
    public final static int ANIM_NPC_08_MAIDEN = 21;
    public final static int ACTION_NPC_08_MAIDEN_STAND_UP = 0;
    public final static int ACTION_NPC_08_MAIDEN_STAND_RIGHT = 1;
    public final static int ACTION_NPC_08_MAIDEN_STAND_DOWN = 2;
    public final static int ACTION_NPC_08_MAIDEN_STAND_LEFT = 3;
    public final static int ACTION_NPC_08_MAIDEN_WALK_UP = 4;
    public final static int ACTION_NPC_08_MAIDEN_WALK_RIGHT = 5;
    public final static int ACTION_NPC_08_MAIDEN_WALK_DOWN = 6;
    public final static int ACTION_NPC_08_MAIDEN_WALK_LEFT = 7;
    public final static int ACTION_NPC_08_MAIDEN_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_08_MAIDEN_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_08_MAIDEN_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_08_MAIDEN_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_08_MAIDEN_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_08_MAIDEN_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_08_MAIDEN_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_08_MAIDEN_TRANSPORT_MOVE_LEFT = 15;
    //animation:NPC_09_KING
    public final static int ANIM_NPC_09_KING = 22;
    public final static int ACTION_NPC_09_KING_STAND_UP = 0;
    public final static int ACTION_NPC_09_KING_STAND_RIGHT = 1;
    public final static int ACTION_NPC_09_KING_STAND_DOWM = 2;
    public final static int ACTION_NPC_09_KING_STAND_LEFT = 3;
    public final static int ACTION_NPC_09_KING_WALK_UP = 4;
    public final static int ACTION_NPC_09_KING_WALK_RIGHT = 5;
    public final static int ACTION_NPC_09_KING_WALK_DOWN = 6;
    public final static int ACTION_NPC_09_KING_WALK_LEFT = 7;
    public final static int ACTION_NPC_09_KING_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_09_KING_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_09_KING_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_09_KING_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_09_KING_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_09_KING_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_09_KING_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_09_KING_TRANSPORT_MOVE_LEFT = 15;
    //animation:NPC_10_PRINCESS
    public final static int ANIM_NPC_10_PRINCESS = 23;
    public final static int ACTION_NPC_10_PRINCESS_STAND_UP = 0;
    public final static int ACTION_NPC_10_PRINCESS_STAND_RIGHT = 1;
    public final static int ACTION_NPC_10_PRINCESS_STAND_DOWN = 2;
    public final static int ACTION_NPC_10_PRINCESS_STAND_LEFT = 3;
    public final static int ACTION_NPC_10_PRINCESS_WALK_UP = 4;
    public final static int ACTION_NPC_10_PRINCESS_WALK_RIGHT = 5;
    public final static int ACTION_NPC_10_PRINCESS_WALK_DOWN = 6;
    public final static int ACTION_NPC_10_PRINCESS_WALK_LEFT = 7;
    public final static int ACTION_NPC_10_PRINCESS_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_10_PRINCESS_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_10_PRINCESS_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_10_PRINCESS_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_10_PRINCESS_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_10_PRINCESS_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_10_PRINCESS_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_10_PRINCESS_TRANSPORT_MOVE_LEFT = 15;
    //animation:NPC_11_GUARD
    public final static int ANIM_NPC_11_GUARD = 24;
    public final static int ACTION_NPC_11_GUARD_STAND_UP = 0;
    public final static int ACTION_NPC_11_GUARD_STAND_RIGHT = 1;
    public final static int ACTION_NPC_11_GUARD_STAND_DOWN = 2;
    public final static int ACTION_NPC_11_GUARD_STAND_LEFT = 3;
    public final static int ACTION_NPC_11_GUARD_WALK_UP = 4;
    public final static int ACTION_NPC_11_GUARD_WALK_RIGHT = 5;
    public final static int ACTION_NPC_11_GUARD_WALK_DOWN = 6;
    public final static int ACTION_NPC_11_GUARD_WALK_LEFT = 7;
    public final static int ACTION_NPC_11_GUARD_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_11_GUARD_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_11_GUARD_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_11_GUARD_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_11_GUARD_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_11_GUARD_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_11_GUARD_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_11_GUARD_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_NPC_11_GUARD_DEAD_RIGHT = 16;
    public final static int ACTION_NPC_11_GUARD_DEAD_LEFT = 17;
    public final static int ACTION_NPC_11_GUARD_LIE_RIGHT = 18;
    public final static int ACTION_NPC_11_GUARD_LIE_LEFT = 19;
    //animation:NPC_12_DOCTOR
    public final static int ANIM_NPC_12_DOCTOR = 25;
    public final static int ACTION_NPC_12_DOCTOR_STAND_UP = 0;
    public final static int ACTION_NPC_12_DOCTOR_STAND_RIGHT = 1;
    public final static int ACTION_NPC_12_DOCTOR_STAND_DOWN = 2;
    public final static int ACTION_NPC_12_DOCTOR_STAND_LEFT = 3;
    public final static int ACTION_NPC_12_DOCTOR_WALK_UP = 4;
    public final static int ACTION_NPC_12_DOCTOR_WALK_RIGHT = 5;
    public final static int ACTION_NPC_12_DOCTOR_WALK_DOWN = 6;
    public final static int ACTION_NPC_12_DOCTOR_WALK_LEFT = 7;
    public final static int ACTION_NPC_12_DOCTOR_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_12_DOCTOR_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_12_DOCTOR_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_12_DOCTOR_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_12_DOCTOR_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_12_DOCTOR_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_12_DOCTOR_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_12_DOCTOR_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_NPC_12_DOCTOR_ANGRY_LEFT = 16;
    //animation:NPC_13_YINGBOKONG
    public final static int ANIM_NPC_13_YINGBOKONG = 26;
    public final static int ACTION_NPC_13_YINGBOKONG_STAND_UP = 0;
    public final static int ACTION_NPC_13_YINGBOKONG_STAND_RIGHT = 1;
    public final static int ACTION_NPC_13_YINGBOKONG_STAND_DOWN = 2;
    public final static int ACTION_NPC_13_YINGBOKONG_STAND_LEFT = 3;
    public final static int ACTION_NPC_13_YINGBOKONG_WALK_UP = 4;
    public final static int ACTION_NPC_13_YINGBOKONG_WALK_RIGHT = 5;
    public final static int ACTION_NPC_13_YINGBOKONG_WALK_DOWN = 6;
    public final static int ACTION_NPC_13_YINGBOKONG_WALK_LEFT = 7;
    public final static int ACTION_NPC_13_YINGBOKONG_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_13_YINGBOKONG_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_13_YINGBOKONG_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_13_YINGBOKONG_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_13_YINGBOKONG_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_13_YINGBOKONG_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_13_YINGBOKONG_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_13_YINGBOKONG_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_NPC_13_YINGBOKONG_SHAKE = 16;
    public final static int ACTION_NPC_13_YINGBOKONG_SHAKE_STOP = 17;
    //animation:NPC_14_JUNZHANTIAN
    public final static int ANIM_NPC_14_JUNZHANTIAN = 27;
    public final static int ACTION_NPC_14_JUNZHANTIAN_STAND_UP = 0;
    public final static int ACTION_NPC_14_JUNZHANTIAN_STAND_RIGHT = 1;
    public final static int ACTION_NPC_14_JUNZHANTIAN_STAND_DOWN = 2;
    public final static int ACTION_NPC_14_JUNZHANTIAN_STAND_LEFT = 3;
    public final static int ACTION_NPC_14_JUNZHANTIAN_WALK_UP = 4;
    public final static int ACTION_NPC_14_JUNZHANTIAN_WALK_RIGHT = 5;
    public final static int ACTION_NPC_14_JUNZHANTIAN_WALK_DOWN = 6;
    public final static int ACTION_NPC_14_JUNZHANTIAN_WALK_LEFT = 7;
    public final static int ACTION_NPC_14_JUNZHANTIAN_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_14_JUNZHANTIAN_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_14_JUNZHANTIAN_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_14_JUNZHANTIAN_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_14_JUNZHANTIAN_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_14_JUNZHANTIAN_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_14_JUNZHANTIAN_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_14_JUNZHANTIAN_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_NPC_14_JUNZHANTIAN_ANGRY = 16;
    public final static int ACTION_NPC_14_JUNZHANTIAN_SHAKE_DOWN = 17;
    //animation:NPC_15_ASSASIN
    public final static int ANIM_NPC_15_ASSASIN = 28;
    public final static int ACTION_NPC_15_ASSASIN_STAND_UP = 0;
    public final static int ACTION_NPC_15_ASSASIN_STAND_RIGHT = 1;
    public final static int ACTION_NPC_15_ASSASIN_STAND_DOWN = 2;
    public final static int ACTION_NPC_15_ASSASIN_STAND_LEFT = 3;
    public final static int ACTION_NPC_15_ASSASIN_WALK_UP = 4;
    public final static int ACTION_NPC_15_ASSASIN_WALK_RIGHT = 5;
    public final static int ACTION_NPC_15_ASSASIN_WALK_DOWN = 6;
    public final static int ACTION_NPC_15_ASSASIN_WALK_LEFT = 7;
    public final static int ACTION_NPC_15_ASSASIN_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_15_ASSASIN_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_15_ASSASIN_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_15_ASSASIN_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_15_ASSASIN_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_15_ASSASIN_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_15_ASSASIN_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_15_ASSASIN_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_NPC_15_ASSASIN_DIE = 16;
    public final static int ACTION_NPC_15_ASSASIN_DIE_LEFT = 17;
    public final static int ACTION_NPC_15_ASSASIN_FLASH_UP = 18;
    public final static int ACTION_NPC_15_ASSASIN_FLASH_RIGHT = 19;
    public final static int ACTION_NPC_15_ASSASIN_FLASH_DOWN = 20;
    public final static int ACTION_NPC_15_ASSASIN_FLASH_LEFT = 21;
    public final static int ACTION_NPC_15_ASSASIN_LIE_RIGHT = 22;
    public final static int ACTION_NPC_15_ASSASIN_LIE_LEFT = 23;
    //animation:NPC_16_TANGYUAN
    public final static int ANIM_NPC_16_TANGYUAN = 29;
    public final static int ACTION_NPC_16_TANGYUAN_STAND_UP = 0;
    public final static int ACTION_NPC_16_TANGYUAN_STAND_RIGHT = 1;
    public final static int ACTION_NPC_16_TANGYUAN_STAND_DOWN = 2;
    public final static int ACTION_NPC_16_TANGYUAN_STAND_LEFT = 3;
    public final static int ACTION_NPC_16_TANGYUAN_WALK_UP = 4;
    public final static int ACTION_NPC_16_TANGYUAN_WALK_RIGHT = 5;
    public final static int ACTION_NPC_16_TANGYUAN_WALK_DOWN = 6;
    public final static int ACTION_NPC_16_TANGYUAN_WALK_LEFT = 7;
    public final static int ACTION_NPC_16_TANGYUAN_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_16_TANGYUAN_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_16_TANGYUAN_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_16_TANGYUAN_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_16_TANGYUAN_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_16_TANGYUAN_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_16_TANGYUAN_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_16_TANGYUAN_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_NPC_16_TANGYUAN_TRIP_RIGHT = 16;
    public final static int ACTION_NPC_16_TANGYUAN_TRIP_UP = 17;
    public final static int ACTION_NPC_16_TANGYUAN_TRIP_LEFT = 18;
    public final static int ACTION_NPC_16_TANGYUAN_DEAD = 19;
    public final static int ACTION_NPC_16_TANGYUAN_DEAD_LEFT = 20;
    public final static int ACTION_NPC_16_TANGYUAN_CRY = 21;
    public final static int ACTION_NPC_16_TANGYUAN_PANIC = 22;
    public final static int ACTION_NPC_16_TANGYUAN_BETHROW = 23;
    public final static int ACTION_NPC_16_TANGYUAN_JUMP_TWICE_UP = 24;
    //animation:NPC_17_MERCHANT
    public final static int ANIM_NPC_17_MERCHANT = 30;
    public final static int ACTION_NPC_17_MERCHANT_STAND_UP = 0;
    public final static int ACTION_NPC_17_MERCHANT_STAND_RIGHT = 1;
    public final static int ACTION_NPC_17_MERCHANT_STAND_DOWN = 2;
    public final static int ACTION_NPC_17_MERCHANT_STAND_LEFT = 3;
    public final static int ACTION_NPC_17_MERCHANT_WALK_UP = 4;
    public final static int ACTION_NPC_17_MERCHANT_WALK_RIGHT = 5;
    public final static int ACTION_NPC_17_MERCHANT_WALK_DOWN = 6;
    public final static int ACTION_NPC_17_MERCHANT_WALK_LEFT = 7;
    public final static int ACTION_NPC_17_MERCHANT_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_17_MERCHANT_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_17_MERCHANT_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_17_MERCHANT_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_17_MERCHANT_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_17_MERCHANT_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_17_MERCHANT_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_17_MERCHANT_TRANSPORT_MOVE_LEFT = 15;
    //animation:NPC_18_JUNWUYIN
    public final static int ANIM_NPC_18_JUNWUYIN = 31;
    public final static int ACTION_NPC_18_JUNWUYIN_STAND_UP = 0;
    public final static int ACTION_NPC_18_JUNWUYIN_STAND_RIGHT = 1;
    public final static int ACTION_NPC_18_JUNWUYIN_STAND_DOWN = 2;
    public final static int ACTION_NPC_18_JUNWUYIN_STAND_LEFT = 3;
    public final static int ACTION_NPC_18_JUNWUYIN_WALK_UP = 4;
    public final static int ACTION_NPC_18_JUNWUYIN_WALK_RIGHT = 5;
    public final static int ACTION_NPC_18_JUNWUYIN_WALK_DOWN = 6;
    public final static int ACTION_NPC_18_JUNWUYIN_WALK_LEFT = 7;
    public final static int ACTION_NPC_18_JUNWUYIN_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_18_JUNWUYIN_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_18_JUNWUYIN_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_18_JUNWUYIN_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_18_JUNWUYIN_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_18_JUNWUYIN_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_18_JUNWUYIN_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_18_JUNWUYIN_TRANSPORT_MOVE_LEFT = 15;
    //animation:NPC_19_TEENAGER
    public final static int ANIM_NPC_19_TEENAGER = 32;
    public final static int ACTION_NPC_19_TEENAGER_STAND_UP = 0;
    public final static int ACTION_NPC_19_TEENAGER_STAND_RIGHT = 1;
    public final static int ACTION_NPC_19_TEENAGER_STAND_DOWN = 2;
    public final static int ACTION_NPC_19_TEENAGER_STAND_LEFT = 3;
    public final static int ACTION_NPC_19_TEENAGER_WALK_UP = 4;
    public final static int ACTION_NPC_19_TEENAGER_WALK_RIGHT = 5;
    public final static int ACTION_NPC_19_TEENAGER_WALK_DOWN = 6;
    public final static int ACTION_NPC_19_TEENAGER_WALK_LEFT = 7;
    public final static int ACTION_NPC_19_TEENAGER_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_19_TEENAGER_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_19_TEENAGER_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_19_TEENAGER_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_19_TEENAGER_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_19_TEENAGER_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_19_TEENAGER_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_19_TEENAGER_TRANSPORT_MOVE_LEFT = 15;
    //animation:NPC_20_MENGHAIZHOU
    public final static int ANIM_NPC_20_MENGHAIZHOU = 33;
    public final static int ACTION_NPC_20_MENGHAIZHOU_STAND_UP = 0;
    public final static int ACTION_NPC_20_MENGHAIZHOU_STAND_RIGHT = 1;
    public final static int ACTION_NPC_20_MENGHAIZHOU_STAND_DOWN = 2;
    public final static int ACTION_NPC_20_MENGHAIZHOU_STAND_LEFT = 3;
    public final static int ACTION_NPC_20_MENGHAIZHOU_WALK_UP = 4;
    public final static int ACTION_NPC_20_MENGHAIZHOU_WALK_RIGHT = 5;
    public final static int ACTION_NPC_20_MENGHAIZHOU_WALK_DOWN = 6;
    public final static int ACTION_NPC_20_MENGHAIZHOU_WALK_LEFT = 7;
    public final static int ACTION_NPC_20_MENGHAIZHOU_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_20_MENGHAIZHOU_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_20_MENGHAIZHOU_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_20_MENGHAIZHOU_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_20_MENGHAIZHOU_TRANSPORT_MOV_UP = 12;
    public final static int ACTION_NPC_20_MENGHAIZHOU_TRANSPORT_MOV_RIGHT = 13;
    public final static int ACTION_NPC_20_MENGHAIZHOU_TRANSPORT_MOV_DOWN = 14;
    public final static int ACTION_NPC_20_MENGHAIZHOU_TRANSPORT_MOV_LEFT = 15;
    public final static int ACTION_NPC_20_MENGHAIZHOU_DEAD = 16;
    public final static int ACTION_NPC_20_MENGHAIZHOU_ANGRY = 17;
    public final static int ACTION_NPC_20_MENGHAIZHOU_111 = 18;
    //animation:NPC_21_LIFENG
    public final static int ANIM_NPC_21_LIFENG = 34;
    public final static int ACTION_NPC_21_LIFENG_STAND_UP = 0;
    public final static int ACTION_NPC_21_LIFENG_STAND_RIGHT = 1;
    public final static int ACTION_NPC_21_LIFENG_STAND_DOWN = 2;
    public final static int ACTION_NPC_21_LIFENG_STAND_LEFT = 3;
    public final static int ACTION_NPC_21_LIFENG_WALK_UP = 4;
    public final static int ACTION_NPC_21_LIFENG_WALK_RIGHT = 5;
    public final static int ACTION_NPC_21_LIFENG_WALK_DOWN = 6;
    public final static int ACTION_NPC_21_LIFENG_WALK_LEFT = 7;
    public final static int ACTION_NPC_21_LIFENG_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_21_LIFENG_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_21_LIFENG_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_21_LIFENG_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_21_LIFENG_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_21_LIFENG_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_21_LIFENG_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_21_LIFENG_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_NPC_21_LIFENG_DEAD = 16;
    //animation:NPC_22_XIAOHAN
    public final static int ANIM_NPC_22_XIAOHAN = 35;
    public final static int ACTION_NPC_22_XIAOHAN_STAND_UP = 0;
    public final static int ACTION_NPC_22_XIAOHAN_STAND_RIGHT = 1;
    public final static int ACTION_NPC_22_XIAOHAN_STAND_DOWN = 2;
    public final static int ACTION_NPC_22_XIAOHAN_STAND_LEFT = 3;
    public final static int ACTION_NPC_22_XIAOHAN_WALK_UP = 4;
    public final static int ACTION_NPC_22_XIAOHAN_WALK_RIGHT = 5;
    public final static int ACTION_NPC_22_XIAOHAN_WALK_DOWN = 6;
    public final static int ACTION_NPC_22_XIAOHAN_WALK_LEFT = 7;
    public final static int ACTION_NPC_22_XIAOHAN_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_22_XIAOHAN_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_22_XIAOHAN_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_22_XIAOHAN_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_22_XIAOHAN_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_22_XIAOHAN_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_22_XIAOHAN_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_22_XIAOHAN_TRANSPORT_MOVE_LEFT = 15;
    //animation:NPC_23_SHAOBANGZHU
    public final static int ANIM_NPC_23_SHAOBANGZHU = 36;
    public final static int ACTION_NPC_23_SHAOBANGZHU_STAND_UP = 0;
    public final static int ACTION_NPC_23_SHAOBANGZHU_STAND_RIGHT = 1;
    public final static int ACTION_NPC_23_SHAOBANGZHU_STAND_DOWN = 2;
    public final static int ACTION_NPC_23_SHAOBANGZHU_STAND_LEFT = 3;
    public final static int ACTION_NPC_23_SHAOBANGZHU_WALK_UP = 4;
    public final static int ACTION_NPC_23_SHAOBANGZHU_WALK_RIGHT = 5;
    public final static int ACTION_NPC_23_SHAOBANGZHU_WALK_DOWN = 6;
    public final static int ACTION_NPC_23_SHAOBANGZHU_WALK_LEFT = 7;
    public final static int ACTION_NPC_23_SHAOBANGZHU_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_23_SHAOBANGZHU_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_23_SHAOBANGZHU_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_23_SHAOBANGZHU_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_23_SHAOBANGZHU_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_23_SHAOBANGZHU_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_23_SHAOBANGZHU_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_23_SHAOBANGZHU_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_NPC_23_SHAOBANGZHU_ANGRY_LEFT = 16;
    public final static int ACTION_NPC_23_SHAOBANGZHU_JUMP_TWICE_LEFT = 17;
    //animation:NPC_24_QINHU
    public final static int ANIM_NPC_24_QINHU = 37;
    public final static int ACTION_NPC_24_QINHU_STAND_UP = 0;
    public final static int ACTION_NPC_24_QINHU_STAND_RIGHT = 1;
    public final static int ACTION_NPC_24_QINHU_STAND_DOWN = 2;
    public final static int ACTION_NPC_24_QINHU_STAND_LEFT = 3;
    public final static int ACTION_NPC_24_QINHU_WALK_UP = 4;
    public final static int ACTION_NPC_24_QINHU_WALK_RIGHT = 5;
    public final static int ACTION_NPC_24_QINHU_WALK_DOWN = 6;
    public final static int ACTION_NPC_24_QINHU_WALK_LEFT = 7;
    public final static int ACTION_NPC_24_QINHU_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_24_QINHU_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_24_QINHU_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_24_QINHU_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_24_QINHU_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_24_QINHU_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_24_QINHU_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_24_QINHU_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_NPC_24_QINHU_FAILED = 16;
    //animation:NPC_25_DOCTOR2
    public final static int ANIM_NPC_25_DOCTOR2 = 38;
    public final static int ACTION_NPC_25_DOCTOR2_STAND_UP = 0;
    public final static int ACTION_NPC_25_DOCTOR2_STAND_RIGHT = 1;
    public final static int ACTION_NPC_25_DOCTOR2_STAND_DOWN = 2;
    public final static int ACTION_NPC_25_DOCTOR2_STAND_LEFT = 3;
    public final static int ACTION_NPC_25_DOCTOR2_WALK_UP = 4;
    public final static int ACTION_NPC_25_DOCTOR2_WALK_RIGHT = 5;
    public final static int ACTION_NPC_25_DOCTOR2_WALK_DOWN = 6;
    public final static int ACTION_NPC_25_DOCTOR2_WALK_LEFT = 7;
    public final static int ACTION_NPC_25_DOCTOR2_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_25_DOCTOR2_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_25_DOCTOR2_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_25_DOCTOR2_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_25_DOCTOR2_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_25_DOCTOR2_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_25_DOCTOR2_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_25_DOCTOR2_TRANSPORT_MOVE_LEFT = 15;
    //animation:NPC_26_BIGMAN
    public final static int ANIM_NPC_26_BIGMAN = 39;
    public final static int ACTION_NPC_26_BIGMAN_STAND_UP = 0;
    public final static int ACTION_NPC_26_BIGMAN_STAND_RIGHT = 1;
    public final static int ACTION_NPC_26_BIGMAN_STAND_DOWN = 2;
    public final static int ACTION_NPC_26_BIGMAN_STAND_LEFT = 3;
    public final static int ACTION_NPC_26_BIGMAN_WALK_UP = 4;
    public final static int ACTION_NPC_26_BIGMAN_WALK_RIGHT = 5;
    public final static int ACTION_NPC_26_BIGMAN_WALK_DOWN = 6;
    public final static int ACTION_NPC_26_BIGMAN_WALK_LEFT = 7;
    public final static int ACTION_NPC_26_BIGMAN_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_26_BIGMAN_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_26_BIGMAN_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_26_BIGMAN_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_26_BIGMAN_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_26_BIGMAN_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_26_BIGMAN_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_26_BIGMAN_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_NPC_26_BIGMAN_PUSHED = 16;
    //animation:NPC_27_XIONGKAISHAN
    public final static int ANIM_NPC_27_XIONGKAISHAN = 40;
    public final static int ACTION_NPC_27_XIONGKAISHAN_STAND_UP = 0;
    public final static int ACTION_NPC_27_XIONGKAISHAN_STAND_RIGHT = 1;
    public final static int ACTION_NPC_27_XIONGKAISHAN_STAND_DOWN = 2;
    public final static int ACTION_NPC_27_XIONGKAISHAN_STAND_LEFT = 3;
    public final static int ACTION_NPC_27_XIONGKAISHAN_WALK_UP = 4;
    public final static int ACTION_NPC_27_XIONGKAISHAN_WALK_RIGHT = 5;
    public final static int ACTION_NPC_27_XIONGKAISHAN_WALK_DOWN = 6;
    public final static int ACTION_NPC_27_XIONGKAISHAN_WALK_LEFT = 7;
    public final static int ACTION_NPC_27_XIONGKAISHAN_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_27_XIONGKAISHAN_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_27_XIONGKAISHAN_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_27_XIONGKAISHAN_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_27_XIONGKAISHAN_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_27_XIONGKAISHAN_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_27_XIONGKAISHAN_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_27_XIONGKAISHAN_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_NPC_27_XIONGKAISHAN_16 = 16;
    public final static int ACTION_NPC_27_XIONGKAISHAN_17 = 17;
    public final static int ACTION_NPC_27_XIONGKAISHAN_18 = 18;
    //animation:NPC_28_HECHONGXIAO
    public final static int ANIM_NPC_28_HECHONGXIAO = 41;
    public final static int ACTION_NPC_28_HECHONGXIAO_STAND_UP = 0;
    public final static int ACTION_NPC_28_HECHONGXIAO_STAND_RIGHT = 1;
    public final static int ACTION_NPC_28_HECHONGXIAO_STAND_DOWN = 2;
    public final static int ACTION_NPC_28_HECHONGXIAO_STAND_LEFT = 3;
    public final static int ACTION_NPC_28_HECHONGXIAO_WALK_UP = 4;
    public final static int ACTION_NPC_28_HECHONGXIAO_WALK_RIGHT = 5;
    public final static int ACTION_NPC_28_HECHONGXIAO_WALK_DOWN = 6;
    public final static int ACTION_NPC_28_HECHONGXIAO_WALK_LEFT = 7;
    public final static int ACTION_NPC_28_HECHONGXIAO_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_28_HECHONGXIAO_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_28_HECHONGXIAO_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_28_HECHONGXIAO_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_28_HECHONGXIAO_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_28_HECHONGXIAO_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_28_HECHONGXIAO_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_28_HECHONGXIAO_TRANSPORT_MOVE_LEFT = 15;
    //animation:NPC_29_QIANXUN
    public final static int ANIM_NPC_29_QIANXUN = 42;
    public final static int ACTION_NPC_29_QIANXUN_STAND_UP = 0;
    public final static int ACTION_NPC_29_QIANXUN_STAND_RIGHT = 1;
    public final static int ACTION_NPC_29_QIANXUN_STAND_DOWN = 2;
    public final static int ACTION_NPC_29_QIANXUN_STAND_LEFT = 3;
    public final static int ACTION_NPC_29_QIANXUN_WALK_UP = 4;
    public final static int ACTION_NPC_29_QIANXUN_WALK_RIGHT = 5;
    public final static int ACTION_NPC_29_QIANXUN_WALK_DOWN = 6;
    public final static int ACTION_NPC_29_QIANXUN_WALK_LEFT = 7;
    public final static int ACTION_NPC_29_QIANXUN_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_29_QIANXUN_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_29_QIANXUN_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_29_QIANXUN_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_29_QIANXUN_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_29_QIANXUN_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_29_QIANXUN_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_29_QIANXUN_TRANSPORT_MOVE_LEFT = 15;
    //animation:OBJECT_00_SIGN
    public final static int ANIM_OBJECT_00_SIGN = 43;
    public final static int ACTION_OBJECT_00_SIGN_ARMOR = 0;
    public final static int ACTION_OBJECT_00_SIGN_WEAPON = 1;
    public final static int ACTION_OBJECT_00_SIGN_MEDICINE = 2;
    public final static int ACTION_OBJECT_00_SIGN_SKILL = 3;
    public final static int ACTION_OBJECT_00_SIGN_MISC = 4;
    //animation:OBJECT_27_CG_START
    public final static int ANIM_OBJECT_27_CG_START = 44;
    public final static int ACTION_OBJECT_27_CG_START_CG = 0;
    //animation:OBJECT_02_TREE
    public final static int ANIM_OBJECT_02_TREE = 45;
    public final static int ACTION_OBJECT_02_TREE_GREEN_00 = 0;
    public final static int ACTION_OBJECT_02_TREE_GREEN_01 = 1;
    public final static int ACTION_OBJECT_02_TREE_GREEN_02 = 2;
    public final static int ACTION_OBJECT_02_TREE_GREEN_03 = 3;
    public final static int ACTION_OBJECT_02_TREE_GREEN_TOWN_00 = 4;
    public final static int ACTION_OBJECT_02_TREE_GREEN_TOWN_01 = 5;
    public final static int ACTION_OBJECT_02_TREE_GREEN_TOWN_02 = 6;
    public final static int ACTION_OBJECT_02_TREE_GREEN_TOWN_03 = 7;
    public final static int ACTION_OBJECT_02_TREE_FLOWER_00 = 8;
    public final static int ACTION_OBJECT_02_TREE_FLOWER_01 = 9;
    public final static int ACTION_OBJECT_02_TREE_FLOWER_02 = 10;
    public final static int ACTION_OBJECT_02_TREE_FLOWER_03 = 11;
    public final static int ACTION_OBJECT_02_TREE_FLOWER_TOWN_00 = 12;
    public final static int ACTION_OBJECT_02_TREE_FLOWER_TOWN_01 = 13;
    public final static int ACTION_OBJECT_02_TREE_FLOWER_TOWN_02 = 14;
    public final static int ACTION_OBJECT_02_TREE_FLOWER_TOWN_03 = 15;
    public final static int ACTION_OBJECT_02_TREE_GREEN_SMALL_00L = 16;
    public final static int ACTION_OBJECT_02_TREE_GREEN_SMALL_01 = 17;
    public final static int ACTION_OBJECT_02_TREE_GREEN_SMALL_02 = 18;
    public final static int ACTION_OBJECT_02_TREE_FLOWER_SMALL_00 = 19;
    public final static int ACTION_OBJECT_02_TREE_FLOWER_SMALL_01 = 20;
    public final static int ACTION_OBJECT_02_TREE_FLOWER_SMALL_02 = 21;
    //animation:NPC_30_ELDER
    public final static int ANIM_NPC_30_ELDER = 46;
    public final static int ACTION_NPC_30_ELDER_STAND_UP = 0;
    public final static int ACTION_NPC_30_ELDER_STAND_RIGHT = 1;
    public final static int ACTION_NPC_30_ELDER_STAND_DOWN = 2;
    public final static int ACTION_NPC_30_ELDER_STAND_LEFT = 3;
    public final static int ACTION_NPC_30_ELDER_WALK_UP = 4;
    public final static int ACTION_NPC_30_ELDER_WALK_RIGHT = 5;
    public final static int ACTION_NPC_30_ELDER_WALK_DOWN = 6;
    public final static int ACTION_NPC_30_ELDER_WALK_LEFT = 7;
    public final static int ACTION_NPC_30_ELDER_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_30_ELDER_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_30_ELDER_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_30_ELDER_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_30_ELDER_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_30_ELDER_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_30_ELDER_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_30_ELDER_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_NPC_30_ELDER_DIAGUP = 16;
    public final static int ACTION_NPC_30_ELDER_HITWALL = 17;
    public final static int ACTION_NPC_30_ELDER_BEHIT = 18;
    //animation:NPC_31_MONSTER
    public final static int ANIM_NPC_31_MONSTER = 47;
    public final static int ACTION_NPC_31_MONSTER_STAND_UP = 0;
    public final static int ACTION_NPC_31_MONSTER_STAND_RIGHT = 1;
    public final static int ACTION_NPC_31_MONSTER_STAND_DOWN = 2;
    public final static int ACTION_NPC_31_MONSTER_STAND_LEFT = 3;
    public final static int ACTION_NPC_31_MONSTER_WALK_UP = 4;
    public final static int ACTION_NPC_31_MONSTER_WALK_RIGHT = 5;
    public final static int ACTION_NPC_31_MONSTER_WALK_DOWN = 6;
    public final static int ACTION_NPC_31_MONSTER_WALK_LEFT = 7;
    public final static int ACTION_NPC_31_MONSTER_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_31_MONSTER_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_31_MONSTER_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_31_MONSTER_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_31_MONSTER_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_31_MONSTER_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_31_MONSTER_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_31_MONSTER_TRANSPORT_MOVE_LEFT = 15;
    public final static int ACTION_NPC_31_MONSTER_APEAR_RIGHT = 16;
    public final static int ACTION_NPC_31_MONSTER_DISAPPEAR_RIGHT = 17;
    public final static int ACTION_NPC_31_MONSTER_SHAKE_RIGHT = 18;
    //animation:OBJECT_03_TREESHADOW
    public final static int ANIM_OBJECT_03_TREESHADOW = 48;
    public final static int ACTION_OBJECT_03_TREESHADOW_BIG = 0;
    public final static int ACTION_OBJECT_03_TREESHADOW_MEDIUM = 1;
    public final static int ACTION_OBJECT_03_TREESHADOW_SMALL = 2;
    //animation:OBJECT_04_TRANSPORT
    public final static int ANIM_OBJECT_04_TRANSPORT = 49;
    public final static int ACTION_OBJECT_04_TRANSPORT_STATIC = 0;
    public final static int ACTION_OBJECT_04_TRANSPORT_GO = 1;
    public final static int ACTION_OBJECT_04_TRANSPORT_COME = 2;
    public final static int ACTION_OBJECT_04_TRANSPORT_RIGHT = 3;
    public final static int ACTION_OBJECT_04_TRANSPORT_LEFT = 4;
    //animation:OBJECT_05_SPARK
    public final static int ANIM_OBJECT_05_SPARK = 50;
    public final static int ACTION_OBJECT_05_SPARK_SPARK = 0;
    //animation:OBJECT_06_ARROW
    public final static int ANIM_OBJECT_06_ARROW = 51;
    public final static int ACTION_OBJECT_06_ARROW_UP = 0;
    public final static int ACTION_OBJECT_06_ARROW_RIGHT = 1;
    public final static int ACTION_OBJECT_06_ARROW_DOWN = 2;
    public final static int ACTION_OBJECT_06_ARROW_LEFT = 3;
    public final static int ACTION_OBJECT_06_ARROW_1 = 4;
    public final static int ACTION_OBJECT_06_ARROW_2 = 5;
    //animation:OBJECT_07_ROOMNOBLE
    public final static int ANIM_OBJECT_07_ROOMNOBLE = 52;
    public final static int ACTION_OBJECT_07_ROOMNOBLE_SCREEN_SINGLE = 0;
    public final static int ACTION_OBJECT_07_ROOMNOBLE_SCREEN_DOUBLE = 1;
    public final static int ACTION_OBJECT_07_ROOMNOBLE_TABLE = 2;
    public final static int ACTION_OBJECT_07_ROOMNOBLE_CHAIR = 3;
    public final static int ACTION_OBJECT_07_ROOMNOBLE_LAMP1 = 4;
    public final static int ACTION_OBJECT_07_ROOMNOBLE_LAMP2 = 5;
    public final static int ACTION_OBJECT_07_ROOMNOBLE_COLUMN = 6;
    public final static int ACTION_OBJECT_07_ROOMNOBLE_FLOWER = 7;
    public final static int ACTION_OBJECT_07_ROOMNOBLE_FAN = 8;
    public final static int ACTION_OBJECT_07_ROOMNOBLE_CURTAIN = 9;
    public final static int ACTION_OBJECT_07_ROOMNOBLE_MIRROR = 10;
    //animation:OBJECT_08_ROOMNORMAL
    public final static int ANIM_OBJECT_08_ROOMNORMAL = 53;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_CHAIR = 0;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_TABLE1 = 1;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_TABLE2 = 2;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_TABLE3 = 3;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_TABLE4 = 4;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_TABLE5 = 5;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_TABLE6 = 6;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_TABLE7 = 7;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_JAR1 = 8;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_JAR2 = 9;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_JAR3 = 10;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_JAR4 = 11;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_BUCKET = 12;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_WOOD1 = 13;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_WOOD2 = 14;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_CHEST1 = 15;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_CHEST2 = 16;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_FLOWER1 = 17;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_FLOWER2 = 18;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_FLOWER3 = 19;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_FLOWER4 = 20;
    public final static int ACTION_OBJECT_08_ROOMNORMAL_WEAPONS = 21;
    //animation:OBJECT_09_YARD
    public final static int ANIM_OBJECT_09_YARD = 54;
    public final static int ACTION_OBJECT_09_YARD_TABLE = 0;
    public final static int ACTION_OBJECT_09_YARD_STONE1 = 1;
    public final static int ACTION_OBJECT_09_YARD_STONE1_WATER = 2;
    public final static int ACTION_OBJECT_09_YARD_STONE2 = 3;
    public final static int ACTION_OBJECT_09_YARD_STONE2_WATER = 4;
    public final static int ACTION_OBJECT_09_YARD_STONE3 = 5;
    public final static int ACTION_OBJECT_09_YARD_STONE3_WATER = 6;
    public final static int ACTION_OBJECT_09_YARD_STONE4 = 7;
    public final static int ACTION_OBJECT_09_YARD_STONE5 = 8;
    public final static int ACTION_OBJECT_09_YARD_STONE5_WATER = 9;
    public final static int ACTION_OBJECT_09_YARD_STONE6 = 10;
    public final static int ACTION_OBJECT_09_YARD_STONE6_WATER = 11;
    public final static int ACTION_OBJECT_09_YARD_STONE7 = 12;
    public final static int ACTION_OBJECT_09_YARD_STONE7_WATER = 13;
    //animation:OBJECT_10_MARKET
    public final static int ANIM_OBJECT_10_MARKET = 55;
    public final static int ACTION_OBJECT_10_MARKET_MISC1 = 0;
    public final static int ACTION_OBJECT_10_MARKET_MISC2 = 1;
    public final static int ACTION_OBJECT_10_MARKET_BAG1 = 2;
    public final static int ACTION_OBJECT_10_MARKET_BAG2 = 3;
    public final static int ACTION_OBJECT_10_MARKET_BAG3 = 4;
    public final static int ACTION_OBJECT_10_MARKET_STALL1 = 5;
    public final static int ACTION_OBJECT_10_MARKET_STALL2 = 6;
    public final static int ACTION_OBJECT_10_MARKET_STALL3 = 7;
    public final static int ACTION_OBJECT_10_MARKET_STALL4 = 8;
    public final static int ACTION_OBJECT_10_MARKET_STALL5 = 9;
    public final static int ACTION_OBJECT_10_MARKET_STOVE1 = 10;
    public final static int ACTION_OBJECT_10_MARKET_STOVE2 = 11;
    public final static int ACTION_OBJECT_10_MARKET_STOVE3 = 12;
    public final static int ACTION_OBJECT_10_MARKET_STALL6 = 13;
    public final static int ACTION_OBJECT_10_MARKET_STALL7 = 14;
    public final static int ACTION_OBJECT_10_MARKET_STALL8 = 15;
    public final static int ACTION_OBJECT_10_MARKET_WAGON1 = 16;
    public final static int ACTION_OBJECT_10_MARKET_WAGON2 = 17;
    public final static int ACTION_OBJECT_10_MARKET_WAGON3 = 18;
    public final static int ACTION_OBJECT_10_MARKET_RACK = 19;
    public final static int ACTION_OBJECT_10_MARKET_SIGN = 20;
    public final static int ACTION_OBJECT_10_MARKET_SIGN_SMITH = 21;
    public final static int ACTION_OBJECT_10_MARKET_SIGN_MEDICINE = 22;
    public final static int ACTION_OBJECT_10_MARKET_SIGN_FOOD = 23;
    public final static int ACTION_OBJECT_10_MARKET_SIGN_GAMBLE = 24;
    public final static int ACTION_OBJECT_10_MARKET_ROOF = 25;
    //animation:OBJECT_11_FIELD
    public final static int ANIM_OBJECT_11_FIELD = 56;
    public final static int ACTION_OBJECT_11_FIELD_WATERFALL1 = 0;
    public final static int ACTION_OBJECT_11_FIELD_WATERFALL2 = 1;
    public final static int ACTION_OBJECT_11_FIELD_GRAVE = 2;
    public final static int ACTION_OBJECT_11_FIELD_LOTUS1 = 3;
    public final static int ACTION_OBJECT_11_FIELD_LOTUS2 = 4;
    public final static int ACTION_OBJECT_11_FIELD_LOTUS3 = 5;
    public final static int ACTION_OBJECT_11_FIELD_LOTUS4 = 6;
    public final static int ACTION_OBJECT_11_FIELD_BUSH1 = 7;
    public final static int ACTION_OBJECT_11_FIELD_BUSH2 = 8;
    public final static int ACTION_OBJECT_11_FIELD_BUSH3 = 9;
    public final static int ACTION_OBJECT_11_FIELD_FLOWER = 10;
    public final static int ACTION_OBJECT_11_FIELD_GRASS1 = 11;
    public final static int ACTION_OBJECT_11_FIELD_GRASS2 = 12;
    public final static int ACTION_OBJECT_11_FIELD_GRASS3 = 13;
    public final static int ACTION_OBJECT_11_FIELD_FISH = 14;
    public final static int ACTION_OBJECT_11_FIELD_WATERFALL3 = 15;
    public final static int ACTION_OBJECT_11_FIELD_WATERFALL4 = 16;
    public final static int ACTION_OBJECT_11_FIELD_WATERFAL5 = 17;
    public final static int ACTION_OBJECT_11_FIELD_WATERFALL6 = 18;
    //animation:OBJECT_12_HALL
    public final static int ANIM_OBJECT_12_HALL = 57;
    public final static int ACTION_OBJECT_12_HALL_STARS = 0;
    public final static int ACTION_OBJECT_12_HALL_STOVE = 1;
    public final static int ACTION_OBJECT_12_HALL_DESK_BOOK = 2;
    public final static int ACTION_OBJECT_12_HALL_DESK_BOOK_RISE = 3;
    public final static int ACTION_OBJECT_12_HALL_DESK_BOOK_FLOAT = 4;
    public final static int ACTION_OBJECT_12_HALL_DESK_BOOK_FALL = 5;
    public final static int ACTION_OBJECT_12_HALL_SWORD = 6;
    public final static int ACTION_OBJECT_12_HALL_RUNEFIRE = 7;
    public final static int ACTION_OBJECT_12_HALL_RUNEFIRE_STATIC = 8;
    public final static int ACTION_OBJECT_12_HALL_RUNEFIRE_RISE = 9;
    public final static int ACTION_OBJECT_12_HALL_RUNEFIRE_FLOAT = 10;
    public final static int ACTION_OBJECT_12_HALL_STATUE1 = 11;
    public final static int ACTION_OBJECT_12_HALL_STATUE2 = 12;
    public final static int ACTION_OBJECT_12_HALL_STATUE3 = 13;
    public final static int ACTION_OBJECT_12_HALL_CENSER = 14;
    public final static int ACTION_OBJECT_12_HALL_DESK = 15;
    //animation:OBJECT_13_SHINING
    public final static int ANIM_OBJECT_13_SHINING = 58;
    public final static int ACTION_OBJECT_13_SHINING_SMALL = 0;
    public final static int ACTION_OBJECT_13_SHINING_MEDIUM = 1;
    public final static int ACTION_OBJECT_13_SHINING_BIG = 2;
    public final static int ACTION_OBJECT_13_SHINING_AREA = 3;
    //animation:NPC_32_BROTHERHOOD
    public final static int ANIM_NPC_32_BROTHERHOOD = 59;
    public final static int ACTION_NPC_32_BROTHERHOOD_STAND_UP = 0;
    public final static int ACTION_NPC_32_BROTHERHOOD_STAND_RIGHT = 1;
    public final static int ACTION_NPC_32_BROTHERHOOD_STAND_DOWN = 2;
    public final static int ACTION_NPC_32_BROTHERHOOD_STAND_LEFT = 3;
    public final static int ACTION_NPC_32_BROTHERHOOD_WALK_UP = 4;
    public final static int ACTION_NPC_32_BROTHERHOOD_WALK_RIGHT = 5;
    public final static int ACTION_NPC_32_BROTHERHOOD_WALK_DOWN = 6;
    public final static int ACTION_NPC_32_BROTHERHOOD_WALK_LEFT = 7;
    public final static int ACTION_NPC_32_BROTHERHOOD_TRANSPORT_UP = 8;
    public final static int ACTION_NPC_32_BROTHERHOOD_TRANSPORT_RIGHT = 9;
    public final static int ACTION_NPC_32_BROTHERHOOD_TRANSPORT_DOWN = 10;
    public final static int ACTION_NPC_32_BROTHERHOOD_TRANSPORT_LEFT = 11;
    public final static int ACTION_NPC_32_BROTHERHOOD_TRANSPORT_MOVE_UP = 12;
    public final static int ACTION_NPC_32_BROTHERHOOD_TRANSPORT_MOVE_RIGHT = 13;
    public final static int ACTION_NPC_32_BROTHERHOOD_TRANSPORT_MOVE_DOWN = 14;
    public final static int ACTION_NPC_32_BROTHERHOOD_TRANSPORT_MOVE_LEFT = 15;
    //animation:OBJECT_14_WARNING
    public final static int ANIM_OBJECT_14_WARNING = 60;
    public final static int ACTION_OBJECT_14_WARNING_0 = 0;
    public final static int ACTION_OBJECT_14_WARNING_1 = 1;
    //animation:EMOTION
    public final static int ANIM_EMOTION = 61;
    public final static int ACTION_EMOTION_ANGRY = 0;
    public final static int ACTION_EMOTION_RAGE = 1;
    public final static int ACTION_EMOTION_DISDAIN = 2;
    public final static int ACTION_EMOTION_AMAZE = 3;
    public final static int ACTION_EMOTION_CHAOS = 4;
    public final static int ACTION_EMOTION_HAPPY = 5;
    public final static int ACTION_EMOTION_SWEAT = 6;
    public final static int ACTION_EMOTION_CURIOUS = 7;
    public final static int ACTION_EMOTION_TALK = 8;
    //animation:OBJECT_15_FIGHTCHAOS
    public final static int ANIM_OBJECT_15_FIGHTCHAOS = 62;
    public final static int ACTION_OBJECT_15_FIGHTCHAOS_FIGHTING = 0;
    //animation:OBJECT_16_ENEMY
    public final static int ANIM_OBJECT_16_ENEMY = 63;
    public final static int ACTION_OBJECT_16_ENEMY_ENEMY = 0;
    //animation:OBJECT_17_MAP
    public final static int ANIM_OBJECT_17_MAP = 64;
    public final static int ACTION_OBJECT_17_MAP_ = 0;
    //animation:BOX
    public final static int ANIM_BOX = 65;
    public final static int ACTION_BOX_SMALL_CLOSED = 0;
    public final static int ACTION_BOX_SMALL_OPEN = 1;
    public final static int ACTION_BOX_BIG_CLOSED = 2;
    public final static int ACTION_BOX_BIG_OPEN = 3;
    //animation:OBJECT_18_MISSONMARK
    public final static int ANIM_OBJECT_18_MISSONMARK = 66;
    public final static int ACTION_OBJECT_18_MISSONMARK_MAINQUEST_READY = 0;
    public final static int ACTION_OBJECT_18_MISSONMARK_MAINQUEST_COMPLETE = 1;
    public final static int ACTION_OBJECT_18_MISSONMARK_QUEST_READY = 2;
    public final static int ACTION_OBJECT_18_MISSONMARK_QUEST_COMPLETE = 3;
    public final static int ACTION_OBJECT_18_MISSONMARK_QUEST_UNCOMPLETE = 4;
    //animation:OBJECT_19_SUPERJAR
    public final static int ANIM_OBJECT_19_SUPERJAR = 67;
    public final static int ACTION_OBJECT_19_SUPERJAR_NORMAL = 0;
    public final static int ACTION_OBJECT_19_SUPERJAR_RED = 1;
    public final static int ACTION_OBJECT_19_SUPERJAR_TIGER = 2;
    public final static int ACTION_OBJECT_19_SUPERJAR_BROKEN = 3;
    //animation:OBJECT_26_CG_DUGUXIAOYI
    public final static int ANIM_OBJECT_26_CG_DUGUXIAOYI = 68;
    public final static int ACTION_OBJECT_26_CG_DUGUXIAOYI_CG = 0;
    //animation:OBJECT_21_BURSTPOWER
    public final static int ANIM_OBJECT_21_BURSTPOWER = 69;
    public final static int ACTION_OBJECT_21_BURSTPOWER_BLUE = 0;
    public final static int ACTION_OBJECT_21_BURSTPOWER_YELLOW = 1;
    //animation:OBJECT_22_TOWER
    public final static int ANIM_OBJECT_22_TOWER = 70;
    public final static int ACTION_OBJECT_22_TOWER_FLOAT = 0;
    //animation:OBJECT_23_RUINS
    public final static int ANIM_OBJECT_23_RUINS = 71;
    public final static int ACTION_OBJECT_23_RUINS_SMOKE = 0;
    public final static int ACTION_OBJECT_23_RUINS_FIRE1 = 1;
    public final static int ACTION_OBJECT_23_RUINS_FIRE2 = 2;
    public final static int ACTION_OBJECT_23_RUINS_FIRESTRUCTURE1 = 3;
    public final static int ACTION_OBJECT_23_RUINS_FIRESTRUCTURE2 = 4;
    public final static int ACTION_OBJECT_23_RUINS_FIRECOLUMN1 = 5;
    public final static int ACTION_OBJECT_23_RUINS_FIRECOLUMN2 = 6;
    public final static int ACTION_OBJECT_23_RUINS_BROKENSTRUCTURE1 = 7;
    public final static int ACTION_OBJECT_23_RUINS_BROKENSTRUCTURE2 = 8;
    public final static int ACTION_OBJECT_23_RUINS_BROKENCOLUNM1 = 9;
    public final static int ACTION_OBJECT_23_RUINS_BROKENCOLUNM2 = 10;
    public final static int ACTION_OBJECT_23_RUINS_BROKENSTONE1 = 11;
    public final static int ACTION_OBJECT_23_RUINS_BROKENSTONE2 = 12;
    public final static int ACTION_OBJECT_23_RUINS_BROKENSTONE3 = 13;
    public final static int ACTION_OBJECT_23_RUINS_BLOOD1 = 14;
    public final static int ACTION_OBJECT_23_RUINS_BLOOD2 = 15;
    public final static int ACTION_OBJECT_23_RUINS_BLOOD3 = 16;
    public final static int ACTION_OBJECT_23_RUINS_BLOOD4 = 17;
    public final static int ACTION_OBJECT_23_RUINS_DIRTYGROUND = 18;
    public final static int ACTION_OBJECT_23_RUINS_BROKENGROUND = 19;
    //animation:OBJECT_24_CGANGERFIRE
    public final static int ANIM_OBJECT_24_CGANGERFIRE = 72;
    public final static int ACTION_OBJECT_24_CGANGERFIRE_ = 0;
    //animation:OBJECT_25_CG_GUANQINGHAN
    public final static int ANIM_OBJECT_25_CG_GUANQINGHAN = 73;
    public final static int ACTION_OBJECT_25_CG_GUANQINGHAN_CG = 0;
    //animation:UI_SMS
    public final static int ANIM_UI_SMS = 74;
    public final static int ACTION_UI_SMS_SMS = 0;
    //animation:BA_00
    public final static int ANIM_BA_00 = 75;
    public final static int ACTION_BA_00_WAIT = 0;
    public final static int ACTION_BA_00_HURT = 1;
    public final static int ACTION_BA_00_DODGE = 2;
    public final static int ACTION_BA_00_WEAK = 3;
    public final static int ACTION_BA_00_DIE = 4;
    public final static int ACTION_BA_00_WIN = 5;
    public final static int ACTION_BA_00_GUARD = 6;
    public final static int ACTION_BA_00_G_HURT = 7;
    public final static int ACTION_BA_00_ATT = 8;
    public final static int ACTION_BA_00_SKILL_SLASH = 9;
    public final static int ACTION_BA_00_SKILL_COLUMN = 10;
    public final static int ACTION_BA_00_SKILL_SNIPER = 11;
    public final static int ACTION_BA_00_SKILL_SPHERE = 12;
    //animation:BA_01
    public final static int ANIM_BA_01 = 76;
    public final static int ACTION_BA_01_WAIT = 0;
    public final static int ACTION_BA_01_HURT = 1;
    public final static int ACTION_BA_01_DODGE = 2;
    public final static int ACTION_BA_01_WEAK = 3;
    public final static int ACTION_BA_01_DIE = 4;
    public final static int ACTION_BA_01_WIN = 5;
    public final static int ACTION_BA_01_GUARD = 6;
    public final static int ACTION_BA_01_G_HURT = 7;
    public final static int ACTION_BA_01_ATT = 8;
    public final static int ACTION_BA_01_SKILL_0_SMILE = 9;
    public final static int ACTION_BA_01_SKILL_1_DANCE = 10;
    public final static int ACTION_BA_01_SKILL_2_SPEEDUP = 11;
    public final static int ACTION_BA_01_SKILL_3_PHOENIX = 12;
    //animation:BA_02
    public final static int ANIM_BA_02 = 77;
    public final static int ACTION_BA_02_WAIT = 0;
    public final static int ACTION_BA_02_HURT = 1;
    public final static int ACTION_BA_02_DODGE = 2;
    public final static int ACTION_BA_02_WEAK = 3;
    public final static int ACTION_BA_02_DIE = 4;
    public final static int ACTION_BA_02_WIN = 5;
    public final static int ACTION_BA_02_GUARD = 6;
    public final static int ACTION_BA_02_G_HURT = 7;
    public final static int ACTION_BA_02_ATT = 8;
    public final static int ACTION_BA_02_SKILL_0_HEALING = 9;
    public final static int ACTION_BA_02_SKILL_1_ICEWALL = 10;
    public final static int ACTION_BA_02_SKILL_2_BLIZZARD = 11;
    public final static int ACTION_BA_02_SKILL_3_CUREWIND = 12;
    public final static int ACTION_BA_02_SKILL_4_RESURRECT = 13;
    //animation:BA_03
    public final static int ANIM_BA_03 = 78;
    public final static int ACTION_BA_03_1 = 0;
    //animation:BA_04
    public final static int ANIM_BA_04 = 79;
    public final static int ACTION_BA_04_1 = 0;
    //animation:BA_05
    public final static int ANIM_BA_05 = 80;
    public final static int ACTION_BA_05_WAIT = 0;
    public final static int ACTION_BA_05_HURT = 1;
    public final static int ACTION_BA_05_DODGE = 2;
    public final static int ACTION_BA_05_WEAK = 3;
    public final static int ACTION_BA_05_DIE = 4;
    public final static int ACTION_BA_05_WIN = 5;
    public final static int ACTION_BA_05_GUARD = 6;
    public final static int ACTION_BA_05_G_HURT = 7;
    public final static int ACTION_BA_05_ATT = 8;
    public final static int ACTION_BA_05_SKILL_0_HEALING = 9;
    public final static int ACTION_BA_05_SKILL_1_ICEWALL = 10;
    public final static int ACTION_BA_05_SKILL_2_BLIZZARD = 11;
    public final static int ACTION_BA_05_SKILL_3_CUREWIND = 12;
    public final static int ACTION_BA_05_SKILL_4_RESURRECT = 13;
    //animation:ENEMY_00
    public final static int ANIM_ENEMY_00 = 81;
    public final static int ACTION_ENEMY_00_WAIT = 0;
    public final static int ACTION_ENEMY_00_HURT = 1;
    public final static int ACTION_ENEMY_00_DODGE = 2;
    public final static int ACTION_ENEMY_00_WEAK = 3;
    public final static int ACTION_ENEMY_00_DIE = 4;
    public final static int ACTION_ENEMY_00_WIN = 5;
    public final static int ACTION_ENEMY_00_GUARD = 6;
    public final static int ACTION_ENEMY_00_G_HURT = 7;
    public final static int ACTION_ENEMY_00_ATT = 8;
    public final static int ACTION_ENEMY_00_HURT222 = 9;
    //animation:ENEMY_01
    public final static int ANIM_ENEMY_01 = 82;
    public final static int ACTION_ENEMY_01_WAIT = 0;
    public final static int ACTION_ENEMY_01_HURT = 1;
    public final static int ACTION_ENEMY_01_DODGE = 2;
    public final static int ACTION_ENEMY_01_WEAK = 3;
    public final static int ACTION_ENEMY_01_DIE = 4;
    public final static int ACTION_ENEMY_01_WIN = 5;
    public final static int ACTION_ENEMY_01_GUARD = 6;
    public final static int ACTION_ENEMY_01_G_HURT = 7;
    public final static int ACTION_ENEMY_01_ATT = 8;
    public final static int ACTION_ENEMY_01_DOUBLEATTACKAAA = 9;
    public final static int ACTION_ENEMY_01_DOUBLEATTACK = 10;
    //animation:ENEMY_02
    public final static int ANIM_ENEMY_02 = 83;
    public final static int ACTION_ENEMY_02_WAIT = 0;
    public final static int ACTION_ENEMY_02_HURT = 1;
    public final static int ACTION_ENEMY_02_DODGE = 2;
    public final static int ACTION_ENEMY_02_WEAK = 3;
    public final static int ACTION_ENEMY_02_DIE = 4;
    public final static int ACTION_ENEMY_02_WIN = 5;
    public final static int ACTION_ENEMY_02_GUARD = 6;
    public final static int ACTION_ENEMY_02_G_HURT = 7;
    public final static int ACTION_ENEMY_02_ATT = 8;
    public final static int ACTION_ENEMY_02_CAST = 9;
    public final static int ACTION_ENEMY_02_DOUBLEATTACK = 10;
    //animation:ENEMY_03
    public final static int ANIM_ENEMY_03 = 84;
    public final static int ACTION_ENEMY_03_WAIT = 0;
    public final static int ACTION_ENEMY_03_HURT = 1;
    public final static int ACTION_ENEMY_03_DODGE = 2;
    public final static int ACTION_ENEMY_03_WEAK = 3;
    public final static int ACTION_ENEMY_03_DIE = 4;
    public final static int ACTION_ENEMY_03_WIN = 5;
    public final static int ACTION_ENEMY_03_GUARD = 6;
    public final static int ACTION_ENEMY_03_G_HURT = 7;
    public final static int ACTION_ENEMY_03_ATT = 8;
    public final static int ACTION_ENEMY_03_CAST = 9;
    public final static int ACTION_ENEMY_03_DOUBLEATTACK = 10;
    public final static int ACTION_ENEMY_03_TRIPPLEATTACK = 11;
    //animation:ENEMY_04
    public final static int ANIM_ENEMY_04 = 85;
    public final static int ACTION_ENEMY_04_WAIT = 0;
    public final static int ACTION_ENEMY_04_HURT = 1;
    public final static int ACTION_ENEMY_04_DODGE = 2;
    public final static int ACTION_ENEMY_04_WEAK = 3;
    public final static int ACTION_ENEMY_04_DIE = 4;
    public final static int ACTION_ENEMY_04_WIN = 5;
    public final static int ACTION_ENEMY_04_GUARD = 6;
    public final static int ACTION_ENEMY_04_G_HURT = 7;
    public final static int ACTION_ENEMY_04_ATT = 8;
    public final static int ACTION_ENEMY_04_CAST = 9;
    public final static int ACTION_ENEMY_04_DOUBLEATTACK = 10;
    public final static int ACTION_ENEMY_04_TRIPPLEATTACK = 11;
    public final static int ACTION_ENEMY_04_4ATTACK = 12;
    //animation:ENEMY_05
    public final static int ANIM_ENEMY_05 = 86;
    public final static int ACTION_ENEMY_05_WAIT = 0;
    public final static int ACTION_ENEMY_05_HURT = 1;
    public final static int ACTION_ENEMY_05_DODGE = 2;
    public final static int ACTION_ENEMY_05_WEAK = 3;
    public final static int ACTION_ENEMY_05_DIE = 4;
    public final static int ACTION_ENEMY_05_WIN = 5;
    public final static int ACTION_ENEMY_05_GUARD = 6;
    public final static int ACTION_ENEMY_05_G_HURT = 7;
    public final static int ACTION_ENEMY_05_ATT = 8;
    public final static int ACTION_ENEMY_05_CAST = 9;
    public final static int ACTION_ENEMY_05_DOUBLEATTACK = 10;
    public final static int ACTION_ENEMY_05_TRIPPLEATTACK = 11;
    //animation:ENEMY_06
    public final static int ANIM_ENEMY_06 = 87;
    public final static int ACTION_ENEMY_06_WAIT = 0;
    public final static int ACTION_ENEMY_06_HURT = 1;
    public final static int ACTION_ENEMY_06_DODGE = 2;
    public final static int ACTION_ENEMY_06_WEAK = 3;
    public final static int ACTION_ENEMY_06_DIE = 4;
    public final static int ACTION_ENEMY_06_WIN = 5;
    public final static int ACTION_ENEMY_06_GUARD = 6;
    public final static int ACTION_ENEMY_06_G_HURT = 7;
    public final static int ACTION_ENEMY_06_ATT = 8;
    //animation:ENEMY_07
    public final static int ANIM_ENEMY_07 = 88;
    public final static int ACTION_ENEMY_07_WAIT = 0;
    public final static int ACTION_ENEMY_07_HURT = 1;
    public final static int ACTION_ENEMY_07_DODGE = 2;
    public final static int ACTION_ENEMY_07_WEAK = 3;
    public final static int ACTION_ENEMY_07_DIE = 4;
    public final static int ACTION_ENEMY_07_WIN = 5;
    public final static int ACTION_ENEMY_07_GUARD = 6;
    public final static int ACTION_ENEMY_07_G_HURT = 7;
    public final static int ACTION_ENEMY_07_ATT = 8;
    public final static int ACTION_ENEMY_07_CAST = 9;
    public final static int ACTION_ENEMY_07_DOUBLEATTACT = 10;
    public final static int ACTION_ENEMY_07_TRIPPLEATTACT = 11;
    //animation:ENEMY_08
    public final static int ANIM_ENEMY_08 = 89;
    public final static int ACTION_ENEMY_08_WAIT = 0;
    public final static int ACTION_ENEMY_08_HURT = 1;
    public final static int ACTION_ENEMY_08_DODGE = 2;
    public final static int ACTION_ENEMY_08_WEAK = 3;
    public final static int ACTION_ENEMY_08_DIE = 4;
    public final static int ACTION_ENEMY_08_WIN = 5;
    public final static int ACTION_ENEMY_08_GUARD = 6;
    public final static int ACTION_ENEMY_08_G_HURT = 7;
    public final static int ACTION_ENEMY_08_ATT = 8;
    public final static int ACTION_ENEMY_08_CAST = 9;
    public final static int ACTION_ENEMY_08_DOUBLEATTACK = 10;
    public final static int ACTION_ENEMY_08_TRIPPLEATTACK = 11;
    //animation:ENEMY_09
    public final static int ANIM_ENEMY_09 = 90;
    public final static int ACTION_ENEMY_09_WAIT = 0;
    public final static int ACTION_ENEMY_09_HURT = 1;
    public final static int ACTION_ENEMY_09_DODGE = 2;
    public final static int ACTION_ENEMY_09_WEAK = 3;
    public final static int ACTION_ENEMY_09_DIE = 4;
    public final static int ACTION_ENEMY_09_WIN = 5;
    public final static int ACTION_ENEMY_09_GUARD = 6;
    public final static int ACTION_ENEMY_09_G_HURT = 7;
    public final static int ACTION_ENEMY_09_ATT = 8;
    public final static int ACTION_ENEMY_09_CAST = 9;
    public final static int ACTION_ENEMY_09_DOUBLEATTACK = 10;
    public final static int ACTION_ENEMY_09_TRIPPLEATTACK = 11;
    //animation:ENEMY_10
    public final static int ANIM_ENEMY_10 = 91;
    public final static int ACTION_ENEMY_10_WAIT = 0;
    public final static int ACTION_ENEMY_10_HURT = 1;
    public final static int ACTION_ENEMY_10_DODGE = 2;
    public final static int ACTION_ENEMY_10_WEAK = 3;
    public final static int ACTION_ENEMY_10_DIE = 4;
    public final static int ACTION_ENEMY_10_WIN = 5;
    public final static int ACTION_ENEMY_10_GUARD = 6;
    public final static int ACTION_ENEMY_10_G_HURT = 7;
    public final static int ACTION_ENEMY_10_ATT = 8;
    public final static int ACTION_ENEMY_10_CAST = 9;
    public final static int ACTION_ENEMY_10_DOUBLEATTACK = 10;
    public final static int ACTION_ENEMY_10_TRIPPLEATTACK = 11;
    //animation:ENEMY_11
    public final static int ANIM_ENEMY_11 = 92;
    public final static int ACTION_ENEMY_11_WAIT = 0;
    public final static int ACTION_ENEMY_11_HURT = 1;
    public final static int ACTION_ENEMY_11_DODGE = 2;
    public final static int ACTION_ENEMY_11_WEAK = 3;
    public final static int ACTION_ENEMY_11_DIE = 4;
    public final static int ACTION_ENEMY_11_WIN = 5;
    public final static int ACTION_ENEMY_11_GUARD = 6;
    public final static int ACTION_ENEMY_11_G_HURT = 7;
    public final static int ACTION_ENEMY_11_ATT = 8;
    public final static int ACTION_ENEMY_11_CAST = 9;
    public final static int ACTION_ENEMY_11_DOUBLE_ATTACK = 10;
    public final static int ACTION_ENEMY_11_TRIPPLE_ATTACK = 11;
    //animation:ENEMY_12
    public final static int ANIM_ENEMY_12 = 93;
    public final static int ACTION_ENEMY_12_WAIT = 0;
    public final static int ACTION_ENEMY_12_HURT = 1;
    public final static int ACTION_ENEMY_12_DODGE = 2;
    public final static int ACTION_ENEMY_12_WEAK = 3;
    public final static int ACTION_ENEMY_12_DIE = 4;
    public final static int ACTION_ENEMY_12_WIN = 5;
    public final static int ACTION_ENEMY_12_GUARD = 6;
    public final static int ACTION_ENEMY_12_G_HURT = 7;
    public final static int ACTION_ENEMY_12_ATT = 8;
    public final static int ACTION_ENEMY_12_SKILL_0 = 9;
    //animation:ENEMY_13
    public final static int ANIM_ENEMY_13 = 94;
    public final static int ACTION_ENEMY_13_1 = 0;
    //animation:ENEMY_14
    public final static int ANIM_ENEMY_14 = 95;
    public final static int ACTION_ENEMY_14_1 = 0;
    //animation:ENEMY_15
    public final static int ANIM_ENEMY_15 = 96;
    public final static int ACTION_ENEMY_15_1 = 0;
    //animation:ENEMY_16
    public final static int ANIM_ENEMY_16 = 97;
    public final static int ACTION_ENEMY_16_1 = 0;
    //animation:ENEMY_17
    public final static int ANIM_ENEMY_17 = 98;
    public final static int ACTION_ENEMY_17_1 = 0;
    //animation:ENEMY_18
    public final static int ANIM_ENEMY_18 = 99;
    public final static int ACTION_ENEMY_18_1 = 0;
    //animation:ENEMY_19
    public final static int ANIM_ENEMY_19 = 100;
    public final static int ACTION_ENEMY_19_1 = 0;
    //animation:ENEMY_20
    public final static int ANIM_ENEMY_20 = 101;
    public final static int ACTION_ENEMY_20_1 = 0;
    //animation:ENEMY_21
    public final static int ANIM_ENEMY_21 = 102;
    public final static int ACTION_ENEMY_21_1 = 0;
    //animation:ENEMY_22
    public final static int ANIM_ENEMY_22 = 103;
    public final static int ACTION_ENEMY_22_1 = 0;
    //animation:ENEMY_23
    public final static int ANIM_ENEMY_23 = 104;
    public final static int ACTION_ENEMY_23_1 = 0;
    //animation:ENEMY_24
    public final static int ANIM_ENEMY_24 = 105;
    public final static int ACTION_ENEMY_24_1 = 0;
    //animation:ENEMY_25
    public final static int ANIM_ENEMY_25 = 106;
    public final static int ACTION_ENEMY_25_1 = 0;
    //animation:ENEMY_26
    public final static int ANIM_ENEMY_26 = 107;
    public final static int ACTION_ENEMY_26_1 = 0;
    //animation:SHADOW
    public final static int ANIM_SHADOW = 108;
    public final static int FRAME_SHADOW_STANDARD = 0;
    //animation:BATTLE_BG0
    public final static int ANIM_BATTLE_BG0 = 109;
    public final static int ACTION_BATTLE_BG0_ = 0;
    //animation:BATTLE_BG1
    public final static int ANIM_BATTLE_BG1 = 110;
    public final static int ACTION_BATTLE_BG1_ = 0;
    //animation:BATTLE_BG2
    public final static int ANIM_BATTLE_BG2 = 111;
    public final static int ACTION_BATTLE_BG2_ = 0;
    //animation:BATTLE_BG3
    public final static int ANIM_BATTLE_BG3 = 112;
    public final static int ACTION_BATTLE_BG3_ = 0;
    //animation:PORTRAIT_00
    public final static int ANIM_PORTRAIT_00 = 113;
    public final static int ACTION_PORTRAIT_00_R_NORMAL = 0;
    public final static int ACTION_PORTRAIT_00_R_HAPPY = 1;
    public final static int ACTION_PORTRAIT_00_R_ANGRY = 2;
    public final static int ACTION_PORTRAIT_00_R_SAD = 3;
    public final static int ACTION_PORTRAIT_00_R_AMAZE = 4;
    public final static int ACTION_PORTRAIT_00_L_NORMAL = 5;
    public final static int ACTION_PORTRAIT_00_L_HAPPY = 6;
    public final static int ACTION_PORTRAIT_00_L_ANGRY = 7;
    public final static int ACTION_PORTRAIT_00_L_SAD = 8;
    public final static int ACTION_PORTRAIT_00_L_AMAZE = 9;
    //animation:PORTRAIT_01
    public final static int ANIM_PORTRAIT_01 = 114;
    public final static int ACTION_PORTRAIT_01_R_NORMAL = 0;
    public final static int ACTION_PORTRAIT_01_R_HAPPY = 1;
    public final static int ACTION_PORTRAIT_01_R_ANGRY = 2;
    public final static int ACTION_PORTRAIT_01_R_SAD = 3;
    public final static int ACTION_PORTRAIT_01_R_AMAZE = 4;
    public final static int ACTION_PORTRAIT_01_L_NORMAL = 5;
    public final static int ACTION_PORTRAIT_01_L_HAPPY = 6;
    public final static int ACTION_PORTRAIT_01_L_ANGRY = 7;
    public final static int ACTION_PORTRAIT_01_L_SAD = 8;
    public final static int ACTION_PORTRAIT_01_L_AMAZE = 9;
    //animation:PORTRAIT_02
    public final static int ANIM_PORTRAIT_02 = 115;
    public final static int ACTION_PORTRAIT_02_R_NORMAL = 0;
    public final static int ACTION_PORTRAIT_02_R_HAPPY = 1;
    public final static int ACTION_PORTRAIT_02_R_ANGRY = 2;
    public final static int ACTION_PORTRAIT_02_R_SAD = 3;
    public final static int ACTION_PORTRAIT_02_R_AMAZE = 4;
    public final static int ACTION_PORTRAIT_02_L_NORMAL = 5;
    public final static int ACTION_PORTRAIT_02_L_HAPPY = 6;
    public final static int ACTION_PORTRAIT_02_L_ANGRY = 7;
    public final static int ACTION_PORTRAIT_02_L_SAD = 8;
    public final static int ACTION_PORTRAIT_02_L_AMAZE = 9;
    //animation:PORTRAIT_03
    public final static int ANIM_PORTRAIT_03 = 116;
    public final static int ACTION_PORTRAIT_03_R_NORMAL = 0;
    public final static int ACTION_PORTRAIT_03_R_HAPPY = 1;
    public final static int ACTION_PORTRAIT_03_R_ANGRY = 2;
    public final static int ACTION_PORTRAIT_03_R_SAD = 3;
    public final static int ACTION_PORTRAIT_03_R_AMAZE = 4;
    public final static int ACTION_PORTRAIT_03_L_NORMAL = 5;
    public final static int ACTION_PORTRAIT_03_L_HAPPY = 6;
    public final static int ACTION_PORTRAIT_03_L_ANGRY = 7;
    public final static int ACTION_PORTRAIT_03_L_SAD = 8;
    public final static int ACTION_PORTRAIT_03_L_AMAZE = 9;
    //animation:PORTRAIT_04
    public final static int ANIM_PORTRAIT_04 = 117;
    public final static int ACTION_PORTRAIT_04_R_NORMAL = 0;
    public final static int ACTION_PORTRAIT_04_R_HAPPY = 1;
    public final static int ACTION_PORTRAIT_04_R_ANGRY = 2;
    public final static int ACTION_PORTRAIT_04_R_SAD = 3;
    public final static int ACTION_PORTRAIT_04_R_AMAZE = 4;
    public final static int ACTION_PORTRAIT_04_L_NORMAL = 5;
    public final static int ACTION_PORTRAIT_04_L_HAPPY = 6;
    public final static int ACTION_PORTRAIT_04_L_ANGRY = 7;
    public final static int ACTION_PORTRAIT_04_L_SAD = 8;
    public final static int ACTION_PORTRAIT_04_L_AMAZE = 9;
    //animation:PORTRAIT_05
    public final static int ANIM_PORTRAIT_05 = 118;
    public final static int ACTION_PORTRAIT_05_R_NORMAL = 0;
    public final static int ACTION_PORTRAIT_05_R_HAPPY = 1;
    public final static int ACTION_PORTRAIT_05_R_ANGRY = 2;
    public final static int ACTION_PORTRAIT_05_R_SAD = 3;
    public final static int ACTION_PORTRAIT_05_R_AMAZE = 4;
    public final static int ACTION_PORTRAIT_05_L_NORMAL = 5;
    public final static int ACTION_PORTRAIT_05_L_HAPPY = 6;
    public final static int ACTION_PORTRAIT_05_L_ANGRY = 7;
    public final static int ACTION_PORTRAIT_05_L_SAD = 8;
    public final static int ACTION_PORTRAIT_05_L_AMAZE = 9;
    //animation:PORTRAIT_06
    public final static int ANIM_PORTRAIT_06 = 119;
    public final static int ACTION_PORTRAIT_06_R_NORMAL = 0;
    public final static int ACTION_PORTRAIT_06_R_HAPPY = 1;
    public final static int ACTION_PORTRAIT_06_R_ANGRY = 2;
    public final static int ACTION_PORTRAIT_06_R_SAD = 3;
    public final static int ACTION_PORTRAIT_06_R_AMAZE = 4;
    public final static int ACTION_PORTRAIT_06_L_NORMAL = 5;
    public final static int ACTION_PORTRAIT_06_L_HAPPY = 6;
    public final static int ACTION_PORTRAIT_06_L_ANGRY = 7;
    public final static int ACTION_PORTRAIT_06_L_SAD = 8;
    public final static int ACTION_PORTRAIT_06_L_AMAZE = 9;
    //animation:PORTRAIT_07
    public final static int ANIM_PORTRAIT_07 = 120;
    public final static int ACTION_PORTRAIT_07_0 = 0;
    public final static int ACTION_PORTRAIT_07_1 = 1;
    public final static int ACTION_PORTRAIT_07_2 = 2;
    public final static int ACTION_PORTRAIT_07_3 = 3;
    public final static int ACTION_PORTRAIT_07_4 = 4;
    public final static int ACTION_PORTRAIT_07_5 = 5;
    public final static int ACTION_PORTRAIT_07_6 = 6;
    public final static int ACTION_PORTRAIT_07_7 = 7;
    public final static int ACTION_PORTRAIT_07_8 = 8;
    public final static int ACTION_PORTRAIT_07_9 = 9;
    //animation:PORTRAIT_08
    public final static int ANIM_PORTRAIT_08 = 121;
    public final static int ACTION_PORTRAIT_08_0 = 0;
    public final static int ACTION_PORTRAIT_08_1 = 1;
    public final static int ACTION_PORTRAIT_08_2 = 2;
    public final static int ACTION_PORTRAIT_08_3 = 3;
    public final static int ACTION_PORTRAIT_08_4 = 4;
    public final static int ACTION_PORTRAIT_08_5 = 5;
    public final static int ACTION_PORTRAIT_08_6 = 6;
    public final static int ACTION_PORTRAIT_08_7 = 7;
    public final static int ACTION_PORTRAIT_08_8 = 8;
    public final static int ACTION_PORTRAIT_08_9 = 9;

    //Aniamtions Offset
    public final static long ANIM_OFFSET[] = { 0,
                                              6904,
                                              9057,
                                              11470,
                                              12320,
                                              12347,
                                              13048,
                                              31882,
                                              38285,
                                              40223,
                                              41199,
                                              47706,
                                              48339,
                                              48395,
                                              48437,
                                              48670,
                                              49027,
                                              49260,
                                              49650,
                                              49923,
                                              50262,
                                              50364,
                                              51033,
                                              51232,
                                              51722,
                                              52844,
                                              53374,
                                              54221,
                                              55184,
                                              56736,
                                              58333,
                                              58606,
                                              59307,
                                              59640,
                                              60109,
                                              60446,
                                              61197,
                                              61582,
                                              61874,
                                              62107,
                                              62545,
                                              63784,
                                              64561,
                                              65286,
                                              65761,
                                              65788,
                                              67101,
                                              67975,
                                              70630,
                                              70701,
                                              70968,
                                              77824,
                                              78400,
                                              78791,
                                              79544,
                                              80265,
                                              81320,
                                              82786,
                                              86172,
                                              86746,
                                              86979,
                                              87676,
                                              90189,
                                              90258,
                                              90576,
                                              90843,
                                              92484,
                                              92834,
                                              92927,
                                              92954,
                                              93849,
                                              96597,
                                              99380,
                                              99407,
                                              99434,
                                              100133,
                                              105663,
                                              117240,
                                              127812,
                                              127839,
                                              127866,
                                              138438,
                                              138903,
                                              139369,
                                              140044,
                                              140649,
                                              141722,
                                              143119,
                                              144177,
                                              144701,
                                              146361,
                                              148047,
                                              149280,
                                              150353,
                                              152349,
                                              152376,
                                              152403,
                                              152430,
                                              152457,
                                              152484,
                                              152511,
                                              152538,
                                              152565,
                                              152592,
                                              152619,
                                              152646,
                                              152673,
                                              152700,
                                              152727,
                                              152749,
                                              152824,
                                              152931,
                                              153038,
                                              153113,
                                              153442,
                                              153771,
                                              153924,
                                              154077,
                                              154230,
                                              154383,
                                              154536,
                                              154689,
                                              };
//@@
//@@../Const/StringCombined.h
    public static final int STR_SYSTEM_ITEMS_NAME_0 = 0;
    public static final int STR_SYSTEM_ITEMS_NAME_1 = 1;
    public static final int STR_SYSTEM_ITEMS_NAME_2 = 2;
    public static final int STR_SYSTEM_ITEMS_NAME_3 = 3;
    public static final int STR_SYSTEM_ITEMS_NAME_4 = 4;
    public static final int STR_SYSTEM_ITEMS_NAME_5 = 5;
    public static final int STR_SYSTEM_ITEMS_NAME_6 = 6;
    public static final int STR_SYSTEM_ITEMS_NAME_7 = 7;
    public static final int STR_SYSTEM_ITEMS_NAME_8 = 8;
    public static final int STR_SYSTEM_ITEMS_NAME_9 = 9;
    public static final int STR_SYSTEM_ITEMS_NAME_10 = 10;
    public static final int STR_SYSTEM_ITEMS_NAME_11 = 11;
    public static final int STR_SYSTEM_ITEMS_NAME_12 = 12;
    public static final int STR_SYSTEM_ITEMS_NAME_13 = 13;
    public static final int STR_SYSTEM_ITEMS_NAME_14 = 14;
    public static final int STR_SYSTEM_ITEMS_NAME_15 = 15;
    public static final int STR_SYSTEM_ITEMS_NAME_16 = 16;
    public static final int STR_SYSTEM_ITEMS_NAME_17 = 17;
    public static final int STR_SYSTEM_ITEMS_NAME_18 = 18;
    public static final int STR_SYSTEM_ITEMS_NAME_19 = 19;
    public static final int STR_SYSTEM_ITEMS_NAME_20 = 20;
    public static final int STR_SYSTEM_ITEMS_NAME_21 = 21;
    public static final int STR_SYSTEM_ITEMS_NAME_22 = 22;
    public static final int STR_SYSTEM_ITEMS_NAME_23 = 23;
    public static final int STR_SYSTEM_ITEMS_NAME_24 = 24;
    public static final int STR_SYSTEM_ITEMS_NAME_25 = 25;
    public static final int STR_SYSTEM_ITEMS_NAME_26 = 26;
    public static final int STR_SYSTEM_ITEMS_NAME_27 = 27;
    public static final int STR_SYSTEM_ITEMS_NAME_28 = 28;
    public static final int STR_SYSTEM_ITEMS_NAME_29 = 29;
    public static final int STR_SYSTEM_ITEMS_NAME_30 = 30;
    public static final int STR_SYSTEM_ITEMS_NAME_31 = 31;
    public static final int STR_SYSTEM_ITEMS_NAME_32 = 32;
    public static final int STR_SYSTEM_ITEMS_NAME_33 = 33;
    public static final int STR_SYSTEM_ITEMS_NAME_34 = 34;
    public static final int STR_SYSTEM_ITEMS_NAME_35 = 35;
    public static final int STR_SYSTEM_ITEMS_NAME_36 = 36;
    public static final int STR_SYSTEM_ITEMS_NAME_37 = 37;
    public static final int STR_SYSTEM_ITEMS_NAME_38 = 38;
    public static final int STR_SYSTEM_ITEMS_NAME_39 = 39;
    public static final int STR_SYSTEM_ITEMS_NAME_40 = 40;
    public static final int STR_SYSTEM_ITEMS_NAME_41 = 41;
    public static final int STR_SYSTEM_ITEMS_NAME_42 = 42;
    public static final int STR_SYSTEM_ITEMS_NAME_43 = 43;
    public static final int STR_SYSTEM_ITEMS_NAME_44 = 44;
    public static final int STR_SYSTEM_ITEMS_NAME_45 = 45;
    public static final int STR_SYSTEM_ITEMS_NAME_46 = 46;
    public static final int STR_SYSTEM_ITEMS_NAME_47 = 47;
    public static final int STR_SYSTEM_ITEMS_NAME_48 = 48;
    public static final int STR_SYSTEM_ITEMS_NAME_49 = 49;
    public static final int STR_SYSTEM_ITEMS_NAME_50 = 50;
    public static final int STR_SYSTEM_ITEMS_NAME_51 = 51;
    public static final int STR_SYSTEM_ITEMS_NAME_52 = 52;
    public static final int STR_SYSTEM_ITEMS_NAME_53 = 53;
    public static final int STR_SYSTEM_ITEMS_NAME_54 = 54;
    public static final int STR_SYSTEM_ITEMS_NAME_55 = 55;
    public static final int STR_SYSTEM_ITEMS_NAME_56 = 56;
    public static final int STR_SYSTEM_ITEMS_NAME_57 = 57;
    public static final int STR_SYSTEM_ITEMS_NAME_58 = 58;
    public static final int STR_SYSTEM_ITEMS_NAME_59 = 59;
    public static final int STR_SYSTEM_ITEMS_NAME_60 = 60;
    public static final int STR_SYSTEM_ITEMS_NAME_61 = 61;
    public static final int STR_SYSTEM_ITEMS_NAME_62 = 62;
    public static final int STR_SYSTEM_ITEMS_NAME_63 = 63;
    public static final int STR_SYSTEM_ITEMS_NAME_64 = 64;
    public static final int STR_SYSTEM_ITEMS_NAME_65 = 65;
    public static final int STR_SYSTEM_ITEMS_NAME_66 = 66;
    public static final int STR_SYSTEM_ITEMS_DESC_0 = 67;
    public static final int STR_SYSTEM_ITEMS_DESC_1 = 68;
    public static final int STR_SYSTEM_ITEMS_DESC_2 = 69;
    public static final int STR_SYSTEM_ITEMS_DESC_3 = 70;
    public static final int STR_SYSTEM_ITEMS_DESC_4 = 71;
    public static final int STR_SYSTEM_ITEMS_DESC_5 = 72;
    public static final int STR_SYSTEM_ITEMS_DESC_6 = 73;
    public static final int STR_SYSTEM_ITEMS_DESC_7 = 74;
    public static final int STR_SYSTEM_ITEMS_DESC_8 = 75;
    public static final int STR_SYSTEM_ITEMS_DESC_9 = 76;
    public static final int STR_SYSTEM_ITEMS_DESC_10 = 77;
    public static final int STR_SYSTEM_ITEMS_DESC_11 = 78;
    public static final int STR_SYSTEM_ITEMS_DESC_12 = 79;
    public static final int STR_SYSTEM_ITEMS_DESC_13 = 80;
    public static final int STR_SYSTEM_ITEMS_DESC_14 = 81;
    public static final int STR_SYSTEM_ITEMS_DESC_15 = 82;
    public static final int STR_SYSTEM_ITEMS_DESC_16 = 83;
    public static final int STR_SYSTEM_ITEMS_DESC_17 = 84;
    public static final int STR_SYSTEM_ITEMS_DESC_18 = 85;
    public static final int STR_SYSTEM_ITEMS_DESC_19 = 86;
    public static final int STR_SYSTEM_ITEMS_DESC_20 = 87;
    public static final int STR_SYSTEM_ITEMS_DESC_21 = 88;
    public static final int STR_SYSTEM_ITEMS_DESC_22 = 89;
    public static final int STR_SYSTEM_ITEMS_DESC_23 = 90;
    public static final int STR_SYSTEM_ITEMS_DESC_24 = 91;
    public static final int STR_SYSTEM_ITEMS_DESC_25 = 92;
    public static final int STR_SYSTEM_ITEMS_DESC_26 = 93;
    public static final int STR_SYSTEM_ITEMS_DESC_27 = 94;
    public static final int STR_SYSTEM_ITEMS_DESC_28 = 95;
    public static final int STR_SYSTEM_ITEMS_DESC_29 = 96;
    public static final int STR_SYSTEM_ITEMS_DESC_30 = 97;
    public static final int STR_SYSTEM_ITEMS_DESC_31 = 98;
    public static final int STR_SYSTEM_ITEMS_DESC_32 = 99;
    public static final int STR_SYSTEM_ITEMS_DESC_33 = 100;
    public static final int STR_SYSTEM_ITEMS_DESC_34 = 101;
    public static final int STR_SYSTEM_ITEMS_DESC_35 = 102;
    public static final int STR_SYSTEM_ITEMS_DESC_36 = 103;
    public static final int STR_SYSTEM_ITEMS_DESC_37 = 104;
    public static final int STR_SYSTEM_ITEMS_DESC_38 = 105;
    public static final int STR_SYSTEM_ITEMS_DESC_39 = 106;
    public static final int STR_SYSTEM_ITEMS_DESC_40 = 107;
    public static final int STR_SYSTEM_ITEMS_DESC_41 = 108;
    public static final int STR_SYSTEM_ITEMS_DESC_42 = 109;
    public static final int STR_SYSTEM_ITEMS_DESC_43 = 110;
    public static final int STR_SYSTEM_ITEMS_DESC_44 = 111;
    public static final int STR_SYSTEM_ITEMS_DESC_45 = 112;
    public static final int STR_SYSTEM_ITEMS_DESC_46 = 113;
    public static final int STR_SYSTEM_ITEMS_DESC_47 = 114;
    public static final int STR_SYSTEM_ITEMS_DESC_48 = 115;
    public static final int STR_SYSTEM_ITEMS_DESC_49 = 116;
    public static final int STR_SYSTEM_ITEMS_DESC_50 = 117;
    public static final int STR_SYSTEM_ITEMS_DESC_51 = 118;
    public static final int STR_SYSTEM_ITEMS_DESC_52 = 119;
    public static final int STR_SYSTEM_ITEMS_DESC_53 = 120;
    public static final int STR_SYSTEM_ITEMS_DESC_54 = 121;
    public static final int STR_SYSTEM_ITEMS_DESC_55 = 122;
    public static final int STR_SYSTEM_ITEMS_DESC_56 = 123;
    public static final int STR_SYSTEM_ITEMS_DESC_57 = 124;
    public static final int STR_SYSTEM_ITEMS_DESC_58 = 125;
    public static final int STR_SYSTEM_ITEMS_DESC_59 = 126;
    public static final int STR_SYSTEM_ITEMS_DESC_60 = 127;
    public static final int STR_SYSTEM_ITEMS_DESC_61 = 128;
    public static final int STR_SYSTEM_ITEMS_DESC_62 = 129;
    public static final int STR_SYSTEM_ITEMS_DESC_63 = 130;
    public static final int STR_SYSTEM_ITEMS_DESC_64 = 131;
    public static final int STR_SYSTEM_ITEMS_DESC_65 = 132;
    public static final int STR_SYSTEM_ITEMS_DESC_66 = 133;
    public static final int STR_SYSTEM_EQUIPS_NAME_0 = 134;
    public static final int STR_SYSTEM_EQUIPS_NAME_1 = 135;
    public static final int STR_SYSTEM_EQUIPS_NAME_2 = 136;
    public static final int STR_SYSTEM_EQUIPS_NAME_3 = 137;
    public static final int STR_SYSTEM_EQUIPS_NAME_4 = 138;
    public static final int STR_SYSTEM_EQUIPS_NAME_5 = 139;
    public static final int STR_SYSTEM_EQUIPS_NAME_6 = 140;
    public static final int STR_SYSTEM_EQUIPS_NAME_7 = 141;
    public static final int STR_SYSTEM_EQUIPS_NAME_8 = 142;
    public static final int STR_SYSTEM_EQUIPS_NAME_9 = 143;
    public static final int STR_SYSTEM_EQUIPS_NAME_10 = 144;
    public static final int STR_SYSTEM_EQUIPS_NAME_11 = 145;
    public static final int STR_SYSTEM_EQUIPS_NAME_12 = 146;
    public static final int STR_SYSTEM_EQUIPS_NAME_13 = 147;
    public static final int STR_SYSTEM_EQUIPS_NAME_14 = 148;
    public static final int STR_SYSTEM_EQUIPS_NAME_15 = 149;
    public static final int STR_SYSTEM_EQUIPS_NAME_16 = 150;
    public static final int STR_SYSTEM_EQUIPS_NAME_17 = 151;
    public static final int STR_SYSTEM_EQUIPS_NAME_18 = 152;
    public static final int STR_SYSTEM_EQUIPS_NAME_19 = 153;
    public static final int STR_SYSTEM_EQUIPS_NAME_20 = 154;
    public static final int STR_SYSTEM_EQUIPS_NAME_21 = 155;
    public static final int STR_SYSTEM_EQUIPS_NAME_22 = 156;
    public static final int STR_SYSTEM_EQUIPS_NAME_23 = 157;
    public static final int STR_SYSTEM_EQUIPS_NAME_24 = 158;
    public static final int STR_SYSTEM_EQUIPS_NAME_25 = 159;
    public static final int STR_SYSTEM_EQUIPS_NAME_26 = 160;
    public static final int STR_SYSTEM_EQUIPS_NAME_27 = 161;
    public static final int STR_SYSTEM_EQUIPS_NAME_28 = 162;
    public static final int STR_SYSTEM_EQUIPS_NAME_29 = 163;
    public static final int STR_SYSTEM_EQUIPS_NAME_30 = 164;
    public static final int STR_SYSTEM_EQUIPS_NAME_31 = 165;
    public static final int STR_SYSTEM_EQUIPS_NAME_32 = 166;
    public static final int STR_SYSTEM_EQUIPS_NAME_33 = 167;
    public static final int STR_SYSTEM_EQUIPS_NAME_34 = 168;
    public static final int STR_SYSTEM_EQUIPS_NAME_35 = 169;
    public static final int STR_SYSTEM_EQUIPS_NAME_36 = 170;
    public static final int STR_SYSTEM_EQUIPS_NAME_37 = 171;
    public static final int STR_SYSTEM_EQUIPS_NAME_38 = 172;
    public static final int STR_SYSTEM_EQUIPS_NAME_39 = 173;
    public static final int STR_SYSTEM_EQUIPS_NAME_40 = 174;
    public static final int STR_SYSTEM_EQUIPS_NAME_41 = 175;
    public static final int STR_SYSTEM_EQUIPS_NAME_42 = 176;
    public static final int STR_SYSTEM_EQUIPS_NAME_43 = 177;
    public static final int STR_SYSTEM_EQUIPS_NAME_44 = 178;
    public static final int STR_SYSTEM_EQUIPS_NAME_45 = 179;
    public static final int STR_SYSTEM_EQUIPS_NAME_46 = 180;
    public static final int STR_SYSTEM_EQUIPS_NAME_47 = 181;
    public static final int STR_SYSTEM_EQUIPS_NAME_48 = 182;
    public static final int STR_SYSTEM_EQUIPS_NAME_49 = 183;
    public static final int STR_SYSTEM_EQUIPS_NAME_50 = 184;
    public static final int STR_SYSTEM_EQUIPS_NAME_51 = 185;
    public static final int STR_SYSTEM_EQUIPS_NAME_52 = 186;
    public static final int STR_SYSTEM_EQUIPS_NAME_53 = 187;
    public static final int STR_SYSTEM_EQUIPS_NAME_54 = 188;
    public static final int STR_SYSTEM_EQUIPS_NAME_55 = 189;
    public static final int STR_SYSTEM_EQUIPS_NAME_56 = 190;
    public static final int STR_SYSTEM_EQUIPS_NAME_57 = 191;
    public static final int STR_SYSTEM_EQUIPS_NAME_58 = 192;
    public static final int STR_SYSTEM_EQUIPS_NAME_59 = 193;
    public static final int STR_SYSTEM_EQUIPS_DESC_0 = 194;
    public static final int STR_SYSTEM_EQUIPS_DESC_1 = 195;
    public static final int STR_SYSTEM_EQUIPS_DESC_2 = 196;
    public static final int STR_SYSTEM_EQUIPS_DESC_3 = 197;
    public static final int STR_SYSTEM_EQUIPS_DESC_4 = 198;
    public static final int STR_SYSTEM_EQUIPS_DESC_5 = 199;
    public static final int STR_SYSTEM_EQUIPS_DESC_6 = 200;
    public static final int STR_SYSTEM_EQUIPS_DESC_7 = 201;
    public static final int STR_SYSTEM_EQUIPS_DESC_8 = 202;
    public static final int STR_SYSTEM_EQUIPS_DESC_9 = 203;
    public static final int STR_SYSTEM_EQUIPS_DESC_10 = 204;
    public static final int STR_SYSTEM_EQUIPS_DESC_11 = 205;
    public static final int STR_SYSTEM_EQUIPS_DESC_12 = 206;
    public static final int STR_SYSTEM_EQUIPS_DESC_13 = 207;
    public static final int STR_SYSTEM_EQUIPS_DESC_14 = 208;
    public static final int STR_SYSTEM_EQUIPS_DESC_15 = 209;
    public static final int STR_SYSTEM_EQUIPS_DESC_16 = 210;
    public static final int STR_SYSTEM_EQUIPS_DESC_17 = 211;
    public static final int STR_SYSTEM_EQUIPS_DESC_18 = 212;
    public static final int STR_SYSTEM_EQUIPS_DESC_19 = 213;
    public static final int STR_SYSTEM_EQUIPS_DESC_20 = 214;
    public static final int STR_SYSTEM_EQUIPS_DESC_21 = 215;
    public static final int STR_SYSTEM_EQUIPS_DESC_22 = 216;
    public static final int STR_SYSTEM_EQUIPS_DESC_23 = 217;
    public static final int STR_SYSTEM_EQUIPS_DESC_24 = 218;
    public static final int STR_SYSTEM_EQUIPS_DESC_25 = 219;
    public static final int STR_SYSTEM_EQUIPS_DESC_26 = 220;
    public static final int STR_SYSTEM_EQUIPS_DESC_27 = 221;
    public static final int STR_SYSTEM_EQUIPS_DESC_28 = 222;
    public static final int STR_SYSTEM_EQUIPS_DESC_29 = 223;
    public static final int STR_SYSTEM_EQUIPS_DESC_30 = 224;
    public static final int STR_SYSTEM_EQUIPS_DESC_31 = 225;
    public static final int STR_SYSTEM_EQUIPS_DESC_32 = 226;
    public static final int STR_SYSTEM_EQUIPS_DESC_33 = 227;
    public static final int STR_SYSTEM_EQUIPS_DESC_34 = 228;
    public static final int STR_SYSTEM_EQUIPS_DESC_35 = 229;
    public static final int STR_SYSTEM_EQUIPS_DESC_36 = 230;
    public static final int STR_SYSTEM_EQUIPS_DESC_37 = 231;
    public static final int STR_SYSTEM_EQUIPS_DESC_38 = 232;
    public static final int STR_SYSTEM_EQUIPS_DESC_39 = 233;
    public static final int STR_SYSTEM_EQUIPS_DESC_40 = 234;
    public static final int STR_SYSTEM_EQUIPS_DESC_41 = 235;
    public static final int STR_SYSTEM_EQUIPS_DESC_42 = 236;
    public static final int STR_SYSTEM_EQUIPS_DESC_43 = 237;
    public static final int STR_SYSTEM_EQUIPS_DESC_44 = 238;
    public static final int STR_SYSTEM_EQUIPS_DESC_45 = 239;
    public static final int STR_SYSTEM_EQUIPS_DESC_46 = 240;
    public static final int STR_SYSTEM_EQUIPS_DESC_47 = 241;
    public static final int STR_SYSTEM_EQUIPS_DESC_48 = 242;
    public static final int STR_SYSTEM_EQUIPS_DESC_49 = 243;
    public static final int STR_SYSTEM_EQUIPS_DESC_50 = 244;
    public static final int STR_SYSTEM_EQUIPS_DESC_51 = 245;
    public static final int STR_SYSTEM_EQUIPS_DESC_52 = 246;
    public static final int STR_SYSTEM_EQUIPS_DESC_53 = 247;
    public static final int STR_SYSTEM_EQUIPS_DESC_54 = 248;
    public static final int STR_SYSTEM_EQUIPS_DESC_55 = 249;
    public static final int STR_SYSTEM_EQUIPS_DESC_56 = 250;
    public static final int STR_SYSTEM_EQUIPS_DESC_57 = 251;
    public static final int STR_SYSTEM_EQUIPS_DESC_58 = 252;
    public static final int STR_SYSTEM_EQUIPS_DESC_59 = 253;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_0 = 254;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_1 = 255;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_2 = 256;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_3 = 257;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_4 = 258;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_5 = 259;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_6 = 260;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_7 = 261;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_8 = 262;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_9 = 263;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_10 = 264;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_11 = 265;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_12 = 266;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_13 = 267;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_14 = 268;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_15 = 269;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_16 = 270;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_17 = 271;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_18 = 272;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_19 = 273;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_20 = 274;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_21 = 275;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_22 = 276;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_23 = 277;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_24 = 278;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_25 = 279;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_26 = 280;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_27 = 281;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_28 = 282;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_29 = 283;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_30 = 284;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_31 = 285;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_32 = 286;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_33 = 287;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_34 = 288;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_35 = 289;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_36 = 290;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_37 = 291;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_38 = 292;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_39 = 293;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_40 = 294;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_41 = 295;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_42 = 296;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_43 = 297;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_44 = 298;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_45 = 299;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_46 = 300;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_47 = 301;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_48 = 302;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_49 = 303;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_50 = 304;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_51 = 305;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_52 = 306;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_53 = 307;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_54 = 308;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_55 = 309;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_56 = 310;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_57 = 311;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_58 = 312;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_59 = 313;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_60 = 314;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_61 = 315;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_62 = 316;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_63 = 317;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_64 = 318;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_65 = 319;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_66 = 320;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_67 = 321;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_68 = 322;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_69 = 323;
    public static final int STR_SYSTEM_EFFECT_SET_NAME_70 = 324;
    public static final int STR_SYSTEM_SKILLS_DESC_0 = 325;
    public static final int STR_SYSTEM_SKILLS_DESC_1 = 326;
    public static final int STR_SYSTEM_SKILLS_DESC_2 = 327;
    public static final int STR_SYSTEM_SKILLS_DESC_3 = 328;
    public static final int STR_SYSTEM_SKILLS_DESC_4 = 329;
    public static final int STR_SYSTEM_SKILLS_DESC_5 = 330;
    public static final int STR_SYSTEM_SKILLS_DESC_6 = 331;
    public static final int STR_SYSTEM_SKILLS_DESC_7 = 332;
    public static final int STR_SYSTEM_SKILLS_DESC_8 = 333;
    public static final int STR_SYSTEM_SKILLS_DESC_9 = 334;
    public static final int STR_SYSTEM_SKILLS_DESC_10 = 335;
    public static final int STR_SYSTEM_SKILLS_DESC_11 = 336;
    public static final int STR_SYSTEM_SKILLS_DESC_12 = 337;
    public static final int STR_SYSTEM_SKILLS_DESC_13 = 338;
    public static final int STR_SYSTEM_SKILLS_DESC_14 = 339;
    public static final int STR_SYSTEM_SKILLS_DESC_15 = 340;
    public static final int STR_SYSTEM_SKILLS_DESC_16 = 341;
    public static final int STR_SYSTEM_SKILLS_DESC_17 = 342;
    public static final int STR_SYSTEM_SKILLS_DESC_18 = 343;
    public static final int STR_SYSTEM_SKILLS_DESC_19 = 344;
    public static final int STR_SYSTEM_SKILLS_DESC_20 = 345;
    public static final int STR_SYSTEM_SKILLS_DESC_21 = 346;
    public static final int STR_SYSTEM_SKILLS_DESC_22 = 347;
    public static final int STR_SYSTEM_SKILLS_DESC_23 = 348;
    public static final int STR_SYSTEM_SKILLS_DESC_24 = 349;
    public static final int STR_SYSTEM_SKILLS_DESC_25 = 350;
    public static final int STR_SYSTEM_SKILLS_DESC_26 = 351;
    public static final int STR_SYSTEM_SKILLS_DESC_27 = 352;
    public static final int STR_SYSTEM_SKILLS_DESC_28 = 353;
    public static final int STR_SYSTEM_SKILLS_DESC_29 = 354;
    public static final int STR_SYSTEM_SKILLS_DESC_30 = 355;
    public static final int STR_SYSTEM_SKILLS_DESC_31 = 356;
    public static final int STR_SYSTEM_SKILLS_DESC_32 = 357;
    public static final int STR_SYSTEM_SKILLS_DESC_33 = 358;
    public static final int STR_SYSTEM_SKILLS_DESC_34 = 359;
    public static final int STR_SYSTEM_SKILLS_DESC_35 = 360;
    public static final int STR_SYSTEM_SKILLS_DESC_36 = 361;
    public static final int STR_SYSTEM_SKILLS_DESC_37 = 362;
    public static final int STR_SYSTEM_SKILLS_DESC_38 = 363;
    public static final int STR_SYSTEM_SKILLS_DESC_39 = 364;
    public static final int STR_SYSTEM_SKILLS_DESC_40 = 365;
    public static final int STR_SYSTEM_SKILLS_DESC_41 = 366;
    public static final int STR_SYSTEM_SKILLS_DESC_42 = 367;
    public static final int STR_SYSTEM_SKILLS_DESC_43 = 368;
    public static final int STR_SYSTEM_SKILLS_DESC_44 = 369;
    public static final int STR_SYSTEM_SKILLS_DESC_45 = 370;
    public static final int STR_SYSTEM_SKILLS_DESC_46 = 371;
    public static final int STR_SYSTEM_SKILLS_DESC_47 = 372;
    public static final int STR_SYSTEM_SKILLS_DESC_48 = 373;
    public static final int STR_SYSTEM_SKILLS_DESC_49 = 374;
    public static final int STR_SYSTEM_SKILLS_DESC_50 = 375;
    public static final int STR_SYSTEM_SKILLS_DESC_51 = 376;
    public static final int STR_SYSTEM_SKILLS_DESC_52 = 377;
    public static final int STR_SYSTEM_SKILLS_DESC_53 = 378;
    public static final int STR_SYSTEM_SKILLS_DESC_54 = 379;
    public static final int STR_SYSTEM_SKILLS_DESC_55 = 380;
    public static final int STR_SYSTEM_SKILLS_DESC_56 = 381;
    public static final int STR_SYSTEM_SKILLS_DESC_57 = 382;
    public static final int STR_SYSTEM_SKILLS_DESC_58 = 383;
    public static final int STR_SYSTEM_SKILLS_DESC_59 = 384;
    public static final int STR_SYSTEM_SKILLS_DESC_60 = 385;
    public static final int STR_SYSTEM_SKILLS_DESC_61 = 386;
    public static final int STR_SYSTEM_SKILLS_DESC_62 = 387;
    public static final int STR_SYSTEM_SKILLS_DESC_63 = 388;
    public static final int STR_SYSTEM_SKILLS_DESC_64 = 389;
    public static final int STR_SYSTEM_SKILLS_DESC_65 = 390;
    public static final int STR_SYSTEM_SKILLS_DESC_66 = 391;
    public static final int STR_SYSTEM_SKILLS_DESC_67 = 392;
    public static final int STR_SYSTEM_SKILLS_DESC_68 = 393;
    public static final int STR_SYSTEM_SKILLS_DESC_69 = 394;
    public static final int STR_SYSTEM_SKILLS_DESC_70 = 395;
    public static final int STR_SYSTEM_SKILLS_DESC_71 = 396;
    public static final int STR_SYSTEM_SKILLS_DESC_72 = 397;
    public static final int STR_SYSTEM_SKILLS_DESC_73 = 398;
    public static final int STR_SYSTEM_SKILLS_DESC_74 = 399;
    public static final int STR_SYSTEM_ENEMIES_DESC_0 = 400;
    public static final int STR_SYSTEM_ENEMIES_DESC_1 = 401;
    public static final int STR_SYSTEM_ENEMIES_DESC_2 = 402;
    public static final int STR_SYSTEM_ENEMIES_DESC_3 = 403;
    public static final int STR_SYSTEM_ENEMIES_DESC_4 = 404;
    public static final int STR_SYSTEM_ENEMIES_DESC_5 = 405;
    public static final int STR_SYSTEM_ENEMIES_DESC_6 = 406;
    public static final int STR_SYSTEM_ENEMIES_DESC_7 = 407;
    public static final int STR_SYSTEM_ENEMIES_DESC_8 = 408;
    public static final int STR_SYSTEM_ENEMIES_DESC_9 = 409;
    public static final int STR_SYSTEM_ENEMIES_DESC_10 = 410;
    public static final int STR_SYSTEM_ENEMIES_DESC_11 = 411;
    public static final int STR_SYSTEM_ENEMIES_DESC_12 = 412;
    public static final int STR_SYSTEM_ENEMIES_DESC_13 = 413;
    public static final int STR_SYSTEM_ENEMIES_DESC_14 = 414;
    public static final int STR_SYSTEM_ENEMIES_DESC_15 = 415;
    public static final int STR_SYSTEM_ENEMIES_DESC_16 = 416;
    public static final int STR_SYSTEM_ENEMIES_DESC_17 = 417;
    public static final int STR_SYSTEM_ENEMIES_DESC_18 = 418;
    public static final int STR_SYSTEM_ENEMIES_DESC_19 = 419;
    public static final int STR_SYSTEM_ENEMIES_DESC_20 = 420;
    public static final int STR_SYSTEM_ENEMIES_DESC_21 = 421;
    public static final int STR_SYSTEM_ENEMIES_DESC_22 = 422;
    public static final int STR_SYSTEM_ENEMIES_DESC_23 = 423;
    public static final int STR_SYSTEM_ENEMIES_DESC_24 = 424;
    public static final int STR_SYSTEM_ENEMIES_DESC_25 = 425;
    public static final int STR_SYSTEM_ENEMIES_DESC_26 = 426;
    public static final int STR_SYSTEM_ENEMIES_DESC_27 = 427;
    public static final int STR_SYSTEM_ENEMIES_DESC_28 = 428;
    public static final int STR_SYSTEM_ENEMIES_DESC_29 = 429;
    public static final int STR_SYSTEM_ENEMIES_DESC_30 = 430;
    public static final int STR_SYSTEM_ENEMIES_DESC_31 = 431;
    public static final int STR_SYSTEM_ENEMIES_DESC_32 = 432;
    public static final int STR_SYSTEM_ENEMIES_DESC_33 = 433;
    public static final int STR_SYSTEM_ENEMIES_DESC_34 = 434;
    public static final int STR_SYSTEM_ENEMIES_DESC_35 = 435;
    public static final int STR_SYSTEM_ENEMIES_DESC_36 = 436;
    public static final int STR_SYSTEM_ENEMIES_DESC_37 = 437;
    public static final int STR_SYSTEM_ENEMIES_DESC_38 = 438;
    public static final int STR_SYSTEM_ENEMIES_DESC_39 = 439;
    public static final int STR_SYSTEM_ENEMIES_DESC_40 = 440;
    public static final int STR_SYSTEM_ENEMIES_DESC_41 = 441;
    public static final int STR_SYSTEM_ENEMIES_DESC_42 = 442;
    public static final int STR_SYSTEM_ENEMIES_DESC_43 = 443;
    public static final int STR_SYSTEM_ENEMIES_DESC_44 = 444;
    public static final int STR_SYSTEM_ENEMIES_DESC_45 = 445;
    public static final int STR_SYSTEM_ENEMIES_DESC_46 = 446;
    public static final int STR_SYSTEM_ENEMIES_DESC_47 = 447;
    public static final int STR_SYSTEM_ENEMIES_DESC_48 = 448;
    public static final int STR_SYSTEM_ENEMIES_DESC_49 = 449;
    public static final int STR_SYSTEM_ENEMIES_DESC_50 = 450;
    public static final int STR_SYSTEM_ENEMIES_DESC_51 = 451;
    public static final int STR_SYSTEM_ENEMIES_DESC_52 = 452;
    public static final int STR_SYSTEM_ENEMIES_DESC_53 = 453;
    public static final int STR_SYSTEM_TASKS_NAME_0 = 454;
    public static final int STR_SYSTEM_TASKS_NAME_1 = 455;
    public static final int STR_SYSTEM_TASKS_NAME_2 = 456;
    public static final int STR_SYSTEM_TASKS_NAME_3 = 457;
    public static final int STR_SYSTEM_TASKS_NAME_4 = 458;
    public static final int STR_SYSTEM_TASKS_NAME_5 = 459;
    public static final int STR_SYSTEM_TASKS_NAME_6 = 460;
    public static final int STR_SYSTEM_TASKS_NAME_7 = 461;
    public static final int STR_SYSTEM_TASKS_NAME_8 = 462;
    public static final int STR_SYSTEM_TASKS_NAME_9 = 463;
    public static final int STR_SYSTEM_TASKS_NAME_10 = 464;
    public static final int STR_SYSTEM_TASKS_NAME_11 = 465;
    public static final int STR_SYSTEM_TASKS_NAME_12 = 466;
    public static final int STR_SYSTEM_TASKS_NAME_13 = 467;
    public static final int STR_SYSTEM_TASKS_NAME_14 = 468;
    public static final int STR_SYSTEM_TASKS_NAME_15 = 469;
    public static final int STR_SYSTEM_TASKS_NAME_16 = 470;
    public static final int STR_SYSTEM_TASKS_NAME_17 = 471;
    public static final int STR_SYSTEM_TASKS_NAME_18 = 472;
    public static final int STR_SYSTEM_TASKS_NAME_19 = 473;
    public static final int STR_SYSTEM_TASKS_NAME_20 = 474;
    public static final int STR_SYSTEM_TASKS_NAME_21 = 475;
    public static final int STR_SYSTEM_TASKS_NAME_22 = 476;
    public static final int STR_SYSTEM_TASKS_NAME_23 = 477;
    public static final int STR_SYSTEM_TASKS_NAME_24 = 478;
    public static final int STR_SYSTEM_TASKS_NAME_25 = 479;
    public static final int STR_SYSTEM_TASKS_NAME_26 = 480;
    public static final int STR_SYSTEM_TASKS_NAME_27 = 481;
    public static final int STR_SYSTEM_TASKS_NAME_28 = 482;
    public static final int STR_SYSTEM_TASKS_NAME_29 = 483;
    public static final int STR_SYSTEM_TASKS_NAME_30 = 484;
    public static final int STR_SYSTEM_TASKS_NAME_31 = 485;
    public static final int STR_SYSTEM_TASKS_NAME_32 = 486;
    public static final int STR_SYSTEM_TASKS_NAME_33 = 487;
    public static final int STR_SYSTEM_TASKS_NAME_34 = 488;
    public static final int STR_SYSTEM_TASKS_NAME_35 = 489;
    public static final int STR_SYSTEM_TASKS_NAME_36 = 490;
    public static final int STR_SYSTEM_TASKS_NAME_37 = 491;
    public static final int STR_SYSTEM_TASKS_NAME_38 = 492;
    public static final int STR_SYSTEM_TASKS_NAME_39 = 493;
    public static final int STR_SYSTEM_TASKS_NAME_40 = 494;
    public static final int STR_SYSTEM_TASKS_NAME_41 = 495;
    public static final int STR_SYSTEM_TASKS_NAME_42 = 496;
    public static final int STR_SYSTEM_TASKS_NAME_43 = 497;
    public static final int STR_SYSTEM_TASKS_NAME_44 = 498;
    public static final int STR_SYSTEM_TASKS_NAME_45 = 499;
    public static final int STR_SYSTEM_TASKS_NAME_46 = 500;
    public static final int STR_SYSTEM_TASKS_NAME_47 = 501;
    public static final int STR_SYSTEM_TASKS_NAME_48 = 502;
    public static final int STR_SYSTEM_TASKS_NAME_49 = 503;
    public static final int STR_SYSTEM_TASKS_NAME_50 = 504;
    public static final int STR_SYSTEM_TASKS_NAME_51 = 505;
    public static final int STR_SYSTEM_TASKS_NAME_52 = 506;
    public static final int STR_SYSTEM_TASKS_NAME_53 = 507;
    public static final int STR_SYSTEM_TASKS_NAME_54 = 508;
    public static final int STR_SYSTEM_TASKS_NAME_55 = 509;
    public static final int STR_SYSTEM_TASKS_NAME_56 = 510;
    public static final int STR_SYSTEM_TASKS_NAME_57 = 511;
    public static final int STR_SYSTEM_TASKS_NAME_58 = 512;
    public static final int STR_SYSTEM_TASKS_NAME_59 = 513;
    public static final int STR_SYSTEM_TASKS_NAME_60 = 514;
    public static final int STR_SYSTEM_TASKS_NAME_61 = 515;
    public static final int STR_SYSTEM_TASKS_NAME_62 = 516;
    public static final int STR_SYSTEM_TASKS_NAME_63 = 517;
    public static final int STR_SYSTEM_TASKS_NAME_64 = 518;
    public static final int STR_SYSTEM_TASKS_NAME_65 = 519;
    public static final int STR_SYSTEM_TASKS_NAME_66 = 520;
    public static final int STR_SYSTEM_TASKS_NAME_67 = 521;
    public static final int STR_SYSTEM_TASKS_DESC_0 = 522;
    public static final int STR_SYSTEM_TASKS_DESC_1 = 523;
    public static final int STR_SYSTEM_TASKS_DESC_2 = 524;
    public static final int STR_SYSTEM_TASKS_DESC_3 = 525;
    public static final int STR_SYSTEM_TASKS_DESC_4 = 526;
    public static final int STR_SYSTEM_TASKS_DESC_5 = 527;
    public static final int STR_SYSTEM_TASKS_DESC_6 = 528;
    public static final int STR_SYSTEM_TASKS_DESC_7 = 529;
    public static final int STR_SYSTEM_TASKS_DESC_8 = 530;
    public static final int STR_SYSTEM_TASKS_DESC_9 = 531;
    public static final int STR_SYSTEM_TASKS_DESC_10 = 532;
    public static final int STR_SYSTEM_TASKS_DESC_11 = 533;
    public static final int STR_SYSTEM_TASKS_DESC_12 = 534;
    public static final int STR_SYSTEM_TASKS_DESC_13 = 535;
    public static final int STR_SYSTEM_TASKS_DESC_14 = 536;
    public static final int STR_SYSTEM_TASKS_DESC_15 = 537;
    public static final int STR_SYSTEM_TASKS_DESC_16 = 538;
    public static final int STR_SYSTEM_TASKS_DESC_17 = 539;
    public static final int STR_SYSTEM_TASKS_DESC_18 = 540;
    public static final int STR_SYSTEM_TASKS_DESC_19 = 541;
    public static final int STR_SYSTEM_TASKS_DESC_20 = 542;
    public static final int STR_SYSTEM_TASKS_DESC_21 = 543;
    public static final int STR_SYSTEM_TASKS_DESC_22 = 544;
    public static final int STR_SYSTEM_TASKS_DESC_23 = 545;
    public static final int STR_SYSTEM_TASKS_DESC_24 = 546;
    public static final int STR_SYSTEM_TASKS_DESC_25 = 547;
    public static final int STR_SYSTEM_TASKS_DESC_26 = 548;
    public static final int STR_SYSTEM_TASKS_DESC_27 = 549;
    public static final int STR_SYSTEM_TASKS_DESC_28 = 550;
    public static final int STR_SYSTEM_TASKS_DESC_29 = 551;
    public static final int STR_SYSTEM_TASKS_DESC_30 = 552;
    public static final int STR_SYSTEM_TASKS_DESC_31 = 553;
    public static final int STR_SYSTEM_TASKS_DESC_32 = 554;
    public static final int STR_SYSTEM_TASKS_DESC_33 = 555;
    public static final int STR_SYSTEM_TASKS_DESC_34 = 556;
    public static final int STR_SYSTEM_TASKS_DESC_35 = 557;
    public static final int STR_SYSTEM_TASKS_DESC_36 = 558;
    public static final int STR_SYSTEM_TASKS_DESC_37 = 559;
    public static final int STR_SYSTEM_TASKS_DESC_38 = 560;
    public static final int STR_SYSTEM_TASKS_DESC_39 = 561;
    public static final int STR_SYSTEM_TASKS_DESC_40 = 562;
    public static final int STR_SYSTEM_TASKS_DESC_41 = 563;
    public static final int STR_SYSTEM_TASKS_DESC_42 = 564;
    public static final int STR_SYSTEM_TASKS_DESC_43 = 565;
    public static final int STR_SYSTEM_TASKS_DESC_44 = 566;
    public static final int STR_SYSTEM_TASKS_DESC_45 = 567;
    public static final int STR_SYSTEM_TASKS_DESC_46 = 568;
    public static final int STR_SYSTEM_TASKS_DESC_47 = 569;
    public static final int STR_SYSTEM_TASKS_DESC_48 = 570;
    public static final int STR_SYSTEM_TASKS_DESC_49 = 571;
    public static final int STR_SYSTEM_TASKS_DESC_50 = 572;
    public static final int STR_SYSTEM_TASKS_DESC_51 = 573;
    public static final int STR_SYSTEM_TASKS_DESC_52 = 574;
    public static final int STR_SYSTEM_TASKS_DESC_53 = 575;
    public static final int STR_SYSTEM_TASKS_DESC_54 = 576;
    public static final int STR_SYSTEM_TASKS_DESC_55 = 577;
    public static final int STR_SYSTEM_TASKS_DESC_56 = 578;
    public static final int STR_SYSTEM_TASKS_DESC_57 = 579;
    public static final int STR_SYSTEM_TASKS_DESC_58 = 580;
    public static final int STR_SYSTEM_TASKS_DESC_59 = 581;
    public static final int STR_SYSTEM_TASKS_DESC_60 = 582;
    public static final int STR_SYSTEM_TASKS_DESC_61 = 583;
    public static final int STR_SYSTEM_TASKS_DESC_62 = 584;
    public static final int STR_SYSTEM_TASKS_DESC_63 = 585;
    public static final int STR_SYSTEM_TASKS_DESC_64 = 586;
    public static final int STR_SYSTEM_TASKS_DESC_65 = 587;
    public static final int STR_SYSTEM_TASKS_DESC_66 = 588;
    public static final int STR_SYSTEM_TASKS_DESC_67 = 589;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_0 = 590;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_1 = 591;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_2 = 592;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_3 = 593;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_4 = 594;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_5 = 595;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_6 = 596;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_7 = 597;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_8 = 598;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_9 = 599;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_10 = 600;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_11 = 601;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_12 = 602;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_13 = 603;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_14 = 604;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_15 = 605;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_16 = 606;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_17 = 607;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_18 = 608;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_19 = 609;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_20 = 610;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_21 = 611;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_22 = 612;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_23 = 613;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_24 = 614;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_25 = 615;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_26 = 616;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_27 = 617;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_28 = 618;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_29 = 619;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_30 = 620;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_31 = 621;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_32 = 622;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_33 = 623;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_34 = 624;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_35 = 625;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_36 = 626;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_37 = 627;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_38 = 628;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_39 = 629;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_40 = 630;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_41 = 631;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_42 = 632;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_43 = 633;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_44 = 634;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_45 = 635;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_46 = 636;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_47 = 637;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_48 = 638;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_49 = 639;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_50 = 640;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_51 = 641;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_52 = 642;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_53 = 643;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_54 = 644;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_55 = 645;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_56 = 646;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_57 = 647;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_58 = 648;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_59 = 649;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_60 = 650;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_61 = 651;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_62 = 652;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_63 = 653;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_64 = 654;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_65 = 655;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_66 = 656;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_67 = 657;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_68 = 658;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_69 = 659;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_70 = 660;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_71 = 661;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_72 = 662;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_73 = 663;
    public static final int STR_SYSTEM_SKILL_NAME_DESC_74 = 664;
    public static final int STR_SYSTEM_SMS_NAME_0 = 665;
    public static final int STR_SYSTEM_SMS_NAME_1 = 666;
    public static final int STR_SYSTEM_SMS_NAME_2 = 667;
    public static final int STR_SYSTEM_SMS_NAME_3 = 668;
    public static final int STR_SYSTEM_SMS_NAME_4 = 669;
    public static final int STR_SYSTEM_SMS_NAME_5 = 670;
    public static final int STR_SYSTEM_SMS_NAME_6 = 671;
    public static final int STR_SYSTEM_SMS_NAME_7 = 672;
    public static final int STR_SYSTEM_SMS_DESC_0 = 673;
    public static final int STR_SYSTEM_SMS_DESC_1 = 674;
    public static final int STR_SYSTEM_SMS_DESC_2 = 675;
    public static final int STR_SYSTEM_SMS_DESC_3 = 676;
    public static final int STR_SYSTEM_SMS_DESC_4 = 677;
    public static final int STR_SYSTEM_SMS_DESC_5 = 678;
    public static final int STR_SYSTEM_SMS_DESC_6 = 679;
    public static final int STR_SYSTEM_SMS_DESC_7 = 680;
    public static final int STR_SYSTEM_GAMEINFO_NAME_0 = 681;
    public static final int STR_SYSTEM_GAMEINFO_NAME_1 = 682;
    public static final int STR_SYSTEM_GAMEINFO_NAME_2 = 683;
    public static final int STR_SYSTEM_GAMEINFO_NAME_3 = 684;
    public static final int STR_SYSTEM_EFFORT_DESC_0 = 685;
    public static final int STR_SYSTEM_EFFORT_DESC_1 = 686;
    public static final int STR_SYSTEM_EFFORT_DESC_2 = 687;
    public static final int STR_SYSTEM_EFFORT_DESC_3 = 688;
    public static final int STR_SYSTEM_EFFORT_DESC_4 = 689;
    public static final int STR_SYSTEM_EFFORT_DESC_5 = 690;
    public static final int STR_SYSTEM_EFFORT_DESC_6 = 691;
    public static final int STR_SYSTEM_EFFORT_DESC_7 = 692;
    public static final int STR_SYSTEM_EFFORT_DESC_8 = 693;
    public static final int STR_SYSTEM_EFFORT_DESC_9 = 694;
    public static final int STR_SYSTEM_EFFORT_DESC_10 = 695;
    public static final int STR_SYSTEM_EFFORT_DESC_11 = 696;
    public static final int STR_SYSTEM_EFFORT_DESC_12 = 697;
    public static final int STR_SYSTEM_EFFORT_DESC_13 = 698;
    public static final int STR_SYSTEM_EFFORT_DESC_14 = 699;
    public static final int STR_SYSTEM_EFFORT_DESC_15 = 700;
    public static final int STR_SYSTEM_EFFORT_DESC_16 = 701;
    public static final int STR_SYSTEM_EFFORT_DESC_17 = 702;
    public static final int STR_SYSTEM_EFFORT_DESC_18 = 703;
    public static final int STR_SYSTEM_EFFORT_DESC_19 = 704;
    public static final int STR_SYSTEM_EFFORT_DESC_20 = 705;
    public static final int STR_SYSTEM_EFFORT_DESC_21 = 706;
    public static final int STR_SYSTEM_EFFORT_DESC_22 = 707;
    public static final int STR_SYSTEM_EFFORT_DESC_23 = 708;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_0 = 709;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_1 = 710;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_2 = 711;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_3 = 712;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_4 = 713;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_5 = 714;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_6 = 715;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_7 = 716;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_8 = 717;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_9 = 718;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_10 = 719;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_11 = 720;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_12 = 721;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_13 = 722;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_14 = 723;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_15 = 724;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_16 = 725;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_17 = 726;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_18 = 727;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_19 = 728;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_20 = 729;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_21 = 730;
    public static final int STR_SYSTEM_EFFORT_NAME_DESC_22 = 731;
    public static final int STR_SYSTEM_MIXTRUE_NAME_0 = 732;
    public static final int STR_SYSTEM_MIXTRUE_NAME_1 = 733;
    public static final int STR_SYSTEM_MIXTRUE_NAME_2 = 734;
    public static final int STR_SYSTEM_MIXTRUE_NAME_3 = 735;
    public static final int STR_SYSTEM_MIXTRUE_NAME_4 = 736;
    public static final int STR_SYSTEM_MIXTRUE_NAME_5 = 737;
    public static final int STR_SYSTEM_MIXTRUE_NAME_6 = 738;
    public static final int STR_SYSTEM_MIXTRUE_NAME_7 = 739;
    public static final int STR_SYSTEM_MIXTRUE_NAME_8 = 740;
    public static final int STR_SYSTEM_MIXTRUE_NAME_9 = 741;
    public static final int STR_SYSTEM_MIXTRUE_NAME_10 = 742;
    public static final int STR_SYSTEM_MIXTRUE_NAME_11 = 743;
    public static final int STR_SYSTEM_MIXTRUE_NAME_12 = 744;
    public static final int STR_SYSTEM_MIXTRUE_NAME_13 = 745;
    public static final int STR_SYSTEM_MIXTRUE_NAME_14 = 746;
    public static final int STR_SYSTEM_MIXTRUE_NAME_15 = 747;
    public static final int STR_SYSTEM_MIXTRUE_NAME_16 = 748;
    public static final int STR_SYSTEM_MIXTRUE_DESC_0 = 749;
    public static final int STR_SYSTEM_MIXTRUE_DESC_1 = 750;
    public static final int STR_SYSTEM_MIXTRUE_DESC_2 = 751;
    public static final int STR_SYSTEM_MIXTRUE_DESC_3 = 752;
    public static final int STR_SYSTEM_MIXTRUE_DESC_4 = 753;
    public static final int STR_SYSTEM_MIXTRUE_DESC_5 = 754;
    public static final int STR_SYSTEM_MIXTRUE_DESC_6 = 755;
    public static final int STR_SYSTEM_MIXTRUE_DESC_7 = 756;
    public static final int STR_SYSTEM_MIXTRUE_DESC_8 = 757;
    public static final int STR_SYSTEM_MIXTRUE_DESC_9 = 758;
    public static final int STR_SYSTEM_MIXTRUE_DESC_10 = 759;
    public static final int STR_SYSTEM_MIXTRUE_DESC_11 = 760;
    public static final int STR_SYSTEM_MIXTRUE_DESC_12 = 761;
    public static final int STR_SYSTEM_MIXTRUE_DESC_13 = 762;
    public static final int STR_SYSTEM_MIXTRUE_DESC_14 = 763;
    public static final int STR_SYSTEM_MIXTRUE_DESC_15 = 764;
    public static final int STR_SYSTEM_MIXTRUE_DESC_16 = 765;
    public static final int STR_SYSTEM_SHOP_NAME_0 = 766;
    public static final int STR_SYSTEM_SHOP_NAME_1 = 767;
    public static final int STR_SYSTEM_SHOP_NAME_2 = 768;
    public static final int STR_SYSTEM_SHOP_NAME_3 = 769;
    public static final int STR_SYSTEM_SHOP_NAME_4 = 770;
    public static final int STR_SYSTEM_SHOP_NAME_5 = 771;
    public static final int STR_SYSTEM_EVENT_DESC_0 = 772;
    public static final int STR_SYSTEM_EVENT_DESC_1 = 773;
    public static final int STR_SYSTEM_EVENT_DESC_2 = 774;
    public static final int STR_SYSTEM_EVENT_DESC_3 = 775;
    public static final int STR_SYSTEM_EVENT_DESC_4 = 776;
    public static final int STR_SYSTEM_EVENT_DESC_5 = 777;
    public static final int STR_SYSTEM_EVENT_DESC_6 = 778;
    public static final int STR_SYSTEM_EVENT_DESC_7 = 779;
    public static final int STR_SYSTEM_EVENT_DESC_8 = 780;
    public static final int STR_SYSTEM_EVENT_DESC_9 = 781;
    public static final int STR_SYSTEM_EVENT_DESC_10 = 782;
    public static final int STR_SYSTEM_EVENT_DESC_11 = 783;
    public static final int STR_SYSTEM_EVENT_DESC_12 = 784;
    public static final int STR_SYSTEM_EVENT_DESC_13 = 785;
    public static final int STR_SYSTEM_EVENT_DESC_14 = 786;
    public static final int STR_SYSTEM_EVENT_DESC_15 = 787;
    public static final int STR_SYSTEM_EVENT_DESC_16 = 788;
    public static final int STR_SYSTEM_EVENT_DESC_17 = 789;
    public static final int STR_SYSTEM_EVENT_DESC_18 = 790;
    public static final int STR_SYSTEM_EVENT_DESC_19 = 791;
    public static final int STR_SYSTEM_EVENT_DESC_20 = 792;
    public static final int STR_SYSTEM_EVENT_DESC_21 = 793;
    public static final int STR_SYSTEM_EVENT_DESC_22 = 794;
    public static final int STR_SYSTEM_EVENT_DESC_23 = 795;
    public static final int STR_SYSTEM_EVENT_DESC_24 = 796;
    public static final int STR_SYSTEM_EVENT_DESC_25 = 797;
    public static final int STR_SYSTEM_EVENT_DESC_26 = 798;
    public static final int STR_SYSTEM_EVENT_DESC_27 = 799;
    public static final int STR_SYSTEM_EVENT_DESC_28 = 800;
    public static final int STR_SYSTEM_EVENT_DESC_29 = 801;
    public static final int STR_SYSTEM_EVENT_DESC_30 = 802;
    public static final int STR_SYSTEM_EVENT_DESC_31 = 803;
    public static final int STR_SYSTEM_EVENT_DESC_32 = 804;
    public static final int STR_SYSTEM_EVENT_DESC_33 = 805;
    public static final int STR_SYSTEM_EVENT_DESC_34 = 806;
    public static final int STR_SYSTEM_EVENT_DESC_35 = 807;
    public static final int STR_SYSTEM_EVENT_DESC_36 = 808;
    public static final int STR_SYSTEM_EVENT_DESC_37 = 809;
    public static final int STR_SYSTEM_EVENT_DESC_38 = 810;
    public static final int STR_SYSTEM_EVENT_DESC_39 = 811;
    public static final int STR_SYSTEM_EVENT_DESC_40 = 812;
    public static final int STR_SYSTEM_EVENT_DESC_41 = 813;
    public static final int STR_SYSTEM_EVENT_DESC_42 = 814;
    public static final int STR_SYSTEM_EVENT_DESC_43 = 815;
    public static final int STR_SYSTEM_EVENT_DESC_44 = 816;
    public static final int STR_SYSTEM_EVENT_DESC_45 = 817;
    public static final int STR_SYSTEM_EVENT_DESC_46 = 818;
    public static final int STR_SYSTEM_EVENT_DESC_47 = 819;
    public static final int STR_SYSTEM_EVENT_DESC_48 = 820;
    public static final int STR_SYSTEM_EVENT_DESC_49 = 821;
    public static final int STR_SYSTEM_EVENT_DESC_50 = 822;
    public static final int STR_SYSTEM_EVENT_DESC_51 = 823;
    public static final int STR_SYSTEM_EVENT_DESC_52 = 824;
    public static final int STR_SYSTEM_EVENT_DESC_53 = 825;
    public static final int STR_SYSTEM_EVENT_DESC_54 = 826;
    public static final int STR_SYSTEM_EVENT_DESC_55 = 827;
    public static final int STR_SYSTEM_EVENT_DESC_56 = 828;
    public static final int STR_SYSTEM_EVENT_DESC_57 = 829;
    public static final int STR_SYSTEM_EVENT_DESC_58 = 830;
    public static final int STR_SYSTEM_EVENT_DESC_59 = 831;
    public static final int STR_SYSTEM_EVENT_DESC_60 = 832;
    public static final int STR_SYSTEM_EVENT_DESC_61 = 833;
    public static final int STR_SYSTEM_EVENT_DESC_62 = 834;
    public static final int STR_SYSTEM_EVENT_DESC_63 = 835;
    public static final int STR_SYSTEM_EVENT_DESC_64 = 836;
    public static final int STR_SYSTEM_EVENT_DESC_65 = 837;
    public static final int STR_SYSTEM_EVENT_DESC_66 = 838;
    public static final int STR_SYSTEM_EVENT_DESC_67 = 839;
    public static final int STR_SYSTEM_EVENT_DESC_68 = 840;
    public static final int STR_SYSTEM_EVENT_DESC_69 = 841;
    public static final int STR_SYSTEM_EVENT_DESC_70 = 842;
    public static final int STR_SYSTEM_EVENT_DESC_71 = 843;
    public static final int STR_SYSTEM_EVENT_DESC_72 = 844;
    public static final int STR_SYSTEM_EVENT_DESC_73 = 845;
    public static final int STR_SYSTEM_EVENT_DESC_74 = 846;
    public static final int STR_SYSTEM_EVENT_DESC_75 = 847;
    public static final int STR_SYSTEM_EVENT_DESC_76 = 848;
    public static final int STR_SYSTEM_EVENT_DESC_77 = 849;
    public static final int STR_SYSTEM_EVENT_DESC_78 = 850;
    public static final int STR_SYSTEM_EVENT_DESC_79 = 851;
    public static final int STR_SYSTEM_EVENT_DESC_80 = 852;
    public static final int STR_SYSTEM_EVENT_DESC_81 = 853;
    public static final int STR_SYSTEM_EVENT_DESC_82 = 854;
    public static final int STR_SYSTEM_EVENT_DESC_83 = 855;
    public static final int STR_SYSTEM_EVENT_DESC_84 = 856;
    public static final int STR_SYSTEM_EVENT_DESC_85 = 857;
    public static final int STR_SYSTEM_EVENT_DESC_86 = 858;
    public static final int STR_SYSTEM_EVENT_DESC_87 = 859;
    public static final int STR_SYSTEM_EVENT_DESC_88 = 860;
    public static final int STR_SYSTEM_EVENT_DESC_89 = 861;
    public static final int STR_SYSTEM_EVENT_DESC_90 = 862;
    public static final int STR_SYSTEM_EVENT_DESC_91 = 863;
    public static final int STR_SYSTEM_EVENT_DESC_92 = 864;
    public static final int STR_SYSTEM_EVENT_DESC_93 = 865;
    public static final int STR_SYSTEM_EVENT_DESC_94 = 866;
    public static final int STR_SYSTEM_EVENT_DESC_95 = 867;
    public static final int STR_SYSTEM_EVENT_DESC_96 = 868;
    public static final int STR_SYSTEM_EVENT_DESC_97 = 869;
    public static final int STR_SYSTEM_EVENT_DESC_98 = 870;
    public static final int STR_SYSTEM_EVENT_DESC_99 = 871;
    public static final int STR_SYSTEM_EVENT_DESC_100 = 872;
    public static final int STR_SYSTEM_EVENT_DESC_101 = 873;
    public static final int STR_SYSTEM_EVENT_DESC_102 = 874;
    public static final int STR_SYSTEM_EVENT_DESC_103 = 875;
    public static final int STR_SYSTEM_EVENT_DESC_104 = 876;
    public static final int STR_SYSTEM_EVENT_DESC_105 = 877;
    public static final int STR_SYSTEM_EVENT_DESC_106 = 878;
    public static final int STR_SYSTEM_EVENT_DESC_107 = 879;
    public static final int STR_SYSTEM_EVENT_DESC_108 = 880;
    public static final int STR_SYSTEM_EVENT_DESC_109 = 881;
    public static final int STR_SYSTEM_EVENT_DESC_110 = 882;
    public static final int STR_SYSTEM_EVENT_DESC_111 = 883;
    public static final int STR_SYSTEM_EVENT_DESC_112 = 884;
    public static final int STR_SYSTEM_EVENT_DESC_113 = 885;
    public static final int STR_SYSTEM_EVENT_DESC_114 = 886;
    public static final int STR_SYSTEM_EVENT_DESC_115 = 887;
    public static final int STR_SYSTEM_EVENT_DESC_116 = 888;
    public static final int STR_SYSTEM_EVENT_DESC_117 = 889;
    public static final int STR_SYSTEM_EVENT_DESC_118 = 890;
    public static final int STR_SYSTEM_EVENT_DESC_119 = 891;
    public static final int STR_SYSTEM_EVENT_DESC_120 = 892;
    public static final int STR_SYSTEM_EVENT_DESC_121 = 893;
    public static final int STR_SYSTEM_EVENT_DESC_122 = 894;
    public static final int STR_SYSTEM_EVENT_DESC_123 = 895;
    public static final int STR_SYSTEM_EVENT_DESC_124 = 896;
    public static final int STR_SYSTEM_EVENT_DESC_125 = 897;
    public static final int STR_SYSTEM_EVENT_DESC_126 = 898;
    public static final int STR_SYSTEM_EVENT_DESC_127 = 899;
    public static final int STR_SYSTEM_EVENT_DESC_128 = 900;
    public static final int STR_SYSTEM_EVENT_DESC_129 = 901;
    public static final int STR_SYSTEM_EVENT_DESC_130 = 902;
    public static final int STR_SYSTEM_EVENT_DESC_131 = 903;
    public static final int STR_SYSTEM_EVENT_DESC_132 = 904;
    public static final int STR_SYSTEM_EVENT_DESC_133 = 905;
    public static final int STR_SYSTEM_EVENT_DESC_134 = 906;
    public static final int STR_SYSTEM_EVENT_DESC_135 = 907;
    public static final int STR_SYSTEM_EVENT_DESC_136 = 908;
    public static final int STR_SYSTEM_EVENT_DESC_137 = 909;
    public static final int STR_SYSTEM_EVENT_DESC_138 = 910;
    public static final int STR_SYSTEM_EVENT_DESC_139 = 911;
    public static final int STR_SYSTEM_EVENT_DESC_140 = 912;
    public static final int STR_SYSTEM_EVENT_DESC_141 = 913;
    public static final int STR_SYSTEM_EVENT_DESC_142 = 914;
    public static final int STR_SYSTEM_EVENT_DESC_143 = 915;
    public static final int STR_SYSTEM_EVENT_DESC_144 = 916;
    public static final int STR_SYSTEM_EVENT_DESC_145 = 917;
    public static final int STR_SYSTEM_EVENT_DESC_146 = 918;
    public static final int STR_SYSTEM_EVENT_DESC_147 = 919;
    public static final int STR_SYSTEM_EVENT_DESC_148 = 920;
    public static final int STR_SYSTEM_EVENT_DESC_149 = 921;
    public static final int STR_SYSTEM_EVENT_DESC_150 = 922;
    public static final int STR_SYSTEM_EVENT_DESC_151 = 923;
    public static final int STR_SYSTEM_EVENT_DESC_152 = 924;
    public static final int STR_SYSTEM_EVENT_DESC_153 = 925;
    public static final int STR_SYSTEM_EVENT_DESC_154 = 926;
    public static final int STR_SYSTEM_EVENT_DESC_155 = 927;
    public static final int STR_SYSTEM_EVENT_DESC_156 = 928;
    public static final int STR_SYSTEM_EVENT_DESC_157 = 929;
    public static final int STR_SYSTEM_EVENT_DESC_158 = 930;
    public static final int STR_SYSTEM_EVENT_DESC_159 = 931;
    public static final int STR_SYSTEM_EVENT_DESC_160 = 932;
    public static final int STR_SYSTEM_EVENT_DESC_161 = 933;
    public static final int STR_SYSTEM_EVENT_DESC_162 = 934;
    public static final int STR_SYSTEM_EVENT_DESC_163 = 935;
    public static final int STR_SYSTEM_EVENT_DESC_164 = 936;
    public static final int STR_SYSTEM_EVENT_DESC_165 = 937;
    public static final int STR_SYSTEM_EVENT_DESC_166 = 938;
    public static final int STR_SYSTEM_EVENT_DESC_167 = 939;
    public static final int STR_SYSTEM_EVENT_DESC_168 = 940;
    public static final int STR_SYSTEM_EVENT_DESC_169 = 941;
    public static final int STR_SYSTEM_EVENT_DESC_170 = 942;
    public static final int STR_SYSTEM_EVENT_DESC_171 = 943;
    public static final int STR_SYSTEM_EVENT_DESC_172 = 944;
    public static final int STR_SYSTEM_EVENT_DESC_173 = 945;
    public static final int STR_SYSTEM_EVENT_DESC_174 = 946;
    public static final int STR_SYSTEM_EVENT_DESC_175 = 947;
    public static final int STR_SYSTEM_EVENT_DESC_176 = 948;
    public static final int STR_SYSTEM_EVENT_DESC_177 = 949;
    public static final int STR_SYSTEM_EVENT_DESC_178 = 950;
    public static final int STR_SYSTEM_EVENT_DESC_179 = 951;
    public static final int STR_SYSTEM_EVENT_DESC_180 = 952;
    public static final int STR_SYSTEM_EVENT_DESC_181 = 953;
    public static final int STR_SYSTEM_EVENT_DESC_182 = 954;
    public static final int STR_SYSTEM_EVENT_DESC_183 = 955;
    public static final int STR_SYSTEM_EVENT_DESC_184 = 956;
    public static final int STR_SYSTEM_EVENT_DESC_185 = 957;
    public static final int STR_SYSTEM_EVENT_DESC_186 = 958;
    public static final int STR_SYSTEM_EVENT_DESC_187 = 959;
    public static final int STR_SYSTEM_EVENT_DESC_188 = 960;
    public static final int STR_SYSTEM_EVENT_DESC_189 = 961;
    public static final int STR_SYSTEM_EVENT_DESC_190 = 962;
    public static final int STR_SYSTEM_EVENT_DESC_191 = 963;
    public static final int STR_SYSTEM_EVENT_DESC_192 = 964;
    public static final int STR_SYSTEM_EVENT_DESC_193 = 965;
    public static final int STR_SYSTEM_EVENT_DESC_194 = 966;
    public static final int STR_SYSTEM_EVENT_DESC_195 = 967;
    public static final int STR_SYSTEM_EVENT_DESC_196 = 968;
    public static final int STR_SYSTEM_EVENT_DESC_197 = 969;
    public static final int STR_SYSTEM_EVENT_DESC_198 = 970;
    public static final int STR_SYSTEM_EVENT_DESC_199 = 971;
    public static final int STR_SYSTEM_EVENT_DESC_200 = 972;
    public static final int STR_SYSTEM_EVENT_DESC_201 = 973;
    public static final int STR_SYSTEM_EVENT_DESC_202 = 974;
    public static final int STR_SYSTEM_EVENT_DESC_203 = 975;
    public static final int STR_SYSTEM_EVENT_DESC_204 = 976;
    public static final int STR_SYSTEM_EVENT_DESC_205 = 977;
    public static final int STR_SYSTEM_EVENT_DESC_206 = 978;
    public static final int STR_SYSTEM_EVENT_DESC_207 = 979;
    public static final int STR_SYSTEM_EVENT_DESC_208 = 980;
    public static final int STR_SYSTEM_EVENT_DESC_209 = 981;
    public static final int STR_SYSTEM_EVENT_DESC_210 = 982;
    public static final int STR_SYSTEM_EVENT_DESC_211 = 983;
    public static final int STR_SYSTEM_EVENT_DESC_212 = 984;
    public static final int STR_SYSTEM_EVENT_DESC_213 = 985;
    public static final int STR_SYSTEM_EVENT_DESC_214 = 986;
    public static final int STR_SYSTEM_EVENT_DESC_215 = 987;
    public static final int STR_SYSTEM_EVENT_DESC_216 = 988;
    public static final int STR_SYSTEM_EVENT_DESC_217 = 989;
    public static final int STR_SYSTEM_EVENT_DESC_218 = 990;
    public static final int STR_SYSTEM_EVENT_DESC_219 = 991;
    public static final int STR_SYSTEM_EVENT_DESC_220 = 992;
    public static final int STR_SYSTEM_EVENT_DESC_221 = 993;
    public static final int STR_SYSTEM_EVENT_DESC_222 = 994;
    public static final int STR_SYSTEM_EVENT_DESC_223 = 995;
    public static final int STR_SYSTEM_EVENT_DESC_224 = 996;
    public static final int STR_SYSTEM_EVENT_DESC_225 = 997;
    public static final int STR_SYSTEM_EVENT_DESC_226 = 998;
    public static final int STR_SYSTEM_EVENT_DESC_227 = 999;
    public static final int STR_SYSTEM_EVENT_DESC_228 = 1000;
    public static final int STR_SYSTEM_EVENT_DESC_229 = 1001;
    public static final int STR_SYSTEM_EVENT_DESC_230 = 1002;
    public static final int STR_SYSTEM_EVENT_DESC_231 = 1003;
    public static final int STR_SYSTEM_EVENT_DESC_232 = 1004;
    public static final int STR_SYSTEM_EVENT_DESC_233 = 1005;
    public static final int STR_SYSTEM_EVENT_DESC_234 = 1006;
    public static final int STR_SYSTEM_EVENT_DESC_235 = 1007;
    public static final int STR_SYSTEM_EVENT_DESC_236 = 1008;
    public static final int STR_SYSTEM_EVENT_DESC_237 = 1009;
    public static final int STR_SYSTEM_EVENT_DESC_238 = 1010;
    public static final int STR_SYSTEM_EVENT_DESC_239 = 1011;
    public static final int STR_SYSTEM_EVENT_DESC_240 = 1012;
    public static final int STR_SYSTEM_EVENT_DESC_241 = 1013;
    public static final int STR_SYSTEM_EVENT_DESC_242 = 1014;
    public static final int STR_SYSTEM_EVENT_DESC_243 = 1015;
    public static final int STR_SYSTEM_EVENT_DESC_244 = 1016;
    public static final int STR_SYSTEM_EVENT_DESC_245 = 1017;
    public static final int STR_SYSTEM_EVENT_DESC_246 = 1018;
    public static final int STR_SYSTEM_EVENT_DESC_247 = 1019;
    public static final int STR_SYSTEM_EVENT_DESC_248 = 1020;
    public static final int STR_SYSTEM_EVENT_DESC_249 = 1021;
    public static final int STR_SYSTEM_EVENT_DESC_250 = 1022;
    public static final int STR_SYSTEM_EVENT_DESC_251 = 1023;
    public static final int STR_SYSTEM_EVENT_DESC_252 = 1024;
    public static final int STR_SYSTEM_EVENT_DESC_253 = 1025;
    public static final int STR_SYSTEM_EVENT_DESC_254 = 1026;
    public static final int STR_SYSTEM_EVENT_DESC_255 = 1027;
    public static final int STR_SYSTEM_EVENT_DESC_256 = 1028;
    public static final int STR_SYSTEM_EVENT_DESC_257 = 1029;
    public static final int STR_SYSTEM_EVENT_DESC_258 = 1030;
    public static final int STR_SYSTEM_EVENT_DESC_259 = 1031;
    public static final int STR_SYSTEM_EVENT_DESC_260 = 1032;
    public static final int STR_SYSTEM_EVENT_DESC_261 = 1033;
    public static final int STR_SYSTEM_EVENT_DESC_262 = 1034;
    public static final int STR_SYSTEM_EVENT_DESC_263 = 1035;
    public static final int STR_SYSTEM_EVENT_DESC_264 = 1036;
    public static final int STR_SYSTEM_EVENT_DESC_265 = 1037;
    public static final int STR_SYSTEM_EVENT_DESC_266 = 1038;
    public static final int STR_SYSTEM_EVENT_DESC_267 = 1039;
    public static final int STR_SYSTEM_EVENT_DESC_268 = 1040;
    public static final int STR_SYSTEM_EVENT_DESC_269 = 1041;
    public static final int STR_SYSTEM_EVENT_DESC_270 = 1042;
    public static final int STR_SYSTEM_EVENT_DESC_271 = 1043;
    public static final int STR_SYSTEM_EVENT_DESC_272 = 1044;
    public static final int STR_SYSTEM_EVENT_DESC_273 = 1045;
    public static final int STR_SYSTEM_EVENT_DESC_274 = 1046;
    public static final int STR_SYSTEM_EVENT_DESC_275 = 1047;
    public static final int STR_SYSTEM_EVENT_DESC_276 = 1048;
    public static final int STR_SYSTEM_EVENT_DESC_277 = 1049;
    public static final int STR_SYSTEM_EVENT_DESC_278 = 1050;
    public static final int STR_SYSTEM_EVENT_DESC_279 = 1051;
    public static final int STR_SYSTEM_EVENT_DESC_280 = 1052;
    public static final int STR_SYSTEM_EVENT_DESC_281 = 1053;
    public static final int STR_SYSTEM_EVENT_DESC_282 = 1054;
    public static final int STR_SYSTEM_EVENT_DESC_283 = 1055;
    public static final int STR_SYSTEM_EVENT_DESC_284 = 1056;
    public static final int STR_SYSTEM_EVENT_DESC_285 = 1057;
    public static final int STR_SYSTEM_EVENT_DESC_286 = 1058;
    public static final int STR_SYSTEM_EVENT_DESC_287 = 1059;
    public static final int STR_SYSTEM_EVENT_DESC_288 = 1060;
    public static final int STR_SYSTEM_EVENT_DESC_289 = 1061;
    public static final int STR_SYSTEM_EVENT_DESC_290 = 1062;
    public static final int STR_SYSTEM_EVENT_DESC_291 = 1063;
    public static final int STR_SYSTEM_EVENT_DESC_292 = 1064;
    public static final int STR_SYSTEM_GAME_DATA_MONEY = 1065;
    public static final int STR_SYSTEM_GAME_DATA_TOKEN = 1066;
    public static final int STR_SYSTEM_GAME_DATA_CONSUMPTION = 1067;
    public static final int STR_SYSTEM_GAME_DATA_KILL = 1068;
    public static final int STR_SYSTEM_GAME_DATA_WIN = 1069;
    public static final int STR_SYSTEM_GAME_DATA_EQUIP = 1070;
    public static final int STR_SYSTEM_GAME_DATA_DISCOUNT = 1071;
    public static final int STR_SYSTEM_GAME_DATA_SCORE = 1072;
    public static final int STR_SYSTEM_GAME_DATA_EFFORT = 1073;
    public static final int STR_SYSTEM_GAME_DATA_DESIGH = 1074;
    public static final int STR_SYSTEM_EFFORT_AWARD_HP = 1075;
    public static final int STR_SYSTEM_EFFORT_AWARD_MP = 1076;
    public static final int STR_SYSTEM_EFFORT_AWARD_NAP = 1077;
    public static final int STR_SYSTEM_EFFORT_AWARD_SAP = 1078;
    public static final int STR_SYSTEM_EFFORT_AWARD_DP = 1079;
    public static final int STR_SYSTEM_EFFORT_AWARD_CH = 1080;
    public static final int STR_SYSTEM_EFFORT_AWARD_DODGE = 1081;
    public static final int STR_SYSTEM_EFFORT_AWARD_SPEED = 1082;
    public static final int STR_SYSTEM_LV_LESS = 1083;
    public static final int STR_SYSTEM_FORG_LESS = 1084;
    public static final int STR_SYSTEM_EQUIP_ERROR = 1085;
    public static final int STR_SYSTEM_BATTLE_USE_ONLY = 1086;
    public static final int STR_SYSTEM_UI_SAVE_MENU_RMS_LOAD = 1087;
    public static final int STR_SYSTEM_UI_SAVE_MENU_RMS_COVER = 1088;
    public static final int STR_SYSTEM_UI_SAVE_MENU_RMS_SAVE = 1089;
    public static final int STR_SYSTEM_SAVE_SUCCESS = 1090;
    public static final int STR_SYSTEM_SAVEING = 1091;
    public static final int STR_SYSTEM_UI_SAVE_MENU_RMS0 = 1092;
    public static final int STR_SYSTEM_UI_SAVE_MENU_RMS1 = 1093;
    public static final int STR_SYSTEM_UI_SAVE_MENU_RMS2 = 1094;
    public static final int STR_SYSTEM_UI_NULL_TEXT = 1095;
    public static final int STR_SYSTEM_KONG = 1096;
    public static final int STR_SYSTEM_EQUIP_TYPE_WEAPON = 1097;
    public static final int STR_SYSTEM_EQUIP_TYPE_ARMOR = 1098;
    public static final int STR_SYSTEM_EQUIP_TYPE_OTHER = 1099;
    public static final int STR_SYSTEM_CHOOSE = 1100;
    public static final int STR_SYSTEM_BACK = 1101;
    public static final int STR_SYSTEM_SCENESHOP_TITLE = 1102;
    public static final int STR_SYSTEM_SCENESHOP_0 = 1103;
    public static final int STR_SYSTEM_SCENESHOP_1 = 1104;
    public static final int STR_SYSTEM_SCENESHOP_2 = 1105;
    public static final int STR_SYSTEM_SCENESHOP_3 = 1106;
    public static final int STR_SYSTEM_SCENESHOP_4 = 1107;
    public static final int STR_SYSTEM_ACTIVATE_FLY = 1108;
    public static final int STR_SYSTEM_LESSMONEY = 1109;
    public static final int STR_SYSTEM_LESSLV = 1110;
    public static final int STR_SYSTEM_ISBUY = 1111;
    public static final int STR_SYSTEM_ISSELL = 1112;
    public static final int STR_SYSTEM_ASK_ADVANCE = 1113;
    public static final int STR_SYSTEM_ASK_LEARN_SKILL = 1114;
    public static final int STR_SYSTEM_NOSKILLS_DESC = 1115;
    public static final int STR_SYSTEM_SKILL_MAX = 1116;
    public static final int STR_SYSTEM_HAVA_SKILL_LVUP = 1117;
    public static final int STR_SYSTEM_ADVANCE_MAX = 1118;
    public static final int STR_SYSTEM_SKILL_OF_HERO = 1119;
    public static final int STR_SYSTEM_SKILL_ADVANCE = 1120;
    public static final int STR_SYSTEM_EQUIP_PURPOSE0 = 1121;
    public static final int STR_SYSTEM_EQUIP_PURPOSE1 = 1122;
    public static final int STR_SYSTEM_EQUIP_PURPOSE2 = 1123;
    public static final int STR_SYSTEM_EQUIP_PURPOSE3 = 1124;
    public static final int STR_SYSTEM_EQUIP_PURPOSE4 = 1125;
    public static final int STR_SYSTEM_EQUIP_PURPOSE5 = 1126;
    public static final int STR_SYSTEM_EQUIP_PURPOSE6 = 1127;
    public static final int STR_SYSTEM_EQUIP_PURPOSE7 = 1128;
    public static final int STR_SYSTEM_EQUIP_CHANGE = 1129;
    public static final int STR_SYSTEM_EQUIP_ADVANCE = 1130;
    public static final int STR_SYSTEM_EQUIP_CHANCEL = 1131;
    public static final int STR_SYSTEM_EQU_ADV_SUC = 1132;
    public static final int STR_SYSTEM_ISEIXT = 1133;
    public static final int STR_SYSTEM_ISRETURN = 1134;
    public static final int STR_SYSTEM_GAME_HELP = 1135;
    public static final int STR_SYSTEM_GAME_BUY_SUCCESS = 1136;
    public static final int STR_SYSTEM_CHANGE_EQUIP = 1137;
    public static final int STR_SYSTEM_TAKE_OFF = 1138;
    public static final int STR_SYSTEM_ITEM = 1139;
    public static final int STR_SYSTEM_USE_ITEM = 1140;
    public static final int STR_SYSTEM_USE_README = 1141;
    public static final int STR_SYSTEM_YES = 1142;
    public static final int STR_SYSTEM_NO = 1143;
    public static final int STR_SYSTEM_CANNOTSKILL = 1144;
    public static final int STR_SYSTEM_NOSKILL = 1145;
    public static final int STR_SYSTEM_NOITEM = 1146;
    public static final int STR_SYSTEM_LESSMP = 1147;
    public static final int STR_SYSTEM_LESSSP = 1148;
    public static final int STR_SYSTEM_CD_TIME = 1149;
    public static final int STR_SYSTEM_WIN = 1150;
    public static final int STR_SYSTEM_LOSE = 1151;
    public static final int STR_SYSTEM_ALL_DIE = 1152;
    public static final int STR_SYSTEM_ESCAPE_WIN = 1153;
    public static final int STR_SYSTEM_ESCAPE_LOSE = 1154;
    public static final int STR_SYSTEM_ESCAPE_CANNOT = 1155;
    public static final int STR_SYSTEM_GETEXP = 1156;
    public static final int STR_SYSTEM_GETITEM = 1157;
    public static final int STR_SYSTEM_GETIMONEY = 1158;
    public static final int STR_SYSTEM_LVUP = 1159;
    public static final int STR_SYSTEM_LEARN = 1160;
    public static final int STR_SYSTEM_X = 1161;
    public static final int STR_SYSTEM_ATTACK = 1162;
    public static final int STR_SYSTEM_TEAM_EMPTY = 1163;
    public static final int STR_SYSTEM_TEAM_FULL = 1164;
    public static final int STR_SYSTEM_EMPTYRMS = 1165;
    public static final int STR_SYSTEM_HP_FULL = 1166;
    public static final int STR_SYSTEM_MP_FULL = 1167;
    public static final int STR_SYSTEM_HPMP_FULL = 1168;
    public static final int STR_SYSTEM_SOUND = 1169;
    public static final int STR_SYSTEM_QUICKEN = 1170;
    public static final int STR_SYSTEM_SKIP = 1171;
    public static final int STR_SYSTEM_HAVEUSED = 1172;
    public static final int STR_SYSTEM_LEARN_SUCCESS = 1173;
    public static final int STR_SYSTEM_SELECT_NUM = 1174;
    public static final int STR_SYSTEM_SELECT_NUM_BUY = 1175;
    public static final int STR_SYSTEM_SELECT_NUM_SELL = 1176;
    public static final int STR_SYSTEM_COST_MONEY = 1177;
    public static final int STR_SYSTEM_GAME_OVER = 1178;
    public static final int STR_SYSTEM_SCENE_NAME_0 = 1179;
    public static final int STR_SYSTEM_SAVE_ONTIME = 1180;
    public static final int STR_SYSTEM_SAVE_ON = 1181;
    public static final int STR_SYSTEM_UNAVAILABLE = 1182;
    public static final int STR_SYSTEM_LOADING_NEWS_0 = 1183;
    public static final int STR_SYSTEM_LOADING_NEWS_1 = 1184;
    public static final int STR_SYSTEM_LOADING_NEWS_2 = 1185;
    public static final int STR_SYSTEM_LOADING_NEWS_3 = 1186;
    public static final int STR_SYSTEM_LOADING_NEWS_4 = 1187;
    public static final int STR_SYSTEM_LOADING_NEWS_END = 1188;
    public static final int STR_SYSTEM_IGM_MISSION = 1189;
    public static final int STR_SYSTEM_MAIN_MISSION = 1190;
    public static final int STR_SYSTEM_SUB_MISSION = 1191;
    public static final int STR_SYSTEM_NOTITEM = 1192;
    public static final int STR_SYSTEM_FIRE = 1193;
    public static final int STR_SYSTEM_CANCEL = 1194;
    public static final int STR_SYSTEM_SOUND_OFF = 1195;
    public static final int STR_SYSTEM_ITEM_NAME = 1196;
    public static final int STR_SYSTEM_ITEM_COUNT = 1197;
    public static final int STR_SYSTEM_ITEM_MONEY = 1198;
    public static final int STR_SYSTEM_SKILL_NAME = 1199;
    public static final int STR_SYSTEM_SKILL_COST = 1200;
    public static final int STR_SYSTEM_ITEM_LEVEL = 1201;
    public static final int STR_SYSTEM_GS_OPTION_NAME_0 = 1202;
    public static final int STR_SYSTEM_GS_OPTION_NAME_1 = 1203;
    public static final int STR_SYSTEM_GS_OPTION_NAME_ON = 1204;
    public static final int STR_SYSTEM_GS_OPTION_NAME_OFF = 1205;
    public static final int STR_SYSTEM_EFFORT_EASY = 1206;
    public static final int STR_SYSTEM_EFFORT_NORMAL = 1207;
    public static final int STR_SYSTEM_EFFORT_HARD = 1208;
    public static final int STR_SYSTEM_EFFORT_EXPERTS = 1209;
    public static final int STR_SYSTEM_EFFORT_UNENABLE = 1210;
    public static final int STR_SYSTEM_EFFORT_GET_ITEM = 1211;
    public static final int STR_SYSTEM_EFFORT_STATUS = 1212;
    public static final int STR_SYSTEM_EFFORT_COMPLETE = 1213;
    public static final int STR_SYSTEM_EFFORT_UNFINISHED = 1214;
    public static final int STR_SYSTEM_GS_HELP_TITLE = 1215;
    public static final int STR_SYSTEM_GS_HELP_NAME_0 = 1216;
    public static final int STR_SYSTEM_GS_HELP_NAME_1 = 1217;
    public static final int STR_SYSTEM_GS_HELP_NAME_2 = 1218;
    public static final int STR_SYSTEM_GS_HELP_NAME_3 = 1219;
    public static final int STR_SYSTEM_GS_HELP_NAME_4 = 1220;
    public static final int STR_SYSTEM_GS_HELP_NAME_5 = 1221;
    public static final int STR_SYSTEM_GS_HELP_NAME_6 = 1222;
    public static final int STR_SYSTEM_GS_HELP_NAME_7 = 1223;
    public static final int STR_SYSTEM_GS_HELP_NAME_8 = 1224;
    public static final int STR_SYSTEM_GS_HELP_NAME_9 = 1225;
    public static final int STR_SYSTEM_GS_HELP_NAME_10 = 1226;
    public static final int STR_SYSTEM_GS_HELP_NAME_11 = 1227;
    public static final int STR_SYSTEM_GS_HELP_NAME_12 = 1228;
    public static final int STR_SYSTEM_GS_HELP_NAME_13 = 1229;
    public static final int STR_SYSTEM_GS_HELP_NAME_14 = 1230;
    public static final int STR_SYSTEM_GS_HELP_NAME_15 = 1231;
    public static final int STR_SYSTEM_GS_HELP_NAME_16 = 1232;
    public static final int STR_SYSTEM_GS_HELP_NAME_17 = 1233;
    public static final int STR_SYSTEM_GS_HELP_NAME_END = 1234;
    public static final int STR_SYSTEM_GS_HELP_CONNECT_0 = 1235;
    public static final int STR_SYSTEM_GS_HELP_CONNECT_1 = 1236;
    public static final int STR_SYSTEM_GS_HELP_CONNECT_2 = 1237;
    public static final int STR_SYSTEM_GS_HELP_CONNECT_3 = 1238;
    public static final int STR_SYSTEM_GS_HELP_CONNECT_4 = 1239;
    public static final int STR_SYSTEM_GS_HELP_CONNECT_END = 1240;
    public static final int STR_SYSTEM_GS_HELP_CONTENT = 1241;
    public static final int STR_SYSTEM_GS_ABOUT_TITLE = 1242;
    public static final int STR_SYSTEM_GS_ABOUT_NAME_0 = 1243;
    public static final int STR_SYSTEM_GS_ABOUT_NAME_1 = 1244;
    public static final int STR_SYSTEM_GS_ABOUT_NAME_2 = 1245;
    public static final int STR_SYSTEM_GS_ABOUT_NAME_3 = 1246;
    public static final int STR_SYSTEM_GS_ABOUT_NAME_4 = 1247;
    public static final int STR_SYSTEM_GS_ABOUT_NAME_5 = 1248;
    public static final int STR_SYSTEM_GS_ABOUT_NAME_6 = 1249;
    public static final int STR_SYSTEM_GS_ABOUT_NAME_7 = 1250;
    public static final int STR_SYSTEM_GS_ABOUT_NAME_8 = 1251;
    public static final int STR_SYSTEM_GS_ABOUT_NAME_9 = 1252;
    public static final int STR_SYSTEM_GS_ABOUT_NAME_10 = 1253;
    public static final int STR_SYSTEM_GS_ABOUT_NAME_11 = 1254;
    public static final int STR_SYSTEM_GS_ABOUT_NAME_END = 1255;
    public static final int STR_SYSTEM_GS_ABOUT_CONTENT = 1256;
    public static final int STR_SYSTEM_MORE_GAME_TIP = 1257;
    public static final int STR_SYSTEM_SMS_COMPELETE = 1258;
    public static final int STR_SYSTEM_SMS_PRICE_2RMB = 1259;
    public static final int STR_SYSTEM_SMS_PRICE_4RMB = 1260;
    public static final int STR_SYSTEM_SMS_PRICE_DO_4RMB = 1261;
    public static final int STR_SYSTEM_SMS_SENDING = 1262;
    public static final int STR_SYSTEM_SMS_SUCCESS = 1263;
    public static final int STR_SYSTEM_SMS_FAIL = 1264;
    public static final int STR_SYSTEM_SMS_SHOP_INACTIVE = 1265;
    public static final int STR_SYSTEM_SMS_SUCCESS_0 = 1266;
    public static final int STR_SYSTEM_SMS_SUCCESS_1 = 1267;
    public static final int STR_SYSTEM_SMS_SUCCESS_2 = 1268;
    public static final int STR_SYSTEM_SMS_SUCCESS_3 = 1269;
    public static final int STR_SYSTEM_SMS_SUCCESS_4 = 1270;
    public static final int STR_SYSTEM_SMS_SUCCESS_5 = 1271;
    public static final int STR_SYSTEM_SMS_SUCCESS_6 = 1272;
    public static final int STR_SYSTEM_SMS_SUCCESS_7 = 1273;
    public static final int STR_SYSTEM_SMS_MONEY_SAVE_TIP = 1274;
    public static final int STR_SYSTEM_SMS_RELIFE = 1275;
    public static final int STR_SYSTEM_SCRIPT_SCORE = 1276;
    public static final int STR_SYSTEM_GET_SCORE = 1277;
    public static final int STR_SYSTEM_QQ_SCORE_TITLE = 1278;
    public static final int STR_SYSTEM_QQ_SCORE_CONTENT = 1279;
    public static final int STR_SYSTEM_QQ_SCORE_ZERO = 1280;
    public static final int STR_SYSTEM_TITLE_MESSAGE_0 = 1281;
    public static final int STR_SYSTEM_TITLE_MESSAGE_1 = 1282;
    public static final int STR_SYSTEM_TITLE_MESSAGE_2 = 1283;
    public static final int STR_SYSTEM_TITLE_MESSAGE_3 = 1284;
    public static final int STR_SYSTEM_TITLE_MESSAGE_4 = 1285;
    public static final int STR_SYSTEM_TITLE_MESSAGE_5 = 1286;
    public static final int STR_SYSTEM_TITLE_MESSAGE_6 = 1287;
    public static final int STR_SYSTEM_TITLE_MESSAGE_7 = 1288;
    public static final int STR_SYSTEM_TITLE_MESSAGE_8 = 1289;
    public static final int STR_SYSTEM_TITLE_MESSAGE_9 = 1290;
    public static final int STR_SYSTEM_MIXTRUE_BUTTON = 1291;
    public static final int STR_SYSTEM_MIXTRUE_NEED = 1292;
    public static final int STR_SYSTEM_MIXTRUE_SUCCESS = 1293;
    public static final int STR_SYSTEM_MIXTRUE_FAIL = 1294;
    public static final int STR_SYSTEM_MIXTRUE_GET = 1295;
    public static final int STR_SYSTEM_MIXTRUE_LACK = 1296;
    public static final int STR_SYSTEM_LESS_PROFIC = 1297;
    public static final int STR_SYSTEM_LESS_TOKEN = 1298;
    public static final int STR_SYSTEM_EQUIP_COMPARE = 1299;
    public static final int STR_SYSTEM_ITEM_SUITABLE = 1300;
    public static final int STR_SYSTEM_NORMAL_ATTACK = 1301;
    public static final int STR_SYSTEM_OPTION_HELP = 1302;
    public static final int STR_SYSTEM_PRESS_KEY_0 = 1303;
    public static final int STR_SYSTEM_HERO_0_STATUS_DESC = 1304;
    public static final int STR_SYSTEM_HERO_1_STATUS_DESC = 1305;
    public static final int STR_SYSTEM_HERO_2_STATUS_DESC = 1306;
    public static final int STR_SYSTEM_LESSTOKEN = 1307;
    public static final int STR_SYSTEM_RETRY = 1308;

    public static final int STR_SCENE_PALACE_SCRIPT0 = 1309;
    public static final int STR_SCENE_PALACE_SCRIPT1 = 1310;
    public static final int STR_SCENE_PALACE_SCRIPT2 = 1311;
    public static final int STR_SCENE_PALACE_SCRIPT3 = 1312;
    public static final int STR_SCENE_PALACE_SCRIPT4 = 1313;
    public static final int STR_SCENE_PALACE_SCRIPT5 = 1314;
    public static final int STR_SCENE_PALACE_SCRIPT6 = 1315;
    public static final int STR_SCENE_PALACE_SCRIPT7 = 1316;
    public static final int STR_SCENE_PALACE_SCRIPT8 = 1317;
    public static final int STR_SCENE_PALACE_SCRIPT9 = 1318;
    public static final int STR_SCENE_PALACE_SCRIPT10 = 1319;
    public static final int STR_SCENE_PALACE_SCRIPT11 = 1320;
    public static final int STR_SCENE_PALACE_SCRIPT12 = 1321;
    public static final int STR_SCENE_PALACE_SCRIPT13 = 1322;
    public static final int STR_SCENE_PALACE_SCRIPT14 = 1323;
    public static final int STR_SCENE_PALACE_SCRIPT15 = 1324;
    public static final int STR_SCENE_PALACE_SCRIPT16 = 1325;
    public static final int STR_SCENE_PALACE_SCRIPT17 = 1326;
    public static final int STR_SCENE_PALACE_SCRIPT18 = 1327;
    public static final int STR_SCENE_PALACE_SCRIPT19 = 1328;
    public static final int STR_SCENE_PALACE_SCRIPT20 = 1329;
    public static final int STR_SCENE_PALACE_SCRIPT21 = 1330;
    public static final int STR_SCENE_PALACE_SCRIPT22 = 1331;
    public static final int STR_SCENE_PALACE_SCRIPT23 = 1332;
    public static final int STR_SCENE_PALACE_SCRIPT24 = 1333;
    public static final int STR_SCENE_PALACE_SCRIPT25 = 1334;
    public static final int STR_SCENE_PALACE_SCRIPT26 = 1335;
    public static final int STR_SCENE_PALACE_SCRIPT27 = 1336;
    public static final int STR_SCENE_PALACE_SCRIPT28 = 1337;
    public static final int STR_SCENE_PALACE_SCRIPT29 = 1338;
    public static final int STR_SCENE_PALACE_SCRIPT30 = 1339;
    public static final int STR_SCENE_PALACE_SCRIPT31 = 1340;
    public static final int STR_SCENE_PALACE_SCRIPT32 = 1341;
    public static final int STR_SCENE_PALACE_SCRIPT33 = 1342;
    public static final int STR_SCENE_PALACE_SCRIPT34 = 1343;
    public static final int STR_SCENE_PALACE_SCRIPT35 = 1344;
    public static final int STR_SCENE_PALACE_SCRIPT36 = 1345;
    public static final int STR_SCENE_PALACE_SCRIPT37 = 1346;
    public static final int STR_SCENE_PALACE_SCRIPT38 = 1347;
    public static final int STR_SCENE_PALACE_SCRIPT39 = 1348;
    public static final int STR_SCENE_PALACE_SCRIPT40 = 1349;
    public static final int STR_SCENE_PALACE_SCRIPT41 = 1350;
    public static final int STR_SCENE_PALACE_SCRIPT42 = 1351;
    public static final int STR_SCENE_PALACE_SCRIPT43 = 1352;
    public static final int STR_SCENE_PALACE_SCRIPT44 = 1353;
    public static final int STR_SCENE_PALACE_SCRIPT45 = 1354;
    public static final int STR_SCENE_PALACE_SCRIPT46 = 1355;
    public static final int STR_SCENE_PALACE_SCRIPT47 = 1356;
    public static final int STR_SCENE_PALACE_SCRIPT48 = 1357;
    public static final int STR_SCENE_PALACE_SCRIPT49 = 1358;
    public static final int STR_SCENE_PALACE_SCRIPT50 = 1359;
    public static final int STR_SCENE_PALACE_SCRIPT51 = 1360;
    public static final int STR_SCENE_PALACE_SCRIPT52 = 1361;
    public static final int STR_SCENE_PALACE_SCRIPT53 = 1362;
    public static final int STR_SCENE_PALACE_SCRIPT54 = 1363;
    public static final int STR_SCENE_PALACE_SCRIPT55 = 1364;
    public static final int STR_SCENE_PALACE_SCRIPT56 = 1365;
    public static final int STR_SCENE_PALACE_SCRIPT57 = 1366;
    public static final int STR_SCENE_PALACE_SCRIPT58 = 1367;
    public static final int STR_SCENE_PALACE_SCRIPT59 = 1368;
    public static final int STR_SCENE_PALACE_SCRIPT60 = 1369;
    public static final int STR_SCENE_PALACE_SCRIPT61 = 1370;
    public static final int STR_SCENE_PALACE_SCRIPT62 = 1371;
    public static final int STR_SCENE_PALACE_SCRIPT63 = 1372;
    public static final int STR_SCENE_PALACE_SCRIPT64 = 1373;
    public static final int STR_SCENE_PALACE_SCRIPT65 = 1374;
    public static final int STR_SCENE_PALACE_SCRIPT66 = 1375;
    public static final int STR_SCENE_PALACE_SCRIPT67 = 1376;
    public static final int STR_SCENE_PALACE_SCRIPT68 = 1377;
    public static final int STR_SCENE_PALACE_SCRIPT69 = 1378;
    public static final int STR_SCENE_PALACE_SCRIPT70 = 1379;
    public static final int STR_SCENE_PALACE_SCRIPT71 = 1380;

    public static final int STR_SCENE_SONGJI_SCRIPT0 = 1309;
    public static final int STR_SCENE_SONGJI_SCRIPT1 = 1310;
    public static final int STR_SCENE_SONGJI_SCRIPT2 = 1311;
    public static final int STR_SCENE_SONGJI_SCRIPT3 = 1312;
    public static final int STR_SCENE_SONGJI_SCRIPT4 = 1313;
    public static final int STR_SCENE_SONGJI_SCRIPT5 = 1314;
    public static final int STR_SCENE_SONGJI_SCRIPT6 = 1315;
    public static final int STR_SCENE_SONGJI_SCRIPT7 = 1316;
    public static final int STR_SCENE_SONGJI_SCRIPT8 = 1317;
    public static final int STR_SCENE_SONGJI_SCRIPT9 = 1318;
    public static final int STR_SCENE_SONGJI_SCRIPT10 = 1319;
    public static final int STR_SCENE_SONGJI_SCRIPT11 = 1320;
    public static final int STR_SCENE_SONGJI_SCRIPT12 = 1321;
    public static final int STR_SCENE_SONGJI_SCRIPT13 = 1322;
    public static final int STR_SCENE_SONGJI_SCRIPT14 = 1323;
    public static final int STR_SCENE_SONGJI_SCRIPT15 = 1324;
    public static final int STR_SCENE_SONGJI_SCRIPT16 = 1325;
    public static final int STR_SCENE_SONGJI_SCRIPT17 = 1326;
    public static final int STR_SCENE_SONGJI_SCRIPT18 = 1327;
    public static final int STR_SCENE_SONGJI_SCRIPT19 = 1328;
    public static final int STR_SCENE_SONGJI_SCRIPT20 = 1329;
    public static final int STR_SCENE_SONGJI_SCRIPT21 = 1330;
    public static final int STR_SCENE_SONGJI_SCRIPT22 = 1331;
    public static final int STR_SCENE_SONGJI_SCRIPT23 = 1332;
    public static final int STR_SCENE_SONGJI_SCRIPT24 = 1333;
    public static final int STR_SCENE_SONGJI_SCRIPT25 = 1334;
    public static final int STR_SCENE_SONGJI_SCRIPT26 = 1335;
    public static final int STR_SCENE_SONGJI_SCRIPT27 = 1336;
    public static final int STR_SCENE_SONGJI_SCRIPT28 = 1337;
    public static final int STR_SCENE_SONGJI_SCRIPT29 = 1338;
    public static final int STR_SCENE_SONGJI_SCRIPT30 = 1339;
    public static final int STR_SCENE_SONGJI_SCRIPT31 = 1340;
    public static final int STR_SCENE_SONGJI_SCRIPT32 = 1341;
    public static final int STR_SCENE_SONGJI_SCRIPT33 = 1342;
    public static final int STR_SCENE_SONGJI_SCRIPT34 = 1343;
    public static final int STR_SCENE_SONGJI_SCRIPT35 = 1344;
    public static final int STR_SCENE_SONGJI_SCRIPT36 = 1345;
    public static final int STR_SCENE_SONGJI_SCRIPT37 = 1346;
    public static final int STR_SCENE_SONGJI_SCRIPT38 = 1347;
    public static final int STR_SCENE_SONGJI_SCRIPT39 = 1348;
    public static final int STR_SCENE_SONGJI_SCRIPT40 = 1349;
    public static final int STR_SCENE_SONGJI_SCRIPT41 = 1350;
    public static final int STR_SCENE_SONGJI_SCRIPT42 = 1351;
    public static final int STR_SCENE_SONGJI_SCRIPT43 = 1352;
    public static final int STR_SCENE_SONGJI_SCRIPT44 = 1353;
    public static final int STR_SCENE_SONGJI_SCRIPT45 = 1354;
    public static final int STR_SCENE_SONGJI_SCRIPT46 = 1355;
    public static final int STR_SCENE_SONGJI_SCRIPT47 = 1356;
    public static final int STR_SCENE_SONGJI_SCRIPT48 = 1357;
    public static final int STR_SCENE_SONGJI_SCRIPT49 = 1358;
    public static final int STR_SCENE_SONGJI_SCRIPT50 = 1359;
    public static final int STR_SCENE_SONGJI_SCRIPT51 = 1360;
    public static final int STR_SCENE_SONGJI_SCRIPT52 = 1361;
    public static final int STR_SCENE_SONGJI_SCRIPT53 = 1362;
    public static final int STR_SCENE_SONGJI_SCRIPT54 = 1363;
    public static final int STR_SCENE_SONGJI_SCRIPT55 = 1364;
    public static final int STR_SCENE_SONGJI_SCRIPT56 = 1365;
    public static final int STR_SCENE_SONGJI_SCRIPT57 = 1366;
    public static final int STR_SCENE_SONGJI_SCRIPT58 = 1367;
    public static final int STR_SCENE_SONGJI_SCRIPT59 = 1368;
    public static final int STR_SCENE_SONGJI_SCRIPT60 = 1369;
    public static final int STR_SCENE_SONGJI_SCRIPT61 = 1370;
    public static final int STR_SCENE_SONGJI_SCRIPT62 = 1371;
    public static final int STR_SCENE_SONGJI_SCRIPT63 = 1372;
    public static final int STR_SCENE_SONGJI_SCRIPT64 = 1373;
    public static final int STR_SCENE_SONGJI_SCRIPT65 = 1374;
    public static final int STR_SCENE_SONGJI_SCRIPT66 = 1375;
    public static final int STR_SCENE_SONGJI_SCRIPT67 = 1376;
    public static final int STR_SCENE_SONGJI_SCRIPT68 = 1377;
    public static final int STR_SCENE_SONGJI_SCRIPT69 = 1378;
    public static final int STR_SCENE_SONGJI_SCRIPT70 = 1379;
    public static final int STR_SCENE_SONGJI_SCRIPT71 = 1380;
    public static final int STR_SCENE_SONGJI_SCRIPT72 = 1381;
    public static final int STR_SCENE_SONGJI_SCRIPT73 = 1382;
    public static final int STR_SCENE_SONGJI_SCRIPT74 = 1383;
    public static final int STR_SCENE_SONGJI_SCRIPT75 = 1384;
    public static final int STR_SCENE_SONGJI_SCRIPT76 = 1385;
    public static final int STR_SCENE_SONGJI_SCRIPT77 = 1386;
    public static final int STR_SCENE_SONGJI_SCRIPT78 = 1387;
    public static final int STR_SCENE_SONGJI_SCRIPT79 = 1388;

    public static final int STR_SCENE_SB_SCRIPT0 = 1309;
    public static final int STR_SCENE_SB_SCRIPT1 = 1310;
    public static final int STR_SCENE_SB_SCRIPT2 = 1311;
    public static final int STR_SCENE_SB_SCRIPT3 = 1312;
    public static final int STR_SCENE_SB_SCRIPT4 = 1313;
    public static final int STR_SCENE_SB_SCRIPT5 = 1314;
    public static final int STR_SCENE_SB_SCRIPT6 = 1315;
    public static final int STR_SCENE_SB_SCRIPT7 = 1316;
    public static final int STR_SCENE_SB_SCRIPT8 = 1317;
    public static final int STR_SCENE_SB_SCRIPT9 = 1318;

    public static final int STR_SCENE_BEDROOM_SCRIPT0 = 1309;
    public static final int STR_SCENE_BEDROOM_SCRIPT1 = 1310;
    public static final int STR_SCENE_BEDROOM_SCRIPT2 = 1311;
    public static final int STR_SCENE_BEDROOM_SCRIPT3 = 1312;
    public static final int STR_SCENE_BEDROOM_SCRIPT4 = 1313;
    public static final int STR_SCENE_BEDROOM_SCRIPT5 = 1314;
    public static final int STR_SCENE_BEDROOM_SCRIPT6 = 1315;
    public static final int STR_SCENE_BEDROOM_SCRIPT7 = 1316;
    public static final int STR_SCENE_BEDROOM_SCRIPT8 = 1317;
    public static final int STR_SCENE_BEDROOM_SCRIPT9 = 1318;
    public static final int STR_SCENE_BEDROOM_SCRIPT10 = 1319;
    public static final int STR_SCENE_BEDROOM_SCRIPT11 = 1320;
    public static final int STR_SCENE_BEDROOM_SCRIPT12 = 1321;
    public static final int STR_SCENE_BEDROOM_SCRIPT13 = 1322;
    public static final int STR_SCENE_BEDROOM_SCRIPT14 = 1323;
    public static final int STR_SCENE_BEDROOM_SCRIPT15 = 1324;
    public static final int STR_SCENE_BEDROOM_SCRIPT16 = 1325;
    public static final int STR_SCENE_BEDROOM_SCRIPT17 = 1326;
    public static final int STR_SCENE_BEDROOM_SCRIPT18 = 1327;
    public static final int STR_SCENE_BEDROOM_SCRIPT19 = 1328;
    public static final int STR_SCENE_BEDROOM_SCRIPT20 = 1329;
    public static final int STR_SCENE_BEDROOM_SCRIPT21 = 1330;
    public static final int STR_SCENE_BEDROOM_SCRIPT22 = 1331;
    public static final int STR_SCENE_BEDROOM_SCRIPT23 = 1332;
    public static final int STR_SCENE_BEDROOM_SCRIPT24 = 1333;
    public static final int STR_SCENE_BEDROOM_SCRIPT25 = 1334;
    public static final int STR_SCENE_BEDROOM_SCRIPT26 = 1335;
    public static final int STR_SCENE_BEDROOM_SCRIPT27 = 1336;
    public static final int STR_SCENE_BEDROOM_SCRIPT28 = 1337;
    public static final int STR_SCENE_BEDROOM_SCRIPT29 = 1338;
    public static final int STR_SCENE_BEDROOM_SCRIPT30 = 1339;
    public static final int STR_SCENE_BEDROOM_SCRIPT31 = 1340;
    public static final int STR_SCENE_BEDROOM_SCRIPT32 = 1341;
    public static final int STR_SCENE_BEDROOM_SCRIPT33 = 1342;
    public static final int STR_SCENE_BEDROOM_SCRIPT34 = 1343;
    public static final int STR_SCENE_BEDROOM_SCRIPT35 = 1344;
    public static final int STR_SCENE_BEDROOM_SCRIPT36 = 1345;
    public static final int STR_SCENE_BEDROOM_SCRIPT37 = 1346;
    public static final int STR_SCENE_BEDROOM_SCRIPT38 = 1347;
    public static final int STR_SCENE_BEDROOM_SCRIPT39 = 1348;
    public static final int STR_SCENE_BEDROOM_SCRIPT40 = 1349;
    public static final int STR_SCENE_BEDROOM_SCRIPT41 = 1350;
    public static final int STR_SCENE_BEDROOM_SCRIPT42 = 1351;
    public static final int STR_SCENE_BEDROOM_SCRIPT43 = 1352;
    public static final int STR_SCENE_BEDROOM_SCRIPT44 = 1353;
    public static final int STR_SCENE_BEDROOM_SCRIPT45 = 1354;
    public static final int STR_SCENE_BEDROOM_SCRIPT46 = 1355;
    public static final int STR_SCENE_BEDROOM_SCRIPT47 = 1356;
    public static final int STR_SCENE_BEDROOM_SCRIPT48 = 1357;
    public static final int STR_SCENE_BEDROOM_SCRIPT49 = 1358;
    public static final int STR_SCENE_BEDROOM_SCRIPT50 = 1359;
    public static final int STR_SCENE_BEDROOM_SCRIPT51 = 1360;
    public static final int STR_SCENE_BEDROOM_SCRIPT52 = 1361;
    public static final int STR_SCENE_BEDROOM_SCRIPT53 = 1362;
    public static final int STR_SCENE_BEDROOM_SCRIPT54 = 1363;
    public static final int STR_SCENE_BEDROOM_SCRIPT55 = 1364;
    public static final int STR_SCENE_BEDROOM_SCRIPT56 = 1365;
    public static final int STR_SCENE_BEDROOM_SCRIPT57 = 1366;
    public static final int STR_SCENE_BEDROOM_SCRIPT58 = 1367;
    public static final int STR_SCENE_BEDROOM_SCRIPT59 = 1368;
    public static final int STR_SCENE_BEDROOM_SCRIPT60 = 1369;
    public static final int STR_SCENE_BEDROOM_SCRIPT61 = 1370;
    public static final int STR_SCENE_BEDROOM_SCRIPT62 = 1371;
    public static final int STR_SCENE_BEDROOM_SCRIPT63 = 1372;
    public static final int STR_SCENE_BEDROOM_SCRIPT64 = 1373;
    public static final int STR_SCENE_BEDROOM_SCRIPT65 = 1374;
    public static final int STR_SCENE_BEDROOM_SCRIPT66 = 1375;
    public static final int STR_SCENE_BEDROOM_SCRIPT67 = 1376;
    public static final int STR_SCENE_BEDROOM_SCRIPT68 = 1377;
    public static final int STR_SCENE_BEDROOM_SCRIPT69 = 1378;
    public static final int STR_SCENE_BEDROOM_SCRIPT70 = 1379;
    public static final int STR_SCENE_BEDROOM_SCRIPT71 = 1380;
    public static final int STR_SCENE_BEDROOM_SCRIPT72 = 1381;
    public static final int STR_SCENE_BEDROOM_SCRIPT73 = 1382;
    public static final int STR_SCENE_BEDROOM_SCRIPT74 = 1383;
    public static final int STR_SCENE_BEDROOM_SCRIPT75 = 1384;
    public static final int STR_SCENE_BEDROOM_SCRIPT76 = 1385;
    public static final int STR_SCENE_BEDROOM_SCRIPT77 = 1386;
    public static final int STR_SCENE_BEDROOM_SCRIPT78 = 1387;
    public static final int STR_SCENE_BEDROOM_SCRIPT79 = 1388;
    public static final int STR_SCENE_BEDROOM_SCRIPT80 = 1389;
    public static final int STR_SCENE_BEDROOM_SCRIPT81 = 1390;
    public static final int STR_SCENE_BEDROOM_SCRIPT82 = 1391;
    public static final int STR_SCENE_BEDROOM_SCRIPT83 = 1392;
    public static final int STR_SCENE_BEDROOM_SCRIPT84 = 1393;
    public static final int STR_SCENE_BEDROOM_SCRIPT85 = 1394;
    public static final int STR_SCENE_BEDROOM_SCRIPT86 = 1395;
    public static final int STR_SCENE_BEDROOM_SCRIPT87 = 1396;
    public static final int STR_SCENE_BEDROOM_SCRIPT88 = 1397;
    public static final int STR_SCENE_BEDROOM_SCRIPT89 = 1398;
    public static final int STR_SCENE_BEDROOM_SCRIPT90 = 1399;
    public static final int STR_SCENE_BEDROOM_SCRIPT91 = 1400;
    public static final int STR_SCENE_BEDROOM_SCRIPT92 = 1401;
    public static final int STR_SCENE_BEDROOM_SCRIPT93 = 1402;
    public static final int STR_SCENE_BEDROOM_SCRIPT94 = 1403;
    public static final int STR_SCENE_BEDROOM_SCRIPT95 = 1404;
    public static final int STR_SCENE_BEDROOM_SCRIPT96 = 1405;
    public static final int STR_SCENE_BEDROOM_SCRIPT97 = 1406;
    public static final int STR_SCENE_BEDROOM_SCRIPT98 = 1407;
    public static final int STR_SCENE_BEDROOM_SCRIPT99 = 1408;
    public static final int STR_SCENE_BEDROOM_SCRIPT100 = 1409;
    public static final int STR_SCENE_BEDROOM_SCRIPT101 = 1410;

    public static final int STR_SCENE_POORCITY_SCRIPT0 = 1309;
    public static final int STR_SCENE_POORCITY_SCRIPT1 = 1310;
    public static final int STR_SCENE_POORCITY_SCRIPT2 = 1311;
    public static final int STR_SCENE_POORCITY_SCRIPT3 = 1312;
    public static final int STR_SCENE_POORCITY_SCRIPT4 = 1313;
    public static final int STR_SCENE_POORCITY_SCRIPT5 = 1314;
    public static final int STR_SCENE_POORCITY_SCRIPT6 = 1315;
    public static final int STR_SCENE_POORCITY_SCRIPT7 = 1316;
    public static final int STR_SCENE_POORCITY_SCRIPT8 = 1317;
    public static final int STR_SCENE_POORCITY_SCRIPT9 = 1318;
    public static final int STR_SCENE_POORCITY_SCRIPT10 = 1319;
    public static final int STR_SCENE_POORCITY_SCRIPT11 = 1320;
    public static final int STR_SCENE_POORCITY_SCRIPT12 = 1321;
    public static final int STR_SCENE_POORCITY_SCRIPT13 = 1322;
    public static final int STR_SCENE_POORCITY_SCRIPT14 = 1323;
    public static final int STR_SCENE_POORCITY_SCRIPT15 = 1324;
    public static final int STR_SCENE_POORCITY_SCRIPT16 = 1325;
    public static final int STR_SCENE_POORCITY_SCRIPT17 = 1326;
    public static final int STR_SCENE_POORCITY_SCRIPT18 = 1327;
    public static final int STR_SCENE_POORCITY_SCRIPT19 = 1328;
    public static final int STR_SCENE_POORCITY_SCRIPT20 = 1329;
    public static final int STR_SCENE_POORCITY_SCRIPT21 = 1330;
    public static final int STR_SCENE_POORCITY_SCRIPT22 = 1331;
    public static final int STR_SCENE_POORCITY_SCRIPT23 = 1332;
    public static final int STR_SCENE_POORCITY_SCRIPT24 = 1333;
    public static final int STR_SCENE_POORCITY_SCRIPT25 = 1334;
    public static final int STR_SCENE_POORCITY_SCRIPT26 = 1335;
    public static final int STR_SCENE_POORCITY_SCRIPT27 = 1336;
    public static final int STR_SCENE_POORCITY_SCRIPT28 = 1337;
    public static final int STR_SCENE_POORCITY_SCRIPT29 = 1338;
    public static final int STR_SCENE_POORCITY_SCRIPT30 = 1339;
    public static final int STR_SCENE_POORCITY_SCRIPT31 = 1340;
    public static final int STR_SCENE_POORCITY_SCRIPT32 = 1341;
    public static final int STR_SCENE_POORCITY_SCRIPT33 = 1342;
    public static final int STR_SCENE_POORCITY_SCRIPT34 = 1343;
    public static final int STR_SCENE_POORCITY_SCRIPT35 = 1344;
    public static final int STR_SCENE_POORCITY_SCRIPT36 = 1345;
    public static final int STR_SCENE_POORCITY_SCRIPT37 = 1346;
    public static final int STR_SCENE_POORCITY_SCRIPT38 = 1347;
    public static final int STR_SCENE_POORCITY_SCRIPT39 = 1348;
    public static final int STR_SCENE_POORCITY_SCRIPT40 = 1349;

    public static final int STR_SCENE_INN_SCRIPT0 = 1309;
    public static final int STR_SCENE_INN_SCRIPT1 = 1310;
    public static final int STR_SCENE_INN_SCRIPT2 = 1311;
    public static final int STR_SCENE_INN_SCRIPT3 = 1312;
    public static final int STR_SCENE_INN_SCRIPT4 = 1313;
    public static final int STR_SCENE_INN_SCRIPT5 = 1314;
    public static final int STR_SCENE_INN_SCRIPT6 = 1315;
    public static final int STR_SCENE_INN_SCRIPT7 = 1316;
    public static final int STR_SCENE_INN_SCRIPT8 = 1317;
    public static final int STR_SCENE_INN_SCRIPT9 = 1318;
    public static final int STR_SCENE_INN_SCRIPT10 = 1319;
    public static final int STR_SCENE_INN_SCRIPT11 = 1320;
    public static final int STR_SCENE_INN_SCRIPT12 = 1321;
    public static final int STR_SCENE_INN_SCRIPT13 = 1322;
    public static final int STR_SCENE_INN_SCRIPT14 = 1323;
    public static final int STR_SCENE_INN_SCRIPT15 = 1324;
    public static final int STR_SCENE_INN_SCRIPT16 = 1325;
    public static final int STR_SCENE_INN_SCRIPT17 = 1326;
    public static final int STR_SCENE_INN_SCRIPT18 = 1327;
    public static final int STR_SCENE_INN_SCRIPT19 = 1328;
    public static final int STR_SCENE_INN_SCRIPT20 = 1329;
    public static final int STR_SCENE_INN_SCRIPT21 = 1330;
    public static final int STR_SCENE_INN_SCRIPT22 = 1331;
    public static final int STR_SCENE_INN_SCRIPT23 = 1332;
    public static final int STR_SCENE_INN_SCRIPT24 = 1333;
    public static final int STR_SCENE_INN_SCRIPT25 = 1334;
    public static final int STR_SCENE_INN_SCRIPT26 = 1335;
    public static final int STR_SCENE_INN_SCRIPT27 = 1336;
    public static final int STR_SCENE_INN_SCRIPT28 = 1337;
    public static final int STR_SCENE_INN_SCRIPT29 = 1338;
    public static final int STR_SCENE_INN_SCRIPT30 = 1339;
    public static final int STR_SCENE_INN_SCRIPT31 = 1340;
    public static final int STR_SCENE_INN_SCRIPT32 = 1341;
    public static final int STR_SCENE_INN_SCRIPT33 = 1342;
    public static final int STR_SCENE_INN_SCRIPT34 = 1343;
    public static final int STR_SCENE_INN_SCRIPT35 = 1344;
    public static final int STR_SCENE_INN_SCRIPT36 = 1345;
    public static final int STR_SCENE_INN_SCRIPT37 = 1346;
    public static final int STR_SCENE_INN_SCRIPT38 = 1347;
    public static final int STR_SCENE_INN_SCRIPT39 = 1348;
    public static final int STR_SCENE_INN_SCRIPT40 = 1349;
    public static final int STR_SCENE_INN_SCRIPT41 = 1350;
    public static final int STR_SCENE_INN_SCRIPT42 = 1351;
    public static final int STR_SCENE_INN_SCRIPT43 = 1352;
    public static final int STR_SCENE_INN_SCRIPT44 = 1353;
    public static final int STR_SCENE_INN_SCRIPT45 = 1354;
    public static final int STR_SCENE_INN_SCRIPT46 = 1355;
    public static final int STR_SCENE_INN_SCRIPT47 = 1356;
    public static final int STR_SCENE_INN_SCRIPT48 = 1357;
    public static final int STR_SCENE_INN_SCRIPT49 = 1358;
    public static final int STR_SCENE_INN_SCRIPT50 = 1359;
    public static final int STR_SCENE_INN_SCRIPT51 = 1360;
    public static final int STR_SCENE_INN_SCRIPT52 = 1361;
    public static final int STR_SCENE_INN_SCRIPT53 = 1362;
    public static final int STR_SCENE_INN_SCRIPT54 = 1363;
    public static final int STR_SCENE_INN_SCRIPT55 = 1364;
    public static final int STR_SCENE_INN_SCRIPT56 = 1365;
    public static final int STR_SCENE_INN_SCRIPT57 = 1366;
    public static final int STR_SCENE_INN_SCRIPT58 = 1367;
    public static final int STR_SCENE_INN_SCRIPT59 = 1368;
    public static final int STR_SCENE_INN_SCRIPT60 = 1369;
    public static final int STR_SCENE_INN_SCRIPT61 = 1370;
    public static final int STR_SCENE_INN_SCRIPT62 = 1371;
    public static final int STR_SCENE_INN_SCRIPT63 = 1372;
    public static final int STR_SCENE_INN_SCRIPT64 = 1373;
    public static final int STR_SCENE_INN_SCRIPT65 = 1374;
    public static final int STR_SCENE_INN_SCRIPT66 = 1375;
    public static final int STR_SCENE_INN_SCRIPT67 = 1376;
    public static final int STR_SCENE_INN_SCRIPT68 = 1377;
    public static final int STR_SCENE_INN_SCRIPT69 = 1378;
    public static final int STR_SCENE_INN_SCRIPT70 = 1379;
    public static final int STR_SCENE_INN_SCRIPT71 = 1380;
    public static final int STR_SCENE_INN_SCRIPT72 = 1381;
    public static final int STR_SCENE_INN_SCRIPT73 = 1382;
    public static final int STR_SCENE_INN_SCRIPT74 = 1383;
    public static final int STR_SCENE_INN_SCRIPT75 = 1384;
    public static final int STR_SCENE_INN_SCRIPT76 = 1385;
    public static final int STR_SCENE_INN_SCRIPT77 = 1386;
    public static final int STR_SCENE_INN_SCRIPT78 = 1387;
    public static final int STR_SCENE_INN_SCRIPT79 = 1388;
    public static final int STR_SCENE_INN_SCRIPT80 = 1389;
    public static final int STR_SCENE_INN_SCRIPT81 = 1390;
    public static final int STR_SCENE_INN_SCRIPT82 = 1391;
    public static final int STR_SCENE_INN_SCRIPT83 = 1392;
    public static final int STR_SCENE_INN_SCRIPT84 = 1393;
    public static final int STR_SCENE_INN_SCRIPT85 = 1394;
    public static final int STR_SCENE_INN_SCRIPT86 = 1395;
    public static final int STR_SCENE_INN_SCRIPT87 = 1396;
    public static final int STR_SCENE_INN_SCRIPT88 = 1397;
    public static final int STR_SCENE_INN_SCRIPT89 = 1398;
    public static final int STR_SCENE_INN_SCRIPT90 = 1399;
    public static final int STR_SCENE_INN_SCRIPT91 = 1400;
    public static final int STR_SCENE_INN_SCRIPT92 = 1401;
    public static final int STR_SCENE_INN_SCRIPT93 = 1402;
    public static final int STR_SCENE_INN_SCRIPT94 = 1403;
    public static final int STR_SCENE_INN_SCRIPT95 = 1404;
    public static final int STR_SCENE_INN_SCRIPT96 = 1405;
    public static final int STR_SCENE_INN_SCRIPT97 = 1406;
    public static final int STR_SCENE_INN_SCRIPT98 = 1407;
    public static final int STR_SCENE_INN_SCRIPT99 = 1408;
    public static final int STR_SCENE_INN_SCRIPT100 = 1409;
    public static final int STR_SCENE_INN_SCRIPT101 = 1410;
    public static final int STR_SCENE_INN_SCRIPT102 = 1411;
    public static final int STR_SCENE_INN_SCRIPT103 = 1412;
    public static final int STR_SCENE_INN_SCRIPT104 = 1413;
    public static final int STR_SCENE_INN_SCRIPT105 = 1414;
    public static final int STR_SCENE_INN_SCRIPT106 = 1415;
    public static final int STR_SCENE_INN_SCRIPT107 = 1416;
    public static final int STR_SCENE_INN_SCRIPT108 = 1417;
    public static final int STR_SCENE_INN_SCRIPT109 = 1418;
    public static final int STR_SCENE_INN_SCRIPT110 = 1419;
    public static final int STR_SCENE_INN_SCRIPT111 = 1420;
    public static final int STR_SCENE_INN_SCRIPT112 = 1421;
    public static final int STR_SCENE_INN_SCRIPT113 = 1422;
    public static final int STR_SCENE_INN_SCRIPT114 = 1423;
    public static final int STR_SCENE_INN_SCRIPT115 = 1424;
    public static final int STR_SCENE_INN_SCRIPT116 = 1425;
    public static final int STR_SCENE_INN_SCRIPT117 = 1426;
    public static final int STR_SCENE_INN_SCRIPT118 = 1427;
    public static final int STR_SCENE_INN_SCRIPT119 = 1428;
    public static final int STR_SCENE_INN_SCRIPT120 = 1429;
    public static final int STR_SCENE_INN_SCRIPT121 = 1430;
    public static final int STR_SCENE_INN_SCRIPT122 = 1431;
    public static final int STR_SCENE_INN_SCRIPT123 = 1432;
    public static final int STR_SCENE_INN_SCRIPT124 = 1433;
    public static final int STR_SCENE_INN_SCRIPT125 = 1434;
    public static final int STR_SCENE_INN_SCRIPT126 = 1435;
    public static final int STR_SCENE_INN_SCRIPT127 = 1436;
    public static final int STR_SCENE_INN_SCRIPT128 = 1437;
    public static final int STR_SCENE_INN_SCRIPT129 = 1438;
    public static final int STR_SCENE_INN_SCRIPT130 = 1439;
    public static final int STR_SCENE_INN_SCRIPT131 = 1440;
    public static final int STR_SCENE_INN_SCRIPT132 = 1441;
    public static final int STR_SCENE_INN_SCRIPT133 = 1442;
    public static final int STR_SCENE_INN_SCRIPT134 = 1443;
    public static final int STR_SCENE_INN_SCRIPT135 = 1444;
    public static final int STR_SCENE_INN_SCRIPT136 = 1445;
    public static final int STR_SCENE_INN_SCRIPT137 = 1446;
    public static final int STR_SCENE_INN_SCRIPT138 = 1447;

    public static final int STR_SCENE_TOWER_SCRIPT0 = 1309;
    public static final int STR_SCENE_TOWER_SCRIPT1 = 1310;
    public static final int STR_SCENE_TOWER_SCRIPT2 = 1311;
    public static final int STR_SCENE_TOWER_SCRIPT3 = 1312;
    public static final int STR_SCENE_TOWER_SCRIPT4 = 1313;
    public static final int STR_SCENE_TOWER_SCRIPT5 = 1314;
    public static final int STR_SCENE_TOWER_SCRIPT6 = 1315;
    public static final int STR_SCENE_TOWER_SCRIPT7 = 1316;
    public static final int STR_SCENE_TOWER_SCRIPT8 = 1317;
    public static final int STR_SCENE_TOWER_SCRIPT9 = 1318;
    public static final int STR_SCENE_TOWER_SCRIPT10 = 1319;
    public static final int STR_SCENE_TOWER_SCRIPT11 = 1320;
    public static final int STR_SCENE_TOWER_SCRIPT12 = 1321;
    public static final int STR_SCENE_TOWER_SCRIPT13 = 1322;
    public static final int STR_SCENE_TOWER_SCRIPT14 = 1323;
    public static final int STR_SCENE_TOWER_SCRIPT15 = 1324;
    public static final int STR_SCENE_TOWER_SCRIPT16 = 1325;
    public static final int STR_SCENE_TOWER_SCRIPT17 = 1326;
    public static final int STR_SCENE_TOWER_SCRIPT18 = 1327;
    public static final int STR_SCENE_TOWER_SCRIPT19 = 1328;
    public static final int STR_SCENE_TOWER_SCRIPT20 = 1329;
    public static final int STR_SCENE_TOWER_SCRIPT21 = 1330;
    public static final int STR_SCENE_TOWER_SCRIPT22 = 1331;
    public static final int STR_SCENE_TOWER_SCRIPT23 = 1332;
    public static final int STR_SCENE_TOWER_SCRIPT24 = 1333;
    public static final int STR_SCENE_TOWER_SCRIPT25 = 1334;
    public static final int STR_SCENE_TOWER_SCRIPT26 = 1335;
    public static final int STR_SCENE_TOWER_SCRIPT27 = 1336;
    public static final int STR_SCENE_TOWER_SCRIPT28 = 1337;
    public static final int STR_SCENE_TOWER_SCRIPT29 = 1338;
    public static final int STR_SCENE_TOWER_SCRIPT30 = 1339;
    public static final int STR_SCENE_TOWER_SCRIPT31 = 1340;
    public static final int STR_SCENE_TOWER_SCRIPT32 = 1341;
    public static final int STR_SCENE_TOWER_SCRIPT33 = 1342;
    public static final int STR_SCENE_TOWER_SCRIPT34 = 1343;
    public static final int STR_SCENE_TOWER_SCRIPT35 = 1344;
    public static final int STR_SCENE_TOWER_SCRIPT36 = 1345;
    public static final int STR_SCENE_TOWER_SCRIPT37 = 1346;
    public static final int STR_SCENE_TOWER_SCRIPT38 = 1347;
    public static final int STR_SCENE_TOWER_SCRIPT39 = 1348;
    public static final int STR_SCENE_TOWER_SCRIPT40 = 1349;
    public static final int STR_SCENE_TOWER_SCRIPT41 = 1350;
    public static final int STR_SCENE_TOWER_SCRIPT42 = 1351;
    public static final int STR_SCENE_TOWER_SCRIPT43 = 1352;
    public static final int STR_SCENE_TOWER_SCRIPT44 = 1353;
    public static final int STR_SCENE_TOWER_SCRIPT45 = 1354;
    public static final int STR_SCENE_TOWER_SCRIPT46 = 1355;
    public static final int STR_SCENE_TOWER_SCRIPT47 = 1356;
    public static final int STR_SCENE_TOWER_SCRIPT48 = 1357;
    public static final int STR_SCENE_TOWER_SCRIPT49 = 1358;
    public static final int STR_SCENE_TOWER_SCRIPT50 = 1359;
    public static final int STR_SCENE_TOWER_SCRIPT51 = 1360;
    public static final int STR_SCENE_TOWER_SCRIPT52 = 1361;
    public static final int STR_SCENE_TOWER_SCRIPT53 = 1362;
    public static final int STR_SCENE_TOWER_SCRIPT54 = 1363;
    public static final int STR_SCENE_TOWER_SCRIPT55 = 1364;
    public static final int STR_SCENE_TOWER_SCRIPT56 = 1365;
    public static final int STR_SCENE_TOWER_SCRIPT57 = 1366;
    public static final int STR_SCENE_TOWER_SCRIPT58 = 1367;
    public static final int STR_SCENE_TOWER_SCRIPT59 = 1368;
    public static final int STR_SCENE_TOWER_SCRIPT60 = 1369;
    public static final int STR_SCENE_TOWER_SCRIPT61 = 1370;
    public static final int STR_SCENE_TOWER_SCRIPT62 = 1371;
    public static final int STR_SCENE_TOWER_SCRIPT63 = 1372;
    public static final int STR_SCENE_TOWER_SCRIPT64 = 1373;
    public static final int STR_SCENE_TOWER_SCRIPT65 = 1374;
    public static final int STR_SCENE_TOWER_SCRIPT66 = 1375;
    public static final int STR_SCENE_TOWER_SCRIPT67 = 1376;
    public static final int STR_SCENE_TOWER_SCRIPT68 = 1377;
    public static final int STR_SCENE_TOWER_SCRIPT69 = 1378;
    public static final int STR_SCENE_TOWER_SCRIPT70 = 1379;
    public static final int STR_SCENE_TOWER_SCRIPT71 = 1380;
    public static final int STR_SCENE_TOWER_SCRIPT72 = 1381;
    public static final int STR_SCENE_TOWER_SCRIPT73 = 1382;
    public static final int STR_SCENE_TOWER_SCRIPT74 = 1383;
    public static final int STR_SCENE_TOWER_SCRIPT75 = 1384;
    public static final int STR_SCENE_TOWER_SCRIPT76 = 1385;
    public static final int STR_SCENE_TOWER_SCRIPT77 = 1386;
    public static final int STR_SCENE_TOWER_SCRIPT78 = 1387;
    public static final int STR_SCENE_TOWER_SCRIPT79 = 1388;
    public static final int STR_SCENE_TOWER_SCRIPT80 = 1389;
    public static final int STR_SCENE_TOWER_SCRIPT81 = 1390;
    public static final int STR_SCENE_TOWER_SCRIPT82 = 1391;
    public static final int STR_SCENE_TOWER_SCRIPT83 = 1392;
    public static final int STR_SCENE_TOWER_SCRIPT84 = 1393;
    public static final int STR_SCENE_TOWER_SCRIPT85 = 1394;
    public static final int STR_SCENE_TOWER_SCRIPT86 = 1395;
    public static final int STR_SCENE_TOWER_SCRIPT87 = 1396;
    public static final int STR_SCENE_TOWER_SCRIPT88 = 1397;

    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT0 = 1309;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT1 = 1310;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT2 = 1311;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT3 = 1312;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT4 = 1313;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT5 = 1314;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT6 = 1315;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT7 = 1316;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT8 = 1317;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT9 = 1318;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT10 = 1319;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT11 = 1320;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT12 = 1321;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT13 = 1322;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT14 = 1323;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT15 = 1324;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT16 = 1325;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT17 = 1326;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT18 = 1327;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT19 = 1328;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT20 = 1329;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT21 = 1330;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT22 = 1331;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT23 = 1332;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT24 = 1333;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT25 = 1334;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT26 = 1335;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT27 = 1336;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT28 = 1337;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT29 = 1338;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT30 = 1339;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT31 = 1340;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT32 = 1341;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT33 = 1342;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT34 = 1343;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT35 = 1344;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT36 = 1345;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT37 = 1346;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT38 = 1347;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT39 = 1348;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT40 = 1349;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT41 = 1350;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT42 = 1351;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT43 = 1352;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT44 = 1353;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT45 = 1354;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT46 = 1355;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT47 = 1356;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT48 = 1357;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT49 = 1358;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT50 = 1359;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT51 = 1360;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT52 = 1361;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT53 = 1362;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT54 = 1363;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT55 = 1364;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT56 = 1365;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT57 = 1366;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT58 = 1367;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT59 = 1368;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT60 = 1369;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT61 = 1370;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT62 = 1371;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT63 = 1372;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT64 = 1373;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT65 = 1374;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT66 = 1375;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT67 = 1376;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT68 = 1377;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT69 = 1378;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT70 = 1379;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT71 = 1380;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT72 = 1381;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT73 = 1382;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT74 = 1383;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT75 = 1384;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT76 = 1385;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT77 = 1386;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT78 = 1387;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT79 = 1388;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT80 = 1389;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT81 = 1390;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT82 = 1391;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT83 = 1392;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT84 = 1393;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT85 = 1394;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT86 = 1395;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT87 = 1396;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT88 = 1397;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT89 = 1398;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT90 = 1399;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT91 = 1400;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT92 = 1401;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT93 = 1402;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT94 = 1403;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT95 = 1404;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT96 = 1405;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT97 = 1406;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT98 = 1407;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT99 = 1408;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT100 = 1409;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT101 = 1410;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT102 = 1411;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT103 = 1412;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT104 = 1413;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT105 = 1414;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT106 = 1415;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT107 = 1416;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT108 = 1417;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT109 = 1418;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT110 = 1419;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT111 = 1420;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT112 = 1421;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT113 = 1422;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT114 = 1423;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT115 = 1424;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT116 = 1425;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT117 = 1426;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT118 = 1427;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT119 = 1428;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT120 = 1429;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT121 = 1430;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT122 = 1431;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT123 = 1432;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT124 = 1433;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT125 = 1434;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT126 = 1435;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT127 = 1436;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT128 = 1437;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT129 = 1438;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT130 = 1439;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT131 = 1440;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT132 = 1441;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT133 = 1442;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT134 = 1443;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT135 = 1444;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT136 = 1445;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT137 = 1446;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT138 = 1447;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT139 = 1448;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT140 = 1449;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT141 = 1450;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT142 = 1451;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT143 = 1452;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT144 = 1453;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT145 = 1454;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT146 = 1455;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT147 = 1456;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT148 = 1457;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT149 = 1458;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT150 = 1459;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT151 = 1460;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT152 = 1461;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT153 = 1462;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT154 = 1463;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT155 = 1464;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT156 = 1465;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT157 = 1466;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT158 = 1467;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT159 = 1468;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT160 = 1469;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT161 = 1470;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT162 = 1471;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT163 = 1472;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT164 = 1473;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT165 = 1474;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT166 = 1475;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT167 = 1476;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT168 = 1477;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT169 = 1478;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT170 = 1479;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT171 = 1480;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT172 = 1481;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT173 = 1482;
    public static final int STR_SCENE_HOME_WESTYARD_SCRIPT174 = 1483;

    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT0 = 1309;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT1 = 1310;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT2 = 1311;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT3 = 1312;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT4 = 1313;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT5 = 1314;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT6 = 1315;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT7 = 1316;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT8 = 1317;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT9 = 1318;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT10 = 1319;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT11 = 1320;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT12 = 1321;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT13 = 1322;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT14 = 1323;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT15 = 1324;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT16 = 1325;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT17 = 1326;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT18 = 1327;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT19 = 1328;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT20 = 1329;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT21 = 1330;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT22 = 1331;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT23 = 1332;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT24 = 1333;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT25 = 1334;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT26 = 1335;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT27 = 1336;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT28 = 1337;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT29 = 1338;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT30 = 1339;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT31 = 1340;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT32 = 1341;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT33 = 1342;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT34 = 1343;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT35 = 1344;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT36 = 1345;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT37 = 1346;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT38 = 1347;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT39 = 1348;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT40 = 1349;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT41 = 1350;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT42 = 1351;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT43 = 1352;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT44 = 1353;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT45 = 1354;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT46 = 1355;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT47 = 1356;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT48 = 1357;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT49 = 1358;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT50 = 1359;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT51 = 1360;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT52 = 1361;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT53 = 1362;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT54 = 1363;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT55 = 1364;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT56 = 1365;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT57 = 1366;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT58 = 1367;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT59 = 1368;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT60 = 1369;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT61 = 1370;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT62 = 1371;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT63 = 1372;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT64 = 1373;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT65 = 1374;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT66 = 1375;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT67 = 1376;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT68 = 1377;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT69 = 1378;
    public static final int STR_SCENE_HOME_MAINYARD_SCRIPT70 = 1379;

    public static final int STR_SCENE_GATEWAY_SCRIPT0 = 1309;
    public static final int STR_SCENE_GATEWAY_SCRIPT1 = 1310;
    public static final int STR_SCENE_GATEWAY_SCRIPT2 = 1311;
    public static final int STR_SCENE_GATEWAY_SCRIPT3 = 1312;
    public static final int STR_SCENE_GATEWAY_SCRIPT4 = 1313;
    public static final int STR_SCENE_GATEWAY_SCRIPT5 = 1314;
    public static final int STR_SCENE_GATEWAY_SCRIPT6 = 1315;
    public static final int STR_SCENE_GATEWAY_SCRIPT7 = 1316;
    public static final int STR_SCENE_GATEWAY_SCRIPT8 = 1317;
    public static final int STR_SCENE_GATEWAY_SCRIPT9 = 1318;
    public static final int STR_SCENE_GATEWAY_SCRIPT10 = 1319;
    public static final int STR_SCENE_GATEWAY_SCRIPT11 = 1320;
    public static final int STR_SCENE_GATEWAY_SCRIPT12 = 1321;
    public static final int STR_SCENE_GATEWAY_SCRIPT13 = 1322;
    public static final int STR_SCENE_GATEWAY_SCRIPT14 = 1323;
    public static final int STR_SCENE_GATEWAY_SCRIPT15 = 1324;
    public static final int STR_SCENE_GATEWAY_SCRIPT16 = 1325;
    public static final int STR_SCENE_GATEWAY_SCRIPT17 = 1326;
    public static final int STR_SCENE_GATEWAY_SCRIPT18 = 1327;
    public static final int STR_SCENE_GATEWAY_SCRIPT19 = 1328;
    public static final int STR_SCENE_GATEWAY_SCRIPT20 = 1329;
    public static final int STR_SCENE_GATEWAY_SCRIPT21 = 1330;
    public static final int STR_SCENE_GATEWAY_SCRIPT22 = 1331;
    public static final int STR_SCENE_GATEWAY_SCRIPT23 = 1332;
    public static final int STR_SCENE_GATEWAY_SCRIPT24 = 1333;
    public static final int STR_SCENE_GATEWAY_SCRIPT25 = 1334;
    public static final int STR_SCENE_GATEWAY_SCRIPT26 = 1335;
    public static final int STR_SCENE_GATEWAY_SCRIPT27 = 1336;
    public static final int STR_SCENE_GATEWAY_SCRIPT28 = 1337;
    public static final int STR_SCENE_GATEWAY_SCRIPT29 = 1338;
    public static final int STR_SCENE_GATEWAY_SCRIPT30 = 1339;
    public static final int STR_SCENE_GATEWAY_SCRIPT31 = 1340;
    public static final int STR_SCENE_GATEWAY_SCRIPT32 = 1341;
    public static final int STR_SCENE_GATEWAY_SCRIPT33 = 1342;
    public static final int STR_SCENE_GATEWAY_SCRIPT34 = 1343;
    public static final int STR_SCENE_GATEWAY_SCRIPT35 = 1344;
    public static final int STR_SCENE_GATEWAY_SCRIPT36 = 1345;
    public static final int STR_SCENE_GATEWAY_SCRIPT37 = 1346;
    public static final int STR_SCENE_GATEWAY_SCRIPT38 = 1347;
    public static final int STR_SCENE_GATEWAY_SCRIPT39 = 1348;
    public static final int STR_SCENE_GATEWAY_SCRIPT40 = 1349;
    public static final int STR_SCENE_GATEWAY_SCRIPT41 = 1350;
    public static final int STR_SCENE_GATEWAY_SCRIPT42 = 1351;
    public static final int STR_SCENE_GATEWAY_SCRIPT43 = 1352;
    public static final int STR_SCENE_GATEWAY_SCRIPT44 = 1353;
    public static final int STR_SCENE_GATEWAY_SCRIPT45 = 1354;
    public static final int STR_SCENE_GATEWAY_SCRIPT46 = 1355;
    public static final int STR_SCENE_GATEWAY_SCRIPT47 = 1356;
    public static final int STR_SCENE_GATEWAY_SCRIPT48 = 1357;
    public static final int STR_SCENE_GATEWAY_SCRIPT49 = 1358;
    public static final int STR_SCENE_GATEWAY_SCRIPT50 = 1359;
    public static final int STR_SCENE_GATEWAY_SCRIPT51 = 1360;
    public static final int STR_SCENE_GATEWAY_SCRIPT52 = 1361;
    public static final int STR_SCENE_GATEWAY_SCRIPT53 = 1362;
    public static final int STR_SCENE_GATEWAY_SCRIPT54 = 1363;
    public static final int STR_SCENE_GATEWAY_SCRIPT55 = 1364;
    public static final int STR_SCENE_GATEWAY_SCRIPT56 = 1365;
    public static final int STR_SCENE_GATEWAY_SCRIPT57 = 1366;
    public static final int STR_SCENE_GATEWAY_SCRIPT58 = 1367;
    public static final int STR_SCENE_GATEWAY_SCRIPT59 = 1368;
    public static final int STR_SCENE_GATEWAY_SCRIPT60 = 1369;
    public static final int STR_SCENE_GATEWAY_SCRIPT61 = 1370;
    public static final int STR_SCENE_GATEWAY_SCRIPT62 = 1371;
    public static final int STR_SCENE_GATEWAY_SCRIPT63 = 1372;
    public static final int STR_SCENE_GATEWAY_SCRIPT64 = 1373;
    public static final int STR_SCENE_GATEWAY_SCRIPT65 = 1374;
    public static final int STR_SCENE_GATEWAY_SCRIPT66 = 1375;
    public static final int STR_SCENE_GATEWAY_SCRIPT67 = 1376;
    public static final int STR_SCENE_GATEWAY_SCRIPT68 = 1377;
    public static final int STR_SCENE_GATEWAY_SCRIPT69 = 1378;
    public static final int STR_SCENE_GATEWAY_SCRIPT70 = 1379;
    public static final int STR_SCENE_GATEWAY_SCRIPT71 = 1380;
    public static final int STR_SCENE_GATEWAY_SCRIPT72 = 1381;
    public static final int STR_SCENE_GATEWAY_SCRIPT73 = 1382;
    public static final int STR_SCENE_GATEWAY_SCRIPT74 = 1383;
    public static final int STR_SCENE_GATEWAY_SCRIPT75 = 1384;
    public static final int STR_SCENE_GATEWAY_SCRIPT76 = 1385;
    public static final int STR_SCENE_GATEWAY_SCRIPT77 = 1386;
    public static final int STR_SCENE_GATEWAY_SCRIPT78 = 1387;
    public static final int STR_SCENE_GATEWAY_SCRIPT79 = 1388;
    public static final int STR_SCENE_GATEWAY_SCRIPT80 = 1389;
    public static final int STR_SCENE_GATEWAY_SCRIPT81 = 1390;
    public static final int STR_SCENE_GATEWAY_SCRIPT82 = 1391;
    public static final int STR_SCENE_GATEWAY_SCRIPT83 = 1392;
    public static final int STR_SCENE_GATEWAY_SCRIPT84 = 1393;
    public static final int STR_SCENE_GATEWAY_SCRIPT85 = 1394;
    public static final int STR_SCENE_GATEWAY_SCRIPT86 = 1395;
    public static final int STR_SCENE_GATEWAY_SCRIPT87 = 1396;
    public static final int STR_SCENE_GATEWAY_SCRIPT88 = 1397;
    public static final int STR_SCENE_GATEWAY_SCRIPT89 = 1398;
    public static final int STR_SCENE_GATEWAY_SCRIPT90 = 1399;
    public static final int STR_SCENE_GATEWAY_SCRIPT91 = 1400;
    public static final int STR_SCENE_GATEWAY_SCRIPT92 = 1401;
    public static final int STR_SCENE_GATEWAY_SCRIPT93 = 1402;
    public static final int STR_SCENE_GATEWAY_SCRIPT94 = 1403;
    public static final int STR_SCENE_GATEWAY_SCRIPT95 = 1404;
    public static final int STR_SCENE_GATEWAY_SCRIPT96 = 1405;
    public static final int STR_SCENE_GATEWAY_SCRIPT97 = 1406;
    public static final int STR_SCENE_GATEWAY_SCRIPT98 = 1407;
    public static final int STR_SCENE_GATEWAY_SCRIPT99 = 1408;
    public static final int STR_SCENE_GATEWAY_SCRIPT100 = 1409;
    public static final int STR_SCENE_GATEWAY_SCRIPT101 = 1410;
    public static final int STR_SCENE_GATEWAY_SCRIPT102 = 1411;
    public static final int STR_SCENE_GATEWAY_SCRIPT103 = 1412;
    public static final int STR_SCENE_GATEWAY_SCRIPT104 = 1413;
    public static final int STR_SCENE_GATEWAY_SCRIPT105 = 1414;
    public static final int STR_SCENE_GATEWAY_SCRIPT106 = 1415;
    public static final int STR_SCENE_GATEWAY_SCRIPT107 = 1416;
    public static final int STR_SCENE_GATEWAY_SCRIPT108 = 1417;
    public static final int STR_SCENE_GATEWAY_SCRIPT109 = 1418;
    public static final int STR_SCENE_GATEWAY_SCRIPT110 = 1419;
    public static final int STR_SCENE_GATEWAY_SCRIPT111 = 1420;
    public static final int STR_SCENE_GATEWAY_SCRIPT112 = 1421;
    public static final int STR_SCENE_GATEWAY_SCRIPT113 = 1422;
    public static final int STR_SCENE_GATEWAY_SCRIPT114 = 1423;
    public static final int STR_SCENE_GATEWAY_SCRIPT115 = 1424;
    public static final int STR_SCENE_GATEWAY_SCRIPT116 = 1425;
    public static final int STR_SCENE_GATEWAY_SCRIPT117 = 1426;
    public static final int STR_SCENE_GATEWAY_SCRIPT118 = 1427;
    public static final int STR_SCENE_GATEWAY_SCRIPT119 = 1428;
    public static final int STR_SCENE_GATEWAY_SCRIPT120 = 1429;
    public static final int STR_SCENE_GATEWAY_SCRIPT121 = 1430;
    public static final int STR_SCENE_GATEWAY_SCRIPT122 = 1431;
    public static final int STR_SCENE_GATEWAY_SCRIPT123 = 1432;
    public static final int STR_SCENE_GATEWAY_SCRIPT124 = 1433;
    public static final int STR_SCENE_GATEWAY_SCRIPT125 = 1434;
    public static final int STR_SCENE_GATEWAY_SCRIPT126 = 1435;
    public static final int STR_SCENE_GATEWAY_SCRIPT127 = 1436;
    public static final int STR_SCENE_GATEWAY_SCRIPT128 = 1437;
    public static final int STR_SCENE_GATEWAY_SCRIPT129 = 1438;
    public static final int STR_SCENE_GATEWAY_SCRIPT130 = 1439;
    public static final int STR_SCENE_GATEWAY_SCRIPT131 = 1440;
    public static final int STR_SCENE_GATEWAY_SCRIPT132 = 1441;
    public static final int STR_SCENE_GATEWAY_SCRIPT133 = 1442;
    public static final int STR_SCENE_GATEWAY_SCRIPT134 = 1443;
    public static final int STR_SCENE_GATEWAY_SCRIPT135 = 1444;
    public static final int STR_SCENE_GATEWAY_SCRIPT136 = 1445;
    public static final int STR_SCENE_GATEWAY_SCRIPT137 = 1446;
    public static final int STR_SCENE_GATEWAY_SCRIPT138 = 1447;
    public static final int STR_SCENE_GATEWAY_SCRIPT139 = 1448;
    public static final int STR_SCENE_GATEWAY_SCRIPT140 = 1449;
    public static final int STR_SCENE_GATEWAY_SCRIPT141 = 1450;
    public static final int STR_SCENE_GATEWAY_SCRIPT142 = 1451;
    public static final int STR_SCENE_GATEWAY_SCRIPT143 = 1452;
    public static final int STR_SCENE_GATEWAY_SCRIPT144 = 1453;
    public static final int STR_SCENE_GATEWAY_SCRIPT145 = 1454;
    public static final int STR_SCENE_GATEWAY_SCRIPT146 = 1455;
    public static final int STR_SCENE_GATEWAY_SCRIPT147 = 1456;
    public static final int STR_SCENE_GATEWAY_SCRIPT148 = 1457;
    public static final int STR_SCENE_GATEWAY_SCRIPT149 = 1458;
    public static final int STR_SCENE_GATEWAY_SCRIPT150 = 1459;
    public static final int STR_SCENE_GATEWAY_SCRIPT151 = 1460;
    public static final int STR_SCENE_GATEWAY_SCRIPT152 = 1461;
    public static final int STR_SCENE_GATEWAY_SCRIPT153 = 1462;
    public static final int STR_SCENE_GATEWAY_SCRIPT154 = 1463;
    public static final int STR_SCENE_GATEWAY_SCRIPT155 = 1464;
    public static final int STR_SCENE_GATEWAY_SCRIPT156 = 1465;
    public static final int STR_SCENE_GATEWAY_SCRIPT157 = 1466;
    public static final int STR_SCENE_GATEWAY_SCRIPT158 = 1467;
    public static final int STR_SCENE_GATEWAY_SCRIPT159 = 1468;
    public static final int STR_SCENE_GATEWAY_SCRIPT160 = 1469;
    public static final int STR_SCENE_GATEWAY_SCRIPT161 = 1470;
    public static final int STR_SCENE_GATEWAY_SCRIPT162 = 1471;
    public static final int STR_SCENE_GATEWAY_SCRIPT163 = 1472;
    public static final int STR_SCENE_GATEWAY_SCRIPT164 = 1473;
    public static final int STR_SCENE_GATEWAY_SCRIPT165 = 1474;
    public static final int STR_SCENE_GATEWAY_SCRIPT166 = 1475;
    public static final int STR_SCENE_GATEWAY_SCRIPT167 = 1476;
    public static final int STR_SCENE_GATEWAY_SCRIPT168 = 1477;
    public static final int STR_SCENE_GATEWAY_SCRIPT169 = 1478;
    public static final int STR_SCENE_GATEWAY_SCRIPT170 = 1479;
    public static final int STR_SCENE_GATEWAY_SCRIPT171 = 1480;
    public static final int STR_SCENE_GATEWAY_SCRIPT172 = 1481;
    public static final int STR_SCENE_GATEWAY_SCRIPT173 = 1482;
    public static final int STR_SCENE_GATEWAY_SCRIPT174 = 1483;
    public static final int STR_SCENE_GATEWAY_SCRIPT175 = 1484;
    public static final int STR_SCENE_GATEWAY_SCRIPT176 = 1485;
    public static final int STR_SCENE_GATEWAY_SCRIPT177 = 1486;
    public static final int STR_SCENE_GATEWAY_SCRIPT178 = 1487;
    public static final int STR_SCENE_GATEWAY_SCRIPT179 = 1488;
    public static final int STR_SCENE_GATEWAY_SCRIPT180 = 1489;
    public static final int STR_SCENE_GATEWAY_SCRIPT181 = 1490;
    public static final int STR_SCENE_GATEWAY_SCRIPT182 = 1491;
    public static final int STR_SCENE_GATEWAY_SCRIPT183 = 1492;
    public static final int STR_SCENE_GATEWAY_SCRIPT184 = 1493;
    public static final int STR_SCENE_GATEWAY_SCRIPT185 = 1494;
    public static final int STR_SCENE_GATEWAY_SCRIPT186 = 1495;
    public static final int STR_SCENE_GATEWAY_SCRIPT187 = 1496;

    public static final int STR_SCENE_MARKET_SCRIPT0 = 1309;
    public static final int STR_SCENE_MARKET_SCRIPT1 = 1310;
    public static final int STR_SCENE_MARKET_SCRIPT2 = 1311;
    public static final int STR_SCENE_MARKET_SCRIPT3 = 1312;
    public static final int STR_SCENE_MARKET_SCRIPT4 = 1313;
    public static final int STR_SCENE_MARKET_SCRIPT5 = 1314;
    public static final int STR_SCENE_MARKET_SCRIPT6 = 1315;
    public static final int STR_SCENE_MARKET_SCRIPT7 = 1316;
    public static final int STR_SCENE_MARKET_SCRIPT8 = 1317;
    public static final int STR_SCENE_MARKET_SCRIPT9 = 1318;
    public static final int STR_SCENE_MARKET_SCRIPT10 = 1319;
    public static final int STR_SCENE_MARKET_SCRIPT11 = 1320;
    public static final int STR_SCENE_MARKET_SCRIPT12 = 1321;
    public static final int STR_SCENE_MARKET_SCRIPT13 = 1322;
    public static final int STR_SCENE_MARKET_SCRIPT14 = 1323;
    public static final int STR_SCENE_MARKET_SCRIPT15 = 1324;
    public static final int STR_SCENE_MARKET_SCRIPT16 = 1325;
    public static final int STR_SCENE_MARKET_SCRIPT17 = 1326;
    public static final int STR_SCENE_MARKET_SCRIPT18 = 1327;
    public static final int STR_SCENE_MARKET_SCRIPT19 = 1328;
    public static final int STR_SCENE_MARKET_SCRIPT20 = 1329;
    public static final int STR_SCENE_MARKET_SCRIPT21 = 1330;
    public static final int STR_SCENE_MARKET_SCRIPT22 = 1331;
    public static final int STR_SCENE_MARKET_SCRIPT23 = 1332;
    public static final int STR_SCENE_MARKET_SCRIPT24 = 1333;
    public static final int STR_SCENE_MARKET_SCRIPT25 = 1334;
    public static final int STR_SCENE_MARKET_SCRIPT26 = 1335;
    public static final int STR_SCENE_MARKET_SCRIPT27 = 1336;
    public static final int STR_SCENE_MARKET_SCRIPT28 = 1337;
    public static final int STR_SCENE_MARKET_SCRIPT29 = 1338;
    public static final int STR_SCENE_MARKET_SCRIPT30 = 1339;
    public static final int STR_SCENE_MARKET_SCRIPT31 = 1340;
    public static final int STR_SCENE_MARKET_SCRIPT32 = 1341;
    public static final int STR_SCENE_MARKET_SCRIPT33 = 1342;
    public static final int STR_SCENE_MARKET_SCRIPT34 = 1343;
    public static final int STR_SCENE_MARKET_SCRIPT35 = 1344;
    public static final int STR_SCENE_MARKET_SCRIPT36 = 1345;
    public static final int STR_SCENE_MARKET_SCRIPT37 = 1346;
    public static final int STR_SCENE_MARKET_SCRIPT38 = 1347;
    public static final int STR_SCENE_MARKET_SCRIPT39 = 1348;
    public static final int STR_SCENE_MARKET_SCRIPT40 = 1349;
    public static final int STR_SCENE_MARKET_SCRIPT41 = 1350;
    public static final int STR_SCENE_MARKET_SCRIPT42 = 1351;
    public static final int STR_SCENE_MARKET_SCRIPT43 = 1352;
    public static final int STR_SCENE_MARKET_SCRIPT44 = 1353;
    public static final int STR_SCENE_MARKET_SCRIPT45 = 1354;
    public static final int STR_SCENE_MARKET_SCRIPT46 = 1355;
    public static final int STR_SCENE_MARKET_SCRIPT47 = 1356;
    public static final int STR_SCENE_MARKET_SCRIPT48 = 1357;
    public static final int STR_SCENE_MARKET_SCRIPT49 = 1358;
    public static final int STR_SCENE_MARKET_SCRIPT50 = 1359;
    public static final int STR_SCENE_MARKET_SCRIPT51 = 1360;
    public static final int STR_SCENE_MARKET_SCRIPT52 = 1361;
    public static final int STR_SCENE_MARKET_SCRIPT53 = 1362;
    public static final int STR_SCENE_MARKET_SCRIPT54 = 1363;
    public static final int STR_SCENE_MARKET_SCRIPT55 = 1364;
    public static final int STR_SCENE_MARKET_SCRIPT56 = 1365;
    public static final int STR_SCENE_MARKET_SCRIPT57 = 1366;
    public static final int STR_SCENE_MARKET_SCRIPT58 = 1367;
    public static final int STR_SCENE_MARKET_SCRIPT59 = 1368;
    public static final int STR_SCENE_MARKET_SCRIPT60 = 1369;
    public static final int STR_SCENE_MARKET_SCRIPT61 = 1370;
    public static final int STR_SCENE_MARKET_SCRIPT62 = 1371;
    public static final int STR_SCENE_MARKET_SCRIPT63 = 1372;
    public static final int STR_SCENE_MARKET_SCRIPT64 = 1373;
    public static final int STR_SCENE_MARKET_SCRIPT65 = 1374;
    public static final int STR_SCENE_MARKET_SCRIPT66 = 1375;
    public static final int STR_SCENE_MARKET_SCRIPT67 = 1376;
    public static final int STR_SCENE_MARKET_SCRIPT68 = 1377;
    public static final int STR_SCENE_MARKET_SCRIPT69 = 1378;
    public static final int STR_SCENE_MARKET_SCRIPT70 = 1379;
    public static final int STR_SCENE_MARKET_SCRIPT71 = 1380;
    public static final int STR_SCENE_MARKET_SCRIPT72 = 1381;
    public static final int STR_SCENE_MARKET_SCRIPT73 = 1382;
    public static final int STR_SCENE_MARKET_SCRIPT74 = 1383;
    public static final int STR_SCENE_MARKET_SCRIPT75 = 1384;
    public static final int STR_SCENE_MARKET_SCRIPT76 = 1385;
    public static final int STR_SCENE_MARKET_SCRIPT77 = 1386;
    public static final int STR_SCENE_MARKET_SCRIPT78 = 1387;
    public static final int STR_SCENE_MARKET_SCRIPT79 = 1388;
    public static final int STR_SCENE_MARKET_SCRIPT80 = 1389;
    public static final int STR_SCENE_MARKET_SCRIPT81 = 1390;
    public static final int STR_SCENE_MARKET_SCRIPT82 = 1391;
    public static final int STR_SCENE_MARKET_SCRIPT83 = 1392;
    public static final int STR_SCENE_MARKET_SCRIPT84 = 1393;
    public static final int STR_SCENE_MARKET_SCRIPT85 = 1394;
    public static final int STR_SCENE_MARKET_SCRIPT86 = 1395;
    public static final int STR_SCENE_MARKET_SCRIPT87 = 1396;
    public static final int STR_SCENE_MARKET_SCRIPT88 = 1397;
    public static final int STR_SCENE_MARKET_SCRIPT89 = 1398;
    public static final int STR_SCENE_MARKET_SCRIPT90 = 1399;
    public static final int STR_SCENE_MARKET_SCRIPT91 = 1400;
    public static final int STR_SCENE_MARKET_SCRIPT92 = 1401;
    public static final int STR_SCENE_MARKET_SCRIPT93 = 1402;
    public static final int STR_SCENE_MARKET_SCRIPT94 = 1403;
    public static final int STR_SCENE_MARKET_SCRIPT95 = 1404;
    public static final int STR_SCENE_MARKET_SCRIPT96 = 1405;
    public static final int STR_SCENE_MARKET_SCRIPT97 = 1406;
    public static final int STR_SCENE_MARKET_SCRIPT98 = 1407;
    public static final int STR_SCENE_MARKET_SCRIPT99 = 1408;
    public static final int STR_SCENE_MARKET_SCRIPT100 = 1409;
    public static final int STR_SCENE_MARKET_SCRIPT101 = 1410;
    public static final int STR_SCENE_MARKET_SCRIPT102 = 1411;
    public static final int STR_SCENE_MARKET_SCRIPT103 = 1412;
    public static final int STR_SCENE_MARKET_SCRIPT104 = 1413;
    public static final int STR_SCENE_MARKET_SCRIPT105 = 1414;
    public static final int STR_SCENE_MARKET_SCRIPT106 = 1415;
    public static final int STR_SCENE_MARKET_SCRIPT107 = 1416;
    public static final int STR_SCENE_MARKET_SCRIPT108 = 1417;
    public static final int STR_SCENE_MARKET_SCRIPT109 = 1418;
    public static final int STR_SCENE_MARKET_SCRIPT110 = 1419;
    public static final int STR_SCENE_MARKET_SCRIPT111 = 1420;
    public static final int STR_SCENE_MARKET_SCRIPT112 = 1421;
    public static final int STR_SCENE_MARKET_SCRIPT113 = 1422;
    public static final int STR_SCENE_MARKET_SCRIPT114 = 1423;
    public static final int STR_SCENE_MARKET_SCRIPT115 = 1424;
    public static final int STR_SCENE_MARKET_SCRIPT116 = 1425;
    public static final int STR_SCENE_MARKET_SCRIPT117 = 1426;
    public static final int STR_SCENE_MARKET_SCRIPT118 = 1427;
    public static final int STR_SCENE_MARKET_SCRIPT119 = 1428;
    public static final int STR_SCENE_MARKET_SCRIPT120 = 1429;
    public static final int STR_SCENE_MARKET_SCRIPT121 = 1430;
    public static final int STR_SCENE_MARKET_SCRIPT122 = 1431;
    public static final int STR_SCENE_MARKET_SCRIPT123 = 1432;
    public static final int STR_SCENE_MARKET_SCRIPT124 = 1433;
    public static final int STR_SCENE_MARKET_SCRIPT125 = 1434;
    public static final int STR_SCENE_MARKET_SCRIPT126 = 1435;
    public static final int STR_SCENE_MARKET_SCRIPT127 = 1436;
    public static final int STR_SCENE_MARKET_SCRIPT128 = 1437;
    public static final int STR_SCENE_MARKET_SCRIPT129 = 1438;
    public static final int STR_SCENE_MARKET_SCRIPT130 = 1439;
    public static final int STR_SCENE_MARKET_SCRIPT131 = 1440;
    public static final int STR_SCENE_MARKET_SCRIPT132 = 1441;
    public static final int STR_SCENE_MARKET_SCRIPT133 = 1442;
    public static final int STR_SCENE_MARKET_SCRIPT134 = 1443;
    public static final int STR_SCENE_MARKET_SCRIPT135 = 1444;
    public static final int STR_SCENE_MARKET_SCRIPT136 = 1445;
    public static final int STR_SCENE_MARKET_SCRIPT137 = 1446;
    public static final int STR_SCENE_MARKET_SCRIPT138 = 1447;
    public static final int STR_SCENE_MARKET_SCRIPT139 = 1448;
    public static final int STR_SCENE_MARKET_SCRIPT140 = 1449;
    public static final int STR_SCENE_MARKET_SCRIPT141 = 1450;
    public static final int STR_SCENE_MARKET_SCRIPT142 = 1451;
    public static final int STR_SCENE_MARKET_SCRIPT143 = 1452;
    public static final int STR_SCENE_MARKET_SCRIPT144 = 1453;
    public static final int STR_SCENE_MARKET_SCRIPT145 = 1454;
    public static final int STR_SCENE_MARKET_SCRIPT146 = 1455;
    public static final int STR_SCENE_MARKET_SCRIPT147 = 1456;
    public static final int STR_SCENE_MARKET_SCRIPT148 = 1457;
    public static final int STR_SCENE_MARKET_SCRIPT149 = 1458;
    public static final int STR_SCENE_MARKET_SCRIPT150 = 1459;
    public static final int STR_SCENE_MARKET_SCRIPT151 = 1460;
    public static final int STR_SCENE_MARKET_SCRIPT152 = 1461;
    public static final int STR_SCENE_MARKET_SCRIPT153 = 1462;
    public static final int STR_SCENE_MARKET_SCRIPT154 = 1463;
    public static final int STR_SCENE_MARKET_SCRIPT155 = 1464;
    public static final int STR_SCENE_MARKET_SCRIPT156 = 1465;
    public static final int STR_SCENE_MARKET_SCRIPT157 = 1466;
    public static final int STR_SCENE_MARKET_SCRIPT158 = 1467;
    public static final int STR_SCENE_MARKET_SCRIPT159 = 1468;
    public static final int STR_SCENE_MARKET_SCRIPT160 = 1469;
    public static final int STR_SCENE_MARKET_SCRIPT161 = 1470;
    public static final int STR_SCENE_MARKET_SCRIPT162 = 1471;
    public static final int STR_SCENE_MARKET_SCRIPT163 = 1472;
    public static final int STR_SCENE_MARKET_SCRIPT164 = 1473;
    public static final int STR_SCENE_MARKET_SCRIPT165 = 1474;
    public static final int STR_SCENE_MARKET_SCRIPT166 = 1475;
    public static final int STR_SCENE_MARKET_SCRIPT167 = 1476;
    public static final int STR_SCENE_MARKET_SCRIPT168 = 1477;
    public static final int STR_SCENE_MARKET_SCRIPT169 = 1478;
    public static final int STR_SCENE_MARKET_SCRIPT170 = 1479;
    public static final int STR_SCENE_MARKET_SCRIPT171 = 1480;
    public static final int STR_SCENE_MARKET_SCRIPT172 = 1481;
    public static final int STR_SCENE_MARKET_SCRIPT173 = 1482;
    public static final int STR_SCENE_MARKET_SCRIPT174 = 1483;
    public static final int STR_SCENE_MARKET_SCRIPT175 = 1484;
    public static final int STR_SCENE_MARKET_SCRIPT176 = 1485;
    public static final int STR_SCENE_MARKET_SCRIPT177 = 1486;
    public static final int STR_SCENE_MARKET_SCRIPT178 = 1487;
    public static final int STR_SCENE_MARKET_SCRIPT179 = 1488;
    public static final int STR_SCENE_MARKET_SCRIPT180 = 1489;
    public static final int STR_SCENE_MARKET_SCRIPT181 = 1490;
    public static final int STR_SCENE_MARKET_SCRIPT182 = 1491;
    public static final int STR_SCENE_MARKET_SCRIPT183 = 1492;
    public static final int STR_SCENE_MARKET_SCRIPT184 = 1493;
    public static final int STR_SCENE_MARKET_SCRIPT185 = 1494;
    public static final int STR_SCENE_MARKET_SCRIPT186 = 1495;
    public static final int STR_SCENE_MARKET_SCRIPT187 = 1496;
    public static final int STR_SCENE_MARKET_SCRIPT188 = 1497;
    public static final int STR_SCENE_MARKET_SCRIPT189 = 1498;
    public static final int STR_SCENE_MARKET_SCRIPT190 = 1499;
    public static final int STR_SCENE_MARKET_SCRIPT191 = 1500;
    public static final int STR_SCENE_MARKET_SCRIPT192 = 1501;
    public static final int STR_SCENE_MARKET_SCRIPT193 = 1502;
    public static final int STR_SCENE_MARKET_SCRIPT194 = 1503;
    public static final int STR_SCENE_MARKET_SCRIPT195 = 1504;
    public static final int STR_SCENE_MARKET_SCRIPT196 = 1505;
    public static final int STR_SCENE_MARKET_SCRIPT197 = 1506;
    public static final int STR_SCENE_MARKET_SCRIPT198 = 1507;
    public static final int STR_SCENE_MARKET_SCRIPT199 = 1508;
    public static final int STR_SCENE_MARKET_SCRIPT200 = 1509;
    public static final int STR_SCENE_MARKET_SCRIPT201 = 1510;
    public static final int STR_SCENE_MARKET_SCRIPT202 = 1511;
    public static final int STR_SCENE_MARKET_SCRIPT203 = 1512;
    public static final int STR_SCENE_MARKET_SCRIPT204 = 1513;
    public static final int STR_SCENE_MARKET_SCRIPT205 = 1514;
    public static final int STR_SCENE_MARKET_SCRIPT206 = 1515;
    public static final int STR_SCENE_MARKET_SCRIPT207 = 1516;
    public static final int STR_SCENE_MARKET_SCRIPT208 = 1517;
    public static final int STR_SCENE_MARKET_SCRIPT209 = 1518;
    public static final int STR_SCENE_MARKET_SCRIPT210 = 1519;
    public static final int STR_SCENE_MARKET_SCRIPT211 = 1520;
    public static final int STR_SCENE_MARKET_SCRIPT212 = 1521;
    public static final int STR_SCENE_MARKET_SCRIPT213 = 1522;
    public static final int STR_SCENE_MARKET_SCRIPT214 = 1523;

    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT0 = 1309;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT1 = 1310;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT2 = 1311;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT3 = 1312;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT4 = 1313;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT5 = 1314;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT6 = 1315;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT7 = 1316;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT8 = 1317;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT9 = 1318;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT10 = 1319;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT11 = 1320;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT12 = 1321;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT13 = 1322;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT14 = 1323;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT15 = 1324;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT16 = 1325;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT17 = 1326;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT18 = 1327;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT19 = 1328;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT20 = 1329;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT21 = 1330;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT22 = 1331;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT23 = 1332;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT24 = 1333;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT25 = 1334;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT26 = 1335;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT27 = 1336;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT28 = 1337;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT29 = 1338;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT30 = 1339;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT31 = 1340;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT32 = 1341;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT33 = 1342;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT34 = 1343;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT35 = 1344;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT36 = 1345;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT37 = 1346;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT38 = 1347;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT39 = 1348;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT40 = 1349;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT41 = 1350;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT42 = 1351;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT43 = 1352;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT44 = 1353;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT45 = 1354;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT46 = 1355;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT47 = 1356;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT48 = 1357;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT49 = 1358;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT50 = 1359;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT51 = 1360;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT52 = 1361;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT53 = 1362;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT54 = 1363;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT55 = 1364;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT56 = 1365;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT57 = 1366;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT58 = 1367;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT59 = 1368;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT60 = 1369;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT61 = 1370;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT62 = 1371;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT63 = 1372;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT64 = 1373;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT65 = 1374;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT66 = 1375;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT67 = 1376;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT68 = 1377;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT69 = 1378;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT70 = 1379;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT71 = 1380;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT72 = 1381;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT73 = 1382;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT74 = 1383;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT75 = 1384;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT76 = 1385;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT77 = 1386;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT78 = 1387;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT79 = 1388;
    public static final int STR_SCENE_GAMBLE_GATEWAY_SCRIPT80 = 1389;

    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT0 = 1309;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT1 = 1310;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT2 = 1311;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT3 = 1312;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT4 = 1313;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT5 = 1314;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT6 = 1315;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT7 = 1316;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT8 = 1317;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT9 = 1318;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT10 = 1319;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT11 = 1320;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT12 = 1321;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT13 = 1322;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT14 = 1323;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT15 = 1324;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT16 = 1325;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT17 = 1326;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT18 = 1327;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT19 = 1328;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT20 = 1329;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT21 = 1330;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT22 = 1331;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT23 = 1332;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT24 = 1333;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT25 = 1334;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT26 = 1335;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT27 = 1336;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT28 = 1337;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT29 = 1338;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT30 = 1339;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT31 = 1340;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT32 = 1341;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT33 = 1342;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT34 = 1343;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT35 = 1344;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT36 = 1345;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT37 = 1346;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT38 = 1347;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT39 = 1348;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT40 = 1349;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT41 = 1350;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT42 = 1351;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT43 = 1352;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT44 = 1353;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT45 = 1354;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT46 = 1355;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT47 = 1356;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT48 = 1357;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT49 = 1358;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT50 = 1359;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT51 = 1360;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT52 = 1361;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT53 = 1362;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT54 = 1363;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT55 = 1364;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT56 = 1365;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT57 = 1366;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT58 = 1367;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT59 = 1368;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT60 = 1369;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT61 = 1370;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT62 = 1371;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT63 = 1372;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT64 = 1373;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT65 = 1374;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT66 = 1375;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT67 = 1376;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT68 = 1377;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT69 = 1378;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT70 = 1379;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT71 = 1380;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT72 = 1381;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT73 = 1382;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT74 = 1383;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT75 = 1384;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT76 = 1385;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT77 = 1386;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT78 = 1387;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT79 = 1388;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT80 = 1389;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT81 = 1390;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT82 = 1391;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT83 = 1392;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT84 = 1393;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT85 = 1394;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT86 = 1395;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT87 = 1396;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT88 = 1397;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT89 = 1398;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT90 = 1399;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT91 = 1400;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT92 = 1401;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT93 = 1402;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT94 = 1403;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT95 = 1404;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT96 = 1405;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT97 = 1406;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT98 = 1407;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT99 = 1408;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT100 = 1409;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT101 = 1410;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT102 = 1411;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT103 = 1412;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT104 = 1413;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT105 = 1414;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT106 = 1415;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT107 = 1416;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT108 = 1417;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT109 = 1418;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT110 = 1419;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT111 = 1420;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT112 = 1421;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT113 = 1422;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT114 = 1423;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT115 = 1424;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT116 = 1425;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT117 = 1426;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT118 = 1427;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT119 = 1428;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT120 = 1429;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT121 = 1430;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT122 = 1431;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT123 = 1432;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT124 = 1433;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT125 = 1434;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT126 = 1435;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT127 = 1436;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT128 = 1437;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT129 = 1438;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT130 = 1439;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT131 = 1440;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT132 = 1441;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT133 = 1442;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT134 = 1443;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT135 = 1444;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT136 = 1445;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT137 = 1446;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT138 = 1447;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT139 = 1448;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT140 = 1449;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT141 = 1450;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT142 = 1451;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT143 = 1452;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT144 = 1453;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT145 = 1454;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT146 = 1455;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT147 = 1456;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT148 = 1457;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT149 = 1458;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT150 = 1459;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT151 = 1460;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT152 = 1461;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT153 = 1462;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT154 = 1463;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT155 = 1464;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT156 = 1465;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT157 = 1466;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT158 = 1467;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT159 = 1468;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT160 = 1469;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT161 = 1470;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT162 = 1471;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT163 = 1472;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT164 = 1473;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT165 = 1474;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT166 = 1475;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT167 = 1476;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT168 = 1477;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT169 = 1478;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT170 = 1479;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT171 = 1480;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT172 = 1481;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT173 = 1482;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT174 = 1483;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT175 = 1484;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT176 = 1485;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT177 = 1486;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT178 = 1487;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT179 = 1488;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT180 = 1489;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT181 = 1490;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT182 = 1491;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT183 = 1492;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT184 = 1493;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT185 = 1494;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT186 = 1495;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT187 = 1496;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT188 = 1497;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT189 = 1498;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT190 = 1499;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT191 = 1500;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT192 = 1501;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT193 = 1502;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT194 = 1503;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT195 = 1504;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT196 = 1505;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT197 = 1506;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT198 = 1507;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT199 = 1508;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT200 = 1509;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT201 = 1510;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT202 = 1511;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT203 = 1512;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT204 = 1513;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT205 = 1514;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT206 = 1515;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT207 = 1516;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT208 = 1517;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT209 = 1518;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT210 = 1519;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT211 = 1520;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT212 = 1521;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT213 = 1522;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT214 = 1523;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT215 = 1524;
    public static final int STR_SCENE_GAMBLINGHOUSE_SCRIPT216 = 1525;

    public static final int STR_SCENE_GAMBLINGHOUSE1_SCRIPT0 = 1309;
    public static final int STR_SCENE_GAMBLINGHOUSE1_SCRIPT1 = 1310;
    public static final int STR_SCENE_GAMBLINGHOUSE1_SCRIPT2 = 1311;
    public static final int STR_SCENE_GAMBLINGHOUSE1_SCRIPT3 = 1312;
    public static final int STR_SCENE_GAMBLINGHOUSE1_SCRIPT4 = 1313;
    public static final int STR_SCENE_GAMBLINGHOUSE1_SCRIPT5 = 1314;
    public static final int STR_SCENE_GAMBLINGHOUSE1_SCRIPT6 = 1315;
    public static final int STR_SCENE_GAMBLINGHOUSE1_SCRIPT7 = 1316;
    public static final int STR_SCENE_GAMBLINGHOUSE1_SCRIPT8 = 1317;
    public static final int STR_SCENE_GAMBLINGHOUSE1_SCRIPT9 = 1318;
    public static final int STR_SCENE_GAMBLINGHOUSE1_SCRIPT10 = 1319;
    public static final int STR_SCENE_GAMBLINGHOUSE1_SCRIPT11 = 1320;
    public static final int STR_SCENE_GAMBLINGHOUSE1_SCRIPT12 = 1321;
    public static final int STR_SCENE_GAMBLINGHOUSE1_SCRIPT13 = 1322;
    public static final int STR_SCENE_GAMBLINGHOUSE1_SCRIPT14 = 1323;

    public static final int STR_SCENE_MAP_SCRIPT0 = 1309;
    public static final int STR_SCENE_MAP_SCRIPT1 = 1310;
    public static final int STR_SCENE_MAP_SCRIPT2 = 1311;
    public static final int STR_SCENE_MAP_SCRIPT3 = 1312;
    public static final int STR_SCENE_MAP_SCRIPT4 = 1313;
    public static final int STR_SCENE_MAP_SCRIPT5 = 1314;
    public static final int STR_SCENE_MAP_SCRIPT6 = 1315;
    public static final int STR_SCENE_MAP_SCRIPT7 = 1316;
    public static final int STR_SCENE_MAP_SCRIPT8 = 1317;
    public static final int STR_SCENE_MAP_SCRIPT9 = 1318;
    public static final int STR_SCENE_MAP_SCRIPT10 = 1319;
    public static final int STR_SCENE_MAP_SCRIPT11 = 1320;
    public static final int STR_SCENE_MAP_SCRIPT12 = 1321;
    public static final int STR_SCENE_MAP_SCRIPT13 = 1322;
    public static final int STR_SCENE_MAP_SCRIPT14 = 1323;
    public static final int STR_SCENE_MAP_SCRIPT15 = 1324;
    public static final int STR_SCENE_MAP_SCRIPT16 = 1325;
    public static final int STR_SCENE_MAP_SCRIPT17 = 1326;
    public static final int STR_SCENE_MAP_SCRIPT18 = 1327;
    public static final int STR_SCENE_MAP_SCRIPT19 = 1328;
    public static final int STR_SCENE_MAP_SCRIPT20 = 1329;
    public static final int STR_SCENE_MAP_SCRIPT21 = 1330;
    public static final int STR_SCENE_MAP_SCRIPT22 = 1331;
    public static final int STR_SCENE_MAP_SCRIPT23 = 1332;
    public static final int STR_SCENE_MAP_SCRIPT24 = 1333;
    public static final int STR_SCENE_MAP_SCRIPT25 = 1334;
    public static final int STR_SCENE_MAP_SCRIPT26 = 1335;
    public static final int STR_SCENE_MAP_SCRIPT27 = 1336;
    public static final int STR_SCENE_MAP_SCRIPT28 = 1337;
    public static final int STR_SCENE_MAP_SCRIPT29 = 1338;
    public static final int STR_SCENE_MAP_SCRIPT30 = 1339;
    public static final int STR_SCENE_MAP_SCRIPT31 = 1340;
    public static final int STR_SCENE_MAP_SCRIPT32 = 1341;
    public static final int STR_SCENE_MAP_SCRIPT33 = 1342;
    public static final int STR_SCENE_MAP_SCRIPT34 = 1343;
    public static final int STR_SCENE_MAP_SCRIPT35 = 1344;
    public static final int STR_SCENE_MAP_SCRIPT36 = 1345;
    public static final int STR_SCENE_MAP_SCRIPT37 = 1346;
    public static final int STR_SCENE_MAP_SCRIPT38 = 1347;
    public static final int STR_SCENE_MAP_SCRIPT39 = 1348;
    public static final int STR_SCENE_MAP_SCRIPT40 = 1349;
    public static final int STR_SCENE_MAP_SCRIPT41 = 1350;
    public static final int STR_SCENE_MAP_SCRIPT42 = 1351;
    public static final int STR_SCENE_MAP_SCRIPT43 = 1352;
    public static final int STR_SCENE_MAP_SCRIPT44 = 1353;
    public static final int STR_SCENE_MAP_SCRIPT45 = 1354;
    public static final int STR_SCENE_MAP_SCRIPT46 = 1355;
    public static final int STR_SCENE_MAP_SCRIPT47 = 1356;
    public static final int STR_SCENE_MAP_SCRIPT48 = 1357;
    public static final int STR_SCENE_MAP_SCRIPT49 = 1358;
    public static final int STR_SCENE_MAP_SCRIPT50 = 1359;
    public static final int STR_SCENE_MAP_SCRIPT51 = 1360;
    public static final int STR_SCENE_MAP_SCRIPT52 = 1361;
    public static final int STR_SCENE_MAP_SCRIPT53 = 1362;
    public static final int STR_SCENE_MAP_SCRIPT54 = 1363;

    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT0 = 1309;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT1 = 1310;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT2 = 1311;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT3 = 1312;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT4 = 1313;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT5 = 1314;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT6 = 1315;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT7 = 1316;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT8 = 1317;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT9 = 1318;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT10 = 1319;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT11 = 1320;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT12 = 1321;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT13 = 1322;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT14 = 1323;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT15 = 1324;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT16 = 1325;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT17 = 1326;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT18 = 1327;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT19 = 1328;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT20 = 1329;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT21 = 1330;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT22 = 1331;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT23 = 1332;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT24 = 1333;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT25 = 1334;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT26 = 1335;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT27 = 1336;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT28 = 1337;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT29 = 1338;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT30 = 1339;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT31 = 1340;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT32 = 1341;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT33 = 1342;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT34 = 1343;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT35 = 1344;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT36 = 1345;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT37 = 1346;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT38 = 1347;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT39 = 1348;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT40 = 1349;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT41 = 1350;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT42 = 1351;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT43 = 1352;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT44 = 1353;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT45 = 1354;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT46 = 1355;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT47 = 1356;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT48 = 1357;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT49 = 1358;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT50 = 1359;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT51 = 1360;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT52 = 1361;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT53 = 1362;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT54 = 1363;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT55 = 1364;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT56 = 1365;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT57 = 1366;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT58 = 1367;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT59 = 1368;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT60 = 1369;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT61 = 1370;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT62 = 1371;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT63 = 1372;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT64 = 1373;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT65 = 1374;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT66 = 1375;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT67 = 1376;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT68 = 1377;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT69 = 1378;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT70 = 1379;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT71 = 1380;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT72 = 1381;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT73 = 1382;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT74 = 1383;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT75 = 1384;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT76 = 1385;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT77 = 1386;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT78 = 1387;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT79 = 1388;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT80 = 1389;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT81 = 1390;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT82 = 1391;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT83 = 1392;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT84 = 1393;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT85 = 1394;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT86 = 1395;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT87 = 1396;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT88 = 1397;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT89 = 1398;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT90 = 1399;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT91 = 1400;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT92 = 1401;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT93 = 1402;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT94 = 1403;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT95 = 1404;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT96 = 1405;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT97 = 1406;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT98 = 1407;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT99 = 1408;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT100 = 1409;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT101 = 1410;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT102 = 1411;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT103 = 1412;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT104 = 1413;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT105 = 1414;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT106 = 1415;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT107 = 1416;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT108 = 1417;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT109 = 1418;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT110 = 1419;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT111 = 1420;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT112 = 1421;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT113 = 1422;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT114 = 1423;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT115 = 1424;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT116 = 1425;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT117 = 1426;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT118 = 1427;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT119 = 1428;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT120 = 1429;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT121 = 1430;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT122 = 1431;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT123 = 1432;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT124 = 1433;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT125 = 1434;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT126 = 1435;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT127 = 1436;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT128 = 1437;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT129 = 1438;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT130 = 1439;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT131 = 1440;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT132 = 1441;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT133 = 1442;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT134 = 1443;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT135 = 1444;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT136 = 1445;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT137 = 1446;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT138 = 1447;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT139 = 1448;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT140 = 1449;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT141 = 1450;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT142 = 1451;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT143 = 1452;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT144 = 1453;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT145 = 1454;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT146 = 1455;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT147 = 1456;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT148 = 1457;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT149 = 1458;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT150 = 1459;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT151 = 1460;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT152 = 1461;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT153 = 1462;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT154 = 1463;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT155 = 1464;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT156 = 1465;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT157 = 1466;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT158 = 1467;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT159 = 1468;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT160 = 1469;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT161 = 1470;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT162 = 1471;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT163 = 1472;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT164 = 1473;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT165 = 1474;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT166 = 1475;
    public static final int STR_SCENE_SONGJI_ROAD_SCRIPT167 = 1476;

    public static final int STR_SCENE_PALACE1_SCRIPT0 = 1309;
    public static final int STR_SCENE_PALACE1_SCRIPT1 = 1310;
    public static final int STR_SCENE_PALACE1_SCRIPT2 = 1311;
    public static final int STR_SCENE_PALACE1_SCRIPT3 = 1312;
    public static final int STR_SCENE_PALACE1_SCRIPT4 = 1313;
    public static final int STR_SCENE_PALACE1_SCRIPT5 = 1314;
    public static final int STR_SCENE_PALACE1_SCRIPT6 = 1315;
    public static final int STR_SCENE_PALACE1_SCRIPT7 = 1316;
    public static final int STR_SCENE_PALACE1_SCRIPT8 = 1317;
    public static final int STR_SCENE_PALACE1_SCRIPT9 = 1318;
    public static final int STR_SCENE_PALACE1_SCRIPT10 = 1319;
    public static final int STR_SCENE_PALACE1_SCRIPT11 = 1320;
    public static final int STR_SCENE_PALACE1_SCRIPT12 = 1321;
    public static final int STR_SCENE_PALACE1_SCRIPT13 = 1322;
    public static final int STR_SCENE_PALACE1_SCRIPT14 = 1323;
    public static final int STR_SCENE_PALACE1_SCRIPT15 = 1324;
    public static final int STR_SCENE_PALACE1_SCRIPT16 = 1325;
    public static final int STR_SCENE_PALACE1_SCRIPT17 = 1326;
    public static final int STR_SCENE_PALACE1_SCRIPT18 = 1327;
    public static final int STR_SCENE_PALACE1_SCRIPT19 = 1328;
    public static final int STR_SCENE_PALACE1_SCRIPT20 = 1329;
    public static final int STR_SCENE_PALACE1_SCRIPT21 = 1330;
    public static final int STR_SCENE_PALACE1_SCRIPT22 = 1331;
    public static final int STR_SCENE_PALACE1_SCRIPT23 = 1332;
    public static final int STR_SCENE_PALACE1_SCRIPT24 = 1333;
    public static final int STR_SCENE_PALACE1_SCRIPT25 = 1334;
    public static final int STR_SCENE_PALACE1_SCRIPT26 = 1335;
    public static final int STR_SCENE_PALACE1_SCRIPT27 = 1336;
    public static final int STR_SCENE_PALACE1_SCRIPT28 = 1337;
    public static final int STR_SCENE_PALACE1_SCRIPT29 = 1338;

    public static final int STR_SCENE_BEDROOM1_SCRIPT0 = 1309;
    public static final int STR_SCENE_BEDROOM1_SCRIPT1 = 1310;
    public static final int STR_SCENE_BEDROOM1_SCRIPT2 = 1311;
    public static final int STR_SCENE_BEDROOM1_SCRIPT3 = 1312;
    public static final int STR_SCENE_BEDROOM1_SCRIPT4 = 1313;
    public static final int STR_SCENE_BEDROOM1_SCRIPT5 = 1314;
    public static final int STR_SCENE_BEDROOM1_SCRIPT6 = 1315;
    public static final int STR_SCENE_BEDROOM1_SCRIPT7 = 1316;
    public static final int STR_SCENE_BEDROOM1_SCRIPT8 = 1317;
    public static final int STR_SCENE_BEDROOM1_SCRIPT9 = 1318;
    public static final int STR_SCENE_BEDROOM1_SCRIPT10 = 1319;
    public static final int STR_SCENE_BEDROOM1_SCRIPT11 = 1320;
    public static final int STR_SCENE_BEDROOM1_SCRIPT12 = 1321;
    public static final int STR_SCENE_BEDROOM1_SCRIPT13 = 1322;
    public static final int STR_SCENE_BEDROOM1_SCRIPT14 = 1323;
    public static final int STR_SCENE_BEDROOM1_SCRIPT15 = 1324;
    public static final int STR_SCENE_BEDROOM1_SCRIPT16 = 1325;
    public static final int STR_SCENE_BEDROOM1_SCRIPT17 = 1326;
    public static final int STR_SCENE_BEDROOM1_SCRIPT18 = 1327;
    public static final int STR_SCENE_BEDROOM1_SCRIPT19 = 1328;
    public static final int STR_SCENE_BEDROOM1_SCRIPT20 = 1329;
    public static final int STR_SCENE_BEDROOM1_SCRIPT21 = 1330;
    public static final int STR_SCENE_BEDROOM1_SCRIPT22 = 1331;
    public static final int STR_SCENE_BEDROOM1_SCRIPT23 = 1332;
    public static final int STR_SCENE_BEDROOM1_SCRIPT24 = 1333;
    public static final int STR_SCENE_BEDROOM1_SCRIPT25 = 1334;
    public static final int STR_SCENE_BEDROOM1_SCRIPT26 = 1335;
    public static final int STR_SCENE_BEDROOM1_SCRIPT27 = 1336;
    public static final int STR_SCENE_BEDROOM1_SCRIPT28 = 1337;
    public static final int STR_SCENE_BEDROOM1_SCRIPT29 = 1338;
    public static final int STR_SCENE_BEDROOM1_SCRIPT30 = 1339;
    public static final int STR_SCENE_BEDROOM1_SCRIPT31 = 1340;
    public static final int STR_SCENE_BEDROOM1_SCRIPT32 = 1341;
    public static final int STR_SCENE_BEDROOM1_SCRIPT33 = 1342;
    public static final int STR_SCENE_BEDROOM1_SCRIPT34 = 1343;
    public static final int STR_SCENE_BEDROOM1_SCRIPT35 = 1344;
    public static final int STR_SCENE_BEDROOM1_SCRIPT36 = 1345;
    public static final int STR_SCENE_BEDROOM1_SCRIPT37 = 1346;
    public static final int STR_SCENE_BEDROOM1_SCRIPT38 = 1347;
    public static final int STR_SCENE_BEDROOM1_SCRIPT39 = 1348;
    public static final int STR_SCENE_BEDROOM1_SCRIPT40 = 1349;
    public static final int STR_SCENE_BEDROOM1_SCRIPT41 = 1350;
    public static final int STR_SCENE_BEDROOM1_SCRIPT42 = 1351;
    public static final int STR_SCENE_BEDROOM1_SCRIPT43 = 1352;
    public static final int STR_SCENE_BEDROOM1_SCRIPT44 = 1353;
    public static final int STR_SCENE_BEDROOM1_SCRIPT45 = 1354;
    public static final int STR_SCENE_BEDROOM1_SCRIPT46 = 1355;
    public static final int STR_SCENE_BEDROOM1_SCRIPT47 = 1356;
    public static final int STR_SCENE_BEDROOM1_SCRIPT48 = 1357;
    public static final int STR_SCENE_BEDROOM1_SCRIPT49 = 1358;
    public static final int STR_SCENE_BEDROOM1_SCRIPT50 = 1359;
    public static final int STR_SCENE_BEDROOM1_SCRIPT51 = 1360;
    public static final int STR_SCENE_BEDROOM1_SCRIPT52 = 1361;
    public static final int STR_SCENE_BEDROOM1_SCRIPT53 = 1362;
    public static final int STR_SCENE_BEDROOM1_SCRIPT54 = 1363;
    public static final int STR_SCENE_BEDROOM1_SCRIPT55 = 1364;
    public static final int STR_SCENE_BEDROOM1_SCRIPT56 = 1365;
    public static final int STR_SCENE_BEDROOM1_SCRIPT57 = 1366;
    public static final int STR_SCENE_BEDROOM1_SCRIPT58 = 1367;
    public static final int STR_SCENE_BEDROOM1_SCRIPT59 = 1368;
    public static final int STR_SCENE_BEDROOM1_SCRIPT60 = 1369;
    public static final int STR_SCENE_BEDROOM1_SCRIPT61 = 1370;
    public static final int STR_SCENE_BEDROOM1_SCRIPT62 = 1371;
    public static final int STR_SCENE_BEDROOM1_SCRIPT63 = 1372;
    public static final int STR_SCENE_BEDROOM1_SCRIPT64 = 1373;
    public static final int STR_SCENE_BEDROOM1_SCRIPT65 = 1374;
    public static final int STR_SCENE_BEDROOM1_SCRIPT66 = 1375;
    public static final int STR_SCENE_BEDROOM1_SCRIPT67 = 1376;
    public static final int STR_SCENE_BEDROOM1_SCRIPT68 = 1377;
    public static final int STR_SCENE_BEDROOM1_SCRIPT69 = 1378;
    public static final int STR_SCENE_BEDROOM1_SCRIPT70 = 1379;
    public static final int STR_SCENE_BEDROOM1_SCRIPT71 = 1380;
    public static final int STR_SCENE_BEDROOM1_SCRIPT72 = 1381;
    public static final int STR_SCENE_BEDROOM1_SCRIPT73 = 1382;
    public static final int STR_SCENE_BEDROOM1_SCRIPT74 = 1383;
    public static final int STR_SCENE_BEDROOM1_SCRIPT75 = 1384;
    public static final int STR_SCENE_BEDROOM1_SCRIPT76 = 1385;
    public static final int STR_SCENE_BEDROOM1_SCRIPT77 = 1386;
    public static final int STR_SCENE_BEDROOM1_SCRIPT78 = 1387;
    public static final int STR_SCENE_BEDROOM1_SCRIPT79 = 1388;
    public static final int STR_SCENE_BEDROOM1_SCRIPT80 = 1389;
    public static final int STR_SCENE_BEDROOM1_SCRIPT81 = 1390;
    public static final int STR_SCENE_BEDROOM1_SCRIPT82 = 1391;
    public static final int STR_SCENE_BEDROOM1_SCRIPT83 = 1392;
    public static final int STR_SCENE_BEDROOM1_SCRIPT84 = 1393;
    public static final int STR_SCENE_BEDROOM1_SCRIPT85 = 1394;
    public static final int STR_SCENE_BEDROOM1_SCRIPT86 = 1395;
    public static final int STR_SCENE_BEDROOM1_SCRIPT87 = 1396;
    public static final int STR_SCENE_BEDROOM1_SCRIPT88 = 1397;
    public static final int STR_SCENE_BEDROOM1_SCRIPT89 = 1398;
    public static final int STR_SCENE_BEDROOM1_SCRIPT90 = 1399;
    public static final int STR_SCENE_BEDROOM1_SCRIPT91 = 1400;
    public static final int STR_SCENE_BEDROOM1_SCRIPT92 = 1401;
    public static final int STR_SCENE_BEDROOM1_SCRIPT93 = 1402;
    public static final int STR_SCENE_BEDROOM1_SCRIPT94 = 1403;
    public static final int STR_SCENE_BEDROOM1_SCRIPT95 = 1404;
    public static final int STR_SCENE_BEDROOM1_SCRIPT96 = 1405;
    public static final int STR_SCENE_BEDROOM1_SCRIPT97 = 1406;
    public static final int STR_SCENE_BEDROOM1_SCRIPT98 = 1407;
    public static final int STR_SCENE_BEDROOM1_SCRIPT99 = 1408;
    public static final int STR_SCENE_BEDROOM1_SCRIPT100 = 1409;
    public static final int STR_SCENE_BEDROOM1_SCRIPT101 = 1410;
    public static final int STR_SCENE_BEDROOM1_SCRIPT102 = 1411;
    public static final int STR_SCENE_BEDROOM1_SCRIPT103 = 1412;
    public static final int STR_SCENE_BEDROOM1_SCRIPT104 = 1413;
    public static final int STR_SCENE_BEDROOM1_SCRIPT105 = 1414;
    public static final int STR_SCENE_BEDROOM1_SCRIPT106 = 1415;
    public static final int STR_SCENE_BEDROOM1_SCRIPT107 = 1416;
    public static final int STR_SCENE_BEDROOM1_SCRIPT108 = 1417;
    public static final int STR_SCENE_BEDROOM1_SCRIPT109 = 1418;
    public static final int STR_SCENE_BEDROOM1_SCRIPT110 = 1419;
    public static final int STR_SCENE_BEDROOM1_SCRIPT111 = 1420;
    public static final int STR_SCENE_BEDROOM1_SCRIPT112 = 1421;
    public static final int STR_SCENE_BEDROOM1_SCRIPT113 = 1422;
    public static final int STR_SCENE_BEDROOM1_SCRIPT114 = 1423;
    public static final int STR_SCENE_BEDROOM1_SCRIPT115 = 1424;
    public static final int STR_SCENE_BEDROOM1_SCRIPT116 = 1425;
    public static final int STR_SCENE_BEDROOM1_SCRIPT117 = 1426;
    public static final int STR_SCENE_BEDROOM1_SCRIPT118 = 1427;
    public static final int STR_SCENE_BEDROOM1_SCRIPT119 = 1428;
    public static final int STR_SCENE_BEDROOM1_SCRIPT120 = 1429;
    public static final int STR_SCENE_BEDROOM1_SCRIPT121 = 1430;
    public static final int STR_SCENE_BEDROOM1_SCRIPT122 = 1431;

    public static final int STR_SCENE_HOME_MAINYARD1_SCRIPT0 = 1309;
    public static final int STR_SCENE_HOME_MAINYARD1_SCRIPT1 = 1310;
    public static final int STR_SCENE_HOME_MAINYARD1_SCRIPT2 = 1311;
    public static final int STR_SCENE_HOME_MAINYARD1_SCRIPT3 = 1312;
    public static final int STR_SCENE_HOME_MAINYARD1_SCRIPT4 = 1313;
    public static final int STR_SCENE_HOME_MAINYARD1_SCRIPT5 = 1314;
    public static final int STR_SCENE_HOME_MAINYARD1_SCRIPT6 = 1315;
    public static final int STR_SCENE_HOME_MAINYARD1_SCRIPT7 = 1316;
    public static final int STR_SCENE_HOME_MAINYARD1_SCRIPT8 = 1317;
    public static final int STR_SCENE_HOME_MAINYARD1_SCRIPT9 = 1318;
    public static final int STR_SCENE_HOME_MAINYARD1_SCRIPT10 = 1319;
    public static final int STR_SCENE_HOME_MAINYARD1_SCRIPT11 = 1320;
    public static final int STR_SCENE_HOME_MAINYARD1_SCRIPT12 = 1321;
    public static final int STR_SCENE_HOME_MAINYARD1_SCRIPT13 = 1322;
    public static final int STR_SCENE_HOME_MAINYARD1_SCRIPT14 = 1323;
    public static final int STR_SCENE_HOME_MAINYARD1_SCRIPT15 = 1324;

    public static final int STR_SCENE_HOME_HALL_SCRIPT0 = 1309;
    public static final int STR_SCENE_HOME_HALL_SCRIPT1 = 1310;
    public static final int STR_SCENE_HOME_HALL_SCRIPT2 = 1311;
    public static final int STR_SCENE_HOME_HALL_SCRIPT3 = 1312;
    public static final int STR_SCENE_HOME_HALL_SCRIPT4 = 1313;
    public static final int STR_SCENE_HOME_HALL_SCRIPT5 = 1314;
    public static final int STR_SCENE_HOME_HALL_SCRIPT6 = 1315;
    public static final int STR_SCENE_HOME_HALL_SCRIPT7 = 1316;
    public static final int STR_SCENE_HOME_HALL_SCRIPT8 = 1317;
    public static final int STR_SCENE_HOME_HALL_SCRIPT9 = 1318;
    public static final int STR_SCENE_HOME_HALL_SCRIPT10 = 1319;
    public static final int STR_SCENE_HOME_HALL_SCRIPT11 = 1320;
    public static final int STR_SCENE_HOME_HALL_SCRIPT12 = 1321;
    public static final int STR_SCENE_HOME_HALL_SCRIPT13 = 1322;
    public static final int STR_SCENE_HOME_HALL_SCRIPT14 = 1323;
    public static final int STR_SCENE_HOME_HALL_SCRIPT15 = 1324;
    public static final int STR_SCENE_HOME_HALL_SCRIPT16 = 1325;
    public static final int STR_SCENE_HOME_HALL_SCRIPT17 = 1326;
    public static final int STR_SCENE_HOME_HALL_SCRIPT18 = 1327;
    public static final int STR_SCENE_HOME_HALL_SCRIPT19 = 1328;
    public static final int STR_SCENE_HOME_HALL_SCRIPT20 = 1329;
    public static final int STR_SCENE_HOME_HALL_SCRIPT21 = 1330;
    public static final int STR_SCENE_HOME_HALL_SCRIPT22 = 1331;
    public static final int STR_SCENE_HOME_HALL_SCRIPT23 = 1332;
    public static final int STR_SCENE_HOME_HALL_SCRIPT24 = 1333;
    public static final int STR_SCENE_HOME_HALL_SCRIPT25 = 1334;
    public static final int STR_SCENE_HOME_HALL_SCRIPT26 = 1335;
    public static final int STR_SCENE_HOME_HALL_SCRIPT27 = 1336;
    public static final int STR_SCENE_HOME_HALL_SCRIPT28 = 1337;
    public static final int STR_SCENE_HOME_HALL_SCRIPT29 = 1338;
    public static final int STR_SCENE_HOME_HALL_SCRIPT30 = 1339;
    public static final int STR_SCENE_HOME_HALL_SCRIPT31 = 1340;
    public static final int STR_SCENE_HOME_HALL_SCRIPT32 = 1341;
    public static final int STR_SCENE_HOME_HALL_SCRIPT33 = 1342;
    public static final int STR_SCENE_HOME_HALL_SCRIPT34 = 1343;
    public static final int STR_SCENE_HOME_HALL_SCRIPT35 = 1344;
    public static final int STR_SCENE_HOME_HALL_SCRIPT36 = 1345;
    public static final int STR_SCENE_HOME_HALL_SCRIPT37 = 1346;
    public static final int STR_SCENE_HOME_HALL_SCRIPT38 = 1347;
    public static final int STR_SCENE_HOME_HALL_SCRIPT39 = 1348;
    public static final int STR_SCENE_HOME_HALL_SCRIPT40 = 1349;
    public static final int STR_SCENE_HOME_HALL_SCRIPT41 = 1350;
    public static final int STR_SCENE_HOME_HALL_SCRIPT42 = 1351;
    public static final int STR_SCENE_HOME_HALL_SCRIPT43 = 1352;
    public static final int STR_SCENE_HOME_HALL_SCRIPT44 = 1353;
    public static final int STR_SCENE_HOME_HALL_SCRIPT45 = 1354;
    public static final int STR_SCENE_HOME_HALL_SCRIPT46 = 1355;
    public static final int STR_SCENE_HOME_HALL_SCRIPT47 = 1356;
    public static final int STR_SCENE_HOME_HALL_SCRIPT48 = 1357;
    public static final int STR_SCENE_HOME_HALL_SCRIPT49 = 1358;
    public static final int STR_SCENE_HOME_HALL_SCRIPT50 = 1359;
    public static final int STR_SCENE_HOME_HALL_SCRIPT51 = 1360;
    public static final int STR_SCENE_HOME_HALL_SCRIPT52 = 1361;
    public static final int STR_SCENE_HOME_HALL_SCRIPT53 = 1362;
    public static final int STR_SCENE_HOME_HALL_SCRIPT54 = 1363;
    public static final int STR_SCENE_HOME_HALL_SCRIPT55 = 1364;
    public static final int STR_SCENE_HOME_HALL_SCRIPT56 = 1365;
    public static final int STR_SCENE_HOME_HALL_SCRIPT57 = 1366;
    public static final int STR_SCENE_HOME_HALL_SCRIPT58 = 1367;
    public static final int STR_SCENE_HOME_HALL_SCRIPT59 = 1368;
    public static final int STR_SCENE_HOME_HALL_SCRIPT60 = 1369;
    public static final int STR_SCENE_HOME_HALL_SCRIPT61 = 1370;
    public static final int STR_SCENE_HOME_HALL_SCRIPT62 = 1371;
    public static final int STR_SCENE_HOME_HALL_SCRIPT63 = 1372;
    public static final int STR_SCENE_HOME_HALL_SCRIPT64 = 1373;
    public static final int STR_SCENE_HOME_HALL_SCRIPT65 = 1374;
    public static final int STR_SCENE_HOME_HALL_SCRIPT66 = 1375;
    public static final int STR_SCENE_HOME_HALL_SCRIPT67 = 1376;
    public static final int STR_SCENE_HOME_HALL_SCRIPT68 = 1377;
    public static final int STR_SCENE_HOME_HALL_SCRIPT69 = 1378;
    public static final int STR_SCENE_HOME_HALL_SCRIPT70 = 1379;
    public static final int STR_SCENE_HOME_HALL_SCRIPT71 = 1380;
    public static final int STR_SCENE_HOME_HALL_SCRIPT72 = 1381;
    public static final int STR_SCENE_HOME_HALL_SCRIPT73 = 1382;
    public static final int STR_SCENE_HOME_HALL_SCRIPT74 = 1383;
    public static final int STR_SCENE_HOME_HALL_SCRIPT75 = 1384;
    public static final int STR_SCENE_HOME_HALL_SCRIPT76 = 1385;
    public static final int STR_SCENE_HOME_HALL_SCRIPT77 = 1386;
    public static final int STR_SCENE_HOME_HALL_SCRIPT78 = 1387;
    public static final int STR_SCENE_HOME_HALL_SCRIPT79 = 1388;
    public static final int STR_SCENE_HOME_HALL_SCRIPT80 = 1389;
    public static final int STR_SCENE_HOME_HALL_SCRIPT81 = 1390;
    public static final int STR_SCENE_HOME_HALL_SCRIPT82 = 1391;
    public static final int STR_SCENE_HOME_HALL_SCRIPT83 = 1392;
    public static final int STR_SCENE_HOME_HALL_SCRIPT84 = 1393;
    public static final int STR_SCENE_HOME_HALL_SCRIPT85 = 1394;
    public static final int STR_SCENE_HOME_HALL_SCRIPT86 = 1395;
    public static final int STR_SCENE_HOME_HALL_SCRIPT87 = 1396;
    public static final int STR_SCENE_HOME_HALL_SCRIPT88 = 1397;
    public static final int STR_SCENE_HOME_HALL_SCRIPT89 = 1398;
    public static final int STR_SCENE_HOME_HALL_SCRIPT90 = 1399;
    public static final int STR_SCENE_HOME_HALL_SCRIPT91 = 1400;
    public static final int STR_SCENE_HOME_HALL_SCRIPT92 = 1401;
    public static final int STR_SCENE_HOME_HALL_SCRIPT93 = 1402;
    public static final int STR_SCENE_HOME_HALL_SCRIPT94 = 1403;
    public static final int STR_SCENE_HOME_HALL_SCRIPT95 = 1404;
    public static final int STR_SCENE_HOME_HALL_SCRIPT96 = 1405;
    public static final int STR_SCENE_HOME_HALL_SCRIPT97 = 1406;
    public static final int STR_SCENE_HOME_HALL_SCRIPT98 = 1407;
    public static final int STR_SCENE_HOME_HALL_SCRIPT99 = 1408;
    public static final int STR_SCENE_HOME_HALL_SCRIPT100 = 1409;
    public static final int STR_SCENE_HOME_HALL_SCRIPT101 = 1410;
    public static final int STR_SCENE_HOME_HALL_SCRIPT102 = 1411;
    public static final int STR_SCENE_HOME_HALL_SCRIPT103 = 1412;
    public static final int STR_SCENE_HOME_HALL_SCRIPT104 = 1413;
    public static final int STR_SCENE_HOME_HALL_SCRIPT105 = 1414;
    public static final int STR_SCENE_HOME_HALL_SCRIPT106 = 1415;
    public static final int STR_SCENE_HOME_HALL_SCRIPT107 = 1416;
    public static final int STR_SCENE_HOME_HALL_SCRIPT108 = 1417;
    public static final int STR_SCENE_HOME_HALL_SCRIPT109 = 1418;
    public static final int STR_SCENE_HOME_HALL_SCRIPT110 = 1419;
    public static final int STR_SCENE_HOME_HALL_SCRIPT111 = 1420;
    public static final int STR_SCENE_HOME_HALL_SCRIPT112 = 1421;
    public static final int STR_SCENE_HOME_HALL_SCRIPT113 = 1422;
    public static final int STR_SCENE_HOME_HALL_SCRIPT114 = 1423;
    public static final int STR_SCENE_HOME_HALL_SCRIPT115 = 1424;
    public static final int STR_SCENE_HOME_HALL_SCRIPT116 = 1425;
    public static final int STR_SCENE_HOME_HALL_SCRIPT117 = 1426;
    public static final int STR_SCENE_HOME_HALL_SCRIPT118 = 1427;
    public static final int STR_SCENE_HOME_HALL_SCRIPT119 = 1428;
    public static final int STR_SCENE_HOME_HALL_SCRIPT120 = 1429;
    public static final int STR_SCENE_HOME_HALL_SCRIPT121 = 1430;
    public static final int STR_SCENE_HOME_HALL_SCRIPT122 = 1431;
    public static final int STR_SCENE_HOME_HALL_SCRIPT123 = 1432;
    public static final int STR_SCENE_HOME_HALL_SCRIPT124 = 1433;
    public static final int STR_SCENE_HOME_HALL_SCRIPT125 = 1434;
    public static final int STR_SCENE_HOME_HALL_SCRIPT126 = 1435;
    public static final int STR_SCENE_HOME_HALL_SCRIPT127 = 1436;
    public static final int STR_SCENE_HOME_HALL_SCRIPT128 = 1437;
    public static final int STR_SCENE_HOME_HALL_SCRIPT129 = 1438;
    public static final int STR_SCENE_HOME_HALL_SCRIPT130 = 1439;
    public static final int STR_SCENE_HOME_HALL_SCRIPT131 = 1440;
    public static final int STR_SCENE_HOME_HALL_SCRIPT132 = 1441;
    public static final int STR_SCENE_HOME_HALL_SCRIPT133 = 1442;
    public static final int STR_SCENE_HOME_HALL_SCRIPT134 = 1443;
    public static final int STR_SCENE_HOME_HALL_SCRIPT135 = 1444;
    public static final int STR_SCENE_HOME_HALL_SCRIPT136 = 1445;
    public static final int STR_SCENE_HOME_HALL_SCRIPT137 = 1446;
    public static final int STR_SCENE_HOME_HALL_SCRIPT138 = 1447;
    public static final int STR_SCENE_HOME_HALL_SCRIPT139 = 1448;
    public static final int STR_SCENE_HOME_HALL_SCRIPT140 = 1449;
    public static final int STR_SCENE_HOME_HALL_SCRIPT141 = 1450;
    public static final int STR_SCENE_HOME_HALL_SCRIPT142 = 1451;
    public static final int STR_SCENE_HOME_HALL_SCRIPT143 = 1452;
    public static final int STR_SCENE_HOME_HALL_SCRIPT144 = 1453;
    public static final int STR_SCENE_HOME_HALL_SCRIPT145 = 1454;
    public static final int STR_SCENE_HOME_HALL_SCRIPT146 = 1455;
    public static final int STR_SCENE_HOME_HALL_SCRIPT147 = 1456;
    public static final int STR_SCENE_HOME_HALL_SCRIPT148 = 1457;
    public static final int STR_SCENE_HOME_HALL_SCRIPT149 = 1458;
    public static final int STR_SCENE_HOME_HALL_SCRIPT150 = 1459;
    public static final int STR_SCENE_HOME_HALL_SCRIPT151 = 1460;
    public static final int STR_SCENE_HOME_HALL_SCRIPT152 = 1461;
    public static final int STR_SCENE_HOME_HALL_SCRIPT153 = 1462;
    public static final int STR_SCENE_HOME_HALL_SCRIPT154 = 1463;
    public static final int STR_SCENE_HOME_HALL_SCRIPT155 = 1464;
    public static final int STR_SCENE_HOME_HALL_SCRIPT156 = 1465;
    public static final int STR_SCENE_HOME_HALL_SCRIPT157 = 1466;

    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT0 = 1309;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT1 = 1310;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT2 = 1311;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT3 = 1312;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT4 = 1313;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT5 = 1314;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT6 = 1315;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT7 = 1316;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT8 = 1317;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT9 = 1318;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT10 = 1319;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT11 = 1320;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT12 = 1321;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT13 = 1322;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT14 = 1323;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT15 = 1324;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT16 = 1325;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT17 = 1326;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT18 = 1327;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT19 = 1328;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT20 = 1329;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT21 = 1330;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT22 = 1331;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT23 = 1332;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT24 = 1333;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT25 = 1334;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT26 = 1335;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT27 = 1336;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT28 = 1337;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT29 = 1338;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT30 = 1339;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT31 = 1340;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT32 = 1341;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT33 = 1342;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT34 = 1343;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT35 = 1344;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT36 = 1345;
    public static final int STR_SCENE_ARISTOCRATDISTRICT_SCRIPT37 = 1346;

    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT0 = 1309;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT1 = 1310;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT2 = 1311;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT3 = 1312;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT4 = 1313;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT5 = 1314;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT6 = 1315;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT7 = 1316;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT8 = 1317;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT9 = 1318;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT10 = 1319;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT11 = 1320;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT12 = 1321;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT13 = 1322;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT14 = 1323;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT15 = 1324;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT16 = 1325;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT17 = 1326;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT18 = 1327;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT19 = 1328;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT20 = 1329;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT21 = 1330;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT22 = 1331;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT23 = 1332;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT24 = 1333;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT25 = 1334;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT26 = 1335;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT27 = 1336;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT28 = 1337;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT29 = 1338;
    public static final int STR_SCENE_JUNGLE_NORTH_SCRIPT30 = 1339;

    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT0 = 1309;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT1 = 1310;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT2 = 1311;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT3 = 1312;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT4 = 1313;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT5 = 1314;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT6 = 1315;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT7 = 1316;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT8 = 1317;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT9 = 1318;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT10 = 1319;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT11 = 1320;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT12 = 1321;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT13 = 1322;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT14 = 1323;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT15 = 1324;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT16 = 1325;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT17 = 1326;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT18 = 1327;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT19 = 1328;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT20 = 1329;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT21 = 1330;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT22 = 1331;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT23 = 1332;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT24 = 1333;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT25 = 1334;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT26 = 1335;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT27 = 1336;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT28 = 1337;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT29 = 1338;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT30 = 1339;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT31 = 1340;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT32 = 1341;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT33 = 1342;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT34 = 1343;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT35 = 1344;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT36 = 1345;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT37 = 1346;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT38 = 1347;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT39 = 1348;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT40 = 1349;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT41 = 1350;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT42 = 1351;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT43 = 1352;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT44 = 1353;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT45 = 1354;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT46 = 1355;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT47 = 1356;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT48 = 1357;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT49 = 1358;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT50 = 1359;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT51 = 1360;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT52 = 1361;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT53 = 1362;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT54 = 1363;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT55 = 1364;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT56 = 1365;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT57 = 1366;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT58 = 1367;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT59 = 1368;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT60 = 1369;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT61 = 1370;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT62 = 1371;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT63 = 1372;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT64 = 1373;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT65 = 1374;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT66 = 1375;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT67 = 1376;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT68 = 1377;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT69 = 1378;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT70 = 1379;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT71 = 1380;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT72 = 1381;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT73 = 1382;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT74 = 1383;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT75 = 1384;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT76 = 1385;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT77 = 1386;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT78 = 1387;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT79 = 1388;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT80 = 1389;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT81 = 1390;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT82 = 1391;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT83 = 1392;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT84 = 1393;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT85 = 1394;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT86 = 1395;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT87 = 1396;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT88 = 1397;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT89 = 1398;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT90 = 1399;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT91 = 1400;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT92 = 1401;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT93 = 1402;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT94 = 1403;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT95 = 1404;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT96 = 1405;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT97 = 1406;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT98 = 1407;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT99 = 1408;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT100 = 1409;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT101 = 1410;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT102 = 1411;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT103 = 1412;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT104 = 1413;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT105 = 1414;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT106 = 1415;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT107 = 1416;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT108 = 1417;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT109 = 1418;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT110 = 1419;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT111 = 1420;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT112 = 1421;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT113 = 1422;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT114 = 1423;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT115 = 1424;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT116 = 1425;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT117 = 1426;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT118 = 1427;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT119 = 1428;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT120 = 1429;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT121 = 1430;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT122 = 1431;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT123 = 1432;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT124 = 1433;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT125 = 1434;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT126 = 1435;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT127 = 1436;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT128 = 1437;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT129 = 1438;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT130 = 1439;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT131 = 1440;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT132 = 1441;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT133 = 1442;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT134 = 1443;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT135 = 1444;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT136 = 1445;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT137 = 1446;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT138 = 1447;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT139 = 1448;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT140 = 1449;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT141 = 1450;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT142 = 1451;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT143 = 1452;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT144 = 1453;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT145 = 1454;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT146 = 1455;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT147 = 1456;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT148 = 1457;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT149 = 1458;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT150 = 1459;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT151 = 1460;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT152 = 1461;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT153 = 1462;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT154 = 1463;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT155 = 1464;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT156 = 1465;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT157 = 1466;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT158 = 1467;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT159 = 1468;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT160 = 1469;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT161 = 1470;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT162 = 1471;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT163 = 1472;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT164 = 1473;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT165 = 1474;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT166 = 1475;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT167 = 1476;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT168 = 1477;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT169 = 1478;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT170 = 1479;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT171 = 1480;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT172 = 1481;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT173 = 1482;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT174 = 1483;
    public static final int STR_SCENE_JUNGLE_SOUTH_SCRIPT175 = 1484;

    public static final int STR_SCENE_TOWER1_SCRIPT0 = 1309;
    public static final int STR_SCENE_TOWER1_SCRIPT1 = 1310;
    public static final int STR_SCENE_TOWER1_SCRIPT2 = 1311;
    public static final int STR_SCENE_TOWER1_SCRIPT3 = 1312;
    public static final int STR_SCENE_TOWER1_SCRIPT4 = 1313;
    public static final int STR_SCENE_TOWER1_SCRIPT5 = 1314;
    public static final int STR_SCENE_TOWER1_SCRIPT6 = 1315;
    public static final int STR_SCENE_TOWER1_SCRIPT7 = 1316;
    public static final int STR_SCENE_TOWER1_SCRIPT8 = 1317;
    public static final int STR_SCENE_TOWER1_SCRIPT9 = 1318;
    public static final int STR_SCENE_TOWER1_SCRIPT10 = 1319;
    public static final int STR_SCENE_TOWER1_SCRIPT11 = 1320;
    public static final int STR_SCENE_TOWER1_SCRIPT12 = 1321;
    public static final int STR_SCENE_TOWER1_SCRIPT13 = 1322;
    public static final int STR_SCENE_TOWER1_SCRIPT14 = 1323;
    public static final int STR_SCENE_TOWER1_SCRIPT15 = 1324;
    public static final int STR_SCENE_TOWER1_SCRIPT16 = 1325;
    public static final int STR_SCENE_TOWER1_SCRIPT17 = 1326;
    public static final int STR_SCENE_TOWER1_SCRIPT18 = 1327;
    public static final int STR_SCENE_TOWER1_SCRIPT19 = 1328;
    public static final int STR_SCENE_TOWER1_SCRIPT20 = 1329;
    public static final int STR_SCENE_TOWER1_SCRIPT21 = 1330;
    public static final int STR_SCENE_TOWER1_SCRIPT22 = 1331;
    public static final int STR_SCENE_TOWER1_SCRIPT23 = 1332;
    public static final int STR_SCENE_TOWER1_SCRIPT24 = 1333;
    public static final int STR_SCENE_TOWER1_SCRIPT25 = 1334;
    public static final int STR_SCENE_TOWER1_SCRIPT26 = 1335;
    public static final int STR_SCENE_TOWER1_SCRIPT27 = 1336;
    public static final int STR_SCENE_TOWER1_SCRIPT28 = 1337;
    public static final int STR_SCENE_TOWER1_SCRIPT29 = 1338;
    public static final int STR_SCENE_TOWER1_SCRIPT30 = 1339;
    public static final int STR_SCENE_TOWER1_SCRIPT31 = 1340;
    public static final int STR_SCENE_TOWER1_SCRIPT32 = 1341;
    public static final int STR_SCENE_TOWER1_SCRIPT33 = 1342;
    public static final int STR_SCENE_TOWER1_SCRIPT34 = 1343;
    public static final int STR_SCENE_TOWER1_SCRIPT35 = 1344;
    public static final int STR_SCENE_TOWER1_SCRIPT36 = 1345;
    public static final int STR_SCENE_TOWER1_SCRIPT37 = 1346;
    public static final int STR_SCENE_TOWER1_SCRIPT38 = 1347;
    public static final int STR_SCENE_TOWER1_SCRIPT39 = 1348;
    public static final int STR_SCENE_TOWER1_SCRIPT40 = 1349;
    public static final int STR_SCENE_TOWER1_SCRIPT41 = 1350;
    public static final int STR_SCENE_TOWER1_SCRIPT42 = 1351;
    public static final int STR_SCENE_TOWER1_SCRIPT43 = 1352;
    public static final int STR_SCENE_TOWER1_SCRIPT44 = 1353;
    public static final int STR_SCENE_TOWER1_SCRIPT45 = 1354;
    public static final int STR_SCENE_TOWER1_SCRIPT46 = 1355;
    public static final int STR_SCENE_TOWER1_SCRIPT47 = 1356;
    public static final int STR_SCENE_TOWER1_SCRIPT48 = 1357;
    public static final int STR_SCENE_TOWER1_SCRIPT49 = 1358;
    public static final int STR_SCENE_TOWER1_SCRIPT50 = 1359;
    public static final int STR_SCENE_TOWER1_SCRIPT51 = 1360;
    public static final int STR_SCENE_TOWER1_SCRIPT52 = 1361;
    public static final int STR_SCENE_TOWER1_SCRIPT53 = 1362;
    public static final int STR_SCENE_TOWER1_SCRIPT54 = 1363;
    public static final int STR_SCENE_TOWER1_SCRIPT55 = 1364;
    public static final int STR_SCENE_TOWER1_SCRIPT56 = 1365;
    public static final int STR_SCENE_TOWER1_SCRIPT57 = 1366;
    public static final int STR_SCENE_TOWER1_SCRIPT58 = 1367;
    public static final int STR_SCENE_TOWER1_SCRIPT59 = 1368;
    public static final int STR_SCENE_TOWER1_SCRIPT60 = 1369;
    public static final int STR_SCENE_TOWER1_SCRIPT61 = 1370;
    public static final int STR_SCENE_TOWER1_SCRIPT62 = 1371;
    public static final int STR_SCENE_TOWER1_SCRIPT63 = 1372;
    public static final int STR_SCENE_TOWER1_SCRIPT64 = 1373;
    public static final int STR_SCENE_TOWER1_SCRIPT65 = 1374;
    public static final int STR_SCENE_TOWER1_SCRIPT66 = 1375;
    public static final int STR_SCENE_TOWER1_SCRIPT67 = 1376;
    public static final int STR_SCENE_TOWER1_SCRIPT68 = 1377;
    public static final int STR_SCENE_TOWER1_SCRIPT69 = 1378;
    public static final int STR_SCENE_TOWER1_SCRIPT70 = 1379;
    public static final int STR_SCENE_TOWER1_SCRIPT71 = 1380;
    public static final int STR_SCENE_TOWER1_SCRIPT72 = 1381;
    public static final int STR_SCENE_TOWER1_SCRIPT73 = 1382;
    public static final int STR_SCENE_TOWER1_SCRIPT74 = 1383;
    public static final int STR_SCENE_TOWER1_SCRIPT75 = 1384;
    public static final int STR_SCENE_TOWER1_SCRIPT76 = 1385;
    public static final int STR_SCENE_TOWER1_SCRIPT77 = 1386;
    public static final int STR_SCENE_TOWER1_SCRIPT78 = 1387;
    public static final int STR_SCENE_TOWER1_SCRIPT79 = 1388;
    public static final int STR_SCENE_TOWER1_SCRIPT80 = 1389;
    public static final int STR_SCENE_TOWER1_SCRIPT81 = 1390;
    public static final int STR_SCENE_TOWER1_SCRIPT82 = 1391;
    public static final int STR_SCENE_TOWER1_SCRIPT83 = 1392;
    public static final int STR_SCENE_TOWER1_SCRIPT84 = 1393;
    public static final int STR_SCENE_TOWER1_SCRIPT85 = 1394;
    public static final int STR_SCENE_TOWER1_SCRIPT86 = 1395;
    public static final int STR_SCENE_TOWER1_SCRIPT87 = 1396;
    public static final int STR_SCENE_TOWER1_SCRIPT88 = 1397;
    public static final int STR_SCENE_TOWER1_SCRIPT89 = 1398;
    public static final int STR_SCENE_TOWER1_SCRIPT90 = 1399;
    public static final int STR_SCENE_TOWER1_SCRIPT91 = 1400;
    public static final int STR_SCENE_TOWER1_SCRIPT92 = 1401;
    public static final int STR_SCENE_TOWER1_SCRIPT93 = 1402;
    public static final int STR_SCENE_TOWER1_SCRIPT94 = 1403;
    public static final int STR_SCENE_TOWER1_SCRIPT95 = 1404;
    public static final int STR_SCENE_TOWER1_SCRIPT96 = 1405;
    public static final int STR_SCENE_TOWER1_SCRIPT97 = 1406;
    public static final int STR_SCENE_TOWER1_SCRIPT98 = 1407;
    public static final int STR_SCENE_TOWER1_SCRIPT99 = 1408;
    public static final int STR_SCENE_TOWER1_SCRIPT100 = 1409;
    public static final int STR_SCENE_TOWER1_SCRIPT101 = 1410;
    public static final int STR_SCENE_TOWER1_SCRIPT102 = 1411;
    public static final int STR_SCENE_TOWER1_SCRIPT103 = 1412;
    public static final int STR_SCENE_TOWER1_SCRIPT104 = 1413;
    public static final int STR_SCENE_TOWER1_SCRIPT105 = 1414;
    public static final int STR_SCENE_TOWER1_SCRIPT106 = 1415;
    public static final int STR_SCENE_TOWER1_SCRIPT107 = 1416;
    public static final int STR_SCENE_TOWER1_SCRIPT108 = 1417;
    public static final int STR_SCENE_TOWER1_SCRIPT109 = 1418;
    public static final int STR_SCENE_TOWER1_SCRIPT110 = 1419;
    public static final int STR_SCENE_TOWER1_SCRIPT111 = 1420;
    public static final int STR_SCENE_TOWER1_SCRIPT112 = 1421;
    public static final int STR_SCENE_TOWER1_SCRIPT113 = 1422;
    public static final int STR_SCENE_TOWER1_SCRIPT114 = 1423;
    public static final int STR_SCENE_TOWER1_SCRIPT115 = 1424;
    public static final int STR_SCENE_TOWER1_SCRIPT116 = 1425;
    public static final int STR_SCENE_TOWER1_SCRIPT117 = 1426;
    public static final int STR_SCENE_TOWER1_SCRIPT118 = 1427;
    public static final int STR_SCENE_TOWER1_SCRIPT119 = 1428;
    public static final int STR_SCENE_TOWER1_SCRIPT120 = 1429;
    public static final int STR_SCENE_TOWER1_SCRIPT121 = 1430;
    public static final int STR_SCENE_TOWER1_SCRIPT122 = 1431;
    public static final int STR_SCENE_TOWER1_SCRIPT123 = 1432;
    public static final int STR_SCENE_TOWER1_SCRIPT124 = 1433;
    public static final int STR_SCENE_TOWER1_SCRIPT125 = 1434;
    public static final int STR_SCENE_TOWER1_SCRIPT126 = 1435;
    public static final int STR_SCENE_TOWER1_SCRIPT127 = 1436;

    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT0 = 1309;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT1 = 1310;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT2 = 1311;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT3 = 1312;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT4 = 1313;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT5 = 1314;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT6 = 1315;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT7 = 1316;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT8 = 1317;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT9 = 1318;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT10 = 1319;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT11 = 1320;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT12 = 1321;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT13 = 1322;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT14 = 1323;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT15 = 1324;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT16 = 1325;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT17 = 1326;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT18 = 1327;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT19 = 1328;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT20 = 1329;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT21 = 1330;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT22 = 1331;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT23 = 1332;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT24 = 1333;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT25 = 1334;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT26 = 1335;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT27 = 1336;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT28 = 1337;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT29 = 1338;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT30 = 1339;
    public static final int STR_SCENE_JUNGLE_SOUTH1_SCRIPT31 = 1340;

    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT0 = 1309;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT1 = 1310;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT2 = 1311;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT3 = 1312;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT4 = 1313;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT5 = 1314;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT6 = 1315;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT7 = 1316;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT8 = 1317;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT9 = 1318;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT10 = 1319;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT11 = 1320;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT12 = 1321;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT13 = 1322;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT14 = 1323;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT15 = 1324;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT16 = 1325;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT17 = 1326;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT18 = 1327;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT19 = 1328;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT20 = 1329;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT21 = 1330;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT22 = 1331;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT23 = 1332;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT24 = 1333;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT25 = 1334;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT26 = 1335;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT27 = 1336;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT28 = 1337;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT29 = 1338;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT30 = 1339;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT31 = 1340;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT32 = 1341;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT33 = 1342;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT34 = 1343;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT35 = 1344;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT36 = 1345;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT37 = 1346;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT38 = 1347;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT39 = 1348;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT40 = 1349;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT41 = 1350;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT42 = 1351;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT43 = 1352;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT44 = 1353;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT45 = 1354;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT46 = 1355;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT47 = 1356;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT48 = 1357;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT49 = 1358;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT50 = 1359;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT51 = 1360;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT52 = 1361;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT53 = 1362;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT54 = 1363;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT55 = 1364;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT56 = 1365;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT57 = 1366;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT58 = 1367;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT59 = 1368;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT60 = 1369;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT61 = 1370;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT62 = 1371;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT63 = 1372;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT64 = 1373;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT65 = 1374;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT66 = 1375;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT67 = 1376;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT68 = 1377;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT69 = 1378;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT70 = 1379;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT71 = 1380;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT72 = 1381;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT73 = 1382;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT74 = 1383;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT75 = 1384;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT76 = 1385;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT77 = 1386;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT78 = 1387;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT79 = 1388;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT80 = 1389;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT81 = 1390;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT82 = 1391;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT83 = 1392;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT84 = 1393;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT85 = 1394;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT86 = 1395;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT87 = 1396;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT88 = 1397;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT89 = 1398;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT90 = 1399;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT91 = 1400;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT92 = 1401;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT93 = 1402;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT94 = 1403;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT95 = 1404;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT96 = 1405;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT97 = 1406;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT98 = 1407;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT99 = 1408;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT100 = 1409;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT101 = 1410;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT102 = 1411;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT103 = 1412;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT104 = 1413;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT105 = 1414;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT106 = 1415;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT107 = 1416;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT108 = 1417;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT109 = 1418;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT110 = 1419;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT111 = 1420;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT112 = 1421;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT113 = 1422;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT114 = 1423;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT115 = 1424;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT116 = 1425;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT117 = 1426;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT118 = 1427;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT119 = 1428;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT120 = 1429;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT121 = 1430;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT122 = 1431;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT123 = 1432;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT124 = 1433;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT125 = 1434;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT126 = 1435;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT127 = 1436;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT128 = 1437;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT129 = 1438;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT130 = 1439;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT131 = 1440;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT132 = 1441;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT133 = 1442;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT134 = 1443;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT135 = 1444;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT136 = 1445;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT137 = 1446;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT138 = 1447;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT139 = 1448;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT140 = 1449;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT141 = 1450;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT142 = 1451;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT143 = 1452;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT144 = 1453;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT145 = 1454;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT146 = 1455;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT147 = 1456;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT148 = 1457;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT149 = 1458;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT150 = 1459;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT151 = 1460;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT152 = 1461;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT153 = 1462;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT154 = 1463;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT155 = 1464;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT156 = 1465;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT157 = 1466;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT158 = 1467;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT159 = 1468;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT160 = 1469;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT161 = 1470;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT162 = 1471;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT163 = 1472;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT164 = 1473;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT165 = 1474;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT166 = 1475;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT167 = 1476;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT168 = 1477;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT169 = 1478;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT170 = 1479;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT171 = 1480;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT172 = 1481;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT173 = 1482;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT174 = 1483;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT175 = 1484;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT176 = 1485;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT177 = 1486;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT178 = 1487;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT179 = 1488;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT180 = 1489;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT181 = 1490;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT182 = 1491;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT183 = 1492;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT184 = 1493;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT185 = 1494;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT186 = 1495;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT187 = 1496;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT188 = 1497;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT189 = 1498;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT190 = 1499;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT191 = 1500;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT192 = 1501;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT193 = 1502;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT194 = 1503;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT195 = 1504;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT196 = 1505;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT197 = 1506;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT198 = 1507;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT199 = 1508;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT200 = 1509;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT201 = 1510;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT202 = 1511;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT203 = 1512;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT204 = 1513;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT205 = 1514;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT206 = 1515;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT207 = 1516;
    public static final int STR_SCENE_ARISTOCRAT_RUIN_SCRIPT208 = 1517;

    public static final int STR_SCENE_CG_ANGERFIRE_SCRIPT0 = 1309;
    public static final int STR_SCENE_CG_ANGERFIRE_SCRIPT1 = 1310;
    public static final int STR_SCENE_CG_ANGERFIRE_SCRIPT2 = 1311;
    public static final int STR_SCENE_CG_ANGERFIRE_SCRIPT3 = 1312;
    public static final int STR_SCENE_CG_ANGERFIRE_SCRIPT4 = 1313;
    public static final int STR_SCENE_CG_ANGERFIRE_SCRIPT5 = 1314;

    public static final int STR_SCENE_MEMORY_SCRIPT0 = 1309;
    public static final int STR_SCENE_MEMORY_SCRIPT1 = 1310;
    public static final int STR_SCENE_MEMORY_SCRIPT2 = 1311;
    public static final int STR_SCENE_MEMORY_SCRIPT3 = 1312;
    public static final int STR_SCENE_MEMORY_SCRIPT4 = 1313;
    public static final int STR_SCENE_MEMORY_SCRIPT5 = 1314;
    public static final int STR_SCENE_MEMORY_SCRIPT6 = 1315;
    public static final int STR_SCENE_MEMORY_SCRIPT7 = 1316;
    public static final int STR_SCENE_MEMORY_SCRIPT8 = 1317;
    public static final int STR_SCENE_MEMORY_SCRIPT9 = 1318;
    public static final int STR_SCENE_MEMORY_SCRIPT10 = 1319;
    public static final int STR_SCENE_MEMORY_SCRIPT11 = 1320;
    public static final int STR_SCENE_MEMORY_SCRIPT12 = 1321;
    public static final int STR_SCENE_MEMORY_SCRIPT13 = 1322;
    public static final int STR_SCENE_MEMORY_SCRIPT14 = 1323;
    public static final int STR_SCENE_MEMORY_SCRIPT15 = 1324;
    public static final int STR_SCENE_MEMORY_SCRIPT16 = 1325;
    public static final int STR_SCENE_MEMORY_SCRIPT17 = 1326;
    public static final int STR_SCENE_MEMORY_SCRIPT18 = 1327;
    public static final int STR_SCENE_MEMORY_SCRIPT19 = 1328;

    public static final int STR_SCENE_MEMORY1_SCRIPT0 = 1309;
    public static final int STR_SCENE_MEMORY1_SCRIPT1 = 1310;
    public static final int STR_SCENE_MEMORY1_SCRIPT2 = 1311;
    public static final int STR_SCENE_MEMORY1_SCRIPT3 = 1312;
    public static final int STR_SCENE_MEMORY1_SCRIPT4 = 1313;
    public static final int STR_SCENE_MEMORY1_SCRIPT5 = 1314;
    public static final int STR_SCENE_MEMORY1_SCRIPT6 = 1315;
    public static final int STR_SCENE_MEMORY1_SCRIPT7 = 1316;
    public static final int STR_SCENE_MEMORY1_SCRIPT8 = 1317;
    public static final int STR_SCENE_MEMORY1_SCRIPT9 = 1318;
    public static final int STR_SCENE_MEMORY1_SCRIPT10 = 1319;
    public static final int STR_SCENE_MEMORY1_SCRIPT11 = 1320;
    public static final int STR_SCENE_MEMORY1_SCRIPT12 = 1321;
    public static final int STR_SCENE_MEMORY1_SCRIPT13 = 1322;

    public static final int STR_SCENE_MEMORY2_SCRIPT0 = 1309;
    public static final int STR_SCENE_MEMORY2_SCRIPT1 = 1310;
    public static final int STR_SCENE_MEMORY2_SCRIPT2 = 1311;
    public static final int STR_SCENE_MEMORY2_SCRIPT3 = 1312;
    public static final int STR_SCENE_MEMORY2_SCRIPT4 = 1313;
    public static final int STR_SCENE_MEMORY2_SCRIPT5 = 1314;
    public static final int STR_SCENE_MEMORY2_SCRIPT6 = 1315;
    public static final int STR_SCENE_MEMORY2_SCRIPT7 = 1316;
    public static final int STR_SCENE_MEMORY2_SCRIPT8 = 1317;
    public static final int STR_SCENE_MEMORY2_SCRIPT9 = 1318;
    public static final int STR_SCENE_MEMORY2_SCRIPT10 = 1319;

    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT0 = 1309;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT1 = 1310;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT2 = 1311;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT3 = 1312;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT4 = 1313;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT5 = 1314;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT6 = 1315;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT7 = 1316;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT8 = 1317;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT9 = 1318;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT10 = 1319;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT11 = 1320;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT12 = 1321;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT13 = 1322;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT14 = 1323;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT15 = 1324;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT16 = 1325;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT17 = 1326;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT18 = 1327;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT19 = 1328;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT20 = 1329;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT21 = 1330;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT22 = 1331;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT23 = 1332;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT24 = 1333;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT25 = 1334;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT26 = 1335;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT27 = 1336;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT28 = 1337;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT29 = 1338;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT30 = 1339;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT31 = 1340;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT32 = 1341;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT33 = 1342;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT34 = 1343;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT35 = 1344;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT36 = 1345;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT37 = 1346;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT38 = 1347;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT39 = 1348;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT40 = 1349;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT41 = 1350;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT42 = 1351;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT43 = 1352;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT44 = 1353;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT45 = 1354;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT46 = 1355;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT47 = 1356;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT48 = 1357;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT49 = 1358;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT50 = 1359;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT51 = 1360;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT52 = 1361;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT53 = 1362;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT54 = 1363;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT55 = 1364;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT56 = 1365;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT57 = 1366;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT58 = 1367;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT59 = 1368;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT60 = 1369;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT61 = 1370;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT62 = 1371;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT63 = 1372;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT64 = 1373;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT65 = 1374;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT66 = 1375;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT67 = 1376;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT68 = 1377;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT69 = 1378;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT70 = 1379;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT71 = 1380;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT72 = 1381;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT73 = 1382;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT74 = 1383;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT75 = 1384;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT76 = 1385;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT77 = 1386;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT78 = 1387;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT79 = 1388;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT80 = 1389;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT81 = 1390;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT82 = 1391;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT83 = 1392;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT84 = 1393;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT85 = 1394;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT86 = 1395;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT87 = 1396;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT88 = 1397;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT89 = 1398;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT90 = 1399;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT91 = 1400;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT92 = 1401;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT93 = 1402;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT94 = 1403;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT95 = 1404;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT96 = 1405;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT97 = 1406;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT98 = 1407;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT99 = 1408;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT100 = 1409;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT101 = 1410;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT102 = 1411;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT103 = 1412;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT104 = 1413;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT105 = 1414;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT106 = 1415;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT107 = 1416;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT108 = 1417;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT109 = 1418;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT110 = 1419;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT111 = 1420;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT112 = 1421;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT113 = 1422;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT114 = 1423;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT115 = 1424;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT116 = 1425;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT117 = 1426;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT118 = 1427;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT119 = 1428;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT120 = 1429;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT121 = 1430;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT122 = 1431;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT123 = 1432;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT124 = 1433;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT125 = 1434;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT126 = 1435;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT127 = 1436;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT128 = 1437;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT129 = 1438;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT130 = 1439;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT131 = 1440;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT132 = 1441;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT133 = 1442;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT134 = 1443;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT135 = 1444;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT136 = 1445;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT137 = 1446;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT138 = 1447;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT139 = 1448;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT140 = 1449;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT141 = 1450;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT142 = 1451;
    public static final int STR_SCENE_MAINYARD_ACTIVE_SCRIPT143 = 1452;

    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT0 = 1309;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT1 = 1310;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT2 = 1311;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT3 = 1312;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT4 = 1313;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT5 = 1314;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT6 = 1315;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT7 = 1316;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT8 = 1317;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT9 = 1318;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT10 = 1319;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT11 = 1320;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT12 = 1321;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT13 = 1322;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT14 = 1323;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT15 = 1324;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT16 = 1325;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT17 = 1326;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT18 = 1327;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT19 = 1328;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT20 = 1329;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT21 = 1330;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT22 = 1331;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT23 = 1332;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT24 = 1333;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT25 = 1334;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT26 = 1335;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT27 = 1336;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT28 = 1337;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT29 = 1338;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT30 = 1339;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT31 = 1340;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT32 = 1341;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT33 = 1342;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT34 = 1343;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT35 = 1344;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT36 = 1345;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT37 = 1346;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT38 = 1347;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT39 = 1348;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT40 = 1349;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT41 = 1350;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT42 = 1351;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT43 = 1352;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT44 = 1353;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT45 = 1354;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT46 = 1355;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT47 = 1356;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT48 = 1357;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT49 = 1358;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT50 = 1359;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT51 = 1360;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT52 = 1361;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT53 = 1362;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT54 = 1363;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT55 = 1364;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT56 = 1365;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT57 = 1366;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT58 = 1367;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT59 = 1368;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT60 = 1369;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT61 = 1370;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT62 = 1371;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT63 = 1372;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT64 = 1373;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT65 = 1374;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT66 = 1375;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT67 = 1376;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT68 = 1377;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT69 = 1378;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT70 = 1379;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT71 = 1380;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT72 = 1381;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT73 = 1382;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT74 = 1383;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT75 = 1384;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT76 = 1385;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT77 = 1386;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT78 = 1387;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT79 = 1388;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT80 = 1389;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT81 = 1390;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT82 = 1391;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT83 = 1392;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT84 = 1393;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT85 = 1394;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT86 = 1395;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT87 = 1396;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT88 = 1397;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT89 = 1398;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT90 = 1399;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT91 = 1400;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT92 = 1401;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT93 = 1402;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT94 = 1403;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT95 = 1404;
    public static final int STR_SCENE_ORPHANAGE_GATEWAY_SCRIPT96 = 1405;

    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT0 = 1309;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT1 = 1310;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT2 = 1311;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT3 = 1312;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT4 = 1313;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT5 = 1314;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT6 = 1315;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT7 = 1316;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT8 = 1317;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT9 = 1318;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT10 = 1319;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT11 = 1320;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT12 = 1321;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT13 = 1322;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT14 = 1323;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT15 = 1324;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT16 = 1325;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT17 = 1326;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT18 = 1327;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT19 = 1328;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT20 = 1329;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT21 = 1330;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT22 = 1331;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT23 = 1332;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT24 = 1333;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT25 = 1334;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT26 = 1335;
    public static final int STR_SCENE_ORPHANAGE_HALL_SCRIPT27 = 1336;

    public static final int STR_SCENE_ORPHANAGE1_SCRIPT0 = 1309;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT1 = 1310;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT2 = 1311;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT3 = 1312;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT4 = 1313;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT5 = 1314;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT6 = 1315;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT7 = 1316;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT8 = 1317;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT9 = 1318;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT10 = 1319;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT11 = 1320;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT12 = 1321;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT13 = 1322;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT14 = 1323;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT15 = 1324;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT16 = 1325;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT17 = 1326;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT18 = 1327;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT19 = 1328;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT20 = 1329;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT21 = 1330;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT22 = 1331;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT23 = 1332;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT24 = 1333;
    public static final int STR_SCENE_ORPHANAGE1_SCRIPT25 = 1334;

    public static final int STR_SCENE_ORPHANAGE2_SCRIPT0 = 1309;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT1 = 1310;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT2 = 1311;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT3 = 1312;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT4 = 1313;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT5 = 1314;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT6 = 1315;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT7 = 1316;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT8 = 1317;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT9 = 1318;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT10 = 1319;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT11 = 1320;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT12 = 1321;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT13 = 1322;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT14 = 1323;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT15 = 1324;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT16 = 1325;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT17 = 1326;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT18 = 1327;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT19 = 1328;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT20 = 1329;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT21 = 1330;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT22 = 1331;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT23 = 1332;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT24 = 1333;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT25 = 1334;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT26 = 1335;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT27 = 1336;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT28 = 1337;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT29 = 1338;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT30 = 1339;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT31 = 1340;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT32 = 1341;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT33 = 1342;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT34 = 1343;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT35 = 1344;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT36 = 1345;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT37 = 1346;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT38 = 1347;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT39 = 1348;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT40 = 1349;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT41 = 1350;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT42 = 1351;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT43 = 1352;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT44 = 1353;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT45 = 1354;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT46 = 1355;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT47 = 1356;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT48 = 1357;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT49 = 1358;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT50 = 1359;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT51 = 1360;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT52 = 1361;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT53 = 1362;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT54 = 1363;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT55 = 1364;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT56 = 1365;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT57 = 1366;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT58 = 1367;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT59 = 1368;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT60 = 1369;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT61 = 1370;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT62 = 1371;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT63 = 1372;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT64 = 1373;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT65 = 1374;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT66 = 1375;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT67 = 1376;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT68 = 1377;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT69 = 1378;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT70 = 1379;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT71 = 1380;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT72 = 1381;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT73 = 1382;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT74 = 1383;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT75 = 1384;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT76 = 1385;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT77 = 1386;
    public static final int STR_SCENE_ORPHANAGE2_SCRIPT78 = 1387;

    public static final int STR_SCENE_SONGJI1_SCRIPT0 = 1309;
    public static final int STR_SCENE_SONGJI1_SCRIPT1 = 1310;
    public static final int STR_SCENE_SONGJI1_SCRIPT2 = 1311;
    public static final int STR_SCENE_SONGJI1_SCRIPT3 = 1312;
    public static final int STR_SCENE_SONGJI1_SCRIPT4 = 1313;
    public static final int STR_SCENE_SONGJI1_SCRIPT5 = 1314;
    public static final int STR_SCENE_SONGJI1_SCRIPT6 = 1315;
    public static final int STR_SCENE_SONGJI1_SCRIPT7 = 1316;
    public static final int STR_SCENE_SONGJI1_SCRIPT8 = 1317;
    public static final int STR_SCENE_SONGJI1_SCRIPT9 = 1318;
    public static final int STR_SCENE_SONGJI1_SCRIPT10 = 1319;
    public static final int STR_SCENE_SONGJI1_SCRIPT11 = 1320;
    public static final int STR_SCENE_SONGJI1_SCRIPT12 = 1321;
    public static final int STR_SCENE_SONGJI1_SCRIPT13 = 1322;
    public static final int STR_SCENE_SONGJI1_SCRIPT14 = 1323;
    public static final int STR_SCENE_SONGJI1_SCRIPT15 = 1324;
    public static final int STR_SCENE_SONGJI1_SCRIPT16 = 1325;
    public static final int STR_SCENE_SONGJI1_SCRIPT17 = 1326;
    public static final int STR_SCENE_SONGJI1_SCRIPT18 = 1327;
    public static final int STR_SCENE_SONGJI1_SCRIPT19 = 1328;
    public static final int STR_SCENE_SONGJI1_SCRIPT20 = 1329;
    public static final int STR_SCENE_SONGJI1_SCRIPT21 = 1330;
    public static final int STR_SCENE_SONGJI1_SCRIPT22 = 1331;
    public static final int STR_SCENE_SONGJI1_SCRIPT23 = 1332;
    public static final int STR_SCENE_SONGJI1_SCRIPT24 = 1333;
    public static final int STR_SCENE_SONGJI1_SCRIPT25 = 1334;
    public static final int STR_SCENE_SONGJI1_SCRIPT26 = 1335;
    public static final int STR_SCENE_SONGJI1_SCRIPT27 = 1336;
    public static final int STR_SCENE_SONGJI1_SCRIPT28 = 1337;
    public static final int STR_SCENE_SONGJI1_SCRIPT29 = 1338;
    public static final int STR_SCENE_SONGJI1_SCRIPT30 = 1339;
    public static final int STR_SCENE_SONGJI1_SCRIPT31 = 1340;
    public static final int STR_SCENE_SONGJI1_SCRIPT32 = 1341;
    public static final int STR_SCENE_SONGJI1_SCRIPT33 = 1342;
    public static final int STR_SCENE_SONGJI1_SCRIPT34 = 1343;
    public static final int STR_SCENE_SONGJI1_SCRIPT35 = 1344;
    public static final int STR_SCENE_SONGJI1_SCRIPT36 = 1345;
    public static final int STR_SCENE_SONGJI1_SCRIPT37 = 1346;
    public static final int STR_SCENE_SONGJI1_SCRIPT38 = 1347;
    public static final int STR_SCENE_SONGJI1_SCRIPT39 = 1348;
    public static final int STR_SCENE_SONGJI1_SCRIPT40 = 1349;
    public static final int STR_SCENE_SONGJI1_SCRIPT41 = 1350;
    public static final int STR_SCENE_SONGJI1_SCRIPT42 = 1351;
    public static final int STR_SCENE_SONGJI1_SCRIPT43 = 1352;
    public static final int STR_SCENE_SONGJI1_SCRIPT44 = 1353;
    public static final int STR_SCENE_SONGJI1_SCRIPT45 = 1354;
    public static final int STR_SCENE_SONGJI1_SCRIPT46 = 1355;
    public static final int STR_SCENE_SONGJI1_SCRIPT47 = 1356;
    public static final int STR_SCENE_SONGJI1_SCRIPT48 = 1357;
    public static final int STR_SCENE_SONGJI1_SCRIPT49 = 1358;
    public static final int STR_SCENE_SONGJI1_SCRIPT50 = 1359;
    public static final int STR_SCENE_SONGJI1_SCRIPT51 = 1360;
    public static final int STR_SCENE_SONGJI1_SCRIPT52 = 1361;
    public static final int STR_SCENE_SONGJI1_SCRIPT53 = 1362;
    public static final int STR_SCENE_SONGJI1_SCRIPT54 = 1363;
    public static final int STR_SCENE_SONGJI1_SCRIPT55 = 1364;
    public static final int STR_SCENE_SONGJI1_SCRIPT56 = 1365;
    public static final int STR_SCENE_SONGJI1_SCRIPT57 = 1366;
    public static final int STR_SCENE_SONGJI1_SCRIPT58 = 1367;
    public static final int STR_SCENE_SONGJI1_SCRIPT59 = 1368;
    public static final int STR_SCENE_SONGJI1_SCRIPT60 = 1369;
    public static final int STR_SCENE_SONGJI1_SCRIPT61 = 1370;
    public static final int STR_SCENE_SONGJI1_SCRIPT62 = 1371;
    public static final int STR_SCENE_SONGJI1_SCRIPT63 = 1372;
    public static final int STR_SCENE_SONGJI1_SCRIPT64 = 1373;
    public static final int STR_SCENE_SONGJI1_SCRIPT65 = 1374;
    public static final int STR_SCENE_SONGJI1_SCRIPT66 = 1375;
    public static final int STR_SCENE_SONGJI1_SCRIPT67 = 1376;
    public static final int STR_SCENE_SONGJI1_SCRIPT68 = 1377;
    public static final int STR_SCENE_SONGJI1_SCRIPT69 = 1378;
    public static final int STR_SCENE_SONGJI1_SCRIPT70 = 1379;
    public static final int STR_SCENE_SONGJI1_SCRIPT71 = 1380;
    public static final int STR_SCENE_SONGJI1_SCRIPT72 = 1381;
    public static final int STR_SCENE_SONGJI1_SCRIPT73 = 1382;
    public static final int STR_SCENE_SONGJI1_SCRIPT74 = 1383;
    public static final int STR_SCENE_SONGJI1_SCRIPT75 = 1384;
    public static final int STR_SCENE_SONGJI1_SCRIPT76 = 1385;
    public static final int STR_SCENE_SONGJI1_SCRIPT77 = 1386;
    public static final int STR_SCENE_SONGJI1_SCRIPT78 = 1387;
    public static final int STR_SCENE_SONGJI1_SCRIPT79 = 1388;
    public static final int STR_SCENE_SONGJI1_SCRIPT80 = 1389;
    public static final int STR_SCENE_SONGJI1_SCRIPT81 = 1390;
    public static final int STR_SCENE_SONGJI1_SCRIPT82 = 1391;
    public static final int STR_SCENE_SONGJI1_SCRIPT83 = 1392;
    public static final int STR_SCENE_SONGJI1_SCRIPT84 = 1393;
    public static final int STR_SCENE_SONGJI1_SCRIPT85 = 1394;
    public static final int STR_SCENE_SONGJI1_SCRIPT86 = 1395;
    public static final int STR_SCENE_SONGJI1_SCRIPT87 = 1396;
    public static final int STR_SCENE_SONGJI1_SCRIPT88 = 1397;
    public static final int STR_SCENE_SONGJI1_SCRIPT89 = 1398;
    public static final int STR_SCENE_SONGJI1_SCRIPT90 = 1399;
    public static final int STR_SCENE_SONGJI1_SCRIPT91 = 1400;
    public static final int STR_SCENE_SONGJI1_SCRIPT92 = 1401;
    public static final int STR_SCENE_SONGJI1_SCRIPT93 = 1402;
    public static final int STR_SCENE_SONGJI1_SCRIPT94 = 1403;
    public static final int STR_SCENE_SONGJI1_SCRIPT95 = 1404;
    public static final int STR_SCENE_SONGJI1_SCRIPT96 = 1405;
    public static final int STR_SCENE_SONGJI1_SCRIPT97 = 1406;
    public static final int STR_SCENE_SONGJI1_SCRIPT98 = 1407;
    public static final int STR_SCENE_SONGJI1_SCRIPT99 = 1408;
    public static final int STR_SCENE_SONGJI1_SCRIPT100 = 1409;
    public static final int STR_SCENE_SONGJI1_SCRIPT101 = 1410;
    public static final int STR_SCENE_SONGJI1_SCRIPT102 = 1411;
    public static final int STR_SCENE_SONGJI1_SCRIPT103 = 1412;
    public static final int STR_SCENE_SONGJI1_SCRIPT104 = 1413;
    public static final int STR_SCENE_SONGJI1_SCRIPT105 = 1414;
    public static final int STR_SCENE_SONGJI1_SCRIPT106 = 1415;
    public static final int STR_SCENE_SONGJI1_SCRIPT107 = 1416;
    public static final int STR_SCENE_SONGJI1_SCRIPT108 = 1417;
    public static final int STR_SCENE_SONGJI1_SCRIPT109 = 1418;
    public static final int STR_SCENE_SONGJI1_SCRIPT110 = 1419;
    public static final int STR_SCENE_SONGJI1_SCRIPT111 = 1420;
    public static final int STR_SCENE_SONGJI1_SCRIPT112 = 1421;
    public static final int STR_SCENE_SONGJI1_SCRIPT113 = 1422;
    public static final int STR_SCENE_SONGJI1_SCRIPT114 = 1423;
    public static final int STR_SCENE_SONGJI1_SCRIPT115 = 1424;
    public static final int STR_SCENE_SONGJI1_SCRIPT116 = 1425;
    public static final int STR_SCENE_SONGJI1_SCRIPT117 = 1426;
    public static final int STR_SCENE_SONGJI1_SCRIPT118 = 1427;
    public static final int STR_SCENE_SONGJI1_SCRIPT119 = 1428;
    public static final int STR_SCENE_SONGJI1_SCRIPT120 = 1429;
    public static final int STR_SCENE_SONGJI1_SCRIPT121 = 1430;
    public static final int STR_SCENE_SONGJI1_SCRIPT122 = 1431;
    public static final int STR_SCENE_SONGJI1_SCRIPT123 = 1432;
    public static final int STR_SCENE_SONGJI1_SCRIPT124 = 1433;
    public static final int STR_SCENE_SONGJI1_SCRIPT125 = 1434;
    public static final int STR_SCENE_SONGJI1_SCRIPT126 = 1435;
    public static final int STR_SCENE_SONGJI1_SCRIPT127 = 1436;
    public static final int STR_SCENE_SONGJI1_SCRIPT128 = 1437;
    public static final int STR_SCENE_SONGJI1_SCRIPT129 = 1438;
    public static final int STR_SCENE_SONGJI1_SCRIPT130 = 1439;
    public static final int STR_SCENE_SONGJI1_SCRIPT131 = 1440;
    public static final int STR_SCENE_SONGJI1_SCRIPT132 = 1441;
    public static final int STR_SCENE_SONGJI1_SCRIPT133 = 1442;
    public static final int STR_SCENE_SONGJI1_SCRIPT134 = 1443;
    public static final int STR_SCENE_SONGJI1_SCRIPT135 = 1444;
    public static final int STR_SCENE_SONGJI1_SCRIPT136 = 1445;
    public static final int STR_SCENE_SONGJI1_SCRIPT137 = 1446;
    public static final int STR_SCENE_SONGJI1_SCRIPT138 = 1447;


    public static final int STR_SCENE_FOREST_CENTER_SCRIPT0 = 1309;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT1 = 1310;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT2 = 1311;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT3 = 1312;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT4 = 1313;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT5 = 1314;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT6 = 1315;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT7 = 1316;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT8 = 1317;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT9 = 1318;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT10 = 1319;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT11 = 1320;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT12 = 1321;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT13 = 1322;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT14 = 1323;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT15 = 1324;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT16 = 1325;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT17 = 1326;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT18 = 1327;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT19 = 1328;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT20 = 1329;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT21 = 1330;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT22 = 1331;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT23 = 1332;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT24 = 1333;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT25 = 1334;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT26 = 1335;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT27 = 1336;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT28 = 1337;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT29 = 1338;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT30 = 1339;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT31 = 1340;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT32 = 1341;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT33 = 1342;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT34 = 1343;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT35 = 1344;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT36 = 1345;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT37 = 1346;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT38 = 1347;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT39 = 1348;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT40 = 1349;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT41 = 1350;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT42 = 1351;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT43 = 1352;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT44 = 1353;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT45 = 1354;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT46 = 1355;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT47 = 1356;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT48 = 1357;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT49 = 1358;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT50 = 1359;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT51 = 1360;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT52 = 1361;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT53 = 1362;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT54 = 1363;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT55 = 1364;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT56 = 1365;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT57 = 1366;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT58 = 1367;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT59 = 1368;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT60 = 1369;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT61 = 1370;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT62 = 1371;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT63 = 1372;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT64 = 1373;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT65 = 1374;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT66 = 1375;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT67 = 1376;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT68 = 1377;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT69 = 1378;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT70 = 1379;
    public static final int STR_SCENE_FOREST_CENTER_SCRIPT71 = 1380;

    public static final int STR_SCENE_FOREST_EAST_SCRIPT0 = 1309;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT1 = 1310;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT2 = 1311;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT3 = 1312;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT4 = 1313;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT5 = 1314;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT6 = 1315;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT7 = 1316;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT8 = 1317;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT9 = 1318;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT10 = 1319;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT11 = 1320;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT12 = 1321;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT13 = 1322;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT14 = 1323;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT15 = 1324;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT16 = 1325;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT17 = 1326;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT18 = 1327;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT19 = 1328;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT20 = 1329;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT21 = 1330;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT22 = 1331;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT23 = 1332;
    public static final int STR_SCENE_FOREST_EAST_SCRIPT24 = 1333;



//@@
//@@../Const/Const_Common.h
    /**
     * 引擎使用的常量
     */

    //----调试----
    final static boolean DEBUG_SHOWACTORACTIVEZONE = !true;
    final static boolean DEBUG_SHOWACTORPOS = !true;
    final static boolean DEBUG_SHOWLOADINFO = !true;
    final static boolean DEBUG_SHOWACTORPHYCOLLIDE = false;
    final static boolean DEBUG_AUTOSAVE = !true;
    static final boolean DEBUG_MEM_CHECK = !true;
    static final boolean DEBUG_DRAW_OUT = !true;
    static final boolean DEBUG_PRINT_OUT = !true;
    static final boolean DEBUG_SCRIPT_DETAIL = !true;
    static final boolean DEBUG_SKIP_BATTLE = !true;
    static final boolean DEBUG_BATTLE_INFO = !true;
    static final boolean DEBUG_LOADINFO = !true;

    //----渲染----
    public static final int TRANS_MIRROR = 2;
    public static final int TRANS_MIRROR_ROT180 = 1;
    public static final int TRANS_MIRROR_ROT270 = 4;
    public static final int TRANS_MIRROR_ROT90 = 7;
    public static final int TRANS_NONE = 0;
    public static final int TRANS_ROT180 = 3;
    public static final int TRANS_ROT270 = 6;
    public static final int TRANS_ROT90 = 5;
//@#switch (FRAME_LIMIT)
//@#{
//@#case "FL_FASTEST":
//@    public static final int FRAME_LIMIT = 25;
//@#break
//@#case "FL_FASTER":
    public static final int FRAME_LIMIT = 20;
//@#break
//@#case "FL_FAST":
//@    public static final int FRAME_LIMIT = 18;
//@#break
//@#case "FL_NORMAL":
//@	public static final int FRAME_LIMIT = 15;
//@#break
//@#case "FL_SLOW":
//@	public static final int FRAME_LIMIT = 12;
//@#break
//@#case "FL_SLOWEST":
//@	public static final int FRAME_LIMIT = 10;
//@#break
//@#default:
//@	public static final int FRAME_LIMIT = 0;
//@#break
//@#}

    //战斗时间轴速度
    public static final int FRAME_LIMIT_NUM = -10;
    public static final int TIME_IN_FRAME = 1000 / FRAME_LIMIT;

    static final int RMS_COMMON_SIZE = 1024;
    static final int RMS_SCENE_SIZE = 1024 * 10;

    //碰撞类型,同物理层碰撞,同actor碰撞,无碰撞
    public final static int COLLIDE_TYPE_NONE = 0;
    public final static int COLLIDE_TYPE_PHY = 1;
    public final static int COLLIDE_TYPE_ACTOR = 2;

    final static int GROUND_DRAWLIST_MAX = 128;
    final static int FLY_DRAWLIST_MAX = 64;
    final static int DRAWLIST_ALL_LEN = GROUND_DRAWLIST_MAX + FLY_DRAWLIST_MAX;

    final static int WND_ACTION_NONE = -1;
    final static int WND_ACTION_PAGEDOWN = 0;
    final static int WND_ACTION_PAGEUP = 1;

    final static int WND_BTN_YES = 1;
    final static int WND_BTN_NO = 0;
    final static int WND_BTN_NONE = -1;

    /**对话框还没有返回值*/
    static final int CONFIRM_STATE_NO = 0;
    static final int CONFIRM_STATE_YES = 1;
    static final int CONFIRM_STATE_MODULE = 2;
    static final int CONFIRM_STATE_HIDE = 3;

    static final int CONFIRM_TYPE_QUITGAME = 0;
    static final int CONFIRM_TYPE_SHOP = 1;
    static final int CONFIRM_TYPE_SL = 2;

    //----计时器----
    final static byte TIMER_STATE_IDLE = 0;
    final static byte TIMER_STATE_START = 1;
    final static byte TIMER_STATE_PAUSE = 3;
    final static byte TIMER_STATE_STOP = 4;

    final static int TIMER_RANGE_USER = 0;
    final static int TIMER_RANGE_SYSTEM = 1;
    final static int TIMER_RANGE_ALL = 2;

    final static int TIMER_POOL_SIZE = 16;
    final static int TIMER_POOL_USER_SIZE = 12;
    final static int TIMER_POOL_SYSTEM_SIZE = 4;
    final static int TIMER_POOL_SYSTEM_START = TIMER_POOL_USER_SIZE;

    //----预读取数据----
    final static int PRELOAD_TILEX = 0;
    final static int PRELOAD_TILEY = 1;
    final static int PRELOAD_MAPID = 2;
    final static int PRELOAD_DIR = 3;
    final static int AMOUNT_PRELOAD = 4;

    //----并行任务----
    final static int PARALLEL_STATE_WAIT = 0;
    final static int PARALLEL_STATE_START = 1;
    final static int PARALLEL_STATE_RUN = 2;
    final static int PARALLEL_STATE_END = 3;

    final static int PARALLEL_WINDOW_START_TIME = 10;

    //----UI脚本----
    final static int UI_SCRIPT_SIZE = 32;
    final static int UI_SCRIPT_PARA = 5;

    //----阵型----
    /**站立、移动偏差*/
    final static int ACTION_DEVIATION = 4;

    //----队伍----
    final static int TEAM_SIZE = AMOUNT_DB_GAMEINFO;
    final static int FORMATION_SIZE = 3;

    //---跟随者数量----
    final static int FOLLOW_TO_TEAM_SIZE = 8;

    //----UI----
    public static final int UTIL_STATE_SHOWING = 2;
    public static final int UTIL_STATE_OVER = 1;
    public static final int UTIL_STATE_OK = 0;

    /**逃跑成功率*/
    public static final int BATTLE_ESCAPE_PERCENT = 50;
    public static final int BATTLE_WEAK = 3;

    public static final int BATTLE_ALLY_MAX = 3;
    public static final int BATTLE_ENEMY_MAX = 5;
    public static final int BATTLE_ACTOR_MAX = BATTLE_ALLY_MAX + BATTLE_ENEMY_MAX;

    public static final int RS_INIT = 0;
    public static final int RS_UI = 1;
    public static final int RS_AIM = 2;
    public static final int RS_LOOT = 3;
    public static final int RS_ESCAPE = 4;
    public static final int RS_ROUNDEND = 5;
    public static final int RS_PARALLEL = 6;
    /**显现自身状态*/
    public static final int RS_READY = 10;
    public static final int RS_PARALLEL_SHOW = 11;
    public static final int RS_ROUNDCOUNT = 12;
//@#if SMS
//@    public static final int RS_ALL_DIE = 14;
//@    public static final int RS_KILL_ALL = 15;
//@#endif
    public static final int BATTLE_RESULT_WIN = 0;
    public static final int BATTLE_RESULT_LOSE = 1;
    public static final int BATTLE_RESULT_ESCAPE = 2;

    /**战斗阵营--英雄*/
    public static final int CAMP_ALLY = 0;
    /**战斗阵营--敌人*/
    public static final int CAMP_FOE = 1;

    /*战斗数据显示*/
    static final int SHOW_DATA_VALUE = 1 << 0;
    static final int SHOW_DATA_PERCENT = 1 << 1;

    /**空闲*/
    public static final short BAS_NORMAL = 0;
    /**死亡*/
    public static final short BAS_DEAD = 1 << 0;

    /**给出伤害方 -- 普通攻击*/
    public static final int BATTLE_ACTION_TYPE_NORMAL = 0;
    /**给出伤害方 -- 使用技能*/
    public static final int BATTLE_ACTION_TYPE_SKILL = 1;
    /**给出伤害方 -- 使用物品*/
    public static final int BATTLE_ACTION_TYPE_ITEM = 2;
    public static final int BATTLE_ACTION_GUARD = 3;
    public static final int BATTLE_ACTION_ESCAPE_SCUU = 4;
    public static final int BATTLE_ACTION_ESCAPE_FAIL = 5;
    public static final int BATTLE_ACTION_ESCAPE_DISABLE = 6;
    /**反弹伤害*/
    public static final int BATTLE_ACTION_TYPE_REFLECT = 7;

    //----镜头----
    final static int CAMERA_STATE_FREE = 0;
    final static int CAMERA_STATE_STAND = 1;
    final static int CAMERA_STATE_ACTOR = 2;
    final static int CAMERA_STATE_MOVE = 3;

    //----振动----
    final static int CAMERA_SHOCK_X = 0;
    final static int CAMERA_SHOCK_Y = 1;
    final static int CAMERA_SHOCK_MX = 2;
    final static int CAMERA_SHOCK_MY = 3;
    final static int CAMERA_SHOCK_VX = 4;
    final static int CAMERA_SHOCK_VY = 5;
    final static int AMOUNT_CAMERA_SHOCK = 6;

    //----矩形----
    final static int RECT_X = 0;
    final static int RECT_Y = 1;
    final static int RECT_W = 2;
    final static int RECT_H = 3;
    final static int AMOUNT_RECT = 4;

    //----特效----
    final static int EFFECT_TAG_UPDATEANIM = 1 << 1;

    final static int EFFECT_TYPE_CURTAIN_OPEN = 0;
    final static int EFFECT_TYPE_CURTAIN_CLOSE = 1;
    final static int EFFECT_TYPE_CURTAIN_MOVIE_ON = 2;
    final static int EFFECT_TYPE_CURTAIN_MOVIE_OFF = 3;

    final static byte RANDOM_EFFECT_TYPE_SQUARE = 0;
    final static byte RANDOM_EFFECT_TYPE_SHADOW = 1;
    final static byte RANDOM_EFFECT_TYPE_CROSS = 2;
    final static byte RANDOM_EFFECT_TYPE_SECTOR = 3;

    final static byte RANDOM_EFFECT_TYPE_SPECIAL0 = 4;
    final static byte RANDOM_EFFECT_TYPE_SPECIAL1 = 5;
    final static byte RANDOM_EFFECT_TYPE_MOVIE = 6;
//@#if USE_ALL_SCREEN_EFFECT
    final static byte AMOUNT_RANDOM_EFFECT = 4;
//@#else
//@    final static byte AMOUNT_RANDOM_EFFECT = 2;
//@#endif

    //----场景中特效转换方式----
    public static final int TRANSLATION_EFFECT_NONE = 0;
    public static final int TRANSLATION_EFFECT_MIRROR = 1;
    public static final int TRANSLATION_EFFECT_TEMP = 1 << 1;

    //----战斗----
    final static int BATTLE_UI_ACTOR_COUNT = 8;
    final static int BATTLE_SCRIPT_SIZE = 64;
    final static int BATTLE_LIMIT_ACTIONTYPE = 0;
    final static int BATTLE_LIMIT_ACTIONID = 1;
    final static int BATTLE_LIMIT_AIM = 2;
    final static int BATTLE_LIMIT_SCRIPT_ID = 3;
    final static int AMOUNT_BATTLE_LIMIT = 4;

    final static int UI_CONTROL_MANUAL = 0;
    final static int UI_CONTROL_AI = 1;
    final static int UI_CONTROL_SCRIPT = 2;
    final static int UI_CONTROL_NONE = 3;

    public static final byte SMOOTH_LEFT = 0;
    public static final byte SMOOTH_RIGHT = 1;
    public static final byte SMOOTH_UP = 2;
    public static final byte SMOOTH_DOWN = 3;
    public static final int SEARCH_RANGE = 4;
    public static final int LANDING_SMOOTH_RANGE = 3;

    public static final int ACTOR_FLY_HEIGHT = 10;
    public static final int HERO_DISTANCE_IN_TEAM = 24;
    public static final int SYNCH_COLLIDE_DISTANCE = 150;

    final static byte ACTOR_MOVE_TYPE_STAND = 0;
    final static byte ACTOR_MOVE_TYPE_TRAVELLINE = 1;
    final static byte ACTOR_MOVE_TYPE_AUTOMOVE = 2;
    final static byte ACTOR_MOVE_TYPE_SYNCH = 3;
    final static byte ACTOR_MOVE_TYPE_RANGERANDOM = 4;
    final static byte ACTOR_MOVE_TYPE_RANGENPC = 5;

    final static byte DAMAGE_RANDOM_RANGE = 10;
    final static byte DAMAGE_RANDOM_MIN = 100 - DAMAGE_RANDOM_RANGE;
    final static byte DAMAGE_RANDOM_MAX = 100 + DAMAGE_RANDOM_RANGE;

    //技能“灭”的ID
    final static byte SPECIAL_SKILL_ID = 56;

    //与ACTOR对话 第一个动画ID
    public final static int ACTION_UP = 0;

    //----脚本状态----
    final static int SCRIPT_STATE_DEACTIVATED = -1;
    final static int SCRIPT_STATE_READY = 0;
    final static int SCRIPT_STATE_WAIT_TIMER = 1;
    final static int SCRIPT_STATE_WAIT_ACTOR_PARA = 2;
    final static int SCRIPT_STATE_WAIT_CAMERA = 3;
    final static int SCRIPT_STATE_WAIT_KEY = 4;
    final static int SCRIPT_STATE_PAUSE = 5;
    final static int SCRIPT_STATE_END = 6;
    final static int SCRIPT_STATE_COLLIDE_END = 7;

    //----脚本触发条件----
    final static int CONDITION_AUTO = 0;
    final static int CONDITION_KEY = 1;
    final static int CONDITION_COLLIDE = 2;
    final static int CONDITION_TIMER = 3;
    final static int CONDITION_INIT = 4;

    //----脚本重复----
    final static int SCRIPT_REPEAT_ONCE = 0;
    final static int SCRIPT_REPEAT_ALWAYS = 1;

    final static int UI_CLOSE_PARENT = -1;//父窗口 关闭
    final static int UI_CLOSE_ACTIVE = 0;//Active窗口 关闭

    final static int UI_AUTO_PAUSE = 0;
    final static int UI_AUTO_CURSOR = 1;
    final static int UI_AUTO_CONFIRM = 2;
    final static int UI_AUTO_RETURN = 3;
    final static int UI_AUTO_RESET = 4;
    final static int UI_AUTO_CONTROL = 5;
    final static int UI_AUTO_CLOSE_ACTIVE = 6;



    //----颜色----
    public final static int COLOR_NONE = -1;
    public final static int COLOR_WHITE = 0xFFFFFF;
    public final static int COLOR_BLACK = 0x000000;
    public static final int COLOR_HOARY = 0xC6B38D; //灰白色
    public final static int COLOR_RED = 0xFF0000;
    public final static int COLOR_DARK_RED = 0x5a1b0c;
    public final static int COLOR_BLOOD_RED = 0x9c0e0c;
    public final static int COLOR_GREEN = 0x00FF00;
    public final static int COLOR_DARK_GREEN = 0x194e11;
    public final static int COLOR_EXP_GREEN0 = 0x3f7a39;
    public final static int COLOR_EXP_GREEN1 = 0xcafe92;
    public final static int COLOR_LIGHT_GREEN = 0xe2ffb2;
    public final static int COLOR_BLUE = 0x0000FF;
    public final static int COLOR_GREEN_BLUE = 0x359797;
    public final static int COLOR_SHADOW_BLUE = 0x0794a3;
    public final static int COLOR_MP_BLUE = 0x84fefb;
    public final static int COLOR_ROSE = 0xFEA7D0;
    public static final int COLOR_PINK = 0xF9E3DF;
    public static final int COLOR_DARK_PINK = 0xcf7379;
    public static final int COLOR_PURPLE = 0x915FCD;
    public static final int COLOR_PINK_MENU = 0xFEA6D0;
    public static final int COLOR_YELLOW = 0xFFDD12;
    public static final int COLOR_DARK_BLUE = 0x144f6b;
    public static final int COLOR_LIGHT_BLUE = 0xdde9ff;
    public static final int COLOR_LIGHT_BROWN = 0xc9995c;
    public static final int COLOR_BROWN = 0x976523;
    public static final int COLOR_DARK_BROWN = 0x583c3c;
    public static final int COLOR_BROWN_SHADOW = 0x74562d;
    public static final int COLOR_LIGHT_GRAY = 0xc6cfce;
    public static final int COLOR_GRAY = 0x929292;
    public static final int COLOR_DARK_GRAY = 0x708eae;
    public static final int COLOR_BLUE_GRAY = 0xb5ccd4;
    public static final int COLOR_BEIGE = 0xffe5b3;
    public static final int COLOR_EARTH = 0xe9de93;
    public static final int COLOR_ORANGE = 0xfdb073;
    public static final int COLOR_LIGHT_RED = 0xf28f86;
    public static final int COLOR_LIGHTYELLOW = 0xF6E09D;
    public static final int COLOR_UI_EDGE = 0xb2553f;
    public static final int COLOR_ALPHA = 0x99000000;
    public static final int COLOR_LUCENCY = -1;  // 没有颜色
    public static final int COLOR_BACK_BLUE = 0x6695BF;  // 背景蓝色
    public static final int COLOR_LIGHT_BLUER = 0x44d0e8;
    public static final int COLOR_DEEP_BLUER = 0x226688;
    public static final int COLOR_GRAY_BLUER = 0x5ddcdf;
    public static final int COLOR_FONT_BLUER = 0x0f4669;
    public static final int COLOR_HP1_BLUER = 0x25cdcd;
    public static final int COLOR_HP2_BLUER = 0x03442f;

    static final int[] COLOR_BAR_EDGE =
            { //@in X:\RPG22_CMCC\trunk\SourceCode\Const\Const_Common.h
            COLOR_DARK_RED, COLOR_DARK_BLUE, COLOR_DARK_GREEN};
    static final int[] COLOR_BAR_SHADOW =
            {
            COLOR_BLOOD_RED, COLOR_SHADOW_BLUE, COLOR_EXP_GREEN0};
    static final int[] COLOR_BAR_LIGHT =
            {
            COLOR_LIGHT_RED, COLOR_MP_BLUE, COLOR_EXP_GREEN1};

    static final int[] COLOR_DB =
                                  {
                                  COLOR_BLACK, COLOR_WHITE, COLOR_RED};

    //----WndSkin----
    public static final int CONNER_NONE = -1;
    public static final int HORIZ_NONE = -1;
    public static final int VERTI_NONE = -1;

    //----数据----
    public final static int NUMBER_BYTE = 0;
    public final static int NUMBER_SHORT = 1;
    public final static int NUMBER_INT = 2;
    public final static int NUMBER_UNSIGNED_BYTE = 3;
    public final static int NUMBER_UNSIGNED_SHORT = 4;

    public final static int NUMBER_SIZE_BYTE = 1;
    public final static int NUMBER_SIZE_SHORT = 2;
    public final static int NUMBER_SIZE_INT = 4;

    final static int MAPTILEFLIP_X = 1 << 13;
    final static int MAPTILEFLIP_Y = 1 << 14;

    //---- ui ----
    final static int UI_DATA_SIZE = 64;

    static final int UI_TYPE_DIALOG = 0;
    static final int UI_TYPE_MESSAGEBOX = 1;
    static final int UI_TYPE_CHOOSE = 2;
    static final int UI_TYPE_BOARD = 3;

    static final int WND_STATE_RETURN = -1;
    static final int WND_STATE_WAIT = 0;
    static final int WND_STATE_YES = 1;
    static final int WND_STATE_NO = 2;
    static final int WND_STATE_CANCEL = 3;
    static final int WND_STATE_PAGEUP = 4;
    static final int WND_STATE_PAGEDOWN = 5;
    static final int WND_STATE_CYCEL = 6;

    final static byte T_FALSE = 0;
    final static byte T_TRUE = 1;

    public static final byte DIRECTION_OVERLAP = -1;
    public static final byte DIRECTION_UP = 0;
    public static final byte DIRECTION_RIGHT = 1;
    public static final byte DIRECTION_DOWN = 2;
    public static final byte DIRECTION_LEFT = 3;

    final static int LOAD_DATA_NONE = 0;
    final static int LOAD_DATA_GLOBE = 1 << 0;
    final static int LOAD_DATA_COMMON = 1 << 1;
    final static int LOAD_DATA_OTHER = 1 << 2;

    final static int SCENELOAD_TYPE_NEW = 0;
    final static int SCENELOAD_TYPE_SWITCH = 1;
    final static int SCENELOAD_TYPE_NORMAL = 2;
    final static int SCENELOAD_TYPE_LOAD = 3;

    final static int DEFAULT_BATTLR_ACTOR_AP = 100;
    //更改飞行时碰撞当前坐标  策划不改的程序得改
    final static int ACTOR_NEW_COORDINATE_OFFSET_X = 6;
    final static int ACTOR_NEW_COORDINATE_OFFSET_Y = 6;

//@@

//@#switch (platform)
//@#{
//@#case "NOKIA_N5800":
//@#include "../Const/Const_Position_N5800.h"
//@#include "../Const/Const_Project_N5800.h"
//@#break
//@#case "NOKIA_N73":
//@@../Const/Const_Position_N73.h
    // 屏幕尺寸与公共信息
    public final static int SCREEN_WIDTH = 240;
    public final static int SCREEN_HEIGHT = 320;
    public final static int SCREEN_WIDTH_HALF = SCREEN_WIDTH>>1;
    public final static int SCREEN_HEIGHT_HALF = SCREEN_HEIGHT>>1;

    public static final int VIEW_WIDTH = SCREEN_WIDTH;
    public static final int VIEW_WIDTH_HALF = VIEW_WIDTH>>1;
    public static final int VIEW_TOP = 0;
    public static final int ViEW_HEIGHT = SCREEN_HEIGHT;
    public final static int VIEW_LEFT = 0;
    public final static int VIEW_RIGHT = SCREEN_WIDTH;
    public final static int VIEW_BOTTOM = SCREEN_HEIGHT;
    public final static int VIEW_HEIGHT = VIEW_BOTTOM - VIEW_TOP;
    public final static int VIEW_HEIGHT_HALF = (VIEW_HEIGHT>>1);
    public final static int VIEW_OFFSET_X = 0;
    public final static int BATTLE_UI_HEIGHT = 68;

    public final static int PREVIEW_IMG_Y = 30;
    public final static int PREVIEW_LOGO_Y = 170;
    public final static int MACHINE_IMG_X = SCREEN_WIDTH_HALF - (176>>1);
    public final static int MACHINE_IMG_Y = SCREEN_HEIGHT_HALF - (208>>1);
    public final static int MACHINE_SLOT_X0 = MACHINE_IMG_X + (176>>1) - 52;
    public final static int MACHINE_SLOT_X1 = MACHINE_SLOT_X0 + 52;
    public final static int MACHINE_SLOT_X2 = MACHINE_SLOT_X1 + 52;
    public final static int MACHINE_SLOT_Y = MACHINE_IMG_Y + 100;
    public final static int MACHINE_SPIN_X0 = MACHINE_SLOT_X0 - 26;
    public final static int MACHINE_SPIN_X1 = MACHINE_SLOT_X1 - 26;
    public final static int MACHINE_SPIN_X2 = MACHINE_SLOT_X2 - 26;
    public final static int MACHINE_SPIN_Y = MACHINE_IMG_Y + 72;

    final static int RANDOM_TYPE_0_RANGE = 40;
    final static int RANDOM_TYPE_0_ROW = SCREEN_WIDTH / RANDOM_TYPE_0_RANGE;
    final static int RANDOM_TYPE_0_COL = SCREEN_HEIGHT / RANDOM_TYPE_0_RANGE;
    final static int RANDOM_TYPE_1_RANGE1 = 15;
    final static int RANDOM_TYPE_1_RANGE2 = 10;
    final static int RANDOM_TYPE_1_RANGE3 = 5;
    final static int RANDOM_TYPE_1_SPACE1 = 8;
    final static int RANDOM_TYPE_1_SPACE2 = (RANDOM_TYPE_1_SPACE1<<1) + 13;
    final static int RANDOM_TYPE_1_SPACE3 = RANDOM_TYPE_1_SPACE2 + 14;
    final static int RANDOM_TYPE_2_RANGE = 32;
    final static int RANDOM_TYPE_2_ROW = SCREEN_HEIGHT / RANDOM_TYPE_2_RANGE;
    final static int RANDOM_TYPE_3_RANGE = 400;
    final static int RANDOM_TYPE_3_RANGE_HALF = RANDOM_TYPE_3_RANGE>>1;

    final static int EFFECT_RANGE_PERFRAME_0 = 4;
    final static int EFFECT_RANGE_PERFRAME_1 = 10;
    final static int EFFECT_RANGE_PERFRAME_2 = 10;
    final static int EFFECT_RANGE_PERFRAME_3 = 15;
    final static int EFFECT_RANGE_PERFRAME_SP0 = 8;
    final static int EFFECT_RANGE_PERFRAME_SP1 = SCREEN_WIDTH;
    final static int EFFECT_RANGE_PERFRAME_MOVIE = 15;

    final static int ICON_X_OFFSET = -15;
    final static int ICON_Y_OFFSET = 11;

    //----游戏主菜单-------
    final static int IGM_MIAN_MENU_STR_Y = 290;
    final static int IGM_MIAN_MENU_ARROW_Y = IGM_MIAN_MENU_STR_Y - 5;
    final static int IGM_MIAN_LEFT_ARROW_X = SCREEN_WIDTH_HALF - 45;
    final static int IGM_MIAN_RIGHT_ARROW_X = SCREEN_WIDTH_HALF + 35;
    //----步长----
    final static short ACTOR_STEP_LEN = 6;
    final static short ACTOR_TRANSPORT_STEP_LEN = ACTOR_STEP_LEN << 1;

    //----场景人物----
    public static final int ENEMY_SEARCH_RANGE = 50;
    public static final int ENEMY_SEARCH_SPEED = 2;

    //----任务飞马提示-----
    public static final int PROMPT_X = SCREEN_WIDTH_HALF;
    public static final int PROMPT_Y = SCREEN_HEIGHT - 5;

    final static int GAME_OVER_NUM_PERLINE = 8;
    final static int LOADING_NUM_PERLINE = 9;

    //------MainMenu---------------
    final static int MAINMENU_SHOW_ITEM = 1;
//@#if MORE_GAME_LINK
    //----更多游戏链接-----
    public static final int MORE_GAME_TITLE_Y = 5;
    public static final int MORE_GAME_CONTENT_OFF_Y = MORE_GAME_TITLE_Y + 5;
    public static final int MORE_GAME_LINE = 12;
    //--------
    final static int MI_MAINMENU_NEW = 0;
    final static int MI_MAINMENU_LOAD = 1;
//@#if QQ_FORUM
//@    final static int MI_MAINMENU_OPTION = 2;
//@    final static int MI_MAINMENU_FORUM = 3;
//@    final static int MI_MAINMENU_TOP = 4;
//@    final static int MI_MAINMENU_HELP = 5;
//@    final static int MI_MAINMENU_ABOUT = 6;
//@    final static int MI_MAINMENU_MORE = 7;
//@    final static int MI_MAINMENU_EXIT = 8;
//@    final static int AMOUNT_MAINMENU_ITEM = 9;
//@#else
//@#if Channel == "GF"
//@#switch (GF_PACKAGE)
//@#{
//@#case "CONNECT":
    final static int MI_MAINMENU_GAMEZONE = 2;
    final static int MI_MAINMENU_RECOMMEND = 3;
    final static int MI_MAINMENU_MORE = 4;
    final static int MI_MAINMENU_OPTION = 5;
    final static int MI_MAINMENU_HELP = 6;
    final static int MI_MAINMENU_ABOUT = 7;
    final static int MI_MAINMENU_EXIT = 8;
    final static int AMOUNT_MAINMENU_ITEM = 9;
//@#break
//@#case "SMS":
//@    final static int MI_MAINMENU_RECOMMEND = 2;
//@    final static int MI_MAINMENU_MORE = 3;
//@    final static int MI_MAINMENU_HELP = 4;
//@    final static int MI_MAINMENU_OPTION = 5;
//@    final static int MI_MAINMENU_ABOUT = 6;
//@    final static int MI_MAINMENU_EXIT = 7;
//@    final static int AMOUNT_MAINMENU_ITEM = 8;
//@#break
//@#default:
//@    final static int MI_MAINMENU_MORE = 2;
//@    final static int MI_MAINMENU_OPTION = 3;
//@    final static int MI_MAINMENU_HELP = 4;
//@    final static int MI_MAINMENU_ABOUT = 5;
//@    final static int MI_MAINMENU_EXIT = 6;
//@    final static int AMOUNT_MAINMENU_ITEM = 7;
//@#}
//@#else
//@    final static int MI_MAINMENU_OPTION = 2;
//@    final static int MI_MAINMENU_HELP = 3;
//@    final static int MI_MAINMENU_ABOUT = 4;
//@    final static int MI_MAINMENU_MORE = 5;
//@    final static int MI_MAINMENU_EXIT = 6;
//@    final static int AMOUNT_MAINMENU_ITEM = 7;
//@#endif
//@#endif
//@#else
//@    //----IGM----
//@    final static int MI_MAINMENU_NEW = 0;
//@    final static int MI_MAINMENU_LOAD = 1;
//@#if Channel == "GF"
//@#switch (GF_PACKAGE)
//@#{
//@#case "CONNECT":
//@    final static int MI_MAINMENU_GAMEZONE = 2;
//@    final static int MI_MAINMENU_RECOMMEND = 3;
//@    final static int MI_MAINMENU_OPTION = 4;
//@    final static int MI_MAINMENU_HELP = 5;
//@    final static int MI_MAINMENU_ABOUT = 6;
//@    final static int MI_MAINMENU_EXIT = 7;
//@    final static int AMOUNT_MAINMENU_ITEM = 8;
//@#break
//@#case "SMS":
//@    final static int MI_MAINMENU_RECOMMEND = 2;
//@    final static int MI_MAINMENU_HELP = 3;
//@    final static int MI_MAINMENU_OPTION = 4;
//@    final static int MI_MAINMENU_ABOUT = 5;
//@    final static int MI_MAINMENU_EXIT = 6;
//@    final static int AMOUNT_MAINMENU_ITEM = 7;
//@#break
//@#default:
//@    final static int MI_MAINMENU_HELP = 2;
//@    final static int MI_MAINMENU_ABOUT = 3;
//@    final static int MI_MAINMENU_OPTION = 4;
//@    final static int MI_MAINMENU_EXIT = 5;
//@    final static int AMOUNT_MAINMENU_ITEM = 6;
//@#}
//@#else
//@    final static int MI_MAINMENU_OPTION = 2;
//@    final static int MI_MAINMENU_HELP = 3;
//@    final static int MI_MAINMENU_ABOUT = 4;
//@//    final static int MI_MAINMENU_MORE = 5;
//@    final static int MI_MAINMENU_EXIT = 5;
//@    final static int AMOUNT_MAINMENU_ITEM = 6;
//@#endif
//@#endif
    public final static int IGM_GAMEMAIN_COLOR = 0x24a5a1;
    public final static int IGM_MENUTIP_COLOR = 0x0c6265;
    public final static int IGM_CONNER3_COLOR = 0x6b4135;
    public final static int IGM_MENUMISSION_COLOR = 0x610096;
    public final static int IGM_BUY_OR_SELL_W = 80;
    public final static int IGM_BUY_OR_SELL_H = 70;
    public final static int IGM_BUY_OR_SELL_X = SCREEN_WIDTH_HALF - (IGM_BUY_OR_SELL_W>>1);
    public final static int IGM_BUY_OR_SELL_Y = 110;
    public final static int IGM_BUY_ITEM_X = 115;
    public final static int IGM_BUY_ITEM_Y = 119;
    public final static int IGM_SELL_ITEM_X = 115;
    public final static int IGM_SELL_ITEM_Y = 139;
    public final static int IGM_EXIT_SHOP_X = 115;
    public final static int IGM_EXIT_SHOP_Y = 159;
    public final static int IGM_BUY_OR_SELL_HAND_X = 93;
    public final static int IGM_BUY_OR_SELL_HAND_SPACE = 20;
    public final static int IGM_RECT_W = 176;
    public final static int IGM_RECT_H = 173;
    public final static int IGM_RECT_X = SCREEN_WIDTH_HALF - (IGM_RECT_W>>1);
    public final static int IGM_RECT_Y = SCREEN_HEIGHT_HALF - (IGM_RECT_H>>1);
    public final static int IGM_RECT_STR_OFF_Y = 2;
    public final static int IGM_RECT_NUM_OFF_Y = 7;

    //----BATTLE UI----
    public final static int BATTLE_SKILL_RECT_X = SCREEN_WIDTH_HALF - (IGM_RECT_W>>1);
    public final static int BATTLE_SKILL_RECT_Y = SCREEN_HEIGHT_HALF - (IGM_RECT_H>>1) - 36;

    public final static int BATTLE_SKILL_TITLE_X = SCREEN_WIDTH_HALF;
    public final static int BATTLE_SKILL_TITLE_Y = SCREEN_HEIGHT_HALF - 104 - 36;

    public final static int BATTLE_SKILL_PORTRAIT_W = 54;
    public final static int BATTLE_SKILL_PORTRAIT_H = 70;
    public final static int BATTLE_SKILL_PORTRAIT_X = BATTLE_SKILL_TITLE_X + 6;
    public final static int BATTLE_SKILL_PORTRAIT_Y = BATTLE_SKILL_TITLE_Y + 24;

    public final static int BATTLE_SKILL_PORTRAITIMG_X = BATTLE_SKILL_PORTRAIT_X + 3;
    public final static int BATTLE_SKILL_PORTRAITIMG_Y = BATTLE_SKILL_PORTRAIT_Y + 3;
    public final static int BATTLE_SKILL_PORTRAITIMG_W = BATTLE_SKILL_PORTRAIT_W - 6;
    public final static int BATTLE_SKILL_PORTRAITIMG_H = BATTLE_SKILL_PORTRAIT_H - 6;

    //--------------战斗伤害--------------------
    //伤害信息总偏移
    final static int DAMAGE_INFO_ALLY_X = 30;
    final static int DAMAGE_INFO_FOE_X = -20;
    final static int DAMAGE_INFO_OFFSET_Y = 60;
    //普通攻击伤害数字偏移
    final static int DAMAGE_NORMAL_OFFSET_X = 0;
    final static int DAMAGE_NORMAL_OFFSET_Y = 0;
    //暴击文字偏移
    final static int DAMAGE_CH_STR_OFFSET_X = 35;
    final static int DAMAGE_CH_STR_OFFSET_Y = 0;
    //暴击数字偏移
    final static int DAMAGE_CH_NUM_OFFSET_X = 10;
    final static int DAMAGE_CH_NUM_OFFSET_Y = 0;
    //回HP数字偏移
    final static int DAMAGE_HP_NUM_OFFSET_X = 0;
    final static int DAMAGE_HP_NUM_OFFSET_Y = 10;
    //回MP数字偏移
    final static int DAMAGE_MP_NUM_OFFSET_X = 0;
    final static int DAMAGE_MP_NUM_OFFSET_Y = 0;
    //技能伤害数字偏移
    final static int DAMAGE_SKILL_NUM_OFFSET_X = 0;
    final static int DAMAGE_SKILL_NUM_OFFSET_Y = 0;
    //MISS信息偏移
    final static int DAMAGE_MISS_OFFSET_X = 10;
    final static int DAMAGE_MISS_OFFSET_Y = 0;
    //自动战斗信息偏移
    final static int DAMAGE_AUTO_OFFSET_X = 0;
    final static int DAMAGE_AUTO_OFFSET_Y = 0;


    //----ui name----
    public final static int UI_NAME_RECT_W = 70;
    public final static int UI_NAME_RECT_H = 22;
    public final static int UI_NAME_STR_Y_OFFSET = 1;

    //----ui level----
    public final static int UI_LV_RECT_W = 55;
    public final static int UI_LV_SKILL_W = 70;
    public final static int UI_LV_RECT_H = 20;

    //----ui skill----
    public final static int UI_SKILL_NAME_OFFSETX = 62;
    public final static int UI_SKILL_NAME_OFFSETY = 8;

    //----ui dialog----
    public final static int UI_DEFAULT_DIALOG_POSH = 0;
    public final static int UI_DEFAULT_DIALOG_POSV = 5;
    public final static int UI_DEFAULT_DIALOG_HEAD = -1;

    public final static int UI_SKILL_PORTAIT_OFFSETX = 2 + 4;
    public final static int UI_SKILL_PORTAIT_OFFSETY = 6 + 1;

    public final static int UI_SKILL_CLIP_OFFSETX = 3;
    public final static int UI_SKILL_CLIP_OFFSETY = 3;
    public final static int UI_SKILL_CLIP_OFFSETW = 48;
    public final static int UI_SKILL_CLIP_OFFSETH = 64;

    public final static int UI_SKILL_LV_OFFSETX = 62;
    public final static int UI_SKILL_LV_OFFSETY = 30;

    public final static int UI_SKILL_LIST_OFFSETX = 6;
    public final static int UI_SKILL_LIST_OFFSETY = 76;

    public final static int UI_SKILL_LIST_NAME_OFFSETX = UI_SKILL_LIST_OFFSETX + 5;
    public final static int UI_SKILL_LIST_NAME_OFFSETY = UI_SKILL_LIST_OFFSETY + 2;

    public final static int UI_SKILL_LIST_COST_OFFSETX = UI_SKILL_LIST_OFFSETX + 155;
    public final static int UI_SKILL_LIST_COST_OFFSETY = UI_SKILL_LIST_OFFSETY + 9;

    public final static int UI_SKILL_DESC_OFFSETX = 90;
    public final static int UI_SKILL_DESC_OFFSETY = 134 - 6;

    public final static int UI_SKILL_SK_OFFSETX = 90 - 76 - 6;
    public final static int UI_SKILL_SK_OFFSETY = 134 + 35;
    public final static int UI_SKILL_SKSTR_OFFSETY = 134 + 35 + 3;

    public final static int UI_SKILL_ARROW_OFFSETX = 80;
    public final static int UI_SKILL_ARROW_OFFSETY0 = 85;
    public final static int UI_SKILL_ARROW_OFFSETY1 = 135;

    public final static int UI_SKILL_EXP_OFFSETX = 100 - 39 + 1;
    public final static int UI_SKILL_EXP_OFFSETY = 51;

    //----ui softkey----
    public final static int UI_SOFTKEY_BG_X = IGM_RECT_X;
    public final static int UI_IGMSK_X = IGM_RECT_X + 13;
    public final static int UI_IGMSK_OFFSETX = 122;
    public final static int UI_IGMSK_Y = IGM_RECT_Y + 175;

    //----ui exp----
    public final static int UI_EXP_DASH_OFFSETX = 62;
    public final static int UI_EXP_DASH_OFFSETY = 7;

    public final static int UI_EXP_NUM0_OFFSETX = UI_EXP_DASH_OFFSETX - 1;
    public final static int UI_EXP_NUM0_OFFSETY = UI_EXP_DASH_OFFSETY;

    public final static int UI_EXP_NUM1_OFFSETX = UI_EXP_DASH_OFFSETX + 3;
    public final static int UI_EXP_NUM1_OFFSETY = UI_EXP_DASH_OFFSETY;

    public final static int UI_EXP_LINE_OFFSETX = 0;
    public final static int UI_EXP_LINE_OFFSETY = 25;

    //----ui battle skill----
    public final static int UI_BATTLESKILL_PORTAIT_OFFSETX = 2 + 4;
    public final static int UI_BATTLESKILL_PORTAIT_OFFSETY = 6 + 1 + 18;

    public final static int UI_SOFTKEY_BG_Y = IGM_RECT_Y + 176 - 5;

    public final static int WND_RECT_W = 176;
    public final static int WND_RECT_H = 208;
    public final static int WND_RECT_X = SCREEN_WIDTH_HALF - (WND_RECT_W>>1);
    public final static int WND_RECT_Y = SCREEN_HEIGHT_HALF - (WND_RECT_H>>1);

    //----IGM----
    public final static int IGM_X = WND_RECT_X;
    public final static int IGM_Y = WND_RECT_Y;
    public final static int IGM_W = WND_RECT_W;
    public final static int IGM_H = WND_RECT_H;
    public final static int IGM_SUB_OFFX = 3;
    public final static int IGM_SUB_OFFY = 4;
    public final static int IGM_TOP_W = IGM_W;
    public final static int IGM_TOP_H = 28;
    public final static int IGM_TOP_X = IGM_X;
    public final static int IGM_TOP_Y = SCREEN_HEIGHT_HALF - (IGM_TOP_H >> 1);
    public final static int IGM_TOP_W_INIT = IGM_TOP_W >> 2;
    public final static int IGM_SUB_X = IGM_X + IGM_SUB_OFFX;
    public final static int IGM_SUB_Y = IGM_Y + IGM_SUB_OFFY;
    public final static int IGM_SUB_W = IGM_W - IGM_SUB_OFFX * 2;
    public final static int IGM_SUB_H = IGM_H - IGM_SUB_OFFY * 2;
    public final static int IGM_TITLE_RECT_H0 = 0;
    public final static int IGM_TITLE_RECT_H1 = 8;
    public final static int IGM_ICON_X = 11;
    public final static int IGM_ICON_Y = 8;
    public final static int IGM_CUSOR_X = 19;
    public final static int IGM_CUSOR_Y = 15;
    public final static int IGM_ITEM_NAME_Y = -9;
    public final static int IGM_ICON_OFFX = 23;
    public final static int IGM_SUBBOX_Y_OFFY = 6;
    public final static int IGM_MONEY_X = 35;
    public final static int IGM_TOKEN_X = 100;
    public final static int IGM_INFO_Y = 15;
    public final static int IGM_INFO_NUM_X = 13;
    public final static int IGM_INFO_NUM_Y = 2;
    public final static int SCROLL_BAR_COLOR0 = COLOR_DARK_GRAY;
    public final static int SCROLL_BAR_COLOR1 = COLOR_BLUE_GRAY;
    public final static int LIST_SCROLL_BAR_X = 20;
    public final static int LIST_SCROLL_BAR_Y = 5;
    public final static int LIST_SCROLL_BAR_H = 60;

    public final static int LIST_2D_NUM_OFFX = 10;
    public final static int LIST_2D_NUM_OFFY = 2;

    //----Dragon----
    public final static int IGM_DRAGON_X0 = 28;
    public final static int IGM_DRAGON_Y0 = 79;
    public final static int IGM_DRAGON_X1 = 0;
    public final static int IGM_DRAGON_Y1 = 215;

    //----Desc----
    public final static int IGM_DESC_H = 50;
    public final static int IGM_DESC_TEXT_X = 5;
    public final static int IGM_DESC_TEXT_Y = 7;

    //----State----
    public final static int IGM_STATE_ICON_X = 3;
    public final static int IGM_STATE_ICON_Y = 7;
    public final static int IGM_STATE_VALUENAME_X = 2;
    public final static int IGM_STATE_VALUENAME_Y = 127;
    public final static int IGM_STATE_VALUENAME_OFFSET = 14;
    public final static int IGM_STATE_LV_X = IGM_STATE_ICON_X + 38;
    public final static int IGM_STATE_LV_Y = IGM_STATE_ICON_Y + 100;
    public final static int IGM_STATE_CURSOR_X = 38;
    public final static int IGM_STATE_CURSOR_Y = 83;

    //----State Role----
    public final static int IGM_STATE_ROLE_X = IGM_X + 4;
    public final static int IGM_STATE_ROLE_Y = IGM_Y + IGM_TITLE_RECT_H0 + 22;
    public final static int IGM_STATE_ROLE_W = 55;
    public final static int IGM_STATE_ROLE_H = 163;
    public final static int IGM_STATE_ROLE_OFFX = 56;
    public final static int IGM_STATE_DESC_Y = IGM_STATE_ROLE_Y + IGM_STATE_ROLE_H / 2;
    public final static int IGM_STATE_DESC_W = 176;
    public final static int IGM_STATE_DESC_H = 90;

    //----Equip----
    public final static int IGM_ROLEEQUIP_ROLE_X = IGM_X;
    public final static int IGM_ROLEEQUIP_ROLE_Y = IGM_Y + IGM_TITLE_RECT_H0;
    public final static int IGM_ROLEEQUIP_ROLE_W = IGM_W;
    public final static int IGM_ROLEEQUIP_ROLE_H = 89;
    public final static int IGM_ROLEEQUIP_ROLE_RECT_W = 54;
    public final static int IGM_ROLEEQUIP_ROLE_RECT_H = 68;
    public final static int IGM_ROLEEQUIP_ROLE_RECT_X = (IGM_ROLEEQUIP_ROLE_W - IGM_ROLEEQUIP_ROLE_RECT_W) >> 1;
    public final static int IGM_ROLEEQUIP_ROLE_RECT_OFFX = 32;  //整体改变 BOC 箭头 图片
    public final static int IGM_ROLEEQUIP_ROLE_RECT_Y = 19;
    public final static int IGM_ROLEEQUIP_ROLE_ACTOR_Y = IGM_ROLEEQUIP_ROLE_RECT_Y + IGM_ROLEEQUIP_ROLE_RECT_H - 10;
    public final static int IGM_ROLEEQUIP_ROLE_ARROW_OFFX1 = -21;
    public final static int IGM_ROLEEQUIP_ROLE_ARROW_OFFX2 = -15;
    public final static int IGM_ROLEEQUIP_ROLE_ARROW_Y = 40;

    public final static int IGM_ROLEEQUIP_DESC_X = 6;
    public final static int IGM_ROLEEQUIP_DESC_Y = 160;
    public final static int IGM_ROLEEQUIP_DESC_W = IGM_W - IGM_ROLEEQUIP_DESC_X * 2;
    public final static int IGM_ROLEEQUIP_DESC_H = 30;

    final static int IGM_ROLEEQUIP_RECT_X = 6;
    final static int IGM_ROLEEQUIP_RECT_Y = IGM_ROLEEQUIP_ROLE_RECT_Y + IGM_ROLEEQUIP_ROLE_RECT_H + 1 + 22;
    final static int IGM_ROLEEQUIP_RECT_W = IGM_W - IGM_ROLEEQUIP_RECT_X * 2;
    final static int IGM_ROLEEQUIP_RECT_H = 72 - 22;
    final static int IGM_ROLEEQUIP_VALUE_X = IGM_ROLEEQUIP_RECT_X + 6;
    final static int IGM_ROLEEQUIP_VALUE_Y = IGM_ROLEEQUIP_RECT_Y + 4;
    final static int IGM_ROLEEQUIP_VALUE_OFFX = 75;
    final static int IGM_ROLEEQUIP_VALUE_OFFY = 14;
    final static int IGM_ROLEEQUIP_VALUE_NUM_X = 60;
    final static int IGM_ROLEEQUIP_VALUE_NUM_Y = 3;
    final static int[] IGM_ROLEEQUIP_ICON_X = {38, 38, 38, 38, 38, 38};
    final static int[] IGM_ROLEEQUIP_ICON_Y = {IGM_TITLE_RECT_H0 + 30, IGM_TITLE_RECT_H0 + 53, IGM_TITLE_RECT_H0 + 76, IGM_TITLE_RECT_H0 + 99,
                                              IGM_TITLE_RECT_H0 + 122, IGM_TITLE_RECT_H0 + 145};
    final static int IGM_ROLEEQUIP_CURSOR_X = 5;
    final static int IGM_ROLEEQUIP_CURSOR_Y = 0;
    final static int IGM_ROLEEQUIP_ROW_NUM = 3;
    final static int IGM_ROLEEQUIP_COL_NUM = 2;

    //----EquipStandby----
    public final static int IGM_EQUIPSTANDBY_TABEL_X = 43;
    public final static int IGM_EQUIPSTANDBY_TABEL_Y = 20 + IGM_TITLE_RECT_H0;
    public final static int IGM_EQUIPSTANDBY_TABEL_NAME_X = 28;
    public final static int IGM_EQUIPSTANDBY_TABEL_NAME_Y = 22 + IGM_TITLE_RECT_H0;
    public final static int IGM_EQUIPSTANDBY_TABEL_DY = 19;
    public final static int IGM_EQUIPSTANDBY_RECT_X = IGM_EQUIPSTANDBY_TABEL_X + 1;
    public final static int IGM_EQUIPSTANDBY_RECT_Y = IGM_EQUIPSTANDBY_TABEL_Y - 1;
    public final static int IGM_EQUIPSTANDBY_RECT_W = 125;
    public final static int IGM_EQUIPSTANDBY_RECT_H = 97;
    public final static int IGM_EQUIPSTANDBY_ICON_X = IGM_EQUIPSTANDBY_RECT_X + 20;
    public final static int IGM_EQUIPSTANDBY_ICON_Y = IGM_EQUIPSTANDBY_RECT_Y + 13;
    public final static int IGM_EQUIPSTANDBY_ICON_OFFX = 25;
    public final static int IGM_EQUIPSTANDBY_ICON_OFFY = 23;
    public final static int IGM_EQUIPSTANDBY_DESC_X = IGM_X + 6;
    public final static int IGM_EQUIPSTANDBY_DESC_Y = IGM_EQUIPSTANDBY_RECT_Y + IGM_EQUIPSTANDBY_RECT_H;
    public final static int IGM_EQUIPSTANDBY_DESC_W = IGM_W - 12;
    public final static int IGM_EQUIPSTANDBY_DESC_H = 75;
    public final static int IGM_EQUIPSTANDBY_SUSPEN_W = 75;
    public final static int IGM_EQUIPSTANDBY_SUSPEN_TEXT_X = 6;
    public final static int IGM_EQUIPSTANDBY_SUSPEN_TEXT_Y = 6;
    public final static int IGM_EQUIPSTANDBY_SUSPEN_NUM_Y = 4;
    public final static int IGM_EQUIPSTANDBY_SUSPEN_TEXT_OFFY = 15;
    public final static int IGM_EQUIPSTANDBY_SUSPEN_Y = 15;
    public final static int IGM_EQUIPSTANDBY_SUSPEN_H = IGM_EQUIPSTANDBY_SUSPEN_TEXT_Y * 2 + IGM_EQUIPSTANDBY_SUSPEN_TEXT_OFFY * 6;
    public final static int IGM_EQUIPSTANDBY_COMPARE_OFFX_LEFT = 10;
    public final static int IGM_EQUIPSTANDBY_COMPARE_OFFX_RIGHT = -10;

    //----EquipPurpose----
    public final static int IGM_EQUIPPURPOSE_Y = 23;
    public final static int IGM_EQUIPPURPOSE_W = 52;
    public final static int IGM_EQUIPPURPOSE_H = 72;
    public final static int IGM_EQUIPPURPOSE_X = (IGM_W - IGM_EQUIPPURPOSE_W) >> 1;
    public final static int IGM_EQUIPPURPOSE_OFFY = 20;
    public final static int IGM_EQUIPPURPOSE_CURSOR_X = 40;
    public final static int IGM_EQUIPPURPOSE_CURSOR_Y = 5;

    //----EquipAdvance----
    public final static int IGM_EQUIPADVANCE_VALUE_RECT_X = 8;
    public final static int IGM_EQUIPADVANCE_VALUE_RECT_Y = 20 + IGM_TITLE_RECT_H0;
    public final static int IGM_EQUIPADVANCE_VALUE_RECT_W = IGM_W - IGM_EQUIPADVANCE_VALUE_RECT_X * 2;
    public final static int IGM_EQUIPADVANCE_VALUE_RECT_H = 120;
    public final static int IGM_EQUIPADVANCE_NAME_Y = IGM_EQUIPADVANCE_VALUE_RECT_Y + 4;
    public final static int IGM_EQUIPADVANCE_VALUE_TEXT_X = 6;
    public final static int IGM_EQUIPADVANCE_VALUE_TEXT_Y = IGM_EQUIPADVANCE_NAME_Y + FONT_HEIGHT;
    public final static int IGM_EQUIPADVANCE_VALUE_NAME_X = IGM_EQUIPADVANCE_VALUE_TEXT_X + 8;
    public final static int IGM_EQUIPADVANCE_VALUE_NUM0_X = IGM_EQUIPADVANCE_VALUE_TEXT_X + 58;
    public final static int IGM_EQUIPADVANCE_VALUE_NUM1_X = IGM_EQUIPADVANCE_VALUE_RECT_X + IGM_EQUIPADVANCE_VALUE_RECT_W - 20;
    public final static int IGM_EQUIPADVANCE_ARROW_X = IGM_EQUIPADVANCE_VALUE_RECT_X + 95;
    public final static int IGM_EQUIPADVANCE_VALUE_NUM_Y = IGM_EQUIPADVANCE_VALUE_TEXT_Y;
    public final static int IGM_EQUIPADVANCE_VALUE_NAME_Y = 4;
    public final static int IGM_EQUIPADVANCE_VALUE_TEXT_OFFY = 16;
    public final static int IGM_EQUIPADVANCE_EXPEND_X = 8;
    public final static int IGM_EQUIPADVANCE_EXPEND_Y = IGM_EQUIPADVANCE_VALUE_RECT_Y + IGM_EQUIPADVANCE_VALUE_RECT_H + 3;
    public final static int IGM_EQUIPADVANCE_ICON_GOLD_X = 38;
    public final static int IGM_EQUIPADVANCE_COST_GOLD_X = 53;
    public final static int IGM_EQUIPADVANCE_ICON_TOKEN_X = 108;
    public final static int IGM_EQUIPADVANCE_COST_TOKEN_X = 123;
    public final static int IGM_EQUIPADVANCE_COST_Y = IGM_EQUIPADVANCE_EXPEND_Y + 2;
    public final static int IGM_EQUIPADVANCE_BUTTON_Y = IGM_EQUIPADVANCE_EXPEND_Y + 20;

    //--skill--
    public final static int IGM_SKILL_H = 121;
    public final static int IGM_SKILL_ROLE_X = IGM_X + 12;
    public final static int IGM_SKILL_ROLE_Y = IGM_Y + 36 + IGM_TITLE_RECT_H0;
    public final static int IGM_SKILL_ICONBG_X = 25;
    public final static int IGM_SKILL_ICONBG_Y = 55;
    public final static int IGM_SKILL_ROLE_BOX_OFFX = -4;
    public final static int IGM_SKILL_ROLE_NAME_X = IGM_SKILL_ROLE_BOX_OFFX + 26;
    public final static int IGM_SKILL_ROLE_NAME_Y = -15;
    public final static int IGM_SKILL_TABEL_OFFX = 55;
    public final static int IGM_SKILL_RECT_X = 6;
    public final static int IGM_SKILL_RECT_Y = 36 + IGM_TITLE_RECT_H0;
    public final static int IGM_SKILL_RECT_W = IGM_W - IGM_SKILL_RECT_X * 2;
    public final static int IGM_SKILL_RECT_H = 80;
    public final static int IGM_SKILL_LIST_X = 40;
    public final static int IGM_SKILL_LIST_Y = 44;
    public final static int IGM_SKILL_LIST_W = 100;
    public final static int IGM_SKILL_LIST_DX = 120;
    public final static int IGM_SKILL_LIST_DY = 22;
    public final static int IGM_SKILL_CURSOR_X = IGM_SKILL_RECT_X + 110;
    public final static int IGM_SKILL_CURSOR_Y = IGM_SKILL_LIST_Y + 8;
    public final static int IGM_SKILL_SCROLL_BAR_X = 158;
    public final static int IGM_SKILL_SCROLL_BAR_Y = 50;
    public final static int IGM_SKILL_SCROLL_BAR_H = 50;
    public final static int IGM_SKILL_DESC_X = IGM_X + 6;
    public final static int IGM_SKILL_DESC_Y = IGM_Y + IGM_SKILL_RECT_Y + IGM_SKILL_RECT_H;
    public final static int IGM_SKILL_DESC_W = IGM_W - 12;
    public final static int IGM_SKILL_DESC_H = 75;

    //----Artical----
    public final static int IGM_ARTICAL_TABEL_X = 43;
    public final static int IGM_ARTICAL_TABEL_Y = 21 + IGM_TITLE_RECT_H0;
    public final static int IGM_ARTICAL_TABEL_NAME_X = IGM_ARTICAL_TABEL_X - 17;
    public final static int IGM_ARTICAL_TABEL_NAME_Y = IGM_ARTICAL_TABEL_Y + 3;
    public final static int IGM_ARTICAL_TABEL_DY = 20;
    public final static int IGM_ARTICAL_TYPE_TABEL_X = 10;
    public final static int IGM_ARTICAL_TYPE_TABEL_Y = 30 + IGM_TITLE_RECT_H0;
    public final static int IGM_ARTICAL_TYPE_TABEL_DY = 25;
    public final static int IGM_ARTICAL_LIST_RECT_X = IGM_ARTICAL_TABEL_X + 1;
    public final static int IGM_ARTICAL_LIST_RECT_Y = 18;
    public final static int IGM_ARTICAL_LIST_RECT_W = 125;
    public final static int IGM_ARTICAL_LIST_RECT_H = 97;
    public final static int IGM_ARTICAL_LIST_ICON_X = IGM_X + IGM_ARTICAL_LIST_RECT_X + 20;
    public final static int IGM_ARTICAL_LIST_ICON_Y = IGM_Y + IGM_ARTICAL_LIST_RECT_Y + 13;
    public final static int IGM_ARTICAL_LIST_ICON_OFFX = 25;
    public final static int IGM_ARTICAL_LIST_ICON_OFFY = 23;
    public final static int IGM_ARTICAL_TITLE_W = 40;
    public final static int IGM_ARTICAL_TITLE_H = UI_NAME_RECT_H;
    public final static int IGM_ARTICAL_RECT_W = 120;
    public final static int IGM_ARTICAL_RECT_H = UI_NAME_RECT_H;
    public final static int IGM_ARTICAL_DESC_X = IGM_X + 6;
    public final static int IGM_ARTICAL_DESC_Y = IGM_Y + IGM_ARTICAL_LIST_RECT_Y + IGM_ARTICAL_LIST_RECT_H;
    public final static int IGM_ARTICAL_DESC_W = IGM_W - 12;
    public final static int IGM_ARTICAL_DESC_H = 75;

    //----Advance----
    public final static int IGM_ADVANCE_TYPEBG_X = 5;
    public final static int IGM_ADVANCE_TYPEBG_Y = 3;
    public final static int IGM_ADVANCE_TYPENAME_X = 29;
    public final static int IGM_ADVANCE_TYPENAME_Y = 13;
    public final static int IGM_ADVANCE_TYPE_OFFX = 48;
    public final static int IGM_ADVANCE_DECOLINE_Y = 51;
    public final static int IGM_ADVANCE_DECOLINE_OFFY = 20;
    public final static int IGM_ADVANCE_DECO_X0 = 5;
    public final static int IGM_ADVANCE_DECO_X1 = 80;
    public final static int IGM_ADVANCE_DECO_Y = 24;
    public final static int IGM_ADVANCE_ITEMNAME_X0 = 27;
    public final static int IGM_ADVANCE_ITEMNAME_X1 = 122;
    public final static int IGM_ADVANCE_ITEMNAME_Y = 22;
    public final static int IGM_ADVANCE_ARROW_X = 10;
    public final static int IGM_ADVANCE_ARROW_Y = 57;
    public final static int IGM_ADVANCE_LIST_X = 20;
    public final static int IGM_ADVANCE_LIST_Y = 43;
    public final static int IGM_ADVANCE_LIST_W = 90;
    public final static int IGM_ADVANCE_LIST_DX = 120;
    public final static int IGM_ADVANCE_LIST_DY = 20;
    public final static int IGM_UI_ADVANCE_SELECT_OFFY = 12;

    //----AdvanceSelect----
    public final static int IGM_ADVANCE_SELECT_H = 121;
    public final static int IGM_ADVANCE_SELECT_OFFY = 6;
    public final static int IGM_ADVANCE_SELRECT_OFFW = 6;
    public final static int IGM_ADVANCE_SELRECT_H = 17;
    public final static int IGM_ADVANCE_MENUBOX_OFFY = 43;
    public final static int IGM_ADVANCE_MENUBOX_OFFH = 38;
    public final static int IGM_ADVANCE_SELRECT_OFFX = 3;
    public final static int IGM_ADVANCE_SELRECT_OFFY = 4;
    public final static int IGM_ADVANCE_MENUBOX_OFFY1 = 84;
    public final static int IGM_ADVANCE_MENUBOX_OFFH1 = -79;

    //----Skill Advance----
    public final static int IGM_SKILL_ADVANCE_RECT_X = 10;
    public final static int IGM_SKILL_ADVANCE_RECT_Y = 10 + IGM_TITLE_RECT_H0;
    public final static int IGM_SKILL_ADVANCE_RECT_W = IGM_W - IGM_SKILL_ADVANCE_RECT_X * 2;
    public final static int IGM_SKILL_ADVANCE_RECT_H = 130;
    public final static int IGM_SKILL_ADVANCE_RECT1_X = 10;
    public final static int IGM_SKILL_ADVANCE_RECT1_Y = 75 + IGM_TITLE_RECT_H0;
    public final static int IGM_SKILL_ADVANCE_RECT1_W = IGM_W - IGM_SKILL_ADVANCE_RECT_X * 2;
    public final static int IGM_SKILL_ADVANCE_RECT1_H = 66;
    public final static int IGM_SKILL_ADVANCE_TEXT_X = 10;
    public final static int IGM_SKILL_ADVANCE_TEXT_Y = 20 + IGM_SKILL_ADVANCE_RECT_Y;
    public final static int IGM_SKILL_ADVANCE_TEXT_H = IGM_SKILL_ADVANCE_RECT1_H - 22;
    public final static int IGM_SKILL_ADVANCE_TEXT1_X = 10;
    public final static int IGM_SKILL_ADVANCE_TEXT1_Y = 20 + IGM_SKILL_ADVANCE_RECT1_Y;
    public final static int IGM_SKILL_ADVANCE_TEXT1_H = IGM_SKILL_ADVANCE_RECT1_H - 22;
    public final static int IGM_SKILL_ADVANCE_NAME_Y = IGM_SKILL_ADVANCE_RECT_Y + 2;
    public final static int IGM_SKILL_ADVANCE_NAME1_Y = IGM_SKILL_ADVANCE_RECT1_Y + 2;
    public final static int IGM_SKILL_ADVANCE_COMPARE_ITEM_X = IGM_SKILL_ADVANCE_RECT_X + 5;
    public final static int IGM_SKILL_ADVANCE_COMPARE_ITEM_Y = IGM_SKILL_ADVANCE_NAME_Y + FONT_HEIGHT + 10;
    public final static int IGM_SKILL_ADVANCE_COMPARE_ITEM_OFFY = 20;
    public final static int IGM_SKILL_ADVANCE_COMPARE_VALUE0_X = IGM_SKILL_ADVANCE_COMPARE_ITEM_X + 58;
    public final static int IGM_SKILL_ADVANCE_COMPARE_VALUE1_X = IGM_SKILL_ADVANCE_RECT_X + IGM_SKILL_ADVANCE_RECT_W - 20;
    public final static int IGM_SKILL_ADVANCE_COMPARE_ARROW_X = IGM_SKILL_ADVANCE_COMPARE_VALUE0_X + 30;
    public final static int IGM_SKILL_EXPEND_X = 8;
    public final static int IGM_SKILL_EXPEND_Y = IGM_SKILL_ADVANCE_RECT_Y + IGM_SKILL_ADVANCE_RECT_H + 3;
    public final static int IGM_SKILL_ICON_GOLD_X = 38;
    public final static int IGM_SKILL_COST_GOLD_X = 53;
    public final static int IGM_SKILL_ICON_TOKEN_X = 108;
    public final static int IGM_SKILL_COST_TOKEN_X = 123;
    public final static int IGM_SKILL_COST_Y = IGM_SKILL_EXPEND_Y + 2;
    public final static int IGM_SKILL_BUTTON_Y = IGM_SKILL_EXPEND_Y + 14;
    public final static int IGM_SKILL_ADVANCE_X = SCREEN_WIDTH_HALF;
    public final static int IGM_SKILL_ADVANCE_Y = IGM_SKILL_EXPEND_Y - 26;


    //-----------SYSTEM---------------------
    public final static int IGM_SYSTEM_FRAME_Y = 24;
    public final static int IGM_SYSTEM_CURSOR_X = 120;
    public final static int IGM_SYSTEM_CURSOR_Y = 30;
    public final static int IGM_SYSTEM_FRAME_OFFY = 24;
    final static int SYSTEM_BOX_X = 4;
    final static int SYSTEM_BOX_Y = 18;
    final static int SYSTEM_BOX_W = -SYSTEM_BOX_X << 1;
    final static int SYSTEM_BOX_H = -SYSTEM_BOX_Y << 1;


    //----ui softkey----
    public final static int UI_SOFTKE_X = 20;
    public final static int UI_SOFTKE_Y = 16;
    public final static int UI_RMS_X = 10;
    public final static int UI_RMS_Y = 10;


    //------------portable shop-------------------
    public final static int PORTABLE_SHOP_LIST_X = 40;
    public final static int PORTABLE_SHOP_LIST_Y = 30;
    public final static int PORTABLE_SHOP_LIST_DY = 25;
    public final static int PORTABLE_SHOP_LIST_W = 80;

    //----商店----
    public final static int IGM_NORMAL_SHOP = 0;
    public final static int IGM_BATTLE_SHOP = 1;

    final static int SHOP_BOX_X = 3;
    final static int SHOP_BOX_Y = 17;
    final static int SHOP_BOX_W = -SHOP_BOX_X<<1;
    final static int SHOP_BOX_H = -SHOP_BOX_Y<<1;

    public final static int IGM_SHOP_SUB_X = IGM_RECT_X + 11;
    public final static int IGM_SHOP_SUB_Y = IGM_RECT_Y + 45;
    public final static int IGM_SHOP_SUB_W = 155;
    public final static int IGM_SHOP_SUB_H = 80;

    public final static int IGM_SHOP_TITLE_OFFX = 50;
    public final static int IGM_SHOP_TITLE_OFFY = 0;
    public final static int IGM_SHOP_TABEL_X = 35;
    public final static int IGM_SHOP_TABEL_Y = 37;
    public final static int IGM_SHOP_TABEL_OFFX = 55;
    public final static int IGM_SHOP_RECT_SPACE = 50;
    public final static int IGM_SHOP_RECT_OFF_X = 40;
    public final static int IGM_SHOP_RECT_OFF_Y = 13;
    public final static int IGM_SHOP_NAME_SPACE = IGM_SHOP_RECT_SPACE + 3;
    public final static int IGM_SHOP_NAME_OFF_X = IGM_SHOP_RECT_OFF_X + 7;
    public final static int IGM_SHOP_NAME_OFF_Y = 29;
    public final static int IGM_SHOP_NAME_W = 71;
    public final static int IGM_SHOP_NAME_H = 30;
    public final static int IGM_SHOP_STR_SIDE_OFF_X = 50;
    public final static int IGM_SHOP_STR_SIDE_OFF_Y = 13;
    public final static int IGM_SHOP_STR_SIDE_SPACE = 50;
    public final static int IGM_SHOP_ICON_X = IGM_X + 15;
    public final static int IGM_SHOP_ICON_Y = IGM_Y + 50;
    public final static int IGM_SHOP_ICON_OFFX = 25;
    public final static int IGM_SHOP_ICON_OFFY = 25;

    public final static int IGM_SHOP_ARROW_OFF_X = 8;
    public final static int IGM_SHOP_ARROW_OFF_Y = 16;
    public final static int IGM_SHOP_ARROW_SPACE = 19;
    public final static int IGM_SHOP_CURSOR_X = 0;
    public final static int IGM_SHOP_CURSOR_Y = 0;
    public final static int IGM_SHOP_CURSOR_OFFX = IGM_SHOP_STR_SIDE_SPACE / 2;

    public final static int IGM_SHOP_DESC_X = IGM_RECT_X + 11;
    public final static int IGM_SHOP_DESC_Y = IGM_RECT_Y + 119;
    public final static int IGM_SHOP_DESC_W = 155;
    public final static int IGM_SHOP_DESC_H = 49;
    public final static int IGM_SHOP_CONFIRM_W = 100;
    public final static int IGM_SHOP_CONFIRM_H = 80;
    public final static int IGM_SHOP_CONFIRM_X = SCREEN_WIDTH_HALF - (IGM_SHOP_CONFIRM_W>>1);
    public final static int IGM_SHOP_CONFIRM_Y = SCREEN_HEIGHT_HALF - (IGM_SHOP_CONFIRM_H>>1);
    public final static int IGM_SHOP_CONFIRM_OFF_X = 15;
    public final static int IGM_SHOP_CONFIRM_ARROW_X = 55;
    public final static int IGM_SHOP_CONFIRM_ARROW_UP = 15;
    public final static int IGM_SHOP_CONFIRM_ARROW_DOWN = 35;
    public final static int IGM_SHOP_CONFIRM_NUM_OFF_X = 60;
    public final static int IGM_SHOP_CONFIRM_BUY_OFF_Y = 20;
    public final static int IGM_SHOP_CONFIRM_COUNT_OFF_Y = 50;

    //------------Mission---------------------
    public final static int IGM_MISSION_SHOW_NUM = 3;
    public final static int IGM_MISSION_TABEL_X = 10 + 11;
    public final static int IGM_MISSION_TABEL_Y = 36 + IGM_TITLE_RECT_H0;
    public final static int IGM_MISSION_TABEL_DX = 70;
    public final static int IGM_MISSION_TABEL_NAME_X = IGM_MISSION_TABEL_X + 32;
    public final static int IGM_MISSION_TABEL_NAME_Y = IGM_MISSION_TABEL_Y - 16;
    public final static int IGM_MISSION_RECT_X = 6;
    public final static int IGM_MISSION_RECT_Y = IGM_MISSION_TABEL_Y;
    public final static int IGM_MISSION_RECT_W = IGM_W - IGM_MISSION_RECT_X * 2;
    public final static int IGM_MISSION_RECT_H = 80;
    public final static int IGM_MISSION_LIST_X = IGM_MISSION_RECT_X + 32;
    public final static int IGM_MISSION_LIST_Y = IGM_MISSION_RECT_Y + 5;
    public final static int IGM_MISSION_LIST_DY = 22;
    public final static int IGM_MISSION_ICON_X = IGM_MISSION_LIST_X + ICON_X_OFFSET;
    public final static int IGM_MISSION_LIST_COMPLETE_X = IGM_MISSION_LIST_X + 100;
    public final static int IGM_MISSION_TXT_W = 120;
    public final static int IGM_MISSION_DESC_X = IGM_MISSION_RECT_X;
    public final static int IGM_MISSION_DESC_Y = IGM_MISSION_TABEL_Y + IGM_MISSION_RECT_H;
    public final static int IGM_MISSION_DESC_W = IGM_W - IGM_MISSION_DESC_X * 2;
    public final static int IGM_MISSION_DESC_H = 75;
    public final static int IGM_MISSION_SCROLL_BAR_X = 158;
    public final static int IGM_MISSION_SCROLL_BAR_Y = 50;
    public final static int IGM_MISSION_SCROLL_BAR_H = 50;

    //------------practice------------
    public final static int PRACTICE_RECT_X = 8;
    public final static int PRACTICE_RECT_Y = 10 + IGM_TITLE_RECT_H0;
    public final static int PRACTICE_RECT_W = IGM_W - PRACTICE_RECT_X * 2;
    public final static int PRACTICE_RECT_H = 65;
    public final static int PRACTICE_RECT_OFFY = PRACTICE_RECT_H + 5;

    //------------mixtrue------------
    public final static int MIXTRUE_X = IGM_X;
    public final static int MIXTRUE_Y = IGM_Y;
    public final static int MIXTRUE_W = IGM_W;
    public final static int MIXTRUE_H = IGM_H;
    public final static int MIXTRUE_RECT_X = 7;
    public final static int MIXTRUE_RECT_Y = 35;
    public final static int MIXTRUE_RECT_W = IGM_W - MIXTRUE_RECT_X * 2;
    public final static int MIXTRUE_RECT_H = 80;
    public final static int MIXTRUE_TABEL_X = MIXTRUE_RECT_X + 15;
    public final static int MIXTRUE_TABEL_Y = MIXTRUE_RECT_Y;
    public final static int MIXTRUE_ITEM_X = 42+ 15 - 3;
    public final static int MIXTRUE_ITEM_Y = MIXTRUE_TABEL_Y - 15;
    public final static int MIXTRUE_TABEL_DX = 69;

    public final static int MIXTRUE_LIST_X = MIXTRUE_X + MIXTRUE_RECT_X;
    public final static int MIXTRUE_LIST_Y = MIXTRUE_Y + MIXTRUE_TABEL_Y;
    public final static int MIXTRUE_LIST_W = MIXTRUE_W - MIXTRUE_RECT_X * 2;
    public final static int MIXTRUE_LIST_H = 80;
    public final static int MIXTRUE_LIST_NAME_X = 40;
    public final static int MIXTRUE_LIST_NAME_Y = 8;
    public final static int MIXTRUE_LIST_ICON_X = 24;
    public final static int MIXTRUE_LIST_ICON_Y = 20;
    public final static int MIXTRUE_LIST_OFFY = 22;
    public final static int MIXTRUE_LIST_RECT_H = MIXTRUE_LIST_H - 16;
    public final static int MIXTRUE_SCROLL_BAR_X = 151;
    public final static int MIXTRUE_SCROLL_BAR_Y = 16;
    public final static int MIXTRUE_SCROLL_BAR_H = 50;
    public final static int MIXTRUE_LIST_CURSOR_X = 120;
    public final static int MIXTRUE_LIST_CURSOR_Y = 18;

    public final static int MIXTRUE_DESC_X = MIXTRUE_LIST_X;
    public final static int MIXTRUE_DESC_Y = MIXTRUE_LIST_Y + MIXTRUE_LIST_H;
    public final static int MIXTRUE_DESC_W = MIXTRUE_LIST_W;
    public final static int MIXTRUE_DESC_H = 75;

    public final static int MIXTRUE_INFO_X = IGM_X;
    public final static int MIXTRUE_INFO_Y = IGM_Y;
    public final static int MIXTRUE_INFO_W = IGM_W;
    public final static int MIXTRUE_INFO_H = IGM_H;
    public final static int MIXTRUE_INFO_ITEM_ICON_X = 20;
    public final static int MIXTRUE_INFO_ITEM_ICON_Y = 28;
    public final static int MIXTRUE_INFO_ITEM_NAME_X = MIXTRUE_INFO_ITEM_ICON_X + 15;
    public final static int MIXTRUE_INFO_ITEM_NAME_Y = MIXTRUE_INFO_ITEM_ICON_Y - ICON_Y_OFFSET;
    public final static int MIXTRUE_INFO_TITLE_Y = MIXTRUE_INFO_ITEM_ICON_Y + ICON_Y_OFFSET + 4;
    public final static int MIXTRUE_INFO_MESSAGE_X = 6;
    public final static int MIXTRUE_INFO_MESSAGE_Y = MIXTRUE_INFO_TITLE_Y + 20;
    public final static int MIXTRUE_INFO_MESSAGE_W = MIXTRUE_INFO_W - MIXTRUE_INFO_MESSAGE_X * 2;
    public final static int MIXTRUE_INFO_MESSAGE_H = 105;
    public final static int MIXTRUE_INFO_STUFF_X = MIXTRUE_INFO_MESSAGE_X + 10;
    public final static int MIXTRUE_INFO_COUNT_X = MIXTRUE_INFO_STUFF_X + FONT_WIDTH * 5;
    public final static int MIXTRUE_INFO_NUM_X = MIXTRUE_INFO_MESSAGE_X + MIXTRUE_INFO_MESSAGE_W - 10;
    public final static int MIXTRUE_INFO_ITEM_Y = MIXTRUE_INFO_MESSAGE_Y + 5;
    public final static int MIXTRUE_INFO_ITEM_OFFY = ICON_Y_OFFSET * 2;
    public final static int MIXTRUE_INFO_START_Y = MIXTRUE_INFO_MESSAGE_Y + MIXTRUE_INFO_MESSAGE_H;
    public final static int MIXTRUE_INFO_CURSOR_X = MIXTRUE_INFO_MESSAGE_X + 110;
    public final static int MIXTRUE_INFO_CURSOR_Y = MIXTRUE_INFO_START_Y + 5;

    public final static int MIXTRUE_MESSAGE_W = IGM_W;
    public final static int MIXTRUE_MESSAGE_H = FONT_HEIGHT * 5 + 20;
    public final static int MIXTRUE_MESSAGE_X = SCREEN_WIDTH_HALF - (MIXTRUE_MESSAGE_W >> 1);
    public final static int MIXTRUE_MESSAGE_Y = IGM_Y + 30;
    public final static int MIXTRUE_MESSAGE_TITLE_X = 10;
    public final static int MIXTRUE_MESSAGE_TITLE_Y = 10;
    public final static int MIXTRUE_MESSAGE_CONTENT_X = 10;
    public final static int MIXTRUE_MESSAGE_ITEM_X = MIXTRUE_MESSAGE_CONTENT_X;
    public final static int MIXTRUE_MESSAGE_NUM_X = MIXTRUE_MESSAGE_W - MIXTRUE_MESSAGE_TITLE_X;
    public final static int MIXTRUE_MESSAGE_CONTENT_Y = MIXTRUE_MESSAGE_TITLE_Y + FONT_HEIGHT;

    //------------role value------------
    public final static int IGM_ROLE_VALUE_W = 160;
    public final static int IGM_ROLE_VALUE_H = 140;
    public final static int IGM_ROLE_VALUE_X = SCREEN_WIDTH_HALF - (IGM_ROLE_VALUE_W >> 1);
    public final static int IGM_ROLE_VALUE_Y = SCREEN_HEIGHT_HALF - (IGM_ROLE_VALUE_H >> 1);
    public final static int IGM_ROLE_VALUE_SHOW_NUM = 6;
    public final static int IGM_ROLE_VALUE_TITLE_Y = 8;
    public final static int IGM_ROLE_VALUE_NAME_X = 10;
    public final static int IGM_ROLE_VALUE_NAME_Y = IGM_ROLE_VALUE_TITLE_Y + FONT_HEIGHT + 2;
    public final static int IGM_ROLE_VALUE_OLD_X = 60;
    public final static int IGM_ROLE_VALUE_NEW_X = 120;
    public final static int IGM_ROLE_VALUE_NUM_Y = IGM_ROLE_VALUE_TITLE_Y + FONT_HEIGHT + 6;
    public final static int IGM_ROLE_VALUE_OFF_Y = 16;
    public final static int IGM_ROLE_VALUE_SCROLL_X = 15;
    public final static int IGM_ROLE_VALUE_SCROLL_Y = IGM_ROLE_VALUE_NAME_Y + 10;
    public final static int IGM_ROLE_VALUE_SCROLL_H = 70;

    //------------animation------------
    public final static int ANIMATION_RECT_W = 165;
    public final static int ANIMATION_RECT_H = 75;
    public final static int ANIMATION_RECT_X = SCREEN_WIDTH_HALF - (ANIMATION_RECT_W >> 1);
    public final static int ANIMATION_RECT_Y = SCREEN_HEIGHT_HALF - (ANIMATION_RECT_H >> 1);
    public final static int ANIMATION_TITLE_Y = 8;

    //------------soul------------
    public final static int SOUL_RECT_X = 10;
    public final static int SOUL_RECT_Y = 15 + IGM_TITLE_RECT_H0;
    public final static int SOUL_RECT_W = IGM_W - SOUL_RECT_X * 2;
    public final static int SOUL_RECT_H = 140;

////@#if EXIST_EFFORT
    //------------Effort------------
    public final static int EFFORT_X = IGM_X;
    public final static int EFFORT_Y = IGM_Y;
    public final static int EFFORT_W = IGM_W;
    public final static int EFFORT_H = IGM_H;

    public final static int IGM_EFFORT_SHOW_NUM = 4;
    public final static int IGM_EFFORT_TABEL_X = 7;
    public final static int IGM_EFFORT_TABEL_Y = 35 + IGM_TITLE_RECT_H0;
    public final static int IGM_EFFORT_TABEL_DX = 40;
    public final static int IGM_EFFORT_TABEL_NAME_X = IGM_EFFORT_TABEL_X + 4;
    public final static int IGM_EFFORT_TABEL_NAME_Y = IGM_EFFORT_TABEL_Y;
    public final static int IGM_EFFORT_RECT_X = 6;
    public final static int IGM_EFFORT_RECT_Y = IGM_EFFORT_TABEL_Y;
    public final static int IGM_EFFORT_RECT_W = IGM_W - IGM_EFFORT_RECT_X * 2;
    public final static int IGM_EFFORT_RECT_H = 100;
    public final static int IGM_EFFORT_LIST_X = IGM_EFFORT_RECT_X + 32;
    public final static int IGM_EFFORT_LIST_Y = IGM_EFFORT_RECT_Y + 5;
    public final static int IGM_EFFORT_LIST_DY = 22;
    public final static int IGM_EFFORT_ICON_X = IGM_EFFORT_LIST_X + ICON_X_OFFSET;
    public final static int IGM_EFFORT_LIST_COMPLETE_X = IGM_EFFORT_LIST_X + 100;
    public final static int IGM_EFFORT_TXT_W = 120;
    public final static int IGM_EFFORT_DESC_X = IGM_EFFORT_RECT_X;
    public final static int IGM_EFFORT_DESC_Y = IGM_EFFORT_TABEL_Y + IGM_EFFORT_RECT_H;
    public final static int IGM_EFFORT_DESC_W = IGM_W - IGM_EFFORT_DESC_X * 2;
    public final static int IGM_EFFORT_DESC_H = 75;
    public final static int IGM_EFFORT_SCROLL_BAR_X = 158;
    public final static int IGM_EFFORT_SCROLL_BAR_Y = 56;
    public final static int IGM_EFFORT_SCROLL_BAR_H = 60;


//    public final static int IGM_EFFORT_TABEL_X = 7;
//    public final static int IGM_EFFORT_TABEL_Y = 35;
//    public final static int IGM_EFFORT_TABEL_DX = 40;
//    public final static int IGM_EFFORT_RECT_X = 6;
//    public final static int IGM_EFFORT_RECT_Y = IGM_EFFORT_TABEL_Y;
//    public final static int IGM_EFFORT_RECT_W = IGM_W - IGM_EFFORT_RECT_X * 2;
//    public final static int IGM_EFFORT_RECT_H = 100;
//    public final static int IGM_EFFORT_ICON_X = IGM_EFFORT_LIST_X + ICON_X_OFFSET;
//    public final static int IGM_EFFORT_LIST_DY = 100;
//    public final static int IGM_EFFORT_TABEL_NAME_X = 11;
//    public final static int IGM_EFFORT_TABEL_NAME_Y = 35;
    public final static int EFFORT_TABEL_X = 12;
    public final static int EFFORT_TABEL_Y = 8;
    public final static int EFFORT_WORD_X = 22;
    public final static int EFFORT_WORD_Y = 12;
    public final static int EFFORT_TABEL_OFFX = 50;
    public final static int EFFORT_DATA_X = 118;
    public final static int EFFORT_DATA_Y = 22;
    public final static int EFFORT_DATA_W = 48;
    public final static int EFFORT_DATA_H = 33;
    public final static int EFFORT_DATA_NAME_X = 128;
    public final static int EFFORT_DATA_NAME_Y = 11;
    public final static int EFFORT_DATA_OFFY = 40;
    public final static int EFFORT_DATA_SCORE_X = 160;
    public final static int EFFORT_DATA_SCORE_Y = 27;
    public final static int EFFORT_DATA_DIFFICULTY_X = EFFORT_DATA_X + (EFFORT_DATA_W >> 1);
    public final static int EFFORT_DATA_DIFFICULTY_Y = EFFORT_DATA_SCORE_Y - 4 + EFFORT_DATA_OFFY;
    public final static int EFFORT_DATA_PROGRESS_X = EFFORT_DATA_DIFFICULTY_X - 3;
    public final static int EFFORT_DATA_PROGRESS_Y = EFFORT_DATA_DIFFICULTY_Y + 6 + EFFORT_DATA_OFFY;
    public final static int EFFORT_DATA_PROGRESS_SLASH_X = EFFORT_DATA_PROGRESS_X;
    public final static int EFFORT_DATA_PROGRESS_MAX_X = EFFORT_DATA_PROGRESS_X + 5;

    public final static int EFFORT_LIST_X = EFFORT_X + 8;
    public final static int EFFORT_LIST_Y = EFFORT_Y + 30;
    public final static int EFFORT_LIST_W = 105;
    public final static int EFFORT_LIST_H = 105;
    public final static int EFFORT_LIST_NAME_Y = 8;
    public final static int EFFORT_LIST_RECT_X = 5;
    public final static int EFFORT_LIST_RECT_Y = 8;
    public final static int EFFORT_LIST_RECT_W = EFFORT_LIST_W - EFFORT_LIST_RECT_X * 2;
    public final static int EFFORT_LIST_RECT_H = EFFORT_LIST_H - (EFFORT_LIST_NAME_Y>>1);

    public final static int EFFORT_DESC_X = EFFORT_X + 8;
    public final static int EFFORT_DESC_Y = EFFORT_LIST_Y + EFFORT_LIST_H + 4;
    public final static int EFFORT_DESC_W = EFFORT_W - 16;
    public final static int EFFORT_DESC_H = 50;
    public final static int EFFORT_DESC_BUTTON_W = 50;
    public final static int EFFORT_DESC_RECT_X = 5;
    public final static int EFFORT_DESC_RECT_Y = 5;
    public final static int EFFORT_DESC_RECT_W = EFFORT_DESC_W - EFFORT_DESC_RECT_X * 2 - EFFORT_DESC_BUTTON_W;
    public final static int EFFORT_DESC_RECT_H = EFFORT_DESC_H - EFFORT_DESC_RECT_Y * 2;

    public final static int EFFORT_INFO_TITLE_Y = 15;
    public final static int EFFORT_INFO_STATUS_Y = 10;
    public final static int EFFORT_INFO_TIP_X = 10;
    public final static int EFFORT_INFO_TIP_Y = EFFORT_INFO_TITLE_Y + FONT_HEIGHT;
    public final static int EFFORT_INFO_NUM_MODULE = MODULE_NUMBERS_0_A;
    public final static int EFFORT_INFO_NUM_SLASH = MODULE_UI_SLASH;
    public final static int EFFORT_INFO_ITEM_X = 10;
    public final static int EFFORT_INFO_ITEM_Y = EFFORT_INFO_TIP_Y + FONT_HEIGHT;
    public final static int EFFORT_INFO_SLASH_X = 14;
    public final static int EFFORT_INFO_NUM_X = 15;
    public final static int EFFORT_INFO_ITEM_NUM_Y = EFFORT_INFO_ITEM_Y + 6;

    public final static int EFFORT_FINISH_W = 160;
    public final static int EFFORT_FINISH_H = FONT_HEIGHT + 20;
    public final static int EFFORT_FINISH_X = SCREEN_WIDTH_HALF - (EFFORT_FINISH_W >> 1);
    public final static int EFFORT_FINISH_Y = SCREEN_WIDTH_HALF - (EFFORT_FINISH_H >> 1);
    public final static int EFFORT_FINISH_TITLE_Y = 10;
    public final static int EFFORT_FINISH_AWARD_X = 10;
    public final static int EFFORT_FINISH_AWARD_Y = EFFORT_FINISH_TITLE_Y + FONT_HEIGHT;
    public final static int EFFORT_FINISH_ITEM_X = 10;
    public final static int EFFORT_FINISH_ITEM_Y = EFFORT_FINISH_AWARD_Y + FONT_HEIGHT;
////@#endif

    //------------ActorData---------------------
    public final static int UI_ACTOR_VALUEBAR_W = 51;
    public final static int UI_ACTOR_VALUECUR_X = 5;
    public final static int UI_ACTOR_VALUE_Y = 7;
    public final static int UI_ACTOR_VALUE_NAME_Y = -9;

    public final static int UI_ACTOR_SP_CUR_X = 7;
    public final static int UI_ACTOR_SP_CUR_Y = 7;
    public final static int UI_ACTOR_SP_CUR_W = 16;
    public final static int UI_ACTOR_SP_CUR_H = 15;

    final static int ENTITY_SHOENUM_IN_IGM = 4;
    final static int SKILL_SHOENUM_IN_IGM = 3;

    //信息框坐标
    public final static int MESSAGE_SHOW_TIME = FRAME_LIMIT;
    public final static int IGM_MESSAGE_OFF_X = 5;
    public final static int IGM_MESSAGE_OFF_Y = 5;
    public final static int IGM_MESSAGE_INFO_X = IGM_MESSAGE_OFF_X;
    public final static int IGM_MESSAGE_INFO_Y = IGM_MESSAGE_OFF_Y;
    public final static int IGM_MESSAGE_W = 140;
    public final static int IGM_MESSAGE_H = IGM_MESSAGE_OFF_Y * 2 + FONT_HEIGHT;
    public final static int IGM_MESSAGE_X = SCREEN_WIDTH_HALF - (IGM_MESSAGE_W>>1);
    public final static int IGM_MESSAGE_Y = SCREEN_HEIGHT_HALF - (IGM_MESSAGE_H>>1);

    public final static int BATTLE_DESC_X = 6;
    public final static int BATTLE_DESC_Y = 116;
    public final static int BATTLE_DESC_H = 75;
    public final static int BATTLE_DESC_W = IGM_W - BATTLE_DESC_X * 2;

    public final static int BATTLE_TIP_X = 0;
    public final static int BATTLE_TIP_Y = 220;
    public final static int BATTLE_TIP_TEXT_Y = 3;
    public final static int BATTLE_TIP_W = SCREEN_WIDTH;
    public final static int BATTLE_TIP_H = FONT_HEIGHT + BATTLE_TIP_TEXT_Y * 2;
    public final static int BATTLE_TIP_TEXT_OFFX = 2;

    //是否菜单坐标
    public final static int IGM_CONFIRM_OFF_X = 5;
    public final static int IGM_CONFIRM_OFF_Y = 5;
    public final static int IGM_CONFIRM_W = 162;
    public final static int IGM_CONFIRM_H = 60;
    public final static int IGM_CONFIRM_X = SCREEN_WIDTH_HALF - (IGM_CONFIRM_W>>1);
    public final static int IGM_CONFIRM_Y = SCREEN_HEIGHT_HALF - (IGM_CONFIRM_H>>1);
    public final static int IGM_CONFIRM_TITLE_X = IGM_CONFIRM_OFF_X;
    public final static int IGM_CONFIRM_TITLE_Y = IGM_CONFIRM_OFF_Y;
    public final static int IGM_CONFIRM_ITEM_X = IGM_CONFIRM_W>>2;
    public final static int IGM_CONFIRM_ITEM_OFFX = IGM_CONFIRM_W>>1;
    public final static int IGM_CONFIRM_ITEM_OFFY = 0;
    public final static int IGM_CONFIRM_RECT_X = IGM_CONFIRM_ITEM_X - (FONT_WIDTH>>1) - 1;
    public final static int IGM_CONFIRM_RECT_W = FONT_WIDTH + 2;
    public final static int IGM_CONFIRM_RECT_H = FONT_HEIGHT + 2;

    //RMS菜单
    public final static int IGM_RMS_X = IGM_X;
    public final static int IGM_RMS_Y = IGM_Y;
    public final static int IGM_RMS_W = IGM_W;
    public final static int IGM_RMS_H = IGM_H;
    public final static int IGM_RMSSLOT_X = IGM_X + 6;
    public final static int IGM_RMSSLOT_Y = IGM_Y + 20;
    public final static int IGM_RMSSLOT_W = IGM_W - 12;
    public final static int IGM_RMSSLOT_H = 84;
    public final static int IGM_RMS_SUB_OFFSET = IGM_RMSSLOT_H;
    public final static int IGM_RMS_CURSOR_X = 20;
    public final static int IGM_RMS_CURSOR_Y = 18;
    public final static int IGM_RMS_PORTRAIT_X = 72;
    public final static int IGM_RMS_PORTRAIT_Y = 7;
    public final static int IGM_RMS_PORTRAIT_OFFX = 25;
    public final static int IGM_RMS_PORTRAIT_BG_X = 80;
    public final static int IGM_RMS_PORTRAIT_BG_Y = 18;
    public final static int IGM_RMS_PORTRAIT_BG_OFFX = 25;
    public final static int IGM_RMS_INFO_RECT_W = 134;
    public final static int IGM_RMS_INFO_RECT_H = 13;
    public final static int IGM_RMS_INFO_RECT_X = (IGM_RMSSLOT_W - 134) / 2;
    public final static int IGM_RMS_INFO_RECT_Y = 40;
    public final static int IGM_RMS_INFO_MONEY_X = IGM_RMS_INFO_RECT_X + 8;
    public final static int IGM_RMS_INFO_TOKEN_X = IGM_RMS_INFO_RECT_X + 67;
    public final static int IGM_RMS_INFO_ICON_Y = IGM_RMS_INFO_RECT_Y + 1;
    public final static int IGM_RMS_INFO_MONEY_VALUE_X = IGM_RMS_INFO_RECT_X + 25;
    public final static int IGM_RMS_INFO_TOKEN_VALUE_X = IGM_RMS_INFO_RECT_X + 84;
    public final static int IGM_RMS_INFO_VALUE_Y = IGM_RMS_INFO_ICON_Y + 2;
    public final static int IGM_RMS_TIME_RECT_W = 134;
    public final static int IGM_RMS_TIME_RECT_H = 13;
    public final static int IGM_RMS_TIME_RECT_X = (IGM_RMSSLOT_W - 134) / 2;
    public final static int IGM_RMS_TIME_RECT_Y = IGM_RMS_INFO_RECT_Y + IGM_RMS_INFO_RECT_H + 7;
    public final static int IGM_RMS_TIME_ICON_X = IGM_RMS_INFO_RECT_X + 8;
    public final static int IGM_RMS_TIME_ICON_Y = IGM_RMS_TIME_RECT_Y + 1;
    public final static int IGM_RMS_TIME_HOUR_X = IGM_RMS_INFO_RECT_X + 40;
    public final static int IGM_RMS_TIME_HOUR_COLON_X = IGM_RMS_INFO_RECT_X + 43;
    public final static int IGM_RMS_TIME_MIN_X = IGM_RMS_INFO_RECT_X + 60;
    public final static int IGM_RMS_TIME_MIN_COLON_X = IGM_RMS_INFO_RECT_X + 63;
    public final static int IGM_RMS_TIME_SECOND_X = IGM_RMS_INFO_RECT_X + 80;
    public final static int IGM_RMS_TIME_VALUE_Y = IGM_RMS_TIME_ICON_Y + 2;
    public final static int IGM_RMS_ID_X = 15;
    public final static int IGM_RMS_ID_Y = 8;

    public final static int IGM_LOOT_OFF_Y = 5;
    //战斗结果窗口--经验
    public final static int IGM_LOOT_EXP_W = 150;
    public final static int IGM_LOOT_EXP_H = 170;
    public final static int IGM_LOOT_EXP_X = SCREEN_WIDTH_HALF - (IGM_LOOT_EXP_W >> 1);
    public final static int IGM_LOOT_EXP_Y = SCREEN_HEIGHT_HALF - (IGM_LOOT_EXP_H >> 1);
    public final static int IGM_LOOT_EXP_RECT_X = 6;
    public final static int IGM_LOOT_EXP_RECT_Y = 20;
    public final static int IGM_LOOT_EXP_RECT_W = IGM_LOOT_EXP_W - IGM_LOOT_EXP_RECT_X * 2;
    public final static int IGM_LOOT_EXP_RECT_H = (IGM_LOOT_EXP_H - IGM_LOOT_EXP_RECT_Y * 2) / 3;
    public final static int IGM_LOOT_EXP_RECT_OFFY = IGM_LOOT_EXP_RECT_H;
    public final static int IGM_LOOT_EXP_ICON_X = IGM_LOOT_EXP_RECT_X + 4;
    public final static int IGM_LOOT_EXP_ICON_Y = 5;
    public final static int IGM_LOOT_EXP_NUM_X = IGM_LOOT_EXP_RECT_X + 35;
    public final static int IGM_LOOT_EXP_NUM_Y = 12;
    public final static int IGM_LOOT_EXP_LEVEL_X = IGM_LOOT_EXP_ICON_X;
    public final static int IGM_LOOT_EXP_LEVEL_Y = 28;
    public final static int IGM_LOOT_EXP_BAR_X = IGM_LOOT_EXP_NUM_X;
    public final static int IGM_LOOT_EXP_BAR_Y = 30;
    public final static int IGM_LOOT_EXP_BAR_W = 95;
    public final static int LOOT_EXP_INCREASE_TIME = 15;
    public final static int IGM_LOOT_EXP_ANIM_X = IGM_LOOT_EXP_RECT_X + 100;
    public final static int IGM_LOOT_EXP_ANIM_Y = IGM_LOOT_EXP_RECT_Y + 30;

    //技能升级窗口
    public final static int IGM_LOOT_SLV_W = 176;
    public final static int IGM_LOOT_SLV_H = (FONT_HEIGHT<<1) + (IGM_LOOT_OFF_Y<<1);
    public final static int IGM_LOOT_SLV_X = SCREEN_WIDTH_HALF - (IGM_LOOT_SLV_W>>1);
    public final static int IGM_LOOT_SLV_Y = SCREEN_HEIGHT_HALF - (IGM_LOOT_SLV_H>>1);
    public final static int IGM_LOOT_SLV_GET_OFF_Y = IGM_LOOT_OFF_Y;
    public final static int IGM_LOOT_SLV_DESC_OFF_Y = IGM_LOOT_SLV_GET_OFF_Y + FONT_HEIGHT;

    //战斗结果窗口--学习技能
    public final static int IGM_LOOT_SKILL_TEXT_Y = 6;
    public final static int IGM_LOOT_SKILL_W = IGM_W;
    public final static int IGM_LOOT_SKILL_H = (FONT_HEIGHT * 3) + (IGM_LOOT_SKILL_TEXT_Y << 1);
    public final static int IGM_LOOT_SKILL_X = SCREEN_WIDTH_HALF - (IGM_LOOT_SKILL_W >> 1);
    public final static int IGM_LOOT_SKILL_Y = SCREEN_HEIGHT_HALF - (IGM_LOOT_SKILL_H >> 1);

    //战斗结果窗口--获得物品
    public final static int IGM_LOOT_ITEM_W = IGM_W;
    public final static int IGM_LOOT_ITEM_H = 155;
    public final static int IGM_LOOT_ITEM_X = SCREEN_WIDTH_HALF - (IGM_LOOT_ITEM_W >> 1);
    public final static int IGM_LOOT_ITEM_Y = SCREEN_HEIGHT_HALF - (IGM_LOOT_ITEM_H >> 1);
    public final static int IGM_LOOT_ITEM_RECT_X = 6;
    public final static int IGM_LOOT_ITEM_RECT_Y = 20;
    public final static int IGM_LOOT_ITEM_RECT_W = IGM_LOOT_ITEM_W - IGM_LOOT_ITEM_RECT_X * 2;
    public final static int IGM_LOOT_ITEM_RECT_H = IGM_LOOT_ITEM_H - IGM_LOOT_ITEM_RECT_Y * 2;
    public final static int IGM_LOOT_ITEM_NAME_X = IGM_LOOT_ITEM_RECT_X + 33;
    public final static int IGM_LOOT_ITEM_NAME_Y = IGM_LOOT_ITEM_RECT_Y + 5;
    public final static int IGM_LOOT_ITEM_NUM_X = IGM_LOOT_ITEM_RECT_X + IGM_LOOT_ITEM_RECT_W - 20;
    public final static int IGM_LOOT_ITEM_NUM_Y = IGM_LOOT_ITEM_NAME_Y + 5;
    public final static int IGM_LOOT_ITEM_ICON_X = IGM_LOOT_ITEM_NAME_X + ICON_X_OFFSET;
    public final static int IGM_LOOT_ITEM_ICON_Y = IGM_LOOT_ITEM_NAME_Y + ICON_Y_OFFSET;
    public final static int IGM_LOOT_ITEM_OFF_Y = 23;
    public final static int LOOT_SCROLL_BAR_X = IGM_LOOT_ITEM_RECT_X + IGM_LOOT_ITEM_RECT_W - 10;
    public final static int LOOT_SCROLL_BAR_Y = IGM_LOOT_ITEM_RECT_Y + 17;
    public final static int LOOT_SCROLL_BAR_H = 80;

    //战斗失败
    public final static int IGM_LOOT_FAIL_W = 176;
    public final static int IGM_LOOT_FAIL_H = (FONT_HEIGHT<<1) + (IGM_LOOT_OFF_Y<<1);
    public final static int IGM_LOOT_FAIL_X = SCREEN_WIDTH_HALF - (IGM_LOOT_FAIL_W>>1);
    public final static int IGM_LOOT_FAIL_Y = SCREEN_HEIGHT_HALF - (IGM_LOOT_FAIL_H>>1);
    public final static int IGM_LOOT_FAIL_OFF_Y = IGM_LOOT_OFF_Y;

    //地图名称
    public final static int IGM_MAP_NAME_W = 120;
    public final static int IGM_MAP_NAME_H = FONT_HEIGHT + 10;
    public final static int IGM_MAP_NAME_X = 0;
    public final static int IGM_MAP_NAME_Y = 0;
    public final static int IGM_MAP_NAME_Y2 = 5;
    //任务提示
    final static int MISSION_UI_STATE = MI_ART_FRAME_FONT;
    final static int MISSION_UI_ID = FRAME_UI_JUQING;
    //菜单提示
    final static int IGM_UI_STATE = MI_ART_FRAME_FONT;
    final static int IGM_UI_ID = FRAME_UI_BUTTON_IGM;
    //成就提示
    final static int IGM_ACH_ID = FRAME_UI_BUTTON_ACH;

    //0键提示
    public static final int UI_TITLECHOOSEBOX_W = 124;
    public static final int UI_TITLECHOOSEBOX_H = 24;
    public static final int UI_TITLECHOOSE_OFFY = 5; //改变它 可以使 整体下移
    public static final int UI_TITLECHOOSEBOX_OFFY = 2;
    final static int SHOP_UI_STATE = MI_ART_FRAME_FONT;
    final static int SHOP_UI_ID = FRAME_UI_BUTTON_SHOP;
    final static int SHOP_UI_OFFX = 52;
    final static int SHOP_UI_OFFY = 0;
    //系统提示
    final static int IGM_SYSTEM_STATE = MI_ART_FRAME_FONT;
    final static int IGM_SYSTEM_ID = FRAME_UI_TITLE_SMSSHOP;
    //loading提示
    public final static int LOADING_TIP_Y = 40;

    public final static int STATE_POS_X = 115;
    public final static int STATE_POS_Y = 125;
    public final static int STATE_ICON_X = 60;
    public final static int STATE_ICON_Y = 201;
    public final static int STATE_POS_KUANG_X = 117;
    public final static int SYS_POS_Y = 102;
    public final static int SUB_PACAGE_X = 35;
    public final static int SUB_PACAGE_Y = 14;

    final static int BATTLE_ANCHOR_X0 = 190;
    final static int BATTLE_ANCHOR_X1 = 30;
    final static int BATTLE_ANCHOR_Y0 = 150;
    final static int BATTLE_ANCHOR_Y1 = 150;
    /**我方人物坐标 X*/
    public static final short[] BATTLE_HERO_POS_X =
            { //@in X:\RPG22_CMCC\trunk\SourceCode\Const\Const_Position_N73.h
            BATTLE_ANCHOR_X0 + 20, BATTLE_ANCHOR_X0, BATTLE_ANCHOR_X0 + 20};
    /**我方人物坐标 Y*/
    public static final short[] BATTLE_HERO_POS_Y =
            {
            BATTLE_ANCHOR_Y0, BATTLE_ANCHOR_Y0 + 35, BATTLE_ANCHOR_Y0 + 70};

    /**敌方人物坐标 X*/
    public static final short[] BATTLE_ENEMY_POS_X =
            {
            BATTLE_ANCHOR_X1, BATTLE_ANCHOR_X1 + 15, BATTLE_ANCHOR_X1, BATTLE_ANCHOR_X1 + 50, BATTLE_ANCHOR_X1 + 50};
    /**敌方人物坐标 Y*/
    public static final short[] BATTLE_ENEMY_POS_Y =
            {
            BATTLE_ANCHOR_Y1, BATTLE_ANCHOR_Y1 + 35, BATTLE_ANCHOR_Y1 + 70, BATTLE_ANCHOR_Y1 + 15, BATTLE_ANCHOR_Y1 + 55};
    public static final short BATTLE_XUAN_POS_Y = 15;
    public static final short BATTLE_ACTOR_HPBAR_X = -19;
    public static final short BATTLE_ACTOR_HPBAR_W = 38;
    public static final short BATTLE_ACTOR_HPBAR_H = 2;
    public static final short BATTLE_ACTOR_BUFF_Y = -12;
    public static final short BATTLE_ACTOR_OFF_X = 50;

    public static final int BATTLE_UI_MAINMENU_BG_X = SCREEN_WIDTH_HALF;
    public static final int BATTLE_UI_MAINMENU_BG_Y = 100;
    public static final int BATTLE_UI_MAINMENU_CUESOR_X = 0;
    public static final int BATTLE_UI_MAINMENU_CUESOR_Y = -2;
    public static final int BATTLE_UI_SKILLUIBOX_Y = 110;

    /* 战斗主UI坐标顺序: 攻击, 技能, 物品, 商店, 必杀 */
    public static final int[] BATTLE_UI_MAINMENU_X =
            {
            BATTLE_UI_MAINMENU_BG_X, BATTLE_UI_MAINMENU_BG_X-27, BATTLE_UI_MAINMENU_BG_X+27, BATTLE_UI_MAINMENU_BG_X
//@#if EXIST_SPECIAL
//@            , BATTLE_UI_MAINMENU_BG_X
//@#endif
        };
    public static final int[] BATTLE_UI_MAINMENU_Y =
            {
            BATTLE_UI_MAINMENU_BG_Y+4, BATTLE_UI_MAINMENU_BG_Y + 4, BATTLE_UI_MAINMENU_BG_Y + 4, BATTLE_UI_MAINMENU_BG_Y + 32
//@#if EXIST_SPECIAL
//@            , BATTLE_UI_MAINMENU_BG_Y -41
//@#endif
    };
    public static final int BATTLE_UI_MAINMENU_SP_X = 0;
    public static final int BATTLE_UI_MAINMENU_SP_Y = 0;
    public static final int BATTLE_UI_SUBMENU_X = IGM_X;
    public static final int BATTLE_UI_SUBMENU_Y = IGM_Y;
    public static final int BATTLE_UI_SUBMENU_W = IGM_W;
    public static final int BATTLE_UI_SUBMENU_H = IGM_H;

    public static final int BATTLE_UI_INFORMATION_W = SCREEN_WIDTH;
    public static final int BATTLE_UI_INFORMATION_H = 80;
    public static final int BATTLE_UI_INFORMATION_X = 0;
    public static final int BATTLE_UI_INFORMATION_Y = SCREEN_HEIGHT - BATTLE_UI_INFORMATION_H + 5;
    public static final int BATTLE_UI_INFORMATION_SUB_X = BATTLE_UI_INFORMATION_X + 6;
    public static final int BATTLE_UI_INFORMATION_SUN_Y = BATTLE_UI_INFORMATION_Y + 6;
    public static final int BATTLE_UI_INFORMATION_SUB_W = 76;
    public static final int BATTLE_UI_INFORMATION_SUN_H = SCREEN_HEIGHT - BATTLE_UI_INFORMATION_SUN_Y;
    public static final int BATTLE_UI_INFORMATION_OFFX = BATTLE_UI_INFORMATION_SUB_W;
    public static final int BATTLE_UI_INFORMATION_OFFY = 6;
    public static final int BATTLE_UI_INFORMATION_OFFW = 0;
    public static final int BATTLE_UI_INFORMATION_OFFH = 12;
    public static final int BATTLE_UI_INFORMATION_SPACE = 3;

    public static final int BATTLE_UI_SKILL_RECT_W = SCREEN_WIDTH;
    public static final int BATTLE_UI_SKILL_RECT_OFFW = 10;
    public static final int BATTLE_UI_SKILL_RECT_H = 25;
    public static final int BATTLE_UI_SKILL_RECT_X = 0;
    public static final int BATTLE_UI_SKILL_RECT_Y = 220;

    public static final int BATTLE_UI_HERO_X = 60;
    public static final int BATTLE_UI_HERO_OFFX = 76;
    public static final int BATTLE_UI_HERO_HP_X = -32;
    public static final int BATTLE_UI_HERO_HP_Y = BATTLE_UI_INFORMATION_Y + 55;
    public static final int BATTLE_UI_HERO_MP_X = -32;
    public static final int BATTLE_UI_HERO_MP_Y = BATTLE_UI_HERO_HP_Y + 13;
    public static final int BATTLE_UI_HERO_SP_X = 7;
    public static final int BATTLE_UI_HERO_SP_Y = BATTLE_UI_INFORMATION_Y + 35;
    public static final int BATTLE_UI_HERO_NAME_OFFX = -34;
    public static final int BATTLE_UI_HERO_NAME_OFFY = -65;
    public static final int BATTLE_UI_HEROICON_OFFX = -20;
    public static final int BATTLE_UI_HEROICON_OFFY = BATTLE_UI_INFORMATION_Y + 24;
    public static final int BATTLE_UI_HERO_HP_OFFX = -42;
    public static final int BATTLE_UI_HERO_LV_X = 0;
    public static final int BATTLE_UI_HERO_LV_Y = BATTLE_UI_HERO_HP_Y - 17;
    public static final int BATTLE_UI_HERO_ICON_HP_Y = BATTLE_UI_HERO_HP_Y - 5;
    public static final int BATTLE_UI_HERO_ICON_MP_Y = BATTLE_UI_HERO_MP_Y - 5;
    public static final int BATTLE_UI_HERO_LV_NNUM_X = 25;

    public static final int BU_QUEUE_RECT_X = 40;
    public static final int BU_QUEUE_RECT_Y = 25;
    public static final int BU_QUEUE_RECT_W = 220;
    public static final int BU_QUEUE_RECT_H = 13;
    public static final int BU_QUEUE_BG_X = BU_QUEUE_RECT_X + 10;
    public static final int BU_QUEUE_BG_Y = BU_QUEUE_RECT_Y - 26;
    public static final int BU_QUEUE_ARROW_AY = BU_QUEUE_RECT_Y + 2;
    public static final int BU_QUEUE_ARROW_FY = BU_QUEUE_RECT_Y + BU_QUEUE_RECT_H - 5;
    public static final int BU_QUEUE_X = BU_QUEUE_RECT_X + 2;
    public static final int BU_QUEUE_Y = 25;
    public static final int BU_QUEUE_FOR_Y = BU_QUEUE_RECT_Y + BU_QUEUE_RECT_H + 2;
    public static final int BU_QUEUE_W = 127;
    public static final int BU_QUEUE_H = 10;
    public static final int BU_QUEUE_GOAL_X = BU_QUEUE_X + BU_QUEUE_W;
    public static final int BU_QUEUE_ANIM_TIME = 5;
    public static final int BU_QUEUE_ANIM_VX = BU_QUEUE_W / BU_QUEUE_ANIM_TIME;
    public static final int BU_QUEUE_ARROW_X = 6;
    public static final int BU_QUEUE_OFFX = 10;
    public static final int BU_QUEUE_OFFY = 25;

    public static final int BATTLE_UI_FINGER_OFFX = 0;
    public static final int BATTLE_UI_FINGER_OFFY = -20;

    public static final short BATTLE_CAMERA_SHOCK_MAX = 4;
    public static final short BATTLE_CAMERA_SHOCK_SPEED_X = 0;
    public static final short BATTLE_CAMERA_SHOCK_SPEED_Y = 4;
    public static final short BATTLE_CAMERA_CRITICAL_SPEED_X = 4;
    public static final short BATTLE_CAMERA_CRITICAL_SPEED_Y = 0;

    public static final int[] BATTLE_MI_CHOOSE_POS_X =
            {
            130, 105, 155, 130, 130};
    public static final int[] BATTLE_MI_CHOOSE_POS_Y =
            {
            170, 170, 170, 145, 195};
    public static final short BATTLE_SUB_TITLE_Y = 14;
    public static final short BATTLE_SUB_TITLE_SPACE = 25;
    public static final short BATTLE_SUB_TITLE_H = 50;
    public static final short BATTLE_ITEM_RECT_X = 6;
    public static final short BATTLE_ITEM_RECT_Y = 17;
    public static final short BATTLE_ITEM_RECT_W = BATTLE_UI_SUBMENU_W - BATTLE_ITEM_RECT_X * 2;
    public static final short BATTLE_ITEM_RECT_H = 100;
    public static final short BATTLE_ITEM_ICON_X = BATTLE_ITEM_RECT_X + 27;
    public static final short BATTLE_ITEM_ICON_Y = BATTLE_ITEM_RECT_Y + 16;
    public static final short BATTLE_ITEM_ICON_OFFX = 25;
    public static final short BATTLE_ITEM_ICON_OFFY = 23;

    public static final short BATTLE_SUB_NAME_X = 37;
    public static final short BATTLE_SUB_NAME_Y = 25;
    public static final short BATTLE_SUB_NAME_W = 115;
    public static final short BATTLE_SUB_NAME_H = 22;

    public static final int BATTLE_SKILL_NAME_W = 90;
    public static final int BATTLE_SKILL_SCROLL_BAR_X = 158;
    public static final int BATTLE_SKILL_SCROLL_BAR_Y = 38;
    public static final int BATTLE_SKILL_SCROLL_BAR_H = 60;
    public static final int BATTLE_SKILL_CURSOR_X = BATTLE_ITEM_RECT_X + 110;
    public static final int BATTLE_SKILL_CURSOR_Y = BATTLE_SUB_NAME_Y + 8;
    public static final short BATTLE_SKILL_ICON_X = 22;
    public static final short BATTLE_SKILL_ICON_Y = BATTLE_SUB_NAME_Y + 11;

    public static final int LOAD_ANIM_Y = SCREEN_HEIGHT * 3 / 4;
    public static final int LOAD_PROG_OFFX = 30;
    public static final int LOAD_PROG_Y = LOAD_ANIM_Y + 30;
    public static final int LOAD_PROG_BAR_X = 28;
    public static final int LOAD_PROG_BAR_Y = 275;
    public static final int LOAD_PROG_BAR_W = 113;
    public static final int LOAD_PROG_BAR_H = 3;
    public static final int UI_DIALOG_MAX_LINE = 2;
    public static final int UI_DIALOG_POS_X = 25;
    public static final int UI_DIALOG_POS_Y_OFF = 7;
    public static final int UI_DIALOG_WIDTH = 200;
    public static final int UI_DIALOG_HEIGHT = UI_DIALOG_MAX_LINE * FONT_HEIGHT;
    public static final int UI_DIALOG_HEAD_POS_Y = SCREEN_HEIGHT - UI_DIALOG_HEIGHT;
    public static final int UI_DIALOGUE_IMG_H = 91;
    public static final int UI_DIALOG_BOX_HEIGHT = UI_DIALOG_HEIGHT + UI_DIALOG_POS_Y_OFF * 2;
    public static final int UI_DIALOG_SPECIAL_OFFX = 7;
    public static final int UI_DIALOG_SPECIAL_OFFY = 3;
    public static final int UI_DIALOG_SCROLL_SPEED_DEFAULT = 1;
    public static final int UI_DIALOG_SCROLL_SPEED_FAST = 8;
    public static final int UI_DIALOG_SCROLL_END_Y = -30;
    public static final int UI_DIALOG_HIDE_BG_X = 30;
    public static final int UI_DIALOG_HIDE_BG_WORDS = 11;
    public static final int UI_DIALOG_BOX_OFFX = 7;
    public static final int UI_DIALOG_BOX_OFFY = 6;
    public static final int UI_DIALOG_BOX_OFFH = 12;
    public static final int UI_DIALOG_BOX_OFFW = 14;
    public static final int UI_DIALOG_NAME_TEXT_X = 8;
    public static final int UI_DIALOG_NAME_TEXT_Y = 6;
    public static final int UI_DIALOG_NAME_RECT_X = 0;
    public static final int UI_DIALOG_NAME_RECT_H = FONT_HEIGHT + UI_DIALOG_NAME_TEXT_Y * 2;

    public static final int MESSAGE_MARK_LEN = 2;
    public static final int MESSAGE_VARIABLE_LEN = 3;
    public static final int MESSAGE_MARK_VARIABLE_LEN = MESSAGE_MARK_LEN + MESSAGE_VARIABLE_LEN;



    ///---------------对话Icon显示坐标表以及相关信息-----------
    public static final int MESSAGE_ICON_LEN = 2;
    public static final int MESSAGE_VARIABLE_ICON = 3;
    public static final int MESSAGE_VARIABLE_ICON_OFFSET = 1;
    public static final int MESSAGE_ICON_VARIABLE_LEN = MESSAGE_ICON_LEN + MESSAGE_VARIABLE_ICON;
    public static final int DIALOG_ICON_X = 0;
    public static final int DIALOG_ICON_Y = 10;
    public static final int DIALOG_ICON_W = 16;


    public static final int UI_MESSAGE_W_OFF = 30;
    public static final int UI_MESSAGE_H = 40;
    public static final int UI_MESSAGE_INNER_COLOR = 0xffdd77;
    public static final int UI_MESSAGE_POS_Y_OFF = 0;
    public static final int UI_MESSAGE_MAX_LINE = 1;

    public static final int UI_BOARD_INFO_X = 20;
    public static final int UI_BOARD_INFO_Y = 15;
    public static final int UI_BOARD_W_OFF = UI_BOARD_INFO_X * 2;
    public static final int UI_BOARD_H_OFF = UI_BOARD_INFO_Y * 2;
    public static final int UI_BOARD_QUICKEN_X = 5;
    public static final int UI_BOARD_SKIP_X = SCREEN_WIDTH - 5;
    public static final int UI_BOARD_TIP_Y = SCREEN_HEIGHT - 5;
    public static final int UI_BOARD_ICON_CLIP_X = 5;
//-------------对话中的选择项的一些信息---------------------------
    public static final int UI_CHOOSEDIALOG_INNER_COLOR = 0xffdd77;
    public static final int UI_CHOOSEDIALOG_TITLE_OFFY = 2;
    public static final int UI_CHOOSEDIALOG_POS_H = 8;
    public static final int UI_CHOOSEDIALOG_POS_W = SCREEN_WIDTH;
    public static final int UI_CHOOSEDIALOG_POS_X = 15;
    public static final int UI_CHOOSEDIALOG_POS_Y = SCREEN_HEIGHT_HALF;
    public static final int UI_CHOOSEDIALOG_POS_OFF_X = 8;
    public static final int UI_CHOOSEDIALOG_POS_OFF_Y = 5;
    public static final int UI_CHOOSEDIALOG_WIDTH = SCREEN_WIDTH - (UI_CHOOSEDIALOG_POS_X<<1);
    public static final int UI_CHOOSEDIALOG_LINE = 10;
    public static final int UI_CHOOSEDIALOG_RECT_OFFY = 1;
    public static final int UI_CHOOSEDIALOG_TEXT_W = UI_CHOOSEDIALOG_POS_W - (UI_CHOOSEDIALOG_POS_OFF_X << 1);
    final static byte AMOUNT_DV = 5;
    final static byte DV_SPEED = 8;
    public static final int UI_CHOOSEDIALOG_KEY_OFFY = 2;
    public static final int UI_CHOOSEDIALOG_STR_OFFX = 15;
    public static final int UI_CHOOSEDIALOG_STR_OFFY = 22;
    public static final int UI_CHOOSEDIALOG_BOX_OFFX = 30;
    public static final int UI_CHOOSEDIALOG_BOX_OFFY = 30;

    //-----------UI动画-------------------
    final static int UI_ANIMATION_CONNER = -1;
    final static int UI_ANIMATION_HORIZ = -1;
    final static int UI_ANIMATION_VERTI = -1;
    final static int UI_ANIMATION_COLOR = COLOR_LUCENCY;

    //-----------SMS菜单-------------------
    public final static int IGM_SMS_X = IGM_X;
    public final static int IGM_SMS_Y = IGM_Y;
    public final static int IGM_SMS_W = IGM_W;
    public final static int IGM_SMS_H = IGM_H;
    public final static int IGM_SMS_RECT_OFF = 24;
    public final static int IGM_SMS_STRING_OFF = 2;
    public final static int IGM_SMS_X1 = IGM_SMS_X + IGM_SMS_W;
    public final static int IGM_SMS_DESC = IGM_RECT_Y + IGM_SMS_RECT_OFF * 4 + 8;

    //---------------SMS--------------------------
    public final static int SMS_TEXT_COLOR = COLOR_WHITE;
    public final static int SMS_MENU_ITEM_Y = 20;
    public final static int SMS_TITLE_Y = 30;
    public final static int SMS_CONTENT_Y = 60;
    public final static int SMS_DESC_Y = 180;
    public final static int SMS_SEND_TIME_Y = 250;
    public final static int SMS_TXT_CHAR_LINE = 12;
    public final static int SMS_WND_CHAR_ROW = 3;
    public final static int SMS_SEND_RESULT_SUC_Y = 60;
    public final static int SMS_SEND_RESULT_FAIL_Y = 120;
    public final static int SMS_SEND_SUCCESS_TEXT_Y = 100;
    public final static int SMS_MONEY_TIP_Y = 160;

    public final static int SMS_SCORE_TITILE_X0 = 20;
    public final static int SMS_SCORE_TITILE_X1 = 20;
    public final static int SMS_SCORE_TITILE_Y = 30;
    public final static int SMS_SCORE_DESC_Y = 60;
    public final static int SMS_SCORE_WND_W = IGM_W;
    public final static int SMS_SCORE_WND_H = FONT_HEIGHT * 2 + 12;
    public final static int SMS_SCORE_WND_X = IGM_X;
    public final static int SMS_SCORE_WND_Y = SCREEN_HEIGHT_HALF - SMS_SCORE_WND_H / 2;
    public final static int SMS_SCORE_RECT_X = 8;
    public final static int SMS_SCORE_RECT_Y = 20;
    public final static int SMS_SCORE_RECT_W = IGM_W - SMS_SCORE_RECT_X * 2;
    public final static int SMS_SCORE_RECT_H = IGM_H - SMS_SCORE_RECT_Y * 2;
    public final static int SMS_SCORE_LOOT_Y = 6;
    public final static int SMS_SCORE_LOOT_X0 = 15;
    public final static int SMS_SCORE_LOOT_X1 = 15;
    public final static int SMS_SCORE_WORD_NUM = 8;
    public final static int SMS_SCORE_BUTTON_OFF_POS = 11;

    //第一个和最后一个战斗中的敌人动画
    public static final int ANIM_FIRST_ENEMY = ANIM_ENEMY_00;
    public static final int ANIM_LAST_ENEMY = ANIM_ENEMY_26;
//@@
//@@../Const/Const_Project_N73.h
    public static final String RMS_NAME = CHANNEL_NAME + "_RPG22_N73";

//@#if EXIST_VIBRATE
//@        final static int OPTION_FLAG_SOUND = 1 << 0;
//@        final static int OPTION_FLAG_VIBRATE = 1 << 1;
//@        final static int OPTION_FLAG_SUSPEN = 1 << 2;
//@        final static int OPTION_FLAG_ALL = OPTION_FLAG_SOUND | OPTION_FLAG_VIBRATE | OPTION_FLAG_SUSPEN;
//@#else
        final static int OPTION_FLAG_SOUND = 1 << 0;
        final static int OPTION_FLAG_SUSPEN = 1 << 1;
        final static int OPTION_FLAG_ALL = OPTION_FLAG_SUSPEN;//| OPTION_FLAG_VIBRATE
        final static int OPTION_FLAG_VIBRATE = 1 << 1;
//@#endif

    public final static int IGM_DESC_TEXT_WAIT_TIME = 15;
    public final static int IGM_LIST_TEXT_WAIT_TIME = 10;
    public final static int IGM_TASK_TEXT_WAIT_TIME = 15;
    public final static int BATTLE_DESC_TEXT_WAIT_TIME = 10;

    //----新的游戏----
    public static final int NEW_GAME_ANIM_SCENE = 46;
    public static final int NEW_GAME_MAP_SCENE = 2;
//@#if QQ_FORUM
//@    final static int MI_IGM_SYSTEM_SMS_SHOP = 0;
//@    final static int MI_IGM_SYSTEM_SAVE = 1;
//@    final static int MI_IGM_SYSTEM_LOAD = 2;
//@    final static int MI_IGM_SYSTEM_OPTION = 3;
//@    final static int MI_IGM_SYSTEM_HELP = 4;
//@    final static int MI_IGM_SYSTEM_SCORE = 5;
//@    final static int MI_IGM_SYSTEM_EXIT = 6;
//@    final static int AMOUNT_MI_IGM_SYSTEM = 7;
//@#else
//@#if Channel == "GF"
//@#if SMS
//@    final static int MI_IGM_SYSTEM_SMS_SHOP = 0;
//@    final static int MI_IGM_SYSTEM_SAVE = 1;
//@    final static int MI_IGM_SYSTEM_LOAD = 2;
//@    final static int MI_IGM_SYSTEM_OPTION = 3;
//@    final static int MI_IGM_SYSTEM_HELP = 4;
//@    final static int MI_IGM_SYSTEM_EXIT = 5;
//@    final static int MI_IGM_SYSTEM_SCORE = 6;
//@    final static int AMOUNT_MI_IGM_SYSTEM = 6;
//@#else
    final static int MI_IGM_SYSTEM_SAVE = 0;
    final static int MI_IGM_SYSTEM_LOAD = 1;
    final static int MI_IGM_SYSTEM_OPTION = 2;
    final static int MI_IGM_SYSTEM_HELP = 3;
    final static int MI_IGM_SYSTEM_EXIT = 4;
    final static int AMOUNT_MI_IGM_SYSTEM = 5;
    final static int MI_IGM_SYSTEM_SMS_SHOP = 0;
    final static int MI_IGM_SYSTEM_SCORE = 5;
//@#endif
//@#else
//@#if TB_SMS
//@    final static int MI_IGM_SYSTEM_SAVE = 0;
//@    final static int MI_IGM_SYSTEM_LOAD = 1;
//@    final static int MI_IGM_SYSTEM_OPTION = 2;
//@    final static int MI_IGM_SYSTEM_HELP = 3;
//@    final static int MI_IGM_SYSTEM_EXIT = 4;
//@    final static int AMOUNT_MI_IGM_SYSTEM = 5;
//@    final static int MI_IGM_SYSTEM_SMS_SHOP = 6;
//@    final static int MI_IGM_SYSTEM_SCORE = 7;
//@#else
//@    final static int MI_IGM_SYSTEM_SMS_SHOP = 0;
//@    final static int MI_IGM_SYSTEM_SAVE = 1;
//@    final static int MI_IGM_SYSTEM_LOAD = 2;
//@    final static int MI_IGM_SYSTEM_OPTION = 3;
//@    final static int MI_IGM_SYSTEM_HELP = 4;
//@    final static int MI_IGM_SYSTEM_SCORE = 5;
//@    final static int MI_IGM_SYSTEM_EXIT = 6;
//@    final static int AMOUNT_MI_IGM_SYSTEM = 7;
//@#endif
//@#endif
//@#endif

//@#if EXIST_EFFORT
//@    final static int MI_EFFORT_EFFORT = 0;
//@    final static int MI_EFFORT_DESIGN = 1;
//@    final static int AMOUNT_MI_EFFORT = 2;
//@#endif

    //U I 各 窗 口 角 边 面 ID
    final static int WND_IGM_CONNER = MODULE_UI_CONNER_6;
    final static int WND_IGM_HORIZ = MODULE_UI_HORIZ_6;
    final static int WND_IGM_VERTI = MODULE_UI_VERTI_6;
    final static int WND_IGM_COLOR = COLOR_LIGHT_BLUE;

    //对话框
    public final static int UI_DIALOG_CONNER = MODULE_UI_CONNER_1;//MODULE_UI_CONNER_4,MODULE_UI_HORIZ_4,MODULE_UI_VERTI_4
    public final static int UI_DIALOG_HORIZ = MODULE_UI_HORIZ_1;
    public final static int UI_DIALOG_VERTI = MODULE_UI_VERTI_1;
    public final static int UI_DIALOG_COLOR = COLOR_LIGHT_BLUE;

    //战斗伤害
//    final static int BATTLE_UI_DAMAGE_CRITICAL_WORD = MODULE_UI_CRITICAL;
    final static int BATTLE_UI_DAMAGE_DODGE_WORD = MODULE_UI_DODGE;

    final static int BATTLE_UI_DAMAGE_CRITICAL_NUM = MODULE_NUMBERS_0_F;
    final static int BATTLE_UI_DAMAGE_SKILL_NUM = MODULE_NUMBERS_0_D;
    final static int BATTLE_UI_DAMAGE_NORMAL_NUM = MODULE_NUMBERS_0_D;
    final static int BATTLE_UI_DAMAGE_RECOVERHP_NUM = MODULE_NUMBERS_0_E;
    final static int BATTLE_UI_DAMAGE_RECOVERMP_NUM = MODULE_NUMBERS_0_E;

    final static int SUSPEN_APPEAR_TIME = FRAME_LIMIT >> 1;

    //-----------状态窗口------------------
    final static int IGM_UI_STATUS_OFFH = 8;
    final static int IGM_UI_STATUS_OFFW = 3;
    final static int IGM_UI_STATUS_OFFY = 12;

    //-----------技能窗口------------------
    final static int SKILL_TITLE_COLOR = COLOR_BLACK;

    //-----------帮助窗口------------------
    final static int HELP_PAGE_Y = 5;
    final static int HELP_TEXT_X = 5;
    final static int HELP_TEXT_Y = 5;
    final static int HELP_BOX_X = 4;
    final static int HELP_BOX_Y = 24;
    final static int HELP_BOX_W = -HELP_BOX_X<<1;
    final static int HELP_BOX_H = -HELP_BOX_Y<<1;
    final static int HELP_STR_LINE = 6;
//@#if Channel == "GF"
//@#if GF_PACKAGE in ("CONNECT", "SMS")
    final static int HELP_COUNT = STR_SYSTEM_GS_HELP_CONNECT_END - STR_SYSTEM_GS_HELP_NAME_0 - HELP_STR_LINE + 2;
//@#else
//@    final static int HELP_COUNT = STR_SYSTEM_GS_HELP_NAME_END - STR_SYSTEM_GS_HELP_NAME_0 - HELP_STR_LINE + 2;
//@#endif
//@#else
//@    final static int HELP_COUNT = STR_SYSTEM_GS_HELP_NAME_END - STR_SYSTEM_GS_HELP_NAME_0 - HELP_STR_LINE + 2;
//@#endif
    final static int HELP_ARROW_UP_X = SCREEN_WIDTH_HALF - (IGM_RECT_W >> 1)-15;
    final static int HELP_ARROW_DOWN_X = HELP_ARROW_UP_X-18;
    final static int HELP_ARROW_OFFECT_Y = 19;

    //-----------关于窗口------------------
    final static int ABOUT_PAGE_Y = 5;
    final static int ABOUT_TEXT_X = 5;
    final static int ABOUT_TEXT_Y = 5;
    final static int ABOUT_BOX_X = 4;
    final static int ABOUT_BOX_Y = 24;
    final static int ABOUT_STR_LINE = 6;
    final static int ABOUT_COUNT = STR_SYSTEM_GS_ABOUT_NAME_END - STR_SYSTEM_GS_ABOUT_NAME_0 - ABOUT_STR_LINE + 2;
    final static int ABOUT_BOX_W = -ABOUT_BOX_X<<1;
    final static int ABOUT_BOX_H = -ABOUT_BOX_Y<<1;
    final static int ABOUT_ARROW_UP_X = HELP_ARROW_UP_X;
    final static int ABOUT_ARROW_DOWN_X = HELP_ARROW_DOWN_X;
    final static int ABOUT_ARROW_OFFECT_Y = HELP_ARROW_OFFECT_Y;
    //-----------设置窗口------------------
    final static int SYSTEM_OPTION_X1 = 50;
    final static int SYSTEM_OPTION_Y1 = 226;
    final static int SYSTEM_OPTION_X2 = 188;
    final static int SYSTEM_OPTION_Y2 = 226;
    final static int SYSTEM_OPTION_RECT_X = 8;
    final static int SYSTEM_OPTION_RECT_Y = 21;
    final static int SYSTEM_OPTION_RECT_W = IGM_W - SYSTEM_OPTION_RECT_X * 2;
    final static int SYSTEM_OPTION_RECT_H = 164;
    final static int SYSTEM_OPTION_NAME_X = SYSTEM_OPTION_RECT_X + 45 - 11;
    final static int SYSTEM_OPTION_OPEN_X = SYSTEM_OPTION_RECT_X + 85;
    final static int SYSTEM_OPTION_CLOSE_X = SYSTEM_OPTION_RECT_X + 120;
    final static int SYSTEM_OPTION_ITEM_Y = SYSTEM_OPTION_RECT_Y + 30;
    final static int SYSTEM_OPTION_FLAG_Y = -11;
    final static int SYSTEM_OPTION_CURSOR_X = SYSTEM_OPTION_NAME_X + 10;
    final static int SYSTEM_OPTION_CURSOR_Y = SYSTEM_OPTION_ITEM_Y + 4;
    final static int SYSTEM_OPTION_ITEM_OFFY = 30;
    final static int SYSTEM_OPTION_DESC_Y = 130;
    final static int OPTION_WORD_NUM_PERLINE = 8;

    //-----------loding-----------
    final static int LOADING_TIME_GLOBE = 16;
    final static int LOADING_TIME_MENU = 16;
    final static int LOADING_TIME_SCENE = 20;
    final static int LOADING_TIME_BATTLE = 8;
    final static int FREE_TIME_BATTLE = 6;

    //-----------软键状态------------------
    final static int SOFTKEY_UI_STATE = MI_ART_FRAME_FONT;
    final static int SOFTKEY_UI_CONFIRM = FRAME_UI_KEY_ENTER;
    final static int SOFTKEY_UI_CANCEL = FRAME_UI_KEY_CANCEL;
    final static int SOFTKEY_UI_NONE = -1;

    public static final int SOFT_SELECT_X = 4;
    public static final int SOFT_CANCEL_X = SCREEN_WIDTH - 4;
    public static final int SOFT_SELECT_ALIGN = ALIGN_LEFT_TOP;
    public static final int SOFT_CANCEL_ALIGN = ALIGN_RIGHT_TOP;
    public static final int SOFT_Y = SCREEN_HEIGHT - FONT_HEIGHT;

    //-----------软键坐标偏移------------------
    final static int SOFTKEY_IGM_CONFIRM_OFF_X = 0;
    final static int SOFTKEY_IGM_CANCEL_OFF_X = -25;
    final static int SOFTKEY_IGM_OFF_Y = -14;
    final static int SOFTKEY_IGM_RECT_OFF_X = 5;
    final static int SOFTKEY_IGM_RECT_OFF_Y = 5;

    //-----------装备栏显示数量--------------
    final static int AMOUNT_PORTRAIT_EXPRESSION = 5;
    final static int IGM_STATUS_VALUE_SHOWNUM = 3;
    final static int IGM_EQUIP_VALUE_ROW = 3;
    final static int IGM_EQUIP_VALUE_COL = 2;

    //-----------装备部位--------------
    final static int AMOUNT_EQUIP_PART = 3;
//@@
//@#break
//@#case "NOKIA_N7370":
//@#include "../Const/Const_Position_N7370.h"
//@#include "../Const/Const_Project_N7370.h"
//@#break
//@#case "NOKIA_N7610":
//@#include "../Const/Const_Position_N7610.h"
//@#include "../Const/Const_Project_N7610.h"
//@#break
//@#case "SONY_K700":
//@#include "../Const/Const_Position_K700.h"
//@#include "../Const/Const_Project_K700.h"
//@#break
//@#case "SEK790":
//@#include "../Const/Const_Position_SEK790.h"
//@#include "../Const/Const_Project_SEK790.h"
//@#break
//@#case "MOTO_V8":
//@#include "../Const/Const_Position_V8.h"
//@#include "../Const/Const_Project_V8.h"
//@#break
//@#case "MOTO_E680":
//@#include "../Const/Const_Position_E680.h"
//@#include "../Const/Const_Project_E680.h"
//@#break
//@#case "MOTO_E2":
//@#include "../Const/Const_Position_E2.h"
//@#include "../Const/Const_Project_E2.h"
//@#break
//@#case "MOTO_E6":
//@#include "../Const/Const_Position_E6.h"
//@#include "../Const/Const_Project_E6.h"
//@#break
//@#case "NOKIA_E62":
//@#include "../Const/Const_Position_E62.h"
//@#include "../Const/Const_Project_E62.h"
//@#break
//@#case "SONY_S700":
//@#include "../Const/Const_Position_S700.h"
//@#include "../Const/Const_Project_S700.h"
//@#break
//@#case "SAMSUNG_D608":
//@#include "../Const/Const_Position_D608.h"
//@#include "../Const/Const_Project_D608.h"
//@#break
//@#case "NOKIA_N6230I":
//@#include "../Const/Const_Position_N6230i.h"
//@#include "../Const/Const_Project_N6230i.h"
//@#break
//@#case "LG_KG90n":
//@#include "../Const/Const_Position_KG90n.h"
//@#include "../Const/Const_Project_KG90n.h"
//@#}
//@@../Const/Const_ProjectCommon.h
    /**
     * 项目使用的常量
     */
    final static int GAME_MONEY_MAX = 9999999;
    final static int GAME_CONSUMP_MAX = 9999999;
    final static int GAME_SCORE_MAX = 9999;
    final static int GAME_KILLS_MAX = 99999;
    final static int GAME_WIN_MAX = 99999;
    final static int GAME_TOKEN_MAX = 999;
    final static int GAME_ICON_NUM_MAX = 1000;

    final static int DIALOG_CHOOSE_MAX = 5;

    final static int SPLASH_MOVEIMG = 0;
    final static int SPLASH_DOG = 1;
    final static int SPLASH_OVER = 2;

    final static int PARALLEL_STEP_ATTACKSET_ACT = 0;
    final static int PARALLEL_STEP_ATTACKSET_BUFF = 1;

    final static int PARALLEL_STEP_ATTACK_DOING = 0;
    final static int PARALLEL_STEP_ATTACK_FRAME_END = 1;
    final static int PARALLEL_STEP_ATTACK_WAITEND = 2;
    final static int PARALLEL_STEP_ATTACK_RESET = 3;

    final static int PARALLEL_STEP_FEEDBACK_WAIT = 0;
    final static int PARALLEL_STEP_FEEDBACK_HURT = 1;
    final static int PARALLEL_STEP_FEEDBACK_APPLY = 2;
    final static int PARALLEL_STEP_FEEDBACK_END = 3;

    final static int PARALLEL_STEP_BUFFSET_JUDGE = 0;
    final static int PARALLEL_STEP_BUFFSET_RESET = 1;

    final static int EFFECT_CHILD_FIRST = 1;
    final static int EFFECT_CHILD_LAST = 3;

    final static int FEEDBACK_CHILD_FIRST = 4;
    final static int FEEDBACK_CHILD_LAST = 6;

    final static int PARALLEL_TYPE_RUSH = 0;
    final static int PARALLEL_TYPE_ATTACK_SET = 1;
    final static int PARALLEL_TYPE_SINGLE_ATTACK = 2;
    final static int PARALLEL_TYPE_ATTACK_ANIM = 3;
    final static int PARALLEL_TYPE_HURT_ANIM = 4;
    final static int PARALLEL_TYPE_FEEDBACKSET = 5;
    final static int PARALLEL_TYPE_DAMAGEVALUE = 6;
    final static int PARALLEL_TYPE_EFFECT_ANIM = 7;
    final static int PARALLEL_TYPE_ACTOR_RESET = 8;
    final static int PARALLEL_TYPE_BUFFSET = 9;
    final static int PARALLEL_TYPE_BUFF_ANIM = 10;
    final static int PARALLEL_TYPE_DIALOG = 11;
    final static int PARALLEL_TYPE_UIANIM = 12;
    final static int PARALLEL_TYPE_BATTLE_END = 13;
    final static int PARALLEL_TYPE_POSE = 14;
    final static int PARALLEL_TYPE_WINDOW = 15;
    final static int PARALLEL_TYPE_SOUND = 16;

    final static int PARALLEL_CONDITION_AUTO = 0;
    final static int PARALLEL_CONDITION_MANUAL = 1;

    final static int BATTLE_RUSH_TYPE_STAND = 0;
    final static int BATTLE_RUSH_TYPE_NEAR = 1;
    final static int BATTLE_RUSH_TYPE_CENTER = 2;

    final static int[] EQUIP_TYPE = {ENUM_EQUIPS_TYPE_WEAPON, ENUM_EQUIPS_TYPE_ARMOR, ENUM_EQUIPS_TYPE_BOOT, ENUM_EQUIPS_TYPE_OTHER};

    //----菜单----
    final static int MENU_SLOT_NONE = 0;
    final static int MENU_SLOT_MAINMENU = 1;
    final static int MENU_SLOT_IGM = 2;
    final static int MENU_SLOT_STATUS = 3;
    final static int MENU_SLOT_EQUIP_ROLE = 4;
    final static int MENU_SLOT_EQUIP_TYPE = 5;
    final static int MENU_SLOT_EQUIP_PROPOSE = 6;
    final static int MENU_SLOT_ARTICAL = 7;
    final static int MENU_SLOT_TEAM = 8;
    final static int MENU_SLOT_SYSTEM = 9;
    final static int MENU_SLOT_ADVANCE = 10;
    final static int MENU_SLOT_SKILL = 11;
    final static int MENU_SLOT_PACKAGE = 12;
    final static int MENU_SLOT_MISSION = 13;
    final static int MENU_SLOT_MISSION_LIST = 14;
    final static int MENU_SLOT_RMS = 15;
    final static int MENU_SLOT_CHOOSE = 16;
    final static int MENU_SLOT_BATTLE_MAIN = 17;
    final static int MENU_SLOT_SHOP = 18;
    final static int MENU_SLOT_BUY_OR_SELL = 19;
    final static int MENU_SLOT_CATALOG = 20;
    final static int MENU_SLOT_CONFIRM = 21;
    final static int MENU_SLOT_SEL_ALLY = 22;
    final static int MENU_SLOT_2D_LIST = 23;
    final static int MENU_SLOT_SKILL_STATE = 24;
    final static int MENU_SLOT_SKILLWND = 25;
    final static int MENU_SLOT_DEAL_NUM = 26;
    final static int MENU_SLOT_BUFFER = 27;
    final static int MENU_SLOT_SMS = 28;
    final static int MENU_SLOT_OPTION = 29;
    final static int MENU_SLOT_EFFORT = 30;
    final static int MENU_SLOT_EFFORT_LIST = 31;
    final static int MENU_SLOT_OTHER = 32;
    final static int MENU_SLOT_PORTABLE_SHOP = 33;
    final static int MENU_SLOT_MIXTRUE = 34;
    final static int MENU_SLOT_MIXTRUE_LIST = 35;
    final static int MENU_SLOT_EQUIP_STANDBY = 36;
    final static int MENU_SLOT_HELP = 29;
    final static int MENU_SLOT_ABOUT = 29;
    final static int AMOUNT_MENU_SLOT = 37;

    final static int ENTITY_LIST_ITEM = 0;
    final static int ENTITY_LIST_SKILL = 1;
    final static int ENTITY_LIST_EQUIP = 2;
    final static int ENTITY_LIST_BUY = 3;
    final static int ENTITY_LIST_SELL = 4;
    final static int ENTITY_LIST_BATTLE = 5;
    final static int ENTITY_LIST_MISSION = 6;
    final static int ENTITY_LIST_SMS = 7;
    final static int ENTITY_LIST_BATTLE_SMS = 8;
    final static int ENTITY_LIST_EFFORT = 9;
    final static int ENTITY_LIST_MIXTRUE = 10;
    final static int ENTITY_LIST_SOUL = 11;
    final static int ENTITY_LIST_PORTABLE_SHOP = 12;
    final static int ENTITY_LIST_PORTABLE_GOODS = 13;
    final static int ENTITY_LIST_EVENT = 14;
    final static int AMOUNT_ENTITY_LIST = 15;
//@#if Channel == "GF"
    final static int MI_IGM_STATUS = 0;
    final static int MI_IGM_EQUIP = 1;
    final static int MI_IGM_ADVANCE = 2;
    final static int MI_IGM_ITEM = 3;
    final static int MI_IGM_SKILL = 4;
    final static int MI_IGM_SYSTEM = 5;
    final static int AMOUNT_MI_IGM = 6;
    final static int MI_IGM_MIXTRUE = 7;
//@#else
//@#if TB
//@    final static int MI_IGM_STATUS = 0;
//@    final static int MI_IGM_EQUIP = 1;
//@    final static int MI_IGM_ADVANCE = 2;
//@    final static int MI_IGM_ITEM = 3;
//@    final static int MI_IGM_SKILL = 4;
//@    final static int MI_IGM_SYSTEM = 5;
//@    final static int AMOUNT_MI_IGM = 6;
//@    final static int MI_IGM_MIXTRUE = 7;
//@#else
//@    final static int MI_IGM_STATUS = 0;
//@    final static int MI_IGM_EQUIP = 1;
//@    final static int MI_IGM_ADVANCE = 2;
//@    final static int MI_IGM_MIXTRUE = 3;
//@    final static int MI_IGM_ITEM = 4;
//@    final static int MI_IGM_SKILL = 5;
//@    final static int MI_IGM_SYSTEM = 6;
//@    final static int AMOUNT_MI_IGM = 7;
//@#endif
//@#endif
    final static int MI_CONFIRM_YES = 0;
    final static int MI_CONFIRM_NO = 1;
    final static int AMOUNT_MI_CONFIRM = 2;

    final static int MI_IGM_EQUIP_PROPOSE_CHANGE = 0;
    final static int MI_IGM_EQUIP_PROPOSE_ADVANCE = 1;
    final static int MI_IGM_EQUIP_PROPOSE_CHANCEL = 2;
    final static int AMOUNT_MI_IGM_EQUIP_PROPOSE = 3;

    final static int MI_IGM_EQUIP_SUB_CHANGEEQUIP = 0;
    final static int MI_IGM_EQUIP_SUB_INFOEQUIP = 1;
    final static int AMOUNT_MI_IGM_EQUIP_SUB = 2;

    final static int MI_IGM_RMS1 = 0;
    final static int MI_IGM_RMS2 = 1;
    final static int AMOUNT_MI_IGM_RMS = 2;

    final static int MI_IGM_SHOP_BUY = 0;
    final static int MI_IGM_SHOP_SELL = 1;
    final static int AMOUNT_MI_IGM_SHOP = 2;

    final static int MI_MISSION_MAIN = 0;
    final static int MI_MISSION_SUB = 1;
    final static int AMOUNT_MI_MISSION = 2;

    final static int MI_IGM_PRACTICE_MIXTRUE = 0;
    final static int MI_IGM_PRACTICE_SOUL = 1;
    final static int AMOUNT_MI_IGM_PRACTICE = 2;

//@#if EXIST_VIBRATE
//@    final static int MI_SYSTEM_OPTION_SOUND = 0;
//@    final static int MI_SYSTEM_OPTION_VIBRATE = 1;
//@    final static int MI_SYSTEM_OPTION_SUSPEN = 2;
//@    final static int AMOUNT_MI_SYSTEM_OPTION = 3;
//@#else
//@#if JAR_LIMIT_300K
//@    final static int MI_SYSTEM_OPTION_SOUND = 0;
//@    final static int AMOUNT_MI_SYSTEM_OPTION = 1;
//@    final static int MI_SYSTEM_OPTION_SUSPEN = 1;
//@    final static int MI_SYSTEM_OPTION_VIBRATE = 3;
//@#else
    final static int MI_SYSTEM_OPTION_SOUND = 0;
    final static int MI_SYSTEM_OPTION_SUSPEN =1;
    final static int AMOUNT_MI_SYSTEM_OPTION = 2;
    final static int MI_SYSTEM_OPTION_VIBRATE = 3;
//@#endif
//@#endif
    final static int DISPLAY_PACKAGE_SIZE = 4;
    final static int DISPLAY_BATTLESKILL_SIZE = 4;

    final static int DISPLAY_EQUIP_ROW = 4;
    final static int DISPLAY_EQUIP_COL = 4;

    final static int DISPLAY_BATTLE_ITEM_ROW = 4;
    final static int DISPLAY_BATTLE_ITEM_COL = 5;

    final static int EQUIP_ADVANCE_SHOW_NUM = 2;
    final static int SKILL_ADVANCE_SHOW_NUM = 2;

    //----LOADING动画的actor数
//@#if JAR_LIMIT_300K
//@    public static final int LOADING_ANIM_NUMBER = 0;
//@#else
    public static final int LOADING_ANIM_NUMBER =  ACTION_LOAD_LOADING_LAST - ACTION_LOAD_LOADING_FIRST + 1;
//@#endif

    //----队伍反馈
    final static byte TEAM_FEEDBACK_OK = 0;
    final static byte TEAM_FEEDBACK_LESS = 1;
    final static byte TEAM_FEEDBACK_MORE = 2;
    final static byte TEAM_FEEDBACK_INGURE = 3;

    //----任务----
    final static int MI_IGM_MISSION_MAIN = 0;
    final static int MI_IGM_MISSION_SUB = 1;
    final static int AMOUNT_MI_IGM_MISSION = 2;

    public static final int BATTLE_MI_ATT = 0;  //攻击
    public static final int BATTLE_MI_SKILL = 1; // 技能
    public static final int BATTLE_MI_ITEM = 2; //物品
    public static final int BATTLE_MI_SHOP = 3; //商店
    public static final int BATTLE_MI_SPECIAL = 4; //灭
    public static final int AMOUNT_BATTLE_MI = 5;
//@#if EXIST_SPECIAL
//@    public static final int AMOUNT_BATTLE_MI_NO_SP = AMOUNT_BATTLE_MI;
//@#else
    public static final int AMOUNT_BATTLE_MI_NO_SP = AMOUNT_BATTLE_MI - 1;
//@#endif

    //----damage value----
    final static int DAMAGE_VALUE_OFFSET_X = 0;
    final static int DAMAGE_VALUE_OFFSET_Y = 1;
    final static int DAMAGE_VALUE_OFFSET_NUM = 2;
    final static int DAMAGE_VALUE_OFFSET_MODULEID = 3;
    final static int DAMAGE_VALUE_OFFSET_TYPE = 4;
    final static int DAMAGE_VALUE_OFFSET_TIME = 5;
    final static int DAMAGE_VALUE_OFFSET_ALIGN = 6;
    final static int AMOUNT_DAMAGE_VALUE_OFFSET = 7;
    final static int DAMAGE_VALUE_COUNT_MAX = 32;

    final static int DAMAGE_VALUE_TYPE_NUM = 0;
    final static int DAMAGE_VALUE_TYPE_STR = 1;

    final static int ACTOR_INTEAM_FLAG_STATUS = 1 << 0;
    final static int ACTOR_INTEAM_FLAG_ENABLE = 1 << 1;

    final static int BV_LASTEXP = 0;
    final static int BV_EXP = 1;
    final static int BV_NEXTEXP = 2;
    final static int BV_SP = 3;
    final static int BV_HP = 4;
    final static int BV_MP = 5;
    final static int BV_SPMAX = 6;
    final static int BV_HPMAX = 7;
    final static int BV_MPMAX = 8;
    final static int BV_PHYDMG = 9;
    final static int BV_PSYDMG = 10;
    final static int BV_DP = 11;
    final static int BV_CH = 12;
    final static int BV_DODGE= 13;
    final static int BV_SPEED = 14;
    final static int BV_LV = 15;
    final static int BV_SPINCREASE = 16;
    final static int BV_FRAME_INFO = 17;

    /**合成经验*/
    final static int BV_PROFIC_EXP = 18;
    /**合成熟练度*/
    final static int BV_PROFIC_LV = 19;
    /**战斗中该人物所处的阵营*/
    final static int BV_CAMP = 20;
    /**当在战斗中该Actor所处的状态*/
    final static int BV_STATE = 21;
    /**当前回合人物改变的魔数值的偏移量*/
    final static int BV_DMG_VALUE = 22;
    /**当前回合人物改变的数值的类型*/
    final static int BV_DMG_TYPE = 23;
    /**buff免疫*/
    final static int BV_AVOID_BUFF = 24;
    /**普通攻击目标数量*/
    final static int BV_TARGET_COUNT = 25;
    /**是我方队员的时候,存储队伍中的序号;是敌方成员的时候,存储数据库中的编号*/
    final static int BV_ID = 26;
    final static int BV_HEIGHT = 27;
    final static int BV_ACTION = 28;
    final static int BV_AP = 29;
    final static int BV_QUEUE = 30;
    final static int BV_EQUIP_CHANGE = 31;
    final static int AMOUNT_BV = 32;

    final static int BATTLE_UI_NONE = 0;
    final static int BATTLE_UI_BG = 1 << 0; //背景
    final static int BATTLE_UI_PROTRAIT = 1 << 1; //头像
    final static int BATTLE_UI_NAME = 1 << 2; //姓名
    final static int BATTLE_UI_VALUE = 1 << 3; //HP槽
    final static int BATTLE_UI_QUEUE = 1 << 4; //等待队列
    final static int BATTLE_UI_DIALOG = 1 << 5; //对话框
    final static int BATTLE_UI_WINDOW = 1 << 6; //信息框
    final static int BATTLE_UI_BAT_MESSAGE = 1 << 7; //战斗信息
    final static int BATTLE_UI_TARGET = 1 << 8; //战斗进度条头像选中光标
    final static int BATTLE_UI_ALL = BATTLE_UI_BG | BATTLE_UI_PROTRAIT | BATTLE_UI_NAME | BATTLE_UI_VALUE | BATTLE_UI_QUEUE | BATTLE_UI_TARGET;
    final static int BATTLE_UI_FRAME_HIDE = BATTLE_UI_QUEUE; //帧信息屏蔽的UI

    final static int BATTLE_ANIM_NONE = 0;
    final static int BATTLE_ANIM_BG = 1 << 0; //背景图
    final static int BATTLE_ANIM_ACTOR = 1 << 1; //人物
    final static int BATTLE_ANIM_INFO = 1 << 2; //信息
    final static int BATTLE_ANIM_CURSOR = 1 << 3; //光标
    final static int BATTLE_ANIM_ALL = BATTLE_ANIM_BG | BATTLE_ANIM_ACTOR | BATTLE_ANIM_INFO | BATTLE_ANIM_CURSOR;

    final static int BATTLE_AIM_ALLY = 1 << 0;
    final static int BATTLE_AIM_FOE = 1 << 1;
    final static int BATTLE_AIM_SELF = 1 << 2;
    final static int BATTLE_AIM_EXCLUDE_SELF = 1 << 3;
    final static int BATTLE_AIM_DEAD = 1 << 4;
    final static int BATTLE_AIM_ALIVE = 1 << 5;

    final static int AVOID_RECOVER_NONE = 0;
    final static int AVOID_RECOVER_RELIFE = 1 << 0;
    final static int AVOID_RECOVER_HP = 1 << 1;
    final static int AVOID_RECOVER_MP = 1 << 2;
    final static int AVOID_RECOVER_HPMP = AVOID_RECOVER_RELIFE | AVOID_RECOVER_HP;
    final static int AVOID_RECOVER_ALL = AVOID_RECOVER_RELIFE | AVOID_RECOVER_HP | AVOID_RECOVER_MP;

    final static int BATTLE_SP_INCREASE = 5;

    //----wnd----
    final static int WND_ARTICAL_DATA_CATALOG = 0;
    final static int WND_ARTICAL_DATA_ID = 1;
    final static int WND_ARTICAL_DATA_ACTOR = 2;
    final static int WND_ARTICAL_DATA_ACTION = 3;

    final static int WND_ACTION_USE_ITEM = 0;
    final static int WND_ACTION_USE_EQUIP = 1;
    final static int WND_ACTION_USE_BOOK = 1;
    final static int WND_ACTION_ERROR = 1;

    // 游戏状态
    public static final byte GS_INIT = 0;
    public static final byte GS_LOAD_INIT = 1;
    public static final byte GS_LOGO = 2;
    public static final byte GS_LOAD_COMMON = 3;
    public static final byte GS_SOUND = 4;
    public static final byte GS_SPLASH = 5;
    public static final byte GS_LOADMENU = 6;
    public static final byte GS_MAINMENU = 7;
    public static final byte GS_LOADSCENE = 8;
    public static final byte GS_LOADCOMMON = 9;
    public static final byte GS_CONTINUE = 10;
    public static final byte GS_SCENE = 11;
    public static final byte GS_RETRY_LEVEL = 12;
    public static final byte GS_THE_END = 13;
    public static final byte GS_GAME_OVER = 14;
    public static final byte GS_GAME_LINK = 15;
    public static final byte GS_PAUSE = 16;
    public static final byte GS_QUIT = 17;
//@#if Channel == "GF"
    public static final byte GS_PREVIEW = 18;
    public static final byte GS_SLOT_MACHINE = 19;
    public static final byte GS_UPLOADSCORE = 20;
//@#if GF_PACKAGE in ("CONNECT","SMS")
    public static final byte GS_CONNECT = 21;
//@#endif
//以下这两个状态本来是为了GF的“G+游戏圈”和“精品推荐”准备的，但没用上
//    public static final byte GS_GAMEZONE = 21;
//    public static final byte GS_RECOMMEND = 22;
//@#endif

    //----窗口----
    final static int WND_CHILDREN_SIZE = 8;
    final static int WND_DATA_SIZE = 8;
    final static int WND_DATA_SIZE_INC = 8;
//@#if EXIST_EFFORT
//@    final static int WND_EFFORT_DATA_PER = 3;
//@    final static int WND_EFFORT_DATA_SIZE = WND_EFFORT_DATA_PER * 6;
//@    final static int WND_EFFORT_AWARD_SIZE = 16;
//@#endif
    final static int WND_MIXTRUE_INFO_PER = 3;
    final static int WND_MIXTRUE_INFO_SIZE = WND_MIXTRUE_INFO_PER * 4;

    final static int WND_MIXTRUE_MESSAGE_PER = 2;
    final static int WND_MIXTRUE_MESSAGE_SIZE = WND_MIXTRUE_INFO_PER * 4;

    final static int WND_COMPARE_DATA_PER = 2;
    final static int WND_COMPARE_DATA_SIZE = (AMOUNT_ENUM_EQUIPS_PURPOSE0 - 1) * WND_COMPARE_DATA_PER;

    final static int WND_EQUIP_ADVANCE_PER = 3;
    final static int WND_EQUIP_ADVANCE_SIZE = ((DB_EQUIPS_PURPOSE_LAST - DB_EQUIPS_PURPOSE0) / 2 + 2) * WND_EQUIP_ADVANCE_PER;

    final static int WND_SKILL_ADVANCE_PER = 3;
    final static int WND_SKILL_ADVANCE_SIZE = ((DB_SKILLS_PURPOSE_LAST - DB_SKILLS_PURPOSE0) / 2 + 3) * WND_SKILL_ADVANCE_PER;

    final static int WND_LOOT_ITEM_DATA_BASE = 2;
    final static int WND_LOOT_ITEM_DATA_PER = 3;
    final static int WND_LOOT_ITEM_DATA_SIZE = WND_LOOT_ITEM_DATA_BASE + WND_LOOT_ITEM_DATA_PER * 10;

    final static int WND_LOOT_EXP_DATA_PER = 3;
    final static int WND_LOOT_EXP_DATA_SIZE = WND_LOOT_ITEM_DATA_PER * TEAM_SIZE;

    final static int WND_LOOT_LEARN_DATA_PER = 2;
    final static int WND_LOOT_LEARN_DATA_SIZE = WND_LOOT_LEARN_DATA_PER * TEAM_SIZE;

    final static int WND_ROLE_VALUE_DATA_PER = 2;
    final static int WND_ROLE_VALUE_DATA_NUM = 8;
    final static int WND_ROLE_VALUE_DATA_SIZE = WND_LOOT_LEARN_DATA_PER * WND_ROLE_VALUE_DATA_NUM;

    final static int WND_SKIN_CONNER = 0;
    final static int WND_SKIN_HORIZ = 1;
    final static int WND_SKIN_VERTI = 2;
    final static int WND_SKIN_COLOR = 3;
    final static int WND_SKIN_ALPHA = 4;
    final static int AMOUNT_WND_SKIN = 5;

    final static int WND_ABSTRACT = 0;
    final static int WND_IGM = 1;
    final static int WND_SYSTEM = 2;
    final static int WND_EFFORT = 3;
    final static int WND_PORTABLE_SHOP = 4;
    final static int WND_STATUS = 5;
    final static int WND_EQUIP = 6;
    final static int WND_PACKAGE_ITEM = 7;
    final static int WND_MIXTRUE = 8;
    final static int WND_PACKAGE_EQUIP = 9;
    final static int WND_SKILL = 10;
    final static int WND_MISSION = 11;
    final static int WND_LOAD = 12;
    final static int WND_SAVE = 13;
    final static int WND_IGM_DESC = 14;
    final static int WND_STATUS_ROLE = 15;
    final static int WND_EQUIP_ROLE = 16;
    final static int WND_EQUIP_STANDBY = 17;
    final static int WND_EQUIP_ADVANCE = 18;
    final static int WND_EQUIP_COMPARE = 19;
    final static int WND_EQUIP_PROPOSE = 20;
    final static int WND_MIXTRUE_LIST = 21;
    final static int WND_MIXTRUE_INFO = 22;
    final static int WND_MIXTRUE_MESSAGE = 23;
    final static int WND_SKILL_ROLE = 24;
    final static int WND_SKILL_ADVANCE = 25;
    final static int WND_SMS_MENU = 26;
    final static int WND_SMS_MESSAGE = 27;
    final static int WND_EFFORT_LIST = 28;
    final static int WND_EFFORT_DESC = 29;
    final static int WND_EFFORT_INFO = 30;
    final static int WND_EFFORT_FINISH = 31;
    final static int WND_SHOP = 32;
    final static int WND_SHOP_CONFIRM = 33;
    final static int WND_BATTLE_MAIN = 34;
    final static int WND_BATTLE_AIM = 35;
    final static int WND_BATTLE_DESC = 36;
    final static int WND_BATTLE_SKILL = 37;
    final static int WND_BATTLE_ITEM = 38;
    final static int WND_BATTLE_TIP = 39;
    final static int WND_LOOT_ITEM = 40;
    final static int WND_LOOT_EXP = 41;
    final static int WND_LOOT_FAIL = 42;
    final static int WND_LOOT_NEWSKILL = 43;
    final static int WND_LOOT_SCORE = 44;
    final static int WND_RMS_SLOT = 45;
    final static int WND_MAINMENU = 46;
    final static int WND_OPTION = 47;
    final static int WND_HELP = 48;
    final static int WND_ABOUT = 49;
    final static int WND_EXIT = 50;
    final static int WND_MORE_GAME = 51;
    final static int WND_ROLE_VALUE = 52;
    final static int WND_QQ_FORUM = 53;
    final static int WND_QQ_SCORE = 54;
    final static int WND_LIST = 55;
    final static int WND_CONFIRM = 56;
    final static int WND_MESSAGE = 57;
    final static int WND_2D_LIST = 58;
    final static int WND_SOFT_KEY = 59;
    final static int WND_STATUS_DESC = 60;
    final static int WND_ANIMATION = 61;
    // 播放成就动画窗口
    final static int WND_UI_ANIMATION = 62;

    final static int DAMAGE_TYPE_NORMAL = 0;
    final static int DAMAGE_TYPE_MISS = 1;
    final static int DAMAGE_TYPE_CRITICAL = 2;
    final static int DAMAGE_TYPE_ITEM = 3;
    final static int DAMAGE_TYPE_SKILL = 4;
    final static int DAMAGE_TYPE_AUTO = 5;

    public static final byte EP_NORMAL = 0;
    public static final byte EP_DIALOG = 1;
    public static final byte EP_LOADBATTLE = 2;
    public static final byte EP_BATTLE = 3;
    public static final byte EP_IGM = 4; // 菜单
    public static final byte EP_INTERLUDE = 5;
    public static final byte EP_FREEBATTLE = 6;
    public static final byte EP_EVENT = 7;

    public static final int SAVESLOT_MAX = 4;
    public static final int RMS_INFO_RMS = 0;
    public static final int RMS_INFO_ALLY0 = 1;
    public static final int RMS_INFO_ALLY1 = 2;
    public static final int RMS_INFO_ALLY2 = 3;
    public static final int RMS_INFO_ALLY3 = 4;
    public static final int RMS_INFO_ALLY4 = 5;
    public static final int RMS_INFO_SCENEID = 6;
    public static final int RMS_INFO_MONEY = 7;
    public static final int RMS_INFO_TOKEN = 8;
    public static final int RMS_INFO_HOUR = 9;
    public static final int RMS_INFO_MIN = 10;
    public static final int RMS_INFO_SECOND = 11;
    public static final int AMOUNT_RMS_MESSAGE = 12;

    final static int IGM_DESC_DATA_TYPE = 0;
    final static int IGM_DESC_DATA_ID = 1;
    final static int IGM_DESC_SHOW_CHAR = 2;

    final static int BATTLE_DESC_DATA_TYPE = 0;
    final static int BATTLE_DESC_DATA_ID = 1;

    final static int SHOP_DATA_TYPE = 0;

    final static int IGM_STATUS_ISTARGET = 0;
    final static int IGM_STATUS_DATA_ACTORID = 0;
    final static int IGM_STATUS_DATA_SHOWFOUCS = 1;

    final static int IGM_EQUIPRLOE_DATA_TYPE = 0;

    final static int IGM_EQUIPDATA_DATA_ACTOR = 0;

    final static int IGM_EQUIPSTANDBY_DATA_ID = 0;

    final static int IGM_EQUIPADVANCE_DATA_ID = 0;

    final static int IGM_ARTICAL_DATA_ACTOR = 0;
    final static int IGM_ARTICAL_DATA_FOUCS = 1;
    final static int IGM_ARTICAL_DATA_COUNT = 2;
    final static int IGM_ARTICAL_DATA_NOW_LV = 3;

    final static int IGM_ARTICAL_DATA_ID = 0;

    final static int AMOUNT_BUFF_EFFECT = 35;
    final static int AMOUNT_BUFF_ICON = 5;
    final static int AMOUNT_BUFF_SET = 20;
    final static int AMOUNT_BUFF_BASIC = AMOUNT_BUFF_EFFECT + AMOUNT_BUFF_ICON;

    final static byte ENTITY_INFO_TYPE_NONE = -1;
    final static byte ENTITY_INFO_TYPE_COUNT = 0;
    final static byte ENTITY_INFO_TYPE_COST_MP = 1;
    final static byte ENTITY_INFO_TYPE_SKILL_LV = 2;
    final static byte ENTITY_INFO_TYPE_COUNT_NULL = 3;

    final static byte ENTITY_ENABLE_TYPE_NONE = -1;
    final static byte ENTITY_ENABLE_TYPE_PROFIC = 0;

    final static byte ACTOR_DATA_TYPE_HP = 0;
    final static byte ACTOR_DATA_TYPE_MP = 1;
    final static byte ACTOR_DATA_TYPE_EXP = 2;

    final static byte SMS_RELIFE_ID = 2;
    final static byte SMS_ELIMINATE_ID = 7;
//@@
//@@../Const/Const_ProjectIndependence.h

    //第一个和最后一个主人公动画文件
    public static final int ANIM_FIRST_LA = ANIM_HERO_00;
    public static final int ANIM_LAST_LA = ANIM_HERO_02;
    //第一个战斗主人公资源
    public static final int ANIM_FIRST_BA = ANIM_BA_00;

//    //箱子动画
//    public static final int ANIM_CHEST = ANIM_OBJECT_01_CHEST;

    //站立
    public final static int ACTION_STAND_DOWN = ACTION_HERO_00_STAND_DOWN;
    public final static int ACTION_STAND_UP = ACTION_HERO_00_STAND_UP;
    public final static int ACTION_STAND_LEFT = ACTION_HERO_00_STAND_LEFT;
    public final static int ACTION_STAND_RIGHT = ACTION_HERO_00_STAND_RIGHT;
    //移动
    public final static int ACTION_MOVE_DOWN = ACTION_HERO_00_WALK_DOWN;
    public final static int ACTION_MOVE_UP = ACTION_HERO_00_WALK_UP;
    public final static int ACTION_MOVE_LEFT = ACTION_HERO_00_WALK_LEFT;
    public final static int ACTION_MOVE_RIGHT = ACTION_HERO_00_WALK_RIGHT;
    //飞行
    public final static int ACTION_TRANSPORT_DOWN = ACTION_HERO_00_TRANSPORT_DOWN;
    public final static int ACTION_TRANSPORT_UP = ACTION_HERO_00_TRANSPORT_UP;
    public final static int ACTION_TRANSPORT_LEFT = ACTION_HERO_00_TRANSPORT_LEFT;
    public final static int ACTION_TRANSPORT_RIGHT = ACTION_HERO_00_TRANSPORT_RIGHT;
    //战斗
    public final static int ACTION_BATTLE_WAIT = ACTION_BA_00_WAIT;
    public final static int ACTION_BATTLE_HURT = ACTION_BA_00_HURT;
    public final static int ACTION_BATTLE_GUARD = ACTION_BA_00_GUARD;
    public final static int ACTION_BATTLE_DODGE = ACTION_BA_00_DODGE;
    public final static int ACTION_BATTLE_DIE = ACTION_BA_00_DIE;
    public final static int ACTION_BATTLE_WEAK = ACTION_BA_00_WEAK;
    public final static int ACTION_BATTLE_G_HURT = ACTION_BA_00_G_HURT;
    public final static int ACTION_BATTLE_WIN = ACTION_BA_00_WIN;
    //死亡特效
//@#if ONE_SKILL_EF
//@    public final static int ACTION_BATTLE_DIE_EFFECT = ACTION_BUFFLIGHT_E_COLUMN;
//@#else
    public final static int ACTION_BATTLE_DIE_EFFECT = ACTION_BUFFLIGHT_DIE;
//@#endif
    //反弹特效
    public final static int ACTION_BATTLE_FEEDBACK_EFFECT = 1;
    //特殊攻击
    public final static int FRAME_SPECIAL_ATTACK = 0;
    //特殊物品ID
    public final static int ITEM_ID_FLY = 11;
    public final static int ITEM_ID_KEY = 12;

    //----头像----
    final static int ANIM_PORTRAIT_0 = ANIM_PORTRAIT_00;

    final static int BATTLE_SHOP_ITEM_COUNT = 10;
    final static int BATTLE_SHOP_ITEM_ID = 12;
    final static int SHOP_DEAL_NUM_LIMIT = 99;

    final static int AMOUNT_SCENE = 46;

    public static final int RMS_RETURN_STATE_OK = 0;
    public static final int RMS_RETURN_STATE_ERROR = 1;
    public static final int RMS_RETURN_STATE_NULL = 2;

    public final static int IGNORE_FOCUS = -1;

    //----游戏属性----
    final static int GAME_ATTRIB_WEATHER = 0;
    final static int AMOUNT_GAME_ATTRIB = 1;

    //----战斗属性----
    final static int BATTLE_PARA_CAN_ESCAPE = 0;
    final static int BATTLE_PARA_LOSE_CONTINUE = 1;
    final static int BATTLE_PARA_ENEMY_TEAM_ID = 2;
    final static int BATTLE_PARA_SHOW_UI = 3;
    final static int AMOUNT_BATTLE_PARA = 4;

    //----实体----
    final static int ENTITY_DATA_SIZE = 8;
    final static int ENTITY_DATA_SIZE_INC = 8;

//索引
    final static int WND_ARTICAL_ITEM = 0;
    final static int WND_ARTICAL_EQUIP = 1;
    final static int WND_ARTICAL_BOOK = 2;
    final static int WND_ARTICAL_SPEC = 3;
    final static int WND_ARTICAL_GOLD = 4;

    public static final byte HEADIMAGE_XIMENQING = 0;
    public static final byte HEADIMAGE_LISHISHI = 1;
    public static final byte HEADIMAGE_LIPINGER = 2;
    public static final byte HEADIMAGE_PANJINLIAN = 3;
    public static final byte HEADIMAGE_PANGCHUNMEI = 4;
    public static final byte HEADIMAGE_WUSONG = 5;
    public static final byte HEADIMAGE_REN = 6;
    public static final byte HEADIMAGE_LANG = 7;
    public static final byte HEADIMAGE_BOSS = 8;
//@@

//@#if RUN_SCRIPT_IN_IGM
//@@..\Const\Const_UIScript.h
    final static byte UI_SCRIPT_STATE_RUN = 0;
    final static byte UI_SCRIPT_STATE_DIALOG = 1;
    final static byte UI_SCRIPT_STATE_WAIT = 2;

    final static byte UI_LIMIT_CUSOR = 0;
    final static byte UI_LIMIT_PROTRAIT = 1;
    final static byte UI_LIMIT_POS_H = 2;
    final static byte UI_LIMIT_POS_V = 3;
    final static byte UI_LIMIT_MEESAGE = 4;
    final static byte UI_LIMIT_EXPRESSION = 5;
    final static byte AMOUNT_UI_LIMIT = 6;

    final static byte MENU_UI_ACTOR_COUNT = 8;
//@@
//@#endif

//@#if SMS
//@#include "../Const/Const_SMS.h"
//@#endif

//@#if not USE_API_NEXTRECORDID
//@@../Const/Const_RMS.h
    public final static int RMS_COMMONRECORD_ID = 1;
    public final static int RMS_SCENERECORD_ID_OFFSET = RMS_COMMONRECORD_ID + 1;
//@@
//@#endif

}
