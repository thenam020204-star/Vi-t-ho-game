import org.recompile.mobile.*;
import java.io.*;
import java.lang.reflect.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Harness {
    static final PrintStream LOG; static { PrintStream x = null; try { x = new PrintStream(new FileOutputStream(System.getProperty("hlog","h.log")), true); } catch (Exception e) {} LOG = x; }
    static MobilePlatform p; static volatile boolean allowExit = false;
    public static void main(String[] a) throws Exception {
        String jar = a[0]; int w = Integer.parseInt(a[1]), h = Integer.parseInt(a[2]);
        String out = a[4]; new File(out).mkdirs();
        LOG.println("start"); System.setProperty("file.encoding", "Shift_JIS"); Mobile.textEncoding = "Shift_JIS"; Runtime.getRuntime().addShutdownHook(new Thread(() -> { LOG.println("shutdown"); for (StackTraceElement[] st : Thread.getAllStackTraces().values()) {} })); System.setSecurityManager(new SecurityManager() {
            public void checkPermission(java.security.Permission perm) {}
            public void checkExit(int st) { if (!allowExit) { LOG.println("EXIT(" + st + ") called:"); new Throwable().printStackTrace(LOG); throw new SecurityException("exit blocked"); } }
        });
        p = new MobilePlatform(w, h); LOG.println("platform");
        Mobile.setPlatform(p, () -> {});
        p.setPainter(() -> {});
        if (!p.load(new File(jar).toURI().toURL().toString())) { LOG.println("LOAD FAILED"); System.exit(2); }
        Thread t = new Thread(() -> { try { p.runJar(); } catch (Throwable e) { e.printStackTrace(LOG); } }, "midlet-start");
        t.start();
        long t0 = System.currentTimeMillis();
        for (String line : java.nio.file.Files.readAllLines(new File(a[3]).toPath())) {
            line = line.trim(); if (line.isEmpty() || line.startsWith("#")) continue;
            String[] s = line.split("\\s+"); try {
            switch (s[0]) {
                case "wait": Thread.sleep(Long.parseLong(s[1])); break;
                case "press": { int k = Integer.parseInt(s[1]); long hold = s.length > 2 ? Long.parseLong(s[2]) : 120;
                    MobilePlatform.keyPressed(k); Thread.sleep(hold); MobilePlatform.keyReleased(k); Thread.sleep(150); break; }
                case "shot": shot(out + "/" + s[1] + ".png"); break;
                case "probe": probe(s[1], s[2], t0); break;
                case "rawdown": ((javax.microedition.lcdui.Canvas) Mobile.getDisplay().getCurrent()).keyPressed(Integer.parseInt(s[1])); break;
                case "rawup": ((javax.microedition.lcdui.Canvas) Mobile.getDisplay().getCurrent()).keyReleased(Integer.parseInt(s[1])); break;
                case "down": MobilePlatform.keyPressed(Integer.parseInt(s[1])); break;
                case "up": MobilePlatform.keyReleased(Integer.parseInt(s[1])); break;
            }
            } catch (Throwable e) { LOG.println("cmd error " + line + ": " + e); }
        }
        LOG.println("DONE");
        allowExit = true; System.exit(0);
    }
    static void shot(String f) throws Exception {
        PlatformImage pi = p.getLcdFrontbuffer();
        Object o = pi.getClass().getMethod("getCanvas").invoke(pi);
        BufferedImage src = (BufferedImage) o;
        BufferedImage img = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
        img.getGraphics().drawImage(src, 0, 0, null);
        ImageIO.write(img, "png", new File(f));
        LOG.println("shot " + f);
    }
    static void probe(String cls, String field, long t0) {
        try {
            Method flc = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class); flc.setAccessible(true);
            Class<?> c = (Class<?>) flc.invoke(p.loader, cls);
            if (c == null) { LOG.println("probe " + cls + " not loaded"); return; }
            Field f = null; for (Field x : c.getDeclaredFields()) if (x.getName().equals(field) && (f == null || x.getType() == int.class)) f = x; f.setAccessible(true);
            LOG.println("probe " + cls + "." + field + " = " + f.get(null) + " at " + (System.currentTimeMillis() - t0) + "ms");
        } catch (Throwable e) { LOG.println("probe err " + e); }
    }
}
