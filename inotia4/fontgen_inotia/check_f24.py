#!/usr/bin/env python3
"""Kiem font f24vn.bin (T7b). Thoat 0 chi khi KHONG co vi pham.

    python3 check_f24.py <f18vn.bin> <f24vn.bin> [--list]

Kiem:
  1. charset va thu tu glyph == f18vn.bin (6 glyph ve tay _ = ~ ' ' * van co); H <= 28 va <= 1,3*24.
  2. Luoi: chu hoa cao tu hang 9 den BASE=23, x-height bat dau hang 15 (do tu H, x).
  3. Moi glyph co dau Viet (thanh, mu, ap, sung, nang): lay diem dau = glyph \\ than chu (than chu = glyph chu goc cung o,
     co sung thi lay glyph o/u co sung). Moi thanh phan dau (8-lien thong) phai:
       - bbox >= 2x2 px;
       - khong co diem nao day 1 px theo ca hai phuong (moi diem thuoc doan ngang >= 2 hoac doan doc >= 2);
       - dau tren: hang duoi cung cua dau <= hang tren cung cua than (trong dai cot cua dau) - 2, tuc cach >= 1 hang;
         dau duoi: hang tren cung >= hang day + 2;
       - khoang cach Chebyshev toi moi diem than chu >= 2 (khong cham/khong lien 8-ke);
       - hai dau chong nhau (tren/duoi cung mot glyph) cach nhau >= 1 hang.
     Sung (o/u co sung): phan sung = glyph(o) \\ glyph(o) goc, bbox >= 2x2, day >= 2, noi lien voi than.
     d/D: thanh ngang = glyph(d) \\ glyph(d) goc, day >= 2 hang.
  Liet ke glyph vi pham (muc tieu = 0).
"""
import os
import sys
import unicodedata

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import f18io

CAP_TOP, X_TOP, BASE = 9, 15, 23
TONES = {0x301, 0x300, 0x309, 0x303, 0x323}
MODS = {0x302, 0x306}


def pixels(rows):
    return set((x, y) for y, r in enumerate(rows) for x, ch in enumerate(r) if ch == '#')


def comps(pts):
    pts = set(pts)
    out = []
    while pts:
        s = pts.pop()
        comp = {s}
        st = [s]
        while st:
            x, y = st.pop()
            for dx in (-1, 0, 1):
                for dy in (-1, 0, 1):
                    q = (x + dx, y + dy)
                    if q in pts:
                        pts.remove(q)
                        comp.add(q)
                        st.append(q)
        out.append(comp)
    return out


def bbox(p):
    xs = [a for a, _ in p]
    ys = [b for _, b in p]
    return min(xs), min(ys), max(xs), max(ys)


def thick_ok(comp):
    """Moi diem thuoc mot doan ngang >= 2 hoac doan doc >= 2 (khong co net 1 px theo ca hai phuong)."""
    bad = []
    for (x, y) in comp:
        h = ((x - 1, y) in comp) or ((x + 1, y) in comp)
        v = ((x, y - 1) in comp) or ((x, y + 1) in comp)
        if not (h or v):
            bad.append((x, y))
    return bad


def solid2x2(comp):
    return any((x + 1, y) in comp and (x, y + 1) in comp and (x + 1, y + 1) in comp for (x, y) in comp)


def cheb(a, b):
    return max(abs(a[0] - b[0]), abs(a[1] - b[1]))


def decompose(ch):
    if ch in 'đĐ':
        return None
    d = unicodedata.normalize('NFD', ch)
    horn = False
    mods = []
    for c in d[1:]:
        o = ord(c)
        if o == 0x31B:
            horn = True
        elif o in TONES or o in MODS:
            mods.append(o)
        else:
            return None
    if not horn and not mods:
        return None
    return d[0], horn, mods


