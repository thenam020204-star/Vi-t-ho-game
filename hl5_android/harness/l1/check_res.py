#!/usr/bin/env python3
"""L1: kiểm màn kết quả Expert (StateGame state 2 sub 1 hộp thắng/thua, sub 2 bảng số liệu) menu mới vs gốc. Dùng: check_res.py W H MODE OUTDIR  (MODE new|orig)"""
import sys, os, json, hashlib
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
c = Ctx(W, Hh, MODE, OUT); NEW = c.NEW; r = c.r; g = c.g; tag = c.tag; gv = c.gv
RAW = os.path.join(OUT, 'raw_' + tag); os.makedirs(RAW, exist_ok=True); c.out = RAW
def fl(): t = c.top(); return {'cls': hex(c.cls()), 'f': [c.s8(t, o) for o in range(0xc, 0x14)]}
def dump(n):
    if NEW: json.dump({str(a): b for a, b in c.hits().items()}, open(os.path.join(OUT, 'hits_%s_%s.json' % (n, tag)), 'w'))
def setv(mode):
    c.wr(gv + 6457, [mode])
    for o, v in ((6460, 11), (6464, 63), (6468, 84), (6472, 12), (5156, 12480), (5160, 98765), (5164, 1234567)): c.wr(gv + o, list(v.to_bytes(4, 'little')))
def enter(sub, e, mode):
    setv(mode); t = c.top(); r.call(0xda410, t, 2, e); r.call(0xda470, t, sub); c.step(6)
def ok():
    if NEW: c.tap(0xF2, 6)
    else: c.key('OK', 6)
def sc_sub1():
    out = {}
    for e in (0, 1):
        for mode in (1, 2, 3):
            enter(1, e, mode); out['e%dm%d' % (e, mode)] = fl()
            if e == 0 and mode == 3: c.shot('res1_win'); dump('res1')
            if e == 1 and mode == 2: c.shot('res1_lose')
    enter(1, 0, 2); ok(); out['after_ok'] = fl(); return out
def sc_sub2():
    out = {}
    for mode in (1, 2, 3):
        enter(2, 6, mode); out['m%d_f6' % mode] = fl()
        if mode == 3: c.shot('res2_a'); dump('res2')
        c.step(10); out['m%d_f16' % mode] = fl()
        if mode == 3: c.shot('res2_b')
        c.step(20); out['m%d_f36' % mode] = fl()
        c.step(10); out['m%d_f46' % mode] = fl()
        if mode == 3: c.shot('res2_c')
        if mode == 2: c.shot('res2_m2')
        if mode == 1: c.shot('res2_m1')
    enter(2, 6, 3); c.step(10); ok(); out['skip'] = fl(); c.step(2); ok(); out['leave'] = fl(); return out
def sc_cycles():
    def one():
        enter(2, 6, 3); c.step(3); ok(); ok()
        for _ in range(3):
            if c.cls() == 0x15e150: break
            r.key('CLR'); r.step(5)
    for k in range(20): one()
    live0 = len(g.heap.sizes); top0 = g.heap.top
    for k in range(20): one()
    r.step(10)
    return {'live': len(g.heap.sizes) - live0, 'top': g.heap.top - top0, 'cls': hex(c.cls())}
for nm in os.environ.get('SC', 'sub1,sub2,cycles').split(','): c.fork(globals()['sc_' + nm], nm)
