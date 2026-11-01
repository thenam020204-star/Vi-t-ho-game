import com.github.unidbg.Emulator;
import com.github.unidbg.Module;
import com.github.unidbg.Symbol;
import com.github.unidbg.arm.backend.Backend;
import com.github.unidbg.debugger.Debugger;
import com.github.unidbg.memory.Memory;
import com.github.unidbg.pointer.UnidbgPointer;
import unicorn.Arm64Const;
import unicorn.UnicornConst;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

/**
 * T2 huong 2: thay malloc/calloc/realloc/free/memalign/posix_memalign/malloc_usable_size cua libc (jemalloc) bang
 * bo cap phat viet bang Java. Sieu du lieu (kich thuoc, danh sach trong) nam phia Java; vung nho cap phat nam trong
 * mot arena mmap lien tuc cua emulator. Chi dung trong emulator, khong dung toi game hay lib.
 *
 * Cach hoat dong: dat breakpoint o dau cac ham xuat khau cua libc.so; callback doc doi so, tinh ket qua, ghi x0 va
 * dat PC = LR (nhu hook gettimeofday). Con tro khong do JMalloc cap (vd. cap tu jemalloc truoc khi cai hook) thi
 * callback tra true ma khong doi thanh ghi => ham goc cua libc chay nhu thuong.
 *
 * Bat bang -Demu.malloc=java ; -Demu.jmalloc.mb=N (kich thuoc arena, mac dinh 768 MB ao); -Demu.jmalloc.poison=true
 * (ghi 0xA5 len khoi vua giai phong, de bat use-after-free).
 */
public class JMalloc {
    final Emulator<?> emu;
    final Backend be;
    long base, limit, bump;
    final long arenaSize = Long.getLong("emu.jmalloc.mb", 768L) << 20;
    final boolean poison = Boolean.getBoolean("emu.jmalloc.poison");
    /** con tro tra cho chuong trinh -> {dau khoi, kich thuoc lop} */
    final HashMap<Long, long[]> live = new HashMap<>();
    final HashMap<Long, ArrayDeque<Long>> freeLists = new HashMap<>();
    long nMalloc, nCalloc, nRealloc, nFree, nMemalign, nUsable, nForeignFree, nForeignRealloc, nForeignUsable, nFail, nReuse;
    long liveBytes, peakBytes, doubleFree;

    public JMalloc(Emulator<?> emu) {
        this.emu = emu;
        this.be = emu.getBackend();
    }

    /** lop kich thuoc: 16,32,48,64 roi 4 buoc cho moi lan gap doi (80,96,112,128,160,...). */
    static long cls(long n) {
        if (n <= 16) return 16;
        if (n <= 64) return (n + 15) & ~15L;
        long m = n - 1;
        int hi = 63 - Long.numberOfLeadingZeros(m);
        int sh = hi - 2;
        return ((m >> sh) + 1) << sh;
    }

    static long arg(Backend be, int i) { return be.reg_read(Arm64Const.UC_ARM64_REG_X0 + i).longValue(); }

    final boolean trace = Boolean.getBoolean("emu.jmalloc.trace");
    long traceN;
    void tr(String op, long a0, long a1, long res) {
        if (!trace || ++traceN > Long.getLong("emu.jmalloc.tracemax", 400L)) return;
        long lr = be.reg_read(Arm64Const.UC_ARM64_REG_LR).longValue();
        Module m = emu.getMemory().findModuleByAddress(lr);
        System.out.println("[jm] " + op + "(0x" + Long.toHexString(a0) + (a1 != -1 ? ",0x" + Long.toHexString(a1) : "") + ") -> 0x" + Long.toHexString(res)
                + " lr=" + (m == null ? "0x" + Long.toHexString(lr) : m.name + "+0x" + Long.toHexString(lr - m.base)));
    }

    void ret(long v) {
        be.reg_write(Arm64Const.UC_ARM64_REG_X0, v);
        be.reg_write(Arm64Const.UC_ARM64_REG_PC, be.reg_read(Arm64Const.UC_ARM64_REG_LR).longValue());
    }

