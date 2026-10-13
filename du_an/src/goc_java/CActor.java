import javax.microedition.lcdui.*;

/**
 * The base class of all actors.
 */
public class CActor
        implements Const
{
    public static final int DATA_LEN_OF_SPRITE = 4; // [modID,flag(bit0:flipX,bit1:flipY), X, Y], modID is byte type
    public static final int SPRITE_DATA_MODID = 0;
    public static final int SPRITE_DATA_OX = 2;
    public static final int SPRITE_DATA_OY = 1;

    public static final int DATA_LEN_OF_MODULE = 4; // [modID,flag(bit0:flipX,bit1:flipY), X, Y], modID is byte type
    public static final int MODULE_DATA_LEFT = 0;
    public static final int MODULE_DATA_TOP = 1;
    public static final int MODULE_DATA_W = 2;
    public static final int MODULE_DATA_H = 3;

    public static final byte RES_TYPE_PBG = 0;
    public static final byte RES_TYPE_IMG = 1;

//@#if SUPERBIG_DATA
    static short[][] s_moduleData; // number * 4 : left, top, width, height
    static short[][] s_animsFramesSet; // size = number of sprites * DATA_LEN_OF_SPRITE
//@#else
//@    static byte[][] s_moduleData; // number * 4 : left, top, width, height
//@    static byte[][] s_animsFramesSet; // size = number of sprites * DATA_LEN_OF_SPRITE
//@#endif
    static short[][] s_animsFrameSetOffset;
    static byte[][] s_animsActionsFrames; // without duration
    static short[][] s_animsActionsFramesOffset;
    static byte[][] s_actionsDuration;
    static byte[][] s_animsActionsPos;
//@#if ANIM_FLAG
//@    static byte[][] s_animsActionFlag;
//@#endif

    int m_animationID;
    int m_actionID;
    int m_actionIDOld;
    int m_frameID;
    int m_spriteID;
    int m_duration;
    byte m_actionOffsetX;
    byte m_actionOffsetY;
//@#if ANIM_FLAG
//@    byte m_actionFlag;
//@#endif
    /**循环播放*/
    boolean m_repeat = true;

    /**动画结束*/
    boolean m_end = false;

    final static int TRAVELLINT_DATA_CAPACITY = 32;
    short[] m_travelLineDest;
    int m_travelLineCount;
    int m_travelLinePoint;
    boolean m_travelLineEnd;
    int m_imgW;
    int m_imgH;

    /**
     * 与物理层碰撞
     */
    boolean m_isCollide = false;

    short[] m_parameters;
    short[] m_activatedBox;
    short[] m_collideBox;
    /** @todo  m_posBox 设计未完*/
    short[] m_posBox;
    int m_classID;
    public int m_flag; // flags

    public static final int RUNTIME_HIDE = 1;

    /**
     * 怪物类Actor的X锚点,怪物游荡的参考点.
     */
    public short m_anchorX;
    /**
     * 怪物类Actor的Y锚点,怪物游荡的参考点.
     */
    public short m_anchorY;

    public short m_curX; // position
    public short m_curY;
    public short m_vX;
    public short m_vY;
    public short m_aX;
    public short m_aY;
    byte m_resType = RES_TYPE_PBG;
//@#if USE_PBG_RES
//@    CPBG m_refPBG;
//@#endif
    Image m_refImage;

    /** destination's coordination*/
    int m_destX, m_destY;

    /**Actor属性*/
    short[] m_properties;

    /**Actor对应的角色数值*/
    int[] m_value;

    /**Actor装备链表*/
    int[] m_equips;

    /**Actor异常状态的数值和时间*/
    int[] m_buff;
    /**Actor提升属性*/
    int[] m_extraValue;

    CEntity m_entitySkill;
    CEntity m_entitySkillEx;
    boolean m_isHumanControl = false;
    CActor m_actorEff = null;

    final static byte FLAG_FLIP_X = 0x01;
    final static byte FLAG_FLIP_Y = 0x02;

//@@..\CActor\CActor_Init.h
    public CActor()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Init.h
        m_isHumanControl = false;
        m_travelLineDest = new short[TRAVELLINT_DATA_CAPACITY * 2];
        m_travelLineCount = 0;
        m_travelLinePoint = 0;
        m_travelLineEnd = false;
        m_properties = new short[DEFINE_AMOUNT_ACTOR_PROPERTY];
        m_properties[DEFINE_ACTOR_PROPERTY_PARA_MOVETYPE] = ACTOR_MOVE_TYPE_STAND;
        m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] = T_TRUE;
        m_chestAmount = 0;
        m_activatedBox = new short[AMOUNT_RECT];
        m_collideBox = new short[AMOUNT_RECT];
        m_posBox = new short[AMOUNT_RECT];
    }

    protected void finalize()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Init.h
        Free();
    }

    public CActor(int classID, int animID, int actionID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Init.h
        this();
        m_classID = classID;
        m_animationID = animID;
        if (actionID >= 0)
        {
            SetAction(actionID);
        }
    }

//@#if USE_PBG_RES
//@    public CActor(int classID, int animID, int actionID, CPBG spbg, boolean repeat)
//@    {
//@        this(classID, animID, actionID);
//@        m_repeat = repeat;
//@        SetPBG(spbg);
//@    }
//@#endif
    public CActor(int classID, int animID, int actionID, Image img, boolean repeat)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Init.h
        this(classID, animID, actionID);
        m_repeat = repeat;
        SetImage(img);
    }

    void Init()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Init.h
        for (int i = 0; i < m_properties.length; i++)
        {
            m_properties[i] = 0;
        }
        m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] = T_TRUE;
    }

//@#if USE_PBG_RES
//@    public void SetPBG(CPBG spbg)
//@    {
//@        m_resType = RES_TYPE_PBG;
//@        m_refImage = null;
//@        m_refPBG = spbg;
//@    }
//@#endif
    public void SetImage(Image image)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Init.h
        m_resType = RES_TYPE_IMG;
//@#if USE_PBG_RES
//@        m_refPBG = null;
//@#endif
        m_refImage = image;
        m_imgW = image.getWidth();
        m_imgH = image.getHeight();
    }

    void Free()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Init.h
        if (null != m_chest)
        {
            m_chest = null;
        }
        if (null != m_actorEff)
        {
            m_actorEff = null;
        }
        if (null != m_properties)
        {
            m_properties = null;
        }
        if (null != m_buff)
        {
            m_buff = null;
        }
        if (null != m_entitySkill)
        {
            ReleaseAllSkill();
            m_entitySkill.Release();
            m_entitySkill = null;
            m_entitySkillEx.Release();
            m_entitySkillEx = null;
        }

        if (null != m_refImage)
        {
            m_refImage = null;
        }
    }

//@@

    /**
     * clone an actor
     * @param curx  new actor's x coordination
     * @param cury  new actor's y coordination
     * @param flag  new actor's flag
     * @param actionID set the new actor to this action
     * @return the new actor just cloned
     */
    CActor Clone(int curx, int cury, int flag, int actionID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        CActor actor = new CActor();
        // set value
        actor.m_classID = m_classID;
        actor.m_flag |= flag;
        actor.m_animationID = m_animationID;
        actor.m_actionID = actionID;
        actor.m_curX = (short) curx;
        actor.m_curY = (short) cury;
        // copy data
        int len = m_parameters.length;
        if (len > 0)
        {
            actor.m_parameters = new short[len];
            System.arraycopy(m_parameters, 0, actor.m_parameters, 0, len);
        }
        actor.m_activatedBox = new short[AMOUNT_RECT];
        actor.m_collideBox = new short[AMOUNT_RECT];
        System.arraycopy(m_activatedBox, 0, actor.m_activatedBox, 0, AMOUNT_RECT);
        System.arraycopy(m_collideBox, 0, actor.m_collideBox, 0, AMOUNT_RECT);
        return actor;
    }

    void BattleClone(CActor obj)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        m_value = null;
        m_value = obj.m_value;
        System.arraycopy(obj.m_properties, 0, m_properties, 0, DEFINE_AMOUNT_ACTOR_PROPERTY);
    }

