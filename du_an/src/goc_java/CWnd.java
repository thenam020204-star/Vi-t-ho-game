import javax.microedition.lcdui.*;

/**
 * <p>Title: </p>
 *
 * <p>Description:
 * DoModule方式在进行的时候令自己的父窗口失去焦点,在DoModule之后关闭自身,但不将自身从父窗口的子窗口列表中
 * Remove.
 *
 *
 * </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class CWnd
        implements Const
{
    static CActor s_actorUI;

    CWnd m_parent; //父窗口
    CWnd[] m_children; //子窗口
    CWnd m_suspen; //悬浮窗口
    int m_childCount; //子窗口数量
    int m_type; //窗口类型
    boolean m_show; //是否显示
    int m_menuSlot; //光标索引
    boolean m_isTop; //是否为顶窗口
    int m_title; //标题
    int m_state; //按键响应
    int m_focusID; //焦点ID，-1为有焦点，>=0则第m_focusID个子窗口有焦点
    boolean m_closeActive;


    /**
     * 这次更新是否受到自动命令的影响
     */
    static boolean s_autoCmd = false;
    static int s_autoCmdType = -1;
    static int s_autoCmdPara = -1;
    static boolean s_autoCmdConfirmed = false;
    static boolean s_autoCmdReturned = false;
    static int s_autoPauseTime = 0;

    int[] m_data;
    int m_dataCount;
    int m_showTimer;
    int m_id;

    //----渲染----
    int[] m_rect; //矩形范围
    int[] m_skin; //皮肤
    int m_count; //当前List的数量
    int m_showNum; //当前List显示框显示几项
    int m_select; //List选中
    int m_listStartPos; //当前List显示第一项
    int[] m_keyUpdate; //更新响应按键
    boolean m_isLoop; //是否循环
    int m_timer; //计时器
    int m_textPosMax; //滚屏、翻页最大值
    int m_timeBuffer; //缓冲时间
    int m_column; //列
    Image m_imgRect; //透明色
    //----实体List指针----
    CEntity m_curSelect = null;
    CEntity m_showFirst = null;

    private static CWnd s_pool[] = null;
    final static int CWND_POOL_SIZE = 32;
    final static int CWND_ACTIVE_SIZE = 2;
    static int s_poolIdleID = 0;
//@#if DEBUG_STACKINFO
//@#if DEBUG_STACKINFO_CWND
//@    String m_debugStackInfo = "";
//@    Throwable m_debugStackInfoEx = null;
//@#endif
//@#endif

    private CWnd()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CWnd.jpp
        m_childCount = 0;
        m_children = new CWnd[WND_CHILDREN_SIZE];
        m_skin = new int[AMOUNT_WND_SKIN];
        m_activeWnd = new CWnd[CWND_ACTIVE_SIZE];
        m_type = WND_ABSTRACT;
        m_state = WND_STATE_WAIT;
        m_isTop = false;
        m_id = 0;
//@#if not SUPPORT_MEMORY_RECOVER
//@        m_rect = new int[4];
//@        m_data = new int[WND_DATA_SIZE_MAX];
//@#endif
    }

    int GetChildIDByType(int type)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CWnd.jpp
        int res = -1;
        for (int i = 0; i < m_childCount; i++)
        {
            if (m_children[i].m_type == type)
            {
                res = i;
                break;
            }
        }
        return res;
    }

    void ReturnFocus()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CWnd.jpp
        if (null != m_parent)
        {
            m_parent.m_focusID = -1;
        }
        else if (m_isTop)
        {
            CGame.CloseAllWnd();
        }
    }

    void SetChildFocused(int id)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CWnd.jpp
        m_children[id].m_focusID = -1;
        m_focusID = id;
    }

    void SetAllChildrenShown(boolean show)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CWnd.jpp
        for (int i = 0; i < m_childCount; i++)
        {
            m_children[i].m_show = show;
        }
    }

    /**
     * 关闭这个窗口,如果这个窗口有父窗口,那么将焦点转移给它的父窗口,失去焦点的窗口不
     * 释放自身,由父窗口注销这个窗口.
     */
    void Close()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CWnd.jpp
        ReturnFocus();
        m_show = false;
    }

    void DoModule(int time)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CWnd.jpp
        if (null != m_parent)
        {
            m_parent.m_focusID = m_id;
        }
        m_focusID = -1;
        m_show = true;
        m_select = -1;
        m_state = WND_STATE_WAIT;
        m_showTimer = time;
    }

    void DoModule()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CWnd.jpp
        DoModule( -1);
    }

    void AppendChild(CWnd child)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CWnd.jpp
        child.m_parent = this;
        m_children[m_childCount] = child;
        m_children[m_childCount].m_id = m_childCount;
        m_childCount++;
    }

    void InsertChild(CWnd child, int id)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CWnd.jpp
        for (int i = m_childCount; i > id; i--)
        {
            m_children[i] = m_children[i - 1];
        }
        m_children[id] = child;
        m_childCount++;
    }

    void RemoveAllChildren()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CWnd.jpp
        for (int i = 0; i < m_childCount; i++)
        {
            m_children[i].Release();
            m_children[i] = null;
        }
        m_childCount = 0;
    }

    CWnd RemoveChild(int id)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CWnd.jpp
        CWnd res = null;
        if (id < m_childCount)
        {
            res = m_children[id];
            res.m_parent = null;
            for (int i = id; i < m_childCount - 1; i++)
            {
                m_children[i] = m_children[i + 1];
            }
            m_childCount--;
        }
        return res;
    }

    void Free()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CWnd.jpp
        m_dataCount = 0;
        m_childCount = 0;
        m_keyUpdate = null;
        m_parent = null;
        m_curSelect = null;
        m_showFirst = null;
        m_imgRect = null;
        DeleteActiveWnd();
//@#if SUPPORT_MEMORY_RECOVER
        m_rect = null;
        m_data = null;
//@#else
//@        m_rect[RECT_W] = m_rect[RECT_H] = -1;
//@        for (int i = 0; i < WND_DATA_SIZE; i++)
//@        {
//@            m_data[i] = 0;
//@        }
//@#endif
    }

//@@../CWnd/CWnd_Pool.h
    static void InitPool()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Pool.h
        if (s_pool == null)
        {
            s_pool = new CWnd[CWND_POOL_SIZE];
            s_poolIdleID = CWND_POOL_SIZE - 1;
            for (int i = 0; i < CWND_POOL_SIZE; i++)
            {
                s_pool[i] = new CWnd();
            }
        }
    }

    /**
     * 从缓存池中得到实例，如果池中已经没有可用对象，让虚拟机抛出异常，
     * 不用刻意去计算池的大小，抛出异常之后再处理。
     *
     * @return CWnd
     */
    static CWnd GetInstance()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Pool.h
        CWnd res = s_pool[s_poolIdleID];
//@#if DEBUG_STACKINFO
//@#if DEBUG_STACKINFO_CWND
//@        System.out.println("-----CWnd-----");
//@        System.out.println("取出第" + s_poolIdleID + "个CWnd");
//@        res.m_debugStackInfo = CDebug.GetStackInfo();
//@        res.m_debugStackInfoEx = CDebug.GetThrowableStaceInfo();
//@//        res.m_debugStackInfoEx.printStackTrace();
//@        System.out.println("--------------");
//@#endif
//@#endif
        s_poolIdleID--;
        return res;
    }

    /**
     * 将对象归还内存池，直到下次被申请，这个对象不能再次被使用。
     */
    void Release()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Pool.h
        for (int i = 0; i < m_childCount; i++)
        {
            m_children[i].Release();
            m_children[i] = null;
        }
        if (m_suspen != null)
        {
            m_suspen.Release();
            m_suspen = null;
        }
        Free();
        s_poolIdleID++;
//@#if DEBUG_STACKINFO
//@#if DEBUG_STACKINFO_CWND
//@        System.out.println("-----CWnd-----");
//@        System.out.println("归还第" + s_poolIdleID + "个CWnd");
//@        System.out.println("归还于:");
//@//        CDebug.GetThrowableStaceInfo().printStackTrace();
//@        System.out.println("分配于:");
//@//        m_debugStackInfoEx.printStackTrace();
//@        System.out.println("--------------");
//@#endif
//@#endif
        s_pool[s_poolIdleID] = this;
    }

    /**
     * J2ME不支持析构函数，CWnd加入这个方法是借助J2SE的功能进行检查，检查没有被Release回缓存池被释放的CWnd对象。
     * 在真实设备以及部分模拟器上，这个函数永远不会被执行。
     */
    protected void finalize()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Pool.h
        System.out.println("Fatal Error: CWnd Finalize in GC()!");
//@#if DEBUG_STACKINFO
//@#if DEBUG_STACKINFO_CWND
//@        System.out.println("Allocated at: ");
//@        System.out.println("---------");
//@        m_debugStackInfoEx.printStackTrace();
//@        System.out.println("---------");
//@#endif
//@#endif
        Release();
    }

//@@
//@@../CWnd/CWnd_Data.h
    void InitData(int size)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Data.h
//@#if SUPPORT_MEMORY_RECOVER
        if (m_data == null)
        {
            m_data = new int[size];
        }
//@#endif
    }

    void AddData(int value)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Data.h
//@#if SUPPORT_MEMORY_RECOVER
        if (m_dataCount >= m_data.length)
        {
            int[] tmp = new int[m_data.length + WND_DATA_SIZE_INC];
            System.arraycopy(m_data, 0, tmp, 0, m_data.length);
            m_data = null;
            m_data = tmp;
        }
//@#endif
        m_data[m_dataCount] = value;
        m_dataCount++;
    }

//@@
//@@../CWnd/CWnd_Skin.h
    void SetSkin(int conner, int horiz, int verti, int color, int alpha)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Skin.h
        m_skin[WND_SKIN_CONNER] = conner;
        m_skin[WND_SKIN_HORIZ] = horiz;
        m_skin[WND_SKIN_VERTI] = verti;
        m_skin[WND_SKIN_COLOR] = color;
        m_skin[WND_SKIN_ALPHA] = alpha;
//@#if SUPPORT_ALPHA_UI
        if (alpha == T_TRUE)
        {
            if (m_imgRect == null)
            {
                int horizH = 0;
                int vertiW = 0;
                if (m_skin[WND_SKIN_HORIZ] >= 0)
                {
                    horizH = s_actorUI.GetModuleHeight(m_skin[WND_SKIN_HORIZ]);
                }
                if (m_skin[WND_SKIN_VERTI] >= 0)
                {
                    vertiW = s_actorUI.GetModuleWidth(m_skin[WND_SKIN_VERTI]);
                }
                int[] rgb = new int[(m_rect[RECT_W] - (vertiW << 1)) * (m_rect[RECT_H] - (horizH << 1))];
                for (int i = 0; i < rgb.length; i++)
                {
                    rgb[i] = (COLOR_ALPHA | color);
                }
                m_imgRect = Image.createRGBImage(rgb, m_rect[RECT_W] - (vertiW << 1), m_rect[RECT_H] - (horizH << 1), true);
            }
        }
//@#endif
    }

    static void GetSkinRes(CActor actor)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Skin.h
        s_actorUI = actor;
    }

