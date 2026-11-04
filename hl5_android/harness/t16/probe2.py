import sys, os, time, struct
H = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, os.path.join(H, '..', 't17c1'))
from c1lib import *
from unicorn import UC_HOOK_CODE
from unicorn.arm_const import UC_ARM_REG_R0
W, Hh = int(sys.argv[1]), int(sys.argv[2]); mode = sys.argv[3]; out = sys.argv[4]; last = int(sys.argv[5]); every = int(sys.argv[6])
WK = os.path.expanduser('~/hl5work_t16'); WK0 = os.path.expanduser('~/hl5work')
so = WK + '/t16/dev/dev.so' if mode == 'new' else WK0 + '/build_apk/libHeroesLore5.so'
vfs = WK + '/t16/dev/data.vfs.mp3' if mode == 'new' else WK0 + '/build_apk/data.vfs.mp3'
os.makedirs(out, exist_ok=True)
r = Run(so, vfs, W, Hh, 'p2_%s_%d' % (mode, os.getpid()), WK + '/t16')
EP = [0]
def hk(uc, a, s, u): EP[0] = uc.reg_read(UC_ARM_REG_R0)
r.g.uc.hook_add(UC_HOOK_CODE, hk, begin=BASE + 0x6ddcc, end=BASE + 0x6ddcc)
ev = {}
for it in ROUTE.split(','):
    f, k = it.split(':'); f = int(f)
    if f < 960: ev.setdefault(f, []).append(k)
ev.setdefault(970, []).append('OK')
t0 = time.time()
while r.fr < last:
    for k in ev.get(r.fr, []): r.key(k)
    if r.fr > 1000 and r.fr % 60 == 0: r.key('OK')
    r.step(1)
    if r.fr % every == 0:
        info = ''
        if EP[0]:
            e = EP[0]; i = r.rdb(e + 172, 3); st = []
            for k in range(2):
                D = e + 176 + 60 * k; b = r.rdb(D, 60); st.append(b.hex())
            info = 'EP=%x idx=%s ' % (e, i.hex()) + ' | '.join(st)
        print(r.fr, hex(r.cls()), info, flush=True)
        save_png(os.path.join(out, 'f%04d.png' % r.fr), W, Hh, r.shot())
