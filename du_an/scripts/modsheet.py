"""Bang module cua 1 anim: python3 modsheet.py <anim.bin> <id> <anh> <ra.png> [scale]"""
import sys
sys.path.insert(0, 'scripts')
from PIL import Image, ImageDraw
import anim
an, _ = anim.parse(open(sys.argv[1], 'rb').read())
A = next(a for a in an if a['id'] == int(sys.argv[2]))
img = Image.open(sys.argv[3]).convert('RGBA')
sc = int(sys.argv[5]) if len(sys.argv) > 5 else 2
cells = []
for i, (y, x, w, h) in enumerate(A['mods']):
    c = Image.new('RGBA', (w, h), (255, 0, 255, 255)); c.alpha_composite(img.crop((x, y, x + w, y + h)))
    cells.append((i, c.resize((max(1, w * sc), max(1, h * sc)), Image.NEAREST)))
W = 1200; X = Y = 0; rh = 0; pos = []
for i, c in cells:
    cw = max(c.width, 28)
    if X + cw > W: X = 0; Y += rh + 12; rh = 0
    pos.append((i, c, X, Y)); X += cw + 4; rh = max(rh, c.height)
out = Image.new('RGB', (W, Y + rh + 12), (40, 40, 40)); d = ImageDraw.Draw(out)
for i, c, x, y in pos:
    out.paste(c.convert('RGB'), (x, y + 11)); d.text((x, y), str(i), fill=(255, 255, 0))
out.save(sys.argv[4])
