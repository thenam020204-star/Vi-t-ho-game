"""T20-F: fuzz tương đương fbcopy vs internalGrpCopyFrameBuffer gốc (0x144cdc), cùng bộ nhớ đầu vào, so toàn bộ vùng đệm sau khi chạy.
  fuzz.py [số ca=20000] [seed=1]   (dùng dev.so; chỉ boot, không cần vào cảnh chơi)"""
import sys, os, struct, random, time; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t20lib import *
N = int(sys.argv[1]) if len(sys.argv) > 1 else 20000; seed = int(sys.argv[2]) if len(sys.argv) > 2 else 1
r = mkd(400, 240, 'dev', 'fz'); g = r.g; uc = g.uc
fbc = BASE + r.sym['fbcopy']; orig = BASE + 0x144cdc
rnd = random.Random(seed)
POOL = 0x4000
buf = g.heap.alloc(POOL * 2 + 0x400); structs = g.heap.alloc(0x200)
CANARY = 0xA5
DST = structs; SRC = structs + 0x40; CTX = structs + 0x80
def setfb(p, w, h, stride, depth, data): uc.mem_write(p, struct.pack('<iiiiI', w, h, stride, depth, data & 0xffffffff))
stats = dict(n=0, fast=0, same=0, bad=0, kinds={})
def counters(): return fbst(r)
t0 = time.time()
for it in range(N):
    depth = rnd.choice([16] * 8 + [32] * 4 + [8, 24])
    es = max(depth // 8, 1)
    dw, dh = rnd.randint(6, 36), rnd.randint(6, 30); sw, sh = rnd.randint(6, 36), rnd.randint(6, 30)
    dstr = dw * es + rnd.choice([0, 0, 0, 2, 4, 12]); sstr = sw * es + rnd.choice([0, 0, 0, 2, 4, 12])
    if rnd.random() < 0.04: dstr = max(1, dw * es - rnd.randint(1, 5))     # stride nhỏ hơn hàng (vô lý nhưng phải trùng)
    mode = rnd.choice(['sep', 'sep', 'sep', 'sep', 'adj', 'same', 'ovl', 'blk'])
    if mode == 'blk': sw = dw; sh = rnd.randint(dh, 36); sstr = dstr = dw * es
    dsz, ssz = dstr * dh + 64, sstr * sh + 64
    if mode == 'sep': dbase = buf + 0x100; sbase = buf + POOL + 0x100
    elif mode == 'adj': dbase = buf + 0x100 + 0x200; sbase = dbase + dstr * dh + rnd.randint(-48, 48) - rnd.randint(0, sstr * sh)     # sát nhau / chồng nhẹ quanh biên bao
    elif mode == 'blk': dbase = buf + 0x100; sbase = buf + POOL + 0x100
    elif mode == 'same': dbase = sbase = buf + 0x100
    else: dbase = buf + 0x100 + rnd.randint(0, 600); sbase = buf + 0x100 + rnd.randint(0, 600)
    dx, dy = rnd.randint(-8, dw - 1), rnd.randint(-8, dh - 1); sx, sy = rnd.randint(-8, sw - 1), rnd.randint(-8, sh - 1)
    if rnd.random() < 0.6: sx, sy = max(sx, 0), max(sy, 0)
    if mode == 'blk': dx = sx = 0; dy = rnd.randint(-2, 4); sy = rnd.randint(0, 4); w = dw + rnd.choice([0, 0, 0, 3])
    w, h = rnd.randint(-2, 40), rnd.randint(-2, 34)
    cx0, cy0 = rnd.randint(-6, 20), rnd.randint(-6, 16); cx1, cy1 = rnd.randint(8, 50), rnd.randint(8, 40)
    if rnd.random() < 0.5: cx0, cy0, cx1, cy1 = 0, 0, dw, dh
    key = rnd.choice([0] * 9 + [1]); alpha = rnd.choice([255] * 9 + [254, 128, -1 & 0xffffffff, 300]); cb = 0
    keyc = rnd.randint(0, 0xffff) if rnd.random() < .5 else 0x1234
    data = bytes(rnd.getrandbits(8) for _ in range(POOL * 2 + 0x400))
    if rnd.random() < 0.3:   # nhiều điểm trùng màu key để nhánh key có tác dụng
        data = data.replace(struct.pack('<H', 0x1234 & 0xffff), b'\x34\x12') if False else bytes(b if rnd.random() > .02 else (0x34 if i % 2 == 0 else 0x12) for i, b in enumerate(data))
    outs = []
    for which in (orig, fbc):
        uc.mem_write(buf, data); uc.mem_write(structs, b'\x00' * 0x200)
        setfb(DST, dw, dh, dstr, depth, dbase); setfb(SRC, sw, sh, sstr, depth, sbase)
        if mode == 'same': setfb(SRC, dw, dh, dstr, depth, dbase)
        c = [0] * 14; c[1], c[2], c[3], c[4] = cx0, cy0, cx1, cy1; c[7] = keyc; c[8] = alpha; c[11] = cb; c[12] = 0
        uc.mem_write(CTX, struct.pack('<14I', *[v & 0xffffffff for v in c]))
        src_ptr = DST if mode == 'same' else SRC
        c0 = counters()
        g.call(which, DST, dx, dy, w, h, src_ptr, sx, sy, CTX, key)
        outs.append((bytes(uc.mem_read(buf, POOL * 2 + 0x400)), bytes(uc.mem_read(structs, 0x200)), counters()))
        if which == fbc: stats['kinds'][mode] = stats['kinds'].get(mode, 0) + 1
        if which == fbc and counters()['n_fast'] > c0['n_fast']: stats['fast'] += 1
        # tái dùng cùng tham số ngẫu nhiên: setfb(SRC..) gọi rnd -> đã cố định 'depth' (rnd.random()<2 luôn đúng) nhưng tiêu thụ 1 giá trị: giữ đồng bộ bằng cách tính trước
    stats['n'] += 1
    if outs[0][0] != outs[1][0] or outs[0][1] != outs[1][1]:
        stats['bad'] += 1
        if stats['bad'] <= 5:
            d0 = [i for i in range(len(outs[0][0])) if outs[0][0][i] != outs[1][0][i]]; print('  byte khác', len(d0), 'đầu', d0[:6], 'dbase-buf', dbase - buf, 'sbase-buf', sbase - buf, 'counters', outs[0][2], outs[1][2])
        if stats['bad'] <= 5: print('KHÁC ca', it, dict(mode=mode, depth=depth, dw=dw, dh=dh, sw=sw, sh=sh, dstr=dstr, sstr=sstr, dx=dx, dy=dy, sx=sx, sy=sy, w=w, h=h, clip=(cx0, cy0, cx1, cy1), key=key, alpha=alpha))
    if it % 5000 == 4999: print(it + 1, stats, '%ds' % (time.time() - t0)); sys.stdout.flush()
print('XONG', stats, fbst(r))
