import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l6'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'l3'))
from qlib import *
W, Hh = int(sys.argv[1]), int(sys.argv[2])
c = Ctx(W, Hh, 'new', '/tmp/l6b_q3', boot_to=1000); r = c.r; g = c.g
tm = c.u32(BASE + 0x16a238 - 0x16a238 + 0)  # placeholder
# TextMgr*: _pTextMgr symbol
tmgr = c.u32(BASE + c.sym['_pTextMgr']) if '_pTextMgr' in c.sym else None
print('tm', hex(tmgr) if tmgr else None)
for i in (14, 261, 262, 56):
    p = c.r.call(0x13fa94, tmgr, i); print('CT', i, hex(p), bytes(g.uc.mem_read(p, 12)))
for i in (261, 262, 261, 262):
    p = c.r.call(0x13fa94, tmgr, i); print('CT again', i, hex(p), bytes(g.uc.mem_read(p, 12)))
qm = c.u32(c.gv + 5580)
for i in (0, 1, 2): c.r.call(0xcdcc4, qm, i, 1)
rec = qm + 1428 + 368 * 2
print('rec flags', [c.b8(rec, o) for o in (0, 0x1, 0x2, 0x3)], 'st', c.b8(qm + 0x594 - 0x594 + 0x488, 0), 'rec+0x595-1428', c.b8(qm, 0x595 + 368 * 2 - 0))
for flag in (0,):
    c.wr(qm + 0x595 + 368 * 2, [flag])
    c.r.call(0xd0a68, qm, 2); c.step(4); q = c.top()
    print('flag', flag, 'cls', hex(c.cls()), 'type', c.s8(q, 0xe), 'nbtn', c.s8(q, 0x10), 'c29', c.b8(q, 0xc29), 'c2b', c.b8(q, 0xc2b), 'btn', bytes(g.uc.mem_read(q + 0x43c, 8)))
    c.shot('q3')
    c.key('OK', 3); print('after OK cls', hex(c.cls()), 'top', hex(c.top()), 'q', hex(q), 'c29', c.b8(q, 0xc29))
