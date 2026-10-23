"""Xuat anh preview cho T4:  font/preview/marks.png, marks_context.png, glyphs_all.png.
   python3 -I tools/preview_marks.py
TTF tam chi de render preview (work/font/_preview.ttf), KHONG phai san pham T5."""
import os
import sys

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.abspath(os.path.join(HERE, '..'))
sys.path.insert(0, HERE)
sys.path.insert(0, os.path.join(ROOT, 'font'))

from PIL import Image, ImageDraw, ImageFont
from fontTools.ttLib import TTFont, newTable
from fontTools import subset
from fontTools.pens.ttGlyphPen import TTGlyphPen

import marks
import layout
from f18 import F18

JEJU = os.path.join(ROOT, 'work', 'font', 'JejuHallasan.ttf')
TMP = os.path.join(ROOT, 'work', 'font', '_preview.ttf')
OUT = os.path.join(ROOT, 'font', 'preview')

BG, FG, GRID, CTRL, ACC = (255, 255, 255), (20, 20, 20), (205, 205, 215), (220, 40, 40), (30, 90, 200)


class _AnyGS(dict):
    def __contains__(self, k):
        return True


# TTF tam
def build_preview_ttf():
    opts = subset.Options()
    opts.hinting = True
    opts.glyph_names = True
    opts.layout_features = []
    opts.notdef_outline = True
    opts.name_IDs = ['*']
    uni = list(range(0x20, 0x7F)) + [0x131, 0xD0, 0x111, 0x2022, 0x2026]
    f = TTFont(JEJU)
    s = subset.Subsetter(opts)
    s.populate(unicodes=uni)
    s.subset(f)
    cmap = f.getBestCmap()
    glyf, hmtx = f['glyf'], f['hmtx']
    order = f.getGlyphOrder()[:]
    new = {}
    for name in marks.MARKS:
        for v in ('R', 'C', 'S'):
            pen = TTGlyphPen(None)
            marks.draw(pen, name, v)
            gname = 'mk_%s_%s' % (name, v)
            new[gname] = pen.glyph()
    cm = {}
    for ch, (body, ms) in layout.table().items():
        gname = 'u%04X' % ord(ch)
        pen = TTGlyphPen(_AnyGS())
        pen.addComponent(cmap[ord(body)], (1, 0, 0, 1, 0, 0))
        for (m, v, dx, dy) in ms:
            pen.addComponent('mk_%s_%s' % (m, v if v in 'RCS' else 'R'), (1, 0, 0, 1, round(dx), round(dy)))
        new[gname] = pen.glyph()
        cm[ord(ch)] = (gname, hmtx[cmap[ord(body)]][0])
    for gname, g in new.items():
        order.append(gname)
        glyf.glyphs[gname] = g
        hmtx.metrics[gname] = (0, 0)
    for ch, (gname, adv) in cm.items():
        hmtx.metrics[gname] = (adv, 0)
    f.setGlyphOrder(order)
    glyf.glyphOrder = order
    for t in f['cmap'].tables:
        if t.isUnicode():
            for u, (g, a) in cm.items():
                t.cmap[u] = g
    f['maxp'].numGlyphs = len(order)
    for t in ('post',):
        if t in f:
            f['post'].formatType = 2.0
            f['post'].extraNames = []
            f['post'].mapping = {}
    f.save(TMP)
    return TMP


