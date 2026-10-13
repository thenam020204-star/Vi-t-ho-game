"""Anh nho co chu: LOAD (anim 10, '载入中' -> 'Dang tai'), png74 (秘籍 -> Bi kip), png43 (bieu tuong cua hang).
  python3 make_small.py <jar_x dir> <anim.bin vao> <res_vi dir> <anim.bin ra>"""
import sys, os
sys.path.insert(0, 'scripts')
from PIL import Image
import anim, f12
from make_png7 import outlined, C


def save_pal(img, path, transparent=True):
    rgb = img.convert('RGB')
    q = rgb.quantize(colors=255, method=Image.Quantize.MEDIANCUT, dither=Image.Dither.NONE)
    pal = q.getpalette()[:255 * 3]; idx = q.load(); al = img.getchannel('A').load()
    p = Image.new('P', img.size); p.putpalette([255, 0, 255] + pal); op = p.load()
    for y in range(img.height):
        for x in range(img.width):
            op[x, y] = 0 if al[x, y] < 128 else idx[x, y] + 1
    p.save(path, format='PNG', optimize=True, transparency=0)


def get(an, i):
    return next(a for a in an if a['id'] == i)


src, ain, dst, aout = sys.argv[1:5]
an, tail = anim.parse(open(ain, 'rb').read())

# ---------- LOAD: 3 chu nay len (M9 载, M10 入, M11 中) -> 'Dang' + 'tai', M11 bo
im = Image.open(os.path.join(src, 'LOAD')).convert('RGBA')
A = get(an, 10)
w1 = outlined('Đang', '2bc8ff', '0884c6'); w2 = outlined('tải', '2bc8ff', '0884c6')
H = im.height + 16
out = Image.new('RGBA', (im.width, H), (0, 0, 0, 0)); out.paste(im, (0, 0))
out.alpha_composite(w1, (0, im.height + 1)); out.alpha_composite(w2, (w1.width + 2, im.height + 1))
A['mods'][9] = (im.height + 1, 0, w1.width, w1.height)
A['mods'][10] = (im.height + 1, w1.width + 2, w2.width, w2.height)
A['mods'][11] = (H - 1, im.width - 1, 1, 1)
for fi in range(4, 10):
    A['frames'][fi] = [(m, dy - 1 if m in (9, 10) else dy, {9: -38 - w1.width - w2.width - 2, 10: -38 - w2.width}.get(m, dx), fl)
                       for (m, dy, dx, fl) in A['frames'][fi]]
save_pal(out, os.path.join(dst, 'LOAD'))

