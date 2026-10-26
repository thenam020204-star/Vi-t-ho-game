# -*- coding: utf-8 -*-
"""Tien ich doc/ghi chuoi readUTF (u2 do dai byte + Java modified UTF-8) trong resource game."""
import struct

def mutf8_decode(b):
    out = []; i = 0; n = len(b)
    while i < n:
        c = b[i]
        if c < 0x80 and c != 0:
            out.append(chr(c)); i += 1
        elif (c & 0xE0) == 0xC0 and i + 1 < n and (b[i + 1] & 0xC0) == 0x80:
            out.append(chr(((c & 0x1F) << 6) | (b[i + 1] & 0x3F))); i += 2
        elif (c & 0xF0) == 0xE0 and i + 2 < n and (b[i + 1] & 0xC0) == 0x80 and (b[i + 2] & 0xC0) == 0x80:
            out.append(chr(((c & 0x0F) << 12) | ((b[i + 1] & 0x3F) << 6) | (b[i + 2] & 0x3F))); i += 3
        else:
            raise ValueError('mutf8 sai o byte %d' % i)
    return ''.join(out)

def mutf8_encode(s):
    o = bytearray()
    for ch in s:
        c = ord(ch)
        if c == 0: o += b'\xc0\x80'
        elif c < 0x80: o.append(c)
        elif c < 0x800: o += bytes([0xC0 | (c >> 6), 0x80 | (c & 0x3F)])
        elif c < 0x10000: o += bytes([0xE0 | (c >> 12), 0x80 | ((c >> 6) & 0x3F), 0x80 | (c & 0x3F)])
        else: raise ValueError('ky tu ngoai BMP: %r' % ch)
    return bytes(o)

def utf(s):
    b = mutf8_encode(s)
    assert len(b) <= 0xFFFF
    return struct.pack('>H', len(b)) + b

class R:
    def __init__(self, d, p=0): self.d = d; self.p = p
    def u1(self): v = self.d[self.p]; self.p += 1; return v
    def s1(self): v = struct.unpack('>b', self.d[self.p:self.p + 1])[0]; self.p += 1; return v
    def s2(self): v = struct.unpack('>h', self.d[self.p:self.p + 2])[0]; self.p += 2; return v
    def s4(self): v = struct.unpack('>i', self.d[self.p:self.p + 4])[0]; self.p += 4; return v
    def utf(self):
        n = struct.unpack('>H', self.d[self.p:self.p + 2])[0]
        s = mutf8_decode(self.d[self.p + 2:self.p + 2 + n]); self.p += 2 + n; return s
    def raw(self, n): v = self.d[self.p:self.p + n]; self.p += n; return v

def is_cjk(ch):
    c = ord(ch)
    return 0x2E80 <= c <= 0x9FFF or 0xF900 <= c <= 0xFAFF or 0xFF00 <= c <= 0xFFEF or 0x3000 <= c <= 0x303F

def scan(d, need_cjk=True):
    """Do tim moi vi tri co dang readUTF hop le chua chu Han. Tra [(offset, text)]."""
    res = []; p = 0; n = len(d)
    while p + 2 <= n:
        L = struct.unpack('>H', d[p:p + 2])[0]
        if L > 0 and p + 2 + L <= n:
            try:
                s = mutf8_decode(d[p + 2:p + 2 + L])
                ok = all(ord(c) >= 0x20 or c in '\n' for c in s) and (not need_cjk or any(is_cjk(c) for c in s))
            except ValueError:
                ok = False
            if ok:
                res.append((p, s)); p += 2 + L; continue
        p += 1
    return res
