"""L6b: chụp Chuyên Gia sub 1 / Thành tựu / Trợ giúp (mới). Dùng: shots_mm2.py W H MODE OUTDIR [what...]   MODE new|orig|off"""
import sys, os, signal
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l6'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'l3'))
from qlib import *
W, Hh, MODE, OUT = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4]; what = sys.argv[5:] or ['expert', 'mission', 'help']
PFX = os.environ.get('PFX', 'mm_')
c = Ctx(W, Hh, 'new' if MODE == 'off' else MODE, OUT, boot_to=10); r = c.r; c.mode = MODE
if MODE == 'off': c.g.uc.mem_write(BASE + c.sym['mui_flags'], (1).to_bytes(4, 'little'))
r.step(700); r.key('OK'); r.step(10)
T6 = [0, 1, 4, 5, 6, 8]
def grid_to(o):
    if MODE == 'new': c.tap(0x10 + T6.index(o), 6)
    else:
        for k in ['DOWN'] * (o // 3) + ['RIGHT'] * (o % 3) + ['OK']: c.key(k, 3)
        c.step(4)
def run(nm, fn):
    pid = os.fork()
    if pid: os.waitpid(pid, 0); return
    try: signal.alarm(280); fn(); c.shot(nm)
    except Exception as e: import traceback; print('ERR', nm, e, traceback.format_exc(), flush=True)
    finally: os._exit(0)
def expert():
    grid_to(1); t = c.top(); c.wr(t + 0xc, [6, 1]); c.step(4)
    t = c.top(); print('expert', c.st(), c.sub(), c.s8(t, 0xf), flush=True)
def mission():
    grid_to(5); c.step(4); print('mission', c.st(), c.sub(), flush=True)
def help1():
    grid_to(6); c.step(4)
    if MODE == 'new': c.tap(0x50, 6)
    else: c.key('OK', 4)
    c.step(4); print('help1', c.st(), c.sub(), flush=True)
def mission_tap():
    grid_to(5); c.step(4)
    if MODE == 'new': c.tap(0x52, 3)
    else: c.touch_xy(W // 2, Hh // 2, 3)
    print('mission_tap', c.st(), c.sub(), flush=True)
def help_():
    grid_to(6); c.step(4); print('help', c.st(), c.sub(), flush=True)
for w in what:
    run(PFX + w, {'expert': expert, 'mission': mission, 'help': help_, 'help1': help1, 'mission_tap': mission_tap}[w])
