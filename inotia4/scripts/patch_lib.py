#!/usr/bin/env python3
"""Va nhi phan toi thieu libgame.so (ARM64) cho ban offline.

    python3 patch_lib.py <libgame.so vao> <libgame.so ra> [--disasm] [--group g1,g2]
    python3 patch_lib.py --list [--group g1,g2]
    python3 patch_lib.py --diff <lib A> <lib B> [--group g1,g2] [-v]   # moi byte khac nhau phai nam trong vung patch da khai bao
    python3 patch_lib.py --check-asm                              # dung lai cave `mod`/`view` (llvm-mc/ld.lld) va cac lenh `hud`/`pool`/`hudbtn` (llvm-mc), so voi byte nhung san

Nhom patch (--group, mac dinh = tat ca tru hudbtn): base (0x203e68, da co trong lib TEST4), p1 (T3b, ma hoa save), mod (T8b, mod menu:
3 diem vao 0x18b3e0/0x1c7860/0x1c7a44 + cave 0x18bb00..0x18bbec, xem docs/mod_menu.md), view (T9b, tam nhin: diem vao 0x14709c +
cave 52 B 0x133454..0x133488, doc P o 0x72f78c, chi nhan 100..150, W chan), hud (T9b, 29 diem le HUD: lui vao 24 px ngang / 16 px doc),
pool (T9b, tran pool MEM_Create 8 MB -> 10 MB: 0x145730, 0x145734); xem docs/tam_nhin_hud.md. Nhom hudbtn (T10b, 3 lenh kieu ve
`mov w5,#2` -> `mov w5,#1` tai 0x169ae8/0x169bb4/0x169bfc: lop "tan cong bam" ve alpha thuong thay vi cong; docs/hud_thay_nut.md) chi
di cung atlas HUD moi (build_from_apk.sh --hud) nen KHONG nam trong nhom mac dinh: phai goi ro `--group ...,hudbtn`.
Moi nhom phai ap tron ven: mot nhom vua co patch "ap" vua co patch "da ap" => DUNG.

Moi patch (xem PATCHES): (vaddr, byte cu `old`, byte moi `new`, ly do, refs, asm_moi).
  * Doan byte tai vaddr == old  -> ap.
  * == new                      -> bao "da ap" (lib cua TEST4 da mang patch 0x203e68 tu luc dung ban dau) va bo qua.
  * khac ca hai                 -> DUNG, khong ghi gi (khong va mo).
  * refs = [(vaddr, bytes)]: du lieu/lenh CHI DOC ma patch dua vao (dich cua `adr`, lenh ke tiep). Phai khop dung.
  * Patch cave (vung dai): `old` = None, thay vao do `old_sha256` = sha256 cua `old_len` byte goc (khong nhung 236 B cu vao file).
Doan .text nam o LOAD dau (file offset == vaddr); moi patch phai nam trong [TEXT_BEG, TEXT_END).
--disasm: in llvm-objdump truoc/sau moi patch va so voi `asm_moi` (can llvm-objdump).
"""
import hashlib
import re
import struct
import subprocess
import sys

TEXT_BEG = 0x127580      # .text
TEXT_END = 0x2a81bc      # het .text (sau do __lcxx_override, .plt)


# ------------------------------------------------------------ bo ma hoa lenh ARM64 (kiem lai bang llvm-mc/llvm-objdump)
def _rel(src, dst, bits):
    off = dst - src
    assert off % 4 == 0 and -(1 << (bits + 1)) <= off < (1 << (bits + 1)), 'nhay qua xa: 0x%x -> 0x%x' % (src, dst)
    return (off >> 2) & ((1 << bits) - 1)


def w(x):
    return struct.pack('<I', x)


def b_imm(src, dst):
    """B (nhanh khong dieu kien)."""
    return w(0x14000000 | _rel(src, dst, 26))


def bl(src, dst):
    return w(0x94000000 | _rel(src, dst, 26))


def cbnz_x(rt, src, dst):
    return w(0xB5000000 | (_rel(src, dst, 19) << 5) | rt)


def adr(rd, src, dst):
    off = dst - src
    assert -(1 << 20) <= off < (1 << 20), 'adr qua xa: 0x%x -> 0x%x' % (src, dst)
    return w(0x10000000 | ((off & 3) << 29) | (((off >> 2) & 0x7ffff) << 5) | rd)


MOV_X0_X20 = w(0xAA1403E0)     # mov  x0, x20
CMP_W0_0 = w(0x7100001F)       # cmp  w0, #0
CSINC_W0_NE = w(0x1A9F1400)    # csinc w0, w0, wzr, ne     (w0 = ne ? w0 : 1)
NOP = w(0xD503201F)

# ------------------------------------------------------------ danh sach patch
# Dia chi (disasm /opt/work/lib4/dis.txt cua libgame.so TEST4):
ENCRYPT_PROCESS2 = 0x140970          # 408 byte, den 0x140b08
STRLEN_PLT = 0x2a83d0                # strlen@plt
STR_123 = 0xa5e67                    # .rodata: "123\0" (duoi cua chuoi "N123\0" @0xa5e66, RELATIVE reloc tham chieu 0xa5e66)

