#!/usr/bin/env python3
"""Sinh các dòng so_patches.tsv + caves.tsv cho T10 (menu ÷k + HD). build.sh phải chạy trước.

  inject_t10.py SO_GOC [--tsv ../patch/so_patches.tsv] [--caves ../patch/caves.tsv] [--diff ../patch/t10_patch_diff.txt]

SO_GOC = .so gốc (bất kỳ bản nào chưa vá các vùng T10). Mỗi dòng kiểm câu lệnh gốc bằng capstone rồi ghi byte gốc đọc từ SO_GOC.
Khối T10 nằm giữa '# >>> T10 hd-menu' và '# <<< T10 hd-menu' trong so_patches.tsv (chạy lại thì thay khối).
"""
import sys, os, struct, argparse
from capstone import Cs, CS_ARCH_ARM, CS_MODE_ARM
from keystone import Ks, KS_ARCH_ARM, KS_MODE_ARM

HERE = os.path.dirname(os.path.abspath(__file__))
CAVE_B = 0x14afb4; CAVE_B_SZ = 2436
START, END = '# >>> T10 hd-menu', '# <<< T10 hd-menu'
cs = Cs(CS_ARCH_ARM, CS_MODE_ARM)
ks = Ks(KS_ARCH_ARM, KS_MODE_ARM)

# (vaddr, lệnh gốc mong đợi (capstone), thay: ('b'|'bl', symbol) | asm str, mô tả)
SITES = [
    # --- KeyOption (StateMainMenu::KeyOption 0x11eb3c): bảng nhảy phím (addls pc,pc,r7 lsl 2), mỗi mục là 1 lệnh b ---
    (0x11ebe8, 'b #0x11ed68', ('b', 't10_OK'), 'KeyOption bảng nhảy OK(-5): mục 9 đổi k, còn lại chạy mã gốc 0x11ed68'),
    (0x11ebec, 'b #0x11edb8', ('b', 't10_R'), 'KeyOption bảng nhảy PHẢI(-4): mục 9 k+1'),
    (0x11ebf0, 'b #0x11ee0c', ('b', 't10_L'), 'KeyOption bảng nhảy TRÁI(-3): mục 9 k-1'),
    # 10 mục thay 9: DOWN (0x11ee90 cmp 8<<24 -> 9<<24; 0x11ee9c quay vòng -9 -> -10), UP (0x11eee0 +9 -> +10)
    (0x11ee90, 'cmp r0, #0x8000000', 'cmp r0, #0x9000000', 'KeyOption XUỐNG: mục cuối 8 -> 9'),
    (0x11ee9c, 'sub r2, r2, #9', 'sub r2, r2, #10', 'KeyOption XUỐNG: quay vòng -10'),
    (0x11eee0, 'add r2, r2, #9', 'add r2, r2, #10', 'KeyOption LÊN: quay vòng +10'),
    # --- DrawOption (0x11a1ec) ---
    (0x11a3bc, 'mov lr, #9', 'mov lr, #10', 'DrawOption: tổng số mục cho DrawScroll 9 -> 10'),
    (0x11a4b4, 'ldrsh r1, [r9]', ('bl', 't10_lblu'), 'DrawOption (dòng thường): id nhãn mục 9 = 210'),
    (0x11a8c8, 'ldrsh r1, [r1, #-0x1c]', ('bl', 't10_lbls'), 'DrawOption (dòng chọn): id nhãn mục 9 = 210'),
    (0x11a540, 'ldrsb r3, [r3, #-0xc]', ('bl', 't10_type'), 'DrawOption: loại mục 9 = 5'),
    (0x11a54c, 'b #0x11a5d8', ('b', 't10_valth'), 'DrawOption: nhánh loại > 4 -> vẽ giá trị ÷k + ghi chú'),
    # --- TouchOption (0x1186e8): 10 mục ---
    (0x118994, 'cmp r12, #3', 'cmp r12, #4', 'TouchOption cuộn xuống: dòng đầu tối đa 3 -> 4'),
    (0x1189f4, 'mov r3, #8', 'mov r3, #9', 'TouchOption cuộn lên từ đầu: chọn mục cuối 8 -> 9'),
    # --- HD ---
    (0x9987c, 'stmib sp, {r7, r12}', ('bl', 't10_h1th'), 'onStartApp trước ctor BFont: logic cao >= HD_MIN_H -> hd=1, [69]=HD_LINE'),
    (0x414d8, 'bl #0x31af4', ('bl', 't10_gshth'), 'BFont::DrawText: ";" xuống dòng dùng [69] khi HD'),
]


def v2off(d, va):
    po = struct.unpack_from('<I', d, 0x1c)[0]; n = struct.unpack_from('<H', d, 0x2c)[0]
    for i in range(n):
        t, off, vad, _, fsz = struct.unpack_from('<5I', d, po + 32 * i)
        if t == 1 and vad <= va < vad + fsz: return va - vad + off
    raise ValueError(hex(va))


