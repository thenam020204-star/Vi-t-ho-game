# -*- coding: utf-8 -*-
"""Ghep glyph f12 (bo tool NAM_P) vao bin/font.bin goc cua game + sinh bin/fw.bin.

  python3 -I build_font12.py <font.bin goc> <f12.bin> <out_dir> [--keep "'"]

font.bin (doc boi bs.a()):  s1 size(=12) | s2 n | s2 code[n] (sap xep theo short CO DAU) |
                            n * glyph, glyph = size hang * ((size-1>>3)+1) byte, MSB = cot trai.
fw.bin  (moi, doc boi bs.a() da sua): u1 advance[n] theo dung thu tu code[] cua font.bin moi.
Glyph f12 thay glyph goc cung ma (ASCII) va them ky tu Viet con thieu. Ky tu khong co trong f12
giu glyph goc, advance theo luat goc (c < 0x7F ? size/2 : size).
"""
import struct, sys, os

def load_game(p):
    d = open(p, 'rb').read()
    size = d[0]; n = struct.unpack('>h', d[1:3])[0]
    codes = list(struct.unpack('>%dh' % n, d[3:3 + 2 * n]))
    bpr = ((size - 1) >> 3) + 1; gl = size * bpr; p0 = 3 + 2 * n
    assert len(d) == p0 + n * gl, 'font.bin co du lieu thua/thieu'
    glyphs = [d[p0 + i * gl: p0 + (i + 1) * gl] for i in range(n)]
    return size, bpr, codes, glyphs

def load_f12(p):
    d = open(p, 'rb').read()
    ln = struct.unpack('>H', d[:2])[0]
    chars = d[2:2 + ln].replace(b'\xc0\x80', b'\x00').decode('utf-8'); q = 2 + ln
    h = d[q]; q += 1
    w = list(d[q:q + len(chars)]); q += len(chars)
    W = sum(w); bits = d[q:]; rows = []; bi = 0
    for y in range(h):
        r = []
        for x in range(W):
            r.append((bits[bi >> 3] >> (bi & 7)) & 1); bi += 1
        rows.append(r)
    out = {}; x0 = 0
    for ch, cw in zip(chars, w):
        out[ch] = (cw, [r[x0:x0 + cw] for r in rows]); x0 += cw
    return h, out

def pack(size, bpr, cols):
    b = bytearray(size * bpr)
    for y in range(size):
        for x, v in enumerate(cols[y]):
            if v: b[y * bpr + (x >> 3)] |= 0x80 >> (x & 7)
    return bytes(b)

def main():
    src, f12, outd = sys.argv[1:4]
    size, bpr, codes, glyphs = load_game(src)
    h, fv = load_f12(f12)
    assert h == size == 12, (h, size)
    tab = {}
    for c, g in zip(codes, glyphs):
        u = c & 0xFFFF
        tab[u] = (g, (size >> 1) if u < 0x7F else size)
    rep = add = 0
    for ch, (cw, rows) in fv.items():
        u = ord(ch)
        assert cw <= size
        if u in tab: rep += 1
        else: add += 1
        tab[u] = (pack(size, bpr, rows), cw)
    keys = sorted(tab, key=lambda u: u - 0x10000 if u >= 0x8000 else u)   # thu tu short co dau
    os.makedirs(outd, exist_ok=True)
    o = bytearray([size]); o += struct.pack('>h', len(keys))
    for u in keys: o += struct.pack('>H', u)
    for u in keys: o += tab[u][0]
    open(os.path.join(outd, 'font.bin'), 'wb').write(o)
    open(os.path.join(outd, 'fw.bin'), 'wb').write(bytes(tab[u][1] for u in keys))
    print('glyph goc %d, thay %d, them %d -> %d; font.bin %d byte, fw.bin %d byte'
          % (len(codes), rep, add, len(keys), len(o), len(keys)))

if __name__ == '__main__':
    main()
