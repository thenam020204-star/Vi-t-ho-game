#!/usr/bin/env python3
"""L6c: kiểm các menu con mới (chạm) vs gốc v0.2-hd (phím / chạm gốc). Dùng: check_l6c.py W H MODE OUTDIR [PART]  (MODE new|orig|off; 'off' = dev.so + cờ MUI_F_DISABLE).
PART: quest (Nhiệm vụ sub 4/5/6) | skill (sub 8/10) | skill2 (sub 7, 9) | spirit | data. Kết quả OUTDIR/raw_<WxH>/sc_<tên>_<WxH>_<mode>.json"""
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
    if os.environ.get('ONLY') and name not in os.environ['ONLY'].split(','): return
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

# ---------------- Kỹ năng sub 7 (quản lý ô nhanh) và sub 9 (Chế biến) ----------------
def belt():
    h = c.hero; out = []
    for bank in (0, 1):
        for slot in range(5): out.append((c.r.call(0x84f3c, h, slot, bank) & 255, c.r.call(0x84f54, h, slot, bank) & 255))
    return out + [c.r.call(0x84ef0, h) & 255]
def sk7_enter():
    c.S3(4, 8); t = c.top(); h = c.hero
    for slot, bank, ty, ix in ((1, 0, 0, 0), (2, 0, 0, 1), (1, 1, 0, 2)): c.callx(0x84f1c, h, slot, bank, ty, ix)
    c.r.call(0xe2eb8, t, 7); c.wr(t + 0xe, [0]); c.wr(t + 0xf, [0]); c.wr(t + 0x10, [0]); c.wr(t + 0x11, [0]); c.step(4)
def f7(extra=None):
    d = fl(); d['belt'] = belt(); d['pop'] = popi()
    return d
def sk7_shot():
    sk7_enter(); shot('sk7'); return {'in': f7()}
def sk7_sel():                       # chạm ô 2 từ con trỏ 0: chỉ đổi con trỏ (như TouchSkillMenu)
    sk7_enter()
    if NEW: tap(0xB2)
    else: keys('RIGHT', 'RIGHT')
    return {'a': f7()}
def sk7_clear():                     # chọn ô 1 rồi chạm lần 2 = OK gốc (xoá ô)
    sk7_enter(); out = {}
    if NEW: tap(0xB1)
    else: keys('RIGHT')
    out['sel'] = f7()
    if NEW: tap(0xB1)
    else: keys('OK')
    out['ok'] = f7(); return out
def sk7_clrbtn():                    # nút "Bỏ chọn" = OK gốc
    sk7_enter()
    if NEW: tap(0xB1)
    else: keys('RIGHT')
    if NEW: tap(0xC8)
    else: keys('OK')
    return {'a': f7()}
def sk7_swap():
    sk7_enter()
    if NEW: tap(0xB5)
    else: keys(35)
    return {'a': f7()}
def sk7_back():
    sk7_enter()
    if NEW: tap(0xC9)
    else: keys('CLR')
    return {'a': f7()}
def sk7_wrap():                      # LEFT từ ô 0 vòng sang ô 4 (phím gốc; mới: chạm ô 4 = đổi con trỏ)
    sk7_enter()
    if NEW: tap(0xB4)
    else: keys('LEFT')
    return {'a': f7()}
if PART == 'skill2':
    for nm, fn in [('s7', sk7_shot), ('s7s', sk7_sel), ('s7c', sk7_clear), ('s7b', sk7_clrbtn), ('s7w', sk7_swap), ('s7k', sk7_back), ('s7l', sk7_wrap)]: sc(nm, fn)

def sk9_enter(cur=0):
    c.S3(4, 8); t = c.top(); arr = c.u32(c.hero + 0x308)
    for i in range(0, 116, 2): c.wr(arr + i, [1])
    c.r.call(0xe2eb8, t, 9); c.wr(t + 0x11, [cur & 3]); c.wr(t + 0x12, [cur]); c.step(4)
