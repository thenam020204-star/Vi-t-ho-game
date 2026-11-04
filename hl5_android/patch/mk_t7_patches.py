#!/usr/bin/env python3
"""L1/T7: sinh khối '# >>> L1 T7' trong so_patches.tsv (patch byte logic offline) + patch/t7_patch_diff.txt (diff disasm + lần luồng).
Dùng: mk_t7_patches.py SO_GOC [--update-repo]   (SO_GOC = apk/assets/libHeroesLore5.so). Điểm móc `bl` (netConnect/SetPopupState -> t7.c) do engine/menu/inject_menu.py sinh (hooks.def)."""
import os, struct, sys
from capstone import Cs, CS_ARCH_ARM, CS_MODE_ARM
HERE = os.path.dirname(os.path.abspath(__file__)); START, END = '# >>> L1 T7', '# <<< L1 T7'
P = [  # (vaddr, mnemonic cũ mong đợi, word mới, lý do)
 (0xd9988, 'ldrh', 0xE3A0B000, 'T7 cash: giá (rec+18) -> 0: ldrh r11,[r3,#18] -> mov r11,#0 (StateCashShop::KeyPressed, popup xác nhận ghi giá 0)'),
 (0xd9c2c, 'ldrheq', 0x03A0B000, 'T7 cash: giá (rec+20) -> 0: ldrheq r11,[r5,#20] -> moveq r11,#0'),
 (0xd9c30, 'ldrhne', 0x13A0B000, 'T7 cash: giá (rec+16) -> 0: ldrhne r11,[r5,#16] -> movne r11,#0'),
 (0xdbf40, 'bgt', 0xEA000010, 'T7 hồi sinh: KeyGameOver `bgt 0xdbf70` (có Bùa -> trừ Bùa) -> `b 0xdbf88` (Revival() + SetState(4) luôn, không cần/không trừ Bùa, không SetProductID(26)+netConnect(5))'),
]
def v2off(d, va):
    po = struct.unpack_from('<I', d, 0x1c)[0]; n = struct.unpack_from('<H', d, 0x2c)[0]
    for i in range(n):
        t, off, vad, _, fsz = struct.unpack_from('<5I', d, po + 32 * i)
        if t == 1 and vad <= va < vad + fsz: return va - vad + off
    raise ValueError(hex(va))
d = open(sys.argv[1], 'rb').read(); md = Cs(CS_ARCH_ARM, CS_MODE_ARM)
def dis(w, va): i = next(md.disasm(struct.pack('<I', w), va)); return '%s %s' % (i.mnemonic, i.op_str)
rows, diff = [], ['# L1/T7 diff disasm (cũ -> mới)']
for va, mn, new, why in P:
    o = v2off(d, va); old = struct.unpack_from('<I', d, o)[0]
    cur = dis(old, va)
    assert cur.split()[0] == mn, (hex(va), cur)
    rows.append('%x\t%s\t%s\t%s' % (va, struct.pack('<I', old).hex(), struct.pack('<I', new).hex(), why))
    diff.append('%x: %-28s -> %s' % (va, cur, dis(new, va)))
diff += ['', 'Lần luồng tới hết hàm bao:',
 '- KeyPressed 0xd97ac: 3 lệnh ldrh giá -> mov r11,#0, r11 chỉ dùng làm đối số sprintk (0xd99e4 `lsl r3,r11,#16`) rồi SetPopupState(2) -> pop (0xd9a28..0xd9a30). Không rẽ nhánh theo r11.',
 '- KeyGameOver 0xdbe90: `b 0xdbf88` -> `ldr r0,[r6,r5]` (r6,r5 đã đặt ở 0xdbf24/0xdbf20) -> HERO::Revival() -> mov r0,r4; r1=4; pop {r4-r8,lr}; b StateGame::SetState(int) 0xdbc54 (= đường đã có khi có Bùa). 0xdbf44..0xdbf6c (SetProductID(26)+netConnect(5)) không còn tới được.',
 '- Điểm móc bl (hooks.def, inject_menu.py): 0xd9330 netConnect(3), 0xd9350 SetPopupState, 0xdc188 netConnect(5) -> t7.c (không gọi MTX/NET; mode khác 3/5 vẫn chuyển cho netConnect gốc).']
open(os.path.join(HERE, 't7_patch_diff.txt'), 'w', encoding='utf-8').write('\n'.join(diff) + '\n')
print('\n'.join(rows))
if '--update-repo' in sys.argv:
    t = os.path.join(HERE, 'so_patches.tsv'); old = open(t, encoding='utf-8').read().split('\n')
    if START in old: i, j = old.index(START), old.index(END); old = old[:i] + old[j + 1:]
    k = old.index('# >>> T17c menu-vtable') if '# >>> T17c menu-vtable' in old else len(old)
    old[k:k] = [START] + rows + [END]
    open(t, 'w', encoding='utf-8').write('\n'.join(old))
    print('đã cập nhật so_patches.tsv')
