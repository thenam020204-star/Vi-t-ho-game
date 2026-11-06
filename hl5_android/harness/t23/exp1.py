"""T23 thí nghiệm: mô phỏng phía harness bản vá MC_grpFillRect (emu.enable_fillrect) -> so byte-khối + ảnh trùng từng byte. exp1.py W H"""
import sys, json, hashlib; sys.path.insert(0, '/home/user/Vi-t-ho-game/hl5_android/harness/t23')
from t23lib import *
import emu
W, Hh = int(sys.argv[1]), int(sys.argv[2])
c = mk(W, Hh, 0); r = c.r; T = Timeline(c); T.addroute(0, 960)
OUT = os.path.expanduser('~/hl5work_t23/out')
def go(fr):
    while r.fr < fr: T.frame()
def measure(n, key):
    res = []
    for i in range(n):
        L = T.L; L.start(); fr = r.fr; T.frame(); L.stop()
        res.append((fr, L.total, hashlib.md5(r.shot()).hexdigest()[:10]))
    return res
def fork_run(name, fn):
    pid = os.fork()
    if pid: os.waitpid(pid, 0); return
    try: res = fn()
    except Exception as e:
        import traceback; res = {'ERR': traceback.format_exc()}
    json.dump(res, open(OUT + '/exp1_%s_%dx%d.json' % (name, W, Hh), 'w')); os._exit(0)
def scen(fr, n, name):
    go(fr)
    fork_run(name + '_base', lambda: measure(n, 'b'))
    def withemu():
        emu.enable_fillrect(c); m = measure(n, 'e'); return dict(m=m, st=emu.stats)
    fork_run(name + '_emu', withemu)
scen(100, 4, 'title')
scen(895, 12, 'cut_start')
scen(1210, 6, 'cut_mid')
