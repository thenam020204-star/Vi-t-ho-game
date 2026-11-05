"""Heroes Lore 4 *_BM image container: u32 count, then per image u32 size + body.
body: u8 type (0x0b = 4 bpp, 0x0c = 8 bpp), u16 w, u16 h, u16 aux, u16 ncolors,
      ncolors x u16 RGB565 palette (entry 0 = 0xF81F = transparent key), pixels (row major,
      4 bpp: high nibble first)."""
import struct, sys, os
from PIL import Image

def rgb(c):
    r = (c >> 11) & 31; g = (c >> 5) & 63; b = c & 31
    return ((r * 255 + 15) // 31, (g * 255 + 31) // 63, (b * 255 + 15) // 31)

def parse(d):
    n = struct.unpack_from('<I', d, 0)[0]
    o = 4; out = []
    for i in range(n):
        size = struct.unpack_from('<I', d, o)[0]; o += 4
        body = d[o:o + size]; o += size
        out.append(body)
    return out, d[o:]

def decode(body):
    t, w, h, aux, nc = struct.unpack_from('<BHHHH', body, 0)
    pal = struct.unpack_from('<%dH' % nc, body, 9)
    px = body[9 + 2 * nc:]
    im = Image.new('RGBA', (w, h))
    P = im.load()
    for y in range(h):
        for x in range(w):
            if t == 0x0b:
                i = y * ((w + 1) // 2) + x // 2
                v = px[i] >> 4 if x % 2 == 0 else px[i] & 15
            else:
                v = px[y * w + x]
            c = pal[v] if v < nc else 0xF81F
            P[x, y] = (0, 0, 0, 0) if c == 0xF81F else rgb(c) + (255,)
    return im, (t, w, h, aux, nc)

if __name__ == '__main__':
    f, outdir = sys.argv[1], sys.argv[2]
    os.makedirs(outdir, exist_ok=True)
    d = open(f, 'rb').read()
    bodies, tail = parse(d)
    for i, b in enumerate(bodies):
        try:
            im, hd = decode(b)
            im.save('%s/%s_%02d.png' % (outdir, os.path.basename(f), i))
            print(i, hd, len(b))
        except Exception as e:
            print(i, 'ERR', e, b[:12].hex())
    print('tail', len(tail))


def to565(r, g, b):
    return ((r * 31 + 127) // 255) << 11 | ((g * 63 + 127) // 255) << 5 | ((b * 31 + 127) // 255)


def encode(im, colors=255, dither=True):
    """RGBA image -> 8 bpp body (palette entry 0 = transparent 0xF81F)."""
    im = im.convert('RGBA')
    w, h = im.size
    A = im.getchannel('A').load()
    q = im.convert('RGB').quantize(colors=colors, method=Image.Quantize.MEDIANCUT,
                                   dither=Image.Dither.FLOYDSTEINBERG if dither else Image.Dither.NONE)
    k = max(c for n, c in q.getcolors(256)) + 1
    pal = q.getpalette()
    palette = [0xF81F] + [to565(pal[3 * i], pal[3 * i + 1], pal[3 * i + 2]) for i in range(k)]
    idx = q.load()
    px = bytearray(w * h)
    for y in range(h):
        for x in range(w):
            px[y * w + x] = 0 if A[x, y] < 128 else idx[x, y] + 1
    return struct.pack('<BHHHH', 0x0c, w, h, w, len(palette)) + struct.pack('<%dH' % len(palette), *palette) + bytes(px)


def blank(body):
    """Same size image, fully transparent."""
    t, w, h, aux, nc = struct.unpack_from('<BHHHH', body, 0)
    n = ((w + 1) // 2) * h if t == 0x0b else w * h
    return struct.pack('<BHHHH', t, w, h, aux, 1) + struct.pack('<H', 0xF81F) + bytes(n)


def build(bodies, tail=b''):
    out = struct.pack('<I', len(bodies))
    for b in bodies:
        out += struct.pack('<I', len(b)) + b
    return out + tail
