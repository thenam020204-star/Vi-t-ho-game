"""T18c-2: cờ BẬT vs TẮT cùng chuỗi phím (theo chỉ số tick): trạng thái logic (hero, bộ nhớ .so/heap trừ FB) sau mỗi N tick. c2.py W H [ticks] [dev]"""
import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18b import *
W, Hh = int(sys.argv[1]), int(sys.argv[2]); N = int(sys.argv[3]) if len(sys.argv) > 3 else 130; dev = sys.argv[4] if len(sys.argv) > 4 else 'dev'
rs = [mkd(W, Hh, dev, 'c2off'), mkd(W, Hh, dev, 'c2on')]
for r in rs: to_play(r, 1300)
OO = os.environ.get('OFFOFF')
if not OO: st_wr(rs[1], 'flag', 2)
SC = {5: ('RIGHT', 1), 40: ('RIGHT', 0), 42: ('OK', 1), 45: ('OK', 0), 60: ('LEFT', 1), 100: ('LEFT', 0), 110: ('OK', 1), 112: ('OK', 0)}
def regs(r): top = r.g.heap.top; return [(0x10000000, 0x10171000), (0x20000000, (top + 0xfff) & ~0xfff)]
def snapr(r): return [(b, np.frombuffer(bytes(r.g.uc.mem_read(b, e - b)), np.uint8)) for b, e in regs(r)]
base = [r.ticks for r in rs]; hs = [[], []]; heap = [[], []]; allbad = {}
for n in range(N):
    for k, r in enumerate(rs):
        if n in SC: ev(r, SC[n][0], SC[n][1])
        while r.ticks < base[k] + n + 1: vloop(r, 19 if (OO and k) else VS)
        hs[k].append(hero(r)); heap[k].append((len(r.g.heap.sizes), r.g.heap.top))
    if n % 10 == 9 or n == N - 1:
        s0, s1 = snapr(rs[0]), snapr(rs[1]); fb = rs[0].hostfb; ex = [(fb, fb + W * Hh * 2 + 0x100)]
        d = diff(s0, s1, ex)
        for x, l in d:
            allbad[x] = allbad.get(x, 0) + 1
            if os.environ.get('DETAIL'): print('   ', hex(x), l, 'off', bytes(rs[0].g.uc.mem_read(x, min(l, 16))).hex(), 'on', bytes(rs[1].g.uc.mem_read(x, min(l, 16))).hex())
        print('tick', n + 1, 'hero', hs[0][-1], hs[1][-1], 'heap', heap[0][-1], heap[1][-1], 'khác bộ nhớ:', [(hex(x), l) for x, l in d[:10]], len(d), 'mid', st_rd(rs[1], 'n_mid'), 'lagtick', st_rd(rs[1], 'n_tick_lag'))
print('hero lệch ở', sum(a != b for a, b in zip(*hs)), '/', N, '; heap lệch', sum(a != b for a, b in zip(*heap)))
print('địa chỉ bộ nhớ khác (số lần):', {hex(a): c for a, c in sorted(allbad.items())})
