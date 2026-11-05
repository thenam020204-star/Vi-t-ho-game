"""T14b: rò bộ nhớ 2000 khung ở chiến đấu. check_leak.py W H mode(new|orig) [OUT]: heap (số khối, top) sau khi vào game, mỗi 500 khung; xen chạm nút/dpad (new) hoặc chạm tương ứng (orig)."""
import sys, os, json; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t14lib import *
W, Hh, mode = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3]; OUT = sys.argv[4] if len(sys.argv) > 4 else os.path.join(REPO, 'shots', 'T14')
r = mk(W, Hh, mode, 'lk'); to_play(r, 1300); r.step(5)
def heap(): return (len(r.g.heap.sizes), r.g.heap.top)
pts = [(W - 40, Hh - 40), (W - 60, Hh - 90), (W // 4, Hh // 2), (W - 20, Hh - 80)]
h = [heap()]
for blk in range(4):
    for k in range(5):
        x, y = pts[(blk + k) % 4]
        r.touch(x, y, up=False); r.step(5); r.move(x + 3, y + 3); r.step(5); r.g.call(r.touchf, r.g.env, 0, 20, x, y, 0); r.step(5)
        r.step(85)
    h.append(heap())
res = dict(W=W, H=Hh, mode=mode, frames=r.fr, heap=h)
json.dump(res, open(os.path.join(OUT, 'leak_%dx%d_%s.json' % (W, Hh, mode)), 'w')); print(res)