//@@
//@@../CWnd/CWnd_Command.h
    static byte s_UICtrlMode = 0;
    CWnd[] m_activeWnd;
    int m_activeWndCount = 0;

    void SetListKey(int keyNext, int keyPrev, int keyConf, boolean loop, int menuSlot)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Command.h
        if (m_keyUpdate == null)
        {
            m_keyUpdate = new int[AMOUNT_KEY_PRESS];
        }
        m_keyUpdate[KEY_PRESS_NEXT] = keyNext;
        m_keyUpdate[KEY_PRESS_PREV] = keyPrev;
        m_keyUpdate[KEY_PRESS_CONF] = keyConf;
        m_isLoop = loop;
        m_menuSlot = menuSlot;
    }

    void UpdateCommand()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Command.h
        int oldMenuItem = CGame.s_menuSlot[m_menuSlot];
        int newMenuItem = 0;
        boolean isReturn = false;
        m_state = WND_STATE_WAIT;

        if (m_keyUpdate != null)
        {
            switch (s_UICtrlMode)
            {
            case UI_CONTROL_MANUAL: //玩家控制
                newMenuItem = CGame.UpdateMenuSlot(m_menuSlot, m_count, m_keyUpdate[KEY_PRESS_NEXT], m_keyUpdate[KEY_PRESS_PREV], m_keyUpdate[KEY_PRESS_CONF],
                        m_isLoop);
                isReturn = CGame.IsKeySinglePressed(GKEY_SOFTRIGHT);
                break;
            case UI_CONTROL_AI: //AI控制
            case UI_CONTROL_SCRIPT: //脚本控制
                if (!m_closeActive)
                {
                    if (s_autoPauseTime > 0)
                    {
                        s_autoPauseTime--;
                        return;
                    }
                    newMenuItem = CGame.UpdateAIControl(this);
                    isReturn = s_autoCmdReturned;
                }
                break;
            }

            if (CGame.s_menuLoop)
            { //如果循环
                m_state = WND_STATE_CYCEL;
                ResetTimer();
            }
            else if (newMenuItem > oldMenuItem)
            { //如果光标下移
                m_state = WND_STATE_PAGEDOWN;
                ResetTimer();
            }
            else if (newMenuItem < oldMenuItem)
            { //如果光标上移
                m_state = WND_STATE_PAGEUP;
                ResetTimer();
            }
        }
        if (isReturn || (m_showTimer == 0))
        { //如果返回
//@#if RUN_SCRIPT_IN_IGM
            if (CGame.s_UILimit[UI_LIMIT_CUSOR] >= 0)
            {
                CGame.ShowUILimitInfo();
            }
            else
//@#endif
            {
                m_state = WND_STATE_RETURN;
                ResetTimer();
            }
        }
        else
        { //如果确定
            m_select = CGame.s_menuConfirmID;
        }
    }

    void AddActiveWnd(CWnd wnd)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Command.h
        m_activeWnd[m_activeWndCount] = wnd;
        m_activeWndCount++;
    }

    void DeleteActiveWnd()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Command.h
        for (int i = 0; i < m_activeWndCount; i++)
        {
            m_activeWnd[i] = null;
        }
        m_activeWndCount = 0;
    }

    void ResetTextPos(int posMax)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Command.h
        m_textPosMax = posMax;
        ResetTimer();
    }

    void ResetTimer()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Command.h
        m_timer = 0;
        m_timeBuffer = 0;
        if (m_suspen != null)
        {
            m_suspen.ResetTimer();
        }
    }

    void UpdateSuspenTimer()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Command.h
        if ((CGame.s_optionFlag & OPTION_FLAG_SUSPEN) != 0)
        {
            if (m_timeBuffer <= SUSPEN_APPEAR_TIME)
            {
                m_timeBuffer++;
                m_show = false;
            }
            else
            {
                m_show = true;
            }
        }
        else
        {
            if (CGame.IsKeySinglePressed(GKEY_0))
            {
                m_show = !m_show;
            }
        }
    }
//@@
//@@../CWnd/CWnd_Update.h
    void Update()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Update.h
        if (m_focusID < 0)
        {
//@#if PHONE_TOUCH
//@                        if (m_rect != null)
//@                        {
//@                            if (CGame.IsTouchInPoint(IGM_X, IGM_Y + IGM_H - POINTER_RANGE_H , POINTER_RANGE_W,
//@                                                     POINTER_RANGE_H))
//@                            {
//@                                CGame.KeyHandlePressed(KEYVALUE_LEFTSOFT);
//@                            }
//@                            else if (CGame.IsTouchInPoint(IGM_X + IGM_W - POINTER_RANGE_W,
//@                                                          IGM_Y + IGM_H - POINTER_RANGE_H,
//@                                                          POINTER_RANGE_W, POINTER_RANGE_H))
//@                            {
//@                                CGame.KeyHandlePressed(KEYVALUE_RIGHTSOFT);
//@                            }
//@                            if (CGame.IsTouchInPoint(m_rect[RECT_X], m_rect[RECT_Y] + m_rect[RECT_H] - POINTER_RANGE_H, POINTER_RANGE_W, POINTER_RANGE_H))
//@                            {
//@                                CGame.KeyHandlePressed(KEYVALUE_LEFTSOFT);
//@                            }
//@                            else if (CGame.IsTouchInPoint(m_rect[RECT_X] + m_rect[RECT_W] - POINTER_RANGE_W, m_rect[RECT_Y] + m_rect[RECT_H] - POINTER_RANGE_H,
//@                                                          POINTER_RANGE_W, POINTER_RANGE_H))
//@                            {
//@                                CGame.KeyHandlePressed(KEYVALUE_RIGHTSOFT);
//@                            }
//@                        }
//@#endif
//            System.out.println(m_type + "   m_type");
            UpdateCommand();
            switch (m_type)
            {
            case WND_LIST:
                UpdateList();
                break;
            case WND_2D_LIST:
                Update2DList();
                break;
            case WND_CONFIRM:
                CGame.UpdateConfirm(this);
                break;
            case WND_MAINMENU:
                CGame.UpdateMainMenu(this);
                break;
            case WND_LOAD:
            case WND_SAVE:
                CGame.UpdateRMS(this);
                break;
            case WND_IGM:
                CGame.UpdateIGM(this);
                break;
            case WND_IGM_DESC:
                CGame.UpdateIGMDesc(this);
                break;
            case WND_BATTLE_DESC:
                CGame.UpdateBattleDesc(this);
                break;
            case WND_STATUS:
                CGame.UpdateIGMStatus(this);
                break;
            case WND_STATUS_ROLE:
                CGame.UpdateIGMStatusRole(this);
                break;
            case WND_STATUS_DESC:
                CGame.UpdateIGMStatusDesc(this);
                break;
            case WND_ROLE_VALUE:
                CGame.UpdateIGMRoleValue(this);
                break;
            case WND_EQUIP:
                CGame.UpdateIGMEquip(this);
                break;
            case WND_EQUIP_ROLE:
                CGame.UpdateIGMEquipRole(this);
                break;
            case WND_EQUIP_STANDBY:
                CGame.UpdateIGMEquipStandby(this);
                break;
            case WND_EQUIP_COMPARE:
                break;
            case WND_EQUIP_PROPOSE:
                CGame.UpdateIGMEquipPropose(this);
                break;
            case WND_PACKAGE_EQUIP:
                CGame.UpdateIGMPackageEquip(this);
                break;
            case WND_EQUIP_ADVANCE:
                CGame.UpdateIGMEquipAdvance(this);
                break;
            case WND_SKILL:
                CGame.UpdateIGMSkill(this);
                break;
            case WND_SKILL_ADVANCE:
                CGame.UpdateIGMSkillAdvance(this);
                break;
            case WND_SKILL_ROLE:
                CGame.UpdateIGMSkillRole(this);
                break;
            case WND_SYSTEM:
                CGame.UpdateIGMSystem(this);
                break;
            case WND_PORTABLE_SHOP:
                CGame.UpdatePortableShop(this);
                break;
            case WND_EXIT:
                CGame.UpdateExitGame(this);
                break;
            case WND_OPTION:
                CGame.UpdateOption(this);
                break;
            case WND_HELP:
                CGame.UpdateHelp(this);
                break;
            case WND_ABOUT:
                CGame.UpdateAbout(this);
                break;
            case WND_PACKAGE_ITEM:
                CGame.UpdateIGMPackageItem(this);
                break;
            case WND_LOOT_ITEM:
                CGame.UpdateLootItem(this);
                break;
            case WND_LOOT_EXP:
                CGame.UpdateLootExp(this);
                break;
            case WND_LOOT_NEWSKILL:
                CGame.UpdateLootNewSkill(this);
                break;
            case WND_LOOT_FAIL:
                CGame.UpdateMessage(this);
                break;
            case WND_MESSAGE:
                CGame.UpdateMessageQueue(this);
                break;
            case WND_BATTLE_MAIN:
                CGame.UpdateIGMBattleMain(this);
                break;
            case WND_BATTLE_AIM:
                CGame.UpdateRSAim(this);
                break;
            case WND_SHOP:
                CGame.UpdateShop(this);
                break;
            case WND_SHOP_CONFIRM:
                CGame.UpdateShopConfirm(this);
                break;
            case WND_MISSION:
                CGame.UpdateIGMMission(this);
                break;
            case WND_BATTLE_SKILL:
                CGame.UpdateBattleSkill(this);
                break;
            case WND_BATTLE_ITEM:
                CGame.UpdateBattleItem(this);
                break;
            case WND_BATTLE_TIP:
                break;
//@#if MORE_GAME_LINK
            case WND_MORE_GAME:
                CGame.UpdateLinkConfirm(this);
                break;
//@#endif
//@#if EXIST_EFFORT
//@            case WND_EFFORT:
//@                CGame.UpdateWndEffort(this);
//@                break;
//@            case WND_EFFORT_LIST:
//@                UpdateList();
//@                break;
//@            case WND_EFFORT_DESC:
//@                CGame.UpdateWndEffortDesc(this);
//@                break;
//@            case WND_EFFORT_INFO:
//@                CGame.UpdateWndEffortInfo(this);
//@                break;
//@            case WND_EFFORT_FINISH:
//@                CGame.UpdateWndEffortFinish(this);
//@                break;
//@#endif
            case WND_MIXTRUE:
                CGame.UpdateWndMixtrue(this);
                break;
            case WND_MIXTRUE_LIST:
                UpdateList();
                break;
            case WND_MIXTRUE_INFO:
                CGame.UpdateWndMixtrueInfo(this);
                break;
            case WND_MIXTRUE_MESSAGE:
                CGame.UpdateWndMixtrueMessage(this);
                break;
            case WND_ANIMATION:
                UpdateWndAnimation();
                break;
            case WND_UI_ANIMATION:
                UpdateWndUiAnimation();
                break;
//@#if SMS
//@            case WND_SMS_MENU: //我要发短信
//@                CGame.UpdateWndSMS(this);
//@                break;
//@            case WND_SMS_MESSAGE:
//@                CGame.UpdateSMSMessage(this);
//@                break;
//@#if QQ_FORUM
//@            case WND_QQ_FORUM:
//@                CGame.UpdateQQForum(this);
//@                break;
//@            case WND_QQ_SCORE:
//@                CGame.UpdateQQScore(this);
//@                break;
//@            case WND_LOOT_SCORE:
//@                CGame.UpdateMessage(this);
//@                break;
//@#endif
//@#endif
            case WND_SOFT_KEY:
                CGame.UpdateSoftKey(this);
                break;
            }
            //其他List指令更新
            for (int i = 0; i < m_activeWndCount; i++)
            {
                m_activeWnd[i].Update();
            }
            if (m_suspen != null)
            {
                m_suspen.UpdateSuspenTimer();
            }
            if (s_autoCmd)
            {
                s_autoCmd = false;
                s_autoCmdConfirmed = false;
                s_autoCmdReturned = false;
            }
        }
        else
        {
            m_children[m_focusID].Update();
        }
        //窗口计时器刷新
        if (m_showTimer > 0)
        {
            m_showTimer--;
        }
    }

    void UpdateList()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Update.h
        int menuItem = CGame.s_menuSlot[m_menuSlot];

        if (m_state == WND_STATE_CYCEL)
        {
            if (menuItem == 0)
            {
                m_listStartPos = 0;
            }
            else
            {
                m_listStartPos = Math.max(0, menuItem - m_showNum + 1);
            }
            return;
        }
        else if (m_state == WND_STATE_PAGEDOWN)
        {
            if (m_curSelect != null)
            {
                m_curSelect = m_curSelect.m_next;
            }
            if (menuItem - m_listStartPos >= m_showNum)
            {
                m_listStartPos++;
                if (m_showFirst != null)
                {
                    m_showFirst = m_showFirst.m_next;
                }
            }
            return;
        }
        else if (m_state == WND_STATE_PAGEUP)
        {
            if (m_curSelect != null)
            {
                m_curSelect = m_curSelect.m_prev;
            }
            if (menuItem < m_listStartPos)
            {
                m_listStartPos--;
                if (m_showFirst != null)
                {
                    m_showFirst = m_showFirst.m_prev;
                }
            }
            return;
        }
    }

    void Update2DList()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Update.h
        int menuItem = CGame.s_menuSlot[m_menuSlot];
        if (m_state == WND_STATE_RETURN || m_select >= 0)
        {
            ReturnFocus();
            return;
        }
        if (m_state == WND_STATE_PAGEDOWN)
        {
            if (m_curSelect != null)
            {
                m_curSelect = m_curSelect.m_next;
            }
            if (menuItem / m_column - m_listStartPos >= m_showNum)
            {
                m_listStartPos++;
                for (int i = 0; i < m_column; i++)
                {
                    if (m_showFirst != null)
                    {
                        m_showFirst = m_showFirst.m_next;
                    }
                }
            }
            ReturnFocus();
            return;
        }
        else if (m_state == WND_STATE_PAGEUP)
        {
            if (m_curSelect != null)
            {
                m_curSelect = m_curSelect.m_prev;
            }
            if (menuItem / m_column < m_listStartPos)
            {
                m_listStartPos--;
                for (int i = 0; i < m_column; i++)
                {
                    if (m_showFirst != null)
                    {
                        m_showFirst = m_showFirst.m_prev;
                    }
                }
            }
            ReturnFocus();
            return;
        }
        if (CGame.IsKeySinglePressed(GKEY_MOVEDOWN))
        {
            if (menuItem < m_count - m_column)
            {
                CGame.s_menuSlot[m_menuSlot] += m_column;
                if (m_curSelect != null)
                {
                    for (int i = 0; i < m_column; i++)
                    {
                        if (m_curSelect.m_next != null)
                        {
                            m_curSelect = m_curSelect.m_next;
                        }
                    }
                }
                if (menuItem / m_column - m_listStartPos >= m_showNum - 1)
                {
                    m_listStartPos++;
                    if (m_showFirst != null)
                    {
                        for (int i = 0; i < m_column; i++)
                        {
                            if (m_showFirst.m_next != null)
                            {
                                m_showFirst = m_showFirst.m_next;
                            }
                        }
                    }
                }
                m_state = WND_STATE_PAGEDOWN;
                ResetTimer();
                ReturnFocus();
            }
        }
        else if (CGame.IsKeySinglePressed(GKEY_MOVEUP))
        {
            if (menuItem >= m_column)
            {
                CGame.s_menuSlot[m_menuSlot] -= m_column;
                if (m_curSelect != null)
                {
                    for (int i = 0; i < m_column; i++)
                    {
                        if (m_curSelect.m_prev != null)
                        {
                            m_curSelect = m_curSelect.m_prev;
                        }
                    }
                }
                if (menuItem / m_column <= m_listStartPos)
                {
                    m_listStartPos--;
                    if (m_showFirst != null)
                    {
                        for (int i = 0; i < m_column; i++)
                        {
                            if (m_showFirst.m_prev != null)
                            {
                                m_showFirst = m_showFirst.m_prev;
                            }
                        }
                    }
                }
                m_state = WND_STATE_PAGEUP;
                ResetTimer();
                ReturnFocus();
            }
        }
    }

    void UpdateWndAnimation()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Update.h
        if (!s_actorUI.m_end)
        {
            s_actorUI.UpdateAnimation();
        }
        else
        {
            m_state = WND_STATE_RETURN;
            Close();
        }
    }

    void UpdateWndUiAnimation()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Update.h
        if (!CGame.s_actorAchievement.m_end)
        {
            CGame.s_actorAchievement.UpdateAnimation();
        }
        else
        {
            m_state = WND_STATE_RETURN;
            Close();
        }
    }

