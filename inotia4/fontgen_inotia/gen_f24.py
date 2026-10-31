#!/usr/bin/env python3
"""Sinh font/f24vn.bin: ve lai font f18 o co lon that (thiet ke cho size 24, o chu 1:1).

Y tuong (xem docs/font_lon.md):
  * Than chu (A-Z a-z 0-9, dau cau, d/D, o/u co sung) lay tu f18 bang "but 2x2": moi diem sang cua f18 thanh
    khoi 2x2, toa do nhan he so ~1,4 (ngang) / ~1,44 (doc) => net day deu 2 px, cao chu hoa 15 hang, x-height 9 hang.
  * Dau thanh / dau mu / dau ap duoc VE LAI THEO LUOI 24 (khong phong f18) va ghep len than chu theo quy tac:
    moi thanh phan dau >= 2x2 px, net day >= 2, cach than chu >= 1 hang, hai dau chong cach nhau >= 1 hang.
  * Charset, thu tu glyph va 6 glyph ve tay (_ = ~ ' ' *) giu NGUYEN theo f18vn.bin.

    python3 gen_f24.py <f18vn.bin> <out f24vn.bin>
"""
import os
import sys
import unicodedata

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import f18io

# ---------------------------------------------------------------- luoi
H = 28                     # chieu cao o chu
BASE = 23                  # hang day chu (hang cuoi cua than chu)
CAP_TOP = 9                # hang tren cua chu hoa
X_TOP = 15                 # hang tren cua chu thuong (x-height)
ADV = 11                   # be rong o chu (chu rong 7 trong f18)
ADV_SPACE = 8              # dau cach (5 trong f18)
GAP_ROWS = 1               # khoang trong toi thieu giua dau va than

XM = [0, 1, 3, 4, 6, 7, 8, 10, 11, 13]   # cot f18 -> cot dich (toa do goc trai khoi 2x2)


def ym(y):
    """Hang f18 -> hang dich (goc tren khoi 2x2): hang 5 -> 9, hang 14 -> 22, tuyen tinh he so 13/9."""
    return int(CAP_TOP + (y - 5) * 13 / 9 + 0.5) if y >= 5 else int(CAP_TOP + (y - 5) * 13 / 9 - 0.5)


def block_map(rows18, drop_rows=()):
    """Bitmap f18 (list str) -> tap (x, y) diem sang tren luoi dich."""
    pts = set()
    for y, r in enumerate(rows18):
        if y in drop_rows:
            continue
        for x, ch in enumerate(r):
            if ch == '#':
                X, Y = XM[x], ym(y)
                for dy in (0, 1):
                    for dx in (0, 1):
                        pts.add((X + dx, Y + dy))
    return pts


# ---------------------------------------------------------------- dau (ve tay tren luoi 24)
# Moi dau: (cac hang, cot tam). Cot tam = cot (tinh tu 0 cua hinh) nam tren tam than chu.
MARKS = {
    'acute': ([".##", ".##", "##.", "##."], 1),
    'grave': (["##.", "##.", ".##", ".##"], 1),
    'hook': (["###.", "..##", "..##", ".##."], 1),
    'tilde': (["..###..##", ".##.####.", "##...##.."], 4),
    'circ': (["..###..", ".##.##.", "##...##"], 3),
    'breve': (["##...##", ".##.##.", "..###.."], 3),
    'dot': (["##", "##"], 0),     # cot tam = cot trai cua cap
}


def stamp(pts, mark, cx, bottom):
    rows, mc = MARKS[mark]
    if cx - mc < 0:
        cx = mc                                  # khong de dau tran ra ngoai o ben trai
    top = bottom - len(rows) + 1
    for j, r in enumerate(rows):
        for i, ch in enumerate(r):
            if ch == '#':
                pts.add((cx - mc + i, top + j))
    return top


