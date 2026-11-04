import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
D=[l.rstrip('\n') for l in open(os.path.expanduser('~/hl5work_l1b/vi_android/dict.txt'),encoding='utf-8')]
def dec(b):
    o=[];i=0
    while i<len(b):
        x=b[i]
        if 0xC9<=x and i+1<len(b): idx=(x-0xC9)*94+(b[i+1]-0xA1); o.append(D[idx] if idx<len(D) else '?%d'%idx); i+=2
        else: o.append(chr(x) if x>=32 else '<%d>'%x); i+=1
    return ' '.join(o)
c = Ctx(600, 270, 'new', os.path.expanduser('~/hl5work_l1b/p')); g=c.g; r=c.r
tm=g.rd32(BASE+0x16a220)
kind = os.environ.get('K', 'igt')
fn = 0x13faa0 if kind == 'igt' else 0x13fa94
for a in sys.argv[1:]:
    lo, _, hi = a.partition('-'); lo = int(lo); hi = int(hi or lo)
    for i in range(lo, hi + 1):
        p=r.call(fn, tm, i); b=bytes(g.uc.mem_read(p,160)).split(b'\0')[0]; print(i, '|', dec(b))
