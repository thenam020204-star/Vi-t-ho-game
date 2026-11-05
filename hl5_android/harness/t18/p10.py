"""T18a-2c: thử đổi trường camera Map+0xc0.. rồi mainPaint thêm: ảnh có dịch đúng k px không? p10.py W H"""
import sys, os, struct; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18lib import *
from unicorn.arm_const import UC_ARM_REG_R0
from hl5libc import KEYS
W, Hh = int(sys.argv[1]), int(sys.argv[2])
r = mk(W, Hh, 'orig', 't18'); to_play(r, 1300); g = r.g
st = {}
def on_mp(uc, a, s, u): st['map'] = uc.reg_read(UC_ARM_REG_R0)
def on_flush(uc, a, s, u): st['fb'] = uc.reg_read(UC_ARM_REG_R1)
g.uc.hook_add(UC_HOOK_CODE, on_mp, begin=BASE + 0xb129c, end=BASE + 0xb129c)
g.uc.hook_add(UC_HOOK_CODE, on_flush, begin=BASE + 0x3d9d4, end=BASE + 0x3d9d4)
g.uc.ctl_flush_tb(); r.step(2); m = st['map']; fb = st['fb']
VARS = {'c0': [(0xc0, 1)], 'c0,c8,d0,d8': [(0xc0, 1), (0xc8, 1), (0xd0, 1), (0xd8, 1)], 'c0..d8+e0,e8': [(0xc0, 1), (0xc8, 1), (0xd0, 1), (0xd8, 1), (0xe0, 1), (0xe8, -1)], 'all8+a8,b0': [(0xc0, 1), (0xc8, 1), (0xd0, 1), (0xd8, 1), (0xe0, 1), (0xe8, -1), (0xa8, -1), (0xb0, -1)]}
res = {}
def rdfb(): return np.frombuffer(bytes(g.uc.mem_read(fb, W * Hh * 2)), np.uint16).reshape(Hh, W).copy()
def inhook(uc, a, s, u):
    f0 = rdfb()
    for name, fl in VARS.items():
        for k in (3, 6):
            saved = bytes(uc.mem_read(m, 0x100))
            for o, sg in fl:
                v = struct.unpack('<i', saved[o:o + 4])[0]; uc.mem_write(m + o, struct.pack('<i', v + sg * k))
            r.call(0x987c0); f1 = rdfb(); uc.mem_write(m, saved)
            y0, y1 = Hh // 4, Hh * 3 // 4; x0, x1 = 60, W - 80
            best = {}
            for d in range(-9, 10):
                a_ = f1[y0:y1, x0:x1]; b_ = f0[y0:y1, x0 + d:x1 + d]
                best[d] = float((a_ == b_).mean())
            bd = max(best, key=best.get)
            res.setdefault(name, []).append((k, bd, round(best[bd], 3), round(best[0], 3)))
    r.call(0x987c0)    # trả FB về trạng thái gốc
g.uc.hook_add(UC_HOOK_CODE, inhook, begin=BASE + 0x99fa8, end=BASE + 0x99fa8)
g.uc.ctl_flush_tb()
def ev(c, d): g.call(r.keyf, g.env, 0, d, c & 0xffffffff, 0)
ev(KEYS['RIGHT'], 2); r.step(1)
res.clear(); r.step(1)
for k, v in res.items(): print(k, v)
ev(KEYS['RIGHT'], 3)
