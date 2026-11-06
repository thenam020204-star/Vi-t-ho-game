"""T20-STK: byte stack khác giữa base và dev nằm DƯỚI sp (vùng chết) hay trên sp (trạng thái sống)? stk.py W H [ticks=20]
 Kiểm lúc ranh giới nativeLoop (g.call trả về), nơi sp của guest = đỉnh khung gọi."""
import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t20lib import *
W, Hh = int(sys.argv[1]), int(sys.argv[2]); N = int(sys.argv[3]) if len(sys.argv) > 3 else 20
rs = [mkd(W, Hh, d, 'stk') for d in ('base', 'dev')]
evs = {}
for it in ROUTE.split(','):
    f, k = it.split(':'); evs.setdefault(int(f), []).append(k)
for fr in range(1300):
    for r in rs:
        for k in evs.get(fr, []): r.key(k)
        r.step(1)
for r in rs: ev(r, 'RIGHT', True)
for i in range(N):
    for r in rs: r.step(1)
    sp = [r.g.uc.reg_read(UC_ARM_REG_SP) for r in rs]
    a = np.frombuffer(bytes(rs[0].g.uc.mem_read(0x70000000, 0x400000)), np.uint8); b = np.frombuffer(bytes(rs[1].g.uc.mem_read(0x70000000, 0x400000)), np.uint8)
    idx = np.nonzero(a != b)[0] + 0x70000000
    print(i, 'sp', [hex(s) for s in sp], 'stack byte khác', len(idx), 'trên/bằng sp:', int((idx >= sp[0]).sum()), 'dải', (hex(idx.min()), hex(idx.max())) if len(idx) else '-')
