"""T14b: dpad 8 hướng, hai chế độ. check_dpad.py W H [OUT]. Đo toạ độ hero (OBJECT::GetX 0xcbc58 / GetY 0xcbc68) khi giữ từng hướng."""
import sys, os, json, struct; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t14lib import *
W, Hh = int(sys.argv[1]), int(sys.argv[2]); OUT = sys.argv[3] if len(sys.argv) > 3 else os.path.join(REPO, 'shots', 'T14'); os.makedirs(OUT, exist_ok=True)
F0 = 1300; NMD = ['N', 'NE', 'E', 'SE', 'S', 'SW', 'W', 'NW']
UX = [0, 707, 1000, 707, 0, -707, -1000, -707]; UY = [-1000, -707, 0, 707, 1000, 707, 0, -707]
r = mk(W, Hh, 'new', 'dp'); to_play(r, F0); r.step(2); g = r.g; sa = r.sym['S']
def rdS(o, n): return bytes(g.uc.mem_read(BASE + sa + o, n))
def S(): return dict(dmode=rdS(26, 1)[0], dk=rdS(28, 1)[0], fvis=rdS(29, 1)[0], want13=rdS(14, 1)[0], cx=struct.unpack('<i', rdS(84 + 52, 4))[0], cy=struct.unpack('<i', rdS(140 + 52, 4))[0], d=struct.unpack('<i', rdS(196 + 52, 4))[0], fcx=struct.unpack('<i', rdS(32, 4))[0])
def hero():
    h = r.rd(GV + 5180); return (r.call(0xcbc58, h), r.call(0xcbc68, h))
st0 = S(); res = {'size': [W, Hh], 'dpad': st0}
def run_mode(mode, ox=0, oy=0):
    out = {}
    if mode: g.uc.mem_write(BASE + sa + 26, b'\1')
    r.step(2)
    for k in range(8):
        pid = os.fork()
        rd, wr = (None, None)
        out[NMD[k]] = None
    return out
def one(mode, k):
    rd, wr = os.pipe(); pid = os.fork()
    if pid == 0:
        os.close(rd)
        try:
            if mode: g.uc.mem_write(BASE + sa + 26, b'\1')
            r.step(2); s = S()
            if mode: bx, by = W // 4, Hh // 2        # theo ngón: chạm ở bất kỳ đâu nửa trái, ngoài vùng nút gốc
            else: bx, by = s['cx'], s['cy']
            D = s['d']; x1 = bx + UX[k] * D * 35 // 100000; y1 = by + UY[k] * D * 35 // 100000
            h0 = hero(); r.touch(bx, by, up=False); r.step(1)
            if mode: pass
            r.move(x1, y1); r.step(2); sd = S()
            r.step(25); h1 = hero(); sm = S()
            shot(r, os.path.join(OUT, 'dpad_%s_%s_%dx%d.png' % ('follow' if mode else 'fixed', NMD[k], W, Hh))) if k in (0, 1) else None
            r.g.call(r.touchf, r.g.env, 0, 20, x1, y1, 0); r.step(2); sr = S()
            res_ = dict(dk=sd['dk'], dk_mid=sm['dk'], fvis=sm['fvis'], dx=h1[0] - h0[0], dy=h1[1] - h0[1], dk_after_release=sr['dk'], fvis_after=sr['fvis'])
        except Exception as e: res_ = {'error': repr(e)}
        os.write(wr, json.dumps(res_).encode()); os._exit(0)
    os.close(wr); data = b''
    while True:
        c = os.read(rd, 1 << 20)
        if not c: break
        data += c
    os.waitpid(pid, 0); return json.loads(data)
for mode in (0, 1):
    res['follow' if mode else 'fixed'] = {NMD[k]: one(mode, k) for k in range(8)}
# giữ ngón đổi hướng (theo ngón): N -> E mà không nhả
def swap():
    g.uc.mem_write(BASE + sa + 26, b'\1'); r.step(2); s = S(); bx, by = W // 4, Hh // 2; D = s['d']
    r.touch(bx, by, up=False); r.step(1); r.move(bx, by - D * 35 // 100); r.step(2); a = S()['dk']; r.move(bx + D * 35 // 100, by); r.step(2); b = S()['dk']
    r.move(bx + D * 25 // 100, by + D * 25 // 100); r.step(2); c = S()['dk']; r.move(bx, by); r.step(2); d = S()['dk']
    return dict(N=a, E=b, SE=c, deadzone=d)
rd, wr = os.pipe(); pid = os.fork()
if pid == 0:
    os.close(rd); os.write(wr, json.dumps(swap()).encode()); os._exit(0)
os.close(wr); data = b''
while True:
    c = os.read(rd, 1 << 20)
    if not c: break
    data += c
os.waitpid(pid, 0); res['swap'] = json.loads(data)
json.dump(res, open(os.path.join(OUT, 'res_dpad_%dx%d.json' % (W, Hh)), 'w'), indent=1)
exp = {'N': 1, 'NE': 9, 'E': 8, 'SE': 10, 'S': 2, 'SW': 6, 'W': 4, 'NW': 5}
for m in ('fixed', 'follow'):
    print(m, 'dk_ok', all(res[m][k].get('dk') == exp[k] for k in NMD), {k: (res[m][k].get('dk'), res[m][k].get('dx'), res[m][k].get('dy')) for k in NMD})
print('swap', res['swap'], 'expect N1 E8 SE10 dz0')
