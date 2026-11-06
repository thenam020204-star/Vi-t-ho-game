"""T23: kịch bản đo toàn game. scen.py W H  -> ~/hl5work_t23/out/prof_WxH.json
Dòng thời gian: tiêu đề/chọn lớp -> hoạt cảnh mở đầu + hội thoại -> chơi thường (đứng/đi/đánh) -> menu trong game -> mép map."""
import sys, json, time; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__))) if False else None
sys.path.insert(0, '/home/user/Vi-t-ho-game/hl5_android/harness/t23')
from t23lib import *
W, Hh = int(sys.argv[1]), int(sys.argv[2]); N = int(os.environ.get('NF', 30))
c = mk(W, Hh, 0); r = c.r; g = c.g; T = Timeline(c); T.addroute(0, 960)
OUT = os.path.expanduser('~/hl5work_t23/out'); log = open(OUT + '/scen_%dx%d.log' % (W, Hh), 'w')
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
    json.dump({'W': W, 'H': Hh, 'windows': T.windows, 'series': T.series}, open(OUT + '/prof_%dx%d.json' % (W, Hh), 'w'))
# 1 tiêu đề / chọn lớp
FAST = os.environ.get('FAST')
def fastgo(fr):
    while r.fr < fr: T.frame()
if FAST: fastgo(895)
else:
    goto(100); W_('title', 20)
    goto(300); W_('select_a', 20)
    goto(560); W_('select_b', 20)
    goto(700); W_('select_c', 20)
# 2 hoạt cảnh mở đầu (route 960+ chưa nạp: đứng yên chờ hội thoại, chỉ bấm OK mỗi 40 khung để thoại tiến)
goto(895)
T.ev = {}
for f in range(900, 3000, 40): T.ev.setdefault(f, []).append('OK')
W_('cut_dialog_start', N)
goto(1000); W_('cut_fx', N)
goto(1200); W_('cut_mid', N)
goto(1500); W_('cut_late', N)
# tiếp cutscene tới khi e75==0 hoặc 3000
while r.fr < 3000 and info()['e75'] != 0: T.run_light(10, 'cut')
L('cutscene end', info())
np_ = {}
# 3 chơi thường
T.ev = {}
T.run_light(40, 'settle'); W_('play_idle', N)
for f in range(r.fr, r.fr + 200): pass
T.ev = {r.fr: ['+RIGHT'], r.fr + N + 5: ['-RIGHT']}
W_('play_move_right', N)
T.run_light(10, 'x'); T.ev = {r.fr: ['+LEFT'], r.fr + N + 5: ['-LEFT']}
W_('play_move_left', N)
# đánh: OK liên tục
T.ev = {r.fr + i: ['OK'] for i in range(0, N + 5, 3)}
W_('play_attack_key', N)
# 4 menu trong game
for nm, n in [('menu3x3', 0), ('bag', 2), ('equip', 3), ('skill', 7), ('quest', 8), ('system', 9), ('char', 1)]:
    T.ev = {}
    c.S3(n, 10); T.ev = {r.fr + i: ['RIGHT' if (i // 5) % 2 == 0 else 'DOWN'] for i in range(2, N, 6)}
    W_('menu_' + nm, N)
    for _ in range(4):
        c.key('-16', 6)
T.ev = {}
c.step(10)
# 5 mép map: giữ LEFT 90 tick
T.ev = {r.fr: ['+LEFT']}; T.run_light(90, 'edge'); W_('edge_left', N)
L('done', info())
