#!/usr/bin/env python3
"""Ảnh xem nhanh T8: logs/shots_T8/truoc_sau.png (trước | sau mọi ảnh đã sửa) và ngang_320.png."""
import os, glob
from PIL import Image, ImageDraw
ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
def flat(path, k):
    im = Image.open(path).convert('RGBA')
    im = im.resize((im.width * k, im.height * k), Image.NEAREST)
    bg = Image.new('RGB', im.size, (140, 200, 140)); bg.paste(im, (0, 0), im); return bg
rows = []
for f in sorted(glob.glob(os.path.join(ROOT, 'res_vi/Image/*.png'))):
    b = os.path.basename(f)
    if b == 'namp.png': continue
    w = Image.open(f).width
    k = max(1, min(4, 620 // w))
    rows.append((b, flat(os.path.join(ROOT, 'work/x/Image', b), k), flat(f, k)))
W = max(a.width + c.width for _, a, c in rows) + 30
H = sum(max(a.height, c.height) + 16 for _, a, c in rows)
out = Image.new('RGB', (W, H), (30, 30, 36)); d = ImageDraw.Draw(out); y = 0
for b, a, c in rows:
    d.text((2, y), b + '   (trước | sau)', fill=(255, 255, 120))
    out.paste(a, (0, y + 12)); out.paste(c, (a.width + 10, y + 12))
    y += max(a.height, c.height) + 16
out.save(os.path.join(ROOT, 'logs/shots_T8/truoc_sau.png'))
m = flat(os.path.join(ROOT, 'res_vi_320/Image/menu.png'), 1); n = flat(os.path.join(ROOT, 'res_vi_320/Image/namp.png'), 1)
o = Image.new('RGB', (m.width + n.width + 10, 240), (30, 30, 36)); o.paste(m, (0, 0)); o.paste(n, (m.width + 10, 0))
o.save(os.path.join(ROOT, 'logs/shots_T8/ngang_320.png'))
print(len(rows), 'ảnh', out.size)