//@@..\CActor\CActor_Util.h
    public int GetModuleWidth(int moduleID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Util.h
        int res = -1;
        Image image = null;
        boolean bUsePBG = false;
//@#if USE_PBG_RES
//@        CPBG pbg = null;
//@#endif
        if (m_resType == RES_TYPE_PBG)
        {
//@#if USE_PBG_RES
//@            pbg = m_refPBG;
//@#endif
        }
        else
        {
            image = m_refImage;
        }

        //PBG
//@#if USE_PBG_RES
//@        if (pbg != null)
//@        {
//@            bUsePBG = true;
//@        }
//@        else
//@#endif
        {
            if (image == null)
            {
                return -1;
            }
        }
        int modW;
//@#if SUPERBIG_DATA
        short[] module = s_moduleData[m_animationID];
//@#else
//@        byte[] module = s_moduleData[m_animationID];
//@#endif

        if (bUsePBG)
        {
//@#if USE_PBG_RES
//@            int off = moduleID * CPBG.sf_module_size;
//@            modW = pbg.m_modules[off] & 0xFF;
//@            res = modW;
//@#endif
        }
        else
        {
            moduleID <<= 2;
            modW = module[moduleID + 2] & 0xFFFF;
            res = modW;
        }
        return res;
    }

    public int GetModuleHeight(int moduleID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Util.h
        int res = -1;
        Image image = null;
        boolean bUsePBG = false;
//@#if USE_PBG_RES
//@        CPBG pbg = null;
//@#endif
        if (m_resType == RES_TYPE_PBG)
        {
//@#if USE_PBG_RES
//@            pbg = m_refPBG;
//@#endif
        }
        else
        {
            image = m_refImage;
        }

        //PBG
//@#if USE_PBG_RES
//@        if (pbg != null)
//@        {
//@            bUsePBG = true;
//@        }
//@        else
//@#endif
        {
            if (image == null)
            {
                return -1;
            }
        }
        int modH;
//@#if SUPERBIG_DATA
        short[] module = s_moduleData[m_animationID];
//@#else
//@        byte[] module = s_moduleData[m_animationID];
//@#endif

        if (bUsePBG)
        {
//@#if USE_PBG_RES
//@            int off = moduleID * CPBG.sf_module_size;
//@            modH = pbg.m_modules[off + 1] & 0xFF;
//@            res = modH;
//@#endif
        }
        else
        {
            moduleID <<= 2;
            modH = module[moduleID + 3] & 0xFFFF;
            res = modH;
        }
        return res;
    }

//@@
//@@..\CActor\CActor_Skill.h
    int GetSkillCount()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Skill.h
        int res;
        res = m_buff[DB_EFFECT_SET_ENUMERATE_SKILL_EX] > 0 ? (m_entitySkillEx.GetCount() - 1) : (m_entitySkill.GetCount() - 1);
        return res;
    }

    void SetSkill(int skillID, int exp)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Skill.h
        int color = COLOR_DB[CGame.s_dbSkills[skillID][DB_SKILLS_COLOR]];
        int icon = CGame.s_dbSkills[skillID][DB_SKILLS_ICON];
        int skillExID = CGame.s_dbSkills[skillID][DB_SKILLS_SKILL_EX];
        CEntity.Modify(m_entitySkill, skillID, DEFINE_OPERATOR_SET, exp, color, icon);
        if (skillExID >= 0)
        {
            color = COLOR_DB[CGame.s_dbSkills[skillExID][DB_SKILLS_COLOR]];
            CEntity.Modify(m_entitySkillEx, skillExID, DEFINE_OPERATOR_SET, exp, color, icon);
        }
    }

    int GetSkill(int id)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Skill.h
        return m_entitySkill.GetID(id);
    }

    CEntity GetSkillEntity(int id)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Skill.h
        CEntity res = m_entitySkill.SearchObject(id);
        return res;
    }

    void ReleaseAllSkill()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Skill.h
        m_entitySkill.ReleaseAll();
        m_entitySkill.m_next = null;
        m_entitySkillEx.ReleaseAll();
        m_entitySkillEx.m_next = null;
    }

//@@
//@@..\CActor\CActor_Equip.h
    void ClearEquip(int equipID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Equip.h
        if (CGame.s_dbEquips[equipID][DB_EQUIPS_DISAPPEAR] == T_TRUE)
        {
            for (int i = DB_EQUIPS_PURPOSE0; i <= DB_EQUIPS_PURPOSE_LAST; i += 2)
            {
                if (CGame.s_dbEquips[equipID][i] == ENUM_EQUIPS_PURPOSE0_SKILL)
                {
                    SetSkill(CGame.s_dbEquips[equipID][i + 1], -1);
                }
            }
        }
    }
//@@

//@@..\CActor\CActor_Action.h
    /**
     * 设置动作,并制定动作中的特定帧
     * @param actionID action index in animations.
     * @param frameID frame index in actions.
     */
    void SetAction(int actionID, int frameID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Action.h
        SetActionFrame(actionID, frameID, m_curX, m_curY);
    }

    /**
     * 设置动作
     * @param actionID action index in animations.
     * @see #setAction(int state, int dir)
     * @see CAI#PlayAction(int state, int dir)
     */
    void SetAction(int actionID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Action.h
        SetActionFrame(actionID, 0, m_curX, m_curY);
    }

    /**
     * 设置动作,指定动作中的特定帧,并更改X,Y坐标
     * @param actionID set the actor to this action
     * @param frameID draw this frame
     * @param x coordinate x on scene
     * @param y coordinate y on scene
     */
    void SetActionFrame(int actionID, int frameID, int x, int y)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Action.h
        m_curX = (short) x;
        m_curY = (short) y;
        if (m_animationID == -1)
        {
            return;
        }

        int offset = s_animsActionsFramesOffset[m_animationID][actionID];
        int length = s_animsActionsFramesOffset[m_animationID][actionID + 1] - offset;
        if (length == 0)
        {
            return;
        }
        frameID %= length;

        m_spriteID = s_animsActionsFrames[m_animationID][offset + frameID] & 0xFF;
        m_duration = s_actionsDuration[m_animationID][offset + frameID] & 0xFF;
        m_actionOffsetX = s_animsActionsPos[m_animationID][(offset + frameID) << 1];
        m_actionOffsetY = s_animsActionsPos[m_animationID][((offset + frameID) << 1) + 1];

//@#if ANIM_FLAG
//@        m_actionFlag = s_animsActionFlag[m_animationID][offset + frameID];
//@#endif
        m_frameID = frameID;
        m_actionID = actionID;
        m_end = false;
    }

    /**
     * 面朝某个方向
     *
     * @param x int
     * @param y int
     * @param baseAction int
     */
    void FaceTo(int x, int y, int baseAction)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Action.h
        m_actionIDOld = m_actionID;
        int dir = CGame.UtilCalcDirection(m_curX, m_curY, x, y);
        if (dir != -1)
        {
            SetAction(baseAction + dir);
        }
    }

