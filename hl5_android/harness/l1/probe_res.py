import sys, os, json
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
W, Hh = 600, 270
MODE = sys.argv[1] if len(sys.argv) > 1 else 'orig'
c = Ctx(W, Hh, MODE, os.path.expanduser('~/hl5work_l1/p_res')); r = c.r; gv = c.gv
def f(): t = c.top(); return [c.s8(t, o) for o in range(0xc, 0x14)]
def gvs():
    return {'m6457': c.s8(gv, 6457), 'v6460': c.u32(gv + 6460), 'v6464': c.u32(gv + 6464), 'v6468': c.u32(gv + 6468), 'v5156': c.u32(gv + 5156), 'v5160': c.u32(gv + 5160), 'v5164': c.u32(gv + 5164)}
def run(mode):
    c.wr(gv + 6457, [mode]); t = c.top(); r.call(0xda410, t, 2, 0)
    for o, v in ((6460, 111), (6464, 222), (6468, 333), (5156, 444), (5160, 555), (5164, 666)): c.wr(gv + o, list(v.to_bytes(4, 'little')))
    c.step(50); c.shot('res_m%d_sub1' % mode); print(mode, 'sub1', f(), flush=True)
    c.key('OK', 3); print(mode, 'afterOK', f(), flush=True)
    for i in range(0, 120):
        c.step(1)
        if i in (5, 15, 25, 35, 45, 60, 100): c.shot('res_m%d_r%d' % (mode, i)); print(mode, i, f(), flush=True)
    c.key('OK', 5); print(mode, 'OK2', f(), hex(c.cls()), flush=True)
for mode in (1, 2, 3):
    pid = os.fork()
    if pid: os.waitpid(pid, 0); continue
    try: run(mode)
    except Exception as e: print('ERR', e, flush=True)
    os._exit(0)
