import com.github.unidbg.AndroidEmulator;
import com.github.unidbg.Module;
import com.github.unidbg.arm.backend.Backend;
import com.github.unidbg.arm.backend.Unicorn2Factory;
import com.github.unidbg.linux.android.AndroidEmulatorBuilder;
import com.github.unidbg.linux.android.AndroidResolver;
import com.github.unidbg.linux.android.dvm.DalvikModule;
import com.github.unidbg.linux.android.dvm.VM;
import com.github.unidbg.memory.Memory;
import com.github.unidbg.memory.MemoryBlock;
import com.github.unidbg.pointer.UnidbgPointer;
import unicorn.Arm64Const;

import java.io.File;

/**
 * T8d: goi thang cac diem va cua mod menu trong libgame.so bang unidbg `emu_start` (khong chay game).
 *   java ModCheck <libgame.so> <patched|orig>
 * patched: lib da va (3 diem vao + cave): ket qua phai dung cong thuc docs/mod_menu.md.
 * orig   : lib goc (doi chung): he so khong co tac dung, ket qua == gia tri dau vao.
 *
 * A) CHAR_AddDamage 0x18b3e0  : x0 ke tan cong | NULL, x1 muc tieu, w2 dmg. Chay toi 0x18b434 (`bl CHAR_AddLife`) va doc x0 = muc tieu,
 *    w1 = -dmg (= doi so CHAR_AddLife); dmg < 1 thi chay toi 0x18b7e4 (nhanh thoat cua ham goc) va doc w2.
 * B) MERCENARYSYSTEM_AddExperience 0x1c7860: w19 = EXP co so; chay toi 0x1c7884 (`bl PARTY_GetMember`), doc w19 va w25 (= w19*110/100).
 * C) MERCENARYSYSTEM_AddExpFixed 0x1c7a44: w0 = EXP; chay toi 0x1c7a48, doc w19 va x8 (phai nguyen).
 * He so (Mdmg/Mdef/Mexp) dat truc tiep o base+0x72f780/84/88 trong .bss.
 */
public class ModCheck {
    static AndroidEmulator emu;
    static Backend be;
    static Memory mem;
    static long base;
    static long bss;
    static long stackTop, tls, tgt, att;
    static int pass, fail;
    static boolean patched;

    static final long CAP_DMG = 999999L;
    static final long CAP_EXP = 0xFFFFFFL;

    static void wr(int i, long v) { be.reg_write(Arm64Const.UC_ARM64_REG_X0 + i, v); }
    static long rd(int i) { return be.reg_read(Arm64Const.UC_ARM64_REG_X0 + i).longValue(); }
    static int rw(int i) { return (int) rd(i); }

    static void setCoef(int dmg, int def, int exp) {
        be.mem_write(bss, le(dmg));
        be.mem_write(bss + 4, le(def));
        be.mem_write(bss + 8, le(exp));
    }

    static byte[] le(int v) {
        return new byte[] {(byte) v, (byte) (v >>> 8), (byte) (v >>> 16), (byte) (v >>> 24)};
    }

    static boolean valid(int m) { return m >= 2 && m <= 50; }

    // ------------------------------------------------------------ mo hinh (cong thuc trong docs/mod_menu.md)
    static int modelDamage(int dmg, int tf, int af, boolean hasAtt, int mdmg, int mdef) {
        if (dmg < 1) return dmg;
        if (tf == 0) {
            if (!valid(mdef)) return dmg;
            int r = dmg / mdef;
            return r < 1 ? 1 : r;
        }
        if (!hasAtt || af != 0 || !valid(mdmg)) return dmg;
        if (dmg >= CAP_DMG) return dmg;
        return (int) Math.min((long) dmg * mdmg, CAP_DMG);
    }

    static int modelExp(int e, int mexp) {
        if (e < 1) return e;
        if (!valid(mexp)) return e;
        if (e >= CAP_EXP) return e;
        return (int) Math.min((long) e * mexp, CAP_EXP);
    }

    static void report(String name, boolean ok, String detail) {
        if (ok) pass++; else fail++;
        System.out.printf("%s %s %s%n", ok ? "DAT " : "LOI ", name, detail);
    }