P1_VA = 0x1409a4
P1_OLD = bytes.fromhex('a30000b5' '28468652' 'f4130091' '6806a072' 'e80700b9' 'e00314aa' '859e0594')
P1_NEW = (cbnz_x(3, P1_VA + 0x00, P1_VA + 0x08)            # 1409a4 cbnz x3, 0x1409ac   (co khoa -> bo qua khoa mac dinh)
          + adr(20, P1_VA + 0x04, STR_123)                 # 1409a8 adr  x20, "123"      (khoa NULL -> "123", y het ban goc)
          + MOV_X0_X20                                     # 1409ac mov  x0, x20
          + bl(P1_VA + 0x0c, STRLEN_PLT)                   # 1409b0 bl   strlen@plt
          + CMP_W0_0                                       # 1409b4 cmp  w0, #0
          + CSINC_W0_NE                                    # 1409b8 csinc w0, w0, wzr, ne   => L = (L != 0) ? L : 1
          + NOP)                                           # 1409bc nop
P1_ASM = [
    (0x1409a4, 'cbnz x3, 0x1409ac'),
    (0x1409a8, 'adr x20, 0xa5e67'),
    (0x1409ac, 'mov x0, x20'),
    (0x1409b0, 'bl 0x2a83d0'),
    (0x1409b4, 'cmp w0, #0x0'),
    (0x1409b8, 'csinc w0, w0, wzr, ne'),
    (0x1409bc, 'nop'),
]
P1_ASM_OLD = [
    (0x1409a4, 'cbnz x3, 0x1409b8'),
    (0x1409a8, 'mov w8, #0x3231'),
    (0x1409ac, 'add x20, sp, #0x4'),
    (0x1409b0, 'movk w8, #0x33, lsl #16'),
    (0x1409b4, 'str w8, [sp, #0x4]'),
    (0x1409b8, 'mov x0, x20'),
    (0x1409bc, 'bl 0x2a83d0'),
]

# ------------------------------------------------------------ nhom `mod` (T8b): xem docs/mod_menu.md
MOD_CAVE_VA = 0x18bb00
MOD_CAVE_LEN = 236                       # 59 lenh, den 0x18bbec = CHAR_AddDamageEffect (0x18bb00) + CHAR_AddDamageByDefaultCheck (0x18bb34), 2 ham chet
MOD_CAVE_OLD_SHA256 = '48718ee58e6904aacd34067ee87fead67afd709899bcc11fa3f276ceaaf73c36'
MOD_CAVE_NEW_SHA256 = 'cb64516299bc0de2441d8025c053b08341ac526fd24a088d673c0ac55d659765'
# 236 B do llvm-mc + ld.lld dung tu scripts/asm/mod_cave.s (bash scripts/asm/build_mod_cave.sh; `--check-asm` dung lai va so)
MOD_CAVE_NEW = bytes.fromhex(
    'ffc301d15f0400714b03005429444c392a2d0090290100354b8547b96c090051'
    '9fc10071680200544c08cb1a9f01007182159f1a0f000014c00100b409444c39'
    '890100354b8147b96c0900519fc1007108010054ed478852ed01a0725f000d6b'
    '8a0000544c7c0b1b9f010d6b82b18d1a1dfeff17c80d8052292d00902a8947b9'
    '4b0900517fc1007108010054ecff9f52ec1fa0727f020c6b8a0000546b7e0a1b'
    '7f010c6b73b18c1a2fef0014f303002a7f060071ab010054292d00902a8947b9'
    '4b0900517fc1007108010054ecff9f52ec1fa0727f020c6b8a0000546b7e0a1b'
    '7f010c6b73b18c1a98ef0014')
# disasm mong doi cua cave (llvm-objdump da bo chu thich/nhan): "<vaddr> <lenh>"
MOD_CAVE_ASM = [
    '18bb00 sub sp, sp, #0x70',
    '18bb04 cmp w2, #0x1',
    '18bb08 b.lt 0x18bb70',
    '18bb0c ldrb w9, [x1, #0x311]',
    '18bb10 adrp x10, 0x72f000',
    '18bb14 cbnz w9, 0x18bb38',
    '18bb18 ldr w11, [x10, #0x784]',
    '18bb1c sub w12, w11, #0x2',
    '18bb20 cmp w12, #0x30',
    '18bb24 b.hi 0x18bb70',
    '18bb28 udiv w12, w2, w11',
    '18bb2c cmp w12, #0x0',
    '18bb30 csinc w2, w12, wzr, ne',
    '18bb34 b 0x18bb70',
    '18bb38 cbz x0, 0x18bb70',
    '18bb3c ldrb w9, [x0, #0x311]',
    '18bb40 cbnz w9, 0x18bb70',
    '18bb44 ldr w11, [x10, #0x780]',
    '18bb48 sub w12, w11, #0x2',
    '18bb4c cmp w12, #0x30',
    '18bb50 b.hi 0x18bb70',
    '18bb54 mov w13, #0x423f',
    '18bb58 movk w13, #0xf, lsl #16',
    '18bb5c cmp w2, w13',
    '18bb60 b.ge 0x18bb70',
    '18bb64 mul w12, w2, w11',
    '18bb68 cmp w12, w13',
    '18bb6c csel w2, w12, w13, lt',
    '18bb70 b 0x18b3e4',
    '18bb74 mov w8, #0x6e',
    '18bb78 adrp x9, 0x72f000',
    '18bb7c ldr w10, [x9, #0x788]',
    '18bb80 sub w11, w10, #0x2',
    '18bb84 cmp w11, #0x30',
    '18bb88 b.hi 0x18bba8',
    '18bb8c mov w12, #0xffff',
    '18bb90 movk w12, #0xff, lsl #16',
    '18bb94 cmp w19, w12',
    '18bb98 b.ge 0x18bba8',
    '18bb9c mul w11, w19, w10',
    '18bba0 cmp w11, w12',
    '18bba4 csel w19, w11, w12, lt',
    '18bba8 b 0x1c7864',
    '18bbac mov w19, w0',
    '18bbb0 cmp w19, #0x1',
    '18bbb4 b.lt 0x18bbe8',
    '18bbb8 adrp x9, 0x72f000',
    '18bbbc ldr w10, [x9, #0x788]',
    '18bbc0 sub w11, w10, #0x2',
    '18bbc4 cmp w11, #0x30',
    '18bbc8 b.hi 0x18bbe8',
    '18bbcc mov w12, #0xffff',
    '18bbd0 movk w12, #0xff, lsl #16',
    '18bbd4 cmp w19, w12',
    '18bbd8 b.ge 0x18bbe8',
    '18bbdc mul w11, w19, w10',
    '18bbe0 cmp w11, w12',
    '18bbe4 csel w19, w11, w12, lt',
    '18bbe8 b 0x1c7a48',
]
MOD_CAVE_ASM = [(int(l.split(' ', 1)[0], 16), l.split(' ', 1)[1]) for l in MOD_CAVE_ASM]


