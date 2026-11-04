#!/usr/bin/env python3
"""T16b: chạy hoạt cảnh mở đầu, lái hội thoại bằng OK, ghi thông điệp/trang/cấp phát + chụp ảnh các trang.
Dùng: check_t16.py W H mode OUTDIR [f_end]      (mode: orig | new | off; kết quả OUTDIR/msgs_WxH_mode.json + png)
So sánh: compare_t16.py"""
import sys, os, json, time
H = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, H)
from t16lib import Dlg

W, Hh, mode, out = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
f_end = int(sys.argv[5]) if len(sys.argv) > 5 else 2400
os.makedirs(out, exist_ok=True)
t0 = time.time()
d = Dlg(W, Hh, mode)
shots = {(mi, p): False for mi in range(40) for p in range(6)}
msgs = d.run_dialogs(f_end, shots, out)
res = dict(W=W, H=Hh, mode=mode, frames=d.r.fr, msgs=msgs, heap0=d.h0, heap1=d.h1, secs=round(time.time() - t0))
json.dump(res, open(os.path.join(out, 'msgs_%dx%d_%s.json' % (W, Hh, mode)), 'w'), indent=1, default=str)
print('%dx%d %s: %d thông điệp, %d trang, frames=%d heap %s -> %s, %ds' % (W, Hh, mode, len(msgs), sum(len(m['pages']) for m in msgs), d.r.fr, d.h0, d.h1, res['secs']))
