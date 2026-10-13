/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.midlet.MIDlet
 */
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

public class IMARPG22
extends MIDlet
implements i {
    static IMARPG22 a;
    private static c b;

    public IMARPG22() {
        a = this;
        b = new c();
    }

    public void startApp() {
        Display.getDisplay((MIDlet)this).setCurrent((Displayable)b);
        new Thread(b).start();
    }

    public void pauseApp() {
        c.f = true;
        this.notifyPaused();
    }

    public void destroyApp(boolean bl2) {
        this.notifyDestroyed();
    }
}

