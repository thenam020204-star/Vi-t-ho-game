#!/usr/bin/env python3
"""T17c3b: kiểm Đặc tính (InGameMenu tab 5, menu mới) so với bản gốc (v0.2-hd) cùng thao tác.
Dùng: check_c3b.py W H MODE OUTDIR [kịch_bản,...]     MODE = new (dev.so, chạm) | orig (v0.2-hd, phím gốc làm chuẩn dữ liệu)
Điểm/điểm-nhánh (HERO+754/+756..) bị game tính lại MỖI khung bằng Formula::calc (0x93e68/0x93e90/0x93ec0 'strh r0,[r4,r3]')
-> harness chèn hook mã đặt r0 = giá trị thử (cả hai mode như nhau)."""
import sys, os, json, struct
H = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, H)
from qlib3 import *
from unicorn import UC_HOOK_CODE
from unicorn.arm_const import UC_ARM_REG_R0
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
c = Ctx(W, Hh, MODE, OUT)
NEW = c.NEW; r = c.r; g = c.g; tag = c.tag; h = c.hero
RAW = os.path.join(OUT, 'raw_' + tag); os.makedirs(RAW, exist_ok=True); c.out = RAW
PTS = {'p': 7, 'b0': 6, 'b1': 0}
def hook(uc, addr, size, ud):
    uc.reg_write(UC_ARM_REG_R0, PTS[ud] & 0xffff)
for off, k in ((0x93e68, 'p'), (0x93e90, 'b0'), (0x93ec0, 'b1')):
    g.uc.hook_add(UC_HOOK_CODE, hook, k, BASE + off, BASE + off)
g.uc.ctl_flush_tb()

def A(k, hid, w=3):
    if NEW: c.tap(hid, w)
    else: c.key(k, w)
def ptrs():
    t = c.top()
    return {'st': c.b8(t, 0xc), 'sub': c.b8(t, 0xd), 'e': c.b8(t, 0xe), 'f': c.b8(t, 0xf), 'ab': c.b8(t, 0x10), 'c11': c.b8(t, 0x11), 'cls': hex(c.cls())}
def snap():
    return {'pts': c.s16(h + 754), 'inv': [c.s16(h + 756 + 2 * i) for i in range(3)], 'lv': [c.s8(h, 588 + k) for k in range(10, 43)], 'f': c.files()}
def setup(levels=((10, 2), (11, 3), (12, 1))):
    for k, lv in levels: c.wr(h + 588 + k, bytes([lv]))
    c.S3(5, 8)
