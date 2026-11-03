import sys; sys.path.insert(0, '/home/user/Vi-t-ho-game/hl5_android/harness/t17c3')
from qlib import *
from unicorn import UC_HOOK_CODE
from unicorn.arm_const import *
c = Ctx(600, 270, 'orig', '/tmp/claude-0/q3')
h = c.hero; uc = c.g.uc
c.S3(6, 8); t = c.top()
cap = {}
def hk(u, addr, size, ud):
  try:
    _hk(u)
  except Exception as ex: cap['err']=repr(ex)
def _hk(u):
    sp = u.reg_read(UC_ARM_REG_SP)
    p = struct.unpack('<I', bytes(u.mem_read(sp+0x18, 4)))[0]; n = struct.unpack('<I', bytes(u.mem_read(sp+0x1c, 4)))[0]
    cap.update(p=p, n=n, tab=bytes(u.mem_read(p, 20)).hex(), r10=u.reg_read(UC_ARM_REG_R10))
uc.hook_add(UC_HOOK_CODE, hk, begin=BASE+0x140ae8, end=BASE+0x140ae8)
uc.ctl_flush_tb()
c.wr(h+0x22c, [1]); c.wr(h+0x267, [1])
for e in (0, 3, 4):
    c.r.call(0xe2eb8, t, 1); c.wr(t+0xe, [e]); c.wr(t+0xf, [0]); cap.clear(); c.key('RIGHT', 1)
    print('e', e, cap, 'f', c.b8(t,0xf)); c.shot('lk5_e%d' % e)
# who writes 0x267? just report current values
print('267', c.b8(h,0x267))
