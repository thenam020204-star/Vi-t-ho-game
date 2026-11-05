import hl4.lgt.LgtRuntime;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

/**
 * Offline helper that boots the LGT game headless and uses its own routines as tools.
 *
 * usage:
 *   HL4Tool des <decrypt|encrypt> <inDir> <outDir> <file...>   (paths relative to inDir)
 */
public class HL4Tool {
    static final int LGT_DES_DECRYPT = 0x8e28, LGT_DES_ENCRYPT = 0x8e9c;

    public static void main(String[] args) throws Exception {
        RunHeadless h = new RunHeadless();
        byte[] zip = Files.readAllBytes(new File(System.getProperty("zip", "/home/claude/work/LGT.zip")).toPath());
        Map<String, byte[]> outer = RunHeadless.unzip(zip);
        for (Map.Entry<String, byte[]> e : outer.entrySet()) if (e.getKey().endsWith(".jar")) h.files.putAll(RunHeadless.unzip(e.getValue()));
        h.storageDir = Files.createTempDirectory("hl4tool").toFile();
        LgtRuntime rt = new LgtRuntime(h);
        h.rt = rt;
        rt.aid = "0002D74B";
        rt.pid = "PD140249";
        rt.load(h.files.get("binary.mod"));
        rt.start();
        h.vbase = h.vtime;
        // run the start-up until the DES tables are initialised
        while (h.currentTimeMillis() - h.vbase < 1500) {
            long w = rt.pump();
            if (w != 0) h.vtime += w < 0 ? 10 : Math.min(w, 10);
        }
        if (args[0].equals("des")) {
            boolean enc = args[1].equals("encrypt");
            File in = new File(args[2]), out = new File(args[3]);
            int buf = rt.alloc(1 << 20);
            for (int i = 4; i < args.length; i++) {
                byte[] d = Files.readAllBytes(new File(in, args[i]).toPath());
                rt.mem.writeBytes(buf, d, 0, d.length);
                rt.cpu.call((enc ? LGT_DES_ENCRYPT : LGT_DES_DECRYPT) | 1, buf, d.length);
                byte[] o = rt.mem.readBytes(buf, d.length);
                File f = new File(out, args[i]);
                f.getParentFile().mkdirs();
                Files.write(f.toPath(), o);
            }
            System.err.println("done " + (args.length - 4) + " files");
        }
    }
}
