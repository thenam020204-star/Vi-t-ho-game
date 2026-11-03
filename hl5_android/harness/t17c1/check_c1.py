#!/usr/bin/env python3
"""T17c1: kiểm InGameMenu 3x3 + Nhân vật + Hệ thống + popup chung.
Dùng: check_c1.py W H MODE OUTDIR     MODE = new (dev.so, menu mới) | orig (v0.2-hd gốc, làm chuẩn so dữ liệu)
Chạy tới khung 1000 (đã vào game) rồi os.fork() từng kịch bản; ghi OUTDIR/res_<WxH>_<mode>.json; ảnh (mode new) -> OUTDIR/raw_<WxH>/.
check_c1_all.py chạy 3 cỡ x 2 mode và so sánh."""
import sys, os, json, struct, hashlib, glob
H = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, H)
from c1lib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
WK = os.path.expanduser('~/hl5work'); os.makedirs(OUT, exist_ok=True)
NEW = MODE == 'new'
so = WK + ('/t17c1/dev/dev.so' if NEW else '/build_apk/libHeroesLore5.so')
vfs = WK + ('/t17c1/dev/data.vfs.mp3' if NEW else '/build_apk/data.vfs.mp3')
tag = '%dx%d' % (W, Hh)
RAW = os.path.join(OUT, 'raw_' + tag); os.makedirs(RAW, exist_ok=True)
r = Run(so, vfs, W, Hh, 'c1_%s_%s' % (tag, MODE), WK + '/t17c1')
route(r, 1000)
g = r.g
sym = {}
if NEW:
    for l in open(WK + '/t17c1/dev/menu/menu.sym'):
        p = l.split()
        if len(p) == 3: sym[p[2]] = int(p[0], 16)
GVB = 0x16a238
gv = BASE + GVB
hero = g.rd32(gv + 5180)
res = {}

def S3(n, wait=10):
    r.call(0x428bc, 0, 3, n); r.step(wait)
def top(): return r.top()
def st(): return bytes(g.uc.mem_read(top() + 0xc, 1))[0]
def b8(a, o=0): return bytes(g.uc.mem_read(a + o, 1))[0]
def s16(a): return struct.unpack('<h', bytes(g.uc.mem_read(a, 2)))[0]
def cls(): return r.cls()
def hits():
    n = r.rd(sym['mui_nhit']); out = {}
    for i in range(n):
        a = BASE + sym['mui_hits'] + 14 * i
        x0, y0, x1, y1, hid = struct.unpack('<hhhhh', bytes(g.uc.mem_read(a, 10))); kind = b8(a, 10)
        out[hid] = (x0, y0, x1, y1, kind)
    return out
def tap(hid, up=True):
    h = hits()[hid]; x, y = (h[0] + h[2]) // 2, (h[1] + h[3]) // 2
    r.touch(x, y, up); r.step(3)
def shot(name):
    if not NEW: return
    from hl5libc import save_png
    save_png(os.path.join(RAW, name + '.png'), W, Hh, r.shot())
def files():
    out = {}
    for f in sorted(glob.glob(os.path.join(g.a.storage, '**', '*'), recursive=True)):
        if os.path.isfile(f): out[os.path.relpath(f, g.a.storage)] = hashlib.md5(open(f, 'rb').read()).hexdigest()[:12] + ':%d' % os.path.getsize(f)
    return out
CLS_POPUP = None

def run(name, fn):
    pid = os.fork()
    if pid:
        os.waitpid(pid, 0); return
    try:
        out = fn()
    except SystemExit:
        raise
    except Exception as e:
        out = {'ERR': repr(e)}
    json.dump(out, open(os.path.join(OUT, 'sc_%s_%s_%s.json' % (name, tag, MODE)), 'w'))
    os._exit(0)

# ---- chạm / phím vào cùng một đích (mode new: chạm; orig: phím gốc) ----
def sc_menu3():
    S3(0); shot('menu3x3')
    return {'state': st(), 'hits': hits() if NEW else {}}