def br(src, dst, link):
    imm = (dst - (src + 8)) // 4
    assert (dst - (src + 8)) % 4 == 0 and -(1 << 23) <= imm < (1 << 23)
    return struct.pack('<I', 0xe0000000 | (0x0b000000 if link else 0x0a000000) | (imm & 0xffffff))


import re
def norm(t):
    t = t.replace('#0x', '#').replace('0x', '')
    return re.sub(r'\b(sb|sl|fp|ip)\b', lambda m: {'sb': 'r9', 'sl': 'r10', 'fp': 'r11', 'ip': 'r12'}[m.group(1)], t)


def dis(va, b):
    return '; '.join('%s %s' % (i.mnemonic, i.op_str) for i in cs.disasm(b, va)) or '?'


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('so')
    ap.add_argument('--tsv', default=os.path.join(HERE, '../patch/so_patches.tsv'))
    ap.add_argument('--caves', default=os.path.join(HERE, '../patch/caves.tsv'))
    ap.add_argument('--diff', default=os.path.join(HERE, '../patch/t10_patch_diff.txt'))
    ap.add_argument('--out', default=os.path.join(HERE, 'out'))
    a = ap.parse_args()
    d = open(a.so, 'rb').read()
    blob = open(os.path.join(a.out, 't10.bin'), 'rb').read()
    sym = {}
    for l in open(os.path.join(a.out, 't10.sym')):
        v, _, n = l.split(); sym[n] = int(v, 16)
    assert len(blob) <= CAVE_B_SZ, len(blob)
    rows = []; diff = []
    o = v2off(d, CAVE_B)
    orig = d[o:o + len(blob)]
    for k in range(0, len(blob), 128):
        rows.append('%x\t%s\t%s\t%s' % (CAVE_B + k, orig[k:k + 128].hex(), blob[k:k + 128].hex(),
                                        'cave B (AndroidService::AndroidService C2, chết) +0x%x: code T10 (menu ÷k, HD)' % k))
    for va, exp, new, why in SITES:
        o = v2off(d, va); cur = d[o:o + 4]
        got = dis(va, cur)
        assert norm(got) == norm(exp), (hex(va), got, exp)
        if isinstance(new, tuple):
            nb = br(va, sym[new[1]], new[0] == 'bl'); how = '%s %s (%#x)' % (new[0], new[1], sym[new[1]])
        else:
            enc, _ = ks.asm(new, va); nb = bytes(enc); how = new
        assert len(nb) == 4, (hex(va), new)
        rows.append('%x\t%s\t%s\t%s' % (va, cur.hex(), nb.hex(), 'T10: %s' % why))
        diff.append('%x  trước: %-30s sau: %-34s  [%s]' % (va, got, dis(va, nb), how))
    old = open(a.tsv, encoding='utf-8').read().split('\n')
    if START in old:
        i, j = old.index(START), old.index(END)
        old = old[:i] + old[j + 1:]
    while old and old[-1] == '': old.pop()
    old += [START, '# T10: menu Cài đặt "Độ phân giải ÷k" + chữ 18 px ở HD (engine/t10.c, build.sh, inject_t10.py). BAO-CAO-RE.md §11.'] + rows + [END, '']
    open(a.tsv, 'w', encoding='utf-8').write('\n'.join(old))
    lines = []
    if os.path.exists(a.caves):
        lines = [l for l in open(a.caves, encoding='utf-8').read().split('\n') if l and not l.startswith('cave B\t')]
    lines.append('cave B\t0x%x\t%d\tđã dùng %d B (0x%x..0x%x)\tt10.c\tAndroidService::AndroidService C2 (chết, refscan §9.3); còn trống %d B' % (
        CAVE_B, CAVE_B_SZ, len(blob), CAVE_B, CAVE_B + len(blob), CAVE_B_SZ - len(blob)))
    # giữ tiêu đề đầu
    hdr = [l for l in lines if l.startswith('# vùng')]
    body = [l for l in lines if not l.startswith('# vùng')]
    open(a.caves, 'w', encoding='utf-8').write('\n'.join((hdr or ['# vùng\tbắt đầu\tkích thước\tdùng\tchủ\tghi chú']) + body) + '\n')
    out = ['# T10: diff disasm các điểm vá (trước/sau); thunk trong cave B', ''] + diff + ['', '# Thunk (cave B):']
    for nm in ('t10_R', 't10_L', 't10_OK', 't10_lblu', 't10_lbls', 't10_type', 't10_valth', 't10_h1th', 't10_gshth'):
        s = sym[nm] - CAVE_B
        out.append('%s @ %#x:' % (nm, sym[nm]))
        n = 0
        for i in cs.disasm(blob[s:s + 0x40], sym[nm]):
            out.append('   %x: %s %s' % (i.address, i.mnemonic, i.op_str)); n += 1
            if i.mnemonic in ('bx', 'b') and n > 1 or (i.mnemonic == 'b' and i.op_str.startswith('#')): break
    open(a.diff, 'w', encoding='utf-8').write('\n'.join(out) + '\n')
    print('đã ghi %d dòng patch T10 vào %s' % (len(rows), a.tsv))

main()
