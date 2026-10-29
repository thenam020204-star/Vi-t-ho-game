"""Doc/ghi file text resource cua Tinh Van Truyen Thuyet (khop src/m.java).
 D-file (m.d): short rows, short cols, rows*cols x readUTF           -> iteminfo/skillinfo/missioninfo0,1/mapinfo/universeinfo
 E-file (m.b): short n, n x readUTF, roi cac mang short (giu nguyen) -> e<N>.data
"""
import struct
D_FILES = ['iteminfo', 'skillinfo', 'missioninfo0', 'missioninfo1', 'mapinfo', 'universeinfo']


def _utf(d, p):
    l = struct.unpack_from('>H', d, p)[0]
    return d[p + 2:p + 2 + l].decode('utf-8'), p + 2 + l


def _w(s):
    e = s.encode('utf-8')
    if '\x00' in s or len(e) > 65535:
        raise ValueError('chuoi khong hop le cho readUTF: %r' % s[:30])
    return struct.pack('>H', len(e)) + e


def read_d(d):
    r, c = struct.unpack_from('>hh', d, 0); p = 4; cells = []
    for _ in range(r * c):
        s, p = _utf(d, p); cells.append(s)
    if p != len(d):
        raise ValueError('D-file thua %d byte' % (len(d) - p))
    return r, c, cells


def write_d(r, c, cells):
    return struct.pack('>hh', r, c) + b''.join(_w(s) for s in cells)


def read_e(d):
    n = struct.unpack_from('>h', d, 0)[0]; p = 2; ss = []
    for _ in range(n):
        s, p = _utf(d, p); ss.append(s)
    tail = d[p:]
    # kiem phan duoi: 3 mang short[][] (m.b) -> phai doc het
    q = 0
    for _ in range(3):
        k = struct.unpack_from('>h', tail, q)[0]; q += 2
        for _ in range(k):
            m = struct.unpack_from('>h', tail, q)[0]; q += 2 + 2 * m
    return ss, tail, q


def write_e(ss, tail):
    return struct.pack('>h', len(ss)) + b''.join(_w(s) for s in ss) + tail
