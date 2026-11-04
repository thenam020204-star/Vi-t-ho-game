"""Dò lời gọi hàm guest trong 1 khung vẽ của NPC state. trace.py W H TYPE [extra-actions] ; actions: key:NAME, step:N, set8:ADDR:VAL
Hook vào mọi hàm khớp REGEX (nm của .so gốc)."""
import sys, os, re, subprocess, struct
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l2'))
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't17c2'))
from qlib import *
from unicorn import UC_HOOK_CODE
from unicorn.arm_const import *
W, Hh, MODE = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3]
typ = int(sys.argv[4]); acts = sys.argv[5:]
REGEX = os.environ.get('RX', r'^(Graphic::(Draw|Fill)|BFont::Draw|ItemInfo::Draw|CommonUi::|HERO::|BagItem::|NpcShop::|ItemTable::|RefineItem::|StateInNpcMenu::|EquipItem|GMenu::)')
SO = os.path.expanduser('~/hl5work_l2/apk/assets/libHeroesLore5.so')
nm = subprocess.run(['llvm-nm', '-C', '--defined-only', SO], capture_output=True, text=True).stdout.splitlines()
funcs = {}
for l in nm:
    p = l.split(' ', 2)
    if len(p) == 3 and re.search(REGEX, p[2]) and p[1] in 'tT':
        funcs[int(p[0], 16)] = re.sub(r'\(.*', '', p[2])
c = Ctx(W, Hh, MODE, '/tmp/claude-0/o1')
r = c.r; uc = c.g.uc
log = []; on = [False]
def hook(uc_, addr, size, ud):
    if not on[0]: return
    a = addr - BASE
    if a in funcs: log.append((funcs[a], [uc.reg_read(x) for x in (UC_ARM_REG_R0, UC_ARM_REG_R1, UC_ARM_REG_R2, UC_ARM_REG_R3)]))
for a in funcs: uc.hook_add(UC_HOOK_CODE, hook, begin=BASE + a, end=BASE + a)
uc.ctl_flush_tb()
r.call(0x42b08, 0, typ, 1); c.step(20)
for a in acts:
    k, _, v = a.partition(':')
    if k == 'key': c.key(v, 4)
    elif k == 'step': c.step(int(v))
    elif k == 'set8': ad, vv = v.split(':'); tp = c.top(); c.wr(tp + int(ad, 0), [int(vv, 0)])
    elif k == 'money': c.wr(c.hero + 6080, struct.pack('<i', int(v)))
on[0] = True; c.step(2); on[0] = False
seen = 0
for n, args in log:
    print(n, ' '.join('%x' % x if x > 0xffff else str(x if x < 0x80000000 else x - (1 << 32)) for x in args))
