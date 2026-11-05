package hl4.lgt;

import hl4.cpu.Cpu;
import hl4.cpu.Memory;

/**
 * Offline fixes for HL5 (code patches, installed before start):
 * <ul>
 * <li>Revive on game over: with no revive item the game buys one from the server (op 5) and fails with a
 * network error. The "no item" branch at 0x4bdf0 now jumps to 0x4bdbe (the item path after the item removal), so the revive is free.</li>
 * <li>Main menu without the network items (창고접속, 업데이트, 게임문의): the 3x3 grid becomes 2x3, grid
 * position p shows item ITEMS[p]. The selection byte [menu+0xe] still holds the item index, so the
 * game's OK handler is unchanged; navigation (UP/DOWN/LEFT/RIGHT) is done in Java.</li>
 * </ul>
 */
final class Hl5Offline {
    static final int[] ITEMS = {0, 1, 4, 5, 6, 8};
    static final int STUB_MAP = 0x11f010, STUB_NAV = 0x11f018;
    static final int SITE_SEL = 0x5025c, SITE_TXT1 = 0x5036c, SITE_TXT2 = 0x504c4, SITE_NAV = 0x512e0;
    static final int NAV_LIT = 0x5154c, NAV_EXIT = 0x5153a;
    static final int T_UP = 0x512ea, T_DOWN = 0x51316, T_LEFT = 0x51342, T_RIGHT = 0x5137c;

    static boolean DEBUG = System.getProperty("hl5.navlog") != null;
    private final LgtRuntime rt;
    private final Cpu cpu;
    private final Memory mem;

    Hl5Offline(LgtRuntime rt) {
        this.rt = rt;
        this.cpu = rt.cpu;
        this.mem = rt.mem;
    }

    private void expect(int a, int v) {
        if (mem.read16(a) != v) throw new RuntimeException("HL5 offline: unexpected code at " + Integer.toHexString(a));
    }

    void install() {
        expect(0x4bdf0, 0x4a1f);
        expect(0x50194, 0x3b63);
        expect(0x501ba, 0x2b02);
        expect(SITE_SEL, 0x18d3); expect(SITE_SEL + 2, 0x4299);
        expect(SITE_TXT1, 0x18d3); expect(SITE_TXT1 + 2, 0x33a6);
        expect(SITE_TXT2, 0x18d3); expect(SITE_TXT2 + 2, 0x33a6);
        expect(SITE_NAV, 0x008a); expect(SITE_NAV + 2, 0x4b9a);
        mem.write16(0x4bdf0, 0xE7E5); // b 0x4bdbe: skip only the item removal, then revive as with an item
        // main menu: 2 rows (cmp r3,#1), grid moved down half a row (subs r3,#0x46)
        mem.write16(0x501ba, 0x2B01);
        mem.write16(0x50194, 0x3B46);
        cpu.makeStub(STUB_MAP, (LgtRuntime.CAT_HLE << 16) | 102);
        cpu.makeStub(STUB_NAV, (LgtRuntime.CAT_HLE << 16) | 103);
        rt.patchBL(SITE_SEL, STUB_MAP);
        rt.patchBL(SITE_TXT1, STUB_MAP);
        rt.patchBL(SITE_TXT2, STUB_MAP);
        rt.patchBL(SITE_NAV, STUB_NAV);
    }

    private static int item(int pos) {
        return pos >= 0 && pos < ITEMS.length ? ITEMS[pos] : pos;
    }

    private static int pos(int item) {
        for (int i = 0; i < ITEMS.length; i++) if (ITEMS[i] == item) return i;
        return 0;
    }

    /** HLE 102: "adds r3,r2,r3" (r2 = row*3, r3 = col) + the next instruction, at the menu drawer sites. */
    void map() {
        int[] r = cpu.r;
        int site = (r[14] & ~1) - 4;
        int it = item(r[2] + r[3]);
        if (site == SITE_SEL) { // cmp r1, r3
            int a = r[1], b = it, d = a - b;
            cpu.n = d < 0;
            cpu.z = d == 0;
            cpu.c = (a & 0xffffffffL) >= (b & 0xffffffffL);
            cpu.v = ((a ^ b) & (a ^ d)) < 0;
            r[3] = it;
        } else { // adds r3, #0xa6 (flags are overwritten by the next instruction)
            int v = it + 0xa6;
            cpu.n = v < 0;
            cpu.z = v == 0;
            cpu.c = false;
            cpu.v = false;
            r[3] = v;
        }
    }

    /** HLE 103: main-menu key switch ("lsls r2,r1,#2; ldr r3,=table"); r1 = case index. */
    void nav() {
        int[] r = cpu.r;
        int table = mem.read32(NAV_LIT);
        int target = mem.read32(table + (r[1] << 2)) & ~1;
        if (DEBUG) rt.log("[Hl5Offline] nav case " + r[1] + " target " + Integer.toHexString(target) + " sel " + mem.read8(mem.read32(r[13] + 0x148) + 0xe));
        int obj = mem.read32(r[13] + 0x148);
        int p = pos(mem.read8(obj + 0xe)), row = p / 3, col = p % 3;
        boolean moved = true;
        if (target == T_UP || target == T_DOWN) row ^= 1;
        else if (target == T_LEFT) col = (col + 2) % 3;
        else if (target == T_RIGHT) col = (col + 1) % 3;
        else moved = false;
        if (moved) {
            mem.write8(obj + 0xe, item(row * 3 + col));
            r[14] = NAV_EXIT | 1;
            return;
        }
        // OK (and anything else): original code
        r[2] = r[1] << 2;
        r[3] = table;
        // r14 = 0x512e4 (continue with "adds r3, r2, r3")
    }
}
