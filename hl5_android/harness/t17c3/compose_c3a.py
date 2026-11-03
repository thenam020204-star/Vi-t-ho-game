#!/usr/bin/env python3
"""T17c3a: ghép ảnh harness (trái) với mockup lien_kich (phải) -> shots/T17c3/lien_kich_*.png (4 ảnh)."""
import os
from PIL import Image
H = os.path.dirname(os.path.abspath(__file__)); REPO = os.path.abspath(os.path.join(H, '..', '..'))
RAW = os.path.expanduser('~/hl5work_c3/t17c3/out'); SH = os.path.join(REPO, 'shots/T17c3'); MK = os.path.join(REPO, 'ui_new/menu'); os.makedirs(SH, exist_ok=True)
for f in os.listdir(SH):
    if f.startswith('lien_kich_') and f.endswith('.png'): os.remove(os.path.join(SH, f))
PICK = [('lien_kich', 400, 240, 'lien_kich'), ('lien_kich', 1200, 540, 'lien_kich'), ('lien_kich_nav', 600, 270, 'lien_kich'), ('lien_kich_5', 600, 270, None)]
for mine, w, h, mock in PICK:
    a = os.path.join(RAW, 'raw_%dx%d/%s_%dx%d_new.png' % (w, h, mine, w, h)); A = Image.open(a).convert('RGB')
    if mock:
        b = os.path.join(MK, ('trung_gian/%s_%dx%d.png' if w == 600 else '%s_%dx%d.png') % (mock, w, h)); B = Image.open(b).convert('RGB')
        if B.size != (w, h): B = B.resize((w, h))
        im = Image.new('RGB', (2 * w + 4, h), (255, 0, 255)); im.paste(A, (0, 0)); im.paste(B, (w + 4, 0))
    else: im = A
    im.save(os.path.join(SH, '%s_%dx%d.png' % (mine, w, h))); print('ok', mine, w, h)
