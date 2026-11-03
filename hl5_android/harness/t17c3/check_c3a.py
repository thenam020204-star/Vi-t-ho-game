#!/usr/bin/env python3
"""T17c3a: kiểm Liên kích (menu mới) so với v0.2-hd. Dùng: check_c3a.py W H MODE OUTDIR (MODE new = chạm | orig = phím gốc). Ghi OUTDIR/res_<WxH>_<mode>.json."""
import sys, os, json, glob, hashlib
H = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, H)
from qlib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
c = Ctx(W, Hh, MODE, OUT)
NEW = c.NEW; r = c.r; g = c.g; tag = c.tag; h = c.hero
RAW = os.path.join(OUT, 'raw_' + tag); os.makedirs(RAW, exist_ok=True); c.out = RAW
def belt(): return [r.call(0x84ee0, h, i) & 255 for i in range(5)]
def ptrs():
    t = c.top(); return {'st': c.b8(t, 0xc), 'sub': c.b8(t, 0xd), 'e': c.b8(t, 0xe), 'f': c.b8(t, 0xf), 'cls': hex(c.cls()), 'belt': belt(), 'ptype': c.b8(t, 0xe) if c.cls() != 0x15e290 else None}
def learn(ids):          # đặt kỹ năng đã học (cấp 1) cho id kỹ năng trong bảng ứng viên
    for sk in ids:
        idx = r.call(g.rd32(g.rd32(h) + 0x24) - BASE, h, sk) & 255; c.wr(h + 584 + idx + 4, [1])
def A(keys, hid, w=3):
    """mode new: chạm hid; orig: chuỗi phím gốc"""
    if NEW: c.tap(hid, w)
    else:
        for k in keys: c.key(k, 3)
def sc_assign():
    learn([3, 4, 5, 6]); c.S3(6, 8); c.shot('lien_kich_a')
    out = {'open': ptrs()}
    A(['OK'], 0xA0 + 0); out['sub1'] = ptrs(); c.shot('lien_kich')
    A(['RIGHT', 'OK'], 0xC0 + 0); out['a0'] = ptrs()
    A(['RIGHT', 'OK'], 0xA0 + 1); out['s1'] = ptrs()          # NEW: chạm ô 1 (vào sub 1)
    A(['RIGHT', 'RIGHT', 'RIGHT', 'OK'], 0xC0 + 2); out['a1'] = ptrs()
    return out
def sc_unlearned():
    c.S3(6, 8); A(['OK'], 0xA0 + 0)
    A(['RIGHT', 'RIGHT', 'RIGHT', 'OK'], 0xC0 + 2, 6); out = {'p': ptrs()}; c.shot('lien_kich_popup')
    A(['OK'], 0xF2, 8) if NEW else c.key('OK', 8); r.step(10); out['after'] = ptrs(); return out
def sc_clear():
    learn([3, 4]); c.S3(6, 8)
    if NEW: c.tap(0xA0 + 0); c.tap(0xC0 + 0, 5)
    else:
        for k in ['OK', 'RIGHT', 'OK']: c.key(k, 3)
    out = {'assigned': ptrs()}
    if NEW: c.tap(0xCA, 6)                                   # sub 0 hoặc 1: nút Bỏ chọn
    else:
        for k in ['OK', 'OK']: c.key(k, 3)                   # gốc: sub 0 -> OK (sub 1, f=0) -> OK (Tháo)
    out['cleared'] = ptrs()
    return out
def sc_nav():
    learn([3]); c.S3(6, 8); A(['OK'], 0xA0 + 0); out = {}
    for i in range(7): A(['RIGHT'], 0xC6); out['n%d' % i] = ptrs()
    A(['LEFT'], 0xC5); out['p'] = ptrs()
    c.shot('lien_kich_nav'); return out
def sc_slot_back():
    learn([3]); c.S3(6, 8); A(['OK'], 0xA0 + 0); out = {'s1': ptrs()}
    if NEW: c.tap(0xA0 + 2)                                 # đổi ô chuỗi khi đang ở sub 1
    else: c.key('CLR', 3); c.key('RIGHT', 3); c.key('RIGHT', 3); c.key('OK', 3)
    out['s2'] = ptrs(); return out
