#!/usr/bin/env python3
"""Generate a self-contained fontcus.java (font data embedded in the constant pool,
no /font.bin resource) from a font.bin. Public API stays binary-identical."""
import sys, fontbin

def jstr_ascii(s):
    """Java string literal, pure ASCII source, no \\u escapes for control chars."""
    o = []
    for ch in s:
        c = ord(ch)
        # NEVER emit a literal backslash pair: "\\\\" followed by "\\uXXXX" would make the
        # javac unicode-escape preprocessor see an odd number of backslashes and fail.
        if ch == '"':    o.append('\\042')
        elif ch == '\\': o.append('\\134')
        elif 0x20 <= c <= 0x7e: o.append(ch)
        elif c <= 0xff:  o.append('\\%03o' % c)          # octal: lexer-level, LF/CR safe
        else:            o.append('\\u%04x' % c)          # BMP, never a surrogate here
    return '"' + ''.join(o) + '"'

def jstr_bytes(bs):
    return '"' + ''.join('\\%03o' % b for b in bs) + '"'

def chunk(lit, per=90):
    """split a long literal into concatenated pieces javac folds at compile time"""
    body = lit[1:-1]
    out, i, parts = [], 0, []
    # never split in the middle of an escape
    cur = ''
    j = 0
    while j < len(body):
        if body[j] == '\\':
            n = 2
            if j+1 < len(body) and body[j+1] in '01234567': n = 4
            elif j+1 < len(body) and body[j+1] == 'u': n = 6
            tok = body[j:j+n]; j += n
        else:
            tok = body[j]; j += 1
        if len(cur) + len(tok) > per:
            parts.append(cur); cur = ''
        cur += tok
    if cur: parts.append(cur)
    return parts

def emit_field(name, lit, indent='    '):
    parts = chunk(lit)
    lines = ['%sprivate static final String %s =' % (indent, name)]
    for k, p in enumerate(parts):
        sep = ';' if k == len(parts)-1 else ' +'
        lines.append('%s    "%s"%s' % (indent, p, sep))
    return '\n'.join(lines)

def gen(binpath, out, build_charindex=True, cache=6):
    f = fontbin.decode(binpath)
    chars, h, widths, W = f['chars'], f['height'], f['widths'], f['atlasW']
    # re-pack bitmap exactly as the container stores it
    packed = bytearray(); acc = 0; nb = 0
    for y in range(h):
        for x in range(W):
            if f['rows'][y][x]: acc |= 1 << nb
            nb += 1
            if nb == 8: packed.append(acc); acc = 0; nb = 0
    if nb: packed.append(acc)
    assert len(packed) == (h*W+7)//8
    for w in widths:
        assert 0 <= w <= 255, "width out of unsigned byte range: %d" % w
    assert 0 <= h <= 127, "fontHeight must fit a signed byte"

    src = TEMPLATE
    src = src.replace('@NCHARS@',  str(len(chars)))
    src = src.replace('@HEIGHT@',  str(h))
    src = src.replace('@ATLASW@',  str(W))
    src = src.replace('@NBITS@',   str(len(packed)))
    src = src.replace('@CACHE@',   str(cache))
    src = src.replace('@CHARIDX@', 'true' if build_charindex else 'false')
    src = src.replace('@CHARSET_FIELD@', emit_field('CHARSET', jstr_ascii(chars)))
    src = src.replace('@WIDTHS_FIELD@',  emit_field('WIDTHS',  jstr_bytes(bytes(widths))))
    src = src.replace('@BITS_FIELD@',    emit_field('BITS',    jstr_bytes(bytes(packed))))
    open(out, 'w', encoding='ascii').write(src)
    return dict(chars=len(chars), h=h, atlasW=W, bits=len(packed))

