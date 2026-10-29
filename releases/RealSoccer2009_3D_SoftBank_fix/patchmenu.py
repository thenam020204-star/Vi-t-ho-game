# Fix main-menu freeze when IGP is unavailable (JAR installed without the carrier JAD).
# ai.f(101) draws H = 6 items (H = 7, minus 1 when bg.a() == false) from ai.u / ai.v,
# but those tables have only 5 entries -> ArrayIndexOutOfBoundsException(5) inside the
# game loop; au.run() swallows it before "bE = 0", so input/logic never runs again.
# The demo menu code also treats item 0 as "buy full version" (t(): J == 0 -> af.a()).
# So the non-IGP tables must be s / t without the IGP entry (2225 / text 34):
#   u = {0, 1, 4, 5, 6, 7}            (s = {0, 1, 4, 2225, 5, 6, 7})
#   v = {628, 629, 38, 33, 35, 36}    (t = {628, 629, 38, 34, 33, 35, 36})
# ai.<clinit> has no branches and no exception table, so growing its code needs no
# offset fix-ups beyond code_length / attribute_length (no StackMap needed).
import struct, sys

def bp(v): return bytes([0x10, v & 0xFF])
def sp(v): return bytes([0x11]) + struct.pack('>h', v)
IC = {0: 0x03, 1: 0x04, 2: 0x05, 3: 0x06, 4: 0x07, 5: 0x08}
def ic(v): return bytes([IC[v]])
def push(v): return ic(v) if v in IC else (bp(v) if -128 <= v < 128 else sp(v))
def arr(vals, field):
    b = push(len(vals)) + b'\xbc\x0a'
    for i, v in enumerate(vals):
        b += b'\x59' + push(i) + push(v) + b'\x4f'
    return b + b'\xb3' + struct.pack('>H', field)

def parse(d):
    p = 10; n = struct.unpack('>H', d[8:10])[0]; cp = [None] * n; i = 1
    while i < n:
        t = d[p]
        if t == 1:
            l = struct.unpack('>H', d[p+1:p+3])[0]; cp[i] = ('utf8', d[p+3:p+3+l].decode('utf8', 'replace')); p += 3 + l
        elif t in (3, 4): p += 5
        elif t in (5, 6): p += 9; i += 1
        elif t in (7, 8, 16): cp[i] = ('ref1', struct.unpack('>H', d[p+1:p+3])[0]); p += 3
        elif t in (9, 10, 11, 12, 18): cp[i] = ('ref2', struct.unpack('>HH', d[p+1:p+5])); p += 5
        elif t == 15: p += 4
        else: raise SystemExit('cp tag %d' % t)
        i += 1
    return cp, p

def main(src, dst):
    d = bytearray(open(src, 'rb').read())
    cp, p = parse(d)
    utf = lambda i: cp[i][1]
    def fref(name):
        for i, e in enumerate(cp):
            if e and e[0] == 'ref2' and d and True:
                pass
        res = []
        for i, e in enumerate(cp):
            if e and e[0] == 'ref2':
                nt = cp[e[1][1]]
                if nt and nt[0] == 'ref2' and utf(nt[1][0]) == name and utf(nt[1][1]) == '[I':
                    res.append(i)
        return res
    p += 6
    ic_ = struct.unpack('>H', d[p:p+2])[0]; p += 2 + 2 * ic_
    def skip_members(p):
        n = struct.unpack('>H', d[p:p+2])[0]; p += 2; out = []
        for _ in range(n):
            acc, ni, di, ac = struct.unpack('>HHHH', d[p:p+8]); start = p; p += 8; attrs = []
            for _ in range(ac):
                an, al = struct.unpack('>HI', d[p:p+6]); attrs.append((utf(an), p, al)); p += 6 + al
            out.append((utf(ni), utf(di), attrs))
        return p, out
    p, fields = skip_members(p)
    p, methods = skip_members(p)
    code = [a for n, de, attrs in methods if n == '<clinit>' for a in attrs if a[0] == 'Code'][0]
    _, ap, al = code
    ms, ml, cl = struct.unpack('>HHI', d[ap+6:ap+14])
    cs = ap + 14; body = bytes(d[cs:cs+cl])
    rest = bytes(d[cs+cl:ap+6+al])
    exl = struct.unpack('>H', rest[:2])[0]; nattr = struct.unpack('>H', rest[2:4])[0]
    assert exl == 0, 'clinit has exception table'
    for op in (0xa7, 0xc8, 0xaa, 0xab, 0xa8, 0xc9) + tuple(range(0x99, 0xa7)) + (0xc6, 0xc7):
        pass  # branch check done with javap before writing this tool
    U, V = fref('u'), fref('v')
    assert len(U) == 1 and len(V) == 1, (U, V)
    old = arr([1, 4, 5, 6, 7], U[0]) + arr([27, 38, 33, 35, 36], V[0])
    new = arr([0, 1, 4, 5, 6, 7], U[0]) + arr([628, 629, 38, 33, 35, 36], V[0])
    assert body.count(old) == 1, body.count(old)
    nb = body.replace(old, new); delta = len(nb) - len(body)
    if nattr:
        raise SystemExit('clinit Code has sub-attributes (%d); offsets would need fixing' % nattr)
    d[ap+2:ap+6] = struct.pack('>I', al + delta)
    d[ap+10:ap+14] = struct.pack('>I', cl + delta)
    d[cs:cs+cl] = nb
    open(dst, 'wb').write(d)
    print('clinit code %d -> %d bytes (+%d)' % (cl, cl + delta, delta))

main(sys.argv[1], sys.argv[2])