    /** cap khoi >= n byte, can `align` (luy thua 2 >= 16). Tra {ptr, dau khoi, lop, sach(1)/ban(0)} hoac null. */
    long[] alloc(long n, long align) {
        long need = align > 16 ? n + align : n;
        if (need < 0 || need > arenaSize) return null;
        long c = cls(Math.max(need, 1));
        ArrayDeque<Long> fl = freeLists.get(c);
        long start;
        long fresh = 0;
        if (fl != null && !fl.isEmpty()) {
            start = fl.pollLast();
            nReuse++;
        } else {
            if (bump + c > limit) return null;
            start = bump;
            bump += c;
            fresh = 1;
        }
        long p = align > 16 ? (start + align - 1) & ~(align - 1) : start;
        live.put(p, new long[]{start, c});
        liveBytes += c;
        if (liveBytes > peakBytes) peakBytes = liveBytes;
        return new long[]{p, start, c, fresh};
    }

    void zero(long addr, long len) {
        byte[] z = new byte[(int) Math.min(len, 1 << 20)];
        for (long off = 0; off < len; off += z.length) {
            int k = (int) Math.min(z.length, len - off);
            be.mem_write(addr + off, k == z.length ? z : new byte[k]);
        }
    }

    void release(long p, long[] blk) {
        live.remove(p);
        liveBytes -= blk[1];
        if (poison) {
            byte[] a5 = new byte[(int) Math.min(blk[1], 1 << 20)];
            java.util.Arrays.fill(a5, (byte) 0xA5);
            for (long off = 0; off < blk[1]; off += a5.length) {
                int k = (int) Math.min(a5.length, blk[1] - off);
                be.mem_write(blk[0] + off, k == a5.length ? a5 : java.util.Arrays.copyOf(a5, k));
            }
        }
        freeLists.computeIfAbsent(blk[1], k -> new ArrayDeque<>()).addLast(blk[0]);
    }

    long usable(long p, long[] blk) { return blk[0] + blk[1] - p; }

    void copy(long dst, long src, long len) {
        for (long off = 0; off < len; off += 1 << 20) {
            int k = (int) Math.min(1 << 20, len - off);
            be.mem_write(dst + off, be.mem_read(src + off, k));
        }
    }

