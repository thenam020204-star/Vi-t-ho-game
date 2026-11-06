"""T25: nội suy BẬT vs TẮT, bước 16 ms (vsync), cùng ROUTE (khung phím x3): so trạng thái logic ở mỗi tick thật.
lock_interp.py DEVDIR W H NSTEP on|off OUT.json"""
import sys, os, json, hashlib, struct, re
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'l1'))
from c2lib import Run, ROUTE, BASE
D, W, Hh, N, MODE, OUT = sys.argv[1], int(sys.argv[2]), int(sys.argv[3]), int(sys.argv[4]), sys.argv[5], sys.argv[6]
LIMIT = int(os.environ.get("LIMIT", "100000"))
sym = {}
for l in open(D + '/menu/menu.sym'):
    p = l.split()
    if len(p) == 3: sym[p[2]] = int(p[0], 16)
r = Run(D + '/dev.so', D + '/data.vfs.mp3', W, Hh, 'li_%s_%d' % (MODE, os.getpid()), os.path.dirname(OUT))
g = r.g; GV = BASE + 0x16a238
def rd(a, n): return bytes(g.uc.mem_read(a, n))
def u32(a): return g.rd32(a)
ev = {}
for it in ROUTE.split(','):
    f, k = it.split(':'); ev.setdefault(int(f), []).append(k)   # theo SỐ TICK (route gốc 50 ms/bước = 1 tick/bước)
FR = 16
def step():
    g.frame = r.fr; g.vclock += FR; g.call(r.loop, g.env, 0); r.fr += 1
def state():
    ep = u32(GV + 5204); mp = u32(GV + 5200); hero = u32(GV + 5180)
    h = hashlib.md5()
    if ep: h.update(rd(ep, 1788))
    if mp: h.update(rd(mp + 0xb8, 0x30)); h.update(rd(mp + 0x14a, 4)); h.update(rd(mp + 0x160, 1)); h.update(rd(mp + 108, 8))
    if hero: h.update(rd(hero + 4, 8)); h.update(rd(hero + 0xf0, 4))
    objs = []
    for l in range(5):
        L = u32(GV + 5536 + 4 * l)
        node = u32(L) if L else 0; k = 0
        while node and k < 1024:
            o = u32(node + 8)
            if o: h.update(rd(o + 4, 8)); objs.append(o)
            node = u32(node + 4); k += 1
    return h.hexdigest()[:12], len(objs)
dump = []; seq = []; last = None; nmid = 0; ntick = 0; done = set()
for i in range(N):
    if ntick > LIMIT: break
    if ntick not in done:
        done.add(ntick)
        for k in ev.get(ntick, []): r.key(k)
    if r.fr == 30 and MODE == 'off': g.uc.mem_write(BASE + sym['interp_st'], struct.pack('<I', 1))
    step()
    t0 = u32(GV + 7584)
    if t0 != last:
        last = t0; ntick += 1; s = state()
        if 893 <= ntick <= 900:
            ep = u32(GV + 5204); mp = u32(GV + 5200)
            dump.append({'t': ntick, 'ep': rd(ep, 1788).hex(), 'map': rd(mp, 4284).hex(), 'hero': rd(u32(GV + 5180), 0x400).hex()})
        seq.append([r.fr, s[0], s[1], g.uc.mem_read(u32(GV + 5204) + 0x75, 1)[0] if u32(GV + 5204) else -1])
st = struct.unpack('<21I', rd(BASE + sym['interp_st'], 84))
json.dump({'seq': seq, 'interp_st': st, 'dump': dump}, open(OUT, 'w'))
print(MODE, 'ticks', len(seq), 'flag', st[0], 'n_mid', st[-3], 'n_tick_lag', st[-2], 'n_skip', st[-1])