def drag(hid, dy, w=3):
    x0, y0, x1, y1 = c.hits()[hid][:4]; x = (x0 + x1) // 2; y = (y0 + y1) // 2
    r.touch(x, y, up=False); r.step(1)
    n = 4
    for i in range(1, n + 1): r.move(x, y + dy * i // n); r.step(1)
    r.g.call(r.touchf, r.g.env, 0, 20, x, y + dy, 0); r.step(w)
def top_of(p): return (p['ab'] - p['f']) // 3

def sc_tabs():
    setup(); out = {'p0': ptrs()}
    A('RIGHT', 0x31); out['p1'] = ptrs()
    A('RIGHT', 0x32); out['p2'] = ptrs()
    A('LEFT', 0x31); out['p3'] = ptrs()
    c.shot('dac_tinh_nhanh')
    return out
def sc_enter():
    """vào lưới ở ô (1,1): orig = XUỐNG, XUỐNG, PHẢI; new = chạm ô (1,1) từ hàng tab"""
    setup()
    if NEW: c.tap(0xA0 + 4)
    else: c.key('DOWN'); c.key('DOWN')              # XUỐNG đầu tiên vào lưới ở kỹ năng đầu hàng 0 (nhánh 0: ô 1), XUỐNG kế = ô 4
    if NEW: json.dump({str(k): v for k, v in c.hits().items()}, open(os.path.join(OUT, 'hits_sel_%s.json' % tag), 'w'))
    return {'p': ptrs()}
def sc_deep():
    setup()
    if NEW: c.tap(0xA0 + 9)
    else:
        c.key('DOWN')
        for _ in range(3): c.key('DOWN')
        c.key('LEFT')                                # ô 10 -> 9 (vòng trong hàng)
    out = {'p': ptrs()}
    if NEW: c.tap(0xA0 + 11)
    else: c.key('RIGHT'); c.key('RIGHT')
    out['p2'] = ptrs()
    return out
def sc_scroll():
    """cuộn: new = kéo dọc vùng lưới; orig = XUỐNG tới hàng 4 (cửa sổ cuộn). So hàng đầu cửa sổ + tính hợp lệ (kéo giữ ô, phím đẩy ô: không so con trỏ)."""
    setup(); out = {}
    if NEW:
        c.tap(0xA0 + 4)
        hh = c.hits()[0x6F]
        drag(0x6F, -((hh[3] - hh[1]) // 3))
        out['p'] = ptrs(); c.shot('dac_tinh_cuon')
        drag(0x6F, -400); out['p2'] = ptrs()
        drag(0x6F, 400); out['p3'] = ptrs()
        for k in ('p', 'p2', 'p3'): out[k]['top'] = top_of(out[k])
    else:
        c.key('DOWN')
        for _ in range(4): c.key('DOWN')
        out['p'] = ptrs(); out['p']['top'] = top_of(out['p'])
    return out
def sc_invest(btn):
    def f():
        setup()
        if NEW:
            c.tap(0xA0 + 1)
            if btn: c.tap(0xC8, 8)
            else: c.tap(0xA0 + 1, 8)
        else:
            c.key('DOWN'); c.key('OK', 8)
        out = {'p': ptrs(), 'pop': c.b8(c.top(), 0xe) if c.cls() != 0x15e290 else -1}
        if not btn: c.shot('dac_tinh_xac_nhan')
        if NEW: out['hits_pop'] = sorted(c.hits().keys())
        out['snap0'] = snap()
        if c.cls() != 0x15e290: A('OK', 0xA0, 8)          # popup loại 7: nút 0 = xác nhận (con trỏ gốc mặc định 0)
        r.step(20)
        out['after'] = ptrs(); out['snap'] = snap()
        return out
    return f
def sc_invest_fail():
    setup()                                            # kỹ năng 15 (ô 8: hàng 2, cột 2): cần tiền đề 11 (cấp>0) và điểm nhánh 8 (b0=6: thiếu)
    if NEW: c.tap(0xA0 + 8); c.tap(0xA0 + 8, 8)
    else:
        c.key('DOWN'); c.key('DOWN'); c.key('DOWN'); c.key('RIGHT'); c.key('OK', 8)
    out = {'p': ptrs(), 'pop': c.b8(c.top(), 0xe) if c.cls() != 0x15e290 else -1, 'snap0': snap()}
    if c.cls() != 0x15e290: A('OK', 0xA0, 8)
    r.step(20); out['after'] = ptrs(); out['snap'] = snap()
    return out
def sc_tabs9():
    for i in range(9):
        pid = os.fork()
        if pid: os.waitpid(pid, 0); continue
        try:
            setup(); r.step(2)
            if NEW: c.tap(0x10 + i)
            else: r.call(0xfa894, c.top(), i + 1); r.step(3)
            r.step(10)
            json.dump({'state': c.st()}, open(os.path.join(OUT, 'tab%d_%s_%s.json' % (i, tag, MODE)), 'w'))
        except Exception as e:
            json.dump({'ERR': repr(e)}, open(os.path.join(OUT, 'tab%d_%s_%s.json' % (i, tag, MODE)), 'w'))
        os._exit(0)
    return {'done': 1}
def sc_back():
    setup(); A('CLR', 1, 4); return {'p': ptrs()}
def sc_view():
    """ảnh nghiệm thu (mode new)"""
    setup(); c.shot('dac_tinh_0')
    if NEW: c.tap(0xA0 + 2); c.shot('dac_tinh_chon')
    return {}
def sc_cycles():
    live0 = len(g.heap.sizes); top0 = g.heap.top
    for k in range(20):
        c.S3(5, 5)
        if NEW: c.tap(0xA0 + 1)
        else: c.key('DOWN'); c.key('RIGHT')
        for _ in range(4):
            if c.cls() == 0x15e150: break
            r.key('CLR'); r.step(5)
    r.step(10)
    import hashlib
    return {'live': len(g.heap.sizes) - live0, 'top': g.heap.top - top0, 'img': hashlib.md5(r.shot()).hexdigest(), 'cls': hex(c.cls())}

only = set(sys.argv[5].split(',')) if len(sys.argv) > 5 else None
for name, fn in [('tabs', sc_tabs), ('enter', sc_enter), ('deep', sc_deep), ('scroll', sc_scroll), ('inv_node', sc_invest(False)), ('inv_btn', sc_invest(True)),
                 ('inv_fail', sc_invest_fail), ('tabs9', sc_tabs9), ('back', sc_back), ('view', sc_view), ('cycles', sc_cycles)]:
    if only and name not in only: continue
    c.fork(fn, name)
res = {}
import glob
for fn in glob.glob(os.path.join(RAW, 'sc_*_%s_%s.json' % (tag, MODE))) + glob.glob(os.path.join(OUT, 'tab[0-9]_%s_%s.json' % (tag, MODE))):
    res[os.path.basename(fn).split('_%s_' % tag)[0].replace('sc_', '', 1)] = json.load(open(fn))
json.dump(res, open(os.path.join(OUT, 'res_%s_%s.json' % (tag, MODE)), 'w'), indent=1, sort_keys=True)
print('xong', tag, MODE)
