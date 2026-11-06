"""T18c-2c đối chứng: hai lần chạy cờ TẮT khác nhịp thời gian (16 vs 19 ms/vòng): ảnh FB cùng tick có khác không (dải hàng 92-122 theo thời gian?). c10.py"""
import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18b import *
W, Hh = 400, 240
rs = [mkd(W, Hh, 'dev', 'c10a'), mkd(W, Hh, 'dev', 'c10b')]
for r in rs: to_play(r, 1300)
for r in rs: ev(r, 'RIGHT', True)
b = [r.ticks for r in rs]; im = []
for k, r in enumerate(rs):
    while r.ticks < b[k] + 20: vloop(r, 19 if k else 16)
    im.append(fbimg(r, W, Hh))
d = im[0] != im[1]; ys, xs = np.nonzero(d)
print('OFF(16ms) vs OFF(19ms) tick 20: px khác', int(d.sum()), 'bbox', (xs.min(), ys.min(), xs.max(), ys.max()) if d.any() else None)
