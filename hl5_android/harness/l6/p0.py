import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l6'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
c = Ctx(400, 240, 'new', '/tmp/w/o', boot_to=10); r = c.r
r.step(700); r.key('OK'); r.step(10)
t=c.top(); print(hex(c.cls()), c.st(), c.sub(), len(c.sym))
c.shot('mm')
