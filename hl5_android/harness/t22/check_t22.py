#!/usr/bin/env python3
"""T22: bình máu (Elixir) mua ở cash shop phải vào loại 11 (uống được, gán phím tắt được).
Dùng: WORK=... DEVDIR=... check_t22.py W H OUTDIR [orig]. Kết quả in JSON."""
import sys, os, json
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'l1'))
from qlib import *
W, Hh, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3]
c = Ctx(W, Hh, 'new', OUT); r = c.r
def bunch(cat, i): return r.call(0x9b6c0, c.bag(), cat, i) & 0xffffffff
def sx(v): return v - 256 if v > 127 else v
res = {}
# mua ô 0 (Elixir (20))
r.call(0x42ab8, 0, 0); c.step(10)
t = c.top(); recs = c.u32(c.u32(t + 0x14) + 4); rec = [c.b8(recs, i) for i in range(24)]
res['rec0'] = rec
b = {k: bunch(*k) for k in [(18, 34), (11, 8)]}
c.key('OK', 6); c.key('OK', 6); c.shot('t22_bought'); c.key('OK', 6)
res['before'] = {str(k): v for k, v in b.items()}
res['after'] = {str(k): bunch(*k) for k in [(18, 34), (11, 8)]}
# thoát cửa hàng về cảnh chơi, uống Elixir bằng HERO::BattleUseItem(11, id 8) như phím tắt gốc
c.key('-16', 10)
h = c.hero; hpmax = c.u32(h + 0x180)
c.wr(h + 0xf0, (5).to_bytes(4, 'little'))
def rec_fx(): return {'b2fe': c.b8(h, 0x2fe), 'h300': c.u32(h + 0x300) & 0xffff, 'h302': c.u32(h + 0x302) & 0xffff, 'cool': r.call(0x85394, h) & 0xff}
res['fx0'] = rec_fx()
ok = r.call(0x8bda0, h, 8) & 0xff
res['fx_elixir'] = rec_fx()
c.step(30)
res['use'] = {'ret': ok, 'hp_before': 5, 'hp_after': c.u32(h + 0xf0), 'hpmax': hpmax, 'cnt_after': bunch(11, 8)}
print(json.dumps(res, ensure_ascii=False))
# đối chứng: Training Potion (11,6) như SetDefaultItem cấp, dùng cùng đường
res['cls_after_back'] = hex(c.cls())
for i in range(6):
    if c.cls() == res.get('cls_game'): break
    c.key('-16', 10)
res['cls_now'] = hex(c.cls())
c.wr(h + 0xf0, (5).to_bytes(4, 'little'))
n6 = bunch(11, 6); ok6 = r.call(0x8bda0, h, 6) & 0xff
hp = []
for i in range(12): c.step(10); hp.append(c.u32(h + 0xf0))
res['ctrl_potion6'] = {'cnt_before': n6, 'ret': ok6, 'hp_trace': hp, 'cnt_after': bunch(11, 6)}
c.wr(h + 0xf0, (5).to_bytes(4, 'little'))
r.wr(h + 0, b'') if False else None
ok8 = r.call(0x8bda0, h, 8) & 0xff
hp = []
for i in range(12): c.step(10); hp.append(c.u32(h + 0xf0))
res['elixir8'] = {'ret': ok8, 'hp_trace': hp, 'cnt_after': bunch(11, 8)}
c.shot('t22_after_use')
print(json.dumps(res, ensure_ascii=False))
