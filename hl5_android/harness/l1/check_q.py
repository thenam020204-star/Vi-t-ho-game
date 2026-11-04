#!/usr/bin/env python3
"""L1/T17c4: kiểm Nhiệm vụ (menu mới) vs bản gốc cùng thao tác. Dùng: check_q.py W H MODE OUTDIR."""
import sys, os, json, hashlib
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
c = Ctx(W, Hh, MODE, OUT); NEW = c.NEW; r = c.r; g = c.g; tag = c.tag
RAW = os.path.join(OUT, 'raw_' + tag); os.makedirs(RAW, exist_ok=True); c.out = RAW
ROW, SUB, NODE, BTN, DET = 0x20, 0x30, 0x40, 0x50, 0x3D
def fl():
    t = c.top()
    return {'cls': hex(c.cls()), 'f': [c.s8(t, o) for o in range(0xc, 0x15)], 'pan': [c.s16(t + o) for o in (0x38, 0x3a, 0x3c, 0x3e)], 'cnt': c.s8(c.u32(c.gv + 5580), 12468)}
def seed():
    qm = c.u32(c.gv + 5580)
    for i in range(9): c.r.call(0xcdcc4, qm, i, 1)
    for i in range(9): c.r.call(0xd0a68, qm, i)
    c.r.call(0xcdcc4, qm, 0, 5); c.r.call(0xcdcc4, qm, 1, 5)
SEEDED = [0]
def enter():
    if not SEEDED[0]: seed(); SEEDED[0] = 1
    c.S3(8, 6)
def tap(hid, w=4):
    if not NEW: raise RuntimeError('tap in orig')
    c.tap(hid, w)
def dump(nm):
    if NEW: json.dump({str(a): b for a, b in c.hits().items()}, open(os.path.join(OUT, 'hits_%s_%s.json' % (nm, tag)), 'w'))
def keys(*ks):
    for k in ks: c.key(k, 4)
def drag(hid, dy):
    h = c.hits()[hid]; x = (h[0] + h[2]) // 2; y = (h[1] + h[3]) // 2
    r.touch(x, y, False); r.step(2)
    n = 4
    for i in range(1, n + 1): r.move(x, y + dy * i // n); r.step(1)
    g.call(r.touchf, g.env, 0, 20, x, y + dy, 0); r.step(8)
def sc_tab():
    enter(); out = {'init': fl()}; c.shot('q0'); dump('q')
    if NEW: tap(SUB + 1)
    else: keys('RIGHT')
    out['tab1'] = fl(); c.shot('q_tree'); dump('qt')
    if NEW: tap(SUB)
    else: keys('LEFT')
    out['tab0'] = fl(); return out
def sc_list():
    enter(); out = {}
    if NEW: tap(ROW + 3)
    else: keys('DOWN', 'DOWN', 'DOWN', 'DOWN')
    out['sel3'] = fl(); c.shot('q_list')
    if NEW: tap(ROW + 3)
    else: keys('OK')
    out['detail'] = fl(); c.shot('q_detail'); dump('qd'); c.step(4)
    if NEW: tap(ROW + 1)          # Back + chọn hàng 1
    else: keys('CLR', 'UP', 'UP')
    out['row1'] = fl()
    if NEW: tap(ROW + 1)
    else: keys('OK')
    out['detail1'] = fl()
    if NEW: tap(BTN)               # nút Xem phần thưởng: +0x11=0, OK
    else: keys('OK')
    out['rewards'] = fl(); c.shot('q_rewards'); return out
def sc_map():
    enter()
    if NEW: tap(ROW + 2)
    else: keys('DOWN', 'DOWN', 'DOWN')
    if NEW: tap(ROW + 2)
    else: keys('OK')
    if NEW: tap(BTN + 1)           # nút Bản đồ lớn: +0x11=1, OK
    else: keys('RIGHT', 'OK')
    out = {'map': fl()}; c.shot('q_map'); return out
def sc_drag():
    enter(); out = {}
    if NEW: tap(ROW + 2)
    else: keys('DOWN', 'DOWN', 'DOWN')
    out['sel2'] = fl()
    if NEW: P = c.hits()[ROW + 1][1] - c.hits()[ROW][1]; drag(ROW + 1, -2 * P)
    else: keys('UP', 'DOWN', 'DOWN')
    out['drag'] = fl(); c.shot('q_drag'); return out
def sc_tree():
    enter(); out = {}
    if NEW: tap(SUB + 1)
    else: keys('RIGHT')
    if NEW: P = c.hits()[NODE + 1][1] - c.hits()[NODE][1]; drag(NODE + 0, -2 * P)
    else: keys('DOWN', 'DOWN', 'DOWN')
    c.step(40); out['pan'] = fl(); c.shot('q_tree2')
    if NEW: P = c.hits()[NODE + 1][1] - c.hits()[NODE][1]; drag(NODE + 0, 1 * P)
    else: keys('UP')
    c.step(40); out['pan2'] = fl(); return out
def sc_cycles():
    for k in range(20): enter(); [r.key('CLR') or r.step(5) for _ in range(4)]
    live0 = len(g.heap.sizes); top0 = g.heap.top
    for k in range(20):
        enter(); c.step(2)
        if k % 2:
            if NEW: tap(SUB + 1)
            else: keys('RIGHT')
            c.step(2)
        else:
            if NEW: tap(ROW + 1); c.step(2); tap(ROW + 1)
            else: keys('DOWN', 'DOWN', 'OK')
            c.step(2)
        for _ in range(6):
            if c.cls() == 0x15e150: break
            r.key('CLR'); r.step(5)
    r.step(10)
    return {'live': len(g.heap.sizes) - live0, 'top': g.heap.top - top0, 'img': hashlib.md5(r.shot()).hexdigest(), 'mimg': c.mimg(), 'cls': hex(c.cls())}
for nm in os.environ.get('SC', 'tab,list,map,drag,tree,cycles').split(','): c.fork(globals()['sc_' + nm], nm)
