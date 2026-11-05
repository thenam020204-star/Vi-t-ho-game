# HL5 (LGT binary.mod) image helper: map sections, read, find strings/xrefs, disasm
import struct, sys, capstone
ELF = '/home/claude/work/hl5/jar/binary.mod'
d = open(ELF, 'rb').read()
SECS = []
shoff = struct.unpack_from('<I', d, 0x20)[0]
shnum = struct.unpack_from('<H', d, 0x30)[0]
for i in range(shnum):
    sh = shoff + i * 40
    name, typ, flags, addr, off, size = struct.unpack_from('<IIIIII', d, sh)
    if addr and size:
        SECS.append((addr, size, off, typ))
def off(a):
    for addr, size, o, typ in SECS:
        if addr <= a < addr + size:
            return None if typ == 8 else o + a - addr
    return None
def rd(a, n):
    o = off(a); return d[o:o + n]
def u32(a): return struct.unpack('<I', rd(a, 4))[0]
def u16(a): return struct.unpack('<H', rd(a, 2))[0]
def cstr(a):
    o = off(a); e = d.index(b'\0', o); return d[o:e]
TEXT = SECS[0]
def text_bytes():
    a, s, o, t = TEXT; return a, d[o:o + s]
def find_bytes(b):
    res = []
    for addr, size, o, typ in SECS:
        if typ == 8: continue
        blob = d[o:o + size]; i = blob.find(b)
        while i >= 0:
            res.append(addr + i); i = blob.find(b, i + 1)
    return res
def xrefs(val):
    """word-aligned literals equal to val"""
    res = []
    pk = struct.pack('<I', val)
    for addr, size, o, typ in SECS:
        if typ == 8: continue
        blob = d[o:o + size]; i = blob.find(pk)
        while i >= 0:
            if i % 4 == 0: res.append(addr + i)
            i = blob.find(pk, i + 1)
    return res
mdT = capstone.Cs(capstone.CS_ARCH_ARM, capstone.CS_MODE_THUMB)
mdA = capstone.Cs(capstone.CS_ARCH_ARM, capstone.CS_MODE_ARM)
def dis(a, e, thumb=True, prof=None):
    md = mdT if thumb else mdA
    out = []
    pc = a
    while pc < e:
        got = False
        for i in md.disasm(rd(pc, e - pc), pc):
            got = True
            extra = ''
            if i.mnemonic.startswith('ldr') and 'pc' in i.op_str and '[' in i.op_str:
                try:
                    imm = int(i.op_str.split('#')[-1].rstrip(']'), 0)
                    la = ((i.address + 4) & ~3) + imm
                    v = u32(la)
                    extra = ' ; =0x%x' % v
                    try:
                        s = cstr(v)
                        if 2 <= len(s) < 80 and all(32 <= c < 127 or c >= 0xa1 for c in s):
                            extra += ' "%s"' % s.decode('euc-kr', 'replace')
                    except Exception: pass
                except Exception: pass
            out.append('%x  %-8s %s%s' % (i.address, i.mnemonic, i.op_str, extra))
            pc = i.address + i.size
        if pc < e and not got or (pc < e and not any(True for _ in [0])):
            pass
        if pc < e:
            # undecodable halfword/word
            if thumb:
                out.append('%x  .short   0x%04x' % (pc, u16(pc))); pc += 2
            else:
                out.append('%x  .word    0x%08x' % (pc, u32(pc))); pc += 4
    return '\n'.join(out)
def funcstart(a):
    """scan back for thumb push {.., lr}"""
    x = a & ~1
    while x > 0x1000:
        h = u16(x)
        if (h & 0xFF00) == 0xB500: return x
        x -= 2
    return None
if __name__ == '__main__':
    cmd = sys.argv[1]
    if cmd == 'dis':
        print(dis(int(sys.argv[2], 16), int(sys.argv[3], 16), len(sys.argv) < 5 or sys.argv[4] != 'arm'))
    elif cmd == 'str':
        for a in find_bytes(sys.argv[2].encode('euc-kr')):
            print(hex(a), [hex(x) for x in xrefs(a)])
    elif cmd == 'xref':
        v = int(sys.argv[2], 16)
        for x in xrefs(v): print(hex(x))
def imports():
    """import thunks: E: str lr,[sp,#-4]! ; bl resolver ; .word table ; .word fn"""
    res = {}
    for addr, size, o, typ in SECS:
        if typ == 8: continue
        for k in range(0, size - 16, 4):
            w = struct.unpack_from('<I', d, o + k)[0]
            if w == 0xe52de004 and (struct.unpack_from('<I', d, o + k + 4)[0] >> 24) == 0xeb:
                t, f = struct.unpack_from('<II', d, o + k + 8)
                res[addr + k] = (t, f)
    return res
def thunk_of(table, fn):
    return [a for a, v in imports().items() if v == (table, fn)]
def users(table, fn):
    res = []
    for t in thunk_of(table, fn):
        res += [(t, x) for x in xrefs(t)]
    return res
def literal_users(lit):
    """Thumb 'ldr rd, [pc, #imm]' instructions that load the literal word at address lit"""
    a0, blob = text_bytes()
    res = []
    lo = max(0, lit - 1024 - a0)
    for o in range(lo & ~1, min(len(blob) - 2, lit - a0 + 2), 2):
        h = struct.unpack_from('<H', blob, o)[0]
        if (h & 0xF800) == 0x4800:
            a = a0 + o
            if ((a + 4) & ~3) + (h & 0xFF) * 4 == lit:
                res.append(a)
    return res
