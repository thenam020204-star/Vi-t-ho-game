#!/usr/bin/env python3
"""Tao font chunk + atlas PNG moi cho EA SDK font (o.a(h, byte[]) trong o.class).

Dinh dang chunk (big-endian), xac minh tu bytecode o.class:
  u1 flags | u1 x7 metrics [dir, dir2, lineExtra, ascent, descent, missingW, spacing]
  u2 nChars | u2 nRects | u2 chars[nChars] (Unicode, sap tang) | u2 off[nChars]
  glyph data: u1 advance, u1 nRect, nRect x (u1 srcX, u1 srcY, s1 dx, s1 dy, u1 w, u1 h)
  ve: x = penX + dx ; y = lineTop + ascent - dy - h
Atlas PNG: indexed 8-bit, PLTE 256 mau + tRNS chep nguyen tu anh goc (bang mau thay the
cua game ghi de PLTE tai cho). Index 0 = than chu, 1 = vien, 2 = trong suot.

python3 make_font.py <old_chunk.bin> <old_atlas.png> <f17.bin> <pixeltext_dir> <out_chunk> <out_png>
"""
import sys, struct, zlib
sys.path.insert(0, sys.argv[4])
from pixeltext import container
from PIL import Image

BODY, LINE, CLEAR = 0, 1, 2
ATLAS_W = 256
# game dung '+' lam (TM) va '{' lam (C) trong glyph goc -> giu glyph cu cho 2 ma nay
KEEP_OLD = {ord('+'), ord('{')}

def parse_chunk(b):
    n5, s2 = struct.unpack('>HH', b[8:12]); p = 12
    chars = struct.unpack('>%dH' % n5, b[p:p+2*n5]); p += 2*n5
    offs = struct.unpack('>%dH' % n5, b[p:p+2*n5]); p += 2*n5
    g = b[p:]
    glyphs = {}
    for c, o in zip(chars, offs):
        adv, n = g[o], g[o+1]
        rects = [struct.unpack('BBbbBB', g[o+2+6*k:o+8+6*k]) for k in range(n)]
        glyphs[c] = (adv, rects)
    return b[:8], glyphs

def png_chunk(t, d):
    return struct.pack('>I', len(d)) + t + d + struct.pack('>I', zlib.crc32(t + d) & 0xffffffff)

