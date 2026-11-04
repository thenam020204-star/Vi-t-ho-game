#!/usr/bin/env python3
"""T17c2: kiểm Túi đồ + Trang bị + popup chi tiết (menu mới) so với bản gốc (v0.2-hd) cùng thao tác.
Dùng: check_c2.py W H MODE OUTDIR     MODE = new (dev.so, chạm) | orig (v0.2-hd, phím gốc làm chuẩn dữ liệu)
Boot tới khung 1000 (đã vào game) rồi os.fork() từng kịch bản; ghi OUTDIR/res_<WxH>_<mode>.json; ảnh (mode new) -> OUTDIR/raw_<WxH>/.
Mọi kịch bản chạy CÙNG số khung ở hai mode (chạm = phím gốc tương ứng) để dữ liệu phụ thuộc thời gian cũng so được. check_c2_all.py chạy 3 cỡ x 2 mode và so sánh."""
import sys, os, json
H = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, H)
from qlib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
c = Ctx(W, Hh, MODE, OUT)
NEW = c.NEW; r = c.r; g = c.g; tag = c.tag
RAW = os.path.join(OUT, 'raw_' + tag); os.makedirs(RAW, exist_ok=True); c.out = RAW
tbl = c.u32(c.gv + 5132)
KEY0 = 48; HASH = 35

def add(cat, i, n): c.callx(0xa321c, tbl, cat, i, n, 1)
def A(k, hid, w=3):
    """một thao tác: mode new = chạm vùng hid; orig = phím gốc k"""
    if NEW: c.tap(hid, w)
    else: c.key(k, w)
