import sys, os, struct; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t14lib import *
r=mk(400,240,'new','dbg'); g=r.g; sa=r.sym['S']; to_play(r,1300)
def rd(o,n): return bytes(g.uc.mem_read(BASE+sa+o,n))
def dump(t): print(t,'edit',rd(16,1)[0],'hold',rd(17,1)[0],'cap',list(rd(21,4)),'pc',struct.unpack('<i',rd(28,4))[0],'t0',struct.unpack('<i',rd(32,4))[0],'cls',hex(r.cls()),'want',list(rd(1,13)),flush=True)
r.step(2); dump('pre'); cx=struct.unpack('<13i',rd(68,52))[9]; cy=struct.unpack('<13i',rd(120,52))[9]; print('pause',cx,cy)
r.touch(cx,cy,up=False); dump('after touch call'); r.step(1); dump('f1'); r.step(10); dump('f11'); r.step(20); dump('f31')