# ------------------------------------------------------------ nhom `view` (T9b): tam nhin = nhan cfg.W/H cua khung dem FB voi P% (docs/tam_nhin_hud.md)
VIEW_VA = 0x14709c                       # OriginalDisplay_ConfigInfo: `strb w20,[x19]` -> `b cave` (cave tu lam lai lenh nay)
VIEW_CAVE_VA = 0x133454                  # than ham chet SGL_grpResume (0x133454, 1156 B: 0 caller, 0 reloc, khong PLT; scripts/re/view_check.py muc [4])
VIEW_CAVE_OLD = bytes.fromhex('ffc301d1fd7b02a9f91b00f9f85f04a9f65705a9f44f06a9fd83009156d03bd5490c00f0e0031f2ac81640f9291d40f9e1031f2a')
# 52 B do llvm-mc + ld.lld dung tu scripts/asm/view_cave.s (bash scripts/asm/build_view_cave.sh; `--check-asm` dung lai va so)
VIEW_CAVE_NEW = bytes.fromhex('74020039ea2f00904a8d47b94b9101517fc90071e80000548b0c8052087d0a1b0809cb1a08791f12297d0a1b2909cb1a074f0014')
VIEW_CAVE_ASM = [
    (0x133454, 'strb w20, [x19]'),
    (0x133458, 'adrp x10, 0x72f000'),
    (0x13345c, 'ldr w10, [x10, #0x78c]'),
    (0x133460, 'sub w11, w10, #0x64'),
    (0x133464, 'cmp w11, #0x32'),
    (0x133468, 'b.hi 0x133484'),
    (0x13346c, 'mov w11, #0x64'),
    (0x133470, 'mul w8, w8, w10'),
    (0x133474, 'udiv w8, w8, w11'),
    (0x133478, 'and w8, w8, #0xfffffffe'),
    (0x13347c, 'mul w9, w9, w10'),
    (0x133480, 'udiv w9, w9, w11'),
    (0x133484, 'b 0x1470a0'),
]

