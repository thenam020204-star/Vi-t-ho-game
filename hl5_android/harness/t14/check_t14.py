"""T14b: kiểm HUD mới. check_t14.py W H [OUT]. (1) bản mới: ảnh HUD, toạ độ nút mới/vùng chạm gốc đọc từ bộ nhớ; chạm tâm từng nút mới -> chữ ký hành động;
(2) bản gốc: chạm tâm vùng gốc tương ứng -> chữ ký; so khớp; (3) nuốt chạm vùng gốc trống; (4) sửa bố cục: giữ tạm dừng, kéo, Lưu/Mặc định/Huỷ, đọc lại; ghi OUT/res_WxH.json + ảnh."""
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
NM = ['ATT', 'S0', 'S1', 'S2', 'S3', 'S4', 'S5', 'SWAP', 'DASH', 'PAUSE', 'MAP', 'SHOP', 'SAVE', 'DPAD']
res = {'size': [W, Hh]}
r, log = start('new'); S = r.sym['S'] if 'S' in r.sym else None
Sa = BASE + r.sym['S'] if False else None
sa = r.sym['S']; g = r.g
def rdS(off, n): return bytes(g.uc.mem_read(BASE + sa + off, n)) if sa < BASE else bytes(g.uc.mem_read(sa + off, n))
def snap():
    cx = struct.unpack('<14i', rdS(84, 56)); cy = struct.unpack('<14i', rdS(140, 56)); d = struct.unpack('<14i', rdS(196, 56)); want = list(rdS(1, 14))
    old = [struct.unpack('<4h', rdS(252 + 8 * i, 8)) for i in range(14)]
    return dict(cx=cx, cy=cy, d=d, want=want, old=old, edit=rdS(17, 1)[0], custom=rdS(20, 1)[0], sel=struct.unpack('<i', rdS(72, 4))[0])
r.step(2); st = snap(); res['new_layout'] = st
shot(r, os.path.join(OUT, 'hud_new_%dx%d.png' % (W, Hh)))
def newtap(i):
    return fork_do(lambda: dict(zip(('sig', 'cls'), tap(r, log, st['cx'][i], st['cy'][i]))))
