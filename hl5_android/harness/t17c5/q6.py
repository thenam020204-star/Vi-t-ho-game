"""q6: dựng Bộ sưu tập có ô đã đăng ký + ô có trong túi (ảnh coll_own_WxH_MODE.png). env W,H,MODE"""
import sys, os, struct
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l2'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't17c2'))
from qlib import *
W, Hh, MODE = int(os.environ.get('W', 400)), int(os.environ.get('H', 240)), os.environ.get('MODE', 'new')
c = Ctx(W, Hh, MODE, '/tmp/claude-0/o1'); r = c.r
def f():
    r.call(0x85fd8, c.hero, 0, 0); r.call(0x85fd8, c.hero, 0, 1)
    tbl = c.g.rd32(c.gv + 5132)
    ids = [(s - 256 if s > 127 else s) for s in bytes(c.g.uc.mem_read(c.gv + 6499, 6))]
    print('ids', ids)
    r.call(0xa321c, tbl, 0, ids[2], 1, 1)
    r.call(0x42b08, 0, 7, 1); c.step(15)
    c.key('OK', 4); c.key('DOWN', 4); c.key('RIGHT', 4); c.key('RIGHT', 4); c.step(6); c.shot('coll_own')
    if MODE == 'new': print({k: v for k, v in c.hits().items() if k in (0x90,)})
    c.key('OK', 4); c.step(6); c.shot('coll_own_ok')
    print('f', c.b8(c.top(), 0xf), hex(c.cls()))
c.fork(f, 'x')
