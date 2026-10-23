"""Glyph bo tro (khong phai dau): than 'i' bo cham tu Jeju + net gach ngang cho d/D (vector net co)."""
import copy
import math
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import marks
from marks import _arc_pts, _rnd

IDOTLESS = 'idotless.jeju'
IDOTLESS_BBOX = (95, 17, 264, 497)      # = contour than cua 'i' Jeju (contour 1), do bang tools (fontTools)


def make_idotless(font):
    """Glyph = 'i' cua Jeju bo contour cham (contour co yMin cao nhat), khong instruction. Tra ve (glyph, advance, lsb)."""
    from fontTools.ttLib.tables import ttProgram
    glyf = font['glyf']
    src = glyf['i']
    coords, ends, flags = src.getCoordinates(glyf)
    spans, s = [], 0
    for e in ends:
        spans.append((s, e))
        s = e + 1
    ymin = [min(p[1] for p in coords[a:b + 1]) for a, b in spans]
    keep = [k for k in range(len(spans)) if k != ymin.index(max(ymin))]
    g = copy.deepcopy(src)
    pts, fl, en = [], [], []
    for k in keep:
        a, b = spans[k]
        pts += [tuple(p) for p in coords[a:b + 1]]
        fl += list(flags[a:b + 1])
        en.append(len(pts) - 1)
    from fontTools.ttLib.tables._g_l_y_f import GlyphCoordinates
    g.coordinates = GlyphCoordinates(pts)
    g.flags = type(src.flags)([f & 1 for f in fl]) if hasattr(src, 'flags') else fl
    g.endPtsOfContours = en
    g.numberOfContours = len(en)
    g.program = ttProgram.Program()
    g.program.fromBytecode(b'')
    g.recalcBounds(glyf)
    return g


def capsule(x0, x1, yc, r0, r1, tilt=0):
    """Net gach ngang net co: dau trai ban kinh r0, dau phai r1 (to hon), nghieng len phai `tilt`."""
    yl, yr = yc - tilt / 2.0, yc + tilt / 2.0
    cr = (x1 - r1, yr)
    cl = (x0 + r0, yl)
    right = _arc_pts(cr, r1, -90, 90)
    left = _arc_pts(cl, r0, 90, 270)
    return _rnd(right + left[:-1] + [(left[-1][0], left[-1][1])])


# f18: d -> gach cot 3..6 (x 240..560) o hang 6 (y ~ 600); D -> gach cot 0..3 (x 20..380) o hang 9 (y ~ 380)
DBAR = capsule(232, 580, 598, 32, 42, tilt=14)
UDBAR = capsule(0, 412, 378, 32, 42, tilt=10)
marks.MARKS['dbar'] = {'R': [DBAR]}
marks.MARKS['Dbar'] = {'R': [UDBAR]}
