import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l2'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't17c2'))
from qlib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
c = Ctx(W, Hh, MODE, OUT)
def one(t):
    def f():
        c.r.call(0x42b08, 0, t, 1); c.step(30)
        tp = c.top()
        c.shot('npc%d' % t)
        return {'cls': hex(c.cls()), 'e': c.b8(tp, 0xe), 'f': [c.b8(tp, o) for o in range(0xc, 0x30)]}
    return f
for t in range(8): c.fork(one(t), 'npc%d' % t)
