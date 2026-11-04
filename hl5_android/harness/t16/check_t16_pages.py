#!/usr/bin/env python3
"""T16b: thử NHIỀU TRANG. Tới hộp thoại đầu tiên rồi thay chuỗi của slot đang nói bằng một chuỗi rất dài (ghép mọi thông điệp của hoạt cảnh,
byte mã game thật) trong vùng nhớ guest, đặt trang=0, tiến độ=0; lái bằng OK như check_t16.py: ghi số trang, ảnh mỗi trang, cấp phát.
Dùng: check_t16_pages.py W H mode OUTDIR MSGS_JSON    (MSGS_JSON = msgs_WxH_orig.json của check_t16.py, lấy chuỗi)"""
import sys, os, json, struct
H = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, H)
from t16lib import Dlg, BASE, GV

W, Hh, mode, out, src = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4], sys.argv[5]
os.makedirs(out, exist_ok=True)
texts = [bytes.fromhex(m['text']) for m in json.load(open(src))['msgs']]
long_text = b' '.join(texts[:8])
d = Dlg(W, Hh, mode); r = d.r; g = r.g
d.step(900 - r.fr)
while r.fr < 2400:
    d.step(1); s = d.state()
    if s and s['st'] == 2 and s['text'] and s['prog'] >= 6: break
ep = d.ep(); cur = r.rdb(ep + 172)[0]; D = ep + 176 + 60 * cur
ptr = g.misc_alloc(len(long_text) + 8); g.uc.mem_write(ptr, long_text + b'\0')
g.wr32(D + 32, ptr); g.uc.mem_write(D + 9, b'\0'); g.uc.mem_write(D + 12, struct.pack('<h', 0)); g.uc.mem_write(D + 10, struct.pack('<h', 1))
h0 = d.heap(); pages = {}; cnt = 0; cur_page = -1; f0 = r.fr
while r.fr < f0 + 1500:
    d.step(1); s = d.state()
    if not s or s['st'] != 2 or s['text'] != long_text[:len(s['text'])] or not s['text']:
        if pages: break
        continue
    p = s['page']
    if p != cur_page: cur_page = p; cnt = 0
    cnt += 1
    if cnt == 3: r.key('OK')
    elif cnt == 6:
        pages[p] = dict(frame=r.fr, prog=s['prog'], speed=s['speed'], lines=s['lines'], tx=s['tx'], box=s['box'])
        d.shot(os.path.join(out, 'pg_p%d_%dx%d_%s.png' % (p, W, Hh, mode)))
    elif cnt == 7: r.key('OK')
d.step(30)
res = dict(W=W, H=Hh, mode=mode, text_len=len(long_text), npages=len(pages), pages=pages, heap0=h0, heap1=d.heap(), frames=r.fr - f0)
json.dump(res, open(os.path.join(out, 'pages_%dx%d_%s.json' % (W, Hh, mode)), 'w'), indent=1)
print('%dx%d %s: chuỗi %d byte -> %d trang, heap %s -> %s' % (W, Hh, mode, len(long_text), len(pages), h0, d.heap()))
