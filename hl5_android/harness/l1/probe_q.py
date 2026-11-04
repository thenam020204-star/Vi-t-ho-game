import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
c = Ctx(600, 270, 'orig', os.path.expanduser('~/hl5work_l1/p'))
def f():
    t = c.top(); return [c.s8(t, o) for o in (0xc, 0xd, 0xe, 0xf, 0x10, 0x11, 0x12, 0x13, 0x14, 0x2e, 0x3c, 0x6c)]
c.S3(8, 6); print('init', f()); c.shot('q0')
for k in os.environ.get('KEYS', 'RIGHT,DOWN,UP,OK,LEFT,DOWN,DOWN,OK,-16').split(','):
    c.key(k, 4); print(k, f(), hex(c.cls()))
c.shot('q1')
