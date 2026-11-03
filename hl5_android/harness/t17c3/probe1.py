import sys; sys.path.insert(0, '/home/user/Vi-t-ho-game/hl5_android/harness/t17c3')
from qlib import *
from unicorn import UC_HOOK_CODE
from unicorn.arm_const import *
W, Hh = 600, 270
c = Ctx(W, Hh, 'orig', '/tmp/claude-0/q3')
h = c.hero
print('class', c.b8(h,0x22c), '267', c.b8(h,0x267), 'lvl', c.b8(h,0x22d))
c.S3(6, 8)
t = c.top(); print('state', c.st(), 'sub', c.sub(), hex(c.cls()))
# hook after table setup 0x1026ac
cap = {}
def hk(uc, addr, size, ud):
    sp = uc.reg_read(UC_ARM_REG_SP)
    p = struct.unpack('<I', bytes(uc.mem_read(sp+0x18, 4)))[0]; n = struct.unpack('<I', bytes(uc.mem_read(sp+0x1c, 4)))[0]
    cap['p'] = p; cap['n'] = n; cap['tab'] = bytes(uc.mem_read(p, 20)).hex()
c.g.uc.hook_add(UC_HOOK_CODE, hk, begin=BASE+0x1026ac, end=BASE+0x1026ac)
for cls in range(4):
  for f in (0, 1):
    c.wr(h+0x22c, [cls]); c.wr(h+0x267, [f])
    cap.clear(); c.key('LEFT', 1)
    print('cls', cls, 'f267', f, cap)
