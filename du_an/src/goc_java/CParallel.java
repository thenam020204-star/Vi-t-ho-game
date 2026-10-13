/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class CParallel
        implements Const
{
    int m_infoID;
    CParallel m_parent;
    CParallel[] m_children;
    int m_effectValue[];
    int m_childCount;

    int m_condition;
    int m_type;

    int m_attackerID;
    int m_actionType;
    int m_actionID;

    int m_step;

    int m_timer;
    int m_state;

    int[] m_objectID;
    int m_objectCount;
    int m_tag;


    private static CParallel s_pool[] = null;
    final static int CPARALLEL_POOL_SIZE = 32;
    static int s_poolIdleID = 0;

    static boolean s_isAllDone;
    static int s_nextRS;
//@#if DEBUG_STACKINFO
//@#if DEBUG_STACKINFO_CPARALLEL
//@    String m_debugStackInfo = "";
//@    Throwable m_debugStackInfoEx = null;
//@#endif
//@#endif

    private CParallel()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CParallel.jpp
//@#if not SUPPORT_MEMORY_RECOVER
//@        m_effectValue = new int[AMOUNT_DB_EFFECT_SET];
//@#endif
        m_children = new CParallel[8];
        m_objectID = new int[BATTLE_ACTOR_MAX];
        m_childCount = 0;
        m_tag = 0;
    }

    protected void finalize()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CParallel.jpp
        System.out.println("Fatal Error: CParallel Finalize in GC()!");
//@#if DEBUG_STACKINFO
//@#if DEBUG_STACKINFO_CPARALLEL
//@        System.out.println("Allocated at: ");
//@        System.out.println("---------");
//@        m_debugStackInfoEx.printStackTrace();
//@        System.out.println("---------");
//@#endif
//@#endif
        Release();
    }

    void Free()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\Common\CParallel.jpp
        m_childCount = 0;
        m_objectCount = 0;
        m_parent = null;
//@#if SUPPORT_MEMORY_RECOVER
        m_effectValue = null;
//@#endif
    }

    CParallel GetDirectLineDescent()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\Common\CParallel.jpp
        CParallel res = this;
        if (m_childCount <= 0)
        {
            return res;
        }
        res = m_children[0];
        while (res.m_childCount > 0)
        {
            res = res.m_children[0];
        }
        return res;
    }

//@@../CParallel/CParallel_Pool.h
    static void InitPool()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CParallel\CParallel_Pool.h
        if (s_pool == null)
        {
            s_pool = new CParallel[CPARALLEL_POOL_SIZE];
            s_poolIdleID = CPARALLEL_POOL_SIZE - 1;
            for (int i = 0; i < CPARALLEL_POOL_SIZE; i++)
            {
                s_pool[i] = new CParallel();
            }
        }
    }

    static CParallel GetInstance()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CParallel\CParallel_Pool.h
        CParallel res;
        res = s_pool[s_poolIdleID];
//@#if DEBUG_STACKINFO
//@#if DEBUG_STACKINFO_CPARALLEL
//@        System.out.println("-----CParallel-----");
//@        System.out.println("取出第" + s_poolIdleID + "个CParallel");
//@        res.m_debugStackInfo = CDebug.GetStackInfo();
//@        res.m_debugStackInfoEx = CDebug.GetThrowableStaceInfo();
//@        System.out.println("-------------------");
//@#endif
//@#endif
        s_poolIdleID--;
        CParallel.s_isAllDone = false;
        return res;
    }

    void Release()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CParallel\CParallel_Pool.h
        for (int i = 0; i < m_childCount; i++)
        {
            m_children[i].Release();
            m_children[i] = null;
        }
        Free();
        s_poolIdleID++;
        s_pool[s_poolIdleID] = this;
//@#if DEBUG_STACKINFO
//@#if DEBUG_STACKINFO_CPARALLEL
//@        System.out.println("-----CParallel-----");
//@        System.out.println("归还第" + s_poolIdleID + "个CParallel");
//@        m_debugStackInfoEx.printStackTrace();
//@        System.out.println("-------------------");
//@#endif
//@#endif
    }

//@@
//@@../CParallel/CParallel_Struct.h
    boolean IsAllChildrenEnd()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CParallel\CParallel_Struct.h
        boolean res = true;
        for (int i = 0; i < m_childCount; i++)
        {
            if (m_children[i].m_state != PARALLEL_STATE_END)
            {
                res = false;
                break;
            }
        }
        return res;
    }

    void AddChild(CParallel aParallel)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CParallel\CParallel_Struct.h
        aParallel.m_parent = this;
        m_children[m_childCount] = aParallel;
        m_childCount++;
    }
