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
 * A) CHAR_AddDamage 0x18b3e0  : x0 ke tan cong | NULL, x1 muc tieu, w2 dmg. Chay toi CHAR_AddLife 0x18b844 (qua `bl` @0x18b434 + PLT 0x2aecc0) va doc x0 = muc tieu,
 *    w1 = -dmg (= doi so CHAR_AddLife, tuc sat thuong thuc nhan); dmg < 1 thi chay toi 0x18b7e4 (nhanh thoat cua ham goc) va doc w2.
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

    static final int INT4 = 0x78;                    // int thu 4 @0x72f78c (cua T9): cave khong duoc doc/ghi
    static byte[] coefBytes;

    static void setCoef(int dmg, int def, int exp) {
        be.mem_write(bss, le(dmg));
        be.mem_write(bss + 4, le(def));
        be.mem_write(bss + 8, le(exp));
        be.mem_write(bss + 12, le(INT4));
        coefBytes = be.mem_read(bss, 16);
    }

    /** Cave khong duoc ghi vao vung he so (4 int). */
    static boolean coefIntact() {
        return java.util.Arrays.equals(coefBytes, be.mem_read(bss, 16));
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

    static final String[] GN = {"A (CHAR_AddDamage)", "B (AddExperience)", "C (AddExpFixed)", "khac"};
    static final int[] gPass = new int[4], gFail = new int[4], gOut = new int[4];
    static int grp;                                  // nhom dang chay: 0 = A, 1 = B, 2 = C, 3 = khac
    static boolean quiet;                            // true: ca DAT chi vao log day du, khong in ra man hinh
    static boolean noCount;                          // true: bang ca bien (chay lai cac ca da dem): khong cong vao so ca DAT
    static java.io.PrintStream full;                 // log day du (moi ca)

    /** ok = null: ca ngoai mien (khong dat toi duoc trong game), chi ghi nhan. */
    static void report(String name, Boolean ok, String detail) {
        String tag;
        if (ok == null) { gOut[grp]++; tag = "NGM  "; }
        else if (ok) { if (!noCount) { pass++; gPass[grp]++; } tag = "DAT  "; }
        else { fail++; gFail[grp]++; tag = "LOI  "; }
        String line = tag + name + " " + detail;
        if (full != null) full.println(line);
        if (!(quiet && "DAT  ".equals(tag))) System.out.println(line);
    }

    /** Mo hinh w25 cua ham goc (0x1c7864..0x1c7880): w8 = (int32)(w19*110); w25 = trunc(w8 * 0x51eb851f >> 37) + bit dau. */
    static int modelW25(int w19) {
        int w8 = w19 * 110;
        long p = (long) w8 * 0x51eb851fL;
        return (int) ((p >> 37) + (p >>> 63));
    }

    // ------------------------------------------------------------ A
    static void caseA(int dmg, int tf, int af, boolean hasAtt, int mdmg, int mdef) {
        grp = 0;
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
        long until = dmg < 1 ? base + 0x18b7e4 : base + 0x18b844;     // 0x18b844 = CHAR_AddLife (qua PLT 0x2aecc0): x0 = muc tieu, w1 = -sat thuong thuc nhan
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
        report(name, got == (int) want0 && coefIntact(), String.format("%s ket qua=%d mong doi=%d", extra, got, (int) want0));
    }

    // ------------------------------------------------------------ B
    static void caseB(int e, int mexp) {
        grp = 1;
        setCoef(0x66, 0x77, mexp);
        for (int i = 0; i <= 28; i++) wr(i, 0x2222222200000000L + i);
        wr(19, e & 0xffffffffL);
        wr(8, 0x1234);
        for (int i = 9; i <= 12; i++) wr(i, 0xDEAD0000L + i);
        wr(22, 0x22);
        be.reg_write(Arm64Const.UC_ARM64_REG_SP, stackTop - 0x80);
        String name = String.format("B EXP co so=%d Mexp=%d", e, mexp);
        int w19 = patched ? modelExp(e, mexp) : e;
        int w25 = modelW25(w19);                                    // lib goc khong doi: dung phep tinh 32 bit cua game (co the tran, nhu game goc)
        try {
            be.emu_start(base + 0x1c7860, base + 0x1c7884, 0, 0);
        } catch (Throwable t) {
            report(name, false, "NGOAI LE " + t);
            return;
        }
        // x22 (hieu cap) va w0 = 0 (`mov w0,wzr` @0x1c7868) phai nguyen / dung
        boolean ok = rw(19) == w19 && rw(25) == w25 && rd(22) == 0x22 && rd(0) == 0 && coefIntact();
        String detail = String.format("w19=%d (mong doi %d) w25=%d (mong doi %d)", rw(19), w19, rw(25), w25);
        // EXP < 0 khong the toi B: w19 >= 1 sau CAL_Calculate (cmp w0,#1 @0x1c7754), roi nhan he so 2..15 / 10 (>= 0) va (GetAttr(0x72)+100)/100.
        // Cave B nhan ca so am (giu dau, khong kep), nen chi ghi nhan.
        if (patched && e < 0 && valid(mexp)) report(name, null, detail + " [ngoai mien: EXP co so < 0 khong toi duoc @0x1c7860]");
        else report(name, ok, detail);
    }

    // ------------------------------------------------------------ C
    static void caseC(int e, int mexp) {
        grp = 2;
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
        boolean ok = rw(19) == w19 && rd(8) == 0xCAFE000000001000L && rw(0) == e && coefIntact();
        report(name, ok, String.format("w19=%d (mong doi %d) x8 %s w0 %s", rw(19), w19, rd(8) == 0xCAFE000000001000L ? "nguyen" : "BI DOI", rw(0) == e ? "nguyen" : "BI DOI"));
    }

    static void table(String title) {
        System.out.println("--- " + title);
    }

    public static void main(String[] a) throws Exception {
        File lib = new File(a[0]);
        patched = a[1].equals("patched");
        if (a.length > 2) full = new java.io.PrintStream(new java.io.FileOutputStream(a[2]), false, "UTF-8");
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
        quiet = true;
        grp = 3;
        // kiem vung he so ghi/doc duoc (trang cuoi cua .bss) va ban dau = 0
        byte[] init = be.mem_read(bss, 16);
        boolean zero = true;
        for (byte b : init) if (b != 0) zero = false;
        report("vung he so 0x72f780..0x72f790 map duoc va ban dau = 0", zero, "");
        be.mem_write(bss + 12, le(INT4));
        report("ghi/doc int thu 4 @0x72f78c trong cung trang", java.util.Arrays.equals(be.mem_read(bss + 12, 4), le(INT4)), "");

        int[] coefs = {0, 1, 2, 3, 10, 49, 50, 51, 100, -1, Integer.MIN_VALUE};
        int[] dmgs = {Integer.MIN_VALUE, -5, 0, 1, 2, 3, 49, 50, 51, 999, 19999, 333333, 499999, 999997, 999998, 999999, 1000000, 40000000, Integer.MAX_VALUE};
        int[] dmgsB = {-1, 0, 1, 2, 49, 50, 51, 1000, 19999, 20000, 999998, 999999, 1000000};      // cho to hop (Mdmg, Mdef) khac nhau
        // phe (muc tieu, ke tan cong, co ke tan cong): du 3 x 3 phe + ke tan cong NULL cho moi phe muc tieu
        int[][] pairs = new int[12][];
        int np = 0;
        for (int t = 0; t < 3; t++) {
            for (int f = 0; f < 3; f++) pairs[np++] = new int[] {t, f, 1};
            pairs[np++] = new int[] {t, 0, 0};
        }
        for (int[] pr : pairs) {
            // (1) Mdmg = Mdef = c, quet toan bo dmg
            for (int c : coefs) for (int d : dmgs) caseA(d, pr[0], pr[1], pr[2] == 1, c, c);
            // (2) moi to hop (Mdmg, Mdef) khac nhau, dmg bien
            for (int c1 : coefs) for (int c2 : coefs) for (int d : dmgsB) caseA(d, pr[0], pr[1], pr[2] == 1, c1, c2);
        }
        int[] exps = {Integer.MIN_VALUE, -1, 0, 1, 2, 3, 100, 5000, 335543, 335544, 335545, 16777213, 16777214, 16777215, 16777216, 16777217, 100000000, Integer.MAX_VALUE};
        for (int c : coefs) {
            for (int e : exps) {
                caseB(e, c);
                caseC(e, c);
            }
        }

        // ---- bang ca bien (in ra du ca DAT; khong cong lai vao so ca)
        quiet = false;
        noCount = true;
        System.out.println("=== BANG CA BIEN (" + a[1] + "): dmg/EXP vao -> ra ===");
        table("A: ke tan cong phe 0 -> muc tieu phe 1 (nhan Mdmg=50): quanh tran 999.999, nhan 2 so");
        for (int d : new int[] {0, 1, 2, 19999, 20000, 999998, 999999, 1000000}) caseA(d, 1, 0, true, 50, 50);
        table("A: ke tan cong phe 1 -> muc tieu phe 0 (chia Mdef=50): dmg nho, min 1");
        for (int d : new int[] {0, 1, 49, 50, 51, 100, 999999, 1000000}) caseA(d, 0, 1, true, 50, 50);
        table("A: dmg=1000, he so Mdmg=Mdef=h, ke tan cong phe 0 -> muc tieu phe 1 / ke tan cong phe 1 -> muc tieu phe 0");
        for (int h : new int[] {0, 1, 2, 3, 50, 51, -1}) {
            caseA(1000, 1, 0, true, h, h);
            caseA(1000, 0, 1, true, h, h);
        }
        table("A: dmg=1000, Mdmg=10, Mdef=10, du phe (muc tieu, ke tan cong)");
        for (int[] pr : pairs) caseA(1000, pr[0], pr[1], pr[2] == 1, 10, 10);
        table("B/C: EXP quanh tran 0xFFFFFF, Mexp=50 va Mexp=2");
        for (int h : new int[] {50, 2}) {
            for (int e : new int[] {0, 1, 335543, 335544, 335545, 16777214, 16777215, 16777216}) {
                caseB(e, h);
                caseC(e, h);
            }
        }
        table("B/C: EXP=1000, Mexp = 0, 1, 2, 3, 50, 51, -1");
        for (int h : new int[] {0, 1, 2, 3, 50, 51, -1}) {
            caseB(1000, h);
            caseC(1000, h);
        }
        noCount = false;

        System.out.println("=== TONG HOP (" + a[1] + ") ===");
        int tp = 0, tf = 0, tn = 0;
        for (int g = 0; g < 4; g++) {
            System.out.printf("  %-20s DAT=%d LOI=%d ngoai mien=%d%n", GN[g], gPass[g], gFail[g], gOut[g]);
            tp += gPass[g];
            tf += gFail[g];
            tn += gOut[g];
        }
        System.out.printf("KET QUA %s: tong %d ca, DAT=%d LOI=%d NGOAI_MIEN(ghi nhan)=%d%n", a[1], tp + tf + tn, tp, tf, tn);
        System.out.println(tf == 0 ? "TONG KET (" + a[1] + "): TAT CA DAT" : "TONG KET (" + a[1] + "): CO LOI");
        if (full != null) full.close();
        System.exit(tf == 0 ? 0 : 1);
    }
}
