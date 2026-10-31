#!/usr/bin/env python3
"""In chuoi C tai cac dia chi ao trong ELF.  cstr.py <so> 0xaddr ..."""
import sys
from elftools.elf.elffile import ELFFile
f = open(sys.argv[1], 'rb'); e = ELFFile(f)
def rd(va, n=120):
    for s in e.iter_segments():
        if s['p_type'] == 'PT_LOAD' and s['p_vaddr'] <= va < s['p_vaddr'] + s['p_filesz']:
            f.seek(s['p_offset'] + va - s['p_vaddr']); return f.read(n)
for a in sys.argv[2:]:
    v = int(a, 16); print(hex(v), rd(v).split(b'\0')[0])
