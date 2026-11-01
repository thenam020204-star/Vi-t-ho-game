#!/usr/bin/env python3
"""Áp các patch byte trong so_patches.tsv vào libHeroesLore5.so (kiểm tra byte gốc trước).
Dùng: apply_so.py IN.so OUT.so [patches.tsv ...]"""
import sys
from elftools.elf.elffile import ELFFile

def v2off(elf, va):
    for s in elf.iter_segments():
        if s['p_type'] == 'PT_LOAD' and s['p_vaddr'] <= va < s['p_vaddr'] + s['p_filesz']:
            return va - s['p_vaddr'] + s['p_offset']
    raise ValueError('vaddr %#x ngoài segment' % va)

src, dst = sys.argv[1], sys.argv[2]
tsvs = sys.argv[3:] or [__file__.rsplit('/', 1)[0] + '/so_patches.tsv']
data = bytearray(open(src, 'rb').read())
elf = ELFFile(open(src, 'rb'))
n = 0
for t in tsvs:
    for line in open(t, encoding='utf-8'):
        if not line.strip() or line.startswith('#'): continue
        va, old, new, why = line.rstrip('\n').split('\t')[:4]
        va = int(va, 16); old = bytes.fromhex(old); new = bytes.fromhex(new)
        assert len(old) == len(new), line
        o = v2off(elf, va)
        cur = bytes(data[o:o + len(old)])
        if cur == new:
            print('đã áp %#x' % va); continue
        if cur != old:
            sys.exit('SAI byte gốc tại %#x: có %s, cần %s (%s)' % (va, cur.hex(), old.hex(), why))
        data[o:o + len(new)] = new; n += 1
open(dst, 'wb').write(data)
print('áp %d patch -> %s' % (n, dst))