//@@
    public void SetPos(int x, int y)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        m_curX = (short) x;
        m_curY = (short) y;
    }

    void SetDestPos(int x, int y)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        m_destX = x;
        m_destY = y;
    }

    boolean IsInRect(int x, int y, int w, int h)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        GetFrameRect(m_spriteID, m_curX, m_curY, 0);
        return CGame.UtilIsRectsCollide(x, y, w, h, m_posBox[RECT_X], m_posBox[RECT_Y], m_posBox[RECT_W], m_posBox[RECT_H]);
    }
    int m_actDistance = -1 , m_phyDistance = 0;
    void UpdateControl()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        m_isCollide = false;
        int key = -1;
        int stepLength;
        if (CGame.IsKeyPressed(GKEY_MOVEUP))
        {
            key = GKEY_UP;
        }
        else if (CGame.IsKeyPressed(GKEY_MOVEDOWN))
        {
            key = GKEY_DOWN;
        }
        else if (CGame.IsKeyPressed(GKEY_MOVELEFT))
        {
            key = GKEY_LEFT;
        }
        else if (CGame.IsKeyPressed(GKEY_MOVERIGHT))
        {
            key = GKEY_RIGHT;
        }
        if (key != -1)
        {
            //无控制权
            int dir = -1;
            switch (key)
            {
            case GKEY_UP:
                dir = DIRECTION_UP;
                break;
            case GKEY_DOWN:
                dir = DIRECTION_DOWN;
                break;
            case GKEY_LEFT:
                dir = DIRECTION_LEFT;
                break;
            case GKEY_RIGHT:
                dir = DIRECTION_RIGHT;
                break;
            }

            stepLength = m_properties[DEFINE_ACTOR_PROPERTY_PARA_TRANSPORT] == T_TRUE ? ACTOR_TRANSPORT_STEP_LEN : ACTOR_STEP_LEN;
            UpdateOrientedMove(dir, stepLength, true, true);

            if (m_actDistance == 0 || m_phyDistance == 0)
            {
            }
            else
            {
                //同步队伍位置
                CGame.SynchTeamPos();
            }
        }
        else
        {
            //有控制权
            CGame.SetFollowActorAction();
        }
    }

    void Update()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        switch (m_animationID)
        {
        default:
            UpdateMove();
            UpdateAnimation();
            UpdateEffect();
            if (m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISENEMY] == T_TRUE && m_properties[DEFINE_ACTOR_PROPERTY_PARA_SPAWNTIMER] != 0)
            {
                m_properties[DEFINE_ACTOR_PROPERTY_PARA_SPAWNTIMER]--;
                if (m_properties[DEFINE_ACTOR_PROPERTY_PARA_SPAWNTIMER] <= 0)
                {
                    m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] = T_TRUE;
                }
            }
            break;
        }
    }

