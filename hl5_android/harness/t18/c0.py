"""T18b-0: thử nhanh: cờ bật, đi phải, đếm tick/khung trung gian/flush. c0.py W H dev [bật=1] [số vòng]"""
import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18b import *
W, Hh, dev = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3]; on = int(sys.argv[4]) if len(sys.argv) > 4 else 1; N = int(sys.argv[5]) if len(sys.argv) > 5 else 120
r = mkd(W, Hh, dev); to_play(r, 1300)
if on: st_wr(r, 'flag', 2)
print('hero', hero(r), 'ticks', r.ticks, 'flushes', r.flushes)
ev(r, 'RIGHT', True)
t0 = r.ticks; f0 = r.flushes
for i in range(N):
    vloop(r)
    if i % 10 == 0: print(i, 'tick', r.ticks - t0, 'flush', r.flushes - f0, 'hero', hero(r), {k: st_rd(r, k) for k in ('armed', 'n_mid', 'n_tick_lag', 'n_skip', 'tp')})
ev(r, 'RIGHT', False)
print('tick', r.ticks - t0, 'flush', r.flushes - f0)
