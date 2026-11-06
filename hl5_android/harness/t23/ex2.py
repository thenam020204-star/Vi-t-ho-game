"""T23: chi tiết từng khung quanh điểm bắt đầu hoạt cảnh (khung 893..899 + 1 khung bấm OK): hàm nặng nhất mỗi khung. ex2.py W H [f0] [n]"""
import sys, json; sys.path.insert(0, '/home/user/Vi-t-ho-game/hl5_android/harness/t23')
from t23lib import *
W, Hh = int(sys.argv[1]), int(sys.argv[2]); f0 = int(sys.argv[3]) if len(sys.argv) > 3 else 893; n = int(sys.argv[4]) if len(sys.argv) > 4 else 8
c = mk(W, Hh, 0); r = c.r; T = Timeline(c); T.addroute(0, 960)
while r.fr < f0: T.frame()
out = {}
for i in range(n):
    P = T.P; P.start(); fr = r.fr; T.frame(); P.stop(); s = P.snapshot(1)
    print('FRAME', fr, 'total', s['total'])
    for k, v in sorted(s['excl'].items(), key=lambda x: -x[1])[:7]: print('   X %9d %s calls %d' % (v, k[:80], s['calls'].get(k, 0)))
    for k, v in sorted(s['incl'].items(), key=lambda x: -x[1])[:14]:
        if k.startswith('Java_') or k.startswith('Timer') or k.startswith('MIDAS'): continue
        print('   I %9d %s calls %d' % (v, k[:80], s['calls'].get(k, 0)))
    print('   host', sorted(s['host'].items(), key=lambda x: -x[1])[:5])
    out[fr] = s
json.dump(out, open(os.path.expanduser('~/hl5work_t23/out/ex2_%dx%d.json' % (W, Hh)), 'w'))
