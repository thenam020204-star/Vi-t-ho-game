"""T20-DBG6: gỡ lỗi mô hình c5b (mid_per_tick luôn 0): in trạng thái interp_st sau mỗi nativeLoop. dbg6.py W H dev ipms_k PAINT PROC MM MCC MCMB [loops=60]"""
import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
sys.argv = [sys.argv[0], sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4], '0'] + sys.argv[5:]
exec(open(os.path.join(os.path.dirname(os.path.abspath(__file__)), 'c5b.py')).read().split("res = []")[0])
LOOPS = 60
cur['ipms'] = SP[0] * 1000.0; base = r.g.vclock; tkp = r.ticks; t0 = r.g.vclock
for i in range(LOOPS):
    k = int((r.g.vclock - base) // 16.667) + 1; r.g.vclock = max(r.g.vclock + 1, int(round(base + k * 16.667)))
    if r.ticks != tkp: tkp = r.ticks; tickwatch()
    v0 = r.g.vclock; r.g.frame = r.fr; r.g.call(r.loop, r.g.env, 0); r.fr += 1
    print(i, 'T+%d' % (v0 - t0), 'dt_loop', r.g.vclock - v0, 'ticks', r.ticks, 'fl', r.flushes, {f: st_rd(r, f) for f in ('armed', 'lag_pending', 'D', 'P', 'Pexp', 'tp', 'valid', 'n_mid', 'n_tick_lag', 'n_skip')}, 'hero', hero(r))
