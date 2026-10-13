"""Ve chu bang font f12.bin cua bo tool (dung dung advance trong file, khong tao glyph moi)."""
import sys, os
sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..', 'work', 'tools', 'pixeltext-kit'))
from pixeltext import container
from PIL import Image
_d = container.decode(open(os.path.join(os.path.dirname(__file__), '..', 'work', 'tools', 'fonts', 'f12.bin'), 'rb').read())
H = _d['height']
W = dict(zip(_d['chars'], _d['widths']))
OFF = {}
_o = 0
for c, w in zip(_d['chars'], _d['widths']):
    OFF[c] = _o; _o += w
def width(s):
    return sum(W.get(c, W[' ']) for c in s)
def render(s, rgba):
    """anh RGBA trong suot, chu 1 mau; loi neu ky tu khong co trong font"""
    miss = [c for c in s if c not in W]
    if miss: raise ValueError('thieu glyph: %r' % ''.join(miss))
    im = Image.new('RGBA', (max(1, width(s)), H), (0, 0, 0, 0))
    x = 0
    for c in s:
        for y in range(H):
            for k in range(W[c]):
                if _d['rows'][y][OFF[c] + k]: im.putpixel((x + k, y), rgba)
        x += W[c]
    return im
