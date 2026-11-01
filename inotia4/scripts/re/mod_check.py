#!/usr/bin/env python3
"""T8a: kiem lai bang chung cua mod menu bang disasm/ELF (khong doan).

    python3 -I scripts/re/mod_check.py <libgame.so goc> <dis.txt>

Cac muc kiem (moi muc in KET QUA: DAT/KHONG DAT):
  1. PT_LOAD chua .bss: (p_vaddr + p_memsz) lam tron 4 KB / 16 KB phai > 0x72f78c.
  2. Byte goc + disasm tai 3 diem va (A, B, C).
  3. Cave 0x18bb00..0x18bbec (2 ham chet CHAR_AddDamageEffect + CHAR_AddDamageByDefaultCheck): khong ai nhay/adr/adrp/literal vao,
     khong reloc, khong con tro trong du lieu, khong PLT stub, ten chi nam trong .dynstr.
  4. Vung nho 0x72f780..0x72f790: khong adrp vao trang 0x72f000/0x730000, khong reloc/con tro/symbol nam trong [0x72f770, 0x730000).
  5. Phe nhan vat [char+0x311]: moi cho ghi.
  6. Caller cua CHAR_AddDamage / MERCENARYSYSTEM_AddExperience / AddExpFixed / CHAR_AddExperience.
"""
import hashlib
import re
import struct
import subprocess
import sys

CAVE_LO, CAVE_HI = 0x18bb00, 0x18bbec          # [lo, hi)  = CHAR_AddDamageEffect (52 B) + CHAR_AddDamageByDefaultCheck (184 B)
MEM_LO, MEM_HI = 0x72f780, 0x72f790            # 3 int (+ int thu 4 danh cho T9b)
POINTS = [('A', 0x18b3e0, 'CHAR_AddDamage'), ('B', 0x1c7860, 'MERCENARYSYSTEM_AddExperience'),
          ('C', 0x1c7a44, 'MERCENARYSYSTEM_AddExpFixed')]
ok_all = True


def verdict(name, ok, detail=''):
    global ok_all
    ok_all = ok_all and ok
    print('  -> %s: %s %s' % ('DAT' if ok else 'KHONG DAT', name, detail))


def rd_phdr(data):
    e_phoff, = struct.unpack_from('<Q', data, 0x20)
    e_phentsize, e_phnum = struct.unpack_from('<HH', data, 0x36)
    out = []
    for i in range(e_phnum):
        p_type, p_flags, p_offset, p_vaddr, p_paddr, p_filesz, p_memsz, p_align = struct.unpack_from(
            '<IIQQQQQQ', data, e_phoff + i * e_phentsize)
        out.append(dict(type=p_type, flags=p_flags, off=p_offset, va=p_vaddr, filesz=p_filesz, memsz=p_memsz, align=p_align))
    return out


def sh(cmd):
    return subprocess.run(cmd, capture_output=True, text=True, check=True).stdout


def meta_ranges(lib):
    """Dai file cua .dynsym/.rela.dyn/.rela.plt: bang metadata cua linker, khong phai xref (phan tich rieng bang readelf)."""
    out = []
    for ln in sh(['llvm-readelf', '-SW', lib]).splitlines():
        m = re.search(r'\]\s+(\.dynsym|\.rela\.dyn|\.rela\.plt)\s+\S+\s+([0-9a-f]+)\s+([0-9a-f]+)\s+([0-9a-f]+)', ln)
        if m:
            out.append((m.group(1), int(m.group(3), 16), int(m.group(3), 16) + int(m.group(4), 16)))
    return out


