import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l6'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from plib import *
c = Ctx(400, 240, 'new', '/tmp/w/o', boot_to=10); r = c.r
r.step(700); r.key('OK'); r.step(10)
p = Probe(c); t = c.top()
for s in range(13):
    row = []
    for sub in range(7):
        c.wr(t + 0xc, [s & 255, sub]); row.append(p.decide('MAIN', t))
    print(s, row)
