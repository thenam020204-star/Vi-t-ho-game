#!/usr/bin/env python3
"""L3/T17c7: kiểm MainMenu mới (chạm) vs gốc v0.2-hd (phím). Dùng: check_mm.py W H MODE OUTDIR  (MODE new|orig|off). 'off' = bản dựng mới nhưng cờ MUI_F_DISABLE bật (đường gốc, dùng phím).
Kết quả OUTDIR/raw_<WxH>/sc_<tên>_<WxH>_<mode>.json; hits_*.json (new)."""
import sys, os, json
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l3'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
c = Ctx(W, Hh, 'new' if MODE == 'off' else MODE, OUT, boot_to=10); NEW = MODE == 'new'; r = c.r; g = c.g; tag = c.tag
c.mode = MODE
RAW = os.path.join(OUT, 'raw_' + tag); os.makedirs(RAW, exist_ok=True); c.out = RAW
if MODE == 'off': g.uc.mem_write(BASE + c.sym['mui_flags'], (1).to_bytes(4, 'little'))
T6 = [0, 1, 4, 5, 6, 8]
GV = c.gv
def fl(): t = c.top(); return {'cls': hex(c.cls()), 'f': [c.s8(t, o) for o in range(0xc, 0x14)]}
def gvv(): return [c.s8(GV, 132 + i) for i in range(15)]
def kfile():
    p = os.path.join(g.a.storage, 'hd_scale.txt'); return open(p).read() if os.path.exists(p) else None
def enter(): r.step(700); r.key('OK'); r.step(10)
def pop(): t = c.top(); res = c.u32(t + 0x14); return {'t': c.s8(t, 0xe), 'nb': c.s8(t, 0x10), 'res': c.s8(res) if res else None}
def kseq(ks):
    for k in ks: c.key(k, 3)
