#!/usr/bin/env python3
"""L1b/T17c8: kiểm StateMap (menu mới) vs bản gốc cùng thao tác. Dùng: check_map.py W H MODE OUTDIR. Kịch bản (SC=): toggle, star, cursor, close, cycles."""
import sys, os, json, hashlib
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1b'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
c = Ctx(W, Hh, MODE, OUT); NEW = c.NEW; r = c.r; g = c.g; tag = c.tag
RAW = os.path.join(OUT, 'raw_' + tag); os.makedirs(RAW, exist_ok=True); c.out = RAW
H_STAR, H_HASH, H_CLOSE = 0x30, 0x31, 1; H5 = Hh
def fl():
    t = c.top()
    return {'cls': hex(c.cls()), 'f': [c.s8(t, o) for o in (0xc, 0xd)], 'cur': [c.s8(t, 0x1a), c.s8(t, 0x1b), c.s8(t, 0x1c), c.s8(t, 0x1d)],
            'cam': [c.s16(t + o) for o in (0x16, 0x18)], 'zone': [c.s16(t + o) for o in (0x2a, 0x2c, 0x2e, 0x30, 0x32, 0x34, 0x3a, 0x3c)] + [c.s8(t, 0x38), c.s8(t, 0x39)],
            'mm': c.s8(c.u32(c.gv + 5200), 4224), 'close': c.s8(c.gv, 0xe0)}      # chỉ so trường LOGIC: camera đích +0x16/+0x18; KHÔNG so +0xe/+0x10/+0x12/+0x14/+0x1e/+0x20 (hoạt hình do DrawWorldMap gốc cập nhật mỗi khung, Paint mới không gọi); +0x2a/+0x2c (offset vẽ khu vực) được trả lại đúng giá trị gốc sau DrawZone
def enter(par=0): c.r.call(0x428bc, 0, 7, par); c.step(8)
def hits(name):
    if NEW: json.dump({str(a): b for a, b in c.hits().items()}, open(os.path.join(OUT, 'hits_%s_%s.json' % (name, tag)), 'w'))
def to_big():                       # khu vực -> bản đồ lớn: '#'
    if NEW: c.tap(H_HASH, 12)
    else: c.key('HASH', 12)
def to_zone():                      # bản đồ lớn -> khu vực: OK gốc ('#' không làm gì ở bản đồ lớn)
    if NEW: c.tap(H_HASH, 12)
    else: c.key('OK', 12)
def star():
    if NEW: c.tap(H_STAR, 6)
    else: c.key('STAR', 6)
def sc_toggle():
    enter(); out = {'init': fl()}; c.shot('map1'); hits('mz'); to_big()
    out['big'] = fl(); c.shot('map0'); hits('mb'); to_zone()
    out['zone'] = fl(); to_big(); out['big2'] = fl(); return out
def sc_star():
    enter(); out = {'init': fl()}
    for k in range(3): star(); out['star%d' % k] = fl()
    c.shot('map_star'); to_big()
    for k in range(2): star(); out['bstar%d' % k] = fl()
    return out
def sc_cursor():
    enter(); out = {}; to_big()
    for i, k in enumerate(('RIGHT', 'RIGHT', 'DOWN', 'LEFT', 'UP')):
        c.key(k, 14); out['k%d' % i] = fl()
    c.shot('map_cursor'); return out
def sc_pt():                         # chạm điểm: gốc = chạm hộp 60x60 quanh điểm (ptx-30..ptx+60, pty-30..pty+60, trừ camera); mới = vùng chạm H_PT+i
    enter(); out = {}; to_big(); t = c.top(); out['b0'] = fl()
    def tap_pt(i):
        if NEW: c.tap(0x40 + i, 14)
        else:
            px, py = c.s16(t + 0x2e8 + 8 * i), c.s16(t + 0x2ea + 8 * i); cx, cy = c.s16(t + 0xe), c.s16(t + 0x10)
            c.touch_xy(px + 15 - cx, py + 15 - cy, 14)
    for i in (3, 5, 1): tap_pt(i); out['pt%d' % i] = fl()
    c.shot('map_pt'); tap_pt(1); out['pt1_again'] = fl(); return out
def sc_pan():
    enter(); out = {'init': fl()}
    for nm, ox, oy in (('D', W // 2, H5 * 5 // 6), ('D2', W // 2, H5 * 5 // 6), ('U', W // 2, H5 // 6), ('U2', W // 2, H5 // 6)):
        if NEW: c.tap(0x51 if nm[0] == 'D' else 0x50, 14)
        else: c.touch_xy(ox, oy, 14)
        out[nm] = fl()
    for k in ('LEFT', 'RIGHT'): c.key(k, 14); out[k] = fl()
    return out
def sc_mtouch():                     # '*' qua chạm: gốc chạm góc trái dưới (0..69 x H-17..H); cả 2 chế độ
    enter(); out = {'init': fl()}
    for k in range(2):
        if NEW: c.tap(H_STAR, 8)
        else: c.touch_xy(20, H5 - 8, 8)
        out['z%d' % k] = fl()
    to_big()
    for k in range(2):
        if NEW: c.tap(H_STAR, 8)
        else: c.touch_xy(20, H5 - 8, 8)
        out['b%d' % k] = fl()
    return out
def sc_close():
    enter(); out = {'init': fl()}
    if NEW: c.tap(H_CLOSE, 6)
    else: c.key('CLR', 6)
    out['after'] = fl(); c.step(10); out['later'] = fl(); return out
def sc_cycles():
    c.skip_intro()
    for k in range(10):
        enter(); c.key('HASH', 6); c.key('CLR', 6); c.step(10)
    live0 = len(g.heap.sizes); top0 = g.heap.top
    for k in range(20):
        enter(); c.step(2)
        if k % 2: to_big() if k % 4 == 1 else None
        if k % 3 == 0: star()
        if k % 4 == 3: to_big(); to_zone()
        c.key('CLR', 6); c.step(10)
    r.step(10); c.shot('cyc_end')
    return {'live': len(g.heap.sizes) - live0, 'top': g.heap.top - top0, 'cls': hex(c.cls()), 'mimg': c.mimg()}
for nm in os.environ.get('SC', 'toggle,star,cursor,pt,pan,mtouch,close,cycles').split(','): c.fork(globals()['sc_' + nm], nm)