//@@
//@@../CWnd/CWnd_Render.h
    void Render(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Render.h
        if (m_show)
        {
            RenderRect(g);
            //自身render
            switch (m_type)
            {
            case WND_LIST:
//                CGame.RenderList(g, this);
                break;
            case WND_2D_LIST:
                CGame.Render2DList(g, this);
                break;
            case WND_CONFIRM:
                CGame.RenderConfirm(g, this);
                break;
            case WND_MAINMENU:
                CGame.RenderMainMenu(g, this);
                break;
            case WND_IGM:
                CGame.RenderIGM(g, this);
                break;
            case WND_IGM_DESC:
                CGame.RenderIGMDesc(g, this);
                break;
            case WND_BATTLE_DESC:
                CGame.RenderBattleDesc(g, this);
                break;
            case WND_RMS_SLOT:
                CGame.RenderRMSSlot(g, this);
                break;
            case WND_LOAD:
            case WND_SAVE:
                CGame.RenderRMS(g, this);
                break;
            case WND_STATUS:
                CGame.RenderIGMStatus(g, this);
                break;
            case WND_STATUS_ROLE:
                CGame.RenderIGMStatusRole(g, this);
                break;
            case WND_STATUS_DESC:
                CGame.RenderIGMStatusDesc(g, this);
                break;
            case WND_ROLE_VALUE:
                CGame.RenderIGMRoleValue(g, this);
                break;
            case WND_EQUIP:
                CGame.RenderIGMEquip(g, this);
                break;
            case WND_EQUIP_ROLE:
                CGame.RenderIGMEquipRole(g, this);
                break;
            case WND_EQUIP_STANDBY:
                CGame.RenderIGMPackageEquip(g, this);
                break;
            case WND_EQUIP_COMPARE:
                CGame.RenderIGMEquipCompare(g, this);
                break;
            case WND_EQUIP_PROPOSE:
                CGame.RenderIGMEquipPropose(g, this);
                break;
            case WND_PACKAGE_EQUIP:
                CGame.RenderIGMPackageEquip(g, this);
                break;
            case WND_EQUIP_ADVANCE:
                CGame.RenderIGMEquipAdvance(g, this);
                break;
            case WND_SKILL_ROLE:
                CGame.RenderIGMSkillRole(g, this);
                break;
            case WND_SKILL:
                CGame.RenderIGMSkill(g, this);
                break;
            case WND_SKILL_ADVANCE:
                CGame.RenderIGMSkillAdvance(g, this);
                break;
            case WND_PACKAGE_ITEM:
                CGame.RenderIGMPackageItem(g, this);
                break;
            case WND_SYSTEM:
                CGame.RenderIGMSystem(g, this);
                break;
            case WND_PORTABLE_SHOP:
                CGame.RenderPortableShop(g, this);
                break;
            case WND_LOOT_ITEM:
                CGame.RenderLootItem(g, this);
                break;
            case WND_LOOT_EXP:
                CGame.RenderLootExp(g, this);
                break;
            case WND_LOOT_FAIL:
                CGame.RenderLootFail(g, this);
                break;
            case WND_LOOT_NEWSKILL:
                CGame.RenderLootNewSkill(g, this);
                break;
            case WND_MESSAGE:
                CGame.RenderMessage(g, this);
                break;
            case WND_BATTLE_MAIN:
                CGame.RenderIGMBattleMain(g, this);
                break;
            case WND_BATTLE_AIM:
                break;
            case WND_SHOP:
                CGame.RenderShop(g, this);
                break;
            case WND_SHOP_CONFIRM:
                CGame.RenderIGMShopConfirm(g, this);
                break;
            case WND_MISSION:
                CGame.RenderIGMMission(g, this);
                break;
            case WND_BATTLE_SKILL:
                CGame.RenderBattleSkill(g, this);
                break;
            case WND_BATTLE_ITEM:
                CGame.RenderBattleItem(g, this);
                break;
            case WND_BATTLE_TIP:
                CGame.RenderBattleTip(g, this);
                break;
//@#if MORE_GAME_LINK
            case WND_MORE_GAME:
                CGame.RenderLinkConfirm(g, this);
                break;
//@#endif
            case WND_EXIT:
                CGame.RenderExit(g, this);
                break;
            case WND_HELP:
                CGame.RenderHelp(g, this);
                break;
            case WND_ABOUT:
                CGame.RenderAbout(g, this);
                break;
            case WND_OPTION:
                CGame.RenderOption(g, this);
                break;
            case WND_MIXTRUE:
                CGame.RenderWndMixtrue(g, this);
                break;
            case WND_MIXTRUE_LIST:
                CGame.RenderWndMixtrueList(g, this);
                break;
            case WND_MIXTRUE_INFO:
                CGame.RenderWndMixtrueInfo(g, this);
                break;
            case WND_MIXTRUE_MESSAGE:
                CGame.RenderWndMixtrueMessage(g, this);
                break;
            case WND_ANIMATION:
                CGame.RenderWndAnimation(g, this);
                break;
            case WND_UI_ANIMATION:
                CGame.RenderWndUiAnimation(g, this);
                break;

//@#if EXIST_EFFORT
//@            case WND_EFFORT:
//@                CGame.RenderWndEffort(g, this);
//@                break;
//@            case WND_EFFORT_LIST:
//@                CGame.RenderWndEffortList(g, this);
//@                break;
//@            case WND_EFFORT_DESC:
//@                CGame.RenderWndEffortDesc(g, this);
//@                break;
//@            case WND_EFFORT_INFO:
//@                CGame.RenderWndEffortInfo(g, this);
//@                break;
//@            case WND_EFFORT_FINISH:
//@                CGame.RenderWndEffortFinish(g, this);
//@                break;
//@#endif
//@#if SMS
//@            case WND_SMS_MENU: //我要发短信
//@                CGame.RenderWndSMS(g, this);
//@                break;
//@            case WND_SMS_MESSAGE: //短信弹出菜单
//@                CGame.RenderSMSMessage(g, this);
//@                break;
//@#if QQ_FORUM
//@            case WND_QQ_FORUM:
//@                CGame.RenderQQForum(g, this);
//@                break;
//@            case WND_QQ_SCORE:
//@                CGame.RenderQQScore(g, this);
//@                break;
//@            case WND_LOOT_SCORE:
//@                CGame.RenderIGMLootScore(g, this);
//@                break;
//@#endif
//@#endif
            case WND_SOFT_KEY:
                CGame.RenderSoftKey(g, this);
                break;
            }
        }
        if (m_suspen != null && m_suspen.m_show)
        {
            m_suspen.Render(g);
        }
        for (int i = 0; i < m_childCount; i++)
        {
            if (m_children[i].m_show)
            {
                m_children[i].Render(g);
            }
        }
    }
//@@
//@@../CWnd/CWnd_Rect.h
    void SetRect(int x, int y, int w, int h)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Rect.h
