import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.*;

public class IMARPG22
        extends MIDlet implements Const
{

    static IMARPG22 s_midlet;
    static CGame s_game;
    public IMARPG22()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\IMARPG22.jpp
        s_midlet = this;
        s_game = new CGame();
    }

    public void startApp()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\IMARPG22.jpp
        Display.getDisplay(this).setCurrent(s_game);
        new Thread(s_game).start();
//@#if MOTO_INTERRUPT
//@        if (CGame.s_pauseRun)
//@        {
//@            s_game.showNotify();
//@        }
//@#endif
    }

    public void pauseApp()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\IMARPG22.jpp
        CGame.s_pauseRun = true;
        notifyPaused();
    }

    public void destroyApp(boolean unconditional)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\IMARPG22.jpp
        notifyDestroyed();
    }
}
