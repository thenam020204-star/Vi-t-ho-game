import sys, os, json
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
W, Hh = 600, 270
c = Ctx(W, Hh, 'orig', os.path.expanduser('~/hl5work_l1/p'))
def f():
    t = c.top(); return [c.s8(t, o) for o in range(0xc, 0x20)]
c.S3(7, 6); print('init', f()); c.shot('sp0')
for k in ['DOWN', 'RIGHT', 'LEFT', 'UP', 'OK', 'DOWN','DOWN','RIGHT','OK']:
    c.key(k, 4); print(k, f(), hex(c.cls()))
    c.shot('sp_' + k)
