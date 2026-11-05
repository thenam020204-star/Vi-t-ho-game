#!/usr/bin/env python3
"""L6b: so kết quả check_l6b: new vs orig và off vs orig. Dùng: compare_l6b.py OUTDIR > bảng"""
import sys, os, json, glob
OUT = sys.argv[1]; bad = 0; n = 0
names = {}
for f in sorted(glob.glob(os.path.join(OUT, 'raw_*', 'sc_*.json'))):
    b = os.path.basename(f)[3:-5]; p = b.rsplit('_', 2); nm, sz, mode = p[0], p[1], p[2]
    names.setdefault((sz, nm), {})[mode] = json.load(open(f))
def norm(d):
    return json.dumps(d, sort_keys=True)
for (sz, nm), d in sorted(names.items()):
    o = d.get('orig')
    for mode in ('new', 'off'):
        x = d.get(mode)
        if o is None or x is None: print('THIEU', sz, nm, mode, 'orig' if o is None else '', 'x' if x is None else ''); bad += 1; continue
        n += 1
        if 'ERR' in x or 'ERR' in o: print('ERR  ', sz, nm, mode, (x.get('ERR') or o.get('ERR'))); bad += 1; continue
        if norm(x) == norm(o): print('DAT  ', sz, nm, mode)
        else: bad += 1; print('LECH ', sz, nm, mode, '\n   %s: %s\n   orig: %s' % (mode, norm(x)[:300], norm(o)[:300]))
print('tong', n, 'lech', bad)
