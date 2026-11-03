import sys; sys.path.insert(0, '/home/user/Vi-t-ho-game/hl5_android/harness/t17c3')
from qlib import *
from unicorn import UC_HOOK_CODE
from unicorn.arm_const import *
W, Hh = 600, 270
c = Ctx(W, Hh, 'orig', '/tmp/claude-0/q3')
h = c.hero; uc = c.g.uc
c.S3(6, 8)
log = []
def rd(a, n=24):
    try: return bytes(uc.mem_read(a, n))
    except Exception: return b''
def mk(name, nargs, strarg=None):
    def hk(u, addr, size, ud):
        regs = [u.reg_read(r) for r in (UC_ARM_REG_R0, UC_ARM_REG_R1, UC_ARM_REG_R2, UC_ARM_REG_R3)]
        sp = u.reg_read(UC_ARM_REG_SP); st = [struct.unpack('<i', rd(sp+4*i, 4))[0] for i in range(4)]
        a = [struct.unpack('<i', struct.pack('<I', x))[0] for x in regs]
        s = ''
        if strarg is not None:
            p = (regs+st)[strarg]; s = rd(p, 20).split(b'\0')[0].hex()
        log.append((name, a[1:]+st, s))
    return hk
for name, addr, sa in (('SkillBeltIcon', 0x7d6c0, None), ('DrawText', 0x41d78, 3), ('AutoSkillBox', 0xeb490, None), ('SharpInfo', 0x74144, None), ('DrawScrollHor', 0x7491c, None)):
    uc.hook_add(UC_HOOK_CODE, mk(name, 4, sa), begin=BASE+addr, end=BASE+addr)
def dump(tag):
    log.clear(); c.step(1)
    print('==', tag, 'sub', c.sub(), 'e', c.b8(c.top(),0xe), 'f', c.b8(c.top(),0xf))
    for l in log: print('  ', l)
dump('sub0 e0')
c.key('OK'); dump('sub1 f0')
c.key('RIGHT'); dump('sub1 f1')
for i in range(5): c.key('RIGHT')
dump('f6')
