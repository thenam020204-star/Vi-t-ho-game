"""Doc/ghi dinh dang trunk .pak cua engine RPG22 (CGame.OpenTrunk).
header: byte n (signed), n x varint7 (LSB-first, bit7 = con tiep) = do dai tung trunk; sau do du lieu noi tiep."""
import sys

def read_pak(data):
    n = data[0]
    if n >= 128: n -= 256
    pos = 1
    lens = []
    for _ in range(n):
        v = 0; sh = 0
        while True:
            b = data[pos]; pos += 1
            v += (b & 0x7f) << sh; sh += 7
            if not b & 0x80: break
        lens.append(v)
    trunks = []
    for L in lens:
        trunks.append(data[pos:pos + L]); pos += L
    return trunks, data[pos:]

def varint(v):
    out = bytearray()
    while True:
        b = v & 0x7f; v >>= 7
        if v: out.append(b | 0x80)
        else: out.append(b); break
    return bytes(out)

def write_pak(trunks, tail=b''):
    out = bytearray([len(trunks) & 0xff])
    for t in trunks: out += varint(len(t))
    for t in trunks: out += t
    return bytes(out) + tail

def read_strings(t):
    """trunk chuoi: short count, count x short len, roi cac chuoi utf-8"""
    cnt = int.from_bytes(t[0:2], 'big', signed=True)
    lens = [int.from_bytes(t[2 + 2*i:4 + 2*i], 'big', signed=True) for i in range(cnt)]
    pos = 2 + 2*cnt; out = []
    for L in lens:
        out.append(t[pos:pos+L].decode('utf-8')); pos += L
    return out, t[pos:]

def write_strings(strs, tail=b''):
    bs = [s.encode('utf-8') for s in strs]
    out = bytearray(len(bs).to_bytes(2, 'big'))
    for b in bs:
        assert len(b) < 10240, len(b)
        out += len(b).to_bytes(2, 'big')
    for b in bs: out += b
    return bytes(out) + tail

if __name__ == '__main__':
    d = open(sys.argv[1], 'rb').read()
    tr, tail = read_pak(d)
    print('trunks', len(tr), 'tail', len(tail))
    assert write_pak(tr, tail) == d
    for i, t in enumerate(tr):
        try:
            s, rest = read_strings(t)
            assert write_strings(s, rest) == t
            print(i, len(t), 'strings', len(s), 'rest', len(rest), repr(s[:3])[:150])
        except Exception as e:
            print(i, len(t), 'not strings', e)
