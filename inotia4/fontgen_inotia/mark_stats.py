#!/usr/bin/env python3
"""Do do ro cua dau thanh sau khi phong GPU x1,6875 bilinear (mo phong GL_LINEAR). Dung cho docs/font_lon.md.

    python3 -I mark_stats.py <f18vn.bin> <f24vn.bin>

f18: nhu TEST7, size 24 -> q=24 mau 16 (x1,5, nearest). f24: size 24 -> 1:1. Moi thanh phan dau (8-lien thong cua
glyph co dau tru glyph chu goc, bo ho 'i' vi chu goc co cham) do: be ngang nho nhat (px dem), do dam dinh sau bilinear
(0..255) va dien tich vung >= 50% (px dem^2).
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import f18io
import numpy as np
from PIL import Image
from check_f24 import comps, decompose

Z = 1.6875


def sc(v, q, d):
    return (v * q) // d


def render(gl, h, c, q, den):
    g = gl[c]
    w = len(g[0])
    buf = np.zeros((sc(h, q, den) + 2, sc(20, q, den) + 4), np.uint8)
    for r in range(h):
        for x in range(w):
            if g[r][x] == '#':
                buf[sc(r, q, den):sc(r + 1, q, den), sc(x, q, den):sc(x + 1, q, den)] = 255
    return buf


def stats(path, q, den):
    cs, h, gl = f18io.load(path)
    res = []
    for c in cs:
        d = decompose(c)
        if d is None or d[0] == 'i' or d[0] not in gl:
            continue
        G = render(gl, h, c, q, den)
        B = render(gl, h, d[0], q, den)
        pts = set(zip(*np.nonzero((G > 0) & (B == 0))))
        big = np.array(Image.fromarray(G).resize((int(G.shape[1] * Z + .5), int(G.shape[0] * Z + .5)), Image.BILINEAR))
        for cp in comps(pts):
            ys = [p[0] for p in cp]
            xs = [p[1] for p in cp]
            y0, y1, x0, x1 = min(ys), max(ys), min(xs), max(xs)
            sub = big[int(y0 * Z):int((y1 + 1) * Z) + 2, int(x0 * Z):int((x1 + 1) * Z) + 2]
            res.append((c, min(y1 - y0 + 1, x1 - x0 + 1), int(sub.max()), int((sub >= 128).sum() / Z / Z + .5)))
    return res


def main(a):
    for name, path, q, den in (('f18 x1,5 (TEST7)', a[0], 24, 16), ('f24vn 1:1', a[1], 24, 24)):
        r = stats(path, q, den)
        mn = np.array([x[1] for x in r])
        pk = np.array([x[2] for x in r])
        ar = np.array([x[3] for x in r])
        print('%s: %d thanh phan dau' % (name, len(r)))
        print('  be ngang nho nhat (px dem): min %d, trung vi %d, max %d' % (mn.min(), np.median(mn), mn.max()))
        print('  do dam dinh sau bilinear (0-255): min %d, trung vi %d' % (pk.min(), np.median(pk)))
        print('  dien tich >=50%% (px dem^2): min %d, trung vi %d' % (ar.min(), np.median(ar)))
        print('  be ngang 1 px: %d; do dam dinh < 200: %d' % ((mn == 1).sum(), (pk < 200).sum()))


if __name__ == '__main__':
    main(sys.argv[1:])
