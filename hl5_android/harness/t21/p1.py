"""T21-p1: dò camera ở mép map. Giữ một hướng nhiều tick, ghi hero x,y (OBJECT+4/+8) và trường Map (+108..+114, +184..+0xe4). p1.py W H DIR(LEFT/RIGHT/UP/DOWN) N"""
import sys, os, struct; sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't18'))
from t18lib import *
from hl5libc import KEYS
W, Hh, D, N = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], int(sys.argv[4])
r = mk(W, Hh, 'orig', 't21'); to_play(r, 1300); g = r.g
gvb = BASE + 0x16a238
def rd(a, n): return bytes(g.uc.mem_read(a, n))
def s16(a): return struct.unpack('<h', rd(a, 2))[0]
def s32(a): return struct.unpack('<i', rd(a, 4))[0]
m = g.rd32(gvb + 5200); h = g.rd32(gvb + 5180)
print('map', hex(m), 'hero', hex(h), 'W,H map', s16(m + 112), s16(m + 114), 'f108,110', s16(m + 108), s16(m + 110))
def ev(c, d): g.call(r.keyf, g.env, 0, d, c & 0xffffffff, 0)
ev(KEYS[D], 2)
for t in range(N):
    r.step(1)
    hx, hy = s32(h + 4), s32(h + 8)
    f = [s32(m + o) for o in range(0xc0, 0xe8, 4)]
    print(t, 'hero', hx, hy, 'spd', s16(m + 184), s16(m + 186), 'bc', rd(m + 0xbc, 1)[0], 'c0..e4', f, 'scr', hx - f[4], hy - f[5])
ev(KEYS[D], 3)
