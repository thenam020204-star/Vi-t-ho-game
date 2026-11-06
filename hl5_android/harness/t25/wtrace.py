"""T25: ai ghi các trường bị paint thay đổi trong hoạt cảnh. Theo ROUTE tới F, bật hook ghi trên vùng các đối tượng, gọi mainPaint thêm 1 lần, in PC ghi (gom theo hàm) với địa chỉ ngoài FB/FBOff/stack.
wtrace.py DEVDIR W H F1,F2,..."""
import sys, os, re, bisect
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'l1'))
from c2lib import Run, route, BASE
from unicorn import UC_HOOK_MEM_WRITE
D, W, Hh, FS = sys.argv[1], int(sys.argv[2]), int(sys.argv[3]), [int(x) for x in sys.argv[4].split(',')]
syms = []
for l in open(os.path.expanduser('~/hl5work/so.dis')):
    m = re.match(r'^([0-9a-f]{8}) <(.*)>:$', l)
    if m: syms.append((int(m.group(1), 16), m.group(2)))
for l in open(D + '/menu/menu.sym'):
    p = l.split()
    if len(p) == 3: syms.append((int(p[0], 16), 'M:' + p[2]))
syms.sort(); keys = [a for a, _ in syms]
def fn(pc):
    o = pc - BASE; i = bisect.bisect_right(keys, o) - 1
    return syms[i][1] if i >= 0 else hex(o)
r = Run(D + '/dev.so', D + '/data.vfs.mp3', W, Hh, 'wt_%d' % os.getpid(), os.path.expanduser('~/hl5work_t22/t25'))
g = r.g; GV = BASE + 0x16a238
for F in FS:
    route(r, F)
    gfx = g.rd32(BASE + 0x16a210); ex = [(0x70000000, 0x70400000)]
    for f_ in (0x4e4f8, 0x4e508):
        fbs = r.call(f_, gfx) & 0xffffffff
        if fbs: d = g.rd32(fbs + 0x10); ex.append((d, d + g.rd32(fbs + 8) * g.rd32(fbs + 4) + 64))
    hits = {}
    def hw(uc, acc, a, sz, v, u):
        for lo, hi in ex:
            if lo <= a < hi: return
        if BASE + 0x171000 <= a < BASE + 0x600000: return          # bss đoạn menu (của ta)
        pc = uc.reg_read(__import__('unicorn').arm_const.UC_ARM_REG_PC)
        k = fn(pc); e = hits.setdefault(k, [0, set()]); e[0] += 1
        if len(e[1]) < 6: e[1].add(hex(a))
    h = g.uc.hook_add(UC_HOOK_MEM_WRITE, hw)
    r.call(0x987c0)
    g.uc.hook_del(h)
    ep = g.rd32(GV + 5204)
    print('== F%d ev75=%d' % (F, g.uc.mem_read(ep + 0x75, 1)[0] if ep else -1))
    for k, (n, ads) in sorted(hits.items(), key=lambda x: -x[1][0])[:40]: print('  %6d %-60s %s' % (n, k[:60], sorted(ads)[:4]))
    r.step(1)
