"""T25: trong hoạt cảnh, gọi thêm mainPaint (0x987c0) một lần sau tick và diff bộ nhớ (bỏ FB/FBOff) => trường paint ghi. pdiff.py DEVDIR W H F1,F2,..."""
import sys, os, struct
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'l1')); sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't18'))
from c2lib import Run, route
from t18lib import snap, diff, rb
from c2lib import BASE
D, W, Hh, FS = sys.argv[1], int(sys.argv[2]), int(sys.argv[3]), [int(x) for x in sys.argv[4].split(',')]
r = Run(D + '/dev.so', D + '/data.vfs.mp3', W, Hh, 'pd_%d' % os.getpid(), os.path.expanduser('~/hl5work_t22/t25'))
g = r.g; GV = BASE + 0x16a238
def u32(a): return g.rd32(a)
for F in FS:
    route(r, F)
    ep = u32(GV + 5204); mp = u32(GV + 5200); hero = u32(GV + 5180)
    gfx = u32(BASE + 0x16a210)
    fb = u32(gfx + 0) if False else None
    ex = []
    for fn in (0x4e4f8, 0x4e508):
        fbs = r.call(fn, gfx) & 0xffffffff
        if fbs: dat = u32(fbs + 0x10); ex.append((dat, dat + u32(fbs + 8) * u32(fbs + 4) + 64))
    ex.append((0x70000000, 0x70400000))
    s0 = snap(r); hp0 = (len(g.heap.sizes), g.heap.top)
    r.call(0x987c0)
    s1 = snap(r); hp1 = (len(g.heap.sizes), g.heap.top)
    d = diff(s0, s1, ex)
    big = [(a, n) for a, n in d if n > 20000]
    small = [(a, n) for a, n in d if n <= 20000]
    print('== F%d ev75=%d ep686=%d top=%#x heap %s->%s; vùng lớn (FB?) %s' % (F, g.uc.mem_read(ep + 0x75, 1)[0] if ep else -1, g.uc.mem_read(ep + 0x686, 1)[0] if ep else -1, r.top(), hp0, hp1, [(hex(a), n) for a, n in big]))
    for a, n in small[:60]:
        tag = ''
        for nm, b in (('gv', GV), ('EP', ep), ('MAP', mp), ('HERO', hero)):
            if b and b <= a < b + 0x4000: tag = '%s+%#x' % (nm, a - b); break
        print('  %08x +%d %-14s %s -> %s' % (a, n, tag, rb(s0, a, min(n, 8)).hex(), rb(s1, a, min(n, 8)).hex()))
    r.step(1)
