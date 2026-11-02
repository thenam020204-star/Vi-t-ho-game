#!/usr/bin/env python3
"""Sinh các dòng so_patches.tsv + caves.tsv cho engine chữ Việt (build.sh phải chạy trước).

  inject.py SO_GOC [--tsv ../patch/so_patches.tsv] [--caves ../patch/caves.tsv] [--diff patch_diff.txt]

SO_GOC = .so TRƯỚC khi áp các patch vn (p1.so: đã có 4 patch offline). Mỗi dòng ghi byte gốc đọc từ SO_GOC.
Khối vn nằm giữa '# >>> vn-engine' và '# <<< vn-engine' trong so_patches.tsv; chạy lại thì thay khối (idempotent).
"""
import sys, os, struct, argparse
from capstone import Cs, CS_ARCH_ARM, CS_MODE_ARM

HERE = os.path.dirname(os.path.abspath(__file__))
CAVE = 0xaef38; CAVE_SZ = 2620
# (vaddr, byte gốc đã biết, thunk, mô tả)
SITES = [
    (0x40ee8, 'e1d904d6', 'vn_th0', 'GetWidth(Pciii): ldrsb r0,[r9,#70] (bề rộng mã 2 byte - 1)'),
    (0x40f6c, 'e1d634d6', 'vn_th1', 'GetByteLenPrintable: ldrsb r3,[r6,#70]'),
    (0x41510, 'e1d534d6', 'vn_th2', 'DrawText lõi (nhánh bit7, đo): ldrsb r3,[r5,#70]'),
    (0x4170c, 'e1d534d6', 'vn_th3', 'DrawText lõi (sau vẽ 2 byte, tiến x): ldrsb r3,[r5,#70]'),
    (0x4178c, 'e1d5a4d6', 'vn_th4', 'DrawText lõi (nhánh bit7 vòng chính): ldrsb r10,[r5,#70]'),
]
DRAW_SITE = (0x41804, 'ebffedbd')          # bl MX_fntDrawString (đường vẽ 2 byte)
MEMSZ = (0x88, '202d0100', '202e0100')     # p_memsz PT_LOAD #2 (file off 0x88 = vaddr 0x88 trong segment 0): 0x12d20 -> 0x12e20
START, END = '# >>> vn-engine', '# <<< vn-engine'


def v2off(d, va):
    po = struct.unpack_from('<I', d, 0x1c)[0]; n = struct.unpack_from('<H', d, 0x2c)[0]
    for i in range(n):
        t, off, vad, _, fsz = struct.unpack_from('<5I', d, po + 32 * i)
        if t == 1 and vad <= va < vad + fsz: return va - vad + off
    raise ValueError(hex(va))


def le(h): return bytes.fromhex(h)[::-1].hex()


