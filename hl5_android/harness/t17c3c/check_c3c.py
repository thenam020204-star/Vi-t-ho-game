#!/usr/bin/env python3
"""T17c3c: kiểm Kỹ năng (menu mới) so với bản gốc (v0.2-hd) cùng thao tác. Dùng: check_c3c.py W H MODE OUTDIR   MODE = new (dev.so, chạm) | orig (phím gốc làm chuẩn).
Mỗi kịch bản os.fork() từ khung ~1000; kết quả OUTDIR/res_<WxH>_<mode>.json; vùng chạm (mode new) -> OUTDIR/hits_*_<WxH>.json; ảnh (mode new) -> OUTDIR/raw_<WxH>/.
WORK = thư mục làm việc (mặc định ~/hl5work_c3c): cần $WORK/t17c2/dev (harness/t17c2/build_dev.sh)."""
import sys, os, json
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_c3c'))
H = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, os.path.join(H, '..', 't17c2'))
from qlib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
c = Ctx(W, Hh, MODE, OUT)
NEW = c.NEW; r = c.r; g = c.g; tag = c.tag
RAW = os.path.join(OUT, 'raw_' + tag); os.makedirs(RAW, exist_ok=True); c.out = RAW
SUB, CELL, ASSIGN, BELT, SWAP, CANCEL = 0x40, 0xA0, 0x72, 0xB0, 0xB5, 0xC9

def ptrs():
    t = c.top()
    return {'cls': hex(c.cls()), 'st': c.b8(t, 0xc), 'f': [c.s8(t, o) for o in range(0xd, 0x1d)], 'x2a': c.s8(t, 0x2a), 'x2c': c.s8(t, 0x2c),
            'gv': [c.b8(c.gv, 6483)]}
def snap():
    h = c.hero
    belt = [[(r.call(0x84f3c, h, s, b) & 255, r.call(0x84f54, h, s, b) & 255) for s in range(5)] for b in range(2)]
    return {'toggle': r.call(0x84ef0, h) & 255, 'belt': belt, 'lv': [c.s8(h, 588 + i) for i in range(43)], 'cnt': c.s8(h, 8128)}
def dump_hits(name):
    if NEW: json.dump({str(k): v for k, v in c.hits().items()}, open(os.path.join(OUT, 'hits_%s_%s.json' % (name, tag)), 'w'))
def enter(tab=0):
    """vào Kỹ năng + chọn tab con `tab` + vào lưới (orig: phím; new: chạm tab con)"""
    c.S3(4)
    if NEW: c.tap(SUB + tab, 4)
    else:
        for _ in range(tab): c.key('RIGHT', 3)
        c.key('DOWN', 4)

def sc_init():
    c.S3(4); c.shot('ky_nang0'); dump_hits('sub0')
    return {'p': ptrs()}
def sc_subtabs():
    out = {}
    for i in range(5):
        pid = os.fork()
        if pid: os.waitpid(pid, 0); continue
        res = {}
        try:
            enter(i); res['grid'] = ptrs()
            if i == 2: c.shot('ky_nang_dac_tinh')
            if i == 0: dump_hits('grid')
        except Exception as e: res['ERR'] = repr(e)
        json.dump(res, open(os.path.join(OUT, 'sub%d_%s_%s.json' % (i, tag, MODE)), 'w')); os._exit(0)
    return {'done': 1}
def sc_switch():           # từ lưới tab 0 (con trỏ ô 5) sang tab con 3 và 4
    enter(0)
    if NEW: c.tap(CELL + 5, 3)
    else: c.key('DOWN', 3); c.key('RIGHT', 3)
    out = {'sel5': ptrs()}
    if NEW: c.tap(SUB + 3, 4)
    else:
        c.S3(4)
        for _ in range(3): c.key('RIGHT', 3)
        c.key('DOWN', 4)
    out['tab3'] = ptrs()
    if NEW: c.tap(SUB + 4, 4)
    else:
        c.S3(4)
        for _ in range(4): c.key('RIGHT', 3)
        c.key('DOWN', 4)
    out['tab4'] = ptrs()
    return out
