import java.io.*;
import java.util.*;

/**
 * Debug (harness only, HL4_OVF=1|base, interpreter): finds text that does not fit its box (HL5).
 * For every call of the string drawer 0x7540c: snapshot the screen before, collect the pixels the text
 * draws (0x444c calls; colour read back after the call). Checks:
 *  - clip: pixels clipped / off screen;
 *  - border: a background line (vertical/horizontal edge in the snapshot) crosses the text;
 *  - cover: at the next frame present, part of the text was overdrawn by something drawn later
 *    (value boxes, buttons...). The smallest cover ratio over all frames is kept.
 * HL4_OVF=base writes a baseline (Korean run) that the Vietnamese run subtracts (HL4_OVFBASE=file[,file]):
 * borders inside the Korean extent and cover ratios the Korean text also had are ignored.
 * Output (at exit): out/ovf.txt, out/draws.txt (every draw: key, room, extent, clipped, cover, time, text hex), out/ovf/NNNN.png.
 */
class OvfDetect {
    static OvfDetect inst;
    static final int DRAW = 0x7540c, PIX = 0x444c;
    final hl4.cpu.Memory m;
    final PrintWriter w;
    final File dir;
    final RunHeadless h;
    final boolean baseMode;
    int depth, retAddr, retSp, site;
    int[] snap;
    int sw, sh, buf, x, y, align, len, str, maxw;
    int minx, miny, maxx, maxy, clipped, npix;
    int cx0, cy0, cx1, cy1;
    int[] pp = new int[4096];

    /** statistics per draw (key|hex) */
    static class Rec {
        String key, hex;
        int minx, miny, maxx, maxy, clipped, n;
        TreeSet<String> borders = new TreeSet<>();
        double cover = 2; // min over frames
        int room0, room1;
        long t;
        byte[] crop; int cw, chh;
        int[] pos, col;
        int cminx = 9999, cmaxx = -9999; // covered pixel columns (at the best frame)
        double fcover;
        TreeSet<String> ovl = new TreeSet<>(); // keys of other texts whose box overlaps this one (same frame)
    }
    final Map<String, Rec> recs = new LinkedHashMap<>();
    final List<Rec> pending = new ArrayList<>();
    final Map<String, String[]> base = new HashMap<>();

    OvfDetect(RunHeadless h, hl4.cpu.Memory m, String out) throws IOException {
        inst = this;
        this.h = h;
        this.m = m;
        this.w = new PrintWriter(new OutputStreamWriter(new FileOutputStream(out + "/ovf.txt"), "UTF-8"));
        this.dir = new File(out, "ovf");
        dir.mkdirs();
        baseMode = "base".equals(System.getenv("HL4_OVF"));
        String bf = System.getenv("HL4_OVFBASE");
        if (bf != null) for (String bfile : bf.split(",")) {
            BufferedReader r = new BufferedReader(new FileReader(bfile));
            for (String l; (l = r.readLine()) != null; ) {
                String[] c = l.split("\t", -1);
                if (c.length < 5) continue;
                String[] old = base.get(c[0]);
                String ov = c.length > 5 ? c[5] : "";
                if (old == null) base.put(c[0], new String[]{c[1], c[2], c[3], c[4], ov});
                else {
                    old[4] = old[4] + " " + ov;
                    old[0] = old[0] + " " + c[1];
                    old[1] = String.valueOf(Math.max(Integer.parseInt(old[1]), Integer.parseInt(c[2])));
                    old[2] = union(old[2], c[3]);
                    old[3] = String.valueOf(Math.max(Double.parseDouble(old[3]), Double.parseDouble(c[4])));
                }
            }
            r.close();
        }
        java.lang.Runtime.getRuntime().addShutdownHook(new Thread(this::report));
    }

    static String union(String a, String b) {
        String[] p = a.split(","), q = b.split(",");
        return Math.min(Integer.parseInt(p[0]), Integer.parseInt(q[0])) + "," + Math.min(Integer.parseInt(p[1]), Integer.parseInt(q[1])) + ","
                + Math.max(Integer.parseInt(p[2]), Integer.parseInt(q[2])) + "," + Math.max(Integer.parseInt(p[3]), Integer.parseInt(q[3]));
    }

    int lit(int a) { return m.read32(m.read32(a)); }

