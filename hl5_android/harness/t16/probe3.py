"""T16a: ghi tham số các lời gọi BFont::DrawTextField/IsTextFieldDrawComplete/GetTextFieldPageLen trong khung thoại (bản gốc)."""
import sys, os, time, struct
H = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, os.path.join(H, '..', 't17c1'))
from c1lib import *
from unicorn import UC_HOOK_CODE
from unicorn.arm_const import *
W, Hh = int(sys.argv[1]), int(sys.argv[2]); mode = sys.argv[3]; f0, f1 = int(sys.argv[4]), int(sys.argv[5])
WK = os.path.expanduser('~/hl5work_t16'); WK0 = os.path.expanduser('~/hl5work')
so = WK + '/t16/dev/dev.so' if mode == 'new' else WK0 + '/build_apk/libHeroesLore5.so'
vfs = WK + '/t16/dev/data.vfs.mp3' if mode == 'new' else WK0 + '/build_apk/data.vfs.mp3'
r = Run(so, vfs, W, Hh, 'p3_%s_%d' % (mode, os.getpid()), WK + '/t16')
g = r.g; uc = g.uc
ON = [False]
def hk(uc, a, s, u):
    if not ON[0]: return
    off = a - BASE
    R = [uc.reg_read(x) for x in (UC_ARM_REG_R0, UC_ARM_REG_R1, UC_ARM_REG_R2, UC_ARM_REG_R3)]
    sp = uc.reg_read(UC_ARM_REG_SP); st = struct.unpack('<7i', bytes(uc.mem_read(sp, 28)))
    name = {0x41b00: 'DrawTextField', 0x4112c: 'IsComplete', 0x41080: 'PageLen', 0x7e838: 'DrawDialogBox', 0x7e628: 'NameBox', 0x5b328: 'DrawEffect', 0x5ae20: 'DrawImageBlending'}[off]
    txt = ''
    for v in R + list(st):
        if 0x20000000 <= (v & 0xffffffff) < 0x28000000 and name in ('DrawTextField', 'IsComplete', 'PageLen'):
            try: txt = bytes(uc.mem_read(v & 0xffffffff, 12)).split(b'\0')[0][:10].hex()
            except Exception: pass
    print('  fr%d %s r=%s st=%s %s' % (r.fr, name, [x if x < 0x80000000 else x - 2**32 for x in R], list(st), txt), flush=True)
for a in (0x41b00, 0x4112c, 0x41080, 0x7e838, 0x7e628, 0x5b328, 0x5ae20):
    uc.hook_add(UC_HOOK_CODE, hk, begin=BASE + a, end=BASE + a)
ev = {}
for it in ROUTE.split(','):
    f, k = it.split(':'); f = int(f)
    if f < 960: ev.setdefault(f, []).append(k)
ev.setdefault(970, []).append('OK')
while r.fr < f1:
    for k in ev.get(r.fr, []): r.key(k)
    if r.fr > 1000 and r.fr % 60 == 0:
        print('KEY OK at', r.fr, flush=True); r.key('OK')
    ON[0] = r.fr >= f0
    r.step(1)
