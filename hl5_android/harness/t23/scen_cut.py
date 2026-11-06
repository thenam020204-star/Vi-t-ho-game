"""T23: kịch bản đo toàn game. scen.py W H  -> ~/hl5work_t23/out/prof_WxH.json
Dòng thời gian: tiêu đề/chọn lớp -> hoạt cảnh mở đầu + hội thoại -> chơi thường (đứng/đi/đánh) -> menu trong game -> mép map."""
import sys, json, time; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__))) if False else None
sys.path.insert(0, '/home/user/Vi-t-ho-game/hl5_android/harness/t23')
from t23lib import *
W, Hh = int(sys.argv[1]), int(sys.argv[2]); N = int(os.environ.get('NF', 30))
c = mk(W, Hh, 0); r = c.r; g = c.g; T = Timeline(c); T.addroute(0, 960)
OUT = os.path.expanduser('~/hl5work_t23/out'); log = open(OUT + '/scencut_%dx%d.log' % (W, Hh), 'w')
def L(*a):
    print(*a, file=log, flush=True)
def goto(fr, light=True):
    if r.fr < fr: T.run_light(fr - r.fr, 'route')
def GV(o): return r.rd(0x16a238 + o)
def info():
    ep = GV(5204); e75 = r.rdb(ep + 0x75)[0] if ep else -1; top = r.top()
    return dict(fr=r.fr, e75=e75, st=r.rdb(top + 0xc)[0] if top else -1, sub=r.rdb(top + 0xd)[0] if top else -1, cls=hex(r.cls()))
def W_(label, n=N, shot=True):
    t0 = time.time(); w = T.window(label, n); L(label, info(), 'tot/frame', w['snap']['total'], 'wall', round(time.time() - t0, 1))
    if shot: c.shot('s_' + label)
    json.dump({'W': W, 'H': Hh, 'windows': T.windows, 'series': T.series}, open(OUT + '/profcut_%dx%d.json' % (W, Hh), 'w'))
def fastgo(fr):
    while r.fr < fr: T.frame()
fastgo(895)
# 2 hoạt cảnh mở đầu (route 960+ chưa nạp: đứng yên chờ hội thoại, chỉ bấm OK mỗi 40 khung để thoại tiến)
goto(895)
T.ev = {}
for f in range(900, 3000, 40): T.ev.setdefault(f, []).append('OK')
W_('cut_dialog_start', N)
goto(1000); W_('cut_fx', N)
goto(1200); W_('cut_mid', N)
goto(1500); W_('cut_late', N)
