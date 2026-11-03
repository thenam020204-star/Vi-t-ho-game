#!/usr/bin/env python3
"""T17c3a: chạy check_c3a.py 3 cỡ x 2 mode, so chạm (new) với phím gốc (orig), so vùng chạm với hit_rects.json. Dùng: check_c3a_all.py [--skip-run] [--redo-orig]. Kết quả: shots/T17c3/check_c3a.txt"""
import sys, os, json, subprocess
H = os.path.dirname(os.path.abspath(__file__)); REPO = os.path.abspath(os.path.join(H, '..', '..'))
OUT = os.path.expanduser('~/hl5work_c3/t17c3/out'); SH = os.path.join(REPO, 'shots/T17c3'); os.makedirs(SH, exist_ok=True); os.makedirs(OUT, exist_ok=True)
SIZES = [(400, 240), (600, 270), (1200, 540)]
if '--skip-run' not in sys.argv:
    ps = []
    for (w, h) in SIZES:
        for mode in ('new', 'orig'):
            if mode == 'orig' and os.path.exists(os.path.join(OUT, 'res_%dx%d_orig.json' % (w, h))) and '--redo-orig' not in sys.argv: continue
            ps.append(subprocess.Popen([sys.executable, os.path.join(H, 'check_c3a.py'), str(w), str(h), mode, OUT], stdout=open(os.path.join(OUT, 'log_%d_%s.txt' % (w, mode)), 'w'), stderr=subprocess.STDOUT))
    for p in ps: p.wait()
L = []; BAD = [0]
def log(s=''): print(s); L.append(s)
def ok(c, m):
    log(('ĐẠT   ' if c else 'LỖI   ') + m)
    if not c: BAD[0] += 1
ld = lambda w, h, m: json.load(open(os.path.join(OUT, 'res_%dx%d_%s.json' % (w, h, m))))
HR = json.load(open(os.path.join(REPO, 'ui_new/menu/hit_rects.json')))
DESC = {'assign': 'gán kỹ năng vào ô chuỗi (chạm == phím)', 'unlearned': 'ứng viên chưa học -> popup', 'clear': 'nút Bỏ chọn', 'nav': 'mũi tên trái/phải (cuộn trang ứng viên)', 'slot_back': 'đổi ô chuỗi khi đang chọn ứng viên',
        'slot5': 'ô thứ 5 (lớp 1, HERO+0x267>0)', 'class0': 'lớp 0 chọn ứng viên', 'class1': 'lớp 1 chọn ứng viên', 'class2': 'lớp 2 chọn ứng viên', 'class3': 'lớp 3 chọn ứng viên', 'back': 'nút < quay lại'}
for (w, h) in SIZES:
    tag = '%dx%d' % (w, h); new, orig = ld(w, h, 'new'), ld(w, h, 'orig')
    errs = {k: v for k, v in new.items() if isinstance(v, dict) and 'ERR' in v}
    ok(not errs, '%s: kịch bản không lỗi/crash %s' % (tag, errs or ''))
    for k, d in DESC.items():
        a, b = new.get(k), orig.get(k)
        ok(a == b and a is not None, '%s: %s: trạng thái/HERO sau chạm == phím gốc%s' % (tag, d, '' if a == b else '  NEW=%s\n      ORIG=%s' % (json.dumps(a)[:500], json.dumps(b)[:500])))
    for i in range(9):
        k = 'tab%d' % i
        ok(new.get(k, {}).get('state') == orig.get(k, {}).get('state'), '%s: chạm tab %d từ Liên kích -> state %s == gốc %s' % (tag, i, new.get(k, {}).get('state'), orig.get(k, {}).get('state')))
    cn, co = new.get('cycles', {}), orig.get('cycles', {})
    ok(cn.get('live', 99) <= co.get('live', 0) + 2 and cn.get('cls') == co.get('cls'), '%s: mở/đóng Liên kích 20 lần: allocation sống +%s (gốc +%s), heap top +%s (gốc +%s), state cuối %s' % (tag, cn.get('live'), co.get('live'), cn.get('top'), co.get('top'), cn.get('cls')))
    log('%s: ảnh cảnh sau 20 lần new=%s orig=%s' % (tag, cn.get('img'), co.get('img')))
    d = {e['id']: e['rect'] for e in HR['lien_kich_%s' % tag]}
    mine = {int(k): v for k, v in json.load(open(os.path.join(OUT, 'hits_combo_%s.json' % tag))).items()}
    idmap = dict([('close', 1), ('prev', 0xC5), ('next', 0xC6), ('clear', 0xCA), ('ok', 0xC8)] + [('tab%d' % i, 0x10 + i) for i in range(9)] + [('combo%d' % i, 0xA0 + i) for i in range(4)] + [('cand%d' % i, 0xC0 + i) for i in range(5)])
    bad = [(m, d[m], mine.get(i, 'thiếu')[:4] if i in mine else 'thiếu') for m, i in idmap.items() if m in d and (i not in mine or list(mine[i][:4]) != d[m])]
    ok(not bad, '%s: vùng chạm lien_kich == hit_rects.json (%d mục)%s' % (tag, len(idmap), '' if not bad else '  LỆCH %s' % bad[:4]))
log('KẾT LUẬN: %s' % ('ĐẠT' if not BAD[0] else 'KHÔNG ĐẠT (%d lỗi)' % BAD[0]))
open(os.path.join(SH, 'check_c3a.txt'), 'w').write('\n'.join(L) + '\n')
