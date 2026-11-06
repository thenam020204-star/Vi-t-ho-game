"""T18c-3: ảnh khung trung gian. TẮT: ảnh tick n-1, n (hero đi phải ổn định). BẬT: ảnh tick (trễ, alpha=0) và các khung trung gian ép alpha=0,64,128,192,256.
So: (a) alpha=256 == ảnh tick n của TẮT? (b) alpha=0 == ảnh tick của BẬT; (c) nền lệch đúng alpha*Δ. c3.py W H [n_tick] [outdir]"""
import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18b import *
from PIL import Image
W, Hh = int(sys.argv[1]), int(sys.argv[2]); NT = int(sys.argv[3]) if len(sys.argv) > 3 else 20; OUT = sys.argv[4] if len(sys.argv) > 4 else os.path.expanduser('~/hl5work_t18/out/c3')
os.makedirs(OUT, exist_ok=True)
off, on = mkd(W, Hh, 'dev', 'c3off'), mkd(W, Hh, 'dev', 'c3on')
for r in (off, on): to_play(r, 1300)
st_wr(on, 'flag', 2)
def tick_to(r, n):
    while r.ticks < n: vloop(r)
b = [off.ticks, on.ticks]
ev(off, 'RIGHT', True); ev(on, 'RIGHT', True)
imgs = {}
for n in range(1, NT + 1):
    tick_to(off, b[0] + n); tick_to(on, b[1] + n)
    if n == NT - 1: imgs['off_prev'] = fbimg(off, W, Hh); h_prev = hero(off)
    if n == NT:
        imgs['off_cur'] = fbimg(off, W, Hh); h_cur = hero(off)
        imgs['on_tick'] = fbimg(on, W, Hh)
print('hero off prev/cur', h_prev, h_cur, 'ON hero (logic)', hero(on), 'armed', st_rd(on, 'armed'), 'P', st_rd(on, 'P'), 'p/c', st_rd(on, 'p0'), st_rd(on, 'c0'))
P = st_rd(on, 'P'); fl0 = on.flushes; tk0 = on.ticks
hk = {'a': 0}
def hmid(uc, ad, sz, u):   # đầu interp_mid: ép T để alpha đúng (now32 sẽ trả vclock+1)
    now = (on.g.vclock + 1) & 0xffffffff
    st_wr(on, 'T', (now - ((hk['a'] * P) >> 8)) & 0xffffffff); st_wr(on, 'D', (now + 1000) & 0xffffffff)
on.g.uc.hook_add(UC_HOOK_CODE, hmid, begin=BASE + on.sym['interp_mid'], end=BASE + on.sym['interp_mid']); on.g.uc.ctl_flush_tb()
for a in (0, 64, 128, 192, 256):
    hk['a'] = a; f = on.flushes; vloop(on)
    assert on.flushes == f + 1 and on.ticks == tk0, ('không phải khung trung gian', on.flushes - f, on.ticks - tk0)
    imgs['mid%d' % a] = fbimg(on, W, Hh); print('alpha', a, 'hero logic', hero(on))
def rgb(q):
    q = q.astype(np.uint32); return np.stack([((q >> 11) & 31) << 3, ((q >> 5) & 63) << 2, (q & 31) << 3], -1).astype(np.uint8)
for k, v in imgs.items(): Image.fromarray(rgb(v)).save('%s/%s.png' % (OUT, k))
eq = lambda a, b: int((imgs[a] != imgs[b]).sum())
print('(a) mid256 vs off_cur: px khác', eq('mid256', 'off_cur'), '| (b) mid0 vs on_tick:', eq('mid0', 'on_tick'), '| on_tick vs off_prev:', eq('on_tick', 'off_prev'), '| off_prev vs off_cur:', eq('off_prev', 'off_cur'))
# (c) dịch nền: với mỗi alpha tìm dịch ngang s (px) tối ưu giữa mid và off_prev (s so với prev) trên dải hàng nền
def best_shift(img, ref, rows):
    best = None
    for s in range(-40, 41):
        if s >= 0: m = (img[rows, s:] == ref[rows, :W - s]).mean()
        else: m = (img[rows, :W + s] == ref[rows, -s:]).mean()
        if best is None or m > best[0]: best = (m, s)
    return best
rows = slice(int(Hh * 0.06), int(Hh * 0.22))
base = best_shift(imgs['off_cur'], imgs['off_prev'], rows)
print('dịch nền tick prev->cur (off):', base)
for a in (0, 64, 128, 192, 256):
    m, s = best_shift(imgs['mid%d' % a], imgs['off_prev'], rows); print('alpha', a, 'dịch nền so với off_prev:', s, 'khớp %.3f' % m, ' kỳ vọng', round(base[1] * a / 256, 2))