def sc_slot5():
    c.wr(h + 0x22c, [1]); c.wr(h + 0x267, [1]); learn([5, 9, 12]); c.S3(6, 8); c.shot('lien_kich_5')
    out = {}
    if NEW: c.tap(0xA0 + 4); c.tap(0xC0 + 1, 5)
    else:
        c.key('LEFT', 3); c.key('OK', 3); c.key('RIGHT', 3); c.key('RIGHT', 3); c.key('OK', 5)
    out['p'] = ptrs(); return out
def sc_class(k):
    def f():
        c.wr(h + 0x22c, [k]); c.S3(6, 8); learn(range(0, 21)); A(['OK'], 0xA0 + 0)
        A(['RIGHT', 'RIGHT', 'RIGHT', 'RIGHT', 'RIGHT', 'RIGHT', 'OK'], 0xC0 + 4)       # NEW: cand 4 trang 0 = f 5 ; orig: f=6?  (xem so sánh)
        return {'p': ptrs()}
    return f
def sc_class_fix(k):
    def f():
        c.wr(h + 0x22c, [k]); c.S3(6, 8); learn(range(0, 21)); A(['OK'], 0xA0 + 0)
        A(['RIGHT'] * 5 + ['OK'], 0xC0 + 4); return {'p': ptrs()}
    return f
def sc_back():
    c.S3(6, 8); A(['CLR'], 1, 4); return {'p': ptrs()}
def sc_tabs():
    for i in range(9):
        pid = os.fork()
        if pid: os.waitpid(pid, 0); continue
        c.S3(6); r.step(2)
        try:
            if NEW: c.tap(0x10 + i)
            else: r.call(0xfa894, c.top(), i + 1); r.step(3)
            r.step(10); json.dump({'state': c.st()}, open(os.path.join(OUT, 'tab%d_%s_%s.json' % (i, tag, MODE)), 'w'))
        except Exception as e: json.dump({'ERR': repr(e)}, open(os.path.join(OUT, 'tab%d_%s_%s.json' % (i, tag, MODE)), 'w'))
        os._exit(0)
    return {'done': 1}
def sc_cycles():
    live0 = len(g.heap.sizes); top0 = g.heap.top
    for k in range(20):
        c.S3(6, 5)
        if k % 2 == 0 and NEW: c.tap(0xA0 + 1)
        elif k % 2 == 0: c.key('OK')
        for _ in range(4):
            if c.cls() == 0x15e150: break
            r.key('CLR'); r.step(5)
    r.step(10)
    return {'live': len(g.heap.sizes) - live0, 'top': g.heap.top - top0, 'img': hashlib.md5(r.shot()).hexdigest(), 'cls': hex(c.cls())}
def sc_hits():
    if not NEW: return {}
    c.S3(6, 8); c.tap(0xA0 + 0)
    d = {str(k): v for k, v in c.hits().items()}
    json.dump(d, open(os.path.join(OUT, 'hits_combo_%s.json' % tag), 'w')); return {'ok': 1}
for name, fn in [('assign', sc_assign), ('unlearned', sc_unlearned), ('clear', sc_clear), ('nav', sc_nav), ('slot_back', sc_slot_back), ('slot5', sc_slot5)] + \
                [('class%d' % k, sc_class_fix(k)) for k in range(4)] + [('back', sc_back), ('tabs', sc_tabs), ('cycles', sc_cycles), ('hits', sc_hits)]:
    c.fork(fn, name)
res = {}
for fn in glob.glob(os.path.join(RAW, 'sc_*_%s_%s.json' % (tag, MODE))) + glob.glob(os.path.join(OUT, 'tab*_%s_%s.json' % (tag, MODE))):
    res[os.path.basename(fn).split('_%s_' % tag)[0].replace('sc_', '', 1)] = json.load(open(fn))
json.dump(res, open(os.path.join(OUT, 'res_%s_%s.json' % (tag, MODE)), 'w'), indent=1, sort_keys=True)
print('xong', tag, MODE)
