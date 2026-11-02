#!/usr/bin/env python3
"""Áp bridge_patches.tsv vào libhl5_arm64_bridge.so (arm64), kiểm byte gốc trước.
Dùng: apply_bridge.py IN.so OUT.so [bridge_patches.tsv]   (vaddr -> file offset theo PT_LOAD)"""
import sys, os
from elftools.elf.elffile import ELFFile
src, dst = sys.argv[1], sys.argv[2]
tsv = sys.argv[3] if len(sys.argv) > 3 else os.path.join(os.path.dirname(os.path.abspath(__file__)), 'bridge_patches.tsv')
data = bytearray(open(src, 'rb').read()); elf = ELFFile(open(src, 'rb'))
def v2off(va):
    for s in elf.iter_segments():
        if s['p_type'] == 'PT_LOAD' and s['p_vaddr'] <= va < s['p_vaddr'] + s['p_filesz']:
            return va - s['p_vaddr'] + s['p_offset']
    raise ValueError('vaddr %#x ngoài segment' % va)
n = 0
for line in open(tsv, encoding='utf-8'):
    if not line.strip() or line.startswith('#'): continue
    va, old, new, why = line.rstrip('\n').split('\t')[:4]
    va = int(va, 16); old = bytes.fromhex(old); new = bytes.fromhex(new)
    assert len(old) == len(new)
    o = v2off(va); cur = bytes(data[o:o + len(old)])
    if cur == new: print('đã áp %#x' % va); continue
    if cur != old: sys.exit('SAI byte gốc tại %#x: có %s, cần %s' % (va, cur.hex(), old.hex()))
    data[o:o + len(new)] = new; n += 1
open(dst, 'wb').write(data); print('áp %d patch -> %s' % (n, dst))
