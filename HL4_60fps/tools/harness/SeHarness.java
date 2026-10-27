import hl4.game.*;
import hl4.gfx.*;
import java.io.*;
import java.util.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/* Java SE harness: runs hl4.game.Engine ticks directly (no Canvas), captures the
 * tick frame and every interpolated (ghost) frame Interp hands to the Sink. */
public class SeHarness implements Interp.Sink {
    int slots = 3;
    List<short[]> ghosts = new ArrayList<>();
    public int slots() { return slots; }
    static long tickBase; static int curPeriod;
    // ghost i is rendered at the start of display slot i (see GameCanvas.show)
    static long tlate, tTickStart; static PrintWriter tlog;
    public boolean late(int n) { tlate = System.nanoTime(); hl4.sys.Clock.setVirtual(tickBase - curPeriod + (long) curPeriod * n / slots); return false; }
    public void show(Frame f, int n) { if (tlog != null) tlog.println("ghost " + tickNo + " " + n + " " + (System.nanoTime() - tlate) / 1000); ghosts.add(f.px.clone()); if (n == slots - 1) hl4.sys.Clock.setVirtual(tickBase); }

    static String out;
    static PrintWriter log;
    static short[] prevTick;
    static Map<String, int[]> stats = new LinkedHashMap<>();
    static int tickNo;
    static String label = "start";
    static boolean dumpAll;
    static PrintWriter hashLog;

