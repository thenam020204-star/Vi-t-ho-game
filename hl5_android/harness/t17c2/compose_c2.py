#!/usr/bin/env python3
"""T17c2: ghép ảnh harness (trái) với mockup tương ứng (phải) -> shots/T17c2/<ten>_<WxH>.png (<= 10 ảnh)."""
import os, sys
from PIL import Image
H = os.path.dirname(os.path.abspath(__file__)); REPO = os.path.abspath(os.path.join(H, '..', '..'))
RAW = os.path.join(os.path.expanduser(os.environ.get('WORK', '~/hl5work_c2')), 't17c2/out2'); SH = os.path.join(REPO, 'shots/T17c2'); MK = os.path.join(REPO, 'ui_new/menu')
os.makedirs(SH, exist_ok=True)
for f in os.listdir(SH):
    if f.endswith('.png'): os.remove(os.path.join(SH, f))
PICK = [('tui_do', 'tui_do', 400, 240), ('tui_do', 'tui_do', 1200, 540), ('tui_do_menu', 'tui_do_menu', 600, 270), ('tui_do_phim_tat', 'tui_do_phim_tat', 400, 240),
        ('trang_bi_chon', 'tui_do_chon', 600, 270), ('trang_bi', 'trang_bi', 400, 240), ('trang_bi', 'trang_bi', 1200, 540), ('trang_bi_menu', 'trang_bi_menu', 600, 270),
        ('popup_chi_tiet', 'popup_chi_tiet', 400, 240), ('popup_chi_tiet', 'popup_chi_tiet', 1200, 540)]
for mine, mock, w, h in PICK:
    a = os.path.join(RAW, 'raw_%dx%d/%s_%dx%d_new.png' % (w, h, mine, w, h))
    b = os.path.join(MK, ('trung_gian/%s_%dx%d.png' if w == 600 else '%s_%dx%d.png') % (mock, w, h))
    if not (os.path.exists(a) and os.path.exists(b)): print('thiếu', a, b); continue
    A, B = Image.open(a).convert('RGB'), Image.open(b).convert('RGB')
    if B.size != (w, h): B = B.resize((w, h))
    im = Image.new('RGB', (2 * w + 4, h), (255, 0, 255)); im.paste(A, (0, 0)); im.paste(B, (w + 4, 0))
    im.save(os.path.join(SH, '%s_%dx%d.png' % (mine, w, h))); print('ok', mine, w, h)
