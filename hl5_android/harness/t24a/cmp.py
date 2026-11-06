"""so sánh hai JSON của run.py: python3 harness/t24a/cmp.py BASE.json NEW.json [NEW2.json ...] -> md5 trùng / số đo byte-khối"""
import sys, json
def load(*fs):
    d = {}
    for f in fs:
        try: d.update(json.load(open(f)))
        except Exception as e: print('thiếu', f, e)
    return d
b = load(sys.argv[1]); n = load(*sys.argv[2:])
tot_ok = tot = 0
print('%-12s %9s %14s %14s %7s' % ('cửa sổ', 'trùng', 'B/khung base', 'B/khung new', 'new/base'))
for k in b:
    if k not in n: continue
    mb, mn = b[k]['md5'], n[k]['md5']; m = min(len(mb), len(mn)); ok = sum(1 for i in range(m) if mb[i] == mn[i]); tot_ok += ok; tot += m
    first = next((i for i in range(m) if mb[i] != mn[i]), None)
    bb = sum(b[k]['bytes']) / max(1, len(b[k]['bytes'])); bn = sum(n[k]['bytes']) / max(1, len(n[k]['bytes']))
    print('%-12s %4d/%-4d %14.0f %14.0f %7.2f%s' % (k, ok, m, bb, bn, bn / bb if bb else 0, '' if first is None else '  KHÁC từ khung %d (fr %d)' % (first, b[k]['fr0'] + first)))
print('TỔNG trùng %d/%d' % (tot_ok, tot))
