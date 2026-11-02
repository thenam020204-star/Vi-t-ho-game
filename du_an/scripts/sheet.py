import sys
from PIL import Image, ImageDraw
files = sys.argv[2:]; out = sys.argv[1]
ims = []
for f in files:
    im = Image.open(f).convert('RGBA')
    bg = Image.new('RGBA', im.size, (255, 0, 255, 255)); bg.alpha_composite(im)
    ims.append((f.split('/')[-1], bg))
W = 1000; x = y = 0; rowh = 0; pos = []
for n, im in ims:
    if x + im.width > W: x = 0; y += rowh + 14; rowh = 0
    pos.append((n, im, x, y)); x += im.width + 6; rowh = max(rowh, im.height)
sh = Image.new('RGB', (W, y + rowh + 14), (40, 40, 40)); d = ImageDraw.Draw(sh)
for n, im, x, y in pos:
    sh.paste(im.convert('RGB'), (x, y + 12)); d.text((x, y), n, fill=(255, 255, 0))
sh.save(out)
