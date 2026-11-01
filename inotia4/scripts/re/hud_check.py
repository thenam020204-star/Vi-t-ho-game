#!/usr/bin/env python3
"""T10a: kiem lai doc lap bang byte/disasm cac bang loc cua UI_014 / UI_015 va 3 lenh kieu ve `mov w5,#2` (khong tin ban giao A2).

    python3 -I scripts/re/hud_check.py <libgame.so> [--atlas-dir <thu muc game_res>] [--allow-patched]

Cac muc (moi muc in DAT / KHONG DAT, dong cuoi TAT CA DAT hoac CO MUC KHONG DAT):
  [1] Ten nhom: IMGSYS_UnitLoad nap `GRPX_LoadImage(name_table + g*64)`, name_table = GOT 0x2bddb8 -> 0x2cc370; nhom 0x15 = UI_014.png, 0x16 = UI_015.png.
  [2] Bang loc: IMGSYS_GetLoc (0x12c3a8): bang nhay byte @0xacb60 -> `mov w8,#stride; adr x9,base; b 0x12c628`; base UI_014 = 0xaf3cc,
      UI_015 = 0xaf474, stride 24 B. In het cac muc va doi chieu 7 rect can thay + 4 rect GIU (loc4/5/10/17 va nen ban nguyet).
  [3] GRPX_DrawPart (0x12a43c): tham so (x0 nhom, w1 x, w2 y, x3 loc, w4 neo, w5 kieu, w6 %); kieu -> SGL_DrawTexturePartEx [sp+8] -> w20;
      voi anh RGBA (0x1908): 1 = ve thuong, 2 = glBlendFunc(SRC_ALPHA, ONE) (0x1318c8) roi tra ve (SRC_ALPHA, ONE_MINUS_SRC_ALPHA).
  [4] Moi noi goi IMGSYS_GetLoc voi (w0 = 0x15, w1 = 3) = dung 3 noi: 0x169ad0, 0x169b9c, 0x169be4; moi noi co `mov w5,#2` o 0x169ae8, 0x169bb4, 0x169bfc
      (byte 45008052), theo sau `mov w6,wzr; bl GRPX_DrawPart`, truoc do `mov w4,#2` va `bl IMGSYS_GetLoc`; va byte moi 25008052 = `mov w5,#1` (llvm-mc).
  [5] (neu co --atlas-dir) kich thuoc atlas 512x512 RGBA va moi rect nam trong atlas, khong chong len nhau.
  [6] Dieu khien -> loc trong UIPlayPorting_Draw (0x16985c): ID 1 -> UI_015 loc1, ID 2 -> loc2, ID 3 -> loc0, ID 7 -> loc3, ID 0x14 -> UI_014 loc2
      (+ loc3 lop bam): nhanh nhay `cmp xID` -> khoi `GetGroup(0x16); GetLoc(0x16, loc)` trong ban dich.
  [7] Thu tu ve o khung ky nang (loc11 se dung khung dac): nen loc5 -> vong loc11 -> icon (`CONVERTSKILL_GetIconGroup`) o ca 3 ham: SHORTCUTPorting_Draw (HUD),
      UIShortcutMenu_Draw va UIShortcutMenu_DrawShortcutControl (popup gan phim tat) => khung dac khong che icon.
"""
import hashlib
import os
import struct
import subprocess
import sys
import tempfile

ok_all = True


def verdict(name, ok, detail=''):
    global ok_all
    ok_all = ok_all and ok
    print('  -> %s: %s %s' % ('DAT' if ok else 'KHONG DAT', name, detail))