def main(a):
    cs18, h18, _ = f18io.load(a[0])
    cs, h, gl = f18io.load(a[1])
    viol = []

    def bad(c, msg):
        viol.append((c, msg))

    if cs != cs18:
        bad('*', 'charset khac f18vn.bin (%d vs %d ky tu)' % (len(cs), len(cs18)))
    for c in '_=~’‘★':
        if c not in gl or not pixels(gl[c]):
            bad(c, 'thieu glyph ve tay')
    if h > 28 or h > 1.3 * 24:
        bad('*', 'H=%d > 28' % h)
    P = {c: pixels(gl[c]) for c in cs}
    # luoi
    for c in 'HEIZ':
        y0, y1 = bbox(P[c])[1], bbox(P[c])[3]
        if (y0, y1) != (CAP_TOP, BASE):
            bad(c, 'chu hoa khong o hang %d..%d (co %d..%d)' % (CAP_TOP, BASE, y0, y1))
    for c in 'xzvw':
        y0, y1 = bbox(P[c])[1], bbox(P[c])[3]
        if (y0, y1) != (X_TOP, BASE):
            bad(c, 'x-height khong o hang %d..%d (co %d..%d)' % (X_TOP, BASE, y0, y1))
    n_mark_glyph = 0
    n_marks = 0
    n_solid = 0
    nsolid_missing = []
    for c in cs:
        d = decompose(c)
        if d is None:
            continue
        base, horn, mods = d
        n_mark_glyph += 1
        G = P[c]
        plain = set(p for p in P[base] if p[1] >= X_TOP) if base.islower() else set(P[base])
        if horn:
            hb = {'o': 'ơ', 'u': 'ư', 'O': 'Ơ', 'U': 'Ư'}[base]
            ref = P[hb]
            # sung: phan khac glyph goc
            hp = P[hb] - P[base]
            cl = comps(hp)
            if not cl:
                bad(hb, 'khong thay sung')
            for cp in cl:
                x0, y0, x1, y1 = bbox(cp)
                if x1 - x0 + 1 < 2 or y1 - y0 + 1 < 2:
                    bad(hb, 'sung nho hon 2x2')
                if thick_ok(cp):
                    bad(hb, 'sung co net 1 px')
        elif base.islower():
            ref = set(p for p in P[base] if p[1] >= X_TOP)
        else:
            ref = set(P[base])
        if not ref <= G:
            bad(c, 'mat than chu (than khong nam tron trong glyph)')
            continue
        mk = G - ref
        # i co cham: ị giu cham (cham la thanh phan > than, kiem nhu dau)
        if horn and not mods:
            continue
        cl = comps(mk)
        bt = min(p[1] for p in plain)
        n_comp = []
        for cp in cl:
            n_marks += 1
            x0, y0, x1, y1 = bbox(cp)
            if x1 - x0 + 1 < 2 or y1 - y0 + 1 < 2:
                bad(c, 'dau %s nho hon 2x2' % (bbox(cp),))
            tb = thick_ok(cp)
            if tb:
                bad(c, 'dau co net 1 px tai %s' % (tb[:3],))
            if solid2x2(cp):
                n_solid += 1
            else:
                nsolid_missing.append(c)
            near = min(cheb(p, q) for p in cp for q in G - mk) if (G - mk) else 99
            if near < 2:
                bad(c, 'dau cham/lien ke than chu (khoang cach %d)' % near)
            if y1 < bt:                              # dau tren
                cols = [q for q in plain if x0 <= q[0] <= x1]
                top = min(q[1] for q in cols) if cols else bt
                if y1 > top - 2:
                    bad(c, 'dau tren cach than <1 hang (day dau %d, dinh than %d)' % (y1, top))
            elif y0 > BASE:                          # dau duoi
                if y0 < BASE + 2:
                    bad(c, 'dau duoi cach day <1 hang')
            else:
                bad(c, 'dau nam giua than chu (hang %d..%d)' % (y0, y1))
            n_comp.append((y0, y1))
        up = sorted([t for t in n_comp if t[1] < bt])
        for (a0, a1), (b0, b1) in zip(up, up[1:]):
            if a1 > b0 - 2:
                bad(c, 'hai dau chong cach nhau <1 hang (%s,%s)' % ((a0, a1), (b0, b1)))
    # d / D
    for c, b in (('đ', 'd'), ('Đ', 'D')):
        bar = P[c] - P[b]                  # hai manh trai/phai cua than noi nhau qua than: xet chung
        if not bar:
            bad(c, 'thieu thanh ngang')
            continue
        x0, y0, x1, y1 = bbox(bar)
        if y1 - y0 + 1 < 2 or x1 - x0 + 1 < 2:
            bad(c, 'thanh ngang mong <2 px')
        if thick_ok(bar):
            bad(c, 'thanh ngang co net 1 px')
    print('f24vn: %d glyph, H=%d; glyph co dau/sung: %d; thanh phan dau: %d (co khoi 2x2 dac: %d)'
          % (len(cs), h, n_mark_glyph, n_marks, n_solid))
    print('VI PHAM: %d' % len(viol))
    for c, m in viol[:60]:
        print('  %r: %s' % (c, m))
    if '--list' in a:
        print('thanh phan dau KHONG co khoi 2x2 dac (chi thong tin): %s' % ''.join(sorted(set(nsolid_missing))))
    return 1 if viol else 0


if __name__ == '__main__':
    sys.exit(main(sys.argv[1:]))
