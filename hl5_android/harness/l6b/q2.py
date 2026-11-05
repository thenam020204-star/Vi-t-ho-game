"""L6b: dò buffer mô tả popup nhiệm vụ (qm+28) ở chế độ 3 và 4."""
import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l6'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'l3'))
from qlib import *
W, Hh = int(sys.argv[1]), int(sys.argv[2])
c = Ctx(W, Hh, os.environ.get('MODE', 'new'), '/tmp/l6b_q2', boot_to=1000); r = c.r; g = c.g
qm = c.u32(c.gv + 5580)
for i in (0, 1, 2): c.r.call(0xcdcc4, qm, i, 1)
for i in (0, 1, 2): c.r.call(0xd0a68, qm, i)
c.step(4); q = c.top()
def dump(tag):
    print(tag, 'mode', c.b8(q, 0xc2b), 'sel', c.b8(q, 0xc29), 'scr', c.b8(q, 0xc2a), 'nbtn', c.s8(q, 0x10), 'res', c.s8(c.u32(q + 0x14)), 'lines21c/d/e', c.b8(qm, 0x21c), c.b8(qm, 0x21d), c.b8(qm, 0x21e))
    print('  buf', bytes(g.uc.mem_read(qm + 28, 160)))
    print('  w/h', c.s16(qm + 616), c.s16(qm + 618), 'pos', [c.s16(qm + 556 + 2 * i) for i in range(8)])
    print('  btn', [bytes(g.uc.mem_read(q + 0x43c + 48 * i, 8)) for i in range(2)])
dump('m3')
c.key('DOWN', 2); dump('after DOWN')
c.key('LEFT', 2); dump('after LEFT')
c.key('OK', 3); dump('after OK (reward)'); c.shot('mode4')
c.key('DOWN', 2); dump('after DOWN')
c.key('OK', 3); dump('after OK back')
c.key('RIGHT', 2); dump('after RIGHT')
c.key('OK', 3); print('after OK2 cls', hex(c.cls()), 'top', hex(c.top()), 'q', hex(q))
