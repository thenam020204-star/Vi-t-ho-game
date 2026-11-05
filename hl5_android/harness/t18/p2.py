import sys, os; sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't14'))
from t14lib import *
r = mk(600, 270, 'orig', 't18'); to_play(r, 1300)
for b, e, p in r.g.uc.mem_regions(): print(hex(b), hex(e), e - b, p)
print(hex(BASE), r.g.heap.top if hasattr(r.g.heap,'top') else '')
