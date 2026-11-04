import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
c = Ctx(400, 240, 'orig', os.path.expanduser('~/hl5work_l1/p')); g = c.g; r = c.r
tbl = c.u32(c.gv + 5132)
p = g.heap.alloc(0x800)
for cat in range(0, 20):
    out = []
    for i in range(0, 128):
        c.wr(p, bytes(0x800))
        r.call(0x9e670, tbl, p, cat, i)
        b = bytes(g.uc.mem_read(p, 0x200))
        if b[20] in (5, 6) and b[0x155] == 7: out.append((i, b[20]))
    if out: print(cat, out[:12], flush=True)