# ------------------------------------------------------------ nhom `hud` (T9b): 29 diem le HUD, moi diem 1 lenh. (vaddr, byte goc, byte moi, lenh moi (objdump = llvm-mc), lenh ke tiep (None neu cung bi va), ly do)
HUD_ROWS = [
    (0x167f7c, '137c4093', '136000d1', 'sub x19, x0, #0x18', '54030594',
     'UIPlay_Create: x19 = W (sxtw) -> W-24; dich ID 101..103, 8..13, 7, 0x14, hinh nen qua [sp,#8]'),
    (0x167f88, '187c4093', '184000d1', 'sub x24, x0, #0x10', '219443f9',
     'UIPlay_Create: x24 = H (sxtw) -> H-16; dich ID 8..13, 7, 0x14'),
    (0x167fb8, '21008052', '21038052', 'mov w1, #0x19', '62138052',
     'cot trai ID 1 (x 1 -> 25)'),
    (0x16800c, '21008052', '21038052', 'mov w1, #0x19', '021d8052',
     'cot trai ID 2'),
    (0x16805c, '21008052', '21038052', 'mov w1, #0x19', 'a2268052',
     'cot trai ID 3'),
    (0x1680cc, '42018052', '42038052', 'mov w2, #0x1a', 'e10315aa',
     'cot phai ID 101 (y 10 -> 26)'),
    (0x16811c, 'c20d8052', 'c20f8052', 'mov w2, #0x7e', 'c3098052',
     'cot phai ID 102 (y 110 -> 126)'),
    (0x168168, '421a8052', '421c8052', 'mov w2, #0xe2', 'c3098052',
     'cot phai ID 103 (y 210 -> 226)'),
    (0x1681b8, '682a07f1', '68ca06f1', 'subs x8, x19, #0x1b2', None,
     'thanh ID 4: (W-24-434) = W-458, giu tam man hinh'),
    (0x1681bc, '692607d1', '69c606d1', 'sub x9, x19, #0x1b1', 'e00315aa',
     'thanh ID 4 (nhanh am cua phep chia 2)'),
    (0x1681c8, 'e2031faa', '020280d2', 'mov x2, #0x10', '43398052',
     'thanh ID 4: y 0 -> 16'),
    (0x168424, '08f00351', '08300451', 'sub w8, w0, #0x10c', 'e00315aa',
     'can 0x15: y = H-252-16'),
    (0x16842c, '41018052', '41048052', 'mov w1, #0x22', '027d4093',
     'can 0x15: x 10 -> 34'),
    (0x168490, '08f00551', '08300651', 'sub w8, w0, #0x18c', 'e00315aa',
     'vung cham can 0x16: y = H-380-16'),
    (0x168498, '41018052', '41048052', 'mov w1, #0x22', '027d4093',
     'vung cham can 0x16: x 10 -> 34'),
    (0x1684e8, '08ac0251', '08ec0251', 'sub w8, w0, #0xbb', 'e00315aa',
     'mui ten 0x1e: y'),
    (0x1684f0, '21168052', '21198052', 'mov w1, #0xc9', '027d4093',
     'mui ten 0x1e: x 177 -> 201'),
    (0x168540, '08540151', '08940151', 'sub w8, w0, #0x65', 'e00315aa',
     'mui ten 0x1f: y'),
    (0x168548, '610b8052', '610e8052', 'mov w1, #0x73', '027d4093',
     'mui ten 0x1f: x 91 -> 115'),
    (0x168598, '08ac0251', '08ec0251', 'sub w8, w0, #0xbb', 'e00315aa',
     'mui ten 0x20: y'),
    (0x1685a0, '21038052', '21068052', 'mov w1, #0x31', '027d4093',
     'mui ten 0x20: x 25 -> 49'),
    (0x1685f0, '08b40351', '08f40351', 'sub w8, w0, #0xfd', 'e00314aa',
     'mui ten 0x21: y'),
    (0x1685f8, '610b8052', '610e8052', 'mov w1, #0x73', '027d4093',
     'mui ten 0x21: x 91 -> 115'),
    (0x1698c4, '14500451', '14b00451', 'sub w20, w0, #0x12c', '02fd0494',
     'UIPlayPorting_Draw: hinh nen ban nguyet x = W-276-24'),
    (0x1698cc, '152c0451', '156c0451', 'sub w21, w0, #0x11b', 'a0028052',
     'hinh nen ban nguyet y = H-267-16'),
    (0x16b234, 'e1008052', 'e1038052', 'mov w1, #0x1f', None,
     'UIPlay_MakeMinimap: rect ID 5 x 7 -> 31'),
    (0x16b238, 'e2008052', 'e2028052', 'mov w2, #0x17', '097d191b',
     'rect ID 5 y 7 -> 23'),
    (0x16b264, 'e1008052', 'e1038052', 'mov w1, #0x1f', None,
     'rect ID 6 x 7 -> 31'),
    (0x16b268, 'e2008052', 'e2028052', 'mov w2, #0x17', '097d191b',
     'rect ID 6 y 7 -> 23')
]

# ------------------------------------------------------------ nhom `pool` (T9b): MEM_Create 0x1456e4, tran pool 8 MB -> 10 MB
POOL_ROWS = [
    (0x145730, '0910a052', '0914a052', 'mov w9, #0xa00000', None,
     'T9b / pool. MEM_Create: tran pool `mov w9,#0x800000` -> `mov w9,#0xa00000` (movz imm16 0xa0 lsl 16). Mat na `and x0,x8,#0x1fffc00` @0x145748 '
     '(toi da 32 MB) giu nguyen 10 MB. Ly do: GRP_Initialize xin MEM_Malloc(2*W*W) tu pool, them toi ~0,66 MB o P = 150.'),
    (0x145734, '1f0160f1', '1f0168f1', 'cmp x8, #0xa00, lsl #12', '0831899a',      # ke tiep: csel x8, x8, x9, lo
     'T9b / pool. MEM_Create: `cmp x8,#0x800,lsl#12` -> `cmp x8,#0xa00,lsl#12` (imm12 0xa00 << 12 = 10 MB); cung tran voi 0x145730 (csel lo). '
     'CS_knlAlloc = calloc(1,n); free = ActivityManager.MemoryInfo.availMem.'),
]

