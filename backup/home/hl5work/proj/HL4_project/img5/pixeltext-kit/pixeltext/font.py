# -*- coding: utf-8 -*-
"""Font objects and glyph metrics."""
import os
from . import container

HERE = os.path.dirname(os.path.abspath(__file__))
FONTS = {
    10: 'font_10px.bin',
    '10v2': 'font_10px_v2.bin',      # 10px, Vietnamese diacritics redrawn
    13: 'font_13px.bin',
    15: 'font_15px.bin',
    '15w': 'font_15px_wide.bin',      # the original fontcusvnrebuilt v2 cut
}
SIZES = (10, 13, 15)          # sizes fit() may choose from
ALL = (10, '10v2', 13, 15)    # every loadable cut

_cache = {}


class Glyph(object):
    __slots__ = ('ch', 'bitmap', 'advance', 'x0', 'x1', 'y0', 'y1')

    def __init__(self, ch, bitmap, advance):
        self.ch = ch
        self.bitmap = bitmap                     # list of rows, each list of 0/1
        self.advance = advance
        cols = [x for x in range(advance) if any(r[x] for r in bitmap)]
        rws = [y for y, r in enumerate(bitmap) if any(r)]
        self.x0, self.x1 = (cols[0], cols[-1]) if cols else (0, -1)
        self.y0, self.y1 = (rws[0], rws[-1]) if rws else (0, -1)

    @property
    def blank(self):
        return self.x1 < self.x0

    @property
    def ink_w(self):
        return 0 if self.blank else self.x1 - self.x0 + 1


class Font(object):
    """A loaded bitmap font.  `Font.load(13)` is the normal entry point."""
    _amb = None

    def __init__(self, data, name):
        f = container.decode(data)
        self.name = name
        self.height = f['height']
        self.chars = f['chars']
        self.glyphs = {}
        o = 0
        for ch, w in zip(f['chars'], f['widths']):
            bm = [[f['rows'][y][o + x] for x in range(w)] for y in range(f['height'])]
            self.glyphs[ch] = Glyph(ch, bm, w)
            o += w
        used = [g for g in self.glyphs.values() if not g.blank]
        self.ink_top = min(g.y0 for g in used)
        self.ink_bottom = max(g.y1 for g in used)
        # baseline = bottom row of capital letters
        caps = [self.glyphs[c] for c in 'ABDEFHIKLMNPRTUVWXYZ' if c in self.glyphs]
        self.baseline = max(g.y1 for g in caps) if caps else self.ink_bottom
        self.cap_height = self.baseline - min(g.y0 for g in caps) + 1 if caps else self.height
        xs = [self.glyphs[c] for c in 'acemnorsuvwxz' if c in self.glyphs]
        self.x_height = self.baseline - min(g.y0 for g in xs) + 1 if xs else 0
        self.space = self.glyphs[' '].advance if ' ' in self.glyphs else max(2, self.height // 4)

    # ------------------------------------------------------------------ load
    @classmethod
    def load(cls, size=13):
        key = size
        if key not in _cache:
            if size not in FONTS:
                raise KeyError("unknown size %r; available: %s" % (size, list(FONTS)))
            path = os.path.join(HERE, 'fonts', FONTS[size])
            _cache[key] = cls(path, 'font_%s' % size)
        return _cache[key]

    # --------------------------------------------------------------- queries
    def has(self, ch):
        return ch in self.glyphs

    def ambiguous(self):
        """chars whose bitmap is identical to another char's.

        The 10px cut has no room for tone marks above capitals, so `Á` is drawn
        exactly like `A`.  Anything listed here will be unreadable in that font.
        """
        if getattr(self, '_amb', None) is None:
            bybm = {}
            for ch, g in self.glyphs.items():
                if g.blank: continue
                key = (g.advance, tuple(tuple(r) for r in g.bitmap))
                bybm.setdefault(key, []).append(ch)
            amb = {}
            for chars in bybm.values():
                if len(chars) > 1:
                    for c in chars:
                        amb[c] = [o for o in chars if o != c]
            self._amb = amb
        return self._amb

    def ambiguous_in(self, text):
        amb = self.ambiguous()
        seen, out = set(), []
        for ch in text:
            if ch in amb and ch not in seen:
                seen.add(ch); out.append((ch, ''.join(amb[ch])))
        return out

    def missing(self, text):
        """characters of `text` this font cannot draw (order preserved, unique)"""
        seen, out = set(), []
        for ch in text:
            if ch != '\n' and ch not in self.glyphs and ch not in seen:
                seen.add(ch); out.append(ch)
        return out

    def __repr__(self):
        return ("<Font %s height=%d cap=%d x=%d baseline=%d chars=%d>"
                % (self.name, self.height, self.cap_height, self.x_height,
                   self.baseline, len(self.glyphs)))
