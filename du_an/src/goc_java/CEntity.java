/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class CEntity
        implements Const
{
    CEntity m_prev;
    CEntity m_next;

    byte m_type;
    int m_id; //唯一,且升序
    int m_count;
    int m_color;
    int m_icon;
    short m_coolDown;

    final static int ENTITY_POOL_SIZE = 1024;
    final static int ENTITY_POOL_SIZE_INC = 16;
    static CEntity s_pool[] = null;
    static int s_poolIdleID = 0;
//@#if DEBUG_STACKINFO
//@#if DEBUG_STACKINFO_CENTITY
//@    String m_debugStackInfo = "";
//@    Throwable m_debugStackInfoEx = null;
//@#endif
//@#endif

    private CEntity()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CEntity.jpp
        Init();
    }

    protected void finalize()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CEntity.jpp
        System.out.println("Fatal Error: CEntity Finalize in GC()!");
//@#if DEBUG_STACKINFO
//@#if DEBUG_STACKINFO_CENTITY
//@        System.out.println("Allocated at: ");
//@        System.out.println("---------");
//@        m_debugStackInfoEx.printStackTrace();
//@        System.out.println("---------");
//@#endif
//@#endif
//        Free();
//        Release();
    }

    void Free()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CEntity.jpp
        m_prev = null;
        m_next = null;
    }

//@@../CEntity/CEntity_Init.h
    void Init()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CEntity\CEntity_Init.h
        m_prev = null;
        m_next = null;
        m_type = 0;
        m_id = 0;
        m_count = 0;
        m_color = 0;
    }

//@@
//@@../CEntity/CEntity_Pool.h

    static void InitPool()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CEntity\CEntity_Pool.h
        if (s_pool == null)
        {
            s_pool = new CEntity[ENTITY_POOL_SIZE];
            s_poolIdleID = ENTITY_POOL_SIZE - 1;
            for (int i = 0; i < ENTITY_POOL_SIZE; i++)
            {
                s_pool[i] = new CEntity();
            }
        }
    }

    static CEntity GetInstance()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CEntity\CEntity_Pool.h
        CEntity res = s_pool[s_poolIdleID];
        s_poolIdleID--;
        res.Init();
//@#if DEBUG_STACKINFO
//@#if DEBUG_STACKINFO_CENTITY
//@        System.out.println("----CEntity-----");
//@        System.out.println("取出第" + s_poolIdleID + "个CEntity");
//@        res.m_debugStackInfo = CDebug.GetStackInfo();
//@        res.m_debugStackInfoEx = CDebug.GetThrowableStaceInfo();
//@        res.m_debugStackInfoEx.printStackTrace();
//@        System.out.println("---------------");
//@#endif
//@#endif
        return res;
    }

    void Release()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CEntity\CEntity_Pool.h
        s_poolIdleID++;
        s_pool[s_poolIdleID] = this;
//@#if DEBUG_STACKINFO
//@#if DEBUG_STACKINFO_CENTITY
//@        System.out.println("----CEntity-----");
//@        System.out.println("归还第" + s_poolIdleID + "个CEntity");
//@        m_debugStackInfoEx.printStackTrace();
//@        System.out.println("---------------");
//@#endif
//@#endif
    }

    void ReleaseAll()
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CEntity\CEntity_Pool.h
        CEntity entity = this.m_next;
        while (entity != null)
        {
            entity.Release();
            entity = entity.m_next;
        }
    }
