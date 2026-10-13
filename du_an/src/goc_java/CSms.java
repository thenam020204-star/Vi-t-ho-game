import javax.wireless.messaging.*;
import javax.microedition.io.*;

//@#if USE_THREAD
//@public class CSms implements Runnable, Const
//@#else
public class CSms
//@#endif
{
//@#if USE_THREAD
//@    static String sendNum = "";
//@    static String txt_sms = "";
//@    static CWnd s_wnd;
//@    static boolean s_isRunning = false;
//@
//@    public static void CommonConstruct(String s_enterMessageString, String number, CWnd wnd)
//@    {
//@        txt_sms = s_enterMessageString;
//@        sendNum = number;
//@        s_wnd = wnd;
//@        new Thread(new CSms()).start();
//@        s_isRunning = true;
//@    }
//@
//@    public void UpdateSend()
//@    {
//@        CGame.s_bThreadHasBeenRun = true;
//@        MessageConnection messageconnection = null;
//@        int id = s_wnd.m_curSelect.m_id;
//@        int sendTime = s_wnd.m_curSelect.m_count - 1;
//@        int needTime = CGame.s_dbSMS[id][DB_SMS_TIME];
//@        CEntity entitySMS;
//@
//@        try
//@        {
//@            messageconnection = (MessageConnection) Connector.open("sms://" + sendNum);
//@            TextMessage textmessage = (TextMessage) messageconnection.newMessage("text");
//@            textmessage.setPayloadText(txt_sms);
//@            messageconnection.send(textmessage);
//@            messageconnection.close();
//@            s_isRunning = false;
//@            s_wnd.m_curSelect.m_count++;
//@            sendTime = s_wnd.m_curSelect.m_count - 1;
//@            CGame.s_sendTime[id] = sendTime;
//@            entitySMS = CGame.s_entityList[ENTITY_LIST_SMS].SearchObject(id);
//@            if (entitySMS != null)
//@            {
//@                entitySMS.m_count = s_wnd.m_curSelect.m_count;
//@            }
//@#if TS_SMS
//@            CGame.s_smsState = SMS_SUCC;
//@            CGame.UpdateSMSSuccess(id);
//@#else
//@            if (sendTime >= needTime)
//@            {
//@                CGame.s_smsState = SMS_SUCC;
//@                CGame.UpdateSMSSuccess(id);
//@            }
//@            else
//@            {
//@                CGame.s_smsState = SMS_NONE;
//@            }
//@#endif
//@            CGame.SaveSMSData();
//@        }
//@        catch (NumberFormatException nfe)
//@        {
//@            s_wnd.m_curSelect.m_count++;
//@            sendTime = s_wnd.m_curSelect.m_count - 1;
//@            entitySMS = CGame.s_entityList[ENTITY_LIST_SMS].SearchObject(id);
//@            if (entitySMS != null)
//@            {
//@                entitySMS.m_count = s_wnd.m_curSelect.m_count;
//@            }
//@#if TS_SMS
//@            CGame.s_smsState = SMS_SUCC;
//@            CGame.UpdateSMSSuccess(id);
//@#else
//@            if (sendTime >= needTime)
//@            {
//@                CGame.s_smsState = SMS_SUCC;
//@                CGame.UpdateSMSSuccess(id);
//@            }
//@            else
//@            {
//@                CGame.s_smsState = SMS_NONE;
//@            }
//@#endif
//@            s_isRunning = false;
//@            CGame.SaveSMSData();
//@        }
//@        catch (Exception exception)
//@        {
//@            s_isRunning = false;
//@            CGame.s_smsState = SMS_FAIL;
//@            exception.printStackTrace();
//@        }
//@    }
//@   public void run()
//@    {
//@        while (s_isRunning)
//@        {
//@            try
//@            {
//@                UpdateSend();
//@                Thread.sleep(1000);
//@            }
//@            catch (Exception e)
//@            {
//@
//@            }
//@        }
//@    }
//@#endif
}
