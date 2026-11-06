import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18lib import *
from unicorn.arm_const import *
from unicorn import UC_HOOK_MEM_WRITE
r = mk(400, 240, 'orig', 't18'); g = r.g; uc = g.uc
ad = []
def h(uc, a, s, u): ad.append(uc.reg_read(UC_ARM_REG_R12))
uc.hook_add(UC_HOOK_CODE, lambda uc,a,s,u: ad.append(uc.reg_read(UC_ARM_REG_R12)), begin=BASE+0x36124, end=BASE+0x36124)  # sau ldrb? r12 = giá trị
uc.hook_add(UC_HOOK_CODE, lambda uc,a,s,u: print('ptr r12', hex(uc.reg_read(UC_ARM_REG_R12))), begin=BASE+0x36120, end=BASE+0x36120)
uc.ctl_flush_tb()
to_play(r, 470)
print(ad[:3])