//@@
//@@../CEntity/CEntity_Search.h
    int GetCount()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CEntity\CEntity_Search.h
        int res = 0;
        CEntity aEntity;
        aEntity = this;
        while (null != aEntity)
        {
            res++;
            aEntity = aEntity.m_next;
        }
        return res;
    }

    CEntity GetNext(int offset)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CEntity\CEntity_Search.h
        CEntity res = null;
        if (offset < 0)
        {
            return res;
        }
        res = this;
        for (int i = 0; i < offset; i++)
        {
            res = res.m_next;
        }
        return res;
    }

    CEntity GetLast()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CEntity\CEntity_Search.h
        CEntity res = this;
        while (res.m_next != null)
        {
            res = res.m_next;
        }
        return res;
    }

    int GetID(int id)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CEntity\CEntity_Search.h
        int count = GetCount();
        CEntity aEntity;
        aEntity = this;
        if (null != aEntity)
        {
            for (int i = 0; i < count; i++)
            {
                if (i == id)
                {
                    return aEntity.m_id;
                }
                aEntity = aEntity.m_next;
            }
        }
        return -1;
    }

    CEntity SearchArticalPrev(int id)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CEntity\CEntity_Search.h
        CEntity pEntity = this;
        while (null != pEntity)
        {
            if (null == pEntity.m_next)
            {
                return pEntity;
            }
            if (pEntity.m_next.m_id < id)
            {
                pEntity = pEntity.m_next;
            }
            else
            {
                return pEntity;
            }
        }
        return pEntity;

    }

    CEntity SearchObject(int id)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CEntity\CEntity_Search.h
        CEntity res = m_next;
        while (res != null)
        {
            if (res.m_id == id)
            {
                return res;
            }
            res = res.m_next;
        }
        return null;
    }

    /**
     * 查找节点,如有ID吻合的,则返回吻合的节点,否则返回之前的一个节点.
     *
     * @param targetID int
     * @return CEntity
     */
    CEntity Find(int targetID)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CEntity\CEntity_Search.h
        CEntity res = null;
        if (m_id == targetID)
        {
            return this;
        }
        else if (m_id > targetID)
        {
            return null;
        }
        else
        {
            res = this;
            while (res.m_next != null)
            {
                if (res.m_next.m_id > targetID)
                {
                    return res;
                }
                res = res.m_next;
            }
            return res;
        }
    }

//@@
//@@../CEntity/CEntity_Modify.h
    static void InsertAfter(CEntity headEntity, CEntity newEntity)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CEntity\CEntity_Modify.h
        if (null == headEntity)
        {
            headEntity = newEntity;
        }
        else
        {
            headEntity.InsertAfter(newEntity);
        }
    }

    void InsertAfter(CEntity newEntity)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CEntity\CEntity_Modify.h
        if (null != m_next)
        {
            m_next.m_prev = newEntity;
            newEntity.m_next = m_next;
        }
        m_next = newEntity;
        newEntity.m_prev = this;
    }

    static void Modify(CEntity headEntity, int id, int operator, int value, int color, int icon)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CEntity\CEntity_Modify.h
        CEntity aEntity0 = null, aEntity1 = null;

        if (headEntity == null)
        {
            return;
        }
        if (id < headEntity.m_id)
        {
            if (value > 0)
            {
                aEntity0 = GetInstance();
                aEntity0.m_id = id;
                aEntity0.m_count = value;
                aEntity0.m_color = color;
                aEntity0.m_icon = icon;
                aEntity0.InsertAfter(headEntity);
                headEntity = aEntity0;
            }
        }
        else
        {
            aEntity0 = headEntity.Find(id);
            if (aEntity0.m_id == id)
            {
                aEntity0.m_count = CGame.UtilCalc(aEntity0.m_count, operator, value);
                if (aEntity0.m_count <= 0)
                {
                    //如果小于等于0,去除这个节点
                    if (headEntity.equals(aEntity0))
                    {
                        headEntity = headEntity.m_next;
                    }
                    aEntity0.RemoveSelf();
                    aEntity0.Release();
                }
            }
            else
            {
                if (value > 0)
                {
                    aEntity1 = GetInstance();
                    aEntity1.m_id = id;
                    aEntity1.m_count = value;
                    aEntity1.m_color = color;
                    aEntity1.m_icon = icon;
                    aEntity0.InsertAfter(aEntity1);
                }
            }
        }
    }

    void RemoveSelf()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\CEntity\CEntity_Modify.h
        if (null != m_prev)
        {
            m_prev.m_next = m_next;
        }
        if (null != m_next)
        {
            m_next.m_prev = m_prev;
        }
        m_next = null;
        m_prev = null;
    }

    void Clone(CEntity entity)
    {//@in X:\RPG22_CMCC\trunk\SourceCode\CEntity\CEntity_Modify.h
        m_id = entity.m_id;
        m_type = entity.m_type;
        m_count = entity.m_count;
        m_color = entity.m_color;
        m_icon = entity.m_icon;
    }
//@@
}
