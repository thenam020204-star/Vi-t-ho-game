import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l6'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'l3'))
from qlib import *
W, Hh = 400, 240
c = Ctx(W, Hh, 'new', '/tmp/l6b_dbg', boot_to=10); r = c.r
r.step(700); r.key('OK'); r.step(10)
c.tap(0x10 + 4, 6); c.step(5)
print('st', c.st(), c.sub(), {k: v for k, v in sorted(c.hits().items())})
at = lambda h: c.tap(h, 6)
at(0x71); at(0x71); print([c.s8(c.top(), o) for o in range(0xc, 0x12)]); print(c.hits()[0x70], c.hits()[1])
c.shot('dbg')
at(0x70); print([c.s8(c.top(), o) for o in range(0xc, 0x12)])
