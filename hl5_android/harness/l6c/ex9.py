import sys, os, struct
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l6'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'l3'))
from qlib import *
W, Hh, MODE = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3]
c = Ctx(W, Hh, MODE, '/tmp/l6c_ex', boot_to=1000); r = c.r
tbl = c.u32(c.gv + 5132); h = c.hero
n = r.call(0x9b3d4, tbl, 15) & 0xffff
arr = c.u32(h + 0x308)
print('len15', n, 'arr', hex(arr), 'cnt', c.b8(h, 0x30c), bytes(c.g.uc.mem_read(arr, 24)).hex() if arr else None)
buf = c.g.alloc(512) if hasattr(c.g, 'alloc') else None
print('alloc', buf)
c.S3(4, 8); t = c.top()
for i in range(0, 116, 2): c.wr(arr + i, [1])
c.r.call(0xe2eb8, t, 9); c.step(4)
c.wr(t + 0x11, [0]); c.wr(t + 0x12, [0]); c.step(3); c.shot('f9a')
for k in ('RIGHT', 'RIGHT', 'DOWN'): c.key(k, 4)
print('cur', c.s8(t, 0x11), c.s8(t, 0x12)); c.shot('f9b')
c.key('OK', 6); print('after OK', hex(c.cls()), c.s8(t, 0xd), c.s8(t, 0xe), c.s8(t, 0x10)); c.shot('f9c')
