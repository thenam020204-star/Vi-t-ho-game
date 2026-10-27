import java.lang.reflect.*;
import java.net.*;
import java.util.*;
/* differential test: original hl4.gfx.Fx.scaled (jar A) vs hl4.gfx.FastFx.scaled (jar B), real game images */
public class FxDiff {
    public static void main(String[] a) throws Exception {
        ClassLoader la = new URLClassLoader(new URL[]{new java.io.File(a[0]).toURI().toURL()}, null);
        ClassLoader lb = new URLClassLoader(new URL[]{new java.io.File(a[1]).toURI().toURL()}, null);
        Class<?> resA = la.loadClass("hl4.sys.Res"), gbmA = la.loadClass("hl4.gfx.Gbm"), frA = la.loadClass("hl4.gfx.Frame"), fxA = la.loadClass("hl4.gfx.Fx");
        Class<?> resB = lb.loadClass("hl4.sys.Res"), gbmB = lb.loadClass("hl4.gfx.Gbm"), frB = lb.loadClass("hl4.gfx.Frame"), ffB = lb.loadClass("hl4.gfx.FastFx");
        String[] packs = {"/H4/001/_HIMG_001_000", "/H4/001/_HIMG_001_004", "/H4/001/_HIMG_001_005", "/H4/001/_HIMG_001_007", "/CM/GRADE_BM", "/CM/_MMENU_BM", "/GMenu/_GMENU_ONLY_BM", "/l/_LOGO"};
        List<Object> ga = new ArrayList<>(), gb = new ArrayList<>();
        Method lmA = la.loadClass("hl4.gfx.Img").getMethod("loadAll", String.class, int.class), lmB = lb.loadClass("hl4.gfx.Img").getMethod("loadAll", String.class, int.class);
        for (String p : packs) {
            try { Object[] x = (Object[]) lmA.invoke(null, p, -1), y = (Object[]) lmB.invoke(null, p, -1);
                if (x == null) { System.out.println("missing " + p); continue; }
                for (int i = 0; i < x.length; i++) if (x[i] == null || y[i] == null) { x[i] = null; y[i] = null; }
                List<Object> xs = new ArrayList<>(Arrays.asList(x)), ys = new ArrayList<>(Arrays.asList(y)); xs.removeIf(Objects::isNull); ys.removeIf(Objects::isNull); x = xs.toArray(); y = ys.toArray(); ga.addAll(Arrays.asList(x)); gb.addAll(Arrays.asList(y)); }
            catch (InvocationTargetException e) { System.out.println("not merged gbm " + p + " " + e.getCause()); }
        }
        System.out.println("images " + ga.size()); { int t11 = 0, t12 = 0; for (Object g : ga) { if (gbmA.getField("type").getInt(g) == 11) t11++; else t12++; } System.out.println("type11=" + t11 + " type12=" + t12); }
        Method sa = fxA.getDeclaredMethod("scaled", frA, gbmA, int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, boolean.class, int.class, int.class);
        sa.setAccessible(true);
        Method sb = ffB.getMethod("scaled", frB, gbmB, int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, boolean.class, int.class, int.class);
        Constructor<?> ca = frA.getConstructor(int.class, int.class), cb = frB.getConstructor(int.class, int.class);
        Field pa = frA.getField("px"), pb = frB.getField("px");
        Method clA = frA.getMethod("setClip", int.class, int.class, int.class, int.class), clB = frB.getMethod("setClip", int.class, int.class, int.class, int.class);
        Field gw = gbmA.getField("w"), gh = gbmA.getField("h");
        Random r = new Random(1); int bad = 0, N = Integer.parseInt(a[2]);
        Object fA = ca.newInstance(240, 320), fB = cb.newInstance(240, 320);
        short[] A = (short[]) pa.get(fA), B = (short[]) pb.get(fB);
        for (int t = 0; t < N; t++) {
            int k = r.nextInt(ga.size()); Object ia = ga.get(k), ib = gb.get(k);
            int w = gw.getInt(ia), h = gh.getInt(ia);
            for (int i = 0; i < A.length; i++) { short v = (short) r.nextInt(65536); A[i] = v; B[i] = v; }
            int cx = r.nextInt(3) == 0 ? r.nextInt(120) - 20 : 0, cy = r.nextInt(3) == 0 ? r.nextInt(160) - 20 : 0;
            int cw = r.nextInt(3) == 0 ? 100 + r.nextInt(160) : 240, ch = r.nextInt(3) == 0 ? 120 + r.nextInt(220) : 320;
            clA.invoke(fA, cx, cy, cw, ch); clB.invoke(fB, cx, cy, cw, ch);
            int sw = 1 + r.nextInt(Math.max(1, w + 4)), shh = 1 + r.nextInt(Math.max(1, h + 4));
            Object[] args = {null, null, r.nextInt(320) - 40, r.nextInt(400) - 40, sw, shh, r.nextInt(6) - 2, r.nextInt(6) - 2, r.nextInt(8),
                1 + r.nextInt(400), 1 + r.nextInt(400), new int[]{0, 2, 4, 16, 18, 20, 32, 34, 36, 17, 33}[r.nextInt(11)], r.nextBoolean(),
                new int[]{0, 50, 100, 128, 200, 254, 255, 300}[r.nextInt(8)], r.nextInt(20) - 2};
            Object[] x = args.clone(); x[0] = fA; x[1] = ia; sa.invoke(null, x);
            Object[] y = args.clone(); y[0] = fB; y[1] = ib; sb.invoke(null, y);
            if (!Arrays.equals(A, B)) { if (bad++ < 5) System.out.println("MISMATCH " + Arrays.toString(Arrays.copyOfRange(args, 2, 15))); }
        }
        System.out.println("tests " + N + " mismatches " + bad);
    }
}
