"""T20-1: ghi mọi lần gọi internalGrpCopyFrameBuffer (0x144cdc) trong ~N vòng cảnh chơi: tham số + struct FB/ctx. p1.py W H dev [N]"""
import sys, os, struct, collections; sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't18'))
from t18b import *
W, Hh, dev = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3]; N = int(sys.argv[4]) if len(sys.argv) > 4 else 60
r = mkd(W, Hh, dev, 'p20'); to_play(r, 1300); g = r.g; uc = g.uc
rd = lambda a: g.rd32(a)
calls = []
def h(uc, a, s, u):
    R = lambda n: uc.reg_read(n)
    sp = R(UC_ARM_REG_SP)
    args = [R(UC_ARM_REG_R0), R(UC_ARM_REG_R1), R(UC_ARM_REG_R2), R(UC_ARM_REG_R3)] + [rd(sp + 4 * i) for i in range(6)]
    sg = lambda v: v - (1 << 32) if v & 0x80000000 else v
    dst, src, ctx = args[0], args[5], args[8]
    fbs = lambda p: tuple(sg(rd(p + o)) if o != 0x10 else rd(p + o) for o in (0, 4, 8, 0xc, 0x10))
    cx = tuple(sg(rd(ctx + o)) for o in (4, 8, 0xc, 0x10, 0x1c, 0x20, 0x2c, 0x30))
    calls.append(((args[1], args[2], args[3], args[4], args[6], args[7]), args[9] & 0xff, fbs(dst), fbs(src), cx, dst == src, args[8]))
uc.hook_add(UC_HOOK_CODE, h, begin=BASE + 0x144cdc, end=BASE + 0x144cdc)
uc.ctl_flush_tb()
ev(r, 'RIGHT', True)
for i in range(N): vloop(r)
ev(r, 'RIGHT', False)
print('calls', len(calls), 'ticks', r.ticks, 'hostfb(flush r1)', hex(r.hostfb), 'dst.data', hex(calls[0][2][4]), 'src.data', hex(calls[0][3][4]))
C = collections.Counter()
for p, key, d, s, cx, same, ctx in calls: C[(key, d[3], s[3], cx[5], cx[6], same)] += 1
print('(key, dstdepth, srcdepth, alpha, cb, same) ->', dict(C))
for c in calls[:8]: print(c)
# so sánh dst/src: địa chỉ vùng
D = collections.Counter((c[2], c[3]) for c in calls); print(len(D)); 
for k, v in list(D.items())[:6]: print(v, k)
