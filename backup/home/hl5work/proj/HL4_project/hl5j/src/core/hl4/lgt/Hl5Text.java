package hl4.lgt;

import hl4.core.VnFont;
import hl4.cpu.Cpu;
import hl4.cpu.Memory;

/**
 * HL5 Vietnamese text (BAO-CAO B11). Vietnamese words are stored in the game data as 2-byte codes
 * (lead 0xC9.., trail 0xA1.., index into dict.txt) so every "byte >= 0x80 = 2-byte character" rule
 * of the game's text module keeps working. Two things are replaced:
 * <ul>
 * <li>the glyph width: the text module reads the fixed Hangul width (font byte +0x46) at five
 * places; each read is patched to a call that returns the real pixel width of the word;</li>
 * <li>the glyph draw 0x4f60 (called only from 0x5be4): a word is drawn with the VnFont through the
 * game's own put-pixel 0x4c84 (clip, colour and blend stay the game's).</li>
 * </ul>
 * The game's layout (line breaks ';', auto-wrap, alignment, colour codes) is left untouched.
 */
final class Hl5Text {
    static final int LEAD0 = 0xC9, TRAIL0 = 0xA1;
    static final int STUB_W = 0x11f000, STUB_G = 0x11f008;
    static final int GLYPH = 0x4f60, GLYPH_CALL = 0x5be4, PUTPIXEL = 0x4c84, CUR_FONT = 0x15000dc;
    /** width read sites: "adds r3, #0x46; ldrb r3, [r3]" (r3 = font) */
    static final int[] SITES = {0x74c7e, 0x74da4, 0x7551e, 0x757c8, 0x75888};

    private final LgtRuntime rt;
    private final Cpu cpu;
    private final Memory mem;
    private final VnFont font;
    private final String[] dict;
    private final int[] width;
    private final int spaceW, yOff;
    private final int[] pp = new int[4];

    Hl5Text(LgtRuntime rt, VnFont font, String[] dict) {
        this(rt, font, null, dict);
    }

    Hl5Text(LgtRuntime rt, VnFont font, VnFont small, String[] dict) {
        this.rt = rt;
        this.small = small;
        yOffS = small != null ? 9 - small.baseline() : 0;
        this.cpu = rt.cpu;
        this.mem = rt.mem;
        this.font = font;
        this.dict = dict;
        width = new int[dict.length];
        int sw = font.advance(' ');
        spaceW = sw > 0 ? sw : 4;
        // Hangul glyphs are 11 rows with the baseline near row 9; put the VnFont baseline there
        yOff = 9 - font.baseline();
        mode = new byte[dict.length];
        for (int i = 0; i < dict.length; i++) {
            String d = dict[i];
            if (d.length() > 0 && (d.charAt(0) == TIGHT || d.charAt(0) == SMALL)) {
                mode[i] = (byte) (d.charAt(0) == SMALL && small != null ? 2 : 1);
                dict[i] = d.substring(1);
            }
            width[i] = measure(dict[i], mode[i]);
        }
    }

    /**
     * dict prefixes of compact entries (build_vi5.py uses them for text that would not fit its box):
     * TIGHT = 15 px font, 1 px less per letter, 3 px spaces; SMALL = 10 px font, 1 px less per letter, 3 px spaces.
     */
    static final char TIGHT = '\u0001', SMALL = '\u0002';
    private final byte[] mode;
    private final VnFont small;
    private final int yOffS;

    private int adv(char c, int m) {
        if (c == ' ') return m != 0 ? 3 : spaceW;
        if (m == 2) { int a = small.advance(c); return a > 2 ? a - 1 : a; }
        int a = font.advance(c);
        return m == 1 && a > 2 ? a - 1 : a;
    }

    private int measure(String s, int m) {
        int w = 0;
        for (int i = 0; i < s.length(); i++) {
            int a = adv(s.charAt(i), m);
            if (a > 0) w += a;
        }
        return w;
    }

    void install() {
        for (int i = 0; i < SITES.length; i++) {
            int s = SITES[i];
            if (mem.read16(s) != 0x3346 || mem.read16(s + 2) != 0x781b)
                throw new RuntimeException("HL5 text: unexpected code at " + Integer.toHexString(s));
        }
        cpu.makeStub(STUB_W, (LgtRuntime.CAT_HLE << 16) | 96);
        cpu.makeStub(STUB_G, (LgtRuntime.CAT_HLE << 16) | 97);
        for (int i = 0; i < SITES.length; i++) rt.patchBL(SITES[i], STUB_W);
        rt.patchBL(GLYPH_CALL, STUB_G);
    }

    /** code index of the 2-byte character at p, or -1 */
    private int code(int p) {
        int c = mem.read8(p);
        if (c < LEAD0) return -1;
        int c2 = mem.read8(p + 1);
        if (c2 < TRAIL0) return -1;
        int k = (c - LEAD0) * 94 + (c2 - TRAIL0);
        return k < dict.length ? k : -1;
    }

    /** HLE 96: width read at one of SITES (return address identifies the site). */
    void widthSite() {
        int site = (cpu.r[14] & ~1) - 4;
        int sp = cpu.r[13];
        int p;
        switch (site) {
            case 0x74c7e: p = mem.read32(sp + 0x30) + mem.read32(sp + 8); break;
            case 0x74da4: p = mem.read32(sp + 0x2c) + mem.read32(sp + 0x10); break;
            case 0x7551e: p = mem.read32(sp + 0x40) + mem.read32(sp + 0x48); break;
            case 0x757c8: p = mem.read32(sp + 0xa4) + mem.read32(sp + 0x80); break;
            default: p = mem.read32(sp + 0xa4) + mem.read32(sp + 0x80) - 2; break;
        }
        int k = code(p);
        if (k < 0) {
            cpu.r[3] = mem.read8(cpu.r[3] + 0x46);
        } else {
            int w = width[k] - 1;
            cpu.r[3] = w < 0 ? 0 : w > 127 ? 127 : w;
        }
    }

    /** HLE 97: glyph draw (x, y, str, len) in place of 0x4f60. */
    void glyph() {
        int x = cpu.r[0], y = cpu.r[1], str = cpu.r[2];
        int k = code(str);
        if (k < 0) {
            cpu.r[0] = cpu.call(GLYPH | 1, new int[]{x, y, str, cpu.r[3]});
            return;
        }
        int cur = mem.read32(CUR_FONT);
        pp[2] = mem.read32(cur + 0x40);
        pp[3] = mem.read32(cur + 0x24);
        String s = dict[k];
        int t = mode[k];
        VnFont f = t == 2 ? small : font;
        int yo = t == 2 ? yOffS : yOff;
        int x0 = x;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                x += adv(c, t);
                continue;
            }
            int[] px = f.pixels(c);
            if (px == null) continue;
            for (int j = 0; j < px.length; j++) {
                pp[0] = x + (px[j] & 0xFF);
                pp[1] = y + yo + (px[j] >> 8);
                cpu.call(PUTPIXEL | 1, pp);
            }
            x += adv(c, t);
        }
        cpu.r[0] = x - x0;
    }
}
