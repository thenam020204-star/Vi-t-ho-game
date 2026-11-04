"""q8: dò popup + RefineItem sau mỗi phím. python3 q8.py 'TYPE:k1+k2+...=shot' ...; env W H MODE ADD(m,i,n;...) MONEY
In: lớp trên cùng, trường NPC [0xc..0x30] hoặc (nếu StatePopup) loại/số nút/kết quả/+0x20/thông điệp/nút, tiền, RefineItem R[24]."""
import sys, os, struct
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l2'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't17c2'))
from qlib import *
W, Hh, MODE = int(os.environ.get('W', 400)), int(os.environ.get('H', 240)), os.environ.get('MODE', 'orig')
c = Ctx(W, Hh, MODE, os.environ.get('OUT', '/tmp/claude-0/o1')); r = c.r; g = c.g
ADD = [tuple(map(int, a.split(','))) for a in os.environ.get('ADD', '0,19,1;0,20,1;5,20,1;11,10,3').split(';') if a]
def cstr(a, n=48):
    b = bytes(g.uc.mem_read(a, n)); return b.split(b'\0')[0].decode('latin1')
def dump():
    tp = c.top(); cl = c.cls(); b = bytes(g.uc.mem_read(tp, 0x44)); money = struct.unpack('<i', bytes(g.uc.mem_read(c.hero + 6080, 4)))[0]
    rp = r.call(0x85468, c.hero); rb = bytes(g.uc.mem_read(rp, 0x30))
    if cl == 0x15e5b0:
        res = struct.unpack('<I', b[0x14:0x18])[0]
        d = dict(cls=hex(cl), type=b[0xe], nbtn=b[0x10], res=(g.uc.mem_read(res, 1)[0] if res else None), v20=struct.unpack('<i', b[0x20:0x24])[0], msg=cstr(tp + 0x24), b0=cstr(tp + 0x43c), b1=cstr(tp + 0x43c + 48),
                 w=struct.unpack('<8h', bytes(g.uc.mem_read(tp + 0x800, 16))), c28=bytes(g.uc.mem_read(tp + 0xc28, 4)).hex(), par=hex(g.rd32(tp + 8)))
    else: d = dict(cls=hex(cl), f=b[0xc:0x20].hex(), f2=b[0x20:0x44].hex())
    return d, money, rb[24:25].hex(), rb[0x11:0x12].hex()
def run(item):
    typ, _, rest = item.partition(':'); typ = int(typ); seq, _, shot = rest.partition('=')
    tbl = g.rd32(c.gv + 5132)
    for cat, id, n in ADD:
        for _ in range(n): r.call(0xa321c, tbl, cat, id, 1, 1)
    c.wr(c.hero + 6080, struct.pack('<i', int(os.environ.get('MONEY', 1234567))))
    r.call(0x42b08, 0, typ, 1); c.step(15)
    print(item, 'init', dump(), flush=True)
    for k in seq.split('+'):
        if not k: continue
        c.key(k, 4); print(item, k, dump(), flush=True)
    if shot: c.step(8); c.shot(shot)
pids = []
for it in sys.argv[1:]:
    pid = os.fork()
    if pid == 0:
        try: run(it)
        except Exception: import traceback; print(it, traceback.format_exc(), flush=True)
        os._exit(0)
    pids.append(pid)
    if len(pids) >= 4: os.waitpid(pids.pop(0), 0)
for p in pids: os.waitpid(p, 0)
