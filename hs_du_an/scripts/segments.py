"""Sinh danh sách đoạn (file, start, end) cho các định dạng có cấu trúc: .m (kịch bản) và i.dat."""
import struct, glob, os

def m_segments(path):
    b = open(path, 'rb').read()
    A = struct.unpack('>I', b[:4])[0]
    n = b[8]
    p = 9 + 5 * n
    segs = []
    for i in range(n):
        sid, L = struct.unpack('>BI', b[9 + 5 * i:14 + 5 * i])
        segs.append((p, p + L, sid))
        p += L
    assert p == 8 + A, path
    return segs

def idat_segments(path):
    b = open(path, 'rb').read()
    n = struct.unpack('>i', b[:4])[0]
    ent = [struct.unpack('>hi', b[4 + 6 * i:10 + 6 * i]) for i in range(n)]
    base = 4 + 6 * n
    blen = struct.unpack('>i', b[base:base + 4])[0]
    base += 4
    assert base + blen == len(b), path
    offs = sorted(set(o for _, o in ent))
    segs = []
    for i, o in enumerate(offs):
        e = offs[i + 1] if i + 1 < len(offs) else blen
        segs.append((base + o, base + e, o))
    return segs