//@@
//@@../CParallel/CParallel_Update.h
    boolean Update()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CParallel\CParallel_Update.h
        boolean isEnd = false;
        switch (m_state)
        {
        case PARALLEL_STATE_WAIT:
            UpdateWait();
            break;
        case PARALLEL_STATE_RUN:
            UpdateRun();
            break;
        case PARALLEL_STATE_END:
            isEnd = true;
            break;
        case PARALLEL_STATE_START:
            UpdateStart();
            break;
        }
        for (int i = 0; i < m_childCount; i++)
        {
            isEnd &= m_children[i].Update();
        }
        if (!isEnd)
        {
            CParallel.s_isAllDone = false;
        }
        return isEnd;
    }

    private void UpdateWait()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CParallel\CParallel_Update.h
        switch (m_condition)
        {
        case PARALLEL_CONDITION_AUTO:
            m_state = PARALLEL_STATE_START;
            break;
        }
    }

    private void UpdateStart()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CParallel\CParallel_Update.h
        m_state = PARALLEL_STATE_RUN;
        switch (m_type)
        {
        case PARALLEL_TYPE_RUSH:
            CGame.RPRushStart(this);
            break;
        case PARALLEL_TYPE_FEEDBACKSET:
            CGame.RPFeedbackSetStart(this);
            break;
        case PARALLEL_TYPE_ATTACK_ANIM:
            CGame.RPAttackAnimStart(this);
            break;
        case PARALLEL_TYPE_EFFECT_ANIM:
            CGame.RPEffectAnimStart(this);
            break;
        case PARALLEL_TYPE_HURT_ANIM:
            CGame.RPHurtAnimStart(this);
            break;
        case PARALLEL_TYPE_ATTACK_SET:
            CGame.RPAttackSetStart(this);
            break;
        case PARALLEL_TYPE_SINGLE_ATTACK:
            CGame.RPSingleAttackStart(this);
            break;
        case PARALLEL_TYPE_DAMAGEVALUE:
            CGame.RPDamageValueStart(this);
            break;
        case PARALLEL_TYPE_ACTOR_RESET:
            CGame.RPActorRsetStart(this);
            break;
        case PARALLEL_TYPE_BUFFSET:
            CGame.RPBuffSetStart(this);
            break;
        case PARALLEL_TYPE_BUFF_ANIM:
            CGame.RPBuffAnimStart(this);
            break;
        case PARALLEL_TYPE_DIALOG:
            CGame.RPDialogStart(this);
            break;
        case PARALLEL_TYPE_UIANIM:
            CGame.RPUIAnimStart(this);
            break;
        case PARALLEL_TYPE_BATTLE_END:
            CGame.RPBattleEndStart(this);
            break;
        case PARALLEL_TYPE_POSE:
            CGame.RPPoseStart(this);
            break;
        case PARALLEL_TYPE_WINDOW:
            CGame.RPWindowStart(this);
            break;
        case PARALLEL_TYPE_SOUND:
            CGame.RPSoundStart(this);
            break;
        }
    }

    private void UpdateRun()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CParallel\CParallel_Update.h
        switch (m_type)
        {
        case PARALLEL_TYPE_RUSH:
            m_state = PARALLEL_STATE_END;
            break;
        case PARALLEL_TYPE_ATTACK_SET:
            switch (m_step)
            {
            case PARALLEL_STEP_ATTACKSET_ACT:
                if (IsAllChildrenEnd())
                {
                    CParallel parallel = m_children[1];
                    parallel.ReStart();
                    m_step = PARALLEL_STEP_ATTACKSET_BUFF;
                }
                break;
            case PARALLEL_STEP_ATTACKSET_BUFF:
                if (IsAllChildrenEnd())
                {
                    m_state = PARALLEL_STATE_END;
                }
                break;
            }
            break;
        case PARALLEL_TYPE_FEEDBACKSET:
            CGame.RPFeedbackSetRun(this);
            break;
        case PARALLEL_TYPE_ATTACK_ANIM:
            CGame.RPAttackAnimRun(this);
            break;
        case PARALLEL_TYPE_EFFECT_ANIM:
            CGame.RPEffectAnimRun(this);
            break;
        case PARALLEL_TYPE_HURT_ANIM:
            CGame.RPHurtAnimRun(this);
            break;
        case PARALLEL_TYPE_SINGLE_ATTACK:
            CGame.RPSingleAttackRun(this);
            break;
        case PARALLEL_TYPE_DAMAGEVALUE:
            CGame.RPDamageValueRun(this);
            break;
        case PARALLEL_TYPE_ACTOR_RESET:
            m_state = PARALLEL_STATE_END;
            break;
        case PARALLEL_TYPE_BUFFSET:
            CGame.RPBuffSetRun(this);
            break;
        case PARALLEL_TYPE_BUFF_ANIM:
            CGame.RPHurtAnimRun(this);
            break;
        case PARALLEL_TYPE_DIALOG:
            CGame.RPDialogRun(this);
            break;
        case PARALLEL_TYPE_UIANIM:
            CGame.RPUIAnimRun(this);
            break;
        case PARALLEL_TYPE_BATTLE_END:
            CGame.RPBattleEndRun(this);
            break;
        case PARALLEL_TYPE_POSE:
            CGame.RPPoseRun(this);
            break;
        case PARALLEL_TYPE_WINDOW:
            CGame.RPWindowRun(this);
            break;
        case PARALLEL_TYPE_SOUND:
            CGame.RPSoundRun(this);
            break;
        }
        m_timer++;
    }
//@@
//@@../CParallel/CParallel_Init.h
    void SetActionInfo(int attackerID, int actionType, int actionID, int aimID, int aimCount)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CParallel\CParallel_Init.h
        m_attackerID = attackerID;
        m_actionType = actionType;
        m_actionID = actionID;
        m_objectID[0] = aimID;
        m_objectCount = aimCount;
    }

    void Init(int type, int condition)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CParallel\CParallel_Init.h
        m_type = type;
        m_condition = condition;
        m_state = PARALLEL_STATE_WAIT;
        m_tag = 0;
        m_timer = 0;
        m_step = 0;
    }

    void InitEffectValue()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CParallel\CParallel_Init.h
//@#if SUPPORT_MEMORY_RECOVER
        m_effectValue = new int[AMOUNT_DB_EFFECT_SET];
//@#endif
    }

    void ReStart()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CParallel\CParallel_Init.h
        m_state = PARALLEL_STATE_START;
        m_timer = 0;
    }
//@@
//@@../CParallel/CParallel_Object.h
    void AddObject(int id)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CParallel\CParallel_Object.h
        m_objectID[m_objectCount] = id;
        m_objectCount++;
    }
//@@
}
