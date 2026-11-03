#!/usr/bin/env python3
"""T17p: thêm đoạn PT_LOAD thử vào libHeroesLore5.so (bản v0.2-hd đã vá) và móc 1 hàm nhỏ chạy mỗi khung.

Dùng: mk_ptload_test.py IN.so OUT.so [patches_out.tsv]
Tái lập, không sửa tay. Mọi kiểm tra byte gốc đều thoát lỗi nếu lệch.

Đoạn mới (vaddr 0x171000, ngay sau bss đã nới đến 0x1705a8):
  +0x0000..0x0fff  (file-backed, filesz 0x1000)  mã payload + bảng màu
  +0x1000..0x1fff  (zero-fill,  memsz  0x2000)   dữ liệu: +0 bộ đếm khung, +4 cờ kiểm zero-fill (1 ok, 2 hỏng),
                                                  +8..+0x1f và +0xffc canary (phải = 0 lúc nạp), +0x20 vùng 8x8 điểm ảnh (RGB565)
phdr[4] PT_GNU_STACK (bridge ElfLoader bỏ qua p_type != 1) -> PT_LOAD.
Móc: CUSTOM_grpFlushLcd (0x3d9d4) gọi MC_grpFlushLcd tại 0x3da08 (mỗi khung của game).
     `bl MC_grpFlushLcd` -> `bl payload`; payload chép lại 2 đối số trên ngăn xếp, gọi MC_grpFlushLcd như cũ,
     rồi (chỉ khi cờ r0 == 0, màn chính) nạp thêm chấm 8x8 vào texture màn hình bằng
     Texture2D::bindTextureFromDirect (cùng đường GL mà chính game dùng), rồi trả về như bl gốc.
Chấm 8x8 ở góc trên-trái màn logic: viền 2 px TRẮNG (zero-fill đúng) hoặc MAGENTA (zero-fill hỏng), lõi 4x4 đổi
đỏ -> xanh lá -> xanh dương -> vàng mỗi 16 khung.
Mọi call tới hàm guest là bl tương đối (PIC); dữ liệu truy cập PC-relative; không cần relocation.
"""
import struct, sys
from keystone import Ks, KS_ARCH_ARM, KS_MODE_ARM

VA = 0x171000            # đầu đoạn mới
DATA = VA + 0x1000       # vùng zero-fill
HOOK_VA = 0x3da08        # bl MC_grpFlushLcd trong CUSTOM_grpFlushLcd
HOOK_OLD = bytes.fromhex('b42b04eb')
FLUSH, GETINST, GETWIPI, BINDDIRECT = 0x1488e0, 0x14c468, 0x14c4f8, 0x14e050
GNU_STACK = struct.pack('<8I', 0x6474e551, 0, 0, 0, 0, 0, 6, 4)

SRC = """
  push {r4, r5, r6, lr}
  mov r5, r0
  ldr r4, [sp, #16]
  ldr r6, [sp, #20]
  push {r4, r6}
  bl %(FLUSH)#x
  add sp, sp, #8
  cmp r5, #0
  bne done
  bl %(GETINST)#x
  bl %(GETWIPI)#x
  cmp r0, #0
  beq done
  mov r5, r0
  add r6, pc, #0x1000
  sub r6, r6, #%(D)d
  ldr r0, [r6]
  cmp r0, #0
  bne nochk
  ldr r1, [r6, #8]
  ldr r2, [r6, #12]
  orr r1, r1, r2
  ldr r2, [r6, #16]
  orr r1, r1, r2
  ldr r2, [r6, #20]
  orr r1, r1, r2
  ldr r2, [r6, #24]
  orr r1, r1, r2
  ldr r2, [r6, #28]
  orr r1, r1, r2
  add r3, r6, #0xf00
  ldr r2, [r3, #0xfc]
  orrs r1, r1, r2
  movne r1, #2
  moveq r1, #1
  str r1, [r6, #4]
nochk:
  add r0, r0, #1
  str r0, [r6]
  mov r1, r0, lsr #4
  and r1, r1, #3
  adr r2, table
  mov r1, r1, lsl #1
  ldrh r4, [r2, r1]
  ldr r1, [r6, #4]
  cmp r1, #2
  ldrh r3, [r2, #8]
  ldrheq r3, [r2, #10]
  mov r1, #0
fill:
  and r12, r1, #7
  sub r12, r12, #2
  mov r0, r1, lsr #3
  sub r0, r0, #2
  cmp r12, #4
  cmplo r0, #4
  add r0, r6, r1, lsl #1
  add r0, r0, #0x20
  strhlo r4, [r0]
  strhhs r3, [r0]
  add r1, r1, #1
  cmp r1, #64
  blt fill
  mov r0, r5
  mov r1, #0
  mov r2, #0
  mov r3, #8
  add r12, r6, #0x20
  mov r4, #8
  sub sp, sp, #8
  str r4, [sp]
  str r12, [sp, #4]
  bl %(BIND)#x
  add sp, sp, #8
done:
  pop {r4, r5, r6, pc}
.balign 4
table:
  .short 0xF800, 0x07E0, 0x001F, 0xFFE0, 0xFFFF, 0xF81F
"""