def grid_to(o):
    if NEW: c.tap(0x10 + T6.index(o), 5)
    else: kseq(['DOWN'] * (o // 3) + ['RIGHT'] * (o % 3) + ['OK']); c.step(2)
def back():
    if NEW: c.tap(1, 5)
    else: c.key('CLR', 5)
# ---- kịch bản (mỗi cái chạy trong tiến trình con từ cùng điểm: menu chính state 4) ----
def sc_grid():
    out = {}
    for o in T6:
        pid = os.fork()
        if pid: os.waitpid(pid, 0); continue
        try:
            grid_to(o); d = fl(); d['gv'] = gvv()
            if o == 8: d['pop'] = pop()
            if o in (0, 1): d['slots'] = [c.s8(c.top(), 0xe)]
        except Exception as e: d = {'ERR': repr(e)}
        json.dump(d, open(os.path.join(RAW, 'g_%d_%s_%s.json' % (o, tag, MODE)), 'w')); os._exit(0)
    for o in T6: out[o] = json.load(open(os.path.join(RAW, 'g_%d_%s_%s.json' % (o, tag, MODE))))
    return out
def sc_nav():       # phím điều hướng lưới (cả 'off' = 3x3 gốc, nên chỉ kiểm new: chuỗi ô gốc đi qua)
    out = []; t = c.top()
    for k in ['RIGHT', 'RIGHT', 'RIGHT', 'DOWN', 'LEFT', 'LEFT', 'LEFT', 'UP', 'UP', 'DOWN', 'DOWN', 'RIGHT']: c.key(k, 2); out.append(c.s8(t, 0xe))
    return out
def sc_slot_class():
    out = {}; grid_to(0)
    out['slot'] = fl()
    if NEW: c.tap(0x20, 5)
    else: c.key('OK', 5)
    out['class_in'] = fl()
    for t in (3, 1, 2, 0, 3):
        if NEW: c.tap(0x30 + t, 4)
        else:
            cur = c.s8(c.top(), 0xf)
            if (cur ^ t) & 1: c.key('RIGHT', 3)
            if ((c.s8(c.top(), 0xf)) ^ t) & 2: c.key('DOWN', 3)
        out['cls%d' % t] = fl()
    if NEW: c.tap(0x33, 5)
    else: c.key('OK', 5)
    out['popup'] = fl(); out['popup_f'] = pop()
    if NEW: out['popup_hits'] = {str(k): v[:4] for k, v in c.hits().items()}
    c.shot('classpop')
    hero0 = None
    if NEW: c.tap(0xC0, 10)
    else: c.key('OK', 10)
    c.step(20); out['after_ok'] = fl(); out['hero_cls'] = c.s8(c.hero, 0x22c) if c.hero else None
    return out
def sc_class_cancel():
    grid_to(0)
    if NEW: c.tap(0x20, 5)
    else: c.key('OK', 5)
    if NEW: c.tap(0x31, 4); c.tap(0x31, 5)
    else: c.key('RIGHT', 3); c.key('OK', 5)
    out = {'popup': fl(), 'pf': pop()}
    if NEW: c.tap(0xC1, 5)
    else: c.key('RIGHT', 3); c.key('OK', 5)
    out['after'] = fl(); out['pf2'] = pop() if c.cls() == 0x15e5b0 else None
    return out
def sc_back():
    out = {}
    for name, steps in (('slot', [0]), ('class', [0, 'slot']), ('opt', [4]), ('exp', [1])):
        pid = os.fork()
        if pid: os.waitpid(pid, 0); out[name] = json.load(open(os.path.join(RAW, 'b_%s_%s_%s.json' % (name, tag, MODE)))); continue
        try:
            if name == 'slot': grid_to(0)
            elif name == 'class':
                grid_to(0)
                if NEW: c.tap(0x20, 5)
                else: c.key('OK', 5)
            elif name == 'opt': grid_to(4)
            else: grid_to(1)
            a = fl(); back(); b = fl(); d = {'in': a, 'out': b}
            if name == 'class':
                back(); d['out2'] = fl()
        except Exception as e: d = {'ERR': repr(e)}
        json.dump(d, open(os.path.join(RAW, 'b_%s_%s_%s.json' % (name, tag, MODE)), 'w')); os._exit(0)
    return out
def sc_expert():
    grid_to(1); out = {'in': fl()}
    if NEW: c.tap(0x22, 5)
    else: c.key('DOWN', 3); c.key('DOWN', 3); c.key('OK', 5)
    out['sel'] = fl(); out['pop'] = pop() if c.cls() == 0x15e5b0 else None
    c.shot('expert')
    return out
def opt_step(i):
    t = c.top()
    if NEW:
        while c.s8(t, 0xf) < i: c.tap(0x91, 2)
        row = c.s8(t, 0xe); ty = [1, 1, 2, 0, 4, 0, 0, 3, -1, 5][i]
        if ty in (2, 3, 4): c.tap(0x60 + row, 3)
        elif ty in (0, 1): c.tap(0x70 + 8 * row + (1 if oval(i) else 0), 3)
        elif ty == 5: c.tap(0x70 + 8 * row + 3, 3)
        else: c.tap(0x50 + row, 3)
    else:
        for _ in range(i): c.key('DOWN', 2)
        if i != 8: c.key('RIGHT', 3)
        if i == 9: c.key('RIGHT', 3)
def oval(i): return c.s8(GV, 142 if i == 7 else 132 + i)
def sc_opt():
    out = {}
    for i in range(10):
        pid = os.fork()
        if pid: os.waitpid(pid, 0); out[i] = json.load(open(os.path.join(RAW, 'o_%d_%s_%s.json' % (i, tag, MODE)))); continue
        try:
            grid_to(4); d0 = fl(); opt_step(i); d = fl(); d['gv'] = gvv(); d['k'] = kfile(); d['in'] = d0['f'][:2]
        except Exception as e: d = {'ERR': repr(e)}
        json.dump(d, open(os.path.join(RAW, 'o_%d_%s_%s.json' % (i, tag, MODE)), 'w')); os._exit(0)
    return out
def sc_cycles():
    def one():
        grid_to(4 if NEW else 4); back()
        grid_to(0); back()
    for _ in range(3): one()
    live0 = len(g.heap.sizes); top0 = g.heap.top
    for _ in range(20): one()
    r.step(5)
    d = fl(); return {'live': len(g.heap.sizes) - live0, 'top': g.heap.top - top0, 'f': d['f'][:2], 'cls': d['cls']}
def sc_hits():
    out = {}
    def snap(name): out[name] = {str(k): v[:4] for k, v in c.hits().items()}
    snap('grid'); grid_to(0); snap('slot'); c.tap(0x20, 5); snap('class')
    c.tap(0x30, 4); c.tap(0x30, 5); snap('classpop')
    return out
def sc_hits2():
    out = {}
    grid_to(4); out['opt'] = {str(k): v[:4] for k, v in c.hits().items()}; c.shot('opt')
    return out
names = os.environ.get('SC', 'grid,slot_class,class_cancel,back,expert,opt,cycles' + (',nav,hits,hits2' if NEW else '')).split(',')
enter()
for nm in names: c.fork(globals()['sc_' + nm], nm)
