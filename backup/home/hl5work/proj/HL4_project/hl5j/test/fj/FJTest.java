import org.recompile.mobile.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.*;

/** Drive FreeJ2ME headless: load jar, press scripted keys, dump LCD frames. */
public class FJTest {
    static int key(String k) {
        switch (k) {
            case "UP": return -1; case "DOWN": return -2; case "LEFT": return -3; case "RIGHT": return -4;
            case "OK": return -5; case "LSK": return -6; case "RSK": return -7; case "CLR": return -8;
            case "#": return 35; case "*": return 42; default: return k.charAt(0);
        }
    }
    public static void main(String[] a) throws Exception {
        String jar = a[0]; long total = Long.parseLong(a[1]); long every = Long.parseLong(a[2]); String out = a[3];
        new File(out).mkdirs();
        List<long[]> keys = new ArrayList<>();
        if (a.length > 4 && !a[4].isEmpty()) for (String s : a[4].split(",")) { String[] p = s.split(":"); keys.add(new long[]{Long.parseLong(p[0]), key(p[1])}); }
        Mobile.setPlatform(new MobilePlatform(240, 320));
        final long[] paints = {0};
        Mobile.getPlatform().setPainter(() -> paints[0]++);
        if (!Mobile.getPlatform().loadJar(new File(jar).toURI().toURL().toString())) throw new RuntimeException("load failed");
        Mobile.getPlatform().runJar();
        long t0 = System.currentTimeMillis();
        int ki = 0; long next = every;
        while (true) {
            long t = System.currentTimeMillis() - t0;
            if (t > total) break;
            while (ki < keys.size() && keys.get(ki)[0] <= t) {
                int k = (int) keys.get(ki)[1];
                try { Mobile.getPlatform().keyPressed(k); Thread.sleep(100); Mobile.getPlatform().keyReleased(k); } catch (Exception e) { System.out.println("key err " + e); }
                ki++;
            }
            if (t >= next) {
                BufferedImage img = Mobile.getPlatform().getLCD();
                ImageIO.write(img, "png", new File(String.format("%s/f%07d.png", out, next)));
                next += every;
            }
            Thread.sleep(20);
        }
        System.out.println("paints " + paints[0]);
        System.exit(0);
    }
}
