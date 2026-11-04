import sys, os, time, struct
H = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, os.path.join(H, '..', 't17c1'))
from c1lib import *
W, Hh = int(sys.argv[1]), int(sys.argv[2]); mode = sys.argv[3]; f0, f1, every = int(sys.argv[4]), int(sys.argv[5]), int(sys.argv[6])
WK = os.path.expanduser('~/hl5work_t16'); WK0 = os.path.expanduser('~/hl5work')
so = WK + '/t16/dev/dev.so' if mode == 'new' else WK0 + '/build_apk/libHeroesLore5.so'
vfs = WK + '/t16/dev/data.vfs.mp3' if mode == 'new' else WK0 + '/build_apk/data.vfs.mp3'
r = Run(so, vfs, W, Hh, 'p4_%s_%d' % (mode, os.getpid()), WK + '/t16')
ev = {}
for it in ROUTE.split(','):
    f, k = it.split(':'); f = int(f)
    if f < 960: ev.setdefault(f, []).append(k)
ev.setdefault(970, []).append('OK')
GV = 0x16a238
while r.fr < f1:
    for k in ev.get(r.fr, []): r.key(k)
    if r.fr > 1000 and r.fr % 60 == 0: r.key('OK')
    r.step(1)
    if r.fr >= f0 and r.fr % every == 0:
        ep = r.rd(GV + 5204); e = r.rdb(ep, 0x6f0)
        D = [e[176 + 60 * k:176 + 60 * k + 60] for k in range(2)]
        print(r.fr, 'ep=%x' % ep, 'e75=%d e76=%d s6e0=%d s6e1=%d s6e2=%d a4=%d cur=%d idx=%d,%d' % (e[0x75], e[0x76], e[0x6e0], e[0x6e1], e[0x6e2], e[164], e[172], e[173], e[174]),
              ' | '.join('st=%d side=%d name=%d lines=%d page=%d spd=%d prog=%d face=%x pos=%d,%d off=%d,%d wh=%d,%d fl=%d,%d' % (d[43], d[40], d[7], d[8], d[9], struct.unpack('<h', d[10:12])[0], struct.unpack('<h', d[12:14])[0], struct.unpack('<I', d[36:40])[0], *struct.unpack('<hhhh', d[48:56]), *struct.unpack('<hh', d[56:60]), d[45], d[46]) for d in D), flush=True)
