"""T20-V1: lockstep base (không T20) vs dev (có fbcopy). Mỗi flush so ảnh FB đầy đủ; mỗi 30 vòng + cuối so bộ nhớ .so/heap/misc/stack (loại từ hook).
  v1.py W H [vòng_chơi=170] [boot=1300] [interp=0]      interp=1: ghi interp.txt=1 ở cả hai (cờ nội suy bật, vloop 16 ms thay r.step)"""
import sys, os, time; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t20lib import *
W, Hh = int(sys.argv[1]), int(sys.argv[2]); NP = int(sys.argv[3]) if len(sys.argv) > 3 else 170; NB = int(sys.argv[4]) if len(sys.argv) > 4 else 1300
INT = int(sys.argv[5]) if len(sys.argv) > 5 else 0
rs = [mkd(W, Hh, d, 'v1') for d in ('base', 'dev')]
if INT:
    for r in rs: open(os.path.join(r.g.a.storage, 'interp.txt'), 'w').write('1')
EX = hookaddrs(None, None); t0 = time.time()
bad = [0, 0]; nfl = 0; heapbad = 0; phase_cnt = {}
def cmpfb(tag):
    a, b = fbimg2(rs[0], W, Hh), fbimg2(rs[1], W, Hh)
    if not (a == b).all(): bad[0] += 1; print('  FB KHÁC', tag, int((a != b).sum()), 'điểm'); return False
    return True
def cmpmem(tag):
    fb = rs[0].g.rd32(rs[0].hostfb + 0x10); d = memdiff(rs[0], rs[1], EX, (fb, fb + W * Hh * 2))
    s = {k: v[0] for k, v in d.items()}; fbn = d['heap'][3] if False else [v[3] for v in d.values()]
    print('  bộ nhớ', tag, 'byte khác (so/heap/misc/stack):', s, [v[3] for v in d.values()], 'dải heap/so:', d['heap'][1][:3], d['so'][1][:3], 'heap ở', len(rs[0].g.heap.sizes), len(rs[1].g.heap.sizes), rs[0].g.heap.top, rs[1].g.heap.top)
    return s
def advance(fn_each, tag):
    global nfl
    f0 = rs[0].flushes
    for r in rs: fn_each(r)
    if rs[0].flushes != rs[1].flushes or rs[0].ticks != rs[1].ticks: print('  LỆCH flush/tick', tag, rs[0].flushes, rs[1].flushes, rs[0].ticks, rs[1].ticks); bad[1] += 1
    if rs[0].flushes != f0 or (rs[0].flushes and rs[0].hostfb): 
        if rs[0].flushes != f0: nfl += 1; cmpfb(tag)
# ---- phase boot (ROUTE tới NB) ----
evs = {}
for it in ROUTE.split(','):
    f, k = it.split(':'); evs.setdefault(int(f), []).append(k)
step = (lambda r: vloop(r)) if INT else (lambda r: r.step(1))
c0 = fbst(rs[1])
for fr in range(NB):
    for r in rs:
        for k in evs.get(fr, []): r.key(k)
    advance(step, 'boot%d' % fr)
    if fr % 300 == 299: print('boot', fr + 1, 'flush', rs[0].flushes, 'FB khác', bad, 'fbcopy', fbst(rs[1]), '%ds' % (time.time() - t0)); sys.stdout.flush()
c1 = fbst(rs[1]); print('BOOT xong: FB khác', bad, 'flush', nfl, 'fbcopy', c1)
cmpmem('sau boot')
# ---- phase chơi ----
S = {5: ('RIGHT', 1), 45: ('RIGHT', 0), 50: ('OK', 1), 52: ('OK', 0), 56: ('OK', 1), 58: ('OK', 0), 62: ('LEFT', 1), 100: ('LEFT', 0), 104: ('DOWN', 1), 114: ('DOWN', 0),
     118: ('RIGHT', 1), 120: ('DOWN', 1), 135: ('RIGHT', 0), 136: ('DOWN', 0), 140: ('UP', 1), 160: ('UP', 0)}
def menu_open(n):
    for r in rs: r.call(0x428bc, 0, 3, n)
for i in range(NP):
    for r in rs:
        if i in S: ev(r, S[i][0], S[i][1])
    advance(step, 'play%d' % i)
    if i % 40 == 39: cmpmem('play%d' % i)
c2 = fbst(rs[1]); print('CHƠI xong: FB khác', bad, 'flush', nfl, 'fbcopy', {k: c2[k] - c1[k] for k in c2})
# ---- phase menu trong game ----
for n in (0, 1, 0):
    menu_open(n)
    for i in range(12):
        advance(step, 'menu%d_%d' % (n, i))
    for k in ('DOWN', 'RIGHT', 'OK', 'CLR'):
        for r in rs: r.key(k)
        for i in range(4): advance(step, 'menu%d_%s%d' % (n, k, i))
for k in ('CLR', 'CLR'):
    for r in rs: r.key(k)
    for i in range(6): advance(step, 'back')
for i in range(30):
    for r in rs:
        if i == 3: ev(r, 'RIGHT', 1)
        if i == 25: ev(r, 'RIGHT', 0)
    advance(step, 'after%d' % i)
c3 = fbst(rs[1]); print('MENU xong: FB khác', bad, 'flush', nfl, 'fbcopy', {k: c3[k] - c2[k] for k in c3})
cmpmem('cuối')
print('interp: n_mid base/dev', [st_rd(r, 'n_mid') for r in rs], 'n_tick_lag', [st_rd(r, 'n_tick_lag') for r in rs], 'flag', [st_rd(r, 'flag') for r in rs])
print('KẾT: FB khác ở %d flush / %d (lệch nhịp %d); tổng fbcopy %s; %ds' % (bad[0], nfl, bad[1], c3, time.time() - t0))