def sc_tiles(i):
    def f():
        S3(0)
        if NEW: tap(0x40 + i)
        else: g.uc.mem_write(top() + 0xe, bytes([i])); r.key('OK'); r.step(3)
        r.step(5)
        return {'state': st(), 'sub': b8(top(), 0xd), 'cls': cls() != 0, 'popup': hex(cls())}
    return f
def sc_char():
    S3(1); shot('nhan_vat')
    out = {}
    g.uc.mem_write(hero + 0x22d, bytes([10])); r.step(4)
    if NEW: out_hits = hits(); json.dump({str(k): v for k, v in out_hits.items()}, open(os.path.join(OUT, 'hits_char_%s.json' % tag), 'w'))
    g.uc.mem_write(hero + 0x22d, bytes([10]))                           # cấp 10 -> có điểm chỉ số để cộng (điểm tính lại mỗi khung)
    r.step(4)
    def snap(k):
        out[k] = {'base': [s16(hero + 574 + 2 * i) for i in range(4)], 'shown': [s16(hero + 0x2de + 2 * i) for i in range(4)],
                  'pend': [s16(top() + 0x20 + 2 * i) for i in range(4)], 'pts': s16(hero + 732)}
    snap('start')
    for row in (0, 2):
        g.uc.mem_write(hero + 732, struct.pack('<h', 5))                # điểm chỉ số (Calc tính lại mỗi khung): đặt ngay trước thao tác
        if NEW: tap(0x28 + row); g.uc.mem_write(hero + 732, struct.pack('<h', 5)); tap(0x30 + row)
        else: g.uc.mem_write(top() + 0xe, bytes([row])); r.key('OK'); r.step(3)
        snap('plus_row%d' % row)
    shot('nhan_vat_cong_diem')
    if NEW: tap(0x21)
    else: r.key('STAR'); r.step(3)
    snap('after_cancel')
    if NEW: tap(0x20)
    else: r.key('HASH'); r.step(3)
    r.step(3); out['sub_after_hash'] = b8(top(), 0xd); shot('nhan_vat_chi_tiet')
    return out
def sc_char_cls(c):
    def f():
        S3(1); g.uc.mem_write(hero + 0x22c, bytes([c])); r.step(3)
        if NEW: tap(0x20)
        else: r.key('HASH'); r.step(3)
        r.step(3); shot('nhan_vat_chi_tiet_lop%d' % c)
        return {'sub': b8(top(), 0xd)}
    return f
def sc_sys():
    S3(9); shot('he_thong')
    if NEW: json.dump({str(k): v for k, v in hits().items()}, open(os.path.join(OUT, 'hits_sys_%s.json' % tag), 'w'))
    out = {'opt0': [b8(gv + 132 + i) for i in range(6)]}
    # Cài đặt
    if NEW: tap(0x41)
    else: g.uc.mem_write(top() + 0xe, bytes([1])); r.key('OK'); r.step(3)
    r.step(3); out['sub_opt'] = b8(top(), 0xd); shot('he_thong_cai_dat')
    if NEW: json.dump({str(k): v for k, v in hits().items()}, open(os.path.join(OUT, 'hits_opt_%s.json' % tag), 'w'))
    for row in (1, 3):
        if NEW: tap(0x40 + row); tap(0x60 + row)                       # ô hàng + nút ▶
        else:
            g.uc.mem_write(top() + 0xf, bytes([row, row])); r.key('RIGHT'); r.step(3)
    out['opt1'] = [b8(gv + 132 + i) for i in range(6)]
    if NEW: tap(0x50 + 1)                                              # ◀ hàng 1
    else: g.uc.mem_write(top() + 0xf, bytes([1, 1])); r.key('LEFT'); r.step(3)
    out['opt2'] = [b8(gv + 132 + i) for i in range(6)]
    # về menu rồi Trợ giúp
    if NEW: tap(1)
    else: r.key('CLR'); r.step(3)
    r.step(3); out['sub_back'] = b8(top(), 0xd)
    if NEW: tap(0x42)
    else: g.uc.mem_write(top() + 0xe, bytes([2])); r.key('OK'); r.step(3)
    r.step(3); out['sub_help'] = b8(top(), 0xd)
    if NEW: tap(0x70 + 3)
    else:
        for _ in range(3): r.key('DOWN'); r.step(2)
    r.step(3); out['help_cur'] = b8(top(), 0x10); shot('he_thong_tro_giup')
    if NEW: json.dump({str(k): v for k, v in hits().items()}, open(os.path.join(OUT, 'hits_help_%s.json' % tag), 'w'))
    return out
