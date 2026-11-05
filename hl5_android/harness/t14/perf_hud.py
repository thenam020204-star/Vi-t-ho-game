"""T14c: đo chi phí vẽ HUD. perf_hud.py W H mode(new|off|orig) [dev_dir_tên] [N khung]
 new = HUD mới; off = HUD mới nhưng cờ MUI_F_DISABLE (mui_flags=1); orig = bản gốc v0.2-hd.
 Đếm: ins_menu = số lệnh ARM chạy trong đoạn menu (0x171000.., khối/4); blk_all = tổng byte các khối cơ bản của cả guest (xấp xỉ chi phí toàn khung).
 HUD_DEV=dev_before để đo bản cũ (mặc định dev)."""
import sys, os, json; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import t14lib
from t14lib import *
from unicorn import UC_HOOK_BLOCK
W, Hh, mode = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3]
N = int(sys.argv[4]) if len(sys.argv) > 4 else 40
r = mk(W, Hh, 'orig' if mode == 'orig' else 'new', 'pf')
lo, hi = BASE + 0x171000, BASE + 0x171000 + 0x50000
if mode == 'off':
    fa = BASE + r.sym['mui_flags']; r.g.uc.mem_write(fa, (1).to_bytes(4, 'little'))
to_play(r, 1300); r.step(5)
cnt = {'m': 0, 'a': 0, 'on': False}
def hb(uc, addr, size, ud):
    if not cnt['on']: return
    cnt['a'] += size
    if lo <= addr < hi: cnt['m'] += size // 4
h = r.g.uc.hook_add(UC_HOOK_BLOCK, hb)
cnt['on'] = True
for i in range(N):
    if i % 10 == 3: r.key('OK')
    r.step(1)
cnt['on'] = False
res = dict(W=W, H=Hh, mode=mode, frames=N, ins_menu_per_frame=cnt['m'] // N, blk_all_per_frame=cnt['a'] // N)
print(json.dumps(res))
