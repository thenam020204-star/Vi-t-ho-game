import sys, os, struct
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l2'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't17c2'))
from qlib import *
c = Ctx(400, 240, 'orig', '/tmp/claude-0/o1')
c.r.call(0x42b08, 0, 7, 1); c.step(15)
for i in range(10):
    b = bytes(c.g.uc.mem_read(c.gv + 6496 + 42 * i, 42)); print(i, [x - 256 if x > 127 else x for x in b])
tp = c.top(); print('top fields', list(bytes(c.g.uc.mem_read(tp + 0xc, 8))))
print('tab wrap f=1:'); 
c.key('OK', 4)
v = []
for k in range(7):
    c.key('RIGHT', 3); v.append(c.b8(c.top(), 0x11))
print(v)
c.key('LEFT', 3); print('left', c.b8(c.top(), 0x11))
