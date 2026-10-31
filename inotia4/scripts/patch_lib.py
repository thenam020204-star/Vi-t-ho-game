#!/usr/bin/env python3
"""Va nhi phan toi thieu libgame.so (ARM64) cho ban offline.

    python3 patch_lib.py <libgame.so vao> <libgame.so ra> [--disasm]
    python3 patch_lib.py --list
    python3 patch_lib.py --diff <lib A> <lib B>       # moi byte khac nhau phai nam trong vung patch da khai bao

Moi patch (xem PATCHES): (vaddr, byte cu `old`, byte moi `new`, ly do, refs, asm_moi).
  * Doan byte tai vaddr == old  -> ap.
  * == new                      -> bao "da ap" (lib cua TEST4 da mang patch 0x203e68 tu luc dung ban dau) va bo qua.
  * khac ca hai                 -> DUNG, khong ghi gi (khong va mo).
  * refs = [(vaddr, bytes)]: du lieu/lenh CHI DOC ma patch dua vao (dich cua `adr`, lenh ke tiep). Phai khop dung.
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

PATCHES = [
    # (vaddr, old, new, ly do, refs, asm_moi)
    (0x203e68, bytes.fromhex('a0040036'), b_imm(0x203e68, 0x203f40),
     'STATE_EnterMainMenu (khi bam START): `bl isAuthUser; tbz w0,#0,popup_dang_nhap_Hive` -> luon di nhanh 0x203f40 = '
     'UI_SetPopupProcessInfo(1,0), y het nut NO (MainMenu_HubLoginCancel): bo popup "Dang nhap Com2uS Hive?" khi bam START.',
     [], [(0x203e68, 'b 0x203f40')]),

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
     P1_ASM),
]


def check_ranges():
    last = 0
    for va, old, new, _why, _refs, _asm in sorted(PATCHES, key=lambda p: p[0]):
        assert len(old) == len(new), 'patch 0x%x: old/new khac do dai' % va
        assert TEXT_BEG <= va and va + len(new) <= TEXT_END, 'patch 0x%x ngoai .text' % va
        assert va >= last, 'patch 0x%x chong len patch truoc' % va
        last = va + len(new)


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
def apply(src, dst, disasm=False):
    check_ranges()
    data = bytearray(open(src, 'rb').read())
    print('vao  sha256', hashlib.sha256(data).hexdigest(), src)
    plan = []
    for va, old, new, why, refs, asm in PATCHES:
        cur = bytes(data[va:va + len(old)])
        if cur == old:
            state = 'ap'
        elif cur == new:
            state = 'da ap'
        else:
            raise SystemExit('PATCH 0x%x: byte hien tai %s khac ca byte goc %s lan byte moi %s -> DUNG, khong ghi gi'
                             % (va, cur.hex(), old.hex(), new.hex()))
        for rva, rb in refs:
            got = bytes(data[rva:rva + len(rb)])
            if got != rb:
                raise SystemExit('PATCH 0x%x: tham chieu 0x%x = %s, ky vong %s -> DUNG' % (va, rva, got.hex(), rb.hex()))
        plan.append((va, old, new, why, asm, state))
    n_new = 0
    for va, old, new, why, asm, state in plan:
        print('0x%06x [%s] %d B  %s -> %s' % (va, state, len(new), old.hex(), new.hex()))
        print('    ly do: ' + why)
        if disasm:
            if state == 'ap':
                show_disasm(src, va, len(old), 'truoc')
        if state == 'ap':
            data[va:va + len(new)] = new
            n_new += 1
    open(dst, 'wb').write(data)
    print('ra   sha256', hashlib.sha256(data).hexdigest(), dst,
          '(%d patch moi, %d da co san)' % (n_new, len(plan) - n_new))
    if disasm:
        for va, old, new, why, asm, state in plan:
            show_disasm(dst, va, len(new), 'sau ', asm)


def diff(a, b):
    check_ranges()
    da, db = open(a, 'rb').read(), open(b, 'rb').read()
    if len(da) != len(db):
        raise SystemExit('khac do dai: %d vs %d' % (len(da), len(db)))
    decl = [(va, va + len(new)) for va, _o, new, _w, _r, _a in PATCHES]
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
    for s, e in runs:
        inside = any(lo <= s and e <= hi for lo, hi in decl)
        print('khac [0x%06x, 0x%06x) %3d B  %s' % (s, e, e - s, 'trong vung patch da khai bao' if inside else 'NGOAI VUNG KHAI BAO'))
        if not inside:
            bad.append((s, e))
    print('tong %d byte khac, %d doan; %s' % (sum(e - s for s, e in runs), len(runs),
                                              'TAT CA nam trong vung khai bao' if not bad else 'CO %d doan ngoai vung!' % len(bad)))
    return 1 if bad else 0


def main(argv):
    if argv[:1] == ['--list']:
        for va, old, new, why, refs, _a in PATCHES:
            print('0x%06x %d B  %s' % (va, len(new), why))
        return 0
    if argv[:1] == ['--diff'] and len(argv) == 3:
        return diff(argv[1], argv[2])
    disasm = '--disasm' in argv
    args = [a for a in argv if a != '--disasm']
    if len(args) != 2:
        print(__doc__)
        return 2
    apply(args[0], args[1], disasm)
    return 0


if __name__ == '__main__':
    sys.exit(main(sys.argv[1:]))
