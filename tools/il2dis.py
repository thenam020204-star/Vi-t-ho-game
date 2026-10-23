#!/usr/bin/env python3
"""Disassemble arm64 trong libil2cpp.so tai RVA/ten method, chu thich bl/chuoi/metadata bang script.json.
Dung: python3 -I tools/il2dis.py <libil2cpp.so> <script.json> <RVA hex | ten-con-chuoi> [--max N]
"""
import sys, json, struct, re, bisect
from capstone import Cs, CS_ARCH_ARM64, CS_MODE_ARM

def load(so, sj):
    data = open(so, 'rb').read()
    phoff = struct.unpack_from('<Q', data, 0x20)[0]
    phentsize, phnum = struct.unpack_from('<HH', data, 0x36)
    segs = []
    for i in range(phnum):
        t, fl, off, va, pa, fsz, msz, al = struct.unpack_from('<IIQQQQQQ', data, phoff + i * phentsize)
        if t == 1: segs.append((va, off, fsz))
    j = json.load(open(sj))
    # relocations RELATIVE: slot -> addend
    dyn = None
    for i in range(phnum):
        t, fl, off, va, pa, fsz, msz, al = struct.unpack_from('<IIQQQQQQ', data, phoff + i * phentsize)
        if t == 2: dyn = (off, fsz)
    rela = relasz = 0
    o, sz = dyn
    for k in range(0, sz, 16):
        tag, val = struct.unpack_from('<qQ', data, o + k)
        if tag == 7: rela = val
        if tag == 8: relasz = val
    rel = {}
    ro = va2off(segs, rela)
    for k in range(0, relasz, 24):
        off_, info, add = struct.unpack_from('<QQq', data, ro + k)
        if info & 0xffffffff == 1027: rel[off_] = add
    return data, segs, j, rel

def va2off(segs, va):
    for v, o, s in segs:
        if v <= va < v + s: return o + va - v
    raise ValueError(hex(va))

class Dis:
    def __init__(self, so, sj):
        self.data, self.segs, j, self.rel = load(so, sj)
        self.meth = {m['Address']: m['Name'] for m in j['ScriptMethod']}
        self.byname = {}
        for m in j['ScriptMethod']: self.byname.setdefault(m['Name'], []).append(m['Address'])
        self.addrs = sorted(set(j['Addresses']) | set(self.meth))
        self.slot = {}
        for s in j['ScriptString']: self.slot[s['Address']] = 'STR ' + json.dumps(s['Value'], ensure_ascii=False)[:120]
        for s in j['ScriptMetadata']: self.slot[s['Address']] = 'TYPE ' + s['Name']
        for s in j['ScriptMetadataMethod']: self.slot[s['Address']] = 'METH ' + s['Name']
        self.cs = Cs(CS_ARCH_ARM64, CS_MODE_ARM); self.cs.detail = False

    def size(self, a):
        i = bisect.bisect_right(self.addrs, a)
        return (self.addrs[i] - a) if i < len(self.addrs) else 0x400

    def run(self, a, maxn=4000):
        n = self.size(a)
        o = va2off(self.segs, a)
        code = self.data[o:o + n]
        regs = {}
        out = []
        for ins in self.cs.disasm(code, a):
            note = ''
            if ins.mnemonic == 'adrp':
                m = re.match(r'(\w+), #(0x[0-9a-f]+)', ins.op_str); regs[m.group(1)] = int(m.group(2), 16)
            elif ins.mnemonic in ('ldr', 'add') and '#0x' in ins.op_str:
                m = re.match(r'\w+, \[?(\w+)(?:, #(0x[0-9a-f]+))?\]?', ins.op_str)
                if m and m.group(1) in regs:
                    t = regs[m.group(1)] + (int(m.group(2), 16) if m.group(2) else 0)
                    tv = self.rel.get(t)
                    note = ('-> %#x ' % tv + self.slot.get(tv, '')) if tv is not None else self.slot.get(t, 'ADDR %#x' % t)
            elif ins.mnemonic in ('bl', 'b'):
                t = int(ins.op_str.lstrip('#'), 16)
                note = self.meth.get(t, '')
            if ins.mnemonic not in ('adrp','cmp','tst','cbz','cbnz','tbz','tbnz','str','strb','strh','stp','b','bl','ret','bl') and ins.op_str:
                regs.pop(ins.op_str.split(',')[0].strip(), None)
            out.append('%#x: %-6s %-34s %s' % (ins.address, ins.mnemonic, ins.op_str, ('; ' + note) if note else ''))
            if len(out) >= maxn: break
        return out

if __name__ == '__main__':
    so, sj, q = sys.argv[1:4]
    mx = int(sys.argv[5]) if len(sys.argv) > 5 and sys.argv[4] == '--max' else 4000
    d = Dis(so, sj)
    if re.fullmatch(r'(0x)?[0-9a-fA-F]+', q) and (q.startswith('0x') or len(q) >= 6):
        addrs = [int(q, 16)]
    else:
        addrs = [a for n, l in d.byname.items() if q in n for a in l]
    for a in addrs:
        print('== %#x %s (size %#x)' % (a, d.meth.get(a, ''), d.size(a)))
        print('\n'.join(d.run(a, mx)))
