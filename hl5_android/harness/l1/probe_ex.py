import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
exec(open(os.path.join(os.path.dirname(os.path.abspath(__file__)), 'probe_txt2.py')).read().split('c = Ctx')[0].split('from qlib import *')[1])
c = Ctx(600, 270, 'new', os.path.expanduser('~/hl5work_l1/p')); g = c.g; r = c.r
tm = g.rd32(BASE + 0x16a220); tbl = c.u32(c.gv + 5132)
def ct(i): p = r.call(0x13fa94, tm, i); return dec(bytes(g.uc.mem_read(p, 160)).split(b'\0')[0])
r.call(0x428bc, 0, 6, 0); r.step(10)
t = c.top(); print('cls', hex(c.cls()), 'fields', [c.s8(t, o) for o in range(0xc, 0x18)], 'mode', c.s8(c.gv, 6457), flush=True)
c.shot('ex0')
p = g.heap.alloc(0x800)
for cat, ids in ((11, range(8, 30)),):
    for i in ids:
        c.wr(p, bytes(0x800)); r.call(0x9e670, tbl, p, cat, i)
        b = bytes(g.uc.mem_read(p, 0x200)); nm = int.from_bytes(b[24:28], 'little')
        print(cat, i, 'type', b[20], 'icon', int.from_bytes(b[22:24], 'little'), 'price', int.from_bytes(b[0x30:0x34], 'little'), hex(nm), flush=True)
for i in (195, 196, 197, 198, 299, 185, 244, 246, 90, 166, 261): print(i, ct(i))
print('money', r.call(0x9b4e8, c.bag()))
c.key('RIGHT'); print('after RIGHT', [c.s8(t, o) for o in range(0xc, 0x18)])
c.key('RIGHT'); print('after RIGHT', [c.s8(t, o) for o in range(0xc, 0x18)])