# Moi patch: (vaddr, old, new, ly do, refs, asm_moi, group, old_sha256|None). old = None cho cave (kiem sha256).
PATCHES = [
    (0x203e68, bytes.fromhex('a0040036'), b_imm(0x203e68, 0x203f40),
     'STATE_EnterMainMenu (khi bam START): `bl isAuthUser; tbz w0,#0,popup_dang_nhap_Hive` -> luon di nhanh 0x203f40 = '
     'UI_SetPopupProcessInfo(1,0), y het nut NO (MainMenu_HubLoginCancel): bo popup "Dang nhap Com2uS Hive?" khi bam START.',
     [], [(0x203e68, 'b 0x203f40')], 'base', None),

    (P1_VA, P1_OLD, P1_NEW,
     'T3b / P1. ENCRYPT_Process2 (0x140970): khoa rong (strlen=0) lam `sdiv` chia 0 -> msub tra nguyen chi so -> doc key[r+i] '
     'vuot buffer HubSave_key (64 B) sang con tro heap thay doi moi lan chay (docs/save_format.md) -> save khong giai ma duoc o '
     'tien trinh khac. Sua: L = max(strlen(key), 1); khoa rong => key[0]=NUL => keystream = T (tat dinh). Khoa khac rong va '
     'khoa NULL ("123") giu nguyen. Khong co vung dem >= 16 B trong segment R-X (khe giua section chi 8/12 B) nen viet lai '
     'chinh khoi 0x1409a4..0x1409bf (7 lenh): khoa mac dinh "123" dung adr toi chuoi "123\\0" co san trong .rodata thay vi dung '
     '4 lenh tren stack, nhuong 2 lenh cho `cmp w0,#0; csinc w0,w0,wzr,ne`. w8 khong con bi ghi o day (khong ai dung truoc khi gan lai); '
     'o nho [sp,#4] khong con duoc ghi (khong ai doc ngoai x20).',
     [(STR_123, b'123\x00'),                     # dich cua adr: dung 4 byte "123\0"
      (0x1409c0, bytes.fromhex('36030034'))],    # lenh ke tiep `cbz w22, 0x140a24` khong doi
     P1_ASM, 'p1', None),

    # ---- T8b: mod menu (docs/mod_menu.md). 3 diem vao + cave 236 B.
    (0x18b3e0, bytes.fromhex('ffc301d1'), b_imm(0x18b3e0, 0x18bb00),
     'T8b / A. CHAR_AddDamage(x0 ke tan cong|NULL, x1 muc tieu, w2 sat thuong int32): lenh dau `sub sp,sp,#0x70` -> `b cave_A`. '
     'Cave A tu lam lai `sub sp,sp,#0x70`; muc tieu phe 0 ([x1+0x311]==0) => dmg / Mdef (toi thieu 1); ke tan cong != NULL va phe 0 => '
     'dmg * Mdmg, tran 999.999 (khong lam yeu dmg goc >= tran). He so chi nhan 2..50, ngoai khoang = 1. Dung x9..x13.',
     [(0x18b3e4, bytes.fromhex('fd7b02a9'))],                  # lenh ke tiep: stp x29, x30, [sp, #0x20]
     [(0x18b3e0, 'b 0x18bb00')], 'mod', None),

    (0x1c7860, bytes.fromhex('c80d8052'), b_imm(0x1c7860, 0x18bb74),
     'T8b / B. MERCENARYSYSTEM_AddExperience (giet quai): `mov w8,#0x6e` @0x1c7860 -> `b cave_B`; w19 = EXP co so * Mexp, tran 0xFFFFFF '
     '(`w19*110` @0x1c7868 con < 2^31); w25 = w19*110/100 tinh tiep tu w19 moi. Dung x9..x12 (chet tai day).',
     [(0x1c7864, bytes.fromhex('e9a39052'))],                  # lenh ke tiep: mov w9, #0x851f
     [(0x1c7860, 'b 0x18bb74')], 'mod', None),

    (0x1c7a44, bytes.fromhex('f303002a'), b_imm(0x1c7a44, 0x18bbac),
     'T8b / C. MERCENARYSYSTEM_AddExpFixed (thuong nhiem vu): `mov w19,w0` @0x1c7a44 -> `b cave_C`; w19 = w0 * Mexp, tran 0xFFFFFF. '
     'x8 SONG (adrp x8 @0x1c7a40) nen cave chi dung x9..x12.',
     [(0x1c7a48, bytes.fromhex('08ed45f9'))],                  # lenh ke tiep: ldr x8, [x8, #0xbd8]
     [(0x1c7a44, 'b 0x18bbac')], 'mod', None),

    (MOD_CAVE_VA, None, MOD_CAVE_NEW,
     'T8b / cave. Than 2 ham chet CHAR_AddDamageEffect + CHAR_AddDamageByDefaultCheck (0 caller, 0 reloc/adrp/literal/PLT, ten chi trong '
     '.dynstr, libHercules khong import: scripts/re/mod_check.py muc [3]). 59 lenh chua cave A (29), B (14), C (16); doc 3 int32 o '
     '0x72f780 (Mdmg), 0x72f784 (Mdef), 0x72f788 (Mexp) trong .bss (khong ai tham chieu: muc [4]); khong ghi bo nho.',
     [], MOD_CAVE_ASM, 'mod', (MOD_CAVE_LEN, MOD_CAVE_OLD_SHA256)),

    # ---- T9b: tam nhin (nhom `view`), docs/tam_nhin_hud.md muc 7
    (VIEW_VA, bytes.fromhex('74020039'), b_imm(VIEW_VA, VIEW_CAVE_VA),
     'T9b / view. OriginalDisplay_ConfigInfo (0x147030, 1 caller = startClet): `strb w20,[x19]` @0x14709c -> `b cave_view`. Moi duong toi day deu co '
     'w8 = cfg.W, w9 = cfg.H (nhanh W < 480 qua 0x147090 cung vay); sau do chi con strh w8,[x19,#2]; strh w9,[x19,#4]; stp s1,s0; epilogue '
     '(w10, w11, NZCV khong con duoc doc). Cave tu lam lai `strb w20,[x19]` roi nhan w8/w9 voi P% (P = int32 o 0x72f78c).',
     [(0x1470a0, bytes.fromhex('68060079'))],                  # lenh ke tiep: strh w8, [x19, #2]
     [(VIEW_VA, 'b 0x133454')], 'view', None),

    (VIEW_CAVE_VA, VIEW_CAVE_OLD, VIEW_CAVE_NEW,
     'T9b / view cave. 52 B dau than SGL_grpResume (0 caller, 0 reloc kem RELATIVE, 0 adrp/adr/literal, khong PLT, ten chi trong .dynstr; chuoi "grpResume" '
     'khong co trong dex/smali/asset/libHercules). Cave: strb w20,[x19]; P = [0x72f78c]; neu P - 100 > 50 (khong dau; ke ca 0, am, > 150) giu nguyen; '
     'nguoc lai W = floor(W*P/100) & ~1 (W chan: pitch FB = 2W, upload GL can 4 byte), H = floor(H*P/100); b 0x1470a0. Dung w10, w11 (chet).',
     [], VIEW_CAVE_ASM, 'view', None),
]
for _va, _old, _new, _asm, _nxt, _why in HUD_ROWS:
    PATCHES.append((_va, bytes.fromhex(_old), bytes.fromhex(_new),
                    'T9b / hud. ' + _why + ' (lui vao 24 px ngang / 16 px doc; sua rect nen hinh va vung cham cung doi)',
                    [(_va + 4, bytes.fromhex(_nxt))] if _nxt else [], [(_va, _asm)], 'hud', None))
