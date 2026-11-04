#!/usr/bin/env python3
"""T16: ghép ảnh game (mới) cạnh mockup v2 bản A, từng cỡ, người nói trái/phải; đo bbox viền cam (hộp, bảng tên, nút Bỏ qua) ở hai bên.
Dùng: compose_v2.py CHKDIR [OUTDIR]   (CHKDIR = thư mục của check_t16.py: msgs_<cỡ>_new.json + msgNN_p0_<cỡ>_new.png)
Ra: OUTDIR/so_mockup_<cỡ>_<trai|phai>.png (trái = game, phải = mockup) + so_mockup.txt"""
import sys, os, json
from PIL import Image
H = os.path.dirname(os.path.abspath(__file__)); REPO = os.path.abspath(os.path.join(H, '..', '..'))
D = sys.argv[1]; OUT = sys.argv[2] if len(sys.argv) > 2 else os.path.join(REPO, 'shots', 'T16'); os.makedirs(OUT, exist_ok=True)
OR = (232, 137, 43); L = []
def log(s=''): print(s); L.append(s)
def orange_boxes(im):
    """thành phần liên thông (4-lân cận, theo hàng/cột) của điểm cam: trả các bbox đủ lớn, sắp theo diện tích giảm"""
    w, h = im.size; px = im.load(); seen = bytearray(w * h); out = []
    isor = lambda p: abs(p[0] - OR[0]) < 24 and abs(p[1] - OR[1]) < 24 and abs(p[2] - OR[2]) < 30
    for y in range(h):
        for x in range(w):
            if seen[y * w + x] or not isor(px[x, y]): continue
            st = [(x, y)]; seen[y * w + x] = 1; x0 = x1 = x; y0 = y1 = y; n = 0
            while st:
                cx, cy = st.pop(); n += 1
                x0 = min(x0, cx); x1 = max(x1, cx); y0 = min(y0, cy); y1 = max(y1, cy)
                for nx, ny in ((cx + 1, cy), (cx - 1, cy), (cx, cy + 1), (cx, cy - 1)):
                    if 0 <= nx < w and 0 <= ny < h and not seen[ny * w + nx] and isor(px[nx, ny]): seen[ny * w + nx] = 1; st.append((nx, ny))
            if (x1 - x0) >= 20 and (y1 - y0) >= 8: out.append((x0, y0, x1 + 1, y1 + 1))
    return sorted(out, key=lambda b: -(b[2] - b[0]) * (b[3] - b[1]))
def mock_box(W, Hh, side, fw0=155, fh0=220):
    """hộp theo công thức mock_dialog_v2.py::render (px 18/15, lh = px+6/3, 3 dòng)"""
    sc = max(1, min(3, (Hh * 82 // 100) // fh0)); px = 18 if Hh >= 480 else 15; b = 2 if Hh >= 480 else 1; lh = px + (6 if Hh >= 480 else 3)
    m = 8 * sc; fw = fw0 * sc; ph = lh * 3 + (24 if Hh >= 480 else 14); py = Hh - ph - m // 2; over = fw * 30 // 100
    if side == 0: x0 = m + fw - over; pw = W - x0 - m
    else: x0 = m; pw = (W - m - fw) + over - m
    return [x0, py, pw, ph]
def classify(bx, W, Hh):
    """hộp = bbox lớn nhất ở nửa dưới; nút Bỏ qua = ở nửa trên; bảng tên = nhỏ hơn hộp, sát mép trên hộp"""
    res = {'hop': None, 'ten': None, 'nut': None}
    for b in bx:
        if b[1] < Hh // 3: res['nut'] = res['nut'] or b
        elif res['hop'] is None: res['hop'] = b
        elif res['ten'] is None: res['ten'] = b
    return res
for sz in ('400x240', '600x270', '1200x540'):
    W, Hh = map(int, sz.split('x'))
    try: n = json.load(open(os.path.join(D, 'msgs_%s_new.json' % sz)))
    except Exception: continue
    log('== ' + sz)
    for side, nm in ((0, 'trai'), (1, 'phai')):
        cand = [(len(m['text']), i) for i, m in enumerate(n['msgs']) if m['face'] and m['side'] == side and '0' in m['pages']]
        if not cand: log('  %s: không có thông điệp có chân dung phía này' % nm); continue
        mi = max(cand)[1]; f = os.path.join(D, 'msg%02d_p0_%s_new.png' % (mi, sz))
        if not os.path.exists(f): continue
        a = Image.open(f).convert('RGB'); b = Image.open(os.path.join(REPO, 'ui_new/dialog/v2/v2A_%s_%s.png' % (sz, nm))).convert('RGB')
        S = Image.new('RGB', (2 * W + 4, Hh), (255, 0, 255)); S.paste(a, (0, 0)); S.paste(b, (W + 4, 0)); S.save(os.path.join(OUT, 'so_mockup_%s_%s.png' % (sz, nm)))
        ga, gb = classify(orange_boxes(a), W, Hh), classify(orange_boxes(b), W, Hh)
        gb_, mb_ = n['msgs'][mi]['box'], mock_box(W, Hh, side)
        log('  %s (thông điệp %d): hộp game (DIALOG_INFO) x,y,w,h=%s ; hộp mockup (công thức) %s ; chênh x %+d y %+d w %+d h %+d' % (nm, mi, gb_, mb_, gb_[0] - mb_[0], gb_[1] - mb_[1], gb_[2] - mb_[2], gb_[3] - mb_[3]))
        for k, t in (('hop', 'hộp'), ('ten', 'bảng tên'), ('nut', 'nút Bỏ qua')):
            x, y = ga[k], gb[k]
            dif = '' if not (x and y) else '   chênh (game - mock): x0 %+d y0 %+d x1 %+d y1 %+d, cao %+d' % (x[0] - y[0], x[1] - y[1], x[2] - y[2], x[3] - y[3], (x[3] - x[1]) - (y[3] - y[1]))
            log('    %-10s game %s  mock %s%s' % (t, x, y, dif))
open(os.path.join(OUT, 'so_mockup.txt'), 'w').write('\n'.join(L) + '\n')
