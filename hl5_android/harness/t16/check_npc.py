#!/usr/bin/env python3
"""T16: thoại NPC thường NGOÀI hoạt cảnh mở đầu. Chạy hoạt cảnh mở đầu, bỏ qua bằng '#' (cùng nhịp mọi mode) tới khi tự do (e75 = 0 liên tục 150 khung),
rồi đặt hero sát một sự kiện NPC (EventProc+556+20*(i-1)), quay mặt về nó và bấm OK (đường gốc StateGame::KeyPressed -> Event_Collision_Check -> Event_checkEvent),
rồi lái hội thoại như check_t16 (run_dialogs). Ra OUT/msgs_WxH_mode.json + ảnh trang (đúng tên check_t16 để compare_t16.py dùng lại).
Dùng: check_npc.py W H mode OUT scan            (liệt kê sự kiện, thử từng cái ở nhánh fork, in cái nào mở thoại)
      check_npc.py W H mode OUT IDX [DIR]       (chạy thoại NPC của sự kiện IDX, hero đứng phía DIR: 0 dưới (mặt lên), 1 trên (mặt xuống), 2 phải (mặt trái), 3 trái (mặt phải))"""
import sys, os, json, struct, time
H = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, H)
from t16lib import Dlg, GV
W, Hh, mode, out, what = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4], sys.argv[5]
os.makedirs(out, exist_ok=True)
d = Dlg(W, Hh, mode); r = d.r; t0 = time.time()
d.step(900 - r.fr); free = 0
while r.fr < 4200:
    d.step(1)
    if d.e75(): free = 0
    else: free += 1
    if free >= 150: break
    if d.e75() and r.fr % 40 == 0: r.key('HASH')
    elif d.e75() and r.fr % 40 == 20: r.key('OK')
print('tự do tại khung', r.fr, 'e75', d.e75(), 'free', free, '%ds' % (time.time() - t0), flush=True)
ep = d.ep(); cnt = r.rdb(ep + 0x8c)[0]; hero = r.rd(GV + 5180)
ents = []
for i in range(1, cnt):
    e = r.rdb(ep + 556 + 20 * (i - 1), 20); ents.append((i, struct.unpack('<hh', e[:4]), e[4], e[5], e[7], e[18]))
    print('sự kiện', i, 'xy', ents[-1][1], 'type', e[4], 'id', e[5], '+7', e[7], 'cờ+18', e[18])
print('hero', r.call(0xcbc58, hero), r.call(0xcbc68, hero), 'dir', r.call(0x472f0, hero) & 255, flush=True)
OFF = {0: (0, 16, 0), 1: (0, -16, 1), 2: (16, 0, 2), 3: (-16, 0, 3)}   # (dx, dy, dir hero): 0 hero dưới mặt lên(dir0)... dir0 = lên, 1 = xuống, 2 = trái, 3 = phải
def go(idx, dirk):
    x, y = [e for e in ents if e[0] == idx][0][1]; dx, dy, hd = OFF[dirk]
    r.call(0xcbc70, hero, x + dx, y + dy); r.call(0x472e8, hero, hd)
    d.step(2); r.key('OK'); d.step(3)
if what == 'scan':
    for e in ents:
        for dk in range(4):
            pid = os.fork()
            if pid: os.waitpid(pid, 0); continue
            go(e[0], dk); d.step(30)
            s = d.state(); print('  thử sự kiện', e[0], 'hướng', dk, '->', 'e75', d.e75(), 'st', s and s['st'], 'name', s and s['name'], 'text', s and s['text'][:16].hex(), flush=True)
            os._exit(0)
    sys.exit(0)
idx = int(what); dk = int(sys.argv[6]) if len(sys.argv) > 6 else 0
go(idx, dk)
shots = {(mi, p): False for mi in range(40) for p in range(6)}
msgs = d.run_dialogs(r.fr + 1500, shots, out)
res = dict(W=W, H=Hh, mode=mode, frames=r.fr, msgs=msgs, heap0=d.h0, heap1=d.h1, secs=round(time.time() - t0), npc=dict(idx=idx, dir=dk))
json.dump(res, open(os.path.join(out, 'msgs_%dx%d_%s.json' % (W, Hh, mode)), 'w'), indent=1, default=str)
print('%dx%d %s: %d thông điệp, %d trang, heap %s -> %s' % (W, Hh, mode, len(msgs), sum(len(m['pages']) for m in msgs), d.h0, d.h1))
