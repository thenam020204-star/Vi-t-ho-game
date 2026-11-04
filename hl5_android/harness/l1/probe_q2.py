import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
c = Ctx(600, 270, 'orig', os.path.expanduser('~/hl5work_l1/p'))
qm = c.u32(c.gv + 5580)
print('qm', hex(qm), 'count', c.s8(qm, 12468), 'status0..12', [c.b8(qm, 0x488 + i) for i in range(12)])
for i in range(0, 8):
    print(i, bytes(c.g.uc.mem_read(qm + 1428 + 368 * i, 40)).hex())
ids = [int(x) for x in os.environ.get('IDS', '0,1,2').split(',')]
for i in ids:
    c.r.call(0xcdcc4, qm, i, int(os.environ.get('ST', '3')))
for i in ids:
    c.r.call(0xd0a68, qm, i)
c.r.call(0xcdd00, qm)
print('count', c.s8(qm, 12468), [c.b8(qm, 0x488 + i) for i in range(12)])
for i in range(0, 4):
    print(i, bytes(c.g.uc.mem_read(qm + 1428 + 368 * i, 48)).hex())
c.S3(8, 6); c.shot('q0')
t = c.top()
print([c.s8(t, o) for o in (0xc,0xd,0xe,0xf,0x10,0x11,0x12,0x4c,0x4d)])
for k in os.environ.get('KEYS', 'DOWN,OK,DOWN,OK').split(','):
    c.key(k, 4); t = c.top(); print(k, [c.s8(t, o) for o in (0xc,0xd,0xe,0xf,0x10,0x11,0x12,0x4c,0x4d)]); c.shot('q_' + k)
