#!/usr/bin/env python3
"""L6b: kiểm 3 màn mới (chạm) vs gốc v0.2-hd (phím / chạm gốc). Dùng: check_l6b.py W H MODE OUTDIR  (MODE new|orig|off). 'off' = dev.so với cờ MUI_F_DISABLE (đường gốc).
Kết quả OUTDIR/raw_<WxH>/sc_<tên>_<WxH>_<mode>.json"""
import sys, os, json
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l6'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'l3'))
from qlib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
PART = sys.argv[5] if len(sys.argv) > 5 else 'menu'
c = Ctx(W, Hh, 'new' if MODE == 'off' else MODE, OUT, boot_to=10 if PART == 'menu' else 1000); NEW = MODE == 'new'; r = c.r; g = c.g; tag = c.tag
c.mode = MODE
RAW = os.path.join(OUT, 'raw_' + tag); os.makedirs(RAW, exist_ok=True); c.out = RAW
if MODE == 'off': g.uc.mem_write(BASE + c.sym['mui_flags'], (1).to_bytes(4, 'little'))
GV = c.gv
def fl(): t = c.top(); return {'cls': hex(c.cls()), 'f': [c.s8(t, o) for o in range(0xc, 0x14)]}
def gvb(): return [c.s8(GV, 6400 + i) for i in (56, 57, 58)]
def pop():
    t = c.top(); res = c.u32(t + 0x14); return {'cls': hex(c.cls()), 't': c.s8(t, 0xe), 'nb': c.s8(t, 0x10), 'res': c.s8(res) if res else None, 'c29': c.b8(t, 0xc29), 'c2a': c.b8(t, 0xc2a), 'c2b': c.b8(t, 0xc2b)}
