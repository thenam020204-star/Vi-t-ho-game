#!/usr/bin/env python3
"""T16b: ghép ảnh nghiệm thu từ kết quả check_t16.py -> shots/T16/. Dùng: compose_t16.py CHKDIR [OUTDIR]
  dialog_<WxH>_vs_mockup.png : trên = harness (mới), dưới = mockup_dialog_hd_sach.jpg co về cùng cỡ
  dialog_<WxH>_goc_moi.png   : trái = gốc, phải = mới (cùng thông điệp, trang đầu)"""
import sys, os, json, glob
from PIL import Image
H = os.path.dirname(os.path.abspath(__file__)); REPO = os.path.abspath(os.path.join(H, '..', '..'))
D = sys.argv[1]; OUT = sys.argv[2] if len(sys.argv) > 2 else os.path.join(REPO, 'shots', 'T16'); os.makedirs(OUT, exist_ok=True)
mock = Image.open(os.path.join(REPO, 'ui_new', 'dialog', 'mockup_dialog_hd_sach.jpg')).convert('RGB')
for sz in ('400x240', '600x270', '1200x540'):
    W, Hh = map(int, sz.split('x'))
    try: n = json.load(open(os.path.join(D, 'msgs_%s_new.json' % sz)))
    except Exception: continue
    # thông điệp có chân dung (Tir), dài nhất; trang đầu
    cand = [(len(m['text']), i) for i, m in enumerate(n['msgs']) if m['face'] and 0 in map(int, m['pages'])]
    if not cand: continue
    mi = max(cand)[1]
    f_new = os.path.join(D, 'msg%02d_p0_%s_new.png' % (mi, sz))
    if not os.path.exists(f_new): continue
    a = Image.open(f_new).convert('RGB'); b = mock.resize((W, Hh), Image.LANCZOS)
    S = Image.new('RGB', (W, 2 * Hh + 4), (255, 0, 255)); S.paste(a, (0, 0)); S.paste(b, (0, Hh + 4))
    S.save(os.path.join(OUT, 'dialog_%s_vs_mockup.png' % sz))
    f_old = os.path.join(D, 'msg%02d_p0_%s_orig.png' % (mi, sz))
    if os.path.exists(f_old):
        o = Image.open(f_old).convert('RGB')
        S = Image.new('RGB', (2 * W + 4, Hh), (255, 0, 255)); S.paste(o, (0, 0)); S.paste(a, (W + 4, 0))
        S.save(os.path.join(OUT, 'dialog_%s_goc_moi.png' % sz))
    print(sz, 'msg', mi)
