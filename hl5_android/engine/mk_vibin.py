#!/usr/bin/env python3
"""Sinh vi.bin (định dạng HLVI v1, xem BAO-CAO-RE.md §9.7) từ dict.txt + font.bin (15px) + font10.bin (10px) + f18.bin (18px).

  mk_vibin.py DIR_VI_ANDROID OUT.bin        (DIR có dict.txt, font.bin, font10.bin, f18.bin)

Bố cục (little-endian):
  0   "HLVI" u16 ver=1 u16 nwords u16 nchars u8 nfonts=3 u8 spaceW
  12  u32 off_wordidx, off_words, off_font[3]            ; offset từ đầu file (căn 4)
      wordidx : u16[nwords+1], offset so với off_words
      words   : mỗi từ = u8 mode (0 thường, 1 nén, 2 cỡ 10) u8 n u8 glyph[n]
      font f  : u8 height, u8 baseline, u16 atlasW, u16 nbytes, u16 pad
                s8 raw[N] | s8 adv0[N] | s8 adv1[N] | pad | u16 goff[N] | atlas      (N = nchars căn 4)
        raw  = bề rộng ô thật trong atlas (<=0: thiếu glyph);  adv0 = bước thường, adv1 = bước nén
        (khoảng trắng đã đặt sẵn: adv0 = spaceW, adv1 = 3; ký tự thiếu = 0)
      chars   : u16 codepoint[nchars] (chỉ để debug) ở cuối file
"""
import sys, struct, os
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

def read_font(p):
    d = open(p, 'rb').read()
    n = struct.unpack_from('>H', d, 0)[0]
    cs = d[2:2 + n].decode('utf-8')          # chuỗi UTF-8 chuẩn (không có NUL/surrogate trong bộ này)
    o = 2 + n
    h = struct.unpack_from('b', d, o)[0]; o += 1
    w = list(struct.unpack_from('%db' % len(cs), d, o)); o += len(cs)
    tot = sum(w)
    nb = (h * tot + 7) // 8
    bits = d[o:o + nb]
    assert len(bits) == nb, (p, len(bits), nb)
    return dict(cs=cs, h=h, w=w, tot=tot, bits=bits, idx={c: i for i, c in enumerate(cs)})

def glyph_pixels(f, c):
    i = f['idx'].get(c)
    if i is None: return None
    off = sum(f['w'][:i]); out = []
    for y in range(f['h']):
        for x in range(f['w'][i]):
            b = y * f['tot'] + off + x
            if f['bits'][b >> 3] >> (b & 7) & 1: out.append((x, y))
    return out

def baseline(f):
    p = glyph_pixels(f, 'H')
    return max(y for _, y in p) if p else f['h'] - 1

def build(dirv):
    words = open(os.path.join(dirv, 'dict.txt'), encoding='utf-8').read().split('\n')
    if words and words[-1] == '': words.pop()
    fonts = [read_font(os.path.join(dirv, n)) for n in ('font.bin', 'font10.bin', 'f18.bin')]
    modes, bodies = [], []
    for w in words:
        m = 0
        if w[:1] == '\x01': m, w = 1, w[1:]
        elif w[:1] == '\x02': m, w = 2, w[1:]
        modes.append(m); bodies.append(w)
    chars = sorted({c for b in bodies for c in b} | {' '})
    nch = len(chars); gi = {c: i for i, c in enumerate(chars)}
    assert nch <= 255 and len(words) <= 94 * 55, (nch, len(words))
    sw = fonts[0]['w'][fonts[0]['idx'][' ']] if ' ' in fonts[0]['idx'] else 4
    if sw <= 0: sw = 4
    N = (nch + 3) & ~3
    # từ
    wb = bytearray(); widx = []
    for m, b in zip(modes, bodies):
        widx.append(len(wb)); wb += bytes([m, len(b)]) + bytes(gi[c] for c in b)
    widx.append(len(wb))
    # font
    fblocks = []
    for fi, f in enumerate(fonts):
        raw = bytearray(N); a0 = bytearray(N); a1 = bytearray(N); goff = [0] * N
        glyphs = []; pos = 0
        for c in chars:
            i = f['idx'].get(c); g = gi[c]
            if i is not None and f['w'][i] > 0:
                r = f['w'][i]; raw[g] = r
                a0[g] = r; a1[g] = r - 1 if r > 2 else r
                # tìm vị trí trong atlas gốc
                goff[g] = sum(f['w'][:i])
            if c == ' ':
                a0[g] = sw; a1[g] = 3
        # atlas gốc giữ nguyên (đúng fontcus); goff = offset cột trong hàng
        hdr = struct.pack('<BBHHH', f['h'], baseline(f), f['tot'], len(f['bits']), 0)
        blk = hdr + bytes(raw) + bytes(a0) + bytes(a1)
        while len(blk) % 4: blk += b'\0'
        blk += struct.pack('<%dH' % N, *goff) + f['bits']
        while len(blk) % 4: blk += b'\0'
        fblocks.append(blk)
    head = 36                      # +4: u32 off_t10 (khối chuỗi T10) ở offset 32
    off_widx = head
    wi = struct.pack('<%dH' % len(widx), *widx)
    off_words = off_widx + len(wi)
    off_words = (off_words + 3) & ~3
    words_blob = bytes(wb)
    o = off_words + len(words_blob); o = (o + 3) & ~3
    offs = []
    for b in fblocks: offs.append(o); o += len(b)
    chars_off = o
    chars_len = nch * 2
    t10_off = (chars_off + chars_len + 3) & ~3
    try:
        import t10_text
        t10 = t10_text.enc(t10_text.NOTE, words)
        t10_blob = bytes([1, len(t10)]) + t10
    except Exception as e:
        raise SystemExit('T10: không mã hoá được chuỗi bằng từ điển: %r' % (e,))
    out = bytearray(b'HLVI' + struct.pack('<HHHBB', 1, len(words), nch, 3, sw))
    out += struct.pack('<5I', off_widx, off_words, *offs)
    out += struct.pack('<I', t10_off)
    out += wi
    out += b'\0' * (off_words - len(out)); out += words_blob
    out += b'\0' * (offs[0] - len(out))
    for b in fblocks: out += b
    out += struct.pack('<%dH' % nch, *[ord(c) for c in chars])
    out += b'\0' * (t10_off - len(out)); out += t10_blob
    return bytes(out), dict(words=len(words), chars=nch, sizes=[len(b) for b in fblocks], spaceW=sw)

if __name__ == '__main__':
    data, info = build(sys.argv[1])
    open(sys.argv[2], 'wb').write(data)
    print(len(data), 'byte', info)
