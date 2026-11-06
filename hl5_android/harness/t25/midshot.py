"""T25: nội suy bật, bước 16 ms: đếm khung trung gian trong sự kiện/chơi thường; lưu chuỗi ảnh 9 bước liên tiếp trong sự kiện có camera di chuyển. midshot.py DEVDIR W H OUTDIR"""
import sys, os, struct
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'l1'))
from c2lib import Run, ROUTE, BASE
from hl5libc import save_png
D, W, Hh, OUT = sys.argv[1], int(sys.argv[2]), int(sys.argv[3]), sys.argv[4]; os.makedirs(OUT, exist_ok=True)
sym = {l.split()[2]: int(l.split()[0], 16) for l in open(D + '/menu/menu.sym') if len(l.split()) == 3}
r = Run(D + '/dev.so', D + '/data.vfs.mp3', W, Hh, 'ms_%d' % os.getpid(), OUT); g = r.g; GV = BASE + 0x16a238
ev = {}
for it in ROUTE.split(','):
    f, k = it.split(':'); ev.setdefault(int(f), []).append(k)
def u32(a): return g.rd32(a)
nmid_addr = BASE + sym['interp_st'] + 4 * 18
cnt = {'ev': 0, 'play': 0}; ntick = 0; last = None; done = set(); shots = 0; prev_n = 0
for i in range(4200):
    if ntick not in done:
        done.add(ntick); [r.key(k) for k in ev.get(ntick, [])]
    g.frame = r.fr; g.vclock += 16; g.call(r.loop, g.env, 0); r.fr += 1
    t0 = u32(GV + 7584)
    if t0 != last: last = t0; ntick += 1
    n = u32(nmid_addr); e = u32(GV + 5204); inev = e and g.uc.mem_read(e + 0x75, 1)[0]
    if n != prev_n: cnt['ev' if inev else 'play'] += n - prev_n
    prev_n = n
    if inev and 940 <= ntick and shots < 9:
        mp = u32(GV + 5200)
        save_png(os.path.join(OUT, 's%d_t%d_cam%d_%d_%s.png' % (shots, ntick, struct.unpack('<i', bytes(g.uc.mem_read(mp + 0xd0, 4)))[0], struct.unpack('<i', bytes(g.uc.mem_read(mp + 0xd4, 4)))[0], 'mid' if n != getattr(r, '_pn', n) else 'x')), W, Hh, r.shot()); shots += 1
    r._pn = n
print(cnt)
