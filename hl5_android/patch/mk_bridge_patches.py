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
patch(0xc6dd8, 'mov w0, #0', 'hook đo GetWidth(Pciii): bỏ bl 0xe8834 (uc_hook_add); w0=0 = thành công (cbnz w0 -> 0xc8cec là throw); T13b sửa từ #1')
patch(0xc6e60, 'mov w0, #0', 'hook đo GetByteLenPrintable: như trên (w0=0 = thành công)')

# (2) khung logic = màn / k (T10). k = k_user (file hd_scale.txt trong filesDir, '1'..'4'; không có/hỏng -> DEFAULT_K),
#     kẹp: k <= scrH/240, rồi giảm k tới khi W>=400 && H>=240; scrH<240 -> 400x240 như cũ.
#     0xc5ce8 nhảy vào CAVE_K (callback hook chữ 0xcf64c, chết vì vòng cài hook đã bị bỏ qua), cave đọc file rồi nhảy về 0xc5d38.
#     Vào: w25=scrW, w22=scrH, x20=env, x24=jstring internalPath (filesDir). Ra: w9=W, w8=H (-> 0xc5d38). Giữ nguyên x19..x28, sp.
sys.path.insert(0, __import__('os').path.dirname(__import__('os').path.abspath(__file__)))
from hd_cfg import DEFAULT_K   # <<< hằng duy nhất (patch/hd_cfg.py): k mặc định khi không có hd_scale.txt
CAVE_K = 0xcf64c
PLT = {'snprintf': 0x1d2b90, 'open': 0x1d2ad0, 'read': 0x1d2ae0, 'close': 0x1d2ab0}
def cave_k():
    def src():
        return """
 stp x29, x30, [sp, #-0x140]!
 mov x29, sp
 stp x19, x21, [sp, #0x10]
 mov w21, #%d
 cbz x24, calc
 ldr x8, [x20]
 ldr x8, [x8, #0x548]
 mov x0, x20
 mov x1, x24
 mov x2, xzr
 blr x8
 mov x19, x0
 cbz x19, calc
 add x0, sp, #0x20
 mov x1, #256
 adr x2, fmt
 mov x3, x19
 bl #%#x
 ldr x8, [x20]
 ldr x8, [x8, #0x550]
 mov x0, x20
 mov x1, x24
 mov x2, x19
 blr x8
 str xzr, [sp, #0x128]
 add x0, sp, #0x20
 mov w1, #0
 bl #%#x
 mov w19, w0
 tbnz w19, #31, calc
 mov w0, w19
 add x1, sp, #0x128
 mov x2, #8
 bl #%#x
 mov w0, w19
 bl #%#x
 ldrb w8, [sp, #0x128]
 ldrb w9, [sp, #0x129]
 sub w8, w8, #0x31
 cmp w8, #3
 b.hi calc
 cmp w9, #0x21
 b.hs calc
 add w21, w8, #1
calc:
 mov w9, #400
 mov w8, #240
 cmp w22, #240
 b.lo fin
 mov w10, #240
 udiv w11, w22, w10
 cmp w11, w21
 csel w11, w11, w21, ls
loop:
 udiv w9, w25, w11
 udiv w8, w22, w11
 cmp w11, #1
 b.ls ev
 cmp w9, #400
 b.lo dec
 cmp w8, #240
 b.hs ev
dec:
 sub w11, w11, #1
 b loop
ev:
 and w9, w9, #0xfffffffe
 and w8, w8, #0xfffffffe
fin:
 ldp x19, x21, [sp, #0x10]
 ldp x29, x30, [sp], #0x140
 b #0xc5d38
fmt:
.asciz "%%s/hd_scale.txt"
""" % (DEFAULT_K, PLT['snprintf'], PLT['open'], PLT['read'], PLT['close'])
    return asm(CAVE_K, src())
_cave = cave_k()
assert CAVE_K + len(_cave) <= 0xcfdd0, hex(CAVE_K + len(_cave))
patch(0xc5ce8, 'b #%#x' % CAVE_K, 'T10: W/H do cave đọc hd_scale.txt (k=1..4, mặc định DEFAULT_K=%d), kẹp W>=400,H>=240; vùng thừa nop' % DEFAULT_K, pad_to=0xc5d38 - 0xc5ce8)
_old = d[off(CAVE_K):off(CAVE_K) + len(_cave)]
rows.append('%x\t%s\t%s\t%s' % (CAVE_K, _old.hex(), _cave.hex(), 'T10: cave_k (callback hook chữ 0xcf64c, chết): đọc <filesDir>/hd_scale.txt, tính W/H, nhảy 0xc5d38; sau mã: "%s" + "/hd_scale.txt"'))

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
