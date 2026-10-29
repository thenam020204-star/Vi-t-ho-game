# Demo limits that can be lifted without new code (same-length edits, no new branch
# targets, StackMap frames untouched):
#   au.class  run(): play counter "au.a = au.a - 1" (2 sites)    -> subtract 0
#   ai.class  t():   exhibition gate "if (au.a > 0) ... else buy screen" (offset 956)
#                    getstatic au.a -> iconst_1; nop; nop  (always > 0)
#   ai.class  a()Z:  demo cursor gate (only x 1 <-> 2, never for the 2nd team) -> always true
#   ai.class  f(I):  padlock overlay skipped only for row 0 col 1/2 (offset 5024..5041)
#                    -> condition always "skip"
# Usage: patchdemo.py <au.class in> <au.class out> <ai.class in> <ai.class out>
import struct, sys

def u2(b, o): return struct.unpack('>H', b[o:o+2])[0]
def u4(b, o): return struct.unpack('>I', b[o:o+4])[0]

def code_of(d, name, desc):
    p = 10; n = u2(d, 8); cp = [None] * n; i = 1
    while i < n:
        t = d[p]
        if t == 1: l = u2(d, p+1); cp[i] = d[p+3:p+3+l].decode('utf8', 'replace'); p += 3 + l
        elif t in (3, 4): p += 5
        elif t in (5, 6): p += 9; i += 1
        elif t in (7, 8, 16): p += 3
        elif t in (9, 10, 11, 12, 18): p += 5
        elif t == 15: p += 4
        else: raise SystemExit('cp tag %d' % t)
        i += 1
    p += 6; p += 2 + 2 * u2(d, p); hits = []
    for _ in range(2):
        n = u2(d, p); p += 2
        for _ in range(n):
            ni, di, ac = u2(d, p+2), u2(d, p+4), u2(d, p+6); p += 8
            for _ in range(ac):
                an, al = u2(d, p), u4(d, p+2)
                if cp[an] == 'Code' and cp[ni] == name and cp[di] == desc: hits.append(p + 14)
                p += 6 + al
    assert len(hits) == 1, (name, desc, hits)
    return hits[0]

def edit(d, base, off, old, new):
    assert len(old) == len(new)
    got = bytes(d[base+off:base+off+len(old)])
    assert got == bytes(old), (off, got.hex(), bytes(old).hex())
    d[base+off:base+off+len(new)] = bytes(new)

au = bytearray(open(sys.argv[1], 'rb').read())
base = code_of(au, 'run', '()V')
for off in (1293, 1604):   # getstatic #482; iconst_1; isub; putstatic #482
    edit(au, base, off, [0xb2, 0x01, 0xe2, 0x04, 0x64, 0xb3, 0x01, 0xe2], [0xb2, 0x01, 0xe2, 0x03, 0x64, 0xb3, 0x01, 0xe2])
open(sys.argv[2], 'wb').write(au)

ai = bytearray(open(sys.argv[3], 'rb').read())
base = code_of(ai, 't', '()V')
edit(ai, base, 956, [0xb2, 0x01, 0xb8, 0x9d], [0x04, 0x00, 0x00, 0x9d])        # getstatic au.a:I; ifgt
base = code_of(ai, 'a', '()Z')
edit(ai, base, 6, [0x03, 0xac], [0x04, 0xac])
edit(ai, base, 41, [0x03, 0xac], [0x04, 0xac])
base = code_of(ai, 'f', '(I)V')
edit(ai, base, 5024, [0x1c, 0x9a], [0x03, 0x9a])                               # iload_2 -> iconst_0 ; ifne
edit(ai, base, 5028, [0x15, 0x04, 0x04, 0x9f], [0x04, 0x00, 0x04, 0x9f])       # iload 4 -> iconst_1; nop ; iconst_1; if_icmpeq
open(sys.argv[4], 'wb').write(ai)
print('ok')
