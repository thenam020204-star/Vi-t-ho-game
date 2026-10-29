"""Ghep anh chup thanh 1 tam (phong to x2): sheet.py out.png a.png b.png ..."""
import sys
from PIL import Image
ims = [Image.open(f).convert('RGB') for f in sys.argv[2:]]
W = sum(i.width for i in ims) + 6 * len(ims); H = max(i.height for i in ims)
o = Image.new('RGB', (W, H), (120, 120, 120)); x = 0
for i in ims:
    o.paste(i, (x, 0)); x += i.width + 6
o.resize((W * 2, H * 2), Image.NEAREST).save(sys.argv[1])
