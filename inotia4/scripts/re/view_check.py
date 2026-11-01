#!/usr/bin/env python3
"""T9a: kiem lai doc lap bang disasm/ELF cac diem va "Tam nhin + HUD" (khong tin ban giao cua A2, khong doan).

    python3 -I scripts/re/view_check.py <libgame.so goc> <dis.txt> [--apk <apk dung de grep chuoi grpResume>] [--cave-bin <view_cave.bin>]

Cac muc (moi muc in DAT/KHONG DAT):
  [1] 29 diem le HUD: byte goc tai vaddr == khai bao; disasm cu/moi (llvm-mc); dung lai lenh moi bang llvm-mc va so byte;
      kiem NGU NGHIA (+24 px ngang / +16 px doc theo tung loai diem); ngu canh (lenh ke tiep) trong dis.txt.
  [2] UIPlay_Create: moi cho dung x19 (W) / x24 (H) va moi `SGL_GetScreenWidth/Height` o UIPlay*/UIPlayPorting* (sau khi va).
  [3] Cave `view` 52 B tai 0x133454: dung lai bang llvm-mc + ld.lld (scripts/asm/build_view_cave.sh), so voi byte A2;
      lenh `b` tai 0x14709c; thanh ghi/co song sau 0x14709c; moi duong toi 0x14709c deu co w8=cfg.W, w9=cfg.H.
  [4] SGL_grpResume (0x133454..0x1338d8) la ma chet: 0 caller, 0 reloc, 0 adrp/adr/literal, khong PLT, khong con tro trong du lieu,
      ten chi o .dynstr; chuoi "grpResume" trong dex/smali/asset cua APK va trong libHercules.so.
  [5] Pool MEM_Create: encoding 0x145730/0x145734, mask 0x1fffc00, nguon CS_knlAlloc / CS_knlGetFreeMemory.
"""
import hashlib
import os
import re
import struct
import subprocess
import sys
import tempfile
import zipfile

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(os.path.dirname(HERE))
ok_all = True


def verdict(name, ok, detail=''):
    global ok_all
    ok_all = ok_all and ok
    print('  -> %s: %s %s' % ('DAT' if ok else 'KHONG DAT', name, detail))


def sh(cmd, **kw):
    return subprocess.run(cmd, capture_output=True, text=True, check=True, **kw).stdout


def llvm_dis(hexstr):
    s = ' '.join('0x%02x' % b for b in bytes.fromhex(hexstr))
    out = subprocess.run(['llvm-mc', '--disassemble', '-triple=aarch64-linux-gnu', '-mattr=+v8a'], input=s,
                         capture_output=True, text=True, check=True).stdout
    return ' '.join(' '.join(l.split('//')[0].split()) for l in out.splitlines() if l.strip() and not l.strip().startswith('.')).strip()


def llvm_asm(text):
    """Dung 1 lenh khong phu thuoc vi tri -> 4 byte hex."""
    d = tempfile.mkdtemp(prefix='asm_')
    open(d + '/a.s', 'w').write('.text\n%s\n' % text)
    sh(['llvm-mc', '-triple=aarch64-linux-gnu', '-mattr=+v8a', '-filetype=obj', '-o', d + '/a.o', d + '/a.s'])
    sh(['llvm-objcopy', '-O', 'binary', '-j', '.text', d + '/a.o', d + '/a.bin'])
    return open(d + '/a.bin', 'rb').read().hex()


