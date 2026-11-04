import sys, os, struct
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l2'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't17c2'))
from qlib import *
from unicorn import UC_HOOK_CODE
c = Ctx(400, 240, 'new', '/tmp/claude-0/c5'); r = c.r; uc = c.g.uc
c.g.uc.mem_write(c.hero + 6080, struct.pack('<i', 100000)); c.r.call(0x42b08, 0, 0, 1); c.step(15)
tp = c.top(); vt = c.g.rd32(tp)
print('vt', hex(vt - BASE), 'tp slot', hex(c.g.rd32(vt + 0x24) - BASE), 'sym', {k: hex(v) for k, v in c.sym.items() if k.startswith('mui_tp') or k in ('npc_touch', 'mui_touch_press')})
hit = []
for n, a in (('tp', c.sym['mui_tp_NPC']), ('BaseTouchPress', BASE + 0x42d80), ('mainKeyPressed', BASE + 0x98af0), ('origNPC', BASE + 0x10e864), ('onHandle', BASE + 0x9831c)):
    uc.hook_add(UC_HOOK_CODE, lambda u, ad, s, nn: hit.append(nn), n, begin=a, end=a)
uc.ctl_flush_tb()
c.touch_xy(60, 75, 4)
print('after touch', c.b8(c.top(), 0x10))
c.r.call(c.sym['mui_tp_NPC'], c.top(), 60, 75, 0); print('direct thunk', c.b8(c.top(), 0x10), hit)
c.r.call(c.sym['npc_touch'], c.top(), 60, 75); print('direct npc_touch', c.b8(c.top(), 0x10), 'lastid', c.r.rd(c.sym['mui_last_id']), c.r.rd(c.sym['mui_flags']))
print('hits', hit, 'cur', c.b8(c.top(), 0x10), 'nhit', c.r.rd(c.sym['mui_nhit']))
print(c.hits().get(65))
c.touch_xy(60, 75, 6)
print('2nd tap -> top', hex(c.cls()), 'type', c.b8(c.top(), 0xe), 'nbtn', c.b8(c.top(), 0x10), 'nhit', c.r.rd(c.sym['mui_nhit']), sorted(c.hits().keys())[:8])
tp = c.top(); par = c.g.rd32(tp + 8); print('popup parent vptr', hex(c.g.rd32(par) - BASE), 'npc_vt+8', hex(c.sym['NPC_vtable'] + 8 if 'NPC_vtable' in c.sym else 0), 'type', c.b8(tp, 0xe), 'nb', c.b8(tp, 0x10))
