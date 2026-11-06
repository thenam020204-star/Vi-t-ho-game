"""T18c-1: cờ TẮT: dev (có interp.c, cờ không bật) so với base (không có interp): ảnh FB từng khung + bộ nhớ .so/heap. c1.py W H [vòng]"""
import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18b import *
W, Hh = int(sys.argv[1]), int(sys.argv[2]); N = int(sys.argv[3]) if len(sys.argv) > 3 else 150
rs = [mkd(W, Hh, d) for d in ('base', 'dev')]
for r in rs: to_play(r, 1300)
SCRIPT = {5: ('RIGHT', 1), 40: ('RIGHT', 0), 42: ('OK', 1), 45: ('OK', 0), 60: ('LEFT', 1), 100: ('LEFT', 0), 110: ('OK', 1), 112: ('OK', 0)}
bad_fb = 0; nfl = [0, 0]; bad_mem = []
def regs(r): top = r.g.heap.top; return [(0x10000000, 0x10171000), (0x20000000, (top + 0xfff) & ~0xfff)]
def snapr(r): return [(b, np.frombuffer(bytes(r.g.uc.mem_read(b, e - b)), np.uint8)) for b, e in regs(r)]
for i in range(N):
    for r in rs:
        if i in SCRIPT: ev(r, SCRIPT[i][0], SCRIPT[i][1])
        vloop(r)
    if rs[0].flushes != rs[1].flushes or rs[0].ticks != rs[1].ticks: print('loop', i, 'lệch flush/tick', rs[0].flushes, rs[1].flushes, rs[0].ticks, rs[1].ticks); break
    a, b = fbimg(rs[0], W, Hh), fbimg(rs[1], W, Hh)
    if not (a == b).all(): bad_fb += 1; print('loop', i, 'FB khác', int((a != b).sum()))
    if i % 30 == 29:
        s0, s1 = snapr(rs[0]), snapr(rs[1]); fb = rs[0].hostfb; ex = [(fb, fb + W * Hh * 2 + 0x100)]
        # FBOff (bộ đệm nền) nằm trong heap: không loại; loại stack
        d = diff(s0, s1, ex)
        print('loop', i, 'ticks', rs[0].ticks, 'khác bộ nhớ:', [(hex(x), n) for x, n in d[:12]], len(d))
print('XONG: vòng', i + 1, 'ticks', rs[0].ticks, 'flushes', rs[0].flushes, 'FB khác ở', bad_fb, 'vòng')
