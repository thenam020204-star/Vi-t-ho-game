"""Định dạng font bitmap (magic e3 0a f2 23) trong common.dat của This War of Mine.
  u32 magic, u32 ver(=6), u8 flag (bpp = 1+flag), u32 nPages, u32 H[nPages],
  pages: 1024*H*bpp byte mỗi trang, u32 nGlyph,
  glyph 16 byte: u32 codepoint, i8 page, i8 xoff, i8 yoff (= -khoảng cách từ đỉnh dòng), u8 advance,
                 u16 x0, x1, y0, y1 (rect gồm 1px viền trống, x1/y1 loại trừ),
  đuôi: u32 (chiều cao dòng)."""
import struct
W = 1024
def parse(d):
    assert d[:4] == b'\xe3\x0a\xf2\x23'
    ver, = struct.unpack('<I', d[4:8]); flag = d[8]; bpp = 1 + flag
    n, = struct.unpack('<I', d[9:13]); H = list(struct.unpack('<%dI' % n, d[13:13 + 4 * n]))
    p = 13 + 4 * n; pages = []
    for h in H: pages.append(d[p:p + W * h * bpp]); p += W * h * bpp
    cnt, = struct.unpack('<I', d[p:p + 4]); p += 4
    G = [list(struct.unpack('<IbbbBHHHH', d[p + 16 * i:p + 16 * i + 16])) for i in range(cnt)]
    p += 16 * cnt
    return dict(ver=ver, flag=flag, bpp=bpp, H=H, pages=pages, G=G, tail=d[p:])
def build(f):
    o = bytearray(b'\xe3\x0a\xf2\x23' + struct.pack('<I', f['ver']) + bytes([f['flag']]))
    o += struct.pack('<I', len(f['H'])) + struct.pack('<%dI' % len(f['H']), *f['H'])
    for pg in f['pages']: o += pg
    o += struct.pack('<I', len(f['G']))
    for g in f['G']: o += struct.pack('<IbbbBHHHH', *g)
    o += f['tail']; return bytes(o)
