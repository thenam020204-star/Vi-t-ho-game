#!/usr/bin/env python3
"""L1/T7: cash miễn phí + hồi sinh offline (menu dev.so). Dùng: check_t7.py W H OUTDIR. Kết quả OUTDIR/t7_<WxH>.json"""
import sys, os, json
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
W, Hh, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3]
c = Ctx(W, Hh, 'new', OUT); r = c.r; g = c.g
def logpos(): g.log.flush(); return os.path.getsize(g.log.name)
def logsince(p):
    g.log.flush(); s = open(g.log.name, 'rb').read()[p:].decode('latin1').split('\n')
    return {'NET': [l for l in s if l.startswith('NET')], 'JNIC': sorted(set(l for l in s if l.startswith('JNIC')))}
def bunch(cat, i): return r.call(0x9b6c0, c.bag(), cat, i) & 0xffffffff
def popup_info():
    t = c.top(); return {'cls': hex(c.cls()), 'f': [c.s8(t, o) for o in range(0xc, 0x1c)]}
res = {}
def buy(idx):
    p0 = logpos()
    r.call(0x42ab8, 0, 0); c.step(10)
    t = c.top(); recs = c.u32(c.u32(t + 0x14) + 4)
    for _ in range(idx):
        c.key('RIGHT', 3)
    rec = c.u32 and [c.b8(recs + 24 * idx, i) for i in range(24)]
    cat, iid, cnt = 18, rec[8], rec[9] or 1
    before = bunch(cat, iid); res['rec%d' % idx] = rec
    c.key('OK', 6); res['p1'] = popup_info(); c.shot('cash_confirm')
    c.key('OK', 6); res['p2'] = popup_info(); c.shot('cash_done')
    c.key('OK', 6); res['p3'] = popup_info()
    res['b%d' % idx] = {'id': iid, 'cnt': cnt, 'before': before, 'after': bunch(cat, iid)}
    res['log%d' % idx] = logsince(p0)
def sc_buy0(): buy(0); return res
def sc_buy5():
    r.call(0x42ab8, 0, 0); c.step(10)
    for k in ['RIGHT'] * 2 + ['DOWN']: c.key(k, 3)
    t = c.top(); idx = c.s8(t, 15); r.touch = r.touch   # chỉ để ghi ô
    p0 = logpos(); recs = c.u32(c.u32(t + 0x14) + 4); rec = [c.b8(recs + 24 * idx, i) for i in range(24)]
    before = bunch(18, rec[8]); c.key('OK', 6); c.key('OK', 6); c.shot('cash_done5'); c.key('OK', 6)
    return {'idx': idx, 'rec': rec, 'before': before, 'after': bunch(18, rec[8]), 'log': logsince(p0), 'st': popup_info()}
def sc_revive():
    p0 = logpos(); h = c.hero
    c.wr(h + 0xf0, b'\0\0\0\0'); res = {'hp0': c.u32(h + 0xf0)}
    seq = []
    for i in range(80):
        c.step(5); t = c.top(); s = (hex(c.cls()), c.s8(t, 0xc), c.s8(t, 0xe))
        if not seq or seq[-1] != s: seq.append(s)
    res['seq'] = seq; res['hpnow'] = c.u32(h + 0xf0); c.shot('go')
    return res
def sc_revive_ok():
    p0 = logpos(); h = c.hero; t = c.top(); r.call(0xda410, t, 1, 0); c.step(8); c.shot('go_popup')
    st1 = (hex(c.cls()), c.s8(t, 0xc), c.s8(t, 0xe), c.u32(h + 0xf0)); c.wr(h + 0xf0, (5).to_bytes(4, 'little'))
    hpmax = c.u32(h + 0x180); a0 = bunch(18, 23)
    c.key('OK', 10); c.shot('go_after'); t = c.top()
    st2 = (hex(c.cls()), c.s8(t, 0xc), c.s8(t, 0xe), c.u32(h + 0xf0))
    c.key('OK', 10); t = c.top(); st3 = (hex(c.cls()), c.s8(t, 0xc), c.s8(t, 0xe), c.u32(h + 0xf0))
    return {'popup': st1, 'hpmax': hpmax, 'after_yes': st2, 'after_ok': st3, 'amulet': (a0, bunch(18, 23)), 'rev': c.r.rd(c.sym['t7_rev']), 'log': logsince(p0)}
for nm in ['revive_ok']:
    c.fork(globals()['sc_' + nm], nm)