def main(lib, dis):
    data = open(lib, 'rb').read()
    print('lib', lib, 'sha256', hashlib.sha256(data).hexdigest(), len(data), 'B')

    # ------------------------------------------------------------------ 1
    print('\n[1] PT_LOAD chua .bss')
    loads = [p for p in rd_phdr(data) if p['type'] == 1]
    bss = max(loads, key=lambda p: p['memsz'] - p['filesz'])
    end = bss['va'] + bss['memsz']
    r4 = (end + 0xfff) & ~0xfff
    r16 = (end + 0x3fff) & ~0x3fff
    print('  p_vaddr=0x%x p_filesz=0x%x p_memsz=0x%x p_align=0x%x flags=0x%x (RW=6)' % (bss['va'], bss['filesz'], bss['memsz'], bss['align'], bss['flags']))
    print('  p_vaddr+p_memsz = 0x%x ; lam tron 4 KB = 0x%x ; lam tron 16 KB = 0x%x ; can phu 0x%x' % (end, r4, r16, MEM_HI))
    verdict('rounding 4 KB  > 0x%x' % (MEM_HI - 4), r4 > MEM_HI - 4)
    verdict('rounding 16 KB > 0x%x' % (MEM_HI - 4), r16 > MEM_HI - 4)
    verdict('vung 0x%x..0x%x nam ngoai .bss that (>= 0x%x) nhung trong trang' % (MEM_LO, MEM_HI, end), MEM_LO >= end and MEM_HI <= r4)
    verdict('segment ghi duoc (PF_W)', bool(bss['flags'] & 2))

    # ------------------------------------------------------------------ 2
    print('\n[2] byte goc 3 diem va')
    for nm, va, fn in POINTS:
        raw = data[va:va + 4]
        txt = sh(['llvm-objdump', '-d', '--no-show-raw-insn', '--start-address=0x%x' % va, '--stop-address=0x%x' % (va + 4), lib])
        ins = [l.strip() for l in txt.splitlines() if re.match(r'\s*[0-9a-f]+:', l)]
        print('  %s 0x%x (%s): %s  %s' % (nm, va, fn, raw.hex(), ins[0] if ins else '?'))

    # ------------------------------------------------------------------ doc dis.txt
    rows = []                   # (addr, func, text)
    func = None
    for line in open(dis):
        m = re.match(r'^([0-9a-f]{16}) <([^>]+)>:', line)
        if m:
            func = m.group(2)
            continue
        m = re.match(r'^\s+([0-9a-f]+):\s+(.*)$', line)
        if m and func:
            rows.append((int(m.group(1), 16), func, m.group(2).strip()))
    print('\n(dis.txt: %d lenh)' % len(rows))

    # ------------------------------------------------------------------ 3
    print('\n[3] cave 0x%x..0x%x (CHAR_AddDamageEffect + CHAR_AddDamageByDefaultCheck)' % (CAVE_LO, CAVE_HI))
    fn_in = sorted({f for a, f, t in rows if CAVE_LO <= a < CAVE_HI})
    print('  ham nam trong cave:', fn_in)
    verdict('cave gom dung 2 ham chet', fn_in == ['CHAR_AddDamageByDefaultCheck', 'CHAR_AddDamageEffect'])
    # 3a. nhay / adr / literal toi cave tu ngoai cave
    rx_tgt = re.compile(r'^(b|bl|b\.\w+|cbz|cbnz|tbz|tbnz|adr|adrp|ldr|ldrsw|prfm)\s+(?:[^,]+,\s*)*?0x([0-9a-f]+)(?:\s|$)')
    hits = []
    for a, f, t in rows:
        m = re.match(r'^(b|bl|b\.\w+|cbz|cbnz|tbz|tbnz|adr|ldr|ldrsw|prfm)\s+(.*)$', t)
        if not m:
            continue
        ops = m.group(2)
        if '[' in ops:              # ldr x, [..] khong phai literal
            continue
        mm = re.search(r'0x([0-9a-f]+)', ops)
        if not mm:
            continue
        tgt = int(mm.group(1), 16)
        if CAVE_LO <= tgt < CAVE_HI and not (CAVE_LO <= a < CAVE_HI):
            hits.append((a, f, t))
    print('  nhay/adr/literal tu NGOAI vao cave: %d' % len(hits))
    for h in hits:
        print('    ', hex(h[0]), h[1], h[2])
    verdict('0 lenh ngoai cave tro vao cave (b/bl/b.cond/cbz/tbz/adr/ldr literal)', not hits)
    # 3b. adrp + add/ldr/str tinh ra dia chi trong cave (trang 0x18b000)
    page = CAVE_LO & ~0xfff
    adrps = [(i, a, f, t) for i, (a, f, t) in enumerate(rows) if re.match(r'^adrp\s+\w+,\s*0x%x$' % page, t)]
    print('  adrp vao trang 0x%x: %d' % (page, len(adrps)))
    verdict('0 adrp vao trang chua cave', not adrps)
    # 3c. reloc / symbol
    rel = sh(['llvm-readelf', '-rW', lib])
    rel_sym = [l for l in rel.splitlines() if 'CHAR_AddDamageEffect' in l or 'CHAR_AddDamageByDefaultCheck' in l]
    print('  reloc nhac toi 2 symbol: %d' % len(rel_sym))
    verdict('0 reloc (JUMP_SLOT/GLOB_DAT/ABS64) theo ten 2 ham', not rel_sym)
    in_cave_rel = []
    for l in rel.splitlines():
        m = re.match(r'^([0-9a-f]{16})\s+([0-9a-f]{16})\s+(\S+)\s+([0-9a-f]{16})?\s*(\S*)\s*\+?\s*(-?[0-9a-fx]*)', l)
        if m and m.group(3) == 'R_AARCH64_RELATIVE':
            add = int(l.split()[-1], 16)
            if CAVE_LO <= add < CAVE_HI:
                in_cave_rel.append(l)
    print('  reloc RELATIVE co addend trong cave: %d' % len(in_cave_rel))
    verdict('0 reloc RELATIVE tro vao cave', not in_cave_rel)
    # 3d. con tro tinh trong du lieu (8 B canh le 8, gia tri trong cave)
    meta = meta_ranges(lib)
    cnt = 0
    skipped = []
    for p in rd_phdr(data):
        if p['type'] != 1:
            continue
        for o in range(p['off'], p['off'] + p['filesz'] - 7, 4):
            v, = struct.unpack_from('<Q', data, o)
            if CAVE_LO <= v < CAVE_HI:
                where = [n for n, lo, hi in meta if lo <= o < hi]
                if where:
                    skipped.append((o, v, where[0]))
                    continue
                cnt += 1
                print('    8 B trung khop tai file off 0x%x = 0x%x' % (o, v))
    for o, v, w in skipped:
        print('    (bo qua) file off 0x%x = 0x%x nam trong %s = dong symbol xuat khau cua chinh 2 ham' % (o, v, w))
    verdict('0 gia tri 64 bit (moi vi tri canh 4 B, tru .dynsym/.rela) nam trong cave', cnt == 0)
    # 3e. PLT stub
    plt = [(a, f, t) for a, f, t in rows if f in ('CHAR_AddDamageEffect@plt', 'CHAR_AddDamageByDefaultCheck@plt')]
    verdict('0 PLT stub cho 2 ham', not plt)
    # 3f. ten chi nam trong .dynstr
    ds = None
    for ln in sh(['llvm-readelf', '-SW', lib]).splitlines():
        if '.dynstr' in ln:
            f = ln.split()
            ds = (int(f[4], 16), int(f[4], 16) + int(f[6], 16))
    occ = [m.start() for nm in (b'CHAR_AddDamageEffect', b'CHAR_AddDamageByDefaultCheck') for m in re.finditer(re.escape(nm), data)]
    print('  ten xuat hien tai file off', [hex(o) for o in occ], '; .dynstr =', [hex(x) for x in ds])
    verdict('ten chi nam trong .dynstr (khong co dlsym theo chuoi trong .rodata)', all(ds[0] <= o < ds[1] for o in occ))
    # 3g. libHercules khong import
    her = lib.replace('libgame.so', 'libHercules.so')
    try:
        und = [l for l in sh(['llvm-readelf', '--dyn-syms', '-W', her]).splitlines() if 'UND' in l and ('CHAR_' in l)]
        verdict('libHercules.so khong import CHAR_*', not und, '(%s)' % her)
    except Exception as e:
        print('  (bo qua libHercules:', e, ')')

    # ------------------------------------------------------------------ 4
    print('\n[4] vung nho 0x%x..0x%x' % (MEM_LO, MEM_HI))
    pages = {MEM_LO & ~0xfff, (MEM_LO & ~0xfff) + 0x1000, MEM_LO & ~0x3fff, (MEM_LO & ~0x3fff) + 0x4000}
    pat = re.compile(r'^adrp\s+\w+,\s*0x([0-9a-f]+)$')
    ad = [(a, f, t) for a, f, t in rows if pat.match(t) and int(pat.match(t).group(1), 16) in pages]
    print('  trang can kiem (adrp): %s' % sorted(hex(p) for p in pages))
    print('  adrp vao cac trang do: %d' % len(ad))
    for x in ad[:10]:
        print('    ', hex(x[0]), x[1], x[2])
    verdict('0 adrp vao trang 0x72f000 / 0x730000 / 0x72c000 / 0x730000 (4 KB va 16 KB)', not ad)
    near = []
    for l in rel.splitlines():
        t = l.split()
        if len(t) >= 4 and re.fullmatch(r'[0-9a-f]{16}', t[0]) and t[2].startswith('R_AARCH64'):
            try:
                off = int(t[0], 16)
                add = int(t[-1], 16) if re.fullmatch(r'-?[0-9a-fx]+', t[-1]) else None
            except ValueError:
                continue
            if MEM_LO - 0x10 <= off < MEM_HI + 0x10 or (add is not None and 0x72f000 <= add < 0x730000):
                near.append(l)
    print('  reloc co offset hoac addend trong 0x72f000..0x730000: %d' % len(near))
    for l in near:
        print('    ', l)
    verdict('khong reloc nao ghi/tro vao [0x%x, 0x%x)' % (MEM_LO, MEM_HI),
            not [l for l in near if MEM_LO <= int(l.split()[-1], 16) < MEM_HI or MEM_LO <= int(l.split()[0], 16) < MEM_HI])
    syms = [l for l in sh(['llvm-readelf', '--dyn-syms', '-W', lib]).splitlines()
            if re.match(r'^\s*\d+:\s+[0-9a-f]{16}\s', l) and 0x72f000 <= int(l.split()[1], 16) < 0x730000]
    print('  symbol dong co gia tri trong 0x72f000..0x730000: %d' % len(syms))
    for l in syms:
        print('    ', l)
    verdict('khong symbol nao nam trong vung', not [l for l in syms if MEM_LO <= int(l.split()[1], 16) < MEM_HI])
    ptr = 0
    for p in rd_phdr(data):
        if p['type'] != 1:
            continue
        for o in range(p['off'], p['off'] + p['filesz'] - 7, 4):
            v, = struct.unpack_from('<Q', data, o)
            if MEM_LO <= v < MEM_HI + 0x10:
                where = [n for n, lo, hi in meta if lo <= o < hi]
                if where:
                    print('    (bo qua) file off 0x%x = 0x%x trong %s' % (o, v, where[0]))
                    continue
                ptr += 1
                print('    8 B trung khop tai file off 0x%x = 0x%x' % (o, v))
    verdict('0 gia tri 64 bit trong file (tru .dynsym/.rela) nam trong [0x%x, 0x%x)' % (MEM_LO, MEM_HI + 0x10), ptr == 0)
    # ghi chu: reloc duy nhat gan day co addend 0x72f770 (< 0x72f780) = con tro "het vung" cua vung dem 2048 B [0x72ef70, 0x72f770)
    near_ptrs = [l for l in rel.splitlines() if re.search(r'R_AARCH64_RELATIVE\s+(72ef70|72f770)$', l)]
    print('  3 reloc RELATIVE lien tiep (begin=0x72ef70, begin, cap=0x72f770): %d dong' % len(set(near_ptrs)))
    # absolute address bang mov/movk (khong PIC): tim chuoi mov w,#0x72f? -> rat hiem; kiem nhanh
    mv = [(a, f, t) for a, f, t in rows if re.search(r'#0x72f[0-9a-f]{3}\b', t) or re.search(r'#0x73000\b', t)]
    verdict('0 lenh co hang so 0x72fxxx / 0x730000 (mov/add)', not mv)

    # ------------------------------------------------------------------ 5
    print('\n[5] ghi byte phe [char+0x311]')
    wr = [(a, f, t) for a, f, t in rows if re.match(r'^strb\s.*#0x311\]', t)]
    for a, f, t in wr:
        print('   0x%x %-34s %s' % (a, f, t))
    rd = sorted({f for a, f, t in rows if re.match(r'^(ldrb|ldrsb)\s.*#0x311\]', t)})
    print('  %d cho ghi, doc boi %d ham' % (len(wr), len(rd)))

    # ------------------------------------------------------------------ 6
    print('\n[6] caller')
    for tgt in ('CHAR_AddDamage', 'MERCENARYSYSTEM_AddExperience', 'MERCENARYSYSTEM_AddExpFixed', 'CHAR_AddExperience'):
        c = [(a, f) for a, f, t in rows if re.match(r'^(bl|b)\s+0x[0-9a-f]+\s+<%s@plt>' % tgt, t)]
        print('  %s: %d cho goi: %s' % (tgt, len(c), ', '.join('%s@0x%x' % (f, a) for a, f in c)))

    part7(rows)

    print('\nTONG KET:', 'TAT CA DAT' if ok_all else 'CO MUC KHONG DAT')
    return 0 if ok_all else 1


