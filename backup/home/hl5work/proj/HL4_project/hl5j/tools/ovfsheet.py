# ovfsheet.py <run dir> <out.png> [scale]: contact sheet of the overflow detector crops (ovf/NNNN.png)
import sys, os, glob
from PIL import Image, ImageDraw
d, out = sys.argv[1], sys.argv[2]; sc = int(sys.argv[3]) if len(sys.argv) > 3 else 2
ims = []
for f in sorted(glob.glob(os.path.join(d, 'ovf', '*.png'))):
    im = Image.open(f).convert('RGB'); im = im.resize((im.width * sc, im.height * sc), Image.NEAREST)
    ims.append((os.path.basename(f)[:-4], im))
W = 960; x = y = 0; rowh = 0; pos = []
for n, im in ims:
    if x + im.width > W: x = 0; y += rowh + 14; rowh = 0
    pos.append((x, y + 12)); x += im.width + 6; rowh = max(rowh, im.height)
H = y + rowh + 14
sheet = Image.new('RGB', (W, max(H, 20)), (60, 60, 60)); dr = ImageDraw.Draw(sheet)
for (n, im), (px, py) in zip(ims, pos):
    sheet.paste(im, (px, py)); dr.text((px, py - 12), str(int(n)), fill=(255, 255, 0))
sheet.save(out)
