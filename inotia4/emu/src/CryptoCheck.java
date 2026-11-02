import com.github.unidbg.AndroidEmulator;
import com.github.unidbg.Module;
import com.github.unidbg.Symbol;
import com.github.unidbg.arm.backend.Unicorn2Factory;
import com.github.unidbg.linux.android.AndroidEmulatorBuilder;
import com.github.unidbg.linux.android.AndroidResolver;
import com.github.unidbg.linux.android.dvm.DalvikModule;
import com.github.unidbg.linux.android.dvm.VM;
import com.github.unidbg.memory.Memory;
import com.github.unidbg.memory.MemoryBlock;
import com.github.unidbg.pointer.UnidbgPointer;

import java.io.File;
import java.nio.file.Files;
import java.util.Random;

/**
 * T3c: goi thang ENCRYPT_Process2 (libgame.so) trong unidbg, khong chay game.
 *   java CryptoCheck <libgame.so> <out dir> <tag> [thu muc logs/save_TEST7]
 * Ghi <out>/<tag>.<case>.{in,out,mem} de scripts/re/crypto_xcheck.py so voi save_crypto.py, va in cac dong
 * "CASE <ten> ..." tom tat. Khoa rong = buffer HubSave_key (64 B) y nhu SAVE_Save/SAVE_LoadData.
 */
public class CryptoCheck {
    static AndroidEmulator emu;
    static Memory mem;
    static Symbol fn;
    static long keyAddr;
    static File out;
    static String tag;
    static final int GARB = 4600;      // so byte rac ghi ngay sau HubSave_key (64 B)

    static byte[] bytes(Random r, int n) {
        byte[] b = new byte[n];
        r.nextBytes(b);
        return b;
    }

    /** Ghi rac "giong bo nho" sau HubSave_key: nua so qword la con tro heap tag 0xb4/0x54.. , phan con lai ngau nhien. */
    static byte[] garbage(long seed) {
        Random r = new Random(seed);
        byte[] g = new byte[GARB];
        r.nextBytes(g);
        for (int i = 0; i + 8 <= GARB; i += 8) {
            if (r.nextBoolean()) {
                long p = 0xb400000000000000L | ((long) (0x70 + r.nextInt(0x40)) << 32) | (r.nextInt() & 0xfffffff0L);
                for (int k = 0; k < 8; k++) g[i + k] = (byte) (p >>> (8 * k));
            }
        }
        return g;
    }

    static void putKeyRegion(byte[] g) {          // HubSave_key[0..63] = 0, roi g ngay sau do
        UnidbgPointer k = UnidbgPointer.pointer(emu, keyAddr);
        k.write(0, new byte[64], 0, 64);
        k.write(64, g, 0, g.length);
    }

    static byte[] dumpKeyRegion() {
        return UnidbgPointer.pointer(emu, keyAddr).getByteArray(0, 64 + GARB);
    }

    /** Goi ENCRYPT_Process2(buf, n, mode, key). key: 0 = HubSave_key (rong theo mac dinh), 1 = NULL, 2 = chuoi `str`. */
    static Object[] call(byte[] buf, int n, int mode, int keyKind, String str) {
        MemoryBlock mb = mem.malloc(buf.length + 16, true);
        UnidbgPointer p = mb.getPointer();
        p.write(0, buf, 0, buf.length);
        Object keyArg;
        MemoryBlock kb = null;
        if (keyKind == 0) keyArg = UnidbgPointer.pointer(emu, keyAddr);
        else if (keyKind == 1) keyArg = 0L;
        else {
            kb = mem.malloc(256, true);
            kb.getPointer().setString(0, str);
            keyArg = kb.getPointer();
        }
        Number ret = fn.call(emu, p, n, mode, keyArg);
        byte[] after = p.getByteArray(0, buf.length);
        mb.free();
        if (kb != null) kb.free();
        return new Object[]{(ret.intValue() & 0xff) != 0, after};
    }

    static void save(String name, String ext, byte[] b) throws Exception {
        Files.write(new File(out, tag + "." + name + "." + ext).toPath(), b);
    }

    static boolean eq(byte[] a, byte[] b, int n) {
        for (int i = 0; i < n; i++) if (a[i] != b[i]) return false;
        return true;
    }

    /** ma hoa `plain` voi khoa da cho, ghi in/out, tra ve ban ma (n+3 byte). */
    static byte[] enc(String name, byte[] plain, int keyKind, String str, boolean dumpMem) throws Exception {
        int n = plain.length;
        byte[] buf = new byte[n + 3];
        System.arraycopy(plain, 0, buf, 0, n);
        Object[] r = call(buf, n, 0, keyKind, str);
        byte[] e = (byte[]) r[1];
        save(name, "in", plain);
        save(name, "out", e);
        if (dumpMem) save(name, "mem", dumpKeyRegion());
        System.out.printf("CASE %-22s ma hoa n=%d ret=%s r=%d sum_ma=%d sum_tho=%d%n", name, n, r[0], e[n + 1] & 255, e[n] & 255, e[n + 2] & 255);
        return e;
    }

