#!/usr/bin/env python3
"""T16 hồi quy T17c2: ảnh cảnh sau 20 lần mở/đóng Túi đồ+Trang bị. Dùng: hoi_quy.py W H new|orig|off OUT
Ghi OUT/hq_<WxH>_<mode>_{before,after}.png + .json (e75, md5, trạng thái). Cùng kịch bản với check_c2.sc_cycles."""
import sys, os, json, hashlib
H = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, os.path.join(H, '..', 't17c2'))
from qlib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
c = Ctx(W, Hh, 'orig' if MODE == 'orig' else 'new', OUT, boot_to=0 if MODE == 'off' else 1000)
if MODE == 'off':   # MUI_F_DISABLE (mui_flags=1) đặt TRƯỚC khi chạy tới khung 1000, như check_t16 mode off
    c.g.wr32(BASE + c.sym['mui_flags'], 1); route(c.r, 1000); c.hero = c.g.rd32(c.gv + 5180)
NEW = MODE == 'new'; r = c.r; g = c.g   # off: menu tắt -> phím gốc
GV = BASE + 0x16a238
def ep(): return r.rd(0x16a238 + 5204)
def flags():
    e = ep(); d = {'ep': hex(e)}
    if e: d['e75'] = r.rdb(e + 0x75)[0]; d['D172'] = r.rdb(e + 172)[0]; d['D43'] = [r.rdb(e + 176 + 60 * i + 43)[0] for i in range(2)]
    d['cls'] = hex(c.cls()); d['md5'] = hashlib.md5(r.shot()).hexdigest()
    return d
res = {'start': flags()}
c.shot('hq_before_%s' % MODE)
for k in range(20):
    c.S3(2 if k % 2 == 0 else 3, 5)
    if k % 4 == 0 and NEW: c.tap(0xA0 + 1)
    elif k % 4 == 0: c.key('OK')
    for _ in range(4):
        if c.cls() == 0x15e150: break
        r.key('CLR'); r.step(5)
    if k in (0, 9): res['k%d' % k] = flags()
r.step(10); res['end'] = flags(); c.shot('hq_after_%s' % MODE)
json.dump(res, open(os.path.join(OUT, 'hq_%dx%d_%s.json' % (W, Hh, MODE)), 'w'), indent=1)
print(json.dumps(res))
