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
H_ROW, H_CLEAR, Q_NO, Q_M10, Q_M1, Q_P1, Q_P10, C0 = 0xB0, 0x94, 0xA1, 0xA2, 0xA3, 0xA4, 0xA5, 0xC0
def money(): return struct.unpack('<i', bytes(g.uc.mem_read(c.hero + 6080, 4)))[0]
def files_rel():                                   # 'kickass' = bản lưu có khối ngẫu nhiên (nonce) khác nhau giữa gốc/mới từ lúc khởi động (kể cả kịch bản cũ khi chỉ mở NPC): so kích thước, nội dung game so qua LOCAL.sav + túi + kỹ năng + tiền
    f = c.files(); p = os.path.join(g.a.storage, 'kickass')
    if 'kickass' in f: f['kickass'] = os.path.getsize(p) if os.path.exists(p) else -1
    return f
def snap():
    tp = c.top(); bag = r.call(0x85448, c.hero)
    return {'cls': hex(c.cls()), 'f': list(bytes(g.uc.mem_read(tp, 0x14))[0xc:]), 'money': money(), 'n': (list(bytes(g.uc.mem_read(tp + 0x18, 4))) + [bytes(g.uc.mem_read(c.gv + 6918, 1))[0]]) if c.cls() == 0x15e330 else None,
            'bag': hashlib.md5(bytes(g.uc.mem_read(bag + 0x188, 0x60)) + bytes(g.uc.mem_read(bag + 872, 2))).hexdigest()[:10], 'sk': hashlib.md5(bytes(g.uc.mem_read(c.hero + 588, 43))).hexdigest()[:8],
            'files': files_rel()}
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
    elif a[0] == 'keys':
        for k in a[1]: c.key(k, 4)
