import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
c = Ctx(600, 270, 'orig', os.path.expanduser('~/hl5work_l1/p'))
qm = c.u32(c.gv + 5580)
c.S3(8, 6); t = c.top()
g = c.u32(t + 0x3fc); print('grid ptr', hex(g), 'cols', c.s8(t,0x4c), 'rows', c.s8(t,0x4d))
mem = bytes(c.g.uc.mem_read(g, 5 * 72))
for r in range(72):
    row = mem[r*5:(r+1)*5]
    if any(b != 255 for b in row): print(r, [b if b != 255 else '.' for b in row])
print([ (c.s16(t+o)) for o in (0x34,0x36,0x38,0x3a,0x3c,0x3e,0x48,0x4a)])