# ------------------------------------------------------------------------------------------------ ve contour
def flatten(c, n=14):
    """Contour (marks.py) -> polyline xap xi + danh sach diem on/off de ve."""
    pts = [(p[0], p[1], len(p) < 3) for p in c]
    k = next(i for i, p in enumerate(pts) if p[2])
    pts = pts[k:] + pts[:k]
    poly = [pts[0][:2]]
    cur = pts[0][:2]
    i = 1
    m = len(pts)
    offs = []
    seq = pts[1:] + [pts[0]]
    for p in seq:
        if p[2]:
            if offs:
                a = offs[0] if len(offs) == 1 else None
                ctrl = offs
                # nhieu off lien tiep: chia bang trung diem (khong dung o day)
                q0 = cur
                for j, o in enumerate(ctrl):
                    q2 = p[:2] if j == len(ctrl) - 1 else ((o[0] + ctrl[j + 1][0]) / 2, (o[1] + ctrl[j + 1][1]) / 2)
                    for t in range(1, n + 1):
                        u = t / n
                        poly.append(((1 - u) ** 2 * q0[0] + 2 * (1 - u) * u * o[0] + u * u * q2[0],
                                     (1 - u) ** 2 * q0[1] + 2 * (1 - u) * u * o[1] + u * u * q2[1]))
                    q0 = q2
                offs = []
            else:
                poly.append(p[:2])
            cur = p[:2]
        else:
            offs.append(p[:2])
    return poly


def draw_mark_vec(d, name, v, ox, oy, s, ctrl=True, color=FG):
    """Ve dau o toa do anh: goc (ox,oy) ung voi (0,0) cuc bo; s = pixel / don vi font."""
    for c in marks.contours(name, v):
        poly = [(ox + x * s, oy - y * s) for x, y in flatten(c)]
        d.polygon(poly, fill=color)
    if ctrl:
        for c in marks.contours(name, v):
            pts = [(ox + p[0] * s, oy - p[1] * s, len(p) < 3) for p in c]
            n = len(pts)
            for i, (x, y, on) in enumerate(pts):
                if not on:   # duong noi off-curve toi on-curve ke
                    for j in (i - 1, (i + 1) % n):
                        if pts[j][2]:
                            d.line([(x, y), pts[j][:2]], fill=(235, 150, 150), width=1)
            for x, y, on in pts:
                if on:
                    d.rectangle([x - 3, y - 3, x + 3, y + 3], outline=ACC, fill=(255, 255, 255))
                else:
                    d.ellipse([x - 3, y - 3, x + 3, y + 3], outline=CTRL, fill=CTRL)


def f18_bitmap(d, rows, x0, y0, px, color=FG, on_color=None):
    for y, r in enumerate(rows):
        for x, v in enumerate(r):
            if v:
                d.rectangle([x0 + x * px, y0 + y * px, x0 + (x + 1) * px - 1, y0 + (y + 1) * px - 1], fill=color)


def text(d, xy, s, size=15, fill=FG):
    d.text(xy, s, font=ImageFont.truetype('/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf', size), fill=fill)


# ------------------------------------------------------------------------------------------------ marks.png
SAMPLE = {   # (dau, mau chu thuong f18, mau chu hoa f18)
    'acute': ('á', 'Á'), 'grave': ('à', 'À'), 'hook': ('ả', 'Ả'), 'tilde': ('ã', 'Ã'), 'dotbelow': ('ạ', 'Ạ'),
    'circumflex': ('â', 'Â'), 'breve': ('ă', 'Ă'), 'horn': ('ơ', 'Ơ'),
}
BASE_OF = {'á': 'a', 'à': 'a', 'ả': 'a', 'ã': 'a', 'ạ': 'a', 'â': 'a', 'ă': 'a', 'ơ': 'o',
           'Á': 'A', 'À': 'A', 'Ả': 'A', 'Ã': 'A', 'Ạ': 'A', 'Â': 'A', 'Ă': 'A', 'Ơ': 'O'}


def f18_mark_rows(f, ch):
    """Bitmap f18 chi con dau (tru than chu goc). Tra ve (rows, y0 hang dau tien khac rong)."""
    g = f.glyph(ch)
    b = f.glyph(BASE_OF[ch])
    return [[1 if (g[y][x] and not b[y][x]) else 0 for x in range(len(g[0]))] for y in range(18)]


