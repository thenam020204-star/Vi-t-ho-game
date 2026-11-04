import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1b'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
W, Hh, MODE = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3]
par = int(sys.argv[4]) if len(sys.argv) > 4 else 0
out = os.path.expanduser('~/hl5work_l1b/p')
c = Ctx(W, Hh, MODE, out); r = c.r; g = c.g
c.r.call(0x428bc, 0, 7, par); c.step(6)
t = c.top(); print('cls', hex(c.cls()), 'top', hex(t), 'fields', [c.s8(t, o) for o in range(0xc, 0x14)])
print('+0x2dc', hex(c.u32(t+0x2dc)), hex(c.u32(t+0x2e0)))
c.shot('map_p%d' % par)
for k in sys.argv[5:]:
    c.key(k, 5); print(k, [c.s8(t, o) for o in range(0xc, 0x14)]); c.shot('map_p%d_%s' % (par, k))
import struct
def dump():
    m = bytes(g.uc.mem_read(t, 0x3e0))
    print('hdr +0x0..0x30', m[0:0x30].hex(' '))
    print('s16 14,16,18,20,22,24,26,28..', struct.unpack_from('<9h', m, 14))
    print('1a,1b,1c,1d,38,39', m[0x1a], m[0x1b], m[0x1c], m[0x1d], m[0x38], m[0x39], 'w/h', struct.unpack_from('<2h', m, 70), 'c/r', m[0x42], m[0x43])
    for k in range(10):
        o = 0x2e0 + 8 * k; print(hex(o), m[o:o+8].hex(' '), struct.unpack_from('<hh', m, o + 8))
    print('3d4', m[0x3d4])
if os.environ.get("DUMP"): dump()
