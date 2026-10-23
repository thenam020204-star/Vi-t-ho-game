"""Doc f18.bin (font bitmap 18px). Dinh dang: writeUTF(charset) | byte height | byte width[n] | atlas 1bpp
theo hang, LSB-first, do rong hang = tong width, bit lien tuc (khong dem byte)."""
import struct, sys, os

DEFAULT = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'work', 'nam_p', 'NAM_P', 'fonts', 'f18.bin')


class F18:
    def __init__(self, path=DEFAULT):
        d = open(path, 'rb').read()
        n = struct.unpack('>H', d[:2])[0]
        self.charset = d[2:2 + n].decode('utf-8')
        p = 2 + n
        self.height = d[p]
        p += 1
        cnt = len(self.charset)
        self.widths = list(d[p:p + cnt])
        p += cnt
        self.atlas_w = sum(self.widths)
        bits = d[p:]
        self.offsets = []
        o = 0
        for w in self.widths:
            self.offsets.append(o)
            o += w
        self._bits = bits
        self.rows_bits = [[(bits[(y * self.atlas_w + x) >> 3] >> ((y * self.atlas_w + x) & 7)) & 1
                           for x in range(self.atlas_w)] for y in range(self.height)]

    def glyph(self, ch):
        """Tra ve list height hang, moi hang list 0/1 (do rong = width cua ky tu)."""
        i = self.charset.index(ch)
        o, w = self.offsets[i], self.widths[i]
        return [r[o:o + w] for r in self.rows_bits]

    def width(self, ch):
        return self.widths[self.charset.index(ch)]

    def ascii(self, ch):
        return '\n'.join(''.join('#' if v else '.' for v in r) for r in self.glyph(ch))

    def __contains__(self, ch):
        return ch in self.charset


if __name__ == '__main__':
    f = F18()
    print(f.height, len(f.charset), f.atlas_w)
    for ch in sys.argv[1:] or ['a', 'ấ']:
        print(ch, f.width(ch)); print(f.ascii(ch)); print()
