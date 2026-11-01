#!/usr/bin/env python3
"""Giải mã kiểu PIC gcc: r_base = PC(lệnh add rX,pc,rX)+8 + [lit_base]; ô GOT = r_base + [lit_off].
Dùng: gotsym.py SO add_pc_insn_addr lit_base_addr lit_off_addr [lit_off_addr ...]"""
import sys, struct
from elftools.elf.elffile import ELFFile
from elftools.elf.relocation import RelocationSection
so = sys.argv[1]; f = open(so, 'rb'); elf = ELFFile(f); data = open(so, 'rb').read()
def rd(va):
    for s in elf.iter_segments():
        if s['p_type'] == 'PT_LOAD' and s['p_vaddr'] <= va < s['p_vaddr'] + s['p_filesz']:
            return struct.unpack_from('<I', data, va - s['p_vaddr'] + s['p_offset'])[0]
dsym = elf.get_section_by_name('.dynsym'); syms = {}
for s in elf.get_section_by_name('.symtab').iter_symbols():
    if s['st_value']: syms.setdefault(s['st_value'], s.name)
rel = {}
for sec in elf.iter_sections():
    if isinstance(sec, RelocationSection):
        for r in sec.iter_relocations():
            if r['r_info_sym']:
                rel[r['r_offset']] = dsym.get_symbol(r['r_info_sym']).name
            else:
                rel[r['r_offset']] = 'REL->%s' % syms.get(rd(r['r_offset']), hex(rd(r['r_offset'])))
pc = int(sys.argv[2], 16); base = (pc + 8 + rd(int(sys.argv[3], 16))) & 0xffffffff
for a in sys.argv[4:]:
    slot = (base + rd(int(a, 16))) & 0xffffffff
    print(a, 'slot', hex(slot), rel.get(slot, '(không reloc) ' + str(syms.get(slot))))
