"""T20-M1: chi phí guest (byte khối cơ bản ~ 4 B/lệnh) mỗi pha của tick + top hàm, bản base (không T20) hoặc dev (T20); đứng yên / đi phải.
  m1.py W H dev [ticks=12] [move=1]   (to_play 1300 rồi RIGHT giữ nếu move) ; đếm thêm số lần/byte host memcpy trong paint"""
import sys, os, bisect, subprocess; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t20lib import *
from unicorn import UC_HOOK_BLOCK
W, Hh, dev = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3]; N = int(sys.argv[4]) if len(sys.argv) > 4 else 12; MOVE = int(sys.argv[5]) if len(sys.argv) > 5 else 1
r = mkd(W, Hh, dev, 'm1'); to_play(r, 1300); uc = r.g.uc
if MOVE: ev(r, 'RIGHT', True); vloop(r); vloop(r)
nm = subprocess.run(['llvm-nm', '-C', '--defined-only', os.path.expanduser('~/hl5work/apk/assets/libHeroesLore5.so')], capture_output=True, text=True).stdout.splitlines()
syms = sorted((int(l.split(' ', 2)[0], 16), l.split(' ', 2)[2]) for l in nm if len(l.split(' ', 2)) == 3)
msym = sorted((v, k) for k, v in r.sym.items() if not k.startswith('mui_') or True)
keys = [s[0] for s in syms]; mk_ = [s[0] for s in msym]
P = {0x99d98: 'pre', 0x98a78: 'process', 0x987c0: 'paint', 0x98760: 'endproc', 0x3d9d4: 'flush'}
cur = ['x']; cnt = {}; fn = {}; on = [False]; mc = {'calls': 0, 'bytes': 0}
def hb(uc, a, s, u):
    if not on[0]: return
    cnt[cur[0]] = cnt.get(cur[0], 0) + s
    o = a - BASE
    if o >= 0x171000:
        i = bisect.bisect_right(mk_, o) - 1; n = msym[i][1] if i >= 0 else '?'
    else:
        i = bisect.bisect_right(keys, o) - 1; n = syms[i][1] if i >= 0 else '?'
    fn[n] = fn.get(n, 0) + s
def hp(uc, a, s, u): cur[0] = P[a - BASE]
def hm(uc, a, s, u):
    if on[0]: mc['calls'] += 1; mc['bytes'] += uc.reg_read(UC_ARM_REG_R2)
for a in P: uc.hook_add(UC_HOOK_CODE, hp, begin=BASE + a, end=BASE + a)
uc.hook_add(UC_HOOK_CODE, hm, begin=BASE + 0x30110, end=BASE + 0x30110)   # PLT memcpy
uc.hook_add(UC_HOOK_BLOCK, hb); uc.ctl_flush_tb(); on[0] = True
t0 = r.ticks
for i in range(N): r.step(1)
tk = max(r.ticks - t0, 1); tot = sum(cnt.values())
print('%dx%d %s move=%d ticks=%d: %s | tổng/tick %.2fM (byte khối) ~ %.2fM lệnh | paint %.2fM | host memcpy/tick: %d lần, %.2f MB' % (W, Hh, dev, MOVE, tk, {k: round(v / tk / 1e6, 3) for k, v in cnt.items()}, tot / tk / 1e6, tot / tk / 4e6, cnt['paint'] / tk / 1e6, mc['calls'] / tk, mc['bytes'] / tk / 1e6))
for k, v in sorted(fn.items(), key=lambda x: -x[1])[:8]: print('   %5.1f%% %8.3fM %s' % (100 * v / tot, v / tk / 1e6, k))
