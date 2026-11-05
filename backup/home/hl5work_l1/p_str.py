import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l1'))
sys.path.insert(0, '/home/user/Vi-t-ho-game/hl5_android/harness/l1')
from qlib import *
c = Ctx(400, 240, 'orig', os.path.expanduser('~/hl5work_l1/p_res'))
r5 = 0x15e774 + 0xc
for nm, v in (('734', 0xffff4520), ('740', 0xffff62f8), ('74c', 0xffff6230), ('750', 0xffff6300)):
    a = (r5 + v) & 0xffffffff
    print(nm, hex(a), bytes(c.g.uc.mem_read(BASE + a, 24)))