def main():
    oldb = open(sys.argv[1], 'rb').read()
    hdr, old = parse_chunk(oldb)
    asc = hdr[4]
    assert hdr[0] == 0x21 and asc == 14 and hdr[5] == 3, hdr.hex()
    atlas = Image.open(sys.argv[2]); assert atlas.mode == 'P'
    raw = open(sys.argv[2], 'rb').read()
    f = container.decode(sys.argv[3]); H = f['height']; assert H == 17
    # --- glyph bitmaps: list of (char, adv, dx, dy_top(line row of bitmap top), bitmap[rows][cols] of index)
    items = []
    o = 0
    newset = set()
    for ch, w in zip(f['chars'], f['widths']):
        cols = range(o, o + w); o += w
        body = [[f['rows'][y][x] for x in cols] for y in range(H)]
        c = ord(ch)
        if c in KEEP_OLD: continue
        newset.add(c)
        ink = [(y, x) for y in range(H) for x in range(w) if body[y][x]]
        adv = w if not any(x >= w - 1 for _, x in ink) else w + 1   # than cham cot cach -> them 1 cot de vien chu sau khong de len
        if ch == ' ' or not ink:
            items.append((c, adv, 0, 0, None)); continue
        y0 = min(y for y, _ in ink) - 1; y1 = max(y for y, _ in ink) + 1
        x0 = min(x for _, x in ink) - 1; x1 = max(x for _, x in ink) + 1
        bm = [[CLEAR] * (x1 - x0 + 1) for _ in range(y1 - y0 + 1)]
        for y, x in ink:
            for dy in (-1, 0, 1):
                for dx in (-1, 0, 1):
                    yy, xx = y + dy - y0, x + dx - x0
                    if bm[yy][xx] == CLEAR: bm[yy][xx] = LINE
        for y, x in ink: bm[y - y0][x - x0] = BODY
        items.append((c, adv, x0, y0, bm))   # line row = cell row
    kept_old = 0
    for c, (adv, rects) in old.items():
        if c in newset: continue
        kept_old += 1
        if not rects:
            items.append((c, adv, 0, 0, None)); continue
        # ghep cac rect thanh 1 bitmap (toa do dong: top = asc - dy - h)
        boxes = [(dx, asc - dy - h, sx, sy, w, h) for sx, sy, dx, dy, w, h in rects]
        x0 = min(b[0] for b in boxes); y0 = min(b[1] for b in boxes)
        x1 = max(b[0] + b[4] for b in boxes); y1 = max(b[1] + b[5] for b in boxes)
        bm = [[CLEAR] * (x1 - x0) for _ in range(y1 - y0)]
        for X, Y, sx, sy, w, h in boxes:
            for yy in range(h):
                for xx in range(w):
                    v = atlas.getpixel((sx + xx, sy + yy))
                    if v != CLEAR: bm[Y - y0 + yy][X - x0 + xx] = v
        items.append((c, adv, x0, y0, bm))
    items.sort(key=lambda t: t[0])
    # --- shelf packing, cao giam dan
    order = sorted([i for i, t in enumerate(items) if t[4]], key=lambda i: -len(items[i][4]))
    pos = {}; x = y = rowh = 0
    for i in order:
        bm = items[i][4]; w, h = len(bm[0]), len(bm)
        if x + w > ATLAS_W: x = 0; y += rowh; rowh = 0
        pos[i] = (x, y); x += w; rowh = max(rowh, h)
    AH = y + rowh
    assert AH < 256
    img = [[CLEAR] * ATLAS_W for _ in range(AH)]
    for i, (px, py) in pos.items():
        for yy, r in enumerate(items[i][4]):
            img[py + yy][px:px + len(r)] = r
    # --- PNG: IHDR moi + PLTE/tRNS goc (byte-for-byte) + IDAT
    p = 8; anc = b''
    while p < len(raw):
        n, t = struct.unpack('>I4s', raw[p:p+8])
        if t in (b'PLTE', b'tRNS'): anc += raw[p:p+12+n]
        p += 12 + n
    assert anc[4:8] == b'PLTE' and struct.unpack('>I', anc[:4])[0] == 768
    ihdr = struct.pack('>IIBBBBB', ATLAS_W, AH, 8, 3, 0, 0, 0)
    data = b''.join(b'\x00' + bytes(r) for r in img)
    png = b'\x89PNG\r\n\x1a\n' + png_chunk(b'IHDR', ihdr) + anc + png_chunk(b'IDAT', zlib.compress(data, 9)) + png_chunk(b'IEND', b'')
    open(sys.argv[6], 'wb').write(png)
    # --- chunk
    gd = b''; offs = []; nrect = 0
    for i, (c, adv, x0, y0, bm) in enumerate(items):
        offs.append(len(gd))
        if bm is None:
            gd += bytes([adv, 0]); continue
        w, h = len(bm[0]), len(bm)
        sx, sy = pos[i]
        dy = asc - h - y0                 # y0 = asc - dy - h
        assert -128 <= dy < 128 and -128 <= x0 < 128
        gd += bytes([adv, 1]) + struct.pack('BBbbBB', sx, sy, x0, dy, w, h); nrect += 1
    assert len(gd) < 32768
    n = len(items)
    out = hdr + struct.pack('>HH', n, nrect) + struct.pack('>%dH' % n, *[t[0] for t in items]) + struct.pack('>%dH' % n, *offs) + gd
    assert 12 + 4 * n + (2 * n + 6 * nrect) == len(out)   # cong thuc kich thuoc cua o.a(h, byte[])
    open(sys.argv[5], 'wb').write(out)
    print('glyph', n, '(moi %d, giu cu %d) rect %d, atlas %dx%d, png %d B, chunk %d B' % (len(newset), kept_old, nrect, ATLAS_W, AH, len(png), len(out)))

main()
