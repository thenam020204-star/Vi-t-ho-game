"""T5: Jeju + 134 chu Viet (composite) -> work/font/JejuHallasan-VI.ttf.
   python3 -I tools/build_font.py [out.ttf]
Giu nguyen byte moi glyph cu (glyf khong bi decompile/compile lai), metrics doc, hinting, kern; chi:
  - noi them glyph (cuoi glyph order): idotless.jeju, cac glyph dau (simple), 134 composite uniXXXX
  - cmap (3,1): +134 ky tu, U+0111 tro sang composite moi (D3a)
  - hmtx / hhea(numberOfHMetrics, minLSB, minRSB, xMaxExtent) / maxp / head bbox theo glyph moi."""
import os
import sys

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.abspath(os.path.join(HERE, '..'))
sys.path.insert(0, os.path.join(ROOT, 'font'))

from fontTools.ttLib import TTFont
from fontTools.pens.ttGlyphPen import TTGlyphPen

import marks
import extra
import layout

SRC = os.path.join(ROOT, 'work', 'font', 'JejuHallasan.ttf')
DST = os.path.join(ROOT, 'work', 'font', 'JejuHallasan-VI.ttf')


class _AnyGS(dict):
    def __contains__(self, k):
        return True


def mark_name(m, v):
    if m in ('dotbelow', 'dbar', 'Dbar'):
        v = 'R'                         # chi co bien the R
    return 'mk_%s_%s' % (m, v), v


def pts_of(g):
    return [tuple(p) for p in g.coordinates]


def main(dst=DST):
    f = TTFont(SRC, recalcBBoxes=False)           # khong ep tinh lai bbox -> glyph cu giu nguyen byte
    ref = TTFont(SRC)                             # ban doc de lay toa do than chu (decompile tren ban nay)
    glyf, hmtx = f['glyf'], f['hmtx']
    rglyf = ref['glyf']
    cmap = f.getBestCmap()
    order = f.getGlyphOrder()[:]
    n_old = len(order)
    new_glyphs, new_metrics = {}, {}

    # 1) than i khong cham
    idg = extra.make_idotless(ref)
    new_glyphs[extra.IDOTLESS] = idg
    new_metrics[extra.IDOTLESS] = (hmtx['i'][0], idg.xMin)
    body_pts = {extra.IDOTLESS: (pts_of(idg), len(idg.endPtsOfContours))}

    # 2) glyph dau (simple), chi tao cac bien the thuc su dung
    layout_t = layout.table()
    used = set()
    for ch, (body, ms) in layout_t.items():
        for (m, v, dx, dy) in ms:
            used.add(mark_name(m, v))
    mark_pts = {}
    for gname, v in sorted(used):
        m = gname[3:-2]
        pen = TTGlyphPen(None)
        marks.draw(pen, m, v)
        g = pen.glyph()
        g.recalcBounds(None)
        new_glyphs[gname] = g
        new_metrics[gname] = (0, g.xMin)
        mark_pts[gname] = (pts_of(g), len(g.endPtsOfContours))

    # 3) 134 composite
    new_cmap = {}
    comp_stats = []
    for ch, (body, ms) in layout_t.items():
        gname = 'uni%04X' % ord(ch)
        assert gname not in order
        bname = body if body == extra.IDOTLESS else cmap[ord(body)]
        pen = TTGlyphPen(_AnyGS())
        pen.addComponent(bname, (1, 0, 0, 1, 0, 0))
        pts = list(body_pts[bname][0]) if bname in body_pts else None
        ncont = body_pts[bname][1] if bname in body_pts else None
        if pts is None:
            rg = rglyf[bname]
            pts = [tuple(p) for p in rg.getCoordinates(rglyf)[0]]
            ncont = len(rg.getCoordinates(rglyf)[1])
            body_pts[bname] = (pts, ncont)
        allp = list(pts)
        nc = ncont
        for (m, v, dx, dy) in ms:
            mn, _ = mark_name(m, v)
            pen.addComponent(mn, (1, 0, 0, 1, round(dx), round(dy)))
            allp += [(x + round(dx), y + round(dy)) for x, y in mark_pts[mn][0]]
            nc += mark_pts[mn][1]
        g = pen.glyph()
        g.xMin = min(p[0] for p in allp)
        g.xMax = max(p[0] for p in allp)
        g.yMin = min(p[1] for p in allp)
        g.yMax = max(p[1] for p in allp)
        adv = new_metrics[bname][0] if bname in new_metrics else hmtx[bname][0]
        new_glyphs[gname] = g
        new_metrics[gname] = (adv, g.xMin)
        new_cmap[ord(ch)] = gname
        comp_stats.append((len(g.components), len(allp), nc))

    # 4) ghi vao font
    for name, g in new_glyphs.items():
        order.append(name)
        glyf.glyphs[name] = g
        hmtx.metrics[name] = new_metrics[name]
    f.setGlyphOrder(order)
    glyf.glyphOrder = order
    for t in f['cmap'].tables:
        if t.isUnicode():
            t.cmap.update(new_cmap)
    # maxp
    mp = f['maxp']
    mp.numGlyphs = len(order)
    mp.maxPoints = max(mp.maxPoints, max(len(p[0]) for p in mark_pts.values()), len(pts_of(idg)))
    mp.maxContours = max(mp.maxContours, max(c for _, c in mark_pts.values()), len(idg.endPtsOfContours))
    mp.maxComponentElements = max(mp.maxComponentElements, max(s[0] for s in comp_stats))
    mp.maxComponentDepth = max(mp.maxComponentDepth, 1)
    mp.maxCompositePoints = max(mp.maxCompositePoints, max(s[1] for s in comp_stats))
    mp.maxCompositeContours = max(mp.maxCompositeContours, max(s[2] for s in comp_stats))
    # head bbox + hhea extents
    h, hh = f['head'], f['hhea']
    for g in new_glyphs.values():
        h.xMin, h.yMin = min(h.xMin, g.xMin), min(h.yMin, g.yMin)
        h.xMax, h.yMax = max(h.xMax, g.xMax), max(h.yMax, g.yMax)
    for name in new_glyphs:
        adv, lsb = new_metrics[name]
        if name.startswith('mk_'):
            continue                     # glyph dau chi la thanh phan, khong dung rieng
        g = new_glyphs[name]
        hh.minLeftSideBearing = min(hh.minLeftSideBearing, lsb)
        hh.minRightSideBearing = min(hh.minRightSideBearing, adv - g.xMax)
        hh.xMaxExtent = max(hh.xMaxExtent, lsb + (g.xMax - g.xMin))
    f.save(dst)
    print('saved', dst, os.path.getsize(dst), 'bytes;', len(order) - n_old, 'glyph moi,', len(new_cmap), 'cmap moi')
    return dst


if __name__ == '__main__':
    main(*sys.argv[1:])
