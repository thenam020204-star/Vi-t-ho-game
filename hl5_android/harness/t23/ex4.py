"""T23: số liệu chân dung hộp thoại (blit của dialog.c): tham số, số điểm đục, số đoạn liên tiếp theo hàng -> ước lượng chi phí bản bộ đệm+đoạn. ex4.py W H [f_end]"""
import sys, json, struct; sys.path.insert(0, '/home/user/Vi-t-ho-game/hl5_android/harness/t23')
from t23lib import *
W, Hh = int(sys.argv[1]), int(sys.argv[2]); END = int(sys.argv[3]) if len(sys.argv) > 3 else 1300
c = mk(W, Hh, 0); r = c.r; g = c.g; T = Timeline(c); T.addroute(0, 960)
for f in range(900, 3000, 40): T.ev.setdefault(f, []).append('OK')
blit = BASE + 0x199ff8; seen = []
def hk(uc, a, s, u):
    R = lambda i: uc.reg_read(UC_ARM_REG_R0 + i)
    sp = uc.reg_read(UC_ARM_REG_SP); st = struct.unpack('<11i', bytes(uc.mem_read(sp, 44)))
    seen.append((R(0), R(1), R(2), R(3)) + st)
g.uc.hook_add(UC_HOOK_CODE, hk, begin=blit, end=blit); g.uc.ctl_flush_tb()
out = []
while r.fr < END:
    n0 = len(seen); T.frame()
    if len(seen) > n0 and len(out) < 2:
        for a in seen[n0:]:
            gbm, dx0, dy0, dw = [x & 0xffffffff if i == 0 else x for i, x in enumerate(a[:4])]
            dh, sx0, sy0, sw, sh, flip, alpha, tint = a[4:12]
            w = struct.unpack('<H', bytes(g.uc.mem_read(gbm + 2, 2)))[0]; h = struct.unpack('<H', bytes(g.uc.mem_read(gbm + 4, 2)))[0]
            pal = struct.unpack('<I', bytes(g.uc.mem_read(gbm + 8, 4)))[0]; pix = struct.unpack('<I', bytes(g.uc.mem_read(gbm + 0xc, 4)))[0]
            nib = (bytes(g.uc.mem_read(gbm, 1))[0] & 15) == 4; stride = (w + 1) >> 1 if nib else w
            palv = struct.unpack('<%dH' % 256, bytes(g.uc.mem_read(pal, 512)))
            data = bytes(g.uc.mem_read(pix, stride * h))
            SK = 0xF81F
            def opaque(sx, sy):
                idx = ((data[sy * stride + (sx >> 1)] & 15) if sx & 1 else data[sy * stride + (sx >> 1)] >> 4) if nib else data[sy * stride + sx]
                return palv[idx] != SK
            opq = 0; runs = 0; rows = 0; distinct_rows = 0; prev = None
            xs = [min(w - 1, max(0, (sx0 + ((2 * x + 1) * sw) // (2 * dw)) if not flip else sx0 + sw - 1 - ((2 * x + 1) * sw) // (2 * dw))) for x in range(dw)]
            for y in range(dh):
                sy = sy0 + ((2 * y + 1) * sh) // (2 * dh)
                if sy < 0 or sy >= h: continue
                rows += 1
                if sy != prev: distinct_rows += 1; prev = sy
                inrun = False
                for x in range(dw):
                    o = opaque(xs[x], sy)
                    if o: opq += 1
                    if o and not inrun: runs += 1
                    inrun = o
            out.append(dict(fr=r.fr, args=dict(dw=dw, dh=dh, sw=sw, sh=sh, flip=flip, alpha=alpha, tint=tint, w=w, h=h, nib=nib), dst_px=dw * dh, opaque_px=opq, runs=runs, rows=rows, distinct_src_rows=distinct_rows))
json.dump(out, open(os.path.expanduser('~/hl5work_t23/out/ex4_%dx%d.json' % (W, Hh)), 'w'), indent=1)
print(json.dumps(out))
