"""T18a-1: diff bộ nhớ (bỏ FB) quanh mainPaint. p3.py W H"""
import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18lib import *
W, Hh = int(sys.argv[1]), int(sys.argv[2])
r = mk(W, Hh, 'orig', 't18'); to_play(r, 1300)
st = {}
def on_flush(uc, a, s, u): st['fb'] = uc.reg_read(UC_ARM_REG_R1)
def on_pe(uc, a, s, u): st['s0'] = snap(r)
def on_pr(uc, a, s, u): st['s1'] = snap(r)
r.g.uc.hook_add(UC_HOOK_CODE, on_flush, begin=BASE + 0x3d9d4, end=BASE + 0x3d9d4)
r.g.uc.hook_add(UC_HOOK_CODE, on_pe, begin=BASE + 0x987c0, end=BASE + 0x987c0)
r.g.uc.hook_add(UC_HOOK_CODE, on_pr, begin=BASE + 0x99fa8, end=BASE + 0x99fa8)
r.g.uc.ctl_flush_tb()
r.step(3)
fb = st['fb']; ex = [(fb, fb + W * Hh * 2 + 0x100)]
print('fb', hex(fb))
def show(tag, a, b):
    d = diff(a, b, ex); tot = sum(n for _, n in d)
    print('== %s: %d ranges, %d bytes' % (tag, len(d), tot))
    for ad, n in d[:70]:
        print('  %08x +%d  %s -> %s' % (ad, n, rb(a, ad, min(n, 8)).hex(), rb(b, ad, min(n, 8)).hex()))
show('paint #1 (in tick)', st['s0'], st['s1'])
s2 = snap(r); heap0 = (len(r.g.heap.sizes), r.g.heap.top)
r.call(0x987c0); s3 = snap(r); heap1 = (len(r.g.heap.sizes), r.g.heap.top)
show('paint #2 extra (after tick)', s2, s3)
print('heap', heap0, heap1)