for _va, _old, _new, _asm, _nxt, _why in POOL_ROWS:
    PATCHES.append((_va, bytes.fromhex(_old), bytes.fromhex(_new), _why,
                    [(_va + 4, bytes.fromhex(_nxt))] if _nxt else [], [(_va, _asm)], 'pool', None))

# ------------------------------------------------------------ nhom `hudbtn` (T10b): kieu ve cua lop "tan cong bam" (UI_014 loc3) trong UIPlayPorting_Draw
# Ca 3 noi goi IMGSYS_GetLoc(0x15, 3) (0x169ad0, 0x169b9c, 0x169be4; scripts/re/hud_check.py muc [4]) co dang
#   mov w0,#0x15; mov w1,#3; bl GetLoc; mov x3,x0; mov x0,x24; mov w1,w22; mov w2,w23; mov w4,#2; mov w5,#KIEU; mov w6,wzr; bl GRPX_DrawPart
# GRPX_DrawPart(g, x, y, loc, neo=w4, kieu=w5, %=w6): kieu 2 = glBlendFunc(SRC_ALPHA, ONE) (cong), kieu 1 = ve thuong (SRC_ALPHA, ONE_MINUS_SRC_ALPHA).
# Anh "tan cong bam" cua nguoi dung la anh day mau (khong phai vong sang): ve cong se chay sang => doi kieu 2 -> 1 (mov w5,#2 -> mov w5,#1).
HUDBTN_ROWS = [
    (0x169ae8, 0x169ad0, 'nhap nhay huong dan: [0x2bec78] != 0, [x26] == 4, TutorialDescription() != 0, bo dem % 10 <= 3'),
    (0x169bb4, 0x169b9c, 'nhap nhay huong dan: [0x2bec78] != 0, [0x2bf7b0] != 0, [x26] == 0xd, TutorialDescription() != 0, bo dem % 10 <= 3'),
    (0x169bfc, 0x169be4, 'lop bam that: ControlObject_GetValue(control ID 0x14 = tan cong) == 1'),
]
for _va, _gl, _why in HUDBTN_ROWS:
    PATCHES.append((_va, bytes.fromhex('45008052'), bytes.fromhex('25008052'),
                    'T10b / hudbtn. UIPlayPorting_Draw: ' + _why + '. `mov w5,#2` (kieu cong) -> `mov w5,#1` (alpha thuong) cho GRPX_DrawPart(UI_014 loc3): '
                    'ngu canh khop o 0x%x..0x%x (mov w4,#2 / mov w6,wzr / bl GRPX_DrawPart); chi doi imm 2 -> 1 trong `movz w5`.' % (_va - 4, _va + 8),
                    [(_va - 4, bytes.fromhex('44008052')),                      # mov w4,#2 (neo = tam)
                     (_va + 4, bytes.fromhex('e6031f2a')),                      # mov w6,wzr
                     (_va + 8, bl(_va + 8, 0x2a89a0))],                         # bl GRPX_DrawPart@plt
                    [(_va, 'mov w5, #0x1')], 'hudbtn', None))
GROUPS = ['base', 'p1', 'mod', 'view', 'hud', 'pool', 'hudbtn']
DEFAULT_GROUPS = ['base', 'p1', 'mod', 'view', 'hud', 'pool']       # hudbtn chi di cung --hud (atlas moi): phai goi ten


def selected(groups):
    for g in groups:
        if g not in GROUPS:
            raise SystemExit('nhom patch la: %s (co: %s)' % (g, ','.join(GROUPS)))
    return [pt for pt in PATCHES if pt[6] in groups]


def old_len(pt):
    return len(pt[1]) if pt[1] is not None else pt[7][0]


def matches_old(pt, cur):
    if pt[1] is not None:
        return cur == pt[1]
    return hashlib.sha256(cur).hexdigest() == pt[7][1]


