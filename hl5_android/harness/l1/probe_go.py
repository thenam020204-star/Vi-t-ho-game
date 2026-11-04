import sys, os, json
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
W, Hh = 600, 270
c = Ctx(W, Hh, 'new', os.path.expanduser('~/hl5work_l1/p'))
r=c.r
def bunch(cat, i): return r.call(0x9b6c0, c.bag(), cat, i) & 0xffffffff
def go(n):
    t=c.top(); r.call(0xda410, t, n, 0); c.step(8)
    t=c.top(); out=[n, hex(c.cls()), c.s8(t,0xc), c.s8(t,0xd), c.s8(t,0xe)]
    c.shot('go%d'%n)
    c.key('OK',10); t=c.top(); out+=['afterOK', hex(c.cls()), c.s8(t,0xc), c.s8(t,0xe), 'amulet', bunch(18,23), 'hp', c.u32(c.hero+0xf0)]
    return out
for n in (1,2,4,6,7):
    pid=os.fork()
    if pid: os.waitpid(pid,0); continue
    try: print(go(n), flush=True)
    except Exception as e: print(n,'ERR',e, flush=True)
    os._exit(0)
