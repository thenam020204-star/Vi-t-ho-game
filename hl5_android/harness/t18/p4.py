"""T18a-1b: mainPaint thêm một lần sau mỗi tick trong kịch bản di chuyển+đánh: bộ nhớ (heap + ảnh .so, bỏ FB, bỏ stack) có đổi không; hàm host nào được gọi. p4.py W H [ticks]"""
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
def ev(c, d): g.call(r.keyf, g.env, 0, d, c & 0xffffffff, 0)
bad = 0; hostset = {}; fbsame = 0; heap_ch = 0
for t in range(N):
    if t == 5: ev(KEYS['RIGHT'], 2)
    if t == 40: ev(KEYS['RIGHT'], 3); ev(KEYS['OK'], 2)
    if t == 45: ev(KEYS['OK'], 3)
    if t == 60: ev(KEYS['LEFT'], 2)
    if t == 100: ev(KEYS['LEFT'], 3)
    r.step(1)
    s0 = snap2(); h0 = (len(g.heap.sizes), g.heap.top)
    fb0 = bytes(g.uc.mem_read(fb, W * Hh * 2))
    log.clear(); on[0] = True; r.call(0x987c0); on[0] = False
    s1 = snap2(); h1 = (len(g.heap.sizes), g.heap.top)
    fb1 = bytes(g.uc.mem_read(fb, W * Hh * 2)); fbsame += fb0 == fb1
    if fb0 != fb1 and t in (2, 3, 50):
        a_=np.frombuffer(fb0,np.uint16).reshape(Hh,W); b_=np.frombuffer(fb1,np.uint16).reshape(Hh,W); ys,xs=np.nonzero(a_!=b_); print('tick',t,'FB diff px',len(ys),'bbox',xs.min(),ys.min(),xs.max(),ys.max())
        from PIL import Image; [Image.fromarray(((q.astype(np.uint32)>>11<<3)<<16|((q.astype(np.uint32)>>5&63)<<2)<<8|(q.astype(np.uint32)&31)<<3).astype(np.uint32).view(np.uint8).reshape(Hh,W,4)[:,:,[2,1,0]]).crop((340,30,580,150)).resize((720,360)).save(os.path.expanduser('~/hl5work_t14c/fb%d_%d.png'%(i,t))) for i,q in enumerate((a_,b_))]; Image.fromarray(np.where(a_!=b_,255,0).astype(np.uint8)).save(os.path.expanduser('~/hl5work_t14c/fbdiff_%d.png'%t))
    d = diff(s0, s1, ex)
    for n in log: hostset[n] = hostset.get(n, 0) + 1
    if d or h0 != h1:
        bad += 1
        if bad <= 8: print('tick', t, 'diff', [(hex(a), n) for a, n in d[:10]], h0, h1)
print('ticks', N, 'ticks có ghi ngoài FB/stack:', bad, '| FB trùng sau paint thêm:', fbsame, '/', N)
print('hàm host gọi trong paint thêm:', hostset)