def check_ranges():
    last = 0
    for va, old, new, _why, _refs, _asm, _g, osha in sorted(PATCHES, key=lambda p: p[0]):
        olen = len(old) if old is not None else osha[0]
        assert olen == len(new), 'patch 0x%x: old/new khac do dai' % va
        assert TEXT_BEG <= va and va + len(new) <= TEXT_END, 'patch 0x%x ngoai .text' % va
        assert va >= last, 'patch 0x%x chong len patch truoc' % va
        last = va + len(new)
    for va, old, new, _why, _refs, asm, _g, osha in PATCHES:
        if osha is not None:
            assert hashlib.sha256(new).hexdigest() == MOD_CAVE_NEW_SHA256, 'MOD_CAVE_NEW khong khop sha256 khai bao'
            assert len(asm) == len(new) // 4, 'asm mong doi cua cave phai du %d lenh' % (len(new) // 4)


# ------------------------------------------------------------ disasm (tuy chon)
def objdump(path, beg, end):
    out = subprocess.run(['llvm-objdump', '-d', '--no-show-raw-insn', '--start-address=0x%x' % beg,
                          '--stop-address=0x%x' % end, path], capture_output=True, text=True, check=True).stdout
    rows = []
    for line in out.splitlines():
        m = re.match(r'\s*([0-9a-f]+):\s+(.*)$', line)
        if m:
            txt = re.sub(r'\s*<[^>]*>', '', m.group(2))
            txt = re.sub(r'\s*//.*$', '', txt)
            rows.append((int(m.group(1), 16), re.sub(r'\s+', ' ', txt.replace('\t', ' ')).strip()))
    return rows


def show_disasm(path, va, n, label, expect=None):
    rows = objdump(path, va, va + n)
    print('    %s:' % label)
    for a, t in rows:
        print('      0x%06x  %s' % (a, t))
    if expect is not None:
        got = [(a, t) for a, t in rows]
        if got != expect:
            raise SystemExit('DISASM KHONG KHOP du kien tai 0x%x:\n  co   %s\n  muon %s' % (va, got, expect))
        print('      -> khop %d lenh du kien' % len(expect))


# ------------------------------------------------------------ ap patch
def apply(src, dst, disasm=False, groups=DEFAULT_GROUPS):
    check_ranges()
    pts = selected(groups)
    data = bytearray(open(src, 'rb').read())
    print('vao  sha256', hashlib.sha256(data).hexdigest(), src)
    print('nhom', ','.join(groups))
    plan = []
    for pt in pts:
        va, old, new, why, refs, asm, grp, osha = pt
        olen = old_len(pt)
        cur = bytes(data[va:va + olen])
        if matches_old(pt, cur):
            state = 'ap'
        elif cur == new:
            state = 'da ap'
        else:
            raise SystemExit('PATCH 0x%x [%s]: byte hien tai (%d B, sha256 %s) khac ca ban goc lan ban moi -> DUNG, khong ghi gi'
                             % (va, grp, len(cur), hashlib.sha256(cur).hexdigest()[:16]))
        for rva, rb in refs:
            got = bytes(data[rva:rva + len(rb)])
            if got != rb:
                raise SystemExit('PATCH 0x%x: tham chieu 0x%x = %s, ky vong %s -> DUNG' % (va, rva, got.hex(), rb.hex()))
        plan.append((pt, state))
    for g in groups:                                            # moi nhom phai ap tron ven
        st = {state for pt, state in plan if pt[6] == g}
        if len(st) > 1:
            raise SystemExit('NHOM %s o trang thai lech (vua "ap" vua "da ap") -> DUNG, khong ghi gi' % g)
    n_new = 0
    for pt, state in plan:
        va, old, new, why, refs, asm, grp, osha = pt
        if osha is None:
            print('0x%06x [%s] [%s] %d B  %s -> %s' % (va, grp, state, len(new), old.hex(), new.hex()))
        else:
            print('0x%06x [%s] [%s] %d B  sha256 %s -> %s' % (va, grp, state, len(new), osha[1][:16], hashlib.sha256(new).hexdigest()[:16]))
        print('    ly do: ' + why)
        if disasm and state == 'ap':
            show_disasm(src, va, old_len(pt), 'truoc')
        if state == 'ap':
            data[va:va + len(new)] = new
            n_new += 1
    open(dst, 'wb').write(data)
    print('ra   sha256', hashlib.sha256(data).hexdigest(), dst,
          '(%d patch moi, %d da co san)' % (n_new, len(plan) - n_new))
    if disasm:
        for pt, state in plan:
            show_disasm(dst, pt[0], len(pt[2]), 'sau ', pt[5])


def diff(a, b, groups=DEFAULT_GROUPS):
    check_ranges()
    da, db = open(a, 'rb').read(), open(b, 'rb').read()
    if len(da) != len(db):
        raise SystemExit('khac do dai: %d vs %d' % (len(da), len(db)))
    decl = [(pt[0], pt[0] + len(pt[2])) for pt in selected(groups)]
    bad = []
    runs = []
    i = 0
    while i < len(da):
        if da[i] != db[i]:
            j = i
            while j + 1 < len(da) and da[j + 1] != db[j + 1]:
                j += 1
            runs.append((i, j + 1))
            i = j + 1
        else:
            i += 1
    verbose = '-v' in sys.argv
    for s, e in runs:
        inside = any(lo <= s and e <= hi for lo, hi in decl)
        if verbose or not inside:
            print('khac [0x%06x, 0x%06x) %3d B  %s' % (s, e, e - s, 'trong vung patch da khai bao' if inside else 'NGOAI VUNG KHAI BAO'))
        if not inside:
            bad.append((s, e))
    print('theo vung khai bao (nhom %s):' % ','.join(groups))
    for pt in selected(groups):
        lo, hi = pt[0], pt[0] + len(pt[2])
        n = sum(1 for i in range(lo, hi) if da[i] != db[i])
        kind = 'da giong nhau (patch co san / chua ap)' if n == 0 else '%d/%d byte khac' % (n, hi - lo)
        print('  0x%06x..0x%06x [%s] %3d B: %s' % (lo, hi, pt[6], hi - lo, kind))
    print('tong %d byte khac, %d doan; %s' % (sum(e - s for s, e in runs), len(runs),
                                              'TAT CA nam trong vung khai bao' if not bad else 'CO %d doan ngoai vung!' % len(bad)))
    return 1 if bad else 0


