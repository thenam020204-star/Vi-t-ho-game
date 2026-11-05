#!/usr/bin/env python3
"""L1/T17c6: kiểm Cash shop (menu mới) vs bản gốc cùng thao tác. Dùng: check_cash.py W H MODE OUTDIR  (MODE new|orig). Kết quả OUTDIR/res_cash_<WxH>_<mode>.json, ảnh raw_<WxH>/, hit_*.json (new)."""
import sys, os, json
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
c = Ctx(W, Hh, MODE, OUT); NEW = c.NEW; r = c.r; g = c.g; tag = c.tag
RAW = os.path.join(OUT, 'raw_' + tag); os.makedirs(RAW, exist_ok=True); c.out = RAW
def logpos(): g.log.flush(); return os.path.getsize(g.log.name)
def nets(p): g.log.flush(); return [l for l in open(g.log.name, 'rb').read()[p:].decode('latin1').split('\n') if l.startswith('NET')]
def bunch(i): return r.call(0x9b6c0, c.bag(), 18, i) & 0xffffffff
def enter(): r.call(0x42ab8, 0, 0); c.step(10)
def fl(): t = c.top(); return {'cls': hex(c.cls()), 'f': [c.s8(t, o) for o in range(0xc, 0x1c)]}
CELL = 0x10
def sel(k, key_seq):
    if NEW: c.tap(CELL + k, 3)
    else:
        for q in key_seq: c.key(q, 3)
def sc_sel():
    enter(); out = {'init': fl()}; c.shot('cash0')
    if NEW: json.dump({str(a): b for a, b in c.hits().items()}, open(os.path.join(OUT, 'hits_cash_%s.json' % tag), 'w'))
    sel(5, ['RIGHT', 'DOWN']); out['sel5'] = fl()
    sel(14, ['DOWN', 'DOWN', 'RIGHT']); out['sel14'] = fl(); c.shot('cash_sel')
    sel(3, ['UP', 'UP', 'LEFT', 'LEFT', 'LEFT'][:0] or ['LEFT'] * 0); return out
def sc_drag():
    enter(); out = {}
    if NEW:
        c.tap(CELL + 12, 3); h = c.hits()[0x42]; x = h[2] - 2; y0 = h[1] + 60
        r.touch(x, y0, False); r.move(x, y0 - (h[3] - h[1]) // 4 - 3); c.step(2); r.g.call(r.touchf, r.g.env, 0, 20, x, y0 - 40, 0); c.step(3)
    else:
        for _ in range(4): c.key('DOWN', 3)
    out['drag'] = fl(); c.shot('cash_drag'); return out
def sc_popup():
    enter(); sel(4, ['RIGHT'] * 4)
    if NEW: c.tap(CELL + 4, 5)
    else: c.key('OK', 5)
    return fl()
def sc_cycles():
    c.skip_intro()
    for k in range(20): enter(); sel(k % 16, ['RIGHT'] * (k % 4) + ['DOWN'] * ((k % 16) // 4)); [r.key('CLR') or r.step(5) for _ in range(3)]   # khởi động nguội (cấp phát một lần)
    live0 = len(g.heap.sizes); top0 = g.heap.top
    for k in range(20):
        enter(); sel(k % 16, ['RIGHT'] * (k % 4) + ['DOWN'] * ((k % 16) // 4)); c.step(2)
        for _ in range(6):
            if c.cls() == 0x15e150: break
            r.key('CLR'); r.step(5)
    r.step(10); c.shot("cyc_end")
    import hashlib
    return {'live': len(g.heap.sizes) - live0, 'top': g.heap.top - top0, 'img': hashlib.md5(r.shot()).hexdigest(), 'mimg': c.mimg(), 'cls': hex(c.cls())}
def sc_buy():
    p0 = logpos(); enter(); res = {}
    rec = r.rdb(c.u32(c.u32(c.top() + 0x14) + 4) + 24 * 4, 24); iid = rec[8]; res['rec'] = list(rec)
    res['before'] = bunch(iid)
    sel(4, ['RIGHT'] * 4)
    if NEW: c.tap(CELL + 4, 5)
    else: c.key('OK', 5)
    res['popup1'] = fl(); c.shot('cash_popup1')
    if NEW:
        hs = c.hits(); res['popup_hits'] = {str(k): v for k, v in hs.items()}
        yes = [v for k, v in hs.items() if v[4] == 4 and k not in (0xFE,)]
        res['yes'] = yes[:2]
        h = yes[0]; r.touch((h[0] + h[2]) // 2, (h[1] + h[3]) // 2, True); c.step(5)
    else: c.key('OK', 5)
    res['popup2'] = fl(); c.shot('cash_popup2')
    if NEW:
        hs = c.hits(); h = [v for k, v in hs.items() if v[4] == 4 and k != 0xFE][0]; r.touch((h[0] + h[2]) // 2, (h[1] + h[3]) // 2, True); c.step(5)
    else: c.key('OK', 5)
    res['popup3'] = fl(); res['after'] = bunch(iid); res['net'] = nets(p0)
    return res
for nm in (os.environ.get('SC', 'sel,drag,popup,cycles,buy' if NEW else 'sel,drag,popup,cycles').split(',')): c.fork(globals()['sc_' + nm], nm)