def snap():
    bag = c.bag(); n = c.s16(bag + 6); items = []
    for i in range(n):
        p = r.call(0x9b5f4, bag, i // 16, i % 16)
        items.append((c.s8(p, 20), c.s8(p, 21), c.b8(bag + 0x188 + i)))
    eq = r.call(0x85450, c.hero); slots = []
    for s in range(8):
        p = r.call(0x9c978, eq, s); slots.append((c.s8(p, 20), c.s8(p, 21)) if p else None)
    h = c.hero
    stats = [c.s16(h + 0x18a + 2 * i) for i in range(16)] + [c.u32(h + 0xf0), c.u32(h + 0x180), c.s16(h + 0x248), c.s16(h + 0x2e6)]
    toggle = r.call(0x84ef0, h) & 255
    belt = [[(r.call(0x84f3c, h, s, b) & 255, r.call(0x84f54, h, s, b) & 255) for s in range(5)] for b in range(2)]
    return {'n': n, 'items': items, 'slots': slots, 'stats': stats, 'toggle': toggle, 'belt': belt, 'money': r.call(0x9b4e8, bag)}
def ptrs():
    t = c.top(); return {'st': c.b8(t, 0xc), 'sub': c.b8(t, 0xd), 'e': c.b8(t, 0xe), 'f': c.b8(t, 0xf), 'c10': c.b8(t, 0x10), 'cls': hex(c.cls())}
def setup_items():
    add(15, 1, 3); add(5, 1, 1); add(11, 1, 2)

def sc_page():
    setup_items(); c.S3(2); c.shot('tui_do')
    A('DOWN', 0x80 + 1); A('DOWN', 0x80 + 2)
    out = {'page': ptrs()}
    A('OK', 0xA0 + 5, 3)
    if not NEW: c.key('DOWN'); c.key('RIGHT')
    out['cell'] = ptrs(); out['snap'] = snap()
    return out
def sc_sort():
    setup_items(); c.S3(2)
    A(KEY0, 0x70, 5)
    return {'p': ptrs(), 'snap': snap()}
def bag_to_cell(k):
    """vào Túi đồ rồi chọn ô k (trang 0): new = chạm ô 2 lần (lần 1 vào sub 1, lần 2 mở menu); orig = OK, phím di chuyển, OK"""
    c.S3(2)
    if NEW:
        c.tap(0xA0 + k); c.tap(0xA0 + k, 6)
    else:
        c.key('OK')
        for _ in range(k // 4): c.key('DOWN')
        for _ in range(k % 4): c.key('RIGHT')
        c.key('OK', 6)
def sc_use():
    setup_items(); bag_to_cell(4); c.shot('tui_do_menu')
    out = {'menu': ptrs()}
    A('OK', 0xA0 + 0, 10)                               # Sử dụng
    r.step(20); out['after'] = ptrs(); out['snap'] = snap()
    return out
def sc_drop(yes):
    def f():
        setup_items(); bag_to_cell(3)
        A('DOWN', 0xA0 + 1, 6)                         # NEW: nút "Vứt bỏ" của menu; ORIG: DOWN rồi OK
        if not NEW: c.key('OK', 6)
        out = {'confirm': ptrs(), 'ptype': c.b8(c.top(), 0xe)}
        c.shot('xac_nhan_vut') if yes else None
        if yes: A('OK', 0xF0, 6)
        else:
            if NEW: c.tap(0xF1, 6)
            else: c.key('RIGHT', 2); c.key('OK', 6)
        r.step(20); out['after'] = ptrs(); out['snap'] = snap()
        return out
    return f
def sc_belt():
    setup_items(); bag_to_cell(3)
    A('OK', 0xA0 + 0, 8)                               # Gán phím tắt -> SubState 2
    out = {'sub2': ptrs()}; c.shot('tui_do_phim_tat')
    if NEW: json.dump({str(k): v for k, v in c.hits().items()}, open(os.path.join(OUT, 'hits_belt_%s.json' % tag), 'w'))
    A(HASH, 0xB5, 5); out['swap'] = ptrs(); out['snap0'] = snap()          # đổi bộ 1 <-> 2 (gốc: phím '#' ở SubState 2 = HERO::ChangeHotKeyToggle)
    if NEW: c.tap(0xB0 + 2)
    else: c.key('RIGHT'); c.key('RIGHT'); c.key('OK', 3)
    r.step(10); out['assign'] = ptrs(); out['snap'] = snap()
    return out
def sc_equip():
    setup_items(); c.S3(3); c.shot('trang_bi')
    if NEW: json.dump({str(k): v for k, v in c.hits().items()}, open(os.path.join(OUT, 'hits_equip_%s.json' % tag), 'w'))
    if NEW: c.tap(0xA0 + 1); c.tap(0xA0 + 1, 6)
    else: c.key('RIGHT'); c.key('OK', 6)
    out = {'menu': ptrs()}; c.shot('trang_bi_menu')
    A('OK', 0xA0 + 0, 8); out['pick'] = ptrs(); c.shot('trang_bi_chon')
    if NEW: json.dump({str(k): v for k, v in c.hits().items()}, open(os.path.join(OUT, 'hits_pick_%s.json' % tag), 'w'))
    if NEW: c.tap(0xC0 + 1); c.tap(0xC0 + 1, 6)
    else: c.key('RIGHT'); c.key('OK', 6)
    out['equipped'] = ptrs(); out['snap'] = snap()
    A('OK', 0xF2, 6); r.step(10); out['closed'] = ptrs(); out['snap2'] = snap()
    return out
def sc_unequip():
    setup_items(); c.S3(3)
    if NEW: c.tap(0xA0 + 2); c.tap(0xA0 + 2, 6)
    else: c.key('RIGHT'); c.key('RIGHT'); c.key('OK', 6)
    A('DOWN', 0xA0 + 1, 6)
    if not NEW: c.key('OK', 6)
    r.step(20); return {'p': ptrs(), 'snap': snap()}
def sc_pick_nav():
    """danh sách lọc: chạm mũi tên/ô == phím trái/phải; ghi vùng chạm"""
    setup_items(); add(5, 2, 1); add(5, 3, 1); add(5, 4, 1); add(5, 5, 1); add(5, 6, 1); add(5, 7, 1)
    c.S3(3)
    if NEW: c.tap(0xA0 + 1); c.tap(0xA0 + 1, 6)
    else: c.key('RIGHT'); c.key('OK', 6)
    A('OK', 0xA0 + 0, 8); out = {'p0': ptrs()}
    A('RIGHT', 0xC6); A('RIGHT', 0xC6); A('RIGHT', 0xC6); A('RIGHT', 0xC6); A('RIGHT', 0xC6)
    out['p1'] = ptrs(); c.shot('trang_bi_chon2')
    A('LEFT', 0xC5); out['p2'] = ptrs()
    return out
def sc_popup10():
    setup_items(); c.S3(2)
    bag = c.bag(); it = r.call(0x9b5f4, bag, 0, 3)
    import subprocess
    pt = [int(l.split()[0], 16) for l in subprocess.run(['llvm-nm', '-C', '--defined-only', os.path.join(os.path.expanduser(os.environ.get('WORK', '~/hl5work')), 'apk/assets/libHeroesLore5.so')], capture_output=True, text=True).stdout.splitlines() if l.endswith(' _pTextMgr')][0]
    txt = r.call(0x13fa94, g.rd32(BASE + pt), 295)
    c.callx(0xe31b8, c.top(), 24, txt, 0, 0, 9, it); r.step(6); c.shot('popup_chi_tiet')
    out = {'p': ptrs()}
    if NEW: json.dump({str(k): v for k, v in c.hits().items()}, open(os.path.join(OUT, 'hits_pop10_%s.json' % tag), 'w'))
    A('OK', 0xF2, 8); r.step(10); out['after'] = ptrs()
    return out
def sc_back():
    c.S3(2)
    A('CLR', 1, 4); out = {'p': ptrs()}
    return out
def sc_tabs():
    out = {}
    for i in range(9):
        pid = os.fork()
        if pid: os.waitpid(pid, 0); continue
        c.S3(2); r.step(2)
        try:
            if NEW: c.tap(0x10 + i)
            else:
                r.call(0xfa894, c.top(), i + 1); r.step(3)
            r.step(10)
            json.dump({'state': c.st()}, open(os.path.join(OUT, 'tab%d_%s_%s.json' % (i, tag, MODE)), 'w'))
        except Exception as e:
            json.dump({'ERR': repr(e)}, open(os.path.join(OUT, 'tab%d_%s_%s.json' % (i, tag, MODE)), 'w'))
        os._exit(0)
    return {'done': 1}
def sc_cycles():
    live0 = len(g.heap.sizes); top0 = g.heap.top
    for k in range(20):
        c.S3(2 if k % 2 == 0 else 3, 5)
        if k % 4 == 0 and NEW: c.tap(0xA0 + 1)
        elif k % 4 == 0: c.key('OK')
        for _ in range(4):
            if c.cls() == 0x15e150: break
            r.key('CLR'); r.step(5)
    r.step(10)
    import hashlib
    return {'live': len(g.heap.sizes) - live0, 'top': g.heap.top - top0, 'img': hashlib.md5(r.shot()).hexdigest(), 'cls': hex(c.cls())}
def sc_hits():
    if not NEW: return {}
    out = {}
    c.S3(2); out['bag'] = {str(k): v for k, v in c.hits().items()}
    json.dump(out['bag'], open(os.path.join(OUT, 'hits_bag_%s.json' % tag), 'w'))
    return out

for name, fn in [('page', sc_page), ('sort', sc_sort), ('use', sc_use), ('drop_yes', sc_drop(True)), ('drop_no', sc_drop(False)), ('belt', sc_belt),
                 ('equip', sc_equip), ('unequip', sc_unequip), ('picknav', sc_pick_nav), ('popup10', sc_popup10), ('back', sc_back), ('tabs', sc_tabs),
                 ('cycles', sc_cycles), ('hits', sc_hits)]:
    c.fork(fn, name)
res = {}
import glob
for fn in glob.glob(os.path.join(RAW, 'sc_*_%s_%s.json' % (tag, MODE))) + glob.glob(os.path.join(OUT, 'tab*_%s_%s.json' % (tag, MODE))):
    res[os.path.basename(fn).split('_%s_' % tag)[0].replace('sc_', '', 1)] = json.load(open(fn))
json.dump(res, open(os.path.join(OUT, 'res_%s_%s.json' % (tag, MODE)), 'w'), indent=1, sort_keys=True)
print('xong', tag, MODE)
