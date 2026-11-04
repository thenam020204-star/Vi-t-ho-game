import sys, os, json
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
W, Hh = 600, 270
NEWITEM = int(os.environ.get('NEWITEM', '0'), 16)
mode = sys.argv[1] if len(sys.argv) > 1 else 'orig'
c = Ctx(W, Hh, mode, os.path.expanduser('~/hl5work_l1/p'))
r = c.r
def f():
    t = c.top(); return [c.s8(t, o) for o in range(0xc, 0x14)] + [c.s8(t, 0x28), c.s8(t, 0x29)]
sp = r.call(0x853dc, c.hero)
c.wr(sp + 2, [44, 45, 46, 47])
c.wr(sp + 1, [12]); c.wr(sp + 0x14, [77, 0])
for i in (44, 45, 46, 47): c.wr(c.hero + 588 + i, [i - 41])
c.wr(c.hero + 8064 + 37, [3, 5])
tbl = c.u32(c.gv + 5132)
for cat, i in ((5, 81), (5, 82), (5, 83), (6, 81), (6, 82)): c.callx(0xa321c, tbl, cat, i, 1, 1)
c.S3(7, 6); print('init', f()); c.shot('spt0')
for k in os.environ.get('KEYS', 'DOWN,OK,RIGHT,OK,OK,DOWN,OK,-16,-16').split(','):
    c.key(k, 4); print(k, f(), hex(c.cls()))
    if k in ('DOWN', 'OK'): c.shot('spt_' + k)
