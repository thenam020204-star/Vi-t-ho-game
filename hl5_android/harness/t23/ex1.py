import sys; sys.path.insert(0, '/home/user/Vi-t-ho-game/hl5_android/harness/t23')
from t23lib import *
W, Hh = int(sys.argv[1]), int(sys.argv[2]); END = int(sys.argv[3]) if len(sys.argv) > 3 else 1400
c = mk(W, Hh, 0); r = c.r; g = c.g
ev = {}
for it in ROUTE.split(','):
    f, k = it.split(':'); f = int(f)
    if f < 960: ev.setdefault(f, []).append(k)
okat = set(int(x) for x in os.environ.get('OKAT', '970').split(','))
tc = [0]; sd = []
def h_tc(uc, a, s, u): tc[0] += 1
g.uc.hook_add(UC_HOOK_CODE, h_tc, begin=BASE + 0x99d98, end=BASE + 0x99d98)
def h_st(uc, a, s, u): sd.append((uc.reg_read(UC_ARM_REG_R0), uc.reg_read(UC_ARM_REG_R1), uc.reg_read(UC_ARM_REG_R2)))
g.uc.hook_add(UC_HOOK_CODE, h_st, begin=BASE + 0x36100, end=BASE + 0x36100)
g.uc.ctl_flush_tb()
GV = 0x16a238
t0 = g.vclock
while r.fr < END:
    for k in ev.get(r.fr, []): r.key(k)
    if r.fr in okat: r.key('OK')
    n0 = tc[0]; sd.clear()
    r.step(1)
    ep = r.rd(GV + 5204); e75 = r.rdb(ep + 0x75)[0] if ep else -1
    top = r.top(); st = r.b8(top, 0xc) if False else r.rdb(top + 0xc)[0]; sub = r.rdb(top + 0xd)[0]
    hero = r.rd(GV + 5180)
    if r.fr % 10 == 0 or '-v' in sys.argv:
        print(r.fr, 'tc', tc[0] - n0, 'e75', e75, 'state', st, sub, 'cls %x' % r.cls(), 'timer', sd[:2], 'vclock', g.vclock)
    if r.fr % 100 == 0: c.shot('ex1_%d' % r.fr)
