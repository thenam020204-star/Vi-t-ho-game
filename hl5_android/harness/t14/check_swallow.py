"""T14b: nuốt chạm vùng nút gốc (điểm trong vùng gốc, ngoài mọi nút mới): bản mới không kích hoạt hành động gốc; bản gốc (đối chứng) có. check_swallow.py W H [OUT]"""
import sys, os, re, json, struct; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t14lib import *
from unicorn import UC_HOOK_CODE
W, Hh = int(sys.argv[1]), int(sys.argv[2]); OUT = sys.argv[3] if len(sys.argv) > 3 else os.path.join(REPO, 'shots', 'T14'); os.makedirs(OUT, exist_ok=True)
F0 = 1300
names = {}
for l in open(os.path.expanduser('~/hl5work/so.dis')):
    m = re.match(r'^([0-9a-f]{8}) <((HERO|StateGame|Battle|CommonUi|GMenu|StateInGameMenu|StateMap)::[^(]*)\(', l)
    if m:
        n = m.group(2); b = n.split('::')[1]
        if b.startswith(('Get', 'Is', 'Draw', 'Check', 'Process', 'Paint', '~')) or n.split('::')[0] in ('StateInGameMenu', 'StateMap', 'CommonUi', 'GMenu', 'Battle') and not b.startswith(('Set', 'Key', 'Start', 'Open')): continue
        if n.startswith('StateGame::') and b.startswith(('StateGame', 'Touch', 'Key')) and b != 'KeyPressed': continue
        names[int(m.group(1), 16)] = n
NOISE = ('Proc', 'Calc', 'Init', 'Decrease', 'ProcAuto', 'EventMove', 'ChangeColor', 'ReDraw', 'SetTouchKeypad', 'ApplyBuild', 'HeroBuff', 'ProcHero', 'EventProc')
def sig(lst):
    return [n for n in dict.fromkeys(lst) if not any(n.split('::')[1].startswith(p) for p in NOISE)]
def start(mode):
    r = mk(W, Hh, mode, 'ck'); log = []
    for a in names: r.g.uc.hook_add(UC_HOOK_CODE, lambda uc, a_, s, u: log.append(names[a_ - BASE]), begin=BASE + a, end=BASE + a)
    to_play(r, F0); return r, log
def fork_do(fn):
    rd, wr = os.pipe(); pid = os.fork()
    if pid == 0:
        os.close(rd)
        try: res = fn()
        except Exception as e: res = {'error': repr(e)}
        os.write(wr, json.dumps(res).encode()); os._exit(0)
    os.close(wr); data = b''
    while True:
        c = os.read(rd, 1 << 20)
        if not c: break
        data += c
    os.waitpid(pid, 0); return json.loads(data)
def tap(r, log, x, y, n1=1):
    log.clear(); r.touch(x, y, up=False); r.step(n1); a = list(log); log.clear()
    r.g.call(r.touchf, r.g.env, 0, 20, x, y, 0); r.step(1); b = list(log)
    return sig(a + b), r.cls()

ACT = ('KeyPressed', 'SetMapMode', 'SetState', 'SetSystemMsgUi', 'SaveAll', 'UseSkill', 'BattleUseItem', 'ChangeHotKeyToggle', 'SetDashFlag', 'SetAttackMotion')
NM = ['ATT', 'S0', 'S1', 'S2', 'S3', 'S4', 'S5', 'SWAP', 'DASH', 'PAUSE', 'MAP', 'SHOP', 'SAVE', 'DPAD']
r, log = start('new'); sa = r.sym['S']; g = r.g
def rdS(off, n): return bytes(g.uc.mem_read(BASE + sa + off, n))
r.step(2)
cx = struct.unpack('<14i', rdS(84, 56)); cy = struct.unpack('<14i', rdS(140, 56)); d = struct.unpack('<14i', rdS(196, 56)); want = list(rdS(1, 14))
old = [struct.unpack('<4h', rdS(252 + 8 * i, 8)) for i in range(14)]
def inside_new(x, y): return any(want[i] and (x - cx[i]) ** 2 + (y - cy[i]) ** 2 <= (d[i] // 2 + 2) ** 2 for i in range(14))
pts = []
for i in range(13):
    if not want[i]: continue
    o = old[i]
    for (x, y) in ((o[0] + 2, o[1] + 2), (o[2] - 3, o[1] + 2), (o[0] + 2, o[3] - 3), (o[2] - 3, o[3] - 3)):
        if 0 <= x < W and 0 <= y < Hh and not inside_new(x, y) and not any(abs(x - p[1]) + abs(y - p[2]) < 6 for p in pts): pts.append((NM[i], x, y)); break
res = {'size': [W, Hh], 'pts': pts, 'new': {}, 'orig': {}}
for nm, x, y in pts:
    s = fork_do(lambda: dict(zip(('sig', 'cls'), tap(r, log, x, y))))
    s['fired'] = [n for n in s['sig'] if n.endswith(ACT)]; res['new'][nm] = s
del r
r, log = start('orig'); r.step(2)
for nm, x, y in pts:
    s = fork_do(lambda: dict(zip(('sig', 'cls'), tap(r, log, x, y))))
    s['fired'] = [n for n in s['sig'] if n.endswith(ACT)]; res['orig'][nm] = s
res['swallow_ok'] = all(not v['fired'] for v in res['new'].values())
res['orig_fires'] = {k: bool(v['fired']) for k, v in res['orig'].items()}
json.dump(res, open(os.path.join(OUT, 'res_swallow_%dx%d.json' % (W, Hh)), 'w'), indent=1)
print(W, Hh, 'swallow_ok', res['swallow_ok'], 'pts', pts, 'orig_fires', res['orig_fires'], {k: v['fired'] for k, v in res['new'].items()})