# ---------- png74: nut 秘籍 (M3 cam y=29 40x23) va ban toi (y=53..74) ve lai tai cho
im = Image.open(os.path.join(src, 'png74')).convert('RGBA')
for (y0, y1, body, line) in ((29, 52, 'f8820e', '511911'), (53, 75, 'fce223', '241110')):
    for y in range(y0, y1):
        for x in range(0, im.width):
            if y0 == 53 or x < 40:
                im.putpixel((x, y), (0, 0, 0, 0))
    t = outlined('Bí kíp', body, line)
    wbox = 40 if y0 == 29 else 44
    im.alpha_composite(t, ((wbox - t.width) // 2, y0 + (y1 - y0 - t.height) // 2))
save_pal(im, os.path.join(dst, 'png74'))

# ---------- png43: bieu tuong cua hang tren dau NPC: giu phan hinh tren, thay chu Han bang tu Han Viet
im = Image.open(os.path.join(src, 'png43')).convert('RGBA')
A = get(an, 43)
words = ['Giáp', 'Võ', 'Dược', 'Kỹ', 'Tạp']
cells = []
for mi in range(10):
    y, x, w, h = A['mods'][mi]
    o = im.crop((x, y, x + w, y + h))
    word = words[mi // 2]
    t = outlined(word, 'fae568', 'e21410')
    W = max(w, t.width + 2); Hh = h
    cv = Image.new('RGBA', (W, Hh), (0, 0, 0, 0))
    top = o.crop((0, 0, w, 12))
    # bo vien xanh cu cua phan tren, ve lai sau
    tp = top.load()
    for yy in range(top.height):
        for xx in range(top.width):
            if tp[xx, yy][:3] == (0, 254, 240): tp[xx, yy] = (0, 0, 0, 0)
    cv.alpha_composite(top, ((W - w) // 2, 1))
    cv.alpha_composite(t, ((W - t.width) // 2, Hh - t.height - 1))
    # vien ngoai xanh nhu ban goc
    px = cv.load(); add = []
    for yy in range(Hh):
        for xx in range(W):
            if px[xx, yy][3] < 128 and any(0 <= xx + dx < W and 0 <= yy + dy < Hh and px[xx + dx, yy + dy][3] >= 128
                                           for dx, dy in ((1, 0), (-1, 0), (0, 1), (0, -1))):
                add.append((xx, yy))
    for p in add: px[p] = (0, 254, 240, 255)
    cells.append(cv)
CW = max(c.width for c in cells)
out = Image.new('RGBA', (CW * 2 + 1, sum(cells[i].height for i in range(0, 10, 2)) + 5), (0, 0, 0, 0))
yy = 0
for i in range(0, 10, 2):
    for j in (0, 1):
        c = cells[i + j]; xx = j * (CW + 1)
        out.alpha_composite(c, (xx, yy))
        oy, ox, ow, oh = A['mods'][i + j]
        A['mods'][i + j] = (yy, xx, c.width, c.height)
    yy += cells[i].height + 1
for fi, f in enumerate(A['frames']):
    nf = []
    for (m, dy, dx, fl) in f:
        nf.append((m, dy, -(A['mods'][m][2] // 2), fl))
    A['frames'][fi] = nf
save_pal(out, os.path.join(dst, 'png43'))
# ---------- png55: bien hieu cua tiem (bieu tuong nho tren dau NPC): 武 Vo, 药 Duoc, 粮 Gao, 赌 Bac
im = Image.open(os.path.join(src, 'png55')).convert('RGBA')
A = get(an, 55)
signs = {27: ('Võ', (200, 0, 0, 255), True), 28: ('Dược', (11, 94, 176, 255), True),
         29: ('Gạo', (30, 30, 28, 255), False), 30: ('Bạc', (30, 30, 28, 255), False)}
out = Image.new('RGBA', (im.width, im.height + 14), (0, 0, 0, 0)); out.paste(im, (0, 0))
X = 0
for m, (word, col, border) in signs.items():
    y, x, w, h = A['mods'][m]
    o = im.crop((x, y, x + w, y + h))
    t = f12.render(word, col)
    bb = t.getbbox(); t = t.crop((0, bb[1], t.width, bb[3]))
    W = t.width + 4
    pl = Image.new('RGBA', (W, h), o.getpixel((2, 2)) if border else (245, 249, 231, 255))
    if border:
        for xx in range(W):
            pl.putpixel((xx, 0), o.getpixel((6, 0))); pl.putpixel((xx, h - 1), o.getpixel((6, h - 1)))
        for yy in range(h):
            pl.putpixel((0, yy), o.getpixel((0, 6))); pl.putpixel((W - 1, yy), o.getpixel((w - 1, 6)))
    pl.alpha_composite(t, (2, max(0, (h - t.height) // 2)))
    out.alpha_composite(pl, (X, im.height + 1))
    A['mods'][m] = (im.height + 1, X, W, h)
    X += W + 1
for fi, f in enumerate(A['frames']):
    A['frames'][fi] = [(m, dy, (-(A['mods'][m][2] // 2) if m in signs else dx), fl) for (m, dy, dx, fl) in f]
save_pal(out, os.path.join(dst, 'png55'))
open(aout, 'wb').write(anim.write(an, tail))
print('ok LOAD png74 png43 png55')
