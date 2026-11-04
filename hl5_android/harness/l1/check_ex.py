#!/usr/bin/env python3
"""L1/T17c6: kiểm Chuyên gia (chuẩn bị + cửa hàng + popup) menu mới vs bản gốc. Dùng: check_ex.py W H MODE OUTDIR."""
import sys, os, json, hashlib
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
c = Ctx(W, Hh, MODE, OUT); NEW = c.NEW; r = c.r; g = c.g; tag = c.tag
RAW = os.path.join(OUT, 'raw_' + tag); os.makedirs(RAW, exist_ok=True); c.out = RAW
START, CHAR, SHOP, CELL, BUY = 0x41, 0x42, 0x43, 0x10, 0x41
def enter(): r.call(0x428bc, 0, 6, 0); c.step(10)
def fl(): t = c.top(); return {'cls': hex(c.cls()), 'f': [c.s8(t, o) for o in range(0xc, 0x14)]}
def money(): return r.call(0x9b4e8, c.bag()) & 0xffffffff
def bunch(i): return r.call(0x9b6c0, c.bag(), 11, i) & 0xffffffff
def dump(nm):
    if NEW: json.dump({str(a): b for a, b in c.hits().items()}, open(os.path.join(OUT, 'hits_%s_%s.json' % (nm, tag)), 'w'))
def goshop():
    if NEW: c.tap(SHOP, 4)
    else: c.key('RIGHT', 3); c.key('OK', 5)
def sel(k):
    if NEW: c.tap(CELL + k, 3)
    else:
        for q in ['RIGHT'] * (k % 4) + ['DOWN'] * (k // 4): c.key(q, 3)
def popup_btn(first=True):
    if NEW and c.s8(c.top(), 0xe) in (4, 5):
        c.tap(0xA0 if first else 0xA1, 5); return
    if NEW and c.s8(c.top(), 0xe) in (1, 2, 3):
        hs = c.hits(); b = [v for k, v in hs.items() if v[4] == 4 and k != 0xFE]
        if not b: c.key('OK' if first else 'CLR', 5); return          # popup loại 5 (chọn số lượng): vẽ/xử lý gốc ở cả hai bản
        h = b[0] if first else b[-1]; r.touch((h[0] + h[2]) // 2, (h[1] + h[3]) // 2, True); c.step(5)
    else: c.key('OK' if first else 'CLR', 5)
def sc_ready():
    enter(); out = {'init': fl()}; c.shot('ex0'); dump('ex')
    goshop(); out['shop'] = fl(); c.shot('ex_shop'); dump('exs'); return out
def sc_char():
    enter()
    if NEW: c.tap(CHAR, 6)
    else: c.key('OK', 6)
    out = {'char': fl()}; c.shot('ex_char'); return out
def sc_start():
    enter(); heroHP = 0
    if NEW: c.tap(START, 4)
    else:
        c.key('UP', 3); c.key('OK', 4)
    return {'start': fl()}
def sc_shop():
    enter(); goshop(); out = {}
    sel(2); out['sel2'] = fl(); c.shot('ex_sel')
    if NEW: c.tap(CELL + 2, 5)
    else: c.key('OK', 5)
    out['poor'] = fl(); c.shot('ex_poor')
    popup_btn(); out['closed'] = fl()
    sel(5) if False else None
    return out
def sc_buy():
    enter(); goshop(); out = {}
    r.call(0x9e828, c.bag(), 1000); out['money0'] = money(); out['b0'] = bunch(12)
    sel(1)
    if NEW: c.tap(BUY, 5)
    else: c.key('OK', 5)
    out['confirm'] = fl(); c.shot('ex_confirm')
    popup_btn(True); out['after1'] = fl()
    popup_btn(True); out['after2'] = fl(); out['money1'] = money(); out['b1'] = bunch(12)
    return out
def sc_cycles():
    def one(k):
        enter(); c.step(2)
        if k % 2:
            goshop(); c.step(2); sel(k % 3)
        for _ in range(6):
            if c.cls() == 0x15e150: break
            r.key('CLR'); r.step(5)
    for k in range(20): one(k)
    live0 = len(g.heap.sizes); top0 = g.heap.top
    for k in range(20): one(k)
    r.step(10)
    return {'live': len(g.heap.sizes) - live0, 'top': g.heap.top - top0, 'img': hashlib.md5(r.shot()).hexdigest(), 'cls': hex(c.cls())}
for nm in os.environ.get('SC', 'ready,char,shop,buy,cycles').split(','): c.fork(globals()['sc_' + nm], nm)
