#!/usr/bin/env python3
"""Replace images in an HL5 .mgr bank by PNGs (transparent pixels -> 0xF81F).
usage: mgr_replace.py <in.mgr> <out.mgr> <index>=<file.png> [...]
The new image keeps the bit depth of the old one (4 bpp: 15 colours, 8 bpp: 255 colours); size may change."""
import sys, struct
from PIL import Image
from mgr import parse

def encode(png, fmt):
    im = Image.open(png).convert('RGBA')
    w, h = im.size
    bpp = fmt & 15
    maxc = 15 if bpp == 4 else 254
    alpha = im.getchannel('A')
    rgb = Image.new('RGB', im.size, (0, 0, 0)); rgb.paste(im, mask=alpha)
    q = rgb.quantize(colors=maxc, method=Image.Quantize.MEDIANCUT, dither=Image.Dither.FLOYDSTEINBERG)
    pal = q.getpalette()
    idx = list(q.get_flattened_data()) if hasattr(q,'get_flattened_data') else list(q.getdata())
    a = list(alpha.get_flattened_data()) if hasattr(alpha,'get_flattened_data') else list(alpha.getdata())
    cols = [0xF81F]
    for i in range(len(pal) // 3):
        r, g, b = pal[3 * i:3 * i + 3]
        c = ((r >> 3) << 11) | ((g >> 2) << 5) | (b >> 3)
        cols.append(0xF81E if c == 0xF81F else c)
    px = [0 if a[k] < 128 else idx[k] + 1 for k in range(w * h)]
    used = max(px) + 1
    cols = cols[:used]
    if bpp == 8:
        data = bytes(px)
    else:
        rb = (w + 1) // 2; data = bytearray(rb * h)
        for y in range(h):
            for x in range(w):
                v = px[y * w + x]
                data[y * rb + x // 2] |= (v << 4) if x % 2 == 0 else v
        data = bytes(data)
    return struct.pack('<BBHH', fmt, used, w, h) + struct.pack('<%dH' % used, *cols) + data

src, dst = sys.argv[1], sys.argv[2]
ents = [d for (o, d) in parse(open(src, 'rb').read())]
for a in sys.argv[3:]:
    i, f = a.split('=', 1); i = int(i)
    ents[i] = encode(f, ents[i][0])
    print('image', i, '<-', f)
out = struct.pack('<I', len(ents))
for d in ents: out += struct.pack('<I', len(d)) + d
open(dst, 'wb').write(out)
