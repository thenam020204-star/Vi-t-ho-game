"""T20-COV: độ phủ kịch bản: theo từng khung (boot ROUTE + chơi + menu): lớp state trên cùng, cờ hội thoại, số lần fbcopy; đa dạng tham số. cov.py W H"""
import sys, os, collections; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t20lib import *
W, Hh = int(sys.argv[1]), int(sys.argv[2])
r = mkd(W, Hh, 'dev', 'cov'); g = r.g; uc = g.uc
GVB = BASE + 0x16a238
cnt = [0]; par = collections.Counter(); rng = {}
def h(uc, a, s, u):
    R = lambda n: uc.reg_read(n); sp = R(UC_ARM_REG_SP); sg = lambda v: v - (1 << 32) if v & 0x80000000 else v
    dx, dy, w, h_ = sg(R(UC_ARM_REG_R1)), sg(R(UC_ARM_REG_R2)), sg(R(UC_ARM_REG_R3)), sg(g.rd32(sp))
    sx, sy = sg(g.rd32(sp + 8)), sg(g.rd32(sp + 12)); cnt[0] += 1
    for k, v in (('dx', dx), ('dy', dy), ('w', w), ('h', h_), ('sx', sx), ('sy', sy)): rng[k] = (min(rng.get(k, (v, v))[0], v), max(rng.get(k, (v, v))[1], v))
    par[(dx != 0, dy != 0, sx != 0, sy != 0)] += 1
uc.hook_add(UC_HOOK_CODE, h, begin=BASE + r.sym['fbcopy'], end=BASE + r.sym['fbcopy']); uc.ctl_flush_tb()
evs = {}
for it in ROUTE.split(','):
    f, k = it.split(':'); evs.setdefault(int(f), []).append(k)
cov = collections.Counter()
def dlg():
    ep = g.rd32(GVB + 5204)
    return bool(ep and (bytes(uc.mem_read(ep + 0x75, 1))[0]))
def one(tag):
    c0 = cnt[0]; r.step(1); cov[(hex(r.cls()), dlg(), tag)] += 1; cov[('fbcopy_calls', hex(r.cls()), dlg(), tag)] += cnt[0] - c0
for fr in range(1300):
    for k in evs.get(fr, []): r.key(k)
    one('boot')
print('sau boot: hero', hero(r))
ev(r, 'RIGHT', True)
for i in range(150): one('play')
ev(r, 'RIGHT', False)
for k, v in sorted(cov.items(), key=lambda x: str(x[0])): print(k, v)
print('tham số (min,max):', rng); print('(dx!=0,dy!=0,sx!=0,sy!=0):', dict(par))