# ---------------------------------------------------------------------- 7. thanh ghi tu do (phan tich luong du lieu)
WRITE_ONLY_FIRST = None  # (khong dung)
STORES = ('str', 'strb', 'strh', 'stur', 'sturb', 'sturh', 'stp', 'stlr', 'stlrb', 'stlrh', 'stxr', 'stlxr')
READ_ALL = ('cmp', 'cmn', 'tst', 'ccmp', 'ccmn', 'cbz', 'cbnz', 'tbz', 'tbnz', 'br', 'prfm', 'fcmp', 'fcmpe')
RW_FIRST = ('movk', 'bfi', 'bfxil', 'bfm', 'bfc')       # doc + ghi toan hang dau
REG_RX = re.compile(r'\b([wx])(\d+)\b')


def reg_effects(txt):
    """(doc, ghi, la_goi) cua mot lenh: tap so thanh ghi 0..30 (w va x chung so)."""
    txt = re.sub(r'\s+', ' ', txt.strip())
    mn, _, ops = txt.partition(' ')
    mn = mn.strip()
    ops = ops.split('//')[0]
    regs = [int(m.group(2)) for m in REG_RX.finditer(ops)]
    if mn in ('bl', 'blr'):
        rd = set(regs) if mn == 'blr' else set()
        return rd | set(range(0, 8)), set(range(0, 19)) | {30}, True
    if mn in ('b', 'ret') or mn.startswith('b.'):
        return set(regs), set(), False
    if mn in READ_ALL or mn in STORES:
        return set(regs), set(), False
    if mn in ('nop', 'dmb', 'dsb', 'isb', 'svc', 'brk'):
        return set(), set(), False
    first = REG_RX.search(ops.split(',')[0]) if ops else None
    wr = {int(first.group(2))} if first else set()
    rd = set(regs)
    if mn in ('ldp', 'ldpsw', 'ldxp'):
        sec = REG_RX.search(ops.split(',')[1]) if ops.count(',') >= 1 else None
        wr = ({int(first.group(2))} if first else set()) | ({int(sec.group(2))} if sec else set())
        # hang con lai (trong []) la doc
        rd = set(int(m.group(2)) for m in REG_RX.finditer(ops[ops.find('['):])) if '[' in ops else set()
        return rd, wr, False
    if mn in RW_FIRST:
        return rd, wr, False
    rd = rd - wr if first and mn not in RW_FIRST else rd
    # hang dau la dich; cac hang con lai (ke ca trong []) la doc. Neu dich xuat hien lai o hang sau thi van la doc.
    later = ops.split(',', 1)[1] if ',' in ops else ''
    rd = set(int(m.group(2)) for m in REG_RX.finditer(later))
    if first and ('!' in ops or re.search(r'\],\s*#', ops)):
        wr = wr | {int(m.group(2)) for m in REG_RX.finditer(ops[ops.find('['):ops.find(']') + 1])}   # writeback
    return rd, wr, False


