package com.namp.vh;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Kenh Java -> native cua mod menu (T8, xem docs/mod_menu.md muc 6).
 *
 * libgame.so (vá nhóm `mod`) doc N int32 o .bss: base + 0x72f780 (Mdmg), +4 (Mdef), +8 (Mexp), +12 danh cho T9b.
 * Moi lan ghi:
 *   1. doc /proc/self/maps: tim base cua libgame.so (dong co duong dan ket thuc bang /libgame.so, offset 0, dia chi thap nhat),
 *      kiem [base+OFF, base+OFF+16) nam tron trong cac VMA `rw-p`;
 *   2. kenh chinh /proc/self/mem: kiem magic ELF tai base, ghi, doc lai so sanh;
 *   3. neu hong: sun.misc.Unsafe (chi khi magic ELF dung va vung rw-p), ghi, doc lai so sanh;
 *   4. ca hai hong: tra ve chuoi loi (khong nem ngoai le), game van chay.
 * Chi dung java.* (khong android.*) de kiem duoc tren JVM may tinh (java/test/ModMemTest.java).
 * Dia chi trong maps luon < 2^63 tren arm64 Android nen dung Long.parseLong (Long.parseUnsignedLong la API 26).
 */
public final class ModMem {
    private ModMem() { }

    public static final String LIB = "libgame.so";
    /** vaddr cua int32 dau tien (Mdmg) trong libgame.so. */
    public static final long OFF = 0x72f780L;
    /** So byte phai nam trong VMA rw-p (3 int cua T8 + 1 int danh cho T9b). */
    public static final int LOCATE_LEN = 16;
    static final int ELF_MAGIC = 0x464c457f;      // 7f 'E' 'L' 'F' doc little-endian

    /** Co the doi trong test tren JVM may tinh. */
    public static String mapsPath = "/proc/self/maps";
    public static String memPath = "/proc/self/mem";

    /** Kenh da dung o lan ghi thanh cong gan nhat: "mem" hoac "unsafe". */
    public static String lastChannel = "";

    /** Dia chi da tim duoc. */
    public static final class Target {
        public final long base;
        public final long addr;

        Target(long base, long addr) {
            this.base = base;
            this.addr = addr;
        }
    }

    /** Ket qua cua locate: dung mot trong hai truong khac null. */
    public static final class Located {
        public final Target target;
        public final String error;

        Located(Target t, String e) {
            target = t;
            error = e;
        }
    }