    static boolean dec(String name, byte[] encd, byte[] plain, int keyKind, String str) throws Exception {
        int n = encd.length - 3;
        Object[] r = call(encd.clone(), n, 1, keyKind, str);
        boolean ok = (Boolean) r[0];
        boolean same = plain != null && eq((byte[]) r[1], plain, n);
        System.out.printf("CASE %-22s giai ma ret=%s plaintext_giong_ban_dau=%s%n", name, ok, plain == null ? "n/a" : String.valueOf(same));
        save(name, "dec", (byte[]) r[1]);
        return ok && (plain == null || same);
    }

    public static void main(String[] a) throws Exception {
        File lib = new File(a[0]);
        out = new File(a[1]);
        tag = a[2];
        File saves = a.length > 3 ? new File(a[3]) : null;
        out.mkdirs();
        File root = new File(out, tag + "_rootfs");
        root.mkdirs();
        emu = AndroidEmulatorBuilder.for64Bit().setRootDir(root).setProcessName("cryptocheck")
                .addBackendFactory(new Unicorn2Factory(true)).build();
        mem = emu.getMemory();
        mem.setLibraryResolver(new AndroidResolver(23));
        VM vm = emu.createDalvikVM();
        DalvikModule dm = vm.loadLibrary(lib, false);
        Module m = dm.getModule();
        fn = m.findSymbolByName("ENCRYPT_Process2", false);
        keyAddr = m.findSymbolByName("HubSave_key", false).getAddress();
        System.out.printf("lib=%s ENCRYPT_Process2=base+0x%x HubSave_key=base+0x%x%n", lib, fn.getAddress() - m.base, keyAddr - m.base);

        Random rnd = new Random(2026);
        byte[] P = bytes(rnd, 3189);                         // cung kich thuoc save0.dat

        // --- A. khoa rong, bo nho sau HubSave_key = 0 (chua co rac): ban goc == ban va
        byte[] eZ = enc("A_empty_zero", P, 0, null, true);
        dec("A_empty_zero", eZ, P, 0, null);

        // --- B. khoa rong, RAC G1 sau HubSave_key (mo phong tien trinh luc luu)
        byte[] g1 = garbage(1), g2 = garbage(2);
        putKeyRegion(g1);
        byte[] eG = enc("B_empty_garbageG1", P, 0, null, true);
        boolean sameMem = dec("B_dec_same_G1", eG, P, 0, null);
        putKeyRegion(g2);                                    // tien trinh khac: bo nho khac
        boolean diffMem = dec("B_dec_other_G2", eG, P, 0, null);
        System.out.printf("KET QUA B: giai ma khi bo nho GIONG=%s ; khi bo nho KHAC (tien trinh khac)=%s%n", sameMem, diffMem);
        putKeyRegion(new byte[GARB]);                        // tra ve 0 cho cac ca sau

        // --- C. cac khoa khong rong va NULL: ban va phai KHONG doi
        byte[] e1 = enc("C_key_394572", P, 2, "394572", false);
        dec("C_key_394572", e1, P, 2, "394572");
        byte[] e2 = enc("C_key_a", P, 2, "a", false);
        dec("C_key_a", e2, P, 2, "a");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 63; i++) sb.append((char) ('A' + i % 26));
        byte[] e3 = enc("C_key_63", P, 2, sb.toString(), false);
        dec("C_key_63", e3, P, 2, sb.toString());
        byte[] e4 = enc("C_key_NULL123", P, 1, null, false);
        dec("C_key_NULL123", e4, P, 1, null);
        byte[] small = bytes(rnd, 28);
        byte[] e5 = enc("C_small_n28", small, 2, "394572", false);
        dec("C_small_n28", e5, small, 2, "394572");
        byte[] e6 = enc("C_empty_n0", new byte[0], 0, null, false);
        dec("C_empty_n0", e6, new byte[0], 0, null);

        // --- D. file that cua nguoi dung
        if (saves != null) {
            byte[] thp = Files.readAllBytes(new File(saves, "edf2113c684f2b822de06dad8d06b016A/thpcheckslot1.dat").toPath());
            boolean okThp = dec("D_thpcheckslot1", thp, null, 2, "394572");
            System.out.println("KET QUA D thpcheckslot1 (khoa 394572): " + okThp);
            byte[] s0 = Files.readAllBytes(new File(saves, "d41d8cd98f00b204e9800998ecf8427e/save0.dat").toPath());
            save("D_save0", "in", s0);
            boolean okS0 = dec("D_save0_key_empty", s0, null, 0, null);
            System.out.println("KET QUA D save0.dat (khoa rong, bo nho = 0): " + okS0 + " (mong doi false: save goc dung keystream co rac)");
        }
        System.out.println("XONG");
        emu.close();
    }
}
