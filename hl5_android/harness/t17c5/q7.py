"""q7: dò Tôi luyện/Orb/Chế biến: thêm vật phẩm vào túi, chạy chuỗi phím, in diff trường + chụp. python3 q7.py TYPE 'k1+k2=shot' ... ; env W H MODE"""
import sys, os, struct
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l2'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't17c2'))
from qlib import *
W, Hh, MODE = int(os.environ.get('W', 400)), int(os.environ.get('H', 240)), os.environ.get('MODE', 'orig')
c = Ctx(W, Hh, MODE, os.environ.get('OUT', '/tmp/claude-0/o1')); r = c.r
ADD = [tuple(map(int, a.split(','))) for a in os.environ.get('ADD', '0,19,1;0,20,1;5,20,1;11,10,3').split(';')]
def run(item):
    typ, _, rest = item.partition(':'); typ = int(typ); seq, _, shot = rest.partition('=')
    tbl = c.g.rd32(c.gv + 5132)
    for cat, id, n in ADD:
        for _ in range(n): r.call(0xa321c, tbl, cat, id, 1, 1)
    c.wr(c.hero + 6080, struct.pack('<i', 1234567))
    r.call(0x42b08, 0, typ, 1); c.step(15)
    tp = c.top(); b0 = bytes(c.g.uc.mem_read(tp, 0x44)); out = []
    for k in seq.split('+'):
        if not k: continue
        c.key(k, 4); tp2 = c.top(); b = bytes(c.g.uc.mem_read(tp2, 0x44))
        if tp2 == tp: out.append((k, {hex(i): (b0[i], b[i]) for i in range(0xc, 0x44) if b0[i] != b[i]}))
        else: out.append((k, 'TOP', hex(c.cls())))
        rp = r.call(0x85468, c.hero); rb = bytes(c.g.uc.mem_read(rp, 0x30)); out[-1] = out[-1] + ({hex(i): rb[i] for i in range(0x30) if rb[i] not in (0,)}, 'm', struct.unpack('<i', bytes(c.g.uc.mem_read(c.hero + 6080, 4)))[0])
        tp, b0 = tp2, b
    if shot: c.step(8); c.shot(shot)
    rp = r.call(0x85468, c.hero); out.append(('R', bytes(c.g.uc.mem_read(rp, 0x30)).hex()))
    return out
pids = []
for it in sys.argv[1:]:
    pid = os.fork()
    if pid == 0:
        try: res = run(it)
        except Exception: import traceback; res = traceback.format_exc()
        print(it, res, flush=True); os._exit(0)
    pids.append(pid)
    if len(pids) >= 4: os.waitpid(pids.pop(0), 0)
for p in pids: os.waitpid(p, 0)
