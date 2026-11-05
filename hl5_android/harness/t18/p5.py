"""T18a-1c: mainPaint gọi thêm NGAY SAU paint của tick (trước mainEndProcess, trong hook 0x99fa8): bộ nhớ (heap+ảnh .so, bỏ FB) và FB có đổi không. p5.py W H [ticks]"""
import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18lib import *
from hl5libc import KEYS
W, Hh = int(sys.argv[1]), int(sys.argv[2]); N = int(sys.argv[3]) if len(sys.argv) > 3 else 120
r = mk(W, Hh, 'orig', 't18'); to_play(r, 1300)
g = r.g; log = []; on = [False]
for a, (nm, fn) in list(g.handlers.items()):
    def mkw(nm, fn):
        def w(*x, **k):
            if on[0]: log.append(nm)
            return fn(*x, **k)
        return w
    g.handlers[a] = (nm, mkw(nm, fn))
fbh = {}
def on_flush(uc, a, s, u): fbh['fb'] = uc.reg_read(UC_ARM_REG_R1)
g.uc.hook_add(UC_HOOK_CODE, on_flush, begin=BASE + 0x3d9d4, end=BASE + 0x3d9d4)
g.uc.ctl_flush_tb(); r.step(2)
fb = fbh['fb']; ex = [(fb, fb + W * Hh * 2 + 0x100)]
def snap2(): top = g.heap.top; return [(b, np.frombuffer(bytes(g.uc.mem_read(b, e - b)), np.uint8)) for b, e in [(0x10000000, 0x10171000), (0x20000000, (top + 0xfff) & ~0xfff)]]
res = []
from unicorn import UC_HOOK_MEM_WRITE
wl = []
def mw(uc, t, a, sz, v, u):
    if on[0]: wl.append((uc.reg_read(UC_ARM_REG_LR) - BASE, uc.reg_read(15) - BASE, v))
g.uc.hook_add(UC_HOOK_MEM_WRITE, mw, begin=0x204b7210, end=0x204b7210)
def inhook(uc, a, s, u):
    s0 = snap2(); h0 = (len(g.heap.sizes), g.heap.top); fb0 = bytes(uc.mem_read(fb, W * Hh * 2))
    log.clear(); on[0] = True; r.call(0x987c0); on[0] = False
    s1 = snap2(); h1 = (len(g.heap.sizes), g.heap.top); fb1 = bytes(uc.mem_read(fb, W * Hh * 2))
    ys = np.nonzero(np.frombuffer(fb0, np.uint16) != np.frombuffer(fb1, np.uint16))[0]
    res.append((diff(s0, s1, ex), h0 != h1, len(ys), list(log)))
g.uc.hook_add(UC_HOOK_CODE, inhook, begin=BASE + 0x99fa8, end=BASE + 0x99fa8)
g.uc.ctl_flush_tb()
def ev(c, d): g.call(r.keyf, g.env, 0, d, c & 0xffffffff, 0)
for t in range(N):
    if t == 5: ev(KEYS['RIGHT'], 2)
    if t == 40: ev(KEYS['RIGHT'], 3); ev(KEYS['OK'], 2)
    if t == 45: ev(KEYS['OK'], 3)
    if t == 60: ev(KEYS['LEFT'], 2)
    if t == 100: ev(KEYS['LEFT'], 3)
    r.step(1)
print('ticks', N, 'paint thêm trong tick:', len(res))
print('tick có ghi bộ nhớ (ngoài FB/stack):', sum(1 for d, h, f, l in res if d), '| heap đổi:', sum(1 for x in res if x[1]), '| FB khác:', sum(1 for x in res if x[2]), '| tổng điểm FB khác:', sum(x[2] for x in res))
for i, (d, h, f, l) in enumerate(res):
    if d: print(' tick', i, [(hex(a), n) for a, n in d[:6]])
hs = {}
for x in res:
    for n in x[3]: hs[n] = hs.get(n, 0) + 1
print('host:', hs); print('writes 0x204b7210 (lr,pc,val):', [(hex(a),hex(b),c) for a,b,c in wl[:6]])
