"""Thu nghiem: dung glyph pixel f18.bin NGUYEN BAN (moi o = hinh vuong) cho font game.
Bien the:
  A: chi 134 chu Viet lay tu f18, chu khac giu Jeju  -> work/font_px/JejuHallasan-f18A.ttf
  B: toan bo 227 ky tu f18 (Latin, so, dau cau, chu Viet), giu chu Han/Han Quoc cua Jeju -> ...-f18B.ttf
  C: nhu B nhung lam dam: moi o pixel to them 1 o sang phai (bold kieu font pixel), rong +1 -> ...-f18C.ttf
Khong doi metrics doc, khong doi glyph cu (chi them glyph moi va tro cmap sang glyph moi).
Chay: python3 -I tools/build_font_f18px.py [--px 80]
"""
import os, sys, argparse
from collections import defaultdict
from fontTools.ttLib import TTFont
from fontTools.pens.ttGlyphPen import TTGlyphPen

ROOT = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..')
sys.path.insert(0, os.path.join(ROOT, 'tools'))
from f18 import F18  # noqa: E402

SRC = os.path.join(ROOT, 'work', 'font', 'JejuHallasan.ttf')
OUT = os.path.join(ROOT, 'work', 'font_px')
BASE_ROW = 14  # day hang 14 = baseline cua f18
VI = ("ăâđêôơưĂÂĐÊÔƠƯáàảãạắằẳẵặấầẩẫậéèẻẽẹếềểễệíìỉĩịóòỏõọốồổỗộớờởỡợúùủũụứừửữựýỳỷỹỵ"
      "ÁÀẢÃẠẮẰẲẴẶẤẦẨẪẬÉÈẺẼẸẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌỐỒỔỖỘỚỜỞỠỢÚÙỦŨỤỨỪỬỮỰÝỲỶỸỴ")


def contours(bitmap):
    """Bitmap (hang tren xuong) -> danh sach contour (toa do o, y huong len), chieu kim dong ho cho vien ngoai."""
    h = len(bitmap)
    edges = {}
    for r, row in enumerate(bitmap):
        for c, v in enumerate(row):
            if not v:
                continue
            y0 = BASE_ROW - r  # day o
            y1 = y0 + 1
            # o vuong theo chieu kim dong ho (y huong len): (c,y0)->(c,y1)->(c+1,y1)->(c+1,y0)
            for a, b in (((c, y0), (c, y1)), ((c, y1), (c + 1, y1)),
                         ((c + 1, y1), (c + 1, y0)), ((c + 1, y0), (c, y0))):
                if (b, a) in edges:
                    del edges[(b, a)]  # canh chung giua 2 o -> huy
                else:
                    edges[(a, b)] = True
    out = defaultdict(list)
    for a, b in edges:
        out[a].append(b)
    loops = []
    while out:
        start = next(iter(out))
        loop = [start]
        cur = start
        prev_dir = None
        while True:
            nxts = out[cur]
            if len(nxts) > 1 and prev_dir is not None:
                # dinh cheo: uu tien re phai (giu o cheo tach rieng)
                def turn(n):
                    d = (n[0] - cur[0], n[1] - cur[1])
                    return prev_dir[0] * d[1] - prev_dir[1] * d[0]
                nxts.sort(key=turn)
            nxt = nxts.pop(0)
            if not nxts:
                del out[cur]
            prev_dir = (nxt[0] - cur[0], nxt[1] - cur[1])
            if nxt == start:
                break
            loop.append(nxt)
            cur = nxt
        # bo diem thang hang
        simp = []
        n = len(loop)
        for i in range(n):
            p0, p1, p2 = loop[i - 1], loop[i], loop[(i + 1) % n]
            if (p1[0] - p0[0]) * (p2[1] - p1[1]) - (p1[1] - p0[1]) * (p2[0] - p1[0]) != 0:
                simp.append(p1)
        loops.append(simp)
    return loops


def build(variant, px):
    f = F18()
    font = TTFont(SRC)
    glyf, hmtx = font['glyf'], font['hmtx']
    order = font.getGlyphOrder()
    chars = [ch for ch in f.charset if ch in VI] if variant == 'A' else list(f.charset)
    bold = variant == 'C'
    cmap_tables = [t for t in font['cmap'].tables if t.isUnicode()]
    added = []
    for ch in chars:
        name = 'f18px.%04X' % ord(ch)
        pen = TTGlyphPen(None)
        bm = f.glyph(ch)
        if bold:
            bm = [[1 if (v or (c > 0 and row[c - 1])) else 0 for c, v in enumerate(row + [0])] for row in bm]
        for loop in contours(bm):
            pen.moveTo((loop[0][0] * px, loop[0][1] * px))
            for p in loop[1:]:
                pen.lineTo((p[0] * px, p[1] * px))
            pen.closePath()
        g = pen.glyph()
        g.recalcBounds(glyf)
        order.append(name)
        glyf.glyphs[name] = g
        hmtx.metrics[name] = ((f.width(ch) + bold) * px, g.xMin if g.numberOfContours else 0)
        for t in cmap_tables:
            if t.format in (4, 12) or ord(ch) <= 0xFFFF:
                t.cmap[ord(ch)] = name
        added.append(ch)
    font.setGlyphOrder(order)
    font['maxp'].numGlyphs = len(order)
    if 'post' in font and font['post'].formatType == 2.0:
        font['post'].extraNames = []
    os.makedirs(OUT, exist_ok=True)
    dst = os.path.join(OUT, 'JejuHallasan-f18%s.ttf' % variant)
    font.save(dst)
    TTFont(dst)['glyf']  # mo lai kiem tra
    return dst, len(added)


if __name__ == '__main__':
    ap = argparse.ArgumentParser()
    ap.add_argument('--px', type=int, default=80, help='don vi font cho 1 o pixel (UPM 1000)')
    a = ap.parse_args()
    for v in 'ABC':
        dst, n = build(v, a.px)
        print(v, dst, n, 'glyph', os.path.getsize(dst), 'byte')
