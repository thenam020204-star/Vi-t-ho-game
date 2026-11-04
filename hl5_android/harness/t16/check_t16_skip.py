#!/usr/bin/env python3
"""T16b: skip + chạm. Tới hộp thoại đầu tiên (đang chạy chữ), rồi fork các kịch bản:
  key_hash  : phím '#' -> sự kiện kết thúc (e75 về 0), băm ảnh sau 300 khung
  touch_skip: chạm nút Bỏ qua (mới: tâm nút đọc từ dlg_S; gốc: (10,10)) -> cùng kết quả như key_hash
  touch_next: chạm ngoài nút -> như phím OK (so với phím OK ở cùng build: trang/tiến độ/tốc độ)
  key_next  : phím OK
Dùng: check_t16_skip.py W H mode OUTDIR   -> OUTDIR/skip_WxH_mode.json"""
import sys, os, json, hashlib, struct
H = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, H)
from t16lib import Dlg, BASE

W, Hh, mode, out = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]
os.makedirs(out, exist_ok=True)
d = Dlg(W, Hh, mode); r = d.r
d.step(900 - r.fr)
while r.fr < 2400:
    d.step(1); s = d.state()
    if s and s['st'] == 2 and s['text'] and s['prog'] >= 6: break
base = d.state(); base_fr = r.fr
skip_rect = None
if mode == 'new':
    a = BASE + d.sym['dlg_S']; v = struct.unpack('<6i', bytes(r.g.uc.mem_read(a, 24)))
    skip_rect = v[2:6] if v[1] else None
res = dict(base=dict((k, v) for k, v in base.items() if k != 'text'), base_text=base['text'].hex(), skip_rect=skip_rect)

def settle(n=300):
    t = None
    for i in range(n):
        d.step(1)
        if t is None and not d.e75(): t = i
    d.shot(os.path.join(out, 'skip_%dx%d_%s_%s.png' % (W, Hh, mode, os.getpid())))
    return t, hashlib.md5(bytes(r.shot())).hexdigest()[:12]

def scen(name, fn):
    pid = os.fork()
    if pid: os.waitpid(pid, 0); return
    try: res[name] = fn()
    except Exception as e:
        import traceback; res[name] = {'ERR': repr(e), 'tb': traceback.format_exc()}
    json.dump(res[name], open(os.path.join(out, 'skip_%dx%d_%s_%s.json' % (W, Hh, mode, name)), 'w'), default=str)
    os._exit(0)

def key_hash():
    r.key('HASH'); t, h = settle(); return dict(e75_zero_at=t, hash=h)
def touch_skip():
    if mode == 'new':
        x0, y0, x1, y1 = skip_rect[0:4] if skip_rect else (0, 0, 1, 1)
        x, y = (x0 + x1) // 2, (y0 + y1) // 2
    else: x, y = 10, 10
    r.touch(x, y, True); t, h = settle(); return dict(e75_zero_at=t, hash=h, at=[x, y])
def after(f):
    def g():
        f(); d.step(2); s = d.state(); return dict(page=s['page'], prog=s['prog'], speed=s['speed'], st=s['st'], text=s['text'].hex())
    return g
scen('key_hash', key_hash)
scen('touch_skip', touch_skip)
scen('touch_next', after(lambda: r.touch(W // 2, Hh // 2, True)))
scen('key_next', after(lambda: r.key('OK')))
# chạm góc trên-trái 20x10 khi nút ở chỗ khác (mới) = chạm "khác" -> như OK
scen('touch_corner', after(lambda: r.touch(10, 10, True)))
print(json.dumps(res, default=str)[:400])
