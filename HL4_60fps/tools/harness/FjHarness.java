import org.recompile.mobile.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/* Runs the real FreeJ2ME (GUI, under Xvfb) with the game, wraps the LCD painter to count
 * displayed frames, injects MIDP key codes from a script, logs fps per 2 s window. */
public class FjHarness {
    static volatile int frames;
    public static void main(String[] a) throws Exception {
        String jar = a[0]; List<String> script = Files.readAllLines(Paths.get(a[1]));
        PrintWriter log = new PrintWriter(new FileWriter(a[2]), true);
        new Thread(() -> { try { org.recompile.freej2me.FreeJ2ME.main(new String[]{jar, "240", "320"}); } catch (Throwable t) { t.printStackTrace(); } }, "fj").start();
        MobilePlatform p = null;
        for (int i = 0; i < 200 && (p = Mobile.getPlatform()) == null; i++) Thread.sleep(50);
        Thread.sleep(500);
        Thread.sleep(1500);
        p = Mobile.getPlatform();
        final Runnable orig = MobilePlatform.painter;
        log.println("painter=" + orig + " selected=" + MIDletLoader.MIDletSelected);
        Runnable wrap = () -> { frames++; if (orig != null) orig.run(); };
        MobilePlatform.painter = wrap;
        new Thread(() -> { while (true) { try { Thread.sleep(200); } catch (Exception e) {} if (MobilePlatform.painter != wrap) { log.println("painter replaced " + MobilePlatform.painter); } } }).start();
        long t0 = System.currentTimeMillis();
        for (String line : script) {
            String[] t = line.trim().split("\\s+");
            if (t[0].isEmpty() || t[0].startsWith("#")) continue;
            switch (t[0]) {
                case "wait": Thread.sleep(Long.parseLong(t[1])); break;
                case "press": MobilePlatform.keyPressed(Integer.parseInt(t[1])); Thread.sleep(t.length > 2 ? Long.parseLong(t[2]) : 80); MobilePlatform.keyReleased(Integer.parseInt(t[1])); break;
                case "fps": { int n = Integer.parseInt(t[2]); for (int i = 0; i < n; i++) { int f0 = frames; long s = System.currentTimeMillis(); Thread.sleep(2000); int f1 = frames; long e = System.currentTimeMillis();
                    log.printf("fps %s %.1f at %d ms%n", t[1], (f1 - f0) * 1000.0 / (e - s), e - t0); } break; }
                case "shot": { javax.imageio.ImageIO.write(Mobile.getPlatform().getLcdFrontbuffer().getCanvas(), "png", new File(t[1])); break; }
                case "probe": try {
                    java.lang.reflect.Method flc = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class); flc.setAccessible(true);
                    Class<?> c = (Class<?>) flc.invoke(Mobile.getPlatform().loader, "hl4.game.Interp");
                    Class<?> e = (Class<?>) flc.invoke(Mobile.getPlatform().loader, "hl4.game.Engine");
                    StringBuilder sb = new StringBuilder("probe");
                    for (String f : new String[]{"on", "sprAnim", "serial", "lgW", "camValid", "sink"}) { java.lang.reflect.Field fl = c.getDeclaredField(f); fl.setAccessible(true); sb.append(" ").append(f).append("=").append(fl.get(null)); }
                    java.lang.reflect.Field ln = c.getDeclaredField("lgN"); ln.setAccessible(true); sb.append(" lgN=").append(Arrays.toString((int[]) ln.get(null)));
                    java.lang.reflect.Field cur = e.getDeclaredField("cur"); sb.append(" cur=").append(cur.get(null)).append(" fd=").append(e.getDeclaredField("frameDelay").get(null));
                    log.println(sb); } catch (Throwable x) { log.println("probe failed " + x); x.printStackTrace(log); } break;
                case "label": log.println("label " + t[1] + " at " + (System.currentTimeMillis() - t0)); break;
            }
        }
        log.println("done"); log.close();
        Runtime.getRuntime().halt(0);
    }
}