//@#if SUPPORT_MEMORY_RECOVER
        if (m_rect == null)
        {
            m_rect = new int[AMOUNT_RECT];
        }
//@#endif
        m_rect[RECT_X] = x;
        m_rect[RECT_Y] = y;
        m_rect[RECT_W] = w;
        m_rect[RECT_H] = h;
    }

    void RenderRect(Graphics g)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Rect.h
        if (m_rect == null || m_rect[RECT_W] <= 0 || m_rect[RECT_H] <= 0)
        {
            return;
        }

        int connerID = m_skin[WND_SKIN_CONNER];
        int horizID = m_skin[WND_SKIN_HORIZ];
        int vertiID = m_skin[WND_SKIN_VERTI];
        int color = m_skin[WND_SKIN_COLOR];
        int connerW = 0;
        int connerH = 0;
        int horizH = 0;
        int vertiW = 0;

        if (connerID >= 0)
        {
            connerW = s_actorUI.GetModuleWidth(connerID);
            connerH = s_actorUI.GetModuleHeight(connerID);
        }
        if (horizID >= 0)
        {
            horizH = s_actorUI.GetModuleHeight(horizID);
        }
        if (vertiID >= 0)
        {
            vertiW = s_actorUI.GetModuleWidth(vertiID);
        }

        int subX = m_rect[RECT_X] + vertiW;
        int subY = m_rect[RECT_Y] + horizH;
        int subW = m_rect[RECT_W] - (vertiW << 1);
        int subH = m_rect[RECT_H] - (horizH << 1);

        g.setClip(m_rect[RECT_X], m_rect[RECT_Y], m_rect[RECT_W], m_rect[RECT_H]);

        if (color >= 0)
        {
//@#if SUPPORT_ALPHA_UI
            if (m_skin[WND_SKIN_ALPHA] == T_TRUE)
            {
                g.drawImage(m_imgRect, subX, subY, 0);
            }
            else
//@#endif
            {
                g.setColor(color);
                g.fillRect(subX, subY, subW, subH);
            }
        }
        //绘制边
        int posX, posY;
        int length = 0;

        if (horizID >= 0)
        {
            posX = m_rect[RECT_X] + connerW;
            posY = m_rect[RECT_Y];
            length = s_actorUI.GetModuleWidth(horizID);
            while (posX < (m_rect[RECT_X] + m_rect[RECT_W] - length - connerW))
            {
                s_actorUI.DrawModule(g, horizID, posX, posY, TRANS_NONE);
                s_actorUI.DrawModule(g, horizID, posX, subY + subH, TRANS_MIRROR_ROT180);
                posX += length;
            }
            if (m_rect[RECT_W] > (connerW << 1))
            {
                s_actorUI.DrawModule(g, horizID, m_rect[RECT_W] + m_rect[RECT_X] - length - connerW, posY, TRANS_NONE);
                s_actorUI.DrawModule(g, horizID, m_rect[RECT_W] + m_rect[RECT_X] - length - connerW, subY + subH, TRANS_MIRROR_ROT180);
            }
        }
        if (vertiID >= 0)
        {
            posX = m_rect[RECT_X];
            posY = m_rect[RECT_Y] + connerH;
            length = s_actorUI.GetModuleHeight(vertiID);
            while (posY < m_rect[RECT_Y] + m_rect[RECT_H] - length - connerH)
            {
                s_actorUI.DrawModule(g, vertiID, posX, posY, TRANS_NONE);
                s_actorUI.DrawModule(g, vertiID, subX + subW, posY, TRANS_MIRROR);
                posY += length;
            }
            if (m_rect[RECT_H] > (connerH << 1))
            {
                s_actorUI.DrawModule(g, vertiID, posX, m_rect[RECT_Y] + m_rect[RECT_H] - length - connerH, TRANS_NONE);
                s_actorUI.DrawModule(g, vertiID, subX + subW, m_rect[RECT_Y] + m_rect[RECT_H] - length - connerH, TRANS_MIRROR);
            }
        }

        //绘制角
        if (connerID >= 0)
        {
            s_actorUI.DrawModule(g, connerID, m_rect[RECT_X], m_rect[RECT_Y], TRANS_NONE);
            s_actorUI.DrawModule(g, connerID, m_rect[RECT_X] + m_rect[RECT_W] - connerW, m_rect[RECT_Y], TRANS_MIRROR);
            s_actorUI.DrawModule(g, connerID, m_rect[RECT_X], m_rect[RECT_Y] + m_rect[RECT_H] - connerH, TRANS_MIRROR_ROT180);
            s_actorUI.DrawModule(g, connerID, m_rect[RECT_X] + m_rect[RECT_W] - connerW, m_rect[RECT_Y] + m_rect[RECT_H] - connerH, TRANS_ROT180);
        }
    }

//@@
//@@../CWnd/CWnd_Auto.h
    void AutoCmd()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Auto.h
        switch (s_autoCmdType)
        {
        case UI_AUTO_PAUSE:
            s_autoPauseTime += s_autoCmdPara;
            break;
        case UI_AUTO_CURSOR:
            if (m_menuSlot >= 0)
            {
                CGame.s_menuSlot[m_menuSlot] = s_autoCmdPara;
            }
            break;
        case UI_AUTO_CONFIRM:
            s_autoCmdConfirmed = true;
            break;
        case UI_AUTO_RETURN:
            s_autoCmdReturned = true;
            break;
        case UI_AUTO_RESET:
            if (m_menuSlot >= 0)
            {
                CGame.s_menuSlot[m_menuSlot] = 0;
            }
            break;
        case UI_AUTO_CONTROL:
            s_UICtrlMode = (byte) s_autoCmdPara;
            break;
        case UI_AUTO_CLOSE_ACTIVE:
            if (s_autoCmdPara == UI_CLOSE_PARENT)
            {
                m_closeActive = !m_closeActive;
            }
            else if (s_autoCmdPara >= m_activeWnd.length)
            {
                for (int i = 0; i < m_activeWnd.length; i++)
                {
                    m_activeWnd[i].m_closeActive = !m_activeWnd[i].m_closeActive;
                }
            }
            else
            {
                m_activeWnd[s_autoCmdPara].m_closeActive = !m_activeWnd[s_autoCmdPara].m_closeActive;
            }
            break;
        }
    }

//@@
//@@../CWnd/CWnd_Init.h
    void Init(int type)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        m_type = type;
        m_count = 0;
        m_state = WND_STATE_WAIT;
        m_focusID = -1;
        m_select = -1;
        m_isTop = false;
        m_listStartPos = 0;
        m_showTimer = -1;
        m_timer = 0;
        m_textPosMax = 0;
        m_title = 0;
		m_closeActive = false;

        switch (type)
        {
        case WND_LIST:
            InitWndList();
            break;
        case WND_2D_LIST:
            Init2DList();
            break;
        case WND_MAINMENU:
            InitMainMenu();
            break;
        case WND_CONFIRM:
            InitConfirm();
            break;
        case WND_RMS_SLOT:
            SetSkin(MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1, COLOR_LIGHT_GREEN, T_FALSE);
            break;
        case WND_SAVE:
        case WND_LOAD:
            InitRMS();
            break;
        case WND_OPTION:
            InitOption();
            break;
        case WND_HELP:
            InitHelp();
            break;
        case WND_ABOUT:
            InitAbout();
            break;
        case WND_EXIT:
            InitExit();
            break;
        case WND_IGM:
            InitIGM();
            break;
        case WND_IGM_DESC:
            InitIGMDesc();
            break;
        case WND_BATTLE_DESC:
            InitBattleDesc();
            break;
        case WND_STATUS:
            InitStatus();
            break;
        case WND_STATUS_ROLE:
            InitStatusRole();
            break;
        case WND_STATUS_DESC:
            InitStatusDesc();
            break;
        case WND_ROLE_VALUE:
            InitRoleValue();
            break;
        case WND_EQUIP:
            InitWndEquip();
            break;
        case WND_EQUIP_ROLE:
            InitWndEquipRole();
            break;
        case WND_EQUIP_STANDBY:
            InitWndEquipStandby();
            break;
        case WND_EQUIP_COMPARE:
            InitWndEquipCompare();
            break;
        case WND_EQUIP_PROPOSE:
            InitEquipPropose();
            break;
        case WND_PACKAGE_EQUIP:
            InitWndPackageEquip();
            break;
        case WND_EQUIP_ADVANCE:
            InitWndEquipAdvance();
            break;
        case WND_PACKAGE_ITEM:
            InitWndPackageItem();
            break;
        case WND_SKILL_ROLE:
            InitWndSkillRole();
            break;
        case WND_SKILL:
            InitWndSkill();
            break;
        case WND_SKILL_ADVANCE:
            InitWndSkillAdvance();
            break;
        case WND_SYSTEM:
            InitWndSystem();
            break;
        case WND_PORTABLE_SHOP:
            InitWndPortableShop();
            break;
        case WND_LOOT_ITEM:
            InitLootItem();
            break;
        case WND_LOOT_EXP:
            InitLootExp();
            break;
        case WND_LOOT_NEWSKILL:
            InitLootNewSkill();
            break;
        case WND_LOOT_FAIL:
            InitLootEscape();
            break;
        case WND_MESSAGE:
            InitMessage();
            break;
        case WND_BATTLE_MAIN:
            InitWndBattleMain();
            break;
        case WND_BATTLE_AIM:
            InitWndBattleAim();
            break;
        case WND_SHOP:
            InitWndShop();
            break;
        case WND_SHOP_CONFIRM:
            InitWndShopConfirm();
            break;
        case WND_MISSION:
            InitWndMission();
            break;
        case WND_BATTLE_SKILL:
            InitBattleSkill();
            break;
        case WND_BATTLE_ITEM:
            InitBattleItem();
            break;
        case WND_BATTLE_TIP:
            InitBattleTip();
            break;
        case WND_MIXTRUE:
            InitMixtrue();
            break;
        case WND_MIXTRUE_LIST:
            InitMixtrueList();
            break;
        case WND_MIXTRUE_INFO:
            InitMixtrueInfo();
            break;
        case WND_MIXTRUE_MESSAGE:
            InitMixtrueMessage();
            break;
        case WND_ANIMATION:
            InitAnimation();
            break;
        case WND_UI_ANIMATION:
            InitUiAnimation();
            break;

        case WND_MORE_GAME:
            InitLinkConfirm();
            break;
//@#if SMS
//@        case WND_SMS_MENU:
//@            InitWndSMS();
//@            break;
//@        case WND_SMS_MESSAGE:
//@            InitSMSMessage();
//@            break;
//@#if EXIST_EFFORT
//@        case WND_EFFORT:
//@            InitEffort();
//@            break;
//@        case WND_EFFORT_LIST:
//@            InitEffortList();
//@            break;
//@        case WND_EFFORT_DESC:
//@            InitEffortDesc();
//@            break;
//@        case WND_EFFORT_INFO:
//@            InitEffortInfo();
//@            break;
//@        case WND_EFFORT_FINISH:
//@            InitEffortFinish();
//@            break;
//@#endif
//@#if QQ_FORUM
//@        case WND_QQ_FORUM:
//@            InitQQForum();
//@            break;
//@        case WND_QQ_SCORE:
//@            InitQQScore();
//@            break;
//@        case WND_LOOT_SCORE:
//@            InitLootScore();
//@            break;
//@#endif
//@#endif
        case WND_SOFT_KEY:
            SetSkin(CONNER_NONE, HORIZ_NONE, VERTI_NONE, COLOR_NONE,T_FALSE);
            break;
        }
        CGame.s_menuSlot[m_menuSlot] = 0;
    }

    void InitWndShopConfirm()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_2, MODULE_UI_HORIZ_2, MODULE_UI_VERTI_2, COLOR_BEIGE, T_FALSE);
        SetListKey(GKEY_MOVEUP, GKEY_MOVEDOWN, GKEY_OK, false, MENU_SLOT_DEAL_NUM);
    }

    void InitWndMission()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        m_count = AMOUNT_MI_MISSION;
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        SetListKey(GKEY_MOVERIGHT, GKEY_MOVELEFT, 0, false, MENU_SLOT_MISSION);
        CWnd wnd0, wnd1, wnd2;
        wnd0 = CWnd.GetInstance();
        wnd0.Init(WND_LIST);
        wnd0.SetListKey(GKEY_MOVEDOWN, GKEY_MOVEUP, 0, false, MENU_SLOT_MISSION_LIST);
        wnd0.m_showNum = IGM_MISSION_SHOW_NUM;
        CGame.GetEntityIndex(MI_MISSION_MAIN, wnd0, CGame.s_entityList[ENTITY_LIST_MISSION]);
        AppendChild(wnd0);
        wnd1 = CWnd.GetInstance();
        wnd1.Init(WND_IGM_DESC, m_rect[RECT_X] + IGM_MISSION_DESC_X, m_rect[RECT_Y] + IGM_MISSION_DESC_Y, IGM_MISSION_DESC_W, IGM_MISSION_DESC_H, true);
        wnd1.m_data[IGM_DESC_DATA_ID] = wnd0.m_curSelect == null ? -1 : wnd0.m_curSelect.m_id;
        wnd1.m_data[IGM_DESC_DATA_TYPE] = ENTITY_LIST_MISSION;
        wnd1.m_data[IGM_DESC_SHOW_CHAR] = T_TRUE;
        //任务初始滚屏
        if (wnd0.m_curSelect == null)
        {
        }
        else
        {
            wnd1.ResetTextPos(CGame.CalcTextPos(CGame.GetString(STR_SYSTEM_TASKS_DESC_0 + wnd0.m_curSelect.m_id), IGM_MISSION_DESC_W, SCROLL_DIR_UP));
        }
        AppendChild(wnd1);
        AddActiveWnd(wnd0);
        AddActiveWnd(wnd1);