    // ------------------------------------------------------------------ maps
    static List<String> readMaps() throws IOException {
        List<String> out = new ArrayList<String>(512);
        BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(mapsPath), "UTF-8"));
        try {
            String s;
            while ((s = r.readLine()) != null) out.add(s);
        } finally {
            try { r.close(); } catch (IOException ignore) { }
        }
        return out;
    }

    /** Tim base va kiem vung [base+off, base+off+len) nam tron trong cac VMA co quyen rw-p. */
    public static Located locate(List<String> maps, String lib, long off, int len) {
        long base = -1L;
        int n = maps.size();
        long[] vs = new long[n];
        long[] ve = new long[n];
        boolean[] rw = new boolean[n];
        int cnt = 0;
        String suffix = "/" + lib;
        for (int i = 0; i < n; i++) {
            String[] f = maps.get(i).trim().split("\\s+", 6);
            if (f.length < 5) continue;
            int dash = f[0].indexOf('-');
            if (dash <= 0) continue;
            long s, e;
            try {
                s = Long.parseLong(f[0].substring(0, dash), 16);
                e = Long.parseLong(f[0].substring(dash + 1), 16);
            } catch (NumberFormatException ex) {
                continue;
            }
            String perms = f[1];
            vs[cnt] = s;
            ve[cnt] = e;
            rw[cnt] = perms.length() >= 4 && perms.charAt(0) == 'r' && perms.charAt(1) == 'w' && perms.charAt(3) == 'p';
            cnt++;
            if (f.length >= 6 && f[5].endsWith(suffix)) {
                long o;
                try {
                    o = Long.parseLong(f[2], 16);
                } catch (NumberFormatException ex) {
                    continue;
                }
                if (o == 0L && (base == -1L || s < base)) base = s;
            }
        }
        if (base == -1L) return new Located(null, "không thấy " + lib + " (offset 0) trong " + mapsPath);
        long addr = base + off;
        long cur = addr, end = addr + len;
        while (cur < end) {
            boolean found = false;
            for (int i = 0; i < cnt; i++) {
                if (vs[i] <= cur && cur < ve[i]) {
                    if (!rw[i]) return new Located(null, String.format("vùng 0x%x không phải rw-p", cur));
                    cur = ve[i];
                    found = true;
                    break;
                }
            }
            if (!found) return new Located(null, String.format("vùng 0x%x không có trong maps", cur));
        }
        return new Located(new Target(base, addr), null);
    }

    // ------------------------------------------------------------------ /proc/self/mem
    static byte[] memRead(long addr, int n) throws IOException {
        RandomAccessFile f = new RandomAccessFile(memPath, "r");
        try {
            f.seek(addr);
            byte[] b = new byte[n];
            f.readFully(b);
            return b;
        } finally {
            try { f.close(); } catch (IOException ignore) { }
        }
    }

    static void memWrite(long addr, byte[] b) throws IOException {
        RandomAccessFile f = new RandomAccessFile(memPath, "rw");
        try {
            f.seek(addr);
            f.write(b);
        } finally {
            try { f.close(); } catch (IOException ignore) { }
        }
    }

    static byte[] toBytes(int[] v) {
        byte[] b = new byte[v.length * 4];
        for (int i = 0; i < v.length; i++) {
            b[i * 4] = (byte) v[i];
            b[i * 4 + 1] = (byte) (v[i] >>> 8);
            b[i * 4 + 2] = (byte) (v[i] >>> 16);
            b[i * 4 + 3] = (byte) (v[i] >>> 24);
        }
        return b;
    }

    static int le32(byte[] b, int o) {
        return (b[o] & 0xff) | (b[o + 1] & 0xff) << 8 | (b[o + 2] & 0xff) << 16 | (b[o + 3] & 0xff) << 24;
    }

    /** Tra null neu OK, nguoc lai ly do. */
    static String viaMem(Target t, int[] v) {
        try {
            int magic = le32(memRead(t.base, 4), 0);
            if (magic != ELF_MAGIC) return String.format("magic ELF sai tại base 0x%x: 0x%08x", t.base, magic);
            byte[] want = toBytes(v);
            memWrite(t.addr, want);
            byte[] got = memRead(t.addr, want.length);
            for (int i = 0; i < want.length; i++) {
                if (got[i] != want[i]) return "đọc lại khác dữ liệu đã ghi";
            }
            return null;
        } catch (Throwable e) {
            return e.getClass().getSimpleName() + ": " + e.getMessage();
        }
    }

    // ------------------------------------------------------------------ Unsafe (du phong)
    private static Object unsafe;
    private static Method mPutInt;
    private static Method mGetInt;

    private static synchronized void unsafeInit() throws Exception {
        if (unsafe != null) return;
        Class<?> c = Class.forName("sun.misc.Unsafe");
        Field f = c.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Object u = f.get(null);
        mPutInt = c.getMethod("putInt", long.class, int.class);
        mGetInt = c.getMethod("getInt", long.class);
        unsafe = u;
    }

    static String viaUnsafe(Target t, int[] v) {
        try {
            unsafeInit();
            int magic = ((Integer) mGetInt.invoke(unsafe, Long.valueOf(t.base))).intValue();
            if (magic != ELF_MAGIC) return String.format("magic ELF sai tại base 0x%x: 0x%08x", t.base, magic);
            for (int i = 0; i < v.length; i++) mPutInt.invoke(unsafe, Long.valueOf(t.addr + 4L * i), Integer.valueOf(v[i]));
            for (int i = 0; i < v.length; i++) {
                int g = ((Integer) mGetInt.invoke(unsafe, Long.valueOf(t.addr + 4L * i))).intValue();
                if (g != v[i]) return "đọc lại khác dữ liệu đã ghi";
            }
            return null;
        } catch (Throwable e) {
            Throwable c = e.getCause() != null ? e.getCause() : e;
            return c.getClass().getSimpleName() + ": " + c.getMessage();
        }
    }

    // ------------------------------------------------------------------ API
    private static Target cached;

    /**
     * Ghi cac int32 vao vung nho cua libgame.so. Tra null neu OK (lastChannel = "mem" | "unsafe"),
     * nguoc lai chuoi loi (da thu ca hai kenh). Khong nem ngoai le.
     */
    public static synchronized String write(int[] vals) {
        try {
            if (vals == null || vals.length == 0 || vals.length * 4 > LOCATE_LEN) return "số lượng hệ số không hợp lệ";
            Target t = cached;
            if (t == null) {
                Located l = locate(readMaps(), LIB, OFF, LOCATE_LEN);
                if (l.error != null) return l.error;
                t = l.target;
            }
            String e1 = viaMem(t, vals);
            if (e1 == null) {
                cached = t;
                lastChannel = "mem";
                return null;
            }
            String e2 = viaUnsafe(t, vals);
            if (e2 == null) {
                cached = t;
                lastChannel = "unsafe";
                return null;
            }
            cached = null;           // lan sau tim lai maps
            lastChannel = "";
            return "mem: " + e1 + "; unsafe: " + e2;
        } catch (Throwable e) {
            cached = null;
            lastChannel = "";
            return e.getClass().getSimpleName() + ": " + e.getMessage();
        }
    }

    /** Chi cho test: xoa cache dia chi. */
    public static synchronized void resetCache() {
        cached = null;
        lastChannel = "";
    }
}
