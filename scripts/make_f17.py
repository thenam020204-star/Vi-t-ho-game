#!/usr/bin/env python3
"""Sinh f17.bin (co chan, net f18) bang dung thuat toan nen cua fontgen/build_fonts.py.
17px = 18px bo 1 hang o vung 'tren x' (dau 5 | tren-x 3 | x-height 6 | duoi 3):
giu nguyen x-height va cho dau nhu f18; chu hoa thap hon 1 hang.
python3 scripts/make_f17.py <tools_dir> <out.bin> [--sheet]"""
import sys, os
tools = os.path.abspath(sys.argv[1])
sys.path.insert(0, os.path.join(tools, 'fontgen'))
sys.path.insert(0, os.path.join(tools, 'pixeltext-kit'))
import build_fonts as bf
bf.KEEP[17] = dict(A=5, T=3, X=6, D=3)
bf.LAYOUT[17] = dict(cap=5, x=8, base=13)
charset, h, widths, rows, glyphs = bf.build('serif', 17)
assert h == 17
data = bf.container.encode(charset, h, widths, rows)
open(sys.argv[2], 'wb').write(data)
print('f17 serif:', len(charset), 'glyph,', len(data), 'byte')
if '--sheet' in sys.argv:
    print(bf.sheet(charset, glyphs))