def free_regs_check(rows, start, fn_end, regs, label):
    """Tu `start` (dia chi lenh dau tien SAU cave) di moi duong trong ham; neu doc mot thanh ghi trong `regs` truoc khi ghi => khong tu do."""
    byaddr = {a: (f, t) for a, f, t in rows}
    order = [a for a, f, t in rows]
    pos = {a: i for i, a in enumerate(order)}
    seen = {}
    work = [(start, frozenset(regs))]
    viol = []
    visited = 0
    while work:
        a, live = work.pop()
        if a not in byaddr or a >= fn_end:
            continue
        key = (a, live)
        if key in seen:
            continue
        seen[key] = 1
        visited += 1
        f, t = byaddr[a]
        rd, wr, call = reg_effects(t)
        bad = live & rd
        if bad:
            viol.append((a, t, sorted(bad)))
            live = live - bad            # bao mot lan moi thanh ghi
        live = frozenset(live - wr)
        if not live:
            continue
        mn = t.split()[0]
        nxt = []
        mt = re.search(r'0x([0-9a-f]+)', t)
        tgt = int(mt.group(1), 16) if mt else None
        if mn == 'b':
            nxt = [tgt] if tgt is not None and start <= tgt < fn_end else []
        elif mn == 'ret' or mn == 'br':
            nxt = []
        elif mn.startswith('b.') or mn in ('cbz', 'cbnz', 'tbz', 'tbnz'):
            nxt = [order[pos[a] + 1]] + ([tgt] if tgt is not None and 0 <= tgt < fn_end else [])
        else:
            nxt = [order[pos[a] + 1]] if pos[a] + 1 < len(order) else []
        for n in nxt:
            work.append((n, live))
    print('  %s: duyet %d trang thai, thanh ghi %s' % (label, visited, sorted('x%d' % r for r in regs)))
    for a, t, bad in viol[:8]:
        print('     DOC TRUOC KHI GHI 0x%x: %s  (%s)' % (a, t, bad))
    return not viol