def build_payload():
    ks = Ks(KS_ARCH_ARM, KS_MODE_ARM)
    prm = dict(FLUSH=FLUSH, GETINST=GETINST, GETWIPI=GETWIPI, BIND=BINDDIRECT, D=0)
    code, _ = ks.asm(SRC % prm, VA)          # lượt 1: vị trí lệnh `add r6, pc, #0x1000`
    # lệnh thứ 22 (0-based) là `add r6, pc, #0x1000`: đếm theo dòng
    lines = [l.strip() for l in (SRC % prm).split('\n') if l.strip() and not l.strip().endswith(':')
             and not l.strip().startswith('.')]
    idx = next(i for i, l in enumerate(lines) if l.startswith('add r6, pc, #0x1000'))
    a = VA + 4 * idx
    d = a + 8 + 0x1000 - DATA
    assert 0 <= d < 256, d
    prm['D'] = d
    code, _ = ks.asm(SRC % prm, VA)
    code = bytes(code)
    assert len(code) < 0x400
    return code + bytes(0x1000 - len(code))


def main():
    src, dst = sys.argv[1], sys.argv[2]
    b = bytearray(open(src, 'rb').read())
    phoff = struct.unpack_from('<I', b, 0x1c)[0]
    phnum = struct.unpack_from('<H', b, 0x2c)[0]
    assert phnum == 5 and phoff == 52
    assert bytes(b[phoff + 4 * 32: phoff + 5 * 32]) == GNU_STACK, 'phdr[4] không phải PT_GNU_STACK như mong đợi'
    # PT_LOAD cuối hiện có: kiểm vaddr đầu mới nằm sau bss
    end = 0
    for i in range(phnum):
        t, off, va, pa, fs, ms, fl, al = struct.unpack_from('<8I', b, phoff + 32 * i)
        if t == 1: end = max(end, va + ms)
    assert end <= VA, hex(end)
    assert bytes(b[HOOK_VA:HOOK_VA + 4]) == HOOK_OLD, 'byte gốc tại móc lệch'
    while len(b) % 0x1000: b.append(0)
    off = len(b)
    payload = build_payload()
    b += payload
    ph = struct.pack('<8I', 1, off, VA, VA, len(payload), 0x2000, 7, 0x1000)
    b[phoff + 4 * 32: phoff + 5 * 32] = ph
    ks = Ks(KS_ARCH_ARM, KS_MODE_ARM)
    new, _ = ks.asm('bl %#x' % VA, HOOK_VA)
    new = bytes(new)
    b[HOOK_VA:HOOK_VA + 4] = new
    open(dst, 'wb').write(b)
    print('PT_LOAD mới: offset %#x vaddr %#x filesz %#x memsz %#x; móc %#x %s -> %s; file %d B'
          % (off, VA, len(payload), 0x2000, HOOK_VA, HOOK_OLD.hex(), new.hex(), len(b)))
    if len(sys.argv) > 3:
        with open(sys.argv[3], 'w', encoding='utf-8') as f:
            f.write('# sinh bởi patch/mk_ptload_test.py (T17p, chỉ bản thử --test-ptload; KHÔNG nằm trong so_patches.tsv)\n')
            f.write('# vaddr\tbyte gốc\tbyte mới\tlý do\n')
            f.write('%x\t%s\t%s\tT17p: bl MC_grpFlushLcd -> bl payload 0x%x (PT_LOAD thử)\n' % (HOOK_VA, HOOK_OLD.hex(), new.hex(), VA))
            f.write('# phdr[4] (file off 0xb4): PT_GNU_STACK -> PT_LOAD off=%#x va=%#x filesz=0x1000 memsz=0x2000 flags=7 align=0x1000\n' % (off, VA))


if __name__ == '__main__':
    main()