//@@..\CActor\CActor_Move.h
    void Move()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Move.h
        if (m_vX >= 0)
        {
            m_curX = (short) Math.min(m_destX, m_curX + m_vX);
        }
        else
        {
            m_curX = (short) Math.max(m_destX, m_curX + m_vX);
        }
        if (m_vY >= 0)
        {
            m_curY = (short) Math.min(m_destY, m_curY + m_vY);
        }
        else
        {
            m_curY = (short) Math.max(m_destY, m_curY + m_vY);
        }
    }

    void UpdateOrientedMove(int dir, int distance, boolean isContinue, boolean collide)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Move.h
        //这次移动，实际的移动距离
        int actualDistance = distance, tmpDistance, actualX = m_curX, actualY = m_curY;
        int collideType = COLLIDE_TYPE_NONE;
        int newX = -1, newY = -1;
        int tileSize = TILE_SIZE;
        int tileCount = 0;
        int action = 0;
        //tileCount
        if (m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISFLY] == T_FALSE)
        {
            switch (dir)
            {
            case DIRECTION_UP:
                tileCount = CGame.UtilCeiling(m_collideBox[RECT_H], tileSize);
                newX = m_curX + m_collideBox[RECT_X] ;
                newY = m_curY + m_collideBox[RECT_Y] - distance ;
                break;
            case DIRECTION_DOWN:
                tileCount = CGame.UtilCeiling(m_collideBox[RECT_H], tileSize);
                newX = m_curX + m_collideBox[RECT_X] ;
                newY = m_curY + m_collideBox[RECT_Y] + distance + m_collideBox[RECT_H] ;
                break;
            case DIRECTION_LEFT:
                tileCount = CGame.UtilCeiling(m_collideBox[RECT_W], tileSize);
                newX = m_curX + m_collideBox[RECT_X] - distance ;
                newY = m_curY + m_collideBox[RECT_Y] ;
                break;
            case DIRECTION_RIGHT:
                tileCount = CGame.UtilCeiling(m_collideBox[RECT_W], tileSize) ;
                newX = m_curX + m_collideBox[RECT_X] + distance + m_collideBox[RECT_W] ;
                newY = m_curY + m_collideBox[RECT_Y];
                break;
            }
        }
        else
        {
            //更改飞行时碰撞当前坐标  策划不改的程序得改\u2740
            switch (dir)
            {
            case DIRECTION_UP:
                tileCount = CGame.UtilCeiling(m_collideBox[RECT_H], tileSize);
                newX = m_curX + m_collideBox[RECT_X] ;
                newY = m_curY + m_collideBox[RECT_Y] - distance + ACTOR_NEW_COORDINATE_OFFSET_Y;
                break;
            case DIRECTION_DOWN:
                tileCount = CGame.UtilCeiling(m_collideBox[RECT_H], tileSize);
                newX = m_curX + m_collideBox[RECT_X] ;
                newY = m_curY + m_collideBox[RECT_Y] + distance + m_collideBox[RECT_H] - ACTOR_NEW_COORDINATE_OFFSET_Y;
                break;
            case DIRECTION_LEFT:
                tileCount = CGame.UtilCeiling(m_collideBox[RECT_W], tileSize);
                newX = m_curX + m_collideBox[RECT_X] - distance + ACTOR_NEW_COORDINATE_OFFSET_X;
                newY = m_curY + m_collideBox[RECT_Y] ;
                break;
            case DIRECTION_RIGHT:
                tileCount = CGame.UtilCeiling(m_collideBox[RECT_W], tileSize) ;
                newX = m_curX + m_collideBox[RECT_X] + distance + m_collideBox[RECT_W] - ACTOR_NEW_COORDINATE_OFFSET_X;
                newY = m_curY + m_collideBox[RECT_Y];
                break;
            }
        }
        if (CGame.CheckMapBoundary(newX, newY))
        {
            m_phyDistance = 0;
            return;
        }

        int actorDistance = Integer.MAX_VALUE;
        if (m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISFLY] == T_FALSE)
        {
            actorDistance = collide ? CGame.IsRectCollideWithAnyActor(m_curX + m_collideBox[RECT_X], m_curY + m_collideBox[RECT_Y],
                    m_collideBox[RECT_W], m_collideBox[RECT_H], dir, distance) : -1;
            if (actorDistance >= 0)
            {
                collideType = COLLIDE_TYPE_ACTOR;
                m_actDistance = actorDistance;
            }
            else
            {
                m_actDistance = -1;
                for (int i = 0; i <= tileCount; i++)
                {
                    switch (dir)
                    {
                    case DIRECTION_UP:
                        newX = m_curX + Math.min(i * tileSize, m_collideBox[RECT_W]) + m_collideBox[RECT_X];
                        newY = m_curY + m_collideBox[RECT_Y];
                        tmpDistance = CGame.UtilPointCollideWhitPhy(newX, newY, dir, distance);
                        if (tmpDistance < actualDistance)
                        {
                            //物理层碰撞
                            collideType = COLLIDE_TYPE_PHY;
                            actualDistance = tmpDistance;
                        }
                        //IsCollide
                        actualY = m_curY - actualDistance;
                        break;
                    case DIRECTION_DOWN:
                        newX = m_curX + Math.min(i * tileSize, m_collideBox[RECT_W]) + m_collideBox[RECT_X];
                        newY = m_curY + m_collideBox[RECT_Y] + m_collideBox[RECT_H];
                        tmpDistance = CGame.UtilPointCollideWhitPhy(newX, newY, dir, distance);
                        if (tmpDistance < actualDistance)
                        {
                            //物理层碰撞
                            collideType = COLLIDE_TYPE_PHY;
                            actualDistance = tmpDistance;
                        }
                        actualY = m_curY + actualDistance;
                        break;
                    case DIRECTION_LEFT:
                        newX = m_curX + m_collideBox[RECT_X];
                        newY = m_curY + Math.min(i * tileSize, m_collideBox[RECT_H]) + m_collideBox[RECT_Y];
                        tmpDistance = CGame.UtilPointCollideWhitPhy(newX, newY, dir, distance);
                        if (tmpDistance < actualDistance)
                        {
                            //物理层碰撞
                            collideType = COLLIDE_TYPE_PHY;
                            actualDistance = tmpDistance;
                        }
                        actualX = m_curX - actualDistance;
                        break;
                    case DIRECTION_RIGHT:
                        newX = m_curX + m_collideBox[RECT_X] + m_collideBox[RECT_W];
                        newY = m_curY + Math.min(i * tileSize, m_collideBox[RECT_H]) + m_collideBox[RECT_Y];
                        tmpDistance = CGame.UtilPointCollideWhitPhy(newX, newY, dir, distance);
                        if (tmpDistance < actualDistance)
                        {
                            //物理层碰撞
                            collideType = COLLIDE_TYPE_PHY;
                            actualDistance = tmpDistance;
                        }
                        actualX = m_curX + actualDistance;
                        break;
                    }
                }
               m_phyDistance = actualDistance;
            }
        }
        else
        {
            switch (dir)
            {
            case DIRECTION_UP:
                actualY = m_curY - distance;
                break;
            case DIRECTION_DOWN:
                actualY = m_curY + distance;
                break;
            case DIRECTION_LEFT:
                actualX = m_curX - distance;
                break;
            case DIRECTION_RIGHT:
                actualX = m_curX + distance;
                break;
            }
        }
        switch (collideType)
        {
        case COLLIDE_TYPE_ACTOR:
            switch (dir)
            {
            case DIRECTION_UP:
                m_curX = (short) (m_curX);
                m_curY = (short) (m_curY - actorDistance);
                break;
            case DIRECTION_DOWN:
                m_curX = (short) (m_curX);
                m_curY = (short) (m_curY + actorDistance);
                break;
            case DIRECTION_LEFT:
                m_curX = (short) (m_curX - actorDistance);
                m_curY = (short) (m_curY);
                break;
            case DIRECTION_RIGHT:
                m_curX = (short) (m_curX + actorDistance);
                m_curY = (short) (m_curY);
                break;
            }
            break;
        case COLLIDE_TYPE_PHY:
        case COLLIDE_TYPE_NONE:
            m_curX = (short) actualX;
            m_curY = (short) actualY;
            break;
        }
        switch (dir)
        {
        case DIRECTION_UP:
            action = ACTION_MOVE_UP;
            break;
        case DIRECTION_RIGHT:
            action = ACTION_MOVE_RIGHT;
            break;
        case DIRECTION_DOWN:
            action = ACTION_MOVE_DOWN;
            break;
        case DIRECTION_LEFT:
            action = ACTION_MOVE_LEFT;
            break;
        }

        if (m_properties[DEFINE_ACTOR_PROPERTY_PARA_TRANSPORT] == T_TRUE)
        {
            action += ACTION_DEVIATION + 4;
        }

        if (action != m_actionIDOld)
        {
            SetAction(action, m_frameID);
            m_actionIDOld = action;
        }
        if (actualDistance != distance && isContinue)
        {
            int distanceRemain = (distance - actualDistance);
            int newDir = CGame.GetSlideDir(m_curX + m_collideBox[RECT_X], m_curY + m_collideBox[RECT_Y], m_collideBox[RECT_W], m_collideBox[RECT_H], dir);
            UpdateOrientedMove(newDir, distanceRemain, false, true);
        }
    }

    void UpdateMove()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Move.h
        int i, j, x, y, v;

        switch (m_properties[DEFINE_ACTOR_PROPERTY_PARA_MOVETYPE])
        {
        case ACTOR_MOVE_TYPE_STAND:
            m_curX += m_vX;
            m_curY += m_vY;
            m_vX += m_aX;
            m_vY += m_aY;
            break;
        case ACTOR_MOVE_TYPE_SYNCH:
            break;
        case ACTOR_MOVE_TYPE_TRAVELLINE:
            boolean isEnd = TravelLineUpdate();
            if (isEnd)
            {
                m_travelLineEnd = true;
                /** @todo 加入TL重复的实现 */
                m_properties[DEFINE_ACTOR_PROPERTY_PARA_MOVETYPE] = ACTOR_MOVE_TYPE_STAND;
                m_travelLineCount = 0;
                m_travelLinePoint = 0;
                m_vX = 0;
                m_vY = 0;
                m_aX = 0;
                m_aY = 0;
            }
            break;
        case ACTOR_MOVE_TYPE_AUTOMOVE:
            if (m_vX >= 0)
            {
                m_curX = (short) Math.min(m_destX, m_curX + m_vX);
            }
            else
            {
                m_curX = (short) Math.max(m_destX, m_curX + m_vX);
            }
            if (m_vY >= 0)
            {
                m_curY = (short) Math.min(m_destY, m_curY + m_vY);
            }
            else
            {
                m_curY = (short) Math.max(m_destY, m_curY + m_vY);
            }
            if ((m_curX == m_destX) && (m_curY == m_destY))
            {
                m_properties[DEFINE_ACTOR_PROPERTY_PARA_MOVETYPE] = ACTOR_MOVE_TYPE_STAND;
                m_vX = 0;
                m_vY = 0;
                m_aX = 0;
                m_aY = 0;
                if (m_animationID >= ANIM_FIRST_LA && m_animationID <= ANIM_LAST_LA)
                {
                    if (m_actionID >= ACTION_MOVE_DOWN && m_actionID <= ACTION_MOVE_RIGHT)
                    {
                        SetAction(ACTION_STAND_DOWN + (m_actionID - ACTION_MOVE_DOWN));
                    }
                    else if (m_actionID >= ACTION_STAND_DOWN && m_actionID <= ACTION_STAND_RIGHT)
                    {
                        SetAction(ACTION_STAND_DOWN + (m_actionID - ACTION_STAND_DOWN));
                    }
                }
            }
            break;
        case ACTOR_MOVE_TYPE_RANGENPC:
            if ((m_curX == m_destX) && (m_curY == m_destY))
            {
                i = CGame.UtilRandom(0, 2);
            }
            Move();
            break;
        case ACTOR_MOVE_TYPE_RANGERANDOM:
            if (m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] == T_TRUE && CGame.s_actorHumanControl != null
                && CGame.s_actorHumanControl.m_properties[DEFINE_ACTOR_PROPERTY_PARA_TRANSPORT] != T_TRUE
                && CGame.s_actorHumanControl.m_curX > m_curX - ENEMY_SEARCH_RANGE && CGame.s_actorHumanControl.m_curX < m_curX + ENEMY_SEARCH_RANGE
                && CGame.s_actorHumanControl.m_curY > (m_curY - ENEMY_SEARCH_RANGE) && CGame.s_actorHumanControl.m_curY < (m_curY + ENEMY_SEARCH_RANGE))
            {
                if (m_curX != CGame.s_actorHumanControl.m_curX)
                {
                    m_curX += (CGame.s_actorHumanControl.m_curX - m_curX > 0 ? ENEMY_SEARCH_SPEED : -ENEMY_SEARCH_SPEED);
                }
                if (m_curY != CGame.s_actorHumanControl.m_curY)
                {
                    m_curY += (CGame.s_actorHumanControl.m_curY - m_curY > 0 ? ENEMY_SEARCH_SPEED : -ENEMY_SEARCH_SPEED);
                }
                if (CGame.s_actorHumanControl.m_properties[DEFINE_ACTOR_PROPERTY_PARA_ISFLY] == T_FALSE)
                {
                    if (CGame.UtilIsRectsCollide(
                            m_curX + m_collideBox[RECT_X],
                            m_curY + m_collideBox[RECT_Y],
                            m_collideBox[RECT_W],
                            m_collideBox[RECT_H],
                            CGame.s_actorHumanControl.m_curX + CGame.s_actorHumanControl.m_collideBox[RECT_X],
                            CGame.s_actorHumanControl.m_curY + CGame.s_actorHumanControl.m_collideBox[RECT_Y],
                            CGame.s_actorHumanControl.m_collideBox[RECT_W],
                            CGame.s_actorHumanControl.m_collideBox[RECT_H]))
                    {
                        int k = 0;
                        int p = 0;
                        int enemyID;
                        for (i = 0; i < m_chestAmount; i++)
                        {
                            k += m_chest[i * 2 + 1];
                        }
                        p = CGame.UtilRandom(0, k);
                        enemyID = m_chestAmount - 1;
                        for (i = 0; i < m_chestAmount; i++)
                        {
                            p -= m_chest[i * 2 + 1];
                            if (p < 0)
                            {
                                enemyID = i;
                                break;
                            }
                        }
                        if (!CGame.s_battleStart)
                        {
                            CGame.s_engage = m_chest[enemyID * 2];
                            m_properties[DEFINE_ACTOR_PROPERTY_PARA_SHOW] = T_FALSE;
                            m_properties[DEFINE_ACTOR_PROPERTY_PARA_SPAWNTIMER] = m_properties[DEFINE_ACTOR_PROPERTY_PARA_SPAWNTIME];
                        }
                        CGame.s_battleStart = true;
                    }
                }
            }
            //MoveTo(destX, destY, ACTOR_STEP_LEN);
            else
            {
                Move();
                if ((m_curX == m_destX) && (m_curY == m_destY))
                {
                    //决定下一个目标
                    do
                    {
                        x = m_anchorX + CGame.UtilRandom( -32, 32);
                        y = m_anchorY + CGame.UtilRandom( -32, 32);
                        i = CGame.UtilGetDistance(x, y, m_curX, m_curY);
                        j = CGame.UtilGetDistance(x, y, m_anchorX, m_anchorY);
                    }
                    while (i >= 16 * 16 && j >= 16 * 16);
                    //确定速度
                    j = CGame.UtilRandom(4, 8);
                    m_destX = x;
                    m_destY = y;
                    m_vX = (short) CGame.UtilCeiling(m_destX - m_curX, j);
                    m_vY = (short) CGame.UtilCeiling(m_destY - m_curY, j);
                    m_aX = 0;
                    m_aY = 0;
                }
            }
            break;
        default:
            break;
        }
    }

