"""T14d: harness đi chéo. Boot -> vào cảnh chơi, rồi fork mỗi kịch bản (giữ phím trực tiếp qua nativeKeyHandleEvent, không qua HUD).
Dùng: from d8lib import *; r = boot_play(so, vfs, W, H); trace = scen(r, [(0,'D','UP'),(1,'D','RIGHT')], 30)"""
import os, sys, json
H = os.path.dirname(os.path.abspath(__file__)); REPO = os.path.abspath(os.path.join(H, '..', '..'))
sys.path.insert(0, os.path.expanduser("~/hl5work/proj/HL4_project/img5"))
sys.path.insert(0, os.path.join(H, '..', 't17c1')); sys.path.insert(0, os.path.join(H, '..'))
from c1lib import Run, ROUTE, BASE
from hl5libc import save_png, KEYS
GV = 0x16a238
WK = os.path.expanduser(os.environ.get('WORK', '~/hl5work_t14d'))
def boot_play(so, vfs, W, Hh, f0=1300, tag='t14d'):
    wd = WK + '/run'; os.makedirs(wd, exist_ok=True)
    r = Run(so, vfs, W, Hh, '%s_%dx%d_%d' % (tag, W, Hh, os.getpid()), wd)
    ev = {}
    for it in ROUTE.split(','):
        f, k = it.split(':'); ev.setdefault(int(f), []).append(k)
    while r.fr < f0:
        for k in ev.get(r.fr, []): r.key(k)
        r.step(1)
    r.step(2); return r
def kdown(r, name): r.g.call(r.keyf, r.g.env, 0, 2, KEYS[name] & 0xffffffff, 0)
def kup(r, name): r.g.call(r.keyf, r.g.env, 0, 3, KEYS[name] & 0xffffffff, 0)
def hero(r): return r.rd(GV + 5180)
def pos(r):
    h = hero(r); return (r.call(0xcbc58, h), r.call(0xcbc68, h))
def hstate(r):
    h = hero(r); b = r.rdb(h + 0x1c, 1)[0]; d = r.rdb(h + 0x2d, 1)[0]; m = r.rdb(h + 0x2c, 1)[0]
    f = r.rdb(h + 7429, 4)
    return dict(ms=b, dir=d, motion=m, flags=list(f))
def scen(r, events, nframes, rec_every=1):
    """events: [(frame, 'D'|'U', KEYNAME)]; chạy trong tiến trình con; trả danh sách (frame,x,y,ms,dir,motion,flags)."""
    rd, wr = os.pipe(); pid = os.fork()
    if pid == 0:
        os.close(rd)
        try:
            out = []; ev = {}
            for f, t, k in events: ev.setdefault(f, []).append((t, k))
            for f in range(nframes + 1):
                for t, k in ev.get(f, []): (kdown if t == 'D' else kup)(r, k)
                if f % rec_every == 0:
                    x, y = pos(r); s = hstate(r); out.append([f, x, y, s['ms'], s['dir'], s['motion'], s['flags']])
                r.step(1)
            res = out
        except Exception as e:
            import traceback; res = {'error': traceback.format_exc()}
        os.write(wr, json.dumps(res).encode()); os._exit(0)
    os.close(wr); data = b''
    while True:
        c = os.read(rd, 1 << 20)
        if not c: break
        data += c
    os.waitpid(pid, 0); return json.loads(data)
def shot(r, path): save_png(path, r.w, r.h, r.shot())
