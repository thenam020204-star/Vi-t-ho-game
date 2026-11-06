#!/usr/bin/env python3
"""L6c: kiểm các menu con mới (chạm) vs gốc v0.2-hd (phím / chạm gốc). Dùng: check_l6c.py W H MODE OUTDIR [PART]  (MODE new|orig|off; 'off' = dev.so + cờ MUI_F_DISABLE).
PART: quest (Nhiệm vụ sub 4/5/6) | skill | spirit | data. Kết quả OUTDIR/raw_<WxH>/sc_<tên>_<WxH>_<mode>.json"""
import sys, os, json
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l6'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'l3'))
from qlib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
PART = sys.argv[5] if len(sys.argv) > 5 else 'quest'
c = Ctx(W, Hh, 'new' if MODE == 'off' else MODE, OUT, boot_to=10 if PART == 'data' else 1000); NEW = MODE == 'new'; r = c.r; g = c.g; tag = c.tag
c.mode = MODE
RAW = os.path.join(OUT, 'raw_' + tag); os.makedirs(RAW, exist_ok=True); c.out = RAW
if MODE == 'off': g.uc.mem_write(BASE + c.sym['mui_flags'], (1).to_bytes(4, 'little'))
HW, HH = W // 2, Hh // 2
def fl():
    t = c.top(); return {'cls': hex(c.cls()), 'f': [c.s8(t, o) for o in range(0xc, 0x15)], 'm': [c.s8(t, o) for o in (0x2e, 0x41, 0x6c)], 'pan': [c.s16(t + o) for o in (0x34, 0x36, 0x38, 0x3a)], 'sk': [c.s16(t + 0x2a), c.s16(t + 0x2c)]}
def sc(name, fn):
    pid = os.fork()
    if pid: os.waitpid(pid, 0); return
    try: d = fn()
    except Exception as e:
        import traceback; d = {'ERR': repr(e), 'tb': traceback.format_exc()}
    json.dump(d, open(os.path.join(RAW, 'sc_%s_%s_%s.json' % (name, tag, MODE)), 'w'), default=str); os._exit(0)
def keys(*ks):
    for k in ks: c.key(k, 4)
def tap(hid, w=4):
    if not NEW: raise RuntimeError('tap in orig')
    c.tap(hid, w)
def shot(nm): c.shot(nm)
# ---------------- Nhiệm vụ ----------------
def seed():
    qm = c.u32(c.gv + 5580)
    for i in range(9): c.r.call(0xcdcc4, qm, i, 1)
    for i in range(9): c.r.call(0xd0a68, qm, i)
    c.r.call(0xcdcc4, qm, 0, 5); c.r.call(0xcdcc4, qm, 1, 5)
SEEDED = [0]
def qenter():
    if not SEEDED[0]: seed(); SEEDED[0] = 1
    c.S3(8, 6)
def to_detail(row=2):
    qenter()
    if NEW: tap(0x20 + row)
    else: keys(*(['DOWN'] * (row + 1)))
    if NEW: tap(0x20 + row)
    else: keys('OK')
def to_reward():
    to_detail()
    if NEW: tap(0x50)
    else: keys('OK')
def q_reward():
    out = {}; to_detail(); out['detail'] = fl()
    if NEW: tap(0x50)
    else: keys('OK')
    out['reward'] = fl(); shot('q_reward')
    for i in range(3):
        if NEW: tap(0xE0, 1) if False else None
    if NEW: tap(0x50)
    else: keys('OK')
    out['back_desc'] = fl()          # nút 0 ở sub 4 = "Xem nội dung" -> sub 3
    if NEW: tap(0x50)
    else: keys('OK')
    out['reward2'] = fl()
    if NEW: tap(1)
    else: keys('CLR')
    out['back1'] = fl()
    return out
def q_scroll():
    out = {}; to_reward()
    t = c.top(); out['in'] = fl()
    if NEW:
        h = c.hits(); hx = [k for k in h if 0xE0 <= k < 0xE4]
        out['hits_e0'] = hx
    return out
def q_back_btn():
    out = {}; to_reward()
    if NEW: tap(1)
    else: keys('CLR')
    out['b1'] = fl()
    return out
def to_map():
    to_reward()
    if NEW: tap(0x51)
    else: keys('RIGHT', 'OK')
