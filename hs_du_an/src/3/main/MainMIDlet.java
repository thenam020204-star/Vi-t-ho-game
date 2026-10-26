/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.midlet.MIDlet
 */
package main;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import main.a;

public class MainMIDlet
extends MIDlet {
    private static Display b = null;
    public static MainMIDlet a = null;

    public MainMIDlet() {
        a a2;
        b = Display.getDisplay((MIDlet)this);
        a = this;
        main.a.a = a2 = new a();
        this.getAppProperty("MIDlet-Version");
        b.setCurrent((Displayable)a2);
    }

    protected void destroyApp(boolean bl) {
    }

    protected void pauseApp() {
    }

    protected void startApp() {
    }
}

