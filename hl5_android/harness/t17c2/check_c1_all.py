#!/usr/bin/env python3
"""T17c1: chạy check_c1.py ở 3 cỡ x 2 mode (new/orig), so kết quả mới với gốc, ghép ảnh so mockup. Dùng: check_c1_all.py [--skip-run]  (kết quả: shots/T17c1/)."""
import sys, os, json, subprocess, glob
H = os.path.dirname(os.path.abspath(__file__)); REPO = os.path.abspath(os.path.join(H, '..', '..'))
OUT = os.path.join(os.path.expanduser(os.environ.get('WORK', '~/hl5work_c2')), 't17c2/out1'); SH = os.path.join(REPO, 'shots/T17c1'); os.makedirs(SH, exist_ok=True); os.makedirs(OUT, exist_ok=True)
SIZES = [(400, 240), (600, 270), (1200, 540)]
if '--skip-run' not in sys.argv:
    ps = []
    for (w, h) in SIZES:
        for mode in ('new', 'orig'):
            if mode == 'orig' and os.path.exists(os.path.join(OUT, 'res_%dx%d_orig.json' % (w, h))) and '--redo-orig' not in sys.argv: continue   # chuẩn gốc không đổi: dùng lại
            for fn in glob.glob(os.path.join(OUT, '*_%dx%d_%s.json' % (w, h, mode))): os.remove(fn)
            ps.append(subprocess.Popen([sys.executable, os.path.join(H, 'check_c1.py'), str(w), str(h), mode, OUT], stdout=open(os.path.join(OUT, 'log_%d_%s.txt' % (w, mode)), 'w'), stderr=subprocess.STDOUT))
    for p in ps: p.wait()
L = []; BAD = [0]
def log(s=''): print(s); L.append(s)
def ok(c, m):
    log(('ĐẠT   ' if c else 'LỖI   ') + m)
    if not c: BAD[0] += 1
ld = lambda w, h, m: json.load(open(os.path.join(OUT, 'res_%dx%d_%s.json' % (w, h, m))))
orig = ld(600, 270, 'orig')
for (w, h) in SIZES:
    new = ld(w, h, 'new'); tag = '%dx%d' % (w, h)
    if os.path.exists(os.path.join(OUT, 'res_%s_orig.json' % tag)): orig = ld(w, h, 'orig')      # chuẩn cùng cỡ khung nếu có (--all-orig)
    errs = {k: v for k, v in new.items() if isinstance(v, dict) and 'ERR' in v}
    ok(not errs, '%s: kịch bản không lỗi/crash %s' % (tag, errs or ''))
    for i in range(9):
        k = 'tile%d' % i
        ok(new.get(k, {}).get('state') == orig.get(k, {}).get('state') and new.get(k, {}).get('sub') == orig.get(k, {}).get('sub'),
           '%s: chạm ô %d (3x3) -> state %s == phím gốc %s' % (tag, i, new.get(k, {}).get('state'), orig.get(k, {}).get('state')))
    for key in ('char', 'sys'):
        a, b = new.get(key, {}), orig.get(key, {})
        ok(a == b, '%s: %s: dữ liệu HERO/tuỳ chọn sau thao tác chạm == phím gốc%s' % (tag, key, '' if a == b else '  NEW=%s ORIG=%s' % (a, b)))
    for k in ('save_yes', 'save_no'):
        a, b = new.get(k, {}), orig.get(k, {})
        ok(a.get('files_new') == b.get('files_new') and a.get('top_after') == b.get('top_after'), '%s: %s: tệp lưu ghi == gốc  new=%s orig=%s' % (tag, k, a.get('files_new'), b.get('files_new')))
        ok(a.get('type') == 2 and a.get('nbtn') == 2, '%s: %s: popup là StatePopup loại 2, 2 nút' % (tag, k))
    for i in range(9):
        k = 'tab%d' % i
        ok(new.get(k, {}).get('state') == orig.get(k, {}).get('state'), '%s: chạm tab %d -> state %s == gốc %s' % (tag, i, new.get(k, {}).get('state'), orig.get(k, {}).get('state')))
    c = new.get('cycles', {}); o = orig.get('cycles', {})
    ok(c.get('live', 99) <= o.get('live', 0) + 2 and c.get('cls') == o.get('cls'), '%s: mở/đóng 20 lần: allocation sống +%s (gốc +%s), state cuối %s' % (tag, c.get('live'), o.get('live'), c.get('cls')))
    log('%s: ảnh cảnh sau 20 lần mở/đóng new=%s orig=%s' % (tag, c.get('img'), o.get('img')))
# ---- vùng chạm so hit_rects.json (nguồn số đo duy nhất) ----
HR = json.load(open(os.path.join(REPO, 'ui_new/menu/hit_rects.json')))
def cmp_hits(tag, mock, mine_file, idmap):
    d = {e['id']: e['rect'] for e in HR['%s_%s' % (mock, tag)]}
    try: mine = {int(k): v for k, v in json.load(open(os.path.join(OUT, mine_file % tag))).items()}
    except Exception as e: ok(False, '%s: thiếu %s (%s)' % (tag, mine_file, e)); return
    bad = []
    for mid, hid in idmap.items():
        if mid in d and hid in mine and list(mine[hid][:4]) != d[mid]: bad.append((mid, d[mid], mine[hid][:4]))
        elif mid in d and hid not in mine: bad.append((mid, 'thiếu hit'))
    ok(not bad, '%s: vùng chạm %s == hit_rects.json (%d mục)%s' % (tag, mock, len(idmap), '' if not bad else '  LỆCH %s' % bad[:4]))
for (w, h) in SIZES:
    tag = '%dx%d' % (w, h)
    new = ld(w, h, 'new')
    mh = {int(k): v for k, v in new.get('menu3', {}).get('hits', {}).items()}
    d = {e['id']: e['rect'] for e in HR['menu3x3_' + tag]}
    bad = [(m, d[m], mh.get(0x40 + int(m[4:]), mh.get(1) if m == 'close' else None)) for m in d if m.startswith('tile') and list(mh.get(0x40 + int(m[4:]), [None] * 4)[:4]) != d[m]]
    ok(not bad and mh.get(1) and list(mh[1][:4]) == d['close'], '%s: vùng chạm menu3x3 (9 ô + nút <) == hit_rects.json %s' % (tag, bad[:3]))
    cmp_hits(tag, 'nhan_vat', 'hits_char_%s.json', dict([('tab%d' % i, 0x10 + i) for i in range(9)] + [('page_toggle', 0x20)] + [('stat%d' % i, 0x28 + i) for i in range(4)] + [('plus%d' % i, 0x30 + i) for i in range(4)]))
    cmp_hits(tag, 'he_thong', 'hits_sys_%s.json', {'save': 0x40, 'option': 0x41, 'help': 0x42, 'title': 0x43})
    cmp_hits(tag, 'he_thong_cai_dat', 'hits_opt_%s.json', dict([('opt%d_prev' % i, 0x50 + i) for i in range(6)] + [('opt%d_next' % i, 0x60 + i) for i in range(6)]))
    cmp_hits(tag, 'he_thong_tro_giup', 'hits_help_%s.json', dict([('help%d' % i, 0x70 + i) for i in range(5)]))
    cmp_hits(tag, 'he_thong_popup_luu', 'hits_popup_%s.json', {'popup_yes': 0xF0, 'popup_no': 0xF1})
log('KẾT LUẬN: %s' % ('ĐẠT' if not BAD[0] else 'KHÔNG ĐẠT (%d lỗi)' % BAD[0]))
open(os.path.join(SH, 'check.txt'), 'w').write('\n'.join(L) + '\n')
