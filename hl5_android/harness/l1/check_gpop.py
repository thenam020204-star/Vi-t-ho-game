#!/usr/bin/env python3
"""L1/T17c6: kiểm popup StateGame (hồi sinh Có/Không, thành công) menu mới vs gốc. Dùng: check_gpop.py W H MODE OUTDIR"""
import sys, os, json, hashlib
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
OFF = MODE == 'off'   # cùng dev.so nhưng tắt menu mới (cờ MUI_F_DISABLE): chuẩn 'gốc + logic T7' (bản v0.2-hd thuần không có hồi sinh offline)
c = Ctx(W, Hh, 'new' if OFF else MODE, OUT)
if OFF:
    c.NEW = False; c.mode = 'orig'; c.wr(BASE + c.sym['mui_flags'], [1, 0, 0, 0])
NEW = c.NEW; r = c.r; g = c.g; tag = c.tag
RAW = os.path.join(OUT, 'raw_' + tag); os.makedirs(RAW, exist_ok=True); c.out = RAW
def fl():
    t = c.top(); return {'cls': hex(c.cls()), 'f': [c.s8(t, o) for o in range(0xc, 0x14)], 'hp': c.u32(c.hero + 0xf0), 'bua': r.call(0x9b6c0, c.bag(), 18, 23) & 0xffffffff}
def dump(n): 
    if NEW: json.dump({str(a): b for a, b in c.hits().items()}, open(os.path.join(OUT, 'hits_%s_%s.json' % (n, tag)), 'w'))
def go(n): t = c.top(); r.call(0xda410, t, n, 0); c.step(8)
def sc_yes():
    go(1); out = {'s1': fl()}; c.shot('gp1'); dump('gp1'); c.wr(c.hero + 0xf0, [5, 0, 0, 0])
    if NEW: c.tap(0xF0, 6)
    else: c.key('OK', 6)
    out['after_yes'] = fl(); c.shot('gp4'); dump('gp4')
    if NEW: c.tap(0xF2, 6)
    else: c.key('OK', 6)
    out['after_ok'] = fl(); return out
def sc_bua():       # có Bùa Hồi Sinh trong túi (loại 18 id 23): popup xác nhận CT(293)
    c.callx(0xa321c, c.u32(c.gv + 5132), 18, 23, 2, 1)
    go(1); out = {'s1': fl()}; c.shot('gp1x'); dump('gp1x'); c.wr(c.hero + 0xf0, [5, 0, 0, 0])
    if NEW: c.tap(0xF0, 6)
    else: c.key('OK', 6)
    out['after_yes'] = fl(); c.step(10); out['later'] = fl(); return out
def sc_no():
    go(1); out = {'s1': fl()}
    if NEW: c.tap(0xF1, 6)
    else: c.key('RIGHT', 4); c.key('OK', 6)
    out['after_no'] = fl(); c.step(10); out['later'] = fl(); return out
def sc_cycles():
    c.skip_intro()
    def one():
        go(1)
        if NEW: c.tap(0xF0, 4); c.tap(0xF2, 4)
        else: c.key('OK', 4); c.key('OK', 4)
    for k in range(20): one()
    live0 = len(g.heap.sizes); top0 = g.heap.top
    for k in range(20): one()
    r.step(10)
    return {'live': len(g.heap.sizes) - live0, 'top': g.heap.top - top0, 'img': hashlib.md5(r.shot()).hexdigest(), 'mimg': c.mimg(), 'cls': hex(c.cls())}
for nm in os.environ.get('SC', 'yes,no,bua,cycles').split(','): c.fork(globals()['sc_' + nm], nm)
