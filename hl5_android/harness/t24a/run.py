"""T24a: so ảnh từng khung (md5) + đo byte-khối/khung, base vs new.
Dùng:  WORK=~/hl5work_t24a DEVDIR=<thư mục dev.so> python3 harness/t24a/run.py W H TAG
Ra: ~/hl5work_t24a/out/<TAG>_<W>x<H>.json  {nhãn: {md5:[..], bytes:[..(khung đo)], fr0}}
Kịch bản (mỗi cửa sổ 100 khung, md5 mọi khung; đo byte-khối 20 khung đầu/cửa sổ bằng UC_HOOK_BLOCK):
  mm_grid (menu chính lưới, khung 520..) mm_slot (chọn lớp, 650..) ; cut_* (hoạt cảnh+hội thoại chân dung, fork tại 895: 895..1300, OK mỗi 40);
  play_idle/play_right/play_attack, menu_3x3/bag/skill (từ khung 1900 theo route đầy đủ, như harness/t23/scen_play.py)."""
import sys, os, json, hashlib, time
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_t24a'))
H = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, os.path.join(H, '..', 'l1'))
from qlib import *
from unicorn import UC_HOOK_BLOCK
from hl5libc import KEYS as KEYS_
W, Hh, TAG = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3]
ONLY = sys.argv[4].split(',') if len(sys.argv) > 4 else None     # tập con: mm,cut,play | mmb (riêng)
OUT = os.path.expanduser('~/hl5work_t24a/out2'); os.makedirs(OUT, exist_ok=True)
c = Ctx(W, Hh, 'new', OUT, boot_to=0); r = c.r; uc = c.g.uc
EV = {}
for it in ROUTE.split(','):
    f, k = it.split(':'); EV.setdefault(int(f), []).append(k)
class Light:
    def __init__(s): s.t = 0; s.h = None
    def hk(s, u, a, sz, _): s.t += sz
    def start(s): s.t = 0; s.h = uc.hook_add(UC_HOOK_BLOCK, s.hk); uc.ctl_flush_tb()
    def stop(s):
        if s.h is not None: uc.hook_del(s.h); s.h = None
        v = s.t; s.t = 0; return v
LT = Light()
def frame(ev):
    for k in ev.get(r.fr, []):
        if k.startswith('+'): r.g.call(r.keyf, r.g.env, 0, 2, KEYS_[k[1:]] & 0xffffffff, 0)
        elif k.startswith('-') and k[1:] in KEYS_: r.g.call(r.keyf, r.g.env, 0, 3, KEYS_[k[1:]] & 0xffffffff, 0)
        else: r.key(k)
    r.step(1)
def rec(res, label, n, ev, nmeas=20, meas_at=0):
    """n khung; md5 mọi khung; đo byte-khối các khung [meas_at, meas_at+nmeas)"""
    d = {'fr0': r.fr, 'md5': [], 'bytes': []}
    for i in range(n):
        m = meas_at <= i < meas_at + nmeas
        if m: LT.start()
        frame(ev)
        if m: d['bytes'].append(LT.stop())
        d['md5'].append(hashlib.md5(r.shot()).hexdigest())
    res[label] = d
    json.dump(res, open('%s/%s_%dx%d.json' % (OUT, TAG + ('mmb' if ONLY and 'mmb' in ONLY else ''), W, Hh), 'w'))
    print(label, 'fr', r.fr, 'bytes/frame', (sum(d['bytes']) // max(1, len(d['bytes']))), flush=True)
def goto(fr, ev):
    while r.fr < fr: frame(ev)
res = {}
want = lambda k: ONLY is None or k in ONLY
t0 = time.time()
if want('mm'):
    goto(520, EV); rec(res, 'mm_grid', 100, EV)
    goto(650, EV); rec(res, 'mm_slot', 100, EV)
if ONLY and 'mmb' in ONLY:       # menu chính lưới (select_b ở khung 560) và ô/slot (select_c, 700): cửa sổ riêng, tiến trình riêng
    goto(560, EV); rec(res, 'mm_b', 100, EV)
    goto(700, EV); rec(res, 'mm_c', 100, EV)
    json.dump(res, open('%s/%s_%dx%d.json' % (OUT, TAG + 'mmb', W, Hh), 'w')); sys.exit(0)
goto(895, EV)
if want('cut'):
    pid = os.fork()
    if pid == 0:
        try:
            res2 = {}
            ev2 = {}
            for f in range(900, 3000, 40): ev2.setdefault(f, []).append('OK')
            rec(res2, 'cut_a', 100, ev2, 20, 0)
            rec(res2, 'cut_b', 100, ev2, 20, 5)
            rec(res2, 'cut_c', 100, ev2, 20, 0)
            rec(res2, 'cut_d', 100, ev2, 20, 0)
            rec(res2, 'cut_e', 100, ev2, 20, 0)
            json.dump(res2, open('%s/%s_cut_%dx%d.json' % (OUT, TAG, W, Hh), 'w'))
        finally:
            os._exit(0)
    os.waitpid(pid, 0)
    cut = json.load(open('%s/%s_cut_%dx%d.json' % (OUT, TAG, W, Hh))); res.update(cut)
if want('play'):
    goto(1900, EV)
    for _ in range(40): frame({})
    rec(res, 'play_idle', 100, {})
    rec(res, 'play_right', 100, {r.fr: ['+RIGHT'], r.fr + 90: ['-RIGHT']})
    for _ in range(10): frame({})
    rec(res, 'play_attack', 100, {r.fr + i: ['OK'] for i in range(0, 100, 3)})
    for nm, n in [('menu_3x3', 0), ('menu_bag', 2), ('menu_skill', 7), ('menu_equip', 3), ('menu_char', 1)]:
        c.S3(n, 10)
        rec(res, nm, 100, {r.fr + i: ['RIGHT' if (i // 5) % 2 == 0 else 'DOWN'] for i in range(2, 100, 6)})
        for _ in range(4): c.key('-16', 6)
json.dump(res, open('%s/%s_%dx%d.json' % (OUT, TAG, W, Hh), 'w'))
print('done', round(time.time() - t0), 's')
