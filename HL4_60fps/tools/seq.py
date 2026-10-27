# seq.py dir t0 t1 x0 y0 x1 y1 scale out : rows = ticks, cols = prev tick, ghost1, ghost2, tick
import sys, os
from PIL import Image
d, t0, t1, x0, y0, x1, y1, sc, out = sys.argv[1], *map(int, sys.argv[2:9]), sys.argv[9]
w, h = (x1 - x0) * sc, (y1 - y0) * sc
rows = list(range(t0, t1 + 1))
m = Image.new('RGB', (w * 3 + 8, h * len(rows) + 2 * len(rows)), (255, 0, 255))
for r, t in enumerate(rows):
    for c, suf in enumerate(['0', 'g1', 'g2']):
        p = os.path.join(d, 't%d_%s.png' % (t, suf))
        if os.path.exists(p):
            im = Image.open(p).crop((x0, y0, x1, y1)).resize((w, h), Image.NEAREST)
            m.paste(im, (c * (w + 4), r * (h + 2)))
m.save(out)
