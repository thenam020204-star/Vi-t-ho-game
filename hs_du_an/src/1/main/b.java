/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Canvas
 *  javax.microedition.lcdui.Graphics
 */
package main;

import g.i;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import main.MainMIDlet;
import main.c;

public final class b
extends Canvas
implements Runnable {
    public static b a = null;
    public static int b = 0;
    private boolean j = false;
    private int k = 0;
    private int l = 0;
    public static int c = 0;
    public static int d = 0;
    public static int e = 0;
    public static int f = 0;
    public static final int[][] g = new int[][]{{72, 80}, {71, 80}, null, {80, 95}};
    public static int h = 0;
    private Thread m = null;
    public c i = null;

    public b() {
        this.setFullScreenMode(true);
        c = 240;
        d = 320;
        e = c / 2;
        f = d / 2;
    }

    protected final void paint(Graphics graphics) {
        if (graphics.getFont() != h.b.a) {
            graphics.setFont(h.b.a);
        }
        this.i.a(graphics);
    }

    public final void run() {
        long l2 = 0L;
        long l3 = 0L;
        System.currentTimeMillis();
        while (!this.j) {
            Object object;
            l2 = System.currentTimeMillis();
            try {
                if (++h > 9999) {
                    h = 0;
                }
                object = this;
                int n2 = ((b)object).k;
                ((b)object).k = 0;
                ((b)object).i.a(n2, ((b)object).l);
                c c2 = ((b)object).i.d();
                if (c2 != null) {
                    ((b)object).i.b();
                    ((b)object).i = c2;
                    ((b)object).i.a();
                }
                System.currentTimeMillis();
                System.currentTimeMillis();
                this.repaint();
                this.serviceRepaints();
                System.currentTimeMillis();
            }
            catch (Exception exception) {
                object = exception;
                exception.printStackTrace();
            }
            l3 = System.currentTimeMillis() - l2;
            if (l3 < 33L) {
                h.b.a(33L - l3);
                continue;
            }
            h.b.a(2L);
        }
        MainMIDlet.a.notifyDestroyed();
    }

    protected final void keyPressed(int n2) {
        if (n2 == -6 || n2 == -7) {
            this.k = n2;
            this.l = n2;
            return;
        }
        int n3 = this.getGameAction(n2);
        switch (n3) {
            case 1: {
                n2 = 50;
                break;
            }
            case 6: {
                n2 = 56;
                break;
            }
            case 2: {
                n2 = 52;
                break;
            }
            case 5: {
                n2 = 54;
                break;
            }
            case 8: {
                n2 = 53;
            }
        }
        this.k = n2;
        this.l = n2;
    }

    protected final void keyReleased(int n2) {
        this.l = 0;
    }

    public final void a() {
        this.repaint();
        this.serviceRepaints();
    }

    public final void b() {
        this.j = true;
    }

    protected final void showNotify() {
        if (this.m == null) {
            this.i = new i();
            this.i.a();
            this.m = new Thread(this);
            this.m.start();
        }
    }
}

