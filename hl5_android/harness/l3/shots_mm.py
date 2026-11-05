#!/usr/bin/env python3
"""L3: chụp ảnh MainMenu mới. shots_mm.py W H OUTDIR"""
import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l3'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
W, Hh, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3]
c = Ctx(W, Hh, 'new', OUT, boot_to=10); r = c.r
r.step(700); r.key('OK'); r.step(10)
T6 = [0, 1, 4, 5, 6, 8]
c.shot('mm_grid')
def sub(fn, nm):
    pid = os.fork()
    if pid: os.waitpid(pid, 0); return
    fn(); c.shot(nm); os._exit(0)
def slot(): c.tap(0x10, 5)
def cls(): slot(); c.tap(0x20, 5); c.tap(0x31, 4)
def pop(): cls(); c.tap(0x31, 5); c.step(5)
def opt(): c.tap(0x10 + 2, 5); c.key('DOWN', 2)
def opt9():
    c.tap(0x12, 5)
    for _ in range(9): c.key('DOWN', 2)
def quit_(): c.tap(0x15, 6)
def expert(): c.tap(0x11, 5)
for fn, nm in ((slot, 'mm_slot'), (cls, 'mm_class'), (pop, 'mm_classpop'), (opt, 'mm_opt'), (opt9, 'mm_opt_res'), (quit_, 'mm_quit'), (expert, 'mm_expert')): sub(fn, nm)
