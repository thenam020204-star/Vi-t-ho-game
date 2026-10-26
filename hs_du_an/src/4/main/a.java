/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Canvas
 *  javax.microedition.lcdui.Graphics
 */
package main;

import a.f;
import d.k;
import d.l;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import main.MainMIDlet;

public final class a
extends Canvas
implements Runnable {
    public static a a = null;
    public static int b = 0;
    private boolean l = false;
    private int m = 0;
    public int c = 0;
    private static Graphics n = null;
    public static int d = 0;
    public static int e = 0;
    public static int f = 0;
    public static int g = 0;
    public static int h = 0;
    public static int i = 0;
    public static final int[][] j = new int[][]{{72, 80}, {71, 80}, {67, 80}, {80, 95}};
    private static int o = 0;
    private Thread p = null;
    public k k = null;

    public a() {
        this.setFullScreenMode(true);
        d = 240;
        e = 320;
        f = d / 2;
        g = e / 2;
        h = d;
        i = e;
    }

    protected final void paint(Graphics graphics) {
        if (graphics.getFont() != b.e.a.a) {
            graphics.setFont(b.e.a.a);
        }
        n = graphics;
        this.k.a(n);
    }

    public final void run() {
        long l2 = 0L;
        long l3 = 0L;
        while (!this.l) {
            Object object;
            l2 = System.currentTimeMillis();
            try {
                if (++o > 9999) {
                    o = 0;
                }
                object = this;
                int n2 = ((a)object).m;
                ((a)object).m = 0;
                ((a)object).k.a(n2, ((a)object).c);
                k k2 = ((a)object).k.e();
                if (k2 != null) {
                    ((a)object).k.b();
                    ((a)object).k.i = null;
                    ((a)object).k = k2;
                    ((a)object).k.a();
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
                b.e.a.a(33L - l3);
                continue;
            }
            b.e.a.a(2L);
        }
        MainMIDlet.a.notifyDestroyed();
    }

    protected final void keyPressed(int n2) {
        if (n2 == -6 || n2 == -7) {
            this.m = n2;
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
        this.c = this.m = n2;
    }

    protected final void keyReleased(int n2) {
        this.c = 0;
    }

    public final void a() {
        this.repaint();
        this.serviceRepaints();
    }

    public final void b() {
        this.l = true;
    }

    protected final void showNotify() {
        if (this.p == null) {
            this.k = new l();
            this.k.a();
            this.p = new Thread(this);
            this.p.start();
        }
        a.f.i();
    }

    public final int c() {
        int n2 = this.m;
        this.m = 0;
        return n2;
    }
}

