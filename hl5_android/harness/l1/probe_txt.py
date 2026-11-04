import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
D=[l.rstrip('\n') for l in open(os.path.expanduser('~/hl5work_l1/vi_android/dict.txt'),encoding='utf-8')]
def dec(b):
    o=[];i=0
    while i<len(b):
        x=b[i]
        if 0xC9<=x and i+1<len(b): idx=(x-0xC9)*94+(b[i+1]-0xA1); o.append(D[idx] if idx<len(D) else '?%d'%idx); i+=2
        else: o.append(chr(x) if x>=32 else '<%d>'%x); i+=1
    return ' '.join(o)
c = Ctx(600, 270, 'new', os.path.expanduser('~/hl5work_l1/p')); g=c.g; r=c.r
tm=g.rd32(BASE+0x16a220)
for i in (91,92,93,302,304,331,260,261,293,350,245,244):
    p=r.call(0x13fa94, tm, i); b=bytes(g.uc.mem_read(p,80)).split(b'\0')[0]; print(i, b.hex()[:40], '|', dec(b))