    void step(hl4.cpu.Cpu cpu, int addr, int op, boolean thumb) {
        if (!thumb) return;
        if (depth > 0 && addr == retAddr && cpu.r[13] == retSp) { finish(); depth = 0; return; }
        if ((op >>> 11) != 31) return;
        int target = (cpu.r[14] + ((op & 0x7FF) << 1)) & ~1;
        if (target == DRAW && depth == 0) start(cpu, addr);
        else if (target == PIX && depth > 0) pix(cpu.r[0], cpu.r[1]);
    }

    void start(hl4.cpu.Cpu cpu, int addr) {
        int sp = cpu.r[13];
        sw = lit(0x4544); sh = lit(0x4548); buf = lit(0x4554);
        if (sw <= 0 || sh <= 0 || sw > 1024 || sh > 1024) return;
        depth = 1;
        retAddr = addr + 2;
        retSp = sp;
        site = addr;
        x = cpu.r[2]; y = cpu.r[3];
        maxw = m.read32(sp); str = m.read32(sp + 4); len = m.read32(sp + 16); align = m.read32(sp + 20);
        int c = m.read32(0x454c);
        cx0 = m.read32(c); cy0 = m.read32(c + 4); cx1 = m.read32(c + 8); cy1 = m.read32(c + 12);
        if (snap == null || snap.length != sw * sh) snap = new int[sw * sh];
        for (int i = 0; i < snap.length; i++) snap[i] = m.read16(buf + 2 * i);
        minx = miny = Integer.MAX_VALUE; maxx = maxy = Integer.MIN_VALUE; clipped = 0; npix = 0;
    }

    void pix(int px, int py) {
        if (px < minx) minx = px; if (px > maxx) maxx = px;
        if (py < miny) miny = py; if (py > maxy) maxy = py;
        if (px < 0 || py < 0 || px >= sw || py >= sh || px < cx0 || py < cy0 || px >= cx1 || py >= cy1) { clipped++; return; }
        if (npix >= pp.length) pp = Arrays.copyOf(pp, pp.length * 2);
        pp[npix++] = py * sw + px;
    }

    static int diff(int a, int b) {
        int dr = Math.abs(((a >> 11) & 31) - ((b >> 11) & 31)) * 8;
        int dg = Math.abs(((a >> 5) & 63) - ((b >> 5) & 63)) * 4;
        int db = Math.abs((a & 31) - (b & 31)) * 8;
        return dr + dg + db;
    }

    int at(int px, int py) {
        if (px < 0) px = 0; if (py < 0) py = 0; if (px >= sw) px = sw - 1; if (py >= sh) py = sh - 1;
        return snap[py * sw + px];
    }

    boolean vline(int c, int yy) {
        int k = 0;
        for (int r = yy; r < yy + 11; r++) if (diff(at(c, r), at(c - 1, r)) > 90) k++;
        return k >= 9;
    }

    void finish() {
        if (minx == Integer.MAX_VALUE) return;
        byte[] s = new byte[Math.max(0, Math.min(len, 600))];
        for (int i = 0; i < s.length; i++) s[i] = (byte) m.read8(str + i);
        String hex = hex(s);
        String key = Integer.toHexString(site) + ":" + x + ":" + y + ":" + align;
        Rec r = recs.get(key + "|" + hex);
        if (r == null) {
            r = new Rec();
            r.key = key; r.hex = hex; r.t = h.currentTimeMillis() - h.vbase;
            r.minx = minx; r.miny = miny; r.maxx = maxx; r.maxy = maxy;
            int y0 = Math.max(miny, 0), y1 = Math.min(maxy, sh - 1), x0 = Math.max(minx, 0), x1 = Math.min(maxx, sw - 1);
            int rows = y1 - y0 + 1;
            if (rows >= 5) for (int c = x0 + 1; c <= x1; c++) {
                int k = 0;
                for (int yy = y0; yy <= y1; yy++) if (diff(at(c, yy), at(c - 1, yy)) > 90) k++;
                if (k >= rows * 9 / 10) r.borders.add("v" + c);
            }
            int cols = x1 - x0 + 1;
            if (cols >= 12) for (int rr = y0 + 1; rr <= y1; rr++) {
                int k = 0;
                for (int xx = x0; xx <= x1; xx++) if (diff(at(xx, rr), at(xx, rr - 1)) > 90) k++;
                if (k >= cols * 9 / 10) r.borders.add("h" + rr);
            }
            // free room on the first line around the anchor
            int a = align == 2 ? x - 1 : x;
            int yy = Math.max(0, Math.min(y + 1, sh - 11));
            int L = Math.min(Math.max(a, 1), sw - 1), R = Math.min(Math.max(a + 1, 1), sw);
            while (L > 0 && !vline(L, yy)) L--;
            while (R < sw && !vline(R, yy)) R++;
            r.room0 = L; r.room1 = R;
            recs.put(key + "|" + hex, r);
        }
        r.clipped = Math.max(r.clipped, clipped);
        r.n++;
        // pixels + colours after drawing, checked at the next present
        int n = Math.min(npix, 4000);
        r.pos = new int[n]; r.col = new int[n];
        for (int i = 0; i < n; i++) { r.pos[i] = pp[i]; r.col[i] = m.read16(buf + 2 * pp[i]); }
        pending.add(r);
    }