class Elf:
    """Chi doc PT_LOAD: vaddr -> byte trong file."""

    def __init__(self, path):
        self.d = open(path, 'rb').read()
        d = self.d
        phoff = struct.unpack_from('<Q', d, 0x20)[0]
        phentsize, phnum = struct.unpack_from('<HH', d, 0x36)
        self.loads = []
        for i in range(phnum):
            t, fl, off, va, pa, fsz, msz, al = struct.unpack_from('<IIQQQQQQ', d, phoff + i * phentsize)
            if t == 1:
                self.loads.append((va, off, fsz, msz))
        self.reloc = {}
        self.symval = []
        shoff = struct.unpack_from('<Q', d, 0x28)[0]
        shentsize, shnum = struct.unpack_from('<HH', d, 0x3a)
        secs = [struct.unpack_from('<IIQQQQIIQQ', d, shoff + i * shentsize) for i in range(shnum)]
        for s in secs:
            if s[1] == 11:                                            # SHT_DYNSYM
                self.symval = [struct.unpack_from('<IBBHQQ', d, s[4] + j * 24)[4] for j in range(s[5] // 24)]
        for s in secs:
            if s[1] == 4:                                             # SHT_RELA
                for j in range(s[5] // 24):
                    off, info, add = struct.unpack_from('<QQq', d, s[4] + j * 24)
                    self.reloc[off] = (info & 0xffffffff, info >> 32, add)

    def rd(self, va, n):
        for v, o, fsz, msz in self.loads:
            if v <= va < v + msz:
                if va - v + n > fsz:
                    raise KeyError('0x%x nam trong .bss' % va)
                return self.d[o + va - v:o + va - v + n]
        raise KeyError('0x%x ngoai PT_LOAD' % va)

    def u32(self, va):
        return struct.unpack('<I', self.rd(va, 4))[0]

    def slot(self, va):
        """Con tro luu o GOT/data: RELATIVE reloc -> addend, khong thi gia tri trong file."""
        r = self.reloc.get(va)
        if r:
            t, sym, add = r
            if t == 0x403:                                            # R_AARCH64_RELATIVE
                return add
            if t in (0x401, 0x402, 0x101):                            # GLOB_DAT / JUMP_SLOT / ABS64: gia tri symbol + addend
                return self.symval[sym] + add
        return struct.unpack('<Q', self.rd(va, 8))[0]


def objdump(path, beg, end):
    out = subprocess.run(['llvm-objdump', '-d', '--no-show-raw-insn', '--start-address=0x%x' % beg, '--stop-address=0x%x' % end, path],
                         capture_output=True, text=True, check=True).stdout
    rows = {}
    for line in out.splitlines():
        s = line.strip()
        if ':' in s and s.split(':', 1)[0].isalnum():
            a, t = s.split(':', 1)
            try:
                rows[int(a, 16)] = ' '.join(t.split('//')[0].split())
            except ValueError:
                pass
    return rows


def llvm_asm(text):
    d = tempfile.mkdtemp(prefix='hudasm_')
    open(d + '/a.s', 'w').write('.text\n%s\n' % text)
    subprocess.run(['llvm-mc', '-triple=aarch64-linux-gnu', '-mattr=+v8a', '-filetype=obj', '-o', d + '/a.o', d + '/a.s'], check=True, capture_output=True)
    subprocess.run(['llvm-objcopy', '-O', 'binary', '-j', '.text', d + '/a.o', d + '/a.bin'], check=True)
    return open(d + '/a.bin', 'rb').read()


def sx(v, bits):
    return v - (1 << bits) if v >> (bits - 1) else v


# ---------------------------------------------------------------------------------------------------------------
# Du kien (PLAN.md muc T10, "Quyet dinh cua quan doc")
PLT_GETLOC = 0x2a8f80
PLT_DRAWPART = 0x2a89a0
GETLOC_FN = 0x12c3a8
JT_BYTES = 0xacb60
JT_CODE = 0x12c3e4
NAME_TABLE_GOT = 0x2bddb8
HANDLE_GOT = 0x2bddc0
SITES_MOV_W5 = (0x169ae8, 0x169bb4, 0x169bfc)
SITES_GETLOC = (0x169ad0, 0x169b9c, 0x169be4)
# (nhom, loc, ten, (x,y,w,h), thay?)
RECTS = [
    (0x16, 0, 'UI_015 loc0  banh rang  <- btn_pause', (2, 2, 75, 75), True),
    (0x16, 1, 'UI_015 loc1  luu        <- btn_save', (80, 2, 75, 75), True),
    (0x16, 2, 'UI_015 loc2  gio hang   <- btn_shop', (158, 2, 75, 75), True),
    (0x16, 3, 'UI_015 loc3  doi (swap) <- btn_swap', (236, 2, 75, 75), True),
    (0x16, 11, 'UI_015 loc11 vong o ky nang <- btn_frame', (416, 99, 94, 94), True),
    (0x15, 2, 'UI_014 loc2  tan cong   <- attack', (281, 128, 159, 160), True),
    (0x15, 3, 'UI_014 loc3  tan cong bam <- attack_pressed', (281, 291, 159, 160), True),
    (0x15, 1, 'UI_014 loc1  nen ban nguyet (GIU)', (2, 128, 276, 267), False),
    (0x16, 4, 'UI_015 loc4  lop bam chung (GIU)', (314, 2, 75, 75), False),
    (0x16, 5, 'UI_015 loc5  nen o ky nang (GIU)', (416, 2, 94, 94), False),
    (0x16, 10, 'UI_015 loc10 cham so luong (GIU)', (375, 81, 38, 40), False),
    (0x16, 17, 'UI_015 loc17 o ky nang bam (GIU)', (416, 196, 94, 94), False),
]


def decode_getloc(e, g):
    """Giai ma nhanh cua IMGSYS_GetLoc cho nhom g bang byte: (stride, base)."""
    b = e.rd(JT_BYTES + g, 1)[0]
    a = JT_CODE + b * 4
    stride = base = None
    for _ in range(8):
        w = e.u32(a)
        if (w & 0xffe0001f) == 0x52800008:                                # mov w8,#imm
            stride = (w >> 5) & 0xffff
        elif (w & 0x9f00001f) == 0x10000009:                              # adr x9,label
            imm = sx((((w >> 5) & 0x7ffff) << 2) | ((w >> 29) & 3), 21)
            base = a + imm
        elif (w >> 26) == 0x5:                                            # b
            break
        a += 4
    return stride, base


def main():
    args = sys.argv[1:]
    if not args:
        print(__doc__)
        return 2
    path = args[0]
    atlas_dir = args[args.index('--atlas-dir') + 1] if '--atlas-dir' in args else None
    allow_patched = '--allow-patched' in args
    e = Elf(path)
    print('lib  sha256', hashlib.sha256(e.d).hexdigest(), path)

    print('\n[1] Ten nhom (IMGSYS_UnitLoad @0x12ba18 -> GRPX_LoadImage(name_table + g*64))')
    rows = objdump(path, 0x12ba18, 0x12ba58)
    want = ['ldr x8, [x8, #0xdb8]', 'add x0, x8, x20, lsl #6', 'bl 0x2a8920 <GRPX_LoadImage@plt>']
    txt = '\n'.join(rows.values())
    verdict('IMGSYS_UnitLoad: ldr [GOT 0x2bddb8]; add x0,x8,x20,lsl #6; bl GRPX_LoadImage', all(w in txt for w in want))
    nt = e.slot(NAME_TABLE_GOT)
    hd = e.slot(HANDLE_GOT)
    verdict('GOT 0x2bddb8 -> bang ten 0x2cc370 (64 B/muc), GOT 0x2bddc0 -> mang con tro nhom 0x2d0ce8 (8 B/muc)', nt == 0x2cc370 and hd == 0x2d0ce8,
            '(%#x, %#x)' % (nt, hd))
    names = {}
    for g in (0x15, 0x16):
        names[g] = e.rd(nt + g * 64, 64).split(b'\0')[0].decode()
        print('    nhom 0x%02x @0x%x = %s' % (g, nt + g * 64, names[g]))
    verdict('nhom 0x15 = UI_014.png, 0x16 = UI_015.png', names[0x15] == 'UI_014.png' and names[0x16] == 'UI_015.png')
    rows = objdump(path, 0x12b970, 0x12b9d0)
    verdict('IMGSYS_Load nap 98 nhom (0x310/8): vong `cmp x21, #0x310`', any('cmp x21, #0x310' in t for t in rows.values()))

    print('\n[2] Bang loc (IMGSYS_GetLoc @0x12c3a8)')
    rows = objdump(path, GETLOC_FN, GETLOC_FN + 0x40)
    t = '\n'.join(rows.values())
    verdict('GetLoc: `cmp w0,#0x61`, bang nhay byte `adr x9,0xacb60`, `br x10` (98 nhom)', 'cmp w0, #0x61' in t and 'adr x9, 0xacb60' in t and 'br x10' in t)
    info = {}
    for g in (0x15, 0x16):
        stride, base = decode_getloc(e, g)
        info[g] = (stride, base)
        print('    nhom 0x%02x (%s): stride=%s base=%s' % (g, names[g], stride, hex(base) if base else None))
    verdict('UI_014 base 0xaf3cc, UI_015 base 0xaf474, stride 24 B', info[0x15] == (24, 0xaf3cc) and info[0x16] == (24, 0xaf474), str({k: (v[0], hex(v[1])) for k, v in info.items()}))

    def loc(g, i):
        stride, base = info[g]
        return struct.unpack('<6i', e.rd(base + i * stride, 24))

    # so muc moi bang = (base ke tiep - base) / stride, base ke tiep lay tu bang nhay cua 98 nhom
    bases = sorted({b for b in (decode_getloc(e, g)[1] for g in range(0x62)) if b})
    count = {g: (min(b for b in bases if b > info[g][1]) - info[g][1]) // info[g][0] for g in (0x15, 0x16)}
    print('    so muc: UI_014 = %d (bang ket thuc dung tai base UI_015), UI_015 = %d' % (count[0x15], count[0x16]))
    verdict('UI_014 co 7 muc (0xaf3cc + 7*24 = 0xaf474), UI_015 co 40 muc (0xaf474 + 40*24 = 0xaf834)', count == {0x15: 7, 0x16: 40}, str(count))
    for g, n in ((0x15, count[0x15]), (0x16, count[0x16])):
        print('    %s (x, y, w, h, u4, u5):' % names[g])
        for i in range(n):
            v = loc(g, i)
            if not any(v):
                break
            tag = ''
            for gg, ii, nm, r, rep in RECTS:
                if gg == g and ii == i:
                    tag = ('<== THAY ' if rep else '<== GIU  ') + ' '.join(nm.split()[:2])
            print('      loc%-2d %4d %4d %4d %4d  %d %d  %s' % ((i,) + v + (tag,)))
    for g, i, nm, r, rep in RECTS:
        v = loc(g, i)
        verdict('%s = %s' % (nm, r), tuple(v[:4]) == r and v[4] == 0 and v[5] == 0, str(v))

    print('\n[3] GRPX_DrawPart (0x12a43c): kieu ve = w5 -> [sp+8] cua SGL_DrawTexturePartEx -> w20')
    rows = objdump(path, 0x12a43c, 0x12a4c4)
    t = '\n'.join('%x %s' % (a, s) for a, s in sorted(rows.items()))
    need = ['mov w8, w5', 'str w6, [sp, #0x10]', 'str w8, [sp, #0x8]', 'bl 0x2a89b0 <SGL_DrawTexturePartEx@plt>', 'ldr w5, [x3, #0x8]', 'ldp w9, w4, [x3]']
    verdict('GRPX_DrawPart: w8=w5 (kieu) -> [sp,#8]; w6 (%) -> [sp,#0x10]; rect = x3 (x,y,w,h); bl SGL_DrawTexturePartEx', all(s in t for s in need))
    rows = objdump(path, 0x131340, 0x131400)
    t = '\n'.join(rows.values())
    verdict('SGL_DrawTexturePartEx: w20 = [x29,#0x68] (= [sp,#8] cua nguoi goi)', 'ldr w20, [x29, #0x68]' in t and 'sub sp, sp, #0x80' in t and 'add x29, sp, #0x20' in t)
    rows = objdump(path, 0x131768, 0x131790)
    t = '\n'.join(rows.values())
    verdict('dinh dang texture [x19+0x60]: 0x1906 -> nhanh A8, 0x1907 -> RGB, 0x1908 (RGBA) -> nhanh 0x131790',
            'mov w9, #0x1908' in t and 'b.ne 0x1318a0' in t)
    rows = objdump(path, 0x131790, 0x1317c0)
    t = '\n'.join('%x %s' % (a, s) for a, s in sorted(rows.items()))
    verdict('nhanh RGBA: kieu 1 -> 0x131858 (glDrawArrays), kieu 2 -> 0x1318c8, kieu > 2 -> 0x131884',
            'cmp w20, #0x2' in t and 'b.gt 0x131884' in t and 'cmp w20, #0x1' in t and 'b.eq 0x131858' in t and 'b.eq 0x1318c8' in t)
    rows = objdump(path, 0x131858, 0x131888)
    t = '\n'.join(rows.values())
    verdict('kieu 1: chi `glDrawArrays` (khong doi blend)', 'b 0x2a96c0 <glDrawArrays@plt>' in t and 'glBlendFunc' not in t)
    rows = objdump(path, 0x1318c8, 0x131918)
    t = '\n'.join('%x %s' % (a, s) for a, s in sorted(rows.items()))
    verdict('kieu 2: glBlendFunc(0x302 = SRC_ALPHA, 1 = ONE); ve; tra ve (0x302, 0x303 = ONE_MINUS_SRC_ALPHA)',
            'mov w0, #0x302' in t and 'mov w1, #0x1' in t and 'bl 0x2a9510 <glBlendFunc@plt>' in t and 'mov w1, #0x303' in t and 'glDrawArrays' in t)

    print('\n[4] Cac noi goi IMGSYS_GetLoc(0x15, 3) va 3 lenh `mov w5,#2`')
    # quet nhi phan .text: moi `bl PLT_GETLOC`, tim `mov w0,#imm` / `mov w1,#imm` gan nhat phia truoc (toi da 12 lenh)
    text_beg, text_end = 0x127580, 0x2a81bc
    sites = []
    for a in range(text_beg, text_end, 4):
        w = e.u32(a)
        if (w >> 26) == 0x25 and a + sx(w & 0x3ffffff, 26) * 4 == PLT_GETLOC:
            w0 = w1 = None
            for k in range(1, 13):
                p = e.u32(a - 4 * k)
                if w0 is None and (p & 0xffe0001f) == 0x52800000:
                    w0 = (p >> 5) & 0xffff
                if w1 is None and (p & 0xffe0001f) == 0x52800001:
                    w1 = (p >> 5) & 0xffff
                if (p >> 26) in (0x25, 0x5) or (p & 0xfffffc1f) == 0xd65f0000:     # bl / b / ret: dung
                    break
            sites.append((a, w0, w1))
    print('    tong so noi goi IMGSYS_GetLoc@plt: %d' % len(sites))
    hits = [a for a, w0, w1 in sites if w0 == 0x15 and w1 == 3]
    print('    noi goi voi (w0 = 0x15, w1 = 3):', [hex(a) for a in hits])
    verdict('dung 3 noi goi UI_014 loc3', tuple(hits) == SITES_GETLOC)
    # cac noi goi lien quan toi UI_014 (w0 = 0x15) de biet loc nao duoc ve o dau
    print('    moi noi goi UI_014:', ', '.join('%x:loc%s' % (a, w1) for a, w0, w1 in sites if w0 == 0x15))
    expect_old = bytes.fromhex('45008052')
    expect_new = bytes.fromhex('25008052')
    mc = llvm_asm('mov w5, #1')
    verdict('llvm-mc: `mov w5,#1` = 25008052 (va `mov w5,#2` = 45008052)', mc == expect_new and llvm_asm('mov w5, #2') == expect_old)
    for site, gl in zip(SITES_MOV_W5, SITES_GETLOC):
        got = e.rd(site, 4)
        state = 'goc (45008052)' if got == expect_old else ('DA VA (25008052)' if got == expect_new else 'LA ' + got.hex())
        rows = objdump(path, gl - 0x10, site + 0x10)
        seq = [rows.get(gl - 0x10 + 4 * i) for i in range(0, (site + 0x10 - (gl - 0x10)) // 4)]
        ctx = {a: s for a, s in rows.items()}
        ok = (ctx.get(gl - 4) == 'mov w1, #0x3' and ctx.get(gl - 8) == 'mov w0, #0x15' and ctx.get(gl) == 'bl 0x2a8f80 <IMGSYS_GetLoc@plt>' and
              ctx.get(gl + 4) == 'mov x3, x0' and ctx.get(gl + 8) == 'mov x0, x24' and ctx.get(gl + 0xc) == 'mov w1, w22' and ctx.get(gl + 0x10) == 'mov w2, w23')
        ok2 = (ctx.get(site - 4) == 'mov w4, #0x2' and ctx.get(site) == 'mov w5, #0x2' and ctx.get(site + 4) == 'mov w6, wzr' and
               ctx.get(site + 8) == 'bl 0x2a89a0 <GRPX_DrawPart@plt>') if got == expect_old else None
        print('    0x%x: %s' % (site, state))
        for a in range(gl - 8, site + 12, 4):
            r = objdump(path, a, a + 4)
            print('        %x  %s' % (a, r.get(a)))
        if got == expect_old:
            verdict('0x%x: GetLoc(0x15,3) @0x%x -> GRPX_DrawPart(x0=nhom, w1,w2=toa do, x3=loc3, w4=2 (tam), w5=2 (kieu), w6=0)' % (site, gl), ok and ok2)
        elif allow_patched and got == expect_new:
            verdict('0x%x: da vá, ngu canh GetLoc/DrawPart van dung' % site, ok)
        else:
            verdict('0x%x: byte la %s' % (site, got.hex()), False)
    # moi `mov w5,#2` (45008052) ngay truoc `mov w6,wzr; bl GRPX_DrawPart` trong UIPlayPorting_Draw: cac kieu ve khac duoc giu nguyen
    n_all = 0
    for a in range(0x16985c, 0x16b200, 4):
        if e.u32(a) in (0x52800045, 0x52800025) and e.u32(a + 4) == 0x2a1f03e6 and e.u32(a + 8) >> 26 == 0x25:
            tgt = a + 8 + sx(e.u32(a + 8) & 0x3ffffff, 26) * 4
            if tgt == PLT_DRAWPART:
                n_all += 1
                print('    UIPlayPorting_Draw: %x kieu=%d' % (a, (e.u32(a) >> 5) & 0xffff))
    print('    so noi `mov w5,#k; mov w6,wzr; bl GRPX_DrawPart` trong 0x16985c..0x16b200: %d' % n_all)

    print('\n[6] Dieu khien (ControlObject_GetID) -> loc trong UIPlayPorting_Draw')
    disp = objdump(path, 0x16a130, 0x16a320)
    for cid, name, entry, grp, locno, gl in ((1, 'ID1 UIPlay_CallSave', 0x16a350, 0x16, 1, 0x16a368), (2, 'ID2 CallInAppShop', 0x16a260, 0x16, 2, 0x16a278),
                                              (3, 'ID3 CallMenu', 0x16a404, 0x16, 0, 0x16a41c), (7, 'ID7 ButtonSwap', 0x16a304, 0x16, 3, 0x16a318)):
        r = objdump(path, entry, gl + 4)
        seq = [r.get(a) for a in range(entry, gl + 4, 4)]
        wl = 'mov w1, wzr' if locno == 0 else 'mov w1, #0x%x' % locno
        ok_blk = ('mov w0, #0x%x' % grp) in seq and wl in seq and seq[-1] == 'bl 0x2a8f80 <IMGSYS_GetLoc@plt>' and any(t and t.startswith('bl 0x2a8f70') for t in seq)
        want_cmp = {1: 'cmp x22, #0x1', 2: 'cmp x22, #0x2', 3: 'cmp x22, #0x3', 7: 'cmp x22, #0x7'}[cid]
        ok_cmp = want_cmp in disp.values() or want_cmp in objdump(path, 0x16a2fc, 0x16a304).values()
        verdict('%s: dispatch `%s` -> khoi 0x%x: GetGroup(0x%x), GetLoc(0x%x, %d) @0x%x -> UI_015 loc%d' % (name, want_cmp, entry, grp, grp, locno, gl, locno), ok_blk and ok_cmp)
    r = objdump(path, 0x169a28, 0x169a68)
    verdict('ID 0x14 (tan cong): `cmp w0,#0x14` -> GetLoc(0x15, 2) @0x169a44 -> GRPX_DrawPart kieu 1 (0x169a5c `mov w5,#1`)',
            r.get(0x169a28) == 'cmp w0, #0x14' and r.get(0x169a40) == 'mov w1, #0x2' and r.get(0x169a3c) == 'mov w0, #0x15' and r.get(0x169a5c) == 'mov w5, #0x1')

    print('\n[7] Thu tu ve o ky nang: loc5 -> loc11 -> icon')
    PLT_ICONGROUP = 0x2a8fa0
    for fn, lo, hi in (('SHORTCUTPorting_Draw', 0x1d0e00, 0x1d1000), ('UIShortcutMenu_Draw', 0x170340, 0x170430),
                       ('UIShortcutMenu_DrawShortcutControl', 0x1705a0, 0x170660)):
        l5 = [a for a, w0, w1 in sites if lo <= a < hi and w0 == 0x16 and w1 == 5]
        l11 = [a for a, w0, w1 in sites if lo <= a < hi and w0 == 0x16 and w1 == 11]
        ic = [a for a in range(lo, hi, 4) if (e.u32(a) >> 26) == 0x25 and a + sx(e.u32(a) & 0x3ffffff, 26) * 4 == PLT_ICONGROUP]
        verdict('%s: GetLoc(0x16,5) @%s < GetLoc(0x16,11) @%s < GetIconGroup @%s' % (fn, [hex(a) for a in l5], [hex(a) for a in l11], [hex(a) for a in ic[:1]]),
                bool(l5 and l11 and ic) and l5[0] < l11[0] < ic[0])

    if atlas_dir:
        print('\n[5] Atlas (%s)' % atlas_dir)
        from PIL import Image                                          # noqa: PLC0415 (chi can khi kiem atlas)
        for g in (0x15, 0x16):
            f = os.path.join(atlas_dir, names[g] + '.jpg')
            im = Image.open(f)
            verdict('%s: PNG %s %s (khong phai JPEG du duoi .jpg)' % (os.path.basename(f), im.format, im.size), im.format == 'PNG' and im.size == (512, 512) and im.mode == 'RGBA',
                    'mode=%s sha256=%s' % (im.mode, hashlib.sha256(open(f, 'rb').read()).hexdigest()[:16]))
            rects = [(i, tuple(loc(g, i)[:4])) for i in range(count[g])]
            inside = all(r[0] >= 0 and r[1] >= 0 and r[0] + r[2] <= 512 and r[1] + r[3] <= 512 for _, r in rects)
            verdict('%s: %d rect, deu nam trong 512x512' % (names[g], len(rects)), inside)
            bad = []
            for i, (x, y, w, h) in rects:
                for j, (x2, y2, w2, h2) in rects:
                    if j > i and x < x2 + w2 and x2 < x + w and y < y2 + h2 and y2 < y + h:
                        bad.append((i, j))
            print('    cap rect chong len nhau:', bad if bad else 'khong')
            rep = {i for gg, i, _, _, r in RECTS if gg == g and r}
            verdict('%s: cac rect THAY %s khong chong len rect nao khac' % (names[g], sorted(rep)), not any(i in rep or j in rep for i, j in bad))

    print('\nKET LUAN:', 'TAT CA DAT' if ok_all else 'CO MUC KHONG DAT')
    return 0 if ok_all else 1


if __name__ == '__main__':
    sys.exit(main())
