import sys; sys.path.insert(0, '/home/user/Vi-t-ho-game/hl5_android/harness/t17c2')
from qlib import *
W, Hh = int(sys.argv[1]), int(sys.argv[2])
c = Ctx(W, Hh, 'new', '/tmp/claude-0/q1')
c.S3(0); c.shot('menu3x3')
c.S3(1); c.shot('nhan_vat')
c.S3(3); c.shot('equip')
print('zone byte', c.b8(c.u32(c.gv + 5204) + 0x8d))