    /** called by the harness when a frame is shown */
    void present() {
        if (pending.isEmpty()) return;
        for (Rec r : pending) {
            if (r.pos == null || r.pos.length == 0) continue;
            int k = 0, c0 = 9999, c1 = -9999;
            for (int i = 0; i < r.pos.length; i++) if (m.read16(buf + 2 * r.pos[i]) != r.col[i]) {
                k++;
                int px = r.pos[i] % sw;
                if (px < c0) c0 = px; if (px > c1) c1 = px;
            }
            double c = k / (double) r.pos.length;
            r.fcover = c;
            if (c < r.cover) {
                r.cover = c;
                r.cminx = c0; r.cmaxx = c1;
                if (!baseMode) grab(r);
            }
        }
        // text boxes overlapping each other in this frame (>= 2 px both ways); texts hidden under a popup
        // (mostly overdrawn in this frame) do not count
        for (int i = 0; i < pending.size(); i++) for (int j = i + 1; j < pending.size(); j++) {
            Rec a = pending.get(i), b = pending.get(j);
            if (a.key.equals(b.key) || a.fcover > 0.3 || b.fcover > 0.3) continue;
            int ox = Math.min(a.maxx, b.maxx) - Math.max(a.minx, b.minx), oy = Math.min(a.maxy, b.maxy) - Math.max(a.miny, b.miny);
            if (ox >= 2 && oy >= 2) { a.ovl.add(b.key); b.ovl.add(a.key); }
        }
        pending.clear();
    }

    void grab(Rec r) {
        int x0 = Math.max(0, r.minx - 24), y0 = Math.max(0, r.miny - 16), x1 = Math.min(sw, r.maxx + 25), y1 = Math.min(sh, r.maxy + 17);
        if (x1 <= x0 || y1 <= y0) return;
        r.cw = x1 - x0; r.chh = y1 - y0;
        r.crop = new byte[r.cw * r.chh * 2];
        int k = 0;
        for (int yy = y0; yy < y1; yy++) for (int xx = x0; xx < x1; xx++) {
            int p = m.read16(buf + 2 * (yy * sw + xx));
            r.crop[k++] = (byte) p; r.crop[k++] = (byte) (p >> 8);
        }
    }

