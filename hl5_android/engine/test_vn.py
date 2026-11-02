#!/usr/bin/env python3
"""Test đơn vị engine chữ Việt trong harness (Unicorn): đo/ngắt dòng/vòng PageLen với hộp hẹp nhất.
Dùng: test_vn.py p2.so vi.vfs vi.bin OUTDIR"""
import sys, os, struct
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '../harness'))
import argparse
from hl5run import Guest, BASE
from hl5libc import boot
from unicorn.arm_const import *
so, vfs, vib, out = sys.argv[1:5]
os.makedirs(out, exist_ok=True)
a = argparse.Namespace(so=so, vfs=vfs, out=out, w=400, h=240, frames=60, shot=0, keys='', touch='', storage=out + '/st', reslog=False)
os.makedirs(a.storage, exist_ok=True)
g = Guest(a); boot(g)
from hl5libc import run_frames
a.frames = 30; run_frames(g)
vb = open(vib, 'rb').read()
nw = struct.unpack_from('<H', vb, 6)[0]; ow = struct.unpack_from('<I', vb, 16)[0]; oi = struct.unpack_from('<I', vb, 12)[0]
offs = struct.unpack_from('<%dH' % (nw + 1), vb, oi)
nch = struct.unpack_from('<H', vb, 8)[0]; N = (nch + 3) & ~3
f0 = struct.unpack_from('<I', vb, 20)[0]
adv0 = vb[f0 + 8 + N: f0 + 8 + 2 * N]
def width(k):
    o = ow + offs[k]; m, n = vb[o], vb[o + 1]
    return sum(adv0[x] for x in vb[o + 2:o + 2 + n])  # chỉ gần đúng (mode 0)
code = lambda k: bytes([0xC9 + k // 94, 0xA1 + k % 94])
ws = sorted(range(nw), key=lambda k: -width(k))
S = lambda n: g.sym(n)
bf = g.heap.alloc(128)
g.uc.mem_write(bf, bytes(128)); g.uc.mem_write(bf + 68, bytes([11, 11, 9, 5]))
def call(sym, *args, n=2_000_000):
    g.uc.reg_write(UC_ARM_REG_R0, 0)
    r = g.call(S(sym), *args, max_insn=n)
    return r, g.uc.reg_read(UC_ARM_REG_PC)
ok = True
PL = '_ZN5BFont19GetTextFieldPageLenEiiPc'
LC = '_ZN5BFont12GetLineCountEPcii'
import itertools
syms = [s for s in g.syms if 'GetTextFieldPageLen' in s or 'GetLineCount' in s or 'GetWidth' in s]
print(syms)
PL = [s for s in syms if 'PageLen' in s][0]; LC = [s for s in syms if 'GetLineCount' in s][0]
GW = [s for s in syms if s.startswith('_ZN5BFont8GetWidthEPci') and s.endswith('i')]
print(PL, LC, GW)
def run(name, *args, n=3_000_000):
    ret = g.stub_top
    r = g.call(S(name), *args, max_insn=n)
    hung = g.uc.reg_read(UC_ARM_REG_PC) != ret
    return r, hung
for maxw in (1, 3, 8, 20, 60, 200):
    txt = b''.join(code(k) for k in ws[:6]) + b'\x00'
    s = g.new_str(txt)
    r1, h1 = run(PL, bf, maxw, 2, s)
    r2, h2 = run(LC, bf, s, len(txt) - 1, maxw)
    print('maxw=%3d PageLen(2 dong)=%d hang=%s | LineCount=%d hang=%s' % (maxw, r1, h1, r2, h2))
    ok &= not (h1 or h2)
# GetWidth(Pci) cua tu rong nhat vs bang
for k in ws[:3] + ws[-2:]:
    r, h = run(GW[0], bf, g.new_str(code(k) + b'\x00'), 2)
    print('k=%d width(model mode0)=%d GetWidth=%d (=W-1+1)' % (k, width(k), r))
print('OK' if ok else 'HANG')
sys.exit(0 if ok else 1)
