"""khám phá kịch bản: ex1.py W H dev  -> lưu ảnh vào $WORK/out/ex1"""
import sys, os, time; sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't18'))
from t18b import *
from PIL import Image
W, Hh, dev = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3]
O = os.path.expanduser('~/hl5work_t20/out/ex1'); os.makedirs(O, exist_ok=True)
def rgb(q):
    q = q.astype(np.uint32); return np.stack([((q >> 11) & 31) << 3, ((q >> 5) & 63) << 2, (q & 31) << 3], -1).astype(np.uint8)
r = mkd(W, Hh, dev, 'ex1'); t=time.time()
def sv(n): Image.fromarray(rgb(fbimg(r, W, Hh))).save('%s/%s.png' % (O, n))
for f in (1100, 1200, 1300):
    pass
to_play(r, 1300); print('to_play', time.time()-t, 'cls', hex(r.cls()), 'ticks', r.ticks); sv('a1300')
for k in ('OK','OK','OK'):
    r.key(k); r.step(30); 
sv('a_ok3'); print(hex(r.cls()))
