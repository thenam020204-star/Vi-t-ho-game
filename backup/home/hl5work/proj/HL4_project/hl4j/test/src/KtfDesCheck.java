import hl4.ktf.KtfRuntime;
import java.io.File;
import java.nio.file.Files;
import java.util.*;

/** Decrypt every KTF resource with the game's DES, guest code vs host HLE, and compare. */
public class KtfDesCheck {
    static KtfRuntime boot(RunHeadless h, boolean hle) throws Exception {
        KtfRuntime.hleDes = hle;
        KtfRuntime k = new KtfRuntime(h);
        h.rt = k;
        String bin = null;
        for (String n : h.files.keySet()) if (n.startsWith("client.bin")) bin = n;
        k.load(h.files.get(bin), Integer.parseInt(bin.substring(10)));
        k.start("Clet");
        h.vbase = h.vtime;
        while (h.currentTimeMillis() - h.vbase < 3000) {
            long w = k.pump();
            if (w != 0) h.vtime += w < 0 ? 10 : Math.min(w, 10);
        }
        return k;
    }

    public static void main(String[] a) throws Exception {
        byte[] zip = Files.readAllBytes(new File(a[0]).toPath());
        Map<String, byte[]> outer = RunHeadless.unzip(zip);
        RunHeadless h1 = new RunHeadless(), h2 = new RunHeadless();
        for (Map.Entry<String, byte[]> e : outer.entrySet()) if (e.getKey().endsWith(".jar")) { h1.files.putAll(RunHeadless.unzip(e.getValue())); h2.files.putAll(RunHeadless.unzip(e.getValue())); }
        h1.storageDir = Files.createTempDirectory("d1").toFile();
        h2.storageDir = Files.createTempDirectory("d2").toFile();
        KtfRuntime g = boot(h1, false), x = boot(h2, true);
        int n = 0, bad = 0, total = 0;
        List<String> names = new ArrayList<>(h1.files.keySet());
        Collections.sort(names);
        for (String name : names) {
            byte[] d = h1.files.get(name);
            if (d.length < 8 || d.length > 200000 || (d.length & 7) != 0) continue;
            byte[] r1 = run(g, d, a.length > 1), r2 = run(x, d, a.length > 1);
            n++;
            total += d.length;
            if (!Arrays.equals(r1, r2)) { bad++; System.out.println("MISMATCH " + name); }
        }
        System.out.println("files " + n + " bytes " + total + " mismatches " + bad);
    }

    static byte[] run(KtfRuntime k, byte[] d, boolean enc) {
        int buf = k.alloc(d.length);
        k.mem.writeBytes(buf, d, 0, d.length);
        k.cpu.call((enc ? 0x1089b0 : 0x108920) | 1, new int[]{buf, d.length});
        return k.mem.readBytes(buf, d.length);
    }
}