    public void install() {
        Memory mem = emu.getMemory();
        UnidbgPointer arena = mem.mmap((int) Math.min(arenaSize, Integer.MAX_VALUE & ~0xfffL), UnicornConst.UC_PROT_READ | UnicornConst.UC_PROT_WRITE);
        base = arena.peer;
        bump = (base + 15) & ~15L;
        limit = base + Math.min(arenaSize, Integer.MAX_VALUE & ~0xfffL);
        Module libc = mem.findModule("libc.so");
        Debugger dbg = emu.attach();
        System.out.println("[jmalloc] arena 0x" + Long.toHexString(base) + "..0x" + Long.toHexString(limit) + " (" + ((limit - base) >> 20) + " MB ao), libc base 0x" + Long.toHexString(libc.base));
        hook(dbg, libc, "malloc", () -> {
            nMalloc++;
            long n = arg(be, 0);
            long[] r = alloc(n, 16);
            if (r == null) nFail++;
            tr("malloc", n, -1, r == null ? 0 : r[0]);
            ret(r == null ? 0 : r[0]);
            return true;
        });
        hook(dbg, libc, "calloc", () -> {
            nCalloc++;
            long a = arg(be, 0), b = arg(be, 1);
            if (a != 0 && Long.compareUnsigned(b, Long.divideUnsigned(-1L, a)) > 0) { nFail++; ret(0); return true; }
            long n = a * b;
            long[] r = alloc(n, 16);
            if (r == null) { nFail++; tr("calloc", a, b, 0); ret(0); return true; }
            if (r[3] == 0) zero(r[0], n);
            tr("calloc", a, b, r[0]);
            ret(r[0]);
            return true;
        });
        hook(dbg, libc, "free", () -> {
            long p = arg(be, 0);
            if (p == 0) { ret(0); return true; }
            long[] blk = live.get(p);
            if (blk == null) {
                if (p >= base && p < limit) { doubleFree++; if (doubleFree <= 5) System.out.println("[jmalloc] free() lan 2 / con tro la 0x" + Long.toHexString(p)); tr("free(la)", p, -1, 0); ret(0); return true; }
                nForeignFree++;
                tr("free(ngoai)", p, -1, 0);
                return true;           // khong phai cua minh: de free goc cua libc chay
            }
            nFree++;
            tr("free", p, -1, 0);
            release(p, blk);
            ret(0);
            return true;
        });
        hook(dbg, libc, "realloc", () -> {
            long p = arg(be, 0), n = arg(be, 1);
            nRealloc++;
            if (p == 0) {
                long[] r = alloc(n, 16);
                if (r == null) nFail++;
                tr("realloc", p, n, r == null ? 0 : r[0]);
                ret(r == null ? 0 : r[0]);
                return true;
            }
            long[] blk = live.get(p);
            if (blk == null) { nForeignRealloc++; tr("realloc(ngoai)", p, n, 0); return true; }
            if (n == 0) { tr("realloc->free", p, n, 0); release(p, blk); ret(0); return true; }           // jemalloc 4: realloc(p,0) == free(p)
            long us = usable(p, blk);
            if (n <= us && cls(n) * 2 > us) { tr("realloc(giu)", p, n, p); ret(p); return true; }
            long[] r = alloc(n, 16);
            if (r == null) { nFail++; tr("realloc", p, n, 0); ret(0); return true; }
            copy(r[0], p, Math.min(us, n));
            tr("realloc", p, n, r[0]);
            release(p, blk);
            ret(r[0]);
            return true;
        });
        hook(dbg, libc, "memalign", () -> {
            nMemalign++;
            long al = arg(be, 0), n = arg(be, 1);
            if (al == 0 || (al & (al - 1)) != 0) { nFail++; ret(0); return true; }
            long[] r = alloc(n, Math.max(al, 16));
            if (r == null) nFail++;
            tr("memalign", al, n, r == null ? 0 : r[0]);
            ret(r == null ? 0 : r[0]);
            return true;
        });
        hook(dbg, libc, "posix_memalign", () -> {
            nMemalign++;
            long out = arg(be, 0), al = arg(be, 1), n = arg(be, 2);
            if (al < 8 || (al & (al - 1)) != 0) { ret(22); return true; }       // EINVAL
            long[] r = alloc(n, Math.max(al, 16));
            if (r == null) { nFail++; ret(12); return true; }                   // ENOMEM
            UnidbgPointer.pointer(emu, out).setLong(0, r[0]);
            tr("posix_memalign", al, n, r[0]);
            ret(0);
            return true;
        });
        hook(dbg, libc, "malloc_usable_size", () -> {
            long p = arg(be, 0);
            nUsable++;
            if (p == 0) { ret(0); return true; }
            long[] blk = live.get(p);
            if (blk == null) { nForeignUsable++; tr("usable(ngoai)", p, -1, 0); return true; }
            tr("usable", p, -1, usable(p, blk));
            ret(usable(p, blk));
            return true;
        });
    }

    interface Fn { boolean run(); }

    void hook(Debugger dbg, Module libc, String name, Fn fn) {
        Symbol s = libc.findSymbolByName(name, false);
        if (s == null) { System.out.println("[jmalloc] khong co symbol " + name); return; }
        dbg.addBreakPoint(s.getAddress(), (e, addr) -> fn.run());
    }

    public String report() {
        return "[jmalloc] malloc=" + nMalloc + " calloc=" + nCalloc + " realloc=" + nRealloc + " free=" + nFree + " memalign=" + nMemalign + " usable=" + nUsable
                + " | tai dung=" + nReuse + " that bai=" + nFail + " | free ngoai(jemalloc goc)=" + nForeignFree + " realloc ngoai=" + nForeignRealloc + " usable ngoai=" + nForeignUsable
                + " free_lap/la=" + doubleFree + " | dang giu=" + liveBytes + " B dinh=" + peakBytes + " B, arena da dung=" + (bump - base) + " B";
    }
}