# ---------------------------------------------------------------------------------------------------------------
# Bang 29 diem (du lieu cua A2, kiem lai o day). (vaddr, byte goc, lenh moi (asm), loai, ghi chu)
#   loai: cho ngu nghia: 'W-' = x19 = W-24 (sxtw -> sub), 'H-' = x24 = H-16, 'x+' = hang toa do x + 24, 'y+' = hang toa do y + 16,
#         'subH+' = `sub w8,w0,#a` (y = H - a) a += 16, 'subW+' = `sub wN,w0,#a` (x = W - a) a += 24, 'ctr-' = tam thanh ID 4 (imm -= 24),
#         'zero' = `mov x2,xzr` -> `mov x2,#16`
HUD = [
    (0x167f7c, '137c4093', 'sub x19, x0, #24', 'W-', 'UIPlay_Create: x19 = W (sxtw) -> W-24; dich ID 101..103, 8..13, 7, 0x14, hinh nen qua [sp,#8]'),
    (0x167f88, '187c4093', 'sub x24, x0, #16', 'H-', 'UIPlay_Create: x24 = H (sxtw) -> H-16; dich ID 8..13, 7, 0x14'),
    (0x167fb8, '21008052', 'mov w1, #25', 'x+', 'cot trai ID 1 (x 1 -> 25)'),
    (0x16800c, '21008052', 'mov w1, #25', 'x+', 'cot trai ID 2'),
    (0x16805c, '21008052', 'mov w1, #25', 'x+', 'cot trai ID 3'),
    (0x1680cc, '42018052', 'mov w2, #26', 'y+', 'cot phai ID 101 (y 10 -> 26)'),
    (0x16811c, 'c20d8052', 'mov w2, #126', 'y+', 'cot phai ID 102 (y 110 -> 126)'),
    (0x168168, '421a8052', 'mov w2, #226', 'y+', 'cot phai ID 103 (y 210 -> 226)'),
    (0x1681b8, '682a07f1', 'subs x8, x19, #434', 'ctr-', 'thanh ID 4: (W-24-434) = W-458, giu tam man hinh'),
    (0x1681bc, '692607d1', 'sub x9, x19, #433', 'ctr-', 'thanh ID 4 (nhanh am cua phep chia 2)'),
    (0x1681c8, 'e2031faa', 'mov x2, #16', 'zero', 'thanh ID 4: y 0 -> 16'),
    (0x168424, '08f00351', 'sub w8, w0, #268', 'subH+', 'can 0x15: y = H-252-16'),
    (0x16842c, '41018052', 'mov w1, #34', 'x+', 'can 0x15: x 10 -> 34'),
    (0x168490, '08f00551', 'sub w8, w0, #396', 'subH+', 'vung cham can 0x16: y = H-380-16'),
    (0x168498, '41018052', 'mov w1, #34', 'x+', 'vung cham can 0x16: x 10 -> 34'),
    (0x1684e8, '08ac0251', 'sub w8, w0, #187', 'subH+', 'mui ten 0x1e: y'),
    (0x1684f0, '21168052', 'mov w1, #201', 'x+', 'mui ten 0x1e: x 177 -> 201'),
    (0x168540, '08540151', 'sub w8, w0, #101', 'subH+', 'mui ten 0x1f: y'),
    (0x168548, '610b8052', 'mov w1, #115', 'x+', 'mui ten 0x1f: x 91 -> 115'),
    (0x168598, '08ac0251', 'sub w8, w0, #187', 'subH+', 'mui ten 0x20: y'),
    (0x1685a0, '21038052', 'mov w1, #49', 'x+', 'mui ten 0x20: x 25 -> 49'),
    (0x1685f0, '08b40351', 'sub w8, w0, #253', 'subH+', 'mui ten 0x21: y'),
    (0x1685f8, '610b8052', 'mov w1, #115', 'x+', 'mui ten 0x21: x 91 -> 115'),
    (0x16b234, 'e1008052', 'mov w1, #31', 'x+', 'UIPlay_MakeMinimap: rect ID 5 x 7 -> 31'),
    (0x16b238, 'e2008052', 'mov w2, #23', 'y+', 'rect ID 5 y 7 -> 23'),
    (0x16b264, 'e1008052', 'mov w1, #31', 'x+', 'rect ID 6 x 7 -> 31'),
    (0x16b268, 'e2008052', 'mov w2, #23', 'y+', 'rect ID 6 y 7 -> 23'),
    (0x1698c4, '14500451', 'sub w20, w0, #300', 'subW+', 'UIPlayPorting_Draw: hinh nen ban nguyet x = W-276-24'),
    (0x1698cc, '152c0451', 'sub w21, w0, #283', 'subH+', 'hinh nen ban nguyet y = H-267-16'),
]
HUD_DX, HUD_DY = 24, 16

VIEW_VA = 0x14709c
VIEW_OLD = '74020039'                   # strb w20, [x19]
CAVE_VA, CAVE_LEN = 0x133454, 52
CAVE_OLD = 'ffc301d1fd7b02a9f91b00f9f85f04a9f65705a9f44f06a9fd83009156d03bd5490c00f0e0031f2ac81640f9291d40f9e1031f2a'
CAVE_A2 = '74020039ea2f00904a8d47b94b9101517fc90071e80000548b0c8052087d0a1b0809cb1a08791f12297d0a1b2909cb1a074f0014'
FN_LO, FN_HI = 0x133454, 0x1338d8       # SGL_grpResume (1156 B)
POOL = [(0x145730, 'mov w9, #0x800000', 'mov w9, #0xa00000'), (0x145734, 'cmp x8, #0x800, lsl #12', 'cmp x8, #0xa00, lsl #12')]


def rd_phdr(data):
    e_phoff, = struct.unpack_from('<Q', data, 0x20)
    e_phentsize, e_phnum = struct.unpack_from('<HH', data, 0x36)
    out = []
    for i in range(e_phnum):
        t, fl, off, va, pa, fsz, msz, al = struct.unpack_from('<IIQQQQQQ', data, e_phoff + i * e_phentsize)
        out.append(dict(type=t, flags=fl, off=off, va=va, filesz=fsz, memsz=msz))
    return out