    // ------------------------------------------------------------ A
    static void caseA(int dmg, int tf, int af, boolean hasAtt, int mdmg, int mdef) {
        setCoef(mdmg, mdef, 0x55);
        be.mem_write(tgt + 0x311, new byte[] {(byte) tf});
        be.mem_write(att + 0x311, new byte[] {(byte) af});
        for (int i = 0; i <= 28; i++) wr(i, 0x1111111100000000L + i);       // rac cho moi thanh ghi
        wr(0, hasAtt ? att : 0);
        wr(1, tgt);
        wr(2, dmg & 0xffffffffL);
        wr(3, 7);
        wr(4, 1);
        wr(19, 0xAAAA0019L);
        for (int i = 9; i <= 13; i++) wr(i, 0xDEAD0000L + i);
        long sp0 = stackTop - 0x80;
        be.reg_write(Arm64Const.UC_ARM64_REG_SP, sp0);
        be.reg_write(Arm64Const.UC_ARM64_REG_TPIDR_EL0, tls);
        String name = String.format("A dmg=%d tgtPhe=%d attPhe=%s Mdmg=%d Mdef=%d", dmg, tf, hasAtt ? "" + af : "NULL", mdmg, mdef);
        long want0 = patched ? modelDamage(dmg, tf, af, hasAtt, mdmg, mdef) : dmg;
        long until = dmg < 1 ? base + 0x18b7e4 : base + 0x18b434;
        try {
            be.emu_start(base + 0x18b3e0, until, 0, 0);
        } catch (Throwable t) {
            report(name, false, "NGOAI LE " + t);
            return;
        }
        int got;
        String extra;
        if (dmg < 1) {
            got = rw(2);
            extra = String.format("w2=%d sp-%s", got, (sp0 - be.reg_read(Arm64Const.UC_ARM64_REG_SP).longValue()) == 0x70 ? "0x70" : "?");
        } else {
            got = -rw(1);                                           // neg w1,w2  => w1 = -dmg'
            boolean inv = rd(0) == tgt && rw(20) == 7 && rw(25) == 1 && rd(19) == tgt && rd(21) == (hasAtt ? att : 0);
            extra = String.format("w1=%d sp-0x%x w20=%d w25=%d %s", rw(1), sp0 - be.reg_read(Arm64Const.UC_ARM64_REG_SP).longValue(), rw(20), rw(25),
                    inv ? "(x0/x19/x21/w20/w25 giu dung)" : "(THANH GHI BI DOI)");
            if (!inv) {
                report(name, false, extra);
                return;
            }
            if ((sp0 - be.reg_read(Arm64Const.UC_ARM64_REG_SP).longValue()) != 0x70) {
                report(name, false, extra + " SP SAI");
                return;
            }
        }
        report(name, got == (int) want0, String.format("%s ket qua=%d mong doi=%d", extra, got, (int) want0));
    }

    // ------------------------------------------------------------ B
    static void caseB(int e, int mexp) {
        setCoef(0x66, 0x77, mexp);
        for (int i = 0; i <= 28; i++) wr(i, 0x2222222200000000L + i);
        wr(19, e & 0xffffffffL);
        wr(8, 0x1234);
        for (int i = 9; i <= 12; i++) wr(i, 0xDEAD0000L + i);
        wr(22, 0x22);
        be.reg_write(Arm64Const.UC_ARM64_REG_SP, stackTop - 0x80);
        String name = String.format("B EXP co so=%d Mexp=%d", e, mexp);
        int w19 = patched ? modelExp(e, mexp) : e;
        long w25 = ((long) w19 * 110) / 100;                       // 0x1c7868..0x1c7880 (smull * 0x51eb851f >> 37, w19*110 < 2^31 va >= 0)
        try {
            be.emu_start(base + 0x1c7860, base + 0x1c7884, 0, 0);
        } catch (Throwable t) {
            report(name, false, "NGOAI LE " + t);
            return;
        }
        boolean ok = rw(19) == w19 && rw(25) == (int) w25 && rd(22) == 0x22 && rd(0) == 0;
        // w8 sau `mul w8,w19,w8` khong kiem; kiem x22 (EXP/cap khac) va w0 = 0 (lenh `mov w0,wzr` cua ham goc)
        report(name, ok, String.format("w19=%d (mong doi %d) w25=%d (mong doi %d)", rw(19), w19, rw(25), (int) w25));
    }