def sc_select():
    enter(0)
    if NEW: c.tap(CELL + 6, 3)
    else: c.key('DOWN', 3); c.key('RIGHT', 3); c.key('RIGHT', 3)
    out = {'sel6': ptrs()}
    if NEW: c.tap(CELL + 9, 3)
    else: c.key('DOWN', 3); c.key('LEFT', 3)
    out['sel9'] = ptrs()
    if NEW: c.tap(CELL + 15, 3)
    else: c.key('DOWN', 3); [c.key('RIGHT', 3) for _ in range(2)]
    out['sel15'] = ptrs(); c.shot('ky_nang_chon')
    return out
def sc_drag():
    enter(0)
    if NEW:
        h = c.hits()[0x71]; x = h[2] - 2; y0 = h[1] + 6
        r.touch(x, y0, False); r.step(2); r.move(x, y0 - L_pitch()); r.step(2); r.move(x, y0 - L_pitch()); r.step(2)
        r.g.call(r.touchf, r.g.env, 0, 20, x, y0 - L_pitch(), 0); r.step(3)
    else:
        for _ in range(4): c.key('DOWN', 3)
        for _ in range(3): c.key('UP', 3)
    return {'p': ptrs()}
def sc_drag2():            # kéo xuống 1 hàng rồi kéo lại: con trỏ rơi vào hàng 1 == 1 phím DOWN
    enter(0)
    if NEW:
        h = c.hits()[0x71]; x = h[2] - 2; y0 = h[1] + 40
        r.touch(x, y0, False); r.step(2); r.move(x, y0 - L_pitch()); r.step(2); r.move(x, y0); r.step(2)
        r.g.call(r.touchf, r.g.env, 0, 20, x, y0, 0); r.step(3)
        r.touch(x, y0, False); r.step(2); r.move(x, y0 - 3 * L_pitch()); r.step(2); r.move(x, y0 - 40 * L_pitch()); r.step(2)
        r.g.call(r.touchf, r.g.env, 0, 20, x, y0 - 40 * L_pitch(), 0); r.step(3)
        a = ptrs(); r.touch(x, y0, False); r.step(2); r.move(x, y0 + 60 * L_pitch()); r.step(2); r.g.call(r.touchf, r.g.env, 0, 20, x, y0 + 60 * L_pitch(), 0); r.step(3)
        json.dump({'bottom': a, 'top': ptrs()}, open(os.path.join(OUT, 'dragb_%s.json' % tag), 'w'))
        return {'p': ptrs()}
    else:
        c.key('DOWN', 3)
        return {'p': ptrs()}
def L_pitch():
    return {400: 32, 600: 32, 800: 54, 1200: 76}[W]
def sc_assign():
    enter(0)
    # ô 1 (đã học, hiện popup): chọn rồi chạm lần 2 -> popup loại 7; OK mục 0 -> sub 6; chọn ô nhanh 3 (trống) -> gán, quay lại sub 1
    if NEW: c.tap(CELL + 1, 3); c.tap(CELL + 1, 6)
    else: c.key('RIGHT', 3); c.key('OK', 6)
    out = {'popup': ptrs()}
    if NEW: c.tap(0xA0, 8)                               # nút 0 của popup (Gán phím tắt)
    else: c.key('OK', 8)
    out['sub6'] = ptrs(); c.shot('ky_nang_phim_tat'); dump_hits('belt')
    if NEW: c.tap(SWAP, 5)
    else: c.key(35, 5)
    out['swap'] = ptrs(); out['snap0'] = snap()
    if NEW: c.tap(BELT + 3, 3)
    else: c.key('RIGHT', 3); c.key('RIGHT', 3); c.key('RIGHT', 3); c.key('OK', 3)
    r.step(10); out['assign'] = ptrs(); out['snap'] = snap()
    return out
def sc_assign_swap():      # gán đè ô đang có (ô 0 bộ 1): giữ món cũ, ở lại sub 6; rồi Huỷ
    enter(0)
    if NEW: c.tap(CELL + 1, 3); c.tap(CELL + 1, 6); c.tap(0xA0, 8); c.tap(BELT + 1, 5)
    else: c.key('RIGHT', 3); c.key('OK', 6); c.key('OK', 8); c.key('RIGHT', 3); c.key('OK', 5)
    out = {'held': ptrs(), 'snap': snap()}
    if NEW: c.tap(CANCEL, 4)
    else: c.key('CLR', 4)
    out['cancel'] = ptrs()
    return out