//@#if EXIST_SOFT_KEY
        wnd2 = GetInstance();
        wnd2.Init(WND_SOFT_KEY, WND_RECT_X, WND_RECT_Y, WND_RECT_W, WND_RECT_H, true);
        AppendChild(wnd2);
//@#endif
    }

    void Init(int type, int x, int y, int w, int h, boolean show)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetRect(x, y, w, h);
        m_show = show;
        Init(type);
    }

    void InitMainMenu()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        m_count = AMOUNT_MAINMENU_ITEM;
        m_showNum = MAINMENU_SHOW_ITEM;
        SetListKey(GKEY_MOVERIGHT, GKEY_MOVELEFT, GKEY_OK, true, MENU_SLOT_MAINMENU);
        CWnd wnd0, wnd1, wnd2, wnd3;
        wnd0 = GetInstance();
        wnd0.Init(WND_LOAD, IGM_RMS_X, IGM_RMS_Y, IGM_RMS_W, IGM_RMS_H, false);
        AppendChild(wnd0);
        wnd1 = GetInstance();
        wnd1.Init(WND_ABSTRACT, IGM_X, IGM_Y, IGM_W, IGM_H, false);
        AppendChild(wnd1);
        wnd2 = GetInstance();
        wnd2.Init(WND_EXIT);
        wnd2.m_show = false;
        AppendChild(wnd2);
        wnd3 = GetInstance();
        wnd3.Init(WND_CONFIRM, IGM_CONFIRM_X, IGM_CONFIRM_Y, IGM_CONFIRM_W, IGM_CONFIRM_H, false);
        AppendChild(wnd3);

        CWnd wndSMS = GetInstance();
        wndSMS.Init(WND_SMS_MESSAGE, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, false);
        AppendChild(wndSMS);

        CWnd wndForum = GetInstance();
        wndForum.Init(WND_QQ_FORUM);
        wndForum.m_show = false;
        AppendChild(wndForum);


        CWnd wndLink = GetInstance();
        wndLink.Init(WND_MORE_GAME, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, false);
        AppendChild(wndLink);

    }
    void InitConfirm()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        m_count = AMOUNT_MI_CONFIRM;
        m_textPosMax = FONT_HEIGHT;
        SetSkin(MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1, COLOR_LIGHT_BLUE, T_FALSE);
        SetListKey(GKEY_MOVERIGHT, GKEY_MOVELEFT, GKEY_OK, false, MENU_SLOT_CONFIRM);
    }

    void InitMessage()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1, COLOR_LIGHT_BLUE, T_FALSE);
        m_textPosMax = FONT_HEIGHT;
    }

    void InitLootItem()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        SetRect(IGM_LOOT_ITEM_X, IGM_LOOT_ITEM_Y, IGM_LOOT_ITEM_W, IGM_LOOT_ITEM_H);
        m_showNum = 4;
        InitData(WND_LOOT_ITEM_DATA_SIZE);
        CGame.CalcLootItem(this);
        m_count = (m_dataCount - 2) / WND_LOOT_ITEM_DATA_PER;
    }

    void InitLootExp()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        SetRect(IGM_LOOT_EXP_X, IGM_LOOT_EXP_Y, IGM_LOOT_EXP_W, IGM_LOOT_EXP_H);
        InitData(WND_LOOT_EXP_DATA_SIZE);
        CGame.CalcLootExp(this);
        m_count = m_dataCount / WND_LOOT_EXP_DATA_PER;
        CWnd wnd = CWnd.GetInstance();
        wnd.Init(WND_LOOT_NEWSKILL, IGM_LOOT_SKILL_X, IGM_LOOT_SKILL_Y, IGM_LOOT_SKILL_W, IGM_LOOT_SKILL_H, false);
        AppendChild(wnd);
    }

    void InitLootNewSkill()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        InitData(WND_LOOT_LEARN_DATA_SIZE);
    }

    void InitLootEscape()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_2, MODULE_UI_HORIZ_2, MODULE_UI_VERTI_2, COLOR_LIGHT_BLUE, T_FALSE);
        SetRect(IGM_LOOT_FAIL_X, IGM_LOOT_FAIL_Y, IGM_LOOT_FAIL_W, IGM_LOOT_FAIL_H);
    }

    void InitExit()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
//        CGame.s_imgMoreGame = Image.createImage("/");
    }

    void InitIGM()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        m_count = AMOUNT_MI_IGM;
        //左键弹出菜单边框
        SetSkin(WND_IGM_CONNER, WND_IGM_HORIZ, WND_IGM_VERTI, WND_IGM_COLOR, T_FALSE);
        SetListKey(GKEY_MOVERIGHT, GKEY_MOVELEFT, GKEY_OK, true, MENU_SLOT_IGM);
        CWnd wnd = CWnd.GetInstance();
        wnd.Init(WND_ABSTRACT);
        AppendChild(wnd);
        CGame.RefreshTeam();
//@#if AMIN_IGM_CURSOR
//@        CGame.s_actorUI.SetPos(m_rect[RECT_X] + IGM_ICON_X, IGM_ICON_Y);
//@#endif
//@#if EXIST_SOFT_KEY
//@#if PHONE_TOUCH
//@        CWnd wnd2 = GetInstance();
//@        wnd2.Init(WND_SOFT_KEY,WND_RECT_X, WND_RECT_Y, WND_RECT_W, WND_RECT_H, true);
//@        AppendChild(wnd2);
//@#endif
//@#endif
    }

    void InitRMS()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        SetListKey(GKEY_MOVEDOWN, GKEY_MOVEUP, GKEY_OK, true, MENU_SLOT_RMS);
        m_count = AMOUNT_MI_IGM_RMS; // 存档数量
        for (int i = 0; i < m_count; i++)
        {
            CWnd child = GetInstance();
            child.Init(WND_RMS_SLOT, IGM_RMSSLOT_X, IGM_RMSSLOT_Y + IGM_RMS_SUB_OFFSET * i, IGM_RMSSLOT_W, IGM_RMSSLOT_H, true);
            AppendChild(child);
        }
        CWnd wnd0, wnd1,wnd2;
        wnd0 = GetInstance();
        wnd0.Init(WND_CONFIRM, IGM_CONFIRM_X, IGM_CONFIRM_Y, IGM_CONFIRM_W, IGM_CONFIRM_H, false);
        AppendChild(wnd0);
        wnd1 = GetInstance();
        wnd1.Init(WND_MESSAGE, IGM_MESSAGE_X, IGM_MESSAGE_Y, IGM_MESSAGE_W, IGM_MESSAGE_H, false);
        AppendChild(wnd1);
//@#if EXIST_SOFT_KEY
        wnd2 = GetInstance();
        wnd2.Init(WND_SOFT_KEY, WND_RECT_X, WND_RECT_Y, WND_RECT_W, WND_RECT_H, true);
        AppendChild(wnd2);
//@#endif
    }

    void InitOption()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        SetListKey(GKEY_MOVEDOWN, GKEY_MOVEUP, GKEY_OK, true, MENU_SLOT_OPTION);
        m_count = AMOUNT_MI_SYSTEM_OPTION;

//@#if EXIST_SOFT_KEY
        CWnd wnd0;
        wnd0 = GetInstance();
        wnd0.Init(WND_SOFT_KEY, WND_RECT_X, WND_RECT_Y, WND_RECT_W, WND_RECT_H, true);
        AppendChild(wnd0);
//@#endif

    }

    void InitHelp()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        m_title = STR_SYSTEM_GS_HELP_TITLE;
        SetListKey(GKEY_MOVEDOWN, GKEY_MOVEUP, GKEY_OK, false, MENU_SLOT_HELP);
//@#if EXIST_SOFT_KEY
        CWnd wnd0;
        wnd0 = GetInstance();
        wnd0.Init(WND_SOFT_KEY, WND_RECT_X, WND_RECT_Y, WND_RECT_W, WND_RECT_H, true);
        AppendChild(wnd0);
//@#endif

    }

    void InitAbout()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        m_title = STR_SYSTEM_GS_ABOUT_TITLE;
        SetListKey(GKEY_MOVEDOWN, GKEY_MOVEUP, GKEY_OK, false, MENU_SLOT_ABOUT);
//@#if EXIST_SOFT_KEY
        CWnd wnd0;
        wnd0 = GetInstance();
        wnd0.Init(WND_SOFT_KEY, WND_RECT_X, WND_RECT_Y, WND_RECT_W, WND_RECT_H, true);
        AppendChild(wnd0);
