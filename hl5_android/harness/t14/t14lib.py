"""T14: harness HUD chiến đấu. mode 'orig' (HL5_ORIG, mặc định ~/hl5work/orig_v02hd) | 'new' (WORK/t16/dev/dev.so)."""
import os, sys
sys.path.insert(0, os.path.expanduser("~/hl5work/proj/HL4_project/img5"))
H = os.path.dirname(os.path.abspath(__file__)); REPO = os.path.abspath(os.path.join(H, '..', '..'))
sys.path.insert(0, os.path.join(H, '..', 't17c1')); sys.path.insert(0, os.path.join(H, '..'))
from c1lib import Run, ROUTE, BASE
from hl5libc import save_png
WK = os.path.expanduser(os.environ.get('WORK', '~/hl5work_t14')); ORIGD = os.path.expanduser(os.environ.get('HL5_ORIG', '~/hl5work/orig_v02hd'))
GV = 0x16a238
def mk(W, Hh, mode, tag='t14'):
    new = mode == 'new'
    so = WK + '/t16/dev/dev.so' if new else ORIGD + '/libHeroesLore5.so'
    vfs = WK + '/t16/dev/data.vfs.mp3' if new else ORIGD + '/data.vfs.mp3'
    r = Run(so, vfs, W, Hh, '%s_%dx%d_%s_%d' % (tag, W, Hh, mode, os.getpid()), WK + '/t16')
    r.sym = {}
    if new:
        for l in open(WK + '/t16/dev/menu/menu.sym'):
            p = l.split()
            if len(p) == 3: r.sym[p[2]] = int(p[0], 16)
    return r
def to_play(r, upto=1200):
    ev = {}
    for it in ROUTE.split(','):
        f, k = it.split(':'); ev.setdefault(int(f), []).append(k)
    while r.fr < upto:
        for k in ev.get(r.fr, []): r.key(k)
        r.step(1)
def shot(r, path): save_png(path, r.w, r.h, r.shot())
