"""T18b-0: phân bổ chi phí mainPaint theo hàm (byte khối cơ bản / tick), bản gốc. p11.py W H [ticks]"""
import sys, os, bisect, subprocess; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18lib import *
from unicorn import UC_HOOK_BLOCK
W, Hh = int(sys.argv[1]), int(sys.argv[2]); N = int(sys.argv[3]) if len(sys.argv) > 3 else 10
r = mk(W, Hh, 'orig', 't18'); to_play(r, 1300)
nm = subprocess.run(['llvm-nm', '-C', '--defined-only', os.path.expanduser('~/hl5work/apk/assets/libHeroesLore5.so')], capture_output=True, text=True).stdout.splitlines()
syms = sorted((int(l.split(' ', 2)[0], 16), l.split(' ', 2)[2]) for l in nm if len(l.split(' ', 2)) == 3)
keys = [s[0] for s in syms]
cnt = {}; on = [False]
def hb(uc, a, s, u):
    if on[0]:
        i = bisect.bisect_right(keys, a - BASE) - 1; n = syms[i][1] if i >= 0 else '?'
        cnt[n] = cnt.get(n, 0) + s
r.g.uc.hook_add(UC_HOOK_BLOCK, hb); r.g.uc.ctl_flush_tb(); on[0] = True
for i in range(N): r.step(1)
tot = sum(cnt.values())
print('tong/tick', tot // N)
for k, v in sorted(cnt.items(), key=lambda x: -x[1])[:25]: print('%6.1f%% %9d %s' % (100 * v / tot, v // N, k))