def check_asm():
    """Dung lai cave `mod` (build_mod_cave.sh) va cave `view` (build_view_cave.sh) bang llvm-mc + ld.lld, va cac lenh don `hud`/`pool`
    bang llvm-mc (gom ca 3 lenh `hudbtn`); so voi byte nhung san va disasm mong doi."""
    import os
    import tempfile
    here = os.path.dirname(os.path.abspath(__file__))
    out = tempfile.mkdtemp(prefix='modcave_')
    subprocess.run(['bash', os.path.join(here, 'asm', 'build_mod_cave.sh'), out], check=True, capture_output=True)
    built = open(os.path.join(out, 'mod_cave.bin'), 'rb').read()
    rows = objdump(os.path.join(out, 'mod_cave.elf'), MOD_CAVE_VA, MOD_CAVE_VA + MOD_CAVE_LEN)
    ok = built == MOD_CAVE_NEW and rows == MOD_CAVE_ASM
    print('cave `mod` dung lai tu mod_cave.s: %d B, sha256 %s' % (len(built), hashlib.sha256(built).hexdigest()))
    print('  khop byte nhung san:', built == MOD_CAVE_NEW, '| khop disasm mong doi:', rows == MOD_CAVE_ASM)
    outv = tempfile.mkdtemp(prefix='viewcave_')
    subprocess.run(['bash', os.path.join(here, 'asm', 'build_view_cave.sh'), outv], check=True, capture_output=True)
    bv = open(os.path.join(outv, 'view_cave.bin'), 'rb').read()
    rv = objdump(os.path.join(outv, 'view_cave.elf'), VIEW_CAVE_VA, VIEW_CAVE_VA + len(VIEW_CAVE_NEW))
    okv = bv == VIEW_CAVE_NEW and rv == VIEW_CAVE_ASM
    print('cave `view` dung lai tu view_cave.s: %d B, sha256 %s' % (len(bv), hashlib.sha256(bv).hexdigest()))
    print('  khop byte nhung san:', bv == VIEW_CAVE_NEW, '| khop disasm mong doi:', rv == VIEW_CAVE_ASM)
    if rv != VIEW_CAVE_ASM:
        print('  co  ', rv)
        print('  muon', VIEW_CAVE_ASM)
    ok = ok and okv
    # lenh don (khong phu thuoc vi tri): dung bang llvm-mc theo dung thu tu bang
    singles = [pt for pt in PATCHES if pt[6] in ('hud', 'pool', 'hudbtn')]
    src = '.text\n' + ''.join('%s\n' % pt[5][0][1] for pt in singles)
    open(os.path.join(outv, 'singles.s'), 'w').write(src)
    subprocess.run(['llvm-mc', '-triple=aarch64-linux-gnu', '-mattr=+v8a', '-filetype=obj', '-o', os.path.join(outv, 'singles.o'),
                    os.path.join(outv, 'singles.s')], check=True, capture_output=True)
    subprocess.run(['llvm-objcopy', '-O', 'binary', '-j', '.text', os.path.join(outv, 'singles.o'), os.path.join(outv, 'singles.bin')], check=True)
    sb = open(os.path.join(outv, 'singles.bin'), 'rb').read()
    want = b''.join(pt[2] for pt in singles)
    bad = [hex(singles[i][0]) for i in range(len(singles)) if sb[4 * i:4 * i + 4] != singles[i][2]]
    print('lenh don `hud`+`pool`+`hudbtn` (%d lenh) dung lai bang llvm-mc: khop byte nhung san: %s%s' % (len(singles), sb == want, '' if sb == want else ' ; lech tai ' + ','.join(bad)))
    ok = ok and sb == want
    return 0 if ok else 1


def main(argv):
    groups = DEFAULT_GROUPS
    if '--group' in argv:
        k = argv.index('--group')
        groups = argv[k + 1].split(',')
        argv = argv[:k] + argv[k + 2:]
    if argv[:1] == ['--list']:
        for pt in selected(groups):
            print('0x%06x %3d B [%s] %s' % (pt[0], len(pt[2]), pt[6], pt[3]))
        return 0
    if argv[:1] == ['--check-asm']:
        return check_asm()
    if argv[:1] == ['--diff'] and len([a for a in argv if a != '-v']) == 3:
        argv = [a for a in argv if a != '-v']                        # -v do diff() doc tu sys.argv
        return diff(argv[1], argv[2], groups)
    disasm = '--disasm' in argv
    args = [a for a in argv if a != '--disasm']
    if len(args) != 2:
        print(__doc__)
        return 2
    apply(args[0], args[1], disasm, groups)
    return 0


if __name__ == '__main__':
    sys.exit(main(sys.argv[1:]))