def q_map():
    out = {}; to_map(); out['map'] = fl(); shot('q_map0')
    return out
def q_map_star():
    out = {}; to_map(); out['in'] = fl()
    if NEW: tap(0xC0)
    else: c.touch_xy(10, Hh - 5, 4)
    out['star'] = fl(); out['flag'] = c.s8(c.u32(c.gv + 5200), 4224); return out
def q_map_zone():
    out = {}; to_map(); keys('OK'); out['zone'] = fl(); shot('q_map1')
    if NEW: tap(0xC1)
    else: c.touch_xy(W - 10, Hh - 5, 4)
    out['hash'] = fl(); return out
def q_map_zone_pan():
    out = {}; to_map(); keys('OK'); out['zone'] = fl()
    c.touch_xy(W // 2, 3, 4); out['up'] = fl()
    c.touch_xy(W // 2, Hh - 3, 4); out['down'] = fl(); return out
def q_map_back():
    out = {}; to_map()
    if NEW: tap(0xC2)
    else: c.touch_xy(HW - 88 + 3, HH - 102 + 3, 4)
    out['back'] = fl(); return out
def q_map_pt():
    out = {}; to_map(); out['in'] = fl()
    t = c.top(); px = []
    for i in range(8):
        a = t + 780 + 8 * i; px.append((c.s16(a), c.s16(a + 2)))
    out['pts'] = px; cx, cy = c.s16(t + 52), c.s16(t + 54); out['cam'] = (cx, cy)
    for i in (2, 5):
        x = px[i][0] - cx; y = px[i][1] - cy
        c.touch_xy(x, y, 4); out['pt%d' % i] = fl()
        c.touch_xy(x, y, 4); out['pt%dok' % i] = fl()
    return out
def q_map_back_key():
    out = {}; to_map(); keys('CLR'); out['b'] = fl(); return out
if PART == 'quest':
    S = [('qrw', q_reward), ('qbk', q_back_btn), ('qmap', q_map), ('qstar', q_map_star), ('qzone', q_map_zone), ('qpan', q_map_zone_pan), ('qmback', q_map_back), ('qpt', q_map_pt), ('qmbk', q_map_back_key)]
    for nm, fn in S: sc(nm, fn)

# ---------------- Kỹ năng sub 8 / 10 (vào bằng SetSubState: đường phím thật tới sub 8 qua tab 5 chưa lần ra; cả orig lẫn new dùng chung cách vào) ----------------
def sk_enter(sub=8):
    c.S3(4, 8); t = c.top(); c.r.call(0xe2eb8, t, sub); c.step(4)
def popi():
    t = c.top(); return [hex(c.cls()), c.s8(t, 0xe), c.s8(t, 0x10)]
def sk8_shot():
    sk_enter(8); shot('sk8'); out = {'in': fl()}; return out
def sk8_slot(i):
    def f():
        sk_enter(8); out = {'in': fl()}
        if i: keys('RIGHT')
        if NEW: tap(0xD0 + i)
        else: keys('OK')
        out['after'] = fl(); shot('sk10_%d' % i); return out
    return f
def sk8_start():
    sk_enter(8); out = {}
    if NEW: tap(0xD2)
    else: keys('DOWN', 'OK')
    out['after'] = fl(); out['pop'] = popi(); return out
def sk8_clr():
    sk_enter(8); t = c.top(); c.wr(t + 0x2a, [0, 0, 1, 0]); c.step(2); out = {'in': fl()}; shot('sk8_items')
    if NEW: tap(0xD3)
    else: keys('0')
    out['after'] = fl(); return out
def sk8_back():
    sk_enter(8)
    if NEW: tap(1)
    else: keys('CLR')
    return {'b': fl()}
def sk10_back():
    sk_enter(10)
    if NEW: tap(1)
    else: keys('CLR')
    return {'b': fl()}
def sk10_cancel():
    sk_enter(10); out = {'in': fl()}; shot('sk10')
    if NEW: tap(0xC9)
    else: keys('CLR')
    out['b'] = fl(); return out
if PART == 'skill':
    for nm, fn in [('s8', sk8_shot), ('s8a', sk8_slot(0)), ('s8b', sk8_slot(1)), ('s8s', sk8_start), ('s8c', sk8_clr), ('s8k', sk8_back), ('s10k', sk10_back), ('s10c', sk10_cancel)]: sc(nm, fn)