//@@
//@@..\CActor\CActor_Collide.h
    void SetCollideRect(int w, int h)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Collide.h
        m_collideBox[RECT_X] = (short) - (w >> 1);
        m_collideBox[RECT_Y] = (short) - (h >> 1);
        m_collideBox[RECT_W] = (short) w;
        m_collideBox[RECT_H] = (short) h;
    }

//@@

    void UpdateEffect()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        if (m_properties[DEFINE_ACTOR_PROPERTY_PARA_EMOTION_TIME] > 0 && null != m_actorEff)
        {
            m_actorEff.SetPos(m_curX, m_curY - 32);
            m_actorEff.Update();
            m_properties[DEFINE_ACTOR_PROPERTY_PARA_EMOTION_TIME]--;
        }
    }

    void DrawEffectAnimation(Graphics g, int cameraX, int cameraY)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        if (m_properties[DEFINE_ACTOR_PROPERTY_PARA_EMOTION_TIME] > 0 && null != m_actorEff)
        {
            m_actorEff.DrawAnimation(g, cameraX, cameraY);
        }
    }


//===========================================================================//
// Animations
//===========================================================================//
    /**
     * update animation
     */
    void UpdateAnimation()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        if (m_animationID == -1 || m_end)
        { // if m_animationID == -1, indicate that this actor has no animation
            return;
        }
        m_duration--;
        if (m_duration > 0)
        {
            return;
        }
        m_frameID++;
        int offset = s_animsActionsFramesOffset[m_animationID][m_actionID];
        int length = s_animsActionsFramesOffset[m_animationID][m_actionID + 1] - offset;
        if (m_frameID >= length)
        {
            //anim.m_nFrame[m_actionID])
            if (!m_repeat)
            {
                m_end = true;
                return;
            }
            m_frameID = 0;
        }
        m_spriteID = s_animsActionsFrames[m_animationID][offset + m_frameID] & 0xFF;
        m_duration = s_actionsDuration[m_animationID][offset + m_frameID] & 0xFF;
        m_actionOffsetX = s_animsActionsPos[m_animationID][(offset + m_frameID) << 1];
        m_actionOffsetY = s_animsActionsPos[m_animationID][((offset + m_frameID) << 1) + 1];
//@#if ANIM_FLAG
//@        m_actionFlag = s_animsActionFlag[m_animationID][offset + m_frameID];
//@#endif
    }

    public int GetModulesCountInFrame(int frameID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        int begin = s_animsFrameSetOffset[m_animationID][frameID];
        int end = s_animsFrameSetOffset[m_animationID][frameID + 1];
        return ((end - begin) >> 2);
    }


    void UpdateAnimation(boolean bRepeat)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        if (m_frameID == -1 && !bRepeat)
        {
            return;
        }
        m_frameID++;
        //byte[] frames = s_animsActionsFrames[m_animationID][m_actionID & CRes.MASK_REAL_ACTION_ID];
        int offset = s_animsActionsFramesOffset[m_animationID][m_actionID];
        int length = s_animsActionsFramesOffset[m_animationID][m_actionID + 1] - offset;
        if (m_frameID >= length)
        {
            m_frameID = -1;
            if (!bRepeat)
            {
                return;
            }
        }
        m_spriteID = s_animsActionsFrames[m_animationID][offset + m_frameID] & 0xFF;
        m_duration = s_actionsDuration[m_animationID][offset + m_frameID] & 0xFF;
        m_actionOffsetX = s_animsActionsPos[m_animationID][(offset + m_frameID) << 1];
        m_actionOffsetY = s_animsActionsPos[m_animationID][((offset + m_frameID) << 1) + 1];
