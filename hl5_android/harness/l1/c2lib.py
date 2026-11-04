"""T17c1: thư viện harness chung (boot -> vào game -> fork kịch bản). Dùng bởi check_c1.py và công cụ dò."""
import sys, os, struct, argparse
H = os.path.dirname(os.path.abspath(__file__)); REPO = os.path.abspath(os.path.join(H, '..', '..'))
sys.path.insert(0, os.path.join(H, '..')); sys.path.insert(0, os.path.join(H, '..', 't17c1'))
import hl5run
from hl5run import BASE
from hl5libc import boot, screen_rgb, save_png, KEYS, FRAME_MS

ROUTE = ('460:OK,560:OK,640:OK,720:OK,800:OK,880:OK,960:HASH,970:OK,1000:HASH,1010:OK,1040:HASH,1050:OK,1080:HASH,1090:OK,'
         '1120:HASH,1130:OK,1160:HASH,1170:OK,1200:HASH,1210:OK,1240:HASH,1250:OK,1280:HASH,1290:OK,1320:HASH,1330:OK,1360:HASH,1370:OK,'
         '1400:HASH,1410:OK,1440:HASH,1450:OK,1480:HASH,1490:OK,1520:HASH,1530:OK,1560:HASH,1570:OK,1600:HASH,1610:OK,1640:HASH,1650:OK,'
         '1680:HASH,1690:OK,1720:HASH,1730:OK,1760:HASH,1770:OK')

class Run:
    def __init__(self, so, vfs, w, h, tag, wd):
        d = os.path.join(wd, 'run_' + tag); os.makedirs(d, exist_ok=True)
        a = argparse.Namespace(so=so, vfs=vfs, out=d, w=w, h=h, frames=0, shot=0, keys='', touch='', storage=os.path.join(d, 'st'), reslog=False)
        import shutil; shutil.rmtree(a.storage, ignore_errors=True)
        os.makedirs(a.storage, exist_ok=True)
        self.g = hl5run.Guest(a); boot(self.g); self.w, self.h = w, h
        S = self.g.sym; self.loop = S('Java_co_kr_eamobile_CletEntry_nativeLoop'); self.keyf = S('Java_co_kr_eamobile_CletEntry_nativeKeyHandleEvent')
        self.touchf = S('Java_co_kr_eamobile_CletEntry_nativeTouchHandleEvent'); self.fr = 0
    def key(self, k):
        c = KEYS[k] if k in KEYS else int(k)
        self.g.call(self.keyf, self.g.env, 0, 2, c & 0xffffffff, 0); self.g.call(self.keyf, self.g.env, 0, 3, c & 0xffffffff, 0)
    def touch(self, x, y, up=True):
        self.g.call(self.touchf, self.g.env, 0, 19, x, y, 0)
        if up: self.g.call(self.touchf, self.g.env, 0, 20, x, y, 0)
    def move(self, x, y): self.g.call(self.touchf, self.g.env, 0, 21, x, y, 0)
    def step(self, n=1):
        for _ in range(n):
            self.g.frame = self.fr; self.g.vclock += FRAME_MS
            self.g.call(self.loop, self.g.env, 0); self.fr += 1
    def shot(self): return screen_rgb(self.g)
    def top(self): return self.g.rd32(BASE + 0x16a238 + 0x244)
    def rd(self, a): return self.g.rd32(BASE + a)
    def rdb(self, a, n=1): return bytes(self.g.uc.mem_read(a, n))
    def cls(self):   # id lớp state trên cùng theo vtable
        t = self.top()
        return self.g.rd32(t) - BASE if t else 0
    def call(self, addr, *args): return self.g.nested_call(BASE + addr, *args)

def route(r, upto, keys=ROUTE):
    ev = {}
    for it in keys.split(','):
        f, k = it.split(':'); ev.setdefault(int(f), []).append(k)
    while r.fr < upto:
        for k in ev.get(r.fr, []): r.key(k)
        r.step(1)
