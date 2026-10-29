/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Canvas
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 *  javax.microedition.media.Manager
 *  javax.microedition.media.MediaException
 *  javax.microedition.media.Player
 *  javax.microedition.media.control.VolumeControl
 */
package src;

import java.io.InputStream;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;
import src.GameMidlet;
import src.k;
import src.x;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class q
extends Canvas
implements Runnable,
x {
    private Image a;
    private Image b;
    private Image c;
    private Image d;
    private GameMidlet a;
    private Image e;
    private Player a;
    public static int a = 0;
    private static boolean a = false;
    private static boolean b = true;
    private static boolean c = true;
    private volatile Thread a;
    private int b;
    private long a;
    private static int c = 40;
    private Image f = null;
    private Image[] a;
    private static int d = 0;
    private int[] a;
    private int[] b = 0;
    private int[] c;
    private int[][] a = new int[]{48, 119, 190};

    public q(GameMidlet object) {
        this.b = new int[]{16, 14, 5, 1, -3, -2, 2};
        int[] nArray = new int[]{16, 12, 2, -8, -12};
        this.c = new int[]{18, 15, -2, 0};
        this.a = new int[][]{{2, this.b[6], 0, this.b[6], 1, this.b[6], 500}, {2, this.b[2], 0, this.b[2], 1, this.b[2], c}, {2, this.b[1], 0, this.b[1], 1, this.b[1], c}, {2, this.b[0], 0, this.b[0], 1, this.b[0], c, 2}, {-1, this.c[0], -1, this.c[0], -1, this.c[0], c}, {-1, this.c[0], 5, this.c[0], -1, this.c[0], c}, {-1, this.c[0], 5, this.c[1], -1, this.c[0], c}, {-1, this.c[0], 5, this.c[2], -1, this.c[0], c}, {-1, this.c[0], 5, this.c[3], -1, this.c[0], c}, {-1, this.c[0], 5, this.c[0], -1, this.c[0], c}, {-1, this.c[0], 5, this.c[1], 5, this.c[0], c, 1}, {-1, this.c[0], 5, this.c[2], 5, this.c[1], c}, {5, this.c[0], 5, this.c[3], 5, this.c[2], c}, {5, this.c[1], 5, this.c[0], 5, this.c[3], c}, {5, this.c[2], 5, this.c[1], 5, this.c[0], c}, {5, this.c[3], 5, this.c[2], 5, this.c[1], c}, {5, this.c[0], 5, this.c[3], 5, this.c[2], c}, {5, this.c[1], 5, this.c[0], 5, this.c[3], c}, {5, this.c[2], 2, this.b[0], 5, this.c[0], c}, {5, this.c[3], 2, this.b[1], 5, this.c[1], c}, {5, this.c[0], 2, this.b[2], 5, this.c[2], c}, {5, this.c[1], 2, this.b[3], 5, this.c[3], c}, {5, this.c[2], 2, this.b[4], 5, this.c[0], c}, {5, this.c[3], 2, this.b[5], 5, this.c[1], c}, {3, this.b[0], 2, this.b[6], 5, this.c[2], c}, {3, this.b[1], 2, this.b[6], 5, this.c[3], c}, {3, this.b[2], 2, this.b[6], 5, this.c[0], c}, {3, this.b[3], 2, this.b[6], 5, this.c[1], c}, {3, this.b[4], 2, this.b[6], 5, this.c[2], c}, {3, this.b[5], 2, this.b[6], 5, this.c[3], c}, {3, this.b[6], 2, this.b[6], 5, this.c[0], c}, {3, this.b[6], 2, this.b[6], 5, this.c[1], c}, {3, this.b[6], 2, this.b[6], 5, this.c[2], c}, {3, this.b[6], 2, this.b[6], 5, this.c[3], c}, {4, this.b[0], 2, this.b[6], 3, this.b[6], c}, {4, this.b[1], 2, this.b[6], 3, this.b[6], c}, {4, this.b[2], 2, this.b[6], 3, this.b[6], c}, {4, this.b[6], 2, this.b[6], 3, this.b[6], 1000}};
        this.setFullScreenMode(true);
        this.a = object;
        this.a();
        object = this;
        try {
            ((q)object).a = Manager.createPlayer((InputStream)object.getClass().getResourceAsStream("/logo/sound1.mid"), (String)"audio/midi");
            ((q)object).a.realize();
            object = (VolumeControl)((q)object).a.getControl("VolumeControl");
            if (object != null) {
                object.setLevel(60);
            }
        }
        catch (Exception exception) {}
        this.a = new Thread(this);
        this.a.start();
    }

    private void a() {
        try {
            this.c = Image.createImage((String)"/logo/gameLogo.png");
            this.d = Image.createImage((String)"/logo/gameLogo1.png");
            this.e = Image.createImage((String)"/logo/soundAsk.png");
            this.f = Image.createImage((String)"/logo/bg.png");
            this.a = new Image[6];
            for (int i2 = 0; i2 < 6; ++i2) {
                this.a[i2] = Image.createImage((String)("/logo/logo" + i2 + ".png"));
            }
            this.a = Image.createImage((String)"/logo/logo51.png");
            this.b = Image.createImage((String)"/logo/logo52.png");
            return;
        }
        catch (Exception exception) {
            System.out.print("there is a error when init");
            exception.printStackTrace();
            return;
        }
    }

    private synchronized void b() {
        if (this.a != null) {
            this.a = null;
        }
    }

    public final void run() {
        try {
            while (b) {
                if (++this.b == 30) {
                    c = true;
                    b = false;
                }
                this.repaint();
                this.serviceRepaints();
                if (this.a == 0L) {
                    this.a = System.currentTimeMillis();
                    continue;
                }
                long l2 = System.currentTimeMillis() - this.a;
                if (l2 < (long)c) {
                    Thread.sleep((long)c - l2);
                }
                this.a = System.currentTimeMillis();
            }
            while (c) {
                q q2 = this;
                if (k.a) {
                    k.b();
                } else {
                    k.a();
                }
                if (c) {
                    if (k.a(65536)) {
                        a = 4;
                        a = true;
                        c = false;
                        if (q2.a != null) {
                            try {
                                q2.a.start();
                            }
                            catch (MediaException mediaException) {}
                        }
                    } else if (k.a(131072)) {
                        a = 0;
                        a = true;
                        c = false;
                    }
                }
                this.repaint();
                this.serviceRepaints();
                if (this.a == 0L) {
                    this.a = System.currentTimeMillis();
                    continue;
                }
                long l3 = System.currentTimeMillis() - this.a;
                if (l3 < (long)c) {
                    Thread.sleep((long)c - l3);
                }
                this.a = System.currentTimeMillis();
            }
            while (a) {
                long l4;
                this.repaint();
                this.serviceRepaints();
                Thread.sleep(this.a[d][6]);
                ++d;
                if (this.a != 0L && (l4 = System.currentTimeMillis() - this.a) < (long)c) {
                    Thread.sleep((long)c - l4);
                }
                this.a = System.currentTimeMillis();
                if (d < this.a.length) continue;
                a = false;
                q q3 = this;
                if (q3.a != null) {
                    q3.a.close();
                }
                q3 = this;
                this.c = null;
                q3.d = null;
                q3.f = null;
                q3.a = null;
                q3.a = null;
                q3.b = null;
                q3.e = null;
                q3.a = null;
                q3.a = null;
                q3.a = null;
                q3.b = null;
                q3.c = null;
                System.gc();
                this.b();
                this.a.startGame((byte)1);
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void paint(Graphics object) {
        object.setClip(0, 0, 240, 320);
        if (b) {
            object.setColor(0);
            object.fillRect(0, 0, 240, 320);
            object.drawImage(this.c, 120, 98, 3);
            object.drawImage(this.d, 120, 220, 3);
            return;
        }
        if (c) {
            object.setColor(0);
            object.fillRect(0, 0, 240, 320);
            object.drawImage(this.c, 120, 98, 3);
            object.drawImage(this.d, 120, 220, 3);
            object.drawImage(this.e, 0, 320, 36);
            return;
        }
        if (a) {
            Graphics graphics = object;
            object = this;
            graphics.setClip(0, 0, 240, 320);
            graphics.setColor(0);
            graphics.fillRect(0, 0, 240, 320);
            graphics.drawImage(object.f, 0, 0, 0);
            for (int i2 = 0; i2 < 3; ++i2) {
                if (object.a[d][i2 << 1] == -1) continue;
                int n2 = object.a[i2];
                if (object.a[d][i2 << 1] == 3) {
                    n2 = object.a[0];
                } else if (object.a[d][i2 << 1] == 4) {
                    n2 = object.a[2];
                }
                if (object.a[d][i2 << 1] == 5) {
                    int[] nArray = new int[]{0, -1, -1};
                    if (object.a[d][(i2 << 1) + 1] == object.c[0]) {
                        graphics.drawImage(object.a, n2 + nArray[i2], 111, 17);
                        continue;
                    }
                    if (object.a[d][(i2 << 1) + 1] == object.c[1]) {
                        graphics.drawImage(object.a[object.a[d][i2 << 1]], n2, 158, 3);
                        continue;
                    }
                    if (object.a[d][(i2 << 1) + 1] != object.c[2]) continue;
                    graphics.drawImage(object.b, n2 + nArray[i2], 111, 17);
                    continue;
                }
                graphics.drawImage(object.a[object.a[d][i2 << 1]], n2, object.a[d][(i2 << 1) + 1] + 100 + 52, 3);
            }
            return;
        }
        object.setColor(0);
        object.fillRect(0, 0, object.getClipWidth(), object.getClipHeight());
    }

    protected final void keyPressed(int n2) {
        k.a(n2);
    }

    protected final void keyReleased(int n2) {
        k.b(n2);
    }
}

