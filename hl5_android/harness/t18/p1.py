"""T18a: vết thứ tự TimerCb/mainKeyPressed/mainStateMgr/mainProcess/mainPaint/mainEndProcess/flush mỗi lần nativeLoop (bản gốc)."""
import sys, os; sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't14'))
from t14lib import *
from unicorn import UC_HOOK_CODE
W, Hh = int(sys.argv[1]), int(sys.argv[2])
r = mk(W, Hh, 'orig', 't18'); to_play(r, 1300)
A = {0x99d98: 'TimerCb', 0x98af0: 'KeyPressed', 0x98430: 'StateMgr', 0x98a78: 'Process', 0x987c0: 'Paint', 0x98760: 'EndProc', 0x3d9d4: 'Flush', 0x36100: 'SetTimer', 0x146da0: 'CurTime'}
log = []
from unicorn.arm_const import UC_ARM_REG_LR
def hk(uc, addr, size, ud): log.append('%s@%x' % (A[addr - BASE], uc.reg_read(UC_ARM_REG_LR) - BASE))
for a in A: r.g.uc.hook_add(UC_HOOK_CODE, hk, begin=BASE + a, end=BASE + a)
r.g.uc.ctl_flush_tb()
for i in range(8):
    log.clear(); r.step(1); print(i, ' '.join(log))
