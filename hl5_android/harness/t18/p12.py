"""T18b-0: extSetTimer: chế độ bảng (cờ=1)? delay/hạn mỗi tick; khoảng cách giữa các TimerCb. p12.py W H"""
import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18lib import *
from unicorn.arm_const import *
W, Hh = int(sys.argv[1]), int(sys.argv[2])
r = mk(W, Hh, 'orig', 't18'); to_play(r, 1300); g = r.g; uc = g.uc
log = []
def h128(uc, a, s, u): log.append(('flag', uc.reg_read(UC_ARM_REG_R12)))
def h100(uc, a, s, u): log.append(('set', hex(uc.reg_read(UC_ARM_REG_R0)), uc.reg_read(UC_ARM_REG_R2), uc.reg_read(UC_ARM_REG_R3), 'vclock', g.vclock - 1_600_000_000_000))
def hcb(uc, a, s, u): log.append(('TimerCb', g.vclock - 1_600_000_000_000))
uc.hook_add(UC_HOOK_CODE, h128, begin=BASE + 0x36128, end=BASE + 0x36128)
uc.hook_add(UC_HOOK_CODE, h100, begin=BASE + 0x36100, end=BASE + 0x36100)
uc.hook_add(UC_HOOK_CODE, hcb, begin=BASE + 0x99d98, end=BASE + 0x99d98)
uc.ctl_flush_tb()
for i in range(4): log.clear(); r.step(1); print(i, log)
