"""Kiem tra JejuHallasan-VI.ttf: fontTools mo lai, 134/134 chu Viet trong cmap, FreeType render 17/18/24/30px, ots-sanitize.
   python3 -I tools/verify_font.py [font.ttf]"""
import os
import subprocess
import sys

import freetype
from fontTools.ttLib import TTFont

ROOT = os.path.abspath(os.path.join(os.path.dirname(os.path.abspath(__file__)), '..'))
sys.path.insert(0, os.path.join(ROOT, 'font'))
import layout  # noqa: E402

P = sys.argv[1] if len(sys.argv) > 1 else os.path.join(ROOT, 'work', 'font', 'JejuHallasan-VI.ttf')


def main():
    ok = True
    f = TTFont(P)
    cm = f.getBestCmap()
    chars = layout.all_chars()
    miss = [c for c in chars if ord(c) not in cm]
    print('fontTools mo lai OK; numGlyphs', f['maxp'].numGlyphs, '; chu Viet trong cmap: %d/%d' % (len(chars) - len(miss), len(chars)), miss)
    ok &= not miss and len(chars) == 134
    # ma hoa lai + doc lai (glyf/loca/maxp nhat quan)
    g = f['glyf']
    for c in chars:
        g[cm[ord(c)]].getCoordinates(g)
    print('maxp', {k: getattr(f['maxp'], k) for k in ('maxPoints', 'maxContours', 'maxCompositePoints', 'maxCompositeContours', 'maxComponentElements', 'maxComponentDepth')})
    face = freetype.Face(P)
    bad = 0
    for size in (17, 18, 24, 30):
        face.set_pixel_sizes(0, size)
        for flags_name, flags in (('hinted', freetype.FT_LOAD_RENDER), ('nohint', freetype.FT_LOAD_RENDER | freetype.FT_LOAD_NO_HINTING)):
            n = 0
            for c in chars + list('Aa Đđ'):
                try:
                    face.load_char(c, flags)
                    bm = face.glyph.bitmap
                    if c != ' ' and bm.width * bm.rows == 0:
                        raise RuntimeError('bitmap rong')
                    n += 1
                except Exception as e:      # noqa: BLE001
                    bad += 1
                    print('  LOI', size, flags_name, repr(c), e)
            print('FreeType %2dpx %-6s: %d ky tu render OK' % (size, flags_name, n))
    ok &= bad == 0
    r = None
    for exe in ('ots-sanitize',):
        try:
            r = subprocess.run([exe, P], capture_output=True, text=True)
        except FileNotFoundError:
            pass
    if r is None:
        try:
            import ots
            ots.sanitize(P, check=True)
            print('ots (python) PASS')
        except Exception as e:      # noqa: BLE001
            print('ots LOI/khong co:', e)
            ok = False
    else:
        print('ots-sanitize rc=%d %s %s' % (r.returncode, r.stdout.strip()[:200], r.stderr.strip()[:300]))
        ok &= r.returncode == 0
    print('KET QUA:', 'PASS' if ok else 'FAIL')
    return 0 if ok else 1


if __name__ == '__main__':
    sys.exit(main())
