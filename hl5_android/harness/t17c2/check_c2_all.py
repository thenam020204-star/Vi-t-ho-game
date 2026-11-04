#!/usr/bin/env python3
"""T17c2: chạy check_c2.py ở 3 cỡ x 2 mode, so kết quả mới (chạm) với gốc (phím), so vùng chạm với hit_rects.json. Dùng: check_c2_all.py [--skip-run] [--redo-orig]  (kết quả: shots/T17c2/check.txt)."""
import sys, os, json, subprocess, glob
H = os.path.dirname(os.path.abspath(__file__)); REPO = os.path.abspath(os.path.join(H, '..', '..'))
OUT = os.path.join(os.path.expanduser(os.environ.get('WORK', '~/hl5work_c2')), 't17c2/out2'); SH = os.path.join(REPO, 'shots/T17c2'); os.makedirs(SH, exist_ok=True); os.makedirs(OUT, exist_ok=True)
SIZES = [(400, 240), (600, 270), (1200, 540)]
if '--skip-run' not in sys.argv:
    ps = []
    for (w, h) in SIZES:
        for mode in ('new', 'orig'):
            if mode == 'orig' and os.path.exists(os.path.join(OUT, 'res_%dx%d_orig.json' % (w, h))) and '--redo-orig' not in sys.argv: continue
            ps.append(subprocess.Popen([sys.executable, os.path.join(H, 'check_c2.py'), str(w), str(h), mode, OUT], stdout=open(os.path.join(OUT, 'log_%d_%s.txt' % (w, mode)), 'w'), stderr=subprocess.STDOUT))
        for p in ps[-2:]: pass
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
    ok(not bad, '%s: vùng chạm %s == hit_rects.json (%d mục)%s' % (tag, mock, len(idmap), '' if not bad else '  LỆCH %s' % bad[:4]))
DESC = {'page': 'đổi trang + chọn ô (chạm == phím)', 'sort': 'Sắp xếp', 'use': 'dùng vật phẩm (menu Sử dụng)', 'drop_yes': 'vứt bỏ -> Có', 'drop_no': 'vứt bỏ -> Không',
        'belt': 'gán ô nhanh + đổi bộ', 'equip': 'trang bị (menu -> danh sách lọc -> chọn)', 'unequip': 'tháo (menu)', 'picknav': 'danh sách lọc: mũi tên', 'popup10': 'popup chi tiết vật phẩm (loại 10)', 'back': 'nút < quay lại'}
for (w, h) in SIZES:
    tag = '%dx%d' % (w, h); new, orig = ld(w, h, 'new'), ld(w, h, 'orig')
    errs = {k: v for k, v in new.items() if isinstance(v, dict) and 'ERR' in v}
    ok(not errs, '%s: kịch bản không lỗi/crash %s' % (tag, errs or ''))
    for k, d in DESC.items():
        a, b = new.get(k), orig.get(k)
        ok(a == b and a is not None, '%s: %s: trạng thái/dữ liệu sau chạm == phím gốc%s' % (tag, d, '' if a == b else '  NEW=%s\n      ORIG=%s' % (json.dumps(a)[:600], json.dumps(b)[:600])))
    for i in range(9):
        k = 'tab%d' % i
        ok(new.get(k, {}).get('state') == orig.get(k, {}).get('state'), '%s: chạm tab %d từ Túi đồ -> state %s == gốc %s' % (tag, i, new.get(k, {}).get('state'), orig.get(k, {}).get('state')))
    cn, co = new.get('cycles', {}), orig.get('cycles', {})
    ok(cn.get('live', 99) <= co.get('live', 0) + 2 and cn.get('cls') == co.get('cls'), '%s: mở/đóng Túi đồ+Trang bị 20 lần: allocation sống +%s (gốc +%s), heap top +%s (gốc +%s), state cuối %s' % (tag, cn.get('live'), co.get('live'), cn.get('top'), co.get('top'), cn.get('cls')))
    log('%s: ảnh cảnh sau 20 lần mở/đóng new=%s orig=%s' % (tag, cn.get('img'), co.get('img')))
    cmp_hits(tag, 'tui_do', 'hits_bag_%s.json', dict([('page%d' % i, 0x80 + i) for i in range(3)] + [('cell%d' % i, 0xA0 + i) for i in range(16)] + [('sort', 0x70), ('close', 1)] + [('tab%d' % i, 0x10 + i) for i in range(9)]))
    cmp_hits(tag, 'tui_do_phim_tat', 'hits_belt_%s.json', dict([('belt%d' % i, 0xB0 + i) for i in range(5)] + [('belt_swap', 0xB5), ('ok', 0xC8), ('cancel', 0xC9)]))
    cmp_hits(tag, 'trang_bi', 'hits_equip_%s.json', dict([('slot%d' % i, 0xA0 + i) for i in range(8)] + [('close', 1)] + [('tab%d' % i, 0x10 + i) for i in range(9)]))
    cmp_hits(tag, 'tui_do_chon', 'hits_pick_%s.json', dict([('prev', 0xC5), ('next', 0xC6), ('hscroll', 0xC7), ('ok', 0xC8), ('cancel', 0xC9)] + [('cand%d' % i, 0xC0 + i) for i in range(5)]))
log('KẾT LUẬN: %s' % ('ĐẠT' if not BAD[0] else 'KHÔNG ĐẠT (%d lỗi)' % BAD[0]))
open(os.path.join(SH, 'check.txt'), 'w').write('\n'.join(L) + '\n')
