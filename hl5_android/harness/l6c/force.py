import sys, os, json
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l6'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'l3'))
from qlib import *
W, Hh, MODE = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3]
c = Ctx(W, Hh, MODE, '/tmp/l6c_ex', boot_to=1000); r = c.r
c.S3(int(os.environ.get('ST', '4')), 8)
def st():
    t = c.top(); return (hex(c.cls()), [c.s8(t, o) for o in range(0xc, 0x14)], 'pop', c.s8(t, 0xe), c.s8(t, 0x10))
for sub in [int(x) for x in sys.argv[4].split(',')]:
    t = c.top(); c.r.call(0xe2eb8, t, sub); c.step(3); print(sub, st()); c.shot('f%d' % sub)
