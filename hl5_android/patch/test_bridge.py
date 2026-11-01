#!/usr/bin/env python3
"""Giả lập (unicorn arm64) các hàm bridge đã vá (T8m): W/H, viewport, chạm, ép NEAREST.
Dùng: test_bridge.py PATCHED.so"""
import sys, struct
from unicorn import *
from unicorn.arm64_const import *
from elftools.elf.elffile import ELFFile
so = sys.argv[1]
elf = ELFFile(open(so, 'rb'))
def mk():
    uc = Uc(UC_ARCH_ARM64, UC_MODE_ARM)
    uc.mem_map(0, 0x240000)
    for s in elf.iter_segments():
        if s['p_type'] == 'PT_LOAD': uc.mem_write(s['p_vaddr'], s.data())
    uc.mem_map(0x10000000, 0x10000)
    uc.reg_write(UC_ARM64_REG_SP, 0x10008000)
    # bật FP/SIMD
    uc.reg_write(UC_ARM64_REG_CPACR_EL1, 0x300000)
    return uc
W = lambda uc, n: uc.reg_read(getattr(__import__('unicorn.arm64_const', fromlist=['x']), 'UC_ARM64_REG_W%d' % n))
def run(uc, a, b): uc.emu_start(a, b, count=2000)
def rd32(uc, a): return struct.unpack('<I', uc.mem_read(a, 4))[0]
print('| màn | k | W x H (khung logic) | viewport (x,y,w,h) | chạm: vpW,vpH,offX,offY | nhận xét |')
print('|---|---|---|---|---|---|')
for sw, sh in ((2400, 1080), (1920, 1080), (1600, 720), (1280, 720), (800, 480), (600, 270), (400, 240)):
    uc = mk()
    uc.reg_write(UC_ARM64_REG_X26, 0x10001000)
    uc.reg_write(UC_ARM64_REG_X25, sw); uc.reg_write(UC_ARM64_REG_X22, sh)
    run(uc, 0xc5cc0, 0xc5d48)
    lw, lh = rd32(uc, 0x1fdac8), rd32(uc, 0x1fdacc)
    k = max(1, min(sw // lw, sh // lh))
    # viewport
    uc = mk()
    obj = 0x10002000; stub = 0x10004000
    uc.mem_write(stub, struct.pack('<I', 0xd65f03c0))
    uc.mem_write(obj + 0x130, struct.pack('<Q', stub))
    uc.mem_write(obj + 0x50, struct.pack('<4I', sw, sh, lw, lh))
    uc.reg_write(UC_ARM64_REG_X0, obj)
    run(uc, 0xe1e38, stub)
    vp = tuple(uc.reg_read(r) & 0xffffffff for r in (UC_ARM64_REG_X0, UC_ARM64_REG_X1, UC_ARM64_REG_X2, UC_ARM64_REG_X3))
    vp = tuple(v - (1 << 32) if v >= 1 << 31 else v for v in vp)
    st = struct.unpack('<2I', uc.mem_read(obj + 0x38, 8))
    # chạm: lõi cc6a4..cc704
    uc = mk()
    from unicorn.arm64_const import UC_ARM64_REG_Q0
    q = struct.pack('<4I', sw, sh, lw, lh)
    uc.reg_write(UC_ARM64_REG_Q0, int.from_bytes(q, 'little'))
    run(uc, 0xcc6a4, 0xcc704)
    t = tuple(uc.reg_read(r) & 0xffffffff for r in (UC_ARM64_REG_X12, UC_ARM64_REG_X10, UC_ARM64_REG_X14, UC_ARM64_REG_X11))
    ok = st == (vp[2], vp[3]) and t == (vp[2], vp[3], vp[0], vp[1]) and vp[2] == lw * k and vp[3] == lh * k
    print('| %dx%d | %d | %dx%d | %d,%d,%d,%d | %d,%d,%d,%d | %s |' % (sw, sh, k, lw, lh, *vp, *t, 'OK' if ok else 'LỆCH'))
# ép NEAREST
for pname, val in ((0x2801, 9729.0), (0x2800, 9729.0), (0x2802, 33071.0), (0x2803, 9729.0)):
    uc = mk()
    obj1, obj2, args, fn = 0x10001000, 0x10002000, 0x10003000, 0x10004000
    uc.mem_write(fn, struct.pack('<I', 0xd65f03c0))        # ret (ghi nhận tại điểm dừng)
    uc.mem_write(obj1 + 8, struct.pack('<Q', obj2)); uc.mem_write(obj2 + 0x118, struct.pack('<Q', fn))
    uc.mem_write(args, struct.pack('<IIf', 0xde1, pname, val))
    uc.mem_write(obj1 + 0x200, struct.pack('<Q', args))
    uc.reg_write(UC_ARM64_REG_X0, obj1); uc.reg_write(UC_ARM64_REG_X1, obj1 + 0x200)
    uc.reg_write(UC_ARM64_REG_X30, 0x10005000)
    run(uc, 0xe3748, fn)
    s0 = struct.unpack('<f', struct.pack('<I', uc.reg_read(UC_ARM64_REG_Q0) & 0xffffffff))[0]
    print('glTexParameterf(pname=%#x, %.0f) -> %.0f' % (pname, val, s0))
