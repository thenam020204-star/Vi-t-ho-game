import json, sys
f, lab = sys.argv[1], sys.argv[2]; K = int(sys.argv[3]) if len(sys.argv) > 3 else 14
d = json.load(open(f)); w = d['windows'][lab]; s = w['snap']
print(lab, 'frames', w['n'], 'total/frame', s['total'], 'host calls/frame', sum(s['host'].values()), 'memcpy/memset bytes', s['hostb'])
ph = {}
for row in w['per']:
    for k, v in row.items():
        if k not in ('frame',): ph.setdefault(k, []).append(v)
print('phase avg:', {k: sum(v) // len(v) for k, v in ph.items()})
print('-- exclusive'); 
for k, v in sorted(s['excl'].items(), key=lambda x: -x[1])[:K]: print('  %9d %5.1f%% %s  calls %.1f' % (v, 100 * v / s['total'], k[:90], s['calls'].get(k, 0)))
print('-- inclusive')
for k, v in sorted(s['incl'].items(), key=lambda x: -x[1])[:K]: print('  %9d %5.1f%% %s  calls %.1f' % (v, 100 * v / s['total'], k[:90], s['calls'].get(k, 0)))
print('-- host', sorted(s['host'].items(), key=lambda x: -x[1])[:8])