    // ------------------------------------------------------------ C
    static void caseC(int e, int mexp) {
        setCoef(0x66, 0x77, mexp);
        for (int i = 0; i <= 28; i++) wr(i, 0x3333333300000000L + i);
        wr(0, e & 0xffffffffL);
        wr(8, 0xCAFE000000001000L);                                // x8 = ket qua adrp: phai nguyen
        for (int i = 9; i <= 12; i++) wr(i, 0xDEAD0000L + i);
        be.reg_write(Arm64Const.UC_ARM64_REG_SP, stackTop - 0x80);
        String name = String.format("C EXP co dinh=%d Mexp=%d", e, mexp);
        int w19 = patched ? modelExp(e, mexp) : e;
        try {
            be.emu_start(base + 0x1c7a44, base + 0x1c7a48, 0, 0);
        } catch (Throwable t) {
            report(name, false, "NGOAI LE " + t);
            return;
        }
        boolean ok = rw(19) == w19 && rd(8) == 0xCAFE000000001000L && rw(0) == e;
        report(name, ok, String.format("w19=%d (mong doi %d) x8 %s w0 %s", rw(19), w19, rd(8) == 0xCAFE000000001000L ? "nguyen" : "BI DOI", rw(0) == e ? "nguyen" : "BI DOI"));
    }

    public static void main(String[] a) throws Exception {
        File lib = new File(a[0]);
        patched = a[1].equals("patched");
        File root = new File("work/modcheck_rootfs_" + a[1]);
        root.mkdirs();
        emu = AndroidEmulatorBuilder.for64Bit().setRootDir(root).setProcessName("modcheck")
                .addBackendFactory(new Unicorn2Factory(true)).build();
        mem = emu.getMemory();
        mem.setLibraryResolver(new AndroidResolver(23));
        VM vm = emu.createDalvikVM();
        DalvikModule dm = vm.loadLibrary(lib, false);
        Module m = dm.getModule();
        be = emu.getBackend();
        base = m.base;
        bss = base + 0x72f780L;
        MemoryBlock sb = mem.malloc(0x2000, true);
        stackTop = sb.getPointer().peer + 0x2000;
        MemoryBlock tb = mem.malloc(0x1000, true);
        tls = tb.getPointer().peer;
        MemoryBlock gb = mem.malloc(0x800, true);
        tgt = gb.getPointer().peer;
        MemoryBlock ab = mem.malloc(0x800, true);
        att = ab.getPointer().peer;
        System.out.printf("lib=%s che do=%s base=0x%x bss(he so)=0x%x%n", lib, a[1], base, bss);
        // kiem vung he so ghi/doc duoc (trang cuoi cua .bss) va ban dau = 0
        byte[] init = be.mem_read(bss, 16);
        boolean zero = true;
        for (byte b : init) if (b != 0) zero = false;
        report("vung he so 0x72f780..0x72f790 map duoc va ban dau = 0", zero, "");

        int[] coefs = {0, 1, 2, 3, 10, 49, 50, 51, 100, -1, Integer.MIN_VALUE};
        int[] dmgs = {Integer.MIN_VALUE, -5, 0, 1, 2, 3, 49, 50, 51, 999, 19999, 333333, 499999, 999997, 999998, 999999, 1000000, 40000000, Integer.MAX_VALUE};
        // phe (muc tieu, ke tan cong, co ke tan cong)
        int[][] pairs = {{0, 0, 1}, {0, 1, 1}, {0, 2, 1}, {0, 0, 0}, {1, 0, 1}, {1, 1, 1}, {1, 2, 1}, {1, 0, 0}, {2, 0, 1}, {2, 1, 1}};
        for (int[] pr : pairs) {
            for (int c : coefs) {
                for (int d : dmgs) {
                    // moi to hop (Mdmg, Mdef) = (c, c): kiem ca nhanh nhan lan nhanh chia
                    caseA(d, pr[0], pr[1], pr[2] == 1, c, c);
                }
            }
        }
        // he so khac nhau giua dmg va def
        for (int[] pr : pairs) {
            caseA(1000, pr[0], pr[1], pr[2] == 1, 7, 3);
            caseA(1000, pr[0], pr[1], pr[2] == 1, 3, 7);
            caseA(999999, pr[0], pr[1], pr[2] == 1, 50, 50);
            caseA(999998, pr[0], pr[1], pr[2] == 1, 50, 50);
            caseA(19999, pr[0], pr[1], pr[2] == 1, 50, 50);
            caseA(20000, pr[0], pr[1], pr[2] == 1, 50, 50);
        }
        int[] exps = {Integer.MIN_VALUE, -1, 0, 1, 2, 100, 5000, 335543, 335544, 335545, 16777213, 16777214, 16777215, 16777216, 16777217, 100000000, Integer.MAX_VALUE};
        for (int c : coefs) {
            for (int e : exps) {
                caseB(e, c);
                caseC(e, c);
            }
        }
        System.out.printf("so ca: DAT=%d LOI=%d%n", pass, fail);
        System.out.println(fail == 0 ? "TONG KET: TAT CA DAT" : "TONG KET: CO LOI");
        System.exit(fail == 0 ? 0 : 1);
    }
}
