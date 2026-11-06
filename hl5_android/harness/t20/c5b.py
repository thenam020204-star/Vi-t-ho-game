"""T20-C5b: mô phỏng thời gian với interp BẬT: số khung hiển thị / khung trung gian mỗi tick theo tốc độ giả định của máy; so base (không T20) và dev (T20).
  c5b.py W H dev ipms_list(k lệnh/ms, phẩy) giây PAINT_INS PROC_INS MM_INS MC_CALLS MC_MB
Mô hình (GIẢ ĐỊNH, ghi trong báo cáo): vsync 16,667 ms; nativeLoop chạy ở vsync đầu tiên >= lúc loop trước kết thúc; chi phí guest mỗi mainPaint = PAINT_INS lệnh (đo m1.py: byte khối/4),
mỗi mainProcess = PROC_INS, mỗi Map::MapMove = MM_INS (135k * H/240, c6.py T18); host memcpy: MC_CALLS lần/paint x 2 us/lần + MC_MB / 4000 MB/s (giả định); flush/GL phía host = 0.
Hero đi qua lại (đổi hướng khi đứng yên 4 tick, vòng RIGHT/LEFT/DOWN/UP) để luôn di chuyển."""
import sys, os, json; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t20lib import *
W, Hh, dev = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3]; SP = [float(x) for x in sys.argv[4].split(',')]; SEC = float(sys.argv[5])
PAINT, PROC, MM, MCC, MCMB = [float(x) for x in sys.argv[6:11]]
CALL_US = float(os.environ.get('CALL_US', '2')); BW = float(os.environ.get('BW_MBS', '4000'))
r = mkd(W, Hh, dev, 'c5b'); to_play(r, 1300); st_wr(r, 'flag', 2)
cur = {'ipms': 1e6}; acc = {'ms': 0.0}
def charge(ins, extra_ms=0.0):
    acc['ms'] += ins / cur['ipms'] + extra_ms
    k = int(acc['ms']); acc['ms'] -= k; r.g.vclock += k
uc = r.g.uc
host_paint_ms = MCC * CALL_US / 1000.0 + MCMB / BW * 1000.0
for addr, ins, ex in ((0x987c0, PAINT, host_paint_ms), (0x98a78, PROC, 0.0), (0xafc54, MM, 0.0)):
    uc.hook_add(UC_HOOK_CODE, (lambda ins, ex: lambda uc, a, s, u: charge(ins, ex))(ins, ex), begin=BASE + addr, end=BASE + addr)
uc.ctl_flush_tb()
DIRS = ['RIGHT', 'LEFT', 'DOWN', 'UP']; D = [0]; ev(r, DIRS[0], True); last = [hero(r), 0, 0]
def tickwatch():
    """mỗi tick thật: giữ phím; đứng yên >= 3 tick (chạm tường) -> nhả, nghỉ 2 tick (game cần >= 1 tick để nhận nhả), rồi nhấn hướng kế"""
    h = hero(r)
    if last[2] > 0:
        last[2] -= 1
        if last[2] == 0: D[0] = (D[0] + 1) % 4; ev(r, DIRS[D[0]], True); last[1] = 0
        return
    if h != last[0]: last[0] = h; last[1] = 0
    else: last[1] += 1
    if last[1] >= 3: ev(r, DIRS[D[0]], False); last[2] = 2
for k_ in range(40): vloop(r)
res = []
for ip in SP:
    cur['ipms'] = ip * 1000.0
    t0 = r.g.vclock; tk0, fl0 = r.ticks, r.flushes; m0 = st_rd(r, 'n_mid'); lag0 = st_rd(r, 'n_tick_lag'); sk0 = st_rd(r, 'n_skip'); base = r.g.vclock; tkp = r.ticks
    while r.g.vclock - t0 < SEC * 1000:
        k = -(-(r.g.vclock - base) // 16.667); r.g.vclock = int(round(base + k * 16.667)) if k * 16.667 + base > r.g.vclock else r.g.vclock + 1
        if r.ticks != tkp: tkp = r.ticks; tickwatch()
        r.g.frame = r.fr; r.g.call(r.loop, r.g.env, 0); r.fr += 1
    dt = (r.g.vclock - t0) / 1000.0; tk = r.ticks - tk0; fl = r.flushes - fl0
    row = dict(W=W, H=Hh, dev=dev, ipms_k=ip, sec=round(dt, 2), tick_hz=round(tk / dt, 1), frames_hz=round(fl / dt, 1), mid_per_tick=round((st_rd(r, 'n_mid') - m0) / max(tk, 1), 2),
               lag_ticks=st_rd(r, 'n_tick_lag') - lag0, skipped=st_rd(r, 'n_skip') - sk0, tp_ms=st_rd(r, 'tp'), hero=hero(r))
    print(json.dumps(row)); sys.stdout.flush(); res.append(row)
