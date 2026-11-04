#!/usr/bin/env python3
"""L1b/T17c8: kiểm StateMap (menu mới) vs bản gốc cùng thao tác. Dùng: check_map.py W H MODE OUTDIR. Kịch bản (SC=): toggle, star, cursor, close, cycles."""
import sys, os, json, hashlib
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1b'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
c = Ctx(W, Hh, MODE, OUT); NEW = c.NEW; r = c.r; g = c.g; tag = c.tag
RAW = os.path.join(OUT, 'raw_' + tag); os.makedirs(RAW, exist_ok=True); c.out = RAW
H_STAR, H_HASH, H_CLOSE = 0x30, 0x31, 1
def fl():
    t = c.top(); return {'cls': hex(c.cls()), 'f': [c.s8(t, o) for o in (0xc, 0xd, 0x12, 0x13)], 'cur': [c.s8(t, 0x1a), c.s8(t, 0x1b)],
                         'mm': c.s8(c.u32(c.gv + 5200), 4224), 'close': c.s8(c.gv, 0xe0)}
def enter(par=0): c.r.call(0x428bc, 0, 7, par); c.step(8)
def hits(name):
    if NEW: json.dump({str(a): b for a, b in c.hits().items()}, open(os.path.join(OUT, 'hits_%s_%s.json' % (name, tag)), 'w'))
def sc_toggle():
    enter(); out = {'init': fl()}; c.shot('map1'); hits('mz')
    if NEW: c.tap(H_HASH, 12)
    else: c.key('HASH', 12)
    out['big'] = fl(); c.shot('map0'); hits('mb')
    if NEW: c.tap(H_HASH, 12)
    else: c.key('HASH', 12)
    out['zone'] = fl(); return out
def sc_star():
    enter(); out = {'init': fl()}
    for k in range(3):
        if NEW: c.tap(H_STAR, 6)
        else: c.key('STAR', 6)
        out['star%d' % k] = fl()
    c.shot('map_star'); return out
def sc_cursor():
    enter(); out = {}
    if NEW: c.tap(H_HASH, 12)
    else: c.key('HASH', 12)
    for i, k in enumerate(('RIGHT', 'RIGHT', 'DOWN', 'LEFT', 'UP')):
        c.key(k, 14); out['k%d' % i] = fl()
    c.shot('map_cursor'); return out
def sc_close():
    enter(); out = {'init': fl()}
    if NEW: c.tap(H_CLOSE, 6)
    else: c.key('CLR', 6)
    out['after'] = fl(); c.step(10); out['later'] = fl(); return out
def sc_cycles():
    for k in range(10):
        enter(); c.key('HASH', 6); c.key('CLR', 6); c.step(10)
    live0 = len(g.heap.sizes); top0 = g.heap.top
    for k in range(20):
        enter(); c.step(2)
        if NEW and k % 2: c.tap(H_HASH, 6)
        elif k % 2: c.key('HASH', 6)
        c.key('CLR', 6); c.step(10)
    r.step(10)
    return {'live': len(g.heap.sizes) - live0, 'top': g.heap.top - top0, 'cls': hex(c.cls()), 'mimg': c.mimg()}
for nm in os.environ.get('SC', 'toggle,star,cursor,close,cycles').split(','): c.fork(globals()['sc_' + nm], nm)
