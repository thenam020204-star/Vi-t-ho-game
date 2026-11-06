"""T18c hồi quy cỡ khung: reg.py W H [vòng]: tới cảnh chơi, cờ tắt rồi bật, đi phải; không crash; đếm tick/khung/mid; ảnh vào shots/T18/. """
import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18b import *
from PIL import Image
W, Hh = int(sys.argv[1]), int(sys.argv[2]); N = int(sys.argv[3]) if len(sys.argv) > 3 else 40
OUT = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', '..', 'shots', 'T18'); os.makedirs(OUT, exist_ok=True)
def rgb(q):
    q = q.astype(np.uint32); return np.stack([((q >> 11) & 31) << 3, ((q >> 5) & 63) << 2, (q & 31) << 3], -1).astype(np.uint8)
res = {}
for on in (0, 1):
    r = mkd(W, Hh, 'dev', 'reg%d' % on)
    if on: open(os.path.join(r.g.a.storage, 'interp.txt'), 'w').write('1')   # cờ bằng TỆP (cùng cơ chế hd_scale.txt), bật từ đầu: kiểm menu/tiêu đề không bị ảnh hưởng
    to_play(r, 1300)
    if on: assert st_rd(r, 'flag') == 2, 'interp.txt không được đọc'
    ev(r, 'RIGHT', True); t0, f0, h0 = r.ticks, r.flushes, hero(r)
    for i in range(N): vloop(r)
    ev(r, 'RIGHT', False)
    res['on' if on else 'off'] = dict(ticks=r.ticks - t0, flushes=r.flushes - f0, hero=(h0, hero(r)), mid=st_rd(r, 'n_mid'), lag=st_rd(r, 'n_tick_lag'))
    Image.fromarray(rgb(fbimg(r, W, Hh))).save('%s/reg_%dx%d_%s.png' % (OUT, W, Hh, 'on' if on else 'off'))
print(W, Hh, res)
