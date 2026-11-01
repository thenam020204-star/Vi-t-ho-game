#!/usr/bin/env python3
"""Sinh bridge_patches.tsv (T8m) cho libhl5_arm64_bridge.so (arm64): mk_bridge_patches.py BRIDGE.so > bridge_patches.tsv
Đọc byte gốc từ BRIDGE.so (nên mọi dòng đều kiểm được). Dùng keystone để dịch asm tại đúng địa chỉ."""
import sys, struct
from keystone import Ks, KS_ARCH_ARM64, KS_MODE_LITTLE_ENDIAN
ks = Ks(KS_ARCH_ARM64, KS_MODE_LITTLE_ENDIAN)
d = open(sys.argv[1], 'rb').read()
TEXT_VA, TEXT_OFF = 0xc3f80, 0xbff80
def off(va): return va - TEXT_VA + TEXT_OFF
def asm(va, src):
    b, _ = ks.asm(src, va); return bytes(b)
rows = []
def patch(va, src, why, pad_to=None):
    new = asm(va, src)
    if pad_to is not None:
        assert len(new) <= pad_to, (hex(va), len(new), pad_to)
        new += asm(va + len(new), '\n'.join(['nop'] * ((pad_to - len(new)) // 4)))
    old = d[off(va):off(va) + len(new)]
    rows.append('%x\t%s\t%s\t%s' % (va, old.hex(), new.hex(), why))

# (1) tắt hook chữ
patch(0xc6c30, 'b #0xc6d4c', 'bỏ vòng cài 10 hook chữ BFont::* (callback 0xcf64c): nhảy thẳng 0xc6d4c (x22..x27 không được đọc trước khi gán lại)')
patch(0xc6dd8, 'mov w0, #1', 'hook đo GetWidth(Pciii): bỏ bl 0xe8834 (uc_hook_add); w0=1 -> cbnz đi nhánh đã cài')
patch(0xc6e60, 'mov w0, #1', 'hook đo GetByteLenPrintable: như trên')

# (2) khung logic = màn / k; k = min(4, scrH/240), giảm k nếu W<400 hoặc H<240. w25=scrW, w22=scrH; ra w9=W, w8=H (-> 0xc5d38)
patch(0xc5ce8, """
 cmp w22, #240
 b.lo #0xc5d38
 mov w10, #240
 udiv w11, w22, w10
 cmp w11, #4
 mov w12, #4
 csel w11, w11, w12, ls
 udiv w9, w25, w11
 udiv w8, w22, w11
 cmp w11, #1
 b.ls #0xc5d2c
 cmp w9, #400
 b.lo #0xc5d24
 cmp w8, #240
 b.hs #0xc5d2c
 sub w11, w11, #1
 b #0xc5d04
 and w9, w9, #0xfffffffe
 and w8, w8, #0xfffffffe
""", 'khung logic W=scrW/k, H=scrH/k (làm chẵn xuống), k=min(4,scrH/240) giảm tới khi W>=400 && H>=240; scrH<240 -> 400x240 như cũ', pad_to=0xc5d38 - 0xc5ce8)

# (3a) viewport: hàm 0xe1e38 (x0 = đối tượng GL: [0x50..0x5c] = surfW,surfH,logicW,logicH) -> k nguyên = min(surfW/logicW, surfH/logicH), căn giữa
patch(0xe1e38, """
 ldr x4, [x0, #0x130]
 cbz x4, #0xe1ec4
 mov x15, x0
 ldp w9, w10, [x0, #0x50]
 ldp w11, w12, [x0, #0x58]
 cmp w9, #1
 ccmp w10, #1, #8, ge
 ccmp w11, #1, #8, ge
 ccmp w12, #1, #8, ge
 b.lt #0xe1ec4
 udiv w13, w9, w11
 udiv w14, w10, w12
 cmp w13, w14
 csel w13, w13, w14, ls
 cmp w13, #0
 csinc w13, w13, wzr, ne
 mul w2, w11, w13
 mul w3, w12, w13
 sub w0, w9, w2
 asr w0, w0, #1
 sub w1, w10, w3
 asr w1, w1, #1
 stp w2, w3, [x15, #0x38]
 br x4
""", 'glViewport(x,y,w*k,h*k) căn giữa, k nguyên (NEAREST); lưu [0x38]=vw,[0x3c]=vh', pad_to=0xe1ec4 - 0xe1e38)
# (3b) bản sao inline trong hàm xoá khung (0xe1f84..): gọi hàm trên
patch(0xe1f84, """
 mov x0, x19
 ldr x19, [sp, #0x10]
 ldp x29, x30, [sp], #0x20
 b #0xe1e38
""", 'bản sao tính viewport trong hàm clear: tail-call 0xe1e38')
# (3c) chạm: tính lại viewport giống trên (đầu ra w12=vpW w10=vpH w14=offX w11=offY như mã gốc; 5 lệnh đầu 0xcc6a4..b4 giữ)
patch(0xcc6b8, """
 udiv w13, w11, w12
 udiv w14, w15, w10
 cmp w13, w14
 csel w13, w13, w14, ls
 cmp w13, #0
 csinc w13, w13, wzr, ne
 mul w12, w12, w13
 mul w10, w10, w13
 sub w14, w11, w12
 asr w14, w14, #1
 sub w11, w15, w10
 asr w11, w11, #1
""", 'nativeOnTouchEvent: viewport nguyên k như 0xe1e38 (vpW,vpH,offX,offY)', pad_to=0xcc704 - 0xcc6b8)
# (3d) lọc NEAREST: bộ gọi glTexParameterf (0xe3748): MIN/MAG (0x2801/0x2800) -> 9728.0 (0x2600); cave = callback hook đo đã chết (0xcfdd0)
CAVE = 0xcfdd0
patch(0xe3770, 'b #%#x' % CAVE, 'glTexParameterf: nhảy cave để ép NEAREST cho MIN/MAG')
patch(CAVE, """
 ldr s0, [x9, #8]
 mov w11, #0x2800
 sub w11, w1, w11
 cmp w11, #2
 b.hs #0xe3774
 mov w11, #0x2600
 scvtf s0, w11
 b #0xe3774
""", 'cave (callback hook đo 0xcfdd0, chết sau khi tắt hook): s0 = 9728.0 nếu pname là 0x2800/0x2801')
print('# vaddr(hex)\tbyte gốc\tbyte mới\tlý do (bridge arm64; vaddr = địa chỉ ảo, .text vaddr-0x4000 = file offset). T8m.')
print('\n'.join(rows))
