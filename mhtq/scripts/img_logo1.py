#!/usr/bin/env python3 -I
"""Thay /logo1.png bang fonts_user/logo_namp.png (240x320): PNG 8-bit palette, khong interlace, khong chunk phu.
Dau ra: work/build/img_overlay/logo1.png"""
import os, io, struct, zlib
from PIL import Image
ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
im = Image.open(os.path.join(ROOT, 'fonts_user', 'logo_namp.png'))
assert im.size == (240, 320), im.size
if im.mode != 'P':
    rgb = Image.new('RGB', im.size, (255, 255, 255))
    rgb.paste(im.convert('RGBA'), mask=im.convert('RGBA').split()[3])
    n = len(rgb.getcolors(1 << 24) or [])
    im = rgb.quantize(256, method=Image.MEDIANCUT, dither=Image.FLOYDSTEINBERG) if n > 256 else rgb.convert('P', palette=Image.ADAPTIVE)
else:
    im = im.convert('RGB').convert('P', palette=Image.ADAPTIVE) if im.info.get('transparency') is not None else im
buf = io.BytesIO(); im.save(buf, 'PNG', optimize=True, bits=8)
data = buf.getvalue()
# bo chunk phu: chi giu IHDR PLTE tRNS IDAT IEND
out = data[:8]; i = 8; kinds = []
while i < len(data):
    n = struct.unpack('>I', data[i:i+4])[0]; k = data[i+4:i+8]
    if k in (b'IHDR', b'PLTE', b'tRNS', b'IDAT', b'IEND'): out += data[i:i+12+n]; kinds.append(k.decode())
    i += 12 + n
assert out[24] == 8 and out[25] == 3 and out[28] == 0, 'IHDR'
d = os.path.join(ROOT, 'work', 'build', 'img_overlay'); os.makedirs(d, exist_ok=True)
open(os.path.join(d, 'logo1.png'), 'wb').write(out)
print('OK', len(out), 'bytes', kinds[:4], 'palette', len(im.getpalette())//3)
