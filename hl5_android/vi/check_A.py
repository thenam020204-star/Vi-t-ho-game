#!/usr/bin/env python3
"""Kiểm tra vi/A_dich.tsv (T3): mã điều khiển, ký tự cấm, số, độ dài mã hoá <= cap, từ điển mới.
  python3 -I check_A.py [A_dich.tsv] [--kind scn|dat|all]   (mặc định: chỉ dòng đã có bản dịch)
WORK lấy từ env WORK (mặc định scratchpad của phiên)."""
import sys, os, re, json
HERE = os.path.dirname(os.path.abspath(__file__))
WORK = os.environ.get('WORK', '/tmp/claude-0/-home-user-Vi-t-ho-game/31266a47-2a55-5b6d-86d1-5e3a70f1f083/scratchpad')
V5 = os.path.join(WORK, 'proj/HL4_project/vi5')
sys.path.insert(0, V5)
from build_vi5 import Encoder, load_font_chars, CTRL, check_codes   # noqa: E402

FORBID = "'\"\\`^<[{}$&|"
DICT_BUDGET = 5076
tsv = next((a for a in sys.argv[1:] if not a.startswith('--')), os.path.join(HERE, 'A_dich.tsv'))
kind = 'scn'
if '--kind' in sys.argv: kind = sys.argv[sys.argv.index('--kind') + 1]
cat = json.load(open(os.path.join(HERE, 'android_catalog.json')))
cap = {}
for e in cat: cap[(e['file'], e['kind'], e.get('idx', (e.get('rec'), e.get('off'), e.get('ps'))))] = e['cap']

fc = load_font_chars(os.path.join(V5, 'font/font_15px.bin'))
enc = Encoder(fc)
old_dict = [l.rstrip('\n') for l in open(os.path.join(V5, 'build/overlay/vi5/dict.txt'), encoding='utf-8')]
base = set(old_dict)
rows = [l.rstrip('\n').split('\t') for l in open(tsv, encoding='utf-8')][1:]
bad = []; n = 0; maxlen = (0, '')
def err(i, m): bad.append('%s: %s' % (i, m))
for r in rows:
    r += [''] * (6 - len(r))
    i, f, pos, en, vi, note = r[:6]
    k = 'scn' if f.endswith('.scn') else 'dat'
    if kind != 'all' and k != kind: continue
    if not vi.strip(): err(i, 'chưa dịch'); continue
    n += 1
    # 1. mã điều khiển giống hệt tiếng Anh
    for m in check_codes(en, vi): err(i, 'lệch mã ' + m)
    if [c for c in en if c in CTRL] and sum(en.count(c) for c in CTRL) != sum(vi.count(c) for c in CTRL):
        err(i, 'tổng mã khác')
    # 2. ký tự cấm ngoài mã: số lượng từng ký tự cấm phải bằng tiếng Anh (mã đã tính ở trên)
    for c in FORBID:
        if vi.count(c) != en.count(c): err(i, 'ký tự cấm %r %d->%d' % (c, en.count(c), vi.count(c)))
    if '\t' in vi or '\n' in vi: err(i, 'tab/xuống dòng trong bản dịch')
    # 3. số
    for num in re.findall(r'\d+', en):
        if num not in vi: err(i, 'thiếu số ' + num)
    # 4. độ dài mã hoá
    e = enc.encode(vi)
    if enc.missing: err(i, 'thiếu glyph %s' % dict(enc.missing)); enc.missing.clear()
    try: c = int(pos.split()[1]) if k == 'scn' else None
    except ValueError: c = None
    key = (f, k, c) if k == 'scn' else None
    cp = cap.get(key, 254 if k == 'scn' else 255)
    if len(e) > cp: err(i, 'QUÁ DÀI %d > %d' % (len(e), cp))
    if len(e) > maxlen[0]: maxlen = (len(e), i)
newd = [w for w in enc.dict if w not in base]
print('đã kiểm %d dòng (%s); lỗi %d; dài nhất %d byte (%s)' % (n, kind, len(bad), maxlen[0], maxlen[1]))
print('từ điển: cũ %d + mới %d = %d / %d' % (len(base), len(newd), len(base) + len(newd), DICT_BUDGET))
for b in bad: print('  ' + b)
sys.exit(1 if bad else 0)