    void report() {
        try {
            PrintWriter dw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(dir.getParentFile(), "draws.txt")), "UTF-8"));
            for (Rec r : recs.values()) {
                double cov = r.cover > 1 ? 0 : r.cover;
                dw.println(r.key + "\t" + r.room0 + "," + r.room1 + "\t" + r.minx + "," + r.miny + "," + r.maxx + "," + r.maxy + "\t" + r.clipped + "\t"
                        + String.format(Locale.ROOT, "%.3f", cov) + "\t" + r.t + "\t" + r.hex);
            }
            dw.close();
            if (baseMode) {
                // baseline per key: borders, clipped, extent, cover
                Map<String, String[]> b = new LinkedHashMap<>();
                for (Rec r : recs.values()) {
                    double cov = r.cover > 1 ? 0 : r.cover;
                    String ext = r.minx + "," + r.miny + "," + r.maxx + "," + r.maxy;
                    String[] o = b.get(r.key);
                    if (o == null) b.put(r.key, new String[]{String.join(" ", r.borders), "" + r.clipped, ext, "" + cov, String.join(" ", r.ovl)});
                    else { o[0] = (o[0] + " " + String.join(" ", r.borders)).trim(); o[1] = "" + Math.max(Integer.parseInt(o[1]), r.clipped); o[2] = union(o[2], ext); o[3] = "" + Math.max(Double.parseDouble(o[3]), cov); o[4] = (o[4] + " " + String.join(" ", r.ovl)).trim(); }
                }
                for (Map.Entry<String, String[]> e : b.entrySet()) w.println(e.getKey() + "\t" + String.join("\t", e.getValue()));
                w.close();
                return;
            }
            int id = 0;
            for (Rec r : recs.values()) {
                double cov = r.cover > 1 ? 0 : r.cover;
                String[] bs = base.get(r.key);
                TreeSet<String> borders = new TreeSet<>(r.borders);
                StringBuilder why = new StringBuilder();
                boolean clipB = false; double covB = 0;
                if (bs != null) {
                    borders.removeAll(Arrays.asList(bs[0].split(" ")));
                    String[] e = bs[2].split(",");
                    int kx0 = Integer.parseInt(e[0]), ky0 = Integer.parseInt(e[1]), kx1 = Integer.parseInt(e[2]), ky1 = Integer.parseInt(e[3]);
                    for (Iterator<String> it = borders.iterator(); it.hasNext(); ) {
                        String s = it.next();
                        int v = Integer.parseInt(s.substring(1));
                        if (s.charAt(0) == 'v' ? (v > kx0 && v <= kx1) : (v > ky0 && v <= ky1)) it.remove();
                    }
                    clipB = Integer.parseInt(bs[1]) > 0;
                    covB = Double.parseDouble(bs[3]);
                } else why.append("nobase ");
                if (r.clipped > 0 && !clipB) why.append("clip").append(r.clipped).append(' ');
                if (cov > Math.max(0.08, covB + 0.05)) why.append(String.format(Locale.ROOT, "cover%.2f ", cov));
                TreeSet<String> ovl = new TreeSet<>(r.ovl);
                if (bs != null) ovl.removeAll(Arrays.asList(bs[4].split(" ")));
                else ovl.clear();
                // an overlap only counts when the other text is also known to the baseline
                for (Iterator<String> it = ovl.iterator(); it.hasNext(); ) if (!base.containsKey(it.next())) it.remove();
                if (!ovl.isEmpty()) why.append("ovl:").append(ovl.first()).append(' ');
                for (String s : borders) why.append(s).append(' ');
                if (why.length() == 0 || why.toString().equals("nobase ")) continue;
                int n = id++;
                // estimated free width: nearest new border / covered column around the anchor
                String[] kp = r.key.split(":");
                int ax = Integer.parseInt(kp[1]), al = Integer.parseInt(kp[3]);
                int lo = r.room0, hi = r.room1;
                for (String bd : borders) if (bd.charAt(0) == 'v') {
                    int v = Integer.parseInt(bd.substring(1));
                    if (v <= ax && v > lo) lo = v; if (v > ax && v < hi) hi = v;
                }
                if (cov > Math.max(0.08, covB + 0.05)) {
                    if (r.cminx > ax && r.cminx < hi) hi = r.cminx;
                    if (r.cmaxx < ax && r.cmaxx >= lo) lo = r.cmaxx + 1;
                    if (al == 0 && r.cminx >= ax && r.cminx < hi) hi = r.cminx;
                }
                int fitw = al == 0 ? hi - ax - 1 : al == 1 ? 2 * Math.min(ax - lo, hi - ax) - 2 : ax - lo - 1;
                w.println(n + "\t" + r.t + "\t" + why.toString().trim() + "\t" + r.key + "\troom=" + lo + "," + hi + " fit=" + fitw
                        + "\text=" + r.minx + "," + r.miny + "-" + r.maxx + "," + r.maxy + "\t" + r.hex);
                if (r.crop != null) {
                    java.awt.image.BufferedImage im = new java.awt.image.BufferedImage(r.cw, r.chh, java.awt.image.BufferedImage.TYPE_INT_RGB);
                    int k = 0;
                    for (int yy = 0; yy < r.chh; yy++) for (int xx = 0; xx < r.cw; xx++) {
                        int p = (r.crop[k++] & 0xff) | (r.crop[k++] & 0xff) << 8;
                        int R = (p >> 11) & 31, G = (p >> 5) & 63, B = p & 31;
                        im.setRGB(xx, yy, (R << 19) | (R << 14 & 0x70000) | (G << 10) | (G << 4 & 0x300) | (B << 3) | (B >> 2));
                    }
                    javax.imageio.ImageIO.write(im, "png", new File(dir, String.format("%04d.png", n)));
                }
            }
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String hex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte x : b) sb.append(String.format("%02x", x & 0xff));
        return sb.toString();
    }
}
