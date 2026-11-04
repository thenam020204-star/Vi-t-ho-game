import sys, os, struct, subprocess, re
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l2'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't17c2'))
from qlib import *
from unicorn import UC_HOOK_CODE
from unicorn.arm_const import *
c = Ctx(600, 270, 'orig', '/tmp/claude-0/o1'); r = c.r; uc = c.g.uc; g = c.g
log = []; on = [0]
def hook(u, a, s, ud):
    if on[0]: log.append((uc.reg_read(UC_ARM_REG_R1) , uc.reg_read(UC_ARM_REG_R2) & 255, uc.reg_read(UC_ARM_REG_R3) & 255))
uc.hook_add(UC_HOOK_CODE, hook, begin=BASE + 0x9e670, end=BASE + 0x9e670)
uc.ctl_flush_tb()
r.call(0x42b08, 0, 1, 1); c.step(15)
shop = g.rd32(c.gv + 5140); print('shop ptr', hex(shop), 'bytes', bytes(uc.mem_read(shop, 0x20)).hex())
buf = (uc.reg_read(UC_ARM_REG_SP) - 0x2000) & ~7
def item(slot):
    uc.mem_write(buf, bytes(400))
    ok = r.call(0x9e790 - 0, 0, 0) if False else None
for tab in range(3):
    if tab: c.key('RIGHT', 4)
    log.clear(); on[0] = 1; c.step(1); on[0] = 0
    print('tab', tab, 'sub', c.b8(c.top(), 0xf), 'tabfield', c.b8(c.top(), 0x10), [(a & 0xff, b, cc) for a, b, cc in log][:40])
seq = []
for slot in range(0, 48):
    uc.mem_write(buf, bytes(400)); ok = r.call(0x9e790, shop, buf, slot) & 255
    b = bytes(uc.mem_read(buf, 40)); seq.append((slot, ok, b[20] if b[20] < 128 else b[20] - 256, b[21]))
print([s for s in seq if s[1]])