//@#if ANIM_FLAG
//@        m_actionFlag = s_animsActionFlag[m_animationID][offset + m_frameID];
//@#endif
    }

    void DrawModule(Graphics g, int moduleID, int x, int y, int flip)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        switch (m_resType)
        {
        case RES_TYPE_PBG:

//@#if USE_PBG_RES
//@            CPBG pbg = m_refPBG;
//@            if (pbg != null)
//@            {
//@                pbg.draw(g, moduleID, x, y, flip);
//@            }
//@#endif
            break;
        case RES_TYPE_IMG:
            int screenX = x;
            int screenY = y;
            Image image = m_refImage;

            int i = 0, posX = 0, posY = 0;
            int modID, modX = 0, modY = 0, modW, modH;

//@#if SUPERBIG_DATA
            short[] sprite = s_animsFramesSet[m_animationID];

//@#else
//@#endif
//@#if SUPERBIG_DATA
            short[] module = s_moduleData[m_animationID];

//@#else
//@            byte[] module = s_moduleData[m_animationID];
//@#endif
            modID = moduleID << 2;

//@#if SUPERBIG_DATA
            modX = module[modID + 1] & 0xFFFF;
            modY = module[modID + 0] & 0xFFFF;
            modW = module[modID + 2] & 0xFFFF;
            modH = module[modID + 3] & 0xFFFF;

//@#else
//@            modX = module[modID + 1] & 0xFF;
//@            modY = module[modID + 0] & 0xFF;
//@            modW = module[modID + 2] & 0xFF;
//@            modH = module[modID + 3] & 0xFF;
//@#endif
            posX = screenX + m_actionOffsetX;
            posY = screenY + m_actionOffsetY;

            if (modX + modW > m_imgW)
            {
                modW = m_imgW - modX;
            }
            if (modY + modH > m_imgH)
            {
                modH = m_imgH - modY;
            }
            if (modW <= 0 || modH <= 0)
            {
            }
            else
            {
//@#if NOKIA_LIB
//@                CGame.DrawRegion(image, modX, modY, modW, modH, flip, posX, posY, ALIGN_LEFT_TOP);
//@#else
                g.drawRegion(image, modX, modY, modW, modH, flip, posX, posY, ALIGN_LEFT_TOP);
//@#endif
            }
            g.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

            break;
        default:
            break;
        }
    }

    void DrawModule(Graphics g, int moduleID, int x, int y)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        DrawModule(g, moduleID, x, y, TRANS_NONE);
    }

    public void GetModuleRect(int[] rc, int module, int posX, int posY)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        int offset = module << 2;
        rc[0] = posX;
        rc[1] = posY;
        rc[2] = posX + s_moduleData[m_animationID][offset + 2];
        rc[3] = posY + s_moduleData[m_animationID][offset + 3];
    }

    /**
     * GetModuleRectInFrame
     * 获得FRAME中的MODULE的信息
     * 返回一个数组
     * RC[0]:X
     * RC[1]:Y
     * RC[2]:X+W
     * RC[3]:Y+H
     *
     * @param rect int[]
     * @param moduleIDInFrame int
     * @param frameID int
     */
    public void GetModuleRectInFrame(short[] rect, int moduleIDInFrame, int frameID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
//@#if SUPERBIG_DATA
        short[] sprite = s_animsFramesSet[m_animationID];
//@#else
//@        byte[] sprite = s_animsFramesSet[m_animationID];
//@#endif
        int begin = s_animsFrameSetOffset[m_animationID][frameID] + moduleIDInFrame * DATA_LEN_OF_SPRITE;

        //step1 获得真正的module的ID
        int _realModID = sprite[begin + SPRITE_DATA_MODID];
//@#if SUPERBIG_DATA
        short[] module = s_moduleData[m_animationID];
//@#else
//@        byte[] module = s_moduleData[m_animationID];
//@#endif
        rect[0] = sprite[begin + SPRITE_DATA_OX];
        rect[1] = sprite[begin + SPRITE_DATA_OY];

        int offset = _realModID * DATA_LEN_OF_MODULE;

        rect[2] = module[offset + MODULE_DATA_W];
        rect[3] = module[offset + MODULE_DATA_H];

        //然后根据这个module在frame中的位置和flip信息调整XY坐标

    }

    /**
     * GetFramesCountInAction
     * 获得指定的Action中包含多少个frame
     *
     * @param animID int
     * @return int
     */
    int GetFramesCountInAction(int actionID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        int offset = s_animsActionsFramesOffset[m_animationID][actionID];
        int length = s_animsActionsFramesOffset[m_animationID][actionID + 1] - offset;
        return length;
    }

    int GetFramesIndexInAction(int actionID, int frameNo)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        int offset = s_animsActionsFramesOffset[m_animationID][actionID];
        int index = s_animsActionsFrames[m_animationID][offset + frameNo] & 0xFF;
        return index;
    }

    /**
     * 获得一个FRAME的总面积
     *
     * @param rect int[]
     * @param frameID int
     */
    public void GetFrameRect(int frameID, int posX, int posY, int flags)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        //step 1. 查找这个frame一共有几个module
        int moduleCount = GetModulesCountInFrame(frameID);
        m_posBox[RECT_X] = m_posBox[RECT_Y] = m_posBox[RECT_W] = m_posBox[RECT_H] = 0;
        if (moduleCount <= 0)
        {
            return;
        }

        int fx = 0;
        int fy = 0;
        int fw = 0;
        int fh = 0;
        for (int i = 0; i < moduleCount; i++)
        {
            GetModuleRectInFrame(m_posBox, i, frameID);
            int minX = Math.min(fx, m_posBox[0]);
            int minY = Math.min(fy, m_posBox[1]);
            int maxW = Math.max(fx + fw, m_posBox[0] + m_posBox[2]) - minX;
            int maxH = Math.max(fy + fh, m_posBox[1] + m_posBox[3]) - minY;

            fx = minX;
            fy = minY;
            fw = maxW;
            fh = maxH;
        }
        posX = ((flags & FLAG_FLIP_X) != 0) ? (posX - (fx + fw)) : (posX + fx);
        posY = ((flags & FLAG_FLIP_Y) != 0) ? (posY - (fy + fh)) : (posY + fy);

        m_posBox[RECT_X] = (short) posX;
        m_posBox[RECT_Y] = (short) posY;

        m_posBox[RECT_W] = (short) fw;
        m_posBox[RECT_H] = (short) fh;
    }

    void DrawFrame(Graphics g, int X, int Y, int frameID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        int screenX = X;
        int screenY = Y;
        Image image = null;
//@#if USE_PBG_RES
//@        CPBG pbg = null;
//@#endif
        if (m_resType == RES_TYPE_PBG)
        {
//@#if USE_PBG_RES
//@            pbg = m_refPBG;
//@#endif
        }
        else if (m_resType == RES_TYPE_IMG)
        {
            image = m_refImage;
        }

        int i = 0, posX = 0, posY = 0, flag = 0;
        int modID, modX = 0, modY = 0, modW, modH, clipW, clipH;

//@#if SUPERBIG_DATA
        short[] sprite = s_animsFramesSet[m_animationID];
//@#else
//@        byte[] sprite = s_animsFramesSet[m_animationID];
//@#endif
        int begin = s_animsFrameSetOffset[m_animationID][frameID];
        int end = s_animsFrameSetOffset[m_animationID][frameID + 1];
//@#if SUPERBIG_DATA
        short[] module = s_moduleData[m_animationID];
//@#else
//@        byte[] module = s_moduleData[m_animationID];
//@#endif
        int flagOff = 3;
        int flipXOff = 2;
        int flipYOff = 1;
        int spriteOff = DATA_LEN_OF_SPRITE;

        for (i = begin; i < end; i += spriteOff)
        {
            modID = sprite[i] & 0xFF;
            flag = sprite[i + flagOff] & 0xFF;
            if (m_resType == RES_TYPE_PBG)
            {
//@#if USE_PBG_RES
//@                int off = modID * CPBG.sf_module_size;
//@                modW = pbg.m_modules[off] & 0xFF;
//@                modH = pbg.m_modules[off + 1] & 0xFF;
//@#endif
            }
//@#if ANIM_FLAG
//@            //m_actionFlag = s_animsActionFlag[m_animationID][offset + m_frameID];
//@            if((m_actionFlag & 0x01) != 0)
//@            {
//@                posX = screenX - m_actionOffsetX;
//@                flag ^= 0x01;
//@            }
//@            else
//@            {
//@                posX = screenX + m_actionOffsetX;
//@            }
//@            if ((m_actionFlag & 0x02) != 0)
//@            {
//@                posY = screenY - m_actionOffsetY;
//@                flag ^= 0x02;
//@            }
//@            else
//@            {
//@                posY = screenY + m_actionOffsetY;
//@            }
//@#else
            posX = screenX + m_actionOffsetX;
            posY = screenY + m_actionOffsetY;
//@#endif
            if ((m_flag & 0x01) != 0)
            {
                posX -= sprite[i + flipXOff];
                flag ^= 0x01;
            }
            else
            {
                posX += sprite[i + flipXOff];
            }
            if ((m_flag & 0x02) != 0)
            {
                posY -= sprite[i + flipYOff];
                flag ^= 0x02;
            }
            else
            {
                posY += sprite[i + flipYOff];
            }
            switch (flag)
            {
            case 1: //flipX
                flag = TRANS_MIRROR;
                break;
            case 2: //flipY
                flag = TRANS_MIRROR_ROT180;
                break;
            case 3: //flipX + flipY
                flag = TRANS_ROT180;
                break;
            default:
                flag = 0;
            }
            if (m_resType == RES_TYPE_PBG)
            {
//@#if USE_PBG_RES
//@                pbg.draw(g, modID, posX, posY, flag);
//@#endif
            }
            else
            {
                DrawModule(g, modID, posX, posY, flag);
            }
        }
        g.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

//@#if USE_PBG_RES
//@    public void attachPBG(CPBG pbg)
//@    {
//@        m_refPBG = pbg;
//@        m_resType = RES_TYPE_PBG;
//@    }
//@#endif

    void DrawAnimation(Graphics g, int cameraX, int cameraY)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        int screenX = m_curX - cameraX;
        int screenY = m_curY - cameraY;
        int id = 0;
        Image image = null;
        //boolean bUsePBG = false;
//@#if USE_PBG_RES
//@        CPBG pbg = null;
//@#endif
        switch (m_resType)
        {
        case RES_TYPE_PBG:

//@#if USE_PBG_RES
//@            pbg = m_refPBG;
//@#endif
            break;
        case RES_TYPE_IMG:
            image = m_refImage;
            break;
        default:
            break;
        }

        //PBG
//@#if USE_PBG_RES
//@        if (pbg != null)
//@        {
//@        }
//@        else
//@#endif
        {
            if (image == null)
            {
                return;
            }
            m_imgW = image.getWidth();
            m_imgH = image.getHeight();
        }

        int i = 0, posX = 0, posY = 0, flag = 0;
        int modID, modX = 0, modY = 0, modW = 0, modH = 0;
//@#if SUPERBIG_DATA
        short[] sprite = s_animsFramesSet[m_animationID];
//@#else
//@        byte[] sprite = s_animsFramesSet[m_animationID];
//@#endif
        int begin = s_animsFrameSetOffset[m_animationID][m_spriteID];
        int end = s_animsFrameSetOffset[m_animationID][m_spriteID + 1];
//@#if SUPERBIG_DATA
        short[] module = s_moduleData[m_animationID];
//@#else
//@        byte[] module = s_moduleData[m_animationID];
//@#endif
        int flagOff = 3;
        int offsetX = 2;
        int offsetY = 1;
        int spriteOff = DATA_LEN_OF_SPRITE;

        for (i = begin; i < end; i += spriteOff)
        {
            modID = sprite[i] & 0xFF;
            flag = sprite[i + flagOff] & 0xFF;

            switch (m_resType)
            {
            case RES_TYPE_PBG:

//@#if USE_PBG_RES
//@                int off = modID * CPBG.sf_module_size;
//@                modW = pbg.m_modules[off] & 0xFF;
//@                modH = pbg.m_modules[off + 1] & 0xFF;
//@#endif
                break;
            case RES_TYPE_IMG:
                id = modID << 2;

//@#if SUPERBIG_DATA
                modY = module[id] & 0xFFFF;
                modX = module[id + 1] & 0xFFFF;
                modW = module[id + 2] & 0xFFFF;
                modH = module[id + 3] & 0xFFFF;

//@#else
//@                modY = module[id] & 0xFF;
//@                modX = module[id + 1] & 0xFF;
//@                modW = module[id + 2] & 0xFF;
//@                modH = module[id + 3] & 0xFF;
//@#endif
                break;
            }
//@#if ANIM_FLAG
//@            //m_actionFlag = s_animsActionFlag[m_animationID][offset + m_frameID];
//@#else
            posX = screenX + m_actionOffsetX;
            posY = screenY + m_actionOffsetY;
//@#endif
            if ((m_flag & 0x01) != 0)
            {
                posX -= sprite[i + offsetX];
                flag ^= 0x01;
            }
            else
            {
                posX += sprite[i + offsetX];
            }
            if ((m_flag & 0x02) != 0)
            {
                posY -= sprite[i + offsetY];
                flag ^= 0x02;
            }
            else
            {
                posY += sprite[i + offsetY];
            }
            if ((flag & 0x04) != 0)
            {
                if ((flag & 0x01) == 0)
                {
                    posX -= modH;
                }
                if ((flag & 0x02) != 0)
                {
                    posY -= modW;
                }
            }
            switch (flag)
            {
            case 1: //flipX
                flag = TRANS_MIRROR;
                break;
            case 2: //flipY
                flag = TRANS_MIRROR_ROT180;
                break;
            case 3: //flipX + flipY
                flag = TRANS_ROT180;
                break;
            default:
                flag = 0;
            }

            switch (m_resType)
            {
            case RES_TYPE_PBG:

//@#if USE_PBG_RES
//@                pbg.draw(g, modID, posX, posY, flag);
//@#endif
                break;
            case RES_TYPE_IMG:
                if (modX + modW > m_imgW)
                {
                    modW = m_imgW - modX;
                }
                if (modY + modH > m_imgH)
                {
                    modH = m_imgH - modY;
                }
                if (modW <= 0 || modH <= 0)
                {
                    continue;
                }

                DrawModule(g, modID, posX, posY, flag);
                break;
            default:
                break;
            }
        }

        if (DEBUG_SHOWACTORPHYCOLLIDE)
        {
            if (m_isCollide)
            {
                CGame.DrawSystemString(g, "Collide!", screenX, screenY + FONT_HEIGHT, 0, 0xffffff, 0);
            }
        }
        if (DEBUG_SHOWACTORPOS)
        {
            g.setColor(0x0);
            g.fillRect(screenX - 2, screenY - 2, 5, 5);
            g.setColor(0xffffff);
            g.fillRect(screenX - 1, screenY - 1, 3, 3);
            g.setColor(0xff0000);
            g.fillRect(screenX, screenY, 1, 1);
        }
//@#if DEBUG_SHOW_ACTIVEZONE
//@        if (m_activatedBox[RECT_W] > 0 && m_activatedBox[RECT_H] > 0)
//@        {
//@            g.setColor(COLOR_YELLOW);
//@            g.drawRect(screenX + m_activatedBox[RECT_X],
//@                       screenY + m_activatedBox[RECT_Y],
//@                       m_activatedBox[RECT_W],
//@                       m_activatedBox[RECT_H]);
//@            g.setColor(COLOR_VOILET);
//@            g.drawRect(screenX + m_activatedBox[RECT_X] - 1,
//@                       screenY + m_activatedBox[RECT_Y] - 1,
//@                       m_activatedBox[RECT_W] + 2,
//@                       m_activatedBox[RECT_H] + 2);
//@        }
//@#endif
    }

    boolean CheckSkill(int skillID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        if (m_entitySkill.SearchObject(skillID) == null)
        {
            while (CGame.s_dbSkills[skillID][DB_SKILLS_NEXTLVNAME] >= 0)
            {
                skillID = CGame.s_dbSkills[skillID][DB_SKILLS_NEXTLVNAME];
                if (m_entitySkill.SearchObject(skillID) != null)
                {
                    return true;
                }
            }
            return false;
        }
        else
        {
            return true;
        }
    }

    void CalcSPIncrease()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\Common\CActor.jpp
        if (m_buff[DB_EFFECT_SET_ENUMERATE_SP_FORBID] == 0)
        {
            m_value[BV_SP] += (m_value[BV_DMG_VALUE] * m_value[BV_SPINCREASE] / m_value[BV_HPMAX]);
            m_value[BV_SP] = Math.min(m_value[BV_SP], m_value[BV_SPMAX]);
        }
    }