    public static void main(String[] a) throws Exception {
        String script = a[0]; out = a[1];
        new File(out).mkdirs();
        log = new PrintWriter(new FileWriter(out + "/run.log"));
        hashLog = new PrintWriter(new FileWriter(out + "/hash.log"));
        if (System.getProperty("timing") != null) tlog = new PrintWriter(new FileWriter(out + "/timing.log"));
        hl4.gfx.Vi.enable();
        Hud.ktf = true;
        hl4.sys.Debug.strict = false;
        FakeServer.full = true;
        LogoFrame.skipNotices = true;
        MMenuStory.nebed = true;
        Interp.on = !"0".equals(System.getProperty("interp", "1"));
        SeHarness h = new SeHarness();
        Interp.sink = h;
        String sv = System.getProperty("save");
        if (sv != null) for (File f : new File(sv).listFiles()) if (f.getName().endsWith(".1.rsr")) {
            byte[] b = java.nio.file.Files.readAllBytes(f.toPath());
            byte[] d = java.util.Arrays.copyOfRange(b, 12, b.length);
            java.lang.reflect.Method m = hl4.sys.Store.class.getDeclaredMethod("store", String.class, byte[].class);
            m.setAccessible(true); m.invoke(null, "./" + f.getName().replace(".1.rsr", ""), d);
            log.println("loaded save " + f.getName() + " " + d.length);
        }
        Engine.startApp();
        for (String line : java.nio.file.Files.readAllLines(new File(script).toPath())) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;
            String[] t = line.split("\\s+");
            switch (t[0]) {
                case "label": label = t[1]; break;
                case "ticks": for (int i = 0, n = Integer.parseInt(t[1]); i < n; i++) h.tick(); break;
                case "press": {
                    int k = Integer.parseInt(t[1]); int hold = t.length > 2 ? Integer.parseInt(t[2]) : 1;
                    Engine.onKey(true, k); for (int i = 0; i < hold; i++) h.tick();
                    Engine.onKey(false, k); h.tick(); break; }
                case "down": Engine.onKey(true, Integer.parseInt(t[1])); break;
                case "up": Engine.onKey(false, Integer.parseInt(t[1])); break;
                case "shot": png(Gfx.scr.px, out + "/" + t[1] + ".png"); break;
                case "hold": { int k = Integer.parseInt(t[1]); Engine.onKey(true, k); for (int i = 0, n = Integer.parseInt(t[2]); i < n; i++) h.tick(); Engine.onKey(false, k); h.tick(); break; }
                case "skipevents": { int max = Integer.parseInt(t[1]), i = 0, quiet = 0;
                    while (i < max && quiet < 60) { if (G.event[510] != 0 || !(Engine.cur instanceof MainGameFrame)) { quiet = 0; int k = (i / 20) % 2 == 0 ? 35 : -5; Engine.onKey(true, k); h.tick(); Engine.onKey(false, k); h.tick(); for (int j = 0; j < 18; j++) h.tick(); i += 20; } else { quiet++; h.tick(); i++; } }
                    log.println("skipevents done tick " + tickNo + " event=" + G.event[510]); break; }
                case "efftest": {
                    int tested = 0, bad = 0;
                    for (int p : new int[]{2, 3, 4}) for (Obj o : Pool.pools[p]) {
                        if (!(o instanceof Char)) continue;
                        for (Obj e = ((Char) o).effects.head; e != null; e = e.next) {
                            byte[] m = e.m; byte keep = m[34];
                            for (int c = 0; c <= 11; c++) {
                                m[34] = (byte) c;
                                java.util.Arrays.fill(Gfx.scr.px, (short) 0); Effects.draw(e, 120, 160); short[] a1 = Gfx.scr.px.clone();
                                java.util.Arrays.fill(Gfx.scr.px, (short) 0); java.lang.reflect.Field fa = Interp.class.getField("alpha"); fa.setInt(null, 256);
                                Interp.drawEffect(e, 120, 160); short[] a2 = Gfx.scr.px.clone();
                                tested++; if (!java.util.Arrays.equals(a1, a2)) bad++;
                            }
                            m[34] = keep;
                        }
                    }
                    log.println("efftest tested=" + tested + " bad=" + bad); break; }
                case "dump": dumpAll = t[1].equals("on"); break;
                case "until": { // until <Frame simple class name> maxTicks
                    int max = Integer.parseInt(t[2]);
                    for (int i = 0; i < max && !Engine.cur.getClass().getSimpleName().equals(t[1]); i++) h.tick();
                    log.println("until " + t[1] + " -> " + Engine.cur.getClass().getSimpleName() + " tick " + tickNo); break; }
                default: throw new RuntimeException("bad line " + line);
            }
        }
        log.println("== summary: label frame ticks changed noGhost avgStuck% ==");
        for (Map.Entry<String, int[]> e : stats.entrySet()) {
            int[] s = e.getValue();
            log.printf("%s ticks=%d changed=%d noGhost=%d stuck=%.1f%% early=%.1f%% mid=%.1f%%%n", e.getKey(), s[0], s[1], s[2], s[1] == 0 ? 0.0 : s[3] / 10.0 / s[1], s[1] == 0 ? 0.0 : s[4] / 10.0 / s[1], s[1] == 0 ? 0.0 : 100 - (s[3] + s[4]) / 10.0 / s[1]);
        }
        log.close(); hashLog.close(); if (tlog != null) tlog.close();
        System.exit(0);
    }

    void tick() throws IOException {
        int period = Engine.frameDelay + 15;
        slots = Math.min(4, (period * 60 + 500) / 1000);
        ghosts.clear(); long tk0 = System.nanoTime();
        tickBase = 1000000L + (long) tickNo * period; curPeriod = period;
        hl4.sys.Clock.setVirtual(tickBase);
        Engine.tick();
        tickNo++;
        if (tlog != null) tlog.println("tick " + tickNo + " " + (System.nanoTime() - tk0) / 1000 + " " + Engine.cur.getClass().getSimpleName());
        short[] cur = Gfx.scr.px.clone();
        if (hashLog != null) { int hsh = java.util.Arrays.hashCode(cur); hashLog.println(tickNo + " " + Engine.cur.getClass().getSimpleName() + " " + Integer.toHexString(hsh) + " " + java.util.Arrays.hashCode(G.game) + " " + java.util.Arrays.hashCode(G.map) + " " + java.util.Arrays.hashCode(G.event) + " " + java.util.Arrays.hashCode(Pool.hero.m)); }
        String key = label + "/" + Engine.cur.getClass().getSimpleName() + "/g" + G.game[0];
        int[] s = stats.computeIfAbsent(key, k -> new int[5]);
        s[0]++;
        if (prevTick != null) {
            int diff = 0, x0 = 999, y0 = 999, x1 = -1, y1 = -1;
            for (int i = 0; i < cur.length; i++) if (cur[i] != prevTick[i]) { diff++; int x = i % 240, y = i / 240; if (x < x0) x0 = x; if (x > x1) x1 = x; if (y < y0) y0 = y; if (y > y1) y1 = y; }
            if (diff > 0) {
                s[1]++;
                if (ghosts.isEmpty()) { s[2]++; s[3] += 1000; }
                else {
                    short[] g = ghosts.get(0); int stuck = 0, early = 0;
                    for (int i = 0; i < cur.length; i++) if (cur[i] != prevTick[i]) { if (g[i] == prevTick[i]) stuck++; else if (g[i] == cur[i]) early++; }
                    s[3] += stuck * 1000 / diff; s[4] += early * 1000 / diff;
                }
                log.println("tick " + tickNo + " " + key + " diff=" + diff + " ghosts=" + ghosts.size() + " slots=" + slots + " box=" + x0 + "," + y0 + "-" + x1 + "," + y1);
                if (dumpAll || (tickNo >= Integer.getInteger("d0", -1) && tickNo <= Integer.getInteger("d1", -1))) {
                    png(prevTick, out + "/t" + tickNo + "_0.png");
                    for (int j = 0; j < ghosts.size(); j++) png(ghosts.get(j), out + "/t" + tickNo + "_g" + (j + 1) + ".png");
                    png(cur, out + "/t" + tickNo + "_9.png");
                }
            }
        }
        prevTick = cur;
        if (System.getProperty("herolog") != null) try {
            java.lang.reflect.Field fs = Interp.class.getDeclaredField("lgSpr"), fk = Interp.class.getDeclaredField("lgKey"), fn = Interp.class.getDeclaredField("lgN"), fw = Interp.class.getDeclaredField("lgW");
            for (java.lang.reflect.Field f : new java.lang.reflect.Field[]{fs, fk, fn, fw}) f.setAccessible(true);
            int w = fw.getInt(null) ^ 1; // buffer written by the real render just done is lgW (swap happens next tick) -> use lgW
            w = fw.getInt(null);
            Object[][] sp = (Object[][]) fs.get(null); int[][] k = (int[][]) fk.get(null); int n = ((int[]) fn.get(null))[w];
            StringBuilder sb = new StringBuilder("hspr " + tickNo);
            for (int i = 0; i < n; i++) if (sp[w][i] == Pool.hero.spr) sb.append(" a").append(k[w][i] >> 16 & 0xFF).append("/d").append(k[w][i] >> 8 & 0xFF).append("/f").append(k[w][i] & 0xFF);
            log.println(sb);
        } catch (Exception ex) { throw new RuntimeException(ex); }
        if (System.getProperty("camlog") != null) log.println("cam " + tickNo + " cx=" + hl4.sys.Bytes.s32(G.map, 156) + " cy=" + hl4.sys.Bytes.s32(G.map, 160) + " hx=" + hl4.sys.Bytes.s32(Pool.hero.m, 8) + " hy=" + hl4.sys.Bytes.s32(Pool.hero.m, 12) + " ox=" + hl4.sys.Bytes.s32(Pool.hero.m, 32) + " oy=" + hl4.sys.Bytes.s32(Pool.hero.m, 36));
        if (System.getProperty("heroanim") != null) log.println("hero tick " + tickNo + " anim=" + Pool.hero.m[359] + " frame=" + Pool.hero.m[140] + " st=" + Pool.hero.m[358] + " dir=" + Pool.hero.m[360] + " cls=" + G.gameMenu[22]);
        if (Particles.curCount > 0 && System.getProperty("pc") != null) log.println("particles tick " + tickNo + " n=" + Particles.curCount);
    }

    static void png(short[] px, String f) throws IOException {
        BufferedImage im = new BufferedImage(240, 320, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < px.length; i++) {
            int v = px[i] & 0xFFFF, r = v >> 11 & 31, g = v >> 5 & 63, b = v & 31;
            im.setRGB(i % 240, i / 240, (r << 3 | r >> 2) << 16 | (g << 2 | g >> 4) << 8 | (b << 3 | b >> 2));
        }
        ImageIO.write(im, "png", new File(f));
    }
}
