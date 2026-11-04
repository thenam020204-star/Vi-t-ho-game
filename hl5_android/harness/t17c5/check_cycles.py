#!/usr/bin/env python3
"""L2/T17c5: 20 lần mở/đóng NPC (loại 0,1,2,7 xen kẽ, có chạm/phím + popup) đo rò heap guest. check_cycles.py W H MODE OUTDIR"""
import sys, os, json, struct, hashlib
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l2'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't17c2'))
from qlib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
c = Ctx(W, Hh, MODE, OUT); NEW = c.NEW; r = c.r; g = c.g
def f():
    cls0 = c.cls(); r.call(0x85448, c.hero)
    g.uc.mem_write(c.hero + 6080, struct.pack('<i', 100000))
    live0 = len(g.heap.sizes); top0 = g.heap.top; trace = []
    for k in range(20):
        typ = (0, 1, 2, 7, 3, 5)[k % 6]
        r.call(0x42b08, 0, typ, 1); c.step(8)
        if k % 2 == 0:
            if NEW:
                h = c.hits()
                for hid in (0x41, 0x44):
                    if hid in h: c.tap(hid, 3)
            else: c.key('RIGHT', 3); c.key('OK', 3)
            if typ in (0, 1, 2): c.key('OK', 3)           # có thể bật popup
        if typ in (3, 5, 7) and k % 2 == 1:
            c.key('OK', 3)
            if typ == 7: c.key('DOWN', 3); c.key('RIGHT', 3); c.key('OK', 3)
            c.key('RIGHT', 3)
        for _ in range(8):
            if c.cls() == cls0: break
            r.key('CLR'); r.step(5)
        trace.append(hex(c.cls()))
        if k == 5: live4 = len(g.heap.sizes) - live0; top4 = g.heap.top - top0
    r.step(10)
    return {'live': len(g.heap.sizes) - live0, 'top': g.heap.top - top0, 'live4': live4, 'top4': top4, 'back_ok': all(t == hex(cls0) for t in trace), 'cls0': hex(cls0)}
c.fork(f, 'cycles')
