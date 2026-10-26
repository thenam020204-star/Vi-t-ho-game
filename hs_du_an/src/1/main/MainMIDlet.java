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
import main.b;

public class MainMIDlet
extends MIDlet {
    private static Display b = null;
    public static MainMIDlet a = null;

    public MainMIDlet() {
        b = Display.getDisplay((MIDlet)object);
        a = object;
        Object object = new b();
        main.b.a = object;
        b.setCurrent((Displayable)object);
    }

    protected void destroyApp(boolean bl) {
    }

    protected void pauseApp() {
    }

    protected void startApp() {
    }
}