//@#endif

    }

    void InitWndShop()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        SetListKey(GKEY_MOVERIGHT, GKEY_MOVELEFT, GKEY_OK, true, MENU_SLOT_BUY_OR_SELL);
        m_count = AMOUNT_MI_IGM_SHOP;
        CWnd wnd0, wnd1, wnd2, wnd3, wnd4;
        wnd0 = GetInstance();
        wnd0.Init(WND_2D_LIST, IGM_SHOP_ICON_X, IGM_SHOP_ICON_Y, IGM_SHOP_ICON_OFFX, IGM_SHOP_ICON_OFFY, true);
        wnd0.m_showNum = 4;
        wnd0.m_column = 6;
        wnd0.m_curSelect = CGame.s_entityList[ENTITY_LIST_BUY].m_next;
        wnd0.m_showFirst = wnd0.m_curSelect;
        wnd0.m_count = wnd0.m_curSelect == null ? 0 : wnd0.m_curSelect.GetCount();
        AppendChild(wnd0);
        wnd1 = GetInstance();
        wnd1.Init(WND_IGM_DESC, IGM_SHOP_DESC_X, IGM_SHOP_DESC_Y, IGM_SHOP_DESC_W, IGM_SHOP_DESC_H, true);
        wnd1.m_data[IGM_DESC_SHOW_CHAR] = T_FALSE;
        AppendChild(wnd1);
        wnd2 = GetInstance();
        wnd2.Init(WND_SHOP_CONFIRM, IGM_SHOP_CONFIRM_X, IGM_SHOP_CONFIRM_Y, IGM_SHOP_CONFIRM_W, IGM_SHOP_CONFIRM_H, false);
        AppendChild(wnd2);
        wnd3 = GetInstance();
        wnd3.Init(WND_MESSAGE, IGM_MESSAGE_X, IGM_MESSAGE_Y, IGM_MESSAGE_W, IGM_MESSAGE_H, false);
        AppendChild(wnd3);
        wnd0.AddActiveWnd(wnd1);
//@#if EXIST_SOFT_KEY
        wnd4 = GetInstance();
        wnd4.Init(WND_SOFT_KEY, WND_RECT_X, WND_RECT_Y, WND_RECT_W, WND_RECT_H, true);
        AppendChild(wnd4);
//@#endif
    }

    void InitWndSkillRole()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(CONNER_NONE, HORIZ_NONE, VERTI_NONE, COLOR_NONE, T_FALSE);
        SetListKey(GKEY_MOVERIGHT, GKEY_MOVELEFT, GKEY_OK, false, MENU_SLOT_SKILLWND);
        m_count = CGame.s_teamMateCount;
        CGame.s_menuSlot[m_menuSlot] = 0;
    }

    void InitWndSkill()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        SetListKey(GKEY_MOVEDOWN, GKEY_MOVEUP, GKEY_OK, false, MENU_SLOT_SKILL);
        m_showNum = SKILL_SHOENUM_IN_IGM;
        CWnd wnd0, wnd1, wnd2, wnd3, wnd4;
        m_curSelect = CGame.s_actorCommon[CGame.s_teamMateActorID[0]].m_entitySkill.m_next;
        m_showFirst = m_curSelect;
        m_count = m_curSelect == null ? 0 : m_curSelect.GetCount();
        wnd0 = GetInstance();
        wnd0.Init(WND_SKILL_ROLE, IGM_SKILL_ROLE_X, IGM_SKILL_ROLE_Y, 0, 0, true);
        AppendChild(wnd0);
        wnd1 = GetInstance();
        wnd1.Init(WND_SKILL_ADVANCE, IGM_X, IGM_Y, IGM_W, IGM_H, false);
        wnd1.ResetTextPos(CGame.CalcTextPos(CGame.GetString(STR_SYSTEM_SKILLS_DESC_0 + m_curSelect.m_id), wnd1.m_rect[RECT_W] - (IGM_DESC_TEXT_X << 1), SCROLL_DIR_UP));
        AppendChild(wnd1);
        wnd2 = GetInstance();
        wnd2.Init(WND_IGM_DESC, IGM_SKILL_DESC_X, IGM_SKILL_DESC_Y, IGM_SKILL_DESC_W, IGM_SKILL_DESC_H, false);
        wnd2.m_show = true;
        wnd2.m_data[IGM_DESC_DATA_TYPE] = ENTITY_LIST_SKILL;
        wnd2.m_data[IGM_DESC_SHOW_CHAR] = T_TRUE;
        if (m_curSelect != null)
        {
            wnd2.m_data[IGM_DESC_DATA_ID] = m_curSelect.m_id;
        }
        else
        {
            wnd2.m_data[IGM_DESC_DATA_ID] = -1;
        }
        AppendChild(wnd2);
        AddActiveWnd(wnd0);
        AddActiveWnd(wnd2);
        wnd2.ResetTextPos(CGame.CalcTextPos(CGame.GetString(STR_SYSTEM_SKILLS_DESC_0 + m_curSelect.m_id), wnd2.m_rect[RECT_W] - (IGM_DESC_TEXT_X << 1),
                                                 SCROLL_DIR_UP));

//@#if EXIST_SOFT_KEY
        wnd3 = GetInstance();
        wnd3.Init(WND_SOFT_KEY, WND_RECT_X, WND_RECT_Y, WND_RECT_W, WND_RECT_H, true);
        AppendChild(wnd3);
//@#endif
        wnd4 = GetInstance();
        wnd4.Init(WND_MESSAGE, IGM_MESSAGE_X, IGM_MESSAGE_Y, IGM_MESSAGE_W, IGM_MESSAGE_H, false);
        AppendChild(wnd4);
    }

    void InitWndSkillAdvance()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        SetListKey(0, 0, GKEY_OK, false, MENU_SLOT_OTHER);
        InitData(WND_SKILL_ADVANCE_SIZE);
        CWnd wnd0, wnd1;
        wnd0 = GetInstance();
        wnd0.Init(WND_MESSAGE, IGM_MESSAGE_X, IGM_MESSAGE_Y, IGM_MESSAGE_W, IGM_MESSAGE_H, false);
        AppendChild(wnd0);
//@#if EXIST_SOFT_KEY
        wnd1 = GetInstance();
        wnd1.Init(WND_SOFT_KEY, WND_RECT_X, WND_RECT_Y, WND_RECT_W, WND_RECT_H, true);
        AppendChild(wnd1);
//@#endif
    }

    void InitIGMDesc()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        //装备显示信息框
        SetSkin(MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1, COLOR_LIGHT_GREEN, T_FALSE);
        InitData(WND_DATA_SIZE);
    }

    void InitBattleDesc()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1, COLOR_LIGHT_GREEN, T_FALSE);
        InitData(WND_DATA_SIZE);
    }

    void InitStatus()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        m_count = CGame.s_teamMateCount;
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
//@#if JAR_LIMIT_300K
//@        SetListKey(GKEY_MOVEDOWN, GKEY_MOVEUP, GKEY_OK, true, MENU_SLOT_STATUS);
//@#else
        SetListKey(GKEY_MOVERIGHT, GKEY_MOVELEFT, GKEY_OK, true, MENU_SLOT_STATUS);
//@#endif
        InitData(WND_DATA_SIZE);
        for (int i = 0; i < CGame.s_teamMateCount; i++)
        {
            CWnd wnd = GetInstance();
//@#if JAR_LIMIT_300K
//@            wnd.Init(WND_STATUS_ROLE, IGM_STATE_ROLE_X, IGM_STATE_ROLE_Y + IGM_STATE_ROLE_OFFY * i, IGM_STATE_ROLE_W, IGM_STATE_ROLE_H, true);
//@#else
             wnd.Init(WND_STATUS_ROLE, IGM_STATE_ROLE_X+ IGM_STATE_ROLE_OFFX * i, IGM_STATE_ROLE_Y , IGM_STATE_ROLE_W, IGM_STATE_ROLE_H, true);
//@#endif

            wnd.AddData(CGame.s_teamMateActorID[i]);
            AppendChild(wnd);
        }
        CWnd wnd0 = GetInstance();
        wnd0.Init(WND_STATUS_DESC, WND_RECT_X, IGM_STATE_DESC_Y, IGM_STATE_DESC_W, IGM_STATE_DESC_H, false);
        wnd0. SetListKey(-1, -1, GKEY_OK, false, MENU_SLOT_STATUS);
        AppendChild(wnd0);
        CWnd wnd1 = GetInstance();
        wnd1.Init(WND_MESSAGE, IGM_MESSAGE_X, IGM_MESSAGE_Y, IGM_MESSAGE_W, IGM_MESSAGE_H, false);
        AppendChild(wnd1);
//@#if EXIST_SOFT_KEY
        CWnd wnd2 = GetInstance();
        wnd2.Init(WND_SOFT_KEY, WND_RECT_X, WND_RECT_Y, WND_RECT_W, WND_RECT_H, true);
        AppendChild(wnd2);
//@#endif

    }

    void InitStatusRole()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1, COLOR_LIGHT_GREEN, T_FALSE);
        InitData(WND_DATA_SIZE);
    }

    void InitStatusDesc()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1, COLOR_LIGHT_GREEN, T_FALSE);
        InitData(WND_DATA_SIZE);
    }

    void InitRoleValue()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_2, MODULE_UI_HORIZ_2, MODULE_UI_VERTI_2, COLOR_WHITE, T_FALSE);
        InitData(WND_ROLE_VALUE_DATA_SIZE);
        m_count = WND_ROLE_VALUE_DATA_NUM;
        m_showNum = IGM_ROLE_VALUE_SHOW_NUM;
    }

    void InitWndEquip()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        CWnd wnd0, wnd1, wnd2, wnd3, wnd4, wnd5,wnd6;

        m_count = EQUIP_TYPE.length;

        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        SetListKey(GKEY_MOVEDOWN, GKEY_MOVEUP, GKEY_OK, true, MENU_SLOT_EQUIP_TYPE);
        wnd0 = GetInstance();
        wnd0.Init(WND_EQUIP_ROLE, IGM_ROLEEQUIP_ROLE_X, IGM_ROLEEQUIP_ROLE_Y, IGM_ROLEEQUIP_ROLE_W, IGM_ROLEEQUIP_ROLE_H, true);
        AppendChild(wnd0);
        wnd1 = GetInstance();
        wnd1.Init(WND_EQUIP_PROPOSE, m_rect[RECT_X] + IGM_EQUIPPURPOSE_X, m_rect[RECT_Y] + IGM_EQUIPPURPOSE_Y, IGM_EQUIPPURPOSE_W, IGM_EQUIPPURPOSE_H, false);
        AppendChild(wnd1);
        wnd2 = GetInstance();
        wnd2.Init(WND_EQUIP_STANDBY, IGM_X, IGM_Y, IGM_W, IGM_H, false);
        AppendChild(wnd2);
        wnd3 = GetInstance();
        wnd3.Init(WND_EQUIP_ADVANCE, IGM_X, IGM_Y, IGM_W, IGM_H, false);
        AppendChild(wnd3);
        wnd4 = GetInstance();
        wnd4.Init(WND_IGM_DESC, m_rect[RECT_X] + IGM_ROLEEQUIP_DESC_X, m_rect[RECT_Y] + IGM_ROLEEQUIP_DESC_Y, IGM_ROLEEQUIP_DESC_W, IGM_ROLEEQUIP_DESC_H, true);
        wnd4.m_data[IGM_DESC_SHOW_CHAR] =
            CGame.s_actorCommon[CGame.s_teamMateActorID[CGame.s_menuSlot[wnd0.m_menuSlot]]].m_equips[CGame.s_menuSlot[m_menuSlot]] >= 0 ? T_TRUE : T_FALSE;
        wnd4.m_data[IGM_DESC_DATA_TYPE] = ENTITY_LIST_EQUIP;
        wnd4.m_data[IGM_DESC_DATA_ID] = CGame.s_actorCommon[CGame.s_teamMateActorID[0]].m_equips[ENUM_EQUIPS_TYPE_WEAPON];
        AppendChild(wnd4);
        int roleID =CGame. s_menuSlot[wnd0.m_menuSlot];
        int typeID = CGame.s_menuSlot[wnd4.m_menuSlot];
        int equipID;
        CActor actor = CGame.s_actorCommon[CGame.s_teamMateActorID[roleID]];
        equipID = actor.m_equips[typeID];

        wnd4.ResetTextPos(CGame.CalcTextPos(CGame.GetString(STR_SYSTEM_EQUIPS_DESC_0 + equipID), IGM_ROLEEQUIP_DESC_W, SCROLL_DIR_UP));
        wnd5 = GetInstance();
        wnd5.Init(WND_MESSAGE, IGM_MESSAGE_X, IGM_MESSAGE_Y, IGM_MESSAGE_W, IGM_MESSAGE_H, false);
        AppendChild(wnd5);
        AddActiveWnd(wnd0);
        AddActiveWnd(wnd4);
