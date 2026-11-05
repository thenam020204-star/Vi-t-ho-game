#!/usr/bin/env python3
"""Edit the font as a picture.

  export:  font_png.py export font.bin sheet.png sheet.json
  import:  font_png.py import sheet.png sheet.json font_new.bin

The sheet is a 1-bit-per-pixel style PNG: black (any pixel darker than 128) = ink,
white = transparent. Glyphs sit side by side in charset order with a 1px red
separator column so you can see where each glyph ends. The JSON holds the charset,
the height and the per-glyph advance widths - edit it to add or remove characters
(add the matching glyph column in the PNG too, widths must add up).
"""
import json, sys
from PIL import Image
import fontbin

SEP = (255, 0, 0)

def export(binp, pngp, jsonp):
    f = fontbin.decode(binp)
    h, W, rows, widths, chars = f['height'], f['atlasW'], f['rows'], f['widths'], f['chars']
    n = len(chars)
    img = Image.new('RGB', (W + n, h), (255, 255, 255))
    px = img.load()
    src = 0; dst = 0
    for i in range(n):
        for x in range(widths[i]):
            for y in range(h):
                if rows[y][src + x]:
                    px[dst + x, y] = (0, 0, 0)
        src += widths[i]; dst += widths[i]
        for y in range(h):
            px[dst, y] = SEP
        dst += 1
    img.save(pngp)
    json.dump({'height': h, 'chars': chars, 'widths': widths,
               'note': 'glyph i occupies widths[i] columns, then 1 red separator column'},
              open(jsonp, 'w'), ensure_ascii=False, indent=1)
    print("exported %d glyphs, %dx%d sheet -> %s / %s" % (n, W + n, h, pngp, jsonp))

def imp(pngp, jsonp, binp):
    meta = json.load(open(jsonp))
    chars, widths, h = meta['chars'], meta['widths'], meta['height']
    assert len(chars) == len(widths), "chars/widths length mismatch (%d vs %d)" % (len(chars), len(widths))
    img = Image.open(pngp).convert('RGB'); px = img.load()
    need_w = sum(widths) + len(widths)
    assert img.size[1] == h, "sheet height %d != height %d" % (img.size[1], h)
    assert img.size[0] >= need_w, "sheet too narrow: %d < %d" % (img.size[0], need_w)
    W = sum(widths)
    rows = [bytearray(W) for _ in range(h)]
    src = 0; dst = 0
    for i, w in enumerate(widths):
        for x in range(w):
            for y in range(h):
                r, g, b = px[src + x, y]
                if (r, g, b) != SEP and (r + g + b) // 3 < 128:
                    rows[y][dst + x] = 1
        src += w + 1; dst += w
    data = fontbin.encode(chars, h, widths, rows)
    open(binp, 'wb').write(data)
    print("imported -> %s (%d bytes, %d glyphs, atlas %dx%d)" % (binp, len(data), len(chars), W, h))

if __name__ == '__main__':
    if sys.argv[1] == 'export': export(*sys.argv[2:5])
    elif sys.argv[1] == 'import': imp(*sys.argv[2:5])
    else: print(__doc__)