res['new'] = {NM[i]: newtap(i) for i in range(13) if st['want'][i]}
# nuốt chạm: tâm vùng gốc của MAP nằm ngoài mọi nút mới
o = st['old'][10]; ox, oy = (o[0] + o[2]) // 2, (o[1] + o[3]) // 2
res['swallow'] = dict(pt=[ox, oy], **fork_do(lambda: dict(zip(('sig', 'cls'), tap(r, log, ox, oy)))))
# sửa bố cục
def edit_test():
    p = (st['cx'][9], st['cy'][9]); r.touch(p[0], p[1], up=False); r.step(2)
    s1 = snap(); r.step(30); s2 = snap()
    shot(r, os.path.join(OUT, 'edit_%dx%d.png' % (W, Hh)))
    out = dict(edit_before=s1['edit'], edit_after_hold=s2['edit'])
    r.g.call(r.touchf, r.g.env, 0, 20, p[0], p[1], 0); r.step(2); out['edit_after_release'] = snap()['edit']
    a0 = (st['cx'][0], st['cy'][0]); tx, ty = W // 2 - 60, Hh // 2 - 90
    r.touch(a0[0], a0[1], up=False); r.step(1); r.move(a0[0] - 10, a0[1] - 10); r.step(1); r.move(tx, ty); r.step(2)
    r.g.call(r.touchf, r.g.env, 0, 20, tx, ty, 0); r.step(2)
    s3 = snap(); out['att_moved_to'] = [s3['cx'][0], s3['cy'][0]]; out['att_target'] = [tx, ty]
    # chặn ra ngoài màn: kéo ATT ra ngoài góc
    r.touch(s3['cx'][0], s3['cy'][0], up=False); r.step(1); r.move(W + 50, Hh + 50); r.step(2); r.g.call(r.touchf, r.g.env, 0, 20, W + 50, Hh + 50, 0); r.step(1)
    s4 = snap(); out['att_clamped'] = [s4['cx'][0], s4['cy'][0]]; out['att_r'] = s4['d'][0] // 2
    # chặn đè nặng: kéo S1 lên đúng tâm S0
    r.touch(s4['cx'][2], s4['cy'][2], up=False); r.step(1); r.move(s4['cx'][1], s4['cy'][1]); r.step(2); r.g.call(r.touchf, r.g.env, 0, 20, s4['cx'][1], s4['cy'][1], 0); r.step(1)
    s5 = snap(); out['s1_after_overlap_try'] = [s5['cx'][2], s5['cy'][2]]; out['s1_before'] = [s4['cx'][2], s4['cy'][2]]
    shot(r, os.path.join(OUT, 'edit_drag_%dx%d.png' % (W, Hh)))
    L_btnH = struct.unpack('<i', bytes(g.uc.mem_read(BASE + r.sym['L'] + 76, 4)))[0]; bh = L_btnH; bw = 4 * bh; gap = bh // 2
    x0 = W // 2 - (3 * bw + 2 * gap) // 2; yy = Hh // 2
    btn = lambda i: (x0 + i * (bw + gap) + bw // 2, yy)
    # Lưu
    bx, by = btn(0); r.touch(bx, by, up=False); r.step(1); r.g.call(r.touchf, r.g.env, 0, 20, bx, by, 0); r.step(3)
    s6 = snap(); out['after_save'] = dict(edit=s6['edit'], custom=s6['custom'], att=[s6['cx'][0], s6['cy'][0]])
    st_dir = r.g.a.storage; fn = [os.path.join(dp, f) for dp, _, fs in os.walk(st_dir) for f in fs if f == 'hud_layout.dat']
    out['file'] = [fn[0].replace(st_dir, ''), os.path.getsize(fn[0])] if fn else None
    # đọc lại: ép nạp lại từ tệp
    g.uc.mem_write(BASE + sa + 19, b'\0\0'); g.uc.mem_write(BASE + sa + 60, struct.pack('<ii', 0, 0)); r.step(3)
    s7 = snap(); out['reloaded'] = dict(custom=s7['custom'], att=[s7['cx'][0], s7['cy'][0]], same=(s7['cx'] == s6['cx'] and s7['cy'] == s6['cy']))
    return out
res['edit'] = fork_do(edit_test)
def edit2():   # vào sửa -> Mặc định -> Huỷ ; vào sửa -> Mặc định -> Lưu (về mặc định)
    p = (st['cx'][9], st['cy'][9]); r.touch(p[0], p[1], up=False); r.step(30); r.g.call(r.touchf, r.g.env, 0, 20, p[0], p[1], 0); r.step(2)
    a0 = (st['cx'][0], st['cy'][0]); r.touch(a0[0], a0[1], up=False); r.step(1); r.move(W // 2, Hh // 2 - 80); r.step(1); r.g.call(r.touchf, r.g.env, 0, 20, W // 2, Hh // 2 - 80, 0); r.step(2)
    moved = snap()['cx'][0]
    L_btnH = struct.unpack('<i', bytes(g.uc.mem_read(BASE + r.sym['L'] + 76, 4)))[0]; bh = L_btnH; bw = 4 * bh; gap = bh // 2
    x0 = W // 2 - (3 * bw + 2 * gap) // 2; btn = lambda i: (x0 + i * (bw + gap) + bw // 2, Hh // 2)
    bx, by = btn(1); r.touch(bx, by, up=False); r.step(1); r.g.call(r.touchf, r.g.env, 0, 20, bx, by, 0); r.step(2); s = snap(); defaulted = (s['cx'][0] == st['cx'][0] and s['cy'][0] == st['cy'][0])
    r.touch(a0[0], a0[1], up=False); r.step(1); r.move(W // 2, Hh // 2 - 80); r.step(1); r.g.call(r.touchf, r.g.env, 0, 20, W // 2, Hh // 2 - 80, 0); r.step(1)
    bx, by = btn(2); r.touch(bx, by, up=False); r.step(1); r.g.call(r.touchf, r.g.env, 0, 20, bx, by, 0); r.step(3); s2 = snap()
    return dict(moved_x=moved, default_ok=defaulted, cancel_edit=s2['edit'], cancel_custom=s2['custom'], att_after_cancel=[s2['cx'][0], s2['cy'][0]], att_default=[st['cx'][0], st['cy'][0]])
res['edit2'] = fork_do(edit2)
json.dump(res, open(os.path.join(OUT, 'res_new_%dx%d.json' % (W, Hh)), 'w'))
del r
# --- bản gốc ---
r, log = start('orig'); r.step(2)
shot(r, os.path.join(OUT, 'hud_orig_%dx%d.png' % (W, Hh)))
res['orig'] = {}
for i in range(13):
    if not st['want'][i]: continue
    o = st['old'][i]; ox, oy = (o[0] + o[2]) // 2, (o[1] + o[3]) // 2
    res['orig'][NM[i]] = dict(pt=[ox, oy], **fork_do(lambda: dict(zip(('sig', 'cls'), tap(r, log, ox, oy)))))
ok = True; rows = []
for k in res['new']:
    a, b = res['new'][k], res['orig'][k]; same = a['sig'] == b['sig'] and a['cls'] == b['cls']; ok &= same
    rows.append('%s %-5s new=%s orig@%s=%s' % ('DAT' if same else 'LECH', k, a['sig'][:6], b['pt'], b['sig'][:6]))
res['swallow_ok'] = not [n for n in res['swallow']['sig'] if n.endswith('KeyPressed') or 'Set' in n]
res['rows'] = rows; res['all_same'] = ok
json.dump(res, open(os.path.join(OUT, 'res_%dx%d.json' % (W, Hh)), 'w'), indent=1)
print('\n'.join(rows)); print('swallow', res['swallow'], res['swallow_ok']); print('edit', res['edit']); print('edit2', res['edit2']); print('ALL_SAME', ok)
