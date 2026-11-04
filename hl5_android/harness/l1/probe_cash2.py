import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
c = Ctx(600, 270, 'new', os.path.expanduser('~/hl5work_l1/p')); r=c.r
r.call(0x42ab8, 0, 0); c.step(10)
t=c.top(); d=c.u32(t+0x14); print('hdr', bytes(c.g.uc.mem_read(d,16)).hex(), 'this', bytes(c.g.uc.mem_read(t+0xc,0x14)).hex())
recs=c.u32(d+4)
for i in range(0,40):
    b=bytes(c.g.uc.mem_read(recs+24*i,24)); print(i, b[:4].hex(), b[8], b[9], int.from_bytes(b[16:18],'little'))
    if i>3 and b[2]==0 and b[3]==0: break
# scroll: press DOWN 5 times, show fields
for k in range(6):
    c.key('DOWN',3); print('DOWN', [c.s8(t,o) for o in (0xe,0xf)])