def cellxy(k): return (HW - 50 + (k % 4) * 30, HH - 45 + (k // 4) * 30)
def f9():
    d = fl(); d['pop'] = popi(); d['g19'] = c.b8(c.gv, 6464 + 19); return d
def sk9_shot():
    sk9_enter(6); shot('sk9'); return {'in': f9()}
def sk9_cell():
    sk9_enter(0); out = {}
    for k in (5, 5, 2):
        if NEW: tap(0xD8 + k)
        else: c.touch_xy(*cellxy(k), 4)
        out['k%d' % k] = f9()
    return out
def sk9_ok():
    sk9_enter(0)
    if NEW: tap(0xEA)
    else: keys('OK')
    return {'a': f9()}
def sk9_up():
    sk9_enter(1); out = {}
    for i in range(3):
        if NEW: tap(0xE8)
        else: c.touch_xy(HW + 78, HH - 49, 4)
        out['u%d' % i] = f9()
    return out
def sk9_dn():
    sk9_enter(5); out = {}
    for i in range(3):
        if NEW: tap(0xE9)
        else: c.touch_xy(HW + 78, HH - 7, 4)
        out['d%d' % i] = f9()
    return out
def sk9_wrap():
    sk9_enter(113); out = {}                    # gần cuối: ▼ vòng về đầu; ▲ từ đầu vòng về cuối
    for i in range(3):
        if NEW: tap(0xE9)
        else: c.touch_xy(HW + 78, HH - 7, 4)
        out['d%d' % i] = f9()
    for i in range(2):
        if NEW: tap(0xE8)
        else: c.touch_xy(HW + 78, HH - 49, 4)
        out['u%d' % i] = f9()
    return out
def sk9_back():
    sk9_enter(0)
    if NEW: tap(1)
    else: keys('CLR')
    return {'a': f9()}
if PART == 'skill2':
    for nm, fn in [('s9', sk9_shot), ('s9c', sk9_cell), ('s9o', sk9_ok), ('s9u', sk9_up), ('s9d', sk9_dn), ('s9w', sk9_wrap), ('s9k', sk9_back)]: sc(nm, fn)

# ---------------- Tinh linh sub 4 ----------------
def sp_ptr(): return c.r.call(0x853dc, c.hero)
def sp4_enter(cur=0):
    c.S3(7, 8); t = c.top(); p = sp_ptr()
    for i in range(0, 16, 2): c.wr(p + 22 + i, [1])
    c.r.call(0xe2eb8, t, 4); c.wr(t + 0xf, [1]); c.wr(t + 0x10, [cur]); c.step(4)
def f4():
    d = fl(); p = sp_ptr(); d['sp'] = list(bytes(c.g.uc.mem_read(p, 6))); d['pop'] = popi(); d['g19'] = c.b8(c.gv, 6464 + 19); return d
def sp4_shot():
    sp4_enter(2); shot('sp4'); return {'in': f4()}
def sp4_cell():
    sp4_enter(0); out = {}
    for k in (5, 5, 2):
        if NEW: tap(0xB0 + k)
        else: c.touch_xy(HW - 50 + (k % 4) * 30, HH - 45 + (k // 4) * 30, 4)
        out['k%d' % k] = f4()
    return out
def sp4_ok():
    sp4_enter(2)
    if NEW: tap(0xC4)
    else: keys('OK')
    return {'a': f4()}
def sp4_ok_unlearned():
    sp4_enter(1)
    if NEW: tap(0xC4)
    else: keys('OK')
    return {'a': f4()}
def sp4_back():
    sp4_enter(0)
    if NEW: tap(1)
    else: keys('CLR')
    return {'a': f4()}
if PART == 'spirit':
    for nm, fn in [('p4', sp4_shot), ('p4c', sp4_cell), ('p4o', sp4_ok), ('p4u', sp4_ok_unlearned), ('p4k', sp4_back)]: sc(nm, fn)

# ---------------- Quản lý dữ liệu (MainMenu state 9 sub 1) ----------------
def kseq(ks):
    for k in ks: c.key(k, 3)
def d_enter():
    if NEW: c.tap(0x10 + [0, 1, 4, 5, 6, 8].index(4), 5)
    else: kseq(['DOWN', 'RIGHT', 'OK']); c.step(2)
    c.wr(c.gv + 141, [1]); t = c.top()
    if NEW:
        while c.s8(t, 0xf) < 8: c.tap(0x91, 2)
        c.tap(0x50 + c.s8(t, 0xe), 4)
    else:
        for _ in range(8): c.key('DOWN', 2)
        c.key('OK', 4)
def fd():
    t = c.top(); d = {'cls': hex(c.cls()), 'f': [c.s8(t, o) for o in range(0xc, 0x14)]}
    if c.cls() == 0x15e5b0: res = c.u32(t + 0x14); d['pop'] = {'t': c.s8(t, 0xe), 'nb': c.s8(t, 0x10), 'res': c.s8(res) if res else None}
    return d
def d_shot(): d_enter(); shot('data'); return {'in': fd()}
def d_ok():
    d_enter(); out = {'in': fd()}
    if NEW: tap(0xD0)
    else: c.touch_xy(HW, HH + 1, 4)
    out['a'] = fd(); return out
def d_back():
    d_enter()
    if NEW: tap(1)
    else: keys('CLR')
    return {'a': fd()}
if PART == 'data':
    c.step(700); c.key('OK', 10)
    for nm, fn in [('d', d_shot), ('dok', d_ok), ('dk', d_back)]: sc(nm, fn)
