import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * Diem vao moi (MIDlet-1): hien logo "Viet hoa boi NAMP" ~2 giay roi chuyen sang game goc.
 * Khong sua class goc nao: chi override startApp() cua com.ea.sdk.SDKMIDlet.
 * Anh duoc giai phong (img = null) truoc khi vao game. Nhan phim bat ky de bo qua.
 */
public class NampMIDlet extends com.ea.sdk.SDKMIDlet {
    private boolean splashDone;

    protected void startApp() {
        if (!splashDone && Splash.cur == null) {
            Splash s = new Splash(this);
            Splash.cur = s;
            Display.getDisplay(this).setCurrent(s);
            new Thread(s).start();
            return;
        }
        if (splashDone) {
            super.startApp();
        }
    }

    final void endSplash() {
        synchronized (this) {
            if (splashDone) return;
            splashDone = true;
        }
        Splash.cur = null;
        super.startApp();
    }

    static final class Splash extends Canvas implements Runnable {
        static Splash cur;
        static volatile boolean skip;
        private final NampMIDlet m;
        private Image img;

        Splash(NampMIDlet m) {
            this.m = m;
            setFullScreenMode(true);
            try {
                img = Image.createImage("/namp.png");
            } catch (Exception e) {
                img = null;
            }
        }

        protected void paint(Graphics g) {
            g.setColor(0);
            g.fillRect(0, 0, getWidth(), getHeight());
            Image i = img;   // doc 1 lan: finish() co the gan null tu thread khac
            if (i != null) {
                g.drawImage(i, getWidth() >> 1, getHeight() >> 1, Graphics.HCENTER | Graphics.VCENTER);
            }
        }

        public void keyPressed(int k) {
            skip = true;
            finish();
        }

        public void run() {
            // ve lai dinh ky: mot so may/emulator chua san sang ve o lan repaint dau tien
            for (int i = 0; i < 20 && !Splash.skip; i++) {
                repaint();
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                }
            }
            finish();
        }

        private void finish() {
            img = null;
            m.endSplash();
        }
    }
}
