#!/usr/bin/env python3
"""Vietnamese versions of the images with Korean text (drawn with the user's pixeltext kit, 15px / 10px font).
menu.mgr: 12/13 rating badges, 14-17 prologue lines, 18 "no such god" line; gmenu.mgr: 38/39 result subtitles.
All images are drawn centred on their anchor, so content is placed by padding symmetrically (see mk_title.py)."""
import os, sys, subprocess
sys.path.insert(0, '/home/claude/work/img5/pixeltext-kit')
import pixeltext as pt
from PIL import Image, ImageDraw
W = os.path.dirname(os.path.abspath(__file__))
R = '/home/claude/work/hl5/jar/res/c'
OUT = os.path.join(W, 'vi_img'); os.makedirs(OUT, exist_ok=True)
sys.path.insert(0, W)
from mgr import parse, decode

def orig(bank, i):
    return decode(parse(open(R + bank, 'rb').read())[i][1])[2]

def place(content, x0, y0, ax, ay):
    w, h = content.size
    CW = 2 * max(ax - x0, x0 + w - ax, 1); CH = 2 * max(ay - y0, y0 + h - ay, 1)
    c = Image.new('RGBA', (CW, CH), (0, 0, 0, 0))
    c.paste(content, (x0 - (ax - CW // 2), y0 - (ay - CH // 2)), content)
    return c

def lines(texts, size=15, fill='#d8d8d8', outline=None, align='left', gap=1):
    ims = [pt.render(t, size=size, fill=fill, outline=outline, trim=True) for t in texts]
    lh = max(15 if size == 15 else 10, max(i.height for i in ims)) + gap
    wmax = max(i.width for i in ims)
    c = Image.new('RGBA', (wmax, lh * len(ims)), (0, 0, 0, 0))
    for k, im in enumerate(ims):
        x = 0 if align == 'left' else (wmax - im.width) // 2
        c.paste(im, (x, k * lh + (lh - im.height) // 2), im)
    return c

files = {}
# prologue (anchors = centre of the original images on screen)
p14 = lines(['Cuộc tập kích của Cổ tộc', 'bắt đầu từ Thánh Địa...'])
files[('menu', 14)] = place(p14, 4, 96, 95, 112)
p15 = lines(['Loài người và Nemed phải ngừng chiến,', 'quần đảo Orbis tìm lại hoà bình, nhưng -'])
files[('menu', 15)] = place(p15, 4, 134, 130, 145)
p16 = lines(['Phải chăng đó là tai hoạ thần giáng xuống', 'để chấm dứt cuộc chiến vô nghĩa?'])
files[('menu', 16)] = place(p16, 4, 172, 95, 185)
p17 = lines(['Có thể khẳng định...'], fill='#f0f0f0')
files[('menu', 17)] = place(p17, 120 - p17.width // 2, 160 - p17.height // 2, 120, 160)
# 18: orange text on the original dark glow (interior repainted dark, glow edge kept)
from PIL import ImageFilter
big = orig('/img/menu.mgr', 18).copy()
alpha = big.getchannel('A').point(lambda v: 255 if v else 0)
inner = alpha.filter(ImageFilter.MinFilter(11))
px = big.load(); pin = inner.load()
dark = (24, 10, 4, 255)
for y in range(big.height):
    for x in range(big.width):
        # inner rounded rectangle
        dx = max(0, 9 - x, x - (big.width - 10)); dy = max(0, 7 - y, y - (big.height - 8))
        if dx * dx + dy * dy <= 36 and px[x, y][3]: px[x, y] = dark
        elif 9 <= x < big.width - 9 and 7 <= y < big.height - 7: px[x, y] = dark
t18 = lines(['Thế giới này', 'không có vị thần nào như thế.'], fill='#ffb13b', align='center', gap=0)
big.paste(t18, ((big.width - t18.width) // 2, (big.height - t18.height) // 2), t18)
files[('menu', 18)] = big

# rating badges: repaint the label band
def badge(i, text):
    im = orig('/img/menu.mgr', i).copy(); px = im.load()
    ys = [y for y in range(im.height * 2 // 3, im.height) for x in range(im.width) if px[x, y][3] and min(px[x, y][:3]) > 200]
    y0, y1 = min(ys) - 1, max(ys) + 1
    band = px[6, (y0 + y1) // 2]
    for y in range(y0, y1 + 1):
        for x in range(4, im.width - 4):
            if px[x, y][3]: px[x, y] = band
    t = pt.render(text, size=15, fill='#ffffff', trim=True)
    im.paste(t, ((im.width - t.width) // 2, (y0 + y1 + 1) // 2 - t.height // 2), t)
    return im
files[('menu', 12)] = badge(12, 'Từ 12 tuổi')
files[('menu', 13)] = badge(13, 'May rủi')

# Success / Failed subtitles
def result(i, text):
    im = orig('/img/gmenu.mgr', i).copy(); px = im.load()
    ys = [y for y in range(im.height // 2, im.height) for x in range(im.width) if px[x, y][3] and min(px[x, y][:3]) > 170]
    y0, y1 = min(ys) - 1, max(ys) + 1
    xs = [x for y in range(y0, y1 + 1) for x in range(im.width) if px[x, y][3] and min(px[x, y][:3]) > 170]
    x0, x1 = min(xs) - 1, max(xs) + 1
    base = px[x0 - 1, (y0 + y1) // 2]
    for y in range(y0, y1 + 1):
        for x in range(x0, x1 + 1):
            if px[x, y][3]: px[x, y] = base
    t = pt.render(text, size=10, fill='#ffffff', outline='#000000', trim=True)
    im.paste(t, ((im.width - t.width) // 2, (y0 + y1 + 1) // 2 - t.height // 2), t)
    return im
files[('gmenu', 38)] = result(38, 'Thành công!')
files[('gmenu', 39)] = result(39, 'Thất bại!')

for (bank, i), im in files.items():
    im.save(os.path.join(OUT, '%s_%03d.png' % (bank, i)))
for bank in ('menu', 'gmenu'):
    args = ['%d=%s' % (i, os.path.join(OUT, '%s_%03d.png' % (b, i))) for (b, i) in files if b == bank]
    dst = os.path.join(W, 'overlay/res/c/img/%s.mgr' % bank); os.makedirs(os.path.dirname(dst), exist_ok=True)
    subprocess.check_call([sys.executable, os.path.join(W, 'mgr_replace.py'), R + '/img/%s.mgr' % bank, dst] + args)