def bl(src, dst, cond=0xe):
    imm = (dst - (src + 8)) // 4
    assert (dst - (src + 8)) % 4 == 0 and -(1 << 23) <= imm < (1 << 23)
    return struct.pack('<I', cond << 28 | 0x0b000000 | (imm & 0xffffff))


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('so')
    ap.add_argument('--tsv', default=os.path.join(HERE, '../patch/so_patches.tsv'))
    ap.add_argument('--caves', default=os.path.join(HERE, '../patch/caves.tsv'))
    ap.add_argument('--diff', default=os.path.join(HERE, 'patch_diff.txt'))
    ap.add_argument('--out', default=os.path.join(HERE, 'out'))
    a = ap.parse_args()
    d = open(a.so, 'rb').read()
    blob = open(os.path.join(a.out, 'vn.bin'), 'rb').read()
    sym = {}
    for l in open(os.path.join(a.out, 'vn.sym')):
        v, _, n = l.split(); sym[n] = int(v, 16)
    assert len(blob) <= CAVE_SZ, len(blob)
    cs = Cs(CS_ARCH_ARM, CS_MODE_ARM)

    def dis(va, b):
        return '; '.join('%s %s' % (i.mnemonic, i.op_str) for i in cs.disasm(b, va)) or '?'

    rows = []; diff = []
    # 1) cave
    orig_cave = d[v2off(d, CAVE):v2off(d, CAVE) + len(blob)]
    for o in range(0, len(blob), 128):
        c = blob[o:o + 128]; oc = orig_cave[o:o + 128]
        rows.append('%x\t%s\t%s\t%s' % (CAVE + o, oc.hex(), c.hex(), 'cave A (Map::MapMove_back, chết) +0x%x: code engine chữ Việt' % o))
    # 2) 5 thunk
    for va, old, th, why in SITES:
        o = v2off(d, va); cur = d[o:o + 4]
        old = le(old); assert cur.hex() == old, (hex(va), cur.hex())
        new = bl(va, sym[th])
        rows.append('%x\t%s\t%s\t%s' % (va, old, new.hex(), 'engine chữ Việt: ldrsb -> bl %s (%s)' % (th, why)))
        diff.append('%x  trước: %-26s sau: %s   [%s -> %#x]' % (va, dis(va, cur), dis(va, new), th, sym[th]))
    # 3) vẽ
    va, old = DRAW_SITE
    o = v2off(d, va); cur = d[o:o + 4]; old = le(old); assert cur.hex() == old
    new = bl(va, sym['vn_draw'])
    rows.append('%x\t%s\t%s\t%s' % (va, old, new.hex(), 'engine chữ Việt: bl MX_fntDrawString -> bl vn_draw (đường vẽ 2 byte của BFont::DrawText)'))
    diff.append('%x  trước: %-26s sau: %s   [vn_draw -> %#x]' % (va, dis(va, cur), dis(va, new), sym['vn_draw']))
    # 4) p_memsz
    va, old, new = MEMSZ
    assert d[va:va + 4].hex() == old
    rows.append('%x\t%s\t%s\t%s' % (va, old, new, 'p_memsz PT_LOAD#2 0x12d20 -> 0x12e20: bss thật dài thêm 0x100 B cho vn_state tại 0x1704c0 (hết bss cũ 0x1704a8)'))
    diff.append('%x  p_memsz: 0x12d20 -> 0x12e20 (bss tới 0x1705a8; vn_state 0x1704c0..0x170500)' % va)

    # ghi tsv (thay khối cũ)
    old = open(a.tsv, encoding='utf-8').read().split('\n')
    if START in old:
        i, j = old.index(START), old.index(END)
        old = old[:i] + old[j + 1:]
    while old and old[-1] == '': old.pop()
    old += [START, '# T6b: engine chữ Việt (engine/vn.c, build.sh, inject.py). Thiết kế: BAO-CAO-RE.md §9.'] + rows + [END, '']
    open(a.tsv, 'w', encoding='utf-8').write('\n'.join(old))

    # caves.tsv
    hdr = '# vùng\tbắt đầu\tkích thước\tdùng\tchủ\tghi chú\n'
    lines = []
    if os.path.exists(a.caves):
        lines = [l for l in open(a.caves, encoding='utf-8').read().split('\n') if l and not l.startswith('# vùng') and not l.startswith('cave A\t')]
    lines.append('cave A\t0x%x\t%d\tđã dùng %d B (0x%x..0x%x)\tvn.c\tMap::MapMove_back (chết, refscan 0 tham chiếu); còn trống %d B' % (CAVE, CAVE_SZ, len(blob), CAVE, CAVE + len(blob), CAVE_SZ - len(blob)))
    lines.append('bss\t0x1704c0\t64\tvn_state\tvn.c\tsau .bss cũ (0x1704a8); cần p_memsz +0x100 (patch 0x88)') if not any(l.startswith('bss\t') for l in lines) else None
    open(a.caves, 'w', encoding='utf-8').write(hdr + '\n'.join(lines) + '\n')

    # diff
    out = ['# Diff disasm các điểm vá engine chữ Việt (trước/sau)', ''] + diff + ['', '# Thunk (cave A):']
    for nm in ('vn_th0', 'vn_th1', 'vn_th2', 'vn_th3', 'vn_th4'):
        s = sym[nm] - CAVE
        out.append('%s @ %#x:' % (nm, sym[nm]))
        for i in cs.disasm(blob[s:s + 0x30], sym[nm]):
            out.append('   %x: %s %s' % (i.address, i.mnemonic, i.op_str))
    open(a.diff, 'w', encoding='utf-8').write('\n'.join(out) + '\n')
    print('đã ghi %d dòng patch vào %s' % (len(rows), a.tsv))

main()
