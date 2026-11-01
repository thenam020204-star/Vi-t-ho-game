import com.namp.vh.ModMem;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * T8c: kiem kenh Java -> native (ModMem) tren JVM may tinh (Linux), khong can Android.
 *   javac -cp <classes com.namp.vh> -d <out> java/test/ModMemTest.java ; java -cp <out>:<classes> ModMemTest
 * Cap phat 0x740000 B bang Unsafe, dat magic ELF o dau, dung "maps" gia (ghi vao tep tam) mo ta no nhu libgame.so,
 * roi goi ModMem.write that: kenh /proc/self/mem cua nhan Linux that, doc lai bang Unsafe.
 */
public class ModMemTest {
    static Object U;
    static Method alloc, free, putInt, getInt, setMemory;
    static int fail;

    static void check(String name, boolean ok) {
        System.out.println((ok ? "  DAT  " : "  LOI  ") + name);
        if (!ok) fail++;
    }

    static int gi(long a) throws Exception {
        return ((Integer) getInt.invoke(U, a)).intValue();
    }

    static void pi(long a, int v) throws Exception {
        putInt.invoke(U, a, v);
    }

    static void maps(File f, String... lines) throws Exception {
        FileWriter w = new FileWriter(f);
        for (String l : lines) w.write(l + "\n");
        w.close();
        ModMem.mapsPath = f.getPath();
        ModMem.resetCache();
    }

    static String line(long s, long e, String perms, long off, String path) {
        return String.format("%x-%x %s %08x fc:05 1234 %s", s, e, perms, off, path);
    }

