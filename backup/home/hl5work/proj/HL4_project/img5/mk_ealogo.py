#!/usr/bin/env python3
"""Replace the publisher logo (res/c/sp/imgcom/ealogo.mgr, 3 frames drawn centred, fade-in) with a 240x320 picture.
usage: mk_ealogo.py <picture.png> <out ealogo.mgr>"""
import sys, struct
from PIL import Image, ImageEnhance

def enc8(im):
    """RGB image -> mgr entry, 8 bpp, <=255 colours, never 0xF81F"""
    q = im.convert('RGB').quantize(colors=255, method=Image.Quantize.MEDIANCUT, dither=Image.Dither.FLOYDSTEINBERG)
    pal = q.getpalette()[:255 * 3]
    n = len(set(q.getdata()))
    n = max(q.getdata()) + 1
    cols = []
    for i in range(n):
        r, g, b = pal[3 * i:3 * i + 3]
        c = ((r >> 3) << 11) | ((g >> 2) << 5) | (b >> 3)
        if c == 0xF81F: c = 0xF81E
        cols.append(c)
    w, h = q.size
    d = struct.pack('<BBHH', 0x18, n, w, h) + struct.pack('<%dH' % n, *cols) + bytes(q.getdata())
    return d

src = Image.open(sys.argv[1]).convert('RGB')
assert src.size == (240, 320), src.size
frames = [ImageEnhance.Brightness(src).enhance(k) for k in (0.35, 0.7, 1.0)]
out = struct.pack('<I', 3)
for f in frames:
    d = enc8(f); out += struct.pack('<I', len(d)) + d
open(sys.argv[2], 'wb').write(out)
print('ealogo.mgr', len(out), 'bytes')
