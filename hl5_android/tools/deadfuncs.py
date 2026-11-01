#!/usr/bin/env python3
"""Tìm hàm trong .so không có tham chiếu: không lệnh b/bl nào trỏ tới, không word nào
trong các section dữ liệu/literal bằng địa chỉ của nó, không reloc trỏ tới.
Dùng: deadfuncs.py SO DISASM [min_size]"""
import sys, re, struct
from elftools.elf.elffile import ELFFile
from elftools.elf.relocation import RelocationSection
so, dis = sys.argv[1], sys.argv[2]; mn = int(sys.argv[3]) if len(sys.argv) > 3 else 256
elf = ELFFile(open(so, 'rb')); data = open(so, 'rb').read()
funcs = {}
for s in elf.get_section_by_name('.symtab').iter_symbols():
    if s['st_info']['type'] == 'STT_FUNC' and s['st_value'] and s['st_size']:
        funcs.setdefault(s['st_value'] & ~1, (s['st_size'], s.name))
refs = set()
for l in open(dis):
    m = re.search(r'\s(b|bl|blx|b[a-z]{2})\s+0x([0-9a-f]+)', l)
    if m: refs.add(int(m.group(2), 16))
# mọi word 4 byte trong file (literal pool, vtable, data) 
words = set()
for sec in elf.iter_sections():
    if sec['sh_flags'] & 2 and sec['sh_type'] == 'SHT_PROGBITS':   # SHF_ALLOC
        d = sec.data(); words.update(struct.unpack_from('<%dI' % (len(d) // 4), d[:len(d) // 4 * 4]))
for sec in elf.iter_sections():
    if isinstance(sec, RelocationSection):
        for r in sec.iter_relocations():
            pass
dyn = {s.name for s in elf.get_section_by_name('.dynsym').iter_symbols()}
dead = []
for a, (sz, nm) in funcs.items():
    if a in refs or a in words or (a | 1) in words: continue
    if nm in dyn and (nm.startswith('Java_') or nm.startswith('JNI_')): continue
    if sz >= mn: dead.append((sz, a, nm))
dead.sort(reverse=True)
tot = 0
for sz, a, nm in dead:
    tot += sz; print('%06x %5d %s' % (a, sz, nm))
print('tổng', tot)
