"""Dump chuỗi CommonText (ct) / InGameText (igt) đã giải mã từ so guest. Dùng: txt.py ct|igt a-b [a-b..]"""
import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l6'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'l3'))
from qlib import *
D = [l.rstrip('\n') for l in open(os.path.expanduser('~/hl5work_l6/vi_android/dict.txt'), encoding='utf-8')]
def dec(b):
    o = []; i = 0
    while i < len(b):
        x = b[i]
        if 0xC9 <= x and i + 1 < len(b): idx = (x - 0xC9) * 94 + (b[i + 1] - 0xA1); o.append(D[idx] if idx < len(D) else '?%d' % idx); i += 2
        else: o.append(chr(x) if x >= 32 else '<%d>' % x); i += 1
    return ' '.join(o)
c = Ctx(400, 240, 'orig', '/tmp/l6c_txt', boot_to=1000); g = c.g; r = c.r
tm = g.rd32(BASE + 0x16a220); fn = 0x13faa0 if sys.argv[1] == 'igt' else 0x13fa94
for a in sys.argv[2:]:
    lo, hi = (a.split('-') + [a])[:2] if '-' in a else (a, a)
    for i in range(int(lo), int(hi) + 1):
        p = r.call(fn, tm, i); print(i, '|', dec(bytes(g.uc.mem_read(p, 160)).split(b'\0')[0]))
