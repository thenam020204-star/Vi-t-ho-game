"""T18b/c: thư viện harness cho nội suy. mkd(W,H,dev,tag): chạy dev.so tại $WORK/t16/<dev>/ (dev = bản có interp.c, base = bản KHÔNG có interp)."""
import sys, os, struct; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18lib import *
from hl5libc import KEYS
from unicorn.arm_const import *
ST = dict(flag=0, armed=4, lag_pending=8, mid=12, D=16, T=20, P=24, Pexp=28, tp=32, valid=36, hero=40, map=44, p0=48, p1=52, c0=56, c1=60, fx130=64, n_mid=68, n_tick_lag=72, n_skip=76)
VS = 16   # ms mỗi nativeLoop (vsync ~60 Hz; làm tròn)
def mkd(W, Hh, dev, tag='t18b'):
    d = WK + '/t16/' + dev
    r = Run(d + '/dev.so', d + '/data.vfs.mp3', W, Hh, '%s_%dx%d_%s_%d' % (tag, W, Hh, dev, os.getpid()), WK + '/t16')
    r.sym = {}
    for l in open(d + '/menu/menu.sym'):
        p = l.split()
        if len(p) == 3: r.sym[p[2]] = int(p[0], 16)
    r.ticks = 0; r.mids = 0; r.flushes = 0; r.hostfb = None
    uc = r.g.uc
    def hp(uc, a, s, u): r.ticks += 1
    uc.hook_add(UC_HOOK_CODE, hp, begin=BASE + 0x98a78, end=BASE + 0x98a78)   # mainProcess = 1 tick thật
    def hf(uc, a, s, u): r.flushes += 1; r.hostfb = uc.reg_read(UC_ARM_REG_R1)
    uc.hook_add(UC_HOOK_CODE, hf, begin=BASE + 0x3d9d4, end=BASE + 0x3d9d4)
    uc.ctl_flush_tb()
    return r
def st_rd(r, f): return struct.unpack('<i', bytes(r.g.uc.mem_read(BASE + r.sym['interp_st'] + ST[f], 4)))[0]
def st_wr(r, f, v): r.g.uc.mem_write(BASE + r.sym['interp_st'] + ST[f], struct.pack('<I', v & 0xffffffff))
def vloop(r, dt=VS):
    r.g.frame = r.fr; r.g.vclock += dt; r.g.call(r.loop, r.g.env, 0); r.fr += 1
def ev(r, name, down):
    c = KEYS[name]; r.g.call(r.keyf, r.g.env, 0, 2 if down else 3, c & 0xffffffff, 0)
def fbimg(r, W, Hh):
    """FB cuối cùng được flush (RGB565 -> mảng uint16 HxW)"""
    return np.frombuffer(bytes(r.g.uc.mem_read(r.hostfb, W * Hh * 2)), np.uint16).reshape(Hh, W).copy()
def hero(r):
    gv = BASE + 0x16a238; hp = r.g.rd32(gv + 5180)
    return struct.unpack('<ii', bytes(r.g.uc.mem_read(hp + 4, 8)))
