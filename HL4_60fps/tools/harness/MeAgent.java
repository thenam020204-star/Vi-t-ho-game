import java.lang.instrument.*;
import java.security.ProtectionDomain;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/* Java agent for MicroEmulator 2.0.4: counts SwingDisplayComponent.repaintRequest calls
 * (one per MIDP repaint serviced = displayed frame), drives keys with java.awt.Robot from
 * a script, logs fps per 2 s window. Property: me.script, me.log */
public class MeAgent {
    public static volatile int n;
    public static void hit() { n++; }
    public static void premain(String args, Instrumentation inst) {
        Thread t = new Thread(MeAgent::drive, "me-driver"); t.setDaemon(true); t.start();
    }
    static boolean hook(java.awt.Component c) {
        if (c instanceof org.microemu.app.ui.swing.SwingDisplayComponent) {
            ((org.microemu.app.ui.swing.SwingDisplayComponent) c).addDisplayRepaintListener(img -> n++);
            return true;
        }
        if (c instanceof java.awt.Container) for (java.awt.Component k : ((java.awt.Container) c).getComponents()) if (hook(k)) return true;
        return false;
    }
    static void drive() {
        try {
            List<String> script = Files.readAllLines(Paths.get(System.getProperty("me.script")));
            PrintWriter log = new PrintWriter(new FileWriter(System.getProperty("me.log")), true);
            java.awt.Robot r = new java.awt.Robot();
            long t0 = System.currentTimeMillis();
            boolean ok = false;
            for (int i = 0; i < 100 && !ok; i++) { Thread.sleep(100); for (java.awt.Frame f : java.awt.Frame.getFrames()) if (hook(f)) { ok = true; break; } }
            log.println("hooked " + ok);
            for (String line : script) {
                String[] t = line.trim().split("\\s+");
                if (t[0].isEmpty() || t[0].startsWith("#")) continue;
                switch (t[0]) {
                    case "wait": Thread.sleep(Long.parseLong(t[1])); break;
                    case "press": { int k = java.awt.event.KeyEvent.VK_0 + (Integer.parseInt(t[1]) - 48); r.keyPress(k); Thread.sleep(200); r.keyRelease(k); break; }
                    case "fps": { for (int i = 0, m = Integer.parseInt(t[2]); i < m; i++) { int a = n; long s = System.currentTimeMillis(); Thread.sleep(2000); log.printf("fps %s %.1f at %d ms%n", t[1], (n - a) * 1000.0 / (System.currentTimeMillis() - s), System.currentTimeMillis() - t0); } break; }
                    case "shot": { java.awt.image.BufferedImage im = r.createScreenCapture(new java.awt.Rectangle(0, 0, 800, 600)); javax.imageio.ImageIO.write(im, "png", new File(t[1])); break; }
                    default: break;
                }
            }
            log.println("done"); log.close();
            Runtime.getRuntime().halt(0);
        } catch (Throwable e) { e.printStackTrace(); }
    }
}
