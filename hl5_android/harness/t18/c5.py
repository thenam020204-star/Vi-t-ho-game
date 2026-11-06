"""T18c-5: mô phỏng thời gian: số khung hiển thị / khung trung gian mỗi tick theo tốc độ giả định của máy. c5.py W H ipms_list(k lệnh/ms, phẩy) [giây ảo] [bật=1]
Mô hình (GIẢ ĐỊNH, ghi trong báo cáo): vsync 16,667 ms; nativeLoop chạy ở vsync đầu tiên >= lúc loop trước kết thúc; chi phí guest = hằng số lệnh cho mainPaint/mainProcess (đo T18a-4, byte khối/4)
cộng chi phí đo của Map::MapMove (hook khối, hiệu chuẩn); flush/GL phía host tính 0. Thời gian ảo cộng vào vclock tại điểm vào hàm."""
import sys, os, json; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t18b import *
W, Hh = int(sys.argv[1]), int(sys.argv[2]); SP = [float(x) for x in sys.argv[3].split(',')]; SEC = float(sys.argv[4]) if len(sys.argv) > 4 else 6; ON = int(sys.argv[5]) if len(sys.argv) > 5 else 1
PAINT = {(400, 240): 9.36e6, (600, 270): 14.8e6, (800, 360): 25.2e6}[(W, Hh)]   # lệnh/paint (T18a-4)
PROC = 0.16e6
MM = float(os.environ.get('MM_INS', '0'))   # lệnh/lần Map::MapMove (hiệu chuẩn bằng c4.py)
r = mkd(W, Hh, 'dev', 'c5'); to_play(r, 1300)
if ON: st_wr(r, 'flag', 2)
cur = {'ipms': 3.2e6}; acc = {'ms': 0.0}
def charge(ins):
    acc['ms'] += ins / cur['ipms']
    k = int(acc['ms']); acc['ms'] -= k; r.g.vclock += k
uc = r.g.uc
for addr, ins in ((0x987c0, PAINT), (0x98a78, PROC), (0xafc54, MM)):
    uc.hook_add(UC_HOOK_CODE, (lambda ins: lambda uc, a, s, u: charge(ins))(ins), begin=BASE + addr, end=BASE + addr)
uc.ctl_flush_tb()
DIR = ['RIGHT']; ev(r, 'RIGHT', True); KT = [r.ticks]
def flip():
    print('flip', DIR[0], 'tick', r.ticks, 'hero', hero(r), file=sys.stderr)
    ev(r, DIR[0], False); DIR[0] = 'LEFT' if DIR[0] == 'RIGHT' else 'RIGHT'; ev(r, DIR[0], True); KT[0] = r.ticks
for k_ in range(40): vloop(r)   # ổn định
res = []
for ip in SP:
    cur['ipms'] = ip * 1000.0
    t0 = r.g.vclock; tk0, fl0 = r.ticks, r.flushes; m0 = st_rd(r, 'n_mid'); lag0 = st_rd(r, 'n_tick_lag'); sk0 = st_rd(r, 'n_skip')
    loops = 0; base = r.g.vclock
    while r.g.vclock - t0 < SEC * 1000:
        k = -(-(r.g.vclock - base) // 16.667); r.g.vclock = int(round(base + k * 16.667)) if k * 16.667 + base > r.g.vclock else r.g.vclock + 1
        if r.ticks - KT[0] >= 25: flip()   # đi qua lại giữa hai tường (hero luôn di chuyển)
        r.g.frame = r.fr; r.g.call(r.loop, r.g.env, 0); r.fr += 1; loops += 1
    dt = (r.g.vclock - t0) / 1000.0; tk = r.ticks - tk0; fl = r.flushes - fl0
    row = dict(ipms_k=ip, sec=round(dt, 2), tick_hz=round(tk / dt, 1), frames_hz=round(fl / dt, 1), mid_per_tick=round((st_rd(r, 'n_mid') - m0) / max(tk, 1), 2),
               lag_ticks=st_rd(r, 'n_tick_lag') - lag0, skipped=st_rd(r, 'n_skip') - sk0, tp_ms=st_rd(r, 'tp'))
    row['hero'] = hero(r); print(json.dumps(row)); res.append(row)