//@#if EXIST_SOFT_KEY
        wnd6 = GetInstance();
        wnd6.Init(WND_SOFT_KEY, WND_RECT_X, WND_RECT_Y, WND_RECT_W, WND_RECT_H, true);
        AppendChild(wnd6);
//@#endif

    }

    void InitWndEquipRole()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(CONNER_NONE, HORIZ_NONE, VERTI_NONE, COLOR_NONE, T_FALSE);
        SetListKey(GKEY_MOVERIGHT, GKEY_MOVELEFT, GKEY_OK, true, MENU_SLOT_EQUIP_ROLE);
        m_count = CGame.s_teamMateCount;
        InitData(WND_DATA_SIZE);
        AddData(-1);
    }

    void InitWndEquipStandby()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        SetListKey(0, 0, GKEY_OK, false, MENU_SLOT_EQUIP_STANDBY);
        m_count = AMOUNT_ENUM_EQUIPS_TYPE;
        InitData(WND_DATA_SIZE);
        CWnd wnd0, wnd1;
        wnd0 = CWnd.GetInstance();
        wnd0.Init(WND_IGM_DESC, IGM_EQUIPSTANDBY_DESC_X, IGM_Y + IGM_EQUIPSTANDBY_DESC_Y, IGM_EQUIPSTANDBY_DESC_W, IGM_EQUIPSTANDBY_DESC_H, true);
        wnd0.m_data[IGM_DESC_SHOW_CHAR] = T_TRUE;
        wnd0.m_data[IGM_DESC_DATA_TYPE] = ENTITY_LIST_EQUIP;
        AppendChild(wnd0);
        wnd1 = CWnd.GetInstance();
        wnd1.Init(WND_2D_LIST, IGM_X + IGM_EQUIPSTANDBY_ICON_X, IGM_Y + IGM_EQUIPSTANDBY_ICON_Y, IGM_EQUIPSTANDBY_ICON_OFFX, IGM_EQUIPSTANDBY_ICON_OFFY, true);
        wnd1.m_column = DISPLAY_EQUIP_COL;
        wnd1.m_showNum = DISPLAY_EQUIP_ROW;
        wnd1.m_title = T_TRUE;
        wnd1.m_suspen = CWnd.GetInstance();
        wnd1.m_suspen.Init(WND_EQUIP_COMPARE, 0, 0, IGM_EQUIPSTANDBY_SUSPEN_W, IGM_EQUIPSTANDBY_SUSPEN_H, false);
        AppendChild(wnd1);
        AddActiveWnd(wnd0);
        AddActiveWnd(wnd1);
    }

    void InitWndPackageEquip()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        SetListKey(GKEY_MOVEDOWN, GKEY_MOVEUP, GKEY_OK, true, MENU_SLOT_EQUIP_TYPE);
        m_count = AMOUNT_ENUM_EQUIPS_TYPE;
        CWnd wnd0, wnd1, wnd2, wnd3,wnd4;
        wnd0 = CWnd.GetInstance();
        wnd0.Init(WND_IGM_DESC, IGM_EQUIPSTANDBY_DESC_X, IGM_Y + IGM_EQUIPSTANDBY_DESC_Y, IGM_EQUIPSTANDBY_DESC_W, IGM_EQUIPSTANDBY_DESC_H, true);
        wnd0.m_data[IGM_DESC_DATA_TYPE] = ENTITY_LIST_EQUIP;
        AppendChild(wnd0);
        wnd1 = CWnd.GetInstance();
        wnd1.Init(WND_2D_LIST, IGM_X + IGM_EQUIPSTANDBY_ICON_X, IGM_Y + IGM_EQUIPSTANDBY_ICON_Y, IGM_EQUIPSTANDBY_ICON_OFFX, IGM_EQUIPSTANDBY_ICON_OFFY, true);
        wnd1.m_column = DISPLAY_EQUIP_COL;
        wnd1.m_showNum = DISPLAY_EQUIP_ROW;
        CGame.GetEquipList(EQUIP_TYPE[0], wnd1, -1);
        AppendChild(wnd1);
        wnd2 = CWnd.GetInstance();
        wnd2.Init(WND_EQUIP_ADVANCE, IGM_X, IGM_Y, IGM_W, IGM_H, false);
        AppendChild(wnd2);
        wnd3 = CWnd.GetInstance();
        wnd3.Init(WND_MESSAGE, IGM_MESSAGE_X, IGM_MESSAGE_Y, IGM_MESSAGE_W, IGM_MESSAGE_H, false);
        AppendChild(wnd3);
        wnd1.AddActiveWnd(wnd0);
        wnd1.m_suspen = CWnd.GetInstance();
        wnd1.m_suspen.Init(WND_EQUIP_COMPARE, 0, 0, IGM_EQUIPSTANDBY_SUSPEN_W, IGM_EQUIPSTANDBY_SUSPEN_H, false);
//@#if EXIST_SOFT_KEY
        wnd4 = GetInstance();
        wnd4.Init(WND_SOFT_KEY, WND_RECT_X, WND_RECT_Y, WND_RECT_W, WND_RECT_H, true);
        AppendChild(wnd4);
//@#endif
    }

    void InitWndEquipCompare()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_2, MODULE_UI_HORIZ_2, MODULE_UI_VERTI_2, COLOR_LIGHT_BLUE, T_TRUE);
        InitData(WND_COMPARE_DATA_SIZE);
    }

    void InitEquipPropose()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(CONNER_NONE, HORIZ_NONE, VERTI_NONE, COLOR_NONE, T_FALSE);
        SetListKey(GKEY_MOVEDOWN, GKEY_MOVEUP, GKEY_OK, true, MENU_SLOT_EQUIP_PROPOSE);
        m_count = AMOUNT_MI_IGM_EQUIP_PROPOSE;
    }

    void InitWndEquipAdvance()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        SetListKey(0, 0, GKEY_OK, false, MENU_SLOT_NONE);
        InitData(WND_EQUIP_ADVANCE_SIZE);
        CWnd wnd = CWnd.GetInstance();
        wnd.Init(WND_MESSAGE, IGM_MESSAGE_X, IGM_MESSAGE_Y, IGM_MESSAGE_W, IGM_MESSAGE_H, false);
        AppendChild(wnd);
//@#if EXIST_SOFT_KEY
        CWnd wnd0 = GetInstance();
        wnd0.Init(WND_SOFT_KEY, WND_RECT_X, WND_RECT_Y, WND_RECT_W, WND_RECT_H, true);
        AppendChild(wnd0);
//@#endif

    }
    /**
     * 初始化物品窗口
     */
    void InitWndPackageItem()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        m_count = AMOUNT_ENUM_ITEMS_TYPE;
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        SetListKey(GKEY_MOVEDOWN, GKEY_MOVEUP, GKEY_OK, true, MENU_SLOT_ARTICAL);
        InitData(WND_DATA_SIZE);
        CWnd wnd0, wnd1, wnd2, wnd3, wnd4, wnd5;
        wnd0 = GetInstance();
        wnd0.Init(WND_IGM_DESC, IGM_ARTICAL_DESC_X, IGM_ARTICAL_DESC_Y, IGM_ARTICAL_DESC_W, IGM_ARTICAL_DESC_H, true);
        wnd0.m_data[IGM_DESC_SHOW_CHAR] = T_FALSE;
        wnd0.m_data[IGM_DESC_DATA_TYPE] = CGame.ENTITY_LIST_ITEM;
        AppendChild(wnd0);
        wnd1 = GetInstance();
        wnd1.Init(WND_2D_LIST, IGM_ARTICAL_LIST_ICON_X, IGM_ARTICAL_LIST_ICON_Y, IGM_ARTICAL_LIST_ICON_OFFX, IGM_ARTICAL_LIST_ICON_OFFY, true);
        wnd1.m_showNum = 4;
        wnd1.m_column = 4;
        CGame.GetEntityIndex(ENUM_ITEMS_TYPE_ITEM, wnd1, CGame.s_entityList[ENTITY_LIST_ITEM]);
        wnd1.AddActiveWnd(wnd0);
        AppendChild(wnd1);
        wnd2 = GetInstance();
        wnd2.Init(WND_STATUS, IGM_X, IGM_Y, IGM_W, IGM_H, false);
        AppendChild(wnd2);
        wnd3 = GetInstance();
        wnd3.Init(WND_MESSAGE, IGM_MESSAGE_X, IGM_MESSAGE_Y, IGM_MESSAGE_W, IGM_MESSAGE_H, false);
        AppendChild(wnd3);
        wnd4 = GetInstance();
        wnd4.Init(WND_ROLE_VALUE, IGM_ROLE_VALUE_X, IGM_ROLE_VALUE_Y, IGM_ROLE_VALUE_W, IGM_ROLE_VALUE_H, false);
        AppendChild(wnd4);
//@#if EXIST_SOFT_KEY
        wnd5 = GetInstance();
        wnd5.Init(WND_SOFT_KEY, WND_RECT_X, WND_RECT_Y, WND_RECT_W, WND_RECT_H, true);
        AppendChild(wnd5);
//@#endif
    }

    void InitWndSystem()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        SetListKey(GKEY_MOVEDOWN, GKEY_MOVEUP, GKEY_OK, true, MENU_SLOT_SYSTEM);
        m_count = AMOUNT_MI_IGM_SYSTEM;
        CWnd wnd0, wnd1, wnd2 , wnd3;
        wnd0 = GetInstance();
        wnd0.Init(WND_ABSTRACT);
        AppendChild(wnd0);
        wnd1 = GetInstance();
        wnd1.Init(WND_CONFIRM, IGM_CONFIRM_X, IGM_CONFIRM_Y, IGM_CONFIRM_W, IGM_CONFIRM_H, false);
        AppendChild(wnd1);
        wnd2 = GetInstance();
        wnd2.Init(WND_MESSAGE, IGM_MESSAGE_X, IGM_MESSAGE_Y, IGM_MESSAGE_W, IGM_MESSAGE_H, false);
        AppendChild(wnd2);
//@#if EXIST_SOFT_KEY
        wnd3 = GetInstance();
        wnd3.Init(WND_SOFT_KEY, WND_RECT_X, WND_RECT_Y, WND_RECT_W, WND_RECT_H, false);
        AppendChild(wnd3);
