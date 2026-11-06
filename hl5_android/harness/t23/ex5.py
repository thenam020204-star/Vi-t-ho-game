"""T23: tham số các hàm vẽ toàn màn mỗi khung trong hoạt cảnh (ChangeFrameBufferBlending565 0x500e8, FillRectAlpha 0x4e684): ex5.py W H f0 f1"""
import sys, json, struct; sys.path.insert(0, '/home/user/Vi-t-ho-game/hl5_android/harness/t23')
from t23lib import *
W, Hh, f0, f1 = int(sys.argv[1]), int(sys.argv[2]), int(sys.argv[3]), int(sys.argv[4])
c = mk(W, Hh, 0); r = c.r; g = c.g; T = Timeline(c); T.addroute(0, 960)
for f in range(900, 3000, 40): T.ev.setdefault(f, []).append('OK')
log = []
def mk_hook(name):
    def hk(uc, a, s, u):
        R = lambda i: uc.reg_read(UC_ARM_REG_R0 + i)
        sp = uc.reg_read(UC_ARM_REG_SP); st = struct.unpack('<6i', bytes(uc.mem_read(sp, 24)))
        lr = uc.reg_read(UC_ARM_REG_LR) - BASE
        log.append((r.fr, name, [R(i) & 0xffffffff for i in range(4)], list(st), hex(lr)))
    return hk
for nm, ad in (('CFBB565', 0x500e8), ('FillRectAlpha', 0x4e684), ('MapEffect', 0xa8b60)):
    g.uc.hook_add(UC_HOOK_CODE, mk_hook(nm), begin=BASE + ad, end=BASE + ad)
g.uc.ctl_flush_tb()
while r.fr < f1:
    T.frame()
json.dump(log, open(os.path.expanduser('~/hl5work_t23/out/ex5_%dx%d.json' % (W, Hh)), 'w'))
for x in log:
    if x[0] >= f0: print(x)
