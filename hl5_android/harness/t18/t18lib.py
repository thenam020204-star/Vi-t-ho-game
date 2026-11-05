import sys, os; sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't14'))
from t14lib import *
import numpy as np
from unicorn import UC_HOOK_CODE
from unicorn.arm_const import UC_ARM_REG_R1, UC_ARM_REG_LR
def regions(r):
    top = r.g.heap.top
    return [(0x10000000, 0x10171000), (0x20000000, (top + 0xfff) & ~0xfff), (0x60000000, 0x60400000), (0x70000000, 0x70400000)]
def snap(r):
    return [(b, np.frombuffer(bytes(r.g.uc.mem_read(b, e - b)), np.uint8)) for b, e in regions(r)]
def diff(s0, s1, excl=()):
    out = []
    for (b, a), (_, c) in zip(s0, s1):
        n = min(len(a), len(c)); idx = np.nonzero(a[:n] != c[:n])[0]
        if not len(idx): continue
        ad = idx + b; keep = np.ones(len(ad), bool)
        for lo, hi in excl: keep &= ~((ad >= lo) & (ad < hi))
        ad = ad[keep]
        if not len(ad): continue
        st = ad[0]; pv = ad[0]
        for x in ad[1:]:
            if x - pv > 8: out.append((int(st), int(pv - st + 1))); st = x
            pv = x
        out.append((int(st), int(pv - st + 1)))
    return out
def rb(s, addr, n):
    for b, a in s:
        if b <= addr < b + len(a): return bytes(a[addr - b: addr - b + n])