def enter(): r.step(700); r.key('OK'); r.step(10)
T6 = [0, 1, 4, 5, 6, 8]
def grid_to(o):
    if NEW: c.tap(0x10 + T6.index(o), 6)
    else:
        for k in ['DOWN'] * (o // 3) + ['RIGHT'] * (o % 3) + ['OK']: c.key(k, 3)
        c.step(4)
def fk(ks):
    for k in ks: c.key(k, 3)
def sc(name, fn):
    pid = os.fork()
    if pid: os.waitpid(pid, 0); return
    try: d = fn()
    except Exception as e:
        import traceback; d = {'ERR': repr(e), 'tb': traceback.format_exc()}
    json.dump(d, open(os.path.join(RAW, 'sc_%s_%s_%s.json' % (name, tag, MODE)), 'w'), default=str); os._exit(0)
def at(hid): c.tap(hid, 6)
def to_expert():
    grid_to(1); t = c.top(); c.wr(t + 0xc, [6, 1]); c.step(5)
def expert_mode(i):
    def f():
        to_expert(); out = {'in': fl()}
        if NEW: at(0x40 + i)
        else:
            seq = {0: [], 1: ['RIGHT'], 2: ['RIGHT', 'RIGHT'], 3: ['UP']}[i]; fk(seq + ['OK'])
        c.step(4); out['after'] = fl(); out['gv'] = gvb(); out['pop'] = pop() if c.cls() == int(out['in']['cls'], 16) and False else None
        out['popcls'] = hex(c.cls()); out['popt'] = [c.s8(c.top(), 0xe), c.s8(c.top(), 0x10)]
        return out
    return f
def expert_back():
    to_expert()
    if NEW: at(1)
    else: c.key('CLR', 5)
    c.step(4); return fl()
def expert_nav():
    to_expert(); out = []
    for k in ['RIGHT', 'RIGHT', 'RIGHT', 'UP', 'DOWN', 'LEFT', 'LEFT']: c.key(k, 3); out.append(c.s8(c.top(), 0xf))
    return out
def to_mission(): grid_to(5); c.step(5)
def mission_rows():
    to_mission(); out = {'in': fl()}
    for rr in (2, 4):
        if NEW: at(0x50 + rr)
        else: fk(['DOWN'] * 2)
        out['r%d' % rr] = fl()
    return out
def mission_ok():
    to_mission()
    if NEW: at(0x60)
    else: c.key('OK', 4)
    c.step(5); out = {'f': fl(), 'popt': [c.s8(c.top(), 0xe), c.s8(c.top(), 0x10)], 'cls': hex(c.cls())}
    return out
def mission_arrows():
    to_mission(); out = {}; hw, hh = W // 2, Hh // 2
    for nm, hid, (ox, oy) in (('dn', 0x71, (hw - 88 + 166, hh - 102 + 170)), ('dn2', 0x71, (hw - 88 + 166, hh - 102 + 170)), ('up', 0x70, (hw - 88 + 166, hh - 102 + 65))):
        if NEW: at(hid)
        else: c.touch_xy(ox, oy, 5)
        out[nm] = fl()
    return out
def mission_wrap_up():   # từ đầu danh sách bấm ▲: về cuối
    to_mission()
    if NEW: at(0x70)
    else: c.touch_xy(W // 2 - 88 + 166, Hh // 2 - 102 + 65, 5)
    return fl()
def mission_back():
    to_mission()
    if NEW: at(1)
    else: c.key('CLR', 5)
    c.step(4); return fl()
def mission_long():     # cuộn xa: 8 lần DOWN rồi chạm hàng 3
    to_mission(); fk(['DOWN'] * 8); out = {'a': fl()}
    if NEW: at(0x53)
    else: fk(['DOWN'] * 0)   # (orig không có cách chạm tương đương ở đây: chỉ so cửa sổ sau phím)
    out['b'] = fl(); return out
def to_help(): grid_to(6); c.step(5)
def help_rows():
    to_help(); out = {'in': fl()}
    for rr in (2, 4):
        if NEW: at(0x50 + rr)
        else: fk(['DOWN'] * 2)
        out['r%d' % rr] = fl()
    return out
def help_ok():
    to_help(); out = {}
    if NEW: at(0x50)
    else: c.key('OK', 4)
    c.step(4); out['f1'] = fl()
    if NEW: at(1)
    else: c.key('CLR', 5)
    c.step(4); out['back1'] = fl()
    if NEW: at(1)
    else: c.key('CLR', 5)
    c.step(4); out['back0'] = fl(); return out
def help_arrows():
    to_help(); out = {}; hw, hh = W // 2, Hh // 2
    for nm, hid, (ox, oy) in (('dn', 0x71, (hw - 88 + 160, hh - 102 + 165)), ('dn2', 0x71, (hw - 88 + 160, hh - 102 + 165)), ('up', 0x70, (hw - 88 + 160, hh - 102 + 45))):
        if NEW: at(hid)
        else: c.touch_xy(ox, oy, 5)
        out[nm] = fl()
    return out
def help_wrap_up():
    to_help()
    if NEW: at(0x70)
    else: c.touch_xy(W // 2 - 88 + 160, Hh // 2 - 102 + 45, 5)
    return fl()
def mk_quest():
    QTOP[0] = 0
    qm = c.u32(c.gv + 5580)
    for i in (0, 1, 2): c.r.call(0xcdcc4, qm, i, 1)
    for i in (0, 1, 2): c.r.call(0xd0a68, qm, i)
    c.step(4); return qm
QTOP = [0]
def q_state(qm):
    t = c.top(); d = pop(); import hashlib
    d['qi'] = c.b8(t, 0xc20); d['same'] = (t == QTOP[0]); QTOP[0] = QTOP[0] or t
    d['buf'] = hashlib.md5(bytes(g.uc.mem_read(qm + 28, 128))).hexdigest()[:8]; return d
def q_x():
    qm = mk_quest(); out = {'in': q_state(qm)}
    if NEW: at(0xB0)
    else: c.key('CLR', 5)
    c.step(4); out['after'] = q_state(qm); return out
def q_reward():
    qm = mk_quest(); out = {'in': q_state(qm)}
    if NEW: at(0xB2)
    else: fk(['LEFT', 'OK'])
    c.step(4); out['m4'] = q_state(qm)
    if NEW: at(0xB2)
    else: fk(['OK'])
    c.step(4); out['m3'] = q_state(qm); return out
def q_ok():
    qm = mk_quest(); out = {'in': q_state(qm)}
    if NEW: at(0xB3)
    else: c.key('OK', 5)
    c.step(4); out['after'] = q_state(qm); out['top'] = hex(c.cls()); return out
if PART == 'menu': enter()
S = [('e0', expert_mode(0)), ('e1', expert_mode(1)), ('e2', expert_mode(2)), ('e3', expert_mode(3)), ('eback', expert_back), ('enav', expert_nav),
     ('mrows', mission_rows), ('mok', mission_ok), ('marrows', mission_arrows), ('mwrap', mission_wrap_up), ('mback', mission_back), ('hrows', help_rows), ('hok', help_ok), ('harrows', help_arrows), ('hwrap', help_wrap_up)] if PART == 'menu' else [('qx', q_x), ('qreward', q_reward), ('qok', q_ok)]
for nm, fn in S: sc(nm, fn)
