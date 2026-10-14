/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.midlet.MIDlet
 */
package com.ea.sdk;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SDKMIDlet
extends MIDlet
implements Runnable {
    private static long b = 71;
    public static SDKMIDlet a;
    private static boolean b;
    private boolean c;
    private boolean d = false;
    private r a;
    public boolean a;
    private long c = false;
    public long a = null;
    private boolean e;

    public SDKMIDlet() {
        System.currentTimeMillis();
        this.a = false;
        this.c = 0L;
        this.a = 0L;
        this.e = false;
        a = this;
    }

    protected void destroyApp(boolean bl) {
        if (this.a != null && b) {
            b = false;
            this.d = true;
            while (!this.c) {
                Thread.yield();
            }
        }
        this.notifyDestroyed();
    }

    protected void pauseApp() {
        if (this.a != null) {
            this.b();
        }
    }

    protected void startApp() {
        if (this.a == null) {
            o.a(this);
            this.a = new r();
            Display.getDisplay((MIDlet)this).setCurrent((Displayable)this.a);
            this.a.setFullScreenMode(true);
            new Thread(this).start();
            return;
        }
        this.a();
    }

    public final void a() {
        if (this.a) {
            this.a += System.currentTimeMillis() - this.c;
            r.a(0);
            z.d();
            this.a = false;
        }
    }

    public final void b() {
        if (!this.a) {
            this.c = System.currentTimeMillis();
            if (this.a.a != null) {
                z.c();
            }
            this.a = true;
            p.a().a();
        }
    }

    public void run() {
        SDKMIDlet sDKMIDlet = this;
        if (!sDKMIDlet.e) {
            sDKMIDlet.e = true;
            sDKMIDlet.a.b();
            b = true;
            sDKMIDlet.c = false;
            sDKMIDlet.d = false;
        }
        while (b) {
            if (this.a) {
                try {
                    Thread.sleep(500L);
                }
                catch (Exception exception) {}
                continue;
            }
            long l2 = System.currentTimeMillis();
            long l3 = l2 + b;
            this.a.a();
            try {
                z.a(l2 - this.a);
            }
            catch (Exception exception) {}
            this.a.repaint();
            this.a.serviceRepaints();
            long l4 = l3 - System.currentTimeMillis();
            try {
                Thread.sleep(l4 < 1L ? 1L : l4);
            }
            catch (InterruptedException interruptedException) {}
        }
        z.e();
        this.c = true;
        if (!this.d) {
            this.destroyApp(false);
        }
    }

    public static void c() {
        b = false;
    }

    public static String a(String string) {
        return a.getAppProperty(string);
    }

    static {
        b = false;
    }
}

