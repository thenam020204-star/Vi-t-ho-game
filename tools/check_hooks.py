#!/usr/bin/env python3
"""Kiem tinh (truoc khi build libmsmod): 16 byte dau moi ham hook trong mods/native/msmod.c khop libil2cpp.so goc,
khong co nhanh nao nhay vao 20 byte dau ham, cac lenh PC-relative trong 16 byte dau (se do Dobby relocate) duoc liet ke.
Thoat != 0 neu lech. Dung: python3 -I tools/check_hooks.py [--so libil2cpp.so goc] [-v]"""
import os, re, struct, sys
from capstone import Cs, CS_ARCH_ARM64, CS_MODE_ARM
ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
sys.path.insert(0, os.path.join(ROOT, 'tools'))
import patch_so

so = os.path.join(ROOT, 'work/il2/lib/arm64-v8a/libil2cpp.so')
verbose = '-v' in sys.argv
data = open(so, 'rb').read()
segs = patch_so.load_segments(data)
hdr = open(os.path.join(ROOT, 'mods/native/offsets.h')).read()
src = open(os.path.join(ROOT, 'mods/native/msmod.c')).read()
rva = {m.group(1): int(m.group(2), 16) for m in re.finditer(r'#define\s+(RVA_\w+)\s+(0x[0-9A-Fa-f]+)u', hdr)}
pairs = {'w_damage': 'RVA_UNIT_DAMAGE', 'w_death': 'RVA_UNIT_DEATH', 'w_update': 'RVA_BATTLE_UPDATE',
         'w_start': 'RVA_BATTLE_START', 'w_mana': 'RVA_MANA_GAIN'}
cs = Cs(CS_ARCH_ARM64, CS_MODE_ARM)
bad = 0
for var, rn in pairs.items():
    m = re.search(r'%s\[4\]\s*=\s*\{([^}]*)\}' % var, src)
    want = [int(x, 16) for x in re.findall(r'0x[0-9a-fA-F]+', m.group(1))]
    r = rva[rn]
    off = patch_so.rva2off(segs, r, 4096)
    have = list(struct.unpack_from('<4I', data, off))
    ok = want == have
    bad += not ok
    ins = list(cs.disasm(data[off:off + 0x2000], r))
    # ket thuc ham = lenh dau ham ke tiep trong script khong co san => dung 0x2000 B de tim nhanh vao 20 byte dau
    inb = []
    for i in ins:
        if i.mnemonic in ('b', 'bl') or i.mnemonic.startswith('b.') or i.mnemonic in ('cbz', 'cbnz', 'tbz', 'tbnz'):
            try: t = int(i.op_str.split('#')[-1], 16)
            except ValueError: continue
            if r < t < r + 20 and i.address >= r: inb.append(hex(i.address))
    pcrel = [f'{i.mnemonic} {i.op_str}' for i in ins[:4] if i.mnemonic in ('adr', 'adrp', 'b', 'bl', 'ldr') and ('#0x' in i.op_str and i.mnemonic != 'ldr' or i.mnemonic == 'ldr' and '[' not in i.op_str)]
    print('%-18s %-10s byte dau %s; nhanh-vao-20B-dau: %s; PC-relative trong 16B: %s' % (rn, hex(r), 'KHOP' if ok else 'LECH', inb or 'khong', pcrel or 'khong'))
    bad += bool(inb)
sys.exit(1 if bad else 0)
