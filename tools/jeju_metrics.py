"""Do so lieu JejuHallasan (stem, x-height, cap, bbox).  python3 -I tools/jeju_metrics.py [ttf] > docs/jeju_metrics.txt"""
import sys, os, json
from fontTools.ttLib import TTFont
from fontTools.pens.boundsPen import BoundsPen
from fontTools.pens.recordingPen import DecomposingRecordingPen
from fontTools.pens.pointPen import PointToSegmentPen

ROOT = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..')
TTF = os.path.join(ROOT, 'work', 'font', 'JejuHallasan.ttf')


def load(path=TTF):
    f = TTFont(path, lazy=True)
    return f, f.getBestCmap(), f.getGlyphSet()


def bounds(gs, name):
    bp = BoundsPen(gs); gs[name].draw(bp); return bp.bounds


def scan(gs, name, y, steps=1):
    """Cac doan x (xmin,xmax) ma duong ngang y cat qua vung to cua glyph (non-zero) - dung polyline xap xi."""
    from fontTools.pens.basePen import BasePen
    segs = []

    class P(BasePen):
        def __init__(s, gs): super().__init__(gs); s.cur = None; s.pts = []
        def _moveTo(s, p): s.cur = p; s.start = p
        def _lineTo(s, p): s.edge(s.cur, p); s.cur = p
        def _curveToOne(s, a, b, c):
            p0 = s.cur
            for i in range(1, 17):
                t = i / 16; u = 1 - t
                q = (u**3 * p0[0] + 3 * u * u * t * a[0] + 3 * u * t * t * b[0] + t**3 * c[0],
                     u**3 * p0[1] + 3 * u * u * t * a[1] + 3 * u * t * t * b[1] + t**3 * c[1])
                s.edge(s.cur, q); s.cur = q
        def _qCurveToOne(s, a, c):
            p0 = s.cur
            for i in range(1, 17):
                t = i / 16; u = 1 - t
                q = (u * u * p0[0] + 2 * u * t * a[0] + t * t * c[0], u * u * p0[1] + 2 * u * t * a[1] + t * t * c[1])
                s.edge(s.cur, q); s.cur = q
        def _closePath(s): s.edge(s.cur, s.start)
        def edge(s, a, b):
            if (a[1] - y) * (b[1] - y) < 0 or (a[1] == y) != (b[1] == y) and min(a[1], b[1]) < y <= max(a[1], b[1]):
                x = a[0] + (y - a[1]) * (b[0] - a[0]) / (b[1] - a[1]); d = 1 if b[1] > a[1] else -1
                s.pts.append((x, d))
    p = P(gs); gs[name].draw(p)
    pts = sorted(p.pts); out = []; w = 0; x0 = None
    for x, d in pts:
        if w == 0: x0 = x
        w += d
        if w == 0: out.append((round(x0), round(x)))
    return out


def main():
    path = sys.argv[1] if len(sys.argv) > 1 else TTF
    f, cmap, gs = load(path)
    head, hhea, os2 = f['head'], f['hhea'], f['OS/2']
    out = {}
    print('UPM', head.unitsPerEm, 'hhea asc/desc/gap', hhea.ascent, hhea.descent, hhea.lineGap,
          'OS2 typo', os2.sTypoAscender, os2.sTypoDescender, os2.sTypoLineGap, 'win', os2.usWinAscent, os2.usWinDescent,
          'sxHeight', getattr(os2, 'sxHeight', None), 'sCapHeight', getattr(os2, 'sCapHeight', None))
    out['upm'] = head.unitsPerEm
    print('\nglyph bbox / advance')
    for ch in 'aeouyıilxzAEIOUYÐĐđDHNMVW':
        if ord(ch) not in cmap:
            print(ch, 'khong co'); continue
        n = cmap[ord(ch)]; b = bounds(gs, n); adv = f['hmtx'][n][0]
        out[ch] = dict(name=n, bbox=b, adv=adv)
        print('%s U+%04X %-12s bbox=%s adv=%d' % (ch, ord(ch), n, b, adv))
    # stem: l / i / I / H
    print('\nstem (rong net doc o giua chieu cao):')
    for ch, yy in (('l', 300), ('ı', 250), ('I', 330), ('H', 330), ('i', 250), ('u', 250), ('o', 250), ('e', 250)):
        n = cmap[ord(ch)]; print(ch, yy, scan(gs, n, yy))
    # x-height / cap qua nhieu ky tu
    xs = {c: bounds(gs, cmap[ord(c)]) for c in 'xzvwu'}
    print('\nx-height flat (x z v w u yMax):', {c: b[3] for c, b in xs.items()})
    ov = {c: bounds(gs, cmap[ord(c)]) for c in 'aeosc'}
    print('x-height round overshoot (a e o s c yMax / yMin):', {c: (b[3], b[1]) for c, b in ov.items()})
    caps = {c: bounds(gs, cmap[ord(c)]) for c in 'EHIZ'}
    print('cap flat (E H I Z yMax):', {c: b[3] for c, b in caps.items()})
    capo = {c: bounds(gs, cmap[ord(c)]) for c in 'OCSGU'}
    print('cap round (O C S G U yMax/yMin):', {c: (b[3], b[1]) for c, b in capo.items()})
    print('ascender (b d l h k yMax):', {c: bounds(gs, cmap[ord(c)])[3] for c in 'bdlhk'})
    print('descender (p q g y yMin):', {c: bounds(gs, cmap[ord(c)])[1] for c in 'pqgy'})
    print('dau co san: ', {hex(c): cmap[c] for c in (0x300, 0x301, 0x303, 0x309, 0x323, 0x2C6, 0x2DC, 0x60, 0xB4) if c in cmap})
    # diem gan mong (horn) o/u/O/U: mep phai than o do cao ymax-85
    out['horn_attach'] = {}
    for ch in 'ouOU':
        n = cmap[ord(ch)]; b = bounds(gs, n); ya = round(b[3] - 85)
        segs = scan(gs, n, ya); out['horn_attach'][ch] = [segs[-1][1], ya]
    print('horn attach (x mep phai, y):', out['horn_attach'])
    # y: vung trong duoi baseline de dat dau nang
    n = cmap[ord('y')]
    out['y_tail'] = {str(yy): scan(gs, n, yy) for yy in (-20, -60, -100, -140)}
    print('y duoi:', out['y_tail'])
    json.dump(out, open(os.path.join(ROOT, 'docs', 'jeju_metrics.json'), 'w'), indent=1, ensure_ascii=False)


if __name__ == '__main__':
    main()