def run(typ, seq, m=100000, name=None):
    def f():
        g.uc.mem_write(c.hero + 6080, struct.pack('<i', m))
        if name and name.startswith('mixok'):
            tbl = g.rd32(c.gv + 5132)
            for cat, id_, n_ in ((13, 38, 10), (13, 17, 10), (13, 40, 10)):
                for _ in range(n_): r.call(0xa321c, tbl, cat, id_, 1, 1)
        if name and name.split('_')[0] in ('pick', 'ref', 'orb', 'qty'):
            tbl = g.rd32(c.gv + 5132)
            for cat, id_, n_ in ((0, 19, 1), (0, 20, 1), (5, 20, 1), (11, 10, 3), (18, 8, 3), (12, 1, 1), (12, 2, 1), (18, 9, 1)):
                for _ in range(n_): r.call(0xa321c, tbl, cat, id_, 1, 1)
        if name and name.split('_')[0] == 'orb':                       # trang bị có 3 ô Orb trống (IsOrbCombinableItem: [0x168]>0, ô đầu = 0)
            bag = r.call(0x85448, c.hero); cnt = r.call(0x9b500, bag)
            for idx in range(cnt):
                ptr = r.call(0x9b5f4, bag, idx // 16, idx % 16)
                if ptr and g.uc.mem_read(ptr + 20, 1)[0] <= 9: g.uc.mem_write(ptr + 0x168, bytes([3, 0, 0, 0]))
        r.call(0x42b08, 0, typ, 1); c.step(15)
        out = [snap()]
        if NEW and name: json.dump({str(k): v for k, v in c.hits().items()}, open(os.path.join(OUT, 'hits_%s_%s.json' % (name, tag)), 'w'))
        for a in seq:
            x = a[1] if NEW else a[0]
            if x is not None: act(x)
            out.append(snap())
        if name: c.step(4); c.shot(name)
        if NEW and name: json.dump({str(k): v for k, v in c.hits().items()}, open(os.path.join(OUT, 'hitsend_%s_%s.json' % (name, tag)), 'w'))
        return out
    return f
K = lambda k: ('key', k)
KS = lambda *k: ('keys', list(k))
T = lambda h: ('tap', h)
ORB_FLOW = [(K('OK'), T(H_ROW)), (K('OK'), T(C0)), (KS('DOWN', 'OK'), T(H_ROW + 1)), (K('OK'), T(C0)), (KS('DOWN', 'OK'), T(H_ROW + 2)), (K('OK'), T(C0)), (K('48'), T(H_CLEAR))]
ORB_BTN = ORB_FLOW[:6] + [(KS('DOWN', 'OK'), T(H_BUY)), (K('OK'), T(POP)), (K('OK'), T(POP))]
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
 'coll_grid': (7, [(K('OK'), T(H_CELL + 0)), (K('RIGHT'), T(H_TAB + 1)), (KS('DOWN', 'RIGHT'), T(H_CELL + 1)), (KS('DOWN', 'DOWN', 'DOWN'), T(H_CELL + 13)), (K('LEFT'), T(H_CELL + 12)), (K('OK'), T(H_CELL + 12))]),
 'coll_acc': (7, [(KS('DOWN', 'DOWN', 'OK'), T(H_CELL + 2)), (K('RIGHT'), T(H_CELL + 1)), (KS('DOWN', 'DOWN'), T(H_CELL + 9))]),
 'coll_arm': (7, [(KS('DOWN', 'OK'), T(H_CELL + 1)), (KS('RIGHT', 'RIGHT', 'RIGHT'), T(H_TAB + 3)), (K('DOWN'), T(H_CELL + 0))]),
 'pick_ref': (3, [(K('OK'), K('OK')), (K('RIGHT'), T(0xC0 + 1)), (K('OK'), T(0xC0 + 1))]),
 'pick_cancel': (4, [(K('OK'), K('OK')), (K('CLR'), T(0xC9))]),
 'ref_flow': (3, [(K('OK'), T(H_ROW)), (K('OK'), T(C0)), (KS('DOWN', 'OK'), T(H_ROW + 1)), (K('RIGHT'), T(Q_P1)), (K('OK'), T(POP)), (KS('DOWN', 'OK'), T(H_BUY)), (K('OK'), T(POP)), (K('OK'), T(POP))]),
 'qty_pop': (3, [(K('OK'), T(H_ROW)), (K('OK'), T(C0)), (KS('DOWN', 'OK'), T(H_ROW + 1)), (K('RIGHT'), T(Q_P1))]),
 'ref_hi': (4, [(K('OK'), T(H_ROW)), (K('OK'), T(C0)), (KS('DOWN', 'OK'), T(H_ROW + 1)), (K('UP'), T(Q_P10)), (K('LEFT'), T(Q_M1)), (K('OK'), T(POP)), (KS('DOWN', 'OK'), T(H_BUY)), (K('OK'), T(POP)), (K('OK'), T(POP))]),
 'ref_cancel': (3, [(K('OK'), T(H_ROW)), (K('OK'), T(C0)), (KS('DOWN', 'OK'), T(H_ROW + 1)), (K('RIGHT'), T(Q_P1)), (K('CLR'), T(Q_NO)), (KS('DOWN', 'OK'), T(H_BUY)), (K('OK'), T(POP))]),
 'ref_err': (3, [(KS('DOWN', 'DOWN', 'OK'), T(H_BUY)), (K('OK'), T(POP)), (KS('UP', 'OK'), T(H_ROW + 1)), (K('LEFT'), T(Q_M1)), (K('OK'), T(POP)), (KS('DOWN', 'OK'), T(H_BUY)), (K('OK'), T(POP))]),
 'qty_buy': (1, [(KS('RIGHT', 'RIGHT'), T(H_TAB + 2)), (K('OK'), T(H_CELL + 0)), (K('OK'), T(H_CELL + 0)), (K('RIGHT'), T(Q_P1)), (K('UP'), T(Q_P10)), (K('OK'), T(POP)), (K('OK'), T(POP)), (K('OK'), T(POP))]),
 'ref_rate': (3, [(K('OK'), T(H_ROW)), (K('OK'), T(C0)), (KS('DOWN', 'OK'), T(H_ROW + 1)), (K('RIGHT'), T(Q_P1)), (K('RIGHT'), T(Q_P1)), (K('OK'), T(POP))]),
 'ref_hirate': (4, [(K('OK'), T(H_ROW)), (K('OK'), T(C0)), (KS('DOWN', 'OK'), T(H_ROW + 1)), (K('RIGHT'), T(Q_P1)), (K('OK'), T(POP))]),
 'orb_rate': (5, ORB_FLOW[:6]),
 'orb_flow': (5, ORB_FLOW),
 'orb_btn': (5, ORB_BTN),
 'mix_flow': (6, [(K('RIGHT'), T(H_CELL + 1)), (K('OK'), T(H_CELL + 1)), (K('OK'), T(POP)), (K('DOWN'), T(H_CELL + 5)), (K('OK'), T(H_CELL + 5)), (K('OK'), T(POP))]),
 'mixok_btn': (6, [(KS('RIGHT', 'RIGHT'), T(H_CELL + 2)), (K('OK'), T(H_BUY)), (K('OK'), T(POP)), (K('OK'), T(POP)), (KS('DOWN', 'LEFT'), T(H_CELL + 5)), (K('OK'), T(H_CELL + 5)), (K('OK'), T(POP)), (K('OK'), T(POP))]),
 'mix_btn': (6, [(KS('RIGHT', 'RIGHT'), T(H_CELL + 2)), (K('OK'), T(H_BUY)), (K('OK'), T(POP)), (KS('DOWN', 'LEFT'), T(H_CELL + 5)), (K('OK'), T(H_BUY)), (K('OK'), T(POP))]),
 'pick_coll': (7, [(KS('OK', 'DOWN', 'RIGHT', 'RIGHT', 'OK'), KS('OK', 'DOWN', 'RIGHT', 'RIGHT', 'OK')), (K('OK'), T(0xC0)), (K('OK'), T(POP))]),
}
for name, spec in SC.items():
    if only and name not in only: continue
    typ, seq = spec[0], spec[1]; m = spec[2] if len(spec) > 2 else 100000
    c.fork(run(typ, seq, m, name), name)
