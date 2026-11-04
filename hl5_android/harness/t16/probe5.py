#!/usr/bin/env python3
"""T16 NPC: sau skip hoạt cảnh mở đầu, dump danh sách sự kiện bản đồ (EventProc+556+20*(i-1), i=1..[+0x8c]-1) và vị trí hero. Dùng: probe5.py W H mode"""
import sys, os, struct
H = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, H)
from t16lib import Dlg, BASE, GV
W, Hh, mode = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3]
d = Dlg(W, Hh, mode); r = d.r
d.step(900 - r.fr)
while r.fr < 2400:
    d.step(1); s_ = d.state()
    if s_ and s_['st'] == 2 and s_['text'] and s_['prog'] >= 6: break
free = 0; skips = 0; log = []
while r.fr < 7000:
    d.step(1)
    if d.e75(): free = 0
    else: free += 1
    if r.fr % 500 == 0: print(r.fr, 'e75', d.e75(), 'skips', skips, flush=True); d.shot('/root/hl5work_t16/p5_%d.png' % r.fr)
    if free >= 150: break
    if d.e75() and r.fr % 40 == 0 and skips < 30:
        r.key('HASH'); skips += 1; log.append(r.fr)
print('skips', skips, log, 'free', free)
print('fr', r.fr, 'e75', d.e75())
ep = d.ep(); cnt = r.rdb(ep + 0x8c)[0]
hero = r.rd(GV + 5180)
print('count', cnt, 'hero', hex(hero), 'x', r.call(0xcbc58, hero) if hasattr(r, 'call') else None, 'y', r.call(0xcbc68, hero))
for i in range(1, min(cnt, 40)):
    b = ep + 556 + 20 * (i - 1); e = r.rdb(b, 20)
    print(i, 'x,y', struct.unpack('<hh', e[:4]), 'raw', e.hex())
