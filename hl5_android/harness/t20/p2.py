"""T20-P2: ai gọi host memcpy/memset nhiều nhất trong paint (theo LR -> hàm guest + cỡ): p2.py W H [ticks]"""
import sys, os, bisect, subprocess, collections; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t20lib import *
W, Hh = int(sys.argv[1]), int(sys.argv[2]); N = int(sys.argv[3]) if len(sys.argv) > 3 else 6
r = mkd(W, Hh, 'dev', 'p2'); to_play(r, 1300); uc = r.g.uc
nm = subprocess.run(['llvm-nm', '-C', '--defined-only', os.path.expanduser('~/hl5work/apk/assets/libHeroesLore5.so')], capture_output=True, text=True).stdout.splitlines()
syms = sorted((int(l.split(' ', 2)[0], 16), l.split(' ', 2)[2]) for l in nm if len(l.split(' ', 2)) == 3); keys = [s[0] for s in syms]
msym = sorted((v, k) for k, v in r.sym.items()); mk_ = [s[0] for s in msym]
def who(o):
    if o >= 0x171000: i = bisect.bisect_right(mk_, o) - 1; return msym[i][1]
    i = bisect.bisect_right(keys, o) - 1; return syms[i][1]
C = collections.Counter(); on = [False]
def mk(nm_):
    def h(uc, a, s, u):
        if on[0]: C[(nm_, who(uc.reg_read(UC_ARM_REG_LR) - BASE)[:60], min(uc.reg_read(UC_ARM_REG_R2) if nm_ == 'memcpy' else uc.reg_read(UC_ARM_REG_R2), 100000))] += 1
    return h
uc.hook_add(UC_HOOK_CODE, mk('memcpy'), begin=BASE + 0x30110, end=BASE + 0x30110); uc.ctl_flush_tb()
on[0] = True; ev(r, 'RIGHT', True)
for i in range(N): r.step(1)
for k, v in C.most_common(10): print(v // N, 'lần/tick', k)