def sc_unequip_menu():     # popup loại 7 mục "Tháo" (mục 1)
    enter(0)
    if NEW: c.tap(CELL + 1, 3); c.tap(CELL + 1, 6); c.tap(0xA1, 8)
    else: c.key('RIGHT', 3); c.key('OK', 6); c.key('DOWN', 3); c.key('OK', 8)
    r.step(10); return {'p': ptrs(), 'snap': snap()}
def sc_button():           # nút "Gán phím tắt" == phím OK
    enter(0)
    if NEW: c.tap(CELL + 1, 3); c.tap(ASSIGN, 6)
    else: c.key('RIGHT', 3); c.key('OK', 6)
    return {'p': ptrs()}
def sc_back():
    enter(0)
    if NEW: c.tap(1, 4)
    else: c.key('CLR', 4)
    return {'p': ptrs()}
def sc_tabs():
    out = {}
    for i in range(9):
        pid = os.fork()
        if pid: os.waitpid(pid, 0); continue
        c.S3(4); r.step(2)
        try:
            if NEW: c.tap(0x10 + i)
            else: r.call(0xfa894, c.top(), i + 1); r.step(3)
            r.step(10)
            json.dump({'state': c.st()}, open(os.path.join(OUT, 'tab%d_%s_%s.json' % (i, tag, MODE)), 'w'))
        except Exception as e:
            json.dump({'ERR': repr(e)}, open(os.path.join(OUT, 'tab%d_%s_%s.json' % (i, tag, MODE)), 'w'))
        os._exit(0)
    return {'done': 1}
def sc_deep():             # sub 7..10 (mua/ghép) vẫn đường gốc: vào bằng ghi SetSubState, không crash
    c.S3(4)
    out = {}
    for s in (7, 8, 9, 10):
        r.call(0xe2eb8, c.top(), s); r.step(3); out['s%d' % s] = ptrs()
    return out
def sc_cycles():
    live0 = len(g.heap.sizes); top0 = g.heap.top
    for k in range(20):
        c.S3(4, 5)
        if k % 2 == 0:
            if NEW: c.tap(SUB + (k // 2) % 5, 3)
            else: [c.key('RIGHT', 3) for _ in range((k // 2) % 5)]; c.key('DOWN', 3)
        if k % 4 == 0 and (k // 2) % 5 in (0, 1, 2):
            if NEW: c.tap(CELL + 1, 3); c.tap(CELL + 1, 6)
            else: c.key('RIGHT', 3); c.key('OK', 6)
            for _ in range(2): r.key('CLR'); r.step(4)
        for _ in range(5):
            if c.cls() == 0x15e150: break
            r.key('CLR'); r.step(5)
    r.step(10)
    import hashlib
    return {'live': len(g.heap.sizes) - live0, 'top': g.heap.top - top0, 'img': hashlib.md5(r.shot()).hexdigest(), 'cls': hex(c.cls())}

SC = [('init', sc_init), ('subtabs', sc_subtabs), ('switch', sc_switch), ('select', sc_select), ('drag', sc_drag), ('drag2', sc_drag2), ('assign', sc_assign),
      ('assign_swap', sc_assign_swap), ('tao', sc_unequip_menu), ('button', sc_button), ('back', sc_back), ('tabs', sc_tabs), ('deep', sc_deep), ('cycles', sc_cycles)]
for name, fn in SC: c.fork(fn, name)
res = {}
import glob
for fn in glob.glob(os.path.join(RAW, 'sc_*_%s_%s.json' % (tag, MODE))) + glob.glob(os.path.join(OUT, 'tab*_%s_%s.json' % (tag, MODE))) + glob.glob(os.path.join(OUT, 'sub*_%s_%s.json' % (tag, MODE))):
    res[os.path.basename(fn).split('_%s_' % tag)[0].replace('sc_', '', 1)] = json.load(open(fn))
json.dump(res, open(os.path.join(OUT, 'res_%s_%s.json' % (tag, MODE)), 'w'), indent=1, sort_keys=True)
print('xong', tag, MODE)
