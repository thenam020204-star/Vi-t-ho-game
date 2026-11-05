"""T18a-2b: đối số MapLayerDraw 0xae294 / WARRIOR::Draw / Monster::Draw mỗi tick khi hero đi phải; Map+0xa8.. ; OBJECT x,y. p9.py W H"""
import sys, os, struct; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18lib import *
from unicorn.arm_const import UC_ARM_REG_R0, UC_ARM_REG_R2, UC_ARM_REG_R3, UC_ARM_REG_SP
from hl5libc import KEYS
W, Hh = int(sys.argv[1]), int(sys.argv[2])
r = mk(W, Hh, 'orig', 't18'); to_play(r, 1300); g = r.g
nm = {}
for l in open(os.path.expanduser('~/hl5work/so.dis')):
    import re
    m = re.match(r'^([0-9a-f]{8}) <((WARRIOR|Monster|Map|GUNNER|ROGUE)::(Draw|MapLayerDraw)\()', l)
    if m: nm[int(m.group(1), 16)] = m.group(2)[:-1]
print({hex(k): v for k, v in nm.items()})
log = []
def hk(uc, a, s, u):
    sp = uc.reg_read(UC_ARM_REG_SP); rd = lambda o: struct.unpack('<i', bytes(uc.mem_read(sp + o, 4)))[0]
    log.append((nm[a - BASE], uc.reg_read(UC_ARM_REG_R0) - 0, uc.reg_read(2) if False else None, [struct.unpack('<i', struct.pack('<I', uc.reg_read(x)))[0] for x in (UC_ARM_REG_R0 + 1, UC_ARM_REG_R0 + 2, UC_ARM_REG_R0 + 3)], [rd(0), rd(4)]))
for a in nm: g.uc.hook_add(UC_HOOK_CODE, hk, begin=BASE + a, end=BASE + a)
g.uc.ctl_flush_tb(); r.step(2)
def ev(c, d): g.call(r.keyf, g.env, 0, d, c & 0xffffffff, 0)
ev(KEYS['RIGHT'], 2)
for t in range(5):
    log.clear(); r.step(1)
    print('tick', t)
    for x in log[:12]: print('  ', x[0], hex(x[1]), x[3], x[4])
ev(KEYS['RIGHT'], 3)
