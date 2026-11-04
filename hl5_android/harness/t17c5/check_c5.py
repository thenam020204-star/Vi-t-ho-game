#!/usr/bin/env python3
"""L2/T17c5: kiểm NPC menu mới so với bản gốc. check_c5.py W H MODE OUTDIR  (MODE new = dev.so, chạm | orig = phím gốc làm chuẩn). WORK mặc định ~/hl5work_l2.
Mỗi kịch bản fork từ khung ~1000 (boot ~90 s): vào NPC state T (tiền 100000), thao tác, ghi: trường state NPC, lớp state trên cùng, tiền, hash túi/kỹ năng."""
import sys, os, json, struct, hashlib
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l2'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't17c2'))
from qlib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
only = sys.argv[5:] 
c = Ctx(W, Hh, MODE, OUT); NEW = c.NEW; r = c.r; g = c.g; tag = c.tag
RAW = os.path.join(OUT, 'raw_' + tag); os.makedirs(RAW, exist_ok=True); c.out = RAW
H_CELL, H_TAB, H_PAGE, H_BUY, POP = 0x40, 0x70, 0x78, 0x90, 0xA0
def money(): return struct.unpack('<i', bytes(g.uc.mem_read(c.hero + 6080, 4)))[0]
def snap():
    tp = c.top(); bag = r.call(0x85448, c.hero)
    return {'cls': hex(c.cls()), 'f': list(bytes(g.uc.mem_read(tp, 0x14))[0xc:]), 'money': money(),
            'bag': hashlib.md5(bytes(g.uc.mem_read(bag + 0x188, 0x60)) + bytes(g.uc.mem_read(bag + 872, 2))).hexdigest()[:10], 'sk': hashlib.md5(bytes(g.uc.mem_read(c.hero + 588, 43))).hexdigest()[:8],
            'files': c.files()}
def act(a):
    if not NEW:
        if a[0] == 'key': c.key(a[1], 4)
        if a[0] == 'keys':
            for k in a[1]: c.key(k, 4)
        return
    if a[0] == 'tap':
        h = c.hits(); hid = a[1]
        if hid == POP and POP not in h: hid = 0xF0 if 0xF0 in h else 0xF2
        if hid == POP + 1 and POP + 1 not in h: hid = 0xF1
        if os.environ.get('DBG'): print('TAP', hex(hid), 'cls', hex(c.cls()), sorted(h.keys())[:6], file=sys.stderr, flush=True)
        c.tap(hid, 4)
    elif a[0] == 'key': c.key(a[1], 4)
def run(typ, seq, m=100000, name=None):
    def f():
        g.uc.mem_write(c.hero + 6080, struct.pack('<i', m)); r.call(0x42b08, 0, typ, 1); c.step(15)
        out = [snap()]
        if NEW and name: json.dump({str(k): v for k, v in c.hits().items()}, open(os.path.join(OUT, 'hits_%s_%s.json' % (name, tag)), 'w'))
        for a in seq:
            x = a[1] if NEW else a[0]
            if x is not None: act(x)
            out.append(snap())
        if name: c.step(4); c.shot(name)
        return out
    return f
K = lambda k: ('key', k)
KS = lambda *k: ('keys', list(k))
T = lambda h: ('tap', h)
SC = {
 'skill_buy': (0, [(K('RIGHT'), T(H_CELL + 1)), (K('OK'), T(H_CELL + 1)), (K('OK'), T(POP)), (K('OK'), T(POP))]),
 'skill_nomoney': (0, [(K('OK'), T(H_CELL + 0)), (K('OK'), T(POP))], 100),
 'item_buy': (1, [(KS('OK', 'RIGHT'), T(H_CELL + 1)), (K('OK'), T(H_CELL + 1)), (K('OK'), T(POP)), (K('OK'), T(POP))]),
 'item_buy_tab1': (1, [(K('RIGHT'), T(H_TAB + 1)), (KS('OK', 'DOWN'), T(H_CELL + 4)), (K('OK'), T(H_CELL + 4)), (K('OK'), T(POP)), (K('OK'), T(POP))]),
 'item_buy_no': (1, [(KS('OK'), T(H_CELL + 0)), (K('OK'), T(H_CELL + 0)), (K('RIGHT'), None), (K('OK'), T(POP + 1))]),
 'item_buy_btn': (1, [(K('RIGHT'), T(H_TAB + 1)), (KS('OK', 'OK'), T(H_BUY)), (K('OK'), T(POP))]),
 'sale': (2, [(KS('OK', 'RIGHT', 'RIGHT', 'RIGHT'), T(H_CELL + 3)), (K('OK'), T(H_CELL + 3)), (K('OK'), T(POP))]),
 'sale_page': (2, [(K('DOWN'), T(H_PAGE + 1)), (KS('OK'), T(H_CELL + 0)), (K('OK'), T(H_CELL + 0))]),
 'coll_menu': (7, [(KS('DOWN', 'OK'), T(H_CELL + 1))]),
}
for name, spec in SC.items():
    if only and name not in only: continue
    typ, seq = spec[0], spec[1]; m = spec[2] if len(spec) > 2 else 100000
    c.fork(run(typ, seq, m, name), name)