TEMPLATE = r'''// fontcus.java - self-contained Vietnamese bitmap font for J2ME (CLDC 1.0+ / MIDP 2.0)
//
// GENERATED FILE - do not hand-edit the CHARSET/WIDTHS/BITS constants.
// Regenerate with:  python3 gen_class.py font.bin fontcus.java
//
// Drop-in replacement for the original fontcus.class:
//   * every public field and method keeps the exact same name and descriptor,
//     so already-compiled callers link without recompiling;
//   * rendering output is bit-for-bit identical to the original;
//   * the /font.bin resource is NO LONGER NEEDED - the font lives in this class.
//     Injecting the font into a game is now "copy one .class into the JAR".
//
// Font: @NCHARS@ glyphs, @HEIGHT@ px tall, atlas strip @ATLASW@ px wide, 1 bpp (@NBITS@ bytes packed).
//
// Runtime memory (unchanged from the original, sized for a real KVM):
//   glyphBits  byte[@HEIGHT@][@ATLASW@]   ~ @HEIGHT@ * @ATLASW@ bytes
//   charMap    short[8448]      ~ 16.5 KB  (public - direct O(1) char -> glyph index)
//   charIndex  Hashtable        ~ 11 KB    (public, legacy; set BUILD_CHARINDEX=false to drop it)
//   atlas cache CACHE * (atlasW * fontHeight * 4) bytes of Image data, lazily, per colour.
//     CACHE=@CACHE@ => up to ~@CACHE@ x 80 KB of image memory. Lower CACHE on tight-heap devices.
//     Every allocation there is guarded: on Throwable it falls back to per-char drawRGB.

import java.util.Hashtable;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class fontcus {

    // ---- public API (binary-compatible with the original class) ----
    public static byte fontHeight;
    public static int unusedNhungW;
    public static Hashtable charIndex;
    public static int[] glyphW;
    public static byte[][] glyphBits;
    public static int[] glyphOff;
    public static short[] charMap;
    public static int cacheN;
    public static int atlasW;
    public static boolean atlasOk;

    // ---- tunables ----
    private static final int MAPLEN = 8448;   // 0x2100: charMap covers U+0000..U+20FF
    private static final int CACHE = @CACHE@;      // coloured atlas images kept alive (LRU)
    private static final boolean BUILD_CHARINDEX = @CHARIDX@;

    private static final int[] cacheCol = new int[CACHE];
    private static final Image[] cacheImg = new Image[CACHE];

    // ---- embedded font data ----
    // CHARSET: glyph order; index i in this string == glyph index i.
@CHARSET_FIELD@

    // WIDTHS: advance width of glyph i, one char per glyph, value 0..255.
@WIDTHS_FIELD@

    // BITS: 1 bpp atlas, row-major, row length = sum(WIDTHS), LSB first,
    //       bit stream continuous across rows (no per-row padding).
@BITS_FIELD@

    public fontcus() {
    }

    static {
        atlasOk = true;
        charIndex = new Hashtable();
        try {
            String cs = CHARSET;
            int n = cs.length();
            byte h = (byte) @HEIGHT@;
            fontHeight = h;

            int[] gw = new int[n];
            int[] go = new int[n];
            short[] map = new short[MAPLEN];
            for (int i = 0; i < MAPLEN; i++) {
                map[i] = -1;
            }

            int off = 0;
            for (int i = 0; i < n; i++) {
                int w = WIDTHS.charAt(i);
                gw[i] = w;
                go[i] = off;
                int c = cs.charAt(i);
                if (c < MAPLEN) {
                    map[c] = (short) i;
                }
                if (BUILD_CHARINDEX) {
                    charIndex.put(new Integer(c), new Integer(i));
                }
                off += w;
            }
            glyphW = gw;
            glyphOff = go;
            charMap = map;
            atlasW = off;

            byte[][] rows = new byte[h][];
            for (int y = 0; y < h; y++) {
                rows[y] = new byte[off];
            }
            int acc = 0;
            int bit = 7;
            int p = 0;
            String bits = BITS;
            for (int y = 0; y < h; y++) {
                byte[] row = rows[y];
                for (int x = 0; x < off; x++) {
                    if (++bit >= 8) {
                        acc = bits.charAt(p++);
                        bit = 0;
                    }
                    if ((acc & 1) != 0) {
                        row[x] = 1;
                    }
                    acc >>= 1;
                }
            }
            glyphBits = rows;

            unusedNhungW = strWidth("nhung");
        } catch (Throwable t) {
            // embedded data cannot fail to parse; stay defensive so a broken font
            // can never take the MIDlet down with an ExceptionInInitializerError.
            atlasOk = false;
        }
    }

    /** glyph index of a code point, or -1 when the font has no glyph for it. */
    private static int idxOf(int c) {
        return (c >= 0 && c < MAPLEN) ? charMap[c] : -1;
    }

    /** the whole atlas strip tinted with one colour, MRU-cached. null => caller falls back. */
    private static Image atlas(int rgb) {
        for (int i = 0; i < cacheN; i++) {
            if (cacheCol[i] != rgb) {
                continue;
            }
            Image hit = cacheImg[i];
            if (i != 0) {
                System.arraycopy(cacheCol, 0, cacheCol, 1, i);
                System.arraycopy(cacheImg, 0, cacheImg, 1, i);
                cacheCol[0] = rgb;
                cacheImg[0] = hit;
            }
            return hit;
        }
        if (!atlasOk || glyphBits == null) {
            return null;
        }
        Image img;
        int h = fontHeight;
        int w = atlasW;
        try {
            int[] px = new int[w * h];
            int on = 0xFF000000 | rgb;
            for (int y = 0; y < h; y++) {
                byte[] row = glyphBits[y];
                int base = y * w;
                for (int x = 0; x < w; x++) {
                    if (row[x] != 0) {
                        px[base + x] = on;
                    }
                }
            }
            img = Image.createRGBImage(px, w, h, true);
        } catch (Throwable t) {
            atlasOk = false;   // out of memory / no createRGBImage: never try again
            return null;
        }
        if (img == null) {
            atlasOk = false;
            return null;
        }
        int keep = (cacheN < CACHE) ? cacheN : (CACHE - 1);
        System.arraycopy(cacheCol, 0, cacheCol, 1, keep);
        System.arraycopy(cacheImg, 0, cacheImg, 1, keep);
        cacheCol[0] = rgb;
        cacheImg[0] = img;
        if (cacheN < CACHE) {
            cacheN++;
        }
        return img;
    }

    /** MIDP2.0 fallback: build one glyph as ARGB and blit it. Allocates - atlas path is preferred. */
    private static int drawCharRGB(Graphics g, int idx, int x, int y) {
        int w = glyphW[idx];
        int off = glyphOff[idx];
        int h = fontHeight;
        int[] px = new int[w * h];
        int on = 0xFF000000 | g.getColor();
        for (int r = 0; r < h; r++) {
            byte[] row = glyphBits[r];
            int base = r * w;
            for (int c = 0; c < w; c++) {
                if (row[c + off] != 0) {
                    px[base + c] = on;
                }
            }
        }
        g.drawRGB(px, 0, w, x, y, w, h, true);
        return w;
    }

    public static boolean XuongDong(int ch, int a, int b, int c) {
        if (a > b) {
            return true;
        }
        if (ch != 32) {
            return false;
        }
        return b - c < 35;
    }

    public static boolean XuongDong2(String s, int i, int penX, int softLimit, int hardLimit) {
        if (s == null) {
            return false;
        }
        int n = s.length();
        if (i >= 0 && i < n) {
            char c = s.charAt(i);
            if (doChu(penX, c) > hardLimit) {
                return true;
            }
            if (penX > softLimit && i > 0 && c != ' ' && s.charAt(i - 1) == ' ') {
                while (i < n) {
                    char c2 = s.charAt(i);
                    if (c2 == ' ') {
                        break;
                    }
                    penX = doChu(penX, c2);
                    if (penX > hardLimit) {
                        return true;
                    }
                    i++;
                }
            }
        }
        return false;
    }

    public static final int drawChar(Graphics g, char c, int x, int y) {
        y -= 3;
        int idx = idxOf(c);
        if (idx < 0) {
            return 0;
        }
        Image img = atlas(g.getColor() & 0xFFFFFF);
        if (img == null) {
            return drawCharRGB(g, idx, x, y);
        }
        int w = glyphW[idx];
        g.drawRegion(img, glyphOff[idx], 0, w, fontHeight, 0, x, y, 20);
        return w;
    }

    public static short strWidth(String s) {
        if (s == null) {
            return 0;
        }
        short[] map = charMap;
        int[] gw = glyphW;
        if (map == null || gw == null) {
            return 0;
        }
        int n = s.length();
        int total = 0;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c >= MAPLEN) {
                continue;
            }
            short idx = map[c];
            if (idx < 0) {
                continue;
            }
            total += gw[idx];
        }
        return (short) total;
    }

    public static final void drawString(Graphics g, String s, int x, int y) {
        y -= 3;
        if (s == null) {
            return;
        }
        int n = s.length();
        if (n == 0) {
            return;
        }
        short[] map = charMap;
        int[] gw = glyphW;
        int[] go = glyphOff;
        if (map == null) {
            return;
        }
        Image img = atlas(g.getColor() & 0xFFFFFF);
        byte h = fontHeight;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c >= MAPLEN) {
                continue;
            }
            short idx = map[c];
            if (idx < 0) {
                continue;
            }
            if (img != null) {
                int w = gw[idx];
                g.drawRegion(img, go[idx], 0, w, h, 0, x, y, 20);
                x += w;
            } else {
                x += drawCharRGB(g, idx, x, y);
            }
        }
    }

    public static short charWidth(char c) {
        int idx = idxOf(c);
        return (idx >= 0) ? (short) glyphW[idx] : (short) 0;
    }

    /** advance the pen: penX + width(c), or penX + 12 for a character the font lacks. */
    public static final int doChu(int penX, int c) {
        int idx = idxOf(c);
        return (idx >= 0) ? penX + glyphW[idx] : penX + 12;
    }

    public static void drawUniversal(Graphics g, char c, int x, int y, int anchor) {
        drawUniversal(g, String.valueOf(c), x, y, anchor);
    }

    public static void drawUniversal(Graphics g, String s, int x, int y, int anchor) {
        if (s == null) {
            return;
        }
        if ((anchor & 9) != 0) {            // HCENTER | RIGHT
            short w = strWidth(s);
            if ((anchor & 1) != 0) {
                x -= w >> 1;
            }
            if ((anchor & 8) != 0) {
                x -= w;
            }
        }
        if ((anchor & 2) != 0) {            // VCENTER
            y -= 8;
        }
        if ((anchor & 32) != 0) {           // BOTTOM
            y -= 16;
        }
        drawString(g, s, x, y);
    }
}
'''

if __name__ == '__main__':
    inp = sys.argv[1] if len(sys.argv) > 1 else 'in/font.bin'
    out = sys.argv[2] if len(sys.argv) > 2 else 'build/src/fontcus.java'
    print(gen(inp, out))
