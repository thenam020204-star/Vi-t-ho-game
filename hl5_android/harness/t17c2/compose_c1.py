#!/usr/bin/env python3
"""T17c1: ghép ảnh harness (trái) với mockup tương ứng (phải) -> shots/T17c1/<ten>_<WxH>.png (<= 10 ảnh)."""
import os, sys
from PIL import Image
H = os.path.dirname(os.path.abspath(__file__)); REPO = os.path.abspath(os.path.join(H, '..', '..'))
RAW = os.path.join(os.path.expanduser(os.environ.get('WORK', '~/hl5work_c2')), 't17c2/out1'); SH = os.path.join(REPO, 'shots/T17c1'); MK = os.path.join(REPO, 'ui_new/menu')
PICK = [('menu3x3', 'menu3x3', 400, 240), ('menu3x3', 'menu3x3', 1200, 540), ('nhan_vat', 'nhan_vat', 600, 270), ('nhan_vat', 'nhan_vat', 1200, 540),
        ('nhan_vat_chi_tiet', 'nhan_vat_co_ban', 400, 240), ('he_thong', 'he_thong', 400, 240), ('he_thong_cai_dat', 'he_thong_cai_dat', 600, 270),
        ('he_thong_tro_giup', 'he_thong_tro_giup', 1200, 540), ('popup_luu', 'he_thong_popup_luu', 400, 240), ('popup_luu', 'he_thong_popup_luu', 1200, 540)]
for mine, mock, w, h in PICK:
    a = os.path.join(RAW, 'raw_%dx%d/%s.png' % (w, h, mine))
    b = os.path.join(MK, ('trung_gian/%s_%dx%d.png' if w == 600 else '%s_%dx%d.png') % (mock, w, h))
    if not (os.path.exists(a) and os.path.exists(b)): print('thiếu', a, b); continue
    A, B = Image.open(a).convert('RGB'), Image.open(b).convert('RGB')
    if B.size != (w, h): B = B.resize((w, h))
    im = Image.new('RGB', (2 * w + 4, h), (255, 0, 255)); im.paste(A, (0, 0)); im.paste(B, (w + 4, 0))
    im.save(os.path.join(SH, '%s_%dx%d.png' % (mine, w, h))); print('ok', mine, w, h)
