#!/usr/bin/env python3
"""L0: kiểm icon kỹ năng ở ô nhanh của Túi đồ (bag.c::belt_slot) khi lớp nhân vật (HERO+0x22c) != 0.
Hook mui_cell_i (đối số icon = r2) trong lúc vẽ bước Gán phím tắt; kỳ vọng icon = sk>42 ? sk+130 : 43*lớp + sk (GMenu::DrawSkillBeltIcon 0x7d6c0).
Dùng: check_belt_icon.py [W H]   (WORK mặc định ~/hl5work_l0 qua qlib; kết quả: in ra + exit 1 nếu lỗi)"""
import sys, os
H = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, H)
from qlib import *
from unicorn import UC_HOOK_CODE
from unicorn.arm_const import UC_ARM_REG_R2
W, Hh = (int(sys.argv[1]), int(sys.argv[2])) if len(sys.argv) > 2 else (400, 240)
c = Ctx(W, Hh, 'new', os.path.join(WK, 't17c2/out_belt')); r = c.r; g = c.g
h = c.hero; seen = []
def cb(uc, addr, size, ud): seen.append(uc.reg_read(UC_ARM_REG_R2) & 0xffffffff)
g.uc.hook_add(UC_HOOK_CODE, cb, begin=BASE + c.sym['mui_cell_i'], end=BASE + c.sym['mui_cell_i'])
def run(cls, idx):
    c.wr(h + 0x22c, [cls])
    bank = r.call(0x84ef0, h) & 255
    c.callx(0x84f1c, h, 0, bank, 0, idx)                  # SetHotKeyBelt(slot 0, bank, type 0 = kỹ năng, idx)
    assert (r.call(0x84f3c, h, 0, bank) & 255) == 0 and (r.call(0x84f54, h, 0, bank) & 255) == idx
    f = g.rd32(g.rd32(h) + 0x24); sk = r.call(f - BASE, h, idx) & 255
    sk = sk - 256 if sk > 127 else sk
    seen.clear(); c.S3(2); c.tap(0xA0 + 3); c.tap(0xA0 + 3, 6); c.tap(0xA0 + 0, 8); r.step(4)   # Túi đồ -> ô 3 -> Gán phím tắt (vẽ ô nhanh)
    want = sk + 130 if sk > 42 else 43 * cls + sk
    return {'cls': cls, 'sk': sk, 'got': want if want in seen[-12:] else (seen[-5] if len(seen) >= 5 else None), 'want': want, 'last': seen[-12:], 'calls': len(seen)}   # icon kỳ vọng phải nằm trong các lần vẽ ô cuối (ô nhanh vẽ sau cùng)
CL = (0, 1, 2, 3)
for cls in CL: c.fork(lambda cls=cls: run(cls, 5), 'belt%d' % cls)
import json; bad = 0
for cls in CL:
    d = json.load(open(os.path.join(c.out, 'sc_belt%d_%s_new.json' % (cls, c.tag))))
    ok = d.get('got') == d.get('want') and d.get('got') is not None
    bad += not ok
    print('ĐẠT  ' if ok else 'LỖI  ', d)
print('KẾT LUẬN:', 'ĐẠT' if not bad else 'KHÔNG ĐẠT')
sys.exit(1 if bad else 0)