def make_marks_png():
    f = F18()
    names = ['acute', 'grave', 'hook', 'tilde', 'dotbelow', 'circumflex', 'breve', 'horn']
    S = 1.55                        # px / don vi font cho contour
    PXL = 83 * S                    # 1 pixel f18 (chu thuong) tuong ung 83 don vi
    cellw, rowh = 520, 400
    W = 330 + cellw * 3 + 40
    H = 70 + rowh * len(names)
    im = Image.new('RGB', (W, H), BG)
    d = ImageDraw.Draw(im)
    text(d, (20, 14), 'marks.png: moi hang = 1 loai dau | trai: bitmap f18 phong to (1 o = 83 don vi, chu thuong / chu hoa=65) | phai: contour vector R, C, S voi diem dieu khien '
         '(vuong xanh = on-curve, tron do = off-curve)', 15)
    text(d, (350, 42), 'R  chu thuong / dau chong chu thuong', 15)
    text(d, (350 + cellw, 42), 'C  chu hoa (don)', 15)
    text(d, (350 + 2 * cellw, 42), 'S  chu hoa, dau chong', 15)
    for r, name in enumerate(names):
        y0 = 70 + r * rowh
        d.line([(0, y0), (W, y0)], fill=GRID)
        text(d, (12, y0 + 6), name, 18, ACC)
        # f18: chu thuong va chu hoa canh nhau, vung dau + than
        for k, ch in enumerate(SAMPLE[name]):
            px = 12 if k == 0 else 10
            g = f.glyph(ch)
            rows = g
            # chi cat vung hang 0..(14|17) de du dau, tinh tu trai
            x0 = 14 + k * 150
            top = y0 + 36
            # than chu do xam, dau do den
            mk = f18_mark_rows(f, ch)
            for y in range(18):
                for x in range(len(g[0])):
                    if g[y][x]:
                        col = FG if mk[y][x] else (175, 175, 190)
                        d.rectangle([x0 + x * px, top + y * px, x0 + (x + 1) * px - 1, top + (y + 1) * px - 1], fill=col)
            text(d, (x0, y0 + 38 + 18 * px + 2), '%s f18 (dau = den)' % ch, 13)
        for k, v in enumerate(('R', 'C', 'S')):
            if v not in marks.MARKS[name]:
                continue
            bx = 330 + k * cellw
            bb = marks.bbox(name, v)
            ox = bx + cellw / 2 - 20
            oy = y0 + rowh - 110 - 0
            # luoi: day dau (y=0)
            d.line([(bx + 10, oy), (bx + cellw - 10, oy)], fill=GRID)
            d.line([(ox, y0 + 30), (ox, y0 + rowh - 20)], fill=GRID)
            draw_mark_vec(d, name, v, ox, oy, S)
            text(d, (bx + 10, y0 + rowh - 40), 'bbox x %d..%d, y %d..%d  (%dx%d)' % (bb[0], bb[2], bb[1], bb[3], bb[2] - bb[0], bb[3] - bb[1]), 13)
    os.makedirs(OUT, exist_ok=True)
    im.save(os.path.join(OUT, 'marks.png'))


# ------------------------------------------------------------------------------------------------ context + all
def f18_glyph_image(f, ch, px, color=FG):
    g = f.glyph(ch)
    im = Image.new('RGB', (len(g[0]) * px, 18 * px), BG)
    d = ImageDraw.Draw(im)
    f18_bitmap(d, g, 0, 0, px, color)
    return im


def jeju_font(size, path=TMP):
    return ImageFont.truetype(path, size)


def draw_ttf_text(d, xy, s, size, ttf=TMP, fill=FG):
    d.text(xy, s, font=ImageFont.truetype(ttf, size), fill=fill)


