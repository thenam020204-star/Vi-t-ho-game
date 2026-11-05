"""L6b: dò popup nhiệm vụ loại 9 (trường StatePopup + QuestMgr) và chụp. Dùng: q1.py W H"""
import sys, os, struct
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l6'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'l3'))
from qlib import *
W, Hh = int(sys.argv[1]), int(sys.argv[2])
OUT = '/home/user/Vi-t-ho-game/hl5_android/shots/L6'
c = Ctx(W, Hh, os.environ.get('MODE', 'new'), os.environ.get('OUTD', '/tmp/l6b_q1_%d' % W), boot_to=1000); r = c.r; g = c.g
qm = c.u32(c.gv + 5580)
for i in (0, 1, 2): c.r.call(0xcdcc4, qm, i, 1)
for i in (0, 1, 2): c.r.call(0xd0a68, qm, i)
c.step(4)
q = c.top()
print('cls', hex(c.cls()), 'type', c.s8(q, 0xe), 'nbtn', c.s8(q, 0x10))
print('res ptr', hex(c.u32(q + 0x14)), 'popup', hex(q), 'c29 addr', hex(q + 0xc29))
print('rect', [c.s16(q + 0x800 + 2 * i) for i in range(4)])
print('c20..c2b', [c.b8(q, 0xc20 + i) for i in range(12)])
for i in range(2): print('btn', i, bytes(g.uc.mem_read(q + 0x43c + 48 * i, 16)))
lst = c.u32(qm + 0)
print('qm ptr fields', [hex(c.u32(qm + 4 * i)) for i in range(8)])
base = c.u32(c.gv + 5580)
if os.environ.get('REWARD'): c.key('LEFT', 2); c.key('OK', 4)
c.shot(os.environ.get('SHOTNAME', 'q1'))
