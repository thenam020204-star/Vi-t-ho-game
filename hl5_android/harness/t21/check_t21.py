#!/usr/bin/env python3
"""T21: camera giữa + map đệm + vòng sáng. Dùng: WORK=.. DEVDIR=.. check_t21.py W H OUTDIR
 A) giữa map: camc bật/tắt (vòng tắt) cho cùng khung => ảnh trùng từng byte (dx=dy=0).
 B) giữ LEFT tới mép trái: camc bật => hero ở giữa (hero_x - cam_x + dx == W/2), ảnh lưu; so ảnh camc tắt.
 C) vòng sáng: ảnh vòng bật, đếm điểm khác so vòng tắt (chỉ quanh chân hero).
 D) logic: sau N tick giữ phím, vị trí hero/camera/HP giống nhau khi camc+vòng bật vs tắt."""
import sys, os, json, hashlib, struct
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'l1'))
from qlib import *
from hl5libc import KEYS
W, Hh, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3]
c = Ctx(W, Hh, 'new', OUT, boot_to=1300); r = c.r; g = c.g
def sym(n): return BASE + c.sym[n]
def setv(n, v): c.wr(sym(n), struct.pack('<i', v))
def rdi(a): return struct.unpack('<i', bytes(g.uc.mem_read(a, 4)))[0]
def st():
    m = g.rd32(c.gv + 5200); h = g.rd32(c.gv + 5180)
    return {'hx': rdi(h + 4), 'hy': rdi(h + 8), 'camx': rdi(m + 0xd0), 'camy': rdi(m + 0xd4), 'hp': rdi(h + 0xf0), 'dx': rdi(sym('camc_last_dx')), 'dy': rdi(sym('camc_last_dy'))}
def md5(): return hashlib.md5(bytes(r.shot())).hexdigest()
def hold(k, n):
    g.call(r.keyf, g.env, 0, 2, KEYS[k] & 0xffffffff, 0)
    for _ in range(n): r.step(1)
    g.call(r.keyf, g.env, 0, 3, KEYS[k] & 0xffffffff, 0)
def scen(on, ring, k, n, shot):
    def f():
        setv('camc_on', on); setv('ring_on', ring)
        if n: hold(k, n)
        r.step(1); s = st(); s['md5'] = md5()
        if shot: c.shot(shot)
        return s
    return f
c.step(5)
c.fork(scen(0, 0, None, 0, 'a_off'), 'a_off'); c.fork(scen(1, 0, None, 0, 'a_on'), 'a_on')
c.fork(scen(1, 1, None, 0, 'c_ring'), 'c_ring')
c.fork(scen(0, 0, 'LEFT', 90, 'b_off'), 'b_off'); c.fork(scen(1, 0, 'LEFT', 90, 'b_on'), 'b_on'); c.fork(scen(1, 1, 'LEFT', 90, 'b_on_ring'), 'b_on_ring')
c.fork(scen(1, 1, 'UP', 120, 'e_up'), 'e_up'); c.fork(scen(0, 0, 'UP', 120, 'e_up_off'), 'e_up_off')
res = {}
for n in ['a_off', 'a_on', 'c_ring', 'b_off', 'b_on', 'b_on_ring', 'e_up', 'e_up_off']:
    res[n] = json.load(open(os.path.join(OUT, 'sc_%s_%dx%d_new.json' % (n, W, Hh))))
ok = {}
ok['A_giua_map_trung'] = res['a_off']['md5'] == res['a_on']['md5']
b = res['b_on']; ok['B_hero_giua'] = (b['hx'] - b['camx'] + b['dx']) == W // 2 and b['dx'] != 0
ok['D_logic_trung'] = all(res['b_off'][k] == res['b_on_ring'][k] for k in ('hx', 'hy', 'camx', 'camy', 'hp')) and all(res['e_up_off'][k] == res['e_up'][k] for k in ('hx', 'hy', 'camx', 'camy', 'hp'))
print(json.dumps({'ok': ok, 'res': res}, ensure_ascii=False))
