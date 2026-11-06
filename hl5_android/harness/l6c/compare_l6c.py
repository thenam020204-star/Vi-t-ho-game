#!/usr/bin/env python3
"""So new/off với orig theo từng kịch bản. Dùng: compare_l6c.py OUTDIR [tiền tố kịch bản]"""
import sys, os, json, glob, re
OUT = sys.argv[1]; pre = sys.argv[2] if len(sys.argv) > 2 else ''
tot = ok = 0; bad = []
for sz in ('400x240', '600x270', '1200x540'):
    d = os.path.join(OUT, 'raw_' + sz)
    names = sorted({re.match(r'sc_(.+?)_%s_' % sz, os.path.basename(f)).group(1) for f in glob.glob(d + '/sc_*.json')})
    for n in names:
        if not n.startswith(pre): continue
        L = {}
        for m in ('orig', 'new', 'off'):
            f = '%s/sc_%s_%s_%s.json' % (d, n, sz, m)
            L[m] = json.load(open(f)) if os.path.exists(f) else None
        for m in ('new', 'off'):
            tot += 1
            a, b = L['orig'], L[m]
            if a is None or b is None: bad.append((sz, n, m, 'thiếu')); continue
            if 'ERR' in a or 'ERR' in b: bad.append((sz, n, m, 'ERR %s | %s' % (str(a.get('ERR'))[:80], str(b.get('ERR'))[:80]))); continue
            a = {k: v for k, v in a.items() if k not in ('hits_e0',)}; b = {k: v for k, v in b.items() if k not in ('hits_e0',)}
            if a == b: ok += 1
            else: bad.append((sz, n, m, 'lệch: orig=%s new=%s' % (json.dumps(a)[:300], json.dumps(b)[:300])))
print('ĐẠT %d/%d' % (ok, tot))
for x in bad: print('LỖI', *x)
