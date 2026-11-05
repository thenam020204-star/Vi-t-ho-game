# ovf_sheet_all.py <scratch> <out.png> [from] [to]: crops of the unique flagged texts (ovf_all.tsv), labelled with row/ID
import sys, os
from PIL import Image, ImageDraw
S, out = sys.argv[1], sys.argv[2]
rows = [l.rstrip('\n').split('\t') for l in open(os.path.join(S, 'ovf_all.tsv'), encoding='utf-8')][1:]
a = int(sys.argv[3]) if len(sys.argv) > 3 else 0; b = int(sys.argv[4]) if len(sys.argv) > 4 else len(rows)
ims = []
for i, r in enumerate(rows[a:b], a):
    run, n = r[6].split()[0].split('#')
    p = os.path.join(S, 'ovf_' + run, 'ovf', '%04d.png' % int(n))
    if not os.path.exists(p): continue
    im = Image.open(p).convert('RGB'); im = im.resize((im.width * 2, im.height * 2), Image.NEAREST)
    ims.append(('%d %s' % (i, r[0]), im))
W = 1000; x = y = 0; rowh = 0; pos = []
for n, im in ims:
    if x + im.width > W: x = 0; y += rowh + 14; rowh = 0
    pos.append((x, y + 12)); x += im.width + 6; rowh = max(rowh, im.height)
sheet = Image.new('RGB', (W, y + rowh + 14), (60, 60, 60)); dr = ImageDraw.Draw(sheet)
for (n, im), (px, py) in zip(ims, pos):
    sheet.paste(im, (px, py)); dr.text((px, py - 12), n, fill=(255, 255, 0))
sheet.save(out)
