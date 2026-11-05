"""T18a-2: dò biến camera: Map* (r0 của Map::Paint 0xb129c) đổi những trường nào theo tick khi hero đi; toạ độ hero (OBJECT::GetX 0xcbc58/GetY 0xcbc68). p8.py W H"""
import sys, os, struct; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18lib import *
from unicorn.arm_const import UC_ARM_REG_R0
from hl5libc import KEYS
W, Hh = int(sys.argv[1]), int(sys.argv[2])
r = mk(W, Hh, 'orig', 't18'); to_play(r, 1300); g = r.g
st = {}; hero = []
def on_mp(uc, a, s, u): st['map'] = uc.reg_read(UC_ARM_REG_R0)
g.uc.hook_add(UC_HOOK_CODE, on_mp, begin=BASE + 0xb129c, end=BASE + 0xb129c)
g.uc.ctl_flush_tb(); r.step(2)
m = st['map']; print('Map*', hex(m))
def ev(c, d): g.call(r.keyf, g.env, 0, d, c & 0xffffffff, 0)
prev = None; ev(KEYS['RIGHT'], 2)
chg = {}
for t in range(30):
    r.step(1)
    cur = bytes(g.uc.mem_read(m, 0x400))
    if prev:
        for o in range(0, 0x400, 2):
            if cur[o:o+2] != prev[o:o+2]: chg.setdefault(o, []).append(struct.unpack_from('<h', cur, o)[0])
    prev = cur
for o, v in sorted(chg.items()):
    if len(v) >= 3: print(hex(o), len(v), v[:14])
ev(KEYS['RIGHT'], 3)
