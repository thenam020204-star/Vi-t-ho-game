"""T18c-4: không cấp phát/gọi host trong khung trung gian; đếm hàm host gọi trong khung trung gian vs tick; heap (số khối, top) trước/sau. c4.py W H [vòng]"""
import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18b import *
W, Hh = int(sys.argv[1]), int(sys.argv[2]); N = int(sys.argv[3]) if len(sys.argv) > 3 else 80
r = mkd(W, Hh, 'dev', 'c4'); to_play(r, 1300); g = r.g
st_wr(r, 'flag', 2)
log = []
for a, (nm, fn) in list(g.handlers.items()):
    def mkw(nm, fn):
        def w(*x, **k): log.append(nm); return fn(*x, **k)
        return w
    g.handlers[a] = (nm, mkw(nm, fn))
ev(r, 'RIGHT', True)
mid_calls = {}; tick_calls = {}; heap_bad = 0; nm_ = 0; nt = 0
for i in range(N):
    log.clear(); t0, f0 = r.ticks, r.flushes; h0 = (len(g.heap.sizes), g.heap.top); m0 = st_rd(r, 'n_mid')
    vloop(r)
    h1 = (len(g.heap.sizes), g.heap.top); ismid = st_rd(r, 'n_mid') > m0 and r.ticks == t0
    tgt = mid_calls if ismid else tick_calls
    for n in log: tgt[n] = tgt.get(n, 0) + 1
    if ismid:
        nm_ += 1
        if h0 != h1: heap_bad += 1
    elif r.ticks > t0: nt += 1
print('khung trung gian', nm_, 'tick', nt, '| heap đổi ở', heap_bad, 'khung trung gian | host gọi trong KHUNG TRUNG GIAN:', mid_calls, '| host gọi trong TICK:', tick_calls)
