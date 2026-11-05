#!/usr/bin/env python3
"""fontbin.py - decode/encode the J2ME `font.bin` bitmap-font container used by fontcus.class

Container layout (exactly what fontcus.<clinit> reads, verified against bytecode):
  u2            utfLen
  u1[utfLen]    charset, Java modified-UTF8 (DataInputStream.readUTF)
  s1            fontHeight
  s1[nChars]    glyph advance width, one per char, in charset order
  bits          1bpp atlas, row-major, rows are height, row length = sum(widths),
                LSB-first within each byte, bitstream is CONTINUOUS across rows
                (no per-row byte padding). Total = ceil(h*atlasW/8) bytes.
"""
import struct, sys, json, os

def modified_utf8_encode(s):
    out = bytearray()
    for ch in s:
        c = ord(ch)
        if c == 0:                      out += b'\xc0\x80'
        elif c < 0x80:                  out.append(c)
        elif c < 0x800:                 out += bytes([0xc0|(c>>6), 0x80|(c&0x3f)])
        elif c < 0x10000:               out += bytes([0xe0|(c>>12), 0x80|((c>>6)&0x3f), 0x80|(c&0x3f)])
        else: raise ValueError("supplementary char %r not supported by this font container" % ch)
    return bytes(out)

def modified_utf8_decode(b):
    # all real fonts here are BMP/no-NUL; handle the CESU-8 NUL case anyway
    return b.replace(b'\xc0\x80', b'\x00').decode('utf-8')

def decode(path):
    d = open(path,'rb').read()
    n = struct.unpack('>H', d[0:2])[0]
    chars = modified_utf8_decode(d[2:2+n]); p = 2+n
    h = d[p] - (256 if d[p] > 127 else 0); p += 1
    N = len(chars)
    widths = []
    for i in range(N):
        w = d[p]; p += 1
        widths.append(w - 256 if w > 127 else w)
    atlasW = sum(widths)
    need = (h*atlasW + 7)//8
    bits = d[p:p+need]
    trailing = d[p+need:]
    rows = []
    acc = 0; nbit = 8; bi = 0
    for y in range(h):
        row = bytearray(atlasW)
        for x in range(atlasW):
            if nbit >= 8:
                acc = bits[bi]; bi += 1; nbit = 0
            if acc & 1: row[x] = 1
            acc >>= 1; nbit += 1
        rows.append(row)
    return dict(chars=chars, height=h, widths=widths, atlasW=atlasW, rows=rows,
                trailing=trailing, size=len(d))

def encode(chars, height, widths, rows):
    assert len(chars) == len(widths), "charset/width count mismatch"
    atlasW = sum(widths)
    out = bytearray()
    u = modified_utf8_encode(chars)
    assert len(u) <= 0xFFFF, "charset UTF8 too long for u2 length"
    out += struct.pack('>H', len(u)); out += u
    assert -128 <= height <= 127
    out.append(height & 0xFF)
    for w in widths:
        assert -128 <= w <= 127, "width %d out of signed byte range" % w
        out.append(w & 0xFF)
    acc = 0; nbit = 0
    for y in range(height):
        row = rows[y]
        assert len(row) == atlasW, "row %d length %d != atlasW %d" % (y, len(row), atlasW)
        for x in range(atlasW):
            if row[x]: acc |= (1 << nbit)
            nbit += 1
            if nbit == 8:
                out.append(acc); acc = 0; nbit = 0
    if nbit: out.append(acc)
    return bytes(out)

if __name__ == '__main__':
    f = decode(sys.argv[1])
    print("chars=%d height=%d atlasW=%d filesize=%d trailing=%d"
          % (len(f['chars']), f['height'], f['atlasW'], f['size'], len(f['trailing'])))
