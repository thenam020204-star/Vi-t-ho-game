"""T21: đo chi phí ở mép map theo chế độ: số byte-khối cơ bản (UC_HOOK_BLOCK) mỗi khung, 30 khung, sau khi giữ LEFT 90 tick. perf_t21.py W H"""
import sys, os, json, struct
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'l1'))
from qlib import *
from hl5libc import KEYS
from unicorn import UC_HOOK_BLOCK
W, Hh = int(sys.argv[1]), int(sys.argv[2])
c = Ctx(W, Hh, 'new', os.path.expanduser('~/hl5work_t22/perf'), boot_to=1300); r = c.r; g = c.g
def setv(n, v): c.wr(BASE + c.sym[n], struct.pack('<i', v))
g.call(r.keyf, g.env, 0, 2, KEYS['LEFT'] & 0xffffffff, 0)
for _ in range(90): r.step(1)
g.call(r.keyf, g.env, 0, 3, KEYS['LEFT'] & 0xffffffff, 0)
cnt = [0]
cm = [0]
def hb(uc, a, s, u):
    cnt[0] += s
    if BASE + 0x171000 <= a < BASE + 0x171000 + 0x400000: cm[0] += s
def run(on, mode):
    def f():
        setv('camc_on', on); setv('camc_mode', mode); setv('ring_on', on)
        h = g.uc.hook_add(UC_HOOK_BLOCK, hb); g.uc.ctl_flush_tb(); cnt[0] = 0; cm[0] = 0
        for _ in range(30): r.step(1)
        return {'blk_per_frame': cnt[0] // 30, 'menu_per_frame': cm[0] // 30, 'frames': r.fr, 'dx': struct.unpack('<i', bytes(g.uc.mem_read(BASE + c.sym['camc_last_dx'], 4)))[0]}
    return f
for nm, on, mode in [('tat', 0, 2), ('den', 1, 2), ('dom_dom', 1, 0), ('phan_chieu', 1, 1)]:
    c.fork(run(on, mode), nm)
out = {nm: json.load(open(os.path.join(c.out, 'sc_%s_%dx%d_new.json' % (nm, W, Hh)))) for nm in ['tat', 'den', 'dom_dom', 'phan_chieu']}
print(json.dumps(out))
