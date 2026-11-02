#!/usr/bin/env python3
"""T13b: rà các patch bridge. BR_ORIG=bridge gốc, BR_FIX=bridge đã vá (apply_bridge). Dùng: BR_ORIG=.. BR_FIX=.. python3 review.py
(1) liveness: thanh ghi được đọc-trước-khi-ghi sau các điểm nhảy/vá; (2) giả lập đoạn 0xc6c30..0xc6e68 (vòng hook + 2 hook đo)
trên bridge gốc (uc_hook_add giả trả 0 = thành công) và bridge đã vá, so thanh ghi callee-saved + đếm throw."""
import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from df import livein
from seg import *
for a in (0xc6d4c, 0xc5d38, 0xc6e68, 0xcc704): print('livein %#x:' % a, {k: v for k, v in sorted(livein(a).items())})
def strlen(uc, ev):
    p = uc.reg_read(UC_ARM64_REG_X0); n = 0
    while uc.mem_read(p + n, 1)[0]: n += 1
    return n
def memmove(uc, ev):
    d_, s_, n = (uc.reg_read(r) for r in (UC_ARM64_REG_X0, UC_ARM64_REG_X1, UC_ARM64_REG_X2))
    uc.mem_write(d_, bytes(uc.mem_read(s_, n))); return d_
def alloc(uc, ev): HEAP[0] += 0x1000; return HEAP[0]
def stubs(hook_ret):
    return {0x1d2a60: strlen, 0x1d2a70: memmove, 0x1d290c: alloc, 0x1ccdd4: alloc, 0x190584: lambda uc, ev: 0,
            0x1ccfbc: lambda uc, ev: (ev.append('THROW'), uc.emu_stop())[0], 0xd7620: lambda uc, ev: (ev.append('lookup'), 1)[1],
            0xe8834: lambda uc, ev: (ev.append('uc_hook_add'), hook_ret)[1], 0x1ccd94: lambda uc, ev: 0, 0x1d2b40: lambda uc, ev: 0}
regs = {'x20': 0x10200000, 'x21': 0x10201000, 'x22': 0x10202000, 'x27': 0x10203000, 'x19': 0x10204000}
import unicorn.arm64_const as C
R = {}
for tag, so, hr in (('goc (uc_hook_add=0)', os.environ['BR_ORIG'], 0), ('goc (uc_hook_add=1: giả lỗi)', os.environ['BR_ORIG'], 1), ('da va', os.environ['BR_FIX'], 0)):
    ev, ok, uc = seg(so, 0xc6c30, 0xc6e68, regs, stubs(hr), maxn=3000000)
    pc = uc.reg_read(UC_ARM64_REG_PC)
    R[tag] = {n: uc.reg_read(getattr(C, 'UC_ARM64_REG_' + n)) for n in ['X%d' % i for i in range(19, 29)] + ['SP']}
    print('%-30s tới c6e68=%s throw=%s hook_add=%d lookup=%d' % (tag, pc == 0xc6e68, 'THROW' in ev, ev.count('uc_hook_add'), ev.count('lookup')))
a, b = R['goc (uc_hook_add=0)'], R['da va']
print('callee-saved khác nhau gốc vs đã vá tại c6e68:', {k: (hex(a[k]), hex(b[k])) for k in a if a[k] != b[k]})

# (3) hàm clear 0xe1f1c (chứa vá 0xe1f84): chạy cả hàm, GL giả là `ret`; kiểm SP/x19/x29/x30 được khôi phục và đối số glViewport
import struct
for tag, so in (('goc', os.environ['BR_ORIG']), ('da va', os.environ['BR_FIX'])):
    for sw, sh, lw, lh in ((2400, 1080, 600, 270), (1600, 720, 532, 240), (800, 480, 400, 240)):
        obj, stub, SENT = 0x10002000, 0x10004000, 0x10005000
        mem = {stub: struct.pack('<I', 0xd65f03c0), SENT: struct.pack('<I', 0xd65f03c0),
               obj + 0x130: struct.pack('<Q', stub), obj + 0x98: struct.pack('<Q', stub), obj + 0x90: struct.pack('<Q', stub),
               obj + 0x50: struct.pack('<4I', sw, sh, lw, lh)}
        regs = {'x0': obj, 'x19': 0xabcd, 'x29': 0x1234, 'x30': SENT}
        cap = {}
        def stubs_():
            return {}
        uc = None
        ev, ok, uc = seg(so, 0xe1f1c, SENT, regs, {}, mem=mem, maxn=2000)
        # viewport: argumentos w0..w3 al llegar al stub se pierden tras ret; leer registros finales
        r = lambda n: uc.reg_read(getattr(C, 'UC_ARM64_REG_' + n)) & 0xffffffff
        print('clear %-6s %dx%d -> pc=%#x(sentinel=%s) sp ok=%s x19=%#x x29=%#x glViewport(%d,%d,%d,%d) %s' % (tag, sw, sh, uc.reg_read(UC_ARM64_REG_PC), uc.reg_read(UC_ARM64_REG_PC) == SENT, uc.reg_read(UC_ARM64_REG_SP) == 0x10080000, uc.reg_read(UC_ARM64_REG_X19), uc.reg_read(UC_ARM64_REG_X29), r('X0'), r('X1'), r('X2'), r('X3'), ev))
