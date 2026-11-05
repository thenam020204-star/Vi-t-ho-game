"""L6a: dò quyết định dispatch (menu mới / rơi về gốc) cho (lớp, state, sub) mà KHÔNG chạy Paint thật: móc địa chỉ hàm gốc và hàm paint mới, ghi tên rồi trả về ngay."""
import sys, os, re
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from qlib import *
from unicorn import UC_HOOK_CODE
from unicorn.arm_const import UC_ARM_REG_LR, UC_ARM_REG_PC

CLS = {'GAME': 'StateGame', 'POPUP': 'StatePopup', 'INGAME': 'StateInGameMenu', 'NPC': 'StateInNpcMenu', 'MAIN': 'StateMainMenu', 'EXPERT': 'StateInExpertMenu', 'MAP': 'StateMap', 'CASH': 'StateCashShop'}
ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), '..', '..'))

def new_paints():
    """tên paint mới trong states.def"""
    out = []
    for l in open(ROOT + '/engine/menu/states.def'):
        m = re.match(r'MUI_STATE\((\w+), (\w+), .*, (\w+), (\w+), (\w+)\)$', l.strip())
        if m: out.append((m.group(1), m.group(2), m.group(3)))
    return out

class Probe:
    def __init__(self, c):
        self.c = c; self.g = c.g; uc = c.g.uc
        self.names = {}
        for cl, cn in CLS.items():
            a = c.sym.get(cn + '_Paint'); 
            if a: self.names[BASE + a] = ('orig', cl)
        for cl, nm, p in new_paints():
            a = c.sym.get(p)
            if a: self.names[BASE + a] = ('new:' + nm, cl)
        self.hit = None
        uc.hook_add(UC_HOOK_CODE, self.cb, begin=min(self.names), end=max(self.names))
        try: uc.ctl_flush_tb()
        except Exception as e: print("flush", e)
    def cb(self, uc, addr, size, ud):
        if addr in self.names and self.hit is None:
            self.hit = self.names[addr][0]
            uc.reg_write(UC_ARM_REG_PC, uc.reg_read(UC_ARM_REG_LR))
    def decide(self, cls, obj):
        """gọi thunk paint của lớp cls trên obj; trả 'orig' hoặc 'new:<dòng>'"""
        self.hit = None
        self.g.nested_call(BASE + self.c.sym['mui_paint_' + cls], obj)
        return self.hit or '?'
