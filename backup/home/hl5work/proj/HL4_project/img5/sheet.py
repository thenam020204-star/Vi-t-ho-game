#!/usr/bin/env python3
"""contact sheet of every image in .mgr files: sheet.py out.png file.mgr..."""
import sys
from PIL import Image, ImageDraw
from mgr import parse, decode
out = sys.argv[1]; tiles = []
for f in sys.argv[2:]:
    for i, (o, d) in enumerate(parse(open(f, 'rb').read())):
        try: im = decode(d)[2]
        except Exception as e: continue
        tiles.append(('%s#%d' % (f.split('/')[-1].replace('.mgr', ''), i), im))
W = 1400; x = y = 0; rowh = 0; pos = []
for name, im in tiles:
    w, h = max(im.width, 60), im.height + 10
    if x + w > W: x = 0; y += rowh + 2; rowh = 0
    pos.append((x, y)); x += w + 4; rowh = max(rowh, h)
o = Image.new('RGBA', (W, y + rowh + 2), (70, 70, 110, 255)); dr = ImageDraw.Draw(o)
for (name, im), (x, y) in zip(tiles, pos):
    o.paste(im, (x, y + 10), im); dr.text((x, y), name, fill='yellow')
o.save(out); print(out, o.size, len(tiles))
