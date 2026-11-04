import sys, os, time
H = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, os.path.join(H, '..', 't17c1'))
from c1lib import *
W, Hh = int(sys.argv[1]), int(sys.argv[2]); mode = sys.argv[3]; out = sys.argv[4]; last = int(sys.argv[5])
WK = os.path.expanduser('~/hl5work_t16'); WK0 = os.path.expanduser('~/hl5work')
so = WK + '/t16/dev/dev.so' if mode == 'new' else WK0 + '/build_apk/libHeroesLore5.so'
vfs = WK + '/t16/dev/data.vfs.mp3' if mode == 'new' else WK0 + '/build_apk/data.vfs.mp3'
os.makedirs(out, exist_ok=True)
r = Run(so, vfs, W, Hh, 'p1_%s_%d' % (mode, os.getpid()), WK + '/t16')
ev = {}
for it in ROUTE.split(','):
    f, k = it.split(':'); ev.setdefault(int(f), []).append(k)
t0 = time.time()
while r.fr < last:
    for k in ev.get(r.fr, []): r.key(k)
    r.step(1)
    if r.fr % 40 == 0:
        t = r.top()
        print(r.fr, hex(r.cls()), round(time.time() - t0), flush=True)
        save_png(os.path.join(out, 'f%04d.png' % r.fr), W, Hh, r.shot())
