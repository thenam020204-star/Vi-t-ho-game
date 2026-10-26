/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Canvas
 *  javax.microedition.lcdui.Graphics
 */
package main;

import b.a;
import d.j;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import main.MainMIDlet;

public final class b
extends Canvas
implements Runnable {
    public static b a = null;
    public static int b = 0;
    private boolean j = false;
    private int k = 0;
    public int c = 0;
    public static int d = 0;
    public static int e = 0;
    public static int f = 0;
    public static int g = 0;
    public static final int[][] h = new int[][]{{72, 80}, {71, 80}, {67, 80}, {80, 95}};
    private static int l = 0;
    private Thread m = null;
    public d.a i = null;

    public b() {
        this.setFullScreenMode(true);
        d = 240;
        e = 320;
        f = d / 2;
        g = e / 2;
    }

    protected final void paint(Graphics graphics) {
        if (graphics.getFont() != e.a.b.a) {
            graphics.setFont(e.a.b.a);
        }
        this.i.a(graphics);
    }

    public final void run() {
        long l2 = 0L;
        long l3 = 0L;
        while (!this.j) {
            Object object;
            l2 = System.currentTimeMillis();
            try {
                if (++l > 9999) {
                    l = 0;
                }
                object = this;
                int n2 = ((b)object).k;
                ((b)object).k = 0;
                ((b)object).i.a(n2, ((b)object).c);
                d.a a2 = ((b)object).i.d();
                if (a2 != null) {
                    ((b)object).i.b();
                    ((b)object).i.b = null;
                    ((b)object).i = a2;
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
                e.a.b.a(33L - l3);
                continue;
            }
            e.a.b.a(2L);
        }
        MainMIDlet.a.notifyDestroyed();
    }

    protected final void keyPressed(int n2) {
        if (n2 == -6 || n2 == -7) {
            this.k = n2;
            this.c = n2;
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
        this.c = n2;
    }

    protected final void keyReleased(int n2) {
        this.c = 0;
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
            this.i = new j();
            this.i.a();
            this.m = new Thread(this);
            this.m.start();
        }
        b.a.i();
    }

    public final int c() {
        int n2 = this.k;
        this.k = 0;
        return n2;
    }
}