TONES = {0x301: 'acute', 0x300: 'grave', 0x309: 'hook', 0x303: 'tilde', 0x323: 'dot'}
MODS = {0x302: 'circ', 0x306: 'breve'}


def decompose(ch):
    """-> (chu_goc, horn, mod, tone) hoac None neu khong co dau."""
    if ch in 'đĐ':
        return None
    d = unicodedata.normalize('NFD', ch)
    base = d[0]
    horn = False
    mod = tone = None
    for c in d[1:]:
        o = ord(c)
        if o == 0x31B:
            horn = True
        elif o in MODS:
            mod = MODS[o]
        elif o in TONES:
            tone = TONES[o]
        else:
            return None
    if not horn and mod is None and tone is None:
        return None
    return base, horn, mod, tone


def bbox(pts):
    xs = [p[0] for p in pts]
    ys = [p[1] for p in pts]
    return min(xs), min(ys), max(xs), max(ys)


def build(gl18, charset):
    """-> {ky tu: (adv, set diem)}"""
    out = {}
    plain = {}
    for c in charset:
        if c not in gl18:
            raise SystemExit('thieu glyph f18 %r' % c)
    for c in charset:
        if decompose(c) is not None and c not in 'ơưƠƯ':
            continue
        if c == ' ':
            out[c] = (ADV_SPACE, set())
            continue
        pts = block_map(gl18[c])
        w18 = len(gl18[c][0])
        adv = ADV if w18 == 7 else int(w18 * 1.5 + 2.5)
        adv = max(adv, bbox(pts)[2] + 3 if pts else adv)
        out[c] = (adv, pts)
    # chu goc khong dau cho viec ghep (i khong cham)
    for c in 'aeiouyAEIOUY':
        plain[c] = out[c][1]
    dotless = set(p for p in plain['i'] if p[1] >= X_TOP - 1)   # bo cham cua i
    plain['i'] = dotless
    for c in charset:
        d = decompose(c)
        if d is None:
            continue
        base, horn, mod, tone = d
        if c in 'ơưƠƯ':
            continue
        lower = base.islower()
        if horn:
            hc = {'o': 'ơ', 'u': 'ư', 'O': 'Ơ', 'U': 'Ư'}[base]
            body = out[hc][1]
        elif base == 'i' and tone == 'dot':
            body = out['i'][1]          # i.dot-duoi giu cham tren
        else:
            body = plain[base]
        pts = set(body)
        ref = plain[base]
        x0, y0, x1, y1 = bbox(ref)
        cx = (x0 + x1) // 2
        top = (CAP_TOP if base.isupper() else X_TOP)
        bottom = top - 1 - GAP_ROWS          # hang duoi cung cua dau tren
        if mod:
            mtop = stamp(pts, mod, cx, bottom)
            bottom = mtop - 1 - GAP_ROWS
        if tone and tone != 'dot':
            stamp(pts, tone, cx, bottom)
        if tone == 'dot':
            stamp(pts, 'dot', cx if base not in 'y' else cx, BASE + 3)
        adv = max(ADV, bbox(pts)[2] + 3)
        out[c] = (adv, pts)
    return out


def to_rows(adv, pts):
    rows = [['.'] * adv for _ in range(H)]
    for x, y in pts:
        if 0 <= y < H and 0 <= x < adv:
            rows[y][x] = '#'
        else:
            raise SystemExit('diem ngoai o: %r adv=%d' % ((x, y), adv))
    return [''.join(r) for r in rows]


def main(a):
    cs, h, gl = f18io.load(a[0])
    assert h == 18
    g = build(gl, cs)
    glyphs = {}
    for c in cs:
        adv, pts = g[c]
        glyphs[c] = to_rows(adv, pts)
    f18io.save(a[1], cs, H, glyphs)
    print('f24vn: %d ky tu, H=%d BASE=%d' % (len(cs), H, BASE))


if __name__ == '__main__':
    main(sys.argv[1:])
