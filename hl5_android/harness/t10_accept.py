#!/usr/bin/env python3
"""T10 nghiệm thu bằng harness: đổi ÷k trong Cài đặt -> tệp hd_scale.txt được ghi -> "khởi động lại" với k mới.
Phần bridge được mô phỏng bằng mô hình Python đã đối chiếu với mã bridge thật trong unicorn (patch/test_bridge_k.py, 160 lượt):
cỡ logic = model(màn thật, nội dung hd_scale.txt). Dùng: t10_accept.py SO VFS OUTDIR [màn_W màn_H]   (mặc định 2400 1080)"""
import sys, os, struct, argparse, shutil
HERE = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, HERE)
from hl5run import Guest, BASE
from hl5libc import boot, run_frames, save_png, screen_rgb
so, vfs, out = sys.argv[1:4]
SW, SH = (int(sys.argv[4]), int(sys.argv[5])) if len(sys.argv) > 5 else (2400, 1080)
sys.path.insert(0, os.path.join(HERE, '../patch'))
from hd_cfg import DEFAULT_K


def model(sw, sh, content):
    k = DEFAULT_K
    if content and 0x31 <= content[0] <= 0x34 and (len(content) < 2 or content[1] < 0x21): k = content[0] - 0x30
    if sh < 240: return 400, 240
    k = min(k, sh // 240)
    while True:
        w, h = sw // k, sh // k
        if k <= 1 or (w >= 400 and h >= 240): break
        k -= 1
    return w & ~1, h & ~1


NAV = {460: 'OK', 620: 'DOWN', 640: 'DOWN', 660: 'RIGHT', 680: 'OK'}
for i in range(1, 10): NAV[690 + i * 8] = 'DOWN'


def stage(tag, storage, extra, frames, shots):
    fp = os.path.join(storage, 'hd_scale.txt')
    content = open(fp, 'rb').read() if os.path.exists(fp) else None
    w, h = model(SW, SH, content)
    d = os.path.join(out, tag); os.makedirs(d, exist_ok=True)
    keys = dict(NAV); keys.update(extra)
    ks = ','.join('%d:%s' % (f, k) for f, k in sorted(keys.items()))
    a = argparse.Namespace(so=so, vfs=vfs, out=d, w=w, h=h, frames=frames, shot=0, keys=ks, touch='', storage=storage, reslog=False)
    g = Guest(a); boot(g)
    # chụp ở các khung chỉ định bằng cách chạy từng đoạn
    import hl5libc
    orig = hl5libc.screen_rgb
    shotset = set(shots)
    a.shot = 1
    base_save = hl5libc.save_png
    def sp(path, ww, hh, rgb):
        n = int(os.path.basename(path)[1:6])
        if n in shotset: base_save(os.path.join(d, 'shot_%d.png' % n), ww, hh, rgb)
    hl5libc.save_png = sp
    run_frames(g)
    hl5libc.save_png = base_save
    st = struct.unpack('<14I', bytes(g.uc.mem_read(BASE + 0x1704c0, 56)))
    gf = struct.unpack('<I', bytes(g.uc.mem_read(BASE + 0x16a210, 4)))[0]
    lw, lh = struct.unpack('<2I', bytes(g.uc.mem_read(gf + 0x54, 8)))
    after = open(fp, 'rb').read() if os.path.exists(fp) else None
    print('%s: file trước=%r màn %dx%d -> logic %dx%d (guest báo %dx%d); vn_state.k=%d k0=%d hd=%d; file sau=%r' % (
        tag, content, SW, SH, w, h, lw, lh, st[10], st[11], st[9], after))
    assert (lw, lh) == (w, h)
    return st, after


storage = os.path.join(out, 'storage'); shutil.rmtree(storage, ignore_errors=True); os.makedirs(storage)
st, f1 = stage('run1_default', storage, {790: 'RIGHT'}, 810, [779, 809])
assert st[10] == 3 and f1 == b'3', 'đổi k phải ghi tệp "3"'
st, f2 = stage('run2_restart_k3', storage, {}, 790, [779])
assert st[10] == 3 and st[11] == 3 and f2 == b'3', 'khởi động lại phải đọc k=3'
print('ĐẠT: k=3 được ghi, đọc lại, và kích thước logic mới áp dụng ở lần chạy sau')
