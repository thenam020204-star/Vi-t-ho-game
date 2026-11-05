"""T18a-1d: ghi bộ nhớ trong paint thêm (trong tick, trước EndProc), nhóm theo hàm guest gọi. p6.py W H ticks"""
import sys, os, bisect; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18lib import *
from hl5libc import KEYS
from unicorn import UC_HOOK_MEM_WRITE
W, Hh = int(sys.argv[1]), int(sys.argv[2]); N = int(sys.argv[3])
r = mk(W, Hh, 'orig', 't18'); to_play(r, 1300)
g = r.g; on = [False]; fbh = {}
syms = sorted((v - BASE, k) for k, v in g.syms.items() if v >= BASE)
def fn(a): i = bisect.bisect_right([s[0] for s in syms], a) - 1; return syms[i][1] if i >= 0 else '?'
def on_flush(uc, a, s, u): fbh['fb'] = uc.reg_read(UC_ARM_REG_R1)
g.uc.hook_add(UC_HOOK_CODE, on_flush, begin=BASE + 0x3d9d4, end=BASE + 0x3d9d4)
g.uc.ctl_flush_tb(); r.step(2)
fb = fbh['fb']; fbe = fb + W * Hh * 2 + 0x100
W_ = {}
allocs = []
def mw(uc, t, a, sz, v, u):
    if not on[0] or fb <= a < fbe: return
    pc = uc.reg_read(15); lr = uc.reg_read(UC_ARM_REG_LR)
    k = (fn(lr - BASE), 'guest' if BASE <= pc < BASE + 0x171000 else 'stub')
    d = W_.setdefault(k, [0, set()]); d[0] += 1; d[1].add(a >> 8)
g.uc.hook_add(UC_HOOK_MEM_WRITE, mw, begin=0x10000000, end=0x203fffff)
def inhook(uc, a, s, u): on[0] = True; r.call(0x987c0); on[0] = False
g.uc.hook_add(UC_HOOK_CODE, inhook, begin=BASE + 0x99fa8, end=BASE + 0x99fa8)
g.uc.ctl_flush_tb()
def ev(c, d): g.call(r.keyf, g.env, 0, d, c & 0xffffffff, 0)
for t in range(N):
    if t % 40 == 5: ev(KEYS['RIGHT'], 2)
    if t % 40 == 25: ev(KEYS['RIGHT'], 3); ev(KEYS['OK'], 2)
    if t % 40 == 28: ev(KEYS['OK'], 3)
    if t % 40 == 30: ev(KEYS['LEFT'], 2)
    if t % 40 == 38: ev(KEYS['LEFT'], 3)
    r.step(1)
for k, (n, pg) in sorted(W_.items(), key=lambda x: -x[1][0])[:30]: print(n, len(pg), k)