//@#endif
    }

    void InitWndPortableShop()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        SetListKey(GKEY_MOVEDOWN, GKEY_MOVEUP, GKEY_OK, false, MENU_SLOT_PORTABLE_SHOP);
        m_count = CGame.GetMenuItem(ENTITY_LIST_PORTABLE_SHOP, -1);
        m_showNum = m_count;
        m_curSelect = CGame.s_tempList.m_next;
        m_showFirst = m_curSelect;
        CWnd wnd = GetInstance();
        wnd.Init(WND_SHOP, IGM_X, IGM_Y, IGM_W, IGM_H, false);
        wnd.InitData(WND_DATA_SIZE);
        AppendChild(wnd);
    }

    void InitWndList()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(CONNER_NONE, HORIZ_NONE, VERTI_NONE, COLOR_NONE, T_FALSE);
    }

    void Init2DList()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(CONNER_NONE, HORIZ_NONE, VERTI_NONE, COLOR_NONE, T_FALSE);
        SetListKey(GKEY_MOVERIGHT, GKEY_MOVELEFT, GKEY_OK, false, MENU_SLOT_2D_LIST);
    }

    void InitWndBattleMain()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        m_count = AMOUNT_BATTLE_MI_NO_SP;
        m_showNum = AMOUNT_BATTLE_MI_NO_SP;
        //只用GKEY_OK键
        SetListKey(0, 0, GKEY_OK, true, MENU_SLOT_BATTLE_MAIN);
    }

    void InitWndBattleAim()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        InitData(WND_DATA_SIZE);
    }

    void InitBattleSkill()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        CWnd wnd0, wnd1, wnd2, wnd3;
        wnd0 = GetInstance();
        wnd0.m_menuSlot = m_menuSlot;
        wnd0.Init(WND_LIST);
        wnd0.SetListKey(GKEY_MOVEDOWN, GKEY_MOVEUP, GKEY_OK, false, MENU_SLOT_SKILL);
        wnd0.m_curSelect = CGame.GetBattleSkill();
        wnd0.m_showFirst = wnd0.m_curSelect;
        wnd0.m_count = wnd0.m_curSelect.GetCount();
        wnd0.m_showNum = DISPLAY_BATTLESKILL_SIZE;
        AppendChild(wnd0);
        wnd1 = GetInstance();
        wnd1.Init(WND_MESSAGE, IGM_MESSAGE_X, IGM_MESSAGE_Y, IGM_MESSAGE_W, IGM_MESSAGE_H, false);
        AppendChild(wnd1);
        wnd2 = GetInstance();
        wnd2.Init(WND_BATTLE_DESC, m_rect[RECT_X] + BATTLE_DESC_X, m_rect[RECT_Y] + BATTLE_DESC_Y, BATTLE_DESC_W, BATTLE_DESC_H, true);
        wnd2.m_data[BATTLE_DESC_DATA_TYPE] = ENTITY_LIST_SKILL;
        wnd2.m_data[BATTLE_DESC_DATA_ID] = wnd0.m_curSelect.m_id;
        AppendChild(wnd2);
        AddActiveWnd(wnd0);
        AddActiveWnd(wnd2);
        wnd0.ResetTextPos(CGame.CalcTextPos(CGame.GetString(STR_SYSTEM_SKILL_NAME_DESC_0 + wnd0.m_curSelect.m_id), BATTLE_SKILL_NAME_W, SCROLL_DIR_UP));
        wnd2.ResetTextPos(CGame.CalcTextPos(CGame.GetString(STR_SYSTEM_SKILLS_DESC_0 + wnd0.m_curSelect.m_id), wnd2.m_rect[RECT_W]-10, SCROLL_DIR_UP));
//@#if EXIST_SOFT_KEY
        wnd3 = GetInstance();
        wnd3.Init(WND_SOFT_KEY, WND_RECT_X, WND_RECT_Y, WND_RECT_W, WND_RECT_H, true);
        AppendChild(wnd3);
//@#endif

    }

    void InitBattleItem()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        m_menuSlot = MENU_SLOT_PACKAGE;
        CWnd wnd0, wnd1, wnd2, wnd3;
        wnd0 = GetInstance();
        wnd0.Init(WND_2D_LIST, m_rect[RECT_X] + BATTLE_ITEM_ICON_X, m_rect[RECT_Y] + BATTLE_ITEM_ICON_Y, BATTLE_ITEM_ICON_OFFX, BATTLE_ITEM_ICON_OFFY, true);
        wnd0.m_column = DISPLAY_BATTLE_ITEM_COL;
        wnd0.m_showNum = DISPLAY_BATTLE_ITEM_ROW;
        wnd0.m_curSelect = CGame.s_entityList[ENTITY_LIST_BATTLE].m_next;
        wnd0.m_showFirst = wnd0.m_curSelect;
        wnd0.m_count = wnd0.m_curSelect.GetCount();
        wnd0.m_title = T_TRUE;
        AppendChild(wnd0);
        wnd1 = GetInstance();
        wnd1.Init(WND_BATTLE_DESC, m_rect[RECT_X] + BATTLE_DESC_X, m_rect[RECT_Y] + BATTLE_DESC_Y, BATTLE_DESC_W, BATTLE_DESC_H, true);
        wnd1.m_data[0] = ENTITY_LIST_BATTLE;
        wnd1.m_data[1] = wnd0.m_curSelect.m_id;
        AppendChild(wnd1);
        AddActiveWnd(wnd1);
        wnd1.ResetTextPos(CGame.CalcTextPos(CGame.GetString(STR_SYSTEM_ITEMS_DESC_0 + wnd0.m_curSelect.m_id), wnd1.m_rect[RECT_W], SCROLL_DIR_UP));
        wnd2 = GetInstance();
        wnd2.Init(WND_MESSAGE, IGM_MESSAGE_X, IGM_MESSAGE_Y, IGM_MESSAGE_W, IGM_MESSAGE_H, false);
        AppendChild(wnd2);
        AddActiveWnd(wnd0);
//        AddActiveWnd(wnd2);
//        wnd2.ResetTextPos(CGame.CalcTextPos(CGame.GetString(STR_SYSTEM_SKILLS_DESC_0 + wnd0.m_curSelect.m_id), wnd2.m_rect[RECT_W] - 10, SCROLL_DIR_LEFT));
//@#if EXIST_SOFT_KEY
        wnd3 = GetInstance();
        wnd3.Init(WND_SOFT_KEY, WND_RECT_X, WND_RECT_Y, WND_RECT_W, WND_RECT_H, true);
        AppendChild(wnd3);
//@#endif

    }

    void InitBattleTip()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        //战斗选择敌兵是显示信息框
//@#if JAR_LIMIT_300K
//@        SetSkin(MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1, COLOR_BEIGE, T_FALSE);
//@#else
        SetSkin(MODULE_UI_CONNER_6, MODULE_UI_HORIZ_6, MODULE_UI_VERTI_6, COLOR_BEIGE, T_FALSE);
//@#endif
        InitData(WND_DATA_SIZE);
    }

    void InitMixtrue()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        SetListKey(GKEY_MOVERIGHT, GKEY_MOVELEFT, GKEY_OK, true, MENU_SLOT_MIXTRUE);
        m_count = AMOUNT_ENUM_MIXTRUE_TYPE;
        CWnd wnd0, wnd1, wnd2, wnd3 , wnd4;
        wnd0 = GetInstance();
        wnd0.Init(WND_MIXTRUE_LIST, MIXTRUE_LIST_X, MIXTRUE_LIST_Y, MIXTRUE_LIST_W, MIXTRUE_LIST_H, true);
        AppendChild(wnd0);
        wnd1 = GetInstance();
        wnd1.Init(WND_IGM_DESC, MIXTRUE_DESC_X, MIXTRUE_DESC_Y, MIXTRUE_DESC_W, MIXTRUE_DESC_H, true);
        wnd1.m_data[IGM_DESC_DATA_TYPE] = ENTITY_LIST_MIXTRUE;
        wnd1.m_data[IGM_DESC_DATA_ID] = wnd0.m_curSelect == null ? -1 : wnd0.m_curSelect.m_id;
        wnd1.m_data[IGM_DESC_SHOW_CHAR] = T_TRUE;
        AppendChild(wnd1);
        wnd2 = GetInstance();
        wnd2.Init(WND_MIXTRUE_INFO, MIXTRUE_INFO_X, MIXTRUE_INFO_Y, MIXTRUE_INFO_W, MIXTRUE_INFO_H, false);
        AppendChild(wnd2);
        wnd3 = GetInstance();
        wnd3.Init(WND_MESSAGE, IGM_MESSAGE_X, IGM_MESSAGE_Y, IGM_MESSAGE_W, IGM_MESSAGE_H, false);
        AppendChild(wnd3);
        AddActiveWnd(wnd0);
        AddActiveWnd(wnd1);
//@#if EXIST_SOFT_KEY
        wnd4 = GetInstance();
        wnd4.Init(WND_SOFT_KEY, WND_RECT_X, WND_RECT_Y, WND_RECT_W, WND_RECT_H, true);
        AppendChild(wnd4);
//@#endif

    }

    void InitMixtrueList()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(CONNER_NONE, HORIZ_NONE, VERTI_NONE, COLOR_NONE, T_FALSE);
        m_showNum = MIXTRUE_LIST_RECT_H / FONT_HEIGHT;
        SetListKey(GKEY_MOVEDOWN, GKEY_MOVEUP, GKEY_OK, false, MENU_SLOT_MIXTRUE_LIST);
        CGame.GetEntityIndex(ENUM_MIXTRUE_TYPE_NORMAL, this, CGame.s_entityList[ENTITY_LIST_MIXTRUE]);
        int id = m_count > 0 ? m_curSelect.m_id : 0;
        ResetTextPos(CGame.CalcTextPos(CGame.GetString(STR_SYSTEM_MIXTRUE_NAME_0 + id), EFFORT_LIST_RECT_W, SCROLL_DIR_LEFT));
    }

    void InitMixtrueInfo()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_0, MODULE_UI_HORIZ_0, MODULE_UI_VERTI_0, COLOR_BACK_BLUE, T_FALSE);
        SetListKey(0, 0, GKEY_OK, false, 0);
        InitData(WND_MIXTRUE_INFO_SIZE);
        CWnd wnd0, wnd1;
        wnd0 = GetInstance();
        wnd0.Init(WND_MIXTRUE_MESSAGE, MIXTRUE_MESSAGE_X, MIXTRUE_MESSAGE_Y, MIXTRUE_MESSAGE_W, MIXTRUE_MESSAGE_H, false);
        AppendChild(wnd0);
        wnd1 = GetInstance();
        wnd1.Init(WND_ANIMATION, ANIMATION_RECT_X, ANIMATION_RECT_Y, ANIMATION_RECT_W, ANIMATION_RECT_H, false);
        AppendChild(wnd1);
    }

    void InitMixtrueMessage()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_2, MODULE_UI_HORIZ_2, MODULE_UI_VERTI_2, COLOR_BEIGE, T_FALSE);
        InitData(WND_MIXTRUE_INFO_SIZE);
//@#if SMS
//@        CWnd wnd = GetInstance();
//@        wnd.Init(WND_SMS_MESSAGE, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, false);
//@        AppendChild(wnd);
//@#endif
    }

    void InitAnimation()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(MODULE_UI_CONNER_1, MODULE_UI_HORIZ_1, MODULE_UI_VERTI_1, COLOR_LIGHT_GREEN, T_FALSE);
        s_actorUI.SetPos(m_rect[RECT_X] + (m_rect[RECT_W] >> 1), m_rect[RECT_Y] + (m_rect[RECT_H] >> 1));
        s_actorUI.m_repeat = false;
    }
    void InitUiAnimation()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(UI_ANIMATION_CONNER, UI_ANIMATION_HORIZ, UI_ANIMATION_VERTI, UI_ANIMATION_COLOR, T_FALSE);
        CGame.s_actorAchievement.SetPos(SCREEN_WIDTH_HALF , SCREEN_HEIGHT_HALF);
        CGame.s_actorAchievement.m_repeat = false;
    }
    void InitLinkConfirm()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CWnd\CWnd_Init.h
        SetSkin(CONNER_NONE, HORIZ_NONE, VERTI_NONE, COLOR_BLACK, T_FALSE);
    }

//@@
//@#if EXIST_EFFORT
//@#include "../CWnd/CWnd_Effort.h"
//@#endif

//@#if SMS
//@#include "../CWnd/CWnd_SMS.h"
//@#endif
}
