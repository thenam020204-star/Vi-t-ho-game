/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.midlet.MIDlet
 */
package main;

import b.a;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import main.b;

public class MainMIDlet
extends MIDlet {
    private static Display b = null;
    public static MainMIDlet a = null;

    public MainMIDlet() {
        b b2;
        b = Display.getDisplay((MIDlet)object);
        a = object;
        main.b.a = b2 = new b();
        Object object = object.getAppProperty("MIDlet-Version");
        if (object != null) {
            b.a.al = object;
        }
        b.setCurrent((Displayable)b2);
    }

    protected void destroyApp(boolean bl) {
    }

    protected void pauseApp() {
    }

    protected void startApp() {
    }
}

