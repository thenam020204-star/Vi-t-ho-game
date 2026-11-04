import sys, os, json
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
W, Hh = 600, 270
c = Ctx(W, Hh, 'orig', os.path.expanduser('~/hl5work_l1/p'))
g=c.g
def fl():
    t = c.top(); return [c.s8(t, o) for o in range(0xc, 0x1c)]
def cstr(a):
    b=bytes(g.uc.mem_read(a,60)); return b.split(b'\0')[0]
print('state0', hex(c.cls()))
c.r.call(0x42ab8, 0, 0); c.step(10)
print('cash', hex(c.cls()), fl()); c.shot('cash0')
for k in ['RIGHT','DOWN','OK','OK']:
    c.key(k, 6); print(k, hex(c.cls()), fl())
    t=c.top()
    if c.cls()==0x15e5f0 or True:
        c.shot('cash_'+k)
t=c.top(); print('rec', [c.b8(c.u32(c.u32(t+0x14)+4), i) for i in range(48)])
