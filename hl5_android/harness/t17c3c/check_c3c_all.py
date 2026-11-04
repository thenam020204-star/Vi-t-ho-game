#!/usr/bin/env python3
"""T17c3c: chạy check_c3c.py ở 3 cỡ x 2 mode, so kết quả mới (chạm) với gốc (phím), so vùng chạm với hit_rects.json.
Dùng: check_c3c_all.py [--skip-run] [--redo-orig]   (kết quả: shots/T17c3/check_c3c.txt). WORK mặc định ~/hl5work_c3c."""
import sys, os, json, subprocess
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_c3c'))
H = os.path.dirname(os.path.abspath(__file__)); REPO = os.path.abspath(os.path.join(H, '..', '..'))
OUT = os.path.join(os.environ['WORK'], 't17c3c/out'); SH = os.path.join(REPO, 'shots/T17c3'); os.makedirs(SH, exist_ok=True); os.makedirs(OUT, exist_ok=True)
SIZES = [(400, 240), (600, 270), (1200, 540)]
if '--skip-run' not in sys.argv:
    ps = []
    for (w, h) in SIZES:
        for mode in ('new', 'orig'):
            if mode == 'orig' and os.path.exists(os.path.join(OUT, 'res_%dx%d_orig.json' % (w, h))) and '--redo-orig' not in sys.argv: continue
            ps.append(subprocess.Popen([sys.executable, os.path.join(H, 'check_c3c.py'), str(w), str(h), mode, OUT], stdout=open(os.path.join(OUT, 'log_%d_%s.txt' % (w, mode)), 'w'), stderr=subprocess.STDOUT))
    for p in ps: p.wait()
L = []; BAD = [0]
def log(s=''): print(s); L.append(s)
def ok(c, m):
    log(('ĐẠT   ' if c else 'LỖI   ') + m)
    if not c: BAD[0] += 1
ld = lambda w, h, m: json.load(open(os.path.join(OUT, 'res_%dx%d_%s.json' % (w, h, m))))
HR = json.load(open(os.path.join(REPO, 'ui_new/menu/hit_rects.json')))
def cmp_hits(tag, mock, fname, idmap):
    d = {e['id']: e['rect'] for e in HR['%s_%s' % (mock, tag)]}
    try: mine = {int(k): v for k, v in json.load(open(os.path.join(OUT, fname % tag))).items()}
    except Exception as e: ok(False, '%s: thiếu %s (%s)' % (tag, fname % tag, e)); return
    bad = []
    for mid, hid in idmap.items():
        if mid not in d: continue
        if hid not in mine: bad.append((mid, 'thiếu hit'))
        elif list(mine[hid][:4]) != d[mid]: bad.append((mid, d[mid], mine[hid][:4]))
    ok(not bad, '%s: vùng chạm %s == hit_rects.json (%d mục)%s' % (tag, mock, len(idmap), '' if not bad else '  LỆCH %s' % bad[:6]))
DESC = {'init': 'mở tab Kỹ năng (sub 0)', 'switch': 'đổi tab con từ lưới (chọn ô + tab 3, 4)', 'select': 'chọn ô lưới (chạm == phím)', 'drag': 'kéo lưới 1 hàng == DOWN x4 rồi UP x3', 'drag2': 'kéo xuống 1 hàng rồi kéo lại == 1 phím DOWN',
        'assign': 'ô -> popup -> Gán phím tắt -> đổi bộ -> gán ô nhanh', 'assign_swap': 'gán đè ô nhanh (giữ món cũ) rồi Huỷ', 'tao': 'popup mục 2 (Tháo)', 'button': 'nút Gán phím tắt == OK', 'back': 'nút < quay lại',
        'deep': 'sub 7..10 (đường gốc) không crash'}
for (w, h) in SIZES:
    tag = '%dx%d' % (w, h); new, orig = ld(w, h, 'new'), ld(w, h, 'orig')
    errs = {k: v for k, v in new.items() if isinstance(v, dict) and 'ERR' in v}
    ok(not errs, '%s: kịch bản không lỗi/crash %s' % (tag, errs or ''))
    for k, d in DESC.items():
        a, b = new.get(k), orig.get(k)
        ok(a == b and a is not None, '%s: %s: trạng thái/dữ liệu sau chạm == phím gốc%s' % (tag, d, '' if a == b else '  NEW=%s\n      ORIG=%s' % (json.dumps(a)[:700], json.dumps(b)[:700])))
    try:
        db = json.load(open(os.path.join(OUT, 'dragb_%s.json' % tag))); f = db['bottom']['f']
        ok((f[3] - f[2]) == 4 and 0 <= f[2] <= 15 and f[3] <= 18, '%s: kéo hết cỡ xuống: cửa sổ hàng 1 (ô trong cửa sổ %d, chỉ số %d, hiệu 4), kéo hết cỡ lên: %s' % (tag, f[2], f[3], db['top']['f'][2:4]))
    except Exception as e: ok(False, '%s: thiếu kết quả kéo (%s)' % (tag, e))
    for i in range(5):
        k = 'sub%d' % i
        ok(new.get(k) == orig.get(k) and new.get(k) is not None, '%s: chạm tab con %d (từ sub 0) == RIGHT x%d + DOWN gốc' % (tag, i, i))
    for i in range(9):
        k = 'tab%d' % i
        ok(new.get(k, {}).get('state') == orig.get(k, {}).get('state'), '%s: chạm tab %d từ Kỹ năng -> state %s == gốc %s' % (tag, i, new.get(k, {}).get('state'), orig.get(k, {}).get('state')))
    cn, co = new.get('cycles', {}), orig.get('cycles', {})
    ok(cn.get('live', 99) <= co.get('live', 0) + 2 and cn.get('cls') == co.get('cls'), '%s: mở/đóng Kỹ năng 20 lần: allocation sống +%s (gốc +%s), heap top +%s (gốc +%s), state cuối %s' % (tag, cn.get('live'), co.get('live'), cn.get('top'), co.get('top'), cn.get('cls')))
    log('%s: ảnh cảnh sau 20 lần new=%s orig=%s' % (tag, cn.get('img'), co.get('img')))
    common = dict([('close', 1)] + [('tab%d' % i, 0x10 + i) for i in range(9)] + [('subtab%d' % i, 0x40 + i) for i in range(5)] + [('skill%d' % i, 0xA0 + i) for i in range(16)] +
                  [('grid_scroll', 0x71), ('assign', 0x72), ('detail', 0xE0), ('desc_scroll', 0xE1)])
    cmp_hits(tag, 'ky_nang', 'hits_grid_%s.json', common)
    cmp_hits(tag, 'ky_nang_phim_tat', 'hits_belt_%s.json', dict([('belt%d' % i, 0xB0 + i) for i in range(5)] + [('belt_swap', 0xB5), ('popup_cancel', 0xC9)]))
log('KẾT LUẬN: %s' % ('ĐẠT' if not BAD[0] else 'KHÔNG ĐẠT (%d lỗi)' % BAD[0]))
open(os.path.join(SH, 'check_c3c.txt'), 'w').write('\n'.join(L) + '\n')
