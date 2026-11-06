"""T18c-5 hiệu chuẩn: chi phí (byte khối cơ bản) của Map::MapMove trong khung trung gian / tick trễ vs mainPaint. c6.py W H [vòng]"""
import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18b import *
from unicorn import UC_HOOK_BLOCK
W, Hh = int(sys.argv[1]), int(sys.argv[2]); N = int(sys.argv[3]) if len(sys.argv) > 3 else 12
r = mkd(W, Hh, 'dev', 'c6'); to_play(r, 1300); st_wr(r, 'flag', 2); uc = r.g.uc
S = {'in': None, 'mm': 0, 'paint': 0, 'n_mm': 0, 'n_paint': 0, 'pin': None, 'on': False}
def pe(uc, a, s, u):
    if S['on']: S['pin'] = uc.reg_read(UC_ARM_REG_LR); S['n_paint'] += 1
def me(uc, a, s, u):
    if S['on']: S['in'] = uc.reg_read(UC_ARM_REG_LR); S['n_mm'] += 1
uc.hook_add(UC_HOOK_CODE, me, begin=BASE + 0xafc54, end=BASE + 0xafc54)
uc.hook_add(UC_HOOK_CODE, pe, begin=BASE + 0x987c0, end=BASE + 0x987c0)
def hb(uc, a, s, u):
    if not S['on']: return
    if S['in'] is not None:
        if a == S['in']: S['in'] = None
        else: S['mm'] += s
    elif S['pin'] is not None:
        if a == S['pin']: S['pin'] = None
        else: S['paint'] += s
uc.hook_add(UC_HOOK_BLOCK, hb); uc.ctl_flush_tb()
ev(r, 'RIGHT', True)
for i in range(30): vloop(r)
S['on'] = True; t0 = r.ticks
for i in range(N): vloop(r)
print(W, Hh, 'vòng', N, 'tick', r.ticks - t0, 'MapMove: %d lần, %.0f B/lần (~%.0f lệnh)' % (S['n_mm'], S['mm'] / max(S['n_mm'], 1), S['mm'] / max(S['n_mm'], 1) / 4), '| mainPaint: %d lần, %.0f B/lần (~%.0f lệnh)' % (S['n_paint'], S['paint'] / max(S['n_paint'], 1), S['paint'] / max(S['n_paint'], 1) / 4))