def make_context_png():
    f = F18()
    chars = 'áàảãạâăơưấầẩẫậắằẳẵặếềểễệốồổỗộớờởỡợứừửữựíìỉĩịýỳỷỹỵ'
    chars_u = 'ÁÀẢÃẠÂĂƠƯẤẦẨẪẬẮẰẲẴẶẾỀỂỄỆỐỒỔỖỘỚỜỞỠỢỨỪỬỮỰÍÌỈĨỊÝỲỶỸỴĐ'
    px = 5
    SZ = 78           # px cao cua em Jeju
    cw = 100
    per = 14
    def block(cs, y0, d, im):
        for i, ch in enumerate(cs):
            col, row = i % per, i // per
            x = 10 + col * (cw * 2 + 10)
            y = y0 + row * 130
            g = f18_glyph_image(f, ch, px) if ch in f else None
            if g:
                im.paste(g, (x, y + 8))
            draw_ttf_text(d, (x + cw + 10, y - 6), ch, SZ)
    rows_l = (len(chars) + per - 1) // per
    rows_u = (len(chars_u) + per - 1) // per
    H = 20 + 130 * (rows_l + rows_u) + 40
    W = 10 + per * (cw * 2 + 10)
    im = Image.new('RGB', (W, H), BG)
    d = ImageDraw.Draw(im)
    block(chars, 10, d, im)
    block(chars_u, 20 + 130 * rows_l, d, im)
    im.save(os.path.join(OUT, 'marks_context.png'))


def make_glyphs_all():
    t = layout.table()
    lows = [c for c in layout.all_chars() if c.islower() or c == 'đ']
    ups = [c for c in layout.all_chars() if c.isupper()]
    SZ = 96
    cell_w, cell_h = 220, 150
    per = 8
    def grid(cs):
        return (len(cs) + per - 1) // per
    sample = 'Trò chơi mới • Phép thuật • ĐỘC DƯỢC • Kỹ năng tăng 50% sát thương'
    H = 40 + cell_h * (grid(lows) + grid(ups)) + 80 + 3 * 120 + 60
    W = per * cell_w + 20
    im = Image.new('RGB', (W, H), BG)
    d = ImageDraw.Draw(im)
    text(d, (10, 8), 'glyphs_all.png: 134 chu Viet ghep tam (trai) canh chu goc Jeju (phai, mau xam: ky tu goc khong dau khi co)', 15)
    y = 40
    for cs in (lows, ups):
        for i, ch in enumerate(cs):
            col, row = i % per, i // per
            x = 10 + col * cell_w
            yy = y + row * cell_h
            d.line([(x, yy + 110), (x + cell_w - 12, yy + 110)], fill=(235, 235, 245))
            draw_ttf_text(d, (x, yy), ch, SZ)
            body = layout.body_of(ch)
            gc = {'ı': 'i'}.get(body, body)
            if gc != ch:
                draw_ttf_text(d, (x + 100, yy), body, SZ, fill=(150, 150, 165))
            else:
                text(d, (x + 110, yy + 40), '(Jeju)', 13, (150, 150, 165))
        y += grid(cs) * cell_h
    y += 10
    text(d, (10, y), 'Cau mau, 18 / 24 / 30 px (FreeType, Jeju + dau ve tay):', 15)
    y += 28
    for sz in (18, 24, 30):
        draw_ttf_text(d, (10, y), sample, sz)
        y += sz + 28
    # cau mau phong to de soi dau
    draw_ttf_text(d, (10, y), 'ÂẤẦẨẪẬ ẮẰẲẴẶ ẾỀỂỄỆ ỐỒỔỖỘ ỚỜỞỠỢ ỨỪỬỮỰ', 60)
    y += 90
    draw_ttf_text(d, (10, y), 'Tiếng Việt: đường nguyễn quỳnh hương ướt', 60)
    im.crop((0, 0, W, y + 90)).save(os.path.join(OUT, 'glyphs_all.png'))


def make_samples_png():
    sample = 'Trò chơi mới • Phép thuật • ĐỘC DƯỢC • Kỹ năng tăng 50% sát thương'
    im = Image.new('RGB', (1400, 330), BG)
    d = ImageDraw.Draw(im)
    y = 8
    for sz in (17, 18, 24, 30):
        draw_ttf_text(d, (10, y), sample, sz)
        y += sz + 18
    # so sanh voi Jeju goc (khong dau: chi hien ky tu co)
    im.save(os.path.join(OUT, 'sample_tmp.png'))


def main():
    os.makedirs(OUT, exist_ok=True)
    build_preview_ttf()
    make_marks_png()
    make_context_png()
    make_glyphs_all()
    print('ok ->', OUT)


if __name__ == '__main__':
    main()