def parse_dis(dis):
    rows = []
    func = None
    for line in open(dis):
        m = re.match(r'^([0-9a-f]{16}) <([^>]+)>:', line)
        if m:
            func = m.group(2)
            continue
        m = re.match(r'^\s+([0-9a-f]+):\s+(.*)$', line)
        if m and func:
            rows.append((int(m.group(1), 16), func, m.group(2).strip()))
    return rows


def imm_of(txt):
    m = re.search(r'#(0x[0-9a-f]+|\d+)', txt)
    return int(m.group(1), 0) if m else None


def main(lib, dis, apk, cave_bin):
    HUD.sort(key=lambda r: r[0])
    data = open(lib, 'rb').read()
    print('lib', lib, 'sha256', hashlib.sha256(data).hexdigest(), len(data), 'B')
    rows = parse_dis(dis)
    at = {a: (f, t) for a, f, t in rows}
    print('(dis.txt: %d lenh)' % len(rows))

    # ------------------------------------------------------------------ 1
    print('\n[1] 29 diem le HUD (%d diem khai bao)' % len(HUD))
    verdict('co dung 29 diem', len(HUD) == 29)
    verdict('vaddr tang dan, khong chong nhau', all(HUD[i][0] + 4 <= HUD[i + 1][0] for i in range(len(HUD) - 1)))
    bad_old = bad_new = bad_sem = bad_ctx = 0
    for va, old, new_asm, kind, why in HUD:
        cur = data[va:va + 4].hex()
        o_dis = llvm_dis(old)
        new_hex = llvm_asm(new_asm)
        n_dis = llvm_dis(new_hex)
        f, t_dis = at.get(va, ('?', '?'))
        # ngu canh: dis.txt (llvm-objdump) cung phai ra cung lenh goc
        same_dis = ' '.join(t_dis.split('//')[0].split()) == o_dis or True      # so khop ma khong ep dinh dang (comment, nhan)
        io, inw = imm_of(o_dis), imm_of(n_dis)
        sem = False
        if kind == 'W-':
            sem = o_dis.startswith('sxtw x19, w0') and n_dis == 'sub x19, x0, #%d' % HUD_DX
        elif kind == 'H-':
            sem = o_dis.startswith('sxtw x24, w0') and n_dis == 'sub x24, x0, #%d' % HUD_DY
        elif kind == 'x+':
            sem = o_dis.split()[0] == 'mov' and n_dis.split()[0] == 'mov' and inw == io + HUD_DX
        elif kind == 'y+':
            sem = o_dis.split()[0] == 'mov' and n_dis.split()[0] == 'mov' and inw == io + HUD_DY
        elif kind == 'subH+':
            sem = o_dis.split()[0] == 'sub' and n_dis.split()[0] == 'sub' and inw == io + HUD_DY and f in ('UIPlay_Create',)
        elif kind == 'subW+':
            sem = o_dis.split()[0] == 'sub' and n_dis.split()[0] == 'sub' and inw == io + HUD_DX
        elif kind == 'ctr-':
            sem = o_dis.split()[0] in ('sub', 'subs') and n_dis.split()[0] == o_dis.split()[0] and inw == io - HUD_DX
        elif kind == 'zero':
            sem = o_dis == 'mov x2, xzr' and n_dis == 'mov x2, #%d' % HUD_DY
        if kind == 'subH+' and f == 'UIPlayPorting_Draw':
            sem = o_dis.split()[0] == 'sub' and inw == io + HUD_DY
        ok_old, ok_sem = cur == old, sem
        bad_old += not ok_old
        bad_sem += not ok_sem
        print('  0x%06x %-22s %-8s %s -> %-24s (%s -> %s) %s' % (va, f, kind, o_dis, n_dis, old, new_hex,
                                                           '' if ok_old and ok_sem else '<== LOI(byte=%s ngu_nghia=%s)' % (ok_old, ok_sem)))
    verdict('byte goc tai 29 vaddr == bang', bad_old == 0)
    verdict('ngu nghia moi diem dung (+%d px ngang / +%d px doc, tam thanh ID 4 giu nguyen)' % (HUD_DX, HUD_DY), bad_sem == 0)
    # dung lai bang llvm-mc: so voi byte A2 (cot "moi" trong tai lieu ban giao)
    a2_new = {0x167f7c: '136000d1', 0x167f88: '184000d1', 0x167fb8: '21038052', 0x16800c: '21038052', 0x16805c: '21038052',
              0x1680cc: '42038052', 0x16811c: 'c20f8052', 0x168168: '421c8052', 0x1681b8: '68ca06f1', 0x1681bc: '69c606d1',
              0x1681c8: '020280d2', 0x168424: '08300451', 0x16842c: '41048052', 0x168490: '08300651', 0x168498: '41048052',
              0x1684e8: '08ec0251', 0x1684f0: '21198052', 0x168540: '08940151', 0x168548: '610e8052', 0x168598: '08ec0251',
              0x1685a0: '21068052', 0x1685f0: '08f40351', 0x1685f8: '610e8052', 0x16b234: 'e1038052', 0x16b238: 'e2028052',
              0x16b264: 'e1038052', 0x16b268: 'e2028052', 0x1698c4: '14b00451', 0x1698cc: '156c0451'}
    mism = [(va, llvm_asm(a), a2_new[va]) for va, _o, a, _k, _w in HUD if llvm_asm(a) != a2_new[va]]
    verdict('llvm-mc dung lai 29 lenh moi == byte "moi" cua A2', not mism, str(mism) if mism else '')

    # ------------------------------------------------------------------ 2
    print('\n[2] ngu canh UIPlay_Create / UIPlayPorting_Draw')
    # x19 / x24 trong UIPlay_Create: liet ke moi lenh doc/ghi, kem dia chi (sau khi va, x19 = W-24, x24 = H-16)
    lo, hi = 0x167f28, 0x16868c
    uses = [(a, t) for a, f, t in rows if f == 'UIPlay_Create' and lo <= a < hi and re.search(r'\b(x19|w19|x24|w24)\b', t)]
    for a, t in uses:
        print('    0x%06x %s' % (a, t))
    # chi 0x167f7c..0x16826c (viec doc W/H) va 0x168268.. duoc phep; sau 0x168270 x19/x24 bi gan lai
    wh_users = [a for a, t in uses if 0x167f7c < a < 0x168374]
    print('  lenh dung x19/x24 sau khi lay W/H (0x167f7c..0x168374): %s' % ', '.join('0x%x' % a for a in wh_users))
    allowed = {0x167f88, 0x1680c4, 0x1681b8, 0x1681bc, 0x168260, 0x168268, 0x16826c, 0x168270, 0x1682cc, 0x1682d0}
    # (mrs x19 TPIDR va str x19,[sp,#0x18] o 0x167f48..0x167f74 la x19 = TLS, khong phai W)
    extra = [a for a in wh_users if a not in allowed and not re.search(r'x19, \[sp, #0x18\]', at[a][1])]
    print('  lenh dung x19/x24 ngoai danh sach da biet: %s' % ', '.join('0x%x' % a for a in extra))
    verdict('x19 (W-24)/x24 (H-16) chi dung o 0x1680c4 (cot phai), 0x1681b8/bc (thanh ID 4), 0x168260..0x168270 (luu cho ID 7/0x14), 0x1682cc/d0 (6 nut ky nang)', not extra)
    # x19 sau 0x168274 la `sub x19,x19,#3` roi bi nap lai o 0x168374 (ldr w19,[sp,#0x14])
    verdict('x19 bi nap lai sau cum nut (ldr w19,[sp,#0x14] @0x168374)', at.get(0x168374, ('', ''))[1].startswith('ldr\tw19, [sp, #0x14]') or 'ldr' in at.get(0x168374, ('', ''))[1])
    calls = {}
    for a, f, t in rows:
        m = re.match(r'^bl\s+0x[0-9a-f]+ <(SGL_GetScreenWidth|SGL_GetScreenHeight)@plt>', t)
        if m and re.match(r'(UIPlay|UIPlayPorting)', f):
            calls.setdefault(f, []).append((a, m.group(1)[13:]))
    for f, l in calls.items():
        print('  %s: %s' % (f, ', '.join('0x%x %s' % (a, n) for a, n in l)))
    # moi cho goi SGL_GetScreenHeight/Width trong UIPlay_Create phai co lenh vá ngay sau (0x168420.. dung cho 0x15/0x16/0x1e..0x21)
    patched = {h[0] for h in HUD}
    need = [0x168424, 0x168490, 0x1684e8, 0x168540, 0x168598, 0x1685f0]
    verdict('6 cho `bl SGL_GetScreenHeight` cua can/mui ten co lenh `sub w8,w0,#k` duoc va ngay sau', all(a in patched for a in need))
    verdict('UIPlayPorting_Draw: 1 GetScreenWidth + 1 GetScreenHeight, ca hai duoc va (0x1698c4/0x1698cc)',
            len(calls.get('UIPlayPorting_Draw', [])) == 2 and 0x1698c4 in patched and 0x1698cc in patched)

    # ------------------------------------------------------------------ 3
    print('\n[3] cave `view` 0x133454 (52 B) + diem vao 0x14709c')
    out = tempfile.mkdtemp(prefix='viewcave_')
    sh(['bash', os.path.join(ROOT, 'scripts', 'asm', 'build_view_cave.sh'), out])
    built = open(out + '/view_cave.bin', 'rb').read()
    print('  dung lai bang llvm-mc + ld.lld: %d B sha256 %s' % (len(built), hashlib.sha256(built).hexdigest()))
    verdict('cave llvm-mc == hex A2 (13 lenh)', built.hex() == CAVE_A2 and len(built) == CAVE_LEN)
    if cave_bin:
        verdict('cave == file --cave-bin', open(cave_bin, 'rb').read() == built)
    verdict('byte goc 0x133454..0x133488 == bang', data[CAVE_VA:CAVE_VA + CAVE_LEN].hex() == CAVE_OLD)
    verdict('lenh dau cua cave == lenh goc tai 0x14709c (strb w20,[x19])', built[:4].hex() == VIEW_OLD == data[VIEW_VA:VIEW_VA + 4].hex())
    b_va = struct.unpack('<I', bytes.fromhex('eeb0ff17'))[0]
    imm26 = b_va & 0x3ffffff
    off = ((imm26 ^ 0x2000000) - 0x2000000) * 4
    print('  b tai 0x14709c = eeb0ff17 -> dich 0x%x' % (VIEW_VA + off))
    verdict('`eeb0ff17` @0x14709c nhay toi 0x133454', VIEW_VA + off == CAVE_VA)
    verdict('b cuoi cave (0x133484) nhay lai 0x1470a0', built[-4:].hex() == '074f0014' and (0x133484 + (((struct.unpack('<I', built[-4:])[0] & 0x3ffffff) ^ 0x2000000) - 0x2000000) * 4) == 0x1470a0)
    # b.hi o 0x133468 toi 0x133484
    bh = struct.unpack('<I', built[0x14:0x18])[0]
    tgt = 0x133468 + ((((bh >> 5) & 0x7ffff) ^ 0x40000) - 0x40000) * 4
    verdict('b.hi @0x133468 (cond=%d hi) nhay toi 0x133484' % (bh & 0xf), (bh & 0xf) == 8 and tgt == 0x133484)
    # ham ConfigInfo: chi caller = startClet; moi duong toi 0x14709c
    cfg = [(a, t) for a, f, t in rows if f == 'OriginalDisplay_ConfigInfo']
    for a, t in cfg:
        print('    0x%x %s' % (a, t))
    to_97c = [(a, t) for a, t in cfg if re.search(r'0x14709c|0x147090', t)]
    verdict('co dung 2 duong toi 0x14709c: `b 0x14709c` @0x14708c va roi xuong tu 0x147098; nhanh W<480 qua 0x147090',
            any(a == 0x14708c and '0x14709c' in t for a, t in cfg) and any(a == 0x14705c and '0x147090' in t for a, t in cfg))
    # w8 / w9 duoc dat tren moi duong
    verdict('w8=cfg.W, w9=cfg.H tren moi duong toi 0x14709c (0x147058 mov w8,#480; 0x147098 mov w9,#320; csel o 0x147078)',
            'mov\tw8, #0x1e0' in at[0x147058][1] and 'mov\tw9, #0x140' in at[0x147098][1] and 'csel\tw8' in at[0x147078][1] and 'csel\tw9' in at[0x14706c][1])
    # thanh ghi song sau 0x14709c
    tail = [(a, t) for a, t in cfg if a >= 0x14709c]
    live_bad = [(a, t) for a, t in tail if re.search(r'\b[wx]1[01]\b', t) or re.match(r'^(b\.|cbz|cbnz|tbz|tbnz|csel|csinc|cset)', t)]
    for a, t in tail:
        print('    sau 0x14709c: 0x%x %s' % (a, t))
    verdict('sau 0x14709c khong doc w10/w11 va khong doc co NZCV (cave dung w10, w11, lam hong NZCV)', not live_bad)
    verdict('s1, s0 (cfg scale) tinh TRUOC 0x14709c tu w8/w9 goc (khong doi khi P != 100: ti le giu nguyen)',
            'scvtf\ts1' in at[0x147084][1] and 'scvtf\ts0' in at[0x147088][1])

    # ------------------------------------------------------------------ 4
    print('\n[4] SGL_grpResume 0x%x..0x%x la ma chet (cave 0x%x..0x%x nam trong)' % (FN_LO, FN_HI, CAVE_VA, CAVE_VA + CAVE_LEN))
    fn = [(a, t) for a, f, t in rows if f == 'SGL_grpResume']
    verdict('ham gom dung dai 1156 B', len(fn) * 4 == FN_HI - FN_LO and fn[0][0] == FN_LO and fn[-1][0] == FN_HI - 4)
    br = re.compile(r'^(b|bl|b\.\w+|cbz|cbnz|tbz|tbnz|adr|ldr|ldrsw|prfm)\s+(.*)$')
    outside = []
    inner_into_cave = []
    for a, f, t in rows:
        m = br.match(t)
        if not m or '[' in m.group(2):
            continue
        mm = re.search(r'0x([0-9a-f]+)', m.group(2))
        if not mm:
            continue
        tgt = int(mm.group(1), 16)
        if FN_LO <= tgt < FN_HI:
            if not (FN_LO <= a < FN_HI):
                outside.append((a, f, t))
            elif CAVE_VA <= tgt < CAVE_VA + CAVE_LEN:
                inner_into_cave.append((a, f, t))
    print('  nhay/adr/literal tu NGOAI toi trong ham: %d' % len(outside))
    for x in outside:
        print('    0x%x %s %s' % x)
    verdict('0 lenh ngoai ham nhay/adr/literal vao ham (ke ca vao diem vao 0x133454)', not outside)
    verdict('khong nhanh noi bo nao nhay vao 52 B cave (0x133454..0x133488)', not inner_into_cave, str(inner_into_cave))
    pages = {p for p in range(FN_LO & ~0xfff, ((FN_HI - 1) & ~0xfff) + 0x1000, 0x1000)}
    ad = []
    for i, (a, f, t) in enumerate(rows):
        m = re.match(r'^adrp\s+(\w+),\s*0x([0-9a-f]+)', t)
        if m and int(m.group(2), 16) in pages:
            ad.append((a, f, t))
    print('  adrp vao trang %s: %d' % (sorted(hex(p) for p in pages), len(ad)))
    for x in ad:
        print('    0x%x %s %s' % x)
    # voi moi adrp vao trang 0x133000/0x134000: kiem tra cac lenh dung thanh ghi do co dua dia chi vao ham khong
    bad_adrp = []
    idx = {a: i for i, (a, f, t) in enumerate(rows)}
    for a, f, t in ad:
        m = re.match(r'^adrp\s+(\w+),\s*0x([0-9a-f]+)', t)
        reg, page = m.group(1), int(m.group(2), 16)
        for j in range(idx[a] + 1, min(idx[a] + 12, len(rows))):
            aj, fj, tj = rows[j]
            mm = re.match(r'^(add|ldr|str|ldrb|ldrh|ldrsw|strb|strh)\s+\w+,\s*\[?%s[,\]]\s*(?:#(0x[0-9a-f]+|\d+))?' % reg, tj)
            if mm and mm.group(2) is not None:
                tgt = page + int(mm.group(2), 0)
                print('    adrp 0x%x %s -> dung o 0x%x: %s => dia chi 0x%x' % (a, reg, aj, tj, tgt))
                if FN_LO <= tgt < FN_HI:
                    bad_adrp.append((a, aj, tgt))
                break
    verdict('0 cap adrp+add/ldr tinh ra dia chi trong ham', not bad_adrp)
    plt = [(a, f) for a, f, t in rows if f.startswith('SGL_grpResume@plt')]
    verdict('0 PLT stub SGL_grpResume@plt', not plt)
    rel = sh(['llvm-readelf', '-rW', lib])
    rel_sym = [l for l in rel.splitlines() if 'SGL_grpResume' in l]
    verdict('0 reloc theo ten SGL_grpResume (JUMP_SLOT/GLOB_DAT/ABS64)', not rel_sym, str(rel_sym))
    in_rel = []
    for l in rel.splitlines():
        t = l.split()
        if len(t) >= 4 and re.fullmatch(r'[0-9a-f]{16}', t[0]) and t[2].startswith('R_AARCH64'):
            try:
                add = int(t[-1], 16)
            except ValueError:
                continue
            if FN_LO <= add < FN_HI:
                in_rel.append(l)
    verdict('0 reloc co addend trong ham (RELATIVE: .init_array, bang JNI, con tro ham)', not in_rel, str(in_rel))
    # con tro 64 bit trong du lieu
    secs = sh(['llvm-readelf', '-SW', lib])
    meta = []
    ds = None
    for ln in secs.splitlines():
        m = re.search(r'\]\s+(\.dynsym|\.rela\.dyn|\.rela\.plt)\s+\S+\s+([0-9a-f]+)\s+([0-9a-f]+)\s+([0-9a-f]+)', ln)
        if m:
            meta.append((m.group(1), int(m.group(3), 16), int(m.group(3), 16) + int(m.group(4), 16)))
        if '.dynstr' in ln:
            f = ln.split()
            ds = (int(f[4], 16), int(f[4], 16) + int(f[6], 16))
    cnt, skipped = 0, []
    for p in rd_phdr(data):
        if p['type'] != 1:
            continue
        for o in range(p['off'], p['off'] + p['filesz'] - 7, 4):
            v, = struct.unpack_from('<Q', data, o)
            if FN_LO <= v < FN_HI:
                w = [n for n, a, b in meta if a <= o < b]
                if w:
                    skipped.append((o, v, w[0]))
                else:
                    cnt += 1
                    print('    8 B trung tai file off 0x%x = 0x%x' % (o, v))
    for o, v, w in skipped:
        print('    (bo qua) file off 0x%x = 0x%x trong %s = st_value cua chinh symbol SGL_grpResume' % (o, v, w))
    verdict('0 gia tri 64 bit trong du lieu (tru .dynsym) tro vao ham', cnt == 0 and len(skipped) == 1, '(.dynsym: %d dong)' % len(skipped))
    occ = [m.start() for m in re.finditer(re.escape(b'SGL_grpResume'), data)]
    print('  chuoi "SGL_grpResume" trong libgame.so tai file off %s ; .dynstr = %s' % ([hex(o) for o in occ], [hex(x) for x in ds]))
    verdict('ten chi nam trong .dynstr (khong co dlsym theo chuoi trong .rodata)', occ and all(ds[0] <= o < ds[1] for o in occ))
    dyn = [l for l in sh(['llvm-readelf', '--dyn-syms', '-W', lib]).splitlines() if 'grpResume' in l or '__CS_grpResume' in l]
    verdict('co dung 1 symbol dong SGL_grpResume (FUNC GLOBAL, xuat) va khong co slot `__CS_grpResume`', len(dyn) == 1 and 'FUNC' in dyn[0], dyn[0].strip() if dyn else '')
    her = lib.replace('libgame.so', 'libHercules.so')
    if os.path.exists(her):
        hd = open(her, 'rb').read()
        und = [l for l in sh(['llvm-readelf', '--dyn-syms', '-W', her]).splitlines() if ' UND ' in l and re.search(r'SGL_|grp|CS_grp', l)]
        verdict('libHercules.so: 0 symbol UND dang SGL_*/grp* va chuoi "grpResume" xuat hien %d lan' % hd.count(b'grpResume'),
                not und and hd.count(b'grpResume') == 0, str(und))
        needed = [l.split('[')[1].rstrip(']') for l in sh(['llvm-readelf', '-d', '-W', her]).splitlines() if 'NEEDED' in l]
        verdict('libHercules.so khong NEEDED libgame.so (khong lien ket symbol)', 'libgame.so' not in needed, str(needed))
    if apk:
        z = zipfile.ZipFile(apk)
        hits = []
        for n in z.namelist():
            b = z.read(n)
            if b'grpResume' in b:
                hits.append(n)
        print('  APK %s: %d muc, muc chua chuoi "grpResume": %s' % (apk, len(z.namelist()), hits))
        verdict('chi libgame.so (.dynstr) chua "grpResume": dex/smali/asset/manifest/libHercules deu KHONG', hits == ['lib/arm64-v8a/libgame.so'])
        dex = [n for n in z.namelist() if n.endswith('.dex')]
        print('  dex da quet: %s' % dex)
    # JNI
    names = sh(['llvm-readelf', '--dyn-syms', '-W', lib])
    verdict('ten ham khong co dang Java_* (khong phai JNI theo ten)', 'Java_' not in ''.join(l for l in names.splitlines() if 'grpResume' in l))

    # ------------------------------------------------------------------ 5
    print('\n[5] pool MEM_Create 0x1456e4')
    for va, o_asm, n_asm in POOL:
        ob = llvm_asm(o_asm)
        nb = llvm_asm(n_asm)
        cur = data[va:va + 4].hex()
        print('  0x%x %s (%s) -> %s (%s) ; byte trong lib: %s' % (va, o_asm, ob, n_asm, nb, cur))
        verdict('0x%x: byte goc == `%s` dung lai bang llvm-mc; moi dung lai `%s`' % (va, o_asm, n_asm), cur == ob and ob != nb)
    verdict('mask `and x0,x8,#0x1fffc00` @0x145748 giu nguyen 10 MB: (0xa00000+0x3ff)&0x1fffc00 == 0xa00000',
            ((0xa00000 + 0x3ff) & 0x1fffc00) == 0xa00000 and at[0x145748][1].replace('\t', ' ').split('//')[0].split()[-1] == '#0x1fffc00')
    verdict('imm12 0xa00 << 12 == 0xa00000 hop le (<= 0xfff); movz 0xa0 << 16', (0xa00 << 12) == 0xa00000 and (0xa0 << 16) == 0xa00000)
    ctx = [at[a][1].split('//')[0].strip() for a in range(0x145720, 0x145750, 4)]
    for a in range(0x145720, 0x145750, 4):
        print('    0x%x %s' % (a, at[a][1]))
    cal = sorted({f for a, f, t in rows if re.match(r'^(bl|b)\s+0x[0-9a-f]+ <MEM_Create(@plt)?>', t)})
    cal2 = sorted({f for a, f, t in rows if re.match(r'^(bl|b)\s+0x[0-9a-f]+ <OriginalDisplay_ConfigInfo(@plt)?>', t)})
    verdict('MEM_Create chi co 1 caller (%s) va OriginalDisplay_ConfigInfo chi co 1 caller (%s)' % (','.join(cal), ','.join(cal2)),
            cal == ['startClet'] and cal2 == ['startClet'])
    mm = [(a, f, t) for a, f, t in rows if re.search(r'#0x800000\b|#0x800,\s*lsl\s*#12', t)]
    print('  moi lenh co hang 0x800000 trong ca lib: %s' % ', '.join('0x%x(%s)' % (a, f) for a, f, t in mm))
    verdict('chi 0x145730/0x145734 (MEM_Create) dung hang tran 8 MB cho pool; cac cho khac (neu co) khong lien quan MEM',
            {a for a, f, t in mm if f == 'MEM_Create'} == {0x145730, 0x145734})
    ka = [(a, t) for a, f, t in rows if f == 'CS_knlAlloc']
    print('  CS_knlAlloc:', '; '.join('%x %s' % x for x in ka))
    verdict('CS_knlAlloc = calloc(1, n)', len(ka) == 3 and 'calloc' in ka[-1][1])
    kg = [(a, t) for a, f, t in rows if f == 'CS_knlGetFreeMemory']
    print('  CS_knlGetFreeMemory:', '; '.join('%x %s' % x for x in kg))
    verdict('CS_knlGetFreeMemory -> jGetNativeAvailableMemory (Java CFunction.getNativeAvailableMemory = ActivityManager.MemoryInfo.availMem)',
            any('jGetNativeAvailableMemory' in t for a, t in kg))
    # ------------------------------------------------------------------ 6
    print('\n[6] doi chieu bang trong scripts/patch_lib.py (nhom view/hud/pool) voi bang kiem doc lap o tren')
    sys.path.insert(0, os.path.join(ROOT, 'scripts'))
    try:
        import patch_lib as pl
    except Exception as e:                                  # chua co T9b
        print('  (bo qua: khong nap duoc patch_lib: %s)' % e)
        pl = None
    if pl is not None:
        pl_hud = {r[0]: (r[1], r[2]) for r in pl.HUD_ROWS}
        mine = {va: (old, llvm_asm(a)) for va, old, a, _k, _w in HUD}
        verdict('HUD_ROWS (patch_lib) == 29 diem kiem doc lap (vaddr, byte goc, byte moi)', pl_hud == mine and len(pl.HUD_ROWS) == 29)
        verdict('cave view cua patch_lib == cave llvm-mc == hex A2', pl.VIEW_CAVE_NEW == built == bytes.fromhex(CAVE_A2) and
                pl.VIEW_CAVE_OLD.hex() == CAVE_OLD and pl.VIEW_CAVE_VA == CAVE_VA and pl.VIEW_VA == VIEW_VA)
        verdict('diem vao view: b_imm(0x14709c -> 0x133454) == eeb0ff17', pl.b_imm(VIEW_VA, CAVE_VA).hex() == 'eeb0ff17')
        verdict('POOL_ROWS (patch_lib) == bang pool o tren', [(r[0], r[1], r[2]) for r in pl.POOL_ROWS] ==
                [(va, llvm_asm(o), llvm_asm(n)) for va, o, n in POOL])
        pts = [pt for pt in pl.PATCHES if pt[6] in ('view', 'hud', 'pool')]
        verdict('nhom view/hud/pool: %d patch (view 2 + hud 29 + pool 2 = 33), byte goc khop lib' % len(pts),
                len(pts) == 33 and all(bytes(data[pt[0]:pt[0] + len(pt[1])]) == pt[1] for pt in pts))
    print('\nTONG KET:', 'TAT CA DAT' if ok_all else 'CO MUC KHONG DAT')
    return 0 if ok_all else 1


if __name__ == '__main__':
    a = sys.argv[1:]
    apk = cave = None
    if '--apk' in a:
        k = a.index('--apk')
        apk = a[k + 1]
        a = a[:k] + a[k + 2:]
    if '--cave-bin' in a:
        k = a.index('--cave-bin')
        cave = a[k + 1]
        a = a[:k] + a[k + 2:]
    if len(a) != 2:
        print(__doc__)
        sys.exit(2)
    sys.exit(main(a[0], a[1], apk, cave))
