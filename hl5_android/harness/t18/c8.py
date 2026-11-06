"""T18c-2b: ai ghi các địa chỉ khác biệt (heap) trong khung trung gian? c8.py W H"""
import sys, os, subprocess, bisect; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18b import *
from unicorn import UC_HOOK_MEM_WRITE
W, Hh = int(sys.argv[1]), int(sys.argv[2])
r = mkd(W, Hh, 'dev', 'c8'); to_play(r, 1300); st_wr(r, 'flag', 2); uc = r.g.uc
nm = subprocess.run(['llvm-nm', '-C', '--defined-only', os.path.expanduser('~/hl5work/apk/assets/libHeroesLore5.so')], capture_output=True, text=True).stdout.splitlines()
syms = sorted((int(l.split(' ', 2)[0], 16), l.split(' ', 2)[2]) for l in nm if len(l.split(' ', 2)) == 3); keys = [s[0] for s in syms]
def sy(pc): i = bisect.bisect_right(keys, pc - BASE) - 1; return syms[i][1][:60] if i >= 0 else hex(pc)
T = [0x200015b4, 0x2003d3d0, 0x203623a8, 0x203623c8, 0x203623e0, 0x2004afa0]
hits = {}; phase = ['none']
def hw(uc, access, addr, size, value, ud):
    pc = uc.reg_read(UC_ARM_REG_PC); k = (phase[0], hex(addr - addr % 4), sy(pc)); hits[k] = hits.get(k, 0) + 1
for a in T: uc.hook_add(UC_HOOK_MEM_WRITE, hw, begin=a, end=a + 15)
ev(r, 'RIGHT', True)
for i in range(30):
    m0 = st_rd(r, 'n_mid'); t0 = r.ticks; phase[0] = 'loop'; vloop(r)
    phase[0] = 'mid' if (st_rd(r, 'n_mid') > m0 and r.ticks == t0) else 'tick'
    # (fase asignada tras: reetiquetar)
hits.clear(); phase[0] = 'x'
for i in range(12):
    # fase conocida antes: probamos hooking por el contador
    m0 = st_rd(r, 'n_mid'); t0 = r.ticks
    h_before = dict(hits); phase[0] = '?'; vloop(r)
    kind = 'mid' if (st_rd(r, 'n_mid') > m0 and r.ticks == t0) else 'tick'
    for k, v in list(hits.items()):
        if k[0] == '?': hits[(kind,) + k[1:]] = hits.get((kind,) + k[1:], 0) + v; hits[k] = 0
for k, v in sorted(hits.items()):
    if v: print(k, v)