    public static void main(String[] a) throws Exception {
        Class<?> c = Class.forName("sun.misc.Unsafe");
        Field f = c.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        U = f.get(null);
        alloc = c.getMethod("allocateMemory", long.class);
        free = c.getMethod("freeMemory", long.class);
        putInt = c.getMethod("putInt", long.class, int.class);
        getInt = c.getMethod("getInt", long.class);
        setMemory = c.getMethod("setMemory", long.class, long.class, byte.class);
        File tmp = File.createTempFile("maps", ".txt");
        tmp.deleteOnExit();
        final long SZ = 0x740000L;
        long raw = ((Long) alloc.invoke(U, SZ + 0x1000)).longValue();
        long base = (raw + 0xfff) & ~0xfffL;
        setMemory.invoke(U, raw, SZ + 0x1000, (byte) 0);
        pi(base, 0x464c457f);
        long addr = base + ModMem.OFF;
        String lib = "/data/app/~~x/com.x/lib/arm64/libgame.so";

        System.out.println("[1] locate (maps gia)");
        List<String> m = new ArrayList<String>(Arrays.asList(
            line(0x7000000000L, 0x7000001000L, "r--p", 0, "/system/lib64/libc.so"),
            line(base, base + 0x2b6000L, "r--p", 0, lib),
            line(base + 0x2b6000L, base + 0x2cc000L, "rw-p", 0x2b5000L, lib),
            line(base + 0x2cc000L, base + 0x2d0000L, "rw-p", 0x2c4000L, lib),
            line(base + 0x2d0000L, base + 0x730000L, "rw-p", 0, "[anon:.bss]")));
        ModMem.Located l = ModMem.locate(m, "libgame.so", ModMem.OFF, 16);
        check("tim thay base va dia chi dich", l.target != null && l.target.base == base && l.target.addr == addr);
        // VMA bi tach doi tai 0x72f000 van phu du
        List<String> split = new ArrayList<String>(m);
        split.set(4, line(base + 0x2d0000L, base + 0x72f000L, "rw-p", 0, "[anon:.bss]"));
        split.add(line(base + 0x72f000L, base + 0x730000L, "rw-p", 0, "[anon:.bss]"));
        check("VMA tach doi van duoc chap nhan", ModMem.locate(split, "libgame.so", ModMem.OFF, 16).target != null);
        List<String> ro = new ArrayList<String>(m);
        ro.set(4, line(base + 0x2d0000L, base + 0x730000L, "r--p", 0, "[anon:.bss]"));
        check("vung khong rw-p => loi", ModMem.locate(ro, "libgame.so", ModMem.OFF, 16).error != null);
        List<String> shortv = new ArrayList<String>(m);
        shortv.set(4, line(base + 0x2d0000L, base + 0x72f784L, "rw-p", 0, "[anon:.bss]"));
        check("VMA ngan hon vung can ghi => loi", ModMem.locate(shortv, "libgame.so", ModMem.OFF, 16).error != null);
        List<String> nolib = new ArrayList<String>(m.subList(0, 1));
        check("khong co libgame.so => loi", ModMem.locate(nolib, "libgame.so", ModMem.OFF, 16).error != null);
        List<String> nooff0 = new ArrayList<String>(m);
        nooff0.set(1, line(base, base + 0x2b6000L, "r--p", 0x1000, lib));
        check("khong co dong offset 0 => loi", ModMem.locate(nooff0, "libgame.so", ModMem.OFF, 16).error != null);
        List<String> lowest = new ArrayList<String>(m);
        lowest.add(0, line(base - 0x100000L, base - 0x80000L, "r--p", 0, "/other/dir/libgame.so"));
        check("base = dia chi thap nhat trong cac dong offset 0", ModMem.locate(lowest, "libgame.so", ModMem.OFF, 16).target.base == base - 0x100000L);
        check("duong dan co khoang trang van khop", ModMem.locate(Arrays.asList(
            line(base, base + 0x730000L, "rw-p", 0, "/data/app/my app/lib/arm64/libgame.so")), "libgame.so", ModMem.OFF, 16).target != null);

        System.out.println("[2] write that qua /proc/self/mem (nhan Linux)");
        maps(tmp, m.toArray(new String[0]));
        String err = ModMem.write(new int[] {2, 50, 7});
        check("write tra null (OK): " + err, err == null);
        check("kenh = mem", "mem".equals(ModMem.lastChannel));
        check("Mdmg/Mdef/Mexp doc lai bang Unsafe = 2/50/7", gi(addr) == 2 && gi(addr + 4) == 50 && gi(addr + 8) == 7);
        check("int thu 4 khong bi ghi (= 0)", gi(addr + 12) == 0);
        check("magic ELF khong bi doi", gi(base) == 0x464c457f);
        err = ModMem.write(new int[] {1, 1, 1});
        check("ghi lai x1 (cache dia chi)", err == null && gi(addr) == 1 && gi(addr + 4) == 1 && gi(addr + 8) == 1);
        err = ModMem.write(new int[] {49, 48, 47, 130});
        check("ghi 4 int (T9b) duoc", err == null && gi(addr + 12) == 130 && gi(addr) == 49);
        check("ghi 5 int bi tu choi", ModMem.write(new int[5]) != null);
        check("mang rong bi tu choi", ModMem.write(new int[0]) != null);

        System.out.println("[3] du phong Unsafe khi /proc/self/mem hong");
        String oldMem = ModMem.memPath;
        ModMem.memPath = "/khong/co/mem";
        ModMem.resetCache();
        err = ModMem.write(new int[] {5, 6, 7});
        check("mem hong => Unsafe ghi duoc: " + err, err == null && "unsafe".equals(ModMem.lastChannel));
        check("gia tri 5/6/7", gi(addr) == 5 && gi(addr + 4) == 6 && gi(addr + 8) == 7);

        System.out.println("[4] magic ELF sai => khong ghi gi, bao loi");
        ModMem.memPath = oldMem;
        ModMem.resetCache();
        pi(base, 0x12345678);
        pi(addr, 0x0badf00d);
        err = ModMem.write(new int[] {9, 9, 9});
        check("loi (ca hai kenh): " + err, err != null && err.contains("magic"));
        check("vung dich khong bi ghi", gi(addr) == 0x0badf00d);
        pi(base, 0x464c457f);

        System.out.println("[5] maps khong co lib / vung khong rw-p => loi, khong nem ngoai le");
        maps(tmp, line(base, base + 0x730000L, "r--p", 0, lib));
        err = ModMem.write(new int[] {3, 3, 3});
        check("khong rw-p: " + err, err != null && gi(addr) == 0x0badf00d);
        ModMem.mapsPath = "/khong/co/maps";
        ModMem.resetCache();
        err = ModMem.write(new int[] {3, 3, 3});
        check("maps khong doc duoc: " + err, err != null);

        free.invoke(U, raw);
        System.out.println(fail == 0 ? "TONG KET: TAT CA DAT" : "TONG KET: " + fail + " LOI");
        System.exit(fail == 0 ? 0 : 1);
    }
}