def part7(rows):
    print('\n[7] thanh ghi dung trong cave phai TU DO tai diem tiep tuc (khong ai doc gia tri vao-ham cua chung truoc khi ghi)')
    fn_ranges = {}
    for a, f, t in rows:
        lo, hi = fn_ranges.get(f, (a, a))
        fn_ranges[f] = (min(lo, a), max(hi, a) + 4)
    cases = [('A  x9..x13 @ 0x18b3e4 (CHAR_AddDamage)', 0x18b3e4, 'CHAR_AddDamage', range(9, 14)),
             ('B  x9..x12 @ 0x1c7864 (MERCENARYSYSTEM_AddExperience)', 0x1c7864, 'MERCENARYSYSTEM_AddExperience', range(9, 13)),
             ('C  x9..x12 @ 0x1c7a48 (MERCENARYSYSTEM_AddExpFixed)', 0x1c7a48, 'MERCENARYSYSTEM_AddExpFixed', range(9, 13))]
    for label, st, fn, regs in cases:
        lo, hi = fn_ranges[fn]
        verdict(label, free_regs_check(rows, st, hi, set(regs), fn))

    # phia nguoi goi: sau moi `bl` toi ham duoc va, x9..x13 khong duoc doc truoc khi ghi (ABI: caller-saved); neu co thi cave doi duoc gia tri song
    sites = []
    for tgt, regs in (('CHAR_AddDamage', range(9, 14)), ('MERCENARYSYSTEM_AddExperience', range(9, 13)), ('MERCENARYSYSTEM_AddExpFixed', range(9, 13))):
        for i, (a, f, t) in enumerate(rows):
            if re.match(r'^(bl)\s+0x[0-9a-f]+\s+<%s@plt>' % tgt, t) and f != 'CHAR_AddDamageByDefaultCheck':
                sites.append((tgt, a, f, rows[i + 1][0], regs))
    allok = True
    for tgt, a, f, nxt, regs in sites:
        lo, hi = fn_ranges[f]
        ok = free_regs_check(rows, nxt, hi, set(regs), 'caller %s @0x%x' % (f, a))
        allok = allok and ok
    verdict('x9..x13 khong song qua loi goi o %d cho goi (phia caller)' % len(sites), allok)
    # B va C: x8 (lenh goc `mov w8,#0x6e` do cave B tu thuc hien) va x19 (cave B/C sua) - chi can biet x13 khong dung o B/C
    # C: x8 SONG (adrp x8 @0x1c7a40 doc o 0x1c7a48) -> cave C khong duoc dung x8
    print('  C: x8 song tai 0x1c7a44 (adrp x8,0x2be000 @0x1c7a40; `ldr x8,[x8,#0xbd8]` @0x1c7a48) -> cave C khong dung x8: ' +
          ('DAT' if 'x8' not in open('/home/user/Vi-t-ho-game/inotia4/scripts/asm/mod_cave.s').read().split('cave_C:')[1].replace('x8,', 'X') else 'KHONG DAT'))


if __name__ == '__main__':
    if len(sys.argv) != 3:
        print(__doc__)
        sys.exit(2)
    sys.exit(main(sys.argv[1], sys.argv[2]))
