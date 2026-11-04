import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
c = Ctx(600, 270, 'orig', os.path.expanduser('~/hl5work_l1/p'))
qm = c.u32(c.gv + 5580)
for i in (0, 1, 2): c.r.call(0xcdcc4, qm, i, 1)
for i in (0, 1, 2): c.r.call(0xd0a68, qm, i)
c.S3(8, 6)
def snap(): return bytes(c.g.uc.mem_read(qm, 0x3400))
a = snap(); c.key('DOWN', 4); c.key('OK', 4); b = snap()
d = [i for i in range(len(a)) if a[i] != b[i]]
runs = []
for i in d:
    if runs and i - runs[-1][1] <= 4: runs[-1][1] = i
    else: runs.append([i, i])
for s, e in runs: print(hex(s), hex(e), b[s:e+1][:64].hex())
print('view', [c.s8(qm, o) for o in (0x21c, 0x21d)])
t = c.top(); print('this+0x3fc..', hex(c.u32(t + 0x3fc)), [hex(c.u32(t + o)) for o in (0x3f0, 0x3f4, 0x3f8, 0x400, 0x404)])
D=[l.rstrip('\n') for l in open(os.path.expanduser('~/hl5work_l1/vi_android/dict.txt'),encoding='utf-8')]
def dec(b):
    o=[];i=0
    while i<len(b):
        x=b[i]
        if 0xC9<=x and i+1<len(b): idx=(x-0xC9)*94+(b[i+1]-0xA1); o.append(D[idx] if idx<len(D) else '?%d'%idx); i+=2
        else: o.append(chr(x) if x>=32 else '<%d>'%x); i+=1
    return ' '.join(o)
m = b
import struct
print('ptrs', [hex(x) for x in struct.unpack('<6I', m[4:28])])
print(m[0x1c:0x90].hex())
for p in struct.unpack('<6I', m[4:28]):
    try: print(hex(p), dec(bytes(c.g.uc.mem_read(p, 200)).split(b'\0')[0]))
    except Exception as e: print('err', e)
rec = qm + 1428
print('rec0', bytes(c.g.uc.mem_read(rec, 368)).hex())
