import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18b import *
W, Hh = 400, 240
r = mkd(W, Hh, 'dev', 'c9'); to_play(r, 1300); g = r.g
lcd = g.rd32(BASE + 0x16a210)
fb = r.call(0x4e4f8, lcd); off = r.call(0x4e508, lcd)
print('FB', hex(fb), 'FBOff', hex(off), 'FB end', hex(fb + W * Hh * 2), 'Off end', hex(off + W * Hh * 2), 'hostfb', hex(r.hostfb))
