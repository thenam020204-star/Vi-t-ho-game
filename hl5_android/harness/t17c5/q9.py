"""q9: in công thức Chế biến của hero (shop = [0xc]) trong harness: python3 q9.py"""
import sys, os, struct
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l2'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't17c2'))
from qlib import *
c = Ctx(400, 240, 'orig', '/tmp/claude-0/o1'); r = c.r; g = c.g
def s8(v): return v - 256 if v > 127 else v
def run():
    r.call(0x42b08, 0, 6, 1); c.step(10)
    tp = c.top(); print('shop [0xc]', struct.unpack('<h', bytes(g.uc.mem_read(tp + 0xc, 2)))[0])
    for i in range(16):
        p = r.call(0x85474, c.hero, i); b = bytes(g.uc.mem_read(p + 0x11c, 0x14))
        print(i, 'res', s8(b[0x126 - 0x11c]), s8(b[0x127 - 0x11c]), 'n', b[0], 'mats', [(s8(b[1 + k]), s8(b[4 + k]), b[7 + k]) for k in range(3)])
def run2():
    run(); sys.stdout.flush()
c.fork(run2, "q9")