def sc_save(yes):
    def f():
        S3(9); r.step(3)
        before = files()
        if NEW: tap(0x40)
        else: g.uc.mem_write(top() + 0xe, bytes([0])); r.key('OK'); r.step(3)
        r.step(5)
        out = {'popup_cls': hex(cls()), 'type': b8(top(), 0xe), 'nbtn': b8(top(), 0x10)}
        shot('popup_luu' if yes else 'popup_luu_2')
        if NEW and yes: json.dump({str(k): v for k, v in hits().items()}, open(os.path.join(OUT, 'hits_popup_%s.json' % tag), 'w'))
        if yes:
            if NEW: tap(0xF0)
            else: r.key('OK'); r.step(3)
        else:
            if NEW: tap(0xF1)
            else: r.key('RIGHT'); r.step(2); r.key('OK'); r.step(3)
        r.step(60)
        out['top_after'] = hex(cls()); out['res'] = b8(top(), 0x19) if cls() else None
        out['files_new'] = {k: v for k, v in files().items() if before.get(k) != v}
        return out
    return f
def sc_tabs():
    out = {}
    for i in range(9):
        pid = os.fork()
        if pid: os.waitpid(pid, 0); continue
        S3(1); r.step(2)
        try:
            if NEW: tap(0x10 + i)
            else:
                if i == 8: r.key('1')
                else: r.call(0xfa894, top(), i + 1); r.step(3)
            r.step(10)
            json.dump({'state': st()}, open(os.path.join(OUT, 'tab%d_%s_%s.json' % (i, tag, MODE)), 'w'))
            shot('tab%d' % i)
        except Exception as e:
            json.dump({'ERR': repr(e)}, open(os.path.join(OUT, 'tab%d_%s_%s.json' % (i, tag, MODE)), 'w'))
        os._exit(0)
    return {'done': 1}
def sc_cycles():
    live0 = len(g.heap.sizes); top0 = g.heap.top
    for k in range(20):
        r.call(0x428bc, 0, 3, 0); r.step(5)
        if k % 2:
            if NEW: tap(0x40 + 1)
            else: g.uc.mem_write(top() + 0xe, bytes([1])); r.key('OK'); r.step(3)
            r.step(5)
        for _ in range(4):
            if cls() == 0x15e150: break
            r.key('CLR'); r.step(5)
    r.step(10)
    img = hashlib.md5(r.shot()).hexdigest()
    return {'live': len(g.heap.sizes) - live0, 'top': g.heap.top - top0, 'img': img, 'cls': hex(cls())}

for i in range(9): run('tile%d' % i, sc_tiles(i))
run('menu3', sc_menu3); run('char', sc_char)
for c in (1, 2): run('charcls%d' % c, sc_char_cls(c))
run('sys', sc_sys); run('save_yes', sc_save(True)); run('save_no', sc_save(False)); run('tabs', sc_tabs); run('cycles', sc_cycles)
# gom
for fn in glob.glob(os.path.join(OUT, 'sc_*_%s_%s.json' % (tag, MODE))) + glob.glob(os.path.join(OUT, 'tab*_%s_%s.json' % (tag, MODE))):
    res[os.path.basename(fn).split('_%s_' % tag)[0].replace('sc_', '', 1)] = json.load(open(fn))
json.dump(res, open(os.path.join(OUT, 'res_%s_%s.json' % (tag, MODE)), 'w'), indent=1, sort_keys=True)
print('xong', tag, MODE)
