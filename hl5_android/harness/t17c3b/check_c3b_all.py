#!/usr/bin/env python3
"""T17c3b: chạy check_c3b.py 3 cỡ x 2 mode, so kết quả mới (chạm) với gốc (phím), so vùng chạm với hit_rects.json.
Dùng: check_c3b_all.py [--skip-run] [--redo-orig]   (kết quả: shots/T17c3/check_c3b.txt). WORK=~/hl5work_c3b."""
import sys, os, json, subprocess
H = os.path.dirname(os.path.abspath(__file__)); REPO = os.path.abspath(os.path.join(H, '..', '..'))
WK = os.path.expanduser(os.environ.get('WORK', '~/hl5work_c3b'))
OUT = os.path.join(WK, 't17c3b/out'); SH = os.path.join(REPO, 'shots/T17c3'); os.makedirs(SH, exist_ok=True); os.makedirs(OUT, exist_ok=True)
SIZES = [(400, 240), (600, 270), (1200, 540)]
if '--skip-run' not in sys.argv:
    ps = []
    for (w, h) in SIZES:
        for mode in ('new', 'orig'):
            if mode == 'orig' and os.path.exists(os.path.join(OUT, 'res_%dx%d_orig.json' % (w, h))) and '--redo-orig' not in sys.argv: continue
            ps.append(subprocess.Popen([sys.executable, os.path.join(H, 'check_c3b.py'), str(w), str(h), mode, OUT], stdout=open(os.path.join(OUT, 'log_%d_%s.txt' % (w, mode)), 'w'), stderr=subprocess.STDOUT))
    for p in ps: p.wait()
L = []; BAD = [0]
def log(s=''): print(s); L.append(s)
def ok(c, m):
    log(('ĐẠT   ' if c else 'LỖI   ') + m)
    if not c: BAD[0] += 1
ld = lambda w, h, m: json.load(open(os.path.join(OUT, 'res_%dx%d_%s.json' % (w, h, m))))
HR = json.load(open(os.path.join(REPO, 'ui_new/menu/hit_rects.json')))
def cmp_hits(tag, fname, idmap):
    d = {e['id']: e['rect'] for e in HR['dac_tinh_%s' % tag]}
    try: mine = {int(k): v for k, v in json.load(open(os.path.join(OUT, fname % tag))).items()}
    except Exception as e: ok(False, '%s: thiếu %s (%s)' % (tag, fname % tag, e)); return
    bad = []; n = 0
    for mid, hid in idmap.items():
        if mid not in d: continue
        n += 1
        if hid not in mine: bad.append((mid, 'thiếu hit'))
        elif list(mine[hid][:4]) != d[mid]: bad.append((mid, d[mid], mine[hid][:4]))
    ok(not bad and n > 0, '%s: vùng chạm == hit_rects.json dac_tinh (%d mục)%s' % (tag, n, '' if not bad else '  LỆCH %s' % bad[:5]))
IDMAP = dict([('close', 1)] + [('tab%d' % i, 0x10 + i) for i in range(9)] + [('branch%d' % i, 0x30 + i) for i in range(4)] +
             [('node_%d_%d' % (r, c), 0xA0 + 3 * r + c) for r in range(4) for c in range(3)] + [('grid_scroll', 0x6F), ('desc_scroll', 0xE0), ('detail', 0xE1), ('invest', 0xC8)])
SAME = [('tabs', 'đổi nhánh (chạm tab == TRÁI/PHẢI gốc)'), ('enter', 'vào lưới ở ô (1,1) (chạm == XUỐNG,XUỐNG)'), ('deep', 'chọn ô hàng 3 + đổi cột (cuộn cửa sổ gốc)'),
        ('inv_node', 'đầu tư: chạm ô 2 lần -> xác nhận Có (== OK gốc)'), ('inv_btn', 'đầu tư: nút "Đầu tư" -> xác nhận Có (== OK gốc)'),
        ('inv_fail', 'đầu tư không đủ điều kiện (popup gốc)'), ('back', 'nút < quay lại')]
for (w, h) in SIZES:
    tag = '%dx%d' % (w, h); new, orig = ld(w, h, 'new'), ld(w, h, 'orig')
    errs = {k: v for k, v in new.items() if isinstance(v, dict) and 'ERR' in v}
    ok(not errs, '%s: kịch bản không lỗi/crash %s' % (tag, str(errs)[:300] if errs else ''))
    for k, d in SAME:
        a, b = new.get(k), orig.get(k)
        if isinstance(a, dict): a = {kk: vv for kk, vv in a.items() if kk != 'hits_pop'}     # hits_pop = dữ liệu phụ chỉ có ở new
        ok(a == b and a is not None, '%s: %s: trạng thái/dữ liệu == phím gốc%s' % (tag, d, '' if a == b else '\n      NEW=%s\n      ORIG=%s' % (json.dumps(a)[:700], json.dumps(b)[:700])))
    sc, so = new.get('scroll', {}), orig.get('scroll', {})
    ok(sc.get('p', {}).get('top') == 1 == so.get('p', {}).get('top') and sc.get('p2', {}).get('top') == 2 and sc.get('p3', {}).get('top') == 0
       and all(0 <= sc.get(k, {}).get('f', -1) <= 11 and 0 <= sc.get(k, {}).get('ab', -1) <= 17 for k in ('p', 'p2', 'p3')),
       '%s: kéo cuộn lưới: hàng đầu cửa sổ 1/2/0 (gốc phím: %s), con trỏ hợp lệ: %s' % (tag, so.get('p', {}).get('top'), json.dumps({k: sc.get(k) for k in ('p', 'p2', 'p3')})[:300]))
    for i in range(9):
        k = 'tab%d' % i
        ok(new.get(k, {}).get('state') == orig.get(k, {}).get('state'), '%s: chạm tab %d -> state %s == gốc %s' % (tag, i, new.get(k, {}).get('state'), orig.get(k, {}).get('state')))
    cn, co = new.get('cycles', {}), orig.get('cycles', {})
    ok(cn.get('live', 99) <= co.get('live', 0) + 2 and cn.get('cls') == co.get('cls'), '%s: mở/đóng 20 lần: allocation sống +%s (gốc +%s), heap top +%s (gốc +%s), state cuối %s' % (tag, cn.get('live'), co.get('live'), cn.get('top'), co.get('top'), cn.get('cls')))
    log('%s: ảnh cảnh sau 20 lần mở/đóng new=%s orig=%s' % (tag, cn.get('img'), co.get('img')))
    cmp_hits(tag, 'hits_sel_%s.json', IDMAP)
log('KẾT LUẬN: %s' % ('ĐẠT' if not BAD[0] else 'KHÔNG ĐẠT (%d lỗi)' % BAD[0]))
open(os.path.join(SH, 'check_c3b.txt'), 'w').write('\n'.join(L) + '\n')
