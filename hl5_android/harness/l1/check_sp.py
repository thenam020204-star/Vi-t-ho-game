#!/usr/bin/env python3
"""L1/T17c4: kiểm Tinh linh (menu mới) vs bản gốc cùng thao tác. Dùng: check_sp.py W H MODE OUTDIR. Kết quả OUTDIR/raw_<WxH>/sc_*.json, ảnh, hits_sp_*.json"""
import sys, os, json, hashlib
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
c = Ctx(W, Hh, MODE, OUT); NEW = c.NEW; r = c.r; g = c.g; tag = c.tag
RAW = os.path.join(OUT, 'raw_' + tag); os.makedirs(RAW, exist_ok=True); c.out = RAW
def fl():
    t = c.top(); return {'cls': hex(c.cls()), 'f': [c.s8(t, o) for o in range(0xc, 0x14)]}
def setup():
    sp = r.call(0x853dc, c.hero)
    c.wr(sp + 2, [44, 45, 46, 47]); c.wr(sp + 1, [12]); c.wr(sp + 0x14, [77, 0]); c.wr(sp + 8, [0x39, 0x01, 0, 0]); c.wr(sp + 0x10, [0x3b, 0x01, 0, 0])
    for i in (44, 45, 46, 47): c.wr(c.hero + 588 + i, [i - 41])
    c.wr(c.hero + 8064 + 37, [3, 5])
    tbl = c.u32(c.gv + 5132)
    for cat, i in ((5, 81), (5, 82), (5, 83), (6, 81), (6, 82)): c.callx(0xa321c, tbl, cat, i, 1, 1)
def eqinfo():
    eq = r.call(0x8545c, c.hero); out = []
    for s in (0, 1):
        p = r.call(0x9c920, eq, s)
        out.append(None if not p else [c.s16(p + 22), bytes(g.uc.mem_read(p + 24, 8)).hex()])
    return out
def spstate():
    sp = r.call(0x853dc, c.hero); return {'eq': eqinfo(), 'info': bytes(g.uc.mem_read(sp, 0x20)).hex(), 'cool': [c.s8(c.hero + 8064, 37), c.s8(c.hero + 8064, 38)]}
def enter(tab=0):
    setup(); c.S3(7, 6)
def tap(hid, w=4):
    if not NEW: raise RuntimeError('tap in orig')
    c.tap(hid, w)
def popup_ok():
    hs = c.hits(); ks = [(k, v) for k, v in hs.items() if v[4] == 4 and k != 0xFE]
    if ks: h = ks[0][1]; r.touch((h[0] + h[2]) // 2, (h[1] + h[3]) // 2, True); c.step(5)
SUB, SLOT, SKILL, USE = 0x30, 0x34, 0x38, 0x3C
def sc_tab():
    enter(); out = {'init': fl()}; c.shot('sp0')
    if NEW: json.dump({str(a): b for a, b in c.hits().items()}, open(os.path.join(OUT, 'hits_sp_%s.json' % tag), 'w'))
    if NEW: tap(SUB + 1)
    else: c.key('RIGHT', 4)
    out['tab1'] = fl(); c.shot('sp_tab1')
    if NEW: json.dump({str(a): b for a, b in c.hits().items()}, open(os.path.join(OUT, 'hits_sp1_%s.json' % tag), 'w'))
    if NEW: tap(SUB)
    else: c.key('LEFT', 4)
    out['tab0'] = fl(); return out
def sc_equip():
    enter(); out = {}
    if NEW: tap(SLOT)
    else: c.key('DOWN', 4); c.key('OK', 4)
    out['filter'] = fl(); c.shot('sp_filter')
    if NEW: json.dump({str(a): b for a, b in c.hits().items()}, open(os.path.join(OUT, 'hits_spf_%s.json' % tag), 'w'))
    if NEW: tap(0xC1)     # H_CAND+1 (bag.c)
    else: c.key('RIGHT', 4)
    out['cand1'] = fl()
    if NEW: tap(0xC1)     # chạm lần 2 = OK
    else: c.key('OK', 4)
    out['popup'] = fl(); c.shot('sp_eqpop')
    if NEW: popup_ok()
    else: c.key('OK', 5)
    out['after'] = fl(); out['sp'] = spstate(); c.shot('sp_eq1')
    # gỡ: ô 0 đã có đồ
    if NEW: c.key('CLR', 4); c.key('CLR', 4); tap(SLOT, 5)
    else: c.key('CLR', 4); c.key('CLR', 4); c.key('DOWN', 4); c.key('OK', 5)
    out['unq'] = fl(); c.shot('sp_unq')
    if NEW: popup_ok()
    else: c.key('OK', 5)
    c.step(5); out['unq_after'] = fl(); out['sp2'] = spstate(); return out
def sc_skill():
    enter(); out = {}
    if NEW: tap(SUB + 1); tap(SKILL + 2)
    else: c.key('RIGHT', 4); c.key('DOWN', 4); c.key('LEFT', 4)
    out['sel2'] = fl(); c.shot('sp_skill2')
    if NEW: json.dump({str(a): b for a, b in c.hits().items()}, open(os.path.join(OUT, 'hits_sps_%s.json' % tag), 'w'))
    if NEW: tap(SKILL + 3)
    else: c.key('RIGHT', 4)
    out['sel3'] = fl()
    if NEW: tap(USE, 5)
    else: c.key('OK', 5)
    out['use'] = fl(); out['sp'] = spstate(); return out
def sc_cycles():
    for k in range(20): enter(); [r.key('CLR') or r.step(5) for _ in range(4)]
    live0 = len(g.heap.sizes); top0 = g.heap.top
    for k in range(20):
        enter(); c.step(2)
        if NEW and k % 2: tap(SUB + 1); c.step(2)
        for _ in range(6):
            if c.cls() == 0x15e150: break
            r.key('CLR'); r.step(5)
    r.step(10)
    return {'live': len(g.heap.sizes) - live0, 'top': g.heap.top - top0, 'img': hashlib.md5(r.shot()).hexdigest(), 'cls': hex(c.cls())}
for nm in os.environ.get('SC', 'tab,equip,skill,cycles').split(','): c.fork(globals()['sc_' + nm], nm)
