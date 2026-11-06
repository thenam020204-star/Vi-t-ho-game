"""T20-S1: ảnh minh chứng base | dev | |khác| (khuếch đại) ở vài thời điểm: s1.py W H -> shots/T20/fb_WxH_<tag>.png (3 ảnh cạnh nhau, FB RGB565 nguyên)"""
import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t20lib import *
from PIL import Image
W, Hh = int(sys.argv[1]), int(sys.argv[2])
OUT = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', '..', 'shots', 'T20'); os.makedirs(OUT, exist_ok=True)
rs = [mkd(W, Hh, d, 's1') for d in ('base', 'dev')]
def rgb(q):
    q = q.astype(np.uint32); return np.stack([((q >> 11) & 31) << 3, ((q >> 5) & 63) << 2, (q & 31) << 3], -1).astype(np.uint8)
def save(tag):
    a, b = fbimg2(rs[0], W, Hh), fbimg2(rs[1], W, Hh); d = (a != b)
    dd = np.stack([d * 255] * 3, -1).astype(np.uint8)
    Image.fromarray(np.concatenate([rgb(a), rgb(b), dd], 1)).save('%s/fb_%dx%d_%s.png' % (OUT, W, Hh, tag)); print(tag, 'điểm khác', int(d.sum()), 'hero', hero(rs[1]))
evs = {}
for it in ROUTE.split(','):
    f, k = it.split(':'); evs.setdefault(int(f), []).append(k)
for fr in range(1300):
    for r in rs:
        for k in evs.get(fr, []): r.key(k)
        r.step(1)
    if fr == 1100: save('hoithoai_boot1100')
save('choi_1300')
for r in rs: ev(r, 'RIGHT', 1)
for i in range(1, 46):
    for r in rs: r.step(1)
    if i == 25: save('cuon_phai_25')
for r in rs: ev(r, 'RIGHT', 0)
for r in rs: r.call(0x428bc, 0, 3, 0)
for i in range(12):
    for r in rs: r.step(1)
save('menu_ingame')
