"""T20-A1: không cấp phát: đếm lệnh gọi host (theo tên) trong N tick chơi (đi phải) ở base vs dev; heap (số khối, top) trước/sau. a1.py W H [ticks=40]"""
import sys, os, collections; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t20lib import *
W, Hh = int(sys.argv[1]), int(sys.argv[2]); N = int(sys.argv[3]) if len(sys.argv) > 3 else 40
res = {}
for dev in ('base', 'dev'):
    r = mkd(W, Hh, dev, 'a1'); to_play(r, 1300); g = r.g
    log = collections.Counter(); sizes = collections.Counter()
    for a, (nm, fn) in list(g.handlers.items()):
        def mkw(nm, fn):
            def w(*x, **k):
                log[nm] += 1
                if nm == 'memcpy': sizes[g.uc.reg_read(UC_ARM_REG_R2)] += 1
                return fn(*x, **k)
            return w
        g.handlers[a] = (nm, mkw(nm, fn))
    h0 = (len(g.heap.sizes), g.heap.top); ev(r, 'RIGHT', True); t0 = r.ticks
    for i in range(N): r.step(1)
    h1 = (len(g.heap.sizes), g.heap.top); tk = r.ticks - t0
    res[dev] = (log, h0, h1, tk, sizes)
    print(dev, 'ticks', tk, 'heap trước', h0, 'sau', h1, '| host gọi:', dict(log), '| memcpy theo cỡ (top):', sizes.most_common(4))
lb, ld = res['base'][0], res['dev'][0]
diff = {k: (lb[k], ld[k]) for k in set(lb) | set(ld) if lb[k] != ld[k]}
print('khác nhau giữa base và dev (base, dev):', diff)
print('KẾT:', 'heap đổi base=%s dev=%s' % (res['base'][1] != res['base'][2], res['dev'][1] != res['dev'][2]), '; malloc/calloc/free: base', [lb[k] for k in ('malloc', 'calloc', 'free')], 'dev', [ld[k] for k in ('malloc', 'calloc', 'free')])
