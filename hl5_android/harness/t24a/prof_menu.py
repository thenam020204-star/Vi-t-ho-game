"""T24a: hồ sơ hàm của một cảnh sau khi vào chơi (route tới 1900): prof_menu.py W H DEVDIR scen   (scen: bag | play | skill | dlg)  -> top exclusive byte-khối/khung"""
import sys, os
W, Hh, dev, scen = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
os.environ['WORK'] = os.path.expanduser('~/hl5work_t24a'); os.environ['DEVDIR'] = os.path.expanduser(dev)
sys.path.insert(0, '/home/user/Vi-t-ho-game/hl5_android/harness/t23')
import t23lib
t23lib.SO_DIS = os.path.expanduser('~/hl5work/so.dis')
from t23lib import *
c = mk(W, Hh, 0); r = c.r
ev = {}
for it in ROUTE.split(','):
    f, k = it.split(':'); ev.setdefault(int(f), []).append(k)
last = {'dlg': 1250, 'mm': 565}.get(scen, 1900)
while r.fr < last:
    for k in ev.get(r.fr, []): r.key(k)
    r.step(1)
for _ in range(30 if scen != 'mm' else 4): r.step(1)
if scen == 'bag': c.S3(2, 10)
elif scen == 'skill': c.S3(7, 10)
P = Prof(c); P.start()
N = 10
for _ in range(N): r.step(1)
P.stop(); s = P.snapshot(N)
print('total/frame', s['total'])
for k, v in sorted(s['excl'].items(), key=lambda kv: -kv[1])[:14]: print('%-40s %10d %5.1f%%' % (k, v, 100.0 * v / s['total']))
print('host', {k: round(v, 1) for k, v in s['host'].items() if v > 3}, 'hostb', s['hostb'])
