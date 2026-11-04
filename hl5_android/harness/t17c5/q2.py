"""q2.py 'T:K1+K2+...[=shotname]' ... : mỗi mục = 1 kịch bản (fork) trên NPC state T (tiền 100000); in diff trường sau từng phím; =name chụp ảnh. Chung 1 lần boot.
 MODE qua env MODE (orig|new), cỡ qua env W/H. Phím đặc biệt: m<N> = đặt tiền N; t<x>,<y> = chạm."""
import sys, os, json, struct
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l2'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't17c2'))
from qlib import *
W, Hh, MODE, OUT = int(os.environ.get('W', 600)), int(os.environ.get('H', 270)), os.environ.get('MODE', 'orig'), os.environ.get('OUT', '/tmp/claude-0/o1')
c = Ctx(W, Hh, MODE, OUT)
def snap():
    tp = c.top(); return tp, bytes(c.g.uc.mem_read(tp, 0x44))
def run(item):
    spec, _, shot = item.partition('=')
    t, _, ks = spec.partition(':'); t = int(t); seq = ks.split('+') if ks else []
    c.wr(c.hero + 6080, struct.pack('<i', 100000))
    c.r.call(0x42b08, 0, t, 1); c.step(15)
    tp0, b0 = snap(); out = []
    for k in seq:
        if k[0] == 'm' and k[1:].isdigit(): c.wr(c.hero + 6080, struct.pack('<i', int(k[1:]))); continue
        if k == 'dumpR':
            rp = c.r.call(0x85468, c.hero); out.append(('R', hex(rp), bytes(c.g.uc.mem_read(rp, 0x30)).hex())); continue
        if k[0] == 't': x, y = k[1:].split(','); c.touch_xy(int(x), int(y), 4)
        else: c.key(k, 4)
        tp, b = snap()
        if tp == tp0: d = {hex(i): (b0[i], b[i]) for i in range(0xc, 0x44) if b0[i] != b[i]}
        else: d = {'TOP': hex(c.cls()), 'f': list(b[0xc:0x14]), 'w': [struct.unpack('<i', b[o:o+4])[0] for o in range(0x14, 0x30, 4)], 'par': hex(struct.unpack('<I', b[8:12])[0]), 'old': hex(tp0)}
        out.append((k, d)); tp0, b0 = tp, b
    if shot: c.step(8); c.shot(shot)
    out.append(('money', struct.unpack('<i', bytes(c.g.uc.mem_read(c.hero + 6080, 4)))[0]))
    return out
items = sys.argv[1:]
pids = []
for it in items:
    pid = os.fork()
    if pid == 0:
        try: res = run(it)
        except Exception as e:
            import traceback; res = traceback.format_exc()
        print(it, res, flush=True); os._exit(0)
    pids.append(pid)
    if len(pids) >= 4: os.waitpid(pids.pop(0), 0)
for p in pids: os.waitpid(p, 0)
