import sys, time; sys.path.insert(0, '/home/user/Vi-t-ho-game/hl5_android/harness/t23')
from t23lib import *
t=time.time(); c = mk(400,240,1300); print('boot', time.time()-t, hl5run.STUB if hasattr(hl5run,'STUB') else None, hex(hl5run.BASE))
p = Prof(c); p.start(); t=time.time(); c.step(5); p.stop(); print('5 frames', time.time()-t)
s = p.snapshot(5); print(s['total'])
for k,v in sorted(s['excl'].items(), key=lambda x:-x[1])[:15]: print(k, v)
for k,v in sorted(s['incl'].items(), key=lambda x:-x[1])[:15]: print('I', k, v)
print(sorted(s['host'].items(), key=lambda x:-x[1])[:10], s['hostb'])
