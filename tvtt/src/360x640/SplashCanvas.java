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
import src.Control;
import src.GameData;
import src.GameMidlet;
import src.GamePointer;

public class SplashCanvas
extends Canvas
implements Runnable,
GameData {
    Image transImg1 = null;
    Image transImg2 = null;
    GamePointer pointer;
    Image gamelogo;
    Image gamelogo1;
    GameMidlet theMidlet;
    Image soundask;
    Player play;
    public static int soundEffect = 0;
    public static boolean showLogoEffect = false;
    public static boolean showGameLogo = true;
    public static boolean showSoundAsk = true;
    private volatile Thread sendThread;
    int showgamelogocounter = 0;
    long lastTime = 0L;
    public static int sleeptime = 40;
    Image bg = null;
    Image[] logo;
    public static int index = 0;
    int[] widoff = new int[]{48, 119, 190};
    int[] heioff0 = new int[]{16, 14, 5, 1, -3, -2, 2};
    int[] heioff2 = new int[]{16, 12, 2, -8, -12};
    int[] heioff1 = new int[]{18, 15, -2, 0};
    int hei5 = 72;
    int[][] logoInfo = new int[][]{{2, this.heioff0[6], 0, this.heioff0[6], 1, this.heioff0[6], 500}, {2, this.heioff0[2], 0, this.heioff0[2], 1, this.heioff0[2], sleeptime}, {2, this.heioff0[1], 0, this.heioff0[1], 1, this.heioff0[1], sleeptime}, {2, this.heioff0[0], 0, this.heioff0[0], 1, this.heioff0[0], sleeptime, 2}, {-1, this.heioff1[0], -1, this.heioff1[0], -1, this.heioff1[0], sleeptime}, {-1, this.heioff1[0], 5, this.heioff1[0], -1, this.heioff1[0], sleeptime}, {-1, this.heioff1[0], 5, this.heioff1[1], -1, this.heioff1[0], sleeptime}, {-1, this.heioff1[0], 5, this.heioff1[2], -1, this.heioff1[0], sleeptime}, {-1, this.heioff1[0], 5, this.heioff1[3], -1, this.heioff1[0], sleeptime}, {-1, this.heioff1[0], 5, this.heioff1[0], -1, this.heioff1[0], sleeptime}, {-1, this.heioff1[0], 5, this.heioff1[1], 5, this.heioff1[0], sleeptime, 1}, {-1, this.heioff1[0], 5, this.heioff1[2], 5, this.heioff1[1], sleeptime}, {5, this.heioff1[0], 5, this.heioff1[3], 5, this.heioff1[2], sleeptime}, {5, this.heioff1[1], 5, this.heioff1[0], 5, this.heioff1[3], sleeptime}, {5, this.heioff1[2], 5, this.heioff1[1], 5, this.heioff1[0], sleeptime}, {5, this.heioff1[3], 5, this.heioff1[2], 5, this.heioff1[1], sleeptime}, {5, this.heioff1[0], 5, this.heioff1[3], 5, this.heioff1[2], sleeptime}, {5, this.heioff1[1], 5, this.heioff1[0], 5, this.heioff1[3], sleeptime}, {5, this.heioff1[2], 2, this.heioff0[0], 5, this.heioff1[0], sleeptime}, {5, this.heioff1[3], 2, this.heioff0[1], 5, this.heioff1[1], sleeptime}, {5, this.heioff1[0], 2, this.heioff0[2], 5, this.heioff1[2], sleeptime}, {5, this.heioff1[1], 2, this.heioff0[3], 5, this.heioff1[3], sleeptime}, {5, this.heioff1[2], 2, this.heioff0[4], 5, this.heioff1[0], sleeptime}, {5, this.heioff1[3], 2, this.heioff0[5], 5, this.heioff1[1], sleeptime}, {3, this.heioff0[0], 2, this.heioff0[6], 5, this.heioff1[2], sleeptime}, {3, this.heioff0[1], 2, this.heioff0[6], 5, this.heioff1[3], sleeptime}, {3, this.heioff0[2], 2, this.heioff0[6], 5, this.heioff1[0], sleeptime}, {3, this.heioff0[3], 2, this.heioff0[6], 5, this.heioff1[1], sleeptime}, {3, this.heioff0[4], 2, this.heioff0[6], 5, this.heioff1[2], sleeptime}, {3, this.heioff0[5], 2, this.heioff0[6], 5, this.heioff1[3], sleeptime}, {3, this.heioff0[6], 2, this.heioff0[6], 5, this.heioff1[0], sleeptime}, {3, this.heioff0[6], 2, this.heioff0[6], 5, this.heioff1[1], sleeptime}, {3, this.heioff0[6], 2, this.heioff0[6], 5, this.heioff1[2], sleeptime}, {3, this.heioff0[6], 2, this.heioff0[6], 5, this.heioff1[3], sleeptime}, {4, this.heioff0[0], 2, this.heioff0[6], 3, this.heioff0[6], sleeptime}, {4, this.heioff0[1], 2, this.heioff0[6], 3, this.heioff0[6], sleeptime}, {4, this.heioff0[2], 2, this.heioff0[6], 3, this.heioff0[6], sleeptime}, {4, this.heioff0[6], 2, this.heioff0[6], 3, this.heioff0[6], 1000}};

    public SplashCanvas(GameMidlet midlet) {
        this.setFullScreenMode(true);
        this.theMidlet = midlet;
        this.init();
        this.initSound();
        this.pointer = new GamePointer(this);
        GamePointer.pointerState = 0;
        this.sendThread = new Thread(this);
        this.sendThread.start();
    }

    public void init() {
        try {
            this.gamelogo = Image.createImage((String)"/logo/gameLogo.png");
            this.gamelogo1 = Image.createImage((String)"/logo/gameLogo1.png");
            this.soundask = Image.createImage((String)"/logo/soundAsk.png");
            this.bg = Image.createImage((String)"/logo/bg.png");
            this.logo = new Image[6];
            for (int i = 0; i < 6; ++i) {
                this.logo[i] = Image.createImage((String)("/logo/logo" + i + ".png"));
            }
            this.transImg1 = Image.createImage((String)"/logo/logo51.png");
            this.transImg2 = Image.createImage((String)"/logo/logo52.png");
        }
        catch (Exception e) {
            System.out.print("there is a error when init");
            e.printStackTrace();
        }
    }

    public void initSound() {
        try {
            this.play = Manager.createPlayer((InputStream)this.getClass().getResourceAsStream("/logo/sound1.mid"), (String)"audio/midi");
            this.play.realize();
            VolumeControl vc = (VolumeControl)this.play.getControl("VolumeControl");
            if (vc != null) {
                vc.setLevel(60);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void gameKeyProcess() {
        if (Control.KeyEnable) {
            Control.GetKey();
        } else {
            Control.KeyStateDestroy();
        }
        if (showSoundAsk) {
            if (Control.IsKeyDown(65536)) {
                soundEffect = 4;
                showLogoEffect = true;
                showSoundAsk = false;
                this.playSound();
            } else if (Control.IsKeyDown(131072)) {
                soundEffect = 0;
                showLogoEffect = true;
                showSoundAsk = false;
            }
        }
    }

    public synchronized void StopThread() {
        if (this.sendThread != null) {
            this.sendThread = null;
        }
    }

    public void run() {
        try {
            long waitTime;
            while (showGameLogo) {
                if (++this.showgamelogocounter == 30) {
                    showSoundAsk = true;
                    showGameLogo = false;
                }
                this.repaint();
                this.serviceRepaints();
                if (this.lastTime == 0L) {
                    this.lastTime = System.currentTimeMillis();
                    continue;
                }
                waitTime = System.currentTimeMillis() - this.lastTime;
                if (waitTime < (long)sleeptime) {
                    Thread.sleep((long)sleeptime - waitTime);
                }
                this.lastTime = System.currentTimeMillis();
            }
            while (showSoundAsk) {
                this.pointer.spointerProcces();
                this.gameKeyProcess();
                this.repaint();
                this.serviceRepaints();
                if (this.lastTime == 0L) {
                    this.lastTime = System.currentTimeMillis();
                    continue;
                }
                waitTime = System.currentTimeMillis() - this.lastTime;
                if (waitTime < (long)sleeptime) {
                    Thread.sleep((long)sleeptime - waitTime);
                }
                this.lastTime = System.currentTimeMillis();
            }
            while (showLogoEffect) {
                this.repaint();
                this.serviceRepaints();
                Thread.sleep(this.logoInfo[index][6]);
                ++index;
                if (this.lastTime == 0L) {
                    this.lastTime = System.currentTimeMillis();
                } else {
                    waitTime = System.currentTimeMillis() - this.lastTime;
                    if (waitTime < (long)sleeptime) {
                        Thread.sleep((long)sleeptime - waitTime);
                    }
                    this.lastTime = System.currentTimeMillis();
                }
                if (index < this.logoInfo.length) continue;
                showLogoEffect = false;
                this.closeSound();
                this.clear();
                this.StopThread();
                this.theMidlet.startGame((byte)1);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void playSound() {
        if (this.play != null) {
            try {
                this.play.start();
            }
            catch (MediaException mediaException) {
                // empty catch block
            }
        }
    }

    public void closeSound() {
        if (this.play != null) {
            this.play.close();
        }
    }

    public void clear() {
        this.gamelogo = null;
        this.gamelogo1 = null;
        this.bg = null;
        this.logo = null;
        this.transImg1 = null;
        this.transImg2 = null;
        this.soundask = null;
        this.play = null;
        this.logoInfo = null;
        this.widoff = null;
        this.heioff0 = null;
        this.heioff2 = null;
        this.heioff1 = null;
        System.gc();
    }

    public void paint(Graphics g) {
        g.setColor(0);
        g.fillRect(0, 0, 640, 360);
        g.setClip(0, 0, 640, 360);
        if (showGameLogo) {
            g.setColor(0);
            g.fillRect(0, 0, 640, 360);
            g.drawImage(this.gamelogo, 320, 118, 3);
            g.drawImage(this.gamelogo1, 320, 240, 3);
            return;
        }
        if (showSoundAsk) {
            g.setColor(0);
            g.fillRect(0, 0, 640, 360);
            g.drawImage(this.gamelogo, 320, 118, 3);
            g.drawImage(this.gamelogo1, 320, 240, 3);
            g.drawImage(this.soundask, 0, 360, 36);
            return;
        }
        if (showLogoEffect) {
            g.translate(140, 0);
            this.drawLogo(g);
            return;
        }
        g.setColor(0);
        g.fillRect(0, 0, g.getClipWidth(), g.getClipHeight());
    }

    public void drawLogo(Graphics g) {
        g.setClip(0, 0, 640, 360);
        g.setColor(0);
        g.fillRect(0, 0, 640, 360);
        g.drawImage(this.bg, 0, 0, 0);
        for (int i = 0; i < 3; ++i) {
            if (this.logoInfo[index][i * 2] == -1) continue;
            int www = this.widoff[i];
            if (this.logoInfo[index][i * 2] == 3) {
                www = this.widoff[0];
            } else if (this.logoInfo[index][i * 2] == 4) {
                www = this.widoff[2];
            }
            if (this.logoInfo[index][i * 2] == 5) {
                int[] jiaozhengx = new int[]{0, -1, -1};
                if (this.logoInfo[index][i * 2 + 1] == this.heioff1[0]) {
                    g.drawImage(this.transImg1, www + jiaozhengx[i] + 60, 128, 17);
                    continue;
                }
                if (this.logoInfo[index][i * 2 + 1] == this.heioff1[1]) {
                    g.drawImage(this.logo[this.logoInfo[index][i * 2]], www + 60, 158, 3);
                    continue;
                }
                if (this.logoInfo[index][i * 2 + 1] != this.heioff1[2]) continue;
                g.drawImage(this.transImg2, www + jiaozhengx[i] + 60, 127, 17);
                continue;
            }
            g.drawImage(this.logo[this.logoInfo[index][i * 2]], www + 60, this.logoInfo[index][i * 2 + 1] + 100 + 72, 3);
        }
    }

    public void pointerPressed(int x, int y) {
        if (!showSoundAsk) {
            return;
        }
        this.pointer.pointerPressed(x, y);
    }

    public void pointerReleased(int x, int y) {
        if (!showSoundAsk) {
            return;
        }
        this.pointer.pointerReleased(x, y);
    }
}

