#!/usr/bin/env python3
"""T17c1: dò trường dữ liệu mà hàm Draw gốc đọc: ghi chuỗi sự kiện (GetCommonText, DrawText*, sprintf, đọc bộ nhớ HERO) khi Paint chạy.
Dùng: trace_fields.py SO VFS STATEARG FUNC_LO FUNC_HI [frames]   (STATEARG = S3.<tab> hoặc chuỗi hành động 'S3.1,OK'...)"""
import sys
sys.path.insert(0, __import__('os').path.dirname(__import__('os').path.abspath(__file__)))
from c1lib import *
from unicorn import UC_HOOK_CODE, UC_HOOK_MEM_READ
from unicorn.arm_const import *
so, vfs, scen = sys.argv[1:4]
W = os.path.expanduser(os.environ.get('WORK', '~/hl5work'))
r = Run(so, vfs, 600, 270, 'trace', W + '/t17c1')
route(r, 1000)
g = r.g; uc = g.uc
import struct
def cstr(p, n=96):
    try:
        b = bytes(uc.mem_read(p, n)); b = b.split(b'\0')[0]; return b.hex() if any(c >= 0x80 for c in b) else repr(b.decode('latin1'))
    except Exception: return '?'
def stk(u, i=0): return struct.unpack('<I', bytes(u.mem_read(u.reg_read(UC_ARM_REG_SP) + 4 * i, 4)))[0]
ev = []
def hk(name, addr, f):
    uc.hook_add(UC_HOOK_CODE, lambda u, a, s, d: ev.append((name, f(u))), begin=BASE + addr, end=BASE + addr)
R = lambda u, i: u.reg_read(UC_ARM_REG_R0 + i)
hk('CT', 0x13fa94, lambda u: R(u, 1))
for nm, ad in (('DrawText', 0x41878), ('DrawText6', 0x41d78)):
    hk(nm, ad, lambda u: ('x=%d y=%d' % (R(u, 2), R(u, 3)), cstr(stk(u))))
hk('sprintf', 0x14951c, lambda u: ('fmt', cstr(R(u, 1)), 'args', [R(u, 2) & 0xffffffff, R(u, 3) & 0xffffffff, stk(u)]))
hk('Num', 0x41f34, lambda u: [R(u, i) for i in (2, 3)] + [stk(u)])
hero = r.g.rd32(BASE + 0x16a238 + 5180)
LO = [0, 0]
def rdhook(u, access, addr, size, value, ud):
    pc = u.reg_read(UC_ARM_REG_PC)
    if LO[0] <= pc - BASE < LO[1]:
        ev.append(('rd', 'off=%#x sz=%d val=%d' % (addr - hero, size, int.from_bytes(bytes(u.mem_read(addr, size)), 'little', signed=True))))
TOP = [0]; GV = BASE + 0x16a238
def rdhook(u, access, addr, size, value, ud):
    pc = u.reg_read(UC_ARM_REG_PC)
    if LO[0] <= pc - BASE < LO[1]:
        if hero <= addr < hero + 0x1000: tg, off = 'h', addr - hero
        elif TOP[0] <= addr < TOP[0] + 0xc00: tg, off = 't', addr - TOP[0]
        elif GV <= addr < GV + 0x2000: tg, off = 'g', addr - GV
        else: return
        ev.append(('rd', '%s+%#x sz=%d val=%d' % (tg, off, size, int.from_bytes(bytes(u.mem_read(addr, size)), 'little', signed=True))))
uc.hook_add(UC_HOOK_MEM_READ, rdhook, begin=0x40000000, end=0x60000000)
uc.ctl_flush_tb()
for sc in scen.split(';'):
    acts, lo, hi = sc.split(':'); LO[0] = int(lo, 16); LO[1] = int(hi, 16)
    pid = os.fork()
    if pid: os.waitpid(pid, 0); continue
    for a in acts.split(','):
        if a.startswith('S'):
            i, x = a[1:].split('.'); r.call(0x428bc, 0, int(i), int(x))
        else: r.key(a)
        r.step(8)
    TOP[0] = r.top(); ev.clear(); r.step(1)
    print('=====', sc); seen = set()
    for e in ev:
        k = repr(e)
        if e[0] == 'rd':
            if k in seen: continue
            seen.add(k)
        print(e)
    sys.stdout.flush(); os._exit(0)
