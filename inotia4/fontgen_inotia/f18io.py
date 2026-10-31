"""Doc/ghi font .bin cua bo tool NAM_P (writeUTF charset | u8 height | u8 width[n] | atlas 1bpp LSB-first)."""
import struct


def load(path):
    b = open(path, 'rb').read()
    n = struct.unpack('>H', b[:2])[0]
    cs = b[2:2 + n].decode('utf-8')  # charset chi gom BMP, khong co U+0000 -> UTF-8 == MUTF-8
    p = 2 + n
    h = b[p]; p += 1
    ws = list(b[p:p + len(cs)]); p += len(cs)
    W = sum(ws)
    bits = b[p:p + ((W * h + 7) >> 3)]
    glyphs = {}
    off = 0
    for c, w in zip(cs, ws):
        rows = []
        for y in range(h):
            r = ''
            for x in range(w):
                k = y * W + off + x
                r += '#' if bits[k >> 3] & (1 << (k & 7)) else '.'
            rows.append(r)
        glyphs[c] = rows
        off += w
    return cs, h, glyphs


def save(path, cs, h, glyphs):
    ws = [len(glyphs[c][0]) for c in cs]
    W = sum(ws)
    bits = bytearray((W * h + 7) >> 3)
    off = 0
    for c, w in zip(cs, ws):
        g = glyphs[c]
        assert len(g) == h and all(len(r) == w for r in g), c
        for y in range(h):
            for x in range(w):
                if g[y][x] == '#':
                    k = y * W + off + x
                    bits[k >> 3] |= 1 << (k & 7)
        off += w
    enc = cs.encode('utf-8')
    with open(path, 'wb') as f:
        f.write(struct.pack('>H', len(enc)) + enc + bytes([h]) + bytes(ws) + bytes(bits))


def show(glyphs, chars):
    h = len(next(iter(glyphs.values())))
    for y in range(h):
        print('%2d ' % y + ' '.join(glyphs[c][y] for c in chars))