//@@..\CActor\CActor_Travelline.h
    void TravelLineClean()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Travelline.h
        m_travelLineCount = 0;
        m_travelLinePoint = 0;
    }

    void TravelLineAddData(byte dir, short distance)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Travelline.h
        short pX = 0, pY = 0;
        if (m_travelLineCount == 0)
        {
            pX = m_curX;
            pY = m_curY;
        }
        else
        {
            pX = m_travelLineDest[(m_travelLineCount - 1) * 2];
            pY = m_travelLineDest[(m_travelLineCount - 1) * 2 + 1];
        }

        if (m_travelLineCount < TRAVELLINT_DATA_CAPACITY)
        {
            switch (dir)
            {
            case DIRECTION_DOWN:
                pY += distance;
                break;
            case DIRECTION_LEFT:
                pX -= distance;
                break;
            case DIRECTION_UP:
                pY -= distance;
                break;
            case DIRECTION_RIGHT:
                pX += distance;
                break;
            }
            m_travelLineDest[m_travelLineCount * 2] = pX;
            m_travelLineDest[m_travelLineCount * 2 + 1] = pY;
            m_travelLineCount++;
        }
    }

    /**
     * 更新行动路线,如果到达终点,返回True,否则返回False
     *
     * @return boolean
     */
    boolean TravelLineUpdate()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Travelline.h
        int dirAction;
        if (m_travelLineEnd)
        {
            return true;
        }
        //计算坐标 更新坐标
        short destX = m_travelLineDest[m_travelLinePoint * 2];
        short destY = m_travelLineDest[m_travelLinePoint * 2 + 1];

        m_destX = destX;
        m_destY = destY;

        if (destX >= m_curX)
        {
            m_vX = ACTOR_STEP_LEN;
        }
        else
        {
            m_vX = -ACTOR_STEP_LEN;
        }

        if (destY >= m_curY)
        {
            m_vY = ACTOR_STEP_LEN;
        }
        else
        {
            m_vY = -ACTOR_STEP_LEN;
        }
        Move();

        //更新动画
        if (m_curX == destX && m_curY == destY)
        {
            m_travelLinePoint++;
            if (m_travelLinePoint >= m_travelLineCount)
            {
                //执行完毕,修改动作
                if (m_actionID >= ACTION_MOVE_UP && m_actionID <= ACTION_MOVE_LEFT)
                {
                    dirAction = m_actionID - ACTION_MOVE_UP;
                    SetAction(dirAction);
                    m_actionIDOld = dirAction;
                }
                return true;
            }
        }
        dirAction = CGame.UtilCalcDirection(m_curX, m_curY, destX, destY);
        dirAction += ACTION_MOVE_UP;
        if (m_actionID != dirAction)
        {
            if (m_properties[DEFINE_ACTOR_PROPERTY_PARA_TRANSPORT] == T_TRUE)
            {
                dirAction += ACTION_DEVIATION;
            }
            if (m_actionID >= ACTION_MOVE_UP && m_actionID <= ACTION_MOVE_LEFT)
            {
                SetAction(dirAction, m_frameID);
            }
            else
            {
                SetAction(dirAction);
            }
        }
        return false;
    }

    void TravelLineStart()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Travelline.h
        m_travelLinePoint = 0;
        m_travelLineEnd = false;
    }

