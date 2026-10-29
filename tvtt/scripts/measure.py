"""Do do rong px (font f12, giong VH.width: bo qua $n, ： = ':') cua chuoi.  measure.py "chuoi" ...  hoac import width()"""
import sys, os, struct
_d = open(os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'tools', 'fonts', 'f12.bin'), 'rb').read()
_l = struct.unpack('>H', _d[:2])[0]; _cs = _d[2:2 + _l].decode(); _w = _d[3 + _l:3 + _l + len(_cs)]
W = {c: _w[i] for i, c in enumerate(_cs)}
MAP = {'：': ':', '，': ',', '。': '.', '！': '!', '？': '?', '（': '(', '）': ')', '、': ',', '％': '%'}


def width(s):
    t = 0; i = 0
    while i < len(s):
        c = s[i]
        if c == '$':
            i += 2; continue
        c = MAP.get(c, c)
        t += W.get(c, 12 if ord(c) >= 0x2e80 else W[' ']); i += 1
    return t


if __name__ == '__main__':
    for a in sys.argv[1:]:
        print(width(a), a)
