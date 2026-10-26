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

    protected void destroyApp(boolean bl) {
    }

    protected void pauseApp() {
    }

    protected void startApp() {
        if (a == null) {
            b = Display.getDisplay((MIDlet)a2);
            a = a2;
            a a2 = new a();
            main.a.a = a2;
            a2.showNotify();
            b.setCurrent((Displayable)a2);
        }
    }
}