//@@
//@@..\CActor\CActor_Chest.h
    int[] m_chest;
    int m_chestAmount = 0;

    final static int CHEST_INIT = 6;
    final static int CHEST_INC = 4;

    void AddChest(int id, int quantity)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Chest.h
        if (null == m_chest)
        {
            m_chest = new int[CHEST_INIT];
        }
        if (m_chest.length <= (m_chestAmount * 2))
        {
            int[] newArray = new int[m_chest.length + CHEST_INC];
            for (int i = 0; i < m_chest.length; i++)
            {
                newArray[i] = m_chest[i];
            }
            m_chest = null;
            m_chest = newArray;
        }
        m_chest[m_chestAmount * 2] = id;
        m_chest[m_chestAmount * 2 + 1] = quantity;
        m_chestAmount++;
    }

//@@
//@@..\CActor\CActor_Buff.h
    void CalcBuff()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Buff.h
        int effectID = -1;
        int effectValue = -1;

        for (int i = 0; i < AMOUNT_BUFF_EFFECT + AMOUNT_BUFF_ICON; i++)
        {
            m_buff[i] = 0;
        }
        //拆分效果
        for (int i = AMOUNT_BUFF_EFFECT + AMOUNT_BUFF_ICON; i < AMOUNT_DB_EFFECT_SET; i++)
        {
            if (m_buff[i] > 0)
            {
                for (int j = DB_EFFECT_SET_PURPOSE0; j <= DB_EFFECT_SET_PURPOSE_LAST; j += 2)
                {
                    effectID = CGame.s_dbEffectSet[i][j];
                    if (effectID > DB_EFFECT_SET_ENUMERATE_NONE)
                    {
                        m_buff[effectID] = CGame.s_dbEffectSet[i][j + 1];
                    }
                }
            }
        }
        //将百分比属性转换为绝对值
        for (int i = DB_EFFECT_SET_ENUMERATE_HP; i < AMOUNT_BUFF_EFFECT; i++)
        {
            if (m_buff[i] != 0)
            {
                switch (i)
                {
                case DB_EFFECT_SET_ENUMERATE_POISON:
                    effectValue = m_value[BV_HP] * m_buff[i] / 100;
                    break;
                case DB_EFFECT_SET_ENUMERATE_HPP:
                    effectValue = m_value[BV_HP] * m_buff[i] / 100;
                    break;
                case DB_EFFECT_SET_ENUMERATE_MPP:
                    effectValue = m_value[BV_MP] * m_buff[i] / 100;
                    break;
                case DB_EFFECT_SET_ENUMERATE_DEFENSE:
                    effectValue = m_value[BV_DP] * m_buff[i] / 100;
                    break;
                case DB_EFFECT_SET_ENUMERATE_NAP:
                    effectValue = m_value[BV_PHYDMG] * m_buff[i] / 100;
                    break;
                case DB_EFFECT_SET_ENUMERATE_SAP:
                    effectValue = m_value[BV_PSYDMG] * m_buff[i] / 100;
                    break;
                case DB_EFFECT_SET_ENUMERATE_SPEED:
                    effectValue = m_value[BV_SPEED] * m_buff[i] / 100;
                    break;
                default:
                    effectValue = m_buff[i];
                    break;
                }
                m_buff[i] = effectValue;
            }
        }
    }

    /**
     * 更新持续状态的时间.
     * 持续状态是一个或多个非持续效果的合集.
     * 在这个函数中,有复数效果的持续状态会计算到每个非持续效果的子项上.
     * 由此可见,这个过程应该在每个回合开始之前进行.
     * 并且,我们需要先减少持续状态的时间,再更新数值.
     */
    void UpdateBuff()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_Buff.h
        for (int i = DB_EFFECT_SET_ENUMERATE_BUF_POISON; i < AMOUNT_DB_EFFECT_SET; i++)
        {
            //减少buff类效果的时间
            if (m_buff[i] > 0)
            {
                m_buff[i]--;
            }
        }
        CalcBuff();
    }
//@@
//@@..\CActor\CActor_CoolDown.h
    void InitCoolDown()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_CoolDown.h
        CEntity entity = m_entitySkill.m_next;
        while (entity != null)
        {
            entity.m_coolDown = 0;
            entity = entity.m_next;
        }
    }

    void UpdateCoolDown()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CActor\CActor_CoolDown.h
        CEntity entity = m_entitySkill.m_next;
        while (entity != null)
        {
            if (entity.m_coolDown > 0)
            {
                entity.m_coolDown--;
            }
            entity = entity.m_next;
        }
    }
//@@
}
