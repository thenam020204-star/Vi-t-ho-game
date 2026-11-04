import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1b'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
from unicorn import UC_HOOK_CODE
from unicorn.arm_const import UC_ARM_REG_R1, UC_ARM_REG_LR
c = Ctx(400, 240, sys.argv[1] if len(sys.argv) > 1 else 'orig', '/tmp/claude-0/pb'); r = c.r; g = c.g
LOG = []
def hk(uc, addr, sz, ud):
    LOG.append((uc.reg_read(UC_ARM_REG_R1), uc.reg_read(UC_ARM_REG_LR) - BASE))
g.uc.hook_add(UC_HOOK_CODE, hk, None, BASE + 0x13fa94, BASE + 0x13fa94)
def go(n): t = c.top(); r.call(0xda410, t, n, 0); c.step(8)
def show(tag): print(tag, 'cls', hex(c.cls()), [c.s8(c.top(), o) for o in range(0xc, 0x14)], 'GCT:', sorted(set(LOG))[:12]); LOG.clear()
c.wr(c.hero + 0xf0, [5, 0, 0, 0])
go(1); show('state1')
c.key('OK', 8); show('after OK (Có)')
c.key('OK', 8); show('OK2')
c.step(20); show('later')
