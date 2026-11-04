import json, sys, glob, os
d = sys.argv[1]; tag = sys.argv[2]
bad = 0
for f in sorted(glob.glob(os.path.join(d, 'new', 'raw_%s' % tag, 'sc_*_%s_new.json' % tag))):
    name = os.path.basename(f)[3:-len('_%s_new.json' % tag)]
    fo = os.path.join(d, 'orig', 'raw_%s' % tag, 'sc_%s_%s_orig.json' % (name, tag))
    if not os.path.exists(fo): print('THIEU', name); continue
    a, b = json.load(open(fo)), json.load(open(f))
    if isinstance(a, dict) or isinstance(b, dict): print('LOI', name, str(a)[:200], str(b)[:300]); bad += 1; continue
    ok = True
    for i, (x, y) in enumerate(zip(a, b)):
        if x != y: ok = False; print('LECH', name, 'buoc', i, '\n  goc', x, '\n  moi', y)
    print('DAT' if ok else 'LECH', name, len(a)); bad += not ok
print('TONG LECH', bad)
