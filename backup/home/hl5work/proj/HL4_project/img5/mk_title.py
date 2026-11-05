#!/usr/bin/env python3
"""Draw the English/Vietnamese title logo with pixeltext and pack it into title.mgr.
Images are drawn centred on fixed anchors (from the original frame): image 6/5 (title) centre (80,134),
image 14 (first subtitle glyph) centre (86,155). Content is placed by padding the image symmetrically."""
import sys, os, subprocess
sys.path.insert(0, os.environ.get('PIXELTEXT', '/home/claude/work/img5/pixeltext-kit'))
import pixeltext as pt
from PIL import Image
W = os.path.dirname(os.path.abspath(__file__))
OUT = os.path.join(W, 'title')

def place(content, x0, y0, ax, ay):
    w, h = content.size
    CW = 2 * max(ax - x0, x0 + w - ax); CH = 2 * max(ay - y0, y0 + h - ay)
    c = Image.new('RGBA', (CW, CH), (0, 0, 0, 0))
    c.paste(content, (x0 - (ax - CW // 2), y0 - (ay - CH // 2)), content)
    return c

title = pt.render('Heroes Lore', size=15, scale=2, fill=['#ffffff', '#c8c8d8'], outline='#1a1a24', trim=True)
tw, th = title.size
tx = 138 - tw; ty = 134 - th // 2
img6 = place(title, tx, ty, 80, 134)
sub = pt.render('Covenant of Darkness', size=15, fill='#f0f0f0', outline='#101010', trim=True)
cred = pt.render('Việt hoá bởi NAM.P', size=15, fill='#ffd84a', outline='#101010', trim=True)
cx = tx + tw // 2
sy = ty + th + 3
lines = Image.new('RGBA', (max(sub.width, cred.width), sub.height + cred.height + 4), (0, 0, 0, 0))
lines.paste(sub, ((lines.width - sub.width) // 2, 0), sub)
lines.paste(cred, ((lines.width - cred.width) // 2, sub.height + 4), cred)
img14 = place(lines, cx - lines.width // 2, sy, 86, 155)
blank = Image.new('RGBA', (1, 1), (0, 0, 0, 0))
os.makedirs(OUT, exist_ok=True)
img6.save(os.path.join(OUT, '006.png')); img6.save(os.path.join(OUT, '005.png'))
img14.save(os.path.join(OUT, '014.png'))
for k in (15, 16, 17, 18): blank.save(os.path.join(OUT, '%03d.png' % k))
print('title', title.size, 'at', tx, ty, '| lines', lines.size)
dst = os.path.join(W, 'overlay/res/c/sp/imgcom/title.mgr')
subprocess.check_call([sys.executable, os.path.join(W, 'mgr_replace.py'), '/home/claude/work/hl5/jar/res/c/sp/imgcom/title.mgr', dst]
                      + ['%d=%s' % (k, os.path.join(OUT, '%03d.png' % k)) for k in (5, 6, 14, 15, 16, 17, 18)])
