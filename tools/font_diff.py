"""So sanh JejuHallasan.ttf (goc) voi JejuHallasan-VI.ttf: glyph/cmap them/doi, glyph cu byte-giong?
   python3 -I tools/font_diff.py [goc.ttf] [moi.ttf]"""
import os
import struct
import sys

from fontTools.ttLib import TTFont

ROOT = os.path.abspath(os.path.join(os.path.dirname(os.path.abspath(__file__)), '..'))
A = sys.argv[1] if len(sys.argv) > 1 and sys.argv[1] != '-v' else os.path.join(ROOT, 'work', 'font', 'JejuHallasan.ttf')
B = sys.argv[2] if len(sys.argv) > 2 and sys.argv[2] != '-v' else os.path.join(ROOT, 'work', 'font', 'JejuHallasan-VI.ttf')


def slices(f):
    n = f['maxp'].numGlyphs
    loca = f.reader['loca']
    glyf = f.reader['glyf']
    if f['head'].indexToLocFormat:
        offs = struct.unpack('>%dL' % (n + 1), loca[:4 * (n + 1)])
    else:
        offs = [o * 2 for o in struct.unpack('>%dH' % (n + 1), loca[:2 * (n + 1)])]
    return [glyf[offs[i]:offs[i + 1]] for i in range(n)]


def main():
    fa, fb = TTFont(A, lazy=True), TTFont(B, lazy=True)
    print('size', os.path.getsize(A), '->', os.path.getsize(B), '(+%d)' % (os.path.getsize(B) - os.path.getsize(A)))
    na, nb = fa['maxp'].numGlyphs, fb['maxp'].numGlyphs
    print('numGlyphs', na, '->', nb)
    # bang
    print('-- bang (byte-giong / khac)')
    for t in sorted(set(fa.reader.tables) | set(fb.reader.tables)):
        if t in fa.reader.tables and t in fb.reader.tables:
            same = fa.reader[t] == fb.reader[t]
            print('  %-5s %s' % (t, 'giong' if same else 'KHAC (%d -> %d byte)' % (len(fa.reader[t]), len(fb.reader[t]))))
        else:
            print('  %-5s chi co o %s' % (t, 'goc' if t in fa.reader.tables else 'moi'))
    # glyph cu
    sa, sb = slices(fa), slices(fb)
    changed = [i for i in range(na) if sa[i] != sb[i]]
    print('-- glyph cu thay doi (outline/instruction):', len(changed), changed[:10])
    # hmtx
    ha, hb = fa['hmtx'].metrics, fb['hmtx'].metrics
    oa = fa.getGlyphOrder()
    ob = fb.getGlyphOrder()
    hch = [i for i in range(na) if ha[oa[i]] != hb[ob[i]]]
    print('-- hmtx glyph cu thay doi:', len(hch))
    # glyph moi
    print('-- glyph moi:', nb - na, '(1 than i, 22 dau/gach simple, 135 composite; liet ke 24 dau, them -v de liet ke het)')
    verbose = '-v' in sys.argv
    sys.argv = [a for a in sys.argv if a != '-v']
    for i in range(na, nb):
        if not verbose and i >= na + 24:
            break
        g = fb['glyf'][ob[i]]
        kind = 'composite(%d)' % len(g.components) if g.isComposite() else 'simple(%d contour)' % g.numberOfContours
        print('   gid %5d  %-18s %-22s adv %4d  bbox %s' % (i, ob[i], kind, hb[ob[i]][0], (g.xMin, g.yMin, g.xMax, g.yMax)))
    # cmap
    ca, cb = fa.getBestCmap(), fb.getBestCmap()
    gi_a = {n: i for i, n in enumerate(oa)}
    added = sorted(set(cb) - set(ca))
    modified = sorted(u for u in ca if u in cb and gi_a[ca[u]] != fb.getGlyphID(cb[u]))
    removed = sorted(set(ca) - set(cb))
    print('-- cmap: them %d, doi %d, bo %d' % (len(added), len(modified), len(removed)))
    print('   them  :', ' '.join('U+%04X' % u for u in added))
    print('   doi   :', ' '.join('U+%04X(gid %d -> %d)' % (u, gi_a[ca[u]], fb.getGlyphID(cb[u])) for u in modified))
    print('-- ket luan: %d glyph cu thay doi byte (glyph cu bi thay chi qua cmap U+0111), %d glyph cu hmtx doi' % (len(changed), len(hch)))
    print('   metrics doc (hhea/OS2/head ascent/descent/UPM) giong:',
          all(getattr(fa[t], k) == getattr(fb[t], k) for t, k in
              (('hhea', 'ascent'), ('hhea', 'descent'), ('hhea', 'lineGap'), ('head', 'unitsPerEm'),
               ('OS/2', 'sTypoAscender'), ('OS/2', 'sTypoDescender'), ('OS/2', 'usWinAscent'), ('OS/2', 'usWinDescent'))))
    return 0 if not changed and not hch else 1


if __name__ == '__main__':
    sys.exit(main())
