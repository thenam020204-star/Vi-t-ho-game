#!/usr/bin/env python3
"""HL5 .mgr image bank: u32 count, then per image u32 size + [u8 fmt][u8 ncolors][u16 w][u16 h][u16 pal[ncolors]][pixels]
fmt low nibble = bpp (4 or 8); 4bpp rows padded to whole bytes, high nibble first(?). colour 0xF81F = transparent."""
import struct, sys, os
from PIL import Image

def parse(b):
    n = struct.unpack_from('<I', b, 0)[0]; p = 4; out = []
    for i in range(n):
        sz = struct.unpack_from('<I', b, p)[0]; out.append((p + 4, b[p + 4:p + 4 + sz])); p += 4 + sz
    assert p == len(b), (p, len(b))
    return out

def rgb(c):
    return ((c >> 11) * 255 // 31, ((c >> 5) & 63) * 255 // 63, (c & 31) * 255 // 31)

def decode(d):
    fmt, nc, w, h = struct.unpack_from('<BBHH', d, 0)
    if nc == 0: nc = 256
    pal = struct.unpack_from('<%dH' % nc, d, 6); px = d[6 + 2 * nc:]
    bpp = fmt & 15
    im = Image.new('RGBA', (w, h))
    rb = (w * bpp + 7) // 8
    for y in range(h):
        for x in range(w):
            if bpp == 8: i = px[y * w + x]
            else:
                v = px[y * rb + x // 2]; i = (v >> 4) if x % 2 == 0 else (v & 15)
            c = pal[i] if i < len(pal) else 0
            im.putpixel((x, y), (0, 0, 0, 0) if c == 0xF81F else rgb(c) + (255,))
    return fmt, pal, im

if __name__ == '__main__':
    src, out = sys.argv[1], sys.argv[2]
    os.makedirs(out, exist_ok=True)
    for i, (o, d) in enumerate(parse(open(src, 'rb').read())):
        fmt, pal, im = decode(d)
        im.save(os.path.join(out, '%03d.png' % i))
        print(i, 'fmt %02x colors %d %dx%d' % (fmt, len(pal), im.width, im.height))
