"""T18a-4: chi phí (byte khối cơ bản guest, ~4 B/lệnh) mỗi pha của một tick: pre(KeyPressed+StateMgr) / Process / Paint / EndProc / Flush+sau. p7.py W H [ticks] [orig|new]"""
import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18lib import *
from unicorn import UC_HOOK_BLOCK
W, Hh = int(sys.argv[1]), int(sys.argv[2]); N = int(sys.argv[3]) if len(sys.argv) > 3 else 20; mode = sys.argv[4] if len(sys.argv) > 4 else 'orig'
r = mk(W, Hh, mode, 't18'); to_play(r, 1300)
P = {0x99d98: 'pre', 0x98a78: 'process', 0x987c0: 'paint', 0x98760: 'endproc', 0x3d9d4: 'flush'}
cur = ['x']; cnt = {}; on = [False]
def hb(uc, a, s, u):
    if on[0]: cnt[cur[0]] = cnt.get(cur[0], 0) + s
def hp(uc, a, s, u): cur[0] = P[a - BASE]
for a in P: r.g.uc.hook_add(UC_HOOK_CODE, hp, begin=BASE + a, end=BASE + a)
r.g.uc.hook_add(UC_HOOK_BLOCK, hb); r.g.uc.ctl_flush_tb(); on[0] = True
for i in range(N): r.step(1)
tot = sum(cnt.values())
print(W, Hh, mode, {k: v // N for k, v in cnt.items()}, 'tong', tot // N, 'paint%%=%.0f' % (100 * cnt['paint'] / tot))
